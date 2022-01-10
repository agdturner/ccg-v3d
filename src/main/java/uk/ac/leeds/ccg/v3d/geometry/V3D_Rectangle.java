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
 * For representing and processing rectangles in 3D. A rectangle has four corner
 * points {@link #p}, {@link #q}, {@link #r}, {@link #s}. The
 * following depicts a generic rectangle {@code
 *          qr
 *  q *-------------* r
 *    |             |
 * pq |             | rs
 *    |             |
 *  p *-------------* s
 *          sp
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Rectangle extends V3D_Plane implements V3D_Face {

    private static final long serialVersionUID = 1L;

//    /**
//     * For storing if q is at the origin.
//     */
//    protected final boolean qAtOrigin2;
    /**
     * The other corner of the rectangle. The others are {@link #p}, {@link #q},
     * and {@link #r}.
     */
    protected final V3D_Vector s;
    
//    /**
//     * {@link #s} with rotations applied.
//     */
//    protected V3D_Vector sTemp;

//    /**
//     * Initialised when the q provided to initialise this is at the origin and p
//     * and q have been swapped to define the plane. So, this represents the
//     * vector from the point p input (which is now stored as {@link #q}) to
//     * {@link #s}.
//     */
//    protected final V3D_Vector qs;
    /**
     * For storing the envelope
     */
    protected V3D_Envelope en;

//    /**
//     * For storing the vector from {@link #p} to {@link #q}.
//     */
//    protected final V3D_LineSegment l;
//
//    /**
//     * For storing the line segment from {@link #q} to {@link #r}.
//     */
//    protected final V3D_LineSegment t;
//
//    /**
//     * For storing the line segment from {@link #r} to {@link #s}.
//     */
//    protected final V3D_LineSegment ri;
//
//    /**
//     * For storing the line segment from {@link #s} to {@link #p}.
//     */
//    protected final V3D_LineSegment b;
    /**
     * Create a new instance.
     *
     * @param p The bottom left corner of the rectangle.
     * @param q The top left corner of the rectangle.
     * @param r The top right corner of the rectangle.
     * @param s The bottom right corner of the rectangle.
     * @param oom The Order of Magnitude for the initialisation.
     * @throws java.lang.RuntimeException iff the points do not define a
     * rectangle.
     */
    public V3D_Rectangle(V3D_Vector p, V3D_Vector q, V3D_Vector r, V3D_Vector s,
            int oom) {
        super(p, q, r, oom);
        /**
         * p and q get swapped if q is at the origin in defining the plane, in
         * which case {@link #pq} is now representing the reverse, and
         * {@link qr} represents the vector from p to r.
         */
        boolean qAtOrigin2 = q.equals(V3D_Vector.ZERO);
        V3D_Vector qs;
        if (qAtOrigin2) {
            qs = s.subtract(p, oom);
        } else {
            qs = null;
        }
        this.s = s;
        //en = new V3D_Envelope(p, q, r, s); Not initialised here as it causes a StackOverflowError
//        l = new V3D_LineSegment(p, q, oom);
//        t = new V3D_LineSegment(q, r, oom);
//        ri = new V3D_LineSegment(r, s, oom);
//        b = new V3D_LineSegment(s, p, oom);
        // Check for rectangle.
        V3D_Vector tpq = getPQV();
        V3D_Vector tqr = getQRV();
        if (tpq.isZeroVector()) {
            if (tqr.isZeroVector()) {
                // Rectangle is a point.
            } else {
                // Rectangle is a line.
            }
        } else {
            if (tqr.isZeroVector()) {
                // Rectangle is a line.
            } else {
                // Rectangle has area.
                if (qAtOrigin2) {
                    if (!(tpq.isOrthogonal(qs, oom))) {
                        throw new RuntimeException("The points do not define a rectangle.");
                    }
                } else {
                    if (!(tpq.isOrthogonal(tqr, oom))) {
                        throw new RuntimeException("The points do not define a rectangle.");
                    }
                }
            }
        }
    }

    /**
     * Create a new instance.
     *
     * @param r What {@code this} is created from.
     * @param oom The Order of Magnitude for the initialisation.
     * @throws java.lang.RuntimeException iff the points do not define a
     * rectangle.
     */
    public V3D_Rectangle(V3D_Envelope.Rectangle r, int oom) {
        this(new V3D_Vector(r.p), new V3D_Vector(r.q), new V3D_Vector(r.r),
                new V3D_Vector(r.s), oom);
    }

    @Override
    public String toString(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFields(pad + " ") + "\n"
                + pad + ")";
    }

    @Override
    protected String toStringFields(String pad) {
        return super.toStringFields(pad) + "\n"
                + pad + ",\n"
                + pad + "s=" + s.toString(pad);
    }

//    /**
//     * @param oom The Order of Magnitude for the calculation.
//     * @return The vector from {@link #q} to {@link #s}.
//     */
//    public V3D_Vector getQS(int oom) {
//        return rotate(s.subtract(q, oom), bI, theta);
//    }

//    /**
//     * @return {@link #sTemp} rotated.
//     */
//    public V3D_Vector getSV() {
//        //return new V3D_Point(offset, rotate(s, theta));
//        if (sTemp == null) {
//            sTemp = s;
//        }
//        sTemp = rotate(sTemp, bI, theta);
//        return sTemp;
//    }
    
    /**
     * @return {@link #sTemp} rotated.
     */
    public V3D_Vector getSV() {
        return s;
    }
    
    /**
     * @return {@link #sTemp} rotated and with {@link #offset} applied.
     */
    public V3D_Point getS() {
        return new V3D_Point(offset, getSV());
    }

    @Override
    public V3D_Envelope getEnvelope(int oom) {
        if (en == null) {
            en = new V3D_Envelope(oom, getP(), getQ(), getR(), getS());
        }
        return en;
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
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The line segment from {@link #r} to {@link #s}.
     */
    protected V3D_LineSegment getRS() {
        //return new V3D_LineSegment(offset, rotate(r, theta), rotate(s, theta), oom);
        return new V3D_LineSegment(offset, getRV(), getSV(), oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The line segment from {@link #s} to {@link #p}.
     */
    protected V3D_LineSegment getSP() {
        //return new V3D_LineSegment(offset, rotate(s, theta), rotate(p, theta), oom);
        return new V3D_LineSegment(offset, getSV(), getPV(), oom);
    }

    private boolean isIntersectedBy0(V3D_Line ls, int oom) {
        return getQR().isIntersectedBy(ls, oom) || getRS().isIntersectedBy(ls, oom)
                || getSP().isIntersectedBy(ls, oom) || getPQ().isIntersectedBy(ls, oom);
    }

    private boolean isIntersectedBy0(V3D_Point pt, int oom) {
        // Special cases
        V3D_Point tp = this.getP();
        if (tp.equals(pt)) {
            return true;
        }
        V3D_Point tq = this.getQ();
        if (tq.equals(pt)) {
            return true;
        }
        V3D_Point tr = this.getR();
        if (tr.equals(pt)) {
            return true;
        }
        V3D_Point ts = this.getS();
        if (ts.equals(pt)) {
            return true;
        }
        if (true) {
            //if (V3D_Geometrics.isCoplanar(this, pt)) {
            // Check the areas
            // Area pqpt
            BigDecimal apqpt = new V3D_Triangle(tp.getVector(oom), 
                    tq.getVector(oom), pt.getVector(oom), oom).getArea(oom);
            // Area qrpt
            BigDecimal aqrpt = new V3D_Triangle(tq.getVector(oom), 
                    tr.getVector(oom), pt.getVector(oom), oom).getArea(oom);
            // Area rspt
            BigDecimal arspt = new V3D_Triangle(tr.getVector(oom), 
                    ts.getVector(oom), pt.getVector(oom), oom).getArea(oom);
            // Area sppt
            BigDecimal asppt = new V3D_Triangle(ts.getVector(oom), 
                    tp.getVector(oom), pt.getVector(oom), oom).getArea(oom);
            if (this.getArea(oom).compareTo(apqpt.add(aqrpt).add(arspt).add(asppt)) == 0) {
                return true;
                //return V3D_Geometrics.isCoplanar(this, pt);
            }
        }
        if (getQR().isIntersectedBy(pt, oom) || getRS().isIntersectedBy(pt, oom)
                || getSP().isIntersectedBy(pt, oom) || getPQ().isIntersectedBy(pt, oom)) {
            return true;
        }
        if (getQ().equals(V3D_Point.ORIGIN)) {
            V3D_Vector ppt = new V3D_Vector(tq, pt, oom);
            V3D_Vector qpt = new V3D_Vector(tp, pt, oom);
            V3D_Vector rpt = new V3D_Vector(tr, pt, oom);
            V3D_Vector spt = new V3D_Vector(ts, pt, oom);
            V3D_Vector rs = new V3D_Vector(tr, ts, oom);
            V3D_Vector sp = new V3D_Vector(ts, tq, oom);
            V3D_Vector cp = getPQV().reverse().getCrossProduct(ppt, oom);
            V3D_Vector cq = getQRV().getCrossProduct(qpt, oom);
            V3D_Vector cr = rs.getCrossProduct(rpt, oom);
            V3D_Vector cs = sp.getCrossProduct(spt, oom);
            /**
             * If cp, cq, cr, and cs are all in the same direction then pt
             * intersects.
             */
            Math_BigRational mp = cp.getMagnitudeSquared();
            Math_BigRational mq = cq.getMagnitudeSquared();
            V3D_Vector cpq = cp.add(cq, oom);
            Math_BigRational mpq = cpq.getMagnitudeSquared();
            if (mpq.compareTo(mp) == 1 && mpq.compareTo(mq) == 1) {
                Math_BigRational mr = cr.getMagnitudeSquared();
                V3D_Vector cpqr = cpq.add(cr, oom);
                Math_BigRational mpqr = cpqr.getMagnitudeSquared();
                if (mpqr.compareTo(mr) == 1 && mpqr.compareTo(mpq) == 1) {
                    Math_BigRational ms = cs.getMagnitudeSquared();
                    Math_BigRational mpqrs = cpqr.add(cs, oom).getMagnitudeSquared();
                    if (mpqrs.compareTo(ms) == 1 && mpqrs.compareTo(mpqr) == 1) {
                        return true;
                    }
                }
            }
        } else {
            V3D_Vector ppt = new V3D_Vector(tp, pt, oom);
            V3D_Vector qpt = new V3D_Vector(tq, pt, oom);
            V3D_Vector rpt = new V3D_Vector(tr, pt, oom);
            V3D_Vector spt = new V3D_Vector(ts, pt, oom);
            V3D_Vector rs = new V3D_Vector(tr, ts, oom);
            V3D_Vector sp = new V3D_Vector(ts, tp, oom);
            V3D_Vector cp = getPQV().getCrossProduct(ppt, oom);
            V3D_Vector cq = getQRV().getCrossProduct(qpt, oom);
            V3D_Vector cr = rs.getCrossProduct(rpt, oom);
            V3D_Vector cs = sp.getCrossProduct(spt, oom);
            /**
             * If cp, cq, cr, and cs are all in the same direction then pt
             * intersects.
             */
            Math_BigRational mp = cp.getMagnitudeSquared();
            Math_BigRational mq = cq.getMagnitudeSquared();
            V3D_Vector cpq = cp.add(cq, oom);
            Math_BigRational mpq = cpq.getMagnitudeSquared();
            if (mpq.compareTo(mp) == 1 && mpq.compareTo(mq) == 1) {
                Math_BigRational mr = cr.getMagnitudeSquared();
                V3D_Vector cpqr = cpq.add(cr, oom);
                Math_BigRational mpqr = cpqr.getMagnitudeSquared();
                if (mpqr.compareTo(mr) == 1 && mpqr.compareTo(mpq) == 1) {
                    Math_BigRational ms = cs.getMagnitudeSquared();
                    Math_BigRational mpqrs = cpqr.add(cs, oom).getMagnitudeSquared();
                    if (mpqrs.compareTo(ms) == 1 && mpqrs.compareTo(mpqr) == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        V3D_Plane pl = new V3D_Plane(this);
        if (pl.isIntersectedBy(l, oom)) {
            V3D_Geometry g = pl.getIntersection(l, oom);
            if (g instanceof V3D_Point v3D_Point) {
                return isIntersectedBy0(v3D_Point, oom);
            } else {
                return isIntersectedBy0((V3D_Line) g, oom);
            }
        }
        return false;
    }

    /**
     * Intersection test.
     *
     * @param l The line segment to test for intersection.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @param b This is a flag to distinguish this method from
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Line, int)}.
     * @return {@code true} iff this rectangle is intersected by {@code l}
     */
    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, boolean b) {
        if (getEnvelope(oom).isIntersectedBy(l.getEnvelope(oom))) {
            V3D_Plane pl = new V3D_Plane(this);
            if (pl.isIntersectedBy(l, oom)) {
                V3D_Geometry g = pl.getIntersection(l, oom, b);
                if (g instanceof V3D_Point v3D_Point) {
                    return isIntersectedBy(v3D_Point, oom);
                } else {
                    return isIntersectedBy((V3D_LineSegment) g, oom, b);
                }
            }
        }
        return false;
    }

    /**
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return A point or line segment.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        V3D_Geometry g = new V3D_Plane(this).getIntersection(l, oom);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_Point v3D_Point) {
                if (this.isIntersectedBy(v3D_Point, oom)) {
                    return g;
                } else {
                    return null;
                }
            } else {
                V3D_Line li = (V3D_Line) g;
                /**
                 * Whilst in theory a test of the envelope might seem a good
                 * idea (for speeding things up), timing experiments are wanted
                 * to be sure it does. Optimisation plans should bear in mind
                 * that some parts of intersection algorithms that can be done
                 * in parallel, so it might not be obvious what the quickest
                 * ways are! One of the reasons that V3D_Envelope has it's own
                 * geometry is because they needed their own special rectangles
                 * otherwise this "optimisation" resulted in a StackOverflow
                 * error.
                 */
                // Quick test of the envelope?!
                if (!getEnvelope(oom).isIntersectedBy(l, oom)) {
                    return null;
                }
                /**
                 * Get the intersection of the line and each edge of the
                 * rectangle.
                 */
                V3D_LineSegment tqr = getQR();
                V3D_LineSegment trs = getRS();
                V3D_LineSegment tsp = getSP();
                V3D_LineSegment tpq = getPQ();

                V3D_Geometry ti = tqr.getIntersection(li, oom);
                if (ti == null) {
                    // Check ri, b, l
                    V3D_Geometry rii = trs.getIntersection(li, oom);
                    if (rii == null) {
                        // Check b, l
                        V3D_Geometry bi = tsp.getIntersection(li, oom);
                        if (bi == null) {
                            // Check l
                            V3D_Geometry tli = tpq.getIntersection(li, oom);
                            if (tli == null) {
                                return null;
                            } else {
                                return tli;
                            }
                        } else if (bi instanceof V3D_LineSegment) {
                            return bi;
                        } else {
                            // Check l
                            V3D_Geometry tli = tpq.getIntersection(li, oom);
                            if (tli == null) {
                                return bi;
                            } else {
                                return new V3D_LineSegment(
                                        ((V3D_Point) bi).getVector(oom),
                                        ((V3D_Point) tli).getVector(oom), oom);
                            }
                        }
                    } else if (rii instanceof V3D_LineSegment) {
                        return rii;
                    } else {
                        // Check b, l
                        V3D_Geometry bi = tsp.getIntersection(li, oom);
                        if (bi == null) {
                            // Check l
                            V3D_Geometry tli = tpq.getIntersection(li, oom);
                            if (tli == null) {
                                return rii;
                            } else {
                                return new V3D_LineSegment(
                                        ((V3D_Point) rii).getVector(oom),
                                        ((V3D_Point) tli).getVector(oom), oom);
                            }
                        } else if (bi instanceof V3D_LineSegment) {
                            return bi;
                        } else {
                            // Check l
                            V3D_Geometry tli = tpq.getIntersection(li, oom);
                            if (tli == null) {
                                V3D_Point riip = (V3D_Point) rii;
                                V3D_Point bip = (V3D_Point) bi;
                                if (riip.equals(bip)) {
                                    return bip;
                                } else {
                                    return new V3D_LineSegment(
                                            riip.getVector(oom),
                                            bip.getVector(oom), oom);
                                }
                            } else {
                                return new V3D_LineSegment(
                                        ((V3D_Point) bi).getVector(oom),
                                        ((V3D_Point) tli).getVector(oom), oom);
                            }
                        }
                    }
                } else if (ti instanceof V3D_LineSegment) {
                    return ti;
                } else {
                    // Check ri, b, l
                    V3D_Geometry rii = trs.getIntersection(li, oom);
                    if (rii == null) {
                        // Check b, l
                        V3D_Geometry bi = tsp.getIntersection(li, oom);
                        if (bi == null) {
                            // Check l
                            V3D_Geometry tli = tpq.getIntersection(li, oom);
                            if (tli == null) {
                                return ti;
                            } else {
                                V3D_Point tlip = (V3D_Point) tli;
                                V3D_Point tip = (V3D_Point) ti;
                                if (tlip.equals(tip)) {
                                    return tlip;
                                } else {
                                    return new V3D_LineSegment(
                                            tlip.getVector(oom), 
                                            tip.getVector(oom), 
                                            oom);
                                }
                            }
                        } else if (bi instanceof V3D_LineSegment) {
                            return bi;
                        } else {
                            return new V3D_LineSegment(
                                    ((V3D_Point) ti).getVector(oom),
                                    ((V3D_Point) bi).getVector(oom), oom);
                        }
                    } else {
                        V3D_Point tip = (V3D_Point) ti;
                        V3D_Point riip = (V3D_Point) rii;
                        if (tip.equals(riip)) {
                            // Check b, l
                            V3D_Geometry sri = tsp.getIntersection(li, oom);
                            if (sri == null) {
                                // Check l
                                V3D_Geometry tli = tpq.getIntersection(li, oom);
                                if (tli == null) {
                                    return rii;
                                } else {
                                    return new V3D_LineSegment(
                                            riip.getVector(oom),
                                            ((V3D_Point) tli).getVector(oom), oom);
                                }
                            } else if (sri instanceof V3D_LineSegment) {
                                return sri;
                            } else {
                                return new V3D_LineSegment(
                                        riip.getVector(oom), 
                                        ((V3D_Point) sri).getVector(oom), oom);
                            }
                        } else {
                            return new V3D_LineSegment(
                                    riip.getVector(oom), tip.getVector(oom), oom);
                        }
                    }
                }
            }
        }
    }

    /**
     * Calculates and returns the intersections between {@code this} and
     * {@code l}.
     *
     * @param l The line segment to test for intersection.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @param flag This is to distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Line, int)}. The
     * value is ignored.
     * @return The intersection or {@code null} iff there is no intersection.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom, boolean flag) {
        V3D_Point lp = l.getP(oom);
        V3D_Point lq = l.getQ(oom);
        boolean lip = isIntersectedBy(lp, oom);
        boolean liq = isIntersectedBy(lq, oom);
        if (lip) {
            if (liq) {
                return l;
            } else {
                V3D_Geometry li = getIntersection(l, oom);
                if (li instanceof V3D_Point) {
                    return lp;
                } else {
                    V3D_LineSegment lli = (V3D_LineSegment) li;
                    V3D_Vector llip = lli.getP();
                    V3D_Vector lpp = l.getP();
                    if (llip.equals(lpp)) {
                        return new V3D_LineSegment(lpp, lli.getQ(), oom);
                    } else {
                        return new V3D_LineSegment(lpp, llip, oom);
                    }
                }
            }
        } else {
            V3D_Geometry li = getIntersection(l, oom);
            if (liq) {
                if (li instanceof V3D_Point) {
                    return lq;
                } else {
                    V3D_LineSegment lli = (V3D_LineSegment) li;
                    V3D_Vector lliq = lli.getQ();
                    V3D_Vector lqq = l.getQ();
                    if (lliq.equals(lqq)) {
                        return new V3D_LineSegment(lqq, lli.getP(), oom);
                    } else {
                        return new V3D_LineSegment(lqq, lliq, oom);
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
                    return li;
                }
            }
        }
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
        return getEnvelope(oom).isIntersectedBy(l, oom);
    }

    @Override
    public BigDecimal getPerimeter(int oom) {
        int oomn2 = oom - 2;
        return getPQ().getLength(oom).toBigDecimal(oomn2)
                .add(getQR().getLength(oom).toBigDecimal(oomn2))
                .multiply(BigDecimal.valueOf(2));
    }

    @Override
    public BigDecimal getArea(int oom) {
//        return Math_BigDecimal.roundDown(l.v.getMagnitude(oomn2)
//                .multiply(t.v.getMagnitude(oomn2)), oom);
        return getPQ().getV(oom).getMagnitude().multiply(getQR().getV(oom).getMagnitude(), oom).toBigDecimal(oom);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The distance from {@code this} to {@code p}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        if (this.isIntersectedBy(p, oom)) {
            return BigDecimal.ZERO;
        }
        BigDecimal dp = super.getDistance(p, oom);
        BigDecimal ld = getPQ().getDistance(p, oom);
        BigDecimal td = getQR().getDistance(p, oom);
        BigDecimal rd = getRS().getDistance(p, oom);
        BigDecimal bd = getSP().getDistance(p, oom);
        if (dp.compareTo(ld) == 0 && dp.compareTo(td) == 0
                && dp.compareTo(rd) == 0 && dp.compareTo(bd) == 0) {
            return dp;
        } else {
            return Math_BigDecimal.min(ld, td, rd, bd);
        }
    }
}
