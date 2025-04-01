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
import java.util.Iterator;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

/**
 *
 * A V3D_TetrahedronDouble comprises 4 corner V3D_Point_d points that are not
 * coplanar or collinear or coincident. There are 4 V3D_Triangle_d faces defined
 * from the outside with the points arranged clockwise. Each face has a
 * V3D_Line_SegmentDouble edge equal to that of another face, but these are
 * stored independently in that the order of the points and the directions of
 * the vectors might be opposite. The points are shared between all the
 * triangles. {@code
 * pl *- - - - - - - - - - - + - - - - - - - - - - -* qv
 *     \ ~                                         /|
 *      \     ~                                   / |
 *       \         ~                             /  |
 *        \             ~                       /   |
 *         \                 ~                 /    |
 *          \                      ~          /     |
 *           \                          ~    /      |
 *            \                             / ~     |
 *             \                           /       ~s
 *              \                         /        :
 *               \                       /
 *                \                     /      :
 *                 \                   /
 *                  \                 /     :
 *                   \               /
 *                    \             /    :
 *                     \           /
 *                      \         /   :
 *                       \       /
 *                        \     /  :
 *                         \   /
 *                          \ /:
 *                           *
 *                           r
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Tetrahedron_d extends V3D_Volume_d {

    private static final long serialVersionUID = 1L;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector_d p;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector_d q;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector_d r;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector_d s;

    /**
     * For storing the pqr triangle of the tetrahedron.
     */
    public V3D_Triangle_d pqr;

    /**
     * For storing the qsr triangle of the tetrahedron.
     */
    public V3D_Triangle_d qsr;

    /**
     * For storing the spr triangle of the tetrahedron.
     */
    public V3D_Triangle_d spr;

    /**
     * For storing the psq triangle of the tetrahedron.
     */
    public V3D_Triangle_d psq;

    /**
     *
     * @param t The tetrahedron to instantiate from/copy/clone.
     */
    public V3D_Tetrahedron_d(V3D_Tetrahedron_d t) {
        super(t.env, new V3D_Vector_d(t.offset));
        p = new V3D_Vector_d(t.p);
        q = new V3D_Vector_d(t.q);
        r = new V3D_Vector_d(t.r);
        s = new V3D_Vector_d(t.s);
        pqr = new V3D_Triangle_d(t.pqr);
        qsr = new V3D_Triangle_d(t.qsr);
        spr = new V3D_Triangle_d(t.spr);
        psq = new V3D_Triangle_d(t.psq);
    }

    /**
     * Create a new instance. {@code pl}, {@code qv}, {@code r} and {@code s}
     * must all be different, not the zero vector and collectively they must be
     * three dimensional. This is generally the fastest way to construct a
     * tetrahedron.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p A point that defines the tetrahedron.
     * @param q A point that defines the tetrahedron.
     * @param r A point that defines the tetrahedron.
     * @param s A point that defines the tetrahedron.
     */
    public V3D_Tetrahedron_d(V3D_Environment_d env, V3D_Vector_d offset, V3D_Vector_d p,
            V3D_Vector_d q, V3D_Vector_d r, V3D_Vector_d s) {
        super(env, offset);
        this.p = new V3D_Vector_d(p);
        this.q = new V3D_Vector_d(q);
        this.r = new V3D_Vector_d(r);
        this.s = new V3D_Vector_d(s);
    }

    /**
     * Create a new instance. {@code pl}, {@code qv}, {@code r} and {@code s}
     * must all be different and not coplanar. No test is done to check these
     * things.
     *
     * @param p Used to set {@link #p} and {@link #offset}.
     * @param q Used to set {@link #q}.
     * @param r Used to set {@link #r}.
     * @param s Used to set {@link #s}.
     */
    public V3D_Tetrahedron_d(V3D_Point_d p, V3D_Point_d q,
            V3D_Point_d r, V3D_Point_d s, double epsilon) {
        super(p.env, p.offset);
        this.p = new V3D_Vector_d(p.rel);
        V3D_Point_d qp = new V3D_Point_d(q);
        qp.setOffset(offset);
        this.q = qp.rel;
        V3D_Point_d rp = new V3D_Point_d(r);
        rp.setOffset(offset);
        this.r = rp.rel;
        V3D_Point_d sp = new V3D_Point_d(s);
        sp.setOffset(offset);
        this.s = sp.rel;

        if (V3D_Plane_d.isCoplanar(epsilon, p, q, r, s)) {
            // The tetrahedron is flat and without volume.
            int debug = 1;
        }
    }

    /**
     * Create a new instance. If {@code p} is coplanar with t then the
     * tetrahedron is flat and without volume.
     *
     * @param p Used to set {@link #p} and {@link #offset}.
     * @param t Used to set {@link #q}, {@link #r} and {@link #s}.
     */
    public V3D_Tetrahedron_d(V3D_Point_d p, V3D_Triangle_d t, double epsilon) {
        this(p, t.getP(), t.getQ(), t.getR(), epsilon);
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
        return pad + this.getClass().getSimpleName() + "\n"
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
        String st = super.toStringFields(pad);
        st += pad + ",\n";
        st += pad + "p=" + p.toString(pad) + "\n";
        st += pad + ",\n";
        st += pad + "q=" + q.toString(pad) + "\n";
        st += pad + ",\n";
        st += pad + "r=" + r.toString(pad) + "\n";
        st += pad + ",\n";
        st += pad + "s=" + s.toString(pad);// + "\n";
        return st;
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFieldsSimple(String pad) {
        String st = pad + "p=" + p.toStringSimple(pad) + ",\n";
        st += pad + "q=" + q.toStringSimple(pad) + ",\n";
        st += pad + "r=" + r.toStringSimple(pad) + ",\n";
        st += pad + "s=" + s.toStringSimple(pad);
        return st;
    }

    @Override
    public V3D_AABB_d getAABB() {
        if (en == null) {
            en = getP().getAABB()
                    .union(getQ().getAABB())
                    .union(getR().getAABB())
                    .union(getS().getAABB());
        }
        return en;
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point_d getP() {
        return new V3D_Point_d(env, offset, p);
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point_d getQ() {
        return new V3D_Point_d(env, offset, q);
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point_d getR() {
        return new V3D_Point_d(env, offset, r);
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point_d getS() {
        return new V3D_Point_d(env, offset, s);
    }

    /**
     * If {@code null} initialise {@link #pqr} and return it.
     *
     * @return {@link #pqr}
     */
    public V3D_Triangle_d getPqr() {
        if (pqr == null) {
            pqr = new V3D_Triangle_d(env, offset, p, q, r);
        }
        return pqr;
    }

    /**
     * If {@code null} initialise {@link #qsr} and return it.
     *
     * @return {@link #qsr}
     */
    public V3D_Triangle_d getQsr() {
        if (qsr == null) {
            qsr = new V3D_Triangle_d(env, offset, q, s, r);
        }
        return qsr;
    }

    /**
     * If {@code null} initialise {@link #spr} and return it.
     *
     * @return {@link #spr}
     */
    public V3D_Triangle_d getSpr() {
        if (spr == null) {
            spr = new V3D_Triangle_d(env, offset, s, p, r);
        }
        return spr;
    }

    /**
     * If {@code null} initialise {@link #psq} and return it.
     *
     * @return {@link #psq}
     */
    public V3D_Triangle_d getPsq() {
        if (psq == null) {
            psq = new V3D_Triangle_d(env, offset, p, s, q);
        }
        return psq;
    }

    /**
     * For calculating and returning the surface area.
     *
     * @return The outer surface area of the tetrahedron (rounded).
     */
    @Override
    public double getArea() {
        return getPqr().getArea() + getQsr().getArea() + getSpr().getArea()
                + getPsq().getArea();
    }

    /**
     * Calculate and return the volume.
     * https://en.wikipedia.org/wiki/Tetrahedron#Volume This implementation is
     * currently a bit rough and ready.
     */
    @Override
    public double getVolume() {
        V3D_Triangle_d tpqr = getPqr();
        V3D_Point_d ts = getS();
        double hd3 = tpqr.pl.getPointOfProjectedIntersection(ts)
                .getDistance(ts) / 3d;
        return tpqr.getArea() * hd3;
    }

    /**
     * Calculate and return the centroid as a point.
     *
     * The original implementation used intersection, but it is simpler to get
     * the average of the x, y and z coordinates from the points at each vertex.
     * https://www.globalspec.com/reference/52702/203279/4-8-the-centroid-of-a-tetrahedron
     *
     * @return The centroid point.
     */
    public V3D_Point_d getCentroid() {
        double dx = (p.dx + q.dx + r.dx + s.dx) / 4d;
        double dy = (p.dy + q.dy + r.dy + s.dy) / 4d;
        double dz = (p.dz + q.dz + r.dz + s.dz) / 4d;
        return new V3D_Point_d(env, offset, new V3D_Vector_d(dx, dy, dz));
    }

    /**
     * Identify if this is intersected by point {@code pt}.
     *
     * @param pt The point to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public boolean intersects(V3D_Point_d pt, double epsilon) {
        pqr = getPqr();
        psq = getPsq();
        spr = getSpr();
        qsr = getQsr();
        if (pqr.pl.isOnSameSide(pt, getS(), epsilon)) {
            if (psq.pl.isOnSameSide(pt, getR(), epsilon)) {
                if (spr.pl.isOnSameSide(pt, getQ(), epsilon)) {
                    if (qsr.pl.isOnSameSide(pt, getP(), epsilon)) {
                        return true;
                    }
                }
            }
        }
        if (qsr.intersects(pt, epsilon)) {
            return true;
        }
        if (spr.intersects(pt, epsilon)) {
            return true;
        }
        if (psq.intersects(pt, epsilon)) {
            return true;
        }
        return pqr.intersects(pt, epsilon);
    }

    /**
     * @param r The ray for which the intersection is returned.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection of this and r.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_Ray_d r,
            double epsilon) {
        V3D_FiniteGeometry_d irl = getIntersect(r.l, epsilon);
        if (irl == null) {
            return null;
        }
        if (irl instanceof V3D_Point_d irlp) {
            if (r.isAligned(irlp, epsilon)) {
                return irl;
            } else {
                return null;
            }
        }
        return ((V3D_LineSegment_d) irl).clip(r.getPl(), r.l.getQ(), epsilon);
    }

    /**
     * Get the intersection between this and the line {@code l}.
     *
     * @param l The line to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_Line_d l,
            double epsilon) {
        V3D_FiniteGeometry_d pqri = getPqr().getIntersect(l, epsilon);
        if (pqri == null) {
            V3D_FiniteGeometry_d psqi = getPsq().getIntersect(l, epsilon);
            if (psqi == null) {
                V3D_FiniteGeometry_d qsri = getQsr().getIntersect(l, epsilon);
                if (qsri == null) {
                    return null;
                }
                if (qsri instanceof V3D_LineSegment_d) {
                    return qsri;
                }
                V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                if (spri == null) {
                    return null;
                }
                return V3D_LineSegment_d.getGeometry((V3D_Point_d) qsri,
                        (V3D_Point_d) spri, epsilon);
            } else {
                if (psqi instanceof V3D_LineSegment_d) {
                    return psqi;
                }
                V3D_FiniteGeometry_d qsri = getQsr().getIntersect(l, epsilon);
                if (qsri == null) {
                    return psqi;
                }
                if (qsri instanceof V3D_LineSegment_d) {
                    return qsri;
                }
                return V3D_LineSegment_d.getGeometry((V3D_Point_d) psqi,
                        (V3D_Point_d) qsri, epsilon);
            }
        } else if (pqri instanceof V3D_Point_d pqrip) {
            V3D_FiniteGeometry_d psqi = getPsq().getIntersect(l, epsilon);
            if (psqi == null) {
                V3D_FiniteGeometry_d qsri = getQsr().getIntersect(l, epsilon);
                if (qsri == null) {
                    return null;
                } else if (qsri instanceof V3D_Point_d qsrip) {
                    V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                    if (spri == null) {
                        return V3D_LineSegment_d.getGeometry(qsrip, pqrip, epsilon);
                    } else if (spri instanceof V3D_Point_d sprip) {
                        return V3D_Triangle_d.getGeometry(qsrip, pqrip, sprip, epsilon);
                    } else {
                        return spri;
                    }
                } else {
                    return qsri;
                }
            } else if (psqi instanceof V3D_Point_d psqip) {
                V3D_FiniteGeometry_d qsri = getQsr().getIntersect(l, epsilon);
                if (qsri == null) {
                    V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                    if (spri == null) {
                        return V3D_LineSegment_d.getGeometry(psqip, pqrip, epsilon);
                    } else if (spri instanceof V3D_Point_d sprip) {
                        return V3D_Triangle_d.getGeometry(psqip, pqrip, sprip, epsilon);
                    } else {
                        return spri;
                    }
                } else if (qsri instanceof V3D_Point_d qsrip) {
                    V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                    if (spri == null) {
                        return V3D_LineSegment_d.getGeometry(psqip, pqrip, epsilon);
                    } else if (spri instanceof V3D_Point_d sprip) {
                        return getGeometry(pqrip, psqip, qsrip, sprip, epsilon);
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
     * If p, q, r and s are equal then the point is returned. If there are just
     * two different points then a line segment is returned. If 3 points are
     * different then a triangle is returned. If 4 points are different then if
     * they are coplanar, a V3D_Triangle_dCoplanar is returned, otherwise a
     * tetrahedron is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @param s Another possibly equal point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return either {@code pl} or {@code new V3D_LineSegment_d(pl, qv)} or
     * {@code new V3D_Triangle_d(pl, qv, r)}
     */
    public static V3D_FiniteGeometry_d getGeometry(V3D_Point_d p,
            V3D_Point_d q, V3D_Point_d r, V3D_Point_d s,
            double epsilon) {
        if (p.equals(q)) {
            return V3D_Triangle_d.getGeometry(p, r, s, epsilon);
        } else if (p.equals(r)) {
            return V3D_Triangle_d.getGeometry(p, q, s, epsilon);
        } else if (p.equals(s)) {
            return V3D_Triangle_d.getGeometry(p, q, r, epsilon);
        } else if (q.equals(r)) {
            return V3D_Triangle_d.getGeometry(p, q, s, epsilon);
        } else if (q.equals(s)) {
            return V3D_Triangle_d.getGeometry(p, q, r, epsilon);
        } else if (r.equals(s)) {
            return V3D_Triangle_d.getGeometry(p, q, r, epsilon);
        } else {
            if (V3D_Plane_d.isCoplanar(epsilon, p, q, r, s)) {
                if (V3D_Line_d.isCollinear(epsilon, p, q, r, s)) {
                    //return V3D_Line(pl, qv, r, s);
                    throw new UnsupportedOperationException("Need code to construct a line segment from 4 points!");
                }
                V3D_LineSegment_d pq = new V3D_LineSegment_d(p, q);
                V3D_LineSegment_d rs = new V3D_LineSegment_d(r, s);
                if (pq.getIntersect(rs, epsilon) != null) {
                    return null;
//                    return new V3D_Polygon_d(
//                            new V3D_Triangle_d(p, q, r),
//                            new V3D_Triangle_d(p, q, s));
                } else {
                    V3D_LineSegment_d pr = new V3D_LineSegment_d(p, r);
                    V3D_LineSegment_d qs = new V3D_LineSegment_d(q, s);
                    if (pr.getIntersect(qs, epsilon) != null) {
                        return null;
//                        return new V3D_Polygon_d(
//                                new V3D_Triangle_d(p, r, q),
//                                new V3D_Triangle_d(p, r, s));
                    } else {
                        return null;
//                        return new V3D_Polygon_d(
//                                new V3D_Triangle_d(p, q, s),
//                                new V3D_Triangle_d(p, r, s));
                    }
                }
            }
            return new V3D_Tetrahedron_d(p, q, r, s, epsilon);
        }
    }

    /**
     * At least two of the points input are expected to be the same. If all
     * points are equal then the point is returned. If there are just two
     * different points then a line segment is returned. If 3 points are
     * different then a triangle is returned. If 4 points are different then if
     * they are coplanar, a V3D_Triangle_dCoplanar is returned, otherwise a
     * tetrahedron is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @param s Another possibly equal point.
     * @param t Another possibly equal point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return either {@code pl} or {@code new V3D_LineSegment_d(pl, qv)} or
     * {@code new V3D_Triangle_d(pl, qv, r)}
     */
    protected static V3D_FiniteGeometry_d getGeometry(V3D_Point_d p,
            V3D_Point_d q, V3D_Point_d r, V3D_Point_d s,
            V3D_Point_d t, double epsilon) {
        if (p.equals(q)) {
            return getGeometry(p, r, s, t, epsilon);
        } else if (p.equals(r)) {
            return getGeometry(p, q, s, t, epsilon);
        } else if (p.equals(s)) {
            return getGeometry(p, q, r, t, epsilon);
        } else if (p.equals(t)) {
            return getGeometry(p, q, r, s, epsilon);
        } else if (q.equals(r)) {
            return getGeometry(p, q, s, t, epsilon);
        } else if (q.equals(s)) {
            return getGeometry(p, q, r, t, epsilon);
        } else if (q.equals(t)) {
            return getGeometry(p, q, r, s, epsilon);
        } else if (r.equals(s)) {
            return getGeometry(p, q, r, t, epsilon);
        } else if (r.equals(t)) {
            return getGeometry(p, q, r, s, epsilon);
        } else {
            return getGeometry(p, q, r, t, epsilon);
        }
    }

    /**
     * Get the intersection between this and the line segment {@code l}.
     *
     * @param l The line segment to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_LineSegment_d l,
            double epsilon) {
        V3D_FiniteGeometry_d g = getIntersect(l.l, epsilon);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_Point_d gp) {
                if (intersects(gp, epsilon)) {
                    return g;
                } else {
                    return null;
                }
            } else {
                V3D_Point_d lp = l.getP();
                V3D_Point_d lq = l.getQ();
                if (intersects(lp, epsilon)) {
                    if (intersects(lq, epsilon)) {
                        return ((V3D_LineSegment_d) g).getIntersect(l, epsilon);
                    } else {
                        V3D_FiniteGeometry_d pqri = getPqr().getIntersect(l, epsilon);
                        if (pqri == null) {
                            V3D_FiniteGeometry_d psqi = getPsq().getIntersect(l, epsilon);
                            if (psqi == null) {
                                V3D_FiniteGeometry_d qsri = getQsr().getIntersect(l, epsilon);
                                if (qsri == null) {
                                    return new V3D_LineSegment_d(lp,
                                            (V3D_Point_d) getSpr().getIntersect(l, epsilon));
                                } else {
                                    if (qsri instanceof V3D_Point_d qsrip) {
                                        V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                                        if (spri == null) {
                                            return new V3D_LineSegment_d(lp, qsrip);
                                        } else {
                                            return V3D_Triangle_d.getGeometry(lp, qsrip, (V3D_Point_d) spri, epsilon);
                                        }
                                    } else {
                                        return qsri;
                                    }
                                }
                            } else {
                                if (psqi instanceof V3D_Point_d psqip) {
                                    V3D_FiniteGeometry_d qsri = getQsr().getIntersect(l, epsilon);
                                    if (qsri == null) {
                                        V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                                        if (spri == null) {
                                            return new V3D_LineSegment_d(lp, psqip);
                                        } else {
                                            if (spri instanceof V3D_Point_d sprip) {
                                                return V3D_Triangle_d.getGeometry(lp, psqip, sprip, epsilon);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        if (qsri instanceof V3D_Point_d qsrip) {
                                            V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                                            if (spri == null) {
                                                return V3D_Triangle_d.getGeometry(lp, psqip, qsrip, epsilon);
                                            } else {
                                                if (spri instanceof V3D_Point_d sprip) {
                                                    return getGeometry(lp, psqip, qsrip, sprip, epsilon);
                                                } else {
                                                    return spri;
                                                }
                                            }
                                        } else {
                                            return qsri;
                                        }
                                    }
                                } else {
                                    return psqi;
                                }
                            }
                        } else {
                            if (pqri instanceof V3D_Point_d pqrip) {
                                V3D_FiniteGeometry_d psqi = getPsq().getIntersect(l, epsilon);
                                if (psqi == null) {
                                    V3D_FiniteGeometry_d qsri = getQsr().getIntersect(l, epsilon);
                                    if (qsri == null) {
                                        V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                                        if (spri == null) {
                                            return new V3D_LineSegment_d(lp, pqrip);
                                        } else {
                                            if (spri instanceof V3D_Point_d sprip) {
                                                return V3D_Triangle_d.getGeometry(lp, pqrip, sprip, epsilon);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        if (qsri instanceof V3D_Point_d qsrip) {
                                            V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                                            if (spri == null) {
                                                return V3D_Triangle_d.getGeometry(lp, pqrip, qsrip, epsilon);
                                            } else {
                                                if (spri instanceof V3D_Point_d sprip) {
                                                    return getGeometry(lp, pqrip, qsrip, sprip, epsilon);
                                                } else {
                                                    return spri;
                                                }
                                            }
                                        } else {
                                            return qsri;
                                        }
                                    }
                                } else {
                                    if (psqi instanceof V3D_Point_d psqip) {
                                        V3D_FiniteGeometry_d qsri = getQsr().getIntersect(l, epsilon);
                                        if (qsri == null) {
                                            V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                                            if (spri == null) {
                                                return V3D_Triangle_d.getGeometry(lp, pqrip, psqip, epsilon);
                                            } else {
                                                if (spri instanceof V3D_Point_d sprip) {
                                                    return getGeometry(lp, pqrip, psqip, sprip, epsilon);
                                                } else {
                                                    return spri;
                                                }
                                            }
                                        } else {
                                            if (qsri instanceof V3D_Point_d qsrip) {
                                                V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                                                if (spri == null) {
                                                    return getGeometry(lp, psqip, pqrip, qsrip, epsilon);
                                                } else {
                                                    if (spri instanceof V3D_Point_d sprip) {
                                                        return getGeometry(lp, psqip, pqrip, qsrip, sprip, epsilon);
                                                    } else {
                                                        return spri;
                                                    }
                                                }
                                            } else {
                                                return qsri;
                                            }
                                        }
                                    } else {
                                        return psqi;
                                    }
                                }
                            } else {
                                return pqri;
                            }
                        }
                    }
                } else {
                    V3D_FiniteGeometry_d pqri = getPqr().getIntersect(l, epsilon);
                    if (pqri == null) {
                        V3D_FiniteGeometry_d psqi = getPsq().getIntersect(l, epsilon);
                        if (psqi == null) {
                            V3D_FiniteGeometry_d qsri = getQsr().getIntersect(l, epsilon);
                            if (qsri == null) {
                                return new V3D_LineSegment_d(lq,
                                        (V3D_Point_d) getSpr().getIntersect(l, epsilon));
                            } else {
                                if (qsri instanceof V3D_Point_d qsrip) {
                                    V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                                    if (spri == null) {
                                        return new V3D_LineSegment_d(lq, qsrip);
                                    } else {
                                        return V3D_Triangle_d.getGeometry(lq, qsrip, (V3D_Point_d) spri, epsilon);
                                    }
                                } else {
                                    return qsri;
                                }
                            }
                        } else {
                            if (psqi instanceof V3D_Point_d psqip) {
                                V3D_FiniteGeometry_d qsri = getQsr().getIntersect(l, epsilon);
                                if (qsri == null) {
                                    V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                                    if (spri == null) {
                                        return new V3D_LineSegment_d(lq, psqip);
                                    } else {
                                        if (spri instanceof V3D_Point_d sprip) {
                                            return V3D_Triangle_d.getGeometry(lq, psqip, sprip, epsilon);
                                        } else {
                                            return spri;
                                        }
                                    }
                                } else {
                                    if (qsri instanceof V3D_Point_d qsrip) {
                                        V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                                        if (spri == null) {
                                            return V3D_Triangle_d.getGeometry(lq, psqip, qsrip, epsilon);
                                        } else {
                                            if (spri instanceof V3D_Point_d sprip) {
                                                return getGeometry(lq, psqip, qsrip, sprip, epsilon);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        return qsri;
                                    }
                                }
                            } else {
                                return psqi;
                            }
                        }
                    } else {
                        if (pqri instanceof V3D_Point_d pqrip) {
                            V3D_FiniteGeometry_d psqi = getPsq().getIntersect(l, epsilon);
                            if (psqi == null) {
                                V3D_FiniteGeometry_d qsri = getQsr().getIntersect(l, epsilon);
                                if (qsri == null) {
                                    V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                                    if (spri == null) {
                                        return new V3D_LineSegment_d(lq, pqrip);
                                    } else {
                                        if (spri instanceof V3D_Point_d sprip) {
                                            return V3D_Triangle_d.getGeometry(lq, pqrip, sprip, epsilon);
                                        } else {
                                            return spri;
                                        }
                                    }
                                } else {
                                    if (qsri instanceof V3D_Point_d qsrip) {
                                        V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                                        if (spri == null) {
                                            return V3D_Triangle_d.getGeometry(lq, pqrip, qsrip, epsilon);
                                        } else {
                                            if (spri instanceof V3D_Point_d sprip) {
                                                return getGeometry(lq, pqrip, qsrip, sprip, epsilon);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        return qsri;
                                    }
                                }
                            } else {
                                if (psqi instanceof V3D_Point_d psqip) {
                                    V3D_FiniteGeometry_d qsri = getQsr().getIntersect(l, epsilon);
                                    if (qsri == null) {
                                        V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                                        if (spri == null) {
                                            return V3D_Triangle_d.getGeometry(lq, pqrip, psqip, epsilon);
                                        } else {
                                            if (spri instanceof V3D_Point_d sprip) {
                                                return getGeometry(lq, pqrip, psqip, sprip, epsilon);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        if (qsri instanceof V3D_Point_d qsrip) {
                                            V3D_FiniteGeometry_d spri = getSpr().getIntersect(l, epsilon);
                                            if (spri == null) {
                                                return getGeometry(lq, psqip, pqrip, qsrip, epsilon);
                                            } else {
                                                if (spri instanceof V3D_Point_d sprip) {
                                                    return getGeometry(lq, psqip, pqrip, qsrip, sprip, epsilon);
                                                } else {
                                                    return spri;
                                                }
                                            }
                                        } else {
                                            return qsri;
                                        }
                                    }
                                } else {
                                    return psqi;
                                }
                            }
                        } else {
                            return pqri;
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the intersection between the geometry and the plane {@code p}. The
     * intersection will be null, a point, a line segment, a triangle or a
     * quadrilateral. It should be that any points of intersection are within
     * this.
     *
     * @param pl The plane to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_Plane_d pl,
            double epsilon) {
        V3D_FiniteGeometry_d pqri = getPqr().getIntersect(pl, epsilon);
        if (pqri == null) {
            V3D_FiniteGeometry_d psqi = getPsq().getIntersect(pl, epsilon);
            if (psqi == null) {
                return null;
            } else if (psqi instanceof V3D_Point_d psqip) {
                // psqip must be the point s!
                return psqip;
            } else if (psqi instanceof V3D_LineSegment_d psqil) {
                V3D_FiniteGeometry_d qsri = getQsr().getIntersect(pl, epsilon);
                V3D_FiniteGeometry_d spri = getSpr().getIntersect(pl, epsilon);
                if (qsri == null) {
                    if (spri == null) {
                        return psqil;
                    } else if (spri instanceof V3D_LineSegment_d spril) {
                        return V3D_Triangle_d.getGeometry(pl, psqil, spril, epsilon);
                    } else {
                        return V3D_Triangle_d.getGeometry(psqil, (V3D_Point_d) spri, epsilon);
                    }
                } else if (qsri instanceof V3D_LineSegment_d qsril) {
                    if (spri instanceof V3D_LineSegment_d spril) {
                        return getGeometry(pl, psqil, qsril, spril, epsilon);
                    } else {
                        return V3D_Triangle_d.getGeometry(psqil, qsril, (V3D_Point_d) spri, epsilon);
                    }
                } else {
                    if (spri instanceof V3D_LineSegment_d spril) {
                        return V3D_Triangle_d.getGeometry(psqil, spril, (V3D_Point_d) qsri, epsilon);
                    } else {
                        return V3D_Triangle_d.getGeometry(pl, psqil, (V3D_Point_d) qsri, (V3D_Point_d) spri, epsilon);
                    }
                }
            } else {
                // Triangle
                return (V3D_Triangle_d) psqi;
            }
        } else if (pqri instanceof V3D_Point_d pqrip) {
            V3D_FiniteGeometry_d psqi = getPsq().getIntersect(pl, epsilon);
            if (psqi == null) {
                return pqrip;
            } else if (psqi instanceof V3D_Point_d psqip) {
                V3D_FiniteGeometry_d qsri = getQsr().getIntersect(pl, epsilon);
                if (qsri == null) {
                    return psqip;
                } else {
                    if (qsri instanceof V3D_Point_d qsrip) {
                        return V3D_LineSegment_d.getGeometry(qsrip, psqip, epsilon);
                    } else {
                        return V3D_Triangle_d.getGeometry(
                                (V3D_LineSegment_d) qsri, psqip, epsilon);
                    }
                }
            } else if (psqi instanceof V3D_LineSegment_d psqil) {
                V3D_FiniteGeometry_d qsri = getQsr().getIntersect(pl, epsilon);
                if (qsri == null) {
                    throw new RuntimeException("Paradox A");
                } else if (qsri instanceof V3D_Point_d) {
                    return psqil;
                } else {
                    V3D_LineSegment_d qsril = (V3D_LineSegment_d) qsri;
                    // The result is a triangle
                    return V3D_ConvexArea_d.getGeometry(epsilon,
                            psqil.getP(), psqil.getQ(), qsril.getP(), qsril.getQ());
                }
            } else {
                // Triangle
                return psqi;
            }
        } else if (pqri instanceof V3D_LineSegment_d pqril) {
            V3D_FiniteGeometry_d psqi = getPsq().getIntersect(pl, epsilon);
            if (psqi == null) {
                V3D_FiniteGeometry_d qsri = getQsr().getIntersect(pl, epsilon);
                if (qsri == null) {

                    // debugging code
                    getPqr().getIntersect(pl, epsilon);
                    getPsq().getIntersect(pl, epsilon);
                    getQsr().getIntersect(pl, epsilon);
                    getSpr().getIntersect(pl, epsilon);
                    //V3D_Plane_d.isCoplanar(epsilon, getP(), getQ(), getR(), getS());

                    return pqri;
                    //throw new RuntimeException("Paradox B:" + this.toString() + pl.toString());
                } else if (qsri instanceof V3D_Point_d qsrip) {

                    // debugging code
                    getPqr().getIntersect(pl, epsilon);
                    getPsq().getIntersect(pl, epsilon);
                    getQsr().getIntersect(pl, epsilon);
                    getSpr().getIntersect(pl, epsilon);
                    //V3D_Plane_d.isCoplanar(epsilon, getP(), getQ(), getR(), getS());

                    return V3D_Triangle_d.getGeometry(pqril, qsrip, epsilon);
                    //throw new RuntimeException("Paradox C");
                } else if (qsri instanceof V3D_LineSegment_d qsril) {
                    // The result is a triangle
                    return V3D_ConvexArea_d.getGeometry(epsilon,
                            pqril.getP(), pqril.getQ(), qsril.getP(), qsril.getQ());
                } else {

                    // debugging code
                    getPqr().getIntersect(pl, epsilon);
                    getPsq().getIntersect(pl, epsilon);
                    getQsr().getIntersect(pl, epsilon);
                    getSpr().getIntersect(pl, epsilon);
                    //V3D_Plane_d.isCoplanar(epsilon, getP(), getQ(), getR(), getS());

                    return qsri;
                    //throw new RuntimeException("Paradox D");
                }
            } else if (psqi instanceof V3D_Point_d psqip) {
                V3D_FiniteGeometry_d qsri = getQsr().getIntersect(pl, epsilon);
                if (qsri == null) {

                    // debugging code
                    getPqr().getIntersect(pl, epsilon);
                    getPsq().getIntersect(pl, epsilon);
                    getQsr().getIntersect(pl, epsilon);
                    getSpr().getIntersect(pl, epsilon);
                    //V3D_Plane_d.isCoplanar(epsilon, getP(), getQ(), getR(), getS());

                    return V3D_ConvexArea_d.getGeometry(epsilon,
                            pqril.getP(), pqril.getQ(), psqip);
                    //throw new RuntimeException("Paradox E");
                } else if (qsri instanceof V3D_Point_d qsrip) {
                    // Triangle
                    return V3D_ConvexArea_d.getGeometry(epsilon,
                            pqril.getP(), pqril.getQ(), qsrip, psqip);
                } else if (qsri instanceof V3D_LineSegment_d qsril) {
                    // Triangle
                    return V3D_ConvexArea_d.getGeometry(epsilon,
                            pqril.getP(), pqril.getQ(), qsril.getP(),
                            qsril.getQ(), psqip);
                } else {

                    // debugging code
                    getPqr().getIntersect(pl, epsilon);
                    getPsq().getIntersect(pl, epsilon);
                    getQsr().getIntersect(pl, epsilon);
                    getSpr().getIntersect(pl, epsilon);
                    //V3D_Plane_d.isCoplanar(epsilon, getP(), getQ(), getR(), getS());

                    return qsri;
                    //throw new RuntimeException("Paradox F");
                }
            } else if (psqi instanceof V3D_LineSegment_d psqil) {
                //return V3D_Triangle_d.getGeometry2(psqil, pqril, epsilon);
                return V3D_ConvexArea_d.getGeometry(epsilon,
                        psqil.getP(), psqil.getQ(), pqril.getP(), pqril.getQ());
            } else {
                // Triangle
                return psqi;
            }
        } else {
            // Triangle
            return pqri;
        }
    }

    /**
     * Useful in calculating the intersection of a tetrahedron and a plane. If
     * ab, cd and ef are equal then the line segment is returned. If there are 3
     * unique points then a triangle is returned. If there are 4 or more unique
     * points, then a V3D_ConvexArea_d is returned.
     *
     * @param pl For the normal of any constructed V3D_ConvexArea_d.
     * @param ab A line segment.
     * @param cd A line segment.
     * @param ef A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A line segment, triangle, or coplanar convex hull.
     */
    protected static V3D_FiniteGeometry_d getGeometry(V3D_Plane_d pl,
            V3D_LineSegment_d ab, V3D_LineSegment_d cd,
            V3D_LineSegment_d ef, double epsilon) {
        ArrayList<V3D_Point_d> points;
        {
            HashMap<Integer, V3D_Point_d> pts = new HashMap<>(6);
            if (ab != null) {
                pts.put(0, ab.getP());
                pts.put(1, ab.getQ());
            }
            if (cd != null) {
                pts.put(2, cd.getP());
                pts.put(3, cd.getQ());
            }
            if (ef != null) {
                pts.put(4, ef.getP());
                pts.put(5, ef.getQ());
            }
            points = V3D_Point_d.getUnique(pts, epsilon);
        }
        int n = points.size();
        switch (n) {
            case 2 -> {
                return ab;
            }
            case 3 -> {
                Iterator<V3D_Point_d> ite = points.iterator();
                return V3D_Triangle_d.getGeometry(ite.next(), ite.next(), ite.next(), epsilon);
            }
            default -> {
                V3D_Point_d[] pts = new V3D_Point_d[points.size()];
                int i = 0;
                for (var p : points) {
                    pts[i] = p;
                    i++;
                }
                return new V3D_ConvexArea_d(epsilon, pl.n, pts);
            }

        }
    }

    /**
     * Get the intersection between this and the triangle {@code t}. The
     * intersection will be null, a point, a line segment, a triangle,
     * quadrilateral, pentagon or a hexagon.
     *
     * @param t The triangle to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_Triangle_d t,
            double epsilon) {
        V3D_FiniteGeometry_d i = getIntersect(t.pl, epsilon);
        if (i == null) {
            return null;
        } else if (i instanceof V3D_Point_d ip) {
            if (t.intersects00(ip, epsilon)) {
                //if (t.intersects(pip)) {
                return i;
            } else {
                return null;
            }
        } else if (i instanceof V3D_LineSegment_d il) {
            /**
             * Need to get the intersections of pil and the plane edges of the
             * triangle.
             */
            V3D_Vector_d n = t.pl.n;
            V3D_Plane_d lp = new V3D_Plane_d(env, offset, t.p, t.q, t.p.add(n));
            V3D_FiniteGeometry_d lpiil = lp.getIntersect(il, epsilon);
            if (lpiil == null) {
                V3D_Plane_d lq = new V3D_Plane_d(env, offset, t.q, t.r, t.q.add(n));
                V3D_FiniteGeometry_d lqiil = lq.getIntersect(il, epsilon);
                if (lqiil == null) {
                    V3D_Plane_d lr = new V3D_Plane_d(env, offset, t.r, t.p, t.r.add(n));
                    V3D_FiniteGeometry_d lriil = lr.getIntersect(il, epsilon);
                    if (lriil == null) {
                        return il;
                    } else if (lriil instanceof V3D_Point_d lriilp) {
                        // Find the other point and return the line segment.
                        V3D_Point_d pq = t.getQ();
                        if (lr.isOnSameSide(lriilp, pq, epsilon)) {
                            return new V3D_LineSegment_d(lriilp, pq);
                        } else {
                            return new V3D_LineSegment_d(lriilp, t.getP());
                        }
                    } else {
                        // Return the line segment.
                        return (V3D_LineSegment_d) lriil;
                    }
                } else if (lqiil instanceof V3D_Point_d lqiilp) {
                    // Find the other point and return the linesegment.
                    V3D_Plane_d lr = new V3D_Plane_d(env, offset, t.r, t.p, t.r.add(n));
                    V3D_FiniteGeometry_d lriil = lr.getIntersect(il, epsilon);
                    if (lriil == null) {
                        // For the points on the right side (if any)
                        V3D_Point_d ilp = il.getP();
                        V3D_Point_d ilq = il.getQ();
                        V3D_Point_d tpp = t.getP();
                        if (lq.isOnSameSide(ilp, tpp, epsilon)) {
                            if (lq.isOnSameSide(ilq, tpp, epsilon)) {
                                if (lqiilp.getDistanceSquared(ilp)
                                        < lqiilp.getDistanceSquared(ilq)) {
                                    return new V3D_LineSegment_d(lqiilp, ilq);
                                } else {
                                    return new V3D_LineSegment_d(lqiilp, ilp);
                                }
                            } else {
                                return new V3D_LineSegment_d(lqiilp, ilp);
                            }
                        } else {
                            //if (lq.isOnSameSide(pilq, tpp)) {
                            return new V3D_LineSegment_d(lqiilp, ilq);
                            //} else {
                            //    // This should not happen!
                            //}
                        }
                    } else if (lriil instanceof V3D_Point_d lriilp) {
                        if (lqiilp.equals(lriilp)) {
                            // Find the other point and return the line segment.
                            V3D_Point_d ilp = il.getP();
                            V3D_Point_d ilq = il.getQ();
                            if (lq.isOnSameSide(ilp, ilq, epsilon)) {
                                if (lqiilp.getDistanceSquared(ilp)
                                        < lqiilp.getDistanceSquared(ilq)) {
                                    return new V3D_LineSegment_d(lqiilp, ilq);
                                } else {
                                    return new V3D_LineSegment_d(lqiilp, ilp);
                                }
                            } else {
                                return new V3D_LineSegment_d(lqiilp, ilp);
                            }
                        } else {
                            return new V3D_LineSegment_d(lriilp, lqiilp);
                        }
                    } else {
                        // Return the line segment.
                        return (V3D_LineSegment_d) lriil;
                    }
                } else {
                    // Return the line segment.
                    return (V3D_LineSegment_d) lqiil;
                }
            } else if (lpiil instanceof V3D_Point_d lpiilp) {
                // Find the other point and return the linesegment.
                V3D_Plane_d lq = new V3D_Plane_d(env, offset, t.q, t.r, t.q.add(n));
                V3D_FiniteGeometry_d lqiil = lq.getIntersect(il, epsilon);
                if (lqiil == null) {
                    V3D_Plane_d lr = new V3D_Plane_d(env, offset, t.r, t.p, t.r.add(n));
                    V3D_FiniteGeometry_d lriil = lr.getIntersect(il, epsilon);
                    if (lriil == null) {
                        // Find the other point and return the line segment.
                        V3D_Point_d ilp = il.getP();
                        V3D_Point_d ilq = il.getQ();
                        if (lq.isOnSameSide(ilp, ilq, epsilon)) {
                            if (lpiilp.getDistanceSquared(ilp)
                                    < lpiilp.getDistanceSquared(ilq)) {
                                return new V3D_LineSegment_d(lpiilp, ilq);
                            } else {
                                return new V3D_LineSegment_d(lpiilp, ilp);
                            }
                        } else {
                            return new V3D_LineSegment_d(lpiilp, ilp);
                        }
                    } else if (lriil instanceof V3D_Point_d lriilp) {
                        if (lpiilp.equals(lriilp)) {
                            // Find the other point and return the line segment.
                            V3D_Point_d ilp = il.getP();
                            V3D_Point_d ilq = il.getQ();
                            if (lq.isOnSameSide(ilp, ilq, epsilon)) {
                                if (lpiilp.getDistanceSquared(ilp)
                                        < lpiilp.getDistanceSquared(ilq)) {
                                    return new V3D_LineSegment_d(lpiilp, ilq);
                                } else {
                                    return new V3D_LineSegment_d(lpiilp, ilp);
                                }
                            } else {
                                if (lp.isOnSameSide(t.getR(), ilp, epsilon)) {
                                    return new V3D_LineSegment_d(lpiilp, ilp);
                                } else {
                                    return new V3D_LineSegment_d(lpiilp, ilq);
                                }
                            }
                        } else {
                            return new V3D_LineSegment_d(lpiilp, lriilp);
                        }
                    } else {
                        // Return the line segment.
                        return (V3D_LineSegment_d) lriil;
                    }
                } else if (lqiil instanceof V3D_Point_d lqiilp) {
                    // Find the other point and return the linesegment.
                    V3D_Plane_d lr = new V3D_Plane_d(env, offset, t.r, t.p, t.r.add(n));
                    V3D_FiniteGeometry_d lriil = lr.getIntersect(il, epsilon);
                    if (lriil == null) {
                        // For the points on the right side (if any)
                        V3D_Point_d pilp = il.getP();
                        V3D_Point_d pilq = il.getQ();
                        if (lq.isOnSameSide(pilp, pilq, epsilon)) {
                            if (lqiilp.getDistanceSquared(pilp)
                                    < lqiilp.getDistanceSquared(pilq)) {
                                return new V3D_LineSegment_d(lqiilp, pilq);
                            } else {
                                return new V3D_LineSegment_d(lqiilp, pilp);
                            }
                        } else {
                            if (lq.isOnSameSide(pilp, t.getP(), epsilon)) {
                                return new V3D_LineSegment_d(lqiilp, pilp);
                            } else {
                                return new V3D_LineSegment_d(lqiilp, pilq);
                            }
                        }
                    } else if (lriil instanceof V3D_Point_d lriilp) {
                        if (lriilp.equals(lpiilp)) {
                            return new V3D_LineSegment_d(lriilp, lqiilp);
                        } else {
                            return new V3D_LineSegment_d(lriilp, lpiilp);
                        }
                    } else {
                        // Return the line segment.
                        return (V3D_LineSegment_d) lriil;
                    }
                } else {
                    // Return the line segment.
                    return (V3D_LineSegment_d) lqiil;
                }
            } else {
                // Return the line segment.
                return (V3D_LineSegment_d) lpiil;
            }
            //return t.getIntersect(pil);
        } else if (i instanceof V3D_Triangle_d it) {
            //it.getIntersect(t);
            return it.clip(t, t.getCentroid(), epsilon);
//                //return it.getIntersect(t, oom); // This does not work due to precision issues.
//                /**
//                 * If all the points of t are within the planes of it, then
//                 * return t. If any edges of t intersect the planes of it, then further
//                 * intersections are needed to derive the final shape. Otherwise
//                 * return it.
//                 */
//                V3D_Point_d tp = t.pl.getP();
//                V3D_Point_d tq = t.pl.getQ();
//                V3D_Point_d tr = t.pl.getR();
//                V3D_Point_d itp = it.pl.getP();
//                V3D_Point_d itq = it.pl.getQ();
//                V3D_Point_d itr = it.pl.getR();
//                V3D_Vector_d itn = it.pl.getN(oom);
//                V3D_Point_d itpp = new V3D_Point_d(e, itp.offset.add(itn, oom), itp.rel);
//                V3D_Plane_d itppl = new V3D_Plane_d(itp, itq, itpp);
//                V3D_Point_d itqp = new V3D_Point_d(e, itq.offset.add(itn, oom), itq.rel);
//                V3D_Plane_d itqpl = new V3D_Plane_d(itq, itr, itqp);
//                V3D_Point_d itrp = new V3D_Point_d(e, itr.offset.add(itn, oom), itr.rel);
//                V3D_Plane_d itrpl = new V3D_Plane_d(itr, itp, itrp);
//                V3D_FiniteGeometry_d itc = it.clip(itppl, tr, oom);
//                if (itc == null) {
//                    return null;
//                } else if (itc instanceof V3D_Point_d) {
//                    return itc;
//                } else if (itc instanceof V3D_LineSegment_d) {
//                    
//                } else if (itc instanceof V3D_Triangle_d) {
//                    
//                } else {
//                    // itc instanceof V3D_ConvexArea_d
//                
//                }
//                // There are 512 cases to deal with!
//                if (itppl.isOnSameSide(tp, itr, oom)) {
//                    if (itppl.isOnSameSide(tq, itr, oom)) {
//                        if (itppl.isOnSameSide(tr, itr, oom)) {
//                            if (itqpl.isOnSameSide(tp, itp, oom)) {
//                                if (itqpl.isOnSameSide(tq, itp, oom)) {
//                                    if (itqpl.isOnSameSide(tr, itp, oom)) {
//                                        if (itqpl.isOnSameSide(tp, itp, oom)) {
//                                            if (itqpl.isOnSameSide(tq, itp, oom)) {
//                                                if (itqpl.isOnSameSide(tr, itp, oom)) {
//                                                    return t;
//                                                } else {
//                                                    V3D_LineSegment_d ititpq = (V3D_LineSegment_d) it.getIntersect(t.pl.getPQ(), oom);
//                                                    return V3D_Triangle_d.getGeometry(ititpq, t.pl.getQR());
//                                                }
//                                            } else {
//                                                if (itqpl.isOnSameSide(tr, itp, oom)) {

          
            ////                                                    V3D_LineSegment_d ititpq = (V3D_LineSegment_d) it.getIntersect(t.pl.getPQ(), oom);
////                                                    return V3D_Triangle_d.getGeometry(ititpq, t.pl.getQR());
//                                                    //?
//                                                } else {
//                                                    //?
//                                                }
//                                            }
//                                        } else {
//                                        }
//                                    } else {
//                                    }
//                                } else {
//                                }
//                            } else {
//                            }
//                        } else {
//                        }
//                    } else {
//                    }
//                } else {
//                    if (itppl.isOnSameSide(tq, itr, oom)) {
//                        if (itppl.isOnSameSide(tr, itr, oom)) {
//                            if (itqpl.isOnSameSide(tp, itp, oom)) {
//                                if (itqpl.isOnSameSide(tq, itp, oom)) {
//                                    if (itqpl.isOnSameSide(tr, itp, oom)) {
//                                        if (itqpl.isOnSameSide(tp, itp, oom)) {
//                                            if (itqpl.isOnSameSide(tq, itp, oom)) {
//                                                if (itqpl.isOnSameSide(tr, itp, oom)) {
//                                                    return t;
//                                                } else {
//                                                    V3D_LineSegment_d ititpq = (V3D_LineSegment_d) it.getIntersect(t.pl.getPQ(), oom);
//                                                    return V3D_Triangle_d.getGeometry(ititpq, t.pl.getQR());
//                                                }
//                                            } else {
//                                                if (itqpl.isOnSameSide(tr, itp, oom)) {
////                                                    V3D_LineSegment_d ititpq = (V3D_LineSegment_d) it.getIntersect(t.pl.getPQ(), oom);
////                                                    return V3D_Triangle_d.getGeometry(ititpq, t.pl.getQR());
//                                                    //?
//                                                } else {
//                                                    //?
//                                                }
//                                            }
//                                        } else {
//                                        }
//                                    } else {
//                                    }
//                                } else {
//                                }
//                            } else {
//                            }
//                        } else {
//                        }
//                    } else {
//                        //...  return it;
//                    }
//                }
//                throw new UnsupportedOperationException();
        } else {
            /**
             * Quadrilateral.
             */
            V3D_ConvexArea_d ic = (V3D_ConvexArea_d) i;
            return ic.clip(t, t.getCentroid(), epsilon);
//                /**
//                 * If all the points of t are within the planes of ic, then
//                 * return t. If any of lines of the t intersect ic, then further
//                 * intersections are needed to derive the final shape.
//                 */
//                throw new UnsupportedOperationException();

        
    

    ////                if () {
////
////                } else {
////                    //... return ic;
////                }
        }
    }

    /**
     * Get the intersection with the rectangle {@code r}.
     *
     * @param r The rectangle to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_FiniteGeometry_d.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_Rectangle_d r, double epsilon) {
        V3D_FiniteGeometry_d pqri = getIntersect(r.pqr, epsilon);
        V3D_FiniteGeometry_d rspi = getIntersect(r.rsp, epsilon);
        /**
         * The intersections will be null, a point, a line segment, a triangle,
         * a quadrilateral, a pentagon or a hexagon.
         *
         */
        if (pqri == null) {
            return rspi;
        } else if (pqri instanceof V3D_Point_d) {
            if (rspi == null) {
                return pqri;
            } else {
                return rspi;
            }
        } else if (pqri instanceof V3D_LineSegment_d) {
            if (rspi == null) {
                return pqri;
            } else if (rspi instanceof V3D_Point_d) {
                return pqri;
            } else {
                return rspi;
            }
        } else if (pqri instanceof V3D_Triangle_d pqrit) {
            if (rspi == null) {
                return pqri;
            } else if (rspi instanceof V3D_Point_d) {
                return pqri;
            } else if (rspi instanceof V3D_LineSegment_d) {
                return pqri;
            } else {
                V3D_Point_d[] pqritp = pqrit.getPointsArray();
                V3D_Point_d[] rspip = rspi.getPointsArray();
                V3D_Point_d[] pts = new V3D_Point_d[pqritp.length + rspip.length];
                System.arraycopy(pqritp, 0, pts, 0, pqritp.length);
                System.arraycopy(rspip, 0, pts, pqritp.length, rspip.length);
                return V3D_ConvexArea_d.getGeometry(epsilon, pts);
            }
        } else {
            // pqr instanceof V3D_ConvexArea_d
            if (rspi == null) {
                return pqri;
            } else if (rspi instanceof V3D_Point_d) {
                return pqri;
            } else if (rspi instanceof V3D_LineSegment_d) {
                return pqri;
            } else {
                V3D_Point_d[] pqrip = pqri.getPointsArray();
                V3D_Point_d[] rspip = rspi.getPointsArray();
                V3D_Point_d[] pts = new V3D_Point_d[pqrip.length + rspip.length];
                System.arraycopy(pqrip, 0, pts, 0, pqrip.length);
                System.arraycopy(rspip, 0, pts, pqrip.length, rspip.length);
                return V3D_ConvexArea_d.getGeometry(epsilon, pts);
            }
        }
    }

    @Override
    public V3D_Point_d[] getPointsArray() {
        V3D_Point_d[] re = new V3D_Point_d[4];
        re[0] = getP();
        re[1] = getQ();
        re[2] = getR();
        re[3] = getS();
        return re;
    }

    @Override
    public HashMap<Integer, V3D_Point_d> getPoints() {
        if (points == null) {
            points = new HashMap<>(4);
            points.put(0, getP());
            points.put(1, getQ());
            points.put(2, getR());
            points.put(3, getS());
        }
        return points;
    }

    @Override
    public HashMap<Integer, V3D_Area_d> getFaces() {
        if (faces == null) {
            faces = new HashMap<>(4);
            faces.put(0, getPqr());
            faces.put(1, getPsq());
            faces.put(2, getQsr());
            faces.put(3, getSpr());
        }
        return faces;
    }

    /**
     * Get the distance to {@code p}.
     *
     * @param p A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance squared to {@code p}.
     */
    public double getDistance(V3D_Point_d p, double epsilon) {
        return Math.sqrt(getDistanceSquared(p, epsilon));
    }

    /**
     * Get the distance squared to {@code p}.
     *
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance squared to {@code p}.
     */
    public double getDistanceSquared(V3D_Point_d pt, double epsilon) {
        if (intersects(pt, epsilon)) {
            return 0d;
        } else {
            return Math.min(getPqr().getDistanceSquared(pt, epsilon),
                    Math.min(getPsq().getDistanceSquared(pt, epsilon),
                            Math.min(getQsr().getDistanceSquared(pt, epsilon),
                                    getSpr().getDistanceSquared(pt, epsilon))));
        }
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistance(V3D_Line_d l, double epsilon) {
        return Math.sqrt(getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistance(V3D_LineSegment_d l, double epsilon) {
        return Math.sqrt(getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistanceSquared(V3D_LineSegment_d l, double epsilon) {
        if (getIntersect(l, epsilon) != null) {
            return 0d;
        } else {
            return Math.min(getPqr().getDistanceSquared(l, epsilon),
                    Math.min(getPsq().getDistanceSquared(l, epsilon),
                            Math.min(getQsr().getDistanceSquared(l, epsilon),
                                    getSpr().getDistanceSquared(l, epsilon))));
        }
    }

    /**
     * Change {@link #offset} without changing the overall line.
     *
     * @param offset What {@link #offset} is set to.
     */
    public void setOffset(V3D_Vector_d offset) {
        p = p.add(offset).subtract(this.offset);
        q = q.add(offset).subtract(this.offset);
        r = r.add(offset).subtract(this.offset);
        s = s.add(offset).subtract(this.offset);
        this.offset = offset;
    }

    @Override
    public V3D_Tetrahedron_d rotate(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V3D_Tetrahedron_d(this);
        } else {
            return rotateN(ray, uv, theta, epsilon);
        }
    }

    @Override
    public V3D_Tetrahedron_d rotateN(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        return new V3D_Tetrahedron_d(
                getP().rotate(ray, uv, theta, epsilon),
                getQ().rotate(ray, uv, theta, epsilon),
                getR().rotate(ray, uv, theta, epsilon),
                getS().rotate(ray, uv, theta, epsilon), epsilon);
    }

    /**
     * Get the intersection between the geometry and the tetrahedron {@code t}.
     *
     * @param t The tetrahedron to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_Tetrahedron_d t,
            double epsilon) {
        if (!getAABB().intersects(t.getAABB())) {
            return null;
        }
        V3D_FiniteGeometry_d pqri = getIntersect(t.getPqr(), epsilon);
        if (pqri == null) {
            V3D_FiniteGeometry_d psqi = getIntersect(t.getPsq(), epsilon);
            if (psqi == null) {
                V3D_FiniteGeometry_d qsri = getIntersect(t.getQsr(), epsilon);
                if (qsri == null) {
                    V3D_FiniteGeometry_d spri = getIntersect(t.getSpr(), epsilon);
                    if (spri == null) {
                        V3D_FiniteGeometry_d tpqri = t.getIntersect(getPqr(), epsilon);
                        if (tpqri == null) {
                            V3D_FiniteGeometry_d tpsqi = t.getIntersect(getPsq(), epsilon);
                            if (tpsqi == null) {
                                V3D_FiniteGeometry_d tqsri = t.getIntersect(getQsr(), epsilon);
                                if (tqsri == null) {
                                    V3D_FiniteGeometry_d tspri = t.getIntersect(getSpr(), epsilon);
                                    if (tspri == null) {
                                        if (intersects(t.getP(), epsilon)
                                                && intersects(t.getQ(), epsilon)
                                                && intersects(t.getR(), epsilon)
                                                && intersects(t.getS(), epsilon)) {
                                            return t;
                                        } else {
                                            if (t.intersects(getP(), epsilon)
                                                    && t.intersects(getQ(), epsilon)
                                                    && t.intersects(getR(), epsilon)
                                                    && t.intersects(getS(), epsilon)) {
                                                return this;
                                            } else {
                                                return null;
                                            }
                                        }
                                    } else {
                                        // Has to be a point.
                                        return tspri;
                                    }
                                } else if (tqsri instanceof V3D_Point_d) {
                                    return tqsri;
                                } else if (tqsri instanceof V3D_LineSegment_d) {
                                    return tqsri;
                                } else if (tqsri instanceof V3D_Triangle_d) {
                                    /**
                                     * Triangle: Find which point is on the
                                     * other side of the triangle plane. In this
                                     * case, this is the only point of
                                     *
                                     */
                                } else {
                                    // V3D_Triangle_dCoplanar
                                }
                            } else if (tpsqi instanceof V3D_Point_d) {
                                return tpsqi;
                            } else if (tpsqi instanceof V3D_LineSegment_d) {
                                return tpsqi;
                            } else if (tpsqi instanceof V3D_Triangle_d) {
                                /**
                                 * Triangle: Find which point is on the other
                                 * side of the triangle plane. In this case,
                                 * this is the only point of
                                 *
                                 */
                            } else {
                                // V3D_Triangle_dCoplanar
                            }
                        }
                    }
                }
            }
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A lineDouble.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistanceSquared(V3D_Line_d l, double epsilon) {
        if (getIntersect(l, epsilon) != null) {
            return 0d;
        } else {
            return Math.min(getPqr().getDistanceSquared(l, epsilon),
                    Math.min(getPsq().getDistanceSquared(l, epsilon),
                            Math.min(getQsr().getDistanceSquared(l, epsilon),
                                    getSpr().getDistanceSquared(l, epsilon))));
        }
    }

    /**
     * Get the minimum distance to {@code p}.
     *
     * @param p A planeDouble.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code p}.
     */
    public double getDistance(V3D_Plane_d p, double epsilon) {
        return Math.sqrt(getDistanceSquared(p, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code p}.
     *
     * @param p A planeDouble.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code p}.
     */
    public double getDistanceSquared(V3D_Plane_d p, double epsilon) {
        if (getIntersect(p, epsilon) != null) {
            return 0d;
        } else {
            return Math.min(getPqr().getDistanceSquared(p, epsilon),
                    Math.min(getPsq().getDistanceSquared(p, epsilon),
                            Math.min(getQsr().getDistanceSquared(p, epsilon),
                                    getSpr().getDistanceSquared(p, epsilon))));
        }
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A triangleDouble.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code t}.
     */
    public double getDistance(V3D_Triangle_d t, double epsilon) {
        return Math.sqrt(getDistanceSquared(t, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A triangle.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code t}.
     */
    public double getDistanceSquared(V3D_Triangle_d t, double epsilon) {
        if (getIntersect(t, epsilon) != null) {
            return 0d;
        } else {
            return Math.min(getPqr().getDistanceSquared(t, epsilon),
                    Math.min(getPsq().getDistanceSquared(t, epsilon),
                            Math.min(getQsr().getDistanceSquared(t, epsilon),
                                    getSpr().getDistanceSquared(t, epsilon))));
        }
    }

    /**
     * Get the minimum distance to {@code r}.
     *
     * @param r A rectangle.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code t}.
     */
    public double getDistance(V3D_Rectangle_d r, double epsilon) {
        return Math.sqrt(getDistanceSquared(r, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code r}.
     *
     * @param r A rectangle.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code t}.
     */
    public double getDistanceSquared(V3D_Rectangle_d r, double epsilon) {
        double pqrd = getDistanceSquared(r.pqr, epsilon);
        if (pqrd == 0d) {
            return pqrd;
        } else {
            return Math.min(pqrd, getDistanceSquared(r.rsp, epsilon));
        }
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A tetrahedron.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code t}.
     */
    public double getDistance(V3D_Tetrahedron_d t, double epsilon) {
        return Math.sqrt(getDistanceSquared(t, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A tetrahedron.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code t}.
     */
    public double getDistanceSquared(V3D_Tetrahedron_d t, double epsilon) {
        if (getIntersect(t, epsilon) != null) {
            return 0d;
        } else {
            return Math.min(t.getDistanceSquared(getPqr(), epsilon),
                    Math.min(t.getDistanceSquared(getPsq(), epsilon),
                            Math.min(t.getDistanceSquared(getQsr(), epsilon),
                                    t.getDistanceSquared(getSpr(), epsilon))));
        }
    }

    //@Override
    public boolean intersects(V3D_AABB_d aabb, double epsilon) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
