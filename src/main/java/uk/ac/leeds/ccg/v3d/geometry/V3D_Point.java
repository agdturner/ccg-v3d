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

import ch.obermuhlner.math.big.BigRational;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.geometry.light.V3D_V;

/**
 * A point is defined by two vectors: {@link #offset} and {@link #rel}. Adding
 * these gives the position of a point. Two points are equal according to
 * {@link #equals(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int, java.math.RoundingMode)}
 * if they have the same position. The "*" denotes a point in 3D in the
 * following depiction: {@code
 *
 * y           -
 * +          /                * pv=<x0,y0,z0>
 * |         /                 |  =offset+v
 * |        /                  |  =<x1+x2,y1+y2,z1+z2>
 * |    z0-/-------------------|
 * r          |      /                   /
 * v=<x2,y2,z2>     |     /                   /
 * |    /                   /
 * |   /                   /      offset=<x1,y1,z1>
 * y0-|  /                   /                o
 * | /                   /
 * |/                   /
 * - ----------------------|-------------------/---- + x
 * /|                  x0
 * / |
 * /  |
 * /   |
 * /    |
 * /     |
 * /      |
 * /       |
 * +        |
 * z         -
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Point extends V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The origin of the Euclidean space.
     */
    public static final V3D_Point ORIGIN = new V3D_Point(0, 0, 0);

    /**
     * The position relative to the {@link #offset}. Taken together with
     * {@link #offset}, this gives the point location.
     */
    public V3D_Vector rel;

    /**
     * Create a new instance which is completely independent of {@code pv}.
     *
     * @param p The point to clone/duplicate.
     */
    public V3D_Point(V3D_Point p) {
        super(new V3D_Vector(p.offset));
        this.rel = new V3D_Vector(p.rel);
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param rel Cloned to initialise {@link #rel}.
     */
    public V3D_Point(V3D_Vector rel) {
        this(V3D_Vector.ZERO, rel);
    }

    /**
     * Create a new instance.
     *
     * @param offset Cloned to initialise {@link #offset}.
     * @param rel Cloned to initialise {@link #rel}.
     */
    public V3D_Point(V3D_Vector offset, V3D_Vector rel) {
//        super(new V3D_Vector(offset), Math.min(offset.getMagnitude().getOom(),
//                v.getMagnitude().getOom()));
        //super(e, new V3D_Vector(offset));
        super(offset);
        this.rel = new V3D_Vector(rel);
    }

    /**
     * Create a new instance.
     *
     * @param v The point to clone/duplicate.
     */
    public V3D_Point(V3D_V v) {
        super(V3D_Vector.ZERO);
        this.rel = new V3D_Vector(v);
    }

//    /**
//     * Create a new instance with {@link #offset} set to
//     * {@link V3D_Vector#ZERO}.
//     *
//     * @param pv Used to initialise {@link #v} and {@link #e}.
//     */
//    public V3D_Point(V3D_Envelope.Point pv) {
//        super(pv.e, V3D_Vector.ZERO);
//        this.v = new V3D_Vector(pv);
//    }
    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_Point(BigRational x, BigRational y,
            BigRational z) {
        super(V3D_Vector.ZERO);
        this.rel = new V3D_Vector(x, y, z);
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_Point(BigDecimal x, BigDecimal y,
            BigDecimal z) {
        this(BigRational.valueOf(x), BigRational.valueOf(y),
                BigRational.valueOf(z));
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_Point(double x, double y, double z) {
        this(BigRational.valueOf(x), BigRational.valueOf(y),
                BigRational.valueOf(z));
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_Point(long x, long y, long z) {
        this(BigRational.valueOf(x), BigRational.valueOf(y),
                BigRational.valueOf(z));
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

    /**
     * Two points are equal if they are at the same location defined by each
     * points relative start location and translation vector at the given oom
     * and rm precision.
     *
     * @param p The point to test if it is the same as {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code pv} is the same as {@code this}.
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
    public V3D_Point[] getPoints(int oom, RoundingMode rm) {
        V3D_Point[] r = new V3D_Point[1];
        r[0] = this;
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return The vector - {@code v.add(offset, oom)}.
     */
    public V3D_Vector getVector(int oom, RoundingMode rm) {
        return rel.add(offset, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The x component of {@link #rel} with {@link #offset} applied.
     */
    public BigRational getX(int oom, RoundingMode rm) {
        return rel.getDX(oom, rm).add(offset.getDX(oom, rm));
    }

    /**
     * @param oom The Order of Magnitude for the application of {@link #offset}.
     * @param rm The RoundingMode if rounding is needed.
     * @return The y component of {@link #rel} with {@link #offset} applied.
     */
    public BigRational getY(int oom, RoundingMode rm) {
        return rel.getDY(oom, rm).add(offset.getDY(oom, rm));
    }

    /**
     * @param oom The Order of Magnitude for the application of {@link #offset}.
     * @param rm The RoundingMode if rounding is needed.
     * @return The z component of {@link #rel} with {@link #offset} applied.
     */
    public BigRational getZ(int oom, RoundingMode rm) {
        return rel.getDZ(oom, rm).add(offset.getDZ(oom, rm));
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return true iff this is equal to the ORIGIN.
     */
    public boolean isOrigin(int oom, RoundingMode rm) {
        return equals(ORIGIN, oom, rm);
    }

    /**
     * Get the distance between this and {@code pv}.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @param p A point.
     * @return The distance from {@code pv} to this.
     */
    public Math_BigRationalSqrt getDistance(int oom, RoundingMode rm, V3D_Point p) {
        if (this.equals(p, oom, rm)) {
            return Math_BigRationalSqrt.ZERO;
        }
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm);
    }

    /**
     * Get the distance between this and {@code pv}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return The distance from {@code pv} to this.
     */
    public BigRational getDistance(V3D_Point p, int oom, RoundingMode rm) {
        if (this.equals(p, oom, rm)) {
            return BigRational.ZERO;
        }
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom - 6, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * Get the distance squared between this and {@code pt}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared from {@code pv} to this.
     */
    public BigRational getDistanceSquared(V3D_Point pt, int oom,
            RoundingMode rm) {
        int oomn6 = oom - 6;
        BigRational dx = getX(oomn6, rm).subtract(pt.getX(oomn6, rm));
        BigRational dy = getY(oomn6, rm).subtract(pt.getY(oomn6, rm));
        BigRational dz = getZ(oomn6, rm).subtract(pt.getZ(oomn6, rm));
        return dx.pow(2).add(dy.pow(2)).add(dz.pow(2));
    }

    @Override
    public V3D_Envelope getEnvelope(int oom, RoundingMode rm) {
        return new V3D_Envelope(oom, rm, this);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
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
        if (getX(oom, rm).compareTo(BigRational.ZERO) != -1) {
            if (getY(oom, rm).compareTo(BigRational.ZERO) != -1) {
                if (getZ(oom, rm).compareTo(BigRational.ZERO) != -1) {
                    if (isOrigin(oom, rm)) {
                        return 0;
                    }
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (getZ(oom, rm).compareTo(BigRational.ZERO) != -1) {
                    return 3;
                } else {
                    return 4;
                }
            }
        } else {
            if (getY(oom, rm).compareTo(BigRational.ZERO) != -1) {
                if (getZ(oom, rm).compareTo(BigRational.ZERO) != -1) {
                    return 5;
                } else {
                    return 6;
                }
            } else {
                if (getZ(oom, rm).compareTo(BigRational.ZERO) != -1) {
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @param offset What {@link #offset} is set to.
     */
    public void setOffset(V3D_Vector offset, int oom, RoundingMode rm) {
        if (!offset.equals(this.offset, oom, rm)) {
            rel = getVector(oom, rm).subtract(offset, oom, rm);
            this.offset = offset;
        }
    }

    /**
     * Change {@link #rel} without changing the overall point vector by also
     * adjusting {@link #offset}.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @param rel What {@link #rel} is set to.
     */
    public void setRel(V3D_Vector rel, int oom, RoundingMode rm) {
        //offset = getVector(e.oom).subtract(v, e.oom);
        offset = offset.subtract(rel, oom, rm).add(this.rel, oom, rm);
        this.rel = rel;
    }

    /**
     * Rotates the point about {@link offset}.
     *
     * @param axis The axis of rotation.
     * @param theta The angle of rotation.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     */
    @Override
    public V3D_Point rotate(V3D_Line axis, BigRational theta,
            int oom, RoundingMode rm) {
        int oomn9 = oom - 9;
        BigRational na = V3D_Angle.normalise(theta, oom, rm);
        if (na.compareTo(BigRational.ZERO) == 0) {
            return new V3D_Point(this);
        }
        V3D_Vector tv = axis.getP().getVector(oomn9, rm);
        V3D_Point tp = new V3D_Point(this);
        tp.translate(tv, oomn9, rm);
        V3D_Vector rv = axis.v.getUnitVector(oomn9, rm);
        V3D_Vector tpr = tp.getVector(oomn9, rm).rotate(rv, na, oomn9, rm);
        V3D_Point r = new V3D_Point(tpr);
        r.translate(tv.reverse(), oom, rm);
        return r;
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff all the points are coincident.
     */
    public static boolean isCoincident(int oom, RoundingMode rm,
            V3D_Point... points) {
        V3D_Point p0 = points[0];
        for (V3D_Point p1 : points) {
            if (!p1.equals(p0, oom, rm)) {
                return false;
            }
        }
        return true;
    }

    /**
     * A collection method for getting unique points.
     *
     * @param pts The points to derive a unique list from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return A unique list made from those in pts.
     */
    public static ArrayList<V3D_Point> getUnique(List<V3D_Point> pts,
            int oom, RoundingMode rm) {
        HashSet<Integer> indexes = new HashSet<>();
        ArrayList<V3D_Point> r = new ArrayList<>();
        for (int i = 0; i < pts.size(); i++) {
            if (!indexes.contains(i)) {
                V3D_Point p = pts.get(i);
                r.add(p);
                for (int j = i + 1; j < pts.size(); j++) {
                    if (!indexes.contains(j)) {
                        V3D_Point p2 = pts.get(j);
                        if (p.equals(p2, oom, rm)) {
                            indexes.add(j);
                        }
                    }
                }
            }
        }
        return r;
    }
}
