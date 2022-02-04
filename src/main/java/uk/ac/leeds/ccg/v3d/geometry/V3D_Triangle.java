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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

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
     * @param e What {@link #e} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     */
    public V3D_Triangle(V3D_Environment e, V3D_Vector p, V3D_Vector q,
            V3D_Vector r) {
        super(e, V3D_Vector.ZERO, p, q, r);
    }

    /**
     * Creates a new triangle.
     *
     * @param e What {@link #e} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     */
    public V3D_Triangle(V3D_Environment e, V3D_Vector offset, V3D_Vector p,
            V3D_Vector q, V3D_Vector r) {
        super(e, offset, p, q, r);
    }

    /**
     * Creates a new triangle.
     *
     * @param l A line segment representing one of the three edges of the
     * triangle.
     * @param r Defines the other point relative l.offset that defines the
     * triangle.
     */
    public V3D_Triangle(V3D_LineSegment l, V3D_Vector r) {
        super(l.e, l.offset, l.getP(), l.getQ(), r);
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
     */
    public V3D_Triangle(V3D_LineSegment lpq, V3D_LineSegment lqr,
            V3D_LineSegment lrp) {
        super(lpq.e, V3D_Vector.ZERO,
                lpq.getP(lpq.e.oom).getVector(lpq.e.oom),
                lpq.getQ(lpq.e.oom).getVector(lpq.e.oom),
                lqr.getQ(lpq.e.oom).getVector(lpq.e.oom));
    }

    /**
     * Creates a new instance.
     *
     * @param p Used to initialise {@link #e}, {@link #offset} and {@link #p}.
     * @param q Used to initialise {@link #q}.
     * @param r Used to initialise {@link #r}.
     */
    public V3D_Triangle(V3D_Point p, V3D_Point q, V3D_Point r) {
        super(p, q, r);
    }

    @Override
    public String toString() {
        return toString("");
    }

    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
            en = new V3D_Envelope(e, getP(), getQ(), getR());
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
    
//    /**
//     * 
//     * @param pt The point to intersect with.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @param b Distinguishes this from
//     * {@link V3D_Plane#isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int)}.
//     * @return A point or line segment.
//     */
//    public boolean isIntersectedBy(V3D_Point pt, int oom, boolean b) {
//        if (super.isIntersectedBy(pt, oom)) {
//            /**
//             * The point p has to be on the same side of the each edge as the
//             * other point in the triangle.
//             */
//            V3D_Point pt2 = new V3D_Point(pt2);
//            pt2.setOffset(offset);
//            V3D_Vector cp = getN(oom).getCrossProduct(pt2.rel, oom);
//            int cpv = pt2.rel.getDotProduct(cp, oom).compareTo(Math_BigRational.ZERO);
//            if (checkPoint(getPV(), cp, cpv, oom)) {
//                if (checkPoint(getQV(), cp, cpv, oom)) {
//                    if (checkPoint(getRV(), cp, cpv, oom)) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    protected boolean checkPoint(V3D_Vector v, V3D_Vector cp, int cpv, int oom) {
//        int cv = v.getDotProduct(cp, oom).compareTo(Math_BigRational.ZERO);
//        if (cv == 0) {
//            return true;
//        } else {
//            return cv == cpv;
//        }
//    }
    
    /**
     * @param pt The point to intersect with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @param b Distinguishes this from
     * {@link V3D_Plane#isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int)}.
     * @return A point or line segment.
     */
    public boolean isIntersectedBy(V3D_Point pt, int oom, boolean b) {
        if (getEnvelope().isIntersectedBy(pt, oom)) {
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
        if (getEnvelope().isIntersectedBy(l.getEnvelope())) {
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
     * For checking if {@code t} intersects {@code this}.
     *
     * @param t The triangle to test for intersection with this.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff the geometry is intersected by {@code l}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Triangle t, int oom) {
        boolean b = true;
        if (getEnvelope().isIntersectedBy(t.getEnvelope())) {
            if (isIntersectedBy(new V3D_Plane(t), oom)) {
                V3D_Geometry g = super.getIntersection(t, oom);
                if (g == null) {
                    return false;
                } else {
                    if (g instanceof V3D_Point pt) {
                        return t.isIntersectedBy(pt, oom);
                    } else if (g instanceof V3D_LineSegment l) {
                        return t.isIntersectedBy(l, oom, b);
                    } else {
                        /**
                         * Check for a line that goes between the triangles. For
                         * each side of one triangle. Compute the dot product of
                         * the corners of the other triangle with the normal of
                         * the side. Test that all these have the same side.
                         */
                        V3D_Vector nn = getN(oom);
                        if (checkSide(t, nn, getPQV(), oom)) {
                            return true;
                        }
                        if (checkSide(t, nn, getQRV(), oom)) {
                            return true;
                        }
                        return checkSide(t, nn, getRPV(), oom);
                    }
                }
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
     * @return {@code true} if an intersection is found and {@code false}
     * otherwise.
     */
    protected boolean checkSide(V3D_Triangle t, V3D_Vector n, V3D_Vector v, int oom) {
        V3D_Vector cp = n.getCrossProduct(v, oom);
        int cpv = v.getDotProduct(cp, oom).compareTo(Math_BigRational.ZERO);
        if (cpv == 0) {
            return true;
        } else if (cpv == 1) {
            int cqv = t.getQV().getDotProduct(cp, oom).compareTo(Math_BigRational.ZERO);
            if (cqv == 0) {
                return true;
            } else {
                if (cqv == 1) {
                    int crv = t.getRV().getDotProduct(cp, oom).compareTo(Math_BigRational.ZERO);
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
            int cqv = t.getQV().getDotProduct(cp, oom).compareTo(Math_BigRational.ZERO);
            if (cqv == 0) {
                return true;
            } else {
                if (cqv == 1) {
                    return true;
                } else {
                    int crv = t.getRV().getDotProduct(cp, oom).compareTo(Math_BigRational.ZERO);
                    if (crv == 0) {
                        return true;
                    } else {
                        return crv == 1;
                    }
                }
            }
        }
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
        if (!getEnvelope().isIntersectedBy(l, oom)) {
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
                    return getGeometry((V3D_Point) lpqi,
                            (V3D_Point) lqri, (V3D_Point) lrpi);
//                    return V3D_FiniteGeometry.getGeometry((V3D_Point) lpqi,
//                            (V3D_Point) lrpi, (V3D_Point) lrpi);
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
     * @return Either a line segment or a point.
     */
    public V3D_Geometry getGeometry(V3D_Vector v1, V3D_Vector v2) {
        if (v1.equals(v2)) {
            return new V3D_Point(e, v1);
        } else {
            return new V3D_LineSegment(e, v1, v2);
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
                    return ((V3D_LineSegment) li).getIntersection(l, oom, b);
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
                    return ((V3D_LineSegment) li).getIntersection(l, oom, b);
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

    /**
     * Calculate and return the intersection between {@code this} and {@code p}.
     * A question about how to do this:
     * https://stackoverflow.com/questions/3142469/determining-the-intersection-of-a-triangle-and-a-plane
     *
     * @param pl The plane to intersect with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code p}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Plane pl, int oom) {
        // Get intersection if this were a plane
        //V3D_Geometry pi = pl.getIntersection(this, oom);
        V3D_Geometry pi = pl.getIntersection(new V3D_Plane(this), oom);
        if (pi == null) {
            return null;
        }
        if (pi instanceof V3D_Plane) {
            return this;
        } else {
            // (pi instanceof V3D_Line)
            V3D_Geometry gPQ = pl.getIntersection(getPQ(), oom, true);
            if (gPQ == null) {
                V3D_Geometry gQR = pl.getIntersection(getQR(), oom, true);
                if (gQR == null) {
                    V3D_Geometry gRP = pl.getIntersection(this.getRP(), oom, true);
                    if (gRP == null) {
                        return null;
                    } else {
                        return getGeometry((V3D_Point) gPQ,
                                (V3D_Point) gQR, (V3D_Point) gRP);
                    }
                } else {
                    V3D_Geometry gRP = pl.getIntersection(getRP(), oom, true);
                    if (gRP == null) {
                        return gQR;
                    } else {
                        return V3D_LineSegment.getGeometry((V3D_Point) gQR,
                                (V3D_Point) gRP);
                    }
                }
            } else if (gPQ instanceof V3D_LineSegment) {
                return gPQ;
            } else {
                V3D_Geometry gQR = pl.getIntersection(getQR(), oom, true);
                if (gQR == null) {
                    V3D_Geometry gRP = pl.getIntersection(getRP(), oom, true);
                    if (gRP == null) {
                        return (V3D_Point) gPQ;
                    } else if (gRP instanceof V3D_LineSegment) {
                        return gRP;
                    } else {
                        return V3D_LineSegment.getGeometry((V3D_Point) gPQ,
                                (V3D_Point) gRP);
                    }
                } else {
                    return V3D_LineSegment.getGeometry((V3D_Point) gPQ,
                            (V3D_Point) gQR);
                }
            }
        }
    }

    /**
     * Computes and returns the intersection between {@code this} and {@code t}.
     *
     * @param t The triangle to test for intersection with this.
     * @param oom The Order of Magnitude for the precision.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Triangle t, int oom) {
        boolean b = true;
        if (getEnvelope().isIntersectedBy(t.getEnvelope())) {
            if (isIntersectedBy(new V3D_Plane(t), oom)) {
                V3D_Geometry g = super.getIntersection(this, oom);
                //V3D_Geometry g = this.getIntersection(t, oom);
                if (g == null) {
                    return g;
                } else {
                    if (g instanceof V3D_Point pt) {
                        if (t.isIntersectedBy(pt, oom)) {
                            return pt;
                        } else {
                            return null;
                        }
                    } else if (g instanceof V3D_LineSegment l) {
                        return t.getIntersection(l, oom, b);
                    } else {
                        /**
                         * The two triangles are in the same plane Get
                         * intersections between the triangle edges. If there
                         * are none, then return t. If there are some, then in
                         * some cases the result is a single triangle, and in
                         * others it is a polygon which can be represented as a
                         * set of coplanar triangles.
                         */
                        V3D_Geometry gpq = t.getIntersection(getPQ(), oom, b);
                        V3D_Geometry gqr = t.getIntersection(getQR(), oom, b);
                        V3D_Geometry grp = t.getIntersection(getRP(), oom, b);
                        if (gpq == null) {
                            if (gqr == null) {
                                if (grp == null) {
                                    // Return the smaller of the triangles
//                                    if (t.getArea(oom).compareTo(getArea(oom)) == 1) {
                                    if (t.getPerimeter(oom).compareTo(getPerimeter(oom)) == 1) {
                                        return this;
                                    } else {
                                        return t;
                                    }
                                } else {
                                    return grp;
                                }
                            } else if (gqr instanceof V3D_Point gqrp) {
                                if (grp == null) {
                                    return gqr;
                                } else if (grp instanceof V3D_Point grpp) {
                                    return V3D_LineSegment.getGeometry(
                                            gqrp, grpp);
                                } else {
                                    V3D_LineSegment ls = (V3D_LineSegment) grp;
                                    return getGeometry(
                                            gqrp, ls.getP(oom), ls.getQ(oom));
                                }
                            } else {
                                if (grp == null) {
                                    return gqr;
                                } else if (grp instanceof V3D_Point grpp) {
                                    V3D_LineSegment ls = (V3D_LineSegment) gqr;
                                    return getGeometry(
                                            grpp, ls.getP(oom), ls.getQ(oom));
                                } else {
                                    return getGeometry(
                                            (V3D_LineSegment) gqr, (V3D_LineSegment) grp);
                                }
                            }
                        } else if (gpq instanceof V3D_Point gpqp) {
                            if (gqr == null) {
                                if (grp == null) {
                                    return gpq;
                                } else if (grp instanceof V3D_Point grpp) {
                                    return V3D_LineSegment.getGeometry(
                                            gpqp, grpp);
                                } else {
                                    V3D_LineSegment ls = (V3D_LineSegment) grp;
                                    return getGeometry(
                                            gpqp, ls.getP(oom), ls.getQ(oom));
                                }
                            } else if (gqr instanceof V3D_Point gqrp) {
                                if (grp == null) {
                                    return gqr;
                                } else if (grp instanceof V3D_Point grpp) {
                                    return V3D_LineSegment.getGeometry(gqrp, grpp); // Check!
                                } else {
                                    throw new UnsupportedOperationException("Not supported yet."); // TODO: Figure out the geometry (two points and a line segment).
                                }
                            } else {
                                if (grp == null) {
                                    V3D_LineSegment ls = (V3D_LineSegment) gqr;
                                    return getGeometry(gpqp, ls.getP(oom), ls.getQ(oom));
                                } else if (grp instanceof V3D_Point grpp) {
                                    return null; // TODO: Figure out the geometry (two points and a line segment).
                                } else {
                                    return null; // TODO: Figure out the geometry (point and two line segments).
                                }
                            }
                        } else {
                            V3D_LineSegment ls = (V3D_LineSegment) gpq;
                            if (gqr == null) {
                                if (grp == null) {
                                    return gpq;
                                } else if (grp instanceof V3D_Point grpp) {
                                    return getGeometry(grpp, ls.getP(oom), ls.getQ(oom));
                                } else {
                                    return getGeometry(ls, (V3D_LineSegment) grp);
                                }
                            } else if (gqr instanceof V3D_Point gqrp) {
                                if (grp == null) {
                                    return getGeometry(gqrp, ls.getP(oom), ls.getQ(oom));
                                } else if (grp instanceof V3D_Point grpp) {
                                    throw new UnsupportedOperationException("Not supported yet."); // TODO: Figure out the geometry (two points and a line segment).
                                } else {
                                    throw new UnsupportedOperationException("Not supported yet."); // TODO: Figure out the geometry (point and two line segments).
                                }
                            } else {
                                if (grp == null) {
                                    throw new UnsupportedOperationException("Not supported yet."); // TODO: Figure out the geometry (two line segments).
                                } else if (grp instanceof V3D_Point grpp) {
                                    throw new UnsupportedOperationException("Not supported yet."); // TODO: Figure out the geometry (point and two line segments).
                                } else {
                                    return getGeometry((V3D_LineSegment) gpq, (V3D_LineSegment) gqr, (V3D_LineSegment) grp);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
        return getEnvelope().isIntersectedBy(l, oom);
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
        return new V3D_Point(e, getPV().add(getQV(), oom).add(getRV(), oom)
                .divide(Math_BigRational.valueOf(3), oom));
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) {
            if (o instanceof V3D_Triangle t) {
                return equals(t, true);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.en);
        return hash;
    }

    /**
     * Test if two triangles are equal. Two triangles are equal if they have 3
     * coincident points, so even if the order is different and one is clockwise
     * and the other anticlockwise.
     *
     * @param t The other triangle to test for equality.
     * @param b To distinguish this method from
     * {@link #equals(uk.ac.leeds.ccg.v3d.geometry.V3D_Plane)}. The value is
     * ignored.
     * @return {@code true} iff {@code this} is equal to {@code t}.
     */
    public boolean equals(V3D_Triangle t, boolean b) {
        V3D_Point tp = t.getP();
        V3D_Point thisp = getP();
        V3D_Point tq = t.getQ();
        V3D_Point thisq = getQ();
        V3D_Point tr = t.getR();
        V3D_Point thisr = getR();
        if (tp.equals(thisp)) {
            if (tq.equals(thisq)) {
                return tr.equals(thisr);
            } else if (tq.equals(thisr)) {
                return tr.equals(thisq);
            } else {
                return false;
            }
        } else if (tp.equals(thisq)) {
            if (tq.equals(thisr)) {
                return tr.equals(thisp);
            } else if (tq.equals(thisp)) {
                return tr.equals(thisr);
            } else {
                return false;
            }
        } else if (tp.equals(thisr)) {
            if (tq.equals(thisp)) {
                return tr.equals(thisq);
            } else if (tq.equals(thisq)) {
                return tr.equals(thisp);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    /**
     * Move the plane.
     *
     * @param v What is added to {@link #p}, {@link #q}, {@link #r}.
     */
    @Override
    public void translate(V3D_Vector v) {
        super.translate(v);
        en = null;
    }
    
    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta) {
        super.rotate(axisOfRotation, theta);
        en = null;
    }

    /**
     * If p, q and r are equal then the point is returned. If two of the points
     * are the same, then a line segment is returned. If all points are
     * different then a triangle is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @return either {@code p} or {@code new V3D_LineSegment(p, q)} or
     * {@code new V3D_Triangle(p, q, r)}
     */
    public static V3D_Geometry getGeometry(V3D_Point p, V3D_Point q, V3D_Point r) {
        if (p.equals(q)) {
            return V3D_LineSegment.getGeometry(p, r);
        } else {
            if (q.equals(r)) {
                return V3D_LineSegment.getGeometry(q, p);
            } else {
                if (r.equals(p)) {
                    return V3D_LineSegment.getGeometry(r, q);
                } else {
                    return new V3D_Triangle(p.e, V3D_Vector.ZERO,
                            p.getVector(p.e.oom),
                            q.getVector(p.e.oom), r.getVector(p.e.oom));
                }
            }
        }
    }

    /**
     * Used in intersecting two triangles to give the overall intersection. If
     * l1, l2 and l3 are equal then the line segment is returned. If there are 3
     * unique points then a triangle is returned. If there are 4 or more unique
     * points, then contiguous coplanar triangles are returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param l3 A line segment.
     * @return either {@code p} or {@code new V3D_LineSegment(p, q)} or
     * {@code new V3D_Triangle(p, q, r)}
     */
    protected V3D_Geometry getGeometry(V3D_LineSegment l1, V3D_LineSegment l2,
            V3D_LineSegment l3) {
        V3D_Point l1p = l1.getP(e.oom);
        V3D_Point l1q = l1.getQ(e.oom);
        V3D_Point l2p = l2.getP(e.oom);
        V3D_Point l2q = l2.getQ(e.oom);
        V3D_Point l3p = l3.getP(e.oom);
        V3D_Point l3q = l3.getQ(e.oom);
        HashSet<V3D_Point> points = new HashSet<>();
        points.add(l1p);
        points.add(l1q);
        points.add(l2p);
        points.add(l2q);
        points.add(l3p);
        points.add(l3q);
        int n = points.size();
        if (n == 2) {
            return l1;
        } else if (n == 3) {
            Iterator<V3D_Point> ite = points.iterator();
            return getGeometry(ite.next(), ite.next(), ite.next());
        } else if (n == 4) {
            V3D_Triangle t1;
            V3D_Triangle t2;
            // Case: closed polygon with 4 sides
            // Find the two unique points
            V3D_Point illl2 = (V3D_Point) l1.getIntersection(l2, e.oom, true);
            V3D_Point illl3 = (V3D_Point) l1.getIntersection(l3, e.oom, true);
            V3D_Point il2l3 = (V3D_Point) l2.getIntersection(l3, e.oom, true);
            if (illl2 == null) {
                V3D_Point op1 = l1.getOtherPoint(illl3);
                V3D_Point op2 = l2.getOtherPoint(il2l3);
                t1 = new V3D_Triangle(op1, op2, l3p);
                t2 = new V3D_Triangle(l3p, l3q, op2);
            } else {
                V3D_Point op1 = l1.getOtherPoint(illl2);
                V3D_Point op3;
                if (illl3 == null) {
                    op3 = l3.getOtherPoint(il2l3);
                    t1 = new V3D_Triangle(op1, op3, illl2);
                    t2 = new V3D_Triangle(l3p, l3q, illl2);
                } else {
                    op3 = l3.getOtherPoint(illl3);
                    t1 = new V3D_Triangle(op1, op3, illl2);
                    t2 = new V3D_Triangle(l3p, l3q, illl2);
                }
            }
            return new V3D_TrianglesCoplanar(t1, t2);
            //throw new UnsupportedOperationException("Not supported yet.");
        } else if (n == 5) {
            V3D_Triangle t1;
            V3D_Triangle t2;
            V3D_Triangle t3;
            // Case: closed polygon with 4 sides
            // Find the two unique points
            V3D_Point illl2 = (V3D_Point) l1.getIntersection(l2, e.oom, true);
            V3D_Point illl3 = (V3D_Point) l1.getIntersection(l3, e.oom, true);
            V3D_Point il2l3 = (V3D_Point) l2.getIntersection(l3, e.oom, true);
            // Find the two lines that intersect
            if (illl2 == null) {
                if (illl3 == null) {
                    // l2 and l3 intersect
                    V3D_Point op1 = l1.getOtherPoint(illl3);
                    V3D_Point op3 = l3.getOtherPoint(illl3);
                    t1 = new V3D_Triangle(op1, op3, illl3);
                    t2 = new V3D_Triangle(op1, op3, l2p); // This might be twisted?
                    t3 = new V3D_Triangle(op3, l2p, l2q);
                } else {
                    // l2 and l3 intersect
                    V3D_Point op2 = l2.getOtherPoint(il2l3);
                    V3D_Point op3 = l3.getOtherPoint(il2l3);
                    t1 = new V3D_Triangle(op2, op3, il2l3);
                    t2 = new V3D_Triangle(op2, op3, l1p); // This might be twisted?
                    t3 = new V3D_Triangle(op3, l1p, l1q);
                }
            } else {
                // l1 and l2 intersect
                V3D_Point op1 = l1.getOtherPoint(illl2);
                V3D_Point op2 = l2.getOtherPoint(illl2);
                t1 = new V3D_Triangle(op1, op2, illl2);
                t2 = new V3D_Triangle(op1, op2, l3p); // This might be twisted?
                t3 = new V3D_Triangle(op2, l3p, l3q);
            }
            return new V3D_TrianglesCoplanar(t1, t2, t3);
        } else {
            // n = 6
            V3D_Triangle t1;
            V3D_Triangle t2;
            V3D_Triangle t3;
            V3D_Triangle t4;
            /**
             * Find the two points that are the minimum distance between any two
             * lines. This will be an extra side to the triangle.
             */
            // dl1l2
            Math_BigRational dl1pl2p = l1p.getDistanceSquared(l2p, e.oom);
            Math_BigRational dl1pl2q = l1p.getDistanceSquared(l2q, e.oom);
            Math_BigRational dl1ql2p = l1q.getDistanceSquared(l2p, e.oom);
            Math_BigRational dl1ql2q = l1q.getDistanceSquared(l2q, e.oom);
            // dl1l3
            Math_BigRational dl1pl3p = l1p.getDistanceSquared(l3p, e.oom);
            Math_BigRational dl1pl3q = l1p.getDistanceSquared(l3q, e.oom);
            Math_BigRational dl1ql3p = l1q.getDistanceSquared(l3p, e.oom);
            Math_BigRational dl1ql3q = l1q.getDistanceSquared(l3q, e.oom);
//            // dl2l3
//            Math_BigRational dl2pl3p = l2p.getDistanceSquared(l3p, e.oom);
//            Math_BigRational dl2pl3q = l2p.getDistanceSquared(l3q, e.oom);
//            Math_BigRational dl2ql3p = l2q.getDistanceSquared(l3p, e.oom);
//            Math_BigRational dl2ql3q = l2q.getDistanceSquared(l3q, e.oom);
            if (dl1pl2p.compareTo(dl1pl2q) == -1) {
                if (dl1pl2p.compareTo(dl1ql2q) == -1) {
                    t1 = new V3D_Triangle(l1p, l1q, l2p);
                    if (dl1pl2p.compareTo(dl1ql2q) == -1) {
                        t2 = new V3D_Triangle(l3q, l2p, l2q);
                        if (dl1ql3p.compareTo(dl1ql3q) == -1) {
                            t3 = new V3D_Triangle(l3q, l2p, l1q);
                            t4 = new V3D_Triangle(l3q, l2p, l1q);
                        } else {
                            t3 = new V3D_Triangle(l3p, l2p, l1q);
                            t4 = new V3D_Triangle(l3p, l2p, l1q);
                        }
                    } else {
                        t2 = new V3D_Triangle(l3q, l2p, l2q);
                        if (dl1pl3p.compareTo(dl1pl3q) == -1) {
                            t3 = new V3D_Triangle(l3q, l2p, l1q);
                            t4 = new V3D_Triangle(l3q, l2p, l1q);
                        } else {
                            t3 = new V3D_Triangle(l3p, l2p, l1q);
                            t4 = new V3D_Triangle(l3q, l2p, l1q);
                        }
                    }
                } else {
                    t1 = new V3D_Triangle(l1p, l1q, l2p);
                    if (dl1pl2p.compareTo(dl1ql2q) == -1) {
                        t2 = new V3D_Triangle(l3q, l2p, l2q);
                        if (dl1ql3p.compareTo(dl1ql3q) == -1) {
                            t3 = new V3D_Triangle(l1p, l2p, l3q);
                            t4 = new V3D_Triangle(l3q, l2p, l1q);
                        } else {
                            t3 = new V3D_Triangle(l3p, l2p, l1q);
                            t4 = new V3D_Triangle(l3q, l2p, l1q);
                        }
                    } else {
                        t2 = new V3D_Triangle(l3q, l2p, l2q);
                        if (dl1pl3p.compareTo(dl1pl3q) == -1) {
                            t3 = new V3D_Triangle(l3q, l2p, l1q);
                            t4 = new V3D_Triangle(l3q, l2p, l1q);
                        } else {
                            t3 = new V3D_Triangle(l3p, l2p, l1q);
                            t4 = new V3D_Triangle(l3q, l2p, l1q);
                        }
                    }
                }
            } else {
                if (dl1pl2p.compareTo(dl1ql2q) == -1) {
                    t1 = new V3D_Triangle(l1p, l1q, l2p);
                    if (dl1pl2p.compareTo(dl1ql2q) == -1) {
                        t2 = new V3D_Triangle(l3q, l2p, l2q);
                        if (dl1ql3p.compareTo(dl1ql3q) == -1) {
                            t3 = new V3D_Triangle(l3q, l2p, l1q);
                            t4 = new V3D_Triangle(l3q, l2p, l1q);
                        } else {
                            t3 = new V3D_Triangle(l3p, l2p, l1q);
                            t4 = new V3D_Triangle(l3p, l2p, l1q);
                        }
                    } else {
                        t2 = new V3D_Triangle(l3q, l2p, l2q);
                        if (dl1pl3p.compareTo(dl1pl3q) == -1) {
                            t3 = new V3D_Triangle(l3q, l2p, l1q);
                            t4 = new V3D_Triangle(l3q, l2p, l1q);
                        } else {
                            t3 = new V3D_Triangle(l3p, l2p, l1q);
                            t4 = new V3D_Triangle(l3q, l2p, l1q);
                        }
                    }
                } else {
                    t1 = new V3D_Triangle(l1p, l1q, l2q);
                    if (dl1pl2p.compareTo(dl1ql2q) == -1) {
                        t2 = new V3D_Triangle(l3q, l2p, l2q);
                        if (dl1ql3p.compareTo(dl1ql3q) == -1) {
                            t3 = new V3D_Triangle(l1p, l2p, l3q);
                            t4 = new V3D_Triangle(l3q, l2p, l1q);
                        } else {
                            t3 = new V3D_Triangle(l3p, l2p, l1q);
                            t4 = new V3D_Triangle(l3q, l2p, l1q);
                        }
                    } else {
                        t2 = new V3D_Triangle(l3p, l2p, l2q);
                        if (dl1pl3p.compareTo(dl1pl3q) == -1) {
                            t3 = new V3D_Triangle(l3q, l2p, l1q);
                            t4 = new V3D_Triangle(l3q, l2p, l1q);
                        } else {
                            t3 = new V3D_Triangle(l3p, l3q, l1q);
                            t4 = new V3D_Triangle(l3p, l2p, l1q);
                        }
                    }
                }
            }
            return new V3D_TrianglesCoplanar(t1, t2, t3, t4);
        }
    }

    /**
     * This may be called when there is an intersection of two triangles. If l1
     * and l2 are two sides of a triangle, return the triangle.
     *
     * @param l1 A line segment and triangle edge.
     * @param l2 A line segment and triangle edge.
     * @return a triangle for which l1 and l2 are edges
     */
    protected V3D_Geometry getGeometry(V3D_LineSegment l1, V3D_LineSegment l2) {
        V3D_Point pt = (V3D_Point) l1.getIntersection(l2, l1.e.oom, true);
        V3D_Point l1p = l1.getP(l1.e.oom);
        V3D_Point l2p = l2.getP(l1.e.oom);
        if (l1p.equals(pt)) {
            if (l2p.equals(pt)) {
                return new V3D_Triangle(pt, l1.getQ(l1.e.oom), l2.getQ(l1.e.oom));
            } else {
                return new V3D_Triangle(pt, l1.getQ(l1.e.oom), l2p);
            }
        } else {
            if (l2p.equals(pt)) {
                return new V3D_Triangle(pt, l1p, l2.getQ(l1.e.oom));
            } else {
                return new V3D_Triangle(pt, l1p, l2p);
            }
        }
    }

    /**
     * @param l a line segment either equal to {@link #getPQ()},
     * {@link #getQR()} or {@link #getRP()}.
     * @return The point of {@code this} that does not intersect with {@code l}.
     */
    public V3D_Point getOpposite(V3D_LineSegment l) {
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

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        if (super.isIntersectedBy(l, oom)) {
            V3D_Plane pl = new V3D_Plane(this);
            V3D_Geometry g = pl.getIntersection(l, oom);
            if (g instanceof V3D_Point pt) {
                if (isIntersectedBy(pt, oom)) {
                    return true;
                }
            } else {
                if (getIntersection(l, oom) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_Plane pl, int oom) {
        if (super.isIntersectedBy(pl, oom)) {
            V3D_Plane tpl = new V3D_Plane(this);
            V3D_Geometry g = tpl.getIntersection(pl, oom);
            if (g instanceof V3D_Line l) {
                return isIntersectedBy(l, oom);
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom) {
        if (super.isIntersectedBy(t, oom)) {
            /**
             * If this is inside t then they intersect. First implement 
             * V3D_Tetrahedron.isIntersectedBy(V3D_Point)
            */
            /**
             * If this intersects any of the faces of t, then they intersect.
             */
            // Otherwise there is no intersection.
        }
        return false;
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Tetrahedron t, int oom) {
        V3D_Geometry pi = super.getIntersection(t, oom);
        if (pi != null) {
            
        }
        return pi;
    }

    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);      
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Point p, int oom) {
        V3D_Point pt = new V3D_Point(p);
        pt.setOffset(offset);
        V3D_Point poi = (new V3D_Plane(this)).getPointOfProjectedIntersection(p, oom);
        Math_BigRational poid2 = poi.getDistanceSquared(pt, oom);
        if (getEnvelope().isIntersectedBy(pt, oom)) {
            if (isIntersectedBy(poi, oom, true)) {
                return poid2;
            }
        }
        Math_BigRational pqd2 = getPQ().getDistanceSquared(p, oom);
        Math_BigRational qrd2 = getQR().getDistanceSquared(p, oom);
        Math_BigRational rpd2 = getRP().getDistanceSquared(p, oom);
        if (pqd2.compareTo(qrd2) == -1) {
            return rpd2.min(pqd2);
        } else {
            return rpd2.min(qrd2);            
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom) {
        Math_BigRational dpq2 = getPQ().getDistanceSquared(l, oom);
        Math_BigRational dqr2 = getQR().getDistanceSquared(l, oom);
        Math_BigRational drp2 = getRP().getDistanceSquared(l, oom);
        return Math_BigRational.min(dpq2, dqr2, drp2);        
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom) {
        // Figure out what the situation is.
        // Is one of the end points of l closest to a corner of this, a part of an edge of this, of some part of the face of this?
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
