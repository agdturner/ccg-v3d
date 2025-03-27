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
import java.math.RoundingMode;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * An Axis Aligned Bounding Box with a single y value. If {@link xMin} &LT;
 * {@link xMax} and {@link zMin} &LT; {@link zMax} the bounding box defines a
 * rectangular area with a normal vector parallel to the Y axis. If
 * {@link xMin} = {@link xMax} or {@link zMin} = {@link zMax} the bounding box
 * is a line segment parallel to either the Z axis or X axis respectively. If
 * {@link xMin} = {@link xMax} and {@link zMin} = {@link zMax} the bounding box
 * is a point. It is wanted to calculate if there is intersection/containment of
 * finite geometries in the V3D_AABB instance. A general rectangle cannot be
 * used due to recursive complications.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_AABBY extends V3D_AABB2D {

    private static final long serialVersionUID = 1L;

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
     * @param e An envelope.
     */
    public V3D_AABBY(V3D_AABBY e) {
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
                top = points[0];
                left = top;
                right = top;
                bottom = top;
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
     * Test for equality.
     *
     * @param e The V3D_AABBZ to test for equality with this.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} and {@code e} are equal.
     */
    public boolean equals(V3D_AABBY e, int oom, RoundingMode rm) {
        return this.getXMin(oom).compareTo(e.getXMin(oom)) == 0
                && this.getXMax(oom).compareTo(e.getXMax(oom)) == 0
                && this.getY(oom, rm).compareTo(e.getY(oom, rm)) == 0
                && this.getZMin(oom).compareTo(e.getZMin(oom)) == 0
                && this.getZMax(oom).compareTo(e.getZMax(oom)) == 0;
    }

    /**
     * For getting {@link #xMin} rounded. RoundingMode.FLOOR is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #xMin} with {@link #offset}.dx added.
     */
    public BigRational getXMin(int oom) {
        return getXMin(oom, RoundingMode.FLOOR);
    }

    /**
     * For getting {@link #xMin} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #xMin} with {@link #offset}.dx added.
     */
    public BigRational getXMin(int oom, RoundingMode rm) {
        return xMin.add(offset.getDX(oom - 2, rm));
    }

    /**
     * For getting {@link #xMax} rounded. RoundingMode.CEILING is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #xMax} with {@link #offset}.dx added.
     */
    public BigRational getXMax(int oom) {
        return getXMax(oom, RoundingMode.CEILING);
    }

    /**
     * For getting {@link #xMax} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #xMax} with {@link #offset}.dx added.
     */
    public BigRational getXMax(int oom, RoundingMode rm) {
        return xMax.add(offset.getDX(oom - 2, rm));
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #y} with {@link #offset}.dy added.
     */
    public BigRational getY(int oom, RoundingMode rm) {
        return y.add(offset.getDY(oom - 2, rm));
    }

    /**
     * For getting {@link #zMin} rounded. RoundingMode.FLOOR is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #zMin} with {@link #offset}.dz added.
     */
    public BigRational getZMin(int oom) {
        return getZMin(oom, RoundingMode.FLOOR);
    }

    /**
     * For getting {@link #zMin} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #zMin} with {@link #offset}.dz added.
     */
    public BigRational getZMin(int oom, RoundingMode rm) {
        return zMin.add(offset.getDZ(oom - 2, rm));
    }

    /**
     * For getting {@link #zMax} rounded. RoundingMode.CEILING is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #zMax} with {@link #offset}.dz added.
     */
    public BigRational getZMax(int oom) {
        return getZMax(oom, RoundingMode.CEILING);
    }

    /**
     * For getting {@link #zMax} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #zMax} with {@link #offset}.dz added.
     */
    public BigRational getZMax(int oom, RoundingMode rm) {
        return zMax.add(offset.getDZ(oom - 2, rm));
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #ll} setting it first if it is null.
     */
    @Override
    public V3D_Point getll(int oom, RoundingMode rm) {
        if (ll == null) {
            ll = new V3D_Point(env, getXMin(oom, rm), getY(oom, rm), getZMin(oom, rm));
        }
        return ll;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #lu} setting it first if it is null.
     */
    @Override
    public V3D_Point getlu(int oom, RoundingMode rm) {
        if (lu == null) {
            lu = new V3D_Point(env, xMin, y, zMax);
        }
        return lu;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #uu} setting it first if it is null.
     */
    @Override
    public V3D_Point getuu(int oom, RoundingMode rm) {
        if (uu == null) {
            uu = new V3D_Point(env, xMax, y, zMax);
        }
        return uu;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #ul} setting it first if it is null.
     */
    @Override
    public V3D_Point getul(int oom, RoundingMode rm) {
        if (ul == null) {
            ul = new V3D_Point(env, xMax, y, zMin);
        }
        return ul;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The {@link #ypl} initialising it first if it is null.
     */
    public V3D_Plane getYPl(int oom, RoundingMode rm) {
        if (ypl == null) {
            ypl = new V3D_Plane(getll(oom, rm), V3D_Vector.J);
        }
        return ypl;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the left of the envelope.
     */
    @Override
    public V3D_FiniteGeometry getLeft(int oom, RoundingMode rm) {
        if (left == null) {
            BigRational xmin = getXMin(oom);
            BigRational ty = getY(oom, rm);
            BigRational zmin = getZMin(oom);
            BigRational zmax = getZMax(oom);
            if (zmin.compareTo(zmax) == 0) {
                left = new V3D_Point(env, xmin, ty, zmax);
            } else {
                left = new V3D_LineSegment(
                        new V3D_Point(env, xmin, ty, zmin),
                        new V3D_Point(env, xmin, ty, zmax), oom, rm);
            }
        }
        return left;
    }

    /**
     * The left plane is orthogonal to the yPlane. With a normal pointing away.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #lpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane getLeftPlane(int oom, RoundingMode rm) {
        if (lpl == null) {
            lpl = new V3D_Plane(new V3D_Point(env, getXMin(oom), getY(oom, rm), 
                    getZMin(oom)), V3D_Vector.NI);
        }
        return lpl;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the right of the envelope.
     */
    @Override
    public V3D_FiniteGeometry getRight(int oom, RoundingMode rm) {
        if (right == null) {
            BigRational xmax = getXMax(oom);
            BigRational ty = getY(oom, rm);
            BigRational zmin = getZMin(oom);
            BigRational zmax = getZMax(oom);
            if (zmin.compareTo(zmax) == 0) {
                right = new V3D_Point(env, xmax, ty, zmax);
            } else {
                right = new V3D_LineSegment(
                        new V3D_Point(env, xmax, ty, zmin),
                        new V3D_Point(env, xmax, ty, zmax), oom, rm);
            }
        }
        return right;
    }

    /**
     * The right plane is orthogonal to the yPlane. With a normal pointing away.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #rpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane getRightPlane(int oom, RoundingMode rm) {
        if (rpl == null) {
            rpl = new V3D_Plane(new V3D_Point(env, getXMax(oom), getY(oom, rm), 
                    getZMax(oom)), V3D_Vector.I);
        }
        return rpl;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the top of the envelope.
     */
    @Override
    public V3D_FiniteGeometry getTop(int oom, RoundingMode rm) {
        if (top == null) {
            BigRational xmin = getXMin(oom);
            BigRational xmax = getXMax(oom);
            BigRational ty = getY(oom, rm);
            BigRational zmax = getZMax(oom);
            if (xmin.compareTo(xmax) == 0) {
                top = new V3D_Point(env, xmin, ty, zmax);
            } else {
                top = new V3D_LineSegment(
                        new V3D_Point(env, xmin, ty, zmax),
                        new V3D_Point(env, xmax, ty, zmax), oom, rm);
            }
        }
        return top;
    }

    /**
     * The top plane is orthogonal to the yPlane. With a normal pointing away.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #tpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane getTopPlane(int oom, RoundingMode rm) {
        if (tpl == null) {
            tpl = new V3D_Plane(new V3D_Point(env, getXMax(oom), getY(oom, rm), 
                    getZMax(oom)), V3D_Vector.K);
        }
        return tpl;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the bottom of the envelope.
     */
    @Override
    public V3D_FiniteGeometry getBottom(int oom, RoundingMode rm) {
        if (bottom == null) {
            BigRational xmin = getXMin(oom);
            BigRational xmax = getXMax(oom);
            BigRational ty = getY(oom, rm);
            BigRational zmin = getZMin(oom);
            if (xmin.compareTo(xmax) == 0) {
                bottom = new V3D_Point(env, xmin, ty, zmin);
            } else {
                bottom = new V3D_LineSegment(
                        new V3D_Point(env, xmin, ty, zmin),
                        new V3D_Point(env, xmax, ty, zmin), oom, rm);
            }
        }
        return bottom;
    }

    /**
     * The bottom plane is orthogonal to the yPlane. With a normal pointing
     * away.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #bpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane getBottomPlane(int oom, RoundingMode rm) {
        if (bpl == null) {
            bpl = new V3D_Plane(new V3D_Point(env, getXMin(oom), getY(oom, rm),
                    getZMin(oom)), V3D_Vector.NK);
        }
        return bpl;
    }

    /**
     * Calculate and return the approximate (or exact) centroid of the envelope.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The approximate or exact centre of this.
     */
    @Override
    public V3D_Point getCentroid(int oom, RoundingMode rm) {
        return new V3D_Point(env,
                getXMax(oom).add(getXMin(oom)).divide(2),
                y,
                getZMax(oom).add(getZMin(oom)).divide(2));
    }

    /**
     * @param e The V3D_AABBY to union with this. It is assumed to be in the
     * same Y plane.
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
     * @param e The V3D_AABBY to test for intersection. It is assumed to be in
     * the same Y plane.
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
     * @param p The point to test if it is contained. It is assumed to be in the
     * same Y plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code} true iff {@code this} contains {@code p}.
     */
    @Override
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
     * @return {@code true} if this contains {@code left}
     */
    @Override
    public boolean contains(V3D_LineSegment l, int oom, RoundingMode rm) {
        return contains(l.getP(), oom) && contains(l.getQ(oom, rm), oom);
    }

    /**
     * @param p The point to test for intersection. It is assumed to be in the
     * same Y plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this intersects with {@code p}
     */
    @Override
    public boolean intersects(V3D_Point p, int oom, RoundingMode rm) {
        return intersects(p.getX(oom, rm), p.getZ(oom, rm), oom);
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with a point defined by
     * {@code x}, {@link #y}, and {@code z}}.
     */
    public boolean intersects(BigRational x, BigRational z, int oom) {
        return x.compareTo(getXMin(oom)) != -1
                && x.compareTo(getXMax(oom)) != 1
                && z.compareTo(getZMin(oom)) != -1
                && z.compareTo(getZMax(oom)) != 1;
    }

    /**
     * Gets the intersect {@code left} with {@code ls} where {@code ls} is a
     * side either {@link #top}, {@link #bottom}, {@link #left} or
     * {@link #right} when a line segment.
     *
     * @param ls The line segment to get the intersect with left. The line
     * segment must be lying in the same yPlane.
     * @param l The line to get intersection with this. The line must be lying
     * in the same yPlane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code this} and {@code left}.
     */
    @Override
    public V3D_FiniteGeometry getIntersect(V3D_LineSegment ls, V3D_Line l, 
            int oom, RoundingMode rm) {
        BigRational x1 = ls.getP().getX(oom, rm);
        BigRational x2 = ls.getQ(oom, rm).getX(oom, rm);
        BigRational x3 = l.getP().getX(oom, rm);
        BigRational x4 = l.getQ(oom, rm).getX(oom, rm);
        BigRational z1 = ls.l.p.getZ(oom, rm);
        BigRational z2 = ls.l.q.getZ(oom, rm);
        BigRational z3 = l.p.getZ(oom, rm);
        BigRational z4 = l.q.getZ(oom, rm);
        BigRational den = V3D_AABB.getIntersectDenominator(x1, x2, x3, x4, z1, z2, z3, z4);
        V3D_Geometry li = getIntersect(ls.l, l, den, x1, x2, x3, x4, z1, z2, z3, z4, oom, rm);
        if (li != null) {
            if (li instanceof V3D_Point pli) {
                //if (intersects(pli, oom, rm)) {
                if (ls.isAligned(pli, oom, rm)) {
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
     * @param l0 A line to get the intersect with left. The line must be lying
     * in the same plane as this and left.
     * @param l Line to intersect with.
     * @param den getIntersectDenominator(x1, x2, x3, x4, z1, z2, z3, z4)
     * @param x1 getP().getX(oom, rm)
     * @param x2 getQ(oom, rm).getX(oom, rm)
     * @param x3 left.getP().getX(oom, rm)
     * @param x4 left.getQ(oom, rm).getX(oom, rm)
     * @param z1 p.getZ(oom, rm)
     * @param z2 q.getZ(oom, rm)
     * @param z3 left.p.getZ(oom, rm)
     * @param z4 left.q.getZ(oom, rm)
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The geometry or null if there is no intersection.
     */
    public V3D_Geometry getIntersect(V3D_Line l0, V3D_Line l, BigRational den,
            BigRational x1, BigRational x2, BigRational x3, BigRational x4,
            BigRational z1, BigRational z2, BigRational z3, BigRational z4,
            int oom, RoundingMode rm) {
        if (V3D_AABB.intersects(l0, l, den, oom, rm)) {
            // Check for coincident lines
            if (l0.equals(l, oom - 1, rm)) {
                return l;
            }
            BigRational x1z2sz1x2 = ((x1.multiply(z2)).subtract(z1.multiply(x2)));
            BigRational x3z4sz3x4 = ((x3.multiply(z4)).subtract(z3.multiply(x4)));
            BigRational numx = (x1z2sz1x2.multiply(x3.subtract(x4))).subtract(
                    (x1.subtract(x2)).multiply(x3z4sz3x4));
            BigRational numz = (x1z2sz1x2.multiply(z3.subtract(z4))).subtract(
                    (z1.subtract(z2)).multiply(x3z4sz3x4));
            return new V3D_Point(env, numx.divide(den), getY(oom, rm), 
                    numz.divide(den));
        }
        return null;
    }
}
