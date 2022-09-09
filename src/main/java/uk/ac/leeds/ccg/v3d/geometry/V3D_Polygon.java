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
import java.util.ArrayList;
import java.util.Iterator;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
//import java.util.ArrayList;

/**
 * Comprising two collections of V3D_ConvexHullCoplanar one representing parts
 * and the other representing holes. Parts may intersect. Holes may intersect.
 * So in OGC terms, this is pore like a multi-polygon as holes may effectively
 * split parts and they may collectively cover all parts. Simplification of a
 * V3D_Polygon may be possible and may involve creating new geometries to
 * represent parts individually which may involve some rounding.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Polygon extends V3D_Plane implements V3D_Face {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    protected V3D_Envelope en;

    /**
     * The collection of non-holes.
     */
    protected final ArrayList<V3D_ConvexHullCoplanar> parts;

    /**
     * The collection of holes.
     */
    protected final ArrayList<V3D_ConvexHullCoplanar> holes;

    /**
     * For storing the convex hull of the parts.
     */
    protected V3D_ConvexHullCoplanar convexHull;

    /**
     * Create a new instance.
     *
     * @param parts A non-empty list of V3D_ConvexHullCoplanar parts.
     * @param holes A potentially empty list of V3D_ConvexHullCoplanar holes.
     */
    public V3D_Polygon(ArrayList<V3D_ConvexHullCoplanar> parts,
            ArrayList<V3D_ConvexHullCoplanar> holes) {
        super(parts.get(0).triangles.get(0));
        this.parts = parts;
        this.holes = holes;
    }

    /**
     * Create a new instance.
     *
     * @param triangles A non-empty list of coplanar triangles.
     */
    public V3D_Polygon(V3D_Triangle... parts) {
        super(parts[0]);
        this.parts = new ArrayList<>();
        this.holes = null;
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        {
            s += "\nParts(\n";
            Iterator<V3D_ConvexHullCoplanar> ite = parts.iterator();
            s += ite.next().toString();
            while (ite.hasNext()) {
                s += ", " + ite.next();
            }
            s += "\n)\n";
        }
        {
            if (holes != null) {
                s += "\nHoles(\n";
                Iterator<V3D_ConvexHullCoplanar> ite = holes.iterator();
                s += ite.next().toString();
                while (ite.hasNext()) {
                    s += ", " + ite.next();
                }
                s += "\n)\n";
            }
        }
        s += "\n)";
        return s;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (o instanceof V3D_Polygon i) {
//            return equals(i);
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 53 * hash + Objects.hashCode(this.triangles);
//        return hash;
//    }
//
//    /**
//     * Check if {@code this} is equal to {@code i}.
//     *
//     * @param i An instance to compare for equality.
//     * @param b To distinguish the method from
//     * {@link #equals(uk.ac.leeds.ccg.v3d.geometry.V3D_Plane)}.
//     * @return {@code true} iff all the triangles are the same.
//     */
//    public boolean equals(V3D_Polygon i, boolean b) {
//        /**
//         * The triangularisation of this and i might be different and the number
//         * of points in each might be different, but the areas they define might
//         * be the same. For the areas to be the same each triangle from each
//         * must either be in the other, or it must fully intersect the other.
//         */
//        if (!new V3D_Plane(this).equals(new V3D_Plane(i))) {
//            // If they are not in the same plane, they are unequal!
//            return false;
//        }
//        for (V3D_Triangle t : triangles) {
//            V3D_Geometry g = i.getIntersection(t, e.oom);
//            if (g instanceof V3D_Triangle gt) {
//                if (!t.equals(gt)) {
//                    return false;
////                } else {
////                    int debug = 1;
//                }
//            }
//        }
//        for (V3D_Triangle t : i.triangles) {
//            V3D_Geometry g = getIntersection(t, e.oom);
//            if (g instanceof V3D_Triangle gt) {
//                if (!t.equals(gt)) {
//                    return false;
////                } else {
////                    int debug = 1;
//                }
//            }
//        }
//        return true;
//    }
    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
            Iterator<V3D_ConvexHullCoplanar> ite = parts.iterator();
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
        // Holes and parts could be checked in parallel.
        // Check holes first
        if (holes != null) {
            for (V3D_ConvexHullCoplanar h : holes) {
                if (h.isIntersectedBy0(pt, oom)) {
                    return false;
                }
            }
        }
        for (V3D_ConvexHullCoplanar pa : parts) {
            if (pa.isIntersectedBy0(pt, oom)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        throw new UnsupportedOperationException();
//        if (super.isIntersectedBy(l, oom)) {
//            //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy(l, oom)));        
//            return triangles.stream().anyMatch(t -> (t.isIntersectedBy(l, oom)));
//        }
//        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException();
//        if (getEnvelope().isIntersectedBy(l.getEnvelope())) {
//            if (super.isIntersectedBy(l, oom)) {
//                for (V3D_Triangle triangle : triangles) {
//                    if (triangle.isIntersectedBy(l, oom)) {
//                        return true;
//                    }
//                }
//                return false;
//                //return triangles.parallelStream().anyMatch(t -> (t.isIntersectedBy(l, oom, b)));
//            }
//        }
//        return false;
    }

    /**
     * This sums all the areas irrespective of any overlaps.
     *
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The area of the triangle (rounded).
     */
    @Override
    public BigDecimal getArea(int oom) {
        throw new UnsupportedOperationException();
//        BigDecimal sum = BigDecimal.ZERO;
//        for (var t : triangles) {
//            sum = sum.add(t.getArea(oom));
//        }
//        return sum;
    }

    /**
     * This sums all the perimeters irrespective of any overlaps.
     *
     * @param oom The Order of Magnitude for the precision of the calculation.
     */
    @Override
    public BigDecimal getPerimeter(int oom) {
        throw new UnsupportedOperationException();
//        BigDecimal sum = BigDecimal.ZERO;
//        for (var t : triangles) {
//            sum = sum.add(t.getPerimeter(oom));
//        }
//        return sum;
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
        throw new UnsupportedOperationException();
//        // Create a set all the intersecting triangles from this.
//        HashSet<V3D_Triangle> t2s = new HashSet<>();
//        for (V3D_Triangle t2 : this.triangles) {
//            if (t2.isIntersectedBy(t, oom)) {
//                t2s.add(t2);
//            }
//        }
//        int size = t2s.size();
//        switch (size) {
//            case 0 -> {
//                return null;
//            }
//            case 1 -> {
//                return t2s.iterator().next();
//            }
//            case 2 -> {
//                Iterator<V3D_Triangle> ite = t2s.iterator();
//                return getGeometry(ite.next(), ite.next(), oom);
//            }
//            default -> {
//                //return getGeometry(oom, t2s.toArray(V3D_Triangle[]::new));
//                return getGeometry(oom, t2s);
//            }
//        }
//        switch (size) {
//            case 0:
//                return null;
//            case 1:
//                return t2s.iterator().next();
//            case 2:
//                Iterator<V3D_Triangle> ite = t2s.iterator();
//                return getGeometry(ite.next(), ite.next(), oom);
//            default:
//                return getGeometry(oom, t2s.toArray(V3D_Triangle[]::new));
    }

//    @Override
//    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
//        return getEnvelope().isIntersectedBy(l, oom);
//    }
    /**
     * For this to work as expected, all triangles should have the same offset
     * (point for the rotation).
     *
     * @param axisOfRotation The axis of rotation.
     * @param theta The angle in radians.
     */
    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta) {
        if (holes != null) {
            for (var x : holes) {
                x.rotate(axisOfRotation, theta);
            }
        }
        for (var x : parts) {
            x.rotate(axisOfRotation, theta);
        }
    }

    /**
     * Return the convex hull calculating it first if it has not already been
     * calculated.
     *
     * @return Get the convex hull.
     */
    public V3D_ConvexHullCoplanar getConvexHull() {
        if (convexHull == null) {
            ArrayList<V3D_Point> pts = new ArrayList<>();
            for (var x : parts) {
                pts.addAll(x.points);
            }
            convexHull = new V3D_ConvexHullCoplanar(n, pts.toArray(V3D_Point[]::new));
        }
        return convexHull;
    }
}
