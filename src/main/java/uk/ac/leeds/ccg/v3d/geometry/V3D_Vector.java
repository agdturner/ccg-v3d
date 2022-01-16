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

import ch.obermuhlner.math.big.BigDecimalMath;
import java.io.Serializable;
import java.math.MathContext;
import java.util.Objects;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigInteger;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.math.number.Math_Quaternion_BigRational;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.geometry.light.V3D_V;

/**
 * A vector used to translate geometries. The magnitude of the vector is not
 * generally calculated and stored, but this is done so upon request to an Order
 * of Magnitude. Other than the magnitude which may be calculated and stored at
 * higher levels of precision, instances are immutable.
 *
 * @author Andy Turner
 * @version 1.1
 */
public class V3D_Vector implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The change in x.
     */
    protected final Math_BigRationalSqrt dx;

    /**
     * The change in y.
     */
    protected final Math_BigRationalSqrt dy;

    /**
     * The change in z.
     */
    protected final Math_BigRationalSqrt dz;

    /**
     * For storing the magnitude.
     */
    protected Math_BigRationalSqrt m;

    /**
     * The zero vector {@code <0,0,0>} where:
     * {@link #dx} = {@link #dy} = {@link #dz} = 0.
     */
    public static final V3D_Vector ZERO = new V3D_Vector(0, 0, 0);

    /**
     * The I vector {@code <1,0,0>} where:
     * {@link #dx} = 1; {@link #dy} = {@link #dz} = 0.
     */
    public static final V3D_Vector I = new V3D_Vector(1, 0, 0);

    /**
     * The J vector {@code <0,1,0>} where:
     * {@link #dy} = 1; {@link #dx} = {@link #dz} = 0.
     */
    public static final V3D_Vector J = new V3D_Vector(0, 1, 0);

    /**
     * The K vector {@code <0,0,1>} where:
     * {@link #dy} = 1; {@link #dx} = {@link #dz} = 0.
     */
    public static final V3D_Vector K = new V3D_Vector(0, 0, 1);

    /**
     * Create a new instance.
     *
     * @param v Used to initialise this. A deep copy of all components is made
     * so that {@code this} is completely independent of {@code v}.
     */
    public V3D_Vector(V3D_Vector v) {
        this.dx = v.dx;
        this.dy = v.dy;
        this.dz = v.dz;
    }

    /**
     * Create a new instance.
     *
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_Vector(Math_BigRationalSqrt dx, Math_BigRationalSqrt dy,
            Math_BigRationalSqrt dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    /**
     * @param dx Used to initialise {@link #dx}.
     * @param dy Used to initialise {@link #dy}.
     * @param dz Used to initialise {@link #dz}.
     */
    public V3D_Vector(Math_BigRational dx, Math_BigRational dy,
            Math_BigRational dz) {
        Math_BigRational dx2 = dx.pow(2);
        Math_BigRational dy2 = dy.pow(2);
        Math_BigRational dz2 = dz.pow(2);
        this.dx = new Math_BigRationalSqrt(dx2, dx);
        this.dy = new Math_BigRationalSqrt(dy2, dy);
        this.dz = new Math_BigRationalSqrt(dz2, dz);
    }

    /**
     * @param dx Used to initialise {@link #dx}.
     * @param dy Used to initialise {@link #dy}.
     * @param dz Used to initialise {@link #dz}.
     * @param m What {@link #m} is set to.
     */
    public V3D_Vector(Math_BigRational dx, Math_BigRational dy,
            Math_BigRational dz, Math_BigRationalSqrt m) {
        this(dx, dy, dz);
        this.m = m;
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy Used to initialise {@link #dy}.
     * @param dz Used to initialise {@link #dz}.
     */
    public V3D_Vector(Math_BigRationalSqrt dx, Math_BigRational dy,
            Math_BigRational dz) {
        Math_BigRational dy2 = dy.pow(2);
        Math_BigRational dz2 = dz.pow(2);
        this.dx = dx;
        this.dy = new Math_BigRationalSqrt(dy2, dy);
        this.dz = new Math_BigRationalSqrt(dz2, dz);
    }

    /**
     * @param dx Used to initialise {@link #dx}.
     * @param dy What {@link #dy} is set to.
     * @param dz Used to initialise {@link #dz}.
     */
    public V3D_Vector(Math_BigRational dx, Math_BigRationalSqrt dy,
            Math_BigRational dz) {
        Math_BigRational dx2 = dx.pow(2);
        Math_BigRational dz2 = dz.pow(2);
        this.dx = new Math_BigRationalSqrt(dx2, dx);
        this.dy = dy;
        this.dz = new Math_BigRationalSqrt(dz2, dz);
    }

    /**
     * @param dx Used to initialise {@link #dx}.
     * @param dy Used to initialise {@link #dy}.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_Vector(Math_BigRational dx, Math_BigRational dy,
            Math_BigRationalSqrt dz) {
        Math_BigRational dx2 = dx.pow(2);
        Math_BigRational dy2 = dy.pow(2);
        this.dx = new Math_BigRationalSqrt(dx2, dx);
        this.dy = new Math_BigRationalSqrt(dy2, dy);
        this.dz = dz;
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz Used to initialise {@link #dz}.
     */
    public V3D_Vector(Math_BigRationalSqrt dx, Math_BigRationalSqrt dy,
            Math_BigRational dz) {
        Math_BigRational dz2 = dz.pow(2);
        this.dx = dx;
        this.dy = dy;
        this.dz = new Math_BigRationalSqrt(dz2, dz);
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy Used to initialise {@link #dy}.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_Vector(Math_BigRationalSqrt dx, Math_BigRational dy,
            Math_BigRationalSqrt dz) {
        Math_BigRational dy2 = dy.pow(2);
        this.dx = dx;
        this.dy = new Math_BigRationalSqrt(dy2, dy);
        this.dz = dz;
    }

    /**
     * @param dx Used to initialise {@link #dx}.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_Vector(Math_BigRational dx, Math_BigRationalSqrt dy,
            Math_BigRationalSqrt dz) {
        Math_BigRational dx2 = dx.pow(2);
        this.dx = new Math_BigRationalSqrt(dx2, dx);
        this.dy = dy;
        this.dz = dz;
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_Vector(long dx, long dy, long dz) {
        this(Math_BigRational.valueOf(dx), Math_BigRational.valueOf(dy),
                Math_BigRational.valueOf(dz));
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_Vector(double dx, double dy, double dz) {
        this(Math_BigRational.valueOf(dx), Math_BigRational.valueOf(dy),
                Math_BigRational.valueOf(dz));
    }

    /**
     * Creates a vector from the origin to {@code p}
     *
     * @param p the point to which the vector starting at the origin goes.
     */
    public V3D_Vector(V3D_Envelope.Point p) {
        this(p.x, p.y, p.z);
    }

    /**
     * Creates a vector from {@code p} to {@code q}.
     *
     * @param p the point where the vector starts.
     * @param q the point where the vector ends.
     * @param oom Used for initial square root calculations for magnitude.
     */
    public V3D_Vector(V3D_Point p, V3D_Point q, int oom) {
        this(q.getX(oom).subtract(p.getX(oom)),
                q.getY(oom).subtract(p.getY(oom)),
                q.getZ(oom).subtract(p.getZ(oom)));
    }

    /**
     * Creates a vector from the Origin to {@code p} to {@code q}.
     *
     * @param p the point where the vector starts.
     * @param oom Used for initial square root calculations for magnitude.
     */
    public V3D_Vector(V3D_Point p, int oom) {
        this(p.getVector(oom));
    }

    /**
     * Creates a vector from {@code p} to {@code q}.
     *
     * @param p the point where the vector starts.
     * @param q the point where the vector ends.
     */
    public V3D_Vector(V3D_Envelope.Point p, V3D_Envelope.Point q) {
        this(q.x.subtract(p.x), q.y.subtract(p.y), q.z.subtract(p.z));
    }

    /**
     * Creates a vector from {@code v}.
     *
     * @param v the V3D_V vector.
     */
    public V3D_Vector(V3D_V v) {
        this(v.x, v.y, v.z);
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
    protected String toStringFields(String pad) {
        return pad + "dx=" + dx + ",\n"
                + pad + "dy=" + dy + ",\n"
                + pad + "dz=" + dz;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Vector v3D_Vector) {
            return equals(v3D_Vector);
        }
        return false;
    }

    /**
     * Indicates if {@code this} and {@code v} are equal.
     *
     * @param v The vector to test for equality with {@code this}.
     * @return {@code true} iff {@code this} is the same as {@code v}.
     */
    public boolean equals(V3D_Vector v) {
        return dx.compareTo(v.dx) == 0 && dy.compareTo(v.dy) == 0
                && dz.compareTo(v.dz) == 0;
    }

    /**
     * Indicates if {@code this} is the reverse of {@code v}.
     *
     * @param v The vector to compare with {@code this}.
     * @return {@code true} iff {@code this} is the reverse of {@code v}.
     */
    public boolean isReverse(V3D_Vector v) {
        return equals(v.reverse());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.dx);
        hash = 23 * hash + Objects.hashCode(this.dy);
        hash = 23 * hash + Objects.hashCode(this.dz);
        return hash;
    }

    /**
     * @return {@code true} if {@code this.equals(e.zeroVector)}
     */
    public boolean isZeroVector() {
        return this.equals(ZERO);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The value of {@link #dx} as a Math_BigRational.
     */
    public Math_BigRational getDX(int oom) {
        return dx.getSqrt(oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The value of {@link #dy} as a Math_BigRational.
     */
    public Math_BigRational getDY(int oom) {
        return dy.getSqrt(oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The value of {@link #dz} as a Math_BigRational.
     */
    public Math_BigRational getDZ(int oom) {
        return dz.getSqrt(oom);
    }

    /**
     * @return {@link #dx}
     */
    public Math_BigRationalSqrt getDX() {
        return dx;
    }

    /**
     * @return {@link #dy}
     */
    public Math_BigRationalSqrt getDY() {
        return dy;
    }

    /**
     * @return {@link #dz}
     */
    public Math_BigRationalSqrt getDZ() {
        return dz;
    }

    /**
     * @param s The scalar value to multiply this by.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return Scaled vector.
     */
    public V3D_Vector multiply(Math_BigRational s, int oom) {
        return new V3D_Vector(
                getDX(oom).multiply(s),
                getDY(oom).multiply(s),
                getDZ(oom).multiply(s));
    }

    /**
     * @param s The scalar value to divide this by.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return Scaled vector.
     */
    public V3D_Vector divide(Math_BigRational s, int oom) {
        return new V3D_Vector(
                getDX(oom).divide(s),
                getDY(oom).divide(s),
                getDZ(oom).divide(s));
    }

    /**
     * Adding or applying.
     *
     * @param v The vector to add.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return A new vector which is {@code this} add {@code v}.
     */
    public V3D_Vector add(V3D_Vector v, int oom) {
        return new V3D_Vector(
                getDX(oom).add(v.getDX(oom)),
                getDY(oom).add(v.getDY(oom)),
                getDZ(oom).add(v.getDZ(oom)));
    }

    /**
     * @param v The vector to subtract.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return A new vector which is {@code this} minus {@code v}.
     */
    public V3D_Vector subtract(V3D_Vector v, int oom) {
        return new V3D_Vector(
                getDX(oom).subtract(v.getDX(oom)),
                getDY(oom).subtract(v.getDY(oom)),
                getDZ(oom).subtract(v.getDZ(oom)));
    }

    /**
     * @return A new vector which is the opposite to {@code this}.
     */
    public V3D_Vector reverse() {
        return new V3D_Vector(dx.negate(), dy.negate(), dz.negate());
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Dot_product">dot product</A>.
     *
     * @param v V3D_Vector
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return dot product
     */
    public Math_BigRational getDotProduct(V3D_Vector v, int oom) {
        return (v.getDX(oom).multiply(getDX(oom)))
                .add(v.getDY(oom).multiply(getDY(oom)))
                .add(v.getDZ(oom).multiply(getDZ(oom)));
//        Math_BigRational vdx = v.getDX().abs();
//        if (v.dx.negative) {
//            vdx = vdx.negate();
//        }
//        Math_BigRational vdy = v.getDY().abs();
//        if (v.dy.negative) {
//            vdy = vdy.negate();
//        }
//        Math_BigRational vdz = v.getDZ().abs();
//        if (v.dz.negative) {
//            vdz = vdz.negate();
//        }
//        Math_BigRational tdx = getDX().abs();
//        if (dx.negative) {
//            tdx = tdx.negate();
//        }
//        Math_BigRational tdy = getDY().abs();
//        if (dy.negative) {
//            tdy = tdy.negate();
//        }
//        Math_BigRational tdz = getDZ().abs();
//        if (dz.negative) {
//            tdz = tdz.negate();
//        }
//        return (vdx.multiply(tdx)).add(vdy.multiply(tdy)).add(vdz.multiply(tdz));
//        return (v.dx.multiply(dx)).getSqrt()
//                .add(v.dy.multiply(dy).getSqrt())
//                .add(v.dz.multiply(dz).getSqrt());
    }

    /**
     * Test if this is orthogonal to {@code v}.
     *
     * @param v The vector to test for orthogonality with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} if this and {@code v} are orthogonal.
     */
    public boolean isOrthogonal(V3D_Vector v, int oom) {
        if (isScalarMultiple(v, oom)) {
            return false;
        }
        return getDotProduct(v, oom).isZero();
    }

    /**
     * @return The magnitude of the vector squared.
     */
    public Math_BigRational getMagnitudeSquared() {
        return getMagnitude().getX();
    }

    /**
     * @return The magnitude of m. {@link V3D_Environment#DEFAULT_OOM} is used
     * to initialise the result.
     */
    public Math_BigRationalSqrt getMagnitude() {
        if (m == null) {
            initM(V3D_Environment.DEFAULT_OOM);
        }
        return m;
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     */
    private void initM(int oom) {
        m = new Math_BigRationalSqrt(dx.getX().add(dy.getX().add(dz.getX())),
                oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The magnitude of m.
     */
    protected Math_BigRationalSqrt getMagnitude0(int oom) {
        if (m == null) {
            initM(oom);
        } else {
            if (m.getOom() <= oom) {
                return m;
            } else {
                initM(oom);
            }
        }
        return m;
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The magnitude of m.
     */
    public Math_BigRational getMagnitude(int oom) {
        return getMagnitude0(oom).getSqrt(oom);
    }

    /**
     * Test if {@code v} is a scalar multiple of {@code this}.
     *
     * @param v The vector to test if it is a scalar multiple of {@code this}.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} if {@code this} and {@code v} are scalar multiples.
     */
    public boolean isScalarMultiple(V3D_Vector v, int oom) {
        if (dx.isZero()) {
            if (v.dx.isZero()) {
                if (dy.isZero()) {
                    if (v.dy.isZero()) {
                        if (dz.isZero()) {
                            return v.dz.isZero();
                        } else {
                            return true;
                        }
                    } else {
                        return false;
                    }
                } else {
                    if (v.dy.isZero()) {
                        return false;
                    } else {
                        if (dz.isZero()) {
                            return v.getDZ(oom).isZero();
                        } else {
                            if (v.dz.isZero()) {
                                return false;
                            } else {
                                Math_BigRational sy = getDY(oom).divide(v.getDY(oom));
                                Math_BigRational sz = getDZ(oom).divide(v.getDZ(oom));
                                return sy.compareTo(sz) == 0;
                            }
                        }
                    }
                }
            } else {
                return false;
            }
        } else {
            if (v.dx.isZero()) {
                return false;
            } else {
                if (dy.isZero()) {
                    if (v.dy.isZero()) {
                        if (dz.isZero()) {
                            return v.dz.isZero();
                        } else {
                            if (v.dz.isZero()) {
                                return false;
                            } else {
                                Math_BigRational sx = getDX(oom).divide(v.getDX(oom));
                                Math_BigRational sz = getDZ(oom).divide(v.getDZ(oom));
                                return sx.compareTo(sz) == 0;
                            }
                        }
                    } else {
                        return false;
                    }
                } else {
                    if (v.dy.isZero()) {
                        return false;
                    } else {
                        if (dz.isZero()) {
                            if (v.dz.isZero()) {
                                Math_BigRational sx = getDX(oom).divide(v.getDX(oom));
                                Math_BigRational sy = getDY(oom).divide(v.getDY(oom));
                                return sx.compareTo(sy) == 0;
                            } else {
                                return false;
                            }
                        } else {
                            if (v.dz.isZero()) {
                                return false;
                            } else {
                                Math_BigRational sx = getDX(oom).divide(v.getDX(oom));
                                Math_BigRational sy = getDY(oom).divide(v.getDY(oom));
                                Math_BigRational sz = getDZ(oom).divide(v.getDZ(oom));
                                if (sx.compareTo(sy) == 0) {
                                    return sy.compareTo(sz) == 0;
                                } else {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Compute and return the angle (in radians) between {@code #this} and
     * {@code v}. The algorithm is to:
     * <ol>
     * <li>Find the dot product of the vectors.</li>
     * <li>Divide the dot product with the magnitude of the first vector.</li>
     * <li>the second vector.</li>
     * </ol>
     *
     * @param v The vector to find the angle between.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The angle in radians between {@code this} and {@code v}.
     */
    public Math_BigRational getAngle(V3D_Vector v, int oom) {
        Math_BigRational dp = getDotProduct(v, oom);
        Math_BigRational m2 = getMagnitude().getSqrt(oom);
        Math_BigRational vm2 = v.getMagnitude().getSqrt(oom);
        MathContext mc = new MathContext(-oom); // This is almost certainly wrong and needs to be checked!
        return Math_BigRational.valueOf(BigDecimalMath.acos(dp.divide(m2.multiply(vm2)).toBigDecimal(mc), mc));
        //return null;
    }

    /**
     * Calculate and return {@code #this} rotated using the parameters. (see
     * Doug (https://math.stackexchange.com/users/102399/doug), How do you
     * rotate a vector by a unit quaternion?, URL (version: 2019-06-12):
     * https://math.stackexchange.com/q/535223)
     *
     * @param axisOfRotation The axis of rotation. This should be a unit vector 
     * accurate to a sufficient precision.
     * @param theta The angle of rotation.
     * @param bI For the Taylor series for trigonometry calculations.
     * @param oom The Order of Magnitude for the precision of the result
     * components.
     * @return The vector which is {@code #this} rotated using the parameters.
     */
    public V3D_Vector rotate(V3D_Vector axisOfRotation, Math_BigRational theta,
            Math_BigInteger bI, int oom) {
        int oomt = oom - 2;
        Math_BigRational adx = axisOfRotation.getDX(oomt);
        Math_BigRational ady = axisOfRotation.getDY(oomt);
        Math_BigRational adz = axisOfRotation.getDZ(oomt);
        Math_BigRational thetaDiv2 = theta.divide(2);
        Math_BigRational sinThetaDiv2 = thetaDiv2.sin(bI, oomt);
        Math_BigRational w = thetaDiv2.cos(bI, oomt);
        Math_BigRational x = sinThetaDiv2.multiply(adx);
        Math_BigRational y = sinThetaDiv2.multiply(ady);
        Math_BigRational z = sinThetaDiv2.multiply(adz);
        Math_Quaternion_BigRational r = new Math_Quaternion_BigRational(
                w, x, y, z);
        // R'=rR
        Math_Quaternion_BigRational rR = new Math_Quaternion_BigRational(
                w, x.negate(), y.negate(), z.negate());
        Math_Quaternion_BigRational p = new Math_Quaternion_BigRational(
                Math_BigRational.ZERO, this.getDX(oomt), this.getDY(oomt), 
                this.getDZ(oomt));
        // P'=pP
        Math_Quaternion_BigRational pP = r.multiply(p).multiply(rR);
        return new V3D_Vector(pP.x.round(oom), pP.y.round(oom), pP.z.round(oom));
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Cross_product">cross product</A>.
     * Treat this as the first vector and {@code v} as the second vector. The
     * resulting vector is in the direction given by the right hand rule.
     *
     * @param v V3D_Vector
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return V3D_Vector
     */
    public V3D_Vector getCrossProduct(V3D_Vector v, int oom) {
//        Math_BigRational tdx = getDX(oom);
//        Math_BigRational tdy = getDY(oom);
//        Math_BigRational tdz = getDZ(oom);
//        Math_BigRational vdx = v.getDX(oom);
//        Math_BigRational vdy = v.getDY(oom);
//        Math_BigRational vdz = v.getDZ(oom);
//        return new V3D_Vector(
//                tdy.multiply(vdz).subtract(tdz.multiply(vdy)),
//                tdz.multiply(vdx).subtract(tdx.multiply(vdz)),
//                tdx.multiply(vdy).subtract(vdx.multiply(vdy)), oom);
        return new V3D_Vector(
                dy.multiply(v.dz, oom).getSqrt(oom).subtract(dz.multiply(v.dy, oom).getSqrt(oom)),
                dz.multiply(v.dx, oom).getSqrt(oom).subtract(dx.multiply(v.dz, oom).getSqrt(oom)),
                dx.multiply(v.dy, oom).getSqrt(oom).subtract(v.dx.multiply(dy, oom).getSqrt(oom)));
//        return new V3D_Vector(
//                dy.multiply(v.dz, oom).getSqrt(oom).subtract(dz.multiply(v.dy, oom).getSqrt(oom)),
//                dx.multiply(v.dz, oom).getSqrt(oom).subtract(dz.multiply(v.dx, oom).getSqrt(oom)).negate(),
//                dx.multiply(v.dy, oom).getSqrt(oom).subtract(v.dx.multiply(dy, oom).getSqrt(oom)), oom);
    }

    /**
     * Scales by {@link #m} to give a unit vector with length 1.
     *
     * @param oom The order of magnitude for the precision of the result.
     * @return this scaled by {@link #m}.
     */
    public V3D_Vector getUnitVector(int oom) {
        Math_BigRational d = getMagnitude(oom);
//        return new V3D_Vector(
//                dx.getSqrt(oom).divide(d),
//                dy.getSqrt(oom).divide(d),
//                dz.getSqrt(oom).divide(d), oom);
        /**
         * Force the magnitude to be equal to one.
         */
        return new V3D_Vector(
                getDX(oom).divide(d),
                getDY(oom).divide(d),
                getDZ(oom).divide(d), Math_BigRationalSqrt.ONE);
    }

    /**
     * @return The direction of the vector:
     * <Table>
     * <caption>Directions</caption>
     * <thead>
     * <tr><td>ID</td><td>Description</td></tr>
     * </thead>
     * <tbody>
     * <tr><td>1</td><td>Pdx, Pdy, Pdz</td></tr>
     * <tr><td>2</td><td>Pdx, Pdy, Ndz</td></tr>
     * <tr><td>3</td><td>Pdx, Ndy, Pdz</td></tr>
     * <tr><td>4</td><td>Pdx, Ndy, Ndz</td></tr>
     * <tr><td>5</td><td>Ndx, Pdy, Pdz</td></tr>
     * <tr><td>6</td><td>Ndx, Pdy, Ndz</td></tr>
     * <tr><td>7</td><td>Ndx, Ndy, Pdz</td></tr>
     * <tr><td>8</td><td>Ndx, Ndy, Ndz</td></tr>
     * </tbody>
     * </Table>
     */
    public int getDirection() {
        if (dx.compareTo(Math_BigRationalSqrt.ZERO) != -1) {
            if (dy.compareTo(Math_BigRationalSqrt.ZERO) != -1) {
                if (dz.compareTo(Math_BigRationalSqrt.ZERO) != -1) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (dz.compareTo(Math_BigRationalSqrt.ZERO) != -1) {
                    return 3;
                } else {
                    return 4;
                }
            }
        } else {
            if (dy.compareTo(Math_BigRationalSqrt.ZERO) != -1) {
                if (dz.compareTo(Math_BigRationalSqrt.ZERO) != -1) {
                    return 5;
                } else {
                    return 6;
                }
            } else {
                if (dz.compareTo(Math_BigRationalSqrt.ZERO) != -1) {
                    return 7;
                } else {
                    return 8;
                }
            }
        }
    }
}
