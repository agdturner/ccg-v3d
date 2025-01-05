/*
 * Copyright 2020 Andy Turner, University of Leeds.
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
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.math.geometry.Math_Angle;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;

/**
 * 3D representation of an infinite length line. The line passes through the
 * point {@link #pv} with vector {@link #v}. The "*" denotes a point in 3D and
 * the line is shown with a line of "e" symbols in the following depiction: {@code
 * z                e
 * y           -                e
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
 * /      |       e v=(dx,dy,dz)
 * /       |      e
 * /        |     e
 * +         |    e
 * z          -   e
 * y
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
public class V3D_Line extends V3D_Geometry {

    private static final long serialVersionUID = 1L;
    
    /**
     * The x axis.
     */
    public static final V3D_Line X_AXIS = new V3D_Line(
            V3D_Vector.ZERO, V3D_Vector.I);

    /**
     * The y axis.
     */
    public static final V3D_Line Y_AXIS = new V3D_Line(
            V3D_Vector.ZERO, V3D_Vector.J);

    /**
     * The z axis.
     */
    public static final V3D_Line Z_AXIS = new V3D_Line(
            V3D_Vector.ZERO, V3D_Vector.K);
    
    /**
     * If this line is defined by a vector, then the calculation of {@link #q}
     * may be imprecise. If this line is defined by points, then {@link #v} may
     * have been imprecisely calculated.
     */
    public boolean isDefinedByVector;

    /**
     * Used to define {@link #p}.
     */
    protected V3D_Vector pv;

    /**
     * Used to store a point on the line as derived from {@link #offset} and
     * {@link #pv}.
     */
    protected V3D_Point p;

    /**
     * Another point on the line that is derived from {@link #offset},
     * {@link #pv} and {@link v}.
     */
    protected V3D_Point q;

    /**
     * The Order of Magnitude for the calculation of qv.
     */
    int oom;

    /**
     * The RoundingMode for the calculation of qv.
     */
    RoundingMode rm;

    /**
     * The vector that defines the line. This will not change under translation,
     * but will change under rotation.
     */
    public V3D_Vector v;

    /**
     * Create a new instance.
     */
    public V3D_Line() {
        super();
    }

    /**
     * @param l Used to initialise this.
     */
    public V3D_Line(V3D_Line l) {
        super(new V3D_Vector(l.offset));
        this.pv = new V3D_Vector(l.pv);
        if (l.p != null) {
            this.p = new V3D_Point(l.p);
        }
        if (l.q != null) {
            this.q = new V3D_Point(l.q);
        }
        this.oom = l.oom;
        this.rm = l.rm;
        this.v = new V3D_Vector(l.v);
        this.isDefinedByVector = l.isDefinedByVector;
    }

    /**
     * @param l Used to initialise this.
     */
    public V3D_Line(V3D_LineSegment l) {
        this(l.l);
    }

    /**
     * {@code pv} should not be equal to {@code qv}. {@link #offset} is set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param p What {@link #pv} is set to.
     * @param q Another point on the line from which {@link #v} is derived.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Line(V3D_Vector p, V3D_Vector q, int oom, RoundingMode rm) {
        this(V3D_Vector.ZERO, p, q, oom, rm);
    }

    /**
     * {@code pv} should not be equal to {@code qv}.
     *
     * @param offset What {@link #offset} is set to.
     * @param pv What {@link #pv} is cloned from.
     * @param qv Used to calculate {@link q} and {@link #v} (which is calculated
     * by taking the difference between pv and qv.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Line(V3D_Vector offset, V3D_Vector pv,
            V3D_Vector qv, int oom, RoundingMode rm) {
        super(offset);
        this.pv = new V3D_Vector(pv);
        if (pv.equals(qv)) {
            throw new RuntimeException("" + pv + " and " + qv + " are the same"
                    + " so do not define a line.");
        }
        q = new V3D_Point(offset, qv);
        v = qv.subtract(pv, oom, rm);
        this.oom = oom;
        this.rm = rm;
        isDefinedByVector = false;
    }

    /**
     * {@code v} should not be the zero vector {@code <0,0,0>}. {@link #offset}
     * is set to {@link V3D_Vector#ZERO}.
     *
     * @param p What {@link #pv} is cloned from.
     * @param v The vector defining the line from {@link #pv}. What {@link #v}
     * is cloned from.
     */
    public V3D_Line(V3D_Vector p, V3D_Vector v) {
        this(V3D_Vector.ZERO, p, v);
    }

    /**
     * Checks to ensure v is not the zero vector {@code <0,0,0>}.
     *
     * @param p What {@link #pv} is set to.
     * @param v The vector defining the line from {@link #pv}.
     */
    public V3D_Line(V3D_Point p, V3D_Vector v) {
        this(p.offset, p.rel, v);
    }

    /**
     * Checks to ensure v is not the zero vector {@code <0,0,0>}.
     *
     * @param offset What {@link #offset} is set to.
     * @param p Used to initialise {@link #pv}.
     * @param v Used to initialise {@link #v}.
     * @throws RuntimeException if {@code v.isZero()}.
     */
    public V3D_Line(V3D_Vector offset, V3D_Vector p, V3D_Vector v) {
        super(offset);
        if (v.isZero()) {
            throw new RuntimeException("Vector " + v + " is the zero vector "
                    + "which cannot be used to define a line.");
        }
        this.pv = new V3D_Vector(p);
        this.v = new V3D_Vector(v);
        isDefinedByVector = true;
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #pv} is cloned from.
     * @param q What {@link #v} is derived from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Line(V3D_Point p, V3D_Point q, int oom, RoundingMode rm) {
        super(new V3D_Vector(p.offset));
        V3D_Point q2 = new V3D_Point(q);
        q2.setOffset(p.offset, oom, rm);
        if (p.rel.equals(q2.rel)) {
            throw new RuntimeException("Points " + p + " and " + q
                    + " are the same and so do not define a line.");
        }
        this.pv = new V3D_Vector(p.rel);
        this.v = q2.rel.subtract(this.pv, oom, rm);
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
//        if (qv == null) {
//            r += pad + "pv=" + getP().toString(pad) + "\n"
//                    + pad + ",\n"
//                    + pad + "qv=null" + "\n"
//                    + pad + ",\n"
//                    + pad + "v=" + v.toString(pad);
//        } else {
//            if (v == null) {
//                r += pad + "pv=" + getP().toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "qv=" + qv.toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "v=null";
//            } else {
//                r += pad + "pv=" + getP().toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "qv=" + qv.toString(pad) + "\n"
//                        + pad + ",\n"
//                        + pad + "v=" + v.toString(pad);
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
        r += pad + "p=" + getP().toStringSimple("") + ",\n"
                + pad + "v=" + v.toStringSimple(pad);
//        if (qv == null) {
//            r += pad + "pv=" + getP().toStringSimple("") + ",\n"
//                    + pad + "qv=null" + ",\n"
//                    + pad + "v=" + v.toStringSimple(pad);
//        } else {
//            if (v == null) {
//                r += pad + "pv=" + getP().toStringSimple(pad) + ",\n"
//                        + pad + "qv=" + qv.toStringSimple(pad) + ",\n"
//                        + pad + "v=null";
//            } else {
//                r += pad + "pv=" + getP().toStringSimple(pad) + ",\n"
//                        + pad + "qv=" + qv.toStringSimple(pad) + ",\n"
//                        + pad + "v=" + v.toStringSimple(pad);
//            }
//        }
        return r;
    }

    /**
     * @param l The line to test if it is the same as {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_Line l, int oom, RoundingMode rm) {
//        boolean t1 = isIntersectedBy(l.getP(oom), oom);
//        boolean t2 = isIntersectedBy(l.getQ(oom), oom);
//        boolean t3 = l.isIntersectedBy(getP(oom), oom);
//        boolean t4 = l.isIntersectedBy(getQ(oom),oom);
//        boolean t5 = getV(oom).isScalarMultiple(l.getV(oom), oom);
//        return isIntersectedBy(l.getP(), oom, rm)
//                && isIntersectedBy(l.getQ(oom, rm), oom, rm);
        //if (v.isScalarMultiple(l.v, oom, rm)) {
            if (l.isIntersectedBy(getP(), oom, rm)) {
                if (l.isIntersectedBy(getQ(oom, rm), oom, rm)) {
                    return true;
                }
            }
        //}
        return false;
    }

//    /**
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode for any rounding.
//     * @return {@link #pv} with {@link #offset} applied.
//     */
//    public V3D_Vector getPAsVector(int oom, RoundingMode rm) {
//        return pv.add(offset, oom, rm);
//    } 
    /**
     * The point of the line as calculated from {@link #pv} and {@link #offset}.
     *
     * @return {@link #pv} with {@link #offset} applied.
     */
    public V3D_Point getP() {
        if (p == null) {
            p = new V3D_Point(offset, pv);
        }
        return p;
    }

    /**
     * A point on the line as calculated from {@link #pv}, {@link #offset} and
     * {@link #v}.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return Another point on the line derived from {@link #v}.
     */
    public V3D_Point getQ(int oom, RoundingMode rm) {
        if (q == null) {
            initQ(oom, rm);
        } else {
            if (oom < this.oom) {
                initQ(oom, rm);
            } else {
                if (!rm.equals(this.rm)) {
                    initQ(oom, rm);
                }
            }
        }
        return q;
    }

    private void initQ(int oom, RoundingMode rm) {
        q = new V3D_Point(offset, pv.add(v, oom, rm));
        this.oom = oom;
        this.rm = rm;
    }
    
    /**
     * @param pt A point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if pv is on the line.
     */
    public boolean isIntersectedBy(V3D_Point pt, int oom, RoundingMode rm) {
        int oomN2 = oom - 2;
        V3D_Point tp = getP();
        V3D_Point tq = getQ(oom, rm);
        if (tp.equals(pt, oom, rm)) {
            return true;
        }
        if (tq.equals(pt, oom, rm)) {
            return true;
        }
        V3D_Vector cp;
        if (tp.equals(V3D_Point.ORIGIN, oom, rm)) {
            V3D_Vector ppt = new V3D_Vector(
                    pt.getX(oomN2, rm).subtract(tq.getX(oomN2, rm)),
                    pt.getY(oomN2, rm).subtract(tq.getY(oomN2, rm)),
                    pt.getZ(oomN2, rm).subtract(tq.getZ(oomN2, rm)));
            cp = v.getCrossProduct(ppt, oomN2, rm);
        } else {
            V3D_Vector ppt = new V3D_Vector(
                    pt.getX(oomN2, rm).subtract(tp.getX(oomN2, rm)),
                    pt.getY(oomN2, rm).subtract(tp.getY(oomN2, rm)),
                    pt.getZ(oomN2, rm).subtract(tp.getZ(oomN2, rm)));
            cp = v.getCrossProduct(ppt, oomN2, rm);
        }
        return cp.getDX(oom, rm).isZero() && cp.getDY(oom, rm).isZero()
                && cp.getDZ(oom, rm).isZero();
    }

    /**
     * @param l The line to test if it is parallel to this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} If this and {@code l} are parallel.
     */
    public boolean isParallel(V3D_Line l, int oom, RoundingMode rm) {
        //oom -= 2;
        //return getV(oom, rm).isScalarMultiple(l.getV(oom, rm), oom, rm);
        return v.isScalarMultiple(l.v, oom, rm);
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}.
     *
     * @param l The line to get the intersection with {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry getIntersection(V3D_Line l, int oom, RoundingMode rm) {
        // Special case of parallel lines.
        V3D_Point tp = getP();
        if (isParallel(l, oom, rm)) {
            if (l.isIntersectedBy(tp, oom, rm)) {
                // If lines are coincident return this.
                return this;
            } else {
                
                if (Math_BigRational.equals(this.getDistance(l, oom, rm), BigRational.ZERO, oom)) {
                    return this;
                }
                
                return null;
            }
        }
        V3D_Point tq = getQ(oom, rm);
        V3D_Point lp = l.getP();
        V3D_Point lq = l.getQ(oom, rm);
        V3D_Vector plp = new V3D_Vector(tp, lp, oom, rm);
        V3D_Vector lqlp = new V3D_Vector(lq, lp, oom, rm);
        if (lqlp.getMagnitudeSquared().isZero()) {
            if (isIntersectedBy(lp, oom, rm)) {
                return lp;
            }
        }
        V3D_Vector qp = new V3D_Vector(tq, tp, oom, rm);
        if (qp.getMagnitudeSquared().isZero()) {
            if (l.isIntersectedBy(tp, oom, rm)) {
                return tp;
            }
        }
        BigRational a = plp.dx.multiply(lqlp.dx, oom, rm).getSqrt(oom, rm)
                .add(plp.dy.multiply(lqlp.dy, oom, rm).getSqrt(oom, rm))
                .add(plp.dz.multiply(lqlp.dz, oom, rm).getSqrt(oom, rm));
        BigRational b = lqlp.dx.multiply(qp.dx, oom, rm).getSqrt(oom, rm)
                .add(lqlp.dy.multiply(qp.dy, oom, rm).getSqrt(oom, rm))
                .add(lqlp.dz.multiply(qp.dz, oom, rm).getSqrt(oom, rm));
        BigRational c = plp.dx.multiply(qp.dx, oom, rm).getSqrt(oom, rm)
                .add(plp.dy.multiply(qp.dy, oom, rm).getSqrt(oom, rm))
                .add(plp.dz.multiply(qp.dz, oom, rm).getSqrt(oom, rm));
        BigRational d = lqlp.dx.multiply(lqlp.dx, oom, rm).getSqrt(oom, rm)
                .add(lqlp.dy.multiply(lqlp.dy, oom, rm).getSqrt(oom, rm))
                .add(lqlp.dz.multiply(lqlp.dz, oom, rm).getSqrt(oom, rm));
        BigRational eb = qp.dx.multiply(qp.dx, oom, rm).getSqrt(oom, rm)
                .add(qp.dy.multiply(qp.dy, oom, rm).getSqrt(oom, rm))
                .add(qp.dz.multiply(qp.dz, oom, rm).getSqrt(oom, rm));
//        BigRational a = (plp.dx.multiply(lqlp.dx)).add(plp.dy
//                .multiply(lqlp.dy)).add(plp.dz.multiply(lqlp.dz)).getSqrt(oom);
//        BigRational b = (lqlp.dx.multiply(qp.dx)).add(lqlp.dy
//                .multiply(qp.dy)).add(lqlp.dz.multiply(qp.dz)).getSqrt(oom);
//        BigRational c = (plp.dx.multiply(qp.dx)).add(plp.dy
//                .multiply(qp.dy)).add(plp.dz.multiply(qp.dz)).getSqrt(oom);
//        BigRational d = (lqlp.dx.multiply(lqlp.dx)).add(lqlp.dy
//                .multiply(lqlp.dy)).add(lqlp.dz.multiply(lqlp.dz)).getSqrt(oom);
//        BigRational e = (qp.dx.multiply(qp.dx)).add(qp.dy
//                .multiply(qp.dy)).add(qp.dz.multiply(qp.dz)).getSqrt(oom);
        BigRational den = (eb.multiply(d)).subtract(b.multiply(b));
        BigRational num = (a.multiply(b)).subtract(c.multiply(d));
        if (den.compareTo(BigRational.ZERO) == 0) {
            if (num.compareTo(BigRational.ZERO) == 0) {
                BigRational x;
                BigRational y;
                BigRational z;
                BigRational lamda;
                BigRational mu;
                //V3D_Vector tv = getV(oom, rm);
                V3D_Vector tv = v;
                //V3D_Vector lv = l.getV(oom, rm);
                V3D_Vector lv = l.v;
                if (tv.dx.isZero()) {
                    x = tp.getX(oom, rm);
                    if (lv.dx.isZero()) {
                        if (tv.dy.isZero()) {
                            y = tp.getY(oom, rm);
                            if (lv.dy.isZero()) {
                                z = tp.getZ(oom, rm);
                            } else {
                                if (tv.dz.isZero()) {
                                    z = tp.getZ(oom, rm);
                                } else {
                                    if (lv.dz.isZero()) {
                                        z = lp.getZ(oom, rm);
                                    } else {
                                        mu = (tp.getY(oom, rm).subtract(lp.getY(oom, rm))).divide(lv.getDY(oom, rm));
                                        z = lp.getZ(oom, rm).add(lv.getDZ(oom, rm).multiply(mu));
                                    }
                                }
                            }
                        } else {
                            if (lv.dy.isZero()) {
                                y = lp.getY(oom, rm);
                                if (tv.dz.isZero()) {
                                    z = tp.getZ(oom, rm);
                                } else {
                                    if (lv.dz.isZero()) {
                                        z = lp.getZ(oom, rm);
                                    } else {
                                        lamda = (lp.getY(oom, rm).subtract(tp.getY(oom, rm))).divide(tv.getDY(oom, rm));
                                        z = tp.getZ(oom, rm).add(tv.getDZ(oom, rm).multiply(lamda));
                                    }
                                }
                                //x = pv.getX(oom);            
                                //p.getX(oom) + v.getDX(oom) * lamda = l.pv.getX(oom) + l.v.getDX(oom) * mu
                                //p.getY(oom) + v.getDY(oom) * lamda = l.pv.getY(oom) + l.v.getDY(oom) * mu
                                //p.getZ(oom) + v.getDZ(oom) * lamda = l.pv.getZ(oom) + l.v.getDZ(oom) * mu

                            } else {
                                if (tv.dz.isZero()) {
                                    z = tp.getZ(oom, rm);
                                    mu = (tp.getZ(oom, rm).subtract(lp.getZ(oom, rm))).divide(lv.getDY(oom, rm));
                                    y = lp.getY(oom, rm).add(lv.getDY(oom, rm).multiply(mu));
                                } else {
                                    if (lv.dz.isZero()) {
                                        z = lp.getZ(oom, rm);
                                        lamda = (lp.getZ(oom, rm).subtract(tp.getZ(oom, rm))).divide(tv.getDY(oom, rm));
                                        y = tp.getY(oom, rm).add(tv.getDY(oom, rm).multiply(lamda));
                                    } else {
                                        // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                        mu = ((pv.getY(oom).add(v.getDY(oom).multiply(lamda))).subtract(l.pv.getY(oom))).divide(l.v.getDY(oom));
//                                        lamda = ((l.pv.getZ(oom).subtract(pv.getZ(oom))).divide(v.getDZ(oom))).add(l.v.getDZ(oom).multiply(mu));
//                                        lamda = ((l.pv.getZ(oom).subtract(pv.getZ(oom))).divide(v.getDZ(oom))).add(l.v.getDZ(oom).multiply(((pv.getY(oom).add(v.getDY(oom).multiply(lamda))).subtract(l.pv.getY(oom))).divide(l.v.getDY(oom))));
//                                        l = ((bz-az)/adz) + (bdz*(ady*(l-by)/bdy))
//                                        l = ((bz-az)/adz) + bdz*ady*l/bdy - bdz*ady*by/bdy
//                                        l - bdz*ady*l/bdy = ((bz-az)/adz) - bdz*ady*by/bdy
//                                        l (1 - bdz*ady/bdy) = ((bz-az)/adz) - bdz*ady*by/bdy
//                                        l = (((bz-az)/adz) - bdz*ady*by/bdy)/(1 - bdz*ady/bdy)
                                        BigRational den2 = BigRational.ONE.subtract(lv.getDZ(oom, rm).multiply(tv.getDY(oom, rm).divide(lv.getDY(oom, rm))));
                                        if (den2.compareTo(BigRational.ZERO) != 0) {
                                            lamda = (((lp.getZ(oom, rm).subtract(tp.getZ(oom, rm))).divide(tv.getDZ(oom, rm))).subtract(lv.getDZ(oom, rm).multiply(tv.getDY(oom, rm).multiply(lp.getY(oom, rm).divide(lv.getDY(oom, rm)))))).divide(den2);
                                            z = tp.getZ(oom, rm).add(tv.getDZ(oom, rm).multiply(lamda));
                                            y = tp.getY(oom, rm).add(tv.getDY(oom, rm).multiply(lamda));
                                        } else {
                                            den2 = BigRational.ONE.subtract(lv.getDY(oom, rm).multiply(tv.getDZ(oom, rm).divide(lv.getDZ(oom, rm))));
                                            if (den2.compareTo(BigRational.ZERO) != 0) {
                                                lamda = (((lp.getY(oom, rm).subtract(tp.getY(oom, rm))).divide(tv.getDY(oom, rm))).subtract(lv.getDY(oom, rm).multiply(tv.getDZ(oom, rm).multiply(lp.getZ(oom, rm).divide(lv.getDZ(oom, rm)))))).divide(den2);
                                                z = tp.getZ(oom, rm).add(tv.getDZ(oom, rm).multiply(lamda));
                                                y = tp.getY(oom, rm).add(tv.getDY(oom, rm).multiply(lamda));
                                            } else {
                                                // This should not happen!
                                                z = null;
                                                y = null;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        mu = (tp.getX(oom, rm).subtract(lp.getX(oom, rm))).divide(lv.getDX(oom, rm));
                        if (tv.dy.isZero()) {
                            if (lv.dy.isZero()) {
                                y = tp.getY(oom, rm);
                                z = tp.getZ(oom, rm);
                            } else {
                                if (tv.dz.isZero()) {
                                    y = lp.getY(oom, rm).add(lv.getDY(oom, rm).multiply(mu));
                                } else {
                                    y = tp.getY(oom, rm).add(tv.getDY(oom, rm).multiply(mu));
                                }
                                if (lv.dz.isZero()) {
                                    z = tp.getZ(oom, rm);
                                } else {
                                    z = lp.getZ(oom, rm).add(lv.getDZ(oom, rm).multiply(mu));
                                }
                            }
                        } else {
                            lamda = ((lp.getY(oom, rm).add(lv.getDY(oom, rm).multiply(mu)))
                                    .subtract(tp.getX(oom, rm))).divide(tv.getDY(oom, rm));
                            if (tv.dz.isZero()) {
                                z = tp.getZ(oom, rm);
                            } else {
                                z = tp.getZ(oom, rm).add(tv.getDZ(oom, rm).multiply(lamda));
                            }
                            if (lv.dy.isZero()) {
                                y = tp.getY(oom, rm);
                            } else {
                                y = lp.getY(oom, rm).add(lv.getDY(oom, rm).multiply(mu));
                            }
                        }
                    }
                } else {
                    if (lv.dx.isZero()) {
                        lamda = lp.getX(oom, rm).subtract(tp.getX(oom, rm)).divide(tv.getDX(oom, rm));
                        x = lp.getX(oom, rm);
                        if (tv.dy.isZero()) {
                            y = tp.getY(oom, rm);
                            if (tv.dz.isZero()) {
                                z = tp.getZ(oom, rm);
                            } else {
                                if (lv.dz.isZero()) {
                                    z = lp.getZ(oom, rm);
                                } else {
                                    mu = tp.getY(oom, rm).subtract(lp.getY(oom, rm)).divide(lv.getDY(oom, rm));
                                    z = lp.getZ(oom, rm).add(lv.getDZ(oom, rm).multiply(mu));
                                }
                            }
                        } else {
                            if (tv.dy.isZero()) {
                                y = tp.getY(oom, rm);
                                if (lv.dy.isZero()) {
                                    if (tv.dz.isZero()) {
                                        z = tp.getZ(oom, rm);
                                    } else {
                                        if (lv.dz.isZero()) {
                                            z = lp.getZ(oom, rm);
                                        } else {
                                            mu = ((tp.getZ(oom, rm).add(tv.getDZ(oom, rm).multiply(lamda))).subtract(lp.getZ(oom, rm))).divide(lv.getDZ(oom, rm));
                                            z = lp.getZ(oom, rm).add(lv.getDZ(oom, rm).multiply(mu));
                                        }
                                    }
                                } else {
                                    if (tv.dz.isZero()) {
                                        z = tp.getZ(oom, rm);
                                    } else {
                                        if (lv.dz.isZero()) {
                                            z = lp.getZ(oom, rm);
                                        } else {
                                            mu = (tp.getZ(oom, rm).subtract(lp.getZ(oom, rm))).divide(lv.getDZ(oom, rm));
                                            z = lp.getZ(oom, rm).add(lv.getDZ(oom, rm).multiply(mu));
                                        }
                                    }
                                }
                            } else {
                                if (lv.dy.isZero()) {
                                    y = lp.getY(oom, rm);
                                    if (tv.dz.isZero()) {
                                        z = tp.getZ(oom, rm);
                                    } else {
                                        if (lv.dz.isZero()) {
                                            z = lp.getZ(oom, rm);
                                        } else {
                                            mu = ((tp.getZ(oom, rm).add(tv.getDZ(oom, rm).multiply(lamda))).subtract(lp.getZ(oom, rm))).divide(lv.getDZ(oom, rm));
                                            z = lp.getZ(oom, rm).add(lv.getDZ(oom, rm).multiply(mu));
                                        }
                                    }
                                } else {
                                    y = tp.getY(oom, rm).add(tv.getDY(oom, rm).multiply(lamda));
                                    if (tv.dz.isZero()) {
                                        z = tp.getZ(oom, rm);
                                    } else {
                                        if (lv.dz.isZero()) {
                                            //z = l.pv.getZ(oom);
                                            //z = l.getP().getDZ(oom, rm);
                                            z = l.pv.getDZ(oom, rm);
                                        } else {
                                            mu = ((tp.getZ(oom, rm).add(tv.getDZ(oom, rm).multiply(lamda))).subtract(lp.getZ(oom, rm))).divide(lv.getDZ(oom, rm));
                                            z = lp.getZ(oom, rm).add(lv.getDZ(oom, rm).multiply(mu));
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        // v.getDX(oom) > 0 && l.v.getDX(oom) > 0
                        if (tv.dy.isZero()) {
                            y = tp.getY(oom, rm);
                            if (lv.dy.isZero()) {
                                if (tv.dz.isZero()) {
                                    z = tp.getZ(oom, rm);
                                    x = tp.getX(oom, rm);
                                } else {
                                    if (lv.dz.isZero()) {
                                        z = lp.getZ(oom, rm);
                                        lamda = (lp.getZ(oom, rm).subtract(tp.getZ(oom, rm))).divide(tv.getDZ(oom, rm));
                                        x = tp.getX(oom, rm).add(tv.getDX(oom, rm).multiply(lamda));
                                    } else {
                                        // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                        mu = ((pv.getX(oom).add(v.getDX(oom).multiply(lamda))).subtract(l.pv.getX(oom))).divide(l.v.getDX(oom));
//                                        lamda = ((l.pv.getZ(oom).subtract(pv.getZ(oom))).divide(v.getDZ(oom))).add(l.v.getDZ(oom).multiply(mu));
//                                        lamda = ((l.pv.getZ(oom).subtract(pv.getZ(oom))).divide(v.getDZ(oom))).add(l.v.getDZ(oom).multiply(((pv.getX(oom).add(v.getDX(oom).multiply(lamda))).subtract(l.pv.getX(oom))).divide(l.v.getDX(oom))));
//                                        l = ((bz-az)/adz) + (bdz*(adx*(l-bx)/bdx))
//                                        l = ((bz-az)/adz) + bdz*adx*l/bdx - bdz*adx*bx/bdx
//                                        l - bdz*adx*l/bdx = ((bz-az)/adz) - bdz*adx*bx/bdx
//                                        l (1 - bdz*adx/bdx) = ((bz-az)/adz) - bdz*adx*bx/bdx
//                                        l = (((bz-az)/adz) - bdz*adx*bx/bdx)/(1 - bdz*adx/bdx)
                                        BigRational den2 = BigRational.ONE.subtract(lv.getDZ(oom, rm).multiply(tv.getDX(oom, rm).divide(lv.getDX(oom, rm))));
                                        if (den2.compareTo(BigRational.ZERO) != 0) {
                                            lamda = (((lp.getZ(oom, rm).subtract(tp.getZ(oom, rm))).divide(tv.getDZ(oom, rm))).subtract(lv.getDZ(oom, rm).multiply(tv.getDX(oom, rm).multiply(lp.getX(oom, rm).divide(lv.getDX(oom, rm)))))).divide(den2);
                                            z = tp.getZ(oom, rm).add(tv.getDZ(oom, rm).multiply(lamda));
                                            x = tp.getX(oom, rm).add(tv.getDX(oom, rm).multiply(lamda));
                                        } else {
                                            den2 = BigRational.ONE.subtract(lv.getDX(oom, rm).multiply(tv.getDZ(oom, rm).divide(lv.getDZ(oom, rm))));
                                            if (den2.compareTo(BigRational.ZERO) != 0) {
                                                lamda = (((lp.getX(oom, rm).subtract(tp.getX(oom, rm))).divide(tv.getDX(oom, rm))).subtract(lv.getDX(oom, rm).multiply(tv.getDZ(oom, rm).multiply(lp.getZ(oom, rm).divide(lv.getDZ(oom, rm)))))).divide(den2);
                                                z = tp.getZ(oom, rm).add(tv.getDZ(oom, rm).multiply(lamda));
                                                x = tp.getX(oom, rm).add(tv.getDX(oom, rm).multiply(lamda));
                                            } else {
                                                // This should not happen!
                                                z = null;
                                                x = null;
                                            }
                                        }
                                    }
                                }
                            } else {
                                //mu = tp.getY(oom).subtract(lp.getY(oom)).divide(l.v.getDY(oom));
                                //mu = tp.getY(oom, rm).subtract(lp.getY(oom, rm)).divide(l.getV(oom, rm).getDY(oom, rm));
                                mu = tp.getY(oom, rm).subtract(lp.getY(oom, rm)).divide(l.v.getDY(oom, rm));
                                x = lp.getX(oom, rm).add(lv.getDX(oom, rm).multiply(mu));
                                z = lp.getZ(oom, rm).add(lv.getDZ(oom, rm).multiply(mu));
                            }
                        } else {
                            // v.getDX(oom) > 0 && l.v.getDX(oom) > 0 && v.getDY(oom) > 0
                            if (tv.dz.isZero()) {
                                z = tp.getZ(oom, rm);
                                if (lv.dz.isZero()) {
                                    // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                    mu = ((pv.getX(oom).add(v.getDX(oom).multiply(lamda))).subtract(l.pv.getX(oom))).divide(l.v.getDX(oom));
//                                    lamda = ((l.pv.getY(oom).subtract(pv.getY(oom))).divide(v.getDY(oom))).add(l.v.getDY(oom).multiply(mu));
//                                    lamda = ((l.pv.getY(oom).subtract(pv.getY(oom))).divide(v.getDY(oom))).add(l.v.getDY(oom).multiply(((pv.getX(oom).add(v.getDX(oom).multiply(lamda))).subtract(l.pv.getX(oom))).divide(l.v.getDX(oom))));
//                                    l = ((by - ay) / ady) + (bdy * (adx * (l - bx) / bdx))
//                                    l = ((by - ay) / ady) + bdy * adx * l / bdx - bdy * adx * bx / bdx
//                                    l - bdy * adx * l / bdx = ((by - ay) / ady) - bdy * adx * bx / bdx
//                                    l(1 - bdy * adx / bdx) = ((by - ay) / ady) - bdy * adx * bx / bdx
//                                    l = (((by-ay)/ady) - bdy*adx*bx/bdx)/(1 - bdy*adx/bdx)
                                    BigRational den2 = BigRational.ONE.subtract(lv.getDY(oom, rm).multiply(tv.getDX(oom, rm).divide(lv.getDX(oom, rm))));
                                    if (den2.compareTo(BigRational.ZERO) != 0) {
                                        lamda = (((lp.getY(oom, rm).subtract(tp.getY(oom, rm))).divide(tv.getDY(oom, rm))).subtract(lv.getDY(oom, rm).multiply(tv.getDX(oom, rm).multiply(lp.getX(oom, rm).divide(lv.getDX(oom, rm)))))).divide(den2);
                                        y = tp.getY(oom, rm).add(tv.getDY(oom, rm).multiply(lamda));
                                        x = tp.getX(oom, rm).add(tv.getDX(oom, rm).multiply(lamda));
                                    } else {
                                        den2 = BigRational.ONE.subtract(lv.getDX(oom, rm).multiply(tv.getDY(oom, rm).divide(lv.getDY(oom, rm))));
                                        if (den2.compareTo(BigRational.ZERO) != 0) {
                                            lamda = (((lp.getX(oom, rm).subtract(tp.getX(oom, rm))).divide(tv.getDX(oom, rm))).subtract(lv.getDX(oom, rm).multiply(tv.getDY(oom, rm).multiply(lp.getY(oom, rm).divide(lv.getDY(oom, rm)))))).divide(den2);
                                            y = tp.getY(oom, rm).add(tv.getDY(oom, rm).multiply(lamda));
                                            x = tp.getX(oom, rm).add(tv.getDX(oom, rm).multiply(lamda));
                                        } else {
                                            // This should not happen!
                                            y = null;
                                            x = null;
                                        }
                                    }
                                } else {
                                    mu = (tp.getZ(oom, rm).subtract(lp.getZ(oom, rm))).divide(lv.getDZ(oom, rm));
                                    y = lp.getY(oom, rm).add(lv.getDY(oom, rm).multiply(mu));
                                    x = lp.getX(oom, rm).add(lv.getDX(oom, rm).multiply(mu));
                                }
                            } else {
                                if (lv.dz.isZero()) {
                                    z = lp.getZ(oom, rm);
                                    lamda = (lp.getZ(oom, rm).subtract(tp.getZ(oom, rm))).divide(tv.getDZ(oom, rm));
                                    y = tp.getY(oom, rm).add(tv.getDY(oom, rm).multiply(lamda));
                                    x = tp.getX(oom, rm).add(tv.getDX(oom, rm).multiply(lamda));
                                } else {
                                    // There are 6 ways to calculate lamda. One way should work! - If not try calculating mu.
                                    BigRational den2 = BigRational.ONE.subtract(lv.getDY(oom, rm).multiply(tv.getDX(oom, rm).divide(lv.getDX(oom, rm))));
                                    if (den2.compareTo(BigRational.ZERO) != 0) {
                                        lamda = (((lp.getY(oom, rm).subtract(tp.getY(oom, rm))).divide(tv.getDY(oom, rm))).subtract(lv.getDY(oom, rm).multiply(tv.getDX(oom, rm).multiply(lp.getX(oom, rm).divide(lv.getDX(oom, rm)))))).divide(den2);
                                        x = tp.getX(oom, rm).add(tv.getDX(oom, rm).multiply(lamda));
                                        y = tp.getY(oom, rm).add(tv.getDY(oom, rm).multiply(lamda));
                                        z = tp.getZ(oom, rm).add(tv.getDZ(oom, rm).multiply(lamda));
//                                        x = qv.getX(oom, rm).add(v.getDX(oom, rm).multiply(lamda));
//                                        y = qv.getY(oom, rm).add(v.getDY(oom, rm).multiply(lamda));
//                                        z = qv.getZ(oom, rm).add(v.getDZ(oom, rm).multiply(lamda));
                                    } else {
                                        den2 = BigRational.ONE.subtract(lv.getDY(oom, rm).multiply(tv.getDZ(oom, rm).divide(lv.getDZ(oom, rm))));
                                        if (den2.compareTo(BigRational.ZERO) != 0) {
                                            lamda = (((lp.getY(oom, rm).subtract(tp.getY(oom, rm))).divide(tv.getDY(oom, rm))).subtract(lv.getDY(oom, rm).multiply(tv.getDZ(oom, rm).multiply(lp.getZ(oom, rm).divide(lv.getDZ(oom, rm)))))).divide(den2);
                                            x = tp.getX(oom, rm).add(tv.getDX(oom, rm).multiply(lamda));
                                            y = tp.getY(oom, rm).add(tv.getDY(oom, rm).multiply(lamda));
                                            z = tp.getZ(oom, rm).add(tv.getDZ(oom, rm).multiply(lamda));
                                        } else {
                                            den2 = BigRational.ONE.subtract(lv.getDZ(oom, rm).multiply(tv.getDX(oom, rm).divide(lv.getDX(oom, rm))));
                                            if (den2.compareTo(BigRational.ZERO) != 0) {
                                                lamda = (((lp.getZ(oom, rm).subtract(tp.getZ(oom, rm))).divide(tv.getDZ(oom, rm))).subtract(lv.getDZ(oom, rm).multiply(tv.getDX(oom, rm).multiply(lp.getX(oom, rm).divide(lv.getDX(oom, rm)))))).divide(den2);
                                                x = tp.getX(oom, rm).add(tv.getDX(oom, rm).multiply(lamda));
                                                y = tp.getY(oom, rm).add(tv.getDY(oom, rm).multiply(lamda));
                                                z = tp.getZ(oom, rm).add(tv.getDZ(oom, rm).multiply(lamda));
                                            } else {
                                                den2 = BigRational.ONE.subtract(lv.getDZ(oom, rm).multiply(tv.getDY(oom, rm).divide(lv.getDY(oom, rm))));
                                                if (den2.compareTo(BigRational.ZERO) != 0) {
                                                    lamda = (((lp.getZ(oom, rm).subtract(tp.getZ(oom, rm))).divide(tv.getDZ(oom, rm))).subtract(lv.getDZ(oom, rm).multiply(tv.getDY(oom, rm).multiply(lp.getY(oom, rm).divide(lv.getDY(oom, rm)))))).divide(den2);
                                                    x = tp.getX(oom, rm).add(tv.getDX(oom, rm).multiply(lamda));
                                                    y = tp.getY(oom, rm).add(tv.getDY(oom, rm).multiply(lamda));
                                                    z = tp.getZ(oom, rm).add(tv.getDZ(oom, rm).multiply(lamda));
                                                } else {
                                                    den2 = BigRational.ONE.subtract(lv.getDX(oom, rm).multiply(tv.getDX(oom, rm).divide(lv.getDY(oom, rm))));
                                                    if (den2.compareTo(BigRational.ZERO) != 0) {
                                                        lamda = (((lp.getX(oom, rm).subtract(tp.getX(oom, rm))).divide(tv.getDX(oom, rm))).subtract(lv.getDX(oom, rm).multiply(tv.getDY(oom, rm).multiply(lp.getY(oom, rm).divide(lv.getDY(oom, rm)))))).divide(den2);
                                                        x = tp.getX(oom, rm).add(tv.getDX(oom, rm).multiply(lamda));
                                                        y = tp.getY(oom, rm).add(tv.getDY(oom, rm).multiply(lamda));
                                                        z = tp.getZ(oom, rm).add(tv.getDZ(oom, rm).multiply(lamda));
                                                    } else {
                                                        den2 = BigRational.ONE.subtract(lv.getDX(oom, rm).multiply(tv.getDX(oom, rm).divide(lv.getDZ(oom, rm))));
                                                        if (den2.compareTo(BigRational.ZERO) != 0) {
                                                            lamda = (((lp.getX(oom, rm).subtract(tp.getX(oom, rm))).divide(tv.getDX(oom, rm))).subtract(lv.getDX(oom, rm).multiply(tv.getDZ(oom, rm).multiply(lp.getZ(oom, rm).divide(lv.getDZ(oom, rm)))))).divide(den2);
                                                            x = tp.getX(oom, rm).add(tv.getDX(oom, rm).multiply(lamda));
                                                            y = tp.getY(oom, rm).add(tv.getDY(oom, rm).multiply(lamda));
                                                            z = tp.getZ(oom, rm).add(tv.getDZ(oom, rm).multiply(lamda));
                                                        } else {
                                                            // This should not happen!
                                                            x = null;
                                                            y = null;
                                                            z = null;
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
                //p.getX(oom) + v.getDX(oom) * lamda = l.pv.getX(oom) + l.v.getDX(oom) * mu
                //p.getY(oom) + v.getDY(oom) * lamda = l.pv.getY(oom) + l.v.getDY(oom) * mu
                //p.getZ(oom) + v.getDZ(oom) * lamda = l.pv.getZ(oom) + l.v.getDZ(oom) * mu
                return new V3D_Point(x, y, z);
            }
            return null;
        }
        BigRational mua = num.divide(den);
        V3D_Point pi = new V3D_Point(
                (tp.getX(oom, rm).subtract(mua.multiply(qp.getDX(oom, rm)))),
                (tp.getY(oom, rm).subtract(mua.multiply(qp.getDY(oom, rm)))),
                (tp.getZ(oom, rm).subtract(mua.multiply(qp.getDZ(oom, rm)))));
        // If point pv is on both lines then return this as the intersection.
        if (isIntersectedBy(pi, oom, rm) && l.isIntersectedBy(pi, oom, rm)) {
            return pi;
        }
        BigRational mub;
        BigRational num2 = a.add(b.multiply(mua));
        if (d.isZero()) {
            if (num2.isZero()) {
                mub = BigRational.ONE;
            } else {
                throw new RuntimeException("Divide by"); 
            }
        } else {
            mub = num2.divide(d).negate();
        }
        V3D_Point qi = new V3D_Point(
                (lp.getX(oom, rm).add(mub.multiply(lqlp.getDX(oom, rm)))),
                (lp.getY(oom, rm).add(mub.multiply(lqlp.getDY(oom, rm)))),
                (lp.getZ(oom, rm).add(mub.multiply(lqlp.getDZ(oom, rm)))));
        // If point qv is on both lines then return this as the intersection.
        if (isIntersectedBy(qi, oom, rm) && l.isIntersectedBy(qi, oom, rm)) {
            return qi;
        }
        /**
         * The only time when pi and qi should be different is when the lines do
         * not intersect. In this case pi and qi are meant to be the end points
         * of the shortest line between the two lines.
         */
        if (pi.equals(qi, oom, rm)) {
            return pi;
        } else {
            //return null;
            
            if (Math_BigRational.equals(new V3D_LineSegment(pi, qi, oom, rm)
                    .getLength(oom, rm).getSqrt(oom, rm), BigRational.ZERO, oom)) {
                int debug = 1;
                //return pi;
                return qi;
            } else {
                return null;
            }
            
        }
        //return new V3D_Line(pi, qi);
    }

    /**
     * @param pt A point for which the shortest line segment to this is
     * returned.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The line segment having the shortest distance between {@code pt}
     * and {@code this}.
     */
    public V3D_FiniteGeometry getLineOfIntersection(V3D_Point pt, int oom,
            RoundingMode rm) {
        if (isIntersectedBy(pt, oom, rm)) {
            return pt;
        }
        return new V3D_LineSegment(pt, getPointOfIntersection(pt, oom, rm), oom, rm);
        //return new V3D_LineSegment(pt.getVector(oom), getPointOfIntersection(pt, oom).getVector(oom), oom);
    }

    /**
     * Adapted from:
     * https://math.stackexchange.com/questions/1521128/given-a-line-and-a-point-in-3d-how-to-find-the-closest-point-on-the-line
     *
     * @param pt The point projected onto this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A point on {@code this} which is the shortest distance from
     * {@code pt}.
     */
    public V3D_Point getPointOfIntersection(V3D_Point pt, int oom,
            RoundingMode rm) {
        if (isIntersectedBy(pt, oom, rm)) {
            return pt;
        }
        V3D_Plane ptv = new V3D_Plane(pt, v);
        return (V3D_Point) ptv.getIntersection(this, oom, rm);
//        // a = v
//        // p1 = pv
//        // p0 = pt
//        //t = (- a.x * (p1.x - p0.x) - a.y * (p1.y - p0.y) - a.z * (p1.z - p0.z)) 
//        //     / (a.x * a.x + a.y * a.y + a.z * a.z)
//        //V3D_Vector tv = getV(oom, rm);
//        V3D_Vector tv = v;
//        V3D_Point tp = getP();
//        //V3D_Point tq = getQ();
//        BigRational vdx = tv.dx.getSqrt(oom, rm);
//        BigRational t = (vdx.negate().multiply((tp.getX(oom, rm).subtract(pt.getX(oom, rm))))
//                .subtract(tv.dy.getSqrt(oom, rm).multiply((tp.getY(oom, rm).subtract(pt.getY(oom, rm)))))
//                .subtract(tv.dz.getSqrt(oom, rm).multiply(tp.getZ(oom, rm).subtract(pt.getZ(oom, rm)))))
//                .divide(tv.dx.getX().add(tv.dy.getX()).add(tv.dz.getX()));
////        BigRational t2 = (vdx.negate().multiply((qv.getX(oom).subtract(pt.getX(oom))))
////                .subtract(v.dy.getSqrt(oom).multiply((qv.getY(oom).subtract(pt.getY(oom))))) 
////                .subtract(v.dz.getSqrt(oom).multiply(qv.getZ(oom).subtract(pt.getZ(oom))))) 
////                .divide(v.dx.getX().add(v.dy.getX()).add(v.dz.getX()));
//        //return new V3D_Point(pv.getVector(oom).add(v.multiply(t, oom), oom), oom);
//        //return new V3D_Point(pv.rel, pv.offset.add(v.multiply(t, oom), oom));
//        //return tp.translate(tv.multiply(t, oom), oom);
//        return new V3D_Point(tp.getVector(oom, rm).add(tv.multiply(t, oom, rm), oom, rm));

//        // P = pt
//        // Q = pv
//        // R = qv
//        // R-Q = v
//        // Q-P = new V3D_Vector(pt, pv, oom)
//        if (pv.isOrigin()) {
//            V3D_Vector vr = v.reverse();
//            BigRational num = vr.getDotProduct(new V3D_Vector(pt, qv, oom), oom);
//            BigRational den = vr.getDotProduct(vr, oom);
//            return qv.translate(vr.multiply(num.divide(den), oom), oom);
//        } else {
//            BigRational num = v.getDotProduct(new V3D_Vector(pt, pv, oom), oom);
//            BigRational den = v.getDotProduct(v, oom);
//            return pv.translate(v.multiply(num.divide(den), oom), oom);
//        }
//        V3D_Vector a = new V3D_Vector(pt, pv, oom);
//        //V3D_Vector a = new V3D_Vector(pv, pt);
//        BigRational adb = a.getDotProduct(v, oom);
//        BigRational vdv = v.getDotProduct(v, oom);
//        return pv.translate(v.multiply(adb.divide(vdv), oom).reverse(), oom);
//        //return qv.translate(v.multiply(adb.divide(vdv)));
//        //return pv.translate(v.multiply(adb.divide(vdv)));
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The line of intersection between {@code this} and {@code l}. The
     * point pv is a point on or near this, and the point qv is a point on or
     * near l. Whether the points are on or near is down to rounding error and
     * precision.
     */
    public V3D_LineSegment getLineOfIntersection(V3D_Line l, int oom, RoundingMode rm) {
        int oomn6 = oom - 6;
        if (isParallel(l, oom, rm)) {
            return null;
        }
        if (getIntersection(l, oom, rm) != null) {
            return null;
        }
        V3D_Point tp = getP();
        V3D_Point lp = l.getP();
        V3D_Vector A = new V3D_Vector(tp, lp, oomn6, rm);
        //V3D_Vector B = getV(oom, rm).reverse();
        V3D_Vector B = v.reverse();
        //V3D_Vector C = l.getV(oom, rm).reverse();
        V3D_Vector C = l.v.reverse();

        BigRational AdB = A.getDotProduct(B, oomn6, rm);
        BigRational AdC = A.getDotProduct(C, oomn6, rm);
        BigRational CdB = C.getDotProduct(B, oomn6, rm);
        BigRational BdB = B.getDotProduct(B, oomn6, rm);
        BigRational CdC = C.getDotProduct(C, oomn6, rm);

        BigRational ma = (AdC.multiply(CdB)).subtract(AdB.multiply(CdC))
                .divide((BdB.multiply(CdC)).subtract(CdB.multiply(CdB)));
        BigRational mb = ((ma.multiply(CdB)).add(AdC)).divide(CdC);

        //V3D_Point tpi = tp.translate(B.multiply(ma, oom), oom);
        //V3D_Vector tpi = tp.getVector(oom).add(B.multiply(ma, oom), oom);
        V3D_Vector tpi = tp.getVector(oom, rm).subtract(B.multiply(ma, oom, rm), oom, rm);

        //V3D_Point lpi = l.pv.translate(C.multiply(mb, oom), oom);
        //V3D_Point lpi = lp.translate(C.multiply(mb.negate(), oom), oom);
        //V3D_Vector lpi = lp.getVector(oom).add(C.multiply(mb.negate(), oom), oom);
        V3D_Vector lpi = lp.getVector(oom, rm).subtract(C.multiply(mb, oom, rm), oom, rm);

        //return new V3D_LineSegment(tpi, lpi, oom);
        //return new V3D_LineSegment(tpi.getVector(oom), lpi.getVector(oom), oom);
        //return new V3D_LineSegment(e, tpi, lpi);
        V3D_Point loip = new V3D_Point(tpi);
        V3D_Point loiq = new V3D_Point(lpi);
        if (loip.equals(loiq, oom, rm)) {
            return null;
        } else {
            return new V3D_LineSegment(loip, loiq, oom, rm);
        }
//        // p13
//        //V3D_Vector plp = new V3D_Vector(pv, lp, oom);
//        V3D_Vector plp = new V3D_Vector(tp, lp, oom);
//        // p43
//        //V3D_Vector lqlp = l.v.reverse();//new V3D_Vector(l.qv, l.pv);
//        V3D_Vector lqlp = l.v;//new V3D_Vector(l.qv, l.pv);
//        if (lqlp.getMagnitudeSquared().compareTo(BigRational.ZERO) == 0) {
//            return null;
//        }
//        // p21
//        //V3D_Vector qp = v.reverse();//new V3D_Vector(qv, pv);
//        V3D_Vector qp = v;//new V3D_Vector(qv, pv);
//        if (qp.getMagnitudeSquared().compareTo(BigRational.ZERO) == 0) {
//            return null;
//        }
//        // p1343
//        BigRational a = (plp.getDX(oom).multiply(lqlp.getDX(oom))).add(plp.getDY(oom)
//                .multiply(lqlp.getDY(oom))).add(plp.getDZ(oom).multiply(lqlp.getDZ(oom)));
//        BigRational b = (lqlp.getDX(oom).multiply(qp.getDX(oom))).add(lqlp.getDY(oom)
//                .multiply(qp.getDY(oom))).add(lqlp.getDZ(oom).multiply(qp.getDZ(oom)));
//        BigRational c = (plp.getDX(oom).multiply(qp.getDX(oom))).add(plp.getDY(oom)
//                .multiply(qp.getDY(oom))).add(plp.getDZ(oom).multiply(qp.getDZ(oom)));
//        BigRational d = (lqlp.getDX(oom).multiply(lqlp.getDX(oom))).add(lqlp.getDY(oom)
//                .multiply(lqlp.getDY(oom))).add(lqlp.getDZ(oom).multiply(lqlp.getDZ(oom)));
//        BigRational e = (qp.getDX(oom).multiply(qp.getDX(oom))).add(qp.getDY(oom)
//                .multiply(qp.getDY(oom))).add(qp.getDZ(oom).multiply(qp.getDZ(oom)));
//        BigRational den = (e.multiply(d)).subtract(b.multiply(b));
//        if (den.compareTo(BigRational.ZERO) == 0) {
//            return null;
//        }
//        BigRational num = (a.multiply(b)).subtract(c.multiply(d));
//        // dmnop = (xm - xn)(xo - xp) + (ym - yn)(yo - yp) + (zm - zn)(zo - zp)
//        // mua = ( d1343 d4321 - d1321 d4343 ) / ( d2121 d4343 - d4321 d4321 )
//        BigRational mua = num.divide(den);
//        // mub = ( d1343 + mua d4321 ) / d4343
//        BigRational mub = (a.add(b.multiply(mua))).divide(d);
//        V3D_Point pi = new V3D_Point(
//                (pv.getX(oom).add(mua.multiply(qp.getDX(oom)))),
//                (pv.getY(oom).add(mua.multiply(qp.getDY(oom)))),
//                (pv.getZ(oom).add(mua.multiply(qp.getDZ(oom)))));
//        V3D_Point qi = new V3D_Point(
//                (l.pv.getX(oom).add(mub.multiply(lqlp.getDX(oom)))),
//                (l.pv.getY(oom).add(mub.multiply(lqlp.getDY(oom)))),
//                (l.pv.getZ(oom).add(mub.multiply(lqlp.getDZ(oom)))));
//        if (pi.equals(qi)) {
//            return pi;
//        }
//        return new V3D_LineSegment(pi, qi, oom);
    }

//    /**
//     * @param v The vector to translate.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return a new line.
//     */
//    @Override
//    public V3D_Line translate(V3D_Vector v, int oom) {
//        V3D_Line l = new V3D_Line(this);
//        l.offset = l.offset.add(v, oom);
//        return l;
//    }
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance between this and {@code pv}.
     */
    public BigRational getDistance(V3D_Point pt, int oom, RoundingMode rm) {
        if (isIntersectedBy(pt, oom, rm)) {
            return BigRational.ZERO;
        }
        return new Math_BigRationalSqrt(
                getDistanceSquared(pt, true, oom, rm), oom, rm).getSqrt(oom, rm);
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
     * @param pt A point for which the minimum distance from {@code this} is
     * returned.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance between this and {@code pv}.
     */
    public BigRational getDistanceSquared(V3D_Point pt, int oom, RoundingMode rm) {
        if (isIntersectedBy(pt, oom, rm)) {
            return BigRational.ZERO;
        } else {
            return getDistanceSquared(pt, true, oom, rm);
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
     * {@link #getDistanceSquared(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int, java.math.RoundingMode)}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance between this and {@code pv}.
     */
    protected BigRational getDistanceSquared(V3D_Point pt, boolean noInt,
            int oom, RoundingMode rm) {
        V3D_Vector pp = new V3D_Vector(pt, getP(), oom, rm);
        V3D_Vector qp = new V3D_Vector(pt, getQ(oom, rm), oom, rm);
        BigRational num = (pp.getCrossProduct(qp, oom, rm)).getMagnitudeSquared();
        //BigRational den = getV(oom, rm).getMagnitudeSquared();
        BigRational den = v.getMagnitudeSquared();
        return num.divide(den);
    }

    /**
     * https://en.wikipedia.org/wiki/Skew_lines#Nearest_points
     * https://en.wikipedia.org/wiki/Distance_between_two_parallel_lines
     *
     * @param l A line for which the minimum distance from {@code this} is
     * returned.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The minimum distance between this and {@code l}.
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
        V3D_Point tp = getP();
        if (isParallel(l, oom, rm)) {
            return l.getDistanceSquared(tp, oom, rm);
        } else {
            /**
             * Calculate the direction vector of the line connecting the closest
             * points by computing the cross product.
             */
            //V3D_Vector cp = l.v.getCrossProduct(v, oom, rm);
            //V3D_Vector cp = getV(oom, rm).getCrossProduct(l.getV(oom, rm), oom, rm);
            V3D_Vector cp = v.getCrossProduct(l.v, oom, rm); //20, 11, 26
            /**
             * Calculate the delta from {@link #p} and l.p
             */
//            V3D_Vector delta = new V3D_Vector(l.pv, oom).subtract(
//                    new V3D_Vector(tp, oom), oom);
            V3D_Vector delta = l.getP().getVector(oom, rm).subtract(
                    new V3D_Vector(tp, oom, rm), oom, rm);//3,8, -12
            //BigRational m = BigRational.valueOf(cp.getMagnitude(oom - 2));
            BigRational m = cp.getMagnitudeSquared(); //869
            BigRational dp = cp.getDotProduct(delta, oom, rm); //-184
            // m should only be zero if the lines are parallel.
            return (dp.pow(2).divide(m)).abs();
        }
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff this is parallel to the plane defined by x=0.
     */
    public boolean isParallelToX0(int oom, RoundingMode rm) {
        //return v.dx.isZero();
        //return pv.getX(oom).subtract(qv.getX(oom)).isZero();
        //return pv.getDX(oom, rm).subtract(qv.getDX(oom, rm)).isZero();
        if (v.dy.compareTo(Math_BigRationalSqrt.ZERO) == 0) {
            return true;
        }
        return v.dz.compareTo(Math_BigRationalSqrt.ZERO) == 0;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff this is parallel to the plane defined by y=0.
     */
    public boolean isParallelToY0(int oom, RoundingMode rm) {
        //return v.dy.isZero();
        //return pv.getY(oom).subtract(qv.getY(oom)).isZero();
        //return pv.getDY(oom, rm).subtract(qv.getDY(oom, rm)).isZero();
        if (v.dx.compareTo(Math_BigRationalSqrt.ZERO) == 0) {
            return true;
        }
        return v.dz.compareTo(Math_BigRationalSqrt.ZERO) == 0;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff this is parallel to the plane defined by z=0.
     */
    public boolean isParallelToZ0(int oom, RoundingMode rm) {
        //return v.dz.isZero();
        //return pv.getZ(oom).subtract(qv.getZ(oom)).isZero();
        //return pv.getDZ(oom, rm).subtract(qv.getDZ(oom, rm)).isZero();
        if (v.dx.compareTo(Math_BigRationalSqrt.ZERO) == 0) {
            return true;
        }
        return v.dy.compareTo(Math_BigRationalSqrt.ZERO) == 0;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The points that define the plan as a matrix.
     */
    public Math_Matrix_BR getAsMatrix(int oom, RoundingMode rm) {
        V3D_Point tp = getP();
        V3D_Point tq = getQ(oom, rm);
        BigRational[][] m = new BigRational[2][3];
        m[0][0] = tp.getX(oom, rm);
        m[0][1] = tp.getY(oom, rm);
        m[0][2] = tp.getZ(oom, rm);
        m[1][0] = tq.getX(oom, rm);
        m[1][1] = tq.getY(oom, rm);
        m[1][2] = tq.getZ(oom, rm);
        return new Math_Matrix_BR(m);
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
        if (p != null) {
            this.p.translate(v, oom, rm);
        }
        if (q != null) {
            this.q.translate(v, oom, rm);
        }
    }

    @Override
    public V3D_Line rotate(V3D_Ray ray, V3D_Vector uv, Math_Angle ma, 
            BigRational theta, int oom, RoundingMode rm) {
        V3D_Point rp = getP().rotate(ray, uv, ma, theta, oom, rm);
        V3D_Vector rv = v.rotate(uv, ma, theta, oom, rm);
        return new V3D_Line(rp, rv);
    }
    
    /**
     * @param ps The points to test for collinearity.
     * @return {@code true} iff all points are collinear with l.
     */
    public static boolean isCollinear(int oom, RoundingMode rm, V3D_Point... ps) {
        V3D_Line l = getLine(oom, rm, ps);
        if (l == null) {
            return false;
        }
        return isCollinear(oom, rm, l, ps);
    }

    /**
     * @param l The line to test points are collinear with.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @param ps The points to test if they are collinear with l.
     * @return {@code true} iff all points are collinear with l.
     */
    public static boolean isCollinear(int oom, RoundingMode rm, V3D_Line l, 
            V3D_Point... ps) {
        for (var p : ps) {
            if (!l.isIntersectedBy(p, oom, rm)) {
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
     * @param points Any number of points, but with two being different.
     * @return A line defined by any two different points or null if the points
     * are coincident.
     */
    public static V3D_Line getLine(int oom, RoundingMode rm, V3D_Point... points) {
        if (points.length < 2) {
            return null;
        }
        // Find the points which are furthest apart.
        BigRational max = BigRational.ZERO;
        V3D_Point a = null;
        V3D_Point b = null;
        for (int i = 0; i < points.length; i ++) {
            for (int j = i + 1; j < points.length; j ++) {
                BigRational d2 = points[i].getDistanceSquared(points[j], oom, rm);
                if (d2.compareTo(max) == 1) {
                    a = points[i];
                    b = points[j];
                    max = d2;
                }
            }
        }
        if (max.compareTo(BigRational.ZERO) == 0d) {
            return null;
        } else {
            return new V3D_Line(a, b, oom, rm);
        }
    }
}
