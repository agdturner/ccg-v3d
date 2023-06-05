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
package uk.ac.leeds.ccg.v3d.geometry.d;

import java.io.Serializable;
import java.util.ArrayList;

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
public class V3D_EnvelopeDouble implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the offset of this.
     */
    protected V3D_VectorDouble offset;

    /**
     * The minimum x-coordinate.
     */
    protected final double xMin;

    /**
     * The maximum x-coordinate.
     */
    protected final double xMax;

    /**
     * The minimum y-coordinate.
     */
    protected final double yMin;

    /**
     * The maximum y-coordinate.
     */
    protected final double yMax;

    /**
     * The minimum z-coordinate.
     */
    protected final double zMin;

    /**
     * The maximum z-coordinate.
     */
    protected final double zMax;

    /**
     * For storing all the corner points. These are in order: lbf, lba, ltf,
     * lta, rbf, rba, rtf, rta.
     */
    protected V3D_PointDouble[] pts;

    /**
     * Create a new instance.
     *
     * @param points The points used to form the envelop.
     */
    public V3D_EnvelopeDouble(V3D_PointDouble... points) {
        //offset = points[0].offset;
        //offset = V3D_VectorDouble.ZERO;
        int len = points.length;
        switch (len) {
            case 0 ->
                throw new RuntimeException("Cannot create envelope from an empty "
                        + "collection of points.");
            case 1 -> {
                //offset = points[0].offset;
                offset = V3D_VectorDouble.ZERO;
                xMin = points[0].getX();
                xMax = xMin;
                yMin = points[0].getY();
                yMax = yMin;
                zMin = points[0].getZ();
                zMax = zMin;
            }
            default -> {
                //offset = points[0].offset;
                offset = V3D_VectorDouble.ZERO;
                double xmin = points[0].getX();
                double xmax = xmin;
                double ymin = points[0].getY();
                double ymax = ymin;
                double zmin = points[0].getZ();
                double zmax = zmin;
                for (int i = 1; i < points.length; i++) {
                    double x = points[i].getX();
                    xmin = Math.min(xmin, x);
                    xmax = Math.max(xmax, x);
                    double y = points[i].getY();
                    ymin = Math.min(ymin, y);
                    ymax = Math.max(ymax, y);
                    double z = points[i].getZ();
                    zmin = Math.min(zmin, z);
                    zmax = Math.max(zmax, z);
                }
                xMin = xmin;
                xMax = xmax;
                yMin = ymin;
                yMax = ymax;
                zMin = zmin;
                zMax = zmax;
            }
        }
    }

    /**
     * Create a new instance.
     *
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     * @param z The z-coordinate of a point.
     */
    public V3D_EnvelopeDouble(double x, double y, double z) {
        this(new V3D_PointDouble(x, y, z));
    }

    /**
     * Create a new instance.
     *
     * @param xMin What {@link xMin} is set to.
     * @param xMax What {@link xMax} is set to.
     * @param yMin What {@link yMin} is set to.
     * @param yMax What {@link yMax} is set to.
     * @param zMin What {@link zMin} is set to.
     * @param zMax What {@link zMax} is set to.
     */
    public V3D_EnvelopeDouble(
            double xMin, double xMax,
            double yMin, double yMax,
            double zMin, double zMax) {
        this(new V3D_PointDouble(xMin, yMin, zMin),
                new V3D_PointDouble(xMax, yMax, zMax));
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

    /**
     * Translates this using {@code v}.
     *
     * @param v The vector of translation.
     */
    public void translate(V3D_VectorDouble v) {
        offset = offset.add(v);
        pts = null;
    }

    /**
     * @param e The V3D_Envelope to union with this.
     * @return an Envelope which is {@code this} union {@code e}.
     */
    public V3D_EnvelopeDouble union(V3D_EnvelopeDouble e) {
        if (e.isContainedBy(this)) {
            return this;
        } else {
            return new V3D_EnvelopeDouble(Math.min(e.getXMin(), getXMin()),
                    Math.max(e.getXMax(), getXMax()),
                    Math.min(e.getYMin(), getYMin()),
                    Math.max(e.getYMax(), getYMax()),
                    Math.min(e.getZMin(), getZMin()),
                    Math.max(e.getZMax(), getZMax()));
        }
    }

    /**
     * If {@code e} touches, or overlaps then it intersects.
     *
     * @param e The Vector_Envelope2D to test for intersection.
     * @return {@code true} if this intersects with {@code e}.
     */
    public boolean isIntersectedBy(V3D_EnvelopeDouble e) {
        return isIntersectedBy(e, 0.0d);
    }

    /**
     * If {@code e} touches, or overlaps then it intersects.
     *
     * @param e The Vector_Envelope2D to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if this intersects with {@code e}.
     */
    public boolean isIntersectedBy(V3D_EnvelopeDouble e, double epsilon) {
        if (isBeyond(this, e, epsilon)) {
            return !isBeyond(e, this, epsilon);
        } else {
            return true;
        }
    }

    /**
     * @param e1 The envelope to test.
     * @param e2 The envelope to test against.
     * @return {@code true} iff e1 is beyond e2 (i.e. they do not touch or
     * intersect).
     */
    public static boolean isBeyond(V3D_EnvelopeDouble e1,
            V3D_EnvelopeDouble e2) {
        return isBeyond(e1, e2, 0.0d);
    }

    /**
     * @param e1 The envelope to test.
     * @param e2 The envelope to test against.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff e1 is beyond e2 (i.e. they do not touch or
     * intersect).
     */
    public static boolean isBeyond(V3D_EnvelopeDouble e1, V3D_EnvelopeDouble e2,
            double epsilon) {
        if (e1.getXMax() + epsilon < e2.getXMin()) {
            return true;
        } else if (e1.getXMin() - epsilon > e2.getXMax()) {
            return true;
        } else if (e1.getYMax() + epsilon < e2.getYMin()) {
            return true;
        } else if (e1.getYMin() - epsilon > e2.getYMax()) {
            return true;
        } else if (e1.getZMax() + epsilon < e2.getZMin()) {
            return true;
        } else {
            return e1.getZMin() - epsilon > e2.getZMax();
        }
    }

    /**
     * Containment includes the boundary. So anything in or on the boundary is
     * contained.
     *
     * @param e V3D_Envelope
     * @return if this is contained by {@code e}
     */
    public boolean isContainedBy(V3D_EnvelopeDouble e ){
        return getXMax() <= e.getXMax()
                && getXMin() >= e.getXMin()
                && getYMax() <= e.getYMax()
                && getYMin() >= e.getYMin()
                && getZMax() <= e.getZMax()
                && getZMin() >= e.getZMin();
//    public boolean isContainedBy(V3D_EnvelopeDouble e, double epsilon) {
//        return getXMax() <= e.getXMax() + epsilon
//                && getXMin() >= e.getXMin() - epsilon
//                && getYMax() <= e.getYMax() + epsilon
//                && getYMin() >= e.getYMin() - epsilon
//                && getZMax() <= e.getZMax() + epsilon
//                && getZMin() >= e.getZMin() - epsilon;
    }

    /**
     * @param p The point to test for intersection.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean isIntersectedBy(V3D_PointDouble p) {
        return isIntersectedBy(p.getX(), p.getY(), p.getZ());
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean isIntersectedBy(double x, double y, double z) {
        return x >= getXMin() && x <= getXMax()
                && y >= getYMin() && y <= getYMax()
                && z >= getZMin() && z <= getZMax();
    }

    /**
     * @param en The envelop to intersect.
     * @return {@code null} if there is no intersection; {@code en} if
     * {@code this.equals(en)}; otherwise returns the intersection.
     */
    public V3D_EnvelopeDouble getIntersection(V3D_EnvelopeDouble en) {
        if (this.equals(en)) {
            return en;
        }
        if (!this.isIntersectedBy(en)) {
            return null;
        }
        return new V3D_EnvelopeDouble(
                Math.max(getXMin(), en.getXMin()),
                Math.min(getXMax(), en.getXMax()),
                Math.max(getYMin(), en.getYMin()),
                Math.min(getYMax(), en.getYMax()),
                Math.max(getZMin(), en.getZMin()),
                Math.min(getZMax(), en.getZMax()));
    }

    /**
     * Test for equality.
     *
     * @param e The V3D_Envelope to test for equality with this.
     * @return {@code true} iff this and e are equal.
     */
    public boolean equals(V3D_EnvelopeDouble e) {
        return this.getXMin() == e.getXMin()
                && this.getXMax() == e.getXMax()
                && this.getYMin() == e.getYMin()
                && this.getYMax() == e.getYMax()
                && this.getZMin() == e.getZMin()
                && this.getZMax() == e.getZMax();
    }

    /**
     * For getting {@link #xMin}.
     *
     * @return {@link #xMin}.
     */
    public double getXMin() {
        return xMin + offset.dx;
    }

    /**
     * For getting {@link #xMax}.
     *
     * @return {@link #xMax}.
     */
    public double getXMax() {
        return xMax + offset.dx;
    }

    /**
     * For getting {@link #yMin}.
     *
     * @return {@link #yMin}.
     */
    public double getYMin() {
        return yMin + offset.dy;
    }

    /**
     * For getting {@link #yMax}.
     *
     * @return {@link #yMax}.
     */
    public double getYMax() {
        return yMax + offset.dy;
    }

    /**
     * For getting {@link #zMin}.
     *
     * @return {@link #zMin}.
     */
    public double getZMin() {
        return zMin + offset.dz;
    }

    /**
     * For getting {@link #zMax}.
     *
     * @return {@link #zMax}.
     */
    public double getZMax() {
        return zMax + offset.dz;
    }

    /**
     * Calculate and return the approximate (or exact) centroid of the envelope.
     *
     * @return The approximate or exact centre of this.
     */
    public V3D_PointDouble getCentroid() {
        return new V3D_PointDouble(
                (this.getXMax() + this.getXMin()) / 2d,
                (this.getYMax() + this.getYMin()) / 2d,
                (this.getZMax() + this.getZMin()) / 2d);
    }

    /**
     * Return all the points of this with at least oom precision.
     *
     * @return The corners of this as points.
     */
    public V3D_PointDouble[] getPoints() {
        if (pts == null) {
            pts = new V3D_PointDouble[8];
            pts[0] = new V3D_PointDouble(getXMin(), getYMin(), getZMin()); // lbf
            pts[1] = new V3D_PointDouble(getXMin(), getYMin(), getZMax()); // lba
            pts[2] = new V3D_PointDouble(getXMin(), getYMax(), getZMin()); // ltf
            pts[3] = new V3D_PointDouble(getXMin(), getYMax(), getZMax()); // lta
            pts[4] = new V3D_PointDouble(getXMax(), getYMin(), getZMin()); // rbf
            pts[5] = new V3D_PointDouble(getXMax(), getYMin(), getZMax()); // rba
            pts[6] = new V3D_PointDouble(getXMax(), getYMax(), getZMin()); // rtf
            pts[7] = new V3D_PointDouble(getXMax(), getYMax(), getZMax()); // rta
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A viewport - a rectangle between pt and this such that all of
     * this is contained in the planes from the point through the viewport.
     */
    public V3D_RectangleDouble getViewport(V3D_PointDouble pt,
            V3D_VectorDouble v, double epsilon) {
        V3D_RectangleDouble r;
        pts = getPoints();
//        pts[0] = new V3D_Point(lba);
//        pts[1] = new V3D_Point(lbf);
//        pts[2] = new V3D_Point(lta);
//        pts[3] = new V3D_Point(ltf);
//        pts[4] = new V3D_Point(rba);
//        pts[5] = new V3D_Point(rbf);
//        pts[6] = new V3D_Point(rta);
//        pts[7] = new V3D_Point(rtf);
        V3D_PointDouble c = getCentroid();
        V3D_VectorDouble cv = new V3D_VectorDouble(pt, c);
        V3D_VectorDouble v2 = cv.getCrossProduct(v);
        /**
         * Calculate the plane touching this in the direction given from pt to
         * c.
         */
        // Find a closest point (there could be up to 4).
        double d2min = pt.getDistanceSquared(pts[0]);
        V3D_PointDouble closest = pts[0];
        for (int i = 1; i < pts.length; i++) {
            double d2 = pt.getDistanceSquared(pts[i]);
            if (d2 < d2min) {
                d2min = d2;
                closest = pts[i];
            }
        }
        // Use the closest point to define the plane of the screen.
        V3D_PlaneDouble pl0 = new V3D_PlaneDouble(closest, cv);
        // Intersect the rays from pts to each point with the screen.
        V3D_PointDouble[] ipts = new V3D_PointDouble[pts.length];
        // Get the intersecting points on the screen plane from pt
        for (int i = 0; i < pts.length; i++) {
            V3D_RayDouble ray = new V3D_RayDouble(pt, pts[i]);
            ipts[i] = (V3D_PointDouble) ray.getIntersection(pl0, epsilon);
        }
        // Figure out the extremes in relation to v and v2
        // Find top, bottom, left and right planes
        V3D_PlaneDouble vpl = new V3D_PlaneDouble(c, v);
        V3D_PlaneDouble v2pl = new V3D_PlaneDouble(c, v2);
        // Find top and bottom
        V3D_PointDouble ap = new V3D_PointDouble(c);
        ap.translate(v2);
        double aa = 0d;
        V3D_PlaneDouble tpl = null;
        //V3D_Point pb = new V3D_Point(v2pl.getP());
        //pb.translate(v2.reverse(), );
        double ba = 0d;
        V3D_PlaneDouble bpl = null;
        for (var x : ipts) {
            V3D_PointDouble pp = vpl.getPointOfProjectedIntersection(x, epsilon);
            double a = Math.abs(cv.getAngle(new V3D_VectorDouble(pt, pp)));
            if (v2pl.isOnSameSide(ap, x, epsilon)) {
                if (a > aa) {
                    aa = a;
                    //System.out.println(a);
                    V3D_PointDouble xv = new V3D_PointDouble(x);
                    xv.translate(v);
                    tpl = new V3D_PlaneDouble(x, pt, xv);
                }
            } else {
                if (a > ba) {
                    ba = a;
                    V3D_PointDouble xv = new V3D_PointDouble(x);
                    xv.translate(v);
                    bpl = new V3D_PlaneDouble(x, pt, xv);
                }
            }
        }
        // Find left and right
        V3D_PointDouble lp = new V3D_PointDouble(c);
        lp.translate(v.reverse());
        double la = 0d;
        V3D_PlaneDouble lpl = null;
        //V3D_Point pr = new V3D_Point(vpl.getP());
        //pr.translate(v, );
        double ra = 0d;
        V3D_PlaneDouble rpl = null;
        for (var x : ipts) {
            V3D_PointDouble pp = v2pl.getPointOfProjectedIntersection(x, epsilon);
            double a = Math.abs(cv.getAngle(new V3D_VectorDouble(pt, pp)));
            if (vpl.isOnSameSide(lp, x, epsilon)) {
                if (a > la) {
                    la = a;
                    V3D_PointDouble xv = new V3D_PointDouble(x);
                    xv.translate(v2);
                    lpl = new V3D_PlaneDouble(x, pt, xv);
                }
            } else {
                if (a > ra) {
                    ra = a;
                    V3D_PointDouble xv = new V3D_PointDouble(x);
                    xv.translate(v2);
                    rpl = new V3D_PlaneDouble(x, pt, xv);
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
        r = new V3D_RectangleDouble(
                (V3D_PointDouble) lpl.getIntersection(pl0, bpl, epsilon),
                (V3D_PointDouble) lpl.getIntersection(pl0, tpl, epsilon),
                (V3D_PointDouble) rpl.getIntersection(pl0, tpl, epsilon),
                (V3D_PointDouble) rpl.getIntersection(pl0, bpl, epsilon));

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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A viewport - a rectangle between pt and this such that all of
     * this is contained in the planes from the point through the viewport.
     */
    public V3D_RectangleDouble getViewport2(V3D_PointDouble pt,
            V3D_VectorDouble v, double epsilon) {
        V3D_RectangleDouble r;
        // Get the plane of the viewport.
        V3D_PointDouble c = getCentroid();
        double distance = c.getDistance(getPoints()[0]);
        V3D_PointDouble plpt = new V3D_PointDouble(c);
        V3D_VectorDouble vo = new V3D_VectorDouble(c, pt).getUnitVector();
        plpt.translate(vo.multiply(distance));
        V3D_VectorDouble cv = vo.reverse();
        V3D_PlaneDouble pl0 = new V3D_PlaneDouble(plpt, cv);
        // Figure out the extremes in relation to v (and v2).
        V3D_VectorDouble v2 = cv.getCrossProduct(v);
        // Intersect the rays from pts to each point with the screen.
        pts = getPoints();
        V3D_PointDouble[] ipts = new V3D_PointDouble[pts.length];
        // Get the intersecting points on the screen plane from pt
        for (int i = 0; i < pts.length; i++) {
            V3D_RayDouble ray = new V3D_RayDouble(pt, pts[i]);
            ipts[i] = (V3D_PointDouble) ray.getIntersection(pl0, epsilon);
        }
        // Find top, bottom, left and right planes
        V3D_PlaneDouble vpl = new V3D_PlaneDouble(c, v);
        V3D_PlaneDouble v2pl = new V3D_PlaneDouble(c, v2);
        // Find top and bottom
        V3D_PointDouble ap = new V3D_PointDouble(c);
        ap.translate(v2);
        double aa = 0d;
        V3D_PlaneDouble tpl = null;
        //V3D_Point pb = new V3D_Point(v2pl.getP());
        //pb.translate(v2.reverse(), );
        double ba = 0d;
        V3D_PlaneDouble bpl = null;
        for (var x : ipts) {
            V3D_PointDouble pp = vpl.getPointOfProjectedIntersection(x, epsilon);
            double a = Math.abs(cv.getAngle(new V3D_VectorDouble(pt, pp)));
            if (v2pl.isOnSameSide(ap, x, epsilon)) {
                if (a > aa) {
                    aa = a;
                    //System.out.println(a);
                    V3D_PointDouble xv = new V3D_PointDouble(x);
                    xv.translate(v);
                    tpl = new V3D_PlaneDouble(x, pt, xv);
                }
            } else {
                if (a > ba) {
                    ba = a;
                    V3D_PointDouble xv = new V3D_PointDouble(x);
                    xv.translate(v);
                    bpl = new V3D_PlaneDouble(x, pt, xv);
                }
            }
        }
        // Find left and right
        V3D_PointDouble lp = new V3D_PointDouble(c);
        lp.translate(v.reverse());
        double la = 0d;
        V3D_PlaneDouble lpl = null;
        //V3D_Point pr = new V3D_Point(vpl.getP());
        //pr.translate(v, );
        double ra = 0d;
        V3D_PlaneDouble rpl = null;
        for (var x : ipts) {
            V3D_PointDouble pp = v2pl.getPointOfProjectedIntersection(x, epsilon);
            double a = Math.abs(cv.getAngle(new V3D_VectorDouble(pt, pp)));
            if (vpl.isOnSameSide(lp, x, epsilon)) {
                if (a > la) {
                    la = a;
                    V3D_PointDouble xv = new V3D_PointDouble(x);
                    xv.translate(v2);
                    lpl = new V3D_PlaneDouble(x, pt, xv);
                }
            } else {
                if (a > ra) {
                    ra = a;
                    V3D_PointDouble xv = new V3D_PointDouble(x);
                    xv.translate(v2);
                    rpl = new V3D_PlaneDouble(x, pt, xv);
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

        r = new V3D_RectangleDouble(
                (V3D_PointDouble) lpl.getIntersection(pl0, bpl, epsilon),
                (V3D_PointDouble) lpl.getIntersection(pl0, tpl, epsilon),
                (V3D_PointDouble) rpl.getIntersection(pl0, tpl, epsilon),
                (V3D_PointDouble) rpl.getIntersection(pl0, bpl, epsilon));

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
     * @param zoomFactor A zoom factor. A factor of 2 and the screen will be
     * twice as close to the object.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A viewport - a rectangle between pt and this such that all of
     * this is contained in the planes from the point through the viewport.
     */
    public V3D_RectangleDouble getViewport3(V3D_PointDouble pt,
            V3D_VectorDouble v, double zoomFactor, double epsilon) {
        V3D_RectangleDouble r;
        // Get the plane of the viewport.
        V3D_PointDouble c = getCentroid();
        double d = c.getDistance(getPoints()[0]);
        double dby2 = d / 2;
        V3D_PointDouble plpt = new V3D_PointDouble(c);
        V3D_VectorDouble cpt = new V3D_VectorDouble(c, pt);
        V3D_VectorDouble vo = cpt.getUnitVector();
        plpt.translate(vo.multiply(d / zoomFactor));
        V3D_PlaneDouble pl0 = new V3D_PlaneDouble(plpt, cpt);
        V3D_VectorDouble v2 = cpt.getCrossProduct(v);
        // Find top, bottom, left and right planes
        V3D_PointDouble ptv = new V3D_PointDouble(pt);
        ptv.translate(v);
        V3D_PointDouble ptv2 = new V3D_PointDouble(pt);
        ptv2.translate(v2);
        // tp
        V3D_VectorDouble vv = v2.getUnitVector().multiply(dby2);
        V3D_PointDouble tppt = new V3D_PointDouble(plpt);
        tppt.translate(vv);
        V3D_PlaneDouble tpl = new V3D_PlaneDouble(tppt, pt, ptv);
        // bp
        V3D_PointDouble bppt = new V3D_PointDouble(plpt);
        bppt.translate(vv.reverse());
        V3D_PlaneDouble bpl = new V3D_PlaneDouble(bppt, pt, ptv);
        // lp
        V3D_VectorDouble hv = v.getUnitVector().multiply(dby2);
        V3D_PointDouble lppt = new V3D_PointDouble(plpt);
        lppt.translate(hv.reverse());
        V3D_PlaneDouble lpl = new V3D_PlaneDouble(lppt, pt, ptv2);
        // rp
        V3D_PointDouble rppt = new V3D_PointDouble(plpt);
        rppt.translate(hv);
        V3D_PlaneDouble rpl = new V3D_PlaneDouble(rppt, pt, ptv2);
        r = new V3D_RectangleDouble(
                (V3D_PointDouble) lpl.getIntersection(pl0, bpl, epsilon),
                (V3D_PointDouble) lpl.getIntersection(pl0, tpl, epsilon),
                (V3D_PointDouble) rpl.getIntersection(pl0, tpl, epsilon),
                (V3D_PointDouble) rpl.getIntersection(pl0, bpl, epsilon));
        return r;
    }

    /**
     * A collection method to add l to ls iff there is not already an l in ls.
     *
     * @param ls The collection.
     * @param l The line segment to add.
     * @return {@code true} iff l is unique and is added to ls.
     */
    protected boolean addUnique(ArrayList<V3D_LineDouble> ls, V3D_LineDouble l) {
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
     * @return {@code true} iff l is unique and is added to ls.
     */
    protected boolean addUnique(ArrayList<V3D_PointDouble> pts,
            V3D_PointDouble pt) {
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
