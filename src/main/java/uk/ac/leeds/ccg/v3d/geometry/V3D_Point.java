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
import java.math.RoundingMode;
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
public class V3D_Point extends V3D_FiniteGeometry implements 
        V3D_IntersectionAndDistanceCalculations, Comparable<V3D_Point> {

    private static final long serialVersionUID = 1L;

    /**
     * The origin of the Euclidean space.
     */
    public static final V3D_Point ORIGIN = new V3D_Point(new V3D_Environment(), 0, 0, 0);

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
        //return toString("");
        return toStringSimple("");
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toStringSimple(String pad) {
        return this.getClass().getSimpleName() + "("
                + toStringFieldsSimple("") + ")";
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

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFieldsSimple(String pad) {
        return pad + super.toStringFieldsSimple("") + ", rel=" + rel.toStringSimple("");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Point v3D_Point) {
            return equals(v3D_Point, e.oom, e.rm);
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
     * points relative start location and translation vector at the given oom 
     * and rm precision.
     *
     * @param p The point to test if it is the same as {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code p} is the same as {@code this}.
     */
    public boolean equals(V3D_Point p, int oom, RoundingMode rm) {
        if (this.getX(oom, rm).compareTo(p.getX(oom, rm)) == 0) {
            if (this.getY(oom, rm).compareTo(p.getY(oom, rm)) == 0) {
                if (this.getZ(oom, rm).compareTo(p.getZ(oom, rm)) == 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public V3D_Point[] getPoints() {
        V3D_Point[] r = new V3D_Point[1];
        r[0] = this;
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @return The vector - {@code rel.add(offset, oom)}.
     */
    public V3D_Vector getVector(int oom, RoundingMode rm) {
        return rel.add(offset, oom, rm);
    }

    /**
     * @return The x component of {@link #rel} with {@link #offset} applied. The
     * Order of Magnitude for the precision.
     */
    public Math_BigRational getX() {
        return getX(e.oom, e.rm);
    }
    
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The x component of {@link #rel} with {@link #offset} applied.
     */
    public Math_BigRational getX(int oom, RoundingMode rm) {
        return rel.getDX(oom, rm).add(offset.getDX(oom, rm));
    }

    /**
     * @return The y component of {@link #rel} with {@link #offset} applied.
     */
    public Math_BigRational getY() {
        return getY(e.oom, e.rm);
    }
    
    /**
     * @param oom The Order of Magnitude for the application of {@link #offset}.
     * @param rm The RoundingMode if rounding is needed.
     * @return The y component of {@link #rel} with {@link #offset} applied.
     */
    public Math_BigRational getY(int oom, RoundingMode rm) {
        return rel.getDY(oom, rm).add(offset.getDY(oom, rm));
    }

    /**
     * @return The z component of {@link #rel} with {@link #offset} applied.
     */
    public Math_BigRational getZ() {
        return getZ(e.oom, e.rm);
    }
    
    /**
     * @param oom The Order of Magnitude for the application of {@link #offset}.
     * @param rm The RoundingMode if rounding is needed.
     * @return The z component of {@link #rel} with {@link #offset} applied.
     */
    public Math_BigRational getZ(int oom, RoundingMode rm) {
        return rel.getDZ(oom, rm).add(offset.getDZ(oom, rm));
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
    public Math_BigRationalSqrt getDistance(int oom, RoundingMode rm, V3D_Point p) {
        if (this.equals(p)) {
            return Math_BigRationalSqrt.ZERO;
        }
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm);
    }

    /**
     * Get the distance between this and {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance from {@code p} to this.
     */
    @Override
    public BigDecimal getDistance(V3D_Point p, int oom, RoundingMode rm) {
        if (this.equals(p)) {
            return BigDecimal.ZERO;
        }
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    /**
     * Get the distance squared between this and {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared from {@code p} to this.
     */
    @Override
    public Math_BigRational getDistanceSquared(V3D_Point p, int oom, 
            RoundingMode rm) {
        Math_BigRational dx = this.getX(oom, rm).subtract(p.getX(oom, rm));
        Math_BigRational dy = this.getY(oom, rm).subtract(p.getY(oom, rm));
        Math_BigRational dz = this.getZ(oom, rm).subtract(p.getZ(oom, rm));
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
    public BigDecimal getDistance(V3D_Line l, int oom, RoundingMode rm) {
        if (l.isIntersectedBy(this, oom, rm)) {
            return BigDecimal.ZERO;
        }
        // Not sure what oom should be in the cross product...
        V3D_Vector cp = new V3D_Vector(this, l.getP(), oom, rm)
                .getCrossProduct(new V3D_Vector(this, l.getQ(), oom, rm),
                        oom, rm);
        return cp.getMagnitude().divide(l.getV(oom, rm).getMagnitude(), oom, rm).toBigDecimal(oom, rm);
//        return cp.getMagnitude(oom - 1).divide(l.v.getMagnitude(oom - 1), -oom,
//                RoundingMode.HALF_UP);
    }
    
    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom, 
            RoundingMode rm) {
        return l.getDistanceSquared(this, oom, rm);
//        if (l.isIntersectedBy(this, oom)) {
//            return Math_BigRational.ZERO;
//        }
//        return getDistanceSquared(l, true, oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Ray r, int oom, 
            RoundingMode rm) {
        return r.getDistanceSquared(this, oom, rm);
    }

    /**
     * @param l The line to find the distance of {@code this} from. 
     * @param oom The Order of Magnitude for the precision of the result.
     * @param noInt A flag to indicate that {@code this} is not on {@code l}.
     * @return The distance squared between {@code this} and {@code l}.
     */
    public Math_BigRational getDistanceSquared(V3D_Line l, boolean noInt, 
            int oom, RoundingMode rm) {
        V3D_Vector cp = new V3D_Vector(this, l.getP(), oom, rm)
                .getCrossProduct(new V3D_Vector(this, l.getQ(), oom, rm),
                        oom, rm);
        return cp.getMagnitudeSquared().divide(l.getV(oom, rm).getMagnitudeSquared());
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pl A plane.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The distance from {@code p} to this.
     */
    @Override
    public BigDecimal getDistance(V3D_Plane pl, int oom, RoundingMode rm) {
        return pl.getDistance(this, oom, rm);
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
    public Math_BigRational getDistanceSquared(V3D_Plane pl, int oom, 
            RoundingMode rm) {
        //V3D_Vector pq = new V3D_Vector(this, pl.p, oom);
        //V3D_Vector pq = pl.p.subtract(this.getVector(oom), oom);
        V3D_Vector pq = pl.getP().getVector(oom, rm).subtract(this.getVector(oom, rm), oom, rm);
        if (pq.isScalarMultiple(pl.getN(oom, rm), oom, rm)) {
            return pq.getMagnitudeSquared();
        } else {
            Math_BigRational[] coeffs = pl.getEquationCoefficients();
            Math_BigRational num = (coeffs[0].multiply(getX(oom, rm))
                    .add(coeffs[1].multiply(getY(oom, rm)))
                    .add(coeffs[2].multiply(getZ(oom, rm)))
                    .add(coeffs[3])).abs();
            Math_BigRational den = coeffs[0].pow(2).add(coeffs[1].pow(2))
                    .add(coeffs[2].pow(2));
            return num.divide(den).round(oom, rm);
        }
    }

    @Override
    public V3D_Envelope getEnvelope() {
        return new V3D_Envelope(e, getX(), getY(), getZ());
    }

    @Override
    public boolean isIntersectedBy(V3D_Point p, int oom, RoundingMode rm) {
        return this.equals(p, oom, rm);
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom, RoundingMode rm) {
        return l.isIntersectedBy(this, oom, rm);
    }

    @Override
    public boolean isIntersectedBy(V3D_Ray r, int oom, RoundingMode rm) {
        return r.isIntersectedBy(this, oom, rm);
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, RoundingMode rm) {
        return l.isIntersectedBy(this, oom, rm);
    }

    @Override
    public boolean isIntersectedBy(V3D_Plane p, int oom, RoundingMode rm) {
        return p.isIntersectedBy(this, oom, rm);
    }

    /**
     * @param l The line to intersect with {@code this}.
     * @return {@code this} if the point is on the line {@code l} else return
     * {@code null}.
     */
    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Line l, int oom,
            RoundingMode rm) {
        if (l.isIntersectedBy(this, oom, rm)) {
            return this;
        }
        return null;
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Ray r, int oom,
            RoundingMode rm) {
        if (r.isIntersectedBy(this, oom, rm)) {
            return this;
        }
        return null;
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment l, int oom,
            RoundingMode rm) {
        if (l.isIntersectedBy(this, oom, rm)) {
            return this;
        }
        return null;
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom, RoundingMode rm) {
        /**
         * Get the distance from the ends of the line segment to the point. If
         * both these distances is less than the length of the line segment then
         * the distance is the distance from the point to the infinite line.
         *
         */
        int oom2 = oom - 2;
        Math_BigRational l2 = l.getLength2(oom, rm);
        Math_BigRational lp2 = l.getP().getDistanceSquared(this, oom2, rm);
        Math_BigRational lq2 = l.getQ().getDistanceSquared(this, oom2, rm);
        BigDecimal lp = (new V3D_Line(l)).getDistance(this, oom, rm);
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
        BigDecimal pl = (new V3D_Line(l)).getDistance(this, oom2, rm);
        Math_BigRational pl2 = Math_BigRational.valueOf(pl).pow(2);
        V3D_Vector u = l.l.getV(oom, rm).getUnitVector(oom - 2, rm);
        V3D_Point pi = new V3D_Point(e, u.multiply(Math_BigRational.valueOf(
                new Math_BigRationalSqrt(lp2.subtract(pl2), oom2, rm)
                        .toBigDecimal(oom2, rm)), oom2, rm)
                .add(new V3D_Vector(l.getP(), oom2, rm), oom2, rm));
        if (l.isIntersectedBy(pi, oom, rm)) {
            return lp;
        } else {
            return new Math_BigRationalSqrt(Math_BigRational.min(lp2, lq2),
                    oom2, rm).toBigDecimal(oom, rm);
        }
    }

    /**
     * @param r The ray to get the distance from.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance between {@code this} and {@code r}.
     */
    @Override
    public BigDecimal getDistance(V3D_Ray r, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(r, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
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
    public int getLocation(int oom, RoundingMode rm) {
        if (getX(oom, rm).compareTo(Math_BigRational.ZERO) != -1) {
            if (getY(oom, rm).compareTo(Math_BigRational.ZERO) != -1) {
                if (getZ(oom, rm).compareTo(Math_BigRational.ZERO) != -1) {
                    if (isOrigin()) {
                        return 0;
                    }
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (getZ(oom, rm).compareTo(Math_BigRational.ZERO) != -1) {
                    return 3;
                } else {
                    return 4;
                }
            }
        } else {
            if (getY(oom, rm).compareTo(Math_BigRational.ZERO) != -1) {
                if (getZ(oom, rm).compareTo(Math_BigRational.ZERO) != -1) {
                    return 5;
                } else {
                    return 6;
                }
            } else {
                if (getZ(oom, rm).compareTo(Math_BigRational.ZERO) != -1) {
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
        rel = getVector(e.oom, e.rm).subtract(offset, e.oom, e.rm);
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
        offset = offset.subtract(rel, e.oom, e.rm).add(this.rel, e.oom, e.rm);
        this.rel = rel;
    }
    
    /**
     * Move the line.
     *
     * @param v What is added to {@link #offset}.
     */
    public void translate(V3D_Vector v) {
        this.offset = offset.add(v, e.oom, e.rm);
    }

    /**
     * Rotates the point about {@link offset}.
     *
     * @param axisOfRotation The axis of rotation.
     * @param theta The angle of rotation.
     */
    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta) {
        rel = rel.rotate(axisOfRotation, theta, e.bI, e.oom, e.rm);
//        V3D_Vector relt = rel.rotate(axisOfRotation, theta, bI, oom);
//        offset = offset.subtract(rel.subtract(relt, oom), oom);
    }

    @Override
    public int compareTo(V3D_Point o) {
        int cx = getX().compareTo(o.getX());
        switch (cx) {
            case 1 -> {
                return 1;
            }
            case 0 -> {
                int cy = getY().compareTo(o.getY());
                return switch (cy) {
                    case 1 -> 1;
                    case 0 -> getZ().compareTo(o.getZ());
                    default -> -1;
                };
            }
            default -> {
                return -1;
            }
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Triangle t, int oom, RoundingMode rm) {
        return t.isIntersectedBy(this, oom, rm);
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom, 
            RoundingMode rm) {
        return t.isIntersectedBy(this, oom, rm);
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Plane p, int oom, 
            RoundingMode rm) {
        if (p.isIntersectedBy(this, oom, rm)) {
            return this;
        }
        return null;
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Triangle t, int oom, 
            RoundingMode rm) {
        if (t.isIntersectedBy(this, oom, rm)) {
            return this;
        }
        return null;
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Tetrahedron t, int oom, 
            RoundingMode rm) {
        if (t.isIntersectedBy(this, oom, rm)) {
            return this;
        }
        return null;
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom, 
            RoundingMode rm) {
        return l.getDistanceSquared(this, oom, rm);
    }

    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom, RoundingMode rm) {
        return t.getDistance(this, oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom, 
            RoundingMode rm) {
        return t.getDistanceSquared(this, oom, rm);
    }

    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        return t.getDistance(this, oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom, 
            RoundingMode rm) {
        return t.getDistanceSquared(this, oom, rm);
    }
    
    
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
}
