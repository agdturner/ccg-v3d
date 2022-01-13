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

/**
 * For representing and processing triangles in 3D. A triangle has a non-zero
 * area. The corner points are {@link #p}, {@link #q} and {@link #r}. The
 * following depicts a generic triangle {@code
 *                          pq
 *  p *- - - - - - - - - - - + - - - - - - - - - - -* q
 *     \~                   mpq                   ~/
 *      \  ~                 |                 ~  /
 *       \    ~              |              ~    /
 *        \      ~           |           ~      /
 *         \        ~        |        ~        /
 *          \          ~     |     ~          /
 *           \            ~  |  ~            /
 *            \              c              /
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
 * The mid points of the triangle edges may or may not be possible to pin point.
 * Likewise the centroid which is at the intersection of the line segments from
 * these midpoints to the opposite corner.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Triangle extends V3D_Plane implements V3D_Face {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    protected V3D_Envelope en;

//    /**
//     * The line from {@link #p} to {@link #q}.
//     */
//    private V3D_LineSegment lpq;
//
//    /**
//     * The line from {@link #q} to {@link #r}.
//     */
//    private V3D_LineSegment lqr;
//
//    /**
//     * The line from {@link #r} to {@link #p}.
//     */
//    private V3D_LineSegment lrp;
//
//    /**
//     * The midpoint between {@link #p} and {@link #q}.
//     */
//    private V3D_Point mpq;
//
//    /**
//     * The midpoint between {@link #q} and {@link #r}.
//     */
//    private V3D_Point mqr;
//
//    /**
//     * The midpoint between {@link #r} and {@link #p}.
//     */
//    private V3D_Point mrp;
//
//    /**
//     * The centroid.
//     */
//    private V3D_Point c;
    /**
     * Creates a new triangle.
     *
     * @param t The triangle to clone.
     */
    public V3D_Triangle(V3D_Triangle t) {
        super(t);
    }

    /**
     * Creates a new triangle. {@link #offset} is set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     * @param oom What {@link #oom} is set to.
     */
    public V3D_Triangle(V3D_Vector p, V3D_Vector q,
            V3D_Vector r, int oom) {
        super(V3D_Vector.ZERO, p, q, r, oom);
    }

    /**
     * Creates a new triangle.
     *
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     * @param oom What {@link #oom} is set to.
     */
    public V3D_Triangle(V3D_Vector offset, V3D_Vector p, V3D_Vector q,
            V3D_Vector r, int oom) {
        super(offset, p, q, r, oom);
    }

    /**
     * Creates a new triangle.
     *
     * @param l A line segment representing one of the three edges of the
     * triangle.
     * @param r Defines the other point relative l.offset that defines the
     * triangle.
     * @param oom The Order of Magnitude for the initialisation.
     */
    public V3D_Triangle(V3D_LineSegment l, V3D_Vector r, int oom) {
        super(l.offset, l.getP(), l.getQ(), r, oom);
    }

    /**
     * Creates a new triangle.
     *
     * @param lpq A line segment representing the edge of the triangle from p to
     * q.
     * @param lqr A line segment representing the edge of the triangle from q to
     * r.
     * @param lrp A line segment representing the edge of the triangle from r to
     * p.
     * @param oom The Order of Magnitude for the initialisation.
     */
    public V3D_Triangle(V3D_LineSegment lpq, V3D_LineSegment lqr,
            V3D_LineSegment lrp, int oom) {
        super(V3D_Vector.ZERO, lpq.getP(oom).getVector(oom),
                lpq.getQ(oom).getVector(oom), lqr.getQ(oom).getVector(oom), oom);
    }

    @Override
    public String toString() {
        return toString("");
    }

    @Override
    public V3D_Envelope getEnvelope(int oom) {
        if (en == null) {
            en = new V3D_Envelope(oom, getP(), getQ(), getR());
        }
        return en;
    }

