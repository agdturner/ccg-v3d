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
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;

/**
 * 3D representation of a finite length line (a line segment). The line begins
 * at the point of {@link #l} and ends at the point {@link #q}. The "*" denotes a
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
public class V3D_LineSegment extends V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The line of which this segment is part.
     */
    public final V3D_Line l;

    /**
     * For defining the end point of the line segment which is given relative to
     * the {@link #offset}.
     */
    protected V3D_Vector q;

    /**
     * For storing the plane at l.getP() with a normal given l.v.
     */
    protected V3D_Plane ppl;

    /**
     * For storing the plane at getQ() with a normal given by l.v.
     */
    protected V3D_Plane qpl;

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
        super(l.offset);
        this.l = new V3D_Line(l.l);
    }

    /**
     * Create a new instance. {@link #offset} is set to {@link V3D_Vector#ZERO}.
     *
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #q} is cloned from.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_LineSegment(V3D_Vector p, V3D_Vector q,
            int oom, RoundingMode rm) {
        this(V3D_Vector.ZERO, p, q, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param offset What {@link #offset} is set to.
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #q} is cloned from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_LineSegment(V3D_Vector offset, V3D_Vector p,
            V3D_Vector q, int oom, RoundingMode rm) {
        super(offset);
        l = new V3D_Line(offset, p, q, oom, rm);
        this.q = q;
    }

    /**
     * Create a new instance.
     *
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #q} is cloned from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_LineSegment(V3D_Point p, V3D_Point q, int oom, RoundingMode rm) {
        super(p.offset);
        V3D_Point q2 = new V3D_Point(q);
        q2.setOffset(offset, oom, rm);
        l = new V3D_Line(offset, p.rel, q2.rel, oom, rm);
        this.q = q2.rel;
    }

    /**
     * Create a new instance that intersects all points.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points Any number of collinear points, with two being different.
     */
    public V3D_LineSegment(int oom, RoundingMode rm, V3D_Point... points) {
        super(points[0].offset);
        V3D_Point p0 = points[0];
        V3D_Point p1 = points[1];
        V3D_LineSegment ls = new V3D_LineSegment(p0, p1, oom, rm);
        for (int i = 2; i < points.length; i++) {
            if (!ls.isIntersectedBy(points[i], oom, rm)) {
                V3D_LineSegment l2 = new V3D_LineSegment(ls.getP(), points[i], oom, rm);
                V3D_Point lq = ls.getQ();
                if (l2.isIntersectedBy(lq, oom, rm)) {
                    ls = l2;
                } else {
                    ls = new V3D_LineSegment(ls.getQ(), points[i], oom, rm);
                }
            }
        }
        this.l = ls.l;
        this.q = ls.q;
    }

    /**
     * @return {@link #l} p with {@link #l} offset applied.
     */
    public V3D_Point getP() {
        return l.getP();
    }

    /**
     * @return {@link #q} with {@link #offset} applied.
     */
    public V3D_Point getQ() {
        //return l.getQ(oom, rm);
        return new V3D_Point(offset, q);
    }

    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    @Override
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        l.translate(v, oom, rm);
        if (ppl != null) {
            ppl.translate(v, oom, rm);
        }
        if (qpl != null) {
            qpl.translate(v, oom, rm);
        }
    }

    @Override
    public V3D_Point[] getPoints(int oom, RoundingMode rm) {
        V3D_Point[] r = new V3D_Point[2];
        r[0] = getP();
        r[1] = getQ();
        return r;
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_LineSegment(V3D_Line l) {
        super();
        this.l = new V3D_Line(l);
    }

//    /**
//     * Create a new instance.
//     *
//     * @param l What {@code this} is created from.
//     */
//    public V3D_LineSegment(V3D_Envelope.LineSegment l, int oom, RoundingMode rm) {
//        this(l.e, new V3D_Vector(l.p), new V3D_Vector(l.q), oom, rm);
//    }
    @Override
    public String toString() {
        //return toString("");
        return toStringSimple("");
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toString(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFields(pad + " ") + "\n"
                + pad + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toStringSimple(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFieldsSimple(pad + " ") + "\n"
                + pad + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFields(String pad) {
        String r = super.toStringFields(pad) + "\n"
                + pad + ",\n";
        r += pad + "l=" + l.toStringFields(pad) + "\n"
                + pad + ",\n"
                + pad + "q=" + q.toStringFields(pad);
//        if (l.q == null) {
//            r += pad + "p=" + l.getP().toString(pad) + "\n"
//                    + pad + ",\n"
//                    + pad + "q=null" + "\n"
//                    + pad + ",\n"
//                    + pad + "v=" + l.v.toString(pad);
//        } else {
//            if (l.v == null) {
//                r += pad + "p=" + l.getP().toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "q=" + l.q.toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "v=null";
//            } else {
//                r += pad + "p=" + l.getP().toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "q=" + l.q.toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "v=" + l.v.toString(pad);
//            }
//        }
        return r;
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFieldsSimple(String pad) {
        String r = super.toStringFieldsSimple(pad) + ",\n";
        r += pad + "l=" + l.toStringFieldsSimple(pad) + ",\n"
                + pad + "q=" + q.toStringFieldsSimple("");
//        if (l.q == null) {
//            r += pad + "p=" + l.getP().toStringSimple("") + ",\n"
//                    + pad + "q=null" + ",\n"
//                    + pad + "v=" + l.v.toStringSimple(pad);
//        } else {
//            if (l.v == null) {
//                r += pad + "p=" + l.getP().toStringSimple(pad) + ",\n"
//                        + pad + "q=" + l.q.toStringSimple(pad) + ",\n"
//                        + pad + "v=null";
//            } else {
//                r += pad + "p=" + l.getP().toStringSimple(pad) + ",\n"
//                        + pad + "q=" + l.q.toStringSimple(pad) + ",\n"
//                        + pad + "v=" + l.v.toStringSimple(pad);
//            }
//        }
        return r;
    }

    /**
     * @param l The line segment to test if it is the same as {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_LineSegment l, int oom, RoundingMode rm) {
        //return getP().equals(l.getP(), oom, rm) && getQ(oom, rm).equals(l.getQ(oom, rm), oom, rm);
        if (equalsIgnoreDirection(l, oom, rm)) {
            return this.l.getP().equals(l.getP(), oom, rm);
        }
        return false;
    }

    /**
     * @param l The line segment to test if it is equal to {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} is equal to {@code l}. This ignores
     * the order of the point of {@link #l} and {@link #q}.
     */
    public boolean equalsIgnoreDirection(V3D_LineSegment l, int oom, RoundingMode rm) {
//        if (equals(l, oom, rm)) {
//            return true;
//        } else {
////            return getP().equals(l.getQ(oom, rm), oom, rm) 
////                    && getQ(oom, rm).equals(l.getP(), oom, rm);
//            return getP().equals(l.getQ(oom, rm), oom, rm) 
//                    && getQ(oom, rm).equals(l.getP(), oom, rm);
//        }
        if (this.l.equals(l.l, oom, rm)) {
            return isIntersectedBy(l.getQ(), oom, rm)
                    && l.isIntersectedBy(getQ(), oom, rm);
        }
        return false;
    }

//    /**
//     * @param v The vector to translate.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return a new line.
//     */
//    @Override
//    public V3D_LineSegment translate(V3D_Vector v, int oom) {
//        V3D_LineSegment l = new V3D_LineSegment(this, oom);
//        l.translate(oom, v);
//        return l;
//    }
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The length of {@code this}.
     */
    public Math_BigRationalSqrt getLength(int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getLength2(oom, rm), oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The length of {@code this} squared.
     */
    public Math_BigRational getLength2(int oom, RoundingMode rm) {
        return getP().getDistanceSquared(getQ(), oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code new V3D_Envelope(start, end)}
     */
    @Override
    public V3D_Envelope getEnvelope(int oom, RoundingMode rm) {
        if (en == null) {
            en = new V3D_Envelope(oom, rm, getP(), getQ());
        }
        return en;
    }

    /**
     * @param pt A point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V3D_Point pt, int oom, RoundingMode rm) {
        boolean ei = getEnvelope(oom, rm).isIntersectedBy(pt.getEnvelope(oom, rm), oom, rm);
        if (ei) {
            if (l.isIntersectedBy(pt, oom, rm)) {
                V3D_Point tp = getP();
                Math_BigRationalSqrt a = pt.getDistance(oom, rm, tp);
                if (a.getX().isZero()) {
                    return true;
                }
                V3D_Point tq = getQ();
                Math_BigRationalSqrt b = pt.getDistance(oom, rm, tq);
                if (b.getX().isZero()) {
                    return true;
                }
                Math_BigRationalSqrt d = tp.getDistance(oom, rm, tq);
                Math_BigRationalSqrt apb = a.add(b, oom, rm);
                if (apb == null) {
                    int oomt = oom - 2;
                    if (a.getSqrt(oomt, rm).add(b.getSqrt(oomt, rm)).compareTo(d.getSqrt(oomt, rm)) != 1) {
                        return true;
                    }
                } else {
                    if (apb.compareTo(d) != 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if the two line
     * segments do not intersect.
     *
     * @param l The line to get intersection with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Line l, int oom, RoundingMode rm) {
        // Check if infinite lines intersect.
        V3D_Geometry i = this.l.getIntersection(l, oom, rm);
        if (i == null) {
            // There is no intersection.
            return null;
        }
        /**
         * If infinite lines intersects at a point, then check this point is on
         * this.
         */
        if (i instanceof V3D_Point v3D_Point) {
            if (isIntersectedBy(v3D_Point, oom, rm)) {
                return v3D_Point;
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
     * @param ls The line to get intersection with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment ls, int oom, RoundingMode rm) {
        if (!getEnvelope(oom, rm).isIntersectedBy(ls.getEnvelope(oom, rm), oom, rm)) {
            return null;
        }
        // Get intersection of infinite lines. 
        V3D_Geometry r = this.l.getIntersection(ls.l, oom, rm);
        if (r == null) {
            return null;
        }
        if (r instanceof V3D_Point rp) {
            return rp;
        }
        /**
         * Check the type of intersection. {@code
         * A) p_q l.p_l.q
         * 1)  p +----------+ q
         *              l.p +----------+ l.q
         * 2)  p +----------+ q
         *         l.p +----------+ l.q
         * 3)  p +------------------------+ q
         *         l.p +----------+ l.q
         * 4)        p +----------+ q
         *     l.p +----------------------+ l.q
         * 5)    p +----------+ q
         *     l.p +----------+ l.q
         * 6)         p +----------+ q
         *     l.p +----------+ l.q
         * 7)              p +----------+ q
         *     l.p +---------+ l.q
         * B) q_p l.p_l.q
         * 8)  q +----------+ p
         *              l.p +----------+ l.q
         * 9)  q +----------+ p
         *         l.p +----------+ l.q
         * 10) q +------------------------+ p
         *         l.p +----------+ l.q
         * 11)       q +----------+ p
         *     l.p +----------------------+ l.q
         * 12)       q +----------+ p
         *         l.p +----------+ l.q
         * 13)       q +----------+ p
         *     l.p +----------+ l.q
         * 14)              q +----------+ p
         *     l.p +----------+ l.q
         * C) p_q l.q_l.p
         * 15) p +----------+ q
         *         l.q +----------+ l.p
         * 16) p +----------+ q
         *              l.q +----------+ l.p
         * 17) p +------------------------+ q
         *         l.q +----------+ l.p
         * 18)       p +----------+ q
         *     l.q +------------------------+ l.p
         * 19)       p +----------+ q
         *         l.q +----------+ l.p
         * 20)       p +----------+ q
         *     l.q +----------+ l.p
         * 21)              p +----------+ q
         *     l.q +----------+ l.p
         * D) q_p l.q_l.p
         * 22) q +----------+ p
         *              l.q +----------+ l.p
         * 23) q +----------+ p
         *         l.q +----------+ l.p
         * 24) q +------------------------+ p
         *         l.q +----------+ l.p
         * 25)       q +----------+ p
         *     l.q +------------------------+ l.p
         * 26)       p +----------+ q
         *         l.q +---------+ l.p
         * 27)       q +----------+ p
         *    l.q +---------+ l.p
         * 28)            q +----------+ p
         *    l.q +---------+ l.p
         * }
         */
        V3D_Point lp = ls.getP();
        V3D_Point lq = ls.getQ();
        V3D_Point tp = getP();
        V3D_Point tq = getQ();
        if (this.isIntersectedBy(lp, oom, rm)) {
            // Cases: 1, 2, 3, 5, 8, 9, 10, 12, 17, 19, 20, 21, 24, 26, 27, 28
            if (this.isIntersectedBy(lq, oom, rm)) {
                // Cases: 3, 5, 10, 12, 17, 19, 24, 26
                return ls;
//                if (l.isIntersectedBy(tp, oom)) {
//                    // Cases: 5, 12, 19, 26
//                    if (l.isIntersectedBy(tq, oom)) {
//                        // Cases: 5, 12, 19, 26
//                        return l;
//                    } else {
//                        // Cases: 
//                    }
//                } else {
//                    // Cases: 3, 10, 17, 24
//                    if (l.isIntersectedBy(tq, oom)) {
//                        // Cases: 3, 10, 17, 24
//                        return l;
//                    } else {
//                        // Cases: 
//                    }
//                }
            } else {
                // Cases: 1, 2, 8, 9, 20, 21, 27, 28
                if (ls.isIntersectedBy(tp, oom, rm)) {
                    // Cases: 8, 9, 20, 21
                    if (tp.equals(lp, oom, rm)) {
                        // Cases: 8, 21
                        return tp;
                    } else {
                        // Cases: 9, 20
                        return V3D_LineSegment.getGeometry(lp, tp, oom, rm);
                    }
                } else {
                    // Cases: 1, 2, 8, 21, 27, 28
                    if (tp.equals(lp, oom, rm)) {
                        // Cases: 8, 21
                        return tp;
                    } else {
                        // Case: 1, 28
                        if (lp.equals(tq, oom, rm)) {
                            return lp;
                        }
                        // Cases: 2, 27
                        return V3D_LineSegment.getGeometry(lp, tq, oom, rm);
                    }
                }
            }
        } else {
            // Cases: 4, 6, 7, 11, 13, 14, 16, 18, 19, 22, 23, 25
            if (this.isIntersectedBy(lq, oom, rm)) {
                // Cases: 6, 7, 13, 14, 16, 19, 22, 23
                if (ls.isIntersectedBy(tp, oom, rm)) {
                    // Cases: 6, 7, 19, 22, 23
                    if (ls.isIntersectedBy(tq, oom, rm)) {
                        // Cases: 19, 23
                        if (tp.equals(lp, oom, rm)) {
                            // Case: 19
                            return this;
                        } else {
                            // Case: 23
                            return V3D_LineSegment.getGeometry(lq, tp, oom, rm);
                        }
                    } else {
                        // Cases: 6, 7, 22, 
                        if (tp.equals(lq, oom, rm)) {
                            // Cases: 7, 22
                            return tp;
                        } else {
                            // Case: 6
                            return V3D_LineSegment.getGeometry(tp, lq, oom, rm);
                        }
                    }
                } else {
                    // Cases: 13, 14, 16
                    //if (l.isIntersectedBy(tq, oom)) {
                    // Cases: 13, 14, 16
                    if (tq.equals(lq, oom, rm)) {
                        // Cases: 14, 16
                        return tq;
                    } else {
                        // Case: 13
                        //return new V3D_LineSegment(e, l.q, ls.l.q);
                        return V3D_LineSegment.getGeometry(tq, lq, oom, rm);
                    }
//                    } else {
//                        // Cases:                    
//                        return l;
                    //}
                }
            } else {
                // Cases: 4, 11, 18, 25
                return this;
//                if (l.isIntersectedBy(tp, oom)) {
//                    // Cases: 4, 11, 18, 25
//                    if (l.isIntersectedBy(tq, oom)) {
//                        // Cases: 4, 11, 18, 25
//                        return this;
//                    } else {
//                        // Cases:
//                    }
//                } else {
//                    // Cases:
//                }
            }
        }
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance between this and {@code p}.
     */
    public BigDecimal getDistance(V3D_Point p, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    /**
     * If the distance from a point to the line is less than the distance of the
     * point from either end of the line and the distance from either end of the
     * line is greater than the length of the line then the distance is the
     * shortest of the distances from the point to the points at either end of
     * the line segment. In all other cases, the distance is the distance
     * between the point and the line.
     *
     * @param pt A point for which the minimum distance from {@code this} is
     * returned.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance between this and {@code p}.
     */
    public Math_BigRational getDistanceSquared(V3D_Point pt, int oom, 
            RoundingMode rm) {
        if (isIntersectedBy(pt, oom, rm)) {
            return Math_BigRational.ZERO;
        }
        V3D_Point poi = l.getPointOfIntersection(pt, oom, rm);
        if (isAligned(poi, oom, rm)) {
            return poi.getDistanceSquared(pt, oom, rm);
        } else {
            return pt.getDistanceSquared(getP(), oom, rm).min(
                    pt.getDistanceSquared(getQ(), oom, rm));
        }
    }
    
    /**
     * Calculates and returns if pt is in line with this. It is in line if it is
     * between the planes defined by the ends of the line segment with the
     * normal vector as the vector of the line.
     * 
     * @param pt The point. 
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} If pt is in line with this.  
     */
    public boolean isAligned(V3D_Point pt, int oom, RoundingMode rm) {
        if (getPPL().isOnSameSide(pt, getQ(), oom, rm)) {
            return getQPL().isOnSameSide(pt, getP(), oom, rm);
        }
        return false;
    }
    
    /**
     * Calculates and returns if l is in line with this. It is in line if both 
     * end points of l are in line with this as according to
     * {@link #isAligned(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int, java.math.RoundingMode)}.
     * 
     * @param l The line segment. 
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} If pt is in line with this.  
     */
    public boolean isAligned(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (isAligned(l.getP(), oom, rm)) {
            return isAligned(l.getQ(), oom, rm);
        }
        return false;
    }
    
    /**
     * @param l The line segment to return the distance from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The distance from {@code this} to {@code l} at the {@code oom}
     * precision.
     */
    public BigDecimal getDistance(V3D_LineSegment l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code l}.
     */
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom, 
            RoundingMode rm) {
        if (getIntersection(l, oom, rm) != null) {
            return Math_BigRational.ZERO;
        }
        V3D_LineSegment loi = getLineOfIntersection(l, oom, rm);
        if (loi == null) {
            /**
             * Lines are parallel.
             */
            return getDistanceSquared(l.getP(), oom, rm);
        } else {
            return loi.getLength2(oom, rm);
        }
    }

    /**
     * Calculate and return the midpoint between p and q.
     *
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return the midpoint between p and q to the OOM precision.
     */
    public V3D_Point getMidpoint(int oom, RoundingMode rm) {
        //BigDecimal l = getLength().toBigDecimal(oom);
        //V3D_Vector pmpq = v.divide(Math_BigRational.valueOf(l));
        //V3D_Vector pmpq = l.getV(oom, rm).divide(Math_BigRational.valueOf(2), oom, rm);
        V3D_Vector pmpq = l.v.divide(Math_BigRational.valueOf(2), oom, rm);
        //return getP(oom).translate(pmpq, oom);
        return new V3D_Point(offset, l.p.add(pmpq, oom, rm));
    }

    /**
     * For returning the other end of the line segment as a point.
     *
     * @param pt A point equal to either {@link #getP()} or the point of 
     * {@link #l}.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The other point that is not equal to a.
     */
    public V3D_Point getOtherPoint(V3D_Point pt, int oom, RoundingMode rm) {
        if (getP().equals(pt, oom, rm)) {
            return getQ();
        } else {
            return pt;
        }
    }

    /**
     * If p and q are equal then the point is returned otherwise the line
     * segment is returned
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code p} or {@code new V3D_LineSegment(p, q)}
     */
    public static V3D_FiniteGeometry getGeometry(V3D_Point p, V3D_Point q,
            int oom, RoundingMode rm) {
        if (p.equals(q, oom, rm)) {
            return p;
        } else {
            return new V3D_LineSegment(p, q, oom, rm);
        }
    }

    /**
     * Get the line of intersection (the shortest line) between {@code this} and
     * {@code l}.
     *
     * @param l The line to get the line of intersection with.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The line of intersection between {@code this} and {@code l}.
     */
    public V3D_LineSegment getLineOfIntersection(V3D_Line l, int oom, RoundingMode rm) {
        if (getIntersection(l, oom, rm) != null) {
            return null;
        }
        V3D_LineSegment loi = this.l.getLineOfIntersection(l, oom, rm);
        V3D_Point tp = getP();
        V3D_Point tq = getQ();
        if (loi == null) {
            Math_BigRational pd = l.getDistanceSquared(tp, oom, rm);
            Math_BigRational qd = l.getDistanceSquared(tq, oom, rm);
            if (pd.compareTo(qd) == 1) {
                return new V3D_LineSegment(tq, l.getPointOfIntersection(tq, oom, rm), oom, rm);
            } else {
                return new V3D_LineSegment(tp, l.getPointOfIntersection(tp, oom, rm), oom, rm);
            }
        } else {
            V3D_Point lsp = loi.getP();
            //V3D_Point lsq = loi.getQ();
            V3D_Plane plp = new V3D_Plane(tp, l.v);
            V3D_Plane plq = new V3D_Plane(tq, l.v);
            if (plp.isOnSameSide(lsp, tq, oom, rm)) {
                if (plq.isOnSameSide(lsp, tp, oom, rm)) {
                    /**
                     * The line of intersection connects in the line segment, so
                     * return it.
                     */
                    return loi;
                } else {
                    return new V3D_LineSegment(tq, lsp, oom, rm);
                }
            } else {
                return new V3D_LineSegment(tp, lsp, oom, rm);
            }
        }
    }

    /**
     * Get the line of intersection (the shortest line) between {@code this} and
     * {@code l}.
     *
     * @param ls The line segment to get the line of intersection with.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The line of intersection between {@code this} and {@code l}.
     */
    public V3D_LineSegment getLineOfIntersection(V3D_LineSegment ls, int oom, RoundingMode rm) {
        V3D_FiniteGeometry ilsl = getIntersection(ls, oom, rm);
        if (ilsl == null) {
            V3D_Point lsp = ls.getP();
            V3D_Point lsq = ls.getQ();
            V3D_Point tp = getP();
            V3D_Point tq = getQ();
            // Get the line of intersection between this and ls.l
            V3D_LineSegment tloi = getLineOfIntersection(ls.l, oom, rm);
            V3D_LineSegment lsloi = ls.getLineOfIntersection(l, oom, rm);
            if (tloi == null) {
                V3D_Point tip;
                V3D_Point lsloiq = lsloi.getQ();
                // Is the intersection point on this within the line segment?
                // Can use isAligned to do this more clearly?
                V3D_Plane tppl = new V3D_Plane(tp, l.v);
                V3D_Plane tqpl = new V3D_Plane(tq, l.v);
                if (tppl.isOnSameSide(lsloiq, tq, oom, rm)) {
                    if (tqpl.isOnSameSide(lsloiq, tp, oom, rm)) {
                        /**
                         * The line of intersection connects in this, so lsloiq
                         * is one of the points wanted.
                         */
                        return lsloi;
                    } else {
                        // tq is closest.
                        tip = tq;
                    }
                } else {
                    // tp is closest.
                    tip = tp;
                }
                return new V3D_LineSegment(tip, lsloiq, oom, rm);
            } else {
                V3D_Point tloip = tloi.getP(); // This is the end of the line segment on this.
                V3D_Point tloiq = tloi.getQ();
                if (lsloi == null) {
                    V3D_Point lsip;
                    // Is the intersection point on ls within the line segment?
                    // Can use isAligned to do this more clearly?
                    V3D_Plane lsppl = new V3D_Plane(lsp, ls.l.v);
                    if (lsppl.isOnSameSide(tloiq, lsq, oom, rm)) {
                        V3D_Plane lsqpl = new V3D_Plane(lsq, ls.l.v);
                        if (lsqpl.isOnSameSide(tloiq, lsp, oom, rm)) {
                            /**
                             * The line of intersection connects in this, so
                             * lsloiq is one of the points wanted.
                             */
                            lsip = tloiq;
                        } else {
                            // lsq is closest.
                            lsip = lsq;
                        }
                    } else {
                        // lsp is closest.
                        lsip = lsp;
                    }
                    return new V3D_LineSegment(tloip, lsip, oom, rm);
                    //return new V3D_LineSegment(tq, lsip, oom, rm);
                    //return new V3D_LineSegment(tp, lsip, oom, rm);
                    //return new V3D_LineSegment(tloiq, getNearestPoint(this, tloiq, oom, rm), oom, rm);
                } else {
                    // tloip is on
                    if (isBetween(tloip, oom, rm)) {
                        return new V3D_LineSegment(tloip, getNearestPoint(ls, tloip, oom, rm), oom, rm);
                    } else {
                        return new V3D_LineSegment(
                                getNearestPoint(this, tloip, oom, rm),
                                getNearestPoint(ls, tloip, oom, rm), oom, rm);                        
                    }
                }
            }
        } else {
            return null;
        }
    }

    /**
     * Useful for intersection tests.
     * @return The plane with a point at l.getP() and normal l.v 
     */
    public V3D_Plane getPPL() {
        if (ppl == null) {
            ppl = new V3D_Plane(l.getP(), l.v);
        }
        return ppl;
    }
    
    /**
     * Useful for intersection tests.
     * @return The plane with a point at l.getQ() and normal l.v 
     */
    public V3D_Plane getQPL() {
        if (qpl == null) {
            qpl = new V3D_Plane(getQ(), l.v);
        }
        return qpl;
    }
    
    /**
     * Useful for intersection tests.
     * 
     * @param pt The point to test if it is between {@link #q} and the point of {@link #l}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff pt lies between the planes at the end of the 
     * line segment.
     */
    public boolean isBetween(V3D_Point pt, int oom, RoundingMode rm) {
        if (getPPL().isOnSameSide(pt, getQ(), oom, rm)) {
            if (getQPL().isOnSameSide(pt, getP(), oom, rm)) {
                return true;
            }        
        }
        return false;
    }
    
    /**
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param pt Is on the line of {@code l}, but not on {@code l}.
     * @return The nearest point on {@code l} to {@code p}.
     */
    protected static V3D_Point getNearestPoint(V3D_LineSegment l, V3D_Point pt, int oom, RoundingMode rm) {
        V3D_Point lp = l.getP();
        V3D_Point lq = l.getQ();
        Math_BigRational dlpp = lp.getDistanceSquared(pt, oom, rm);
        Math_BigRational dlqp = lq.getDistanceSquared(pt, oom, rm);
        if (dlpp.compareTo(dlqp) == -1) {
            return lp;
        } else {
            return lq;
        }
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigDecimal getDistance(V3D_Line l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code l}.
     */
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom, RoundingMode rm) {
        if (getIntersection(l, oom, rm) != null) {
            return Math_BigRational.ZERO;
        }
        V3D_LineSegment loi = getLineOfIntersection(l, oom, rm);
        if (loi == null) {
            // Lines are parallel.
            return l.getDistanceSquared(getP(), oom, rm);
        }
        return loi.getLength2(oom, rm);
    }

    @Override
    public V3D_LineSegment rotate(V3D_Line axis, Math_BigRational theta, 
            int oom, RoundingMode rm) {
        return new V3D_LineSegment(
                getP().rotate(axis, theta, oom, rm),
                getQ().rotate(axis, theta, oom, rm), oom, rm);
    }

    /**
     * Clips this using pl and returns the part that is on the same side as pt.
     *
     * @param pl The plane that clips.
     * @param pt A point that is used to return the side of the clipped triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometry clip(V3D_Plane pl, V3D_Point pt, int oom, RoundingMode rm) {
        V3D_FiniteGeometry i = pl.getIntersection(this, oom, rm);
        if (i == null) {
            if (pl.isOnSameSide(this.getP(), pt, oom, rm)) {
                return this;
            } else {
                return null;
            }
        } else if (i instanceof V3D_Point ip) {
            V3D_Point tp = this.getP();
            if (pl.isOnSameSide(tp, pt, oom, rm)) {
                V3D_Point tq = this.getQ();
                if (ip.equals(tq, oom, rm)) {
                    return ip;
                } else {
                    return new V3D_LineSegment(ip, tq, oom, rm);
                }
            } else {
                if (ip.equals(tp, oom, rm)) {
                    return ip;
                } else {
                    return new V3D_LineSegment(ip, tp, oom, rm);
                }
            }
        } else {
            return this;
        }
    }
}
