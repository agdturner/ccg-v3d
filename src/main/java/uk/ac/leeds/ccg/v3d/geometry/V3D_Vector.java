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
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * Represents a vector or translation that may involve a change in the x
 * coordinate {@link #dx}, a change in the y coordinate {@link #dy} and/or a
 * change in the z coordinate {@link #dz}.
 *
 * @author Andy Turner
 * @version 1.1
 */
public class V3D_Vector implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The change in x.
     */
    public final Math_BigRationalSqrt dx;

    /**
     * The change in y.
     */
    public final Math_BigRationalSqrt dy;

    /**
     * The change in z.
     */
    public final Math_BigRationalSqrt dz;

    /**
     * The zero vector {@code <0,0,0>} where
     * {@link #dx} = {@link #dy} = {@link #dz} = 0.
     */
    public static final V3D_Vector ZERO = new V3D_Vector(0, 0, 0, 0);

    /**
     * For storing the magnitude squared and getting the magnitude.
     */
    public final Math_BigRationalSqrt m;

    /**
     * The Order of Magnitude used for the root calculation of {@link #m}.
     */
    public int oom;

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     * @param oom The Order of Magnitude used for the root calculation used to
     * initialise {@link #m}.
     */
    public V3D_Vector(Math_BigRationalSqrt dx, Math_BigRationalSqrt dy,
            Math_BigRationalSqrt dz, int oom) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        m = new Math_BigRationalSqrt(dx.getX().abs().add(dy.getX().abs())
                .add(dz.getX().abs()), oom);
        this.oom = oom;
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     * @param oom The Order of Magnitude used for the root calculation used to
     * initialise {@link #m}.
     */
    public V3D_Vector(BigRational dx, BigRational dy, BigRational dz,
            int oom) {
        BigRational dx2 = dx.pow(2);
        BigRational dy2 = dy.pow(2);
        BigRational dz2 = dz.pow(2);
        if (dx.compareTo(BigRational.ZERO) == -1) {
            this.dx = new Math_BigRationalSqrt(dx2.negate(), dx, oom);
        } else {
            this.dx = new Math_BigRationalSqrt(dx2, dx, oom);
        }
        if (dy.compareTo(BigRational.ZERO) == -1) {
            this.dy = new Math_BigRationalSqrt(dy2.negate(), dy, oom);
        } else {
            this.dy = new Math_BigRationalSqrt(dy2, dy, oom);
        }
        if (dz.compareTo(BigRational.ZERO) == -1) {
            this.dz = new Math_BigRationalSqrt(dz2.negate(), dz, oom);
        } else {
            this.dz = new Math_BigRationalSqrt(dz2, dz, oom);
        }
        m = new Math_BigRationalSqrt(dx2.add(dy2.add(dz2)), oom);
        this.oom = oom;
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     * @param oom The Order of Magnitude used for the root calculation used to
     * initialise {@link #m}.
     */
    public V3D_Vector(long dx, long dy, long dz, int oom) {
        this(BigRational.valueOf(dx), BigRational.valueOf(dy),
                BigRational.valueOf(dz), oom);
    }

    /**
     * Creates a vector from the origin to {@code p}
     *
     * @param p the point to which the vector starting at the origin goes.
     * @param oom The Order of Magnitude used for the root calculation used to
     * initialise {@link #m}.
     */
    public V3D_Vector(V3D_Point p, int oom) {
        this(p.x, p.y, p.z, oom);
    }

    /**
     * Creates a vector from the origin to {@code p}
     *
     * @param p the point to which the vector starting at the origin goes.
     * @param oom The Order of Magnitude used for the root calculation used to
     * initialise {@link #m}.
     */
    public V3D_Vector(V3D_Envelope.Point p, int oom) {
        this(p.x, p.y, p.z, oom);
    }

    /**
     * Creates a vector from {@code p} to {@code q}.
     *
     * @param p the point where the vector starts.
     * @param q the point where the vector ends.
     * @param oom The Order of Magnitude used for the root calculation used to
     * initialise {@link #m}.
     */
    public V3D_Vector(V3D_Point p, V3D_Point q, int oom) {
        this(q.x.subtract(p.x), q.y.subtract(p.y), q.z.subtract(p.z), oom);
    }

    /**
     * Creates a vector from {@code p} to {@code q}.
     *
     * @param p the point where the vector starts.
     * @param q the point where the vector ends.
     * @param oomi The Order of Magnitude used for the root calculation used to
     * initialise {@link #m}.
     */
    public V3D_Vector(V3D_Envelope.Point p, V3D_Envelope.Point q, int oomi) {
        this(q.x.subtract(p.x), q.y.subtract(p.y), q.z.subtract(p.z), oomi);
    }

    /**
     * Creates a vector from {@code p} to {@code q}.
     *
     * @param p the point where the vector starts.
     * @param q the point where the vector ends.
     * @param oom The Order of Magnitude used for the root calculation used to
     * initialise {@link #m}.
     */
    public V3D_Vector(V3D_Envelope.Point p, V3D_Point q, int oom) {
        this(q.x.subtract(p.x), q.y.subtract(p.y), q.z.subtract(p.z), oom);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "(dx=" + dx
                + ", dy=" + dy
                + ", dz=" + dz
                + ", m=" + m + ")";
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
     * @return The value of {@link #dx} as a BigRational
     */
    public final BigRational getDX() {
        if (dx.negative) {
            return dx.getSqrt().negate();
        } else {
            return dx.getSqrt();
        }
//        return dx.getX();
    }

    /**
     * @return The value of {@link #dy} as a BigRational
     */
    public final BigRational getDY() {
        if (dy.negative) {
            return dy.getSqrt().negate();
        } else {
            return dy.getSqrt();
        }
//        return dy.getX();
    }

    /**
     * @return The value of {@link #dz} as a BigRational
     */
    public final BigRational getDZ() {
        if (dz.negative) {
            return dz.getSqrt().negate();
        } else {
            return dz.getSqrt();
        }
//        return dz.getX();
    }

    /**
     * @param s The scalar value to multiply this by.
     * @return Scaled vector.
     */
    public V3D_Vector multiply(BigRational s) {
        return new V3D_Vector(
                dx.multiply(s),
                dy.multiply(s),
                dz.multiply(s),
                oom);
    }

    /**
     * @param v The vector to add.
     * @return A new vector which is {@code this} add {@code v}.
     */
    public V3D_Vector add(V3D_Vector v) {
        return new V3D_Vector(
                dx.add(v.dx),
                dy.add(v.dy),
                dz.add(v.dz), v.oom);
    }

    /**
     * @param v The vector to subtract.
     * @return A new vector which is {@code this} minus {@code v}.
     */
    public V3D_Vector subtract(V3D_Vector v) {
        return new V3D_Vector(
                getDX().subtract(v.getDX()),
                getDY().subtract(v.getDY()),
                getDZ().subtract(v.getDZ()), v.oom);
    }

    /**
     * @return A new vector which is the opposite to {@code this}.
     */
    public V3D_Vector reverse() {
        return new V3D_Vector(
                getDX().negate(),
                getDY().negate(),
                getDZ().negate(), oom);
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Dot_product">dot product</A>.
     *
     * @param v V3D_Vector
     * @return dot product
     */
    public BigRational getDotProduct(V3D_Vector v) {
        BigRational vdx = v.getDX().abs();
        if (v.dx.negative) {
            vdx = vdx.negate();
        }
        BigRational vdy = v.getDY().abs();
        if (v.dy.negative) {
            vdy = vdy.negate();
        }
        BigRational vdz = v.getDZ().abs();
        if (v.dz.negative) {
            vdz = vdz.negate();
        }
        BigRational tdx = getDX().abs();
        if (dx.negative) {
            tdx = tdx.negate();
        }
        BigRational tdy = getDY().abs();
        if (dy.negative) {
            tdy = tdy.negate();
        }
        BigRational tdz = getDZ().abs();
        if (dz.negative) {
            tdz = tdz.negate();
        }
        return (vdx.multiply(tdx)).add(vdy.multiply(tdy)).add(vdz.multiply(tdz));
//        return (v.dx.multiply(dx)).getSqrt()
//                .add(v.dy.multiply(dy).getSqrt())
//                .add(v.dz.multiply(dz).getSqrt());
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
     * @return The magnitude of the vector squared.
     */
    public BigRational getMagnitudeSquared() {
        return m.getX();
    }

    /**
     * @return The magnitude of m.
     */
    public Math_BigRationalSqrt getMagnitude() {
        return m;
    }

    /**
     * Test if {@code v} is a scalar multiple of {@code this}.
     *
     * @param v The vector to test if it is a scalar multiple of {@code this}.
     * @return {@code true} if {@code this} and {@code v} are scalar multiples.
     */
    public boolean isScalarMultiple(V3D_Vector v) {
        if (getDX().isZero()) {
            if (v.getDX().isZero()) {
                if (getDY().isZero()) {
                    if (v.getDY().isZero()) {
                        if (getDZ().isZero()) {
                            return v.getDZ().isZero();
                        } else {
                            return true;
                        }
                    } else {
                        return false;
                    }
                } else {
                    if (v.getDY().isZero()) {
                        return false;
                    } else {
                        if (getDZ().isZero()) {
                            return v.getDZ().isZero();
                        } else {
                            if (v.getDZ().isZero()) {
                                return false;
                            } else {
                                BigRational sy = getDY().divide(v.getDY());
                                BigRational sz = getDZ().divide(v.getDZ());
                                return sy == sz;
                            }
                        }
                    }
                }
            } else {
                return false;
            }
        } else {
            if (v.getDX().isZero()) {
                return false;
            } else {
                if (getDY().isZero()) {
                    if (v.getDY().isZero()) {
                        if (getDZ().isZero()) {
                            return v.getDZ().isZero();
                        } else {
                            BigRational sx = getDX().divide(v.getDX());
                            BigRational sz = getDZ().divide(v.getDZ());
                            return sx == sz;
                        }
                    } else {
                        return false;
                    }
                } else {
                    if (v.getDY().isZero()) {
                        return false;
                    } else {
                        BigRational sx = getDX().divide(v.getDX());
                        BigRational sy = getDY().divide(v.getDY());
                        BigRational sz = getDZ().divide(v.getDZ());
                        if (sx == sy) {
                            return sy == sz;
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
        return new V3D_Vector(
                getDY().multiply(v.getDZ()).subtract(v.getDY().multiply(getDZ())),
                getDZ().multiply(v.getDX()).subtract(v.getDZ().multiply(getDX())),
                getDX().multiply(v.getDY()).subtract(v.getDX().multiply(getDY())), v.oom);
//                dy.multiply(v.dz).getSqrt().subtract(v.dy.multiply(dz).getSqrt()),
//                dz.multiply(v.dx).getSqrt().subtract(v.dz.multiply(dx).getSqrt()),
//                dx.multiply(v.dy).getSqrt().subtract(v.dx.multiply(dy).getSqrt()), v.oom);
    }

    /**
     * Scales by {@link #m} to give a unit vector with length 1.
     *
     * @param oom The order of magnitude for the precision of the result.
     * @return this scaled by {@link #m}.
     */
    public V3D_Vector getUnitVector(int oom) {
        BigRational d = m.getSqrt();
        if (d == null) {
            d = BigRational.valueOf(m.toBigDecimal(oom));
        }
        return new V3D_Vector(
                dx.getSqrt().divide(d),
                dy.getSqrt().divide(d),
                dz.getSqrt().divide(d), oom);
    }

    /**
     * @return The points that define the plan as a matrix.
     */
    public Math_Matrix_BR getAsMatrix() {
        Math_Matrix_BR r = new Math_Matrix_BR(1, 3);
        BigRational[][] rm = r.getM();
        rm[0][0] = getDX();
        rm[0][1] = getDY();
        rm[0][2] = getDZ();
        return r;
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
        if (getDX().compareTo(BigRational.ZERO) != -1) {
            if (getDY().compareTo(BigRational.ZERO) != -1) {
                if (getDZ().compareTo(BigRational.ZERO) != -1) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (getDZ().compareTo(BigRational.ZERO) != -1) {
                    return 3;
                } else {
                    return 4;
                }
            }
        } else {
            if (getDY().compareTo(BigRational.ZERO) != -1) {
                if (getDZ().compareTo(BigRational.ZERO) != -1) {
                    return 5;
                } else {
                    return 6;
                }
            } else {
                if (getDZ().compareTo(BigRational.ZERO) != -1) {
                    return 7;
                } else {
                    return 8;
                }
            }
        }
    }
}
