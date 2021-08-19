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
import java.math.BigDecimal;
import java.util.Objects;

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
 *
 *                                                          z
 *                                    y                   +
 *                                    +                   /
 *                                    |                  /
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
 *  - ----  l  |           |                     |           |  r  ---- + x
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
 *                  /                  |
 *                 -                   |
 *                                     -
 * }
 *
 * @author Andy Turner
 * @version 1.0
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
        f = e.f;
        l = e.l;
        a = e.a;
        r = e.r;
        t = e.t;
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
                f = points[0];
                l = f;
                a = f;
                r = f;
                t = f;
                b = f;
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
                            f = new V3D_Point(xmin, ymin, zmin);
                            l = f;
                            a = f;
                            r = f;
                            t = f;
                            b = f;
                        } else {
                            V3D_Point fb = new V3D_Point(xmin, ymin, zmin);
                            V3D_Point ft = new V3D_Point(xmin, ymax, zmin);
                            f = new V3D_LineSegment(fb, ft);
                            l = f;
                            a = f;
                            r = f;
                            t = ft;
                            b = fb;
                        }
                    } else {
                        if (zmin.compareTo(zmax) == 0) {
                            V3D_Point fb = new V3D_Point(xmin, ymin, zmin);
                            V3D_Point lt = new V3D_Point(xmin, ymax, zmin);
                            f = new V3D_LineSegment(fb, lt);
                            l = f;
                            a = f;
                            r = f;
                            t = lt;
                            b = fb;
                        } else {
                            V3D_Point bf = new V3D_Point(xmin, ymin, zmin);
                            V3D_Point ba = new V3D_Point(xmin, ymin, zmax);
                            V3D_Point tf = new V3D_Point(xmin, ymax, zmin);
                            V3D_Point ta = new V3D_Point(xmin, ymax, zmax);
                            f = new V3D_LineSegment(bf, tf);
                            l = new V3D_Rectangle(ba, ta, tf, bf);
                            a = new V3D_LineSegment(ba, ta);
                            r = new V3D_Rectangle(bf, tf, ta, ba);
                            t = new V3D_LineSegment(tf, ta);
                            b = new V3D_LineSegment(ba, bf);
                        }
                    }
                } else {
                    if (ymin.compareTo(ymax) == 0) {
                        if (zmin.compareTo(zmax) == 0) {
                            V3D_Point lf = new V3D_Point(xmin, ymin, zmin);
                            V3D_Point rf = new V3D_Point(xmax, ymin, zmin);
                            f = new V3D_LineSegment(lf, rf);
                            l = lf;
                            a = new V3D_LineSegment(rf, lf);
                            r = rf;
                            t = f;
                            b = f;
                        } else {
                            V3D_Point lf = new V3D_Point(xmin, ymin, zmin);
                            V3D_Point la = new V3D_Point(xmin, ymin, zmax);
                            V3D_Point ra = new V3D_Point(xmax, ymin, zmax);
                            V3D_Point rf = new V3D_Point(xmax, ymin, zmin);
                            f = new V3D_LineSegment(lf, rf);
                            l = new V3D_LineSegment(la, lf);
                            a = new V3D_LineSegment(ra, la);
                            r = new V3D_LineSegment(rf, ra);
                            t = new V3D_Rectangle(lf, la, ra, rf);
                            b = new V3D_Rectangle(la, lf, rf, ra);
                        }
                    } else {
                        if (zmin.compareTo(zmax) == 0) {
                            V3D_Point lb = new V3D_Point(xmin, ymin, zmin);
                            V3D_Point lt = new V3D_Point(xmin, ymax, zmin);
                            V3D_Point rt = new V3D_Point(xmax, ymax, zmin);
                            V3D_Point rb = new V3D_Point(xmax, ymin, zmin);
                            f = new V3D_Rectangle(lb, lt, rt, rb);
                            l = new V3D_LineSegment(lb, lt);
                            a = new V3D_Rectangle(rb, rt, lt, lb);
                            r = new V3D_LineSegment(rb, rt);
                            t = new V3D_LineSegment(lt, rt);
                            b = new V3D_LineSegment(lb, rb);
                        } else {
                            V3D_Point lbf = new V3D_Point(xmin, ymin, zmin);
                            V3D_Point ltf = new V3D_Point(xmin, ymax, zmin);
                            V3D_Point rtf = new V3D_Point(xmax, ymax, zmin);
                            V3D_Point rbf = new V3D_Point(xmax, ymin, zmin);
                            V3D_Point lba = new V3D_Point(xmin, ymin, zmax);
                            V3D_Point lta = new V3D_Point(xmin, ymax, zmax);
                            V3D_Point rta = new V3D_Point(xmax, ymax, zmax);
                            V3D_Point rba = new V3D_Point(xmax, ymin, zmax);
                            f = new V3D_Rectangle(lbf, ltf, rtf, rbf);
                            l = new V3D_Rectangle(lba, lta, ltf, lbf);
                            a = new V3D_Rectangle(rba, rta, lta, lba);
                            r = new V3D_Rectangle(rbf, rtf, rta, rba);
                            t = new V3D_Rectangle(ltf, lta, rta, rtf);
                            b = new V3D_Rectangle(lba, lbf, rbf, rba);
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
        return new V3D_Envelope(lbf, ltf, rtf, rbf, lba, lta, rba, rta);
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
            } else if (lli instanceof V3D_LineSegment) {
                return lli;
            } else {
                V3D_Point llip = (V3D_Point) lli;
                // Check a, r, f, b
                V3D_Geometry ali = a.getIntersection(li);
                if (ali == null) {
                    // Check r, f, b
                    V3D_Geometry rli = r.getIntersection(li);
                    if (rli == null) {
                        // Check f, b
                        V3D_Geometry fli = f.getIntersection(li);
                        if (fli == null) {
                            // Check b
                            V3D_Geometry bli = b.getIntersection(li);
                            if (bli == null) {
                                return getGeometry(llip, (V3D_Point) bli);
                            } else if (bli instanceof V3D_LineSegment) {
                                return bli;
                            } else {
                                return getGeometry((V3D_Point) bli, llip);
                            }
                        } else if (fli instanceof V3D_LineSegment) {
                            return fli;
                        } else {
                            // Check b
                            V3D_Geometry bli = b.getIntersection(li);
                            if (bli == null) {
                                return getGeometry(llip, (V3D_Point) bli);
                            } else if (bli instanceof V3D_LineSegment) {
                                return bli;
                            } else {
                                return getGeometry((V3D_Point) bli, llip);
                            }
                        }
                    } else if (rli instanceof V3D_LineSegment) {
                        return rli;
                    } else {
                        return getGeometry((V3D_Point) rli, llip);
                    }
                } else if (ali instanceof V3D_LineSegment) {
                    return ali;
                } else {
                    // Check for intersection with r, f, b
                    V3D_Point alip = (V3D_Point) ali;
                    if (alip.equals(llip)) {
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
                            return getGeometry((V3D_Point) rli, llip);
                        }
                    } else {
                        return getGeometry(alip, llip);
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
                            // Check b
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

    /**
     * Test for equality.
     *
     * @param g The V3D_Geometry to test for equality with this.
     * @return {@code true} iff this and e are equal.
     */
    @Override
    public boolean equals(V3D_Geometry g) {
        if (g instanceof V3D_Envelope) {
            return equals((V3D_Envelope) g);
        }
        return false;
    }

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
        if (l instanceof V3D_Point) {
            return ((V3D_Point) l).getDistance(p, oom);
        }
        int xcmin = p.x.compareTo(xMin);
        int xcmax = p.x.compareTo(xMax);
        int ycmin = p.y.compareTo(yMin);
        int ycmax = p.y.compareTo(yMax);
        int zcmin = p.z.compareTo(zMin);
        int zcmax = p.z.compareTo(zMax);
        if (xcmin == -1) {
            if (ycmin == -1) {
                if (zcmin == -1) {
                    // lbf
                    if (f instanceof V3D_Rectangle) {
                        return ((V3D_Rectangle) f).p.getDistance(p, oom);
                    } else if (f instanceof V3D_LineSegment) {
                        return ((V3D_LineSegment) f).p.getDistance(p, oom);
                    } else {
                        return ((V3D_Point) f).getDistance(p, oom);
                    }
                } else {
                    if (zcmax == 1) {
                        // bla
                        if (l instanceof V3D_Rectangle) {
                            return ((V3D_Rectangle) l).p.getDistance(p, oom);
                        } else if (l instanceof V3D_LineSegment) {
                            return ((V3D_LineSegment) l).p.getDistance(p, oom);
                        } else {
                            return ((V3D_Point) l).getDistance(p, oom);
                        }
                    } else {
                        // lba - lbf
                        if (l instanceof V3D_Rectangle) {
                            return new V3D_Line(((V3D_Rectangle) l).b).getDistance(p, oom);
                        } else if (l instanceof V3D_LineSegment) {
                            return ((V3D_Line) l).getDistance(p, oom);
                        } else {
                            return ((V3D_Point) l).getDistance(p, oom);
                        }
                    }
                }
            } else {
                if (ycmax == 1) {
                    if (zcmin == -1) {
                        // ltf
                        if (f instanceof V3D_Rectangle) {
                            return ((V3D_Rectangle) f).q.getDistance(p, oom);
                        } else if (f instanceof V3D_LineSegment) {
                            return ((V3D_LineSegment) f).p.getDistance(p, oom);
                        } else {
                            return ((V3D_Point) f).getDistance(p, oom);
                        }
                    } else {
                        if (zcmax == 1) {
                            // lta
                            if (l instanceof V3D_Rectangle) {
                                return ((V3D_Rectangle) l).q.getDistance(p, oom);
                            } else if (l instanceof V3D_LineSegment) {
                                return ((V3D_LineSegment) l).p.getDistance(p, oom);
                            } else {
                                return ((V3D_Point) l).getDistance(p, oom);
                            }
                        } else {
                            // lta - ltf
                            if (l instanceof V3D_Rectangle) {
                                return new V3D_Line(((V3D_Rectangle) l).t).getDistance(p, oom);
                            } else if (l instanceof V3D_LineSegment) {
                                return ((V3D_Line) l).getDistance(p, oom);
                            } else {
                                return ((V3D_Point) l).getDistance(p, oom);
                            }
                        }
                    }
                } else {
                    if (zcmin == -1) {
                        // lbf - ltf
                        if (l instanceof V3D_Rectangle) {
                            return new V3D_Line(((V3D_Rectangle) l).l).getDistance(p, oom);
                        } else if (l instanceof V3D_LineSegment) {
                            return ((V3D_Line) l).getDistance(p, oom);
                        } else {
                            return ((V3D_Point) l).getDistance(p, oom);
                        }
                    } else {
                        if (zcmax == 1) {
                            // lba - lta
                            if (l instanceof V3D_Rectangle) {
                                return new V3D_Line(((V3D_Rectangle) l).l).getDistance(p, oom);
                            } else if (l instanceof V3D_LineSegment) {
                                return ((V3D_Line) l).getDistance(p, oom);
                            } else {
                                return ((V3D_Point) l).getDistance(p, oom);
                            }
                        } else {
                            // lba - lta - ltf - lbf
                            if (l instanceof V3D_Rectangle) {
                                return ((V3D_Plane) l).getDistance(p, oom);
                            } else if (l instanceof V3D_LineSegment) {
                                return ((V3D_Line) l).getDistance(p, oom);
                            } else {
                                return ((V3D_Point) l).getDistance(p, oom);
                            }
                        }
                    }
                }
            }
        } else {
            if (xcmax == 1) {
                if (ycmin == -1) {
                    if (zcmin == -1) {
                        // rbf
                        if (f instanceof V3D_Rectangle) {
                            return ((V3D_Rectangle) f).s.getDistance(p, oom);
                        } else if (f instanceof V3D_LineSegment) {
                            return ((V3D_LineSegment) f).q.getDistance(p, oom);
                        } else {
                            return ((V3D_Point) f).getDistance(p, oom);
                        }
                    } else {
                        if (zcmax == 1) {
                            // rba
                            if (a instanceof V3D_Rectangle) {
                                return ((V3D_Rectangle) a).p.getDistance(p, oom);
                            } else if (a instanceof V3D_LineSegment) {
                                return ((V3D_LineSegment) a).p.getDistance(p, oom);
                            } else {
                                return ((V3D_Point) a).getDistance(p, oom);
                            }
                        } else {
                            // rbf - rba
                            if (r instanceof V3D_Rectangle) {
                                return new V3D_Line(((V3D_Rectangle) r).b).getDistance(p, oom);
                            } else if (r instanceof V3D_LineSegment) {
                                return ((V3D_Line) r).getDistance(p, oom);
                            } else {
                                return ((V3D_Point) r).getDistance(p, oom);
                            }
                        }
                    }
                } else {
                    if (ycmax == 1) {
                        if (zcmin == -1) {
                            // rtf
                            if (f instanceof V3D_Rectangle) {
                                return ((V3D_Rectangle) f).r.getDistance(p, oom);
                            } else if (f instanceof V3D_LineSegment) {
                                return ((V3D_LineSegment) f).q.getDistance(p, oom);
                            } else {
                                return ((V3D_Point) f).getDistance(p, oom);
                            }
                        } else {
                            if (zcmax == 1) {
                                // rta
                                if (a instanceof V3D_Rectangle) {
                                    return ((V3D_Rectangle) a).q.getDistance(p, oom);
                                } else if (a instanceof V3D_LineSegment) {
                                    return ((V3D_LineSegment) a).p.getDistance(p, oom);
                                } else {
                                    return ((V3D_Point) a).getDistance(p, oom);
                                }
                            } else {
                                // rtf - rta
                                if (r instanceof V3D_Rectangle) {
                                    return new V3D_Line(((V3D_Rectangle) r).t).getDistance(p, oom);
                                } else if (r instanceof V3D_LineSegment) {
                                    return ((V3D_Line) r).getDistance(p, oom);
                                } else {
                                    return ((V3D_Point) r).getDistance(p, oom);
                                }
                            }
                        }
                    } else {
                        if (zcmin == -1) {
                            // rbf - rtf
                            if (f instanceof V3D_Rectangle) {
                                return new V3D_Line(((V3D_Rectangle) f).ri).getDistance(p, oom);
                            } else if (f instanceof V3D_LineSegment) {
                                return ((V3D_Line) f).getDistance(p, oom);
                            } else {
                                return ((V3D_Point) f).getDistance(p, oom);
                            }
                        } else {
                            if (zcmax == 1) {
                                // rba - rta
                                if (a instanceof V3D_Rectangle) {
                                    return new V3D_Line(((V3D_Rectangle) a).l).getDistance(p, oom);
                                } else if (a instanceof V3D_LineSegment) {
                                    return ((V3D_Line) a).getDistance(p, oom);
                                } else {
                                    return ((V3D_Point) a).getDistance(p, oom);
                                }
                            } else {
                                // rbf - rtf - rta - rba
                                if (r instanceof V3D_Rectangle) {
                                    return ((V3D_Plane) r).getDistance(p, oom);
                                } else if (r instanceof V3D_LineSegment) {
                                    return ((V3D_Line) r).getDistance(p, oom);
                                } else {
                                    return ((V3D_Point) r).getDistance(p, oom);
                                }
                            }
                        }
                    }
                }
            } else {
                if (ycmin == -1) {
                    if (zcmin == -1) {
                        // lbf - rbf
                        if (f instanceof V3D_Rectangle) {
                            return new V3D_Line(((V3D_Rectangle) f).b).getDistance(p, oom);
                        } else if (f instanceof V3D_LineSegment) {
                            return ((V3D_Line) f).getDistance(p, oom);
                        } else {
                            return ((V3D_Point) f).getDistance(p, oom);
                        }
                    } else {
                        if (zcmax == 1) {
                            // rba - lba
                            if (a instanceof V3D_Rectangle) {
                                return new V3D_Line(((V3D_Rectangle) a).b).getDistance(p, oom);
                            } else if (a instanceof V3D_LineSegment) {
                                return ((V3D_Line) a).getDistance(p, oom);
                            } else {
                                return ((V3D_Point) a).getDistance(p, oom);
                            }
                        } else {
                            // lba - lbf - rbf - rba
                            if (b instanceof V3D_Rectangle) {
                                return ((V3D_Plane) b).getDistance(p, oom);
                            } else if (b instanceof V3D_LineSegment) {
                                return ((V3D_Line) b).getDistance(p, oom);
                            } else {
                                return ((V3D_Point) b).getDistance(p, oom);
                            }
                        }
                    }
                } else {
                    // xany
                    if (ycmax == 1) {
                        if (zcmin == -1) {
                            // ltf - rtf
                            if (f instanceof V3D_Rectangle) {
                                return ((V3D_Rectangle) f).t.getDistance(p, oom);
                            } else if (f instanceof V3D_LineSegment) {
                                return ((V3D_LineSegment) f).q.getDistance(p, oom);
                            } else {
                                return ((V3D_Point) f).getDistance(p, oom);
                            }
                        } else {
                            if (zcmax == 1) {
                                // rta - lta
                                if (a instanceof V3D_Rectangle) {
                                    return new V3D_Line(((V3D_Rectangle) a).t).getDistance(p, oom);
                                } else if (a instanceof V3D_LineSegment) {
                                    return ((V3D_Line) a).getDistance(p, oom);
                                } else {
                                    return ((V3D_Point) a).getDistance(p, oom);
                                }
                            } else {
                                // ltf - lta - rta - rtf
                                if (t instanceof V3D_Rectangle) {
                                    return ((V3D_Plane) t).getDistance(p, oom);
                                } else if (r instanceof V3D_LineSegment) {
                                    return ((V3D_Line) t).getDistance(p, oom);
                                } else {
                                    return ((V3D_Point) t).getDistance(p, oom);
                                }
                            }
                        }
                    } else {
                        if (zcmin == -1) {
                            // lbf - ltf - rtf - rbf
                            if (f instanceof V3D_Rectangle) {
                                return ((V3D_Plane) f).getDistance(p, oom);
                            } else if (f instanceof V3D_LineSegment) {
                                return ((V3D_Line) f).getDistance(p, oom);
                            } else {
                                return ((V3D_Point) f).getDistance(p, oom);
                            }
                        } else {
                            if (zcmax == 1) {
                                // rba - rta - lta - lba
                                if (a instanceof V3D_Rectangle) {
                                    return ((V3D_Plane) a).getDistance(p, oom);
                                } else if (a instanceof V3D_LineSegment) {
                                    return ((V3D_Line) a).getDistance(p, oom);
                                } else {
                                    return ((V3D_Point) a).getDistance(p, oom);
                                }
                            } else {
                                // lba - lbf - rbf - rba
                                if (r instanceof V3D_Rectangle) {
                                    return ((V3D_Plane) b).getDistance(p, oom);
                                } else if (r instanceof V3D_LineSegment) {
                                    return ((V3D_LineSegment) b).getDistance(p, oom);
                                } else {
                                    return ((V3D_Point) b).getDistance(p, oom);
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}
