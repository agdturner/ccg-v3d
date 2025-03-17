/*
 * Copyright 2020-2025 Andy Turner, University of Leeds.
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
import java.math.RoundingMode;
import java.util.ArrayList;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

/**
 * An Axis Aligned Bounding Box defined by the extreme values with respect to
 * the X, Y and Z axes. If {@link xMin} &LT; {@link xMax} and {@link yMin} &LT;
 * {@link yMax} and {@link zMin} &LT; {@link zMax} the bounding box defines a 3D
 * box region with each face being parallel to an axis. If {@link xMin} =
 * {@link xMax} or {@link yMin} = {@link yMax} or {@link zMin} = {@link zMax}
 * the bounding box is a rectangular area parallel to the ZY, XZ or XY plane
 * respectively. If {@link xMin} = {@link xMax} and {@link yMin} = {@link yMax}
 * the bounding box is a line segment parallel to the Z axis. If {@link xMin} =
 * {@link xMax} and {@link zMin} = {@link zMax} the bounding box is a line
 * segment parallel to the Y axis. If {@link yMin} =
 * {@link yMax} and {@link zMin} = {@link zMax} the bounding box is a line
 * segment parallel to the X axis. If {@link xMin} =
 * {@link xMax} and {@link yMin} = {@link yMax} and {@link zMin} = {@link zMax}
 * the bounding box is a point.
 *
 * The following depiction of 3D box type bounding box indicate the location and
 * name of the components. {@code
 *                                                         z
 *                                    y                   -
 *                                    +                  /
 *                                    |                 /
 *                                    |                /
 *                                    |               /
 *                                    |              /
 *                                    |
 *                     lul _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ uul
 *                        /|                                /|
 *                       / |                               / |
 *                      /  |                              /  |
 *                     /   |                             /   |
 *                    /    |             /              /    |
 *                   /     |          - t -            /     |
 *                  /      |           /              /      |
 *                 /       |                 |       /       |
 *                /        |               - a -    /        |
 *               /         |                 |     /         |
 *          luu /_ _ _ _ _ |_ _ _ _ _ _ _ _ _ _ _ /uuu       |
 *             |           |                     |           |
 *             |           |                     |           |
 *     x - ----|--    |/   |                     |     |/    |  ------ + x
 *             |      l    |                     |     r     |
 *             |     /| lll|_ _ _ _ _ _ _ _ _ _ _|_ _ /|_ _ _|ull
 *             |           /                     |           /
 *             |          /     |                |          /
 *             |         /    - f -              |         /
 *             |        /       |                |        /
 *             |       /                /        |       /
 *             |      /              - b -       |      /
 *             |     /                /          |     /
 *             |    /                            |    /
 *             |   /                             |   /
 *             |  /                              |  /
 *             | /                               | /
 *             |/_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ |/
 *           llu                                ulu
 *                                     |
 *                      /              |
 *                     /               |
 *                    /                |
 *                   /                 |
 *                  /                  -
 *                 +                   y
 *                z
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_AABB_d implements Serializable {

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
     * The minimum z-coordinate.
     */
    private final double zMin;

    /**
     * The maximum z-coordinate.
     */
    private final double zMax;

    /**
     * llu.
     */
    private V3D_Point_d llu;

    /**
     * luu.
     */
    private V3D_Point_d luu;

    /**
     * uuu.
     */
    private V3D_Point_d uuu;

    /**
     * ulu.
     */
    private V3D_Point_d ulu;

    /**
     * lll.
     */
    private V3D_Point_d lll;

    /**
     * lul.
     */
    private V3D_Point_d lul;

    /**
     * uul.
     */
    private V3D_Point_d uul;

    /**
     * ull.
     */
    private V3D_Point_d ull;

    /**
     * The left geometry.
     */
    protected V3D_AABBX_d l;

    /**
     * The right geometry.
     */
    protected V3D_AABBX_d r;

    /**
     * The top geometry.
     */
    protected V3D_AABBY_d t;

    /**
     * The bottom geometry.
     */
    protected V3D_AABBY_d b;

    /**
     * The fore geometry.
     */
    protected V3D_AABBZ_d f;

    /**
     * The aft geometry.
     */
    protected V3D_AABBZ_d a;

    /**
     * For storing all the corner points. These are in order: lbf, lba, ltf,
     * lta, rbf, rba, rtf, rta. For storing all the points. N.B {@link #lll}, {@link #llu}, {@link #lul},
     * {@link #luu}, {@link #ull}, {@link #ulu}, {@link #uul}, {@link #uuu} may
     * all be the same.
     */
    protected V3D_Point_d[] pts;
    //protected HashSet<V3D_Point_d> pts;

    /**
     * @param e An Axis Aligned Bounding Box.
     */
    public V3D_AABB_d(V3D_AABB_d e) {
        env = e.env;
        offset = e.offset;
        yMin = e.yMin;
        yMax = e.yMax;
        xMin = e.xMin;
        xMax = e.xMax;
        zMin = e.zMin;
        zMax = e.zMax;
        lll = e.lll;
        llu = e.llu;
        lul = e.lul;
        luu = e.luu;
        ull = e.ull;
        ulu = e.ulu;
        uul = e.uul;
        uuu = e.uuu;
        l = e.l;
        r = e.r;
        b = e.b;
        t = e.t;
        a = e.a;
        f = e.f;
        pts = e.pts;
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     * @param z The z-coordinate of a point.
     */
    public V3D_AABB_d(V3D_Environment_d env, double x, double y, double z) {
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
     * @param zMin What {@link zMin} is set to.
     * @param zMax What {@link zMax} is set to.
     */
    public V3D_AABB_d(V3D_Environment_d env,
            double xMin, double xMax,
            double yMin, double yMax,
            double zMin, double zMax) {
        this(new V3D_Point_d(env, xMin, yMin, zMin),
                new V3D_Point_d(env, xMax, yMax, zMax));
    }

    /**
     * Create a new instance.
     *
     * @param gs The geometries used to form the Axis Aligned Bounding Box.
     */
    public V3D_AABB_d(V3D_FiniteGeometry_d... gs) {
        V3D_AABB_d e = new V3D_AABB_d(gs[0]);
        for (V3D_FiniteGeometry_d g : gs) {
            e = e.union(new V3D_AABB_d(g));
        }
        env = e.env;
        offset = e.offset;
        yMin = e.yMin;
        yMax = e.yMax;
        xMin = e.xMin;
        xMax = e.xMax;
        zMin = e.zMin;
        zMax = e.zMax;
        lll = e.lll;
        llu = e.llu;
        lul = e.lul;
        luu = e.luu;
        ull = e.ull;
        ulu = e.ulu;
        uul = e.uul;
        uuu = e.uuu;
        l = e.l;
        r = e.r;
        b = e.b;
        t = e.t;
        a = e.a;
        f = e.f;
        pts = e.pts;
    }

    /**
     * Create a new instance.
     *
     * @param points The points used to form the envelop.
     */
    public V3D_AABB_d(V3D_Point_d... points) {
        //offset = points[0].offset;
        //offset = V3D_Vector.ZERO;
        int len = points.length;
        switch (len) {
            case 0 ->
                throw new RuntimeException("Cannot create Axis Aligned Bounding Box from an empty "
                        + "collection of points.");
            case 1 -> {
                offset = V3D_Vector_d.ZERO;
                env = points[0].env;
                xMin = points[0].getX();
                xMax = points[0].getX();
                yMin = points[0].getY();
                yMax = points[0].getY();
                zMin = points[0].getZ();
                zMax = points[0].getZ();
            }
            default -> {
                offset = V3D_Vector_d.ZERO;
                env = points[0].env;
                double xmin = points[0].getX();
                double xmax = points[0].getX();
                double ymin = points[0].getY();
                double ymax = points[0].getY();
                double zmin = points[0].getZ();
                double zmax = points[0].getZ();
                for (int i = 1; i < points.length; i++) {
                    xmin = Math.min(xmin, points[i].getX());
                    xmax = Math.max(xmax, points[i].getX());
                    ymin = Math.min(ymin, points[i].getY());
                    ymax = Math.max(ymax, points[i].getY());
                    zmin = Math.min(zmin, points[i].getZ());
                    zmax = Math.max(zmax, points[i].getZ());
                }
                this.xMin = xmin;
                this.xMax = xmax;
                this.yMin = ymin;
                this.yMax = ymax;
                this.zMin = zmin;
                this.zMax = zmax;
            }
        }
    }

    /**
     * @return This represented as a string.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "(xMin=" + getXMin() + ", xMax=" + getXMax()
                + ", yMin=" + getYMin() + ", yMax=" + getYMax()
                + ", zMin=" + getZMin() + ", zMax=" + getZMax() + ")";
    }

//    /**
//     * @return {@link #pts} initialising first if it is null.
//     */
//    public HashSet<V3D_Point_d> getPointsArray() {
//        if (pts == null) {
//            pts = new HashSet<>(8);
//            pts.add(getlll());
//            pts.add(getllu());
//            pts.add(getlul());
//            pts.add(getluu());
//            pts.add(getull());
//            pts.add(getulu());
//            pts.add(getuul());
//            pts.add(getuuu());
//        }
//        return pts;
//    }
    /**
     * Test for equality.
     *
     * @param e The V3D_AABB to test for equality with this.
     * @return {@code true} iff this and e are equal.
     */
    public boolean equals(V3D_AABB_d e) {
        return getXMin() == e.getXMin()
                && getXMax() == e.getXMax()
                && getYMin() == e.getYMin()
                && getYMax() == e.getYMax()
                && getZMin() == e.getZMin()
                && getZMax() == e.getZMax();
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
     * @return {@link #llu} setting it first if it is null.
     */
    public V3D_Point_d getllu() {
        if (llu == null) {
            llu = new V3D_Point_d(env, xMin, yMin, zMax);
        }
        return llu;
    }

    /**
     * @return {@link #luu} setting it first if it is null.
     */
    public V3D_Point_d getluu() {
        if (luu == null) {
            luu = new V3D_Point_d(env, xMin, yMax, zMax);
        }
        return luu;
    }

    /**
     * @return {@link #uuu} setting it first if it is null.
     */
    public V3D_Point_d getuuu() {
        if (uuu == null) {
            uuu = new V3D_Point_d(env, xMax, yMax, zMax);
        }
        return uuu;
    }

    /**
     * @return {@link #ulu} setting it first if it is null.
     */
    public V3D_Point_d getulu() {
        if (ulu == null) {
            ulu = new V3D_Point_d(env, xMax, yMin, zMax);
        }
        return ulu;
    }

    /**
     * @return {@link #lll} setting it first if it is null.
     */
    public V3D_Point_d getlll() {
        if (lll == null) {
            lll = new V3D_Point_d(env, xMin, yMin, zMin);
        }
        return lll;
    }

    /**
     * @return {@link #lul} setting it first if it is null.
     */
    public V3D_Point_d getlul() {
        if (lul == null) {
            lul = new V3D_Point_d(env, xMin, yMax, zMin);
        }
        return lul;
    }

    /**
     * @return {@link #uul} setting it first if it is null.
     */
    public V3D_Point_d getuul() {
        if (uul == null) {
            uul = new V3D_Point_d(env, xMin, yMax, zMin);
        }
        return uul;
    }

    /**
     * @return {@link #ull} setting it first if it is null.
     */
    public V3D_Point_d getull() {
        if (ull == null) {
            ull = new V3D_Point_d(env, xMax, yMin, zMin);
        }
        return ull;
    }

    /**
     * @return {@link #l} setting it first if it is null.
     */
    public V3D_AABBX_d getl() {
        if (l == null) {
            l = new V3D_AABBX_d(env, xMin, yMin, yMax, zMin, zMax);
        }
        return l;
    }

    /**
     * @return {@link #r} setting it first if it is null.
     */
    public V3D_AABBX_d getr() {
        if (r == null) {
            r = new V3D_AABBX_d(env, xMax, yMin, yMax, zMin, zMax);
        }
        return r;
    }

    /**
     * @return {@link #b} setting it first if it is null.
     */
    public V3D_AABBY_d getb() {
        if (b == null) {
            b = new V3D_AABBY_d(env, xMin, xMax, yMin, zMin, zMax);
        }
        return b;
    }

    /**
     * @return {@link #t} setting it first if it is null.
     */
    public V3D_AABBY_d gett() {
        if (t == null) {
            t = new V3D_AABBY_d(env, xMin, xMax, yMax, zMin, zMax);
        }
        return t;
    }

    /**
     * @return {@link #a} setting it first if it is null.
     */
    public V3D_AABBZ_d geta() {
        if (a == null) {
            a = new V3D_AABBZ_d(env, xMin, xMax, yMin, yMax, zMin);
        }
        return a;
    }

    /**
     * @return {@link #f} setting it first if it is null.
     */
    public V3D_AABBZ_d getf() {
        if (f == null) {
            f = new V3D_AABBZ_d(env, xMin, xMax, yMin, yMax, zMax);
        }
        return f;
    }

    /**
     * Translates this using {@code v}.
     *
     * @param v The vector of translation.
     */
    public void translate(V3D_Vector_d v) {
        offset = offset.add(v);
        pts = null;
        if (lll != null) {
            lll.translate(v);
        }
        if (llu != null) {
            llu.translate(v);
        }
        if (lul != null) {
            lul.translate(v);
        }
        if (luu != null) {
            luu.translate(v);
        }
        if (ull != null) {
            ull.translate(v);
        }
        if (ulu != null) {
            ulu.translate(v);
        }
        if (uul != null) {
            uul.translate(v);
        }
        if (uuu != null) {
            uuu.translate(v);
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
        if (f != null) {
            f.translate(v);
        }
        if (a != null) {
            a.translate(v);
        }
    }

    /**
     * Calculate and return the approximate (or exact) centroid of the Axis
     * Aligned Bounding Box.
     *
     * @return The approximate or exact centre of this.
     */
    public V3D_Point_d getCentroid() {
        return new V3D_Point_d(env,
                (getXMax() + getXMin()) / 2d,
                (getYMax() + getYMin()) / 2d,
                (getZMax() + getZMin()) / 2d);
    }

    /**
     * @param e The Axis Aligned Bounding Box to union with this.
     * @return the Axis Aligned Bounding Box which contains both {@code this}
     * and {@code e}.
     */
    public V3D_AABB_d union(V3D_AABB_d e) {
        if (contains(e)) {
            return this;
        } else {
            return new V3D_AABB_d(env,
                    Math.min(e.getXMin(), getXMin()),
                    Math.max(e.getXMax(), getXMax()),
                    Math.min(e.getYMin(), getYMin()),
                    Math.max(e.getYMax(), getYMax()),
                    Math.min(e.getZMin(), getZMin()),
                    Math.max(e.getZMax(), getZMax()));
        }
    }

    /**
     * If {@code e} touches, or overlaps then it intersects.For collision
     * avoidance, this is biased towards returning an intersection even if there
     * may not be one at a lower oom precision.
     *
     * @param e The Vector_Envelope3D to test for intersection.
     * @return {@code true} if this getIntersect with {@code e} it the
     * {@code oom} level of precision.
     */
    public boolean intersects(V3D_AABB_d e) {
        if (isBeyond(e)) {
            return !e.isBeyond(this);
        } else {
            return true;
        }
    }
    
    /**
     * If {@code e} touches, or overlaps then it intersects.For collision
     * avoidance, this is biased towards returning an intersection even if there
     * may not be one at a lower oom precision.
     *
     * @param e The Vector_Envelope3D to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this getIntersect with {@code e} it the
     * {@code oom} level of precision.
     */
    public boolean intersects(V3D_AABB_d e, double epsilon) {
        if (isBeyond(e, epsilon)) {
            return !e.isBeyond(this, epsilon);
        } else {
            return true;
        }
    }

    /**
     * @param e The Axis Aligned Bounding Box to test if {@code this} is beyond.
     * @return {@code true} iff {@code this} is beyond {@code e} (i.e. they do
     * not touch or intersect).
     */
    public boolean isBeyond(V3D_AABB_d e) {
        return getXMax() < e.getXMin()
                || getXMin() > e.getXMax()
                || getYMax() < e.getYMin()
                || getYMin() > e.getYMax()
                || getZMax() < e.getZMin()
                || getZMin() > e.getZMax();
    }
    
    /**
     * @param e The Axis Aligned Bounding Box to test if {@code this} is beyond.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} is beyond {@code e} (i.e. they do
     * not touch or intersect).
     */
    public boolean isBeyond(V3D_AABB_d e, double epsilon) {
        return getXMax() < e.getXMin()
                || getXMin() > e.getXMax() + epsilon
                || getYMax() < e.getYMin() - epsilon
                || getYMin() > e.getYMax() + epsilon
                || getZMax() < e.getZMin() - epsilon
                || getZMin() > e.getZMax() + epsilon;
    }

    /**
     * @param e The Axis Aligned Bounding Box to test if it is contained.
     * @return {@code true} iff {@code this} contains {@code e}.
     */
    public boolean contains(V3D_AABB_d e) {
        return getXMax() >= e.getXMax()
                && getXMin() <= e.getXMin()
                && getYMax() >= e.getYMax()
                && getYMin() <= e.getYMin()
                && getZMax() >= e.getZMax()
                && getZMin() <= e.getZMin();
    }
    
    /**
     * @param e The Axis Aligned Bounding Box to test if it is contained.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} contains {@code e}.
     */
    public boolean contains(V3D_AABB_d e, double epsilon) {
        return getXMax() >= e.getXMax() - epsilon
                && getXMin() <= e.getXMin() + epsilon
                && getYMax() >= e.getYMax() - epsilon
                && getYMin() <= e.getYMin() + epsilon
                && getZMax() >= e.getZMax() - epsilon
                && getZMin() <= e.getZMin() + epsilon;
    }

    /**
     * The location of p may get rounded.
     *
     * @param p The point to test if it is contained.
     * @return {@code} true iff {@code this} contains {@code p}.
     */
    public boolean contains(V3D_Point_d p) {
        return contains(p.getX(), p.getY(), p.getZ());
    }

    /**
     * The location of p may get rounded.
     *
     * @param p The point to test if it is contained.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code} true iff {@code this} contains {@code p}.
     */
    public boolean contains(V3D_Point_d p, double epsilon) {
        return contains(p.getX(), p.getY(), p.getZ(), epsilon);
    }

    /**
     * @param x The x-coordinate of the point to test for containment.
     * @param y The y-coordinate of the point to test for containment.
     * @param z The z-coordinate of the point to test for containment.
     * @return {@code true} iff {@code this} contains the point defined by
     * {@code x}, {@code y} and {@code z}.
     */
    public boolean contains(double x, double y, double z) {
        return getXMax() >= x
                && getXMin() <= x
                && getYMax() >= y
                && getYMin() <= y
                && getZMax() >= z
                && getZMin() <= z;
    }
    
    /**
     * @param x The x-coordinate of the point to test for containment.
     * @param y The y-coordinate of the point to test for containment.
     * @param z The z-coordinate of the point to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} contains the point defined by
     * {@code x}, {@code y} and {@code z}.
     */
    public boolean contains(double x, double y, double z, double epsilon) {
        return getXMax() >= x + epsilon
                && getXMin() <= x - epsilon
                && getYMax() >= y + epsilon
                && getYMin() <= y - epsilon
                && getZMax() >= z + epsilon
                && getZMin() <= z - epsilon;
    }

    /**
     * @param l The line to test for containment.
     * @return {@code true} if this contains {@code l}.
     */
    public boolean contains(V3D_LineSegment_d l) {
        return contains(l.getP()) && contains(l.getQ());
    }

    /**
     * @param l The line to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this contains {@code l}.
     */
    public boolean contains(V3D_LineSegment_d l, double epsilon) {
        return contains(l.getP(), epsilon) && contains(l.getQ(), epsilon);
    }

    /**
     * @param s The shape to test for containment.
     * @return {@code true} if this contains {@code s}.
     */
    public boolean contains(V3D_Face_d s) {
        return contains(s.getAABB())
                && contains0(s);
    }

    /**
     * @param s The shape to test for containment.
     * @return {@code true} if this getIntersect with {@code s}
     */
    public boolean contains0(V3D_Face_d s) {
        return s.getPoints().values().parallelStream().allMatch(x
                -> contains(x));
    }

    /**
     * @param p The point to test for intersection.
     * @return {@code true} if this getIntersect with {@code pl}
     */
    public boolean intersects(V3D_Point_d p) {
        return intersects(p.getX(), p.getY(), p.getZ());
    }

    /**
     * This biases intersection.
     *
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @return {@code true} if this getIntersect with {@code pl}
     */
    public boolean intersects(double x, double y, double z) {
        return x >= getXMin()
                && x <= getXMax()
                && y >= getYMin()
                && y <= getYMax()
                && z >= getZMin()
                && z <= getZMax();
    }

    /**
     * This biases intersection.
     *
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this getIntersect with {@code pl}
     */
    public boolean intersects(double x, double y, double z, double epsilon) {
        return x >= getXMin() - epsilon
                && x <= getXMax() + epsilon
                && y >= getYMin() - epsilon
                && y <= getYMax() + epsilon
                && z >= getZMin() - epsilon
                && z <= getZMax() + epsilon;
    }

    /**
     * @param en The Axis Aligned Bounding Box to intersect.
     * @return {@code null} if there is no intersection; {@code en} if
     * {@code this.equals(en)}; otherwise returns the intersection.
     */
    public V3D_AABB_d getIntersect(V3D_AABB_d en) {
        if (!intersects(en)) {
            return null;
        }
// Probably quicker without: 
//        if (contains(en)) {
//            return this;
//        }
//        if (en.contains(this)) {
//            return en;
//        }
        return new V3D_AABB_d(env,
                Math.max(getXMin(), en.getXMin()),
                Math.min(getXMax(), en.getXMax()),
                Math.max(getYMin(), en.getYMin()),
                Math.min(getYMax(), en.getYMax()),
                Math.max(getZMin(), en.getZMin()),
                Math.min(getZMax(), en.getZMax()));
    }

    /**
     * For storing all the points. N.B
     *
     * @return The corners of this as points: {@link #lll}, {@link #llu},
     * {@link #lul}, {@link #luu}, {@link #ull}, {@link #ulu}, {@link #uul},
     * {@link #uuu}
     */
    public V3D_Point_d[] getPoints() {
        if (pts == null) {
            pts = new V3D_Point_d[8];
            pts[0] = getllu();
            pts[1] = getllu();
            pts[2] = getluu();
            pts[3] = getlul();
            pts[4] = getulu();
            pts[5] = getull();
            pts[6] = getuuu();
            pts[7] = getuul();
        }
        return pts;
    }
//    public HashSet<V3D_Point_d> getPointsArray() {
//        if (pts == null) {
//            pts = new HashSet<>(8);
//            pts.add(getllu());
//            pts.add(getllu());
//            pts.add(getluu());
//            pts.add(getlul());
//            pts.add(getulu());
//            pts.add(getull());
//            pts.add(getuuu());
//            pts.add(getuul());
//        }
//        return pts;
//    }

    /**
     * Calculate and return the viewport - a rectangle between the point pt and
     * this such that all of this is contained in the planes from the point
     * through the viewport and the viewport intersects (touches) this. When the
     * pt is directly in front of one of the faces of this, the viewport will be
     * that face. In some cases the viewport may intersect (touch) along an edge
     * between two faces. In all other cases, the viewport will intersect
     * (touch) just one corner of this. The right edge of the viewport is in the
     * direction given by the vector v from the point pt.
     *
     * @param pt The point from which observation of this is occurring.
     * @param v The vector pointing to the right of the viewport.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A viewport - a rectangle between pt and this such that all of
     * this is contained in the planes from the point through the viewport.
     */
    public V3D_Rectangle_d getViewport(V3D_Point_d pt,
            V3D_Vector_d v, double epsilon) {
        V3D_Rectangle_d rect;
        pts = getPoints();
//        pts[0] = new V3D_Point_d(lba);
//        pts[1] = new V3D_Point_d(lbf);
//        pts[2] = new V3D_Point_d(lta);
//        pts[3] = new V3D_Point_d(ltf);
//        pts[4] = new V3D_Point_d(rba);
//        pts[5] = new V3D_Point_d(rbf);
//        pts[6] = new V3D_Point_d(rta);
//        pts[7] = new V3D_Point_d(rtf);
        V3D_Point_d c = getCentroid();
        V3D_Vector_d cv = new V3D_Vector_d(pt, c);
        V3D_Vector_d v2 = cv.getCrossProduct(v);
        /**
         * Calculate the plane touching this in the direction given from pt to
         * c.
         */
        // Find a closest point (there could be up to 4).
        double d2min = pt.getDistanceSquared(pts[0]);
        V3D_Point_d closest = pts[0];
        for (int i = 1; i < pts.length; i++) {
            double d2 = pt.getDistanceSquared(pts[i]);
            if (d2 < d2min) {
                d2min = d2;
                closest = pts[i];
            }
        }
        // Use the closest point to define the plane of the screen.
        V3D_Plane_d pl0 = new V3D_Plane_d(closest, cv);
        // Intersect the rays from pts to each point with the screen.
        V3D_Point_d[] ipts = new V3D_Point_d[pts.length];
        // Get the intersecting points on the screen plane from pt
        for (int i = 0; i < pts.length; i++) {
            V3D_Ray_d ray = new V3D_Ray_d(pt, pts[i]);
            ipts[i] = (V3D_Point_d) ray.getIntersect(pl0, epsilon);
        }
        // Figure out the extremes in relation to v and v2
        // Find top, bottom, left and right planes
        V3D_Plane_d vpl = new V3D_Plane_d(c, v);
        V3D_Plane_d v2pl = new V3D_Plane_d(c, v2);
        // Find top and bottom
        V3D_Point_d ap = new V3D_Point_d(c);
        ap.translate(v2);
        double aa = 0d;
        V3D_Plane_d tpl = null;
        //V3D_Point_d pb = new V3D_Point_d(v2pl.getP());
        //pb.translate(v2.reverse(), );
        double ba = 0d;
        V3D_Plane_d bpl = null;
        for (var x : ipts) {
            V3D_Point_d pp = vpl.getPointOfProjectedIntersection(x, epsilon);
            double angle = Math.abs(cv.getAngle(new V3D_Vector_d(pt, pp)));
            if (v2pl.isOnSameSide(ap, x, epsilon)) {
                if (angle > aa) {
                    aa = angle;
                    //System.out.println(a);
                    V3D_Point_d xv = new V3D_Point_d(x);
                    xv.translate(v);
                    tpl = new V3D_Plane_d(x, pt, xv);
                }
            } else {
                if (angle > ba) {
                    ba = angle;
                    V3D_Point_d xv = new V3D_Point_d(x);
                    xv.translate(v);
                    bpl = new V3D_Plane_d(x, pt, xv);
                }
            }
        }
        // Find left and right
        V3D_Point_d lp = new V3D_Point_d(c);
        lp.translate(v.reverse());
        double la = 0d;
        V3D_Plane_d lpl = null;
        //V3D_Point_d pr = new V3D_Point_d(vpl.getP());
        //pr.translate(v, );
        double ra = 0d;
        V3D_Plane_d rpl = null;
        for (var x : ipts) {
            V3D_Point_d pp = v2pl.getPointOfProjectedIntersection(x, epsilon);
            double angle = Math.abs(cv.getAngle(new V3D_Vector_d(pt, pp)));
            if (vpl.isOnSameSide(lp, x, epsilon)) {
                if (angle > la) {
                    la = angle;
                    V3D_Point_d xv = new V3D_Point_d(x);
                    xv.translate(v2);
                    lpl = new V3D_Plane_d(x, pt, xv);
                }
            } else {
                if (angle > ra) {
                    ra = angle;
                    V3D_Point_d xv = new V3D_Point_d(x);
                    xv.translate(v2);
                    rpl = new V3D_Plane_d(x, pt, xv);
                }
            }
        }
//        // Check
//        if (!tp.allOnSameSide(pts, )) {
//            int debug = 1;
//        }
//        if (!bp.allOnSameSide(pts, )) {
//            int debug = 1;
//        }
//        if (!lp.allOnSameSide(pts, )) {
//            int debug = 1;
//        }
//        if (!rp.allOnSameSide(pts, )) {
//            int debug = 1;
//        }
//        
//        lp.n = lp.n.getUnitVector();
//        rp.n = rp.n.getUnitVector();
//        tp.n = tp.n.getUnitVector();
//        bp.n = bp.n.getUnitVector();
        rect = new V3D_Rectangle_d(
                (V3D_Point_d) lpl.getIntersect(pl0, bpl, epsilon),
                (V3D_Point_d) lpl.getIntersect(pl0, tpl, epsilon),
                (V3D_Point_d) rpl.getIntersect(pl0, tpl, epsilon),
                (V3D_Point_d) rpl.getIntersect(pl0, bpl, epsilon));

        return rect;
    }

    /**
     * Calculate and return the viewport2 - a rectangle between the point pt and
     * this such that all of this is contained in the planes from the point
     * through the viewport and the viewport is a fixed distance from the
     * centroid (the distance from the centroid to a corner of this).
     *
     * @param pt The point from which observation of this is occuring.
     * @param v The vector pointing to the right of the viewport.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A viewport - a rectangle between pt and this such that all of
     * this is contained in the planes from the point through the viewport.
     */
    public V3D_Rectangle_d getViewport2(V3D_Point_d pt,
            V3D_Vector_d v, double epsilon) {
        V3D_Rectangle_d rect;
        // Get the plane of the viewport.
        V3D_Point_d c = getCentroid();
        double distance = c.getDistance(getPoints()[0]);
        V3D_Point_d plpt = new V3D_Point_d(c);
        V3D_Vector_d vo = new V3D_Vector_d(c, pt).getUnitVector();
        plpt.translate(vo.multiply(distance));
        V3D_Vector_d cv = vo.reverse();
        V3D_Plane_d pl0 = new V3D_Plane_d(plpt, cv);
        // Figure out the extremes in relation to v (and v2).
        V3D_Vector_d v2 = cv.getCrossProduct(v);
        // Intersect the rays from pts to each point with the screen.
        pts = getPoints();
        V3D_Point_d[] ipts = new V3D_Point_d[pts.length];
        // Get the intersecting points on the screen plane from pt
        for (int i = 0; i < pts.length; i++) {
            V3D_Ray_d ray = new V3D_Ray_d(pt, pts[i]);
            ipts[i] = (V3D_Point_d) ray.getIntersect(pl0, epsilon);
        }
        // Find top, bottom, left and right planes
        V3D_Plane_d vpl = new V3D_Plane_d(c, v);
        V3D_Plane_d v2pl = new V3D_Plane_d(c, v2);
        // Find top and bottom
        V3D_Point_d ap = new V3D_Point_d(c);
        ap.translate(v2);
        double aa = 0d;
        V3D_Plane_d tpl = null;
        //V3D_Point_d pb = new V3D_Point_d(v2pl.getP());
        //pb.translate(v2.reverse(), );
        double ba = 0d;
        V3D_Plane_d bpl = null;
        for (var x : ipts) {
            V3D_Point_d pp = vpl.getPointOfProjectedIntersection(x, epsilon);
            double angle = Math.abs(cv.getAngle(new V3D_Vector_d(pt, pp)));
            if (v2pl.isOnSameSide(ap, x, epsilon)) {
                if (angle > aa) {
                    aa = angle;
                    //System.out.println(a);
                    V3D_Point_d xv = new V3D_Point_d(x);
                    xv.translate(v);
                    tpl = new V3D_Plane_d(x, pt, xv);
                }
            } else {
                if (angle > ba) {
                    ba = angle;
                    V3D_Point_d xv = new V3D_Point_d(x);
                    xv.translate(v);
                    bpl = new V3D_Plane_d(x, pt, xv);
                }
            }
        }
        // Find left and right
        V3D_Point_d lp = new V3D_Point_d(c);
        lp.translate(v.reverse());
        double la = 0d;
        V3D_Plane_d lpl = null;
        //V3D_Point_d pr = new V3D_Point_d(vpl.getP());
        //pr.translate(v, );
        double ra = 0d;
        V3D_Plane_d rpl = null;
        for (var x : ipts) {
            V3D_Point_d pp = v2pl.getPointOfProjectedIntersection(x, epsilon);
            double angle = Math.abs(cv.getAngle(new V3D_Vector_d(pt, pp)));
            if (vpl.isOnSameSide(lp, x, epsilon)) {
                if (angle > la) {
                    la = angle;
                    V3D_Point_d xv = new V3D_Point_d(x);
                    xv.translate(v2);
                    lpl = new V3D_Plane_d(x, pt, xv);
                }
            } else {
                if (angle > ra) {
                    ra = angle;
                    V3D_Point_d xv = new V3D_Point_d(x);
                    xv.translate(v2);
                    rpl = new V3D_Plane_d(x, pt, xv);
                }
            }
        }
//        // Check
//        if (!tp.allOnSameSide(pts, )) {
//            int debug = 1;
//        }
//        if (!bp.allOnSameSide(pts, )) {
//            int debug = 1;
//        }
//        if (!lp.allOnSameSide(pts, )) {
//            int debug = 1;
//        }
//        if (!rp.allOnSameSide(pts, )) {
//            int debug = 1;
//        }
//        
//        lp.n = lp.n.getUnitVector();
//        rp.n = rp.n.getUnitVector();
//        tp.n = tp.n.getUnitVector();
//        bp.n = bp.n.getUnitVector();

        rect = new V3D_Rectangle_d(
                (V3D_Point_d) lpl.getIntersect(pl0, bpl, epsilon),
                (V3D_Point_d) lpl.getIntersect(pl0, tpl, epsilon),
                (V3D_Point_d) rpl.getIntersect(pl0, tpl, epsilon),
                (V3D_Point_d) rpl.getIntersect(pl0, bpl, epsilon));

        return rect;
    }

    /**
     * Calculate and return the viewport3 - a square between the point pt and
     * this such that all of this is contained in the planes from the point
     * through the viewport and the viewport is a fixed distance from the
     * centroid (the distance from the centroid to a corner of this). The
     * viewport also has fixed dimensions based on the distance from the
     * centroid to a corner of this irrespective of the orientation.
     *
     * @param pt The point from which observation of this is to occur.
     * @param v A vector pointing to the right of the viewport. This should be
     * orthogonal to the vector from pt to the centroid.
     * @param zoomFactor A zoom factor. A factor of 2 and the screen will be
     * twice as close to the object.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A viewport - a rectangle between pt and this such that all of
     * this is contained in the planes from the point through the viewport.
     */
    public V3D_Rectangle_d getViewport3(V3D_Point_d pt,
            V3D_Vector_d v, double zoomFactor, double epsilon) {
        V3D_Rectangle_d rect;
        // Get the plane of the viewport.
        V3D_Point_d c = getCentroid();
        double d = c.getDistance(getPoints()[0]);
        double dby2 = d / 2d;
        V3D_Point_d plpt = new V3D_Point_d(c);
        V3D_Vector_d cpt = new V3D_Vector_d(c, pt);
        V3D_Vector_d vo = cpt.getUnitVector();
        plpt.translate(vo.multiply(d / zoomFactor));
        V3D_Plane_d pl0 = new V3D_Plane_d(plpt, cpt);
        V3D_Vector_d v2 = cpt.getCrossProduct(v);
        // Find top, bottom, left and right planes
        V3D_Point_d ptv = new V3D_Point_d(pt);
        ptv.translate(v);
        V3D_Point_d ptv2 = new V3D_Point_d(pt);
        ptv2.translate(v2);
        // tp
        V3D_Vector_d vv = v2.getUnitVector().multiply(dby2);
        V3D_Point_d tppt = new V3D_Point_d(plpt);
        tppt.translate(vv);
        V3D_Plane_d tpl = new V3D_Plane_d(tppt, pt, ptv);
        // bp
        V3D_Point_d bppt = new V3D_Point_d(plpt);
        bppt.translate(vv.reverse());
        V3D_Plane_d bpl = new V3D_Plane_d(bppt, pt, ptv);
        // lp
        V3D_Vector_d hv = v.getUnitVector().multiply(dby2);
        V3D_Point_d lppt = new V3D_Point_d(plpt);
        lppt.translate(hv.reverse());
        V3D_Plane_d lpl = new V3D_Plane_d(lppt, pt, ptv2);
        // rp
        V3D_Point_d rppt = new V3D_Point_d(plpt);
        rppt.translate(hv);
        V3D_Plane_d rpl = new V3D_Plane_d(rppt, pt, ptv2);
        rect = new V3D_Rectangle_d(
                (V3D_Point_d) lpl.getIntersect(pl0, bpl, epsilon),
                (V3D_Point_d) lpl.getIntersect(pl0, tpl, epsilon),
                (V3D_Point_d) rpl.getIntersect(pl0, tpl, epsilon),
                (V3D_Point_d) rpl.getIntersect(pl0, bpl, epsilon));
        return rect;
    }

    /**
     * A collection method to add l to ls iff there is not already an l in ls.
     *
     * @param ls The collection.
     * @param l The line segment to add.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff l is unique and is added to ls.
     */
    protected boolean addUnique(ArrayList<V3D_Line_d> ls, V3D_Line_d l, int oom,
            RoundingMode rm) {
        boolean unique = true;
        for (var x : ls) {
            if (x.equals(l)) {
                unique = false;
                break;
            }
        }
        if (unique) {
            ls.add(l);
        }
        return unique;
    }

    /**
     * A collection method to add pt to pts iff there is not already a pt in
     * pts.
     *
     * @param pts The collection.
     * @param pt The point to add.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff l is unique and is added to ls.
     */
    protected boolean addUnique(ArrayList<V3D_Point_d> pts, V3D_Point_d pt, int oom,
            RoundingMode rm) {
        boolean unique = true;
        for (var x : pts) {
            if (x.equals(pt)) {
                unique = false;
                break;
            }
        }
        if (unique) {
            pts.add(pt);
        }
        return unique;
    }
}
