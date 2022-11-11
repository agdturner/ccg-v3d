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
package uk.ac.leeds.ccg.v3d.geometry.light;

import java.io.Serializable;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Vector;

/**
 * Intended for use as a vector or a coordinate. The idea is that this is 
 * lightweight despite using heavyweight numbers. It is not as heavyweight as 
 * V3D_Vector which uses even more heavyweight Math_BigRationalSqrt numbers 
 * for coordinates.
 * 
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_V implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The x.
     */
    public Math_BigRational x;

    /**
     * The y.
     */
    public Math_BigRational y;

    /**
     * The z.
     */
    public Math_BigRational z;

    /**
     * The origin {@code <0,0,0>}.
     */
    public static final V3D_V ZERO = new V3D_V(0, 0, 0);

    /**
     * @param v Used to construct this.
     */
    public V3D_V(V3D_V v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     * @param z What {@link #z} is set to.
     */
    public V3D_V(Math_BigRational x, Math_BigRational y, Math_BigRational z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @param dx What {@link #x} is set to after conversion.
     * @param dy What {@link #y} is set to after conversion.
     * @param dz What {@link #z} is set to after conversion.
     */
    public V3D_V(long dx, long dy, long dz) {
        this(Math_BigRational.valueOf(dx), Math_BigRational.valueOf(dy),
                Math_BigRational.valueOf(dz));
    }

    /**
     * @param dx What {@link #x} is set to after conversion.
     * @param dy What {@link #y} is set to after conversion.
     * @param dz What {@link #z} is set to after conversion.
     */
    public V3D_V(double dx, double dy, double dz) {
        this(Math_BigRational.valueOf(dx), Math_BigRational.valueOf(dy),
                Math_BigRational.valueOf(dz));
    }

    /**
     * @param p A point to construct from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_V(V3D_Point p, int oom, RoundingMode rm) {
        this(p.getX(oom, rm), p.getY(oom, rm), p.getZ(oom, rm));
    }

    /**
     * @param v A vector to construct from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_V(V3D_Vector v, int oom, RoundingMode rm) {
        this(v.getDX(oom, rm), v.getDY(oom, rm), v.getDZ(oom, rm));
    }

    /**
     * Effectively this becomes a vector of the difference. What needs to be
     * applied to p to get q.
     *
     * @param p The start.
     * @param q The end.
     */
    public V3D_V(V3D_V p, V3D_V q) {
        this(q.x.subtract(p.x), q.y.subtract(p.y), q.z.subtract(p.z));
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
        return pad + "x=" + x + ",\n"
                + pad + "y=" + y + ",\n"
                + pad + "z=" + z;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_V v) {
            return equals(v);
        }
        return false;
    }

    /**
     * Indicates if {@code this} and {@code v} are equal.
     *
     * @param v The instance to test for equality with {@code this}.
     * @return {@code true} iff {@code this} is the same as {@code v}.
     */
    public boolean equals(V3D_V v) {
        return x.compareTo(v.x) == 0 && y.compareTo(v.y) == 0
                && z.compareTo(v.z) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.x);
        hash = 23 * hash + Objects.hashCode(this.y);
        hash = 23 * hash + Objects.hashCode(this.z);
        return hash;
    }

    /**
     * @return {@code true} if {@code this} equals {@link #ZERO}.
     */
    public boolean isZero() {
        return this.equals(ZERO);
    }

    /**
     * Scale by multiplication.
     *
     * @param s The scalar value to multiply this by.
     */
    public void multiply(Math_BigRational s) {
        x = x.multiply(s);
        y = y.multiply(s);
        z = z.multiply(s);
    }

    /**
     * Scale by division.
     *
     * @param s The scalar value to divide this by.
     */
    public void divide(Math_BigRational s) {
        x = x.divide(s);
        y = y.divide(s);
        z = z.divide(s);
    }

    /**
     * Add v to this.
     *
     * @param v The coordinate to apply.
     */
    public void translate(V3D_V v) {
        x = x.add(v.x);
        y = y.add(v.y);
        z = z.add(v.z);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The magnitude.
     */
    public Math_BigRational getMagnitude(int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(x.pow(2).add(y.pow(2))
                .add(z.pow(2)), oom, rm).getSqrt(oom, rm);
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Cross_product">cross product</A>.
     * Treat this as the first vector and {@code v} as the second vector. The
     * resulting vector is in the direction given by the right hand rule.
     *
     * @param v V3D_V
     * @return The CrossProduct.
     */
    public V3D_V getCrossProduct(V3D_V v) {
        return new V3D_V(
                y.multiply(v.z).subtract(z.multiply(v.y)),
                z.multiply(v.x).subtract(x.multiply(v.z)),
                x.multiply(v.y).subtract(v.x.multiply(y)));
    }

    /**
     * Scales by the magnitude to give a unit vector. (N.B. There is no check to
     * be sure that the resulting vector has a magnitude of less than 1).
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return this scaled by the magnitude.
     */
    public V3D_V getUnitVector(int oom, RoundingMode rm) {
        Math_BigRational d = getMagnitude(oom, RoundingMode.UP);
        V3D_V r = new V3D_V(x.divide(d), y.divide(d), z.divide(d));
        return r;
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Dot_product">dot product</A>.
     *
     * @param v The other vector.
     * @return dot product
     */
    public Math_BigRational getDotProduct(V3D_V v) {
        return (v.x.multiply(x)).add(v.y.multiply(y)).add(v.z.multiply(z));
    }

    /**
     * Get the distance between this and {@code p} assuming both are points.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance from {@code p} to this.
     */
    public Math_BigRational getDistance(V3D_V p, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(x.subtract(p.x).pow(2)
                .add(y.subtract(p.y).pow(2))
                .add(y.subtract(p.y).pow(2)), oom, rm).getSqrt(oom, rm);
    }

    /**
     * @return A new vector which is the opposite to {@code this}.
     */
    public V3D_V reverse() {
        return new V3D_V(x.negate(), y.negate(), z.negate());
    }
}
