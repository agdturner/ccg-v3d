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
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.geometrics.V3D_Geometrics;

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
     * @param b To distinguish this method from
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Plane, int)}.
     * @return {@code true} iff the geometry is intersected by {@code l}.
     */
    public boolean isIntersectedBy(V3D_Triangle t, int oom, boolean b) {
        if (getEnvelope().isIntersectedBy(t.getEnvelope())) {
            if (isIntersectedBy(t, oom)) {
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
     * @param p The plane to intersect with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code p}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Plane p, int oom) {
        // Get intersection it this were a plane
        V3D_Geometry pi = p.getIntersection(this, oom);
        if (pi == null) {
            return null;
        }
        if (pi instanceof V3D_Plane) {
            return this;
        } else {
            // (pi instanceof V3D_Line)
            V3D_Geometry gPQ = p.getIntersection(getPQ(), oom, true);
            if (gPQ == null) {
                V3D_Geometry gQR = p.getIntersection(getQR(), oom, true);
                if (gQR == null) {
                    V3D_Geometry gRP = p.getIntersection(this.getRP(), oom, true);
                    if (gRP == null) {
                        return null;
                    } else {
                        return getGeometry((V3D_Point) gPQ,
                                (V3D_Point) gQR, (V3D_Point) gRP);
                    }
                } else {
                    V3D_Geometry gRP = p.getIntersection(getRP(), oom, true);
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
                V3D_Geometry gQR = p.getIntersection(getQR(), oom, true);
                if (gQR == null) {
                    V3D_Geometry gRP = p.getIntersection(getRP(), oom, true);
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
     * @param b To distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Plane, int)}.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V3D_Geometry getIntersection(V3D_Triangle t, int oom, boolean b) {
        if (getEnvelope().isIntersectedBy(t.getEnvelope())) {
            if (isIntersectedBy(t, oom)) {
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
            } else if (tq.equals(thisp)) {
                return tr.equals(thisq);
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
     * This may be called when there is an intersection of two triangles.
     * If l1, l2 and l3 are equal then the line segment is returned. If there
     * are 3 unique points then a triangle is returned. If there are 4 or more
     * unique points, then contiguous coplanar triangles are returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param l3 A line segment.
     * @return either {@code p} or {@code new V3D_LineSegment(p, q)} or
     * {@code new V3D_Triangle(p, q, r)}
     */
    protected V3D_Geometry getGeometry(V3D_LineSegment l1, V3D_LineSegment l2,
            V3D_LineSegment l3) {
        V3D_Environment e = l1.e;
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
        Iterator<V3D_Point> ite = points.iterator();
        int n = points.size();
        if (n == 2) {
            return l1;
        } else if (n == 3) {
            return getGeometry(ite.next(), ite.next(), ite.next());
        } else if (n == 4) {
            // Case: closed polygon with 4 sides
            V3D_Triangle t1;
            V3D_Triangle t2;
//            if (l2.isIntersectedBy(l1p, e.oom)) {
//                t1 = new V3D_Triangle(l2p, l2q, l1q);
//                if (l3.isIntersectedBy(l2p, e.oom)) {
//                    t2 = new V3D_Triangle(l3p, l3q, l1q);
//                    // May need to do something else to be sure this is a closed polygon before returning!
//                } else {
//                    if (l3.isIntersectedBy(l2q, e.oom)) {
//                        t2 = new V3D_Triangle(l3p, l3q, l1p);
//                    } else {
//                        if ()
//                    }
//                }                
//            } else {
//                if (l2.isIntersectedBy(l1q, e.oom)) {
//                    V3D_Triangle t1 = new V3D_Triangle(l3p, l3q, l1p);
//                    V3D_Triangle t2 = new V3D_Triangle(l2p, l2q, l1p);
//                    return new V3D_TrianglesCoplanar(t1, t2);
//                } else {
//                    if (l3.isIntersectedBy(l1p, e.oom)) {
//                        V3D_Triangle t1 = new V3D_Triangle(l3p, l3q, l1q);
//                        if () {
//                            
//                        } else {
//                            
//                        }
//                        V3D_Triangle t2 = new V3D_Triangle(l2p, l2q, l1q);
//                        return new V3D_TrianglesCoplanar(t1, t2);
//                    } else {
//                        if (l3.isIntersectedBy(l1q, e.oom)) {
//                            V3D_Triangle t1 = new V3D_Triangle(l3p, l3q, l1p);
//                            V3D_Triangle t2 = new V3D_Triangle(l2p, l2q, l1p);
//                            return new V3D_TrianglesCoplanar(t1, t2);
//                        } else {
//
//                        }
//                    }
//                }
//            }
//            return new V3D_TrianglesCoplanar(t1, t2);
            throw new UnsupportedOperationException("Not supported yet.");
        } else if (n == 5) {
            throw new UnsupportedOperationException("Not supported yet.");
        } else {
            // n = 6
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    /**
     * This may be called when there is an intersection of two triangles.
     * If l1 and l2 are two sides of a triangle, return the triangle.
     *
     * @param l1 A line segment and triangle edge.
     * @param l2 A line segment and triangle edge.
     * @return a triangle for which l1 and l2 are edges
     */
    protected V3D_Geometry getGeometry(V3D_LineSegment l1, V3D_LineSegment l2) {
        V3D_Point p = (V3D_Point) l1.getIntersection(l2, l1.e.oom, true);
        V3D_Point l1p = l1.getP(l1.e.oom);
        V3D_Point l2p = l2.getP(l1.e.oom);
        if (l1p.equals(p)) {
            if (l2p.equals(p)) {
                return new V3D_Triangle(p, l1.getQ(l1.e.oom), l2.getQ(l1.e.oom));
            } else {
                return new V3D_Triangle(p, l1.getQ(l1.e.oom), l2p);
            }
        } else {
            if (l2p.equals(p)) {
                return new V3D_Triangle(p, l1p, l2.getQ(l1.e.oom));
            } else {
                return new V3D_Triangle(p, l1p, l2p);
            }
        }
    }
}
