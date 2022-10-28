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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;

/**
 * For representing and processing triangles in 3D. A triangle has a non-zero
 * area. The corner points are {@link #pl}, {@link #q} and {@link #r}. The
 * following depicts a generic triangle {@code
 *                           pq
 *  p *- - - - - - - - - - - + - - - - - - - - - - -* q
 *     \~                   mpq                   ~/
 *      \  ~                 |                 ~  /
 *       \    ~              |              ~    /
 *        \      ~           |           ~      /
 *         \        ~        |        ~        /
 *          \          ~     |     ~          /
 *           \            ~  |  ~            /
 *       -n  -n  -n  -n  -n  c  +n  +n  +n  +n  +n  normal heading out from the page.
 *             \          ~  |  ~          /
 *              \      ~     |     ~      /
 *               \  ~        |        ~  /
 *                + mrp      |      mqr +
 *             rp  \         |         /  qr
 *                  \        |        /
 *                   \       |       /
 *                    \      |      /
 *                     \     |     /
 *                      \    |    /
 *                       \   |   /
 *                        \  |  /
 *                         \ | /
 *                          \|/
 *                           *
 *                           r
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Triangle extends V3D_FiniteGeometry implements V3D_Face {

    private static final long serialVersionUID = 1L;

    /**
     * The plane of the triangle
     */
    public V3D_Plane pl;

    /**
     * Defines one of the corners of the triangle.
     */
    public V3D_Vector p;

    /**
     * Defines one of the corners of the triangle.
     */
    public V3D_Vector q;

    /**
     * Defines one of the corners of the triangle.
     */
    public V3D_Vector r;

    /**
     * For storing the line segment from {@link #getP()} to {@link #getQ()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    private V3D_LineSegment pq;

    /**
     * The Order of Magnitude for the precision of pq.
     */
    int pqoom;

    /**
     * The RoundingMode used for the calculation of pq.
     */
    RoundingMode pqrm;

    /**
     * For storing the line segment from {@link #getQ()} to {@link #getR()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    private V3D_LineSegment qr;

    /**
     * The Order of Magnitude for the precision of qr.
     */
    int qroom;

    /**
     * The RoundingMode used for the calculation of qr.
     */
    RoundingMode qrrm;

    /**
     * For storing the line segment from {@link #getR()} to {@link #getP()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    private V3D_LineSegment rp;

    /**
     * The Order of Magnitude for the precision of rp.
     */
    int rpoom;

    /**
     * The RoundingMode used for the calculation of rp.
     */
    RoundingMode rprm;

    /**
     * For storing the plane aligning with {@link #pq} in the direction of the
     * plane normal and with a normal orthogonal to the plane normal.
     */
    private V3D_Plane pqpl;

    /**
     * For storing the plane aligning with {@link #qr} in the direction of the
     * plane normal and with a normal orthogonal to the plane normal.
     */
    private V3D_Plane qrpl;

    /**
     * For storing the plane aligning with {@link #rp} in the direction of the
     * plane normal and with a normal orthogonal to the plane normal.
     */
    private V3D_Plane rppl;

