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
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigDecimal;

/**
 * V3D_Vector
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Vector {

    /**
     * The change in x.
     */
    public final BigDecimal dx;

    /**
     * The change in y.
     */
    public final BigDecimal dy;

    /**
     * The change in z.
     */
    public final BigDecimal dz;

    /**
     * For storing the magnitude.
     */
    public BigDecimal magnitude;

    /**
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_Vector(BigDecimal dx, BigDecimal dy, BigDecimal dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    /**
     * Creates a vector from the origin to {@code p}
     *
     * @param p the point to which the vector starting at the origin goes.
     */
    public V3D_Vector(V3D_Point p) {
        this.dx = p.x;
        this.dy = p.y;
        this.dz = p.z;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(dx=" + dx + ", dy=" + dy
                + ", dz=" + dz + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Vector) {
            V3D_Vector v = (V3D_Vector) o;
            if (dx.compareTo(v.dx) == 0 && dy.compareTo(v.dy) == 0
                    && dz.compareTo(v.dz) == 0) {
                return true;
            }
        }
        return false;
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
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Dot_product">dot product</A>.
     *
     * @param v V3D_Vector
     * @return dot product
     */
    public BigDecimal getDotProduct(V3D_Vector v) {
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
        return getDotProduct(v).compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * Get the magnitude of the vector at the given scale.
     *
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #magnitude} initialised with {@code scale} and {@code rm}.
     */
    public BigDecimal getMagnitude(int scale, RoundingMode rm) {
        if (magnitude == null) {
            return initMagnitude(scale, rm);
        }
        if (magnitude.scale() > scale) {
            return magnitude.setScale(scale);
        } else {
            return initMagnitude(scale, rm);
        }
    }

    /**
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #magnitude} initialised with {@code scale} and {@code rm}.
     */
    protected BigDecimal initMagnitude(int scale, RoundingMode rm) {
        magnitude = Math_BigDecimal.sqrt(dx.multiply(dx).add(dy.multiply(dy))
                .add(dz.multiply(dz)), scale, rm);
        return magnitude;
    }

    /**
     * Test if this is parallel to {@code v}.
     *
     * @param v The
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this and {@code v} are orthogonal.
     */
    public boolean isParallel(V3D_Vector v, int scale, RoundingMode rm) {
        BigDecimal t = BigDecimal.ONE.scaleByPowerOfTen(scale);
        BigDecimal c = Math_BigDecimal.divideRoundIfNecessary(v.dx, dx, scale,
                rm);
        return c.multiply(dy).subtract(v.dy).compareTo(t) == -1
                && c.multiply(dz).subtract(v.dz).compareTo(t) == -1;
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

}
