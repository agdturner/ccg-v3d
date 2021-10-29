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
import java.util.Objects;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.geometrics.V3D_Geometrics;

/**
 * An envelope contains all the extreme values with respect to the X, Y and Z
 * axes. It is an axis aligned bounding box, which may have length of zero in
 * any direction. For a point the envelope is essentially the point. The
 * envelope may also be a line. In any case it has:
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
public class V3D_Envelope extends V3D_Geometry implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * @param oom The Order of Magnitude used in the calculation of the
     * magnitude of vectors.
     */
    private final int oom;

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
     * @param e An envelope.
     */
    public V3D_Envelope(V3D_Envelope e) {
        xMin = e.xMin;
        xMax = e.xMax;
        yMin = e.yMin;
        yMax = e.yMax;
        zMin = e.zMin;
        zMax = e.zMax;
        f = e.f;
        l = e.l;
        a = e.a;
        r = e.r;
        t = e.t;
        b = e.b;
        type = e.type;
        this.oom = e.oom;
    }

    /**
     * @param points The points used to form the envelop.
     * @param oom The Order of Magnitude used in the calculation of the
     * magnitude of the vectors {@link #pq}, {@link #qr}, {@link #n}.
     */
    public V3D_Envelope(int oom, V3D_Point... points) {
        this.oom = oom;
        int len = points.length;
        switch (len) {
            case 0:
                throw new RuntimeException("Cannot create envelope from an empty "
                        + "collection of points.");
            case 1:
                xMin = points[0].x;
                xMax = points[0].x;
                yMin = points[0].y;
                yMax = points[0].y;
                zMin = points[0].z;
                zMax = points[0].z;
                f = new Point(points[0]);
                l = f;
                a = f;
                r = f;
                t = f;
                b = f;
                type = 0; // f, l, a, r, t, b Point
                break;
            default:
                Math_BigRational xmin = points[0].x;
                Math_BigRational xmax = points[0].x;
                Math_BigRational ymin = points[0].y;
                Math_BigRational ymax = points[0].y;
                Math_BigRational zmin = points[0].z;
                Math_BigRational zmax = points[0].z;
                for (int i = 1; i < points.length; i++) {
                    xmin = Math_BigRational.min(xmin, points[i].x);
                    xmax = Math_BigRational.max(xmax, points[i].x);
                    ymin = Math_BigRational.min(ymin, points[i].y);
                    ymax = Math_BigRational.max(ymax, points[i].y);
                    zmin = Math_BigRational.min(zmin, points[i].z);
                    zmax = Math_BigRational.max(zmax, points[i].z);
                }
                if (xmin.compareTo(xmax) == 0) {
                    if (ymin.compareTo(ymax) == 0) {
                        if (zmin.compareTo(zmax) == 0) {
                            f = new Point(xmin, ymin, zmin);
                            l = f;
                            a = f;
                            r = f;
                            t = f;
                            b = f;
                            type = 0; // f, l, a, r, t, b Point
                        } else {
                            Point fp = new Point(xmin, ymin, zmin);
                            Point ap = new Point(xmin, ymax, zmin);
                            f = fp;
                            l = new LineSegment(ap, fp, oom);
                            a = ap;
                            r = new LineSegment(fp, ap, oom);
                            t = r;
                            b = l;
                            type = 1; // f, a Point; l, r, t, b LineSegment
                        }
                    } else {
                        Point bf = new Point(xmin, ymin, zmin);
                        Point tf = new Point(xmin, ymax, zmin);
                        if (zmin.compareTo(zmax) == 0) {
                            f = new LineSegment(bf, tf, oom);
                            l = f;
                            a = f;
                            r = f;
                            t = tf;
                            b = bf;
                            type = 3; // t, b Point; f, l, a, r LineSegment
                        } else {
                            Point ta = new Point(xmin, ymax, zmax);
                            Point ba = new Point(xmin, ymin, zmax);
                            f = new LineSegment(bf, tf, oom);
                            l = new Rectangle(ba, ta, tf, bf, oom);
                            a = new LineSegment(ba, ta, oom);
                            r = new Rectangle(bf, tf, ta, ba, oom);
                            t = new LineSegment(tf, ta, oom);
                            b = new LineSegment(ba, bf, oom);
                            type = 5; // 1, r Rectangle; f, a, t, b LineSegment
                        }
                    }
                } else {
                    if (ymin.compareTo(ymax) == 0) {
                        Point lf = new Point(xmin, ymin, zmin);
                        Point rf = new Point(xmax, ymin, zmin);
                        if (zmin.compareTo(zmax) == 0) {
                            f = new LineSegment(lf, rf, oom);
                            l = lf;
                            a = new LineSegment(rf, lf, oom);
                            r = rf;
                            t = f;
                            b = f;
                            type = 2; // 1, r Point; f, a, t, b LineSegment
                        } else {
                            Point la = new Point(xmin, ymin, zmax);
                            Point ra = new Point(xmax, ymin, zmax);
                            f = new LineSegment(lf, rf, oom);
                            l = new LineSegment(la, lf, oom);
                            a = new LineSegment(ra, la, oom);
                            r = new LineSegment(rf, ra, oom);
                            t = new Rectangle(lf, la, ra, rf, oom);
                            b = new Rectangle(la, lf, rf, ra, oom);
                            type = 6; // t, b Rectangle; f, l, a, r LineSegment
                        }
                    } else {
                        Point lbf = new Point(xmin, ymin, zmin);
                        Point ltf = new Point(xmin, ymax, zmin);
                        Point rtf = new Point(xmax, ymax, zmin);
                        Point rbf = new Point(xmax, ymin, zmin);
                        if (zmin.compareTo(zmax) == 0) {
                            f = new Rectangle(lbf, ltf, rtf, rbf, oom);
                            l = new LineSegment(lbf, ltf, oom);
                            a = new Rectangle(rbf, rtf, ltf, lbf, oom);
                            r = new LineSegment(rbf, rtf, oom);
                            t = new LineSegment(ltf, rtf, oom);
                            b = new LineSegment(lbf, rbf, oom);
                            type = 4; // f, a Rectangle; l, r, t, b LineSegment
                        } else {
                            Point lba = new Point(xmin, ymin, zmax);
                            Point lta = new Point(xmin, ymax, zmax);
                            Point rta = new Point(xmax, ymax, zmax);
                            Point rba = new Point(xmax, ymin, zmax);
                            f = new Rectangle(lbf, ltf, rtf, rbf, oom);
                            l = new Rectangle(lba, lta, ltf, lbf, oom);
                            a = new Rectangle(rba, rta, lta, lba, oom);
                            r = new Rectangle(rbf, rtf, rta, rba, oom);
                            t = new Rectangle(ltf, lta, rta, rtf, oom);
                            b = new Rectangle(lba, lbf, rbf, rba, oom);
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
                break;
        }
    }

    /**
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     * @param z The z-coordinate of a point.
     */
    public V3D_Envelope(Math_BigRational x, Math_BigRational y, Math_BigRational z, int oom) {
        this(oom, new V3D_Point(x, y, z));
    }

    /**
     * @param xMin What {@link xMin} is set to.
     * @param xMax What {@link xMax} is set to.
     * @param yMin What {@link yMin} is set to.
     * @param yMax What {@link yMax} is set to.
     * @param zMin What {@link zMin} is set to.
     * @param zMax What {@link zMax} is set to.
     */
    public V3D_Envelope(Math_BigRational xMin, Math_BigRational xMax, Math_BigRational yMin,
            Math_BigRational yMax, Math_BigRational zMin, Math_BigRational zMax, int oom) {
        this(oom, new V3D_Point(xMin, yMin, zMin), new V3D_Point(xMax, yMax, zMax));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "(xMin=" + getxMin().toString() + ", xMax=" + getxMax().toString()
                + ", yMin=" + getyMin().toString() + ", yMax=" + getyMax().toString()
                + ", zMin=" + getzMin().toString() + ", zMax=" + getzMax().toString() + ")";
    }

    /**
     * @param v The vector to apply.
     * @return a new V3D_Envelope.
     */
    @Override
    public V3D_Envelope apply(V3D_Vector v, int oom) {
        V3D_Point lbf = new V3D_Point(xMin, yMin, zMax).apply(v, oom);
        V3D_Point ltf = new V3D_Point(xMin, yMin, zMin).apply(v, oom);
        V3D_Point rtf = new V3D_Point(xMax, yMin, zMin).apply(v, oom);
        V3D_Point rbf = new V3D_Point(xMax, yMin, zMax).apply(v, oom);
        V3D_Point lba = new V3D_Point(xMin, yMax, zMax).apply(v, oom);
        V3D_Point lta = new V3D_Point(xMin, yMax, zMin).apply(v, oom);
        V3D_Point rba = new V3D_Point(xMax, yMax, zMax).apply(v, oom);
        V3D_Point rta = new V3D_Point(xMax, yMax, zMin).apply(v, oom);
        return new V3D_Envelope(oom, lbf, ltf, rtf, rbf, lba, lta, rba, rta);
    }

    /**
     * @param e The V3D_Envelope to union with this.
     * @return an Envelope which is {@code this} union {@code e}.
     */
    public V3D_Envelope union(V3D_Envelope e) {
        if (e.isContainedBy(this)) {
            return this;
        } else {
            return new V3D_Envelope(
                    Math_BigRational.min(e.getxMin(), getxMin()),
                    Math_BigRational.max(e.getxMax(), getxMax()),
                    Math_BigRational.min(e.getyMin(), getyMin()),
                    Math_BigRational.max(e.getyMax(), getyMax()),
                    Math_BigRational.min(e.getzMin(), getzMin()),
                    Math_BigRational.max(e.getzMax(), getzMax()),
                    oom);
        }
    }

    /**
     * If {@code e} touches, or overlaps then it intersects.
     *
     * @param e The Vector_Envelope2D to test for intersection.
     * @return {@code true} if this intersects with {@code e}.
     */
    public boolean isIntersectedBy(V3D_Envelope e) {
        if (e.getxMax().compareTo(getxMin()) != -1
                && e.getxMin().compareTo(getxMax()) != 1
                && getxMax().compareTo(e.getxMin()) != -1
                && getxMin().compareTo(e.getxMax()) != 1) {
            if (e.getyMax().compareTo(getyMin()) != -1
                    && e.getyMin().compareTo(getyMax()) != 1
                    && getyMax().compareTo(e.getyMin()) != -1
                    && getyMin().compareTo(e.getyMax()) != 1) {
                if (e.getzMax().compareTo(getzMin()) != -1
                        && e.getzMin().compareTo(getzMax()) != 1
                        && getzMax().compareTo(e.getzMin()) != -1
                        && getzMin().compareTo(e.getzMax()) != 1) {
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
    public boolean isContainedBy(V3D_Envelope e) {
        return getxMax().compareTo(e.getxMax()) != 1
                && getxMin().compareTo(e.getxMin()) != -1
                && getyMax().compareTo(e.getyMax()) != 1
                && getyMin().compareTo(e.getyMin()) != -1
                && getzMax().compareTo(e.getzMax()) != 1
                && getzMin().compareTo(e.getzMin()) != -1;
    }

    /**
     * @param p The point to test for intersection.
     * @return {@code true} if this intersects with {@code p}
     */
    @Override
    public boolean isIntersectedBy(V3D_Point p, int oom) {
        return isIntersectedBy(p.x, p.y, p.z);
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @return {@code true} if this intersects with {@code p}
     */
    public boolean isIntersectedBy(Math_BigRational x, Math_BigRational y,
            Math_BigRational z) {
        return x.compareTo(getxMin()) != -1 && x.compareTo(getxMax()) != 1
                && y.compareTo(getyMin()) != -1 && y.compareTo(getyMax()) != 1
                && z.compareTo(getzMin()) != -1 && z.compareTo(getzMax()) != 1;
    }

    /**
     * @param en The envelop to intersect.
     * @return {@code null} if there is no intersection; {@code en} if
     * {@code this.equals(en)}; otherwise returns the intersection.
     */
    public V3D_Envelope getIntersection(V3D_Envelope en) {
        if (this.equals(en)) {
            return en;
        }
        if (!this.isIntersectedBy(en)) {
            return null;
        }
        return new V3D_Envelope(
                Math_BigRational.max(getxMin(), en.getxMin()),
                Math_BigRational.min(getxMax(), en.getxMax()),
                Math_BigRational.max(getyMin(), en.getyMin()),
                Math_BigRational.min(getyMax(), en.getyMax()),
                Math_BigRational.max(getzMin(), en.getzMin()),
                Math_BigRational.min(getzMax(), en.getzMax()),
                oom);
    }

    /**
     * Returns {@code null} if {@code this} does not intersect {@code l};
     * otherwise returns the intersection which is either a point or a line
     * segment.
     *
     * @param li The line to intersect.
     * @return {@code null} if there is no intersection; otherwise returns the
     * intersection.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line li, int oom) {
        switch (type) {
            case 0:
                V3D_Point fp = new V3D_Point((Point) f);
                if (li.isIntersectedBy(fp, oom)) {
                    return fp;
                } else {
                    return null;
                }
            case 1:
                return new V3D_LineSegment((LineSegment) l, oom).getIntersection(li, oom);
            case 2:
                return new V3D_LineSegment((LineSegment) f, oom).getIntersection(li, oom);
            case 3:
                return new V3D_LineSegment((LineSegment) f, oom).getIntersection(li, oom);
            case 4:
                return new V3D_Rectangle((Rectangle) f, oom).getIntersection(li, oom);
            case 5:
                return new V3D_Rectangle((Rectangle) l, oom).getIntersection(li, oom);
            case 6:
                return new V3D_Rectangle((Rectangle) t, oom).getIntersection(li, oom);
            default:
                V3D_Geometry fil = (new V3D_Rectangle((Rectangle) f, oom))
                        .getIntersection(li, oom);
                V3D_Geometry lil = (new V3D_Rectangle((Rectangle) l, oom))
                        .getIntersection(li, oom);
                V3D_Geometry ail = (new V3D_Rectangle((Rectangle) a, oom))
                        .getIntersection(li, oom);
                if (fil == null) {
                    if (lil == null) {
                        V3D_Geometry ril = (new V3D_Rectangle((Rectangle) r, oom))
                                .getIntersection(li, oom);
                        if (ail == null) {
                            V3D_Geometry til = (new V3D_Rectangle(
                                    (Rectangle) t, oom))
                                    .getIntersection(li, oom);
                            if (ril == null) {
                                if (til == null) {
                                    return null;
                                } else {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom))
                                            .getIntersection(li, oom);
                                    return new V3D_LineSegment(
                                            (V3D_Point) til,
                                            (V3D_Point) bil,
                                            oom);
                                }
                            } else {
                                if (til == null) {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom))
                                            .getIntersection(li, oom);
                                    if (bil == null) {
                                        return null;
                                    } else {
                                        return V3D_FiniteGeometry.getGeometry(
                                                (V3D_Point) ril,
                                                (V3D_Point) bil,
                                                oom);
                                    }
                                } else {
                                    return V3D_FiniteGeometry.getGeometry(
                                            (V3D_Point) ril,
                                            (V3D_Point) til,
                                            oom);
                                }
                            }
                        } else {
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom))
                                        .getIntersection(li, oom);
                                if (til == null) {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom))
                                            .getIntersection(li, oom);
                                    if (bil == null) {
                                        return null;
                                    } else {
                                        return V3D_FiniteGeometry.getGeometry(
                                                (V3D_Point) ail,
                                                (V3D_Point) bil,
                                                oom);
                                    }
                                } else {
                                    return V3D_FiniteGeometry.getGeometry(
                                            (V3D_Point) ail, (V3D_Point) til,
                                            oom);
                                }
                            } else {
                                return V3D_FiniteGeometry.getGeometry(
                                        (V3D_Point) ail, (V3D_Point) ril,
                                        oom);
                            }
                        }
                    } else {
                        if (ail == null) {
                            V3D_Geometry ril = (new V3D_Rectangle(
                                    (Rectangle) r, oom))
                                    .getIntersection(li, oom);
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom))
                                        .getIntersection(li, oom);
                                V3D_Geometry bil = (new V3D_Rectangle(
                                        (Rectangle) b, oom))
                                        .getIntersection(li, oom);
                                if (til == null) {
                                    if (bil == null) {
                                        return null;
                                    } else {
                                        return V3D_FiniteGeometry.getGeometry(
                                                (V3D_Point) lil,
                                                (V3D_Point) bil,
                                                oom);
                                    }
                                } else {
                                    if (bil == null) {
                                        return null;
                                    } else {
                                        return V3D_FiniteGeometry.getGeometry(
                                                (V3D_Point) lil,
                                                (V3D_Point) bil,
                                                oom);
                                    }
                                }
                            } else {
                                return new V3D_LineSegment(
                                        (V3D_Point) lil,
                                        (V3D_Point) ril,
                                        oom);
                            }
                        } else {
                            return V3D_FiniteGeometry.getGeometry(
                                    (V3D_Point) lil, (V3D_Point) ail, oom);
                        }
                    }
                } else {
                    if (lil == null) {
                        V3D_Geometry ril = (new V3D_Rectangle((Rectangle) r, oom))
                                .getIntersection(li, oom);
                        if (ail == null) {
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom))
                                        .getIntersection(li, oom);
                                if (til == null) {
                                    return null;
                                } else {
                                    return V3D_FiniteGeometry.getGeometry(
                                            (V3D_Point) fil, (V3D_Point) til,
                                            oom);
                                }
                            } else {
                                return V3D_FiniteGeometry.getGeometry(
                                        (V3D_Point) fil, (V3D_Point) ril,
                                        oom);
                            }
                        } else {
                            return new V3D_LineSegment(
                                    (V3D_Point) fil,
                                    (V3D_Point) ail,
                                    oom);
                        }
                    } else {
                        if (ail == null) {
                            return V3D_FiniteGeometry.getGeometry(
                                    (V3D_Point) fil, (V3D_Point) lil, oom);
                        } else {
                            return V3D_FiniteGeometry.getGeometry(
                                    (V3D_Point) fil, (V3D_Point) ail, oom);
                        }
                    }
                }
        }
    }

    /**
     * @param li Line segment to intersect with {@code this}.
     * @return either a point or line segment which is the intersection of
     * {@code li} and {@code this}.
     * @param flag To distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}. The
     * value is ignored.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment li, int oom, boolean flag) {
        // Special case where both ends of li are within Envelope
        boolean lipi = isIntersectedBy(li.p, oom);
        if (lipi && isIntersectedBy(li.q, oom)) {
            return li;
        }
        switch (type) {
            case 0:
                V3D_Point fp = new V3D_Point((Point) f);
                if (li.isIntersectedBy(fp, oom)) {
                    return fp;
                } else {
                    return null;
                }
            case 1:
                return new V3D_LineSegment((LineSegment) l, oom).getIntersection(li, oom);
            case 2:
                return new V3D_LineSegment((LineSegment) f, oom).getIntersection(li, oom);
            case 3:
                return new V3D_LineSegment((LineSegment) f, oom).getIntersection(li, oom);
            case 4:
                return new V3D_Rectangle((Rectangle) f, oom).getIntersection(li, oom);
            case 5:
                return new V3D_Rectangle((Rectangle) l, oom).getIntersection(li, oom);
            case 6:
                return new V3D_Rectangle((Rectangle) t, oom).getIntersection(li, oom);
            default:
                V3D_Geometry fil = (new V3D_Rectangle((Rectangle) f, oom))
                        .getIntersection(li, oom, flag);
                V3D_Geometry lil = (new V3D_Rectangle((Rectangle) l, oom))
                        .getIntersection(li, oom, flag);
                V3D_Geometry ail = (new V3D_Rectangle((Rectangle) a, oom))
                        .getIntersection(li, oom, flag);
                if (fil == null) {
                    if (lil == null) {
                        V3D_Geometry ril = (new V3D_Rectangle((Rectangle) r, oom))
                                .getIntersection(li, oom, flag);
                        if (ail == null) {
                            V3D_Geometry til = (new V3D_Rectangle(
                                    (Rectangle) t, oom))
                                    .getIntersection(li, oom, flag);
                            if (ril == null) {
                                V3D_Geometry bil = (new V3D_Rectangle(
                                        (Rectangle) b, oom))
                                        .getIntersection(li, oom, flag);
                                if (til == null) {
                                    if (bil == null) {
                                        return null;
                                    } else {
                                        return getIntersection((V3D_Point) bil,
                                                li, lipi);
                                    }
                                } else {
                                    return new V3D_LineSegment(
                                            (V3D_Point) til,
                                            (V3D_Point) bil,
                                            oom);
                                }
                            } else {
                                if (til == null) {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom))
                                            .getIntersection(li, oom);
                                    if (bil == null) {
                                        return getIntersection((V3D_Point) ril,
                                                li, lipi);
                                    } else {
                                        return V3D_FiniteGeometry.getGeometry(
                                                (V3D_Point) ril,
                                                (V3D_Point) bil,
                                                oom);
                                    }
                                } else {
                                    return V3D_FiniteGeometry.getGeometry(
                                            (V3D_Point) ril, (V3D_Point) til,
                                            oom);
                                }
                            }
                        } else {
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom))
                                        .getIntersection(li, oom, flag);
                                if (til == null) {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom))
                                            .getIntersection(li, oom, flag);
                                    if (bil == null) {
                                        return getIntersection((V3D_Point) ail,
                                                li, lipi);
                                    } else {
                                        return V3D_FiniteGeometry.getGeometry(
                                                (V3D_Point) ail,
                                                (V3D_Point) bil,
                                                oom);
                                    }
                                } else {
                                    return V3D_FiniteGeometry.getGeometry(
                                            (V3D_Point) ail, (V3D_Point) til,
                                            oom);
                                }
                            } else {
                                return V3D_FiniteGeometry.getGeometry(
                                        (V3D_Point) ail, (V3D_Point) ril,
                                        oom);
                            }
                        }
                    } else {
                        if (ail == null) {
                            V3D_Geometry ril = (new V3D_Rectangle(
                                    (Rectangle) r, oom))
                                    .getIntersection(li, oom, flag);
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom))
                                        .getIntersection(li, oom, flag);
                                V3D_Geometry bil = (new V3D_Rectangle(
                                        (Rectangle) b, oom))
                                        .getIntersection(li, oom, flag);
                                if (til == null) {
                                    if (bil == null) {
                                        return getIntersection((V3D_Point) lil,
                                                li, lipi);
                                    } else {
                                        return V3D_FiniteGeometry.getGeometry(
                                                (V3D_Point) lil,
                                                (V3D_Point) bil,
                                                oom);
                                    }
                                } else {
                                    if (bil == null) {
                                        return getIntersection((V3D_Point) lil,
                                                li, lipi);
                                    } else {
                                        return V3D_FiniteGeometry.getGeometry(
                                                (V3D_Point) lil,
                                                (V3D_Point) bil,
                                                oom);
                                    }
                                }
                            } else {
                                return new V3D_LineSegment(
                                        (V3D_Point) lil,
                                        (V3D_Point) ril,
                                        oom);
                            }
                        } else {
                            return V3D_FiniteGeometry.getGeometry(
                                    (V3D_Point) lil, (V3D_Point) ail,
                                    oom);
                        }
                    }
                } else {
                    if (lil == null) {
                        V3D_Geometry ril = (new V3D_Rectangle((Rectangle) r, oom))
                                .getIntersection(li, oom, flag);
                        if (ail == null) {
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom))
                                        .getIntersection(li, oom, flag);
                                if (til == null) {
                                    return getIntersection((V3D_Point) fil,
                                            li, lipi);
                                } else {
                                    return V3D_FiniteGeometry.getGeometry(
                                            (V3D_Point) fil, (V3D_Point) til,
                                            oom);
                                }
                            } else {
                                return V3D_FiniteGeometry.getGeometry(
                                        (V3D_Point) fil, (V3D_Point) ril,
                                        oom);
                            }
                        } else {
                            return new V3D_LineSegment((V3D_Point) fil,
                                    (V3D_Point) ail, oom);
                        }
                    } else {
                        if (ail == null) {
                            return getIntersection((V3D_Point) fil, li, lipi);
                        } else {
                            return V3D_FiniteGeometry.getGeometry(
                                    (V3D_Point) fil, (V3D_Point) ail, oom);
                        }
                    }
                }
        }
    }

    private V3D_Geometry getIntersection(V3D_Point pi, V3D_LineSegment li,
            boolean lipi) {
        if (lipi) {
            return V3D_FiniteGeometry.getGeometry(li.p, pi, oom);
        } else {
            return V3D_FiniteGeometry.getGeometry(li.q, pi, oom);
        }
    }

    @Override
    public V3D_Envelope getEnvelope(int oom) {
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
    public boolean equals(V3D_Envelope e) {
        return this.getxMin().compareTo(e.getxMin()) == 0
                && this.getxMax().compareTo(e.getxMax()) == 0
                && this.getyMin().compareTo(e.getyMin()) == 0
                && this.getyMax().compareTo(e.getyMax()) == 0
                && this.getzMin().compareTo(e.getzMin()) == 0
                && this.getzMax().compareTo(e.getzMax()) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Envelope) {
            return equals((V3D_Envelope) o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.getxMin());
        hash = 43 * hash + Objects.hashCode(this.getxMax());
        hash = 43 * hash + Objects.hashCode(this.getyMin());
        hash = 43 * hash + Objects.hashCode(this.getyMax());
        hash = 43 * hash + Objects.hashCode(this.getzMin());
        hash = 43 * hash + Objects.hashCode(this.getzMax());
        return hash;
    }

    /**
     * @return the xMin
     */
    public Math_BigRational getxMin() {
        return xMin;
    }

    /**
     * @return the xMax
     */
    public Math_BigRational getxMax() {
        return xMax;
    }

    /**
     * @return the yMin
     */
    public Math_BigRational getyMin() {
        return yMin;
    }

    /**
     * @return the yMax
     */
    public Math_BigRational getyMax() {
        return yMax;
    }

    /**
     * @return the zMin
     */
    public Math_BigRational getzMin() {
        return zMin;
    }

    /**
     * @return the zMax
     */
    public Math_BigRational getzMax() {
        return zMax;
    }

    /**
     * Test if {@code this} is intersected by {@code li}.
     *
     * @param li The line to test for intersection.
     * @return {@code true} iff {@code this} is intersected by {@code li}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Line li, int oom) {
        switch (type) {
            case 0:
                return li.isIntersectedBy(new V3D_Point((Point) f), oom);
            case 1:
                return new V3D_LineSegment((LineSegment) l, oom).isIntersectedBy(li, oom);
            case 2:
                return new V3D_LineSegment((LineSegment) f, oom).isIntersectedBy(li, oom);
            case 3:
                return new V3D_LineSegment((LineSegment) f, oom).isIntersectedBy(li, oom);
            case 4:
                return new V3D_Rectangle((Rectangle) f, oom).isIntersectedBy(li, oom);
            case 5:
                return new V3D_Rectangle((Rectangle) l, oom).isIntersectedBy(li, oom);
            case 6:
                return new V3D_Rectangle((Rectangle) t, oom).isIntersectedBy(li, oom);
            default:
                /**
                 * Each part of this testing could be done simultaneously. An
                 * alternative method might test three orthogonal sides and the
                 * additional corner that is on none of these sides.
                 */
                if (new V3D_Rectangle((Rectangle) f, oom).isIntersectedBy(li, oom)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) l, oom).isIntersectedBy(li, oom)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) a, oom).isIntersectedBy(li, oom)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) r, oom).isIntersectedBy(li, oom)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) t, oom).isIntersectedBy(li, oom)) {
                    return true;
                } else {
                    return new V3D_Rectangle((Rectangle) b, oom).isIntersectedBy(li, oom);
                }
        }
    }

    /**
     *
     * Test if {@code this} is intersected by {@code li}.
     *
     * @param li The line to test for intersection.
     * @param flag To distinguish this method from
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}. The
     * value is ignored.
     * @return {@code true} iff {@code this} is intersected by {@code li}.
     */
    @Override
    public boolean isIntersectedBy(V3D_LineSegment li, int oom, boolean flag) {
        V3D_Envelope le = li.getEnvelope(oom);
        if (le.isIntersectedBy(this)) {
            switch (type) {
                case 0:
                    return li.isIntersectedBy(new V3D_Point((Point) f), oom);
                case 1:
                    return new V3D_LineSegment((LineSegment) l, oom).isIntersectedBy(li, oom);
                case 2:
                    return new V3D_LineSegment((LineSegment) f, oom).isIntersectedBy(li, oom);
                case 3:
                    return new V3D_LineSegment((LineSegment) f, oom).isIntersectedBy(li, oom);
                case 4:
                    return new V3D_Rectangle((Rectangle) f, oom).isIntersectedBy(li, oom);
                case 5:
                    return new V3D_Rectangle((Rectangle) l, oom).isIntersectedBy(li, oom);
                case 6:
                    return new V3D_Rectangle((Rectangle) t, oom).isIntersectedBy(li, oom);
                default:
                    /**
                     * Each part of this testing could be done simultaneously.
                     * An alternative method might test three orthogonal sides
                     * and the additional corner that is on none of these sides.
                     */
                    if (new V3D_Rectangle((Rectangle) f, oom).isIntersectedBy(li, oom)) {
                        return true;
                    } else if (new V3D_Rectangle((Rectangle) l, oom).isIntersectedBy(li, oom)) {
                        return true;
                    } else if (new V3D_Rectangle((Rectangle) a, oom).isIntersectedBy(li, oom)) {
                        return true;
                    } else if (new V3D_Rectangle((Rectangle) r, oom).isIntersectedBy(li, oom)) {
                        return true;
                    } else if (new V3D_Rectangle((Rectangle) t, oom).isIntersectedBy(li, oom)) {
                        return true;
                    } else {
                        return new V3D_Rectangle((Rectangle) b, oom).isIntersectedBy(li, oom);
                    }
            }
        }
        return false;
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
        return isIntersectedBy(l, oom);
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
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Point)}.
     *
     * @param p The point to find the distance to/from.
     * @param oom The Order of Magnitude for the result precision.
     * @return The approximate or exact distance at the given {@code oom}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        if (this.isIntersectedBy(p, oom)) {
            return BigDecimal.ZERO;
        }
        // Special case where Envelope is infinitesimally small.
        if (l instanceof Point && t instanceof Point) {
            return ((Point) l).getDistance(new Point(p), oom);
        }
        int xcmin = p.x.compareTo(xMin);
        int ycmin = p.y.compareTo(yMin);
        int zcmin = p.z.compareTo(zMin);
        if (xcmin == -1) {
            if (ycmin == -1) {
                if (zcmin == -1) {
                    // lbf
                    if (f instanceof Rectangle) {
                        return ((Rectangle) f).p
                                .getDistance(new Point(p), oom);
                    } else if (f instanceof LineSegment) {
                        return ((LineSegment) f).p
                                .getDistance(new Point(p), oom);
                    } else {
                        return ((Point) f)
                                .getDistance(new Point(p), oom);
                    }
                } else {
                    int zcmax = p.z.compareTo(zMax);
                    if (zcmax == 1) {
                        // bla
                        if (l instanceof Rectangle) {
                            return ((Rectangle) l).p
                                    .getDistance(new Point(p), oom);
                        } else if (l instanceof LineSegment) {
                            return ((LineSegment) l).p
                                    .getDistance(new Point(p), oom);
                        } else {
                            return ((Point) l)
                                    .getDistance(new Point(p), oom);
                        }
                    } else {
                        // lba - lbf
                        if (l instanceof Rectangle) {
                            return new Line(((Rectangle) l).b, oom)
                                    .getDistance(new Point(p), oom);
                        } else if (l instanceof LineSegment) {
                            return ((Line) l)
                                    .getDistance(new Point(p), oom);
                        } else {
                            return ((Point) l)
                                    .getDistance(new Point(p), oom);
                        }
                    }
                }
            } else {
                int ycmax = p.y.compareTo(yMax);
                if (ycmax == 1) {
                    if (zcmin == -1) {
                        // ltf
                        if (f instanceof Rectangle) {
                            return ((Rectangle) f).q
                                    .getDistance(new Point(p), oom);
                        } else if (f instanceof LineSegment) {
                            return ((LineSegment) f).p
                                    .getDistance(new Point(p), oom);
                        } else {
                            return ((Point) f)
                                    .getDistance(new Point(p), oom);
                        }
                    } else {
                        int zcmax = p.z.compareTo(zMax);
                        if (zcmax == 1) {
                            // lta
                            if (l instanceof Rectangle) {
                                return ((Rectangle) l).q
                                        .getDistance(new Point(p), oom);
                            } else if (l instanceof LineSegment) {
                                return ((LineSegment) l).p
                                        .getDistance(new Point(p), oom);
                            } else {
                                return ((Point) l)
                                        .getDistance(new Point(p), oom);
                            }
                        } else {
                            // lta - ltf
                            if (l instanceof Rectangle) {
                                return new Line(((Rectangle) l).t, oom)
                                        .getDistance(new Point(p), oom);
                            } else if (l instanceof LineSegment) {
                                return ((Line) l)
                                        .getDistance(new Point(p), oom);
                            } else {
                                return ((Point) l)
                                        .getDistance(new Point(p), oom);
                            }
                        }
                    }
                } else {
                    if (zcmin == -1) {
                        // lbf - ltf
                        if (l instanceof Rectangle) {
                            return new Line(((Rectangle) l).ri, oom)
                                    .getDistance(new Point(p), oom);
                        } else if (l instanceof LineSegment) {
                            return ((Line) l)
                                    .getDistance(new Point(p), oom);
                        } else {
                            return ((Point) l)
                                    .getDistance(new Point(p), oom);
                        }
                    } else {
                        int zcmax = p.z.compareTo(zMax);
                        if (zcmax == 1) {
                            // lba - lta
                            if (l instanceof Rectangle) {
                                return new Line(((Rectangle) l).l, oom)
                                        .getDistance(new Point(p), oom);
                            } else if (l instanceof LineSegment) {
                                return ((Line) l)
                                        .getDistance(new Point(p), oom);
                            } else {
                                return ((Point) l)
                                        .getDistance(new Point(p), oom);
                            }
                        } else {
                            // lba - lta - ltf - lbf
                            if (l instanceof Rectangle) {
                                return new Plane((Rectangle) l)
                                        .getDistance(new Point(p), oom);
                            } else if (l instanceof LineSegment) {
                                return ((Line) l)
                                        .getDistance(new Point(p), oom);
                            } else {
                                return ((Point) l)
                                        .getDistance(new Point(p), oom);
                            }
                        }
                    }
                }
            }
        } else {
            int xcmax = p.x.compareTo(xMax);
            if (xcmax == 1) {
                if (ycmin == -1) {
                    if (zcmin == -1) {
                        // rbf
                        if (f instanceof Rectangle) {
                            return ((Rectangle) f).s
                                    .getDistance(new Point(p), oom);
                        } else if (f instanceof LineSegment) {
                            return ((LineSegment) f).q
                                    .getDistance(new Point(p), oom);
                        } else {
                            return ((Point) f)
                                    .getDistance(new Point(p), oom);
                        }
                    } else {
                        int zcmax = p.z.compareTo(zMax);
                        if (zcmax == 1) {
                            // rba
                            if (a instanceof Rectangle) {
                                return ((Rectangle) a).p
                                        .getDistance(new Point(p), oom);
                            } else if (a instanceof LineSegment) {
                                return ((LineSegment) a).p
                                        .getDistance(new Point(p), oom);
                            } else {
                                return ((Point) a)
                                        .getDistance(new Point(p), oom);
                            }
                        } else {
                            // rbf - rba
                            if (r instanceof Rectangle) {
                                return new Line(((Rectangle) r).b, oom)
                                        .getDistance(new Point(p), oom);
                            } else if (r instanceof LineSegment) {
                                return ((Line) r).getDistance(new Point(p), oom);
                            } else {
                                return ((Point) r).getDistance(new Point(p), oom);
                            }
                        }
                    }
                } else {
                    int ycmax = p.y.compareTo(yMax);
                    if (ycmax == 1) {
                        if (zcmin == -1) {
                            // rtf
                            if (f instanceof Rectangle) {
                                return ((Rectangle) f).r
                                        .getDistance(new Point(p), oom);
                            } else if (f instanceof LineSegment) {
                                return ((LineSegment) f).q
                                        .getDistance(new Point(p), oom);
                            } else {
                                return ((Point) f)
                                        .getDistance(new Point(p), oom);
                            }
                        } else {
                            int zcmax = p.z.compareTo(zMax);
                            if (zcmax == 1) {
                                // rta
                                if (a instanceof Rectangle) {
                                    return ((Rectangle) a).q
                                            .getDistance(new Point(p), oom);
                                } else if (a instanceof LineSegment) {
                                    return ((LineSegment) a).p
                                            .getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) a)
                                            .getDistance(new Point(p), oom);
                                }
                            } else {
                                // rtf - rta
                                if (r instanceof Rectangle) {
                                    return new Line(((Rectangle) r).t, oom)
                                            .getDistance(new Point(p), oom);
                                } else if (r instanceof LineSegment) {
                                    return ((Line) r)
                                            .getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) r)
                                            .getDistance(new Point(p), oom);
                                }
                            }
                        }
                    } else {
                        if (zcmin == -1) {
                            // rbf - rtf
                            if (f instanceof Rectangle) {
                                return new Line(((Rectangle) f).ri, oom)
                                        .getDistance(new Point(p), oom);
                            } else if (f instanceof LineSegment) {
                                return ((Line) f)
                                        .getDistance(new Point(p), oom);
                            } else {
                                return ((Point) f)
                                        .getDistance(new Point(p), oom);
                            }
                        } else {
                            int zcmax = p.z.compareTo(zMax);
                            if (zcmax == 1) {
                                // rba - rta
                                if (a instanceof Rectangle) {
                                    return new Line(((Rectangle) a).l, oom)
                                            .getDistance(new Point(p), oom);
                                } else if (a instanceof LineSegment) {
                                    return ((Line) a)
                                            .getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) a)
                                            .getDistance(new Point(p), oom);
                                }
                            } else {
                                // rbf - rtf - rta - rba
                                if (r instanceof Rectangle) {
                                    return new Plane((Rectangle) r)
                                            .getDistance(new Point(p), oom);
                                } else if (r instanceof LineSegment) {
                                    return ((Line) r)
                                            .getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) r)
                                            .getDistance(new Point(p), oom);
                                }
                            }
                        }
                    }
                }
            } else {
                if (ycmin == -1) {
                    if (zcmin == -1) {
                        // lbf - rbf
                        if (f instanceof Rectangle) {
                            return new Line(((Rectangle) f).b, oom)
                                    .getDistance(new Point(p), oom);
                        } else if (f instanceof LineSegment) {
                            return ((Line) f)
                                    .getDistance(new Point(p), oom);
                        } else {
                            return ((Point) f)
                                    .getDistance(new Point(p), oom);
                        }
                    } else {
                        int zcmax = p.z.compareTo(zMax);
                        if (zcmax == 1) {
                            // rba - lba
                            if (a instanceof Rectangle) {
                                return new Line(((Rectangle) a).b, oom)
                                        .getDistance(new Point(p), oom);
                            } else if (a instanceof LineSegment) {
                                return ((Line) a)
                                        .getDistance(new Point(p), oom);
                            } else {
                                return ((Point) a)
                                        .getDistance(new Point(p), oom);
                            }
                        } else {
                            // lba - lbf - rbf - rba
                            if (b instanceof Rectangle) {
                                return new Plane((Rectangle) b)
                                        .getDistance(new Point(p), oom);
                            } else if (b instanceof LineSegment) {
                                return ((Line) b)
                                        .getDistance(new Point(p), oom);
                            } else {
                                return ((Point) b)
                                        .getDistance(new Point(p), oom);
                            }
                        }
                    }
                } else {
                    int ycmax = p.y.compareTo(yMax);
                    if (ycmax == 1) {
                        if (zcmin == -1) {
                            // ltf - rtf
                            if (f instanceof Rectangle) {
                                return ((Rectangle) f).t
                                        .getDistance(new Point(p), oom);
                            } else if (f instanceof LineSegment) {
                                return ((LineSegment) f).q
                                        .getDistance(new Point(p), oom);
                            } else {
                                return ((Point) f)
                                        .getDistance(new Point(p), oom);
                            }
                        } else {
                            int zcmax = p.z.compareTo(zMax);
                            if (zcmax == 1) {
                                // rta - lta
                                if (a instanceof Rectangle) {
                                    return new Line(((Rectangle) a).t, oom)
                                            .getDistance(new Point(p), oom);
                                } else if (a instanceof LineSegment) {
                                    return ((Line) a)
                                            .getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) a)
                                            .getDistance(new Point(p), oom);
                                }
                            } else {
                                // ltf - lta - rta - rtf
                                if (t instanceof Rectangle) {
                                    return new Plane((Rectangle) t)
                                            .getDistance(new Point(p), oom);
                                } else if (r instanceof LineSegment) {
                                    return ((Line) t)
                                            .getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) t)
                                            .getDistance(new Point(p), oom);
                                }
                            }
                        }
                    } else {
                        if (zcmin == -1) {
                            // lbf - ltf - rtf - rbf
                            if (f instanceof Rectangle) {
                                return new Plane((Rectangle) f)
                                        .getDistance(new Point(p), oom);
                            } else if (f instanceof LineSegment) {
                                return ((Line) f)
                                        .getDistance(new Point(p), oom);
                            } else {
                                return ((Point) f)
                                        .getDistance(new Point(p), oom);
                            }
                        } else {
                            int zcmax = p.z.compareTo(zMax);
                            if (zcmax == 1) {
                                // rba - rta - lta - lba
                                if (a instanceof Rectangle) {
                                    return new Plane((Rectangle) a)
                                            .getDistance(new Point(p), oom);
                                } else if (a instanceof LineSegment) {
                                    return ((Line) a)
                                            .getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) a)
                                            .getDistance(new Point(p), oom);
                                }
                            } else {
                                // lba - lbf - rbf - rba
                                if (r instanceof Rectangle) {
                                    return new Plane((Rectangle) b)
                                            .getDistance(new Point(p), oom);
                                } else if (r instanceof LineSegment) {
                                    return ((LineSegment) b)
                                            .getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) b)
                                            .getDistance(new Point(p), oom);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Abstract Geometry class for geometries aligning with axes.
     */
    public abstract class Geometry implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * Created a new Geometry.
         */
        public Geometry() {
        }
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
         * Create a new instance from {@code p}.
         *
         * @param p The point to duplicate
         */
        public Point(V3D_Point p) {
            x = p.x;
            y = p.y;
            z = p.z;
        }

        /**
         * Create a new instance from {@code x}, {@code y}, {@code z}.
         *
         * @param x What {@link #x} is set to.
         * @param y What {@link #y} is set to.
         * @param z What {@link #z} is set to.
         */
        public Point(Math_BigRational x, Math_BigRational y, Math_BigRational z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /**
         * Create a new instance from {@code v}.
         *
         * @param v The vector.
         */
        public Point(V3D_Vector v) {
            x = v.getDX(oom);
            y = v.getDY(oom);
            z = v.getDZ(oom);
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "(x=" + x.toString()
                    + ", y=" + y.toString() + ", z=" + z.toString() + ")";
        }

        /**
         * Get the distance between this and {@code p}.
         *
         * @param p A point.
         * @param oom The Order of Magnitude for the precision of the result.
         * @return The distance from {@code p} to this.
         */
        public BigDecimal getDistance(Point p, int oom) {
            if (this.equals(p)) {
                return BigDecimal.ZERO;
            }
            return new Math_BigRationalSqrt(getDistanceSquared(p), oom)
                    .toBigDecimal(oom);
        }

        /**
         * Get the distance between this and {@code p}.
         *
         * @param p A point.
         * @param oom The Order of Magnitude of the precision for the initial
         * root calculation.
         * @return The distance from {@code p} to this.
         */
        public Math_BigRationalSqrt getDistanceExact(Point p) {
            if (this.equals(p)) {
                return Math_BigRationalSqrt.ZERO;
            }
            // The following choice of -1 is arbitrary.
            return new Math_BigRationalSqrt(getDistanceSquared(p), -1);
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
         * The Order of Magnitude used in the calculation of the magnitude of
         * {@link #v}.
         */
        public int oom;

        /**
         * {@code p} should not be equal to {@code q}.
         *
         * @param p What {@link #p} is set to.
         * @param q What {@link #q} is set to.
         * @param oom Used in the calculation of the magnitude of {@link #v}.
         */
        public Line(Point p, Point q, int oom) {
            this.p = p;
            this.q = q;
            v = new V3D_Vector(q.x.subtract(p.x), q.y.subtract(p.y),
                    q.z.subtract(p.z), oom);
            this.oom = oom;
        }

        /**
         * Create a new instance from {@code l}
         *
         * @param l Line to create from.
         * @param oom Used in the calculation of the magnitude of {@link #v}.
         */
        public Line(Line l, int oom) {
            this.p = l.p;
            this.q = l.q;
            v = new V3D_Vector(q.x.subtract(p.x), q.y.subtract(p.y),
                    q.z.subtract(p.z), oom);
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "(p=" + p.toString()
                    + ", q=" + q.toString() + ", v=" + v.toString() + ")";
        }

        /**
         * @param l The line to test this with to see if they are parallel.
         * @return {@code true} If this and {@code l} are parallel.
         */
        public boolean isParallel(Line l, int oom) {
            return v.isScalarMultiple(l.v, oom);
        }

        /**
         * @param pt A point to test for intersection.
         * @return {@code true} if {@code pt} is on {@code this}.
         */
        public boolean isIntersectedBy(Point pt, int oom) {
            V3D_Vector ppt = new V3D_Vector(pt.x.subtract(p.x),
                    pt.y.subtract(p.y), pt.z.subtract(p.z), oom);
            V3D_Vector cp = v.getCrossProduct(ppt, oom);
            return cp.dx.isZero() && cp.dy.isZero() && cp.dz.isZero();
        }

        /**
     * @return {@code true} if {@code this} and {@code l} intersect and false if 
     * they may intersect, but more computation is needed.
     */
    protected boolean isIntersectedBy(Line l, int oom) {
        if (V3D_Geometrics.isCollinear(oom, this, this.p, this.q, l.p)) {
            return true;
        } else {
            Plane p = new Plane(this.p, this.q, l.p, oom);
            if (V3D_Geometrics.isCoplanar(oom, p, l.q)) {
                return true;
            }
        }
        if (V3D_Geometrics.isCollinear(oom, this, this.p, this.q, l.q)) {
            return true;
        } else {
            Plane p = new Plane(this.p, this.q, l.q, oom);
            return V3D_Geometrics.isCoplanar(oom, p, l.p);
        }
    }

        /**
         * Get the intersection between two lines.
         *
         * @param l Another line.
         * @return The intersection between two lines or {@code null}.
         */
        public Geometry getIntersection(Line l, int oom) {
            // Special case of parallel lines.
            if (isParallel(l, oom)) {
                if (l.isIntersectedBy(p, oom)) {
                    // If lines are coincident return this.
                    return this;
                } else {
                    return null;
                }
            }
            V3D_Vector plp = new V3D_Vector(p, l.p, oom);
            V3D_Vector lqlp = new V3D_Vector(l.q, l.p, oom);
            if (lqlp.getMagnitudeSquared().compareTo(Math_BigRational.ZERO) == 0) {
                if (isIntersectedBy(l.p, oom)) {
                    return l.p;
                }
            }
            V3D_Vector qp = new V3D_Vector(q, p, oom);
            if (qp.getMagnitudeSquared().compareTo(Math_BigRational.ZERO) == 0) {
                if (l.isIntersectedBy(p, oom)) {
                    return p;
                }
            }
            Math_BigRational a = plp.dx.multiply(lqlp.dx, oom).getSqrt(oom)
                    .add(plp.dy.multiply(lqlp.dy, oom).getSqrt(oom))
                    .add(plp.dz.multiply(lqlp.dz, oom).getSqrt(oom));
            Math_BigRational b = lqlp.dx.multiply(qp.dx, oom).getSqrt(oom)
                    .add(lqlp.dy.multiply(qp.dy, oom).getSqrt(oom))
                    .add(lqlp.dz.multiply(qp.dz, oom).getSqrt(oom));
            Math_BigRational c = plp.dx.multiply(qp.dx, oom).getSqrt(oom)
                    .add(plp.dy.multiply(qp.dy, oom).getSqrt(oom))
                    .add(plp.dz.multiply(qp.dz, oom).getSqrt(oom));
            Math_BigRational d = lqlp.dx.multiply(lqlp.dx, oom).getSqrt(oom)
                    .add(lqlp.dy.multiply(lqlp.dy, oom).getSqrt(oom))
                    .add(lqlp.dz.multiply(lqlp.dz, oom).getSqrt(oom));
            Math_BigRational e = qp.dx.multiply(qp.dx, oom).getSqrt(oom)
                    .add(qp.dy.multiply(qp.dy, oom).getSqrt(oom))
                    .add(qp.dz.multiply(qp.dz, oom).getSqrt(oom));
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
                                            mu = (p.y.subtract(l.p.y)).divide(l.v.getDY(oom));
                                            z = l.p.z.add(l.v.getDZ(oom).multiply(mu));
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
                                            lamda = (l.p.y.subtract(p.y)).divide(v.getDY(oom));
                                            z = p.z.add(v.getDZ(oom).multiply(lamda));
                                        }
                                    }
                                    //x = p.x;            
                                    //p.x + v.getDX(oom) * lamda = l.p.x + l.v.getDX(oom) * mu
                                    //p.y + v.getDY(oom) * lamda = l.p.y + l.v.getDY(oom) * mu
                                    //p.z + v.getDZ(oom) * lamda = l.p.z + l.v.getDZ(oom) * mu

                                } else {
                                    if (v.dz.isZero()) {
                                        z = p.z;
                                        mu = (p.z.subtract(l.p.z)).divide(l.v.getDY(oom));
                                        y = l.p.y.add(l.v.getDY(oom).multiply(mu));
                                    } else {
                                        if (l.v.dz.isZero()) {
                                            z = l.p.z;
                                            lamda = (l.p.z.subtract(p.z)).divide(v.getDY(oom));
                                            y = p.y.add(v.getDY(oom).multiply(lamda));
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
                                            Math_BigRational den2 = Math_BigRational.ONE.subtract(l.v.getDZ(oom).multiply(v.getDY(oom).divide(l.v.getDY(oom))));
                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                lamda = (((l.p.z.subtract(p.z)).divide(v.getDZ(oom))).subtract(l.v.getDZ(oom).multiply(v.getDY(oom).multiply(l.p.y.divide(l.v.getDY(oom)))))).divide(den2);
                                                z = p.z.add(v.getDZ(oom).multiply(lamda));
                                                y = p.y.add(v.getDY(oom).multiply(lamda));
                                            } else {
                                                den2 = Math_BigRational.ONE.subtract(l.v.getDY(oom).multiply(v.getDZ(oom).divide(l.v.getDZ(oom))));
                                                if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                    lamda = (((l.p.y.subtract(p.y)).divide(v.getDY(oom))).subtract(l.v.getDY(oom).multiply(v.getDZ(oom).multiply(l.p.z.divide(l.v.getDZ(oom)))))).divide(den2);
                                                    z = p.z.add(v.getDZ(oom).multiply(lamda));
                                                    y = p.y.add(v.getDY(oom).multiply(lamda));
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
                            mu = (p.x.subtract(l.p.x)).divide(l.v.getDX(oom));
                            if (v.dy.isZero()) {
                                if (l.v.dy.isZero()) {
                                    y = p.y;
                                    z = p.z;
                                } else {
                                    if (v.dz.isZero()) {
                                        y = l.p.y.add(l.v.getDY(oom).multiply(mu));
                                    } else {
                                        y = p.y.add(v.getDY(oom).multiply(mu));
                                    }
                                    if (l.v.dz.isZero()) {
                                        z = p.z;
                                    } else {
                                        z = l.p.z.add(l.v.getDZ(oom).multiply(mu));
                                    }
                                }
                            } else {
                                lamda = ((l.p.y.add(l.v.getDY(oom).multiply(mu)))
                                        .subtract(p.x)).divide(v.getDY(oom));
                                if (v.dz.isZero()) {
                                    z = p.z;
                                } else {
                                    z = p.z.add(v.getDZ(oom).multiply(lamda));
                                }
                                if (l.v.dy.isZero()) {
                                    y = p.y;
                                } else {
                                    y = l.p.y.add(l.v.getDY(oom).multiply(mu));
                                }
                            }
                        }
                    } else {
                        if (l.v.dx.isZero()) {
                            lamda = l.p.x.subtract(p.x).divide(v.getDX(oom));
                            x = l.p.x;
                            if (v.dy.isZero()) {
                                mu = (p.y.subtract(l.p.y)).divide(l.v.getDY(oom));
                                y = p.y;
                                if (v.dz.isZero()) {
                                    z = p.z;
                                } else {
                                    if (l.v.dz.isZero()) {
                                        z = l.p.z;
                                    } else {
                                        z = l.p.z.add(l.v.getDZ(oom).multiply(mu));
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
                                                mu = ((p.z.add(v.getDZ(oom).multiply(lamda))).subtract(l.p.z)).divide(l.v.getDZ(oom));
                                                z = l.p.z.add(l.v.getDZ(oom).multiply(mu));
                                            }
                                        }
                                    } else {
                                        if (v.dz.isZero()) {
                                            z = p.z;
                                        } else {
                                            if (l.v.dz.isZero()) {
                                                z = l.p.z;
                                            } else {
                                                mu = (p.z.subtract(l.p.z)).divide(l.v.getDZ(oom));
                                                z = l.p.z.add(l.v.getDZ(oom).multiply(mu));
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
                                                mu = ((p.z.add(v.getDZ(oom).multiply(lamda))).subtract(l.p.z)).divide(l.v.getDZ(oom));
                                                z = l.p.z.add(l.v.getDZ(oom).multiply(mu));
                                            }
                                        }
                                    } else {
                                        y = p.y.add(v.getDY(oom).multiply(lamda));
                                        if (v.dz.isZero()) {
                                            z = p.z;
                                        } else {
                                            if (l.v.dz.isZero()) {
                                                z = l.p.z;
                                            } else {
                                                mu = ((p.z.add(v.getDZ(oom).multiply(lamda))).subtract(l.p.z)).divide(l.v.getDZ(oom));
                                                z = l.p.z.add(l.v.getDZ(oom).multiply(mu));
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
                                            lamda = (l.p.z.subtract(p.z)).divide(v.getDZ(oom));
                                            x = p.x.add(v.getDX(oom).multiply(lamda));
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
                                            Math_BigRational den2 = Math_BigRational.ONE.subtract(l.v.getDZ(oom).multiply(v.getDX(oom).divide(l.v.getDX(oom))));
                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                lamda = (((l.p.z.subtract(p.z)).divide(v.getDZ(oom))).subtract(l.v.getDZ(oom).multiply(v.getDX(oom).multiply(l.p.x.divide(l.v.getDX(oom)))))).divide(den2);
                                                z = p.z.add(v.getDZ(oom).multiply(lamda));
                                                x = p.x.add(v.getDX(oom).multiply(lamda));
                                            } else {
                                                den2 = Math_BigRational.ONE.subtract(l.v.getDX(oom).multiply(v.getDZ(oom).divide(l.v.getDZ(oom))));
                                                if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                    lamda = (((l.p.x.subtract(p.x)).divide(v.getDX(oom))).subtract(l.v.getDX(oom).multiply(v.getDZ(oom).multiply(l.p.z.divide(l.v.getDZ(oom)))))).divide(den2);
                                                    z = p.z.add(v.getDZ(oom).multiply(lamda));
                                                    x = p.x.add(v.getDX(oom).multiply(lamda));
                                                } else {
                                                    // This should not happen!
                                                    z = null;
                                                    x = null;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    mu = p.y.subtract(l.p.y).divide(l.v.getDY(oom));
                                    x = l.p.x.add(l.v.getDX(oom).multiply(mu));
                                    z = l.p.z.add(l.v.getDZ(oom).multiply(mu));
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
                                        Math_BigRational den2 = Math_BigRational.ONE.subtract(l.v.getDY(oom).multiply(v.getDX(oom).divide(l.v.getDX(oom))));
                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                            lamda = (((l.p.y.subtract(p.y)).divide(v.getDY(oom))).subtract(l.v.getDY(oom).multiply(v.getDX(oom).multiply(l.p.x.divide(l.v.getDX(oom)))))).divide(den2);
                                            y = p.y.add(v.getDY(oom).multiply(lamda));
                                            x = p.x.add(v.getDX(oom).multiply(lamda));
                                        } else {
                                            den2 = Math_BigRational.ONE.subtract(l.v.getDX(oom).multiply(v.getDY(oom).divide(l.v.getDY(oom))));
                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                lamda = (((l.p.x.subtract(p.x)).divide(v.getDX(oom))).subtract(l.v.getDX(oom).multiply(v.getDY(oom).multiply(l.p.y.divide(l.v.getDY(oom)))))).divide(den2);
                                                y = p.y.add(v.getDY(oom).multiply(lamda));
                                                x = p.x.add(v.getDX(oom).multiply(lamda));
                                            } else {
                                                // This should not happen!
                                                y = null;
                                                x = null;
                                            }
                                        }
                                    } else {
                                        mu = (p.z.subtract(l.p.z)).divide(l.v.getDZ(oom));
                                        y = l.p.y.add(l.v.getDY(oom).multiply(mu));
                                        x = l.p.x.add(l.v.getDX(oom).multiply(mu));
                                    }
                                } else {
                                    if (l.v.dz.isZero()) {
                                        z = l.p.z;
                                        lamda = (l.p.z.subtract(p.z)).divide(v.getDZ(oom));
                                        y = p.y.add(v.getDY(oom).multiply(lamda));
                                        x = p.x.add(v.getDX(oom).multiply(lamda));
                                    } else {
                                        // There are 6 ways to calculate lamda. One way should work! - If not try calculating mu.
                                        Math_BigRational den2 = Math_BigRational.ONE.subtract(l.v.getDY(oom).multiply(v.getDX(oom).divide(l.v.getDX(oom))));
                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                            lamda = (((l.p.y.subtract(p.y)).divide(v.getDY(oom))).subtract(l.v.getDY(oom).multiply(v.getDX(oom).multiply(l.p.x.divide(l.v.getDX(oom)))))).divide(den2);
                                            x = p.x.add(v.getDX(oom).multiply(lamda));
                                            y = p.y.add(v.getDY(oom).multiply(lamda));
                                            z = p.z.add(v.getDZ(oom).multiply(lamda));
//                                        x = q.x.add(v.getDX(oom).multiply(lamda));
//                                        y = q.y.add(v.getDY(oom).multiply(lamda));
//                                        z = q.z.add(v.getDZ(oom).multiply(lamda));
                                        } else {
                                            den2 = Math_BigRational.ONE.subtract(l.v.getDY(oom).multiply(v.getDZ(oom).divide(l.v.getDZ(oom))));
                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                lamda = (((l.p.y.subtract(p.y)).divide(v.getDY(oom))).subtract(l.v.getDY(oom).multiply(v.getDZ(oom).multiply(l.p.z.divide(l.v.getDZ(oom)))))).divide(den2);
                                                x = p.x.add(v.getDX(oom).multiply(lamda));
                                                y = p.y.add(v.getDY(oom).multiply(lamda));
                                                z = p.z.add(v.getDZ(oom).multiply(lamda));
                                            } else {
                                                den2 = Math_BigRational.ONE.subtract(l.v.getDZ(oom).multiply(v.getDX(oom).divide(l.v.getDX(oom))));
                                                if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                    lamda = (((l.p.z.subtract(p.z)).divide(v.getDZ(oom))).subtract(l.v.getDZ(oom).multiply(v.getDX(oom).multiply(l.p.x.divide(l.v.getDX(oom)))))).divide(den2);
                                                    x = p.x.add(v.getDX(oom).multiply(lamda));
                                                    y = p.y.add(v.getDY(oom).multiply(lamda));
                                                    z = p.z.add(v.getDZ(oom).multiply(lamda));
                                                } else {
                                                    den2 = Math_BigRational.ONE.subtract(l.v.getDZ(oom).multiply(v.getDY(oom).divide(l.v.getDY(oom))));
                                                    if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                        lamda = (((l.p.z.subtract(p.z)).divide(v.getDZ(oom))).subtract(l.v.getDZ(oom).multiply(v.getDY(oom).multiply(l.p.y.divide(l.v.getDY(oom)))))).divide(den2);
                                                        x = p.x.add(v.getDX(oom).multiply(lamda));
                                                        y = p.y.add(v.getDY(oom).multiply(lamda));
                                                        z = p.z.add(v.getDZ(oom).multiply(lamda));
                                                    } else {
                                                        den2 = Math_BigRational.ONE.subtract(l.v.getDX(oom).multiply(v.getDX(oom).divide(l.v.getDY(oom))));
                                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                            lamda = (((l.p.x.subtract(p.x)).divide(v.getDX(oom))).subtract(l.v.getDX(oom).multiply(v.getDY(oom).multiply(l.p.y.divide(l.v.getDY(oom)))))).divide(den2);
                                                            x = p.x.add(v.getDX(oom).multiply(lamda));
                                                            y = p.y.add(v.getDY(oom).multiply(lamda));
                                                            z = p.z.add(v.getDZ(oom).multiply(lamda));
                                                        } else {
                                                            den2 = Math_BigRational.ONE.subtract(l.v.getDX(oom).multiply(v.getDX(oom).divide(l.v.getDZ(oom))));
                                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                                lamda = (((l.p.x.subtract(p.x)).divide(v.getDX(oom))).subtract(l.v.getDX(oom).multiply(v.getDZ(oom).multiply(l.p.z.divide(l.v.getDZ(oom)))))).divide(den2);
                                                                x = p.x.add(v.getDX(oom).multiply(lamda));
                                                                y = p.y.add(v.getDY(oom).multiply(lamda));
                                                                z = p.z.add(v.getDZ(oom).multiply(lamda));
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
                    return new Point(x, y, z);
                }
                return null;
            }
            Math_BigRational mua = num.divide(den);
            Math_BigRational mub = (a.add(b.multiply(mua))).divide(d).negate();
            Point pi = new Point(
                    //                (p.x.add(mua.multiply(qp.getDX(oom)))),
                    //                (p.y.add(mua.multiply(qp.getDY(oom)))),
                    //                (p.z.add(mua.multiply(qp.getDZ(oom)))));
                    (p.x.subtract(mua.multiply(qp.getDX(oom)))),
                    (p.y.subtract(mua.multiply(qp.getDY(oom)))),
                    (p.z.subtract(mua.multiply(qp.getDZ(oom)))));
            // If point p is on both lines then return this as the intersection.
            if (isIntersectedBy(pi, oom) && l.isIntersectedBy(pi, oom)) {
                return pi;
            }
            Point qi = new Point(
                    (l.p.x.add(mub.multiply(lqlp.getDX(oom)))),
                    (l.p.y.add(mub.multiply(lqlp.getDY(oom)))),
                    (l.p.z.add(mub.multiply(lqlp.getDZ(oom)))));
            // If point q is on both lines then return this as the intersection.
            if (isIntersectedBy(qi, oom) && l.isIntersectedBy(qi, oom)) {
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
         * @param oom The Order of Magnitude for the precision of the result.
         * @return The minimum distance between this and {@code p}.
         */
        public BigDecimal getDistance(Point p, int oom) {
            V3D_Vector pv = new V3D_Vector(this.p, p, oom);
            V3D_Vector vu = v.getUnitVector(oom - 2);
            BigDecimal pd = p.getDistance(new Point(vu.multiply(pv.getDotProduct(vu, oom), oom)
                    .add(new V3D_Vector(this.p, oom), oom)), oom);
            pv = new V3D_Vector(this.q, p, oom);
            vu = v.reverse().getUnitVector(oom - 2);
            BigDecimal qd = p.getDistance(new Point(vu.multiply(pv.getDotProduct(vu, oom), oom)
                    .add(new V3D_Vector(this.q, oom), oom)), oom);
            return pd.min(qd);
        }

        /**
         *
         * @param l A line for which the minimum distance from {@code this} is
         * returned.
         * @param oom The Order of Magnitude for the precision of the result.
         * @return The minimum distance between this and {@code l}.
         */
        public BigDecimal getDistance(Line l, int oom) {
            if (isParallel(l, oom)) {
                return l.getDistance(p, oom);
            } else {
                /**
                 * Calculate the direction vector of the line connecting the
                 * closest points by computing the cross product.
                 */
                V3D_Vector cp = v.getCrossProduct(l.v, oom);
                /**
                 * Calculate the delta from {@link #p} and l.p
                 */
                V3D_Vector delta = new V3D_Vector(p, oom)
                        .subtract(new V3D_Vector(l.p, oom), oom);
                //Math_BigRational m = Math_BigRational.valueOf(cp.getMagnitude(oom - 2));
                Math_BigRationalSqrt m = cp.getMagnitude();
                // d = cp.(delta)/m
                Math_BigRational dp = cp.getDotProduct(delta, oom);
                // m should only be zero if the lines are parallel.
                Math_BigRational d = dp.divide(m.getX());
                return d.toBigDecimal(oom);
            }
        }
    }

    /**
     * An axis aligned line segment.
     */
    public class LineSegment extends Line {

        private static final long serialVersionUID = 1L;

        /**
         * @param p What {@link #p} is set to.
         * @param q What {@link #q} is set to.
         * @param oom Used in the calculation of the magnitude of {@link #v}.
         */
        public LineSegment(Point p, Point q, int oom) {
            super(p, q, oom);
        }

        /**
         * @param pt A point to test for intersection.
         * @param oomi
         * @return {@code true} if {@code pt} intersects with {@code this}.
         */
        @Override
        public boolean isIntersectedBy(Point pt, int oom) {
            if (super.isIntersectedBy(pt, oom)) {
                Math_BigRationalSqrt a = pt.getDistanceExact(p);
                if (a.getX().isZero()) {
                    return true;
                }
                Math_BigRationalSqrt b = pt.getDistanceExact(q);
                if (b.getX().isZero()) {
                    return true;
                }
                Math_BigRationalSqrt l = this.p.getDistanceExact(q);
                if (a.add(b, oom).compareTo(l) != 1) {
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
         * @return The intersection between {@code this} and {@code l}.
         */
        @Override
        public Geometry getIntersection(Line l, int oom) {
            // Check if infinite lines intersect.
            Geometry li = l.getIntersection(new Line(this, oom), oom);
            if (li == null) {
                // There is no intersection.
                return li;
            }
            /**
             * If infinite lines intersects at a point, then check this point is
             * on this.
             */
            if (li instanceof Point) {
                if (isIntersectedBy((Point) li, oom)) {
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
         * The Order of Magnitude used in the calculation of the magnitude of
         * the vectors {@link #pq}, {@link #qr}, {@link #n}.
         */
        public int oom;

        /**
         * Create a new instance.
         *
         * @param p The plane used to create this.
         */
        public Plane(Plane p) {
            this(p.p, p.q, p.r, p.pq, p.qr, p.n, p.oom);
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
         * @param oom The Order of Magnitude used in the calculation of the
         * magnitude of the vectors {@link #pq}, {@link #qr}, {@link #n}.
         */
        public Plane(Point p, Point q, Point r, V3D_Vector pq, V3D_Vector qr,
                V3D_Vector n, int oom) {
            this.p = p;
            this.q = q;
            this.r = r;
            this.pq = pq;
            this.qr = qr;
            this.n = n;
            this.oom = oom;
        }

        /**
         * Create a new instance.
         *
         * @param p What {@link #p} is set to.
         * @param q What {@link #q} is set to.
         * @param r What {@link #r} is set to.
         * @param oom The Order of Magnitude used in the calculation of the
         * magnitude of the vectors {@link #pq}, {@link #qr}, {@link #n}.
         */
        public Plane(Point p, Point q, Point r, int oom) {
            this(p, q, r,
                    new V3D_Vector(p, q, oom),
                    new V3D_Vector(q, r, oom),
                    new V3D_Vector(p, q, oom).getCrossProduct(
                            new V3D_Vector(q, r, oom), oom), oom);
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "(p=" + p.toString()
                    + ", q=" + q.toString() + ", r=" + r.toString()
                    + ", n=" + n.toString() + ")";
        }

        /**
         * @param l The line to test if it is parallel to this.
         * @return {@code true} if {@code this} is parallel to {@code l}.
         */
        public boolean isParallel(Line l) {
            return n.getDotProduct(l.v, oom).isZero();
        }

        /**
         * @param l The line to test if it is on the plane.
         * @return {@code true} If {@code pt} is on the plane.
         */
        public boolean isOnPlane(Line l, int oom) {
            return isIntersectedBy(l.p, oom) && isIntersectedBy(l.q, oom);
        }

        /**
         * @param pt The point to test if it is on the plane.
         * @return {@code true} If {@code pt} is on the plane.
         */
        public boolean isIntersectedBy(Point pt, int oom) {
            Math_BigRational d = n.dx.multiply(p.x.subtract(pt.x), oom)
                    .add(n.dy.multiply(p.y.subtract(pt.y), oom), oom)
                    .add(n.dz.multiply(p.z.subtract(pt.z), oom), oom).getSqrt(oom);
            return d.compareTo(Math_BigRational.ZERO) == 0;
        }

        /**
         * @param l The line to test for intersection with this.
         * @return {@code true} If this and {@code l} intersect.
         */
        public boolean isIntersectedBy(Line l, int oom) {
            if (isParallel(l)) {
                if (!isOnPlane(l, oom)) {
                    return false;
                }
            }
            return true;
        }

        /**
         * https://en.wikipedia.org/wiki/Line%E2%80%93plane_intersection
         *
         * @param l The line to intersect with the plane.
         * @return The intersection of the line and the plane. This is either
         * {@code null} a line or a point.
         */
        public Geometry getIntersection(Line l, int oom) {
            if (isParallel(l)) {
                if (isOnPlane(l, oom)) {
                    return l;
                } else {
                    return null;
                }
            }
            // Are either of the points of l on the plane.
            if (isIntersectedBy(l.p, oom)) {
                return l.p;
            }
            if (isIntersectedBy(l.q, oom)) {
                return l.q;
            }
            Math_BigRational num = new V3D_Vector(p, l.p, oom).getDotProduct(n, oom);
            Math_BigRational den = l.v.getDotProduct(n, oom);
            Math_BigRational t = num.divide(den);
            return new Point(l.p.x.subtract(l.v.dx.multiply(t, oom).getSqrt(oom)),
                    l.p.y.subtract(l.v.dy.multiply(t, oom).getSqrt(oom)),
                    l.p.z.subtract(l.v.dz.multiply(t, oom).getSqrt(oom)));
        }

        /**
         * Get the distance between this and {@code pl}.
         *
         * @param p A point.
         * @param oom The order of magnitude of the precision.
         * @return The distance from {@code this} to {@code p}.
         */
        public BigDecimal getDistance(Point p, int oom) {
            if (this.isIntersectedBy(p, oom)) {
                return BigDecimal.ZERO;
            }
            V3D_Vector v = new V3D_Vector(p, this.p, oom);
            V3D_Vector u = this.n.getUnitVector(oom);
//        MathContext mc = new MathContext(Math_BigRationalSqrt
//                .getOOM(Math_BigRational.ONE, oom));
            MathContext mc = new MathContext(6 - oom);
            return v.getDotProduct(u, oom).abs().toBigDecimal(mc);
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
         * @param p The bottom left corner of the rectangle.
         * @param q The top left corner of the rectangle.
         * @param r The top right corner of the rectangle.
         * @param s The bottom right corner of the rectangle.
         * @param oom The Order of Magnitude used in the calculation of the
         * magnitude of the vectors {@link #pq}, {@link #qr}, {@link #n}.
         * @throws java.lang.RuntimeException iff the points do not define a
         * rectangle.
         */
        public Rectangle(Point p, Point q, Point r, Point s, int oom) {
            super(p, q, r, oom);
            this.s = s;
            //en = new V3D_Envelope(p, q, r, s); Not initialised here as it causes a StackOverflowError
            l = new LineSegment(p, q, oom);
            t = new LineSegment(q, r, oom);
            ri = new LineSegment(r, s, oom);
            b = new LineSegment(s, p, oom);
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "(p=" + p.toString()
                    + ", q=" + q.toString() + ", r=" + r.toString()
                    + ", s=" + s.toString() + ")";
        }

        /**
         * @param l The line to intersect with.
         * @return A point or line segment.
         */
        @Override
        public Geometry getIntersection(Line l, int oom) {
            Geometry g = new Plane(this).getIntersection(l, oom);
            if (g == null) {
                return null;
            } else {
                if (g instanceof Point) {
                    if (this.isIntersectedBy((Point) g, oom)) {
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
                    Geometry ti = t.getIntersection(li, oom);
                    if (ti == null) {
                        // Check ri, b, l
                        Geometry rii = ri.getIntersection(li, oom);
                        if (rii == null) {
                            // Check b, l
                            Geometry bi = b.getIntersection(li, oom);
                            if (bi == null) {
                                // Check l
                                Geometry tli = this.l.getIntersection(li, oom);
                                if (tli == null) {
                                    return null;
                                } else {
                                    return tli;
                                }
                            } else if (bi instanceof LineSegment) {
                                return bi;
                            } else {
                                // Check l
                                Geometry tli = this.l.getIntersection(li, oom);
                                if (tli == null) {
                                    return bi;
                                } else {
                                    return new LineSegment((Point) bi,
                                            (Point) tli, oom);
                                }
                            }
                        } else if (rii instanceof LineSegment) {
                            return rii;
                        } else {
                            // Check b, l
                            Geometry bi = b.getIntersection(li, oom);
                            if (bi == null) {
                                // Check l
                                Geometry tli = this.l.getIntersection(li, oom);
                                if (tli == null) {
                                    return rii;
                                } else {
                                    return new LineSegment((Point) rii,
                                            (Point) tli, oom);
                                }
                            } else if (bi instanceof LineSegment) {
                                return bi;
                            } else {
                                // Check l
                                Geometry tli = this.l.getIntersection(li, oom);
                                if (tli == null) {
                                    Point riip = (Point) rii;
                                    Point bip = (Point) bi;
                                    if (riip.equals(bip)) {
                                        return bip;
                                    } else {
                                        return new LineSegment(riip, bip, oom);
                                    }
                                } else {
                                    return new LineSegment((Point) bi,
                                            (Point) tli, oom);
                                }
                            }
                        }
                    } else if (ti instanceof LineSegment) {
                        return ti;
                    } else {
                        // Check ri, b, l
                        Geometry rii = ri.getIntersection(li, oom);
                        if (rii == null) {
                            // Check b, l
                            Geometry bi = b.getIntersection(li, oom);
                            if (bi == null) {
                                // Check l
                                Geometry tli = this.l.getIntersection(li, oom);
                                if (tli == null) {
                                    return ti;
                                } else {
                                    Point tlip = (Point) tli;
                                    Point tip = (Point) ti;
                                    if (tlip.equals(tip)) {
                                        return tlip;
                                    } else {
                                        return new LineSegment(tlip, tip, oom);
                                    }
                                }
                            } else if (bi instanceof LineSegment) {
                                return bi;
                            } else {
                                return new LineSegment((Point) ti, (Point) bi, oom);
                            }
                        } else {
                            Point tip = (Point) ti;
                            Point riip = (Point) rii;
                            if (tip.equals(riip)) {
                                // Check b, l
                                Geometry sri = b.getIntersection(li, oom);
                                if (sri == null) {
                                    // Check l
                                    Geometry tli = this.l.getIntersection(li, oom);
                                    if (tli == null) {
                                        return rii;
                                    } else {
                                        return new LineSegment(riip,
                                                (Point) tli, oom);
                                    }
                                } else if (sri instanceof LineSegment) {
                                    return sri;
                                } else {
                                    return new LineSegment(riip, (Point) sri, oom);
                                }
                            } else {
                                return new LineSegment(riip, tip, oom);
                            }
                        }
                    }
                }
            }
        }
    }
}
