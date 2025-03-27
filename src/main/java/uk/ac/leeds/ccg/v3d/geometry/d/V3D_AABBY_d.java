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
 * An Axis Aligned Bounding Box with a single y value. If {@link xMin} &LT;
 * {@link xMax} and {@link zMin} &LT; {@link zMax} the bounding box defines a
 * rectangular area with a normal vector parallel to the Y axis. If
 * {@link xMin} = {@link xMax} or {@link zMin} = {@link zMax} the bounding box
 * is a line segment parallel to either the Z axis or X axis respectively. If
 * {@link xMin} = {@link xMax} and {@link zMin} = {@link zMax} the bounding box
 * is a point. It is wanted to calculate if there is intersection/containment of
 * finite geometries in the V3D_AABB_d instance. A general rectangle cannot 
 * be used due to recursive complications.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_AABBY_d extends V3D_AABB2D_d {

    private static final long serialVersionUID = 1L;

    /**
     * The y value.
     */
    private final double y;

    /**
     * For storing the plane, the y value defines the plane. new
     * V3D_Plane_d(getll(), V3D_Vector_d.J);
     */
    protected V3D_Plane_d ypl;
    
    /**
     * The minimum x-coordinate.
     */
    private final double xMin;

    /**
     * The maximum x-coordinate.
     */
    private final double xMax;

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
    public V3D_AABBY_d(V3D_AABBY_d e) {
        super(e);
        y = e.y;
        ypl = e.ypl;
        xMin = e.xMin;
        xMax = e.xMax;
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
    public V3D_AABBY_d(V3D_Environment_d env,
            double x, double y, double z) {
        this(new V3D_Point_d(env, x, y, z));
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param xMin What {@link xMin} is set to.
     * @param xMax What {@link xMax} is set to.
     * @param y What {@link #y} is set to.
     * @param zMin What {@link zMin} is set to.
     * @param zMax What {@link zMax} is set to.
     */
    public V3D_AABBY_d(V3D_Environment_d env,
            double xMin, double xMax, double y,
            double zMin, double zMax) {
        this(new V3D_Point_d(env, xMin, y, zMin),
                new V3D_Point_d(env, xMax, y, zMax));
    }

    /**
     * Create a new instance.
     *
     * @param gs The geometries used to form the envelope.
     */
    public V3D_AABBY_d(V3D_FiniteGeometry_d... gs) {
        V3D_AABBY_d e = new V3D_AABBY_d(gs[0]);
        for (V3D_FiniteGeometry_d g : gs) {
            e = e.union(new V3D_AABBY_d(g));
        }
        env = e.env;
        offset = e.offset;
        xMin = e.xMin;
        xMax = e.xMax;
        zMin = e.zMin;
        zMax = e.zMax;
        y = e.y;
        ypl = e.ypl;
        top = e.top;
        right = e.right;
        bottom = e.bottom;
        left = e.left;
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
    public V3D_AABBY_d(V3D_FiniteGeometry_d g) {
        this(g.getPointsArray());
    }

    /**
     * @param points The points used to form the envelop.
     * @throws RuntimeException if points.length == 0.
     */
    public V3D_AABBY_d(V3D_Point_d... points) {
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
                zMin = points[0].getZ();
                zMax = points[0].getZ();
                top = points[0];
                left = top;
                right = top;
                bottom = top;
            }
            default -> {
                offset = V3D_Vector_d.ZERO;
                double xmin = points[0].getX();
                double xmax = points[0].getX();
                double zmin = points[0].getZ();
                double zmax = points[0].getZ();
                for (int i = 1; i < points.length; i++) {
                    xmin = Math.min(xmin, points[i].getX());
                    xmax = Math.max(xmax, points[i].getX());
                    zmin = Math.min(zmin, points[i].getZ());
                    zmax = Math.max(zmax, points[i].getZ());
                }
                this.xMin = xmin;
                this.xMax = xmax;
                this.zMin = zmin;
                this.zMax = zmax;
            }
        }
        env = points[0].env;
        y = points[0].getY();
    }

    /**
     * @return This represented as a string.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "(xMin=" + getXMin() + ", xMax=" + getXMax()
                + ", y=" + getY()
                + ", zMin=" + getZMin() + ", zMax=" + getZMax()
                + ")";
    }

    /**
     * Test for equality.
     *
     * @param e The V3D_AABBZ to test for equality with this.
     * @return {@code true} iff {@code this} and {@code e} are equal.
     */
    public boolean equals(V3D_AABBY_d e) {
        return getXMin() == e.getXMin()
                && getXMax() == e.getXMax()
                && getY() == e.getY()
                && getZMin() == e.getZMin()
                && getZMax() == e.getZMax();
    }

    /**
     * @return {@link #xMin} with {@link #offset}.dx added.
     */
    public double getXMin() {
        return xMin + offset.dx;
    }

    /**
     * @return {@link #xMax} with {@link #offset}.dx added.
     */
    public double getXMax() {
        return xMax + offset.dx;
    }
    
    /**
     * @return {@link #y} with {@link #offset}.dy added.
     */
    public double getY() {
        return y + offset.dy;
    }

    /**
     * @return {@link #zMin} with {@link #offset}.dz added.
     */
    public double getZMin() {
        return zMin + offset.dz;
    }

    /**
     * @return {@link #zMax} with {@link #offset}.dz added.
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
            ll = new V3D_Point_d(env, getXMin(), getY(), getZMin());
        }
        return ll;
    }

    /**
     * @return {@link #lu} setting it first if it is null.
     */
    @Override
    public V3D_Point_d getlu() {
        if (lu == null) {
            lu = new V3D_Point_d(env, getXMin(), getY(), getZMax());
        }
        return lu;
    }

    /**
     * @return {@link #uu} setting it first if it is null.
     */
    @Override
    public V3D_Point_d getuu() {
        if (uu == null) {
            uu = new V3D_Point_d(env, getXMax(), getY(), getZMax());
        }
        return uu;
    }

    /**
     * @return {@link #ul} setting it first if it is null.
     */
    @Override
    public V3D_Point_d getul() {
        if (ul == null) {
            ul = new V3D_Point_d(env, getXMax(), getY(), getZMin());
        }
        return ul;
    }

    /**
     * @return The {@link #ypl} initialising it first if it is null.
     */
    public V3D_Plane_d getYPl() {
        if (ypl == null) {
            ypl = new V3D_Plane_d(getll(), V3D_Vector_d.J);
        }
        return ypl;
    }

    /**
     * @return the left of the envelope.
     */
    @Override
    public V3D_FiniteGeometry_d getLeft() {
        if (left == null) {
            double xmin = getXMin();
            double ty = getY();
            double zmin = getZMin();
            double zmax = getZMax();
            if (zmin == zmax) {
                left = new V3D_Point_d(env, xmin, ty, zmax);
            } else {
                left = new V3D_LineSegment_d(
                        new V3D_Point_d(env, xmin, ty, zmin),
                        new V3D_Point_d(env, xmin, ty, zmax));
            }
        }
        return left;
    }

    /**
     * The left plane is orthogonal to the yPlane. With a normal pointing away.
     *
     * @return {@link #lpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane_d getLeftPlane() {
        if (lpl == null) {
            lpl = new V3D_Plane_d(new V3D_Point_d(env, getXMin(), getY(), 
                    getZMin()), V3D_Vector_d.NI);
        }
        return lpl;
    }

    /**
     * @return the right of the envelope.
     */
    @Override
    public V3D_FiniteGeometry_d getRight() {
        if (right == null) {
            double xmax = getXMax();
            double ty = getY();
            double zmin = getZMin();
            double zmax = getZMax();
            if (zmin == zmax) {
                right = new V3D_Point_d(env, xmax, ty, zmax);
            } else {
                right = new V3D_LineSegment_d(
                        new V3D_Point_d(env, xmax, ty, zmin),
                        new V3D_Point_d(env, xmax, ty, zmax));
            }
        }
        return right;
    }

    /**
     * The right plane is orthogonal to the yPlane. With a normal pointing away.
     *
     * @return {@link #rpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane_d getRightPlane() {
        if (rpl == null) {
            rpl = new V3D_Plane_d(new V3D_Point_d(env, getXMax(), getY(), getZMax()),
                    V3D_Vector_d.I);
        }
        return rpl;
    }

    /**
     * @return the top of the envelope.
     */
    @Override
    public V3D_FiniteGeometry_d getTop() {
        if (top == null) {
            double xmin = getXMin();
            double xmax = getXMax();
            double ty = getY();
            double zmax = getZMax();
            if (xmin == xmax) {
                top = new V3D_Point_d(env, xmin, ty, zmax);
            } else {
                top = new V3D_LineSegment_d(
                        new V3D_Point_d(env, xmin, ty, zmax),
                        new V3D_Point_d(env, xmax, ty, zmax));
            }
        }
        return top;
    }

    /**
     * The top plane is orthogonal to the yPlane. With a normal pointing away.
     *
     * @return {@link #tpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane_d getTopPlane() {
        if (tpl == null) {
            tpl = new V3D_Plane_d(new V3D_Point_d(env, getXMax(), getY(), getZMax()),
                    V3D_Vector_d.K);
        }
        return tpl;
    }

    /**
     * @return the bottom of the envelope.
     */
    @Override
    public V3D_FiniteGeometry_d getBottom() {
        if (bottom == null) {
            double xmin = getXMin();
            double xmax = getXMax();
            double ty = getY();
            double zmin = getZMin();
            if (xmin == xmax) {
                bottom = new V3D_Point_d(env, xmin, ty, zmin);
            } else {
                bottom = new V3D_LineSegment_d(
                        new V3D_Point_d(env, xmin, ty, zmin),
                        new V3D_Point_d(env, xmax, ty, zmin));
            }
        }
        return bottom;
    }

    /**
     * The bottom plane is orthogonal to the yPlane. With a normal pointing
     * away.
     *
     * @return {@link #bpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane_d getBottomPlane() {
        if (bpl == null) {
            bpl = new V3D_Plane_d(new V3D_Point_d(env, getXMin(), getY(), getZMin()),
                    V3D_Vector_d.NK);
        }
        return bpl;
    }

    /**
     * Translates this using {@code v}.
     *
     * @param v The vector of translation.
     */
    @Override
    public void translate(V3D_Vector_d v) {
        super.translate(v);
        if (ypl != null) {
            ypl.translate(v);
        }
    }
    
    /**
     * Calculate and return the approximate (or exact) centroid of the envelope.
     *
     * @return The approximate or exact centre of this.
     */
    @Override
    public V3D_Point_d getCentroid() {
        return new V3D_Point_d(env,
                (getXMax() + getXMin()) / 2d,
                getY(),
                (getZMax() + getZMin()) / 2d);
    }

    /**
     * @param e The V3D_AABBY to union with this. It is assumed
     * to be in the same Y plane.
     * @return a V3D_AABBZ which is {@code this} union {@code e}.
     */
    public V3D_AABBY_d union(V3D_AABBY_d e) {
        if (this.contains(e)) {
            return this;
        } else {
            return new V3D_AABBY_d(env,
                    Math.min(e.getXMin(), getXMin()),
                    Math.max(e.getXMax(), getXMax()),
                    getY(),
                    Math.min(e.getZMin(), getZMin()),
                    Math.max(e.getZMax(), getZMax()));
        }
    }

    /**
     * @param e The V3D_AABBY to test for intersection. It is assumed
     * to be in the same Y plane.
     * @return {@code true} if this intersects with {@code e} it the {@code oom}
     * level of precision.
     */
    public boolean intersects(V3D_AABBY_d e) {
        if (isBeyond(e)) {
            return !e.isBeyond(this);
        } else {
            return true;
        }
    }

    /**
     * @param e The V3D_AABBY to test if {@code this} is beyond. It is assumed
     * to be in the same Y plane.
     * @return {@code true} iff {@code this} is beyond {@code e} (i.e. they do
     * not touch or intersect).
     */
    public boolean isBeyond(V3D_AABBY_d e) {
        return getXMax() < e.getXMin()
                || getXMin() > e.getXMax()
                || getZMax() < e.getZMin()
                || getZMin() > e.getZMax();
    }

    /**
     * @param e V3D_AABB The envelope to test if it is contained. It is assumed
     * to be in the same Y plane.
     * @return {@code true} iff {@code this} contains {@code e}.
     */
    public boolean contains(V3D_AABBY_d e) {
        return getXMax() >= e.getXMax()
                && getXMin() <= e.getXMin()
                && getZMax() >= e.getZMax()
                && getZMin() <= e.getZMin();
    }

    /**
     * @param p The point to test if it is contained. It is assumed
     * to be in the same Y plane.
     * @return {@code} true iff {@code this} contains {@code p}.
     */
    @Override
    public boolean contains(V3D_Point_d p) {
        double px = p.getX();
        double pz = p.getZ();
        return getXMax() >= px
                && getXMin() <= px                
                && getZMax() >= pz
                && getZMin() <= pz;
    }

    /**
     * @param l The line to test for containment.
     * @return {@code true} if this contains {@code left}
     */
    @Override
    public boolean contains(V3D_LineSegment_d l) {
        return contains(l.getP()) && contains(l.getQ());
    }

    /**
     * @param p The point to test for intersection. It is assumed
     * to be in the same Y plane.
     * @return {@code true} if this intersects with {@code p}
     */
    @Override
    public boolean intersects(V3D_Point_d p) {
        return intersects(p.getX(), p.getZ());
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @return {@code true} if this intersects with a point defined by 
     * {@code x}, {@link #y}, and {@code z}}.
     */
    public boolean intersects(double x, double z) {
        return x >= getXMin()
                && x <= getXMax()
                && z >= getZMin()
                && z <= getZMax();
    }
    
    /**
     * Gets the intersect {@code left} with {@code ls} where {@code ls} is a side 
     * either {@link #top}, {@link #bottom}, {@link #left} or {@link #right} when a 
     * line segment.
     *     
     * @param ls The line segment to get the intersect with left. The line segment 
 must be lying in the same yPlane. 
     * @param l The line to get intersection with this. The line must be lying 
     * in the same yPlane. 
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return The intersection between {@code this} and {@code left}.
     */
    @Override
    public V3D_FiniteGeometry_d getIntersect(V3D_LineSegment_d ls, V3D_Line_d l,
            double epsilon) {
        double x1 = ls.getP().getX();
        double x2 = ls.getQ().getX();
        double x3 = l.getP().getX();
        double x4 = l.getQ().getX();
        double z1 = ls.l.p.getZ();
        double z2 = ls.l.q.getZ();
        double z3 = l.p.getZ();
        double z4 = l.q.getZ();
        double den = V3D_AABB_d.getIntersectDenominator(x1, x2, x3, x4, z1, z2, z3, z4);
        V3D_Geometry_d li = getIntersect(ls.l, l, den, x1, x2, x3, x4, z1, z2, z3, z4, epsilon);
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
     * @param l0 A line to get the intersect with left. The line must be lying in 
 the same plane as this and left. 
     * @param l Line to intersect with.
     * @param den getIntersectDenominator(x1, x2, x3, x4, z1, z2, z3, z4)
     * @param x1 getP().getX()
     * @param x2 getQ().getX()
     * @param x3 left.getP().getX()
     * @param x4 left.getQ().getX()
     * @param z1 p.getZ()
     * @param z2 q.getZ()
     * @param z3 left.p.getZ()
     * @param z4 left.q.getZ()
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return The geometry or null if there is no intersection.
     */
    public V3D_Geometry_d getIntersect(V3D_Line_d l0, V3D_Line_d l, double den,
            double x1, double x2, double x3, double x4,
            double z1, double z2, double z3, double z4,
            double epsilon) {
        if (V3D_AABB_d.intersects(l0, l, den, epsilon)) {
            // Check for coincident lines
            if (l0.equals(epsilon, l)) {
                return l;
            }
            double x1z2sz1x2 = (x1 * z2) - (z1 * x2);
            double x3z4sz3x4 = (x3 * z4) - (z3 * x4);
            double numx = (x1z2sz1x2 * (x3 - x4)) - ((x1 - x2) * x3z4sz3x4);
            double numz = (x1z2sz1x2 * (z3 - z4)) - ((z1 - z2) * x3z4sz3x4);
            return new V3D_Point_d(env, numx / den, getY(), numz / den);
        }
        return null;
    }
}
