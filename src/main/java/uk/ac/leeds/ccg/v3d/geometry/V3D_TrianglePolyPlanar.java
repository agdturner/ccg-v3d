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
//import java.util.ArrayList;

/**
 * Essentially this is just a collection of coplanar V3D_Triangles.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_TrianglePolyPlanar extends V3D_Plane implements V3D_2DShape {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    protected V3D_Envelope en;

    /**
     * The collection of triangles.
     */
    //protected final List<V3D_Triangle> triangles;
    protected final V3D_Triangle[] triangles;

    /**
     * Create a new instance.
     *
     * @param triangles A non-empty list of coplanar triangles.
     * @param oom The Order of Magnitude for the initialisation.
     */
    public V3D_TrianglePolyPlanar(int oom, V3D_Triangle... triangles) {
        super(triangles[0].getP(oom), triangles[0].getQ(oom),
                triangles[0].getR(oom), oom);
        this.triangles = triangles;
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        int i;
        for (i = 0; i < triangles.length - 1; i++) {
            s += triangles[i].toString() + ", ";
        }
        s += triangles[i].toString() + ")";
        return s;
    }

    @Override
    public V3D_Envelope getEnvelope(int oom) {
        if (en == null) {
            en = triangles[0].getEnvelope(oom);
            for (int i = 1; i < triangles.length; i++) {
                en = en.union(triangles[i].getEnvelope(oom));
            }
        }
        return en;
    }

    /**
     * @param v The vector to apply.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return a new rectangle.
     */
    @Override
    public V3D_TrianglePolyPlanar apply(V3D_Vector v, int oom) {
        V3D_Triangle[] nt = new V3D_Triangle[triangles.length];
        for (int i = 0; i < triangles.length; i++) {
            nt[i] = triangles[i].apply(v, oom);
        }
        return new V3D_TrianglePolyPlanar(oom, nt);
    }

    /**
     * @param pt The point to intersect with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return A point or line segment.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom) {
        if (getEnvelope(oom).isIntersectedBy(pt, oom)) {
            if (super.isIntersectedBy(pt, oom)) {
                return isIntersectedBy0(pt, oom);
            }
        }
        return false;
    }

    protected boolean isIntersectedBy0(V3D_Point pt, int oom) {
        for (V3D_Triangle triangle : triangles) {
            if (triangle.isIntersectedBy0(pt, oom)) {
                return true;
            }
        }
        return false;
        //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy0(pt, oom)));
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        if (super.isIntersectedBy(l, oom)) {
            for (V3D_Triangle triangle : triangles) {
                if (triangle.isIntersectedBy(l, oom)) {
                    return true;
                }
            }
            return false;
            //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy(l, oom)));
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, boolean b) {
        if (this.getEnvelope(oom).isIntersectedBy(l.getEnvelope(oom))) {
            if (super.isIntersectedBy(l, oom)) {
                for (V3D_Triangle triangle : triangles) {
                    if (triangle.isIntersectedBy(l, oom, b)) {
                        return true;
                    }
                }
                return false;
                //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy(l, oom, b)));
            }
        }
        return false;
    }

    /**
     * This sums all the areas irrespective of any overlaps.
     *
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The area of the triangle (rounded).
     */
    @Override
    public BigDecimal getArea(int oom) {
        BigDecimal sum = BigDecimal.ZERO;
        for (var t : triangles) {
            sum = sum.add(t.getArea(oom));
        }
        return sum;
    }

    /**
     * This sums all the perimeters irrespective of any overlaps.
     *
     * @param oom The Order of Magnitude for the precision of the calculation.
     */
    @Override
    public BigDecimal getPerimeter(int oom) {
        BigDecimal sum = BigDecimal.ZERO;
        for (var t : triangles) {
            sum = sum.add(t.getPerimeter(oom));
        }
        return sum;
    }

    /**
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return A point or line segment.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {    
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom, boolean b) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
        return getEnvelope(oom).isIntersectedBy(l, oom);
    }
}
