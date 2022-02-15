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

import java.math.BigDecimal;
import java.util.Objects;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * 3D representation of a ray - a type of infinite line that starts at a point
 * and moves only in one direction and not in the other. The line begins at the
 * point {@link #p} and goes through the point {@link #q}. The "*" denotes a
 * point in 3D and the line is shown with a line of "e" symbols in the following
 * depiction: {@code
 *                                       z
 *                          y           -
 *                          +          /                * p=<x0,y0,z0>
 *                          |         /                e
 *                          |    z0  /                e
 *                          |      \/                e
 *                          |      /                e
 *                          |     /               e
 *                          |    /               e
 *                          |   /               e
 *                       y0-|  /               e
 *                          | /               e
 *                          |/         x1    e
 *  - ----------------------|-----------|---e---|---- + x
 *                         /|              e   x0
 *                        / |-y1          e
 *                       /  |           e
 *                  z1  /   |          e
 *                    \/    |         e
 *                    /     |        e
 *                   /      |       * q=<x1,y1,z1>
 *                  /       |      e
 *                 /        |     e
 *                +         -    e
 *               z          y   e
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Ray extends V3D_Line {
    //implements V3D_IntersectionAndDistanceCalculations {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_Ray(V3D_Ray l) {
        super(l);
    }

    /**
     * Create a new instance. {@link #offset} is set to {@link V3D_Vector#ZERO}.
     *
     * @param e What {@link #e} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     */
    public V3D_Ray(V3D_Environment e, V3D_Vector p, V3D_Vector q) {
        this(e, V3D_Vector.ZERO, p, q);
    }

    /**
     * Create a new instance.
     *
     * @param e What {@link #e} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     */
    public V3D_Ray(V3D_Environment e, V3D_Vector offset, V3D_Vector p,
            V3D_Vector q) {
        super(e, offset, p, q);
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_Ray(V3D_Line l) {
        super(l);
    }

    /**
     * Create a new instance.
     *
     * @param e What {@link #e} is set to.
     * @param l What {@code this} is created from.
     */
    public V3D_Ray(V3D_Environment e, V3D_Envelope.LineSegment l) {
        this(e, new V3D_Vector(l.p), new V3D_Vector(l.q));
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #offset} and {@link #p} are set from.
     * @param q What {@link #q} is set from.
     */
    public V3D_Ray(V3D_Point p, V3D_Point q) {
        super(p, q);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Ray r) {
            return equals(r, e.oom);
        }
        return false;
    }

    /**
     * @param r The line segment to test if it is the same as {@code this}.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} iff {@code r} is the same as {@code this}.
     */
    public boolean equals(V3D_Ray r, int oom) {
        return getP(oom).equals(r.getP(oom))
                && isIntersectedBy(r.getQ(oom), oom);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.p);
        hash = 31 * hash + Objects.hashCode(this.q);
        return hash;
    }

//    /**
//     * @param v The vector to apply to each coordinate of this.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return a new V3D_Ray which is {@code this} with the {@code v} applied.
//     */
//    @Override
//    public V3D_Ray apply(V3D_Vector v, int oom) {
//        return new V3D_Ray(p.add(v, oom), q.add(v, oom), oom);
//    }
    /**
     * @param pt A point to test for intersection.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} if {@code this} is intersected by {@code p}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom) {
        if (pt.equals(getP(oom))) {
            return true;
        }
        /**
         * The following if statement is not necessary, but it may be
         * computationally advantageous.
         */
        if (pt.equals(getQ(oom))) {
            return true;
        }
        V3D_Line l = new V3D_Line(this);
        if (l.isIntersectedBy(pt, oom)) {
            V3D_Point poi = l.getPointOfIntersection(pt, oom);
            V3D_Ray r = new V3D_Ray(e, getP(), poi.getVector(oom));
            return r.getV(oom).getDirection() == getV(oom).getDirection();
        }
//        boolean isPossibleIntersection = isPossibleIntersection(pt, oom);
//        if (isPossibleIntersection) {
//            Math_BigRationalSqrt a = p.getDistance(this.p);
//            if (a.getX().isZero()) {
//                return true;
//            }
//            Math_BigRationalSqrt b = p.getDistance(this.q);
//            if (b.getX().isZero()) {
//                return true;
//            }
//            Math_BigRationalSqrt l = this.p.getDistance(this.q);
//            if (a.add(b, oom).compareTo(l) != 1) {
//                return true;
//            }
//        }
        return false;
    }

//    /**
//     * This compares the location of {@code pt} to the location of {@link #p}
//     * and the direction of {@link #v}. If the {@code pt} is on a side of
//     * {@link #p} and {@link #v} is moving away in any of the axial directions,
//     * then there is no chance of an intersection.
//     *
//     * @param pt The point to test for a possible intersection.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return {@code false} if there is no chance of intersection, and
//     * {@code true} otherwise.
//     */
//    private boolean isPossibleIntersection(V3D_Point pt, int oom) {
//        int ptxcpx = pt.x.compareTo(p.x);
//        int vdxc0 = v.getDX(oom).compareTo(Math_BigRational.ZERO);
//        switch (ptxcpx) {
//            case -1:
//                if (vdxc0 == -1) {
//                    return getptycpy(pt, oom);
//                } else {
//                    return false;
//                }
//            case 0:
//                return getptycpy(pt, oom);
//            default:
//                if (vdxc0 == 1) {
//                    return getptycpy(pt, oom);
//                } else {
//                    return false;
//                }
//        }
//    }
//
//    private boolean getptycpy(V3D_Point pt, int oom) {
//        int ptycpy = pt.y.compareTo(p.y);
//        int vdyc0 = v.getDY(oom).compareTo(Math_BigRational.ZERO);
//        switch (ptycpy) {
//            case -1:
//                if (vdyc0 == -1) {
//                    return getptzcpz(pt, oom);
//                } else {
//                    return false;
//                }
//            case 0:
//                return getptzcpz(pt, oom);
//            default:
//                if (vdyc0 == 1) {
//                    return getptzcpz(pt, oom);
//                } else {
//                    return false;
//                }
//        }
//    }
//
//    private boolean getptzcpz(V3D_Point pt, int oom) {
//        int ptzcpz = pt.z.compareTo(p.z);
//        int vdzc0 = v.getDZ(oom).compareTo(Math_BigRational.ZERO);
//        switch (ptzcpz) {
//            case -1:
//                return vdzc0 == -1;
//            case 0:
//                return true;
//            default:
//                return vdzc0 == 1;
//        }
//    }
    /**
     * @param r A ray to test if it intersects with {@code this}.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} iff {@code r} intersects with {@code this}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Ray r, int oom) {
        if (getP().equals(r.getP())) {
            return true;
        }
        V3D_Line tl = new V3D_Line(this);
        boolean isIntersectedrtl = r.isIntersectedBy(tl, oom);
        if (isIntersectedrtl == false) {
            return false;
        }
        V3D_Line rl = new V3D_Line(r);
        boolean isIntersectedtrl = isIntersectedBy(rl, oom);
        if (isIntersectedtrl == false) {
            return false;
        }
        /**
         * The rays may point along the same line. If they point in the same
         * direction, then they intersect. If they point in opposite directions,
         * then they do not intersect unless the points they start at intersect
         * with the other ray.
         */
        if (isIntersectedrtl && isIntersectedtrl) {
            if (r.isIntersectedBy(getP(oom), oom)) {
                return true;
            }
            if (isIntersectedBy(r.getP(oom), oom)) {
                return true;
            }
        }
        return isIntersectedBy((V3D_Point) getIntersection(r, oom), oom);
    }

    /**
     * @param l A line segment to test if it intersects with {@code this}.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} iff {@code l} intersects with {@code this}.
     */
    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom) {
        V3D_Ray rlpq = new V3D_Ray(l);
        if (!isIntersectedBy(rlpq, oom)) {
            return false;
        }
        V3D_Ray rlqp = new V3D_Ray(e, l.getQ(), l.getP());
        return isIntersectedBy(rlqp, oom);
    }

    /**
     * @param l A line to test for intersection.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} iff {@code l} intersects with {@code this}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        V3D_Geometry i = new V3D_Line(this).getIntersection(l, oom);
        if (i == null) {
            return false;
        }
        if (i instanceof V3D_Point pt) {
            return isIntersectedBy(pt, oom);
        } else {
            return true;
        }
    }

    /**
     * @param pl A plane to test for intersection.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} iff {@code pl} intersects with {@code this}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Plane pl, int oom) {
        V3D_Geometry i = new V3D_Line(this).getIntersection(pl, oom);
        if (i == null) {
            return false;
        }
        if (i instanceof V3D_Point pt) {
            return isIntersectedBy(pt, oom);
        } else {
            return true;
        }
    }

    /**
     * @param t A triangle to test for intersection.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} iff {@code t} intersects with {@code this}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Triangle t, int oom) {
        V3D_Geometry i = new V3D_Line(this).getIntersection(t, oom);
        if (i == null) {
            return false;
        }
        if (i instanceof V3D_Point pt) {
            return isIntersectedBy(pt, oom);
        } else {
            return isIntersectedBy((V3D_LineSegment) i, oom);
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Intersects {@code this} with {@code p}. {@code null} is returned if there
     * is no intersection.
     *
     * @param pl The plane to get the geometrical intersection with this.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code p}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Plane pl, int oom) {
        V3D_Geometry g = new V3D_Line(this).getIntersection(pl, oom);
        if (g == null) {
            return g;
        } else {
            if (g instanceof V3D_Point pt) {
                if (isIntersectedBy(pt, oom)) {
                    return pt;
                } else {
                    return null;
                }
            } else {
                V3D_Point pt = getP(oom);
                V3D_Line gl = (V3D_Line) g;
                V3D_Point glp = gl.getP(oom);
                if (isIntersectedBy(pt, oom)) {
                    int dir = getV(oom).getDirection();
                    if (pt.equals(glp)) {
                        V3D_Point glq = gl.getQ(oom);
                        V3D_Vector ptglq = new V3D_Vector(pt, glq, oom);
                        int dir_ptglq = ptglq.getDirection();
                        if (dir == dir_ptglq) {
                            return new V3D_LineSegment(pt, glq);
                        } else {
                            return pt;
                        }
                    } else {
                        V3D_Vector ptglp = new V3D_Vector(pt, glp, oom);
                        int dir_ptglp = ptglp.getDirection();
                        if (dir == dir_ptglp) {
                            return new V3D_LineSegment(pt, glp);
                        } else {
                            return V3D_LineSegment.getGeometry(pt, gl.getQ(oom));
                        }
                    }
                } else {
                    int dir = getV(oom).getDirection();
                    V3D_Vector ptglp = new V3D_Vector(pt, glp, oom);
                    int dir_ptglp = ptglp.getDirection();
                    if (dir == dir_ptglp) {
                        return this;
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    /**
     * Intersects {@code this} with {@code t}. {@code null} is returned if there
     * is no intersection.
     *
     * @param t The triangle to get the geometrical intersection with this.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code t}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Triangle t, int oom) {
        V3D_Geometry g = getIntersection(new V3D_Plane(t), oom);
        if (g == null) {
            return g;
        } else {
            if (g instanceof V3D_Point pt) {
                if (isIntersectedBy(pt, oom)) {
                    return pt;
                } else {
                    return null;
                }
            } else {
                V3D_Geometry g2 = t.getIntersection(new V3D_Line(this), oom);
                if (g2 instanceof V3D_Point g2p) {
                    if (isIntersectedBy(g2p, oom)) {
                        return g2p;
                    } else {
                        return null;
                    }
                } else {
                    return getIntersection((V3D_LineSegment) g2, oom);
                }
            }
        }
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if there is no
     * intersection.
     *
     * @param l The line to get the geometrical intersection with this.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        // Check if infinite lines intersect.
        V3D_Geometry g = new V3D_Line(this).getIntersection(new V3D_Line(l), oom);
        if (g == null) {
            // There is no intersection.
            return g;
        }
        /**
         * If lines intersects at a point, then check this point is on this.
         */
        if (g instanceof V3D_Point pt) {
            if (isIntersectedBy(pt, oom)) {
                return g;
            } else {
                return null;
            }
        } else if (g instanceof V3D_Line) {
            // If the lines are the same, then return this. 
            return this;
        } else {
            // There is no intersection.
            return null;
        }
    }

    /**
     * Intersects {@code this} with {@code r}. If they are equivalent then
     * return {@code this}. If they overlap then return the part that overlaps -
     * which is either a point, a line segment, or a ray. {@code null} is
     * returned if {@code this} and {@code r} do not intersect.
     *
     * @param r The line to get intersection with this.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code r}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Ray r, int oom) {
        V3D_Line tl = new V3D_Line(this);
        V3D_Geometry rtl = r.getIntersection(tl, oom);
        if (rtl == null) {
            return null;
        } else if (rtl instanceof V3D_Point pt) {
            if (r.isIntersectedBy(pt, oom)) {
                return pt;
            } else {
                return null;
            }
        } else {
            // Then rtl is an instance of V3D_Ray.
            V3D_Line rl = new V3D_Line(r);
            V3D_Geometry grl = getIntersection(rl, oom);
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
                V3D_Point rp = r.getP(oom);
                if (isIntersectedBy(rp, oom)) {
                    V3D_Point tp = getP(oom);
                    if (r.isIntersectedBy(tp, oom)) {
                        return V3D_LineSegment.getGeometry(rp, tp);
//                        return new V3D_LineSegment(e, rp.getVector(oom),
//                                tp.getVector(oom));
                    } else {
                        return r;
                    }
                } else {
                    if (isIntersectedBy(r.getQ(oom), oom)) {
                        return this;
                    } else {
                        if (r.getV().getDirection() == getV().getDirection()) {
                            return this;
                        } else {
                            return null;
                        }
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
     * @param l The line to get intersection with this.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom) {
        V3D_Geometry g = getIntersection(new V3D_Line(l), oom);
        if (g == null) {
            return g;
        } else if (g instanceof V3D_Point pt) {
            if (isIntersectedBy(pt, oom)) {
                return pt;
            } else {
                return null;
            }
        } else if (g instanceof V3D_Ray r) {
            V3D_Point rp = r.getP(oom);
            V3D_Point lp = l.getP(oom);
            V3D_Point lq = l.getQ(oom);
            V3D_LineSegment lsrplq = new V3D_LineSegment(rp, lq);
            int rvdirection = r.getV().getDirection();
            if (rp.equals(lp)) {
                if (lsrplq.getV().getDirection() == rvdirection) {
                    return l;
                } else {
                    return rp;
                }
            } else {
                V3D_LineSegment lsrplp = new V3D_LineSegment(rp, lp);
                if (rp.equals(lq)) {
                    if (lsrplp.getV().getDirection() == rvdirection) {
                        return l;
                    } else {
                        return rp;
                    }
                } else {
                    if (lsrplq.getV().getDirection() == rvdirection) {
                        if (lsrplp.getV().getDirection() == rvdirection) {
                            return l;
                        } else {
                            return new V3D_LineSegment(rp, lq);
                        }
                    } else {
                        if (lsrplp.getV().getDirection() == rvdirection) {
                            return new V3D_LineSegment(rp, lp);
                        } else {
                            return null;
                        }
                    }
                }
            }
        } else {
            V3D_LineSegment ls = (V3D_LineSegment) g;
            V3D_Point lsp = ls.getP(oom);
            if (isIntersectedBy(lsp, oom)) {
                V3D_Point lsq = ls.getQ(oom);
                if (isIntersectedBy(lsq, oom)) {
                    return g;
                } else {
                    return new V3D_LineSegment(lsp, getP(oom));
                }
            } else {
                V3D_Point lsq = ls.getQ(oom);
                if (isIntersectedBy(lsq, oom)) {
                    return new V3D_LineSegment(lsq, getP(oom));
                } else {
                    return null;
                }
            }
        }
    }
    
    /**
     * @param pt A point for which the shortest line to this is returned.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The line having the shortest distance between {@code pt} and
     * {@code this}.
     */
    @Override
    public V3D_Geometry getLineOfIntersection(V3D_Point pt, int oom) {
        // Special case
        V3D_Vector pv = getPV(oom);
        V3D_Vector ptv = pt.getVector(oom);
        if (ptv.equals(pv)) {
            return pt;
        }
        V3D_Geometry i = super.getLineOfIntersection(pt, oom);
        if (i instanceof V3D_Point) {
            V3D_Ray r = new V3D_Ray(e, ptv, pv);
            if (r.getV(oom).getDirection() == getV(oom).getDirection()) {
                return new V3D_LineSegment(e, pv, ptv);
            } else {
                return i;
            }
        } else {
            V3D_LineSegment il = (V3D_LineSegment) i;
            V3D_Ray r = new V3D_Ray(e, ptv, pv);
            if (r.getV(oom).getDirection() == getV(oom).getDirection()) {
                return new V3D_LineSegment(e, ptv, il.getQV(oom));
            } else {
                //return new V3D_LineSegment(ptv, il.q, oom);
                return i;
            }
        }
//        if (isIntersectedBy(i.q, oom)) {
//            return i;
//        } else {
//            return new V3D_LineSegment(pt, p, oom);
//        }
    }

    @Override
    public V3D_Geometry getLineOfIntersection(V3D_Line l, int oom) {
        V3D_Geometry loi = super.getLineOfIntersection(l, oom);
        if (loi == null) {
            return loi;
        }
        //V3D_Geometry loi2 = l.getLineOfIntersection(p, oom);
        V3D_Geometry loi2 = l.getLineOfIntersection(getP(oom), oom);
        if (loi instanceof V3D_Point v3D_Point) {
            //V3D_Ray r = new V3D_Ray(p, v3D_Point, oom);
            V3D_Ray r = new V3D_Ray(e, getPV(oom), v3D_Point.getVector(oom));
            if (r.getV(oom).getDirection() == getV(oom).getDirection()) {
                return loi;
            } else {
                return loi2;
            }
        } else {
            V3D_Ray r = new V3D_Ray(e, getP(), ((V3D_LineSegment) loi).getP());
            if (r.getV(oom).getDirection() == getV(oom).getDirection()) {
                return loi2;
            } else {
                return loi;
            }
        }
    }

    /**
     * @param pt The point projected onto this.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return A point on {@code this} which is the shortest distance from
     * {@code pt}.
     */
    @Override
    public V3D_Point getPointOfIntersection(V3D_Point pt, int oom) {
        V3D_Point poi = new V3D_Line(this).getPointOfIntersection(pt, oom);
        if (isIntersectedBy(poi, oom)) {
            return poi;
        } else {
            //return p;
            return getP(oom);
        }
//            V3D_Ray r = new V3D_Ray(p, poi, oom);
//            if (r.v.getDirection() == v.getDirection()) {
//                return 
//            }
//        if (this.isIntersectedBy(pt, oom)) {
//            return pt;
//        }
//        return (V3D_Point) getIntersection(getLineOfIntersection(pt, oom), oom);
//        
//        }
    }

    /**
     * Let the distance from {@code pt} to {@link #p} be ptpd. Let the distance
     * from {@code pt} ro {@link q} be ptqd. Let the distance from {@link #p} to
     * {@link #q} be pqd. Let the distance from {@code pt} to the line through
     * {@link #p} and {@link #q} be ptld. Then the is point to the ray is less
     * than the distance of the point from either end of the line and the
     * distance from either end of the line is greater than the length of the
     * line then the distance is the shortest of the distances from the point to
     * the points at either end of the line segment. In all other cases, the
     * distance is the distance between the point and the line.
     *
     * @param pt A point for which the minimum distance from {@code this} is
     * returned.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The minimum distance between this and {@code p}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point pt, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(pt, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
//        if (isIntersectedBy(pt, oom)) {
//            return BigDecimal.ZERO;
//        }
//        V3D_Line l = new V3D_Line(this);
//        V3D_Point ip = l.getPointOfIntersection(pt, oom);
//        if (isIntersectedBy(ip, oom)) {
//            return ip.getDistance(pt, oom);
//        } else {
//            //return p.getDistance(pt, oom);
//            return getP(oom).getDistance(pt, oom);
//        }
    }

    /**
     * @param r The ray to get the distance from.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance between {@code this} and {@code r}.
     */
    @Override
    public Math_BigRational getDistanceSquared(V3D_Point pt, int oom) {
        V3D_Point poi = getPointOfIntersection(pt, oom);
        return poi.getDistanceSquared(pt, oom);
    }
        
    /**
     * This is for calculating the minimum distance between {@code this} and
     * {@code r}. If the rays intersect, the distance is zero. If they are going
     * away from each other, the distance is the distance between their start
     * points. If the rays get closer, then calculate the closest they come. If
     * they are parallel, then the distance between them does not change.
     *
     * @param r The line segment to return the distance from.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The distance from {@code this} to {@code r} at the {@code oom}
     * precision.
     */
    @Override
    public BigDecimal getDistance(V3D_Ray r, int oom) {
        if (isParallel(r, oom)) {
            //V3D_Geometry g = getLineOfIntersection(r.p, oom);
            V3D_Geometry g = getLineOfIntersection(r.getP(oom), oom);
            if (g instanceof V3D_Point) {
                return BigDecimal.ZERO;
            } else {
                return ((V3D_LineSegment) g).getLength(oom).toBigDecimal(oom);
            }
        } else {
            V3D_Line tl = new V3D_Line(this);
            //BigDecimal tldr = tl.getDistance(r, oom);
            //V3D_Geometry g = tl.getLineOfIntersection(r.p, oom);
            V3D_Geometry g = tl.getLineOfIntersection(r.getP(oom), oom);
            if (g instanceof V3D_Point) {
                return BigDecimal.ZERO;
            } else {
                V3D_LineSegment tlrp = (V3D_LineSegment) g;
                V3D_Line rl = new V3D_Line(r);
                //BigDecimal rldt = rl.getDistance(this, oom);
                //V3D_Geometry g2 = rl.getLineOfIntersection(p, oom);
                V3D_Geometry g2 = rl.getLineOfIntersection(getP(oom), oom);
                if (g2 instanceof V3D_Point) {
                    return BigDecimal.ZERO;
                } else {
                    V3D_LineSegment rltp = (V3D_LineSegment) g2;
                    //if (isIntersectedBy(tlrp.q, oom)) {
                    if (isIntersectedBy(tlrp.getQ(oom), oom)) {
                        BigDecimal tlrpl = tlrp.getLength(oom).toBigDecimal(oom);
                        //if (r.isIntersectedBy(rltp.q, oom)) {
                        if (r.isIntersectedBy(rltp.getQ(oom), oom)) {
                            return tlrpl.min(rltp.getLength(oom).toBigDecimal(oom));
                        } else {
                            return tlrpl;
                        }
                    } else {
                        //if (r.isIntersectedBy(rltp.q, oom)) {
                        if (r.isIntersectedBy(rltp.getQ(oom), oom)) {
                            return rltp.getLength(oom).toBigDecimal(oom);
                        } else {
                            //return p.getDistance(r.p, oom);
                            return getP(oom).getDistance(r.getP(oom), oom);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
