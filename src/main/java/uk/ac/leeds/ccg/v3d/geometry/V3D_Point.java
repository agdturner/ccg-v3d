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
import java.util.Objects;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;

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
    public Math_BigRational x;

    /**
     * The y coordinate.
     */
    public Math_BigRational y;

    /**
     * The z coordinate.
     */
    public Math_BigRational z;

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
     * Create a new instance.
     * @param v The vector.
     * @param oom The Order of Magnitude for the precision.
     */
    public V3D_Point(V3D_Vector v, int oom) {
        x = v.getDX(oom);
        y = v.getDY(oom);
        z = v.getDZ(oom);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     * @param z What {@link #z} is set to.
     */
    public V3D_Point(Math_BigRational x, Math_BigRational y, Math_BigRational z) {
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
        this.x = Math_BigRational.valueOf(x);
        this.y = Math_BigRational.valueOf(y);
        this.z = Math_BigRational.valueOf(z);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     * @param z What {@link #z} is set to.
     */
    public V3D_Point(double x, double y, double z) {
        this.x = Math_BigRational.valueOf(x);
        this.y = Math_BigRational.valueOf(y);
        this.z = Math_BigRational.valueOf(z);
    }

    /**
     * @param x What {@link #x} is set to.
     * @param y What {@link #y} is set to.
     * @param z What {@link #z} is set to.
     */
    public V3D_Point(long x, long y, long z) {
        this.x = Math_BigRational.valueOf(x);
        this.y = Math_BigRational.valueOf(y);
        this.z = Math_BigRational.valueOf(z);
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
     * @return true iff this is equal to the ORIGIN. 
     */
    public boolean isOrigin() {
        return equals(ORIGIN);
    }
    
    /**
     * @param v The vector to apply.
     * @param oom The Order of Magnitude for the precision.
     * @return a new point.
     */
    @Override
    public V3D_Point apply(V3D_Vector v, int oom) {
        Math_BigRational dx = x.add(v.getDX(oom));
        Math_BigRational dy = y.add(v.getDY(oom));
        Math_BigRational dz = z.add(v.getDZ(oom));
//        Math_BigRational dx = x.subtract(v.getDX());
//        Math_BigRational dy = y.subtract(v.getDY());
//        Math_BigRational dz = z.subtract(v.getDZ());
        return new V3D_Point(dx, dy, dz);
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
        return new Math_BigRationalSqrt(getDistanceSquared(p), -1);
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
        return new Math_BigRationalSqrt(getDistanceSquared(p), oom)
                .getSqrt(oom).toBigDecimal(oom);
    }

    /**
     * Get the distance squared between this and {@code p}.
     *
     * @param p A point.
     * @return The distance squared from {@code p} to this.
     */
    public Math_BigRational getDistanceSquared(V3D_Point p) {
        Math_BigRational dx = this.x.subtract(p.x);
        Math_BigRational dy = this.y.subtract(p.y);
        Math_BigRational dz = this.z.subtract(p.z);
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
        if (l.isIntersectedBy(this, oom)) {
            return BigDecimal.ZERO;
        }
        // Not sure what oom should be in the cross product...
        V3D_Vector cp = new V3D_Vector(this, l.p, oom).getCrossProduct(
                new V3D_Vector(this, l.q, oom), oom);
        return cp.getMagnitude().divide(l.v.getMagnitude(), oom).toBigDecimal(oom);
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
    public V3D_Envelope getEnvelope(int oom) {
        return new V3D_Envelope(x, y, z, oom);
    }

    @Override
    public boolean isIntersectedBy(V3D_Point p, int oom) {
        return this.equals(p);
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        return l.isIntersectedBy(this, oom);
    }

    /**
     * @param l The line to intersect with {@code this}.
     * @return {@code this} if the point is on the line {@code l} else return
     * {@code null}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        if (l.isIntersectedBy(this, oom)) {
            return this;
        }
        return null;
    }

    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom, boolean b) {
        if (l.isIntersectedBy(this, oom)) {
            return this;
        }
        return null;
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, boolean b) {
        return l.isIntersectedBy(this, oom);
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
        return l.isIntersectedBy(this, oom);
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
        Math_BigRational l2 = l.getLength2();
        Math_BigRational lp2 = l.p.getDistanceSquared(this);
        Math_BigRational lq2 = l.q.getDistanceSquared(this);
        BigDecimal lp = (new V3D_Line(l, oom)).getDistance(this, oom);
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
        //Math_BigRational pl2 = (new V3D_Line(l)).getDistanceSquared(this);
        BigDecimal pl = (new V3D_Line(l, oom)).getDistance(this, oom2);
        Math_BigRational pl2 = Math_BigRational.valueOf(pl).pow(2);
        V3D_Vector u = l.v.getUnitVector(oom - 2);
        V3D_Point pi = new V3D_Point(u.multiply(Math_BigRational.valueOf(
                new Math_BigRationalSqrt(lp2.subtract(pl2), oom2)
                        .toBigDecimal(oom2)), oom2)
                .add(new V3D_Vector(l.p, oom2), oom2), oom2);
        if (l.isIntersectedBy(pi, oom)) {
            return lp;
        } else {
            return new Math_BigRationalSqrt(Math_BigRational.min(lp2, lq2), oom2)
                    .toBigDecimal(oom);
        }
    }
    
    /**
     * @param r The ray to get the distance from.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance between {@code this} and {@code r}.
     */
    public BigDecimal getDistance(V3D_Ray r, int oom) {
        V3D_Point pt = r.getPointOfIntersection(this, oom);
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
        if (x.compareTo(Math_BigRational.ZERO) != -1) {
            if (y.compareTo(Math_BigRational.ZERO) != -1) {
                if (z.compareTo(Math_BigRational.ZERO) != -1) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (z.compareTo(Math_BigRational.ZERO) != -1) {
                    return 3;
                } else {
                    return 4;
                }
            }
        } else {
            if (y.compareTo(Math_BigRational.ZERO) != -1) {
                if (z.compareTo(Math_BigRational.ZERO) != -1) {
                    return 5;
                } else {
                    return 6;
                }
            } else {
                if (z.compareTo(Math_BigRational.ZERO) != -1) {
                    return 7;
                } else {
                    return 8;
                }
            }
        }
    }
}
