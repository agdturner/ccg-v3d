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
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
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
     * The I vector {@code <1,0,0>}.
     */
    public static final V3D_Vector I = new V3D_Vector(1, 0, 0);

    /**
     * The J vector {@code <0,1,0>}.
     */
    public static final V3D_Vector J = new V3D_Vector(0, 1, 0);

    /**
     * The K vector {@code <0,0,1>}.
     */
    public static final V3D_Vector K = new V3D_Vector(0, 0, 1);

    /**
     * The IJ vector {@code <1,1,0>}.
     */
    public static final V3D_Vector IJ = new V3D_Vector(1, 1, 0);

    /**
     * The IJ vector {@code <1,-1,0>}.
     */
    public static final V3D_Vector InJ = new V3D_Vector(1, -1, 0);

    /**
     * The IK vector {@code <1,0,1>}.
     */
    public static final V3D_Vector IK = new V3D_Vector(1, 0, 1);

    /**
     * The IK vector {@code <1,0,-1>}.
     */
    public static final V3D_Vector InK = new V3D_Vector(1, 0, -1);

    /**
     * The JK vector {@code <0,1,1>}.
     */
    public static final V3D_Vector JK = new V3D_Vector(0, 1, 1);

    /**
     * The JK vector {@code <0,1,-1>}.
     */
    public static final V3D_Vector JnK = new V3D_Vector(0, 1, -1);

    /**
     * The IJK vector {@code <1,1,1>} where:
     */
    public static final V3D_Vector IJK = new V3D_Vector(1, 1, 1);

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

//    /**
//     * Creates a vector from the origin to {@code p}
//     *
//     * @param p the point to which the vector starting at the origin goes.
//     */
//    public V3D_Vector(V3D_Envelope.Point p) {
//        this(p.x, p.y, p.z);
//    }

    /**
     * Creates a vector from {@code p} to {@code q}.
     *
     * @param p the point where the vector starts.
     * @param q the point where the vector ends.
     * @param oom Used for initial square root calculations for magnitude.
     */
    public V3D_Vector(V3D_Point p, V3D_Point q, int oom, RoundingMode rm) {
        this(q.getX(oom, rm).subtract(p.getX(oom, rm)),
                q.getY(oom, rm).subtract(p.getY(oom, rm)),
                q.getZ(oom, rm).subtract(p.getZ(oom, rm)));
    }

    /**
     * Creates a vector from the Origin to {@code p} to {@code q}.
     *
     * @param p the point where the vector starts.
     * @param oom Used for initial square root calculations for magnitude.
     */
    public V3D_Vector(V3D_Point p, int oom, RoundingMode rm) {
        this(p.getVector(oom, rm));
    }

