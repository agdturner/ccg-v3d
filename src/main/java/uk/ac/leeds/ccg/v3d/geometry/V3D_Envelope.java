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
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * An envelope contains all the extreme values with respect to the X, Y and Z
 * axes. Some call this an Axis Aligned Bounding Box (AABB). In this
 * implementation, it may have length of zero in any direction. For a point the
 * envelope is essentially the point. The envelope may also be a line. In any
 * case it has:
 * <ul>
 * <li>a top ({@link #t}) aligned with {@link #yMax}</li>
 * <li>a bottom ({@link #b}) aligned with {@link #yMin}</li>
 * <li>a left ({@link #l}) aligned with {@link #xMin}</li>
 * <li>a right ({@link #r}) aligned with {@link #xMax}</li>
 * <li>a fore ({@link #f}) aligned with {@link #zMin}</li>
 * <li>a aft ({@link #a}) aligned with {@link #zMax}</li>
 * </ul>
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
 *                                    |
 *                                                  a
 *                                    t
 *                      lta_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ rta
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
 *  x - --- l  |           |                     |           |  r  --- + x
 *             |           |                     |           |
 *             |        lba|_ _ _ _ _ _ _ _ _ _ _|_ _ _ _ _ _|rba
 *             |           /                     |           /
 *             |          /                      |          /
 *             |         /                       |         /
 *             |        /                        |        /
 *             |       /                         |       /
 *             |      /                          |      /
 *             |     /                           |     /
 *             |    /                            |    /
 *             |   /                             |   /
 *             |  /                              |  /
 *             | /                               | /
 *          lbf|/_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ |/rbf
 *
 *                                     b
 *                      f
 *                                     |
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
public class V3D_Envelope extends V3D_Geometry implements
        V3D_FiniteGeometryInterface, V3D_IntersectionAndDistanceCalculations {

    private static final long serialVersionUID = 1L;

    /**
     * @param e The V3D_Environment.
     * @param en The Envelope.
     * @param points The points to test if they are collinear.
     * @return {@code true} iff all the points are collinear or coincident.
     */
    private static boolean isCollinear0(int oom, 
            RoundingMode rm, V3D_Envelope en, V3D_Envelope.Point... points) {
        // Get a line
        V3D_Envelope.Line l = getLine(en, points);
        return V3D_Line.isCollinear(oom, rm, l, points);
    }

    /**
     * @param e The V3D_Environment.
     * @param en The Envelope.
     * @param points The points to test if they are collinear.
     * @return {@code false} if all points are coincident. {@code true} iff all
     * the points are collinear.
     */
    public static boolean isCollinear(int oom, RoundingMode rm, 
            V3D_Envelope en, V3D_Envelope.Point... points) {
        // For the points to be in a line at least two must be different.
        if (V3D_Point.isCoincident(points)) {
            return false;
        }
        return isCollinear0(oom, rm, en, points);
    }

    /**
     * There should be at least two different points.
     *
     * @param en The Envelope.
     * @param points Any number of points, but with two being different.
     * @return A line defined by any two different points or null if the points
     * are coincident.
     */
    public static V3D_Envelope.Line getLine(V3D_Envelope en, V3D_Envelope.Point... points) {
        V3D_Envelope.Point p0 = points[0];
        for (V3D_Envelope.Point p1 : points) {
            if (!p1.equals(p0)) {
                return en.new Line(p0, p1);
            }
        }
        return null;
    }

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
     * The top face.
     */
    protected final Geometry t;

    /**
     * The left face.
     */
    protected final Geometry l;

    /**
     * The aft face.
     */
    protected final Geometry a;

    /**
     * The right face.
     */
    protected final Geometry r;

    /**
     * The fore face.
     */
    protected final Geometry f;

    /**
     * The bottom face.
     */
    protected final Geometry b;

    /**
     * For storing the left top fore point.
     */
    protected final Point ltf;

    /**
     * For storing the left top aft point.
     */
    protected Point lta;

    /**
     * For storing the right top aft point.
     */
    protected Point rta;

    /**
     * For storing the right top fore point.
     */
    protected Point rtf;

    /**
     * For storing the left bottom fore point.
     */
    protected Point lbf;

    /**
     * For storing the left bottom aft point.
     */
    protected Point lba;

    /**
     * For storing the right bottom aft point.
     */
    protected Point rba;

    /**
     * For storing the right bottom fore point.
     */
    protected Point rbf;

    /**
     * <table>
     * <caption>Types</caption>
     * <thead>
     * <tr><td>type</td><td>description</td></tr>
     * </thead>
     * <tbody>
     * <tr><td>0</td><td>f, l, a, r, t, b Point</td></tr>
     * <tr><td>1</td><td>f, a Point; l, r, t, b LineSegment</td></tr>
     * <tr><td>2</td><td>1, r Point; f, a, t, b LineSegment</td></tr>
     * <tr><td>3</td><td>t, b Point; f, l, a, r LineSegment</td></tr>
     * <tr><td>4</td><td>f, a Rectangle; l, r, t, b LineSegment</td></tr>
     * <tr><td>5</td><td>1, r Rectangle; f, a, t, b LineSegment</td></tr>
     * <tr><td>6</td><td>t, b Rectangle; f, l, a, r LineSegment</td></tr>
     * <tr><td>7</td><td>f, l, a, r, t, b Rectangle</td></tr>
     * </tbody>
     * </table>
     */
    protected final int type;

    /**
     * Create a new instance.
     *
     * @param e What {@link #e} is set to.
     * @param points The points used to form the envelop.
     */
    public V3D_Envelope(V3D_Environment e, int oom, RoundingMode rm, V3D_Point... points) {
        super(e, points[0].offset);
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
                ltf = lbf = rbf = rtf = lta = lba = rba = rta = new Point(
                        points[0], oom, rm);
                f = l = a = r = t = b = ltf;
                type = 0; // f, l, a, r, t, b Point
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
                if (xmin.compareTo(xmax) == 0) {
                    if (ymin.compareTo(ymax) == 0) {
                        if (zmin.compareTo(zmax) == 0) {
                            ltf = lbf = rbf = rtf = lta = lba = rba = rta
                                    = new Point(this.e, xmin, ymin, zmin);
                            f = l = a = r = t = b = ltf;
                            type = 0; // f, l, a, r, t, b Point
                        } else {
                            ltf = lbf = rbf = rtf = new Point(this.e, xmin, ymin, zmin);
                            lta = lba = rba = rta = new Point(this.e, xmin, ymax, zmin);
                            f = ltf;
                            l = new LineSegment(lta, ltf);
                            a = lta;
                            r = new LineSegment(ltf, lta);
                            t = r;
                            b = l;
                            type = 1; // f, a Point; l, r, t, b LineSegment
                        }
                    } else {
                        lbf = rbf = new Point(this.e, xmin, ymin, zmin);
                        ltf = rtf = new Point(this.e, xmin, ymax, zmin);
                        if (zmin.compareTo(zmax) == 0) {
                            f = new LineSegment(lbf, ltf);
                            l = f;
                            a = f;
                            r = f;
                            t = ltf;
                            b = lbf;
                            type = 3; // t, b Point; f, l, a, r LineSegment
                        } else {
                            lta = rta = new Point(this.e, xmin, ymax, zmax);
                            lba = rba = new Point(this.e, xmin, ymin, zmax);
                            f = new LineSegment(lbf, ltf);
                            l = new Rectangle(lba, lta, ltf, lbf, oom, rm);
                            a = new LineSegment(lba, lta);
                            r = new Rectangle(lbf, ltf, lta, lba, oom, rm);
                            t = new LineSegment(ltf, lta);
                            b = new LineSegment(lba, lbf);
                            type = 5; // 1, r Rectangle; f, a, t, b LineSegment
                        }
                    }
                } else {
                    if (ymin.compareTo(ymax) == 0) {
                        ltf = lbf = new Point(this.e, xmin, ymin, zmin);
                        rtf = rbf = new Point(this.e, xmax, ymin, zmin);
                        if (zmin.compareTo(zmax) == 0) {
                            f = new LineSegment(ltf, rtf);
                            l = ltf;
                            a = new LineSegment(rtf, ltf);
                            r = rtf;
                            t = f;
                            b = f;
                            type = 2; // 1, r Point; f, a, t, b LineSegment
                        } else {
                            lta = lba = new Point(this.e, xmin, ymin, zmax);
                            rta = rba = new Point(this.e, xmax, ymin, zmax);
                            f = new LineSegment(ltf, rtf);
                            l = new LineSegment(lta, ltf);
                            a = new LineSegment(rta, lta);
                            r = new LineSegment(rtf, rta);
                            t = new Rectangle(ltf, lta, rta, rtf, oom, rm);
                            b = new Rectangle(lta, ltf, rtf, rta, oom, rm);
                            type = 6; // t, b Rectangle; f, l, a, r LineSegment
                        }
                    } else {
                        lbf = new Point(this.e, xmin, ymin, zmin);
                        ltf = new Point(this.e, xmin, ymax, zmin);
                        rtf = new Point(this.e, xmax, ymax, zmin);
                        rbf = new Point(this.e, xmax, ymin, zmin);
                        if (zmin.compareTo(zmax) == 0) {
                            f = new Rectangle(lbf, ltf, rtf, rbf, oom, rm);
                            l = new LineSegment(lbf, ltf);
                            a = new Rectangle(rbf, rtf, ltf, lbf, oom, rm);
                            r = new LineSegment(rbf, rtf);
                            t = new LineSegment(ltf, rtf);
                            b = new LineSegment(lbf, rbf);
                            type = 4; // f, a Rectangle; l, r, t, b LineSegment
                        } else {
                            lba = new Point(this.e, xmin, ymin, zmax);
                            lta = new Point(this.e, xmin, ymax, zmax);
                            rta = new Point(this.e, xmax, ymax, zmax);
                            rba = new Point(this.e, xmax, ymin, zmax);
                            f = new Rectangle(lbf, ltf, rtf, rbf, oom, rm);
                            l = new Rectangle(lba, lta, ltf, lbf, oom, rm);
                            a = new Rectangle(rba, rta, lta, lba, oom, rm);
                            r = new Rectangle(rbf, rtf, rta, rba, oom, rm);
                            t = new Rectangle(ltf, lta, rta, rtf, oom, rm);
                            b = new Rectangle(lba, lbf, rbf, rba, oom, rm);
                            type = 7; // f, l, a, r, t, b Rectangle
                        }
                    }
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
     * @param e What {@link #e} is set to.
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     * @param z The z-coordinate of a point.
     */
    public V3D_Envelope(V3D_Environment e, int oom, RoundingMode rm, 
            Math_BigRational x, Math_BigRational y, Math_BigRational z) {
        this(e, oom, rm, new V3D_Point(e, x, y, z));
    }

    /**
     * Create a new instance.
     *
     * @param e What {@link #e} is set to.
     * @param xMin What {@link xMin} is set to.
     * @param xMax What {@link xMax} is set to.
     * @param yMin What {@link yMin} is set to.
     * @param yMax What {@link yMax} is set to.
     * @param zMin What {@link zMin} is set to.
     * @param zMax What {@link zMax} is set to.
     */
    public V3D_Envelope(V3D_Environment e, int oom, RoundingMode rm,
            Math_BigRational xMin, Math_BigRational xMax,
            Math_BigRational yMin, Math_BigRational yMax,
            Math_BigRational zMin, Math_BigRational zMax) {
        this(e, oom, rm, new V3D_Point(e, xMin, yMin, zMin),
                new V3D_Point(e, xMax, yMax, zMax));
    }

    /**
     * Create a new instance.
     *
     * @param offset What {@link #offset} is set to.
     * @param en An envelope to base this on.
     */
    public V3D_Envelope(V3D_Vector offset, V3D_Envelope en, int oom, 
            RoundingMode rm) {
        this(en.e, oom, rm, 
                en.xMin.subtract(en.offset.getDX(oom, rm))
                        .add(offset.getDX(oom, rm)),
                en.xMax.subtract(en.offset.getDX(oom, rm))
                        .add(offset.getDX(oom, rm)),
                en.yMin.subtract(en.offset.getDY(oom, rm))
                        .add(offset.getDY(oom, rm)),
                en.yMax.subtract(en.offset.getDY(oom, rm))
                        .add(offset.getDY(oom, rm)),
                en.zMin.subtract(en.offset.getDZ(oom, rm))
                        .add(offset.getDZ(oom, rm)),
                en.zMax.subtract(en.offset.getDZ(oom, rm))
                        .add(offset.getDZ(oom, rm)));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "(xMin=" + xMin + ", xMax=" + xMax
                + ", yMin=" + yMin + ", yMax=" + yMax
                + ", zMin=" + zMin + ", zMax=" + zMax + ")";
    }

    /**
     * @param e The V3D_Envelope to union with this.
     * @return an Envelope which is {@code this} union {@code e}.
     */
    public V3D_Envelope union(V3D_Envelope e, int oom, RoundingMode rm) {
        if (e.isContainedBy(this, oom, rm)) {
            return this;
        } else {
            return new V3D_Envelope(this.e, oom, rm, 
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
     * @return {@code true} if this intersects with {@code e}.
     */
    public boolean isIntersectedBy(V3D_Envelope e, int oom, RoundingMode rm) {
        if (e.getXMax(oom, rm).compareTo(getXMin(oom, rm)) != -1
                && e.getXMin(oom, rm).compareTo(getXMax(oom, rm)) != 1
                && getXMax(oom, rm).compareTo(e.getXMin(oom, rm)) != -1
                && getXMin(oom, rm).compareTo(e.getXMax(oom, rm)) != 1) {
            if (e.getYMax(oom, rm).compareTo(getYMin(oom, rm)) != -1
                    && e.getYMin(oom, rm).compareTo(getYMax(oom, rm)) != 1
                    && getYMax(oom, rm).compareTo(e.getYMin(oom, rm)) != -1
                    && getYMin(oom, rm).compareTo(e.getYMax(oom, rm)) != 1) {
                if (e.getZMax(oom, rm).compareTo(getZMin(oom, rm)) != -1
                        && e.getZMin(oom, rm).compareTo(getZMax(oom, rm)) != 1
                        && getZMax(oom, rm).compareTo(e.getZMin(oom, rm)) != -1
                        && getZMin(oom, rm).compareTo(e.getZMax(oom, rm)) != 1) {
                    return true;
                }
            }
        } else if (getXMax(oom, rm).compareTo(e.getXMin(oom, rm)) != -1
                && getXMin(oom, rm).compareTo(e.getXMax(oom, rm)) != 1
                && e.getXMax(oom, rm).compareTo(getXMin(oom, rm)) != -1
                && e.getXMin(oom, rm).compareTo(getXMax(oom, rm)) != 1) {
            if (getYMax(oom, rm).compareTo(e.getYMin(oom, rm)) != -1
                    && getYMin(oom, rm).compareTo(e.getYMax(oom, rm)) != 1
                    && e.getYMax(oom, rm).compareTo(getYMin(oom, rm)) != -1
                    && e.getYMin(oom, rm).compareTo(getYMax(oom, rm)) != 1) {
                if (getZMax(oom, rm).compareTo(e.getZMin(oom, rm)) != -1
                        && getZMin(oom, rm).compareTo(e.getZMax(oom, rm)) != 1
                        && e.getZMax(oom, rm).compareTo(getZMin(oom, rm)) != -1
                        && e.getZMin(oom, rm).compareTo(getZMax(oom, rm)) != 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Containment includes the boundary. So anything in or on the boundary is
     * contained.
     *
     * @param e V3D_Envelope
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
     * @return {@code true} if this intersects with {@code p}
     */
    @Override
    public boolean isIntersectedBy(V3D_Point p, int oom, RoundingMode rm) {
        return isIntersectedBy(p.getX(oom, rm), p.getY(oom, rm), p.getZ(oom, rm), oom, rm);
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @return {@code true} if this intersects with {@code p}
     */
    public boolean isIntersectedBy(Math_BigRational x, Math_BigRational y,
            Math_BigRational z, int oom, RoundingMode rm) {
        return x.compareTo(getXMin(oom, rm)) != -1 && x.compareTo(getXMax(oom, rm)) != 1
                && y.compareTo(getYMin(oom, rm)) != -1 && y.compareTo(getYMax(oom, rm)) != 1
                && z.compareTo(getZMin(oom, rm)) != -1 && z.compareTo(getZMax(oom, rm)) != 1;
    }

    /**
     * @param en The envelop to intersect.
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
        return new V3D_Envelope(this.e, oom, rm,
                getXMin(oom, rm).max(en.getXMin(oom, rm)),
                getXMax(oom, rm).min(en.getXMax(oom, rm)),
                getYMin(oom, rm).max(en.getYMin(oom, rm)),
                getYMax(oom, rm).min(en.getYMax(oom, rm)),
                getZMin(oom, rm).max(en.getZMin(oom, rm)),
                getZMax(oom, rm).min(en.getZMax(oom, rm)));
    }

    /**
     * Returns {@code null} if {@code this} does not intersect {@code li};
     * otherwise returns the intersection which is either a point or a line
     * segment.
     *
     * @param li The line to intersect.
     * @return {@code null} if there is no intersection; otherwise returns the
     * intersection.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line li, int oom, RoundingMode rm) {
        switch (type) {
            case 0 -> {
                V3D_Point fp = new V3D_Point((Point) f);
                if (li.isIntersectedBy(fp, oom, rm)) {
                    return fp;
                } else {
                    return null;
                }
            }
            case 1 -> {
                return new V3D_LineSegment((LineSegment) l).getIntersection(li, oom, rm);
            }
            case 2 -> {
                return new V3D_LineSegment((LineSegment) f).getIntersection(li, oom, rm);
            }
            case 3 -> {
                return new V3D_LineSegment((LineSegment) f).getIntersection(li, oom, rm);
            }
            case 4 -> {
                return new V3D_Rectangle((Rectangle) f, oom, rm).getIntersection(li, oom, rm);
            }
            case 5 -> {
                return new V3D_Rectangle((Rectangle) l, oom, rm).getIntersection(li, oom, rm);
            }
            case 6 -> {
                return new V3D_Rectangle((Rectangle) t, oom, rm).getIntersection(li, oom, rm);
            }
            default -> {
                V3D_Geometry fil = (new V3D_Rectangle((Rectangle) f, oom, rm))
                        .getIntersection(li, oom, rm);
                V3D_Geometry lil = (new V3D_Rectangle((Rectangle) l, oom, rm))
                        .getIntersection(li, oom, rm);
                V3D_Geometry ail = (new V3D_Rectangle((Rectangle) a, oom, rm))
                        .getIntersection(li, oom, rm);
                if (fil == null) {
                    if (lil == null) {
                        V3D_Geometry ril = (new V3D_Rectangle((Rectangle) r, oom, rm))
                                .getIntersection(li, oom, rm);
                        if (ail == null) {
                            V3D_Geometry til = (new V3D_Rectangle(
                                    (Rectangle) t, oom, rm)).getIntersection(li, oom, rm);
                            if (ril == null) {
                                if (til == null) {
                                    return null;
                                } else {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom, rm))
                                            .getIntersection(li, oom, rm);
//                                    return new V3D_LineSegment(
//                                            (V3D_Point) til,
//                                            (V3D_Point) bil,
//                                            oom, rm);
                                    return new V3D_LineSegment(e,
                                            ((V3D_Point) til).getVector(oom, rm),
                                            ((V3D_Point) bil).getVector(oom, rm));
                                }
                            } else {
                                if (til == null) {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom, rm))
                                            .getIntersection(li, oom, rm);
                                    if (bil == null) {
                                        return null;
                                    } else {
                                        return V3D_LineSegment.getGeometry(
                                                (V3D_Point) ril,
                                                (V3D_Point) bil, oom, rm);
                                    }
                                } else {
                                    return V3D_LineSegment.getGeometry(
                                            (V3D_Point) ril,
                                            (V3D_Point) til, oom, rm);
                                }
                            }
                        } else {
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom, rm)).getIntersection(li, oom, rm);
                                if (til == null) {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom, rm))
                                            .getIntersection(li, oom, rm);
                                    if (bil == null) {
                                        return null;
                                    } else {
                                        return V3D_LineSegment.getGeometry(
                                                (V3D_Point) ail,
                                                (V3D_Point) bil, oom, rm);
                                    }
                                } else {
                                    return V3D_LineSegment.getGeometry(
                                            (V3D_Point) ail, (V3D_Point) til, oom, rm);
                                }
                            } else {
                                if (ail instanceof V3D_LineSegment) {
                                    return ail;
                                }
                                return V3D_LineSegment.getGeometry(
                                        (V3D_Point) ail, (V3D_Point) ril, oom, rm);
                            }
                        }
                    } else {
                        if (ail == null) {
                            V3D_Geometry ril = (new V3D_Rectangle(
                                    (Rectangle) r, oom, rm)).getIntersection(li, oom, rm);
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom, rm)).getIntersection(li, oom, rm);
                                V3D_Geometry bil = (new V3D_Rectangle(
                                        (Rectangle) b, oom, rm)).getIntersection(li, oom, rm);
                                if (til == null) {
                                    if (bil == null) {
                                        return null;
                                    } else {
                                        return V3D_LineSegment.getGeometry(
                                                (V3D_Point) lil,
                                                (V3D_Point) bil, oom, rm);
                                    }
                                } else {
                                    if (bil == null) {
                                        return null;
                                    } else {
                                        return V3D_LineSegment.getGeometry(
                                                (V3D_Point) lil,
                                                (V3D_Point) bil, oom, rm);
                                    }
                                }
                            } else {
//                                return new V3D_LineSegment(
//                                        (V3D_Point) lil,
//                                        (V3D_Point) ril,
//                                        oom, rm);
                                return new V3D_LineSegment(e,
                                        ((V3D_Point) lil).getVector(oom, rm),
                                        ((V3D_Point) ril).getVector(oom, rm));
                            }
                        } else {
                            return V3D_LineSegment.getGeometry(
                                    (V3D_Point) lil, (V3D_Point) ail, oom, rm);
                        }
                    }
                } else {
                    if (lil == null) {
                        V3D_Geometry ril = (new V3D_Rectangle((Rectangle) r, oom, rm))
                                .getIntersection(li, oom, rm);
                        if (ail == null) {
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom, rm)).getIntersection(li, oom, rm);
                                if (til == null) {
                                    return null;
                                } else {
                                    return V3D_LineSegment.getGeometry(
                                            (V3D_Point) fil, (V3D_Point) til, oom, rm);
                                }
                            } else {
                                return V3D_LineSegment.getGeometry(
                                        (V3D_Point) fil, (V3D_Point) ril, oom, rm);
                            }
                        } else {
//                            return new V3D_LineSegment(
//                                    (V3D_Point) fil,
//                                    (V3D_Point) ail,
//                                    oom, rm);
                            return new V3D_LineSegment(e,
                                    ((V3D_Point) fil).getVector(oom, rm),
                                    ((V3D_Point) ail).getVector(oom, rm));
                        }
                    } else {
                        if (ail == null) {
                            return V3D_LineSegment.getGeometry(
                                    (V3D_Point) fil, (V3D_Point) lil, oom, rm);
                        } else {
                            return V3D_LineSegment.getGeometry(
                                    (V3D_Point) fil, (V3D_Point) ail, oom, rm);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns {@code null} if {@code this} does not intersect {@code r};
     * otherwise returns the intersection which is either a point or a line
     * segment.
     *
     * @param ray The ray to intersect.
     * @return {@code null} if there is no intersection; otherwise returns the
     * intersection.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Ray ray, int oom, RoundingMode rm) {
        switch (type) {
            case 0 -> {
                V3D_Point fp = new V3D_Point((Point) f);
                if (ray.isIntersectedBy(fp, oom, rm)) {
                    return fp;
                } else {
                    return null;
                }
            }
            case 1 -> {
                return new V3D_LineSegment((LineSegment) l).getIntersection(ray, oom, rm);
            }
            case 2 -> {
                return new V3D_LineSegment((LineSegment) f).getIntersection(ray, oom, rm);
            }
            case 3 -> {
                return new V3D_LineSegment((LineSegment) f).getIntersection(ray, oom, rm);
            }
            case 4 -> {
                return new V3D_Rectangle((Rectangle) f, oom, rm).getIntersection(ray, oom, rm);
            }
            case 5 -> {
                return new V3D_Rectangle((Rectangle) l, oom, rm).getIntersection(ray, oom, rm);
            }
            case 6 -> {
                return new V3D_Rectangle((Rectangle) t, oom, rm).getIntersection(ray, oom, rm);
            }
            default -> {
                V3D_Geometry fil = (new V3D_Rectangle((Rectangle) f, oom, rm))
                        .getIntersection(ray, oom, rm);
                V3D_Geometry lil = (new V3D_Rectangle((Rectangle) l, oom, rm))
                        .getIntersection(ray, oom, rm);
                V3D_Geometry ail = (new V3D_Rectangle((Rectangle) a, oom, rm))
                        .getIntersection(ray, oom, rm);
                if (fil == null) {
                    if (lil == null) {
                        V3D_Geometry ril = (new V3D_Rectangle((Rectangle) r, oom, rm))
                                .getIntersection(ray, oom, rm);
                        if (ail == null) {
                            V3D_Geometry til = (new V3D_Rectangle(
                                    (Rectangle) t, oom, rm)).getIntersection(ray, oom, rm);
                            if (ril == null) {
                                if (til == null) {
                                    return null;
                                } else {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom, rm))
                                            .getIntersection(ray, oom, rm);
//                                    return new V3D_LineSegment(
//                                            (V3D_Point) til,
//                                            (V3D_Point) bil,
//                                            oom, rm);
                                    return new V3D_LineSegment(e,
                                            ((V3D_Point) til).getVector(oom, rm),
                                            ((V3D_Point) bil).getVector(oom, rm));
                                }
                            } else {
                                if (til == null) {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom, rm))
                                            .getIntersection(ray, oom, rm);
                                    if (bil == null) {
                                        return null;
                                    } else {
                                        return V3D_LineSegment.getGeometry(
                                                (V3D_Point) ril,
                                                (V3D_Point) bil, oom, rm);
                                    }
                                } else {
                                    return V3D_LineSegment.getGeometry(
                                            (V3D_Point) ril,
                                            (V3D_Point) til, oom, rm);
                                }
                            }
                        } else {
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom, rm)).getIntersection(ray, oom, rm);
                                if (til == null) {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom, rm))
                                            .getIntersection(ray, oom, rm);
                                    if (bil == null) {
                                        return null;
                                    } else {
                                        return V3D_LineSegment.getGeometry(
                                                (V3D_Point) ail,
                                                (V3D_Point) bil, oom, rm);
                                    }
                                } else {
                                    return V3D_LineSegment.getGeometry(
                                            (V3D_Point) ail, (V3D_Point) til, oom, rm);
                                }
                            } else {
                                if (ail instanceof V3D_LineSegment) {
                                    return ail;
                                }
                                return V3D_LineSegment.getGeometry(
                                        (V3D_Point) ail, (V3D_Point) ril, oom, rm);
                            }
                        }
                    } else {
                        if (ail == null) {
                            V3D_Geometry ril = (new V3D_Rectangle(
                                    (Rectangle) r, oom, rm)).getIntersection(ray, oom, rm);
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom, rm)).getIntersection(ray, oom, rm);
                                V3D_Geometry bil = (new V3D_Rectangle(
                                        (Rectangle) b, oom, rm)).getIntersection(ray, oom, rm);
                                if (til == null) {
                                    if (bil == null) {
                                        return null;
                                    } else {
                                        return V3D_LineSegment.getGeometry(
                                                (V3D_Point) lil,
                                                (V3D_Point) bil, oom, rm);
                                    }
                                } else {
                                    if (bil == null) {
                                        return null;
                                    } else {
                                        return V3D_LineSegment.getGeometry(
                                                (V3D_Point) lil,
                                                (V3D_Point) bil, oom, rm);
                                    }
                                }
                            } else {
//                                return new V3D_LineSegment(
//                                        (V3D_Point) lil,
//                                        (V3D_Point) ril,
//                                        oom, rm);
                                return new V3D_LineSegment(e,
                                        ((V3D_Point) lil).getVector(oom, rm),
                                        ((V3D_Point) ril).getVector(oom, rm));
                            }
                        } else {
                            return V3D_LineSegment.getGeometry(
                                    (V3D_Point) lil, (V3D_Point) ail, oom, rm);
                        }
                    }
                } else {
                    if (lil == null) {
                        V3D_Geometry ril = (new V3D_Rectangle((Rectangle) r, oom, rm))
                                .getIntersection(ray, oom, rm);
                        if (ail == null) {
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom, rm)).getIntersection(ray, oom, rm);
                                if (til == null) {
                                    return null;
                                } else {
                                    return V3D_LineSegment.getGeometry(
                                            (V3D_Point) fil, (V3D_Point) til, oom, rm);
                                }
                            } else {
                                return V3D_LineSegment.getGeometry(
                                        (V3D_Point) fil, (V3D_Point) ril, oom, rm);
                            }
                        } else {
//                            return new V3D_LineSegment(
//                                    (V3D_Point) fil,
//                                    (V3D_Point) ail,
//                                    oom, rm);
                            return new V3D_LineSegment(e,
                                    ((V3D_Point) fil).getVector(oom, rm),
                                    ((V3D_Point) ail).getVector(oom, rm));
                        }
                    } else {
                        if (ail == null) {
                            return V3D_LineSegment.getGeometry(
                                    (V3D_Point) fil, (V3D_Point) lil, oom, rm);
                        } else {
                            return V3D_LineSegment.getGeometry(
                                    (V3D_Point) fil, (V3D_Point) ail, oom, rm);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param li Line segment to intersect with {@code this}.
     * @return either a point or line segment which is the intersection of
     * {@code li} and {@code this}.
     */
    @Override
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment li, int oom, 
            RoundingMode rm) {
        // Special case where both ends of li are within Envelope
        boolean lipi = isIntersectedBy(li.getP(), oom, rm);
        if (lipi && isIntersectedBy(li.getQ(oom, rm), oom, rm)) {
            return li;
        }
        switch (type) {
            case 0 -> {
                V3D_Point fp = new V3D_Point((Point) f);
                if (li.isIntersectedBy(fp, oom, rm)) {
                    return fp;
                } else {
                    return null;
                }
            }
            case 1 -> {
                return new V3D_LineSegment((LineSegment) l).getIntersection(li, 
                        oom, rm);
            }
            case 2 -> {
                return new V3D_LineSegment((LineSegment) f).getIntersection(li, 
                        oom, rm);
            }
            case 3 -> {
                return new V3D_LineSegment((LineSegment) f).getIntersection(li, 
                        oom, rm);
            }
            case 4 -> {
                return new V3D_Rectangle((Rectangle) f, oom, rm).getIntersection(li, oom, 
                        rm);
            }
            case 5 -> {
                return new V3D_Rectangle((Rectangle) l, oom, rm).getIntersection(li, oom,
                        rm);
            }
            case 6 -> {
                return new V3D_Rectangle((Rectangle) t, oom, rm).getIntersection(li, oom, 
                        rm);
            }
            default -> {
                V3D_Geometry fil = (new V3D_Rectangle((Rectangle) f, oom, rm))
                        .getIntersection(li, oom, rm);
                V3D_Geometry lil = (new V3D_Rectangle((Rectangle) l, oom, rm))
                        .getIntersection(li, oom, rm);
                V3D_Geometry ail = (new V3D_Rectangle((Rectangle) a, oom, rm))
                        .getIntersection(li, oom, rm);
                if (fil == null) {
                    if (lil == null) {
                        V3D_Geometry ril = (new V3D_Rectangle((Rectangle) r, oom, rm))
                                .getIntersection(li, oom, rm);
                        if (ail == null) {
                            V3D_Geometry til = (new V3D_Rectangle((Rectangle) t, oom, rm))
                                    .getIntersection(li, oom, rm);
                            if (ril == null) {
                                V3D_Geometry bil = (new V3D_Rectangle(
                                        (Rectangle) b, oom, rm))
                                        .getIntersection(li, oom, rm);
                                if (til == null) {
                                    if (bil == null) {
                                        return null;
                                    } else {
                                        return getIntersection((V3D_Point) bil,
                                                li, lipi, oom, rm);
                                    }
                                } else {
//                                    return new V3D_LineSegment(
//                                            (V3D_Point) til,
//                                            (V3D_Point) bil,
//                                            oom, rm);
                                    return new V3D_LineSegment(e,
                                            ((V3D_Point) til).getVector(oom, rm),
                                            ((V3D_Point) bil).getVector(oom, rm));
                                }
                            } else {
                                if (til == null) {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom, rm))
                                            .getIntersection(li, oom, rm);
                                    if (bil == null) {
                                        return getIntersection((V3D_Point) ril,
                                                li, lipi, oom, rm);
                                    } else {
                                        return V3D_LineSegment.getGeometry(
                                                (V3D_Point) ril,
                                                (V3D_Point) bil, oom, rm);
                                    }
                                } else {
                                    return V3D_LineSegment.getGeometry(
                                            (V3D_Point) ril, (V3D_Point) til, oom, rm);
                                }
                            }
                        } else {
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom, rm))
                                        .getIntersection(li, oom, rm);
                                if (til == null) {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom, rm))
                                            .getIntersection(li, oom, rm);
                                    if (bil == null) {
                                        return getIntersection((V3D_Point) ail,
                                                li, lipi, oom, rm);
                                    } else {
                                        return V3D_LineSegment.getGeometry(
                                                (V3D_Point) ail,
                                                (V3D_Point) bil, oom, rm);
                                    }
                                } else {
                                    return V3D_LineSegment.getGeometry(
                                            (V3D_Point) ail, (V3D_Point) til, oom, rm);
                                }
                            } else {
                                if (ail instanceof V3D_LineSegment aill) {
                                    return aill;
                                }
                                return V3D_LineSegment.getGeometry(
                                        (V3D_Point) ail, (V3D_Point) ril, oom, rm);
                            }
                        }
                    } else {
                        if (ail == null) {
                            V3D_Geometry ril = (new V3D_Rectangle(
                                    (Rectangle) r, oom, rm))
                                    .getIntersection(li, oom, rm);
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom, rm))
                                        .getIntersection(li, oom, rm);
                                V3D_Geometry bil = (new V3D_Rectangle(
                                        (Rectangle) b, oom, rm))
                                        .getIntersection(li, oom, rm);
                                if (til == null) {
                                    if (bil == null) {
                                        return getIntersection((V3D_Point) lil,
                                                li, lipi, oom, rm);
                                    } else {
                                        return V3D_LineSegment.getGeometry(
                                                (V3D_Point) lil,
                                                (V3D_Point) bil, oom, rm);
                                    }
                                } else {
                                    if (bil == null) {
                                        return getIntersection((V3D_Point) lil,
                                                li, lipi, oom, rm);
                                    } else {
                                        return V3D_LineSegment.getGeometry(
                                                (V3D_Point) lil,
                                                (V3D_Point) bil, oom, rm);
                                    }
                                }
                            } else {
//                                return new V3D_LineSegment(
//                                        (V3D_Point) lil,
//                                        (V3D_Point) ril,
//                                        oom, rm);
                                return new V3D_LineSegment(e,
                                        ((V3D_Point) lil).getVector(oom, rm),
                                        ((V3D_Point) ril).getVector(oom, rm));
                            }
                        } else {
                            return V3D_LineSegment.getGeometry(
                                    (V3D_Point) lil, (V3D_Point) ail, oom, rm);
                        }
                    }
                } else {
                    if (lil == null) {
                        V3D_Geometry ril = (new V3D_Rectangle((Rectangle) r, oom, rm))
                                .getIntersection(li, oom, rm);
                        if (ail == null) {
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom, rm))
                                        .getIntersection(li, oom, rm);
                                if (til == null) {
                                    return getIntersection((V3D_Point) fil,
                                            li, lipi, oom, rm);
                                } else {
                                    return V3D_LineSegment.getGeometry(
                                            (V3D_Point) fil, (V3D_Point) til, oom, rm);
                                }
                            } else {
                                return V3D_LineSegment.getGeometry(
                                        (V3D_Point) fil, (V3D_Point) ril, oom, rm);
                            }
                        } else {
//                            return new V3D_LineSegment(
//                                    (V3D_Point) fil,
//                                    (V3D_Point) ail,
//                                    oom, rm);
                            return new V3D_LineSegment(e,
                                    ((V3D_Point) fil).getVector(oom, rm),
                                    ((V3D_Point) ail).getVector(oom, rm));
                        }
                    } else {
                        if (ail == null) {
                            return getIntersection((V3D_Point) fil, li, lipi, 
                                    oom, rm);
                        } else {
                            return V3D_LineSegment.getGeometry(
                                    (V3D_Point) fil, (V3D_Point) ail, oom, rm);
                        }
                    }
                }
            }
        }
    }

    private V3D_FiniteGeometry getIntersection(V3D_Point pi, V3D_LineSegment li,
            boolean lipi, int oom, RoundingMode rm) {
        if (lipi) {
            return V3D_LineSegment.getGeometry(li.getP(), pi, oom, rm);
        } else {
            return V3D_LineSegment.getGeometry(li.getQ(oom, rm), pi, oom, rm);
        }
    }

    @Override
    public V3D_Envelope getEnvelope(int oom, RoundingMode rm) {
        return this;
    }

//    /**
//     * Test for equality.
//     *
//     * @param g The V3D_Geometry to test for equality with this.
//     * @return {@code true} iff this and e are equal.
//     */
//    @Override
//    public boolean equals(V3D_Geometry g) {
//        if (g instanceof V3D_Envelope) {
//            return equals((V3D_Envelope) g);
//        }
//        return false;
//    }
    /**
     * Test for equality.
     *
     * @param e The V3D_Envelope to test for equality with this.
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
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #xMin} rounded.
     */
    public Math_BigRational getXMin(int oom) {
        return getXMin(oom, RoundingMode.FLOOR);
    }

    /**
     * For getting {@link #xMin} rounded.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #xMin} rounded.
     */
    public Math_BigRational getXMin(int oom, RoundingMode rm) {
        return xMin.add(offset.getDX(oom - 2, rm)).round(oom, rm);
    }

    /**
     * For getting {@link #xMax} rounded. RoundingMode.CEILING is used.
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
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #yMin} rounded.
     */
    public Math_BigRational getYMin(int oom) {
        return getYMin(oom, RoundingMode.FLOOR);
    }

    /**
     * For getting {@link #yMin} rounded.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #yMin} rounded.
     */
    public Math_BigRational getYMin(int oom, RoundingMode rm) {
        return yMin.add(offset.getDY(oom - 2, rm)).round(oom, rm);
    }

    /**
     * For getting {@link #yMax} rounded. RoundingMode.CEILING is used.
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
     * @param oom The Order of Magnitude for the precision.
     * @return {@link #zMin} rounded.
     */
    public Math_BigRational getZMin(int oom) {
        return getZMin(oom, RoundingMode.FLOOR);
    }

    /**
     * For getting {@link #zMin} rounded.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for the precision.
     * @return {@link #zMin} rounded.
     */
    public Math_BigRational getZMin(int oom, RoundingMode rm) {
        return zMin.add(offset.getDZ(oom - 2, rm)).round(oom, rm);
    }

    /**
     * For getting {@link #zMax} rounded. RoundingMode.CEILING is used.
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
     * Test if {@code this} is intersected by {@code li}.
     *
     * @param li The line to test for intersection.
     * @return {@code true} iff {@code this} is intersected by {@code li}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Line li, int oom, RoundingMode rm) {
        switch (type) {
            case 0 -> {
                return li.isIntersectedBy(new V3D_Point((Point) f), oom, rm);
            }
            case 1 -> {
                return new V3D_LineSegment((LineSegment) l).isIntersectedBy(li, oom, rm);
            }
            case 2 -> {
                return new V3D_LineSegment((LineSegment) f).isIntersectedBy(li, oom, rm);
            }
            case 3 -> {
                return new V3D_LineSegment((LineSegment) f).isIntersectedBy(li, oom, rm);
            }
            case 4 -> {
                return new V3D_Rectangle((Rectangle) f, oom, rm).isIntersectedBy(li, oom, rm);
            }
            case 5 -> {
                return new V3D_Rectangle((Rectangle) l, oom, rm).isIntersectedBy(li, oom, rm);
            }
            case 6 -> {
                return new V3D_Rectangle((Rectangle) t, oom, rm).isIntersectedBy(li, oom, rm);
            }
            default -> {
                /**
                 * Each part of this testing could be done simultaneously. An
                 * alternative method might test three orthogonal sides and the
                 * additional corner that is on none of these sides.
                 */
                if (new V3D_Rectangle((Rectangle) f, oom, rm).isIntersectedBy(li, oom, rm)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) l, oom, rm).isIntersectedBy(li, oom, rm)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) a, oom, rm).isIntersectedBy(li, oom, rm)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) r, oom, rm).isIntersectedBy(li, oom, rm)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) t, oom, rm).isIntersectedBy(li, oom, rm)) {
                    return true;
                } else {
                    return new V3D_Rectangle((Rectangle) b, oom, rm).isIntersectedBy(li, oom, rm);
                }
            }
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Plane p, int oom, RoundingMode rm) {
        /**
         * Some alternatives for doing this are explained here:
         * https://gdbooks.gitbooks.io/3dcollisions/content/Chapter2/static_aabb_plane.html
         * This implements the approach outlined here:
         * https://stackoverflow.com/a/15691064/1998054 That is: The normal
         * vector of the plane points into one half. The dot product is positive
         * if the point is there, negative otherwise. Zero if it is exactly on
         * the plane. So, the idea is to test the vertices and if we find an
         * intersection return true.
         */
        V3D_Vector n = p.getN(oom, rm);
        if (type == 0) {
            return p.isIntersectedBy(new V3D_Point(ltf), oom, rm);
        }
        Math_BigRational ltfv = n.getDotProduct(new V3D_Vector(p.getP(), new V3D_Point(ltf), oom, rm), oom, rm);
        int ltfvc = ltfv.compareTo(Math_BigRational.ZERO);
        if (ltfvc == 0) {
            return true;
        } else if (ltfvc > 0) {
            Math_BigRational ltav = n.getDotProduct(new V3D_Vector(p.getP(), new V3D_Point(lta), oom, rm), oom, rm);
            int ltavc = ltav.compareTo(Math_BigRational.ZERO);
            if (ltavc == 0) {
                return true;
            } else if (ltfvc > 0) {
                Math_BigRational lbfv = n.getDotProduct(new V3D_Vector(p.getP(), new V3D_Point(lbf), oom, rm), oom, rm);
                int lbfvc = lbfv.compareTo(Math_BigRational.ZERO);
                if (lbfvc == 0) {
                    return true;
                } else if (lbfvc > 0) {
                    Math_BigRational lbav = n.getDotProduct(new V3D_Vector(p.getP(), new V3D_Point(lba), oom, rm), oom, rm);
                    int lbavc = lbav.compareTo(Math_BigRational.ZERO);
                    if (lbavc == 0) {
                        return true;
                    } else if (lbavc > 0) {
                        Math_BigRational rtfv = n.getDotProduct(new V3D_Vector(p.getP(), new V3D_Point(rtf), oom, rm), oom, rm);
                        int rtfvc = rtfv.compareTo(Math_BigRational.ZERO);
                        if (rtfvc == 0) {
                            return true;
                        } else if (rtfvc > 0) {
                            Math_BigRational rtav = n.getDotProduct(new V3D_Vector(p.getP(), new V3D_Point(rta), oom, rm), oom, rm);
                            int rtavc = rtav.compareTo(Math_BigRational.ZERO);
                            if (rtavc == 0) {
                                return true;
                            } else if (rtavc > 0) {
                                Math_BigRational rbfv = n.getDotProduct(new V3D_Vector(p.getP(), new V3D_Point(rbf), oom, rm), oom, rm);
                                int rbfvc = rbfv.compareTo(Math_BigRational.ZERO);
                                if (rbfvc == 0) {
                                    return true;
                                } else if (rbfvc > 0) {
                                    Math_BigRational rbav = n.getDotProduct(new V3D_Vector(p.getP(), new V3D_Point(rba), oom, rm), oom, rm);
                                    int rbavc = rbav.compareTo(Math_BigRational.ZERO);
                                    if (rbavc == 0) {
                                        return true;
                                    } else {
                                        return rbavc <= 0;
                                    }
                                } else {
                                    return true;
                                }
                            } else {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            Math_BigRational ltav = n.getDotProduct(new V3D_Vector(p.getP(), new V3D_Point(lta), oom, rm), oom, rm);
            int ltavc = ltav.compareTo(Math_BigRational.ZERO);
            if (ltavc == 0) {
                return true;
            } else if (ltfvc > 0) {
                return true;
            } else {
                Math_BigRational lbfv = n.getDotProduct(new V3D_Vector(p.getP(), new V3D_Point(lbf), oom, rm), oom, rm);
                int lbfvc = lbfv.compareTo(Math_BigRational.ZERO);
                if (lbfvc == 0) {
                    return true;
                } else if (lbfvc > 0) {
                    return true;
                } else {
                    Math_BigRational lbav = n.getDotProduct(new V3D_Vector(p.getP(), new V3D_Point(lba), oom, rm), oom, rm);
                    int lbavc = lbav.compareTo(Math_BigRational.ZERO);
                    if (lbavc == 0) {
                        return true;
                    } else if (lbavc > 0) {
                        return true;
                    } else {
                        Math_BigRational rtfv = n.getDotProduct(new V3D_Vector(p.getP(), new V3D_Point(rtf), oom, rm), oom, rm);
                        int rtfvc = rtfv.compareTo(Math_BigRational.ZERO);
                        if (rtfvc == 0) {
                            return true;
                        } else if (rtfvc > 0) {
                            return true;
                        } else {
                            Math_BigRational rtav = n.getDotProduct(new V3D_Vector(p.getP(), new V3D_Point(rta), oom, rm), oom, rm);
                            int rtavc = rtav.compareTo(Math_BigRational.ZERO);
                            if (rtavc == 0) {
                                return true;
                            } else if (rtavc > 0) {
                                return true;
                            } else {
                                Math_BigRational rbfv = n.getDotProduct(new V3D_Vector(p.getP(), new V3D_Point(rbf), oom, rm), oom, rm);
                                int rbfvc = rbfv.compareTo(Math_BigRational.ZERO);
                                if (rbfvc == 0) {
                                    return true;
                                } else if (rbfvc > 0) {
                                    return true;
                                } else {
                                    Math_BigRational rbav = n.getDotProduct(new V3D_Vector(p.getP(), new V3D_Point(rba), oom, rm), oom, rm);
                                    int rbavc = rbav.compareTo(Math_BigRational.ZERO);
                                    if (rbavc == 0) {
                                        return true;
                                    } else {
                                        return rbavc > 0;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * For testing if {@code this} is intersected by {@code t}.
     *
     * https://stackoverflow.com/questions/17458562/efficient-aabb-triangle-intersection-in-c-sharp
     *
     * @param t The triangle to be tested for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Triangle t, int oom, RoundingMode rm) {
        /**
         * Test the envelopes for intersection.
         */
        if (t.getEnvelope(oom, rm).isIntersectedBy(this, oom, rm)) {
            /**
             * Test the plane for intersection. This is not needed and may not
             * be computationally advantageous!
             */
            if (isIntersectedBy(t.p, oom, rm)) {
                /**
                 * Test the edges of the triangle for intersection. This is not
                 * needed and may not be computationally advantageous!
                 */
                if (isIntersectedBy(t.p.getPQ(), oom, rm)) {
                    return true;
                }
                if (isIntersectedBy(t.p.getQR(), oom, rm)) {
                    return true;
                }
                if (isIntersectedBy(t.p.getRP(), oom, rm)) {
                    return true;
                }
                /**
                 * <table>
                 * <caption>Types</caption>
                 * <thead>
                 * <tr><td>type</td><td>description</td></tr>
                 * </thead>
                 * <tbody>
                 * <tr><td>0</td><td>f, l, a, r, t, b Point</td></tr>
                 * <tr><td>1</td><td>f, a Point; l, r, t, b
                 * LineSegment</td></tr>
                 * <tr><td>2</td><td>1, r Point; f, a, t, b
                 * LineSegment</td></tr>
                 * <tr><td>3</td><td>t, b Point; f, l, a, r
                 * LineSegment</td></tr>
                 * <tr><td>4</td><td>f, a Rectangle; l, r, t, b
                 * LineSegment</td></tr>
                 * <tr><td>5</td><td>1, r Rectangle; f, a, t, b
                 * LineSegment</td></tr>
                 * <tr><td>6</td><td>t, b Rectangle; f, l, a, r
                 * LineSegment</td></tr>
                 * <tr><td>7</td><td>f, l, a, r, t, b Rectangle</td></tr>
                 * </tbody>
                 * </table>
                 */
                switch (type) {
                    case 0:
                        return true;
                    case 1:
                        return t.isIntersectedBy(new V3D_LineSegment((LineSegment) l), oom, rm);
                    case 2:
                        return t.isIntersectedBy(new V3D_LineSegment((LineSegment) f), oom, rm);
                    case 3:
                        return t.isIntersectedBy(new V3D_LineSegment((LineSegment) f), oom, rm);
                    case 5:
                        if (t.isIntersectedBy(new V3D_LineSegment((LineSegment) f), oom, rm)) {
                            return true;
                        } else {
                            if (t.isIntersectedBy(new V3D_LineSegment((LineSegment) a), oom, rm)) {
                                return true;
                            } else {
                                if (t.isIntersectedBy(new V3D_LineSegment((LineSegment) this.t), oom, rm)) {
                                    return true;
                                } else {
                                    return t.isIntersectedBy(new V3D_LineSegment((LineSegment) this.b), oom, rm);
                                }
                            }
                        }
                    case 6:
                        if (t.isIntersectedBy(new V3D_LineSegment((LineSegment) f), oom, rm)) {
                            return true;
                        } else {
                            if (t.isIntersectedBy(new V3D_LineSegment((LineSegment) l), oom, rm)) {
                                return true;
                            } else {
                                if (t.isIntersectedBy(new V3D_LineSegment((LineSegment) a), oom, rm)) {
                                    return true;
                                } else {
                                    return t.isIntersectedBy(new V3D_LineSegment((LineSegment) r), oom, rm);
                                }
                            }
                        }
                    default:
                        /**
                         * For the case when the triangle is outside and non of
                         * the edges cut through the triangle?
                         */
                        boolean check = checkIntersectionWithSide((Rectangle) l, t, oom, rm);
                        if (check) {
                            return true;
                        } else {
                            check = checkIntersectionWithSide((Rectangle) a, t, oom, rm);
                            if (check) {
                                return true;
                            } else {
                                check = checkIntersectionWithSide((Rectangle) r, t, oom, rm);
                                if (check) {
                                    return true;
                                } else {
                                    check = checkIntersectionWithSide((Rectangle) f, t, oom, rm);
                                    if (check) {
                                        return true;
                                    } else {
                                        check = checkIntersectionWithSide((Rectangle) this.t, t, oom, rm);
                                        if (check) {
                                            return true;
                                        } else {
                                            check = checkIntersectionWithSide((Rectangle) this.b, t, oom, rm);
                                            return check;
                                        }
                                    }
                                }
                            }
                        }
                }
            }
        }
        return false;
    }

    private boolean checkIntersectionWithSide(Rectangle r, V3D_Triangle t, int oom, RoundingMode rm) {
        V3D_Vector n;
        n = new V3D_Rectangle(r, oom, rm).p.getN(oom, rm);
        int tpnc = t.p.p.getDotProduct(n, oom, rm).compareTo(Math_BigRational.ZERO);
        if (tpnc == 0) {
            return true;
        } else if (tpnc == 1) {
            int tqnc = t.p.q.getDotProduct(n, oom, rm).compareTo(Math_BigRational.ZERO);
            if (tqnc != 1) {
                return true;
            } else {
                int trnc = t.p.r.getDotProduct(n, oom, rm).compareTo(Math_BigRational.ZERO);
                if (trnc == 0) {
                    return true;
                } else {
                    return trnc != 1;
                }
            }
        } else {
            int tqnc = t.p.q.getDotProduct(n, oom, rm).compareTo(Math_BigRational.ZERO);
            if (tqnc != -1) {
                return true;
            } else {
                int trnc = t.p.r.getDotProduct(n, oom, rm).compareTo(Math_BigRational.ZERO);
                if (trnc == 0) {
                    return true;
                } else {
                    return trnc == 1;
                }
            }
        }
    }

    /**
     *
     * Test if {@code this} is intersected by {@code r}.
     *
     * @param ray The ray to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} is intersected by {@code li}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Ray ray, int oom, RoundingMode rm) {
        switch (type) {
            case 0 -> {
                return ray.isIntersectedBy(new V3D_Point((Point) f), oom, rm);
            }
            case 1 -> {
                return new V3D_LineSegment((LineSegment) l).isIntersectedBy(ray, oom, rm);
            }
            case 2 -> {
                return new V3D_LineSegment((LineSegment) f).isIntersectedBy(ray, oom, rm);
            }
            case 3 -> {
                return new V3D_LineSegment((LineSegment) f).isIntersectedBy(ray, oom, rm);
            }
            case 4 -> {
                return new V3D_Rectangle((Rectangle) f, oom, rm).isIntersectedBy(ray, oom, rm);
            }
            case 5 -> {
                return new V3D_Rectangle((Rectangle) l, oom, rm).isIntersectedBy(ray, oom, rm);
            }
            case 6 -> {
                return new V3D_Rectangle((Rectangle) t, oom, rm).isIntersectedBy(ray, oom, rm);
            }
            default -> {
                /**
                 * Each part of this testing could be done simultaneously. An
                 * alternative method might test three orthogonal sides and the
                 * additional corner that is on none of these sides.
                 */
                if (new V3D_Rectangle((Rectangle) f, oom, rm).isIntersectedBy(ray, oom, rm)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) l, oom, rm).isIntersectedBy(ray, oom, rm)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) a, oom, rm).isIntersectedBy(ray, oom, rm)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) r, oom, rm).isIntersectedBy(ray, oom, rm)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) t, oom, rm).isIntersectedBy(ray, oom, rm)) {
                    return true;
                } else {
                    return new V3D_Rectangle((Rectangle) b, oom, rm).isIntersectedBy(ray, oom, rm);
                }
            }
        }
    }

    /**
     *
     * Test if {@code this} is intersected by {@code li}.
     *
     * @param li The line segment to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code this} is intersected by {@code li}.
     */
    @Override
    public boolean isIntersectedBy(V3D_LineSegment li, int oom, RoundingMode rm) {
        V3D_Envelope le = li.getEnvelope(oom, rm);
        if (le.isIntersectedBy(this, oom, rm)) {
            switch (type) {
                case 0 -> {
                    return li.isIntersectedBy(new V3D_Point((Point) f), oom, rm);
                }
                case 1 -> {
                    return new V3D_LineSegment((LineSegment) l).isIntersectedBy(li, oom, rm);
                }
                case 2 -> {
                    return new V3D_LineSegment((LineSegment) f).isIntersectedBy(li, oom, rm);
                }
                case 3 -> {
                    return new V3D_LineSegment((LineSegment) f).isIntersectedBy(li, oom, rm);
                }
                case 4 -> {
                    return new V3D_Rectangle((Rectangle) f, oom, rm).isIntersectedBy(li, oom, rm);
                }
                case 5 -> {
                    return new V3D_Rectangle((Rectangle) l, oom, rm).isIntersectedBy(li, oom, rm);
                }
                case 6 -> {
                    return new V3D_Rectangle((Rectangle) t, oom, rm).isIntersectedBy(li, oom, rm);
                }
                default -> {
                    /**
                     * Each part of this testing could be done simultaneously.
                     * An alternative method might test three orthogonal sides
                     * and the additional corner that is on none of these sides.
                     */
                    if (new V3D_Rectangle((Rectangle) f, oom, rm).isIntersectedBy(li, oom, rm)) {
                        return true;
                    } else if (new V3D_Rectangle((Rectangle) l, oom, rm).isIntersectedBy(li, oom, rm)) {
                        return true;
                    } else if (new V3D_Rectangle((Rectangle) a, oom, rm).isIntersectedBy(li, oom, rm)) {
                        return true;
                    } else if (new V3D_Rectangle((Rectangle) r, oom, rm).isIntersectedBy(li, oom, rm)) {
                        return true;
                    } else if (new V3D_Rectangle((Rectangle) t, oom, rm).isIntersectedBy(li, oom, rm)) {
                        return true;
                    } else {
                        return new V3D_Rectangle((Rectangle) this.b, oom, rm).isIntersectedBy(li, oom, rm);
                    }
                }
            }
        }
        return false;
    }

    /**
     * A point within the Envelope currently returns a distance of zero. This
     * could be changed in future to provide a negative distance which would be
     * the distance inside the envelope. To calculate the distance outside the
     * envelope, there are a number of different cases. The first cases are when
     * the closest point on the Envelope to the point {@code p} is one of the
     * corners. The next cases are when it is a point on one of the edges of
     * {@link #t}, {@link #b}, {@link #l}, {@link #r}, {@link #f}. The next
     * cases are when it is a point on one of the faces of {@link #t},
     * {@link #b}, {@link #l}, {@link #r}, {@link #f}. Depending on the values
     * and the oom given. The distance may appear to be zero when it is in fact
     * greater than zero. To test if the distance is zero use
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int)}.
     *
     * @param p The point to find the distance to/from.
     * @param oom The Order of Magnitude for the result precision.
     * @return The approximate or exact distance at the given {@code oom}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point p, int oom, RoundingMode rm) {
        if (this.isIntersectedBy(p, oom, rm)) {
            return BigDecimal.ZERO;
        }
        Math_BigRational d2 = getDistanceSquared(p, true, oom, rm);
        return new Math_BigRationalSqrt(d2, oom, rm).getSqrt(oom, rm).toBigDecimal(oom, rm);
//        // Special case where Envelope is infinitesimally small.
//        if (l instanceof Point && t instanceof Point) {
//            return ((Point) l).getDistance(new Point(p, oom, rm), oom);
//        }
//        int xcmin = p.getX(oom).compareTo(getXMin(oom));
//        int ycmin = p.getY(oom).compareTo(getYMin(oom));
//        int zcmin = p.getZ(oom).compareTo(getZMin(oom));
//        if (xcmin == -1) {
//            if (ycmin == -1) {
//                if (zcmin == -1) {
//                    // lbf
//                    if (f instanceof Rectangle rectangle) {
//                        return rectangle.p.getDistance(new Point(p, oom, rm), oom);
//                    } else if (f instanceof LineSegment lineSegment) {
//                        return lineSegment.p
//                                .getDistance(new Point(p, oom, rm), oom);
//                    } else {
//                        return ((Point) f)
//                                .getDistance(new Point(p, oom, rm), oom);
//                    }
//                } else {
//                    int zcmax = p.getZ(oom).compareTo(getZMax(oom));
//                    if (zcmax == 1) {
//                        // bla
//                        if (l instanceof Rectangle rectangle) {
//                            return rectangle.p
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        } else if (l instanceof LineSegment lineSegment) {
//                            return lineSegment.p
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        } else {
//                            return ((Point) l)
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        }
//                    } else {
//                        // lba - lbf
//                        if (l instanceof Rectangle rectangle) {
//                            return new Line(rectangle.b)
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        } else if (l instanceof LineSegment) {
//                            return ((Line) l)
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        } else {
//                            return ((Point) l)
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        }
//                    }
//                }
//            } else {
//                int ycmax = p.getY(oom).compareTo(getYMax(oom));
//                if (ycmax == 1) {
//                    if (zcmin == -1) {
//                        // ltf
//                        if (f instanceof Rectangle rectangle) {
//                            return rectangle.q
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        } else if (f instanceof LineSegment lineSegment) {
//                            return lineSegment.p
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        } else {
//                            return ((Point) f)
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        }
//                    } else {
//                        int zcmax = p.getZ(oom).compareTo(getZMax(oom));
//                        if (zcmax == 1) {
//                            // lta
//                            if (l instanceof Rectangle rectangle) {
//                                return rectangle.q
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else if (l instanceof LineSegment lineSegment) {
//                                return lineSegment.p
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else {
//                                return ((Point) l)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            }
//                        } else {
//                            // lta - ltf
//                            if (l instanceof Rectangle rectangle) {
//                                return new Line(rectangle.t)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else if (l instanceof LineSegment) {
//                                return ((Line) l)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else {
//                                return ((Point) l)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            }
//                        }
//                    }
//                } else {
//                    if (zcmin == -1) {
//                        // lbf - ltf
//                        if (l instanceof Rectangle rectangle) {
//                            return new Line(rectangle.ri)
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        } else if (l instanceof LineSegment) {
//                            return ((Line) l)
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        } else {
//                            return ((Point) l)
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        }
//                    } else {
//                        int zcmax = p.getZ(oom).compareTo(getZMax(oom));
//                        if (zcmax == 1) {
//                            // lba - lta
//                            if (l instanceof Rectangle rectangle) {
//                                return new Line(rectangle.l)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else if (l instanceof LineSegment) {
//                                return ((Line) l)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else {
//                                return ((Point) l)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            }
//                        } else {
//                            // lba - lta - ltf - lbf
//                            if (l instanceof Rectangle rectangle) {
//                                return new Plane(rectangle)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else if (l instanceof LineSegment) {
//                                return ((Line) l)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else {
//                                return ((Point) l)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            int xcmax = p.getX(oom).compareTo(getXMax(oom));
//            if (xcmax == 1) {
//                if (ycmin == -1) {
//                    if (zcmin == -1) {
//                        // rbf
//                        if (f instanceof Rectangle rectangle) {
//                            return rectangle.s
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        } else if (f instanceof LineSegment lineSegment) {
//                            return lineSegment.q
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        } else {
//                            return ((Point) f)
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        }
//                    } else {
//                        int zcmax = p.getZ(oom).compareTo(getZMax(oom));
//                        if (zcmax == 1) {
//                            // rba
//                            if (a instanceof Rectangle rectangle) {
//                                return rectangle.p
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else if (a instanceof LineSegment lineSegment) {
//                                return lineSegment.p
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else {
//                                return ((Point) a)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            }
//                        } else {
//                            // rbf - rba
//                            if (r instanceof Rectangle rectangle) {
//                                return new Line(rectangle.b)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else if (r instanceof LineSegment) {
//                                return ((Line) r).getDistance(new Point(p, oom, rm), oom);
//                            } else {
//                                return ((Point) r).getDistance(new Point(p, oom, rm), oom);
//                            }
//                        }
//                    }
//                } else {
//                    int ycmax = p.getY(oom).compareTo(getYMax(oom));
//                    if (ycmax == 1) {
//                        if (zcmin == -1) {
//                            // rtf
//                            if (f instanceof Rectangle rectangle) {
//                                return rectangle.r
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else if (f instanceof LineSegment lineSegment) {
//                                return lineSegment.q
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else {
//                                return ((Point) f)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            }
//                        } else {
//                            int zcmax = p.getZ(oom).compareTo(getZMax(oom));
//                            if (zcmax == 1) {
//                                // rta
//                                if (a instanceof Rectangle rectangle) {
//                                    return rectangle.q
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else if (a instanceof LineSegment lineSegment) {
//                                    return lineSegment.p
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else {
//                                    return ((Point) a)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                }
//                            } else {
//                                // rtf - rta
//                                if (r instanceof Rectangle rectangle) {
//                                    return new Line(rectangle.t)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else if (r instanceof LineSegment) {
//                                    return ((Line) r)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else {
//                                    return ((Point) r)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                }
//                            }
//                        }
//                    } else {
//                        if (zcmin == -1) {
//                            // rbf - rtf
//                            if (f instanceof Rectangle rectangle) {
//                                return new Line(rectangle.ri)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else if (f instanceof LineSegment) {
//                                return ((Line) f)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else {
//                                return ((Point) f)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            }
//                        } else {
//                            int zcmax = p.getZ(oom).compareTo(getZMax(oom));
//                            if (zcmax == 1) {
//                                // rba - rta
//                                if (a instanceof Rectangle rectangle) {
//                                    return new Line(rectangle.l)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else if (a instanceof LineSegment) {
//                                    return ((Line) a)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else {
//                                    return ((Point) a)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                }
//                            } else {
//                                // rbf - rtf - rta - rba
//                                if (r instanceof Rectangle rectangle) {
//                                    return new Plane(rectangle)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else if (r instanceof LineSegment) {
//                                    return ((Line) r)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else {
//                                    return ((Point) r)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                }
//                            }
//                        }
//                    }
//                }
//            } else {
//                if (ycmin == -1) {
//                    if (zcmin == -1) {
//                        // lbf - rbf
//                        if (f instanceof Rectangle rectangle) {
//                            return new Line(rectangle.b)
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        } else if (f instanceof LineSegment) {
//                            return ((Line) f)
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        } else {
//                            return ((Point) f)
//                                    .getDistance(new Point(p, oom, rm), oom);
//                        }
//                    } else {
//                        int zcmax = p.getZ(oom).compareTo(getZMax(oom));
//                        if (zcmax == 1) {
//                            // rba - lba
//                            if (a instanceof Rectangle rectangle) {
//                                return new Line(rectangle.b)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else if (a instanceof LineSegment) {
//                                return ((Line) a)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else {
//                                return ((Point) a)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            }
//                        } else {
//                            // lba - lbf - rbf - rba
//                            if (b instanceof Rectangle rectangle) {
//                                return new Plane(rectangle)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else if (b instanceof LineSegment) {
//                                return ((Line) b)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else {
//                                return ((Point) b)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            }
//                        }
//                    }
//                } else {
//                    int ycmax = p.getY(oom).compareTo(getYMax(oom));
//                    if (ycmax == 1) {
//                        if (zcmin == -1) {
//                            // ltf - rtf
//                            if (f instanceof Rectangle rectangle) {
//                                return rectangle.t
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else if (f instanceof LineSegment lineSegment) {
//                                return lineSegment.q
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else {
//                                return ((Point) f)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            }
//                        } else {
//                            int zcmax = p.getZ(oom).compareTo(getZMax(oom));
//                            if (zcmax == 1) {
//                                // rta - lta
//                                if (a instanceof Rectangle rectangle) {
//                                    return new Line(rectangle.t)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else if (a instanceof LineSegment) {
//                                    return ((Line) a)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else {
//                                    return ((Point) a)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                }
//                            } else {
//                                // ltf - lta - rta - rtf
//                                if (t instanceof Rectangle rectangle) {
//                                    return new Plane(rectangle)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else if (r instanceof LineSegment) {
//                                    return ((Line) t)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else {
//                                    return ((Point) t)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                }
//                            }
//                        }
//                    } else {
//                        if (zcmin == -1) {
//                            // lbf - ltf - rtf - rbf
//                            if (f instanceof Rectangle rectangle) {
//                                return new Plane(rectangle)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else if (f instanceof LineSegment) {
//                                return ((Line) f)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            } else {
//                                return ((Point) f)
//                                        .getDistance(new Point(p, oom, rm), oom);
//                            }
//                        } else {
//                            int zcmax = p.getZ(oom).compareTo(getZMax(oom));
//                            if (zcmax == 1) {
//                                // rba - rta - lta - lba
//                                if (a instanceof Rectangle rectangle) {
//                                    return new Plane(rectangle)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else if (a instanceof LineSegment) {
//                                    return ((Line) a)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else {
//                                    return ((Point) a)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                }
//                            } else {
//                                // lba - lbf - rbf - rba
//                                if (r instanceof Rectangle) {
//                                    return new Plane((Rectangle) b)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else if (r instanceof LineSegment) {
//                                    return ((LineSegment) b)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                } else {
//                                    return ((Point) b)
//                                            .getDistance(new Point(p, oom, rm), oom);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    /**
     * A point within the Envelope currently returns a distance of zero. This
     * could be changed in future to provide a negative distance which would be
     * the distance inside the envelope. To calculate the distance outside the
     * envelope, there are a number of different cases. The first cases are when
     * the closest point on the Envelope to the point {@code p} is one of the
     * corners. The next cases are when it is a point on one of the edges of
     * {@link #t}, {@link #b}, {@link #l}, {@link #r}, {@link #f}. The next
     * cases are when it is a point on one of the faces of {@link #t},
     * {@link #b}, {@link #l}, {@link #r}, {@link #f}. Depending on the values
     * and the oom given. The distance may appear to be zero when it is in fact
     * greater than zero. To test if the distance is zero use
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int)}.
     *
     * @param p The point to find the distance to/from.
     * @param oom The Order of Magnitude for the result precision.
     * @return The approximate or exact distance at the given {@code oom}.
     */
    @Override
    public Math_BigRational getDistanceSquared(V3D_Point p, int oom, RoundingMode rm) {
        if (this.isIntersectedBy(p, oom, rm)) {
            return Math_BigRational.ZERO;
        }
        return getDistanceSquared(p, true, oom, rm);
    }

    /**
     * A point within the Envelope currently returns a distance of zero. This
     * could be changed in future to provide a negative distance which would be
     * the distance inside the envelope. To calculate the distance outside the
     * envelope, there are a number of different cases. The first cases are when
     * the closest point on the Envelope to the point {@code p} is one of the
     * corners. The next cases are when it is a point on one of the edges of
     * {@link #t}, {@link #b}, {@link #l}, {@link #r}, {@link #f}. The next
     * cases are when it is a point on one of the faces of {@link #t},
     * {@link #b}, {@link #l}, {@link #r}, {@link #f}. Depending on the values
     * and the oom given. The distance may appear to be zero when it is in fact
     * greater than zero. To test if the distance is zero use
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int)}.
     *
     * @param p The point to find the distance to/from.
     * @param noInt This is ignored, but it distinguishes this method from
     * {@link #getDistanceSquared(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int)}.
     * @param oom The Order of Magnitude for the result precision.
     * @return The approximate or exact distance at the given {@code oom}.
     */
    protected Math_BigRational getDistanceSquared(V3D_Point p, boolean noInt,
            int oom, RoundingMode rm) {
        // Special case where Envelope is infinitesimally small.
        if (l instanceof Point && t instanceof Point) {
            return ((Point) l).getDistanceSquared(new Point(p, oom, rm));
        }
        int xcmin = p.getX(oom, rm).compareTo(getXMin(oom, rm));
        int ycmin = p.getY(oom, rm).compareTo(getYMin(oom, rm));
        int zcmin = p.getZ(oom, rm).compareTo(getZMin(oom, rm));
        if (xcmin == -1) {
            if (ycmin == -1) {
                if (zcmin == -1) {
                    // lbf
                    if (f instanceof Rectangle rectangle) {
                        return rectangle.p.getDistanceSquared(new Point(p, oom, rm));
                    } else if (f instanceof LineSegment lineSegment) {
                        return lineSegment.p.getDistanceSquared(new Point(p, oom, rm));
                    } else {
                        return ((Point) f).getDistanceSquared(new Point(p, oom, rm));
                    }
                } else {
                    int zcmax = p.getZ(oom, rm).compareTo(getZMax(oom, rm));
                    if (zcmax == 1) {
                        // bla
                        if (l instanceof Rectangle rectangle) {
                            return rectangle.p.getDistanceSquared(new Point(p, oom, rm));
                        } else if (l instanceof LineSegment lineSegment) {
                            return lineSegment.p.getDistanceSquared(
                                    new Point(p, oom, rm));
                        } else {
                            return ((Point) l).getDistanceSquared(new Point(p, oom, rm));
                        }
                    } else {
                        // lba - lbf
                        if (l instanceof Rectangle rectangle) {
                            return new Line(rectangle.b).getDistanceSquared(
                                    new Point(p, oom, rm), oom, rm);
                        } else if (l instanceof LineSegment) {
                            return ((Line) l).getDistanceSquared(
                                    new Point(p, oom, rm), oom, rm);
                        } else {
                            return ((Point) l).getDistanceSquared(new Point(p, oom, rm));
                        }
                    }
                }
            } else {
                int ycmax = p.getY(oom, rm).compareTo(getYMax(oom, rm));
                if (ycmax == 1) {
                    if (zcmin == -1) {
                        // ltf
                        if (f instanceof Rectangle rectangle) {
                            return rectangle.q.getDistanceSquared(new Point(p, oom, rm));
                        } else if (f instanceof LineSegment lineSegment) {
                            return lineSegment.p.getDistanceSquared(
                                    new Point(p, oom, rm));
                        } else {
                            return ((Point) f).getDistanceSquared(new Point(p, oom, rm));
                        }
                    } else {
                        int zcmax = p.getZ(oom, rm).compareTo(getZMax(oom, rm));
                        if (zcmax == 1) {
                            // lta
                            if (l instanceof Rectangle rectangle) {
                                return rectangle.q.getDistanceSquared(
                                        new Point(p, oom, rm));
                            } else if (l instanceof LineSegment lineSegment) {
                                return lineSegment.p.getDistanceSquared(
                                        new Point(p, oom, rm));
                            } else {
                                return ((Point) l).getDistanceSquared(
                                        new Point(p, oom, rm));
                            }
                        } else {
                            // lta - ltf
                            if (l instanceof Rectangle rectangle) {
                                return new Line(rectangle.t).getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else if (l instanceof LineSegment) {
                                return ((Line) l).getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else {
                                return ((Point) l).getDistanceSquared(
                                        new Point(p, oom, rm));
                            }
                        }
                    }
                } else {
                    if (zcmin == -1) {
                        // lbf - ltf
                        if (l instanceof Rectangle rectangle) {
                            return new Line(rectangle.ri).getDistanceSquared(
                                    new Point(p, oom, rm), oom, rm);
                        } else if (l instanceof LineSegment) {
                            return ((Line) l).getDistanceSquared(
                                    new Point(p, oom, rm), oom, rm);
                        } else {
                            return ((Point) l).getDistanceSquared(new Point(p, oom, rm));
                        }
                    } else {
                        int zcmax = p.getZ(oom, rm).compareTo(getZMax(oom, rm));
                        if (zcmax == 1) {
                            // lba - lta
                            if (l instanceof Rectangle rectangle) {
                                return new Line(rectangle.l).getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else if (l instanceof LineSegment) {
                                return ((Line) l).getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else {
                                return ((Point) l).getDistanceSquared(
                                        new Point(p, oom, rm));
                            }
                        } else {
                            // lba - lta - ltf - lbf
                            if (l instanceof Rectangle rectangle) {
                                return new Plane(rectangle).getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else if (l instanceof LineSegment) {
                                return ((Line) l).getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else {
                                return ((Point) l).getDistanceSquared(
                                        new Point(p, oom, rm));
                            }
                        }
                    }
                }
            }
        } else {
            int xcmax = p.getX(oom, rm).compareTo(getXMax(oom, rm));
            if (xcmax == 1) {
                if (ycmin == -1) {
                    if (zcmin == -1) {
                        // rbf
                        if (f instanceof Rectangle rectangle) {
                            return rectangle.s.getDistanceSquared(new Point(p, oom, rm));
                        } else if (f instanceof LineSegment lineSegment) {
                            return lineSegment.q.getDistanceSquared(
                                    new Point(p, oom, rm));
                        } else {
                            return ((Point) f).getDistanceSquared(new Point(p, oom, rm));
                        }
                    } else {
                        int zcmax = p.getZ(oom, rm).compareTo(getZMax(oom, rm));
                        if (zcmax == 1) {
                            // rba
                            if (a instanceof Rectangle rectangle) {
                                return rectangle.p.getDistanceSquared(
                                        new Point(p, oom, rm));
                            } else if (a instanceof LineSegment lineSegment) {
                                return lineSegment.p.getDistanceSquared(
                                        new Point(p, oom, rm));
                            } else {
                                return ((Point) a).getDistanceSquared(
                                        new Point(p, oom, rm));
                            }
                        } else {
                            // rbf - rba
                            if (r instanceof Rectangle rectangle) {
                                return new Line(rectangle.b).getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else if (r instanceof LineSegment) {
                                return ((Line) r).getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else {
                                return ((Point) r).getDistanceSquared(
                                        new Point(p, oom, rm));
                            }
                        }
                    }
                } else {
                    int ycmax = p.getY(oom, rm).compareTo(getYMax(oom, rm));
                    if (ycmax == 1) {
                        if (zcmin == -1) {
                            // rtf
                            if (f instanceof Rectangle rectangle) {
                                return rectangle.r.getDistanceSquared(
                                        new Point(p, oom, rm));
                            } else if (f instanceof LineSegment lineSegment) {
                                return lineSegment.q.getDistanceSquared(
                                        new Point(p, oom, rm));
                            } else {
                                return ((Point) f).getDistanceSquared(
                                        new Point(p, oom, rm));
                            }
                        } else {
                            int zcmax = p.getZ(oom, rm).compareTo(getZMax(oom, rm));
                            if (zcmax == 1) {
                                // rta
                                if (a instanceof Rectangle rectangle) {
                                    return rectangle.q.getDistanceSquared(
                                            new Point(p, oom, rm));
                                } else if (a instanceof LineSegment lineSegment) {
                                    return lineSegment.p.getDistanceSquared(
                                            new Point(p, oom, rm));
                                } else {
                                    return ((Point) a).getDistanceSquared(
                                            new Point(p, oom, rm));
                                }
                            } else {
                                // rtf - rta
                                if (r instanceof Rectangle rectangle) {
                                    return new Line(rectangle.t)
                                            .getDistanceSquared(
                                                    new Point(p, oom, rm), oom, rm);
                                } else if (r instanceof LineSegment) {
                                    return ((Line) r).getDistanceSquared(
                                            new Point(p, oom, rm), oom, rm);
                                } else {
                                    return ((Point) r).getDistanceSquared(
                                            new Point(p, oom, rm));
                                }
                            }
                        }
                    } else {
                        if (zcmin == -1) {
                            // rbf - rtf
                            if (f instanceof Rectangle rectangle) {
                                return new Line(rectangle.ri)
                                        .getDistanceSquared(new Point(p, oom, rm), oom, rm);
                            } else if (f instanceof LineSegment) {
                                return ((Line) f).getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else {
                                return ((Point) f).getDistanceSquared(
                                        new Point(p, oom, rm));
                            }
                        } else {
                            int zcmax = p.getZ(oom, rm).compareTo(getZMax(oom, rm));
                            if (zcmax == 1) {
                                // rba - rta
                                if (a instanceof Rectangle rectangle) {
                                    return new Line(rectangle.l)
                                            .getDistanceSquared(new Point(p, oom, rm),
                                                    oom, rm);
                                } else if (a instanceof LineSegment) {
                                    return ((Line) a).getDistanceSquared(
                                            new Point(p, oom, rm), oom, rm);
                                } else {
                                    return ((Point) a).getDistanceSquared(
                                            new Point(p, oom, rm));
                                }
                            } else {
                                // rbf - rtf - rta - rba
                                if (r instanceof Rectangle rectangle) {
                                    return new Plane(rectangle)
                                            .getDistanceSquared(new Point(p, oom, rm),
                                                    oom, rm);
                                } else if (r instanceof LineSegment) {
                                    return ((Line) r).getDistanceSquared(
                                            new Point(p, oom, rm), oom, rm);
                                } else {
                                    return ((Point) r).getDistanceSquared(
                                            new Point(p, oom, rm));
                                }
                            }
                        }
                    }
                }
            } else {
                if (ycmin == -1) {
                    if (zcmin == -1) {
                        // lbf - rbf
                        if (f instanceof Rectangle rectangle) {
                            return new Line(rectangle.b).getDistanceSquared(
                                    new Point(p, oom, rm), oom, rm);
                        } else if (f instanceof LineSegment) {
                            return ((Line) f).getDistanceSquared(new Point(p, oom, rm),
                                    oom, rm);
                        } else {
                            return ((Point) f).getDistanceSquared(new Point(p, oom, rm));
                        }
                    } else {
                        int zcmax = p.getZ(oom, rm).compareTo(getZMax(oom, rm));
                        if (zcmax == 1) {
                            // rba - lba
                            if (a instanceof Rectangle rectangle) {
                                return new Line(rectangle.b).getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else if (a instanceof LineSegment) {
                                return ((Line) a).getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else {
                                return ((Point) a).getDistanceSquared(
                                        new Point(p, oom, rm));
                            }
                        } else {
                            // lba - lbf - rbf - rba
                            if (b instanceof Rectangle rectangle) {
                                return new Plane(rectangle).getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else if (b instanceof LineSegment) {
                                return ((Line) b).getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else {
                                return ((Point) b).getDistanceSquared(
                                        new Point(p, oom, rm));
                            }
                        }
                    }
                } else {
                    int ycmax = p.getY(oom, rm).compareTo(getYMax(oom, rm));
                    if (ycmax == 1) {
                        if (zcmin == -1) {
                            // ltf - rtf
                            if (f instanceof Rectangle rectangle) {
                                return rectangle.t.getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else if (f instanceof LineSegment lineSegment) {
                                return lineSegment.q.getDistanceSquared(
                                        new Point(p, oom, rm));
                            } else {
                                return ((Point) f).getDistanceSquared(
                                        new Point(p, oom, rm));
                            }
                        } else {
                            int zcmax = p.getZ(oom, rm).compareTo(getZMax(oom, rm));
                            if (zcmax == 1) {
                                // rta - lta
                                if (a instanceof Rectangle rectangle) {
                                    return new Line(rectangle.t)
                                            .getDistanceSquared(new Point(p, oom, rm),
                                                    oom, rm);
                                } else if (a instanceof LineSegment) {
                                    return ((Line) a).getDistanceSquared(
                                            new Point(p, oom, rm), oom, rm);
                                } else {
                                    return ((Point) a).getDistanceSquared(
                                            new Point(p, oom, rm));
                                }
                            } else {
                                // ltf - lta - rta - rtf
                                if (t instanceof Rectangle rectangle) {
                                    return new Plane(rectangle)
                                            .getDistanceSquared(new Point(p, oom, rm),
                                                    oom, rm);
                                } else if (r instanceof LineSegment) {
                                    return ((Line) t).getDistanceSquared(
                                            new Point(p, oom, rm), oom, rm);
                                } else {
                                    return ((Point) t).getDistanceSquared(
                                            new Point(p, oom, rm));
                                }
                            }
                        }
                    } else {
                        if (zcmin == -1) {
                            // lbf - ltf - rtf - rbf
                            if (f instanceof Rectangle rectangle) {
                                return new Plane(rectangle).getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else if (f instanceof LineSegment) {
                                return ((Line) f).getDistanceSquared(
                                        new Point(p, oom, rm), oom, rm);
                            } else {
                                return ((Point) f).getDistanceSquared(
                                        new Point(p, oom, rm));
                            }
                        } else {
                            int zcmax = p.getZ(oom, rm).compareTo(getZMax(oom, rm));
                            if (zcmax == 1) {
                                // rba - rta - lta - lba
                                if (a instanceof Rectangle rectangle) {
                                    return new Plane(rectangle)
                                            .getDistanceSquared(new Point(p, oom, rm),
                                                    oom, rm);
                                } else if (a instanceof LineSegment) {
                                    return ((Line) a).getDistanceSquared(
                                            new Point(p, oom, rm), oom, rm);
                                } else {
                                    return ((Point) a).getDistanceSquared(
                                            new Point(p, oom, rm));
                                }
                            } else {
                                // lba - lbf - rbf - rba
                                if (r instanceof Rectangle) {
                                    return new Plane((Rectangle) b)
                                            .getDistanceSquared(new Point(p, oom, rm),
                                                    oom, rm);
                                } else if (r instanceof LineSegment) {
                                    return ((LineSegment) b).getDistanceSquared(
                                            new Point(p, oom, rm), oom, rm);
                                } else {
                                    return ((Point) b).getDistanceSquared(
                                            new Point(p, oom, rm));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Plane p, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Triangle t, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Ray r, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Ray r, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Plane p, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Plane p, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Point[] getPoints(int oom, RoundingMode rm) {
        V3D_Point[] pts = new V3D_Point[8];
        pts[0] = new V3D_Point(lba);
        pts[1] = new V3D_Point(lbf);
        pts[2] = new V3D_Point(lta);
        pts[3] = new V3D_Point(ltf);
        pts[4] = new V3D_Point(rba);
        pts[5] = new V3D_Point(rbf);
        pts[6] = new V3D_Point(rta);
        pts[7] = new V3D_Point(rtf);
        return pts;
    }

    /**
     * Abstract Geometry class for geometries aligning with axes.
     */
    public abstract class Geometry implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The environment.
         */
        public final V3D_Environment e;

        /**
         * Created a new Geometry.
         *
         * @param e What {@link #e} is set to.
         */
        public Geometry(V3D_Environment e) {
            this.e = e;
        }

        /**
         * @param v The vector to tranlsate to the geometry.
         * @return The application of the vector v.
         */
        protected abstract Geometry translate(V3D_Vector v, int oom, RoundingMode rm);
    }

    /**
     * A basic Point class.
     */
    public class Point extends Geometry {

        private static final long serialVersionUID = 1L;

        /**
         * The x coordinate.
         */
        public Math_BigRational x;

        /**
         * The y coordinate.
         */
        public Math_BigRational y;

        /**
         * The z coordinate.
         */
        public Math_BigRational z;

        /**
         * Create a new instance from {@code p} and {@code v}.
         *
         * @param p The point to duplicate.
         * @param v The vector to tranlsate immediately to {@code p}.
         */
        public Point(Point p, V3D_Vector v, int oom, RoundingMode rm) {
            super(p.e);
            x = p.x.add(v.getDX(oom, rm));
            y = p.y.add(v.getDY(oom, rm));
            z = p.z.add(v.getDZ(oom, rm));
        }

        /**
         * Create a new instance from {@code p}.
         *
         * @param p The point to duplicate
         */
        public Point(V3D_Point p, int oom, RoundingMode rm) {
            super(p.e);
            x = p.getX(oom, rm);
            y = p.getY(oom, rm);
            z = p.getZ(oom, rm);
        }

        /**
         * Create a new instance from {@code x}, {@code y}, {@code z}.
         *
         * @param e What {@link #e} is set to.
         * @param x What {@link #x} is set to.
         * @param y What {@link #y} is set to.
         * @param z What {@link #z} is set to.
         */
        public Point(V3D_Environment e, Math_BigRational x, Math_BigRational y,
                Math_BigRational z) {
            super(e);
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /**
         * Create a new instance from {@code v}.
         *
         * @param e What {@link #e} is set to.
         * @param v The vector.
         */
        public Point(V3D_Environment e, V3D_Vector v, int oom, RoundingMode rm) {
            super(e);
            x = v.getDX(oom, rm);
            y = v.getDY(oom, rm);
            z = v.getDZ(oom, rm);
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "(x=" + x.toString()
                    + ", y=" + y.toString() + ", z=" + z.toString() + ")";
        }
        
        public boolean equals(Point b) {
            if (this.x.compareTo(b.x) == 0) {
                if (this.y.compareTo(b.y) == 0) {
                    return this.z.compareTo(b.z) == 0;
                }
            }
            return false;
        }

        
        @Override
        protected Point translate(V3D_Vector v, int oom, RoundingMode rm) {
            return new Point(this, v, oom, rm);
        }

        /**
         * Get the distance between this and {@code p}.
         *
         * @param p A point.
         * @param oom The Order of Magnitude for the precision of the result.
         * @return The distance from {@code p} to this.
         */
        public BigDecimal getDistance(Point p, int oom, RoundingMode rm) {
            if (this.equals(p)) {
                return BigDecimal.ZERO;
            }
            return new Math_BigRationalSqrt(getDistanceSquared(p), oom, rm)
                    .toBigDecimal(oom, rm);
        }

        /**
         * Get the distance between this and {@code p}.
         *
         * @param p A point.
         * @return The distance from {@code p} to this.
         */
        public Math_BigRationalSqrt getDistanceExact(Point p, int oom, RoundingMode rm) {
            if (this.equals(p)) {
                return Math_BigRationalSqrt.ZERO;
            }
            // The following choice of -1 is arbitrary.
            return new Math_BigRationalSqrt(getDistanceSquared(p), oom, rm);
        }

        /**
         * Get the distance squared between this and {@code p}.
         *
         * @param p A point.
         * @return The distance squared from {@code p} to this.
         */
        public Math_BigRational getDistanceSquared(Point p) {
            Math_BigRational dx = x.subtract(p.x);
            Math_BigRational dy = y.subtract(p.y);
            Math_BigRational dz = z.subtract(p.z);
            return dx.pow(2).add(dy.pow(2)).add(dz.pow(2));
        }
    }

    /**
     * A line that aligns with the axes (a reticule).
     */
    public class Line extends Geometry {

        private static final long serialVersionUID = 1L;

        /**
         * A point defining the line.
         */
        public final Point p;

        /**
         * A point defining the line.
         */
        public final Point q;

        /**
         * The direction vector from {@link #p} in the direction of {@link #q}.
         */
        public final V3D_Vector v;

        /**
         * Create a new instance from {@code l} and {@code v}.
         *
         * @param l The line to duplicate.
         * @param v The vector to tranlsate immediately to {@code p}.
         */
        public Line(Line l, V3D_Vector v, int oom, RoundingMode rm) {
            super(l.e);
            this.p = new Point(l.p, v, oom, rm);
            this.q = new Point(l.q, v, oom, rm);
            this.v = new V3D_Vector(
                    q.x.subtract(p.x),
                    q.y.subtract(p.y),
                    q.z.subtract(p.z));
        }

        /**
         * {@code p} should not be equal to {@code q}.
         *
         * @param p What {@link #p} is set to.
         * @param q What {@link #q} is set to.
         */
        public Line(Point p, Point q) {
            super(p.e);
            this.p = p;
            this.q = q;
            this.v = new V3D_Vector(
                    q.x.subtract(p.x),
                    q.y.subtract(p.y),
                    q.z.subtract(p.z));
        }

        /**
         * Create a new instance from {@code l}
         *
         * @param l Line to create from.
         */
        public Line(Line l) {
            super(l.e);
            this.p = l.p;
            this.q = l.q;
            this.v = new V3D_Vector(
                    q.x.subtract(p.x),
                    q.y.subtract(p.y),
                    q.z.subtract(p.z));
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "(p=" + p.toString()
                    + ", q=" + q.toString() + ", v=" + v.toString() + ")";
        }

        @Override
        protected Line translate(V3D_Vector v, int oom, RoundingMode rm) {
            return new Line(this, v, oom, rm);
        }

        /**
         * @param l The line to test this with to see if they are parallel.
         * @param oom The Order of Magnitude for the precision.
         * @return {@code true} If this and {@code l} are parallel.
         */
        public boolean isParallel(Line l, int oom, RoundingMode rm) {
            return v.isScalarMultiple(l.v, oom, rm);
        }

        /**
         * @param pt A point to test for intersection.
         * @param oom The Order of Magnitude for the precision.
         * @return {@code true} if {@code pt} is on {@code this}.
         */
        public boolean isIntersectedBy(Point pt, int oom, RoundingMode rm) {
            V3D_Vector ppt = new V3D_Vector(pt.x.subtract(p.x),
                    pt.y.subtract(p.y), pt.z.subtract(p.z));
            V3D_Vector cp = v.getCrossProduct(ppt, oom, rm);
            return cp.dx.isZero() && cp.dy.isZero() && cp.dz.isZero();
        }

        /**
         * @param l The line.
         * @param oom The Order of Magnitude for the precision.
         * @return {@code true} if {@code this} and {@code l} intersect and
         * false if they may intersect, but more computation is needed.
         */
        protected boolean isIntersectedBy(Line l, int oom, RoundingMode rm) {
            if (V3D_Line.isCollinear(oom, rm, this, this.p, this.q, l.p)) {
                return true;
            } else {
                Plane pl = new Plane(this.p, this.q, l.p, oom, rm);
                if (V3D_Plane.isCoplanar(e, oom, rm, pl, l.q)) {
                    return true;
                }
            }
            if (V3D_Line.isCollinear(oom, rm, this, this.p, this.q, l.q)) {
                return true;
            } else {
                Plane pl = new Plane(this.p, this.q, l.q, oom, rm);
                return V3D_Plane.isCoplanar(e, oom, rm, pl, l.p);
            }
        }

        /**
         * Get the intersection between two lines.
         *
         * @param l Another line.
         * @param oom The Order of Magnitude for the precision.
         * @return The intersection between two lines or {@code null}.
         */
        public Geometry getIntersection(Line l, int oom, RoundingMode rm) {
            // Special case of parallel lines.
            if (isParallel(l, oom, rm)) {
                if (l.isIntersectedBy(p, oom, rm)) {
                    // If lines are coincident return this.
                    return this;
                } else {
                    return null;
                }
            }
            V3D_Vector plp = new V3D_Vector(p, l.p);
            V3D_Vector lqlp = new V3D_Vector(l.q, l.p);
            if (lqlp.getMagnitudeSquared().compareTo(Math_BigRational.ZERO) == 0) {
                if (isIntersectedBy(l.p, oom, rm)) {
                    return l.p;
                }
            }
            V3D_Vector qp = new V3D_Vector(q, p);
            if (qp.getMagnitudeSquared().compareTo(Math_BigRational.ZERO) == 0) {
                if (l.isIntersectedBy(p, oom, rm)) {
                    return p;
                }
            }
            Math_BigRational a = plp.dx.multiply(lqlp.dx, oom, rm).getSqrt(oom, rm)
                    .add(plp.dy.multiply(lqlp.dy, oom, rm).getSqrt(oom, rm))
                    .add(plp.dz.multiply(lqlp.dz, oom, rm).getSqrt(oom, rm));
            Math_BigRational b = lqlp.dx.multiply(qp.dx, oom, rm).getSqrt(oom, rm)
                    .add(lqlp.dy.multiply(qp.dy, oom, rm).getSqrt(oom, rm))
                    .add(lqlp.dz.multiply(qp.dz, oom, rm).getSqrt(oom, rm));
            Math_BigRational c = plp.dx.multiply(qp.dx, oom, rm).getSqrt(oom, rm)
                    .add(plp.dy.multiply(qp.dy, oom, rm).getSqrt(oom, rm))
                    .add(plp.dz.multiply(qp.dz, oom, rm).getSqrt(oom, rm));
            Math_BigRational d = lqlp.dx.multiply(lqlp.dx, oom, rm).getSqrt(oom, rm)
                    .add(lqlp.dy.multiply(lqlp.dy, oom, rm).getSqrt(oom, rm))
                    .add(lqlp.dz.multiply(lqlp.dz, oom, rm).getSqrt(oom, rm));
            Math_BigRational e = qp.dx.multiply(qp.dx, oom, rm).getSqrt(oom, rm)
                    .add(qp.dy.multiply(qp.dy, oom, rm).getSqrt(oom, rm))
                    .add(qp.dz.multiply(qp.dz, oom, rm).getSqrt(oom, rm));
            Math_BigRational den = (e.multiply(d)).subtract(b.multiply(b));
            Math_BigRational num = (a.multiply(b)).subtract(c.multiply(d));
            if (den.compareTo(Math_BigRational.ZERO) == 0) {
                if (num.compareTo(Math_BigRational.ZERO) == 0) {
                    Math_BigRational x;
                    Math_BigRational y;
                    Math_BigRational z;
                    Math_BigRational lamda;
                    Math_BigRational mu;
                    if (v.dx.isZero()) {
                        x = p.x;
                        if (l.v.dx.isZero()) {
                            if (v.dy.isZero()) {
                                y = p.y;
                                if (l.v.dy.isZero()) {
                                    z = p.z;
                                } else {
                                    if (v.dz.isZero()) {
                                        z = p.z;
                                    } else {
                                        if (l.v.dz.isZero()) {
                                            z = l.p.z;
                                        } else {
                                            mu = (p.y.subtract(l.p.y)).divide(l.v.getDY(oom, rm));
                                            z = l.p.z.add(l.v.getDZ(oom, rm).multiply(mu));
                                        }
                                    }
                                }
                            } else {
                                if (l.v.dy.isZero()) {
                                    y = l.p.y;
                                    if (v.dz.isZero()) {
                                        z = p.z;
                                    } else {
                                        if (l.v.dz.isZero()) {
                                            z = l.p.z;
                                        } else {
                                            lamda = (l.p.y.subtract(p.y)).divide(v.getDY(oom, rm));
                                            z = p.z.add(v.getDZ(oom, rm).multiply(lamda));
                                        }
                                    }
                                    //x = p.x;            
                                    //p.x + v.getDX(oom) * lamda = l.p.x + l.v.getDX(oom) * mu
                                    //p.y + v.getDY(oom) * lamda = l.p.y + l.v.getDY(oom) * mu
                                    //p.z + v.getDZ(oom) * lamda = l.p.z + l.v.getDZ(oom) * mu

                                } else {
                                    if (v.dz.isZero()) {
                                        z = p.z;
                                        mu = (p.z.subtract(l.p.z)).divide(l.v.getDY(oom, rm));
                                        y = l.p.y.add(l.v.getDY(oom, rm).multiply(mu));
                                    } else {
                                        if (l.v.dz.isZero()) {
                                            z = l.p.z;
                                            lamda = (l.p.z.subtract(p.z)).divide(v.getDY(oom, rm));
                                            y = p.y.add(v.getDY(oom, rm).multiply(lamda));
                                        } else {
                                            // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                        mu = ((p.y.add(v.getDY(oom).multiply(lamda))).subtract(l.p.y)).divide(l.v.getDY(oom));
//                                        lamda = ((l.p.z.subtract(p.z)).divide(v.getDZ(oom))).add(l.v.getDZ(oom).multiply(mu));
//                                        lamda = ((l.p.z.subtract(p.z)).divide(v.getDZ(oom))).add(l.v.getDZ(oom).multiply(((p.y.add(v.getDY(oom).multiply(lamda))).subtract(l.p.y)).divide(l.v.getDY(oom))));
//                                        l = ((bz-az)/adz) + (bdz*(ady*(l-by)/bdy))
//                                        l = ((bz-az)/adz) + bdz*ady*l/bdy - bdz*ady*by/bdy
//                                        l - bdz*ady*l/bdy = ((bz-az)/adz) - bdz*ady*by/bdy
//                                        l (1 - bdz*ady/bdy) = ((bz-az)/adz) - bdz*ady*by/bdy
//                                        l = (((bz-az)/adz) - bdz*ady*by/bdy)/(1 - bdz*ady/bdy)
                                            Math_BigRational den2 = Math_BigRational.ONE.subtract(l.v.getDZ(oom, rm).multiply(v.getDY(oom, rm).divide(l.v.getDY(oom, rm))));
                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                lamda = (((l.p.z.subtract(p.z)).divide(v.getDZ(oom, rm))).subtract(l.v.getDZ(oom, rm).multiply(v.getDY(oom, rm).multiply(l.p.y.divide(l.v.getDY(oom, rm)))))).divide(den2);
                                                z = p.z.add(v.getDZ(oom, rm).multiply(lamda));
                                                y = p.y.add(v.getDY(oom, rm).multiply(lamda));
                                            } else {
                                                den2 = Math_BigRational.ONE.subtract(l.v.getDY(oom, rm).multiply(v.getDZ(oom, rm).divide(l.v.getDZ(oom, rm))));
                                                if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                    lamda = (((l.p.y.subtract(p.y)).divide(v.getDY(oom, rm))).subtract(l.v.getDY(oom, rm).multiply(v.getDZ(oom, rm).multiply(l.p.z.divide(l.v.getDZ(oom, rm)))))).divide(den2);
                                                    z = p.z.add(v.getDZ(oom, rm).multiply(lamda));
                                                    y = p.y.add(v.getDY(oom, rm).multiply(lamda));
                                                } else {
                                                    // This should not happen!
                                                    z = null;
                                                    y = null;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            mu = (p.x.subtract(l.p.x)).divide(l.v.getDX(oom, rm));
                            if (v.dy.isZero()) {
                                if (l.v.dy.isZero()) {
                                    y = p.y;
                                    z = p.z;
                                } else {
                                    if (v.dz.isZero()) {
                                        y = l.p.y.add(l.v.getDY(oom, rm).multiply(mu));
                                    } else {
                                        y = p.y.add(v.getDY(oom, rm).multiply(mu));
                                    }
                                    if (l.v.dz.isZero()) {
                                        z = p.z;
                                    } else {
                                        z = l.p.z.add(l.v.getDZ(oom, rm).multiply(mu));
                                    }
                                }
                            } else {
                                lamda = ((l.p.y.add(l.v.getDY(oom, rm).multiply(mu)))
                                        .subtract(p.x)).divide(v.getDY(oom, rm));
                                if (v.dz.isZero()) {
                                    z = p.z;
                                } else {
                                    z = p.z.add(v.getDZ(oom, rm).multiply(lamda));
                                }
                                if (l.v.dy.isZero()) {
                                    y = p.y;
                                } else {
                                    y = l.p.y.add(l.v.getDY(oom, rm).multiply(mu));
                                }
                            }
                        }
                    } else {
                        if (l.v.dx.isZero()) {
                            lamda = l.p.x.subtract(p.x).divide(v.getDX(oom, rm));
                            x = l.p.x;
                            if (v.dy.isZero()) {
                                mu = (p.y.subtract(l.p.y)).divide(l.v.getDY(oom, rm));
                                y = p.y;
                                if (v.dz.isZero()) {
                                    z = p.z;
                                } else {
                                    if (l.v.dz.isZero()) {
                                        z = l.p.z;
                                    } else {
                                        z = l.p.z.add(l.v.getDZ(oom, rm).multiply(mu));
                                    }
                                }
                            } else {
                                if (v.dy.isZero()) {
                                    y = p.y;
                                    if (l.v.dy.isZero()) {
                                        if (v.dz.isZero()) {
                                            z = p.z;
                                        } else {
                                            if (l.v.dz.isZero()) {
                                                z = l.p.z;
                                            } else {
                                                mu = ((p.z.add(v.getDZ(oom, rm).multiply(lamda))).subtract(l.p.z)).divide(l.v.getDZ(oom, rm));
                                                z = l.p.z.add(l.v.getDZ(oom, rm).multiply(mu));
                                            }
                                        }
                                    } else {
                                        if (v.dz.isZero()) {
                                            z = p.z;
                                        } else {
                                            if (l.v.dz.isZero()) {
                                                z = l.p.z;
                                            } else {
                                                mu = (p.z.subtract(l.p.z)).divide(l.v.getDZ(oom, rm));
                                                z = l.p.z.add(l.v.getDZ(oom, rm).multiply(mu));
                                            }
                                        }
                                    }
                                } else {
                                    if (l.v.dy.isZero()) {
                                        y = l.p.y;
                                        if (v.dz.isZero()) {
                                            z = p.z;
                                        } else {
                                            if (l.v.dz.isZero()) {
                                                z = l.p.z;
                                            } else {
                                                mu = ((p.z.add(v.getDZ(oom, rm).multiply(lamda))).subtract(l.p.z)).divide(l.v.getDZ(oom, rm));
                                                z = l.p.z.add(l.v.getDZ(oom, rm).multiply(mu));
                                            }
                                        }
                                    } else {
                                        y = p.y.add(v.getDY(oom, rm).multiply(lamda));
                                        if (v.dz.isZero()) {
                                            z = p.z;
                                        } else {
                                            if (l.v.dz.isZero()) {
                                                z = l.p.z;
                                            } else {
                                                mu = ((p.z.add(v.getDZ(oom, rm).multiply(lamda))).subtract(l.p.z)).divide(l.v.getDZ(oom, rm));
                                                z = l.p.z.add(l.v.getDZ(oom, rm).multiply(mu));
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            // v.getDX(oom) > 0 && l.v.getDX(oom) > 0
                            if (v.dy.isZero()) {
                                y = p.y;
                                if (l.v.dy.isZero()) {
                                    if (v.dz.isZero()) {
                                        z = p.z;
                                        x = p.x;
                                    } else {
                                        if (l.v.dz.isZero()) {
                                            z = l.p.z;
                                            lamda = (l.p.z.subtract(p.z)).divide(v.getDZ(oom, rm));
                                            x = p.x.add(v.getDX(oom, rm).multiply(lamda));
                                        } else {
                                            // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                        mu = ((p.x.add(v.getDX(oom).multiply(lamda))).subtract(l.p.x)).divide(l.v.getDX(oom));
//                                        lamda = ((l.p.z.subtract(p.z)).divide(v.getDZ(oom))).add(l.v.getDZ(oom).multiply(mu));
//                                        lamda = ((l.p.z.subtract(p.z)).divide(v.getDZ(oom))).add(l.v.getDZ(oom).multiply(((p.x.add(v.getDX(oom).multiply(lamda))).subtract(l.p.x)).divide(l.v.getDX(oom))));
//                                        l = ((bz-az)/adz) + (bdz*(adx*(l-bx)/bdx))
//                                        l = ((bz-az)/adz) + bdz*adx*l/bdx - bdz*adx*bx/bdx
//                                        l - bdz*adx*l/bdx = ((bz-az)/adz) - bdz*adx*bx/bdx
//                                        l (1 - bdz*adx/bdx) = ((bz-az)/adz) - bdz*adx*bx/bdx
//                                        l = (((bz-az)/adz) - bdz*adx*bx/bdx)/(1 - bdz*adx/bdx)
                                            Math_BigRational den2 = Math_BigRational.ONE.subtract(l.v.getDZ(oom, rm).multiply(v.getDX(oom, rm).divide(l.v.getDX(oom, rm))));
                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                lamda = (((l.p.z.subtract(p.z)).divide(v.getDZ(oom, rm))).subtract(l.v.getDZ(oom, rm).multiply(v.getDX(oom, rm).multiply(l.p.x.divide(l.v.getDX(oom, rm)))))).divide(den2);
                                                z = p.z.add(v.getDZ(oom, rm).multiply(lamda));
                                                x = p.x.add(v.getDX(oom, rm).multiply(lamda));
                                            } else {
                                                den2 = Math_BigRational.ONE.subtract(l.v.getDX(oom, rm).multiply(v.getDZ(oom, rm).divide(l.v.getDZ(oom, rm))));
                                                if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                    lamda = (((l.p.x.subtract(p.x)).divide(v.getDX(oom, rm))).subtract(l.v.getDX(oom, rm).multiply(v.getDZ(oom, rm).multiply(l.p.z.divide(l.v.getDZ(oom, rm)))))).divide(den2);
                                                    z = p.z.add(v.getDZ(oom, rm).multiply(lamda));
                                                    x = p.x.add(v.getDX(oom, rm).multiply(lamda));
                                                } else {
                                                    // This should not happen!
                                                    z = null;
                                                    x = null;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    mu = p.y.subtract(l.p.y).divide(l.v.getDY(oom, rm));
                                    x = l.p.x.add(l.v.getDX(oom, rm).multiply(mu));
                                    z = l.p.z.add(l.v.getDZ(oom, rm).multiply(mu));
                                }
                            } else {
                                // v.getDX(oom) > 0 && l.v.getDX(oom) > 0 && v.getDY(oom) > 0
                                if (v.dz.isZero()) {
                                    z = p.z;
                                    if (l.v.dz.isZero()) {
                                        // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                    mu = ((p.x.add(v.getDX(oom).multiply(lamda))).subtract(l.p.x)).divide(l.v.getDX(oom));
//                                    lamda = ((l.p.y.subtract(p.y)).divide(v.getDY(oom))).add(l.v.getDY(oom).multiply(mu));
//                                    lamda = ((l.p.y.subtract(p.y)).divide(v.getDY(oom))).add(l.v.getDY(oom).multiply(((p.x.add(v.getDX(oom).multiply(lamda))).subtract(l.p.x)).divide(l.v.getDX(oom))));
//                                    l = ((by - ay) / ady) + (bdy * (adx * (l - bx) / bdx))
//                                    l = ((by - ay) / ady) + bdy * adx * l / bdx - bdy * adx * bx / bdx
//                                    l - bdy * adx * l / bdx = ((by - ay) / ady) - bdy * adx * bx / bdx
//                                    l(1 - bdy * adx / bdx) = ((by - ay) / ady) - bdy * adx * bx / bdx
//                                    l = (((by-ay)/ady) - bdy*adx*bx/bdx)/(1 - bdy*adx/bdx)
                                        Math_BigRational den2 = Math_BigRational.ONE.subtract(l.v.getDY(oom, rm).multiply(v.getDX(oom, rm).divide(l.v.getDX(oom, rm))));
                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                            lamda = (((l.p.y.subtract(p.y)).divide(v.getDY(oom, rm))).subtract(l.v.getDY(oom, rm).multiply(v.getDX(oom, rm).multiply(l.p.x.divide(l.v.getDX(oom, rm)))))).divide(den2);
                                            y = p.y.add(v.getDY(oom, rm).multiply(lamda));
                                            x = p.x.add(v.getDX(oom, rm).multiply(lamda));
                                        } else {
                                            den2 = Math_BigRational.ONE.subtract(l.v.getDX(oom, rm).multiply(v.getDY(oom, rm).divide(l.v.getDY(oom, rm))));
                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                lamda = (((l.p.x.subtract(p.x)).divide(v.getDX(oom, rm))).subtract(l.v.getDX(oom, rm).multiply(v.getDY(oom, rm).multiply(l.p.y.divide(l.v.getDY(oom, rm)))))).divide(den2);
                                                y = p.y.add(v.getDY(oom, rm).multiply(lamda));
                                                x = p.x.add(v.getDX(oom, rm).multiply(lamda));
                                            } else {
                                                // This should not happen!
                                                y = null;
                                                x = null;
                                            }
                                        }
                                    } else {
                                        mu = (p.z.subtract(l.p.z)).divide(l.v.getDZ(oom, rm));
                                        y = l.p.y.add(l.v.getDY(oom, rm).multiply(mu));
                                        x = l.p.x.add(l.v.getDX(oom, rm).multiply(mu));
                                    }
                                } else {
                                    if (l.v.dz.isZero()) {
                                        z = l.p.z;
                                        lamda = (l.p.z.subtract(p.z)).divide(v.getDZ(oom, rm));
                                        y = p.y.add(v.getDY(oom, rm).multiply(lamda));
                                        x = p.x.add(v.getDX(oom, rm).multiply(lamda));
                                    } else {
                                        // There are 6 ways to calculate lamda. One way should work! - If not try calculating mu.
                                        Math_BigRational den2 = Math_BigRational.ONE.subtract(l.v.getDY(oom, rm).multiply(v.getDX(oom, rm).divide(l.v.getDX(oom, rm))));
                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                            lamda = (((l.p.y.subtract(p.y)).divide(v.getDY(oom, rm))).subtract(l.v.getDY(oom, rm).multiply(v.getDX(oom, rm).multiply(l.p.x.divide(l.v.getDX(oom, rm)))))).divide(den2);
                                            x = p.x.add(v.getDX(oom, rm).multiply(lamda));
                                            y = p.y.add(v.getDY(oom, rm).multiply(lamda));
                                            z = p.z.add(v.getDZ(oom, rm).multiply(lamda));
//                                        x = q.x.add(v.getDX(oom, rm).multiply(lamda));
//                                        y = q.y.add(v.getDY(oom, rm).multiply(lamda));
//                                        z = q.z.add(v.getDZ(oom, rm).multiply(lamda));
                                        } else {
                                            den2 = Math_BigRational.ONE.subtract(l.v.getDY(oom, rm).multiply(v.getDZ(oom, rm).divide(l.v.getDZ(oom, rm))));
                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                lamda = (((l.p.y.subtract(p.y)).divide(v.getDY(oom, rm))).subtract(l.v.getDY(oom, rm).multiply(v.getDZ(oom, rm).multiply(l.p.z.divide(l.v.getDZ(oom, rm)))))).divide(den2);
                                                x = p.x.add(v.getDX(oom, rm).multiply(lamda));
                                                y = p.y.add(v.getDY(oom, rm).multiply(lamda));
                                                z = p.z.add(v.getDZ(oom, rm).multiply(lamda));
                                            } else {
                                                den2 = Math_BigRational.ONE.subtract(l.v.getDZ(oom, rm).multiply(v.getDX(oom, rm).divide(l.v.getDX(oom, rm))));
                                                if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                    lamda = (((l.p.z.subtract(p.z)).divide(v.getDZ(oom, rm))).subtract(l.v.getDZ(oom, rm).multiply(v.getDX(oom, rm).multiply(l.p.x.divide(l.v.getDX(oom, rm)))))).divide(den2);
                                                    x = p.x.add(v.getDX(oom, rm).multiply(lamda));
                                                    y = p.y.add(v.getDY(oom, rm).multiply(lamda));
                                                    z = p.z.add(v.getDZ(oom, rm).multiply(lamda));
                                                } else {
                                                    den2 = Math_BigRational.ONE.subtract(l.v.getDZ(oom, rm).multiply(v.getDY(oom, rm).divide(l.v.getDY(oom, rm))));
                                                    if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                        lamda = (((l.p.z.subtract(p.z)).divide(v.getDZ(oom, rm))).subtract(l.v.getDZ(oom, rm).multiply(v.getDY(oom, rm).multiply(l.p.y.divide(l.v.getDY(oom, rm)))))).divide(den2);
                                                        x = p.x.add(v.getDX(oom, rm).multiply(lamda));
                                                        y = p.y.add(v.getDY(oom, rm).multiply(lamda));
                                                        z = p.z.add(v.getDZ(oom, rm).multiply(lamda));
                                                    } else {
                                                        den2 = Math_BigRational.ONE.subtract(l.v.getDX(oom, rm).multiply(v.getDX(oom, rm).divide(l.v.getDY(oom, rm))));
                                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                            lamda = (((l.p.x.subtract(p.x)).divide(v.getDX(oom, rm))).subtract(l.v.getDX(oom, rm).multiply(v.getDY(oom, rm).multiply(l.p.y.divide(l.v.getDY(oom, rm)))))).divide(den2);
                                                            x = p.x.add(v.getDX(oom, rm).multiply(lamda));
                                                            y = p.y.add(v.getDY(oom, rm).multiply(lamda));
                                                            z = p.z.add(v.getDZ(oom, rm).multiply(lamda));
                                                        } else {
                                                            den2 = Math_BigRational.ONE.subtract(l.v.getDX(oom, rm).multiply(v.getDX(oom, rm).divide(l.v.getDZ(oom, rm))));
                                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                                lamda = (((l.p.x.subtract(p.x)).divide(v.getDX(oom, rm))).subtract(l.v.getDX(oom, rm).multiply(v.getDZ(oom, rm).multiply(l.p.z.divide(l.v.getDZ(oom, rm)))))).divide(den2);
                                                                x = p.x.add(v.getDX(oom, rm).multiply(lamda));
                                                                y = p.y.add(v.getDY(oom, rm).multiply(lamda));
                                                                z = p.z.add(v.getDZ(oom, rm).multiply(lamda));
                                                            } else {
                                                                // This should not happen!
                                                                x = null;
                                                                y = null;
                                                                z = null;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //p.x + v.getDX(oom) * lamda = l.p.x + l.v.getDX(oom) * mu
                    //p.y + v.getDY(oom) * lamda = l.p.y + l.v.getDY(oom) * mu
                    //p.z + v.getDZ(oom) * lamda = l.p.z + l.v.getDZ(oom) * mu
                    return new Point(this.e, x, y, z);
                }
                return null;
            }
            Math_BigRational mua = num.divide(den);
            Math_BigRational mub = (a.add(b.multiply(mua))).divide(d).negate();
            Point pi = new Point(this.e,
                    //                (p.x.add(mua.multiply(qp.getDX(oom)))),
                    //                (p.y.add(mua.multiply(qp.getDY(oom)))),
                    //                (p.z.add(mua.multiply(qp.getDZ(oom)))));
                    (p.x.subtract(mua.multiply(qp.getDX(oom, rm)))),
                    (p.y.subtract(mua.multiply(qp.getDY(oom, rm)))),
                    (p.z.subtract(mua.multiply(qp.getDZ(oom, rm)))));
            // If point p is on both lines then return this as the intersection.
            if (isIntersectedBy(pi, oom, rm) && l.isIntersectedBy(pi, oom, rm)) {
                return pi;
            }
            Point qi = new Point(this.e,
                    (l.p.x.add(mub.multiply(lqlp.getDX(oom, rm)))),
                    (l.p.y.add(mub.multiply(lqlp.getDY(oom, rm)))),
                    (l.p.z.add(mub.multiply(lqlp.getDZ(oom, rm)))));
            // If point q is on both lines then return this as the intersection.
            if (isIntersectedBy(qi, oom, rm) && l.isIntersectedBy(qi, oom, rm)) {
                return qi;
            }
            /**
             * The only time when pi and qi should be different is when the
             * lines do not intersect. In this case pi and qi are meant to be
             * the end points of the shortest line between the two lines.
             */
            if (pi.equals(qi)) {
                return pi;
            } else {
                return null;
            }
        }

        /**
         * https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
         *
         * @param p A point for which the minimum distance from {@code this} is
         * returned.
         *
         * @param oom, rm The Order of Magnitude for the precision of the result.
         * @return The minimum distance between this and {@code p}.
         */
        public BigDecimal getDistance(Point p, int oom, RoundingMode rm) {
//            V3D_Vector pv = new V3D_Vector(this.p, p);
//            V3D_Vector vu = v.getUnitVector(oom - 2);
//            BigDecimal pd = p.getDistance(new Point(this.e, vu.multiply(
//                    pv.getDotProduct(vu, oom), oom)
//                    .add(new V3D_Vector(this.p), oom)), oom);
//            pv = new V3D_Vector(this.q, p);
//            vu = v.reverse().getUnitVector(oom - 2);
//            BigDecimal qd = p.getDistance(new Point(this.e, vu.multiply(
//                    pv.getDotProduct(vu, oom), oom)
//                    .add(new V3D_Vector(this.q), oom)), oom);
//            return pd.min(qd);
            Math_BigRational d2 = getDistanceSquared(p, oom, rm);
            return (new Math_BigRationalSqrt(d2, oom, rm)).getSqrt(oom, rm).toBigDecimal(oom, rm);
        }

        /**
         * https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
         *
         * @param p A point for which the minimum distance from {@code this} is
         * returned.
         *
         * @param oom The Order of Magnitude for the precision of the result.
         * @return The minimum distance between this and {@code p}.
         */
        public Math_BigRational getDistanceSquared(Point p, int oom, RoundingMode rm) {
            V3D_Vector pv = new V3D_Vector(this.p, p);
            V3D_Vector vu = v.getUnitVector(oom - 2, rm);
            Math_BigRational pd = p.getDistanceSquared(new Point(this.e,
                            vu.multiply(pv.getDotProduct(vu, oom, rm), oom, rm)
                                    .add(new V3D_Vector(this.p), oom, rm), oom, rm));
            pv = new V3D_Vector(this.q, p);
            vu = v.reverse().getUnitVector(oom - 2, rm);
            Math_BigRational qd = p.getDistanceSquared(
                    new Point(this.e, vu.multiply(pv.getDotProduct(vu, oom, rm),
                            oom, rm).add(new V3D_Vector(this.q), oom, rm), oom, rm));
            return pd.min(qd);
        }

        /**
         *
         * @param l A line for which the minimum distance from {@code this} is
         * returned.
         * @param oom The Order of Magnitude for the precision of the result.
         * @return The minimum distance between this and {@code l}.
         */
        public BigDecimal getDistance(Line l, int oom, RoundingMode rm) {
            if (isParallel(l, oom, rm)) {
                return l.getDistance(p, oom, rm);
            } else {
                /**
                 * Calculate the direction vector of the line connecting the
                 * closest points by computing the cross product.
                 */
                V3D_Vector cp = v.getCrossProduct(l.v, oom, rm);
                /**
                 * Calculate the delta from {@link #p} and l.p
                 */
                V3D_Vector delta = new V3D_Vector(p)
                        .subtract(new V3D_Vector(l.p), oom, rm);
                //Math_BigRational m = Math_BigRational.valueOf(cp.getMagnitude(oom - 2));
                Math_BigRationalSqrt m = cp.getMagnitude();
                // d = cp.(delta)/m
                Math_BigRational dp = cp.getDotProduct(delta, oom, rm);
                // m should only be zero if the lines are parallel.
                Math_BigRational d = dp.divide(m.getX());
                return d.toBigDecimal(oom, rm);
            }
        }
    }

    /**
     * An axis aligned line segment.
     */
    public class LineSegment extends Line {

        private static final long serialVersionUID = 1L;

        /**
         * Create a new instance from {@code l} and {@code v}.
         *
         * @param l The line to duplicate.
         * @param v The vector to tranlsate immediately to {@code p}.
         */
        public LineSegment(LineSegment l, V3D_Vector v, int oom, RoundingMode rm) {
            super(new Point(l.p, v, oom, rm), new Point(l.q, v, oom, rm));
        }

        /**
         * @param p What {@link #p} is set to.
         * @param q What {@link #q} is set to.
         */
        public LineSegment(Point p, Point q) {
            super(p, q);
        }

        /**
         * @param pt A point to test for intersection.
         * @param oom The Order of Magnitude for the precision.
         * @return {@code true} if {@code pt} intersects with {@code this}.
         */
        @Override
        public boolean isIntersectedBy(Point pt, int oom, RoundingMode rm) {
            if (super.isIntersectedBy(pt, oom, rm)) {
                Math_BigRationalSqrt a = pt.getDistanceExact(p, oom, rm);
                if (a.getX().isZero()) {
                    return true;
                }
                Math_BigRationalSqrt b = pt.getDistanceExact(q, oom, rm);
                if (b.getX().isZero()) {
                    return true;
                }
                Math_BigRationalSqrt l = this.p.getDistanceExact(q, oom, rm);
                if (a.add(b, oom, rm).compareTo(l) != 1) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Intersects {@code this} with {@code l}. If they are equivalent then
         * return {@code this}. If they overlap in a line return the part that
         * overlaps (the order of points is not defined). If they intersect at a
         * point, the point is returned. {@code null} is returned if the two
         * line segments do not intersect.
         *
         * @param l The line to get intersection with this.
         * @param oom The Order of Magnitude for the precision.
         * @return The intersection between {@code this} and {@code l}.
         */
        @Override
        public Geometry getIntersection(Line l, int oom, RoundingMode rm) {
            // Check if infinite lines intersect.
            Geometry li = l.getIntersection(new Line(this), oom, rm);
            if (li == null) {
                // There is no intersection.
                return li;
            }
            /**
             * If infinite lines intersects at a point, then check this point is
             * on this.
             */
            if (li instanceof Point point) {
                if (isIntersectedBy(point, oom, rm)) {
                    return li;
                } else {
                    return null;
                }
            } else if (li instanceof Line) {
                // If the lines are the same, then return this. 
                return this;
            } else {
                // There is no intersection.
                return null;
            }
        }

        @Override
        protected LineSegment translate(V3D_Vector v, int oom, RoundingMode rm) {
            return new LineSegment(this, v, oom, rm);
        }
    }

    /**
     * A plane aligning with the axes.
     */
    public class Plane extends Geometry {

        private static final long serialVersionUID = 1L;

        /**
         * One of the points that defines the plane.
         */
        protected final Point p;

        /**
         * One of the points that defines the plane.
         */
        protected final Point q;

        /**
         * One of the points that defines the plane.
         */
        protected final Point r;

        /**
         * The vector representing the move from {@link #p} to {@link #q}.
         */
        protected final V3D_Vector pq;

        /**
         * The vector representing the move from {@link #q} to {@link #r}.
         */
        protected final V3D_Vector qr;

        /**
         * The normal vector. (This is perpendicular to the plane and it's
         * direction is given by order in which the two vectors {@link #pq} and
         * {@link #qr} are used in a cross product calculation when the plane is
         * constructed.
         */
        protected final V3D_Vector n;

        /**
         * Create a new instance.
         *
         * @param p The plane used to create this.
         */
        public Plane(Plane p) {
            this(p.p, p.q, p.r, p.pq, p.qr, p.n);
        }

        /**
         * Create a new instance. Immediately applying v to p.
         *
         * @param p The plane used to create this.
         * @param v The vector used to create this.
         */
        public Plane(Plane p, V3D_Vector v, int oom, RoundingMode rm) {
            this(new Point(p.p, v, oom, rm), new Point(p.q, v, oom, rm), 
                    new Point(p.r, v, oom, rm), oom, rm);
        }

        /**
         * Create a new instance.
         *
         * @param p What {@link #p} is set to.
         * @param q What {@link #q} is set to.
         * @param r What {@link #r} is set to.
         * @param pq What {@link #pq} is set to.
         * @param qr What {@link #qr} is set to.
         * @param n What {@link #n} is set to.
         */
        public Plane(Point p, Point q, Point r, V3D_Vector pq, V3D_Vector qr,
                V3D_Vector n) {
            super(p.e);
            this.p = p;
            this.q = q;
            this.r = r;
            this.pq = pq;
            this.qr = qr;
            this.n = n;
        }

        /**
         * Create a new instance.
         *
         * @param p What {@link #p} is set to.
         * @param q What {@link #q} is set to.
         * @param r What {@link #r} is set to.
         */
        public Plane(Point p, Point q, Point r, int oom, RoundingMode rm) {
            this(p, q, r, new V3D_Vector(p, q), new V3D_Vector(q, r),
                    new V3D_Vector(p, q).getCrossProduct(
                            new V3D_Vector(q, r), oom, rm));
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "(p=" + p.toString()
                    + ", q=" + q.toString() + ", r=" + r.toString()
                    + ", n=" + n.toString() + ")";
        }

        @Override
        protected Plane translate(V3D_Vector v, int oom, RoundingMode rm) {
            return new Plane(this, v, oom, rm);
        }

        /**
         * @param l The line to test if it is parallel to this.
         * @return {@code true} if {@code this} is parallel to {@code l}.
         */
        public boolean isParallel(Line l, int oom, RoundingMode rm) {
            return n.getDotProduct(l.v, oom, rm).isZero();
        }

        /**
         * @param l The line to test if it is on the plane.
         * @param oom The Order of Magnitude for the precision.
         * @return {@code true} If {@code pt} is on the plane.
         */
        public boolean isOnPlane(Line l, int oom, RoundingMode rm) {
            return isIntersectedBy(l.p, oom, rm) && isIntersectedBy(l.q, oom, rm);
        }

        /**
         * @param pt The point to test if it is on the plane.
         * @param oom The Order of Magnitude for the precision.
         * @return {@code true} If {@code pt} is on the plane.
         */
        public boolean isIntersectedBy(Point pt, int oom, RoundingMode rm) {
            Math_BigRational d = n.dx.multiply(p.x.subtract(pt.x), oom, rm)
                    .add(n.dy.multiply(p.y.subtract(pt.y), oom, rm), oom, rm)
                    .add(n.dz.multiply(p.z.subtract(pt.z), oom, rm), oom, rm).getSqrt(oom, rm);
            return d.compareTo(Math_BigRational.ZERO) == 0;
        }

        /**
         * @param l The line to test for intersection with this.
         * @param oom The Order of Magnitude for the precision.
         * @return {@code true} If this and {@code l} intersect.
         */
        public boolean isIntersectedBy(Line l, int oom, RoundingMode rm) {
            if (isParallel(l, oom, rm)) {
                if (!isOnPlane(l, oom, rm)) {
                    return false;
                }
            }
            return true;
        }

        /**
         * https://en.wikipedia.org/wiki/Line%E2%80%93plane_intersection
         *
         * @param l The line to intersect with the plane.
         * @param oom The Order of Magnitude for the precision.
         * @return The intersection of the line and the plane. This is either
         * {@code null} a line or a point.
         */
        public Geometry getIntersection(Line l, int oom, RoundingMode rm) {
            if (isParallel(l, oom, rm)) {
                if (isOnPlane(l, oom, rm)) {
                    return l;
                } else {
                    return null;
                }
            }
            // Are either of the points of l on the plane.
            if (isIntersectedBy(l.p, oom, rm)) {
                return l.p;
            }
            if (isIntersectedBy(l.q, oom, rm)) {
                return l.q;
            }
            Math_BigRational num = new V3D_Vector(p, l.p).getDotProduct(n, oom, rm);
            Math_BigRational den = l.v.getDotProduct(n, oom, rm);
            Math_BigRational t = num.divide(den);
            return new Point(this.e,
                    l.p.x.subtract(l.v.dx.multiply(t, oom, rm).getSqrt(oom, rm)),
                    l.p.y.subtract(l.v.dy.multiply(t, oom, rm).getSqrt(oom, rm)),
                    l.p.z.subtract(l.v.dz.multiply(t, oom, rm).getSqrt(oom, rm)));
        }

        /**
         * Get the distance between this and {@code pl}.
         *
         * @param p A point.
         * @param oom The Order of Magnitude for the precision.
         * @return The distance from {@code this} to {@code p}.
         */
        public BigDecimal getDistance(Point p, int oom, RoundingMode rm) {
            if (this.isIntersectedBy(p, oom, rm)) {
                return BigDecimal.ZERO;
            }
            Math_BigRational d2 = getDistanceSquared(p, true, oom, rm);
//        MathContext mc = new MathContext(Math_BigRationalSqrt
//                .getOOM(Math_BigRational.ONE, oom));
            MathContext mc = new MathContext(6 - oom);
            return d2.toBigDecimal(mc);
        }

        /**
         * Get the distance between this and {@code p}.
         *
         * @param p A point.
         * @param oom The Order of Magnitude for the precision.
         * @return The distance from {@code this} to {@code p}.
         */
        public Math_BigRational getDistanceSquared(Point p, int oom,
                RoundingMode rm) {
            if (this.isIntersectedBy(p, oom, rm)) {
                return Math_BigRational.ZERO;
            }
            return getDistanceSquared(p, true, oom, rm);
        }

        /**
         * Get the distance between this and {@code p}.
         *
         * @param p A point.
         * @param noInt To distinguish this from
         * {@link #getDistanceSquared(uk.ac.leeds.ccg.v3d.geometry.V3D_Envelope.Point, int)}
         * @param oom The Order of Magnitude for the precision.
         * @return The distance from {@code this} to {@code p}.
         */
        protected Math_BigRational getDistanceSquared(Point p, boolean noInt,
                int oom, RoundingMode rm) {
            V3D_Vector v = new V3D_Vector(p, this.p);
            V3D_Vector u = this.n.getUnitVector(oom, rm);
            return v.getDotProduct(u, oom, rm).abs();
        }
    }

    /**
     * This rectangle is aligned with the axes.
     *
     * Like with {@link V3D_Rectangle}, the corner points {@link #p}, {@link #q},
     * {@link #r}, {@link #s} are rectangular and {@link #pq} is assumed to be
     * orthogonal to {@link #qr}. The left of a rectangle {@link #l} is the line
     * segment from {@link #p} to {@link #q}. The top of a rectangle {@link #t}
     * is the line segment from {@link #q} to {@link #r}. The right of a
     * rectangle {@link #ri} is the line segment from {@link #r} to {@link #s}.
     * The bottom of a rectangle {@link #b} is the line segment from {@link #s}
     * to {@link #p}. The following depicts a generic rectangle {@code
     *          t
     * q *-------------* r
     *   |             |
     * l |             | ri
     *   |             |
     * p *-------------* s
     *          b
     * }
     */
    public class Rectangle extends Plane {

        private static final long serialVersionUID = 1L;

        /**
         * The other corner of the rectangle. The others are
         * {@link #p}, {@link #q}, and {@link #r}.
         */
        protected final Point s;

        /**
         * For storing the vector from {@link #p} to {@link #q}.
         */
        protected final LineSegment l;

        /**
         * For storing the line segment from {@link #q} to {@link #r}.
         */
        protected final LineSegment t;

        /**
         * For storing the line segment from {@link #r} to {@link #s}.
         */
        protected final LineSegment ri;

        /**
         * For storing the line segment from {@link #s} to {@link #p}.
         */
        protected final LineSegment b;

        /**
         * Create a new instance. Immediately applying v to p.
         *
         * @param r The rectangle used to create this.
         * @param v The vector used to create this.
         */
        public Rectangle(Rectangle r, V3D_Vector v, int oom, RoundingMode rm) {
            this(new Point(r.p, v, oom, rm), new Point(r.q, v, oom, rm), 
                    new Point(r.r, v, oom, rm), new Point(r.s, v, oom, rm), oom, rm);
        }

        /**
         * @param p The bottom left corner of the rectangle.
         * @param q The top left corner of the rectangle.
         * @param r The top right corner of the rectangle.
         * @param s The bottom right corner of the rectangle.
         * @throws java.lang.RuntimeException iff the points do not define a
         * rectangle.
         */
        public Rectangle(Point p, Point q, Point r, Point s, int oom, RoundingMode rm) {
            super(p, q, r, oom, rm);
            this.s = s;
            //en = new V3D_Envelope(p, q, r, s); Not initialised here as it causes a StackOverflowError
            l = new LineSegment(p, q);
            t = new LineSegment(q, r);
            ri = new LineSegment(r, s);
            b = new LineSegment(s, p);
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "(p=" + p.toString()
                    + ", q=" + q.toString() + ", r=" + r.toString()
                    + ", s=" + s.toString() + ")";
        }

        @Override
        protected Rectangle translate(V3D_Vector v, int oom, RoundingMode rm) {
            return new Rectangle(this, v, oom, rm);
        }

        /**
         * @param l The line to intersect with.
         * @return A point or line segment.
         */
        @Override
        public Geometry getIntersection(Line l, int oom, RoundingMode rm) {
            Geometry g = new Plane(this).getIntersection(l, oom, rm);
            if (g == null) {
                return null;
            } else {
                if (g instanceof Point point) {
                    if (this.isIntersectedBy(point, oom, rm)) {
                        return g;
                    } else {
                        return null;
                    }
                } else {
                    Line li = (Line) g;
                    /**
                     * Get the intersection of the line and each edge of the
                     * rectangle.
                     */
                    Geometry ti = t.getIntersection(li, oom, rm);
                    if (ti == null) {
                        // Check ri, b, l
                        Geometry rii = ri.getIntersection(li, oom, rm);
                        if (rii == null) {
                            // Check b, l
                            Geometry bi = b.getIntersection(li, oom, rm);
                            if (bi == null) {
                                // Check l
                                Geometry tli = this.l.getIntersection(li, oom, rm);
                                if (tli == null) {
                                    return null;
                                } else {
                                    return tli;
                                }
                            } else if (bi instanceof LineSegment) {
                                return bi;
                            } else {
                                // Check l
                                Geometry tli = this.l.getIntersection(li, oom, rm);
                                if (tli == null) {
                                    return bi;
                                } else {
                                    return new LineSegment((Point) bi,
                                            (Point) tli);
                                }
                            }
                        } else if (rii instanceof LineSegment) {
                            return rii;
                        } else {
                            // Check b, l
                            Geometry bi = b.getIntersection(li, oom, rm);
                            if (bi == null) {
                                // Check l
                                Geometry tli = this.l.getIntersection(li, oom, rm);
                                if (tli == null) {
                                    return rii;
                                } else {
                                    return new LineSegment((Point) rii,
                                            (Point) tli);
                                }
                            } else if (bi instanceof LineSegment) {
                                return bi;
                            } else {
                                // Check l
                                Geometry tli = this.l.getIntersection(li, oom, rm);
                                if (tli == null) {
                                    Point riip = (Point) rii;
                                    Point bip = (Point) bi;
                                    if (riip.equals(bip)) {
                                        return bip;
                                    } else {
                                        return new LineSegment(riip, bip);
                                    }
                                } else {
                                    return new LineSegment((Point) bi,
                                            (Point) tli);
                                }
                            }
                        }
                    } else if (ti instanceof LineSegment) {
                        return ti;
                    } else {
                        // Check ri, b, l
                        Geometry rii = ri.getIntersection(li, oom, rm);
                        if (rii == null) {
                            // Check b, l
                            Geometry bi = b.getIntersection(li, oom, rm);
                            if (bi == null) {
                                // Check l
                                Geometry tli = this.l.getIntersection(li, oom, rm);
                                if (tli == null) {
                                    return ti;
                                } else {
                                    Point tlip = (Point) tli;
                                    Point tip = (Point) ti;
                                    if (tlip.equals(tip)) {
                                        return tlip;
                                    } else {
                                        return new LineSegment(tlip, tip);
                                    }
                                }
                            } else if (bi instanceof LineSegment) {
                                return bi;
                            } else {
                                return new LineSegment((Point) ti, (Point) bi);
                            }
                        } else {
                            Point tip = (Point) ti;
                            Point riip = (Point) rii;
                            if (tip.equals(riip)) {
                                // Check b, l
                                Geometry sri = b.getIntersection(li, oom, rm);
                                if (sri == null) {
                                    // Check l
                                    Geometry tli = this.l.getIntersection(li, oom, rm);
                                    if (tli == null) {
                                        return rii;
                                    } else {
                                        return new LineSegment(riip, (Point) tli);
                                    }
                                } else if (sri instanceof LineSegment) {
                                    return sri;
                                } else {
                                    return new LineSegment(riip, (Point) sri);
                                }
                            } else {
                                return new LineSegment(riip, tip);
                            }
                        }
                    }
                }
            }
        }
    }
}
