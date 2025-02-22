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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.geometry.light.V3D_VTriangle;

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
 *            \  -n  -n  -n  c  +n  +n  +n  +n  normal going out of the page.
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
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Triangle extends V3D_FiniteGeometry implements V3D_Face {

    private static final long serialVersionUID = 1L;

    /**
     * The plane of the triangle
     */
    protected V3D_Plane pl;
    
    /**
     * Defines one of the corners of the triangle.
     */
    public V3D_Vector p;

    /**
     * Defines one of the corners of the triangle.
     */
    public V3D_Vector q;

    /**
     * Defines one of the corners of the triangle.
     */
    public V3D_Vector r;

    /**
     * The order of magnitude used for the calculation of {@link #pl}.
     */
    public int oom;

    /**
     * The RoundingMode used for the calculation of {@link #pl}.
     */
    public RoundingMode rm;

    /**
     * For storing the line segment from {@link #getP()} to {@link #getQ()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    private V3D_LineSegment pq;

    /**
     * The Order of Magnitude for the precision of {@link #pq}.
     */
    int pqoom;

    /**
     * The RoundingMode used for the calculation of {@link #pq}.
     */
    RoundingMode pqrm;

    /**
     * For storing the line segment from {@link #getQ()} to {@link #getR()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    private V3D_LineSegment qr;

    /**
     * The Order of Magnitude for the precision of {@link #qr}.
     */
    int qroom;

    /**
     * The RoundingMode used for the calculation of {@link #qr}.
     */
    RoundingMode qrrm;

    /**
     * For storing the line segment from {@link #getR()} to {@link #getP()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    private V3D_LineSegment rp;

    /**
     * The Order of Magnitude for the precision of {@link #rp}.
     */
    int rpoom;

    /**
     * The RoundingMode used for the calculation of {@link #rp}.
     */
    RoundingMode rprm;

    /**
     * For storing the plane aligning with {@link #pq} in the direction of the
     * plane normal and with a normal orthogonal to the plane normal.
     */
    private V3D_Plane pqpl;

    /**
     * For storing the plane aligning with {@link #qr} in the direction of the
     * plane normal and with a normal orthogonal to the plane normal.
     */
    private V3D_Plane qrpl;

    /**
     * For storing the plane aligning with {@link #rp} in the direction of the
     * plane normal and with a normal orthogonal to the plane normal.
     */
    private V3D_Plane rppl;

//    /**
//     * For storing the midpoint between {@link #getP()} and {@link #getQ()} at
//     * a given Order of Magnitude and RoundingMode precision.
//     */
//    private V3D_Point mpq;
//
//    /**
//     * For storing the midpoint between {@link #getQ()} and {@link #getR()} at
//     * a given Order of Magnitude and RoundingMode precision.
//     */
//    private V3D_Point mqr;
//
//    /**
//     * For storing the midpoint between {@link #getR()} and {@link #getP()} at
//     * a given Order of Magnitude and RoundingMode precision.
//     */
//    private V3D_Point mrp;
//
//    /**
//     * For storing the centroid at a specific Order of Magnitude and 
//     * RoundingMode precision.
//     */
//    private V3D_Point c;
    /**
     * Creates a new triangle.
     *
     * @param t The triangle to clone.
     */
    public V3D_Triangle(V3D_Triangle t) {
        super(new V3D_Vector(t.offset));
        pl = new V3D_Plane(t.pl);
        p = new V3D_Vector(t.p);
        q = new V3D_Vector(t.q);
        r = new V3D_Vector(t.r);
    }

    /**
     * Creates a new triangle.
     *
     * @param offset What {@link #offset} is set to.
     * @param t The triangle to initialise this from.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Triangle(V3D_Vector offset, V3D_VTriangle t, int oom,
            RoundingMode rm) {
        this(offset, new V3D_Vector(t.pq.p), new V3D_Vector(t.pq.q),
                new V3D_Vector(t.qr.q), oom, rm);
    }

    /**
     * Creates a new triangle. {@link #offset} is set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param p What {@link #pl} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Triangle(V3D_Vector p, V3D_Vector q, V3D_Vector r, int oom,
            RoundingMode rm) {
        this(V3D_Vector.ZERO, p, q, r, oom, rm);
    }

    /**
     * Creates a new triangle.
     *
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Triangle(V3D_Vector offset, V3D_Vector p, V3D_Vector q,
            V3D_Vector r, int oom, RoundingMode rm) {
        super(offset);
        this.oom = oom;
        this.rm = rm;
        this.p = p;
        this.q = q;
        this.r = r;
    }

    /**
     * Creates a new triangle.
     *
     * Warning p, q and r must all be different. No checks are done for
     * efficiency reasons.
     *
     * @param pl What {@link #pl} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     */
    public V3D_Triangle(V3D_Plane pl, V3D_Vector offset,
            V3D_Vector p, V3D_Vector q, V3D_Vector r) {
        super(offset);
        this.p = p;
        this.q = q;
        this.r = r;
        this.pl = pl;

        // Debugging code
        if (p.equals(q) || p.equals(r) || q.equals(r)) {
            int debug = 1;
            throw new RuntimeException("p.equals(q) || p.equals(r) || q.equals(r)");
        }
    }
    
//    /**
//     * Creates a new triangle.
//     *
//     * @param offset What {@link #offset} is set to.
//     * @param p What {@link #pl} is set to.
//     * @param q What {@link #q} is set to.
//     * @param r What {@link #r} is set to.
//     * @param normal What {@link #normal} is set to.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode if rounding is needed.
//     */
//    public V3D_Triangle(V3D_Vector offset, V3D_Vector p, V3D_Vector q, 
//            V3D_Vector r, V3D_Vector normal, int oom, RoundingMode rm) {
//        super(offset);
//        this.oom = oom;
//        this.rm = rm;
//        this.p = p;
//        this.q = q;
//        this.r = r;
//        this.normal = normal;
//    }
//    /**
//     * Creates a new triangle.
//     *
//     * @param l A line segment representing one of the three edges of the
//     * triangle.
//     * @param r Defines the other point relative to l.offset that defines the
//     * triangle.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode if rounding is needed.
//     */
//    public V3D_Triangle(V3D_LineSegment l, V3D_Vector r, int oom,
//            RoundingMode rm) {
//        this(new V3D_Vector(l.offset), l.l.pv, l.qv, r, oom, rm);
//    }
//    /**
//     * Creates a new triangle.
//     *
//     * @param l A line segment representing one of the three edges of the
//     * triangle.
//     * @param r Defines the other point relative to l.offset that defines the
//     * triangle.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode if rounding is needed.
//     */
//    public V3D_Triangle(V3D_LineSegment l, V3D_Point r, int oom,
//            RoundingMode rm) {
//        this(l.offset, l.l.pv, l.qv, r.getVector(oom, rm)
//                .subtract(l.offset, oom, rm), oom, rm);
//    }
    /**
     * Creates a new instance.
     *
     * @param p Used to initialise {@link #offset} and {@link #pl}.
     * @param q Used to initialise {@link #q}.
     * @param r Used to initialise {@link #r}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Triangle(V3D_Point p, V3D_Point q, V3D_Point r, int oom,
            RoundingMode rm) {
        super(new V3D_Vector(p.offset));
        this.p = new V3D_Vector(p.rel);
        this.q = q.getVector(oom, rm).subtract(p.offset, oom, rm);
        this.r = r.getVector(oom, rm).subtract(p.offset, oom, rm);
        this.oom = oom;
        this.rm = rm;
    }

    /**
     * Creates a new instance.
     *
     * @param pt A point giving the direction of the normal vector.
     * @param p Used to initialise {@link #offset} and {@link #pl}.
     * @param q Used to initialise {@link #q}.
     * @param r Used to initialise {@link #r}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Triangle(V3D_Point pt, V3D_Point p, V3D_Point q, V3D_Point r,
            int oom, RoundingMode rm) {
        super(new V3D_Vector(p.offset));
        this.p = new V3D_Vector(p.rel);
        this.q = q.getVector(oom, rm).subtract(p.offset, oom, rm);
        this.r = r.getVector(oom, rm).subtract(p.offset, oom, rm);
        this.oom = oom;
        this.rm = rm;
        this.pl = new V3D_Plane(pt, p.offset, p.getVector(oom, rm),
                q.getVector(oom, rm), r.getVector(oom, rm), oom, rm);
    }

    /**
     * Creates a new triangle.
     */
    public V3D_Triangle(V3D_LineSegment ls, V3D_Point pt, int oom, RoundingMode rm) {
        this(ls.getP(), ls.getQ(), pt, oom, rm);
    }
    
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@link pl} accurate to at least the oom precision using
     * RoundingMode rm.
     */
    public final V3D_Plane getPl(int oom, RoundingMode rm) {
        if (pl == null) {
            initPl(oom, rm);
        } else if (this.oom < oom) {
            return pl;
        } else if (this.oom == oom && this.rm.equals(rm)) {
            return pl;
        }
        initPl(oom, rm);
        return pl;
    }

    private void initPl(int oom, RoundingMode rm) {
        pl = new V3D_Plane(offset, p, q, r, oom, rm);
    }

    /**
     * @param pt The normal will point to this side of the plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@link pl} accurate to at least the oom precision using
     * RoundingMode rm.
     */
    public final V3D_Plane getPl(V3D_Point pt, int oom, RoundingMode rm) {
        if (pl == null) {
            initPl(pt, oom, rm);
        } else if (this.oom < oom) {
            return pl;
        } else if (this.oom == oom && this.rm.equals(rm)) {
            return pl;
        }
        initPl(pt, oom, rm);
        return pl;
    }

    private void initPl(V3D_Point pt, int oom, RoundingMode rm) {
        pl = new V3D_Plane(pt, offset, p, q, r, oom, rm);
    }

    /**
     * @return A new point based on {@link #p} and {@link #offset}.
     */
    public final V3D_Point getP() {
        return new V3D_Point(offset, p);
    }

    /**
     * @return A new point based on {@link #q} and {@link #offset}.
     */
    public final V3D_Point getQ() {
        return new V3D_Point(offset, q);
    }

    /**
     * @return A new point based on {@link #r} and {@link #offset}.
     */
    public final V3D_Point getR() {
        return new V3D_Point(offset, r);
    }

    /**
     * For getting the line segment from {@link #getP()} to {@link #getQ()} with
     * at least oom precision given rm.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Line segment from r to pv.
     */
    public final V3D_LineSegment getPQ(int oom, RoundingMode rm) {
        if (pq == null) {
            initPQ(oom, rm);
        } else {
            if (oom < pqoom) {
                initPQ(oom, rm);
            } else {
                if (!pqrm.equals(rm)) {
                    initPQ(oom, rm);
                }
            }
        }
        return pq;
    }

    private void initPQ(int oom, RoundingMode rm) {
        pq = new V3D_LineSegment(offset, p, q, oom, rm);
        pqoom = oom;
        pqrm = rm;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code qv.subtract(pv, oom, rm)}
     */
    public final V3D_Vector getPQV(int oom, RoundingMode rm) {
        return q.subtract(p, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code r.subtract(qv, oom, rm)}
     */
    public final V3D_Vector getQRV(int oom, RoundingMode rm) {
        return r.subtract(q, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code pv.subtract(r, oom, rm)}
     */
    public final V3D_Vector getRPV(int oom, RoundingMode rm) {
        return p.subtract(r, oom, rm);
    }

    /**
     * For getting the line segment from {@link #getQ()} to {@link #getR()} with
     * at least oom precision given rm.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Line segment from r to pv.
     */
    public final V3D_LineSegment getQR(int oom, RoundingMode rm) {
        if (qr == null) {
            initQR(oom, rm);
        } else {
            if (oom < qroom) {
                initQR(oom, rm);
            } else {
                if (!qrrm.equals(rm)) {
                    initQR(oom, rm);
                }
            }
        }
        return qr;
    }

    private void initQR(int oom, RoundingMode rm) {
        qr = new V3D_LineSegment(offset, q, r, oom, rm);
        qroom = oom;
        qrrm = rm;
    }

    /**
     * For getting the line segment from {@link #getR()} to {@link #getP()} with
     * at least oom precision given rm.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Line segment from r to pv.
     */
    public final V3D_LineSegment getRP(int oom, RoundingMode rm) {
        if (rp == null) {
            initRP(oom, rm);
        } else {
            if (oom < rpoom) {
                initRP(oom, rm);
            } else {
                if (!rprm.equals(rm)) {
                    initRP(oom, rm);
                }
            }
        }
        return rp;
    }

    private void initRP(int oom, RoundingMode rm) {
        rp = new V3D_LineSegment(offset, r, p, oom, rm);
        rpoom = oom;
        rprm = rm;
    }

    /**
     * For getting the plane through {@link #pq} in the direction of the normal.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The plane through {@link #pq} in the direction of the normal.
     */
    public V3D_Plane getPQPl(int oom, RoundingMode rm) {
        if (pqpl == null) {
            initPQPl(oom, rm);
        } else {
            if (oom < pqoom) {
                initPQPl(oom, rm);
            } else {
                if (!pqrm.equals(rm)) {
                    initPQPl(oom, rm);
                }
            }
        }
        return pqpl;
    }

    private void initPQPl(int oom, RoundingMode rm) {
        pq = getPQ(oom, rm);
        pqpl = new V3D_Plane(pq.getP(), pq.l.v.getCrossProduct(
                getPl(oom, rm).n, oom, rm));
    }

    /**
     * For getting the plane through {@link #qr} in the direction of the normal.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The plane through {@link #qr} in the direction of the normal.
     */
    public V3D_Plane getQRPl(int oom, RoundingMode rm) {
        if (qrpl == null) {
            initQRPl(oom, rm);
        } else {
            if (oom < qroom) {
                initQRPl(oom, rm);
            } else {
                if (!qrrm.equals(rm)) {
                    initQRPl(oom, rm);
                }
            }
        }
        return qrpl;
    }

    private void initQRPl(int oom, RoundingMode rm) {
        qr = getQR(oom, rm);
        qrpl = new V3D_Plane(qr.getP(), qr.l.v.getCrossProduct(
                getPl(oom, rm).n, oom, rm));
    }

    /**
     * For getting the plane through {@link #rp} in the direction of the normal.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The plane through {@link #rp} in the direction of the normal.
     */
    public V3D_Plane getRPPl(int oom, RoundingMode rm) {
        if (rppl == null) {
            initRPPl(oom, rm);
        } else {
            if (oom < rpoom) {
                initRPPl(oom, rm);
            } else {
                if (!rprm.equals(rm)) {
                    initRPPl(oom, rm);
                }
            }
        }
        return rppl;
    }

    private void initRPPl(int oom, RoundingMode rm) {
        rp = getRP(oom, rm);
        rppl = new V3D_Plane(rp.getP(), rp.l.v.getCrossProduct(
                getPl(oom, rm).n, oom, rm));
    }

    @Override
    public V3D_Envelope getEnvelope(int oom) {
        if (en == null) {
            en = new V3D_Envelope(oom, getP(), getQ(), getR());
        }
        return en;
    }

    @Override
    public V3D_Point[] getPoints() {
        V3D_Point[] re = new V3D_Point[3];
        re[0] = getP();
        re[1] = getQ();
        re[2] = getR();
        return re;
    }

    /**
     * @param pt The point to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point or line segment.
     */
    public boolean isIntersectedBy(V3D_Point pt, int oom, RoundingMode rm) {
        if (getEnvelope(oom).isIntersectedBy(pt, oom, rm)) {
            if (getPl(oom, rm).isIntersectedBy(pt, oom, rm)) {
                return isAligned(pt, oom, rm);
                //return isIntersectedBy0(pt, oom, rm);
            }
        }
        return false;
    }

    /**
     * @param pt The point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} if this intersects with {@code pt}.
     */
    @Deprecated
    protected boolean isIntersectedBy0(V3D_Point pt, int oom, RoundingMode rm) {
        V3D_LineSegment tpq = getPQ(oom, rm);
        V3D_LineSegment tqr = getQR(oom, rm);
        V3D_LineSegment trp = getRP(oom, rm);
        if (tpq.isIntersectedBy(pt, oom, rm)
                || tqr.isIntersectedBy(pt, oom, rm)
                || trp.isIntersectedBy(pt, oom, rm)) {
            return true;
        }
        V3D_Vector ppt = new V3D_Vector(getP(), pt, oom, rm);
        V3D_Vector qpt = new V3D_Vector(getQ(), pt, oom, rm);
        V3D_Vector rpt = new V3D_Vector(getR(), pt, oom, rm);
        V3D_Vector cp = tpq.l.v.getCrossProduct(ppt, oom, rm);
        V3D_Vector cq = tqr.l.v.getCrossProduct(qpt, oom, rm);
        V3D_Vector cr = trp.l.v.getCrossProduct(rpt, oom, rm);
        /**
         * If cp, cq and cr are all in the same direction then pt intersects.
         */
//        if (cp.dx.isNegative() && cq.dx.isNegative() && cr.dx.isNegative()) {
//            if (cp.dy.isNegative() && cq.dy.isNegative() && cr.dy.isNegative()) {
//                if (cp.dz.isNegative() && cq.dz.isNegative() && cr.dz.isNegative()) {
//                    return true;
//                }
//            }
//        }
        if (cp.dx.isNegative() == cq.dx.isNegative()
                && cp.dx.isNegative() == cr.dx.isNegative()) {
            if (cp.dy.isNegative() == cq.dy.isNegative()
                    && cp.dy.isNegative() == cr.dy.isNegative()) {
                if (cp.dz.isNegative() == cq.dz.isNegative()
                        && cp.dz.isNegative() == cr.dz.isNegative()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * The point pt aligns with this if it is on the same side of each plane
     * defined a triangle edge (with a normal given by the cross product of the
     * triangle normal and the edge line vector), and the other point of the
     * triangle. The plane normal may be imprecisely calculated. Greater
     * precision can be gained using a smaller oom.
     *
     * @param pt The point to check if it is in alignment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff pl is aligned with this.
     */
    public boolean isAligned(V3D_Point pt, int oom, RoundingMode rm) {
//        if (pl.isIntersectedBy(pt, oom, rm)) {
//            return isIntersectedBy0(pt, oom, rm);
//        }
        if (getPQPl(oom, rm).isOnSameSide(pt, getR(), oom, rm)) {
            if (getQRPl(oom, rm).isOnSameSide(pt, getP(), oom, rm)) {
                if (getRPPl(oom, rm).isOnSameSide(pt, getQ(), oom, rm)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A line segment aligns with this if both end points are aligned according
     * to
     * {@link #isAligned(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int, java.math.RoundingMode)}.
     *
     * @param l The line segment to check if it is in alignment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff l is aligned with this.
     */
    public boolean isAligned(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (isAligned(l.getP(), oom, rm)) {
            return isAligned(l.getQ(), oom, rm);
        }
        return false;
    }

    /**
     * A triangle aligns with this if all points are aligned according to
     * {@link #isAligned(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int, java.math.RoundingMode)}.
     *
     * @param t The triangle to check if it is in alignment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff l is aligned with this.
     */
    public boolean isAligned(V3D_Triangle t, int oom, RoundingMode rm) {
        if (isAligned(t.getP(), oom, rm)) {
            if (isAligned(t.getQ(), oom, rm)) {
                return isAligned(t.getR(), oom, rm);
            }
        }
        return false;
    }

    @Override
    public BigRational getArea(int oom, RoundingMode rm) {
        int oomn2 = oom - 2;
        return getPQV(oomn2, rm).getCrossProduct(getRPV(oomn2, rm).reverse(), oomn2, rm)
                .getMagnitude(oomn2, rm).getSqrt(oom, rm).divide(BigRational.TWO);
    }

    @Override
    public BigRational getPerimeter(int oom, RoundingMode rm) {
        int oomn2 = oom - 2;
        return getPQ(oomn2, rm).getLength(oomn2, rm).getSqrt(oom, rm)
                .add(getQR(oomn2, rm).getLength(oomn2, rm).getSqrt(oom, rm))
                .add(getRP(oomn2, rm).getLength(oomn2, rm).getSqrt(oom, rm));
    }

    /**
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point or line segment.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Line l, int oom,
            RoundingMode rm) {
        V3D_Geometry i = getPl(oom, rm).getIntersection(l, oom, rm);
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
            V3D_FiniteGeometry lpqi = getPQ(oom, rm).getIntersection(l, oom, rm);
            V3D_FiniteGeometry lqri = getQR(oom, rm).getIntersection(l, oom, rm);
            V3D_FiniteGeometry lrpi = getRP(oom, rm).getIntersection(l, oom, rm);
            if (lpqi == null) {
                if (lqri == null) {
                    return null; // This should not happen!
                } else {
                    if (lrpi == null) {
                        return lqri;
                    } else {
                        return getGeometry(((V3D_Point) lqri).getVector(oom, rm),
                                ((V3D_Point) lrpi).getVector(oom, rm), oom, rm);
                    }
                }
            } else if (lpqi instanceof V3D_Point lpqip) {
                if (lqri == null) {
                    if (lrpi == null) {
                        return lpqi;
                    } else {
                        return getGeometry(lpqip.getVector(oom, rm),
                                ((V3D_Point) lrpi).getVector(oom, rm), oom, rm);
                    }
                } else if (lqri instanceof V3D_Point lqrip) {
                    if (lrpi == null) {
                        return getGeometry(lqrip.getVector(oom, rm),
                                lpqip.getVector(oom, rm), oom, rm);
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
     * @param v1 A vector.
     * @param v2 A vector.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Either a line segment or a point.
     */
    public static V3D_FiniteGeometry getGeometry(
            V3D_Vector v1, V3D_Vector v2, int oom, RoundingMode rm) {
        if (v1.equals(v2)) {
            return new V3D_Point(v1);
        } else {
            return new V3D_LineSegment(v1, v2, oom, rm);
        }
    }

    /**
     * Get the intersection between the geometry and the ray {@code r}.
     *
     * @param r The ray to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Ray r, int oom,
            RoundingMode rm) {
        V3D_FiniteGeometry g = getIntersection(r.l, oom, rm);
        if (g == null) {
            return null;
        }
        if (g instanceof V3D_Point gp) {
            if (r.isAligned(gp, oom, rm)) {
//                BigRational[] coeffs = this.pl.equation.coeffs;
//                V3D_Point pt = new V3D_Point(
//                        coeffs[0].multiply(gp.getX(oom, rm)),
//                        coeffs[1].multiply(gp.getY(oom, rm)),
//                        coeffs[2].multiply(gp.getZ(oom, rm)));
//                return pt;
                return gp;
            } else {
                return null;
            }
        }
        V3D_LineSegment ls = (V3D_LineSegment) g;
        V3D_Point lsp = ls.getP();
        V3D_Point lsq = ls.getQ();
        if (r.isAligned(lsp, oom, rm)) {
            if (r.isAligned(lsq, oom, rm)) {
                return ls;
            } else {
                return V3D_LineSegment.getGeometry(r.l.getP(), lsp, oom, rm);
            }
        } else {
            if (r.isAligned(lsq, oom, rm)) {
                return V3D_LineSegment.getGeometry(r.l.getP(), lsq, oom, rm);
            } else {
                throw new RuntimeException("Exception in triangle-linesegment intersection.");
            }
        }
    }

    /**
     * Get the intersection between the geometry and the line segment {@code l}.
     *
     * @param l The line segment to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment l, int oom,
            RoundingMode rm) {
        V3D_FiniteGeometry g = getIntersection(l.l, oom, rm);
        if (g == null) {
            return null;
        }
        if (g instanceof V3D_Point gp) {
            if (l.isAligned(gp, oom, rm)) {
                if (l.isBetween(gp, oom, rm)) {
                    return gp;
                }
            }
            return null;
        }
        V3D_LineSegment ls = (V3D_LineSegment) g;
        if (ls.isBetween(l.getP(), oom, rm) || ls.isBetween(l.getQ(), oom, rm) 
                || l.isBetween(getP(), oom, rm)) {
            return l.getIntersectionLS((V3D_LineSegment) g, oom, rm);
        } else {
            return null;
        }
//        V3D_LineSegment ls = (V3D_LineSegment) g;
//        V3D_Point lsp = ls.getP();
//        V3D_Point lsq = ls.getQ();
//        if (l.isAligned(lsp, oom, rm)) {
//            if (l.isAligned(lsq, oom, rm)) {
//                return ls;
//            } else {
//                V3D_Plane lippl = ls.getPPL();
//                V3D_Point lp = l.getP();
//                if (lippl.isOnSameSide(lp, lsq, oom, rm)) {
//                    return V3D_LineSegment.getGeometry(lsp, lp, oom, rm);
//                } else {
//                    return V3D_LineSegment.getGeometry(lsp, l.getQ(), oom, rm);
//                }
//            }
//        } else {
//            if (l.isAligned(lsq, oom, rm)) {
//                V3D_Plane liqpl = ls.getQPL();
//                V3D_Point lp = l.getP();
//                if (liqpl.isOnSameSide(lp, lsp, oom, rm)) {
//                    return V3D_LineSegment.getGeometry(lsq, lp, oom, rm);
//                } else {
//                    return V3D_LineSegment.getGeometry(lsq, l.getQ(), oom, rm);
//                }
//            } else {
//                return l;
//            }
//        }
    }

    /**
     * Find the point of l to be the other end of the result. If l intersects
     * this, it can overshoot or undershoot, so this effectively clips the
     * result.
     *
     * @param l Line segment being intersected with this.
     * @param pt One end of the result.
     * @param opt The other end l.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Either a point or a line segment.
     */
    private V3D_FiniteGeometry getIntersection0(V3D_LineSegment l, V3D_Point pt,
            V3D_Point opt, int oom, RoundingMode rm) {
        V3D_FiniteGeometry lipq = l.getIntersection(getPQ(oom, rm), oom, rm);
        V3D_FiniteGeometry liqr = l.getIntersection(getQR(oom, rm), oom, rm);
        V3D_FiniteGeometry lirp = l.getIntersection(getRP(oom, rm), oom, rm);
        V3D_Plane ptpl = new V3D_Plane(pt, l.l.v);
        if (lipq == null) {
            if (liqr == null) {
                if (lirp instanceof V3D_Point lirpp) {
                    if (ptpl.isOnSameSide(lirpp, opt, oom, rm)) {
                        return V3D_LineSegment.getGeometry(pt, lirpp, oom, rm);
                    } else {
                        return lirpp;
                    }
                } else {
                    return lirp;
                }
            } else {
                if (lirp == null) {
                    if (liqr instanceof V3D_Point liqrp) {
                        if (ptpl.isOnSameSide(liqrp, opt, oom, rm)) {
                            return V3D_LineSegment.getGeometry(pt, liqrp, oom, rm);
                        } else {
                            return liqrp;
                        }
                    } else {
                        return liqr;
                    }
                } else {
                    if (lirp instanceof V3D_LineSegment lirpl) {
                        return getGeometry(pt, lirpl.getP(), lirpl.getQ(), oom, rm);
                    }
                    if (liqr instanceof V3D_LineSegment liqrp) {
                        return getGeometry(pt, liqrp.getP(), liqrp.getQ(), oom, rm);
                    }
                    V3D_Point lirpp = (V3D_Point) lirp;
                    V3D_Point liqrp = (V3D_Point) liqr;
                    if (ptpl.isOnSameSide(lirpp, opt, oom, rm)) {
                        if (ptpl.isOnSameSide(liqrp, opt, oom, rm)) {
                            //return getGeometry(pt, lirpp, liqrp, oom, rm);
                            return V3D_LineSegment.getGeometry(lirpp, liqrp, oom, rm);
                        } else {
                            return V3D_LineSegment.getGeometry(pt, lirpp, oom, rm);
                        }
                    } else {
                        if (ptpl.isOnSameSide(liqrp, opt, oom, rm)) {
                            return V3D_LineSegment.getGeometry(pt, liqrp, oom, rm);
                        } else {
                            return pt;
                        }
                    }
                }
            }
        } else {
            if (lipq instanceof V3D_LineSegment lipql) {
                return getGeometry(pt, lipql.getP(), lipql.getQ(), oom, rm);
            } else {
                if (liqr == null) {
                    if (lirp == null) {
                        V3D_Point lipqp = (V3D_Point) lipq;
                        if (ptpl.isOnSameSide(lipqp, opt, oom, rm)) {
                            return V3D_LineSegment.getGeometry(pt, lipqp, oom, rm);
                        } else {
                            return pt;
                        }
                    } else {
                        if (lirp instanceof V3D_LineSegment lirpl) {
                            return getGeometry(pt, lirpl.getP(), lirpl.getQ(), oom, rm);
                        }
                        V3D_Point lipqp = (V3D_Point) lipq;
                        V3D_Point lirpp = (V3D_Point) lirp;
                        if (ptpl.isOnSameSide(lipqp, opt, oom, rm)) {
                            if (ptpl.isOnSameSide(lirpp, opt, oom, rm)) {
                                return getGeometry(pt, lirpp, lipqp, oom, rm);
                            } else {
                                return V3D_LineSegment.getGeometry(pt, lipqp, oom, rm);
                            }
                        } else {
                            if (ptpl.isOnSameSide(lirpp, opt, oom, rm)) {
                                return V3D_LineSegment.getGeometry(pt, lirpp, oom, rm);
                            } else {
                                return pt;
                            }
                        }
                    }
                } else {
                    if (liqr instanceof V3D_LineSegment liqrp) {
                        return getGeometry(pt, liqrp.getP(), liqrp.getQ(), oom, rm);
                    }
                    if (lirp == null) {
                        V3D_Point lipqp = (V3D_Point) lipq;
                        V3D_Point liqrp = (V3D_Point) liqr;
                        if (ptpl.isOnSameSide(lipqp, opt, oom, rm)) {
                            if (ptpl.isOnSameSide(liqrp, opt, oom, rm)) {
                                return getGeometry(pt, liqrp, lipqp, oom, rm);
                            } else {
                                return V3D_LineSegment.getGeometry(pt, lipqp, oom, rm);
                            }
                        } else {
                            if (ptpl.isOnSameSide(liqrp, opt, oom, rm)) {
                                return V3D_LineSegment.getGeometry(pt, liqrp, oom, rm);
                            } else {
                                return pt;
                            }
                        }
                    } else {
                        if (lirp instanceof V3D_LineSegment lirpl) {
                            return getGeometry(pt, lirpl.getP(), lirpl.getQ(), oom, rm);
                        }
                        V3D_Point lipqp = (V3D_Point) lipq;
                        V3D_Point liqrp = (V3D_Point) liqr;
                        V3D_Point lirpp = (V3D_Point) lirp;
                        if (ptpl.isOnSameSide(lipqp, opt, oom, rm)) {
                            if (ptpl.isOnSameSide(liqrp, opt, oom, rm)) {
                                if (ptpl.isOnSameSide(lirpp, opt, oom, rm)) {
                                    throw new RuntimeException("Issue with Triangle-Traingle intersection.");
                                } else {
                                    return getGeometry(pt, liqrp, lipqp, oom, rm);
                                }
                            } else {
                                if (ptpl.isOnSameSide(lirpp, opt, oom, rm)) {
                                    return getGeometry(pt, lirpp, lipqp, oom, rm);
                                } else {
                                    return V3D_LineSegment.getGeometry(pt, lipqp, oom, rm);
                                }
                            }
                        } else {
                            if (ptpl.isOnSameSide(liqrp, opt, oom, rm)) {
                                if (ptpl.isOnSameSide(lirpp, opt, oom, rm)) {
                                    return getGeometry(pt, liqrp, lirpp, oom, rm);
                                } else {
                                    return V3D_LineSegment.getGeometry(pt, liqrp, oom, rm);
                                }
                            } else {
                                if (ptpl.isOnSameSide(lirpp, opt, oom, rm)) {
                                    return V3D_LineSegment.getGeometry(pt, lirpp, oom, rm);
                                } else {
                                    return pt;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Calculate and return the intersection between {@code this} and
     * {@code pl}. A question about how to do this:
     * https://stackoverflow.com/questions/3142469/determining-the-intersection-of-a-triangle-and-a-plane
     *
     * @param pl The plane to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code this} and {@code pl}.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Plane pl, int oom,
            RoundingMode rm) {
        // Get intersection if this were a plane
        //V3D_Geometry pi = pl.getIntersection(this, oom);
        V3D_Geometry pi = pl.getIntersection(getPl(oom, rm), oom, rm);
        if (pi == null) {
            return null;
        } else if (pi instanceof V3D_Plane) {
            return this;
        } else {
            // (pi instanceof V3D_Line)
            V3D_FiniteGeometry gPQ = pl.getIntersection(getPQ(oom, rm), oom, rm);
            if (gPQ == null) {
                V3D_FiniteGeometry gQR = pl.getIntersection(getQR(oom, rm), oom, rm);
                if (gQR == null) {
                    V3D_FiniteGeometry gRP = pl.getIntersection(getRP(oom, rm), oom, rm);
                    if (gRP == null) {
                        return null;
                    } else {
                        return gRP;
                    }
                } else if (gQR instanceof V3D_LineSegment) {
                    /**
                     * Logically for a triangle there would be non null
                     * intersections with the other edges, but there could be
                     * rounding error.
                     */
                    return gQR;
//                    V3D_FiniteGeometry gRP = pl.getIntersection(getRP(oom, rm), oom, rm);
//                    if (gRP == null) {
//                        return gQR;
//                    } else if (gRP instanceof V3D_Point gRPp) {
//                        return V3D_LineSegment.getGeometry(gQRl, gRPp, oom, rm);
//                    } else {
//                        return gRP;
//                    }
                } else {
                    V3D_FiniteGeometry gRP = pl.getIntersection(getRP(oom, rm), oom, rm);
                    if (gRP == null) {
                        return gQR;
                    } else if (gRP instanceof V3D_LineSegment) {
                        return gRP;
                        //return V3D_LineSegment.getGeometry(gRPl, (V3D_Point) gQR, oom, rm);
                    } else {
                        return V3D_LineSegment.getGeometry((V3D_Point) gRP, (V3D_Point) gQR, oom, rm);
                    }
                }
            } else if (gPQ instanceof V3D_LineSegment) {
                return gPQ;
            } else {
                V3D_FiniteGeometry gQR = pl.getIntersection(getQR(oom, rm), oom, rm);
                if (gQR == null) {
                    V3D_FiniteGeometry gRP = pl.getIntersection(getRP(oom, rm), oom, rm);
                    if (gRP == null) {
                        return gPQ;
                    } else if (gRP instanceof V3D_LineSegment) {
                        return gRP;
                    } else {
                        return V3D_LineSegment.getGeometry((V3D_Point) gPQ,
                                (V3D_Point) gRP, oom, rm);
                    }
                } else if (gQR instanceof V3D_LineSegment) {
                    return gQR;
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
     * @param t The triangle intersect with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Triangle t, int oom,
            RoundingMode rm) {
        if (getEnvelope(oom).isIntersectedBy(t.getEnvelope(oom), oom)) {
            pl = getPl(oom, rm);
            if (pl.equalsIgnoreOrientation(t.getPl(oom, rm), oom, rm)) {
                /**
                 * The two triangles are in the same plane. Get intersections
                 * between the triangle edges. If there are none, then return t.
                 * If there are some, then in some cases the result is a single
                 * triangle, and in others it is a polygon which can be
                 * represented as a set of coplanar triangles.
                 */
                // Check if vertices intersect
                V3D_Point pttp = t.getP();
                V3D_Point pttq = t.getQ();
                V3D_Point pttr = t.getR();
                boolean pi = isIntersectedBy(pttp, oom, rm);
                boolean qi = isIntersectedBy(pttq, oom, rm);
                boolean ri = isIntersectedBy(pttr, oom, rm);
                if (pi && qi && ri) {
                    return t;
                }
                V3D_Point ptp = getP();
                V3D_Point ptq = getQ();
                V3D_Point ptr = getR();
                boolean pit = t.isIntersectedBy(ptp, oom, rm);
                boolean qit = t.isIntersectedBy(ptq, oom, rm);
                boolean rit = t.isIntersectedBy(ptr, oom, rm);
                if (pit && qit && rit) {
                    return this;
                }
//                if (isAligned(t, oom, rm)) {
//                    return t;
//                }
//                if (t.isAligned(this, oom, rm)) {
//                    return this;
//                }
                V3D_FiniteGeometry gpq = t.getIntersection(getPQ(oom, rm), oom, rm);
                V3D_FiniteGeometry gqr = t.getIntersection(getQR(oom, rm), oom, rm);
                V3D_FiniteGeometry grp = t.getIntersection(getRP(oom, rm), oom, rm);
                if (gpq == null) {
                    if (gqr == null) {
                        
                        if (grp == null) {
                            return null;
                        } else if (grp instanceof V3D_Point grpp) {
                            return grp;
                        } else {
                            if (qi) {
                                return getGeometry((V3D_LineSegment) grp, pttq, oom, rm);
                            } else {
                                return grp;
                            }
                        }
                        
                    } else if (gqr instanceof V3D_Point gqrp) {
                        if (grp == null) {
                            return gqr;
                        } else if (grp instanceof V3D_Point grpp) {
                            return V3D_LineSegment.getGeometry(
                                    gqrp, grpp, oom, rm);
                        } else {
                            V3D_LineSegment ls = (V3D_LineSegment) grp;
                            return getGeometry(gqrp, ls.getP(),
                                    ls.getQ(), oom, rm);
                        }
                    } else {
                        V3D_LineSegment gqrl = (V3D_LineSegment) gqr;
                        if (grp == null) {

                            if (pi) {
                                return getGeometry(gqrl, pttp, oom, rm);
                            } else {
                                return gqr;
                            }
                            
                        } else if (grp instanceof V3D_Point grpp) {
                            V3D_LineSegment ls = (V3D_LineSegment) gqr;
                            return getGeometry(grpp, ls.getP(),
                                    ls.getQ(), oom, rm);
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
                                    ls.getQ(), oom, rm);
                        }
                    } else if (gqr instanceof V3D_Point gqrp) {
                        if (grp == null) {
                            return gqr;
                        } else if (grp instanceof V3D_Point grpp) {
                            return V3D_LineSegment.getGeometry(gpqp, gqrp, grpp, oom, rm);
                        } else {
                            V3D_LineSegment grpl = (V3D_LineSegment) grp;
                            return getGeometry(grpl, gqrp, gpqp, oom, rm);
                        }
                    } else {
                        V3D_LineSegment ls = (V3D_LineSegment) gqr;
                        if (grp == null) {
                            return getGeometry(ls, gpqp, oom, rm);
                        } else if (grp instanceof V3D_Point grpp) {
                            return getGeometry(ls, grpp, gpqp, oom, rm);
                        } else {
                            return getGeometry(ls, (V3D_LineSegment) grp, gpqp, oom, rm);
                        }
                    }
                } else {
                    V3D_LineSegment gpql = (V3D_LineSegment) gpq;
                    if (gqr == null) {
                        if (grp == null) {
                            
                            if (ri) {
                                return getGeometry(gpql, pttr, oom, rm);
                            } else {
                                return gpq;
                            }

                        } else if (grp instanceof V3D_Point grpp) {
                            return getGeometry(grpp, gpql.getP(),
                                    gpql.getQ(), oom, rm);
                        } else {
                            return getGeometry(gpql,
                                    (V3D_LineSegment) grp, oom, rm);
                        }
                    } else if (gqr instanceof V3D_Point gqrp) {
                        if (grp == null) {
                            if (gpql.isIntersectedBy(gqrp, oom, rm)) {
                                return gpql;
                            } else {
                                return new V3D_ConvexHullCoplanar(oom,
                                        rm, getPl(oom, rm).n, gpql.getP(),
                                        gpql.getQ(), gqrp);
                            }
                        } else if (grp instanceof V3D_Point grpp) {
                            ArrayList<V3D_Point> pts = new ArrayList<>();
                            pts.add(gpql.getP());
                            pts.add(gpql.getQ());
                            pts.add(gqrp);
                            pts.add(grpp);
                            ArrayList<V3D_Point> pts2 = V3D_Point.getUnique(pts, oom, rm);
                            return switch (pts2.size()) {
                                case 2 ->
                                    new V3D_LineSegment(pts2.get(0), pts2.get(1), oom, rm);
                                case 3 ->
                                    new V3D_Triangle(pts2.get(0), pts2.get(1), pts2.get(2), oom, rm);
                                default ->
                                    new V3D_ConvexHullCoplanar(oom,
                                    rm, getPl(oom, rm).n, gpql.getP(),
                                    gpql.getQ(), gqrp, grpp);
                            };
                        } else {
                            V3D_LineSegment grpl = (V3D_LineSegment) grp;
                            return V3D_ConvexHullCoplanar.getGeometry(
                                    oom, rm, gpql.getP(),
                                    gpql.getQ(), gqrp, grpl.getP(),
                                    grpl.getQ());
                        }
                    } else {
                        V3D_LineSegment gqrl = (V3D_LineSegment) gqr;
                        if (grp == null) {
                            return V3D_ConvexHullCoplanar.getGeometry(
                                    oom, rm, gpql.getP(), gpql.getQ(),
                                    gqrl.getP(), gqrl.getQ());
                        } else if (grp instanceof V3D_Point grpp) {
                            return V3D_ConvexHullCoplanar.getGeometry(
                                    oom, rm, gpql.getP(),
                                    gpql.getQ(), gqrl.getP(),
                                    gqrl.getQ(), grpp);
                        } else {
                            V3D_LineSegment grpl = (V3D_LineSegment) grp;
                            return V3D_ConvexHullCoplanar.getGeometry(
                                    oom, rm, gpql.getP(),
                                    gpql.getQ(), gqrl.getP(),
                                    gqrl.getQ(), grpl.getP(),
                                    grpl.getQ());
                        }
                    }
                }
            } else {
                // Triangles are not coplanar.
                V3D_FiniteGeometry i = getIntersection(t.pl, oom - 6, rm);
                if (i == null) {
                    return i;
                } else if (i instanceof V3D_Point pt) {
                    if (isAligned(pt, oom, rm)) {
                        return pt;
                    } else {
                        return null;
                    }
                } else {
                    V3D_LineSegment il = (V3D_LineSegment) i;
                    V3D_FiniteGeometry ti = t.getIntersection(pl, oom - 6, rm);
                    if (ti == null) {
                        return ti;
                    } else if (ti instanceof V3D_Point pt) {
                        if (t.isAligned(pt, oom, rm)) {
                            return pt;
                        } else {
                            return null;
                        }
                    } else {
                        return il.getIntersection((V3D_LineSegment) ti, oom, rm);
                    }

//                    V3D_Point lp = il.getP();
//                    V3D_Point lq = il.getQ();
//                    if (isAligned(lp, oom, rm)) {
//                        if (isAligned(lq, oom, rm)) {
//                            return il;
//                        } else {
//                            V3D_FiniteGeometry pqplil = getPQPl(oom, rm).getIntersection(il, oom, rm);
//                            V3D_FiniteGeometry qrplil = getQRPl(oom, rm).getIntersection(il, oom, rm);
//                            V3D_FiniteGeometry rpplil = getRPPl(oom, rm).getIntersection(il, oom, rm);
//                            if (pqplil == null) {
//                                if (qrplil == null) {
//                                    if (rpplil == null) {
//                                        return il;
//                                    } else if (rpplil instanceof V3D_LineSegment) {
//                                        return rpplil;
//                                    } else {
//                                        return V3D_LineSegment.getGeometry((V3D_Point) rpplil, lp, oom, rm);
//                                    }
//                                } else {
//                                    if (qrplil instanceof V3D_LineSegment) {
//                                        return qrplil;
//                                    } else {
//                                        if (rpplil == null) {
//                                            return V3D_LineSegment.getGeometry((V3D_Point) qrplil, lp, oom, rm);
//                                        } else if (rpplil instanceof V3D_LineSegment) {
//                                            return rpplil;
//                                        } else {
//                                            return V3D_LineSegment.getGeometry((V3D_Point) qrplil, (V3D_Point) rpplil, lp, oom, rm);
//                                        }
//                                    }
//                                }
//                            } else {
//                                if (pqplil instanceof V3D_LineSegment) {
//                                    return pqplil;
//                                } else {
//                                    if (qrplil == null) {
//                                        if (rpplil == null) {
//                                            return V3D_LineSegment.getGeometry((V3D_Point) pqplil, lp, oom, rm);
//                                        } else if (rpplil instanceof V3D_LineSegment) {
//                                            return rpplil;
//                                        } else {
//                                            return V3D_LineSegment.getGeometry((V3D_Point) rpplil, (V3D_Point) pqplil, lp, oom, rm);
//                                        }
//                                    } else {
//                                        if (qrplil instanceof V3D_LineSegment) {
//                                            return qrplil;
//                                        } else {
//                                            //if (rpplil == null) {
//                                            return V3D_LineSegment.getGeometry((V3D_Point) pqplil, (V3D_Point) qrplil, lp, oom, rm);
//                                            //} else {
//                                            //    if (rpplil instanceof V3D_LineSegment) {
//                                            //        return rpplil;
//                                            //    } else {
//                                            //        return V3D_LineSegment.getGeometry((V3D_Point) rpplil, (V3D_Point) pqplil, lp, oom, rm);
//                                            //    }
//                                            //}
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    } else {
//                        if (isAligned(lq, oom, rm)) {
//                            V3D_FiniteGeometry pqplil = getPQPl(oom, rm).getIntersection(il, oom, rm);
//                            V3D_FiniteGeometry qrplil = getQRPl(oom, rm).getIntersection(il, oom, rm);
//                            V3D_FiniteGeometry rpplil = getRPPl(oom, rm).getIntersection(il, oom, rm);
//                            if (pqplil == null) {
//                                if (qrplil == null) {
//                                    if (rpplil == null) {
//                                        return lq;
//                                    } else if (rpplil instanceof V3D_LineSegment) {
//                                        return rpplil;
//                                    } else {
//                                        return V3D_LineSegment.getGeometry((V3D_Point) rpplil, lq, oom, rm);
//                                    }
//                                } else {
//                                    if (qrplil instanceof V3D_LineSegment) {
//                                        return qrplil;
//                                    } else {
//                                        if (rpplil == null) {
//                                            return V3D_LineSegment.getGeometry((V3D_Point) qrplil, lq, oom, rm);
//                                        } else if (rpplil instanceof V3D_LineSegment) {
//                                            return rpplil;
//                                        } else {
//                                            return V3D_LineSegment.getGeometry((V3D_Point) qrplil, (V3D_Point) rpplil, lq, oom, rm);
//                                        }
//                                    }
//                                }
//                            } else {
//                                if (pqplil instanceof V3D_LineSegment) {
//                                    return pqplil;
//                                } else {
//                                    if (qrplil == null) {
//                                        if (rpplil == null) {
//                                            return V3D_LineSegment.getGeometry((V3D_Point) pqplil, lq, oom, rm);
//                                        } else if (rpplil instanceof V3D_LineSegment) {
//                                            return rpplil;
//                                        } else {
//                                            return V3D_LineSegment.getGeometry((V3D_Point) rpplil, (V3D_Point) pqplil, lq, oom, rm);
//                                        }
//                                    } else {
//                                        if (qrplil instanceof V3D_LineSegment) {
//                                            return qrplil;
//                                        } else {
//                                            //if (rpplil == null) {
//                                            return V3D_LineSegment.getGeometry((V3D_Point) pqplil, (V3D_Point) qrplil, lq, oom, rm);
//                                            //} else {
//                                            //    if (rpplil instanceof V3D_LineSegment) {
//                                            //        return rpplil;
//                                            //    } else {
//                                            //        return V3D_LineSegment.getGeometry((V3D_Point) rpplil, (V3D_Point) pqplil, lp, oom, rm);
//                                            //    }
//                                            //}
//                                        }
//                                    }
//                                }
//                            }
//                        } else {
//                            V3D_FiniteGeometry pqplil = getPQPl(oom, rm).getIntersection(il, oom, rm);
//                            V3D_FiniteGeometry qrplil = getQRPl(oom, rm).getIntersection(il, oom, rm);
//                            V3D_FiniteGeometry rpplil = getRPPl(oom, rm).getIntersection(il, oom, rm);
//                            if (pqplil == null) {
//                                if (qrplil == null) {
//                                    //if (rpplil == null) {
//                                    return null;
//                                    //} else {
//                                    //    return rpplil;
//                                    //}
//                                } else {
//                                    if (qrplil instanceof V3D_LineSegment) {
//                                        return null;
//                                    } else {
//                                        if (rpplil == null) {
//                                            return null;
//                                        } else if (rpplil instanceof V3D_LineSegment) {
//                                            return null;
//                                        } else {
//                                            return V3D_LineSegment.getGeometry((V3D_Point) qrplil, (V3D_Point) rpplil, oom, rm);
//                                        }
//                                    }
//                                }
//                            } else {
//                                if (pqplil instanceof V3D_LineSegment) {
//                                    if (qrplil == null) {
//                                        return null;
//                                    } else if (qrplil instanceof V3D_LineSegment) {
//                                        return null;
//                                    } else {
//                                        if (rpplil == null) {
//                                            return null;
//                                        } else if (rpplil instanceof V3D_LineSegment) {
//                                            return null;
//                                        } else {
//                                            return V3D_LineSegment.getGeometry((V3D_Point) qrplil, (V3D_Point) rpplil, oom, rm);
//                                        }
//                                    }
//                                } else {
//                                    if (qrplil == null) {
//                                        if (rpplil == null) {
//                                            return null;
//                                        } else if (rpplil instanceof V3D_LineSegment) {
//                                            return null;
//                                        } else {
//                                            return V3D_LineSegment.getGeometry((V3D_Point) pqplil, (V3D_Point) rpplil, oom, rm);
//                                        }
//                                    } else if (qrplil instanceof V3D_LineSegment) {
//                                        if (rpplil == null) {
//                                            return null;
//                                        } else if (rpplil instanceof V3D_LineSegment) {
//                                            return null;
//                                        } else {
//                                            return V3D_LineSegment.getGeometry((V3D_Point) pqplil, (V3D_Point) rpplil, oom, rm);
//                                        }
//                                    } else {
//                                        return V3D_LineSegment.getGeometry((V3D_Point) pqplil, (V3D_Point) qrplil, (V3D_Point) rpplil, oom, rm);
//                                    }
//                                }
//                            }
//                        }
//                    }
                }
            }
        } else {
            return null;
        }
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The centroid point.
     */
    public V3D_Point getCentroid(int oom, RoundingMode rm) {
//        V3D_Point mpq = getPQ().getMidpoint(oom);
//        V3D_Point mqr = getQR().getMidpoint(oom);
//        V3D_Point mrp = getRP().getMidpoint(oom);
////        V3D_LineSegment lmpqr = new V3D_LineSegment(mpq, getRP().pl, oom);
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
//        return new V3D_Point(e, offset, getPAsVector().add(getQV(), oom)
//                .add(getRV(), oom).divide(BigRational.valueOf(3), oom));
        oom -= 6;
        BigRational dx = (p.getDX(oom, rm).add(q.getDX(oom, rm))
                .add(r.getDX(oom, rm))).divide(3);
        BigRational dy = (p.getDY(oom, rm).add(q.getDY(oom, rm))
                .add(r.getDY(oom, rm))).divide(3);
        BigRational dz = (p.getDZ(oom, rm).add(q.getDZ(oom, rm))
                .add(r.getDZ(oom, rm))).divide(3);
        return new V3D_Point(offset, new V3D_Vector(dx, dy, dz));
    }

    /**
     * Test if two triangles are equal. Two triangles are equal if they have 3
     * coincident points, so even if the order is different and one is clockwise
     * and the other anticlockwise, or one faces the other way.
     *
     * @param t The other triangle to test for equality.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff {@code this} is equal to {@code t}.
     */
    public boolean equals(V3D_Triangle t, int oom, RoundingMode rm) {
        V3D_Point tp = t.getP();
        V3D_Point thisp = getP();
        if (tp.equals(thisp, oom, rm)) {
            V3D_Point tq = t.getQ();
            V3D_Point thisq = getQ();
            if (tq.equals(thisq, oom, rm)) {
                return t.getR().equals(getR(), oom, rm);
            } else if (tq.equals(getR(), oom, rm)) {
                return t.getR().equals(thisq, oom, rm);
            } else {
                return false;
            }
        } else if (tp.equals(getQ(), oom, rm)) {
            V3D_Point tq = t.getQ();
            V3D_Point thisr = getR();
            if (tq.equals(thisr, oom, rm)) {
                return t.getR().equals(thisp, oom, rm);
            } else if (tq.equals(thisp, oom, rm)) {
                return t.getR().equals(thisr, oom, rm);
            } else {
                return false;
            }
        } else if (tp.equals(getR(), oom, rm)) {
            V3D_Point tq = t.getQ();
            if (tq.equals(thisp, oom, rm)) {
                return t.getR().equals(getQ(), oom, rm);
            } else if (tq.equals(getQ(), oom, rm)) {
                return t.getR().equals(thisp, oom, rm);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        if (en != null) {
            en.translate(v, oom, rm);
        }
        if (pq != null) {
            pq.translate(v, oom, rm);
        }
        if (qr != null) {
            qr.translate(v, oom, rm);
        }
        if (rp != null) {
            rp.translate(v, oom, rm);
        }
        if (pl != null) {
            pl.translate(v, oom, rm);
        }
        if (pqpl != null) {
            pqpl.translate(v, oom, rm);
        }
        if (qrpl != null) {
            qrpl.translate(v, oom, rm);
        }
        if (rppl != null) {
            rppl.translate(v, oom, rm);
        }
    }
    
    @Override
    public V3D_Triangle rotate(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd, 
            BigRational theta, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V3D_Triangle(this);
        } else {
            return rotateN(ray, uv, bd, theta, oom, rm);
        }
    }
    
    @Override
    public V3D_Triangle rotateN(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd, 
            BigRational theta, int oom, RoundingMode rm) {
        return new V3D_Triangle(
                getP().rotate(ray, uv, bd, theta, oom, rm),
                getQ().rotate(ray, uv, bd, theta, oom, rm),
                getR().rotate(ray, uv, bd, theta, oom, rm), oom, rm);
    }

    /**
     * @param pad Padding
     * @return A String representation of this.
     */
    public String toString(String pad) {
        String r = pad + this.getClass().getSimpleName() + "(\n";
        if (pl != null) {
            r += pad + " pl=(" + pl.toString(pad + " ") + "),\n";
        }
        r += pad + " offset=(" + this.offset.toString(pad + " ") + "),\n"
                + pad + " p=(" + this.p.toString(pad + " ") + "),\n"
                + pad + " q=(" + this.q.toString(pad + " ") + "),\n"
                + pad + " r=(" + this.r.toString(pad + " ") + "))";
        return r;
    }

    /**
     * @param pad Padding
     * @return A simple String representation of this.
     */
    public String toStringSimple(String pad) {
        String r = pad + this.getClass().getSimpleName() + "(\n";
        if (pl != null) {
            r += pad + " pl=(" + pl.toStringSimple(pad + " ") + "),\n";
        }
        r += pad + " offset=(" + this.offset.toStringSimple("") + "),\n"
                + pad + " p=(" + this.p.toStringSimple("") + "),\n"
                + pad + " q=(" + this.q.toStringSimple("") + "),\n"
                + pad + " r=(" + this.r.toStringSimple("") + "))";
        return r;
    }

    @Override
    public String toString() {
        //return toString("");
        return toStringSimple("");
    }

    /**
     * If p, q and r are equal then the point is returned. If two of the points
     * are the same, then a line segment is returned. If all points are
     * different then a triangle is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, qv)} or
     * {@code new V3D_Triangle(pl, qv, r)}
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
                    if (V3D_Line.isCollinear(oom, rm, p, q, r)) {
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
//                    return new V3D_Triangle(pl.e, V3D_Vector.ZERO,
//                            pl.getVector(pl.e.oom),
//                            qv.getVector(pl.e.oom), r.getVector(pl.e.oom));
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
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, qv)} or
     * {@code new V3D_Triangle(pl, qv, r)}
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_LineSegment l1,
            V3D_LineSegment l2, V3D_LineSegment l3, int oom, RoundingMode rm) {
        V3D_Point l1p = l1.getP();
        V3D_Point l1q = l1.getQ();
        V3D_Point l2p = l2.getP();
        V3D_Point l2q = l2.getQ();
        V3D_Point l3p = l3.getP();
        V3D_Point l3q = l3.getQ();
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
            return new V3D_ConvexHullCoplanar(oom, rm, pl.n, pts);
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
//            BigRational dl1pl2p = l1p.getDistanceSquared(l2p, oom);
//            BigRational dl1pl2q = l1p.getDistanceSquared(l2q, oom);
//            BigRational dl1ql2p = l1q.getDistanceSquared(l2p, oom);
//            BigRational dl1ql2q = l1q.getDistanceSquared(l2q, oom);
//            // dl1l3
//            BigRational dl1pl3p = l1p.getDistanceSquared(l3p, oom);
//            BigRational dl1pl3q = l1p.getDistanceSquared(l3q, oom);
//            BigRational dl1ql3p = l1q.getDistanceSquared(l3p, oom);
//            BigRational dl1ql3q = l1q.getDistanceSquared(l3q, oom);
////            // dl2l3
////            BigRational dl2pl3p = l2p.getDistanceSquared(l3p, oom);
////            BigRational dl2pl3q = l2p.getDistanceSquared(l3q, oom);
////            BigRational dl2ql3p = l2q.getDistanceSquared(l3p, oom);
////            BigRational dl2ql3q = l2q.getDistanceSquared(l3q, oom);
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
     * Used in intersecting two triangles to give the overall intersection. If
     * there are 3 unique points then a triangle is returned. If there are 4 or
     * more unique points, then a V3D_ConvexHullCoplanar is returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, qv)} or
     * {@code new V3D_Triangle(pl, qv, r)}
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_LineSegment l1,
            V3D_LineSegment l2, V3D_Point pt, int oom, RoundingMode rm) {
        V3D_Point l1p = l1.getP();
        V3D_Point l1q = l1.getQ();
        V3D_Point l2p = l2.getP();
        V3D_Point l2q = l2.getQ();
        ArrayList<V3D_Point> points;
        {
            List<V3D_Point> pts = new ArrayList<>();
            pts.add(l1p);
            pts.add(l1q);
            pts.add(l2p);
            pts.add(l2q);
            pts.add(pt);
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
            return new V3D_ConvexHullCoplanar(oom, rm, pl.n, pts);
        }
    }

    /**
     * Used in intersecting a triangle and a tetrahedron. If there are 3 unique
     * points then a triangle is returned. If there are 4 points, then a
     * V3D_ConvexHullCoplanar is returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, qv)} or
     * {@code new V3D_Triangle(pl, qv, r)}
     */
    protected static V3D_FiniteGeometry getGeometry2(V3D_LineSegment l1,
            V3D_LineSegment l2, int oom, RoundingMode rm) {
        V3D_Point l1p = l1.getP();
        V3D_Point l1q = l1.getQ();
        V3D_Point l2p = l2.getP();
        V3D_Point l2q = l2.getQ();
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
            return new V3D_ConvexHullCoplanar(oom, rm, pl.n, pts);
        }
    }

    /**
     * This may be called when there is an intersection of two triangles. If l1
     * and l2 are two sides of a triangle, return the triangle.
     *
     * @param l1 A line segment and triangle edge.
     * @param l2 A line segment and triangle edge.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return a triangle for which l1 and l2 are edges
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_LineSegment l1,
            V3D_LineSegment l2, int oom, RoundingMode rm) {
        V3D_Point pt = (V3D_Point) l1.getIntersection(l2, oom, rm);
        V3D_Point l1p = l1.getP();
        V3D_Point l2p = l2.getP();
        if (l1p.equals(pt, oom, rm)) {
            if (l2p.equals(pt, oom, rm)) {
                return new V3D_Triangle(pt, l1.getQ(), l2.getQ(), oom, rm);
            } else {
                return new V3D_Triangle(pt, l1.getQ(), l2p, oom, rm);
            }
        } else {
            if (l2p.equals(pt, oom, rm)) {
                return new V3D_Triangle(pt, l1p, l2.getQ(), oom, rm);
            } else {
                return new V3D_Triangle(pt, l1p, l2p, oom, rm);
            }
        }
    }

    /**
     * This may be called when there is an intersection of two triangles where l
     * is a side of a triangle and pl is a point.
     *
     * @param l A line segment.
     * @param p1 A point that is either not collinear to l or intersects l.
     * @param p2 A point that is either not collinear to l or intersects l.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return a triangle for which l is an edge and pl is a vertex.
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_LineSegment l,
            V3D_Point p1, V3D_Point p2, int oom, RoundingMode rm) {
        if (l.isIntersectedBy(p1, oom, rm)) {
            return getGeometry(l, p2, oom, rm);
        } else {
            return new V3D_Triangle(p1, l.getP(), l.getQ(), oom, rm);
        }
    }

    /**
     * This may be called when there is an intersection of two triangles where l
     * is a side of a triangle and pl is a point that is not collinear to l.
     *
     * @param l A line segment.
     * @param p A point that is not collinear to l.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return a triangle for which l is an edge and pl is a vertex.
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_LineSegment l,
            V3D_Point p, int oom, RoundingMode rm) {
        if (l.isIntersectedBy(p, oom, rm)) {
            return l;
        }
        return new V3D_Triangle(p, l.getP(), l.getQ(), oom, rm);
    }

    /**
     * For getting the point opposite a side of a triangle given the side.
     *
     * @param l a line segment either equal to one of the edges of this - null
     * null null null null null null null null null null null null null null
     * null null null null null null null     {@link #getPQ(int, java.math.RoundingMode)},
     * {@link #getQR(int, java.math.RoundingMode)} or
     * {@link #getRP(int, java.math.RoundingMode)}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The point of {@code this} that does not intersect with {@code l}.
     */
    public V3D_Point getOpposite(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (getPQ(oom, rm).equals(l, oom, rm)) {
            return getR();
        } else {
            if (getQR(oom, rm).equals(l, oom, rm)) {
                return getP();
            } else {
                return getQ();
            }
        }
    }

    /**
     * Get the minimum distance to {@code pv}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared to {@code pv}.
     */
    public BigRational getDistance(V3D_Point p, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code pt}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared to {@code pv}.
     */
    public BigRational getDistanceSquared(V3D_Point pt, int oom, RoundingMode rm) {
        if (getPl(oom, rm).isIntersectedBy(pt, oom, rm)) {
            //if (isIntersectedBy0(pt, oom, rm)) {
            if (isAligned(pt, oom, rm)) {
                return BigRational.ZERO;
            } else {
                return getDistanceSquaredEdge(pt, oom, rm);
            }
        }
        V3D_Point poi = pl.getPointOfProjectedIntersection(pt, oom, rm);
        if (isAligned(poi, oom, rm)) {
            return poi.getDistanceSquared(pt, oom, rm);
        } else {
            return getDistanceSquaredEdge(pt, oom, rm);
        }
    }

    /**
     * Get the minimum distance squared to {@code pt} from the perimeter.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared between pt and the nearest edge of this.
     */
    public BigRational getDistanceSquaredEdge(V3D_Point pt, int oom,
            RoundingMode rm) {
        int oomn2 = oom - 2;
        BigRational pqd2 = getPQ(oom, rm).getDistanceSquared(pt, oomn2, rm);
        BigRational qrd2 = getQR(oom, rm).getDistanceSquared(pt, oomn2, rm);
        BigRational rpd2 = getRP(oom, rm).getDistanceSquared(pt, oomn2, rm);
        return BigRational.min(pqd2, qrd2, rpd2);
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
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm);
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
        BigRational dpq2 = getPQ(oom, rm).getDistanceSquared(l, oom, rm);
        BigRational dqr2 = getQR(oom, rm).getDistanceSquared(l, oom, rm);
        BigRational drp2 = getRP(oom, rm).getDistanceSquared(l, oom, rm);
        return BigRational.min(dpq2, dqr2, drp2);
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
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistanceSquared(V3D_LineSegment l, int oom,
            RoundingMode rm) {
        if (getIntersection(l, oom, rm) != null) {
            return BigRational.ZERO;
        }
        BigRational dlpq2 = l.getDistanceSquared(getPQ(oom, rm), oom, rm);
        BigRational dlqr2 = l.getDistanceSquared(getQR(oom, rm), oom, rm);
        BigRational dlrp2 = l.getDistanceSquared(getRP(oom, rm), oom, rm);
        BigRational d2 = BigRational.min(dlpq2, dlqr2, dlrp2);
        /**
         * For any end points of l that are aligned with this, calculate the
         * distances as these could be the minimum.
         */
        V3D_Point lp = l.getP();
        V3D_Point lq = l.getQ();
        if (isAligned(lp, oom, rm)) {
            d2 = BigRational.min(d2, getDistanceSquared(lp, oom, rm));
        }
        if (isAligned(lq, oom, rm)) {
            d2 = BigRational.min(d2, getDistanceSquared(lq, oom, rm));
        }
        return d2;
    }

    /**
     * Get the minimum distance to {@code pl}.
     *
     * @param pl A plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code pv}.
     */
    public BigRational getDistance(V3D_Plane pl, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(pl, oom, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code pl}.
     *
     * @param pl A plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code pv}.
     */
    public BigRational getDistanceSquared(V3D_Plane pl, int oom, RoundingMode rm) {
        BigRational dplpq2 = pl.getDistanceSquared(getPQ(oom, rm), oom, rm);
        BigRational dplqr2 = pl.getDistanceSquared(getQR(oom, rm), oom, rm);
//        BigRational dplrp2 = pl.getDistanceSquared(getRP(oom, rm), oom, rm);
//        return BigRational.min(dplpq2, dplqr2, dplrp2);
        return BigRational.min(dplpq2, dplqr2);
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
        return new Math_BigRationalSqrt(getDistanceSquared(t, oom, rm), oom, rm)
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
    public BigRational getDistanceSquared(V3D_Triangle t, int oom,
            RoundingMode rm) {
        if (getIntersection(t, oom, rm) != null) {
            return BigRational.ZERO;
        }
        BigRational dtpq2 = t.getDistanceSquared(getPQ(oom, rm), oom, rm);
        BigRational dtqr2 = t.getDistanceSquared(getQR(oom, rm), oom, rm);
        BigRational dtrp2 = t.getDistanceSquared(getRP(oom, rm), oom, rm);
        return BigRational.min(dtpq2, dtqr2, dtrp2);
//        BigRational dpq2 = getDistanceSquared(t.getPQ(oom, rm), oom, rm);
//        BigRational dqr2 = getDistanceSquared(t.getQR(oom, rm), oom, rm);
//        BigRational drp2 = getDistanceSquared(t.getRP(oom, rm), oom, rm);
//        return BigRational.min(dtpq2, dtqr2, dtrp2, dpq2, dqr2, drp2);
    }

    /**
     * For retrieving a Set of points that are the corners of the triangles.
     *
     * @param triangles The input.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A Set of points that are the corners of the triangles.
     */
    //public static ArrayList<V3D_Point> getPoints(V3D_Triangle[] triangles) {
    public static V3D_Point[] getPoints(V3D_Triangle[] triangles, int oom, RoundingMode rm) {
        List<V3D_Point> s = new ArrayList<>();
        for (var t : triangles) {
            s.add(t.getP());
            s.add(t.getQ());
            s.add(t.getR());
        }
        ArrayList<V3D_Point> points = V3D_Point.getUnique(s, oom, rm);
        return points.toArray(V3D_Point[]::new);
    }

    /**
     * Clips this using pl and returns the part that is on the same side as pt.
     *
     * @param pl The plane that clips.
     * @param pt A point that is used to return the side of the clipped
     * triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometry clip(V3D_Plane pl, V3D_Point pt, int oom, RoundingMode rm) {
        V3D_FiniteGeometry i = getIntersection(pl, oom, rm);
        V3D_Point ppt = getPl(oom, rm).getP();
        if (i == null) {
            if (pl.isOnSameSide(ppt, pt, oom, rm)) {
                return this;
            } else {
                return null;
            }
        } else if (i instanceof V3D_Point ip) {
            /**
             * If at least two points of the triangle are on the same side of pl
             * as pt, then return this, otherwise return ip. As the calculation
             * of i is perhaps imprecise, then simply testing if ip equals one
             * of the triangle corner points and then testing another point to
             * see if it that is on the same side as pt might not work out
             * right!
             */
            int poll = 0;
            if (pl.isOnSameSide(ppt, pt, oom, rm)) {
                poll++;
            }
            if (pl.isOnSameSide(getQ(), pt, oom, rm)) {
                poll++;
            }
            if (pl.isOnSameSide(getR(), pt, oom, rm)) {
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
            V3D_Point qpt = getQ();
            V3D_Point rpt = getR();
            if (pl.isOnSameSide(ppt, pt, oom, rm)) {
                if (pl.isOnSameSide(qpt, pt, oom, rm)) {
                    if (pl.isOnSameSide(rpt, pt, oom, rm)) {
                        return this;
                    } else {
                        return getGeometry(il, getPQ(oom, rm), oom, rm);
                    }
                } else {
                    if (pl.isOnSameSide(rpt, pt, oom, rm)) {
                        return getGeometry(il, getRP(oom, rm), oom, rm);
                    } else {
                        return getGeometry(il, ppt, oom, rm);
                    }
                }
            } else {
                if (pl.isOnSameSide(qpt, pt, oom, rm)) {
                    if (pl.isOnSameSide(rpt, pt, oom, rm)) {
                        return getGeometry(il, getPQ(oom, rm), oom, rm);
                    } else {
                        return getGeometry(il, qpt, oom, rm);
                    }
                } else {
                    if (pl.isOnSameSide(rpt, pt, oom, rm)) {
                        return getGeometry(il, rpt, oom, rm);
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometry clip(V3D_Triangle t, V3D_Point pt, int oom, RoundingMode rm) {
        V3D_Point tp = t.getP();
        V3D_Point tq = t.getQ();
        V3D_Point tr = t.getR();
        V3D_Vector n = t.getPl(oom, rm).n;
        V3D_Point ppt = new V3D_Point(tp.offset.add(n, oom, rm), tp.rel);
        V3D_Plane ppl = new V3D_Plane(tp, tq, ppt, oom, rm);
        V3D_Point qpt = new V3D_Point(tq.offset.add(n, oom, rm), tq.rel);
        V3D_Plane qpl = new V3D_Plane(tq, tr, qpt, oom, rm);
        V3D_Point rpt = new V3D_Point(tr.offset.add(n, oom, rm), tr.rel);
        V3D_Plane rpl = new V3D_Plane(tr, tp, rpt, oom, rm);
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

    @Override
    public boolean isIntersectedBy(V3D_Envelope aabb, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
