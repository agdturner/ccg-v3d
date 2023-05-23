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

/**
 * For representing and processing rectangles in 3D. A rectangle is a right
 * angled quadrilateral. The four corners are the points
 * {@link #p}, {@link #q}, {@link #r} and {@link #s}. The following depicts a
 * rectangle {@code
 *           qr
 * qv  *-------------* r
 *     |             |
 * pq  |             | rs
 *     |             |
 * p   *-------------* s
 *           sp
 * }
 * The angles PQR, QRS, RSP, SPQ are all 90 degrees or Pi/2 radians.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_RectangleDouble extends V3D_FiniteGeometryDouble
        implements V3D_FaceDouble {

    private static final long serialVersionUID = 1L;

    /**
     * For calculating a corner of the rectangle opposite {@link #r}.
     */
    protected V3D_VectorDouble p;

    /**
     * For calculating a corner of the rectangle opposite {@link #s}.
     */
    protected V3D_VectorDouble q;

    /**
     * For calculating a corner of the rectangle opposite {@link #p}.
     */
    protected V3D_VectorDouble r;

    /**
     * For calculating a corner of the rectangle opposite {@link #q}.
     */
    protected V3D_VectorDouble s;

    /**
     * For storing a triangle that makes up the rectangle.
     */
    protected V3D_TriangleDouble pqr;

    /**
     * For storing a triangle that makes up half the rectangle.
     */
    protected V3D_TriangleDouble rsp;

    /**
     * For storing the convex hull
     */
    protected V3D_ConvexHullCoplanarDouble convexHull;

    /**
     * @return {@link #rsp} initialising it first if it is {@code null}.
     */
    public V3D_TriangleDouble getRSP() {
        if (rsp == null) {
            rsp = new V3D_TriangleDouble(offset, r, s, p);
        }
        return rsp;
    }

    /**
     * @return {@link #pqr} initialising it first if it is {@code null}.
     */
    public V3D_TriangleDouble getPQR() {
        if (pqr == null) {
            pqr = new V3D_TriangleDouble(offset, p, q, r);
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
     * @throws java.lang.RuntimeException iff the points do not define a
     * rectangle.
     */
    public V3D_RectangleDouble(V3D_VectorDouble offset, V3D_VectorDouble p,
            V3D_VectorDouble q, V3D_VectorDouble r, V3D_VectorDouble s) {
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
     */
    public V3D_RectangleDouble(V3D_PointDouble p, V3D_PointDouble q,
            V3D_PointDouble r, V3D_PointDouble s) {
        super(p.offset);
        this.p = p.rel;
        V3D_PointDouble qn = new V3D_PointDouble(q);
        qn.setOffset(p.offset);
        this.q = qn.rel;
        V3D_PointDouble rn = new V3D_PointDouble(r);
        rn.setOffset(p.offset);
        this.r = rn.rel;
        V3D_PointDouble sn = new V3D_PointDouble(s);
        sn.setOffset(p.offset);
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
    public V3D_PointDouble[] getPoints() {
        V3D_PointDouble[] re = new V3D_PointDouble[4];
        re[0] = getP();
        re[1] = getQ();
        re[2] = getR();
        re[3] = getS();
        return re;
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_PointDouble getP() {
        return new V3D_PointDouble(offset, p);
    }

    /**
     * @return {@link #q} with {@link #offset} applied.
     */
    public V3D_PointDouble getQ() {
        return new V3D_PointDouble(offset, q);
    }

    /**
     * @return {@link #r} with {@link #offset} applied.
     */
    public V3D_PointDouble getR() {
        return new V3D_PointDouble(offset, r);
    }

    /**
     * @return {@link #s} with {@link #offset} applied.
     */
    public V3D_PointDouble getS() {
        return new V3D_PointDouble(offset, s);
    }

    @Override
    public V3D_EnvelopeDouble getEnvelope() {
        if (en == null) {
            rsp = getRSP();
            en = getPQR().getEnvelope().union(getRSP().getEnvelope());
        }
        return en;
    }

    /**
     * @param pt The point to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    public boolean isIntersectedBy(V3D_PointDouble pt, double epsilon) {
        if (getPQR().isIntersectedBy(pt, epsilon)) {
            return true;
        } else {
            return getRSP().isIntersectedBy(pt, epsilon);
        }
    }

    /**
     * @return The line segment from {@link #r} to {@link #s}.
     */
    protected V3D_LineSegmentDouble getRS() {
        //return new V3D_LineSegment(offset, rotate(r, theta), rotate(s, theta), oom);
        return new V3D_LineSegmentDouble(offset, r, s);
    }

    /**
     * @return The line segment from {@link #s} to {@link #p}.
     */
    protected V3D_LineSegmentDouble getSP() {
        //return new V3D_LineSegment(offset, rotate(s, theta), rotate(pl, theta), oom);
        return new V3D_LineSegmentDouble(offset, s, p);
    }

    /**
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The plane of the rectangle from {@link #getPQR()}.
     */
    public V3D_PlaneDouble getPlane(double epsilon) {
        return getPQR().getPl(epsilon);
    }

//    @Override
//    protected boolean isIntersectedBy0(V3D_Point pt, int oom, RoundingMode rm) {
//        // Special cases
//        V3D_Point tp = getP();
//        if (tp.equals(pt)) {
//            return true;
//        }
//        V3D_Point tq = getQ();
//        if (tq.equals(pt)) {
//            return true;
//        }
//        V3D_Point tr = getR();
//        if (tr.equals(pt)) {
//            return true;
//        }
//        V3D_Point ts = this.getS();
//        if (ts.equals(pt)) {
//            return true;
//        }
//        if (true) {
//            //if (V3D_Geometrics.isCoplanar(this, pt)) {
//            // Check the areas
//            // Area pqpt
//            Math_BigRational apqpt = new V3D_Triangle(tp.getVector(),
//                    tq.getVector(), pt.getVector()).getArea();
//            // Area qrpt
//            Math_BigRational aqrpt = new V3D_Triangle(tq.getVector(),
//                    tr.getVector(), pt.getVector()).getArea();
//            // Area rspt
//            Math_BigRational arspt = new V3D_Triangle(tr.getVector(),
//                    ts.getVector(), pt.getVector()).getArea();
//            // Area sppt
//            Math_BigRational asppt = new V3D_Triangle(ts.getVector(),
//                    tp.getVector(), pt.getVector()).getArea();
//            if (this.getArea().compareTo(apqpt.add(aqrpt).add(arspt).add(asppt)) == 0) {
//                return true;
//                //return V3D_Geometrics.isCoplanar(this, pt);
//            }
//        }
//        if (getQR().isIntersectedBy(pt)
//                || getRS().isIntersectedBy(pt)
//                || getSP().isIntersectedBy(pt)
//                || getPQ().isIntersectedBy(pt)) {
//            return true;
//        }
//        if (getQ().equals(V3D_Point.ORIGIN)) {
//            V3D_Vector ppt = new V3D_Vector(tq, pt);
//            V3D_Vector qpt = new V3D_Vector(tp, pt);
//            V3D_Vector rpt = new V3D_Vector(tr, pt);
//            V3D_Vector spt = new V3D_Vector(ts, pt);
//            V3D_Vector rs = new V3D_Vector(tr, ts);
//            V3D_Vector sp = new V3D_Vector(ts, tq);
//            V3D_Vector cp = getPQV().reverse().getCrossProduct(ppt);
//            V3D_Vector cq = getQRV().getCrossProduct(qpt);
//            V3D_Vector cr = rs.getCrossProduct(rpt);
//            V3D_Vector cs = sp.getCrossProduct(spt);
//            /**
//             * If cp, cq, cr, and cs are all in the same direction then pt
//             * intersects.
//             */
//            Math_BigRational mp = cp.getMagnitudeSquared();
//            Math_BigRational mq = cq.getMagnitudeSquared();
//            V3D_Vector cpq = cp.add(cq);
//            Math_BigRational mpq = cpq.getMagnitudeSquared();
//            if (mpq.compareTo(mp) == 1 && mpq.compareTo(mq) == 1) {
//                Math_BigRational mr = cr.getMagnitudeSquared();
//                V3D_Vector cpqr = cpq.add(cr);
//                Math_BigRational mpqr = cpqr.getMagnitudeSquared();
//                if (mpqr.compareTo(mr) == 1 && mpqr.compareTo(mpq) == 1) {
//                    Math_BigRational ms = cs.getMagnitudeSquared();
//                    Math_BigRational mpqrs = cpqr.add(cs).getMagnitudeSquared();
//                    if (mpqrs.compareTo(ms) == 1 && mpqrs.compareTo(mpqr) == 1) {
//                        return true;
//                    }
//                }
//            }
//        } else {
//            V3D_Vector ppt = new V3D_Vector(tp, pt);
//            V3D_Vector qpt = new V3D_Vector(tq, pt);
//            V3D_Vector rpt = new V3D_Vector(tr, pt);
//            V3D_Vector spt = new V3D_Vector(ts, pt);
//            V3D_Vector rs = new V3D_Vector(tr, ts);
//            V3D_Vector sp = new V3D_Vector(ts, tp);
//            V3D_Vector cp = getPQV().getCrossProduct(ppt);
//            V3D_Vector cq = getQRV().getCrossProduct(qpt);
//            V3D_Vector cr = rs.getCrossProduct(rpt);
//            V3D_Vector cs = sp.getCrossProduct(spt);
//            /**
//             * If cp, cq, cr, and cs are all in the same direction then pt
//             * intersects.
//             */
//            Math_BigRational mp = cp.getMagnitudeSquared();
//            Math_BigRational mq = cq.getMagnitudeSquared();
//            V3D_Vector cpq = cp.add(cq);
//            Math_BigRational mpq = cpq.getMagnitudeSquared();
//            if (mpq.compareTo(mp) == 1 && mpq.compareTo(mq) == 1) {
//                Math_BigRational mr = cr.getMagnitudeSquared();
//                V3D_Vector cpqr = cpq.add(cr);
//                Math_BigRational mpqr = cpqr.getMagnitudeSquared();
//                if (mpqr.compareTo(mr) == 1 && mpqr.compareTo(mpq) == 1) {
//                    Math_BigRational ms = cs.getMagnitudeSquared();
//                    Math_BigRational mpqrs = cpqr.add(cs).getMagnitudeSquared();
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_LineDouble l,
            double epsilon) {
        if (getPlane(epsilon).getIntersection(l, epsilon) != null) {
            V3D_FiniteGeometryDouble i1 = getPQR().getIntersection(l, epsilon);
            V3D_FiniteGeometryDouble i2 = getRSP().getIntersection(l, epsilon);
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
                    return join(i1, i2, epsilon);
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
     * @param pol1 A point or line segment to join.
     * @param pol2 A point or line segment to join.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    protected V3D_FiniteGeometryDouble join(V3D_FiniteGeometryDouble pol1,
            V3D_FiniteGeometryDouble pol2, double epsilon) {
        if (pol1 instanceof V3D_LineSegmentDouble l1) {
            if (pol2 instanceof V3D_LineSegmentDouble l2) {
                return V3D_LineSegmentDouble.getGeometry(epsilon, l1.getP(), 
                        l1.getQ(),                        l2.getP(), l2.getQ());
            } else {
                return l1;
            }
        } else {
            if (pol2 instanceof V3D_LineSegmentDouble l2) {
                return l2;
            } else {
                if (((V3D_PointDouble) pol1).equals((V3D_PointDouble) pol2, epsilon)) {
                    return pol1;
                }
                return (V3D_PointDouble) pol1;
            }
        }
    }

    /**
     * Calculates and returns the intersections between {@code this} and
     * {@code l}.
     *
     * @param l The line segment to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection or {@code null} iff there is no intersection.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_LineSegmentDouble l,
            double epsilon) {
        V3D_FiniteGeometryDouble i1 = getPQR().getIntersection(l, epsilon);
        V3D_FiniteGeometryDouble i2 = getRSP().getIntersection(l, epsilon);
        if (i1 == null) {
            return i2;
        } else {
            if (i2 == null) {
                return i1;
            } else {
                return join(i1, i2, epsilon);
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
    public double getPerimeter() {
        pqr = getPQR();
        return (pqr.getPQ().getLength() + pqr.getQR().getLength()) * 2d;
    }

    @Override
    public double getArea() {
        pqr = getPQR();
        return pqr.getPQ().getLength() * pqr.getQR().getLength();
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param p A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance from {@code this} to {@code pl}.
     */
    public double getDistance(V3D_PointDouble p, double epsilon) {
        return Math.sqrt(getDistanceSquared(p, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code pt}.
     *
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance squared to {@code p}.
     */
    public double getDistanceSquared(V3D_PointDouble pt, double epsilon) {
        double d1 = getPQR().getDistanceSquared(pt, epsilon);
        double d2 = getRSP().getDistanceSquared(pt, epsilon);
        return Math.min(d1, d2);
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
        pqr = null;
        rsp = null;
        convexHull = null;
    }

    /**
     * Move the rectangle.
     *
     * @param v What is added to {@link #p}, {@link #q}, {@link #r}, {@link #s}.
     */
    @Override
    public void translate(V3D_VectorDouble v) {
        super.translate(v);
        this.pqr = null;
        this.rsp = null;
        convexHull = null;
    }

    @Override
    public V3D_RectangleDouble rotate(V3D_LineDouble axis, double theta,
            double epsilon) {
        return new V3D_RectangleDouble(
                getP().rotate(axis, theta, epsilon),
                getQ().rotate(axis, theta, epsilon),
                getR().rotate(axis, theta, epsilon),
                getS().rotate(axis, theta, epsilon));
    }

    /**
     * Calculate and return the intersection between {@code this} and {@code pl}
     *
     * @param pl The plane to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code pl}.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_PlaneDouble pl,
            double epsilon) {
        V3D_FiniteGeometryDouble pqri = getPQR().getIntersection(pl, epsilon);
        if (pqri == null) {
            return getRSP().getIntersection(pl, epsilon);
        } else {
            if (pqri instanceof V3D_TriangleDouble) {
                return this;
            }
            V3D_FiniteGeometryDouble rspi = getRSP().getIntersection(pl, epsilon);
            if (rspi == null) {
                return pqri;
            }
            return join(pqri, rspi, epsilon);
        }
    }

    /**
     * Computes and returns the intersection between {@code this} and {@code t}.
     * The intersection could be: null, a point, a line segment, a triangle, or
     * a V3D_ConvexHullCoplanar (with 4, 5, 6 or 7 sides).
     *
     * @param t The triangle intersect with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_TriangleDouble t,
            double epsilon) {
        // Test if all points of t are on the same side of this.
        if (this.getPlane(epsilon).allOnSameSideNotOn(t.getPoints(), epsilon)) {
            return null;
        } else {
            // Test if all points of this are on the same side of t.
            if (t.getPl(epsilon).allOnSameSideNotOn(getPoints(), epsilon)) {
                return null;
            }
        }
        V3D_FiniteGeometryDouble pqrit = getPQR().getIntersection(t, epsilon);
        V3D_FiniteGeometryDouble rspit = getRSP().getIntersection(t, epsilon);
        if (pqrit == null) {
            return rspit;
        } else {
            if (rspit == null) {
                return pqrit;
            }
            if (pqrit instanceof V3D_PointDouble) {
                return rspit;
            } else if (pqrit instanceof V3D_LineSegmentDouble t1il) {
                if (rspit instanceof V3D_PointDouble) {
                    return pqrit;
                } else if (rspit instanceof V3D_LineSegmentDouble t2il) {
                    return V3D_LineSegmentsCollinearDouble.getGeometry(t1il, 
                            t2il, epsilon);
                } else {
                    return rspit;
                }
            } else {
                if (rspit instanceof V3D_TriangleDouble t2it) {
                    return new V3D_ConvexHullCoplanarDouble(epsilon,
                            (V3D_TriangleDouble) pqrit, t2it).simplify(epsilon);
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_RayDouble r,
            double epsilon) {
        V3D_FiniteGeometryDouble gpqr = getPQR().getIntersection(r, epsilon);
        V3D_FiniteGeometryDouble grsp = getRSP().getIntersection(r, epsilon);
        if (gpqr == null) {
            return grsp;
        } else {
            if (grsp == null) {
                return gpqr;
            }
            return join(gpqr, grsp, epsilon);
        }
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V3D_LineDouble l, double epsilon) {
        return Math.sqrt(getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistanceSquared(V3D_LineDouble l, double epsilon) {
        return Math.min(
                getRSP().getDistanceSquared(l, epsilon),
                getPQR().getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V3D_LineSegmentDouble l, double epsilon) {
        return Math.sqrt(getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistanceSquared(V3D_LineSegmentDouble l, double epsilon) {
        return Math.min(
                getRSP().getDistanceSquared(l, epsilon),
                getPQR().getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance to {@code pl}.
     *
     * @param pl A plane.
     * @return The minimum distance squared to {@code p}.
     */
    public double getDistance(V3D_PlaneDouble pl) {
        return Math.sqrt(getDistanceSquared(pl));
    }

    /**
     * Get the minimum distance squared to {@code pl}.
     *
     * @param pl A plane.
     * @return The minimum distance squared to {@code p}.
     */
    public double getDistanceSquared(V3D_PlaneDouble pl) {
        return Math.min(
                getRSP().getDistanceSquared(pl),
                getPQR().getDistanceSquared(pl));
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A triangle.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code t}.
     */
    public double getDistance(V3D_TriangleDouble t, double epsilon) {
        return Math.sqrt(getDistanceSquared(t, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A triangle.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code t}.
     */
    public double getDistanceSquared(V3D_TriangleDouble t, double epsilon) {
        return Math.min(
                getRSP().getDistanceSquared(t, epsilon),
                getPQR().getDistanceSquared(t, epsilon));
    }

    /**
     * @param r The rectangle to test if it is equal to this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is equal to r.
     */
    public boolean equals(V3D_RectangleDouble r, double epsilon) {
        V3D_PointDouble[] pts = getPoints();
        V3D_PointDouble[] rpts = r.getPoints();
        for (var x : pts) {
            boolean found = false;
            for (var y : rpts) {
                if (x.equals(y, epsilon)) {
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
                if (x.equals(y, epsilon)) {
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@link #convexHull} initialising it if it is {@code null}.
     */
    public V3D_ConvexHullCoplanarDouble getConvexHull(double epsilon) {
        if (convexHull == null) {
            convexHull = new V3D_ConvexHullCoplanarDouble(epsilon, getPQR(),
                    getRSP());
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff pl, qv, r and s form a rectangle.
     */
    public static boolean isRectangle(V3D_PointDouble p, V3D_PointDouble q,
            V3D_PointDouble r, V3D_PointDouble s, double epsilon) {
        V3D_LineSegmentDouble pq = new V3D_LineSegmentDouble(p, q);
        V3D_LineSegmentDouble qr = new V3D_LineSegmentDouble(q, r);
        V3D_LineSegmentDouble rs = new V3D_LineSegmentDouble(r, s);
        V3D_LineSegmentDouble sp = new V3D_LineSegmentDouble(s, p);
        if (pq.l.isParallel(rs.l, epsilon)) {
            if (qr.l.isParallel(sp.l, epsilon)) {
                return true;
            }
        }
        return false;
    }
}
