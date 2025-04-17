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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;
import uk.ac.leeds.ccg.v3d.geometry.d.light.V3D_VTriangle_d;

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
public class V3D_Triangle_d extends V3D_Area_d {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the normal vector of the triangle.
     */
    protected V3D_Vector_d normal;

    /**
     * Defines one of the corners of the triangle.
     */
    protected V3D_Vector_d pv;

    /**
     * Defines one of the corners of the triangle.
     */
    protected V3D_Vector_d qv;

    /**
     * Defines one of the corners of the triangle.
     */
    protected V3D_Vector_d rv;

    /**
     * For storing a corner point of the triangle corresponding to {@link #pv}.
     */
    protected V3D_Point_d p;

    /**
     * For storing a corner point of the triangle corresponding to {@link #qv}.
     */
    protected V3D_Point_d q;

    /**
     * For storing a corner point of the triangle corresponding to {@link #rv}.
     */
    protected V3D_Point_d r;

    /**
     * For storing the line segment from {@link #getP()} to {@link #getQ()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    protected V3D_LineSegment_d pq;

    /**
     * For storing the line segment from {@link #getQ()} to {@link #getR()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    protected V3D_LineSegment_d qr;

    /**
     * For storing the line segment from {@link #getR()} to {@link #getP()} for
     * a given Order of Magnitude and RoundingMode precision.
     */
    protected V3D_LineSegment_d rp;

    /**
     * For storing the plane aligning with {@link #pq} in the direction of the
     * plane normal and with a normal orthogonal to the plane normal.
     */
    protected V3D_Plane_d pqpl;

    /**
     * For storing the plane aligning with {@link #qr} in the direction of the
     * plane normal and with a normal orthogonal to the plane normal.
     */
    protected V3D_Plane_d qrpl;

    /**
     * For storing the plane aligning with {@link #rp} in the direction of the
     * plane normal and with a normal orthogonal to the plane normal.
     */
    protected V3D_Plane_d rppl;

//    /**
//     * For storing the midpoint between {@link #getP()} and {@link #getQ()}.
//     */
//    private V3D_Point_d mpq;
//
//    /**
//     * For storing the midpoint between {@link #getQ()} and {@link #getR()}.
//     */
//    private V3D_Point_d mqr;
//
//    /**
//     * For storing the midpoint between {@link #getR()} and {@link #getP()}.
//     */
//    private V3D_Point_d mrp;
//
//    /**
//     * For storing the centroid.
//     */
//    private V3D_Point_d c;
    /**
     * Creates a new triangle.
     *
     * @param t The triangle to copy.
     */
    public V3D_Triangle_d(V3D_Triangle_d t) {
        super(t.env, new V3D_Vector_d(t.offset), t.pl);
        pv = new V3D_Vector_d(t.pv);
        qv = new V3D_Vector_d(t.qv);
        rv = new V3D_Vector_d(t.rv);
        if (t.p != null) {
            p = new V3D_Point_d(t.p);
        }
        if (t.q != null) {
            q = new V3D_Point_d(t.q);
        }
        if (t.r != null) {
            r = new V3D_Point_d(t.r);
        }
        if (t.pq != null) {
            pq = new V3D_LineSegment_d(t.pq);
        }
        if (t.qr != null) {
            qr = new V3D_LineSegment_d(t.qr);
        }
        if (t.rp != null) {
            rp = new V3D_LineSegment_d(t.rp);
        }
    }

    /**
     * Creates a new triangle.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param t The triangle to initialise this from.
     */
    public V3D_Triangle_d(V3D_Environment_d env, V3D_Vector_d offset,
            V3D_VTriangle_d t) {
        this(env, offset,
                new V3D_Vector_d(t.pq.p),
                new V3D_Vector_d(t.pq.q),
                new V3D_Vector_d(t.qr.q));
    }

