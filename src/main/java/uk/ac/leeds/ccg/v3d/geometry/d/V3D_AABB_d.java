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
import uk.ac.leeds.ccg.v3d.core.V3D_Environment_d;

/**
 * An Axis Aligned Bounding Box defined by the extreme values with respect to 
 * the X, Y and Z axes. If {@link xMin} &LT; {@link xMax} and {@link yMin} &LT; 
 * {@link yMax} and {@link zMin} &LT; {@link zMax} the bounding box defines a
 * 3D box region with each face being parallel to an axis. If {@link xMin} = 
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
     * lta, rbf, rba, rtf, rta.
     * For storing all the points. N.B {@link #lll}, {@link #llu}, {@link #lul},
     * {@link #luu}, {@link #ull}, {@link #ulu}, {@link #uul}, {@link #uuu}
     * may all be the same.
     */
    protected V3D_Point_d[] pts;
    //protected HashSet<V3D_Point> pts;
    
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
    public V3D_AABB_d(V3D_Environment_d env, double x,  double y, 
            double z) {
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
     * @param oom The Order of Magnitude for the precision.
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
                + ", zMin=" + getZMin(oom, rm) + ", zMax=" + getZMax(oom, rm) + ")";
    }

//    /**
//     * @return {@link #pts} initialising first if it is null.
//     */
//    public HashSet<V3D_Point> getPoints() {
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
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff this and e are equal.
     */
    public boolean equals(V3D_AABB_d e, int oom) {
        return this.getXMin(oom).compareTo(e.getXMin(oom)) == 0
                && this.getXMax(oom).compareTo(e.getXMax(oom)) == 0
                && this.getYMin(oom).compareTo(e.getYMin(oom)) == 0
                && this.getYMax(oom).compareTo(e.getYMax(oom)) == 0
                && this.getZMin(oom).compareTo(e.getZMin(oom)) == 0
                && this.getZMax(oom).compareTo(e.getZMax(oom)) == 0;
    }

    /**
     * For getting {@link #xMin} rounded. RoundingMode.FLOOR is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #xMin} rounded.
     */
    public double getXMin(int oom) {
        return getXMin();
    }

    /**
     * For getting {@link #xMin} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #xMin} rounded.
     */
    public double getXMin(int oom, RoundingMode rm) {
        return xMin.add(offset.getDX(oom - 2, rm));
    }

    /**
     * For getting {@link #xMax} rounded. RoundingMode.CEILING is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #xMax} rounded.
     */
    public double getXMax(int oom) {
        return getXMax();
    }

    /**
     * For getting {@link #xMax} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #xMax} rounded.
     */
    public double getXMax(int oom, RoundingMode rm) {
        return xMax.add(offset.getDX(oom - 2, rm));
    }

    /**
     * For getting {@link #yMin} rounded. RoundingMode.FLOOR is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #yMin} rounded.
     */
    public double getYMin(int oom) {
        return getYMin();
    }

    /**
     * For getting {@link #yMin} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #yMin} rounded.
     */
    public double getYMin(int oom, RoundingMode rm) {
        return yMin.add(offset.getDY(oom - 2, rm));
    }

    /**
     * For getting {@link #yMax} rounded. RoundingMode.CEILING is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #yMax} rounded.
     */
    public double getYMax(int oom) {
        return getYMax();
    }

    /**
     * For getting {@link #yMax} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #yMax} rounded.
     */
    public double getYMax(int oom, RoundingMode rm) {
        return yMax.add(offset.getDY(oom - 2, rm));
    }

    /**
     * @return {@link #zMin} with {@link #offset} added.
     */
    public double getZMin() {
        return zMin.add(offset.getDZ());
    }

    /**
     * For getting {@link #zMax} rounded. RoundingMode.CEILING is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #zMax} rounded.
     */
    public double getZMax(int oom) {
        return getZMax();
    }

    /**
     * For getting {@link #zMax} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #zMax} rounded.
     */
    public double getZMax(int oom, RoundingMode rm) {
        return zMax.add(offset.getDZ(oom - 2, rm));
    }

    /**
     * @return {@link #llu} setting it first if it is null.
     */
    public V3D_Point getllu() {
        if (llu == null) {
            llu = new V3D_Point_d(env, xMin, yMin, zMin);
        }
        return llu;
    }

    /**
     * @return {@link #luu} setting it first if it is null.
     */
    public V3D_Point getluu() {
        if (luu == null) {
            luu = new V3D_Point_d(env, xMin, yMax, zMax);
        }
        return luu;
    }

    /**
     * @return {@link #uuu} setting it first if it is null.
     */
    public V3D_Point getuuu() {
        if (uuu == null) {
            uuu = new V3D_Point_d(env, xMax, yMax, zMax);
        }
        return uuu;
    }

    /**
     * @return {@link #ulu} setting it first if it is null.
     */
    public V3D_Point getulu() {
        if (ulu == null) {
            ulu = new V3D_Point_d(env, xMax, yMin, zMax);
        }
        return ulu;
    }

    /**
     * @return {@link #lll} setting it first if it is null.
     */
    public V3D_Point getlll() {
        if (lll == null) {
            lll = new V3D_Point_d(env, xMin, yMin, zMin);
        }
        return lll;
    }

    /**
     * @return {@link #lul} setting it first if it is null.
     */
    public V3D_Point getlul() {
        if (lul == null) {
            lul = new V3D_Point_d(env, xMin, yMax, zMin);
        }
        return lul;
    }

    /**
     * @return {@link #uul} setting it first if it is null.
     */
    public V3D_Point getuul() {
        if (uul == null) {
            uul = new V3D_Point_d(env, xMin, yMax, zMin);
        }
        return uul;
    }

    /**
     * @return {@link #ull} setting it first if it is null.
     */
    public V3D_Point getull() {
        if (ull == null) {
            ull = new V3D_Point_d(env, xMax, yMin, zMin);
        }
        return ull;
    }
    
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #l} setting it first if it is null.
     */
    public V3D_AABBX getl(int oom, RoundingMode rm) {
        if (l == null) {
            l = new V3D_AABBX(env, oom, rm, xMin, yMin, yMax, zMin, zMax);
        }
        return l;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #r} setting it first if it is null.
     */
    public V3D_AABBX getr(int oom, RoundingMode rm) {
        if (r == null) {
            r = new V3D_AABBX(env, oom, rm, xMax, yMin, yMax, zMin, zMax);
        }
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #b} setting it first if it is null.
     */
    public V3D_AABBY getb(int oom, RoundingMode rm) {
        if (b == null) {
            b = new V3D_AABBY(env, oom, rm, xMin, xMax, yMin, zMin, zMax);
        }
        return b;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #t} setting it first if it is null.
     */
    public V3D_AABBY gett(int oom, RoundingMode rm) {
        if (t == null) {
            t = new V3D_AABBY(env, oom, rm, xMin, xMax, yMax, zMin, zMax);
        }
        return t;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #a} setting it first if it is null.
     */
    public V3D_AABBZ geta(int oom, RoundingMode rm) {
        if (a == null) {
            a = new V3D_AABBZ(env, oom, rm, xMin, xMax, yMin, yMax, zMin);
        }
        return a;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #f} setting it first if it is null.
     */
    public V3D_AABBZ getf(int oom, RoundingMode rm) {
        if (f == null) {
            f = new V3D_AABBZ(env, oom, rm, xMin, xMax, yMin, yMax, zMax);
        }
        return f;
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
        if (lll != null) {
            lll.translate(v, oom, rm);
        }
        if (llu != null) {
            llu.translate(v, oom, rm);
        }
        if (lul != null) {
            lul.translate(v, oom, rm);
        }
        if (luu != null) {
            luu.translate(v, oom, rm);
        }
        if (ull != null) {
            ull.translate(v, oom, rm);
        }
        if (ulu != null) {
            ulu.translate(v, oom, rm);
        }
        if (uul != null) {
            uul.translate(v, oom, rm);
        }
        if (uuu != null) {
            uuu.translate(v, oom, rm);
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
        if (f != null) {
            f.translate(v, oom, rm);
        }
        if (a != null) {
            a.translate(v, oom, rm);
        }
    }
    
    /**
     * Calculate and return the approximate (or exact) centroid of the Axis Aligned Bounding Box.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return The approximate or exact centre of this.
     */
    public V3D_Point getCentroid(int oom, RoundingMode rm) {
        return new V3D_Point_d(env,
                this.getXMax(oom, rm).add(this.getXMin(oom, rm)).divide(2),
                this.getYMax(oom, rm).add(this.getYMin(oom, rm)).divide(2),
                this.getZMax(oom, rm).add(this.getZMin(oom, rm)).divide(2));
    }
    
    /**
     * @param e The Axis Aligned Bounding Box to union with this.
     * @param oom The Order of Magnitude for the precision.
     * @return the Axis Aligned Bounding Box which contains both {@code this} 
     * and {@code e}.
     */
    public V3D_AABB_d union(V3D_AABB_d e, int oom) {
        if (this.contains(e, oom)) {
            return this;
        } else {
            return new V3D_AABB_d(env, oom,
                    Math.min(e.getXMin(oom), getXMin(oom)),
                    Math.max(e.getXMax(oom), getXMax(oom)),
                    Math.min(e.getYMin(oom), getYMin(oom)),
                    Math.max(e.getYMax(oom), getYMax(oom)),
                    Math.min(e.getZMin(oom), getZMin(oom)),
                    Math.max(e.getZMax(oom), getZMax(oom)));
        }
    }
    
    /**
     * If {@code e} touches, or overlaps then it intersects.For collision
 avoidance, this is biased towards returning an intersection even if there
 may not be one at a lower oom precision.
     *
     * @param e The Vector_Envelope3D to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this getIntersect with {@code e} it the {@code oom}
     * level of precision.
     */
    public boolean intersects(V3D_AABB_d e, int oom) {
        if (isBeyond(e, oom)) {
            return !e.isBeyond(this, oom);
        } else {
            return true;
        }
    }

    /**
     * @param e The Axis Aligned Bounding Box to test if {@code this} is beyond.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} is beyond {@code e} (i.e. they do
     * not touch or intersect).
     */
    public boolean isBeyond(V3D_AABB_d e, int oom) {
        return getXMax(oom).compareTo(e.getXMin(oom)) == -1
                || getXMin(oom).compareTo(e.getXMax(oom)) == 1
                || getYMax(oom).compareTo(e.getYMin(oom)) == -1
                || getYMin(oom).compareTo(e.getYMax(oom)) == 1
                || getZMax(oom).compareTo(e.getZMin(oom)) == -1
                || getZMin(oom).compareTo(e.getZMax(oom)) == 1;
    }

    /**
     * @param e The Axis Aligned Bounding Box to test if it is contained.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} contains {@code e}.
     */
    public boolean contains(V3D_AABB_d e, int oom) {
        return getXMax(oom).compareTo(e.getXMax(oom)) != -1
                && getXMin(oom).compareTo(e.getXMin(oom)) != 1
                && getYMax(oom).compareTo(e.getYMax(oom)) != -1
                && getYMin(oom).compareTo(e.getYMin(oom)) != 1
                && getZMax(oom).compareTo(e.getZMax(oom)) != -1
                && getZMin(oom).compareTo(e.getZMin(oom)) != 1;
    }

    /**
     * The location of p may get rounded.
     *
     * @param p The point to test if it is contained.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code} true iff {@code this} contains {@code p}.
     */
    public boolean contains(V3D_Point p, int oom) {
        double xu = p.getX();
        double xl = p.getX();
        double yu = p.getY();
        double yl = p.getY();
        double zu = p.getZ();
        double zl = p.getZ();
        return getXMax(oom).compareTo(xl) != -1
                && getXMin(oom).compareTo(xu) != 1
                && getYMax(oom).compareTo(yl) != -1
                && getYMin(oom).compareTo(yu) != 1
                && getZMax(oom).compareTo(zl) != -1
                && getZMin(oom).compareTo(zu) != 1;
    }

    /**
     * @param x The x-coordinate of the point to test for containment.
     * @param y The y-coordinate of the point to test for containment.
     * @param z The z-coordinate of the point to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} contains the point defined by
     * {@code x}, {@code y} and {@code z}.
     */
    public boolean contains(double x, double y, double z, int oom) {
        return getXMax(oom).compareTo(x) != -1
                && getXMin(oom).compareTo(x) != 1
                && getYMax(oom).compareTo(y) != -1
                && getYMin(oom).compareTo(y) != 1;
    }

    /**
     * @param l The line to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this contains {@code l}.
     */
    public boolean contains(V3D_LineSegment l, int oom, RoundingMode rm) {
        return contains(l.getP(), oom) && contains(l.getQ(oom, rm), oom);
    }

    /**
     * @param s The shape to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this contains {@code s}.
     */
    public boolean contains(V3D_Face_d s, int oom, RoundingMode rm) {
        return contains(s.getAABB(oom, rm), oom)
                && contains0(s, oom, rm);
    }

    /**
     * @param s The shape to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this getIntersect with {@code s}
     */
    public boolean contains0(V3D_Face s, int oom, RoundingMode rm) {
        return s.getPoints(oom, rm).values().parallelStream().allMatch(x
                -> contains(x, oom));
    }

    /**
     * @param p The point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this getIntersect with {@code pl}
     */
    public boolean intersects(V3D_Point p, int oom, RoundingMode rm) {
        return V3D_AABB_d.this.intersects(p.getX(oom, rm), p.getY(oom, rm), p.getZ(oom, rm), oom);
    }

    /**
     * This biases intersection.
     * 
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this getIntersect with {@code pl}
     */
    public boolean intersects(double x, double y, double z, 
            int oom) {
        return x.compareTo(getXMin(oom)) != -1
                && x.compareTo(getXMax(oom)) != 1
                && y.compareTo(getYMin(oom)) != -1
                && y.compareTo(getYMax(oom)) != 1
                && z.compareTo(getZMin(oom)) != -1
                && z.compareTo(getZMax(oom)) != 1;
    }

    /**
     * @param en The Axis Aligned Bounding Box to intersect.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code null} if there is no intersection; {@code en} if
     * {@code this.equals(en)}; otherwise returns the intersection.
     */
    public V3D_AABB_d getIntersect(V3D_AABB_d en, int oom) {
        if (!V3D_AABB_d.this.intersects(en, oom)) {
            return null;
        }
// Probably quicker without: 
//        if (contains(en, oom)) {
//            return this;
//        }
//        if (en.contains(this, oom)) {
//            return en;
//        }
        return new V3D_AABB_d(env, oom,
                double.max(getXMin(oom), en.getXMin(oom)),
                double.min(getXMax(oom), en.getXMax(oom)),
                double.max(getYMin(oom), en.getYMin(oom)),
                double.min(getYMax(oom), en.getYMax(oom)),
                double.max(getZMin(oom), en.getZMin(oom)),
                double.min(getZMax(oom), en.getZMax(oom)));
    }
    
    /**
     * For storing all the points. N.B 
     * @return The corners of this as points: {@link #lll}, {@link #llu}, 
     * {@link #lul}, {@link #luu}, {@link #ull}, {@link #ulu}, {@link #uul},
     * {@link #uuu}
     */
    public V3D_Point[] getPoints() {
        if (pts == null) {
            pts = new V3D_Point[8];
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
//    public HashSet<V3D_Point> getPoints() {
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return A viewport - a rectangle between pt and this such that all of
     * this is contained in the planes from the point through the viewport.
     */
    public V3D_Rectangle getViewport(V3D_Point pt, V3D_Vector v, int oom,
            RoundingMode rm) {
        V3D_Rectangle rect;
        pts = getPoints();
        V3D_Point c = getCentroid(oom, rm);
        V3D_Vector cv = new V3D_Vector(pt, c, oom, rm);
        V3D_Vector v2 = cv.getCrossProduct(v, oom, rm);
        /**
         * Calculate the plane touching this in the direction given from pt to
         * c.
         */
        // Find a closest point (there could be up to 4).
        double d2min = pt.getDistanceSquared(pts[0], oom, rm);
        V3D_Point closest = pts[0];
        for (int i = 1; i < pts.length; i++) {
            double d2 = pt.getDistanceSquared(pts[i], oom, rm);
            if (d2.compareTo(d2min) == -1) {
                d2min = d2;
                closest = pts[i];
            }
        }
        // Use the closest point to define the plane of the screen.
        V3D_Plane pl0 = new V3D_Plane(closest, cv);
        // Intersect the rays from pts to each point with the screen.
        V3D_Point[] ipts = new V3D_Point[pts.length];
        // Get the intersecting points on the screen plane from pt
        for (int i = 0; i < pts.length; i++) {
            V3D_Ray ray = new V3D_Ray(pt, pts[i], oom, rm);
            ipts[i] = (V3D_Point) ray.getIntersect(pl0, oom, rm);
        }
        // Figure out the extremes in relation to v and v2
        // Find top, bottom, left and right planes
        V3D_Plane vpl = new V3D_Plane(c, v);
        V3D_Plane v2pl = new V3D_Plane(c, v2);
        // Find top and bottom
        V3D_Point ap = new V3D_Point_d(c);
        ap.translate(v2, oom, rm);
        double aa = double.ZERO;
        V3D_Plane tpl = null;
        //V3D_Point pb = new V3D_Point_d(v2pl.getP());
        //pb.translate(v2.reverse(), oom, rm);
        double ba = double.ZERO;
        V3D_Plane bpl = null;
        for (var x : ipts) {
            V3D_Point pp = vpl.getPointOfProjectedIntersection(x, oom, rm);
            //double a = v2.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            double angle = cv.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            if (v2pl.isOnSameSide(ap, x, oom, rm)) {
                if (angle.compareTo(aa) == 1) {
                    aa = angle;
                    //System.out.println(a);
                    V3D_Point xv = new V3D_Point_d(x);
                    xv.translate(v, oom, rm);
                    tpl = new V3D_Plane(x, pt, xv, oom, rm);
                }
            } else {
                if (angle.compareTo(ba) == 1) {
                    ba = angle;
                    V3D_Point xv = new V3D_Point_d(x);
                    xv.translate(v, oom, rm);
                    bpl = new V3D_Plane(x, pt, xv, oom, rm);
                }
            }
        }
        // Find left and right
        V3D_Point lp = new V3D_Point_d(c);
        lp.translate(v.reverse(), oom, rm);
        double la = double.ZERO;
        V3D_Plane lpl = null;
        //V3D_Point pr = new V3D_Point_d(vpl.getP());
        //pr.translate(v, oom, rm);
        double ra = double.ZERO;
        V3D_Plane rpl = null;
        for (var x : ipts) {
            V3D_Point pp = v2pl.getPointOfProjectedIntersection(x, oom, rm);
            //double a = v.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            double angle = cv.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            if (vpl.isOnSameSide(lp, x, oom, rm)) {
                if (angle.compareTo(la) == 1) {
                    la = angle;
                    V3D_Point xv = new V3D_Point_d(x);
                    xv.translate(v2, oom, rm);
                    lpl = new V3D_Plane(x, pt, xv, oom, rm);
                }
            } else {
                if (angle.compareTo(ra) == 1) {
                    ra = angle;
                    V3D_Point xv = new V3D_Point_d(x);
                    xv.translate(v2, oom, rm);
                    rpl = new V3D_Plane(x, pt, xv, oom, rm);
                }
            }
        }
//        // Check
//        if (!tp.allOnSameSide(pts, oom, rm)) {
//            int debug = 1;
//        }
//        if (!bp.allOnSameSide(pts, oom, rm)) {
//            int debug = 1;
//        }
//        if (!lp.allOnSameSide(pts, oom, rm)) {
//            int debug = 1;
//        }
//        if (!rp.allOnSameSide(pts, oom, rm)) {
//            int debug = 1;
//        }
//        
//        lp.n = lp.n.getUnitVector(oom, rm);
//        rp.n = rp.n.getUnitVector(oom, rm);
//        tp.n = tp.n.getUnitVector(oom, rm);
//        bp.n = bp.n.getUnitVector(oom, rm);
        rect = new V3D_Rectangle(
                (V3D_Point) lpl.getIntersect(pl0, bpl, oom, rm),
                (V3D_Point) lpl.getIntersect(pl0, tpl, oom, rm),
                (V3D_Point) rpl.getIntersect(pl0, tpl, oom, rm),
                (V3D_Point) rpl.getIntersect(pl0, bpl, oom, rm), oom, rm);

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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return A viewport - a rectangle between pt and this such that all of
     * this is contained in the planes from the point through the viewport.
     */
    public V3D_Rectangle getViewport2(V3D_Point pt, V3D_Vector v, int oom,
            RoundingMode rm) {
        //int oomn2 = oom - 2;
        int oomn4 = oom - 4;
        V3D_Rectangle rect;
        // Get the plane of the viewport.
        V3D_Point c = getCentroid(oomn4, rm);
        double distance = c.getDistance(getPoints()[0], oomn4, rm);
        V3D_Point plpt = new V3D_Point_d(c);
        V3D_Vector vo = new V3D_Vector(c, pt, oomn4, rm).getUnitVector(oomn4, rm);
        plpt.translate(vo.multiply(distance, oomn4, rm), oomn4, rm);
        V3D_Vector cv = vo.reverse();
        V3D_Plane pl0 = new V3D_Plane(plpt, cv);
        // Figure out the extremes in relation to v (and v2).
        V3D_Vector v2 = cv.getCrossProduct(v, oomn4, rm);
        // Intersect the rays from pts to each point with the screen.
        pts = getPoints();
        V3D_Point[] ipts = new V3D_Point[pts.length];
        // Get the intersecting points on the screen plane from pt
        for (int i = 0; i < pts.length; i++) {
            V3D_Ray ray = new V3D_Ray(pt, pts[i], oomn4, rm);
            ipts[i] = (V3D_Point) ray.getIntersect(pl0, oomn4, rm);
        }
        // Find top, bottom, left and right planes
        V3D_Plane vpl = new V3D_Plane(c, v);
        V3D_Plane v2pl = new V3D_Plane(c, v2);
        // Find top and bottom
        V3D_Point ap = new V3D_Point_d(c);
        ap.translate(v2, oomn4, rm);
        double aa = double.ZERO;
        V3D_Plane tpl = null;
        //V3D_Point pb = new V3D_Point_d(v2pl.getP());
        //pb.translate(v2.reverse(), oom, rm);
        double ba = double.ZERO;
        V3D_Plane bpl = null;
        for (var x : ipts) {
            V3D_Point pp = vpl.getPointOfProjectedIntersection(x, oomn4, rm);
            //double a = v2.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            double angle = cv.getAngle(new V3D_Vector(pt, pp, oomn4, rm), oomn4, rm).abs();
            if (v2pl.isOnSameSide(ap, x, oomn4, rm)) {
                if (angle.compareTo(aa) == 1) {
                    aa = angle;
                    //System.out.println(a);
                    V3D_Point xv = new V3D_Point_d(x);
                    xv.translate(v, oomn4, rm);
                    tpl = new V3D_Plane(x, pt, xv, oomn4, rm);
                }
            } else {
                if (angle.compareTo(ba) == 1) {
                    ba = angle;
                    V3D_Point xv = new V3D_Point_d(x);
                    xv.translate(v, oomn4, rm);
                    bpl = new V3D_Plane(x, pt, xv, oomn4, rm);
                }
            }
        }
        // Find left and right
        V3D_Point lp = new V3D_Point_d(c);
        lp.translate(v.reverse(), oomn4, rm);
        double la = double.ZERO;
        V3D_Plane lpl = null;
        //V3D_Point pr = new V3D_Point_d(vpl.getP());
        //pr.translate(v, oom, rm);
        double ra = double.ZERO;
        V3D_Plane rpl = null;
        for (var x : ipts) {
            V3D_Point pp = v2pl.getPointOfProjectedIntersection(x, oomn4, rm);
            //double a = v.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            double angle = cv.getAngle(new V3D_Vector(pt, pp, oomn4, rm), oomn4, rm).abs();
            if (vpl.isOnSameSide(lp, x, oomn4, rm)) {
                if (angle.compareTo(la) == 1) {
                    la = angle;
                    V3D_Point xv = new V3D_Point_d(x);
                    xv.translate(v2, oomn4, rm);
                    lpl = new V3D_Plane(x, pt, xv, oomn4, rm);
                }
            } else {
                if (angle.compareTo(ra) == 1) {
                    ra = angle;
                    V3D_Point xv = new V3D_Point_d(x);
                    xv.translate(v2, oomn4, rm);
                    rpl = new V3D_Plane(x, pt, xv, oomn4, rm);
                }
            }
        }
//        // Check
//        if (!tp.allOnSameSide(pts, oom, rm)) {
//            int debug = 1;
//        }
//        if (!bp.allOnSameSide(pts, oom, rm)) {
//            int debug = 1;
//        }
//        if (!lp.allOnSameSide(pts, oom, rm)) {
//            int debug = 1;
//        }
//        if (!rp.allOnSameSide(pts, oom, rm)) {
//            int debug = 1;
//        }
//        
//        lp.n = lp.n.getUnitVector(oom, rm);
//        rp.n = rp.n.getUnitVector(oom, rm);
//        tp.n = tp.n.getUnitVector(oom, rm);
//        bp.n = bp.n.getUnitVector(oom, rm);

        rect = new V3D_Rectangle(
                (V3D_Point) lpl.getIntersect(pl0, bpl, oomn4, rm),
                (V3D_Point) lpl.getIntersect(pl0, tpl, oomn4, rm),
                (V3D_Point) rpl.getIntersect(pl0, tpl, oomn4, rm),
                (V3D_Point) rpl.getIntersect(pl0, bpl, oomn4, rm), oom, rm);

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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return A viewport - a rectangle between pt and this such that all of
     * this is contained in the planes from the point through the viewport.
     */
    public V3D_Rectangle getViewport3(V3D_Point pt, V3D_Vector v,
            int oom, RoundingMode rm) {
        //int oomn2 = oom - 2;
        int oomn4 = oom - 4;
        V3D_Rectangle rect;
        // Get the plane of the viewport.
        V3D_Point c = getCentroid(oomn4, rm);
        double d = c.getDistance(getPoints()[0], oomn4, rm);
        double dby2 = d.divide(2);
        V3D_Point plpt = new V3D_Point_d(c);
        V3D_Vector cpt = new V3D_Vector(c, pt, oomn4, rm);
        V3D_Vector vo = cpt.getUnitVector(oomn4, rm);
        plpt.translate(vo.multiply(d, oomn4, rm), oomn4, rm);
        V3D_Plane pl0 = new V3D_Plane(plpt, cpt);
        V3D_Vector v2 = cpt.getCrossProduct(v, oomn4, rm);
        // Find top, bottom, left and right planes
        V3D_Point ptv = new V3D_Point_d(pt);
        ptv.translate(v, oomn4, rm);
        V3D_Point ptv2 = new V3D_Point_d(pt);
        ptv2.translate(v2, oomn4, rm);
        // tp
        V3D_Vector vv = v2.getUnitVector(oomn4, rm).multiply(dby2, oomn4, rm);
        V3D_Point tppt = new V3D_Point_d(plpt);
        tppt.translate(vv, oomn4, rm);
        V3D_Plane tpl = new V3D_Plane(tppt, pt, ptv, oomn4, rm);
        // bp
        V3D_Point bppt = new V3D_Point_d(plpt);
        bppt.translate(vv.reverse(), oomn4, rm);
        V3D_Plane bpl = new V3D_Plane(bppt, pt, ptv, oomn4, rm);
        // lp
        V3D_Vector hv = v.getUnitVector(oomn4, rm).multiply(dby2, oomn4, rm);
        V3D_Point lppt = new V3D_Point_d(plpt);
        lppt.translate(hv.reverse(), oomn4, rm);
        V3D_Plane lpl = new V3D_Plane(lppt, pt, ptv2, oomn4, rm);
        // rp
        V3D_Point rppt = new V3D_Point_d(plpt);
        rppt.translate(hv, oomn4, rm);
        V3D_Plane rpl = new V3D_Plane(rppt, pt, ptv2, oomn4, rm);
        rect = new V3D_Rectangle(
                (V3D_Point) lpl.getIntersect(pl0, bpl, oomn4, rm),
                (V3D_Point) lpl.getIntersect(pl0, tpl, oomn4, rm),
                (V3D_Point) rpl.getIntersect(pl0, tpl, oomn4, rm),
                (V3D_Point) rpl.getIntersect(pl0, bpl, oomn4, rm), oom, rm);
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
    protected boolean addUnique(ArrayList<V3D_Line> ls, V3D_Line l, int oom,
            RoundingMode rm) {
        boolean unique = true;
        for (var x : ls) {
            if (x.equals(l, oom, rm)) {
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
    protected boolean addUnique(ArrayList<V3D_Point> pts, V3D_Point pt, int oom,
            RoundingMode rm) {
        boolean unique = true;
        for (var x : pts) {
            if (x.equals(pt, oom, rm)) {
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
