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
import java.util.Objects;

/**
 * An envelope contains all the extreme values with respect to the X, Y and Z
 * axes. It is an axis aligned bounding box, which may have length of zero in
 * any direction. For a point the envelope is essentially the point. The
 * envelope may also be a line. In any case it has:
 * <ul>
 * <li>a top ({@link #t}) aligned with {@link #zMax}</li>
 * <li>a bottom ({@link #b}) aligned with {@link #zMin}</li>
 * <li>a left ({@link #l}) aligned with {@link #xMin}</li>
 * <li>a right ({@link #ri}) aligned with {@link #xMax}</li>
 * <li>a fore ({@link #f}) aligned with {@link #yMin}</li>
 * <li>a aft ({@link #a}) aligned with {@link #yMax}</li>
 * </ul>
 * The following depiction of a bounding box indicate the location of the
 * different faces and also gives an abbreviated name of each point that
 * reflects these. This points are not stored explicitly in an instance of the
 * class with these names, but for a normal envelope (which is not a point or a
 * line or a plane), there are these 8 points stored in the rectangles that 
 * represent each face. {@code
 *
 *                                                          y
 *                                    z                    +
 *                                    +                   /
 *                                    |                  /
 *                                    |                 /
 *                                    |                /
 *                                    |
 *                                                  a
 *                                    t
 *                      tla_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ tra
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
 *          tlf /_ _ _ _ _ |_ _ _ _ _ _ _ _ _ _ _ /trf       |
 *             |           |                     |           |
 *             |           |                     |           |
 *  - ----  l  |           |                     |           |  ri  ---- + x
 *             |           |                     |           |
 *             |        bla|_ _ _ _ _ _ _ _ _ _ _|_ _ _ _ _ _|bra
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
 *          blf|/_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ |/brf
 *
 *                                     b
 *                      f
 *                                     |
 *                    /                |
 *                   /                 |
 *                  /                  |
 *                 -                   |
 *                                     -
 * }
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V3D_Envelope extends V3D_Geometry implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

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
    protected final V3D_FiniteGeometry t;

    /**
     * The left face.
     */
    protected final V3D_FiniteGeometry l;

    /**
     * The aft face.
     */
    protected final V3D_FiniteGeometry a;

    /**
     * The right face.
     */
    protected final V3D_FiniteGeometry r;

    /**
     * The fore face.
     */
    protected final V3D_FiniteGeometry f;

    /**
     * The bottom face.
     */
    protected final V3D_FiniteGeometry b;

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
        t = e.t;
        l = e.l;
        a = e.a;
        r = e.r;
        f = e.f;
        b = e.b;
    }

    /**
     * @param points The points used to form the envelop.
     */
    public V3D_Envelope(V3D_Point... points) {
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
                t = points[0];
                l = t;
                a = t;
                r = t;
                f = t;
                b = t;
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
                            t = new V3D_Point(xmin, ymin, zmin);
                            l = t;
                            a = t;
                            r = t;
                            f = t;
                            b = t;
                        } else {
                            t = new V3D_Point(xmin, ymin, zmax);
                            b = new V3D_Point(xmin, ymin, zmin);
                            l = new V3D_LineSegment((V3D_Point) b, (V3D_Point) t);
                            r = l;
                            f = l;
                            a = l;
                        }
                    } else {
                        if (zmin.compareTo(zmax) == 0) {
                            f = new V3D_Point(xmin, ymin, zmin);
                            a = new V3D_Point(xmin, ymax, zmin);
                            t = new V3D_LineSegment((V3D_Point) f, (V3D_Point) a);
                            l = t;
                            r = t;
                            b = t;
                        } else {
                            V3D_Point bf = new V3D_Point(xmin, ymin, zmin);
                            V3D_Point ba = new V3D_Point(xmin, ymax, zmin);
                            V3D_Point tf = new V3D_Point(xmin, ymin, zmax);
                            V3D_Point ta = new V3D_Point(xmin, ymax, zmax);
                            b = new V3D_LineSegment(bf, ba);
                            t = new V3D_LineSegment(tf, ta);
                            f = new V3D_LineSegment(bf, tf);
                            a = new V3D_LineSegment(ba, ta);
                            l = new V3D_Rectangle(tf, ta, ba, bf);
                            r = l;
                        }
                    }
                } else {
                    if (ymin.compareTo(ymax) == 0) {
                        if (zmin.compareTo(zmax) == 0) {
                            l = new V3D_Point(xmin, ymin, zmin);
                            r = new V3D_Point(xmax, ymin, zmin);
                            f = new V3D_LineSegment((V3D_Point) l, (V3D_Point) r);
                            a = f;
                            t = f;
                            b = f;
                        } else {
                            V3D_Point bl = new V3D_Point(xmin, ymin, zmin);
                            V3D_Point br = new V3D_Point(xmax, ymin, zmin);
                            V3D_Point tl = new V3D_Point(xmin, ymin, zmax);
                            V3D_Point tr = new V3D_Point(xmax, ymin, zmax);
                            b = new V3D_LineSegment(bl, br);
                            t = new V3D_LineSegment(tl, tr);
                            l = new V3D_LineSegment(bl, tl);
                            r = new V3D_LineSegment(br, tr);
                            f = new V3D_Rectangle(tl, tr, br, bl);
                            a = f;
                        }
                    } else {
                        if (zmin.compareTo(zmax) == 0) {
                            V3D_Point fl = new V3D_Point(xmin, ymin, zmin);
                            V3D_Point fr = new V3D_Point(xmax, ymin, zmin);
                            V3D_Point al = new V3D_Point(xmin, ymax, zmin);
                            V3D_Point ar = new V3D_Point(xmax, ymax, zmin);
                            f = new V3D_LineSegment(fl, fr);
                            a = new V3D_LineSegment(al, ar);
                            l = new V3D_LineSegment(fl, al);
                            r = new V3D_LineSegment(fr, ar);
                            t = new V3D_Rectangle(fl, al, ar, fr);
                            b = t;
                        } else {
                            V3D_Point tlf = new V3D_Point(xmin, ymin, zmax);
                            V3D_Point tla = new V3D_Point(xmin, ymax, zmax);
                            V3D_Point tar = new V3D_Point(xmax, ymax, zmax);
                            V3D_Point trf = new V3D_Point(xmax, ymin, zmax);
                            V3D_Point blf = new V3D_Point(xmin, ymin, zmin);
                            V3D_Point bla = new V3D_Point(xmin, ymax, zmin);
                            V3D_Point bar = new V3D_Point(xmax, ymax, zmin);
                            V3D_Point brf = new V3D_Point(xmax, ymin, zmin);
                            t = new V3D_Rectangle(tlf, tla, tar, trf);
                            l = new V3D_Rectangle(tlf, tla, bla, blf);
                            a = new V3D_Rectangle(tla, tar, bar, bla);
                            r = new V3D_Rectangle(trf, tar, bar, brf);
                            f = new V3D_Rectangle(tlf, trf, brf, blf);
                            b = new V3D_Rectangle(blf, bla, bar, brf);
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
    public V3D_Envelope(BigRational x, BigRational y, BigRational z) {
        this(new V3D_Point(x, y, z));
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
            BigRational yMax, BigRational zMin, BigRational zMax) {
        this(new V3D_Point(xMin, yMin, zMin), new V3D_Point(xMax, yMax, zMax));
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
     * @return a new plane.
     */
    @Override
    public V3D_Envelope apply(V3D_Vector v) {
        V3D_Point tlf = new V3D_Point(xMin, yMin, zMax).apply(v);
        V3D_Point tla = new V3D_Point(xMin, yMax, zMax).apply(v);
        V3D_Point tar = new V3D_Point(xMax, yMax, zMax).apply(v);
        V3D_Point trf = new V3D_Point(xMax, yMin, zMax).apply(v);
        V3D_Point blf = new V3D_Point(xMin, yMin, zMin).apply(v);
        V3D_Point bla = new V3D_Point(xMin, yMax, zMin).apply(v);
        V3D_Point bar = new V3D_Point(xMax, yMax, zMin).apply(v);
        V3D_Point brf = new V3D_Point(xMax, yMin, zMin).apply(v);
        return new V3D_Envelope(tlf, tla, tar, trf, blf, bla, bar, brf);
    }

    /**
     * @param e The V3D_Envelope to union with this.
     * @return an Envelope which is {@code this} union {@code e}.
     */
    public V3D_Envelope union(V3D_Envelope e) {
        if (e.isContainedBy(this)) {
            return this;
        } else {
            return new V3D_Envelope(BigRational.min(e.getxMin(), getxMin()),
                    BigRational.max(e.getxMax(), getxMax()),
                    BigRational.min(e.getyMin(), getyMin()),
                    BigRational.max(e.getyMax(), getyMax()),
                    BigRational.min(e.getzMin(), getzMin()),
                    BigRational.max(e.getzMax(), getzMax()));
        }
    }

    /**
     * If {@code e} touches, or overlaps then it intersects.
     *
     * @param e The Vector_Envelope2D to test for intersection.
     * @return {@code true} if this intersects with {@code e}.
     */
    public boolean isIntersectedBy(V3D_Envelope e) {
        // Does this contain any corners of e?
        boolean re = isIntersectedBy(e.getxMin(), e.getyMin(), e.getzMax());
        if (re) {
            return re;
        }
        re = isIntersectedBy(e.getxMin(), e.getyMax(), e.getzMax());
        if (re) {
            return re;
        }
        re = isIntersectedBy(e.getxMax(), e.getyMin(), e.getzMax());
        if (re) {
            return re;
        }
        re = isIntersectedBy(e.getxMax(), e.getyMax(), e.getzMax());
        if (re) {
            return re;
        }
        re = isIntersectedBy(e.getxMin(), e.getyMin(), e.getzMin());
        if (re) {
            return re;
        }
        re = isIntersectedBy(e.getxMin(), e.getyMax(), e.getzMin());
        if (re) {
            return re;
        }
        re = isIntersectedBy(e.getxMax(), e.getyMin(), e.getzMin());
        if (re) {
            return re;
        }
        re = isIntersectedBy(e.getxMax(), e.getyMax(), e.getzMin());
        if (re) {
            return re;
        }
        // Does e contain any corners of this
        re = e.isIntersectedBy(getxMax(), getyMax(), getzMax());
        if (re) {
            return re;
        }
        re = e.isIntersectedBy(getxMin(), getyMax(), getzMax());
        if (re) {
            return re;
        }
        re = e.isIntersectedBy(getxMax(), getyMin(), getzMax());
        if (re) {
            return re;
        }
        re = e.isIntersectedBy(getxMax(), getyMax(), getzMax());
        if (re) {
            return re;
        }
        /**
         * Check to see if xMin and xMax are between e.xMin and e.xMax, e.yMin
         * and e.yMax are between yMin and yMax, and e.zMin and e.zMax are
         * between zMin and zMax.
         */
        if (e.getxMax().compareTo(getxMax()) != 1 && e.getxMax().compareTo(getxMin()) != -1
                && e.getxMin().compareTo(getxMax()) != 1
                && e.getxMin().compareTo(getxMin()) != -1) {
            if (getyMin().compareTo(e.getyMax()) != 1 && getyMin().compareTo(e.getyMin()) != -1
                    && getyMax().compareTo(e.getyMax()) != 1
                    && getyMax().compareTo(e.getyMin()) != -1) {
                if (getzMin().compareTo(e.getzMax()) != 1 && getzMin().compareTo(e.getzMin()) != -1
                        && getzMax().compareTo(e.getzMax()) != 1
                        && getzMax().compareTo(e.getzMin()) != -1) {
                    return true;
                }
            }
        }
        /**
         * Check to see if e.xMin and e.xMax are between xMax, yMin and yMax are
         * between e.yMin and e.yMax, and zMin and zMax are between e.zMin and
         * e.zMax.
         */
        if (getxMax().compareTo(e.getxMax()) != 1 && getxMax().compareTo(e.getxMin()) != -1
                && getxMin().compareTo(e.getxMax()) != 1
                && getxMin().compareTo(e.getxMin()) != -1) {
            if (e.getyMin().compareTo(getyMax()) != 1 && e.getyMin().compareTo(getyMin()) != -1
                    && e.getyMax().compareTo(getyMax()) != 1
                    && e.getyMax().compareTo(getyMin()) != -1) {
                if (e.getzMin().compareTo(getzMax()) != 1 && e.getzMin().compareTo(getzMin()) != -1
                        && e.getzMax().compareTo(getzMax()) != 1
                        && e.getzMax().compareTo(getzMin()) != -1) {
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
        return this.getxMax().compareTo(e.getxMax()) != 1
                && this.getxMin().compareTo(e.getxMin()) != -1
                && this.getyMax().compareTo(e.getyMax()) != 1
                && this.getyMin().compareTo(e.getyMin()) != -1
                && this.getzMax().compareTo(e.getzMax()) != 1
                && this.getzMin().compareTo(e.getzMin()) != -1;
    }

    /**
     * @param l Line segment to intersect with {@code this}.
     * @return either a point or line segment which is the intersection of
     * {@code l} and {@code this}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, boolean b) {
        V3D_Envelope le = l.getEnvelope();
        if (le.isIntersectedBy(this)) {
            return le.getIntersection(this).getIntersection(l);
        }
        return null;
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
    public boolean isIntersectedBy(BigRational x, BigRational y, BigRational z) {
        return x.compareTo(getxMin()) != -1 && x.compareTo(getxMax()) != 1
                && y.compareTo(getyMin()) != -1 && y.compareTo(getyMax()) != 1
                && z.compareTo(getzMin()) != -1 && z.compareTo(getzMax()) != 1;
    }

    /**
     *
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
        return new V3D_Envelope(BigRational.max(getxMin(), en.getxMin()),
                BigRational.min(getxMax(), en.getxMax()),
                BigRational.max(getyMin(), en.getyMin()),
                BigRational.min(getyMax(), en.getyMax()),
                BigRational.max(getzMin(), en.getzMin()),
                BigRational.min(getzMax(), en.getzMax()));
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
        V3D_Geometry tli = t.getIntersection(li);
        if (tli == null) {
            // Check l, a, r, f, b
            V3D_Geometry lli = l.getIntersection(li);
            if (lli == null) {
                // Check a, r, f, b
                V3D_Geometry ali = a.getIntersection(li);
                if (ali == null) {
                    // Check r, f, b
                    V3D_Geometry rli = r.getIntersection(li);
                    if (rli == null) {
                        // Check f, b
                        V3D_Geometry fli = f.getIntersection(li);
                        if (fli == null) {
                            // null intersection.
                            return null;
                        } else if (fli instanceof V3D_LineSegment) {
                            return fli;
                        } else {
                            V3D_Point flip = (V3D_Point) fli;
                            V3D_Point blip = (V3D_Point) b.getIntersection(li);
                            return getGeometry(flip, blip);
                        }
                    } else if (rli instanceof V3D_LineSegment) {
                        return rli;
                    } else {
                        V3D_Point rlip = (V3D_Point) rli;
                        // check for intersection with b
                        V3D_Geometry bli = b.getIntersection(li);
                        if (bli == null) {
                            return rlip;
                        } else {
                            return getGeometry((V3D_Point) bli, rlip);
                        }
                    }
                } else if (ali instanceof V3D_LineSegment) {
                    return ali;
                } else {
                    // Check for intersection with r, f, b
                    V3D_Point alip = (V3D_Point) ali;
                    V3D_Geometry rli = r.getIntersection(li);
                    if (rli == null) {
                        // Check f, b
                        V3D_Geometry fli = f.getIntersection(li);
                        if (fli == null) {
                            // check for intersection with b
                            V3D_Geometry bli = b.getIntersection(li);
                            if (bli == null) {
                                return alip;
                            } else if (bli instanceof V3D_LineSegment) {
                                return bli;
                            } else {
                                return getGeometry((V3D_Point) bli, alip);
                            }
                        } else if (fli instanceof V3D_LineSegment) {
                            return fli;
                        } else {
                            return getGeometry((V3D_Point) fli, alip);
                        }
                    } else {
                        return getGeometry((V3D_Point) rli, alip);
                    }
                }
            }
        } else if (tli instanceof V3D_LineSegment) {
            return tli;
        } else {
            V3D_Point tlip = (V3D_Point) tli;
            // Check l, a, r, f, b
            V3D_Geometry lli = l.getIntersection(li);
            if (lli == null) {
                // Check a, r, f, b
                V3D_Geometry ali = a.getIntersection(li);
                if (ali == null) {
                    // Check r, f, b
                    V3D_Geometry rli = r.getIntersection(li);
                    if (rli == null) {
                        // Check f, b
                        V3D_Geometry fli = f.getIntersection(li);
                        if (fli == null) {
                            // Intersects b
                            V3D_Point blip = (V3D_Point) b.getIntersection(li);
                            return getGeometry(tlip, blip);
                        } else if (fli instanceof V3D_LineSegment) {
                            return fli;
                        } else {
                            // Could have a corner so still need to check b
                            // so far here there is an intersection with t and f
                            // and there is no intersection with a, r, and l
                            // check for intersection with b
                            V3D_Geometry bli = b.getIntersection(li);
                            if (bli == null) {
                                return getGeometry(tlip, (V3D_Point) bli);
                            } else if (bli instanceof V3D_LineSegment) {
                                return bli;
                            } else {
                                return getGeometry((V3D_Point) bli, tlip);
                            }
                        }
                    } else if (rli instanceof V3D_LineSegment) {
                        return rli;
                    } else {
                        V3D_Point rlip = (V3D_Point) rli;
                        // check for intersection with b
                        V3D_Geometry bli = b.getIntersection(li);
                        if (bli == null) {
                            return rlip;
                        } else {
                            return getGeometry((V3D_Point) bli, rlip);
                        }
                    }
                } else if (ali instanceof V3D_LineSegment) {
                    return ali;
                } else {
                    // Check for intersection with r, f, b
                    V3D_Point alip = (V3D_Point) ali;
                    V3D_Geometry rli = r.getIntersection(li);
                    if (rli == null) {
                        // Check f, b
                        V3D_Geometry fli = f.getIntersection(li);
                        if (fli == null) {
                            // check for intersection with b
                            V3D_Geometry bli = b.getIntersection(li);
                            if (bli == null) {
                                return alip;
                            } else if (bli instanceof V3D_LineSegment) {
                                return bli;
                            } else {
                                return getGeometry((V3D_Point) bli, alip);
                            }
                        } else if (fli instanceof V3D_LineSegment) {
                            return fli;
                        } else {
                            return getGeometry((V3D_Point) fli, alip);
                        }
                    } else {
                        V3D_Point rlip = (V3D_Point) rli;
                        if (rlip.equals(alip)) {
                            // Still more checking to do...
                            // Check f, b
                            V3D_Geometry fli = f.getIntersection(li);
                            if (fli == null) {
                                // check for intersection with b
                                V3D_Geometry bli = b.getIntersection(li);
                                if (bli == null) {
                                    return alip;
                                } else if (bli instanceof V3D_LineSegment) {
                                    return bli;
                                } else {
                                    return getGeometry((V3D_Point) bli, alip);
                                }
                            } else if (fli instanceof V3D_LineSegment) {
                                return fli;
                            } else {
                                return getGeometry((V3D_Point) fli, alip);
                            }
                        } else {
                            return getGeometry((V3D_Point) rli, alip);
                        }
                    }
                }
            } else if (lli instanceof V3D_LineSegment) {
                return lli;
            } else {
                // Still more checking to do...
                // intersection top and left could be at a corner and anyway need to check other faces...
                return getGeometry(tlip, (V3D_Point) lli);
            }
        }
        return null; // Should not get here remove after writing test cases.
    }

    private V3D_Geometry getGeometry(V3D_Point p, V3D_Point q) {
        if (p.equals(q)) {
            return p;
        } else {
            return new V3D_LineSegment(p, q);
        }
    }

    @Override
    public V3D_Envelope getEnvelope() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Envelope) {
            V3D_Envelope en = (V3D_Envelope) o;
            if (this.getxMin().compareTo(en.getxMin()) == 0
                    && this.getxMax().compareTo(en.getxMax()) == 0
                    && this.getyMin().compareTo(en.getyMin()) == 0
                    && this.getyMax().compareTo(en.getyMax()) == 0
                    && this.getzMin().compareTo(en.getzMin()) == 0
                    && this.getzMax().compareTo(en.getzMax()) == 0) {
                return true;
            }
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

    @Override
    public boolean isIntersectedBy(V3D_Line li) {
        if (t.isIntersectedBy(li)) {
            return true;
        } else if (l.isIntersectedBy(li)) {
            return true;
        } else if (a.isIntersectedBy(li)) {
            return true;
        } else if (r.isIntersectedBy(li)) {
            return true;
        } else {
            return f.isIntersectedBy(li);
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, boolean b) {
        V3D_Envelope le = l.getEnvelope();
        if (le.isIntersectedBy(this)) {
            if (this.isIntersectedBy(l.p) || this.isIntersectedBy(l.q)) {
                return true;
            }
            if (this.b.isIntersectedBy(l)) {
                return true;
            }
            return isIntersectedBy(l);
        }
        return false;
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l) {
        return isIntersectedBy(l);
    }
}
