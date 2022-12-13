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

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;

/**
 * For representing and processing rectangles in 3D. A rectangle is a right
 * angled quadrilateral. The four corners are the points
 * {@link #p}, {@link #q}, {@link #r} and {@link #s}. The following depicts a
 * rectangle {@code
           qr
 qv  *-------------* r
    |             |
 pq |             | rs
    |             |
  p *-------------* s
           sp
 }
 * The angles PQR, QRS, RSP, SPQ are all 90 degrees or Pi/2 radians.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Rectangle extends V3D_FiniteGeometry implements V3D_Face {

    private static final long serialVersionUID = 1L;

    /**
     * For calculating a corner of the rectangle opposite {@link #r}.
     */
    protected V3D_Vector p;

    /**
     * For calculating a corner of the rectangle opposite {@link #s}.
     */
    protected V3D_Vector q;

    /**
     * For calculating a corner of the rectangle opposite {@link #p}.
     */
    protected V3D_Vector r;

    /**
     * For calculating a corner of the rectangle opposite {@link #q}.
     */
    protected V3D_Vector s;

    /**
     * For storing a triangle that makes up the rectangle.
     */
    protected V3D_Triangle pqr;

    /**
     * For storing a triangle that makes up half the rectangle.
     */
    protected V3D_Triangle rsp;

    /**
     * For storing the convex hull
     */
    protected V3D_ConvexHullCoplanar convexHull;

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@link #rsp} initialising it first if it is {@code null}.
     */
    public V3D_Triangle getRSP(int oom, RoundingMode rm) {
        if (rsp == null) {
            rsp = new V3D_Triangle(offset, r, s, p, oom, rm);
        }
        return rsp;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@link #pqr} initialising it first if it is {@code null}.
     */
    public V3D_Triangle getPQR(int oom, RoundingMode rm) {
        if (pqr == null) {
            pqr = new V3D_Triangle(offset, p, q, r, oom, rm);
        }
        return pqr;
    }

    /**
     * Create a new instance.
     *
     * @param offset What {@link #offset} is set to.
     * @param p The bottom left corner of the rectangle.
     * @param q The top left corner of the rectangle.
     * @param r The top right corner of the rectangle.
     * @param s The bottom right corner of the rectangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @throws java.lang.RuntimeException iff the points do not define a
     * rectangle.
     */
    public V3D_Rectangle(V3D_Vector offset, V3D_Vector p,
            V3D_Vector q, V3D_Vector r, V3D_Vector s, int oom, RoundingMode rm) {
        super(offset);
        this.p = p;
        this.q = q;
        this.r = r;
        this.s = s;
    }

    /**
     * Creates a new instance
     *
     * @param p Used to initialise {@link #offset} and {@link #p}.
     * @param q Used to initialise {@link #q}.
     * @param r Used to initialise {@link #r}.
     * @param s Used to initialise {@link #s}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Rectangle(V3D_Point p, V3D_Point q, V3D_Point r, V3D_Point s,
            int oom, RoundingMode rm) {
        super(p.offset);
        this.p = p.rel;
        V3D_Point qn = new V3D_Point(q);
        qn.setOffset(p.offset, oom, rm);
        this.q = qn.rel;
        V3D_Point rn = new V3D_Point(r);
        rn.setOffset(p.offset, oom, rm);
        this.r = rn.rel;
        V3D_Point sn = new V3D_Point(s);
        sn.setOffset(p.offset, oom, rm);
        this.s = sn.rel;
    }

    @Override
    public String toString() {
        //return toString("");
        return toStringSimple("");
    }

    /**
     * @param pad Padding
     * @return A simple String representation of this.
     */
    public String toStringSimple(String pad) {
        return pad + this.getClass().getSimpleName() + "("
                + toStringFieldsSimple("") + ")";
    }

    @Override
    protected String toStringFields(String pad) {
        return "\n" + super.toStringFields(pad) + ",\n"
                + pad + "p=" + p.toString(pad) + ",\n"
                + pad + "q=" + q.toString(pad) + ",\n"
                + pad + "r=" + r.toString(pad) + ",\n"
                + pad + "s=" + s.toString(pad);
    }

    @Override
    protected String toStringFieldsSimple(String pad) {
        return "\n" + super.toStringFieldsSimple(pad) + ",\n"
                + pad + "p=" + p.toStringSimple(pad) + ",\n"
                + pad + "q=" + q.toStringSimple(pad) + ",\n"
                + pad + "r=" + r.toStringSimple(pad) + ",\n"
                + pad + "s=" + s.toStringSimple(pad);
    }

    @Override
    public V3D_Point[] getPoints(int oom, RoundingMode rm) {
        V3D_Point[] re = new V3D_Point[4];
//        pqr = getPQR(oom, rm);
//        rsp = getRSP(oom, rm);
//        re[0] = rsp.getP();
//        re[1] = rsp.getQ();
//        re[2] = rsp.getR();
//        re[3] = rsp.getQ(); // This may look odd, but it is correct.
        re[0] = getP();
        re[1] = getQ();
        re[2] = getR();
        re[3] = getS();
        return re;
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getP() {
        return new V3D_Point(offset, p);
    }

    /**
     * @return {@link #q} with {@link #offset} applied.
     */
    public V3D_Point getQ() {
        return new V3D_Point(offset, q);
    }

    /**
     * @return {@link #r} with {@link #offset} applied.
     */
    public V3D_Point getR() {
        return new V3D_Point(offset, r);
    }

    /**
     * @return {@link #s} with {@link #offset} applied.
     */
    public V3D_Point getS() {
        return new V3D_Point(offset, s);
    }

    @Override
    public V3D_Envelope getEnvelope(int oom, RoundingMode rm) {
        if (en == null) {
            rsp = getRSP(oom, rm);
            en = getPQR(oom, rm).getEnvelope(oom, rm)
                    .union(getRSP(oom, rm).getEnvelope(oom, rm), oom, rm);
        }
        return en;
    }

    /**
     * @param pt The point to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point or line segment.
     */
    public boolean isIntersectedBy(V3D_Point pt, int oom, RoundingMode rm) {
        if (getPQR(oom, rm).isIntersectedBy(pt, oom, rm)) {
            return true;
        } else {
            return getRSP(oom, rm).isIntersectedBy(pt, oom, rm);
        }
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The line segment from {@link #r} to {@link #s}.
     */
    protected V3D_LineSegment getRS(int oom, RoundingMode rm) {
        //return new V3D_LineSegment(offset, rotate(r, theta), rotate(s, theta), oom);
        return new V3D_LineSegment(offset, r, s, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The line segment from {@link #s} to {@link #p}.
     */
    protected V3D_LineSegment getSP(int oom, RoundingMode rm) {
        //return new V3D_LineSegment(offset, rotate(s, theta), rotate(pl, theta), oom);
        return new V3D_LineSegment(offset, s, p, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The plane of the rectangle from
     * {@link #getPQR(int, java.math.RoundingMode)}.
     */
    public V3D_Plane getPlane(int oom, RoundingMode rm) {
        return getPQR(oom, rm).getPl(oom, rm);
    }

//    @Override
//    protected boolean isIntersectedBy0(V3D_Point pt, int oom, RoundingMode rm) {
//        // Special cases
//        V3D_Point tp = getP();
//        if (tp.equals(pt, oom, rm)) {
//            return true;
//        }
//        V3D_Point tq = getQ();
//        if (tq.equals(pt, oom, rm)) {
//            return true;
//        }
//        V3D_Point tr = getR();
//        if (tr.equals(pt, oom, rm)) {
//            return true;
//        }
//        V3D_Point ts = this.getS();
//        if (ts.equals(pt, oom, rm)) {
//            return true;
//        }
//        if (true) {
//            //if (V3D_Geometrics.isCoplanar(this, pt)) {
//            // Check the areas
//            // Area pqpt
//            Math_BigRational apqpt = new V3D_Triangle(tp.getVector(oom, rm),
//                    tq.getVector(oom, rm), pt.getVector(oom, rm), oom, rm).getArea(oom, rm);
//            // Area qrpt
//            Math_BigRational aqrpt = new V3D_Triangle(tq.getVector(oom, rm),
//                    tr.getVector(oom, rm), pt.getVector(oom, rm), oom, rm).getArea(oom, rm);
//            // Area rspt
//            Math_BigRational arspt = new V3D_Triangle(tr.getVector(oom, rm),
//                    ts.getVector(oom, rm), pt.getVector(oom, rm), oom, rm).getArea(oom, rm);
//            // Area sppt
//            Math_BigRational asppt = new V3D_Triangle(ts.getVector(oom, rm),
//                    tp.getVector(oom, rm), pt.getVector(oom, rm), oom, rm).getArea(oom, rm);
//            if (this.getArea(oom, rm).compareTo(apqpt.add(aqrpt).add(arspt).add(asppt)) == 0) {
//                return true;
//                //return V3D_Geometrics.isCoplanar(this, pt);
//            }
//        }
//        if (getQR(oom, rm).isIntersectedBy(pt, oom, rm)
//                || getRS(oom, rm).isIntersectedBy(pt, oom, rm)
//                || getSP(oom, rm).isIntersectedBy(pt, oom, rm)
//                || getPQ(oom, rm).isIntersectedBy(pt, oom, rm)) {
//            return true;
//        }
//        if (getQ().equals(V3D_Point.ORIGIN, oom, rm)) {
//            V3D_Vector ppt = new V3D_Vector(tq, pt, oom, rm);
//            V3D_Vector qpt = new V3D_Vector(tp, pt, oom, rm);
//            V3D_Vector rpt = new V3D_Vector(tr, pt, oom, rm);
//            V3D_Vector spt = new V3D_Vector(ts, pt, oom, rm);
//            V3D_Vector rs = new V3D_Vector(tr, ts, oom, rm);
//            V3D_Vector sp = new V3D_Vector(ts, tq, oom, rm);
//            V3D_Vector cp = getPQV(oom, rm).reverse().getCrossProduct(ppt, oom, rm);
//            V3D_Vector cq = getQRV(oom, rm).getCrossProduct(qpt, oom, rm);
//            V3D_Vector cr = rs.getCrossProduct(rpt, oom, rm);
//            V3D_Vector cs = sp.getCrossProduct(spt, oom, rm);
//            /**
//             * If cp, cq, cr, and cs are all in the same direction then pt
//             * intersects.
//             */
//            Math_BigRational mp = cp.getMagnitudeSquared();
//            Math_BigRational mq = cq.getMagnitudeSquared();
//            V3D_Vector cpq = cp.add(cq, oom, rm);
//            Math_BigRational mpq = cpq.getMagnitudeSquared();
//            if (mpq.compareTo(mp) == 1 && mpq.compareTo(mq) == 1) {
//                Math_BigRational mr = cr.getMagnitudeSquared();
//                V3D_Vector cpqr = cpq.add(cr, oom, rm);
//                Math_BigRational mpqr = cpqr.getMagnitudeSquared();
//                if (mpqr.compareTo(mr) == 1 && mpqr.compareTo(mpq) == 1) {
//                    Math_BigRational ms = cs.getMagnitudeSquared();
//                    Math_BigRational mpqrs = cpqr.add(cs, oom, rm).getMagnitudeSquared();
//                    if (mpqrs.compareTo(ms) == 1 && mpqrs.compareTo(mpqr) == 1) {
//                        return true;
//                    }
//                }
//            }
//        } else {
//            V3D_Vector ppt = new V3D_Vector(tp, pt, oom, rm);
//            V3D_Vector qpt = new V3D_Vector(tq, pt, oom, rm);
//            V3D_Vector rpt = new V3D_Vector(tr, pt, oom, rm);
//            V3D_Vector spt = new V3D_Vector(ts, pt, oom, rm);
//            V3D_Vector rs = new V3D_Vector(tr, ts, oom, rm);
//            V3D_Vector sp = new V3D_Vector(ts, tp, oom, rm);
//            V3D_Vector cp = getPQV(oom, rm).getCrossProduct(ppt, oom, rm);
//            V3D_Vector cq = getQRV(oom, rm).getCrossProduct(qpt, oom, rm);
//            V3D_Vector cr = rs.getCrossProduct(rpt, oom, rm);
//            V3D_Vector cs = sp.getCrossProduct(spt, oom, rm);
//            /**
//             * If cp, cq, cr, and cs are all in the same direction then pt
//             * intersects.
//             */
//            Math_BigRational mp = cp.getMagnitudeSquared();
//            Math_BigRational mq = cq.getMagnitudeSquared();
//            V3D_Vector cpq = cp.add(cq, oom, rm);
//            Math_BigRational mpq = cpq.getMagnitudeSquared();
//            if (mpq.compareTo(mp) == 1 && mpq.compareTo(mq) == 1) {
//                Math_BigRational mr = cr.getMagnitudeSquared();
//                V3D_Vector cpqr = cpq.add(cr, oom, rm);
//                Math_BigRational mpqr = cpqr.getMagnitudeSquared();
//                if (mpqr.compareTo(mr) == 1 && mpqr.compareTo(mpq) == 1) {
//                    Math_BigRational ms = cs.getMagnitudeSquared();
//                    Math_BigRational mpqrs = cpqr.add(cs, oom, rm).getMagnitudeSquared();
//                    if (mpqrs.compareTo(ms) == 1 && mpqrs.compareTo(mpqr) == 1) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
    /**
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point or line segment.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Line l, int oom, RoundingMode rm) {
        if (getPlane(oom, rm).getIntersection(l, oom, rm) != null) {
            V3D_FiniteGeometry i1 = getPQR(oom, rm).getIntersection(l, oom, rm);
            V3D_FiniteGeometry i2 = getRSP(oom, rm).getIntersection(l, oom, rm);
            if (i1 == null) {
                if (i2 == null) {
                    return null;
                } else {
                    return i2;
                }
            } else {
                if (i2 == null) {
                    return i1;
                } else {
                    return join(i1, i2, oom, rm);
                }
            }
        } else {
            return null;
        }
//        V3D_Geometry g = new V3D_Plane(this).getIntersection(l, oom);
//        if (g == null) {
//            return null;
//        } else {
//            if (g instanceof V3D_Point v3D_Point) {
//                if (this.isIntersectedBy(v3D_Point, oom)) {
//                    return g;
//                } else {
//                    return null;
//                }
//            } else {
//                V3D_Line li = (V3D_Line) g;
//                /**
//                 * Whilst in theory a test of the envelope might seem a good
//                 * idea (for speeding things up), timing experiments are wanted
//                 * to be sure it does. Optimisation plans should bear in mind
//                 * that some parts of intersection algorithms that can be done
//                 * in parallel, so it might not be obvious what the quickest
//                 * ways are! One of the reasons that V3D_Envelope has it's own
//                 * geometry is because they needed their own special rectangles
//                 * otherwise this "optimisation" resulted in a StackOverflow
//                 * error.
//                 */
//                // Quick test of the envelope?!
//                if (!getEnvelope(oom).isIntersectedBy(l, oom)) {
//                    return null;
//                }
//                /**
//                 * Get the intersection of the line and each edge of the
//                 * rectangle.
//                 */
//                V3D_LineSegment tqr = getQR();
//                V3D_LineSegment trs = getRS();
//                V3D_LineSegment tsp = getSP();
//                V3D_LineSegment tpq = getPQ();
//
//                V3D_Geometry ti = tqr.getIntersection(li, oom);
//                if (ti == null) {
//                    // Check ri, b, l
//                    V3D_Geometry rii = trs.getIntersection(li, oom);
//                    if (rii == null) {
//                        // Check b, l
//                        V3D_Geometry bi = tsp.getIntersection(li, oom);
//                        if (bi == null) {
//                            // Check l
//                            V3D_Geometry tli = tpq.getIntersection(li, oom);
//                            if (tli == null) {
//                                return null;
//                            } else {
//                                return tli;
//                            }
//                        } else if (bi instanceof V3D_LineSegment) {
//                            return bi;
//                        } else {
//                            // Check l
//                            V3D_Geometry tli = tpq.getIntersection(li, oom);
//                            if (tli == null) {
//                                return bi;
//                            } else {
//                                return new V3D_LineSegment(
//                                        ((V3D_Point) bi).getVector(oom),
//                                        ((V3D_Point) tli).getVector(oom), oom);
//                            }
//                        }
//                    } else if (rii instanceof V3D_LineSegment) {
//                        return rii;
//                    } else {
//                        // Check b, l
//                        V3D_Geometry bi = tsp.getIntersection(li, oom);
//                        if (bi == null) {
//                            // Check l
//                            V3D_Geometry tli = tpq.getIntersection(li, oom);
//                            if (tli == null) {
//                                return rii;
//                            } else {
//                                return new V3D_LineSegment(
//                                        ((V3D_Point) rii).getVector(oom),
//                                        ((V3D_Point) tli).getVector(oom), oom);
//                            }
//                        } else if (bi instanceof V3D_LineSegment) {
//                            return bi;
//                        } else {
//                            // Check l
//                            V3D_Geometry tli = tpq.getIntersection(li, oom);
//                            if (tli == null) {
//                                V3D_Point riip = (V3D_Point) rii;
//                                V3D_Point bip = (V3D_Point) bi;
//                                if (riip.equals(bip)) {
//                                    return bip;
//                                } else {
//                                    return new V3D_LineSegment(
//                                            riip.getVector(oom),
//                                            bip.getVector(oom), oom);
//                                }
//                            } else {
//                                return new V3D_LineSegment(
//                                        ((V3D_Point) bi).getVector(oom),
//                                        ((V3D_Point) tli).getVector(oom), oom);
//                            }
//                        }
//                    }
//                } else if (ti instanceof V3D_LineSegment) {
//                    return ti;
//                } else {
//                    // Check ri, b, l
//                    V3D_Geometry rii = trs.getIntersection(li, oom);
//                    if (rii == null) {
//                        // Check b, l
//                        V3D_Geometry bi = tsp.getIntersection(li, oom);
//                        if (bi == null) {
//                            // Check l
//                            V3D_Geometry tli = tpq.getIntersection(li, oom);
//                            if (tli == null) {
//                                return ti;
//                            } else {
//                                V3D_Point tlip = (V3D_Point) tli;
//                                V3D_Point tip = (V3D_Point) ti;
//                                if (tlip.equals(tip)) {
//                                    return tlip;
//                                } else {
//                                    return new V3D_LineSegment(
//                                            tlip.getVector(oom),
//                                            tip.getVector(oom),
//                                            oom);
//                                }
//                            }
//                        } else if (bi instanceof V3D_LineSegment) {
//                            return bi;
//                        } else {
//                            return new V3D_LineSegment(
//                                    ((V3D_Point) ti).getVector(oom),
//                                    ((V3D_Point) bi).getVector(oom), oom);
//                        }
//                    } else {
//                        V3D_Point tip = (V3D_Point) ti;
//                        V3D_Point riip = (V3D_Point) rii;
//                        if (tip.equals(riip)) {
//                            // Check b, l
//                            V3D_Geometry sri = tsp.getIntersection(li, oom);
//                            if (sri == null) {
//                                // Check l
//                                V3D_Geometry tli = tpq.getIntersection(li, oom);
//                                if (tli == null) {
//                                    return rii;
//                                } else {
//                                    return new V3D_LineSegment(
//                                            riip.getVector(oom),
//                                            ((V3D_Point) tli).getVector(oom), oom);
//                                }
//                            } else if (sri instanceof V3D_LineSegment) {
//                                return sri;
//                            } else {
//                                return new V3D_LineSegment(
//                                        riip.getVector(oom),
//                                        ((V3D_Point) sri).getVector(oom), oom);
//                            }
//                        } else {
//                            return new V3D_LineSegment(
//                                    riip.getVector(oom), tip.getVector(oom), oom);
//                        }
//                    }
//                }
//            }
//        }
    }

    /**
     * If pointOrLineSegment1 is a line segment then either pointOrLineSegment2
     * is a point at the end of the it or pointOrLineSegment2 is a joining line
     * segment that can be appended. Similarly, if pointOrLineSegment2 is a line
     * segment then either pointOrLineSegment1 is a point at the end of it or
     * pointOrLineSegment1 is a joining line segment that can be appended.
     *
     * @param pointOrLineSegment1 A point or line segment to join.
     * @param pointOrLineSegment2 A point or line segment to join.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point or line segment.
     */
    protected V3D_FiniteGeometry join(V3D_FiniteGeometry pointOrLineSegment1,
            V3D_FiniteGeometry pointOrLineSegment2, int oom, RoundingMode rm) {
        if (pointOrLineSegment1 instanceof V3D_LineSegment l1) {
            if (pointOrLineSegment2 instanceof V3D_LineSegment l2) {
                return V3D_LineSegment.getGeometry(oom, rm, l1.getP(), l1.getQ(), l2.getP(), l2.getQ());
            } else {
                return l1;
            }
        } else {
            if (pointOrLineSegment2 instanceof V3D_LineSegment l2) {
                return l2;
            } else {
                if (((V3D_Point) pointOrLineSegment1).equals((V3D_Point) pointOrLineSegment2, oom, rm)) {
                    return pointOrLineSegment1;
                }
                return (V3D_Point) pointOrLineSegment1;
            }
        }
    }

    /**
     * Calculates and returns the intersections between {@code this} and
     * {@code l}.
     *
     * @param l The line segment to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection or {@code null} iff there is no intersection.
     */
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment l, int oom, RoundingMode rm) {
        V3D_FiniteGeometry i1 = getPQR(oom, rm).getIntersection(l, oom, rm);
        V3D_FiniteGeometry i2 = getRSP(oom, rm).getIntersection(l, oom, rm);
        if (i1 == null) {
            return i2;
        } else {
            if (i2 == null) {
                return i1;
            } else {
                return join(i1, i2, oom, rm);
            }
        }
//        V3D_Point lp = l.getP(oom);
//        V3D_Point lq = l.getQ(oom);
//        boolean lip = isIntersectedBy(lp, oom);
//        boolean liq = isIntersectedBy(lq, oom);
//        if (lip) {
//            if (liq) {
//                return l;
//            } else {
//                V3D_Geometry li = getIntersection(l, oom);
//                if (li instanceof V3D_Point) {
//                    return lp;
//                } else {
//                    V3D_LineSegment lli = (V3D_LineSegment) li;
//                    V3D_Vector llip = lli.getP();
//                    V3D_Vector lpp = l.getP();
//                    if (llip.equals(lpp)) {
//                        return new V3D_LineSegment(lpp, lli.getQ(), oom);
//                    } else {
//                        return new V3D_LineSegment(lpp, llip, oom);
//                    }
//                }
//            }
//        } else {
//            V3D_Geometry li = getIntersection(l, oom);
//            if (liq) {
//                if (li instanceof V3D_Point) {
//                    return lq;
//                } else {
//                    V3D_LineSegment lli = (V3D_LineSegment) li;
//                    V3D_Vector lliq = lli.getQ();
//                    V3D_Vector lqq = l.getQ();
//                    if (lliq.equals(lqq)) {
//                        return new V3D_LineSegment(lqq, lli.getP(), oom);
//                    } else {
//                        return new V3D_LineSegment(lqq, lliq, oom);
//                    }
//                }
//            } else {
//                if (li instanceof V3D_Point pli) {
//                    if (l.isIntersectedBy(pli, oom)) {
//                        return pli;
//                    } else {
//                        return null;
//                    }
//                } else {
//                    return li;
//                }
//            }
//        }
    }

    @Override
    public BigRational getPerimeter(int oom, RoundingMode rm) {
        int oomn2 = oom - 2;
        pqr = getPQR(oomn2, rm);
        return (pqr.getPQ(oomn2, rm).getLength(oomn2, rm).getSqrt(oom, rm)
                .add(pqr.getQR(oomn2, rm).getLength(oomn2, rm).getSqrt(oom, rm)))
                .multiply(BigRational.TWO);
    }

    @Override
    public BigRational getArea(int oom, RoundingMode rm) {
        int oomn2 = oom - 2;
        pqr = getPQR(oomn2, rm);
        return (pqr.getPQ(oomn2, rm).getLength(oomn2, rm).multiply(
                pqr.getQR(oomn2, rm).getLength(oomn2, rm), oomn2, rm))
                .getSqrt(oom, rm);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance from {@code this} to {@code pl}.
     */
    public BigRational getDistance(V3D_Point p, int oom, RoundingMode rm) {
        return (new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm))
                .getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code pt}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared to {@code p}.
     */
    public BigRational getDistanceSquared(V3D_Point pt, int oom, RoundingMode rm) {
        BigRational d1 = getPQR(oom, rm).getDistanceSquared(pt, oom, rm);
        BigRational d2 = getRSP(oom, rm).getDistanceSquared(pt, oom, rm);
        return d1.min(d2);
    }

    /**
     * Change {@link #offset} without changing the overall line.
     *
     * @param offset What {@link #offset} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public void setOffset(V3D_Vector offset, int oom, RoundingMode rm) {
        p = p.add(offset, oom, rm).subtract(this.offset, oom, rm);
        q = q.add(offset, oom, rm).subtract(this.offset, oom, rm);
        r = r.add(offset, oom, rm).subtract(this.offset, oom, rm);
        s = s.add(offset, oom, rm).subtract(this.offset, oom, rm);
        pqr = null;
        rsp = null;
        convexHull = null;
    }

    /**
     * Move the rectangle.
     *
     * @param v What is added to {@link #p}, {@link #q}, {@link #r}, {@link #s}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    @Override
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        this.pqr = null;
        this.rsp = null;
        convexHull = null;
    }

    @Override
    public V3D_Rectangle rotate(V3D_Line axis,
            BigRational theta, int oom, RoundingMode rm) {
        return new V3D_Rectangle(
                getP().rotate(axis, theta, oom, rm),
                getQ().rotate(axis, theta, oom, rm),
                getR().rotate(axis, theta, oom, rm),
                getS().rotate(axis, theta, oom, rm), oom, rm);
    }

    /**
     * Calculate and return the intersection between {@code this} and {@code pl}
     *
     * @param pl The plane to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code this} and {@code pl}.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Plane pl, int oom, RoundingMode rm) {
        V3D_FiniteGeometry pqri = getPQR(oom, rm).getIntersection(pl, oom, rm);
        if (pqri == null) {
            return getRSP(oom, rm).getIntersection(pl, oom, rm);
        } else {
            if (pqri instanceof V3D_Triangle) {
                return this;
            }
            V3D_FiniteGeometry rspi = getRSP(oom, rm).getIntersection(pl, oom, rm);
            if (rspi == null) {
                return pqri;
            }
            return join(pqri, rspi, oom, rm);
        }
    }

    /**
     * Computes and returns the intersection between {@code this} and {@code t}.
     * The intersection could be: null, a point, a line segment, a triangle, or
     * a V3D_ConvexHullCoplanar (with 4, 5, 6 or 7 sides).
     *
     * @param t The triangle intersect with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Triangle t, int oom, RoundingMode rm) {
        V3D_FiniteGeometry pqrit = getPQR(oom, rm).getIntersection(t, oom, rm);
        V3D_FiniteGeometry rspit = getRSP(oom, rm).getIntersection(t, oom, rm);
        if (pqrit == null) {
            return rspit;
        } else {
            if (rspit == null) {
                return pqrit;
            }
            if (pqrit instanceof V3D_Point) {
                return rspit;
            } else if (pqrit instanceof V3D_LineSegment t1il) {
                if (rspit instanceof V3D_Point) {
                    return pqrit;
                } else if (rspit instanceof V3D_LineSegment t2il) {
                    return V3D_LineSegmentsCollinear.getGeometry(t1il, t2il, oom, rm);
                } else {
                    return rspit;
                }
            } else {
                if (rspit instanceof V3D_Triangle t2it) {
                    return new V3D_ConvexHullCoplanar(oom, rm, (V3D_Triangle) pqrit, t2it).simplify(oom, rm);
                } else {
                    return pqrit;
                }
            }
        }
    }

    /**
     * Get the intersection between the geometry and the line segment {@code l}.
     *
     * @param r The ray to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Ray r, int oom,
            RoundingMode rm) {
        V3D_FiniteGeometry gpqr = getPQR(oom, rm).getIntersection(r, oom, rm);
        V3D_FiniteGeometry grsp = getRSP(oom, rm).getIntersection(r, oom, rm);
        if (gpqr == null) {
            return grsp;
        } else {
            if (grsp == null) {
                return gpqr;
            }
            return join(gpqr, grsp, oom, rm);
        }
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistance(V3D_Line l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom - 6, rm), oom, rm).getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistanceSquared(V3D_Line l, int oom, RoundingMode rm) {
        return getRSP(oom, rm).getDistanceSquared(l, oom, rm).min(
                getPQR(oom, rm).getDistanceSquared(l, oom, rm));
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistance(V3D_LineSegment l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom - 6, rm), oom, rm).getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistanceSquared(V3D_LineSegment l, int oom, RoundingMode rm) {
        return getRSP(oom, rm).getDistanceSquared(l, oom, rm).min(
                getPQR(oom, rm).getDistanceSquared(l, oom, rm));
    }

    /**
     * Get the minimum distance to {@code pl}.
     *
     * @param pl A plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code p}.
     */
    public BigRational getDistance(V3D_Plane pl, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(pl, oom - 6, rm), oom, rm).getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code pl}.
     *
     * @param pl A plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code p}.
     */
    public BigRational getDistanceSquared(V3D_Plane pl, int oom, RoundingMode rm) {
        return getRSP(oom, rm).getDistanceSquared(pl, oom, rm).min(
                getPQR(oom, rm).getDistanceSquared(pl, oom, rm));
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code t}.
     */
    public BigRational getDistance(V3D_Triangle t, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(t, oom - 6, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code t}.
     */
    public BigRational getDistanceSquared(V3D_Triangle t, int oom, RoundingMode rm) {
        return getRSP(oom, rm).getDistanceSquared(t, oom, rm).min(
                getPQR(oom, rm).getDistanceSquared(t, oom, rm));
    }

    /**
     * @param r The rectangle to test if it is equal to this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff this is equal to r.
     */
    //@Overrides
    public boolean equals(V3D_Rectangle r, int oom, RoundingMode rm) {
        V3D_Point[] pts = getPoints(oom, rm);
        V3D_Point[] rpts = r.getPoints(oom, rm);
        for (var x : pts) {
            boolean found = false;
            for (var y : rpts) {
                if (x.equals(y, oom, rm)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        for (var x : rpts) {
            boolean found = false;
            for (var y : pts) {
                if (x.equals(y, oom, rm)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * Initialises {@link #convexHull} if it is {@code null} and returns it.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@link #convexHull} initialising it if it is {@code null}.
     */
    public V3D_ConvexHullCoplanar getConvexHull(int oom, RoundingMode rm) {
        if (convexHull == null) {
            convexHull = new V3D_ConvexHullCoplanar(oom, rm, getPQR(oom, rm),
                    getRSP(oom, rm));
        }
        return convexHull;
    }

    /**
     * For testing if four points form a rectangle.
     *
     * @param p First clockwise or anti-clockwise point.
     * @param q Second clockwise or anti-clockwise point.
     * @param r Third clockwise or anti-clockwise point.
     * @param s Fourth clockwise or anti-clockwise point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff pl, qv, r and s form a rectangle.
     */
    public static boolean isRectangle(V3D_Point p, V3D_Point q,
            V3D_Point r, V3D_Point s, int oom, RoundingMode rm) {
        V3D_LineSegment pq = new V3D_LineSegment(p, q, oom, rm);
        V3D_LineSegment qr = new V3D_LineSegment(q, r, oom, rm);
        V3D_LineSegment rs = new V3D_LineSegment(r, s, oom, rm);
        V3D_LineSegment sp = new V3D_LineSegment(s, p, oom, rm);
        if (pq.l.isParallel(rs.l, oom, rm)) {
            if (qr.l.isParallel(sp.l, oom, rm)) {
                return true;
            }
        }
        return false;
    }
}
