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

import java.math.BigDecimal;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * A collection of V3D_Point instances.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_PointsCollinear extends V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The collection of points.
     */
    protected final V3D_Vector[] rels;

    /**
     * Create a new instance.
     *
     * @param e What {@link #e} is set to.
     * @param offset The offset.
     * @param rels The point locations relative to the offset. 
     */
    public V3D_PointsCollinear(V3D_Environment e, V3D_Vector offset, V3D_Vector... rels) {
        super(e, offset);
        this.rels = rels;
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        int i;
        for (i = 0; i < rels.length - 1; i++) {
            s += rels[i].toString() + ", ";
        }
        s += rels[i].toString() + ")";
        return s;
    }

    /**
     * @return The Envelope
     */
    @Override
    public V3D_Envelope getEnvelope(int oom, RoundingMode rm) {
        if (en == null) {
            en = new V3D_Envelope(e, oom, rm, new V3D_Point(e, offset, rels[0]));
            for (int i = 1; i < rels.length; i++) {
                en = en.getIntersection(new V3D_Envelope(e, oom, rm, 
                        new V3D_Point(e, offset, rels[0])), oom, rm);
            }
        }
        return en;
    }

    @Override
    public V3D_Point[] getPoints(int oom, RoundingMode rm) {
        int n = rels.length;
        V3D_Point[] r = new V3D_Point[n];
        for(int i = 0; i < n; i ++) {
            r[i] = new V3D_Point(e, offset, rels[i]);
        }
        return r;
    }
    
    @Override
    public V3D_PointsCollinear rotate(V3D_Vector axisOfRotation, Math_BigRational theta, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
