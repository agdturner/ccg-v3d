/*
 * Copyright 2020 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain xxx copy of the License at
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
import uk.ac.leeds.ccg.math.Math_BigDecimal;

/**
 * For representing and processing rectangles in 3D. A rectangle has a non-zero
 * area and does not have to align with any of the axes. The corner points
 * {@link #p}, {@link #q}, {@link #r}, {@link #s} are arranged in a rectangular
 * fashion so that {@link #pq} is orthogonal to {@link #qr}. The left of a
 * rectangle {@link #l} is the line segment from {@link #p} to {@link #q}. The
 * top of a rectangle {@link #t} is the line segment from {@link #q} to
 * {@link #r}. The right of a rectangle {@link #ri} is the line segment from
 * {@link #r} to {@link #s}. The bottom of a rectangle {@link #b} is the line
 * segment from {@link #s} to {@link #p}. The following depicts a generic
 * rectangle (no attempt has been made to draw this three dimensionally) {@code
 *          t
 * q *-------------* r
 *   |             |
 * l |             | ri
 *   |             |
 * p *-------------* s
 *          b
 * }
 * Although what is depicted can be imagined as aligning with the x and y axes.
 * A rectangle can be defined by any three coordinates in 3D space so long as
 * the three points are at right angles.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Rectangle extends V3D_Plane implements V3D_2DShape {

    private static final long serialVersionUID = 1L;

    /**
     * The other corner of the rectangle. The others are {@link #p}, {@link #q},
     * and {@link #r}.
     */
    protected final V3D_Point s;

    /**
     * For storing the envelope
     */
    protected V3D_Envelope en;

    /**
     * For storing the vector from {@link #p} to {@link #q}.
     */
    protected final V3D_LineSegment l;

    /**
     * For storing the line segment from {@link #q} to {@link #r}.
     */
    protected final V3D_LineSegment t;

    /**
     * For storing the line segment from {@link #r} to {@link #s}.
     */
    protected final V3D_LineSegment ri;

    /**
     * For storing the line segment from {@link #s} to {@link #p}.
     */
    protected final V3D_LineSegment b;

    /**
     * @param p The bottom left corner of the rectangle.
     * @param q The top left corner of the rectangle.
     * @param r The top right corner of the rectangle.
     * @param s The bottom right corner of the rectangle.
     * @throws java.lang.RuntimeException iff the points do not define a
     * rectangle.
     */
    public V3D_Rectangle(V3D_Point p, V3D_Point q, V3D_Point r, V3D_Point s) {
        super(p, q, r);
        this.s = s;
        //en = new V3D_Envelope(p, q, r, s); Not initialised here as it causes a StackOverflowError
        l = new V3D_LineSegment(p, q);
        t = new V3D_LineSegment(q, r);
        ri = new V3D_LineSegment(r, s);
        b = new V3D_LineSegment(s, p);
        // Check for rectangle.
        if (pq.isZeroVector()) {
            if (qr.isZeroVector()) {
                // Rectangle is a point.
            } else {
                // Rectangle is a line.
            }
        } else {
            if (qr.isZeroVector()) {
                // Rectangle is a line.
            } else {
                // Rectangle has area.
                if (!(pq.isOrthogonal(qr))) {
                    throw new RuntimeException("The points do not define a rectangle.");
                }
            }
        }
    }
    
    /**
     * @param p The bottom left corner of the rectangle.
     * @param q The top left corner of the rectangle.
     * @param r The top right corner of the rectangle.
     * @param s The bottom right corner of the rectangle.
     * @throws java.lang.RuntimeException iff the points do not define a
     * rectangle.
     */
    public V3D_Rectangle(V3D_Envelope.Rectangle r) {
        this(new V3D_Point(r.p), new V3D_Point(r.q), new V3D_Point(r.r), new V3D_Point(r.s));
    }
    

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(p=" + p.toString()
                + ", q=" + q.toString() + ", r=" + r.toString()
                + ", s=" + s.toString() + ")";
    }

    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
            en = new V3D_Envelope(p, q, r, s);
        }
        return en;
    }

    /**
     * @param v The vector to apply.
     * @return a new rectangle.
     */
    @Override
    public V3D_Rectangle apply(V3D_Vector v) {
        return new V3D_Rectangle(p.apply(v), q.apply(v), r.apply(v), s.apply(v));
    }

    /**
     * @param pt The point to intersect with.
     * @return A point or line segment.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt) {
        if (getEnvelope().isIntersectedBy(pt)) {
            if (super.isIntersectedBy(pt)) {
                return isIntersectedBy0(pt);
            }
        }
        return false;
    }

    private boolean isIntersectedBy0(V3D_Line ls) {
        return t.isIntersectedBy(ls) || ri.isIntersectedBy(ls)
                || b.isIntersectedBy(ls) || l.isIntersectedBy(ls);
    }

    private boolean isIntersectedBy0(V3D_Point pt) {
        if (t.isIntersectedBy(pt) || ri.isIntersectedBy(pt)
                || b.isIntersectedBy(pt) || l.isIntersectedBy(pt)) {
            return true;
        }
        V3D_Vector ppt = new V3D_Vector(p, pt);
        V3D_Vector qpt = new V3D_Vector(q, pt);
        V3D_Vector rpt = new V3D_Vector(r, pt);
        V3D_Vector spt = new V3D_Vector(s, pt);
        V3D_Vector rs = new V3D_Vector(r, s);
        V3D_Vector sp = new V3D_Vector(s, p);
        V3D_Vector cp = pq.getCrossProduct(ppt);
        V3D_Vector cq = qr.getCrossProduct(qpt);
        V3D_Vector cr = rs.getCrossProduct(rpt);
        V3D_Vector cs = sp.getCrossProduct(spt);
        /**
         * If cp, cq, cr, and cs are all in the same direction then pt
         * intersects.
         */
        BigRational mp = cp.getMagnitudeSquared();
        BigRational mq = cq.getMagnitudeSquared();
        V3D_Vector cpq = cp.add(cq);
        BigRational mpq = cpq.getMagnitudeSquared();
        if (mpq.compareTo(mp) == 1 && mpq.compareTo(mq) == 1) {
            BigRational mr = cr.getMagnitudeSquared();
            V3D_Vector cpqr = cpq.add(cr);
            BigRational mpqr = cpqr.getMagnitudeSquared();
            if (mpqr.compareTo(mr) == 1 && mpqr.compareTo(mpq) == 1) {
                BigRational ms = cs.getMagnitudeSquared();
                BigRational mpqrs = cpqr.add(cs).getMagnitudeSquared();
                if (mpqrs.compareTo(ms) == 1 && mpqrs.compareTo(mpqr) == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l) {
        V3D_Plane pl = new V3D_Plane(this);
        if (pl.isIntersectedBy(l)) {
            V3D_Geometry g = pl.getIntersection(l);
            if (g instanceof V3D_Point) {
                return isIntersectedBy0((V3D_Point) g);
            } else {
                return isIntersectedBy0((V3D_Line) g);
            }
        }
        return false;
    }

    /**
     * Intersection test.
     *
     * @param l The line segment to test for intersection.
     * @param b This is a flag to distinguish this method from
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}.
     * @return {@code true} iff this rectangle is intersected by {@code l}
     */
    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, boolean b) {
        if (getEnvelope().isIntersectedBy(l.getEnvelope())) {
            V3D_Plane pl = new V3D_Plane(this);
            if (pl.isIntersectedBy(l)) {
                V3D_Geometry g = pl.getIntersection(l, b);
                if (g instanceof V3D_Point) {
                    return isIntersectedBy((V3D_Point) g);
                } else {
                    return isIntersectedBy((V3D_LineSegment) g, b);
                }
            }
        }
        return false;
    }

    /**
     * @param l The line to intersect with.
     * @return A point or line segment.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l) {
        V3D_Geometry g = new V3D_Plane(this).getIntersection(l);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_Point) {
                if (this.isIntersectedBy((V3D_Point) g)) {
                    return g;
                } else {
                    return null;
                }
            } else {
                V3D_Line li = (V3D_Line) g;
                /**
                 * This is left here as a comment as whilst in theory a Quick
                 * test of the envelope is a good idea (for speeding things up),
                 * there is a problem as V3D_Rectangles are parts of
                 * V3D_Envelopes and this results in a StackOverflow error as
                 * is. To overcome this a special type of aligned V3D_Rectangle
                 * could be defined and used for V3D_Envelopes for which the
                 * intersection calculations can be simplified.
                 */
//                // Quick test of the envelope.
//                V3D_Geometry enil = getEnvelope().getIntersection(l);
//                if (enil == null) {
//                    return null;
//                }
                /**
                 * Get the intersection of the line and each edge of the
                 * rectangle.
                 */
                V3D_Geometry ti = t.getIntersection(li);
                if (ti == null) {
                    // Check ri, b, l
                    V3D_Geometry rii = ri.getIntersection(li);
                    if (rii == null) {
                        // Check b, l
                        V3D_Geometry bi = b.getIntersection(li);
                        if (bi == null) {
                            // Check l
                            V3D_Geometry tli = this.l.getIntersection(li);
                            if (tli == null) {
                                return null;
                            } else {
                                return tli;
                            }
                        } else if (bi instanceof V3D_LineSegment) {
                            return bi;
                        } else {
                            // Check l
                            V3D_Geometry tli = this.l.getIntersection(li);
                            if (tli == null) {
                                return bi;
                            } else {
                                return new V3D_LineSegment((V3D_Point) bi,
                                        (V3D_Point) tli);
                            }
                        }
                    } else if (rii instanceof V3D_LineSegment) {
                        return rii;
                    } else {
                        // Check b, l
                        V3D_Geometry bi = b.getIntersection(li);
                        if (bi == null) {
                            // Check l
                            V3D_Geometry tli = this.l.getIntersection(li);
                            if (tli == null) {
                                return rii;
                            } else {
                                return new V3D_LineSegment((V3D_Point) rii,
                                        (V3D_Point) tli);
                            }
                        } else if (bi instanceof V3D_LineSegment) {
                            return bi;
                        } else {
                            // Check l
                            V3D_Geometry tli = this.l.getIntersection(li);
                            if (tli == null) {
                                V3D_Point riip = (V3D_Point) rii;
                                V3D_Point bip = (V3D_Point) bi;
                                if (riip.equals(bip)) {
                                    return bip;
                                } else {
                                    return new V3D_LineSegment(riip, bip);
                                }
                            } else {
                                return new V3D_LineSegment((V3D_Point) bi,
                                        (V3D_Point) tli);
                            }
                        }
                    }
                } else if (ti instanceof V3D_LineSegment) {
                    return ti;
                } else {
                    // Check ri, b, l
                    V3D_Geometry rii = ri.getIntersection(li);
                    if (rii == null) {
                        // Check b, l
                        V3D_Geometry bi = b.getIntersection(li);
                        if (bi == null) {
                            // Check l
                            V3D_Geometry tli = this.l.getIntersection(li);
                            if (tli == null) {
                                return ti;
                            } else {
                                V3D_Point tlip = (V3D_Point) tli;
                                V3D_Point tip = (V3D_Point) ti;
                                if (tlip.equals(tip)) {
                                    return tlip;
                                } else {
                                    return new V3D_LineSegment(tlip, tip);
                                }
                            }
                        } else if (bi instanceof V3D_LineSegment) {
                            return bi;
                        } else {
                            return new V3D_LineSegment((V3D_Point) ti, (V3D_Point) bi);
                        }
                    } else {
                        V3D_Point tip = (V3D_Point) ti;
                        V3D_Point riip = (V3D_Point) rii;
                        if (tip.equals(riip)) {
                            // Check b, l
                            V3D_Geometry sri = b.getIntersection(li);
                            if (sri == null) {
                                // Check l
                                V3D_Geometry tli = this.l.getIntersection(li);
                                if (tli == null) {
                                    return rii;
                                } else {
                                    return new V3D_LineSegment(riip,
                                            (V3D_Point) tli);
                                }
                            } else if (sri instanceof V3D_LineSegment) {
                                return sri;
                            } else {
                                return new V3D_LineSegment(riip, (V3D_Point) sri);
                            }
                        } else {
                            return new V3D_LineSegment(riip, tip);
                        }
                    }
                }
            }
        }
    }

    /**
     * Calculates and returns the intersections between {@code this} and
     * {@code l}.
     *
     * @param l The line segment to test for intersection.
     * @param b This is a flag to distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}.
     * @return The intersection or {@code null} iff there is no intersection.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, boolean b) {
        V3D_Geometry g = getIntersection(l);
        if (g == null) {
            return null;
        }
        if (g instanceof V3D_Point) {
            if (l.isIntersectedBy((V3D_Point) g)) {
                return g;
            } else {
                return null;
            }
        } else {
            return l.getIntersection(((V3D_LineSegment) g), b);
        }
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l) {
        return getEnvelope().isIntersectedBy(l);
    }

    @Override
    public BigDecimal getPerimeter(int oom) {
        int oomn2 = oom - 2;
        return l.getLength(oomn2).add(t.getLength(oomn2))
                .multiply(BigDecimal.valueOf(2));
    }

    @Override
    public BigDecimal getArea(int oom) {
        int oomn2 = oom - 2;
        return Math_BigDecimal.round(l.v.getMagnitude(oomn2)
                .multiply(t.v.getMagnitude(oomn2)), oom);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param p A point.
     * @param oom The order of magnitude of the precision.
     * @return The distance from {@code this} to {@code p}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        if (this.isIntersectedBy(p)) {
            return BigDecimal.ZERO;
        }
        BigDecimal dp = super.getDistance(p, oom);
        BigDecimal ld = l.getDistance(p, oom);
        BigDecimal td = t.getDistance(p, oom);
        BigDecimal rd = r.getDistance(p, oom);
        BigDecimal bd = b.getDistance(p, oom);
        if (dp.compareTo(ld) == 0 && dp.compareTo(td) == 0
                && dp.compareTo(rd) == 0 && dp.compareTo(bd) == 0) {
            return dp;
        } else {
            return Math_BigDecimal.min(ld, td, rd, bd);
        }
    }
}
