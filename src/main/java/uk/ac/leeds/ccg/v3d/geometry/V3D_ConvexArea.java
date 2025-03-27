/*
 * Copyright 2022 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.v3d.geometry;

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;

/**
 * A class for representing and using coplanar convex hulls. These are a special
 * type of polygon: They have no holes and all the angles are convex. Below is a
 * basic algorithm for generating a convex hull from a set of coplanar points
 * known as the "quick hull" algorithm (see
 * <a href="https://en.wikipedia.org/wiki/Quickhull">
 * https://en.wikipedia.org/wiki/Quickhull</a>) :
 * <ol>
 * <li>Partition the points:
 * <ul>
 * <li>Calculate the distances between the points with the minimum and maximum
 * x, the minimum and maximum y, and the minimum and maximum z values.</li>
 * <li>Choose the points that have the largest distance between them to define
 * the dividing plane that is orthogonal to the plane of the polygon.</li>
 * <li>Let the points on one side of the dividing plane be one group and those
 * on the other be the another group.</li>
 * </ul></li>
 * <li>Add the two end points of the partition to the convex hull.</li>
 * <li>Deal with each group of points in turn.</li>
 * <li>If there is only one other point on a side of the partition then add it
 * to the convex hull.</li>
 * <li>If there are more than one, then find the one with the biggest distance
 * from the partition and add this to the convex hull.</li>
 * <li>We can now ignore all the other points that intersect the triangle given
 * by the 3 points now in the convex hull.</li>
 * <li>Create a new plane dividing the remaining points on this side of the
 * first dividing plane. Two points on the plane are the last point added to the
 * convex hull and the closest point on the line defined by the other two points
 * in the convex hull. The new dividing plane is orthogonal to the first
 * dividing plane.</li>
 * <li>Let the points in this group that are on one side of the dividing plane
 * be another group and those on the other be the another group.</li>
 * <li>Repeat the process dealing with each group in turn (Steps 3 to 9) in a
 * depth first manner.</li>
 * </ol>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_ConvexArea extends V3D_Area {

    private static final long serialVersionUID = 1L;

    /**
     * The collection of triangles.
     */
    public final HashMap<Integer, V3D_Triangle> triangles;

    public V3D_ConvexArea(V3D_ConvexArea c) {
        super(c.env, c.offset, c.pl);
        points = new HashMap<>();
        //c.points.forEach(x -> points.put(points.size(), new V3D_Point(x)));
        triangles = new HashMap<>();
        c.triangles.values().forEach(x
                -> triangles.put(triangles.size(), new V3D_Triangle(x)));
        edges = new HashMap<>();
        c.edges.values().forEach(x
                -> edges.put(edges.size(), new V3D_LineSegment(x)));
    }

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param triangles A non-empty list of coplanar triangles.
     */
    public V3D_ConvexArea(int oom, RoundingMode rm, V3D_Triangle... triangles) {
        this(oom, rm, triangles[0].getPl(oom, rm).n, V3D_Triangle.getPoints(
                triangles, oom, rm));
    }

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param n The normal for the plane.
     * @param points A non-empty list of points in a plane given by n.
     */
    public V3D_ConvexArea(int oom, RoundingMode rm, V3D_Vector n,
            V3D_Point... points) {
        this(oom, rm, n, Arrays.asList(points));
    }

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param n The normal for the plane.
     * @param points A non-empty list of points in a plane given by n.
     */
    public V3D_ConvexArea(int oom, RoundingMode rm, V3D_Vector n,
            List<V3D_Point> points) {
        super(points.get(0).env, points.get(0).offset, new V3D_Plane(points.get(0), n));
        this.points = new HashMap<>();
        triangles = new HashMap<>();
        edges = new HashMap<>();
        // Get a list of unique points.
        ArrayList<V3D_Point> pts = V3D_Point.getUnique(points, oom, rm);
        V3D_Vector v0 = new V3D_Vector(pts.get(0).rel);
        Math_BigRationalSqrt xmin = v0.dx;
        Math_BigRationalSqrt xmax = v0.dx;
        Math_BigRationalSqrt ymin = v0.dy;
        Math_BigRationalSqrt ymax = v0.dy;
        Math_BigRationalSqrt zmin = v0.dz;
        Math_BigRationalSqrt zmax = v0.dz;
        int xminIndex = 0;
        int xmaxIndex = 0;
        int yminIndex = 0;
        int ymaxIndex = 0;
        int zminIndex = 0;
        int zmaxIndex = 0;
        for (int i = 1; i < pts.size(); i++) {
            V3D_Point pt = pts.get(i);
            Math_BigRationalSqrt x = pt.rel.getDX();
            Math_BigRationalSqrt y = pt.rel.getDY();
            Math_BigRationalSqrt z = pt.rel.getDZ();
            if (x.compareTo(xmin) == -1) {
                xmin = x;
                xminIndex = i;
            }
            if (x.compareTo(xmax) == 1) {
                xmax = x;
                xmaxIndex = i;
            }
            if (y.compareTo(ymin) == -1) {
                ymin = y;
                yminIndex = i;
            }
            if (y.compareTo(ymax) == 1) {
                ymax = y;
                ymaxIndex = i;
            }
            if (z.compareTo(zmin) == -1) {
                zmin = z;
                zminIndex = i;
            }
            if (z.compareTo(zmax) == 1) {
                zmax = z;
                zmaxIndex = i;
            }
        }
        V3D_Point xminp = pts.get(xminIndex);
        V3D_Point xmaxp = pts.get(xmaxIndex);
        V3D_Point yminp = pts.get(yminIndex);
        V3D_Point ymaxp = pts.get(ymaxIndex);
        V3D_Point zminp = pts.get(zminIndex);
        V3D_Point zmaxp = pts.get(zmaxIndex);
        this.offset = xminp.offset;
        if (xminIndex == xmaxIndex) {
            V3D_LineSegment yd = new V3D_LineSegment(ymaxp, yminp, oom, rm);
            V3D_LineSegment zd = new V3D_LineSegment(zmaxp, zminp, oom, rm);
            BigRational ydl2 = yd.getLength2(oom, rm);
            BigRational zdl2 = zd.getLength2(oom, rm);
            if (ydl2.compareTo(zdl2) == 1) {
                compute(pts, yminp, ymaxp, n, 1, oom, rm);
            } else {
                compute(pts, zminp, zmaxp, n, 1, oom, rm);
            }
        } else if (yminIndex == ymaxIndex) {
            V3D_LineSegment xd = new V3D_LineSegment(xmaxp, xminp, oom, rm);
            V3D_LineSegment zd = new V3D_LineSegment(zmaxp, zminp, oom, rm);
            BigRational xdl2 = xd.getLength2(oom, rm);
            BigRational zdl2 = zd.getLength2(oom, rm);
            if (xdl2.compareTo(zdl2) == 1) {
                compute(pts, xminp, xmaxp, n, 1, oom, rm);
            } else {
                compute(pts, zminp, zmaxp, n, 1, oom, rm);
            }
        } else if (zminIndex == zmaxIndex) {
            V3D_LineSegment xd = new V3D_LineSegment(xmaxp, xminp, oom, rm);
            V3D_LineSegment yd = new V3D_LineSegment(ymaxp, yminp, oom, rm);
            BigRational xdl2 = xd.getLength2(oom, rm);
            BigRational ydl2 = yd.getLength2(oom, rm);
            if (xdl2.compareTo(ydl2) == 1) {
                compute(pts, xminp, xmaxp, n, 1, oom, rm);
            } else {
                compute(pts, yminp, ymaxp, n, 1, oom, rm);
            }
        } else {
            V3D_LineSegment xd = new V3D_LineSegment(xmaxp, xminp, oom, rm);
            V3D_LineSegment yd = new V3D_LineSegment(ymaxp, yminp, oom, rm);
            V3D_LineSegment zd = new V3D_LineSegment(zmaxp, zminp, oom, rm);
            BigRational xdl2 = xd.getLength2(oom, rm);
            BigRational ydl2 = yd.getLength2(oom, rm);
            BigRational zdl2 = zd.getLength2(oom, rm);
            if (xdl2.compareTo(ydl2) == 1) {
                if (xdl2.compareTo(zdl2) == 1) {
                    compute(pts, xminp, xmaxp, n, 1, oom, rm);
                } else {
                    compute(pts, zminp, zmaxp, n, 1, oom, rm);
                }
            } else {
                if (ydl2.compareTo(zdl2) == 1) {
                    compute(pts, yminp, ymaxp, n, 1, oom, rm);
                } else {
                    compute(pts, zminp, zmaxp, n, 1, oom, rm);
                }
            }
        }

        if (this.points.size() < 3) {
            int debug = 1;
        }

        V3D_Point pt = this.points.get(0);
        edges.put(edges.size(), new V3D_LineSegment(pt, this.points.get(1), oom, rm));
        V3D_Point rt = null;
        for (int i = 1; i < this.points.size() - 1; i++) {
            V3D_Point qt = this.points.get(i);
            rt = this.points.get(i + 1);
            triangles.put(triangles.size(), new V3D_Triangle(pt, qt, rt, oom, rm));
            edges.put(edges.size(), new V3D_LineSegment(qt, rt, oom, rm));
        }
        edges.put(edges.size(), new V3D_LineSegment(rt, pt, oom, rm));

        if (triangles.isEmpty()) {
            int debug = 1;
        }

    }

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param gs The input convex hulls.
     */
    public V3D_ConvexArea(int oom, RoundingMode rm, V3D_ConvexArea... gs) {
        this(oom, rm, gs[0].triangles.get(0).getPl(oom, rm).n,
                V3D_FiniteGeometry.getPoints(gs, oom, rm));
    }

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param ch The convex hull to add to the convex hull with t.
     * @param t The triangle used to set the normal and to add to the convex
     * hull with ch.
     */
    public V3D_ConvexArea(int oom, RoundingMode rm, V3D_ConvexArea ch,
            V3D_Triangle t) {
        this(oom, rm, ch.triangles.get(0).getPl(oom, rm).n,
                V3D_FiniteGeometry.getPoints(oom, rm, ch, t));
    }

    @Override
    public HashMap<Integer, V3D_Point> getPoints(int oom, RoundingMode rm) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V3D_Point[] getPointsArray(int oom, RoundingMode rm) {
        int np = points.size();
        V3D_Point[] re = new V3D_Point[np];
        for (int i = 0; i < np; i++) {
            re[i] = new V3D_Point(points.get(i));
        }
        return re;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A collection of the external edges.
     */
    @Override
    public HashMap<Integer, V3D_LineSegment> getEdges(int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet.");
//        if (edges == null) {
//            edges = new HashMap<>();
//        }
//        return edges;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.getClass().getName()).append("(\n");
        {
            s.append(" points (\n");
            for (var entry : points.entrySet()) {
                s.append("  (");
                s.append(entry.getKey());
                s.append(", ");
                s.append(entry.getValue().toString());
                s.append("),\n");
            }
            int l = s.length();
            s = s.delete(l - 2, l);
            s.append("\n )");
        }
        s.append("\n)");
        return s.toString();
    }

    /**
     * @return Simple string representation.
     */
    public String toStringSimple() {
        return toString();
    }

    /**
     * Check if {@code this} is equal to {@code i}.
     *
     * @param c An instance to compare for equality.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff all the triangles are the same.
     */
    public boolean equals(V3D_ConvexArea c, int oom, RoundingMode rm) {
        HashSet<Integer> indexes = new HashSet<>();
        for (var x : points.values()) {
            boolean found = false;
            for (int i = 0; i < c.points.size(); i++) {
                if (x.equals(c.points.get(i), oom, rm)) {
                    found = true;
                    indexes.add(i);
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        for (int i = 0; i < c.points.size(); i++) {
            if (!indexes.contains(i)) {
                boolean found = false;
                for (var x : points.values()) {
                    if (x.equals(c.points.get(i), oom, rm)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
        }
        return true;
//        /**
//         * The triangularisation of this and i might be different and the number
//         * of points in each might be different, but the areas they define might
//         * be the same. For the areas to be the same each triangle from each
//         * must either be in the other, or it must fully intersect the other.
//         */
//        if (!this.triangles.get(0).pl.equals(i.triangles.get(0).pl, oom, rm)) {
//            // If they are not in the same plane, they are unequal!
//            return false;
//        }
//        for (V3D_Triangle t : triangles) {
//            V3D_Geometry g = i.getIntersect(t, oom, rm);
//            if (g instanceof V3D_Triangle gt) {
//                if (!t.equals(gt, oom, rm)) {
    

    ////                    System.out.println(gt);
////                    System.out.println(t);
////                    t.equals(gt, oom, rm);
//                    return false;
//                }
//            }
//        }
//        for (V3D_Triangle t : i.triangles) {
//            V3D_Geometry g = getIntersect(t, oom, rm);
//            if (g instanceof V3D_Triangle gt) {
//                if (!t.equals(gt, oom, rm)) {
//                    return false;
//                }
//            }
//        }
//        return true;
    }

    @Override
    protected void initPl(int oom, RoundingMode rm) {
        pl = triangles.get(0).getPl(oom, rm);
    }

    @Override
    protected void initPl(V3D_Point pt, int oom, RoundingMode rm) {
        V3D_Triangle t = triangles.get(0);
        pl = new V3D_Plane(pt, offset, t.pv, t.qv, t.rv, oom, rm);
    }

    @Override
    public V3D_AABB getAABB(int oom, RoundingMode rm) {
        if (en == null) {
            en = points.get(0).getAABB(oom, rm);
            for (int i = 1; i < points.size(); i++) {
                en = en.union(points.get(i).getAABB(oom, rm), oom);
            }
        }
        return en;
    }

    /**
     * If this is effectively a triangle, the triangle is returned. If this is
     * effectively a rectangle, the rectangle is returned. Otherwise this is
     * returned.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return Either a triangle, rectangle or this.
     */
    public V3D_FiniteGeometry simplify(int oom, RoundingMode rm) {
        if (isTriangle()) {
            return new V3D_Triangle(points.get(0), points.get(1),
                    points.get(2), oom, rm);
        } else if (isRectangle(oom, rm)) {
            return new V3D_Rectangle(points.get(0), points.get(2),
                    points.get(1), points.get(3), oom, rm);
        } else {
            return this;
        }
    }

    /**
     * @param pt The point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if the point is aligned with any of the parts.
     */
    protected boolean isAligned(V3D_Point pt, int oom, RoundingMode rm) {
        for (V3D_Triangle triangle : triangles.values()) {
            if (triangle.intersects0(pt, oom, rm)) {
                return true;
            }
        }
        return false;
        //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy0(pt, oom)));
    }

    /**
     * This sums all the areas irrespective of any overlaps.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The area of the triangle (rounded).
     */
    @Override
    public BigRational getArea(int oom, RoundingMode rm) {
        BigRational sum = BigRational.ZERO;
        for (var t : triangles.values()) {
            sum = sum.add(t.getArea(oom, rm));
        }
        return sum;
    }

    /**
     * This sums all the perimeters irrespective of any overlaps.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    @Override
    public BigRational getPerimeter(int oom, RoundingMode rm) {
        BigRational sum = BigRational.ZERO;
        for (var t : triangles.values()) {
            sum = sum.add(t.getPerimeter(oom, rm));
        }
        return sum;
    }

    /**
     * Get the intersection between this and the plane {@code p}.
     *
     * @param p The plane to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersect(V3D_Plane p, int oom, RoundingMode rm) {
        if (this.triangles.get(0).getPl(oom, rm).equalsIgnoreOrientation(p, oom, rm)) {
            return this;
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point or line segment.
     */
    public V3D_FiniteGeometry getIntersect(V3D_Line l, int oom,
            RoundingMode rm) {
        V3D_Geometry i = getPl(oom, rm).getIntersect(l, oom, rm);
        if (i == null) {
            return null;
        } else if (i instanceof V3D_Point ip) {
            if (intersects00(ip, oom, rm)) {
                return ip;
            } else {
                return null;
            }
        } else {
            throw new UnsupportedOperationException();
            /**
             * As the area is convex, there is a line segment which is 
             * the intersection. Either the plane of an edge is parallel to the 
             * line or intersects the line at a point. The points of the line 
             * segment are those intersection points that are different and that 
             * are a minimum distance apart (and are on the edge of the area).
             */
//            V3D_FiniteGeometry lpqi = getPQ(oom, rm).getIntersect(l, oom, rm);
//            V3D_FiniteGeometry lqri = getQR(oom, rm).getIntersect(l, oom, rm);
//            V3D_FiniteGeometry lrpi = getRP(oom, rm).getIntersect(l, oom, rm);
//            if (lpqi == null) {
//                if (lqri == null) {
//                    return null; // This should not happen!
//                } else {
//                    if (lrpi == null) {
//                        return lqri;
//                    } else {
//                        return getGeometry(env, ((V3D_Point) lqri).getVector(oom, rm),
//                                ((V3D_Point) lrpi).getVector(oom, rm), oom, rm);
//                    }
//                }
//            } else if (lpqi instanceof V3D_Point lpqip) {
//                if (lqri == null) {
//                    if (lrpi == null) {
//                        return lpqi;
//                    } else {
//                        return getGeometry(env, lpqip.getVector(oom, rm),
//                                ((V3D_Point) lrpi).getVector(oom, rm), oom, rm);
//                    }
//                } else if (lqri instanceof V3D_Point lqrip) {
//                    if (lrpi == null) {
//                        return getGeometry(env, lqrip.getVector(oom, rm),
//                                lpqip.getVector(oom, rm), oom, rm);
//                    } else if (lrpi instanceof V3D_LineSegment) {
//                        return lrpi;
//                    } else {
//                        return getGeometry(lpqip, lqrip, (V3D_Point) lrpi, oom, rm);
//                    }
//                } else {
//                    return lqri;
//                }
//            } else {
//                return lpqi;
//            }
        }
    }

    /**
     * @param r The ray to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point or line segment.
     */
    @Override
    public V3D_FiniteGeometry getIntersect(V3D_Ray r, int oom,
            RoundingMode rm) {
        V3D_Geometry i = getPl(oom, rm).getIntersect(r.l, oom, rm);
        if (i == null) {
            return null;
        } else if (i instanceof V3D_Point ip) {
            if (r.isAligned(ip, oom, rm)
                && intersects00(ip, oom, rm)) {
                return ip;
            } else {
                return null;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
    /**
     * Get the intersection between the geometry and the triangle {@code t}.
     *
     * @param t The triangle to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersect(V3D_Triangle t, int oom, RoundingMode rm) {
        // Create a set all the intersecting triangles from this.
        List<V3D_Point> ts = new ArrayList<>();
        for (V3D_Triangle t2 : triangles.values()) {
            V3D_FiniteGeometry i = t2.getIntersect(t, oom, rm);
            ts.addAll(Arrays.asList(i.getPointsArray(oom, rm)));
        }
        ArrayList<V3D_Point> tsu = V3D_Point.getUnique(ts, oom, rm);
        if (tsu.isEmpty()) {
            return null;
        } else {
            return new V3D_ConvexArea(oom, rm, t.getPl(oom, rm).n,
                    tsu.toArray(V3D_Point[]::new)).simplify(oom, rm);
        }
//        switch (size) {
//            case 0:
//                return null;
//            case 1:
//                return t2s.iterator().next();
//            case 2:
//                Iterator<V3D_Triangle> ite = t2s.iterator();
//                return getGeometry(ite.next(), ite.next(), oom);
//            default:
//                return getGeometry(oom, t2s.toArray(V3D_Triangle[]::new));
    }

    @Override
    public V3D_ConvexArea rotate(V3D_Ray ray, V3D_Vector uv,
            Math_BigDecimal bd, BigRational theta, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V3D_ConvexArea(this);
        } else {
            return rotateN(ray, uv, bd, theta, oom, rm);
        }
    }

    @Override
    public V3D_ConvexArea rotateN(V3D_Ray ray, V3D_Vector uv,
            Math_BigDecimal bd, BigRational theta, int oom, RoundingMode rm) {
        V3D_Triangle[] rts = new V3D_Triangle[triangles.size()];
        for (int i = 0; i < triangles.size(); i++) {
            rts[0] = triangles.get(i).rotate(ray, uv, bd, theta, oom, rm);
        }
        return new V3D_ConvexArea(oom, rm, rts);
    }

    /**
     *
     * @param pts
     * @param p0
     * @param p1
     * @param n
     * @param index
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the number of points added.
     */
    private void compute(ArrayList<V3D_Point> pts, V3D_Point p0, V3D_Point p1,
            V3D_Vector n, int index, int oom, RoundingMode rm) {
        V3D_Plane pl = new V3D_Plane(p0, p1, new V3D_Point(env,
                offset, p0.rel.add(n, oom, rm)), oom, rm);
        AB ab = new AB(pts, p0, p1, pl, oom, rm);
        {
            // Process ab.a
            points.put(points.size(), p0);
            if (!ab.a.isEmpty()) {
                if (ab.a.size() > 1) {
                    V3D_Point apt = ab.a.get(ab.maxaIndex);
                    points.put(index, apt);
                    index++;
                    V3D_Triangle atr = new V3D_Triangle(p0, p1, apt, oom, rm);
                    TreeSet<Integer> removeIndexes = new TreeSet<>();
                    for (int i = 0; i < ab.a.size(); i++) {
                        if (atr.intersects(ab.a.get(i), oom, rm)) {
                            removeIndexes.add(i);
                            //index--;
                        }
                        Iterator<Integer> ite = removeIndexes.descendingIterator();
                        while (ite.hasNext()) {
                            ab.a.remove(ite.next().intValue());
                        }
                        if (!ab.a.isEmpty()) {
                            if (ab.a.size() > 1) {
                                // Divide again
                                V3D_Line l = new V3D_Line(p0, p1, oom, rm);
                                V3D_Point proj = l.getPointOfIntersect(apt, oom, rm);
                                compute(ab.a, proj, apt, n, index, oom, rm);
                            } else {
                                points.put(index, ab.a.get(0));
                                index++;
                            }
                        }
                    }
                } else {
                    points.put(index, ab.a.get(0));
                    index++;
                }
            }
        }
        {
            // Process ab.b
            points.put(this.points.size(), p1);
            index++;
            if (!ab.b.isEmpty()) {
                if (ab.b.size() > 1) {
                    V3D_Point bpt = ab.b.get(ab.maxbIndex);
                    points.put(index, bpt);
                    index++;
                    V3D_Triangle btr = new V3D_Triangle(p0, p1, bpt, oom, rm);
                    TreeSet<Integer> removeIndexes = new TreeSet<>();
                    for (int i = 0; i < ab.b.size(); i++) {
                        if (btr.intersects(ab.b.get(i), oom, rm)) {
                            removeIndexes.add(i);
                            //index--;
                        }
                    }
                    Iterator<Integer> ite = removeIndexes.descendingIterator();
                    while (ite.hasNext()) {
                        ab.b.remove(ite.next().intValue());
                    }
                    if (!ab.b.isEmpty()) {
                        if (ab.b.size() > 1) {
                            // Divide again
                            V3D_Line l = new V3D_Line(p0, p1, oom, rm);
                            V3D_Point proj = l.getPointOfIntersect(bpt, oom, rm);
                            compute(ab.b, bpt, proj, n, index, oom, rm);
                        } else {
                            points.put(index, ab.b.get(0));
                        }
                    }
                } else {
                    points.put(index, ab.b.get(0));
                }
            }
        }
    }

    @Override
    public boolean contains(V3D_Point pt, int oom, RoundingMode rm) {
        if (intersects(pt, oom, rm)) {
            return !getEdges(oom, rm).values().parallelStream().anyMatch(x
                    -> x.intersects(pt, oom, rm));
        }
        return false;
    }

    @Override
    public boolean contains(V3D_LineSegment ls, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean contains(V3D_Area a, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * A class for helping to calculate a convex hull.
     */
    public class AB {

        /**
         * The points that are above the plane.
         */
        ArrayList<V3D_Point> a;

        /**
         * For storing the index of the point in {@link #a} that is the maximum
         * distance from the plane.
         */
        int maxaIndex;

        /**
         * The points that are below the plane.
         */
        ArrayList<V3D_Point> b;

        /**
         * For storing the index of the point in {@link #b} that is the maximum
         * distance from the plane.
         */
        int maxbIndex;

        /**
         * Create a new instance.
         *
         * @param pts The points.
         * @param p0 One end of the line segment dividing the convex hull.
         * @param p1 One end of the line segment dividing the convex hull.
         * @param plane The plane.
         * @param oom The Order of Magnitude for the precision.
         * @param rm The RoundingMode for any rounding.
         */
        public AB(ArrayList<V3D_Point> pts, V3D_Point p0, V3D_Point p1,
                V3D_Plane plane, int oom, RoundingMode rm) {
            // Find points above and below the plane.
            // Points that are not on the plane.
            ArrayList<V3D_Point> no = new ArrayList<>();
            for (int i = 0; i < pts.size(); i++) {
                V3D_Point pt = pts.get(i);
                if (!plane.intersects(pt, oom, rm)) {
                    no.add(pt);
                }
            }
            no = V3D_Point.getUnique(no, oom, rm);

            if (no.size() < 1) {
                int debug = 1;
            }

            // Go through points that are not on the plane.
            a = new ArrayList<>();
            b = new ArrayList<>();
            V3D_Point pt0 = no.get(0);
            BigRational maxads = plane.getDistanceSquared(pt0, oom, rm);
            BigRational maxbds = BigRational.ZERO;
            a.add(pt0);
            maxaIndex = 0;
            maxbIndex = -1;
            for (int i = 1; i < no.size(); i++) {
                V3D_Point pt = no.get(i);
                BigRational ds = plane.getDistanceSquared(pt, oom, rm);
                if (plane.isOnSameSide(pt0, pt, oom, rm)) {
                    a.add(pt);
                    if (ds.compareTo(maxads) == 1) {
                        maxads = ds;
                        maxaIndex = a.size() - 1;
                    }
                } else {
                    b.add(pt);
                    if (ds.compareTo(maxbds) == 1) {
                        maxbds = ds;
                        maxbIndex = b.size() - 1;
                    }
                }
            }
        }
    }

    /**
     * If all {@link #triangles} form a single triangle return true
     *
     * @return {@code true} iff this is a triangle.
     */
    public final boolean isTriangle() {
        return points.size() == 3;
    }

    /**
     * If all {@link #triangles} form a single triangle return true
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff this is a rectangle.
     */
    public boolean isRectangle(int oom, RoundingMode rm) {
        if (points.size() == 4) {
            return V3D_Rectangle.isRectangle(points.get(0),
                    points.get(1), points.get(2), points.get(3), oom, rm);
        }
        return false;
    }

    /**
     * Clips this using the pl and return the part that is on the same side as
     * pl.
     *
     * @param pl The plane that clips.
     * @param p A point that is used to return the side of the clipped triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometry clip(V3D_Plane pl, V3D_Point p, int oom, RoundingMode rm) {
        V3D_FiniteGeometry i = getIntersect(pl, oom, rm);
        if (i == null) {
            V3D_Point pp = this.triangles.get(0).getPl(oom, rm).getP();
            if (pl.isOnSameSide(pp, p, oom, rm)) {
                return this;
            } else {
                return null;
            }
        } else if (i instanceof V3D_Point ip) {
            if (pl.isOnSameSide(ip, p, oom, rm)) {
                V3D_Point pp = this.triangles.get(0).getPl(oom, rm).getP();
                if (pl.isOnSameSide(pp, p, oom, rm)) {
                    return this;
                } else {
                    return ip;
                }
            } else {
                return null;
            }
        } else {
            // i instanceof V3D_LineSegment
            V3D_LineSegment il = (V3D_LineSegment) i;
            ArrayList<V3D_Point> pts = new ArrayList<>();
            for (V3D_Point pt : points.values()) {
                if (pl.isOnSameSide(pt, p, oom, rm)) {
                    pts.add(pt);
                }
            }
            if (pts.isEmpty()) {
                return il;
            } else {
                return new V3D_ConvexArea(oom, rm,
                        this.triangles.get(0).getPl(oom, rm).n,
                        pts.toArray(V3D_Point[]::new));
            }
        }
    }

    /**
     * Clips this using t.
     *
     * @param t The triangle to clip this with.
     * @param pt A point that is used to return the side of this that is
     * clipped.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometry clip(V3D_Triangle t, V3D_Point pt, int oom, RoundingMode rm) {
        V3D_Point tp = t.getP(oom, rm);
        V3D_Point tq = t.getQ(oom, rm);
        V3D_Point tr = t.getR(oom, rm);
        V3D_Vector n = t.getPl(oom, rm).n;
        V3D_Point pp = new V3D_Point(env, tp.offset.add(n, oom, rm), tp.rel);
        V3D_Plane ppl = new V3D_Plane(tp, tq, pp, oom, rm);
        V3D_Point qp = new V3D_Point(env, tq.offset.add(n, oom, rm), tq.rel);
        V3D_Plane qpl = new V3D_Plane(tq, tr, qp, oom, rm);
        V3D_Point rp = new V3D_Point(env, tr.offset.add(n, oom, rm), tr.rel);
        V3D_Plane rpl = new V3D_Plane(tr, tp, rp, oom, rm);
        V3D_FiniteGeometry cppl = clip(ppl, tr, oom, rm);
        if (cppl == null) {
            return null;
        } else if (cppl instanceof V3D_Point) {
            return cppl;
        } else if (cppl instanceof V3D_LineSegment cppll) {
            V3D_FiniteGeometry cppllcqpl = cppll.clip(qpl, pt, oom, rm);
            if (cppllcqpl == null) {
                return null;
            } else if (cppllcqpl instanceof V3D_Point) {
                return cppllcqpl;
            } else {
                return ((V3D_LineSegment) cppllcqpl).clip(rpl, pt, oom, rm);
            }
        } else if (cppl instanceof V3D_Triangle cpplt) {
            V3D_FiniteGeometry cppltcqpl = cpplt.clip(qpl, pt, oom, rm);
            if (cppltcqpl == null) {
                return null;
            } else if (cppltcqpl instanceof V3D_Point) {
                return cppltcqpl;
            } else if (cppltcqpl instanceof V3D_LineSegment cppltcqpll) {
                return cppltcqpll.clip(rpl, pt, oom, rm);
            } else if (cppltcqpl instanceof V3D_Triangle cppltcqplt) {
                return cppltcqplt.clip(rpl, pt, oom, rm);
            } else {
                V3D_ConvexArea c = (V3D_ConvexArea) cppltcqpl;
                return c.clip(rpl, tq, oom, rm);
            }
        } else {
            V3D_ConvexArea c = (V3D_ConvexArea) cppl;
            V3D_FiniteGeometry cc = c.clip(qpl, pt, oom, rm);
            if (cc == null) {
                return cc;
            } else if (cc instanceof V3D_Point) {
                return cc;
            } else if (cc instanceof V3D_LineSegment cppll) {
                V3D_FiniteGeometry cccqpl = cppll.clip(qpl, pt, oom, rm);
                if (cccqpl == null) {
                    return null;
                } else if (cccqpl instanceof V3D_Point) {
                    return cccqpl;
                } else {
                    return ((V3D_LineSegment) cccqpl).clip(rpl, pt, oom, rm);
                }
            } else if (cc instanceof V3D_Triangle ccct) {
                return ccct.clip(rpl, tq, oom, rm);
            } else {
                V3D_ConvexArea ccc = (V3D_ConvexArea) cc;
                return ccc.clip(rpl, pt, oom, rm);
            }
        }
    }

    /**
     * If pts are all equal then a V3D_Point is returned. If two are different,
     * then a V3D_LineSegment is returned. Three different, then a V3D_Triangle
     * is returned. If four or more are different then a V3D_ConvexArea is
     * returned.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param pts The points.
     * @return Either a V3D_Point, V3D_LineSegment, V3D_Triangle, or
     * V3D_ConvexArea.
     */
    public static V3D_FiniteGeometry getGeometry(int oom, RoundingMode rm, V3D_Point... pts) {
        ArrayList<V3D_Point> upts = V3D_Point.getUnique(Arrays.asList(pts), oom, rm);
        Iterator<V3D_Point> i = upts.iterator();
        switch (upts.size()) {
            case 1 -> {
                return i.next();
            }
            case 2 -> {
                return new V3D_LineSegment(i.next(), i.next(), oom, rm);
            }
            case 3 -> {
                return new V3D_Triangle(i.next(), i.next(), i.next(), oom, rm);
            }
            default -> {
                V3D_Point ip = i.next();
                V3D_Point iq = i.next();
                V3D_Point ir = i.next();
                while (V3D_Line.isCollinear(oom, rm, ip, iq, ir) && i.hasNext()) {
                    ir = i.next();
                }
                V3D_Plane pl;
                if (V3D_Line.isCollinear(oom, rm, ip, iq, ir)) {
                    return new V3D_LineSegment(oom, rm, pts);
                } else {
                    pl = new V3D_Plane(ip, iq, ir, oom, rm);
                    return new V3D_ConvexArea(oom, rm, pl.n, pts);
                }
            }
        }
    }

    /**
     * Identify if this is intersected by point {@code p}. This first check if 
     * {@code pt} intersects the Axis Aligned Bounding Box of {@code this},
     * then checks the point is on the plane.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    @Override
    public boolean intersects(V3D_Point pt, int oom, RoundingMode rm) {
        if (getAABB(oom, rm).intersects(pt, oom, rm)) {
            return intersects0(pt, oom, rm);
        } else {
            return false;
        }
    }

    /**
     * Identify if this is intersected by point {@code p}. There is no check to 
     * evaluate if {@code p} intersects the Axis Aligned Bounding Box, but there 
     * is a check that the point intersects the plane.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    //@Override
    public boolean intersects0(V3D_Point pt, int oom, RoundingMode rm) {
        // Check point is on the plane. 
        if (getPl(oom, rm).intersects(pt, oom, rm)) {
            // Check point is in a triangle
            return intersects00(pt, oom, rm);
        } else {
            return false;
        }
    }

    /**
     * Identify if this is intersected by point {@code p}. There is no check to 
     * evaluate if {@code p} intersects the Axis Aligned Bounding Box or if it 
     * intersects the plane.
     *
     * @param p The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code p}.
     */
    //@Override
    public boolean intersects00(V3D_Point p, int oom, RoundingMode rm) {
        return triangles.values().parallelStream().anyMatch(x
                -> x.intersects00(p, oom, rm));
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param aabb The Axis Aligned Bounding Box to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    @Override
    public boolean intersects(V3D_AABB aabb, int oom, RoundingMode rm) {
        if (getAABB(oom, rm).intersects(aabb, oom)) {
            return triangles.values().parallelStream().anyMatch(x
                    -> x.intersects(aabb, oom, rm));
        }
        return false;
    }

    /**
     * Identify if this is intersected by line {@code l}.
     *
     * @param l The line to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code l}.
     */
    @Override
    public boolean intersects(V3D_Line l, int oom, RoundingMode rm) {
        return l.intersects(getAABB(oom, rm), oom, rm)
                && intersects0(l, oom, rm);
    }

    /**
     * Identify if this is intersected by line {@code l}.
     *
     * @param l The line to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code p}.
     */
    public boolean intersects0(V3D_Line l, int oom, RoundingMode rm) {
        return triangles.values().parallelStream().anyMatch(x
                -> x.intersects(l, oom, rm));
    }

    /**
     * Identify if this is intersected by line segment {@code l}.
     *
     * @param l The line segment to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code l}.
     */
    @Override
    public boolean intersects(V3D_LineSegment l, int oom, RoundingMode rm) {
        return l.intersects(getAABB(oom, rm), oom, rm)
                && intersects0(l, oom, rm);
    }

    /**
     * Identify if this is intersected by line segment {@code l}.
     *
     * @param l The line segment to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code p}.
     */
    public boolean intersects0(V3D_LineSegment l, int oom, RoundingMode rm) {
        return triangles.values().parallelStream().anyMatch(x
                -> x.intersects(l, oom, rm));
    }

    /**
     * If no point aligns, then returns false, otherwise the intersection is 
     * computed, so if that is needed use:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.V3D_Ray, int, java.math.RoundingMode)}
     *
     * @param r The ray to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersects(V3D_Ray r, int oom, RoundingMode rm) {
        if (getPoints(oom, rm).values().parallelStream().anyMatch(x
            -> r.isAligned(x, oom, rm))) {
            return getIntersect(r, oom, rm) != null;
        } else {
            return false;
        }
    }
    
    /**
     * Identify if this is intersected by triangle {@code t}.
     *
     * @param t The triangle to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code t}.
     */
    public boolean intersects(V3D_Triangle t, int oom, RoundingMode rm) {
        return t.intersects(getAABB(oom, rm), oom, rm)
                && intersects0(t, oom, rm);
    }

    /**
     * Identify if this is intersected by triangle {@code t}.
     *
     * @param t The triangle to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code t}.
     */
    public boolean intersects0(V3D_Triangle t, int oom, RoundingMode rm) {
        return triangles.values().parallelStream().anyMatch(x
                -> x.intersects(t, oom, rm));
    }

    /**
     * Identify if this is intersected by rectangle {@code r}.
     *
     * @param r The rectangle to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code r}.
     */
    public boolean intersects(V3D_Rectangle r, int oom, RoundingMode rm) {
        return r.intersects(getAABB(oom, rm), oom, rm)
                && intersects0(r, oom, rm);
    }

    /**
     * Identify if this is intersected by rectangle {@code r}.
     *
     * @param r The rectangle to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code r}.
     */
    public boolean intersects0(V3D_Rectangle r, int oom, RoundingMode rm) {
        return triangles.values().parallelStream().anyMatch(x
                -> r.intersects(x, oom, rm));
    }

    /**
     * Identify if this is intersected by convex hull {@code ch}.
     *
     * @param ch The convex hull to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code ch}.
     */
    public boolean intersects(V3D_ConvexArea ch, int oom, RoundingMode rm) {
        return ch.intersects(getAABB(oom, rm), oom, rm)
                && intersects(ch.getAABB(oom, rm), oom, rm)
                && intersects0(ch, oom, rm);
    }

    /**
     * Identify if this is intersected by convex hull {@code ch}.
     *
     * @param ch The convex hull to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code ch.
     */
    public boolean intersects0(V3D_ConvexArea ch, int oom, RoundingMode rm) {
        return triangles.values().parallelStream().anyMatch(x
                -> ch.intersects0(x, oom, rm))
                || ch.triangles.values().parallelStream().anyMatch(x
                        -> intersects0(x, oom, rm));
    }
    
    /**
     * Get the minimum distance squared to {@code pt}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared to {@code p}.
     */
    public BigRational getDistanceSquared(V3D_Point pt, int oom, RoundingMode rm) {
        Iterator<V3D_Triangle> ite = triangles.values().iterator();
        BigRational r = ite.next().getDistanceSquared(pt, oom, rm);
        while(ite.hasNext()) {
            r = BigRational.min(r, ite.next().getDistanceSquared(pt, oom, rm));
        }
        return r;
    }

}
