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

import java.io.Serializable;
import java.util.HashSet;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

/**
 * An Axis Aligned Bounding Box with a single x value. If {@link yMin} &LT;
 * {@link yMax} and {@link zMin} &LT; {@link zMax} the bounding box defines a
 * rectangular area with a normal vector parallel to the X axis. If
 * {@link yMin} = {@link yMax} or {@link zMin} = {@link zMax} the bounding box
 * is a line segment parallel to either the Z axis or Y axis respectively. If
 * {@link yMin} = {@link yMax} and {@link zMin} = {@link zMax} the bounding box
 * is a point. It is wanted to calculate if there is intersection/containment of
 * a shape in the V3D_AABB instance. A general rectangle cannot easily be used
 * instead without additional complication.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_AABBX_d implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The environment.
     */
    protected final V3D_Environment_d env;

    /**
     * For storing the offset of this.
     */
    private V3D_Vector_d offset;

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
     * For storing the left lower point.
     */
    protected V3D_Point_d ll;

    /**
     * For storing the left upper point.
     */
    protected V3D_Point_d lu;

    /**
     * For storing the right upper point.
     */
    protected V3D_Point_d uu;

    /**
     * For storing the right lower point.
     */
    protected V3D_Point_d ul;

    /**
     * The top/upper edge.
     */
    protected V3D_FiniteGeometry_d t;

    /**
     * The right edge.
     */
    protected V3D_FiniteGeometry_d r;

    /**
     * The bottom/lower edge.
     */
    protected V3D_FiniteGeometry_d b;

    /**
     * The left edge.
     */
    protected V3D_FiniteGeometry_d l;

    /**
     * For storing all the points.N.B {@link #ll}, {@link #lu}, {@link #uu},
     * {@link #lu} may all be the same.
     */
    protected HashSet<V3D_Point_d> pts;

    /**
     * @param e An envelope.
     */
    public V3D_AABBX_d(V3D_AABBX_d e) {
        env = e.env;
        offset = e.offset;
        x = e.x;
        xpl = e.xpl;
        yMin = e.yMin;
        yMax = e.yMax;
        zMin = e.zMin;
        zMax = e.zMax;
        ll = e.ll;
        lu = e.lu;
        uu = e.uu;
        ul = e.ul;
        l = e.l;
        r = e.r;
        b = e.b;
        t = e.t;
        pts = e.pts;
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
     * @return {@link #pts} initialising first if it is null.
     */
    public HashSet<V3D_Point_d> getPoints() {
        if (pts == null) {
            pts = new HashSet<>(4);
            pts.add(getll());
            pts.add(getlu());
            pts.add(getuu());
            pts.add(getul());
        }
        return pts;
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
    public V3D_Point_d getll() {
        if (ll == null) {
            ll = new V3D_Point_d(env, x, yMin, zMin);
        }
        return ll;
    }

    /**
     * @return {@link #lu} setting it first if it is null.
     */
    public V3D_Point_d getlu() {
        if (lu == null) {
            lu = new V3D_Point_d(env, x, yMin, zMax);
        }
        return lu;
    }

    /**
     * @return {@link #uu} setting it first if it is null.
     */
    public V3D_Point_d getuu() {
        if (uu == null) {
            uu = new V3D_Point_d(env, x, yMax, zMax);
        }
        return uu;
    }

    /**
     * @return {@link #ul} setting it first if it is null.
     */
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
     * @return the right of the envelope.
     */
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
     * @return the top of the envelope.
     */
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
     * @return the bottom of the envelope.
     */
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
     * Translates this using {@code v}.
     *
     * @param v The vector of translation.
     */
    public void translate(V3D_Vector_d v) {
        offset = offset.add(v);
        pts = null;
        if (ll != null) {
            ll.translate(v);
        }
        if (lu != null) {
            lu.translate(v);
        }
        if (uu != null) {
            uu.translate(v);
        }
        if (ul != null) {
            ul.translate(v);
        }
        if (l != null) {
            l.translate(v);
        }
        if (t != null) {
            t.translate(v);
        }
        if (r != null) {
            r.translate(v);
        }
        if (b != null) {
            b.translate(v);
        }
//        xMax = xMax.add(v.getDX());
//        xMin = xMin.add(v.getDX());
//        yMax = yMax.add(v.getDY());
//        yMin = yMin.add(v.getDY());
    }

    /**
     * Calculate and return the approximate (or exact) centroid of the envelope.
     *
     * @return The approximate or exact centre of this.
     */
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
    public boolean contains(V3D_LineSegment_d l) {
        return contains(l.getP()) && contains(l.getQ());
    }

    /**
     * @param p The point to test for intersection. It is assumed
     * to be in the same X plane.
     * @return {@code true} if this intersects with {@code p}
     */
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
}
