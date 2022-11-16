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
import java.util.Iterator;
import java.util.List;
import uk.ac.leeds.ccg.v3d.geometry.d.light.V3D_VTriangleDouble;

/**
 * For representing and processing triangles in 3D. A triangle has a non-zero
 * area. The corner points are {@link #pl}, {@link #q} and {@link #r}. The
 * following depicts a generic triangle {@code
                           pq
  pv *- - - - - - - - - - - + - - - - - - - - - - -* qv
     \~                   mpq                   ~/
      \  ~                 |                 ~  /
       \    ~              |              ~    /
        \      ~           |           ~      /
         \        ~        |        ~        /
          \          ~     |     ~          /
           \            ~  |  ~            /
       -n  -n  -n  -n  -n  c  +n  +n  +n  +n  +n  normal heading out from the page.
             \          ~  |  ~          /
              \      ~     |     ~      /
               \  ~        |        ~  /
                + mrp      |      mqr +
             rp  \         |         /  qr
                  \        |        /
                   \       |       /
                    \      |      /
                     \     |     /
                      \    |    /
                       \   |   /
                        \  |  /
                         \ | /
                          \|/

                           r
 }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_TriangleDouble extends V3D_FiniteGeometryDouble implements V3D_FaceDouble {

    private static final long serialVersionUID = 1L;

    /**
     * The plane of the triangle
     */
    protected V3D_PlaneDouble pl;

    /**
     * Defines one of the corners of the triangle.
     */
    public V3D_VectorDouble p;

    /**
     * Defines one of the corners of the triangle.
     */
    public V3D_VectorDouble q;

    /**
     * Defines one of the corners of the triangle.
     */
    public V3D_VectorDouble r;
    
    /**
     * For storing the line segment from {@link #getP()} to {@link #getQ()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    private V3D_LineSegmentDouble pq;

    /**
     * For storing the line segment from {@link #getQ()} to {@link #getR()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    private V3D_LineSegmentDouble qr;

    /**
     * For storing the line segment from {@link #getR()} to {@link #getP()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    private V3D_LineSegmentDouble rp;

    /**
     * For storing the plane aligning with {@link #pq} in the direction of the
     * plane normal and with a normal orthogonal to the plane normal.
     */
    private V3D_PlaneDouble pqpl;

    /**
     * For storing the plane aligning with {@link #qr} in the direction of the
     * plane normal and with a normal orthogonal to the plane normal.
     */
    private V3D_PlaneDouble qrpl;

    /**
     * For storing the plane aligning with {@link #rp} in the direction of the
     * plane normal and with a normal orthogonal to the plane normal.
     */
    private V3D_PlaneDouble rppl;

