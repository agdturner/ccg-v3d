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
 * An Axis Aligned Bounding Box with a single z value. If {@link xMin} &LT;
 * {@link xMax} and {@link yMin} &LT; {@link yMax} the bounding box defines a
 * rectangular area with a normal vector parallel to the Z axis. If
 * {@link xMin} = {@link xMax} or {@link yMin} = {@link yMax} the bounding box
 * is a line segment parallel to either the Y axis or X axis respectively. If
 * {@link xMin} = {@link xMax} and {@link yMin} = {@link yMax} the bounding box
 * is a point. It is wanted to calculate if there is intersection/containment of
 * finite geometries in the V3D_AABB instance. A general rectangle cannot be
 * used due to recursive complications.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_AABBZ extends V3D_AABB2D {

    private static final long serialVersionUID = 1L;

    /**
     * The z value.
     */
    private final BigRational z;

    /**
     * For storing the plane, the z value defines the plane. new
     * V3D_Plane(getll(), V3D_Vector.K);
     */
    protected V3D_Plane zpl;
    /**
     * The minimum x-coordinate.
     */
    private final BigRational xMin;

    /**
     * The maximum x-coordinate.
     */
    private final BigRational xMax;

    /**
     * The minimum y-coordinate.
     */
    private final BigRational yMin;

    /**
     * The maximum y-coordinate.
     */
    private final BigRational yMax;

    /**
     * @param e An envelope.
     */
    public V3D_AABBZ(V3D_AABBZ e) {
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     * @param z What {@link #z} is set to.
     */
    public V3D_AABBZ(V3D_Environment env, int oom, RoundingMode rm,
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
     * @param yMin What {@link yMin} is set to.
     * @param yMax What {@link yMax} is set to.
     * @param z What {@link #z} is set to.
     */
    public V3D_AABBZ(V3D_Environment env, int oom, RoundingMode rm,
            BigRational xMin, BigRational xMax,
            BigRational yMin, BigRational yMax, BigRational z) {
        this(oom, rm, new V3D_Point(env, xMin, yMin, z),
                new V3D_Point(env, xMax, yMax, z));
    }

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @param gs The geometries used to form the envelope.
     */
    public V3D_AABBZ(int oom, RoundingMode rm, V3D_FiniteGeometry... gs) {
        V3D_AABBZ e = new V3D_AABBZ(gs[0], oom, rm);
        for (V3D_FiniteGeometry g : gs) {
            e = e.union(new V3D_AABBZ(g, oom, rm), oom, rm);
        }
        env = e.env;
        offset = e.offset;
        yMin = e.yMin;
        yMax = e.yMax;
        xMin = e.xMin;
        xMax = e.xMax;
        z = e.z;
        zpl = e.zpl;
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
    public V3D_AABBZ(V3D_FiniteGeometry g, int oom, RoundingMode rm) {
        this(oom, rm, g.getPointsArray(oom, rm));
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points The points used to form the envelop.
     * @throws RuntimeException if points.length == 0.
     */
    public V3D_AABBZ(int oom, RoundingMode rm, V3D_Point... points) {
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
                yMin = points[0].getY(oom, RoundingMode.FLOOR);
                yMax = points[0].getY(oom, RoundingMode.CEILING);
                top = points[0];
                left = top;
                right = top;
                bottom = top;
            }
            default -> {
                offset = V3D_Vector.ZERO;
                BigRational xmin = points[0].getX(oom, RoundingMode.FLOOR);
                BigRational xmax = points[0].getX(oom, RoundingMode.CEILING);
                BigRational ymin = points[0].getY(oom, RoundingMode.FLOOR);
                BigRational ymax = points[0].getY(oom, RoundingMode.CEILING);
                for (int i = 1; i < points.length; i++) {
                    xmin = BigRational.min(xmin, points[i].getX(oom, RoundingMode.FLOOR));
                    xmax = BigRational.max(xmax, points[i].getX(oom, RoundingMode.CEILING));
                    ymin = BigRational.min(ymin, points[i].getY(oom, RoundingMode.FLOOR));
                    ymax = BigRational.max(ymax, points[i].getY(oom, RoundingMode.CEILING));
                }
                this.xMin = xmin;
                this.xMax = xmax;
                this.yMin = ymin;
                this.yMax = ymax;
            }
        }
        env = points[0].env;
        z = points[0].getZ(oom, rm);

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
                + ", yMin=" + getYMin(oom, rm) + ", yMax=" + getYMax(oom, rm)
                + ", z=" + getZ(oom, rm).toString() + ")";
    }

    /**
     * Test for equality.
     *
     * @param e The V3D_AABBZ to test for equality with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} and {@code e} are equal.
     */
    public boolean equals(V3D_AABBZ e, int oom, RoundingMode rm) {
        return getXMin(oom).compareTo(e.getXMin(oom)) == 0
                && getXMax(oom).compareTo(e.getXMax(oom)) == 0
                && getYMin(oom).compareTo(e.getYMin(oom)) == 0
                && getYMax(oom).compareTo(e.getYMax(oom)) == 0
                && getZ(oom, rm).compareTo(e.getZ(oom, rm)) == 0;
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
     * For getting {@link #yMin} rounded. RoundingMode.FLOOR is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #yMin} with {@link #offset}.dy added.
     */
    public BigRational getYMin(int oom) {
        return getYMin(oom, RoundingMode.FLOOR);
    }

    /**
     * For getting {@link #yMin} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #yMin} with {@link #offset}.dy added.
     */
    public BigRational getYMin(int oom, RoundingMode rm) {
        return yMin.add(offset.getDY(oom - 2, rm));
    }

    /**
     * For getting {@link #yMax} rounded. RoundingMode.CEILING is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #yMax} with {@link #offset}.dy added.
     */
    public BigRational getYMax(int oom) {
        return getYMax(oom, RoundingMode.CEILING);
    }

    /**
     * For getting {@link #yMax} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #yMax} with {@link #offset}.dy added.
     */
    public BigRational getYMax(int oom, RoundingMode rm) {
        return yMax.add(offset.getDY(oom - 2, rm));
    }

    /**
     * For getting {@link #z} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #yMax} rounded.
     */
    public BigRational getZ(int oom, RoundingMode rm) {
        return yMax.add(offset.getDY(oom - 2, rm));
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #ll} setting it first if it is null.
     */
    @Override
    public V3D_Point getll(int oom, RoundingMode rm) {
        if (ll == null) {
            ll = new V3D_Point(env, getXMin(oom), getYMin(oom), getZ(oom, rm));
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
            lu = new V3D_Point(env, getXMin(oom), getYMax(oom), getZ(oom, rm));
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
            uu = new V3D_Point(env, getXMax(oom), getYMax(oom), getZ(oom, rm));
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
            ul = new V3D_Point(env, getXMax(oom), getYMin(oom), getZ(oom, rm));
        }
        return ul;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The {@link #zpl} initialising it first if it is null.
     */
    public V3D_Plane getZPl(int oom, RoundingMode rm) {
        if (zpl == null) {
            zpl = new V3D_Plane(getll(oom, rm), V3D_Vector.K);
        }
        return zpl;
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
            BigRational ymin = getYMin(oom);
            BigRational ymax = getYMax(oom);
            getZ(oom, rm);
            if (ymin.compareTo(ymax) == 0) {
                left = new V3D_Point(env, xmin, ymax, z);
            } else {
                left = new V3D_LineSegment(
                        new V3D_Point(env, xmin, ymin, z),
                        new V3D_Point(env, xmin, ymax, z), oom, rm);
            }
        }
        return left;
    }

    /**
     * The left plane is orthogonal to the zPlane. With a normal pointing away.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #lpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane getLeftPlane(int oom, RoundingMode rm) {
        if (lpl == null) {
            lpl = new V3D_Plane(new V3D_Point(env, getXMin(oom), getYMin(oom),
                    getZ(oom, rm)), V3D_Vector.NJ);
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
            BigRational ymin = getYMin(oom);
            BigRational ymax = getYMax(oom);
            if (ymin.compareTo(ymax) == 0) {
                right = new V3D_Point(env, xmax, ymax, z);
            } else {
                right = new V3D_LineSegment(
                        new V3D_Point(env, xmax, ymin, z),
                        new V3D_Point(env, xmax, ymax, z), oom, rm);
            }
        }
        return right;
    }

    /**
     * The right plane is orthogonal to the zPlane. With a normal pointing away.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #rpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane getRightPlane(int oom, RoundingMode rm) {
        if (rpl == null) {
            rpl = new V3D_Plane(new V3D_Point(env, getXMax(oom), getYMax(oom),
                    getZ(oom, rm)), V3D_Vector.J);
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
            BigRational ymax = getYMax(oom);
            if (xmin.compareTo(xmax) == 0) {
                top = new V3D_Point(env, xmin, ymax, z);
            } else {
                top = new V3D_LineSegment(
                        new V3D_Point(env, xmin, ymax, z),
                        new V3D_Point(env, xmax, ymax, z), oom, rm);
            }
        }
        return top;
    }

    /**
     * The top plane is orthogonal to the zPlane. With a normal pointing away.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #tpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane getTopPlane(int oom, RoundingMode rm) {
        if (tpl == null) {
            tpl = new V3D_Plane(new V3D_Point(env, getXMax(oom), getYMax(oom),
                    getZ(oom, rm)), V3D_Vector.I);
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
            BigRational ymin = getYMin(oom);
            if (xmin.compareTo(xmax) == 0) {
                bottom = new V3D_Point(env, xmin, ymin, z);
            } else {
                bottom = new V3D_LineSegment(
                        new V3D_Point(env, xmin, ymin, z),
                        new V3D_Point(env, xmax, ymin, z), oom, rm);
            }
        }
        return bottom;
    }

    /**
     * The bottom plane is orthogonal to the zPlane. With a normal pointing
     * away.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #bpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane getBottomPlane(int oom, RoundingMode rm) {
        if (bpl == null) {
            bpl = new V3D_Plane(new V3D_Point(env, getXMin(oom), getYMin(oom),
                    getZ(oom, rm)), V3D_Vector.NI);
        }
        return bpl;
    }

    /**
     * Calculate and return the approximate (or exact) centroid.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The approximate or exact centre of this.
     */
    @Override
    public V3D_Point getCentroid(int oom, RoundingMode rm) {
        return new V3D_Point(env,
                getXMax(oom).add(getXMin(oom)).divide(2),
                getYMax(oom).add(getYMin(oom)).divide(2),
                getZ(oom, rm));
    }

    /**
     * @param e The V3D_AABBZ to union with this. It is assumed to be in the
     * same Z plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return a V3D_AABBZ which is {@code this} union {@code e}.
     */
    public V3D_AABBZ union(V3D_AABBZ e, int oom, RoundingMode rm) {
        if (this.contains(e, oom)) {
            return this;
        } else {
            return new V3D_AABBZ(env, oom, rm,
                    BigRational.min(e.getXMin(oom), getXMin(oom)),
                    BigRational.max(e.getXMax(oom), getXMax(oom)),
                    BigRational.min(e.getYMin(oom), getYMin(oom)),
                    BigRational.max(e.getYMax(oom), getYMax(oom)),
                    getZ(oom, rm));
        }
    }

    /**
     * @param e The V3D_AABBZ to test for intersection. It is assumed to be in
     * the same Z plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with {@code e} it the {@code oom}
     * level of precision.
     */
    public boolean intersects(V3D_AABBZ e, int oom) {
        if (isBeyond(e, oom)) {
            return !e.isBeyond(this, oom);
        } else {
            return true;
        }
    }

    /**
     * @param e The V3D_AABBZ to test if {@code this} is beyond. It is assumed
     * to be in the same Z plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} is beyond {@code e} (i.e. they do
     * not touch or intersect).
     */
    public boolean isBeyond(V3D_AABBZ e, int oom) {
        return getXMax(oom).compareTo(e.getXMin(oom)) == -1
                || getXMin(oom).compareTo(e.getXMax(oom)) == 1
                || getYMax(oom).compareTo(e.getYMin(oom)) == -1
                || getYMin(oom).compareTo(e.getYMax(oom)) == 1;
    }

    /**
     * @param e V3D_AABB The envelope to test if it is contained. It is assumed
     * to be in the same Z plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} contains {@code e}.
     */
    public boolean contains(V3D_AABBZ e, int oom) {
        return getXMax(oom).compareTo(e.getXMax(oom)) != -1
                && getXMin(oom).compareTo(e.getXMin(oom)) != 1
                && getYMax(oom).compareTo(e.getYMax(oom)) != -1
                && getYMin(oom).compareTo(e.getYMin(oom)) != 1;
    }

    /**
     * @param p The point to test if it is contained. It is assumed to be in the
     * same Z plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code} true iff {@code this} contains {@code p}.
     */
    @Override
    public boolean contains(V3D_Point p, int oom) {
        return getXMax(oom).compareTo(p.getX(oom, RoundingMode.FLOOR)) != -1
                && getXMin(oom).compareTo(p.getX(oom, RoundingMode.CEILING)) != 1
                && getYMax(oom).compareTo(p.getY(oom, RoundingMode.FLOOR)) != -1
                && getYMin(oom).compareTo(p.getY(oom, RoundingMode.CEILING)) != 1;
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
     * @param p The point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this intersects with {@code p}
     */
    @Override
    public boolean intersects(V3D_Point p, int oom, RoundingMode rm) {
        return intersects(p.getX(oom, rm), p.getY(oom, rm), oom);
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with a point defined by
     * {@code x}, {@code y}, and {@link #z}}.
     */
    public boolean intersects(BigRational x, BigRational y, int oom) {
        return x.compareTo(getXMin(oom)) != -1
                && x.compareTo(getXMax(oom)) != 1
                && y.compareTo(getYMin(oom)) != -1
                && y.compareTo(getYMax(oom)) != 1;
    }

    /**
     * Gets the intersect {@code left} with {@code ls} where {@code ls} is a
     * side either {@link #top}, {@link #bottom}, {@link #left} or
     * {@link #right} when a line segment.
     *
     * @param ls The line segment to get the intersect with left. The line
     * segment must be lying in the same zPlane.
     * @param l The line to get intersection with this. The line must be lying
     * in the same zPlane.
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
        BigRational y1 = ls.l.p.getY(oom, rm);
        BigRational y2 = ls.l.q.getY(oom, rm);
        BigRational y3 = l.p.getY(oom, rm);
        BigRational y4 = l.q.getY(oom, rm);
        BigRational den = V3D_AABB.getIntersectDenominator(x1, x2, x3, x4, y1, y2, y3, y4);
        V3D_Geometry li = getIntersect(ls.l, l, den, x1, x2, x3, x4, y1, y2, y3, y4, oom, rm);
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
     * @param den getIntersectDenominator(x1, x2, x3, x4, y1, y2, y3, y4)
     * @param x1 getP().getX(oom, rm)
     * @param x2 getQ(oom, rm).getX(oom, rm)
     * @param x3 left.getP().getX(oom, rm)
     * @param x4 left.getQ(oom, rm).getX(oom, rm)
     * @param y1 p.getY(oom, rm)
     * @param y2 q.getY(oom, rm)
     * @param y3 left.p.getY(oom, rm)
     * @param y4 left.q.getY(oom, rm)
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The geometry or null if there is no intersection.
     */
    public V3D_Geometry getIntersect(V3D_Line l0, V3D_Line l, BigRational den,
            BigRational x1, BigRational x2, BigRational x3, BigRational x4,
            BigRational y1, BigRational y2, BigRational y3, BigRational y4,
            int oom, RoundingMode rm) {
        if (V3D_AABB.intersects(l0, l, den, oom, rm)) {
            // Check for coincident lines
            if (l0.equals(l, oom - 1, rm)) {
                return l;
            }
            BigRational x1y2sy1x2 = ((x1.multiply(y2)).subtract(y1.multiply(x2)));
            BigRational x3y4sy3x4 = ((x3.multiply(y4)).subtract(y3.multiply(x4)));
            BigRational numx = (x1y2sy1x2.multiply(x3.subtract(x4))).subtract(
                    (x1.subtract(x2)).multiply(x3y4sy3x4));
            BigRational numy = (x1y2sy1x2.multiply(y3.subtract(y4))).subtract(
                    (y1.subtract(y2)).multiply(x3y4sy3x4));
            return new V3D_Point(env, numx.divide(den), numy.divide(den),
                    getZ(oom, rm));
        }
        return null;
    }
}
