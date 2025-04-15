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
package uk.ac.leeds.ccg.v3d.geometry;

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * 3D representation of a ray - like a line, but one that starts at a point
 * continues infinitely in only one direction.The ray begins at the point of
 {@link #l} and goes in the direction given by the vector of {@link #l}.The
 "*" denotes a point in 3D and the ray is shown as a linear feature of "e"
 symbols in the following depiction: {@code
                                     z
                         y           -
                         +          /                * pl=<x0,y0,z0>
                         |         /                e
                         |    z0 -/                e
                         |       /                e
                         |      /                e
                         |     /               e
                         |    /               e
                         |   /               e
                      y0-|  /               e
                         | /               e
                         |/         x1    e
 - ----------------------|-----------|---e---|---- + x
                        /|              e   x0
                       / |-y1          e
                      /  |           e
                     /   |          e
                z1 -/    |         e
                   /     |        e
                  /      |       * v=(dx,dy,dz)
                 /       |      e
                /        |     e
               +         -    e
              z          y   e
 }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Ray extends V3D_Geometry {

    private static final long serialVersionUID = 1L;

    /**
     * The line of this ray.
     */
    public V3D_Line l;

    /**
     * For storing the plane at the point P of {@link #l} with normal in 
     * direction of the ray vector.
     */
    private V3D_Plane pl;
    
    /**
     * Create a new instance.
     *
     * @param r What {@code this} is created from.
     */
    public V3D_Ray(V3D_Ray r) {
        super(r.env);
        l = new V3D_Line(r.l);
    }

    /**
     * Create a new instance.
     *
     * @param p What {@code this} is created from.
     * @param v What {@code this} is created from.
     */
    public V3D_Ray(V3D_Point p, V3D_Vector v) {
        super(p.env, p.offset);
        l = new V3D_Line(p, v);
    }

    /**
     * Create a new instance. {@link #offset} is set to {@link V3D_Vector#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param pv What {@link #l.pv} is created from.
     * @param v What {@link #l.v} is created from.
     */
    public V3D_Ray(V3D_Environment env, V3D_Vector pv, V3D_Vector v) {
        this(env, V3D_Vector.ZERO, pv, v);
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param pv What {@link #l.pv} is set to.
     * @param v What {@link #l.v} is set from.
     */
    public V3D_Ray(V3D_Environment env, V3D_Vector offset, V3D_Vector pv,
            V3D_Vector v) {
        super(env, offset);
        l = new V3D_Line(env, offset, pv, v);
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_Ray(V3D_Line l) {
        super(l.env, l.offset);
        this.l = new V3D_Line(l);
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #offset} and {@link #l} point are set from.
     * @param q What {@link #l} vector is set from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Ray(V3D_Point p, V3D_Point q, int oom, RoundingMode rm) {
        super(p.env, p.offset);
        this.l = new V3D_Line(p, q, oom, rm);
    }

    /**
     * @param r The ray to test if it is the same as {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code r} is the same as {@code this}.
     */
    public boolean equals(V3D_Ray r, int oom, RoundingMode rm) {
        if (l.getP().equals(r.l.getP(), oom, rm)) {
            if (l.v.getDirection() == r.l.v.getDirection()) {
                return l.v.isScalarMultiple(r.l.v, oom, rm);
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
     * @return {@link #pl} initialised first if {@code null}.
     */
    public V3D_Plane getPl() {
        if (pl == null) {
            pl = new V3D_Plane(l.getP(), l.v);
        }
        return pl;
    }

    /**
     * @param pt A point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} if {@code this} is intersected by {@code pl}.
     */
    public boolean intersects(V3D_Point pt, int oom, RoundingMode rm) {
        if (pt.equals(l.getP(), oom, rm)) {
            return true;
        }
        if (l.intersects(pt, oom, rm)) {
            pl = getPl();
            return pl.isOnSameSide(pt, this.l.getQ(oom, rm), oom, rm);
        }
        return false;
    }
    
    /**
     * @param aabb The V3D_AABB to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public boolean intersects(V3D_AABB aabb, int oom, RoundingMode rm) {
        return aabb.intersects(l.getP(), oom, rm)
                || getIntersect(aabb.getl(oom, rm), oom, rm) != null
                || getIntersect(aabb.getr(oom, rm), oom, rm) != null
                || getIntersect(aabb.gett(oom, rm), oom, rm) != null
                || getIntersect(aabb.getb(oom, rm), oom, rm) != null
                || getIntersect(aabb.getf(oom, rm), oom, rm) != null
                || getIntersect(aabb.geta(oom, rm), oom, rm) != null;
    }
    
    /**
     * @param aabbx The V3D_AABBX to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public V3D_Point getIntersect(V3D_AABBX aabbx, int oom, RoundingMode rm) {
        V3D_Plane xpl = aabbx.getXPl(oom, rm); 
        if (xpl.isParallel(l, oom, rm)) {
            return null;
        }
        // Calculate the intersection point
        V3D_Point pt = (V3D_Point) xpl.getIntersect(l, oom, rm);
        if (isAligned(pt, oom, rm)
            && aabbx.intersects(pt, oom, rm)) {
                return pt;
        }
        return null;
    }
    
    /**
     * @param aabby The V3D_AABBY to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public V3D_Point getIntersect(V3D_AABBY aabby, int oom, RoundingMode rm) {
        V3D_Plane ypl = aabby.getYPl(oom, rm); 
        if (ypl.isParallel(l, oom, rm)) {
            return null;
        }
        // Calculate the intersection point
        V3D_Point pt = (V3D_Point) ypl.getIntersect(l, oom, rm);
        if (isAligned(pt, oom, rm)
            && aabby.intersects(pt, oom, rm)) {
            return pt;
        }
        return null;
    }

    /**
     * @param aabbz The V3D_AABBZ to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this getIntersect with {@code l}
     */
    public V3D_Point getIntersect(V3D_AABBZ aabbz, int oom, RoundingMode rm) {
        V3D_Plane zpl = aabbz.getZPl(oom, rm); 
        if (zpl.isParallel(l, oom, rm)) {
            return null;
        }
        // Calculate the intersection point
        V3D_Point pt = (V3D_Point) zpl.getIntersect(l, oom, rm);
        if (isAligned(pt, oom, rm)
            && aabbz.intersects(pt, oom, rm)) {
            return pt;
        }
        return null;
    }

    /**
     * Compute and return the intersection with {@code pl}. {@code null} is 
     * returned if there
     *
     * It is possible to distinguish a ray intersection with a plane (ray-plane)
     * and a plane intersection with a ray (plane-ray). In some cases the two
     * are the same, but due to coordinate imprecision, sometimes an
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code this} and {@code pl}.
     */
    public V3D_Geometry getIntersect(V3D_Plane pl, int oom, RoundingMode rm) {
        V3D_Geometry g = pl.getIntersect(l, oom, rm);
        if (g == null) {
            return null;
        } else if (g instanceof V3D_Point gp) {
            if (getPl().isOnSameSide(gp, this.l.getQ(oom, rm), oom, rm)) {
                return g;
            } else {
                return null;
            }
        } else {
            return this;
        }
    }
    
    
    /**
     * Compute and return the intersection with {@code pl}. {@code this} is
     * assumed to be non-coplanar with {@code pl}. {@code null} is returned if
     * there is no intersection.
     * 
     * It is possible to distinguish a ray intersection with a plane (ray-plane)
     * and a plane intersection with a ray (plane-ray). In some cases the two
     * are the same, but due to coordinate imprecision, sometimes an
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code this} and {@code pl}.
     */
    public V3D_Point getIntersect0(V3D_Plane pl, int oom, RoundingMode rm) {
        V3D_Point g = pl.getIntersectNonParallel(l, oom, rm);
        if (g == null) {
            return null;
        } else {
            if (getPl().isOnSameSide(g, this.l.getQ(oom, rm), oom, rm)) {
                return g;
            } else {
                return null;
            }
        }
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
//        V3D_Geometry g = getIntersect(t.pl, oom, rm);
//        if (g == null) {
//            return null;
//        } else {
//            if (g instanceof V3D_Point pt) {
//                if (t.isAligned(pt, oom, rm)) {
//                    return pt;
//                } else {
//                    return null;
//                }
//            } else {
//                V3D_Geometry g2 = t.getIntersect(l, oom, rm);
//                if (g2 instanceof V3D_Point g2p) {
//                    //if (getIntersect(g2p, oom, rm)) {
//                    if (t.isAligned(g2p, oom, rm)) {
//                        return g2p;
//                    } else {
//                        return null;
//                    }
//                } else {
//                    return getIntersect((V3D_LineSegment) g2, oom, rm);
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry getIntersect(V3D_Line l, int oom, RoundingMode rm) {
        // Check if infinite lines intersect.
        V3D_Geometry g = this.l.getIntersect(l, oom, rm);
        if (g == null) {
            // There is no intersection.
            return g;
        }
        /**
         * If lines intersects at a point, then check this point is on this.
         */
        if (g instanceof V3D_Point pt) {
            if (isAligned(pt, oom, rm)) {
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code this} and {@code r}.
     */
    public V3D_Geometry getIntersect(V3D_Ray r, int oom, RoundingMode rm) {
        V3D_Geometry rtl = r.getIntersect(l, oom, rm);
        if (rtl == null) {
            return null;
        } else if (rtl instanceof V3D_Point pt) {
            if (getPl().isOnSameSide(pt, l.getQ(oom, rm), oom, rm)) {
                return pt;
            } else {
                return null;
            }
        } else {
            // Then rtl is an instance of V3D_Ray.
            V3D_Geometry grl = getIntersect(r.l, oom, rm);
            if (grl instanceof V3D_Point) {
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
                V3D_Point tp = l.getP();
                V3D_Point rp = r.l.getP();
                V3D_Plane rpl = r.getPl();
                if (getPl().isOnSameSide(rp, l.getQ(oom, rm), oom, rm)) {
                    if (rpl.isOnSameSide(tp, r.l.getQ(oom, rm), oom, rm)) {
                        if (tp.equals(rp, oom, rm)) {
                            return tp;
                        }
                        return new V3D_LineSegment(rp, tp, oom, rm);
                    } else {
                        return new V3D_Ray(rp, l.v);
                    }
                } else {
                    if (rpl.isOnSameSide(tp, r.l.getQ(oom, rm), oom, rm)) {
                        return new V3D_Ray(tp, l.v);
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_FiniteGeometry getIntersect(V3D_LineSegment ls, int oom, RoundingMode rm) {
        V3D_Geometry g = getIntersect(ls.l, oom, rm);
        if (g == null) {
            return null;
        } else if (g instanceof V3D_Point pt) {
            if (isAligned(pt, oom, rm)) {
                if (ls.isAligned(pt, oom, rm)) {
                    return pt;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            V3D_Point rp = l.getP();
            //V3D_Point rq = l.getQ(oom, rm);
            V3D_Point lsp = ls.getP();
            V3D_Point lsq = ls.getQ(oom, rm);
            if (isAligned(lsp, oom, rm)) {
                if (isAligned(lsq, oom, rm)) {
                    return ls;
                } else {
                    return V3D_LineSegment.getGeometry(rp, lsp, oom, rm);
                }
            } else {
                if (isAligned(lsq, oom, rm)) {
                    return V3D_LineSegment.getGeometry(rp, lsq, oom, rm);
                } else {
                    if (intersects(lsp, oom, rm)) {
                        return lsp;
                    }
                    if (intersects(lsq, oom, rm)) {
                        return lsq;
                    }
                    return null;
                }
            }
        }
    }
    
    /**
     * Calculates and returns if pt is in the direction of the ray (i.e. the
     * same side of the ray start point plane as another point on the ray).
     * 
     * @param pt The point. 
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} If pt is in line with this.  
     */
    public boolean isAligned(V3D_Point pt, int oom, RoundingMode rm) {
        return getPl().isOnSameSide(pt, l.getQ(oom, rm), oom, rm);
    }

//    /**
//     * Translate (move relative to the origin).
//     *
//     * @param v The vector to translate.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode if rounding is needed.
//     */
//    @Override
//    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
//        super.translate(v, oom, rm);
//        l.offset = offset;
//        //l.translate(v, oom, rm);
//    }

    @Override
    public V3D_Ray rotate(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd, 
            BigRational theta, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V3D_Ray(this);
        } else {
            return rotateN(ray, uv, bd, theta, oom, rm);
        }
    }
    
    @Override
    public V3D_Ray rotateN(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd, 
            BigRational theta, int oom, RoundingMode rm) {
        return new V3D_Ray(l.rotateN(ray, uv, bd, theta, oom, rm));
    }
}
