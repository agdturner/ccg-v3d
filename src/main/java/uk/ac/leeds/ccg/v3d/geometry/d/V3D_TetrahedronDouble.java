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

import uk.ac.leeds.ccg.math.arithmetic.Math_Double;

/**
 *
 * A V3D_TetrahedronDouble comprises 4 corner V3D_PointDouble points that are
 * not coplanar or collinear or coincident. There are 4 V3D_TriangleDouble faces
 * defined from the outside with the points arranged clockwise. Each face has a
 * V3D_Line_SegmentDouble edge equal to that of another face, but these are
 * stored independently in that the order of the points and the directions of
 * the vectors might be opposite. The points are shared between all the
 * triangles. {@code
 * pl *- - - - - - - - - - - + - - - - - - - - - - -* qv
 * \ ~                                         /|
 * \     ~                                   / |
 * \         ~                             /  |
 * \             ~                       /   |
 * \                 ~                 /    |
 * \                      ~          /     |
 * \                          ~    /      |
 * \                             / ~     |
 * \                           /       ~s
 * \                         /        :
 * \                       /
 * \                     /      :
 * \                   /
 * \                 /     :
 * \               /
 * \             /    :
 * \           /
 * \         /   :
 * \       /
 * \     /  :
 * \   /
 * \ /:
 *
 * r
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_TetrahedronDouble extends V3D_FiniteGeometryDouble
        implements V3D_VolumeDouble {

    private static final long serialVersionUID = 1L;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_VectorDouble p;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_VectorDouble q;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_VectorDouble r;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_VectorDouble s;

    /**
     * For storing the pqr triangle of the tetrahedron.
     */
    public V3D_TriangleDouble pqr;

    /**
     * For storing the qsr triangle of the tetrahedron.
     */
    public V3D_TriangleDouble qsr;

    /**
     * For storing the spr triangle of the tetrahedron.
     */
    public V3D_TriangleDouble spr;

    /**
     * For storing the psq triangle of the tetrahedron.
     */
    public V3D_TriangleDouble psq;

    /**
     * Create a new instance. {@code pl}, {@code qv}, {@code r} and {@code s}
     * must all be different, not the zero vector and collectively they must be
     * three dimensional. This is generally the fastest way to construct a
     * tetrahedron.
     *
     * @param offset What {@link #offset} is set to.
     * @param p A point that defines the tetrahedron.
     * @param q A point that defines the tetrahedron.
     * @param r A point that defines the tetrahedron.
     * @param s A point that defines the tetrahedron.
     */
    public V3D_TetrahedronDouble(V3D_VectorDouble offset, V3D_VectorDouble p,
            V3D_VectorDouble q, V3D_VectorDouble r, V3D_VectorDouble s) {
        super(offset);
        this.p = p;
        this.q = q;
        this.r = r;
        this.s = s;
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
    public V3D_TetrahedronDouble(V3D_PointDouble p, V3D_PointDouble q,
            V3D_PointDouble r, V3D_PointDouble s) {
        super(p.offset);
        this.p = new V3D_VectorDouble(p.rel);
        V3D_PointDouble qp = new V3D_PointDouble(q);
        qp.setOffset(offset);
        this.q = qp.rel;
        V3D_PointDouble rp = new V3D_PointDouble(r);
        rp.setOffset(offset);
        this.r = rp.rel;
        V3D_PointDouble sp = new V3D_PointDouble(s);
        sp.setOffset(offset);
        this.s = sp.rel;
    }

    /**
     * Create a new instance. {@code pl}, must not be coplanar to t. No test is
     * done to check this is the case.
     *
     * @param p Used to set {@link #p} and {@link #offset}.
     * @param t Used to set {@link #q}, {@link #r} and {@link #s}.
     */
    public V3D_TetrahedronDouble(V3D_PointDouble p, V3D_TriangleDouble t) {
        this(p, t.getP(), t.getQ(), t.getR());
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
    public V3D_EnvelopeDouble getEnvelope() {
        if (en == null) {
            en = getP().getEnvelope()
                    .union(getQ().getEnvelope())
                    .union(getR().getEnvelope())
                    .union(getS().getEnvelope());
        }
        return en;
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_PointDouble getP() {
        return new V3D_PointDouble(offset, p);
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_PointDouble getQ() {
        return new V3D_PointDouble(offset, q);
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_PointDouble getR() {
        return new V3D_PointDouble(offset, r);
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_PointDouble getS() {
        return new V3D_PointDouble(offset, s);
    }

    /**
     * If {@code null} initialise {@link #pqr} and return it.
     *
     * @return {@link #pqr}
     */
    public V3D_TriangleDouble getPqr() {
        if (pqr == null) {
            pqr = new V3D_TriangleDouble(offset, p, q, r);
        }
        return pqr;
    }

    /**
     * If {@code null} initialise {@link #qsr} and return it.
     *
     * @return {@link #qsr}
     */
    public V3D_TriangleDouble getQsr() {
        if (qsr == null) {
            qsr = new V3D_TriangleDouble(offset, q, s, r);
        }
        return qsr;
    }

    /**
     * If {@code null} initialise {@link #spr} and return it.
     *
     * @return {@link #spr}
     */
    public V3D_TriangleDouble getSpr() {
        if (spr == null) {
            spr = new V3D_TriangleDouble(offset, s, p, r);
        }
        return spr;
    }

    /**
     * If {@code null} initialise {@link #psq} and return it.
     *
     * @return {@link #psq}
     */
    public V3D_TriangleDouble getPsq() {
        if (psq == null) {
            psq = new V3D_TriangleDouble(offset, p, s, q);
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
        V3D_TriangleDouble tpqr = getPqr();
        V3D_PointDouble ts = getS();
        double hd3 = tpqr.getPl().getPointOfProjectedIntersection(ts)
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
    public V3D_PointDouble getCentroid() {
        double dx = (p.dx + q.dx + r.dx + s.dx) / 4d;
        double dy = (p.dy + q.dy + r.dy + s.dy) / 4d;
        double dz = (p.dz + q.dz + r.dz + s.dz) / 4d;
        return new V3D_PointDouble(offset, new V3D_VectorDouble(dx, dy, dz));
    }

    /**
     * Identify if this is intersected by point {@code pt}.
     *
     * @param pt The point to test for intersection with.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V3D_PointDouble pt) {
        pqr = getPqr();
        psq = getPsq();
        spr = getSpr();
        qsr = getQsr();
        if (pqr.getPl().isOnSameSide(pt, getS())) {
            if (psq.getPl().isOnSameSide(pt, getR())) {
                if (spr.getPl().isOnSameSide(pt, getQ())) {
                    if (qsr.getPl().isOnSameSide(pt, getP())) {
                        return true;
                    }
                }
            }
        }
        if (qsr.isIntersectedBy(pt)) {
            return true;
        }
        if (spr.isIntersectedBy(pt)) {
            return true;
        }
        if (psq.isIntersectedBy(pt)) {
            return true;
        }
        return pqr.isIntersectedBy(pt);
    }

    /**
     * @param r The ray for which the intersection is returned.
     * @return The intersection of this and r.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_RayDouble r) {
        V3D_FiniteGeometryDouble irl = getIntersection(r.l);
        if (irl == null) {
            return null;
        }
        if (irl instanceof V3D_PointDouble irlp) {
            if (r.isAligned(irlp)) {
                return irl;
            } else {
                return null;
            }
        }
        return ((V3D_LineSegmentDouble) irl).clip(r.getPl(), r.l.getQ());
    }

    /**
     * Get the intersection between this and the line {@code l}.
     *
     * @param l The line to intersect with.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_LineDouble l) {
        V3D_FiniteGeometryDouble pqri = getPqr().getIntersection(l);
        if (pqri == null) {
            V3D_FiniteGeometryDouble psqi = getPsq().getIntersection(l);
            if (psqi == null) {
                V3D_FiniteGeometryDouble qsri = getQsr().getIntersection(l);
                if (qsri == null) {
                    return null;
                }
                if (qsri instanceof V3D_LineSegmentDouble) {
                    return qsri;
                }
                V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                if (spri == null) {
                    return null;
                }
                return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) qsri,
                        (V3D_PointDouble) spri);
            } else {
                if (psqi instanceof V3D_LineSegmentDouble) {
                    return psqi;
                }
                V3D_FiniteGeometryDouble qsri = getQsr().getIntersection(l);
                if (qsri == null) {
                    return psqi;
                }
                if (qsri instanceof V3D_LineSegmentDouble) {
                    return qsri;
                }
                return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) psqi,
                        (V3D_PointDouble) qsri);
            }
        } else if (pqri instanceof V3D_PointDouble pqrip) {
            V3D_FiniteGeometryDouble psqi = getPsq().getIntersection(l);
            if (psqi == null) {
                V3D_FiniteGeometryDouble qsri = getQsr().getIntersection(l);
                if (qsri == null) {
                    return null;
                } else if (qsri instanceof V3D_PointDouble qsrip) {
                    V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                    if (spri == null) {
                        return V3D_LineSegmentDouble.getGeometry(qsrip, pqrip);
                    } else if (spri instanceof V3D_PointDouble sprip) {
                        return V3D_TriangleDouble.getGeometry(qsrip, pqrip, sprip);
                    } else {
                        return spri;
                    }
                } else {
                    return qsri;
                }
            } else if (psqi instanceof V3D_PointDouble psqip) {
                V3D_FiniteGeometryDouble qsri = getQsr().getIntersection(l);
                if (qsri == null) {
                    V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                    if (spri == null) {
                        return V3D_LineSegmentDouble.getGeometry(psqip, pqrip);
                    } else if (spri instanceof V3D_PointDouble sprip) {
                        return V3D_TriangleDouble.getGeometry(psqip, pqrip, sprip);
                    } else {
                        return spri;
                    }
                } else if (qsri instanceof V3D_PointDouble qsrip) {
                    V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                    if (spri == null) {
                        return V3D_LineSegmentDouble.getGeometry(psqip, pqrip);
                    } else if (spri instanceof V3D_PointDouble sprip) {
                        return getGeometry(pqrip, psqip, qsrip, sprip);
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
     * they are coplanar, a V3D_TriangleDoubleCoplanar is returned, otherwise a
     * tetrahedron is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @param s Another possibly equal point.
     * @return either {@code pl} or {@code new V3D_LineSegmentDouble(pl, qv)} or
     * {@code new V3D_TriangleDouble(pl, qv, r)}
     */
    public static V3D_FiniteGeometryDouble getGeometry(V3D_PointDouble p,
            V3D_PointDouble q, V3D_PointDouble r, V3D_PointDouble s) {
        if (p.equals(q)) {
            return V3D_TriangleDouble.getGeometry(p, r, s);
        } else if (p.equals(r)) {
            return V3D_TriangleDouble.getGeometry(p, q, s);
        } else if (p.equals(s)) {
            return V3D_TriangleDouble.getGeometry(p, q, r);
        } else if (q.equals(r)) {
            return V3D_TriangleDouble.getGeometry(p, q, s);
        } else if (q.equals(s)) {
            return V3D_TriangleDouble.getGeometry(p, q, r);
        } else if (r.equals(s)) {
            return V3D_TriangleDouble.getGeometry(p, q, r);
        } else {
            if (V3D_PlaneDouble.isCoplanar(p, q, r, s)) {
                if (V3D_LineDouble.isCollinear(p, q, r, s)) {
                    //return V3D_Line(pl, qv, r, s);
                    throw new UnsupportedOperationException("Need code to construct a line segment from 4 points!");
                }
                V3D_LineSegmentDouble pq = new V3D_LineSegmentDouble(p, q);
                V3D_LineSegmentDouble rs = new V3D_LineSegmentDouble(r, s);
                if (pq.getIntersection(rs) != null) {
                    return new V3D_PolygonDouble(
                            new V3D_TriangleDouble(p, q, r),
                            new V3D_TriangleDouble(p, q, s));
                } else {
                    V3D_LineSegmentDouble pr = new V3D_LineSegmentDouble(p, r);
                    V3D_LineSegmentDouble qs = new V3D_LineSegmentDouble(q, s);
                    if (pr.getIntersection(qs) != null) {
                        return new V3D_PolygonDouble(
                                new V3D_TriangleDouble(p, r, q),
                                new V3D_TriangleDouble(p, r, s));
                    } else {
                        return new V3D_PolygonDouble(
                                new V3D_TriangleDouble(p, q, s),
                                new V3D_TriangleDouble(p, r, s));
                    }
                }
            }
            return new V3D_TetrahedronDouble(p, q, r, s);
        }
    }

    /**
     * At least two of the points input are expected to be the same. If all
     * points are equal then the point is returned. If there are just two
     * different points then a line segment is returned. If 3 points are
     * different then a triangle is returned. If 4 points are different then if
     * they are coplanar, a V3D_TriangleDoubleCoplanar is returned, otherwise a
     * tetrahedron is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @param s Another possibly equal point.
     * @param t Another possibly equal point.
     * @return either {@code pl} or {@code new V3D_LineSegmentDouble(pl, qv)} or
     * {@code new V3D_TriangleDouble(pl, qv, r)}
     */
    protected static V3D_FiniteGeometryDouble getGeometry(V3D_PointDouble p,
            V3D_PointDouble q, V3D_PointDouble r, V3D_PointDouble s,
            V3D_PointDouble t) {
        if (p.equals(q)) {
            return getGeometry(p, r, s, t);
        } else if (p.equals(r)) {
            return getGeometry(p, q, s, t);
        } else if (p.equals(s)) {
            return getGeometry(p, q, r, t);
        } else if (p.equals(t)) {
            return getGeometry(p, q, r, s);
        } else if (q.equals(r)) {
            return getGeometry(p, q, s, t);
        } else if (q.equals(s)) {
            return getGeometry(p, q, r, t);
        } else if (q.equals(t)) {
            return getGeometry(p, q, r, s);
        } else if (r.equals(s)) {
            return getGeometry(p, q, r, t);
        } else if (r.equals(t)) {
            return getGeometry(p, q, r, s);
        } else {
            return getGeometry(p, q, r, t);
        }
    }

    /**
     * Get the intersection between this and the line segment {@code l}.
     *
     * @param l The line segment to intersect with.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_LineSegmentDouble l) {
        V3D_FiniteGeometryDouble g = getIntersection(l.l);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_PointDouble gp) {
                if (isIntersectedBy(gp)) {
                    return g;
                } else {
                    return null;
                }
            } else {
                V3D_PointDouble lp = l.getP();
                V3D_PointDouble lq = l.getQ();
                if (isIntersectedBy(lp)) {
                    if (isIntersectedBy(lq)) {
                        return ((V3D_LineSegmentDouble) g).getIntersection(l);
                    } else {
                        V3D_FiniteGeometryDouble pqri = getPqr().getIntersection(l);
                        if (pqri == null) {
                            V3D_FiniteGeometryDouble psqi = getPsq().getIntersection(l);
                            if (psqi == null) {
                                V3D_FiniteGeometryDouble qsri = getQsr().getIntersection(l);
                                if (qsri == null) {
                                    return new V3D_LineSegmentDouble(lp,
                                            (V3D_PointDouble) getSpr().getIntersection(l));
                                } else {
                                    if (qsri instanceof V3D_PointDouble qsrip) {
                                        V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                                        if (spri == null) {
                                            return new V3D_LineSegmentDouble(lp, qsrip);
                                        } else {
                                            return V3D_TriangleDouble.getGeometry(lp, qsrip, (V3D_PointDouble) spri);
                                        }
                                    } else {
                                        return qsri;
                                    }
                                }
                            } else {
                                if (psqi instanceof V3D_PointDouble psqip) {
                                    V3D_FiniteGeometryDouble qsri = getQsr().getIntersection(l);
                                    if (qsri == null) {
                                        V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                                        if (spri == null) {
                                            return new V3D_LineSegmentDouble(lp, psqip);
                                        } else {
                                            if (spri instanceof V3D_PointDouble sprip) {
                                                return V3D_TriangleDouble.getGeometry(lp, psqip, sprip);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        if (qsri instanceof V3D_PointDouble qsrip) {
                                            V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                                            if (spri == null) {
                                                return V3D_TriangleDouble.getGeometry(lp, psqip, qsrip);
                                            } else {
                                                if (spri instanceof V3D_PointDouble sprip) {
                                                    return getGeometry(lp, psqip, qsrip, sprip);
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
                            if (pqri instanceof V3D_PointDouble pqrip) {
                                V3D_FiniteGeometryDouble psqi = getPsq().getIntersection(l);
                                if (psqi == null) {
                                    V3D_FiniteGeometryDouble qsri = getQsr().getIntersection(l);
                                    if (qsri == null) {
                                        V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                                        if (spri == null) {
                                            return new V3D_LineSegmentDouble(lp, pqrip);
                                        } else {
                                            if (spri instanceof V3D_PointDouble sprip) {
                                                return V3D_TriangleDouble.getGeometry(lp, pqrip, sprip);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        if (qsri instanceof V3D_PointDouble qsrip) {
                                            V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                                            if (spri == null) {
                                                return V3D_TriangleDouble.getGeometry(lp, pqrip, qsrip);
                                            } else {
                                                if (spri instanceof V3D_PointDouble sprip) {
                                                    return getGeometry(lp, pqrip, qsrip, sprip);
                                                } else {
                                                    return spri;
                                                }
                                            }
                                        } else {
                                            return qsri;
                                        }
                                    }
                                } else {
                                    if (psqi instanceof V3D_PointDouble psqip) {
                                        V3D_FiniteGeometryDouble qsri = getQsr().getIntersection(l);
                                        if (qsri == null) {
                                            V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                                            if (spri == null) {
                                                return V3D_TriangleDouble.getGeometry(lp, pqrip, psqip);
                                            } else {
                                                if (spri instanceof V3D_PointDouble sprip) {
                                                    return getGeometry(lp, pqrip, psqip, sprip);
                                                } else {
                                                    return spri;
                                                }
                                            }
                                        } else {
                                            if (qsri instanceof V3D_PointDouble qsrip) {
                                                V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                                                if (spri == null) {
                                                    return getGeometry(lp, psqip, pqrip, qsrip);
                                                } else {
                                                    if (spri instanceof V3D_PointDouble sprip) {
                                                        return getGeometry(lp, psqip, pqrip, qsrip, sprip);
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
                    V3D_FiniteGeometryDouble pqri = getPqr().getIntersection(l);
                    if (pqri == null) {
                        V3D_FiniteGeometryDouble psqi = getPsq().getIntersection(l);
                        if (psqi == null) {
                            V3D_FiniteGeometryDouble qsri = getQsr().getIntersection(l);
                            if (qsri == null) {
                                return new V3D_LineSegmentDouble(lq,
                                        (V3D_PointDouble) getSpr().getIntersection(l));
                            } else {
                                if (qsri instanceof V3D_PointDouble qsrip) {
                                    V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                                    if (spri == null) {
                                        return new V3D_LineSegmentDouble(lq, qsrip);
                                    } else {
                                        return V3D_TriangleDouble.getGeometry(lq, qsrip, (V3D_PointDouble) spri);
                                    }
                                } else {
                                    return qsri;
                                }
                            }
                        } else {
                            if (psqi instanceof V3D_PointDouble psqip) {
                                V3D_FiniteGeometryDouble qsri = getQsr().getIntersection(l);
                                if (qsri == null) {
                                    V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                                    if (spri == null) {
                                        return new V3D_LineSegmentDouble(lq, psqip);
                                    } else {
                                        if (spri instanceof V3D_PointDouble sprip) {
                                            return V3D_TriangleDouble.getGeometry(lq, psqip, sprip);
                                        } else {
                                            return spri;
                                        }
                                    }
                                } else {
                                    if (qsri instanceof V3D_PointDouble qsrip) {
                                        V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                                        if (spri == null) {
                                            return V3D_TriangleDouble.getGeometry(lq, psqip, qsrip);
                                        } else {
                                            if (spri instanceof V3D_PointDouble sprip) {
                                                return getGeometry(lq, psqip, qsrip, sprip);
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
                        if (pqri instanceof V3D_PointDouble pqrip) {
                            V3D_FiniteGeometryDouble psqi = getPsq().getIntersection(l);
                            if (psqi == null) {
                                V3D_FiniteGeometryDouble qsri = getQsr().getIntersection(l);
                                if (qsri == null) {
                                    V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                                    if (spri == null) {
                                        return new V3D_LineSegmentDouble(lq, pqrip);
                                    } else {
                                        if (spri instanceof V3D_PointDouble sprip) {
                                            return V3D_TriangleDouble.getGeometry(lq, pqrip, sprip);
                                        } else {
                                            return spri;
                                        }
                                    }
                                } else {
                                    if (qsri instanceof V3D_PointDouble qsrip) {
                                        V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                                        if (spri == null) {
                                            return V3D_TriangleDouble.getGeometry(lq, pqrip, qsrip);
                                        } else {
                                            if (spri instanceof V3D_PointDouble sprip) {
                                                return getGeometry(lq, pqrip, qsrip, sprip);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        return qsri;
                                    }
                                }
                            } else {
                                if (psqi instanceof V3D_PointDouble psqip) {
                                    V3D_FiniteGeometryDouble qsri = getQsr().getIntersection(l);
                                    if (qsri == null) {
                                        V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                                        if (spri == null) {
                                            return V3D_TriangleDouble.getGeometry(lq, pqrip, psqip);
                                        } else {
                                            if (spri instanceof V3D_PointDouble sprip) {
                                                return getGeometry(lq, pqrip, psqip, sprip);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        if (qsri instanceof V3D_PointDouble qsrip) {
                                            V3D_FiniteGeometryDouble spri = getSpr().getIntersection(l);
                                            if (spri == null) {
                                                return getGeometry(lq, psqip, pqrip, qsrip);
                                            } else {
                                                if (spri instanceof V3D_PointDouble sprip) {
                                                    return getGeometry(lq, psqip, pqrip, qsrip, sprip);
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
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_PlaneDouble pl) {
        V3D_FiniteGeometryDouble pqri = getPqr().getIntersection(pl);
        if (pqri == null) {
            V3D_FiniteGeometryDouble psqi = getPsq().getIntersection(pl);
            if (psqi == null) {
                return null;
            } else if (psqi instanceof V3D_PointDouble psqip) {
                // psqip must be the point s!
                return psqip;
            } else if (psqi instanceof V3D_LineSegmentDouble psqil) {
                /**
                 * There will also be a line segment of qsr and spr that
                 * together form a triangle.
                 */
                V3D_LineSegmentDouble qsril = (V3D_LineSegmentDouble) getQsr().getIntersection(pl);
                V3D_LineSegmentDouble spril = (V3D_LineSegmentDouble) getSpr().getIntersection(pl);
                return V3D_TriangleDouble.getGeometry(psqil, qsril, spril);
            } else {
                // Triangle
                return (V3D_TriangleDouble) psqi;
            }
        } else if (pqri instanceof V3D_PointDouble pqrip) {
            V3D_FiniteGeometryDouble psqi = getPsq().getIntersection(pl);
            if (psqi == null) {
                return pqrip;
            } else if (psqi instanceof V3D_PointDouble psqip) {
                V3D_FiniteGeometryDouble qsri = getQsr().getIntersection(pl);
                if (qsri == null) {
                    return psqip;
                } else {
                    return V3D_TriangleDouble.getGeometry((V3D_LineSegmentDouble) qsri, psqip);
                }
            } else if (psqi instanceof V3D_LineSegmentDouble psqil) {
                V3D_FiniteGeometryDouble qsri = getQsr().getIntersection(pl);
                if (qsri == null) {
                    return psqil;
                } else {
                    if (qsri instanceof V3D_PointDouble) {
                        return psqil;
                    } else {
                        //V3D_LineSegmentDouble spri = (V3D_LineSegmentDouble) getSpr().getIntersection(pl);
                        return V3D_TriangleDouble.getGeometry2(psqil, (V3D_LineSegmentDouble) qsri);
                    }
                }
            } else {
                // Triangle
                return (V3D_TriangleDouble) psqi;
            }
        } else if (pqri instanceof V3D_LineSegmentDouble pqril) {
            V3D_FiniteGeometryDouble psqi = getPsq().getIntersection(pl);
            if (psqi == null) {
                V3D_LineSegmentDouble qsril = (V3D_LineSegmentDouble) getQsr().getIntersection(pl);
                V3D_LineSegmentDouble spril = (V3D_LineSegmentDouble) getSpr().getIntersection(pl);
                return V3D_TriangleDouble.getGeometry(pqril, qsril, spril);
            } else if (psqi instanceof V3D_PointDouble) {
                V3D_FiniteGeometryDouble spri = getSpr().getIntersection(pl);
                if (spri instanceof V3D_PointDouble sprip) {
                    return V3D_TriangleDouble.getGeometry(pqril, sprip);
                } else {
                    return V3D_TriangleDouble.getGeometry2((V3D_LineSegmentDouble) spri, pqril);
                }
            } else if (psqi instanceof V3D_LineSegmentDouble psqil) {
                return V3D_TriangleDouble.getGeometry2(psqil, pqril);
            } else {
                // Triangle
                return (V3D_TriangleDouble) psqi;
            }
        } else {
            return (V3D_TriangleDouble) pqri;
        }
    }

    /**
     * Get the intersection between this and the triangle {@code t}. The
     * intersection will be null, a point, a line segment, a triangle,
     * quadrilateral, pentagon or a hexagon.
     *
     * @param t The triangle to intersect with.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_TriangleDouble t) {
        V3D_FiniteGeometryDouble i = getIntersection(t.getPl());
        if (i == null) {
            return null;
        } else {
            if (i instanceof V3D_PointDouble ip) {
                if (t.isAligned(ip)) {
                    //if (t.isIntersectedBy(pip)) {
                    return i;
                } else {
                    return null;
                }
            } else if (i instanceof V3D_LineSegmentDouble il) {
                /**
                 * Need to get the intersections of pil and the plane edges of
                 * the triangle.
                 */
                V3D_VectorDouble n = t.getPl().n;
                V3D_PlaneDouble lp = new V3D_PlaneDouble(offset, t.p, t.q, t.p.add(n));
                V3D_FiniteGeometryDouble lpiil = lp.getIntersection(il);
                if (lpiil == null) {
                    V3D_PlaneDouble lq = new V3D_PlaneDouble(offset, t.q, t.r, t.q.add(n));
                    V3D_FiniteGeometryDouble lqiil = lq.getIntersection(il);
                    if (lqiil == null) {
                        V3D_PlaneDouble lr = new V3D_PlaneDouble(offset, t.r, t.p, t.r.add(n));
                        V3D_FiniteGeometryDouble lriil = lr.getIntersection(il);
                        if (lriil == null) {
                            return il;
                        } else if (lriil instanceof V3D_PointDouble lriilp) {
                            // Find the other point and return the line segment.
                            V3D_PointDouble pq = t.getQ();
                            if (lr.isOnSameSide(lriilp, pq)) {
                                return new V3D_LineSegmentDouble(lriilp, pq);
                            } else {
                                return new V3D_LineSegmentDouble(lriilp, t.getP());
                            }
                        } else {
                            // Return the line segment.
                            return (V3D_LineSegmentDouble) lriil;
                        }
                    } else if (lqiil instanceof V3D_PointDouble lqiilp) {
                        // Find the other point and return the linesegment.
                        V3D_PlaneDouble lr = new V3D_PlaneDouble(offset, t.r, t.p, t.r.add(n));
                        V3D_FiniteGeometryDouble lriil = lr.getIntersection(il);
                        if (lriil == null) {
                            // For the points on the right side (if any)
                            V3D_PointDouble ilp = il.getP();
                            V3D_PointDouble ilq = il.getQ();
                            V3D_PointDouble tpp = t.getP();
                            if (lq.isOnSameSide(ilp, tpp)) {
                                if (lq.isOnSameSide(ilq, tpp)) {
                                    if (lqiilp.getDistanceSquared(ilp)
                                            < lqiilp.getDistanceSquared(ilq)) {
                                        return new V3D_LineSegmentDouble(lqiilp, ilq);
                                    } else {
                                        return new V3D_LineSegmentDouble(lqiilp, ilp);
                                    }
                                } else {
                                    return new V3D_LineSegmentDouble(lqiilp, ilp);
                                }
                            } else {
                                //if (lq.isOnSameSide(pilq, tpp)) {
                                return new V3D_LineSegmentDouble(lqiilp, ilq);
                                //} else {
                                //    // This should not happen!
                                //}
                            }
                        } else if (lriil instanceof V3D_PointDouble lriilp) {
                            if (lqiilp.equals(lriilp)) {
                                // Find the other point and return the line segment.
                                V3D_PointDouble ilp = il.getP();
                                V3D_PointDouble ilq = il.getQ();
                                if (lq.isOnSameSide(ilp, ilq)) {
                                    if (lqiilp.getDistanceSquared(ilp)
                                            < lqiilp.getDistanceSquared(ilq)) {
                                        return new V3D_LineSegmentDouble(lqiilp, ilq);
                                    } else {
                                        return new V3D_LineSegmentDouble(lqiilp, ilp);
                                    }
                                } else {
                                    return new V3D_LineSegmentDouble(lqiilp, ilp);
                                }
                            } else {
                                return new V3D_LineSegmentDouble(lriilp, lqiilp);
                            }
                        } else {
                            // Return the line segment.
                            return (V3D_LineSegmentDouble) lriil;
                        }
                    } else {
                        // Return the line segment.
                        return (V3D_LineSegmentDouble) lqiil;
                    }
                } else if (lpiil instanceof V3D_PointDouble lpiilp) {
                    // Find the other point and return the linesegment.
                    V3D_PlaneDouble lq = new V3D_PlaneDouble(offset, t.q, t.r, t.q.add(n));
                    V3D_FiniteGeometryDouble lqiil = lq.getIntersection(il);
                    if (lqiil == null) {
                        V3D_PlaneDouble lr = new V3D_PlaneDouble(offset, t.r, t.p, t.r.add(n));
                        V3D_FiniteGeometryDouble lriil = lr.getIntersection(il);
                        if (lriil == null) {
                            // Find the other point and return the line segment.
                            V3D_PointDouble ilp = il.getP();
                            V3D_PointDouble ilq = il.getQ();
                            if (lq.isOnSameSide(ilp, ilq)) {
                                if (lpiilp.getDistanceSquared(ilp)
                                        < lpiilp.getDistanceSquared(ilq)) {
                                    return new V3D_LineSegmentDouble(lpiilp, ilq);
                                } else {
                                    return new V3D_LineSegmentDouble(lpiilp, ilp);
                                }
                            } else {
                                return new V3D_LineSegmentDouble(lpiilp, ilp);
                            }
                        } else if (lriil instanceof V3D_PointDouble lriilp) {
                            if (lpiilp.equals(lriilp)) {
                                // Find the other point and return the line segment.
                                V3D_PointDouble ilp = il.getP();
                                V3D_PointDouble ilq = il.getQ();
                                if (lq.isOnSameSide(ilp, ilq)) {
                                    if (lpiilp.getDistanceSquared(ilp)
                                            < lpiilp.getDistanceSquared(ilq)) {
                                        return new V3D_LineSegmentDouble(lpiilp, ilq);
                                    } else {
                                        return new V3D_LineSegmentDouble(lpiilp, ilp);
                                    }
                                } else {
                                    if (lp.isOnSameSide(t.getR(), ilp)) {
                                        return new V3D_LineSegmentDouble(lpiilp, ilp);
                                    } else {
                                        return new V3D_LineSegmentDouble(lpiilp, ilq);
                                    }
                                }
                            } else {
                                return new V3D_LineSegmentDouble(lpiilp, lriilp);
                            }
                        } else {
                            // Return the line segment.
                            return (V3D_LineSegmentDouble) lriil;
                        }
                    } else if (lqiil instanceof V3D_PointDouble lqiilp) {
                        // Find the other point and return the linesegment.
                        V3D_PlaneDouble lr = new V3D_PlaneDouble(offset, t.r, t.p, t.r.add(n));
                        V3D_FiniteGeometryDouble lriil = lr.getIntersection(il);
                        if (lriil == null) {
                            // For the points on the right side (if any)
                            V3D_PointDouble pilp = il.getP();
                            V3D_PointDouble pilq = il.getQ();
                            if (lq.isOnSameSide(pilp, pilq)) {
                                if (lqiilp.getDistanceSquared(pilp)
                                        < lqiilp.getDistanceSquared(pilq)) {
                                    return new V3D_LineSegmentDouble(lqiilp, pilq);
                                } else {
                                    return new V3D_LineSegmentDouble(lqiilp, pilp);
                                }
                            } else {
                                if (lq.isOnSameSide(pilp, t.getP())) {
                                    return new V3D_LineSegmentDouble(lqiilp, pilp);
                                } else {
                                    return new V3D_LineSegmentDouble(lqiilp, pilq);
                                }
                            }
                        } else if (lriil instanceof V3D_PointDouble lriilp) {
                            if (lriilp.equals(lpiilp)) {
                                return new V3D_LineSegmentDouble(lriilp, lqiilp);
                            } else {
                                return new V3D_LineSegmentDouble(lriilp, lpiilp);
                            }
                        } else {
                            // Return the line segment.
                            return (V3D_LineSegmentDouble) lriil;
                        }
                    } else {
                        // Return the line segment.
                        return (V3D_LineSegmentDouble) lqiil;
                    }
                } else {
                    // Return the line segment.
                    return (V3D_LineSegmentDouble) lpiil;
                }
                //return t.getIntersection(pil);
            } else if (i instanceof V3D_TriangleDouble it) {
                //it.getIntersection(t);
                return it.clip(t, t.getCentroid());
//                //return it.getIntersection(t, oom); // This does not work due to precision issues.
//                /**
//                 * If all the points of t are within the planes of it, then
//                 * return t. If any edges of t intersect the planes of it, then further
//                 * intersections are needed to derive the final shape. Otherwise
//                 * return it.
//                 */
//                V3D_PointDouble tp = t.pl.getP();
//                V3D_PointDouble tq = t.pl.getQ();
//                V3D_PointDouble tr = t.pl.getR();
//                V3D_PointDouble itp = it.pl.getP();
//                V3D_PointDouble itq = it.pl.getQ();
//                V3D_PointDouble itr = it.pl.getR();
//                V3D_VectorDouble itn = it.pl.getN(oom);
//                V3D_PointDouble itpp = new V3D_PointDouble(e, itp.offset.add(itn, oom), itp.rel);
//                V3D_PlaneDouble itppl = new V3D_PlaneDouble(itp, itq, itpp);
//                V3D_PointDouble itqp = new V3D_PointDouble(e, itq.offset.add(itn, oom), itq.rel);
//                V3D_PlaneDouble itqpl = new V3D_PlaneDouble(itq, itr, itqp);
//                V3D_PointDouble itrp = new V3D_PointDouble(e, itr.offset.add(itn, oom), itr.rel);
//                V3D_PlaneDouble itrpl = new V3D_PlaneDouble(itr, itp, itrp);
//                V3D_FiniteGeometryDouble itc = it.clip(itppl, tr, oom);
//                if (itc == null) {
//                    return null;
//                } else if (itc instanceof V3D_PointDouble) {
//                    return itc;
//                } else if (itc instanceof V3D_LineSegmentDouble) {
//                    
//                } else if (itc instanceof V3D_TriangleDouble) {
//                    
//                } else {
//                    // itc instanceof V3D_ConvexHullCoplanarDouble
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
//                                                    V3D_LineSegmentDouble ititpq = (V3D_LineSegmentDouble) it.getIntersection(t.pl.getPQ(), oom);
//                                                    return V3D_TriangleDouble.getGeometry(ititpq, t.pl.getQR());
//                                                }
//                                            } else {
//                                                if (itqpl.isOnSameSide(tr, itp, oom)) {
////                                                    V3D_LineSegmentDouble ititpq = (V3D_LineSegmentDouble) it.getIntersection(t.pl.getPQ(), oom);
////                                                    return V3D_TriangleDouble.getGeometry(ititpq, t.pl.getQR());
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
//                                                    V3D_LineSegmentDouble ititpq = (V3D_LineSegmentDouble) it.getIntersection(t.pl.getPQ(), oom);
//                                                    return V3D_TriangleDouble.getGeometry(ititpq, t.pl.getQR());
//                                                }
//                                            } else {
//                                                if (itqpl.isOnSameSide(tr, itp, oom)) {
////                                                    V3D_LineSegmentDouble ititpq = (V3D_LineSegmentDouble) it.getIntersection(t.pl.getPQ(), oom);
////                                                    return V3D_TriangleDouble.getGeometry(ititpq, t.pl.getQR());
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
                V3D_ConvexHullCoplanarDouble ic = (V3D_ConvexHullCoplanarDouble) i;
                return ic.clip(t, t.getCentroid());
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
    }

    /**
     * Get the intersection with the rectangle {@code r}.
     *
     * @param r The rectangle to intersect with.
     * @return The V3D_FiniteGeometryDouble.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_RectangleDouble r) {
        V3D_TriangleDouble r_pqr = r.getPQR();
        V3D_TriangleDouble r_rsp = r.getRSP();
        V3D_FiniteGeometryDouble i_pqr = getIntersection(r_pqr);
        V3D_FiniteGeometryDouble i_rsp = getIntersection(r_rsp);
        /**
         * The intersections will be null, a point, a line segment, a triangle,
         * a quadrilateral, a pentagon or a hexagon.
         *
         */
        if (i_pqr == null) {
            return i_rsp;
        } else if (i_pqr instanceof V3D_PointDouble i_pqr_p) {
            if (i_rsp == null) {
                return i_pqr_p;
            } else {
                return i_rsp;
            }
        } else if (i_pqr instanceof V3D_LineSegmentDouble) {
            if (i_rsp == null) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_PointDouble) {
                return i_pqr;
            } else {
                return i_rsp;
            }
        } else if (i_pqr instanceof V3D_TriangleDouble i_pqrt) {
            if (i_rsp == null) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_PointDouble) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_LineSegmentDouble) {
                return i_pqr;
            } else {
                if (i_rsp instanceof V3D_TriangleDouble i_rspt) {
                    V3D_ConvexHullCoplanarDouble ch
                            = new V3D_ConvexHullCoplanarDouble(i_pqrt, i_rspt);
                    return ch.simplify();
                } else {
                    // i_rsp instanceof V3D_ConvexHullCoplanarDouble
                    V3D_ConvexHullCoplanarDouble ch
                            = new V3D_ConvexHullCoplanarDouble(
                                    (V3D_ConvexHullCoplanarDouble) i_rsp, i_pqrt);
                    return ch.simplify();
                }
            }
        } else {
            // i_pqr instanceof V3D_ConvexHullCoplanarDouble
            if (i_rsp == null) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_PointDouble) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_LineSegmentDouble) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_TriangleDouble i_rspt) {
                V3D_ConvexHullCoplanarDouble ch = new V3D_ConvexHullCoplanarDouble(
                        (V3D_ConvexHullCoplanarDouble) i_pqr, i_rspt);
                return ch.simplify();
            } else {
                // i_rsp instanceof V3D_ConvexHullCoplanarDouble
                V3D_ConvexHullCoplanarDouble ch = new V3D_ConvexHullCoplanarDouble(
                        (V3D_ConvexHullCoplanarDouble) i_pqr,
                        (V3D_ConvexHullCoplanarDouble) i_rsp);
                return ch.simplify();
            }
        }
    }

    @Override
    public V3D_PointDouble[] getPoints() {
        V3D_PointDouble[] re = new V3D_PointDouble[4];
        re[0] = getP();
        re[1] = getQ();
        re[2] = getR();
        re[3] = getS();
        return re;
    }

    /**
     * Get the distance to {@code p}.
     *
     * @param p A point.
     * @return The distance squared to {@code p}.
     */
    public double getDistance(V3D_PointDouble p) {
        return Math.sqrt(getDistanceSquared(p));
    }

    /**
     * Get the distance squared to {@code p}.
     *
     * @param pt A point.
     * @return The distance squared to {@code p}.
     */
    public double getDistanceSquared(V3D_PointDouble pt) {
        if (isIntersectedBy(pt)) {
            return 0d;
        } else {
            return Math.min(getPqr().getDistanceSquared(pt),
                    Math.min(getPsq().getDistanceSquared(pt),
                            Math.min(getQsr().getDistanceSquared(pt),
                                    getSpr().getDistanceSquared(pt))));
        }
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistance(V3D_LineDouble l) {
        return Math.sqrt(getDistanceSquared(l));
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistance(V3D_LineSegmentDouble l) {
        return Math.sqrt(getDistanceSquared(l));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistanceSquared(V3D_LineSegmentDouble l) {
        if (getIntersection(l) != null) {
            return 0d;
        } else {
            return Math.min(getPqr().getDistanceSquared(l),
                    Math.min(getPsq().getDistanceSquared(l),
                            Math.min(getQsr().getDistanceSquared(l),
                                    getSpr().getDistanceSquared(l))));
        }
    }

    /**
     * Change {@link #offset} without changing the overall line.
     *
     * @param offset What {@link #offset} is set to.
     */
    public void setOffset(V3D_VectorDouble offset) {
        p = p.add(offset).subtract(this.offset);
        q = q.add(offset).subtract(this.offset);
        r = r.add(offset).subtract(this.offset);
        s = s.add(offset).subtract(this.offset);
        this.offset = offset;
    }

    @Override
    public V3D_TetrahedronDouble rotate(V3D_LineDouble axis, double theta) {
        return new V3D_TetrahedronDouble(
                getP().rotate(axis, theta),
                getQ().rotate(axis, theta),
                getR().rotate(axis, theta),
                getS().rotate(axis, theta));
    }

    /**
     * Get the intersection between the geometry and the tetrahedron {@code t}.
     *
     * @param t The tetrahedron to intersect with.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_TetrahedronDouble t) {
        if (!getEnvelope().isIntersectedBy(t.getEnvelope())) {
            return null;
        }
        V3D_FiniteGeometryDouble pqri = getIntersection(t.getPqr());
        if (pqri == null) {
            V3D_FiniteGeometryDouble psqi = getIntersection(t.getPsq());
            if (psqi == null) {
                V3D_FiniteGeometryDouble qsri = getIntersection(t.getQsr());
                if (qsri == null) {
                    V3D_FiniteGeometryDouble spri = getIntersection(t.getSpr());
                    if (spri == null) {
                        V3D_FiniteGeometryDouble tpqri = t.getIntersection(getPqr());
                        if (tpqri == null) {
                            V3D_FiniteGeometryDouble tpsqi = t.getIntersection(getPsq());
                            if (tpsqi == null) {
                                V3D_FiniteGeometryDouble tqsri = t.getIntersection(getQsr());
                                if (tqsri == null) {
                                    V3D_FiniteGeometryDouble tspri = t.getIntersection(getSpr());
                                    if (tspri == null) {
                                        if (isIntersectedBy(t.getP())
                                                && isIntersectedBy(t.getQ())
                                                && isIntersectedBy(t.getR())
                                                && isIntersectedBy(t.getS())) {
                                            return t;
                                        } else {
                                            if (t.isIntersectedBy(getP())
                                                    && t.isIntersectedBy(getQ())
                                                    && t.isIntersectedBy(getR())
                                                    && t.isIntersectedBy(getS())) {
                                                return this;
                                            } else {
                                                return null;
                                            }
                                        }
                                    } else {
                                        // Has to be a point.
                                        return tspri;
                                    }
                                } else if (tqsri instanceof V3D_PointDouble) {
                                    return tqsri;
                                } else if (tqsri instanceof V3D_LineSegmentDouble) {
                                    return tqsri;
                                } else if (tqsri instanceof V3D_TriangleDouble) {
                                    /**
                                     * Triangle: Find which point is on the
                                     * other side of the triangle plane. In this
                                     * case, this is the only point of
                                     *
                                     */
                                } else {
                                    // V3D_TriangleDoubleCoplanar
                                }
                            } else if (tpsqi instanceof V3D_PointDouble) {
                                return tpsqi;
                            } else if (tpsqi instanceof V3D_LineSegmentDouble) {
                                return tpsqi;
                            } else if (tpsqi instanceof V3D_TriangleDouble) {
                                /**
                                 * Triangle: Find which point is on the other
                                 * side of the triangle plane. In this case,
                                 * this is the only point of
                                 *
                                 */
                            } else {
                                // V3D_TriangleDoubleCoplanar
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
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistanceSquared(V3D_LineDouble l) {
        if (getIntersection(l) != null) {
            return 0d;
        } else {
            return Math.min(getPqr().getDistanceSquared(l),
                    Math.min(getPsq().getDistanceSquared(l),
                            Math.min(getQsr().getDistanceSquared(l),
                                    getSpr().getDistanceSquared(l))));
        }
    }

    /**
     * Get the minimum distance to {@code p}.
     *
     * @param p A planeDouble.
     * @return The minimum distance squared to {@code p}.
     */
    public double getDistance(V3D_PlaneDouble p) {
        return Math.sqrt(getDistanceSquared(p));
    }

    /**
     * Get the minimum distance squared to {@code p}.
     *
     * @param p A planeDouble.
     * @return The minimum distance squared to {@code p}.
     */
    public double getDistanceSquared(V3D_PlaneDouble p) {
        if (getIntersection(p) != null) {
            return 0d;
        } else {
            return Math.min(getPqr().getDistanceSquared(p),
                    Math.min(getPsq().getDistanceSquared(p),
                            Math.min(getQsr().getDistanceSquared(p),
                                    getSpr().getDistanceSquared(p))));
        }
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A triangleDouble.
     * @return The minimum distance to {@code t}.
     */
    public double getDistance(V3D_TriangleDouble t) {
        return Math.sqrt(getDistanceSquared(t));
    }

    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A triangle.
     * @return The minimum distance to {@code t}.
     */
    public double getDistanceSquared(V3D_TriangleDouble t) {
        if (getIntersection(t) != null) {
            return 0d;
        } else {
            return Math.min(getPqr().getDistanceSquared(t),
                    Math.min(getPsq().getDistanceSquared(t),
                            Math.min(getQsr().getDistanceSquared(t),
                                    getSpr().getDistanceSquared(t))));
        }
    }

    /**
     * Get the minimum distance to {@code r}.
     *
     * @param r A rectangle.
     * @return The minimum distance to {@code t}.
     */
    public double getDistance(V3D_RectangleDouble r) {
        return Math.sqrt(getDistanceSquared(r));
    }

    /**
     * Get the minimum distance squared to {@code r}.
     *
     * @param r A rectangle.
     * @return The minimum distance to {@code t}.
     */
    public double getDistanceSquared(V3D_RectangleDouble r) {
        double pqrd = getDistanceSquared(r.getPQR());
        if (pqrd == 0d) {
            return pqrd;
        } else {
            return Math.min(pqrd, getDistanceSquared(r.getRSP()));
        }
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A tetrahedron.
     * @return The minimum distance to {@code t}.
     */
    public double getDistance(V3D_TetrahedronDouble t) {
        return Math.sqrt(getDistanceSquared(t));
    }

    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A tetrahedron.
     * @return The minimum distance to {@code t}.
     */
    public double getDistanceSquared(V3D_TetrahedronDouble t) {
        if (getIntersection(t) != null) {
            return 0d;
        } else {
            return Math.min(t.getDistanceSquared(getPqr()),
                    Math.min(t.getDistanceSquared(getPsq()),
                            Math.min(t.getDistanceSquared(getQsr()),
                                    t.getDistanceSquared(getSpr()))));
        }
    }
}
