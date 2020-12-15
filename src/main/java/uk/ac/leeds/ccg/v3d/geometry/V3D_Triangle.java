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
 * V3D_Triangle This general class defines those planes that are all bounded
 * by their envelope exactly. It can be extended to consider finite planes that
 * are not triangular a particular shape, such as circles, ellipses, triangles, squares, and
 * other regular or irregular 2D shapes.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V3D_Triangle extends V3D_Plane implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

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
                V3D_Vector rp = new V3D_Vector(r, p);
                V3D_Vector pqppt = pq.getCrossProduct(ppt);
                V3D_Vector qrqpt = qr.getCrossProduct(qpt);
                V3D_Vector rprpt = rp.getCrossProduct(rpt);
                BigRational mpqppt = pqppt.getMagnitudeSquared();
                BigRational mqrqpt = qrqpt.getMagnitudeSquared();
                V3D_Vector pqpptqrqpt = pqppt.add(qrqpt);
                BigRational mpqpptqrqpt = pqpptqrqpt.getMagnitudeSquared();
                if (mpqpptqrqpt.compareTo(mpqppt) == 1 && mpqpptqrqpt.compareTo(mqrqpt) == 1) {
                    BigRational mrprpt = rprpt.getMagnitudeSquared();
                    BigRational mpqpptqrqptmrprpt = pqpptqrqpt.add(rprpt).getMagnitudeSquared();
                    if (mpqpptqrqptmrprpt.compareTo(mrprpt) == 1 && mpqpptqrqptmrprpt.compareTo(mpqpptqrqpt) == 1) {
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
}
