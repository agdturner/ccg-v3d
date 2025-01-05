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
import ch.obermuhlner.math.big.BigRational;
import java.io.Serializable;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
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
     */
    public V3D_Vector() {
        this.dx = Math_BigRationalSqrt.ZERO;
        this.dy = Math_BigRationalSqrt.ZERO;
        this.dz = Math_BigRationalSqrt.ZERO;
    }

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
    public V3D_Vector(BigRational dx, BigRational dy, BigRational dz) {
        this.dx = new Math_BigRationalSqrt(dx.pow(2), dx);
        this.dy = new Math_BigRationalSqrt(dy.pow(2), dy);
        this.dz = new Math_BigRationalSqrt(dz.pow(2), dz);
    }

    /**
     * @param dx Used to initialise {@link #dx}.
     * @param dy Used to initialise {@link #dy}.
     * @param dz Used to initialise {@link #dz}.
     * @param m What {@link #m} is set to.
     */
    public V3D_Vector(BigRational dx, BigRational dy, BigRational dz,
            Math_BigRationalSqrt m) {
        this(dx, dy, dz);
        this.m = m;
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy Used to initialise {@link #dy}.
     * @param dz Used to initialise {@link #dz}.
     */
    public V3D_Vector(Math_BigRationalSqrt dx, BigRational dy, BigRational dz) {
        this.dx = dx;
        this.dy = new Math_BigRationalSqrt(dy.pow(2), dy);
        this.dz = new Math_BigRationalSqrt(dz.pow(2), dz);
    }

    /**
     * @param dx Used to initialise {@link #dx}.
     * @param dy What {@link #dy} is set to.
     * @param dz Used to initialise {@link #dz}.
     */
    public V3D_Vector(BigRational dx, Math_BigRationalSqrt dy, BigRational dz) {
        this.dx = new Math_BigRationalSqrt(dx.pow(2), dx);
        this.dy = dy;
        this.dz = new Math_BigRationalSqrt(dz.pow(2), dz);
    }

    /**
     * @param dx Used to initialise {@link #dx}.
     * @param dy Used to initialise {@link #dy}.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_Vector(BigRational dx, BigRational dy,
            Math_BigRationalSqrt dz) {
        this.dx = new Math_BigRationalSqrt(dx.pow(2), dx);
        this.dy = new Math_BigRationalSqrt(dy.pow(2), dy);
        this.dz = dz;
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz Used to initialise {@link #dz}.
     */
    public V3D_Vector(Math_BigRationalSqrt dx, Math_BigRationalSqrt dy,
            BigRational dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = new Math_BigRationalSqrt(dz.pow(2), dz);
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy Used to initialise {@link #dy}.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_Vector(Math_BigRationalSqrt dx, BigRational dy,
            Math_BigRationalSqrt dz) {
        this.dx = dx;
        this.dy = new Math_BigRationalSqrt(dy.pow(2), dy);
        this.dz = dz;
    }

    /**
     * @param dx Used to initialise {@link #dx}.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_Vector(BigRational dx, Math_BigRationalSqrt dy,
            Math_BigRationalSqrt dz) {
        this.dx = new Math_BigRationalSqrt(dx.pow(2), dx);
        this.dy = dy;
        this.dz = dz;
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_Vector(long dx, long dy, long dz) {
        this(BigRational.valueOf(dx),
                BigRational.valueOf(dy),
                BigRational.valueOf(dz));
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_Vector(double dx, double dy, double dz) {
        this(BigRational.valueOf(dx),
                BigRational.valueOf(dy),
                BigRational.valueOf(dz));
    }

    /**
     * Creates a vector from {@code p} to {@code q}.
     *
     * @param p the point where the vector starts.
     * @param q the point where the vector ends.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     */
    public V3D_Vector(V3D_Point p, int oom, RoundingMode rm) {
        this(p.getVector(oom, rm));
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof V3D_Vector v3D_Vector) {
            return equals(v3D_Vector);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.dx);
        hash = 89 * hash + Objects.hashCode(this.dy);
        hash = 89 * hash + Objects.hashCode(this.dz);
        return hash;
    }

    /**
     * Indicates if {@code this} and {@code v} are equal.
     *
     * @param v The vector to test for equality with {@code this}.
     * @return {@code true} iff {@code this} is the same as {@code v}.
     */
    public boolean equals(V3D_Vector v) {
        return dx.compareTo(v.dx) == 0
                && dy.compareTo(v.dy) == 0
                && dz.compareTo(v.dz) == 0;
    }

    /**
     * Indicates if {@code this} and {@code v} are equal at the oom precision
     * using the RoundingMode rm.
     *
     * @param v The vector to test for equality with {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return {@code true} iff {@code this} is the same as {@code v}.
     */
    public boolean equals(V3D_Vector v, int oom, RoundingMode rm) {
        if (getDX(oom, rm).compareTo(v.getDX(oom, rm)) == 0) {
            if (getDY(oom, rm).compareTo(v.getDY(oom, rm)) == 0) {
                if (getDZ(oom, rm).compareTo(v.getDZ(oom, rm)) == 0) {
                    return true;
                }
            }
        }
        return false;
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
    public boolean isZero() {
        return this.equals(ZERO);
    }

    /**
     * @return {@code true} if {@code this.equals(e.zeroVector)}
     */
    public boolean isZero(int oom, RoundingMode rm) {
        return this.equals(ZERO, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return The value of {@link #dx} as a BigRational.
     */
    public BigRational getDX(int oom, RoundingMode rm) {
        return dx.getSqrt(oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return The value of {@link #dy} as a BigRational.
     */
    public BigRational getDY(int oom, RoundingMode rm) {
        return dy.getSqrt(oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return The value of {@link #dz} as a BigRational.
     */
    public BigRational getDZ(int oom, RoundingMode rm) {
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return Scaled vector.
     */
    public V3D_Vector multiply(long s, int oom, RoundingMode rm) {
        return multiply(BigRational.valueOf(s), oom, rm);
    }

    /**
     * @param s The scalar value to multiply this by.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return Scaled vector.
     */
    public V3D_Vector multiply(BigRational s, int oom, RoundingMode rm) {
        return new V3D_Vector(
                getDX(oom, rm).multiply(s),
                getDY(oom, rm).multiply(s),
                getDZ(oom, rm).multiply(s));
    }

    /**
     * @param s The scalar value to divide this by.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return Scaled vector.
     */
    public V3D_Vector divide(BigRational s, int oom, RoundingMode rm) {
        return new V3D_Vector(
                getDX(oom, rm).divide(s),
                getDY(oom, rm).divide(s),
                getDZ(oom, rm).divide(s));
    }

    /**
     * Add/apply/translate.
     *
     * @param v The vector to add.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A new vector which is {@code this} add {@code v}.
     */
    public V3D_Vector add(V3D_Vector v, int oom, RoundingMode rm) {
        return new V3D_Vector(
                getDX(oom, rm).add(v.getDX(oom, rm)),
                getDY(oom, rm).add(v.getDY(oom, rm)),
                getDZ(oom, rm).add(v.getDZ(oom, rm)));
    }

    /**
     * @param v The vector to subtract.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A new vector which is {@code this} minus {@code v}.
     */
    public V3D_Vector subtract(V3D_Vector v, int oom, RoundingMode rm) {
        return new V3D_Vector(
                getDX(oom, rm).subtract(v.getDX(oom, rm)),
                getDY(oom, rm).subtract(v.getDY(oom, rm)),
                getDZ(oom, rm).subtract(v.getDZ(oom, rm)));
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
    public BigRational getDotProduct(V3D_Vector v, int oom,
            RoundingMode rm) {
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
    public BigRational getMagnitudeSquared() {
        return getMagnitude().getX();
    }

    /**
     * @return The magnitude of m.
     */
    private Math_BigRationalSqrt getMagnitude() {
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
     * Returns the magnitude of m to at least oom precision.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The magnitude of m.
     */
    protected Math_BigRationalSqrt getMagnitude(int oom, RoundingMode rm) {
        if (m == null) {
            initM(oom, rm);
        } else {
            if (oom < m.getOom()) {
                initM(oom, rm);
            } else {
                if (m.getRoundingMode().equals(rm)) {
                    return m;
                }
                initM(oom, rm);
            }
        }
        return m;
    }

    /**
     * Test if {@code v} is a scalar multiple of {@code this}.
     *
     * @param v The vector to test if it is a scalar multiple of {@code this}.
     * @return {@code true} if {@code this} and {@code v} are scalar multiples.
     */
    public boolean isScalarMultiple(V3D_Vector v) {
        if (equals(v)) {
            return true;
        } else {
            // Special cases
            boolean isZero = isZero();
            if (isZero) {
                /**
                 * Can't multiply the zero vector by a scalar to get a non-zero
                 * vector.
                 */
                return v.isZero();
            }
            if (v.isZero()) {
                /**
                 * Already tested that this is not equal to v, so the scalar is
                 * zero.
                 */
                return true;
            }

            int oom = -3;
            RoundingMode rm = RoundingMode.HALF_UP;

            /**
             * General case: A little complicated as there is a need to deal
             * with zero vector components and cases where the vectors point in
             * different directions.
             */
            if (v.dx.abs().compareTo(dx.abs()) == 0) {
                // |dx| = |v.dx|
                if (v.dx.isZero()) {
                    // dx = v.dx = 0d
                    if (v.dy.abs().compareTo(dy.abs()) == 0) {
                        if (v.dy.isZero()) {
                            return true;
                        } else {
                            if (v.dz.abs().compareTo(dz.abs()) == 0) {
//                                    if (v.dz.isZero()) { 
//                                        // This should not happen as it is already tested for. Commented code left for clarity.
//                                        return true;
//                                    } else {

                                Math_BigRationalSqrt scalar = v.dy.divide(dy, oom - 6, rm);
                                Math_BigRationalSqrt dzs = dz.multiply(scalar, oom - 3, rm);
                                return v.dz.equals(dzs, oom);
//                                    }
                            } else {
                                return false;
                            }
                        }
                    } else {
                        if (v.dy.isZero()) {
                            return v.dz.isZero();
                        } else {
                            if (v.dz.abs().compareTo(dz.abs()) == 0) {
                                if (v.dz.isZero()) {
                                    return true;
                                } else {
                                    Math_BigRationalSqrt scalar = v.dy.divide(dy, oom - 6, rm);
                                    Math_BigRationalSqrt dzs = dz.multiply(scalar, oom - 3, rm);
                                    return v.dz.equals(dzs, oom);
                                }
                            } else {
                                Math_BigRationalSqrt scalar = v.dy.divide(dy, oom - 6, rm);
                                Math_BigRationalSqrt dzs = dz.multiply(scalar, oom - 3, rm);
                                return v.dz.equals(dzs, oom);
                            }
                        }
                    }
                } else {
                    // |dx| = |v.dx| != 0d
                    if (v.dy.abs().compareTo(dy.abs()) == 0) {
                        if (v.dy.isZero()) {
                            if (v.dz.abs().compareTo(dz.abs()) == 0) {
                                if (v.dz.isZero()) {
                                    return true;
                                } else {
                                    Math_BigRationalSqrt scalar = v.dx.divide(dx, oom - 6, rm);
                                    Math_BigRationalSqrt dzs = dz.multiply(scalar, oom - 3, rm);
                                    return v.dz.equals(dzs, oom);
                                }
                            } else {
                                Math_BigRationalSqrt scalar = v.dx.divide(dx, oom - 6, rm);
                                Math_BigRationalSqrt dzs = dz.multiply(scalar, oom - 3, rm);
                                return v.dz.equals(dzs, oom);
                            }
                        } else {
                            Math_BigRationalSqrt scalar = v.dx.divide(dx, oom - 6, rm);
                            Math_BigRationalSqrt dys = dy.multiply(scalar, oom - 3, rm);
                            if (v.dy.equals(dys, oom)) {
                                if (v.dz.abs().compareTo(dz.abs()) == 0) {
                                    if (v.dz.isZero()) {
                                        return true;
                                    } else {
                                        Math_BigRationalSqrt dzs = dz.multiply(scalar, oom - 3, rm);
                                        return v.dz.equals(dzs, oom);
                                    }
                                } else {
                                    Math_BigRationalSqrt dzs = dz.multiply(scalar, oom - 3, rm);
                                    return v.dz.equals(dzs, oom);
                                }
                            } else {
                                return false;
                            }
                        }
                    } else {
                        // |dx| = |v.dx| != 0d, |dy| != |v.dy|
                        Math_BigRationalSqrt scalar = v.dx.divide(dx, oom - 6, rm);
                        Math_BigRationalSqrt dys = dy.multiply(scalar, oom - 3, rm);
                        if (v.dy.equals(dys, oom)) {
                            Math_BigRationalSqrt dzs = dz.multiply(scalar, oom - 3, rm);
                            return v.dz.equals(dzs, oom);
                        } else {
                            return false;
                        }
                    }
                }
            } else {
                // |dx| != |v.dx|
                if (dx.isZero()) {
                    return isZero;
                } else {
                    Math_BigRationalSqrt scalar = v.dx.divide(dx, oom - 6, rm);
                    Math_BigRationalSqrt dys = dy.multiply(scalar, oom - 3, rm);
                    if (v.dy.equals(dys, oom)) {
                        Math_BigRationalSqrt dzs = dz.multiply(scalar, oom - 3, rm);
                        return v.dz.equals(dzs, oom);
                    } else {
                        return false;
                    }
                }
            }
        }
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
        if (equals(v, oom, rm)) {
            return true;
        } else {
            // Special cases
            boolean isZero = isZero(oom, rm);
            boolean visZero = v.isZero(oom, rm);
            if (isZero) {
                /**
                 * Can't multiply the zero vector by a scalar to get a non-zero
                 * vector.
                 */
                return visZero;
            }
            if (visZero) {
                /**
                 * Already tested that this is not equal to v, so the scalar is
                 * zero.
                 */
                return true;
            }
            //if (this.getDotProduct(v, oom, rm).compareTo(BigRational.ZERO) == 0) {
            //    return false;
            //}
            /**
             * General case: A little complicated as there is a need to deal
             * with zero vector components and cases where the vectors point in
             * different directions.
             */
            if (v.dx.abs().equals(dx.abs(), oom)) {
                // dx = v.dx
                if (dx.isZero(oom)) {
                    // dx = v.dx = 0d
                    if (v.dy.abs().equals(dy.abs(), oom)) {
                        if (dy.isZero(oom)) {
                            return true;
                            //return !dz.isZero(oom);
                        } else {
                            if (v.dz.abs().equals(dz.abs(), oom)) {
//                                    if (v.dz.isZero(oom)) {
//                                        // This should not happen as it is already tested for. Commented code left for clarity.
//                                        return true;
//                                    } else {
                                Math_BigRationalSqrt scalar = v.dy.divide(dy, oom - 6, rm);
                                return v.dz.equals(dz.multiply(scalar, oom - 3, rm), oom);
//                                    }
                            } else {
                                return false;
                            }
                        }
                    } else {
                        if (dy.isZero(oom)) {
                            return dz.isZero(oom);
                        } else {
                            if (v.dz.abs().equals(dz.abs(), oom)) {
                                if (v.dz.isZero(oom)) {
                                    return true;
                                } else {
                                    Math_BigRationalSqrt scalar = v.dy.divide(dy, oom - 6, rm);
                                    return v.dz.equals(dz.multiply(scalar, oom - 3, rm), oom);
                                }
                            } else {
                                Math_BigRationalSqrt scalar = v.dy.divide(dy, oom - 3, rm);
                                return v.dz.equals(dz.multiply(scalar, oom - 3, rm));
                            }
                        }
                    }
                } else {
                    // |dx| = |v.dx| != 0d
                    Math_BigRationalSqrt scalar = v.dx.divide(dx, oom - 6, rm);
                    if (v.dy.abs().equals(dy.abs(), oom)) {
                        if (dy.isZero(oom)) {
                            if (v.dz.abs().equals(dz.abs(), oom)) {
                                if (v.dz.isZero(oom)) {
                                    return true;
                                } else {
                                    return v.dz.equals(dz.multiply(scalar, oom - 3, rm), oom);
                                }
                            } else {
                                return v.dz.equals(dz.multiply(scalar, oom - 3, rm), oom);
                            }
                        } else {
                            if (v.dy.equals(dy.multiply(scalar, oom - 3, rm))) {
                                if (v.dz.abs().equals(dz.abs(), oom)) {
                                    if (v.dz.isZero(oom)) {
                                        return true;
                                    } else {
                                        return v.dz.equals(dz.multiply(scalar, oom - 3, rm), oom);
                                    }
                                } else {
                                    return v.dz.equals(dz.multiply(scalar, oom - 3, rm), oom);
                                }
                            } else {
                                return false;
                            }
                        }
                    } else {
                        // |dx| = |v.dx| != 0d, |dy| != |v.dy|
                        if (v.dy.equals(dy.multiply(scalar, oom - 3, rm))) {
                            return v.dz.equals(dz.multiply(scalar, oom - 3, rm), oom);
                        } else {
                            return false;
                        }
                    }
                }
            } else {
                // |dx| != |v.dx|
                if (dx.isZero(oom)) {
                    return isZero;
                } else {
                    Math_BigRationalSqrt scalar = v.dx.divide(dx, oom - 6, rm);
                    if (v.dy.equals(dy.multiply(scalar, oom - 3, rm), oom)) {
                        return v.dz.equals(dz.multiply(scalar, oom - 3, rm), oom);
                    } else {
                        return false;
                    }
                }
            }
        }
// Previous way
//        // Special case
//        if (this.isZero() || v.isZero()) {
//            return false;
//        }
//        return this.multiply(getDotProduct(v, oom, rm), oom, rm).equals(
//                v.multiply(getDotProduct(this, oom, rm), oom, rm));
// Old way
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
//                                BigRational sy = getDY(oom, rm)
//                                        .divide(v.getDY(oom, rm));
//                                BigRational sz = getDZ(oom, rm)
//                                        .divide(v.getDZ(oom, rm));
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
//                                BigRational sy = getDY(oom, rm)
//                                        .divide(v.getDY(oom, rm));
//                                BigRational sz = getDZ(oom, rm)
//                                        .divide(v.getDZ(oom, rm));
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
//                                BigRational sx = getDX(oom, rm)
//                                        .divide(v.getDX(oom, rm));
//                                BigRational sz = getDZ(oom, rm)
//                                        .divide(v.getDZ(oom, rm));
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
//                                BigRational sx = getDX(oom, rm)
//                                        .divide(v.getDX(oom, rm));
//                                BigRational sy = getDY(oom, rm)
//                                        .divide(v.getDY(oom, rm));
//                                return sx.compareTo(sy) == 0;
//                            } else {
//                                return false;
//                            }
//                        } else {
//                            if (v.dz.isZero()) {
//                                return false;
//                            } else {
//                                BigRational sx = getDX(oom, rm)
//                                        .divide(v.getDX(oom, rm));
//                                BigRational sy = getDY(oom, rm)
//                                        .divide(v.getDY(oom, rm));
//                                if (sx.compareTo(sy) == 0) {
//                                    BigRational sz = getDZ(oom, rm)
//                                            .divide(v.getDZ(oom, rm));
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
    public BigRational getAngle(V3D_Vector v, int oom, RoundingMode rm) {
        int oomn2 = oom - 2;
        BigRational dp = getDotProduct(v, oomn2, rm);
        BigRational mag = getMagnitude(oomn2, rm).getSqrt(oomn2, rm);
        BigRational vmag = v.getMagnitude(oomn2, rm).getSqrt(oomn2, rm);
        MathContext mc = new MathContext(1 - oom); // This needs checking!
        return BigRational.valueOf(BigDecimalMath.acos(
                dp.divide(mag.multiply(vmag)).toBigDecimal(mc), mc));
    }

    /**
     * Calculate and return {@code #this} rotated using the parameters. (see
     * Doug (https://math.stackexchange.com/users/102399/doug), How do you
     * rotate a vector by a unit quaternion?, URL (version: 2019-06-12):
     * https://math.stackexchange.com/q/535223)
     *
     * @param axis The axis of rotation. This should be a unit vector accurate
     * to a sufficient precision.
     * @param theta The angle of rotation.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The vector which is {@code #this} rotated using the parameters.
     */
    public V3D_Vector rotate(V3D_Vector axis, Math_AngleBigRational ma, BigRational theta,
            int oom, RoundingMode rm) {
        BigRational na = ma.normalise(theta, oom, rm);
        if (na.compareTo(BigRational.ZERO) == 1) {
            int oomn2 = oom - 6;
            BigRational adx = axis.getDX(oomn2, rm);
            BigRational ady = axis.getDY(oomn2, rm);
            BigRational adz = axis.getDZ(oomn2, rm);
            BigRational thetaDiv2 = na.divide(2);
            BigRational sinThetaDiv2 = Math_BigRational.sin(thetaDiv2,
                    V3D_Environment.bd.getBi(), oomn2, rm);
            BigRational w = Math_BigRational.cos(thetaDiv2,
                    V3D_Environment.bd.getBi(), oomn2, rm);
            BigRational x = sinThetaDiv2.multiply(adx);
            BigRational y = sinThetaDiv2.multiply(ady);
            BigRational z = sinThetaDiv2.multiply(adz);
            Math_Quaternion_BigRational r = new Math_Quaternion_BigRational(
                    w, x, y, z);
            // R'=rR
            Math_Quaternion_BigRational rR = new Math_Quaternion_BigRational(
                    w, x.negate(), y.negate(), z.negate());
            Math_Quaternion_BigRational p = new Math_Quaternion_BigRational(
                    BigRational.ZERO, this.getDX(oomn2, rm),
                    this.getDY(oomn2, rm), this.getDZ(oomn2, rm));
            // P'=pP
            Math_Quaternion_BigRational pP = r.multiply(p).multiply(rR);
            return new V3D_Vector(pP.x, pP.y, pP.z);
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
        return new V3D_Vector(
                dy.multiply(v.dz, oom, rm).getSqrt(oom, rm)
                        .subtract(dz.multiply(v.dy, oom, rm).getSqrt(oom, rm)),
                dz.multiply(v.dx, oom, rm).getSqrt(oom, rm)
                        .subtract(dx.multiply(v.dz, oom, rm).getSqrt(oom, rm)),
                dx.multiply(v.dy, oom, rm).getSqrt(oom, rm)
                        .subtract(dy.multiply(v.dx, oom, rm).getSqrt(oom, rm)));
    }

    /**
     * Scales by {@link #m} to give a unit vector with length 1. Six further
     * orders of magnitude are used to produce the result.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return this scaled by {@link #m}.
     */
    public V3D_Vector getUnitVector(int oom, RoundingMode rm) {
        BigRational d = getMagnitude(oom, rm).getSqrt(oom, rm);
//        return new V3D_Vector(
//                dx.getSqrt(oom).divide(d),
//                dy.getSqrt(oom).divide(d),
//                dz.getSqrt(oom).divide(d), oom);
//        /**
//         * Force the magnitude to be equal to one.
//         */
//        return new V3D_Vector(
//                getDX(oomn6, rm).divide(d),
//                getDY(oomn6, rm).divide(d),
//                getDZ(oomn6, rm).divide(d), Math_BigRationalSqrt.ONE);
        return new V3D_Vector(
                getDX(oom, rm).divide(d),
                getDY(oom, rm).divide(d),
                getDZ(oom, rm).divide(d), Math_BigRationalSqrt.ONE);
    }

    /**
     * The unit vector direction is given as being towards the point.
     *
     * @param pt The point controlling the direction of the unit vector.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return this scaled by {@link #m}.
     */
    public V3D_Vector getUnitVector(V3D_Point pt, int oom, RoundingMode rm) {
        int direction = (getDotProduct(pt.getVector(oom, rm), oom, rm)
                .divide(getDotProduct(this, oom, rm)))
                .compareTo(BigRational.ZERO);
        V3D_Vector r = getUnitVector(oom, rm);
        if (direction == -1) {
            r = r.reverse();
        }
        return r;
    }

    /**
     * @return The direction of the vector:
     * <Table>
     * <caption>Directions</caption>
     * <thead>
     * <tr><td>ID</td><td>Description (P is positive, N is Negative)</td></tr>
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