//    /**
//     * Creates a vector from {@code p} to {@code q}.
//     *
//     * @param p the point where the vector starts.
//     * @param q the point where the vector ends.
//     */
//    public V3D_Vector(V3D_Envelope.Point p, V3D_Envelope.Point q) {
//        this(q.x.subtract(p.x), q.y.subtract(p.y), q.z.subtract(p.z));
//    }

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
        return pad + this.getClass().getSimpleName()
                + "(" + toStringFieldsSimple("") + ")";
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

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    protected String toStringFieldsSimple(String pad) {
        return pad + "dx=" + dx.toStringSimple()
                + ", dy=" + dy.toStringSimple()
                + ", dz=" + dz.toStringSimple();
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
    public Math_BigRational getDX(int oom, RoundingMode rm) {
        return dx.getSqrt(oom, rm);
    }

//    /**
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return The value of {@link #dx} as a Math_BigRational.
//     */
//    public Math_BigRational getDX(int oom) {
//        return dx.getSqrt(oom);
//    }
    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The value of {@link #dy} as a Math_BigRational.
     */
    public Math_BigRational getDY(int oom, RoundingMode rm) {
        return dy.getSqrt(oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The value of {@link #dz} as a Math_BigRational.
     */
    public Math_BigRational getDZ(int oom, RoundingMode rm) {
        return dz.getSqrt(oom, rm);
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
    public V3D_Vector multiply(Math_BigRational s, int oom, RoundingMode rm) {
        int oomn5 = oom - 5;
        return new V3D_Vector(
                getDX(oomn5, rm).multiply(s).round(oom, rm),
                getDY(oomn5, rm).multiply(s).round(oom, rm),
                getDZ(oomn5, rm).multiply(s).round(oom, rm));
    }

    /**
     * @param s The scalar value to divide this by.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return Scaled vector.
     */
    public V3D_Vector divide(Math_BigRational s, int oom, RoundingMode rm) {
        int oomn5 = oom - 5;
        return new V3D_Vector(
                getDX(oomn5, rm).divide(s).round(oom, rm),
                getDY(oomn5, rm).divide(s).round(oom, rm),
                getDZ(oomn5, rm).divide(s).round(oom, rm));
    }

    /**
     * Adding or applying.
     *
     * @param v The vector to add.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A new vector which is {@code this} add {@code v}.
     */
    public V3D_Vector add(V3D_Vector v, int oom, RoundingMode rm) {
        int oomn5 = oom - 5;
        return new V3D_Vector(
                getDX(oomn5, rm).add(v.getDX(oomn5, rm)).round(oom, rm),
                getDY(oomn5, rm).add(v.getDY(oomn5, rm)).round(oom, rm),
                getDZ(oomn5, rm).add(v.getDZ(oomn5, rm)).round(oom, rm));
    }

    /**
     * @param v The vector to subtract.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A new vector which is {@code this} minus {@code v}.
     */
    public V3D_Vector subtract(V3D_Vector v, int oom, RoundingMode rm) {
        int oomn5 = oom - 5;
        return new V3D_Vector(
                getDX(oomn5, rm).subtract(v.getDX(oomn5, rm)).round(oom, rm),
                getDY(oomn5, rm).subtract(v.getDY(oomn5, rm)).round(oom, rm),
                getDZ(oomn5, rm).subtract(v.getDZ(oomn5, rm)).round(oom, rm));
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return dot product
     */
    public Math_BigRational getDotProduct(V3D_Vector v, int oom,
            RoundingMode rm) {
        oom -= 6;
        return (v.getDX(oom, rm).multiply(getDX(oom, rm)))
                .add(v.getDY(oom, rm).multiply(getDY(oom, rm)))
                .add(v.getDZ(oom, rm).multiply(getDZ(oom, rm)));
    }

    /**
     * Test if this is orthogonal to {@code v}.
     *
     * @param v The vector to test for orthogonality with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this and {@code v} are orthogonal.
     */
    public boolean isOrthogonal(V3D_Vector v, int oom, RoundingMode rm) {
        // Special case
        if (isScalarMultiple(v, oom, rm)) {
            return false;
        }
        return getDotProduct(v, oom, rm).isZero();
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
            initM(V3D_Environment.DEFAULT_OOM, V3D_Environment.DEFAULT_RM);
        }
        return m;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    private void initM(int oom, RoundingMode rm) {
        m = new Math_BigRationalSqrt(dx.getX().add(dy.getX().add(dz.getX())),
                oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The magnitude of m.
     */
    protected Math_BigRationalSqrt getMagnitude0(int oom, RoundingMode rm) {
        if (m == null) {
            initM(oom, rm);
        } else {
            if (m.getOom() <= oom) {
                return m;
            } else {
                initM(oom, rm);
            }
        }
        return m;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The magnitude of m.
     */
    public Math_BigRational getMagnitude(int oom, RoundingMode rm) {
        return getMagnitude0(oom, rm).getSqrt(oom, rm);
    }

    /**
     * Test if {@code v} is a scalar multiple of {@code this}.
     *
     * @param v The vector to test if it is a scalar multiple of {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} and {@code v} are scalar multiples.
     */
    public boolean isScalarMultiple(V3D_Vector v, int oom, RoundingMode rm) {
        // Special case
        if (this.isZeroVector() || v.isZeroVector()) {
            return false;
        }
        oom -= 6;
        return this.multiply(getDotProduct(v, oom, rm), oom, rm).equals(
                v.multiply(getDotProduct(this, oom, rm), oom, rm));
//        if (dx.isZero()) {
//            if (v.dx.isZero()) {
//                if (dy.isZero()) {
//                    return true;
//                } else {
//                    if (v.dy.isZero()) {
//                        return true;
//                    } else {
//                        if (dz.isZero()) {
//                            return true;
//                        } else {
//                            if (v.dz.isZero()) {
//                                return true;
//                            } else {
//                                Math_BigRational sy = getDY(oom, rm)
//                                        .divide(v.getDY(oom, rm)).round(oom, rm);
//                                Math_BigRational sz = getDZ(oom, rm)
//                                        .divide(v.getDZ(oom, rm)).round(oom, rm);
//                                return sy.compareTo(sz) == 0;
//                            }
//                        }
//                    }
//                }
//            } else {
//                if (dy.isZero()) {
//                    return true;
//                } else {
//                    if (v.dy.isZero()) {
//                        return true;
//                    } else {
//                        if (dz.isZero()) {
//                            return true;
//                        } else {
//                            if (v.dz.isZero()) {
//                                return true;
//                            } else {
//                                Math_BigRational sy = getDY(oom, rm)
//                                        .divide(v.getDY(oom, rm)).round(oom, rm);
//                                Math_BigRational sz = getDZ(oom, rm)
//                                        .divide(v.getDZ(oom, rm)).round(oom, rm);
//                                return sy.compareTo(sz) == 0;
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            if (v.dx.isZero()) {
//                return false;
//            } else {
//                if (dy.isZero()) {
//                    if (v.dy.isZero()) {
//                        if (dz.isZero()) {
//                            return v.dz.isZero();
//                        } else {
//                            if (v.dz.isZero()) {
//                                return false;
//                            } else {
//                                Math_BigRational sx = getDX(oom, rm)
//                                        .divide(v.getDX(oom, rm)).round(oom, rm);
//                                Math_BigRational sz = getDZ(oom, rm)
//                                        .divide(v.getDZ(oom, rm)).round(oom, rm);
//                                return sx.compareTo(sz) == 0;
//                            }
//                        }
//                    } else {
//                        return false;
//                    }
//                } else {
//                    if (v.dy.isZero()) {
//                        return false;
//                    } else {
//                        if (dz.isZero()) {
//                            if (v.dz.isZero()) {
//                                Math_BigRational sx = getDX(oom, rm)
//                                        .divide(v.getDX(oom, rm)).round(oom, rm);
//                                Math_BigRational sy = getDY(oom, rm)
//                                        .divide(v.getDY(oom, rm)).round(oom, rm);
//                                return sx.compareTo(sy) == 0;
//                            } else {
//                                return false;
//                            }
//                        } else {
//                            if (v.dz.isZero()) {
//                                return false;
//                            } else {
//                                Math_BigRational sx = getDX(oom, rm)
//                                        .divide(v.getDX(oom, rm)).round(oom, rm);
//                                Math_BigRational sy = getDY(oom, rm)
//                                        .divide(v.getDY(oom, rm)).round(oom, rm);
//                                if (sx.compareTo(sy) == 0) {
//                                    Math_BigRational sz = getDZ(oom, rm)
//                                            .divide(v.getDZ(oom, rm)).round(oom, rm);
//                                    return sy.compareTo(sz) == 0;
//                                } else {
//                                    return false;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The angle in radians between {@code this} and {@code v}.
     */
    public Math_BigRational getAngle(V3D_Vector v, int oom, RoundingMode rm) {
        Math_BigRational dp = getDotProduct(v, oom, rm);
        Math_BigRational mag = getMagnitude(oom, rm);
        Math_BigRational vmag = v.getMagnitude(oom, rm);
        MathContext mc = new MathContext(1 - oom); // This needs checking!
        return Math_BigRational.valueOf(BigDecimalMath.acos(
                dp.divide(mag.multiply(vmag)).toBigDecimal(mc), mc));
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
     * @param bd For the Taylor series for trigonometry calculations.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The vector which is {@code #this} rotated using the parameters.
     */
    public V3D_Vector rotate(V3D_Vector axisOfRotation, Math_BigRational theta,
            Math_BigDecimal bd, int oom, RoundingMode rm) {
        Math_BigRational twoPi = Math_BigRational.valueOf(bd.getPi(oom, rm)).multiply(2);
        // Change a negative angle into a positive one.
        while (theta.compareTo(Math_BigRational.ZERO) == -1) {
            theta = theta.add(twoPi);
        }
        // Only rotate less than 2Pi radians.
        while (theta.compareTo(twoPi) == 1) {
            theta = theta.subtract(twoPi);
        }
        if (theta.compareTo(Math_BigRational.ZERO) == 1) {
            int oomn2 = oom - 6;
            Math_BigRational adx = axisOfRotation.getDX(oomn2, rm);
            Math_BigRational ady = axisOfRotation.getDY(oomn2, rm);
            Math_BigRational adz = axisOfRotation.getDZ(oomn2, rm);
            Math_BigRational thetaDiv2 = theta.divide(2);
            Math_BigRational sinThetaDiv2 = thetaDiv2.sin(bd.getBi(), oomn2, rm);
            Math_BigRational w = thetaDiv2.cos(bd.getBi(), oomn2, rm);
            Math_BigRational x = sinThetaDiv2.multiply(adx);
            Math_BigRational y = sinThetaDiv2.multiply(ady);
            Math_BigRational z = sinThetaDiv2.multiply(adz);
            Math_Quaternion_BigRational r = new Math_Quaternion_BigRational(
                    w, x, y, z);
            // R'=rR
            Math_Quaternion_BigRational rR = new Math_Quaternion_BigRational(
                    w, x.negate(), y.negate(), z.negate());
            Math_Quaternion_BigRational p = new Math_Quaternion_BigRational(
                    Math_BigRational.ZERO, this.getDX(oomn2, rm),
                    this.getDY(oomn2, rm), this.getDZ(oomn2, rm));
            // P'=pP
            Math_Quaternion_BigRational pP = r.multiply(p).multiply(rR);
            return new V3D_Vector(pP.x.round(oom, rm), pP.y.round(oom, rm),
                    pP.z.round(oom, rm));
        }
        return new V3D_Vector(this);
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Cross_product">cross product</A>.
     * Treat this as the first vector and {@code v} as the second vector. The
     * resulting vector is in the direction given by the right hand rule.
     *
     * @param v V3D_Vector
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return V3D_Vector
     */
    public V3D_Vector getCrossProduct(V3D_Vector v, int oom, RoundingMode rm) {
        oom = oom - 6;
        return new V3D_Vector(
                dy.multiply(v.dz, oom, rm).getSqrt(oom, rm)
                        .subtract(dz.multiply(v.dy, oom, rm).getSqrt(oom, rm)),
                dz.multiply(v.dx, oom, rm).getSqrt(oom, rm)
                        .subtract(dx.multiply(v.dz, oom, rm).getSqrt(oom, rm)),
                dx.multiply(v.dy, oom, rm).getSqrt(oom, rm)
                        .subtract(dy.multiply(v.dx, oom, rm).getSqrt(oom, rm)));
    }

    /**
     * Scales by {@link #m} to give a unit vector with length 1.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return this scaled by {@link #m}.
     */
    public V3D_Vector getUnitVector(int oom, RoundingMode rm) {
        Math_BigRational d = getMagnitude(oom, rm);
//        return new V3D_Vector(
//                dx.getSqrt(oom).divide(d),
//                dy.getSqrt(oom).divide(d),
//                dz.getSqrt(oom).divide(d), oom);
        /**
         * Force the magnitude to be equal to one.
         */
        return new V3D_Vector(
                getDX(oom, rm).divide(d),
                getDY(oom, rm).divide(d),
                getDZ(oom, rm).divide(d), Math_BigRationalSqrt.ONE);
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