    /**
     * Creates a new triangle.{@link #offset} is set to
     * {@link V3D_Vector_d#ZERO}.
     *
     * @param env What {@link #env} is set to.
     * @param pv What {@link #pv} is set to.
     * @param qv What {@link #qv} is set to.
     * @param rv What {@link #rv} is set to.
     */
    public V3D_Triangle_d(V3D_Environment_d env, V3D_Vector_d pv,
            V3D_Vector_d qv, V3D_Vector_d rv) {
        this(env, V3D_Vector_d.ZERO, pv, qv, rv);
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
    public V3D_Triangle_d(V3D_Environment_d env, V3D_Vector_d offset,
            V3D_Vector_d pv, V3D_Vector_d qv, V3D_Vector_d rv) {
        super(env, offset, new V3D_Plane_d(env, offset, pv, qv, rv));
        this.pv = pv;
        this.qv = qv;
        this.rv = rv;
        // Debugging code
        if (pv.equals(qv) || pv.equals(rv) || qv.equals(rv)) {
            throw new RuntimeException("pv.equals(qv) || pv.equals(rv) "
                    + "|| qv.equals(rv)");
        }
    }

    /**
     * Creates a new triangle assuming pv, qv and rv are different.
     *
     * @param pl What {@link #pl} is set to. This must be the plane of the
     * triangle.
     * @param offset What {@link #offset} is set to.
     * @param pv What {@link #pv} is set to.
     * @param qv What {@link #qv} is set to.
     * @param rv What {@link #rv} is set to.
     */
    public V3D_Triangle_d(V3D_Plane_d pl, V3D_Vector_d offset,
            V3D_Vector_d pv, V3D_Vector_d qv, V3D_Vector_d rv) {
        super(pl.env, offset, pl);
        this.pv = pv;
        this.qv = qv;
        this.rv = rv;
        // Debugging code
        if (pv.equals(qv) || pv.equals(rv) || qv.equals(rv)) {
            throw new RuntimeException("p.equals(q) || p.equals(r) "
                    + "|| q.equals(r)");
        }
    }

    /**
     * Creates a new triangle.
     *
     * @param l A line segment representing one of the three edges of the
     * triangle.
     * @param r Defines the other point relative to l.offset that defines the
     * triangle.
     */
    public V3D_Triangle_d(V3D_LineSegment_d l, V3D_Vector_d r) {
        this(l.env, new V3D_Vector_d(l.offset), new V3D_Vector_d(l.l.pv),
                l.l.pv.add(l.l.v), new V3D_Vector_d(r));
    }

    /**
     * Creates a new triangle.
     *
     * @param pl What {@link #pl} is set to.
     * @param ls A line segment representing one of the three edges of the
     * triangle.
     * @param pt The other point that defines the triangle.
     */
    public V3D_Triangle_d(V3D_Plane_d pl, V3D_LineSegment_d ls, V3D_Point_d pt) {
        this(pl, new V3D_Vector_d(ls.offset),
                new V3D_Vector_d(ls.l.pv),
                ls.l.pv.add(ls.l.v),
                pt.getVector().subtract(ls.offset));
    }

    /**
     * Creates a new instance.
     *
     * @param p Used to initialise {@link #offset} and {@link #pl}.
     * @param q Used to initialise {@link #qv}.
     * @param r Used to initialise {@link #rv}.
     */
    public V3D_Triangle_d(V3D_Point_d p, V3D_Point_d q, V3D_Point_d r) {
        this(p.env, new V3D_Vector_d(p.offset), new V3D_Vector_d(p.rel),
                q.getVector().subtract(p.offset),
                r.getVector().subtract(p.offset));
    }

    /**
     * Creates a new triangle.
     *
     * @param pl What {@link #pl} is set to.
     * @param p A point.
     * @param q A point.
     * @param r A point.
     */
    public V3D_Triangle_d(V3D_Plane_d pl, V3D_Point_d p,
            V3D_Point_d q, V3D_Point_d r) {
        this(pl, new V3D_Vector_d(p.offset), new V3D_Vector_d(p.rel),
                q.getVector().subtract(p.offset),
                r.getVector().subtract(p.offset));
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
    public V3D_Triangle_d(V3D_Environment_d env, V3D_Vector_d offset,
            V3D_Vector_d pv, V3D_Vector_d qv, V3D_Vector_d rv,
            V3D_Vector_d normal) {
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
     */
    public V3D_Triangle_d(V3D_Point_d pt, V3D_Point_d p, V3D_Point_d q,
            V3D_Point_d r) {
        super(p.env, new V3D_Vector_d(p.offset), null);
        this.pv = new V3D_Vector_d(p.rel);
        this.p = new V3D_Point_d(p);
        this.qv = q.getVector().subtract(p.offset);
        this.q = new V3D_Point_d(q);
        this.rv = r.getVector().subtract(p.offset);
        this.r = new V3D_Point_d(r);
        this.pl = new V3D_Plane_d(pt, p.offset, p.getVector(),
                q.getVector(), r.getVector());
        this.normal = pl.n;
    }

    /**
     * Creates a new instance.
     *
     * @param ls A line segment defining two of the points.
     * @param pt The third point.
     */
    public V3D_Triangle_d(V3D_LineSegment_d ls, V3D_Point_d pt) {
        this(ls.getP(), ls.getQ(), pt);
    }

    /**
     * @return A new point based on {@link #pv} and {@link #offset}.
     */
    public final V3D_Point_d getP() {
        if (p == null) {
            p = new V3D_Point_d(env, offset, pv);
        }
        return p;
    }

    /**
     * @return A new point based on {@link #qv} and {@link #offset}.
     */
    public final V3D_Point_d getQ() {
        if (q == null) {
            q = new V3D_Point_d(env, offset, qv);
        }
        return q;
    }

    /**
     * @return A new point based on {@link #rv} and {@link #offset}.
     */
    public final V3D_Point_d getR() {
        if (r == null) {
            r = new V3D_Point_d(env, offset, rv);
        }
        return r;
    }

    /**
     * For getting the line segment from {@link #getP()} to {@link #getQ()}.
     *
     * @return Line segment from {@link #getP()} to {@link #getQ()}.
     */
    public final V3D_LineSegment_d getPQ() {
        if (pq == null) {
            pq = new V3D_LineSegment_d(getP(), getQ());
        }
        return pq;
    }

    /**
     * For getting the line segment from {@link #getQ()} to {@link #getR()}.
     *
     * @return Line segment from {@link #getQ()} to {@link #getR()}.
     */
    public final V3D_LineSegment_d getQR() {
        if (qr == null) {
            qr = new V3D_LineSegment_d(getQ(), getR());
        }
        return qr;
    }

    /**
     * For getting the line segment from {@link #getR()} to {@link #getP()}.
     *
     * @return Line segment from {@link #getR()} to {@link #getP()}.
     */
    public final V3D_LineSegment_d getRP() {
        if (rp == null) {
            rp = new V3D_LineSegment_d(getR(), getP());
        }
        return rp;
    }

    /**
     * @return {@code qv.subtract(pv)}
     */
    public final V3D_Vector_d getPQV() {
        return qv.subtract(pv);
    }

    /**
     * @return {@code rv.subtract(qv)}
     */
    public final V3D_Vector_d getQRV() {
        return rv.subtract(qv);
    }

    /**
     * @return {@code pv.subtract(rv)}
     */
    public final V3D_Vector_d getRPV() {
        return pv.subtract(rv);
    }

    /**
     * For getting the plane through {@link #pq} in the direction of the normal.
     *
     * @return The plane through {@link #pq} in the direction of the normal.
     */
    public V3D_Plane_d getPQPl() {
        if (pqpl == null) {
            pq = getPQ();
            pqpl = new V3D_Plane_d(pq.getP(), pq.l.v.getCrossProduct(pl.n));
        }
        return pqpl;
    }

    /**
     * For getting the plane through {@link #qr} in the direction of the normal.
     *
     * @return The plane through {@link #qr} in the direction of the normal.
     */
    public V3D_Plane_d getQRPl() {
        if (qrpl == null) {
            qr = getQR();
            qrpl = new V3D_Plane_d(qr.getP(), qr.l.v.getCrossProduct(pl.n));
        }
        return qrpl;
    }

    /**
     * For getting the plane through {@link #rp} in the direction of the normal.
     *
     * @return The plane through {@link #rp} in the direction of the normal.
     */
    public V3D_Plane_d getRPPl() {
        if (rppl == null) {
            rp = getRP();
            rppl = new V3D_Plane_d(rp.getP(), rp.l.v.getCrossProduct(pl.n));
        }
        return rppl;
    }

    @Override
    public V3D_AABB_d getAABB() {
        if (en == null) {
            en = new V3D_AABB_d(getP(), getQ(), getR());
        }
        return en;
    }

    @Override
    public V3D_Point_d[] getPointsArray() {
        return getPoints().values().toArray(new V3D_Point_d[3]);
    }

    @Override
    public HashMap<Integer, V3D_Point_d> getPoints() {
        if (points == null) {
            points = new HashMap<>(3);
            points.put(0, getP());
            points.put(1, getQ());
            points.put(2, getR());
        }
        return points;
    }

    /**
     * @return {@link #pl} initialising it first if it is null.
     */
    @Override
    public V3D_Plane_d getPl() {
        if (pl == null) {
            pl = new V3D_Plane_d(getP(), getQ(), getR());
        }
        return pl;
    }

    /**
     * @return A collection of the external edges.
     */
    @Override
    public HashMap<Integer, V3D_LineSegment_d> getEdges() {
        if (edges == null) {
            edges = new HashMap<>(3);
            edges.put(0, getPQ());
            edges.put(1, getQR());
            edges.put(2, getRP());
        }
        return edges;
    }

    /**
     * Checks the Axis Aligned Bounding Box.
     * 
     * @param pt
     * @param epsilon
     * @return {@code true} if {@code pt} intersects {@code this}.
     */
    @Override
    public boolean intersects(V3D_Point_d pt, double epsilon) {
        if (getAABB().intersects(pt)) {
            return intersects0(pt, epsilon);
        } else {
            return false;
        }
    }

    /**
     * Does not check the Axis Aligned Bounding Box.
     *
     * @param pt The point to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    public boolean intersects0(V3D_Point_d pt, double epsilon) {
        if (getPl().intersects(epsilon, pt)) {
            return intersectsCoplanar(pt, epsilon);
        } else {
            return false;
        }
    }

    /**
     * The point pt aligns with this if it is on the inside of each plane
     * defined triangle edge (with a normal given by the cross product of the
     * triangle normal and the edge line vector) within a tolerance given by
     * epsilon.
     *
     * @param pt The point to check if it is in alignment.
     * @param epsilon The tolerance.
     * @return {@code true} iff pl is aligned with this.
     */
    public boolean intersectsCoplanar(V3D_Point_d pt, double epsilon) {
        return getPQPl().isOnSameSide(pt, getR(), epsilon)
                && getQRPl().isOnSameSide(pt, getP(), epsilon)
                && getRPPl().isOnSameSide(pt, getQ(), epsilon);
    }

    @Override
    public boolean contains(V3D_Point_d pt, double epsilon) {
        if (getAABB().contains(pt)) {
            return contains0(pt, epsilon);
        } else {
            return false;
        }
    }

    /**
     * @param pt The point to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    //@Override
    public boolean contains0(V3D_Point_d pt, double epsilon) {
        if (getPl().intersects(epsilon, pt)) {
            return contains00(pt, epsilon);
        } else {
            return false;
        }
    }

    /**
     * Check if {@code this} contains {@code pt} which is assumed to be on the
     * plane.
     *
     * @param pt The point to check if it is contained.
     * @param epsilon The tolerance.
     * @return {@code true} if {@code this} contains {@code pt}.
     */
    public boolean contains00(V3D_Point_d pt, double epsilon) {
        return getPQPl().isOnSameSideNotOn(pt, getR(), epsilon)
                && getQRPl().isOnSameSideNotOn(pt, getP(), epsilon)
                && getRPPl().isOnSameSideNotOn(pt, getQ(), epsilon);
    }

    @Override
    public boolean contains(V3D_LineSegment_d l, double epsilon) {
        if (getAABB().contains(l)) {
            return contains0(l, epsilon);
        } else {
            return false;
        }
    }

    /**
     * @param l The line segment to check for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} contains {@code l}.
     */
    //@Override
    public boolean contains0(V3D_LineSegment_d l, double epsilon) {
        if (pl.isOnPlane(l.l, epsilon)) {
            return contains00(l, epsilon);
        } else {
            return false;
        }
    }

    /**
     * Check if {@code this} contains {@code l} which is assumed to be on the
     * plane.
     *
     * @param l The line segment to check if it is contained.
     * @param epsilon The tolerance.
     * @return {@code true} if {@code this} contains {@code l}.
     */
    public boolean contains00(V3D_LineSegment_d l, double epsilon) {
        return contains00(l.getP(), epsilon)
                && contains00(l.getQ(), epsilon);
    }

    /**
     * Check if {@code this} contains {@code t} which is assumed to be on the
     * plane.
     *
     * @param t The triangle to check if it is contained.
     * @param epsilon The tolerance.
     * @return {@code true} if {@code this} contains {@code t}.
     */
    public boolean contains00(V3D_Triangle_d t, double epsilon) {
        return contains00(t.getP(), epsilon)
                && contains00(t.getQ(), epsilon)
                && contains00(t.getR(), epsilon);
    }

    @Override
    public double getArea() {
        return getPQV().getCrossProduct(getRPV()).getMagnitude() / 2.0d;
    }

    @Override
    public double getPerimeter() {
        return getPQ().getLength() + getQR().getLength() + getRP().getLength();
    }

    /**
     * @param l The line to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_Line_d l, double epsilon) {
        V3D_Geometry_d i = pl.getIntersect(l, epsilon);
        if (i == null) {
            return null;
        } else if (i instanceof V3D_Point_d ip) {
            if (intersectsCoplanar(ip, epsilon)) {
                return ip;
            } else {
                return null;
            }
        } else {
            /**
             * Get the intersection of the line and each edge of the triangle.
             */
            V3D_FiniteGeometry_d lpqi = getPQ().getIntersect(l, epsilon);
            V3D_FiniteGeometry_d lqri = getQR().getIntersect(l, epsilon);
            V3D_FiniteGeometry_d lrpi = getRP().getIntersect(l, epsilon);
            if (lpqi == null) {
                if (lqri == null) {
                    return null;
                } else {
                    if (lrpi == null) {
                        return lqri;
                    } else {
                        return V3D_LineSegment_d.getGeometry(
                                (V3D_Point_d) lqri, (V3D_Point_d) lrpi,
                                epsilon);
                    }
                }
            } else if (lpqi instanceof V3D_Point_d lpqip) {
                if (lqri == null) {
                    if (lrpi == null) {
                        return lpqi;
                    } else if (lrpi instanceof V3D_LineSegment_d) {
                        return lrpi;
                    } else {
                        return V3D_LineSegment_d.getGeometry(lpqip,
                                (V3D_Point_d) lrpi, epsilon);
                    }
                } else if (lqri instanceof V3D_Point_d lqrip) {
                    if (lrpi == null) {
                        return V3D_LineSegment_d.getGeometry(lqrip, lpqip,
                                epsilon);
                    } else if (lrpi instanceof V3D_LineSegment_d) {
                        return lrpi;
                    } else {
                        return getGeometry(lpqip, lqrip, (V3D_Point_d) lrpi,
                                epsilon);
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
     * Get the intersection between {@code this} and {@code l}. {@code l} is
     * assumed to be non-coplanar.
     *
     * @param l The line to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.v
     * @return The point of intersection or {@code null}.
     */
    public V3D_Point_d getIntersectNonCoplanar(V3D_Line_d l, double epsilon) {
        V3D_Point_d i = getPl().getIntersectNonParallel(l, epsilon);
        if (i == null) {
            return i;
        } else if (intersectsCoplanar(i, epsilon)) {
            return i;
        } else {
            return null;
        }
    }

    /**
     * Get the intersection between the triangle and the ray {@code rv}.
     *
     * @param r The ray to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The geometry of intersection or {@code null}.
     */
    @Override
    public V3D_FiniteGeometry_d getIntersect(V3D_Ray_d r, double epsilon) {
        V3D_FiniteGeometry_d g = getIntersect(r.l, epsilon);
        if (g == null) {
            return null;
        } else if (g instanceof V3D_Point_d gp) {
            if (r.isAligned(gp, epsilon)) {
                return gp;
            } else {
                return null;
            }
        } else {
            V3D_LineSegment_d ls = (V3D_LineSegment_d) g;
            V3D_Point_d lsp = ls.getP();
            V3D_Point_d lsq = ls.getQ();
            if (r.isAligned(lsp, epsilon)) {
                if (r.isAligned(lsq, epsilon)) {
                    return ls;
                } else {
                    return V3D_LineSegment_d.getGeometry(r.l.getP(), lsp, epsilon);
                }
            } else {
                if (r.isAligned(lsq, epsilon)) {
                    return V3D_LineSegment_d.getGeometry(r.l.getP(), lsq, epsilon);
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The point of intersection or {@code null}.
     */
    @Override
    public V3D_Point_d getIntersectNonCoplanar(V3D_Ray_d r, double epsilon) {
        V3D_Point_d i = V3D_Triangle_d.this.getIntersectNonCoplanar(r.l, epsilon);
        if (i == null) {
            return null;
        } else if (r.isAligned(i, epsilon)) {
            return i;
        } else {
            return null;
        }
    }

    /**
     * Get the intersection between the geometry and the line segment {@code l}.
     *
     * @param l The line segment to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_LineSegment_d l,
            double epsilon) {
        V3D_FiniteGeometry_d g = getIntersect(l.l, epsilon);
        if (g == null) {
            return null;
        } else if (g instanceof V3D_Point_d gp) {
            if (l.isAligned(gp, epsilon)) {
                return gp;
            } else {
                return null;
            }
        } else {
            V3D_LineSegment_d ls = (V3D_LineSegment_d) g;
            if (ls.isAligned(l.getP(), epsilon)
                    || ls.isAligned(l.getQ(), epsilon)
                    || l.isAligned(getP(), epsilon)
                    || l.isAligned(getQ(), epsilon)) {
                return l.getIntersectLS(epsilon, ls);
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_Point_d getIntersectNonCoplanar(V3D_LineSegment_d l,
            double epsilon) {
        V3D_Point_d i = V3D_Triangle_d.this.getIntersectNonCoplanar(l.l, epsilon);
        if (i == null) {
            return null;
        } else if (l.isAligned(i, epsilon)) {
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return Either a point or a line segment.
     */
    private V3D_FiniteGeometry_d getIntersect0(V3D_LineSegment_d l,
            V3D_Point_d pt, V3D_Point_d opt, double epsilon) {
        V3D_FiniteGeometry_d lipq = l.getIntersect(getPQ(), epsilon);
        V3D_FiniteGeometry_d liqr = l.getIntersect(getQR(), epsilon);
        V3D_FiniteGeometry_d lirp = l.getIntersect(getRP(), epsilon);
        V3D_Plane_d ptpl = new V3D_Plane_d(pt, l.l.v);
        if (lipq == null) {
            if (liqr == null) {
                if (lirp instanceof V3D_Point_d lirpp) {
                    if (ptpl.isOnSameSide(lirpp, opt, epsilon)) {
                        return V3D_LineSegment_d.getGeometry(pt, lirpp, epsilon);
                    } else {
                        return lirpp;
                    }
                } else {
                    return lirp;
                }
            } else {
                if (lirp == null) {
                    if (liqr instanceof V3D_Point_d liqrp) {
                        if (ptpl.isOnSameSide(liqrp, opt, epsilon)) {
                            return V3D_LineSegment_d.getGeometry(pt, liqrp, epsilon);
                        } else {
                            return liqrp;
                        }
                    } else {
                        return liqr;
                    }
                } else {
                    if (lirp instanceof V3D_LineSegment_d lirpl) {
                        return getGeometry(pt, lirpl.getP(), lirpl.getQ(), epsilon);
                    }
                    if (liqr instanceof V3D_LineSegment_d liqrp) {
                        return getGeometry(pt, liqrp.getP(), liqrp.getQ(), epsilon);
                    }
                    V3D_Point_d lirpp = (V3D_Point_d) lirp;
                    V3D_Point_d liqrp = (V3D_Point_d) liqr;
                    if (ptpl.isOnSameSide(lirpp, opt, epsilon)) {
                        if (ptpl.isOnSameSide(liqrp, opt, epsilon)) {
                            //return getGeometry(pt, lirpp, liqrp);
                            return V3D_LineSegment_d.getGeometry(lirpp, liqrp, epsilon);
                        } else {
                            return V3D_LineSegment_d.getGeometry(pt, lirpp, epsilon);
                        }
                    } else {
                        if (ptpl.isOnSameSide(liqrp, opt, epsilon)) {
                            return V3D_LineSegment_d.getGeometry(pt, liqrp, epsilon);
                        } else {
                            return pt;
                        }
                    }
                }
            }
        } else {
            if (lipq instanceof V3D_LineSegment_d lipql) {
                return getGeometry(pt, lipql.getP(), lipql.getQ(), epsilon);
            } else {
                if (liqr == null) {
                    if (lirp == null) {
                        V3D_Point_d lipqp = (V3D_Point_d) lipq;
                        if (ptpl.isOnSameSide(lipqp, opt, epsilon)) {
                            return V3D_LineSegment_d.getGeometry(pt, lipqp, epsilon);
                        } else {
                            return pt;
                        }
                    } else {
                        if (lirp instanceof V3D_LineSegment_d lirpl) {
                            return getGeometry(pt, lirpl.getP(), lirpl.getQ(), epsilon);
                        }
                        V3D_Point_d lipqp = (V3D_Point_d) lipq;
                        V3D_Point_d lirpp = (V3D_Point_d) lirp;
                        if (ptpl.isOnSameSide(lipqp, opt, epsilon)) {
                            if (ptpl.isOnSameSide(lirpp, opt, epsilon)) {
                                return getGeometry(pt, lirpp, lipqp, epsilon);
                            } else {
                                return V3D_LineSegment_d.getGeometry(pt, lipqp, epsilon);
                            }
                        } else {
                            if (ptpl.isOnSameSide(lirpp, opt, epsilon)) {
                                return V3D_LineSegment_d.getGeometry(pt, lirpp, epsilon);
                            } else {
                                return pt;
                            }
                        }
                    }
                } else {
                    if (liqr instanceof V3D_LineSegment_d liqrp) {
                        return getGeometry(pt, liqrp.getP(), liqrp.getQ(), epsilon);
                    }
                    if (lirp == null) {
                        V3D_Point_d lipqp = (V3D_Point_d) lipq;
                        V3D_Point_d liqrp = (V3D_Point_d) liqr;
                        if (ptpl.isOnSameSide(lipqp, opt, epsilon)) {
                            if (ptpl.isOnSameSide(liqrp, opt, epsilon)) {
                                return getGeometry(pt, liqrp, lipqp, epsilon);
                            } else {
                                return V3D_LineSegment_d.getGeometry(pt, lipqp, epsilon);
                            }
                        } else {
                            if (ptpl.isOnSameSide(liqrp, opt, epsilon)) {
                                return V3D_LineSegment_d.getGeometry(pt, liqrp, epsilon);
                            } else {
                                return pt;
                            }
                        }
                    } else {
                        if (lirp instanceof V3D_LineSegment_d lirpl) {
                            return getGeometry(pt, lirpl.getP(), lirpl.getQ(), epsilon);
                        }
                        V3D_Point_d lipqp = (V3D_Point_d) lipq;
                        V3D_Point_d liqrp = (V3D_Point_d) liqr;
                        V3D_Point_d lirpp = (V3D_Point_d) lirp;
                        if (ptpl.isOnSameSide(lipqp, opt, epsilon)) {
                            if (ptpl.isOnSameSide(liqrp, opt, epsilon)) {
                                if (ptpl.isOnSameSide(lirpp, opt, epsilon)) {
                                    throw new RuntimeException("Issue with Triangle-Traingle intersection.");
                                } else {
                                    return getGeometry(pt, liqrp, lipqp, epsilon);
                                }
                            } else {
                                if (ptpl.isOnSameSide(lirpp, opt, epsilon)) {
                                    return getGeometry(pt, lirpp, lipqp, epsilon);
                                } else {
                                    return V3D_LineSegment_d.getGeometry(pt, lipqp, epsilon);
                                }
                            }
                        } else {
                            if (ptpl.isOnSameSide(liqrp, opt, epsilon)) {
                                if (ptpl.isOnSameSide(lirpp, opt, epsilon)) {
                                    return getGeometry(pt, liqrp, lirpp, epsilon);
                                } else {
                                    return V3D_LineSegment_d.getGeometry(pt, liqrp, epsilon);
                                }
                            } else {
                                if (ptpl.isOnSameSide(lirpp, opt, epsilon)) {
                                    return V3D_LineSegment_d.getGeometry(pt, lirpp, epsilon);
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code pl}.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_Plane_d pl,
            double epsilon) {
        V3D_Geometry_d pi = pl.getIntersect(getPl(), epsilon);
        if (pi == null) {
            return null;
        } else if (pi instanceof V3D_Plane_d) {
            return this;
        } else {
            // (pi instanceof V3D_Line)
            V3D_FiniteGeometry_d gPQ = pl.getIntersect(getPQ(), epsilon);
            if (gPQ == null) {
                V3D_FiniteGeometry_d gQR = pl.getIntersect(getQR(),
                        epsilon);
                if (gQR == null) {
                    V3D_FiniteGeometry_d gRP = pl.getIntersect(getRP(),
                            epsilon);
                    if (gRP == null) {
                        return null;
                    } else {
                        return gRP;
                    }
                } else if (gQR instanceof V3D_LineSegment_d) {
                    /**
                     * Logically for a triangle there would be non null
                     * intersections with the other edges, but there could be
                     * rounding error.
                     */
                    return gQR;
//                    V3D_FiniteGeometry_d gRP = pl.getIntersect(getRP(),
//                        epsilon);
//                    if (gRP == null) {
//                        return gQR;
//                    } else if (gRP instanceof V3D_Point_d gRPp) {
//                        return V3D_LineSegment_d.getGeometry(gQRl, 
//                                gRPp, epsilon);
//                    } else {
//                        return gRP;
//                    }
                } else {
                    V3D_FiniteGeometry_d gRP = pl.getIntersect(getRP(),
                            epsilon);
                    if (gRP == null) {
                        return gQR;
                    } else if (gRP instanceof V3D_LineSegment_d) {
                        return gRP;
//                        return V3D_LineSegment_d.getGeometry(
//                                gRPl, (V3D_Point_d) gQR,  epsilon);
                    } else {
                        return V3D_LineSegment_d.getGeometry(
                                (V3D_Point_d) gQR, (V3D_Point_d) gRP, epsilon);
                    }
                }
            } else if (gPQ instanceof V3D_LineSegment_d) {
                return gPQ;
            } else {
                V3D_FiniteGeometry_d gQR = pl.getIntersect(getQR(),
                        epsilon);
                if (gQR == null) {
                    V3D_FiniteGeometry_d gRP = pl.getIntersect(getRP(),
                            epsilon);
                    if (gRP == null) {
                        return gPQ;
                    } else if (gRP instanceof V3D_LineSegment_d) {
                        return gRP;
                    } else {
                        return V3D_LineSegment_d.getGeometry(
                                (V3D_Point_d) gPQ, (V3D_Point_d) gRP,
                                epsilon);
                    }
                } else if (gQR instanceof V3D_LineSegment_d) {
                    return gQR;
                } else {
                    return V3D_LineSegment_d.getGeometry(
                            (V3D_Point_d) gPQ, (V3D_Point_d) gQR, epsilon);
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_Triangle_d t, double epsilon) {
        if (pl.equalsIgnoreOrientation(t.pl, epsilon)) {
            return getIntersectCoplanar(t, epsilon);
        } else {
            return getIntersectNonCoplanar(t, epsilon);
        }
    }

    /**
     * Computes and returns the intersection between {@code this} and {@code t}.
     * The intersection could be: null, a point, a line segment, a triangle, or
     * a V3D_ConvexHullCoplanar (with 4, 5, or 6 sides). {@code this} is assumed
     * to be coplanar with {@code t}.
     *
     * @param t The triangle intersect with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V3D_FiniteGeometry_d getIntersectCoplanar(V3D_Triangle_d t, double epsilon) {
        if (getAABB().intersects(t.getAABB())) {
            /**
             * The two triangles are in the same plane. Get intersections
             * between the triangle edges. If there are none, then return t. If
             * there are some, then in some cases the result is a single
             * triangle, and in others it is a polygon which can be represented
             * as a set of coplanar triangles.
             */
            // Check if vertices intersect
            V3D_Point_d pttp = t.getP();
            V3D_Point_d pttq = t.getQ();
            V3D_Point_d pttr = t.getR();
            boolean pi = intersects(pttp, epsilon);
            boolean qi = intersects(pttq, epsilon);
            boolean ri = intersects(pttr, epsilon);
            if (pi && qi && ri) {
                return t;
            }
            V3D_Point_d ptp = getP();
            V3D_Point_d ptq = getQ();
            V3D_Point_d ptr = getR();
            boolean pit = t.intersects(ptp, epsilon);
            boolean qit = t.intersects(ptq, epsilon);
            boolean rit = t.intersects(ptr, epsilon);
            if (pit && qit && rit) {
                return this;
            }
            V3D_FiniteGeometry_d gpq = t.getIntersect(getPQ(), epsilon);
            V3D_FiniteGeometry_d gqr = t.getIntersect(getQR(), epsilon);
            V3D_FiniteGeometry_d grp = t.getIntersect(getRP(), epsilon);
            if (gpq == null) {
                if (gqr == null) {

                    if (grp == null) {
                        return null;
                    } else if (grp instanceof V3D_Point_d) {
                        return grp;
                    } else {
                        if (qi) {
                            return getGeometry((V3D_LineSegment_d) grp, pttq, epsilon);
                        } else {
                            return grp;
                        }
                    }

                } else if (gqr instanceof V3D_Point_d gqrp) {
                    if (grp == null) {
                        return gqr;
                    } else if (grp instanceof V3D_Point_d grpp) {
                        return V3D_LineSegment_d.getGeometry(
                                gqrp, grpp, epsilon);
                    } else {
                        V3D_LineSegment_d ls = (V3D_LineSegment_d) grp;
                        return getGeometry(gqrp, ls.getP(), ls.getQ(), epsilon);
                    }
                } else {

                    V3D_LineSegment_d gqrl = (V3D_LineSegment_d) gqr;
                    if (grp == null) {

                        if (pi) {
                            return getGeometry(gqrl, pttp, epsilon);
                        } else {
                            return gqr;
                        }

                    } else if (grp instanceof V3D_Point_d grpp) {
                        V3D_LineSegment_d ls = (V3D_LineSegment_d) gqr;
                        return getGeometry(grpp, ls.getP(),
                                ls.getQ(), epsilon);
                    } else {
                        return getGeometry(pl, (V3D_LineSegment_d) gqr,
                                (V3D_LineSegment_d) grp, epsilon);
                    }
                }
            } else if (gpq instanceof V3D_Point_d gpqp) {
                if (gqr == null) {
                    if (grp == null) {
                        return gpq;
                    } else if (grp instanceof V3D_Point_d grpp) {
                        return V3D_LineSegment_d.getGeometry(
                                gpqp, grpp, epsilon);
                    } else {
                        V3D_LineSegment_d ls = (V3D_LineSegment_d) grp;
                        return getGeometry(gpqp, ls.getP(),
                                ls.getQ(), epsilon);
                    }
                } else if (gqr instanceof V3D_Point_d gqrp) {
                    if (grp == null) {
                        return gqr;
                    } else if (grp instanceof V3D_Point_d grpp) {
                        return getGeometry(gpqp, gqrp, grpp, epsilon);
                    } else {
                        return getGeometry(pl, (V3D_LineSegment_d) grp,
                                gqrp, gpqp, epsilon);
                    }
                } else {
                    V3D_LineSegment_d ls = (V3D_LineSegment_d) gqr;
                    if (grp == null) {
                        return getGeometry(ls, gpqp, epsilon);
                    } else if (grp instanceof V3D_Point_d grpp) {
                        return getGeometry(pl, ls, grpp, gpqp, epsilon);
                    } else {
                        return getGeometry(ls, (V3D_LineSegment_d) grp,
                                gpqp, epsilon);
                    }
                }
            } else {
                V3D_LineSegment_d gpql = (V3D_LineSegment_d) gpq;
                if (gqr == null) {
                    if (grp == null) {

                        if (ri) {
                            return getGeometry(gpql, pttr, epsilon);
                        } else {
                            return gpq;
                        }

                    } else if (grp instanceof V3D_Point_d grpp) {
                        return getGeometry(grpp, gpql.getP(), gpql.getQ(),
                                epsilon);
                    } else {
                        return getGeometry(pl, gpql,
                                (V3D_LineSegment_d) grp, epsilon);
                    }
                } else if (gqr instanceof V3D_Point_d gqrp) {
                    if (grp == null) {
                        if (gpql.intersects(gqrp, epsilon)) {
                            return gpql;
                        } else {
                            return new V3D_ConvexArea_d(epsilon,
                                    pl.n, gpql.getP(),
                                    gpql.getQ(), gqrp);
                        }
                    } else if (grp instanceof V3D_Point_d grpp) {
                        HashMap<Integer, V3D_Point_d> pts = new HashMap<>(4);
                        pts.put(0, gpql.getP());
                        pts.put(1, gpql.getQ());
                        pts.put(2, gqrp);
                        pts.put(3, grpp);
                        ArrayList<V3D_Point_d> pts2 = V3D_Point_d.getUnique(pts, epsilon);
                        return switch (pts2.size()) {
                            case 2 ->
                                new V3D_LineSegment_d(pts2.get(0), pts2.get(1));
                            case 3 ->
                                new V3D_Triangle_d(pts2.get(0), pts2.get(1), pts2.get(2));
                            default ->
                                new V3D_ConvexArea_d(epsilon,
                                pl.n, gpql.getP(),
                                gpql.getQ(), gqrp, grpp);
                        };
                    } else {
                        V3D_LineSegment_d grpl = (V3D_LineSegment_d) grp;
                        return V3D_ConvexArea_d.getGeometry(
                                epsilon, gpql.getP(),
                                gpql.getQ(), gqrp, grpl.getP(),
                                grpl.getQ());
                    }
                } else {
                    V3D_LineSegment_d gqrl = (V3D_LineSegment_d) gqr;
                    if (grp == null) {
                        return V3D_ConvexArea_d.getGeometry(
                                epsilon,
                                gpql.getP(), gpql.getQ(),
                                gqrl.getP(), gqrl.getQ());
                    } else if (grp instanceof V3D_Point_d grpp) {
                        return V3D_ConvexArea_d.getGeometry(
                                epsilon, gpql.getP(),
                                gpql.getQ(), gqrl.getP(),
                                gqrl.getQ(), grpp);
                    } else {
                        V3D_LineSegment_d grpl = (V3D_LineSegment_d) grp;
                        return V3D_ConvexArea_d.getGeometry(
                                epsilon, gpql.getP(),
                                gpql.getQ(), gqrl.getP(),
                                gqrl.getQ(), grpl.getP(),
                                grpl.getQ());
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V3D_FiniteGeometry_d getIntersectNonCoplanar(V3D_Triangle_d t, double epsilon) {
        if (getAABB().intersects(t.getAABB())) {
            // Triangles are not coplanar.
            V3D_FiniteGeometry_d i = getIntersect(t.pl, epsilon);
            if (i == null) {
                return i;
            } else if (i instanceof V3D_Point_d pt) {
                if (intersectsCoplanar(pt, epsilon)) {
                    return pt;
                } else {
                    return null;
                }
            } else {
                V3D_LineSegment_d il = (V3D_LineSegment_d) i;
                V3D_FiniteGeometry_d ti = t.getIntersect(pl, epsilon);
                if (ti == null) {
                    return ti;
                } else if (ti instanceof V3D_Point_d pt) {
                    if (t.intersectsCoplanar(pt, epsilon)) {
                        return pt;
                    } else {
                        return null;
                    }
                } else {
                    // il and ti are collinear
//                        il.getIntersect((V3D_LineSegment_d) ti,
//                                epsilon);
                    return il.getIntersect0((V3D_LineSegment_d) ti,
                            epsilon);
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
     * @return The centroid point.
     */
    public V3D_Point_d getCentroid() {
        double dx = (pv.dx + qv.dx + rv.dx) / 3d;
        double dy = (pv.dy + qv.dy + rv.dy) / 3d;
        double dz = (pv.dz + qv.dz + rv.dz) / 3d;
        return new V3D_Point_d(env, offset, new V3D_Vector_d(dx, dy, dz));
    }

    /**
     * Test if two triangles are equal. Two triangles are equal if they have 3
     * coincident points, so even if the order is different and one is clockwise
     * and the other anticlockwise, or one faces the other way.
     *
     * @param t The other triangle to test for equality.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is equal to {@code t}.
     */
    public boolean equals(V3D_Triangle_d t, double epsilon) {
        V3D_Point_d tp = t.getP();
        V3D_Point_d thisp = getP();
        if (tp.equals(thisp, epsilon)) {
            V3D_Point_d tq = t.getQ();
            V3D_Point_d thisq = getQ();
            if (tq.equals(thisq, epsilon)) {
                return t.getR().equals(getR(), epsilon);
            } else if (tq.equals(getR(), epsilon)) {
                return t.getR().equals(thisq, epsilon);
            } else {
                return false;
            }
        } else if (tp.equals(getQ(), epsilon)) {
            V3D_Point_d tq = t.getQ();
            V3D_Point_d thisr = getR();
            if (tq.equals(thisr, epsilon)) {
                return t.getR().equals(thisp, epsilon);
            } else if (tq.equals(thisp, epsilon)) {
                return t.getR().equals(thisr, epsilon);
            } else {
                return false;
            }
        } else if (tp.equals(getR(), epsilon)) {
            V3D_Point_d tq = t.getQ();
            if (tq.equals(thisp, epsilon)) {
                return t.getR().equals(getQ(), epsilon);
            } else if (tq.equals(getQ(), epsilon)) {
                return t.getR().equals(thisp, epsilon);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void translate(V3D_Vector_d v) {
        super.translate(v);
        if (pq != null) {
            pq.translate(v);
        }
        if (qr != null) {
            qr.translate(v);
        }
        if (rp != null) {
            rp.translate(v);
        }
        if (pqpl != null) {
            pqpl.translate(v);
        }
        if (qrpl != null) {
            qrpl.translate(v);
        }
        if (rppl != null) {
            rppl.translate(v);
        }
    }

    @Override
    public V3D_Triangle_d rotate(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V3D_Triangle_d(this);
        } else {
            return rotateN(ray, uv, theta, epsilon);
        }
    }

    @Override
    public V3D_Triangle_d rotateN(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        return new V3D_Triangle_d(
                getP().rotate(ray, uv, theta, epsilon),
                getQ().rotate(ray, uv, theta, epsilon),
                getR().rotate(ray, uv, theta, epsilon));
    }

    /**
     * @param pad Padding
     * @return A String representation of this.
     */
    public String toString(String pad) {
        String res = pad + this.getClass().getSimpleName() + "(\n";
        if (pl != null) {
            res += pad + " pl=(" + pl.toString(pad + " ") + "),\n";
        }
        res += pad + " offset=(" + this.offset.toString(pad + " ") + "),\n"
                + pad + " p=(" + this.pv.toString(" ") + "),\n"
                + pad + " q=(" + this.qv.toString(" ") + "),\n"
                + pad + " r=(" + this.rv.toString(" ") + "))";
        return res;
    }

    /**
     * @param pad Padding
     * @return A simple String representation of this.
     */
    public String toStringSimple(String pad) {
        String res = pad + this.getClass().getSimpleName() + "(\n";
        if (pl != null) {
            res += pad + " pl=(" + pl.toStringSimple(pad + " ") + "),\n";
        }
        res += pad + " offset=(" + this.offset.toStringSimple("") + "),\n"
                + pad + " p=(" + this.pv.toStringSimple("") + "),\n"
                + pad + " q=(" + this.qv.toStringSimple("") + "),\n"
                + pad + " r=(" + this.rv.toStringSimple("") + "))";
        return res;
    }

    @Override
    public String toString() {
        //return toString("");
        return toStringSimple("");
    }

    /**
     * If p, q and r are equal then the point is returned. If two of the points
     * are the same, then a line segment is returned. If all points are
     * different then if they are collinear a line segment is returned,
     * otherwise a triangle is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return Either a point, line segment or a triangle.
     */
    public static V3D_FiniteGeometry_d getGeometry(V3D_Point_d p,
            V3D_Point_d q, V3D_Point_d r, double epsilon) {
        if (p.equals(q)) {
            return V3D_LineSegment_d.getGeometry(p, r, epsilon);
        } else {
            if (q.equals(r)) {
                return V3D_LineSegment_d.getGeometry(q, p, epsilon);
            } else {
                if (r.equals(p)) {
                    return V3D_LineSegment_d.getGeometry(r, q, epsilon);
                } else {
                    if (V3D_Line_d.isCollinear(epsilon, p, q, r)) {
                        //return V3D_LineSegment_d.getGeometry(pv, qv, rv, epsilon);
                        V3D_LineSegment_d pq = new V3D_LineSegment_d(p, q);
                        if (pq.intersects(r, epsilon)) {
                            return pq;
                        } else {
                            V3D_LineSegment_d qr = new V3D_LineSegment_d(q, r);
                            if (qr.intersects(p, epsilon)) {
                                return qr;
                            } else {
                                return new V3D_LineSegment_d(p, r);
                            }
                        }
                    }
                    return new V3D_Triangle_d(p, q, r);
                }
            }
        }
    }

    /**
     * Useful in calculating the intersection of two triangles.If there are 3
     * unique points then a triangle is returned. If there are 4 or more unique
     * points, then a V3D_ConvexHullCoplanar is returned.
     *
     * @param l1 A line segment.
     * @param l2 A line segment.
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, qv)} or
     * {@code new V3D_Triangle(pl, qv, rv)}
     */
    protected static V3D_FiniteGeometry_d getGeometry(
            V3D_LineSegment_d l1, V3D_LineSegment_d l2,
            V3D_Point_d pt, double epsilon) {
        V3D_Point_d l1p = l1.getP();
        V3D_Point_d l1q = l1.getQ();
        V3D_Point_d l2p = l2.getP();
        V3D_Point_d l2q = l2.getQ();
        ArrayList<V3D_Point_d> points;
        {
            HashMap<Integer, V3D_Point_d> pts = new HashMap<>(5);
            pts.put(0, l1p);
            pts.put(1, l1q);
            pts.put(2, l2p);
            pts.put(3, l2q);
            pts.put(4, pt);
            points = V3D_Point_d.getUnique(pts, epsilon);
        }
        int n = points.size();
        if (n == 2) {
            return l1;
        } else if (n == 3) {
            Iterator<V3D_Point_d> ite = points.iterator();
            return getGeometry(ite.next(), ite.next(), ite.next(), epsilon);
        } else {
            V3D_Point_d[] pts = new V3D_Point_d[points.size()];
            int i = 0;
            for (var p : points) {
                pts[i] = p;
                i++;
            }
            V3D_Plane_d pl = new V3D_Plane_d(pts[0], pts[1], pts[2]);
            return new V3D_ConvexArea_d(epsilon, pl.n, pts);
        }
    }

//    /**
//     * Used in intersecting a triangle and a tetrahedron. If there are 3 unique
//     * points then a triangle is returned. If there are 4 points, then a
//     * V3D_ConvexHullCoplanar is returned.
//     *
//     * @param l1 A line segment.
//     * @param l2 A line segment.
//     * @param epsilon The tolerance within which two vectors are regarded as
//     * equal.
//     * @return either {@code pl} or {@code new V3D_LineSegment(pl, qv)} or
//     * {@code new V3D_Triangle(pl, qv, rv)}
//     */
//    protected static V3D_FiniteGeometry_d getGeometry2(
//            V3D_LineSegment_d l1, V3D_LineSegment_d l2, double epsilon) {
//        V3D_Point_d l1p = l1.getP();
//        V3D_Point_d l1q = l1.getQ();
//        V3D_Point_d l2p = l2.getP();
//        V3D_Point_d l2q = l2.getQ();
//        ArrayList<V3D_Point_d> points;
//        {
//            List<V3D_Point_d> pts = new ArrayList<>();
//            pts.add(l1p);
//            pts.add(l1q);
//            pts.add(l2p);
//            pts.add(l2q);
//            points = V3D_Point_d.getUnique(pts, epsilon);
//        }
//        int n = points.size();
//        switch (n) {
//            case 2:
//                return l1;
//            case 3:
//                Iterator<V3D_Point_d> ite = points.iterator();
//                return getGeometry(ite.next(), ite.next(), ite.next(), epsilon);
//            default:
//                V3D_Point_d[] pts = new V3D_Point_d[points.size()];
//                int i = 0;
//                for (var pv : points) {
//                    pts[i] = pv;
//                    i++;
//                }
//                V3D_Plane_d pl = new V3D_Plane_d(pts[0], pts[1], pts[2]);
//                return new V3D_ConvexArea_d(pl.n, epsilon, pts);
//        }
//    }
    /**
     * Useful in calculating the intersection of two triangles.
     *
     * @param pl
     * @param ab A line segment and triangle edge.
     * @param cd A line segment and triangle edge.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return a triangle for which l1 and l2 are edges
     */
    protected static V3D_FiniteGeometry_d getGeometry(V3D_Plane_d pl,
            V3D_LineSegment_d ab, V3D_LineSegment_d cd,
            double epsilon) {
        V3D_Point_d pt = (V3D_Point_d) ab.getIntersect(cd, epsilon);
        V3D_Point_d abp = ab.getP();
        V3D_Point_d cdp = cd.getP();
        if (pt == null) {
            V3D_Triangle_d t = new V3D_Triangle_d(pl, cd, abp);
            return new V3D_ConvexArea_d(epsilon, t.pl.getN(),
                    abp, cdp, ab.getQ(), cd.getQ());
        } else {
            if (abp.equals(pt, epsilon)) {
                if (cdp.equals(pt, epsilon)) {
                    return new V3D_Triangle_d(pt, ab.getQ(), cd.getQ());
                } else {
                    return new V3D_Triangle_d(pt, ab.getQ(), cdp);
                }
            } else {
                if (cdp.equals(pt, epsilon)) {
                    return new V3D_Triangle_d(pt, abp, cd.getQ());
                } else {
                    return new V3D_Triangle_d(pt, abp, cdp);
                }
            }
        }
    }

    /**
     * Useful in calculating the intersection of two triangles.
     *
     * @param pl
     * @param l A line segment.
     * @param a A point that is either not collinear to l or intersects l.
     * @param b A point that is either not collinear to l or intersects l.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return a triangle for which l is an edge and pl is a vertex.
     */
    protected static V3D_FiniteGeometry_d getGeometry(V3D_Plane_d pl,
            V3D_LineSegment_d l, V3D_Point_d a, V3D_Point_d b,
            double epsilon) {
        if (l.intersects(a, epsilon)) {
            return getGeometry(l, b, epsilon);
        } else {
            return new V3D_Triangle_d(pl, a, l.getP(), l.getQ());
        }
    }

    /**
     * Useful in calculating the intersection of two triangles.
     *
     * @param l A line segment.
     * @param p A point that is not collinear to l.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return a triangle for which l is an edge and pl is a vertex.
     */
    protected static V3D_FiniteGeometry_d getGeometry(
            V3D_LineSegment_d l, V3D_Point_d p, double epsilon) {
        if (l.intersects(p, epsilon)) {
            return l;
        }
        return new V3D_Triangle_d(p, l.getP(), l.getQ());
    }

    /**
     * For getting the point opposite a side of a triangle given the side.
     *
     * @param l a line segment either equal to one of the edges of this: null
     * null null null null null null null null null null null null null null
     * null null null null null null null null null null null null null null
     * null null null null null null null null null null null null null     {@link #getPQ()},
     * {@link #getQR()} or {@link #getRP()}.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The point of {@code this} that does not intersect with {@code l}.
     */
    public V3D_Point_d getOpposite(V3D_LineSegment_d l, double epsilon) {
        if (getPQ().equalsIgnoreDirection(epsilon, l)) {
            return getR();
        } else {
            if (getQR().equalsIgnoreDirection(epsilon, l)) {
                return getP();
            } else {
                return getQ();
            }
        }
    }

    /**
     * Get the minimum distance to {@code pv}.
     *
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance squared to {@code pv}.
     */
    public double getDistance(V3D_Point_d pt, double epsilon) {
        return Math.sqrt(getDistanceSquared(pt, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code pt}.
     *
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance squared to {@code pv}.
     */
    public double getDistanceSquared(V3D_Point_d pt, double epsilon) {
        if (pl.intersects(epsilon, pt)) {
            //if (intersectsNonCoplanar(pt)) {
            if (intersectsCoplanar(pt, epsilon)) {
                return 0d;
            } else {
                return getDistanceSquaredEdge(pt, epsilon);
            }
        }
        V3D_Point_d poi = pl.getPointOfProjectedIntersect(pt, epsilon);
        if (intersectsCoplanar(poi, epsilon)) {
            return poi.getDistanceSquared(pt);
        } else {
            return getDistanceSquaredEdge(pt, epsilon);
        }
    }

    /**
     * Get the minimum distance squared to {@code pt} from the perimeter.
     *
     * @param pt A point.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The distance squared between pt and the nearest edge of this.
     */
    public double getDistanceSquaredEdge(V3D_Point_d pt, double epsilon) {
        double pqd2 = getPQ().getDistanceSquared(pt, epsilon);
        double qrd2 = getQR().getDistanceSquared(pt, epsilon);
        double rpd2 = getRP().getDistanceSquared(pt, epsilon);
        return Math.min(pqd2, Math.min(qrd2, rpd2));
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param l A line.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V3D_Line_d l, double epsilon) {
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
    public double getDistanceSquared(V3D_Line_d l, double epsilon) {
        double dpq2 = getPQ().getDistanceSquared(l, epsilon);
        double dqr2 = getQR().getDistanceSquared(l, epsilon);
        double drp2 = getRP().getDistanceSquared(l, epsilon);
        return Math.min(dpq2, Math.min(dqr2, drp2));
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V3D_LineSegment_d l, double epsilon) {
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
    public double getDistanceSquared(V3D_LineSegment_d l, double epsilon) {
        if (getIntersect(l, epsilon) != null) {
            return 0d;
        }
        double dlpq2 = l.getDistanceSquared(getPQ(), epsilon);
        double dlqr2 = l.getDistanceSquared(getQR(), epsilon);
        double dlrp2 = l.getDistanceSquared(getRP(), epsilon);
        double d2 = Math.min(dlpq2, Math.min(dlqr2, dlrp2));
        /**
         * For any end points of l that are aligned with this, calculate the
         * distances as these could be the minimum.
         */
        V3D_Point_d lp = l.getP();
        V3D_Point_d lq = l.getQ();
        if (intersectsCoplanar(lp, epsilon)) {
            d2 = Math.min(d2, getDistanceSquared(lp, epsilon));
        }
        if (intersectsCoplanar(lq, epsilon)) {
            d2 = Math.min(d2, getDistanceSquared(lq, epsilon));
        }
        return d2;
    }

    /**
     * Get the minimum distance to {@code pl}.
     *
     * @param pl A plane.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code pv}.
     */
    public double getDistance(V3D_Plane_d pl, double epsilon) {
        return Math.sqrt(getDistanceSquared(pl, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code pl}.
     *
     * @param pl A plane.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code pv}.
     */
    public double getDistanceSquared(V3D_Plane_d pl, double epsilon) {
        double dplpq2 = pl.getDistanceSquared(getPQ(), epsilon);
        double dplqr2 = pl.getDistanceSquared(getQR(), epsilon);
//        double dplrp2 = pl.getDistanceSquared(getRP());
//        return Math_BigRational.min(dplpq2, dplqr2, dplrp2);
        return Math.min(dplpq2, dplqr2);
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A triangle.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code t}.
     */
    public double getDistance(V3D_Triangle_d t, double epsilon) {
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
    public double getDistanceSquared(V3D_Triangle_d t, double epsilon) {
        if (getIntersect(t, epsilon) != null) {
            return 0d;
        }
        double dtpq2 = t.getDistanceSquared(getPQ(), epsilon);
        double dtqr2 = t.getDistanceSquared(getQR(), epsilon);
        double dtrp2 = t.getDistanceSquared(getRP(), epsilon);
        return Math.min(dtpq2, Math.min(dtqr2, dtrp2));
//        double dpq2 = getDistanceSquared(t.getPQ(), epsilon);
//        double dqr2 = getDistanceSquared(t.getQR(), epsilon);
//        double drp2 = getDistanceSquared(t.getRP(), epsilon);
//        double d2 = Math.min(dtpq2, Math.min(dtqr2, Math.min(dtrp2, Math.min(dpq2,
//                Math.min(dqr2, drp2)))));
    }

    /**
     * For retrieving a Set of points that are the corners of the triangles.
     *
     * @param triangles The input.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A Set of points that are the corners of the triangles.
     */
    //public static ArrayList<V3D_Point> getPointsArray(V3D_Triangle[] triangles) {
    public static V3D_Point_d[] getPoints(V3D_Triangle_d[] triangles,
            double epsilon) {
//        HashMap<Integer, V3D_Point_d> s = new HashMap<>(3 * triangles.length);
//        for (var t : triangles) {
//            s.put(s.size(), t.getP());
//            s.put(s.size(), t.getQ());
//            s.put(s.size(), t.getR());
//        }
//        ArrayList<V3D_Point_d> points = V3D_Point_d.getUnique(s, epsilon);
//        return points.toArray(V3D_Point_d[]::new);
        List<V3D_Point_d> s = new ArrayList<>();
        for (var t : triangles) {
            s.add(t.getP());
            s.add(t.getQ());
            s.add(t.getR());
        }
        ArrayList<V3D_Point_d> points = V3D_Point_d.getUnique(s, epsilon);
        return points.toArray(V3D_Point_d[]::new);
    }

    /**
     * Clips this using pl and returns the part that is on the same side as pt.
     *
     * @param pl The plane that clips.
     * @param pt A point that is used to return the side of the clipped
     * triangle.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometry_d clip(V3D_Plane_d pl, V3D_Point_d pt,
            double epsilon) {
        V3D_FiniteGeometry_d i = getIntersect(pl, epsilon);
        V3D_Point_d ppt = this.pl.getP();
        if (i == null) {
            if (pl.isOnSameSide(ppt, pt, epsilon)) {
                return this;
            } else {
                return null;
            }
        } else if (i instanceof V3D_Point_d ip) {
            /**
             * If at least two points of the triangle are on the same side of pl
             * as pt, then return this, otherwise return ip. As the calculation
             * of i is perhaps imprecise, then simply testing if ip equals one
             * of the triangle corner points and then testing another point to
             * see if it that is on the same side as pt might not work out
             * right!
             */
            int poll = 0;
            if (pl.isOnSameSide(ppt, pt, epsilon)) {
                poll++;
            }
            if (pl.isOnSameSide(getQ(), pt, epsilon)) {
                poll++;
            }
            if (pl.isOnSameSide(getR(), pt, epsilon)) {
                poll++;
            }
            if (poll > 1) {
                return this;
            } else {
                return ip;
            }
        } else {
            // i instanceof V3D_LineSegment
            V3D_LineSegment_d il = (V3D_LineSegment_d) i;
            V3D_Point_d qpt = getQ();
            V3D_Point_d rpt = getR();
            if (pl.isOnSameSide(ppt, pt, epsilon)) {
                if (pl.isOnSameSide(qpt, pt, epsilon)) {
                    if (pl.isOnSameSide(rpt, pt, epsilon)) {
                        return this;
                    } else {
                        //return getGeometry(il, getPQ(), epsilon);
                        return V3D_ConvexArea_d.getGeometry(epsilon,
                                il.getP(), il.getQ(), getP(), getQ());
                    }
                } else {
                    if (pl.isOnSameSide(rpt, pt, epsilon)) {
                        //return getGeometry(il, getRP(), epsilon);
                        return V3D_ConvexArea_d.getGeometry(epsilon,
                                il.getP(), il.getQ(), getR(), getP());
                    } else {
                        return getGeometry(il, ppt, epsilon);
                    }
                }
            } else {
                if (pl.isOnSameSide(qpt, pt, epsilon)) {
                    if (pl.isOnSameSide(rpt, pt, epsilon)) {
                        //return getGeometry(il, getQR(), epsilon);
                        return V3D_ConvexArea_d.getGeometry(epsilon,
                                il.getP(), il.getQ(), getQ(), getR());
                    } else {
                        return getGeometry(il, qpt, epsilon);
                    }
                } else {
                    if (pl.isOnSameSide(rpt, pt, epsilon)) {
                        return getGeometry(il, rpt, epsilon);
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return null, the whole or a part of this.
     */
    public V3D_FiniteGeometry_d clip(V3D_Triangle_d t,
            V3D_Point_d pt, double epsilon) {
        V3D_Point_d tp = t.getP();
        V3D_Point_d tq = t.getQ();
        V3D_Point_d tr = t.getR();
        V3D_Vector_d n = t.pl.n;
        V3D_Point_d ppt = new V3D_Point_d(env, tp.offset.add(n), tp.rel);
        V3D_Plane_d ppl = new V3D_Plane_d(tp, tq, ppt);
        V3D_Point_d qpt = new V3D_Point_d(env, tq.offset.add(n), tq.rel);
        V3D_Plane_d qpl = new V3D_Plane_d(tq, tr, qpt);
        V3D_Point_d rpt = new V3D_Point_d(env, tr.offset.add(n), tr.rel);
        V3D_Plane_d rpl = new V3D_Plane_d(tr, tp, rpt);
        V3D_FiniteGeometry_d cppl = clip(ppl, pt, epsilon);
        if (cppl == null) {
            return null;
        } else if (cppl instanceof V3D_Point_d) {
            return cppl;
        } else if (cppl instanceof V3D_LineSegment_d cppll) {
            V3D_FiniteGeometry_d cppllcqpl = cppll.clip(qpl, pt, epsilon);
            if (cppllcqpl == null) {
                return null;
            } else if (cppllcqpl instanceof V3D_Point_d cppllcqplp) {
                return getGeometry(cppll, cppllcqplp, epsilon);
                //return cppllcqpl;
            } else {
                return ((V3D_LineSegment_d) cppllcqpl).clip(rpl, pt, epsilon);
            }
        } else if (cppl instanceof V3D_Triangle_d cpplt) {
            V3D_FiniteGeometry_d cppltcqpl = cpplt.clip(qpl, pt, epsilon);
            if (cppltcqpl == null) {
                return null;
            } else if (cppltcqpl instanceof V3D_Point_d) {
                return cppltcqpl;
            } else if (cppltcqpl instanceof V3D_LineSegment_d cppltcqpll) {
                return cppltcqpll.clip(rpl, pt, epsilon);
            } else if (cppltcqpl instanceof V3D_Triangle_d cppltcqplt) {
                return cppltcqplt.clip(rpl, pt, epsilon);
            } else {
                V3D_ConvexArea_d c = (V3D_ConvexArea_d) cppltcqpl;
                return c.clip(rpl, pt, epsilon);
            }
        } else {
            V3D_ConvexArea_d c = (V3D_ConvexArea_d) cppl;
            V3D_FiniteGeometry_d cc = c.clip(qpl, pt, epsilon);
            if (cc == null) {
                return cc;
            } else if (cc instanceof V3D_Point_d) {
                return cc;
            } else if (cc instanceof V3D_LineSegment_d cppll) {
                V3D_FiniteGeometry_d cccqpl = cppll.clip(qpl, pt, epsilon);
                if (cccqpl == null) {
                    return null;
                } else if (cccqpl instanceof V3D_Point_d) {
                    return cccqpl;
                } else {
                    return ((V3D_LineSegment_d) cccqpl).clip(rpl, pt, epsilon);
                }
            } else if (cc instanceof V3D_Triangle_d ccct) {
                return ccct.clip(rpl, pt, epsilon);
            } else {
                V3D_ConvexArea_d ccc = (V3D_ConvexArea_d) cc;
                return ccc.clip(rpl, pt, epsilon);
            }
        }
    }

    //@Override
    public boolean intersects(V3D_Plane_d pl, double epsilon) {
        V3D_Plane_d tpl = getPl();
        if (pl.isParallel(tpl, epsilon)) {
            return pl.equals(tpl, epsilon);
        } else {
            return intersectsNonParallel(pl, epsilon);
        }
    }

    /**
     * @param pl The plane to test for intersection with which is not parallel.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if there is an intersection.
     */
    public boolean intersectsNonParallel(V3D_Plane_d pl, double epsilon) {
        V3D_Line_d i = pl.getIntersectNonParallel(getPl(), epsilon);
        return intersects(i, epsilon);
    }
            
        //    return getIntersect(pl, epsilon) != null;

    @Override
    public boolean intersects(V3D_AABB_d aabb, double epsilon) {
        /**
         * Test each 2D AABB part of the aabb and at least one point.
         * For rendering things like line segments where a camera focus is the 
         * third point and this method is used to identify if there is an 
         * intersection with a screen or pixel, then it is best to test for 
         * intersection with a point last (or to have another method and not 
         * test this at all).  
         */
        return intersects(aabb.getl(), epsilon)
                || intersects(aabb.getr(), epsilon)
                || intersects(aabb.gett(), epsilon)
                || intersects(aabb.getb(), epsilon)
                || intersects(aabb.getf(), epsilon)
                || intersects(aabb.geta(), epsilon)
                || aabb.intersects(getP());
//                || aabb.intersects(getQ())
//                || aabb.intersects(getR())
//                || getEdges().values().parallelStream().anyMatch(x
//                -> x.intersects(aabb, epsilon));
//                || getPQ().intersects(aabb, epsilon)
//                || getQR().intersects(aabb, epsilon)
//                || getRP().intersects(aabb, epsilon);
    }

    //@Override
    public boolean intersects(V3D_AABBX_d aabbx, double epsilon) {
        if (intersects(aabbx.getXPl(), epsilon)) {
            return getPQ().intersects(aabbx, epsilon)
                    || getQR().intersects(aabbx, epsilon)
                    || getRP().intersects(aabbx, epsilon);
        } else {
            return false;
        }
    }

    //@Override
    public boolean intersects(V3D_AABBY_d aabby, double epsilon) {
        if (intersects(aabby.getYPl(), epsilon)) {
            return getPQ().intersects(aabby, epsilon)
                    || getQR().intersects(aabby, epsilon)
                    || getRP().intersects(aabby, epsilon);
        } else {
            return false;
        }
    }

    //@Override
    public boolean intersects(V3D_AABBZ_d aabbz, double epsilon) {
        if (intersects(aabbz.getZPl(), epsilon)) {
            return getPQ().intersects(aabbz, epsilon)
                    || getQR().intersects(aabbz, epsilon)
                    || getRP().intersects(aabbz, epsilon);
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this getIntersect with {@code l}
     */
    @Override
    public boolean intersects(V3D_Line_d l, double epsilon) {
        if (l.intersects(getAABB(), epsilon)) {
            return getIntersect(l, epsilon) != null;
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this getIntersect with {@code l}
     */
    //@Override
    public boolean intersectsNonCoplanar(V3D_Line_d l, double epsilon) {
        if (l.intersects(getAABB(), epsilon)) {
            return V3D_Triangle_d.this.getIntersectNonCoplanar(l, epsilon) != null;
        } else {
            return false;
        }
    }

    /**
     * Computes the intersect and tests if it is {@code null}. If the
     * intersection is wanted use:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineSegment_d, int, java.math.RoundingMode)}
     *
     * @param l The line segment to test if it intersects.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersects(V3D_LineSegment_d l, double epsilon) {
        // Compute the intersection and return true if it is not null.
        return getIntersect(l, epsilon) != null;
//        Profiling revealed that checking the AABB is generally not an optimisation...
//        if (intersects(l.getAABB(), epsilon)
//                || l.intersects(getAABB(), epsilon)) {
//            // Compute the intersection and return true if it is not null.
//            return getIntersect(l, epsilon) != null;
//        } else {
//            return false;
//        }
    }

    /**
     * Use when {@code l} is not coplanar. This computes the intersect and
     * tests if it is null. If the intersection is wanted use:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineSegment_d, int, java.math.RoundingMode)}
     *
     * @param l The line segment to test if it intersects.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if l intersects this.
     */
    //@Override
    public boolean intersectsNonCoplanar(V3D_LineSegment_d l, double epsilon) {
        // Compute the intersection and return true if it is not null.
        return V3D_Triangle_d.this.getIntersectNonCoplanar(l, epsilon) != null;
//        Profiling revealed that checking the AABB is generally not an optimisation...
//        if (intersects(l.getAABB(), epsilon)
//                || l.intersects(getAABB(), epsilon)) {
//            // Compute the intersection and return true if it is not null.
//            return getIntersectNonCoplanar(l, epsilon) != null;
//        } else {
//            return false;
//        }
    }

    /**
     * If the ray starts outside and points away rom {@code this} then return
     * false, otherwise the intersection is computed and tested to see if it is
     * {@code null}. If the intersection is needed use a method to compute it:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.d.V3D_Ray_d, double)}
     *
     * @param r The ray to test if it intersects.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersects(V3D_Ray_d r, double epsilon) {
        if (r.isAligned(getP(), epsilon)
                || r.isAligned(getQ(), epsilon)
                || r.isAligned(getR(), epsilon)) {
            return getIntersect(r, epsilon) != null;
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if l intersects this.
     */
    //@Override
    public boolean intersectsNonCoplanar(V3D_Ray_d r, double epsilon) {
        if (r.isAligned(getP(), epsilon)
                || r.isAligned(getQ(), epsilon)
                || r.isAligned(getR(), epsilon)) {
            return V3D_Triangle_d.this.getIntersectNonCoplanar(r, epsilon) != null;
        } else {
            return false;
        }
    }

    /**
     * @param t Another triangle to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} intersects {@code t}.
     */
    @Override
    public boolean intersects(V3D_Triangle_d t, double epsilon) {
        if (getPl().equalsIgnoreOrientation(t.getPl(), epsilon)) {
            return t.intersectsCoplanar(getP(), epsilon)
                    || t.intersectsCoplanar(getQ(), epsilon)
                    || t.intersectsCoplanar(getR(), epsilon);
        } else {
            return intersectsNonCoplanar(t, epsilon);
        }
    }

    /**
     * Use when {@code t} is not coplanar.
     * 
     * @param t Another triangle to test for intersection. {@code this} and
     * {@code t} are not coplanar.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} intersects {@code t}.
     */
    //@Override
    public boolean intersectsNonCoplanar(V3D_Triangle_d t, double epsilon) {
        if (getPl().allOnSameSideNotOn(epsilon, t.getP(),
                t.getQ(), t.getR()) //&& intersects(t.getAABB(), epsilon)
                //&& t.intersects(getAABB(), epsilon)
                ) {
            return false;
        } else {
            return t.intersectsNonCoplanar(getPQ(), epsilon)
                    || t.intersectsNonCoplanar(getQR(), epsilon)
                    || t.intersectsNonCoplanar(getRP(), epsilon)
                    || intersectsNonCoplanar(t.getPQ(), epsilon)
                    || intersectsNonCoplanar(t.getQR(), epsilon)
                    || intersectsNonCoplanar(t.getRP(), epsilon)
                    || getIntersectNonCoplanar(t, epsilon) != null;
        }
    }

    /**
     * @param a Another triangle to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} intersects {@code a}.
     */
    @Override
    public boolean intersects(V3D_Area_d a, double epsilon) {
//        Profiling revealed that checking the AABB is generally not an optimisation...
//        if (intersects(a.getAABB(), epsilon)
//                && a.intersects(getAABB(), epsilon)
//                && !a.pl.allOnSameSideNotOn(epsilon, getP(), getQ(),
//                        getR())) {
        if (!a.pl.allOnSameSideNotOn(epsilon, getP(), getQ(),
                        getR())) {
            return a.intersects(getPQ(), epsilon)
                    || a.intersects(getQR(), epsilon)
                    || a.intersects(getRP(), epsilon)
                    || a.getEdges().values().parallelStream().anyMatch(x
                            -> intersects(x, epsilon));
        } else {
            return false;
        }
    }

    /**
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param ls A line segment to test for intersection.
     * @param faces The faces to test for intersection with pv.
     * @return {@code true} if {@code this} is intersected by {@code pv}.
     */
    public static boolean intersects(double epsilon,
            V3D_LineSegment_d ls, Collection<V3D_Area_d> faces) {
        return faces.parallelStream().anyMatch(x -> x.intersects(ls, epsilon));
    }

//    /**
//     * Intersected, but not on the edge.
//     *
//     * @param pt The point to intersect with.
//     * @param epsilon The tolerance within which two vectors are regarded as
//     * equal.
//     * @return True iff pt is in the triangle and not on the edge.
//     */
//    @Override
//    public boolean contains(V3D_Point_d pt, double epsilon) {
//        if (intersects(pt, epsilon)) {

////            return !(getPQ().intersects(pt, epsilon)
////                    || getQR().intersects(pt, epsilon)
////                    || getRP().intersects(pt, epsilon));
//            return !getEdges().values().parallelStream().anyMatch(x
//                    -> x.intersects(pt, epsilon));
//        }
//        return false;
//    }
//
//    /**
//     * @param ls The line segments to test for containment.
//     * @param epsilon The tolerance within which two vectors are regarded as
//     * equal.
//     * @return True iff ls is contained in the triangle.
//     */
//    @Override
//    public boolean contains(V3D_LineSegment_d ls, double epsilon) {
//        return contains(ls.getP(), epsilon)
//                && contains(ls.getQ(), epsilon);
//    }
//
//    /**
//     * Identify if this contains triangle.
//     *
//     * @param t The triangle to test for containment.
//     * @param epsilon The tolerance within which two vectors are regarded as
//     * equal.
//     * @return {@code true} iff {@code this} is intersected by {@code t}.
//     */
//    @Override
//    public boolean contains(V3D_Triangle_d t, double epsilon) {
////        return contains(t.getP(), epsilon)
////                && contains(t.getQ(), epsilon)
////                && contains(t.getR(), epsilon);
//        return t.getPoints().values().parallelStream().allMatch(x
//                -> contains(x, epsilon));
//    }
}
