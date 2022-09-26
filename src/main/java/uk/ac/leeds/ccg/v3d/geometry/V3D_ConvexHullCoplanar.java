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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.TreeSet;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
//import java.util.ArrayList;

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
public class V3D_ConvexHullCoplanar extends V3D_FiniteGeometry 
        implements V3D_Face {

    private static final long serialVersionUID = 1L;

    /**
     * The collection of triangles.
     */
    protected final ArrayList<V3D_Triangle> triangles;

    /**
     * The collection of points.
     */
    protected final ArrayList<V3D_Point> points;

    /**
     * Create a new instance.
     *
     * @param triangles A non-empty list of coplanar triangles.
     */
    public V3D_ConvexHullCoplanar(V3D_Triangle... triangles) {
        this(triangles[0].p.getN(triangles[0].e.oom), V3D_Triangle.getPoints(triangles));
    }

    /**
     * Create a new instance.
     *
     * @param points A non-empty list of points in a plane given by n.
     */
    public V3D_ConvexHullCoplanar(V3D_Vector n, V3D_Point... points) {
        super(points[0].e);
        this.points = new ArrayList<>();
        this.triangles = new ArrayList<>();
        ArrayList<V3D_Point> pts = new ArrayList<>();
        pts.addAll(Arrays.asList(points));
        V3D_Vector v0 = pts.get(0).rel;
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
            V3D_LineSegment yd = new V3D_LineSegment(ymaxp, yminp);
            V3D_LineSegment zd = new V3D_LineSegment(zmaxp, zminp);
            Math_BigRational ydl2 = yd.getLength2(e.oom);
            Math_BigRational zdl2 = zd.getLength2(e.oom);
            if (ydl2.compareTo(zdl2) == 1) {
                this.points.add(yminp);
                this.points.add(ymaxp);
                getConvexHull0(pts, yminp, ymaxp, n, 1);
            } else {
                this.points.add(zminp);
                this.points.add(zmaxp);
                getConvexHull0(pts, zminp, zmaxp, n, 1);
            }
        } else if (yminIndex == ymaxIndex) {
            V3D_LineSegment xd = new V3D_LineSegment(xmaxp, xminp);
            V3D_LineSegment zd = new V3D_LineSegment(zmaxp, zminp);
            Math_BigRational xdl2 = xd.getLength2(e.oom);
            Math_BigRational zdl2 = zd.getLength2(e.oom);
            if (xdl2.compareTo(zdl2) == 1) {
                this.points.add(xminp);
                this.points.add(xmaxp);
                getConvexHull0(pts, xminp, xmaxp, n, 1);
            } else {
                this.points.add(zminp);
                this.points.add(zmaxp);
                getConvexHull0(pts, zminp, zmaxp, n, 1);
            }
        } else if (zminIndex == zmaxIndex) {
            V3D_LineSegment xd = new V3D_LineSegment(xmaxp, xminp);
            V3D_LineSegment yd = new V3D_LineSegment(ymaxp, yminp);
            Math_BigRational xdl2 = xd.getLength2(e.oom);
            Math_BigRational ydl2 = yd.getLength2(e.oom);
            if (xdl2.compareTo(ydl2) == 1) {
                this.points.add(xminp);
                this.points.add(xmaxp);
                getConvexHull0(pts, xminp, xmaxp, n, 1);
            } else {
                this.points.add(yminp);
                this.points.add(ymaxp);
                
                try {
                getConvexHull0(pts, yminp, ymaxp, n, 1);
                } catch (Exception e) {
                    getConvexHull0(pts, yminp, ymaxp, n, 1);
                }
            }
        } else {
            V3D_LineSegment xd = new V3D_LineSegment(xmaxp, xminp);
            V3D_LineSegment yd = new V3D_LineSegment(ymaxp, yminp);
            V3D_LineSegment zd = new V3D_LineSegment(zmaxp, zminp);
            Math_BigRational xdl2 = xd.getLength2(e.oom);
            Math_BigRational ydl2 = yd.getLength2(e.oom);
            Math_BigRational zdl2 = zd.getLength2(e.oom);
            if (xdl2.compareTo(ydl2) == 1) {
                if (xdl2.compareTo(zdl2) == 1) {
                    this.points.add(xminp);
                    this.points.add(xmaxp);
                    getConvexHull0(pts, xminp, xmaxp, n, 1);
                } else {
                    this.points.add(zminp);
                    this.points.add(zmaxp);
                    getConvexHull0(pts, zminp, zmaxp, n, 1);
                }
            } else {
                if (ydl2.compareTo(zdl2) == 1) {
                    this.points.add(yminp);
                    this.points.add(ymaxp);
                    getConvexHull0(pts, yminp, ymaxp, n, 1);
                } else {
                    this.points.add(zminp);
                    this.points.add(zmaxp);
                    getConvexHull0(pts, zminp, zmaxp, n, 1);
                }
            }
        }
        V3D_Point pt = this.points.get(0);
        for (int i = 1; i < this.points.size() - 2; i++) {
            V3D_Point qt = this.points.get(i);
            V3D_Point rt = this.points.get(i + 1);
            triangles.add(new V3D_Triangle(pt, qt, rt));
        }
    }

    /**
     * Create a new instance.
     */
    public V3D_ConvexHullCoplanar(V3D_ConvexHullCoplanar... gs) {
        this(gs[0].triangles.get(0).p.getN(gs[0].e.oom), V3D_FiniteGeometry.getPoints(gs));
    }

    /**
     * Create a new instance.
     */
    public V3D_ConvexHullCoplanar(V3D_ConvexHullCoplanar ch, V3D_Triangle t) {
        this(ch.triangles.get(0).p.getN(ch.e.oom), V3D_FiniteGeometry.getPoints(ch, t));
    }

    @Override
    public V3D_Point[] getPoints() {
        int np = points.size();
        V3D_Point[] re = new V3D_Point[np];
        for (int i = 0; i < np; i++) {
            re[i] = new V3D_Point(points.get(i));
        }
        return re;
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        Iterator<V3D_Point> ite = points.iterator();
        s += ite.next().toString();
        while (ite.hasNext()) {
            s += ", " + ite.next();
        }
        s += ")";
        return s;
    }

    @Override
    public boolean equals(Object o) {
        // Think about the case when there is a single triangle and whether this is equal to that triangle...
        if (o instanceof V3D_ConvexHullCoplanar i) {
            return equals(i);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.points);
        return hash;
    }

    /**
     * Check if {@code this} is equal to {@code i}.
     *
     * @param i An instance to compare for equality.
     * @param b To distinguish the method from
     * {@link #equals(uk.ac.leeds.ccg.v3d.geometry.V3D_Plane)}.
     * @return {@code true} iff all the triangles are the same.
     */
    public boolean equals(V3D_ConvexHullCoplanar i, boolean b) {
        /**
         * The triangularisation of this and i might be different and the number
         * of points in each might be different, but the areas they define might
         * be the same. For the areas to be the same each triangle from each
         * must either be in the other, or it must fully intersect the other.
         */
        if (!this.triangles.get(0).p.equals(i.triangles.get(0).p)) {
            // If they are not in the same plane, they are unequal!
            return false;
        }
        for (V3D_Triangle t : triangles) {
            V3D_Geometry g = i.getIntersection(t, e.oom);
            if (g instanceof V3D_Triangle gt) {
                if (!t.equals(gt)) {
//                    System.out.println(gt);
//                    System.out.println(t);
//                    t.equals(gt);
                    return false;
                }
            }
        }
        for (V3D_Triangle t : i.triangles) {
            V3D_Geometry g = getIntersection(t, e.oom);
            if (g instanceof V3D_Triangle gt) {
                if (!t.equals(gt)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
            en = points.get(0).getEnvelope();
            for (int i = 1; i < points.size(); i++) {
                en = en.union(points.get(i).getEnvelope());
            }
        }
        return en;
    }

    /**
     * If this is effectively a triangle, the triangle is returned. If this is
     * effectively a rectangle, the rectangle is returned. Otherwise this is
     * returned.
     *
     * @return Either a triangle, rectangle or this.
     */
    public V3D_FiniteGeometry simplify() {
        if (isTriangle()) {
            return new V3D_Triangle(points.get(0), points.get(1),
                    points.get(2));
        } else if (isRectangle()) {
            return new V3D_Rectangle(points.get(0), points.get(2),
                    points.get(1), points.get(3));
        } else {
            return this;
        }
    }

    /**
     * @param pt The point to intersect with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return A point or line segment.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom) {
        if (getEnvelope().isIntersectedBy(pt, oom)) {
            if (triangles.get(0).p.isIntersectedBy(pt, oom)) {
                return isIntersectedBy0(pt, oom);
            }
        }
        return false;
    }

    /**
     * @param pt The point.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} if this intersects with {@code pt}.
     */
    protected boolean isIntersectedBy0(V3D_Point pt, int oom) {
        for (V3D_Triangle triangle : triangles) {
            if (triangle.isIntersectedBy0(pt, oom)) {
                return true;
            }
        }
        return false;
        //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy0(pt, oom)));
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        if (triangles.get(0).p.isIntersectedBy(l, oom)) {
            //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy(l, oom)));        
            return triangles.stream().anyMatch(t -> (t.isIntersectedBy(l, oom)));
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom) {
        if (getEnvelope().isIntersectedBy(l.getEnvelope())) {
            if (triangles.get(0).p.isIntersectedBy(l, oom)) {
                for (V3D_Triangle triangle : triangles) {
                    if (triangle.isIntersectedBy(l, oom)) {
                        return true;
                    }
                }
                return false;
                //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy(l, oom, b)));
            }
        }
        return false;
    }

    /**
     * This sums all the areas irrespective of any overlaps.
     *
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The area of the triangle (rounded).
     */
    @Override
    public BigDecimal getArea(int oom) {
        BigDecimal sum = BigDecimal.ZERO;
        for (var t : triangles) {
            sum = sum.add(t.getArea(oom));
        }
        return sum;
    }

    /**
     * This sums all the perimeters irrespective of any overlaps.
     *
     * @param oom The Order of Magnitude for the precision of the calculation.
     */
    @Override
    public BigDecimal getPerimeter(int oom) {
        BigDecimal sum = BigDecimal.ZERO;
        for (var t : triangles) {
            sum = sum.add(t.getPerimeter(oom));
        }
        return sum;
    }

    /**
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return A point or line segment.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Triangle t, int oom) {
        // Create a set all the intersecting triangles from this.
        HashSet<V3D_Point> ts = new HashSet<>();
        for (V3D_Triangle t2 : triangles) {
            V3D_FiniteGeometry i = t2.getIntersection(t, oom);
            ts.addAll(Arrays.asList(i.getPoints()));
        }
        if (ts.size() == 0) {
            return null;
        } else {
            return new V3D_ConvexHullCoplanar(t.p.getN(oom), ts.toArray(V3D_Point[]::new)).simplify();
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

//    @Override
//    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
//        return getEnvelope().isIntersectedBy(l, oom);
//    }
    /**
     * For this to work as expected, all triangles should have the same offset
     * (point for the rotation).
     *
     * @param axisOfRotation The axis of rotation.
     * @param theta The angle in radians.
     */
    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta) {
        for (V3D_Triangle t : triangles) {
            t.rotate(axisOfRotation, theta);
        }
    }

    /**
     *
     * @param pts
     * @param p0
     * @param p1
     * @param n
     * @param index
     * @return the number of points added.
     */
    public final void getConvexHull0(ArrayList<V3D_Point> pts, V3D_Point p0,
            V3D_Point p1, V3D_Vector n, int index) {
        V3D_Plane pl = new V3D_Plane(p0, p1, new V3D_Point(e,
                offset, p0.rel.add(n, e.oom)));
        AboveAndBelow ab = new AboveAndBelow(pts, pl);
        // Process ab.a
        {
                if (!ab.a.isEmpty()) {
            V3D_Point apt = ab.a.get(ab.maxaIndex);
            points.add(index, apt);
            index++;
            V3D_Triangle atr = new V3D_Triangle(p0, p1, apt);
            TreeSet<Integer> removeIndexes = new TreeSet<>();
            for (int i = 0; i < ab.a.size(); i++) {
                if (atr.isIntersectedBy(ab.a.get(i), e.oom)) {
                    removeIndexes.add(i);
                }
            }
            Iterator<Integer> ite = removeIndexes.descendingIterator();
            while (ite.hasNext()) {
                ab.a.remove(ite.next().intValue());
            }
            if (!ab.a.isEmpty()) {
                // Divide again
                V3D_Line l = new V3D_Line(p0, p1);
                V3D_Point proj = l.getPointOfIntersection(apt, e.oom);
                getConvexHull0(ab.a, apt, proj, n, index);
            }
                }
        }
        index++;
        // Process ab.b
        {
                if (!ab.b.isEmpty()) {
            V3D_Point bpt = ab.b.get(ab.maxbIndex);
            points.add(index, bpt);
            V3D_Triangle btr = new V3D_Triangle(p0, p1, bpt);
            TreeSet<Integer> removeIndexes = new TreeSet<>();
            for (int i = 0; i < ab.b.size(); i++) {
                if (btr.isIntersectedBy(ab.b.get(i), e.oom)) {
                    removeIndexes.add(i);
                }
            }
            Iterator<Integer> ite = removeIndexes.descendingIterator();
            while (ite.hasNext()) {
                ab.b.remove(ite.next().intValue());
            }
            if (!ab.b.isEmpty()) {
                // Divide again
                V3D_Line l = new V3D_Line(p0, p1);
                V3D_Point proj = l.getPointOfIntersection(bpt, e.oom);
                getConvexHull0(ab.b, bpt, proj, n, index);
            }
                }
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Ray r, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isIntersectedBy(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isIntersectedBy(V3D_Triangle t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Ray r, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Point p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BigDecimal getDistance(V3D_Ray r, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Ray r, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BigDecimal getDistance(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public class AboveAndBelow {

        // Above
        public ArrayList<V3D_Point> a;
        int maxaIndex;

        // Below
        public ArrayList<V3D_Point> b;
        int maxbIndex;

        public AboveAndBelow(ArrayList<V3D_Point> pts, V3D_Plane p) {
            a = new ArrayList<>();
            b = new ArrayList<>();
            V3D_Vector n = p.getN(e.oom);
            Math_BigRational maxads = Math_BigRational.ZERO;
            Math_BigRational maxbds = Math_BigRational.ZERO;
            for (int i = 0; i < pts.size(); i++) {
                V3D_Point pt = pts.get(i);
                Math_BigRational t = p.getPV().subtract(pt.rel, e.oom).getDotProduct(n, e.oom);
                Math_BigRational ds = pts.get(i).getDistanceSquared(p, e.oom);
                System.out.println(pt.toString() + " " + t);
                switch (t.compareTo(Math_BigRational.ZERO)) {
                    case 1:
                        if (ds.compareTo(maxads) == 1) {
                            maxads = ds;
                            maxaIndex = a.size();
                        }
                        a.add(pt);
                        System.out.println("Above the plane.");
                        break;
                    case -1:
                        if (ds.compareTo(maxbds) == 1) {
                            maxbds = ds;
                            maxbIndex = b.size();
                        }
                        b.add(pt);
                        System.out.println("Below the plane.");
                        break;
                    default:
                        System.out.println("On the plane.");
                        break;
                }
            }
        }
    }

    /**
     * If all {@link #triangles} form a single triangle return true
     *
     * @return
     */
    public final boolean isTriangle() {
        return points.size() == 3;
    }

    /**
     * If all {@link #triangles} form a single triangle return true
     *
     * @return
     */
    public boolean isRectangle() {
        if (points.size() == 4) {
            return V3D_Rectangle.isRectangle(points.get(0),
                    points.get(1), points.get(2), points.get(3));
        }
        return false;
    }

}
