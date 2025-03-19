/*
 * Copyright 2025 Andy Turner, University of Leeds.
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
import java.util.Iterator;
import java.util.List;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;

/**
 * For representing a convex hulls - convex shapes with no holes.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V3D_ConvexVolume extends V3D_Volume {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the contiguous triangles.
     */
    public ArrayList<V3D_Triangle> triangles;

    public V3D_ConvexVolume(V3D_ConvexVolume ch) {
        super(ch.env, ch.offset);
        //ch.faces
                
    }
    
    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param triangles A non-empty list of coplanar triangles.
     */
    public V3D_ConvexVolume(int oom, RoundingMode rm, V3D_Triangle... triangles) {
        this(oom, rm, V3D_Triangle.getPoints(triangles, oom, rm));
    }

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points A list of points with at least 3 that are non-coplanar.
     */
    public V3D_ConvexVolume(int oom, RoundingMode rm, V3D_Point... points) {
        this(oom, rm, Arrays.asList(points));
    }

    /**
     * Create a new instance. An algorithm for generating a convex hull from a
     * set of coplanar points known as the "quick hull" algorithm (see
     * <a href="https://en.wikipedia.org/wiki/Quickhull">
     * https://en.wikipedia.org/wiki/Quickhull</a>) :
     * <ol>
     * <li>Partition the points:
     * <ul>
     * <li>Calculate the distances between the points with the minimum and
     * maximum x, the minimum and maximum y, and the minimum and maximum z
     * values.</li>
     * <li>Choose the points that have the largest distance between them to
     * define the dividing plane that is orthogonal to the plane of the
     * polygon.</li>
     * <li>Let the points on one side of the dividing plane be one group and
     * those on the other be the another group.</li>
     * </ul></li>
     * <li>Add the two end points of the partition to the convex hull.</li>
     * <li>Deal with each group of points in turn.</li>
     * <li>If there is only one other point on a side of the partition then add
     * it to the convex hull.</li>
     * <li>If there are more than one, then find the one with the biggest
     * distance from the partition and add this to the convex hull.</li>
     * <li>We can now ignore all the other points that intersect the triangle
     * given by the 3 points now in the convex hull.</li>
     * <li>Create a new plane dividing the remaining points on this side of the
     * first dividing plane. Two points on the plane are the last point added to
     * the convex hull and the closest point on the line defined by the other
     * two points in the convex hull. The new dividing plane is orthogonal to
     * the first dividing plane.</li>
     * <li>Let the points in this group that are on one side of the dividing
     * plane be another group and those on the other be the another group.</li>
     * <li>Repeat the process dealing with each group in turn (Steps 3 to 9) in
     * a depth first manner.</li>
     * </ol>
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points The points from which to construct the convex hull. There
     * must be at least three non-linear points.
     */
    public V3D_ConvexVolume(int oom, RoundingMode rm, List<V3D_Point> points) {
        super(points.get(0).env, V3D_Vector.ZERO);
//        ArrayList<V3D_Point> h = new ArrayList<>();
//        ArrayList<V3D_Point> uniquePoints = V3D_Point.getUnique(
//                points, oom, rm);
//        //uniquePoints.sort(V3D_Point::compareTo);
//        uniquePoints.sort((p1, p2) -> p1.compareTo(p2, oom, rm));
//        // Compute convex hull
//        // https://rosettacode.org/wiki/Convex_hull#Java
//        // lower hull
//        for (V3D_Point pt : uniquePoints) {
//            while (h.size() >= 2 && !ccw(h.get(h.size() - 2),
//                    h.get(h.size() - 1), pt, oom, rm)) {
//                h.remove(h.size() - 1);
//            }
//            h.add(pt);
//        }
//        // upper hull
//        int t = h.size() + 1;
//        for (int i = uniquePoints.size() - 1; i >= 0; i--) {
//            V3D_Point pt = uniquePoints.get(i);
//            while (h.size() >= t && !ccw(h.get(h.size() - 2), h.get(h.size() - 1), pt, oom, rm)) {
//                h.remove(h.size() - 1);
//            }
//            h.add(pt);
//        }
//        ArrayList<V3D_Point> ups = V3D_Point.getUnique(h, oom, rm);
//        this.points = new HashMap<>();
//        for (var p : ups) {
//            this.points.put(this.points.size(), p);
//        }
//        // Add face
//        faces = new HashMap<>();
//        V3D_Point p0 = this.points.get(0);
//        for (int i = 1; i < this.points.size(); i++) {
//            V3D_Point p1 = this.points.get(i);
//            //faces.put(faces.size(), new V3D_LineSegment(p0, p1, oom, rm));
//            p0 = p1;
//        }
//        faces.put(faces.size(), new V3D_LineSegment(p0, this.points.get(0), oom, rm));
    }

    // ccw returns true if the three points make a counter-clockwise turn
    private static boolean ccw(V3D_Point a, V3D_Point b, V3D_Point c, int oom,
            RoundingMode rm) {
        BigRational ax = a.getX(oom, rm);
        BigRational ay = a.getY(oom, rm);
        return ((b.getX(oom, rm).subtract(ax))
                .multiply(c.getY(oom, rm).subtract(ay)))
                .compareTo((b.getY(oom, rm).subtract(ay))
                        .multiply(c.getX(oom, rm).subtract(ax))) == 1;
    }

    /**
     * Create a new instance.
     *
     * @param ch The input convex hull.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_ConvexVolume(V3D_ConvexVolume ch, int oom, RoundingMode rm) {
        this(oom, rm, ch.getPointsArray(oom, rm));
    }

    /**
     * Create a new instance.
     *
     * @param ch The convex hull to add to the convex hull with t.
     * @param t The triangle to add to the convex hull with ch.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_ConvexVolume(V3D_ConvexVolume ch, V3D_Triangle t,
            int oom, RoundingMode rm) {
        this(oom, rm, V3D_FiniteGeometry.getPoints(oom, rm, ch, t));
    }

    @Override
    public V3D_Point[] getPointsArray(int oom, RoundingMode rm) {
        int np = points.size();
        V3D_Point[] pts = new V3D_Point[np];
        for (int i = 0; i < np; i++) {
            pts[i] = new V3D_Point(points.get(i));
        }
        return pts;
    }

    @Override
    public HashMap<Integer, V3D_Point> getPoints(int oom, RoundingMode rm) {
        return points;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.getClass().getName()).append("(");
        {
            s.append("\npoints (\n");
            for (var entry : points.entrySet()) {
                s.append("(");
                s.append(entry.getKey());
                s.append(",");
                s.append(entry.getValue().toString());
                s.append("), ");
            }
            int l = s.length();
            s = s.delete(l - 2, l);
            s.append("\n)\n");
        }
        s.append("\n)");
        return s.toString();
    }

    /**
     * @return Simple string representation.
     */
    public String toStringSimple() {
        StringBuilder s = new StringBuilder();
        s.append(this.getClass().getName()).append("(");
        {
            s.append("points (");
            for (var entry : points.entrySet()) {
                s.append("(");
                s.append(entry.getKey());
                s.append(",");
                s.append(entry.getValue().toString());
                s.append("), ");
            }
            int l = s.length();
            s = s.delete(l - 2, l);
            s.append(")");
        }
        s.append(")");
        return s.toString();
    }

    /**
     * Check if {@code this} is equal to {@code i}.
     *
     * @param c An instance to compare for equality.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff all the triangles are the same.
     */
    public boolean equals(V3D_ConvexVolume c, int oom, RoundingMode rm) {
//        if (points.values().parallelStream().allMatch(x
//                -> x.equalsAny(c.points.values(), oom, rm))) {
//            return c.points.values().parallelStream().allMatch(x
//                    -> x.equalsAny(points.values(), oom, rm));
//        }
        return false;
//        HashSet<Integer> indexes = new HashSet<>();
//        for (var x : points.values()) {
//            boolean found = false;
//            for (int i = 0; i < c.points.size(); i++) {
//                if (x.equals(c.points.get(i), oom, rm)) {
//                    found = true;
//                    indexes.add(i);
//                    break;
//                }
//            }
//            if (!found) {
//                return false;
//            }
//        }
//        for (int i = 0; i < c.points.size(); i++) {
//            if (!indexes.contains(i)) {
//                boolean found = false;
//                for (var x : points.values()) {
//                    if (x.equals(c.points.get(i), oom, rm)) {
//                        found = true;
//                        break;
//                    }
//                }
//                if (!found) {
//                    return false;
//                }
//            }
//        }
//        return true;
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
//        if (isTriangle()) {
//            return new V3D_Triangle(points.get(0), points.get(1),
//                    points.get(2), oom, rm);
//        } else if (isRectangle(oom, rm)) {
//            return new V3D_Rectangle(points.get(0), points.get(2),
//                    points.get(1), points.get(3), oom, rm);
//        } else {
//            return this;
//        }
        throw new UnsupportedOperationException();
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code p}.
     */
    public boolean intersects(V3D_Point pt, int oom, RoundingMode rm) {
        return getAABB(oom, rm).contains(pt, oom)
                && intersects0(pt, oom, rm);
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code p}.
     */
    public boolean intersects0(V3D_Point pt, int oom, RoundingMode rm) {
        return getTriangles(oom, rm).parallelStream().anyMatch(x
                -> x.intersects(pt, oom, rm));
    }

    /**
     * Identify if {@code this} contains {@code p}.
     *
     * @param pt The point to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} contains {@code p}.
     */
    public boolean contains(V3D_Point pt, int oom, RoundingMode rm) {
//        return intersects(pt, oom, rm)
//                && !V3D_LineSegment.intersects(oom, rm, pt, edges.values());
        throw new UnsupportedOperationException();
    }

    /**
     * Identify if {@code this} contains {@code l}.
     *
     * @param l The line segment to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} contains {@code l}.
     */
    public boolean contains(V3D_LineSegment l, int oom, RoundingMode rm) {
//        return intersects(l, oom, rm)
//                && !V3D_LineSegment.intersects(oom, rm, l, edges.values());
        throw new UnsupportedOperationException();
    }

    /**
     * Identify if {@code this} contains {@code t}.
     *
     * @param t The triangle to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} contains {@code t}.
     */
    public boolean contains(V3D_Triangle t, int oom, RoundingMode rm) {
        return intersects(t, oom, rm)
                && t.getPoints(oom, rm).values().parallelStream().allMatch(x
                        -> contains(x, oom, rm));
//        return intersects(t, oom, rm)
//                && !t.getEdges(oom, rm).values().parallelStream().anyMatch(x
//                        -> V3D_LineSegment.intersects(oom, rm, x,
//                        edges.values()));
    }

    /**
     * Identify if {@code this} contains {@code t}.
     *
     * @param r The triangle to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} contains {@code t}.
     */
    public boolean contains(V3D_Rectangle r, int oom, RoundingMode rm) {
        return intersects(r, oom, rm)
                && r.getPoints(oom, rm).values().parallelStream().allMatch(x
                        -> contains(x, oom, rm));
    }
    
    /**
     * Identify if this contains the convex hull.
     *
     * @param ch The convex hull to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} contains {@code ch}.
     */
    public boolean contains(V3D_ConvexVolume ch, int oom, RoundingMode rm) {
        return intersects(ch, oom, rm)
                && ch.getPoints(oom, rm).values().parallelStream().allMatch(x
                        -> contains(x, oom, rm));
//        return intersects(ch, oom, rm)
//                && !ch.edges.values().parallelStream().anyMatch(x
//                        -> V3D_LineSegment.intersects(oom, rm, x,
//                        edges.values()));
    }

    /**
     * Identify if this is intersected by line segment {@code l}.
     *
     * @param l The line segment to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code l}.
     */
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
//        return getTriangles(oom, rm).parallelStream().anyMatch(x
//                -> x.intersects(l, oom, rm));
        throw new UnsupportedOperationException();
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
        return getTriangles(oom, rm).parallelStream().anyMatch(x
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
//        return getTriangles(oom, rm).parallelStream().anyMatch(x
//                -> r.intersects(x, oom, rm));
        throw new UnsupportedOperationException();
    }

    /**
     * Identify if this is intersected by convex hull {@code ch}.
     *
     * @param ch The convex hull to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code ch}.
     */
    public boolean intersects(V3D_ConvexVolume ch, int oom, RoundingMode rm) {
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
    public boolean intersects0(V3D_ConvexVolume ch, int oom, RoundingMode rm) {
        return getTriangles(oom, rm).parallelStream().anyMatch(x
                -> ch.intersects(x, oom, rm))
                || ch.getTriangles(oom, rm).parallelStream().anyMatch(x
                        -> intersects(x, oom, rm));
    }

    /**
     * This sums all the areas irrespective of any overlaps.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The area of the triangle (rounded).
     */
    public BigRational getArea(int oom, RoundingMode rm) {
        BigRational sum = BigRational.ZERO;
        for (var t : getTriangles(oom, rm)) {
            sum = sum.add(t.getArea(oom, rm));
        }
        return sum;
    }

//    /**
//     * This sums all the perimeters irrespective of any overlaps.
//     *
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode for any rounding.
//     */
//    public BigRational getPerimeter(int oom, RoundingMode rm) {
//        BigRational sum = BigRational.ZERO;
//        for (var t : triangles) {
//            sum = sum.add(t.getPerimeter(oom, rm));
//        }
//        return sum;
//    }
    /**
     * Get the intersection between {@code this} and the triangle {@code t}.
     *
     * @param t The triangle to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersect(V3D_Triangle t, int oom,
            RoundingMode rm) {
        // Create a set all the intersecting triangles from this.
        List<V3D_Point> ts = new ArrayList<>();
        for (V3D_Triangle t2 : triangles) {
            V3D_FiniteGeometry i = t2.getIntersect(t, oom, rm);
            ts.addAll(Arrays.asList(i.getPointsArray(oom, rm)));
        }
        ArrayList<V3D_Point> tsu = V3D_Point.getUnique(ts, oom, rm);
        if (tsu.isEmpty()) {
            return null;
        } else {
            return new V3D_ConvexVolume(oom, rm,
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
    public V3D_ConvexVolume rotate(V3D_Ray ray, V3D_Vector uv, 
            Math_BigDecimal bd, BigRational theta, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V3D_ConvexVolume(this);
        } else {
            return rotateN(ray, uv, bd, theta, oom, rm);
        }
    }
    
    @Override
    public V3D_ConvexVolume rotateN(V3D_Ray ray, V3D_Vector uv, 
            Math_BigDecimal bd, BigRational theta, int oom, RoundingMode rm) {
        V3D_Triangle[] rts = new V3D_Triangle[triangles.size()];
        for (int i = 0; i < triangles.size(); i++) {
            rts[0] = triangles.get(i).rotate(ray, uv, bd, theta, oom, rm);
        }
        return new V3D_ConvexVolume(oom, rm, rts);
    }

    /**
     * Identify if {@code this} is intersected by {@code aabb}.
     * 
     * @param aabb The envelope to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is intersected by {@code aabb}.
     */
    public boolean intersects(V3D_AABB aabb, int oom, RoundingMode rm) {
        return getAABB(oom, rm).intersects(aabb, oom)
                && intersects0(aabb, oom, rm);
    }

    /**
     * For evaluating if {@code this} is intersected by the Axis Aligned 
     * Bounding Box aabb.
     *
     * @param aabb The Axis Aligned Bounding Box to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff the geometry intersects aabb at the given 
     * precision.
     */
    public boolean intersects0(V3D_AABB aabb, int oom, RoundingMode rm) {
        return getTriangles(oom, rm).parallelStream().anyMatch(x
                -> x.intersects(aabb, oom, rm));
    }

    /**
     * If pts are all equal then a V3D_Point is returned. If two are different,
     * then a V3D_LineSegment is returned. Three different, then a V3D_Triangle
     * is returned. If four or more are different then a V3D_ConvexHullCoplanar
     * is returned.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param pts The points.
     * @return Either a V3D_Point, V3D_LineSegment, V3D_Triangle, or
     * V3D_ConvexHullCoplanar.
     */
    public static V3D_FiniteGeometry getGeometry(int oom, RoundingMode rm,
            ArrayList<V3D_Point> pts) {
        return getGeometry(oom, rm, pts.toArray(V3D_Point[]::new));
    }

    /**
     * If pts are all equal then a V3D_Point is returned.If two are different,
     * then a V3D_LineSegment is returned.Three different, then a V3D_Triangle
 is returned. If four or more are different then a V3D_ConvexHull is
 returned.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param pts The points.
     * @return Either a V3D_Point, V3D_LineSegment, V3D_Triangle, or
 V3D_ConvexVolume.
     */
    public static V3D_FiniteGeometry getGeometry(int oom, RoundingMode rm,
            V3D_Point... pts) {
        ArrayList<V3D_Point> upts = V3D_Point.getUnique(
                Arrays.asList(pts), oom, rm);
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
                if (V3D_Line.isCollinear(oom, rm, ip, iq, ir)) {
                    return new V3D_LineSegment(oom, rm, pts);
                } else {
                    return new V3D_ConvexVolume(oom, rm, pts);
                }
            }


        }
    }

    /**
     * Returns the contiguous triangles constructing them first if necessary.
     *
     * This implementation does not compute a centroid and alternates between
     * clockwise and anticlockwise to fill the space with triangles.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A list of triangles that make up the convex hull.
     */
    public ArrayList<V3D_Triangle> getTriangles(int oom, RoundingMode rm) {
        if (triangles == null) {
            triangles = new ArrayList<>();
            V3D_Point[] ps = getPointsArray(oom, rm);
            V3D_Point p0 = ps[0];
            V3D_Point p1 = ps[1];
            for (int i = 2; i < ps.length; i++) {
                V3D_Point p2 = ps[i];
                triangles.add(new V3D_Triangle(p0, p1, p2, oom, rm));
                p1 = p2;
            }
        }
        return triangles;
    }

    /**
     * Returns the edges constructing them first if necessary.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A list of triangles that make up the convex hull.
     */
    @Override
    public HashMap<Integer, V3D_Area> getFaces(int oom, RoundingMode rm) {
//        if (faces == null) {
//            faces = new HashMap<>();
//            V3D_Point p0 = this.points.get(0);
//            V3D_Point p1 = this.points.get(1);
//            this.faces.put(this.faces.size(), new V3D_LineSegment(p0, p1,
//                    oom, rm));
//            for (int i = 2; i < this.points.size(); i++) {
//                p0 = p1;
//                p1 = this.points.get(i);
//                this.faces.put(this.faces.size(), new V3D_LineSegment(p0, p1,
//                        oom, rm));
//            }
//            faces.put(this.faces.size(), new V3D_LineSegment(p1,
//                    this.points.get(0), oom, rm));
//        }
//        return faces;
        throw new UnsupportedOperationException();
    }
    
    @Override
    public BigRational getVolume(int oom, RoundingMode rm){
        throw new UnsupportedOperationException();
    }
}
