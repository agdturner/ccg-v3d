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
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * 3D representation of a ray - like a line, but one that starts at a point
 * continues infinitely in only one direction. The ray begins at the point
 * {@link #p} and goes through the point {@link #q}. The "*" denotes a point in
 * 3D and the ray is shown as a linear feature of "e" symbols in the following
 * depiction: {@code
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
 *                  /      |       * q=<x1,y1,z1>
 *                 /       |      e
 *                /        |     e
 *               +         -    e
 *              z          y   e
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Ray extends V3D_Geometry implements V3D_Intersection {

    private static final long serialVersionUID = 1L;

    /**
     * The line of this ray.
     */
    public V3D_Line l;

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_Ray(V3D_Ray r) {
        super(r.e);
        l = new V3D_Line(r.l);
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_Ray(V3D_Point p, V3D_Vector v) {
        super(p.e, p.offset);
        l = new V3D_Line(p, v);
    }

    /**
     * Create a new instance. {@link #offset} is set to {@link V3D_Vector#ZERO}.
     *
     * @param e What {@link #e} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     */
    public V3D_Ray(V3D_Environment e, V3D_Vector p, V3D_Vector q, int oom, RoundingMode rm) {
        this(e, V3D_Vector.ZERO, p, q, oom, rm);
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
            V3D_Vector q, int oom, RoundingMode rm) {
        super(e, offset);
        l = new V3D_Line(e, offset, p, q, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_Ray(V3D_Line l) {
        super(l.e, l.offset);
        this.l = new V3D_Line(l);
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #offset} and {@link #p} are set from.
     * @param q What {@link #q} is set from.
     */
    public V3D_Ray(V3D_Point p, V3D_Point q, int oom, RoundingMode rm) {
        super(p.e, p.offset);
        this.l = new V3D_Line(p, q, oom, rm);
    }

    /**
     * @param r The ray to test if it is the same as {@code this}.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} iff {@code r} is the same as {@code this}.
     */
    public boolean equals(V3D_Ray r, int oom, RoundingMode rm) {
        return l.getP().equals(r.l.getP(), oom, rm)
                && l.v.isScalarMultiple(r.l.v, oom, rm);
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

//    /**
//     * @param v The vector to translate to each coordinate of this.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return a new V3D_Ray which is {@code this} with the {@code v} applied.
//     */
//    @Override
//    public V3D_Ray translate(V3D_Vector v, int oom) {
//        return new V3D_Ray(pl.add(v, oom), q.add(v, oom), oom);
//    }
    /**
     * @param pt A point to test for intersection.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} if {@code this} is intersected by {@code pl}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom, RoundingMode rm) {
        if (pt.equals(l.getP(), oom, rm)) {
            return true;
        }
        if (l.isIntersectedBy(pt, oom, rm)) {
//            V3D_Point poi = l.getPointOfIntersection(pt, oom, rm);
//            V3D_Ray r = new V3D_Ray(e, getP(), poi.getVector(oom, rm));
            V3D_Plane pl = new V3D_Plane(l.getP(), l.v);
//            V3D_Ray r = new V3D_Ray(l.getP(), pt, oom, rm);
//            return r.l.getV(oom, rm).getDirection() == l.getV(oom, rm).getDirection();
            return pl.isOnSameSide(pt, this.l.getQ(oom, rm), oom, rm);
        }
//        boolean isPossibleIntersection = isPossibleIntersection(pt, oom, rm);
//        if (isPossibleIntersection) {
//            Math_BigRationalSqrt a = pl.getDistance(this.pl);
//            if (a.getX().isZero()) {
//                return true;
//            }
//            Math_BigRationalSqrt b = pl.getDistance(this.q);
//            if (b.getX().isZero()) {
//                return true;
//            }
//            Math_BigRationalSqrt l = this.pl.getDistance(this.q);
//            if (a.add(b, oom).compareTo(l) != 1) {
//                return true;
//            }
//        }
        return false;
    }

//    /**
//     * This compares the location of {@code pt} to the location of {@link #pl}
//     * and the direction of {@link #v}. If the {@code pt} is on a side of
//     * {@link #pl} and {@link #v} is moving away in any of the axial directions,
//     * then there is no chance of an intersection.
//     *
//     * @param pt The point to test for a possible intersection.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return {@code false} if there is no chance of intersection, and
//     * {@code true} otherwise.
//     */
//    private boolean isPossibleIntersection(V3D_Point pt, int oom) {
//        int ptxcpx = pt.x.compareTo(pl.x);
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
//        int ptycpy = pt.y.compareTo(pl.y);
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
//        int ptzcpz = pt.z.compareTo(pl.z);
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
//    /**
//     * @param r A ray to test if it intersects with {@code this}.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return {@code true} iff {@code r} intersects with {@code this}.
//     */
//    @Override
//    public boolean isIntersectedBy(V3D_Ray r, int oom, RoundingMode rm) {
//        if (l.getP().equals(r.l.getP(), oom, rm)) {
//            return true;
//        }
//        boolean ril = r.isIntersectedBy(l, oom, rm);
//        if (ril == false) {
//            return false;
//        }
//        boolean tirl = isIntersectedBy(r.l, oom, rm);
//        if (tirl == false) {
//            return false;
//        }
//        /**
//         * The rays may point along the same line. If they point in the same
//         * direction, then they intersect. If they point in opposite directions,
//         * then they do not intersect unless the points they start at intersect
//         * with the other ray.
//         */
//        if (ril && tirl) {
//            if (r.isIntersectedBy(l.getP(), oom, rm)) {
//                return true;
//            }
//            if (isIntersectedBy(r.l.getP(), oom, rm)) {
//                return true;
//            }
//        }
//        return isIntersectedBy((V3D_Point) getIntersection(r, oom, rm), oom, rm);
//    }
//
//    /**
//     * @param l A line segment to test if it intersects with {@code this}.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return {@code true} iff {@code l} intersects with {@code this}.
//     */
//    @Override
//    public boolean isIntersectedBy(V3D_LineSegment l, int oom, RoundingMode rm) {
//        V3D_Ray rlpq = new V3D_Ray(l.l);
//        if (!isIntersectedBy(rlpq, oom, rm)) {
//            return false;
//        }
//        V3D_Ray rlqp = new V3D_Ray(e, l.offset, l.q, l.l.p, oom, rm);
//        return isIntersectedBy(rlqp, oom, rm);
//    }
//    /**
//     * @param l A line to test for intersection.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return {@code true} iff {@code l} intersects with {@code this}.
//     */
//    @Override
//    public boolean isIntersectedBy(V3D_Line l, int oom, RoundingMode rm) {
//        V3D_Geometry i = this.l.getIntersection(l, oom, rm);
//        if (i == null) {
//            return false;
//        }
//        if (i instanceof V3D_Point pt) {
//            return isIntersectedBy(pt, oom, rm);
//        } else {
//            return true;
//        }
//    }
//    /**
//     * @param pl A plane to test for intersection.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return {@code true} iff {@code pl} intersects with {@code this}.
//     */
//    @Override
//    public boolean isIntersectedBy(V3D_Plane pl, int oom, RoundingMode rm) {
//        V3D_Geometry i = this.l.getIntersection(pl, oom, rm);
//        if (i == null) {
//            return false;
//        }
//        if (i instanceof V3D_Point pt) {
//            //return isIntersectedBy(pt, oom, rm);
//            V3D_Plane pl2 = new V3D_Plane(l.getP(), l.v);
//            return pl2.isOnSameSide(pt, l.getQ(oom, rm), oom, rm);
//        } else {
//            return true;
//        }
//    }
//    /**
//     * Currently this is not taking into account the direction of the ray.
//     * 
//     * 
//     * @param t A triangle to test for intersection.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return {@code true} iff {@code t} intersects with {@code this}.
//     */
//    @Override
//    public boolean isIntersectedBy(V3D_Triangle t, int oom, RoundingMode rm) {
//        V3D_Geometry i = l.getIntersection(t, oom, rm);
//        if (i == null) {
//            return false;
//        }
//        if (i instanceof V3D_Point pt) {
//            if (t.isAligned(pt, oom, rm)) {
//                return true;
//            }
//        } else {
//            return false;
//            //return isIntersectedBy((V3D_LineSegment) i, oom, rm);
//        }
//        return false;
//    }
//    @Override
//    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom, RoundingMode rm) {
//        if (isIntersectedBy(t.getPqr(oom, rm), oom, rm)) {
//            return true;
//        }
//        if (isIntersectedBy(t.getPsq(oom, rm), oom, rm)) {
//            return true;
//        }
//        if (isIntersectedBy(t.getQsr(oom, rm), oom, rm)) {
//            return true;
//        }
//        return isIntersectedBy(t.getSpr(oom, rm), oom, rm);
//    }
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
     * orientation of the axes and origin)
     *
     * @TODO Refactor ray-plane intersection to choose on or before, or on or
     * after.
     *
     * @param pl The plane to get the geometrical intersection with this.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code pl}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Plane pl, int oom, RoundingMode rm) {
        V3D_Geometry g = l.getIntersection(pl, oom, rm);
        if (g == null) {
            return null;
        }
        if (g instanceof V3D_Point gp) {
            //V3D_Plane rp = new V3D_Plane(this.l.getP(), this.l.getV(oom, rm), oom, rm);
            V3D_Plane rp = new V3D_Plane(this.l.getP(), this.l.v);
            if (rp.isOnSameSide(gp, this.l.getQ(oom, rm), oom, rm)) {
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
//                if (isIntersectedBy(pt, oom, rm)) {
//                    return pt;
//                } else {
//                    return null;
//                }
//            } else {
//                V3D_Point pt = l.getP();
//                V3D_Line gl = (V3D_Line) g;
//                V3D_Point glp = gl.getP();
//                int dir = l.getV(oom, rm).getDirection();
//                if (isIntersectedBy(pt, oom, rm)) {
//                    if (pt.equals(glp, oom, rm)) {
//                        V3D_Point glq = gl.getQ(oom, rm);
//                        V3D_Vector ptglq = new V3D_Vector(pt, glq, oom, rm);
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
//                    V3D_Vector ptglp = new V3D_Vector(pt, glp, oom, rm);
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

    /**
     * Intersects {@code this} with {@code t}. {@code null} is returned if there
     * is no intersection.
     *
     * @param t The triangle to get the geometrical intersection with this.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code t}.
     */
    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Triangle t, int oom, RoundingMode rm) {
        V3D_Geometry g = getIntersection(t.pl, oom, rm);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_Point pt) {
                if (t.isAligned(pt, oom, rm)) {
                    return pt;
                } else {
                    return null;
                }
            } else {
                V3D_Geometry g2 = t.getIntersection(l, oom, rm);
                if (g2 instanceof V3D_Point g2p) {
                    //if (isIntersectedBy(g2p, oom, rm)) {
                    if (t.isAligned(g2p, oom, rm)) {
                        return g2p;
                    } else {
                        return null;
                    }
                } else {
                    return getIntersection((V3D_LineSegment) g2, oom, rm);
                }
            }
        }
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        V3D_FiniteGeometry pqri = getIntersection(t.getPqr(oom, rm), oom, rm);
        if (pqri == null) {
            V3D_FiniteGeometry psqi = getIntersection(t.getPsq(oom, rm), oom, rm);
            if (psqi == null) {
                V3D_FiniteGeometry qsri = getIntersection(t.getQsr(oom, rm), oom, rm);
                if (qsri == null) {
                    V3D_Geometry spri = getIntersection(t.getSpr(oom, rm), oom, rm);
                    if (spri == null) {
                        return null;
                    } else {
                        return V3D_LineSegment.getGeometry(l.getP(), (V3D_Point) spri, oom, rm);
                    }
                } else if (qsri instanceof V3D_Point qsrip) {
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(oom, rm), oom, rm);
                    if (spri == null) {
                        return V3D_LineSegment.getGeometry(l.getP(), qsrip, oom, rm);
                    } else if (spri instanceof V3D_Point sprip) {
                        if (qsrip.equals(sprip, oom, rm)) {
                            return V3D_LineSegment.getGeometry(l.getP(), qsrip, oom, rm);
                        } else {
                            return new V3D_LineSegment(qsrip, sprip, oom, rm);
                        }
                    } else {
                        return spri;
                    }
                } else {
                    return qsri;
                }
            } else if (psqi instanceof V3D_Point psqip) {
                V3D_FiniteGeometry qsri = getIntersection(t.getQsr(oom, rm), oom, rm);
                if (qsri == null) {
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(oom, rm), oom, rm);
                    if (spri == null) {
                        return V3D_LineSegment.getGeometry(l.getP(), psqip, oom, rm);
                    } else if (spri instanceof V3D_Point sprip) {
                        if (psqip.equals(sprip, oom, rm)) {
                            return V3D_LineSegment.getGeometry(l.getP(), psqip, oom, rm);
                        } else {
                            return new V3D_LineSegment(psqip, sprip, oom, rm);
                        }
                    } else {
                        return spri;
                    }
                } else if (qsri instanceof V3D_Point qsrip) {
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(oom, rm), oom, rm);
                    if (spri == null) {
                        if (psqip.equals(qsrip, oom, rm)) {
                            return V3D_LineSegment.getGeometry(l.getP(), psqip, oom, rm);
                        } else {
                            return new V3D_LineSegment(psqip, qsrip, oom, rm);
                        }
                    } else if (spri instanceof V3D_Point sprip) {
                        if (psqip.equals(qsrip, oom, rm)) {
                            if (psqip.equals(sprip, oom, rm)) {
                                return V3D_LineSegment.getGeometry(l.getP(), psqip, oom, rm);
                            } else {
                                return new V3D_LineSegment(psqip, sprip, oom, rm);
                            }
                        } else {
                            return new V3D_LineSegment(psqip, qsrip, oom, rm);
                        }
                    } else {
                        return spri;
                    }
                } else {
                    return qsri;
                }
            } else {
                return psqi;
            }
        } else if (pqri instanceof V3D_Point pqrip) {
            V3D_FiniteGeometry psqi = getIntersection(t.getPsq(oom, rm), oom, rm);
            if (psqi == null) {
                V3D_FiniteGeometry qsri = getIntersection(t.getQsr(oom, rm), oom, rm);
                if (qsri == null) {
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(oom, rm), oom, rm);
                    if (spri == null) {
                        return V3D_LineSegment.getGeometry(l.getP(), pqrip, oom, rm);
                    } else if (spri instanceof V3D_Point sprip) {
                        if (pqrip.equals(sprip, oom, rm)) {
                            return V3D_LineSegment.getGeometry(l.getP(), pqrip, oom, rm);
                        } else {
                            return new V3D_LineSegment(pqrip, sprip, oom, rm);
                        }
                    } else {
                        return spri;
                    }
                } else if (qsri instanceof V3D_Point qsrip) {
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(oom, rm), oom, rm);
                    if (spri == null) {
                        if (pqrip.equals(qsrip, oom, rm)) {
                            return V3D_LineSegment.getGeometry(l.getP(), qsrip, oom, rm);
                        } else {
                            return new V3D_LineSegment(pqrip, qsrip, oom, rm);
                        }
                    } else if (spri instanceof V3D_Point sprip) {
                        if (pqrip.equals(qsrip, oom, rm)) {
                            if (qsrip.equals(sprip, oom, rm)) {
                                return V3D_LineSegment.getGeometry(l.getP(), qsrip, oom, rm);
                            } else {
                                return new V3D_LineSegment(qsrip, sprip, oom, rm);
                            }
                        } else {
                            return V3D_LineSegment.getGeometry(pqrip, qsrip, oom, rm);
                        }
                    } else {
                        return spri;
                    }
                } else {
                    return qsri;
                }
            } else if (psqi instanceof V3D_Point psqip) {
                V3D_FiniteGeometry qsri = getIntersection(t.getQsr(oom, rm), oom, rm);
                if (qsri == null) {
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(oom, rm), oom, rm);
                    if (spri == null) {
                        if (pqrip.equals(psqip, oom, rm)) {
                            return V3D_LineSegment.getGeometry(l.getP(), pqrip, oom, rm);
                        } else {
                            return new V3D_LineSegment(pqrip, psqip, oom, rm);
                        }
                    } else if (spri instanceof V3D_Point sprip) {
                        if (pqrip.equals(psqip, oom, rm)) {
                            if (psqip.equals(sprip, oom, rm)) {
                                return V3D_LineSegment.getGeometry(l.getP(), psqip, oom, rm);
                            } else {
                                return new V3D_LineSegment(psqip, sprip, oom, rm);
                            }
                        } else {
                            return new V3D_LineSegment(pqrip, psqip, oom, rm);
                        }
                    } else {
                        return spri;
                    }
                } else if (qsri instanceof V3D_Point qsrip) {
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(oom, rm), oom, rm);
                    if (spri == null) {
                        if (pqrip.equals(psqip, oom, rm)) {
                            if (psqip.equals(qsrip, oom, rm)) {
                                return V3D_LineSegment.getGeometry(l.getP(), psqip, oom, rm);
                            } else {
                                return new V3D_LineSegment(psqip, qsrip, oom, rm);
                            }
                        } else {
                            return new V3D_LineSegment(pqrip, psqip, oom, rm);
                        }
                    } else if (spri instanceof V3D_Point sprip) {
                        if (pqrip.equals(psqip, oom, rm)) {
                            if (psqip.equals(qsrip, oom, rm)) {
                                return V3D_LineSegment.getGeometry(pqrip, sprip, oom, rm);
                            } else {
                                return new V3D_LineSegment(psqip, qsrip, oom, rm);
                            }
                        } else {
                            return new V3D_LineSegment(pqrip, psqip, oom, rm);
                        }
                    } else {
                        return spri;
                    }
                } else {
                    return qsri;
                }
            } else {
                return psqi;
            }
        } else {
            return pqri;
        }
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
    public V3D_Geometry getIntersection(V3D_Line l, int oom, RoundingMode rm) {
        // Check if infinite lines intersect.
        V3D_Geometry g = this.l.getIntersection(l, oom, rm);
        if (g == null) {
            // There is no intersection.
            return g;
        }
        /**
         * If lines intersects at a point, then check this point is on this.
         */
        if (g instanceof V3D_Point pt) {
            if (isIntersectedBy(pt, oom, rm)) {
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
    public V3D_Geometry getIntersection(V3D_Ray r, int oom, RoundingMode rm) {
        V3D_Geometry rtl = r.getIntersection(l, oom, rm);
        if (rtl == null) {
            return null;
        } else if (rtl instanceof V3D_Point pt) {
            V3D_Plane pl = new V3D_Plane(l.getP(), l.v);
            if (pl.isOnSameSide(pt, l.getQ(oom, rm), oom, rm)) {
                return pt;
            } else {
                return null;
            }
        } else {
            // Then rtl is an instance of V3D_Ray.
            V3D_Geometry grl = getIntersection(r.l, oom, rm);
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
                V3D_Plane pl = new V3D_Plane(tp, l.v);
                V3D_Plane pl2 = new V3D_Plane(rp, r.l.v);
                if (pl.isOnSameSide(rp, l.getQ(oom, rm), oom, rm)) {
                    if (pl2.isOnSameSide(tp, r.l.getQ(oom, rm), oom, rm)) {
                        if (tp.equals(rp, oom, rm)) {
                            return tp;
                        }
                        return new V3D_LineSegment(rp, tp, oom, rm);
                    } else {
                        return new V3D_Ray(rp, l.v);
                    }
                } else {
                    if (pl2.isOnSameSide(tp, r.l.getQ(oom, rm), oom, rm)) {
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
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment ls, int oom, RoundingMode rm) {
        V3D_Geometry g = getIntersection(ls.l, oom, rm);
        if (g == null) {
            return null;
        } else if (g instanceof V3D_Point pt) {
            V3D_Plane pl = new V3D_Plane(l.getP(), l.v);
            if (pl.isOnSameSide(pt, ls.getQ(), oom, rm)) {
                return pt;
            } else {
                return null;
            }
        } else {
            V3D_Ray r = (V3D_Ray) g;
            V3D_Point rp = r.l.getP();
            V3D_Point rq = r.l.getQ(oom, rm);
            V3D_Point lsp = ls.getP();
            V3D_Point lsq = ls.getQ();
            V3D_Plane pl = new V3D_Plane(rp, r.l.v);
            if (pl.isOnSameSide(rq, lsp, oom, rm)){
                if (pl.isOnSameSide(rq, lsq, oom, rm)){
                    return ls;
                } else {
                    if (lsp.equals(rp, oom, rm)) {
                        return rp;
                    }
                    return new V3D_LineSegment(lsp, rp, oom, rm);
                }
            } else {
                if (pl.isOnSameSide(rq, lsq, oom, rm)){
                    if (lsq.equals(rp, oom, rm)) {
                        return rp;
                    }
                    return new V3D_LineSegment(lsq, rp, oom, rm);
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     * @param oom The Order of Magnitude for the precision.
     */
    @Override
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        l.offset = offset;
        //l.translate(v, oom, rm);
    }

    @Override
    public V3D_Ray rotate(V3D_Vector axisOfRotation, Math_BigRational theta,
            int oom, RoundingMode rm) {
        V3D_Ray r;
        if (theta.compareTo(Math_BigRational.ZERO) == 1) {
            V3D_Line rl = this.l.rotate(axisOfRotation, theta, oom, rm);
            r = new V3D_Ray(rl);
        } else {
            r = new V3D_Ray(this);
        }
        return r;
    }
}
