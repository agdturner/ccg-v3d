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

import ch.obermuhlner.math.big.BigComplex;
import ch.obermuhlner.math.big.BigComplexMath;
import ch.obermuhlner.math.big.BigDecimalMath;
import java.io.Serializable;
import java.math.MathContext;
import java.util.Objects;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
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
    public V3D_Vector(Math_BigRational dx, Math_BigRational dy, Math_BigRational dz,
            int oom) {
        Math_BigRational dx2 = dx.pow(2);
        Math_BigRational dy2 = dy.pow(2);
        Math_BigRational dz2 = dz.pow(2);
        if (dx.compareTo(Math_BigRational.ZERO) == -1) {
            this.dx = new Math_BigRationalSqrt(dx2.negate(), dx, oom, true);
        } else {
            this.dx = new Math_BigRationalSqrt(dx2, dx, oom, false);
        }
        if (dy.compareTo(Math_BigRational.ZERO) == -1) {
            this.dy = new Math_BigRationalSqrt(dy2.negate(), dy, oom, true);
        } else {
            this.dy = new Math_BigRationalSqrt(dy2, dy, oom, false);
        }
        if (dz.compareTo(Math_BigRational.ZERO) == -1) {
            this.dz = new Math_BigRationalSqrt(dz2.negate(), dz, oom, true);
        } else {
            this.dz = new Math_BigRationalSqrt(dz2, dz, oom, false);
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
        this(Math_BigRational.valueOf(dx), Math_BigRational.valueOf(dy),
                Math_BigRational.valueOf(dz), oom);
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
     * @return The value of {@link #dx} as a Math_BigRational
     */
    public final Math_BigRational getDX() {
        if (dx.negative) {
            return dx.getSqrt().abs().negate();
        } else {
            return dx.getSqrt().abs();
        }
//        return dx.getX();
    }

    /**
     * @return The value of {@link #dy} as a Math_BigRational
     */
    public final Math_BigRational getDY() {
        if (dy.negative) {
            return dy.getSqrt().abs().negate();
        } else {
            return dy.getSqrt().abs();
        }
//        return dy.getX();
    }

    /**
     * @return The value of {@link #dz} as a Math_BigRational
     */
    public final Math_BigRational getDZ() {
        if (dz.negative) {
            return dz.getSqrt().abs().negate();
        } else {
            return dz.getSqrt().abs();
        }
//        return dz.getX();
    }

    /**
     * @param s The scalar value to multiply this by.
     * @return Scaled vector.
     */
    public V3D_Vector multiply(Math_BigRational s) {
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
    public Math_BigRational getDotProduct(V3D_Vector v) {
        return (v.getDX().multiply(getDX()))
                .add(v.getDY().multiply(getDY()))
                .add(v.getDZ().multiply(getDZ()));
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
     * @param v The
     * @return {@code true} if this and {@code v} are orthogonal.
     */
    public boolean isOrthogonal(V3D_Vector v) {
        if (isScalarMultiple(v)) {
            return false;
        }
        return getDotProduct(v).isZero();
    }

    /**
     * @return The magnitude of the vector squared.
     */
    public Math_BigRational getMagnitudeSquared() {
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
                                Math_BigRational sy = getDY().divide(v.getDY());
                                Math_BigRational sz = getDZ().divide(v.getDZ());
                                return sy.compareTo(sz) == 0;
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
                            Math_BigRational sx = getDX().divide(v.getDX());
                            Math_BigRational sz = getDZ().divide(v.getDZ());
                            return sx.compareTo(sz) == 0;
                        }
                    } else {
                        return false;
                    }
                } else {
                    if (v.getDY().isZero()) {
                        return false;
                    } else {
                        if (getDZ().isZero()) {
                            if (v.getDZ().isZero()) {
                                Math_BigRational sx = getDX().divide(v.getDX());
                                Math_BigRational sy = getDY().divide(v.getDY());
                                return sx.compareTo(sy) == 0;
                            } else {
                                return false;
                            }
                        } else {
                            if (v.getDZ().isZero()) {
                                return false;
                            } else {
                                Math_BigRational sx = getDX().divide(v.getDX());
                                Math_BigRational sy = getDY().divide(v.getDY());
                                Math_BigRational sz = getDZ().divide(v.getDZ());
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
     * Find the dot product of the vectors. Divide the dot product with the
     * magnitude of the first vector. Divide the resultant with the magnitude of
     * the second vector.
     *
     * @param v The vector to find the angle between.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The angle in radians between {@code this} and {@code v}.
     */
    public Math_BigRational getAngle(V3D_Vector v, int oom) {
        Math_BigRational dp = getDotProduct(v);
        Math_BigRational m2 = getMagnitude().getSqrt(oom);
        Math_BigRational vm2 = v.getMagnitude().getSqrt(oom);
        MathContext mc = new MathContext(-oom); // This is almost certainly wrong and needs to be checked!
        return Math_BigRational.valueOf(BigDecimalMath.acos(dp.divide(m2.multiply(vm2)).toBigDecimal(mc), mc));

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
                //                getDY().multiply(v.getDZ()).subtract(v.getDY().multiply(getDZ())).negate(),
                //                getDZ().multiply(v.getDX()).subtract(v.getDZ().multiply(getDX())),
                //                getDX().multiply(v.getDY()).subtract(v.getDX().multiply(getDY())), v.oom);
                dy.multiply(v.dz).getSqrt().subtract(v.dy.multiply(dz).getSqrt()),
                dz.multiply(v.dx).getSqrt().subtract(v.dz.multiply(dx).getSqrt()),
                dx.multiply(v.dy).getSqrt().subtract(v.dx.multiply(dy).getSqrt()), v.oom);
    }

    /**
     * Scales by {@link #m} to give a unit vector with length 1.
     *
     * @param oom The order of magnitude for the precision of the result.
     * @return this scaled by {@link #m}.
     */
    public V3D_Vector getUnitVector(int oom) {
        Math_BigRational d = m.getSqrt();
        if (d == null) {
            d = Math_BigRational.valueOf(m.toBigDecimal(oom));
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
        Math_BigRational[][] ma = new Math_BigRational[1][3];
        ma[0][0] = getDX();
        ma[0][1] = getDY();
        ma[0][2] = getDZ();
        return new Math_Matrix_BR(ma);
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
        if (getDX().compareTo(Math_BigRational.ZERO) != -1) {
            if (getDY().compareTo(Math_BigRational.ZERO) != -1) {
                if (getDZ().compareTo(Math_BigRational.ZERO) != -1) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (getDZ().compareTo(Math_BigRational.ZERO) != -1) {
                    return 3;
                } else {
                    return 4;
                }
            }
        } else {
            if (getDY().compareTo(Math_BigRational.ZERO) != -1) {
                if (getDZ().compareTo(Math_BigRational.ZERO) != -1) {
                    return 5;
                } else {
                    return 6;
                }
            } else {
                if (getDZ().compareTo(Math_BigRational.ZERO) != -1) {
                    return 7;
                } else {
                    return 8;
                }
            }
        }
    }
}
