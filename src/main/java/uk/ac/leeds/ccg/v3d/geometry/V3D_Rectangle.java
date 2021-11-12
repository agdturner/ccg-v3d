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
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * For representing and processing rectangles in 3D. A rectangle has four corner
 * points {@link #p}, {@link #q}, {@link #r}, {@link #s}. The left of a
 * rectangle {@link #l} is the line segment from {@link #p} to {@link #q}. The
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
public class V3D_Rectangle extends V3D_Plane implements V3D_2DShape {

    private static final long serialVersionUID = 1L;

//    /**
//     * For storing if q is at the origin.
//     */
//    protected final boolean qAtOrigin2;
    /**
     * The other corner of the rectangle. The others are {@link #p}, {@link #q},
     * and {@link #r}.
     */
    protected final V3D_Point s;

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
    public V3D_Rectangle(V3D_Point p, V3D_Point q, V3D_Point r, V3D_Point s,
            int oom) {
        super(p, q, r, oom);
        /**
         * p and q get swapped if q is at the origin in defining the plane, in
         * which case {@link #pq} is now representing the reverse, and
         * {@link qr} represents the vector from p to r.
         */
        boolean qAtOrigin2 = q.equals(V3D_Environment.P0P0P0);
        V3D_Vector qs;
        if (qAtOrigin2) {
            qs = new V3D_Vector(p, s, oom);
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
        V3D_Vector pq = getPq(oom);
        V3D_Vector qr = getQr(oom);
        if (pq.isZeroVector()) {
            if (qr.isZeroVector()) {
                // Rectangle is a point.
            } else {
                // Rectangle is a line.
            }
        } else {
            if (qr.isZeroVector()) {
                // Rectangle is a line.
            } else {
                // Rectangle has area.
                if (qAtOrigin2) {
                    if (!(pq.isOrthogonal(qs, oom))) {
                        throw new RuntimeException("The points do not define a rectangle.");
                    }
                } else {
                    if (!(pq.isOrthogonal(qr, oom))) {
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
        this(new V3D_Point(r.p), new V3D_Point(r.q), new V3D_Point(r.r),
                new V3D_Point(r.s), oom);
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

    /**
     * @param oom The Order of Magnitude for the calculation.
     * @return The vector from {@link #q} to {@link #s}.
     */
    public V3D_Vector getQs(int oom) {
        return new V3D_Vector(getQ(oom), getS(oom), oom);
    }

    /**
     * @param oom The Order of Magnitude for the application of {@link #offset}.
     * @return {@link #s} with {@link #offset} applied.
     */
    public V3D_Point getS(int oom) {
        return new V3D_Point(s).apply(offset, oom);
    }

    @Override
    public V3D_Envelope getEnvelope(int oom) {
        if (en == null) {
            en = new V3D_Envelope(oom, getP(oom), getQ(oom), getR(oom), getS(oom));
        }
        return en;
    }

    /**
     * @param v The vector to apply.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return a new rectangle.
     */
    @Override
    public V3D_Rectangle apply(V3D_Vector v, int oom) {
        return new V3D_Rectangle(getP(oom).apply(v, oom), getQ(oom).apply(v, oom),
                getR(oom).apply(v, oom), getS(oom).apply(v, oom), oom);
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
     * @return The line segment from {@link #p} to {@link #q}.
     */
    protected V3D_LineSegment getPQ(int oom) {
        return new V3D_LineSegment(getP(oom), getQ(oom), oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The line segment from {@link #q} to {@link #r}.
     */
    protected V3D_LineSegment getQR(int oom) {
        return new V3D_LineSegment(getQ(oom), getR(oom), oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The line segment from {@link #r} to {@link #s}.
     */
    protected V3D_LineSegment getRS(int oom) {
        return new V3D_LineSegment(getR(oom), getS(oom), oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The line segment from {@link #s} to {@link #p}.
     */
    protected V3D_LineSegment getSP(int oom) {
        return new V3D_LineSegment(getS(oom), getP(oom), oom);
    }

    private boolean isIntersectedBy0(V3D_Line ls, int oom) {
        return getQR(oom).isIntersectedBy(ls, oom) || getRS(oom).isIntersectedBy(ls, oom)
                || getSP(oom).isIntersectedBy(ls, oom) || getPQ(oom).isIntersectedBy(ls, oom);
    }

    private boolean isIntersectedBy0(V3D_Point pt, int oom) {
        // Special cases
        V3D_Point tp = this.getP(oom);
        if (tp.equals(pt)) {
            return true;
        }
        V3D_Point tq = this.getQ(oom);
        if (tq.equals(pt)) {
            return true;
        }
        V3D_Point tr = this.getR(oom);
        if (tr.equals(pt)) {
            return true;
        }
        V3D_Point ts = this.getS(oom);
        if (ts.equals(pt)) {
            return true;
        }
        if (true) {
            //if (V3D_Geometrics.isCoplanar(this, pt)) {
            // Check the areas
            // Area pqpt
            BigDecimal apqpt = new V3D_Triangle(tp, tq, pt, oom).getArea(oom);
            // Area qrpt
            BigDecimal aqrpt = new V3D_Triangle(tq, tr, pt, oom).getArea(oom);
            // Area rspt
            BigDecimal arspt = new V3D_Triangle(tr, ts, pt, oom).getArea(oom);
            // Area sppt
            BigDecimal asppt = new V3D_Triangle(ts, tp, pt, oom).getArea(oom);
            if (this.getArea(oom).compareTo(apqpt.add(aqrpt).add(arspt).add(asppt)) == 0) {
                return true;
                //return V3D_Geometrics.isCoplanar(this, pt);
            }
        }
        if (getQR(oom).isIntersectedBy(pt, oom) || getRS(oom).isIntersectedBy(pt, oom)
                || getSP(oom).isIntersectedBy(pt, oom) || getPQ(oom).isIntersectedBy(pt, oom)) {
            return true;
        }
        if (getQ(oom).equals(V3D_Environment.P0P0P0)) {
            V3D_Vector ppt = new V3D_Vector(tq, pt, oom);
            V3D_Vector qpt = new V3D_Vector(tp, pt, oom);
            V3D_Vector rpt = new V3D_Vector(tr, pt, oom);
            V3D_Vector spt = new V3D_Vector(ts, pt, oom);
            V3D_Vector rs = new V3D_Vector(tr, ts, oom);
            V3D_Vector sp = new V3D_Vector(ts, tq, oom);
            V3D_Vector cp = getPq(oom).reverse().getCrossProduct(ppt, oom);
            V3D_Vector cq = getQr(oom).getCrossProduct(qpt, oom);
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
            V3D_Vector cp = getPq(oom).getCrossProduct(ppt, oom);
            V3D_Vector cq = getQr(oom).getCrossProduct(qpt, oom);
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
            if (g instanceof V3D_Point) {
                return isIntersectedBy0((V3D_Point) g, oom);
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
                if (g instanceof V3D_Point) {
                    return isIntersectedBy((V3D_Point) g, oom);
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
            if (g instanceof V3D_Point) {
                if (this.isIntersectedBy((V3D_Point) g, oom)) {
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
                V3D_LineSegment qr = getQR(oom);
                V3D_LineSegment rs = getRS(oom);
                V3D_LineSegment sp = getSP(oom);
                V3D_LineSegment pq = getPQ(oom);

                V3D_Geometry ti = qr.getIntersection(li, oom);
                if (ti == null) {
                    // Check ri, b, l
                    V3D_Geometry rii = rs.getIntersection(li, oom);
                    if (rii == null) {
                        // Check b, l
                        V3D_Geometry bi = sp.getIntersection(li, oom);
                        if (bi == null) {
                            // Check l
                            V3D_Geometry tli = pq.getIntersection(li, oom);
                            if (tli == null) {
                                return null;
                            } else {
                                return tli;
                            }
                        } else if (bi instanceof V3D_LineSegment) {
                            return bi;
                        } else {
                            // Check l
                            V3D_Geometry tli = pq.getIntersection(li, oom);
                            if (tli == null) {
                                return bi;
                            } else {
                                return new V3D_LineSegment(
                                        (V3D_Point) bi, (V3D_Point) tli, oom);
                            }
                        }
                    } else if (rii instanceof V3D_LineSegment) {
                        return rii;
                    } else {
                        // Check b, l
                        V3D_Geometry bi = sp.getIntersection(li, oom);
                        if (bi == null) {
                            // Check l
                            V3D_Geometry tli = pq.getIntersection(li, oom);
                            if (tli == null) {
                                return rii;
                            } else {
                                return new V3D_LineSegment((V3D_Point) rii,
                                        (V3D_Point) tli, oom);
                            }
                        } else if (bi instanceof V3D_LineSegment) {
                            return bi;
                        } else {
                            // Check l
                            V3D_Geometry tli = pq.getIntersection(li, oom);
                            if (tli == null) {
                                V3D_Point riip = (V3D_Point) rii;
                                V3D_Point bip = (V3D_Point) bi;
                                if (riip.equals(bip)) {
                                    return bip;
                                } else {
                                    return new V3D_LineSegment(riip, bip, oom);
                                }
                            } else {
                                return new V3D_LineSegment((V3D_Point) bi,
                                        (V3D_Point) tli, oom);
                            }
                        }
                    }
                } else if (ti instanceof V3D_LineSegment) {
                    return ti;
                } else {
                    // Check ri, b, l
                    V3D_Geometry rii = rs.getIntersection(li, oom);
                    if (rii == null) {
                        // Check b, l
                        V3D_Geometry bi = sp.getIntersection(li, oom);
                        if (bi == null) {
                            // Check l
                            V3D_Geometry tli = pq.getIntersection(li, oom);
                            if (tli == null) {
                                return ti;
                            } else {
                                V3D_Point tlip = (V3D_Point) tli;
                                V3D_Point tip = (V3D_Point) ti;
                                if (tlip.equals(tip)) {
                                    return tlip;
                                } else {
                                    return new V3D_LineSegment(tlip, tip, oom);
                                }
                            }
                        } else if (bi instanceof V3D_LineSegment) {
                            return bi;
                        } else {
                            return new V3D_LineSegment((V3D_Point) ti,
                                    (V3D_Point) bi, oom);
                        }
                    } else {
                        V3D_Point tip = (V3D_Point) ti;
                        V3D_Point riip = (V3D_Point) rii;
                        if (tip.equals(riip)) {
                            // Check b, l
                            V3D_Geometry sri = sp.getIntersection(li, oom);
                            if (sri == null) {
                                // Check l
                                V3D_Geometry tli = pq.getIntersection(li, oom);
                                if (tli == null) {
                                    return rii;
                                } else {
                                    return new V3D_LineSegment(riip,
                                            (V3D_Point) tli, oom);
                                }
                            } else if (sri instanceof V3D_LineSegment) {
                                return sri;
                            } else {
                                return new V3D_LineSegment(riip, (V3D_Point) sri, oom);
                            }
                        } else {
                            return new V3D_LineSegment(riip, tip, oom);
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
        boolean lip = isIntersectedBy(l.p, oom);
        boolean liq = isIntersectedBy(l.q, oom);
        if (lip) {
            if (liq) {
                return l;
            } else {
                V3D_Geometry li = getIntersection(l, oom);
                if (li instanceof V3D_Point) {
                    return l.p;
                } else {
                    V3D_LineSegment lli = (V3D_LineSegment) li;
                    if (lli.p.equals(l.p)) {
                        return new V3D_LineSegment(l.p, lli.q, oom);
                    } else {
                        return new V3D_LineSegment(l.p, lli.p, oom);
                    }
                }
            }
        } else {
            V3D_Geometry li = getIntersection(l, oom);
            if (liq) {
                if (li instanceof V3D_Point) {
                    return l.q;
                } else {
                    V3D_LineSegment lli = (V3D_LineSegment) li;
                    if (lli.q.equals(l.q)) {
                        return new V3D_LineSegment(l.q, lli.p, oom);
                    } else {
                        return new V3D_LineSegment(l.q, lli.q, oom);
                    }
                }
            } else {
                if (li instanceof V3D_Point) {
                    V3D_Point pli = (V3D_Point) li;
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
        return getPQ(oom).getLength(oom).toBigDecimal(oomn2)
                .add(getQR(oom).getLength(oom).toBigDecimal(oomn2))
                .multiply(BigDecimal.valueOf(2));
    }

    @Override
    public BigDecimal getArea(int oom) {
//        return Math_BigDecimal.round(l.v.getMagnitude(oomn2)
//                .multiply(t.v.getMagnitude(oomn2)), oom);
        return getPQ(oom).getV(oom).getMagnitude().multiply(getQR(oom).getV(oom).getMagnitude(), oom).toBigDecimal(oom);
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
        BigDecimal ld = getPQ(oom).getDistance(p, oom);
        BigDecimal td = getQR(oom).getDistance(p, oom);
        BigDecimal rd = getRS(oom).getDistance(p, oom);
        BigDecimal bd = getSP(oom).getDistance(p, oom);
        if (dp.compareTo(ld) == 0 && dp.compareTo(td) == 0
                && dp.compareTo(rd) == 0 && dp.compareTo(bd) == 0) {
            return dp;
        } else {
            return Math_BigDecimal.min(ld, td, rd, bd);
        }
    }
}
