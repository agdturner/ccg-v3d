/*
 * Copyright 2020 Andy Turner, University of Leeds.
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

import java.io.Serializable;
import java.math.RoundingMode;
import java.util.ArrayList;
import uk.ac.leeds.ccg.math.number.Math_BigRational;

/**
 * An envelope contains all the extreme values with respect to the X, Y and Z
 * axes. This is also known as an Axis Aligned Bounding Box (AABB). In this
 * implementation, it may have length of zero in any direction. For a point the
 * envelope is essentially the point. The envelope may also be a line or a
 * rectangle, but often it will have 3 dimensions.
 *
 * The following depiction of a bounding box indicate the location of the
 * different faces and also gives an abbreviated name of each point that
 * reflects these. This points are not stored explicitly in an instance of the
 * class with these names, but for a normal envelope (which is not a point or a
 * line or a plane), there are these 8 points stored in the rectangles that
 * represent each face. {@code
 *                                                         z
 *                                    y                   -
 *                                    +                  /
 *                                    |                 /
 *                                    |                /
 *                                    |               /
 *                                    |              /
 *                                    |    
 *                     lta _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ rta
 *                        /|                                /|
 *                       / |                               / |
 *                      /  |                              /  |
 *                     /   |                             /   |
 *                    /    |                            /    |
 *                   /     |                           /     |
 *                  /      |                          /      |
 *                 /       |                         /       |
 *                /        |                        /        |
 *               /         |                       /         |
 *          ltf /_ _ _ _ _ |_ _ _ _ _ _ _ _ _ _ _ /rtf       |
 *             |           |                     |           |
 *             |           |                     |           |
 *     x - ----|--         |                     |           |  ------ + x
 *             |           |                     |           |
 *             |        lba|_ _ _ _ _ _ _ _ _ _ _|_ _ _ _ _ _|rba
 *             |           /                     |           /
 *             |          /                      |          /
 *             |         /                       |         /
 *             |        /                        |        /
 *             |       /                         |       /
 *             |      /            ymin          |      /
 *             |     /                           |     /
 *             |    /                            |    /
 *             |   /                             |   /
 *             |  /                              |  /
 *             | /                               | /
 *             |/_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ |/
 *          lbf                                  rbf
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
public class V3D_Envelope implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * For storing the offset of this.
     */
    private V3D_Vector offset;
    
    /**
     * The minimum x-coordinate.
     */
    private final Math_BigRational xMin;

    /**
     * The maximum x-coordinate.
     */
    private final Math_BigRational xMax;

    /**
     * The minimum y-coordinate.
     */
    private final Math_BigRational yMin;

    /**
     * The maximum y-coordinate.
     */
    private final Math_BigRational yMax;

    /**
     * The minimum z-coordinate.
     */
    private final Math_BigRational zMin;

    /**
     * The maximum z-coordinate.
     */
    private final Math_BigRational zMax;

    /**
     * For storing all the corner points. These are in order: lbf, lba, ltf,
     * lta, rbf, rba, rtf, rta.
     */
    protected V3D_Point[] pts;

    /**
     * For storing the precision of pts.
     */
    protected int ptsoom;

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points The points used to form the envelop.
     */
    public V3D_Envelope(int oom, RoundingMode rm, V3D_Point... points) {
        //offset = points[0].offset;
        offset = V3D_Vector.ZERO;
        int len = points.length;
        switch (len) {
            case 0 ->
                throw new RuntimeException("Cannot create envelope from an empty "
                        + "collection of points.");
            case 1 -> {
                xMin = points[0].getX(oom, rm);
                xMax = points[0].getX(oom, rm);
                yMin = points[0].getY(oom, rm);
                yMax = points[0].getY(oom, rm);
                zMin = points[0].getZ(oom, rm);
                zMax = points[0].getZ(oom, rm);
            }
            default -> {
                Math_BigRational xmin = points[0].getX(oom, rm);
                Math_BigRational xmax = points[0].getX(oom, rm);
                Math_BigRational ymin = points[0].getY(oom, rm);
                Math_BigRational ymax = points[0].getY(oom, rm);
                Math_BigRational zmin = points[0].getZ(oom, rm);
                Math_BigRational zmax = points[0].getZ(oom, rm);
                for (int i = 1; i < points.length; i++) {
                    xmin = Math_BigRational.min(xmin, points[i].getX(oom, rm));
                    xmax = Math_BigRational.max(xmax, points[i].getX(oom, rm));
                    ymin = Math_BigRational.min(ymin, points[i].getY(oom, rm));
                    ymax = Math_BigRational.max(ymax, points[i].getY(oom, rm));
                    zmin = Math_BigRational.min(zmin, points[i].getZ(oom, rm));
                    zmax = Math_BigRational.max(zmax, points[i].getZ(oom, rm));
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
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     * @param z The z-coordinate of a point.
     */
    public V3D_Envelope(int oom, RoundingMode rm,
            Math_BigRational x, Math_BigRational y, Math_BigRational z) {
        this(oom, rm, new V3D_Point(x, y, z));
    }

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param xMin What {@link xMin} is set to.
     * @param xMax What {@link xMax} is set to.
     * @param yMin What {@link yMin} is set to.
     * @param yMax What {@link yMax} is set to.
     * @param zMin What {@link zMin} is set to.
     * @param zMax What {@link zMax} is set to.
     */
    public V3D_Envelope(int oom, RoundingMode rm,
            Math_BigRational xMin, Math_BigRational xMax,
            Math_BigRational yMin, Math_BigRational yMax,
            Math_BigRational zMin, Math_BigRational zMax) {
        this(oom, rm, new V3D_Point(xMin, yMin, zMin),
                new V3D_Point(xMax, yMax, zMax));
    }

    @Override
    public String toString() {
        return toString(-3, RoundingMode.HALF_UP);
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
    }

    /**
     * @param e The V3D_Envelope to union with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return an Envelope which is {@code this} union {@code e}.
     */
    public V3D_Envelope union(V3D_Envelope e, int oom, RoundingMode rm) {
        if (e.isContainedBy(this, oom, rm)) {
            return this;
        } else {
            return new V3D_Envelope(oom, rm,
                    e.getXMin(oom, rm).min(getXMin(oom, rm)),
                    e.getXMax(oom, rm).max(getXMax(oom, rm)),
                    e.getYMin(oom, rm).min(getYMin(oom, rm)),
                    e.getYMax(oom, rm).max(getYMax(oom, rm)),
                    e.getZMin(oom, rm).min(getZMin(oom, rm)),
                    e.getZMax(oom, rm).max(getZMax(oom, rm)));
        }
    }

    /**
     * If {@code e} touches, or overlaps then it intersects.
     *
     * @param e The Vector_Envelope2D to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this intersects with {@code e}.
     */
    public boolean isIntersectedBy(V3D_Envelope e, int oom, RoundingMode rm) {
        if (isBeyond(this, e, oom, rm)) {
            return !isBeyond(e, this, oom, rm);
        } else {
            return true;
        }
    }

    /**
     * @param e1 The envelope to test.
     * @param e2 The envelope to test against.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff e1 is beyond e2 (i.e. they do not touch or
     * intersect).
     */
    public static boolean isBeyond(V3D_Envelope e1, V3D_Envelope e2, int oom,
            RoundingMode rm) {
        if (e1.getXMax(oom, rm).compareTo(e2.getXMin(oom, rm)) == -1) {
            return true;
        } else if (e1.getXMin(oom, rm).compareTo(e2.getXMax(oom, rm)) == 1) {
            return true;
        } else if (e1.getYMax(oom, rm).compareTo(e2.getYMin(oom, rm)) == -1) {
            return true;
        } else if (e1.getYMin(oom, rm).compareTo(e2.getYMax(oom, rm)) == 1) {
            return true;
        } else if (e1.getZMax(oom, rm).compareTo(e2.getZMin(oom, rm)) == -1) {
            return true;
        } else {
            return e1.getZMin(oom, rm).compareTo(e2.getZMax(oom, rm)) == 1;
        }
    }

    /**
     * Containment includes the boundary. So anything in or on the boundary is
     * contained.
     *
     * @param e V3D_Envelope
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return if this is contained by {@code e}
     */
    public boolean isContainedBy(V3D_Envelope e, int oom, RoundingMode rm) {
        return getXMax(oom, rm).compareTo(e.getXMax(oom, rm)) != 1
                && getXMin(oom, rm).compareTo(e.getXMin(oom, rm)) != -1
                && getYMax(oom, rm).compareTo(e.getYMax(oom, rm)) != 1
                && getYMin(oom, rm).compareTo(e.getYMin(oom, rm)) != -1
                && getZMax(oom, rm).compareTo(e.getZMax(oom, rm)) != 1
                && getZMin(oom, rm).compareTo(e.getZMin(oom, rm)) != -1;
    }

    /**
     * @param p The point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean isIntersectedBy(V3D_Point p, int oom, RoundingMode rm) {
        return isIntersectedBy(p.getX(oom, rm), p.getY(oom, rm), p.getZ(oom, rm), oom, rm);
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean isIntersectedBy(Math_BigRational x, Math_BigRational y,
            Math_BigRational z, int oom, RoundingMode rm) {
        return x.compareTo(getXMin(oom, rm)) != -1 && x.compareTo(getXMax(oom, rm)) != 1
                && y.compareTo(getYMin(oom, rm)) != -1 && y.compareTo(getYMax(oom, rm)) != 1
                && z.compareTo(getZMin(oom, rm)) != -1 && z.compareTo(getZMax(oom, rm)) != 1;
    }

    /**
     * @param en The envelop to intersect.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code null} if there is no intersection; {@code en} if
     * {@code this.equals(en)}; otherwise returns the intersection.
     */
    public V3D_Envelope getIntersection(V3D_Envelope en, int oom, RoundingMode rm) {
        if (this.equals(en, oom, rm)) {
            return en;
        }
        if (!this.isIntersectedBy(en, oom, rm)) {
            return null;
        }
        return new V3D_Envelope(oom, rm,
                getXMin(oom, rm).max(en.getXMin(oom, rm)),
                getXMax(oom, rm).min(en.getXMax(oom, rm)),
                getYMin(oom, rm).max(en.getYMin(oom, rm)),
                getYMax(oom, rm).min(en.getYMax(oom, rm)),
                getZMin(oom, rm).max(en.getZMin(oom, rm)),
                getZMax(oom, rm).min(en.getZMax(oom, rm)));
    }

    /**
     * Test for equality.
     *
     * @param e The V3D_Envelope to test for equality with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff this and e are equal.
     */
    public boolean equals(V3D_Envelope e, int oom, RoundingMode rm) {
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
    public Math_BigRational getXMin(int oom) {
        return getXMin(oom, RoundingMode.FLOOR);
    }

    /**
     * For getting {@link #xMin} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #xMin} rounded.
     */
    public Math_BigRational getXMin(int oom, RoundingMode rm) {
        return xMin.add(offset.getDX(oom - 2, rm)).round(oom, rm);
    }

    /**
     * For getting {@link #xMax} rounded. RoundingMode.CEILING is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #xMax} rounded.
     */
    public Math_BigRational getXMax(int oom) {
        return getXMax(oom, RoundingMode.CEILING);
    }

    /**
     * For getting {@link #xMax} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #xMax} rounded.
     */
    public Math_BigRational getXMax(int oom, RoundingMode rm) {
        return xMax.add(offset.getDX(oom - 2, rm)).round(oom, rm);
    }

    /**
     * For getting {@link #yMin} rounded. RoundingMode.FLOOR is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #yMin} rounded.
     */
    public Math_BigRational getYMin(int oom) {
        return getYMin(oom, RoundingMode.FLOOR);
    }

    /**
     * For getting {@link #yMin} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #yMin} rounded.
     */
    public Math_BigRational getYMin(int oom, RoundingMode rm) {
        return yMin.add(offset.getDY(oom - 2, rm)).round(oom, rm);
    }

    /**
     * For getting {@link #yMax} rounded. RoundingMode.CEILING is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #yMax} rounded.
     */
    public Math_BigRational getYMax(int oom) {
        return getYMax(oom, RoundingMode.CEILING);
    }

    /**
     * For getting {@link #yMax} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #yMax} rounded.
     */
    public Math_BigRational getYMax(int oom, RoundingMode rm) {
        return yMax.add(offset.getDY(oom - 2, rm)).round(oom, rm);
    }

    /**
     * For getting {@link #zMin} rounded. RoundingMode.FLOOR is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #zMin} rounded.
     */
    public Math_BigRational getZMin(int oom) {
        return getZMin(oom, RoundingMode.FLOOR);
    }

    /**
     * For getting {@link #zMin} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #zMin} rounded.
     */
    public Math_BigRational getZMin(int oom, RoundingMode rm) {
        return zMin.add(offset.getDZ(oom - 2, rm)).round(oom, rm);
    }

    /**
     * For getting {@link #zMax} rounded. RoundingMode.CEILING is used.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #zMax} rounded.
     */
    public Math_BigRational getZMax(int oom) {
        return getZMax(oom, RoundingMode.CEILING);
    }

    /**
     * For getting {@link #zMax} rounded.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #zMax} rounded.
     */
    public Math_BigRational getZMax(int oom, RoundingMode rm) {
        return zMax.add(offset.getDZ(oom - 2, rm)).round(oom, rm);
    }

    /**
     * Calculate and return the approximate (or exact) centroid of the envelope.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return The approximate or exact centre of this.
     */
    public V3D_Point getCentroid(int oom, RoundingMode rm) {
        return new V3D_Point(
                this.getXMax(oom, rm).add(this.getXMin(oom, rm)).divide(2),
                this.getYMax(oom, rm).add(this.getYMin(oom, rm)).divide(2),
                this.getZMax(oom, rm).add(this.getZMin(oom, rm)).divide(2));
    }

    /**
     * Return all the points of this with at least oom precision.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode used in the calculation.
     * @return The corners of this as points.
     */
    public V3D_Point[] getPoints(int oom, RoundingMode rm) {
        if (pts == null || ptsoom > oom) {
            pts = new V3D_Point[8];
            pts[0] = new V3D_Point(getXMin(oom, rm), getYMin(oom, rm), getZMin(oom, rm)); // lbf
            pts[1] = new V3D_Point(getXMin(oom, rm), getYMin(oom, rm), getZMax(oom, rm)); // lba
            pts[2] = new V3D_Point(getXMin(oom, rm), getYMax(oom, rm), getZMin(oom, rm)); // ltf
            pts[3] = new V3D_Point(getXMin(oom, rm), getYMax(oom, rm), getZMax(oom, rm)); // lta
            pts[4] = new V3D_Point(getXMax(oom, rm), getYMin(oom, rm), getZMin(oom, rm)); // rbf
            pts[5] = new V3D_Point(getXMax(oom, rm), getYMin(oom, rm), getZMax(oom, rm)); // rba
            pts[6] = new V3D_Point(getXMax(oom, rm), getYMax(oom, rm), getZMin(oom, rm)); // rtf
            pts[7] = new V3D_Point(getXMax(oom, rm), getYMax(oom, rm), getZMax(oom, rm)); // rta
            ptsoom = oom;
        }
        return pts;
    }

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
        V3D_Rectangle r;
        pts = getPoints(oom, rm);
//        pts[0] = new V3D_Point(lba);
//        pts[1] = new V3D_Point(lbf);
//        pts[2] = new V3D_Point(lta);
//        pts[3] = new V3D_Point(ltf);
//        pts[4] = new V3D_Point(rba);
//        pts[5] = new V3D_Point(rbf);
//        pts[6] = new V3D_Point(rta);
//        pts[7] = new V3D_Point(rtf);
        V3D_Point c = getCentroid(oom, rm);
        V3D_Vector cv = new V3D_Vector(pt, c, oom, rm);
        V3D_Vector v2 = cv.getCrossProduct(v, oom, rm);
        /**
         * Calculate the plane touching this in the direction given from pt to
         * c.
         */
        // Find a closest point (there could be up to 4).
        Math_BigRational d2min = pt.getDistanceSquared(pts[0], oom, rm);
        V3D_Point closest = pts[0];
        for (int i = 1; i < pts.length; i++) {
            Math_BigRational d2 = pt.getDistanceSquared(pts[i], oom, rm);
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
            ipts[i] = (V3D_Point) ray.getIntersection(pl0, oom, rm);
        }
        // Figure out the extremes in relation to v and v2
        // Find top, bottom, left and right planes
        V3D_Plane vpl = new V3D_Plane(c, v);
        V3D_Plane v2pl = new V3D_Plane(c, v2);
        // Find top and bottom
        V3D_Point ap = new V3D_Point(c);
        ap.translate(v2, oom, rm);
        Math_BigRational aa = Math_BigRational.ZERO;
        V3D_Plane tpl = null;
        //V3D_Point pb = new V3D_Point(v2pl.getP());
        //pb.translate(v2.reverse(), oom, rm);
        Math_BigRational ba = Math_BigRational.ZERO;
        V3D_Plane bpl = null;
        for (var x : ipts) {
            V3D_Point pp = vpl.getPointOfProjectedIntersection(x, oom, rm);
            //Math_BigRational a = v2.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            Math_BigRational a = cv.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            if (v2pl.isOnSameSide(ap, x, oom, rm)) {
                if (a.compareTo(aa) == 1) {
                    aa = a;
                    //System.out.println(a);
                    V3D_Point xv = new V3D_Point(x);
                    xv.translate(v, oom, rm);
                    tpl = new V3D_Plane(x, pt, xv, oom, rm);
                }
            } else {
                if (a.compareTo(ba) == 1) {
                    ba = a;
                    V3D_Point xv = new V3D_Point(x);
                    xv.translate(v, oom, rm);
                    bpl = new V3D_Plane(x, pt, xv, oom, rm);
                }
            }
        }
        // Find left and right
        V3D_Point lp = new V3D_Point(c);
        lp.translate(v.reverse(), oom, rm);
        Math_BigRational la = Math_BigRational.ZERO;
        V3D_Plane lpl = null;
        //V3D_Point pr = new V3D_Point(vpl.getP());
        //pr.translate(v, oom, rm);
        Math_BigRational ra = Math_BigRational.ZERO;
        V3D_Plane rpl = null;
        for (var x : ipts) {
            V3D_Point pp = v2pl.getPointOfProjectedIntersection(x, oom, rm);
            //Math_BigRational a = v.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            Math_BigRational a = cv.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            if (vpl.isOnSameSide(lp, x, oom, rm)) {
                if (a.compareTo(la) == 1) {
                    la = a;
                    V3D_Point xv = new V3D_Point(x);
                    xv.translate(v2, oom, rm);
                    lpl = new V3D_Plane(x, pt, xv, oom, rm);
                }
            } else {
                if (a.compareTo(ra) == 1) {
                    ra = a;
                    V3D_Point xv = new V3D_Point(x);
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
        r = new V3D_Rectangle(
                (V3D_Point) lpl.getIntersection(pl0, bpl, oom, rm),
                (V3D_Point) lpl.getIntersection(pl0, tpl, oom, rm),
                (V3D_Point) rpl.getIntersection(pl0, tpl, oom, rm),
                (V3D_Point) rpl.getIntersection(pl0, bpl, oom, rm), oom, rm);

        return r;
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
        V3D_Rectangle r;
        // Get the plane of the viewport.
        V3D_Point c = getCentroid(oom, rm);
        Math_BigRational distance = Math_BigRational.valueOf(
                c.getDistance(getPoints(oom, rm)[0], oom, rm));
        V3D_Point plpt = new V3D_Point(c);
        V3D_Vector vo = new V3D_Vector(c, pt, oom, rm);
        plpt.translate(vo.multiply(distance, oom, rm), oom, rm);
        V3D_Vector cv = vo.reverse();
        V3D_Plane pl0 = new V3D_Plane(plpt, cv);
        // Figure out the extremes in relation to v (and v2).
        V3D_Vector v2 = cv.getCrossProduct(v, oom, rm);
        // Intersect the rays from pts to each point with the screen.
        V3D_Point[] ipts = new V3D_Point[pts.length];
        // Get the intersecting points on the screen plane from pt
        for (int i = 0; i < pts.length; i++) {
            V3D_Ray ray = new V3D_Ray(pt, pts[i], oom, rm);
            ipts[i] = (V3D_Point) ray.getIntersection(pl0, oom, rm);
        }
        // Find top, bottom, left and right planes
        V3D_Plane vpl = new V3D_Plane(c, v);
        V3D_Plane v2pl = new V3D_Plane(c, v2);
        // Find top and bottom
        V3D_Point ap = new V3D_Point(c);
        ap.translate(v2, oom, rm);
        Math_BigRational aa = Math_BigRational.ZERO;
        V3D_Plane tpl = null;
        //V3D_Point pb = new V3D_Point(v2pl.getP());
        //pb.translate(v2.reverse(), oom, rm);
        Math_BigRational ba = Math_BigRational.ZERO;
        V3D_Plane bpl = null;
        for (var x : ipts) {
            V3D_Point pp = vpl.getPointOfProjectedIntersection(x, oom, rm);
            //Math_BigRational a = v2.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            Math_BigRational a = cv.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            if (v2pl.isOnSameSide(ap, x, oom, rm)) {
                if (a.compareTo(aa) == 1) {
                    aa = a;
                    //System.out.println(a);
                    V3D_Point xv = new V3D_Point(x);
                    xv.translate(v, oom, rm);
                    tpl = new V3D_Plane(x, pt, xv, oom, rm);
                }
            } else {
                if (a.compareTo(ba) == 1) {
                    ba = a;
                    V3D_Point xv = new V3D_Point(x);
                    xv.translate(v, oom, rm);
                    bpl = new V3D_Plane(x, pt, xv, oom, rm);
                }
            }
        }
        // Find left and right
        V3D_Point lp = new V3D_Point(c);
        lp.translate(v.reverse(), oom, rm);
        Math_BigRational la = Math_BigRational.ZERO;
        V3D_Plane lpl = null;
        //V3D_Point pr = new V3D_Point(vpl.getP());
        //pr.translate(v, oom, rm);
        Math_BigRational ra = Math_BigRational.ZERO;
        V3D_Plane rpl = null;
        for (var x : ipts) {
            V3D_Point pp = v2pl.getPointOfProjectedIntersection(x, oom, rm);
            //Math_BigRational a = v.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            Math_BigRational a = cv.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            if (vpl.isOnSameSide(lp, x, oom, rm)) {
                if (a.compareTo(la) == 1) {
                    la = a;
                    V3D_Point xv = new V3D_Point(x);
                    xv.translate(v2, oom, rm);
                    lpl = new V3D_Plane(x, pt, xv, oom, rm);
                }
            } else {
                if (a.compareTo(ra) == 1) {
                    ra = a;
                    V3D_Point xv = new V3D_Point(x);
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

        r = new V3D_Rectangle(
                (V3D_Point) lpl.getIntersection(pl0, bpl, oom, rm),
                (V3D_Point) lpl.getIntersection(pl0, tpl, oom, rm),
                (V3D_Point) rpl.getIntersection(pl0, tpl, oom, rm),
                (V3D_Point) rpl.getIntersection(pl0, bpl, oom, rm), oom, rm);

        return r;
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
        V3D_Rectangle r;
        // Get the plane of the viewport.
        V3D_Point c = getCentroid(oom, rm);
        Math_BigRational d = Math_BigRational.valueOf(
                c.getDistance(getPoints(oom, rm)[0], oom, rm));
        Math_BigRational dby2 = d.divide(2);
        V3D_Point plpt = new V3D_Point(c);
        V3D_Vector cpt = new V3D_Vector(c, pt, oom, rm);
        V3D_Vector vo = cpt.getUnitVector(oom, rm);
        plpt.translate(vo.multiply(d, oom, rm), oom, rm);
        V3D_Plane pl0 = new V3D_Plane(plpt, cpt);
        V3D_Vector v2 = cpt.getCrossProduct(v, oom, rm);
        // Find top, bottom, left and right planes
        V3D_Point ptv = new V3D_Point(pt);
        ptv.translate(v, oom, rm);
        V3D_Point ptv2 = new V3D_Point(pt);
        ptv2.translate(v2, oom, rm);
        // tp
        V3D_Vector vv = v2.getUnitVector(oom, rm).multiply(dby2, oom, rm);
        V3D_Point tppt = new V3D_Point(plpt);
        tppt.translate(vv, oom, rm);
        V3D_Plane tpl = new V3D_Plane(tppt, pt, ptv, oom, rm);
        // bp
        V3D_Point bppt = new V3D_Point(plpt);
        bppt.translate(vv.reverse(), oom, rm);
        V3D_Plane bpl = new V3D_Plane(bppt, pt, ptv, oom, rm);
        // lp
        V3D_Vector hv = v.getUnitVector(oom, rm).multiply(dby2, oom, rm);
        V3D_Point lppt = new V3D_Point(plpt);
        lppt.translate(hv.reverse(), oom, rm);
        V3D_Plane lpl = new V3D_Plane(lppt, pt, ptv2, oom, rm);
        // rp
        V3D_Point rppt = new V3D_Point(plpt);
        rppt.translate(hv, oom, rm);
        V3D_Plane rpl = new V3D_Plane(rppt, pt, ptv2, oom, rm);
        r = new V3D_Rectangle(
                (V3D_Point) lpl.getIntersection(pl0, bpl, oom, rm),
                (V3D_Point) lpl.getIntersection(pl0, tpl, oom, rm),
                (V3D_Point) rpl.getIntersection(pl0, tpl, oom, rm),
                (V3D_Point) rpl.getIntersection(pl0, bpl, oom, rm), oom, rm);
        return r;
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
     * A collection method to add pt to pts iff there is not already a pt in pts.
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
