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
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.core.V3D_Object;

/**
 * V3D_Vector
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Vector extends V3D_Object {

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
     * @param e V3D_Environment
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_Vector(V3D_Environment e, BigDecimal dx, BigDecimal dy, BigDecimal dz) {
        super(e);
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
        super(p.e);
        this.dx = p.x;
        this.dy = p.y;
        this.dz = p.z;
    }

    /**
     * Creates a vector from {@code p} to {@code q}
     *
     * @param p the point where the vector starts.
     * @param q the point where the vector ends.
     */
    public V3D_Vector(V3D_Point p, V3D_Point q) {
        super(p.e);
        this.dx = q.x.subtract(p.x);
        this.dy = q.y.subtract(p.y);
        this.dz = q.z.subtract(p.z);
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
     * @return {@code true} if {@code this.equals(e.zeroVector)) 
     */
    public boolean isZeroVector() {
        return this.equals(e.zeroVector);
    }
    
    /**
     * @param s The scalar value to multiply this by.
     * @return Scaled vector.
     */
    public V3D_Vector multiply(BigDecimal s) {
        return new V3D_Vector(e, dx.multiply(s), dy.multiply(s), dz.multiply(s));
    }
    
    /**
     * @param v The vector to add.
     * @return A new vector which is this add {@code v}.
     */
    public V3D_Vector add(V3D_Vector v) {
        return new V3D_Vector(e, dx.add(v.dx), dy.add(v.dy), dz.add(v.dz));
    }
    
    /**
     * @param v The vector to subtract.
     * @return A new vector which is this subtract {@code v}.
     */
    public V3D_Vector subtract(V3D_Vector v) {
        return new V3D_Vector(e, dx.subtract(v.dx), dy.subtract(v.dy), dz.subtract(v.dz));
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
        BigDecimal t = BigDecimal.ONE.scaleByPowerOfTen(-scale);
        if (dx.compareTo(BigDecimal.ZERO) == 0 &&
            dy.compareTo(BigDecimal.ZERO) == 0 &&
            dz.compareTo(BigDecimal.ZERO) == 0) {
            return false;        
        }
        if (v.dx.compareTo(BigDecimal.ZERO) == 0 &&
            v.dy.compareTo(BigDecimal.ZERO) == 0 &&
            v.dz.compareTo(BigDecimal.ZERO) == 0) {
            return false;        
        }
        if (dx.compareTo(BigDecimal.ZERO) == 0
                && v.dx.compareTo(BigDecimal.ZERO) != 0) {
            return false;
        }
        if (dy.compareTo(BigDecimal.ZERO) == 0
                && v.dy.compareTo(BigDecimal.ZERO) != 0) {
            return false;
        }
        if (dz.compareTo(BigDecimal.ZERO) == 0
                && v.dz.compareTo(BigDecimal.ZERO) != 0) {
            return false;
        }
        if (dx.compareTo(BigDecimal.ZERO) == 0
                && v.dx.compareTo(BigDecimal.ZERO) == 0) {
            if (dy.compareTo(BigDecimal.ZERO) == 0
                    && v.dy.compareTo(BigDecimal.ZERO) == 0) {
                return !(dz.compareTo(BigDecimal.ZERO) == 0
                        || v.dz.compareTo(BigDecimal.ZERO) == 0);
            } else {
                if (dz.compareTo(BigDecimal.ZERO) == 0
                        && v.dz.compareTo(BigDecimal.ZERO) == 0) {
                    return true;
                } else {
                    BigDecimal c = Math_BigDecimal.divideRoundIfNecessary(v.dy,
                            dy, scale, rm);
                    return c.multiply(dz).subtract(v.dz).abs().compareTo(t) == -1;
                }
            }
        } else {
            if (dy.compareTo(BigDecimal.ZERO) == 0
                    && v.dy.compareTo(BigDecimal.ZERO) == 0) {
                if (dz.compareTo(BigDecimal.ZERO) == 0
                        && v.dz.compareTo(BigDecimal.ZERO) == 0) {
                    return true;
                } else {
                    BigDecimal c = Math_BigDecimal.divideRoundIfNecessary(v.dx,
                            dx, scale, rm);
                    return c.multiply(dy).subtract(v.dy).abs().compareTo(t) == -1;
                }
            } else {
                BigDecimal c = Math_BigDecimal.divideRoundIfNecessary(v.dx, dx,
                        scale, rm);
                if (dz.compareTo(BigDecimal.ZERO) == 0
                        && v.dz.compareTo(BigDecimal.ZERO) == 0) {
                    return c.multiply(dy).subtract(v.dy).abs().compareTo(t) == -1;
                } else {
                    return c.multiply(dy).subtract(v.dy).abs().compareTo(t) == -1
                            && c.multiply(dz).subtract(v.dz).abs().compareTo(t) == -1;
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
        return new V3D_Vector(e, dy.multiply(v.dz).subtract(v.dy.multiply(dz)),
                dz.multiply(v.dx).subtract(v.dz.multiply(dx)),
                dx.multiply(v.dy).subtract(v.dx.multiply(dy)));
    }

    /**
     * Scales the vector by the magnitude so that it has length 1
     *
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return this scaled by the magnitude.
     */
    public V3D_Vector getUnitVector(int scale, RoundingMode rm) {
        BigDecimal m = getMagnitude(scale + 2, rm);
        return new V3D_Vector(e,
                Math_BigDecimal.divideRoundIfNecessary(dx, m, scale, rm),
                Math_BigDecimal.divideRoundIfNecessary(dy, m, scale, rm),
                Math_BigDecimal.divideRoundIfNecessary(dz, m, scale, rm));
    }
}
