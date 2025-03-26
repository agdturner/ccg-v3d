/*
 * Copyright 2025 Andy Turner, University of Leeds.
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

import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

/**
 * An Axis Aligned Bounding Box with a single z value. If {@link xMin} &LT;
 * {@link xMax} and {@link yMin} &LT; {@link yMax} the bounding box defines a
 * rectangular area with a normal vector parallel to the Z axis. If
 * {@link xMin} = {@link xMax} or {@link yMin} = {@link yMax} the bounding box
 * is a line segment parallel to either the Y axis or X axis respectively. If
 * {@link xMin} = {@link xMax} and {@link yMin} = {@link yMax} the bounding box
 * is a point. It is wanted to calculate if there is intersection/containment of
 * finite geometries in the V3D_AABB_d instance. A general rectangle cannot 
 * be used due to recursive complications.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_AABBZ_d extends V3D_AABB2D_d {

    private static final long serialVersionUID = 1L;

    /**
     * The z value.
     */
    private final double z;

    /**
     * For storing the plane, the z value defines the plane. new
     * V3D_Plane_d(getll(), V3D_Vector_d.K);
     */
    protected V3D_Plane_d zpl;
    
    /**
     * The minimum x-coordinate.
     */
    private final double xMin;

    /**
     * The maximum x-coordinate.
     */
    private final double xMax;

    /**
     * The minimum y-coordinate.
     */
    private final double yMin;

    /**
     * The maximum y-coordinate.
     */
    private final double yMax;

    /**
     * @param e An envelope.
     */
    public V3D_AABBZ_d(V3D_AABBZ_d e) {
        super(e);
        z = e.z;
        zpl = e.zpl;
        xMin = e.xMin;
        xMax = e.xMax;
        yMin = e.yMin;
        yMax = e.yMax;
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     * @param z What {@link #z} is set to.
     */
    public V3D_AABBZ_d(V3D_Environment_d env,
            double x, double y, double z) {
        this(new V3D_Point_d(env, x, y, z));
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param xMin What {@link xMin} is set to.
     * @param xMax What {@link xMax} is set to.
     * @param yMin What {@link yMin} is set to.
     * @param yMax What {@link yMax} is set to.
     * @param z What {@link #z} is set to.
     */
    public V3D_AABBZ_d(V3D_Environment_d env,
            double xMin, double xMax,
            double yMin, double yMax, double z) {
        this(new V3D_Point_d(env, xMin, yMin, z),
                new V3D_Point_d(env, xMax, yMax, z));
    }

    /**
     * Create a new instance.
     *
     * @param gs The geometries used to form the envelope.
     */
    public V3D_AABBZ_d(V3D_FiniteGeometry_d... gs) {
        V3D_AABBZ_d e = new V3D_AABBZ_d(gs[0]);
        for (V3D_FiniteGeometry_d g : gs) {
            e = e.union(new V3D_AABBZ_d(g));
        }
        env = e.env;
        offset = e.offset;
        yMin = e.yMin;
        yMax = e.yMax;
        xMin = e.xMin;
        xMax = e.xMax;
        z = e.z;
        zpl = e.zpl;
        t = e.t;
        r = e.r;
        b = e.b;
        l = e.l;
        ll = e.ll;
        lu = e.lu;
        uu = e.uu;
        ul = e.ul;
        pts = e.pts;
    }

    /**
     * Create a new instance.
     *
     * @param g The geometry used to form the envelope.
     */
    public V3D_AABBZ_d(V3D_FiniteGeometry_d g) {
        this(g.getPointsArray());
    }

    /**
     * @param points The points used to form the envelop.
     * @throws RuntimeException if points.length == 0.
     */
    public V3D_AABBZ_d(V3D_Point_d... points) {
        //offset = points[0].offset;
        //offset = V3D_Vector_d.ZERO;
        int len = points.length;
        switch (len) {
            case 0 ->
                throw new RuntimeException("Cannot create envelope from an empty "
                        + "collection of points.");
            case 1 -> {
                offset = V3D_Vector_d.ZERO;
                xMin = points[0].getX();
                xMax = points[0].getX();
                yMin = points[0].getY();
                yMax = points[0].getY();
                t = points[0];
                l = t;
                r = t;
                b = t;
            }
            default -> {
                offset = V3D_Vector_d.ZERO;
                double xmin = points[0].getX();
                double xmax = points[0].getX();
                double ymin = points[0].getY();
                double ymax = points[0].getY();
                for (int i = 1; i < points.length; i++) {
                    xmin = Math.min(xmin, points[i].getX());
                    xmax = Math.max(xmax, points[i].getX());
                    ymin = Math.min(ymin, points[i].getY());
                    ymax = Math.max(ymax, points[i].getY());
                }
                this.xMin = xmin;
                this.xMax = xmax;
                this.yMin = ymin;
                this.yMax = ymax;
            }
        }
        env = points[0].env;
        z = points[0].getZ();
    }

    /**
     * @return This represented as a string.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "(xMin=" + getXMin() + ", xMax=" + getXMax()
                + ", yMin=" + getYMin() + ", yMax=" + getYMax()
                + ", z=" + z + ")";
    }

    /**
     * Test for equality.
     *
     * @param e The V3D_AABBZ to test for equality with this.
     * @return {@code true} iff {@code this} and {@code e} are equal.
     */
    public boolean equals(V3D_AABBZ_d e) {
        return getXMin() == e.getXMin()
                && getXMax() == e.getXMax()
                && getYMin() == e.getYMin()
                && getYMax() == e.getYMax()
                && z == e.z;
    }

    /**
     * @return {@link #xMin} with {@link #offset} added.
     */
    public double getXMin() {
        return xMin + offset.dx;
    }

    /**
     * @return {@link #xMax} with {@link #offset} added.
     */
    public double getXMax() {
        return xMax + offset.dx;
    }

    /**
     * @return {@link #yMin} with {@link #offset} added.
     */
    public double getYMin() {
        return yMin + offset.dy;
    }

    /**
     * @return {@link #yMax} with {@link #offset} added.
     */
    public double getYMax() {
        return yMax + offset.dy;
    }

    /**
     * @return {@link #ll} setting it first if it is null.
     */
    @Override
    public V3D_Point_d getll() {
        if (ll == null) {
            ll = new V3D_Point_d(env, xMin, yMin, z);
        }
        return ll;
    }

    /**
     * @return {@link #lu} setting it first if it is null.
     */
    @Override
    public V3D_Point_d getlu() {
        if (lu == null) {
            lu = new V3D_Point_d(env, xMin, yMax, z);
        }
        return lu;
    }

    /**
     * @return {@link #uu} setting it first if it is null.
     */
    @Override
    public V3D_Point_d getuu() {
        if (uu == null) {
            uu = new V3D_Point_d(env, xMax, yMax, z);
        }
        return uu;
    }

    /**
     * @return {@link #ul} setting it first if it is null.
     */
    @Override
    public V3D_Point_d getul() {
        if (ul == null) {
            ul = new V3D_Point_d(env, xMax, yMin, z);
        }
        return ul;
    }

    /**
     * @return The {@link #zpl} initialising it first if it is null.
     */
    public V3D_Plane_d getZPl() {
        if (zpl == null) {
            zpl = new V3D_Plane_d(getll(), V3D_Vector_d.K);
        }
        return zpl;
    }

    /**
     * @return the left of the envelope.
     */
    @Override
    public V3D_FiniteGeometry_d getLeft() {
        if (l == null) {
            double xmin = getXMin();
            double ymin = getYMin();
            double ymax = getYMax();
            if (ymin == ymax) {
                l = new V3D_Point_d(env, xmin, ymax, z);
            } else {
                l = new V3D_LineSegment_d(
                        new V3D_Point_d(env, xmin, ymin, z),
                        new V3D_Point_d(env, xmin, ymax, z));
            }
        }
        return l;
    }

    /**
     * The left plane is orthogonal to the zPlane. With a normal pointing away.
     *
     * @return {@link #lpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane_d getLeftPlane() {
        if (lpl == null) {
            lpl = new V3D_Plane_d(new V3D_Point_d(env, getXMin(), getYMin(), z),
                    V3D_Vector_d.NJ);
        }
        return lpl;
    }

    /**
     * @return the right of the envelope.
     */
    @Override
    public V3D_FiniteGeometry_d getRight() {
        if (r == null) {
            double xmax = getXMax();
            double ymin = getYMin();
            double ymax = getYMax();
            if (ymin == ymax) {
                r = new V3D_Point_d(env, xmax, ymax, z);
            } else {
                r = new V3D_LineSegment_d(
                        new V3D_Point_d(env, xmax, ymin, z),
                        new V3D_Point_d(env, xmax, ymax, z));
            }
        }
        return r;
    }

    /**
     * The right plane is orthogonal to the zPlane. With a normal pointing away.
     *
     * @return {@link #rpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane_d getRightPlane() {
        if (rpl == null) {
            rpl = new V3D_Plane_d(new V3D_Point_d(env, getXMax(), getYMax(), z),
                    V3D_Vector_d.J);
        }
        return rpl;
    }

    /**
     * @return the top of the envelope.
     */
    @Override
    public V3D_FiniteGeometry_d getTop() {
        if (t == null) {
            double xmin = getXMin();
            double xmax = getXMax();
            double ymax = getYMax();
            if (xmin == xmax) {
                t = new V3D_Point_d(env, xmin, ymax, z);
            } else {
                t = new V3D_LineSegment_d(
                        new V3D_Point_d(env, xmin, ymax, z),
                        new V3D_Point_d(env, xmax, ymax, z));
            }
        }
        return t;
    }

    /**
     * The top plane is orthogonal to the zPlane. With a normal pointing away.
     *
     * @return {@link #tpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane_d getTopPlane() {
        if (tpl == null) {
            tpl = new V3D_Plane_d(new V3D_Point_d(env, getXMax(), getYMax(), z),
                    V3D_Vector_d.I);
        }
        return tpl;
    }

    /**
     * @return the bottom of the envelope.
     */
    @Override
    public V3D_FiniteGeometry_d getBottom() {
        if (b == null) {
            double xmin = getXMin();
            double xmax = getXMax();
            double ymin = getYMin();
            if (xmin == xmax) {
                b = new V3D_Point_d(env, xmin, ymin, z);
            } else {
                b = new V3D_LineSegment_d(
                        new V3D_Point_d(env, xmin, ymin, z),
                        new V3D_Point_d(env, xmax, ymin, z));
            }
        }
        return b;
    }

    /**
     * The bottom plane is orthogonal to the xPlane. With a normal pointing
     * away.
     *
     * @return {@link #bpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane_d getBottomPlane() {
        if (bpl == null) {
            bpl = new V3D_Plane_d(new V3D_Point_d(env, getXMin(), getYMin(), z),
                    V3D_Vector_d.NI);
        }
        return bpl;
    }

    /**
     * Calculate and return the approximate (or exact) centroid.
     *
     * @return The approximate or exact centre of this.
     */
    @Override
    public V3D_Point_d getCentroid() {
        return new V3D_Point_d(env,
                (getXMax() + getXMin()) / 2d,
                (getYMax() + getYMin()) / 2d, z);
    }

    /**
     * @param e The V3D_AABBZ to union with this. It is assumed to be in the
     * same Z plane.
     * @return a V3D_AABBZ which is {@code this} union {@code e}.
     */
    public V3D_AABBZ_d union(V3D_AABBZ_d e) {
        if (this.contains(e)) {
            return this;
        } else {
            return new V3D_AABBZ_d(env,
                    Math.min(e.getXMin(), getXMin()),
                    Math.max(e.getXMax(), getXMax()),
                    Math.min(e.getYMin(), getYMin()),
                    Math.max(e.getYMax(), getYMax()), z);
        }
    }

    /**
     * @param e The V3D_AABBZ to test for intersection. It is assumed
     * to be in the same Z plane.
     * @return {@code true} if this intersects with {@code e} it the {@code oom}
     * level of precision.
     */
    public boolean intersects(V3D_AABBZ_d e) {
        if (isBeyond(e)) {
            return !e.isBeyond(this);
        } else {
            return true;
        }
    }

    /**
     * @param e The V3D_AABBZ to test if {@code this} is beyond. It is assumed
     * to be in the same Z plane.
     * @return {@code true} iff {@code this} is beyond {@code e} (i.e. they do
     * not touch or intersect).
     */
    public boolean isBeyond(V3D_AABBZ_d e) {
        return getXMax() < e.getXMin()
                || getXMin() > e.getXMax()
                || getYMax() < e.getYMin()
                || getYMin() > e.getYMax();
    }

    /**
     * @param e V3D_AABB The envelope to test if it is contained. It is assumed
     * to be in the same Z plane.
     * @return {@code true} iff {@code this} contains {@code e}.
     */
    public boolean contains(V3D_AABBZ_d e) {
        return getXMax() >= e.getXMax()
                && getXMin() <= e.getXMin()
                && getYMax() >= e.getYMax()
                && getYMin() <= e.getYMin();
    }

    /**
     * @param p The point to test if it is contained. It is assumed to be in the
     * same Z plane.
     * @return {@code} true iff {@code this} contains {@code p}.
     */
    @Override
    public boolean contains(V3D_Point_d p) {
        double px = p.getX();
        double py = p.getY();
        return getXMax() >= px
                && getXMin() <= px
                && getYMax() >= py
                && getYMin() <= py;
    }

    /**
     * @param l The line to test for containment.
     * @return {@code true} if this contains {@code l}
     */
    @Override
    public boolean contains(V3D_LineSegment_d l) {
        return contains(l.getP()) && contains(l.getQ());
    }

    /**
     * @param p The point to test for intersection. It is assumed
     * to be in the same Z plane.
     * @return {@code true} if this intersects with {@code p}
     */
    @Override
    public boolean intersects(V3D_Point_d p) {
        return intersects(p.getX(), p.getY());
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @return {@code true} if this intersects with a point defined by
     * {@code x}, {@code y}, and {@link #z}}.
     */
    public boolean intersects(double x, double y) {
        return x >= getXMin()
                && x <= getXMax()
                && y >= getYMin()
                && y <= getYMax();
    }
    
    /**
     * Gets the intersect {@code l} with {@code ls} where {@code ls} is a side 
     * either {@link #t}, {@link #b}, {@link #l} or {@link #r} when a 
     * line segment.
     *     
     * @param ls The line segment to get the intersect with l. The line segment 
     * must be lying in the same zPlane. 
     * @param l The line to get intersection with this. The line must be lying 
     * in the same zPlane. 
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_FiniteGeometry_d getIntersect(V3D_LineSegment_d ls, V3D_Line_d l,
            double epsilon) {
        double x1 = ls.getP().getX();
        double x2 = ls.getQ().getX();
        double x3 = l.getP().getX();
        double x4 = l.getQ().getX();
        double y1 = ls.l.p.getY();
        double y2 = ls.l.q.getY();
        double y3 = l.p.getY();
        double y4 = l.q.getY();
        double den = V3D_AABB_d.getIntersectDenominator(x1, x2, x3, x4, y1, y2, y3, y4);
        V3D_Geometry_d li = getIntersect(ls.l, l, den, x1, x2, x3, x4, y1, y2, y3, y4, epsilon);
        if (li != null) {
            if (li instanceof V3D_Point_d pli) {
                //if (intersects(pli,epsilon)) {
                if (ls.isAligned(pli, epsilon)) {
                    return pli;
                } else {
                    return null;
                }
            } else {
                return ls;
            }
        }
        return null;
    }
    
    /**
     * https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
     *     
     * @param l0 A line to get the intersect with l. The line must be lying in 
     * the same plane as this and l.
     * @param l Line to intersect with.
     * @param den getIntersectDenominator(x1, x2, x3, x4, y1, y2, y3, y4)
     * @param x1 getP().getX()
     * @param x2 getQ().getX()
     * @param x3 l.getP().getX()
     * @param x4 l.getQ().getX()
     * @param y1 p.getY()
     * @param y2 q.getY()
     * @param y3 l.p.getY()
     * @param y4 l.q.getY()
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return The geometry or null if there is no intersection.
     */
    public V3D_Geometry_d getIntersect(V3D_Line_d l0, V3D_Line_d l, double den,
            double x1, double x2, double x3, double x4,
            double y1, double y2, double y3, double y4,
            double epsilon) {
        if (V3D_AABB_d.intersects(l0, l, den, epsilon)) {
            // Check for coincident lines
            if (l0.equals(epsilon, l)) {
                return l;
            }
            double x1y2sy1x2 = (x1 * y2) - (y1 * x2);
            double x3y4sy3x4 = (x3 * y4) - (y3 * x4);
            double numx = (x1y2sy1x2 * (x3 - x4)) - ((x1 - x2) * x3y4sy3x4);
            double numy = (x1y2sy1x2 * (y3 - y4)) - ((y1 - y2) * x3y4sy3x4);
            return new V3D_Point_d(env, numx / den, numy / den, z);
        }
        return null;
    }
}
