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
package uk.ac.leeds.ccg.v3d.geometry.d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import uk.ac.leeds.ccg.math.arithmetic.Math_Double;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

/**
 * A point is defined by two vectors: {@link #offset} and {@link #rel}. Adding
 * these gives the position of a point. Two points are equal according to
 * {@link #equals(uk.ac.leeds.ccg.v3d.geometry.d.V3D_Point)} if they have the
 * same position. The "*" denotes a point in 3D in the following depiction: {@code
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
 * @version 1.0
 */
public class V3D_Point_d extends V3D_FiniteGeometry_d {

    private static final long serialVersionUID = 1L;

    /**
     * The origin of the Euclidean space.
     */
    public static final V3D_Point_d ORIGIN = new V3D_Point_d(null, 0, 0, 0);

    /**
     * The position relative to the {@link #offset}. Taken together with
     * {@link #offset}, this gives the point location.
     */
    public V3D_Vector_d rel;

    /**
     * Create a new instance which is completely independent of {@code pv}.
     *
     * @param p The point to clone/duplicate.
     */
    public V3D_Point_d(V3D_Point_d p) {
        super(p.env, new V3D_Vector_d(p.offset));
        rel = new V3D_Vector_d(p.rel);
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector_d#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param rel Cloned to initialise {@link #rel}.
     */
    public V3D_Point_d(V3D_Environment_d env, V3D_Vector_d rel) {
        this(env, V3D_Vector_d.ZERO, rel);
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param rel Cloned to initialise {@link #rel}.
     */
    public V3D_Point_d(V3D_Environment_d env, V3D_Vector_d offset, 
            V3D_Vector_d rel) {
        super(env, offset);
        this.rel = new V3D_Vector_d(rel);
    }

    /**
     * Create a new instance with {@link #offset} set to
     * {@link V3D_Vector_d#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param x What {@link #rel} x component is set to.
     * @param y What {@link #rel} y component is set to.
     * @param z What {@link #rel} z component is set to.
     */
    public V3D_Point_d(V3D_Environment_d env, double x, double y, double z) {
        super(env, V3D_Vector_d.ZERO);
        rel = new V3D_Vector_d(x, y, z);
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
                + toStringFields("") + ")";
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
        return pad + super.toStringFields("") + ", rel=" + rel.toStringSimple("");
    }

    /**
     * Two points are equal if they are at the same location defined by each
     * points relative start location and translation vector.
     *
     * @param p The point to test if it is the same as {@code this}.
     * @return {@code true} iff {@code pv} is the same as {@code this}.
     */
    public boolean equals(V3D_Point_d p) {
        if (p == null) {
            return false;
        }
        if (getX() == p.getX()) {
            if (getY() == p.getY()) {
                if (getZ() == p.getZ()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * For determining if all points are coincident.
     *
     * @param ps The points to test if they are all equal.
     * @return {@code true} iff {@code pv} is the same as {@code this}.
     */
    public static boolean equals(V3D_Point_d... ps) {
        if (ps.length < 2) {
            return true;
        }
        for (int i = 1; i < ps.length; i++) {
            if (!ps[0].equals(ps[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * For determining if all points are coincident within a tolerance given by 
     * epsilon.
     *
     * @param p The point to test if it is the same as {@code this}.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return {@code true} iff {@code pv} is equal to {@code this} given the
     * epsilon.
     */
    public boolean equals(V3D_Point_d p, double epsilon) {
        if (p == null) {
            return false;
        }
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        double px = p.getX();
        double py = p.getY();
        double pz = p.getZ();
        return Math_Double.equals(x, px, epsilon)
                && Math_Double.equals(y, py, epsilon)
                && Math_Double.equals(z, pz, epsilon);
    }

    /**
     * Two points are equal if they are at the same location defined by each
     * points relative start location and translation vector.
     *
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @param ps The points to test if they are all equal.
     * @return {@code true} iff {@code pv} is the same as {@code this}.
     */
    public static boolean equals(double epsilon, V3D_Point_d... ps) {
        if (ps.length < 2) {
            return true;
        }
        for (int i = 1; i < ps.length; i++) {
            if (!ps[0].equals(ps[i], epsilon)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public V3D_Point_d[] getPointsArray() {
        V3D_Point_d[] r = new V3D_Point_d[1];
        r[0] = this;
        return r;
    }

    /**
     * @return The vector - {@code v.add(offset, oom)}.
     */
    public V3D_Vector_d getVector() {
        return rel.add(offset);
    }

    /**
     * @return The x component of {@link #rel} with {@link #offset} applied.
     */
    public double getX() {
        return rel.dx + offset.dx;
    }

    /**
     * @return The y component of {@link #rel} with {@link #offset} applied.
     */
    public double getY() {
        return rel.dy + offset.dy;
    }

    /**
     * @return The z component of {@link #rel} with {@link #offset} applied.
     */
    public double getZ() {
        return rel.dz + offset.dz;
    }

    /**
     * @return true iff this is equal to the ORIGIN.
     */
    public boolean isOrigin() {
        return equals(ORIGIN);
    }

    /**
     * Returns true if this is between a and b. If this equals a or b then
     * return true. Being between does not necessarily mean being collinear.
     *
     * @param a A point.
     * @param b A point.
     * @return true iff this is between a and b.
     */
    public boolean isAligned(V3D_Point_d a, V3D_Point_d b, double epsilon) {
        V3D_LineSegment_d ls = new V3D_LineSegment_d(epsilon, a, b);
        return ls.isAligned(this, epsilon);
    }

    /**
     * Get the distance between this and {@code pv}.
     *
     * @param p A point.
     * @return The distance from {@code pv} to this.
     */
    public double getDistance(V3D_Point_d p) {
        //if (this.equals(p)) {
        //    return 0d;
        //}
        return Math.sqrt(getDistanceSquared(p));
    }

    /**
     * Get the distance squared between this and {@code pt}.
     *
     * @param pt A point.
     * @return The distance squared from {@code pv} to this.
     */
    public double getDistanceSquared(V3D_Point_d pt) {
        double dx = getX() - pt.getX();
        double dy = getY() - pt.getY();
        double dz = getZ() - pt.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    @Override
    public V3D_AABB_d getAABB() {
        return new V3D_AABB_d(this);
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
        if (getX() >= 0d) {
            if (getY() >= 0d) {
                if (getZ() >= 0d) {
                    if (isOrigin()) {
                        return 0;
                    }
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (getZ() >= 0d) {
                    return 3;
                } else {
                    return 4;
                }
            }
        } else {
            if (getY() >= 0d) {
                if (getZ() >= 0d) {
                    return 5;
                } else {
                    return 6;
                }
            } else {
                if (getZ() >= 0d) {
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
    public void setOffset(V3D_Vector_d offset) {
        if (!offset.equals(this.offset)) {
            rel = getVector().subtract(offset);
            this.offset = offset;
        }
    }

    /**
     * Change {@link #rel} without changing the overall point vector by also
     * adjusting {@link #offset}.
     *
     * @param rel What {@link #rel} is set to.
     */
    public void setRel(V3D_Vector_d rel) {
        //offset = getVector(e.oom).subtract(v, e.oom);
        offset = offset.subtract(rel).add(this.rel);
        this.rel = rel;
    }
    
    @Override
    public V3D_Point_d rotate(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V3D_Point_d(this);
        } else {
            return rotateN(ray, uv, theta, epsilon);
        }
    }

    @Override
    public V3D_Point_d rotateN(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        V3D_Vector_d tv = ray.l.getPointOfIntersect(this, epsilon).getVector();
        V3D_Point_d tp = new V3D_Point_d(this);
        tp.translate(tv.reverse());
        V3D_Vector_d tpv = tp.getVector();
        V3D_Point_d r;
        if (tpv.isZero()) {
            r = new V3D_Point_d(env, tpv);
        } else {
            double magnitude = tpv.getMagnitude();
            V3D_Vector_d tpr = tpv.getUnitVector().rotate(uv, theta);
            r = new V3D_Point_d(env, tpr.multiply(magnitude));
        }
        r.translate(tv);
        return r;
    }

    /**
     * A collection method for getting unique points.
     *
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @param pts The points to derive a unique list from.
     * @return A unique list made from those in pts.
     */
    public static ArrayList<V3D_Point_d> getUnique(
            HashMap<Integer, V3D_Point_d> pts, double epsilon) {
        HashSet<Integer> indexes = new HashSet<>();
        ArrayList<V3D_Point_d> r = new ArrayList<>();
        for (int i = 0; i < pts.size(); i++) {
            if (!indexes.contains(i)) {
                V3D_Point_d p = pts.get(i);
                r.add(p);
                for (int j = i + 1; j < pts.size(); j++) {
                    if (!indexes.contains(j)) {
                        V3D_Point_d p2 = pts.get(j);
                        if (p.equals(p2, epsilon)) {
                            indexes.add(j);
                        }
                    }
                }
            }
        }
        return r;
    }
    
    /**
     * A collection method for getting unique points.
     *
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @param pts The points to derive a unique list from.
     * @return A unique list made from those in pts.
     */
    public static ArrayList<V3D_Point_d> getUnique(
            List<V3D_Point_d> pts, double epsilon) {
        HashSet<Integer> indexes = new HashSet<>();
        ArrayList<V3D_Point_d> r = new ArrayList<>();
        for (int i = 0; i < pts.size(); i++) {
            if (!indexes.contains(i)) {
                V3D_Point_d p = pts.get(i);
                r.add(p);
                for (int j = i + 1; j < pts.size(); j++) {
                    if (!indexes.contains(j)) {
                        V3D_Point_d p2 = pts.get(j);
                        if (p.equals(p2, epsilon)) {
                            indexes.add(j);
                        }
                    }
                }
            }
        }
        return r;
    }
}
