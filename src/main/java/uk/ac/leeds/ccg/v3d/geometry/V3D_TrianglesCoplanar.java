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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
//import java.util.List;
import java.util.Objects;
import java.util.Set;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
//import java.util.ArrayList;

/**
 * Essentially this is just a collection of coplanar V3D_Triangles.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_TrianglesCoplanar extends V3D_Plane implements V3D_Face {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    protected V3D_Envelope en;

    /**
     * The collection of triangles.
     */
    protected final Set<V3D_Triangle> triangles;
    //protected final List<V3D_Triangle> triangles;
    //protected final V3D_Triangle[] triangles;

    /**
     * Create a new instance.
     *
     * @param triangles A non-empty list of coplanar triangles.
     */
    public V3D_TrianglesCoplanar(V3D_Triangle... triangles) {
        super(triangles[0].e, triangles[0].offset, triangles[0].p,
                triangles[0].q, triangles[0].r);
        this.triangles = new HashSet<>();
        this.triangles.addAll(Arrays.asList(triangles));
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        Iterator<V3D_Triangle> ite = triangles.iterator();
        s += ite.next().toString();
        while (ite.hasNext()) {
            s += ", " + ite.next();
        }
//        s += triangles.get(0).toString();
//        for (int i = 1; i < triangles.size(); i++) {
//            s += ", " + triangles.get(i);
//        }
        s += ")";
        return s;
    }

    @Override
    public boolean equals(Object o) {
        // Think about the case when there is a single triangle and whether this is equal to that triangle...
        if (o instanceof V3D_TrianglesCoplanar i) {
            return equals(i);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.triangles);
        return hash;
    }

    /**
     * Check if {@code this} is equal to {@code i}.
     *
     * @param i An instance to compare for equality.
     * @return {@code true} iff all the triangles are the same.
     */
    public boolean equals(V3D_TrianglesCoplanar i) {
        // Think about the case when there is a single triangle and whether this is equal to that triangle...
        /**
         * For a polygon, the triangularisation might be different. If we get
         * the intersection of a triangle in this with i, it should be the same
         * as that triangle. Likewise, if we get the intersection of a triangle
         * in i with this, it should be the same as that triangle. So, this is a
         * way to test. The test may be sped up by first checking for identical
         * triangles in each. These can be removed and the resulting ones that
         * are difference can be tested to see if they cover exactly the same
         * areas.
         */
        if (this.triangles.size() == i.triangles.size()) {
            Iterator<V3D_Triangle> ite = triangles.iterator();
            Iterator<V3D_Triangle> iite = i.triangles.iterator();
            while (ite.hasNext()) {

                V3D_Triangle t = ite.next();
                V3D_Triangle it = iite.next();
//                System.out.println(t);
//                System.out.println(it);
                if (!t.equals(it, true)) {
                    t.equals(it, true);
                    return false;
//                } else {
//                    int debug = 1;
                }

//                if (!ite.next().equals(iite.next(), true)) {
//                    return false;
//                }                
            }
            return true;
        }
        return false;
    }

    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
            Iterator<V3D_Triangle> ite = triangles.iterator();
            en = ite.next().getEnvelope();
            while (ite.hasNext()) {
                en = en.union(ite.next().getEnvelope());
            }
//            en = triangles.get(0).getEnvelope();
//            for (int i = 1; i < triangles.size(); i++) {
//                en = en.union(triangles.get(i).getEnvelope());
//            }
        }
        return en;
    }

    /**
     * @param pt The point to intersect with.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return A point or line segment.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom) {
        if (getEnvelope().isIntersectedBy(pt, oom)) {
            if (super.isIntersectedBy(pt, oom)) {
                return isIntersectedBy0(pt, oom);
            }
        }
        return false;
    }

    /**
     * @param pt The point.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@code true} if this intersects with {@code pt}.
     */
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
            if (triangles.stream().anyMatch(triangle -> (triangle.isIntersectedBy(l, oom)))) {
                return true;
            }
            return false;
            //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy(l, oom)));
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, boolean b) {
        if (getEnvelope().isIntersectedBy(l.getEnvelope())) {
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
        return getEnvelope().isIntersectedBy(l, oom);
    }

    /**
     * For this to work as expected, all triangles should have the same offset
     * (point for the rotation).
     *
     * @param axisOfRotation The axis of rotation.
     * @param theta The angle in radians.
     */
    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta) {
        for (V3D_Triangle t : triangles) {
            t.rotate(axisOfRotation, theta);
        }
    }
}
