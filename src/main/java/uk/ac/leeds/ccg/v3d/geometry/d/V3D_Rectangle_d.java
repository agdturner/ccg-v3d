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

import java.util.Arrays;
import java.util.HashMap;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

/**
 * For representing and processing rectangles in 3D. A rectangle is a right
 * angled quadrilateral. The four corners are the points
 * {@link #p}, {@link #q}, {@link #r} and {@link #s}. The following depicts a
 * rectangle {@code
 * q  *-----* r
 *    |   / |
 *    |  /  |
 *    | /   |
 * p  *-----* s
 * }
 * The angles PQR, QRS, RSP, SPQ are all 90 degrees or Pi/2 radians.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Rectangle_d extends V3D_Area_d {

    private static final long serialVersionUID = 1L;

    /**
     * For storing a triangle that makes up the rectangle.
     */
    protected final V3D_Triangle_d pqr;

    /**
     * For storing a triangle that makes up half the rectangle.
     */
    protected final V3D_Triangle_d rsp;

    /**
     * For storing the convex hull
     */
    protected V3D_ConvexArea_d convexHull;

    /**
     * Create a new instance.
     *
     * @param r The rectangle to clone.
     */
    public V3D_Rectangle_d(V3D_Rectangle_d r) {
        this(r.getP(), r.getQ(), r.getR(), r.getS());
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p The bottom left corner of the rectangle.
     * @param q The top left corner of the rectangle.
     * @param r The top right corner of the rectangle.
     * @param s The bottom right corner of the rectangle.
     * @throws java.lang.RuntimeException iff the points do not define a
     * rectangle.
     */
    public V3D_Rectangle_d(V3D_Environment_d env, V3D_Vector_d offset,
            V3D_Vector_d p, V3D_Vector_d q, V3D_Vector_d r,
            V3D_Vector_d s) {
        super(env, offset, new V3D_Plane_d(env, offset, p, q, r));
        rsp = new V3D_Triangle_d(pl, offset, r, s, p);
        pqr = new V3D_Triangle_d(pl, offset, p, q, r);
    }

    /**
     * Creates a new instance.
     *
     * @param p Used to initialise {@link #offset}, {@link #pqr} and
     * {@link #rsp}.
     * @param q Used to initialise {@link #pqr} and {@link #rsp}.
     * @param r Used to initialise {@link #pqr} and {@link #rsp}.
     * @param s Used to initialise {@link #rsp}.
     */
    public V3D_Rectangle_d(V3D_Point_d p, V3D_Point_d q,
            V3D_Point_d r, V3D_Point_d s) {
        this(new V3D_Plane_d(p, q, r), p, q, r, s);
    }

    /**
     * Creates a new instance.
     *
     * @param pl Used to initialise the planes.
     * @param p Used to initialise {@link #offset}, {@link #pqr} and
     * {@link #rsp}.
     * @param q Used to initialise {@link #pqr} and {@link #rsp}.
     * @param r Used to initialise {@link #pqr} and {@link #rsp}.
     * @param s Used to initialise {@link #rsp}.
     */
    public V3D_Rectangle_d(V3D_Plane_d pl, V3D_Point_d p,
            V3D_Point_d q, V3D_Point_d r, V3D_Point_d s) {
        super(pl.env, p.offset, pl);
        V3D_Point_d qn = new V3D_Point_d(q);
        qn.setOffset(p.offset);
        V3D_Point_d rn = new V3D_Point_d(r);
        rn.setOffset(p.offset);
        V3D_Point_d sn = new V3D_Point_d(s);
        sn.setOffset(p.offset);
        rsp = new V3D_Triangle_d(pl, this.offset, rn.rel, sn.rel, p.rel);
        pqr = new V3D_Triangle_d(pl, this.offset, p.rel, qn.rel, rn.rel);
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
                + pad + "pqr=" + getPQR().toString(pad) + ",\n"
                + pad + "rsp=" + getRSP().toString(pad);
    }

    @Override
    protected String toStringFieldsSimple(String pad) {
        return "\n" + super.toStringFieldsSimple(pad) + ",\n"
                + pad + "pqr=" + getPQR().toStringSimple(pad) + ",\n"
                + pad + "rsp=" + getRSP().toStringSimple(pad);
    }

    @Override
    public V3D_Point_d[] getPointsArray() {
        return getPoints().values().toArray(new V3D_Point_d[4]);
    }

    @Override
    public HashMap<Integer, V3D_Point_d> getPoints() {
        if (points == null) {
            points = new HashMap<>(4);
            points.put(0, getP());
            points.put(1, getQ());
            points.put(2, getR());
            points.put(3, getS());
        }
        return points;
    }

    @Override
    public HashMap<Integer, V3D_LineSegment_d> getEdges() {
        if (edges == null) {
            edges = new HashMap<>(4);
            edges.put(0, pqr.getPQ());
            edges.put(1, pqr.getQR());
            edges.put(2, rsp.getPQ());
            edges.put(3, rsp.getQR());
        }
        return edges;
    }

    /**
     * @return {@link #pqr}.
     */
    public V3D_Triangle_d getPQR() {
        return pqr;
    }

    /**
     * @return {@link #rsp}.
     */
    public V3D_Triangle_d getRSP() {
        return rsp;
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point_d getP() {
        return getPQR().getP();
    }

    /**
     * @return {@link #q} with {@link #offset} applied.
     */
    public V3D_Point_d getQ() {
        return getPQR().getQ();
    }

    /**
     * @return {@link #r} with {@link #offset} applied.
     */
    public V3D_Point_d getR() {
        return getPQR().getR();
    }

    /**
     * @return {@link #s} with {@link #offset} applied.
     */
    public V3D_Point_d getS() {
        return getRSP().getQ();
    }

    @Override
    public V3D_AABB_d getAABB() {
        if (en == null) {
            en = rsp.getAABB().union(pqr.getAABB());
        }
        return en;
    }

    /**
     * @param pt The point to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    @Override
    public boolean intersects(V3D_Point_d pt, double epsilon) {
        if (pqr.intersects(pt, epsilon)) {
            return true;
        } else {
            return rsp.intersects(pt, epsilon);
        }
    }

    /**
     * @return The line segment from {@link #r} to {@link #s}.
     */
    protected V3D_LineSegment_d getRS() {
        return getRSP().getPQ();
    }

    /**
     * @return The line segment from {@link #s} to {@link #p}.
     */
    protected V3D_LineSegment_d getSP() {
        return getRSP().getQR();
    }

    /**
     * @return The plane of the rectangle from {@link #getPQR()}.
     */
    @Override
    public V3D_Plane_d getPl() {
        return pqr.pl;
    }

    /**
     * @param l The line to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or line segment.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_Line_d l,
            double epsilon) {
        if (getPl().getIntersect(l, epsilon) != null) {
            V3D_FiniteGeometry_d pqri = pqr.getIntersect(l, epsilon);
            V3D_FiniteGeometry_d rspi = rsp.getIntersect(l, epsilon);
            return join(epsilon, pqri, rspi);
        } else {
            return null;
        }
    }

    private V3D_FiniteGeometry_d join(double epsilon,
            V3D_FiniteGeometry_d pqri, V3D_FiniteGeometry_d rspi) {
        if (pqri == null) {
            if (rspi == null) {
                return null;
            } else {
                return rspi;
            }
        } else if (pqri instanceof V3D_LineSegment_d pqril) {
            if (rspi == null) {
                return pqri;
            } else if (rspi instanceof V3D_LineSegment_d rspil) {
                return V3D_LineSegment_d.getGeometry(epsilon, pqril, rspil);
            } else {
                return pqri;
            }
        } else {
            // pqri instanceof V3D_Point_d
            if (rspi == null) {
                return pqri;
            } else if (rspi instanceof V3D_LineSegment_d) {
                return rspi;
            } else {
                return pqri;
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
    public V3D_FiniteGeometry_d getIntersect(V3D_LineSegment_d l,
            double epsilon) {
        if (getPl().getIntersect(l, epsilon) != null) {
            V3D_FiniteGeometry_d pqri = pqr.getIntersect(l, epsilon);
            V3D_FiniteGeometry_d rspi = rsp.getIntersect(l, epsilon);
            return join(epsilon, pqri, rspi);
        } else {
            return null;
        }
    }

    @Override
    public double getPerimeter() {
        return (pqr.getPQ().getLength() + pqr.getQR().getLength()) * 2d;
    }

    @Override
    public double getArea() {
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
    public double getDistance(V3D_Point_d p, double epsilon) {
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
    public double getDistanceSquared(V3D_Point_d pt, double epsilon) {
        double d1 = pqr.getDistanceSquared(pt, epsilon);
        double d2 = rsp.getDistanceSquared(pt, epsilon);
        return Math.min(d1, d2);
    }

    /**
     * Move the rectangle.
     *
     * @param v What is added to {@link #p}, {@link #q}, {@link #r}, {@link #s}.
     */
    @Override
    public void translate(V3D_Vector_d v) {
        pqr.translate(v);
        rsp.translate(v);
        convexHull = null;
    }

    @Override
    public V3D_Rectangle_d rotate(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V3D_Rectangle_d(this);
        } else {
            return rotateN(ray, uv, theta, epsilon);
        }
    }

    @Override
    public V3D_Rectangle_d rotateN(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        return new V3D_Rectangle_d(
                getP().rotate(ray, uv, theta, epsilon),
                getQ().rotate(ray, uv, theta, epsilon),
                getR().rotate(ray, uv, theta, epsilon),
                getS().rotate(ray, uv, theta, epsilon));
    }

    /**
     * Calculate and return the intersection between {@code this} and {@code pl}
     *
     * @param pl The plane to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code pl}.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_Plane_d pl,
            double epsilon) {
        if (getPl().equals(pl, epsilon)) {
            return new V3D_Rectangle_d(this);
        }
        V3D_FiniteGeometry_d pqri = pqr.getIntersect(pl, epsilon);
        if (pqri == null) {
            return rsp.getIntersect(pl, epsilon);
        } else if (pqri instanceof V3D_Triangle_d) {
            return new V3D_Rectangle_d(this);
        } else {
            V3D_FiniteGeometry_d rspi = rsp.getIntersect(pl, epsilon);
            return join(epsilon, pqri, rspi);
        }
    }

    /**
     * Computes and returns the intersection between {@code this} and {@code t}.
     * The intersection could be: null, a point, a line segment, a triangle, or
     * a convex hull (with 4, 5, 6 or 7 sides).
     *
     * @param t The triangle intersect with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_Triangle_d t,
            double epsilon) {
        if (getPl().equalsIgnoreOrientation(t.pl, epsilon)) {
            V3D_FiniteGeometry_d pqrit = pqr.getIntersectCoplanar(t, epsilon);
            V3D_FiniteGeometry_d rspit = rsp.getIntersectCoplanar(t, epsilon);
            if (pqrit == null) {
                return rspit;
            } else if (pqrit instanceof V3D_Point_d) {
                if (rspit == null) {
                    return pqrit;
                } else {
                    return rspit;
                }
            } else if (pqrit instanceof V3D_LineSegment_d) {
                if (rspit == null) {
                    return pqrit;
                } else {
                    return rspit;
                }
            } else {
                V3D_Point_d[] pqritps = pqrit.getPointsArray();
                V3D_Point_d[] rspitps = rspit.getPointsArray();
                V3D_Point_d[] pts = Arrays.copyOf(pqritps, pqritps.length + rspitps.length);
                System.arraycopy(rspitps, 0, pts, pqritps.length, rspitps.length);
                return V3D_ConvexArea_d.getGeometry(epsilon, pts);
            }
        } else {
            return getIntersect0(t, epsilon);
        }
    }

    /**
     * Computes and returns the intersection between {@code this} and {@code t}.
     * The intersection could be: null, a point or a line segment. It is assumed
     * that this and t are not coplanar.
     *
     * @param t The triangle intersect with this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V3D_FiniteGeometry_d getIntersect0(V3D_Triangle_d t,
            double epsilon) {
        V3D_FiniteGeometry_d pqrit = pqr.getIntersect0(t, epsilon);
        V3D_FiniteGeometry_d rspit = rsp.getIntersect0(t, epsilon);
        return join(epsilon, pqrit, rspit);
    }

    /**
     * Compute and return the intersection with {@code r}.
     *
     * @param r The ray to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point, line segment or {@code null}.
     */
    @Override
    public V3D_FiniteGeometry_d getIntersect(V3D_Ray_d r,
            double epsilon) {
        V3D_FiniteGeometry_d gpqr = pqr.getIntersect(r, epsilon);
        V3D_FiniteGeometry_d grsp = rsp.getIntersect(r, epsilon);
        if (gpqr == null) {
            return grsp;
        } else {
            if (grsp == null) {
                return gpqr;
            }
            return join(epsilon, gpqr, grsp);
        }
    }
    
    /**
     * Get the intersection with {@code r} which is assumed to be non-coplanar.
     *
     * @param r The ray to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A point or {@code null}.
     */
    @Override
    public V3D_Point_d getIntersect0(V3D_Ray_d r,
            double epsilon) {
        V3D_Point_d gpqr = pqr.getIntersect0(r, epsilon);
        if (gpqr == null) {
            return rsp.getIntersect0(r, epsilon);
        } else {
            return gpqr;
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
        return Math.min(
                rsp.getDistanceSquared(l, epsilon),
                pqr.getDistanceSquared(l, epsilon));
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
        return Math.min(
                rsp.getDistanceSquared(l, epsilon),
                pqr.getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance to {@code pl}.
     *
     * @param pl A plane.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance squared to {@code p}.
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
     * @return The minimum distance squared to {@code p}.
     */
    public double getDistanceSquared(V3D_Plane_d pl, double epsilon) {
        return Math.min(
                rsp.getDistanceSquared(pl, epsilon),
                pqr.getDistanceSquared(pl, epsilon));
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
        return Math.min(
                rsp.getDistanceSquared(t, epsilon),
                pqr.getDistanceSquared(t, epsilon));
    }

    /**
     * @param r The rectangle to test if it is equal to this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff this is equal to r.
     */
    public boolean equals(V3D_Rectangle_d r, double epsilon) {
        V3D_Point_d[] pts = getPointsArray();
        V3D_Point_d[] rpts = r.getPointsArray();
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
    public V3D_ConvexArea_d getConvexHull(double epsilon) {
        if (convexHull == null) {
            convexHull = new V3D_ConvexArea_d(epsilon, pqr,
                    rsp);
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
    public static boolean isRectangle(V3D_Point_d p, V3D_Point_d q,
            V3D_Point_d r, V3D_Point_d s, double epsilon) {
        V3D_LineSegment_d pq = new V3D_LineSegment_d(p, q);
        V3D_LineSegment_d qr = new V3D_LineSegment_d(q, r);
        V3D_LineSegment_d rs = new V3D_LineSegment_d(r, s);
        V3D_LineSegment_d sp = new V3D_LineSegment_d(s, p);
        return pq.l.isParallel(rs.l, epsilon)
                && qr.l.isParallel(sp.l, epsilon);
    }

    @Override
    public boolean intersects(V3D_AABB_d aabb, double epsilon) {
        return pqr.intersects(aabb, epsilon)
                || rsp.intersects(aabb, epsilon);
    }

    /**
     * If there is intersection with the Axis Aligned Bounding Boxes of pqr or
     * rsp, then intersections are computed, so if the intersection is wanted
     * consider using:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.V3D_Line, int, java.math.RoundingMode)}
     *
     * @param l The line segment to test if it intersects.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersects(V3D_Line_d l, double epsilon) {
        return pqr.intersects(l, epsilon)
                || rsp.intersects(l, epsilon);
    }

    /**
     * If there is intersection with the Axis Aligned Bounding Boxes of pqr or
     * rsp, then intersections are computed, so if the intersection is wanted
     * consider using:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineSegment_d, int, java.math.RoundingMode)}
     *
     * @param l The line segment to test if it intersects.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersects(V3D_LineSegment_d l, double epsilon) {
        return pqr.intersects(l, epsilon)
                || rsp.intersects(l, epsilon);
    }

    /**
     * If no point aligns, then returns false, otherwise the intersection is
     * computed, so if that is needed use:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.d.V3D_Ray_d, double)}
     *
     * @param r The ray to test if it intersects.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersects(V3D_Ray_d r, double epsilon) {
        if (r.isAligned(getP(), epsilon)
                || r.isAligned(getQ(), epsilon)
                || r.isAligned(getR(), epsilon)
                || r.isAligned(getS(), epsilon)) {
            return getIntersect(r, epsilon) != null;
        } else {
            return false;
        }
    }

    /**
     * @param t A triangle to test for intersection.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return {@code true} if t intersects this.
     */
    @Override
    public boolean intersects(V3D_Triangle_d t, double epsilon) {
        if (getPl().equalsIgnoreOrientation(t.getPl(), epsilon)) {
            return intersectsCoplanar(t, epsilon);
        } else {
            return intersects0(t, epsilon);
        }
    }

    /**
     * Use when {@code this} and {@code t} are known to be coplanar.
     *
     * @param t A triangle to test for intersection.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return {@code true} if t intersects this.
     */
    public boolean intersectsCoplanar(V3D_Triangle_d t, double epsilon) {
        return t.intersects00(pqr.getP(), epsilon)
                || t.intersects00(pqr.getQ(), epsilon)
                || t.intersects00(pqr.getR(), epsilon)
                || t.intersects00(rsp.getP(), epsilon)
                || t.intersects00(rsp.getQ(), epsilon)
                || t.intersects00(rsp.getR(), epsilon);
    }

    /**
     * Only use if {@code this} and {@code t} are not coplanar.
     *
     * @param t A triangle to test for intersection.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return {@code true} if t intersects this.
     */
    public boolean intersects0(V3D_Triangle_d t, double epsilon) {
        return pqr.intersects0(t, epsilon)
                || rsp.intersects0(t, epsilon);
    }

    /**
     * @param r Another rectangle to test for intersection.
     * @param epsilon The tolerance within which vector components are
     * considered equal.
     * @return {@code true} if t intersects this.
     */
    public boolean intersects0(V3D_Rectangle_d r, double epsilon) {
        if (getPl().equalsIgnoreOrientation(r.getPl(), epsilon)) {
            return r.intersectsCoplanar(pqr, epsilon)
                    || r.intersectsCoplanar(rsp, epsilon);
        } else {
            return r.intersects0(pqr, epsilon)
                    || r.intersects0(rsp, epsilon);
        }
    }
}
