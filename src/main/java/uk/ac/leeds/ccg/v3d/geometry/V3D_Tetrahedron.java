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

import java.math.BigDecimal;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 *
 * A V3D_Tetrahedron is a V3D_Shape comprising 4 corner V3D_Point points that
 * are not coplanar or collinear or coincident. There are 4 V3D_Triangle
 * triangle faces defined from the outside with the points arranged clockwise.
 * Each face has a V3D_Line_Segment edge equal to that of another face, but
 * these are stored independently in that the order of the points and the
 * directions of the vectors might be opposite. The points are shared between
 * all the triangles. {@code
 *  p *- - - - - - - - - - - + - - - - - - - - - - -* q
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
public class V3D_Tetrahedron extends V3D_Geometry implements V3D_Volume {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    protected V3D_Envelope en;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector p;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector q;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector r;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector s;

    /**
     * For storing the pqr triangle of the tetrahedron.
     */
    public V3D_Triangle pqr;

    /**
     * For storing the qsr triangle of the tetrahedron.
     */
    public V3D_Triangle qsr;

    /**
     * For storing the spr triangle of the tetrahedron.
     */
    public V3D_Triangle spr;

    /**
     * For storing the psq triangle of the tetrahedron.
     */
    public V3D_Triangle psq;

    /**
     * Create a new instance.
     *
     * @param e What {@link #e} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p A point that defines the tetrahedron.
     * @param q A point that defines the tetrahedron.
     * @param r A point that defines the tetrahedron.
     * @param s A point that defines the tetrahedron.
     */
    public V3D_Tetrahedron(V3D_Environment e, V3D_Vector offset, V3D_Vector p,
            V3D_Vector q, V3D_Vector r, V3D_Vector s) {
        super(e, offset);
        this.p = p;
        this.q = q;
        this.r = r;
        this.s = s;
    }

    /**
     * Create a new instance. {@code p}, {@code q}, {@code r}, {@code s} must
     * all be different and not coplanar. No test is done to check these things.
     *
     * @param p Used to set {@link #p}, {@link #e} and {@link #offset}.
     * @param q Used to set {@link #q}.
     * @param r Used to set {@link #r}.
     * @param s Used to set {@link #s}.
     */
    public V3D_Tetrahedron(V3D_Point p, V3D_Point q, V3D_Point r, V3D_Point s) {
        super(p.e, p.offset);
        this.p = new V3D_Vector(p.rel);
        V3D_Point qp = new V3D_Point(q);
        qp.setOffset(offset);
        this.q = qp.rel;
        V3D_Point rp = new V3D_Point(r);
        rp.setOffset(offset);
        this.r = rp.rel;
        V3D_Point sp = new V3D_Point(s);
        sp.setOffset(offset);
        this.s = sp.rel;
    }

    @Override
    public String toString() {
        return toString("");
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
        String st = super.toStringFields(pad);
        st += pad + ",\n";
        st += pad + "p=" + p.toString(pad) + "\n";
        st += pad + ",\n";
        st += pad + "q=" + q.toString(pad) + "\n";
        st += pad + ",\n";
        st += pad + "r=" + this.r.toString(pad) + "\n";
        st += pad + ",\n";
        st += pad + "s=" + s.toString(pad);// + "\n";
        return st;
    }

    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
//            en = pqr.getEnvelope(oom).union(qsr.getEnvelope(oom));
            en = getP(e.oom).getEnvelope()
                    .union(getQ(e.oom).getEnvelope())
                    .union(getR(e.oom).getEnvelope())
                    .union(getS(e.oom).getEnvelope());
        }
        return en;
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getP(int oom) {
        return new V3D_Point(e, offset, p);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getQ(int oom) {
        return new V3D_Point(e, offset, q);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getR(int oom) {
        return new V3D_Point(e, offset, r);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getS(int oom) {
        return new V3D_Point(e, offset, s);
    }

    /**
     * If {@code null} initialise {@link #pqr} and return it.
     *
     * @return {@link #pqr}
     */
    public V3D_Triangle getPqr() {
        if (pqr == null) {
            pqr = new V3D_Triangle(e, offset, p, q, r);
        }
        return pqr;
    }

    /**
     * If {@code null} initialise {@link #qsr} and return it.
     *
     * @return {@link #qsr}
     */
    public V3D_Triangle getQsr() {
        if (qsr == null) {
            qsr = new V3D_Triangle(e, offset, q, s, r);
        }
        return qsr;
    }

    /**
     * If {@code null} initialise {@link #spr} and return it.
     *
     * @return {@link #spr}
     */
    public V3D_Triangle getSpr() {
        if (spr == null) {
            spr = new V3D_Triangle(e, offset, s, p, r);
        }
        return spr;
    }

    /**
     * If {@code null} initialise {@link #psq} and return it.
     *
     * @return {@link #psq}
     */
    public V3D_Triangle getPsq() {
        if (psq == null) {
            psq = new V3D_Triangle(e, offset, p, s, q);
        }
        return psq;
    }

    /**
     * For calculating and returning the surface area.
     *
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The outer surface area of the tetrahedron (rounded).
     */
    @Override
    public BigDecimal getArea(int oom) {
        return getPqr().getArea(oom)
                .add(getQsr().getArea(oom))
                .add(getSpr().getArea(oom))
                .add(getPsq().getArea(oom));
    }

    /**
     * Calculate and return the volume.
     * https://en.wikipedia.org/wiki/Tetrahedron#Volume This implementation is
     * currently a bit rough and ready.
     *
     * @param oom The Order of Magnitude for the precision of the calculation.
     */
    @Override
    public BigDecimal getVolume(int oom) {
        V3D_Triangle tpqr = getPqr();
        V3D_Point ts = getS(oom);
        int oomn6 = oom - 6;
        BigDecimal hd3 = new Math_BigRationalSqrt(
                tpqr.getPointOfProjectedIntersection(ts, oom)
                        .getDistanceSquared(ts, oomn6), oomn6).getSqrt(oom).divide(3)
                .toBigDecimal(oomn6);
        return Math_BigDecimal.round(tpqr.getArea(oom - 3).multiply(hd3), oom);
    }

    /**
     * Calculate and return the centroid as a point.
     *
     * The original implementation used intersection, but it is simpler to get
     * the average of the x, y and z coordinates from the points at each vertex.
     * https://www.globalspec.com/reference/52702/203279/4-8-the-centroid-of-a-tetrahedron
     *
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The centroid point.
     */
    public V3D_Point getCentroid(int oom) {
        int oomn6 = oom - 6;
//        V3D_LineSegment a = new V3D_LineSegment(e,
//                getPqr().getCentroid(oom).getVector(oom), 
//                getQsr().getQ().getVector(oom));
//        V3D_LineSegment b = new V3D_LineSegment(e,
//                getPsq().getCentroid(oom).getVector(oom), 
//                getQsr().getR().getVector(oom));
//        return (V3D_Point) a.getIntersection(b, oom);
        Math_BigRational dx = (p.getDX(oomn6).add(q.getDX(oomn6))
                .add(r.getDX(oomn6)).add(s.getDX(oomn6))).divide(4).round(oom);
        Math_BigRational dy = (p.getDY(oomn6).add(q.getDY(oomn6))
                .add(r.getDY(oomn6)).add(s.getDY(oomn6))).divide(4).round(oom);
        Math_BigRational dz = (p.getDZ(oomn6).add(q.getDZ(oomn6))
                .add(r.getDZ(oomn6)).add(s.getDZ(oomn6))).divide(4).round(oom);
        return new V3D_Point(e, offset, new V3D_Vector(dx, dy, dz));
    }

    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom) {
        if (V3D_Plane.checkOnSameSide(pt, getP(oom), getQsr().getN(oom), oom)) {
            if (V3D_Plane.checkOnSameSide(pt, getQ(oom), getSpr().getN(oom), oom)) {
                if (V3D_Plane.checkOnSameSide(pt, getR(oom), getPsq().getN(oom), oom)) {
                    if (V3D_Plane.checkOnSameSide(pt, getS(oom), getPqr().getN(oom), oom)) {
                        return true;
                    }
                }
            }
        }
        if (getQsr().isIntersectedBy(pt, oom)) {
            return true;
        }
        if (getSpr().isIntersectedBy(pt, oom)) {
            return true;
        }
        if (getPsq().isIntersectedBy(pt, oom)) {
            return true;
        }
        return getPqr().isIntersectedBy(pt, oom);
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        if (getPqr().isIntersectedBy(l, oom)) {
            return true;
        } else {
            if (getPsq().isIntersectedBy(l, oom)) {
                return true;
            } else {
                return getQsr().isIntersectedBy(l, oom);
//                // No need for the final test
//                if (getQsr().isIntersectedBy(l, oom)) {
//                    return true;
//                } else {
//                    return getSpr().isIntersectedBy(l, oom);
//                }
            }
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Ray r, int oom) {
        return r.isIntersectedBy(this, oom);
    }
    
    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom) {
        if (isIntersectedBy(l.getP(oom), oom)) {
            return true;
        } else {
            if (isIntersectedBy(l.getQ(oom), oom)) {
                return true;
            } else {
                if (getPqr().isIntersectedBy(l, oom)) {
                    return true;
                } else {
                    if (getPsq().isIntersectedBy(l, oom)) {
                        return true;
                    } else {
                        return getQsr().isIntersectedBy(l, oom);
//                        // No need for the final test
//                        if (getQsr().isIntersectedBy(l, oom)) {
//                            return true;
//                        } else {
//                            return getSpr().isIntersectedBy(l, oom);
//                        }
                    }
                }
            }
        }
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        V3D_Geometry pqri = getPqr().getIntersection(l, oom);
        if (pqri == null) {
            V3D_Geometry psqi = getPsq().getIntersection(l, oom);
            if (psqi == null) {
                return getQsr().getIntersection(l, oom);
            } else {
                return psqi;
            }
        } else if (pqri instanceof V3D_Point pqrip) {
            V3D_Geometry psqi = getPsq().getIntersection(l, oom);
            if (psqi == null) {
                V3D_Geometry qsri = getQsr().getIntersection(l, oom);
                if (qsri == null) {
                    return psqi;
                } else if (qsri instanceof V3D_Point qsrip) {
                    V3D_Geometry spri = getSpr().getIntersection(l, oom);
                    if (spri == null) {
                        return V3D_LineSegment.getGeometry(qsrip, pqrip);
                    } else if (spri instanceof V3D_Point sprip) {
                        return V3D_Triangle.getGeometry(qsrip, pqrip, sprip);
                    } else {
                        return spri;
                    }
                } else {
                    return qsri;
                }
            } else if (psqi instanceof V3D_Point psqip) {
                V3D_Geometry qsri = getQsr().getIntersection(l, oom);
                if (qsri == null) {
                    V3D_Geometry spri = getSpr().getIntersection(l, oom);
                    if (spri == null) {
                        return V3D_LineSegment.getGeometry(psqip, pqrip);
                    } else if (spri instanceof V3D_Point sprip) {
                        return V3D_Triangle.getGeometry(psqip, pqrip, sprip);
                    } else {
                        return spri;
                    }
                } else if (qsri instanceof V3D_Point qsrip) {
                    V3D_Geometry spri = getSpr().getIntersection(l, oom);
                    if (spri == null) {
                        return V3D_LineSegment.getGeometry(psqip, pqrip);
                    } else if (spri instanceof V3D_Point sprip) {
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
     * they are coplanar, a V3D_TriangleCoplanar is returned, otherwise a
     * tetrahedron is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @param s Another possibly equal point.
     * @return either {@code p} or {@code new V3D_LineSegment(p, q)} or
     * {@code new V3D_Triangle(p, q, r)}
     */
    public static V3D_Geometry getGeometry(V3D_Point p, V3D_Point q,
            V3D_Point r, V3D_Point s) {
        if (p.equals(q)) {
            return V3D_Triangle.getGeometry(p, r, s);
        } else {
            if (p.equals(r)) {
                return V3D_Triangle.getGeometry(p, q, s);
            } else {
                if (p.equals(s)) {
                    return V3D_Triangle.getGeometry(p, q, r);
                } else {
                    if (q.equals(r)) {
                        return V3D_Triangle.getGeometry(p, q, s);
                    } else {
                        if (q.equals(s)) {
                            return V3D_Triangle.getGeometry(p, q, r);
                        } else {
                            if (r.equals(s)) {
                                return V3D_Triangle.getGeometry(p, q, r);
                            } else {
                                if (V3D_Plane.isCoplanar(p.e, p, q, r, s)) {
                                    if (V3D_Line.isCollinear(p.e, p, q, r, s)) {
                                        return new V3D_LineSegment(p, q, r, s);
                                    }
                                    V3D_LineSegment pq = new V3D_LineSegment(p, q);
                                    V3D_LineSegment rs = new V3D_LineSegment(r, s);
                                    if (pq.isIntersectedBy(rs, p.e.oom)) {
                                        return new V3D_TrianglesCoplanar(
                                                new V3D_Triangle(p, q, r),
                                                new V3D_Triangle(p, q, s));
                                    } else {
                                        V3D_LineSegment pr = new V3D_LineSegment(p, r);
                                        V3D_LineSegment qs = new V3D_LineSegment(q, s);
                                        if (pr.isIntersectedBy(qs, p.e.oom)) {
                                            return new V3D_TrianglesCoplanar(
                                                    new V3D_Triangle(p, r, q),
                                                    new V3D_Triangle(p, r, s));
                                        } else {
                                            return new V3D_TrianglesCoplanar(
                                                    new V3D_Triangle(p, q, s),
                                                    new V3D_Triangle(p, r, s));
                                        }
                                    }
                                }
                                return new V3D_Tetrahedron(p, q, r, s);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * At least two of the points input are expected to be the same. If all
     * points are equal then the point is returned. If there are just two
     * different points then a line segment is returned. If 3 points are
     * different then a triangle is returned. If 4 points are different then if
     * they are coplanar, a V3D_TriangleCoplanar is returned, otherwise a
     * tetrahedron is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @param s Another possibly equal point.
     * @param t Another possibly equal point.
     * @return either {@code p} or {@code new V3D_LineSegment(p, q)} or
     * {@code new V3D_Triangle(p, q, r)}
     */
    protected static V3D_Geometry getGeometry(V3D_Point p, V3D_Point q,
            V3D_Point r, V3D_Point s, V3D_Point t) {
        if (p.equals(q)) {
            return getGeometry(p, r, s, t);
        } else {
            if (p.equals(r)) {
                return getGeometry(p, q, s, t);
            } else {
                if (p.equals(s)) {
                    return getGeometry(p, q, r, t);
                } else {
                    if (p.equals(t)) {
                        return getGeometry(p, q, r, s);
                    } else {
                        if (q.equals(r)) {
                            return getGeometry(p, q, s, t);
                        } else {
                            if (q.equals(s)) {
                                return getGeometry(p, q, r, t);
                            } else {
                                if (q.equals(t)) {
                                    return getGeometry(p, q, r, s);
                                } else {
                                    if (r.equals(s)) {
                                        return getGeometry(p, q, r, t);
                                    } else {
                                        if (r.equals(t)) {
                                            return getGeometry(p, q, r, s);
                                        } else {
                                            return getGeometry(p, q, r, t);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public V3D_Geometry getIntersection(V3D_Ray r, int oom) {
        return r.getIntersection(this, oom);
    }

    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom) {
        V3D_Geometry g = getIntersection(new V3D_Line(l), oom);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_Point gp) {
                if (isIntersectedBy(gp, oom)) {
                    return g;
                } else {
                    return null;
                }
            } else {
                V3D_Point lp = l.getP(oom);
                V3D_Point lq = l.getQ(oom);
                if (isIntersectedBy(lp, oom)) {
                    if (isIntersectedBy(lq, oom)) {
                        return (V3D_LineSegment) g.getIntersection(l, oom);
                    } else {
                        V3D_Geometry pqri = getPqr().getIntersection(l, oom);
                        if (pqri == null) {
                            V3D_Geometry psqi = getPsq().getIntersection(l, oom);
                            if (psqi == null) {
                                V3D_Geometry qsri = getQsr().getIntersection(l, oom);
                                if (qsri == null) {
                                    return new V3D_LineSegment(lp,
                                            (V3D_Point) getSpr().getIntersection(l, oom));
                                } else {
                                    if (qsri instanceof V3D_Point qsrip) {
                                        V3D_Geometry spri = getSpr().getIntersection(l, oom);
                                        if (spri == null) {
                                            return new V3D_LineSegment(lp, qsrip);
                                        } else {
                                            return V3D_Triangle.getGeometry(lp, qsrip, (V3D_Point) spri);
                                        }
                                    } else {
                                        return qsri;
                                    }
                                }
                            } else {
                                if (psqi instanceof V3D_Point psqip) {
                                    V3D_Geometry qsri = getQsr().getIntersection(l, oom);
                                    if (qsri == null) {
                                        V3D_Geometry spri = getSpr().getIntersection(l, oom);
                                        if (spri == null) {
                                            return new V3D_LineSegment(lp, psqip);
                                        } else {
                                            if (spri instanceof V3D_Point sprip) {
                                                return V3D_Triangle.getGeometry(lp, psqip, sprip);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        if (qsri instanceof V3D_Point qsrip) {
                                            V3D_Geometry spri = getSpr().getIntersection(l, oom);
                                            if (spri == null) {
                                                return V3D_Triangle.getGeometry(lp, psqip, qsrip);
                                            } else {
                                                if (spri instanceof V3D_Point sprip) {
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
                            if (pqri instanceof V3D_Point pqrip) {
                                V3D_Geometry psqi = getPsq().getIntersection(l, oom);
                                if (psqi == null) {
                                    V3D_Geometry qsri = getQsr().getIntersection(l, oom);
                                    if (qsri == null) {
                                        V3D_Geometry spri = getSpr().getIntersection(l, oom);
                                        if (spri == null) {
                                            return new V3D_LineSegment(lp, pqrip);
                                        } else {
                                            if (spri instanceof V3D_Point sprip) {
                                                return V3D_Triangle.getGeometry(lp, pqrip, sprip);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        if (qsri instanceof V3D_Point qsrip) {
                                            V3D_Geometry spri = getSpr().getIntersection(l, oom);
                                            if (spri == null) {
                                                return V3D_Triangle.getGeometry(lp, pqrip, qsrip);
                                            } else {
                                                if (spri instanceof V3D_Point sprip) {
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
                                    if (psqi instanceof V3D_Point psqip) {
                                        V3D_Geometry qsri = getQsr().getIntersection(l, oom);
                                        if (qsri == null) {
                                            V3D_Geometry spri = getSpr().getIntersection(l, oom);
                                            if (spri == null) {
                                                return V3D_Triangle.getGeometry(lp, pqrip, psqip);
                                            } else {
                                                if (spri instanceof V3D_Point sprip) {
                                                    return getGeometry(lp, pqrip, psqip, sprip);
                                                } else {
                                                    return spri;
                                                }
                                            }
                                        } else {
                                            if (qsri instanceof V3D_Point qsrip) {
                                                V3D_Geometry spri = getSpr().getIntersection(l, oom);
                                                if (spri == null) {
                                                    return getGeometry(lp, psqip, pqrip, qsrip);
                                                } else {
                                                    if (spri instanceof V3D_Point sprip) {
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
                    V3D_Geometry pqri = getPqr().getIntersection(l, oom);
                    if (pqri == null) {
                        V3D_Geometry psqi = getPsq().getIntersection(l, oom);
                        if (psqi == null) {
                            V3D_Geometry qsri = getQsr().getIntersection(l, oom);
                            if (qsri == null) {
                                return new V3D_LineSegment(lq,
                                        (V3D_Point) getSpr().getIntersection(l, oom));
                            } else {
                                if (qsri instanceof V3D_Point qsrip) {
                                    V3D_Geometry spri = getSpr().getIntersection(l, oom);
                                    if (spri == null) {
                                        return new V3D_LineSegment(lq, qsrip);
                                    } else {
                                        return V3D_Triangle.getGeometry(lq, qsrip, (V3D_Point) spri);
                                    }
                                } else {
                                    return qsri;
                                }
                            }
                        } else {
                            if (psqi instanceof V3D_Point psqip) {
                                V3D_Geometry qsri = getQsr().getIntersection(l, oom);
                                if (qsri == null) {
                                    V3D_Geometry spri = getSpr().getIntersection(l, oom);
                                    if (spri == null) {
                                        return new V3D_LineSegment(lq, psqip);
                                    } else {
                                        if (spri instanceof V3D_Point sprip) {
                                            return V3D_Triangle.getGeometry(lq, psqip, sprip);
                                        } else {
                                            return spri;
                                        }
                                    }
                                } else {
                                    if (qsri instanceof V3D_Point qsrip) {
                                        V3D_Geometry spri = getSpr().getIntersection(l, oom);
                                        if (spri == null) {
                                            return V3D_Triangle.getGeometry(lq, psqip, qsrip);
                                        } else {
                                            if (spri instanceof V3D_Point sprip) {
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
                        if (pqri instanceof V3D_Point pqrip) {
                            V3D_Geometry psqi = getPsq().getIntersection(l, oom);
                            if (psqi == null) {
                                V3D_Geometry qsri = getQsr().getIntersection(l, oom);
                                if (qsri == null) {
                                    V3D_Geometry spri = getSpr().getIntersection(l, oom);
                                    if (spri == null) {
                                        return new V3D_LineSegment(lq, pqrip);
                                    } else {
                                        if (spri instanceof V3D_Point sprip) {
                                            return V3D_Triangle.getGeometry(lq, pqrip, sprip);
                                        } else {
                                            return spri;
                                        }
                                    }
                                } else {
                                    if (qsri instanceof V3D_Point qsrip) {
                                        V3D_Geometry spri = getSpr().getIntersection(l, oom);
                                        if (spri == null) {
                                            return V3D_Triangle.getGeometry(lq, pqrip, qsrip);
                                        } else {
                                            if (spri instanceof V3D_Point sprip) {
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
                                if (psqi instanceof V3D_Point psqip) {
                                    V3D_Geometry qsri = getQsr().getIntersection(l, oom);
                                    if (qsri == null) {
                                        V3D_Geometry spri = getSpr().getIntersection(l, oom);
                                        if (spri == null) {
                                            return V3D_Triangle.getGeometry(lq, pqrip, psqip);
                                        } else {
                                            if (spri instanceof V3D_Point sprip) {
                                                return getGeometry(lq, pqrip, psqip, sprip);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        if (qsri instanceof V3D_Point qsrip) {
                                            V3D_Geometry spri = getSpr().getIntersection(l, oom);
                                            if (spri == null) {
                                                return getGeometry(lq, psqip, pqrip, qsrip);
                                            } else {
                                                if (spri instanceof V3D_Point sprip) {
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

    @Override
    public V3D_Geometry getIntersection(V3D_Plane p, int oom) {
        // The intersection will be null, a point, a line segment or a triangle...
        V3D_Geometry pqri = getPqr().getIntersection(p, oom);
        if (pqri == null) {
            V3D_Geometry psqi = getPsq().getIntersection(p, oom);
            if (psqi == null) {
                return null;
            } else {
                return psqi;
            }
        } else {
            if (pqri instanceof V3D_Point) {
                return pqri;
            } else if (pqri instanceof V3D_LineSegment pqril) {
                V3D_Geometry psqi = getPsq().getIntersection(p, oom);
                if (psqi == null) {
                    V3D_Geometry qsri = getQsr().getIntersection(p, oom);
                    if (qsri instanceof V3D_Point qsrip) {
                        return new V3D_Triangle(pqril, qsrip);
                    } else if (qsri instanceof V3D_LineSegment qsril) {
                        return V3D_Triangle.getGeometry(pqril, qsril);
                    } else {
                        return qsri;
                    }
                } else if (psqi instanceof V3D_Point psqip) {
                    return new V3D_Triangle(pqril, psqip);
                } else if (psqi instanceof V3D_LineSegment psqil) {
                    return V3D_Triangle.getGeometry(pqril, psqil);
                } else {
                    return psqi;
                }
            } else {
                return pqri;
            }
        }
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Triangle t, int oom) {
        // The intersection will be null, a point, a line segment, a triangle, quadrilateral, pentagon or hexagon...
        V3D_Geometry pi = getIntersection(new V3D_Plane(t), oom);
        if (pi == null) {
            return null;
        } else {
            if (pi instanceof V3D_Point pip) {
                if (t.isIntersectedBy(pip, oom)) {
                    return pi;
                } else {
                    return null;
                }
            } else if (pi instanceof V3D_LineSegment pil) {
                return t.getIntersection(pil, oom);
            } else {
                return ((V3D_Triangle) pi).getIntersection(t, oom);
            }
        }
    }

    //@Override
    public V3D_Geometry getIntersection(V3D_Rectangle r, int oom) {
        V3D_Triangle r_pqr = r.getPQR();
        V3D_Triangle r_rsp = r.getRSP();
        V3D_Geometry i_pqr = getIntersection(r_pqr, oom);
        V3D_Geometry i_rsp = getIntersection(r_rsp, oom);
        /** 
         * The intersections will be null, a point, a line segment, a triangle, 
         * a quadrilateral, a pentagon or a hexagon. 
         * 
         */
        if (i_pqr == null) {
            return i_rsp;
        } else {
            if (i_pqr instanceof V3D_Point i_pqr_p) {
                if (i_rsp == null) {
                    return i_pqr_p;
                } else {
                    return i_rsp;
                }
            } else if (i_pqr instanceof V3D_LineSegment) {
                if (i_rsp == null) {
                    return i_pqr;
                } else if (i_rsp instanceof V3D_Point) {
                    return i_pqr;
                } else {
                    return i_rsp;
                }
            } else if (i_pqr instanceof V3D_Triangle i_pqr_t) {
                if (i_rsp == null) {
                    return i_pqr_t;
                } else if (i_rsp instanceof V3D_Point) {
                    return i_pqr_t;
                } else if (i_rsp instanceof V3D_LineSegment) {
                    return i_pqr_t;
                } else if (i_rsp instanceof V3D_Triangle i_rsp_t) {
                    
                    return new V3D_TrianglesCoplanar(i_pqr_t, i_rsp_t);
                } else if (i_rsp instanceof V3D_Triangle i_rsp_t) {
                    return new V3D_TrianglesCoplanar(i_pqr_t, i_rsp_t);
                    
                }
                if (t.isIntersectedBy(pip, oom)) {
                    return pi;
                } else {
                    return null;
                }
            } else if (pi instanceof V3D_LineSegment pil) {
                return t.getIntersection(pil, oom);
            } else {
                return ((V3D_Triangle) pi).getIntersection(t, oom);
            }
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Point pt, int oom) {
        if (isIntersectedBy(pt, oom)) {
            return Math_BigRational.ZERO;
        } else {
            return Math_BigRational.min(
                    getPqr().getDistanceSquared(pt, oom),
                    getPsq().getDistanceSquared(pt, oom),
                    getQsr().getDistanceSquared(pt, oom),
                    getSpr().getDistanceSquared(pt, oom));
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
    }

    @Override
    public BigDecimal getDistance(V3D_Ray r, int oom) {
        return r.getDistance(this, oom);
    }
    
    @Override
    public Math_BigRational getDistanceSquared(V3D_Ray r, int oom) {
        return r.getDistanceSquared(this, oom);
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
    }
    
    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom) {
        if (isIntersectedBy(l, oom)) {
            return Math_BigRational.ZERO;
        } else {
            return Math_BigRational.min(
                    getPqr().getDistanceSquared(l, oom),
                    getPsq().getDistanceSquared(l, oom),
                    getQsr().getDistanceSquared(l, oom),
                    getSpr().getDistanceSquared(l, oom));
        }
    }

    /**
     * Change {@link #offset} without changing the overall line.
     *
     * @param offset What {@link #offset} is set to.
     */
    public void setOffset(V3D_Vector offset) {
        p = p.add(offset, e.oom).subtract(this.offset, e.oom);
        q = q.add(offset, e.oom).subtract(this.offset, e.oom);
        r = r.add(offset, e.oom).subtract(this.offset, e.oom);
        s = s.add(offset, e.oom).subtract(this.offset, e.oom);
        this.offset = offset;
    }

    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta) {
        p = p.rotate(axisOfRotation, theta, e.bI, e.oom);
        q = q.rotate(axisOfRotation, theta, e.bI, e.oom);
        r = r.rotate(axisOfRotation, theta, e.bI, e.oom);
        s = s.rotate(axisOfRotation, theta, e.bI, e.oom);
        pqr = null;
        psq = null;
        spr = null;
        qsr = null;
        en = null;
    }

    @Override
    public boolean isIntersectedBy(V3D_Plane pl, int oom) {
        if (getPqr().isIntersectedBy(pl, oom)) {
            return true;
        } else {
            return getPsq().isIntersectedBy(pl, oom);
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Triangle t, int oom) {
        V3D_Plane pl = new V3D_Plane(t);
        if (isIntersectedBy(pl, oom)) {
            if (isIntersectedBy(t.getP(), oom) || isIntersectedBy(t.getQ(), oom)
                    || isIntersectedBy(t.getR(), oom)) {
                return true;
            } else {
                if (getPqr().isIntersectedBy(t, oom)) {
                    return true;
                } else {
                    if (getPsq().isIntersectedBy(t, oom)) {
                        return true;
                    } else {
                        if (getQsr().isIntersectedBy(t, oom)) {
                            return true;
                        } else {
                            if (getSpr().isIntersectedBy(t, oom)) {
                                return true;
                            } else {
                                /**
                                 * The points of t are around the outside of
                                 * this, but none of the edges intersect. The
                                 * intersection of the plane of t and this can
                                 * give a point or a triangle. If that point or
                                 * triangle intersects t, then t intersects this
                                 * otherwise it does not.
                                 */
                                V3D_Geometry g = getIntersection(pl, oom);
                                if (g instanceof V3D_Point gp) {
                                    return t.isIntersectedBy(gp, oom);
                                } else {
                                    return t.isIntersectedBy((V3D_Triangle) g, oom);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom) {
        if (getEnvelope().isIntersectedBy(t.getEnvelope())) {
            // Check points
            if (isIntersectedBy(t.getP(oom), oom)) {
                return true;
            }
            if (isIntersectedBy(t.getQ(oom), oom)) {
                return true;
            }
            if (isIntersectedBy(t.getR(oom), oom)) {
                return true;
            }
            if (isIntersectedBy(t.getS(oom), oom)) {
                return true;
            }
            if (t.isIntersectedBy(getP(oom), oom)) {
                return true;
            }
            if (t.isIntersectedBy(getQ(oom), oom)) {
                return true;
            }
            if (t.isIntersectedBy(getR(oom), oom)) {
                return true;
            }
            if (t.isIntersectedBy(getS(oom), oom)) {
                return true;
            }
            // Check faces
            if (isIntersectedBy(t.getPqr(), oom)) {
                return true;
            }
            if (isIntersectedBy(t.getPsq(), oom)) {
                return true;
            }
            if (isIntersectedBy(t.getQsr(), oom)) {
                return true;
            }
            if (isIntersectedBy(t.getSpr(), oom)) {
                return true;
            }
            if (t.isIntersectedBy(getPqr(), oom)) {
                return true;
            }
            if (t.isIntersectedBy(getPsq(), oom)) {
                return true;
            }
            if (t.isIntersectedBy(getQsr(), oom)) {
                return true;
            }
            if (t.isIntersectedBy(getSpr(), oom)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Tetrahedron t, int oom) {
        V3D_Geometry pqri = getIntersection(t.getPqr(), oom);
        if (pqri == null) {
            V3D_Geometry psqi = getIntersection(t.getPsq(), oom);
            if (psqi == null) {
                V3D_Geometry qsri = getIntersection(t.getQsr(), oom);
                if (qsri == null) {
                    V3D_Geometry spri = getIntersection(t.getSpr(), oom);
                    if (spri == null) {
                        V3D_Geometry tpqri = t.getIntersection(getPqr(), oom);
                        if (tpqri == null) {
                            V3D_Geometry tpsqi = t.getIntersection(getPsq(), oom);
                            if (tpsqi == null) {
                                V3D_Geometry tqsri = t.getIntersection(getQsr(), oom);
                                if (tqsri == null) {
                                    V3D_Geometry tspri = t.getIntersection(getSpr(), oom);
                                    if (tspri == null) {
                                        if (isIntersectedBy(t.getP(oom), oom)
                                                && isIntersectedBy(t.getQ(oom), oom)
                                                && isIntersectedBy(t.getR(oom), oom)
                                                && isIntersectedBy(t.getS(oom), oom)) {
                                            return t;
                                        } else {
                                            if (t.isIntersectedBy(getP(oom), oom)
                                                    && t.isIntersectedBy(getQ(oom), oom)
                                                    && t.isIntersectedBy(getR(oom), oom)
                                                    && t.isIntersectedBy(getS(oom), oom)) {
                                                return this;
                                            } else {
                                                return null;
                                            }
                                        }
                                    } else {
                                        // Has to be a point.
                                        return tspri;
                                    }
                                } else if (tqsri instanceof V3D_Point) {
                                    return tqsri;
                                } else if (tqsri instanceof V3D_LineSegment) {
                                    return tqsri;
                                } else if (tqsri instanceof V3D_Triangle) {
                                    /**
                                     * Triangle: Find which point is on the
                                     * other side of the triangle plane. In this
                                     * case, this is the only point of
                                     *
                                     */
                                } else {
                                    // V3D_TriangleCoplanar
                                }
                            } else if (tpsqi instanceof V3D_Point) {
                                return tpsqi;
                            } else if (tpsqi instanceof V3D_LineSegment) {
                                return tpsqi;
                            } else if (tpsqi instanceof V3D_Triangle) {
                                /**
                                 * Triangle: Find which point is on the other
                                 * side of the triangle plane. In this case,
                                 * this is the only point of
                                 *
                                 */
                            } else {
                                // V3D_TriangleCoplanar
                            }
                        }
                    }
                }
            }
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom) {
        if (isIntersectedBy(l, oom)) {
            return Math_BigRational.ZERO;
        } else {
            return Math_BigRational.min(
                    getPqr().getDistanceSquared(l, oom),
                    getPsq().getDistanceSquared(l, oom),
                    getQsr().getDistanceSquared(l, oom),
                    getSpr().getDistanceSquared(l, oom));
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Plane p, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Plane p, int oom) {
        if (isIntersectedBy(p, oom)) {
            return Math_BigRational.ZERO;
        } else {
            return Math_BigRational.min(
                    getPqr().getDistanceSquared(p, oom),
                    getPsq().getDistanceSquared(p, oom),
                    getQsr().getDistanceSquared(p, oom),
                    getSpr().getDistanceSquared(p, oom));
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(t, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom) {
        if (isIntersectedBy(t, oom)) {
            return Math_BigRational.ZERO;
        } else {
            return Math_BigRational.min(
                    getPqr().getDistanceSquared(t, oom),
                    getPsq().getDistanceSquared(t, oom),
                    getQsr().getDistanceSquared(t, oom),
                    getSpr().getDistanceSquared(t, oom));
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(t, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom) {
        if (isIntersectedBy(t, oom)) {
            return Math_BigRational.ZERO;
        } else {
            return Math_BigRational.min(
                    getPqr().getDistanceSquared(t, oom),
                    getPsq().getDistanceSquared(t, oom),
                    getQsr().getDistanceSquared(t, oom),
                    getSpr().getDistanceSquared(t, oom));
        }
    }
}
