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
package uk.ac.leeds.ccg.v3d.geometry.d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
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
public class V3D_ConvexHullCoplanarDouble extends V3D_FiniteGeometryDouble
        implements V3D_FaceDouble {

    private static final long serialVersionUID = 1L;

    /**
     * The collection of triangles.
     */
    protected final ArrayList<V3D_TriangleDouble> triangles;

    /**
     * The collection of points. (Todo: organise to be final)
     */
    protected ArrayList<V3D_PointDouble> points;

    /**
     * Create a new instance.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param triangles A non-empty list of coplanar triangles.
     */
    public V3D_ConvexHullCoplanarDouble(double epsilon,
            V3D_TriangleDouble... triangles) {
        this(triangles[0].pl.n, epsilon,
                V3D_TriangleDouble.getPoints(triangles));
    }

    /**
     * Create a new instance.
     *
     * @param pl The plane.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param points A non-empty list of points in a plane given by n.
     */
    public V3D_ConvexHullCoplanarDouble(V3D_PlaneDouble pl, double epsilon,
            V3D_PointDouble... points) {
        this(pl.n, epsilon, points);
    }
    /**
     * Create a new instance.
     *
     * @param n The normal for the plane.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param points A non-empty list of points in a plane given by n.
     */
    public V3D_ConvexHullCoplanarDouble(V3D_VectorDouble n, double epsilon,
            V3D_PointDouble... points) {
        super();
        this.points = new ArrayList<>();
        this.triangles = new ArrayList<>();
        // Get a list of unique points.
        ArrayList<V3D_PointDouble> pts = V3D_PointDouble.getUnique(
                Arrays.asList(points), epsilon);
        V3D_VectorDouble v0 = new V3D_VectorDouble(pts.get(0).rel);
        double xmin = v0.dx;
        double xmax = v0.dx;
        double ymin = v0.dy;
        double ymax = v0.dy;
        double zmin = v0.dz;
        double zmax = v0.dz;
        int xminIndex = 0;
        int xmaxIndex = 0;
        int yminIndex = 0;
        int ymaxIndex = 0;
        int zminIndex = 0;
        int zmaxIndex = 0;
        for (int i = 1; i < pts.size(); i++) {
            V3D_PointDouble pt = pts.get(i);
            double x = pt.rel.dx;
            double y = pt.rel.dy;
            double z = pt.rel.dz;
            if (x < xmin) {
                xmin = x;
                xminIndex = i;
            }
            if (x > xmax) {
                xmax = x;
                xmaxIndex = i;
            }
            if (y < ymin) {
                ymin = y;
                yminIndex = i;
            }
            if (y > ymax) {
                ymax = y;
                ymaxIndex = i;
            }
            if (z < zmin) {
                zmin = z;
                zminIndex = i;
            }
            if (z > zmax) {
                zmax = z;
                zmaxIndex = i;
            }
        }
        V3D_PointDouble xminp = pts.get(xminIndex);
        V3D_PointDouble xmaxp = pts.get(xmaxIndex);
        V3D_PointDouble yminp = pts.get(yminIndex);
        V3D_PointDouble ymaxp = pts.get(ymaxIndex);
        V3D_PointDouble zminp = pts.get(zminIndex);
        V3D_PointDouble zmaxp = pts.get(zmaxIndex);
        this.offset = xminp.offset;
        if (xminIndex == xmaxIndex) {
            V3D_LineSegmentDouble yd = new V3D_LineSegmentDouble(ymaxp, yminp);
            V3D_LineSegmentDouble zd = new V3D_LineSegmentDouble(zmaxp, zminp);
            double ydl2 = yd.getLength2();
            double zdl2 = zd.getLength2();
            if (ydl2 > zdl2) {
                this.points.add(yminp);
                this.points.add(ymaxp);
                compute(pts, yminp, ymaxp, n, 1, epsilon);
            } else {
                this.points.add(zminp);
                this.points.add(zmaxp);
                compute(pts, zminp, zmaxp, n, 1, epsilon);
            }
        } else if (yminIndex == ymaxIndex) {
            V3D_LineSegmentDouble xd = new V3D_LineSegmentDouble(xmaxp, xminp);
            V3D_LineSegmentDouble zd = new V3D_LineSegmentDouble(zmaxp, zminp);
            double xdl2 = xd.getLength2();
            double zdl2 = zd.getLength2();
            if (xdl2 > zdl2) {
                this.points.add(xminp);
                this.points.add(xmaxp);
                compute(pts, xminp, xmaxp, n, 1, epsilon);
            } else {
                this.points.add(zminp);
                this.points.add(zmaxp);
                compute(pts, zminp, zmaxp, n, 1, epsilon);
            }
        } else if (zminIndex == zmaxIndex) {
            V3D_LineSegmentDouble xd = new V3D_LineSegmentDouble(xmaxp, xminp);
            V3D_LineSegmentDouble yd = new V3D_LineSegmentDouble(ymaxp, yminp);
            double xdl2 = xd.getLength2();
            double ydl2 = yd.getLength2();
            if (xdl2 > ydl2) {
                this.points.add(xminp);
                this.points.add(xmaxp);
                compute(pts, xminp, xmaxp, n, 1, epsilon);
            } else {
                this.points.add(yminp);
                this.points.add(ymaxp);
                compute(pts, yminp, ymaxp, n, 1, epsilon);
            }
        } else {
            V3D_LineSegmentDouble xd = new V3D_LineSegmentDouble(xmaxp, xminp);
            V3D_LineSegmentDouble yd = new V3D_LineSegmentDouble(ymaxp, yminp);
            V3D_LineSegmentDouble zd = new V3D_LineSegmentDouble(zmaxp, zminp);
            double xdl2 = xd.getLength2();
            double ydl2 = yd.getLength2();
            double zdl2 = zd.getLength2();
            if (xdl2 > ydl2) {
                if (xdl2 > zdl2) {
                    this.points.add(xminp);
                    this.points.add(xmaxp);
                    compute(pts, xminp, xmaxp, n, 1, epsilon);
                } else {
                    this.points.add(zminp);
                    this.points.add(zmaxp);
                    compute(pts, zminp, zmaxp, n, 1, epsilon);
                }
            } else {
                if (ydl2 > zdl2) {
                    this.points.add(yminp);
                    this.points.add(ymaxp);
                    compute(pts, yminp, ymaxp, n, 1, epsilon);
                } else {
                    this.points.add(zminp);
                    this.points.add(zmaxp);
                    compute(pts, zminp, zmaxp, n, 1, epsilon);
                }
            }
        }
        this.points = V3D_PointDouble.getUnique(this.points, epsilon);

        if (this.points.size() < 3) {
            int debug = 1;
        }

        V3D_PointDouble pt = this.points.get(0);
        for (int i = 1; i < this.points.size() - 1; i++) {
            V3D_PointDouble qt = this.points.get(i);
            V3D_PointDouble rt = this.points.get(i + 1);
            
            try {
                triangles.add(new V3D_TriangleDouble(pt, qt, rt));
            } catch(RuntimeException e) {
                // Points are colinear!
                triangles.add(new V3D_TriangleDouble(pt, qt, rt));
            }
        }

        if (triangles.isEmpty()) {
            int debug = 1;
        }
    }

    /**
     * Create a new instance.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param gs The input convex hulls.
     */
    public V3D_ConvexHullCoplanarDouble(double epsilon, V3D_ConvexHullCoplanarDouble... gs) {
        this(gs[0].triangles.get(0).pl.n, epsilon, V3D_FiniteGeometryDouble.getPoints(gs));
    }

    /**
     * Create a new instance.
     *
     * @param ch The convex hull to add to the convex hull with t.
     * @param t The triangle used to set the normal and to add to the convex
     * hull with ch.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V3D_ConvexHullCoplanarDouble(V3D_ConvexHullCoplanarDouble ch,
            V3D_TriangleDouble t, double epsilon) {
        this(ch.triangles.get(0).pl.n, epsilon, V3D_FiniteGeometryDouble.getPoints(ch, t));
    }

    @Override
    public V3D_PointDouble[] getPoints() {
        int np = points.size();
        V3D_PointDouble[] re = new V3D_PointDouble[np];
        for (int i = 0; i < np; i++) {
            re[i] = new V3D_PointDouble(points.get(i));
        }
        return re;
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        Iterator<V3D_PointDouble> ite = points.iterator();
        s += ite.next().toString();
        while (ite.hasNext()) {
            s += ", " + ite.next().toString("");
        }
        s += ")";
        return s;
    }

    /**
     * @return Simple string representation.
     */
    public String toStringSimple() {
        String s = this.getClass().getName() + "(";
        Iterator<V3D_PointDouble> ite = points.iterator();
        s += ite.next().toString();
        while (ite.hasNext()) {
            s += ", " + ite.next().toStringSimple(s);
        }
        s += ")";
        return s;
    }

    /**
     * Check if {@code this} is equal to {@code i}.
     *
     * @param c An instance to compare for equality.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff all the triangles are the same.
     */
    public boolean equals(V3D_ConvexHullCoplanarDouble c, double epsilon) {
        HashSet<Integer> indexes = new HashSet<>();
        for (var x : points) {
            boolean found = false;
            for (int i = 0; i < c.points.size(); i++) {
                if (x.equals(epsilon, c.points.get(i))) {
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
                for (var x : points) {
                    if (x.equals(epsilon, c.points.get(i))) {
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
//        if (!this.triangles.get(0).pl.equals(i.triangles.get(0).pl)) {
//            // If they are not in the same plane, they are unequal!
//            return false;
//        }
//        for (V3D_Triangle t : triangles) {
//            V3D_Geometry g = i.getIntersection(t);
//            if (g instanceof V3D_Triangle gt) {
//                if (!t.equals(gt)) {
////                    System.out.println(gt);
////                    System.out.println(t);
////                    t.equals(gt);
//                    return false;
//                }
//            }
//        }
//        for (V3D_Triangle t : i.triangles) {
//            V3D_Geometry g = getIntersection(t);
//            if (g instanceof V3D_Triangle gt) {
//                if (!t.equals(gt)) {
//                    return false;
//                }
//            }
//        }
//        return true;
    }

    @Override
    public V3D_EnvelopeDouble getEnvelope() {
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return Either a triangle, rectangle or this.
     */
    public V3D_FiniteGeometryDouble simplify(double epsilon) {
        if (isTriangle()) {
            return new V3D_TriangleDouble(points.get(0), points.get(1),
                    points.get(2));
        } else if (isRectangle(epsilon)) {
            return new V3D_RectangleDouble(points.get(0), points.get(2),
                    points.get(1), points.get(3));
        } else {
            return this;
        }
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param pt The point to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V3D_PointDouble pt, double epsilon) {
        // Check envelopes intersect.
        if (getEnvelope().isIntersectedBy(pt)) {
            // Check point is on the plane. 
            if (triangles.get(0).pl.isIntersectedBy(epsilon, pt)) {
                // Check point is in a triangle
                for (var t : triangles) {
                    //if (t.isIntersectedBy(pt, epsilon)) {
                    if (t.isAligned(pt, epsilon)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param pt The point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if the point is aligned with any of the parts.
     */
    protected boolean isAligned(V3D_PointDouble pt, double epsilon) {
        for (V3D_TriangleDouble triangle : triangles) {
            if (triangle.isAligned(pt, epsilon)) {
                return true;
            }
        }
        return false;
        //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy0(pt, oom)));
    }

    /**
     * @param pt The point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this intersects with {@code pt}.
     */
    @Deprecated
    protected boolean isIntersectedBy0(V3D_PointDouble pt, double epsilon) {
        for (V3D_TriangleDouble triangle : triangles) {
            if (triangle.isIntersectedBy0(pt, epsilon)) {
                return true;
            }
        }
        return false;
        //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy0(pt, oom)));
    }

    /**
     * This sums all the areas irrespective of any overlaps.
     *
     * @return The area of the triangle (rounded).
     */
    @Override
    public double getArea() {
        double sum = 0d;
        for (var t : triangles) {
            sum = sum + t.getArea();
        }
        return sum;
    }

    /**
     * This sums all the perimeters irrespective of any overlaps.
     */
    @Override
    public double getPerimeter() {
        double sum = 0d;
        for (var t : triangles) {
            sum = sum + t.getPerimeter();
        }
        return sum;
    }

    /**
     * Get the intersection between this and the plane {@code p}.
     *
     * @param p The plane to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_PlaneDouble p,
            double epsilon) {
        if (triangles.get(0).pl.equalsIgnoreOrientation(p, epsilon)) {
            return this;
        }
        ArrayList<V3D_PointDouble> pts = new ArrayList<>();
        for (var t : triangles) {
            V3D_FiniteGeometryDouble ti = t.getIntersection(p, epsilon);
            if (ti != null) {
                pts.addAll(Arrays.asList(ti.getPoints()));
            }
        }
        pts = V3D_PointDouble.getUnique(pts, epsilon);
        int n = pts.size();
        return switch (n) {
            case 0 ->
                null;
            case 1 ->
                pts.iterator().next();
            default ->
                V3D_LineSegmentDouble.getGeometry(epsilon, pts.toArray(
                V3D_PointDouble[]::new));
        };
    }

    /**
     * Get the intersection between the geometry and the triangle {@code t}.
     *
     * @param t The triangle to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_TriangleDouble t,
            double epsilon) {
        // Create a set all the intersecting triangles from this.
        List<V3D_PointDouble> ts = new ArrayList<>();
        for (V3D_TriangleDouble t2 : triangles) {
            V3D_FiniteGeometryDouble i = t2.getIntersection(t, epsilon);
            ts.addAll(Arrays.asList(i.getPoints()));
        }
        ArrayList<V3D_PointDouble> tsu = V3D_PointDouble.getUnique(ts, epsilon);
        if (tsu.isEmpty()) {
            return null;
        } else {
            return new V3D_ConvexHullCoplanarDouble(t.pl.n, epsilon,
                    tsu.toArray(V3D_PointDouble[]::new)).simplify(epsilon);
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
    @Override
    public V3D_ConvexHullCoplanarDouble rotate(V3D_RayDouble ray, 
            V3D_VectorDouble uv, double theta, double epsilon) {
        V3D_TriangleDouble[] rts = new V3D_TriangleDouble[triangles.size()];
        for (int i = 0; i < triangles.size(); i++) {
            rts[0] = triangles.get(i).rotate(ray, uv, theta, epsilon);
        }
        return new V3D_ConvexHullCoplanarDouble(epsilon, rts);
    }

    /**
     * @param pts The points from which the convex hull is formed.
     * @param p0 A point in pts that along with p1 and n defines a plane used to
     * divide the points into 3 types, on, above and below.
     * @param p1 A point in pts that along with p0 and n defines a plane used to
     * divide the points into 3 types, on, above and below.
     * @param n The normal to the plane.
     * @param index The index into which points that form the convex hull are
     * added.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    private void compute(ArrayList<V3D_PointDouble> pts, V3D_PointDouble p0,
            V3D_PointDouble p1, V3D_VectorDouble n, int index, double epsilon) {
        V3D_PlaneDouble pl = new V3D_PlaneDouble(p0, p1, new V3D_PointDouble(
                p0.offset, p0.rel.add(n)));
        AB ab = new AB(pts, p0, p1, pl, epsilon);
        // Process ab.a
        if (!ab.a.isEmpty()) {
            if (ab.a.size() == 1) {
                points.add(index, ab.a.get(0));
                index++;
            } else {
                V3D_PointDouble apt = ab.a.get(ab.maxaIndex);
                points.add(index, apt);
                index++;
                V3D_TriangleDouble atr = new V3D_TriangleDouble(p0, p1, apt);
                TreeSet<Integer> removeIndexes = new TreeSet<>();
                for (int i = 0; i < ab.a.size(); i++) {
                    if (atr.isIntersectedBy(ab.a.get(i), epsilon)) {
                        removeIndexes.add(i);
                        //index--;
                    }
                }
                Iterator<Integer> ite = removeIndexes.descendingIterator();
                while (ite.hasNext()) {
                    ab.a.remove(ite.next().intValue());
                }
                if (!ab.a.isEmpty()) {
                    if (ab.a.size() == 1) {
                        points.add(index, ab.a.get(0));
                        index++;
                    } else {
                        // Divide again
                        V3D_LineDouble l = new V3D_LineDouble(p0, p1);
                        V3D_PointDouble proj = l.getPointOfIntersection(apt, epsilon);
                        compute(ab.a, proj, apt, n, index, epsilon);
                    }
                }
            }
        }
        // Process ab.b
        if (!ab.b.isEmpty()) {
            if (ab.b.size() == 1) {
                points.add(index, ab.b.get(0));
                index++;
            } else {
                V3D_PointDouble bpt = ab.b.get(ab.maxbIndex);
                points.add(index, bpt);
                index++;
                
                // Debug
                if (bpt.equals(epsilon, p1)) {
                    int debug = 1;
                    ab = new AB(pts, p0, p1, pl, epsilon);
                }
                
                V3D_TriangleDouble btr = new V3D_TriangleDouble(p0, p1, bpt); // p1 and bpt might be the same!
                TreeSet<Integer> removeIndexes = new TreeSet<>();
                for (int i = 0; i < ab.b.size(); i++) {
                    if (btr.isIntersectedBy(ab.b.get(i), epsilon)) {
                        removeIndexes.add(i);
                        //index--;
                    }
                }
                Iterator<Integer> ite = removeIndexes.descendingIterator();
                while (ite.hasNext()) {
                    ab.b.remove(ite.next().intValue());
                }
                if (!ab.b.isEmpty()) {
                    if (ab.b.size() == 1) {
                        points.add(index, ab.b.get(0));
                        index++;
                    } else {
                        // Divide again
                        V3D_LineDouble l = new V3D_LineDouble(p0, p1);
                        V3D_PointDouble proj = l.getPointOfIntersection(bpt, epsilon);
                        compute(ab.b, bpt, proj, n, index, epsilon);
                    }
                }
            }
        }
    }

    /**
     * A class for helping to calculate a convex hull.
     */
    public class AB {

        /**
         * The points that are above the plane.
         */
        public ArrayList<V3D_PointDouble> a;

        /**
         * For storing the index of the point in {@link #a} that is the maximum
         * distance from the plane.
         */
        int maxaIndex;

        /**
         * The points that are below the plane.
         */
        public ArrayList<V3D_PointDouble> b;

        /**
         * For storing the index of the point in {@link #b} that is the maximum
         * distance from the plane.
         */
        int maxbIndex;

        /**
         * Create a new instance.
         *
         * @param pts The points from which to calculate the convex hull.
         * @param pl The plane.
         * @param epsilon The tolerance within which two vectors are considered
         * equal.
         */
        public AB(ArrayList<V3D_PointDouble> pts, V3D_PointDouble p0,
                V3D_PointDouble p1, V3D_PlaneDouble pl,
                double epsilon) {
            a = new ArrayList<>();
            b = new ArrayList<>();
            // Find points on, above and below the plane.
            for (int i = 0; i < pts.size(); i++) {
                V3D_PointDouble pt = pts.get(i);
                int sop = pl.getSideOfPlane(pt, epsilon);
                if (sop > 0) {
                    a.add(pt);
                } else if (sop < 0) {
                    b.add(pt);
                }
            }
            a = V3D_PointDouble.getUnique(a, epsilon);
            maxaIndex = 0;
            if (a.size() > 1) {
                V3D_PointDouble pt0 = a.get(0);
                double maxds = pl.getDistanceSquared(pt0, epsilon);
                for (int i = 1; i < a.size(); i++) {
                    V3D_PointDouble pt = a.get(i);
                    double ds = pl.getDistanceSquared(pt, epsilon);
                    if (ds > maxds) {
                        maxds = ds;
                        maxaIndex = i;
                    }
                }
            }
            b = V3D_PointDouble.getUnique(b, epsilon);
            maxbIndex = 0;
            if (b.size() > 1) {
                V3D_PointDouble pt0 = b.get(0);
                double maxds = pl.getDistanceSquared(pt0, epsilon);
                for (int i = 1; i < b.size(); i++) {
                    V3D_PointDouble pt = b.get(i);
                    double ds = pl.getDistanceSquared(pt, epsilon);
                    if (ds > maxds) {
                        maxds = ds;
                        maxbIndex = i;
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is a rectangle.
     */
    public boolean isRectangle(double epsilon) {
        if (points.size() == 4) {
            return V3D_RectangleDouble.isRectangle(points.get(0),
                    points.get(1), points.get(2), points.get(3), epsilon);
        }
        return false;
    }

    /**
     * Clips this using the pl and return the part that is on the same side as
     * pl.
     *
     * @param pl The plane that clips.
     * @param p A point that is used to return the side of the clipped triangle.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometryDouble clip(V3D_PlaneDouble pl, V3D_PointDouble p,
            double epsilon) {
        V3D_FiniteGeometryDouble i = getIntersection(pl, epsilon);
        if (i == null) {
            if (pl.isOnSameSide(points.get(0), p, epsilon)) {
                return this;
            } else {
                return null;
            }
        } else if (i instanceof V3D_PointDouble ip) {
            if (pl.isOnSameSide(ip, p, epsilon)) {
                if (pl.isOnSameSide(points.get(0), p, epsilon)
                        && pl.isOnSameSide(points.get(1), p, epsilon)) {
                    return this;
                } else {
                    return ip;
                }
            } else {
                return null;
            }
        } else if (i instanceof V3D_LineSegmentDouble il) {
            //V3D_LineSegmentDouble il = (V3D_LineSegmentDouble) i;
            ArrayList<V3D_PointDouble> pts = new ArrayList<>();
            for (V3D_PointDouble pt : points) {
                if (pl.isOnSameSide(pt, p, epsilon)) {
                    pts.add(pt);
                }
            }
            if (pts.isEmpty()) {
                return il;
            } else {
                pts.add(il.getP());
                pts.add(il.getQ());
                pts = V3D_PointDouble.getUnique(pts, epsilon);
                return V3D_ConvexHullCoplanarDouble.getGeometry(epsilon,
                        pts.toArray(V3D_PointDouble[]::new));
//                return new V3D_ConvexHullCoplanarDouble(
//                        this.triangles.get(0).getPl(epsilon).n, epsilon,
//                        pts.toArray(V3D_PointDouble[]::new));
            }
        } else {
            //V3D_ConvexHullCoplanarDouble chi = (V3D_ConvexHullCoplanarDouble) i;
            return i;
        }
    }

    /**
     * Clips this using t.
     *
     * @param t The triangle to clip this with.
     * @param pt A point that is used to return the side of this that is
     * clipped.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometryDouble clip(V3D_TriangleDouble t,
            V3D_PointDouble pt, double epsilon) {
        V3D_PointDouble tp = t.getP();
        V3D_PointDouble tq = t.getQ();
        V3D_PointDouble tr = t.getR();
        V3D_VectorDouble n = t.pl.n;
        V3D_PointDouble pp = new V3D_PointDouble(tp.offset.add(n), tp.rel);
        V3D_PlaneDouble ppl = new V3D_PlaneDouble(tp, tq, pp);
        V3D_PointDouble qp = new V3D_PointDouble(tq.offset.add(n), tq.rel);
        V3D_PlaneDouble qpl = new V3D_PlaneDouble(tq, tr, qp);
        V3D_PointDouble rp = new V3D_PointDouble(tr.offset.add(n), tr.rel);
        V3D_PlaneDouble rpl = new V3D_PlaneDouble(tr, tp, rp);
        V3D_FiniteGeometryDouble cppl = clip(ppl, tr, epsilon);
        if (cppl == null) {
            return null;
        } else if (cppl instanceof V3D_PointDouble) {
            return cppl;
        } else if (cppl instanceof V3D_LineSegmentDouble cppll) {
            V3D_FiniteGeometryDouble cppllcqpl = cppll.clip(qpl, pt, epsilon);
            if (cppllcqpl == null) {
                return null;
            } else if (cppllcqpl instanceof V3D_PointDouble) {
                return cppllcqpl;
            } else {
                return ((V3D_LineSegmentDouble) cppllcqpl).clip(rpl, pt, epsilon);
            }
        } else if (cppl instanceof V3D_TriangleDouble cpplt) {
            V3D_FiniteGeometryDouble cppltcqpl = cpplt.clip(qpl, pt, epsilon);
            if (cppltcqpl == null) {
                return null;
            } else if (cppltcqpl instanceof V3D_PointDouble) {
                return cppltcqpl;
            } else if (cppltcqpl instanceof V3D_LineSegmentDouble cppltcqpll) {
                return cppltcqpll.clip(rpl, pt, epsilon);
            } else if (cppltcqpl instanceof V3D_TriangleDouble cppltcqplt) {
                return cppltcqplt.clip(rpl, pt, epsilon);
            } else {
                V3D_ConvexHullCoplanarDouble c = (V3D_ConvexHullCoplanarDouble) cppltcqpl;
                return c.clip(rpl, tq, epsilon);
            }
        } else {
            V3D_ConvexHullCoplanarDouble c = (V3D_ConvexHullCoplanarDouble) cppl;
            V3D_FiniteGeometryDouble cc = c.clip(qpl, pt, epsilon);
            if (cc == null) {
                return cc;
            } else if (cc instanceof V3D_PointDouble) {
                return cc;
            } else if (cc instanceof V3D_LineSegmentDouble cppll) {
                V3D_FiniteGeometryDouble cccqpl = cppll.clip(qpl, pt, epsilon);
                if (cccqpl == null) {
                    return null;
                } else if (cccqpl instanceof V3D_PointDouble) {
                    return cccqpl;
                } else {
                    return ((V3D_LineSegmentDouble) cccqpl).clip(rpl, pt, epsilon);
                }
            } else if (cc instanceof V3D_TriangleDouble ccct) {
                return ccct.clip(rpl, tq, epsilon);
            } else {
                V3D_ConvexHullCoplanarDouble ccc = (V3D_ConvexHullCoplanarDouble) cc;
                return ccc.clip(rpl, pt, epsilon);
            }
        }
    }

    /**
     * If pts are all equal then a V3D_Point is returned. If two are different,
     * then a V3D_LineSegment is returned. Three different, then a V3D_Triangle
     * is returned. If four or more are different then a V3D_ConvexHullCoplanar
     * is returned.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param pts The points.
     * @return Either a V3D_Point, V3D_LineSegment, V3D_Triangle, or
     * V3D_ConvexHullCoplanar.
     */
    public static V3D_FiniteGeometryDouble getGeometry(double epsilon, V3D_PointDouble... pts) {
        ArrayList<V3D_PointDouble> upts = V3D_PointDouble.getUnique(Arrays.asList(pts), epsilon);
        Iterator<V3D_PointDouble> i = upts.iterator();
        switch (upts.size()) {
            case 1 -> {
                return i.next();
            }
            case 2 -> {
                return new V3D_LineSegmentDouble(i.next(), i.next());
            }
            case 3 -> {
                if (V3D_LineDouble.isCollinear(epsilon, pts)) {
                    return V3D_LineSegmentDouble.getGeometry(epsilon, pts);
                } else {
                    return new V3D_TriangleDouble(i.next(), i.next(), i.next());
                }
            }
            default -> {
                V3D_PointDouble ip = i.next();
                V3D_PointDouble iq = i.next();
                V3D_PointDouble ir = i.next();
                while (V3D_LineDouble.isCollinear(epsilon, ip, iq, ir) && i.hasNext()) {
                    ir = i.next();
                }
                V3D_PlaneDouble pl;
                if (V3D_LineDouble.isCollinear(epsilon, ip, iq, ir)) {
                    return new V3D_LineSegmentDouble(epsilon, pts);
                } else {
                    pl = new V3D_PlaneDouble(ip, iq, ir);
                    return new V3D_ConvexHullCoplanarDouble(pl.n, epsilon, pts);
                }
            }
        }
    }

}
