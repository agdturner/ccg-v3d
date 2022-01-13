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
import java.util.Objects;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;

/**
 * 3D representation of a finite length line (a line segment). The line begins
 * at the point {@link #p} and ends at the point {@link #q}. The "*" denotes a
 * point in 3D and the line is shown with a line of "e" symbols in the following
 * depiction: {@code
 *                                       z
 *                          y           -
 *                          +          /                * p=<x0,y0,z0>
 *                          |         /                e
 *                          |        /                e
 *                          |    z0-/                e
 *                          |      /                e
 *                          |     /               e
 *                          |    /               e
 *                          |   /               e
 *                       y0-|  /               e
 *                          | /               e
 *                          |/         x1    e
 * x - ---------------------|-----------/---e---/---- + x
 *                         /|              e   x0
 *                        / |-y1          e
 *                       /  |           e
 *                      /   |          e
 *                  z1-/    |         e
 *                    /     |        e
 *                   /      |       * q=<x1,y1,z1>
 *                  /       |
 *                 /        |
 *                +         -
 *               z          y
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineSegment extends V3D_Line implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope of this.
     */
    protected V3D_Envelope en;

    /**
     * For storing the length of the line squared.
     */
    protected Math_BigRational len2;

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     * @param oom What {@link #oom} is set to.
     */
    public V3D_LineSegment(V3D_LineSegment l, int oom) {
        super(l, oom);
    }

    /**
     * Create a new instance. {@link #offset} is set to {@link V3D_Vector#ZERO}.
     *
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param oom What {@link #oom} is set to.
     */
    public V3D_LineSegment(V3D_Vector p, V3D_Vector q, int oom) {
        super(V3D_Vector.ZERO, p, q, oom);
    }

    /**
     * Create a new instance.
     *
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param oom What {@link #oom} is set to.
     */
    public V3D_LineSegment(V3D_Vector offset, V3D_Vector p, V3D_Vector q, int oom) {
        super(p, q, oom);
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     * @param oom What {@link #oom} is set to.
     */
    public V3D_LineSegment(V3D_Line l, int oom) {
        super(l, oom);
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     * @param oom What {@link #oom} is set to.
     */
    public V3D_LineSegment(V3D_Envelope.LineSegment l, int oom) {
        this(new V3D_Vector(l.p), new V3D_Vector(l.q), oom);
    }

    @Override
    public String toString(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFields(pad + " ") + "\n"
                + pad + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_LineSegment l) {
            V3D_Point tp = getP(oom);
            V3D_Point tq = getQ(oom);
            V3D_Point lp = l.getP(oom);
            V3D_Point lq = l.getQ(oom);
            if (lp.equals(tp) && lq.equals(tq)) {
                return true;
            }
            if (lp.equals(tq) && lq.equals(tp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param l The line segment to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_LineSegment l) {
        return getP(oom).equals(l.getP(oom)) && getQ(oom).equals(l.getQ(oom));
    }

    /**
     * @param l The line segment to test if it is equal to {@code this}.
     * @return {@code true} iff {@code this} is equal to {@code l}. This 
     * ignores the order of {@link #p} and {@link #q}.
     */
    public boolean equalsIgnoreDirection(V3D_LineSegment l) {
        if (equals(l)) {
            return true;
        } else {
            return getP(oom).equals(l.getQ(oom)) && getQ(oom).equals(l.getP(oom));
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.p);
        hash = 31 * hash + Objects.hashCode(this.q);
        return hash;
    }

//    /**
//     * @param v The vector to apply.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return a new line.
//     */
//    @Override
//    public V3D_LineSegment apply(V3D_Vector v, int oom) {
//        V3D_LineSegment l = new V3D_LineSegment(this, oom);
//        l.apply(oom, v);
//        return l;
//    }

    /**
     * @return {@code true} iff this line segment is effectively a point.
     */
    public boolean isPoint() {
        return p.equals(q);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The length of {@code this}.
     */
    public Math_BigRationalSqrt getLength(int oom) {
        // The choice of -1 is arbitrary.
        return new Math_BigRationalSqrt(getLength2(oom), -1);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The length of {@code this} squared.
     */
    public Math_BigRational getLength2(int oom) {
        return getP(oom).getDistanceSquared(getQ(oom), oom);
    }

    /**
     * @param oom The Order of Magnitude for the initialisation.
     * @return {@code new V3D_Envelope(start, end)}
     */
    @Override
    public V3D_Envelope getEnvelope(int oom) {
        if (en == null) {
            en = new V3D_Envelope(oom, getP(oom), getQ(oom));
        }
        return en;
    }

    /**
     * @param pt A point to test for intersection.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} if {@code this} is intersected by {@code p}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom) {
        boolean ei = getEnvelope(oom).isIntersectedBy(pt.getEnvelope(oom));
        if (ei) {
            if (super.isIntersectedBy(pt, oom)) {
                V3D_Point tp = getP(oom);
                Math_BigRationalSqrt a = pt.getDistance(oom, tp);
                if (a.getX().isZero()) {
                    return true;
                }
                V3D_Point tq = getQ(oom);
                Math_BigRationalSqrt b = pt.getDistance(oom, tq);
                if (b.getX().isZero()) {
                    return true;
                }
                Math_BigRationalSqrt l = tp.getDistance(oom, tq);
                Math_BigRationalSqrt apb = a.add(b, oom);
                if (apb == null) {
                    int oomt = oom - 2;
                    if (a.getSqrt(oomt).add(b.getSqrt(oomt)).compareTo(l.getSqrt(oomt)) != 1) {
                        return true;
                    }
                } else {
                    if (apb.compareTo(l) != 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param l A line segment to indicate intersection with this.
     * @param oom The Order of Magnitude for the calculation.
     * @param flag Used to distinguish this method from
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Line, int)}. The
     * value is ignored.
     * @return {@code true} iff {@code l} intersects with {@code this}.
     */
    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, boolean flag) {
        boolean ei = getEnvelope(oom).isIntersectedBy(l.getEnvelope(oom));
        if (ei) {
            return super.isIntersectedBy(l, oom);
        }
        return false;
    }

    /**
     * @param l A line to test for intersection within the specified tolerance.
     * @param oom The Order of Magnitude for the calculation.
     * @return true if p is within t of this given scale.
     */
    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        V3D_Geometry i = super.getIntersection(l, oom);
        if (i == null) {
            return false;
        }
        if (i instanceof V3D_Point v3D_Point) {
            return isIntersectedBy(v3D_Point, oom);
        } else {
            return true;
        }
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if the two line
     * segments do not intersect.
     *
     * @param l The line to get intersection with this.
     * @param oom The Order of Magnitude for the calculation.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        // Check if infinite lines intersect.
        V3D_Geometry i = new V3D_Line(this, oom).getIntersection(
                new V3D_Line(l, oom), oom);
        if (i == null) {
            // There is no intersection.
            return i;
        }
        /**
         * If infinite lines intersects at a point, then check this point is on
         * this.
         */
        if (i instanceof V3D_Point v3D_Point) {
            if (isIntersectedBy(v3D_Point, oom)) {
                return i;
            } else {
                return null;
            }
        } else if (i instanceof V3D_Line) {
            // If the lines are the same, then return this. 
            return this;
        } else {
            // There is no intersection.
            return null;
        }
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if the two line
     * segments do not intersect.
     *
     * @param l The line to get intersection with this.
     * @param oom The Order of Magnitude for the calculation.
     * @param flag To distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Line, int)}. The
     * value is ignored.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom, boolean flag) {
        V3D_Envelope ren = this.getEnvelope(oom).getIntersection(
                l.getEnvelope(oom));
        if (ren == null) {
            return null;
        }
        // Get intersection of infinite lines. 
        V3D_Geometry li = new V3D_Line(this, oom).getIntersection(
                new V3D_Line(l, oom), oom);
        if (li == null) {
            return null;
        }
        if (li instanceof V3D_Point) {
            return li;
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
        V3D_Point lp = l.getP(oom);
        V3D_Point lq = l.getQ(oom);
        if (this.isIntersectedBy(lp, oom)) {
            // Cases 1, 2, 5, 6, 14, 16
            if (this.isIntersectedBy(lq, oom)) {
                // Cases 2, 6, 14
                /**
                 * The line segments are effectively the same although the start
                 * and end points may be opposite.
                 */
                return l;
            } else {
                V3D_Point tp = getP(oom);
                // Cases 1, 5, 16
                if (l.isIntersectedBy(tp, oom)) {
                    // Cases 5
                    return new V3D_LineSegment(l.getP(), getP(), oom);
                } else {
                    // Cases 1, 16
                    return new V3D_LineSegment(l.getP(), getQ(), oom);
                }
            }
        } else {
            // Cases 3, 4, 7, 8, 9, 10, 11, 12, 13, 15
            if (this.isIntersectedBy(lq, oom)) {
                V3D_Point tp = getP(oom);
                // Cases 4, 8, 9, 10, 11
                if (l.isIntersectedBy(tp, oom)) {
                    // Cases 4, 11, 13
                    if (l.isIntersectedBy(getQ(oom), oom)) {
                        // Cases 11
                        return this;
                    } else {
                        // Cases 4, 13
                        return new V3D_LineSegment(getP(), l.getQ(), oom);
                    }
                } else {
                    // Cases 8, 9, 10
                    V3D_Point tq = getQ(oom);
                    if (l.isIntersectedBy(tq, oom)) {
                        // Cases 8, 9
                        return new V3D_LineSegment(getQ(), l.getQ(), oom);
                    } else {
                        // Cases 10                      
                        return l;
                    }
                }
            } else {
                // Cases 3, 7, 12, 15
                V3D_Point tp = getP(oom);
                if (l.isIntersectedBy(tp, oom)) {
                    // Cases 3, 12, 15
                    V3D_Point tq = getQ(oom);
                    if (l.isIntersectedBy(tq, oom)) {
                        // Cases 3, 15
                        return this;
                    } else {
                        // Cases 12                 
                        return new V3D_LineSegment(getP(), l.getP(), oom);
                    }
                } else {
                    // Cases 7
                    return this;
                }
            }
        }
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
        return getEnvelope(oom).isIntersectedBy(l, oom);
    }

    /**
     * If the distance from a point to the line is less than the distance of the
     * point from either end of the line and the distance from either end of the
     * line is greater than the length of the line then the distance is the
     * shortest of the distances from the point to the points at either end of
     * the line segment. In all other cases, the distance is the distance
     * between the point and the line.
     *
     * @param p A point for which the minimum distance from {@code this} is
     * returned.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance between this and {@code p}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        BigDecimal d = super.getDistance(p, oom);
        BigDecimal l = this.getLength(oom).toBigDecimal(oom);
        BigDecimal a = p.getDistance(getP(oom), oom);
        BigDecimal b = p.getDistance(getQ(oom), oom);
        if (d.compareTo(a) == -1 && d.compareTo(b) == -1 && a.compareTo(l) == 1
                && b.compareTo(l) == 1) {
            return a.min(b);
        }
        return d;
    }

    /**
     * @param l The line segment to return the distance from.
     * @param oom The Order of Magnitude for the calculation.
     * @return The distance from {@code this} to {@code l} at the {@code oom}
     * precision.
     */
    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        V3D_Geometry loi = getLineOfIntersection(l, oom);
        if (loi == null) {
            /**
             * Lines are parallel, so the distance is either from one end point
             * to the other line. Calculate them all and choose the minimum.
             */
            return Math_BigDecimal.min(getDistance(l.getP(oom), oom),
                    getDistance(l.getQ(oom), oom), l.getDistance(getP(oom), oom),
                    l.getDistance(getQ(oom), oom));
        } else {
            return loi.getDistance(l, oom);
        }
    }

    /**
     * Calculate and return the midpoint between p and q.
     *
     * @param oom The Order of Magnitude for the calculation.
     * @return the midpoint between p and q to the OOM precision.
     */
    public V3D_Point getMidpoint(int oom) {
        //BigDecimal l = getLength().toBigDecimal(oom);
        //V3D_Vector pmpq = v.divide(Math_BigRational.valueOf(l));
        V3D_Vector pmpq = getV(oom).divide(Math_BigRational.valueOf(2), oom);
        //return getP(oom).apply(pmpq, oom);
        return new V3D_Point(offset, getP().add(pmpq, oom), oom);
    }
}
