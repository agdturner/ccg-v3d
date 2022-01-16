/*
 * Copyright 2019 Andy Turner, University of Leeds.
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;

/**
 * For representing multiple collinear line segments.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineSegmentsCollinear extends V3D_Line implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope of this.
     */
    protected V3D_Envelope en;

    /**
     * The collinear line segments.
     */
    public final Set<V3D_LineSegment> lineSegments;

    /**
     * Create a new instance.
     *
     * @param lineSegments What {@code #lineSegments} is set to.
     */
    public V3D_LineSegmentsCollinear(V3D_LineSegment... lineSegments) {
        super(lineSegments[0]);
        this.lineSegments = new HashSet<>();
        this.lineSegments.addAll(Arrays.asList(lineSegments));
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        Iterator<V3D_LineSegment> ite = lineSegments.iterator();
        if (!lineSegments.isEmpty()) {
            s += ite.next().toString();
        }
        while (ite.hasNext()) {
            s += ", " + ite.next().toString();
        }
        return s + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_LineSegmentsCollinear lsc) {
            return equals(lsc);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.lineSegments);
        return hash;
    }

    /**
     * @param l The line segment to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_LineSegmentsCollinear l) {
        if (!l.lineSegments.containsAll(lineSegments)) {
            return false;
        }
        return lineSegments.containsAll(l.lineSegments);
    }

    /**
     * @param oom The Order of Magnitude for the calculation.
     * @return The length of {@code this}.
     */
    public Math_BigRationalSqrt getLength(int oom) {
        return new Math_BigRationalSqrt(getLength2(oom), oom);
    }

    /**
     * @param oom The Order of Magnitude for the calculation.
     * @return The length of {@code this} squared.
     */
    public Math_BigRational getLength2(int oom) {
        Math_BigRational r = Math_BigRational.ZERO;
        lineSegments.forEach(x -> {
            r.add(x.getLength2(oom));
        });
        return r;
    }

    @Override
    public V3D_Envelope getEnvelope() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public boolean isIntersectedBy(V3D_Point p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom, boolean flag) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
