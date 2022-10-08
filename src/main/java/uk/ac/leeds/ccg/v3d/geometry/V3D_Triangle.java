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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
 *       -n  -n  -n  -n  -n  c  n  n  n  n  n  normal heading out from the page.
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
public class V3D_Triangle extends V3D_FiniteGeometry implements V3D_Face {

    private static final long serialVersionUID = 1L;

    public V3D_Plane p;

//    /**
//     * For storing the line segment from {@link #p} to {@link #q}.
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
        super(t.e);
        p = t.p;
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
        super(e);
        this.p = new V3D_Plane(e, V3D_Vector.ZERO, p, q, r);
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
        super(e);
        this.p = new V3D_Plane(e, offset, p, q, r);
    }

    /**
     * Creates a new triangle.
     *
     * @param l A line segment representing one of the three edges of the
     * triangle.
     * @param r Defines the other point relative to l.offset that defines the
     * triangle.
     */
    public V3D_Triangle(V3D_LineSegment l, V3D_Vector r) {
        super(l.e);
        this.p = new V3D_Plane(l.e, l.offset, l.l.p, l.l.q, r);
    }

    /**
     * Creates a new triangle.
     *
     * @param l A line segment representing one of the three edges of the
     * triangle.
     * @param r Defines the other point relative to l.offset that defines the
     * triangle.
     */
    public V3D_Triangle(V3D_LineSegment l, V3D_Point r, int oom, RoundingMode rm) {
        super(l.e);
        this.p = new V3D_Plane(l.getP(), l.getQ(oom, rm), r, oom, rm);
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
            V3D_LineSegment lrp, int oom, RoundingMode rm) {
        super(lpq.e);
        this.p = new V3D_Plane(e, V3D_Vector.ZERO,
                lpq.getP().getVector(oom, rm),
                lpq.getQ(oom, rm).getVector(oom, rm),
                lqr.getQ(oom, rm).getVector(oom, rm));
    }

    /**
     * Creates a new instance.
     *
     * @param p Used to initialise {@link #e}, {@link #offset} and {@link #p}.
     * @param q Used to initialise {@link #q}.
     * @param r Used to initialise {@link #r}.
     */
    public V3D_Triangle(V3D_Point p, V3D_Point q, V3D_Point r, int oom, RoundingMode rm) {
        super(p.e);
        this.p = new V3D_Plane(p, q, r, oom, rm);
    }

    @Override
    public V3D_Envelope getEnvelope(int oom, RoundingMode rm) {
        if (en == null) {
            en = new V3D_Envelope(e, oom, rm, p.getP(), p.getQ(), p.getR());
        }
        return en;
    }

    @Override
    public V3D_Point[] getPoints(int oom, RoundingMode rm) {
        V3D_Point[] re = new V3D_Point[3];
        re[0] = p.getP();
        re[1] = p.getQ();
        re[2] = p.getR();
        return re;
    }

