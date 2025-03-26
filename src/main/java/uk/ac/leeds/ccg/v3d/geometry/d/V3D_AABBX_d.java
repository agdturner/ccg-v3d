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
 * An Axis Aligned Bounding Box with a single x value. If {@link yMin} &LT;
 * {@link yMax} and {@link zMin} &LT; {@link zMax} the bounding box defines a
 * rectangular area with a normal vector parallel to the X axis. If
 * {@link yMin} = {@link yMax} or {@link zMin} = {@link zMax} the bounding box
 * is a line segment parallel to either the Z axis or Y axis respectively. If
 * {@link yMin} = {@link yMax} and {@link zMin} = {@link zMax} the bounding box
 * is a point. It is wanted to calculate if there is intersection/containment of
 * finite geometries in the V3D_AABB_d instance. A general rectangle cannot 
 * be used due to recursive complications.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_AABBX_d extends V3D_AABB2D_d {

    private static final long serialVersionUID = 1L;

    /**
     * The x value.
     */
    private final double x;

    /**
     * For storing the plane, the x value defines the plane. new
     * V3D_Plane_d(getll(), V3D_Vector_d.I);
     */
    protected V3D_Plane_d xpl;
    
    /**
     * The minimum y-coordinate.
     */
    private final double yMin;

    /**
     * The maximum y-coordinate.
     */
    private final double yMax;

    /**
     * The minimum z-coordinate.
     */
    private final double zMin;

    /**
     * The maximum z-coordinate.
     */
    private final double zMax;

    /**
     * @param e An envelope.
     */
    public V3D_AABBX_d(V3D_AABBX_d e) {
        super(e);
        x = e.x;
        xpl = e.xpl;
        yMin = e.yMin;
        yMax = e.yMax;
        zMin = e.zMin;
        zMax = e.zMax;
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     * @param z What {@link #z} is set to.
     */
    public V3D_AABBX_d(V3D_Environment_d env,
            double x, double y, double z) {
        this(new V3D_Point_d(env, x, y, z));
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param x What {@link #x} is set to.
     * @param yMin What {@link yMin} is set to.
     * @param yMax What {@link yMax} is set to.
     * @param zMin What {@link zMin} is set to.
     * @param zMax What {@link zMax} is set to.
     */
    public V3D_AABBX_d(V3D_Environment_d env,
            double x, double yMin, double yMax, 
            double zMin, double zMax) {
        this(new V3D_Point_d(env, x, yMin, zMin),
                new V3D_Point_d(env, x, yMax, zMax));
    }

    /**
     * Create a new instance.
     *
     * @param gs The geometries used to form the envelope.
     */
    public V3D_AABBX_d(V3D_FiniteGeometry_d... gs) {
        V3D_AABBX_d e = new V3D_AABBX_d(gs[0]);
        for (V3D_FiniteGeometry_d g : gs) {
            e = e.union(new V3D_AABBX_d(g));
        }
        env = e.env;
        offset = e.offset;
        yMin = e.yMin;
        yMax = e.yMax;
        zMin = e.zMin;
        zMax = e.zMax;
        x = e.x;
        xpl = e.xpl;
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
    public V3D_AABBX_d(V3D_FiniteGeometry_d g) {
        this(g.getPointsArray());
    }

    /**
     * @param points The points used to form the envelop.
     * @throws RuntimeException if points.length == 0.
     */
    public V3D_AABBX_d(V3D_Point_d... points) {
        //offset = points[0].offset;
        //offset = V3D_Vector_d.ZERO;
        int len = points.length;
        switch (len) {
            case 0 ->
                throw new RuntimeException("Cannot create envelope from an empty "
                        + "collection of points.");
            case 1 -> {
                offset = V3D_Vector_d.ZERO;
                yMin = points[0].getY();
                yMax = points[0].getY();
                zMin = points[0].getZ();
                zMax = points[0].getZ();
                t = points[0];
                l = t;
                r = t;
                b = t;
            }
            default -> {
                offset = V3D_Vector_d.ZERO;
                double ymin = points[0].getY();
                double ymax = points[0].getY();
                double zmin = points[0].getZ();
                double zmax = points[0].getZ();
                for (int i = 1; i < points.length; i++) {
                    ymin = Math.min(ymin, points[i].getY());
                    ymax = Math.max(ymax, points[i].getY());
                    zmin = Math.min(zmin, points[i].getZ());
                    zmax = Math.max(zmax, points[i].getZ());
                }
                this.yMin = ymin;
                this.yMax = ymax;
                this.zMin = zmin;
                this.zMax = zmax;
            }
        }
        env = points[0].env;
        x = points[0].getX();
    }

    /**
     * @return This represented as a string.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "(x=" + x
                + ", yMin=" + getYMin() + ", yMax=" + getYMax()
                + ", zMin=" + getZMin() + ", zMax=" + getZMax()
                + ")";
    }

    /**
     * Test for equality.
     *
     * @param e The V3D_AABBZ to test for equality with this.
     * @return {@code true} iff {@code this} and {@code e} are equal.
     */
    public boolean equals(V3D_AABBX_d e) {
        return  x == e.x
                && getYMin() == e.getYMin()
                && getYMax() == e.getYMax()
                && getZMin() == e.getZMin()
                && getZMax() == e.getZMax();
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
     * @return {@link #zMin} with {@link #offset} added.
     */
    public double getZMin() {
        return zMin + offset.dz;
    }

    /**
     * @return {@link #zMax} with {@link #offset} added.
     */
    public double getZMax() {
        return zMax + offset.dz;
    }

    /**
     * @return {@link #ll} setting it first if it is null.
     */
    @Override
    public V3D_Point_d getll() {
        if (ll == null) {
            ll = new V3D_Point_d(env, x, yMin, zMin);
        }
        return ll;
    }

    /**
     * @return {@link #lu} setting it first if it is null.
     */
    @Override
    public V3D_Point_d getlu() {
        if (lu == null) {
            lu = new V3D_Point_d(env, x, yMin, zMax);
        }
        return lu;
    }

    /**
     * @return {@link #uu} setting it first if it is null.
     */
    @Override
    public V3D_Point_d getuu() {
        if (uu == null) {
            uu = new V3D_Point_d(env, x, yMax, zMax);
        }
        return uu;
    }

    /**
     * @return {@link #ul} setting it first if it is null.
     */
    @Override
    public V3D_Point_d getul() {
        if (ul == null) {
            ul = new V3D_Point_d(env, x, yMax, zMin);
        }
        return ul;
    }

    /**
     * @return The {@link #ypl} initialising it first if it is null.
     */
    public V3D_Plane_d getXPl() {
        if (xpl == null) {
            xpl = new V3D_Plane_d(getll(), V3D_Vector_d.I);
        }
        return xpl;
    }

    /**
     * @return the left of the envelope.
     */
    @Override
    public V3D_FiniteGeometry_d getLeft() {
        if (l == null) {
            double ymin = getYMin();
            double zmin = getZMin();
            double zmax = getZMax();
            if (zmin == zmax) {
                l = new V3D_Point_d(env, x, ymin, zmax);
            } else {
                l = new V3D_LineSegment_d(
                        new V3D_Point_d(env, x, ymin, zmin),
                        new V3D_Point_d(env, x, ymin, zmax));
            }
        }
        return l;
    }

    /**
     * The left plane is orthogonal to the xPlane. With a normal pointing away.
     *
     * @return {@link #lpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane_d getLeftPlane() {
        if (lpl == null) {
            lpl = new V3D_Plane_d(new V3D_Point_d(env, x, getYMin(), getZMin()),
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
            double ymax = getYMax();
            double zmin = getZMin();
            double zmax = getZMax();
            if (zmin == zmax) {
                r = new V3D_Point_d(env, x, ymax, zmax);
            } else {
                r = new V3D_LineSegment_d(
                        new V3D_Point_d(env, x, ymax, zmin),
                        new V3D_Point_d(env, x, ymax, zmax));
            }
        }
        return r;
    }

    /**
     * The right plane is orthogonal to the xPlane. With a normal pointing away.
     *
     * @return {@link #rpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane_d getRightPlane() {
        if (rpl == null) {
            rpl = new V3D_Plane_d(new V3D_Point_d(env, x, getYMax(), getZMax()),
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
            double ymin = getYMin();
            double ymax = getYMax();
            double zmax = getZMax();
            if (ymin == ymax) {
                t = new V3D_Point_d(env, x, ymin, zmax);
            } else {
                t = new V3D_LineSegment_d(
                        new V3D_Point_d(env, x, ymin, zmax),
                        new V3D_Point_d(env, x, ymax, zmax));
            }
        }
        return t;
    }

    /**
     * The top plane is orthogonal to the xPlane. With a normal pointing away.
     *
     * @return {@link #tpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane_d getTopPlane() {
        if (tpl == null) {
            tpl = new V3D_Plane_d(new V3D_Point_d(env, x, getYMax(), getZMax()),
                    V3D_Vector_d.K);
        }
        return tpl;
    }

    /**
     * @return the bottom of the envelope.
     */
    @Override
    public V3D_FiniteGeometry_d getBottom() {
        if (b == null) {
            double ymin = getYMin();
            double ymax = getYMax();
            double zmin = getZMin();
            if (ymin == ymax) {
                b = new V3D_Point_d(env, x, ymin, zmin);
            } else {
                b = new V3D_LineSegment_d(
                        new V3D_Point_d(env, x, ymin, zmin),
                        new V3D_Point_d(env, x, ymax, zmin));
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
            bpl = new V3D_Plane_d(new V3D_Point_d(env, x, getYMin(), getZMin()),
                    V3D_Vector_d.NK);
        }
        return bpl;
    }
    
    /**
     * Calculate and return the approximate (or exact) centroid of the envelope.
     *
     * @return The approximate or exact centre of this.
     */
    @Override
    public V3D_Point_d getCentroid() {
        return new V3D_Point_d(env,
                x,
                (getYMax() + getYMin()) / 2d,
                (getZMax() + getZMin()) / 2d);
    }

    /**
     * @param e The V3D_AABBZ to union with this. It is assumed
     * to be in the same Y plane.
     * @return a V3D_AABBZ which is {@code this} union {@code e}.
     */
    public V3D_AABBX_d union(V3D_AABBX_d e) {
        if (this.contains(e)) {
            return this;
        } else {
            return new V3D_AABBX_d(env, x,
                    Math.min(e.getYMin(), getYMin()),
                    Math.max(e.getYMax(), getYMax()),
                    Math.min(e.getZMin(), getZMin()),
                    Math.max(e.getZMax(), getZMax()));
        }
    }

    /**
     * @param e The V3D_AABBX to test for intersection. It is assumed
     * to be in the same Y plane.
     * @return {@code true} if this intersects with {@code e} it the {@code oom}
     * level of precision.
     */
    public boolean intersects(V3D_AABBX_d e) {
        if (isBeyond(e)) {
            return !e.isBeyond(this);
        } else {
            return true;
        }
    }

    /**
     * @param e The V3D_AABBX to test if {@code this} is beyond. It is assumed
     * to be in the same X plane.
     * @return {@code true} iff {@code this} is beyond {@code e} (i.e. they do
     * not touch or intersect).
     */
    public boolean isBeyond(V3D_AABBX_d e) {
        return getYMax() < e.getYMin()
                || getYMin() > e.getYMax()
                || getZMax() < e.getZMin()
                || getZMin() > e.getZMax();
    }

    /**
     * @param e V3D_AABB The envelope to test if it is contained. It is assumed
     * to be in the same Y plane.
     * @return {@code true} iff {@code this} contains {@code e}.
     */
    public boolean contains(V3D_AABBX_d e) {
        return getYMax() >= e.getYMax()
                && getYMin() <= e.getYMin()
                && getZMax() >= e.getZMax()
                && getZMin() <= e.getZMin();
    }

    /**
     * The location of p may get rounded.
     *
     * @param p The point to test if it is contained. It is assumed
     * to be in the same Y plane.
     * @return {@code} true iff {@code this} contains {@code p}.
     */
    @Override
    public boolean contains(V3D_Point_d p) {
        double py = p.getY();
        double pz = p.getZ();
        return getYMax() >= py
                && getYMin() <= py                
                && getZMax() >= pz
                && getZMin() <= pz;
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
     * to be in the same X plane.
     * @return {@code true} if this intersects with {@code p}
     */
    @Override
    public boolean intersects(V3D_Point_d p) {
        return intersects(p.getY(), p.getZ());
    }

    /**
     * @param y The y-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @return {@code true} if this intersects with a point defined by 
     * {@link #x}, {@code y}, and {@code z}}.
     */
    public boolean intersects(double y, double z) {
        return y >= getYMin()
                && y <= getYMax()
                && z >= getZMin()
                && z <= getZMax();
    }
    
    /**
     * Gets the intersect {@code l} with {@code ls} where {@code ls} is a side 
     * either {@link #t}, {@link #b}, {@link #l} or {@link #r} when a 
     * line segment.
     *
     * @param ls The line segment to get the intersect with l. The line segment 
     * must be lying in the same xPlane. 
     * @param l The line to get intersection with this. The line must be lying 
     * in the same xPlane. 
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_FiniteGeometry_d getIntersect(V3D_LineSegment_d ls, V3D_Line_d l,
            double epsilon) {
        double y1 = ls.getP().getY();
        double y2 = ls.getQ().getY();
        double y3 = l.getP().getX();
        double y4 = l.getQ().getX();
        double z1 = ls.l.p.getZ();
        double z2 = ls.l.q.getZ();
        double z3 = l.p.getZ();
        double z4 = l.q.getZ();
        double den = V3D_AABB_d.getIntersectDenominator(y1, y2, y3, y4, z1, z2, z3, z4);
        V3D_Geometry_d li = getIntersect(ls.l, l, den, y1, y2, y3, y4, z1, z2, z3, z4, epsilon);
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
     * @param den getIntersectDenominator(x1, x2, x3, x4, z1, z2, z3, z4)
     * @param y1 getP().getY()
     * @param y2 getQ().getY()
     * @param y3 l.getP().getY()
     * @param y4 l.getQ().getY()
     * @param z1 p.getZ()
     * @param z2 q.getZ()
     * @param z3 l.p.getZ()
     * @param z4 l.q.getZ()
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return The geometry or null if there is no intersection.
     */
    public V3D_Geometry_d getIntersect(V3D_Line_d l0, V3D_Line_d l, double den,
            double y1, double y2, double y3, double y4,
            double z1, double z2, double z3, double z4,
            double epsilon) {
        if (V3D_AABB_d.intersects(l0, l, den, epsilon)) {
            // Check for coincident lines
            if (l0.equals(epsilon, l)) {
                return l;
            }
            double y1z2sz1y2 = (y1 * z2) - (z1 * y2);
            double y3z4sz3y4 = (y3 * z4) - (z3 * y4);
            double numy = (y1z2sz1y2 * (y3 - y4)) - ((y1 - y2) * y3z4sz3y4);
            double numz = (y1z2sz1y2 * (z3 - z4)) - ((z1 - z2) * y3z4sz3y4);
            return new V3D_Point_d(env, x, numy / den, numz / den);
        }
        return null;
    }
}
