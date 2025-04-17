/*
 * Copyright 2020-2025 Andy Turner, University of Leeds.
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_FiniteGeometry_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Triangle_d;
import uk.ac.leeds.ccg.v3d.geometry.light.V3D_VTriangle;

/**
 * For representing and processing triangles in 3D.A triangle has a non-zero
 * area.The corner points are defined by {@link #pv}, {@link #qv} and
 * {@link #rv}. The following depicts a generic triangle {@code
 * p                         pq                       q
 * pv *- - - - - - - - - - - + - - - - - - - - - - -* qv
 * \~                   mpq                   ~/
 * \  ~                 |                 ~  /
 * \    ~              |              ~    /
 * \      ~           |           ~      /
 * \        ~        |        ~        /
 * \          ~     |     ~          /
 * \            ~  |  ~            /
 * \  -n  -n  -n  c  +n  +n  +n  +n  normal going out of the page.
 * \          ~  |  ~          /
 * \      ~     |     ~      /
 * \  ~        |        ~  /
 * + mrp      |      mqr +
 * rp  \         |         /  qr
 * \        |        /
 * \       |       /
 * \      |      /
 * \     |     /
 * \    |    /
 * \   |   /
 * \  |  /
 * \ | /
 * \|/
 *
 * rv
 * r
 * }
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V3D_Triangle extends V3D_Area {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the normal vector of the triangle.
     */
    protected V3D_Vector normal;

    /**
     * A vector for a corner of the triangle. This is invariant under
     * translation and is relative to the other vector corners.
     */
    public V3D_Vector pv;

    /**
     * A vector for a corner of the triangle. This is invariant under
     * translation and is relative to the other vector corners.
     */
    public V3D_Vector qv;

    /**
     * A vector for a corner of the triangle. This is invariant under
     * translation and is relative to the other vector corners.
     */
    public V3D_Vector rv;

    /**
     * For storing a corner point of the triangle corresponding to {@link #pv}.
     */
    protected V3D_Point p;

    /**
     * The Order of Magnitude for the precision of {@link #p}.
     */
    int poom;

    /**
     * The RoundingMode used for the calculation of {@link #p} or {@code null}
     * if {@link #p} was input and not calculated.
     */
    RoundingMode prm;

    /**
     * For storing a corner point of the triangle corresponding to {@link #qv}.
     */
    protected V3D_Point q;

    /**
     * The Order of Magnitude for the precision of {@link #q}.
     */
    int qoom;

    /**
     * The RoundingMode used for the calculation of {@link #q}.
     */
    RoundingMode qrm;

    /**
     * For storing a corner point of the triangle corresponding to {@link #rv}.
     */
    protected V3D_Point r;

    /**
     * The Order of Magnitude for the precision of {@link #r}.
     */
    int room;

    /**
     * The RoundingMode used for the calculation of {@link #r}.
     */
    RoundingMode rrm;

    /**
     * For storing the line segment from {@link #p} to {@link #q} for a given
     * Order of Magnitude and RoundingMode precision.
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
     * For storing the line segment from {@link #q} to {@link #r} for a given
     * Order of Magnitude and RoundingMode precision.
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
     * For storing the line segment from {@link #r} to {@link #p} for a given
     * Order of Magnitude and RoundingMode precision.
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
     * @param t The triangle to copy.
     */
    public V3D_Triangle(V3D_Triangle t) {
        super(t.env, new V3D_Vector(t.offset), new V3D_Plane(t.pl));
        pv = new V3D_Vector(t.pv);
        qv = new V3D_Vector(t.qv);
        rv = new V3D_Vector(t.rv);
        if (t.p != null) {
            p = new V3D_Point(t.p);
            poom = t.poom;
            prm = t.prm;
        }
        if (t.q != null) {
            q = new V3D_Point(t.q);
            qoom = t.qoom;
            qrm = t.qrm;
        }
        if (t.r != null) {
            r = new V3D_Point(t.r);
            room = t.room;
            rrm = t.rrm;
        }
        if (t.pq != null) {
            pq = new V3D_LineSegment(t.pq);
            pqoom = t.pqoom;
            pqrm = t.pqrm;
        }
        if (t.qr != null) {
            qr = new V3D_LineSegment(t.qr);
            qroom = t.qroom;
            qrrm = t.qrrm;
        }
        if (t.rp != null) {
            rp = new V3D_LineSegment(t.rp);
            rpoom = t.rpoom;
            rprm = t.rprm;
        }
    }

    /**
     * Creates a new triangle.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param t The triangle to initialise this from.
     */
    public V3D_Triangle(V3D_Environment env, V3D_Vector offset,
            V3D_VTriangle t) {
        this(env, offset,
                new V3D_Vector(t.pq.p),
                new V3D_Vector(t.pq.q),
                new V3D_Vector(t.qr.q));
    }

    /**
     * Creates a new triangle. {@link #offset} is set to
     * {@link V3D_Vector#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param pv What {@link #pv} is set to.
     * @param qv What {@link #qv} is set to.
     * @param rv What {@link #rv} is set to.
     */
    public V3D_Triangle(V3D_Environment env, V3D_Vector pv, V3D_Vector qv,
            V3D_Vector rv) {
        this(env, V3D_Vector.ZERO, pv, qv, rv);
    }

    /**
     * Creates a new triangle. {@code pv}, {@code qv} and {@code rv} must all be
     * different.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param pv What {@link #pv} is set to.
     * @param qv What {@link #qv} is set to.
     * @param rv What {@link #rv} is set to.
     */
    public V3D_Triangle(V3D_Environment env, V3D_Vector offset, V3D_Vector pv,
            V3D_Vector qv, V3D_Vector rv) {
        super(env, offset, null);
        this.pv = pv;
        this.qv = qv;
        this.rv = rv;
        // Debugging code:
        if (pv.equals(qv, env.oom, env.rm) || pv.equals(rv, env.oom, env.rm)
                || qv.equals(rv, env.oom, env.rm)) {
            throw new RuntimeException("pv.equals(qv, env.oom, env.rm) "
                    + "|| pv.equals(rv, env.oom, env.rm) "
                    + "|| qv.equals(rv, env.oom, env.rm)");
        }
    }

    /**
     * Creates a new triangle assuming pv, v and rv are different.
     *
     * @param pl What {@link #pl} is set to. This must be the plane of the
     * triangle.
     * @param offset What {@link #offset} is set to.
     * @param pv What {@link #pv} is set to.
     * @param qv What {@link #qv} is set to.
     * @param rv What {@link #rv} is set to.
     */
    public V3D_Triangle(V3D_Plane pl, V3D_Vector offset,
            V3D_Vector pv, V3D_Vector qv, V3D_Vector rv) {
        super(pl.env, offset, pl);
        this.pv = pv;
        this.qv = qv;
        this.rv = rv;
        // Debugging code:
        if (pv.equals(qv, env.oom, env.rm) || pv.equals(rv, env.oom, env.rm)
                || qv.equals(rv, env.oom, env.rm)) {
            throw new RuntimeException("pv.equals(qv, env.oom, env.rm) "
                    + "|| pv.equals(rv, env.oom, env.rm) "
                    + "|| qv.equals(rv, env.oom, env.rm)");
        }
    }

    /**
     * Creates a new triangle.
     *
     * @param l A line segment representing one of the three edges of the
     * triangle.
     * @param r Defines the other point relative to l.offset that defines the
     * triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Triangle(V3D_LineSegment l, V3D_Vector r,
            int oom, RoundingMode rm) {
        this(l.env, new V3D_Vector(l.offset), new V3D_Vector(l.l.pv),
                l.l.pv.add(l.l.v, oom, rm), new V3D_Vector(r));
    }

    /**
     * Creates a new triangle.
     *
     * @param pl What {@link #pl} is set to.
     * @param ls A line segment representing one of the three edges of the
     * triangle.
     * @param pt The other point that defines the triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Triangle(V3D_Plane pl, V3D_LineSegment ls, V3D_Point pt,
            int oom, RoundingMode rm) {
        this(pl, new V3D_Vector(ls.offset),
                new V3D_Vector(ls.l.pv),
                ls.l.pv.add(ls.l.v, oom, rm),
                pt.getVector(oom, rm).subtract(ls.offset, oom, rm));
    }

    /**
     * Creates a new instance.
     *
     * @param p Used to initialise {@link #offset} and {@link #pl}.
     * @param q Used to initialise {@link #qv}.
     * @param r Used to initialise {@link #rv}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Triangle(V3D_Point p, V3D_Point q, V3D_Point r,
            int oom, RoundingMode rm) {
        this(p.env, new V3D_Vector(p.offset), new V3D_Vector(p.rel),
                q.getVector(oom, rm).subtract(p.offset, oom, rm),
                r.getVector(oom, rm).subtract(p.offset, oom, rm));
    }

    /**
     * Creates a new triangle.
     *
     * @param pl What {@link #pl} is set to.
     * @param p A point.
     * @param q A point.
     * @param r A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Triangle(V3D_Plane pl, V3D_Point p, V3D_Point q, V3D_Point r,
            int oom, RoundingMode rm) {
        this(pl, new V3D_Vector(p.offset), new V3D_Vector(p.rel),
                q.getVector(oom, rm).subtract(p.offset, oom, rm),
                r.getVector(oom, rm).subtract(p.offset, oom, rm));
    }

    /**
     * Creates a new triangle.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param pv What {@link #pv} is set to.
     * @param qv What {@link #qv} is set to.
     * @param rv What {@link #rv} is set to.
     * @param normal What {@link #normal} is set to.
     */
    public V3D_Triangle(V3D_Environment env, V3D_Vector offset, V3D_Vector pv,
            V3D_Vector qv, V3D_Vector rv, V3D_Vector normal) {
        super(env, offset, null);
        this.pv = pv;
        this.qv = qv;
        this.rv = rv;
        this.normal = normal;
    }

    /**
     * Creates a new instance.
     *
     * @param pt A point giving the direction of the normal vector.
     * @param p Used to initialise {@link env}, {@link #offset} and {@link #pv}
     * and {@link p}.
     * @param q Used to initialise {@link #qv} and {@link q}.
     * @param r Used to initialise {@link #rv} and {@link r}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Triangle(V3D_Point pt, V3D_Point p, V3D_Point q, V3D_Point r,
            int oom, RoundingMode rm) {
        super(p.env, new V3D_Vector(p.offset), null);
        this.pv = new V3D_Vector(p.rel);
        this.p = new V3D_Point(p);
        this.qv = q.getVector(oom, rm).subtract(p.offset, oom, rm);
        this.q = new V3D_Point(q);
        this.rv = r.getVector(oom, rm).subtract(p.offset, oom, rm);
        this.r = new V3D_Point(r);
        this.pl = new V3D_Plane(pt, p.offset, p.getVector(oom, rm),
                q.getVector(oom, rm), r.getVector(oom, rm), oom, rm);
        this.normal = pl.n;
    }

    /**
     * Creates a new instance.
     *
     * @param ls A line segment defining two of the points.
     * @param pt The third point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Triangle(V3D_LineSegment ls, V3D_Point pt, int oom, RoundingMode rm) {
        this(ls.getP(), ls.getQ(oom, rm), pt, oom, rm);
    }

    @Override
    protected void initPl(int oom, RoundingMode rm) {
        pl = new V3D_Plane(env, offset, pv, qv, rv, oom, rm);
    }

    @Override
    protected void initPl(V3D_Point pt, int oom, RoundingMode rm) {
        pl = new V3D_Plane(pt, offset, pv, qv, rv, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A new point based on {@link #pv} and {@link #offset}.
     */
    public final V3D_Point getP(int oom, RoundingMode rm) {
        if (p == null) {
            initP(oom, rm);
        } else {
            if (prm != null) {
                if (oom < poom) {
                    initP(oom, rm);
                } else {
                    if (oom == poom) {
                        if (!rm.equals(prm)) {
                            initP(oom, rm);
                        }
                    }
                }
            }
        }
        return p;
    }

    private void initP(int oom, RoundingMode rm) {
        p = new V3D_Point(env, offset, pv);
        poom = oom;
        prm = rm;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A new point based on {@link #qv} and {@link #offset}.
     */
    public final V3D_Point getQ(int oom, RoundingMode rm) {
        if (q == null) {
            initQ(oom, rm);
        } else {
            if (qrm != null) {
                if (oom < this.qoom) {
                    initQ(oom, rm);
                } else {
                    if (oom == this.qoom) {
                        if (!rm.equals(this.qrm)) {
                            initQ(oom, rm);
                        }
                    }
                }
            }
        }
        return q;
    }

    private void initQ(int oom, RoundingMode rm) {
        q = new V3D_Point(env, offset, qv);
        qoom = oom;
        qrm = rm;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A new point based on {@link #rv} and {@link #offset}.
     */
    public final V3D_Point getR(int oom, RoundingMode rm) {
        if (r == null) {
            initR(oom, rm);
        } else {
            if (rrm != null) {
                if (oom < room) {
                    initR(oom, rm);
                } else if (oom == room) {
                    if (!rrm.equals(rm)) {
                        initR(oom, rm);
                    }
                }
            }
        }
        return r;
    }

    private void initR(int oom, RoundingMode rm) {
        r = new V3D_Point(env, offset, rv);
        room = oom;
        rrm = rm;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code v.subtract(pv, oom, rm)}
     */
    public final V3D_Vector getPQV(int oom, RoundingMode rm) {
        return qv.subtract(pv, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code rv.subtract(v, oom, rm)}
     */
    public final V3D_Vector getQRV(int oom, RoundingMode rm) {
        return rv.subtract(qv, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code pv.subtract(rv, oom, rm)}
     */
    public final V3D_Vector getRPV(int oom, RoundingMode rm) {
        return pv.subtract(rv, oom, rm);
    }

    /**
     * For getting the line segment from {@link #getP()} to {@link #getQ()} with
     * at least oom precision given rm.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Line segment from rv to pv.
     */
    public final V3D_LineSegment getPQ(int oom, RoundingMode rm) {
        if (pq == null) {
            initPQ(oom, rm);
        } else {
            if (pqrm == null) {
                initPQ(oom, rm);
            } else {
                if (oom < pqoom) {
                    initPQ(oom, rm);
                } else {
                    if (oom == pqoom) {
                        if (!pqrm.equals(rm)) {
                            initPQ(oom, rm);
                        }
                    }
                }
            }
        }
        return pq;
    }

    private void initPQ(int oom, RoundingMode rm) {
        //pq = new V3D_LineSegment(env, offset, pv, qv.subtract(pv, oom, rm),
        //        oom, rm);
        pq = new V3D_LineSegment(getP(oom, rm), getQ(oom, rm), oom, rm);
        pqoom = oom;
        pqrm = rm;
    }

    /**
     * For getting the line segment from {@link #getQ()} to {@link #getR()} with
     * at least oom precision given rm.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Line segment from rv to pv.
     */
    public final V3D_LineSegment getQR(int oom, RoundingMode rm) {
        if (qr == null) {
            initQR(oom, rm);
        } else {
            if (qrrm == null) {
                initQR(oom, rm);
            } else {
                if (oom < qroom) {
                    initQR(oom, rm);
                } else {
                    if (oom == qroom) {
                        if (!qrrm.equals(rm)) {
                            initQR(oom, rm);
                        }
                    }
                }
            }
        }
        return qr;
    }

    private void initQR(int oom, RoundingMode rm) {
        //qr = new V3D_LineSegment(env, offset, qv, rv.subtract(qv, oom, rm),
        //        oom, rm);
        qr = new V3D_LineSegment(getQ(oom, rm), getR(oom, rm), oom, rm);
        qroom = oom;
        qrrm = rm;
    }

    /**
     * For getting the line segment from {@link #getR()} to {@link #getP()} with
     * at least oom precision given rm.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Line segment from rv to pv.
     */
    public final V3D_LineSegment getRP(int oom, RoundingMode rm) {
        if (rp == null) {
            initRP(oom, rm);
        } else {
            if (rprm == null) {
                initRP(oom, rm);
            } else {
                if (oom < rpoom) {
                    initRP(oom, rm);
                } else {
                    if (oom == rpoom) {
                        if (!rprm.equals(rm)) {
                            initRP(oom, rm);
                        }
                    }
                }
            }
        }
        return rp;
    }

    private void initRP(int oom, RoundingMode rm) {
        //rp = new V3D_LineSegment(env, offset, rv, pv.subtract(rv, oom, rm),
        //        oom, rm);
        rp = new V3D_LineSegment(getR(oom, rm), getP(oom, rm), oom, rm);
        rpoom = oom;
        rprm = rm;
    }

    /**
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The internal angle at {@link #pv}.
     */
    public final BigRational getAngleP(int oom, RoundingMode rm) {
        return getPQV(oom, rm).getAngle(getRPV(oom, rm).reverse(), oom, rm);
    }

    /**
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The internal angle at {@link #qv}.
     */
    public final BigRational getAngleQ(int oom, RoundingMode rm) {
        return getPQV(oom, rm).reverse().getAngle(getQRV(oom, rm), oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The internal angle at {@link #rv}.
     */
    public final BigRational getAngleR(int oom, RoundingMode rm) {
        return getQRV(oom, rm).reverse().getAngle(getRPV(oom, rm), oom, rm);
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
    public V3D_AABB getAABB(int oom, RoundingMode rm) {
        if (en == null) {
            en = new V3D_AABB(oom, getP(oom, rm), getQ(oom, rm), getR(oom, rm));
        }
        return en;
    }

    @Override
    public V3D_Point[] getPointsArray(int oom, RoundingMode rm) {
        return getPoints(oom, rm).values().toArray(new V3D_Point[3]);
    }

    @Override
    public HashMap<Integer, V3D_Point> getPoints(int oom, RoundingMode rm) {
        if (points == null) {
            points = new HashMap<>(3);
            points.put(0, getP(oom, rm));
            points.put(1, getQ(oom, rm));
            points.put(2, getR(oom, rm));
        }
        return points;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A collection of the external edges.
     */
    @Override
    public HashMap<Integer, V3D_LineSegment> getEdges(int oom, RoundingMode rm) {
        if (edges == null) {
            edges = new HashMap<>(3);
            edges.put(0, getPQ(oom, rm));
            edges.put(1, getQR(oom, rm));
            edges.put(2, getRP(oom, rm));
        }
        return edges;
    }

    /**
     * Checks the Axis Aligned Bounding Box.
     *
     * @param pt The point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} if {@code pt} intersects {@code this}.
     */
    @Override
    public boolean intersects(V3D_Point pt, int oom, RoundingMode rm) {
        if (getAABB(oom, rm).intersects(pt, oom, rm)) {
            return intersects0(pt, oom, rm);
        }
        return false;
    }

    /**
     * Does not check the Axis Aligned Bounding Box.
     *
     * @param pt A point to check for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff pt getIntersect.
     */
    public boolean intersects0(V3D_Point pt, int oom, RoundingMode rm) {
        if (getPl(oom, rm).intersects(pt, oom, rm)) {
            return intersectsCoplanar(pt, oom, rm);
        } else {
            return false;
        }
    }

    /**
     * Assumes the {@code p} is on the plane.
     *
     * @param p A point on the plane to check for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff pt getIntersect.
     */
    public boolean intersectsCoplanar(V3D_Point p, int oom, RoundingMode rm) {
        return getPQPl(oom, rm).isOnSameSide(p, getR(oom, rm), oom, rm)
                && getQRPl(oom, rm).isOnSameSide(p, getP(oom, rm), oom, rm)
                && getRPPl(oom, rm).isOnSameSide(p, getQ(oom, rm), oom, rm);
    }

    /**
     * Intersected, but not on the edge.
     *
     * @param pt The point to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return True iff pt is in the triangle and not on the edge.
     */
    //@Override
    public boolean contains00(V3D_Point pt, int oom, RoundingMode rm) {
        return getPQPl(oom, rm).isOnSameSideNotOn(pt, getR(oom, rm), oom, rm)
                && getQRPl(oom, rm).isOnSameSideNotOn(pt, getP(oom, rm), oom, rm)
                && getRPPl(oom, rm).isOnSameSideNotOn(pt, getQ(oom, rm), oom, rm);
    }

    @Override
    public boolean contains(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (getAABB(oom, rm).contains(l, oom, rm)) {
            return contains0(l, oom, rm);
        } else {
            return false;
        }
    }

    /**
     * @param l The line segment to check for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} if {@code this} contains {@code l}.
     */
    //@Override
    public boolean contains0(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (getPl(oom, rm).isOnPlane(l.l, oom, rm)) {
            return contains00(l, oom, rm);
        } else {
            return false;
        }
    }

    public boolean contains00(V3D_LineSegment ls, int oom, RoundingMode rm) {
        return contains00(ls.getP(), oom, rm)
                && contains00(ls.getQ(oom, rm), oom, rm);
    }

    @Override
    public boolean contains(V3D_Area a, int oom, RoundingMode rm) {
        return a.getPoints(oom, rm).values().parallelStream().allMatch(x
                -> contains(x, oom, rm));
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
    public V3D_FiniteGeometry getIntersect(V3D_Line l, int oom,
            RoundingMode rm) {
        V3D_Geometry i = getPl(oom, rm).getIntersect(l, oom, rm);
        if (i == null) {
            return null;
        } else if (i instanceof V3D_Point ip) {
            if (intersectsCoplanar(ip, oom, rm)) {
                return ip;
            } else {
                return null;
            }
        } else {
            return getIntersectCoplanar(l, oom, rm);
        }
    }

    /**
     * Use when {@code l} is coplanar.
     *
     * @param l The line to intersect with assumed to be coplanar.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point or line segment.
     */
    public V3D_FiniteGeometry getIntersectCoplanar(V3D_Line l, int oom,
            RoundingMode rm) {
        /**
         * Get the intersection of the line and each edge of the triangle.
         */
        V3D_FiniteGeometry lpqi = getPQ(oom, rm).getIntersect(l, oom, rm);
        V3D_FiniteGeometry lqri = getQR(oom, rm).getIntersect(l, oom, rm);
        V3D_FiniteGeometry lrpi = getRP(oom, rm).getIntersect(l, oom, rm);
        if (lpqi == null) {
            if (lqri == null) {
                return null; // This should not happen!
            } else {
                if (lrpi == null) {
                    return lqri;
                } else {
                    return V3D_LineSegment.getGeometry((V3D_Point) lqri,
                            (V3D_Point) lrpi, oom, rm);
                }
            }
        } else if (lpqi instanceof V3D_Point lpqip) {
            if (lqri == null) {
                if (lrpi == null) {
                    return lpqi;
                } else if (lrpi instanceof V3D_LineSegment) {
                    return lrpi;
                } else {
                    return V3D_LineSegment.getGeometry(lpqip,
                            (V3D_Point) lrpi, oom, rm);
                }
            } else if (lqri instanceof V3D_Point lqrip) {
                if (lrpi == null) {
                    return V3D_LineSegment.getGeometry(lqrip, lpqip, oom,
                            rm);
                } else if (lrpi instanceof V3D_LineSegment) {
                    return lrpi;
                } else {
                    return getGeometry(lpqip, lqrip, (V3D_Point) lrpi,
                            oom, rm);
                }
            } else {
                return lqri;
            }
        } else {
            return lpqi;
        }
    }

    /**
     * Get the intersection between {@code this} and {@code l}. {@code l} is
     * assumed to be non-coplanar.
     *
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point or null.
     */
    public V3D_Point getIntersectNonCoplanar(V3D_Line l, int oom,
            RoundingMode rm) {
        V3D_Point i = getPl(oom, rm).getIntersectNonParallel(l, oom, rm);
        if (i == null) {
            return i;
        } else if (intersectsCoplanar(i, oom, rm)) {
            return i;
        } else {
            return null;
        }
    }

    /**
     * Get the intersection between {@code this} and {@code r}.
     *
     * @param r The ray to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The geometry of intersection or {@code null}.
     */
    @Override
    public V3D_FiniteGeometry getIntersect(V3D_Ray r, int oom,
            RoundingMode rm) {
        V3D_FiniteGeometry g = getIntersect(r.l, oom, rm);
        if (g == null) {
            return null;
        } else if (g instanceof V3D_Point gp) {
            if (r.isAligned(gp, oom, rm)) {
                return gp;
            } else {
                return null;
            }
        } else {
            V3D_LineSegment ls = (V3D_LineSegment) g;
            V3D_Point lsp = ls.getP();
            V3D_Point lsq = ls.getQ(oom, rm);
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
                    throw new RuntimeException();
                }
            }
        }
    }

    /**
     * Get the intersection between {@code this} and {@code r}. {@code r} is
     * assumed to be non-coplanar.
     *
     * @param r The ray to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The point of intersection or {@code null}.
     */
    @Override
    public V3D_Point getIntersectNonCoplanar(V3D_Ray r, int oom, RoundingMode rm) {
        V3D_Point i = getIntersectNonCoplanar(r.l, oom, rm);
        if (i == null) {
            return null;
        } else if (r.isAligned(i, oom, rm)) {
            return i;
        } else {
            return null;
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
    public V3D_FiniteGeometry getIntersect(V3D_LineSegment l, int oom,
            RoundingMode rm) {
        V3D_FiniteGeometry g = getIntersect(l.l, oom, rm);
        if (g == null) {
            return null;
        } else if (g instanceof V3D_Point gp) {
            if (l.isAligned(gp, oom, rm)) {
                return gp;
            } else {
                return null;
            }
        } else {
            V3D_LineSegment ls = (V3D_LineSegment) g;
            if (ls.isAligned(l.getP(), oom, rm)
                    || ls.isAligned(l.getQ(oom, rm), oom, rm)
                    || l.isAligned(getP(oom, rm), oom, rm)
                    || l.isAligned(getQ(oom, rm), oom, rm)) {
                return l.getIntersectLS(ls, oom, rm);
            } else {
                return null;
            }
        }
    }

    /**
     * Get the intersection between the geometry and the line segment {@code l}
     * which is not coplanar.
     *
     * @param l The line segment to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersectNonCoplanar(V3D_LineSegment l, int oom,
            RoundingMode rm) {
        V3D_Point i = getIntersectNonCoplanar(l.l, oom, rm);
        if (i == null) {
            return null;
        } else if (l.isAligned(i, oom, rm)) {
            return i;
        } else {
            return null;
        }
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
    private V3D_FiniteGeometry getIntersect0(V3D_LineSegment l, V3D_Point pt,
            V3D_Point opt, int oom, RoundingMode rm) {
        V3D_FiniteGeometry lipq = l.getIntersect(getPQ(oom, rm), oom, rm);
        V3D_FiniteGeometry liqr = l.getIntersect(getQR(oom, rm), oom, rm);
        V3D_FiniteGeometry lirp = l.getIntersect(getRP(oom, rm), oom, rm);
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
                        return getGeometry(pt, lirpl.getP(), lirpl.getQ(oom, rm), oom, rm);
                    }
                    if (liqr instanceof V3D_LineSegment liqrp) {
                        return getGeometry(pt, liqrp.getP(), liqrp.getQ(oom, rm), oom, rm);
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
                return getGeometry(pt, lipql.getP(), lipql.getQ(oom, rm), oom, rm);
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
                            return getGeometry(pt, lirpl.getP(), lirpl.getQ(oom, rm), oom, rm);
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
                        return getGeometry(pt, liqrp.getP(), liqrp.getQ(oom, rm), oom, rm);
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
                            return getGeometry(pt, lirpl.getP(), lirpl.getQ(oom, rm), oom, rm);
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
    public V3D_FiniteGeometry getIntersect(V3D_Plane pl, int oom,
            RoundingMode rm) {
        V3D_Geometry pi = pl.getIntersect(getPl(oom, rm), oom, rm);
        if (pi == null) {
            return null;
        } else if (pi instanceof V3D_Plane) {
            return this;
        } else {
            // (pi instanceof V3D_Line)
            V3D_FiniteGeometry gPQ = pl.getIntersect(getPQ(oom, rm), oom, rm);
            if (gPQ == null) {
                V3D_FiniteGeometry gQR = pl.getIntersect(getQR(oom, rm), oom, rm);
                if (gQR == null) {
                    V3D_FiniteGeometry gRP = pl.getIntersect(getRP(oom, rm), oom, rm);
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
//                    V3D_FiniteGeometry gRP = pl.getIntersect(getRP(oom, rm), oom, rm);
//                    if (gRP == null) {
//                        return gQR;
//                    } else if (gRP instanceof V3D_Point gRPp) {
//                        return V3D_LineSegment.getGeometry(gQRl, gRPp, oom, rm);
//                    } else {
//                        return gRP;
//                    }
                } else {
                    V3D_FiniteGeometry gRP = pl.getIntersect(getRP(oom, rm), oom, rm);
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
                V3D_FiniteGeometry gQR = pl.getIntersect(getQR(oom, rm), oom, rm);
                if (gQR == null) {
                    V3D_FiniteGeometry gRP = pl.getIntersect(getRP(oom, rm), oom, rm);
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
     * Calculate and return the intersection between {@code this} and
     * {@code pl}. A question about how to do this:
     * https://stackoverflow.com/questions/3142469/determining-the-intersection-of-a-triangle-and-a-plane
     *
     * @param pl The plane to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code this} and {@code pl}.
     */
    public V3D_FiniteGeometry getIntersect0(V3D_Plane pl, int oom,
            RoundingMode rm) {
        // Get intersection if this were a plane
        //V3D_Geometry pi = pl.getIntersect(this, oom);
        V3D_Geometry pi = pl.getIntersectNonParallel(getPl(oom, rm), oom, rm);
        if (pi == null) {
            return null;
        } else if (pi instanceof V3D_Plane) {
            return this;
        } else {
            // (pi instanceof V3D_Line)
            V3D_FiniteGeometry gPQ = pl.getIntersect(getPQ(oom, rm), oom, rm);
            if (gPQ == null) {
                V3D_FiniteGeometry gQR = pl.getIntersect(getQR(oom, rm), oom, rm);
                if (gQR == null) {
                    V3D_FiniteGeometry gRP = pl.getIntersect(getRP(oom, rm), oom, rm);
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
//                    V3D_FiniteGeometry gRP = pl.getIntersect(getRP(oom, rm), oom, rm);
//                    if (gRP == null) {
//                        return gQR;
//                    } else if (gRP instanceof V3D_Point gRPp) {
//                        return V3D_LineSegment.getGeometry(gQRl, gRPp, oom, rm);
//                    } else {
//                        return gRP;
//                    }
                } else {
                    V3D_FiniteGeometry gRP = pl.getIntersect(getRP(oom, rm), oom, rm);
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
                V3D_FiniteGeometry gQR = pl.getIntersect(getQR(oom, rm), oom, rm);
                if (gQR == null) {
                    V3D_FiniteGeometry gRP = pl.getIntersect(getRP(oom, rm), oom, rm);
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
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V3D_FiniteGeometry getIntersect(V3D_Triangle t, int oom,
            RoundingMode rm) {
        if (getPl(oom, rm).equalsIgnoreOrientation(t.getPl(oom, rm), oom, rm)) {
            return getIntersectCoplanar(t, oom, rm);
        } else {
            return getIntersectNonCoplanar(t, oom, rm);
        }
    }

    /**
     * Computes and returns the intersection between {@code this} and {@code t}.
     * The intersection could be: null, a point, a line segment, a triangle, or
     * a V3D_ConvexArea (with 4, 5, or 6 sides).
     *
     * @param t The triangle intersect with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V3D_FiniteGeometry getIntersectCoplanar(V3D_Triangle t, int oom,
            RoundingMode rm) {
        if (getAABB(oom, rm).intersects(t.getAABB(oom, rm), oom)) {
            /**
             * The two triangles are in the same plane. Get intersections
             * between the triangle edges. If there are none, then return t. If
             * there are some, then in some cases the result is a single
             * triangle, and in others it is a polygon which can be represented
             * as a set of coplanar triangles.
             */
            // Check if vertices intersect
            V3D_Point pttp = t.getP(oom, rm);
            V3D_Point pttq = t.getQ(oom, rm);
            V3D_Point pttr = t.getR(oom, rm);
            boolean pi = intersectsCoplanar(pttp, oom, rm);
            boolean qi = intersectsCoplanar(pttq, oom, rm);
            boolean ri = intersectsCoplanar(pttr, oom, rm);
            if (pi && qi && ri) {
                return t;
            }
            V3D_Point ptp = getP(oom, rm);
            V3D_Point ptq = getQ(oom, rm);
            V3D_Point ptr = getR(oom, rm);
            boolean pit = t.intersectsCoplanar(ptp, oom, rm);
            boolean qit = t.intersectsCoplanar(ptq, oom, rm);
            boolean rit = t.intersectsCoplanar(ptr, oom, rm);
            if (pit && qit && rit) {
                return this;
            }
            V3D_FiniteGeometry gpq = t.getIntersect(getPQ(oom, rm), oom, rm);
            V3D_FiniteGeometry gqr = t.getIntersect(getQR(oom, rm), oom, rm);
            V3D_FiniteGeometry grp = t.getIntersect(getRP(oom, rm), oom, rm);
            if (gpq == null) {
                if (gqr == null) {

                    if (grp == null) {
                        return null;
                    } else if (grp instanceof V3D_Point grpp) {
                        return grpp;
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
                                ls.getQ(oom, rm), oom, rm);
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
                                gpql.getQ(oom, rm), oom, rm);
                    } else {
                        return getGeometry(gpql,
                                (V3D_LineSegment) grp, oom, rm);
                    }
                } else if (gqr instanceof V3D_Point gqrp) {
                    if (grp == null) {
                        if (gpql.intersects(gqrp, oom, rm)) {
                            return gpql;
                        } else {
                            return new V3D_ConvexArea(oom,
                                    rm, getPl(oom, rm).n, gpql.getP(),
                                    gpql.getQ(oom, rm), gqrp);
                        }
                    } else if (grp instanceof V3D_Point grpp) {
                        ArrayList<V3D_Point> pts = new ArrayList<>();
                        pts.add(gpql.getP());
                        pts.add(gpql.getQ(oom, rm));
                        pts.add(gqrp);
                        pts.add(grpp);
                        ArrayList<V3D_Point> pts2 = V3D_Point.getUnique(pts, oom, rm);
                        return switch (pts2.size()) {
                            case 2 ->
                                new V3D_LineSegment(pts2.get(0), pts2.get(1), oom, rm);
                            case 3 ->
                                new V3D_Triangle(pts2.get(0), pts2.get(1), pts2.get(2), oom, rm);
                            default ->
                                new V3D_ConvexArea(oom,
                                rm, getPl(oom, rm).n, gpql.getP(),
                                gpql.getQ(oom, rm), gqrp, grpp);
                        };
                    } else {
                        V3D_LineSegment grpl = (V3D_LineSegment) grp;
                        return V3D_ConvexArea.getGeometry(
                                oom, rm, gpql.getP(),
                                gpql.getQ(oom, rm), gqrp, grpl.getP(),
                                grpl.getQ(oom, rm));
                    }
                } else {
                    V3D_LineSegment gqrl = (V3D_LineSegment) gqr;
                    if (grp == null) {
                        return V3D_ConvexArea.getGeometry(
                                oom, rm, gpql.getP(), gpql.getQ(oom, rm),
                                gqrl.getP(), gqrl.getQ(oom, rm));
                    } else if (grp instanceof V3D_Point grpp) {
                        return V3D_ConvexArea.getGeometry(
                                oom, rm, gpql.getP(),
                                gpql.getQ(oom, rm), gqrl.getP(),
                                gqrl.getQ(oom, rm), grpp);
                    } else {
                        V3D_LineSegment grpl = (V3D_LineSegment) grp;
                        return V3D_ConvexArea.getGeometry(
                                oom, rm, gpql.getP(),
                                gpql.getQ(oom, rm), gqrl.getP(),
                                gqrl.getQ(oom, rm), grpl.getP(),
                                grpl.getQ(oom, rm));
                    }
                }
            }
        } else {
            return null;
        }
    }

    /**
     * Computes and returns the intersection between {@code this} and {@code t}.
     * The intersection could be: null, a point, or a line segment. {@code this}
     * is to be not coplanar with {@code t}.
     *
     * @param t The triangle intersect with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V3D_FiniteGeometry getIntersectNonCoplanar(V3D_Triangle t, int oom,
            RoundingMode rm) {
        if (getAABB(oom, rm).intersects(t.getAABB(oom, rm), oom)) {
            // Triangles are not coplanar.
            V3D_FiniteGeometry i = getIntersect(t.pl, oom - 6, rm);
            if (i == null) {
                return i;
            } else if (i instanceof V3D_Point pt) {
                if (intersects0(pt, oom, rm)) {
                    return pt;
                } else {
                    return null;
                }
            } else {
                V3D_LineSegment il = (V3D_LineSegment) i;
                V3D_FiniteGeometry ti = t.getIntersect(pl, oom - 6, rm);
                if (ti == null) {
                    return ti;
                } else if (ti instanceof V3D_Point pt) {
                    if (t.intersects0(pt, oom, rm)) {
                        return pt;
                    } else {
                        return null;
                    }
                } else {
                    return il.getIntersect((V3D_LineSegment) ti, oom, rm);
                }
            }
        } else {
            return null;
        }
    }

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
        oom -= 6;
        BigRational dx = (pv.getDX(oom, rm).add(qv.getDX(oom, rm))
                .add(rv.getDX(oom, rm))).divide(3);
        BigRational dy = (pv.getDY(oom, rm).add(qv.getDY(oom, rm))
                .add(rv.getDY(oom, rm))).divide(3);
        BigRational dz = (pv.getDZ(oom, rm).add(qv.getDZ(oom, rm))
                .add(rv.getDZ(oom, rm))).divide(3);
        return new V3D_Point(env, offset, new V3D_Vector(dx, dy, dz));
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
        V3D_Point tp = t.getP(oom, rm);
        V3D_Point thisp = getP(oom, rm);
        if (tp.equals(thisp, oom, rm)) {
            V3D_Point tq = t.getQ(oom, rm);
            V3D_Point thisq = getQ(oom, rm);
            if (tq.equals(thisq, oom, rm)) {
                return t.getR(oom, rm).equals(getR(oom, rm), oom, rm);
            } else if (tq.equals(getR(oom, rm), oom, rm)) {
                return t.getR(oom, rm).equals(thisq, oom, rm);
            } else {
                return false;
            }
        } else if (tp.equals(getQ(oom, rm), oom, rm)) {
            V3D_Point tq = t.getQ(oom, rm);
            V3D_Point thisr = getR(oom, rm);
            if (tq.equals(thisr, oom, rm)) {
                return t.getR(oom, rm).equals(thisp, oom, rm);
            } else if (tq.equals(thisp, oom, rm)) {
                return t.getR(oom, rm).equals(thisr, oom, rm);
            } else {
                return false;
            }
        } else if (tp.equals(getR(oom, rm), oom, rm)) {
            V3D_Point tq = t.getQ(oom, rm);
            if (tq.equals(thisp, oom, rm)) {
                return t.getR(oom, rm).equals(getQ(oom, rm), oom, rm);
            } else if (tq.equals(getQ(oom, rm), oom, rm)) {
                return t.getR(oom, rm).equals(thisp, oom, rm);
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
        if (pq != null) {
            pq.translate(v, oom, rm);
        }
        if (qr != null) {
            qr.translate(v, oom, rm);
        }
        if (rp != null) {
            rp.translate(v, oom, rm);
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
                getP(oom, rm).rotate(ray, uv, bd, theta, oom, rm),
                getQ(oom, rm).rotate(ray, uv, bd, theta, oom, rm),
                getR(oom, rm).rotate(ray, uv, bd, theta, oom, rm), oom, rm);
    }

    /**
     * @param pad Padding
     * @return A String representation of this.
     */
    public String toString(String pad) {
        String sb = pad + this.getClass().getSimpleName() + "(\n";
        if (pl != null) {
            sb += pad + " pl=(" + pl.toString(pad + " ") + "),\n";
        }
        sb += pad + " offset=(" + this.offset.toString(pad + " ") + "),\n"
                + pad + " p=(" + this.pv.toString(pad + " ") + "),\n"
                + pad + " q=(" + this.qv.toString(pad + " ") + "),\n"
                + pad + " r=(" + this.rv.toString(pad + " ") + "))";
        return sb;
    }

    /**
     * @param pad Padding
     * @return A simple String representation of this.
     */
    public String toStringSimple(String pad) {
        String sb = pad + this.getClass().getSimpleName() + "(\n";
        if (pl != null) {
            sb += pad + " pl=(" + pl.toStringSimple(pad + " ") + "),\n";
        }
        sb += pad + " offset=(" + this.offset.toStringSimple("") + "),\n"
                + pad + " p=(" + this.pv.toStringSimple("") + "),\n"
                + pad + " q=(" + this.qv.toStringSimple("") + "),\n"
                + pad + " r=(" + this.rv.toStringSimple("") + "))";
        return sb;
    }

    @Override
    public String toString() {
        //return toString("");
        return toStringSimple("");
    }

    /**
     * If pv, q and r are equal then the point is returned.If two of the points
     * are the same, then a line segment is returned.If all points are different
     * then a triangle is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, v)} or
     * {@code new V3D_Triangle(pl, v, rv)}
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
                        if (pq.intersects(r, oom, rm)) {
                            return pq;
                        } else {
                            V3D_LineSegment qr = new V3D_LineSegment(q, r, oom, rm);
                            if (qr.intersects(p, oom, rm)) {
                                return qr;
                            } else {
                                return new V3D_LineSegment(p, r, oom, rm);
                            }
                        }
                    }
                    return new V3D_Triangle(p, q, r, oom, rm);
//                    return new V3D_Triangle(pl.e, V3D_Vector.ZERO,
//                            pl.getVector(pl.e.oom),
//                            v.getVector(pl.e.oom), rv.getVector(pl.e.oom));
                }
            }
        }
    }

    /**
     * Used in intersecting two triangles to give the overall intersection.If
     * l1, l2 and l3 are equal then the line segment is returned.If there are 3
     * unique points then a triangle is returned. If there are 4 or more unique
     * points, then a V3D_ConvexArea is returned.
     *
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param l3 A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, v)} or
     * {@code new V3D_Triangle(pl, v, rv)}
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
            return new V3D_ConvexArea(oom, rm, pl.n, pts);
        }
//       // This way returned polygons.
//       } else if (n == 4) {
//            V3D_Triangle t1;
//            V3D_Triangle t2;
//            // Case: quadrangle (closed polygon with 4 sides)
//            V3D_Point illl2 = (V3D_Point) l1.getIntersect(l2, oom);
//            V3D_Point illl3 = (V3D_Point) l1.getIntersect(l3, oom);
//            V3D_Point il2l3 = (V3D_Point) l2.getIntersect(l3, oom);
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
//            V3D_Point illl2 = (V3D_Point) l1.getIntersect(l2, oom);
//            V3D_Point illl3 = (V3D_Point) l1.getIntersect(l3, oom);
//            V3D_Point il2l3 = (V3D_Point) l2.getIntersect(l3, oom);
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
     * Used in intersecting two triangles to give the overall intersection.If
 there are 3 unique points then a triangle is returned.If there are 4 or
 more unique points, then a V3D_ConvexArea is returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, v)} or
     * {@code new V3D_Triangle(pl, v, rv)}
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_LineSegment l1,
            V3D_LineSegment l2, V3D_Point pt, int oom, RoundingMode rm) {
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
            return new V3D_ConvexArea(oom, rm, pl.n, pts);
        }
    }

    /**
     * Used in intersecting a triangle and a tetrahedron.If there are 3 unique
     * points then a triangle is returned.If there are 4 points, then a
     * V3D_ConvexArea is returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, v)} or
     * {@code new V3D_Triangle(pl, v, rv)}
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
            return new V3D_ConvexArea(oom, rm, pl.n, pts);
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
        V3D_Point pt = (V3D_Point) l1.getIntersect(l2, oom, rm);
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
     * is a side of a triangle and pl is a point.
     *
     * @param l A line segment.
     * @param p1 A point that is either not collinear to l or getIntersect l.
     * @param p2 A point that is either not collinear to l or getIntersect l.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return a triangle for which l is an edge and pl is a vertex.
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_LineSegment l,
            V3D_Point p1, V3D_Point p2, int oom, RoundingMode rm) {
        if (l.intersects(p1, oom, rm)) {
            return getGeometry(l, p2, oom, rm);
        } else {
            return new V3D_Triangle(p1, l.getP(), l.getQ(oom, rm), oom, rm);
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
        if (l.intersects(p, oom, rm)) {
            return l;
        }
        return new V3D_Triangle(p, l.getP(), l.getQ(oom, rm), oom, rm);
    }

    /**
     * For getting the point opposite a side of a triangle given the side.
     *
     * @param l a line segment either equal to one of the edges of this - null
     * null null null null null null null null null null null null null null
     * null null null null null null null null null null null null null null
     * null null null null null     {@link #getPQ(int, java.math.RoundingMode)},
     * {@link #getQR(int, java.math.RoundingMode)} or
     * {@link #getRP(int, java.math.RoundingMode)}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The point of {@code this} that does not intersect with {@code l}.
     */
    public V3D_Point getOpposite(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (getPQ(oom, rm).equals(l, oom, rm)) {
            return getR(oom, rm);
        } else {
            if (getQR(oom, rm).equals(l, oom, rm)) {
                return getP(oom, rm);
            } else {
                return getQ(oom, rm);
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
    @Override
    public BigRational getDistanceSquared(V3D_Point pt, int oom, RoundingMode rm) {
        if (getPl(oom, rm).intersects(pt, oom, rm)) {
            //if (intersectsNonCoplanar(pt, oom, rm)) {
            if (intersects0(pt, oom, rm)) {
                return BigRational.ZERO;
            } else {
                return getDistanceSquaredEdge(pt, oom, rm);
            }
        }
        V3D_Point poi = pl.getPointOfProjectedIntersect(pt, oom, rm);
        if (intersects0(poi, oom, rm)) {
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
        if (getIntersect(l, oom, rm) != null) {
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
        V3D_Point lq = l.getQ(oom, rm);
        if (intersects0(lp, oom, rm)) {
            d2 = BigRational.min(d2, getDistanceSquared(lp, oom, rm));
        }
        if (intersects0(lq, oom, rm)) {
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
        if (getIntersect(t, oom, rm) != null) {
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
    //public static ArrayList<V3D_Point> getPointsArray(V3D_Triangle[] triangles) {
    public static V3D_Point[] getPoints(V3D_Triangle[] triangles, int oom, RoundingMode rm) {
        List<V3D_Point> s = new ArrayList<>();
        for (var t : triangles) {
            s.add(t.getP(oom, rm));
            s.add(t.getQ(oom, rm));
            s.add(t.getR(oom, rm));
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
        V3D_FiniteGeometry i = getIntersect(pl, oom, rm);
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
            if (pl.isOnSameSide(getQ(oom, rm), pt, oom, rm)) {
                poll++;
            }
            if (pl.isOnSameSide(getR(oom, rm), pt, oom, rm)) {
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
            V3D_Point qpt = getQ(oom, rm);
            V3D_Point rpt = getR(oom, rm);
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
     * @param rm The RoundingMode for any rounding.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometry clip(V3D_Triangle t, V3D_Point pt, int oom, RoundingMode rm) {
        V3D_Point tp = t.getP(oom, rm);
        V3D_Point tq = t.getQ(oom, rm);
        V3D_Point tr = t.getR(oom, rm);
        V3D_Vector n = t.getPl(oom, rm).n;
        V3D_Point ppt = new V3D_Point(env, tp.offset.add(n, oom, rm), tp.rel);
        V3D_Plane ppl = new V3D_Plane(tp, tq, ppt, oom, rm);
        V3D_Point qpt = new V3D_Point(env, tq.offset.add(n, oom, rm), tq.rel);
        V3D_Plane qpl = new V3D_Plane(tq, tr, qpt, oom, rm);
        V3D_Point rpt = new V3D_Point(env, tr.offset.add(n, oom, rm), tr.rel);
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
                V3D_ConvexArea c = (V3D_ConvexArea) cppltcqpl;
                return c.clip(rpl, pt, oom, rm);
            }
        } else {
            V3D_ConvexArea c = (V3D_ConvexArea) cppl;
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
                V3D_ConvexArea ccc = (V3D_ConvexArea) cc;
                return ccc.clip(rpl, pt, oom, rm);
            }
        }
    }

    /**
     * @param pl The plane to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if there is an intersection.
     */
    //@Override
    public boolean intersects(V3D_Plane pl, int oom, RoundingMode rm) {
        V3D_Plane tpl = getPl(oom, rm);
        if (pl.isParallel(tpl, oom, rm)) {
            return pl.equals(tpl, oom, rm);
        } else {
            return intersectsNonParallel(pl, oom, rm);
        }
    }

    /**
     * @param pl The plane to test for intersection with which is not parallel.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if there is an intersection.
     */
    public boolean intersectsNonParallel(V3D_Plane pl, int oom, RoundingMode rm) {
        V3D_Line i = pl.getIntersectNonParallel(getPl(oom, rm), oom, rm);
        return intersects(i, oom, rm);
    }

    @Override
    public boolean intersects(V3D_AABB aabb, int oom, RoundingMode rm) {
        /**
         * Test each 2D AABB part of the aabb and at least one point. For
         * rendering things like line segments where a camera focus is the third
         * point and this method is used to identify if there is an intersection
         * with a screen or pixel, then it is best to test for intersection with
         * a point last (or to have another method and not test this at all).
         */
        return intersects(aabb.getl(oom, rm), oom, rm)
                || intersects(aabb.getr(oom, rm), oom, rm)
                || intersects(aabb.gett(oom, rm), oom, rm)
                || intersects(aabb.getb(oom, rm), oom, rm)
                || intersects(aabb.getf(oom, rm), oom, rm)
                || intersects(aabb.geta(oom, rm), oom, rm)
                || aabb.intersects(getP(oom, rm), oom, rm);
//                || aabb.intersects(getQ(oom, rm), oom, rm)
//                || aabb.intersects(getR(oom, rm), oom, rm)
//        return getEdges(oom, rm).values().parallelStream().anyMatch(x
//                -> x.intersects(aabb, oom, rm));
//                || getPQ(oom, rm).intersects(aabb, oom, rm)
//                || getQR(oom, rm).intersects(aabb, oom, rm)
//                || getRP(oom, rm).intersects(aabb, oom, rm);
    }

    //@Override
    public boolean intersects(V3D_AABBX aabbx, int oom, RoundingMode rm) {
        if (intersects(aabbx.getXPl(oom, rm), oom, rm)) {
            return getPQ(oom, rm).intersects(aabbx, oom, rm)
                    || getQR(oom, rm).intersects(aabbx, oom, rm)
                    || getRP(oom, rm).intersects(aabbx, oom, rm);
        } else {
            return false;
        }
    }

    //@Override
    public boolean intersects(V3D_AABBY aabby, int oom, RoundingMode rm) {
        if (intersects(aabby.getYPl(oom, rm), oom, rm)) {
            return getPQ(oom, rm).intersects(aabby, oom, rm)
                    || getQR(oom, rm).intersects(aabby, oom, rm)
                    || getRP(oom, rm).intersects(aabby, oom, rm);
        } else {
            return false;
        }
    }

    //@Override
    public boolean intersects(V3D_AABBZ aabbz, int oom, RoundingMode rm) {
        if (intersects(aabbz.getZPl(oom, rm), oom, rm)) {
            return getPQ(oom, rm).intersects(aabbz, oom, rm)
                    || getQR(oom, rm).intersects(aabbz, oom, rm)
                    || getRP(oom, rm).intersects(aabbz, oom, rm);
        } else {
            return false;
        }
    }

    /**
     * If there is intersection with the Axis Aligned Bounding Boxes, then the
     * intersection is computed, so if that is needed use:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.V3D_Line, int, java.math.RoundingMode)}.
     *
     * @param l The V3D_Line to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this getIntersect with {@code l}
     */
    @Override
    public boolean intersects(V3D_Line l, int oom, RoundingMode rm) {
        if (l.intersects(getAABB(oom, rm), oom, rm)) {
            return getIntersect(l, oom, rm) != null;
        } else {
            return false;
        }
    }

    /**
     * Computes the intersect and tests if it is {@code null}. If the
     * intersection is wanted use:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegment, int, java.math.RoundingMode)}
     *
     * @param l The line segment to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersects(V3D_LineSegment l, int oom, RoundingMode rm) {
        // Compute the intersection and return true if it is not null.
        return getIntersect(l, oom, rm) != null;
//        Profiling revealed that checking the AABB is generally not an optimisation...
//        if (intersects(l.getAABB(oom, rm), oom, rm)
//                || l.intersects(getAABB(oom, rm), oom, rm)) {
//            // Compute the intersection and return true if it is not null.
//            return getIntersect(l, oom, rm) != null;
//        } else {
//            return false;
//        }
    }

    /**
     * *
     * Use when {@code l} is not coplanar. This computes the intersect and tests
     * if it is null. If the intersection is wanted use:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegment, int, java.math.RoundingMode)}
     *
     * @param l The line segment to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if l intersects this.
     */
    //@Override
    public boolean intersectsNonCoplanar(V3D_LineSegment l, int oom, RoundingMode rm) {
        // Compute the intersection and return true if it is not null.
        return getIntersectNonCoplanar(l, oom, rm) != null;
//        Profiling revealed that checking the AABB is generally not an optimisation...
//        if (intersects(l.getAABB(oom, rm), oom, rm)
//                || l.intersects(getAABB(oom, rm), oom, rm)) {
//            // Compute the intersection and return true if it is not null.
//            return getIntersectNonCoplanar(l, oom, rm) != null;
//        } else {
//            return false;
//        }
    }

    /**
     * If the ray starts outside and points away rom {@code this} then return
     * false, otherwise the intersection is computed and tested to see if it is
     * {@code null}. If the intersection is needed use a method to compute it:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.V3D_Ray, int, java.math.RoundingMode)}
     *
     * @param r The ray to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersects(V3D_Ray r, int oom, RoundingMode rm) {
        if (r.isAligned(getP(oom, rm), oom, rm)
                || r.isAligned(getQ(oom, rm), oom, rm)
                || r.isAligned(getR(oom, rm), oom, rm)) {
            return getIntersect(r, oom, rm) != null;
        } else {
            return false;
        }
    }

    /**
     * If the ray starts outside and points away rom {@code this} then return
     * false, otherwise the intersection is computed and tested to see if it is
     * {@code null}. The ray is assumed to not be coplanar with {@code this}.
     *
     * @param r The ray to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersectsNonCoplanar(V3D_Ray r, int oom, RoundingMode rm) {
        if (r.isAligned(getP(oom, rm), oom, rm)
                || r.isAligned(getQ(oom, rm), oom, rm)
                || r.isAligned(getR(oom, rm), oom, rm)) {
            return getIntersectNonCoplanar(r, oom, rm) != null;
        } else {
            return false;
        }
    }

    /**
     * @param t Another triangle to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} intersects {@code t}.
     */
    @Override
    public boolean intersects(V3D_Triangle t, int oom, RoundingMode rm) {
        if (getPl(oom, rm).equalsIgnoreOrientation(t.getPl(oom, rm), oom, rm)) {
            return t.intersectsCoplanar(getP(oom, rm), oom, rm)
                    || t.intersectsCoplanar(getQ(oom, rm), oom, rm)
                    || t.intersectsCoplanar(getR(oom, rm), oom, rm);
        } else {
            return intersectsNonCoplanar(t, oom, rm);
        }
    }

    /**
     * Use when {@code t} is not coplanar.
     *
     * @param t Another triangle to test for intersection. {@code this} and
     * {@code t} are not coplanar.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} intersects {@code t}.
     */
    public boolean intersectsNonCoplanar(V3D_Triangle t, int oom, RoundingMode rm) {
        if (getPl(oom, rm).allOnSameSideNotOn(oom, rm, t.getP(oom, rm),
                t.getQ(oom, rm), t.getR(oom, rm)) //&& intersects(t.getAABB(), epsilon)
                //&& t.intersects(getAABB(), epsilon)
                ) {
            return false;
        } else {
            return t.intersectsNonCoplanar(getPQ(oom, rm), oom, rm)
                    || t.intersectsNonCoplanar(getQR(oom, rm), oom, rm)
                    || t.intersectsNonCoplanar(getRP(oom, rm), oom, rm)
                    || intersectsNonCoplanar(t.getPQ(oom, rm), oom, rm)
                    || intersectsNonCoplanar(t.getQR(oom, rm), oom, rm)
                    || intersectsNonCoplanar(t.getRP(oom, rm), oom, rm)
                    || getIntersectNonCoplanar(t, oom, rm) != null;
        }
    }

    /**
     * @param a An area to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if t intersects this.
     */
    @Override
    public boolean intersects(V3D_Area a, int oom, RoundingMode rm) {
//        Profiling revealed that checking the AABB is generally not an optimisation...
//        if (intersects(a.getAABB(oom, rm), oom, rm)
//                && a.intersects(getAABB(oom, rm), oom, rm)
//                && !a.getPl(oom, rm).allOnSameSide(oom, rm, getP(oom, rm),
//                        getQ(oom, rm), getR(oom, rm))) {
        if (!a.getPl(oom, rm).allOnSameSide(oom, rm, getP(oom, rm),
                getQ(oom, rm), getR(oom, rm))) {
            return a.intersects(getPQ(oom, rm), oom, rm)
                    || a.intersects(getQR(oom, rm), oom, rm)
                    || a.intersects(getRP(oom, rm), oom, rm)
                    || getEdges(oom, rm).values().parallelStream().anyMatch(x
                            -> intersects(x, oom, rm));
        } else {
            return false;
        }
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param ls A line segment to test for intersection.
     * @param faces The faces to test for intersection with pv.
     * @return {@code true} if {@code this} is intersected by {@code pv}.
     */
    public static boolean intersects(int oom, RoundingMode rm,
            V3D_LineSegment ls, Collection<V3D_Area> faces) {
        return faces.parallelStream().anyMatch(x -> x.intersects(ls, oom, rm));
    }
}
