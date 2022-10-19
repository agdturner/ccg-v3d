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
import java.math.RoundingMode;
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
  pl *- - - - - - - - - - - + - - - - - - - - - - -* q
     \ ~                                         /|
      \     ~                                   / |
       \         ~                             /  |
        \             ~                       /   |
         \                 ~                 /    |
          \                      ~          /     |
           \                          ~    /      |
            \                             / ~     |
             \                           /       ~s
              \                         /        :
               \                       /
                \                     /      :
                 \                   /
                  \                 /     :
                   \               /
                    \             /    :
                     \           /
                      \         /   :
                       \       /
                        \     /  :
                         \   /
                          \ /:
                           *
                           r
 }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Tetrahedron extends V3D_FiniteGeometry implements V3D_Volume {

    private static final long serialVersionUID = 1L;

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
     * Create a new instance. {@code pl}, {@code q}, {@code r} and {@code s} must
     * all be different, not the zero vector and collectively they must be three
     * dimensional. This is generally the fastest way to construct a
     * tetrahedron.
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
     * Create a new instance. {@code pl}, {@code q}, {@code r} and {@code s} must
     * all be different and not coplanar. No test is done to check these things.
     *
     * @param p Used to set {@link #p}, {@link #e} and {@link #offset}.
     * @param q Used to set {@link #q}.
     * @param r Used to set {@link #r}.
     * @param s Used to set {@link #s}.
     */
    public V3D_Tetrahedron(V3D_Point p, V3D_Point q, V3D_Point r, V3D_Point s,
            int oom, RoundingMode rm) {
        super(p.e, p.offset);
        this.p = new V3D_Vector(p.rel);
        V3D_Point qp = new V3D_Point(q);
        qp.setOffset(offset, oom, rm);
        this.q = qp.rel;
        V3D_Point rp = new V3D_Point(r);
        rp.setOffset(offset, oom, rm);
        this.r = rp.rel;
        V3D_Point sp = new V3D_Point(s);
        sp.setOffset(offset, oom, rm);
        this.s = sp.rel;
    }

    /**
     * Create a new instance. {@code pl}, must not be coplanar to t. No test is
     * done to check this is the case.
     *
     * @param p Used to set {@link #p}, {@link #e} and {@link #offset}.
     * @param t Used to set {@link #q}, {@link #r} and {@link #s}.
     */
    public V3D_Tetrahedron(V3D_Point p, V3D_Triangle t, int oom, RoundingMode rm) {
        this(p, t.getP(), t.getQ(), t.getR(), oom, rm);
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
        st += pad + "r=" + this.r.toString(pad) + "\n";
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
        st += pad + "r=" + this.r.toStringSimple(pad) + ",\n";
        st += pad + "s=" + s.toStringSimple(pad);
        return st;
    }

    @Override
    public V3D_Envelope getEnvelope(int oom, RoundingMode rm) {
        if (en == null) {
//            en = pqr.getEnvelope(oom).union(qsr.getEnvelope(oom));
            en = getP().getEnvelope(oom, rm)
                    .union(getQ().getEnvelope(oom, rm), oom, rm)
                    .union(getR().getEnvelope(oom, rm), oom, rm)
                    .union(getS().getEnvelope(oom, rm), oom, rm);
        }
        return en;
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getP() {
        return new V3D_Point(e, offset, p);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getQ() {
        return new V3D_Point(e, offset, q);
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getR() {
        return new V3D_Point(e, offset, r);
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getS() {
        return new V3D_Point(e, offset, s);
    }

    /**
     * If {@code null} initialise {@link #pqr} and return it.
     *
     * @return {@link #pqr}
     */
    public V3D_Triangle getPqr(int oom, RoundingMode rm) {
        if (pqr == null) {
            pqr = new V3D_Triangle(e, offset, p, q, r, oom, rm);
        }
        return pqr;
    }

    /**
     * If {@code null} initialise {@link #qsr} and return it.
     *
     * @return {@link #qsr}
     */
    public V3D_Triangle getQsr(int oom, RoundingMode rm) {
        if (qsr == null) {
            qsr = new V3D_Triangle(e, offset, q, s, r, oom, rm);
        }
        return qsr;
    }

    /**
     * If {@code null} initialise {@link #spr} and return it.
     *
     * @return {@link #spr}
     */
    public V3D_Triangle getSpr(int oom, RoundingMode rm) {
        if (spr == null) {
            spr = new V3D_Triangle(e, offset, s, p, r, oom, rm);
        }
        return spr;
    }

    /**
     * If {@code null} initialise {@link #psq} and return it.
     *
     * @return {@link #psq}
     */
    public V3D_Triangle getPsq(int oom, RoundingMode rm) {
        if (psq == null) {
            psq = new V3D_Triangle(e, offset, p, s, q, oom, rm);
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
    public BigDecimal getArea(int oom, RoundingMode rm) {
        return getPqr(oom, rm).getArea(oom, rm)
                .add(getQsr(oom, rm).getArea(oom, rm))
                .add(getSpr(oom, rm).getArea(oom, rm))
                .add(getPsq(oom, rm).getArea(oom, rm));
    }

    /**
     * Calculate and return the volume.
     * https://en.wikipedia.org/wiki/Tetrahedron#Volume This implementation is
     * currently a bit rough and ready.
     *
     * @param oom The Order of Magnitude for the precision of the calculation.
     */
    @Override
    public BigDecimal getVolume(int oom, RoundingMode rm) {
        V3D_Triangle tpqr = getPqr(oom, rm);
        V3D_Point ts = getS();
        int oomn6 = oom - 6;
        BigDecimal hd3 = new Math_BigRationalSqrt(
                tpqr.pl.getPointOfProjectedIntersection(ts, oom, rm)
                        .getDistanceSquared(ts, oomn6, rm), oomn6, rm)
                .getSqrt(oom, rm).divide(3).toBigDecimal(oomn6);
        return Math_BigDecimal.round(tpqr.getArea(oom - 3, rm).multiply(hd3), oom, rm);
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
    public V3D_Point getCentroid(int oom, RoundingMode rm) {
        oom -= 6;
        Math_BigRational dx = (p.getDX(oom, rm).add(q.getDX(oom, rm))
                .add(r.getDX(oom, rm)).add(s.getDX(oom, rm))).divide(4).round(oom, rm);
        Math_BigRational dy = (p.getDY(oom, rm).add(q.getDY(oom, rm))
                .add(r.getDY(oom, rm)).add(s.getDY(oom, rm))).divide(4).round(oom, rm);
        Math_BigRational dz = (p.getDZ(oom, rm).add(q.getDZ(oom, rm))
                .add(r.getDZ(oom, rm)).add(s.getDZ(oom, rm))).divide(4).round(oom, rm);
        return new V3D_Point(e, offset, new V3D_Vector(dx, dy, dz));
    }

    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom, RoundingMode rm) {
        pqr = getPqr(oom, rm);
        psq = getPsq(oom, rm);
        spr = getSpr(oom, rm);
        qsr = getQsr(oom, rm);
        if (pqr.pl.isOnSameSide(pt, getS(), oom, rm)) {
            if (psq.pl.isOnSameSide(pt, getR(), oom, rm)) {
                if (spr.pl.isOnSameSide(pt, getQ(), oom, rm)) {
                    if (qsr.pl.isOnSameSide(pt, getP(), oom, rm)) {
                        return true;
                    }
                }
            }
        }
        if (qsr.isIntersectedBy(pt, oom, rm)) {
            return true;
        }
        if (spr.isIntersectedBy(pt, oom, rm)) {
            return true;
        }
        if (psq.isIntersectedBy(pt, oom, rm)) {
            return true;
        }
        return pqr.isIntersectedBy(pt, oom, rm);
    }

//    @Override
//    public boolean isIntersectedBy(V3D_Line l, int oom, RoundingMode rm) {
//        if (getPqr(oom, rm).isIntersectedBy(l, oom, rm)) {
//            return true;
//        } else {
//            if (getPsq(oom, rm).isIntersectedBy(l, oom, rm)) {
//                return true;
//            } else {
//                return getQsr(oom, rm).isIntersectedBy(l, oom, rm);
////                // No need for the final test
////                if (getQsr(oom, rm).isIntersectedBy(l, oom, rm)) {
////                    return true;
////                } else {
////                    return getSpr(oom, rm).isIntersectedBy(l, oom, rm);
////                }
//            }
//        }
//    }

//    @Override
//    public boolean isIntersectedBy(V3D_Ray r, int oom, RoundingMode rm) {
//        return r.isIntersectedBy(this, oom, rm);
//    }
//
//    @Override
//    public boolean isIntersectedBy(V3D_LineSegment l, int oom, RoundingMode rm) {
//        if (isIntersectedBy(l.getP(), oom, rm)) {
//            return true;
//        } else {
//            if (isIntersectedBy(l.getQ(), oom, rm)) {
//                return true;
//            } else {
//                if (getPqr(oom, rm).isIntersectedBy(l, oom, rm)) {
//                    return true;
//                } else {
//                    if (getPsq(oom, rm).isIntersectedBy(l, oom, rm)) {
//                        return true;
//                    } else {
//                        return getQsr(oom, rm).isIntersectedBy(l, oom, rm);
////                        // No need for the final test
////                        if (getQsr(oom, rm).isIntersectedBy(l, oom, rm)) {
////                            return true;
////                        } else {
////                            return getSpr(oom, rm).isIntersectedBy(l, oom, rm);
////                        }
//                    }
//                }
//            }
//        }
//    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Line l, int oom, RoundingMode rm) {
        V3D_FiniteGeometry pqri = getPqr(oom, rm).getIntersection(l, oom, rm);
        if (pqri == null) {
            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(l, oom, rm);
            if (psqi == null) {
                return getQsr(oom, rm).getIntersection(l, oom, rm);
            } else {
                return psqi;
            }
        } else if (pqri instanceof V3D_Point pqrip) {
            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(l, oom, rm);
            if (psqi == null) {
                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                if (qsri == null) {
                    return null;
                } else if (qsri instanceof V3D_Point qsrip) {
                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                    if (spri == null) {
                        return V3D_LineSegment.getGeometry(qsrip, pqrip, oom, rm);
                    } else if (spri instanceof V3D_Point sprip) {
                        return V3D_Triangle.getGeometry(qsrip, pqrip, sprip, oom, rm);
                    } else {
                        return spri;
                    }
                } else {
                    return qsri;
                }
            } else if (psqi instanceof V3D_Point psqip) {
                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                if (qsri == null) {
                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                    if (spri == null) {
                        return V3D_LineSegment.getGeometry(psqip, pqrip, oom, rm);
                    } else if (spri instanceof V3D_Point sprip) {
                        return V3D_Triangle.getGeometry(psqip, pqrip, sprip, oom, rm);
                    } else {
                        return spri;
                    }
                } else if (qsri instanceof V3D_Point qsrip) {
                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                    if (spri == null) {
                        return V3D_LineSegment.getGeometry(psqip, pqrip, oom, rm);
                    } else if (spri instanceof V3D_Point sprip) {
                        return getGeometry(pqrip, psqip, qsrip, sprip, oom, rm);
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
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, q)} or
     * {@code new V3D_Triangle(pl, q, r)}
     */
    public static V3D_FiniteGeometry getGeometry(V3D_Point p, V3D_Point q,
            V3D_Point r, V3D_Point s, int oom, RoundingMode rm) {
        if (p.equals(q, oom, rm)) {
            return V3D_Triangle.getGeometry(p, r, s, oom, rm);
        } else if (p.equals(r, oom, rm)) {
            return V3D_Triangle.getGeometry(p, q, s, oom, rm);
        } else if (p.equals(s, oom, rm)) {
            return V3D_Triangle.getGeometry(p, q, r, oom, rm);
        } else if (q.equals(r, oom, rm)) {
            return V3D_Triangle.getGeometry(p, q, s, oom, rm);
        } else if (q.equals(s, oom, rm)) {
            return V3D_Triangle.getGeometry(p, q, r, oom, rm);
        } else if (r.equals(s, oom, rm)) {
            return V3D_Triangle.getGeometry(p, q, r, oom, rm);
        } else {
            if (V3D_Plane.isCoplanar(p.e, oom, rm, p, q, r, s)) {
                if (V3D_Line.isCollinear(p.e, oom, rm, p, q, r, s)) {
                    //return V3D_Line(pl, q, r, s);
                    throw new UnsupportedOperationException("Need code to construct a line segment from 4 points!");
                }
                V3D_LineSegment pq = new V3D_LineSegment(p, q, oom, rm);
                V3D_LineSegment rs = new V3D_LineSegment(r, s, oom, rm);
                if (pq.getIntersection(rs, oom, rm) != null) {
                    return new V3D_Polygon(
                            new V3D_Triangle(p, q, r, oom, rm),
                            new V3D_Triangle(p, q, s, oom, rm));
                } else {
                    V3D_LineSegment pr = new V3D_LineSegment(p, r, oom, rm);
                    V3D_LineSegment qs = new V3D_LineSegment(q, s, oom, rm);
                    if (pr.getIntersection(qs, oom, rm) != null) {
                        return new V3D_Polygon(
                                new V3D_Triangle(p, r, q, oom, rm),
                                new V3D_Triangle(p, r, s, oom, rm));
                    } else {
                        return new V3D_Polygon(
                                new V3D_Triangle(p, q, s, oom, rm),
                                new V3D_Triangle(p, r, s, oom, rm));
                    }
                }
            }
            return new V3D_Tetrahedron(p, q, r, s, oom, rm);
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
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, q)} or
     * {@code new V3D_Triangle(pl, q, r)}
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_Point p, V3D_Point q,
            V3D_Point r, V3D_Point s, V3D_Point t, int oom, RoundingMode rm) {
        if (p.equals(q, oom, rm)) {
            return getGeometry(p, r, s, t, oom, rm);
        } else if (p.equals(r, oom, rm)) {
            return getGeometry(p, q, s, t, oom, rm);
        } else if (p.equals(s, oom, rm)) {
            return getGeometry(p, q, r, t, oom, rm);
        } else if (p.equals(t, oom, rm)) {
            return getGeometry(p, q, r, s, oom, rm);
        } else if (q.equals(r, oom, rm)) {
            return getGeometry(p, q, s, t, oom, rm);
        } else if (q.equals(s, oom, rm)) {
            return getGeometry(p, q, r, t, oom, rm);
        } else if (q.equals(t, oom, rm)) {
            return getGeometry(p, q, r, s, oom, rm);
        } else if (r.equals(s, oom, rm)) {
            return getGeometry(p, q, r, t, oom, rm);
        } else if (r.equals(t, oom, rm)) {
            return getGeometry(p, q, r, s, oom, rm);
        } else {
            return getGeometry(p, q, r, t, oom, rm);
        }
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Ray r, int oom, RoundingMode rm) {
        return r.getIntersection(this, oom, rm);
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment l, int oom,
            RoundingMode rm) {
        V3D_FiniteGeometry g = getIntersection(l.l, oom, rm);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_Point gp) {
                if (isIntersectedBy(gp, oom, rm)) {
                    return g;
                } else {
                    return null;
                }
            } else {
                V3D_Point lp = l.getP();
                V3D_Point lq = l.getQ();
                if (isIntersectedBy(lp, oom, rm)) {
                    if (isIntersectedBy(lq, oom, rm)) {
                        return (V3D_LineSegment) g.getIntersection(l, oom, rm);
                    } else {
                        V3D_FiniteGeometry pqri = getPqr(oom, rm).getIntersection(l, oom, rm);
                        if (pqri == null) {
                            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(l, oom, rm);
                            if (psqi == null) {
                                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                                if (qsri == null) {
                                    return new V3D_LineSegment(lp,
                                            (V3D_Point) getSpr(oom, rm).getIntersection(l, oom, rm), oom, rm);
                                } else {
                                    if (qsri instanceof V3D_Point qsrip) {
                                        V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                        if (spri == null) {
                                            return new V3D_LineSegment(lp, qsrip, oom, rm);
                                        } else {
                                            return V3D_Triangle.getGeometry(lp, qsrip, (V3D_Point) spri, oom, rm);
                                        }
                                    } else {
                                        return qsri;
                                    }
                                }
                            } else {
                                if (psqi instanceof V3D_Point psqip) {
                                    V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                                    if (qsri == null) {
                                        V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                        if (spri == null) {
                                            return new V3D_LineSegment(lp, psqip, oom, rm);
                                        } else {
                                            if (spri instanceof V3D_Point sprip) {
                                                return V3D_Triangle.getGeometry(lp, psqip, sprip, oom, rm);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        if (qsri instanceof V3D_Point qsrip) {
                                            V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                            if (spri == null) {
                                                return V3D_Triangle.getGeometry(lp, psqip, qsrip, oom, rm);
                                            } else {
                                                if (spri instanceof V3D_Point sprip) {
                                                    return getGeometry(lp, psqip, qsrip, sprip, oom, rm);
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
                                V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(l, oom, rm);
                                if (psqi == null) {
                                    V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                                    if (qsri == null) {
                                        V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                        if (spri == null) {
                                            return new V3D_LineSegment(lp, pqrip, oom, rm);
                                        } else {
                                            if (spri instanceof V3D_Point sprip) {
                                                return V3D_Triangle.getGeometry(lp, pqrip, sprip, oom, rm);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        if (qsri instanceof V3D_Point qsrip) {
                                            V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                            if (spri == null) {
                                                return V3D_Triangle.getGeometry(lp, pqrip, qsrip, oom, rm);
                                            } else {
                                                if (spri instanceof V3D_Point sprip) {
                                                    return getGeometry(lp, pqrip, qsrip, sprip, oom, rm);
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
                                        V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                                        if (qsri == null) {
                                            V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                            if (spri == null) {
                                                return V3D_Triangle.getGeometry(lp, pqrip, psqip, oom, rm);
                                            } else {
                                                if (spri instanceof V3D_Point sprip) {
                                                    return getGeometry(lp, pqrip, psqip, sprip, oom, rm);
                                                } else {
                                                    return spri;
                                                }
                                            }
                                        } else {
                                            if (qsri instanceof V3D_Point qsrip) {
                                                V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                                if (spri == null) {
                                                    return getGeometry(lp, psqip, pqrip, qsrip, oom, rm);
                                                } else {
                                                    if (spri instanceof V3D_Point sprip) {
                                                        return getGeometry(lp, psqip, pqrip, qsrip, sprip, oom, rm);
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
                    V3D_FiniteGeometry pqri = getPqr(oom, rm).getIntersection(l, oom, rm);
                    if (pqri == null) {
                        V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(l, oom, rm);
                        if (psqi == null) {
                            V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                            if (qsri == null) {
                                return new V3D_LineSegment(lq,
                                        (V3D_Point) getSpr(oom, rm).getIntersection(l, oom, rm), oom, rm);
                            } else {
                                if (qsri instanceof V3D_Point qsrip) {
                                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                    if (spri == null) {
                                        return new V3D_LineSegment(lq, qsrip, oom, rm);
                                    } else {
                                        return V3D_Triangle.getGeometry(lq, qsrip, (V3D_Point) spri, oom, rm);
                                    }
                                } else {
                                    return qsri;
                                }
                            }
                        } else {
                            if (psqi instanceof V3D_Point psqip) {
                                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                                if (qsri == null) {
                                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                    if (spri == null) {
                                        return new V3D_LineSegment(lq, psqip, oom, rm);
                                    } else {
                                        if (spri instanceof V3D_Point sprip) {
                                            return V3D_Triangle.getGeometry(lq, psqip, sprip, oom, rm);
                                        } else {
                                            return spri;
                                        }
                                    }
                                } else {
                                    if (qsri instanceof V3D_Point qsrip) {
                                        V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                        if (spri == null) {
                                            return V3D_Triangle.getGeometry(lq, psqip, qsrip, oom, rm);
                                        } else {
                                            if (spri instanceof V3D_Point sprip) {
                                                return getGeometry(lq, psqip, qsrip, sprip, oom, rm);
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
                            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(l, oom, rm);
                            if (psqi == null) {
                                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                                if (qsri == null) {
                                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                    if (spri == null) {
                                        return new V3D_LineSegment(lq, pqrip, oom, rm);
                                    } else {
                                        if (spri instanceof V3D_Point sprip) {
                                            return V3D_Triangle.getGeometry(lq, pqrip, sprip, oom, rm);
                                        } else {
                                            return spri;
                                        }
                                    }
                                } else {
                                    if (qsri instanceof V3D_Point qsrip) {
                                        V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                        if (spri == null) {
                                            return V3D_Triangle.getGeometry(lq, pqrip, qsrip, oom, rm);
                                        } else {
                                            if (spri instanceof V3D_Point sprip) {
                                                return getGeometry(lq, pqrip, qsrip, sprip, oom, rm);
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
                                    V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                                    if (qsri == null) {
                                        V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                        if (spri == null) {
                                            return V3D_Triangle.getGeometry(lq, pqrip, psqip, oom, rm);
                                        } else {
                                            if (spri instanceof V3D_Point sprip) {
                                                return getGeometry(lq, pqrip, psqip, sprip, oom, rm);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        if (qsri instanceof V3D_Point qsrip) {
                                            V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                            if (spri == null) {
                                                return getGeometry(lq, psqip, pqrip, qsrip, oom, rm);
                                            } else {
                                                if (spri instanceof V3D_Point sprip) {
                                                    return getGeometry(lq, psqip, pqrip, qsrip, sprip, oom, rm);
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
     * The intersection will be null, a point, a line segment, a triangle or a
     * quadrilateral. It should be that any points of intersection are within
     * this.
     */
    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Plane p, int oom, RoundingMode rm) {
        V3D_FiniteGeometry pqri = getPqr(oom, rm).getIntersection(p, oom, rm);
        if (pqri == null) {
            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(p, oom, rm);
            if (psqi == null) {
                return null;
            } else if (psqi instanceof V3D_Point psqip) {
                // psqip must be the point s!
                return psqip;
            } else if (psqi instanceof V3D_LineSegment psqil) {
                /**
                 * There will also be a line segment of qsr and spr that
                 * together form a triangle.
                 */
                V3D_LineSegment qsril = (V3D_LineSegment) getQsr(oom, rm).getIntersection(p, oom, rm);
                V3D_LineSegment spril = (V3D_LineSegment) getSpr(oom, rm).getIntersection(p, oom, rm);
                return V3D_Triangle.getGeometry(psqil, qsril, spril, oom, rm);
            } else {
                // Triangle
                return (V3D_Triangle) psqi;
            }
        } else if (pqri instanceof V3D_Point pqrip) {
            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(p, oom, rm);
            if (psqi == null) {
                return pqrip;
            } else if (psqi instanceof V3D_Point psqip) {
                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(p, oom, rm);
                if (qsri == null) {
                    return psqip;
                } else {
                    return V3D_Triangle.getGeometry((V3D_LineSegment) qsri, psqip, oom, rm);
                }
            } else if (psqi instanceof V3D_LineSegment psqil) {
                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(p, oom, rm);
                if (qsri == null) {
                    return psqil;
                } else {
                    if (qsri instanceof V3D_Point) {
                        return psqil;
                    } else {
                        //V3D_LineSegment spri = (V3D_LineSegment) getSpr(oom, rm).getIntersection(pl, oom, rm);
                        return V3D_Triangle.getGeometry2(psqil, (V3D_LineSegment) qsri, oom, rm);
                    }
                }
            } else {
                // Triangle
                return (V3D_Triangle) psqi;
            }
        } else if (pqri instanceof V3D_LineSegment pqril) {
            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(p, oom, rm);
            if (psqi == null) {
                V3D_LineSegment qsril = (V3D_LineSegment) getQsr(oom, rm).getIntersection(p, oom, rm);
                V3D_LineSegment spril = (V3D_LineSegment) getSpr(oom, rm).getIntersection(p, oom, rm);
                return V3D_Triangle.getGeometry(pqril, qsril, spril, oom, rm);
            } else if (psqi instanceof V3D_Point) {
                V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(p, oom, rm);
                if (spri instanceof V3D_Point sprip) {
                    return V3D_Triangle.getGeometry(pqril, sprip, oom, rm);
                } else {
                    return V3D_Triangle.getGeometry2((V3D_LineSegment) spri, pqril, oom, rm);
                }
            } else if (psqi instanceof V3D_LineSegment psqil) {
                return V3D_Triangle.getGeometry2(psqil, pqril, oom, rm);
            } else {
                // Triangle
                return (V3D_Triangle) psqi;
            }
        } else {
            return (V3D_Triangle) pqri;
        }
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Triangle t, int oom, RoundingMode rm) {
        /**
         * The intersection will be null, a point, a line segment, a triangle,
         * quadrilateral, pentagon or hexagon...
         */
        V3D_FiniteGeometry i = getIntersection(t.pl, oom, rm);
        if (i == null) {
            return null;
        } else {
            if (i instanceof V3D_Point ip) {
                if (t.isAligned(ip, oom, rm)) {
                    //if (t.isIntersectedBy(pip, oom, rm)) {
                    return i;
                } else {
                    return null;
                }
            } else if (i instanceof V3D_LineSegment il) {
                /**
                 * Need to get the intersections of pil and the plane edges of
                 * the triangle.
                 */
                V3D_Vector n = t.pl.n;
                V3D_Plane lp = new V3D_Plane(e, offset, t.p, t.q, t.p.add(n, oom, rm), oom, rm);
                V3D_FiniteGeometry lpiil = lp.getIntersection(il, oom, rm);
                if (lpiil == null) {
                    V3D_Plane lq = new V3D_Plane(e, offset, t.q, t.r, t.q.add(n, oom, rm), oom, rm);
                    V3D_FiniteGeometry lqiil = lq.getIntersection(il, oom, rm);
                    if (lqiil == null) {
                        V3D_Plane lr = new V3D_Plane(e, offset, t.r, t.p, t.r.add(n, oom, rm), oom, rm);
                        V3D_FiniteGeometry lriil = lr.getIntersection(il, oom, rm);
                        if (lriil == null) {
                            return il;
                        } else if (lriil instanceof V3D_Point lriilp) {
                            // Find the other point and return the line segment.
                            V3D_Point pq = t.getQ();
                            if (lr.isOnSameSide(lriilp, pq, oom, rm)) {
                                return new V3D_LineSegment(lriilp, pq, oom, rm);
                            } else {
                                return new V3D_LineSegment(lriilp, t.getP(), oom, rm);
                            }
                        } else {
                            // Return the line segment.
                            return (V3D_LineSegment) lriil;
                        }
                    } else if (lqiil instanceof V3D_Point lqiilp) {
                        // Find the other point and return the linesegment.
                        V3D_Plane lr = new V3D_Plane(e, offset, t.r, t.p, t.r.add(n, oom, rm), oom, rm);
                        V3D_FiniteGeometry lriil = lr.getIntersection(il, oom, rm);
                        if (lriil == null) {
                            // For the points on the right side (if any)
                            V3D_Point ilp = il.getP();
                            V3D_Point ilq = il.getQ();
                            V3D_Point tpp = t.getP();
                            if (lq.isOnSameSide(ilp, tpp, oom, rm)) {
                                if (lq.isOnSameSide(ilq, tpp, oom, rm)) {
                                    if (lqiilp.getDistanceSquared(ilp, oom, rm).compareTo(
                                            lqiilp.getDistanceSquared(ilq, oom, rm)) == -1) {
                                        return new V3D_LineSegment(lqiilp, ilq, oom, rm);
                                    } else {
                                        return new V3D_LineSegment(lqiilp, ilp, oom, rm);
                                    }
                                } else {
                                    return new V3D_LineSegment(lqiilp, ilp, oom, rm);
                                }
                            } else {
                                //if (lq.isOnSameSide(pilq, tpp, oom, rm)) {
                                return new V3D_LineSegment(lqiilp, ilq, oom, rm);
                                //} else {
                                //    // This should not happen!
                                //}
                            }
                        } else if (lriil instanceof V3D_Point lriilp) {
                            if (lqiilp.equals(lriilp, oom, rm)) {
                                // Find the other point and return the line segment.
                                V3D_Point ilp = il.getP();
                                V3D_Point ilq = il.getQ();
                                if (lq.isOnSameSide(ilp, ilq, oom, rm)) {
                                    if (lqiilp.getDistanceSquared(ilp, oom, rm).compareTo(
                                            lqiilp.getDistanceSquared(ilq, oom, rm)) == -1) {
                                        return new V3D_LineSegment(lqiilp, ilq, oom, rm);
                                    } else {
                                        return new V3D_LineSegment(lqiilp, ilp, oom, rm);
                                    }
                                } else {
                                    return new V3D_LineSegment(lqiilp, ilp, oom, rm);
                                }
                            } else {
                                return new V3D_LineSegment(lriilp, lqiilp, oom, rm);
                            }
                        } else {
                            // Return the line segment.
                            return (V3D_LineSegment) lriil;
                        }
                    } else {
                        // Return the line segment.
                        return (V3D_LineSegment) lqiil;
                    }
                } else if (lpiil instanceof V3D_Point lpiilp) {
                    // Find the other point and return the linesegment.
                    V3D_Plane lq = new V3D_Plane(e, offset, t.q, t.r, t.q.add(n, oom, rm), oom, rm);
                    V3D_FiniteGeometry lqiil = lq.getIntersection(il, oom, rm);
                    if (lqiil == null) {
                        V3D_Plane lr = new V3D_Plane(e, offset, t.r, t.p, t.r.add(n, oom, rm), oom, rm);
                        V3D_FiniteGeometry lriil = lr.getIntersection(il, oom, rm);
                        if (lriil == null) {
                            // Find the other point and return the line segment.
                            V3D_Point ilp = il.getP();
                            V3D_Point ilq = il.getQ();
                            if (lq.isOnSameSide(ilp, ilq, oom, rm)) {
                                if (lpiilp.getDistanceSquared(ilp, oom, rm).compareTo(
                                        lpiilp.getDistanceSquared(ilq, oom, rm)) == -1) {
                                    return new V3D_LineSegment(lpiilp, ilq, oom, rm);
                                } else {
                                    return new V3D_LineSegment(lpiilp, ilp, oom, rm);
                                }
                            } else {
                                return new V3D_LineSegment(lpiilp, ilp, oom, rm);
                            }
                        } else if (lriil instanceof V3D_Point lriilp) {
                            if (lpiilp.equals(lriilp, oom, rm)) {
                                // Find the other point and return the line segment.
                                V3D_Point ilp = il.getP();
                                V3D_Point ilq = il.getQ();
                                if (lq.isOnSameSide(ilp, ilq, oom, rm)) {
                                    if (lpiilp.getDistanceSquared(ilp, oom, rm).compareTo(
                                            lpiilp.getDistanceSquared(ilq, oom, rm)) == -1) {
                                        return new V3D_LineSegment(lpiilp, ilq, oom, rm);
                                    } else {
                                        return new V3D_LineSegment(lpiilp, ilp, oom, rm);
                                    }
                                } else {
                                    if (lp.isOnSameSide(t.getR(), ilp, oom, rm)) {
                                        return new V3D_LineSegment(lpiilp, ilp, oom, rm);
                                    } else {
                                        return new V3D_LineSegment(lpiilp, ilq, oom, rm);
                                    }
                                }
                            } else {
                                return new V3D_LineSegment(lpiilp, lriilp, oom, rm);
                            }
                        } else {
                            // Return the line segment.
                            return (V3D_LineSegment) lriil;
                        }
                    } else if (lqiil instanceof V3D_Point lqiilp) {
                        // Find the other point and return the linesegment.
                        V3D_Plane lr = new V3D_Plane(e, offset, t.r, t.p, t.r.add(n, oom, rm), oom, rm);
                        V3D_FiniteGeometry lriil = lr.getIntersection(il, oom, rm);
                        if (lriil == null) {
                            // For the points on the right side (if any)
                            V3D_Point pilp = il.getP();
                            V3D_Point pilq = il.getQ();
                            if (lq.isOnSameSide(pilp, pilq, oom, rm)) {
                                if (lqiilp.getDistanceSquared(pilp, oom, rm).compareTo(
                                        lqiilp.getDistanceSquared(pilq, oom, rm)) == -1) {
                                    return new V3D_LineSegment(lqiilp, pilq, oom, rm);
                                } else {
                                    return new V3D_LineSegment(lqiilp, pilp, oom, rm);
                                }
                            } else {
                                if (lq.isOnSameSide(pilp, t.getP(), oom, rm)) {
                                    return new V3D_LineSegment(lqiilp, pilp, oom, rm);
                                } else {
                                    return new V3D_LineSegment(lqiilp, pilq, oom, rm);
                                }
                            }
                        } else if (lriil instanceof V3D_Point lriilp) {
                            if (lriilp.equals(lpiilp, oom, rm)) {
                                return new V3D_LineSegment(lriilp, lqiilp, oom, rm);
                            } else {
                                return new V3D_LineSegment(lriilp, lpiilp, oom, rm);
                            }
                        } else {
                            // Return the line segment.
                            return (V3D_LineSegment) lriil;
                        }
                    } else {
                        // Return the line segment.
                        return (V3D_LineSegment) lqiil;
                    }
                } else {
                    // Return the line segment.
                    return (V3D_LineSegment) lpiil;
                }
                //return t.getIntersection(pil, oom, rm);
            } else if (i instanceof V3D_Triangle it) {
                //it.getIntersection(t, oom, rm);
                return it.clip(t, t.getCentroid(oom, rm), oom, rm);
//                //return it.getIntersection(t, oom); // This does not work due to precision issues.
//                /**
//                 * If all the points of t are within the planes of it, then
//                 * return t. If any edges of t intersect the planes of it, then further
//                 * intersections are needed to derive the final shape. Otherwise
//                 * return it.
//                 */
//                V3D_Point tp = t.pl.getP();
//                V3D_Point tq = t.pl.getQ();
//                V3D_Point tr = t.pl.getR();
//                V3D_Point itp = it.pl.getP();
//                V3D_Point itq = it.pl.getQ();
//                V3D_Point itr = it.pl.getR();
//                V3D_Vector itn = it.pl.getN(oom);
//                V3D_Point itpp = new V3D_Point(e, itp.offset.add(itn, oom), itp.rel);
//                V3D_Plane itppl = new V3D_Plane(itp, itq, itpp);
//                V3D_Point itqp = new V3D_Point(e, itq.offset.add(itn, oom), itq.rel);
//                V3D_Plane itqpl = new V3D_Plane(itq, itr, itqp);
//                V3D_Point itrp = new V3D_Point(e, itr.offset.add(itn, oom), itr.rel);
//                V3D_Plane itrpl = new V3D_Plane(itr, itp, itrp);
//                V3D_FiniteGeometry itc = it.clip(itppl, tr, oom);
//                if (itc == null) {
//                    return null;
//                } else if (itc instanceof V3D_Point) {
//                    return itc;
//                } else if (itc instanceof V3D_LineSegment) {
//                    
//                } else if (itc instanceof V3D_Triangle) {
//                    
//                } else {
//                    // itc instanceof V3D_ConvexHullCoplanar
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
//                                                    V3D_LineSegment ititpq = (V3D_LineSegment) it.getIntersection(t.pl.getPQ(), oom);
//                                                    return V3D_Triangle.getGeometry(ititpq, t.pl.getQR());
//                                                }
//                                            } else {
//                                                if (itqpl.isOnSameSide(tr, itp, oom)) {
////                                                    V3D_LineSegment ititpq = (V3D_LineSegment) it.getIntersection(t.pl.getPQ(), oom);
////                                                    return V3D_Triangle.getGeometry(ititpq, t.pl.getQR());
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
//                                                    V3D_LineSegment ititpq = (V3D_LineSegment) it.getIntersection(t.pl.getPQ(), oom);
//                                                    return V3D_Triangle.getGeometry(ititpq, t.pl.getQR());
//                                                }
//                                            } else {
//                                                if (itqpl.isOnSameSide(tr, itp, oom)) {
////                                                    V3D_LineSegment ititpq = (V3D_LineSegment) it.getIntersection(t.pl.getPQ(), oom);
////                                                    return V3D_Triangle.getGeometry(ititpq, t.pl.getQR());
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
                V3D_ConvexHullCoplanar ic = (V3D_ConvexHullCoplanar) i;
                return ic.clip(t, t.getCentroid(oom, rm), oom, rm);
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

    //@Override
    public V3D_FiniteGeometry getIntersection(V3D_Rectangle r, int oom, RoundingMode rm) {
        V3D_Triangle r_pqr = r.getPQR();
        V3D_Triangle r_rsp = r.getRSP(oom, rm);
        V3D_FiniteGeometry i_pqr = getIntersection(r_pqr, oom, rm);
        V3D_FiniteGeometry i_rsp = getIntersection(r_rsp, oom, rm);
        /**
         * The intersections will be null, a point, a line segment, a triangle,
         * a quadrilateral, a pentagon or a hexagon.
         *
         */
        if (i_pqr == null) {
            return i_rsp;
        } else if (i_pqr instanceof V3D_Point i_pqr_p) {
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
        } else if (i_pqr instanceof V3D_Triangle i_pqrt) {
            if (i_rsp == null) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_Point) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_LineSegment) {
                return i_pqr;
            } else {
                if (i_rsp instanceof V3D_Triangle i_rspt) {
                    V3D_ConvexHullCoplanar ch = new V3D_ConvexHullCoplanar(oom,
                            rm, i_pqrt, i_rspt);
                    return ch.simplify(oom, rm);
                } else {
                    // i_rsp instanceof V3D_ConvexHullCoplanar
                    V3D_ConvexHullCoplanar ch = new V3D_ConvexHullCoplanar(oom,
                            rm, (V3D_ConvexHullCoplanar) i_rsp, i_pqrt);
                    return ch.simplify(oom, rm);
                }
            }
        } else {
            // i_pqr instanceof V3D_ConvexHullCoplanar
            if (i_rsp == null) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_Point) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_LineSegment) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_Triangle i_rspt) {
                V3D_ConvexHullCoplanar ch = new V3D_ConvexHullCoplanar(oom, rm,
                        (V3D_ConvexHullCoplanar) i_pqr, i_rspt);
                return ch.simplify(oom, rm);
            } else {
                // i_rsp instanceof V3D_ConvexHullCoplanar
                V3D_ConvexHullCoplanar ch = new V3D_ConvexHullCoplanar(oom, rm,
                        (V3D_ConvexHullCoplanar) i_pqr,
                        (V3D_ConvexHullCoplanar) i_rsp);
                return ch.simplify(oom, rm);
            }
        }
    }

    @Override
    public V3D_Point[] getPoints(int oom, RoundingMode rm) {
        V3D_Point[] re = new V3D_Point[4];
        re[0] = getP();
        re[1] = getQ();
        re[2] = getR();
        re[3] = getS();
        return re;
    }

    @Override
    public BigDecimal getDistance(V3D_Point p, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Point pt, int oom, RoundingMode rm) {
        if (isIntersectedBy(pt, oom, rm)) {
            return Math_BigRational.ZERO;
        } else {
            return Math_BigRational.min(
                    getPqr(oom, rm).getDistanceSquared(pt, oom, rm),
                    getPsq(oom, rm).getDistanceSquared(pt, oom, rm),
                    getQsr(oom, rm).getDistanceSquared(pt, oom, rm),
                    getSpr(oom, rm).getDistanceSquared(pt, oom, rm));
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public BigDecimal getDistance(V3D_Ray r, int oom, RoundingMode rm) {
        return r.getDistance(this, oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Ray r, int oom, RoundingMode rm) {
        return r.getDistanceSquared(this, oom, rm);
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (getIntersection(l, oom, rm) != null) {
            return Math_BigRational.ZERO;
        } else {
            return Math_BigRational.min(
                    getPqr(oom, rm).getDistanceSquared(l, oom, rm),
                    getPsq(oom, rm).getDistanceSquared(l, oom, rm),
                    getQsr(oom, rm).getDistanceSquared(l, oom, rm),
                    getSpr(oom, rm).getDistanceSquared(l, oom, rm));
        }
    }

    /**
     * Change {@link #offset} without changing the overall line.
     *
     * @param offset What {@link #offset} is set to.
     */
    public void setOffset(V3D_Vector offset, int oom, RoundingMode rm) {
        p = p.add(offset, oom, rm).subtract(this.offset, oom, rm);
        q = q.add(offset, oom, rm).subtract(this.offset, oom, rm);
        r = r.add(offset, oom, rm).subtract(this.offset, oom, rm);
        s = s.add(offset, oom, rm).subtract(this.offset, oom, rm);
        this.offset = offset;
    }

    @Override
    public V3D_Tetrahedron rotate(V3D_Vector axisOfRotation, Math_BigRational theta,
            int oom, RoundingMode rm) {
        return new V3D_Tetrahedron(e, offset,
        p.rotate(axisOfRotation, theta, e.bd, oom, rm),
        q.rotate(axisOfRotation, theta, e.bd, oom, rm),
        r.rotate(axisOfRotation, theta, e.bd, oom, rm),
        s.rotate(axisOfRotation, theta, e.bd, oom, rm));
    }

//    @Override
//    public boolean isIntersectedBy(V3D_Plane pl, int oom, RoundingMode rm) {
//        if (getPqr(oom, rm).isIntersectedBy(pl, oom, rm)) {
//            return true;
//        } else {
//            return getPsq(oom, rm).isIntersectedBy(pl, oom, rm);
//        }
//    }
//
//    /**
//     * https://stackoverflow.com/questions/24097540/testing-tetrahedron-triangle-intersection
//     *
//     * @param t Triangle
//     * @param oom The order of magnitude for the precision.
//     * @return {@code true} iff this and triangle intersect.
//     */
//    @Override
//    public boolean isIntersectedBy(V3D_Triangle t, int oom, RoundingMode rm) {
//        if (isIntersectedBy(t.pl, oom, rm)) {
//            // If any of the points of the triangle are in this return true.
//            if (isIntersectedBy(t.getP(), oom, rm) || isIntersectedBy(t.getQ(), oom, rm)
//                    || isIntersectedBy(t.getR(), oom, rm)) {
//                return true;
//            } else {
//                /**
//                 * If any faces are intersected by the triangle return true.
//                 */
//                if (getPqr(oom, rm).isIntersectedBy(t, oom, rm)) {
//                    return true;
//                } else if (getPsq(oom, rm).isIntersectedBy(t, oom, rm)) {
//                    return true;
//                } else if (getQsr(oom, rm).isIntersectedBy(t, oom, rm)) {
//                    return true;
//                } else if (getSpr(oom, rm).isIntersectedBy(t, oom, rm)) {
//                    return true;
//                } else {
//                    /**
//                     * The points of t may be around the outside of this, but
//                     * none of the edges intersect. The intersection of the
//                     * plane of t and this can give a point, line segment,
//                     * triangle or convex hull. By controlling rounding in the
//                     * intersection calculation, this can be robust, but
//                     * currently the following does not work when all the
//                     * intersected points are not aligned with t.
//                     */
//                    V3D_FiniteGeometry g = getIntersection(t.pl, oom, rm);
//                    if (g == null) {
//                        // Wierd!
//                        return false;
//                    } else {
//                        if (g instanceof V3D_Point gp) {
//                            return t.isAligned(gp, oom, rm);
//                            //return t.isIntersectedBy(gp, oom, rm);
//                        } else if (g instanceof V3D_LineSegment gl) {
//                            if (t.isAligned(gl.getP(), oom, rm)) {
//                                return true;
//                            }
//                            if (t.isAligned(gl.getQ(), oom, rm)) {
//                                return true;
//                            }
//                            return t.isIntersectedBy(gl, oom, rm);
//                        } else if (g instanceof V3D_Triangle gt) {
//                            return t.isIntersectedBy(gt, oom, rm);
//                        } else {
//                            return t.isIntersectedBy((V3D_ConvexHullCoplanar) g, oom, rm);
//                        }
//                    }
//                }
//            }
//        } else {
//            return false;
//        }
//    }

//    /**
//     * To check if this and y intersect.
//     *
//     * @param t A tetrahedron
//     * @param oom The order of magnitude for the precision.
//     * @return {@code true} iff this and t intersect.
//     */
//    @Override
//    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom, RoundingMode rm) {
//        if (getEnvelope(oom, rm).isIntersectedBy(t.getEnvelope(oom, rm), oom, rm)) {
//            // 1. Check points
//            // If any point of t are in this return true.
//            if (isIntersectedBy(t.getP(), oom, rm)) {
//                return true;
//            }
//            if (isIntersectedBy(t.getQ(), oom, rm)) {
//                return true;
//            }
//            if (isIntersectedBy(t.getR(), oom, rm)) {
//                return true;
//            }
//            if (isIntersectedBy(t.getS(), oom, rm)) {
//                return true;
//            }
//            // If any point of this are in t return true.
//            if (t.isIntersectedBy(getP(), oom, rm)) {
//                return true;
//            }
//            if (t.isIntersectedBy(getQ(), oom, rm)) {
//                return true;
//            }
//            if (t.isIntersectedBy(getR(), oom, rm)) {
//                return true;
//            }
//            if (t.isIntersectedBy(getS(), oom, rm)) {
//                return true;
//            }
//            // 2. Check faces
//            // If this intersects any face of t return true.
//            if (isIntersectedBy(t.getPqr(oom, rm), oom, rm)) {
//                return true;
//            }
//            if (isIntersectedBy(t.getPsq(oom, rm), oom, rm)) {
//                return true;
//            }
//            if (isIntersectedBy(t.getQsr(oom, rm), oom, rm)) {
//                return true;
//            }
//            if (isIntersectedBy(t.getSpr(oom, rm), oom, rm)) {
//                return true;
//            }
//            // If t intersects any face of this return true.
//            if (t.isIntersectedBy(getPqr(oom, rm), oom, rm)) {
//                return true;
//            }
//            if (t.isIntersectedBy(getPsq(oom, rm), oom, rm)) {
//                return true;
//            }
//            if (t.isIntersectedBy(getQsr(oom, rm), oom, rm)) {
//                return true;
//            }
//            if (t.isIntersectedBy(getSpr(oom, rm), oom, rm)) {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        V3D_FiniteGeometry pqri = getIntersection(t.getPqr(oom, rm), oom, rm);
        if (pqri == null) {
            V3D_FiniteGeometry psqi = getIntersection(t.getPsq(oom, rm), oom, rm);
            if (psqi == null) {
                V3D_FiniteGeometry qsri = getIntersection(t.getQsr(oom, rm), oom, rm);
                if (qsri == null) {
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(oom, rm), oom, rm);
                    if (spri == null) {
                        V3D_FiniteGeometry tpqri = t.getIntersection(getPqr(oom, rm), oom, rm);
                        if (tpqri == null) {
                            V3D_FiniteGeometry tpsqi = t.getIntersection(getPsq(oom, rm), oom, rm);
                            if (tpsqi == null) {
                                V3D_FiniteGeometry tqsri = t.getIntersection(getQsr(oom, rm), oom, rm);
                                if (tqsri == null) {
                                    V3D_FiniteGeometry tspri = t.getIntersection(getSpr(oom, rm), oom, rm);
                                    if (tspri == null) {
                                        if (isIntersectedBy(t.getP(), oom, rm)
                                                && isIntersectedBy(t.getQ(), oom, rm)
                                                && isIntersectedBy(t.getR(), oom, rm)
                                                && isIntersectedBy(t.getS(), oom, rm)) {
                                            return t;
                                        } else {
                                            if (t.isIntersectedBy(getP(), oom, rm)
                                                    && t.isIntersectedBy(getQ(), oom, rm)
                                                    && t.isIntersectedBy(getR(), oom, rm)
                                                    && t.isIntersectedBy(getS(), oom, rm)) {
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
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom, RoundingMode rm) {
        if (getIntersection(l, oom, rm) != null) {
            return Math_BigRational.ZERO;
        } else {
            return Math_BigRational.min(
                    getPqr(oom, rm).getDistanceSquared(l, oom, rm),
                    getPsq(oom, rm).getDistanceSquared(l, oom, rm),
                    getQsr(oom, rm).getDistanceSquared(l, oom, rm),
                    getSpr(oom, rm).getDistanceSquared(l, oom, rm));
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Plane p, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Plane p, int oom, RoundingMode rm) {
        if (getIntersection(p, oom, rm) != null) {
            return Math_BigRational.ZERO;
        } else {
            return Math_BigRational.min(
                    getPqr(oom, rm).getDistanceSquared(p, oom, rm),
                    getPsq(oom, rm).getDistanceSquared(p, oom, rm),
                    getQsr(oom, rm).getDistanceSquared(p, oom, rm),
                    getSpr(oom, rm).getDistanceSquared(p, oom, rm));
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(t, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom, RoundingMode rm) {
        if (getIntersection(t, oom, rm) != null) {
            return Math_BigRational.ZERO;
        } else {
            return Math_BigRational.min(
                    getPqr(oom, rm).getDistanceSquared(t, oom, rm),
                    getPsq(oom, rm).getDistanceSquared(t, oom, rm),
                    getQsr(oom, rm).getDistanceSquared(t, oom, rm),
                    getSpr(oom, rm).getDistanceSquared(t, oom, rm));
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(t, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        if (getIntersection(t, oom, rm) != null) {
            return Math_BigRational.ZERO;
        } else {
            return Math_BigRational.min(
                    getPqr(oom, rm).getDistanceSquared(t, oom, rm),
                    getPsq(oom, rm).getDistanceSquared(t, oom, rm),
                    getQsr(oom, rm).getDistanceSquared(t, oom, rm),
                    getSpr(oom, rm).getDistanceSquared(t, oom, rm));
        }
    }
}
