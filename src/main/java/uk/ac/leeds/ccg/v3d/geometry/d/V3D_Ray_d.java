/*
 * Copyright 2021 Andy Turner, University of Leeds.
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

import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

/**
 * 3D representation of a ray - like a line, but one that starts at a point
 * continues infinitely in only one direction. The ray begins at the point of
 * {@link #l} and goes in the direction given by the vector of {@link #l}. The
 * "*" denotes a point in 3D and the ray is shown as a linear feature of "e"
 * symbols in the following depiction: {@code
 *                                     z
 *                         y           -
 *                         +          /                * pl=<x0,y0,z0>
 *                         |         /                e
 *                         |    z0 -/                e
 *                         |       /                e
 *                         |      /                e
 *                         |     /               e
 *                         |    /               e
 *                         |   /               e
 *                      y0-|  /               e
 *                         | /               e
 *                         |/         x1    e
 * - ----------------------|-----------|---e---|---- + x
 *                        /|              e   x0
 *                       / |-y1          e
 *                      /  |           e
 *                     /   |          e
 *                z1 -/    |         e
 *                   /     |        e
 *                  /      |       * v=(dx,dy,dz)
 *                 /       |      e
 *                /        |     e
 *               +         -    e
 *              z          y   e
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Ray_d extends V3D_Geometry_d {

    private static final long serialVersionUID = 1L;

    /**
     * The line of this ray.
     */
    public V3D_Line_d l;

    /**
     * For storing the plane at {@link #l} pv with normal in direction of the
     * ray vector.
     */
    private V3D_Plane_d pl;

    /**
     * Create a new instance.
     *
     * @param r What {@code this} is created from.
     */
    public V3D_Ray_d(V3D_Ray_d r) {
        super(r.env, r.offset);
        l = new V3D_Line_d(r.l);
    }

    /**
     * Create a new instance.
     *
     * @param p What {@code this} is created from.
     * @param v What {@code this} is created from.
     */
    public V3D_Ray_d(V3D_Point_d p, V3D_Vector_d v) {
        super(p.env, p.offset);
        l = new V3D_Line_d(p, v);
    }

    /**
     * Create a new instance. {@link #offset} is set to
     * {@link V3D_Vector_d#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param p What {@code this} is created from.
     * @param q What {@code this} is created from.
     */
    public V3D_Ray_d(V3D_Environment_d env, V3D_Vector_d p, V3D_Vector_d q) {
        this(env, V3D_Vector_d.ZERO, p, q);
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #l} point is set to.
     * @param q What {@link #l} vector is set from.
     */
    public V3D_Ray_d(V3D_Environment_d env, V3D_Vector_d offset, V3D_Vector_d p,
            V3D_Vector_d q) {
        super(env, offset);
        l = new V3D_Line_d(env, offset, p, q);
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_Ray_d(V3D_Line_d l) {
        super(l.env, l.offset);
        this.l = new V3D_Line_d(l);
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #offset} and {@link #l} point are set from.
     * @param q What {@link #l} vector is set from.
     */
    public V3D_Ray_d(V3D_Point_d p, V3D_Point_d q) {
        super(p.env, p.offset);
        this.l = new V3D_Line_d(p, q);
    }

    /**
     * @param r The ray to test if it is the same as {@code this}.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code r} is the same as {@code this}.
     */
    public boolean equals(V3D_Ray_d r, double epsilon) {
        if (l.getP().equals(r.l.getP())) {
            if (l.v.getDirection() == r.l.v.getDirection()) {
                return l.v.isScalarMultiple(r.l.v, epsilon);
            } else {
                return false;
            }
        } else {
            return false;
        }
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
    public String toString(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFields(pad + " ") + "\n"
                + pad + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toStringSimple(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFieldsSimple(pad + " ") + "\n"
                + pad + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFields(String pad) {
        return pad + l.toStringFields(pad);
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFieldsSimple(String pad) {
        return pad + l.toStringFieldsSimple(pad);
    }

    /**
     * For initialising and getting {@link #pl}.
     *
     * @return {@link #pl} initialised first if {@code null}.
     */
    public V3D_Plane_d getPl() {
        if (pl == null) {
            pl = new V3D_Plane_d(l.getP(), l.v);
        }
        return pl;
    }

    /**
     * @param pt A point to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is intersected by {@code pl}.
     */
    public boolean intersects(V3D_Point_d pt, double epsilon) {
        if (pt.equals(l.getP())) {
            return true;
        }
        if (l.intersects(pt, epsilon)) {
            return getPl().isOnSameSide(pt, this.l.getQ(), epsilon);
        }
        return false;
    }

    /**
     * @param aabb The V3D_AABB to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public boolean intersects(V3D_AABB_d aabb, double epsilon) {
        return aabb.intersects(l.getP())
                || getIntersect(aabb.getl(), epsilon) != null
                || getIntersect(aabb.getr(), epsilon) != null
                || getIntersect(aabb.gett(), epsilon) != null
                || getIntersect(aabb.getb(), epsilon) != null
                || getIntersect(aabb.getf(), epsilon) != null
                || getIntersect(aabb.geta(), epsilon) != null;
    }

    /**
     * @param aabbx The V3D_AABBX to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public V3D_Point_d getIntersect(V3D_AABBX_d aabbx, double epsilon) {
        V3D_Plane_d xpl = aabbx.getXPl();
        if (xpl.isParallel(l, epsilon)) {
            return null;
        }
        // Calculate the intersection point
        V3D_Point_d pt = (V3D_Point_d) xpl.getIntersect(l, epsilon);
        if (isAligned(pt, epsilon) && aabbx.intersects(pt)) {
            return pt;
        }
        return null;
    }

    /**
     * @param aabby The V3D_AABBY to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public V3D_Point_d getIntersect(V3D_AABBY_d aabby, double epsilon) {
        V3D_Plane_d ypl = aabby.getYPl();
        if (ypl.isParallel(l, epsilon)) {
            return null;
        }
        // Calculate the intersection point
        V3D_Point_d pt = (V3D_Point_d) ypl.getIntersect(l);
        if (isAligned(pt, epsilon) && aabby.intersects(pt)) {
            return pt;
        }
        return null;
    }

    /**
     * @param aabbz The V3D_AABBZ to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public V3D_Point_d getIntersect(V3D_AABBZ_d aabbz, double epsilon) {
        V3D_Plane_d zpl = aabbz.getZPl();
        if (zpl.isParallel(l, epsilon)) {
            return null;
        }
        // Calculate the intersection point
        V3D_Point_d pt = (V3D_Point_d) zpl.getIntersect(l, epsilon);
        if (isAligned(pt, epsilon) && aabbz.intersects(pt)) {
            return pt;
        }
        return null;
    }

    /**
     * Intersects a ray with a plane. {@code null} is returned if there is no
     * intersection, {@code this} is returned if the ray is on the plane.
     * Otherwise a point is returned.
     *
     * It is possible to distinguish a ray intersection with a plane (ray-plane)
     * and a plane intersection with a ray (plane-ray). In some cases the two
     * are the same, but due to coordinate number imprecision, sometimes an
     * intersection point cannot be found that is both on the ray and on the
     * plane. For a ray-plane intersection we can force the point to be on the
     * ray and either choose a point on or before the plane, or on or after the
     * plane.
     *
     * For the plane-ray intersection we can force the point to be on the plane
     * and choose the vague direction of the point from the intersection using
     * the orientation of the ray relative to the plane (and where the ray is
     * perpendicular to the plane, we can choose the direction relative to the
     * orientation of the axes and origin).
     *
     * Support ray-plane intersection to choose on or before, or on or after?
     *
     * @param pl The plane to get the geometrical intersection with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code pl}.
     */
    public V3D_Geometry_d getIntersect(V3D_Plane_d pl, double epsilon) {
        // Check if infinite line intersects.
        V3D_Geometry_d g = pl.getIntersect(l, epsilon);
        if (g == null) {
            return null;
        }
        if (g instanceof V3D_Point_d gp) {
            //V3D_Plane rp = new V3D_Plane(this.l.getP(), this.l.getV());
            //V3D_Plane rp = new V3D_Plane(this.l.getP(), this.l.v);
            //if (rp.isOnSameSide(gp, this.l.getQ())) {
            if (getPl().isOnSameSide(gp, this.l.getQ(), epsilon)) {
                return g;
            } else {
                return null;
            }
        }
        return this;
//        if (g == null) {
//            return g;
//        } else {
//            if (g instanceof V3D_Point pt) {
//                if 
//                if (intersects(pt)) {
//                    return pt;
//                } else {
//                    return null;
//                }
//            } else {
//                V3D_Point pt = l.getP();
//                V3D_Line gl = (V3D_Line) g;
//                V3D_Point glp = gl.getP();
//                int dir = l.getV().getDirection();
//                if (intersects(pt)) {
//                    if (pt.equals(glp)) {
//                        V3D_Point glq = gl.getQ();
//                        V3D_Vector ptglq = new V3D_Vector(pt, glq);
//                        int dir_ptglq = ptglq.getDirection();
//                        if (dir == dir_ptglq) {
//                            return this;
//                        } else {
//                            return pt;
//                        }
//                    } else {
//                        return this;
//                    }
//                } else {
//                    V3D_Vector ptglp = new V3D_Vector(pt, glp);
//                    int dir_ptglp = ptglp.getDirection();
//                    if (dir == dir_ptglp) {
//                        return this;
//                    } else {
//                        return null;
//                    }
//                }
//            }
//        }
    }

//    /**
//     * Intersects {@code this} with {@code t}. {@code null} is returned if there
//     * is no intersection.
//     *
//     * @param t The triangle to get the geometrical intersection with this.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode if rounding is needed.
//     * @return The intersection between {@code this} and {@code t}.
//     */
//    @Override
//    public V3D_FiniteGeometry getIntersect(V3D_Triangle t, int oom, RoundingMode rm) {
//        V3D_Geometry g = getIntersect(t.pl);
//        if (g == null) {
//            return null;
//        } else {
//            if (g instanceof V3D_Point pt) {
//                if (t.isAligned(pt)) {
//                    return pt;
//                } else {
//                    return null;
//                }
//            } else {
//                V3D_Geometry g2 = t.getIntersect(l);
//                if (g2 instanceof V3D_Point g2p) {
//                    //if (intersects(g2p)) {
//                    if (t.isAligned(g2p)) {
//                        return g2p;
//                    } else {
//                        return null;
//                    }
//                } else {
//                    return getIntersect((V3D_LineSegment) g2);
//                }
//            }
//        }
//    }
    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if there is no
     * intersection.
     *
     * @param l The line to get the geometrical intersection with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry_d getIntersect(V3D_Line_d l, double epsilon) {
        // Check if infinite lines intersect.
        V3D_Geometry_d g = this.l.getIntersect(l, epsilon);
        if (g == null) {
            // There is no intersection.
            return g;
        }
        /**
         * If lines intersects at a point, then check this point is on this.
         */
        if (g instanceof V3D_Point_d pt) {
            if (isAligned(pt, epsilon)) {
                return g;
            } else {
                return null;
            }
        }
        return this;
    }

    /**
     * Intersects {@code this} with {@code r}. If they are equivalent then
     * return {@code this}. If they overlap then return the part that overlaps -
     * which is either a point, a line segment, or a ray. {@code null} is
     * returned if {@code this} and {@code r} do not intersect.
     *
     * @param r The line to get intersection with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code r}.
     */
    public V3D_Geometry_d getIntersect(V3D_Ray_d r, double epsilon) {
        // Check if infinite line intersects.
        V3D_Geometry_d rtl = r.getIntersect(l, epsilon);
        if (rtl == null) {
            return null;
        } else if (rtl instanceof V3D_Point_d pt) {
            if (getPl().isOnSameSide(pt, l.getQ(), epsilon)) {
                return pt;
            } else {
                return null;
            }
        } else {
            // Then rtl is an instance of V3D_Ray.
            V3D_Geometry_d grl = getIntersect(r.l, epsilon);
            if (grl instanceof V3D_Point_d) {
                return grl;
            } else {
                /**
                 * Then grl is an instance of a V3D_Ray. The rays may point
                 * along the same line. If they point in the same direction,
                 * then they intersect and the start of the ray is the start
                 * point of the ray that intersects both rays. If they point in
                 * opposite directions, then they do not intersect unless the
                 * points they start at intersect with the other ray and in this
                 * instance, the intersection is the line segment between them.
                 */
                V3D_Point_d tp = l.getP();
                V3D_Point_d rp = r.l.getP();
                V3D_Plane_d rpl = r.getPl();
                if (getPl().isOnSameSide(rp, l.getQ(), epsilon)) {
                    if (rpl.isOnSameSide(tp, r.l.getQ(), epsilon)) {
                        if (tp.equals(rp)) {
                            return tp;
                        }
                        return new V3D_LineSegment_d(rp, tp);
                    } else {
                        return new V3D_Ray_d(rp, l.v);
                    }
                } else {
                    if (rpl.isOnSameSide(tp, r.l.getQ(), epsilon)) {
                        return new V3D_Ray_d(tp, l.v);
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap then return the part that overlaps -
     * which is either a point, a line segment, or a ray. {@code null} is
     * returned if {@code this} and {@code l} do not intersect.
     *
     * @param ls The line to get intersection with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_LineSegment_d ls,
            double epsilon) {
        // Check if infinite lines intersect.
        V3D_Geometry_d g = getIntersect(ls.l, epsilon);
        if (g == null) {
            return null;
        } else if (g instanceof V3D_Point_d pt) {
            if (isAligned(pt, epsilon)) {
                return pt;
            } else {
                return null;
            }
        } else {
            V3D_Point_d rp = l.getP();
            //V3D_Point rq = l.getQ();
            V3D_Point_d lsp = ls.getP();
            V3D_Point_d lsq = ls.getQ();
            if (isAligned(lsp, epsilon)) {
                if (isAligned(lsq, epsilon)) {
                    return ls;
                } else {
                    return V3D_LineSegment_d.getGeometry(rp, lsp, epsilon);
                }
            } else {
                if (isAligned(lsq, epsilon)) {
                    return V3D_LineSegment_d.getGeometry(rp, lsq, epsilon);
                } else {
                    if (intersects(lsp, epsilon)) {
                        return lsp;
                    }
                    if (intersects(lsq, epsilon)) {
                        return lsq;
                    }
                    return null;
                }
            }
        }
    }

    /**
     * Calculates and returns if pt is in the direction of the ray (i.e.the same
     * side of the ray start point plane as another point on the ray).
     *
     * @param pt The point.
     * @param epsilon
     * @return {@code true} If pt is in line with this.
     */
    public boolean isAligned(V3D_Point_d pt, double epsilon) {
        return getPl().isOnSameSide(pt, l.getQ(), epsilon);
    }

    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     */
    @Override
    public void translate(V3D_Vector_d v) {
        this.l.translate(v);
    }

    @Override
    public V3D_Ray_d rotate(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V3D_Ray_d(this);
        } else {
            return rotateN(ray, uv, theta, epsilon);
        }
    }

    @Override
    public V3D_Ray_d rotateN(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        return new V3D_Ray_d(l.rotate(ray, uv, theta, epsilon));
    }
}
