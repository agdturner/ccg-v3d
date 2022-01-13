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
import uk.ac.leeds.ccg.math.number.Math_BigRational;

/**
 * Essentially this is just a collection of coplanar V3D_Triangles.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Points extends V3D_Geometry {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    protected V3D_Envelope en;

    /**
     * The collection of points.
     */
    protected final V3D_Vector[] rels;

    /**
     * Create a new instance.
     *
     * @param offset The offset.
     * @param oom The Order of Magnitude for the initialisation.
     * @param rels The point locations relative to the offset. 
     */
    public V3D_Points(V3D_Vector offset, int oom, V3D_Vector... rels) {
        super(offset, oom);
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
     * 
     * @param oom The Order of Magnitude for the precision.
     * @return The Envelope
     */
    public V3D_Envelope getEnvelope(int oom) {
        if (en == null) {
            en = new V3D_Envelope(oom, new V3D_Point(offset, rels[0], oom));
            for (int i = 1; i < rels.length; i++) {
                en = en.getIntersection(new V3D_Envelope(oom, new V3D_Point(offset, rels[0], oom)));
            }
        }
        return en;
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta) {
        for (V3D_Vector rel : rels) {
            rel.rotate(axisOfRotation, theta, bI, oom);
        }
    }
}
