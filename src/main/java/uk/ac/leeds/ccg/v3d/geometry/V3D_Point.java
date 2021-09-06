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
import ch.obermuhlner.math.big.BigRational;
import uk.ac.leeds.ccg.math.Math_BigRationalSqrt;

/**
 * 3D representation of a point. The "*" denotes a point in 3D in the following
 * depiction: {@code
 *                                       
 *                          y           -
 *                          +          /                *p=<x0,y0,z0>
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
public class V3D_Point extends V3D_Geometry implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The origin of the Euclidean space.
     */
    public static final V3D_Point ORIGIN = new V3D_Point(0, 0, 0);

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
     * @param p The point to duplicate
     */
    public V3D_Point(V3D_Envelope.Point p) {
        x = p.x;
        y = p.y;
        z = p.z;
    }

    /**
     * @param v The vector.
     */
    public V3D_Point(V3D_Vector v) {
        x = v.dx.getSqrt();
        y = v.dy.getSqrt();
        z = v.dz.getSqrt();
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
     * @return a new point.
     */
    @Override
    public V3D_Point apply(V3D_Vector v) {
        return new V3D_Point(x.add(v.getDX()), y.add(v.getDY()), z.add(v.getDZ()));
    }

    /**
     * Get the distance between this and {@code p}.
     *
     * @param p A point.
     * @return The distance from {@code p} to this.
     */
    public Math_BigRationalSqrt getDistance(V3D_Point p) {
        if (this.equals(p)) {
            return Math_BigRationalSqrt.ZERO;
        }
        return new Math_BigRationalSqrt(getDistanceSquared(p));
    }

    /**
     * Get the distance between this and {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The distance from {@code p} to this.
     */
    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        if (this.equals(p)) {
            return BigDecimal.ZERO;
        }
        return Math_BigDecimal.sqrt(getDistanceSquared(p).toBigDecimal(),
                oom, RoundingMode.HALF_UP);
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
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The distance from {@code p} to this.
     */
    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        if (l.isIntersectedBy(this)) {
            return BigDecimal.ZERO;
        }
        V3D_Vector cp = new V3D_Vector(this, l.p).getCrossProduct(
                new V3D_Vector(this, l.q));
        return cp.getMagnitude().divide(l.v.getMagnitude()).toBigDecimal(oom);
//        return cp.getMagnitude(oom - 1).divide(l.v.getMagnitude(oom - 1), -oom,
//                RoundingMode.HALF_UP);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pl A plane.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The distance from {@code p} to this.
     */
    public BigDecimal getDistance(V3D_Plane pl, int oom) {
        return pl.getDistance(this, oom);
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

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l) {
        return l.isIntersectedBy(this);
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        /**
         * Get the distance from the ends of the line segment to the point. If
         * both these distances is less than the length of the line segment then
         * the distance is the distance from the point to the infinite line.
         *
         */
        int oom2 = oom - 2;
        BigRational l2 = l.getLength2();
        BigRational lp2 = l.p.getDistanceSquared(this);
        BigRational lq2 = l.q.getDistanceSquared(this);
        BigDecimal lp = (new V3D_Line(l)).getDistance(this, oom);
        if (lp2.compareTo(l2) == -1 && lq2.compareTo(l2) == -1) {
            return lp;
        }
        /**
         * If the projection from the point onto the infinite line intersects in
         * a place within the line segment, then the distance is the distance
         * from the point to the infinite line. If it is outside the line
         * segment, then the distance is the minimum of the distances from the
         * point to the ends of the line segment.
         */
        //BigRational pl2 = (new V3D_Line(l)).getDistanceSquared(this);
        BigDecimal pl = (new V3D_Line(l)).getDistance(this, oom2);
        BigRational pl2 = BigRational.valueOf(pl).pow(2);
        V3D_Vector u = l.v.getUnitVector(oom - 2);
        V3D_Point pi = new V3D_Point(u.multiply(BigRational.valueOf(
                new Math_BigRationalSqrt(lp2.subtract(pl2)).toBigDecimal(oom2)))
                .add(new V3D_Vector(l.p)));
        if (l.isIntersectedBy(pi)) {
            return lp;
        } else {
            return new Math_BigRationalSqrt(BigRational.min(lp2, lq2))
                    .toBigDecimal(oom);
        }
    }
    
    /**
     * @param r The ray to get the distance from.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance between {@code this} and {@code r}.
     */
    public BigDecimal getDistance(V3D_Ray r, int oom) {
        V3D_Point pt = r.getPointOfIntersection(this);
        return pt.getDistance(this, oom);
    }

    /**
     * @return The location of the point:
     * <Table>
     * <caption>Locations</caption>
     * <thead>
     * <tr><td>Code</td><td>Description</td></tr>
     * </thead>
     * <tbody>
     * <tr><td>1</td><td>Px, Py, Pz</td></tr>
     * <tr><td>2</td><td>Px, Py, Nz</td></tr>
     * <tr><td>3</td><td>Px, Ny, Pz</td></tr>
     * <tr><td>4</td><td>Px, Ny, Nz</td></tr>
     * <tr><td>5</td><td>Nx, Py, Pz</td></tr>
     * <tr><td>6</td><td>Nx, Py, Nz</td></tr>
     * <tr><td>7</td><td>Nx, Ny, Pz</td></tr>
     * <tr><td>8</td><td>Nx, Ny, Nz</td></tr>
     * </tbody>
     * </Table>
     */
    public int getLocation() {
        if (x.compareTo(BigRational.ZERO) != -1) {
            if (y.compareTo(BigRational.ZERO) != -1) {
                if (z.compareTo(BigRational.ZERO) != -1) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (z.compareTo(BigRational.ZERO) != -1) {
                    return 3;
                } else {
                    return 4;
                }
            }
        } else {
            if (y.compareTo(BigRational.ZERO) != -1) {
                if (z.compareTo(BigRational.ZERO) != -1) {
                    return 5;
                } else {
                    return 6;
                }
            } else {
                if (z.compareTo(BigRational.ZERO) != -1) {
                    return 7;
                } else {
                    return 8;
                }
            }
        }
    }
}
