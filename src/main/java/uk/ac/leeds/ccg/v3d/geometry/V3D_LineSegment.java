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
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

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
     * @param l What {@code this} is cloned from.
     */
    public V3D_LineSegment(V3D_LineSegment l) {
        super(l);
    }

    /**
     * Create a new instance. {@link #offset} is set to {@link V3D_Vector#ZERO}.
     *
     * @param e What {@link #e} is set to.
     * @param p What {@link #p} is cloned from.
     * @param q What {@link #q} is cloned from.
     */
    public V3D_LineSegment(V3D_Environment e, V3D_Vector p, V3D_Vector q) {
        super(e, V3D_Vector.ZERO, p, q);
    }

    /**
     * Create a new instance.
     *
     * @param e What {@link #e} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #p} is cloned from.
     * @param q What {@link #q} is cloned from.
     */
    public V3D_LineSegment(V3D_Environment e, V3D_Vector offset, V3D_Vector p,
            V3D_Vector q) {
        super(e, offset, p, q);
    }
    
    /**
     * Create a new instance.
     *
     * @param p What {@link #p} is cloned from.
     * @param q What {@link #q} is cloned from.
     */
    public V3D_LineSegment(V3D_Point p, V3D_Point q) {
        super(p, q);
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_LineSegment(V3D_Line l) {
        super(l);
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_LineSegment(V3D_Envelope.LineSegment l) {
        this(l.e, new V3D_Vector(l.p), new V3D_Vector(l.q));
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
            V3D_Point tp = getP(e.oom);
            V3D_Point tq = getQ(e.oom);
            V3D_Point lp = l.getP(e.oom);
            V3D_Point lq = l.getQ(e.oom);
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
        return getP(e.oom).equals(l.getP(e.oom)) && getQ(e.oom).equals(l.getQ(e.oom));
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
            return getP(e.oom).equals(l.getQ(e.oom)) && getQ(e.oom).equals(l.getP(e.oom));
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
     * @return {@code new V3D_Envelope(start, end)}
     */
    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
            en = new V3D_Envelope(e, getP(e.oom), getQ(e.oom));
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
        boolean ei = getEnvelope().isIntersectedBy(pt.getEnvelope());
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
     * @param b Used to distinguish this method from
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Line, int)}. The
     * value is ignored.
     * @return {@code true} iff {@code l} intersects with {@code this}.
     */
    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, boolean b) {
        boolean ei = getEnvelope().isIntersectedBy(l.getEnvelope());
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
        V3D_Geometry i = new V3D_Line(this).getIntersection(
                new V3D_Line(l), oom);
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
     * @param b To distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Line, int)}. The
     * value is ignored.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom, boolean b) {
        V3D_Envelope ren = getEnvelope().getIntersection(l.getEnvelope());
        if (ren == null) {
            return null;
        }
        // Get intersection of infinite lines. 
        V3D_Geometry li = new V3D_Line(this).getIntersection(new V3D_Line(l),
                oom);
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
                    return new V3D_LineSegment(e, l.getP(), getP());
                } else {
                    // Cases 1, 16
                    return new V3D_LineSegment(e, l.getP(), getQ());
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
                        return new V3D_LineSegment(e, getP(), l.getQ());
                    }
                } else {
                    // Cases 8, 9, 10
                    V3D_Point tq = getQ(oom);
                    if (l.isIntersectedBy(tq, oom)) {
                        // Cases 8, 9
                        return new V3D_LineSegment(e, getQ(), l.getQ());
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
                        return new V3D_LineSegment(e, getP(), l.getP());
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
        return getEnvelope().isIntersectedBy(l, oom);
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
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
//        BigDecimal d = super.getDistance(p, oom);
//        BigDecimal l = this.getLength(oom).toBigDecimal(oom);
//        BigDecimal a = p.getDistance(getP(oom), oom);
//        BigDecimal b = p.getDistance(getQ(oom), oom);
//        if (d.compareTo(a) == -1 && d.compareTo(b) == -1 && a.compareTo(l) == 1
//                && b.compareTo(l) == 1) {
//            return a.min(b);
//        }
//        return d;
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
    public Math_BigRational getDistanceSquared(V3D_Point p, int oom) {
        Math_BigRational d = super.getDistanceSquared(p, oom);
        Math_BigRational l = this.getLength2(oom);
        Math_BigRational a = p.getDistanceSquared(getP(oom), oom);
        Math_BigRational b = p.getDistanceSquared(getQ(oom), oom);
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
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
//        V3D_Geometry loi = getLineOfIntersection(l, oom);
//        if (loi == null) {
//            /**
//             * Lines are parallel, so the distance is either from one end point
//             * to the other line. Calculate them all and choose the minimum.
//             */
//            return Math_BigDecimal.min(getDistance(l.getP(oom), oom),
//                    getDistance(l.getQ(oom), oom), l.getDistance(getP(oom), oom),
//                    l.getDistance(getQ(oom), oom));
//        } else {
//            return loi.getDistance(l, oom);
//        }
    }
    
    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom) {
        V3D_Geometry loi = getLineOfIntersection(l, oom);
        if (loi == null) {
            /**
             * Lines are parallel, so the distance is either from one end point
             * to the other line. Calculate them all and choose the minimum.
             */
            return Math_BigRational.min(
                    getDistanceSquared(l.getP(oom), oom),
                    getDistanceSquared(l.getQ(oom), oom),
                    l.getDistanceSquared(getP(oom), oom),
                    l.getDistanceSquared(getQ(oom), oom));
        } else {
            return loi.getDistanceSquared(l, oom);
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
        return new V3D_Point(e, offset, getP().add(pmpq, oom));
    }
    
    /**
     * If p and q are equal then the point is returned otherwise the line
     * segment is returned
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @return either {@code p} or {@code new V3D_LineSegment(p, q)}
     */
    public static V3D_Geometry getGeometry(V3D_Point p, V3D_Point q) {
        if (p.equals(q)) {
            return p;
        } else {
            return new V3D_LineSegment(p, q);
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Plane p, int oom) {
        return p.isIntersectedBy(this, oom, true);
    }

    @Override
    public boolean isIntersectedBy(V3D_Triangle t, int oom, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Triangle t, int oom, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        V3D_Geometry i = super.getLineOfIntersection(l, oom);
        if (i instanceof V3D_Point pt) {
            if (this.isIntersectedBy(pt, e.oom)) {
                return BigDecimal.ZERO;
            }
        }
        Math_BigRational ld2 = super.getDistanceSquared(l, oom);
        Math_BigRational pld2 = getP(oom).getDistanceSquared(l, oom);
        Math_BigRational qld2 = getQ(oom).getDistanceSquared(l, oom);
        if (ld2.compareTo(pld2) == -1) {
            if (ld2.compareTo(qld2) == -1){
            }
        }
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom) {
        V3D_Geometry i = super.getLineOfIntersection(l, oom);
        if (i instanceof V3D_Point pt) {
            if (this.isIntersectedBy(pt, e.oom)) {
                return Math_BigRational.ZERO;
            }
        } else {
            V3D_LineSegment il = (V3D_LineSegment) i;
            if (this.isIntersectedBy(il.getP(oom), oom)) {
                return il.getLength2(oom);
            }
        }
        Math_BigRational pld2 = getP(oom).getDistanceSquared(l, oom);
        Math_BigRational qld2 = getQ(oom).getDistanceSquared(l, oom);
        return pld2.min(qld2);
    }

    @Override
    public BigDecimal getDistance(V3D_Plane p, int oom) {
        return p.getDistance(this, oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Plane p, int oom) {
        return p.getDistanceSquared(this, oom);
    }

    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom) {
        return t.getDistance(this, oom);
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
