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
import java.util.List;
//import java.util.List;
import java.util.Objects;
import java.util.Set;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
//import java.util.ArrayList;

/**
 * Essentially this is just a collection of coplanar V3D_Triangles. Geographers
 * might consider this as a polygon or multi-polygon. For simplicity, let us
 * call these flat many sided shapes 'polygons'. Now, there can be different
 * ways to represent the same polygon as a set of triangles. Consider for
 * example four sided polygons (quadrilaterals). There are several types of
 * these, but three main ones in terms of different triangulations are:
 * <ol>
 * <li>Equal to their convex hull</li>
 * <li>Concave and not self intersecting</li>
 * <li>Concave and self intersecting</li>
 * </ol>
 * There is only one way to divide those that are concave and not self
 * intersecting into two triangles (not adding any further points): the division
 * of the quadrilateral is from the concave corner (to the opposite corner). For
 * those equal to their convex hull, there are two ways to divide the
 * quadrilateral into two triangles. One uses one pair of opposite corner
 * points. The other using the other pair of opposite corner points. Self
 * intersecting quadrilaterals can already be regarded as two triangles, but for
 * the triangulation an additional point, the point of intersection is needed.
 * Two polygons are equal if they completely intersect and cover exactly the
 * same area. It is possible that two polygons are equal even if one polygon has
 * more points than another. So, there is complication in testing if two
 * polygons are the same in that it is much more complicated to testing if two
 * triangles are the same.
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
     * @param b To distinguish the method from
     * {@link #equals(uk.ac.leeds.ccg.v3d.geometry.V3D_Plane)}.
     * @return {@code true} iff all the triangles are the same.
     */
    public boolean equals(V3D_TrianglesCoplanar i, boolean b) {
        /**
         * The triangularisation of this and i might be different and the number
         * of points in each might be different, but the areas they define might
         * be the same. For the areas to be the same each triangle from each
         * must either be in the other, or it must fully intersect the other.
         */
        if (!this.equals(i)) {
            // If they are not in the same plane, they are unequal!
            return false;
        }
        Iterator<V3D_Triangle> ite = triangles.iterator();
        while (ite.hasNext()) {
            V3D_Triangle t = ite.next();
            V3D_Geometry g = i.getIntersection(t, e.oom);
            if (g instanceof V3D_Triangle gt) {
                if (!t.equals(gt)) {
                    return false;
//                } else {
//                    int debug = 1;
                }
            }
        }
        Iterator<V3D_Triangle> iite = i.triangles.iterator();
        while (iite.hasNext()) {
            V3D_Triangle t = iite.next();
            V3D_Geometry g = getIntersection(t, e.oom);
            if (g instanceof V3D_Triangle gt) {
                if (!t.equals(gt)) {
                    return false;
//                } else {
//                    int debug = 1;
                }
            }
        }
        return true;
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
     * If the triangles are the same or their area can be simplified/represented
     * as a single triangle, then that triangle is returned, otherwise a
     * V3D_TrianglesCoplanar is returned.
     *
     * @param t1 A triangle.
     * @param t2 Another triangle.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return Either a triangle or a V3D_TrianglesCoplanar.
     */
    public static V3D_Geometry getGeometry(V3D_Triangle t1, V3D_Triangle t2, int oom) {
        if (t1.equals(t2)) {
            return t1;
        }
        if (t1.equals(t2)) {
            // Coplanar
            V3D_Geometry i = t1.getIntersection(t2, 0);
            if (i instanceof V3D_LineSegment l) {
                V3D_Point t1o = t1.getOpposite(l);
                V3D_Point t2o = t2.getOpposite(l);
                V3D_LineSegment lt = new V3D_LineSegment(t1o, t2o);
                V3D_Point lp = l.getP(oom);
                V3D_Point lq = l.getQ(oom);
                if (lt.isIntersectedBy(lp, oom)) {
                    return new V3D_Triangle(t1o, t2o, lq);
                } else {
                    if (lt.isIntersectedBy(lq, oom)) {
                        return new V3D_Triangle(t1o, t2o, lp);
                    }
                }
            }
        }
        return new V3D_TrianglesCoplanar(t1, t2);
    }

    /**
     * If the triangles can be simply simplified then a simplified version is
     * returned. This may be a single triangle or a V3D_TrianglesCoplanar. There
     * are cases of simplification that this will not handle. It is called
     * simply simplifying as it only works on simplifying triangles that have a
     * joining line segment. If those two triangles themselves form a triangle,
     * then a simplification takes place and this is done iteratively until all
     * such simplification is done. This however does not simplify the following
     * case where it can be seen that the 4 triangles represent an area that can
     * be given as a single triangle: null null null null null     {@code
     * *------*------*
     * |     /|     /
     * |    / |    /
     * |   /  |   /
     * |  /   |  /
     * | /    | /
     * |/     |/
     * *------*
     * |     /
     * |    /
     * |   /
     * |  /
     * | /
     * |/
     * *
     * }
     * Fortunately in simple triangle triangle intersection resulting in a
     * hexagon, pentagon or quadrilateral, whatever way these are represented
     * with triangles, when each triangle in the representation is intersected
     * with the other representation, the result can be simplified back to the
     * original and tested for equality. To simplify in a more comprehensive way
     * there are several strategies that could be adopted. One way is to
     * calculate convex hulls for subsets of triangles and see if these
     * themselves are triangles defining the same area as their constituent
     * parts. In the case above where the simple simplifying algorithm does not
     * work, there is still some alignment of constituent triangles which might
     * help...
     *
     * @param triangles The triangles to attempt to simplify.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return Either a triangle or a V3D_TrianglesCoplanar.
     */
    public static V3D_Geometry getGeometry(int oom, V3D_Triangle... triangles) {
        boolean look = true;
        boolean simplified = false;
        List<V3D_Triangle> triangleList = Arrays.asList(triangles);
        while (look) {
            for (int i = 0; i < triangles.length; i++) {
                V3D_Triangle t1 = triangles[i];
                for (int j = i; j < triangles.length; j++) {
                    V3D_Triangle t2 = triangles[j];
                    V3D_Geometry g = getGeometry(t1, t2, oom);
                    if (g instanceof V3D_Triangle gt) {
                        triangleList.remove(i);
                        triangleList.remove(j);
                        triangleList.add(gt);
                        simplified = true;
                    }
                }
            }
            if (simplified) {
                simplified = false;
            } else {
                look = false;
            }
        }
        int tls = triangleList.size();
        if (tls == 1) {
            return triangleList.get(0);
        } else {
            return new V3D_TrianglesCoplanar(triangleList.toArray(
                    new V3D_Triangle[tls]));
        }
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
    public boolean isIntersectedBy(V3D_LineSegment l, int oom) {
        if (getEnvelope().isIntersectedBy(l.getEnvelope())) {
            if (super.isIntersectedBy(l, oom)) {
                for (V3D_Triangle triangle : triangles) {
                    if (triangle.isIntersectedBy(l, oom)) {
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
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Triangle t, int oom) {
        HashSet<V3D_Triangle> t2s = new HashSet<>();
        for (V3D_Triangle t2 : this.triangles) {
            if (t2.isIntersectedBy(t, oom)) {
                t2s.add(t2);
            }
        }
        int size = t2s.size();
        switch (size) {
            case 0:
                return null;
            case 1:
                return t2s.iterator().next();
            case 2:
                Iterator<V3D_Triangle> ite = t2s.iterator();
                return getGeometry(ite.next(), ite.next(), oom);
            default:
                return getGeometry(oom, t2s.toArray(new V3D_Triangle[t2s.size()]));
        }
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
