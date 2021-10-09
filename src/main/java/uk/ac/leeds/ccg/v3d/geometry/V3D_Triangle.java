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

import java.math.BigDecimal;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.math.Math_BigRational;

/**
 * For representing and processing triangles in 3D. For representing and
 * processing rectangles in 3D. A triangle has a non-zero area and does not have
 * to align with any of the axes. The corner points are {@link #p}, {@link #q}
 * and {@link #r}. The vector {@link #rp} stores the vector from {@link #r} to
 * {@link #qr}. The line segment {@link #lpq} is the line segment from
 * {@link #p} to {@link #q}. The line segment {@link #lqr} is the line segment
 * from {@link #q} to {@link #r}. The line segment {@link #lrp} is the line
 * segment from {@link #r} to {@link #p}. The following depicts a generic
 * triangle (no attempt has been made to draw this three dimensionally) {@code
 *      lpq
 *  p -------- q
 *    \      /
 *     \    / lqr
 *  lrp \  /
 *       \/
 *       r
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Triangle extends V3D_Plane implements V3D_2DShape {

    private static final long serialVersionUID = 1L;

    /**
     * The vector representing the move from {@link #r} to {@link #p}.
     */
    protected final V3D_Vector rp;

    /**
     * For storing the envelope.
     */
    protected V3D_Envelope en;

    /**
     * The line from {@link #p} to {@link #q}.
     */
    protected final V3D_LineSegment lpq;

    /**
     * The line from {@link #q} to {@link #r}.
     */
    protected final V3D_LineSegment lqr;

    /**
     * The line from {@link #r} to {@link #p}.
     */
    protected final V3D_LineSegment lrp;

    /**
     * Creates a new triangle.
     *
     * @param p A point that defines the triangle.
     * @param q A point that defines the triangle.
     * @param r A point that defines the triangle.
     */
    public V3D_Triangle(V3D_Point p, V3D_Point q, V3D_Point r, int oom) {
        super(p, q, r, oom);
        lpq = new V3D_LineSegment(p, q, oom);
        lqr = new V3D_LineSegment(q, r, oom);
        lrp = new V3D_LineSegment(r, p, oom);
        rp = new V3D_Vector(r, p, oom);
    }

    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
            en = new V3D_Envelope(-1, p, q, r);
        }
        return en;
    }

    /**
     * @param v The vector to apply.
     * @return a new rectangle.
     */
    @Override
    public V3D_Triangle apply(V3D_Vector v) {
        return new V3D_Triangle(p.apply(v), q.apply(v), r.apply(v), v.oom);
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

    private boolean isIntersectedBy0(V3D_Point pt) {
        if (lpq.isIntersectedBy(pt) || lqr.isIntersectedBy(pt)
                || lrp.isIntersectedBy(pt)) {
            return true;
        }
        V3D_Vector ppt = new V3D_Vector(p, pt, n.oom);
        V3D_Vector qpt = new V3D_Vector(q, pt, n.oom);
        V3D_Vector rpt = new V3D_Vector(r, pt, n.oom);
        V3D_Vector cp = pq.getCrossProduct(ppt);
        V3D_Vector cq = qr.getCrossProduct(qpt);
        V3D_Vector cr = rp.getCrossProduct(rpt);
        /**
         * If cp, cq and cr are all in the same direction then pt intersects.
         */
        Math_BigRational mp = cp.getMagnitudeSquared();
        Math_BigRational mq = cq.getMagnitudeSquared();
        V3D_Vector cpq = cp.add(cq);
        Math_BigRational mpq = cpq.getMagnitudeSquared();
        if (mpq.compareTo(mp) == 1 && mpq.compareTo(mq) == 1) {
            Math_BigRational mr = cr.getMagnitudeSquared();
            Math_BigRational mpqr = cpq.add(cr).getMagnitudeSquared();
            if (mpqr.compareTo(mr) == 1 && mpqr.compareTo(mpq) == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l) {
        if (super.isIntersectedBy(l)) {
            return isIntersectedBy0((V3D_Point) super.getIntersection(l));
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, boolean b) {
        if (this.getEnvelope().isIntersectedBy(l.getEnvelope())) {
            if (super.isIntersectedBy(l)) {
                V3D_Geometry g = super.getIntersection(l, b);
                if (g instanceof V3D_Point) {
                    return l.isIntersectedBy((V3D_Point) g);
                } else {
                    return l.isIntersectedBy((V3D_LineSegment) g, b);
                }
            }
        }
        return false;
    }

    /**
     * @param oom The minimum precision scale of the result.
     * @return The area of the triangle (rounded).
     */
    @Override
    public BigDecimal getArea(int oom) {
        return Math_BigDecimal.round(pq.getCrossProduct(qr).getMagnitude().toBigDecimal(oom - 1)
                .divide(BigDecimal.valueOf(2)), oom);
//        return pq.getCrossProduct(qr).getMagnitude(oom - 1).divide(
//                BigDecimal.valueOf(2));
    }

    /**
     * @param oom The minimum precision scale of the result.
     */
    @Override
    public BigDecimal getPerimeter(int oom) {
        int oomN1 = oom - 1;
        return Math_BigDecimal.round(lpq.getLength().toBigDecimal(oomN1)
                .add(lqr.getLength().toBigDecimal(oomN1))
                .add(lrp.getLength().toBigDecimal(oomN1)), oom);
    }

    /**
     * @param l The line to intersect with.
     * @return A point or line segment.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l) {
        V3D_Geometry g = (new V3D_Plane(this)).getIntersection(l);
        if (g == null) {
            return null;
        }
        V3D_Geometry enil = getEnvelope().getIntersection(l);
        if (enil == null) {
            return null;
        }
        /**
         * Get the intersection of the line and each edge of the triangle.
         */
        V3D_Geometry lpqi = lpq.getIntersection(l);
        V3D_Geometry lrpi = lrp.getIntersection(l);
        V3D_Geometry lqri = lqr.getIntersection(l);
        if (lpqi == null) {
            if (lqri == null) {
                if (lrpi == null) {
                    return null;
                } else {
                    return lrpi;
                }
            } else if (lqri instanceof V3D_LineSegment) {
                return lqri;
            } else {
                return new V3D_LineSegment((V3D_Point) lqri,
                        (V3D_Point) lrpi, n.oom);
            }
        } else if (lpqi instanceof V3D_LineSegment) {
            return lpqi;
        } else {
            if (lqri == null) {
                return new V3D_LineSegment((V3D_Point) lpqi,
                        (V3D_Point) lrpi, n.oom);
            } else if (lqri instanceof V3D_LineSegment) {
                return lqri;
            } else {
                if (lrpi == null) {
                    return new V3D_LineSegment((V3D_Point) lqri,
                            (V3D_Point) lpqi, n.oom);
                } else if (lrpi instanceof V3D_LineSegment) {
                    return lrpi;
                } else {
                    return V3D_FiniteGeometry.getGeometry((V3D_Point) lpqi, 
                            (V3D_Point) lrpi, (V3D_Point) lrpi, n.oom);
                }
            }
        }
    }

    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, boolean b) {
        boolean lip = isIntersectedBy(l.p);
        boolean liq = isIntersectedBy(l.q);
        if (lip) {
            if (liq) {
                return l;
            } else {
                V3D_Geometry li = getIntersection(l);
                if (li instanceof V3D_Point) {
                    return l.p;
                } else {
                    V3D_LineSegment lli = (V3D_LineSegment) li;
                    if (lli.p.equals(l.p)) {
                        return new V3D_LineSegment(l.p, lli.q, n.oom);
                    } else {
                        return new V3D_LineSegment(l.p, lli.p, n.oom);
                    }
                }
            }
        } else {
            V3D_Geometry li = getIntersection(l);
            if (liq) {
                if (li instanceof V3D_Point) {
                    return l.q;
                } else {
                    V3D_LineSegment lli = (V3D_LineSegment) li;
                    if (lli.q.equals(l.q)) {
                        return new V3D_LineSegment(l.q, lli.q, n.oom);
                    } else {
                        return new V3D_LineSegment(l.q, lli.p, n.oom);
                    }
                }
            } else {
                if (li instanceof V3D_Point) {
                    V3D_Point pli = (V3D_Point) li;
                    if (l.isIntersectedBy(pli)) {
                        return pli;
                    } else {
                        return null;
                    }
                } else {
                    return li;
                }
            }
        }
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l) {
        return getEnvelope().isIntersectedBy(l);
    }
}