//    /**
//     * For storing the midpoint between {@link #getP()} and {@link #getQ()} at
//     * a given Order of Magnitude and RoundingMode precision.
//     */
//    private V3D_Point mpq;
//
//    /**
//     * For storing the midpoint between {@link #getQ()} and {@link #getR()} at
//     * a given Order of Magnitude and RoundingMode precision.
//     */
//    private V3D_Point mqr;
//
//    /**
//     * For storing the midpoint between {@link #getR()} and {@link #getP()} at
//     * a given Order of Magnitude and RoundingMode precision.
//     */
//    private V3D_Point mrp;
//
//    /**
//     * For storing the centroid at a specific Order of Magnitude and 
//     * RoundingMode precision.
//     */
//    private V3D_Point c;
    /**
     * Creates a new triangle.
     *
     * @param t The triangle to clone.
     */
    public V3D_Triangle(V3D_Triangle t) {
        super(t.offset);
        pl = new V3D_Plane(t.pl);
        p = new V3D_Vector(t.p);
        q = new V3D_Vector(t.q);
        r = new V3D_Vector(t.r);
    }

    /**
     * Creates a new triangle. {@link #offset} is set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param p What {@link #pl} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Triangle(V3D_Vector p, V3D_Vector q,
            V3D_Vector r, int oom, RoundingMode rm) {
        this(V3D_Vector.ZERO, p, q, r, oom, rm);
    }

    /**
     * Creates a new triangle.
     *
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #pl} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Triangle(V3D_Vector offset, V3D_Vector p,
            V3D_Vector q, V3D_Vector r, int oom, RoundingMode rm) {
        super(offset);
        this.pl = new V3D_Plane(V3D_Vector.ZERO, p, q, r, oom, rm);
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Triangle(V3D_LineSegment l, V3D_Vector r, int oom, RoundingMode rm) {
        this(l.offset, l.l.p, l.q, r, oom, rm);
    }

    /**
     * Creates a new triangle.
     *
     * @param l A line segment representing one of the three edges of the
     * triangle.
     * @param r Defines the other point relative to l.offset that defines the
     * triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Triangle(V3D_LineSegment l, V3D_Point r, int oom, RoundingMode rm) {
        this(l.offset, l.l.p, l.q, r.getVector(oom, rm).subtract(l.offset, oom, rm), oom, rm);
    }

    /**
     * Creates a new instance.
     *
     * @param p Used to initialise {@link #offset} and {@link #pl}.
     * @param q Used to initialise {@link #q}.
     * @param r Used to initialise {@link #r}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Triangle(V3D_Point p, V3D_Point q, V3D_Point r, int oom, RoundingMode rm) {
        super(p.offset);
        this.p = p.rel;
        this.q = q.getVector(oom, rm).subtract(p.offset, oom, rm);
        this.r = r.getVector(oom, rm).subtract(p.offset, oom, rm);
        this.pl = new V3D_Plane(p.offset, p.rel, this.q, this.r, oom, rm);
    }

    /**
     * @return A new point based on {@link #p} and {@link #offset}.
     */
    public final V3D_Point getP() {
        return new V3D_Point(offset, p);
    }

    /**
     * @return A new point based on {@link #q} and {@link #offset}.
     */
    public final V3D_Point getQ() {
        return new V3D_Point(offset, q);
    }

    /**
     * @return A new point based on {@link #r} and {@link #offset}.
     */
    public final V3D_Point getR() {
        return new V3D_Point(offset, r);
    }

    /**
     * For getting the line segment from {@link #getP()} to {@link #getQ()} with
     * at least oom precision given rm.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Line segment from r to p.
     */
    public final V3D_LineSegment getPQ(int oom, RoundingMode rm) {
        if (pq == null) {
            initPQ(oom, rm);
        } else {
            if (oom < pqoom) {
                initPQ(oom, rm);
            } else {
                if (!pqrm.equals(rm)) {
                    initPQ(oom, rm);
                }
            }
        }
        return pq;
    }

    private void initPQ(int oom, RoundingMode rm) {
        pq = new V3D_LineSegment(offset, p, q, oom, rm);
        pqoom = oom;
        pqrm = rm;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code q.subtract(p, oom, rm)}
     */
    public final V3D_Vector getPQV(int oom, RoundingMode rm) {
        return q.subtract(p, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code r.subtract(q, oom, rm)}
     */
    public final V3D_Vector getQRV(int oom, RoundingMode rm) {
        return r.subtract(q, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code p.subtract(r, oom, rm)}
     */
    public final V3D_Vector getRPV(int oom, RoundingMode rm) {
        return p.subtract(r, oom, rm);
    }

    /**
     * For getting the line segment from {@link #getQ()} to {@link #getR()} with
     * at least oom precision given rm.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Line segment from r to p.
     */
    public final V3D_LineSegment getQR(int oom, RoundingMode rm) {
        if (qr == null) {
            initQR(oom, rm);
        } else {
            if (oom < qroom) {
                initQR(oom, rm);
            } else {
                if (!qrrm.equals(rm)) {
                    initQR(oom, rm);
                }
            }
        }
        return qr;
    }

    private void initQR(int oom, RoundingMode rm) {
        qr = new V3D_LineSegment(offset, q, r, oom, rm);
        qroom = oom;
        qrrm = rm;
    }

    /**
     * For getting the line segment from {@link #getR()} to {@link #getP()} with
     * at least oom precision given rm.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Line segment from r to p.
     */
    public final V3D_LineSegment getRP(int oom, RoundingMode rm) {
        if (rp == null) {
            initRP(oom, rm);
        } else {
            if (oom < rpoom) {
                initRP(oom, rm);
            } else {
                if (!rprm.equals(rm)) {
                    initRP(oom, rm);
                }
            }
        }
        return rp;
    }

    private void initRP(int oom, RoundingMode rm) {
        rp = new V3D_LineSegment(offset, r, p, oom, rm);
        rpoom = oom;
        rprm = rm;
    }

    /**
     * For getting the plane through {@link #pq} in the direction of the normal.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The plane through {@link #pq} in the direction of the normal.
     */
    public V3D_Plane getPQPl(int oom, RoundingMode rm) {
        if (pqpl == null) {
            pqpl = new V3D_Plane(getPQ(oom, rm), pl.n, oom, rm);
        } else {
            if (oom < pqoom) {
                pqpl = new V3D_Plane(getPQ(oom, rm), pl.n, oom, rm);
            } else {
                if (!pqrm.equals(rm)) {
                    pqpl = new V3D_Plane(getPQ(oom, rm), pl.n, oom, rm);
                }
            }
        }
        return pqpl;
    }

    /**
     * For getting the plane through {@link #qr} in the direction of the normal.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The plane through {@link #qr} in the direction of the normal.
     */
    public V3D_Plane getQRPl(int oom, RoundingMode rm) {
        if (qrpl == null) {
            qrpl = new V3D_Plane(getQR(oom, rm), pl.n, oom, rm);
        } else {
            if (oom < qroom) {
                qrpl = new V3D_Plane(getQR(oom, rm), pl.n, oom, rm);
            } else {
                if (!qrrm.equals(rm)) {
                    qrpl = new V3D_Plane(getQR(oom, rm), pl.n, oom, rm);
                }
            }
        }
        return qrpl;
    }

    /**
     * For getting the plane through {@link #rp} in the direction of the normal.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The plane through {@link #rp} in the direction of the normal.
     */
    public V3D_Plane getRPPl(int oom, RoundingMode rm) {
        if (rppl == null) {
            rppl = new V3D_Plane(getRP(oom, rm), pl.n, oom, rm);
        } else {
            if (oom < rpoom) {
                rppl = new V3D_Plane(getRP(oom, rm), pl.n, oom, rm);
            } else {
                if (!rprm.equals(rm)) {
                    rppl = new V3D_Plane(getRP(oom, rm), pl.n, oom, rm);
                }
            }
        }
        return rppl;
    }

    @Override
    public V3D_Envelope getEnvelope(int oom, RoundingMode rm) {
        if (en == null) {
            en = new V3D_Envelope(oom, rm, getP(), getQ(), getR());
        }
        return en;
    }

    @Override
    public V3D_Point[] getPoints(int oom, RoundingMode rm) {
        V3D_Point[] re = new V3D_Point[3];
        re[0] = getP();
        re[1] = getQ();
        re[2] = getR();
        return re;
    }

    /**
     * @param pt The point to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point or line segment.
     */
    public boolean isIntersectedBy(V3D_Point pt, int oom, RoundingMode rm) {
        if (getEnvelope(oom, rm).isIntersectedBy(pt, oom, rm)) {
            if (pl.isIntersectedBy(pt, oom, rm)) {
                return isIntersectedBy0(pt, oom, rm);
            }
        }
        return false;
    }

    /**
     * @param pt The point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} if this intersects with {@code pt}.
     */
    protected boolean isIntersectedBy0(V3D_Point pt, int oom, RoundingMode rm) {
        V3D_LineSegment tpq = getPQ(oom, rm);
        V3D_LineSegment tqr = getQR(oom, rm);
        V3D_LineSegment trp = getRP(oom, rm);
        if (tpq.isIntersectedBy(pt, oom, rm)
                || tqr.isIntersectedBy(pt, oom, rm)
                || trp.isIntersectedBy(pt, oom, rm)) {
            return true;
        }
        V3D_Vector ppt = new V3D_Vector(getP(), pt, oom, rm);
        V3D_Vector qpt = new V3D_Vector(getQ(), pt, oom, rm);
        V3D_Vector rpt = new V3D_Vector(getR(), pt, oom, rm);
        V3D_Vector cp = tpq.l.v.getCrossProduct(ppt, oom, rm);
        V3D_Vector cq = tqr.l.v.getCrossProduct(qpt, oom, rm);
        V3D_Vector cr = trp.l.v.getCrossProduct(rpt, oom, rm);
        /**
         * If cp, cq and cr are all in the same direction then pt intersects.
         */
        if (cp.dx.isNegative() == cq.dx.isNegative() && cp.dx.isNegative() == cr.dx.isNegative()) {
            if (cp.dy.isNegative() == cq.dy.isNegative() && cp.dy.isNegative() == cr.dy.isNegative()) {
                if (cp.dz.isNegative() == cq.dz.isNegative() && cp.dz.isNegative() == cr.dz.isNegative()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A point aligns with this if the planes given by each triangle edge given
     * by the normal find the point to the same side as the other point of the
     * triangle. The normal may be imprecisely calculated, so the calculated
     * points on the planes and the definition of the planes may be imprecise.
     * Greater precision can be gained using a smaller oom.
     *
     * @param p The point to check if it is in alignment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff pl is aligned with this.
     */
    public boolean isAligned(V3D_Point p, int oom, RoundingMode rm) {
        V3D_Vector n = pl.n;
        V3D_Plane lp = new V3D_Plane(offset, this.p, q, this.p.add(n, oom, rm), oom, rm);
        if (lp.isOnSameSide(p, getR(), oom, rm)) {
            V3D_Plane lq = new V3D_Plane(offset, q, r, q.add(n, oom, rm), oom, rm);
            if (lq.isOnSameSide(p, this.pl.getP(), oom, rm)) {
                V3D_Plane lr = new V3D_Plane(offset, r, this.p,
                        r.add(n, oom, rm), oom, rm);
                if (lr.isOnSameSide(p, getQ(), oom, rm)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A line segment aligns with this if both end points are in line.
     *
     * @param l The line segment to check if it is in alignment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff l is aligned with this.
     */
    public boolean isAligned(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (isAligned(l.getP(), oom, rm)) {
            return isAligned(l.getQ(), oom, rm);
        }
        return false;
    }

    /**
     * A triangle aligns with this if all points are in line.
     *
     * @param t The triangle to check if it is in alignment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff l is aligned with this.
     */
    public boolean isAligned(V3D_Triangle t, int oom, RoundingMode rm) {
        if (isAligned(t.getP(), oom, rm)) {
            if (isAligned(t.getQ(), oom, rm)) {
                return isAligned(t.getR(), oom, rm);
            }
        }
        return false;
    }

    /**
     * Check a side for intersection.
     *
     * @param t The triangle to check the points to see if they are all on the
     * same side of a line that intersects the edge of another triangle.
     * @param n The normal of the other triangle (the same as the normal of
     * {@code t} - as the triangles are already known to be in the same plane).
     * @param v The vector of the line.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} if an intersection is found and {@code false}
     * otherwise.
     */
    protected boolean checkSide(V3D_Triangle t, V3D_Vector n, V3D_Vector v,
            int oom, RoundingMode rm) {
        V3D_Vector cp = n.getCrossProduct(v, oom, rm);
        int cpv = v.getDotProduct(cp, oom, rm).compareTo(Math_BigRational.ZERO);
        if (cpv == 0) {
            return true;
        } else if (cpv == 1) {
            //int cqv = t.getQV().getDotProduct(cp, oom, rm).compareTo(Math_BigRational.ZERO);
            int cqv = t.q.getDotProduct(cp, oom, rm).compareTo(Math_BigRational.ZERO);
            if (cqv == 0) {
                return true;
            } else {
                if (cqv == 1) {
                    //int crv = t.p.getRV().getDotProduct(cp, oom, rm).compareTo(Math_BigRational.ZERO);
                    int crv = t.r.getDotProduct(cp, oom, rm).compareTo(Math_BigRational.ZERO);
                    if (crv == 0) {
                        return true;
                    } else {
                        return crv != 1;
                    }
                } else {
                    return true;
                }
            }
        } else {
            //int cqv = t.p.getQV().getDotProduct(cp, oom, rm).compareTo(Math_BigRational.ZERO);
            int cqv = t.q.getDotProduct(cp, oom, rm).compareTo(Math_BigRational.ZERO);
            if (cqv == 0) {
                return true;
            } else {
                if (cqv == 1) {
                    return true;
                } else {
                    //int crv = t.p.getRV().getDotProduct(cp, oom, rm).compareTo(Math_BigRational.ZERO);
                    int crv = t.r.getDotProduct(cp, oom, rm).compareTo(Math_BigRational.ZERO);
                    if (crv == 0) {
                        return true;
                    } else {
                        return crv == 1;
                    }
                }
            }
        }
    }

    @Override
    public BigDecimal getArea(int oom, RoundingMode rm) {
        return getPQV(oom, rm).getCrossProduct(getRPV(oom, rm).reverse(), oom, rm)
                .getMagnitude().divide(Math_BigRational.TWO, oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public BigDecimal getPerimeter(int oom, RoundingMode rm) {
        int oomN1 = oom - 1;
        return Math_BigDecimal.round(getPQ(oom, rm).getLength(oom, rm).toBigDecimal(oomN1, rm)
                .add(getQR(oom, rm).getLength(oom, rm).toBigDecimal(oomN1, rm))
                .add(getRP(oom, rm).getLength(oom, rm).toBigDecimal(oomN1, rm)),
                oom, rm);
    }

    /**
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point or line segment.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Line l, int oom,
            RoundingMode rm) {
        V3D_Geometry i = pl.getIntersection(l, oom, rm);
        if (i == null) {
            return null;
        } else if (i instanceof V3D_Point ip) {
            if (isAligned(ip, oom, rm)) {
                return ip;
            } else {
                return null;
            }
        } else {
            /**
             * Get the intersection of the line and each edge of the triangle.
             */
            V3D_FiniteGeometry lpqi = getPQ(oom, rm).getIntersection(l, oom, rm);
            V3D_FiniteGeometry lqri = getQR(oom, rm).getIntersection(l, oom, rm);
            V3D_FiniteGeometry lrpi = getRP(oom, rm).getIntersection(l, oom, rm);
            if (lpqi == null) {
                if (lqri == null) {
                    return null; // This should not happen!
                } else {
                    if (lrpi == null) {
                        return lqri;
                    } else {
                        return getGeometry(((V3D_Point) lqri).getVector(oom, rm),
                                ((V3D_Point) lrpi).getVector(oom, rm), oom, rm);
                    }
                }
            } else if (lpqi instanceof V3D_Point lpqip) {
                if (lqri == null) {
                    if (lrpi == null) {
                        return lpqi;
                    } else {
                        return getGeometry(lpqip.getVector(oom, rm),
                                ((V3D_Point) lrpi).getVector(oom, rm), oom, rm);
                    }
                } else if (lqri instanceof V3D_Point lqrip) {
                    if (lrpi == null) {
                        return getGeometry(lqrip.getVector(oom, rm),
                                lpqip.getVector(oom, rm), oom, rm);
                    } else if (lrpi instanceof V3D_LineSegment) {
                        return lrpi;
                    } else {
                        return getGeometry(lpqip, lqrip, (V3D_Point) lrpi, oom, rm);
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
     * {@link V3D_Vector#ZERO}.
     *
     * @param v1 A vector.
     * @param v2 A vector.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Either a line segment or a point.
     */
    public static V3D_FiniteGeometry getGeometry(
            V3D_Vector v1, V3D_Vector v2, int oom, RoundingMode rm) {
        if (v1.equals(v2)) {
            return new V3D_Point(v1);
        } else {
            return new V3D_LineSegment(v1, v2, oom, rm);
        }
    }

    /**
     * Get the intersection between the geometry and the line segment {@code l}.
     *
     * @param l The line segment to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment l, int oom,
            RoundingMode rm) {
        V3D_Point lp = l.getP();
        V3D_Point lq = l.getQ();
//        boolean lip = isIntersectedBy(l.pl, oom);
//        boolean liq = isIntersectedBy(l.q, oom);
        boolean lip = isIntersectedBy(lp, oom, rm);
        boolean liq = isIntersectedBy(lq, oom, rm);
        if (lip) {
            if (liq) {
                return l;
            } else {
                V3D_Geometry li = getIntersection(l.l, oom, rm);
                if (li == null) {
                    return null;
                }
                if (li instanceof V3D_Point) {
//                    return l.pl;
                    return lp;
                } else {
                    return ((V3D_LineSegment) li).getIntersection(l, oom, rm);
                }
            }
        } else {
            V3D_Geometry li = getIntersection(l.l, oom, rm);
            if (li == null) {
                return null;
            }
            if (liq) {
                if (li instanceof V3D_Point) {
                    //return l.q;
                    return lq;
                } else {
                    return ((V3D_LineSegment) li).getIntersection(l, oom, rm);
                }
            } else {
                if (li instanceof V3D_Point pli) {
                    if (l.isIntersectedBy(pli, oom, rm)) {
                        return pli;
                    } else {
                        return null;
                    }
                } else {
                    return ((V3D_LineSegment) li).getIntersection(l, oom, rm);
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code this} and {@code pl}.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Plane pl, int oom,
            RoundingMode rm) {
        // Get intersection if this were a plane
        //V3D_Geometry pi = pl.getIntersection(this, oom);
        V3D_Geometry pi = pl.getIntersection(this.pl, oom, rm);
        if (pi == null) {
            return null;
        } else if (pi instanceof V3D_Plane) {
            return this;
        } else {
            // (pi instanceof V3D_Line)
            V3D_FiniteGeometry gPQ = pl.getIntersection(getPQ(oom, rm), oom, rm);
            if (gPQ == null) {
                V3D_FiniteGeometry gQR = pl.getIntersection(getQR(oom, rm), oom, rm);
                if (gQR == null) {
                    V3D_FiniteGeometry gRP = pl.getIntersection(getRP(oom, rm), oom, rm);
                    if (gRP == null) {
                        return null;
                    } else {
                        return gRP;
                    }
                } else {
                    V3D_Geometry gRP = pl.getIntersection(getRP(oom, rm), oom, rm);
                    if (gRP == null) {
                        return gQR;
                    } else {
                        if (gRP instanceof V3D_Point gRPp) {
                            return V3D_LineSegment.getGeometry((V3D_Point) gQR,
                                    gRPp, oom, rm);
                        } else {
                            return V3D_Triangle.getGeometry((V3D_LineSegment) gRP,
                                    (V3D_Point) gQR, oom, rm);
                        }
                    }
                }
            } else if (gPQ instanceof V3D_LineSegment) {
                return gPQ;
            } else {
                V3D_FiniteGeometry gQR = pl.getIntersection(getQR(oom, rm), oom, rm);
                if (gQR == null) {
                    V3D_FiniteGeometry gRP = pl.getIntersection(getRP(oom, rm), oom, rm);
                    if (gRP == null) {
                        return (V3D_Point) gPQ;
                    } else if (gRP instanceof V3D_LineSegment) {
                        return gRP;
                    } else {
                        return V3D_LineSegment.getGeometry((V3D_Point) gPQ,
                                (V3D_Point) gRP, oom, rm);
                    }
                } else {
                    if (gQR instanceof V3D_Point gQRp) {
                        return V3D_LineSegment.getGeometry((V3D_Point) gPQ, gQRp, oom, rm);
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
     * @param t The triangle to test for intersection with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Triangle t, int oom,
            RoundingMode rm) {
        if (getEnvelope(oom, rm).isIntersectedBy(t.getEnvelope(oom, rm), oom, rm)) {
            V3D_FiniteGeometry g = t.getIntersection(pl, oom, rm);
            if (g == null) {
                return g;
            } else {
                if (g instanceof V3D_Point pt) {
                    if (t.isIntersectedBy(pt, oom, rm)) {
                        return pt;
                    } else {
                        return null;
                    }
                } else if (g instanceof V3D_LineSegment l) {
                    return t.getIntersection(l, oom, rm);
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
                    boolean pi = isIntersectedBy(t.getP(), oom, rm);
                    boolean qi = isIntersectedBy(t.getQ(), oom, rm);
                    boolean ri = isIntersectedBy(t.getR(), oom, rm);
                    if (pi && qi && ri) {
                        return t;
                    }
                    boolean pit = t.isIntersectedBy(getP(), oom, rm);
                    boolean qit = t.isIntersectedBy(getQ(), oom, rm);
                    boolean rit = t.isIntersectedBy(getR(), oom, rm);
                    if (pit && qit && rit) {
                        return this;
                    }
                    V3D_FiniteGeometry gpq = t.getIntersection(getPQ(oom, rm), oom, rm);
                    V3D_FiniteGeometry gqr = t.getIntersection(getQR(oom, rm), oom, rm);
                    V3D_FiniteGeometry grp = t.getIntersection(getRP(oom, rm), oom, rm);
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
                        } else if (gqr instanceof V3D_Point gqrp) {
                            if (grp == null) {
                                return gqr;
                            } else if (grp instanceof V3D_Point grpp) {
                                return V3D_LineSegment.getGeometry(
                                        gqrp, grpp, oom, rm);
                            } else {
                                V3D_LineSegment ls = (V3D_LineSegment) grp;
                                return getGeometry(gqrp, ls.getP(),
                                        ls.getQ(), oom, rm);
                            }
                        } else {
                            if (grp == null) {
                                return gqr;
                            } else if (grp instanceof V3D_Point grpp) {
                                V3D_LineSegment ls = (V3D_LineSegment) gqr;
                                return getGeometry(grpp, ls.getP(),
                                        ls.getQ(), oom, rm);
                            } else {
                                return getGeometry((V3D_LineSegment) gqr,
                                        (V3D_LineSegment) grp, oom, rm);
                            }
                        }
                    } else if (gpq instanceof V3D_Point gpqp) {
                        if (gqr == null) {
                            if (grp == null) {
                                return gpq;
                            } else if (grp instanceof V3D_Point grpp) {
                                return V3D_LineSegment.getGeometry(
                                        gpqp, grpp, oom, rm);
                            } else {
                                V3D_LineSegment ls = (V3D_LineSegment) grp;
                                return getGeometry(gpqp, ls.getP(),
                                        ls.getQ(), oom, rm);
                            }
                        } else if (gqr instanceof V3D_Point gqrp) {
                            if (grp == null) {
                                return gqr;
                            } else if (grp instanceof V3D_Point grpp) {
                                return V3D_LineSegment.getGeometry(gqrp, grpp, oom, rm); // Check!
                            } else {
                                V3D_LineSegment grpl = (V3D_LineSegment) grp;
                                return getGeometry(grpl, gqrp, gpqp, oom,
                                        rm);
                            }
                        } else {
                            V3D_LineSegment ls = (V3D_LineSegment) gqr;
                            if (grp == null) {
                                return getGeometry(ls, gpqp, oom, rm);
                            } else if (grp instanceof V3D_Point grpp) {
                                return getGeometry(ls, grpp, gpqp, oom, rm);
                            } else {
                                return null; // TODO: Figure out the geometry (point and two line segments).
                            }
                        }
                    } else {
                        V3D_LineSegment gpql = (V3D_LineSegment) gpq;
                        if (gqr == null) {
                            if (grp == null) {
                                return gpq;
                            } else if (grp instanceof V3D_Point grpp) {
                                return getGeometry(grpp, gpql.getP(),
                                        gpql.getQ(), oom, rm);
                            } else {
                                return getGeometry(gpql,
                                        (V3D_LineSegment) grp, oom, rm);
                            }
                        } else if (gqr instanceof V3D_Point gqrp) {
                            if (grp == null) {
                                if (gpql.isIntersectedBy(gqrp, oom, rm)) {
                                    return gpql;
                                } else {
                                    return new V3D_ConvexHullCoplanar(oom,
                                            rm, pl.n, gpql.getP(),
                                            gpql.getQ(), gqrp);
                                }
                            } else if (grp instanceof V3D_Point grpp) {
                                ArrayList<V3D_Point> pts = new ArrayList<>();
                                pts.add(gpql.getP());
                                pts.add(gpql.getQ());
                                pts.add(gqrp);
                                pts.add(grpp);
                                ArrayList<V3D_Point> pts2 = V3D_Point.getUnique(pts, oom, rm);
                                if (pts2.size() == 2) {
                                    return new V3D_LineSegment(pts2.get(0), pts2.get(1), oom, rm);
                                }
                                return new V3D_ConvexHullCoplanar(oom,
                                        rm, pl.n, gpql.getP(),
                                        gpql.getQ(), gqrp, grpp);
                            } else {
                                V3D_LineSegment grpl = (V3D_LineSegment) grp;
                                return new V3D_ConvexHullCoplanar(oom,
                                        rm, pl.n, gpql.getP(),
                                        gpql.getQ(), gqrp, grpl.getP(),
                                        grpl.getQ());
                            }
                        } else {
                            V3D_LineSegment gqrl = (V3D_LineSegment) gqr;
                            if (grp == null) {
                                return new V3D_ConvexHullCoplanar(oom,
                                        rm, pl.n, gpql.getP(),
                                        gpql.getQ(), gqrl.getP(),
                                        gqrl.getQ());
                            } else if (grp instanceof V3D_Point grpp) {
                                return new V3D_ConvexHullCoplanar(oom,
                                        rm, pl.n, gpql.getP(),
                                        gpql.getQ(), gqrl.getP(),
                                        gqrl.getQ(), grpp);
                            } else {
                                V3D_LineSegment grpl = (V3D_LineSegment) grp;
                                return new V3D_ConvexHullCoplanar(oom,
                                        rm, pl.n, gpql.getP(),
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The centroid point.
     */
    public V3D_Point getCentroid(int oom, RoundingMode rm) {
//        V3D_Point mpq = getPQ().getMidpoint(oom);
//        V3D_Point mqr = getQR().getMidpoint(oom);
//        V3D_Point mrp = getRP().getMidpoint(oom);
////        V3D_LineSegment lmpqr = new V3D_LineSegment(mpq, getRP().pl, oom);
////        V3D_LineSegment lmqrp = new V3D_LineSegment(mqr, getP(oom), oom);
//        V3D_LineSegment lmpqr = new V3D_LineSegment(offset, mpq.getRel(), getRP().getP(), oom);
//        V3D_LineSegment lmqrp = new V3D_LineSegment(offset, mqr.getRel(), getRP().getQ(), oom);
//        //V3D_LineSegment lmrpq = new V3D_LineSegment(mrp, getQ(oom), oom);
//        V3D_Point c0 = (V3D_Point) lmpqr.getIntersection(lmqrp, oom, true);
//        //V3D_Point c1 = (V3D_Point) lmpqr.getIntersection(lmrpq, oom, true);
//        //V3D_Point c2 = (V3D_Point) lmrpq.getIntersection(lmqrp, oom, true);
//        //System.out.println(toString());
//        //System.out.println("c0=" + c0.toString());
//        //System.out.println("c1=" + c1.toString());
//        //System.out.println("c2=" + c2.toString());
//        return c0;
//        return new V3D_Point(e, offset, getPV().add(getQV(), oom)
//                .add(getRV(), oom).divide(Math_BigRational.valueOf(3), oom));
        oom -= 6;
        Math_BigRational dx = (p.getDX(oom, rm).add(q.getDX(oom, rm))
                .add(r.getDX(oom, rm))).divide(3).round(oom, rm);
        Math_BigRational dy = (p.getDY(oom, rm).add(q.getDY(oom, rm))
                .add(r.getDY(oom, rm))).divide(3).round(oom, rm);
        Math_BigRational dz = (p.getDZ(oom, rm).add(q.getDZ(oom, rm))
                .add(r.getDZ(oom, rm))).divide(3).round(oom, rm);
        return new V3D_Point(offset, new V3D_Vector(dx, dy, dz));
    }

    /**
     * Test if two triangles are equal. Two triangles are equal if they have 3
     * coincident points, so even if the order is different and one is clockwise
     * and the other anticlockwise, or one faces the other way.
     *
     * @param t The other triangle to test for equality.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is equal to {@code t}.
     */
    public boolean equals(V3D_Triangle t, int oom, RoundingMode rm) {
        V3D_Point tp = t.getP();
        V3D_Point thisp = getP();
        if (tp.equals(thisp, oom, rm)) {
            V3D_Point tq = t.getQ();
            V3D_Point thisq = getQ();
            if (tq.equals(thisq, oom, rm)) {
                return t.getR().equals(getR(), oom, rm);
            } else if (tq.equals(getR(), oom, rm)) {
                return t.getR().equals(thisq, oom, rm);
            } else {
                return false;
            }
        } else if (tp.equals(getQ(), oom, rm)) {
            V3D_Point tq = t.getQ();
            V3D_Point thisr = getR();
            if (tq.equals(thisr, oom, rm)) {
                return t.getR().equals(thisp, oom, rm);
            } else if (tq.equals(thisp, oom, rm)) {
                return t.getR().equals(thisr, oom, rm);
            } else {
                return false;
            }
        } else if (tp.equals(getR(), oom, rm)) {
            V3D_Point tq = t.getQ();
            if (tq.equals(thisp, oom, rm)) {
                return t.getR().equals(getQ(), oom, rm);
            } else if (tq.equals(getQ(), oom, rm)) {
                return t.getR().equals(thisp, oom, rm);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        pl.translate(v, oom, rm);
    }

    @Override
    public V3D_Triangle rotate(V3D_Vector axisOfRotation, Math_BigRational theta,
            int oom, RoundingMode rm) {
        return new V3D_Triangle(
                getP().rotate(axisOfRotation, theta, oom, rm),
                getQ().rotate(axisOfRotation, theta, oom, rm),
                getR().rotate(axisOfRotation, theta, oom, rm), oom, rm);
    }

    /**
     * @param pad Padding
     * @return A String representation of this.
     */
    public String toString(String pad) {
        return pad + this.getClass().getSimpleName() + "(\n"
                + pad + " pl=(" + pl.toString(pad + " ") + "),\n"
                + pad + " offset=(" + this.offset.toString(pad + " ") + "),\n"
                + pad + " p=(" + this.p.toString(pad + " ") + "),\n"
                + pad + " q=(" + this.q.toString(pad + " ") + "),\n"
                + pad + " r=(" + this.r.toString(pad + " ") + "))";
    }

    /**
     * @param pad Padding
     * @return A simple String representation of this.
     */
    public String toStringSimple(String pad) {
        return pad + this.getClass().getSimpleName() + "(\n"
                + pad + " pl=(" + pl.toStringSimple(pad + " ") + "),\n"
                + pad + " offset=(" + this.offset.toStringSimple("") + "),\n"
                + pad + " p=(" + this.p.toStringSimple("") + "),\n"
                + pad + " q=(" + this.q.toStringSimple("") + "),\n"
                + pad + " r=(" + this.r.toStringSimple("") + "))";
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, q)} or
     * {@code new V3D_Triangle(pl, q, r)}
     */
    public static V3D_FiniteGeometry getGeometry(V3D_Point p, V3D_Point q,
            V3D_Point r, int oom, RoundingMode rm) {
        if (p.equals(q, oom, rm)) {
            return V3D_LineSegment.getGeometry(p, r, oom, rm);
        } else {
            if (q.equals(r, oom, rm)) {
                return V3D_LineSegment.getGeometry(q, p, oom, rm);
            } else {
                if (r.equals(p, oom, rm)) {
                    return V3D_LineSegment.getGeometry(r, q, oom, rm);
                } else {
                    if (V3D_Line.isCollinear(oom, rm, p, q, r)) {
                        V3D_LineSegment pq = new V3D_LineSegment(p, q, oom, rm);
                        if (pq.isIntersectedBy(r, oom, rm)) {
                            return pq;
                        } else {
                            V3D_LineSegment qr = new V3D_LineSegment(q, r, oom, rm);
                            if (qr.isIntersectedBy(p, oom, rm)) {
                                return qr;
                            } else {
                                return new V3D_LineSegment(p, r, oom, rm);
                            }
                        }
                    }
                    return new V3D_Triangle(p, q, r, oom, rm);
//                    return new V3D_Triangle(pl.e, V3D_Vector.ZERO,
//                            pl.getVector(pl.e.oom),
//                            q.getVector(pl.e.oom), r.getVector(pl.e.oom));
                }
            }
        }
    }

    /**
     * Used in intersecting two triangles to give the overall intersection. If
     * l1, l2 and l3 are equal then the line segment is returned. If there are 3
     * unique points then a triangle is returned. If there are 4 or more unique
     * points, then a V3D_ConvexHullCoplanar is returned.
     *
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param l3 A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, q)} or
     * {@code new V3D_Triangle(pl, q, r)}
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_LineSegment l1,
            V3D_LineSegment l2, V3D_LineSegment l3, int oom, RoundingMode rm) {
        V3D_Point l1p = l1.getP();
        V3D_Point l1q = l1.getQ();
        V3D_Point l2p = l2.getP();
        V3D_Point l2q = l2.getQ();
        V3D_Point l3p = l3.getP();
        V3D_Point l3q = l3.getQ();
        ArrayList<V3D_Point> points;
        {
            List<V3D_Point> pts = new ArrayList<>();
            pts.add(l1p);
            pts.add(l1q);
            pts.add(l2p);
            pts.add(l2q);
            pts.add(l3p);
            pts.add(l3q);
            points = V3D_Point.getUnique(pts, oom, rm);
        }
        int n = points.size();
        if (n == 2) {
            return l1;
        } else if (n == 3) {
            Iterator<V3D_Point> ite = points.iterator();
            return getGeometry(ite.next(), ite.next(), ite.next(), oom, rm);
        } else {
            V3D_Point[] pts = new V3D_Point[points.size()];
            int i = 0;
            for (var p : points) {
                pts[i] = p;
                i++;
            }
            V3D_Plane pl = new V3D_Plane(pts[0], pts[1], pts[2], oom, rm);
            return new V3D_ConvexHullCoplanar(oom, rm, pl.n, pts);
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
     * Used in intersecting a triangle and a tetrahedron. If there are 3 unique
     * points then a triangle is returned. If there are 4 points, then a
     * V3D_ConvexHullCoplanar is returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, q)} or
     * {@code new V3D_Triangle(pl, q, r)}
     */
    protected static V3D_FiniteGeometry getGeometry2(V3D_LineSegment l1,
            V3D_LineSegment l2, int oom, RoundingMode rm) {
        V3D_Point l1p = l1.getP();
        V3D_Point l1q = l1.getQ();
        V3D_Point l2p = l2.getP();
        V3D_Point l2q = l2.getQ();
        ArrayList<V3D_Point> points;
        {
            List<V3D_Point> pts = new ArrayList<>();
            pts.add(l1p);
            pts.add(l1q);
            pts.add(l2p);
            pts.add(l2q);
            points = V3D_Point.getUnique(pts, oom, rm);
        }
        points.add(l1p);
        points.add(l1q);
        points.add(l2p);
        points.add(l2q);
        int n = points.size();
        if (n == 2) {
            return l1;
        } else if (n == 3) {
            Iterator<V3D_Point> ite = points.iterator();
            return getGeometry(ite.next(), ite.next(), ite.next(), oom, rm);
        } else {
            V3D_Point[] pts = new V3D_Point[points.size()];
            int i = 0;
            for (var p : points) {
                pts[i] = p;
                i++;
            }
            V3D_Plane pl = new V3D_Plane(pts[0], pts[1], pts[2], oom, rm);
            return new V3D_ConvexHullCoplanar(oom, rm, pl.n, pts);
        }
    }

    /**
     * This may be called when there is an intersection of two triangles. If l1
     * and l2 are two sides of a triangle, return the triangle.
     *
     * @param l1 A line segment and triangle edge.
     * @param l2 A line segment and triangle edge.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return a triangle for which l1 and l2 are edges
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_LineSegment l1,
            V3D_LineSegment l2, int oom, RoundingMode rm) {
        V3D_Point pt = (V3D_Point) l1.getIntersection(l2, oom, rm);
        V3D_Point l1p = l1.getP();
        V3D_Point l2p = l2.getP();
        if (l1p.equals(pt, oom, rm)) {
            if (l2p.equals(pt, oom, rm)) {
                return new V3D_Triangle(pt, l1.getQ(), l2.getQ(), oom, rm);
            } else {
                return new V3D_Triangle(pt, l1.getQ(), l2p, oom, rm);
            }
        } else {
            if (l2p.equals(pt, oom, rm)) {
                return new V3D_Triangle(pt, l1p, l2.getQ(), oom, rm);
            } else {
                return new V3D_Triangle(pt, l1p, l2p, oom, rm);
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return a triangle for which l is an edge and pl is a vertex.
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_LineSegment l,
            V3D_Point p1, V3D_Point p2, int oom, RoundingMode rm) {
        if (l.isIntersectedBy(p1, oom, rm)) {
            return getGeometry(l, p2, oom, rm);
        } else {
            return new V3D_Triangle(p1, l.getP(), l.getQ(), oom, rm);
        }
    }

    /**
     * This may be called when there is an intersection of two triangles where l
     * is a side of a triangle and pl is a point that is not collinear to l.
     *
     * @param l A line segment.
     * @param p A point that is not collinear to l.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return a triangle for which l is an edge and pl is a vertex.
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_LineSegment l,
            V3D_Point p, int oom, RoundingMode rm) {
        if (l.isIntersectedBy(p, oom, rm)) {
            return l;
        }
        return new V3D_Triangle(p, l.getP(), l.getQ(), oom, rm);
    }

    /**
     * For getting the point opposite a side of a triangle given the side.
     *
     * @param l a line segment either equal to one of the edges of this - null
     * null null null     {@link #getPQ(int, java.math.RoundingMode)},
     * {@link #getQR(int, java.math.RoundingMode)} or
     * {@link #getRP(int, java.math.RoundingMode)}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The point of {@code this} that does not intersect with {@code l}.
     */
    public V3D_Point getOpposite(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (getPQ(oom, rm).equals(l, oom, rm)) {
            return getR();
        } else {
            if (getQR(oom, rm).equals(l, oom, rm)) {
                return getP();
            } else {
                return getQ();
            }
        }
    }

    /**
     * Get the minimum distance to {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared to {@code p}.
     */
    public BigDecimal getDistance(V3D_Point p, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code pt}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared to {@code p}.
     */
    public Math_BigRational getDistanceSquared(V3D_Point pt, int oom, RoundingMode rm) {
        if (pl.isIntersectedBy(pt, oom, rm)) {
            if (isIntersectedBy0(pt, oom, rm)) {
                return Math_BigRational.ZERO;
            } else {
                return getDistanceSquaredEdge(pt, oom, rm);
            }
        }
        Math_BigRational d2e = getDistanceSquaredEdge(pt, oom, rm);
        V3D_Point poi = pl.getPointOfProjectedIntersection(pt, oom, rm);
        if (isAligned(poi, oom, rm)) {
            return poi.getDistanceSquared(pt, oom, rm).min(d2e);
        } else {
            return d2e;
        }
    }

    /**
     * Get the minimum distance squared to {@code pt} from the perimeter.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared between pt and the nearest edge of this.
     */
    public Math_BigRational getDistanceSquaredEdge(V3D_Point pt, int oom,
            RoundingMode rm) {
        Math_BigRational pqd2 = getPQ(oom, rm).getDistanceSquared(pt, oom, rm);
        Math_BigRational qrd2 = getQR(oom, rm).getDistanceSquared(pt, oom, rm);
        Math_BigRational rpd2 = getRP(oom, rm).getDistanceSquared(pt, oom, rm);
        return Math_BigRational.min(pqd2, qrd2, rpd2);
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigDecimal getDistance(V3D_Line l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom, RoundingMode rm) {
        Math_BigRational dpq2 = getPQ(oom, rm).getDistanceSquared(l, oom, rm);
        Math_BigRational dqr2 = getQR(oom, rm).getDistanceSquared(l, oom, rm);
        Math_BigRational drp2 = getRP(oom, rm).getDistanceSquared(l, oom, rm);
        return Math_BigRational.min(dpq2, dqr2, drp2);
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigDecimal getDistance(V3D_LineSegment l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom,
            RoundingMode rm) {
        if (getIntersection(l, oom, rm) != null) {
            return Math_BigRational.ZERO;
        }
        Math_BigRational dlpq2 = l.getDistanceSquared(getPQ(oom, rm), oom, rm);
        Math_BigRational dlqr2 = l.getDistanceSquared(getQR(oom, rm), oom, rm);
        Math_BigRational dlrp2 = l.getDistanceSquared(getRP(oom, rm), oom, rm);
        /**
         * If l is in line, then we also need to calculate the distances from
         * each line segment end points.
         */
        Math_BigRational dtlp2 = getDistanceSquared(l.getP(), oom, rm);
        Math_BigRational dtlq2 = getDistanceSquared(l.getQ(), oom, rm);
        return Math_BigRational.min(dlpq2, dlqr2, dlrp2, dtlp2, dtlq2);
    }

    /**
     * Get the minimum distance to {@code p}.
     *
     * @param pl A plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code p}.
     */
    public BigDecimal getDistance(V3D_Plane pl, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(pl, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code pl}.
     *
     * @param pl A plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code p}.
     */
    public Math_BigRational getDistanceSquared(V3D_Plane pl, int oom, RoundingMode rm) {
        Math_BigRational dplpq2 = pl.getDistanceSquared(getPQ(oom, rm), oom, rm);
        Math_BigRational dplqr2 = pl.getDistanceSquared(getQR(oom, rm), oom, rm);
//        Math_BigRational dplrp2 = pl.getDistanceSquared(getRP(oom, rm), oom, rm);
//        return Math_BigRational.min(dplpq2, dplqr2, dplrp2);
        return Math_BigRational.min(dplpq2, dplqr2);
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code t}.
     */
    public BigDecimal getDistance(V3D_Triangle t, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(t, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code t}.
     */
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom,
            RoundingMode rm) {
        if (getIntersection(t, oom, rm) != null) {
            return Math_BigRational.ZERO;
        }
        Math_BigRational dtpq2 = t.getDistanceSquared(getPQ(oom, rm), oom, rm);
        Math_BigRational dtqr2 = t.getDistanceSquared(getQR(oom, rm), oom, rm);
        Math_BigRational dtrp2 = t.getDistanceSquared(getRP(oom, rm), oom, rm);
        Math_BigRational dpq2 = getDistanceSquared(t.getPQ(oom, rm), oom, rm);
        Math_BigRational dqr2 = getDistanceSquared(t.getQR(oom, rm), oom, rm);
        Math_BigRational drp2 = getDistanceSquared(t.getRP(oom, rm), oom, rm);
        Math_BigRational minE = Math_BigRational.min(dtpq2, dtqr2, dtrp2, dpq2,
                dqr2, drp2);
        if (isAligned(t, oom, rm)) {
            Math_BigRational dp2 = getDistanceSquared(t.getP(), oom, rm);
            Math_BigRational dq2 = getDistanceSquared(t.getQ(), oom, rm);
            Math_BigRational dr2 = getDistanceSquared(t.getR(), oom, rm);
            return Math_BigRational.min(minE, dp2, dq2, dr2);
        } else {
            if (t.isAligned(this, oom, rm)) {
                Math_BigRational dp2 = t.getDistanceSquared(getP(), oom, rm);
                Math_BigRational dq2 = t.getDistanceSquared(getQ(), oom, rm);
                Math_BigRational dr2 = t.getDistanceSquared(getR(), oom, rm);
                return Math_BigRational.min(minE, dp2, dq2, dr2);
            }
        }
        return minE;
    }

    /**
     * For retrieving a Set of points that are the corners of the triangles.
     *
     * @param triangles The input.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A Set of points that are the corners of the triangles.
     */
    //public static ArrayList<V3D_Point> getPoints(V3D_Triangle[] triangles) {
    public static V3D_Point[] getPoints(V3D_Triangle[] triangles, int oom, RoundingMode rm) {
        List<V3D_Point> s = new ArrayList<>();
        for (var t : triangles) {
            s.add(t.getP());
            s.add(t.getQ());
            s.add(t.getR());
        }
        ArrayList<V3D_Point> points = V3D_Point.getUnique(s, oom, rm);
        return points.toArray(V3D_Point[]::new);
    }

    /**
     * Clips this using pl and returns the part that is on the same side as pt.
     *
     * @param pl The plane that clips.
     * @param pt A point that is used to return the side of the clipped
     * triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometry clip(V3D_Plane pl, V3D_Point pt, int oom, RoundingMode rm) {
        V3D_FiniteGeometry i = getIntersection(pl, oom, rm);
        V3D_Point ppt = this.pl.getP();
        if (i == null) {
            if (pl.isOnSameSide(ppt, pt, oom, rm)) {
                return this;
            } else {
                return null;
            }
        } else if (i instanceof V3D_Point ip) {
            /**
             * If at least two points of the triangle are on the same side of pl
             * as pt, then return this, otherwise return ip. As the calcualtion
             * of i is perhaps imprecise, then simply testing if ip equals one
             * of the triangle corner points and then testing another point to
             * see if it that is on the same side as pt might not work out
             * right!
             *
             */
            int poll = 0;
            if (pl.isOnSameSide(ppt, pt, oom, rm)) {
                poll++;
            }
            if (pl.isOnSameSide(getQ(), pt, oom, rm)) {
                poll++;
            }
            if (pl.isOnSameSide(getR(), pt, oom, rm)) {
                poll++;
            }
            if (poll > 1) {
                return this;
            } else {
                return ip;
            }
        } else {
            // i instanceof V3D_LineSegment
            V3D_LineSegment il = (V3D_LineSegment) i;
            V3D_Point qpt = getQ();
            V3D_Point rpt = getR();
            if (pl.isOnSameSide(ppt, pt, oom, rm)) {
                if (pl.isOnSameSide(qpt, pt, oom, rm)) {
                    if (pl.isOnSameSide(rpt, pt, oom, rm)) {
                        return this;
                    } else {
                        return getGeometry(il, getPQ(oom, rm), oom, rm);
                    }
                } else {
                    if (pl.isOnSameSide(rpt, pt, oom, rm)) {
                        return getGeometry(il, getRP(oom, rm), oom, rm);
                    } else {
                        return getGeometry(il, ppt, oom, rm);
                    }
                }
            } else {
                if (pl.isOnSameSide(qpt, pt, oom, rm)) {
                    if (pl.isOnSameSide(rpt, pt, oom, rm)) {
                        return getGeometry(il, getPQ(oom, rm), oom, rm);
                    } else {
                        return getGeometry(il, qpt, oom, rm);
                    }
                } else {
                    if (pl.isOnSameSide(rpt, pt, oom, rm)) {
                        return getGeometry(il, rpt, oom, rm);
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometry clip(V3D_Triangle t, V3D_Point pt, int oom, RoundingMode rm) {
        V3D_Point tp = t.getP();
        V3D_Point tq = t.getQ();
        V3D_Point tr = t.getR();
        V3D_Vector n = t.pl.n;
        V3D_Point pp = new V3D_Point(tp.offset.add(n, oom, rm), tp.rel);
        V3D_Plane ppl = new V3D_Plane(tp, tq, pp, oom, rm);
        V3D_Point qp = new V3D_Point(tq.offset.add(n, oom, rm), tq.rel);
        V3D_Plane qpl = new V3D_Plane(tq, tr, qp, oom, rm);
        V3D_Point rp = new V3D_Point(tr.offset.add(n, oom, rm), tr.rel);
        V3D_Plane rpl = new V3D_Plane(tr, tp, rp, oom, rm);
        V3D_FiniteGeometry cppl = clip(ppl, pt, oom, rm);
        if (cppl == null) {
            return null;
        } else if (cppl instanceof V3D_Point) {
            return cppl;
        } else if (cppl instanceof V3D_LineSegment cppll) {
            V3D_FiniteGeometry cppllcqpl = cppll.clip(qpl, pt, oom, rm);
            if (cppllcqpl == null) {
                return null;
            } else if (cppllcqpl instanceof V3D_Point cppllcqplp) {
                return getGeometry(cppll, cppllcqplp, oom, rm);
                //return cppllcqpl;
            } else {
                return ((V3D_LineSegment) cppllcqpl).clip(rpl, pt, oom, rm);
            }
        } else if (cppl instanceof V3D_Triangle cpplt) {
            V3D_FiniteGeometry cppltcqpl = cpplt.clip(qpl, pt, oom, rm);
            if (cppltcqpl == null) {
                return null;
            } else if (cppltcqpl instanceof V3D_Point) {
                return cppltcqpl;
            } else if (cppltcqpl instanceof V3D_LineSegment cppltcqpll) {
                return cppltcqpll.clip(rpl, pt, oom, rm);
            } else if (cppltcqpl instanceof V3D_Triangle cppltcqplt) {
                return cppltcqplt.clip(rpl, pt, oom, rm);
            } else {
                V3D_ConvexHullCoplanar c = (V3D_ConvexHullCoplanar) cppltcqpl;
                return c.clip(rpl, pt, oom, rm);
            }
        } else {
            V3D_ConvexHullCoplanar c = (V3D_ConvexHullCoplanar) cppl;
            V3D_FiniteGeometry cc = c.clip(qpl, pt, oom, rm);
            if (cc == null) {
                return cc;
            } else if (cc instanceof V3D_Point) {
                return cc;
            } else if (cc instanceof V3D_LineSegment cppll) {
                V3D_FiniteGeometry cccqpl = cppll.clip(qpl, pt, oom, rm);
                if (cccqpl == null) {
                    return null;
                } else if (cccqpl instanceof V3D_Point) {
                    return cccqpl;
                } else {
                    return ((V3D_LineSegment) cccqpl).clip(rpl, pt, oom, rm);
                }
            } else if (cc instanceof V3D_Triangle ccct) {
                return ccct.clip(rpl, pt, oom, rm);
            } else {
                V3D_ConvexHullCoplanar ccc = (V3D_ConvexHullCoplanar) cc;
                return ccc.clip(rpl, pt, oom, rm);
            }
        }
    }
}
