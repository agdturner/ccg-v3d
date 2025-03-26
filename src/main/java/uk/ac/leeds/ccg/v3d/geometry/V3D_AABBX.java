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
 * An Axis Aligned Bounding Box with a single x value. If {@link yMin} &LT;
 * {@link yMax} and {@link zMin} &LT; {@link zMax} the bounding box defines a
 * rectangular area with a normal vector parallel to the X axis. If
 * {@link yMin} = {@link yMax} or {@link zMin} = {@link zMax} the bounding box
 * is a line segment parallel to either the Z axis or Y axis respectively. If
 * {@link yMin} = {@link yMax} and {@link zMin} = {@link zMax} the bounding box
 * is a point. It is wanted to calculate if there is intersection/containment of
 * finite geometries in the V3D_AABB instance. A general rectangle cannot 
 * be used due to recursive complications.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_AABBX extends V3D_AABB2D {

    private static final long serialVersionUID = 1L;

    /**
     * The x value.
     */
    private final BigRational x;

    /**
     * For storing the plane, the x value defines the plane. new
     * V3D_Plane(getll(), V3D_Vector.I);
     */
    protected V3D_Plane xpl;

    /**
     * The minimum y-coordinate.
     */
    private final BigRational yMin;

    /**
     * The maximum y-coordinate.
     */
    private final BigRational yMax;

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
    public V3D_AABBX(V3D_AABBX e) {
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     * @param z What {@link #z} is set to.
     */
    public V3D_AABBX(V3D_Environment env, int oom, RoundingMode rm,
            BigRational x, BigRational y, BigRational z) {
        this(oom, rm, new V3D_Point(env, x, y, z));
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param x What {@link #x} is set to.
     * @param yMin What {@link yMin} is set to.
     * @param yMax What {@link yMax} is set to.
     * @param zMin What {@link zMin} is set to.
     * @param zMax What {@link zMax} is set to.
     */
    public V3D_AABBX(V3D_Environment env, int oom, RoundingMode rm,
            BigRational x, BigRational yMin, BigRational yMax,
            BigRational zMin, BigRational zMax) {
        this(oom, rm, new V3D_Point(env, x, yMin, zMin),
                new V3D_Point(env, x, yMax, zMax));
    }

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @param gs The geometries used to form the envelope.
     */
    public V3D_AABBX(int oom, RoundingMode rm, V3D_FiniteGeometry... gs) {
        super();
        V3D_AABBX e = new V3D_AABBX(gs[0], oom, rm);
        for (V3D_FiniteGeometry g : gs) {
            e = e.union(new V3D_AABBX(g, oom, rm), oom, rm);
        }
        env = e.env;
        offset = e.offset;
        yMin = e.yMin;
        yMax = e.yMax;
        zMin = e.zMin;
        zMax = e.zMax;
        x = e.x;
        xpl = e.xpl;
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
    public V3D_AABBX(V3D_FiniteGeometry g, int oom, RoundingMode rm) {
        this(oom, rm, g.getPointsArray(oom, rm));
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points The points used to form the envelop.
     * @throws RuntimeException if points.length == 0.
     */
    public V3D_AABBX(int oom, RoundingMode rm, V3D_Point... points) {
        //offset = points[0].offset;
        //offset = V3D_Vector.ZERO;
        int len = points.length;
        switch (len) {
            case 0 ->
                throw new RuntimeException("Cannot create envelope from an empty "
                        + "collection of points.");
            case 1 -> {
                offset = V3D_Vector.ZERO;
                yMin = points[0].getY(oom, RoundingMode.FLOOR);
                yMax = points[0].getY(oom, RoundingMode.CEILING);
                zMin = points[0].getZ(oom, RoundingMode.FLOOR);
                zMax = points[0].getZ(oom, RoundingMode.CEILING);
                top = points[0];
                left = top;
                right = top;
                bottom = top;
            }
            default -> {
                offset = V3D_Vector.ZERO;
                BigRational ymin = points[0].getY(oom, RoundingMode.FLOOR);
                BigRational ymax = points[0].getY(oom, RoundingMode.CEILING);
                BigRational zmin = points[0].getZ(oom, RoundingMode.FLOOR);
                BigRational zmax = points[0].getZ(oom, RoundingMode.CEILING);
                for (int i = 1; i < points.length; i++) {
                    ymin = BigRational.min(ymin, points[i].getY(oom, RoundingMode.FLOOR));
                    ymax = BigRational.max(ymax, points[i].getY(oom, RoundingMode.CEILING));
                    zmin = BigRational.min(zmin, points[i].getZ(oom, RoundingMode.FLOOR));
                    zmax = BigRational.max(zmax, points[i].getZ(oom, RoundingMode.CEILING));
                }
                this.yMin = ymin;
                this.yMax = ymax;
                this.zMin = zmin;
                this.zMax = zmax;
            }
        }
        env = points[0].env;
        x = points[0].getX(oom, rm);
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
                + "(x=" + x.toString()
                + ", yMin=" + getYMin(oom, rm) + ", yMax=" + getYMax(oom, rm)
                + ", zMin=" + getZMin(oom, rm) + ", zMax=" + getZMax(oom, rm)
                + ")";
    }

    /**
     * Test for equality.
     *
     * @param e The V3D_AABBZ to test for equality with this.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} and {@code e} are equal.
     */
    public boolean equals(V3D_AABBX e, int oom) {
        return x.compareTo(e.x) == 0
                && getYMin(oom).compareTo(e.getYMin(oom)) == 0
                && getYMax(oom).compareTo(e.getYMax(oom)) == 0
                && getZMin(oom).compareTo(e.getZMin(oom)) == 0
                && getZMax(oom).compareTo(e.getZMax(oom)) == 0;
    }

    /**
     * For getting {@link #yMin} rounded. RoundingMode.FLOOR is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #yMin} rounded.
     */
    public BigRational getYMin(int oom) {
        return getYMin(oom, RoundingMode.FLOOR);
    }

    /**
     * For getting {@link #yMin} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #yMin} rounded.
     */
    public BigRational getYMin(int oom, RoundingMode rm) {
        return yMin.add(offset.getDY(oom - 2, rm));
    }

    /**
     * For getting {@link #yMax} rounded. RoundingMode.CEILING is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #yMax} rounded.
     */
    public BigRational getYMax(int oom) {
        return getYMax(oom, RoundingMode.CEILING);
    }

    /**
     * For getting {@link #yMax} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #yMax} rounded.
     */
    public BigRational getYMax(int oom, RoundingMode rm) {
        return yMax.add(offset.getDY(oom - 2, rm));
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
        return zMax.add(offset.getDZ(oom - 2, rm));
    }

    /**
     * @return {@link #ll} setting it first if it is null.
     */
    @Override
    public V3D_Point getll() {
        if (ll == null) {
            ll = new V3D_Point(env, x, yMin, zMin);
        }
        return ll;
    }

    /**
     * @return {@link #lu} setting it first if it is null.
     */
    @Override
    public V3D_Point getlu() {
        if (lu == null) {
            lu = new V3D_Point(env, x, yMin, zMax);
        }
        return lu;
    }

    /**
     * @return {@link #uu} setting it first if it is null.
     */
    @Override
    public V3D_Point getuu() {
        if (uu == null) {
            uu = new V3D_Point(env, x, yMax, zMax);
        }
        return uu;
    }

    /**
     * @return {@link #ul} setting it first if it is null.
     */
    @Override
    public V3D_Point getul() {
        if (ul == null) {
            ul = new V3D_Point(env, x, yMax, zMin);
        }
        return ul;
    }

    /**
     * @return The {@link #ypl} initialising it first if it is null.
     */
    public V3D_Plane getXPl() {
        if (xpl == null) {
            xpl = new V3D_Plane(getll(), V3D_Vector.I);
        }
        return xpl;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the left of the envelope.
     */
    @Override
    public V3D_FiniteGeometry getLeft(int oom, RoundingMode rm) {
        if (left == null) {
            BigRational ymin = getYMin(oom);
            BigRational zmin = getZMin(oom);
            BigRational zmax = getZMax(oom);
            if (zmin.compareTo(zmax) == 0) {
                left = new V3D_Point(env, x, ymin, zmax);
            } else {
                left = new V3D_LineSegment(
                        new V3D_Point(env, x, ymin, zmin),
                        new V3D_Point(env, x, ymin, zmax), oom, rm);
            }
        }
        return left;
    }

    /**
     * The left plane is orthogonal to the xPlane. With a normal pointing away.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #lpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane getLeftPlane(int oom, RoundingMode rm) {
        if (lpl == null) {
            lpl = new V3D_Plane(new V3D_Point(env, x, getYMin(oom), getZMin(oom)),
                    V3D_Vector.NJ);
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
            BigRational ymax = getYMax(oom);
            BigRational zmin = getZMin(oom);
            BigRational zmax = getZMax(oom);
            if (zmin.compareTo(zmax) == 0) {
                right = new V3D_Point(env, x, ymax, zmax);
            } else {
                right = new V3D_LineSegment(
                        new V3D_Point(env, x, ymax, zmin),
                        new V3D_Point(env, x, ymax, zmax), oom, rm);
            }
        }
        return right;
    }

    /**
     * The right plane is orthogonal to the xPlane. With a normal pointing away.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #rpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane getRightPlane(int oom, RoundingMode rm) {
        if (rpl == null) {
            rpl = new V3D_Plane(new V3D_Point(env, x, getYMax(oom), getZMax(oom)),
                    V3D_Vector.J);
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
            BigRational ymin = getYMin(oom);
            BigRational ymax = getYMax(oom);
            BigRational zmax = getZMax(oom);
            if (ymin.compareTo(ymax) == 0) {
                top = new V3D_Point(env, x, ymin, zmax);
            } else {
                top = new V3D_LineSegment(
                        new V3D_Point(env, x, ymin, zmax),
                        new V3D_Point(env, x, ymax, zmax), oom, rm);
            }
        }
        return top;
    }

    /**
     * The top plane is orthogonal to the xPlane. With a normal pointing away.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #tpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane getTopPlane(int oom, RoundingMode rm) {
        if (tpl == null) {
            tpl = new V3D_Plane(new V3D_Point(env, x, getYMax(oom), getZMax(oom)),
                    V3D_Vector.K);
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
            BigRational ymin = getYMin(oom);
            BigRational ymax = getYMax(oom);
            BigRational zmin = getZMin(oom);
            if (ymin.compareTo(ymax) == 0) {
                bottom = new V3D_Point(env, x, ymin, zmin);
            } else {
                bottom = new V3D_LineSegment(
                        new V3D_Point(env, x, ymin, zmin),
                        new V3D_Point(env, x, ymax, zmin), oom, rm);
            }
        }
        return bottom;
    }

    /**
     * The bottom plane is orthogonal to the xPlane. With a normal pointing
     * away.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #bpl} initialising first if it is {@code null}.
     */
    @Override
    public V3D_Plane getBottomPlane(int oom, RoundingMode rm) {
        if (bpl == null) {
            bpl = new V3D_Plane(new V3D_Point(env, x, getYMin(oom), getZMin(oom)),
                    V3D_Vector.NK);
        }
        return bpl;
    }

    /**
     * Calculate and return the approximate (or exact) centroid of the envelope.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return The approximate or exact centre of this.
     */
    @Override
    public V3D_Point getCentroid(int oom) {
        return new V3D_Point(env,
                x,
                getYMax(oom).add(getYMin(oom)).divide(2),
                getZMax(oom).add(getZMin(oom)).divide(2));
    }

    /**
     * @param e The V3D_AABBZ to union with this. It is assumed to be in the
     * same Y plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return a V3D_AABBZ which is {@code this} union {@code e}.
     */
    public V3D_AABBX union(V3D_AABBX e, int oom, RoundingMode rm) {
        if (this.contains(e, oom)) {
            return this;
        } else {
            return new V3D_AABBX(env, oom, rm,
                    x,
                    BigRational.min(e.getYMin(oom), getYMin(oom)),
                    BigRational.max(e.getYMax(oom), getYMax(oom)),
                    BigRational.min(e.getZMin(oom), getZMin(oom)),
                    BigRational.max(e.getZMax(oom), getZMax(oom)));
        }
    }

    /**
     * @param e The V3D_AABBX to test for intersection. It is assumed to be in
     * the same Y plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with {@code e} it the {@code oom}
     * level of precision.
     */
    public boolean intersects(V3D_AABBX e, int oom) {
        if (isBeyond(e, oom)) {
            return !e.isBeyond(this, oom);
        } else {
            return true;
        }
    }

    /**
     * @param e The V3D_AABBX to test if {@code this} is beyond. It is assumed
     * to be in the same X plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} is beyond {@code e} (i.e. they do
     * not touch or intersect).
     */
    public boolean isBeyond(V3D_AABBX e, int oom) {
        return getYMax(oom).compareTo(e.getYMin(oom)) == -1
                || getYMin(oom).compareTo(e.getYMax(oom)) == 1
                || getZMax(oom).compareTo(e.getZMin(oom)) == -1
                || getZMin(oom).compareTo(e.getZMax(oom)) == 1;
    }

    /**
     * @param e V3D_AABB The envelope to test if it is contained. It is assumed
     * to be in the same Y plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} contains {@code e}.
     */
    public boolean contains(V3D_AABBX e, int oom) {
        return getYMax(oom).compareTo(e.getYMax(oom)) != -1
                && getYMin(oom).compareTo(e.getYMin(oom)) != 1
                && getZMax(oom).compareTo(e.getZMax(oom)) != -1
                && getZMin(oom).compareTo(e.getZMin(oom)) != 1;
    }

    /**
     * The location of p may get rounded.
     *
     * @param p The point to test if it is contained. It is assumed to be in the
     * same Y plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code} true iff {@code this} contains {@code p}.
     */
    @Override
    public boolean contains(V3D_Point p, int oom) {
        return getYMax(oom).compareTo(p.getY(oom, RoundingMode.FLOOR)) != -1
                && getYMin(oom).compareTo(p.getY(oom, RoundingMode.CEILING)) != 1
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
     * same X plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this intersects with {@code p}
     */
    @Override
    public boolean intersects(V3D_Point p, int oom, RoundingMode rm) {
        return intersects(p.getY(oom, rm), p.getZ(oom, rm), oom);
    }

    /**
     * @param y The y-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with a point defined by
     * {@link #x}, {@code y}, and {@code z}}.
     */
    public boolean intersects(BigRational y, BigRational z, int oom) {
        return y.compareTo(getYMin(oom)) != -1
                && y.compareTo(getYMax(oom)) != 1
                && z.compareTo(getZMin(oom)) != -1
                && z.compareTo(getZMax(oom)) != 1;
    }
    
    /**
     * Gets the intersect {@code left} with {@code ls} where {@code ls} is a side 
     * either {@link #top}, {@link #bottom}, {@link #left} or {@link #right} when a 
     * line segment.
     *
     * @param ls The line segment to get the intersect with left. The line segment 
 must be lying in the same xPlane. 
     * @param l The line to get intersection with this. The line must be lying 
     * in the same xPlane. 
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code this} and {@code left}.
     */
    @Override
    public V3D_FiniteGeometry getIntersect(V3D_LineSegment ls, V3D_Line l, 
            int oom, RoundingMode rm) {
        BigRational y1 = ls.getP().getY(oom, rm);
        BigRational y2 = ls.getQ(oom, rm).getY(oom, rm);
        BigRational y3 = l.getP().getY(oom, rm);
        BigRational y4 = l.getQ(oom, rm).getY(oom, rm);
        BigRational z1 = ls.l.p.getZ(oom, rm);
        BigRational z2 = ls.l.q.getZ(oom, rm);
        BigRational z3 = l.p.getZ(oom, rm);
        BigRational z4 = l.q.getZ(oom, rm);
        BigRational den = V3D_AABB.getIntersectDenominator(y1, y2, y3, y4, z1, z2, z3, z4);
        V3D_Geometry li = getIntersect(ls.l, l, den, y1, y2, y3, y4, z1, z2, z3, z4, oom, rm);
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
     * @param l0 A line to get the intersect with left. The line must be lying in 
 the same plane as this and left. 
     * @param l Line to intersect with.
     * @param den getIntersectDenominator(y1, y2, y3, y4, z1, z2, z3, z4)
     * @param y1 getP().getY(oom, rm)
     * @param y2 getQ(oom, rm).getY(oom, rm)
     * @param y3 left.getP().getY(oom, rm)
     * @param y4 left.getQ(oom, rm).getY(oom, rm)
     * @param z1 p.getZ(oom, rm)
     * @param z2 q.getZ(oom, rm)
     * @param z3 left.p.getZ(oom, rm)
     * @param z4 left.q.getZ(oom, rm)
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The geometry or null if there is no intersection.
     */
    public V3D_Geometry getIntersect(V3D_Line l0, V3D_Line l, BigRational den,
            BigRational y1, BigRational y2, BigRational y3, BigRational y4,
            BigRational z1, BigRational z2, BigRational z3, BigRational z4,
            int oom, RoundingMode rm) {
        if (V3D_AABB.intersects(l0, l, den, oom, rm)) {
            // Check for coincident lines
            if (l0.equals(l, oom - 1, rm)) {
                return l;
            }
            BigRational y1z2sz1y2 = ((y1.multiply(z2)).subtract(z1.multiply(y2)));
            BigRational y3z4sz3y4 = ((y3.multiply(z4)).subtract(z3.multiply(y4)));
            BigRational numy = (y1z2sz1y2.multiply(y3.subtract(y4))).subtract(
                    (y1.subtract(y2)).multiply(y3z4sz3y4));
            BigRational numz = (y1z2sz1y2.multiply(z3.subtract(z4))).subtract(
                    (z1.subtract(z2)).multiply(y3z4sz3y4));
            return new V3D_Point(env, x, numy.divide(den), numz.divide(den));
        }
        return null;
    }
}
