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

import uk.ac.leeds.ccg.v3d.geometry.*;
import ch.obermuhlner.math.big.
double;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.HashSet;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

/**
 * An Axis Aligned Bounding Box with a single z value. If {@link xMin} &LT;
 * {@link xMax} and {@link yMin} &LT; {@link yMax} the bounding box defines a
 * rectangular area with a normal vector parallel to the Z axis. If
 * {@link xMin} = {@link xMax} or {@link yMin} = {@link yMax} the bounding box
 * is a line segment parallel to either the Y axis or X axis respectively. If
 * {@link xMin} = {@link xMax} and {@link yMin} = {@link yMax} the bounding box
 * is a point. It is wanted to calculate if there is intersection/containment of
 * a shape in the V3D_AABB instance. A general rectangle cannot easily be used
 * instead without additional complication.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_AABBZ_d implements Serializable {

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
    public V3D_AABBZ_d(V3D_AABBZ_d e) {
        env = e.env;
        offset = e.offset;
        z = e.z;
        zpl = e.zpl;
        xMin = e.xMin;
        xMax = e.xMax;
        yMin = e.yMin;
        yMax = e.yMax;
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
    public V3D_AABBZ_d(V3D_Environment_d env,
            double x, double y, double z) {
        this(, new V3D_Point_d(env, x, y, z));
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
        this(, new V3D_Point_d(env, xMin, yMin, z),
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
            e = e.union(new V3D_AABBZ_d(g,));
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
        this(, g.getPointsArray());
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
                    xmin = double.min(xmin
                    , points[i].getX()
                    );
                    xmax = double.max(xmax
                    , points[i].getX()
                    );
                    ymin = double.min(ymin
                    , points[i].getY()
                    );
                    ymax = double.max(ymax
                    , points[i].getY()
                
                );
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return This represented as a string.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "(xMin=" + getXMin() + ", xMax=" + getXMax()
                + ", yMin=" + getYMin() + ", yMax=" + getYMax()
                + ", z=" + z.toString() + ")";
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
    public boolean equals(V3D_AABBZ_d e) {
        return getXMin().compareTo(e.getXMin()) == 0
                && getXMax().compareTo(e.getXMax()) == 0
                && getYMin().compareTo(e.getYMin()) == 0
                && getYMax().compareTo(e.getYMax()) == 0
                && z.compareTo(e.z) == 0;
    }

    /**
     * @return {@link #xMin} with {@link #offset} added.
     */
    public double getXMin() {
        return xMin.add(offset.getDX());
    }

    /**
     * @return {@link #xMax} with {@link #offset} added.
     */
    public double getXMax() {
        return xMax.add(offset.getDX());
    }

    /**
     * @return {@link #yMin} with {@link #offset} added.
     */
    public double getYMin() {
        return yMin.add(offset.getDY());
    }

    /**
     * @return {@link #yMax} with {@link #offset} added.
     */
    public double getYMax() {
        return yMax.add(offset.getDY());
    }

    /**
     * @return {@link #ll} setting it first if it is null.
     */
    public V3D_Point_d getll() {
        if (ll == null) {
            ll = new V3D_Point_d(env, xMin, yMin, z);
        }
        return ll;
    }

    /**
     * @return {@link #lu} setting it first if it is null.
     */
    public V3D_Point_d getlu() {
        if (lu == null) {
            lu = new V3D_Point_d(env, xMin, yMax, z);
        }
        return lu;
    }

    /**
     * @return {@link #uu} setting it first if it is null.
     */
    public V3D_Point_d getuu() {
        if (uu == null) {
            uu = new V3D_Point_d(env, xMax, yMax, z);
        }
        return uu;
    }

    /**
     * @return {@link #ul} setting it first if it is null.
     */
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
    public V3D_FiniteGeometry_d getLeft() {
        if (l == null) {
            double xmin = getXMin();
            double ymin = getYMin();
            double ymax = getYMax();
            if (ymin == ymax) {
                l = new V3D_Point_d(env, xmin, ymax, z);
            } else {
                l = new V3D_LineSegment(
                        new V3D_Point_d(env, xmin, ymin, z),
                        new V3D_Point_d(env, xmin, ymax, z),);
            }
        }
        return l;
    }

    /**
     * @return the right of the envelope.
     */
    public V3D_FiniteGeometry_d getRight() {
        if (r == null) {
            double xmax = getXMax();
            double ymin = getYMin();
            double ymax = getYMax();
            if (ymin == ymax) {
                r = new V3D_Point_d(env, xmax, ymax, z);
            } else {
                r = new V3D_LineSegment(
                        new V3D_Point_d(env, xmax, ymin, z),
                        new V3D_Point_d(env, xmax, ymax, z),);
            }
        }
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the top of the envelope.
     */
    public V3D_FiniteGeometry_d getTop() {
        if (t == null) {
            double xmin = getXMin();
            double xmax = getXMax();
            double ymax = getYMax();
            if (xmin.compareTo(xmax) == 0) {
                t = new V3D_Point_d(env, xmin, ymax, z);
            } else {
                t = new V3D_LineSegment(
                        new V3D_Point_d(env, xmin, ymax, z),
                        new V3D_Point_d(env, xmax, ymax, z),);
            }
        }
        return t;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return the bottom of the envelope.
     */
    public V3D_FiniteGeometry_d getBottom() {
        if (b == null) {
            double xmin = getXMin();
            double xmax = getXMax();
            double ymin = getYMin();
            if (xmin.compareTo(xmax) == 0) {
                b = new V3D_Point_d(env, xmin, ymin, z);
            } else {
                b = new V3D_LineSegment(
                        new V3D_Point_d(env, xmin, ymin, z),
                        new V3D_Point_d(env, xmax, ymin, z),);
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
    public void translate(V3D_Vector_d v, ) {
        offset = offset.add(v,);
        pts = null;
        if (ll != null) {
            ll.translate(v,);
        }
        if (lu != null) {
            lu.translate(v,);
        }
        if (uu != null) {
            uu.translate(v,);
        }
        if (ul != null) {
            ul.translate(v,);
        }
        if (l != null) {
            l.translate(v,);
        }
        if (t != null) {
            t.translate(v,);
        }
        if (r != null) {
            r.translate(v,);
        }
        if (b != null) {
            b.translate(v,);
        }
//        xMax = xMax.add(v.getDX());
//        xMin = xMin.add(v.getDX());
//        yMax = yMax.add(v.getDY());
//        yMin = yMin.add(v.getDY());
    }

    /**
     * Calculate and return the approximate (or exact) centroid.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return The approximate or exact centre of this.
     */
    public V3D_Point_d getCentroid(int oom) {
        return new V3D_Point_d(env,
                getXMax().add(getXMin()).divide(2),
                getYMax().add(getYMin()).divide(2), z);
    }

    /**
     * @param e The V3D_AABBZ to union with this. It is assumed to be in the
     * same Z plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return a V3D_AABBZ which is {@code this} union {@code e}.
     */
    public V3D_AABBZ_d union(V3D_AABBZ_d e, ) {
        if (this.contains(e)) {
            return this;
        } else {
            return new V3D_AABBZ_d(env, ,
                    double.min
            (e.getXMin()
            , getXMin()),
                    double.max
            (e.getXMax()
            , getXMax()),
                    double.min
            (e.getYMin()
            , getYMin()),
                    double.max
            (e.getYMax()
            , getYMax()), z
        
    

    );
        }
    }

    /**
     * @param e The V3D_AABBZ to test for intersection. It is assumed
     * to be in the same Z plane.
     * @param oom The Order of Magnitude for the precision.
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
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} is beyond {@code e} (i.e. they do
     * not touch or intersect).
     */
    public boolean isBeyond(V3D_AABBZ_d e) {
        return getXMax().compareTo(e.getXMin()) == -1
                || getXMin().compareTo(e.getXMax()) == 1
                || getYMax().compareTo(e.getYMin()) == -1
                || getYMin().compareTo(e.getYMax()) == 1;
    }

    /**
     * @param e V3D_AABB The envelope to test if it is contained. It is assumed
     * to be in the same Z plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} contains {@code e}.
     */
    public boolean contains(V3D_AABBZ_d e) {
        return getXMax().compareTo(e.getXMax()) != -1
                && getXMin().compareTo(e.getXMin()) != 1
                && getYMax().compareTo(e.getYMax()) != -1
                && getYMin().compareTo(e.getYMin()) != 1;
    }

    /**
     * @param p The point to test if it is contained. It is assumed to be in the
     * same Z plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code} true iff {@code this} contains {@code p}.
     */
    public boolean contains(V3D_Point_d p) {
        return getXMax().compareTo(p.getX()) != -1
                && getXMin().compareTo(p.getX()) != 1
                && getYMax().compareTo(p.getY()) != -1
                && getYMin().compareTo(p.getY()) != 1;
    }

    /**
     * @param l The line to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this contains {@code l}
     */
    public boolean contains(V3D_LineSegment l, ) {
        return contains(l.getP()) && contains(l.getQ());
    }

    /**
     * @param p The point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this intersects with {@code p}
     */
    public boolean intersects(V3D_Point_d p, ) {
        return intersects(p.getX(), p.getY());
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with a point defined by
     * {@code x}, {@code y}, and {@link #z}}.
     */
    public boolean intersects(double x, double y,
            int oom) {
        return x.compareTo(getXMin()) != -1
                && x.compareTo(getXMax()) != 1
                && y.compareTo(getYMin()) != -1
                && y.compareTo(getYMax()) != 1;
    }

    /**
     * @param l The line to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with {@code l}
     */
    public boolean intersects(V3D_Line l, ) {
        return true;
    }

    /**
     * https://www.geometrictools.com/Documentation/IntersectionLineBox.pdf
     *
     * @param l The ray to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with {@code l}
     */
    public boolean intersects(V3D_Ray r, ) {
        return true;
    }

    /**
     * https://www.geometrictools.com/Documentation/IntersectionLineBox.pdf
     *
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with {@code l}
     */
    public boolean intersects(V3D_LineSegment l, ) {
        return true;
    }

}
