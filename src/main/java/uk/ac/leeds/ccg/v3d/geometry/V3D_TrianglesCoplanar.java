/*
 * Copyright 2020 Andy Turner, University of Leeds.
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
import java.util.List;
//import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
//import java.util.ArrayList;

/**
 * Essentially this is just a collection of coplanar V3D_Triangles. Geographers
 * might consider this as a polygon or multi-polygon. For simplicity, let us
 * call these flat many sided shapes 'polygons'. Now, there can be different
 * ways to represent the same polygon as a set of triangles. Consider for
 * example four sided polygons (quadrilaterals). There are several types of
 * these, but three main ones in terms of different triangulations are:
 * <ol>
 * <li>Equal to their convex hull</li>
 * <li>Concave and not self intersecting</li>
 * <li>Concave and self intersecting</li>
 * </ol>
 * There is only one way to divide those that are concave and not self
 * intersecting into two triangles (not adding any further points): the division
 * of the quadrilateral is from the concave corner (to the opposite corner). For
 * those equal to their convex hull, there are two ways to divide the
 * quadrilateral into two triangles. One uses one pair of opposite corner
 * points. The other using the other pair of opposite corner points. Self
 * intersecting quadrilaterals can already be regarded as two triangles, but for
 * the triangulation an additional point, the point of intersection is needed.
 * Two polygons are equal if they completely intersect and cover exactly the
 * same area. It is possible that two polygons are equal even if one polygon has
 * more points than another. So, there is complication in testing if two
 * polygons are the same in that it is much more complicated to testing if two
 * triangles are the same.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_TrianglesCoplanar extends V3D_Plane implements V3D_Face {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    protected V3D_Envelope en;

    /**
     * The collection of triangles.
     */
    protected final ArrayList<V3D_Triangle> triangles;
    //protected final List<V3D_Triangle> triangles;
    //protected final V3D_Triangle[] triangles;

    /**
     * For storing the convex hull.
     */
    protected ArrayList<V3D_Point> convexHull;

    /**
     * For storing the points.
     */
    protected HashSet<V3D_Point> points;

    /**
     * Create a new instance.
     *
     * @param triangles A non-empty list of coplanar triangles.
     */
    public V3D_TrianglesCoplanar(V3D_Triangle... triangles) {
        super(triangles[0].e, triangles[0].offset, triangles[0].p,
                triangles[0].q, triangles[0].r);
        this.triangles = new ArrayList<>();
        // Ensure all triangles have the same offset as the first.
        for (V3D_Triangle t : triangles) {
            t.setOffset(triangles[0].offset);
            this.triangles.add(t);
        }
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        Iterator<V3D_Triangle> ite = triangles.iterator();
        s += ite.next().toString();
        while (ite.hasNext()) {
            s += ", " + ite.next();
        }
//        s += triangles.get(0).toString();
//        for (int i = 1; i < triangles.size(); i++) {
//            s += ", " + triangles.get(i);
//        }
        s += ")";
        return s;
    }

    @Override
    public boolean equals(Object o) {
        // Think about the case when there is a single triangle and whether this is equal to that triangle...
        if (o instanceof V3D_TrianglesCoplanar i) {
            return equals(i);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.triangles);
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
    public boolean equals(V3D_TrianglesCoplanar i, boolean b) {
        /**
         * The triangularisation of this and i might be different and the number
         * of points in each might be different, but the areas they define might
         * be the same. For the areas to be the same each triangle from each
         * must either be in the other, or it must fully intersect the other.
         */
        if (!new V3D_Plane(this).equals(new V3D_Plane(i))) {
            // If they are not in the same plane, they are unequal!
            return false;
        }
        for (V3D_Triangle t : triangles) {
            V3D_Geometry g = i.getIntersection(t, e.oom);
            if (g instanceof V3D_Triangle gt) {
                if (!t.equals(gt)) {
                    return false;
//                } else {
//                    int debug = 1;
                }
            }
        }
        for (V3D_Triangle t : i.triangles) {
            V3D_Geometry g = getIntersection(t, e.oom);
            if (g instanceof V3D_Triangle gt) {
                if (!t.equals(gt)) {
                    return false;
//                } else {
//                    int debug = 1;
                }
            }
        }
        return true;
    }

    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
            Iterator<V3D_Triangle> ite = triangles.iterator();
            en = ite.next().getEnvelope();
            while (ite.hasNext()) {
                en = en.union(ite.next().getEnvelope());
            }
//            en = triangles.get(0).getEnvelope();
//            for (int i = 1; i < triangles.size(); i++) {
//                en = en.union(triangles.get(i).getEnvelope());
//            }
        }
        return en;
    }

    /**
     * If the triangles are the same or their area can be simplified/represented
     * as a single triangle, then that triangle is returned, otherwise a
     * V3D_TrianglesCoplanar is returned.
     *
     * @param t1 A triangle.
     * @param t2 Another triangle.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return Either a triangle or a V3D_TrianglesCoplanar.
     */
    public static V3D_Geometry getGeometry(V3D_Triangle t1, V3D_Triangle t2, int oom) {
        if (t1.equals(t2)) {
            return t1;
        }
        if (t1.equals(t2)) {
            // Coplanar
            V3D_Geometry i = t1.getIntersection(t2, 0);
            if (i instanceof V3D_LineSegment l) {
                V3D_Point t1o = t1.getOpposite(l);
                V3D_Point t2o = t2.getOpposite(l);
                V3D_LineSegment lt = new V3D_LineSegment(t1o, t2o);
                V3D_Point lp = l.getP(oom);
                V3D_Point lq = l.getQ(oom);
                if (lt.isIntersectedBy(lp, oom)) {
                    return new V3D_Triangle(t1o, t2o, lq);
                } else {
                    if (lt.isIntersectedBy(lq, oom)) {
                        return new V3D_Triangle(t1o, t2o, lp);
                    }
                }
            }
        }
        return new V3D_TrianglesCoplanar(t1, t2);
    }

    /**
     * If the triangles can be simply simplified then a simplified version is
     * returned. This may be a single triangle or a V3D_TrianglesCoplanar. There
     * are cases of simplification that this will not handle. It is called
     * simply simplifying as it only works on simplifying triangles that have a
     * joining line segment. If those two triangles themselves form a triangle,
     * then a simplification takes place and this is done iteratively until all
     * such simplification is done. This however does not simplify the following
     * case where it can be seen that the 4 triangles represent an area that can
     * be given as a single triangle: null null null null null null null null
     * null null null null null null null null null null null null null null
     * null null null null null null     {@code
     * *------*------*
     * |     /|     /
     * |    / |    /
     * |   /  |   /
     * |  /   |  /
     * | /    | /
     * |/     |/
     * *------*
     * |     /
     * |    /
     * |   /
     * |  /
     * | /
     * |/
     * *
     * }
     * Fortunately in simple triangle triangle intersection resulting in a
     * hexagon, pentagon or quadrilateral, whatever way these are represented
     * with triangles, when each triangle in the representation is intersected
     * with the other representation, the result can be simplified back to the
     * original and tested for equality. To simplify in a more comprehensive way
     * there are several strategies that could be adopted. One way is to
     * calculate convex hulls for subsets of triangles and see if these
     * themselves are triangles defining the same area as their constituent
     * parts. In the case above where the simple simplifying algorithm does not
     * work, there is still some alignment of constituent triangles which might
     * help...
     *
     * @param triangles The triangles to attempt to simplify.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return Either a triangle or a V3D_TrianglesCoplanar.
     */
    public static V3D_Geometry getGeometry(int oom, V3D_Triangle... triangles) {
        boolean look = true;
        boolean simplified = false;
        List<V3D_Triangle> triangleList = Arrays.asList(triangles);
        while (look) {
            for (int i = 0; i < triangles.length; i++) {
                V3D_Triangle t1 = triangles[i];
                for (int j = i; j < triangles.length; j++) {
                    V3D_Triangle t2 = triangles[j];
                    V3D_Geometry g = getGeometry(t1, t2, oom);
                    if (g instanceof V3D_Triangle gt) {
                        triangleList.remove(i);
                        triangleList.remove(j);
                        triangleList.add(gt);
                        simplified = true;
                    }
                }
            }
            if (simplified) {
                simplified = false;
            } else {
                look = false;
            }
        }
        int tls = triangleList.size();
        if (tls == 1) {
            return triangleList.get(0);
        } else {
            return new V3D_TrianglesCoplanar(triangleList.toArray(
                    new V3D_Triangle[tls]));
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
            if (super.isIntersectedBy(pt, oom)) {
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
        if (super.isIntersectedBy(l, oom)) {
            //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy(l, oom)));        
            return triangles.stream().anyMatch(t -> (t.isIntersectedBy(l, oom)));
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom) {
        if (getEnvelope().isIntersectedBy(l.getEnvelope())) {
            if (super.isIntersectedBy(l, oom)) {
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
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Triangle t, int oom) {
        HashSet<V3D_Triangle> t2s = new HashSet<>();
        for (V3D_Triangle t2 : this.triangles) {
            if (t2.isIntersectedBy(t, oom)) {
                t2s.add(t2);
            }
        }
        int size = t2s.size();
        switch (size) {
            case 0 -> {
                return null;
            }
            case 1 -> {
                return t2s.iterator().next();
            }
            case 2 -> {
                Iterator<V3D_Triangle> ite = t2s.iterator();
                return getGeometry(ite.next(), ite.next(), oom);
            }
            default -> {
                return getGeometry(oom, t2s.toArray(V3D_Triangle[]::new));
            }
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
     * @return Get the relPoints.
     */
    public HashSet<V3D_Point> getPoints() {
        if (points == null) {
            points = new HashSet<>();
            for (V3D_Triangle t : triangles) {
                t.setOffset(offset);
                points.add(t.getP());
                points.add(t.getQ());
                points.add(t.getR());
            }
        }
        return points;
    }

    /**
     * Return the convex hull calculating it first if it has not already been
     * calculated. Below is the basic algorithm:
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
     * @return Get the convex hull.
     */
    public ArrayList<V3D_Point> getConvexHull() {
        if (convexHull == null) {
            convexHull = new ArrayList<>();
            points = getPoints();
            ArrayList<V3D_Point> pts = new ArrayList<>();
            pts.addAll(points);
            getN(e.oom);
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
            if (xminIndex == xmaxIndex) {
                V3D_LineSegment yd = new V3D_LineSegment(ymaxp, yminp);
                V3D_LineSegment zd = new V3D_LineSegment(zmaxp, zminp);
                Math_BigRational ydl2 = yd.getLength2(e.oom);
                Math_BigRational zdl2 = zd.getLength2(e.oom);
                if (ydl2.compareTo(zdl2) == 1) {
                    convexHull.add(yminp);
                    convexHull.add(ymaxp);
                    getConvexHull0(pts, yminp, ymaxp, n);
                } else {
                    convexHull.add(zminp);
                    convexHull.add(zmaxp);
                    getConvexHull0(pts, zminp, zmaxp, n);
                }
            } else if (yminIndex == ymaxIndex) {
                V3D_LineSegment xd = new V3D_LineSegment(xmaxp, xminp);
                V3D_LineSegment zd = new V3D_LineSegment(zmaxp, zminp);
                Math_BigRational xdl2 = xd.getLength2(e.oom);
                Math_BigRational zdl2 = zd.getLength2(e.oom);
                if (xdl2.compareTo(zdl2) == 1) {
                    convexHull.add(xminp);
                    convexHull.add(xmaxp);
                    getConvexHull0(pts, xminp, xmaxp, n);
                } else {
                    convexHull.add(zminp);
                    convexHull.add(zmaxp);
                    getConvexHull0(pts, zminp, zmaxp, n);
                }
            } else if (zminIndex == zmaxIndex) {
                V3D_LineSegment xd = new V3D_LineSegment(xmaxp, xminp);
                V3D_LineSegment yd = new V3D_LineSegment(ymaxp, yminp);
                Math_BigRational xdl2 = xd.getLength2(e.oom);
                Math_BigRational ydl2 = yd.getLength2(e.oom);
                if (xdl2.compareTo(ydl2) == 1) {
                    convexHull.add(xminp);
                    convexHull.add(xmaxp);
                    getConvexHull0(pts, xminp, xmaxp, n);
                } else {
                    convexHull.add(yminp);
                    convexHull.add(ymaxp);
                    getConvexHull0(pts, yminp, ymaxp, n);
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
                        convexHull.add(xminp);
                        convexHull.add(xmaxp);
                        getConvexHull0(pts, xminp, xmaxp, n);
                    } else {
                        convexHull.add(zminp);
                        convexHull.add(zmaxp);
                        getConvexHull0(pts, zminp, zmaxp, n);
                    }
                } else {
                    if (ydl2.compareTo(zdl2) == 1) {
                        convexHull.add(yminp);
                        convexHull.add(ymaxp);
                        getConvexHull0(pts, yminp, ymaxp, n);
                    } else {
                        convexHull.add(zminp);
                        convexHull.add(zmaxp);
                        getConvexHull0(pts, zminp, zmaxp, n);
                    }
                }
            }
        }
        return convexHull;
    }

    public void getConvexHull0(ArrayList<V3D_Point> pts, V3D_Point p0,
            V3D_Point p1, V3D_Vector n) {
        V3D_Plane pl = new V3D_Plane(p0, p1, new V3D_Point(e,
                offset, p0.rel.add(n, e.oom)));
        AboveAndBelow ab = new AboveAndBelow(pts, pl);
        // Process ab.a
        {
            V3D_Point apt = ab.a.get(ab.maxaIndex);
            convexHull.add(apt);
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
                if (ab.a.size() == 1) {
                    convexHull.add(ab.a.get(0));
                } else {
                    // Divide again
                    V3D_Line l = new V3D_Line(p0, p1);
                    V3D_Point proj = l.getPointOfIntersection(apt, e.oom);
                    getConvexHull0(ab.a, apt, proj, n);
                }
            }
        }
        // Process ab.b
        {
            V3D_Point bpt = ab.b.get(ab.maxbIndex);
            convexHull.add(bpt);
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
                if (ab.b.size() == 1) {
                    convexHull.add(ab.b.get(0));
                } else {
                    // Divide again
                    V3D_Line l = new V3D_Line(p0, p1);
                    V3D_Point proj = l.getPointOfIntersection(bpt, e.oom);
                    getConvexHull0(ab.b, bpt, proj, n);
                }
            }
        }
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
    public boolean isTriangle() {
        return getConvexHull().size() == 3;
    }

    /**
     * If all {@link #triangles} form a single triangle return true
     *
     * @return
     */
    public boolean isRectangle() {
        if (getConvexHull().size() == 4) {
            return V3D_Rectangle.isRectangle(convexHull.get(0),
                    convexHull.get(1), convexHull.get(2), convexHull.get(3));
        }
        return false;
    }
}
