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

import uk.ac.leeds.ccg.v3d.geometry.light.V3D_VPoint;
import java.math.BigDecimal;
import java.util.Objects;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * 3D representation of a point. The "*" denotes a point in 3D in the following
 * depiction: {@code
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
public class V3D_Point extends V3D_Geometry implements V3D_FiniteGeometry, 
        V3D_IntersectionAndDistanceCalculations, Comparable<V3D_Point> {

    private static final long serialVersionUID = 1L;

    /**
     * The origin of the Euclidean space.
     */
    public static final V3D_Point ORIGIN = new V3D_Point(new V3D_Environment(), 0, 0, 0);

    /**
     * @param points The points to test if they are coincident.
     * @return {@code true} iff all the points are coincident.
     */
    public static boolean isCoincident(V3D_Vector... points) {
        V3D_Vector p0 = points[0];
        for (V3D_Vector p1 : points) {
            if (!p1.equals(p0)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param points The points to test if they are coincident.
     * @return {@code true} iff all the points are coincident.
     */
    public static boolean isCoincident(V3D_Point... points) {
        V3D_Point p0 = points[0];
        for (V3D_Point p1 : points) {
            if (!p1.equals(p0)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param points The points to test if they are coincident.
     * @return {@code true} iff all the points are coincident.
     */
    public static boolean isCoincident(V3D_Envelope.Point... points) {
        V3D_Envelope.Point p0 = points[0];
        for (V3D_Envelope.Point p1 : points) {
            if (!p1.equals(p0)) {
                return false;
            }
        }
        return true;
    }

    /**
     * The position relative to the {@link #offset}. Taken together with
     * {@link #offset}, this gives the point location.
     */
    public V3D_Vector rel;

    /**
     * Create a new instance which is completely independent of {@code p}.
     *
     * @param p The point to clone/duplicate.
     */
    public V3D_Point(V3D_Point p) {
        super(p.e, new V3D_Vector(p.offset));
        this.rel = new V3D_Vector(p.rel);
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param e What {@link #e} is set to.
     * @param rel Cloned to initialise {@link #rel}.
     */
    public V3D_Point(V3D_Environment e, V3D_Vector rel) {
        this(e, V3D_Vector.ZERO, rel);
    }

    /**
     * Create a new instance.
     *
     * @param e What {@link #e} is set to.
     * @param offset Cloned to initialise {@link #offset}.
     * @param rel Cloned to initialise {@link #rel}.
     */
    public V3D_Point(V3D_Environment e, V3D_Vector offset, V3D_Vector rel) {
//        super(new V3D_Vector(offset), Math.min(offset.getMagnitude().getOom(),
//                rel.getMagnitude().getOom()));
        super(e, new V3D_Vector(offset));
        this.rel = new V3D_Vector(rel);
    }

    /**
     * Create a new instance.
     *
     * @param e What {@link #e} is set to.
     * @param p The point to clone/duplicate.
     */
    public V3D_Point(V3D_Environment e, V3D_VPoint p) {
        super(e, new V3D_Vector(p.offset));
        this.rel = new V3D_Vector(p.rel);
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param p Used to initialise {@link #rel} and {@link #e}.
     */
    public V3D_Point(V3D_Envelope.Point p) {
        super(p.e, V3D_Vector.ZERO);
        this.rel = new V3D_Vector(p);
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param e What {@link #e} is set to.
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_Point(V3D_Environment e, Math_BigRational x, Math_BigRational y,
            Math_BigRational z) {
        super(e, V3D_Vector.ZERO);
        this.rel = new V3D_Vector(x, y, z);
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param e What {@link #e} is set to.
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_Point(V3D_Environment e, BigDecimal x, BigDecimal y,
            BigDecimal z) {
        this(e, Math_BigRational.valueOf(x), Math_BigRational.valueOf(y),
                Math_BigRational.valueOf(z));
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param e What {@link #e} is set to.
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_Point(V3D_Environment e, double x, double y, double z) {
        this(e, Math_BigRational.valueOf(x), Math_BigRational.valueOf(y),
                Math_BigRational.valueOf(z));
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param e What {@link #e} is set to.
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_Point(V3D_Environment e, long x, long y, long z) {
        this(e, Math_BigRational.valueOf(x), Math_BigRational.valueOf(y),
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
    @Override
    protected String toStringFields(String pad) {
        return super.toStringFields(pad) + "\n"
                + pad + ",\n"
                + pad + "rel=" + rel.toString(pad);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Point v3D_Point) {
            return equals(v3D_Point);
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
     * Two points are equal if they are at the same location defined by each
     * points relative start location and translation vector.
     *
     * @param p The point to test if it is the same as {@code this}.
     * @return {@code true} iff {@code p} is the same as {@code this}.
     */
    public boolean equals(V3D_Point p) {
        if (this.getX(e.oom).compareTo(p.getX(e.oom)) == 0) {
            if (this.getY(e.oom).compareTo(p.getY(e.oom)) == 0) {
                if (this.getZ(e.oom).compareTo(p.getZ(e.oom)) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @return The vector - {@code rel.add(offset, oom)}.
     */
    public V3D_Vector getVector(int oom) {
        return rel.add(offset, oom);
    }

    /**
     * @param oom The Order of Magnitude for the application of {@link #offset}.
     * @return The x component of {@link #rel} with {@link #offset} applied.
     */
    public Math_BigRational getX(int oom) {
        return rel.getDX(oom).add(offset.getDX(oom));
    }

    /**
     * @param oom The Order of Magnitude for the application of {@link #offset}.
     * @return The y component of {@link #rel} with {@link #offset} applied.
     */
    public Math_BigRational getY(int oom) {
        return rel.getDY(oom).add(offset.getDY(oom));
    }

    /**
     * @param oom The Order of Magnitude for the application of {@link #offset}.
     * @return The z component of {@link #rel} with {@link #offset} applied.
     */
    public Math_BigRational getZ(int oom) {
        return rel.getDZ(oom).add(offset.getDZ(oom));
    }

    /**
     * @return true iff this is equal to the ORIGIN.
     */
    public boolean isOrigin() {
        return equals(ORIGIN);
    }

    /**
     * Get the distance between this and {@code p}.
     *
     * @param oom The Order of Magnitude for the precision of the result.
     * @param p A point.
     * @return The distance from {@code p} to this.
     */
    public Math_BigRationalSqrt getDistance(int oom, V3D_Point p) {
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
    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        if (this.equals(p)) {
            return BigDecimal.ZERO;
        }
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
    @Override
    public Math_BigRational getDistanceSquared(V3D_Point p, int oom) {
        Math_BigRational dx = this.getX(oom).subtract(p.getX(oom));
        Math_BigRational dy = this.getY(oom).subtract(p.getY(oom));
        Math_BigRational dz = this.getZ(oom).subtract(p.getZ(oom));
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
        V3D_Vector cp = new V3D_Vector(this, l.getP(oom), oom).getCrossProduct(
                new V3D_Vector(this, l.getQ(oom), oom), oom);
        return cp.getMagnitude().divide(l.getV(oom).getMagnitude(), oom).toBigDecimal(oom);
//        return cp.getMagnitude(oom - 1).divide(l.v.getMagnitude(oom - 1), -oom,
//                RoundingMode.HALF_UP);
    }
    
    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom) {
        return l.getDistanceSquared(this, oom);
//        if (l.isIntersectedBy(this, oom)) {
//            return Math_BigRational.ZERO;
//        }
//        return getDistanceSquared(l, true, oom);
    }

    /**
     * @param l The line to find the distance of {@code this} from. 
     * @param oom The Order of Magnitude for the precision of the result.
     * @param noInt A flag to indicate that {@code this} is not on {@code l}.
     * @return The distance squared between {@code this} and {@code l}.
     */
    public Math_BigRational getDistanceSquared(V3D_Line l, boolean noInt, int oom) {
        V3D_Vector cp = new V3D_Vector(this, l.getP(oom), oom).getCrossProduct(
                new V3D_Vector(this, l.getQ(oom), oom), oom);
        return cp.getMagnitudeSquared().divide(l.getV(oom).getMagnitudeSquared());
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pl A plane.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The distance from {@code p} to this.
     */
    @Override
    public BigDecimal getDistance(V3D_Plane pl, int oom) {
        return pl.getDistance(this, oom);
    }

    /**
     * Get the distance between this and {@code pl}. Nykamp DQ, “Distance from
     * point to plane.” From Math Insight.
     * http://mathinsight.org/distance_point_plane
     *
     * @param pl A plane.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The distance from {@code p} to this.
     */
    @Override
    public Math_BigRational getDistanceSquared(V3D_Plane pl, int oom) {
        //V3D_Vector pq = new V3D_Vector(this, pl.p, oom);
        //V3D_Vector pq = pl.p.subtract(this.getVector(oom), oom);
        V3D_Vector pq = pl.getP().getVector(oom).subtract(this.getVector(oom), oom);
        if (pq.isScalarMultiple(pl.getN(oom), oom)) {
            return pq.getMagnitudeSquared();
        } else {
            Math_BigRational[] coeffs = pl.getEquationCoefficients();
            Math_BigRational num = (coeffs[0].multiply(getX(oom))
                    .add(coeffs[1].multiply(getY(oom)))
                    .add(coeffs[2].multiply(getZ(oom)))
                    .add(coeffs[3])).abs();
            Math_BigRational den = coeffs[0].pow(2).add(coeffs[1].pow(2))
                    .add(coeffs[2].pow(2));
            return num.divide(den);
        }
    }

    @Override
    public V3D_Envelope getEnvelope() {
        return new V3D_Envelope(e, getX(e.oom), getY(e.oom), getZ(e.oom));
    }

    @Override
    public boolean isIntersectedBy(V3D_Point p, int oom) {
        return this.equals(p);
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        return l.isIntersectedBy(this, oom);
    }

    @Override
    public boolean isIntersectedBy(V3D_Ray r, int oom) {
        return r.isIntersectedBy(this, oom);
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom) {
        return l.isIntersectedBy(this, oom);
    }

    @Override
    public boolean isIntersectedBy(V3D_Plane p, int oom) {
        return p.isIntersectedBy(this, oom);
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
    public V3D_Geometry getIntersection(V3D_Ray r, int oom) {
        if (r.isIntersectedBy(this, oom)) {
            return this;
        }
        return null;
    }

    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom) {
        if (l.isIntersectedBy(this, oom)) {
            return this;
        }
        return null;
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
        Math_BigRational l2 = l.getLength2(oom);
        Math_BigRational lp2 = l.getP(oom).getDistanceSquared(this, oom2);
        Math_BigRational lq2 = l.getQ(oom).getDistanceSquared(this, oom2);
        BigDecimal lp = (new V3D_Line(l)).getDistance(this, oom);
        if (lp2.compareTo(l2) != 1 || lq2.compareTo(l2) != 1) {
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
        BigDecimal pl = (new V3D_Line(l)).getDistance(this, oom2);
        Math_BigRational pl2 = Math_BigRational.valueOf(pl).pow(2);
        V3D_Vector u = l.getV(oom).getUnitVector(oom - 2);
        V3D_Point pi = new V3D_Point(e, u.multiply(Math_BigRational.valueOf(
                new Math_BigRationalSqrt(lp2.subtract(pl2), oom2)
                        .toBigDecimal(oom2)), oom2)
                .add(new V3D_Vector(l.getP(oom), oom2), oom2));
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
    @Override
    public BigDecimal getDistance(V3D_Ray r, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(r, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
    }

    /**
     * @param r The ray to get the distance from.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance between {@code this} and {@code r}.
     */
    @Override
    public Math_BigRational getDistanceSquared(V3D_Ray r, int oom) {
        return r.getDistanceSquared(this, oom);
//        V3D_Point pt = r.getPointOfIntersection(this, oom);
//        return pt.getDistanceSquared(this, oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the result.
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
    public int getLocation(int oom) {
        if (getX(oom).compareTo(Math_BigRational.ZERO) != -1) {
            if (getY(oom).compareTo(Math_BigRational.ZERO) != -1) {
                if (getZ(oom).compareTo(Math_BigRational.ZERO) != -1) {
                    if (isOrigin()) {
                        return 0;
                    }
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (getZ(oom).compareTo(Math_BigRational.ZERO) != -1) {
                    return 3;
                } else {
                    return 4;
                }
            }
        } else {
            if (getY(oom).compareTo(Math_BigRational.ZERO) != -1) {
                if (getZ(oom).compareTo(Math_BigRational.ZERO) != -1) {
                    return 5;
                } else {
                    return 6;
                }
            } else {
                if (getZ(oom).compareTo(Math_BigRational.ZERO) != -1) {
                    return 7;
                } else {
                    return 8;
                }
            }
        }
    }

    /**
     * Change {@link #offset} without changing the overall point vector by also
     * adjusting {@link #rel}.
     *
     * @param offset What {@link #offset} is set to.
     */
    public void setOffset(V3D_Vector offset) {
        rel = getVector(e.oom).subtract(offset, e.oom);
        this.offset = offset;
    }

    /**
     * Change {@link #rel} without changing the overall point vector by also
     * adjusting {@link #offset}.
     *
     * @param rel What {@link #rel} is set to.
     */
    public void setRel(V3D_Vector rel) {
        //offset = getVector(e.oom).subtract(rel, e.oom);
        offset = offset.subtract(rel, e.oom).add(this.rel, e.oom);
        this.rel = rel;
    }
    
    /**
     * Move the line.
     *
     * @param v What is added to {@link #offset}.
     */
    public void translate(V3D_Vector v) {
        this.offset = offset.add(v, e.oom);
    }

    /**
     * Rotates the point about {@link offset}.
     *
     * @param axisOfRotation The axis of rotation.
     * @param theta The angle of rotation.
     */
    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta) {
        rel = rel.rotate(axisOfRotation, theta, e.bI, e.oom);
//        V3D_Vector relt = rel.rotate(axisOfRotation, theta, bI, oom);
//        offset = offset.subtract(rel.subtract(relt, oom), oom);
    }

    @Override
    public int compareTo(V3D_Point o) {
        int cx = getX(e.oom).compareTo(o.getX(e.oom));
        switch (cx) {
            case 1 -> {
                return 1;
            }
            case 0 -> {
                int cy = getY(e.oom).compareTo(o.getY(e.oom));
                return switch (cy) {
                    case 1 -> 1;
                    case 0 -> getZ(e.oom).compareTo(o.getZ(e.oom));
                    default -> -1;
                };
            }
            default -> {
                return -1;
            }
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Triangle t, int oom) {
        return t.isIntersectedBy(this, oom);
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom) {
        return t.isIntersectedBy(this, oom);
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Plane p, int oom) {
        if (p.isIntersectedBy(this, oom)) {
            return this;
        }
        return null;
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Triangle t, int oom) {
        if (t.isIntersectedBy(this, oom)) {
            return this;
        }
        return null;
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Tetrahedron t, int oom) {
        if (t.isIntersectedBy(this, oom)) {
            return this;
        }
        return null;
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom) {
        return l.getDistanceSquared(this, oom);
    }

    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom) {
        return t.getDistance(this, oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom) {
        return t.getDistanceSquared(this, oom);
    }

    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom) {
        return t.getDistance(this, oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom) {
        return t.getDistanceSquared(this, oom);
    }
}
