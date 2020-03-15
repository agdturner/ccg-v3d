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
package uk.ac.leeds.ccg.v3d.geometry.envelope;

import ch.obermuhlner.math.big.BigRational;
import java.util.Objects;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_FiniteGeometry;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Geometry;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;

/**
 * An envelope contains all the extreme values with respect to the X, Y and Z
 * axes. It is an axis aligned bounding box, which may have length of zero in
 * any direction. For a point the envelope is essentially the point.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Envelope extends V3D_Geometry implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The minimum x-coordinate.
     */
    private BigRational xMin;

    /**
     * The maximum x-coordinate.
     */
    private BigRational xMax;

    /**
     * The minimum y-coordinate.
     */
    private BigRational yMin;

    /**
     * The maximum y-coordinate.
     */
    private BigRational yMax;

    /**
     * The minimum z-coordinate.
     */
    private BigRational zMin;

    /**
     * The maximum z-coordinate.
     */
    private BigRational zMax;

    /**
     * The top face.
     */
    protected V3D_EnvelopeFaceTop t;
    
    /**
     * The left face.
     */
    protected V3D_EnvelopeFaceLeft l;
    
    /**
     * The aft face.
     */
    protected V3D_EnvelopeFaceAft a;
    
    /**
     * The right face.
     */
    protected V3D_EnvelopeFaceRight r;
        
    /**
     * The fore face.
     */
    protected V3D_EnvelopeFaceFore f;
        
    /**
     * The bottom face.
     */
    protected V3D_EnvelopeFaceBottom b;

    /**
     * @param e An envelop.
     */
    public V3D_Envelope(V3D_Envelope e) {
        super(e.e);
        yMin = e.yMin;
        yMax = e.yMax;
        xMin = e.xMin;
        xMax = e.xMax;
        zMin = e.zMin;
        zMax = e.zMax;
        init();
    }

    private void init() {
        V3D_Point tlf = new V3D_Point(e, getxMin(), getyMin(), getzMax());
        V3D_Point tla = new V3D_Point(e, getxMin(), getyMax(), getzMax());
        V3D_Point tar = new V3D_Point(e, getxMax(), getyMax(), getzMax());
        V3D_Point trf = new V3D_Point(e, getxMax(), getyMin(), getzMax());
        V3D_Point blf = new V3D_Point(e, getxMin(), getyMin(), getzMin());
        V3D_Point bla = new V3D_Point(e, getxMin(), getyMax(), getzMin());
        V3D_Point bar = new V3D_Point(e, getxMax(), getyMax(), getzMin());
        V3D_Point brf = new V3D_Point(e, getxMax(), getyMin(), getzMin());
        t = new V3D_EnvelopeFaceTop(tlf, tla, tar, trf);
        l = new V3D_EnvelopeFaceLeft(tlf, tla, bla, blf);
        a = new V3D_EnvelopeFaceAft(tla, tar, bar, bla);
        r = new V3D_EnvelopeFaceRight(trf, tar, bar, brf);
        f = new V3D_EnvelopeFaceFore(tlf, trf, brf, blf);
        b = new V3D_EnvelopeFaceBottom(blf, bla, bar, brf);
    }

    /**
     * @param e An envelop.
     * @param points The points used to form the envelop.
     */
    public V3D_Envelope(V3D_Environment e, V3D_Point... points) {
        super(e);
        if (points.length > 0) {
            xMin = points[0].x;
            xMax = points[0].x;
            yMin = points[0].y;
            yMax = points[0].y;
            zMin = points[0].z;
            zMax = points[0].z;
            for (int i = 1; i < points.length; i++) {
                xMin = BigRational.min(xMin, points[i].x);
                xMax = BigRational.max(xMax, points[i].x);
                yMin = BigRational.min(yMin, points[i].y);
                yMax = BigRational.max(yMax, points[i].y);
                zMin = BigRational.min(zMin, points[i].z);
                zMax = BigRational.max(zMax, points[i].z);
                init();
            }
        }
    }

    /**
     * @param e The vector environment.
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     * @param z The z-coordinate of a point.
     */
    public V3D_Envelope(V3D_Environment e, BigRational x, BigRational y,
            BigRational z) {
        super(e);
        xMin = x;
        xMax = x;
        yMin = y;
        yMax = y;
        zMin = z;
        zMax = z;
    }

    /**
     * @param e The vector environment.
     * @param xMin What {@link xMin} is set to.
     * @param xMax What {@link xMax} is set to.
     * @param yMin What {@link yMin} is set to.
     * @param yMax What {@link yMax} is set to.
     * @param zMin What {@link zMin} is set to.
     * @param zMax What {@link zMax} is set to.
     */
    public V3D_Envelope(V3D_Environment e, BigRational xMin, BigRational xMax,
            BigRational yMin, BigRational yMax, BigRational zMin,
            BigRational zMax) {
        super(e);
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "(xMin=" + getxMin().toString() + ", xMax=" + getxMax().toString() + ","
                + "yMin=" + getyMin().toString() + ", yMax=" + getyMax().toString() + ","
                + "zMin=" + getzMin().toString() + ", zMax=" + getzMax().toString() + ")";
    }

    /**
     * @param e The V3D_Envelope to union with this.
     * @return an Envelope which is {@code this} union {@code e}.
     */
    public V3D_Envelope union(V3D_Envelope e) {
        if (e.isContainedBy(this)) {
            return this;
        } else {
            return new V3D_Envelope(this.e, BigRational.min(e.getxMin(), getxMin()),
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
     * @param flag For distinguishing between this method and
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}.
     * @return either a point or line segment which is the intersection of
     * {@code l} and {@code this}.
     */
    public V3D_Geometry getIntersection(V3D_LineSegment l, boolean flag) {
        V3D_Envelope le = l.getEnvelope();
        if (le.isIntersectedBy(getEnvelope())) {
            V3D_Geometry i = getIntersection(l);
            if (i == null) {
                return null;
            } else if (i instanceof V3D_Point) {
                V3D_Point ip = (V3D_Point) i;
                if (l.isIntersectedBy(ip)) {
                    return ip;
                }
            } else {
                V3D_LineSegment ils = (V3D_LineSegment) i;
                return ils.getIntersection(l);
            }
        }
        return null;
    }

    /**
     * @param p The point to test for intersection.
     * @return {@code true} if this intersects with {@code p}
     */
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
        return new V3D_Envelope(e, BigRational.max(getxMin(), en.getxMin()),
                BigRational.min(getxMax(), en.getxMax()), BigRational.max(getyMin(), en.getyMin()),
                BigRational.min(getyMax(), en.getyMax()), BigRational.max(getzMin(), en.getzMin()),
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
                            if (flip.equals(blip)) {
                                return blip;
                            } else {
                                return new V3D_LineSegment(flip, blip);
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
                            return new V3D_LineSegment((V3D_Point) bli, rlip);
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
                                return new V3D_LineSegment((V3D_Point) bli, alip);
                            }
                        } else if (fli instanceof V3D_LineSegment) {
                            return fli;
                        } else {
                            return new V3D_LineSegment((V3D_Point) fli, alip);
                        }
                    } else {
                        return new V3D_LineSegment((V3D_Point) rli, alip);
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
                            return new V3D_LineSegment(tlip, blip);
                        } else if (fli instanceof V3D_LineSegment) {
                            return fli;
                        } else {
                            // Could have a corner so still need to check b
                            // so far here there is an intersection with t and f
                            // and there is no intersection with a, r, and l
                            // check for intersection with b
                            V3D_Geometry bli = b.getIntersection(li);
                            if (bli == null) {
                                V3D_Point blip = (V3D_Point) bli;
                                if (tlip.equals(blip)) {
                                    return tlip;
                                } else {
                                    return new V3D_LineSegment(tlip, blip);
                                }
                            } else if (bli instanceof V3D_LineSegment) {
                                return bli;
                            } else {
                                return new V3D_LineSegment((V3D_Point) bli, tlip);
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
                            return new V3D_LineSegment((V3D_Point) bli, rlip);
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
                                return new V3D_LineSegment((V3D_Point) bli, alip);
                            }
                        } else if (fli instanceof V3D_LineSegment) {
                            return fli;
                        } else {
                            return new V3D_LineSegment((V3D_Point) fli, alip);
                        }
                    } else {
                        return new V3D_LineSegment((V3D_Point) rli, alip);
                    }
                }
            } else if (lli instanceof V3D_LineSegment) {
                return lli;
            } else {
                // Still more checking to do...
                // intersection top and left could be at a corner and anyway need to check other faces...
                V3D_Point llip = (V3D_Point) lli;
                if (tlip.equals(llip)) {
                    return tlip;
                } else {
                    return new V3D_LineSegment(tlip, llip);
                }
            }
        }
        return null; // Should not get here remove after writing test cases.
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

}
