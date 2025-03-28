/*
 * Copyright 2022 Andy Turner, University of Leeds.
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

import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_Double;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

/**
 * 3D representation of an infinite length line. The line passes through the
 * point {@link #pv} with vector {@link #v}. The "*" denotes a point in 3D and
 * the line is shown with a line of "e" symbols in the following depiction: {@code
 *                                         z                e
 *                            y           -                e
 *                            +          /                * pv=<x0,y0,z0>
 *                            |         /                e
 *                            |        /                e
 *                            |    z0-/                e
 *                            |      /                e
 *                            |     /               e
 *                            |    /               e
 *                            |   /               e
 *                         y0-|  /               e
 *                            | /               e
 *                            |/         x1    e
 *   x - ---------------------|-----------/---e---/---- + x
 *                           /|              e   x0
 *                          / |-y1          e
 *                         /  |           e
 *                        /   |          e
 *                    z1-/    |         e
 *                      /     |        e
 *                     /      |       e v=(dx,dy,dz)
 *                    /       |      e
 *                   /        |     e
 *                  +         |    e
 *                 z          -   e
 *                            y
 * }
 * <ul>
 * <li>Vector Form
 * <ul>
 * <li>{@code (x,y,z) = (pv.getX(oom),pv.getY(oom),pv.getZ(oom)) + t(v.getDX(oom),v.getDY(oom),v.getDZ(oom))}</li>
 * </ul>
 * <li>Parametric Form (where t describes a particular point on the line)
 * <ul>
 * <li>{@code x = pv.getX(oom) + t(v.getDX(oom))}</li>
 * <li>{@code y = pv.getY(oom) + t(v.getDY(oom))}</li>
 * <li>{@code z = pv.getZ(oom) + t(v.getDZ(oom))}</li>
 * </ul>
 * <li>Symmetric Form (assume {@code v.getDX(oom)}, {@code v.getDY(oom)}, and
 * {@code v.getDZ(oom)} are all nonzero)
 * <ul>
 * <li>{@code (x−pv.getX(oom))/v.getDX(oom) = (y−pv.getY(oom))/v.getDY(oom) = (z−pv.getZ(oom))/v.getDZ(oom)}</li>
 * </ul></li>
 * </ul>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Line_d extends V3D_Geometry_d {

    private static final long serialVersionUID = 1L;

    /**
     * The x axis.
     */
    public static final V3D_Line_d X_AXIS = new V3D_Line_d(null,
            V3D_Vector_d.ZERO, V3D_Vector_d.I);

    /**
     * The y axis.
     */
    public static final V3D_Line_d Y_AXIS = new V3D_Line_d(null,
            V3D_Vector_d.ZERO, V3D_Vector_d.J);

    /**
     * The z axis.
     */
    public static final V3D_Line_d Z_AXIS = new V3D_Line_d(null,
            V3D_Vector_d.ZERO, V3D_Vector_d.K);

    /**
     * If this line is defined by a vector, then the calculation of {@link #q}
     * may be imprecise. If this line is defined by points, then {@link #v} may
     * have been imprecisely calculated.
     */
    public boolean isDefinedByVector;

    /**
     * Used to define {@link #p}.
     */
    protected V3D_Vector_d pv;

    /**
     * Used to store a point on the line as derived from {@link #offset} and
     * {@link #pv}.
     */
    protected V3D_Point_d p;

    /**
     * Another point on the line that is derived from {@link #offset},
     * {@link #pv} and {@link v}.
     */
    protected V3D_Point_d q;

    /**
     * The vector that defines the line. This will not change under translation,
     * but will change under rotation.
     */
    public V3D_Vector_d v;

    /**
     * @param l Used to initialise this.
     */
    public V3D_Line_d(V3D_Line_d l) {
        super(l.env, l.offset);
        this.pv = new V3D_Vector_d(l.pv);
        if (l.p != null) {
            this.p = new V3D_Point_d(l.p);
        }
        if (l.q != null) {
            this.q = new V3D_Point_d(l.q);
        }
        this.v = new V3D_Vector_d(l.v);
        this.isDefinedByVector = l.isDefinedByVector;
    }

    /**
     * @param l Used to initialise this.
     */
    public V3D_Line_d(V3D_LineSegment_d l) {
        this(l.l);
    }

    /**
     * {@code pv} should not be equal to {@code qv}. {@link #offset} is set to
     * {@link V3D_Vector_d#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param p What {@link #pv} is set to.
     * @param q Another point on the line from which {@link #v} is derived.
     */
    public V3D_Line_d(V3D_Environment_d env, V3D_Vector_d p, V3D_Vector_d q) {
        this(env, V3D_Vector_d.ZERO, p, q);
    }

    /**
     * {@code pv} should not be equal to {@code qv}.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param pv What {@link #pv} is cloned from.
     * @param qv Used to calculate {@link q} and {@link #v} (which is calculated
     * by taking the difference between pv and qv.
     */
    public V3D_Line_d(V3D_Environment_d env, V3D_Vector_d offset, V3D_Vector_d pv,
            V3D_Vector_d qv) {
        super(env, offset);
        this.pv = new V3D_Vector_d(pv);
        if (pv.equals(qv)) {
            throw new RuntimeException("" + pv + " and " + qv + " are the same"
                    + " so do not define a line.");
        }
        q = new V3D_Point_d(env, offset, qv);
        v = qv.subtract(pv);
        isDefinedByVector = false;
    }

    /**
     * {@code v} should not be the zero vector {@code <0,0,0>}. {@link #offset}
     * is set to {@link V3D_Vector_d#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param p What {@link #pv} is cloned from.
     * @param v The vector defining the line from {@link #pv}. What {@link #v}
     * is cloned from.
     * @param flag To distinguish this method from
     * {@link #V3D_Line_d(uk.ac.leeds.ccg.v3d.geometry.d.V3D_Vector_d, uk.ac.leeds.ccg.v3d.geometry.d.V3D_Vector_d)}
     */
    public V3D_Line_d(V3D_Environment_d env, V3D_Vector_d p, V3D_Vector_d v, boolean flag) {
        this(env, V3D_Vector_d.ZERO, p, v, flag);
    }

    /**
     * Checks to ensure v is not the zero vector {@code <0,0,0>}.
     *
     * @param p What {@link #pv} is set to.
     * @param v The vector defining the line from {@link #pv}.
     */
    public V3D_Line_d(V3D_Point_d p, V3D_Vector_d v) {
        this(p.env, p.offset, p.rel, v, true);
    }

    /**
     * Checks to ensure v is not the zero vector {@code <0,0,0>}.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #pv} is set to.
     * @param v The vector defining the line from {@link #pv}.
     * @param flag To distinguish this from
     * {@link #V3D_Line_d(uk.ac.leeds.ccg.v3d.geometry.d.V3D_Vector_d, uk.ac.leeds.ccg.v3d.geometry.d.V3D_Vector_d, uk.ac.leeds.ccg.v3d.geometry.d.V3D_Vector_d)}
     * @throws RuntimeException if {@code v.isZero()}.
     */
    public V3D_Line_d(V3D_Environment_d env, V3D_Vector_d offset, V3D_Vector_d p, V3D_Vector_d v, boolean flag) {
        super(env, offset);
        if (v.isZero()) {
            throw new RuntimeException("Vector " + v + " is the zero vector "
                    + "which cannot be used to define a line.");
        }
        this.pv = new V3D_Vector_d(p);
        this.v = new V3D_Vector_d(v);
        isDefinedByVector = true;
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #pv} is cloned from.
     * @param q What {@link #v} is derived from.
     */
    public V3D_Line_d(V3D_Point_d p, V3D_Point_d q) {
        super(p.env, new V3D_Vector_d(p.offset));
        V3D_Point_d q2 = new V3D_Point_d(q);
        q2.setOffset(p.offset);
        if (p.rel.equals(q2.rel)) {
            throw new RuntimeException("Points " + p + " and " + q
                    + " are the same and so do not define a line.");
        }
        pv = new V3D_Vector_d(p.rel);
        v = q2.rel.subtract(pv);
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
        r += pad + "p=" + getP().toString(pad) + "\n"
                + pad + ",\n"
                + pad + "v=" + v.toString(pad);
        return r;
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFieldsSimple(String pad) {
        String r = super.toStringFieldsSimple(pad) + ",\n";
        r += pad + "p=" + getP().toStringSimple("") + ",\n"
                + pad + "v=" + v.toStringSimple(pad);
        return r;
    }

    /**
     * @param l The line to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_Line_d l) {
        return l.intersects(getP()) && l.intersects(getQ());
    }

    /**
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @param l The line to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this} within
     * epsilon.
     */
    public boolean equals(double epsilon, V3D_Line_d l) {
        return l.intersects(getP(), epsilon) && l.intersects(getQ(), epsilon);
    }

    /**
     * The point of the line as calculated from {@link #pv} and {@link #offset}.
     *
     * @return {@link #pv} with {@link #offset} applied.
     */
    public V3D_Point_d getP() {
        if (p == null) {
            p = new V3D_Point_d(env, offset, pv);
        }
        return p;
    }

    /**
     * A point on the line as calculated from {@link #pv}, {@link #offset} and
     * {@link #v}.
     *
     * @return Another point on the line derived from {@link #v}.
     */
    public V3D_Point_d getQ() {
        if (q == null) {
            q = new V3D_Point_d(env, offset, pv.add(v));
        }
        return q;
    }

    /**
     * @param pt A point to test for intersection.
     * @return {@code true} if pv is on the line.
     */
    public boolean intersects(V3D_Point_d pt) {
        if (getP().equals(pt) || getQ().equals(pt)) {
            return true;
        }
        V3D_Vector_d dpt = new V3D_Vector_d(
                pt.getX() - p.getX(),
                pt.getY() - p.getY(),
                pt.getZ() - p.getZ());
        return dpt.isScalarMultiple(v);
    }

    /**
     * @param pt A point to test for intersection.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return {@code true} if pv is on the line.
     */
    public boolean intersects(V3D_Point_d pt, double epsilon) {
        if (getP().equals(epsilon, pt) || getQ().equals(epsilon, pt)) {
            return true;
        }
        V3D_Vector_d dpt = new V3D_Vector_d(
                pt.getX() - p.getX(),
                pt.getY() - p.getY(),
                pt.getZ() - p.getZ());
        return dpt.isScalarMultiple(epsilon, v);
    }

    /**
     * @param aabb The V3D_AABB to test for intersection.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public boolean intersects(V3D_AABB_d aabb, double epsilon) {
        return intersects(aabb.getl(), epsilon)
                || intersects(aabb.getr(), epsilon)
                || intersects(aabb.gett(), epsilon)
                || intersects(aabb.getb(), epsilon)
                || intersects(aabb.getf(), epsilon)
                || intersects(aabb.geta(), epsilon);
    }

    /**
     * @param aabbx The V3D_AABBX to test for intersection.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return {@code true} if this intersects with {@code aabbx}
     */
    public boolean intersects(V3D_AABBX_d aabbx, double epsilon) {
        V3D_Plane_d pl = aabbx.getXPl();
        if (pl.isParallel(this, epsilon)) {
            if (pl.isOnPlane(this, epsilon)) {
                return aabbx.intersects(this, epsilon);
            } else {
                return false;
            }
        } else {
            V3D_Geometry_d i = pl.getIntersect(this, epsilon);
            if (i == null) {
                return false;
            } else {
                V3D_Point_d ip = (V3D_Point_d) i;
                return aabbx.getTopPlane().isOnSameSide(
                        aabbx.getll(), ip, epsilon)
                        && aabbx.getBottomPlane().isOnSameSide(
                                aabbx.getuu(), ip, epsilon)
                        && aabbx.getRightPlane().isOnSameSide(
                                aabbx.getll(), ip, epsilon)
                        && aabbx.getLeftPlane().isOnSameSide(
                                aabbx.getuu(), ip, epsilon);
            }
        }
    }

    /**
     * @param aabby The V3D_AABBY to test for intersection.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public boolean intersects(V3D_AABBY_d aabby, double epsilon) {
        V3D_Plane_d pl = aabby.getYPl();
        if (pl.isParallel(this, epsilon)) {
            if (pl.isOnPlane(this, epsilon)) {
                return aabby.intersects(this, epsilon);
            } else {
                return false;
            }
        } else {
            V3D_Geometry_d i = pl.getIntersect(this, epsilon);
            if (i == null) {
                return false;
            } else {
                V3D_Point_d ip = (V3D_Point_d) i;
                return aabby.getTopPlane().isOnSameSide(
                        aabby.getll(), ip, epsilon)
                        && aabby.getBottomPlane().isOnSameSide(
                                aabby.getuu(), ip, epsilon)
                        && aabby.getRightPlane().isOnSameSide(
                                aabby.getll(), ip, epsilon)
                        && aabby.getLeftPlane().isOnSameSide(
                                aabby.getuu(), ip, epsilon);
            }
        }
    }

    /**
     * @param aabbz The V3D_AABBZ_d to test for intersection.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public boolean intersects(V3D_AABBZ_d aabbz, double epsilon) {
        V3D_Plane_d pl = aabbz.getZPl();
        if (pl.isParallel(this, epsilon)) {
            if (pl.isOnPlane(this, epsilon)) {
                return aabbz.intersects(this, epsilon);
            } else {
                return false;
            }
        } else {
            V3D_Geometry_d i = pl.getIntersect(this, epsilon);
            if (i == null) {
                return false;
            } else {
                V3D_Point_d ip = (V3D_Point_d) i;
                return aabbz.getTopPlane().isOnSameSide(
                        aabbz.getll(), ip, epsilon)
                        && aabbz.getBottomPlane().isOnSameSide(
                                aabbz.getuu(), ip, epsilon)
                        && aabbz.getRightPlane().isOnSameSide(
                                aabbz.getll(), ip, epsilon)
                        && aabbz.getLeftPlane().isOnSameSide(
                                aabbz.getuu(), ip, epsilon);
            }
        }
    }

    /**
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @param l The line to test if it is parallel to this.
     * @return {@code true} If this and {@code l} are parallel.
     */
    public boolean isParallel(V3D_Line_d l, double epsilon) {
        return v.isScalarMultiple(epsilon, l.v);
    }

    /**
     * Computes and returns the intersect of {@code this} and {@code l}. This 
     * first checks if the lines are parallel. Then if parallel if coincident.
     * Two lines are considered equal if the distance between them is less
     * than epsilon.
     * 
     * @param l The line to get the intersection with {@code this}.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry_d getIntersect(V3D_Line_d l, double epsilon) {
        // Special case of parallel lines.
        V3D_Point_d tp = getP();
        if (isParallel(l, epsilon)) {
            if (l.intersects(tp, epsilon)) {
                // If lines are coincident return this.
                return this;
            } else {
                if (this.getDistance(l, epsilon) < epsilon) {
                    return this;
                }
                return null;
            }
        }
        return getIntersect0(l, epsilon);
    }

    /**
     * Computes and returns the intersect of {@code this} and {@code l}. This 
     * first checks if the lines are parallel. Then if parallel if coincident.
     * Two lines are considered equal if the distance between them is less
     * than epsilon.
     * 
     * @param l The line to get the intersection with {@code this}.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry_d getIntersect0(V3D_Line_d l, double epsilon) {
        V3D_Point_d tp = getP();
        V3D_Point_d tq = getQ();
        V3D_Point_d lp = l.getP();
        V3D_Point_d lq = l.getQ();
        V3D_Vector_d plp = new V3D_Vector_d(tp, lp);
        V3D_Vector_d lqlp = new V3D_Vector_d(lq, lp);
        if (lqlp.getMagnitudeSquared() == 0d) {
            if (intersects(lp, epsilon)) {
                return lp;
            }
        }
        V3D_Vector_d qp = new V3D_Vector_d(tq, tp);
        if (qp.getMagnitudeSquared() == 0d) {
            if (l.intersects(tp, epsilon)) {
                return tp;
            }
        }
        double a = plp.dx * lqlp.dx + plp.dy * lqlp.dy + plp.dz * lqlp.dz;
        double b = lqlp.dx * qp.dx + lqlp.dy * qp.dy + lqlp.dz * qp.dz;
        double c = plp.dx * qp.dx + plp.dy * qp.dy + plp.dz * qp.dz;
        double d = lqlp.dx * lqlp.dx + lqlp.dy * lqlp.dy + lqlp.dz * lqlp.dz;
        double eb = qp.dx * qp.dx + qp.dy * qp.dy + qp.dz * qp.dz;
        double den = (eb * (d)) - (b * (b));
        double num = (a * (b)) - (c * (d));
        if (den == 0d) {
            if (num == 0d) {
                double x;
                double y;
                double z;
                double lamda;
                double mu;
                //V3D_Vector_d tv = getV();
                V3D_Vector_d tv = v;
                //V3D_Vector_d lv = l.getV();
                V3D_Vector_d lv = l.v;
                if (tv.dx == 0d) {
                    x = tp.getX();
                    if (lv.dx == 0d) {
                        if (tv.dy == 0d) {
                            y = tp.getY();
                            if (lv.dy == 0d) {
                                z = tp.getZ();
                            } else {
                                if (tv.dz == 0d) {
                                    z = tp.getZ();
                                } else {
                                    if (lv.dz == 0d) {
                                        z = lp.getZ();
                                    } else {
                                        mu = (tp.getY() - (lp.getY())) / (lv.dy);
                                        z = lp.getZ() + (lv.dz * (mu));
                                    }
                                }
                            }
                        } else {
                            if (lv.dy == 0d) {
                                y = lp.getY();
                                if (tv.dz == 0d) {
                                    z = tp.getZ();
                                } else {
                                    if (lv.dz == 0d) {
                                        z = lp.getZ();
                                    } else {
                                        lamda = (lp.getY() - (tp.getY())) / (tv.dy);
                                        z = tp.getZ() + (tv.dz * (lamda));
                                    }
                                }
                                //x = pv.getX(oom);            
                                //p.getX(oom) + v.getDX(oom) * lamda = l.pv.getX(oom) + l.v.getDX(oom) * mu
                                //p.getY(oom) + v.getDY(oom) * lamda = l.pv.getY(oom) + l.v.getDY(oom) * mu
                                //p.getZ(oom) + v.getDZ(oom) * lamda = l.pv.getZ(oom) + l.v.getDZ(oom) * mu

                            } else {
                                if (tv.dz == 0d) {
                                    z = tp.getZ();
                                    mu = (tp.getZ() - (lp.getZ())) / (lv.dy);
                                    y = lp.getY() + (lv.dy * (mu));
                                } else {
                                    if (lv.dz == 0d) {
                                        z = lp.getZ();
                                        lamda = (lp.getZ() - (tp.getZ())) / (tv.dy);
                                        y = tp.getY() + (tv.dy * (lamda));
                                    } else {
                                        // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                        mu = ((pv.getY(oom)+(v.getDY(oom)*(lamda)))-(l.pv.getY(oom)))/(l.v.getDY(oom));
//                                        lamda = ((l.pv.getZ(oom)-(pv.getZ(oom)))/(v.getDZ(oom)))+(l.v.getDZ(oom)*(mu));
//                                        lamda = ((l.pv.getZ(oom)-(pv.getZ(oom)))/(v.getDZ(oom)))+(l.v.getDZ(oom)*(((pv.getY(oom)+(v.getDY(oom)*(lamda)))-(l.pv.getY(oom)))/(l.v.getDY(oom))));
//                                        l = ((bz-az)/adz) + (bdz*(ady*(l-by)/bdy))
//                                        l = ((bz-az)/adz) + bdz*ady*l/bdy - bdz*ady*by/bdy
//                                        l - bdz*ady*l/bdy = ((bz-az)/adz) - bdz*ady*by/bdy
//                                        l (1 - bdz*ady/bdy) = ((bz-az)/adz) - bdz*ady*by/bdy
//                                        l = (((bz-az)/adz) - bdz*ady*by/bdy)/(1 - bdz*ady/bdy)
                                        double den2 = 1d - (lv.dz * (tv.dy / (lv.dy)));
                                        if (den2 != 0d) {
                                            lamda = (((lp.getZ() - (tp.getZ())) / (tv.dz)) - (lv.dz * (tv.dy * (lp.getY() / (lv.dy))))) / (den2);
                                            z = tp.getZ() + (tv.dz * (lamda));
                                            y = tp.getY() + (tv.dy * (lamda));
                                        } else {
                                            den2 = 1d - (lv.dy * (tv.dz / (lv.dz)));
                                            if (den2 != 0d) {
                                                lamda = (((lp.getY() - (tp.getY())) / (tv.dy)) - (lv.dy * (tv.dz * (lp.getZ() / (lv.dz))))) / (den2);
                                                z = tp.getZ() + (tv.dz * (lamda));
                                                y = tp.getY() + (tv.dy * (lamda));
                                            } else {
                                                // This should not happen!
                                                z = 0d;
                                                y = 0d;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        mu = (tp.getX() - (lp.getX())) / (lv.dx);
                        if (tv.dy == 0d) {
                            if (lv.dy == 0d) {
                                y = tp.getY();
                                z = tp.getZ();
                            } else {
                                if (tv.dz == 0d) {
                                    y = lp.getY() + (lv.dy * (mu));
                                } else {
                                    y = tp.getY() + (tv.dy * (mu));
                                }
                                if (lv.dz == 0d) {
                                    z = tp.getZ();
                                } else {
                                    z = lp.getZ() + (lv.dz * (mu));
                                }
                            }
                        } else {
                            lamda = ((lp.getY() + (lv.dy * (mu)))
                                    - (tp.getX())) / (tv.dy);
                            if (tv.dz == 0d) {
                                z = tp.getZ();
                            } else {
                                z = tp.getZ() + (tv.dz * (lamda));
                            }
                            if (lv.dy == 0d) {
                                y = tp.getY();
                            } else {
                                y = lp.getY() + (lv.dy * (mu));
                            }
                        }
                    }
                } else {
                    if (lv.dx == 0d) {
                        lamda = lp.getX() - (tp.getX()) / (tv.dx);
                        x = lp.getX();
                        if (tv.dy == 0d) {
                            y = tp.getY();
                            if (tv.dz == 0d) {
                                z = tp.getZ();
                            } else {
                                if (lv.dz == 0d) {
                                    z = lp.getZ();
                                } else {
                                    mu = tp.getY() - (lp.getY()) / (lv.dy);
                                    z = lp.getZ() + (lv.dz * (mu));
                                }
                            }
                        } else {
                            if (tv.dy == 0d) {
                                y = tp.getY();
                                if (lv.dy == 0d) {
                                    if (tv.dz == 0d) {
                                        z = tp.getZ();
                                    } else {
                                        if (lv.dz == 0d) {
                                            z = lp.getZ();
                                        } else {
                                            mu = ((tp.getZ() + (tv.dz * (lamda))) - (lp.getZ())) / (lv.dz);
                                            z = lp.getZ() + (lv.dz * (mu));
                                        }
                                    }
                                } else {
                                    if (tv.dz == 0d) {
                                        z = tp.getZ();
                                    } else {
                                        if (lv.dz == 0d) {
                                            z = lp.getZ();
                                        } else {
                                            mu = (tp.getZ() - (lp.getZ())) / (lv.dz);
                                            z = lp.getZ() + (lv.dz * (mu));
                                        }
                                    }
                                }
                            } else {
                                if (lv.dy == 0d) {
                                    y = lp.getY();
                                    if (tv.dz == 0d) {
                                        z = tp.getZ();
                                    } else {
                                        if (lv.dz == 0d) {
                                            z = lp.getZ();
                                        } else {
                                            mu = ((tp.getZ() + (tv.dz * (lamda))) - (lp.getZ())) / (lv.dz);
                                            z = lp.getZ() + (lv.dz * (mu));
                                        }
                                    }
                                } else {
                                    y = tp.getY() + (tv.dy * (lamda));
                                    if (tv.dz == 0d) {
                                        z = tp.getZ();
                                    } else {
                                        if (lv.dz == 0d) {
                                            //z = l.pv.getZ(oom);
                                            //z = l.getP().dz;
                                            z = l.pv.dz;
                                        } else {
                                            mu = ((tp.getZ() + (tv.dz * (lamda))) - (lp.getZ())) / (lv.dz);
                                            z = lp.getZ() + (lv.dz * (mu));
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        // v.getDX(oom) > 0 && l.v.getDX(oom) > 0
                        if (tv.dy == 0d) {
                            y = tp.getY();
                            if (lv.dy == 0d) {
                                if (tv.dz == 0d) {
                                    z = tp.getZ();
                                    x = tp.getX();
                                } else {
                                    if (lv.dz == 0d) {
                                        z = lp.getZ();
                                        lamda = (lp.getZ() - (tp.getZ())) / (tv.dz);
                                        x = tp.getX() + (tv.dx * (lamda));
                                    } else {
                                        // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                        mu = ((pv.getX(oom)+(v.getDX(oom)*(lamda)))-(l.pv.getX(oom)))/(l.v.getDX(oom));
//                                        lamda = ((l.pv.getZ(oom)-(pv.getZ(oom)))/(v.getDZ(oom)))+(l.v.getDZ(oom)*(mu));
//                                        lamda = ((l.pv.getZ(oom)-(pv.getZ(oom)))/(v.getDZ(oom)))+(l.v.getDZ(oom)*(((pv.getX(oom)+(v.getDX(oom)*(lamda)))-(l.pv.getX(oom)))/(l.v.getDX(oom))));
//                                        l = ((bz-az)/adz) + (bdz*(adx*(l-bx)/bdx))
//                                        l = ((bz-az)/adz) + bdz*adx*l/bdx - bdz*adx*bx/bdx
//                                        l - bdz*adx*l/bdx = ((bz-az)/adz) - bdz*adx*bx/bdx
//                                        l (1 - bdz*adx/bdx) = ((bz-az)/adz) - bdz*adx*bx/bdx
//                                        l = (((bz-az)/adz) - bdz*adx*bx/bdx)/(1 - bdz*adx/bdx)
                                        double den2 = 1d - (lv.dz * (tv.dx / (lv.dx)));
                                        if (den2 != 0d) {
                                            lamda = (((lp.getZ() - (tp.getZ())) / (tv.dz)) - (lv.dz * (tv.dx * (lp.getX() / (lv.dx))))) / (den2);
                                            z = tp.getZ() + (tv.dz * (lamda));
                                            x = tp.getX() + (tv.dx * (lamda));
                                        } else {
                                            den2 = 1d - (lv.dx * (tv.dz / (lv.dz)));
                                            if (den2 != 0d) {
                                                lamda = (((lp.getX() - (tp.getX())) / (tv.dx)) - (lv.dx * (tv.dz * (lp.getZ() / (lv.dz))))) / (den2);
                                                z = tp.getZ() + (tv.dz * (lamda));
                                                x = tp.getX() + (tv.dx * (lamda));
                                            } else {
                                                // This should not happen!
                                                z = 0d;
                                                x = 0d;
                                            }
                                        }
                                    }
                                }
                            } else {
                                //mu = tp.getY(oom)-(lp.getY(oom))/(l.v.getDY(oom));
                                //mu = tp.getY()-(lp.getY())/(l.getV().dy);
                                mu = tp.getY() - (lp.getY()) / (l.v.dy);
                                x = lp.getX() + (lv.dx * (mu));
                                z = lp.getZ() + (lv.dz * (mu));
                            }
                        } else {
                            // v.getDX(oom) > 0 && l.v.getDX(oom) > 0 && v.getDY(oom) > 0
                            if (tv.dz == 0d) {
                                z = tp.getZ();
                                if (lv.dz == 0d) {
                                    // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                    mu = ((pv.getX(oom)+(v.getDX(oom)*(lamda)))-(l.pv.getX(oom)))/(l.v.getDX(oom));
//                                    lamda = ((l.pv.getY(oom)-(pv.getY(oom)))/(v.getDY(oom)))+(l.v.getDY(oom)*(mu));
//                                    lamda = ((l.pv.getY(oom)-(pv.getY(oom)))/(v.getDY(oom)))+(l.v.getDY(oom)*(((pv.getX(oom)+(v.getDX(oom)*(lamda)))-(l.pv.getX(oom)))/(l.v.getDX(oom))));
//                                    l = ((by - ay) / ady) + (bdy * (adx * (l - bx) / bdx))
//                                    l = ((by - ay) / ady) + bdy * adx * l / bdx - bdy * adx * bx / bdx
//                                    l - bdy * adx * l / bdx = ((by - ay) / ady) - bdy * adx * bx / bdx
//                                    l(1 - bdy * adx / bdx) = ((by - ay) / ady) - bdy * adx * bx / bdx
//                                    l = (((by-ay)/ady) - bdy*adx*bx/bdx)/(1 - bdy*adx/bdx)
                                    double den2 = 1d - (lv.dy * (tv.dx / (lv.dx)));
                                    if (den2 != 0d) {
                                        lamda = (((lp.getY() - (tp.getY())) / (tv.dy)) - (lv.dy * (tv.dx * (lp.getX() / (lv.dx))))) / (den2);
                                        y = tp.getY() + (tv.dy * (lamda));
                                        x = tp.getX() + (tv.dx * (lamda));
                                    } else {
                                        den2 = 1d - (lv.dx * (tv.dy / (lv.dy)));
                                        if (den2 != 0d) {
                                            lamda = (((lp.getX() - (tp.getX())) / (tv.dx)) - (lv.dx * (tv.dy * (lp.getY() / (lv.dy))))) / (den2);
                                            y = tp.getY() + (tv.dy * (lamda));
                                            x = tp.getX() + (tv.dx * (lamda));
                                        } else {
                                            // This should not happen!
                                            y = 0d;
                                            x = 0d;
                                        }
                                    }
                                } else {
                                    mu = (tp.getZ() - (lp.getZ())) / (lv.dz);
                                    y = lp.getY() + (lv.dy * (mu));
                                    x = lp.getX() + (lv.dx * (mu));
                                }
                            } else {
                                if (lv.dz == 0d) {
                                    z = lp.getZ();
                                    lamda = (lp.getZ() - (tp.getZ())) / (tv.dz);
                                    y = tp.getY() + (tv.dy * (lamda));
                                    x = tp.getX() + (tv.dx * (lamda));
                                } else {
                                    // There are 6 ways to calculate lamda. One way should work! - If not try calculating mu.
                                    double den2 = 1d - (lv.dy * (tv.dx / (lv.dx)));
                                    if (den2 != 0d) {
                                        lamda = (((lp.getY() - (tp.getY())) / (tv.dy)) - (lv.dy * (tv.dx * (lp.getX() / (lv.dx))))) / (den2);
                                        x = tp.getX() + (tv.dx * (lamda));
                                        y = tp.getY() + (tv.dy * (lamda));
                                        z = tp.getZ() + (tv.dz * (lamda));
//                                        x = qv.getX()+(v.dx*(lamda));
//                                        y = qv.getY()+(v.dy*(lamda));
//                                        z = qv.getZ()+(v.dz*(lamda));
                                    } else {
                                        den2 = 1d - (lv.dy * (tv.dz / (lv.dz)));
                                        if (den2 != 0d) {
                                            lamda = (((lp.getY() - (tp.getY())) / (tv.dy)) - (lv.dy * (tv.dz * (lp.getZ() / (lv.dz))))) / (den2);
                                            x = tp.getX() + (tv.dx * (lamda));
                                            y = tp.getY() + (tv.dy * (lamda));
                                            z = tp.getZ() + (tv.dz * (lamda));
                                        } else {
                                            den2 = 1d - (lv.dz * (tv.dx / (lv.dx)));
                                            if (den2 != 0d) {
                                                lamda = (((lp.getZ() - (tp.getZ())) / (tv.dz)) - (lv.dz * (tv.dx * (lp.getX() / (lv.dx))))) / (den2);
                                                x = tp.getX() + (tv.dx * (lamda));
                                                y = tp.getY() + (tv.dy * (lamda));
                                                z = tp.getZ() + (tv.dz * (lamda));
                                            } else {
                                                den2 = 1d - (lv.dz * (tv.dy / (lv.dy)));
                                                if (den2 != 0d) {
                                                    lamda = (((lp.getZ() - (tp.getZ())) / (tv.dz)) - (lv.dz * (tv.dy * (lp.getY() / (lv.dy))))) / (den2);
                                                    x = tp.getX() + (tv.dx * (lamda));
                                                    y = tp.getY() + (tv.dy * (lamda));
                                                    z = tp.getZ() + (tv.dz * (lamda));
                                                } else {
                                                    den2 = 1d - (lv.dx * (tv.dx / (lv.dy)));
                                                    if (den2 != 0d) {
                                                        lamda = (((lp.getX() - (tp.getX())) / (tv.dx)) - (lv.dx * (tv.dy * (lp.getY() / (lv.dy))))) / (den2);
                                                        x = tp.getX() + (tv.dx * (lamda));
                                                        y = tp.getY() + (tv.dy * (lamda));
                                                        z = tp.getZ() + (tv.dz * (lamda));
                                                    } else {
                                                        den2 = 1d - (lv.dx * (tv.dx / (lv.dz)));
                                                        if (den2 != 0d) {
                                                            lamda = (((lp.getX() - (tp.getX())) / (tv.dx)) - (lv.dx * (tv.dz * (lp.getZ() / (lv.dz))))) / (den2);
                                                            x = tp.getX() + (tv.dx * (lamda));
                                                            y = tp.getY() + (tv.dy * (lamda));
                                                            z = tp.getZ() + (tv.dz * (lamda));
                                                        } else {
                                                            // This should not happen!
                                                            x = 0d;
                                                            y = 0d;
                                                            z = 0d;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return new V3D_Point_d(env, x, y, z);
            }
            return null; // Should this depend on epsilon?
        }
        double mua = num / (den);
        double mub = (a + (b * (mua))) / -d;
        V3D_Point_d pi = new V3D_Point_d(env,
                (tp.getX() - (mua * (qp.dx))),
                (tp.getY() - (mua * (qp.dy))),
                (tp.getZ() - (mua * (qp.dz))));
        // If point pv is on both lines then return this as the intersection.
        if (intersects(pi, epsilon) && l.intersects(pi, epsilon)) {
            return pi;
        }
        V3D_Point_d qi = new V3D_Point_d(env,
                (lp.getX() + (mub * (lqlp.dx))),
                (lp.getY() + (mub * (lqlp.dy))),
                (lp.getZ() + (mub * (lqlp.dz))));
        // If point qv is on both lines then return this as the intersection.
        if (intersects(qi, epsilon) && l.intersects(qi, epsilon)) {
            return qi;
        }
        /**
         * The only time when pi and qi should be different is when the lines do
         * not intersect. In this case pi and qi are meant to be the end points
         * of the shortest line between the two lines.
         */
        if (pi.equals(epsilon, qi)) {
            return pi;
        } else {
            return null;
//            if (new V3D_LineSegment_d(pi, qi).getLength() < epsilon) {
//                int debug = 1;
//                //return pi;
//                return qi;
//            } else {
//                return null;
//            }
        }
    }

    /**
     * @param pt A point for which the shortest line segment to this is
     * returned.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return The line segment having the shortest distance between {@code pt}
     * and {@code this}.
     */
    public V3D_FiniteGeometry_d getLineOfIntersect(V3D_Point_d pt,
            double epsilon) {
        if (intersects(pt, epsilon)) {
            return pt;
        }
        return new V3D_LineSegment_d(pt, getPointOfIntersect(pt, epsilon));
    }

    /**
     * Adapted from:
     * https://math.stackexchange.com/questions/1521128/given-a-line-and-a-point-in-3d-how-to-find-the-closest-point-on-the-line
     *
     * @param pt The point projected onto this.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return A point on {@code this} which is the shortest distance from
     * {@code pt}.
     */
    public V3D_Point_d getPointOfIntersect(V3D_Point_d pt, double epsilon) {
        if (intersects(pt, epsilon)) {
            return pt;
        }
        V3D_Plane_d ptv = new V3D_Plane_d(pt, v);
        V3D_Geometry_d i = ptv.getIntersect(this, epsilon);
        if (i instanceof V3D_Line_d) {
            return pt;
        } else {
            return (V3D_Point_d) i;
        }
    }

    /**
     * Calculate and return the line of intersection (the shortest line) between
     * this and l. If this and l intersect then return null. If this and l are
     * parallel, then return null. If the calculated ends of the line of
     * intersection are the same, the return null (this may happen due to
     * imprecision). Adapted in part from:
     * http://paulbourke.net/geometry/pointlineplane/
     *
     * @param l The line to get the line of intersection with.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return The line of intersection between {@code this} and {@code l}. The
     * point pv is a point on or near this, and the point qv is a point on or
     * near l. Whether the points are on or near is down to rounding error and
     * precision.
     */
    public V3D_LineSegment_d getLineOfIntersect(V3D_Line_d l,
            double epsilon) {
        if (isParallel(l, epsilon)) {
            return null;
        }
        if (getIntersect(l, epsilon) != null) {
            return null;
        }
        V3D_Point_d tp = getP();
        V3D_Point_d lp = l.getP();
        V3D_Vector_d A = new V3D_Vector_d(tp, lp);
        //V3D_Vector_d B = getV().reverse();
        V3D_Vector_d B = v.reverse();
        //V3D_Vector_d C = l.getV().reverse();
        V3D_Vector_d C = l.v.reverse();

        double AdB = A.getDotProduct(B);
        double AdC = A.getDotProduct(C);
        double CdB = C.getDotProduct(B);
        double BdB = B.getDotProduct(B);
        double CdC = C.getDotProduct(C);

        double ma = (AdC * (CdB)) - (AdB * (CdC))
                / ((BdB * (CdC)) - (CdB * (CdB)));
        double mb = ((ma * (CdB)) + (AdC)) / (CdC);
        V3D_Vector_d tpi = tp.getVector().subtract(B.multiply(ma));
        V3D_Vector_d lpi = lp.getVector().subtract(C.multiply(mb));
        V3D_Point_d loip = new V3D_Point_d(env, tpi);
        V3D_Point_d loiq = new V3D_Point_d(env, lpi);
        if (loip.equals(loiq)) {
            return null;
        } else {
            return new V3D_LineSegment_d(loip, loiq);
        }
    }

    /**
     * Calculate and return the distance from {@code this} to {@code pt} rounded
     * to {@code oom} precision. See:
     * <ul>
     * <li>https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line</li>
     * <li>Weisstein, Eric W. "Point-Line Distance--3-Dimensional." From
     * MathWorld--A Wolfram Web Resource.
     * https://mathworld.wolfram.com/Point-LineDistance3-Dimensional.html</li>
     * </ul>
     *
     * @param pt A point for which the minimum distance from {@code this} is
     * returned.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return The minimum distance between this and {@code pv}.
     */
    public double getDistance(V3D_Point_d pt, double epsilon) {
        if (intersects(pt, epsilon)) {
            return 0d;
        }
        return Math.sqrt(getDistanceSquared(pt, true));
    }

    /**
     * Calculates and returns the squared distance from this to pt.
     *
     * See:
     * <ul>
     * <li>https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line</li>
     * <li>Weisstein, Eric W. "Point-Line Distance--3-Dimensional." From
     * MathWorld--A Wolfram Web Resource.
     * https://mathworld.wolfram.com/Point-LineDistance3-Dimensional.html</li>
     * </ul>
     *
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @param pt A point for which the minimum distance from {@code this} is
     * returned.
     * @return The minimum distance between this and {@code pv}.
     */
    public double getDistanceSquared(V3D_Point_d pt, double epsilon) {
        if (intersects(pt, epsilon)) {
            return 0d;
        } else {
            return getDistanceSquared(pt, true);
        }
    }

    /**
     * Calculates and returns the squared distance from {@code this} to
     * {@code pt}. This should only be used if it is known that {@code this}
     * does not intersect with {@code pt} (in which case an error is thrown).
     *
     * See:
     * <ul>
     * <li>https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line</li>
     * <li>Weisstein, Eric W. "Point-Line Distance--3-Dimensional." From
     * MathWorld--A Wolfram Web Resource.
     * https://mathworld.wolfram.com/Point-LineDistance3-Dimensional.html</li>
     * </ul>
     *
     * @param pt A point for which the minimum distance from {@code this} is
     * returned.
     * @param noInt This is ignored, but it distinguishes this method from
     * {@link #getDistanceSquared(uk.ac.leeds.ccg.v3d.geometry.d.V3D_Point_d, double)}.
     * @return The minimum distance between this and {@code pv}.
     */
    protected double getDistanceSquared(V3D_Point_d pt, boolean noInt) {
        V3D_Vector_d pp = new V3D_Vector_d(pt, getP());
        V3D_Vector_d qp = new V3D_Vector_d(pt, getQ());
        double num = (pp.getCrossProduct(qp)).getMagnitudeSquared();
        //double den = getV().getMagnitudeSquared();
        double den = v.getMagnitudeSquared();
        return num / den;
    }

    /**
     * https://en.wikipedia.org/wiki/Skew_lines#Nearest_points
     * https://en.wikipedia.org/wiki/Distance_between_two_parallel_lines
     *
     * @param l A line for which the minimum distance from {@code this} is
     * returned.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return The minimum distance between this and {@code l}.
     */
    public double getDistance(V3D_Line_d l, double epsilon) {
        return Math.sqrt(getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistanceSquared(V3D_Line_d l, double epsilon) {
        V3D_Point_d tp = getP();
        if (isParallel(l, epsilon)) {
            return l.getDistanceSquared(tp, epsilon);
        } else {
            /**
             * Calculate the direction vector of the line connecting the closest
             * points by computing the cross product.
             */
            V3D_Vector_d cp = v.getCrossProduct(l.v);
            /**
             * Calculate the delta from {@link #p} and l.p
             */
            V3D_Vector_d delta = l.getP().getVector().subtract(tp.getVector());
            double m = cp.getMagnitudeSquared();
            double dp = cp.getDotProduct(delta);
            // m should only be zero if the lines are parallel.
            //return Math.abs(dp * dp / m);
            return dp * dp / m;
        }
    }

    /**
     * @return {@code true} iff this is parallel to the plane defined by x=0.
     */
    public boolean isParallelToX0() {
        return v.dx == 0d;
    }

    /**
     * @return {@code true} iff this is parallel to the plane defined by y=0.
     */
    public boolean isParallelToY0() {
        return v.dy == 0d;
    }

    /**
     * @return {@code true} iff this is parallel to the plane defined by z=0.
     */
    public boolean isParallelToZ0() {
        return v.dz == 0d;
    }

    /**
     * @return The points that define the plan as a matrix.
     */
    public Math_Matrix_Double getAsMatrix() {
        V3D_Point_d tp = getP();
        V3D_Point_d tq = getQ();
        double[][] m = new double[2][3];
        m[0][0] = tp.getX();
        m[0][1] = tp.getY();
        m[0][2] = tp.getZ();
        m[1][0] = tq.getX();
        m[1][1] = tq.getY();
        m[1][2] = tq.getZ();
        return new Math_Matrix_Double(m);
    }

    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     */
    @Override
    public void translate(V3D_Vector_d v) {
        super.translate(v);
        //pv.translate(v);
        if (p != null) {
            p.translate(v);
        }
        if (q != null) {
            q.translate(v);
        }

    }

    @Override
    public V3D_Line_d rotate(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V3D_Line_d(this);
        } else {
            return rotateN(ray, uv, theta, epsilon);
        }
    }

    @Override
    public V3D_Line_d rotateN(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        V3D_Point_d rp = getP().rotate(ray, uv, theta, epsilon);
        V3D_Vector_d rv = v.getUnitVector().rotate(uv, theta);
        return new V3D_Line_d(rp, rv);
    }

    /**
     * @param ps The points to test for collinearity.
     * @return {@code true} iff all points are collinear with l.
     */
    public static boolean isCollinear(V3D_Point_d... ps) {
        V3D_Line_d l = getLine(ps);
        if (l == null) {
            return false;
        }
        return isCollinear(l, ps);
    }

    /**
     * There should be at least two different points in points. This does not
     * check ps are collinear.
     *
     * @param ps Any number of points, but with two being different.
     * @return A line defined by any two different points or null if the points
     * are coincident.
     */
    public static V3D_Line_d getLine(V3D_Point_d... ps) {
        var p0 = ps[0];
        for (var p : ps) {
            if (!p.equals(p0)) {
                return new V3D_Line_d(p0, p);
            }
        }
        return null;
    }

    /**
     * @param l The line to test points are collinear with.
     * @param ps The points to test if they are collinear with l.
     * @return {@code true} iff all points are collinear with l.
     */
    public static boolean isCollinear(V3D_Line_d l,
            V3D_Point_d... ps) {
        for (var p : ps) {
            if (!l.intersects(p)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @param ps The points to test for collinear within epsilon tolerance.
     * @return {@code true} iff all points are collinear with l given epsilon
     * tolerance.
     */
    public static boolean isCollinear(double epsilon, V3D_Point_d... ps) {
        V3D_Line_d l = getLine(epsilon, ps);
        if (l == null) {
            return false;
        }
        return isCollinear(epsilon, l, ps);
    }

    /**
     * @param l The line to test points are collinear with.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @param ps The points to test if they are collinear with l.
     * @return {@code true} iff all points are collinear with l.
     */
    public static boolean isCollinear(double epsilon, V3D_Line_d l,
            V3D_Point_d... ps) {
        for (var p : ps) {
            if (!l.intersects(p, epsilon)) {
                return false;
            }
        }
        return true;
    }

    /**
     * There should be at least two different points in points. This does not
     * check for collinearity of all the points. It returns a line defined by
     * the first points that have the greatest distance between them.
     *
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @param points Any number of points, but with two being different.
     * @return A line defined by any two different points or null if the points
     * are coincident.
     */
    public static V3D_Line_d getLine(double epsilon, V3D_Point_d... points) {
        if (points.length < 2) {
            return null;
        }
        // Find the points which are furthest apart.
        double max = 0d;
        V3D_Point_d a = null;
        V3D_Point_d b = null;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double d2 = points[i].getDistanceSquared(points[j]);
                if (d2 > max) {
                    a = points[i];
                    b = points[j];
                    max = d2;
                }
            }
        }
        if (max == 0d) {
            return null;
        } else {
            return new V3D_Line_d(a, b);
        }
    }
}
