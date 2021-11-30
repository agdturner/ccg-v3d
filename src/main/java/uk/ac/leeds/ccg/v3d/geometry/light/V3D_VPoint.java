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

import java.math.BigDecimal;
import java.util.Objects;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;

/**
 * 3D representation of a moveable point. The "*" denotes a point in 3D in the
 * following depiction: {@code
 *
 *                          y           -
 *                          +          /                * p=<x0,y0,z0>
 *                          |         /                 |
 *                          |        /                  |
 *                          |    z0-/-------------------|
 *                          |      /                   /
 *                          |     /                   /
 *                          |    /                   /
 *                          |   /                   /
 *                       y0-|  /                   /
 *                          | /                   /
 *                          |/                   /
 *  - ----------------------|-------------------/---- + x
 *                         /|                  x0
 *                        / |
 *                       /  |
 *                      /   |
 *                     /    |
 *                    /     |
 *                   /      |
 *                  /       |
 *                 +        |
 *                z         -
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_VPoint extends V3D_VGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The coordinate origin.
     */
    public static V3D_VPoint ORIGIN = new V3D_VPoint(0, 0, 0);

    /**
     * The position relative to {@link #offset}.
     */
    public V3D_V rel;

    /**
     * @param p The coordinate vector to duplicate
     */
    public V3D_VPoint(V3D_VPoint p) {
        super(p.offset);
        this.rel = new V3D_V(p.rel);
    }

    /**
     * @param offset What {@link #offset} is set to.
     * @param rel What {@link #rel} is set to.
     */
    public V3D_VPoint(V3D_V offset, V3D_V rel) {
        super(offset);
        this.rel = rel;
    }

    /**
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_VPoint(Math_BigRational x, Math_BigRational y, Math_BigRational z) {
        super(V3D_V.ZERO);
        this.rel = new V3D_V(x, y, z);
    }

    /**
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_VPoint(BigDecimal x, BigDecimal y, BigDecimal z) {
        this(Math_BigRational.valueOf(x), Math_BigRational.valueOf(y),
                Math_BigRational.valueOf(z));
    }

    /**
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_VPoint(double x, double y, double z) {
        this(Math_BigRational.valueOf(x), Math_BigRational.valueOf(y),
                Math_BigRational.valueOf(z));
    }

    /**
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_VPoint(long x, long y, long z) {
        this(Math_BigRational.valueOf(x), Math_BigRational.valueOf(y),
                Math_BigRational.valueOf(z));
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
        return pad + "pos=" + rel.toString(pad) + "\n"
                + pad + ",\n"
                + pad + "offset=" + offset.toString(pad);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_VPoint v3D_VPoint) {
            return equals(v3D_VPoint);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.rel);
        hash = 47 * hash + Objects.hashCode(this.offset);
        return hash;
    }

    /**
     * Equal if they represent the same location defined by {@link #rel} and
     * {@link #offset}.
     *
     * @param p The point to test if it is the same as {@code this}.
     * @return {@code true} iff {@code p} is the same as {@code this}.
     */
    public boolean equals(V3D_VPoint p) {
        if (this.getX().compareTo(p.getX()) == 0) {
            if (this.getY().compareTo(p.getY()) == 0) {
                if (this.getZ().compareTo(p.getZ()) == 0) {
                    return true;
                }
            }
        }
//        int toom = rel.getMagnitude().getOom();
//        int poom = p.rel.getMagnitude().getOom();
//        if (this.getX(toom).compareTo(p.getX(poom)) == 0) {
//            if (this.getY(toom).compareTo(p.getY(poom)) == 0) {
//                if (this.getZ(toom).compareTo(p.getZ(poom)) == 0) {
//                    return true;
//                }
//            }
//        }
        return false;
    }

    /**
     * @return The x component of {@link #rel} with {@link #offset} applied.
     */
    public Math_BigRational getX() {
        return rel.x.add(offset.x);
    }

    /**
     * @return The y component of {@link #rel} with {@link #offset} applied.
     */
    public Math_BigRational getY() {
        return rel.y.add(offset.y);
    }

    /**
     * @return The z component of {@link #rel} with {@link #offset} applied.
     */
    public Math_BigRational getZ() {
        return rel.z.add(offset.z);
    }

    /**
     * Get the distance between this and {@code p}.
     *
     * @param oom The Order of Magnitude for the precision of the result.
     * @param p A point.
     * @return The distance from {@code p} to this.
     */
    public Math_BigRationalSqrt getDistance(int oom, V3D_VPoint p) {
        if (this.equals(p)) {
            return Math_BigRationalSqrt.ZERO;
        }
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom), -1);
    }

    /**
     * Get the distance between this and {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The distance from {@code p} to this.
     */
    public BigDecimal getDistance(V3D_VPoint p, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
    }

    /**
     * Get the distance squared between this and {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The distance squared from {@code p} to this.
     */
    public Math_BigRational getDistanceSquared(V3D_VPoint p, int oom) {
        Math_BigRational dx = this.getX().subtract(p.getX());
        Math_BigRational dy = this.getY().subtract(p.getY());
        Math_BigRational dz = this.getZ().subtract(p.getZ());
        return dx.pow(2).add(dy.pow(2)).add(dz.pow(2));
    }

    @Override
    public void apply(V3D_V v) {
        rel.apply(v);
    }

}
