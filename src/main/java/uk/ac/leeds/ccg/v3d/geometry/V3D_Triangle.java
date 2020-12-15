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
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.v3d.geometry.envelope.V3D_Envelope;

/**
 * V3D_Triangle This general class defines those planes that are all bounded by
 * their envelope exactly. It can be extended to consider finite planes that are
 * not triangular a particular shape, such as circles, ellipses, triangles,
 * squares, and other regular or irregular 2D shapes.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V3D_Triangle extends V3D_Plane implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The vector representing the move from {@link #r} to {@link #p}.
     */
    protected V3D_Vector rp;
    
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
    public V3D_Triangle(V3D_Point p, V3D_Point q, V3D_Point r) {
        super(p, q, r);
        lpq = new V3D_LineSegment(p, q);
        lqr = new V3D_LineSegment(q, r);
        lrp = new V3D_LineSegment(r, p);
    }

    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
            en = new V3D_Envelope(p, q, r);
        }
        return en;
    }

    /**
     * @param pt The point to intersect with.
     * @return A point or line segment.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt) {
        if (getEnvelope().isIntersectedBy(pt)) {
            if (super.isIntersectedBy(pt)) {
                if (lpq.isIntersectedBy(pt) || lqr.isIntersectedBy(pt) || lrp.isIntersectedBy(pt)) {
                    return true;
                }
                V3D_Vector ppt = new V3D_Vector(p, pt);
                V3D_Vector qpt = new V3D_Vector(q, pt);
                V3D_Vector rpt = new V3D_Vector(r, pt);
                V3D_Vector cp = pq.getCrossProduct(ppt);
                V3D_Vector cq = qr.getCrossProduct(qpt);
                V3D_Vector cr = rp.getCrossProduct(rpt);
                /**
                 * If cp, cq and cr are all in the same direction then pt
                 * intersects.
                 */
                BigRational mp = cp.getMagnitudeSquared();
                BigRational mq = cq.getMagnitudeSquared();
                V3D_Vector cpq = cp.add(cq);
                BigRational mpq = cpq.getMagnitudeSquared();
                if (mpq.compareTo(mp) == 1 && mpq.compareTo(mq) == 1) {
                    BigRational mr = cr.getMagnitudeSquared();
                    BigRational mpqr = cpq.add(cr).getMagnitudeSquared();
                    if (mpqr.compareTo(mr) == 1 && mpqr.compareTo(mpq) == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param scale The scale.
     * @param rm RoundingMode.
     * @return The area of the triangle (rounded).
     */
    public BigDecimal getArea(int scale, RoundingMode rm) {
        return Math_BigDecimal.divideRoundIfNecessary(
                pq.getCrossProduct(qr).getMagnitude(scale + 1, rm),
                BigDecimal.valueOf(2), scale, rm);
    }

    /**
     * @param l The line to intersect with.
     * @return A point or line segment.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l) {
        V3D_Geometry i = super.getIntersection(l);
        if (i == null) {
            return null;
        } else if (i instanceof V3D_Line) {
            V3D_Line li = (V3D_Line) i;
            V3D_Geometry enil = getEnvelope().getIntersection(l);
            if (enil == null) {
                return null;
            }
            /**
             * Get the intersection of the line and each edge of the triangle.
             */
            V3D_Geometry lpqi = lpq.getIntersection(li);
            if (lpqi == null) {
                // Check lqr, lrp
                V3D_Geometry lqri = lqr.getIntersection(li);
                if (lqri == null) {
                    // No need to check lrp.
                    return null;
                } else if (lqri instanceof V3D_LineSegment) {
                    return lqri;
                } else {
                    return new V3D_LineSegment((V3D_Point) lqri,
                                    (V3D_Point) lrp.getIntersection(li));
                }
            } else if (lpqi instanceof V3D_LineSegment) {
                return lpqi;
            } else {
                // Check lqr
                V3D_Geometry lqri = lqr.getIntersection(li);
                if (lqri == null) {
                    return new V3D_LineSegment((V3D_Point) lpqi,
                                    (V3D_Point) lrp.getIntersection(li));
                } else if (lqri instanceof V3D_LineSegment) {
                    return lqri;
                } else {
                    return new V3D_LineSegment((V3D_Point) lqri,
                                    (V3D_Point) lpqi);
                }                
            }
        } else {
            V3D_Point pi = (V3D_Point) i;
            if (this.isIntersectedBy(pi)) {
                return pi;
            } else {
                return null;
            }
        }
    }
    
    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, boolean b) {
        V3D_Geometry i = getIntersection(l);
        if (i == null) {
            return null;
        }
        if (i instanceof V3D_Point) {
            if (l.isIntersectedBy((V3D_Point) i)) {
                return i;
            } else {
                return null;
            }
        } else {
            return ((V3D_LineSegment) i).getIntersection(l, b);
        }
    }
}