//    /**
//     * @param v The vector to translate.
//     * @param oom The Order of Magnitude for the precision of the calculation.
//     * @return a new rectangle.
//     */
//    @Override
//    public V3D_Triangle translate(V3D_Vector v, int oom) {
//        return new V3D_Triangle(getP(oom).translate(v, oom), 
//                getQ(oom).translate(v, oom), getR(oom).translate(v, oom), oom);
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
     * @return A point or line segment.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom, RoundingMode rm) {
        if (getEnvelope(oom, rm).isIntersectedBy(pt, oom, rm)) {
            if (p.isIntersectedBy(pt, oom, rm)) {
                return isIntersectedBy0(pt, oom, rm);
            }
        }
        return false;
    }

    /**
     * @param pt The point.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} if this intersects with {@code pt}.
     */
    protected boolean isIntersectedBy0(V3D_Point pt, int oom, RoundingMode rm) {
        if (p.getPQ().isIntersectedBy(pt, oom, rm) || p.getQR().isIntersectedBy(pt, oom, rm)
                || p.getRP().isIntersectedBy(pt, oom, rm)) {
            return true;
        }
        V3D_Vector ppt = new V3D_Vector(p.getP(), pt, oom, rm);
        V3D_Vector qpt = new V3D_Vector(p.getQ(), pt, oom, rm);
        V3D_Vector rpt = new V3D_Vector(p.getR(), pt, oom, rm);
        V3D_Vector cp = p.getPQV(oom, rm).getCrossProduct(ppt, oom, rm);
        V3D_Vector cq = p.getQRV(oom, rm).getCrossProduct(qpt, oom, rm);
        V3D_Vector cr = p.getRPV(oom, rm).getCrossProduct(rpt, oom, rm);
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
     * A point aligns with this if the planes given by each triangle edge given
     * by the normal find the point to the same side as the other point of the
     * triangle. The normal may be imprecisely calculated, so the calculated
     * points on the planes and the definition of the planes may be imprecise.
     * Greater precision can be gained using a smaller oom.
     *
     * @param p The point to check if it is in alignment.
     * @param oom The order of magnitude for the precision.
     * @return {@code true} iff p is aligned with this.
     */
    public boolean isAligned(V3D_Point p, int oom, RoundingMode rm) {
        V3D_Vector n = this.p.getN(oom, rm);
        V3D_Plane lp = new V3D_Plane(e, oom, rm, offset, this.p.p, this.p.q,
                this.p.p.add(n, oom, rm), false);
        if (lp.isOnSameSide(p, this.p.getR(), oom, rm)) {
            V3D_Plane lq = new V3D_Plane(e, oom, rm, offset, this.p.q, this.p.r,
                    this.p.q.add(n, oom, rm), false);
            if (lq.isOnSameSide(p, this.p.getP(), oom, rm)) {
                V3D_Plane lr = new V3D_Plane(e, oom, rm, offset, this.p.r, this.p.p,
                        this.p.r.add(n, oom, rm), false);
                if (lr.isOnSameSide(p, this.p.getQ(), oom, rm)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param l The line to test for intersection with this.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} If this and {@code l} intersect.
     */
    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom, RoundingMode rm) {
        if (p.isIntersectedBy(l, oom, rm)) {
            V3D_Geometry g = p.getIntersection(l, oom, rm);
            if (g instanceof V3D_Point pt) {
                if (isIntersectedBy(pt, oom, rm)) {
                    return true;
                    /**
                     * Logically, if the point of the line plane intersection is
                     * on the triangle then it intersects. However, the point is
                     * only given accurately to the given precision, so testing
                     * if the point is on the triangle does not work. The
                     * commented out else clause and this comment is in the code
                     * on purpose.
                     */
//                } else {
//                    return false; // This seems logical, but does not work for all cases in practice.
                }
                /**
                 * An option is to use the direction of the line vector to
                 * define planes for each edge of the triangle and test that the
                 * point of intersection is on the same side of these planes to
                 * the other point of the triangle in all cases. This option is
                 * commented out as it is thought this is slower than the option
                 * given below. Some timed tests should be done to measure which
                 * is faster...
                 */
//                V3D_Point pp = p.getP();
//                V3D_Point pq = p.getQ();
//                V3D_Point pr = p.getR();
//                V3D_Point qp = new V3D_Point(e, pq.offset, p.getQV().add(l.v, oom));
//                V3D_Plane a = new V3D_Plane(pp, pq, qp);
//                V3D_Point rp = new V3D_Point(e, pr.offset, p.getRV().add(l.v, oom));
//                V3D_Plane b = new V3D_Plane(pq, pr, rp);
//                V3D_Point ppt = new V3D_Point(e, p.offset, p.getPV().add(l.v, oom));
//                V3D_Plane c = new V3D_Plane(pr, ppt, rp);
//                if (a.isOnSameSide(pt, rp, oom)){
//                    if (b.isOnSameSide(pt, pp, oom)) {
//                        if (c.isOnSameSide(pt, pq, oom)) {
//                            return true;
//                        }
//                    }
//                }
//                return false;
                /**
                 * Another option is to use:
                 * https://en.wikipedia.org/wiki/M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm
                 * It is thought this is faster than the option commented out
                 * above. Some timed tests should be done to measure which is
                 * faster...
                 */
                V3D_Vector edge1 = p.getPQV(oom, rm);
                V3D_Vector edge2 = p.getRPV(oom, rm).reverse();
                V3D_Vector h = l.getV(oom, rm).getCrossProduct(edge2, oom, rm);

                if (edge1.getDotProduct(h, oom, rm).compareTo(Math_BigRational.ZERO) == 0) {
                    System.out.println("edge1.getDotProduct(h, oom, rm) is zero in isIntersectedBy");
                    System.out.println("Triangle:");
                    System.out.println(this.toString());
                    System.out.println("Line:");
                    System.out.println(l.toString());
                    return false;
                }

                Math_BigRational f = Math_BigRational.ONE.divide(
                        edge1.getDotProduct(h, oom, rm));
                V3D_Vector s = l.p.subtract(p.getPV(), oom, rm);
                Math_BigRational u = f.multiply(s.getDotProduct(h, oom, rm));
                if (u.compareTo(Math_BigRational.ZERO) == -1
                        || u.compareTo(Math_BigRational.ONE) == 1) {
                    return false;
                }
                V3D_Vector q = s.getCrossProduct(edge1, oom, rm);
                Math_BigRational v = f.multiply(l.getV(oom, rm).getDotProduct(q, oom, rm));
                if (v.compareTo(Math_BigRational.ZERO) == -1
                        || u.add(v).compareTo(Math_BigRational.ONE) == 1) {
                    return false;
                }
                Math_BigRational t
                        = f.multiply(edge2.getDotProduct(q, oom, rm));
                return t.compareTo(Math_BigRational.ZERO) == 1;
            } else {
                if (p.getPQ().isIntersectedBy(l, oom, rm)) {
                    return true;
                }
                if (p.getQR().isIntersectedBy(l, oom, rm)) {
                    return true;
                }
                /**
                 * The following statement is not necessary, but the commented
                 * out code is left here for clarity.
                 */
                // if (getRP().isIntersectedBy(l, oom)) {
                //      return true;
                // }
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (getEnvelope(oom, rm).isIntersectedBy(l.getEnvelope(oom, rm), oom, rm)) {
            if (p.isIntersectedBy(l, oom, rm)) {
                V3D_FiniteGeometry g
                        = p.getIntersection(l, oom, rm);
                if (g == null) {
                    return false;
                } else {
                    if (g instanceof V3D_Point pt) {
                        return isIntersectedBy(pt, oom, rm);
                    } else {
                        return l.isIntersectedBy((V3D_LineSegment) g, oom, rm);
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
    public boolean isIntersectedBy(V3D_Triangle t, int oom, RoundingMode rm) {
        if (getEnvelope(oom, rm).isIntersectedBy(t.getEnvelope(oom, rm), oom, rm)) {
            V3D_Plane plt = t.p;
            if (isIntersectedBy(plt, oom, rm)) {
                V3D_Plane pl = p;
                V3D_FiniteGeometry g = pl.getIntersection(t, oom, rm);
                if (g == null) {
                    return false;
                } else {
                    if (g instanceof V3D_Point pt) {
                        return t.isIntersectedBy(pt, oom, rm);
                    } else if (g instanceof V3D_LineSegment l) {
                        return t.isIntersectedBy(l, oom, rm);
                    } else {
                        /**
                         * Check for a line that goes between the triangles. For
                         * each side of one triangle. Compute the dot product of
                         * the corners of the other triangle with the normal of
                         * the side. Test that all these have the same side.
                         */
                        V3D_Vector nn = p.getN(oom, rm);
                        if (checkSide(t, nn, p.getPQV(oom, rm), oom, rm)) {
                            return true;
                        }
                        if (checkSide(t, nn, p.getQRV(oom, rm), oom, rm)) {
                            return true;
                        }
                        return checkSide(t, nn, p.getRPV(oom, rm), oom, rm);
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
    protected boolean checkSide(V3D_Triangle t, V3D_Vector n, V3D_Vector v,
            int oom, RoundingMode rm) {
        V3D_Vector cp = n.getCrossProduct(v, oom, rm);
        int cpv = v.getDotProduct(cp, oom, rm).compareTo(Math_BigRational.ZERO);
        if (cpv == 0) {
            return true;
        } else if (cpv == 1) {
            int cqv = t.p.getQV().getDotProduct(cp, oom, rm).compareTo(Math_BigRational.ZERO);
            if (cqv == 0) {
                return true;
            } else {
                if (cqv == 1) {
                    int crv = t.p.getRV().getDotProduct(cp, oom, rm).compareTo(Math_BigRational.ZERO);
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
            int cqv = t.p.getQV().getDotProduct(cp, oom, rm).compareTo(Math_BigRational.ZERO);
            if (cqv == 0) {
                return true;
            } else {
                if (cqv == 1) {
                    return true;
                } else {
                    int crv = t.p.getRV().getDotProduct(cp, oom, rm).compareTo(Math_BigRational.ZERO);
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
     * For checking if {@code t} intersects {@code this}.
     *
     * @param t The triangle to test for intersection with this.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff the geometry is intersected by {@code l}.
     */
    //@Override
    public boolean isIntersectedBy(V3D_ConvexHullCoplanar ch, int oom, RoundingMode rm) {
        if (getEnvelope(oom, rm).isIntersectedBy(ch.getEnvelope(oom, rm), oom, rm)) {
            for (var t : ch.triangles) {
                if (isIntersectedBy(t, oom, rm)) {
                    return true;
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
    public BigDecimal getArea(int oom, RoundingMode rm) {
        return p.getPQV(oom, rm).getCrossProduct(p.getRPV(oom, rm).reverse(), oom, rm)
                .getMagnitude().divide(Math_BigRational.TWO, oom, rm).toBigDecimal(oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     */
    @Override
    public BigDecimal getPerimeter(int oom, RoundingMode rm) {
        int oomN1 = oom - 1;
        return Math_BigDecimal.round(
                p.getPQ().getLength(oom, rm).toBigDecimal(oomN1, rm)
                        .add(p.getQR().getLength(oom, rm).toBigDecimal(oomN1, rm))
                        .add(p.getRP().getLength(oom, rm).toBigDecimal(oomN1, rm)),
                oom, rm);
    }

    /**
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return A point or line segment.
     */
    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Line l, int oom,
            RoundingMode rm) {
        V3D_Geometry i = p.getIntersection(l, oom, rm);
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
            V3D_FiniteGeometry lpqi = p.getPQ().getIntersection(l, oom, rm);
            V3D_FiniteGeometry lqri = p.getQR().getIntersection(l, oom, rm);
            V3D_FiniteGeometry lrpi = p.getRP().getIntersection(l, oom, rm);
            if (lpqi == null) {
                if (lqri == null) {
                    return null; // This should not happen!
                } else {
                    if (lrpi == null) {
                        return lqri;
                    } else {
                        return getGeometry(e, ((V3D_Point) lqri).getVector(oom, rm),
                                ((V3D_Point) lrpi).getVector(oom, rm));
                    }
                }
            } else if (lpqi instanceof V3D_Point lpqip) {
                if (lqri == null) {
                    if (lrpi == null) {
                        return lpqi;
                    } else {
                        return getGeometry(e, lpqip.getVector(oom, rm),
                                ((V3D_Point) lrpi).getVector(oom, rm));
                    }
                } else if (lqri instanceof V3D_Point lqrip) {
                    if (lrpi == null) {
                        return getGeometry(e, lqrip.getVector(oom, rm),
                                lpqip.getVector(oom, rm));
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
     * @param e The environment
     * @param v1 A vector.
     * @param v2 A vector.
     * @return Either a line segment or a point.
     */
    public static V3D_FiniteGeometry getGeometry(V3D_Environment e,
            V3D_Vector v1, V3D_Vector v2) {
        if (v1.equals(v2)) {
            return new V3D_Point(e, v1);
        } else {
            return new V3D_LineSegment(e, v1, v2);
        }
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment l, int oom,
            RoundingMode rm) {
        V3D_Point lp = l.getP();
        V3D_Point lq = l.getQ(oom, rm);
//        boolean lip = isIntersectedBy(l.p, oom);
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
//                    return l.p;
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
     * Calculate and return the intersection between {@code this} and {@code p}.
     * A question about how to do this:
     * https://stackoverflow.com/questions/3142469/determining-the-intersection-of-a-triangle-and-a-plane
     *
     * @param pl The plane to intersect with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code p}.
     */
    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Plane pl, int oom,
            RoundingMode rm) {
        // Get intersection if this were a plane
        //V3D_Geometry pi = pl.getIntersection(this, oom);
        V3D_Geometry pi = pl.getIntersection(p, oom, rm);
        if (pi == null) {
            return null;
        } else if (pi instanceof V3D_Plane) {
            return this;
        } else {
            // (pi instanceof V3D_Line)
            V3D_FiniteGeometry gPQ = pl.getIntersection(p.getPQ(), oom, rm);
            if (gPQ == null) {
                V3D_FiniteGeometry gQR = pl.getIntersection(p.getQR(), oom, rm);
                if (gQR == null) {
                    V3D_FiniteGeometry gRP = pl.getIntersection(p.getRP(), oom, rm);
                    if (gRP == null) {
                        return null;
                    } else {
                        return gRP;
                    }
                } else {
                    V3D_Geometry gRP = pl.getIntersection(p.getRP(), oom, rm);
                    if (gRP == null) {
                        return gQR;
                    } else {
                        return V3D_LineSegment.getGeometry((V3D_Point) gQR,
                                (V3D_Point) gRP, oom, rm);
                    }
                }
            } else if (gPQ instanceof V3D_LineSegment) {
                return gPQ;
            } else {
                V3D_FiniteGeometry gQR = pl.getIntersection(p.getQR(), oom, rm);
                if (gQR == null) {
                    V3D_FiniteGeometry gRP = pl.getIntersection(p.getRP(), oom, rm);
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
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Triangle t, int oom,
            RoundingMode rm) {
        if (getEnvelope(oom, rm).isIntersectedBy(t.getEnvelope(oom, rm), oom, rm)) {
            if (isIntersectedBy(p, oom, rm)) {
                V3D_FiniteGeometry g = p.getIntersection(t, oom, rm);
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
                         * The two triangles are in the same plane Get
                         * intersections between the triangle edges. If there
                         * are none, then return t. If there are some, then in
                         * some cases the result is a single triangle, and in
                         * others it is a polygon which can be represented as a
                         * set of coplanar triangles.
                         */
                        // Check if vertices intersect
                        boolean pi = isIntersectedBy(t.p.getP(), oom, rm);
                        boolean qi = isIntersectedBy(t.p.getQ(), oom, rm);
                        boolean ri = isIntersectedBy(t.p.getR(), oom, rm);
                        if (pi && qi && ri) {
                            return t;
                        }
                        boolean pit = t.isIntersectedBy(p.getP(), oom, rm);
                        boolean qit = t.isIntersectedBy(p.getQ(), oom, rm);
                        boolean rit = t.isIntersectedBy(p.getR(), oom, rm);
                        if (pit && qit && rit) {
                            return this;
                        }
                        V3D_FiniteGeometry gpq = t.getIntersection(p.getPQ(), oom, rm);
                        V3D_FiniteGeometry gqr = t.getIntersection(p.getQR(), oom, rm);
                        V3D_FiniteGeometry grp = t.getIntersection(p.getRP(), oom, rm);
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
                                            ls.getQ(oom, rm), oom, rm);
                                }
                            } else {
                                if (grp == null) {
                                    return gqr;
                                } else if (grp instanceof V3D_Point grpp) {
                                    V3D_LineSegment ls = (V3D_LineSegment) gqr;
                                    return getGeometry(grpp, ls.getP(),
                                            ls.getQ(oom, rm), oom, rm);
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
                                            ls.getQ(oom, rm), oom, rm);
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
                                            gpql.getQ(oom, rm), oom, rm);
                                } else {
                                    return getGeometry(gpql,
                                            (V3D_LineSegment) grp, oom, rm);
                                }
                            } else if (gqr instanceof V3D_Point gqrp) {
                                if (grp == null) {
                                    if (gqr.isIntersectedBy(gpql, oom, rm)) {
                                        return gpql;
                                    } else {
                                        return new V3D_ConvexHullCoplanar(oom,
                                                rm, p.getN(oom, rm), gpql.getP(),
                                                gpql.getQ(oom, rm), gqrp);
                                    }
                                } else if (grp instanceof V3D_Point grpp) {
                                    return new V3D_ConvexHullCoplanar(oom,
                                            rm, p.getN(oom, rm), gpql.getP(),
                                            gpql.getQ(oom, rm), gqrp, grpp);
                                } else {
                                    V3D_LineSegment grpl = (V3D_LineSegment) grp;
                                    return new V3D_ConvexHullCoplanar(oom,
                                            rm, p.getN(oom, rm), gpql.getP(),
                                            gpql.getQ(oom, rm), gqrp, grpl.getP(),
                                            grpl.getQ(oom, rm));
                                }
                            } else {
                                V3D_LineSegment gqrl = (V3D_LineSegment) gqr;
                                if (grp == null) {
                                    return new V3D_ConvexHullCoplanar(oom,
                                            rm, p.getN(oom, rm), gpql.getP(),
                                            gpql.getQ(oom, rm), gqrl.getP(),
                                            gqrl.getQ(oom, rm));
                                } else if (grp instanceof V3D_Point grpp) {
                                    return new V3D_ConvexHullCoplanar(oom,
                                            rm, p.getN(oom, rm), gpql.getP(),
                                            gpql.getQ(oom, rm), gqrl.getP(),
                                            gqrl.getQ(oom, rm), grpp);
                                } else {
                                    V3D_LineSegment grpl = (V3D_LineSegment) grp;
                                    return new V3D_ConvexHullCoplanar(oom,
                                            rm, p.getN(oom, rm), gpql.getP(),
                                            gpql.getQ(oom, rm), gqrl.getP(),
                                            gqrl.getQ(oom, rm), grpl.getP(),
                                            grpl.getQ(oom, rm));
                                }
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
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The centroid point.
     */
    public V3D_Point getCentroid(int oom, RoundingMode rm) {
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
//        return new V3D_Point(e, offset, getPV().add(getQV(), oom)
//                .add(getRV(), oom).divide(Math_BigRational.valueOf(3), oom));
        oom -= 6;
        Math_BigRational dx = (p.p.getDX(oom, rm).add(p.q.getDX(oom, rm))
                .add(p.r.getDX(oom, rm))).divide(3).round(oom, rm);
        Math_BigRational dy = (p.p.getDY(oom, rm).add(p.q.getDY(oom, rm))
                .add(p.r.getDY(oom, rm))).divide(3).round(oom, rm);
        Math_BigRational dz = (p.p.getDZ(oom, rm).add(p.q.getDZ(oom, rm))
                .add(p.r.getDZ(oom, rm))).divide(3).round(oom, rm);
        return new V3D_Point(e, offset, new V3D_Vector(dx, dy, dz));
    }

    /**
     * Test if two triangles are equal. Two triangles are equal if they have 3
     * coincident points, so even if the order is different and one is clockwise
     * and the other anticlockwise.
     *
     * @param t The other triangle to test for equality.
     * @return {@code true} iff {@code this} is equal to {@code t}.
     */
    public boolean equals(V3D_Triangle t, int oom, RoundingMode rm) {
        V3D_Point tp = t.p.getP();
        V3D_Point thisp = p.getP();
        if (tp.equals(thisp, oom, rm)) {
            V3D_Point tq = t.p.getQ();
            V3D_Point thisq = p.getQ();
            if (tq.equals(thisq, oom, rm)) {
                return t.p.getR().equals(p.getR(), oom, rm);
            } else if (tq.equals(p.getR(), oom, rm)) {
                return t.p.getR().equals(thisq, oom, rm);
            } else {
                return false;
            }
        } else if (tp.equals(p.getQ(), oom, rm)) {
            V3D_Point tq = t.p.getQ();
            V3D_Point thisr = p.getR();
            if (tq.equals(thisr, oom, rm)) {
                return t.p.getR().equals(thisp, oom, rm);
            } else if (tq.equals(thisp, oom, rm)) {
                return t.p.getR().equals(thisr, oom, rm);
            } else {
                return false;
            }
        } else if (tp.equals(p.getR(), oom, rm)) {
            V3D_Point tq = t.p.getQ();
            if (tq.equals(thisp, oom, rm)) {
                return t.p.getR().equals(p.getQ(), oom, rm);
            } else if (tq.equals(p.getQ(), oom, rm)) {
                return t.p.getR().equals(thisp, oom, rm);
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
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        p.translate(v, oom, rm);
        en = null;
    }

    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta,
            int oom, RoundingMode rm) {
        p.rotate(axisOfRotation, theta, oom, rm);
        en = null;
    }

    public String toString(String pad) {
        return this.getClass().getSimpleName() + "(" + p.toString(pad) + ")";
    }

    public String toStringSimple(String pad) {
        return this.getClass().getSimpleName() + "(" + p.toStringSimple("") + ")";
    }

    @Override
    public String toString() {
        //return toString("");
        return toStringSimple("");
    }

//    @Override
//    public String toString() {
//        return this.getClass().getSimpleName() + "(" + p.toString() + ")";
//    }
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
                    if (V3D_Line.isCollinear(p.e, oom, rm, p, q, r)) {
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
//                    return new V3D_Triangle(p.e, V3D_Vector.ZERO,
//                            p.getVector(p.e.oom),
//                            q.getVector(p.e.oom), r.getVector(p.e.oom));
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
     * @return either {@code p} or {@code new V3D_LineSegment(p, q)} or
     * {@code new V3D_Triangle(p, q, r)}
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_LineSegment l1,
            V3D_LineSegment l2, V3D_LineSegment l3, int oom, RoundingMode rm) {
        V3D_Point l1p = l1.getP();
        V3D_Point l1q = l1.getQ(oom, rm);
        V3D_Point l2p = l2.getP();
        V3D_Point l2q = l2.getQ(oom, rm);
        V3D_Point l3p = l3.getP();
        V3D_Point l3q = l3.getQ(oom, rm);
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
            return new V3D_ConvexHullCoplanar(oom, rm, pl.getN(oom, rm), pts);
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
     * @return either {@code p} or {@code new V3D_LineSegment(p, q)} or
     * {@code new V3D_Triangle(p, q, r)}
     */
    protected static V3D_FiniteGeometry getGeometry2(V3D_LineSegment l1,
            V3D_LineSegment l2, int oom, RoundingMode rm) {
        V3D_Point l1p = l1.getP();
        V3D_Point l1q = l1.getQ(oom, rm);
        V3D_Point l2p = l2.getP();
        V3D_Point l2q = l2.getQ(oom, rm);
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
            return new V3D_ConvexHullCoplanar(oom, rm, pl.getN(oom, rm), pts);
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
    protected static V3D_FiniteGeometry getGeometry(V3D_LineSegment l1,
            V3D_LineSegment l2, int oom, RoundingMode rm) {
        V3D_Point pt = (V3D_Point) l1.getIntersection(l2, oom, rm);
        V3D_Point l1p = l1.getP();
        V3D_Point l2p = l2.getP();
        if (l1p.equals(pt, oom, rm)) {
            if (l2p.equals(pt, oom, rm)) {
                return new V3D_Triangle(pt, l1.getQ(oom, rm), l2.getQ(oom, rm), oom, rm);
            } else {
                return new V3D_Triangle(pt, l1.getQ(oom, rm), l2p, oom, rm);
            }
        } else {
            if (l2p.equals(pt, oom, rm)) {
                return new V3D_Triangle(pt, l1p, l2.getQ(oom, rm), oom, rm);
            } else {
                return new V3D_Triangle(pt, l1p, l2p, oom, rm);
            }
        }
    }

    /**
     * This may be called when there is an intersection of two triangles where l
     * is a side of a triangle and p is a point.
     *
     * @param l A line segment.
     * @param p1 A point that is either not collinear to l or intersects l.
     * @param p2 A point that is either not collinear to l or intersects l.
     * @return a triangle for which l is an edge and p is a vertex.
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_LineSegment l,
            V3D_Point p1, V3D_Point p2, int oom, RoundingMode rm) {
        if (l.isIntersectedBy(p1, oom, rm)) {
            return getGeometry(l, p2, oom, rm);
        } else {
            return new V3D_Triangle(p1, l.getP(), l.getQ(oom, rm), oom, rm);
        }
    }

    /**
     * This may be called when there is an intersection of two triangles where l
     * is a side of a triangle and p is a point that is not collinear to l.
     *
     * @param l A line segment.
     * @param p A point that is not collinear to l.
     * @return a triangle for which l is an edge and p is a vertex.
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_LineSegment l,
            V3D_Point p, int oom, RoundingMode rm) {
        if (l.isIntersectedBy(p, oom, rm)) {
            return l;
        }
        return new V3D_Triangle(p, l.getP(), l.getQ(oom, rm), oom, rm);
    }

    /**
     * For getting the point opposite a side of a triangle given the side.
     *
     * @param l a line segment either equal to {@link #getPQ()},
     * {@link #getQR()} or {@link #getRP()}.
     * @return The point of {@code this} that does not intersect with {@code l}.
     */
    public V3D_Point getOpposite(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (p.getPQ().equals(l, oom, rm)) {
            return p.getR();
        } else {
            if (p.getQR().equals(l, oom, rm)) {
                return p.getP();
            } else {
                return p.getQ();
            }
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Plane pl, int oom, RoundingMode rm) {
        if (p.isIntersectedBy(pl, oom, rm)) {
            V3D_Geometry g = p.getIntersection(pl, oom, rm);

            if (g == null) { // Hack.
                return false;
            }

            if (g instanceof V3D_Line l) {
                return isIntersectedBy(l, oom, rm);
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom,
            RoundingMode rm) {
        if (p.isIntersectedBy(t, oom, rm)) {
            /**
             * If this is inside t then they intersect. First implement
             * V3D_Tetrahedron.isIntersectedBy(V3D_Point)
             */
            if (t.isIntersectedBy(p.getP(), oom, rm)) {
                return true;
            }
            if (t.isIntersectedBy(p.getQ(), oom, rm)) {
                return true;
            }
            if (t.isIntersectedBy(p.getR(), oom, rm)) {
                return true;
            }
            /**
             * If this intersects any of the faces of t, then they intersect.
             */
            if (this.isIntersectedBy(t.getPqr(), oom, rm)) {
                return true;
            }
            if (this.isIntersectedBy(t.getQsr(), oom, rm)) {
                return true;
            }
            if (this.isIntersectedBy(t.getSpr(), oom, rm)) {
                return true;
            }
            if (this.isIntersectedBy(t.getPsq(), oom, rm)) {
                return true;
            }
            // Otherwise there is no intersection.
        }
        return false;
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        return t.getIntersection(this, oom, rm);
    }

    @Override
    public BigDecimal getDistance(V3D_Point p, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Point pt, int oom, RoundingMode rm) {
        V3D_Point pt2 = new V3D_Point(pt);
        pt2.setOffset(offset, oom, rm);
        if (V3D_Plane.isCoplanar(e, oom, rm, p, pt)) {
            if (isIntersectedBy(pt2, oom, rm)) {
                return Math_BigRational.ZERO;
            } else {
                Math_BigRational pqd2 = p.getPQ().getDistanceSquared(pt, oom, rm);
                Math_BigRational qrd2 = p.getQR().getDistanceSquared(pt, oom, rm);
                Math_BigRational rpd2 = p.getRP().getDistanceSquared(pt, oom, rm);
                return Math_BigRational.min(pqd2, qrd2, rpd2);
            }
        }
        V3D_Point poi = p.getPointOfProjectedIntersection(pt, oom, rm);
        if (poi == null) {
            return pt.getDistanceSquared(p, oom, rm);
        }
        Math_BigRational poid2 = poi.getDistanceSquared(pt2, oom, rm);
        if (getEnvelope(oom, rm).isIntersectedBy(poi, oom, rm)) {
            if (isIntersectedBy(poi, oom, rm)) {
                return poid2;
            }
        }
        Math_BigRational pqd2 = p.getPQ().getDistanceSquared(pt, oom, rm);
        Math_BigRational qrd2 = p.getQR().getDistanceSquared(pt, oom, rm);
        Math_BigRational rpd2 = p.getRP().getDistanceSquared(pt, oom, rm);
        if (pqd2.compareTo(qrd2) == -1) {
            return rpd2.min(pqd2);
        } else {
            return rpd2.min(qrd2);
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom, RoundingMode rm) {
        Math_BigRational dpq2 = p.getPQ().getDistanceSquared(l, oom, rm);
        Math_BigRational dqr2 = p.getQR().getDistanceSquared(l, oom, rm);
        Math_BigRational drp2 = p.getRP().getDistanceSquared(l, oom, rm);
        return Math_BigRational.min(dpq2, dqr2, drp2);
    }

    @Override
    public BigDecimal getDistance(V3D_Ray r, int oom, RoundingMode rm) {
        return r.getDistance(this, oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Ray r, int oom, RoundingMode rm) {
        return r.getDistanceSquared(this, oom, rm);
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (isIntersectedBy(l, oom, rm)) {
            return Math_BigRational.ZERO;
        }
//        V3D_Line ll = new V3D_Line(l);
//        Math_BigRational dl2 = getDistanceSquared(new V3D_Line(ll), oom);
        Math_BigRational dlpq2 = l.getDistanceSquared(p.getPQ(), oom, rm);
        Math_BigRational dlqr2 = l.getDistanceSquared(p.getQR(), oom, rm);
        Math_BigRational dlrp2 = l.getDistanceSquared(p.getRP(), oom, rm);
        //Math_BigRational dlmin = Math_BigRational.min(dlpq2, dlqr2, dlrp2);
        Math_BigRational dtlp2 = getDistanceSquared(l.getP(), oom, rm);
        Math_BigRational dtlq2 = getDistanceSquared(l.getQ(oom, rm), oom, rm);
        //Math_BigRational dtmin = Math_BigRational.min(dtlp2, dtlq2);
        return Math_BigRational.min(dlpq2, dlqr2, dlrp2, dtlp2, dtlq2);
    }

    @Override
    public BigDecimal getDistance(V3D_Plane pl, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(pl, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Plane pl, int oom, RoundingMode rm) {
        Math_BigRational dplpq2 = pl.getDistanceSquared(p.getPQ(), oom, rm);
        Math_BigRational dplqr2 = pl.getDistanceSquared(p.getQR(), oom, rm);
        Math_BigRational dplrp2 = pl.getDistanceSquared(p.getRP(), oom, rm);
        return Math_BigRational.min(dplpq2, dplqr2, dplrp2);
    }

    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(t, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom, RoundingMode rm) {
        if (isIntersectedBy(t, oom, rm)) {
            return Math_BigRational.ZERO;
        }
        Math_BigRational dtpq2 = t.getDistanceSquared(p.getPQ(), oom, rm);
        Math_BigRational dtqr2 = t.getDistanceSquared(p.getQR(), oom, rm);
        Math_BigRational dtrp2 = t.getDistanceSquared(p.getRP(), oom, rm);
        Math_BigRational dpq2 = getDistanceSquared(t.p.getPQ(), oom, rm);
        Math_BigRational dqr2 = getDistanceSquared(t.p.getQR(), oom, rm);
        Math_BigRational drp2 = getDistanceSquared(t.p.getRP(), oom, rm);
        return Math_BigRational.min(dtpq2, dtqr2, dtrp2, dpq2, dqr2, drp2);
    }

    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(t, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        if (isIntersectedBy(t, oom, rm)) {
            return Math_BigRational.ZERO;
        }
        Math_BigRational drpqr = getDistanceSquared(t.getPqr(), oom, rm);
        Math_BigRational drpsq = getDistanceSquared(t.getPsq(), oom, rm);
        Math_BigRational drqsr = getDistanceSquared(t.getQsr(), oom, rm);
        Math_BigRational drspr = getDistanceSquared(t.getSpr(), oom, rm);
        return Math_BigRational.min(drpqr, drpsq, drqsr, drspr);
    }

    /**
     * For retrieving a Set of points that are the corners of the triangles.
     *
     * @param triangles The input.
     * @return A Set of points that are the corners of the triangles.
     */
    //public static ArrayList<V3D_Point> getPoints(V3D_Triangle[] triangles) {
    public static V3D_Point[] getPoints(V3D_Triangle[] triangles, int oom, RoundingMode rm) {
        List<V3D_Point> s = new ArrayList<>();
        for (var t : triangles) {
            s.add(t.p.getP());
            s.add(t.p.getQ());
            s.add(t.p.getR());
        }
        ArrayList<V3D_Point> points = V3D_Point.getUnique(s, oom, rm);
        return points.toArray(V3D_Point[]::new);
    }

    @Override
    public boolean isIntersectedBy(V3D_Ray r, int oom, RoundingMode rm) {
        return r.isIntersectedBy(this, oom, rm);
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Ray r, int oom, RoundingMode rm) {
        return r.getIntersection(this, oom, rm);
    }

    /**
     * Clips this using pl and returns the part that is on the same side as pt.
     *
     * @param pl The plane that clips.
     * @param pt A point that is used to return the side of the clipped
     * triangle.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometry clip(V3D_Plane pl, V3D_Point pt, int oom, RoundingMode rm) {
        V3D_FiniteGeometry i = getIntersection(pl, oom, rm);
        V3D_Point pp = this.p.getP();
        if (i == null) {
            if (pl.isOnSameSide(pp, pt, oom, rm)) {
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
            if (pl.isOnSameSide(pp, pt, oom, rm)) {
                poll++;
            }
            if (pl.isOnSameSide(p.getQ(), pt, oom, rm)) {
                poll++;
            }
            if (pl.isOnSameSide(p.getR(), pt, oom, rm)) {
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
            V3D_Point pq = p.getQ();
            V3D_Point pr = p.getR();
            if (pl.isOnSameSide(pp, pt, oom, rm)) {
                if (pl.isOnSameSide(pq, pt, oom, rm)) {
                    if (pl.isOnSameSide(pr, pt, oom, rm)) {
                        return this;
                    } else {
                        return getGeometry(il, p.getPQ(), oom, rm);
                    }
                } else {
                    if (pl.isOnSameSide(pr, pt, oom, rm)) {
                        return getGeometry(il, p.getRP(), oom, rm);
                    } else {
                        return getGeometry(il, pp, oom, rm);
                    }
                }
            } else {
                if (pl.isOnSameSide(pq, pt, oom, rm)) {
                    if (pl.isOnSameSide(pr, pt, oom, rm)) {
                        return getGeometry(il, p.getPQ(), oom, rm);
                    } else {
                        return getGeometry(il, pq, oom, rm);
                    }
                } else {
                    if (pl.isOnSameSide(pr, pt, oom, rm)) {
                        return getGeometry(il, pr, oom, rm);
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
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometry clip(V3D_Triangle t, V3D_Point pt, int oom, RoundingMode rm) {
        V3D_Point tp = t.p.getP();
        V3D_Point tq = t.p.getQ();
        V3D_Point tr = t.p.getR();
        V3D_Vector n = t.p.getN(oom, rm);
        V3D_Point pp = new V3D_Point(e, tp.offset.add(n, oom, rm), tp.rel);
        V3D_Plane ppl = new V3D_Plane(tp, tq, pp, oom, rm);
        V3D_Point qp = new V3D_Point(e, tq.offset.add(n, oom, rm), tq.rel);
        V3D_Plane qpl = new V3D_Plane(tq, tr, qp, oom, rm);
        V3D_Point rp = new V3D_Point(e, tr.offset.add(n, oom, rm), tr.rel);
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
