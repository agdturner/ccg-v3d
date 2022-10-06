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
import java.util.Objects;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import static uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegment.getGeometry;

/**
 * 3D representation of a ray - like a line, but one that starts at a point
 * continues infinitely in only one direction. The ray begins at the point
 * {@link #p} and goes through the point {@link #q}. The "*" denotes a point in
 * 3D and the ray is shown as a linear feature of "e" symbols in the following
 * depiction: {@code
 *                                       z
 *                          y           -
 *                          +          /                * p=<x0,y0,z0>
 *                          |         /                e
 *                          |    z0 -/                e
 *                          |       /                e
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
 *                      /   |          e
 *                 z1 -/    |         e
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
public class V3D_Ray extends V3D_Geometry
        implements V3D_IntersectionAndDistanceCalculations {

    private static final long serialVersionUID = 1L;

    /**
     * The line of this ray.
     */
    V3D_Line l;

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_Ray(V3D_Ray r) {
        super(r.e, r.offset);
        this.l = new V3D_Line(r.l);
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_Ray(V3D_Point p, V3D_Vector v) {
        super(p.e, p.offset);
        this.l = new V3D_Line(offset, p.rel, v, e);
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
        super(e, offset);
        this.l = new V3D_Line(e, offset, p, q);
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
    public V3D_Ray(V3D_Point p, V3D_Point q, int oom, RoundingMode rm) {
        super(p.e, p.offset);
        if (q.offset.equals(p.offset)) {
            l = new V3D_Line(p.e, p.offset, p.rel, q.rel);
        } else {
            q.setOffset(offset, oom, rm);
            l = new V3D_Line(p.e, p.offset, p.rel, q.rel);
        }
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #offset} and {@link #p} are set from.
     * @param q What {@link #q} is set from.
     * @param noCheck This is ignored, but in calling this constructor there is
     * no check that p.offset equals q.offset and ensuring that the offset is
     * the same for {@link #l} and {@link #offset}.
     */
    public V3D_Ray(V3D_Point p, V3D_Point q, boolean noCheck) {
        super(p.e, p.offset);
        l = new V3D_Line(p.e, p.offset, p.rel, q.rel);
    }

    /**
     * @param r The ray to test if it is the same as {@code this}.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} iff {@code r} is the same as {@code this}.
     */
    public boolean equals(V3D_Ray r, int oom, RoundingMode rm) {
        return l.getP().equals(r.l.getP(), oom, rm)
                && isIntersectedBy(r.l.getQ(oom, rm), oom, rm);
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
        String r = super.toStringFields(pad) + "\n"
                + pad + ",\n";
        r += pad + l.toStringFields(pad);
        return r;
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFieldsSimple(String pad) {
        String r = super.toStringFieldsSimple(pad) + ",\n";
        r += pad + l.toStringSimple(pad);
        return r;
    }

//    /**
//     * @param v The vector to translate to each coordinate of this.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return a new V3D_Ray which is {@code this} with the {@code v} applied.
//     */
//    @Override
//    public V3D_Ray translate(V3D_Vector v, int oom) {
//        return new V3D_Ray(p.add(v, oom), q.add(v, oom), oom);
//    }
    /**
     * @param pt A point to test for intersection.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} if {@code this} is intersected by {@code p}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom, RoundingMode rm) {
        if (pt.equals(l.getP(), oom, rm)) {
            return true;
        }
        /**
         * The following if statement is not necessary, but it may be
         * computationally advantageous.
         */
        if (pt.equals(l.getQ(oom, rm), oom, rm)) {
            return true;
        }
        if (l.isIntersectedBy(pt, oom, rm)) {
//            V3D_Point poi = l.getPointOfIntersection(pt, oom, rm);
//            V3D_Ray r = new V3D_Ray(e, getP(), poi.getVector(oom, rm));

            V3D_Ray r = new V3D_Ray(l.getP(), pt, oom, rm);

            return r.l.getV(oom, rm).getDirection() == l.getV(oom, rm).getDirection();
        }
//        boolean isPossibleIntersection = isPossibleIntersection(pt, oom, rm);
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
    public boolean isIntersectedBy(V3D_Ray r, int oom, RoundingMode rm) {
        if (l.getP().equals(r.l.getP(), oom, rm)) {
            return true;
        }
        boolean ril = r.isIntersectedBy(l, oom, rm);
        if (ril == false) {
            return false;
        }
        boolean tirl = isIntersectedBy(r.l, oom, rm);
        if (tirl == false) {
            return false;
        }
        /**
         * The rays may point along the same line. If they point in the same
         * direction, then they intersect. If they point in opposite directions,
         * then they do not intersect unless the points they start at intersect
         * with the other ray.
         */
        if (ril && tirl) {
            if (r.isIntersectedBy(l.getP(), oom, rm)) {
                return true;
            }
            if (isIntersectedBy(r.l.getP(), oom, rm)) {
                return true;
            }
        }
        return isIntersectedBy((V3D_Point) getIntersection(r, oom, rm), oom, rm);
    }

    /**
     * @param l A line segment to test if it intersects with {@code this}.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} iff {@code l} intersects with {@code this}.
     */
    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, RoundingMode rm) {
        V3D_Ray rlpq = new V3D_Ray(l.l);
        if (!isIntersectedBy(rlpq, oom, rm)) {
            return false;
        }
        V3D_Ray rlqp = new V3D_Ray(e, l.l.q, l.l.p);
        return isIntersectedBy(rlqp, oom, rm);
    }

    /**
     * @param l A line to test for intersection.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} iff {@code l} intersects with {@code this}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom, RoundingMode rm) {
        V3D_Geometry i = this.l.getIntersection(l, oom, rm);
        if (i == null) {
            return false;
        }
        if (i instanceof V3D_Point pt) {
            return isIntersectedBy(pt, oom, rm);
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
    public boolean isIntersectedBy(V3D_Plane pl, int oom, RoundingMode rm) {
        V3D_Geometry i = this.l.getIntersection(pl, oom, rm);
        if (i == null) {
            return false;
        }
        if (i instanceof V3D_Point pt) {
            return isIntersectedBy(pt, oom, rm);
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
    public boolean isIntersectedBy(V3D_Triangle t, int oom, RoundingMode rm) {
        V3D_Geometry i = l.getIntersection(t, oom, rm);
        if (i == null) {
            return false;
        }
        if (i instanceof V3D_Point pt) {
            return isIntersectedBy(pt, oom, rm);
        } else {
            return isIntersectedBy((V3D_LineSegment) i, oom, rm);
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        if (isIntersectedBy(t.getPqr(), oom, rm)) {
            return true;
        }
        if (isIntersectedBy(t.getPsq(), oom, rm)) {
            return true;
        }
        if (isIntersectedBy(t.getQsr(), oom, rm)) {
            return true;
        }
        return isIntersectedBy(t.getSpr(), oom, rm);
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
     * orientation of the axes and origin)
     *
     * @TODO Refactor ray-plane intersection to choose on or before, or on or
     * after.
     *
     * @param pl The plane to get the geometrical intersection with this.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code p}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Plane pl, int oom, RoundingMode rm) {
        V3D_Geometry g = l.getIntersection(pl, oom, rm);
        if (g == null) {
            return g;
        } else {
            if (g instanceof V3D_Point pt) {
                if (isIntersectedBy(pt, oom, rm)) {
                    return pt;
                } else {
                    return null;
                }
            } else {
                V3D_Point pt = l.getP();
                V3D_Line gl = (V3D_Line) g;
                V3D_Point glp = gl.getP();
                int dir = l.getV(oom, rm).getDirection();
                if (isIntersectedBy(pt, oom, rm)) {
                    if (pt.equals(glp, oom, rm)) {
                        V3D_Point glq = gl.getQ(oom, rm);
                        V3D_Vector ptglq = new V3D_Vector(pt, glq, oom, rm);
                        int dir_ptglq = ptglq.getDirection();
                        if (dir == dir_ptglq) {
                            return this;
                        } else {
                            return pt;
                        }
                    } else {
                        return this;
                    }
                } else {
                    V3D_Vector ptglp = new V3D_Vector(pt, glp, oom, rm);
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
    public V3D_FiniteGeometry getIntersection(V3D_Triangle t, int oom, RoundingMode rm) {
        V3D_Geometry g = getIntersection(t.p, oom, rm);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_Point pt) {
                if (isIntersectedBy(pt, oom, rm)) {
                    return pt;
                } else {
                    return null;
                }
            } else {
                V3D_Geometry g2 = t.getIntersection(l, oom, rm);
                if (g2 instanceof V3D_Point g2p) {
                    if (isIntersectedBy(g2p, oom, rm)) {
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
        V3D_FiniteGeometry pqri = getIntersection(t.getPqr(), oom, rm);
        if (pqri == null) {
            V3D_FiniteGeometry psqi = getIntersection(t.getPsq(), oom, rm);
            if (psqi == null) {
                V3D_FiniteGeometry qsri = getIntersection(t.getQsr(), oom, rm);
                if (qsri == null) {
                    V3D_Geometry spri = getIntersection(t.getSpr(), oom, rm);
                    if (spri == null) {
                        return null;
                    } else {
                        return V3D_LineSegment.getGeometry(l.getP(), (V3D_Point) spri, oom, rm);
                    }
                } else if (qsri instanceof V3D_Point qsrip) {
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(), oom, rm);
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
                V3D_FiniteGeometry qsri = getIntersection(t.getQsr(), oom, rm);
                if (qsri == null) {
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(), oom, rm);
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
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(), oom, rm);
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
            V3D_FiniteGeometry psqi = getIntersection(t.getPsq(), oom, rm);
            if (psqi == null) {
                V3D_FiniteGeometry qsri = getIntersection(t.getQsr(), oom, rm);
                if (qsri == null) {
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(), oom, rm);
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
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(), oom, rm);
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
                V3D_FiniteGeometry qsri = getIntersection(t.getQsr(), oom, rm);
                if (qsri == null) {
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(), oom, rm);
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
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(), oom, rm);
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
    @Override
    public V3D_Geometry getIntersection(V3D_Ray r, int oom, RoundingMode rm) {
        V3D_Geometry rtl = r.getIntersection(l, oom, rm);
        if (rtl == null) {
            return null;
        } else if (rtl instanceof V3D_Point pt) {
            if (r.isIntersectedBy(pt, oom, rm)) {
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
                V3D_Point rp = r.l.getP();
                if (isIntersectedBy(rp, oom, rm)) {
                    V3D_Point tp = l.getP();
                    if (r.isIntersectedBy(tp, oom, rm)) {
                        return V3D_LineSegment.getGeometry(rp, tp, oom, rm);
//                        return new V3D_LineSegment(e, rp.getVector(oom),
//                                tp.getVector(oom));
                    } else {
                        return r;
                    }
                } else {
                    if (isIntersectedBy(r.l.getQ(oom, rm), oom, rm)) {
                        return this;
                    } else {
                        if (r.l.getV(oom, rm).getDirection() == l.getV(oom, rm).getDirection()) {
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
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment l, int oom, RoundingMode rm) {
        V3D_Geometry g = getIntersection(l.l, oom, rm);
        if (g == null) {
            return null;
        } else if (g instanceof V3D_Point pt) {
            if (isIntersectedBy(pt, oom, rm)) {
                return pt;
            } else {
                return null;
            }
        } else if (g instanceof V3D_Ray r) {
            V3D_Point rp = r.l.getP();
            V3D_Point lp = l.getP();
            V3D_Point lq = l.getQ(oom, rm);
            if (rp.equals(lq, oom, rm)) {
                return rp;
            }
            V3D_LineSegment lsrplq = new V3D_LineSegment(rp, lq, oom, rm);
            int rvdirection = r.l.getV(oom, rm).getDirection();
            if (rp.equals(lp, oom, rm)) {
                if (lsrplq.l.getV(oom, rm).getDirection() == rvdirection) {
                    return l;
                } else {
                    return rp;
                }
            } else {
                V3D_LineSegment lsrplp = new V3D_LineSegment(rp, lp, oom, rm);
                if (rp.equals(lq, oom, rm)) {
                    if (lsrplp.l.getV(oom, rm).getDirection() == rvdirection) {
                        return l;
                    } else {
                        return rp;
                    }
                } else {
                    if (lsrplq.l.getV(oom, rm).getDirection() == rvdirection) {
                        if (lsrplp.l.getV(oom, rm).getDirection() == rvdirection) {
                            return l;
                        } else {
                            return new V3D_LineSegment(rp, lq, oom, rm);
                        }
                    } else {
                        if (lsrplp.l.getV(oom, rm).getDirection() == rvdirection) {
                            return new V3D_LineSegment(rp, lp, oom, rm);
                        } else {
                            return null;
                        }
                    }
                }
            }
        } else {
            V3D_LineSegment ls = (V3D_LineSegment) g;
            V3D_Point lsp = ls.getP();
            if (isIntersectedBy(lsp, oom, rm)) {
                V3D_Point lsq = ls.getQ(oom, rm);
                if (isIntersectedBy(lsq, oom, rm)) {
                    return ls;
                } else {
                    return new V3D_LineSegment(lsp, this.l.getP(), oom, rm);
                }
            } else {
                V3D_Point lsq = ls.getQ(oom, rm);
                if (isIntersectedBy(lsq, oom, rm)) {
                    return new V3D_LineSegment(lsq, this.l.getP(), oom, rm);
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * For returning the line of intersection from pt to the ray
     *
     * @param pt A point for which the shortest line to this is returned.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The line having the shortest distance between {@code pt} and
     * {@code this}.
     */
    //@Override Used to override from V3D_Line
    public V3D_FiniteGeometry getLineOfIntersection(V3D_Point pt, int oom, RoundingMode rm) {
        V3D_Ray r = new V3D_Ray(l.getP(), pt, oom, rm);
        Math_BigRational piby2 = Math_BigRational.valueOf(e.bD.getPiBy2(oom, rm));
        if (r.l.getV(oom, rm).getAngle(l.getV(oom, rm), oom, rm).compareTo(piby2) != 1) {
            return l.getLineOfIntersection(pt, oom, rm);
        } else {
            return new V3D_LineSegment(pt, l.getP(), oom, rm);
        }
//        int rDir = r.l.v.getDirection();
//        int tDir = l.v.getDirection();
//        
//        // Special case
//        V3D_Vector pv = l.getPV(oom, rm);
//        V3D_Vector ptv = pt.getVector(oom, rm);
//        if (ptv.equals(pv)) {
//            return pt;
//        }
//        V3D_Geometry i = l.getLineOfIntersection(pt, oom, rm);
//        if (i instanceof V3D_Point ip) {
//            V3D_Ray r = new V3D_Ray(ip, pv);
//            if (r.l.getV(oom, rm).getDirection() == l.getV(oom, rm).getDirection()) {
//                return new V3D_LineSegment(e, pv, ptv);
//            } else {
//                return ip;
//            }
//        } else {
//            V3D_LineSegment il = (V3D_LineSegment) i;
//            V3D_Ray r = new V3D_Ray(e, ptv, pv);
//            if (r.l.getV(oom, rm).getDirection() == l.getV(oom, rm).getDirection()) {
//                return new V3D_LineSegment(e, ptv, il.l.getQV(oom, rm));
//            } else {
//                //return new V3D_LineSegment(ptv, il.q, oom);
//                return il;
//            }
//        }
//        if (isIntersectedBy(i.q, oom)) {
//            return i;
//        } else {
//            return new V3D_LineSegment(pt, p, oom);
//        }
    }

//    @Override
//    public V3D_Geometry getLineOfIntersection(V3D_Line l, int oom) {
//        V3D_Geometry loi = new V3D_Line(this).getLineOfIntersection(l, oom);
//        if (loi == null) {
//            return loi;
//        }
//        //V3D_Geometry loi2 = l.getLineOfIntersection(p, oom);
//        V3D_Geometry loi2 = l.getLineOfIntersection(getP(oom), oom);
//        if (loi instanceof V3D_Point v3D_Point) {
//            //V3D_Ray r = new V3D_Ray(p, v3D_Point, oom);
//            V3D_Ray r = new V3D_Ray(e, getPV(oom), v3D_Point.getVector(oom));
//            if (r.getV(oom).getDirection() == getV(oom).getDirection()) {
//                return loi;
//            } else {
//                return loi2;
//            }
//        } else {
//            V3D_Ray r = new V3D_Ray(e, getP(), ((V3D_LineSegment) loi).getP());
//            if (r.getV(oom).getDirection() == getV(oom).getDirection()) {
//                return loi2;
//            } else {
//                return loi;
//            }
//        }
//    }
    /**
     * Get the line of intersection (the shortest line) between {@code this} and
     * {@code l}.
     *
     * @param l The line to get the line of intersection with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The line of intersection between {@code this} and {@code l}.
     */
    //@Override Used to override from V3D_Line
    public V3D_FiniteGeometry getLineOfIntersection(V3D_Line l, int oom, RoundingMode rm) {
        V3D_Geometry loi = this.l.getLineOfIntersection(l, oom, rm);
        /**
         * If the loi is a line segment with one point on this and one point on
         * l, then this is a success. However, if one of the points is not on
         * this, then the other point is that point of this closest to that
         * point.
         */
        if (loi == null) {
            return null;
        }
        if (loi instanceof V3D_Point loip) {
            if (isIntersectedBy(loip, oom, rm)) {
                return null;
            } else {
                return l.getLineOfIntersection(getNearestPoint(loip, oom, rm), oom, rm);
            }
        } else {
            V3D_LineSegment loil = (V3D_LineSegment) loi;
            if (isIntersectedBy(loil, oom, rm)) {
                return loil;
            } else {
                V3D_Point loilp = loil.getP();
                //V3D_Point loilq = loil.getQ(oom, rm);
                V3D_Point tp = this.l.getP();
                V3D_Point tq = this.l.getQ(oom, rm);
                if (isIntersectedBy(loilp, oom, rm)) {
                    return new V3D_LineSegment(tp, l.getPointOfIntersection(tp, oom, rm), oom, rm);
                } else {
                    return new V3D_LineSegment(tq, l.getPointOfIntersection(tq, oom, rm), oom, rm);
                }
            }
        }
    }

    /**
     * @param pt Point.
     * @return The nearest point on {@code this} to {@code pt}.
     */
    private V3D_Point getNearestPoint(V3D_Point pt, int oom, RoundingMode rm) {
        V3D_Point poi = l.getPointOfIntersection(pt, oom, rm);
        if (isIntersectedBy(poi, oom, rm)) {
            return poi;
        } else {
            return l.getP();
        }
    }

    /**
     * Get the line of intersection (the shortest line) between {@code this} and
     * {@code l}.
     *
     * @paramlsl The line segment to get the line of intersection with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The line of intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry getLineOfIntersection(V3D_LineSegment ls, int oom, RoundingMode rm) {
        V3D_FiniteGeometry tloi = l.getLineOfIntersection(ls.l, oom, rm);
        V3D_Line ll = new V3D_Line(ls);
        V3D_Geometry lloi = getLineOfIntersection(ll, oom, rm);
        if (tloi == null) {
            if (lloi == null) {
                V3D_Point poi = getPointOfIntersection(ls.getP(), oom, rm);
                V3D_Point poi2 = ls.l.getPointOfIntersection(poi, oom, rm);
                return getGeometry(poi, poi2, oom, rm);
//                return null;
            } else if (lloi instanceof V3D_Point lloip) {
                return new V3D_LineSegment(getNearestPoint(lloip, oom, rm), lloip, oom, rm);
            } else {
                // lloi instanceof V3D_LineSegment
                return null;
            }
        } else if (tloi instanceof V3D_Point tloip) {
            if (lloi == null) {
                return new V3D_LineSegment(getNearestPoint(tloip, oom, rm), tloip, oom, rm);
            } else {
                //V3D_Point lp = ls.getP();
                //V3D_Point lq = ls.getQ();
                if (lloi instanceof V3D_Point lloip) {
                    return getGeometry(lloip, tloip, oom, rm);
                } else {
                    V3D_LineSegment lloil = (V3D_LineSegment) lloi;
                    V3D_Point lloilp = lloil.getP();
                    V3D_Point lloilq = lloil.getQ(oom, rm);
                    if (isIntersectedBy(lloilp, oom, rm)) {
                        return new V3D_LineSegment(lloilp, V3D_LineSegment.getNearestPoint(ls, lloilq, oom, rm), oom, rm);
                    } else {
                        return new V3D_LineSegment(lloilq, V3D_LineSegment.getNearestPoint(ls, lloilp, oom, rm), oom, rm);
                    }
                }
            }
        } else {
            if (lloi == null) {
                return null;
            } else {
                V3D_LineSegment tloil = (V3D_LineSegment) tloi;
                V3D_Point tloilp = tloil.getP();
                V3D_Point tloilq = tloil.getQ(oom, rm);
                if (lloi instanceof V3D_Point) {
                    if (isIntersectedBy(tloilp, oom, rm)) {
                        return new V3D_LineSegment(tloilp, getNearestPoint(tloilq, oom, rm), oom, rm);
                    } else {
                        return new V3D_LineSegment(tloilq, getNearestPoint(tloilp, oom, rm), oom, rm);
                    }
                } else {
                    V3D_LineSegment lloil = (V3D_LineSegment) lloi;
                    V3D_Point lloilp = lloil.getP();
                    V3D_Point lloilq = lloil.getQ(oom, rm);
                    if (l.isIntersectedBy(tloilp, oom, rm)) {
                        if (ll.isIntersectedBy(lloilp, oom, rm)) {
                            return getGeometry(V3D_LineSegment.getNearestPoint(ls, lloilp, oom, rm), tloilp, oom, rm);
                        } else {
                            return getGeometry(V3D_LineSegment.getNearestPoint(ls, lloilq, oom, rm),
                                    getNearestPoint(tloilq, oom, rm), oom, rm);
                        }
                    } else {
                        if (ll.isIntersectedBy(lloilp, oom, rm)) {
                            return getGeometry(V3D_LineSegment.getNearestPoint(ls, lloilp, oom, rm), tloilq, oom, rm);
                        } else {
                            return getGeometry(V3D_LineSegment.getNearestPoint(ls, lloilq, oom, rm), tloilp, oom, rm);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param pt The point projected onto this.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return A point on {@code this} which is the shortest distance from
     * {@code pt}.
     */
    //@Override Used to override from V3D_Line
    public V3D_Point getPointOfIntersection(V3D_Point pt, int oom, RoundingMode rm) {
        V3D_Point poi = l.getPointOfIntersection(pt, oom, rm);
        if (isIntersectedBy(poi, oom, rm)) {
            return poi;
        } else {
            //return p;
            return l.getP();
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
    public BigDecimal getDistance(V3D_Point pt, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(pt, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
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
     * @param pt The point to get the distance squared from.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance between {@code this} and {@code pt}.
     */
    @Override
    public Math_BigRational getDistanceSquared(V3D_Point pt, int oom, RoundingMode rm) {
        V3D_Point poi = getPointOfIntersection(pt, oom, rm);
        return poi.getDistanceSquared(pt, oom, rm);
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
    public BigDecimal getDistance(V3D_Ray r, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(r, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
//        if (isParallel(r, oom)) {
//            //V3D_Geometry g = getLineOfIntersection(r.p, oom);
//            V3D_Geometry g = getLineOfIntersection(r.getP(oom), oom);
//            if (g instanceof V3D_Point) {
//                return BigDecimal.ZERO;
//            } else {
//                return ((V3D_LineSegment) g).getLength(oom).toBigDecimal(oom);
//            }
//        } else {
//            V3D_Line tl = new V3D_Line(this);
//            //BigDecimal tldr = tl.getDistance(r, oom);
//            //V3D_Geometry g = tl.getLineOfIntersection(r.p, oom);
//            V3D_Geometry g = tl.getLineOfIntersection(r.getP(oom), oom);
//            if (g instanceof V3D_Point) {
//                return BigDecimal.ZERO;
//            } else {
//                V3D_LineSegment tlrp = (V3D_LineSegment) g;
//                V3D_Line rl = new V3D_Line(r);
//                //BigDecimal rldt = rl.getDistance(this, oom);
//                //V3D_Geometry g2 = rl.getLineOfIntersection(p, oom);
//                V3D_Geometry g2 = rl.getLineOfIntersection(getP(oom), oom);
//                if (g2 instanceof V3D_Point) {
//                    return BigDecimal.ZERO;
//                } else {
//                    V3D_LineSegment rltp = (V3D_LineSegment) g2;
//                    //if (isIntersectedBy(tlrp.q, oom)) {
//                    if (isIntersectedBy(tlrp.getQ(oom), oom)) {
//                        BigDecimal tlrpl = tlrp.getLength(oom).toBigDecimal(oom);
//                        //if (r.isIntersectedBy(rltp.q, oom)) {
//                        if (r.isIntersectedBy(rltp.getQ(oom), oom)) {
//                            return tlrpl.min(rltp.getLength(oom).toBigDecimal(oom));
//                        } else {
//                            return tlrpl;
//                        }
//                    } else {
//                        //if (r.isIntersectedBy(rltp.q, oom)) {
//                        if (r.isIntersectedBy(rltp.getQ(oom), oom)) {
//                            return rltp.getLength(oom).toBigDecimal(oom);
//                        } else {
//                            //return p.getDistance(r.p, oom);
//                            return getP(oom).getDistance(r.getP(oom), oom);
//                        }
//                    }
//                }
//            }
//        }
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
    public Math_BigRational getDistanceSquared(V3D_Ray r, int oom, RoundingMode rm) {
        if (l.isParallel(r.l, oom, rm)) {
            //V3D_Geometry g = getLineOfIntersection(r.p, oom, rm);
            V3D_Geometry g = getLineOfIntersection(r.l.getP(), oom, rm);
            if (g instanceof V3D_Point) {
                return Math_BigRational.ZERO;
            } else {
                return ((V3D_LineSegment) g).getLength2(oom, rm);
            }
        } else {
            V3D_Geometry g = l.getLineOfIntersection(r.l.getP(), oom, rm);
            if (g instanceof V3D_Point) {
                return Math_BigRational.ZERO;
            } else {
                V3D_LineSegment gl = (V3D_LineSegment) g;
                V3D_Geometry g2 = r.l.getLineOfIntersection(l.getP(), oom, rm);
                if (g2 instanceof V3D_Point) {
                    return Math_BigRational.ZERO;
                } else {
                    V3D_LineSegment g2l = (V3D_LineSegment) g2;
                    //if (isIntersectedBy(tlrp.q, oom)) {
                    if (isIntersectedBy(gl.getQ(oom, rm), oom, rm)) {
                        Math_BigRational tlrpl = gl.getLength2(oom, rm);
                        //if (r.isIntersectedBy(rltp.q, oom)) {
                        if (r.isIntersectedBy(g2l.getQ(oom, rm), oom, rm)) {
                            return tlrpl.min(g2l.getLength2(oom, rm));
                        } else {
                            return tlrpl;
                        }
                    } else {
                        //if (r.isIntersectedBy(rltp.q, oom)) {
                        if (r.isIntersectedBy(g2l.getQ(oom, rm), oom, rm)) {
                            return g2l.getLength2(oom, rm);
                        } else {
                            //return p.getDistance(r.p, oom);
                            return l.getP().getDistanceSquared(r.l.getP(), oom, rm);
                        }
                    }
                }
            }
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom, RoundingMode rm) {
        V3D_Geometry g = getLineOfIntersection(l, oom, rm);
        if (g == null) {
            return l.getDistanceSquared(this.l.getP(), oom, rm);
        }
        if (g instanceof V3D_Point) {
            return Math_BigRational.ZERO;
        } else {
            return ((V3D_LineSegment) g).getLength2(oom, rm);
        }
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom, RoundingMode rm) {
        V3D_Geometry g = getLineOfIntersection(l, oom, rm);
        if (g == null) {
            return l.getDistanceSquared(this.l.getP(), oom, rm);
        }
        if (g instanceof V3D_Point) {
            return Math_BigRational.ZERO;
        } else {
            return ((V3D_LineSegment) g).getLength2(oom, rm);
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Plane pl, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(pl, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Plane pl, int oom, RoundingMode rm) {
        if (isIntersectedBy(pl, oom, rm)) {
            return Math_BigRational.ZERO;
        } else {
            Math_BigRational ld2 = l.getDistanceSquared(pl, oom, rm);
            if (ld2.compareTo(Math_BigRational.ZERO) == 0) {
                return this.l.getP().getDistanceSquared(pl, oom, rm);
            } else {
                return ld2;
            }
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(t, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom, RoundingMode rm) {
        if (isIntersectedBy(t, oom, rm)) {
            return Math_BigRational.ZERO;
        } else {
            Math_BigRational pqd2 = ((V3D_LineSegment) getLineOfIntersection(t.p.getPQ(), oom, rm)).getLength2(oom, rm);
            Math_BigRational qrd2 = ((V3D_LineSegment) getLineOfIntersection(t.p.getQR(), oom, rm)).getLength2(oom, rm);
            Math_BigRational rpd2 = ((V3D_LineSegment) getLineOfIntersection(t.p.getRP(), oom, rm)).getLength2(oom, rm);
            Math_BigRational ld2 = t.getDistanceSquared(this.l.getP(), oom, rm);
            return Math_BigRational.min(pqd2, qrd2, rpd2, ld2);
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(t, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        if (isIntersectedBy(t, oom, rm)) {
            return Math_BigRational.ZERO;
        } else {
            Math_BigRational pqrd2 = t.getPqr().getDistanceSquared(this, oom, rm);
            Math_BigRational psqd2 = t.getPsq().getDistanceSquared(this, oom, rm);
            Math_BigRational qsrd2 = t.getQsr().getDistanceSquared(this, oom, rm);
            Math_BigRational sprd2 = t.getSpr().getDistanceSquared(this, oom, rm);
            return Math_BigRational.min(pqrd2, psqd2, qsrd2, sprd2);
        }
    }

    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta,
            int oom, RoundingMode rm) {
        this.l.rotate(axisOfRotation, theta, oom, rm);
    }
}
