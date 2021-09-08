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
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.math.Math_BigRationalSqrt;

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
     * <tr><td>1</td><td>f, l, a, r, t, b Rectangle</td></tr>
     * <tr><td>2</td><td>f, a Point; l, r, t, b LineSegment</td></tr>
     * <tr><td>4</td><td>1, r Point; f, a, t, b LineSegment</td></tr>
     * <tr><td>6</td><td>t, b Point; f, l, a, r LineSegment</td></tr>
     * <tr><td>3</td><td>f, a LineSegment; l, r, t, b Rectangle</td></tr>
     * <tr><td>5</td><td>1, r LineSegment; f, a, t, b Rectangle</td></tr>
     * <tr><td>7</td><td>t, b LineSegment; f, l, a, r Rectangle</td></tr>
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
                type = 1;
                break;
            default:
                BigRational xmin = points[0].x;
                BigRational xmax = points[0].x;
                BigRational ymin = points[0].y;
                BigRational ymax = points[0].y;
                BigRational zmin = points[0].z;
                BigRational zmax = points[0].z;
                for (int i = 1; i < points.length; i++) {
                    xmin = BigRational.min(xmin, points[i].x);
                    xmax = BigRational.max(xmax, points[i].x);
                    ymin = BigRational.min(ymin, points[i].y);
                    ymax = BigRational.max(ymax, points[i].y);
                    zmin = BigRational.min(zmin, points[i].z);
                    zmax = BigRational.max(zmax, points[i].z);
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
                            type = 0;
                        } else {
                            Point fp = new Point(xmin, ymin, zmin);
                            Point ap = new Point(xmin, ymax, zmin);
                            f = fp;
                            l = new LineSegment(ap, fp, oom);
                            a = ap;
                            r = new LineSegment(fp, ap, oom);
                            t = r;
                            b = l;
                            type = 2;
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
                            type = 4;
                        } else {
                            Point ta = new Point(xmin, ymax, zmax);
                            Point ba = new Point(xmin, ymin, zmax);
                            f = new LineSegment(bf, tf, oom);
                            l = new Rectangle(ba, ta, tf, bf, oom);
                            a = new LineSegment(ba, ta, oom);
                            r = new Rectangle(bf, tf, ta, ba, oom);
                            t = new LineSegment(tf, ta, oom);
                            b = new LineSegment(ba, bf, oom);
                            type = 3;
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
                            type = 6;
                        } else {
                            Point la = new Point(xmin, ymin, zmax);
                            Point ra = new Point(xmax, ymin, zmax);
                            f = new LineSegment(lf, rf, oom);
                            l = new LineSegment(la, lf, oom);
                            a = new LineSegment(ra, la, oom);
                            r = new LineSegment(rf, ra, oom);
                            t = new Rectangle(lf, la, ra, rf, oom);
                            b = new Rectangle(la, lf, rf, ra, oom);
                            type = 5;
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
                            type = 7;
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
                            type = 1;
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
    public V3D_Envelope(BigRational x, BigRational y, BigRational z, int oom) {
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
    public V3D_Envelope(BigRational xMin, BigRational xMax, BigRational yMin,
            BigRational yMax, BigRational zMin, BigRational zMax, int oom) {
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
    public V3D_Envelope apply(V3D_Vector v) {
        V3D_Point lbf = new V3D_Point(xMin, yMin, zMin).apply(v);
        V3D_Point ltf = new V3D_Point(xMin, yMin, zMax).apply(v);
        V3D_Point rtf = new V3D_Point(xMax, yMin, zMax).apply(v);
        V3D_Point rbf = new V3D_Point(xMax, yMin, zMin).apply(v);
        V3D_Point lba = new V3D_Point(xMin, yMax, zMin).apply(v);
        V3D_Point lta = new V3D_Point(xMin, yMax, zMax).apply(v);
        V3D_Point rba = new V3D_Point(xMax, yMax, zMin).apply(v);
        V3D_Point rta = new V3D_Point(xMax, yMax, zMax).apply(v);
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
                    BigRational.min(e.getxMin(), getxMin()),
                    BigRational.max(e.getxMax(), getxMax()),
                    BigRational.min(e.getyMin(), getyMin()),
                    BigRational.max(e.getyMax(), getyMax()),
                    BigRational.min(e.getzMin(), getzMin()),
                    BigRational.max(e.getzMax(), getzMax()),
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
    public boolean isIntersectedBy(V3D_Point p) {
        return isIntersectedBy(p.x, p.y, p.z);
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @return {@code true} if this intersects with {@code p}
     */
    public boolean isIntersectedBy(BigRational x, BigRational y,
            BigRational z) {
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
                BigRational.max(getxMin(), en.getxMin()),
                BigRational.min(getxMax(), en.getxMax()),
                BigRational.max(getyMin(), en.getyMin()),
                BigRational.min(getyMax(), en.getyMax()),
                BigRational.max(getzMin(), en.getzMin()),
                BigRational.min(getzMax(), en.getzMax()),
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
    public V3D_Geometry getIntersection(V3D_Line li) {
        switch (type) {
            case 0:
                V3D_Point fp = new V3D_Point((Point) f);
                if (li.isIntersectedBy(fp)) {
                    return fp;
                } else {
                    return null;
                }
            case 1:
                V3D_Geometry fil = (new V3D_Rectangle((Rectangle) f, oom))
                        .getIntersection(li);
                V3D_Geometry lil = (new V3D_Rectangle((Rectangle) l, oom))
                        .getIntersection(li);
                V3D_Geometry ail = (new V3D_Rectangle((Rectangle) a, oom))
                        .getIntersection(li);
                if (fil == null) {
                    if (lil == null) {
                        V3D_Geometry ril = (new V3D_Rectangle((Rectangle) r, oom))
                                .getIntersection(li);
                        if (ail == null) {
                            V3D_Geometry til = (new V3D_Rectangle(
                                    (Rectangle) t, oom))
                                    .getIntersection(li);
                            if (ril == null) {
                                if (til == null) {
                                    return null;
                                } else {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom))
                                            .getIntersection(li);
                                    return new V3D_LineSegment(
                                            (V3D_Point) til,
                                            (V3D_Point) bil,
                                            oom);
                                }
                            } else {
                                if (til == null) {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom))
                                            .getIntersection(li);
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
                                        .getIntersection(li);
                                if (til == null) {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom))
                                            .getIntersection(li);
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
                                    .getIntersection(li);
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom))
                                        .getIntersection(li);
                                V3D_Geometry bil = (new V3D_Rectangle(
                                        (Rectangle) b, oom))
                                        .getIntersection(li);
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
                                .getIntersection(li);
                        if (ail == null) {
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom))
                                        .getIntersection(li);
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
            case 2:
                return new V3D_LineSegment((LineSegment) l).getIntersection(li);
            case 4:
                return new V3D_LineSegment((LineSegment) f).getIntersection(li);
            case 6:
                return new V3D_LineSegment((LineSegment) f).getIntersection(li);
            case 3:
                return new V3D_Rectangle((Rectangle) l, oom).getIntersection(li);
            case 5:
                return new V3D_Rectangle((Rectangle) f, oom).getIntersection(li);
            default:
                return new V3D_Rectangle((Rectangle) f, oom).getIntersection(li);
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
    public V3D_Geometry getIntersection(V3D_LineSegment li, boolean flag) {
        // Special case where both ends of li are within Envelope
        boolean lipi = isIntersectedBy(li.p);
        if (lipi && isIntersectedBy(li.q)) {
            return li;
        }
        switch (type) {
            case 0:
                V3D_Point fp = new V3D_Point((Point) f);
                if (li.isIntersectedBy(fp)) {
                    return fp;
                } else {
                    return null;
                }
            case 1:
                V3D_Geometry fil = (new V3D_Rectangle((Rectangle) f, oom))
                        .getIntersection(li, flag);
                V3D_Geometry lil = (new V3D_Rectangle((Rectangle) l, oom))
                        .getIntersection(li, flag);
                V3D_Geometry ail = (new V3D_Rectangle((Rectangle) a, oom))
                        .getIntersection(li, flag);
                if (fil == null) {
                    if (lil == null) {
                        V3D_Geometry ril = (new V3D_Rectangle((Rectangle) r, oom))
                                .getIntersection(li, flag);
                        if (ail == null) {
                            V3D_Geometry til = (new V3D_Rectangle(
                                    (Rectangle) t, oom))
                                    .getIntersection(li, flag);
                            if (ril == null) {
                                V3D_Geometry bil = (new V3D_Rectangle(
                                        (Rectangle) b, oom))
                                        .getIntersection(li, flag);
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
                                            .getIntersection(li);
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
                                        .getIntersection(li, flag);
                                if (til == null) {
                                    V3D_Geometry bil = (new V3D_Rectangle(
                                            (Rectangle) b, oom))
                                            .getIntersection(li, flag);
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
                                    .getIntersection(li, flag);
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom))
                                        .getIntersection(li, flag);
                                V3D_Geometry bil = (new V3D_Rectangle(
                                        (Rectangle) b, oom))
                                        .getIntersection(li, flag);
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
                                .getIntersection(li, flag);
                        if (ail == null) {
                            if (ril == null) {
                                V3D_Geometry til = (new V3D_Rectangle(
                                        (Rectangle) t, oom))
                                        .getIntersection(li, flag);
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
            case 2:
                return new V3D_LineSegment((LineSegment) l).getIntersection(li);
            case 4:
                return new V3D_LineSegment((LineSegment) f).getIntersection(li);
            case 6:
                return new V3D_LineSegment((LineSegment) f).getIntersection(li);
            case 3:
                return new V3D_Rectangle((Rectangle) l, oom).getIntersection(li);
            case 5:
                return new V3D_Rectangle((Rectangle) f, oom).getIntersection(li);
            default:
                return new V3D_Rectangle((Rectangle) f, oom).getIntersection(li);
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
    public V3D_Envelope getEnvelope() {
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
    public BigRational getxMin() {
        return xMin;
    }

    /**
     * @return the xMax
     */
    public BigRational getxMax() {
        return xMax;
    }

    /**
     * @return the yMin
     */
    public BigRational getyMin() {
        return yMin;
    }

    /**
     * @return the yMax
     */
    public BigRational getyMax() {
        return yMax;
    }

    /**
     * @return the zMin
     */
    public BigRational getzMin() {
        return zMin;
    }

    /**
     * @return the zMax
     */
    public BigRational getzMax() {
        return zMax;
    }

    /**
     * Test if {@code this} is intersected by {@code li}.
     *
     * @param li The line to test for intersection.
     * @return {@code true} iff {@code this} is intersected by {@code li}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Line li) {
        switch (type) {
            case 0:
                return li.isIntersectedBy(new V3D_Point((Point) f));
            case 1:
                /**
                 * Each part of this testing could be done simultaneously. An
                 * alternative method might test three orthogonal sides and the
                 * additional corner that is on none of these sides.
                 */
                if (new V3D_Rectangle((Rectangle) f, oom).isIntersectedBy(li)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) l, oom).isIntersectedBy(li)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) a, oom).isIntersectedBy(li)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) r, oom).isIntersectedBy(li)) {
                    return true;
                } else if (new V3D_Rectangle((Rectangle) t, oom).isIntersectedBy(li)) {
                    return true;
                } else {
                    return new V3D_Rectangle((Rectangle) b, oom).isIntersectedBy(li);
                }
            case 2:
                return new V3D_LineSegment((LineSegment) l).isIntersectedBy(li);
            case 4:
                return new V3D_LineSegment((LineSegment) f).isIntersectedBy(li);
            case 6:
                return new V3D_LineSegment((LineSegment) f).isIntersectedBy(li);
            case 3:
                return new V3D_Rectangle((Rectangle) l, oom).isIntersectedBy(li);
            case 5:
                return new V3D_Rectangle((Rectangle) f, oom).isIntersectedBy(li);
            default:
                return new V3D_Rectangle((Rectangle) f, oom).isIntersectedBy(li);
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
    public boolean isIntersectedBy(V3D_LineSegment li, boolean flag) {
        V3D_Envelope le = li.getEnvelope();
        if (le.isIntersectedBy(this)) {
            switch (type) {
                case 0:
                    return li.isIntersectedBy(new V3D_Point((Point) f));
                case 1:
                    /**
                     * Each part of this testing could be done simultaneously.
                     * An alternative method might test three orthogonal sides
                     * and the additional corner that is on none of these sides.
                     */
                    if (new V3D_Rectangle((Rectangle) f, oom).isIntersectedBy(li)) {
                        return true;
                    } else if (new V3D_Rectangle((Rectangle) l, oom).isIntersectedBy(li)) {
                        return true;
                    } else if (new V3D_Rectangle((Rectangle) a, oom).isIntersectedBy(li)) {
                        return true;
                    } else if (new V3D_Rectangle((Rectangle) r, oom).isIntersectedBy(li)) {
                        return true;
                    } else if (new V3D_Rectangle((Rectangle) t, oom).isIntersectedBy(li)) {
                        return true;
                    } else {
                        return new V3D_Rectangle((Rectangle) b, oom).isIntersectedBy(li);
                    }
                case 2:
                    return new V3D_LineSegment((LineSegment) l).isIntersectedBy(li);
                case 4:
                    return new V3D_LineSegment((LineSegment) f).isIntersectedBy(li);
                case 6:
                    return new V3D_LineSegment((LineSegment) f).isIntersectedBy(li);
                case 3:
                    return new V3D_Rectangle((Rectangle) l, oom).isIntersectedBy(li);
                case 5:
                    return new V3D_Rectangle((Rectangle) f, oom).isIntersectedBy(li);
                default:
                    return new V3D_Rectangle((Rectangle) f, oom).isIntersectedBy(li);
            }
        }
        return false;
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l) {
        return isIntersectedBy(l);
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
        if (this.isIntersectedBy(p)) {
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
        public BigRational x;

        /**
         * The y coordinate.
         */
        public BigRational y;

        /**
         * The z coordinate.
         */
        public BigRational z;

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
        public Point(BigRational x, BigRational y, BigRational z) {
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
            x = v.getDX();
            y = v.getDY();
            z = v.getDZ();
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
            return Math_BigDecimal.sqrt(getDistanceSquared(p).toBigDecimal(),
                    oom, RoundingMode.HALF_UP);
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
        public BigRational getDistanceSquared(Point p) {
            BigRational dx = x.subtract(p.x);
            BigRational dy = y.subtract(p.y);
            BigRational dz = z.subtract(p.z);
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

        /**
         * @param l The line to test this with to see if they are parallel.
         * @return {@code true} If this and {@code l} are parallel.
         */
        public boolean isParallel(Line l) {
            return v.isScalarMultiple(l.v);
        }

        /**
         * @param pt A point to test for intersection.
         * @return {@code true} if p is on the line.
         */
        public boolean isIntersectedBy(Point pt) {
            V3D_Vector ppt = new V3D_Vector(pt.x.subtract(p.x),
                    pt.y.subtract(p.y), pt.z.subtract(p.z), oom);
            V3D_Vector cp = v.getCrossProduct(ppt);
            return cp.getDX().isZero() && cp.getDY().isZero() && cp.getDZ().isZero();
        }

        /**
         * This computes the intersection and tests if it is {@code null}
         *
         * @param l The line to test if it isIntersectedBy with this.
         * @return {@code true} If this and {@code l} intersect.
         */
        public boolean isIntersectedBy(Line l) {
            return getIntersection(l) != null;
        }

        /**
         * Get the intersection between two lines.
         *
         * @param l Another line.
         * @return The intersection between two lines or {@code null}.
         */
        public Geometry getIntersection(Line l) {
            // If lines are coincident return line.
            if (this.isParallel(l)) {
                if (l.isIntersectedBy(p)) {
                    return l;
                } else {
                    return null;
                }
            }
            if (l.v.getDX().compareTo(BigRational.ZERO) == 0) {
                if (l.v.getDY().compareTo(BigRational.ZERO) == 0) {
                    if (l.v.getDZ().compareTo(BigRational.ZERO) == 0) {
                        if (isIntersectedBy(l.p)) {
                            return l.p;
                        }
                    }
                }
                if (v.getDX().compareTo(BigRational.ZERO) == 0) {
                    //Point p = new Point(l.p.x, );
                }
            } else {

            }
            return null;
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
            return p.getDistance(new Point(vu.multiply(pv.getDotProduct(vu))
                    .add(new V3D_Vector(this.p, oom))), oom);
        }

        /**
         *
         * @param l A line for which the minimum distance from {@code this} is
         * returned.
         * @param oom The Order of Magnitude for the precision of the result.
         * @return The minimum distance between this and {@code l}.
         */
        public BigDecimal getDistance(Line l, int oom) {
            if (isParallel(l)) {
                return l.getDistance(p, oom);
            } else {
                /**
                 * Calculate the direction vector of the line connecting the
                 * closest points by computing the cross product.
                 */
                V3D_Vector cp = v.getCrossProduct(l.v);
                /**
                 * Calculate the delta from {@link #p} and l.p
                 */
                V3D_Vector delta = new V3D_Vector(p, oom)
                        .subtract(new V3D_Vector(l.p, oom));
                //BigRational m = BigRational.valueOf(cp.getMagnitude(oom - 2));
                Math_BigRationalSqrt m = cp.getMagnitude();
                // d = cp.(delta)/m
                BigRational dp = cp.getDotProduct(delta);
                // m should only be zero if the lines are parallel.
                BigRational d = dp.divide(m.getX());
                return Math_BigDecimal.round(d.toBigDecimal(), oom);
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
        public boolean isIntersectedBy(Point pt) {
            if (super.isIntersectedBy(pt)) {
                Math_BigRationalSqrt a = pt.getDistanceExact(p);
                if (a.getX().isZero()) {
                    return true;
                }
                Math_BigRationalSqrt b = pt.getDistanceExact(q);
                if (b.getX().isZero()) {
                    return true;
                }
                Math_BigRationalSqrt l = this.p.getDistanceExact(q);
                if (a.add(b).compareTo(l) != 1) {
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
        public Geometry getIntersection(Line l) {
            // Check if infinite lines intersect.
            Geometry li = l.getIntersection(new Line(this, oom));
            if (li == null) {
                // There is no intersection.
                return li;
            }
            /**
             * If infinite lines intersects at a point, then check this point is
             * on this.
             */
            if (li instanceof Point) {
                if (isIntersectedBy((Point) li)) {
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
                            new V3D_Vector(q, r, oom)),
                    oom);
        }

        /**
         * @param l The line to test if it is parallel to this.
         * @return {@code true} if {@code this} is parallel to {@code l}.
         */
        public boolean isParallel(Line l) {
            return n.getDotProduct(l.v).isZero();
        }

        /**
         * @param l The line to test if it is on the plane.
         * @return {@code true} If {@code pt} is on the plane.
         */
        public boolean isOnPlane(Line l) {
            return isIntersectedBy(l.p) && isIntersectedBy(l.q);
        }

        /**
         * @param pt The point to test if it is on the plane.
         * @return {@code true} If {@code pt} is on the plane.
         */
        public boolean isIntersectedBy(Point pt) {
            BigRational d = n.getDX().multiply(p.x.subtract(pt.x))
                    .add(n.getDY().multiply(p.y.subtract(pt.y)))
                    .add(n.getDZ().multiply(p.z.subtract(pt.z)));
            return d.compareTo(BigRational.ZERO) == 0;
        }

        /**
         * @param l The line to test for intersection with this.
         * @return {@code true} If this and {@code l} intersect.
         */
        public boolean isIntersectedBy(Line l) {
            if (isParallel(l)) {
                if (!isOnPlane(l)) {
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
        public Geometry getIntersection(Line l) {
            if (isParallel(l)) {
                if (isOnPlane(l)) {
                    return l;
                } else {
                    return null;
                }
            }
            // Are either of the points of l on the plane.
            if (isIntersectedBy(l.p)) {
                return l.p;
            }
            if (isIntersectedBy(l.q)) {
                return l.q;
            }
            BigRational num = new V3D_Vector(p, l.p, oom).getDotProduct(n);
            BigRational den = l.v.getDotProduct(n);
            BigRational t = num.divide(den);
            return new Point(l.p.x.subtract(l.v.getDX().multiply(t)),
                    l.p.y.subtract(l.v.getDY().multiply(t)),
                    l.p.z.subtract(l.v.getDZ().multiply(t)));
        }

        /**
         * Get the distance between this and {@code pl}.
         *
         * @param p A point.
         * @param oom The order of magnitude of the precision.
         * @return The distance from {@code this} to {@code p}.
         */
        public BigDecimal getDistance(Point p, int oom) {
            if (this.isIntersectedBy(p)) {
                return BigDecimal.ZERO;
            }
            V3D_Vector v = new V3D_Vector(p, this.p, oom);
            V3D_Vector u = this.n.getUnitVector(oom);
//        MathContext mc = new MathContext(Math_BigRationalSqrt
//                .getOOM(BigRational.ONE, oom));
            MathContext mc = new MathContext(6 - oom);
            return v.getDotProduct(u).abs().toBigDecimal(mc);
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

        /**
         * @param l The line to intersect with.
         * @return A point or line segment.
         */
        @Override
        public Geometry getIntersection(Line l) {
            Geometry g = new Plane(this).getIntersection(l);
            if (g == null) {
                return null;
            } else {
                if (g instanceof Point) {
                    if (this.isIntersectedBy((Point) g)) {
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
                    Geometry ti = t.getIntersection(li);
                    if (ti == null) {
                        // Check ri, b, l
                        Geometry rii = ri.getIntersection(li);
                        if (rii == null) {
                            // Check b, l
                            Geometry bi = b.getIntersection(li);
                            if (bi == null) {
                                // Check l
                                Geometry tli = this.l.getIntersection(li);
                                if (tli == null) {
                                    return null;
                                } else {
                                    return tli;
                                }
                            } else if (bi instanceof LineSegment) {
                                return bi;
                            } else {
                                // Check l
                                Geometry tli = this.l.getIntersection(li);
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
                            Geometry bi = b.getIntersection(li);
                            if (bi == null) {
                                // Check l
                                Geometry tli = this.l.getIntersection(li);
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
                                Geometry tli = this.l.getIntersection(li);
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
                        Geometry rii = ri.getIntersection(li);
                        if (rii == null) {
                            // Check b, l
                            Geometry bi = b.getIntersection(li);
                            if (bi == null) {
                                // Check l
                                Geometry tli = this.l.getIntersection(li);
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
                                Geometry sri = b.getIntersection(li);
                                if (sri == null) {
                                    // Check l
                                    Geometry tli = this.l.getIntersection(li);
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
