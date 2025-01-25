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

import ch.obermuhlner.math.big.BigRational;
import java.io.Serializable;
import java.math.RoundingMode;
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
public class V3D_Envelope implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the offset of this.
     */
    private V3D_Vector offset;

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
     * The minimum z-coordinate.
     */
    private final BigRational zMin;

    /**
     * The maximum z-coordinate.
     */
    private final BigRational zMax;

    /**
     * The minimum x-coordinate Plane.
     */
    private V3D_Plane xMinPlane;

    /**
     * xMinPlane oom.
     */
    private int xMinPlane_oom;

    /**
     * The maximum x-coordinate Plane.
     */
    private V3D_Plane xMaxPlane;

    /**
     * xMaxPlane oom.
     */
    private int xMaxPlane_oom;

    /**
     * The minimum y-coordinate Plane.
     */
    private V3D_Plane yMinPlane;

    /**
     * yMinPlane oom.
     */
    private int yMinPlane_oom;

    /**
     * The maximum y-coordinate Plane.
     */
    private V3D_Plane yMaxPlane;

    /**
     * yMaxPlane oom.
     */
    private int yMaxPlane_oom;

    /**
     * The minimum z-coordinate Plane.
     */
    private V3D_Plane zMinPlane;

    /**
     * zMinPlane oom.
     */
    private int zMinPlane_oom;

    /**
     * The maximum z-coordinate Plane.
     */
    private V3D_Plane zMaxPlane;

    /**
     * zMaxPlane oom.
     */
    private int zMaxPlane_oom;

    /**
     * lbf.
     */
    private V3D_Point lbf;

    /**
     * lbf oom.
     */
    private int lbf_oom;

    /**
     * ltf.
     */
    private V3D_Point ltf;

    /**
     * ltf oom.
     */
    private int ltf_oom;

    /**
     * rtf.
     */
    private V3D_Point rtf;

    /**
     * rtf oom.
     */
    private int rtf_oom;

    /**
     * rbf.
     */
    private V3D_Point rbf;

    /**
     * rbf oom.
     */
    private int rbf_oom;

    /**
     * lba.
     */
    private V3D_Point lba;

    /**
     * lba oom.
     */
    private int lba_oom;

    /**
     * lta.
     */
    private V3D_Point lta;

    /**
     * lta oom.
     */
    private int lta_oom;

    /**
     * rta.
     */
    private V3D_Point rta;

    /**
     * rta oom.
     */
    private int rta_oom;

    /**
     * rba.
     */
    private V3D_Point rba;

    /**
     * rba oom.
     */
    private int rba_oom;

    /**
     * For storing all the corner points. These are in order: lbf, lba, ltf,
     * lta, rbf, rba, rtf, rta.
     */
    protected V3D_Point[] pts;

    /**
     * For storing the precision of pts.
     */
    protected int pts_oom;

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points The points used to form the envelop.
     */
    public V3D_Envelope(int oom, V3D_Point... points) {
        //offset = points[0].offset;
        offset = V3D_Vector.ZERO;
        int len = points.length;
        switch (len) {
            case 0 ->
                throw new RuntimeException("Cannot create envelope from an empty "
                        + "collection of points.");
            case 1 -> {
                xMin = points[0].getX(oom, RoundingMode.FLOOR);
                xMax = points[0].getX(oom, RoundingMode.CEILING);
                yMin = points[0].getY(oom, RoundingMode.FLOOR);
                yMax = points[0].getY(oom, RoundingMode.CEILING);
                zMin = points[0].getZ(oom, RoundingMode.FLOOR);
                zMax = points[0].getZ(oom, RoundingMode.CEILING);
            }
            default -> {
                BigRational xmin = points[0].getX(oom, RoundingMode.FLOOR);
                BigRational xmax = points[0].getX(oom, RoundingMode.CEILING);
                BigRational ymin = points[0].getY(oom, RoundingMode.FLOOR);
                BigRational ymax = points[0].getY(oom, RoundingMode.CEILING);
                BigRational zmin = points[0].getZ(oom, RoundingMode.FLOOR);
                BigRational zmax = points[0].getZ(oom, RoundingMode.CEILING);
                for (int i = 1; i < points.length; i++) {
                    xmin = BigRational.min(xmin, points[i].getX(oom, RoundingMode.FLOOR));
                    xmax = BigRational.max(xmax, points[i].getX(oom, RoundingMode.CEILING));
                    ymin = BigRational.min(ymin, points[i].getY(oom, RoundingMode.FLOOR));
                    ymax = BigRational.max(ymax, points[i].getY(oom, RoundingMode.CEILING));
                    zmin = BigRational.min(zmin, points[i].getZ(oom, RoundingMode.FLOOR));
                    zmax = BigRational.max(zmax, points[i].getZ(oom, RoundingMode.CEILING));
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
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     * @param z The z-coordinate of a point.
     */
    public V3D_Envelope(int oom, BigRational x, BigRational y, BigRational z) {
        this(oom, new V3D_Point(x, y, z));
    }

    /**
     * Create a new instance.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param xMin What {@link xMin} is set to.
     * @param xMax What {@link xMax} is set to.
     * @param yMin What {@link yMin} is set to.
     * @param yMax What {@link yMax} is set to.
     * @param zMin What {@link zMin} is set to.
     * @param zMax What {@link zMax} is set to.
     */
    public V3D_Envelope(int oom,
            BigRational xMin, BigRational xMax,
            BigRational yMin, BigRational yMax,
            BigRational zMin, BigRational zMax) {
        this(oom, new V3D_Point(xMin, yMin, zMin),
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
     * @return an Envelope which is {@code this} union {@code e}.
     */
    public V3D_Envelope union(V3D_Envelope e, int oom) {
        if (e.isContainedBy(this, oom)) {
            return this;
        } else {
            return new V3D_Envelope(oom,
                    BigRational.min(e.getXMin(oom), getXMin(oom)),
                    BigRational.max(e.getXMax(oom), getXMax(oom)),
                    BigRational.min(e.getYMin(oom), getYMin(oom)),
                    BigRational.max(e.getYMax(oom), getYMax(oom)),
                    BigRational.min(e.getZMin(oom), getZMin(oom)),
                    BigRational.max(e.getZMax(oom), getZMax(oom)));
        }
    }

    /**
     * If {@code e} touches, or overlaps then it intersects. For collision 
     * avoidance, this is biased towards returning an intersection even if there 
     * may not be one at a lower oom precision.
     *
     * @param e The Vector_Envelope2D to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with {@code e} it the {@code oom}
     * level of precision.
     */
    public boolean isIntersectedBy(V3D_Envelope e, int oom) {
        if (isBeyond(e, oom)) {
            return !e.isBeyond(this, oom);
        } else {
            return true;
        }
    }

    /**
     * @param e The envelope to test against.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff e is beyond this (i.e. they do not touch or
     * intersect).
     */
    public boolean isBeyond(V3D_Envelope e, int oom) {
        if (getXMax(oom).compareTo(e.getXMin(oom)) == -1) {
            return true;
        } else if (getXMin(oom).compareTo(e.getXMax(oom)) == 1) {
            return true;
        } else if (getYMax(oom).compareTo(e.getYMin(oom)) == -1) {
            return true;
        } else if (getYMin(oom).compareTo(e.getYMax(oom)) == 1) {
            return true;
        } else if (getZMax(oom).compareTo(e.getZMin(oom)) == -1) {
            return true;
        } else {
            return getZMin(oom).compareTo(e.getZMax(oom)) == 1;
        }
    }

    /**
     * Containment includes the boundary. So anything in or on the boundary is
     * contained.
     *
     * @param e V3D_Envelope
     * @param oom The Order of Magnitude for the precision.
     * @return if this is contained by {@code e}
     */
    public boolean isContainedBy(V3D_Envelope e, int oom) {
        return getXMax(oom).compareTo(e.getXMax(oom)) != 1
                && getXMin(oom).compareTo(e.getXMin(oom)) != -1
                && getYMax(oom).compareTo(e.getYMax(oom)) != 1
                && getYMin(oom).compareTo(e.getYMin(oom)) != -1
                && getZMax(oom).compareTo(e.getZMax(oom)) != 1
                && getZMin(oom).compareTo(e.getZMin(oom)) != -1;
    }

    /**
     * @param p The point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean isIntersectedBy(V3D_Point p, int oom, RoundingMode rm) {
        return isIntersectedBy(p.getX(oom, rm), p.getY(oom, rm), p.getZ(oom, rm), oom);
    }

    /**
     * This biases intersection.
     * 
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean isIntersectedBy(BigRational x, BigRational y, BigRational z, 
            int oom) {
        return x.compareTo(getXMin(oom)) != -1 && x.compareTo(getXMax(oom)) != 1
                && y.compareTo(getYMin(oom)) != -1 && y.compareTo(getYMax(oom)) != 1
                && z.compareTo(getZMin(oom)) != -1 && z.compareTo(getZMax(oom)) != 1;
    }

    /**
     * @param l The line to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this intersects with {@code pl}
     */
    public boolean isIntersectedBy(V3D_Line l, int oom, RoundingMode rm) {
        if (isIntersectedBy(l.getP(), oom, rm)) {
            return true;
        } else {
            if (isIntersectedBy(l.getQ(oom, rm), oom, rm)) {
                return true;
            } else {
                V3D_Plane xMinPlane0 = getXMinPlane(oom);
                V3D_Geometry xMinPlaneIntersection = xMinPlane0.getIntersection(l, oom, rm);
                if (xMinPlaneIntersection != null) {
                    if (xMinPlaneIntersection instanceof V3D_Line) {
                        return true;
                    }
                }
                V3D_Plane xMaxPlane0 = getXMaxPlane(oom);
                V3D_Geometry xMaxPlaneIntersection = xMaxPlane0.getIntersection(l, oom, rm);
                if (xMaxPlaneIntersection != null) {
                    if (xMaxPlaneIntersection instanceof V3D_Line) {
                        return true;
                    }
                }
                V3D_Plane yMinPlane0 = getYMinPlane(oom);
                V3D_Geometry yMinPlaneIntersection = yMinPlane0.getIntersection(l, oom, rm);
                if (yMinPlaneIntersection != null) {
                    if (yMinPlaneIntersection instanceof V3D_Line) {
                        return true;
                    }
                }
                V3D_Plane yMaxPlane0 = getYMaxPlane(oom);
                V3D_Geometry yMaxPlaneIntersection = yMaxPlane0.getIntersection(l, oom, rm);
                if (yMaxPlaneIntersection != null) {
                    if (yMaxPlaneIntersection instanceof V3D_Line) {
                        return true;
                    }
                }
                V3D_Plane zMinPlane0 = getZMinPlane(oom);
                V3D_Geometry zMinPlaneIntersection = zMinPlane0.getIntersection(l, oom, rm);
                if (zMinPlaneIntersection != null) {
                    if (zMinPlaneIntersection instanceof V3D_Line) {
                        return true;
                    }
                }
                V3D_Plane zMaxPlane0 = getZMaxPlane(oom);
                V3D_Geometry zMaxPlaneIntersection = zMaxPlane0.getIntersection(l, oom, rm);
                if (zMaxPlaneIntersection != null) {
                    if (zMaxPlaneIntersection instanceof V3D_Line) {
                        return true;
                    }
                }
                // There are 8 cases.
                if (xMinPlaneIntersection == null) {
                    if (yMinPlaneIntersection == null) {
                        V3D_Point zP = (V3D_Point) zMinPlaneIntersection;
                        return zMinPlane0.isBetweenPlanes(zMaxPlane0, zP, oom, rm);
                    } else {
                        V3D_Point yP = (V3D_Point) yMinPlaneIntersection;
                        if (yMinPlane0.isBetweenPlanes(yMaxPlane0, yP, oom, rm)) {
                            return true;
                        } else {
                            if (zMinPlaneIntersection == null) {
                                return false;
                            } else {
                                V3D_Point zP = (V3D_Point) zMinPlaneIntersection;
                                return zMinPlane0.isBetweenPlanes(zMaxPlane0, zP, oom, rm);
                            }
                        }
                    }
                } else {
                    V3D_Point xP = (V3D_Point) xMinPlaneIntersection;
                    if (xMinPlane0.isBetweenPlanes(xMaxPlane0, xP, oom, rm)) {
                        return true;
                    } else {
                        if (yMinPlaneIntersection == null) {
                            if (zMinPlaneIntersection == null) {
                                return false;
                            } else {
                                V3D_Point zP = (V3D_Point) zMinPlaneIntersection;
                                return zMinPlane0.isBetweenPlanes(zMaxPlane0, zP, oom, rm);
                            }
                        } else {
                            V3D_Point yP = (V3D_Point) yMinPlaneIntersection;
                            if (yMinPlane0.isBetweenPlanes(yMaxPlane0, yP, oom, rm)) {
                                return true;
                            } else {
                                if (zMinPlaneIntersection == null) {
                                    return false;
                                } else {
                                    V3D_Point zP = (V3D_Point) zMinPlaneIntersection;
                                    return zMinPlane0.isBetweenPlanes(zMaxPlane0, zP, oom, rm);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * The normal points away from the envelope.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #xMinPlane} at oom precision.
     */
    public V3D_Plane getXMinPlane(int oom) {
        if (xMinPlane == null || xMinPlane_oom > oom) {
            xMinPlane = new V3D_Plane(getLbf(oom), V3D_Vector.I.reverse());
            return xMinPlane;
        } else {
            return new V3D_Plane(getLbf(oom), V3D_Vector.I.reverse());
        }
    }

    /**
     * The normal points away from the envelope.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #xMaxPlane} at oom precision.
     */
    public V3D_Plane getXMaxPlane(int oom) {
        if (xMaxPlane == null || xMaxPlane_oom > oom) {
            xMaxPlane = new V3D_Plane(getRbf(oom), V3D_Vector.I);
            return xMaxPlane;
        } else {
            return new V3D_Plane(getRbf(oom), V3D_Vector.I);
        }
    }

    /**
     * The normal points away from the envelope.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #yMinPlane} at oom precision.
     */
    public V3D_Plane getYMinPlane(int oom) {
        if (yMinPlane == null || xMinPlane_oom > oom) {
            yMinPlane = new V3D_Plane(getLbf(oom), V3D_Vector.J.reverse());
            return yMinPlane;
        } else {
            return new V3D_Plane(getLbf(oom), V3D_Vector.J.reverse());
        }
    }

    /**
     * The normal points away from the envelope.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #yMaxPlane} at oom precision.
     */
    public V3D_Plane getYMaxPlane(int oom) {
        if (yMaxPlane == null || yMaxPlane_oom > oom) {
            yMaxPlane = new V3D_Plane(getRtf(oom), V3D_Vector.J);
            return yMaxPlane;
        } else {
            return new V3D_Plane(getRtf(oom), V3D_Vector.J);
        }
    }

    /**
     * The normal points away from the envelope.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #zMinPlane} at oom precision.
     */
    public V3D_Plane getZMinPlane(int oom) {
        if (zMinPlane == null || zMinPlane_oom > oom) {
            zMinPlane = new V3D_Plane(getLba(oom), V3D_Vector.K.reverse());
            return zMinPlane;
        } else {
            return new V3D_Plane(getLba(oom), V3D_Vector.K.reverse());
        }
    }

    /**
     * The normal points away from the envelope.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #zMaxPlane} at oom precision.
     */
    public V3D_Plane getZMaxPlane(int oom) {
        if (zMaxPlane == null || zMaxPlane_oom > oom) {
            zMaxPlane = new V3D_Plane(getRbf(oom), V3D_Vector.K);
            return zMaxPlane;
        } else {
            return new V3D_Plane(getRbf(oom), V3D_Vector.K);
        }
    }

    /**
     * @param en The envelope to intersect.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code null} if there is no intersection; {@code en} if
     * {@code this.equals(en)}; otherwise returns the intersection.
     */
    public V3D_Envelope getIntersection(V3D_Envelope en, int oom) {
        if (this.equals(en, oom)) {
            return en;
        }
        if (!this.isIntersectedBy(en, oom)) {
            return null;
        }
        return new V3D_Envelope(oom,
                BigRational.max(getXMin(oom), en.getXMin(oom)),
                BigRational.min(getXMax(oom), en.getXMax(oom)),
                BigRational.max(getYMin(oom), en.getYMin(oom)),
                BigRational.min(getYMax(oom), en.getYMax(oom)),
                BigRational.max(getZMin(oom), en.getZMin(oom)),
                BigRational.min(getZMax(oom), en.getZMax(oom)));
    }

    /**
     * Test for equality.
     *
     * @param e The V3D_Envelope to test for equality with this.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff this and e are equal.
     */
    public boolean equals(V3D_Envelope e, int oom) {
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
     * @param oom The Order of Magnitude for the precision
     * @return {@link #lbf} to oom precision.
     */
    public V3D_Point getLbf(int oom) {
        if (lbf == null || lbf_oom > oom) {
            lbf_oom = oom;
            lbf = new V3D_Point(getXMin(oom), getYMin(oom), getZMin(oom));
            return lbf;
        } else {
            return new V3D_Point(getXMin(oom), getYMin(oom), getZMin(oom));
        }
    }

    /**
     * @param oom The Order of Magnitude for the precision
     * @return {@link #ltf} to oom precision.
     */
    public V3D_Point getLtf(int oom) {
        if (ltf == null || ltf_oom > oom) {
            ltf_oom = oom;
            ltf = new V3D_Point(getXMin(oom), getYMax(oom), getZMin(oom));
            return ltf;
        } else {
            return new V3D_Point(getXMin(oom), getYMax(oom), getZMin(oom));
        }
    }

    /**
     * @param oom The Order of Magnitude for the precision
     * @return {@link #rtf} to oom precision.
     */
    public V3D_Point getRtf(int oom) {
        if (rtf == null || rtf_oom > oom) {
            rtf_oom = oom;
            rtf = new V3D_Point(getXMax(oom), getYMax(oom), getZMin(oom));
            return rtf;
        } else {
            return new V3D_Point(getXMax(oom), getYMax(oom), getZMin(oom));
        }
    }

    /**
     * @param oom The Order of Magnitude for the precision
     * @return {@link #rbf} to oom precision.
     */
    public V3D_Point getRbf(int oom) {
        if (rbf == null || rbf_oom > oom) {
            rbf_oom = oom;
            rbf = new V3D_Point(getXMax(oom), getYMin(oom), getZMin(oom));
            return rbf;
        } else {
            return new V3D_Point(getXMax(oom), getYMin(oom), getZMin(oom));
        }
    }

    /**
     * @param oom The Order of Magnitude for the precision
     * @return {@link #lba} to oom precision.
     */
    public V3D_Point getLba(int oom) {
        if (lba == null || lba_oom > oom) {
            lba_oom = oom;
            lba = new V3D_Point(getXMin(oom), getYMin(oom), getZMax(oom));
            return lba;
        } else {
            return new V3D_Point(getXMin(oom), getYMin(oom), getZMax(oom));
        }
    }

    /**
     * @param oom The Order of Magnitude for the precision
     * @return {@link #lta} to oom precision.
     */
    public V3D_Point getLta(int oom) {
        if (lta == null || lta_oom > oom) {
            lta_oom = oom;
            lta = new V3D_Point(getXMin(oom), getYMax(oom), getZMax(oom));
            return lta;
        } else {
            return new V3D_Point(getXMin(oom), getYMax(oom), getZMax(oom));
        }
    }

    /**
     * @param oom The Order of Magnitude for the precision
     * @return {@link #rta} to oom precision.
     */
    public V3D_Point getRta(int oom) {
        if (rta == null || rta_oom > oom) {
            rta_oom = oom;
            rta = new V3D_Point(getXMax(oom), getYMax(oom), getZMax(oom));
            return rta;
        } else {
            return new V3D_Point(getXMax(oom), getYMax(oom), getZMax(oom));
        }
    }

    /**
     * @param oom The Order of Magnitude for the precision
     * @return {@link #rba} to oom precision.
     */
    public V3D_Point getRba(int oom) {
        if (rba == null || rba_oom > oom) {
            rba_oom = oom;
            rba = new V3D_Point(getXMax(oom), getYMin(oom), getZMax(oom));
            return rba;
        } else {
            return new V3D_Point(getXMax(oom), getYMin(oom), getZMax(oom));
        }
    }

    /**
     * Return an array containing lbf, lba, ltf, lta, rbf, rba, rtf, rta with
     * oom precision.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return The corners of this as points.
     */
    public V3D_Point[] getPoints(int oom) {
        if (pts == null || pts_oom > oom) {
            pts = new V3D_Point[8];
            pts[0] = getLbf(oom);
            pts[1] = getLba(oom);
            pts[2] = getLtf(oom);
            pts[3] = getLta(oom);
            pts[4] = getRbf(oom);
            pts[5] = getRba(oom);
            pts[6] = getRtf(oom);
            pts[7] = getRta(oom);
            pts_oom = oom;
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
        pts = getPoints(oom);
        V3D_Point c = getCentroid(oom, rm);
        V3D_Vector cv = new V3D_Vector(pt, c, oom, rm);
        V3D_Vector v2 = cv.getCrossProduct(v, oom, rm);
        /**
         * Calculate the plane touching this in the direction given from pt to
         * c.
         */
        // Find a closest point (there could be up to 4).
        BigRational d2min = pt.getDistanceSquared(pts[0], oom, rm);
        V3D_Point closest = pts[0];
        for (int i = 1; i < pts.length; i++) {
            BigRational d2 = pt.getDistanceSquared(pts[i], oom, rm);
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
        BigRational aa = BigRational.ZERO;
        V3D_Plane tpl = null;
        //V3D_Point pb = new V3D_Point(v2pl.getP());
        //pb.translate(v2.reverse(), oom, rm);
        BigRational ba = BigRational.ZERO;
        V3D_Plane bpl = null;
        for (var x : ipts) {
            V3D_Point pp = vpl.getPointOfProjectedIntersection(x, oom, rm);
            //BigRational a = v2.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            BigRational a = cv.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
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
        BigRational la = BigRational.ZERO;
        V3D_Plane lpl = null;
        //V3D_Point pr = new V3D_Point(vpl.getP());
        //pr.translate(v, oom, rm);
        BigRational ra = BigRational.ZERO;
        V3D_Plane rpl = null;
        for (var x : ipts) {
            V3D_Point pp = v2pl.getPointOfProjectedIntersection(x, oom, rm);
            //BigRational a = v.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            BigRational a = cv.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
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
        //int oomn2 = oom - 2;
        int oomn4 = oom - 4;
        V3D_Rectangle r;
        // Get the plane of the viewport.
        V3D_Point c = getCentroid(oomn4, rm);
        BigRational distance = c.getDistance(getPoints(oom)[0], oomn4, rm);
        V3D_Point plpt = new V3D_Point(c);
        V3D_Vector vo = new V3D_Vector(c, pt, oomn4, rm).getUnitVector(oomn4, rm);
        plpt.translate(vo.multiply(distance, oomn4, rm), oomn4, rm);
        V3D_Vector cv = vo.reverse();
        V3D_Plane pl0 = new V3D_Plane(plpt, cv);
        // Figure out the extremes in relation to v (and v2).
        V3D_Vector v2 = cv.getCrossProduct(v, oomn4, rm);
        // Intersect the rays from pts to each point with the screen.
        pts = getPoints(oomn4);
        V3D_Point[] ipts = new V3D_Point[pts.length];
        // Get the intersecting points on the screen plane from pt
        for (int i = 0; i < pts.length; i++) {
            V3D_Ray ray = new V3D_Ray(pt, pts[i], oomn4, rm);
            ipts[i] = (V3D_Point) ray.getIntersection(pl0, oomn4, rm);
        }
        // Find top, bottom, left and right planes
        V3D_Plane vpl = new V3D_Plane(c, v);
        V3D_Plane v2pl = new V3D_Plane(c, v2);
        // Find top and bottom
        V3D_Point ap = new V3D_Point(c);
        ap.translate(v2, oomn4, rm);
        BigRational aa = BigRational.ZERO;
        V3D_Plane tpl = null;
        //V3D_Point pb = new V3D_Point(v2pl.getP());
        //pb.translate(v2.reverse(), oom, rm);
        BigRational ba = BigRational.ZERO;
        V3D_Plane bpl = null;
        for (var x : ipts) {
            V3D_Point pp = vpl.getPointOfProjectedIntersection(x, oomn4, rm);
            //BigRational a = v2.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            BigRational a = cv.getAngle(new V3D_Vector(pt, pp, oomn4, rm), oomn4, rm).abs();
            if (v2pl.isOnSameSide(ap, x, oomn4, rm)) {
                if (a.compareTo(aa) == 1) {
                    aa = a;
                    //System.out.println(a);
                    V3D_Point xv = new V3D_Point(x);
                    xv.translate(v, oomn4, rm);
                    tpl = new V3D_Plane(x, pt, xv, oomn4, rm);
                }
            } else {
                if (a.compareTo(ba) == 1) {
                    ba = a;
                    V3D_Point xv = new V3D_Point(x);
                    xv.translate(v, oomn4, rm);
                    bpl = new V3D_Plane(x, pt, xv, oomn4, rm);
                }
            }
        }
        // Find left and right
        V3D_Point lp = new V3D_Point(c);
        lp.translate(v.reverse(), oomn4, rm);
        BigRational la = BigRational.ZERO;
        V3D_Plane lpl = null;
        //V3D_Point pr = new V3D_Point(vpl.getP());
        //pr.translate(v, oom, rm);
        BigRational ra = BigRational.ZERO;
        V3D_Plane rpl = null;
        for (var x : ipts) {
            V3D_Point pp = v2pl.getPointOfProjectedIntersection(x, oomn4, rm);
            //BigRational a = v.getAngle(new V3D_Vector(pt, pp, oom, rm), oom, rm).abs();
            BigRational a = cv.getAngle(new V3D_Vector(pt, pp, oomn4, rm), oomn4, rm).abs();
            if (vpl.isOnSameSide(lp, x, oomn4, rm)) {
                if (a.compareTo(la) == 1) {
                    la = a;
                    V3D_Point xv = new V3D_Point(x);
                    xv.translate(v2, oomn4, rm);
                    lpl = new V3D_Plane(x, pt, xv, oomn4, rm);
                }
            } else {
                if (a.compareTo(ra) == 1) {
                    ra = a;
                    V3D_Point xv = new V3D_Point(x);
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

        r = new V3D_Rectangle(
                (V3D_Point) lpl.getIntersection(pl0, bpl, oomn4, rm),
                (V3D_Point) lpl.getIntersection(pl0, tpl, oomn4, rm),
                (V3D_Point) rpl.getIntersection(pl0, tpl, oomn4, rm),
                (V3D_Point) rpl.getIntersection(pl0, bpl, oomn4, rm), oom, rm);

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
        //int oomn2 = oom - 2;
        int oomn4 = oom - 4;
        V3D_Rectangle r;
        // Get the plane of the viewport.
        V3D_Point c = getCentroid(oomn4, rm);
        BigRational d = c.getDistance(getPoints(oom)[0], oomn4, rm);
        BigRational dby2 = d.divide(2);
        V3D_Point plpt = new V3D_Point(c);
        V3D_Vector cpt = new V3D_Vector(c, pt, oomn4, rm);
        V3D_Vector vo = cpt.getUnitVector(oomn4, rm);
        plpt.translate(vo.multiply(d, oomn4, rm), oomn4, rm);
        V3D_Plane pl0 = new V3D_Plane(plpt, cpt);
        V3D_Vector v2 = cpt.getCrossProduct(v, oomn4, rm);
        // Find top, bottom, left and right planes
        V3D_Point ptv = new V3D_Point(pt);
        ptv.translate(v, oomn4, rm);
        V3D_Point ptv2 = new V3D_Point(pt);
        ptv2.translate(v2, oomn4, rm);
        // tp
        V3D_Vector vv = v2.getUnitVector(oomn4, rm).multiply(dby2, oomn4, rm);
        V3D_Point tppt = new V3D_Point(plpt);
        tppt.translate(vv, oomn4, rm);
        V3D_Plane tpl = new V3D_Plane(tppt, pt, ptv, oomn4, rm);
        // bp
        V3D_Point bppt = new V3D_Point(plpt);
        bppt.translate(vv.reverse(), oomn4, rm);
        V3D_Plane bpl = new V3D_Plane(bppt, pt, ptv, oomn4, rm);
        // lp
        V3D_Vector hv = v.getUnitVector(oomn4, rm).multiply(dby2, oomn4, rm);
        V3D_Point lppt = new V3D_Point(plpt);
        lppt.translate(hv.reverse(), oomn4, rm);
        V3D_Plane lpl = new V3D_Plane(lppt, pt, ptv2, oomn4, rm);
        // rp
        V3D_Point rppt = new V3D_Point(plpt);
        rppt.translate(hv, oomn4, rm);
        V3D_Plane rpl = new V3D_Plane(rppt, pt, ptv2, oomn4, rm);
        r = new V3D_Rectangle(
                (V3D_Point) lpl.getIntersection(pl0, bpl, oomn4, rm),
                (V3D_Point) lpl.getIntersection(pl0, tpl, oomn4, rm),
                (V3D_Point) rpl.getIntersection(pl0, tpl, oomn4, rm),
                (V3D_Point) rpl.getIntersection(pl0, bpl, oomn4, rm), oom, rm);
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
