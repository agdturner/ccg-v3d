/*
 * Copyright 2022-2025 Andy Turner, University of Leeds.
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;

/**
 * A class for representing coplanar convex hulls. An algorithm for generating a
 * convex hull from a set of coplanar points known as the "quick hull" (see
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
public class V3D_ConvexArea_d extends V3D_Area_d {

    private static final long serialVersionUID = 1L;

    /**
     * The coplanar triangles that comprise the convex hull.
     */
    public final HashMap<Integer, V3D_Triangle_d> triangles;

    /**
     * @param c The V3D_ConvexHullCoplanar_d to clone.
     */
    public V3D_ConvexArea_d(V3D_ConvexArea_d c) {
        super(c.env, c.offset, c.pl);
        points = new HashMap<>();
        c.points.values().forEach(x
                -> points.put(points.size(), new V3D_Point_d(x)));
        triangles = new HashMap<>();
        c.triangles.values().forEach(x
                -> triangles.put(triangles.size(), new V3D_Triangle_d(x)));
        edges = new HashMap<>();
        c.edges.values().forEach(x
                -> edges.put(edges.size(), new V3D_LineSegment_d(x)));
    }

    /**
     * Create a new instance.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param triangles A non-empty list of coplanar triangles.
     */
    public V3D_ConvexArea_d(double epsilon, V3D_Triangle_d... triangles) {
        this(epsilon, triangles[0].pl.n,
                V3D_Triangle_d.getPoints(triangles, epsilon));
    }

    /**
     * Create a new instance.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param n The normal for the plane.
     * @param points A non-empty list of points in a plane given by n.
     */
    public V3D_ConvexArea_d(double epsilon, V3D_Vector_d n,
            V3D_Point_d... points) {
        this(epsilon, n, Arrays.asList(points));
    }

    /**
     * Create a new instance.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param n The normal for the plane.
     * @param points A non-empty list of points in a plane given by n.
     */
    public V3D_ConvexArea_d(double epsilon, V3D_Vector_d n,
            List<V3D_Point_d> points) {
        super(points.get(0).env, points.get(0).offset,
                new V3D_Plane_d(points.get(0), n));
        this.points = new HashMap<>();
        triangles = new HashMap<>();
        edges = new HashMap<>();
        // Get a list of unique points.
        ArrayList<V3D_Point_d> pts = V3D_Point_d.getUnique(points, epsilon);
        V3D_Vector_d v0 = new V3D_Vector_d(pts.get(0).rel);
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
            V3D_Point_d pt = pts.get(i);
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
        V3D_Point_d xminp = pts.get(xminIndex);
        V3D_Point_d xmaxp = pts.get(xmaxIndex);
        V3D_Point_d yminp = pts.get(yminIndex);
        V3D_Point_d ymaxp = pts.get(ymaxIndex);
        V3D_Point_d zminp = pts.get(zminIndex);
        V3D_Point_d zmaxp = pts.get(zmaxIndex);
        this.offset = xminp.offset;
        if (xminIndex == xmaxIndex) {
            V3D_LineSegment_d yd = new V3D_LineSegment_d(ymaxp, yminp);
            V3D_LineSegment_d zd = new V3D_LineSegment_d(zmaxp, zminp);
            double ydl2 = yd.getLength2();
            double zdl2 = zd.getLength2();
            if (ydl2 > zdl2) {
                compute(pts, yminp, ymaxp, n, 1, epsilon);
            } else {
                compute(pts, zminp, zmaxp, n, 1, epsilon);
            }
        } else if (yminIndex == ymaxIndex) {
            V3D_LineSegment_d xd = new V3D_LineSegment_d(xmaxp, xminp);
            V3D_LineSegment_d zd = new V3D_LineSegment_d(zmaxp, zminp);
            double xdl2 = xd.getLength2();
            double zdl2 = zd.getLength2();
            if (xdl2 > zdl2) {
                compute(pts, xminp, xmaxp, n, 1, epsilon);
            } else {
                compute(pts, zminp, zmaxp, n, 1, epsilon);
            }
        } else if (zminIndex == zmaxIndex) {
            V3D_LineSegment_d xd = new V3D_LineSegment_d(xmaxp, xminp);
            V3D_LineSegment_d yd = new V3D_LineSegment_d(ymaxp, yminp);
            double xdl2 = xd.getLength2();
            double ydl2 = yd.getLength2();
            if (xdl2 > ydl2) {
                compute(pts, xminp, xmaxp, n, 1, epsilon);
            } else {
                compute(pts, yminp, ymaxp, n, 1, epsilon);
            }
        } else {
            V3D_LineSegment_d xd = new V3D_LineSegment_d(xmaxp, xminp);
            V3D_LineSegment_d yd = new V3D_LineSegment_d(ymaxp, yminp);
            V3D_LineSegment_d zd = new V3D_LineSegment_d(zmaxp, zminp);
            double xdl2 = xd.getLength2();
            double ydl2 = yd.getLength2();
            double zdl2 = zd.getLength2();
            if (xdl2 > ydl2) {
                if (xdl2 > zdl2) {
                    compute(pts, xminp, xmaxp, n, 1, epsilon);
                } else {
                    compute(pts, zminp, zmaxp, n, 1, epsilon);
                }
            } else {
                if (ydl2 > zdl2) {
                    compute(pts, yminp, ymaxp, n, 1, epsilon);
                } else {
                    compute(pts, zminp, zmaxp, n, 1, epsilon);
                }
            }
        }

        //if (this.points.size() < 3) {
        //    int debug = 1;
        //}

        V3D_Point_d pt = this.points.get(0);
        edges.put(edges.size(), new V3D_LineSegment_d(pt, this.points.get(1)));
        V3D_Point_d rt = null;
        for (int i = 1; i < this.points.size() - 1; i++) {
            V3D_Point_d qt = this.points.get(i);
            rt = this.points.get(i + 1);
            triangles.put(triangles.size(), new V3D_Triangle_d(pt, qt, rt));
            edges.put(edges.size(), new V3D_LineSegment_d(qt, rt));
        }
        edges.put(edges.size(), new V3D_LineSegment_d(rt, pt));
        // The edges should only intersect at the ends!

        //if (triangles.isEmpty()) {
        //    int debug = 1;
        //}
    }

    /**
     * Create a new instance.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param gs The input convex hulls.
     */
    public V3D_ConvexArea_d(double epsilon, V3D_ConvexArea_d... gs) {
        this(epsilon, gs[0].triangles.get(0).pl.n,
                V3D_FiniteGeometry_d.getPoints(gs));
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
    public V3D_ConvexArea_d(double epsilon, V3D_ConvexArea_d ch,
            V3D_Triangle_d t) {
        this(epsilon, ch.triangles.get(0).pl.n,
                V3D_FiniteGeometry_d.getPoints(ch, t));
    }

    /**
     * @return {@link #pl} initialising it first if it is null.
     */
    @Override
    public V3D_Plane_d getPl() {
        if (pl == null) {
            pl = triangles.get(0).getPl();
        }
        return pl;
    }

    @Override
    public HashMap<Integer, V3D_Point_d> getPoints() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V3D_Point_d[] getPointsArray() {
        int np = points.size();
        V3D_Point_d[] re = new V3D_Point_d[np];
        for (int i = 0; i < np; i++) {
            re[i] = new V3D_Point_d(points.get(i));
        }
        return re;
    }

    /**
     * @return A collection of the external edges.
     */
    @Override
    public HashMap<Integer, V3D_LineSegment_d> getEdges() {
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff all the triangles are the same.
     */
    public boolean equals(V3D_ConvexArea_d c, double epsilon) {
        HashSet<Integer> indexes = new HashSet<>();
        for (var x : points.values()) {
            boolean found = false;
            for (int i = 0; i < c.points.size(); i++) {
                if (x.equals(c.points.get(i), epsilon)) {
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
                    if (x.equals(c.points.get(i), epsilon)) {
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
//            V3D_Geometry g = i.getIntersect(t);
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
//            V3D_Geometry g = getIntersect(t);
//            if (g instanceof V3D_Triangle gt) {
//                if (!t.equals(gt)) {
//                    return false;
//                }
//            }
//        }
//        return true;
    }

    @Override
    public V3D_AABB_d getAABB() {
        if (en == null) {
            en = points.get(0).getAABB();
            for (int i = 1; i < points.size(); i++) {
                en = en.union(points.get(i).getAABB());
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
    public V3D_FiniteGeometry_d simplify(double epsilon) {
        if (isTriangle()) {
            return new V3D_Triangle_d(points.get(0), points.get(1),
                    points.get(2));
        } else if (isRectangle(epsilon)) {
            return new V3D_Rectangle_d(points.get(0), points.get(2),
                    points.get(1), points.get(3));
        } else {
            return this;
        }
    }

    /**
     * @param pt The point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if the point is aligned with any of the parts.
     */
    protected boolean isAligned(V3D_Point_d pt, double epsilon) {
        for (V3D_Triangle_d triangle : triangles.values()) {
            if (triangle.intersects00(pt, epsilon)) {
                return true;
            }
        }
        return false;
        //return triangles.parallelStream().anyMatch(t -> (t.intersects0(pt, oom)));
    }

    /**
     * This sums all the areas irrespective of any overlaps.
     *
     * @return The area of the triangle (rounded).
     */
    @Override
    public double getArea() {
        double sum = 0d;
        for (var t : triangles.values()) {
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
        for (var t : triangles.values()) {
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
    public V3D_FiniteGeometry_d getIntersect(V3D_Plane_d p,
            double epsilon) {
        if (triangles.get(0).pl.equalsIgnoreOrientation(p, epsilon)) {
            return this;
        }
        ArrayList<V3D_Point_d> pts = new ArrayList<>();
        for (var t : triangles.values()) {
            V3D_FiniteGeometry_d ti = t.getIntersect(p, epsilon);
            if (ti != null) {
                pts.addAll(Arrays.asList(ti.getPointsArray()));
            }
        }
        pts = V3D_Point_d.getUnique(pts, epsilon);
        int n = pts.size();
        return switch (n) {
            case 0 ->
                null;
            case 1 ->
                pts.iterator().next();
            default ->
                V3D_LineSegment_d.getGeometry(epsilon, pts.toArray(
                V3D_Point_d[]::new));
        };
    }

    /**
     * Compute and return the intersection with {@code r}.
     * 
     * @param r The ray known to intersect with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    @Override
    public V3D_FiniteGeometry_d getIntersect(V3D_Ray_d r, double epsilon) {
        V3D_Geometry_d i = triangles.get(0).pl.getIntersect(r.l, epsilon);
        if (i == null) {
            return null;
        } else if (i instanceof V3D_Point_d ip) {
            if (r.isAligned(ip, epsilon)
                    && intersects00(ip, epsilon)) {
                return ip;
            } else {
                return null;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Compute and return the intersection with {@code r} which is non-coplanar.
     *
     * @param r The ray known to intersect with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    @Override
    public V3D_Point_d getIntersect0(V3D_Ray_d r, double epsilon) {
        V3D_Point_d i = r.getIntersect0(getPl(), epsilon);
        if (i == null) {
            return null;
        } else if (r.isAligned(i, epsilon)
                && intersects00(i, epsilon)) {
            return i;
        } else {
            return null;
        }
    }

    /**
     * Get the intersection between the geometry and the triangle {@code t}.
     *
     * @param t The triangle to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_Triangle_d t,
            double epsilon) {
        // Create a set all the intersecting triangles from this.
        List<V3D_Point_d> ts = new ArrayList<>();
        for (V3D_Triangle_d t2 : triangles.values()) {
            V3D_FiniteGeometry_d i = t2.getIntersect(t, epsilon);
            ts.addAll(Arrays.asList(i.getPointsArray()));
        }
        ArrayList<V3D_Point_d> tsu = V3D_Point_d.getUnique(ts, epsilon);
        if (tsu.isEmpty()) {
            return null;
        } else {
            return new V3D_ConvexArea_d(epsilon, t.pl.n,
                    tsu.toArray(V3D_Point_d[]::new)).simplify(epsilon);
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
//    public boolean isAABBIntersectedBy(V3D_Line l, int oom) {
//        return getAABB().intersects(l, oom);
//    }
    @Override
    public V3D_ConvexArea_d rotate(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V3D_ConvexArea_d(this);
        } else {
            return rotateN(ray, uv, theta, epsilon);
        }
    }

    @Override
    public V3D_ConvexArea_d rotateN(V3D_Ray_d ray,
            V3D_Vector_d uv, double theta, double epsilon) {
        V3D_Triangle_d[] rts = new V3D_Triangle_d[triangles.size()];
        for (int i = 0; i < triangles.size(); i++) {
            rts[0] = triangles.get(i).rotate(ray, uv, theta, epsilon);
        }
        return new V3D_ConvexArea_d(epsilon, rts);
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
    private void compute(ArrayList<V3D_Point_d> pts, V3D_Point_d p0,
            V3D_Point_d p1, V3D_Vector_d n, int index, double epsilon) {
        V3D_Plane_d plane = new V3D_Plane_d(p0, p1, new V3D_Point_d(env,
                p0.offset, p0.rel.add(n)));
        AB ab = new AB(pts, plane, epsilon);
        // Process ab.a
        points.put(points.size(), p0);
        if (!ab.a.isEmpty()) {
            if (ab.a.size() == 1) {
                points.put(index, ab.a.get(0));
                index++;
            } else {
                V3D_Point_d apt = ab.a.get(ab.maxaIndex);
                points.put(index, apt);
                index++;
                V3D_Triangle_d atr = new V3D_Triangle_d(p0, p1, apt);
                TreeSet<Integer> removeIndexes = new TreeSet<>();
                for (int i = 0; i < ab.a.size(); i++) {
                    if (atr.intersects(ab.a.get(i), epsilon)) {
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
                        points.put(index, ab.a.get(0));
                        index++;
                    } else {
                        // Divide again
                        V3D_Line_d l = new V3D_Line_d(p0, p1);
                        V3D_Point_d proj = l.getPointOfIntersect(apt, epsilon);
                        compute(ab.a, proj, apt, n, index, epsilon);
                    }
                }
            }
        }
        points.put(points.size(), p1);
        index++;
        // Process ab.b
        if (!ab.b.isEmpty()) {
            if (ab.b.size() == 1) {
                points.put(index, ab.b.get(0));
                index++;
            } else {
                V3D_Point_d bpt = ab.b.get(ab.maxbIndex);
                points.put(index, bpt);
                index++;
                V3D_Triangle_d btr = new V3D_Triangle_d(p0, p1, bpt); // p1 and bpt might be the same!
                TreeSet<Integer> removeIndexes = new TreeSet<>();
                for (int i = 0; i < ab.b.size(); i++) {
                    if (btr.intersects(ab.b.get(i), epsilon)) {
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
                        points.put(index, ab.b.get(0));
                        index++;
                    } else {
                        // Divide again
                        V3D_Line_d l = new V3D_Line_d(p0, p1);
                        V3D_Point_d proj = l.getPointOfIntersect(bpt, epsilon);
                        compute(ab.b, bpt, proj, n, index, epsilon);
                    }
                }
            }
        }
    }

    /**
     * Identify if this is intersected by {@code p}. This first check if
     * {@code pt} intersects the Axis Aligned Bounding Box of {@code this}, then
     * checks the point is on the plane.
     *
     * @param p The point to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is intersected by {@code p}.
     */
    @Override
    public boolean intersects(V3D_Point_d p, double epsilon) {
        if (getAABB().intersects(p)) {
            return intersects0(p, epsilon);
        } else {
            return false;
        }
    }

    /**
     * Identify if this is intersected by {@code p}. There is no check to
     * evaluate if {@code p} intersects the Axis Aligned Bounding Box, but there
     * is a check that the point intersects the plane.
     *
     * @param p The point to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is intersected by {@code p}.
     */
    public boolean intersects0(V3D_Point_d p, double epsilon) {
        // Check point is on the plane. 
        if (triangles.get(0).pl.intersects(epsilon, p)) {
            // Check point is in a triangle
            return intersects00(p, epsilon);
        }
        return false;
    }

    /**
     * Identify if this is intersected by {@code p}. There is no check to
     * evaluate if {@code p} intersects the Axis Aligned Bounding Box or if it
     * intersects the plane.
     *
     * @param p The point to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is intersected by {@code p}.
     */
    public boolean intersects00(V3D_Point_d p, double epsilon) {
        return triangles.values().parallelStream().anyMatch(x
                -> x.intersects00(p, epsilon));
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param aabb The Axis Aligned Bounding Box to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    @Override
    public boolean intersects(V3D_AABB_d aabb, double epsilon) {
        if (getAABB().intersects(aabb)) {
            return triangles.values().parallelStream().anyMatch(x
                    -> x.intersects(aabb, epsilon));
        }
        return false;
    }

    /**
     * Identify if this is intersected by line {@code l}.
     *
     * @param l The line to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code l}.
     */
    @Override
    public boolean intersects(V3D_Line_d l, double epsilon) {
        return l.intersects(getAABB(), epsilon)
                && intersects0(l, epsilon);
    }

    /**
     * Identify if this is intersected by line {@code l}.
     *
     * @param l The line to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code p}.
     */
    public boolean intersects0(V3D_Line_d l, double epsilon) {
        return triangles.values().parallelStream().anyMatch(x
                -> x.intersects(l, epsilon));
    }

    /**
     * Identify if this is intersected by line segment {@code l}.
     *
     * @param l The line segment to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code l}.
     */
    @Override
    public boolean intersects(V3D_LineSegment_d l, double epsilon) {
        return l.intersects(getAABB(), epsilon)
                && intersects0(l, epsilon);
    }

    /**
     * Identify if this is intersected by line segment {@code l}.
     *
     * @param l The line segment to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is intersected by {@code p}.
     */
    public boolean intersects0(V3D_LineSegment_d l, double epsilon) {
        return triangles.values().parallelStream().anyMatch(x
                -> x.intersects(l, epsilon));
    }

    /**
     * Identify if this is intersected by {@code r}.
     *
     * @param r The ray to test if it intersects.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersects(V3D_Ray_d r, double epsilon) {
        return triangles.values().parallelStream().anyMatch(x
                -> x.intersects(r, epsilon));
    }

    /**
     * Identify if this is intersected by {@code r} which is assumed to be 
     * non-coplanar.
     *
     * @param r The ray to test if it intersects.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersects0(V3D_Ray_d r, double epsilon) {
        return triangles.values().parallelStream().anyMatch(x
                -> x.intersects0(r, epsilon));
    }

    /**
     * Identify if this is intersected by triangle {@code t}.
     *
     * @param t The triangle to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is intersected by {@code t}.
     */
    @Override
    public boolean intersects(V3D_Triangle_d t, double epsilon) {
        return t.intersects(getAABB(), epsilon)
                && intersects0(t, epsilon);
    }

    /**
     * Identify if this is intersected by triangle {@code t}.
     *
     * @param t The triangle to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is intersected by {@code t}.
     */
    public boolean intersects0(V3D_Triangle_d t, double epsilon) {
        return triangles.values().parallelStream().anyMatch(x
                -> x.intersects0(t, epsilon));
    }

    /**
     * A class for helping to calculate a convex hull.
     */
    public class AB {

        /**
         * The points that are above the plane.
         */
        public ArrayList<V3D_Point_d> a;

        /**
         * For storing the index of the point in {@link #a} that is the maximum
         * distance from the plane.
         */
        int maxaIndex;

        /**
         * The points that are below the plane.
         */
        public ArrayList<V3D_Point_d> b;

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
        public AB(ArrayList<V3D_Point_d> pts, V3D_Plane_d pl, double epsilon) {
            a = new ArrayList<>();
            b = new ArrayList<>();
            // Find points on, above and below the plane.
            for (int i = 0; i < pts.size(); i++) {
                V3D_Point_d pt = pts.get(i);
                int sop = pl.getSideOfPlane(pt, epsilon);
                if (sop > 0) {
                    a.add(pt);
                } else if (sop < 0) {
                    b.add(pt);
                }
            }
//            a = V3D_Point_d.getUnique(a, epsilon);
            maxaIndex = 0;
            if (a.size() > 1) {
                V3D_Point_d pt0 = a.get(0);
                double maxds = pl.getDistanceSquared(pt0, epsilon);
                for (int i = 1; i < a.size(); i++) {
                    V3D_Point_d pt = a.get(i);
                    double ds = pl.getDistanceSquared(pt, epsilon);
                    if (ds > maxds) {
                        maxds = ds;
                        maxaIndex = i;
                    }
                }
            }
            //        b = V3D_Point_d.getUnique(b, epsilon);
            maxbIndex = 0;
            if (b.size() > 1) {
                V3D_Point_d pt0 = b.get(0);
                double maxds = pl.getDistanceSquared(pt0, epsilon);
                for (int i = 1; i < b.size(); i++) {
                    V3D_Point_d pt = b.get(i);
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
            return V3D_Rectangle_d.isRectangle(points.get(0),
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
    public V3D_FiniteGeometry_d clip(V3D_Plane_d pl, V3D_Point_d p,
            double epsilon) {
        V3D_FiniteGeometry_d i = getIntersect(pl, epsilon);
        if (i == null) {
            if (pl.isOnSameSide(points.get(0), p, epsilon)) {
                return this;
            } else {
                return null;
            }
        } else if (i instanceof V3D_Point_d ip) {
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
        } else if (i instanceof V3D_LineSegment_d il) {
            //V3D_LineSegment_d il = (V3D_LineSegment_d) i;
            ArrayList<V3D_Point_d> pts = new ArrayList<>();
            for (V3D_Point_d pt : points.values()) {
                if (pl.isOnSameSide(pt, p, epsilon)) {
                    pts.add(pt);
                }
            }
            if (pts.isEmpty()) {
                return il;
            } else {
                pts.add(il.getP());
                pts.add(il.getQ());
                //      pts = V3D_Point_d.getUnique(pts, epsilon);
                return V3D_ConvexArea_d.getGeometry(epsilon,
                        pts.toArray(V3D_Point_d[]::new));
//                return new V3D_ConvexHullCoplanarDouble(
//                        this.triangles.get(0).getPl(epsilon).n, epsilon,
//                        pts.toArray(V3D_Point_d[]::new));
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
    public V3D_FiniteGeometry_d clip(V3D_Triangle_d t,
            V3D_Point_d pt, double epsilon) {
        V3D_Point_d tp = t.getP();
        V3D_Point_d tq = t.getQ();
        V3D_Point_d tr = t.getR();
        V3D_Vector_d n = t.pl.n;
        V3D_Point_d pp = new V3D_Point_d(env, tp.offset.add(n), tp.rel);
        V3D_Plane_d ppl = new V3D_Plane_d(tp, tq, pp);
        V3D_Point_d qp = new V3D_Point_d(env, tq.offset.add(n), tq.rel);
        V3D_Plane_d qpl = new V3D_Plane_d(tq, tr, qp);
        V3D_Point_d rp = new V3D_Point_d(env, tr.offset.add(n), tr.rel);
        V3D_Plane_d rpl = new V3D_Plane_d(tr, tp, rp);
        V3D_FiniteGeometry_d cppl = clip(ppl, tr, epsilon);
        if (cppl == null) {
            return null;
        } else if (cppl instanceof V3D_Point_d) {
            return cppl;
        } else if (cppl instanceof V3D_LineSegment_d cppll) {
            V3D_FiniteGeometry_d cppllcqpl = cppll.clip(qpl, pt, epsilon);
            if (cppllcqpl == null) {
                return null;
            } else if (cppllcqpl instanceof V3D_Point_d) {
                return cppllcqpl;
            } else {
                return ((V3D_LineSegment_d) cppllcqpl).clip(rpl, pt, epsilon);
            }
        } else if (cppl instanceof V3D_Triangle_d cpplt) {
            V3D_FiniteGeometry_d cppltcqpl = cpplt.clip(qpl, pt, epsilon);
            if (cppltcqpl == null) {
                return null;
            } else if (cppltcqpl instanceof V3D_Point_d) {
                return cppltcqpl;
            } else if (cppltcqpl instanceof V3D_LineSegment_d cppltcqpll) {
                return cppltcqpll.clip(rpl, pt, epsilon);
            } else if (cppltcqpl instanceof V3D_Triangle_d cppltcqplt) {
                return cppltcqplt.clip(rpl, pt, epsilon);
            } else {
                V3D_ConvexArea_d c = (V3D_ConvexArea_d) cppltcqpl;
                return c.clip(rpl, tq, epsilon);
            }
        } else {
            V3D_ConvexArea_d c = (V3D_ConvexArea_d) cppl;
            V3D_FiniteGeometry_d cc = c.clip(qpl, pt, epsilon);
            if (cc == null) {
                return cc;
            } else if (cc instanceof V3D_Point_d) {
                return cc;
            } else if (cc instanceof V3D_LineSegment_d cppll) {
                V3D_FiniteGeometry_d cccqpl = cppll.clip(qpl, pt, epsilon);
                if (cccqpl == null) {
                    return null;
                } else if (cccqpl instanceof V3D_Point_d) {
                    return cccqpl;
                } else {
                    return ((V3D_LineSegment_d) cccqpl).clip(rpl, pt, epsilon);
                }
            } else if (cc instanceof V3D_Triangle_d ccct) {
                return ccct.clip(rpl, tq, epsilon);
            } else {
                V3D_ConvexArea_d ccc = (V3D_ConvexArea_d) cc;
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
    public static V3D_FiniteGeometry_d getGeometry(double epsilon, V3D_Point_d... pts) {
        ArrayList<V3D_Point_d> upts = V3D_Point_d.getUnique(Arrays.asList(pts), epsilon);
        Iterator<V3D_Point_d> i = upts.iterator();
        switch (upts.size()) {
            case 1 -> {
                return i.next();
            }
            case 2 -> {
                return new V3D_LineSegment_d(i.next(), i.next());
            }
            case 3 -> {
                if (V3D_Line_d.isCollinear(epsilon, pts)) {
                    return V3D_LineSegment_d.getGeometry(epsilon, pts);
                } else {
                    return new V3D_Triangle_d(i.next(), i.next(), i.next());
                }
            }
            default -> {
                V3D_Point_d ip = i.next();
                V3D_Point_d iq = i.next();
                V3D_Point_d ir = i.next();
                while (V3D_Line_d.isCollinear(epsilon, ip, iq, ir) && i.hasNext()) {
                    ir = i.next();
                }
                V3D_Plane_d pl;
                if (V3D_Line_d.isCollinear(epsilon, ip, iq, ir)) {
                    return new V3D_LineSegment_d(epsilon, pts);
                } else {
                    pl = new V3D_Plane_d(ip, iq, ir);
                    return new V3D_ConvexArea_d(epsilon, pl.n, pts);
                }
            }

        }
    }

}
