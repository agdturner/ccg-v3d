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
package uk.ac.leeds.ccg.v3d.geometry;

import ch.obermuhlner.math.big.BigRational;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;

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
public class V3D_LineSegmentsCollinear extends V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The collinear line segments.
     */
    public final ArrayList<V3D_LineSegment> lineSegments;

    /**
     * Create a new instance.
     *
     * @param lineSegments What {@code #lineSegments} is set to.
     */
    public V3D_LineSegmentsCollinear(V3D_LineSegment... lineSegments) {
        super();
        this.lineSegments = new ArrayList<>();
        this.lineSegments.addAll(Arrays.asList(lineSegments));
    }

    @Override
    public V3D_Point[] getPoints() {
        int nl = lineSegments.size();
        V3D_Point[] r = new V3D_Point[nl * 2];
        for (int i = 0; i < nl; i++) {
            V3D_LineSegment l = lineSegments.get(i);
            r[i] = l.getP();
            r[i + nl] = l.getQ();
        }
        return r;
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        Iterator<V3D_LineSegment> ite = lineSegments.iterator();
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_LineSegmentsCollinear l, int oom, RoundingMode rm) {
        HashSet<Integer> indexes = new HashSet<>();
        for (var x : lineSegments) {
            boolean found = false;
            for (int i = 0; i < l.lineSegments.size(); i++) {
                if (x.equals(l.lineSegments.get(i), oom, rm)) {
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
                    if (x.equals(l.lineSegments.get(i), oom, rm)) {
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

    /**
     * If p0 and p1 are the same, return the point. Otherwise return a line
     * segment.
     *
     * @param p0 A point which may be the same as p1.
     * @param p1 A point which may be the same as p0.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A V3D_Point if p0 and p1 are equal, otherwise return a
     * V3D_LineSegment with end points p0 an p1.
     */
    public static V3D_FiniteGeometry getGeometry(V3D_Point p0,
            V3D_Point p1, int oom, RoundingMode rm) {
        if (p0.equals(p1, oom, rm)) {
            return p0;
        } else {
            return new V3D_LineSegment(p0, p1, oom, rm);
        }

    }

    /**
     * @param l1 A line segment collinear with {@code l2}.
     * @param l2 A line segment collinear with {@code l1}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A V3D_LineSegmentsCollinear if {@code l1} and {@code l2} do not
     * intersect, otherwise a single V3D_LineSegment.
     */
    public static V3D_FiniteGeometry getGeometry(V3D_LineSegment l1,
            V3D_LineSegment l2, int oom, RoundingMode rm) {
        if (l1.getIntersection(l2, oom, rm) == null) {
            return new V3D_LineSegmentsCollinear(l1, l2);
        }
        /**
         * Check the type of intersection. {@code
         * 1)   l1p ---------- l1q
         *         l2p ---------- l2q
         * 2)   l1p ------------------------ l1q
         *         l2p ---------- l2q
         * 3)        l1p ---------- l1q
         *    l2p ------------------------ l2q
         * 4)        l1p ---------- l1q
         *    l2p ---------- l2q
         * 5)   l1q ---------- l1p
         *         l2p ---------- l2q
         * 6)   l1q ------------------------ l1p
         *         l2p ---------- l2q
         * 7)        l1q ---------- l1p
         *    l2p ------------------------ l2q
         * 8)        l1q ---------- l1p
         *    l2p ---------- l2q
         * 9)   l1p ---------- l1q
         *         l2q ---------- l2p
         * 10)   l1p ------------------------ l1q
         *         l2q ---------- l2p
         * 11)       l1p ---------- l1q
         *    l2q ------------------------ l2p
         * 12)       l1p ---------- l1q
         *    l2q ---------- l2p
         * 13)  l1q ---------- l1p
         *         l2q ---------- l2p
         * 14)  l1q ------------------------ l1p
         *         l2q ---------- l2p
         * 15)       l1q ---------- l1p
         *    l2q ------------------------ l2p
         * 16)       l1q ---------- l1p
         *    l2q ---------- l2p
         * }
         */
        V3D_Point l1p = l1.getP();
        V3D_Point l1q = l1.getQ();
        if (l2.isIntersectedBy(l1p, oom, rm)) {
            // Cases 1, 2, 5, 6, 14, 16
            if (l2.isIntersectedBy(l1q, oom, rm)) {
                // Cases 2, 6, 14
                /**
                 * The line segments are effectively the same although the start
                 * and end points may be opposite.
                 */
                //return l1;
                return l2;
            } else {
                V3D_Point l2p = l2.getP();
                // Cases 1, 5, 16
                if (l1.isIntersectedBy(l2p, oom, rm)) {
                    // Cases 5
                    return getGeometry(l1p, l1q, oom, rm);
                } else {
                    // Cases 1, 16
                    return getGeometry(l2p, l1q, oom, rm);
                }
            }
        } else {
            // Cases 3, 4, 7, 8, 9, 10, 11, 12, 13, 15
            if (l2.isIntersectedBy(l1q, oom, rm)) {
                V3D_Point l2p = l2.getP();
                // Cases 4, 8, 9, 10, 11
                if (l1.isIntersectedBy(l2p, oom, rm)) {
                    // Cases 4, 11, 13
                    if (l1.isIntersectedBy(l2.getQ(), oom, rm)) {
                        // Cases 11
                        return l2;
                    } else {
                        // Cases 4, 13
                        return getGeometry(l1p, l2.getQ(), oom, rm);
                    }
                } else {
                    // Cases 8, 9, 10
                    V3D_Point tq = l2.getQ();
                    if (l1.isIntersectedBy(tq, oom, rm)) {
                        // Cases 8, 9
                        return getGeometry(l2.getQ(), l1q, oom, rm);
                    } else {
                        // Cases 10                      
                        return l1;
                    }
                }
            } else {
                // Cases 3, 7, 12, 15
                V3D_Point tp = l2.getP();
                if (l1.isIntersectedBy(tp, oom, rm)) {
                    // Cases 3, 12, 15
                    V3D_Point tq = l2.getQ();
                    if (l1.isIntersectedBy(tq, oom, rm)) {
                        // Cases 3, 15
                        //return l2;
                        return l1;
                    } else {
                        // Cases 12                 
                        return getGeometry(l2.getP(), l1p, oom, rm);
                    }
                } else {
                    // Cases 7
                    return l2;
                }
            }
        }
    }

    @Override
    public V3D_Envelope getEnvelope(int oom) {
        if (en == null) {
            Iterator<V3D_LineSegment> ite = lineSegments.iterator();
            en = ite.next().getEnvelope(oom);
            while (ite.hasNext()) {
                en = en.union(ite.next().getEnvelope(oom), oom);
            }
        }
        return en;
    }

    /**
     * Get the minimum distance to {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The distance squared to {@code p}.
     */
    public BigDecimal getDistance(V3D_Point p, int oom, RoundingMode rm) {
        return Math_BigRational.toBigDecimal(new Math_BigRationalSqrt(
                getDistanceSquared(p, oom, rm), oom, rm).getSqrt(oom, rm), oom,
                rm);
    }

    /**
     * Get the minimum distance squared to {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The distance squared to {@code p}.
     */
    public BigRational getDistanceSquared(V3D_Point p, int oom, RoundingMode rm) {
        if (isIntersectedBy(p, oom, rm)) {
            return BigRational.ZERO;
        }
        Iterator<V3D_LineSegment> ite = lineSegments.iterator();
        BigRational d = ite.next().getDistanceSquared(p, oom, rm);
        while (ite.hasNext()) {
            d = BigRational.min(d, ite.next().getDistanceSquared(p, oom, rm));
        }
        return d;
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance squared to {@code l}.
     */
    public BigDecimal getDistance(V3D_Line l, int oom, RoundingMode rm) {
        return Math_BigRational.toBigDecimal(new Math_BigRationalSqrt(
                getDistanceSquared(l, oom, rm), oom, rm).getSqrt(oom, rm), oom, 
                rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance squared to {@code l}.
     */
    public BigRational getDistanceSquared(V3D_Line l, int oom, RoundingMode rm) {
//        if (isIntersectedBy(l, oom, rm)) {
//            return BigRational.ZERO;
//        }
        Iterator<V3D_LineSegment> ite = lineSegments.iterator();
        BigRational d = ite.next().getDistanceSquared(l, oom, rm);
        while (ite.hasNext()) {
            d = BigRational.min(d, ite.next().getDistanceSquared(l, oom, rm));
        }
        return d;
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance to {@code l}.
     */
    public BigDecimal getDistance(V3D_LineSegment l, int oom, RoundingMode rm) {
        return Math_BigRational.toBigDecimal(new Math_BigRationalSqrt(
                getDistanceSquared(l, oom, rm), oom, rm).getSqrt(oom, rm), oom,
                rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistanceSquared(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (getIntersection(l, oom, rm) != null) {
            return BigRational.ZERO;
        }
        Iterator<V3D_LineSegment> ite = lineSegments.iterator();
        BigRational d = ite.next().getDistanceSquared(l, oom, rm);
        while (ite.hasNext()) {
            d = BigRational.min(d, ite.next().getDistanceSquared(l, oom, rm));
        }
        return d;
    }

    /**
     * Identify if this is intersected by point {@code pt}.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff the geometry is intersected by {@code pt}.
     */
    public boolean isIntersectedBy(V3D_Point pt, int oom, RoundingMode rm) {
        Iterator<V3D_LineSegment> ite = lineSegments.iterator();
        while (ite.hasNext()) {
            if (ite.next().isIntersectedBy(pt, oom, rm)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the intersection between this and the line {@code l}.
     *
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_Geometry getIntersection(V3D_Line l, int oom, RoundingMode rm) {
        Iterator<V3D_LineSegment> ite = lineSegments.iterator();
        V3D_Geometry g = ite.next().getIntersection(l, oom, rm);
        if (g == null) {
            while (ite.hasNext()) {
                g = ite.next().getIntersection(l, oom, rm);
                if (g instanceof V3D_Point) {
                    return g;
                }
            }
            return null;
        } else if (g instanceof V3D_Point) {
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment ls, int oom, RoundingMode rm) {
        if (lineSegments.get(0).l.isCollinear(ls.l, oom, rm)) {
            ArrayList<V3D_Point> ps = new ArrayList<>();
            ArrayList<V3D_LineSegment> lse = new ArrayList<>();
            Iterator<V3D_LineSegment> ite = lineSegments.iterator();
            while (ite.hasNext()) {
                V3D_Geometry g = ite.next().getIntersection(ls, oom, rm);
                if (g != null) {
                    if (g instanceof V3D_Point gp) {
                        ps.add(gp);
                    } else {
                        lse.add((V3D_LineSegment) g);
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
                    V3D_LineSegmentsCollinear r
                            = new V3D_LineSegmentsCollinear(
                                    lse.toArray(V3D_LineSegment[]::new));
                    return r.simplify(oom, rm);
                }
            }
        } else {
            // Find the point of intersection if there is one.
            Iterator<V3D_LineSegment> ite = lineSegments.iterator();
            while (ite.hasNext()) {
                V3D_FiniteGeometry g = ite.next().getIntersection(ls, oom, rm);
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return Either a V3D_LineSegment or a V3D_LineSegmentsCollinear which is
     * a simplified version of this with overlapping line segments replaced with
     * a single line segment.
     */
    public V3D_FiniteGeometry simplify(int oom, RoundingMode rm) {
        ArrayList<V3D_LineSegment> dummy = new ArrayList<>();
        dummy.addAll(lineSegments);
        ArrayList<V3D_LineSegment> r = simplify0(dummy, 0, oom, rm);
        if (r.size() == 1) {
            return r.get(0);
        } else {
            return new V3D_LineSegmentsCollinear(r.toArray(V3D_LineSegment[]::new));
        }
    }

    private ArrayList<V3D_LineSegment> simplify0(
            ArrayList<V3D_LineSegment> ls, int i, int oom, RoundingMode rm) {
        V3D_LineSegment l0 = ls.get(i);
        ArrayList<V3D_LineSegment> r = new ArrayList<>();
        TreeSet<Integer> removeIndexes = new TreeSet<>();
        r.addAll(ls);
        for (int j = i; j < ls.size(); j++) {
            V3D_LineSegment l1 = ls.get(j);
            if (l0.getIntersection(l1, oom, rm) != null) {
                V3D_Point l0p = l0.getP();
                if (l1.isIntersectedBy(l0p, oom, rm)) {
                    V3D_Point l0q = l0.getQ();
                    if (l1.isIntersectedBy(l0q, oom, rm)) {
                        // l0 is completely overlapped by l1
                        removeIndexes.add(i);
                    } else {
                        V3D_Point l1p = l1.getP();
                        V3D_Point l1q = l1.getQ();
                        if (l0.isIntersectedBy(l1p, oom, rm)) {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegment(l1q, l0q, oom, rm));
                        } else {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegment(l1q, l1p, oom, rm));
                        }
                    }
                } else {
                    V3D_Point l0q = l0.getQ();
                    if (l1.isIntersectedBy(l0q, oom, rm)) {
                        V3D_Point l1p = l1.getP();
                        V3D_Point l1q = l1.getQ();
                        if (l0.isIntersectedBy(l1.getP(), oom, rm)) {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegment(l1q, l0p, oom, rm));
                        } else {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegment(l1p, l0p, oom, rm));
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
            r = simplify0(r, i + 1, oom, rm);
        }
        return r;
    }

    @Override
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        if (en != null) {
            en.translate(v, oom, rm);
        }
        for (int i = 0; i < lineSegments.size(); i++) {
            lineSegments.get(i).translate(v, oom, rm);
        }
    }
    
    @Override
    public V3D_LineSegmentsCollinear rotate(V3D_Ray ray, V3D_Vector uv, 
            BigRational theta, int oom, RoundingMode rm) {
        V3D_LineSegment[] rls = new V3D_LineSegment[lineSegments.size()];
        for (int i = 0; i < lineSegments.size(); i++) {
            rls[0] = lineSegments.get(i).rotate(ray, uv, theta, oom, rm);
        }
        return new V3D_LineSegmentsCollinear(rls);
    }

    @Override
    public boolean isIntersectedBy(V3D_Envelope aabb, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
