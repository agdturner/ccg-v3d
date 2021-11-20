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

import java.io.Serializable;
import java.util.Objects;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;

/**
 * A mutable 3D thing. It could be a vector or a coordinate or something else.
 * It is intentionally lightweight despite having heavy number components.
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
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     * @param z What {@link #z} is set to.
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
    public V3D_V(Math_BigRational x, Math_BigRational y,
            Math_BigRational z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_V(long dx, long dy, long dz) {
        this(Math_BigRational.valueOf(dx), Math_BigRational.valueOf(dy),
                Math_BigRational.valueOf(dz));
    }

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_V(double dx, double dy, double dz) {
        this(Math_BigRational.valueOf(dx), Math_BigRational.valueOf(dy),
                Math_BigRational.valueOf(dz));
    }

    /**
     * @param p A point to construct from.
     * @param oom The Order of Magnitude for the precision.
     */
    public V3D_V(V3D_Point p, int oom) {
        this(p.getX(oom), p.getY(oom), p.getZ(oom));
    }

    /**
     * @param v A vector to construct from.
     * @param oom The Order of Magnitude for the precision.
     */
    public V3D_V(V3D_Vector v, int oom) {
        this(v.getDX(oom), v.getDY(oom), v.getDZ(oom));
    }

    /**
     * Effectively this becomes a vector of the difference. What needs to be
     * applied to p to get q.
     *
     * @param p The start.
     * @param q The end.
     */
    public V3D_V(V3D_VPoint p, V3D_VPoint q) {
        this(q.getX().subtract(p.getX()),
                q.getY().subtract(p.getY()),
                q.getZ().subtract(p.getZ()));
    }

    /**
     * Effectively this becomes a vector of the difference. What needs to be
     * applied to p to get q.
     *
     * @param p The start.
     * @param q The end.
     */
    public V3D_V(V3D_V p, V3D_V q) {
        this(q.x.subtract(p.x),
                q.y.subtract(p.y),
                q.z.subtract(p.z));
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
        if (o instanceof V3D_V) {
            return equals((V3D_V) o);
        }
        return false;
    }

    /**
     * Indicates if {@code this} and {@code v} are equal.
     *
     * @param c The coordinate to test for equality with {@code this}.
     * @return {@code true} iff {@code this} is the same as {@code v}.
     */
    public boolean equals(V3D_V c) {
        return x.compareTo(c.x) == 0 && y.compareTo(c.y) == 0
                && z.compareTo(c.z) == 0;
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
    public boolean isOrigin() {
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
    public void divide(Math_BigRational s, int oom) {
        x = x.divide(s);
        y = y.divide(s);
        z = z.divide(s);
    }

    /**
     * Add v to this.
     *
     * @param v The coordinate to apply.
     */
    public void apply(V3D_V v) {
        x = x.add(v.x);
        y = y.add(v.y);
        z = z.add(v.z);
    }

    /**
     * Subtract s from this.
     *
     * @param s The coordinate to apply.
     */
    public void subtract(V3D_V s) {
        x = x.subtract(s.x);
        y = y.subtract(s.y);
        z = z.subtract(s.z);
    }
    
    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The magnitude.
     */
    public Math_BigRational getMagnitude(int oom) {
        return new Math_BigRationalSqrt(x.pow(2).add(y.pow(2)).add(z.pow(2)), oom).getSqrt(oom);
    }
        
    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Cross_product">cross product</A>.
     * Treat this as the first vector and {@code v} as the second vector. The
     * resulting vector is in the direction given by the right hand rule.
     *
     * @param v V3D_V
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The CrossProduct.
     */
    public V3D_V getCrossProduct(V3D_V v, int oom) {
        return new V3D_V(
                y.multiply(v.z).subtract(z.multiply(v.y)),
                z.multiply(v.x).subtract(x.multiply(v.z)),
                x.multiply(v.y).subtract(v.x.multiply(y)));
    }
    
    /**
     * Scales by {@link #m} to give a unit vector with length 1.
     *
     * @param oom The order of magnitude for the precision of the result.
     * @return this scaled by {@link #m}.
     */
    public V3D_V getUnitVector(int oom) {
        Math_BigRational d = getMagnitude(oom);
        V3D_V r = new V3D_V(
                x.divide(d),
                y.divide(d),
                z.divide(d));
        if (r.getMagnitude(oom).compareTo(Math_BigRational.ONE) == 1) {
            d = d.subtract(Math_BigRational.ONE.divide(oom));
            r = new V3D_V(
                x.divide(d),
                y.divide(d),
                z.divide(d));
        }
        return r;
    }
    
    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Dot_product">dot product</A>.
     *
     * @param v V3D_V
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return dot product
     */
    public Math_BigRational getDotProduct(V3D_V v) {
        return (v.x.multiply(x)).add(v.y.multiply(y)).add(v.z.multiply(z));
    }
    
    /**
     * Get the distance between this and {@code p} assuming both are points.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The distance from {@code p} to this.
     */
    public Math_BigRational getDistance(V3D_V p, int oom) {
        return new Math_BigRationalSqrt(x.subtract(p.x).pow(2)
                .add(y.subtract(p.y).pow(2))
                .add(y.subtract(p.y).pow(2)), oom).getSqrt(oom);
    }
    
    /**
     * @return A new vector which is the opposite to {@code this}.
     */
    public V3D_V reverse() {
        return new V3D_V(x.negate(), y.negate(), z.negate());
    }
}
