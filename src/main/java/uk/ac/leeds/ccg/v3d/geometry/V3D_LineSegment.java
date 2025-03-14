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
import java.math.RoundingMode;
import java.util.ArrayList;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * 3D representation of a finite length line (a line segment). The line begins
 * at the point of {@link #l} and ends at the point {@link #qv}. The "*" denotes
 * a point in 3D and the line is shown with a line of "e" symbols in the
 * following depiction: {@code
 *                                       z
 *                          y           -
 *                          +          /                * pv=<x0,y0,z0>
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
 *                   /      |       * qv=<x1,y1,z1>
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
     * The line of which this line segment is part and for which the point on
     * the line is one end point of this segment.
     */
    public final V3D_Line l;

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
    protected BigRational len2;

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is cloned from.
     */
    public V3D_LineSegment(V3D_LineSegment l) {
        super(l.env, l.offset);
        this.l = new V3D_Line(l.l);
    }

    /**
     * Create a new instance. {@link #offset} is set to {@link V3D_Vector#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #qv} is cloned from.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_LineSegment(V3D_Environment env, V3D_Vector p, V3D_Vector q,
            int oom, RoundingMode rm) {
        this(env, V3D_Vector.ZERO, p, q, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #qv} is cloned from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_LineSegment(V3D_Environment env, V3D_Vector offset, V3D_Vector p,
            V3D_Vector q, int oom, RoundingMode rm) {
        super(env, offset);
        l = new V3D_Line(env, offset, p, q, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #qv} is cloned from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_LineSegment(V3D_Point p, V3D_Point q, int oom, RoundingMode rm) {
        super(p.env, p.offset);
        V3D_Point q2 = new V3D_Point(q);
        q2.setOffset(offset, oom, rm);
        l = new V3D_Line(env, offset, p.rel, q2.rel, oom, rm);
    }

    /**
     * Create a new instance that intersects all points.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points Any number of collinear points, with two being different.
     */
    public V3D_LineSegment(int oom, RoundingMode rm,
            ArrayList<V3D_Point> points) {
        this(oom, rm, points.toArray(V3D_Point[]::new));
    }

    /**
     * Create a new instance that intersects all points.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points Any number of collinear points, with two being different.
     */
    public V3D_LineSegment(int oom, RoundingMode rm, V3D_Point... points) {
        super(points[0].env, points[0].offset);
        V3D_Point p0 = points[0];
        V3D_Point p1 = points[1];
        V3D_LineSegment ls = new V3D_LineSegment(p0, p1, oom, rm);
        for (int i = 2; i < points.length; i++) {
            if (!ls.intersects(points[i], oom, rm)) {
                V3D_LineSegment l2 = new V3D_LineSegment(ls.getP(), points[i], oom, rm);
                V3D_Point lq = ls.getQ(oom, rm);
                if (l2.intersects(lq, oom, rm)) {
                    ls = l2;
                } else {
                    ls = new V3D_LineSegment(ls.getQ(oom, rm), points[i], oom, rm);
                }
            }
        }
        this.l = ls.l;
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_LineSegment(V3D_Line l) {
        super(l.env);
        this.l = new V3D_Line(l);
    }

    /**
     * @return {@link #l} pv with {@link #l} offset applied.
     */
    public V3D_Point getP() {
        return l.getP();
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code l.getQ(oom, rm)}.
     */
    public V3D_Point getQ(int oom, RoundingMode rm) {
        return l.getQ(oom, rm);
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
        r += pad + "l=" + l.toStringFields(pad);
        return r;
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFieldsSimple(String pad) {
        String r = super.toStringFieldsSimple(pad) + ",\n";
        r += pad + "l=" + l.toStringFieldsSimple(pad);
        return r;
    }

    /**
     * @param l The line segment to test if it is the same as {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_LineSegment l, int oom, RoundingMode rm) {
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
     * the order of the point of {@link #l} and {@link #qv}.
     */
    public boolean equalsIgnoreDirection(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (this.l.equals(l.l, oom, rm)) {
            return intersects(l.getQ(oom, rm), oom, rm)
                    && l.intersects(getQ(oom, rm), oom, rm);
        }
        return false;
    }

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
    public BigRational getLength2(int oom, RoundingMode rm) {
        return getP().getDistanceSquared(getQ(oom, rm), oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code new V3D_AABB(start, end)}
     */
    @Override
    public V3D_AABB getAABB(int oom, RoundingMode rm) {
        if (en == null) {
            en = new V3D_AABB(oom, getP(), getQ(oom, rm));
        }
        return en;
    }

    @Override
    public V3D_Point[] getPointsArray(int oom, RoundingMode rm) {
        V3D_Point[] r = new V3D_Point[2];
        r[0] = getP();
        r[1] = getQ(oom, rm);
        return r;
    }
    
    /**
     * @param pt A point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is intersected by {@code pv}.
     */
    public boolean intersects(V3D_Point pt, int oom, RoundingMode rm) {
        boolean ei = getAABB(oom, rm).intersects(pt.getAABB(oom, rm), oom);
        if (ei) {
            if (l.intersects(pt, oom, rm)) {
                V3D_Point tp = getP();
                Math_BigRationalSqrt a = pt.getDistance(oom, rm, tp);
                if (a.getX().isZero()) {
                    return true;
                }
                V3D_Point tq = getQ(oom, rm);
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
                    return apb.equals(d, oom);
//                    if (apb.compareTo(d) != 1) {
//                        return true;
//                    }
                }
            }
        }
        return false;
    }
    
    /**
     * @param aabb The V3D_AABB to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public boolean intersects(V3D_AABB aabb, int oom, RoundingMode rm) {
        return aabb.intersects(l.p, oom, rm)
                || getIntersect(aabb.getl(oom, rm), oom, rm) != null
                || getIntersect(aabb.getr(oom, rm), oom, rm) != null
                || getIntersect(aabb.gett(oom, rm), oom, rm) != null
                || getIntersect(aabb.getb(oom, rm), oom, rm) != null
                || getIntersect(aabb.getf(oom, rm), oom, rm) != null
                || getIntersect(aabb.geta(oom, rm), oom, rm) != null;
    }
    
    /**
     * @param aabbx The V3D_AABBX to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public V3D_Point getIntersect(V3D_AABBX aabbx, int oom, RoundingMode rm) {
        V3D_Plane pl = aabbx.getXPl(); 
        if (pl.isParallel(l, oom, rm)) {
            return null;
        }
        // Calculate the intersection point
        V3D_Point pt = (V3D_Point) pl.getIntersect(l, oom, rm);
        if (isBetween(pt, oom, rm)
            && aabbx.intersects(pt, oom, rm)) {
                return pt;
        }
        return null;
    }
    
    /**
     * @param aabby The V3D_AABBY to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public V3D_Point getIntersect(V3D_AABBY aabby, int oom, RoundingMode rm) {
        V3D_Plane pl = aabby.getYPl(); 
        if (pl.isParallel(l, oom, rm)) {
            return null;
        }
        // Calculate the intersection point
        V3D_Point pt = (V3D_Point) pl.getIntersect(l, oom, rm);
        if (isBetween(pt, oom, rm)
            && aabby.intersects(pt, oom, rm)) {
            return pt;
        }
        return null;
    }

    /**
     * @param aabbz The V3D_AABBZ to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public V3D_Point getIntersect(V3D_AABBZ aabbz, int oom, RoundingMode rm) {
        V3D_Plane zpl = aabbz.getZPl(); 
        if (zpl.isParallel(l, oom, rm)) {
            return null;
        }
        // Calculate the intersection point
        V3D_Point pt = (V3D_Point) zpl.getIntersect(l, oom, rm);
        if (isBetween(pt, oom, rm)
            && aabbz.intersects(pt, oom, rm)) {
            return pt;
        }
        return null;
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
    public V3D_FiniteGeometry getIntersect(V3D_Line l, int oom, RoundingMode rm) {
        // Check if infinite lines intersect.
        V3D_Geometry i = this.l.getIntersect(l, oom, rm);
        if (i == null) {
            // There is no intersection.
            return null;
        }
        /**
         * If infinite lines intersects at a point, then check this point is on
         * this.
         */
        if (i instanceof V3D_Point v3D_Point) {
            //if (intersects(v3D_Point, oom, rm)) {
            if (isBetween(v3D_Point, oom, rm)) {
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
    public V3D_FiniteGeometry getIntersect(V3D_LineSegment ls, int oom, RoundingMode rm) {
        if (!getAABB(oom, rm).intersects(ls.getAABB(oom, rm), oom)) {
            return null;
        }
        // Get intersection of infinite lines. 
        V3D_Geometry r = this.l.getIntersect(ls.l, oom, rm);
        // Get intersection with infinite lines.
        V3D_Geometry li = l.getIntersect(ls.l, oom, rm);
        V3D_FiniteGeometry tils = getIntersect(ls.l, oom, rm);
        V3D_FiniteGeometry lsil = ls.getIntersect(l, oom, rm);
        if (li == null) {
            return null;
        } else {
            if (tils == null) {
                if (lsil == null) {
                    return null;
                } else {
                    if (lsil instanceof V3D_Point lsilp) {
                        //if (intersects(lsilp, oom, rm)) {
                        if (isBetween(lsilp, oom, rm)) {
                            return lsilp;
                        } else {
                            return null;
                        }
                    } else {
                        //throw new RuntimeException();
                        return null;
                    }
                }
            } else {
                if (tils instanceof V3D_Point tilsp) {
                    //if (intersects(tilsp, oom, rm) && ls.intersects(tilsp, oom, rm)) {
                    if (isBetween(tilsp, oom, rm) && ls.isBetween(tilsp, oom, rm)) {
                        return tilsp;
                    } else {
                        return null;
                    }
                } else {
                    return getIntersectLS(ls, oom, rm);
                }
            }
        }
    }

    /**
     * Use when this and ls are collinear and intersect.
     *
     * @param ls An intersecting collinear line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection.
     */
    public V3D_FiniteGeometry getIntersectLS(V3D_LineSegment ls, int oom,
            RoundingMode rm) {
        /**
         * Check the type of intersection. {@code
         * A) p_q l.p_l.q
         *
         * 1)  p +----------+ q
         *              l.p +----------+ l.q
         *
         * 2)  p +----------+ q
         *         l.p +----------+ l.q
         *
         * 3)  p +------------------------+ q
         *         l.p +----------+ l.q
         *
         * 4)        p +----------+ q
         *     l.p +----------------------+ l.q
         *
         * 5)    p +----------+ q
         *     l.p +----------+ l.q
         *
         * 6)         p +----------+ q
         *     l.p +----------+ l.q
         *
         * 7)              p +----------+ q
         *     l.p +---------+ l.q
         *
         * B) q_p l.p_l.q
         *
         * 8)  q +----------+ p
         *              l.p +----------+ l.q
         *
         * 9)  q +----------+ p
         *         l.p +----------+ l.q
         *
         * 10) q +------------------------+ p
         *         l.p +----------+ l.q
         *
         * 11)       q +----------+ p
         *     l.p +----------------------+ l.q
         *
         * 12)       q +----------+ p
         *         l.p +----------+ l.q
         *
         * 13)       q +----------+ p
         *     l.p +----------+ l.q
         *
         * 14)              q +----------+ p
         *     l.p +----------+ l.q
         *
         * C) p_q l.q_l.p
         *
         * 15) p +----------+ q
         *              l.q +----------+ l.p
         *
         * 16) p +----------+ q
         *         l.q +----------+ l.p
         *
         * 17) p +------------------------+ q
         *         l.q +----------+ l.p
         *
         * 18)       p +----------+ q
         *     l.q +------------------------+ l.p
         *
         * 19)       p +----------+ q
         *         l.q +----------+ l.p
         *
         * 20)       p +----------+ q
         *     l.q +----------+ l.p
         *
         * 21)              p +----------+ q
         *     l.q +----------+ l.p
         *
         * D) q_p l.q_l.p
         *
         * 22) q +----------+ p
         *              l.q +----------+ l.p
         *
         * 23) q +----------+ p
         *         l.q +----------+ l.p
         *
         * 24) q +------------------------+ p
         *         l.q +----------+ l.p
         *
         * 25)       q +----------+ p
         *     l.q +------------------------+ l.p
         *
         * 26)       p +----------+ q
         *         l.q +----------+ l.p
         *
         * 27)       q +----------+ p
         *    l.q +---------+ l.p
         *
         * 28)            q +----------+ p
         *    l.q +---------+ l.p
         * }
         */
        V3D_Point lp = ls.getP();
        V3D_Point lq = ls.getQ(oom, rm);
        V3D_Point tp = getP();
        V3D_Point tq = getQ(oom, rm);
        //if (intersects(lp, oom, rm)) {
        if (isBetween(lp, oom, rm)) {
            // Cases: 1, 2, 3, 5, 8, 9, 10, 12, 17, 19, 20, 21, 24, 26, 27, 28
            //if (intersects(lq, oom, rm)) {
            if (isBetween(lq, oom, rm)) {
                // Cases: 3, 5, 10, 12, 17, 19, 24, 26
                return ls;
            } else {
                // Cases: 1, 2, 8, 9, 20, 21, 27, 28
                //if (ls.intersects(tp, oom, rm)) {
                if (ls.isBetween(tp, oom, rm)) {
                    // Cases: 8, 9, 20, 21
                    if (tp.equals(lp, oom, rm)) {
                        // Cases: 8, 21
                        return tp;
                    } else {
                        // Cases: 9, 20
                        return V3D_LineSegment.getGeometry(lp, tp, oom, rm);
                    }
                } else {
                    // Cases: 1, 2, 27, 28
                    if (lp.equals(tq, oom, rm)) {
                        // Cases: 1, 28
                        return lp;
                    } else {
                        // Case: 2, 27
                        return V3D_LineSegment.getGeometry(lp, tq, oom, rm);
                    }
                }
            }
        } else {
            // Cases: 4, 6, 7, 11, 13, 14, 15, 16, 18, 22, 23, 25
            //if (intersects(lq, oom, rm)) {
            if (isBetween(lq, oom, rm)) {
                // Cases: 6, 7, 13, 14, 15, 16, 22, 23
                //if (ls.intersects(tp, oom, rm)) {
                if (ls.isBetween(tp, oom, rm)) {
                    // Cases: 6, 7, 22, 23
                    //if (ls.intersects(tq, oom, rm)) {
                    if (ls.isBetween(tq, oom, rm)) {
                        // Cases: 23
                        return V3D_LineSegment.getGeometry(lq, tp, oom, rm);
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
                    // Cases: 13, 14, 15, 16
                    if (tq.equals(lq, oom, rm)) {
                        // Cases: 14, 15
                        return tq;
                    } else {
                        // Case: 13, 16
                        //return new V3D_LineSegment(e, l.qv, ls.l.qv);
                        return V3D_LineSegment.getGeometry(tq, lq, oom, rm);
                    }
                }
            } else {
                // Cases: 4, 11, 18, 25
                return this;
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
     * @return The minimum distance between this and {@code pv}.
     */
    public BigRational getDistance(V3D_Point p, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm)
                .getSqrt(oom, rm);
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
     * @return The minimum distance between this and {@code pv}.
     */
    public BigRational getDistanceSquared(V3D_Point pt, int oom,
            RoundingMode rm) {
        if (intersects(pt, oom, rm)) {
            return BigRational.ZERO;
        }
        V3D_Point poi = l.getPointOfIntersect(pt, true, oom, rm);
        if (isAligned(poi, oom, rm)) {
            return poi.getDistanceSquared(pt, oom, rm);
        } else {
            return BigRational.min(pt.getDistanceSquared(getP(), oom, rm),
                    pt.getDistanceSquared(getQ(oom, rm), oom, rm));
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
        if (getPPL().isOnSameSide(pt, getQ(oom, rm), oom, rm)) {
            return getQPL(oom, rm).isOnSameSide(pt, getP(), oom, rm);
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
            return isAligned(l.getQ(oom, rm), oom, rm);
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
    public BigRational getDistance(V3D_LineSegment l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code l}.
     */
    public BigRational getDistanceSquared(V3D_LineSegment l, int oom,
            RoundingMode rm) {
        if (getIntersect(l, oom, rm) != null) {
            return BigRational.ZERO;
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
     * Calculate and return the midpoint between pv and qv.
     *
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return the midpoint between pv and qv to the OOM precision.
     */
    public V3D_Point getMidpoint(int oom, RoundingMode rm) {
        //BigDecimal l = getLength().toBigDecimal(oom);
        //V3D_Vector pmpq = v.divide(BigRational.valueOf(l));
        //V3D_Vector pmpq = l.getV(oom, rm).divide(BigRational.valueOf(2), oom, rm);
        V3D_Vector pmpq = l.v.divide(BigRational.valueOf(2), oom, rm);
        //return getP(oom).translate(pmpq, oom);
        return new V3D_Point(env, offset, l.pv.add(pmpq, oom, rm));
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
            return getQ(oom, rm);
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
     * @return either {@code pv} or {@code new V3D_LineSegment(pv, qv)}
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
     * If p, q and r are equal then the point is returned otherwise a line
     * segment is returned where all the points are on the line segment.
     *
     * @param p A point possibly equal to q or r, but certainly collinear.
     * @param q A point possibly equal to p or r, but certainly collinear.
     * @param r A point possibly equal to p or q, but certainly collinear.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pv} or {@code new V3D_LineSegment(pv, qv)}
     */
    public static V3D_FiniteGeometry getGeometry(V3D_Point p, V3D_Point q,
            V3D_Point r, int oom, RoundingMode rm) {
        if (p.equals(q, oom, rm)) {
            return getGeometry(p, r, oom, rm);
        } else if (q.equals(r, oom, rm)) {
            return getGeometry(p, r, oom, rm);
        } else if (p.equals(r, oom, rm)) {
            return getGeometry(p, q, oom, rm);
        } else {
            //V3D_LineSegment ls = new V3D_LineSegment(p, q, oom, rm);
            //if (ls.intersects(r, oom, rm)) {
            if (r.isBetween(p, q, oom, rm)) {
                //return ls;
                return new V3D_LineSegment(p, q, oom, rm);
            } else {
                //ls = new V3D_LineSegment(p, r, oom, rm);
                //if (ls.intersects(q, oom, rm)) {
                if (p.isBetween(r, q, oom, rm)) {
                    //return ls;
                    return new V3D_LineSegment(q, r, oom, rm);
                } else {
                    return new V3D_LineSegment(p, r, oom, rm);
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
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pv} or {@code new V3D_LineSegment(pv, qv)}
     */
    public static V3D_LineSegment getGeometry(V3D_LineSegment l,
            V3D_Point pt, int oom, RoundingMode rm) {
        return (V3D_LineSegment) getGeometry(l.getP(), l.getQ(oom, rm), pt, oom, rm);
    }

    /**
     * Get the smallest line segment intersected by all pts.
     *
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @param pts Collinear points.
     * @return either {@code pv} or {@code new V3D_LineSegment(pv, qv)}
     */
    public static V3D_FiniteGeometry getGeometry(int oom, RoundingMode rm,
            V3D_Point... pts) {
        int length = pts.length;
        switch (length) {
            case 0 -> {
                return null;
            }
            case 1 -> {
                return pts[0];
            }
            case 2 -> {
                return getGeometry(pts[0], pts[1], oom, rm);
            }
            case 3 -> {
                return getGeometry(pts[0], pts[1], pts[2], oom, rm);
            }
            default -> {
                V3D_FiniteGeometry g = getGeometry(pts[0], pts[1], pts[2], oom, rm);
                for (int i = 3; i < length; i++) {
                    if (g instanceof V3D_Point gp) {
                        g = getGeometry(gp, pts[i], oom, rm);
                    } else {
                        g = getGeometry((V3D_LineSegment) g, pts[i], oom, rm);
                    }
                }
                return g;
            }
        }
    }

    /**
     * Calculates the shortest line segment which all line segments intersect
     * with.
     *
     * @param ls Collinear line segments.
     * @return The shortest line segment which all the line segment points in ls
     * intersect with.
     */
    public static V3D_LineSegment getGeometry(int oom, RoundingMode rm,
            V3D_LineSegment... ls) {
        switch (ls.length) {
            case 0 -> {
                return null;
            }
            case 1 -> {
                return ls[0];
            }
            default -> {
                V3D_LineSegment r = getGeometry(ls[0], ls[1].getP(), oom, rm);
                r = getGeometry(r, ls[1].getQ(oom, rm), oom, rm);
                for (int i = 1; i < ls.length; i++) {
                    r = getGeometry(r, ls[i].getP(), oom, rm);
                    r = getGeometry(r, ls[i].getQ(oom, rm), oom, rm);
                }
                return r;
            }
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
        if (getIntersect(l, oom, rm) != null) {
            return null;
        }
        V3D_LineSegment loi = this.l.getLineOfIntersection(l, oom, rm);
        V3D_Point tp = getP();
        V3D_Point tq = getQ(oom, rm);
        if (loi == null) {
            BigRational pd = l.getDistanceSquared(tp, oom, rm);
            BigRational qd = l.getDistanceSquared(tq, oom, rm);
            if (pd.compareTo(qd) == 1) {
                return new V3D_LineSegment(tq, l.getPointOfIntersect(tq, true, oom, rm), oom, rm);
            } else {
                return new V3D_LineSegment(tp, l.getPointOfIntersect(tp, true, oom, rm), oom, rm);
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
        V3D_FiniteGeometry ilsl = getIntersect(ls, oom, rm);
        if (ilsl == null) {
            V3D_Point lsp = ls.getP();
            V3D_Point lsq = ls.getQ(oom, rm);
            V3D_Point tp = getP();
            V3D_Point tq = getQ(oom, rm);
            // Get the line of intersection between this and ls.l
            V3D_LineSegment tloi = getLineOfIntersection(ls.l, oom, rm);
            V3D_LineSegment lsloi = ls.getLineOfIntersection(l, oom, rm);
            if (tloi == null) {
                V3D_Point tip;
                V3D_Point lsloiq = lsloi.getQ(oom, rm);
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
                V3D_Point tloiq = tloi.getQ(oom, rm);
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
     *
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
     *
     * @return The plane with a point at l.getQ() and normal l.v
     */
    public V3D_Plane getQPL(int oom, RoundingMode rm) {
        if (qpl == null) {
            qpl = new V3D_Plane(getQ(oom, rm), l.v);
        }
        return qpl;
    }

    /**
     * Useful for intersection tests.
     *
     * @param pt The point to test if it is between {@link #qv} and the point of
     * {@link #l}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff pt lies between the planes at the end of the
     * line segment.
     */
    public boolean isBetween(V3D_Point pt, int oom, RoundingMode rm) {
        if (getPPL().isOnSameSide(pt, getQ(oom, rm), oom, rm)) {
            if (getQPL(oom, rm).isOnSameSide(pt, getP(), oom, rm)) {
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
     * @return The nearest point on {@code l} to {@code pv}.
     */
    protected static V3D_Point getNearestPoint(V3D_LineSegment l, V3D_Point pt, int oom, RoundingMode rm) {
        V3D_Point lp = l.getP();
        V3D_Point lq = l.getQ(oom, rm);
        BigRational dlpp = lp.getDistanceSquared(pt, oom, rm);
        BigRational dlqp = lq.getDistanceSquared(pt, oom, rm);
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
    public BigRational getDistance(V3D_Line l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code l}.
     */
    public BigRational getDistanceSquared(V3D_Line l, int oom, RoundingMode rm) {
        if (getIntersect(l, oom, rm) != null) {
            return BigRational.ZERO;
        }
        V3D_LineSegment loi = getLineOfIntersection(l, oom, rm);
        if (loi == null) {
            // Lines are parallel.
            return l.getDistanceSquared(getP(), oom, rm);
        }
        return loi.getLength2(oom, rm);
    }

    @Override
    public V3D_LineSegment rotate(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd,
            BigRational theta, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V3D_LineSegment(this);
        } else {
            return rotateN(ray, uv, bd, theta, oom, rm);
        }
    }

    @Override
    public V3D_LineSegment rotateN(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd,
            BigRational theta, int oom, RoundingMode rm) {
        return new V3D_LineSegment(
                getP().rotateN(ray, uv, bd, theta, oom, rm),
                getQ(oom, rm).rotateN(ray, uv, bd, theta, oom, rm), oom, rm);
    }

    /**
     * Clips this using pl and returns the part that is on the same side as pt.
     *
     * @param pl The plane that clips.
     * @param pt A point that is used to return the side of the clipped line
     * segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometry clip(V3D_Plane pl, V3D_Point pt, int oom, RoundingMode rm) {
        V3D_FiniteGeometry i = pl.getIntersect(this, oom, rm);
        V3D_Point tp = getP();
        if (i == null) {
            if (pl.isOnSameSide(tp, pt, oom, rm)) {
                return this;
            } else {
                return null;
            }
        } else if (i instanceof V3D_Point ip) {
            if (pl.isOnSameSide(tp, pt, oom, rm)) {
                return V3D_LineSegment.getGeometry(ip, tp, oom, rm);
            } else {
                V3D_Point tq = this.getQ(oom, rm);
                return V3D_LineSegment.getGeometry(ip, tq, oom, rm);
            }
        } else {
            return this;
        }
    }

}
