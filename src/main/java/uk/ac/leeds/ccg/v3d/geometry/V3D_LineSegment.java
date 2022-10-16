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
     * For storing the length of the line squared.
     */
    protected Math_BigRational len2;

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is cloned from.
     */
    public V3D_LineSegment(V3D_LineSegment l) {
        super(l.e);
        this.l = new V3D_Line(l.l);
    }

    /**
     * Create a new instance. {@link #offset} is set to {@link V3D_Vector#ZERO}.
     *
     * @param e What {@link #e} is set to.
     * @param p What {@link #p} is cloned from.
     * @param q What {@link #q} is cloned from.
     */
    public V3D_LineSegment(V3D_Environment e, V3D_Vector p, V3D_Vector q,
            int oom, RoundingMode rm) {
        this(e, V3D_Vector.ZERO, p, q, oom, rm);
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
            V3D_Vector q, int oom, RoundingMode rm) {
        super(e, offset);
        l = new V3D_Line(e, offset, p, q, oom, rm);
        this.q = q;
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #p} is cloned from.
     * @param q What {@link #q} is cloned from.
     */
    public V3D_LineSegment(V3D_Point p, V3D_Point q, int oom, RoundingMode rm) {
        super(p.e, p.offset);
        V3D_Point q2 = new V3D_Point(q);
        q2.setOffset(offset, oom, rm);
        l = new V3D_Line(e, offset, p.rel, q2.rel, oom, rm);
        this.q = q2.rel;
    }

    /**
     * Create a new instance that intersects all points.
     *
     * @param points Any number of collinear points, with two being different.
     */
    public V3D_LineSegment(int oom, RoundingMode rm, V3D_Point... points) {
        super(points[0].e, points[0].offset);
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
        return new V3D_Point(e, offset, q);
    }

    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     * @param oom The Order of Magnitude for the precision.
     */
    @Override
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        offset = offset.add(v, oom, rm);
        // As offset and l.offset are the same, do not double add!
        //l.offset = l.offset.add(v, oom, rm);
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
        super(l.e);
        this.l = new V3D_Line(l);
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_LineSegment(V3D_Envelope.LineSegment l, int oom, RoundingMode rm) {
        this(l.e, new V3D_Vector(l.p), new V3D_Vector(l.q), oom, rm);
    }

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
     * @return {@code true} iff {@code this} is equal to {@code l}. This ignores
     * the order of {@link #p} and {@link #q}.
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
            return this.l.isIntersectedBy(l.getQ(), oom, rm)
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
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The length of {@code this}.
     */
    public Math_BigRationalSqrt getLength(int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getLength2(oom, rm), oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The length of {@code this} squared.
     */
    public Math_BigRational getLength2(int oom, RoundingMode rm) {
        return getP().getDistanceSquared(getQ(), oom, rm);
    }

    /**
     * @return {@code new V3D_Envelope(start, end)}
     */
    @Override
    public V3D_Envelope getEnvelope(int oom, RoundingMode rm) {
        if (en == null) {
            en = new V3D_Envelope(e, oom, rm, getP(), getQ());
        }
        return en;
    }

    /**
     * @param pt A point to test for intersection.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} if {@code this} is intersected by {@code p}.
     */
    @Override
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
     * @param l A line to test for intersection within the specified tolerance.
     * @param oom The Order of Magnitude for the calculation.
     * @return true if p is within t of this given scale.
     */
    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom, RoundingMode rm) {
        V3D_Geometry i = this.l.getIntersection(l, oom, rm);
        if (i == null) {
            return false;
        }
        if (i instanceof V3D_Point v3D_Point) {
            return isIntersectedBy(v3D_Point, oom, rm);
        } else {
            return true;
        }
    }

    /**
     * @param l A line segment to test if it intersects with this.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} iff {@code l} intersects with {@code this}.
     */
    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, RoundingMode rm) {
        boolean ei = getEnvelope(oom, rm).isIntersectedBy(l.getEnvelope(oom, rm), oom, rm);
        if (ei) {
            return this.l.isIntersectedBy(l, oom, rm);
        }
        return false;
    }

    /**
     * @param r A ray to test if it intersects with this.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} iff {@code l} intersects with {@code this}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Ray r, int oom, RoundingMode rm) {
        return r.isIntersectedBy(this, oom, rm);
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
     * @param r The ray to get intersection with this.
     * @param oom The Order of Magnitude for the calculation.
     * @return The intersection between {@code this} and {@code r}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Ray r, int oom, RoundingMode rm) {
        return r.getIntersection(this, oom, rm);
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if the two line
     * segments do not intersect.
     *
     * @param ls The line to get intersection with this.
     * @param oom The Order of Magnitude for the calculation.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment ls, int oom, RoundingMode rm) {
        V3D_Envelope ren = getEnvelope(oom, rm).getIntersection(ls.getEnvelope(oom, rm), oom, rm);
        if (ren == null) {
            return null;
        }
        // Get intersection of infinite lines. 
        V3D_Geometry r = this.l.getIntersection(new V3D_Line(ls), oom, rm);
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
                        return new V3D_LineSegment(lp, tp, oom, rm);
                    }
                } else {
                    // Cases: 1, 2, 27, 28
                    if (tp.equals(lp, oom, rm)) {
                        // Cases: 1, 28
                        return tp;
                    } else {
                        // Cases: 2, 27
                        return new V3D_LineSegment(lp, tq, oom, rm);
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
                            return new V3D_LineSegment(lq, tp, oom, rm);
                        }
                    } else {
                        // Cases: 6, 7, 22, 
                        if (tp.equals(lq, oom, rm)) {
                            // Cases: 7, 22
                            return tp;
                        } else {
                            // Case: 6
                            return new V3D_LineSegment(tp, lq, oom, rm);
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
                        return new V3D_LineSegment(tq, lq, oom, rm);
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

//    @Override
//    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
//        return getEnvelope().isIntersectedBy(l, oom);
//    }
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
    public BigDecimal getDistance(V3D_Point p, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
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
    public Math_BigRational getDistanceSquared(V3D_Point p, int oom, RoundingMode rm) {
        if (this.isIntersectedBy(p, oom, rm)) {
            return Math_BigRational.ZERO;
        }
        Math_BigRational a = p.getDistanceSquared(getP(), oom, rm);
        Math_BigRational b = p.getDistanceSquared(getQ(), oom, rm);

        V3D_Point loi = l.getPointOfIntersection(p, oom, rm);
        if (isIntersectedBy(loi, oom, rm)) {
            return loi.getDistanceSquared(p, oom, rm);
        } else {
            return a.min(b);
        }
//        
//        V3D_Line tl = new V3D_Line(this);
//        if (tl.isIntersectedBy(p, oom)) {
//            return a.min(b);
//        }
//        Math_BigRational d = super.getDistanceSquared(p, oom);
//        Math_BigRational l = this.getLength2(oom);
//        if (d.compareTo(a) == -1 && d.compareTo(b) == -1 && a.compareTo(l) == 1
//                && b.compareTo(l) == 1) {
//            return a.min(b);
//        }
//        return d;
    }

    /**
     * @param l The line segment to return the distance from.
     * @param oom The Order of Magnitude for the calculation.
     * @return The distance from {@code this} to {@code l} at the {@code oom}
     * precision.
     */
    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
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
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (isIntersectedBy(l, oom, rm)) {
            return Math_BigRational.ZERO;
        }
        V3D_Geometry loi = getLineOfIntersection(l, oom, rm);
        if (loi == null) {
            /**
             * Lines are parallel, so the distance is either from one end point
             * to the other line. Calculate them all and choose the minimum.
             */
            return Math_BigRational.min(
                    getDistanceSquared(l.getP(), oom, rm),
                    getDistanceSquared(l.getQ(), oom, rm),
                    l.getDistanceSquared(getP(), oom, rm),
                    l.getDistanceSquared(getQ(), oom, rm));
        } else {
            if (loi instanceof V3D_Point loip) {
                /**
                 * The two lines intersect at a point that is on one of the
                 * lines, so the shortest distance is from that point to the
                 * non-intersection line segment.
                 */
                if (loip.isIntersectedBy(l, oom, rm)) {
                    return loip.getDistanceSquared(this, oom, rm);
                } else {
                    return loip.getDistanceSquared(l, oom, rm);
                }
            }
            //return loi.getDistanceSquared(l, oom);
            return ((V3D_LineSegment) loi).getLength2(oom, rm);
        }
    }

    /**
     * Calculate and return the midpoint between p and q.
     *
     * @param oom The Order of Magnitude for the calculation.
     * @return the midpoint between p and q to the OOM precision.
     */
    public V3D_Point getMidpoint(int oom, RoundingMode rm) {
        //BigDecimal l = getLength().toBigDecimal(oom);
        //V3D_Vector pmpq = v.divide(Math_BigRational.valueOf(l));
        //V3D_Vector pmpq = l.getV(oom, rm).divide(Math_BigRational.valueOf(2), oom, rm);
        V3D_Vector pmpq = l.v.divide(Math_BigRational.valueOf(2), oom, rm);
        //return getP(oom).translate(pmpq, oom);
        return new V3D_Point(e, offset, l.p.add(pmpq, oom, rm));
    }

    /**
     * For returning the other end of the line segment as a point.
     *
     * @param pt A point equal to either {@link #getP()} or {@link #getQ()}.
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
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The line of intersection between {@code this} and {@code l}.
     */
    public V3D_FiniteGeometry getLineOfIntersection(V3D_Line l, int oom, RoundingMode rm) {
        V3D_Line tl = new V3D_Line(this);
        V3D_Geometry loi = tl.getLineOfIntersection(l, oom, rm);
        /**
         * If the loi is a line segment with one point on this and one point on
         * l, then this is a success. However, if one of the points is not on
         * this, then the other point is that point of this closest to that
         * point.
         */
        if (loi == null) {
            return null;
        }
        if (loi instanceof V3D_Point loip) {
            if (loip.isIntersectedBy(this, oom, rm)) {
                return null;
            } else {
                return l.getLineOfIntersection(getNearestPoint(this, loip, oom, rm), oom, rm);
            }
        } else {
            V3D_LineSegment loil = (V3D_LineSegment) loi;
            if (loil.isIntersectedBy(this, oom, rm)) {
                return loil;
            } else {
                V3D_Point loilp = loil.getP();
                //V3D_Point loilq = loil.getQ(oom);
                V3D_Point tp = getP();
                V3D_Point tq = getQ();
                if (isIntersectedBy(loilp, oom, rm)) {
                    return new V3D_LineSegment(tp, l.getPointOfIntersection(tp, oom, rm), oom, rm);
                } else {
                    return new V3D_LineSegment(tq, l.getPointOfIntersection(tq, oom, rm), oom, rm);
                }
            }
        }
    }

    /**
     * Get the line of intersection (the shortest line) between {@code this} and
     * {@code l}.
     *
     * @param ls The line segment to get the line of intersection with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The line of intersection between {@code this} and {@code l}.
     */
    public V3D_FiniteGeometry getLineOfIntersection(V3D_LineSegment ls, int oom, RoundingMode rm) {
        V3D_FiniteGeometry tloi = l.getLineOfIntersection(ls.l, oom, rm);
        V3D_Line ll = new V3D_Line(ls);
        V3D_Geometry lloi = getLineOfIntersection(ll, oom, rm);
        if (tloi == null) {
            if (lloi == null) {
                return null;
            } else if (lloi instanceof V3D_Point lloip) {
                return new V3D_LineSegment(getNearestPoint(this, lloip, oom, rm), lloip, oom, rm);
            } else {
                // lloi instanceof V3D_LineSegment
                return null;
            }
        } else if (tloi instanceof V3D_Point tloip) {
            if (lloi == null) {
                return new V3D_LineSegment(getNearestPoint(ls, tloip, oom, rm), tloip, oom, rm);
            } else {
                //V3D_Point lp = ls.getP(oom);
                //V3D_Point lq = ls.getQ(oom);
                if (lloi instanceof V3D_Point lloip) {
                    return getGeometry(lloip, tloip, oom, rm);
                } else {
                    V3D_LineSegment lloil = (V3D_LineSegment) lloi;
                    V3D_Point lloilp = lloil.getP();
                    V3D_Point lloilq = lloil.getQ();
                    if (isIntersectedBy(lloilp, oom, rm)) {
                        return new V3D_LineSegment(lloilp, getNearestPoint(ls, lloilq, oom, rm), oom, rm);
                    } else {
                        return new V3D_LineSegment(lloilq, getNearestPoint(ls, lloilp, oom, rm), oom, rm);
                    }
                }
            }
        } else {
            if (lloi == null) {
                return null;
            } else {
                V3D_LineSegment tloil = (V3D_LineSegment) tloi;
                V3D_Point tloilp = tloil.getP();
                V3D_Point tloilq = tloil.getQ();
                if (lloi instanceof V3D_Point) {
                    if (isIntersectedBy(tloilp, oom, rm)) {
                        return new V3D_LineSegment(tloilp, getNearestPoint(this, tloilq, oom, rm), oom, rm);
                    } else {
                        return new V3D_LineSegment(tloilq, getNearestPoint(this, tloilp, oom, rm), oom, rm);
                    }
                } else {
                    V3D_LineSegment lloil = (V3D_LineSegment) lloi;
                    V3D_Point lloilp = lloil.getP();
                    V3D_Point lloilq = lloil.getQ();
                    if (l.isIntersectedBy(tloilp, oom, rm)) {
                        if (ll.isIntersectedBy(lloilp, oom, rm)) {
                            return getGeometry(getNearestPoint(ls, lloilp, oom, rm), tloilp, oom, rm);
                        } else {
                            return getGeometry(getNearestPoint(ls, lloilq, oom, rm),
                                    getNearestPoint(this, tloilq, oom, rm), oom, rm);
                        }
                    } else {
                        if (ll.isIntersectedBy(lloilp, oom, rm)) {
                            return getGeometry(getNearestPoint(ls, lloilp, oom, rm), tloilq, oom, rm);
                        } else {
                            return getGeometry(getNearestPoint(ls, lloilq, oom, rm), tloilp, oom, rm);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param l A line segment.
     * @param p Is on the line of {@code l}, but not on {@code l}.
     * @return The nearest point on {@code l} to {@code p}.
     */
    protected static V3D_Point getNearestPoint(V3D_LineSegment l, V3D_Point p, int oom, RoundingMode rm) {
        V3D_Point lp = l.getP();
        V3D_Point lq = l.getQ();
        Math_BigRational dlpp = lp.getDistanceSquared(p, oom, rm);
        Math_BigRational dlqp = lq.getDistanceSquared(p, oom, rm);
        if (dlpp.compareTo(dlqp) == -1) {
            return lp;
        } else {
            return lq;
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Plane p, int oom, RoundingMode rm) {
        return p.isIntersectedBy(this, oom, rm);
    }

    @Override
    public boolean isIntersectedBy(V3D_Triangle t, int oom, RoundingMode rm) {
        return t.isIntersectedBy(this, oom, rm);
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        return t.isIntersectedBy(this, oom, rm);
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Plane p, int oom, RoundingMode rm) {
        return p.getIntersection(this, oom, rm);
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Triangle t, int oom, RoundingMode rm) {
        return t.getIntersection(this, oom, rm);
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        return t.getIntersection(this, oom, rm);
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
//        V3D_Geometry i = super.getLineOfIntersection(l, oom);
//        if (i instanceof V3D_Point pt) {
//            if (this.isIntersectedBy(pt, e.oom)) {
//                return BigDecimal.ZERO;
//            }
//        }
//        Math_BigRational ld2 = super.getDistanceSquared(l, oom);
//        Math_BigRational pld2 = getP(oom).getDistanceSquared(l, oom);
//        Math_BigRational qld2 = getQ(oom).getDistanceSquared(l, oom);
//        if (ld2.compareTo(pld2) == -1) {
//            if (ld2.compareTo(qld2) == -1) {
//            }
//        }
//
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Line line, int oom, RoundingMode rm) {
        if (isIntersectedBy(line, oom, rm)) {
            return Math_BigRational.ZERO;
        }
        V3D_FiniteGeometry i = l.getLineOfIntersection(line, oom, rm); //V3D_Geometry i = (new V3D_Line(this)).getLineOfIntersection(l, oom);
        if (i == null) {
            return getP().getDistanceSquared(line, oom, rm)
                    .min(getQ().getDistanceSquared(line, oom, rm));
        }
        if (i instanceof V3D_Point pt) {
            if (this.isIntersectedBy(pt, oom, rm)) {
                return Math_BigRational.ZERO;
            }
        } else {
            V3D_LineSegment il = (V3D_LineSegment) i;
            if (this.isIntersectedBy(il.getP(), oom, rm)) {
                return il.getLength2(oom, rm);
            }
        }
        Math_BigRational pld2 = getP().getDistanceSquared(line, oom, rm);
        Math_BigRational qld2 = getQ().getDistanceSquared(line, oom, rm);
        return pld2.min(qld2);
    }

    @Override
    public BigDecimal getDistance(V3D_Plane p, int oom, RoundingMode rm) {
        return p.getDistance(this, oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Plane p, int oom, RoundingMode rm) {
        return p.getDistanceSquared(this, oom, rm);
    }

    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom, RoundingMode rm) {
        return t.getDistance(this, oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom, RoundingMode rm) {
        return t.getDistanceSquared(this, oom, rm);
    }

    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        return t.getDistance(this, oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        return t.getDistanceSquared(this, oom, rm);
    }

    @Override
    public V3D_LineSegment rotate(V3D_Vector axisOfRotation, Math_BigRational theta, int oom, RoundingMode rm) {
        return new V3D_LineSegment(getP().rotate(axisOfRotation, theta, oom, rm),
                getQ().rotate(axisOfRotation, theta, oom, rm), oom, rm);
    }

    @Override
    public BigDecimal getDistance(V3D_Ray r, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Ray r, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Clips this using pl and returns the part that is on the same side as pt.
     *
     * @param pl The plane that clips.
     * @param p A point that is used to return the side of the clipped triangle.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometry clip(V3D_Plane pl, V3D_Point pt, int oom, RoundingMode rm) {
        V3D_FiniteGeometry i = getIntersection(pl, oom, rm);
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
