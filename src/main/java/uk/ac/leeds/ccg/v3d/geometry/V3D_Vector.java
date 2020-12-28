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
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.math.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * Represents a vector or translation that involves a change in the x coordinate
 * {@link #dx}, a change in the y coordinate {@link #dy} and a change in the z
 * coordinate {@link #dz}.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V3D_Vector implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The change in x.
     */
    public final BigRational dx;

    /**
     * The change in y.
     */
    public final BigRational dy;

    /**
     * The change in z.
     */
    public final BigRational dz;

    /**
     * The zero vector {@code <0,0,0>} where
     * {@link #dx} = {@link #dy} = {@link #dz} = 0.
     */
    public static final V3D_Vector ZERO = new V3D_Vector(0, 0, 0);

    /**
     * For storing the magnitude squared and getting the magnitude.
     */
    public final Math_BigRationalSqrt m;

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_Vector(BigRational dx, BigRational dy, BigRational dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        m = new Math_BigRationalSqrt(dx.multiply(dx).add(dy.multiply(dy))
                .add(dz.multiply(dz)));
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_Vector(long dx, long dy, long dz) {
        this(BigRational.valueOf(dx), BigRational.valueOf(dy),
                BigRational.valueOf(dz));
    }

    /**
     * Creates a vector from the origin to {@code p}
     *
     * @param p the point to which the vector starting at the origin goes.
     */
    public V3D_Vector(V3D_Point p) {
        this(p.x, p.y, p.z);
    }

    /**
     * Creates a vector from {@code p} to {@code q}.
     *
     * @param p the point where the vector starts.
     * @param q the point where the vector ends.
     */
    public V3D_Vector(V3D_Point p, V3D_Point q) {
        this(q.x.subtract(p.x), q.y.subtract(p.y), q.z.subtract(p.z));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(dx=" + dx + ", dy=" + dy
                + ", dz=" + dz + ", m=" + m + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Vector) {
            return equals((V3D_Vector) o);
        }
        return false;
    }

    /**
     * Indicates if {@code this} and {@code v} are equal.
     *
     * @param v The vector to compare with {@code this}.
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
        return this.equals(V3D_Environment.V0);
    }

    /**
     * @param s The scalar value to multiply this by.
     * @return Scaled vector.
     */
    public V3D_Vector multiply(BigRational s) {
        return new V3D_Vector(dx.multiply(s), dy.multiply(s), dz.multiply(s));
    }

    /**
     * @param v The vector to add.
     * @return A new vector which is {@code this} add {@code v}.
     */
    public V3D_Vector add(V3D_Vector v) {
        return new V3D_Vector(dx.add(v.dx), dy.add(v.dy), dz.add(v.dz));
    }

    /**
     * @param v The vector to subtract.
     * @return A new vector which is {@code this} minus {@code v}.
     */
    public V3D_Vector subtract(V3D_Vector v) {
        return new V3D_Vector(dx.subtract(v.dx), dy.subtract(v.dy),
                dz.subtract(v.dz));
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
     * @return dot product
     */
    public BigRational getDotProduct(V3D_Vector v) {
        return (v.dx.multiply(this.dx)).add(v.dy.multiply(this.dy))
                .add(v.dz.multiply(this.dz));
    }

    /**
     * Test if this is orthogonal to {@code v}.
     *
     * @param v The
     * @return {@code true} if this and {@code v} are orthogonal.
     */
    public boolean isOrthogonal(V3D_Vector v) {
        return getDotProduct(v).isZero();
    }

    /**
     * @return {@link #m.x}
     */
    public BigRational getMagnitudeSquared() {
        return m.x;
    }
            
    /**
     * Get the m of the vector at the given scale.
     *
     * @param mps The minimum precision scale of the result.
     * @return {@link #m} initialised with {@code scale} and {@code rm}.
     */
    public BigDecimal getMagnitude(int mps) {
        return m.getSqrtApprox(mps);
    }

    /**
     * Test if {@code v} is a scalar multiple of {@code this}.
     *
     * @param v The vector to test if it is a scalar multiple of {@code this}.
     * @return {@code true} if {@code this} and {@code v} are scalar multiples.
     */
    public boolean isScalarMultiple(V3D_Vector v) {
        BigRational s;
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
                            return v.dz.isZero();
                        } else {
                            s = dy.divide(v.dy);
                            return dz.multiply(s).compareTo(v.dz) == 0;
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
                            s = dx.divide(v.dx);
                            return dz.multiply(s).compareTo(v.dz) == 0;
                        }
                    } else {
                        return false;
                    }
                } else {
                    if (v.dy.isZero()) {
                        return false;
                    } else {
                        s = dx.divide(v.dx);
                        if (dy.multiply(s).compareTo(v.dy) == 0) {
                            if (dz.isZero()) {
                                return v.dz.isZero();
                            } else {
                                return dz.multiply(s).compareTo(v.dz) == 0;
                            }
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Cross_product">cross product</A>.
     * Treat this as the first vector and {@code v} as the second vector. The
     * resulting vector is in the direction given by the right hand rule.
     *
     * @param v V3D_Vector
     * @return V3D_Vector
     */
    public V3D_Vector getCrossProduct(V3D_Vector v) {
        return new V3D_Vector(dy.multiply(v.dz).subtract(v.dy.multiply(dz)),
                dz.multiply(v.dx).subtract(v.dz.multiply(dx)),
                dx.multiply(v.dy).subtract(v.dx.multiply(dy)));
    }

    /**
     * Scales the vector by the m so that it has length 1
     *
     * @param scale The scale for the precision of the result.
     * @return this scaled by the m.
     */
    public V3D_Vector getUnitVector(int scale) {
        BigRational d = BigRational.valueOf(m.getSqrtApprox(scale + 2));
        return new V3D_Vector(dx.divide(d), dy.divide(d), dz.divide(d));
    }

    /**
     * @return The points that define the plan as a matrix.
     */
    public Math_Matrix_BR getAsMatrix() {
        Math_Matrix_BR r = new Math_Matrix_BR(1, 3);
        BigRational[][] m = r.getM();
        m[0][0] = dx;
        m[0][1] = dy;
        m[0][2] = dz;
        return r;
    }
}
