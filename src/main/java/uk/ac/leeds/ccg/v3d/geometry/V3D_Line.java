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

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * 3D representation of an infinite length line. The line passes through the
 * point {@link #p} and is travelling through point {@link #q}. The "*" denotes
 * a point in 3D and the line is shown with a line of "e" symbols in the
 * following depiction: {@code
 *                                       z                e
 *                          y           -                e
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
 *                  /       |      e
 *                 /        |     e
 *                +         |    e
 *               z          -   e
 *                          y
 * }
 * <ul>
 * <li>Vector Form
 * <ul>
 * <li>{@code (x,y,z) = (p.getX(oom),p.getY(oom),p.getZ(oom)) + t(v.getDX(oom),v.getDY(oom),v.getDZ(oom))}</li>
 * </ul>
 * <li>Parametric Form (where t describes a particular point on the line)
 * <ul>
 * <li>{@code x = p.getX(oom) + t(v.getDX(oom))}</li>
 * <li>{@code y = p.getY(oom) + t(v.getDY(oom))}</li>
 * <li>{@code z = p.getZ(oom) + t(v.getDZ(oom))}</li>
 * </ul>
 * <li>Symmetric Form (assume {@code v.getDX(oom)}, {@code v.getDY(oom)}, and
 * {@code v.getDZ(oom)} are all nonzero)
 * <ul>
 * <li>{@code (x−p.getX(oom))/v.getDX(oom) = (y−p.getY(oom))/v.getDY(oom) = (z−p.getZ(oom))/v.getDZ(oom)}</li>
 * </ul></li>
 * </ul>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Line extends V3D_Geometry
        implements V3D_IntersectionAndDistanceCalculations {

    private static final long serialVersionUID = 1L;

    /**
     * The x axis.
     */
    public static final V3D_Line X_AXIS = new V3D_Line(
            new V3D_Environment(), V3D_Vector.ZERO, V3D_Vector.I);

    /**
     * The y axis.
     */
    public static final V3D_Line Y_AXIS = new V3D_Line(
            new V3D_Environment(), V3D_Vector.ZERO, V3D_Vector.J);

    /**
     * The z axis.
     */
    public static final V3D_Line Z_AXIS = new V3D_Line(
            new V3D_Environment(), V3D_Vector.ZERO, V3D_Vector.K);
    
    /**
     * A point relative to {@link #offset} that defines the line.
     */
    protected V3D_Vector p;

    /**
     * A second point relative to {@link #offset} that may define the line.
     */
    protected V3D_Vector q;

    /**
     * For storing the vector that defines the line from {@link #p}.
     */
    protected V3D_Vector v;

    /**
     * @param e What {@link #e} is set to.
     */
    public V3D_Line(V3D_Environment e) {
        super(e);
    }
    
    /**
     * @param l Used to initialise this.
     */
    public V3D_Line(V3D_Line l) {
        super(l.e, l.offset);
        this.p = new V3D_Vector(l.p);
        this.q = new V3D_Vector(l.q);
    }

    /**
     * {@code p} should not be equal to {@code q}. {@link #offset} is set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param e What {@link #e} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     */
    public V3D_Line(V3D_Environment e, V3D_Vector p, V3D_Vector q) {
        this(e, V3D_Vector.ZERO, p, q);
    }

    /**
     * {@code p} should not be equal to {@code q}.
     *
     * @param e What {@link #e} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     */
    public V3D_Line(V3D_Environment e, V3D_Vector offset, V3D_Vector p,
            V3D_Vector q) {
        super(e, offset);
        this.p = new V3D_Vector(p);
        this.q = new V3D_Vector(q);
        if (p.equals(q)) {
            throw new RuntimeException("Points " + p + " and " + q
                    + " are the same and so do not define a line.");
        }
    }

    /**
     * If {@code p} equals {@code q} then a RuntimeException is thrown.
     * {@link #offset} is set to {@link V3D_Vector#ZERO}.
     *
     * @param e What {@link #e} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param b Ignored.
     * @throws RuntimeException if {@code p.equals(q)}.
     */
    public V3D_Line(V3D_Environment e, V3D_Vector p, V3D_Vector q, boolean b) {
        this(e, V3D_Vector.ZERO, p, q, b);
    }

    /**
     * If {@code p} equals {@code q} then a RuntimeException is thrown.
     *
     * @param e What {@link #e} is set to.
     * @param offset What {@link #offset} is cloned from.
     * @param p What {@link #p} is cloned from.
     * @param q What {@link #q} is cloned from.
     * @param b Ignored.
     * @throws RuntimeException if {@code p.equals(q)}.
     */
    public V3D_Line(V3D_Environment e, V3D_Vector offset, V3D_Vector p,
            V3D_Vector q, boolean b) {
        super(e, offset);
        if (p.equals(q)) {
            throw new RuntimeException("Points " + p + " and " + q
                    + " are the same and so do not define a line.");
        }
        this.p = new V3D_Vector(p);
        this.q = new V3D_Vector(q);
    }

    /**
     * {@code v} should not be the zero vector {@code <0,0,0>}. {@link #offset}
     * is set to {@link V3D_Vector#ZERO}.
     *
     * @param p What {@link #p} is cloned from.
     * @param v The vector defining the line from {@link #p}. What {@link #v} is
     * cloned from.
     * @param e What {@link #e} is set to.
     */
    public V3D_Line(V3D_Vector p, V3D_Vector v, V3D_Environment e) {
        this(V3D_Vector.ZERO, p, v, e);
    }

    /**
     * {@code v} should not be the zero vector {@code <0,0,0>}.
     *
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #p} is set to.
     * @param v The vector defining the line from {@link #p}. What {@link #v} is
     * set to.
     * @param e What {@link #e} is set to.
     */
    public V3D_Line(V3D_Vector offset, V3D_Vector p, V3D_Vector v, V3D_Environment e) {
        super(e, offset);
        this.p = new V3D_Vector(p);
        this.q = p.add(v, e.oom);
        this.v = new V3D_Vector(v);
    }

    /**
     * Checks to ensure v is not the zero vector {@code <0,0,0>}. Defaults
     * {@link #offset} to {@link V3D_Vector#ZERO}.
     *
     * @param p What {@link #p} is set to.
     * @param v The vector defining the line from {@link #p}. What {@link #v} is
     * set to.
     * @param e What {@link #e} is set to.
     * @param check Ignored.
     * @throws RuntimeException if {@code v.isZeroVector()}.
     */
    public V3D_Line(V3D_Vector p, V3D_Vector v, V3D_Environment e,
            boolean check) {
        this(V3D_Vector.ZERO, p, v, e, check);
    }

    /**
     * Checks to ensure v is not the zero vector {@code <0,0,0>}.
     *
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #p} is set to.
     * @param v The vector defining the line from {@link #p}.
     * @param e What {@link #e} is set to.
     * @param check Ignored.
     * @throws RuntimeException if {@code v.isZeroVector()}.
     */
    public V3D_Line(V3D_Vector offset, V3D_Vector p, V3D_Vector v,
            V3D_Environment e, boolean check) {
        super(e, offset);
        if (v.isZeroVector()) {
            throw new RuntimeException("Vector " + v + " is the zero vector "
                    + "and so cannot define a line.");
        }
        this.p = new V3D_Vector(p);
        this.q = p.add(v, e.oom);
        this.v = new V3D_Vector(v);
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #p} is cloned from.
     * @param q What {@link #q} is cloned from.
     */
    public V3D_Line(V3D_Point p, V3D_Point q) {
        super(p.e);
        V3D_Point q2 = new V3D_Point(q);
        q2.setOffset(p.offset);
        this.offset = new V3D_Vector(p.offset);
        this.p = new V3D_Vector(p.rel);
        this.q = q2.rel;
    }

    @Override
    public String toString() {
        return toString("");
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
     * @return A description of the fields.
     */
    @Override
    protected String toStringFields(String pad) {
        String r = super.toStringFields(pad) + "\n"
                + pad + ",\n";
        if (q == null) {
            r += pad + "p=" + getP().toString(pad) + "\n"
                    + pad + ",\n"
                    + pad + "q=null" + "\n"
                    + pad + ",\n"
                    + pad + "v=" + v.toString(pad);
        } else {
            if (v == null) {
                r += pad + "p=" + getP().toString(pad) + "\n"
                        + pad + ",\n"
                        + pad + "q=" + getQ().toString(pad) + "\n"
                        + pad + ",\n"
                        + pad + "v=null";
            } else {
                r += pad + "p=" + getP().toString(pad) + "\n"
                        + pad + ",\n"
                        + pad + "q=" + getQ().toString(pad) + "\n"
                        + pad + ",\n"
                        + pad + "v=" + v.toString(pad);
            }
        }
        return r;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Line l) {
            return equals(l);
        }
        return false;
    }

    /**
     * @param l The line to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_Line l) {
//        boolean t1 = isIntersectedBy(l.getP(oom), oom);
//        boolean t2 = isIntersectedBy(l.getQ(oom), oom);
//        boolean t3 = l.isIntersectedBy(getP(oom), oom);
//        boolean t4 = l.isIntersectedBy(getQ(oom),oom);
//        boolean t5 = getV(oom).isScalarMultiple(l.getV(oom), oom);
        return isIntersectedBy(l.getP(e.oom), e.oom) && isIntersectedBy(l.getQ(e.oom), e.oom);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.p);
        hash = 17 * hash + Objects.hashCode(this.q);
        return hash;
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Vector getPV(int oom) {
        return getP().add(offset, oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getP(int oom) {
        return new V3D_Point(e, offset, p);
    }

    /**
     * @return {@link #p}.
     */
    public V3D_Vector getP() {
//        if (pTemp == null) {
//            pTemp = p;
//        }
//        pTemp = rotate(pTemp, bI, theta);
//        return pTemp;
        //return rotate(p, theta);
        return p;
    }

    /**
     * @return {@link #q}.
     */
    public V3D_Vector getQ() {
//        if (qTemp == null) {
//            qTemp = q;
//        }
//        qTemp = rotate(qTemp, bI, theta);
//        return qTemp;        
        //return rotate(q, theta);
        return q;
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #q} with {@link #offset} applied.
     */
    public V3D_Vector getQV(int oom) {
        return getQ().add(offset, oom);
//        if (q == null) {
//            return p.add(v, oom).add(offset, oom);
//        } else {
//            return q.add(offset, oom);
//        }
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #q} with {@link #offset} applied.
     */
    public V3D_Point getQ(int oom) {
//        if (q == null) {
//            q = p.add(v, oom);
//        }
        return new V3D_Point(e, offset, getQ());
    }

    /**
     * @return The vector from {@link #p} to {@link #q}.
     */
    protected V3D_Vector getV() {
        if (v == null) {
            v = q.subtract(p, e.oom);
        }
        return v;
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The vector from {@link #p} to {@link #q}.
     */
    public V3D_Vector getV(int oom) {
        return getV().add(offset, oom);
//        return rotate(getV(), bI, theta).add(offset, oom);
    }

    /**
     * @param pt A point to test for intersection.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} if p is on the line.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom) {
        int oomN2 = oom - 2;
        V3D_Point tp = getP(oomN2);
        V3D_Point tq = getQ(oomN2);
        if (tp.equals(pt)) {
            return true;
        }
        if (tq.equals(pt)) {
            return true;
        }
        V3D_Vector cp;
        if (tp.equals(V3D_Point.ORIGIN)) {
            V3D_Vector ppt = new V3D_Vector(
                    pt.getX(oom).subtract(tq.getX(oomN2)),
                    pt.getY(oom).subtract(tq.getY(oomN2)),
                    pt.getZ(oom).subtract(tq.getZ(oomN2)));
            cp = getV(oomN2).getCrossProduct(ppt, oomN2);
        } else {
            V3D_Vector ppt = new V3D_Vector(
                    pt.getX(oomN2).subtract(tp.getX(oomN2)),
                    pt.getY(oomN2).subtract(tp.getY(oomN2)),
                    pt.getZ(oomN2).subtract(tp.getZ(oomN2)));
            cp = getV(oomN2).getCrossProduct(ppt, oomN2);
        }
        //V3D_Vector cp = ppt.getCrossProduct(v, oom);
        return cp.getDX(oom).isZero() && cp.getDY(oom).isZero() && cp.getDZ(oom).isZero();
    }

    /**
     * @param l The line to test if it is parallel to this.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} If this and {@code l} are parallel.
     */
    public boolean isParallel(V3D_Line l, int oom) {
        return getV(oom).isScalarMultiple(l.getV(oom), oom);
    }

    /**
     * @param l The line to test for intersection with this.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} if {@code this} and {@code l} intersect and false if
     * they may intersect, but more computation is needed.
     */
    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        V3D_Point tp = getP(oom);
        V3D_Point tq = getQ(oom);
        V3D_Point lp = l.getP(oom);
        if (isCollinear(e, tp, tq, lp)) {
            return true;
        } else {
            //V3D_Plane pl = new V3D_Plane(tp, tq, lp, oom);
            V3D_Plane pl = new V3D_Plane(e, V3D_Vector.ZERO, tp.getVector(oom),
                    tq.getVector(oom), lp.getVector(oom));
            if (V3D_Plane.isCoplanar(e, pl, l.getQ(oom))) {
                if (!isParallel(l, oom)) {
                    return true;
                }
            }
        }
        V3D_Point lq = l.getQ(oom);
        if (isCollinear(e, tp, tq, lq)) {
            return true;
        } else {
            //V3D_Plane pl = new V3D_Plane(tp, tq, lp, oom);
            V3D_Plane pl = new V3D_Plane(e, V3D_Vector.ZERO, tp.getVector(oom),
                    tq.getVector(oom), lp.getVector(oom));
            if (V3D_Plane.isCoplanar(e, pl, lq)) {
                if (!isParallel(l, oom)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@code p.getIntersection(this, oom)}
     *
     * @param p The plane to intersect.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code null} if there is no intersection and the geometry of
     * intersection otherwise.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Plane p, int oom) {
        return p.getIntersection(this, oom);
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}.
     *
     * @param l The line to get the intersection with {@code this}.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        // Special case of parallel lines.
        V3D_Point tp = getP(oom);
        if (isParallel(l, oom)) {
            if (tp.isIntersectedBy(l, oom)) {
                // If lines are coincident return this.
                return this;
            } else {
                return null;
            }
        }
        V3D_Point tq = getQ(oom);
        V3D_Point lp = l.getP(oom);
        V3D_Point lq = l.getQ(oom);
        V3D_Vector plp = new V3D_Vector(tp, lp, oom);
        V3D_Vector lqlp = new V3D_Vector(lq, lp, oom);
        if (lqlp.getMagnitudeSquared().isZero()) {
            if (isIntersectedBy(lp, oom)) {
                return lp;
            }
        }
        V3D_Vector qp = new V3D_Vector(tq, tp, oom);
        if (qp.getMagnitudeSquared().isZero()) {
            if (l.isIntersectedBy(tp, oom)) {
                return tp;
            }
        }
        Math_BigRational a = plp.dx.multiply(lqlp.dx, oom).getSqrt(oom)
                .add(plp.dy.multiply(lqlp.dy, oom).getSqrt(oom))
                .add(plp.dz.multiply(lqlp.dz, oom).getSqrt(oom));
        Math_BigRational b = lqlp.dx.multiply(qp.dx, oom).getSqrt(oom)
                .add(lqlp.dy.multiply(qp.dy, oom).getSqrt(oom))
                .add(lqlp.dz.multiply(qp.dz, oom).getSqrt(oom));
        Math_BigRational c = plp.dx.multiply(qp.dx, oom).getSqrt(oom)
                .add(plp.dy.multiply(qp.dy, oom).getSqrt(oom))
                .add(plp.dz.multiply(qp.dz, oom).getSqrt(oom));
        Math_BigRational d = lqlp.dx.multiply(lqlp.dx, oom).getSqrt(oom)
                .add(lqlp.dy.multiply(lqlp.dy, oom).getSqrt(oom))
                .add(lqlp.dz.multiply(lqlp.dz, oom).getSqrt(oom));
        Math_BigRational eb = qp.dx.multiply(qp.dx, oom).getSqrt(oom)
                .add(qp.dy.multiply(qp.dy, oom).getSqrt(oom))
                .add(qp.dz.multiply(qp.dz, oom).getSqrt(oom));
//        Math_BigRational a = (plp.dx.multiply(lqlp.dx)).add(plp.dy
//                .multiply(lqlp.dy)).add(plp.dz.multiply(lqlp.dz)).getSqrt(oom);
//        Math_BigRational b = (lqlp.dx.multiply(qp.dx)).add(lqlp.dy
//                .multiply(qp.dy)).add(lqlp.dz.multiply(qp.dz)).getSqrt(oom);
//        Math_BigRational c = (plp.dx.multiply(qp.dx)).add(plp.dy
//                .multiply(qp.dy)).add(plp.dz.multiply(qp.dz)).getSqrt(oom);
//        Math_BigRational d = (lqlp.dx.multiply(lqlp.dx)).add(lqlp.dy
//                .multiply(lqlp.dy)).add(lqlp.dz.multiply(lqlp.dz)).getSqrt(oom);
//        Math_BigRational e = (qp.dx.multiply(qp.dx)).add(qp.dy
//                .multiply(qp.dy)).add(qp.dz.multiply(qp.dz)).getSqrt(oom);
        Math_BigRational den = (eb.multiply(d)).subtract(b.multiply(b));
        Math_BigRational num = (a.multiply(b)).subtract(c.multiply(d));
        if (den.compareTo(Math_BigRational.ZERO) == 0) {
            if (num.compareTo(Math_BigRational.ZERO) == 0) {
                Math_BigRational x;
                Math_BigRational y;
                Math_BigRational z;
                Math_BigRational lamda;
                Math_BigRational mu;
                V3D_Vector tv = getV(oom);
                V3D_Vector lv = l.getV(oom);
                if (tv.dx.isZero()) {
                    x = tp.getX(oom);
                    if (lv.dx.isZero()) {
                        if (tv.dy.isZero()) {
                            y = tp.getY(oom);
                            if (lv.dy.isZero()) {
                                z = tp.getZ(oom);
                            } else {
                                if (tv.dz.isZero()) {
                                    z = tp.getZ(oom);
                                } else {
                                    if (lv.dz.isZero()) {
                                        z = lp.getZ(oom);
                                    } else {
                                        mu = (tp.getY(oom).subtract(lp.getY(oom))).divide(lv.getDY(oom));
                                        z = lp.getZ(oom).add(lv.getDZ(oom).multiply(mu));
                                    }
                                }
                            }
                        } else {
                            if (lv.dy.isZero()) {
                                y = lp.getY(oom);
                                if (tv.dz.isZero()) {
                                    z = tp.getZ(oom);
                                } else {
                                    if (lv.dz.isZero()) {
                                        z = lp.getZ(oom);
                                    } else {
                                        lamda = (lp.getY(oom).subtract(tp.getY(oom))).divide(tv.getDY(oom));
                                        z = tp.getZ(oom).add(tv.getDZ(oom).multiply(lamda));
                                    }
                                }
                                //x = p.getX(oom);            
                                //p.getX(oom) + v.getDX(oom) * lamda = l.p.getX(oom) + l.v.getDX(oom) * mu
                                //p.getY(oom) + v.getDY(oom) * lamda = l.p.getY(oom) + l.v.getDY(oom) * mu
                                //p.getZ(oom) + v.getDZ(oom) * lamda = l.p.getZ(oom) + l.v.getDZ(oom) * mu

                            } else {
                                if (tv.dz.isZero()) {
                                    z = tp.getZ(oom);
                                    mu = (tp.getZ(oom).subtract(lp.getZ(oom))).divide(lv.getDY(oom));
                                    y = lp.getY(oom).add(lv.getDY(oom).multiply(mu));
                                } else {
                                    if (lv.dz.isZero()) {
                                        z = lp.getZ(oom);
                                        lamda = (lp.getZ(oom).subtract(tp.getZ(oom))).divide(tv.getDY(oom));
                                        y = tp.getY(oom).add(tv.getDY(oom).multiply(lamda));
                                    } else {
                                        // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                        mu = ((p.getY(oom).add(v.getDY(oom).multiply(lamda))).subtract(l.p.getY(oom))).divide(l.v.getDY(oom));
//                                        lamda = ((l.p.getZ(oom).subtract(p.getZ(oom))).divide(v.getDZ(oom))).add(l.v.getDZ(oom).multiply(mu));
//                                        lamda = ((l.p.getZ(oom).subtract(p.getZ(oom))).divide(v.getDZ(oom))).add(l.v.getDZ(oom).multiply(((p.getY(oom).add(v.getDY(oom).multiply(lamda))).subtract(l.p.getY(oom))).divide(l.v.getDY(oom))));
//                                        l = ((bz-az)/adz) + (bdz*(ady*(l-by)/bdy))
//                                        l = ((bz-az)/adz) + bdz*ady*l/bdy - bdz*ady*by/bdy
//                                        l - bdz*ady*l/bdy = ((bz-az)/adz) - bdz*ady*by/bdy
//                                        l (1 - bdz*ady/bdy) = ((bz-az)/adz) - bdz*ady*by/bdy
//                                        l = (((bz-az)/adz) - bdz*ady*by/bdy)/(1 - bdz*ady/bdy)
                                        Math_BigRational den2 = Math_BigRational.ONE.subtract(lv.getDZ(oom).multiply(tv.getDY(oom).divide(lv.getDY(oom))));
                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                            lamda = (((lp.getZ(oom).subtract(tp.getZ(oom))).divide(tv.getDZ(oom))).subtract(lv.getDZ(oom).multiply(tv.getDY(oom).multiply(lp.getY(oom).divide(lv.getDY(oom)))))).divide(den2);
                                            z = tp.getZ(oom).add(tv.getDZ(oom).multiply(lamda));
                                            y = tp.getY(oom).add(tv.getDY(oom).multiply(lamda));
                                        } else {
                                            den2 = Math_BigRational.ONE.subtract(lv.getDY(oom).multiply(tv.getDZ(oom).divide(lv.getDZ(oom))));
                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                lamda = (((lp.getY(oom).subtract(tp.getY(oom))).divide(tv.getDY(oom))).subtract(lv.getDY(oom).multiply(tv.getDZ(oom).multiply(lp.getZ(oom).divide(lv.getDZ(oom)))))).divide(den2);
                                                z = tp.getZ(oom).add(tv.getDZ(oom).multiply(lamda));
                                                y = tp.getY(oom).add(tv.getDY(oom).multiply(lamda));
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
                        mu = (tp.getX(oom).subtract(lp.getX(oom))).divide(lv.getDX(oom));
                        if (tv.dy.isZero()) {
                            if (lv.dy.isZero()) {
                                y = tp.getY(oom);
                                z = tp.getZ(oom);
                            } else {
                                if (tv.dz.isZero()) {
                                    y = lp.getY(oom).add(lv.getDY(oom).multiply(mu));
                                } else {
                                    y = tp.getY(oom).add(tv.getDY(oom).multiply(mu));
                                }
                                if (lv.dz.isZero()) {
                                    z = tp.getZ(oom);
                                } else {
                                    z = lp.getZ(oom).add(lv.getDZ(oom).multiply(mu));
                                }
                            }
                        } else {
                            lamda = ((lp.getY(oom).add(lv.getDY(oom).multiply(mu)))
                                    .subtract(tp.getX(oom))).divide(tv.getDY(oom));
                            if (tv.dz.isZero()) {
                                z = tp.getZ(oom);
                            } else {
                                z = tp.getZ(oom).add(tv.getDZ(oom).multiply(lamda));
                            }
                            if (lv.dy.isZero()) {
                                y = tp.getY(oom);
                            } else {
                                y = lp.getY(oom).add(lv.getDY(oom).multiply(mu));
                            }
                        }
                    }
                } else {
                    if (lv.dx.isZero()) {
                        lamda = lp.getX(oom).subtract(tp.getX(oom)).divide(tv.getDX(oom));
                        x = lp.getX(oom);
                        if (tv.dy.isZero()) {
                            mu = (tp.getY(oom).subtract(lp.getY(oom))).divide(lv.getDY(oom));
                            y = tp.getY(oom);
                            if (tv.dz.isZero()) {
                                z = tp.getZ(oom);
                            } else {
                                if (lv.dz.isZero()) {
                                    z = lp.getZ(oom);
                                } else {
                                    z = lp.getZ(oom).add(lv.getDZ(oom).multiply(mu));
                                }
                            }
                        } else {
                            if (tv.dy.isZero()) {
                                y = tp.getY(oom);
                                if (lv.dy.isZero()) {
                                    if (tv.dz.isZero()) {
                                        z = tp.getZ(oom);
                                    } else {
                                        if (lv.dz.isZero()) {
                                            z = lp.getZ(oom);
                                        } else {
                                            mu = ((tp.getZ(oom).add(tv.getDZ(oom).multiply(lamda))).subtract(lp.getZ(oom))).divide(lv.getDZ(oom));
                                            z = lp.getZ(oom).add(lv.getDZ(oom).multiply(mu));
                                        }
                                    }
                                } else {
                                    if (tv.dz.isZero()) {
                                        z = tp.getZ(oom);
                                    } else {
                                        if (lv.dz.isZero()) {
                                            z = lp.getZ(oom);
                                        } else {
                                            mu = (tp.getZ(oom).subtract(lp.getZ(oom))).divide(lv.getDZ(oom));
                                            z = lp.getZ(oom).add(lv.getDZ(oom).multiply(mu));
                                        }
                                    }
                                }
                            } else {
                                if (lv.dy.isZero()) {
                                    y = lp.getY(oom);
                                    if (tv.dz.isZero()) {
                                        z = tp.getZ(oom);
                                    } else {
                                        if (lv.dz.isZero()) {
                                            z = lp.getZ(oom);
                                        } else {
                                            mu = ((tp.getZ(oom).add(tv.getDZ(oom).multiply(lamda))).subtract(lp.getZ(oom))).divide(lv.getDZ(oom));
                                            z = lp.getZ(oom).add(lv.getDZ(oom).multiply(mu));
                                        }
                                    }
                                } else {
                                    y = tp.getY(oom).add(tv.getDY(oom).multiply(lamda));
                                    if (tv.dz.isZero()) {
                                        z = tp.getZ(oom);
                                    } else {
                                        if (lv.dz.isZero()) {
                                            //z = l.p.getZ(oom);
                                            z = l.getP().getDZ(oom);
                                        } else {
                                            mu = ((tp.getZ(oom).add(tv.getDZ(oom).multiply(lamda))).subtract(lp.getZ(oom))).divide(lv.getDZ(oom));
                                            z = lp.getZ(oom).add(lv.getDZ(oom).multiply(mu));
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        // v.getDX(oom) > 0 && l.v.getDX(oom) > 0
                        if (tv.dy.isZero()) {
                            y = tp.getY(oom);
                            if (lv.dy.isZero()) {
                                if (tv.dz.isZero()) {
                                    z = tp.getZ(oom);
                                    x = tp.getX(oom);
                                } else {
                                    if (lv.dz.isZero()) {
                                        z = lp.getZ(oom);
                                        lamda = (lp.getZ(oom).subtract(tp.getZ(oom))).divide(tv.getDZ(oom));
                                        x = tp.getX(oom).add(tv.getDX(oom).multiply(lamda));
                                    } else {
                                        // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                        mu = ((p.getX(oom).add(v.getDX(oom).multiply(lamda))).subtract(l.p.getX(oom))).divide(l.v.getDX(oom));
//                                        lamda = ((l.p.getZ(oom).subtract(p.getZ(oom))).divide(v.getDZ(oom))).add(l.v.getDZ(oom).multiply(mu));
//                                        lamda = ((l.p.getZ(oom).subtract(p.getZ(oom))).divide(v.getDZ(oom))).add(l.v.getDZ(oom).multiply(((p.getX(oom).add(v.getDX(oom).multiply(lamda))).subtract(l.p.getX(oom))).divide(l.v.getDX(oom))));
//                                        l = ((bz-az)/adz) + (bdz*(adx*(l-bx)/bdx))
//                                        l = ((bz-az)/adz) + bdz*adx*l/bdx - bdz*adx*bx/bdx
//                                        l - bdz*adx*l/bdx = ((bz-az)/adz) - bdz*adx*bx/bdx
//                                        l (1 - bdz*adx/bdx) = ((bz-az)/adz) - bdz*adx*bx/bdx
//                                        l = (((bz-az)/adz) - bdz*adx*bx/bdx)/(1 - bdz*adx/bdx)
                                        Math_BigRational den2 = Math_BigRational.ONE.subtract(lv.getDZ(oom).multiply(tv.getDX(oom).divide(lv.getDX(oom))));
                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                            lamda = (((lp.getZ(oom).subtract(tp.getZ(oom))).divide(tv.getDZ(oom))).subtract(lv.getDZ(oom).multiply(tv.getDX(oom).multiply(lp.getX(oom).divide(lv.getDX(oom)))))).divide(den2);
                                            z = tp.getZ(oom).add(tv.getDZ(oom).multiply(lamda));
                                            x = tp.getX(oom).add(tv.getDX(oom).multiply(lamda));
                                        } else {
                                            den2 = Math_BigRational.ONE.subtract(lv.getDX(oom).multiply(tv.getDZ(oom).divide(lv.getDZ(oom))));
                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                lamda = (((lp.getX(oom).subtract(tp.getX(oom))).divide(tv.getDX(oom))).subtract(lv.getDX(oom).multiply(tv.getDZ(oom).multiply(lp.getZ(oom).divide(lv.getDZ(oom)))))).divide(den2);
                                                z = tp.getZ(oom).add(tv.getDZ(oom).multiply(lamda));
                                                x = tp.getX(oom).add(tv.getDX(oom).multiply(lamda));
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
                                mu = tp.getY(oom).subtract(lp.getY(oom)).divide(l.getV(oom).getDY(oom));
                                x = lp.getX(oom).add(lv.getDX(oom).multiply(mu));
                                z = lp.getZ(oom).add(lv.getDZ(oom).multiply(mu));
                            }
                        } else {
                            // v.getDX(oom) > 0 && l.v.getDX(oom) > 0 && v.getDY(oom) > 0
                            if (tv.dz.isZero()) {
                                z = tp.getZ(oom);
                                if (lv.dz.isZero()) {
                                    // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                    mu = ((p.getX(oom).add(v.getDX(oom).multiply(lamda))).subtract(l.p.getX(oom))).divide(l.v.getDX(oom));
//                                    lamda = ((l.p.getY(oom).subtract(p.getY(oom))).divide(v.getDY(oom))).add(l.v.getDY(oom).multiply(mu));
//                                    lamda = ((l.p.getY(oom).subtract(p.getY(oom))).divide(v.getDY(oom))).add(l.v.getDY(oom).multiply(((p.getX(oom).add(v.getDX(oom).multiply(lamda))).subtract(l.p.getX(oom))).divide(l.v.getDX(oom))));
//                                    l = ((by - ay) / ady) + (bdy * (adx * (l - bx) / bdx))
//                                    l = ((by - ay) / ady) + bdy * adx * l / bdx - bdy * adx * bx / bdx
//                                    l - bdy * adx * l / bdx = ((by - ay) / ady) - bdy * adx * bx / bdx
//                                    l(1 - bdy * adx / bdx) = ((by - ay) / ady) - bdy * adx * bx / bdx
//                                    l = (((by-ay)/ady) - bdy*adx*bx/bdx)/(1 - bdy*adx/bdx)
                                    Math_BigRational den2 = Math_BigRational.ONE.subtract(lv.getDY(oom).multiply(tv.getDX(oom).divide(lv.getDX(oom))));
                                    if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                        lamda = (((lp.getY(oom).subtract(tp.getY(oom))).divide(tv.getDY(oom))).subtract(lv.getDY(oom).multiply(tv.getDX(oom).multiply(lp.getX(oom).divide(lv.getDX(oom)))))).divide(den2);
                                        y = tp.getY(oom).add(tv.getDY(oom).multiply(lamda));
                                        x = tp.getX(oom).add(tv.getDX(oom).multiply(lamda));
                                    } else {
                                        den2 = Math_BigRational.ONE.subtract(lv.getDX(oom).multiply(tv.getDY(oom).divide(lv.getDY(oom))));
                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                            lamda = (((lp.getX(oom).subtract(tp.getX(oom))).divide(tv.getDX(oom))).subtract(lv.getDX(oom).multiply(tv.getDY(oom).multiply(lp.getY(oom).divide(lv.getDY(oom)))))).divide(den2);
                                            y = tp.getY(oom).add(tv.getDY(oom).multiply(lamda));
                                            x = tp.getX(oom).add(tv.getDX(oom).multiply(lamda));
                                        } else {
                                            // This should not happen!
                                            y = null;
                                            x = null;
                                        }
                                    }
                                } else {
                                    mu = (tp.getZ(oom).subtract(lp.getZ(oom))).divide(lv.getDZ(oom));
                                    y = lp.getY(oom).add(lv.getDY(oom).multiply(mu));
                                    x = lp.getX(oom).add(lv.getDX(oom).multiply(mu));
                                }
                            } else {
                                if (lv.dz.isZero()) {
                                    z = lp.getZ(oom);
                                    lamda = (lp.getZ(oom).subtract(tp.getZ(oom))).divide(tv.getDZ(oom));
                                    y = tp.getY(oom).add(tv.getDY(oom).multiply(lamda));
                                    x = tp.getX(oom).add(tv.getDX(oom).multiply(lamda));
                                } else {
                                    // There are 6 ways to calculate lamda. One way should work! - If not try calculating mu.
                                    Math_BigRational den2 = Math_BigRational.ONE.subtract(lv.getDY(oom).multiply(tv.getDX(oom).divide(lv.getDX(oom))));
                                    if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                        lamda = (((lp.getY(oom).subtract(tp.getY(oom))).divide(tv.getDY(oom))).subtract(lv.getDY(oom).multiply(tv.getDX(oom).multiply(lp.getX(oom).divide(lv.getDX(oom)))))).divide(den2);
                                        x = tp.getX(oom).add(tv.getDX(oom).multiply(lamda));
                                        y = tp.getY(oom).add(tv.getDY(oom).multiply(lamda));
                                        z = tp.getZ(oom).add(tv.getDZ(oom).multiply(lamda));
//                                        x = q.getX(oom).add(v.getDX(oom).multiply(lamda));
//                                        y = q.getY(oom).add(v.getDY(oom).multiply(lamda));
//                                        z = q.getZ(oom).add(v.getDZ(oom).multiply(lamda));
                                    } else {
                                        den2 = Math_BigRational.ONE.subtract(lv.getDY(oom).multiply(tv.getDZ(oom).divide(lv.getDZ(oom))));
                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                            lamda = (((lp.getY(oom).subtract(tp.getY(oom))).divide(tv.getDY(oom))).subtract(lv.getDY(oom).multiply(tv.getDZ(oom).multiply(lp.getZ(oom).divide(lv.getDZ(oom)))))).divide(den2);
                                            x = tp.getX(oom).add(tv.getDX(oom).multiply(lamda));
                                            y = tp.getY(oom).add(tv.getDY(oom).multiply(lamda));
                                            z = tp.getZ(oom).add(tv.getDZ(oom).multiply(lamda));
                                        } else {
                                            den2 = Math_BigRational.ONE.subtract(lv.getDZ(oom).multiply(tv.getDX(oom).divide(lv.getDX(oom))));
                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                lamda = (((lp.getZ(oom).subtract(tp.getZ(oom))).divide(tv.getDZ(oom))).subtract(lv.getDZ(oom).multiply(tv.getDX(oom).multiply(lp.getX(oom).divide(lv.getDX(oom)))))).divide(den2);
                                                x = tp.getX(oom).add(tv.getDX(oom).multiply(lamda));
                                                y = tp.getY(oom).add(tv.getDY(oom).multiply(lamda));
                                                z = tp.getZ(oom).add(tv.getDZ(oom).multiply(lamda));
                                            } else {
                                                den2 = Math_BigRational.ONE.subtract(lv.getDZ(oom).multiply(tv.getDY(oom).divide(lv.getDY(oom))));
                                                if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                    lamda = (((lp.getZ(oom).subtract(tp.getZ(oom))).divide(tv.getDZ(oom))).subtract(lv.getDZ(oom).multiply(tv.getDY(oom).multiply(lp.getY(oom).divide(lv.getDY(oom)))))).divide(den2);
                                                    x = tp.getX(oom).add(tv.getDX(oom).multiply(lamda));
                                                    y = tp.getY(oom).add(tv.getDY(oom).multiply(lamda));
                                                    z = tp.getZ(oom).add(tv.getDZ(oom).multiply(lamda));
                                                } else {
                                                    den2 = Math_BigRational.ONE.subtract(lv.getDX(oom).multiply(tv.getDX(oom).divide(lv.getDY(oom))));
                                                    if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                        lamda = (((lp.getX(oom).subtract(tp.getX(oom))).divide(tv.getDX(oom))).subtract(lv.getDX(oom).multiply(tv.getDY(oom).multiply(lp.getY(oom).divide(lv.getDY(oom)))))).divide(den2);
                                                        x = tp.getX(oom).add(tv.getDX(oom).multiply(lamda));
                                                        y = tp.getY(oom).add(tv.getDY(oom).multiply(lamda));
                                                        z = tp.getZ(oom).add(tv.getDZ(oom).multiply(lamda));
                                                    } else {
                                                        den2 = Math_BigRational.ONE.subtract(lv.getDX(oom).multiply(tv.getDX(oom).divide(lv.getDZ(oom))));
                                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                            lamda = (((lp.getX(oom).subtract(tp.getX(oom))).divide(tv.getDX(oom))).subtract(lv.getDX(oom).multiply(tv.getDZ(oom).multiply(lp.getZ(oom).divide(lv.getDZ(oom)))))).divide(den2);
                                                            x = tp.getX(oom).add(tv.getDX(oom).multiply(lamda));
                                                            y = tp.getY(oom).add(tv.getDY(oom).multiply(lamda));
                                                            z = tp.getZ(oom).add(tv.getDZ(oom).multiply(lamda));
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
                //p.getX(oom) + v.getDX(oom) * lamda = l.p.getX(oom) + l.v.getDX(oom) * mu
                //p.getY(oom) + v.getDY(oom) * lamda = l.p.getY(oom) + l.v.getDY(oom) * mu
                //p.getZ(oom) + v.getDZ(oom) * lamda = l.p.getZ(oom) + l.v.getDZ(oom) * mu
                return new V3D_Point(this.e, x, y, z);
            }
            return null;
        }
        Math_BigRational mua = num.divide(den);
        Math_BigRational mub = (a.add(b.multiply(mua))).divide(d).negate();
        V3D_Point pi = new V3D_Point(this.e,
                //                (p.getX(oom).add(mua.multiply(qp.getDX(oom)))),
                //                (p.getY(oom).add(mua.multiply(qp.getDY(oom)))),
                //                (p.getZ(oom).add(mua.multiply(qp.getDZ(oom)))));
                (tp.getX(oom).subtract(mua.multiply(qp.getDX(oom)))),
                (tp.getY(oom).subtract(mua.multiply(qp.getDY(oom)))),
                (tp.getZ(oom).subtract(mua.multiply(qp.getDZ(oom)))));
        // If point p is on both lines then return this as the intersection.
        if (isIntersectedBy(pi, oom) && l.isIntersectedBy(pi, oom)) {
            return pi;
        }
        V3D_Point qi = new V3D_Point(this.e,
                (lp.getX(oom).add(mub.multiply(lqlp.getDX(oom)))),
                (lp.getY(oom).add(mub.multiply(lqlp.getDY(oom)))),
                (lp.getZ(oom).add(mub.multiply(lqlp.getDZ(oom)))));
        // If point q is on both lines then return this as the intersection.
        if (isIntersectedBy(qi, oom) && l.isIntersectedBy(qi, oom)) {
            return qi;
        }
        /**
         * The only time when pi and qi should be different is when the lines do
         * not intersect. In this case pi and qi are meant to be the end points
         * of the shortest line between the two lines.
         */
        if (pi.equals(qi)) {
            return pi;
        } else {
            return null;
        }
        //return new V3D_Line(pi, qi);
    }

    /**
     * @param r The ray to test if it intersects with {@code this}.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} If {@code this} and {@code r} intersect.
     */
    @Override
    public boolean isIntersectedBy(V3D_Ray r, int oom) {
        return r.isIntersectedBy(this, oom);
    }

    /**
     * Intersects {@code this} with {@code r}.
     *
     * @param r The ray to get intersection with {@code this}.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code r}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Ray r, int oom) {
        return r.getIntersection(this, oom);
    }

    /**
     * @param pt A point for which the shortest line segment to this is
     * returned.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The line segment having the shortest distance between {@code pt}
     * and {@code this}.
     */
    public V3D_Geometry getLineOfIntersection(V3D_Point pt, int oom) {
        if (isIntersectedBy(pt, oom)) {
            return pt;
        }
        return new V3D_LineSegment(pt, getPointOfIntersection(pt, oom));
        //return new V3D_LineSegment(pt.getVector(oom), getPointOfIntersection(pt, oom).getVector(oom), oom);
    }

    /**
     * Adapted from:
     * <a href="https://math.stackexchange.com/questions/1521128/given-a-line-and-a-point-in-3d-how-to-find-the-closest-point-on-the-line">https://math.stackexchange.com/questions/1521128/given-a-line-and-a-point-in-3d-how-to-find-the-closest-point-on-the-line</a>
     *
     * @param pt The point projected onto this.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return A point on {@code this} which is the shortest distance from
     * {@code pt}.
     */
    public V3D_Point getPointOfIntersection(V3D_Point pt, int oom) {
        if (isIntersectedBy(pt, oom)) {
            return pt;
        }
        // a = v
        // p1 = p
        // p0 = pt
        //t = (- a.x * (p1.x - p0.x) - a.y * (p1.y - p0.y) - a.z * (p1.z - p0.z)) 
        //     / (a.x * a.x + a.y * a.y + a.z * a.z)
        V3D_Vector tv = getV(oom);
        V3D_Point tp = getP(oom);
        //V3D_Point tq = getQ(oom);
        Math_BigRational vdx = tv.dx.getSqrt(oom);
        Math_BigRational t = (vdx.negate().multiply((tp.getX(oom).subtract(pt.getX(oom))))
                .subtract(tv.dy.getSqrt(oom).multiply((tp.getY(oom).subtract(pt.getY(oom)))))
                .subtract(tv.dz.getSqrt(oom).multiply(tp.getZ(oom).subtract(pt.getZ(oom)))))
                .divide(tv.dx.getX().add(tv.dy.getX()).add(tv.dz.getX()));
//        Math_BigRational t2 = (vdx.negate().multiply((q.getX(oom).subtract(pt.getX(oom))))
//                .subtract(v.dy.getSqrt(oom).multiply((q.getY(oom).subtract(pt.getY(oom))))) 
//                .subtract(v.dz.getSqrt(oom).multiply(q.getZ(oom).subtract(pt.getZ(oom))))) 
//                .divide(v.dx.getX().add(v.dy.getX()).add(v.dz.getX()));
        //return new V3D_Point(p.getVector(oom).add(v.multiply(t, oom), oom), oom);
        //return new V3D_Point(p.rel, p.offset.add(v.multiply(t, oom), oom));
        //return tp.apply(tv.multiply(t, oom), oom);
        return new V3D_Point(e, tp.getVector(oom).add(tv.multiply(t, oom), oom));

//        // P = pt
//        // Q = p
//        // R = q
//        // R-Q = v
//        // Q-P = new V3D_Vector(pt, p, oom)
//        if (p.isOrigin()) {
//            V3D_Vector vr = v.reverse();
//            Math_BigRational num = vr.getDotProduct(new V3D_Vector(pt, q, oom), oom);
//            Math_BigRational den = vr.getDotProduct(vr, oom);
//            return q.apply(vr.multiply(num.divide(den), oom), oom);
//        } else {
//            Math_BigRational num = v.getDotProduct(new V3D_Vector(pt, p, oom), oom);
//            Math_BigRational den = v.getDotProduct(v, oom);
//            return p.apply(v.multiply(num.divide(den), oom), oom);
//        }
//        V3D_Vector a = new V3D_Vector(pt, p, oom);
//        //V3D_Vector a = new V3D_Vector(p, pt);
//        Math_BigRational adb = a.getDotProduct(v, oom);
//        Math_BigRational vdv = v.getDotProduct(v, oom);
//        return p.apply(v.multiply(adb.divide(vdv), oom).reverse(), oom);
//        //return q.apply(v.multiply(adb.divide(vdv)));
//        //return p.apply(v.multiply(adb.divide(vdv)));
    }

    /**
     * Get the line of intersection (the shortest line) between two lines. The
     * two lines must not intersect or be parallel for this to work. Part
     * adapted from
     * <a href="http://paulbourke.net/geometry/pointlineplane/">http://paulbourke.net/geometry/pointlineplane/</a>.
     * The re
     *
     * @param l The line to get the line of intersection with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The line of intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry getLineOfIntersection(V3D_Line l, int oom) {
        if (isParallel(l, oom)) {
            return null;
        }
        V3D_Point tp = getP(oom);
        V3D_Point lp = l.getP(oom);
        V3D_Vector A = new V3D_Vector(tp, lp, oom);
        V3D_Vector B = getV(oom).reverse();
        V3D_Vector C = l.getV(oom).reverse();

        Math_BigRational AdB = A.getDotProduct(B, oom);
        Math_BigRational AdC = A.getDotProduct(C, oom);
        Math_BigRational CdB = C.getDotProduct(B, oom);
        Math_BigRational BdB = B.getDotProduct(B, oom);
        Math_BigRational CdC = C.getDotProduct(C, oom);

        Math_BigRational ma = (AdC.multiply(CdB)).subtract(AdB.multiply(CdC))
                .divide((BdB.multiply(CdC)).subtract(CdB.multiply(CdB)));
        Math_BigRational mb = ((ma.multiply(CdB)).add(AdC)).divide(CdC);

        //V3D_Point tpi = tp.apply(B.multiply(ma, oom), oom);
        //V3D_Vector tpi = tp.getVector(oom).add(B.multiply(ma, oom), oom);
        V3D_Vector tpi = tp.getVector(oom).subtract(B.multiply(ma, oom), oom);

        //V3D_Point lpi = l.p.apply(C.multiply(mb, oom), oom);
        //V3D_Point lpi = lp.apply(C.multiply(mb.negate(), oom), oom);
        //V3D_Vector lpi = lp.getVector(oom).add(C.multiply(mb.negate(), oom), oom);
        V3D_Vector lpi = lp.getVector(oom).subtract(C.multiply(mb, oom), oom);

        //return new V3D_LineSegment(tpi, lpi, oom);
        //return new V3D_LineSegment(tpi.getVector(oom), lpi.getVector(oom), oom);
        //return new V3D_LineSegment(e, tpi, lpi);
        return V3D_LineSegment.getGeometry(new V3D_Point(e, tpi), new V3D_Point(e, lpi));

//        // p13
//        //V3D_Vector plp = new V3D_Vector(p, lp, oom);
//        V3D_Vector plp = new V3D_Vector(tp, lp, oom);
//        // p43
//        //V3D_Vector lqlp = l.v.reverse();//new V3D_Vector(l.q, l.p);
//        V3D_Vector lqlp = l.v;//new V3D_Vector(l.q, l.p);
//        if (lqlp.getMagnitudeSquared().compareTo(Math_BigRational.ZERO) == 0) {
//            return null;
//        }
//        // p21
//        //V3D_Vector qp = v.reverse();//new V3D_Vector(q, p);
//        V3D_Vector qp = v;//new V3D_Vector(q, p);
//        if (qp.getMagnitudeSquared().compareTo(Math_BigRational.ZERO) == 0) {
//            return null;
//        }
//        // p1343
//        Math_BigRational a = (plp.getDX(oom).multiply(lqlp.getDX(oom))).add(plp.getDY(oom)
//                .multiply(lqlp.getDY(oom))).add(plp.getDZ(oom).multiply(lqlp.getDZ(oom)));
//        Math_BigRational b = (lqlp.getDX(oom).multiply(qp.getDX(oom))).add(lqlp.getDY(oom)
//                .multiply(qp.getDY(oom))).add(lqlp.getDZ(oom).multiply(qp.getDZ(oom)));
//        Math_BigRational c = (plp.getDX(oom).multiply(qp.getDX(oom))).add(plp.getDY(oom)
//                .multiply(qp.getDY(oom))).add(plp.getDZ(oom).multiply(qp.getDZ(oom)));
//        Math_BigRational d = (lqlp.getDX(oom).multiply(lqlp.getDX(oom))).add(lqlp.getDY(oom)
//                .multiply(lqlp.getDY(oom))).add(lqlp.getDZ(oom).multiply(lqlp.getDZ(oom)));
//        Math_BigRational e = (qp.getDX(oom).multiply(qp.getDX(oom))).add(qp.getDY(oom)
//                .multiply(qp.getDY(oom))).add(qp.getDZ(oom).multiply(qp.getDZ(oom)));
//        Math_BigRational den = (e.multiply(d)).subtract(b.multiply(b));
//        if (den.compareTo(Math_BigRational.ZERO) == 0) {
//            return null;
//        }
//        Math_BigRational num = (a.multiply(b)).subtract(c.multiply(d));
//        // dmnop = (xm - xn)(xo - xp) + (ym - yn)(yo - yp) + (zm - zn)(zo - zp)
//        // mua = ( d1343 d4321 - d1321 d4343 ) / ( d2121 d4343 - d4321 d4321 )
//        Math_BigRational mua = num.divide(den);
//        // mub = ( d1343 + mua d4321 ) / d4343
//        Math_BigRational mub = (a.add(b.multiply(mua))).divide(d);
//        V3D_Point pi = new V3D_Point(
//                (p.getX(oom).add(mua.multiply(qp.getDX(oom)))),
//                (p.getY(oom).add(mua.multiply(qp.getDY(oom)))),
//                (p.getZ(oom).add(mua.multiply(qp.getDZ(oom)))));
//        V3D_Point qi = new V3D_Point(
//                (l.p.getX(oom).add(mub.multiply(lqlp.getDX(oom)))),
//                (l.p.getY(oom).add(mub.multiply(lqlp.getDY(oom)))),
//                (l.p.getZ(oom).add(mub.multiply(lqlp.getDZ(oom)))));
//        if (pi.equals(qi)) {
//            return pi;
//        }
//        return new V3D_LineSegment(pi, qi, oom);
    }

//    /**
//     * @param v The vector to apply.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return a new line.
//     */
//    @Override
//    public V3D_Line apply(V3D_Vector v, int oom) {
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
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The minimum distance between this and {@code p}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point pt, int oom) {
        if (isIntersectedBy(pt, oom)) {
            return BigDecimal.ZERO;
        }
        Math_BigRational res = new Math_BigRationalSqrt(
                getDistanceSquared(pt, true, oom), oom).getSqrt(oom);
        int precision = Math_BigDecimal.getOrderOfMagnitudeOfMostSignificantDigit(
                res.integerPart().toBigDecimal(oom)) - oom;
        MathContext mc = new MathContext(precision);
        return Math_BigDecimal.round(res.toBigDecimal(mc), oom);
//        /**
//         * Calculate the direction vector of the line connecting the closest
//         * points by computing the cross product.
//         */
//        V3D_Vector pv = new V3D_Vector(this.p, p, oom);
//        V3D_Vector cp = pv.getCrossProduct(v);
//        
//        Math_BigRational pvm2 = pv.getMagnitudeSquared();
//        Math_BigRational cpm2 = cp.getMagnitudeSquared();
//        if (pvm2.compareTo(Math_BigRational.ZERO) == 0) {
//            if (cpm2.compareTo(Math_BigRational.ZERO) == 0) {
//                return BigDecimal.ONE;
//            }
//        }
//        Math_BigRationalSqrt pvm = new Math_BigRationalSqrt(pvm2, oom);
//        Math_BigRationalSqrt cpm = new Math_BigRationalSqrt(cpm2, oom);
//        BigDecimal result = cpm.divide(pvm).toBigDecimal(oom);
////        return cpm.divide(pvm).toBigDecimal(oom);
////
////        return getDistance(p).toBigDecimal(oom);
////        V3D_Vector pv = new V3D_Vector(this.p, p, v.oom);
//        V3D_Vector vu = v.getUnitVector(oom - 2);
//        return p.getDistance(new V3D_Point(vu.multiply(pv.getDotProduct(vu))
//                .add(new V3D_Vector(this.p, v.oom))), oom);
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
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The minimum distance between this and {@code p}.
     */
    @Override
    public Math_BigRational getDistanceSquared(V3D_Point pt, int oom) {
        if (isIntersectedBy(pt, oom)) {
            return Math_BigRational.ZERO;
        } else {
            return getDistanceSquared(pt, true, oom);
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
     * {@link #getDistanceSquared(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int)}.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The minimum distance between this and {@code p}.
     */
    protected Math_BigRational getDistanceSquared(V3D_Point pt, boolean noInt,
            int oom) {
//        return pt.getDistanceSquared(this, oom);
//        if (isIntersectedBy(pt, oom)) {
//            return Math_BigRational.ZERO;
//        }
        V3D_Vector pp = new V3D_Vector(pt, getP(oom), oom);
        V3D_Vector qp = new V3D_Vector(pt, getQ(oom), oom);
        Math_BigRational num = (pp.getCrossProduct(qp, oom)).getMagnitudeSquared();
        Math_BigRational den = getV(oom).getMagnitudeSquared();
        return num.divide(den);
    }

//    /**
//     * https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
//     *
//     * @param p A point for which the minimum distance from {@code this} is
//     * returned.
//     * @return The minimum distance between this and {@code p}.
//     */
//    public Math_BigRationalSqrt getDistance(V3D_Point p) {
//        /**
//         * Calculate the direction vector of the line connecting the closest
//         * points by computing the cross product.
//         */
//        V3D_Vector pv = new V3D_Vector(this.p, p);
//        V3D_Vector cp = pv.getCrossProduct(v);
////        Math_BigRational pvm2 = pv.getMagnitudeSquared();
////        Math_BigRational cpm2 = cp.getMagnitudeSquared();
////        if (pvm2.compareTo(Math_BigRational.ZERO) == 0) {
////            if (cpm2.compareTo(Math_BigRational.ZERO) == 0) {
////                return BigDecimal.ONE;
////            }
////        }
////        Math_BigRationalSqrt pvm = new Math_BigRationalSqrt(pvm2);
////        Math_BigRationalSqrt cpm = new Math_BigRationalSqrt(cpm2);
////        return cpm.divide(pvm).toBigDecimal(oom);
//        /**
//         * Calculate the delta from {@link #p} and l.p
//         */
//        V3D_Vector delta = new V3D_Vector(this.p).subtract(pv);
//        Math_BigRationalSqrt dp = new Math_BigRationalSqrt(cp.getDotProduct(delta));
//        if (dp.compareTo(cp.m) == 0) {
//            return Math_BigRationalSqrt.ONE; // Prevent a divide by zero if both are zero.
//        }
//        return dp.divide(cp.m);
//    }
    /**
     * https://en.wikipedia.org/wiki/Skew_lines#Nearest_points
     * https://en.wikipedia.org/wiki/Distance_between_two_parallel_lines
     *
     * @param l A line for which the minimum distance from {@code this} is
     * returned.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The minimum distance between this and {@code l}.
     */
    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
//        V3D_Point tp = getP(oom);
//        if (isParallel(l, oom)) {
//            //q.getDistance(l, scale, rm);
//            return tp.getDistance(l, oom);
//            //return p.getDistance(l, oom);
////            V3D_Vector v2 = new V3D_Vector(l.p, q);
////            return v2.subtract(v.multiply((v2.getDotProduct(v))
////                    .divide(v.getMagnitudeSquared()))).getMagnitude(scale, rm);
//        } else {
//            /**
//             * Calculate the direction vector of the line connecting the closest
//             * points by computing the cross product.
//             */
//            //V3D_Vector cp = l.v.getCrossProduct(v, oom);
//            V3D_Vector cp = getV(oom).getCrossProduct(l.getV(oom), oom);
//            /**
//             * Calculate the delta from {@link #p} and l.p
//             */
////            V3D_Vector delta = new V3D_Vector(l.p, oom).subtract(
////                    new V3D_Vector(tp, oom), oom);
//            V3D_Vector delta = l.getPV(oom).subtract(
//                    new V3D_Vector(tp, oom), oom);
//            //Math_BigRational m = Math_BigRational.valueOf(cp.getMagnitude(oom - 2));
//            Math_BigRationalSqrt m = cp.getMagnitude();
//            // d = cp.(delta)/m
//            Math_BigRational dp = cp.getDotProduct(delta, oom);
//            // m should only be zero if the lines are parallel.
//            //Math_BigRational d = dp.divide(m.getX());
//            Math_BigRational d = dp.divide(m.getSqrt(oom - 6)).abs();
//            return d.toBigDecimal(oom);
//        }
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom) {
        V3D_Point tp = getP(oom);
        if (isParallel(l, oom)) {
            return tp.getDistanceSquared(l, oom);
        } else {
            /**
             * Calculate the direction vector of the line connecting the closest
             * points by computing the cross product.
             */
            //V3D_Vector cp = l.v.getCrossProduct(v, oom);
            V3D_Vector cp = getV(oom).getCrossProduct(l.getV(oom), oom);
            /**
             * Calculate the delta from {@link #p} and l.p
             */
//            V3D_Vector delta = new V3D_Vector(l.p, oom).subtract(
//                    new V3D_Vector(tp, oom), oom);
            V3D_Vector delta = l.getPV(oom).subtract(
                    new V3D_Vector(tp, oom), oom);
            //Math_BigRational m = Math_BigRational.valueOf(cp.getMagnitude(oom - 2));
            Math_BigRationalSqrt m = cp.getMagnitude();
            Math_BigRational dp = cp.getDotProduct(delta, oom);
            // m should only be zero if the lines are parallel.
            return (dp.pow(2).divide(m.getX())).abs();
        }
    }

    /**
     * @param r A ray for which the minimum distance from {@code this} is
     * returned.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The minimum distance between {@code this} and {@code r}.
     */
    @Override
    public BigDecimal getDistance(V3D_Ray r, int oom) {
        return r.getDistance(this, oom);
//        V3D_Geometry g = r.getLineOfIntersection(this, oom);
//        if (g == null) {
//            return getDistance(r.getP(oom), oom);
//        }
//        if (g instanceof V3D_Point) {
//            return BigDecimal.ZERO;
//        } else {
//            return ((V3D_LineSegment) g).getLength(oom).getSqrt(oom).toBigDecimal(oom);
//        }
//
//        //return r.getDistance(this, oom);
//        //public Math_BigRational getDistance(V3D_Ray r) {
//        if (isParallel(r, oom)) {
//            return p.getDistance(new V3D_Line(r, oom), oom);
//        } else {
//            if (isIntersectedBy(r, oom)) {
//                return BigDecimal.ZERO;
//            } else {
//                V3D_Line rl = new V3D_Line(r, oom);
//                if (isIntersectedBy(rl, oom)) {
//                    return getLineOfIntersection(r.p, oom).getLength().toBigDecimal(oom);
//                }
//                V3D_LineSegment li = (V3D_LineSegment) getLineOfIntersection(r, oom);
//                if (li == null) {
//                    li = getLineOfIntersection(r.p, oom);
//                }
//                if (r.isIntersectedBy(li.q, oom)) {
//                    return li.getLength().toBigDecimal(oom);
//                }
//                return r.p.getDistance(this, oom);
//            }
//        }
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Ray r, int oom) {
        return r.getDistanceSquared(this, oom);
    }
    
    /**
     * @return {@code true} iff this is parallel to the plane defined by x=0.
     */
    public boolean isParallelToX0() {
        //return v.dx.isZero();
        //return p.getX(oom).subtract(q.getX(oom)).isZero();
        return getP().getDX(e.oom).subtract(getQ().getDX(e.oom)).isZero();
    }

    /**
     * @return {@code true} iff this is parallel to the plane defined by y=0.
     */
    public boolean isParallelToY0() {
        //return v.dy.isZero();
        //return p.getY(oom).subtract(q.getY(oom)).isZero();
        return getP().getDY(e.oom).subtract(getQ().getDY(e.oom)).isZero();
    }

    /**
     * @return {@code true} iff this is parallel to the plane defined by z=0.
     */
    public boolean isParallelToZ0() {
        //return v.dz.isZero();
        //return p.getZ(oom).subtract(q.getZ(oom)).isZero();
        return getP().getDZ(e.oom).subtract(getQ().getDZ(e.oom)).isZero();
    }

//    //@Override
//    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
//        if (this.isIntersectedBy(l, oom)) {
//            return true;
//        }
//        if (this.isParallelToX0()) {
//            if (this.isParallelToY0()) {
//                return false;
//            } else if (this.isParallelToZ0()) {
//                return false;
//            } else {
//                return !this.isParallel(l, oom);
//            }
//        } else {
//            if (this.isParallelToY0()) {
//                if (this.isParallelToZ0()) {
//                    return false;
//                } else {
//                    return !this.isParallel(l, oom);
//                }
//            } else {
//                if (this.isParallelToZ0()) {
//                    return !this.isParallel(l, oom);
//                } else {
//                    return true;
//                }
//            }
//        }
//    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The points that define the plan as a matrix.
     */
    public Math_Matrix_BR getAsMatrix(int oom) {
        V3D_Point tp = getP(oom);
        V3D_Point tq = getQ(oom);
        Math_BigRational[][] m = new Math_BigRational[2][3];
        m[0][0] = tp.getX(oom);
        m[0][1] = tp.getY(oom);
        m[0][2] = tp.getZ(oom);
        m[1][0] = tq.getX(oom);
        m[1][1] = tq.getY(oom);
        m[1][2] = tq.getZ(oom);
        return new Math_Matrix_BR(m);
    }

    /**
     * @param l The line segment to return the distance from.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The distance from {@code this} to {@code l} at the {@code oom}
     * precision.
     */
    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        return l.getDistance(this, oom);
//        if (l.isIntersectedBy(this, oom)) {
//            return BigDecimal.ZERO;
//        }
//        BigDecimal lpd = getDistance(l.getP(oom), oom);
//        BigDecimal lqd = getDistance(l.getQ(oom), oom);
//        BigDecimal ll = l.getLength(oom).getSqrt(oom).toBigDecimal(oom);
//        BigDecimal min = lpd.min(lqd);
//        if (ll.compareTo(min) == 1) {
//            // Get the line of intersection and calculate the length.
//            return ((V3D_LineSegment) getLineOfIntersection(l, oom)).getLength(oom)
//                    .getSqrt(oom).toBigDecimal(oom);
//        } else {
//            return min;
//        }
    }
    
    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom) {
        return l.getDistanceSquared(this, oom);
    }

    /**
     * Change {@link #offset} without changing the overall line.
     *
     * @param offset What {@link #offset} is set to.
     */
    public void setOffset(V3D_Vector offset) {
        p = p.add(offset, e.oom).subtract(this.offset, e.oom);
        q = q.add(offset, e.oom).subtract(this.offset, e.oom);
        this.offset = offset;
    }

    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta) {
        p = p.rotate(axisOfRotation, theta, e.bI, e.oom);
        q = q.rotate(axisOfRotation, theta, e.bI, e.oom);
        v = null;
        //v = getV(oom);
    }

    /**
     * For returning the other end of the line segment as a point.
     *
     * @param a A point equal to one or other point of {@code this}.
     * @return The other point that is not equal to a.
     */
    public V3D_Point getOtherPoint(V3D_Point a) {
        V3D_Point pt = getP(e.oom);
        if (pt.equals(a)) {
            return getQ(e.oom);
        } else {
            return pt;
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom) {
        return l.isIntersectedBy(this, oom);
    }

    @Override
    public boolean isIntersectedBy(V3D_Plane p, int oom) {
        return p.isIntersectedBy(this, oom);
    }

    @Override
    public boolean isIntersectedBy(V3D_Triangle t, int oom) {
        return t.isIntersectedBy(this, oom);
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom) {
        return t.isIntersectedBy(this, oom);
    }

    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom) {
        return l.getIntersection(this, oom);
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Triangle t, int oom) {
        return t.getIntersection(this, oom);
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Tetrahedron t, int oom) {
        return t.getIntersection(this, oom);
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
        return t.getDistanceSquared(this, oom);
    }

    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom) {
        return t.getDistance(this, oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom) {
        return t.getDistanceSquared(this, oom);
    }

    /**
     * @param e The V3D_Environment.
     * @param l The line to test points are collinear with.
     * @param points The points to test if they are collinear with l.
     * @return {@code true} iff all points are collinear with l.
     */
    public boolean isCollinear(V3D_Point pt) {
        return this.isIntersectedBy(pt, e.oom);
    }
    
    /**
     * @param e The V3D_Environment.
     * @param l The line to test points are collinear with.
     * @param points The points to test if they are collinear with l.
     * @return {@code true} iff all points are collinear with l.
     */
    public boolean isCollinear(V3D_Line l) {
        if (isCollinear(l.getP(e.oom))) {
            if (isCollinear(l.getQ(e.oom))) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param e The V3D_Environment.
     * @param l The line to test points are collinear with.
     * @param points The points to test if they are collinear with l.
     * @return {@code true} iff all points are collinear with l.
     */
    public static boolean isCollinear(V3D_Environment e, V3D_Line l, V3D_Point... points) {
        for (V3D_Point p : points) {
            if (!l.isIntersectedBy(p, e.oom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param e The V3D_Environment.
     * @param l The line to test points are collinear with.
     * @param points The points to test if they are collinear with l.
     * @return {@code true} iff all points are collinear with l.
     */
    public static boolean isCollinear(V3D_Environment e, V3D_Line l, V3D_Vector... points) {
        V3D_Vector lv = l.getV(e.oom);
        for (V3D_Vector p : points) {
            if (!lv.isScalarMultiple(p, e.oom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param e The V3D_Environment.
     * @param l The line to test points are collinear with.
     * @param points The points to test if they are collinear with l.
     * @return {@code true} iff all points are collinear with l.
     */
    public static boolean isCollinear(V3D_Environment e, V3D_Envelope.Line l, V3D_Envelope.Point... points) {
        for (V3D_Envelope.Point p : points) {
            if (!l.isIntersectedBy(p, e.oom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param e The V3D_Environment.
     * @param points The points to test if they are collinear.
     * @return {@code false} if all points are coincident. {@code true} iff all
     * the points are collinear.
     */
    public static boolean isCollinear(V3D_Environment e, V3D_Vector... points) {
        // For the points to be in a line at least two must be different.
        if (V3D_Point.isCoincident(points)) {
            return false;
        }
        return isCollinear0(e, points);
    }

    /**
     * @param e The V3D_Environment.
     * @param points The points to test if they are collinear.
     * @return {@code false} if all points are coincident. {@code true} iff all
     * the points are collinear.
     */
    public static boolean isCollinear(V3D_Environment e, V3D_Point... points) {
        // For the points to be in a line at least two must be different.
        if (V3D_Point.isCoincident(points)) {
            return false;
        }
        return isCollinear0(e, points);
    }


    /**
     * @param e The V3D_Environment.
     * @param points The points to test if they are collinear.
     * @return {@code true} iff all the points are collinear or coincident.
     */
    public static boolean isCollinear0(V3D_Environment e, V3D_Vector... points) {
        // Get a line
        V3D_Line l = getLine(e, points);
        return isCollinear(e, l, points);
    }

    /**
     * @param e The V3D_Environment.
     * @param points The points to test if they are collinear.
     * @return {@code true} iff all the points are collinear or coincident.
     */
    public static boolean isCollinear0(V3D_Environment e, V3D_Point... points) {
        // Get a line
        V3D_Line l = getLine(e, points);
        return isCollinear(e, l, points);
    }
    
    /**
     * There should be at least two different points.
     *
     * @param e The V3D_Environment.
     * @param points Any number of points, but with two being different.
     * @return A line defined by any two different points or null if the points
     * are coincident.
     */
    public static V3D_Line getLine(V3D_Environment e, V3D_Point... points) {
        V3D_Point p0 = points[0];
        for (V3D_Point p1 : points) {
            if (!p1.equals(p0)) {
                //return new V3D_Line(p0, p1, -1);
                return new V3D_Line(e, p0.getVector(e.oom), p1.getVector(e.oom));
            }
        }
        return null;
    }

    /**
     * There should be at least two different points.
     *
     * @param e The V3D_Environment.
     * @param points Any number of points, but with two being different.
     * @return A line defined by any two different points or null if the points
     * are coincident.
     */
    public static V3D_Line getLine(V3D_Environment e, V3D_Vector... points) {
        V3D_Vector p0 = points[0];
        for (V3D_Vector p1 : points) {
            if (!p1.equals(p0)) {
                //return new V3D_Line(p0, p1, -1);
                return new V3D_Line(e, p0, p1);
            }
        }
        return null;
    }
}
