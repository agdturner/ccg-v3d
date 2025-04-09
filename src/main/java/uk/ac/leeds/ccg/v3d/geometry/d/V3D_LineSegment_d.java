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
import java.util.Collection;
import uk.ac.leeds.ccg.math.arithmetic.Math_Double;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

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
public class V3D_LineSegment_d extends V3D_FiniteGeometry_d {

    private static final long serialVersionUID = 1L;

    /**
     * The line of which this line segment is part and for which the point on
     * the line is one end point of this segment.
     */
    public final V3D_Line_d l;

    /**
     * For storing the plane at l.getP() with a normal given l.v.
     */
    protected V3D_Plane_d ppl;

    /**
     * For storing the plane at getQ() with a normal given by l.v.
     */
    protected V3D_Plane_d qpl;

    /**
     * For storing the length of the line squared.
     */
    protected double len2;

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is cloned from.
     */
    public V3D_LineSegment_d(V3D_LineSegment_d l) {
        super(l.env, l.offset);
        this.l = new V3D_Line_d(l.l);
    }

    /**
     * Create a new instance. {@link #offset} is set to {@link V3D_Vector#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param p What the point of {@link #l} is cloned from.
     * @param q What {@link #qv} is cloned from.
     */
    public V3D_LineSegment_d(V3D_Environment_d env, V3D_Vector_d p,
            V3D_Vector_d q) {
        this(env, V3D_Vector_d.ZERO, p, q);
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param pv Used to initialise {@link #l}.
     * @param v Used to initialise {@link #l}.
     */
    public V3D_LineSegment_d(V3D_Environment_d env, V3D_Vector_d offset,
            V3D_Vector_d pv, V3D_Vector_d v) {
        super(env, offset);
        l = new V3D_Line_d(env, offset, pv, v);
    }

    /**
     * Create a new instance.
     *
     * @param p Used to initialise {@link #l}.
     * @param q Used to initialise {@link #l} and {@link #q}.
     */
    public V3D_LineSegment_d(V3D_Point_d p, V3D_Point_d q) {
        super(p.env, p.offset);
        l = new V3D_Line_d(p, q);
    }

    /**
     * Create a new instance that intersects all points.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param points Any number of collinear points, with two being different.
     */
    public V3D_LineSegment_d(double epsilon, ArrayList<V3D_Point_d> points) {
        this(epsilon, points.toArray(V3D_Point_d[]::new));
    }

    /**
     * Create a new instance that intersects all points.
     *
     * @param points Any number of collinear points, with two being different.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V3D_LineSegment_d(double epsilon, V3D_Point_d... points) {
        super(points[0].env, points[0].offset);
        V3D_Point_d p0 = points[0];
        V3D_Point_d p1 = points[1];
        V3D_LineSegment_d ls = new V3D_LineSegment_d(p0, p1);
        for (int i = 2; i < points.length; i++) {
            if (!ls.intersects(points[i], epsilon)) {
                V3D_LineSegment_d l2 = new V3D_LineSegment_d(ls.getP(), points[i]);
                V3D_Point_d lq = ls.getQ();
                if (l2.intersects(lq, epsilon)) {
                    ls = l2;
                } else {
                    ls = new V3D_LineSegment_d(ls.getQ(), points[i]);
                }
            }
        }
        this.l = ls.l;
    }

    /**
     * @return {@link #l} pv with {@link #l} offset applied.
     */
    public V3D_Point_d getP() {
        return l.getP();
    }

    /**
     * @return {@link #qv} with {@link #offset} applied.
     */
    public V3D_Point_d getQ() {
        return l.getQ();
    }

    /**
     * Translate (move relative to the origin).
     *
     */
    @Override
    public void translate(V3D_Vector_d v) {
        super.translate(v);
        l.translate(v);
        if (ppl != null) {
            ppl.translate(v);
        }
        if (qpl != null) {
            qpl.translate(v);
        }
    }

    @Override
    public V3D_Point_d[] getPointsArray() {
        V3D_Point_d[] r = new V3D_Point_d[2];
        r[0] = getP();
        r[1] = getQ();
        return r;
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_LineSegment_d(V3D_Line_d l) {
        super(l.env, l.offset);
        this.l = new V3D_Line_d(l);
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(double epsilon, V3D_LineSegment_d l) {
        if (equalsIgnoreDirection(epsilon, l)) {
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
    public boolean equalsIgnoreDirection(double epsilon, V3D_LineSegment_d l) {
//        if (l == null) {
//            return false;
//        }
        if (this.l.equals(epsilon, l.l)) {
            return intersects(l.getQ(), epsilon)
                    && l.intersects(getQ(), epsilon);
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
     * @return {@code new V3D_AABB(start, end)}
     */
    @Override
    public V3D_AABB_d getAABB() {
        if (en == null) {
            en = new V3D_AABB_d(getP(), getQ());
        }
        return en;
    }

    /**
     * @param pt A point to test for intersection.
     * @return {@code true} if {@code this} is intersected by {@code pv}.
     */
    public boolean intersects(V3D_Point_d pt) {
        boolean ei = getAABB().intersects(pt.getAABB());
        if (ei) {
            if (l.intersects(pt)) {
                V3D_Point_d tp = getP();
                double a = pt.getDistance(tp);
                if (a == 0d) {
                    return true;
                }
                V3D_Point_d tq = getQ();
                double b = pt.getDistance(tq);
                if (b == 0d) {
                    return true;
                }
                double d = tp.getDistance(tq);
                double apb = a + b;
                return apb == d;
            }
        }
        return false;
    }

    /**
     * This first checks that {@code p} intersects the Axis Aligned Bounding
     * Box.
     *
     * @param p A point to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is intersected by {@code p}.
     */
    public boolean intersects(V3D_Point_d p, double epsilon) {
        if (getAABB().intersects(p.getAABB(), epsilon)) {
            return intersects0(p, epsilon);
        } else {
            return false;
        }
    }

    /**
     * This does not first checks that {@code p} intersects the Axis Aligned
     * Bounding Box.
     *
     * @param p A point to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is intersected by {@code p}.
     */
    public boolean intersects0(V3D_Point_d p, double epsilon) {
        if (l.intersects(p, epsilon)) {
            V3D_Point_d tp = getP();
            double a = p.getDistance(tp);
            if (a == 0d) {
                return true;
            }
            V3D_Point_d tq = getQ();
            double b = p.getDistance(tq);
            if (b == 0d) {
                return true;
            }
            double d = tp.getDistance(tq);
            double apb = a + b;
            return Math_Double.equals(apb, d, epsilon);
        } else {
            return false;
        }
    }

    /**
     * @param aabb The V3D_AABB to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public boolean intersects(V3D_AABB_d aabb, double epsilon) {
        /**
         * Test for intersection with each 2D AABB part aabb and point.
         */
        return aabb.intersects(l.getP())
                || aabb.intersects(l.getQ())
                || intersects(aabb.getl(), epsilon)
                || intersects(aabb.getr(), epsilon)
                || intersects(aabb.gett(), epsilon)
                || intersects(aabb.getb(), epsilon)
                || intersects(aabb.getf(), epsilon)
                || intersects(aabb.geta(), epsilon);
    }

    /**
     * @param aabbx The Axis Aligned Bounding Box to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is intersected by {@code aabb}.
     */
    public boolean intersects(V3D_AABBX_d aabbx, double epsilon) {
        V3D_Plane_d aabxpl = aabbx.getXPl();
        if (aabxpl.isOnSameSideNotOn(getP(), getQ(), epsilon)) {
            return false;
        } else {
            if (aabxpl.isOnPlane(l)) {
                // 2D AABB interesection
                V3D_AABBX_d aabbx2 = new V3D_AABBX_d(getP(), getQ());
                if (aabbx.intersects(aabbx2)) {
                    if (aabbx2.intersects(getP())
                            || aabbx2.intersects(getQ())) {
                        return true;
                    } else {
                        V3D_FiniteGeometry_d left = aabbx.getLeft();
                        if (left instanceof V3D_LineSegment_d ll) {
                            if (intersects0(ll, epsilon)) {
                                return true;
                            }
                        } else {
                            if (intersects0((V3D_Point_d) left, epsilon)) {
                                return true;
                            }
                        }
                        V3D_FiniteGeometry_d r = aabbx.getRight();
                        if (r instanceof V3D_LineSegment_d rl) {
                            if (intersects0(rl, epsilon)) {
                                return true;
                            }
                        } else {
                            if (intersects0((V3D_Point_d) r, epsilon)) {
                                return true;
                            }
                        }
                        V3D_FiniteGeometry_d t = aabbx.getTop();
                        if (t instanceof V3D_LineSegment_d tl) {
                            if (intersects0(tl, epsilon)) {
                                return true;
                            }
                        } else {
                            if (intersects0((V3D_Point_d) t, epsilon)) {
                                return true;
                            }
                        }
                        V3D_FiniteGeometry_d b = aabbx.getBottom();
                        if (b instanceof V3D_LineSegment_d bl) {
                            if (intersects0(bl, epsilon)) {
                                return true;
                            }
                        } else {
                            if (intersects0((V3D_Point_d) b, epsilon)) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            } else {
                return aabbx.intersects(aabxpl.getIntersect0(this, epsilon));
            }
        }
    }

    /**
     * @param aabby The Axis Aligned Bounding Box to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is intersected by {@code aabb}.
     */
    public boolean intersects(V3D_AABBY_d aabby, double epsilon) {
        V3D_Plane_d aabypl = aabby.getYPl();
        if (aabypl.isOnSameSideNotOn(getP(), getQ(), epsilon)) {
            return false;
        } else {
            if (aabypl.isOnPlane(l)) {
                // 2D AABB interesection
                V3D_AABBY_d aabby2 = new V3D_AABBY_d(getP(), getQ());
                if (aabby.intersects(aabby2)) {
                    if (aabby2.intersects(getP())
                            || aabby2.intersects(getQ())) {
                        return true;
                    } else {
                        V3D_FiniteGeometry_d left = aabby.getLeft();
                        if (left instanceof V3D_LineSegment_d ll) {
                            if (intersects0(ll, epsilon)) {
                                return true;
                            }
                        } else {
                            if (intersects0((V3D_Point_d) left, epsilon)) {
                                return true;
                            }
                        }
                        V3D_FiniteGeometry_d r = aabby.getRight();
                        if (r instanceof V3D_LineSegment_d rl) {
                            if (intersects0(rl, epsilon)) {
                                return true;
                            }
                        } else {
                            if (intersects0((V3D_Point_d) r, epsilon)) {
                                return true;
                            }
                        }
                        V3D_FiniteGeometry_d t = aabby.getTop();
                        if (t instanceof V3D_LineSegment_d tl) {
                            if (intersects0(tl, epsilon)) {
                                return true;
                            }
                        } else {
                            if (intersects0((V3D_Point_d) t, epsilon)) {
                                return true;
                            }
                        }
                        V3D_FiniteGeometry_d b = aabby.getBottom();
                        if (b instanceof V3D_LineSegment_d bl) {
                            if (intersects0(bl, epsilon)) {
                                return true;
                            }
                        } else {
                            if (intersects0((V3D_Point_d) b, epsilon)) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            } else {
                return aabby.intersects(aabypl.getIntersect0(this, epsilon));
            }
        }
    }

    /**
     * @param aabbz The Axis Aligned Bounding Box to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is intersected by {@code aabb}.
     */
    public boolean intersects(V3D_AABBZ_d aabbz, double epsilon) {
        V3D_Plane_d aabzpl = aabbz.getZPl();
        if (aabzpl.isOnSameSideNotOn(getP(), getQ(), epsilon)) {
            return false;
        } else {
            if (aabzpl.isOnPlane(l)) {
                // 2D AABB interesection
                V3D_AABBZ_d aabbz2 = new V3D_AABBZ_d(getP(), getQ());
                if (aabbz.intersects(aabbz2)) {
                    if (aabbz2.intersects(getP())
                            || aabbz2.intersects(getQ())) {
                        return true;
                    } else {
                        V3D_FiniteGeometry_d left = aabbz.getLeft();
                        if (left instanceof V3D_LineSegment_d ll) {
                            if (intersects0(ll, epsilon)) {
                                return true;
                            }
                        } else {
                            if (intersects0((V3D_Point_d) left, epsilon)) {
                                return true;
                            }
                        }
                        V3D_FiniteGeometry_d r = aabbz.getRight();
                        if (r instanceof V3D_LineSegment_d rl) {
                            if (intersects0(rl, epsilon)) {
                                return true;
                            }
                        } else {
                            if (intersects0((V3D_Point_d) r, epsilon)) {
                                return true;
                            }
                        }
                        V3D_FiniteGeometry_d t = aabbz.getTop();
                        if (t instanceof V3D_LineSegment_d tl) {
                            if (intersects0(tl, epsilon)) {
                                return true;
                            }
                        } else {
                            if (intersects0((V3D_Point_d) t, epsilon)) {
                                return true;
                            }
                        }
                        V3D_FiniteGeometry_d b = aabbz.getBottom();
                        if (b instanceof V3D_LineSegment_d bl) {
                            if (intersects0(bl, epsilon)) {
                                return true;
                            }
                        } else {
                            if (intersects0((V3D_Point_d) b, epsilon)) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            } else {
                return aabbz.intersects(aabzpl.getIntersect0(this, epsilon));
            }
        }
    }

    /**
     * NB. The intersection is computed, so if that is needed use:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.V3D_Line, int, java.math.RoundingMode)}.
     *
     * @param l The V3D_Line to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public boolean intersects(V3D_Line_d l, double epsilon) {
        return getIntersect(l, epsilon) != null;
    }

    /**
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param l A line to test for with any of the lines in ls.
     * @param ls The other lines to test for intersection with l.
     * @return {@code true} if {@code this} is intersected by {@code pv}.
     */
    public static boolean intersects(double epsilon,
            V3D_LineSegment_d l, V3D_LineSegment_d... ls) {
        return intersects(epsilon, l, Arrays.asList(ls));
    }

    /**
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param l A line to test for with any of the lines in ls.
     * @param ls The other lines to test for intersection with l.
     * @return {@code true} if {@code this} is intersected by {@code pv}.
     */
    public static boolean intersects(double epsilon,
            V3D_LineSegment_d l, Collection<V3D_LineSegment_d> ls) {
        return ls.parallelStream().anyMatch(x -> x.intersects(l, epsilon));
    }

    /**
     * @param p A point to test for intersection.
     * @param ls The lines to test for intersection with p.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is intersected by {@code pv}.
     */
    public static boolean intersects(double epsilon, V3D_Point_d p,
            V3D_LineSegment_d... ls) {
        return intersects(epsilon, p, Arrays.asList(ls));
    }

    /**
     * @param p A point to test for intersection.
     * @param ls The lines to test for intersection with p.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is intersected by {@code pv}.
     */
    public static boolean intersects(double epsilon, V3D_Point_d p,
            Collection<V3D_LineSegment_d> ls) {
        return ls.parallelStream().anyMatch(x -> x.intersects(p, epsilon));
    }

    /**
     * If there is intersection with the Axis Aligned Bounding Box, then the
     * intersection is computed, so if that is needed use:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineSegment_d, double) }
     *
     * @param l The line segment to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public boolean intersects(V3D_LineSegment_d l, double epsilon) {
        if (getAABB().intersects(l.getAABB(), epsilon)) {
            return getIntersect0(l, epsilon) != null;
        } else {
            return false;
        }
    }
    
    /**
     * This does not first test for intersection of Axis Aligned Bounding Boxes.
     * The intersection is computed, so if that is needed use:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineSegment_d, double) }
     *
     * @param l The line segment to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public boolean intersects0(V3D_LineSegment_d l, double epsilon) {
        return getIntersect0(l, epsilon) != null;
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
    public V3D_FiniteGeometry_d getIntersect(V3D_Line_d l,
            double epsilon) {
        // Check if infinite lines intersect.
        V3D_Geometry_d i = this.l.getIntersect(l, epsilon);
        if (i == null) {
            return null;
        }
        /**
         * If infinite lines intersects at a point, then check this point is on
         * this.
         */
        if (i instanceof V3D_Point_d ip) {
            //if (intersects(ip, epsilon)) {
            if (isAligned(ip, epsilon)) {
                return ip;
            } else {
                return null;
            }
        } else if (i instanceof V3D_Line_d) {
            return this;
        } else {
            return null;
        }
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if the two line
     * segments do not intersect. This first conducts a bounding box
     * intersection test...
     *
     * @param ls The line to get intersection with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_LineSegment_d ls,
            double epsilon) {
        if (!getAABB().intersects(ls.getAABB())) {
            return null;
        } else {
            return getIntersect0(ls, epsilon);
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
    public V3D_FiniteGeometry_d getIntersect0(V3D_LineSegment_d ls,
            double epsilon) {
        // Get intersection with infinite lines.
        V3D_Geometry_d li = l.getIntersect(ls.l, epsilon);
        V3D_FiniteGeometry_d tils = getIntersect(ls.l, epsilon);
        V3D_FiniteGeometry_d lsil = ls.getIntersect(l, epsilon);
        if (li == null) {
            return null;
        } else {
            if (tils == null) {
                if (lsil == null) {
                    return null;
                } else {
                    if (lsil instanceof V3D_Point_d lsilp) {
                        //if (intersects(lsilp, epsilon)) {
                        if (isAligned(lsilp, epsilon)) {
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
                if (tils instanceof V3D_Point_d tilsp) {
                    //if (intersects(tilsp, epsilon) && ls.intersects(tilsp, epsilon)) {
                    if (isAligned(tilsp, epsilon) && ls.isAligned(tilsp, epsilon)) {
                        return tilsp;
                    } else {
                        return null;
                    }
                } else {
                    return getIntersectLS(epsilon, ls);
                }
            }
        }
    }

    /**
     * Use when this and ls are collinear and intersect.
     *
     * @param ls An intersecting collinear line segment.
     * @param epsilon
     * @return The intersection.
     */
    public V3D_FiniteGeometry_d getIntersectLS(double epsilon,
            V3D_LineSegment_d ls) {
        /**
         * Check the type of intersection. {@code
         *
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
        V3D_Point_d lp = ls.getP();
        V3D_Point_d lq = ls.getQ();
        V3D_Point_d tp = getP();
        V3D_Point_d tq = getQ();
        //if (intersects(lp, epsilon)) {
        if (isAligned(lp, epsilon)) {
            // Cases: 1, 2, 3, 5, 8, 9, 10, 12, 17, 19, 20, 21, 24, 26, 27, 28
            //if (intersects(lq, epsilon)) {
            if (isAligned(lq, epsilon)) {
                // Cases: 3, 5, 10, 12, 17, 19, 24, 26
                return ls;
            } else {
                // Cases: 1, 2, 8, 9, 20, 21, 27, 28
                //if (ls.intersects(tp, epsilon)) {
                if (ls.isAligned(tp, epsilon)) {
                    // Cases: 8, 9, 20, 21
                    if (tp.equals(lp, epsilon)) {
                        // Cases: 8, 21
                        return tp;
                    } else {
                        // Cases: 9, 20
                        return V3D_LineSegment_d.getGeometry(lp, tp, epsilon);
                    }
                } else {
                    // Case: 1, 2, 27, 28
                    if (lp.equals(tq, epsilon)) {
                        // Case: 1, 28
                        return lp;
                    } else {
                        // Cases: 2, 27
                        return V3D_LineSegment_d.getGeometry(lp, tq, epsilon);
                    }
                }
            }
        } else {
            // Cases: 4, 6, 7, 11, 13, 14, 15, 16, 18, 22, 23, 25
            //if (intersects(lq, epsilon)) {
            if (isAligned(lq, epsilon)) {
                // Cases: 6, 7, 13, 14, 15, 16, 22, 23
                //if (ls.intersects(tp, epsilon)) {
                if (ls.isAligned(tp, epsilon)) {
                    // Cases: 6, 7, 22, 23
                    //if (ls.intersects(tq, epsilon)) {
                    if (ls.isAligned(tq, epsilon)) {
                        // Case: 23
                        return V3D_LineSegment_d.getGeometry(lq, tp, epsilon);
                    } else {
                        // Cases: 6, 7, 22, 
                        if (tp.equals(lq, epsilon)) {
                            // Cases: 7, 22
                            return tp;
                        } else {
                            // Case: 6
                            return V3D_LineSegment_d.getGeometry(tp, lq, epsilon);
                        }
                    }
                } else {
                    // Cases: 13, 14, 15, 16
                    if (tq.equals(lq, epsilon)) {
                        // Cases: 14, 15
                        return tq;
                    } else {
                        // Case: 13, 16
                        return V3D_LineSegment_d.getGeometry(tq, lq, epsilon);
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance between this and {@code pv}.
     */
    public double getDistance(V3D_Point_d p, double epsilon) {
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
    public double getDistanceSquared(V3D_Point_d pt, double epsilon) {
        if (intersects(pt, epsilon)) {
            return 0d;
        }
        V3D_Point_d poi = l.getPointOfIntersect(pt, epsilon);
        if (isAligned(poi, epsilon)) {
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} If pt is in line with this.
     */
    public boolean isAligned(V3D_Point_d pt, double epsilon) {
        return getPPL().isOnSameSide(pt, getQ(), epsilon)
                && getQPL().isOnSameSide(pt, getP(), epsilon);
    }

    /**
     * Calculates and returns if l is in line with this. It is in line if both
     * end points of l are in line with this as according to
     * {@link #isAligned(uk.ac.leeds.ccg.v3d.geometry.d.V3D_Point_d)}.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param l The line segment.
     * @return {@code true} If pt is in line with this.
     */
    public boolean isAligned(V3D_LineSegment_d l, double epsilon) {
        return isAligned(l.getP(), epsilon)
                && isAligned(l.getQ(), epsilon);
    }

    /**
     * @param l The line segment to return the distance from.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance from {@code this} to {@code l} at the {@code oom}
     * precision.
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
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistanceSquared(V3D_LineSegment_d l, double epsilon) {
        if (getIntersect(l, epsilon) != null) {
            return 0d;
        }
        V3D_LineSegment_d loi = getLineOfIntersect(l, epsilon);
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
    public V3D_Point_d getMidpoint() {
        V3D_Vector_d pmpq = l.v.divide(2.0d);
        return new V3D_Point_d(env, offset, l.pv.add(pmpq));
    }

    /**
     * For returning the other end of the line segment as a point.
     *
     * @param pt A point equal to either {@link #getP()} or the point of
     * {@link #l}.
     * @return The other point that is not equal to a.
     */
    public V3D_Point_d getOtherPoint(V3D_Point_d pt) {
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
    public static V3D_FiniteGeometry_d getGeometry(V3D_Point_d p,
            V3D_Point_d q, double epsilon) {
        if (p.equals(q, epsilon)) {
            return p;
        } else {
            return new V3D_LineSegment_d(p, q);
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
    public static V3D_FiniteGeometry_d getGeometry(V3D_Point_d p,
            V3D_Point_d q, V3D_Point_d r, double epsilon) {
        if (p.equals(q, epsilon)) {
            return getGeometry(epsilon, p, r);
        } else if (q.equals(r, epsilon)) {
            return getGeometry(epsilon, p, r);
        } else if (p.equals(r, epsilon)) {
            return getGeometry(epsilon, p, q);
        } else {
            //V3D_LineSegment_d ls = new V3D_LineSegment_d(epsilon, p, q);
            //if (ls.intersects(r, epsilon)) {
            if (r.isAligned(p, q, epsilon)) {
                //return ls;
                return new V3D_LineSegment_d(epsilon, p, q);
            } else {
                //ls = new V3D_LineSegment_d(epsilon, p, r);
                //if (ls.intersects(q, epsilon)) {
                if (p.isAligned(r, q, epsilon)) {
                    //return ls;
                    return new V3D_LineSegment_d(epsilon, q, r);
                } else {
                    return new V3D_LineSegment_d(epsilon, q, r);
                }
            }
        }
    }

    /**
     * Calculates the shortest line segment which l and pt intersect with.
     *
     * @param l A line segment.
     * @param pt A point collinear with l.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The shortest line segment which l and pt intersect with.
     */
    public static V3D_LineSegment_d getGeometry(V3D_LineSegment_d l,
            V3D_Point_d pt, double epsilon) {
        return (V3D_LineSegment_d) getGeometry(epsilon, l.getP(), l.getQ(), pt);
    }

    /**
     * Calculates the shortest line segment which all line segments intersect
     * with.
     *
     * @param ls Collinear line segments.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The shortest line segment which all line segments in ls intersect
     * with.
     */
    public static V3D_LineSegment_d getGeometry(double epsilon,
            V3D_LineSegment_d... ls) {
        switch (ls.length) {
            case 0 -> {
                return null;
            }
            case 1 -> {
                return ls[0];
            }
            default -> {
                V3D_LineSegment_d r = getGeometry(ls[0], ls[1].getP(), epsilon);
                r = getGeometry(r, ls[1].getQ(), epsilon);
                for (int i = 1; i < ls.length; i++) {
                    r = getGeometry(r, ls[i].getP(), epsilon);
                    r = getGeometry(r, ls[i].getQ(), epsilon);
                }
                return r;
            }

        }
    }

    /**
     * Calculate and return the smallest line segment intersected by all pts.
     *
     * @param pts Collinear points.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The smallest line segment intersected by all pts
     */
    public static V3D_FiniteGeometry_d getGeometry(double epsilon, V3D_Point_d... pts) {
        int length = pts.length;
        switch (length) {
            case 0 -> {
                return null;
            }
            case 1 -> {
                return pts[0];
            }
            case 2 -> {
                return getGeometry(pts[0], pts[1], epsilon);
            }
            case 3 -> {
                return getGeometry(pts[0], pts[1], pts[2], epsilon);
            }
            default -> {
                V3D_FiniteGeometry_d g = getGeometry(epsilon, pts[0], pts[1], pts[2]);
                for (int i = 3; i < length; i++) {
                    if (g instanceof V3D_Point_d gp) {
                        g = getGeometry(gp, pts[i], epsilon);
                    } else {
                        g = getGeometry((V3D_LineSegment_d) g, pts[i], epsilon);
                    }
                }
                return g;
            }

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
    public V3D_LineSegment_d getLineOfIntersect(V3D_Line_d l,
            double epsilon) {
        if (getIntersect(l, epsilon) != null) {
            return null;
        }
        V3D_LineSegment_d loi = this.l.getLineOfIntersect(l, epsilon);
        V3D_Point_d tp = getP();
        V3D_Point_d tq = getQ();
        if (loi == null) {
            double pd = l.getDistanceSquared(tp, epsilon);
            double qd = l.getDistanceSquared(tq, epsilon);
            if (pd > qd) {
                return new V3D_LineSegment_d(tq, l.getPointOfIntersect(tq, epsilon));
            } else {
                return new V3D_LineSegment_d(tp, l.getPointOfIntersect(tp, epsilon));
            }
        } else {
            V3D_Point_d lsp = loi.getP();
            V3D_Point_d lsq = loi.getQ();
            V3D_Plane_d plp = new V3D_Plane_d(tp, l.v);
            V3D_Plane_d plq = new V3D_Plane_d(tq, l.v);
            if (plp.isOnSameSide(lsp, tq, epsilon)) {
                if (plq.isOnSameSide(lsp, tp, epsilon)) {
                    /**
                     * The line of intersection connects in the line segment, so
                     * return it.
                     */
                    return loi;
                } else {
                    return new V3D_LineSegment_d(tq, lsq);
                }
            } else {
                if (plq.isOnSameSide(lsp, tp, epsilon)) {
                    return new V3D_LineSegment_d(tp, lsq);
                } else {
                    return new V3D_LineSegment_d(tp, lsp);
                }
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
    public V3D_LineSegment_d getLineOfIntersect(V3D_LineSegment_d ls,
            double epsilon) {
        V3D_FiniteGeometry_d ilsl = getIntersect(ls, epsilon);
        if (ilsl == null) {
            V3D_Point_d lsp = ls.getP();
            V3D_Point_d lsq = ls.getQ();
            V3D_Point_d tp = getP();
            V3D_Point_d tq = getQ();
            // Get the line of intersection between this and ls.l
            V3D_LineSegment_d tloi = getLineOfIntersect(ls.l, epsilon);
            V3D_LineSegment_d lsloi = ls.getLineOfIntersect(l, epsilon);
            if (tloi == null) {
                V3D_Point_d tip;
                V3D_Point_d lsloiq = lsloi.getQ();
                // Is the intersection point on this within the line segment?
                // Can use isAligned to do this more clearly?
                V3D_Plane_d tppl = new V3D_Plane_d(tp, l.v);
                V3D_Plane_d tqpl = new V3D_Plane_d(tq, l.v);
                if (tppl.isOnSameSide(lsloiq, tq, epsilon)) {
                    if (tqpl.isOnSameSide(lsloiq, tp, epsilon)) {
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
                return new V3D_LineSegment_d(tip, lsloiq);
            } else {
                V3D_Point_d tloip = tloi.getP(); // This is the end of the line segment on this.
                V3D_Point_d tloiq = tloi.getQ();
                if (lsloi == null) {
                    V3D_Point_d lsip;
                    // Is the intersection point on ls within the line segment?
                    // Can use isAligned to do this more clearly?
                    V3D_Plane_d lsppl = new V3D_Plane_d(lsp, ls.l.v);
                    if (lsppl.isOnSameSide(tloiq, lsq, epsilon)) {
                        V3D_Plane_d lsqpl = new V3D_Plane_d(lsq, ls.l.v);
                        if (lsqpl.isOnSameSide(tloiq, lsp, epsilon)) {
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
                    return new V3D_LineSegment_d(tloip, lsip);
                    //return new V3D_LineSegment(tq, lsip);
                    //return new V3D_LineSegment(tp, lsip);
                    //return new V3D_LineSegment(tloiq, getNearestPoint(this, tloiq));
                } else {
                    if (isAligned(tloip, epsilon)) {
                        if (ls.isAligned(tloiq, epsilon)) {
                            return new V3D_LineSegment_d(tloip, tloiq);
                        } else {
                            return new V3D_LineSegment_d(tloip, getNearestPoint(ls, tloip));
                        }
                    } else {
                        return new V3D_LineSegment_d(
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
    public V3D_Plane_d getPPL() {
        if (ppl == null) {
            ppl = new V3D_Plane_d(l.getP(), l.v);
        }
        return ppl;
    }

    /**
     * Useful for intersection tests.
     *
     * @return The plane with a point at l.getQ() and normal l.v
     */
    public V3D_Plane_d getQPL() {
        if (qpl == null) {
            qpl = new V3D_Plane_d(getQ(), l.v);
        }
        return qpl;
    }

    /**
     * @param l A line segment.
     * @param pt Is on the line of {@code l}, but not on {@code l}.
     * @return The nearest point on {@code l} to {@code pv}.
     */
    protected static V3D_Point_d getNearestPoint(V3D_LineSegment_d l,
            V3D_Point_d pt) {
        V3D_Point_d lp = l.getP();
        V3D_Point_d lq = l.getQ();
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
        if (getIntersect(l, epsilon) != null) {
            return 0d;
        }
        V3D_LineSegment_d loi = getLineOfIntersect(l, epsilon);
        if (loi == null) {
            // Lines are parallel.
            return l.getDistanceSquared(getP(), epsilon);
        }
        return loi.getLength2();
    }

    @Override
    public V3D_LineSegment_d rotate(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V3D_LineSegment_d(this);
        } else {
            return rotateN(ray, uv, theta, epsilon);
        }
    }

    @Override
    public V3D_LineSegment_d rotateN(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        return new V3D_LineSegment_d(
                getP().rotate(ray, uv, theta, epsilon),
                getQ().rotate(ray, uv, theta, epsilon));
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
    public V3D_FiniteGeometry_d clip(V3D_Plane_d pl, V3D_Point_d pt,
            double epsilon) {
        V3D_FiniteGeometry_d i = pl.getIntersect(this, epsilon);
        V3D_Point_d tp = getP();
        if (i == null) {
            if (pl.isOnSameSide(tp, pt, epsilon)) {
                return this;
            } else {
                return null;
            }
        } else if (i instanceof V3D_Point_d ip) {
            if (pl.isOnSameSide(tp, pt, epsilon)) {
                return V3D_LineSegment_d.getGeometry(ip, tp, epsilon);
            } else {
                V3D_Point_d tq = getQ();
                return V3D_LineSegment_d.getGeometry(ip, tq, epsilon);
            }
        } else {
            return this;
        }
    }
}
