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
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * For representing and processing rectangles in 3D. A rectangle is a right
 * angled quadrilateral. The four corners are the points
 * {@link #p}, {@link #q}, {@link #r} and {@link #s}. The following depicts a
 * rectangle {@code
 *          qr
 *  q *-------------* r
 *    |             |
 * pq |             | rs
 *    |             |
 *  p *-------------* s
 *          sp
 * }
 * The angles PQR, QRS, RSP, SPQ are all 90 degrees or Pi/2 radians.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Rectangle extends V3D_Triangle implements V3D_Face {

    private static final long serialVersionUID = 1L;

//    /**
//     * For storing if q is at the origin.
//     */
//    protected final boolean qAtOrigin2;
    /**
     * The other corner of the rectangle. The others are {@link #p}, {@link #q},
     * and {@link #r}.
     */
    protected V3D_Vector s;

    /**
     * For storing the other triangle that makes up the rectangle.
     */
    protected V3D_Triangle rsp;

    /**
     * @return {@link #rsp} initialising it first if it is {@code null}
     */
    public V3D_Triangle getRSP() {
        if (rsp == null) {
            rsp = new V3D_Triangle(e, r, s, p);
        }
        return rsp;
    }

//    /**
//     * Initialised when the q provided to initialise this is at the origin and p
//     * and q have been swapped to define the plane. So, this represents the
//     * vector from the point p input (which is now stored as {@link #q}) to
//     * {@link #s}.
//     */
//    protected final V3D_Vector qs;
//    /**
//     * For storing the vector from {@link #p} to {@link #q}.
//     */
//    protected V3D_LineSegment l;
//
//    /**
//     * For storing the line segment from {@link #q} to {@link #r}.
//     */
//    protected V3D_LineSegment t;
//
//    /**
//     * For storing the line segment from {@link #r} to {@link #s}.
//     */
//    protected V3D_LineSegment ri;
//
//    /**
//     * For storing the line segment from {@link #s} to {@link #p}.
//     */
//    protected V3D_LineSegment b;
    /**
     * Create a new instance.
     *
     * @param e What {@link #e} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p The bottom left corner of the rectangle.
     * @param q The top left corner of the rectangle.
     * @param r The top right corner of the rectangle.
     * @param s The bottom right corner of the rectangle.
     * @throws java.lang.RuntimeException iff the points do not define a
     * rectangle.
     */
    public V3D_Rectangle(V3D_Environment e, V3D_Vector offset, V3D_Vector p,
            V3D_Vector q, V3D_Vector r, V3D_Vector s) {
        super(e, offset, p, q, r);
        /**
         * p and q get swapped if q is at the origin in defining the plane, in
         * which case {@link #pq} is now representing the reverse, and
         * {@link qr} represents the vector from p to r.
         */
        boolean qAtOrigin2 = getQ().equals(V3D_Point.ORIGIN);
        V3D_Vector qs;
        if (qAtOrigin2) {
            qs = s.subtract(p, e.oom);
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
                    if (!(tpq.isOrthogonal(qs, e.oom))) {
                        throw new RuntimeException("The points do not define a rectangle.");
                    }
                } else {
                    if (!(tpq.isOrthogonal(tqr, e.oom))) {
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
     * @throws java.lang.RuntimeException iff the points do not define a
     * rectangle.
     */
    public V3D_Rectangle(V3D_Envelope.Rectangle r) {
        this(r.e, V3D_Vector.ZERO, new V3D_Vector(r.p), new V3D_Vector(r.q),
                new V3D_Vector(r.r), new V3D_Vector(r.s));
    }

    /**
     * Creates a new instance
     *
     * @param p Used to initialise {@link #e}, {@link #offset} and {@link #p}.
     * @param q Used to initialise {@link #q}.
     * @param r Used to initialise {@link #r}.
     * @param s Used to initialise {@link #s}.
     */
    public V3D_Rectangle(V3D_Point p, V3D_Point q, V3D_Point r, V3D_Point s) {
        super(p, q, r);
        V3D_Point s2 = new V3D_Point(s);
        s2.setOffset(p.offset);
        this.s = s2.rel;
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
     * @return {@link #s}.
     */
    public V3D_Vector getSV() {
        return s;
    }

    /**
     * @return {@link #s} with {@link #offset} applied.
     */
    public V3D_Point getS() {
        return new V3D_Point(e, offset, getSV());
    }

    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
            en = new V3D_Envelope(e, getP(), getQ(), getR(), getS());
        }
        return en;
    }

    /**
     * @param pt The point to intersect with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return A point or line segment.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom, boolean b) {
        if (super.isIntersectedBy(pt, oom, b)) {
            return true;
        } else {
            return getRSP().isIntersectedBy(pt, oom, b);
        }
    }

    /**
     * @return The line segment from {@link #r} to {@link #s}.
     */
    protected V3D_LineSegment getRS() {
        //return new V3D_LineSegment(offset, rotate(r, theta), rotate(s, theta), oom);
        return new V3D_LineSegment(e, offset, getRV(), getSV());
    }

    /**
     * @return The line segment from {@link #s} to {@link #p}.
     */
    protected V3D_LineSegment getSP() {
        //return new V3D_LineSegment(offset, rotate(s, theta), rotate(p, theta), oom);
        return new V3D_LineSegment(e, offset, getSV(), getPV());
    }

    private boolean isIntersectedBy0(V3D_Line ls, int oom) {
        return getQR().isIntersectedBy(ls, oom) || getRS().isIntersectedBy(ls, oom)
                || getSP().isIntersectedBy(ls, oom) || getPQ().isIntersectedBy(ls, oom);
    }

    @Override
    protected boolean isIntersectedBy0(V3D_Point pt, int oom) {
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
            BigDecimal apqpt = new V3D_Triangle(e, tp.getVector(oom),
                    tq.getVector(oom), pt.getVector(oom)).getArea(oom);
            // Area qrpt
            BigDecimal aqrpt = new V3D_Triangle(e, tq.getVector(oom),
                    tr.getVector(oom), pt.getVector(oom)).getArea(oom);
            // Area rspt
            BigDecimal arspt = new V3D_Triangle(e, tr.getVector(oom),
                    ts.getVector(oom), pt.getVector(oom)).getArea(oom);
            // Area sppt
            BigDecimal asppt = new V3D_Triangle(e, ts.getVector(oom),
                    tp.getVector(oom), pt.getVector(oom)).getArea(oom);
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
        if (getEnvelope().isIntersectedBy(l.getEnvelope())) {
            V3D_Plane pl = new V3D_Plane(this);
            if (pl.isIntersectedBy(l, oom)) {
                V3D_Geometry g = pl.getIntersection(l, oom, b);
                if (g instanceof V3D_Point v3D_Point) {
                    return isIntersectedBy(v3D_Point, oom);
                } else {
                    return getIntersection((V3D_LineSegment) g, oom, b) != null;
                    //return isIntersectedBy((V3D_LineSegment) g, oom, b);
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
        V3D_Geometry i1 = super.getIntersection(l, oom);
        V3D_Triangle t2 = new V3D_Triangle(e, offset, p, r, s);
        V3D_Geometry i2 = t2.getIntersection(l, oom);
        if (i1 == null) {
            return i2;
        } else {
            if (i2 == null) {
                return i1;
            } else {
                return join(i1, i2);
            }
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
     * @return A point or line segment.
     */
    protected V3D_Geometry join(V3D_Geometry pointOrLineSegment1,
            V3D_Geometry pointOrLineSegment2) {
        if (pointOrLineSegment1 instanceof V3D_LineSegment l1) {
            if (pointOrLineSegment2 instanceof V3D_LineSegment l2) {
                if (l1.getP(e.oom).equals(l2.getP(e.oom))) {
                    return new V3D_LineSegment(e, offset, l1.q, l2.q);
                } else if (l1.getP(e.oom).equals(l2.getQ(e.oom))) {
                    return new V3D_LineSegment(e, offset, l1.q, l2.p);
                } else if (l1.getQ(e.oom).equals(l2.getP(e.oom))) {
                    return new V3D_LineSegment(e, offset, l1.p, l2.q);
                } else {
                    //if (l1.getQ(oom).equals(l2.getQ(oom))) {
                    return new V3D_LineSegment(e, offset, l1.p, l2.p);
                }
            } else {
                return l1;
            }
        } else {
            if (pointOrLineSegment2 instanceof V3D_LineSegment l2) {
                return l2;
            } else {
                return (V3D_Point) pointOrLineSegment1;
            }
        }
    }

    /**
     * Calculates and returns the intersections between {@code this} and
     * {@code l}.
     *
     * @param l The line segment to test for intersection.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @param b This is to distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Line, int)}. The
     * value is ignored.
     * @return The intersection or {@code null} iff there is no intersection.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom, boolean b) {
        V3D_Geometry i1 = super.getIntersection(l, oom, b);
        V3D_Triangle t2 = new V3D_Triangle(e, offset, p, r, s);
        V3D_Geometry i2 = t2.getIntersection(l, oom, b);
        if (i1 == null) {
            return i2;
        } else {
            if (i2 == null) {
                return i1;
            } else {
                return join(i1, i2);
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
    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
        return getEnvelope().isIntersectedBy(l, oom);
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
        return (new Math_BigRationalSqrt(getDistanceSquared(p, oom), oom))
                .getSqrt(oom).toBigDecimal(oom);
//        if (this.isIntersectedBy(p, oom, true)) {
//            return BigDecimal.ZERO;
//        }
//        BigDecimal dp = super.getDistance(p, oom);
//        BigDecimal ld = getPQ().getDistance(p, oom);
//        BigDecimal td = getQR().getDistance(p, oom);
//        BigDecimal rd = getRS().getDistance(p, oom);
//        BigDecimal bd = getSP().getDistance(p, oom);
//        if (dp.compareTo(ld) == 0 && dp.compareTo(td) == 0
//                && dp.compareTo(rd) == 0 && dp.compareTo(bd) == 0) {
//            return dp;
//        } else {
//            return Math_BigDecimal.min(ld, td, rd, bd);
//        }
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Point p, int oom) {
        Math_BigRational d1 = super.getDistanceSquared(p, oom);
        if (d1.compareTo(Math_BigRational.ZERO) == 0) {
            return d1;
        }
        Math_BigRational d2 = getRSP().getDistanceSquared(p, oom);
        return d1.min(d2);
    }

    /**
     * Change {@link #offset} without changing the overall line.
     *
     * @param offset What {@link #offset} is set to.
     */
    @Override
    public void setOffset(V3D_Vector offset) {
        super.setOffset(offset);
        s = s.add(offset, e.oom).subtract(this.offset, e.oom);
    }

    /**
     * Move the rectangle.
     *
     * @param v What is added to {@link #p}, {@link #q}, {@link #r}, {@link #s}.
     */
    @Override
    public void translate(V3D_Vector v) {
        super.translate(v);
        s = s.add(v, e.oom);
    }

    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta) {
        super.rotate(axisOfRotation, theta);
        s = s.rotate(axisOfRotation, theta, e.bI, e.oom);
    }

    @Override
    public boolean isIntersectedBy(V3D_Point p, int oom) {
        if (super.isIntersectedBy(p, oom)) {
            return true;
        } else {
            return getRSP().isIntersectedBy(p, oom);
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Plane p, int oom) {
        if (super.isIntersectedBy(p, oom)) {
            return true;
        } else {
            return getRSP().isIntersectedBy(p, oom);
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Triangle t, int oom) {
        if (super.isIntersectedBy(t, oom)) {
            return true;
        } else {
            return getRSP().isIntersectedBy(t, oom);
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom) {
        if (super.isIntersectedBy(t, oom)) {
            return true;
        } else {
            return getRSP().isIntersectedBy(t, oom);
        }
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Plane p, int oom) {
        V3D_Geometry t1i = super.getIntersection(p, oom);
        V3D_Geometry t2i = getRSP().getIntersection(p, oom);
        if (t1i == null) {
            return t2i;
        }
        if (t2i == null) {
            return t1i;
        }
        if (t1i instanceof V3D_Point) {
            return t2i;
        } else if (t1i instanceof V3D_LineSegment t1il) {
            if (t2i instanceof V3D_Point) {
                return t1i;
            } else {
                return V3D_LineSegmentsCollinear.getGeometry(t1il,
                        (V3D_LineSegment) t2i, oom);
            }
        } else {
            return this;
        }
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Triangle t, int oom) {
        V3D_Geometry t1i = super.getIntersection(t, oom);
        V3D_Geometry t2i = getRSP().getIntersection(t, oom);
        if (t1i == null) {
            return t2i;
        }
        if (t2i == null) {
            return t1i;
        }
        if (t1i instanceof V3D_Point) {
            return t2i;
        } else if (t1i instanceof V3D_LineSegment t1il) {
            if (t2i instanceof V3D_Point) {
                return t1i;
            } else if (t2i instanceof V3D_LineSegment t2il) {
                return V3D_LineSegmentsCollinear.getGeometry(t1il, t2il, oom);
            } else {
                return t2i;
            }
        } else {
            if (t2i instanceof V3D_Triangle t2it) {
                return V3D_TrianglesCoplanar.getGeometry((V3D_Triangle) t1i, t2it, oom);
            } else {
                return t1i;
            }
        }
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
