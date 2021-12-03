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
 * area. The corner points are {@link #p}, {@link #q}
 * and {@link #r}. The following depicts a generic
 * triangle {@code
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
//    private final V3D_LineSegment lpq;
//
//    /**
//     * The line from {@link #q} to {@link #r}.
//     */
//    private final V3D_LineSegment lqr;
//
//    /**
//     * The line from {@link #r} to {@link #p}.
//     */
//    private final V3D_LineSegment lrp;
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
     * Creates a new triangle.
     * {@link #offset} is set to {@link V3D_Vector#ZERO}.
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
     * @param r Defines the other point relative l.offset that defines the triangle.
     * @param oom The Order of Magnitude for the initialisation.
     */
    public V3D_Triangle(V3D_LineSegment l, V3D_Vector r, int oom) {
        super(l.offset, l.p, l.q, r, oom);
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
    public String toString(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFields(pad + " ") + "\n"
                + pad + ")";
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
     * @return The {@link #p}-{@link #q} triangle edge. 
     */
    public V3D_LineSegment getPq() {
        return new V3D_LineSegment(offset, p, q, oom);
    }
    
    /**
     * @return The {@link #q}-{@link #r} triangle edge. 
     */
    public V3D_LineSegment getQr() {
        return new V3D_LineSegment(offset, q, r, oom);
    }
    
    /**
     * @return The {@link #r}-{@link #p} triangle edge. 
     */
    public V3D_LineSegment getRp() {
        return new V3D_LineSegment(offset, r, p, oom);
    }
    
    /**
     * @param pt The point to intersect with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return A point or line segment.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom) {
        if (getEnvelope(oom).isIntersectedBy(pt, oom)) {
            if (super.isIntersectedBy(pt, oom)) {
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
        if (getPq().isIntersectedBy(pt, oom) || getQr().isIntersectedBy(pt, oom)
                || getRp().isIntersectedBy(pt, oom)) {
            return true;
        }
        V3D_Vector ppt = new V3D_Vector(getP(), pt, oom);
        V3D_Vector qpt = new V3D_Vector(getQ(), pt, oom);
        V3D_Vector rpt = new V3D_Vector(getR(), pt, oom);
        V3D_Vector cp = getPq(oom).getCrossProduct(ppt, oom);
        V3D_Vector cq = getQr(oom).getCrossProduct(qpt, oom);
        V3D_Vector cr = getRp(oom).getCrossProduct(rpt, oom);
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

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        if (super.isIntersectedBy(l, oom)) {
            V3D_Geometry g = super.getIntersection(l, oom);
            if (g instanceof V3D_Point) {
                V3D_Point p = (V3D_Point) g;
                return isIntersectedBy(p, oom);
            } else {
                if (getPq().isIntersectedBy(l, oom)) {
                    return true;
                }
                if (getQr().isIntersectedBy(l, oom)) {
                    return true;
                }
                if (getRp().isIntersectedBy(l, oom)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, boolean b) {
        if (this.getEnvelope(oom).isIntersectedBy(l.getEnvelope(oom))) {
            if (super.isIntersectedBy(l, oom)) {
                V3D_Geometry g = super.getIntersection(l, oom, b);
                if (g instanceof V3D_Point) {
                    return l.isIntersectedBy((V3D_Point) g, oom);
                } else {
                    return l.isIntersectedBy((V3D_LineSegment) g, oom, b);
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
        return getPq(oom).getCrossProduct(getRp(oom).reverse(), oom).getMagnitude()
                .divide(Math_BigRational.TWO, oom).toBigDecimal(oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     */
    @Override
    public BigDecimal getPerimeter(int oom) {
        int oomN1 = oom - 1;
        return Math_BigDecimal.round(getPq().getLength(oom).toBigDecimal(oomN1)
                .add(getQr().getLength(oom).toBigDecimal(oomN1))
                .add(getRp().getLength(oom).toBigDecimal(oomN1)), oom);
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
        V3D_Geometry enil = getEnvelope(oom).getIntersection(l, oom);
        if (enil == null) {
            return null;
        }
        /**
         * Get the intersection of the line and each edge of the triangle.
         */
        V3D_Geometry lpqi = getPq().getIntersection(l, oom);
        V3D_Geometry lrpi = getRp().getIntersection(l, oom);
        V3D_Geometry lqri = getQr().getIntersection(l, oom);
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
                return new V3D_LineSegment(
                        ((V3D_Point) lqri).getVector(oom),
                        ((V3D_Point) lrpi).getVector(oom), oom);
            }
        } else if (lpqi instanceof V3D_LineSegment) {
            return lpqi;
        } else {
            if (lqri == null) {
                return new V3D_LineSegment(
                        ((V3D_Point) lpqi).getVector(oom),
                        ((V3D_Point) lrpi).getVector(oom), oom);
            } else if (lqri instanceof V3D_LineSegment) {
                return lqri;
            } else {
                if (lrpi == null) {
                    return new V3D_LineSegment(
                            ((V3D_Point) lqri).getVector(oom),
                            ((V3D_Point) lpqi).getVector(oom), oom);
                } else if (lrpi instanceof V3D_LineSegment) {
                    return lrpi;
                } else {
                    return V3D_FiniteGeometry.getGeometry((V3D_Point) lpqi,
                            (V3D_Point) lrpi, (V3D_Point) lrpi, oom);
                }
            }
        }
    }

    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom, boolean b) {
        V3D_Point lp = l.getP(oom);
        V3D_Point lq = l.getQ(oom);
//        boolean lip = isIntersectedBy(l.p, oom);
//        boolean liq = isIntersectedBy(l.q, oom);
        boolean lip = isIntersectedBy(lp, oom);
        boolean liq = isIntersectedBy(lq, oom);
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
                    if (lli.p.equals(l.p)) {
                        return new V3D_LineSegment(l.offset, l.p, lli.q, oom);
                    } else {
                        return new V3D_LineSegment(l.offset, l.p, lli.p, oom);
                    }
                }
            }
        } else {
            V3D_Geometry li = getIntersection(l, oom);
            if (liq) {
                if (li instanceof V3D_Point) {
                    //return l.q;
                    return lq;
                } else {
                    V3D_LineSegment lli = (V3D_LineSegment) li;
                    if (lli.q.equals(l.q)) {
                        return new V3D_LineSegment(l.offset, l.q, lli.q, oom);
                    } else {
                        return new V3D_LineSegment(l.offset, l.q, lli.p, oom);
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
        V3D_Point mpq = getPq().getMidpoint(oom);
        V3D_Point mqr = getQr().getMidpoint(oom);
        V3D_Point mrp = getRp().getMidpoint(oom);
//        V3D_LineSegment lmpqr = new V3D_LineSegment(mpq, getRp().p, oom);
//        V3D_LineSegment lmqrp = new V3D_LineSegment(mqr, getP(oom), oom);
        V3D_LineSegment lmpqr = new V3D_LineSegment(offset, mpq.rel, getRp().p, oom);
        V3D_LineSegment lmqrp = new V3D_LineSegment(offset, mqr.rel, p, oom);
        //V3D_LineSegment lmrpq = new V3D_LineSegment(mrp, getQ(oom), oom);
        V3D_Point c0 = (V3D_Point) lmpqr.getIntersection(lmqrp, oom, true);
        //V3D_Point c1 = (V3D_Point) lmpqr.getIntersection(lmrpq, oom, true);
        //V3D_Point c2 = (V3D_Point) lmrpq.getIntersection(lmqrp, oom, true);
        //System.out.println(toString());
        //System.out.println("c0=" + c0.toString());
        //System.out.println("c1=" + c1.toString());
        //System.out.println("c2=" + c2.toString());
        return c0;
    }
}
