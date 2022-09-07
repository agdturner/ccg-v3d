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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.TreeSet;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
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
public class V3D_LineSegmentsCollinear extends V3D_Line
        implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope of this.
     */
    protected V3D_Envelope en;

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
        super(lineSegments[0]);
        this.lineSegments = new ArrayList<>();
        this.lineSegments.addAll(Arrays.asList(lineSegments));
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_LineSegmentsCollinear lsc) {
            return equals(lsc);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.lineSegments);
        return hash;
    }

    /**
     * @param l The line segment to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_LineSegmentsCollinear l) {
        if (!l.lineSegments.containsAll(lineSegments)) {
            return false;
        }
        return lineSegments.containsAll(l.lineSegments);
    }

    /**
     *
     * @param l1 A line segment collinear with {@code l2}.
     * @param l2 A line segment collinear with {@code l1}.
     * @param oom The Order of Magnitude for the calculation.
     * @return A V3D_LineSegmentsCollinear if {@code l1} and {@code l2} do not
     * intersect, otherwise a single V3D_LineSegment.
     */
    public static V3D_Geometry getUnion(V3D_LineSegment l1,
            V3D_LineSegment l2, int oom) {
        if (!l1.isIntersectedBy(l2, oom)) {
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
        V3D_Point l1p = l1.getP(oom);
        V3D_Point l1q = l1.getQ(oom);
        if (l2.isIntersectedBy(l1p, oom)) {
            // Cases 1, 2, 5, 6, 14, 16
            if (l2.isIntersectedBy(l1q, oom)) {
                // Cases 2, 6, 14
                /**
                 * The line segments are effectively the same although the start
                 * and end points may be opposite.
                 */
                //return l1;
                return l2;
            } else {
                V3D_Point l2p = l2.getP(oom);
                // Cases 1, 5, 16
                if (l1.isIntersectedBy(l2p, oom)) {
                    // Cases 5
                    //return new V3D_LineSegment(l1p, l2.getP(oom));
                    return new V3D_LineSegment(l1p, l1q);
                } else {
                    // Cases 1, 16
                    //return new V3D_LineSegment(l1p, l2.getQ(oom));
                    return new V3D_LineSegment(l2p, l1q);
                }
            }
        } else {
            // Cases 3, 4, 7, 8, 9, 10, 11, 12, 13, 15
            if (l2.isIntersectedBy(l1q, oom)) {
                V3D_Point l2p = l2.getP(oom);
                // Cases 4, 8, 9, 10, 11
                if (l1.isIntersectedBy(l2p, oom)) {
                    // Cases 4, 11, 13
                    if (l1.isIntersectedBy(l2.getQ(oom), oom)) {
                        // Cases 11
                        return l2;
                    } else {
                        // Cases 4, 13
                        //return new V3D_LineSegment(l2.getP(oom), l1.getQ(oom));
                        return new V3D_LineSegment(l1p, l2.getQ(oom));
                    }
                } else {
                    // Cases 8, 9, 10
                    V3D_Point tq = l2.getQ(oom);
                    if (l1.isIntersectedBy(tq, oom)) {
                        // Cases 8, 9
                        return new V3D_LineSegment(l2.getQ(oom), l1q);
                    } else {
                        // Cases 10                      
                        return l1;
                    }
                }
            } else {
                // Cases 3, 7, 12, 15
                V3D_Point tp = l2.getP(oom);
                if (l1.isIntersectedBy(tp, oom)) {
                    // Cases 3, 12, 15
                    V3D_Point tq = l2.getQ(oom);
                    if (l1.isIntersectedBy(tq, oom)) {
                        // Cases 3, 15
                        //return l2;
                        return l1;
                    } else {
                        // Cases 12                 
                        return new V3D_LineSegment(l2.getP(oom), l1p);
                    }
                } else {
                    // Cases 7
                    return l2;
                }
            }
        }
    }

    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
            Iterator<V3D_LineSegment> ite = lineSegments.iterator();
            en = ite.next().getEnvelope();
            while (ite.hasNext()) {
                en = en.union(ite.next().getEnvelope());
            }
        }
        return en;
    }

    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Point p, int oom) {
        if (isIntersectedBy(p, oom)) {
            return Math_BigRational.ZERO;
        }
        Iterator<V3D_LineSegment> ite = lineSegments.iterator();
        Math_BigRational d = ite.next().getDistanceSquared(p, oom);
        while (ite.hasNext()) {
            d = d.min(ite.next().getDistanceSquared(p, oom));
        }
        return d;
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom) {
        if (isIntersectedBy(l, oom)) {
            return Math_BigRational.ZERO;
        }
        Iterator<V3D_LineSegment> ite = lineSegments.iterator();
        Math_BigRational d = ite.next().getDistanceSquared(l, oom);
        while (ite.hasNext()) {
            d = d.min(ite.next().getDistanceSquared(l, oom));
        }
        return d;
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom) {
        if (isIntersectedBy(l, oom)) {
            return Math_BigRational.ZERO;
        }
        Iterator<V3D_LineSegment> ite = lineSegments.iterator();
        Math_BigRational d = ite.next().getDistanceSquared(l, oom);
        while (ite.hasNext()) {
            d = d.min(ite.next().getDistanceSquared(l, oom));
        }
        return d;
    }

    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom) {
        Iterator<V3D_LineSegment> ite = lineSegments.iterator();
        while (ite.hasNext()) {
            if (ite.next().isIntersectedBy(pt, oom)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        Iterator<V3D_LineSegment> ite = lineSegments.iterator();
        while (ite.hasNext()) {
            if (ite.next().isIntersectedBy(l, oom)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom) {
        Iterator<V3D_LineSegment> ite = lineSegments.iterator();
        while (ite.hasNext()) {
            if (ite.next().isIntersectedBy(l, oom)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        Iterator<V3D_LineSegment> ite = lineSegments.iterator();
        V3D_Geometry g = ite.next().getIntersection(l, oom);
        if (g == null) {
            while (ite.hasNext()) {
                g = ite.next().getIntersection(l, oom);
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
     * This is complicated in that the result of the intersection could be
     * comprised of points and line segments. Line segments have to have
     * different start and end points.
     *
     * @param l The line segment to intersect with.
     * @param oom The order of magnitude for the precision.
     * @return The intersection of this and l.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom) {
        if (isIntersectedBy(l, oom)) {
            if (isCollinear(l)) {
                ArrayList<V3D_Point> ps = new ArrayList<>();
                ArrayList<V3D_LineSegment> ls = new ArrayList<>();
                Iterator<V3D_LineSegment> ite = lineSegments.iterator();
                while (ite.hasNext()) {
                    V3D_Geometry g = ite.next().getIntersection(l, oom);
                    if (g != null) {
                        if (g instanceof V3D_Point gp) {
                            ps.add(gp);
                        } else {
                            ls.add((V3D_LineSegment) g);
                        }
                    }
                }
                if (!ps.isEmpty()) {
                    // Need to handle cases where we have points.
                    throw new UnsupportedOperationException();
                } else {
                    if (ls.size() == 1) {
                        return ls.get(0);
                    } else {
                        V3D_LineSegmentsCollinear r
                                = new V3D_LineSegmentsCollinear(
                                        ls.toArray(V3D_LineSegment[]::new));
                        return r.simplify();
                    }
                }
            } else {
                // Find the point of intersection if there is one.
                Iterator<V3D_LineSegment> ite = lineSegments.iterator();
                while (ite.hasNext()) {
                    V3D_Geometry g = ite.next().getIntersection(l, oom);
                    if (g != null) {
                        return g;
                    }
                }
                // Return null if there is no point of intersection.
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Plane p, int oom) {
        for (V3D_LineSegment l : lineSegments) {
            if (p.isIntersectedBy(l, oom)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_Triangle t, int oom) {
        for (V3D_LineSegment l : lineSegments) {
            if (t.isIntersectedBy(l, oom)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom) {
        for (V3D_LineSegment l : lineSegments) {
            if (t.isIntersectedBy(l, oom)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Combines overlapping line segments into single line segments. If there is
     * only one line segment, then a V3D_LineSegment is returned, otherwise a
     * V3D_LineSegmentsCollinear is returned.
     *
     * @return Either a V3D_LineSegment or a V3D_LineSegmentsCollinear which is
     * a simplified version of this with overlapping line segments replaced with
     * a single line segment.
     */
    public V3D_Geometry simplify() {
        ArrayList<V3D_LineSegment> dummy = new ArrayList<>();
        dummy.addAll(lineSegments);
        ArrayList<V3D_LineSegment> r = simplify0(dummy, 0);
        if (r.size() == 1) {
            return r.get(0);
        } else {
            return new V3D_LineSegmentsCollinear(r.toArray(V3D_LineSegment[]::new));
        }
    }

    protected ArrayList<V3D_LineSegment> simplify0(ArrayList<V3D_LineSegment> ls, int i) {
        V3D_LineSegment l0 = ls.get(i);
        ArrayList<V3D_LineSegment> r = new ArrayList<>();
        TreeSet<Integer> removeIndexes = new TreeSet<>();
        r.addAll(ls);
        for (int j = i; j < ls.size(); j++) {
            V3D_LineSegment l1 = ls.get(j);
            if (l0.isIntersectedBy(l1, e.oom)) {
                V3D_Point l0p = l0.getP(e.oom);
                if (l0p.isIntersectedBy(l1, e.oom)) {
                    V3D_Point l0q = l0.getQ(e.oom);
                    if (l0q.isIntersectedBy(l1, e.oom)) {
                        // l0 is completely overlapped by l1
                        removeIndexes.add(i);
                    } else {
                        V3D_Point l1p = l1.getP(e.oom);
                        V3D_Point l1q = l1.getQ(e.oom);
                        if (l1p.isIntersectedBy(l0, e.oom)) {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegment(l1q, l0q));
                        } else {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegment(l1q, l1p));
                        }
                    }
                } else {
                    V3D_Point l0q = l0.getQ(e.oom);
                    if (l0q.isIntersectedBy(l1, e.oom)) {
                        V3D_Point l1p = l1.getP(e.oom);
                        V3D_Point l1q = l1.getQ(e.oom);
                        if (l1.getP(e.oom).isIntersectedBy(l0, e.oom)) {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegment(l1q, l0p));
                        } else {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegment(l1p, l0p));
                        }
                    } else {
                        // l1 is completely overlapped by l0
                        removeIndexes.add(j);
                    }
                }
            }
        }
        Iterator<Integer> ite = removeIndexes.descendingIterator();
        while(ite.hasNext()) {
            r.remove(ite.next().intValue());
        }
        if (i < r.size() - 1) {
            r = simplify0(r, i + 1);
        }
        return r;
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Triangle t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
