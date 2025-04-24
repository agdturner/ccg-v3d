/*
 * Copyright 2020-2025 Andy Turner, University of Leeds.
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
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
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
     * The change in x as a BigRational.
     */
    protected BigRational dxbr;

    /**
     * The Order of Magnitude of the precision of {@link #dxbr}.
     */
    protected int dxbr_oom;

    /**
     * The RoundingMode of any rounding in the calculation of {@link #dxbr}.
     */
    protected RoundingMode dxbr_rm;

    /**
     * The change in y.
     */
    protected final Math_BigRationalSqrt dy;

    /**
     * The change in x as a BigRational.
     */
    protected BigRational dybr;

    /**
     * The Order of Magnitude of the precision of {@link #dybr}.
     */
    protected int dybr_oom;

    /**
     * The RoundingMode of any rounding in the calculation of {@link #dybr}.
     */
    protected RoundingMode dybr_rm;

    /**
     * The change in z.
     */
    protected final Math_BigRationalSqrt dz;

    /**
     * The change in x as a BigRational.
     */
    protected BigRational dzbr;

    /**
     * The Order of Magnitude of the precision of {@link #dzbr}.
     */
    protected int dzbr_oom;

    /**
     * The RoundingMode of any rounding in the calculation of {@link #dzbr}.
     */
    protected RoundingMode dzbr_rm;

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
     * The NI vector {@code <-1,0,0>}.
     */
    public static final V3D_Vector NI = new V3D_Vector(-1, 0, 0);

    /**
     * The NJ vector {@code <0,-1,0>}.
     */
    public static final V3D_Vector NJ = new V3D_Vector(0, -1, 0);

    /**
     * The NK vector {@code <0,0,-1>}.
     */
    public static final V3D_Vector NK = new V3D_Vector(0, 0, -1);

    /**
     * The IJ vector {@code <1,1,0>}.
     */
    public static final V3D_Vector IJ = new V3D_Vector(1, 1, 0);

    /**
     * The IK vector {@code <1,0,1>}.
     */
    public static final V3D_Vector IK = new V3D_Vector(1, 0, 1);

    /**
     * The JK vector {@code <0,1,1>}.
     */
    public static final V3D_Vector JK = new V3D_Vector(0, 1, 1);

    /**
     * The INK vector {@code <1,0,-1>}.
     */
    public static final V3D_Vector INK = new V3D_Vector(1, 0, -1);

    /**
     * The INJ vector {@code <1,-1,0>}.
     */
    public static final V3D_Vector INJ = new V3D_Vector(1, -1, 0);

    /**
     * The JNK vector {@code <0,1,-1>}.
     */
    public static final V3D_Vector JNK = new V3D_Vector(0, 1, -1);

    /**
     * The NJK vector {@code <0,-1,1>}.
     */
    public static final V3D_Vector NJK = new V3D_Vector(0, -1, 1);

    /**
     * The NIJ vector {@code <-1,1,0>}.
     */
    public static final V3D_Vector NIJ = new V3D_Vector(-1, 1, 0);

    /**
     * The NIK vector {@code <-1,0,1>}.
     */
    public static final V3D_Vector NIK = new V3D_Vector(-1, 0, 1);

    /**
     * The NJNK vector {@code <0,-1,-1>}.
     */
    public static final V3D_Vector NJNK = new V3D_Vector(0, -1, -1);

    /**
     * The NINK vector {@code <-1,0,-1>}.
     */
    public static final V3D_Vector NINK = new V3D_Vector(-1, 0, -1);

    /**
     * The NINJ vector {@code <-1,-1,0>}.
     */
    public static final V3D_Vector NINJ = new V3D_Vector(-1, -1, 0);

    /**
     * The IJK vector {@code <1,1,1>} where:
     */
    public static final V3D_Vector IJK = new V3D_Vector(1, 1, 1);

    /**
     * The IJNK vector {@code <1,1,-1>} where:
     */
    public static final V3D_Vector IJNK = new V3D_Vector(1, 1, -1);

    /**
     * The INJK vector {@code <1,-1,1>} where:
     */
    public static final V3D_Vector INJK = new V3D_Vector(1, -1, 1);

    /**
     * The NIJK vector {@code <-1,1,1>} where:
     */
    public static final V3D_Vector NIJK = new V3D_Vector(-1, 1, 1);

    /**
     * The INJNK vector {@code <1,-1,-1>} where:
     */
    public static final V3D_Vector INJNK = new V3D_Vector(1, -1, -1);

    /**
     * The NIJNK vector {@code <-1,1,-1>} where:
     */
    public static final V3D_Vector NIJNK = new V3D_Vector(-1, 1, -1);

    /**
     * The NINJK vector {@code <-1,-1,1>} where:
     */
    public static final V3D_Vector NINJK = new V3D_Vector(-1, -1, 1);

    /**
     * The NINJNK vector {@code <-1,-1,-1>} where:
     */
    public static final V3D_Vector NINJNK = new V3D_Vector(-1, -1, -1);

    /**
     * Create a new zero vector.
     */
    public V3D_Vector() {
        this(Math_BigRationalSqrt.ZERO, Math_BigRationalSqrt.ZERO,
                Math_BigRationalSqrt.ZERO);
    }

    /**
     * Create a new instance.
     *
     * @param v Used to initialise this. A deep copy of all components is made
     * so that {@code this} is completely independent of {@code v}.
     */
    public V3D_Vector(V3D_Vector v) {
        this(v.dx, v.dy, v.dz);
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
        this(
                new Math_BigRationalSqrt(dx.pow(2), dx),
                new Math_BigRationalSqrt(dy.pow(2), dy),
                new Math_BigRationalSqrt(dz.pow(2), dz));
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
     * Indicates if {@code this} is the reverse of {@code v}.
     *
     * @param v The vector to compare with {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return {@code true} iff {@code this} is the reverse of {@code v}.
     */
    public boolean isReverse(V3D_Vector v, int oom, RoundingMode rm) {
        return equals(v.reverse(), oom, rm);
    }

    /**
     * @return {@code true} if {@code this.equals(e.zeroVector)}
     */
    public boolean isZero() {
        return this.equals(ZERO);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this.equals(e.zeroVector)}
     */
    public boolean isZero(int oom, RoundingMode rm) {
        return this.equals(ZERO, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The value of {@link #dx} as a BigRational.
     */
    public BigRational getDX(int oom, RoundingMode rm) {
        if (dxbr == null) {
            dxbr_oom = oom;
            dxbr_rm = rm;
            dxbr = dx.getSqrt(oom, rm);
        } else {
            if (dxbr_oom >= oom) {
                if (dxbr_oom == oom) { 
                    if (!dxbr_rm.equals(rm)) {
                        dxbr_rm = rm;
                        dxbr = dx.getSqrt(oom, rm);
                    }
                } else {
                    dxbr_oom = oom;
                    dxbr_rm = rm;
                    dxbr = dx.getSqrt(oom, rm);
                }
            }
        }
        return dxbr;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return The value of {@link #dy} as a BigRational.
     */
    public BigRational getDY(int oom, RoundingMode rm) {
        if (dybr == null) {
            dybr_oom = oom;
            dybr_rm = rm;
            dybr = dy.getSqrt(oom, rm);
        } else {
            if (dybr_oom >= oom) {
                if (dybr_oom == oom) { 
                    if (!dybr_rm.equals(rm)) {
                        dybr_rm = rm;
                        dybr = dy.getSqrt(oom, rm);
                    }
                } else {
                    dybr_oom = oom;
                    dybr_rm = rm;
                    dybr = dy.getSqrt(oom, rm);
                }
            }
        }
        return dybr;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return The value of {@link #dz} as a BigRational.
     */
    public BigRational getDZ(int oom, RoundingMode rm) {
        if (dzbr == null) {
            dzbr_oom = oom;
            dzbr_rm = rm;
            dzbr = dz.getSqrt(oom, rm);
        } else {
            if (dzbr_oom >= oom) {
                if (dzbr_oom == oom) { 
                    if (!dzbr_rm.equals(rm)) {
                        dzbr_rm = rm;
                        dzbr = dz.getSqrt(oom, rm);
                    }
                } else {
                    dzbr_oom = oom;
                    dzbr_rm = rm;
                    dzbr = dz.getSqrt(oom, rm);
                }
            }
        }
        return dzbr;
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} and {@code v} are scalar multiples.
     */
    public boolean isScalarMultiple(V3D_Vector v, int oom, RoundingMode rm) {
        if (equals(v, oom, rm)) {
            return true;
        } else {
            // Special cases
            boolean visZero = v.isZero(oom, rm);
            if (visZero) {
                /**
                 * Already tested that this is not equal to v, so the scalar is
                 * zero.
                 */
                return true;
            }
            boolean isZero = isZero(oom, rm);
            if (isZero) {
                /**
                 * Can't multiply the zero vector by a scalar to get a non-zero
                 * vector.
                 */
                return visZero;
            }
            /**
             * General case: A little complicated as there is a need to deal
             * with: zero vector components; cases where the vectors point in
             * different directions; and, precision issues.
             */
            if (v.dx.abs().equals(dx.abs(), oom)) {
                // |v.dx| = |dx|
                if (dx.isZero()) {
                    if (v.dx.isZero(oom * 2)) {
                        // dx = 0d
                        if (v.dy.abs().equals(dy.abs(), oom)) {
                            // |v.dy| = |dy|
                            if (dy.isZero()) {
                                // dy = 0
                                // dz != 0 and can have any non-zero value.
                                return true;
                            } else {
                                if (v.dz.abs().equals(dz.abs(), oom)) {
                                    // |v.dz| = |dz|
                                    // scalar = 1 or -1
                                    Math_BigRationalSqrt scalar = v.dy.divide(dy, oom, rm);
                                    return v.dz.equals(dz.multiply(scalar, oom, rm), oom);
                                } else {
                                    return false;
                                }
                            }
                        } else {
                            // |v.dy| != |dy|
                            if (dy.isZero()) {
                                // dy = 0
                                return dz.isZero();
                            } else {
                                // Divide bigger by smaller number for precision reasons.
                                if (v.dy.abs().compareTo(dy.abs()) == 1) {
                                    // dy != 0
                                    Math_BigRationalSqrt scalar = v.dy.divide(dy, oom, rm);
                                    return v.dz.equals(dz.multiply(scalar, oom, rm), oom);
                                } else {
                                    if (v.dy.isZero()) {
                                        return false;
                                    } else {
                                        Math_BigRationalSqrt scalar = dy.divide(v.dy, oom, rm);
                                        return dz.equals(v.dz.multiply(scalar, oom, rm), oom);
                                    }
                                }
                            }
                        }
                    } else {
                        return false;
                    }
                } else {
                    // |v.dx| = |dx| != 0d
                    // scalar will be 1 or -1
                    Math_BigRationalSqrt scalar = v.dx.divide(dx, oom, rm);
                    if (v.dy.abs().equals(dy.abs(), oom)) {
                        // |v.dy| = |dy|
                        if (dy.isZero()) {
                            // dy = 0
                            if (v.dz.abs().equals(dz.abs(), oom)) {
                                // |v.dz| = |dz|
                                if (v.dz.isZero(oom * 2)) {
                                    return true;
                                } else {
                                    return v.dz.equals(dz.multiply(scalar, oom, rm), oom);
                                }
                            } else {
                                // |v.dz| != |dz|
                                return v.dz.equals(dz.multiply(scalar, oom, rm), oom);
                            }
                        } else {
                            if (v.dy.equals(dy.multiply(scalar, oom, rm))) {
                                if (v.dz.abs().equals(dz.abs(), oom)) {
                                    // |v.dz| = |dz|
                                    if (v.dz.isZero(oom)) {
                                        return true;
                                    } else {
                                        return v.dz.equals(dz.multiply(scalar, oom, rm), oom);
                                    }
                                } else {
                                    // |v.dz| != |dz|
                                    return v.dz.equals(dz.multiply(scalar, oom, rm), oom);
                                }
                            } else {
                                return false;
                            }
                        }
                    } else {
                        // |v.dx| = |dx| != 0d, |v.dy| != |dy|
                        if (v.dy.equals(dy.multiply(scalar, oom, rm))) {
                            return v.dz.equals(dz.multiply(scalar, oom, rm), oom);
                        } else {
                            return false;
                        }
                    }
                }
            } else {
                // |v.dx| != |dx|
                // Divide bigger by smaller number for precision reasons.
                if (v.dx.abs().compareTo(dx.abs()) == 1) {
                    if (dx.isZero()) {
                        // dx = 0
                        return isZero;
                    } else {
                        Math_BigRationalSqrt scalar = v.dx.divide(dx, oom, rm);
                        if (v.dy.equals(dy.multiply(scalar, oom, rm), oom)) {
                            return v.dz.equals(dz.multiply(scalar, oom, rm), oom);
                        } else {
                            return false;
                        }
                    }
                } else {
                    if (v.dx.isZero(oom * 2)) {
                        // v.dx = 0
                        return isZero;
                    } else {
                        Math_BigRationalSqrt scalar = dx.divide(v.dx, oom, rm);
                        if (dy.equals(v.dy.multiply(scalar, oom, rm), oom)) {
                            return dz.equals(v.dz.multiply(scalar, oom, rm), oom);
                        } else {
                            return false;
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
     * @param uv The rotation unit vector.
     * @param bd Used for an approximation of Pi
     * @param theta The angle of rotation.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The vector which is {@code #this} rotated using the parameters.
     */
    public V3D_Vector rotate(V3D_Vector uv, Math_BigDecimal bd,
            BigRational theta, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V3D_Vector(this);
        } else {
            return rotateN(uv, bd, theta, oom, rm);
        }
    }

    /**
     * Calculate and return {@code #this} rotated using the parameters. (see
     * Doug (https://math.stackexchange.com/users/102399/doug), How do you
     * rotate a vector by a unit quaternion?, URL (version: 2019-06-12):
     * https://math.stackexchange.com/q/535223)
     *
     * @param uv The rotation unit vector.
     * @param bd Used for an approximation of Pi
     * @param theta The angle of rotation between 0 and 2Pi.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The vector which is {@code #this} rotated using the parameters.
     */
    public V3D_Vector rotateN(V3D_Vector uv, Math_BigDecimal bd,
            BigRational theta, int oom, RoundingMode rm) {
        int oomn9 = oom - 9;
        BigRational adx = uv.getDX(oomn9, rm);
        BigRational ady = uv.getDY(oomn9, rm);
        BigRational adz = uv.getDZ(oomn9, rm);
        BigRational thetaDiv2 = theta.divide(2);
        BigRational sinThetaDiv2 = Math_BigRational.sin(thetaDiv2,
                V3D_Environment.bd.getBi(), oomn9, rm);
        BigRational w = Math_BigRational.cos(thetaDiv2,
                V3D_Environment.bd.getBi(), oomn9, rm);
        BigRational x = sinThetaDiv2.multiply(adx);
        BigRational y = sinThetaDiv2.multiply(ady);
        BigRational z = sinThetaDiv2.multiply(adz);
        Math_Quaternion_BigRational r = new Math_Quaternion_BigRational(
                w, x, y, z);
        // R'=rR
        Math_Quaternion_BigRational rR = new Math_Quaternion_BigRational(
                w, x.negate(), y.negate(), z.negate());
        Math_Quaternion_BigRational p = new Math_Quaternion_BigRational(
                BigRational.ZERO, this.getDX(oomn9, rm),
                this.getDY(oomn9, rm), this.getDZ(oomn9, rm));
        // P'=pP
        Math_Quaternion_BigRational pP = r.multiply(p).multiply(rR);
        return new V3D_Vector(pP.x, pP.y, pP.z);
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
     * <tr><td>0</td><td>0, 0, 0</td></tr>
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
                    if (isZero()) {
                        return 0;
                    } else {
                        return 1;
                    }
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
