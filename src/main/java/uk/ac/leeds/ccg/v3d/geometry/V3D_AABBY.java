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
package uk.ac.leeds.ccg.v3d.geometry;

import ch.obermuhlner.math.big.BigRational;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.HashSet;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * An Axis Aligned Bounding Box with a single y value. If {@link xMin} &LT;
 * {@link xMax} and {@link zMin} &LT; {@link zMax} the bounding box defines a
 * rectangular area with a normal vector parallel to the Y axis. If
 * {@link xMin} = {@link xMax} or {@link zMin} = {@link zMax} the bounding box
 * is a line segment parallel to either the Z axis or X axis respectively. If
 * {@link xMin} = {@link xMax} and {@link zMin} = {@link zMax} the bounding box
 * is a point. It is wanted to calculate if there is intersection/containment of
 * a shape in the V3D_AABB instance. A general rectangle cannot easily be used
 * instead without additional complication.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_AABBY implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The environment.
     */
    protected final V3D_Environment env;

    /**
     * For storing the offset of this.
     */
    private V3D_Vector offset;

    /**
     * The y value.
     */
    private final BigRational y;

    /**
     * For storing the plane, the y value defines the plane. new
     * V3D_Plane(getll(), V3D_Vector.J);
     */
    protected V3D_Plane ypl;
    /**
     * The minimum x-coordinate.
     */
    private final BigRational xMin;

    /**
     * The maximum x-coordinate.
     */
    private final BigRational xMax;

    /**
     * The minimum z-coordinate.
     */
    private final BigRational zMin;

    /**
     * The maximum z-coordinate.
     */
    private final BigRational zMax;

    /**
     * For storing the left lower point.
     */
    protected V3D_Point ll;

    /**
     * For storing the left upper point.
     */
    protected V3D_Point lu;

    /**
     * For storing the right upper point.
     */
    protected V3D_Point uu;

    /**
     * For storing the right lower point.
     */
    protected V3D_Point ul;

    /**
     * The top/upper edge.
     */
    protected V3D_FiniteGeometry t;

    /**
     * The right edge.
     */
    protected V3D_FiniteGeometry r;

    /**
     * The bottom/lower edge.
     */
    protected V3D_FiniteGeometry b;

    /**
     * The left edge.
     */
    protected V3D_FiniteGeometry l;

    /**
     * For storing all the points.N.B {@link #ll}, {@link #lu}, {@link #uu},
     * {@link #lu} may all be the same.
     */
    protected HashSet<V3D_Point> pts;

    /**
     * @param e An envelope.
     */
    public V3D_AABBY(V3D_AABBY e) {
        env = e.env;
        offset = e.offset;
        y = e.y;
        ypl = e.ypl;
        xMin = e.xMin;
        xMax = e.xMax;
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     * @param z What {@link #z} is set to.
     */
    public V3D_AABBY(V3D_Environment env, int oom, RoundingMode rm,
            BigRational x, BigRational y, BigRational z) {
        this(oom, rm, new V3D_Point(env, x, y, z));
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @param xMin What {@link xMin} is set to.
     * @param xMax What {@link xMax} is set to.
     * @param y What {@link #y} is set to.
     * @param zMin What {@link zMin} is set to.
     * @param zMax What {@link zMax} is set to.
     */
    public V3D_AABBY(V3D_Environment env, int oom, RoundingMode rm,
            BigRational xMin, BigRational xMax, BigRational y,
            BigRational zMin, BigRational zMax) {
        this(oom, rm, new V3D_Point(env, xMin, y, zMin),
                new V3D_Point(env, xMax, y, zMax));
    }

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @param gs The geometries used to form the envelope.
     */
    public V3D_AABBY(int oom, RoundingMode rm, V3D_FiniteGeometry... gs) {
        V3D_AABBY e = new V3D_AABBY(gs[0], oom, rm);
        for (V3D_FiniteGeometry g : gs) {
            e = e.union(new V3D_AABBY(g, oom, rm), oom, rm);
        }
        env = e.env;
        offset = e.offset;
        xMin = e.xMin;
        xMax = e.xMax;
        zMin = e.zMin;
        zMax = e.zMax;
        y = e.y;
        ypl = e.ypl;
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_AABBY(V3D_FiniteGeometry g, int oom, RoundingMode rm) {
        this(oom, rm, g.getPointsArray(oom, rm));
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points The points used to form the envelop.
     * @throws RuntimeException if points.length == 0.
     */
    public V3D_AABBY(int oom, RoundingMode rm, V3D_Point... points) {
        //offset = points[0].offset;
        //offset = V3D_Vector.ZERO;
        int len = points.length;
        switch (len) {
            case 0 ->
                throw new RuntimeException("Cannot create envelope from an empty "
                        + "collection of points.");
            case 1 -> {
                offset = V3D_Vector.ZERO;
                xMin = points[0].getX(oom, RoundingMode.FLOOR);
                xMax = points[0].getX(oom, RoundingMode.CEILING);
                zMin = points[0].getZ(oom, RoundingMode.FLOOR);
                zMax = points[0].getZ(oom, RoundingMode.CEILING);
                t = points[0];
                l = t;
                r = t;
                b = t;
            }
            default -> {
                offset = V3D_Vector.ZERO;
                BigRational xmin = points[0].getX(oom, RoundingMode.FLOOR);
                BigRational xmax = points[0].getX(oom, RoundingMode.CEILING);
                BigRational zmin = points[0].getZ(oom, RoundingMode.FLOOR);
                BigRational zmax = points[0].getZ(oom, RoundingMode.CEILING);
                for (int i = 1; i < points.length; i++) {
                    xmin = BigRational.min(xmin, points[i].getX(oom, RoundingMode.FLOOR));
                    xmax = BigRational.max(xmax, points[i].getX(oom, RoundingMode.CEILING));
                    zmin = BigRational.min(zmin, points[i].getZ(oom, RoundingMode.FLOOR));
                    zmax = BigRational.max(zmax, points[i].getZ(oom, RoundingMode.CEILING));
                }
                this.xMin = xmin;
                this.xMax = xmax;
                this.zMin = zmin;
                this.zMax = zmax;
            }
        }
        env = points[0].env;
        y = points[0].getY(oom, rm);
    }

    @Override
    public String toString() {
        return toString(env.oom, env.rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return This represented as a string.
     */
    public String toString(int oom, RoundingMode rm) {
        return this.getClass().getSimpleName()
                + "(xMin=" + getXMin(oom, rm) + ", xMax=" + getXMax(oom, rm)
                + ", y=" + y.toString()
                + ", zMin=" + getZMin(oom, rm) + ", zMax=" + getZMax(oom, rm)
                + ")";
    }

    /**
     * @return {@link #pts} initialising first if it is null.
     */
    public HashSet<V3D_Point> getPoints() {
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
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} and {@code e} are equal.
     */
    public boolean equals(V3D_AABBY e, int oom) {
        return this.getXMin(oom).compareTo(e.getXMin(oom)) == 0
                && this.getXMax(oom).compareTo(e.getXMax(oom)) == 0
                && this.y.compareTo(e.y) == 0
                && this.getZMin(oom).compareTo(e.getZMin(oom)) == 0
                && this.getZMax(oom).compareTo(e.getZMax(oom)) == 0;
    }

    /**
     * For getting {@link #xMin} rounded. RoundingMode.FLOOR is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #xMin} rounded.
     */
    public BigRational getXMin(int oom) {
        return getXMin(oom, RoundingMode.FLOOR);
    }

    /**
     * For getting {@link #xMin} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #xMin} rounded.
     */
    public BigRational getXMin(int oom, RoundingMode rm) {
        return xMin.add(offset.getDX(oom - 2, rm));
    }

    /**
     * For getting {@link #xMax} rounded. RoundingMode.CEILING is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #xMax} rounded.
     */
    public BigRational getXMax(int oom) {
        return getXMax(oom, RoundingMode.CEILING);
    }

    /**
     * For getting {@link #xMax} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #xMax} rounded.
     */
    public BigRational getXMax(int oom, RoundingMode rm) {
        return xMax.add(offset.getDX(oom - 2, rm));
    }

    /**
     * For getting {@link #zMin} rounded. RoundingMode.FLOOR is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #zMin} rounded.
     */
    public BigRational getZMin(int oom) {
        return getZMin(oom, RoundingMode.FLOOR);
    }

    /**
     * For getting {@link #zMin} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #zMin} rounded.
     */
    public BigRational getZMin(int oom, RoundingMode rm) {
        return zMin.add(offset.getDZ(oom - 2, rm));
    }

    /**
     * For getting {@link #zMax} rounded. RoundingMode.CEILING is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #zMax} rounded.
     */
    public BigRational getZMax(int oom) {
        return getZMax(oom, RoundingMode.CEILING);
    }

    /**
     * For getting {@link #zMax} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #zMax} rounded.
     */
    public BigRational getZMax(int oom, RoundingMode rm) {
        return zMax.add(offset.getDY(oom - 2, rm));
    }

    /**
     * @return {@link #ll} setting it first if it is null.
     */
    public V3D_Point getll() {
        if (ll == null) {
            ll = new V3D_Point(env, xMin, y, zMin);
        }
        return ll;
    }

    /**
     * @return {@link #lu} setting it first if it is null.
     */
    public V3D_Point getlu() {
        if (lu == null) {
            lu = new V3D_Point(env, xMin, y, zMax);
        }
        return lu;
    }

    /**
     * @return {@link #uu} setting it first if it is null.
     */
    public V3D_Point getuu() {
        if (uu == null) {
            uu = new V3D_Point(env, xMax, y, zMax);
        }
        return uu;
    }

    /**
     * @return {@link #ul} setting it first if it is null.
     */
    public V3D_Point getul() {
        if (ul == null) {
            ul = new V3D_Point(env, xMax, y, zMin);
        }
        return ul;
    }

    /**
     * @return The {@link #ypl} initialising it first if it is null.
     */
    public V3D_Plane getYPl() {
        if (ypl == null) {
            ypl = new V3D_Plane(getll(), V3D_Vector.J);
        }
        return ypl;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the left of the envelope.
     */
    public V3D_FiniteGeometry getLeft(int oom, RoundingMode rm) {
        if (l == null) {
            BigRational xmin = getXMin(oom);
            BigRational zmin = getZMin(oom);
            BigRational zmax = getZMax(oom);
            if (zmin.compareTo(zmax) == 0) {
                l = new V3D_Point(env, xmin, y, zmax);
            } else {
                l = new V3D_LineSegment(
                        new V3D_Point(env, xmin, y, zmin),
                        new V3D_Point(env, xmin, y, zmax), oom, rm);
            }
        }
        return l;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the right of the envelope.
     */
    public V3D_FiniteGeometry getRight(int oom, RoundingMode rm) {
        if (r == null) {
            BigRational xmax = getXMax(oom);
            BigRational zmin = getZMin(oom);
            BigRational zmax = getZMax(oom);
            if (zmin.compareTo(zmax) == 0) {
                r = new V3D_Point(env, xmax, y, zmax);
            } else {
                r = new V3D_LineSegment(
                        new V3D_Point(env, xmax, y, zmin),
                        new V3D_Point(env, xmax, y, zmax), oom, rm);
            }
        }
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the top of the envelope.
     */
    public V3D_FiniteGeometry getTop(int oom, RoundingMode rm) {
        if (t == null) {
            BigRational xmin = getXMin(oom);
            BigRational xmax = getXMax(oom);
            BigRational zmax = getZMax(oom);
            if (xmin.compareTo(xmax) == 0) {
                t = new V3D_Point(env, xmin, y, zmax);
            } else {
                t = new V3D_LineSegment(
                        new V3D_Point(env, xmin, y, zmax),
                        new V3D_Point(env, xmax, y, zmax), oom, rm);
            }
        }
        return t;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the bottom of the envelope.
     */
    public V3D_FiniteGeometry getBottom(int oom, RoundingMode rm) {
        if (b == null) {
            BigRational xmin = getXMin(oom);
            BigRational xmax = getXMax(oom);
            BigRational zmin = getZMin(oom);
            if (xmin.compareTo(xmax) == 0) {
                b = new V3D_Point(env, xmin, y, zmin);
            } else {
                b = new V3D_LineSegment(
                        new V3D_Point(env, xmin, y, zmin),
                        new V3D_Point(env, xmax, y, zmin), oom, rm);
            }
        }
        return b;
    }

    /**
     * Translates this using {@code v}.
     *
     * @param v The vector of translation.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        offset = offset.add(v, oom, rm);
        pts = null;
        if (ll != null) {
            ll.translate(v, oom, rm);
        }
        if (lu != null) {
            lu.translate(v, oom, rm);
        }
        if (uu != null) {
            uu.translate(v, oom, rm);
        }
        if (ul != null) {
            ul.translate(v, oom, rm);
        }
        if (l != null) {
            l.translate(v, oom, rm);
        }
        if (t != null) {
            t.translate(v, oom, rm);
        }
        if (r != null) {
            r.translate(v, oom, rm);
        }
        if (b != null) {
            b.translate(v, oom, rm);
        }
//        xMax = xMax.add(v.getDX(oom, rm));
//        xMin = xMin.add(v.getDX(oom, rm));
//        yMax = yMax.add(v.getDY(oom, rm));
//        yMin = yMin.add(v.getDY(oom, rm));
    }

    /**
     * Calculate and return the approximate (or exact) centroid of the envelope.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return The approximate or exact centre of this.
     */
    public V3D_Point getCentroid(int oom) {
        return new V3D_Point(env,
                getXMax(oom).add(getXMin(oom)).divide(2),
                y,
                getZMax(oom).add(getZMin(oom)).divide(2));
    }

    /**
     * @param e The V3D_AABBY to union with this. It is assumed
     * to be in the same Y plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return a V3D_AABBZ which is {@code this} union {@code e}.
     */
    public V3D_AABBY union(V3D_AABBY e, int oom, RoundingMode rm) {
        if (this.contains(e, oom)) {
            return this;
        } else {
            return new V3D_AABBY(env, oom, rm,
                    BigRational.min(e.getXMin(oom), getXMin(oom)),
                    BigRational.max(e.getXMax(oom), getXMax(oom)),
                    y,
                    BigRational.min(e.getZMin(oom), getZMin(oom)),
                    BigRational.max(e.getZMax(oom), getZMax(oom)));
        }
    }

    /**
     * @param e The V3D_AABBY to test for intersection. It is assumed
     * to be in the same Y plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with {@code e} it the {@code oom}
     * level of precision.
     */
    public boolean intersects(V3D_AABBY e, int oom) {
        if (isBeyond(e, oom)) {
            return !e.isBeyond(this, oom);
        } else {
            return true;
        }
    }

    /**
     * @param e The V3D_AABBY to test if {@code this} is beyond. It is assumed
     * to be in the same Y plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} is beyond {@code e} (i.e. they do
     * not touch or intersect).
     */
    public boolean isBeyond(V3D_AABBY e, int oom) {
        return getXMax(oom).compareTo(e.getXMin(oom)) == -1
                || getXMin(oom).compareTo(e.getXMax(oom)) == 1
                || getZMax(oom).compareTo(e.getZMin(oom)) == -1
                || getZMin(oom).compareTo(e.getZMax(oom)) == 1;
    }

    /**
     * @param e V3D_AABB The envelope to test if it is contained. It is assumed
     * to be in the same Y plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} contains {@code e}.
     */
    public boolean contains(V3D_AABBY e, int oom) {
        return getXMax(oom).compareTo(e.getXMax(oom)) != -1
                && getXMin(oom).compareTo(e.getXMin(oom)) != 1
                && getZMax(oom).compareTo(e.getZMax(oom)) != -1
                && getZMin(oom).compareTo(e.getZMin(oom)) != 1;
    }

    /**
     * @param p The point to test if it is contained. It is assumed
     * to be in the same Y plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code} true iff {@code this} contains {@code p}.
     */
    public boolean contains(V3D_Point p, int oom) {
        return getXMax(oom).compareTo(p.getX(oom, RoundingMode.FLOOR)) != -1
                && getXMin(oom).compareTo(p.getX(oom, RoundingMode.CEILING)) != 1
                && getZMax(oom).compareTo(p.getZ(oom, RoundingMode.FLOOR)) != -1
                && getZMin(oom).compareTo(p.getZ(oom, RoundingMode.CEILING)) != 1;
    }

    /**
     * @param l The line to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this contains {@code l}
     */
    public boolean contains(V3D_LineSegment l, int oom, RoundingMode rm) {
        return contains(l.getP(), oom) && contains(l.getQ(oom, rm), oom);
    }

    /**
     * @param p The point to test for intersection. It is assumed
     * to be in the same Y plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean intersects(V3D_Point p, int oom, RoundingMode rm) {
        return intersects(p.getX(oom, rm), p.getZ(oom, rm), oom);
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean intersects(BigRational x, BigRational z, int oom) {
        return x.compareTo(getXMin(oom)) != -1
                && x.compareTo(getXMax(oom)) != 1
                && z.compareTo(getZMin(oom)) != -1
                && z.compareTo(getZMax(oom)) != 1;
    }
}
