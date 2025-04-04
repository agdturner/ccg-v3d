/*
 * Copyright 2019 Andy Turner, University of Leeds.
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
import java.util.TreeSet;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;

/**
 * For representing multiple collinear line segments.
 *
 * Things to do:
 * <ol>
 * <li>Find extremes.</li>
 * </ol>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineSegmentsCollinear_d extends V3D_FiniteGeometry_d {

    private static final long serialVersionUID = 1L;

    /**
     * The collinear line segments.
     */
    public final ArrayList<V3D_LineSegment_d> lineSegments;

    public V3D_LineSegmentsCollinear_d(V3D_LineSegmentsCollinear_d ls) {
        super(ls.env, ls.offset);
        lineSegments = new ArrayList<>(ls.lineSegments.size());
        ls.lineSegments.forEach(x -> lineSegments.add(new V3D_LineSegment_d(x)));
    }

    /**
     * Create a new instance.
     *
     * @param lineSegments What {@code #lineSegments} is set to.
     */
    public V3D_LineSegmentsCollinear_d(V3D_LineSegment_d... lineSegments) {
        super(lineSegments[0].env, lineSegments[0].offset);
        this.lineSegments = new ArrayList<>();
        this.lineSegments.addAll(Arrays.asList(lineSegments));
    }

    @Override
    public V3D_Point_d[] getPointsArray() {
        int nl = lineSegments.size();
        V3D_Point_d[] r = new V3D_Point_d[nl * 2];
        for (int i = 0; i < nl; i++) {
            V3D_LineSegment_d l = lineSegments.get(i);
            r[i] = l.getP();
            r[i + nl] = l.getQ();
        }
        return r;
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        Iterator<V3D_LineSegment_d> ite = lineSegments.iterator();
        if (!lineSegments.isEmpty()) {
            s += ite.next().toString();
        }
        while (ite.hasNext()) {
            s += ", " + ite.next().toString();
        }
        return s + ")";
    }

    /**
     * @param l The line segment to test if it is the same as {@code this}.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_LineSegmentsCollinear_d l, double epsilon) {
        HashSet<Integer> indexes = new HashSet<>();
        for (var x : lineSegments) {
            boolean found = false;
            for (int i = 0; i < l.lineSegments.size(); i++) {
                if (x.equalsIgnoreDirection(epsilon, l.lineSegments.get(i))) {
                    found = true;
                    indexes.add(i);
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        for (int i = 0; i < l.lineSegments.size(); i++) {
            if (!indexes.contains(i)) {
                boolean found = false;
                for (var x : lineSegments) {
                    if (x.equalsIgnoreDirection(epsilon, l.lineSegments.get(i))) {
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
//        if (!l.lineSegments.containsAll(lineSegments)) {
//            return false;
//        }
//        return lineSegments.containsAll(l.lineSegments);
    }

    @Override
    public V3D_AABB_d getAABB() {
        if (en == null) {
            Iterator<V3D_LineSegment_d> ite = lineSegments.iterator();
            en = ite.next().getAABB();
            while (ite.hasNext()) {
                en = en.union(ite.next().getAABB());
            }
        }
        return en;
    }

    /**
     * Get the minimum distance to {@code p}.
     *
     * @param p A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance squared to {@code p}.
     */
    public double getDistance(V3D_Point_d p, double epsilon) {
        return Math.sqrt(getDistanceSquared(p, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code p}.
     *
     * @param p A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance squared to {@code p}.
     */
    public double getDistanceSquared(V3D_Point_d p, double epsilon) {
        if (intersects(p, epsilon)) {
            return 0d;
        }
        Iterator<V3D_LineSegment_d> ite = lineSegments.iterator();
        double d = ite.next().getDistanceSquared(p, epsilon);
        while (ite.hasNext()) {
            d = Math.min(d, ite.next().getDistanceSquared(p, epsilon));
        }
        return d;
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistance(V3D_Line_d l, double epsilon) {
        return Math.sqrt(getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistanceSquared(V3D_Line_d l, double epsilon) {
//        if (intersects(l)) {
//            return Math_BigRational.ZERO;
//        }
        Iterator<V3D_LineSegment_d> ite = lineSegments.iterator();
        double d = ite.next().getDistanceSquared(l, epsilon);
        while (ite.hasNext()) {
            d = Math.min(d, ite.next().getDistanceSquared(l, epsilon));
        }
        return d;
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V3D_LineSegment_d l, double epsilon) {
        return Math.sqrt(getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistanceSquared(V3D_LineSegment_d l, double epsilon) {
        if (getIntersect(l, epsilon) != null) {
            return 0d;
        }
        Iterator<V3D_LineSegment_d> ite = lineSegments.iterator();
        double d = ite.next().getDistanceSquared(l, epsilon);
        while (ite.hasNext()) {
            d = Math.min(d, ite.next().getDistanceSquared(l, epsilon));
        }
        return d;
    }

    /**
     * Identify if this is intersected by point {@code pt}.
     *
     * @param pt The point to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff the geometry is intersected by {@code pt}.
     */
    public boolean intersects(V3D_Point_d pt, double epsilon) {
        Iterator<V3D_LineSegment_d> ite = lineSegments.iterator();
        while (ite.hasNext()) {
            if (ite.next().intersects(pt, epsilon)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the intersection between this and the line {@code l}.
     *
     * @param l The line to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_Geometry_d getIntersect(V3D_Line_d l,
            double epsilon) {
        Iterator<V3D_LineSegment_d> ite = lineSegments.iterator();
        V3D_Geometry_d g = ite.next().getIntersect(l, epsilon);
        if (g == null) {
            while (ite.hasNext()) {
                g = ite.next().getIntersect(l, epsilon);
                if (g instanceof V3D_Point_d) {
                    return g;
                }
            }
            return null;
        } else if (g instanceof V3D_Point_d) {
            return g;
        } else {
            return this;
        }
    }

    /**
     * Get the intersection between this and the line segment {@code l}. This is
     * complicated in that the result of the intersection could be comprised of
     * points and line segments. Line segments have to have different start and
     * end points.
     *
     * @param ls The line segment to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_LineSegment_d ls,
            double epsilon) {
        //if (lineSegments.get(0).l.isCollinear(epsilon, ls.getPointsArray())) {
        if (ls.l.equals(epsilon, lineSegments.get(0).l)) {
            ArrayList<V3D_Point_d> ps = new ArrayList<>();
            ArrayList<V3D_LineSegment_d> lse = new ArrayList<>();
            Iterator<V3D_LineSegment_d> ite = lineSegments.iterator();
            while (ite.hasNext()) {
                V3D_Geometry_d g = ite.next().getIntersect(ls, epsilon);
                if (g != null) {
                    if (g instanceof V3D_Point_d gp) {
                        ps.add(gp);
                    } else {
                        lse.add((V3D_LineSegment_d) g);
                    }
                }
            }
            if (!ps.isEmpty()) {
                // Need to handle cases where we have points.
                throw new UnsupportedOperationException();
            } else {
                if (lse.size() == 1) {
                    return lse.get(0);
                } else {
                    V3D_LineSegmentsCollinear_d r
                            = new V3D_LineSegmentsCollinear_d(
                                    lse.toArray(V3D_LineSegment_d[]::new));
                    return r.simplify(epsilon);
                }
            }
        } else {
            // Find the point of intersection if there is one.
            Iterator<V3D_LineSegment_d> ite = lineSegments.iterator();
            while (ite.hasNext()) {
                V3D_FiniteGeometry_d g = ite.next().getIntersect(ls, epsilon);
                if (g != null) {
                    return g;
                }
            }
            // Return null if there is no point of intersection.
            return null;
        }
    }

    /**
     * Combines overlapping line segments into single line segments. If there is
     * only one line segment, then a V3D_LineSegment is returned, otherwise a
     * V3D_LineSegmentsCollinear is returned.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return Either a V3D_LineSegment or a V3D_LineSegmentsCollinear which is
     * a simplified version of this with overlapping line segments replaced with
     * a single line segment.
     */
    public V3D_FiniteGeometry_d simplify(double epsilon) {
        ArrayList<V3D_LineSegment_d> dummy = new ArrayList<>();
        dummy.addAll(lineSegments);
        ArrayList<V3D_LineSegment_d> r = simplify0(dummy, 0, epsilon);
        if (r.size() == 1) {
            return r.get(0);
        } else {
            return new V3D_LineSegmentsCollinear_d(r.toArray(V3D_LineSegment_d[]::new));
        }
    }

    private ArrayList<V3D_LineSegment_d> simplify0(
            ArrayList<V3D_LineSegment_d> ls, int i, double epsilon) {
        V3D_LineSegment_d l0 = ls.get(i);
        ArrayList<V3D_LineSegment_d> r = new ArrayList<>();
        TreeSet<Integer> removeIndexes = new TreeSet<>();
        r.addAll(ls);
        for (int j = i; j < ls.size(); j++) {
            V3D_LineSegment_d l1 = ls.get(j);
            if (l0.getIntersect(l1, epsilon) != null) {
                V3D_Point_d l0p = l0.getP();
                if (l1.intersects(l0p, epsilon)) {
                    V3D_Point_d l0q = l0.getQ();
                    if (l1.intersects(l0q, epsilon)) {
                        // l0 is completely overlapped by l1
                        removeIndexes.add(i);
                    } else {
                        V3D_Point_d l1p = l1.getP();
                        V3D_Point_d l1q = l1.getQ();
                        if (l0.intersects(l1p, epsilon)) {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegment_d(l1q, l0q));
                        } else {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegment_d(l1q, l1p));
                        }
                    }
                } else {
                    V3D_Point_d l0q = l0.getQ();
                    if (l1.intersects(l0q, epsilon)) {
                        V3D_Point_d l1p = l1.getP();
                        V3D_Point_d l1q = l1.getQ();
                        if (l0.intersects(l1.getP(), epsilon)) {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegment_d(l1q, l0p));
                        } else {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegment_d(l1p, l0p));
                        }
                    } else {
                        // l1 is completely overlapped by l0
                        removeIndexes.add(j);
                    }
                }
            }
        }
        Iterator<Integer> ite = removeIndexes.descendingIterator();
        while (ite.hasNext()) {
            r.remove(ite.next().intValue());
        }
        if (i < r.size() - 1) {
            r = simplify0(r, i + 1, epsilon);
        }
        return r;
    }

    @Override
    public void translate(V3D_Vector_d v) {
        super.translate(v);
        if (en != null) {
            en.translate(v);
        }
        for (int i = 0; i < lineSegments.size(); i++) {
            lineSegments.get(i).translate(v);
        }
    }

    @Override
    public V3D_LineSegmentsCollinear_d rotate(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V3D_LineSegmentsCollinear_d(this);
        } else {
            return rotateN(ray, uv, theta, epsilon);
        }
    }

    @Override
    public V3D_LineSegmentsCollinear_d rotateN(V3D_Ray_d ray,
            V3D_Vector_d uv, double theta, double epsilon) {
        V3D_LineSegment_d[] rls = new V3D_LineSegment_d[lineSegments.size()];
        for (int i = 0; i < lineSegments.size(); i++) {
            rls[0] = lineSegments.get(i).rotate(ray, uv, theta, epsilon);
        }
        return new V3D_LineSegmentsCollinear_d(rls);
    }

}