//    /**
//     * For storing the midpoint between {@link #getP()} and {@link #getQ()}.
//     */
//    private V3D_PointDouble mpq;
//
//    /**
//     * For storing the midpoint between {@link #getQ()} and {@link #getR()}.
//     */
//    private V3D_PointDouble mqr;
//
//    /**
//     * For storing the midpoint between {@link #getR()} and {@link #getP()}.
//     */
//    private V3D_PointDouble mrp;
//
//    /**
//     * For storing the centroid.
//     */
//    private V3D_PointDouble c;
    /**
     * Creates a new triangle.
     *
     * @param t The triangle to clone.
     */
    public V3D_TriangleDouble(V3D_TriangleDouble t) {
        super(t.offset);
        pl = new V3D_PlaneDouble(t.pl);
        p = new V3D_VectorDouble(t.p);
        q = new V3D_VectorDouble(t.q);
        r = new V3D_VectorDouble(t.r);
    }
    
    /**
     * Creates a new triangle.
     *
     * @param offset What {@link #offset} is set to.
     * @param t The triangle to initialise this from.
     */
    public V3D_TriangleDouble(V3D_VectorDouble offset, V3D_VTriangleDouble t) {
        this(offset, new V3D_VectorDouble(t.pq.p), new V3D_VectorDouble(t.pq.q),
                new V3D_VectorDouble(t.qr.q));
    }

    /**
     * Creates a new triangle. {@link #offset} is set to
     * {@link V3D_VectorDouble#ZERO}.
     *
     * @param p What {@link #pl} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     */
    public V3D_TriangleDouble(V3D_VectorDouble p, V3D_VectorDouble q, 
            V3D_VectorDouble r) {
        this(V3D_VectorDouble.ZERO, p, q, r);
    }

    /**
     * Creates a new triangle.
     *
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #pl} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     */
    public V3D_TriangleDouble(V3D_VectorDouble offset, V3D_VectorDouble p,
            V3D_VectorDouble q, V3D_VectorDouble r) {
        super(offset);
        this.p = p;
        this.q = q;
        this.r = r;
    }
    
    /**
     * Creates a new triangle.
     *
     * @param l A line segment representing one of the three edges of the
     * triangle.
     * @param r Defines the other point relative to l.offset that defines the
     * triangle.
     */
    public V3D_TriangleDouble(V3D_LineSegmentDouble l, V3D_VectorDouble r) {
        this(l.offset, l.l.pv, l.qv, r);
    }

    /**
     * Creates a new triangle.
     *
     * @param l A line segment representing one of the three edges of the
     * triangle.
     * @param r Defines the other point relative to l.offset that defines the
     * triangle.
     */
    public V3D_TriangleDouble(V3D_LineSegmentDouble l, V3D_PointDouble r) {
        this(l.offset, l.l.pv, l.qv, r.getVector().subtract(l.offset));
    }

    /**
     * Creates a new instance.
     *
     * @param p Used to initialise {@link #offset} and {@link #pl}.
     * @param q Used to initialise {@link #q}.
     * @param r Used to initialise {@link #r}.
     */
    public V3D_TriangleDouble(V3D_PointDouble p, V3D_PointDouble q,
            V3D_PointDouble r) {
        super(p.offset);
        this.p = p.rel;
        this.q = q.getVector().subtract(p.offset);
        this.r = r.getVector().subtract(p.offset);
    }

    /**
     * Creates a new instance.
     *
     * @param pt A point giving the direction of the normal vector.
     * @param p Used to initialise {@link #offset} and {@link #pl}.
     * @param q Used to initialise {@link #q}.
     * @param r Used to initialise {@link #r}.
     */
    public V3D_TriangleDouble(V3D_PointDouble pt, V3D_PointDouble p, 
            V3D_PointDouble q, V3D_PointDouble r) {
        super(p.offset);
        this.p = p.rel;
        this.q = q.getVector().subtract(p.offset);
        this.r = r.getVector().subtract(p.offset);
    }
    
    /**
     * @return {@link pl} accurate to at least the oom precision using 
     * RoundingMode rm.
     */
    public final V3D_PlaneDouble getPl() {
        if (pl == null) {
            pl = new V3D_PlaneDouble(offset, p, q, r);
        }
        return pl;
    }

    /**
     * @param pt The normal will point to this side of the plane.
     * @return {@link pl} accurate to at least the oom precision using 
     * RoundingMode rm.
     */
    public final V3D_PlaneDouble getPl(V3D_PointDouble pt) {
        if (pl == null) {
            pl = new V3D_PlaneDouble(pt, offset, p, q, r);
        }
        return pl;
    }

    /**
     * @return A new point based on {@link #p} and {@link #offset}.
     */
    public final V3D_PointDouble getP() {
        return new V3D_PointDouble(offset, p);
    }

    /**
     * @return A new point based on {@link #q} and {@link #offset}.
     */
    public final V3D_PointDouble getQ() {
        return new V3D_PointDouble(offset, q);
    }

    /**
     * @return A new point based on {@link #r} and {@link #offset}.
     */
    public final V3D_PointDouble getR() {
        return new V3D_PointDouble(offset, r);
    }

    /**
     * For getting the line segment from {@link #getP()} to {@link #getQ()}.
     *
     * @return Line segment from r to pv.
     */
    public final V3D_LineSegmentDouble getPQ() {
        if (pq == null) {
            pq = new V3D_LineSegmentDouble(offset, p, q);
        }
        return pq;
    }

    /**
     * @return {@code q.subtract(p)}
     */
    public final V3D_VectorDouble getPQV() {
        return q.subtract(p);
    }

    /**
     * @return {@code r.subtract(q)}
     */
    public final V3D_VectorDouble getQRV() {
        return r.subtract(q);
    }

    /**
     * @return {@code p.subtract(r)}
     */
    public final V3D_VectorDouble getRPV() {
        return p.subtract(r);
    }

    /**
     * For getting the line segment from {@link #getQ()} to {@link #getR()}.
     *
     * @return Line segment from q to r.
     */
    public final V3D_LineSegmentDouble getQR() {
        if (qr == null) {
            qr = new V3D_LineSegmentDouble(offset, q, r);
        }
        return qr;
    }

    /**
     * For getting the line segment from {@link #getR()} to {@link #getP()}.
     *
     * @return Line segment from r to p.
     */
    public final V3D_LineSegmentDouble getRP() {
        if (rp == null) {
            rp = new V3D_LineSegmentDouble(offset, r, p);
        }
        return rp;
    }

    /**
     * For getting the plane through {@link #pq} in the direction of the normal.
     *
     * @return The plane through {@link #pq} in the direction of the normal.
     */
    public V3D_PlaneDouble getPQPl() {
        if (pqpl == null) {
            pqpl = new V3D_PlaneDouble(pq.getP(), pq.l.v.getCrossProduct(
                getPl().n));
        }
        return pqpl;
    }

    /**
     * For getting the plane through {@link #qr} in the direction of the normal.
     *
     * @return The plane through {@link #qr} in the direction of the normal.
     */
    public V3D_PlaneDouble getQRPl() {
        if (qrpl == null) {
            qrpl = new V3D_PlaneDouble(qr.getP(), qr.l.v.getCrossProduct(
                getPl().n));
        }
        return qrpl;
    }

    /**
     * For getting the plane through {@link #rp} in the direction of the normal.
     *
     * @return The plane through {@link #rp} in the direction of the normal.
     */
    public V3D_PlaneDouble getRPPl() {
        if (rppl == null) {
            rppl = new V3D_PlaneDouble(rp.getP(), rp.l.v.getCrossProduct(
                getPl().n));
        }
        return rppl;
    }

    @Override
    public V3D_EnvelopeDouble getEnvelope() {
        if (en == null) {
            en = new V3D_EnvelopeDouble(getP(), getQ(), getR());
        }
        return en;
    }

    @Override
    public V3D_PointDouble[] getPoints() {
        V3D_PointDouble[] re = new V3D_PointDouble[3];
        re[0] = getP();
        re[1] = getQ();
        re[2] = getR();
        return re;
    }

    /**
     * @param pt The point to intersect with.
     * @return A point or line segment.
     */
    public boolean isIntersectedBy(V3D_PointDouble pt) {
        if (getEnvelope().isIntersectedBy(pt)) {
            if (getPl().isIntersectedBy(pt)) {
                return isAligned(pt);
                //return isIntersectedBy0(pt);
            }
        }
        return false;
    }

    /**
     * @param pt The point.
     * @return {@code true} if this intersects with {@code pt}.
     */
    @Deprecated
    protected boolean isIntersectedBy0(V3D_PointDouble pt) {
        V3D_LineSegmentDouble tpq = getPQ();
        V3D_LineSegmentDouble tqr = getQR();
        V3D_LineSegmentDouble trp = getRP();
        if (tpq.isIntersectedBy(pt)
                || tqr.isIntersectedBy(pt)
                || trp.isIntersectedBy(pt)) {
            return true;
        }
        V3D_VectorDouble ppt = new V3D_VectorDouble(getP(), pt);
        V3D_VectorDouble qpt = new V3D_VectorDouble(getQ(), pt);
        V3D_VectorDouble rpt = new V3D_VectorDouble(getR(), pt);
        V3D_VectorDouble cp = tpq.l.v.getCrossProduct(ppt);
        V3D_VectorDouble cq = tqr.l.v.getCrossProduct(qpt);
        V3D_VectorDouble cr = trp.l.v.getCrossProduct(rpt);
        /**
         * If cp, cq and cr are all in the same direction then pt intersects.
         */
        if (cp.dx < 0d == cq.dx < 0d && cp.dx < 0d == cr.dx < 0d) {
            if (cp.dy < 0d == cq.dy < 0d && cp.dy < 0d == cr.dy < 0d) {
                if (cp.dz < 0d == cq.dz < 0d && cp.dz < 0d == cr.dz < 0d) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * The point pt aligns with this if it is on the same side of each plane
     * defined a triangle edge (with a normal given by the cross product of the
     * triangle normal and the edge line vector), and the other point of the
     * triangle. The plane normal may be imprecisely calculated. Greater
     * precision can be gained using a smaller oom.
     *
     * @param pt The point to check if it is in alignment.
     * @return {@code true} iff pl is aligned with this.
     */
    public boolean isAligned(V3D_PointDouble pt) {
//        if (pl.isIntersectedBy(pt)) {
//            return isIntersectedBy0(pt);
//        }
        if (getPQPl().isOnSameSide(pt, getR())) {
            if (getQRPl().isOnSameSide(pt, getP())) {
                if (getRPPl().isOnSameSide(pt, getQ())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A line segment aligns with this if both end points are aligned according
     * to
     * {@link #isAligned(uk.ac.leeds.ccg.v3d.geometry.d.V3D_PointDouble)}.
     *
     * @param l The line segment to check if it is in alignment.
     * @return {@code true} iff l is aligned with this.
     */
    public boolean isAligned(V3D_LineSegmentDouble l) {
        if (isAligned(l.getP())) {
            return isAligned(l.getQ());
        }
        return false;
    }

    /**
     * A triangle aligns with this if all points are aligned according to
     * {@link #isAligned(uk.ac.leeds.ccg.v3d.geometry.d.V3D_PointDouble)}.
     *
     * @param t The triangle to check if it is in alignment.
     * @return {@code true} iff l is aligned with this.
     */
    public boolean isAligned(V3D_TriangleDouble t) {
        if (isAligned(t.getP())) {
            if (isAligned(t.getQ())) {
                return isAligned(t.getR());
            }
        }
        return false;
    }

    @Override
    public double getArea() {
        return getPQV().getCrossProduct(getRPV()).getMagnitude() / 2.0d;
    }

    @Override
    public double getPerimeter() {
        return getPQ().getLength() + getQR().getLength() + getRP().getLength();
    }

    /**
     * @param l The line to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return A point or line segment.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_LineDouble l, 
            double epsilon) {
        V3D_GeometryDouble i = getPl().getIntersection(l, epsilon);
        if (i == null) {
            return null;
        } else if (i instanceof V3D_PointDouble ip) {
            if (isAligned(ip)) {
                return ip;
            } else {
                return null;
            }
        } else {
            /**
             * Get the intersection of the line and each edge of the triangle.
             */
            V3D_FiniteGeometryDouble lpqi = getPQ().getIntersection(l, epsilon);
            V3D_FiniteGeometryDouble lqri = getQR().getIntersection(l, epsilon);
            V3D_FiniteGeometryDouble lrpi = getRP().getIntersection(l, epsilon);
            if (lpqi == null) {
                if (lqri == null) {
                    return null; // This should not happen!
                } else {
                    if (lrpi == null) {
                        return lqri;
                    } else {
                        return getGeometry(((V3D_PointDouble) lqri).getVector(),
                                ((V3D_PointDouble) lrpi).getVector());
                    }
                }
            } else if (lpqi instanceof V3D_PointDouble lpqip) {
                if (lqri == null) {
                    if (lrpi == null) {
                        return lpqi;
                    } else {
                        return getGeometry(lpqip.getVector(),
                                ((V3D_PointDouble) lrpi).getVector());
                    }
                } else if (lqri instanceof V3D_PointDouble lqrip) {
                    if (lrpi == null) {
                        return getGeometry(lqrip.getVector(),
                                lpqip.getVector());
                    } else if (lrpi instanceof V3D_LineSegmentDouble) {
                        return lrpi;
                    } else {
                        return getGeometry(lpqip, lqrip, (V3D_PointDouble) lrpi);
                    }
                } else {
                    return lqri;
                }
            } else {
                return lpqi;
            }
        }
    }

    /**
     * If {@code v1} and {@code v2} are the same, then return a point, otherwise
     * return a line segment. In both instance offset is set to
     * {@link V3D_VectorDouble#ZERO}.
     *
     * @param v1 A vector.
     * @param v2 A vector.
     * @return Either a line segment or a point.
     */
    public static V3D_FiniteGeometryDouble getGeometry(V3D_VectorDouble v1,
            V3D_VectorDouble v2) {
        if (v1.equals(v2)) {
            return new V3D_PointDouble(v1);
        } else {
            return new V3D_LineSegmentDouble(v1, v2);
        }
    }

    /**
     * Get the intersection between the geometry and the ray {@code r}.
     *
     * @param r The ray to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_RayDouble r, 
            double epsilon) {
        V3D_FiniteGeometryDouble g = getIntersection(r.l, epsilon);
        if (g == null) {
            return null;
        }
        if (g instanceof V3D_PointDouble gp) {
            if (r.isAligned(gp)) {
                return gp;
            } else {
                return null;
            }
        }
        V3D_LineSegmentDouble ls = (V3D_LineSegmentDouble) g;
        V3D_PointDouble lsp = ls.getP();
        V3D_PointDouble lsq = ls.getQ();
        if (r.isAligned(lsp)) {
            if (r.isAligned(lsq)) {
                return ls;
            } else {
                return V3D_LineSegmentDouble.getGeometry(r.l.getP(), lsp);
            }
        } else {
            if (r.isAligned(lsq)) {
                return V3D_LineSegmentDouble.getGeometry(r.l.getP(), lsq);
            } else {
                throw new RuntimeException("Exception in triangle-linesegment intersection.");
            }
        }
    }

    /**
     * Get the intersection between the geometry and the line segment {@code l}.
     *
     * @param l The line segment to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_LineSegmentDouble l, 
            double epsilon) {
        V3D_FiniteGeometryDouble g = getIntersection(l.l, epsilon);
        if (g == null) {
            return null;
        }
        if (g instanceof V3D_PointDouble gp) {
            if (l.isAligned(gp)) {
                return gp;
            } else {
                return null;
            }
        }
        V3D_LineSegmentDouble ls = (V3D_LineSegmentDouble) g;
        V3D_PointDouble lsp = ls.getP();
        V3D_PointDouble lsq = ls.getQ();
        if (l.isAligned(lsp)) {
            if (l.isAligned(lsq)) {
                return ls;
            } else {
                V3D_PlaneDouble lippl = ls.getPPL();
                V3D_PointDouble lp = l.getP();
                if (lippl.isOnSameSide(lp, lsq)) {
                    return V3D_LineSegmentDouble.getGeometry(lsp, lp);
                } else {
                    return V3D_LineSegmentDouble.getGeometry(lsp, l.getQ());
                }
            }
        } else {
            if (l.isAligned(lsq)) {
                V3D_PlaneDouble liqpl = ls.getQPL();
                V3D_PointDouble lp = l.getP();
                if (liqpl.isOnSameSide(lp, lsp)) {
                    return V3D_LineSegmentDouble.getGeometry(lsq, lp);
                } else {
                    return V3D_LineSegmentDouble.getGeometry(lsq, l.getQ());
                }
            } else {
                return l;
            }
        }
    }

    /**
     * Find the point of l to be the other end of the result. If l intersects
     * this, it can overshoot or undershoot, so this effectively clips the
     * result.
     *
     * @param l Line segment being intersected with this.
     * @param pt One end of the result.
     * @param opt The other end l.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return Either a point or a line segment.
     */
    private V3D_FiniteGeometryDouble getIntersection0(V3D_LineSegmentDouble l, 
            V3D_PointDouble pt, V3D_PointDouble opt, double epsilon) {
        V3D_FiniteGeometryDouble lipq = l.getIntersection(getPQ(), epsilon);
        V3D_FiniteGeometryDouble liqr = l.getIntersection(getQR(), epsilon);
        V3D_FiniteGeometryDouble lirp = l.getIntersection(getRP(), epsilon);
        V3D_PlaneDouble ptpl = new V3D_PlaneDouble(pt, l.l.v);
        if (lipq == null) {
            if (liqr == null) {
                if (lirp instanceof V3D_PointDouble lirpp) {
                    if (ptpl.isOnSameSide(lirpp, opt)) {
                        return V3D_LineSegmentDouble.getGeometry(pt, lirpp);
                    } else {
                        return lirpp;
                    }
                } else {
                    return lirp;
                }
            } else {
                if (lirp == null) {
                    if (liqr instanceof V3D_PointDouble liqrp) {
                        if (ptpl.isOnSameSide(liqrp, opt)) {
                            return V3D_LineSegmentDouble.getGeometry(pt, liqrp);
                        } else {
                            return liqrp;
                        }
                    } else {
                        return liqr;
                    }
                } else {
                    if (lirp instanceof V3D_LineSegmentDouble lirpl) {
                        return getGeometry(pt, lirpl.getP(), lirpl.getQ());
                    }
                    if (liqr instanceof V3D_LineSegmentDouble liqrp) {
                        return getGeometry(pt, liqrp.getP(), liqrp.getQ());
                    }
                    V3D_PointDouble lirpp = (V3D_PointDouble) lirp;
                    V3D_PointDouble liqrp = (V3D_PointDouble) liqr;
                    if (ptpl.isOnSameSide(lirpp, opt)) {
                        if (ptpl.isOnSameSide(liqrp, opt)) {
                            //return getGeometry(pt, lirpp, liqrp);
                            return V3D_LineSegmentDouble.getGeometry(lirpp, liqrp);
                        } else {
                            return V3D_LineSegmentDouble.getGeometry(pt, lirpp);
                        }
                    } else {
                        if (ptpl.isOnSameSide(liqrp, opt)) {
                            return V3D_LineSegmentDouble.getGeometry(pt, liqrp);
                        } else {
                            return pt;
                        }
                    }
                }
            }
        } else {
            if (lipq instanceof V3D_LineSegmentDouble lipql) {
                return getGeometry(pt, lipql.getP(), lipql.getQ());
            } else {
                if (liqr == null) {
                    if (lirp == null) {
                        V3D_PointDouble lipqp = (V3D_PointDouble) lipq;
                        if (ptpl.isOnSameSide(lipqp, opt)) {
                            return V3D_LineSegmentDouble.getGeometry(pt, lipqp);
                        } else {
                            return pt;
                        }
                    } else {
                        if (lirp instanceof V3D_LineSegmentDouble lirpl) {
                            return getGeometry(pt, lirpl.getP(), lirpl.getQ());
                        }
                        V3D_PointDouble lipqp = (V3D_PointDouble) lipq;
                        V3D_PointDouble lirpp = (V3D_PointDouble) lirp;
                        if (ptpl.isOnSameSide(lipqp, opt)) {
                            if (ptpl.isOnSameSide(lirpp, opt)) {
                                return getGeometry(pt, lirpp, lipqp);
                            } else {
                                return V3D_LineSegmentDouble.getGeometry(pt, lipqp);
                            }
                        } else {
                            if (ptpl.isOnSameSide(lirpp, opt)) {
                                return V3D_LineSegmentDouble.getGeometry(pt, lirpp);
                            } else {
                                return pt;
                            }
                        }
                    }
                } else {
                    if (liqr instanceof V3D_LineSegmentDouble liqrp) {
                        return getGeometry(pt, liqrp.getP(), liqrp.getQ());
                    }
                    if (lirp == null) {
                        V3D_PointDouble lipqp = (V3D_PointDouble) lipq;
                        V3D_PointDouble liqrp = (V3D_PointDouble) liqr;
                        if (ptpl.isOnSameSide(lipqp, opt)) {
                            if (ptpl.isOnSameSide(liqrp, opt)) {
                                return getGeometry(pt, liqrp, lipqp);
                            } else {
                                return V3D_LineSegmentDouble.getGeometry(pt, lipqp);
                            }
                        } else {
                            if (ptpl.isOnSameSide(liqrp, opt)) {
                                return V3D_LineSegmentDouble.getGeometry(pt, liqrp);
                            } else {
                                return pt;
                            }
                        }
                    } else {
                        if (lirp instanceof V3D_LineSegmentDouble lirpl) {
                            return getGeometry(pt, lirpl.getP(), lirpl.getQ());
                        }
                        V3D_PointDouble lipqp = (V3D_PointDouble) lipq;
                        V3D_PointDouble liqrp = (V3D_PointDouble) liqr;
                        V3D_PointDouble lirpp = (V3D_PointDouble) lirp;
                        if (ptpl.isOnSameSide(lipqp, opt)) {
                            if (ptpl.isOnSameSide(liqrp, opt)) {
                                if (ptpl.isOnSameSide(lirpp, opt)) {
                                    throw new RuntimeException("Issue with Triangle-Traingle intersection.");
                                } else {
                                    return getGeometry(pt, liqrp, lipqp);
                                }
                            } else {
                                if (ptpl.isOnSameSide(lirpp, opt)) {
                                    return getGeometry(pt, lirpp, lipqp);
                                } else {
                                    return V3D_LineSegmentDouble.getGeometry(pt, lipqp);
                                }
                            }
                        } else {
                            if (ptpl.isOnSameSide(liqrp, opt)) {
                                if (ptpl.isOnSameSide(lirpp, opt)) {
                                    return getGeometry(pt, liqrp, lirpp);
                                } else {
                                    return V3D_LineSegmentDouble.getGeometry(pt, liqrp);
                                }
                            } else {
                                if (ptpl.isOnSameSide(lirpp, opt)) {
                                    return V3D_LineSegmentDouble.getGeometry(pt, lirpp);
                                } else {
                                    return pt;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Calculate and return the intersection between {@code this} and
     * {@code pl}. A question about how to do this:
     * https://stackoverflow.com/questions/3142469/determining-the-intersection-of-a-triangle-and-a-plane
     *
     * @param pl The plane to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return The intersection between {@code this} and {@code pl}.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_PlaneDouble pl,
            double epsilon) {
        // Get intersection if this were a plane
        //V3D_Geometry pi = pl.getIntersection(this, oom);
        V3D_GeometryDouble pi = pl.getIntersection(getPl(), epsilon);
        if (pi == null) {
            return null;
        } else if (pi instanceof V3D_PlaneDouble) {
            return this;
        } else {
            // (pi instanceof V3D_Line)
            V3D_FiniteGeometryDouble gPQ = pl.getIntersection(getPQ(), epsilon);
            if (gPQ == null) {
                V3D_FiniteGeometryDouble gQR = pl.getIntersection(getQR(), epsilon);
                if (gQR == null) {
                    V3D_FiniteGeometryDouble gRP = pl.getIntersection(getRP(), epsilon);
                    if (gRP == null) {
                        return null;
                    } else {
                        return gRP;
                    }
                } else {
                    V3D_GeometryDouble gRP = pl.getIntersection(getRP(), epsilon);
                    if (gRP == null) {
                        return gQR;
                    } else {
                        if (gRP instanceof V3D_PointDouble gRPp) {
                            return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) gQR,
                                    gRPp);
                        } else {
                            return V3D_TriangleDouble.getGeometry((V3D_LineSegmentDouble) gRP,
                                    (V3D_PointDouble) gQR);
                        }
                    }
                }
            } else if (gPQ instanceof V3D_LineSegmentDouble) {
                return gPQ;
            } else {
                V3D_FiniteGeometryDouble gQR = pl.getIntersection(getQR(), epsilon);
                if (gQR == null) {
                    V3D_FiniteGeometryDouble gRP = pl.getIntersection(getRP(), epsilon);
                    if (gRP == null) {
                        return (V3D_PointDouble) gPQ;
                    } else if (gRP instanceof V3D_LineSegmentDouble) {
                        return gRP;
                    } else {
                        return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) gPQ,
                                (V3D_PointDouble) gRP);
                    }
                } else {
                    if (gQR instanceof V3D_PointDouble gQRp) {
                        return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) gPQ, gQRp);
                    } else {
                        return gQR;
                    }
                }
            }
        }
    }

    /**
     * Computes and returns the intersection between {@code this} and {@code t}.
     * The intersection could be: null, a point, a line segment, a triangle, or
     * a V3D_ConvexHullCoplanar (with 4, 5, or 6 sides).
     *
     * @param t The triangle intersect with this.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_TriangleDouble t, 
            double epsilon) {
        if (getEnvelope().isIntersectedBy(t.getEnvelope())) {
            V3D_FiniteGeometryDouble g = t.getIntersection(getPl(), epsilon);
            if (g == null) {
                return g;
            } else {
                if (g instanceof V3D_PointDouble pt) {
//                    if (t.isIntersectedBy(pt)) {
//                        return pt;
//                    } else {
//                        return null;
//                    }
                    if (isAligned(pt)) {
                        return pt;
                    } else {
                        return null;
                    }
                } else if (g instanceof V3D_LineSegmentDouble l) {
                    //return t.getIntersection(l);
                    V3D_PointDouble lp = l.getP();
                    V3D_PointDouble lq = l.getQ();
                    if (isAligned(lp)) {
                        if (isAligned(lq)) {
                            return l;
                        } else {
                            V3D_FiniteGeometryDouble pqplil = getPQPl().getIntersection(l, epsilon);
                            V3D_FiniteGeometryDouble qrplil = getQRPl().getIntersection(l, epsilon);
                            V3D_FiniteGeometryDouble rpplil = getRPPl().getIntersection(l, epsilon);
                            if (pqplil == null) {
                                if (qrplil == null) {
                                    if (rpplil instanceof V3D_LineSegmentDouble) {
                                        return rpplil;
                                    } else {
                                        return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) rpplil, lp);
                                    }
                                } else {
                                    if (qrplil instanceof V3D_LineSegmentDouble) {
                                        return qrplil;
                                    } else {
                                        if (rpplil == null) {
                                            return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) qrplil, lp);
                                        } else {
                                            if (rpplil instanceof V3D_LineSegmentDouble) {
                                                return rpplil;
                                            } else {
                                                return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) rpplil, lp);
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (pqplil instanceof V3D_LineSegmentDouble) {
                                    return pqplil;
                                } else {
                                    if (qrplil == null) {
                                        if (rpplil == null) {
                                            return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) pqplil, lp);
                                        } else {
                                            if (rpplil instanceof V3D_LineSegmentDouble) {
                                                return rpplil;
                                            } else {
                                                return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) rpplil, (V3D_PointDouble) pqplil, lp);
                                            }
                                        }
                                    } else {
                                        if (qrplil instanceof V3D_LineSegmentDouble) {
                                            return qrplil;
                                        } else {
                                            //if (rpplil == null) {
                                            return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) pqplil, (V3D_PointDouble) qrplil, lp);
                                            //} else {
                                            //    if (rpplil instanceof V3D_LineSegment) {
                                            //        return rpplil;
                                            //    } else {
                                            //        return V3D_LineSegment.getGeometry((V3D_Point) rpplil, (V3D_Point) pqplil, lp);
                                            //    }
                                            //}
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (isAligned(lq)) {
                            V3D_FiniteGeometryDouble pqplil = getPQPl().getIntersection(l, epsilon);
                            V3D_FiniteGeometryDouble qrplil = getQRPl().getIntersection(l, epsilon);
                            V3D_FiniteGeometryDouble rpplil = getRPPl().getIntersection(l, epsilon);
                            if (pqplil == null) {
                                if (qrplil == null) {
                                    if (rpplil instanceof V3D_LineSegmentDouble) {
                                        return rpplil;
                                    } else {
                                        return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) rpplil, lq);
                                    }
                                } else {
                                    if (qrplil instanceof V3D_LineSegmentDouble) {
                                        return qrplil;
                                    } else {
                                        if (rpplil == null) {
                                            return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) qrplil, lq);
                                        } else {
                                            if (rpplil instanceof V3D_LineSegmentDouble) {
                                                return rpplil;
                                            } else {
                                                return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) rpplil, lq);
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (pqplil instanceof V3D_LineSegmentDouble) {
                                    return pqplil;
                                } else {
                                    if (qrplil == null) {
                                        if (rpplil == null) {
                                            return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) pqplil, lq);
                                        } else {
                                            if (rpplil instanceof V3D_LineSegmentDouble) {
                                                return rpplil;
                                            } else {
                                                return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) rpplil, (V3D_PointDouble) pqplil, lq);
                                            }
                                        }
                                    } else {
                                        if (qrplil instanceof V3D_LineSegmentDouble) {
                                            return qrplil;
                                        } else {
                                            //if (rpplil == null) {
                                            return V3D_LineSegmentDouble.getGeometry((V3D_PointDouble) pqplil, (V3D_PointDouble) qrplil, lq);
                                            //} else {
                                            //    if (rpplil instanceof V3D_LineSegment) {
                                            //        return rpplil;
                                            //    } else {
                                            //        return V3D_LineSegment.getGeometry((V3D_Point) rpplil, (V3D_Point) pqplil, lp);
                                            //    }
                                            //}
                                        }
                                    }
                                }
                            }
                        } else {
                            throw new RuntimeException("Exception with triangle-triangle intersection.");
                        }
                    }
                } else {
                    /**
                     * The two triangles are in the same plane Get intersections
                     * between the triangle edges. If there are none, then
                     * return t. If there are some, then in some cases the
                     * result is a single triangle, and in others it is a
                     * polygon which can be represented as a set of coplanar
                     * triangles.
                     */
                    // Check if vertices intersect
                    boolean pi = isIntersectedBy(t.getP());
                    boolean qi = isIntersectedBy(t.getQ());
                    boolean ri = isIntersectedBy(t.getR());
                    if (pi && qi && ri) {
                        return t;
                    }
                    boolean pit = t.isIntersectedBy(getP());
                    boolean qit = t.isIntersectedBy(getQ());
                    boolean rit = t.isIntersectedBy(getR());
                    if (pit && qit && rit) {
                        return this;
                    }
                    V3D_FiniteGeometryDouble gpq = t.getIntersection(getPQ(), epsilon);
                    V3D_FiniteGeometryDouble gqr = t.getIntersection(getQR(), epsilon);
                    V3D_FiniteGeometryDouble grp = t.getIntersection(getRP(), epsilon);
                    if (gpq == null) {
                        if (gqr == null) {
//                                if (grp == null) {
//                                    // Return the smaller of the triangles
////                                    if (t.getArea(oom).compareTo(getArea(oom)) == 1) {
//                                    if (t.getPerimeter(oom).compareTo(getPerimeter(oom)) == 1) {
//                                        return this;
//                                    } else {
//                                        return t;
//                                    }
//                                } else {
                            return grp;
//                                }
                        } else if (gqr instanceof V3D_PointDouble gqrp) {
                            if (grp == null) {
                                return gqr;
                            } else if (grp instanceof V3D_PointDouble grpp) {
                                return V3D_LineSegmentDouble.getGeometry(
                                        gqrp, grpp);
                            } else {
                                V3D_LineSegmentDouble ls = (V3D_LineSegmentDouble) grp;
                                return getGeometry(gqrp, ls.getP(),
                                        ls.getQ());
                            }
                        } else {
                            if (grp == null) {
                                return gqr;
                            } else if (grp instanceof V3D_PointDouble grpp) {
                                V3D_LineSegmentDouble ls = (V3D_LineSegmentDouble) gqr;
                                return getGeometry(grpp, ls.getP(),
                                        ls.getQ());
                            } else {
                                return getGeometry((V3D_LineSegmentDouble) gqr,
                                        (V3D_LineSegmentDouble) grp, epsilon);
                            }
                        }
                    } else if (gpq instanceof V3D_PointDouble gpqp) {
                        if (gqr == null) {
                            if (grp == null) {
                                return gpq;
                            } else if (grp instanceof V3D_PointDouble grpp) {
                                return V3D_LineSegmentDouble.getGeometry(
                                        gpqp, grpp);
                            } else {
                                V3D_LineSegmentDouble ls = (V3D_LineSegmentDouble) grp;
                                return getGeometry(gpqp, ls.getP(),
                                        ls.getQ());
                            }
                        } else if (gqr instanceof V3D_PointDouble gqrp) {
                            if (grp == null) {
                                return gqr;
                            } else if (grp instanceof V3D_PointDouble grpp) {
                                return V3D_LineSegmentDouble.getGeometry(gpqp, gqrp, grpp);
                            } else {
                                V3D_LineSegmentDouble grpl = (V3D_LineSegmentDouble) grp;
                                return getGeometry(grpl, gqrp, gpqp);
                            }
                        } else {
                            V3D_LineSegmentDouble ls = (V3D_LineSegmentDouble) gqr;
                            if (grp == null) {
                                return getGeometry(ls, gpqp);
                            } else if (grp instanceof V3D_PointDouble grpp) {
                                return getGeometry(ls, grpp, gpqp);
                            } else {
                                return getGeometry(ls, 
                                        (V3D_LineSegmentDouble) grp, gpqp, epsilon);
                            }
                        }
                    } else {
                        V3D_LineSegmentDouble gpql = (V3D_LineSegmentDouble) gpq;
                        if (gqr == null) {
                            if (grp == null) {
                                return gpq;
                            } else if (grp instanceof V3D_PointDouble grpp) {
                                return getGeometry(grpp, gpql.getP(),
                                        gpql.getQ());
                            } else {
                                return getGeometry(gpql,
                                        (V3D_LineSegmentDouble) grp, epsilon);
                            }
                        } else if (gqr instanceof V3D_PointDouble gqrp) {
                            if (grp == null) {
                                if (gpql.isIntersectedBy(gqrp)) {
                                    return gpql;
                                } else {
                                    return new V3D_ConvexHullCoplanarDouble(getPl().n, epsilon, gpql.getP(),
                                            gpql.getQ(), gqrp);
                                }
                            } else if (grp instanceof V3D_PointDouble grpp) {
                                ArrayList<V3D_PointDouble> pts = new ArrayList<>();
                                pts.add(gpql.getP());
                                pts.add(gpql.getQ());
                                pts.add(gqrp);
                                pts.add(grpp);
                                ArrayList<V3D_PointDouble> pts2 = V3D_PointDouble.getUnique(pts);
                                if (pts2.size() == 2) {
                                    return new V3D_LineSegmentDouble(pts2.get(0), pts2.get(1));
                                }
                                if (pts2.size() == 3) {
                                    return new V3D_TriangleDouble(pts2.get(0), pts2.get(1), pts2.get(2));
                                }
                                return new V3D_ConvexHullCoplanarDouble(
                                        getPl().n, epsilon, gpql.getP(),
                                        gpql.getQ(), gqrp, grpp);
                            } else {
                                V3D_LineSegmentDouble grpl = (V3D_LineSegmentDouble) grp;
                                return V3D_ConvexHullCoplanarDouble.getGeometry(
                                        epsilon, gpql.getP(),
                                        gpql.getQ(), gqrp, grpl.getP(),
                                        grpl.getQ());
                            }
                        } else {
                            V3D_LineSegmentDouble gqrl = (V3D_LineSegmentDouble) gqr;
                            if (grp == null) {
                                return V3D_ConvexHullCoplanarDouble.getGeometry(
                                        epsilon, 
                                        gpql.getP(), gpql.getQ(),
                                        gqrl.getP(), gqrl.getQ());
                            } else if (grp instanceof V3D_PointDouble grpp) {
                                return V3D_ConvexHullCoplanarDouble.getGeometry(
                                        epsilon, gpql.getP(),
                                        gpql.getQ(), gqrl.getP(),
                                        gqrl.getQ(), grpp);
                            } else {
                                V3D_LineSegmentDouble grpl = (V3D_LineSegmentDouble) grp;
                                return V3D_ConvexHullCoplanarDouble.getGeometry(
                                        epsilon, gpql.getP(),
                                        gpql.getQ(), gqrl.getP(),
                                        gqrl.getQ(), grpl.getP(),
                                        grpl.getQ());
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

//    @Override
//    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
//        return getEnvelope().isIntersectedBy(l, oom);
//    }
    /**
     * Calculate and return the centroid as a point. The original implementation
     * used intersection, but it is simpler to get the average of the x, y and z
     * coordinates from the points at each vertex.
     *
     * @return The centroid point.
     */
    public V3D_PointDouble getCentroid() {
        double dx = (p.dx + q.dx + r.dx) / 3d;
        double dy = (p.dy + q.dy + r.dy) / 3d;
        double dz = (p.dz + q.dz + r.dz) / 3d;
        return new V3D_PointDouble(offset, new V3D_VectorDouble(dx, dy, dz));
    }

    /**
     * Test if two triangles are equal. Two triangles are equal if they have 3
     * coincident points, so even if the order is different and one is clockwise
     * and the other anticlockwise, or one faces the other way.
     *
     * @param t The other triangle to test for equality.
     * @return {@code true} iff {@code this} is equal to {@code t}.
     */
    public boolean equals(V3D_TriangleDouble t) {
        V3D_PointDouble tp = t.getP();
        V3D_PointDouble thisp = getP();
        if (tp.equals(thisp)) {
            V3D_PointDouble tq = t.getQ();
            V3D_PointDouble thisq = getQ();
            if (tq.equals(thisq)) {
                return t.getR().equals(getR());
            } else if (tq.equals(getR())) {
                return t.getR().equals(thisq);
            } else {
                return false;
            }
        } else if (tp.equals(getQ())) {
            V3D_PointDouble tq = t.getQ();
            V3D_PointDouble thisr = getR();
            if (tq.equals(thisr)) {
                return t.getR().equals(thisp);
            } else if (tq.equals(thisp)) {
                return t.getR().equals(thisr);
            } else {
                return false;
            }
        } else if (tp.equals(getR())) {
            V3D_PointDouble tq = t.getQ();
            if (tq.equals(thisp)) {
                return t.getR().equals(getQ());
            } else if (tq.equals(getQ())) {
                return t.getR().equals(thisp);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void translate(V3D_VectorDouble v) {
        super.translate(v);
        if (en != null) {
            en.translate(v);
        }
        if (pq != null) {
            pq.translate(v);
        }
        if (qr != null) {
            qr.translate(v);
        }
        if (rp != null) {
            rp.translate(v);
        }
        if (pl != null) {
            pl.translate(v);
        }
        if (pqpl != null) {
            pqpl.translate(v);
        }
        if (qrpl != null) {
            qrpl.translate(v);
        }
        if (rppl != null) {
            rppl.translate(v);
        }
    }

    @Override
    public V3D_TriangleDouble rotate(V3D_LineDouble axis, double theta, 
            double epsilon) {
        return new V3D_TriangleDouble(
                getP().rotate(axis, theta, epsilon),
                getQ().rotate(axis, theta, epsilon),
                getR().rotate(axis, theta, epsilon));
    }

    /**
     * @param pad Padding
     * @return A String representation of this.
     */
    public String toString(String pad) {
        String res = pad + this.getClass().getSimpleName() + "(\n";
        if (pl != null) {
            res+= pad + " pl=(" + pl.toString(pad + " ") + "),\n";
        }
        res+= pad + " offset=(" + this.offset.toString(pad + " ") + "),\n"
                + pad + " p=(" + this.p.toString(pad + " ") + "),\n"
                + pad + " q=(" + this.q.toString(pad + " ") + "),\n"
                + pad + " r=(" + this.r.toString(pad + " ") + "))";
        return res;
    }

    /**
     * @param pad Padding
     * @return A simple String representation of this.
     */
    public String toStringSimple(String pad) {
        String res = pad + this.getClass().getSimpleName() + "(\n";
        if (pl != null) {
            res+= pad + " pl=(" + pl.toStringSimple(pad + " ") + "),\n";
        }
        res+= pad + " offset=(" + this.offset.toStringSimple("") + "),\n"
                + pad + " p=(" + this.p.toStringSimple("") + "),\n"
                + pad + " q=(" + this.q.toStringSimple("") + "),\n"
                + pad + " r=(" + this.r.toStringSimple("") + "))";
        return res;
    }

    @Override
    public String toString() {
        //return toString("");
        return toStringSimple("");
    }

    /**
     * If p, q and r are equal then the point is returned. If two of the points
     * are the same, then a line segment is returned. If all points are
     * different then a triangle is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, qv)} or
     * {@code new V3D_Triangle(pl, qv, r)}
     */
    public static V3D_FiniteGeometryDouble getGeometry(V3D_PointDouble p, 
            V3D_PointDouble q, V3D_PointDouble r) {
        if (p.equals(q)) {
            return V3D_LineSegmentDouble.getGeometry(p, r);
        } else {
            if (q.equals(r)) {
                return V3D_LineSegmentDouble.getGeometry(q, p);
            } else {
                if (r.equals(p)) {
                    return V3D_LineSegmentDouble.getGeometry(r, q);
                } else {
                    if (V3D_LineDouble.isCollinear(p, q, r)) {
                        V3D_LineSegmentDouble pq = new V3D_LineSegmentDouble(p, q);
                        if (pq.isIntersectedBy(r)) {
                            return pq;
                        } else {
                            V3D_LineSegmentDouble qr = new V3D_LineSegmentDouble(q, r);
                            if (qr.isIntersectedBy(p)) {
                                return qr;
                            } else {
                                return new V3D_LineSegmentDouble(p, r);
                            }
                        }
                    }
                    return new V3D_TriangleDouble(p, q, r);
//                    return new V3D_Triangle(pl.e, V3D_Vector.ZERO,
//                            pl.getVector(pl.e.oom),
//                            qv.getVector(pl.e.oom), r.getVector(pl.e.oom));
                }
            }
        }
    }

    /**
     * Used in intersecting two triangles to give the overall intersection. If
     * l1, l2 and l3 are equal then the line segment is returned. If there are 3
     * unique points then a triangle is returned. If there are 4 or more unique
     * points, then a V3D_ConvexHullCoplanarDouble is returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param l3 A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return either {@code pl} or {@code new V3D_LineSegmentDouble(pl, qv)} or
     * {@code new V3D_TriangleDouble(pl, qv, r)}
     */
    protected static V3D_FiniteGeometryDouble getGeometry(
            V3D_LineSegmentDouble l1, V3D_LineSegmentDouble l2, 
            V3D_LineSegmentDouble l3, double epsilon) {
        V3D_PointDouble l1p = l1.getP();
        V3D_PointDouble l1q = l1.getQ();
        V3D_PointDouble l2p = l2.getP();
        V3D_PointDouble l2q = l2.getQ();
        V3D_PointDouble l3p = l3.getP();
        V3D_PointDouble l3q = l3.getQ();
        ArrayList<V3D_PointDouble> points;
        {
            List<V3D_PointDouble> pts = new ArrayList<>();
            pts.add(l1p);
            pts.add(l1q);
            pts.add(l2p);
            pts.add(l2q);
            pts.add(l3p);
            pts.add(l3q);
            points = V3D_PointDouble.getUnique(pts);
        }
        int n = points.size();
        if (n == 2) {
            return l1;
        } else if (n == 3) {
            Iterator<V3D_PointDouble> ite = points.iterator();
            return getGeometry(ite.next(), ite.next(), ite.next());
        } else {
            V3D_PointDouble[] pts = new V3D_PointDouble[points.size()];
            int i = 0;
            for (var p : points) {
                pts[i] = p;
                i++;
            }
            V3D_PlaneDouble pl = new V3D_PlaneDouble(pts[0], pts[1], pts[2]);
            return new V3D_ConvexHullCoplanarDouble(pl.n, epsilon, pts);
        }
//       // This way returned polygons.
//       } else if (n == 4) {
//            V3D_Triangle t1;
//            V3D_Triangle t2;
//            // Case: quadrangle (closed polygon with 4 sides)
//            V3D_Point illl2 = (V3D_Point) l1.getIntersection(l2, oom);
//            V3D_Point illl3 = (V3D_Point) l1.getIntersection(l3, oom);
//            V3D_Point il2l3 = (V3D_Point) l2.getIntersection(l3, oom);
//            if (illl2 == null) {
//                V3D_Point op1 = l1.l.getOtherPoint(illl3);
//                V3D_Point op2 = l2.l.getOtherPoint(il2l3);
//                t1 = new V3D_Triangle(op1, op2, l3p);
//                t2 = new V3D_Triangle(l3p, l3q, op2);
//            } else {
//                V3D_Point op1 = l1.l.getOtherPoint(illl2);
//                V3D_Point op3;
//                if (illl3 == null) {
//                    op3 = l3.l.getOtherPoint(il2l3);
//                    t1 = new V3D_Triangle(op1, op3, illl2);
//                    t2 = new V3D_Triangle(l3p, l3q, illl2);
//                } else {
//                    op3 = l3.l.getOtherPoint(illl3);
//                    t1 = new V3D_Triangle(op1, op3, illl2);
//                    t2 = new V3D_Triangle(l3p, l3q, illl2);
//                }
//            }
//            return new V3D_Polygon(t1, t2); // 
//            //throw new UnsupportedOperationException("Not supported yet.");
//        } else if (n == 5) {
//            V3D_Triangle t1;
//            V3D_Triangle t2;
//            V3D_Triangle t3;
//            // Case: convex pentagon (closed polygon with 5 sides)
//            V3D_Point illl2 = (V3D_Point) l1.getIntersection(l2, oom);
//            V3D_Point illl3 = (V3D_Point) l1.getIntersection(l3, oom);
//            V3D_Point il2l3 = (V3D_Point) l2.getIntersection(l3, oom);
//            // Find the two lines that intersect
//            if (illl2 == null) {
//                if (illl3 == null) {
//                    // l2 and l3 intersect
//                    V3D_Point op1 = l1.l.getOtherPoint(illl3);
//                    V3D_Point op3 = l3.l.getOtherPoint(illl3);
//                    t1 = new V3D_Triangle(op1, op3, illl3);
//                    t2 = new V3D_Triangle(op1, op3, l2p); // This might be twisted?
//                    t3 = new V3D_Triangle(op3, l2p, l2q);
//                } else {
//                    // l2 and l3 intersect
//                    V3D_Point op2 = l2.l.getOtherPoint(il2l3);
//                    V3D_Point op3 = l3.l.getOtherPoint(il2l3);
//                    t1 = new V3D_Triangle(op2, op3, il2l3);
//                    t2 = new V3D_Triangle(op2, op3, l1p); // This might be twisted?
//                    t3 = new V3D_Triangle(op3, l1p, l1q);
//                }
//            } else {
//                // l1 and l2 intersect
//                V3D_Point op1 = l1.l.getOtherPoint(illl2);
//                V3D_Point op2 = l2.l.getOtherPoint(illl2);
//                t1 = new V3D_Triangle(op1, op2, illl2);
//                t2 = new V3D_Triangle(op1, op2, l3p); // This might be twisted?
//                t3 = new V3D_Triangle(op2, l3p, l3q);
//            }
//            return new V3D_Polygon(t1, t2, t3);
//        } else {
//            // n = 6
//            V3D_Triangle t1;
//            V3D_Triangle t2;
//            V3D_Triangle t3;
//            V3D_Triangle t4;
//            /**
//             * Find the two points that are the minimum distance between any two
//             * lines. This will be an extra side to the triangle.
//             */
//            // dl1l2
//            Math_BigRational dl1pl2p = l1p.getDistanceSquared(l2p, oom);
//            Math_BigRational dl1pl2q = l1p.getDistanceSquared(l2q, oom);
//            Math_BigRational dl1ql2p = l1q.getDistanceSquared(l2p, oom);
//            Math_BigRational dl1ql2q = l1q.getDistanceSquared(l2q, oom);
//            // dl1l3
//            Math_BigRational dl1pl3p = l1p.getDistanceSquared(l3p, oom);
//            Math_BigRational dl1pl3q = l1p.getDistanceSquared(l3q, oom);
//            Math_BigRational dl1ql3p = l1q.getDistanceSquared(l3p, oom);
//            Math_BigRational dl1ql3q = l1q.getDistanceSquared(l3q, oom);
////            // dl2l3
////            Math_BigRational dl2pl3p = l2p.getDistanceSquared(l3p, oom);
////            Math_BigRational dl2pl3q = l2p.getDistanceSquared(l3q, oom);
////            Math_BigRational dl2ql3p = l2q.getDistanceSquared(l3p, oom);
////            Math_BigRational dl2ql3q = l2q.getDistanceSquared(l3q, oom);
//            if (dl1pl2p.compareTo(dl1pl2q) == -1) {
//                if (dl1pl2p.compareTo(dl1ql2q) == -1) {
//                    t1 = new V3D_Triangle(l1p, l1q, l2p);
//                    if (dl1pl2p.compareTo(dl1ql2q) == -1) {
//                        t2 = new V3D_Triangle(l3q, l2p, l2q);
//                        if (dl1ql3p.compareTo(dl1ql3q) == -1) {
//                            t3 = new V3D_Triangle(l3q, l2p, l1q);
//                            t4 = new V3D_Triangle(l3q, l2p, l1q);
//                        } else {
//                            t3 = new V3D_Triangle(l3p, l2p, l1q);
//                            t4 = new V3D_Triangle(l3p, l2p, l1q);
//                        }
//                    } else {
//                        t2 = new V3D_Triangle(l3q, l2p, l2q);
//                        if (dl1pl3p.compareTo(dl1pl3q) == -1) {
//                            t3 = new V3D_Triangle(l3q, l2p, l1q);
//                            t4 = new V3D_Triangle(l3q, l2p, l1q);
//                        } else {
//                            t3 = new V3D_Triangle(l3p, l2p, l1q);
//                            t4 = new V3D_Triangle(l3q, l2p, l1q);
//                        }
//                    }
//                } else {
//                    t1 = new V3D_Triangle(l1p, l1q, l2p);
//                    if (dl1pl2p.compareTo(dl1ql2q) == -1) {
//                        t2 = new V3D_Triangle(l3q, l2p, l2q);
//                        if (dl1ql3p.compareTo(dl1ql3q) == -1) {
//                            t3 = new V3D_Triangle(l1p, l2p, l3q);
//                            t4 = new V3D_Triangle(l3q, l2p, l1q);
//                        } else {
//                            t3 = new V3D_Triangle(l3p, l2p, l1q);
//                            t4 = new V3D_Triangle(l3q, l2p, l1q);
//                        }
//                    } else {
//                        t2 = new V3D_Triangle(l3q, l2p, l2q);
//                        if (dl1pl3p.compareTo(dl1pl3q) == -1) {
//                            t3 = new V3D_Triangle(l3q, l2p, l1q);
//                            t4 = new V3D_Triangle(l3q, l2p, l1q);
//                        } else {
//                            t3 = new V3D_Triangle(l3p, l2p, l1q);
//                            t4 = new V3D_Triangle(l3q, l2p, l1q);
//                        }
//                    }
//                }
//            } else {
//                if (dl1pl2p.compareTo(dl1ql2q) == -1) {
//                    t1 = new V3D_Triangle(l1p, l1q, l2p);
//                    if (dl1pl2p.compareTo(dl1ql2q) == -1) {
//                        t2 = new V3D_Triangle(l3q, l2p, l2q);
//                        if (dl1ql3p.compareTo(dl1ql3q) == -1) {
//                            t3 = new V3D_Triangle(l3q, l2p, l1q);
//                            t4 = new V3D_Triangle(l3q, l2p, l1q);
//                        } else {
//                            t3 = new V3D_Triangle(l3p, l2p, l1q);
//                            t4 = new V3D_Triangle(l3p, l2p, l1q);
//                        }
//                    } else {
//                        t2 = new V3D_Triangle(l3q, l2p, l2q);
//                        if (dl1pl3p.compareTo(dl1pl3q) == -1) {
//                            t3 = new V3D_Triangle(l3q, l2p, l1q);
//                            t4 = new V3D_Triangle(l3q, l2p, l1q);
//                        } else {
//                            t3 = new V3D_Triangle(l3p, l2p, l1q);
//                            t4 = new V3D_Triangle(l3q, l2p, l1q);
//                        }
//                    }
//                } else {
//                    t1 = new V3D_Triangle(l1p, l1q, l2q);
//                    if (dl1pl2p.compareTo(dl1ql2q) == -1) {
//                        t2 = new V3D_Triangle(l3q, l2p, l2q);
//                        if (dl1ql3p.compareTo(dl1ql3q) == -1) {
//                            t3 = new V3D_Triangle(l1p, l2p, l3q);
//                            t4 = new V3D_Triangle(l3q, l2p, l1q);
//                        } else {
//                            t3 = new V3D_Triangle(l3p, l2p, l1q);
//                            t4 = new V3D_Triangle(l3q, l2p, l1q);
//                        }
//                    } else {
//                        t2 = new V3D_Triangle(l3p, l2p, l2q);
//                        if (dl1pl3p.compareTo(dl1pl3q) == -1) {
//                            t3 = new V3D_Triangle(l3q, l2p, l1q);
//                            t4 = new V3D_Triangle(l3q, l2p, l1q);
//                        } else {
//                            t3 = new V3D_Triangle(l3p, l3q, l1q);
//                            t4 = new V3D_Triangle(l3p, l2p, l1q);
//                        }
//                    }
//                }
//            }
//            return new V3D_Polygon(t1, t2, t3, t4);
//        }
    }

    /**
     * Used in intersecting two triangles to give the overall intersection. If
     * there are 3 unique points then a triangle is returned. If there are 4 or
     * more unique points, then a V3D_ConvexHullCoplanar is returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, qv)} or
     * {@code new V3D_Triangle(pl, qv, r)}
     */
    protected static V3D_FiniteGeometryDouble getGeometry(
            V3D_LineSegmentDouble l1, V3D_LineSegmentDouble l2, 
            V3D_PointDouble pt, double epsilon) {
        V3D_PointDouble l1p = l1.getP();
        V3D_PointDouble l1q = l1.getQ();
        V3D_PointDouble l2p = l2.getP();
        V3D_PointDouble l2q = l2.getQ();
        ArrayList<V3D_PointDouble> points;
        {
            List<V3D_PointDouble> pts = new ArrayList<>();
            pts.add(l1p);
            pts.add(l1q);
            pts.add(l2p);
            pts.add(l2q);
            pts.add(pt);
            points = V3D_PointDouble.getUnique(pts);
        }
        int n = points.size();
        if (n == 2) {
            return l1;
        } else if (n == 3) {
            Iterator<V3D_PointDouble> ite = points.iterator();
            return getGeometry(ite.next(), ite.next(), ite.next());
        } else {
            V3D_PointDouble[] pts = new V3D_PointDouble[points.size()];
            int i = 0;
            for (var p : points) {
                pts[i] = p;
                i++;
            }
            V3D_PlaneDouble pl = new V3D_PlaneDouble(pts[0], pts[1], pts[2]);
            return new V3D_ConvexHullCoplanarDouble(pl.n, epsilon, pts);
        }
    }

    /**
     * Used in intersecting a triangle and a tetrahedron. If there are 3 unique
     * points then a triangle is returned. If there are 4 points, then a
     * V3D_ConvexHullCoplanar is returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, qv)} or
     * {@code new V3D_Triangle(pl, qv, r)}
     */
    protected static V3D_FiniteGeometryDouble getGeometry2(
            V3D_LineSegmentDouble l1, V3D_LineSegmentDouble l2, double epsilon) {
        V3D_PointDouble l1p = l1.getP();
        V3D_PointDouble l1q = l1.getQ();
        V3D_PointDouble l2p = l2.getP();
        V3D_PointDouble l2q = l2.getQ();
        ArrayList<V3D_PointDouble> points;
        {
            List<V3D_PointDouble> pts = new ArrayList<>();
            pts.add(l1p);
            pts.add(l1q);
            pts.add(l2p);
            pts.add(l2q);
            points = V3D_PointDouble.getUnique(pts);
        }
        points.add(l1p);
        points.add(l1q);
        points.add(l2p);
        points.add(l2q);
        int n = points.size();
        if (n == 2) {
            return l1;
        } else if (n == 3) {
            Iterator<V3D_PointDouble> ite = points.iterator();
            return getGeometry(ite.next(), ite.next(), ite.next());
        } else {
            V3D_PointDouble[] pts = new V3D_PointDouble[points.size()];
            int i = 0;
            for (var p : points) {
                pts[i] = p;
                i++;
            }
            V3D_PlaneDouble pl = new V3D_PlaneDouble(pts[0], pts[1], pts[2]);
            return new V3D_ConvexHullCoplanarDouble(pl.n, epsilon, pts);
        }
    }

    /**
     * This may be called when there is an intersection of two triangles. If l1
     * and l2 are two sides of a triangle, return the triangle.
     *
     * @param l1 A line segment and triangle edge.
     * @param l2 A line segment and triangle edge.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return a triangle for which l1 and l2 are edges
     */
    protected static V3D_FiniteGeometryDouble getGeometry(
            V3D_LineSegmentDouble l1, V3D_LineSegmentDouble l2,
            double epsilon) {
        V3D_PointDouble pt = (V3D_PointDouble) l1.getIntersection(l2, epsilon);
        V3D_PointDouble l1p = l1.getP();
        V3D_PointDouble l2p = l2.getP();
        if (l1p.equals(pt)) {
            if (l2p.equals(pt)) {
                return new V3D_TriangleDouble(pt, l1.getQ(), l2.getQ());
            } else {
                return new V3D_TriangleDouble(pt, l1.getQ(), l2p);
            }
        } else {
            if (l2p.equals(pt)) {
                return new V3D_TriangleDouble(pt, l1p, l2.getQ());
            } else {
                return new V3D_TriangleDouble(pt, l1p, l2p);
            }
        }
    }

    /**
     * This may be called when there is an intersection of two triangles where l
     * is a side of a triangle and pl is a point.
     *
     * @param l A line segment.
     * @param p1 A point that is either not collinear to l or intersects l.
     * @param p2 A point that is either not collinear to l or intersects l.
     * @return a triangle for which l is an edge and pl is a vertex.
     */
    protected static V3D_FiniteGeometryDouble getGeometry(
            V3D_LineSegmentDouble l, V3D_PointDouble p1, V3D_PointDouble p2) {
        if (l.isIntersectedBy(p1)) {
            return getGeometry(l, p2);
        } else {
            return new V3D_TriangleDouble(p1, l.getP(), l.getQ());
        }
    }

    /**
     * This may be called when there is an intersection of two triangles where l
     * is a side of a triangle and pl is a point that is not collinear to l.
     *
     * @param l A line segment.
     * @param p A point that is not collinear to l.
     * @return a triangle for which l is an edge and pl is a vertex.
     */
    protected static V3D_FiniteGeometryDouble getGeometry(
            V3D_LineSegmentDouble l, V3D_PointDouble p) {
        if (l.isIntersectedBy(p)) {
            return l;
        }
        return new V3D_TriangleDouble(p, l.getP(), l.getQ());
    }

    /**
     * For getting the point opposite a side of a triangle given the side.
     *
     * @param l a line segment either equal to one of the edges of this: 
     * {@link #getPQ()},
     * {@link #getQR()} or
     * {@link #getRP()}.
     * @return The point of {@code this} that does not intersect with {@code l}.
     */
    public V3D_PointDouble getOpposite(V3D_LineSegmentDouble l) {
        if (getPQ().equals(l)) {
            return getR();
        } else {
            if (getQR().equals(l)) {
                return getP();
            } else {
                return getQ();
            }
        }
    }

    /**
     * Get the minimum distance to {@code pv}.
     *
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return The distance squared to {@code pv}.
     */
    public double getDistance(V3D_PointDouble pt, double epsilon) {
        return Math.sqrt(getDistanceSquared(pt, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code pt}.
     *
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return The distance squared to {@code pv}.
     */
    public double getDistanceSquared(V3D_PointDouble pt, double epsilon) {
        if (getPl().isIntersectedBy(pt)) {
            //if (isIntersectedBy0(pt)) {
            if (isAligned(pt)) {
                return 0d;
            } else {
                return getDistanceSquaredEdge(pt, epsilon);
            }
        }
        V3D_PointDouble poi = pl.getPointOfProjectedIntersection(pt, epsilon);
        if (isAligned(poi)) {
            return poi.getDistanceSquared(pt);
        } else {
            return getDistanceSquaredEdge(pt, epsilon);
        }
    }

    /**
     * Get the minimum distance squared to {@code pt} from the perimeter.
     *
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return The distance squared between pt and the nearest edge of this.
     */
    public double getDistanceSquaredEdge(V3D_PointDouble pt, double epsilon) {
        double pqd2 = getPQ().getDistanceSquared(pt, epsilon);
        double qrd2 = getQR().getDistanceSquared(pt, epsilon);
        double rpd2 = getRP().getDistanceSquared(pt, epsilon);
        return Math.min(pqd2, Math.min(qrd2, rpd2));
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @param l A line.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V3D_LineDouble l, double epsilon) {
        return Math.sqrt(getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistanceSquared(V3D_LineDouble l, double epsilon) {
        double dpq2 = getPQ().getDistanceSquared(l, epsilon);
        double dqr2 = getQR().getDistanceSquared(l, epsilon);
        double drp2 = getRP().getDistanceSquared(l, epsilon);
        return Math.min(dpq2,  Math.min(dqr2, drp2));
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V3D_LineSegmentDouble l, double epsilon) {
        return Math.sqrt(getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistanceSquared(V3D_LineSegmentDouble l, double epsilon) {
        if (getIntersection(l, epsilon) != null) {
            return 0d;
        }
        double dlpq2 = l.getDistanceSquared(getPQ(), epsilon);
        double dlqr2 = l.getDistanceSquared(getQR(), epsilon);
        double dlrp2 = l.getDistanceSquared(getRP(), epsilon);
        double d2 = Math.min(dlpq2, Math.min(dlqr2, dlrp2));
        /**
         * For any end points of l that are aligned with this, calculate the
         * distances as these could be the minimum.
         */
        V3D_PointDouble lp = l.getP();
        V3D_PointDouble lq = l.getQ();
        if (isAligned(lp)) {
            d2 = Math.min(d2, getDistanceSquared(lp, epsilon));
        }
        if (isAligned(lq)) {
            d2 = Math.min(d2, getDistanceSquared(lq, epsilon));
        }
        return d2;
    }

    /**
     * Get the minimum distance to {@code pl}.
     *
     * @param pl A plane.
     * @return The minimum distance squared to {@code pv}.
     */
    public double getDistance(V3D_PlaneDouble pl) {
        return Math.sqrt(getDistanceSquared(pl));
    }

    /**
     * Get the minimum distance squared to {@code pl}.
     *
     * @param pl A plane.
     * @return The minimum distance squared to {@code pv}.
     */
    public double getDistanceSquared(V3D_PlaneDouble pl) {
        double dplpq2 = pl.getDistanceSquared(getPQ());
        double dplqr2 = pl.getDistanceSquared(getQR());
//        double dplrp2 = pl.getDistanceSquared(getRP());
//        return Math_BigRational.min(dplpq2, dplqr2, dplrp2);
        return Math.min(dplpq2, dplqr2);
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A triangle.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return The minimum distance squared to {@code t}.
     */
    public double getDistance(V3D_TriangleDouble t, double epsilon) {
        return Math.sqrt(getDistanceSquared(t, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A triangle.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return The minimum distance squared to {@code t}.
     */
    public double getDistanceSquared(V3D_TriangleDouble t, double epsilon) {
        if (getIntersection(t, epsilon) != null) {
            return 0d;
        }
        double dtpq2 = t.getDistanceSquared(getPQ(), epsilon);
        double dtqr2 = t.getDistanceSquared(getQR(), epsilon);
        double dtrp2 = t.getDistanceSquared(getRP(), epsilon);
        double dpq2 = getDistanceSquared(t.getPQ(), epsilon);
        double dqr2 = getDistanceSquared(t.getQR(), epsilon);
        double drp2 = getDistanceSquared(t.getRP(), epsilon);
        double d2 = Math.min(dtpq2, Math.min(dtqr2, Math.min(dtrp2, Math.min(dpq2,
                Math.min(dqr2, drp2)))));
        /**
         * If any of the points of t are aligned with this, then these could be
         * closest.
         */
        V3D_PointDouble pt = t.getP();
        if (isAligned(pt)) {
            d2 = Math.min(d2, getDistanceSquared(pt, epsilon));
        }
        pt = t.getQ();
        if (isAligned(pt)) {
            d2 = Math.min(d2, getDistanceSquared(pt, epsilon));
        }
        pt = t.getR();
        if (isAligned(pt)) {
            d2 = Math.min(d2, getDistanceSquared(pt, epsilon));
        }
        /**
         * If any of the points of this are aligned with t, then these could be
         * closest.
         */
        pt = getP();
        if (t.isAligned(pt)) {
            d2 = Math.min(d2, t.getDistanceSquared(pt, epsilon));
        }
        pt = getQ();
        if (t.isAligned(pt)) {
            d2 = Math.min(d2, t.getDistanceSquared(pt, epsilon));
        }
        pt = getR();
        if (t.isAligned(pt)) {
            d2 = Math.min(d2, t.getDistanceSquared(pt, epsilon));
        }
        return d2;
    }

    /**
     * For retrieving a Set of points that are the corners of the triangles.
     *
     * @param triangles The input.
     * @return A Set of points that are the corners of the triangles.
     */
    //public static ArrayList<V3D_Point> getPoints(V3D_Triangle[] triangles) {
    public static V3D_PointDouble[] getPoints(V3D_TriangleDouble[] triangles) {
        List<V3D_PointDouble> s = new ArrayList<>();
        for (var t : triangles) {
            s.add(t.getP());
            s.add(t.getQ());
            s.add(t.getR());
        }
        ArrayList<V3D_PointDouble> points = V3D_PointDouble.getUnique(s);
        return points.toArray(V3D_PointDouble[]::new);
    }

    /**
     * Clips this using pl and returns the part that is on the same side as pt.
     *
     * @param pl The plane that clips.
     * @param pt A point that is used to return the side of the clipped
     * triangle.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometryDouble clip(V3D_PlaneDouble pl, V3D_PointDouble pt, 
            double epsilon) {
        V3D_FiniteGeometryDouble i = getIntersection(pl, epsilon);
        V3D_PointDouble ppt = getPl().getP();
        if (i == null) {
            if (pl.isOnSameSide(ppt, pt)) {
                return this;
            } else {
                return null;
            }
        } else if (i instanceof V3D_PointDouble ip) {
            /**
             * If at least two points of the triangle are on the same side of pl
             * as pt, then return this, otherwise return ip. As the calculation
             * of i is perhaps imprecise, then simply testing if ip equals one
             * of the triangle corner points and then testing another point to
             * see if it that is on the same side as pt might not work out
             * right!
             */
            int poll = 0;
            if (pl.isOnSameSide(ppt, pt)) {
                poll++;
            }
            if (pl.isOnSameSide(getQ(), pt)) {
                poll++;
            }
            if (pl.isOnSameSide(getR(), pt)) {
                poll++;
            }
            if (poll > 1) {
                return this;
            } else {
                return ip;
            }
        } else {
            // i instanceof V3D_LineSegment
            V3D_LineSegmentDouble il = (V3D_LineSegmentDouble) i;
            V3D_PointDouble qpt = getQ();
            V3D_PointDouble rpt = getR();
            if (pl.isOnSameSide(ppt, pt)) {
                if (pl.isOnSameSide(qpt, pt)) {
                    if (pl.isOnSameSide(rpt, pt)) {
                        return this;
                    } else {
                        return getGeometry(il, getPQ(), epsilon);
                    }
                } else {
                    if (pl.isOnSameSide(rpt, pt)) {
                        return getGeometry(il, getRP(), epsilon);
                    } else {
                        return getGeometry(il, ppt);
                    }
                }
            } else {
                if (pl.isOnSameSide(qpt, pt)) {
                    if (pl.isOnSameSide(rpt, pt)) {
                        return getGeometry(il, getPQ(), epsilon);
                    } else {
                        return getGeometry(il, qpt);
                    }
                } else {
                    if (pl.isOnSameSide(rpt, pt)) {
                        return getGeometry(il, rpt);
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    /**
     * Clips this using t.
     *
     * @param t The triangle to clip this with.
     * @param pt A point that is used to return the side of this that is
     * clipped.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometryDouble clip(V3D_TriangleDouble t, 
            V3D_PointDouble pt, double epsilon) {
        V3D_PointDouble tp = t.getP();
        V3D_PointDouble tq = t.getQ();
        V3D_PointDouble tr = t.getR();
        V3D_VectorDouble n = t.getPl().n;
        V3D_PointDouble ppt = new V3D_PointDouble(tp.offset.add(n), tp.rel);
        V3D_PlaneDouble ppl = new V3D_PlaneDouble(tp, tq, ppt);
        V3D_PointDouble qpt = new V3D_PointDouble(tq.offset.add(n), tq.rel);
        V3D_PlaneDouble qpl = new V3D_PlaneDouble(tq, tr, qpt);
        V3D_PointDouble rpt = new V3D_PointDouble(tr.offset.add(n), tr.rel);
        V3D_PlaneDouble rpl = new V3D_PlaneDouble(tr, tp, rpt);
        V3D_FiniteGeometryDouble cppl = clip(ppl, pt, epsilon);
        if (cppl == null) {
            return null;
        } else if (cppl instanceof V3D_PointDouble) {
            return cppl;
        } else if (cppl instanceof V3D_LineSegmentDouble cppll) {
            V3D_FiniteGeometryDouble cppllcqpl = cppll.clip(qpl, pt, epsilon);
            if (cppllcqpl == null) {
                return null;
            } else if (cppllcqpl instanceof V3D_PointDouble cppllcqplp) {
                return getGeometry(cppll, cppllcqplp);
                //return cppllcqpl;
            } else {
                return ((V3D_LineSegmentDouble) cppllcqpl).clip(rpl, pt, epsilon);
            }
        } else if (cppl instanceof V3D_TriangleDouble cpplt) {
            V3D_FiniteGeometryDouble cppltcqpl = cpplt.clip(qpl, pt, epsilon);
            if (cppltcqpl == null) {
                return null;
            } else if (cppltcqpl instanceof V3D_PointDouble) {
                return cppltcqpl;
            } else if (cppltcqpl instanceof V3D_LineSegmentDouble cppltcqpll) {
                return cppltcqpll.clip(rpl, pt, epsilon);
            } else if (cppltcqpl instanceof V3D_TriangleDouble cppltcqplt) {
                return cppltcqplt.clip(rpl, pt, epsilon);
            } else {
                V3D_ConvexHullCoplanarDouble c = (V3D_ConvexHullCoplanarDouble) cppltcqpl;
                return c.clip(rpl, pt, epsilon);
            }
        } else {
            V3D_ConvexHullCoplanarDouble c = (V3D_ConvexHullCoplanarDouble) cppl;
            V3D_FiniteGeometryDouble cc = c.clip(qpl, pt, epsilon);
            if (cc == null) {
                return cc;
            } else if (cc instanceof V3D_PointDouble) {
                return cc;
            } else if (cc instanceof V3D_LineSegmentDouble cppll) {
                V3D_FiniteGeometryDouble cccqpl = cppll.clip(qpl, pt, epsilon);
                if (cccqpl == null) {
                    return null;
                } else if (cccqpl instanceof V3D_PointDouble) {
                    return cccqpl;
                } else {
                    return ((V3D_LineSegmentDouble) cccqpl).clip(rpl, pt, epsilon);
                }
            } else if (cc instanceof V3D_TriangleDouble ccct) {
                return ccct.clip(rpl, pt, epsilon);
            } else {
                V3D_ConvexHullCoplanarDouble ccc = (V3D_ConvexHullCoplanarDouble) cc;
                return ccc.clip(rpl, pt, epsilon);
            }
        }
    }
}
