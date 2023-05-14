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

import uk.ac.leeds.ccg.math.arithmetic.Math_Double;
import uk.ac.leeds.ccg.v3d.geometry.*;

/**
 * 3D representation of a finite length line (a line segment). The line begins
 * at the point of {@link #l} and ends at the point {@link #qv}. The "*" denotes
 * a point in 3D and the line is shown with a line of "e" symbols in the
 * following depiction: {@code
 * z
 * y           -
 * +          /                * pv=<x0,y0,z0>
 * |         /                e
 * |        /                e
 * |    z0-/                e
 * |      /                e
 * |     /               e
 * |    /               e
 * |   /               e
 * y0-|  /               e
 * | /               e
 * |/         x1    e
 * x - ---------------------|-----------/---e---/---- + x
 * /|              e   x0
 * / |-y1          e
 * /  |           e
 * /   |          e
 * z1-/    |         e
 * /     |        e
 * /      |       * qv=<x1,y1,z1>
 * /       |
 * /        |
 * +         -
 * z          y
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineSegmentDouble extends V3D_FiniteGeometryDouble {

    private static final long serialVersionUID = 1L;

    /**
     * The line of which this line segment is part and for which the point on
     * the line is one end point of this segment.
     */
    public final V3D_LineDouble l;

    /**
     * For defining {@link q} which is given relative to {@link #offset}.
     */
    protected V3D_VectorDouble qv;

    /**
     * For defining the other end point of the line segment (the other given in
     * {@link #l}.
     */
    protected V3D_PointDouble q;

    /**
     * For storing the plane at l.getP() with a normal given l.v.
     */
    protected V3D_PlaneDouble ppl;

    /**
     * For storing the plane at getQ() with a normal given by l.v.
     */
    protected V3D_PlaneDouble qpl;

    /**
     * For storing the length of the line squared.
     */
    protected double len2;

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is cloned from.
     */
    public V3D_LineSegmentDouble(V3D_LineSegmentDouble l) {
        super(l.offset);
        this.l = new V3D_LineDouble(l.l);
    }

    /**
     * Create a new instance. {@link #offset} is set to {@link V3D_Vector#ZERO}.
     *
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #qv} is cloned from.
     */
    public V3D_LineSegmentDouble(V3D_VectorDouble p, V3D_VectorDouble q) {
        this(V3D_VectorDouble.ZERO, p, q);
    }

    /**
     * Create a new instance.
     *
     * @param offset What {@link #offset} is set to.
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #qv} is cloned from.
     */
    public V3D_LineSegmentDouble(V3D_VectorDouble offset, V3D_VectorDouble p,
            V3D_VectorDouble q) {
        super(offset);
        l = new V3D_LineDouble(offset, p, q);
        this.qv = q;
    }

    /**
     * Create a new instance.
     *
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #qv} is cloned from.
     */
    public V3D_LineSegmentDouble(V3D_PointDouble p, V3D_PointDouble q) {
        super(p.offset);
        V3D_PointDouble q2 = new V3D_PointDouble(q);
        q2.setOffset(offset);
        l = new V3D_LineDouble(offset, p.rel, q2.rel);
        this.qv = q2.rel;
    }

    /**
     * Create a new instance that intersects all points.
     *
     * @param points Any number of collinear points, with two being different.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V3D_LineSegmentDouble(double epsilon, V3D_PointDouble... points) {
        super(points[0].offset);
        V3D_PointDouble p0 = points[0];
        V3D_PointDouble p1 = points[1];
        V3D_LineSegmentDouble ls = new V3D_LineSegmentDouble(p0, p1);
        for (int i = 2; i < points.length; i++) {
            if (!ls.isIntersectedBy(points[i], epsilon)) {
                V3D_LineSegmentDouble l2 = new V3D_LineSegmentDouble(ls.getP(), points[i]);
                V3D_PointDouble lq = ls.getQ();
                if (l2.isIntersectedBy(lq, epsilon)) {
                    ls = l2;
                } else {
                    ls = new V3D_LineSegmentDouble(ls.getQ(), points[i]);
                }
            }
        }
        this.l = ls.l;
        this.qv = ls.qv;
    }

    /**
     * @return {@link #l} pv with {@link #l} offset applied.
     */
    public V3D_PointDouble getP() {
        return l.getP();
    }

    /**
     * @return {@link #qv} with {@link #offset} applied.
     */
    public V3D_PointDouble getQ() {
        if (q == null) {
            q = new V3D_PointDouble(offset, qv);
        }
        return q;
    }

    /**
     * Translate (move relative to the origin).
     *
     */
    @Override
    public void translate(V3D_VectorDouble v) {
        super.translate(v);
        l.translate(v);
        if (q != null) {
            q.translate(v);
        }
        if (ppl != null) {
            ppl.translate(v);
        }
        if (qpl != null) {
            qpl.translate(v);
        }
    }

    @Override
    public V3D_PointDouble[] getPoints() {
        V3D_PointDouble[] r = new V3D_PointDouble[2];
        r[0] = getP();
        r[1] = getQ();
        return r;
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_LineSegmentDouble(V3D_LineDouble l) {
        super();
        this.l = new V3D_LineDouble(l);
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
                + pad + "q=" + qv.toStringFields(pad);
//        if (l.qv == null) {
//            r += pad + "pv=" + l.getP().toString(pad) + "\n"
//                    + pad + ",\n"
//                    + pad + "qv=null" + "\n"
//                    + pad + ",\n"
//                    + pad + "v=" + l.v.toString(pad);
//        } else {
//            if (l.v == null) {
//                r += pad + "pv=" + l.getP().toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "qv=" + l.qv.toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "v=null";
//            } else {
//                r += pad + "pv=" + l.getP().toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "qv=" + l.qv.toString(pad) + "\n"
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
                + pad + "q=" + qv.toStringFieldsSimple("");
//        if (l.qv == null) {
//            r += pad + "pv=" + l.getP().toStringSimple("") + ",\n"
//                    + pad + "qv=null" + ",\n"
//                    + pad + "v=" + l.v.toStringSimple(pad);
//        } else {
//            if (l.v == null) {
//                r += pad + "pv=" + l.getP().toStringSimple(pad) + ",\n"
//                        + pad + "qv=" + l.qv.toStringSimple(pad) + ",\n"
//                        + pad + "v=null";
//            } else {
//                r += pad + "pv=" + l.getP().toStringSimple(pad) + ",\n"
//                        + pad + "qv=" + l.qv.toStringSimple(pad) + ",\n"
//                        + pad + "v=" + l.v.toStringSimple(pad);
//            }
//        }
        return r;
    }

    /**
     * @param l The line segment to test if it is the same as {@code this}.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_LineSegmentDouble l, double epsilon) {
        if (equalsIgnoreDirection(l, epsilon)) {
            return this.l.getP().equals(l.getP(), epsilon);
        }
        return false;
    }

    /**
     * @param l The line segment to test if it is equal to {@code this}.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is equal to {@code l}. This ignores
     * the order of the point of {@link #l} and {@link #qv}.
     */
    public boolean equalsIgnoreDirection(V3D_LineSegmentDouble l,
            double epsilon) {
//        if (l == null) {
//            return false;
//        }
        if (this.l.equals(l.l, epsilon)) {
            return isIntersectedBy(l.getQ(), epsilon)
                    && l.isIntersectedBy(getQ(), epsilon);
        }
        return false;
    }

    /**
     * @return The length of {@code this}.
     */
    public double getLength() {
        return Math.sqrt(getLength2());
    }

    /**
     * @return The length of {@code this} squared.
     */
    public double getLength2() {
        return getP().getDistanceSquared(getQ());
    }

    /**
     * @return {@code new V3D_Envelope(start, end)}
     */
    @Override
    public V3D_EnvelopeDouble getEnvelope() {
        if (en == null) {
            en = new V3D_EnvelopeDouble(getP(), getQ());
        }
        return en;
    }

    /**
     * @param pt A point to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is intersected by {@code pv}.
     */
    public boolean isIntersectedBy(V3D_PointDouble pt, double epsilon) {
        boolean ei = getEnvelope().isIntersectedBy(pt.getEnvelope(), epsilon);
        if (ei) {
            if (l.isIntersectedBy(pt, epsilon)) {
                V3D_PointDouble tp = getP();
                double a = pt.getDistance(tp);
                if (a == 0d) {
                    return true;
                }
                V3D_PointDouble tq = getQ();
                double b = pt.getDistance(tq);
                if (b == 0d) {
                    return true;
                }
                double d = tp.getDistance(tq);
                double apb = a + b;
                return Math_Double.equals(apb, d, epsilon);
//                if (apb <= d) {
//                    return true;
//                }
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_LineDouble l,
            double epsilon) {
        // Check if infinite lines intersect.
        V3D_GeometryDouble i = this.l.getIntersection(l, epsilon);
        if (i == null) {
            // There is no intersection.
            return null;
        }
        /**
         * If infinite lines intersects at a point, then check this point is on
         * this.
         */
        if (i instanceof V3D_PointDouble v3D_Point) {
            //if (isIntersectedBy(v3D_Point, epsilon)) {
            if (isBetween(v3D_Point)) {
                return v3D_Point;
            } else {
                return null;
            }
        } else if (i instanceof V3D_LineDouble) {
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_LineSegmentDouble ls,
            double epsilon) {
        if (!getEnvelope().isIntersectedBy(ls.getEnvelope(), epsilon)) {
            return null;
        }
        // Get intersection of infinite lines. 
        V3D_GeometryDouble r = this.l.getIntersection(ls.l, epsilon);
        if (r == null) {
            return null;
        }
        if (r instanceof V3D_PointDouble rp) {
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
        V3D_PointDouble lp = ls.getP();
        V3D_PointDouble lq = ls.getQ();
        V3D_PointDouble tp = getP();
        V3D_PointDouble tq = getQ();
        if (this.isIntersectedBy(lp, epsilon)) {
            // Cases: 1, 2, 3, 5, 8, 9, 10, 12, 17, 19, 20, 21, 24, 26, 27, 28
            if (this.isIntersectedBy(lq, epsilon)) {
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
                if (ls.isIntersectedBy(tp, epsilon)) {
                    // Cases: 8, 9, 20, 21
                    if (tp.equals(lp, epsilon)) {
                        // Cases: 8, 21
                        return tp;
                    } else {
                        // Cases: 9, 20
                        return V3D_LineSegmentDouble.getGeometry(lp, tp, epsilon);
                    }
                } else {
                    // Cases: 1, 2, 8, 21, 27, 28
                    if (tp.equals(lp, epsilon)) {
                        // Cases: 8, 21
                        return tp;
                    } else {
                        // Case: 1, 28
                        if (lp.equals(tq, epsilon)) {
                            return lp;
                        }
                        // Cases: 2, 27
                        return V3D_LineSegmentDouble.getGeometry(lp, tq, epsilon);
                    }
                }
            }
        } else {
            // Cases: 4, 6, 7, 11, 13, 14, 16, 18, 19, 22, 23, 25
            if (this.isIntersectedBy(lq, epsilon)) {
                // Cases: 6, 7, 13, 14, 16, 19, 22, 23
                if (ls.isIntersectedBy(tp, epsilon)) {
                    // Cases: 6, 7, 19, 22, 23
                    if (ls.isIntersectedBy(tq, epsilon)) {
                        // Cases: 19, 23
                        if (tp.equals(lp, epsilon)) {
                            // Case: 19
                            return this;
                        } else {
                            // Case: 23
                            return V3D_LineSegmentDouble.getGeometry(lq, tp, epsilon);
                        }
                    } else {
                        // Cases: 6, 7, 22, 
                        if (tp.equals(lq, epsilon)) {
                            // Cases: 7, 22
                            return tp;
                        } else {
                            // Case: 6
                            return V3D_LineSegmentDouble.getGeometry(tp, lq, epsilon);
                        }
                    }
                } else {
                    // Cases: 13, 14, 16
                    //if (l.isIntersectedBy(tq, oom)) {
                    // Cases: 13, 14, 16
                    if (tq.equals(lq, epsilon)) {
                        // Cases: 14, 16
                        return tq;
                    } else {
                        // Case: 13
                        //return new V3D_LineSegment(e, l.qv, ls.l.qv);
                        return V3D_LineSegmentDouble.getGeometry(tq, lq, epsilon);
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance between this and {@code pv}.
     */
    public double getDistance(V3D_PointDouble p, double epsilon) {
        return Math.sqrt(getDistanceSquared(p, epsilon));
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance between this and {@code pv}.
     */
    public double getDistanceSquared(V3D_PointDouble pt, double epsilon) {
        if (isIntersectedBy(pt, epsilon)) {
            return 0d;
        }
        V3D_PointDouble poi = l.getPointOfIntersection(pt, epsilon);
        if (isAligned(poi)) {
            return poi.getDistanceSquared(pt);
        } else {
            return Math.min(pt.getDistanceSquared(getP()),
                    pt.getDistanceSquared(getQ()));
        }
    }

    /**
     * Calculates and returns if pt is in line with this. It is in line if it is
     * between the planes defined by the ends of the line segment with the
     * normal vector as the vector of the line.
     *
     * @param pt The point.
     * @return {@code true} If pt is in line with this.
     */
    public boolean isAligned(V3D_PointDouble pt) {
        if (getPPL().isOnSameSide(pt, getQ())) {
            return getQPL().isOnSameSide(pt, getP());
        }
        return false;
    }

    /**
     * Calculates and returns if l is in line with this. It is in line if both
     * end points of l are in line with this as according to
     * {@link #isAligned(uk.ac.leeds.ccg.v3d.geometry.d.V3D_PointDouble)}.
     *
     * @param l The line segment.
     * @return {@code true} If pt is in line with this.
     */
    public boolean isAligned(V3D_LineSegmentDouble l) {
        if (isAligned(l.getP())) {
            return isAligned(l.getQ());
        }
        return false;
    }

    /**
     * @param l The line segment to return the distance from.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance from {@code this} to {@code l} at the {@code oom}
     * precision.
     */
    public double getDistance(V3D_LineSegmentDouble l, double epsilon) {
        return Math.sqrt(getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistanceSquared(V3D_LineSegmentDouble l, double epsilon) {
        if (getIntersection(l, epsilon) != null) {
            return 0d;
        }
        V3D_LineSegmentDouble loi = getLineOfIntersection(l, epsilon);
        if (loi == null) {
            /**
             * Lines are parallel.
             */
            return getDistanceSquared(l.getP(), epsilon);
        } else {
            return loi.getLength2();
        }
    }

    /**
     * Calculate and return the midpoint between pv and qv.
     *
     * @return the midpoint between pv and qv to the OOM precision.
     */
    public V3D_PointDouble getMidpoint() {
        V3D_VectorDouble pmpq = l.v.divide(2.0d);
        return new V3D_PointDouble(offset, l.pv.add(pmpq));
    }

    /**
     * For returning the other end of the line segment as a point.
     *
     * @param pt A point equal to either {@link #getP()} or the point of
     * {@link #l}.
     * @return The other point that is not equal to a.
     */
    public V3D_PointDouble getOtherPoint(V3D_PointDouble pt) {
        if (getP().equals(pt)) {
            return getQ();
        } else {
            return pt;
        }
    }

    /**
     * If pv and qv are equal then the point is returned otherwise the line
     * segment is returned
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return either {@code pv} or {@code new V3D_LineSegment(pv, qv)}
     */
    public static V3D_FiniteGeometryDouble getGeometry(V3D_PointDouble p,
            V3D_PointDouble q, double epsilon) {
        if (p.equals(q, epsilon)) {
            return p;
        } else {
            return new V3D_LineSegmentDouble(p, q);
        }
    }

    /**
     * If pv, qv and r are equal then the point is returned otherwise a line
     * segment is returned where all the points are on the line segment.
     *
     * @param p A point possibly equal to qv or r, but certainly collinear.
     * @param q A point possibly equal to pv or r, but certainly collinear.
     * @param r A point possibly equal to pv or qv, but certainly collinear.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return either {@code pv} or {@code new V3D_LineSegment(pv, qv)}
     */
    public static V3D_FiniteGeometryDouble getGeometry(V3D_PointDouble p,
            V3D_PointDouble q, V3D_PointDouble r, double epsilon) {
        if (p.equals(q, epsilon)) {
            return getGeometry(epsilon, p, r);
        } else if (q.equals(r, epsilon)) {
            return getGeometry(epsilon, p, r);
        } else if (p.equals(r, epsilon)) {
            return getGeometry(epsilon, p, q);
        } else {
            V3D_LineSegmentDouble ls = new V3D_LineSegmentDouble(epsilon, p, q);
            if (ls.isIntersectedBy(r, epsilon)) {
                return ls;
            } else {
                ls = new V3D_LineSegmentDouble(epsilon, p, r);
                if (ls.isIntersectedBy(q, epsilon)) {
                    return ls;
                } else {
                    return new V3D_LineSegmentDouble(epsilon, q, r);
                }
            }
        }
    }

    /**
     * If pv, qv and r are equal then the point is returned otherwise a line
     * segment is returned where all the points are on the line segment.
     *
     * @param l A line segment.
     * @param pt A point collinear with l.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return either {@code pv} or {@code new V3D_LineSegment(pv, qv)}
     */
    public static V3D_FiniteGeometryDouble getGeometry(V3D_LineSegmentDouble l,
            V3D_PointDouble pt, double epsilon) {
        return getGeometry(epsilon, l.getP(), l.getQ(), pt);
    }

    /**
     * Get the smallest line segment intersected by all pts.
     *
     * @param pts Collinear points.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return either {@code pv} or {@code new V3D_LineSegment(pv, qv)}
     */
    public static V3D_FiniteGeometryDouble getGeometry(double epsilon, V3D_PointDouble... pts) {
        int length = pts.length;
        if (length == 0) {
            return null;
        } else if (length == 1) {
            return pts[0];
        } else if (length == 2) {
            return getGeometry(pts[0], pts[1], epsilon);
        } else if (length == 3) {
            return getGeometry(pts[0], pts[1], pts[2], epsilon);
        } else {
            V3D_FiniteGeometryDouble g = getGeometry(epsilon, pts[0], pts[1], pts[2]);
            for (int i = 3; i < length; i++) {
                if (g instanceof V3D_PointDouble gp) {
                    g = getGeometry(gp, pts[i], epsilon);
                } else {
                    g = getGeometry((V3D_LineSegmentDouble) g, pts[i], epsilon);
                }
            }
            return g;
        }
    }

    /**
     * Get the line of intersection (the shortest line) between {@code this} and
     * {@code l}.
     *
     * @param l The line to get the line of intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The line of intersection between {@code this} and {@code l}.
     */
    public V3D_LineSegmentDouble getLineOfIntersection(V3D_LineDouble l,
            double epsilon) {
        if (getIntersection(l, epsilon) != null) {
            return null;
        }
        V3D_LineSegmentDouble loi = this.l.getLineOfIntersection(l, epsilon);
        V3D_PointDouble tp = getP();
        V3D_PointDouble tq = getQ();
        if (loi == null) {
            double pd = l.getDistanceSquared(tp, epsilon);
            double qd = l.getDistanceSquared(tq, epsilon);
            if (pd > qd) {
                return new V3D_LineSegmentDouble(tq, l.getPointOfIntersection(tq, epsilon));
            } else {
                return new V3D_LineSegmentDouble(tp, l.getPointOfIntersection(tp, epsilon));
            }
        } else {
            V3D_PointDouble lsp = loi.getP();
            //V3D_Point lsq = loi.getQ();
            V3D_PlaneDouble plp = new V3D_PlaneDouble(tp, l.v);
            V3D_PlaneDouble plq = new V3D_PlaneDouble(tq, l.v);
            if (plp.isOnSameSide(lsp, tq)) {
                if (plq.isOnSameSide(lsp, tp)) {
                    /**
                     * The line of intersection connects in the line segment, so
                     * return it.
                     */
                    return loi;
                } else {
                    return new V3D_LineSegmentDouble(tq, lsp);
                }
            } else {
                return new V3D_LineSegmentDouble(tp, lsp);
            }
        }
    }

    /**
     * Get the line of intersection (the shortest line) between {@code this} and
     * {@code l}.
     *
     * @param ls The line segment to get the line of intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The line of intersection between {@code this} and {@code l}.
     */
    public V3D_LineSegmentDouble getLineOfIntersection(V3D_LineSegmentDouble ls,
            double epsilon) {
        V3D_FiniteGeometryDouble ilsl = getIntersection(ls, epsilon);
        if (ilsl == null) {
            V3D_PointDouble lsp = ls.getP();
            V3D_PointDouble lsq = ls.getQ();
            V3D_PointDouble tp = getP();
            V3D_PointDouble tq = getQ();
            // Get the line of intersection between this and ls.l
            V3D_LineSegmentDouble tloi = getLineOfIntersection(ls.l, epsilon);
            V3D_LineSegmentDouble lsloi = ls.getLineOfIntersection(l, epsilon);
            if (tloi == null) {
                V3D_PointDouble tip;
                V3D_PointDouble lsloiq = lsloi.getQ();
                // Is the intersection point on this within the line segment?
                // Can use isAligned to do this more clearly?
                V3D_PlaneDouble tppl = new V3D_PlaneDouble(tp, l.v);
                V3D_PlaneDouble tqpl = new V3D_PlaneDouble(tq, l.v);
                if (tppl.isOnSameSide(lsloiq, tq)) {
                    if (tqpl.isOnSameSide(lsloiq, tp)) {
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
                return new V3D_LineSegmentDouble(tip, lsloiq);
            } else {
                V3D_PointDouble tloip = tloi.getP(); // This is the end of the line segment on this.
                V3D_PointDouble tloiq = tloi.getQ();
                if (lsloi == null) {
                    V3D_PointDouble lsip;
                    // Is the intersection point on ls within the line segment?
                    // Can use isAligned to do this more clearly?
                    V3D_PlaneDouble lsppl = new V3D_PlaneDouble(lsp, ls.l.v);
                    if (lsppl.isOnSameSide(tloiq, lsq)) {
                        V3D_PlaneDouble lsqpl = new V3D_PlaneDouble(lsq, ls.l.v);
                        if (lsqpl.isOnSameSide(tloiq, lsp)) {
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
                    return new V3D_LineSegmentDouble(tloip, lsip);
                    //return new V3D_LineSegment(tq, lsip);
                    //return new V3D_LineSegment(tp, lsip);
                    //return new V3D_LineSegment(tloiq, getNearestPoint(this, tloiq));
                } else {
                    // tloip is on
                    if (isBetween(tloip)) {
                        return new V3D_LineSegmentDouble(tloip, getNearestPoint(ls, tloip));
                    } else {
                        return new V3D_LineSegmentDouble(
                                getNearestPoint(this, tloip),
                                getNearestPoint(ls, tloip));
                    }
                }
            }
        } else {
            return null;
        }
    }

    /**
     * Useful for intersection tests.
     *
     * @return The plane with a point at l.getP() and normal l.v
     */
    public V3D_PlaneDouble getPPL() {
        if (ppl == null) {
            ppl = new V3D_PlaneDouble(l.getP(), l.v);
        }
        return ppl;
    }

    /**
     * Useful for intersection tests.
     *
     * @return The plane with a point at l.getQ() and normal l.v
     */
    public V3D_PlaneDouble getQPL() {
        if (qpl == null) {
            qpl = new V3D_PlaneDouble(getQ(), l.v);
        }
        return qpl;
    }

    /**
     * Useful for intersection tests.
     *
     * @param pt The point to test if it is between {@link #qv} and the point of
     * {@link #l}.
     * @return {@code true} iff pt lies between the planes at the end of the
     * line segment.
     */
    public boolean isBetween(V3D_PointDouble pt) {
        if (getPPL().isOnSameSide(pt, getQ())) {
            if (getQPL().isOnSameSide(pt, getP())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param l A line segment.
     * @param pt Is on the line of {@code l}, but not on {@code l}.
     * @return The nearest point on {@code l} to {@code pv}.
     */
    protected static V3D_PointDouble getNearestPoint(V3D_LineSegmentDouble l,
            V3D_PointDouble pt) {
        V3D_PointDouble lp = l.getP();
        V3D_PointDouble lq = l.getQ();
        double dlpp = lp.getDistanceSquared(pt);
        double dlqp = lq.getDistanceSquared(pt);
        if (dlpp < dlqp) {
            return lp;
        } else {
            return lq;
        }
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V3D_LineDouble l, double epsilon) {
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
    public double getDistanceSquared(V3D_LineDouble l, double epsilon) {
        if (getIntersection(l, epsilon) != null) {
            return 0d;
        }
        V3D_LineSegmentDouble loi = getLineOfIntersection(l, epsilon);
        if (loi == null) {
            // Lines are parallel.
            return l.getDistanceSquared(getP(), epsilon);
        }
        return loi.getLength2();
    }

    @Override
    public V3D_LineSegmentDouble rotate(V3D_LineDouble axis, double theta,
            double epsilon) {
        return new V3D_LineSegmentDouble(
                getP().rotate(axis, theta, epsilon),
                getQ().rotate(axis, theta, epsilon));
    }

    /**
     * Clips this using pl and returns the part that is on the same side as pt.
     *
     * @param pl The plane that clips.
     * @param pt A point that is used to return the side of the clipped line
     * segment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometryDouble clip(V3D_PlaneDouble pl, V3D_PointDouble pt,
            double epsilon) {
        V3D_FiniteGeometryDouble i = pl.getIntersection(this, epsilon);
        V3D_PointDouble tp = getP();
        if (i == null) {
            if (pl.isOnSameSide(tp, pt)) {
                return this;
            } else {
                return null;
            }
        } else if (i instanceof V3D_PointDouble ip) {
            if (pl.isOnSameSide(tp, pt)) {
                return V3D_LineSegmentDouble.getGeometry(ip, tp, epsilon);
            } else {
                V3D_PointDouble tq = this.getQ();
                return V3D_LineSegmentDouble.getGeometry(ip, tq, epsilon);
            }
        } else {
            return this;
        }
    }
}
