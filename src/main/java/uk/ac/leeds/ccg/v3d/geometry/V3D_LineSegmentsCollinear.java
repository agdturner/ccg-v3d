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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;

/**
 * For representing multiple collinear line segments.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineSegmentsCollinear extends V3D_Line implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope of this.
     */
    protected V3D_Envelope en;

    /**
     * The collinear line segments.
     */
    public final Set<V3D_LineSegment> lineSegments;

    /**
     * Create a new instance.
     *
     * @param lineSegments What {@code #lineSegments} is set to.
     */
    public V3D_LineSegmentsCollinear(V3D_LineSegment... lineSegments) {
        super(lineSegments[0]);
        this.lineSegments = new HashSet<>();
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
     * @param oom The Order of Magnitude for the calculation.
     * @return The length of {@code this}.
     */
    public Math_BigRationalSqrt getLength(int oom) {
        return new Math_BigRationalSqrt(getLength2(oom), oom);
    }

    /**
     * @param oom The Order of Magnitude for the calculation.
     * @return The length of {@code this} squared.
     */
    public Math_BigRational getLength2(int oom) {
        Math_BigRational r = Math_BigRational.ZERO;
        lineSegments.forEach(x -> {
            r.add(x.getLength2(oom));
        });
        return r;
    }

    /**
     * 
     * @param l1 A line segment collinear with {@code l2}
     * @param l2 A line segment collinear with {@code l1}
     * @return A V3D_LineSegmentsCollinear if {@code l1} and {@code l2} do not 
     * intersect, otherwise a single V3D_LineSegment
     */
    public static V3D_Geometry getGeometry(V3D_LineSegment l1, V3D_LineSegment l2, int oom) {
        if (!l1.isIntersectedBy(l2, oom, true)) {
            return new V3D_LineSegmentsCollinear(l1, l2);
        }
        /**
         * Check the type of intersection. {@code
         * 1)   p ---------- q
         *         l.p ---------- l.q
         * 2)   p ------------------------ q
         *         l.p ---------- l.q
         * 3)        p ---------- q
         *    l.p ------------------------ l.q
         * 4)        p ---------- q
         *    l.p ---------- l.q
         * 5)   q ---------- p
         *         l.p ---------- l.q
         * 6)   q ------------------------ p
         *         l.p ---------- l.q
         * 7)        q ---------- p
         *    l.p ------------------------ l.q
         * 8)        q ---------- p
         *    l.p ---------- l.q
         * 9)   p ---------- q
         *         l.q ---------- l.p
         * 10)   p ------------------------ q
         *         l.q ---------- l.p
         * 11)       p ---------- q
         *    l.q ------------------------ l.p
         * 12)       p ---------- q
         *    l.q ---------- l.p
         * 13)  q ---------- p
         *         l.q ---------- l.p
         * 14)  q ------------------------ p
         *         l.q ---------- l.p
         * 15)       q ---------- p
         *    l.q ------------------------ l.p
         * 16)       q ---------- p
         *    l.q ---------- l.p
         * }
         */
        V3D_Point lp = l1.getP(oom);
        V3D_Point lq = l1.getQ(oom);
        if (l2.isIntersectedBy(lp, oom)) {
            // Cases 1, 2, 5, 6, 14, 16
            if (l2.isIntersectedBy(lq, oom)) {
                // Cases 2, 6, 14
                /**
                 * The line segments are effectively the same although the start
                 * and end points may be opposite.
                 */
                return l1;
            } else {
                V3D_Point tp = l2.getP(oom);
                // Cases 1, 5, 16
                if (l1.isIntersectedBy(tp, oom)) {
                    // Cases 5
                    return new V3D_LineSegment(l1.getP(oom), l2.getP(oom));
                } else {
                    // Cases 1, 16
                    return new V3D_LineSegment(l1.getP(oom), l2.getQ(oom));
                }
            }
        } else {
            // Cases 3, 4, 7, 8, 9, 10, 11, 12, 13, 15
            if (l2.isIntersectedBy(lq, oom)) {
                V3D_Point tp = l2.getP(oom);
                // Cases 4, 8, 9, 10, 11
                if (l1.isIntersectedBy(tp, oom)) {
                    // Cases 4, 11, 13
                    if (l1.isIntersectedBy(l2.getQ(oom), oom)) {
                        // Cases 11
                        return l2;
                    } else {
                        // Cases 4, 13
                        return new V3D_LineSegment(l2.getP(oom), l1.getQ(oom));
                    }
                } else {
                    // Cases 8, 9, 10
                    V3D_Point tq = l2.getQ(oom);
                    if (l1.isIntersectedBy(tq, oom)) {
                        // Cases 8, 9
                        return new V3D_LineSegment(l2.getQ(oom), l1.getQ(oom));
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
                        return l2;
                    } else {
                        // Cases 12                 
                        return new V3D_LineSegment(l2.getP(oom), l1.getP(oom));
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_Point p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom, boolean flag) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