//    /**
//     * @param v The vector to apply.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return a new rectangle.
//     */
//    @Override
//    public V3D_Triangle apply(V3D_Vector v, int oom) {
//        return new V3D_Triangle(getP(oom).apply(v, oom), 
//                getQ(oom).apply(v, oom), getR(oom).apply(v, oom), oom);
//    }
    /**
     * @param pt The point to intersect with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @param b Distinguishes this from
     * {@link V3D_Plane#isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int)}.
     * @return A point or line segment.
     */
    public boolean isIntersectedBy(V3D_Point pt, int oom, boolean b) {
        if (getEnvelope(oom).isIntersectedBy(pt, oom)) {
            if (isIntersectedBy(pt, oom)) {
                return isIntersectedBy0(pt, oom);
            }
        }
        return false;
    }

    /**
     * @param pt The point.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} if this intersects with {@code pt}.
     */
    protected boolean isIntersectedBy0(V3D_Point pt, int oom) {
        if (getPQ().isIntersectedBy(pt, oom) || getQR().isIntersectedBy(pt, oom)
                || getRP().isIntersectedBy(pt, oom)) {
            return true;
        }
        V3D_Vector ppt = new V3D_Vector(getP(), pt, oom);
        V3D_Vector qpt = new V3D_Vector(getQ(), pt, oom);
        V3D_Vector rpt = new V3D_Vector(getR(), pt, oom);
        V3D_Vector cp = getPQV().getCrossProduct(ppt, oom);
        V3D_Vector cq = getQRV().getCrossProduct(qpt, oom);
        V3D_Vector cr = getRPV().getCrossProduct(rpt, oom);
        /**
         * If cp, cq and cr are all in the same direction then pt intersects.
         */
//        Math_BigRationalSqrt mp = cp.getMagnitude();
//        Math_BigRationalSqrt mq = cq.getMagnitude();
//        Math_BigRationalSqrt mr = cr.getMagnitude();
        if (cp.dx.isNegative() == cq.dx.isNegative() && cp.dx.isNegative() == cr.dx.isNegative()) {
            if (cp.dy.isNegative() == cq.dy.isNegative() && cp.dy.isNegative() == cr.dy.isNegative()) {
                if (cp.dz.isNegative() == cq.dz.isNegative() && cp.dz.isNegative() == cr.dz.isNegative()) {
                    return true;
                }
            }
        }
//        
//        V3D_Vector cpq = cp.add(cq);
//        Math_BigRational mpq = cpq.getMagnitudeSquared();
//        if (mpq.compareTo(mp) == mpq.compareTo(mq) == mpr.compareTo(mr)) {
//            
//            Math_BigRational mr = cr.getMagnitudeSquared();
//            Math_BigRational mpqr = cpq.add(cr).getMagnitudeSquared();
//            //if (mpqr.compareTo(mr) == 1 && mpqr.compareTo(mpq) == 1) {
//            if (mpqr == mr) {
//                return true;
//            }
//        }
        return false;
    }

    /**
     * @param l The line to test for intersection with this.
     * @param oom The Order of Magnitude for the calculation.
     * @param b To distinguish this from
     * {@link V3D_Plane#isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Line, int)}.
     * @return {@code true} If this and {@code l} intersect.
     */
    public boolean isIntersectedBy(V3D_Line l, int oom, boolean b) {
        if (super.isIntersectedBy(l, oom)) {
            V3D_Geometry g = super.getIntersection(l, oom);
            if (g instanceof V3D_Point pt) {
                return isIntersectedBy(pt, oom, b);
            } else {
                if (getPQ().isIntersectedBy(l, oom)) {
                    return true;
                }
                if (getQR().isIntersectedBy(l, oom)) {
                    return true;
                }
                if (getRP().isIntersectedBy(l, oom)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, boolean b) {
        if (this.getEnvelope(oom).isIntersectedBy(l.getEnvelope(oom))) {
            if (isIntersectedBy(l, oom)) {
                V3D_Geometry g = super.getIntersection(l, oom, b);
                if (g == null) {
                    return false;
                } else {
                    if (g instanceof V3D_Point pt) {
                        return l.isIntersectedBy(pt, oom);
                    } else {
                        return l.isIntersectedBy((V3D_LineSegment) g, oom, b);
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The area of the triangle (rounded).
     */
    @Override
    public BigDecimal getArea(int oom) {
        return getPQV().getCrossProduct(getRPV().reverse(), oom).getMagnitude()
                .divide(Math_BigRational.TWO, oom).toBigDecimal(oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     */
    @Override
    public BigDecimal getPerimeter(int oom) {
        int oomN1 = oom - 1;
        return Math_BigDecimal.round(getPQ().getLength(oom).toBigDecimal(oomN1)
                .add(getQR().getLength(oom).toBigDecimal(oomN1))
                .add(getRP().getLength(oom).toBigDecimal(oomN1)), oom);
    }

    /**
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return A point or line segment.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        V3D_Geometry g = (new V3D_Plane(this)).getIntersection(l, oom);
        if (g == null) {
            return null;
        }
        if (!getEnvelope(oom).isIntersectedBy(l, oom)) {
            return null;
        }
//        V3D_Geometry enil = getEnvelope(oom).getIntersection(l, oom);
//        if (enil == null) {
//            return null;
//        }
        /**
         * Get the intersection of the line and each edge of the triangle.
         */
        V3D_Geometry lpqi = getPQ().getIntersection(l, oom);
        V3D_Geometry lrpi = getRP().getIntersection(l, oom);
        V3D_Geometry lqri = getQR().getIntersection(l, oom);
        if (lpqi == null) {
            if (lqri == null) {
                if (lrpi == null) {
                    return null;
                } else {
                    return lrpi;
                }
            } else if (lqri instanceof V3D_LineSegment) {
                return lqri;
            } else {
                if (lrpi == null) {
                    return lqri;
                }
                return getGeometry(((V3D_Point) lqri).getVector(oom),
                        ((V3D_Point) lrpi).getVector(oom));
            }
        } else if (lpqi instanceof V3D_LineSegment) {
            return lpqi;
        } else {
            if (lqri == null) {
                if (lrpi == null) {
                    return lpqi;
                } else {
                    return getGeometry(((V3D_Point) lpqi).getVector(oom),
                            ((V3D_Point) lrpi).getVector(oom));
                }
            } else if (lqri instanceof V3D_LineSegment) {
                return lqri;
            } else {
                if (lrpi == null) {
                    return getGeometry(((V3D_Point) lqri).getVector(oom),
                            ((V3D_Point) lpqi).getVector(oom));
                } else if (lrpi instanceof V3D_LineSegment) {
                    return lrpi;
                } else {
                    return V3D_FiniteGeometry.getGeometry((V3D_Point) lpqi,
                            (V3D_Point) lrpi, (V3D_Point) lrpi, oom);
//                    return V3D_FiniteGeometry.getGeometry((V3D_Point) lpqi,
//                            (V3D_Point) lrpi, (V3D_Point) lqri, oom);
//                    return V3D_FiniteGeometry.getGeometry((V3D_Point) lpqi,
//                            (V3D_Point) lrpi, (V3D_Point) lqri, oom);
                }
            }
        }
    }

    /**
     * If {@code v1} and {@code v2} are the same, then return a point with
     * offset set to {@link V3D_Vector#ZERO}, otherwise return a line segment.
     *
     * @param v1 A vector.
     * @param v2 A vector.
     */
    public V3D_Geometry getGeometry(V3D_Vector v1, V3D_Vector v2) {
        if (v1.equals(v2)) {
            return new V3D_Point(v1, oom);
        } else {
            return new V3D_LineSegment(v1, v2, oom);
        }
    }

    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom, boolean b) {
        V3D_Point lp = l.getP(oom);
        V3D_Point lq = l.getQ(oom);
//        boolean lip = isIntersectedBy(l.p, oom);
//        boolean liq = isIntersectedBy(l.q, oom);
        boolean lip = isIntersectedBy(lp, oom, b);
        boolean liq = isIntersectedBy(lq, oom, b);
        if (lip) {
            if (liq) {
                return l;
            } else {
                V3D_Geometry li = getIntersection(l, oom);
                if (li instanceof V3D_Point) {
//                    return l.p;
                    return lp;
                } else {
                    V3D_LineSegment lli = (V3D_LineSegment) li;
                    V3D_Vector llip = lli.getP();
                    V3D_Vector lpp = l.getP();
                    if (llip.equals(lpp)) {
                        return new V3D_LineSegment(l.offset, lpp, lli.getQ(), oom);
                    } else {
                        return new V3D_LineSegment(l.offset, lpp, llip, oom);
                    }
                }
            }
        } else {
            V3D_Geometry li = getIntersection(l, oom);
            if (li == null) {
                return null;
            }
            if (liq) {
                if (li instanceof V3D_Point) {
                    //return l.q;
                    return lq;
                } else {
                    V3D_LineSegment lli = (V3D_LineSegment) li;
                    V3D_Vector lliq = lli.getQ();
                    V3D_Vector lqq = l.getQ();
                    if (lliq.equals(lqq)) {
                        return new V3D_LineSegment(l.offset, lqq, lliq, oom);
                    } else {
                        return new V3D_LineSegment(l.offset, lqq, lli.getP(), oom);
                    }
                }
            } else {
                if (li instanceof V3D_Point pli) {
                    if (l.isIntersectedBy(pli, oom)) {
                        return pli;
                    } else {
                        return null;
                    }
                } else {
                    return ((V3D_LineSegment) li).getIntersection(l, oom, b);
                }
            }
        }
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
        return getEnvelope(oom).isIntersectedBy(l, oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The centroid point.
     */
    public V3D_Point getCentroid(int oom) {
//        V3D_Point mpq = getPQ().getMidpoint(oom);
//        V3D_Point mqr = getQR().getMidpoint(oom);
//        V3D_Point mrp = getRP().getMidpoint(oom);
////        V3D_LineSegment lmpqr = new V3D_LineSegment(mpq, getRP().p, oom);
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
        return new V3D_Point(getPV().add(getQV(), oom).add(getRV(), oom)
                .divide(Math_BigRational.valueOf(3), oom), oom);
    }

    /**
     * Test if two triangles are equal. Two triangles are equal if they have 3
     * coincident points, so even if the order is different and one is clockwise
     * and the other anticlockwise.
     *
     * @param t The other triangle to test for equality.
     * @return {@code true} iff {@code this} is equal to {@code t}.
     */
    public boolean equals(V3D_Triangle t) {
        if (t.p.equals(p)) {
            if (t.q.equals(q)) {
                return t.r.equals(r);
            } else if (t.q.equals(r)) {
                return t.r.equals(q);
            } else {
                return false;
            }
        } else if (t.p.equals(q)) {
            if (t.q.equals(r)) {
                return t.r.equals(p);
            } else if (t.q.equals(p)) {
                return t.r.equals(r);
            } else {
                return false;
            }
        } else if (t.p.equals(r)) {
            if (t.q.equals(p)) {
                return t.r.equals(q);
            } else if (t.q.equals(p)) {
                return t.r.equals(q);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

//    /**
//     * Change {@link #offset} without changing the overall line.
//     *
//     * @param offset What {@link #offset} is set to.
//     */
//    @Override
//    public void setOffset(V3D_Vector offset) {
//        super.setOffset(offset);
//        lpq = null;
//    }
    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta) {
        super.rotate(axisOfRotation, theta);
        en = null;
    }
}
