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

import uk.ac.leeds.ccg.v3d.geometry.envelope.V3D_Envelope;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import ch.obermuhlner.math.big.BigRational;

/**
 * V3D_Point
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Point extends V3D_Geometry implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The x coordinate.
     */
    public BigRational x;

    /**
     * The y coordinate.
     */
    public BigRational y;

    /**
     * The z coordinate.
     */
    public BigRational z;

    /**
     * @param p The point to duplicate
     */
    public V3D_Point(V3D_Point p) {
        x = p.x;
        y = p.y;
        z = p.z;
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     * @param z What {@link #z} is set to.
     */
    public V3D_Point(BigRational x, BigRational y, BigRational z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     * @param z What {@link #z} is set to.
     */
    public V3D_Point(BigDecimal x, BigDecimal y, BigDecimal z) {
        this.x = BigRational.valueOf(x);
        this.y = BigRational.valueOf(y);
        this.z = BigRational.valueOf(z);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     * @param z What {@link #z} is set to.
     */
    public V3D_Point(double x, double y, double z) {
        this.x = BigRational.valueOf(x);
        this.y = BigRational.valueOf(y);
        this.z = BigRational.valueOf(z);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     * @param z What {@link #z} is set to.
     */
    public V3D_Point(long x, long y, long z) {
        this.x = BigRational.valueOf(x);
        this.y = BigRational.valueOf(y);
        this.z = BigRational.valueOf(z);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(x=" + x.toString()
                + ", y=" + y.toString() + ", z=" + z.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Point) {
            return equals((V3D_Point) o);
        }
        return false;
    }

    /**
     * @param p The point to test if it is the same as {@code this}.
     * @return {@code true} iff {@code p} is the same as {@code this}.
     */
    public boolean equals(V3D_Point p) {
        if (p.x.compareTo(x) == 0) {
            if (p.y.compareTo(y) == 0) {
                if (p.z.compareTo(z) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.x);
        hash = 43 * hash + Objects.hashCode(this.y);
        hash = 43 * hash + Objects.hashCode(this.z);
        return hash;
    }

    /**
     * @param v The vector to apply.
     * @return a new point which is this shifted by v.
     */
    public V3D_Point apply(V3D_Vector v) {
        return new V3D_Point(x.add(v.dx), y.add(v.dy), z.add(v.dz));
    }

    /**
     * Get the distance between this and {@code p}.
     *
     * @param p A point.
     * @param scale The scale. A positive value gives the number of decimal
     * places. A negative value rounds to the left of the decimal point.
     * @param rm The RoundingMode for the calculation.
     * @return The distance from {@code p} to this.
     */
    public BigDecimal getDistance(V3D_Point p, int scale, RoundingMode rm) {
        if (this.equals(p)) {
            return BigDecimal.ZERO;
        }
        return Math_BigDecimal.sqrt(getDistanceSquared(p).toBigDecimal(), scale,
                rm);
    }

    /**
     * Get the distance squared between this and {@code p}.
     *
     * @param p A point.
     * @return The distance squared from {@code p} to this.
     */
    public BigRational getDistanceSquared(V3D_Point p) {
        BigRational dx = this.x.subtract(p.x);
        BigRational dy = this.y.subtract(p.y);
        BigRational dz = this.z.subtract(p.z);
        return dx.pow(2).add(dy.pow(2)).add(dz.pow(2));
    }

    /**
     * Get the distance between this and {@code l}.
     *
     * @param l A line.
     * @param scale The scale. A positive value gives the number of decimal
     * places. A negative value rounds to the left of the decimal point.
     * @param rm The RoundingMode for the calculation.
     * @return The distance from {@code p} to this.
     */
    public BigDecimal getDistance(V3D_Line l, int scale, RoundingMode rm) {
        if (l.isIntersectedBy(this)) {
            return BigDecimal.ZERO;
        }
        l.p.getDistance(l.q, scale, rm);
        V3D_Vector lu = l.v.getUnitVector(scale, rm);
        V3D_Vector v = new V3D_Vector(this, l.p);
        BigRational v_dot_lu = v.getDotProduct(lu);
        V3D_Point p = l.q.apply(lu.multiply(v_dot_lu));
        return p.getDistance(this, scale, rm);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pl A plane.
     * @param scale The scale. A positive value gives the number of decimal
     * places. A negative value rounds to the left of the decimal point.
     * @param rm The RoundingMode for the calculation.
     * @return The distance from {@code p} to this.
     */
    public BigDecimal getDistance(V3D_Plane pl, int scale, RoundingMode rm) {
        if (pl.isIntersectedBy(this)) {
            return BigDecimal.ZERO;
        }
        throw new RuntimeException("Not implemented");
    }

    @Override
    public V3D_Envelope getEnvelope() {
        return new V3D_Envelope(x, y, z);
    }

    @Override
    public boolean isIntersectedBy(V3D_Point p) {
        return this.equals(p);
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l) {
        return l.isIntersectedBy(this);
    }

    /**
     * @param l The line to intersect with {@code this}.
     * @return {@code this} if the point is on the line {@code l} else return
     * {@code null}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l) {
        if (l.isIntersectedBy(this)) {
            return this;
        }
        return null;
    }

    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, boolean b) {
        if (l.isIntersectedBy(this)) {
            return this;
        }
        return null;
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, boolean b) {
        return l.isIntersectedBy(this);
    }

}
