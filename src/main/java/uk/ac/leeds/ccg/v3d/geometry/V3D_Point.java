/*
 * Copyright 2020-2025 Andy Turner, University of Leeds.
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
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * A point is defined by two vectors: {@link #offset} and {@link #rel}. Adding
 * these gives the position of a point. Two points are equal according to
 * {@link #equals(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int, java.math.RoundingMode)}
 * if they have the same position. The "*" denotes a point in 3D in the
 * following depiction: {@code
 *
 *                             y           -
 *                             +          /                * pv=<x0,y0,z0>
 *                             |         /                 |  =offset+v
 *                             |        /                  |  =<x1+x2,y1+y2,z1+z2>
 *                             |    z0-/-------------------|
 *                  r          |      /                   /
 *            v=<x2,y2,z2>     |     /                   /
 *                             |    /                   /
 *                             |   /                   /      offset=<x1,y1,z1>
 *                          y0-|  /                   /                o
 *                             | /                   /
 *                             |/                   /
 *     - ----------------------|-------------------/---- + x
 *                            /|                  x0
 *                           / |
 *                          /  |
 *                         /   |
 *                        /    |
 *                       /     |
 *                      /      |
 *                     /       |
 *                    +        |
 *                   z         -
 * }
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V3D_Point extends V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The origin of the Euclidean space.
     */
    public static final V3D_Point ORIGIN = new V3D_Point(null, 0, 0, 0);

    /**
     * The position relative to the {@link #offset}. Taken together with
     * {@link #offset}, this gives the point location.
     */
    public V3D_Vector rel;

    public BigRational x;
    public int xoom;
    public RoundingMode xrm;

    public BigRational y;
    public int yoom;
    public RoundingMode yrm;

    public BigRational z;
    public int zoom;
    public RoundingMode zrm;
    
    /**
     * Create a new instance.
     *
     * @param p The point to clone/duplicate.
     */
    public V3D_Point(V3D_Point p) {
        super(p.env, new V3D_Vector(p.offset));
        rel = new V3D_Vector(p.rel);
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param rel Cloned to initialise {@link #rel}.
     */
    public V3D_Point(V3D_Environment env, V3D_Vector rel) {
        this(env, V3D_Vector.ZERO, rel);
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param rel Cloned to initialise {@link #rel}.
     */
    public V3D_Point(V3D_Environment env, V3D_Vector offset, V3D_Vector rel) {
        super(env, offset);
        this.rel = new V3D_Vector(rel);
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_Point(V3D_Environment env, BigRational x, BigRational y, 
            BigRational z) {
        super(env, V3D_Vector.ZERO);
        rel = new V3D_Vector(x, y, z);
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_Point(V3D_Environment env, BigDecimal x, BigDecimal y, 
            BigDecimal z) {
        this(env, BigRational.valueOf(x), BigRational.valueOf(y), 
                BigRational.valueOf(z));
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_Point(V3D_Environment env, double x, double y, double z) {
        this(env, BigRational.valueOf(x), BigRational.valueOf(y),
                BigRational.valueOf(z));
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_Point(V3D_Environment env, long x, long y, long z) {
        this(env, BigRational.valueOf(x), BigRational.valueOf(y),
                BigRational.valueOf(z));
    }

    @Override
    public String toString() {
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
        return pad + super.toStringFieldsSimple("") + ", rel=" 
                + rel.toStringSimple("");
    }
    
    /**
     * Two points are equal if they are at the same location defined by each
     * points relative and offset vectors at the given oom and rm precision.
     *
     * @param p The point to test if it is the same as {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code pv} is the same as {@code this}.
     */
    public boolean equals(V3D_Point p, int oom, RoundingMode rm) {
        if (p == null) {
            return false;
        } else {
            return getX(oom, rm).compareTo(p.getX(oom, rm)) == 0
                && getY(oom, rm).compareTo(p.getY(oom, rm)) == 0
                && getZ(oom, rm).compareTo(p.getZ(oom, rm)) == 0;
        }
    }

    /**
     * @param ps The points to test if they are coincident.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff all the points are coincident.
     */
    public static boolean equals(int oom, RoundingMode rm,
            V3D_Point... ps) {
        if (ps.length < 2) {
            return true;
        }
        for (int i = 1; i < ps.length; i++) {
            if (!ps[0].equals(ps[i], oom, rm)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public V3D_Point[] getPointsArray(int oom, RoundingMode rm) {
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
        //return rel.getDX(oom, rm).add(offset.getDX(oom, rm));
        if (x == null) {
            x = rel.getDX(oom, rm).add(offset.getDX(oom, rm));
        } else {
            if (oom < xoom) {
                x = rel.getDX(oom, rm).add(offset.getDX(oom, rm));
            } else {
                if (!rm.equals(xoom)) {
                    x = rel.getDX(oom, rm).add(offset.getDX(oom, rm));
                }
            }
                
        }
        return x;
    }

    /**
     * @param oom The Order of Magnitude for the application of {@link #offset}.
     * @param rm The RoundingMode if rounding is needed.
     * @return The y component of {@link #rel} with {@link #offset} applied.
     */
    public BigRational getY(int oom, RoundingMode rm) {
        //return rel.getDY(oom, rm).add(offset.getDY(oom, rm));
        if (y == null) {
            y = rel.getDY(oom, rm).add(offset.getDY(oom, rm));
        } else {
            if (oom < yoom) {
                y = rel.getDY(oom, rm).add(offset.getDY(oom, rm));
            } else {
                if (!rm.equals(yoom)) {
                    y = rel.getDY(oom, rm).add(offset.getDY(oom, rm));
                }
            }
                
        }
        return y;
    }

    /**
     * @param oom The Order of Magnitude for the application of {@link #offset}.
     * @param rm The RoundingMode if rounding is needed.
     * @return The z component of {@link #rel} with {@link #offset} applied.
     */
    public BigRational getZ(int oom, RoundingMode rm) {
        //return rel.getDZ(oom, rm).add(offset.getDZ(oom, rm));
        if (z == null) {
            z = rel.getDZ(oom, rm).add(offset.getDZ(oom, rm));
        } else {
            if (oom < zoom) {
                z = rel.getDZ(oom, rm).add(offset.getDZ(oom, rm));
            } else {
                if (!rm.equals(zoom)) {
                    z = rel.getDZ(oom, rm).add(offset.getDZ(oom, rm));
                }
            }
                
        }
        return z;
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
     * NB. Being aligned does not necessarily mean being collinear.
     * 
     * @param a A point
     * @param b A point
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return true if this is aligned with the line segment defined by a and b.
     */
    public boolean isAligned(V3D_Point a, V3D_Point b, int oom, RoundingMode rm) {
        V3D_LineSegment ls = new V3D_LineSegment(a, b, oom, rm);
        return ls.isAligned(this, oom, rm);
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
    public V3D_AABB getAABB(int oom, RoundingMode rm) {
        return new V3D_AABB(oom, this);
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

    @Override
    public V3D_Point rotate(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd, 
            BigRational theta, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V3D_Point(this);
        } else {
            return rotateN(ray, uv, bd, theta, oom, rm);
        }
    }
    
    /**
     * Rotates the point about {@link offset}.
     *
     * @param ray The axis of rotation.
     * @param theta The angle of rotation.
     * @param bd The  for obtaining PI and normalising angles.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     */
    @Override
    public V3D_Point rotateN(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd, 
            BigRational theta, int oom, RoundingMode rm) {
        int oomn9 = oom - 9;
        V3D_Vector tv = ray.l.getPointOfIntersect(this, oomn9, rm).getVector(oomn9, rm);
        V3D_Point tp = new V3D_Point(this);
        tp.translate(tv.reverse(), oomn9, rm);
        V3D_Vector tpv = tp.getVector(oomn9, rm);
        V3D_Point r;
        if (tpv.isZero()) {
            r = new V3D_Point(env, tpv);
        } else {
            Math_BigRationalSqrt magnitude = tpv.getMagnitude(oomn9, rm);
            V3D_Vector tpr = tpv.getUnitVector(oomn9, rm).rotateN(uv, bd, theta, oomn9, rm);
            r = new V3D_Point(env, tpr.multiply(magnitude.getSqrt(oomn9, rm), oomn9, rm));
        }
        r.translate(tv, oomn9, rm);
        return r;
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
//        HashSet<Integer> indexes = new HashSet<>();
//        ArrayList<V3D_Point> r = new ArrayList<>();
//        for (int i = 0; i < pts.size(); i++) {
//            if (!indexes.contains(i)) {
//                V3D_Point p = pts.get(i);
//                boolean added = false;
//                for (int j = i + 1; j < pts.size(); j++) {
//                    if (p.equals(pts.get(j), oom, rm)) {
//                        //r.add(p);
//                        r.add(new V3D_Point(p));
//                        indexes.add(j);
//                        added = true;
//                        break;
//                    }
//                }
//                if (!added) {
//                    //r.add(p);
//                    r.add(new V3D_Point(p));
//                }
//            }
//        }
        return r;
    }
}
