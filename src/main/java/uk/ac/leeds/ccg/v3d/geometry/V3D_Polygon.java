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

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
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
public class V3D_Polygon extends V3D_FiniteGeometry implements V3D_Face {

    private static final long serialVersionUID = 1L;

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
        super();
        this.parts = parts;
        this.holes = holes;
    }

    /**
     * Create a new instance.
     *
     * @param parts A non-empty list of coplanar triangles.
     */
    public V3D_Polygon(V3D_Triangle... parts) {
        super();
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

    @Override
    public V3D_Point[] getPoints() {
        int np = 0;
        for (var x : parts) {
            np += x.points.size();
        }
        for (var x : holes) {
            np += x.points.size();
        }
        V3D_Point[] r = new V3D_Point[np];
        int i = 0;
        for (var x : parts) {
            for (var y : x.points) {
                r[i] = new V3D_Point(y);
                i++;
            }
        }
        for (var x : holes) {
            for (var y : x.points) {
                r[i] = new V3D_Point(y);
                i++;
            }
        }
        return r;
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
    public V3D_Envelope getEnvelope(int oom) {
        if (en == null) {
            Iterator<V3D_ConvexHullCoplanar> ite = parts.iterator();
            en = ite.next().getEnvelope(oom);
            while (ite.hasNext()) {
                en = en.union(ite.next().getEnvelope(oom), oom);
            }
//            en = triangles.get(0).getEnvelope();
//            for (int i = 1; i < triangles.size(); i++) {
//                en = en.union(triangles.get(i).getEnvelope());
//            }
        }
        return en;
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V3D_Point pt, int oom, RoundingMode rm) {
        if (getEnvelope(oom).isIntersectedBy(pt, oom, rm)) {
            if (parts.get(0).triangles.get(0).getPl(oom, rm).isIntersectedBy(pt, oom, rm)) {
//                // Holes and parts could be checked in parallel.
//                if (holes != null) {
//                    for (V3D_ConvexHullCoplanar h : holes) {
//                        if (h.isAligned(pt, oom, rm)) {
//                            return false;
//                        }
//                    }
//                }
                for (V3D_ConvexHullCoplanar pa : parts) {
                    if (pa.isAligned(pt, oom, rm)) {
                        return true;
                    }
                }
//                return isIntersectedBy0(pt, oom, rm);
            }
        }
        return false;
    }

//    /**
//     * @param pt The point.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode if rounding is needed.
//     * @return {@code true} if this intersects with {@code pt}.
//     */
//    protected boolean isIntersectedBy0(V3D_Point pt, int oom, RoundingMode rm) {
//        // Holes and parts could be checked in parallel.
//        // Check holes first
//        if (holes != null) {
//            for (V3D_ConvexHullCoplanar h : holes) {
//                if (h.isIntersectedBy0(pt, oom, rm)) {
//                    return false;
//                }
//            }
//        }
//        for (V3D_ConvexHullCoplanar pa : parts) {
//            if (pa.isIntersectedBy0(pt, oom, rm)) {
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * This sums all the areas irrespective of any overlaps.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The area of the triangle (rounded).
     */
    @Override
    public BigRational getArea(int oom, RoundingMode rm) {
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    @Override
    public BigRational getPerimeter(int oom, RoundingMode rm) {
        throw new UnsupportedOperationException();
//        BigDecimal sum = BigDecimal.ZERO;
//        for (var t : triangles) {
//            sum = sum.add(t.getPerimeter(oom));
//        }
//        return sum;
    }

    @Override
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        if (en != null) {
            en.translate(v, oom, rm);
        }
        if (holes != null) {
            for (int i = 0; i < holes.size(); i++) {
                holes.get(i).translate(v, oom, rm);
            }
        }
        for (int i = 0; i < parts.size(); i++) {
            parts.get(i).translate(v, oom, rm);
        }
    }

    @Override
    public V3D_Polygon rotate(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd, 
            BigRational theta, int oom, RoundingMode rm) {
        ArrayList<V3D_ConvexHullCoplanar> rparts = new ArrayList<>();
        ArrayList<V3D_ConvexHullCoplanar> rholes = new ArrayList<>();
        if (holes != null) {
            for (int i = 0; i < holes.size(); i++) {
                rholes.add(holes.get(i).rotate(ray, uv, bd, theta, oom, rm));
            }
        }
        for (int i = 0; i < parts.size(); i++) {
            rparts.add(parts.get(i).rotate(ray, uv, bd, theta, oom, rm));
        }
        return new V3D_Polygon(rparts, rholes);
    }

    /**
     * Return the convex hull calculating it first if it has not already been
     * calculated.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return Get the convex hull.
     */
    public V3D_ConvexHullCoplanar getConvexHull(int oom, RoundingMode rm) {
        if (convexHull == null) {
            ArrayList<V3D_Point> pts = new ArrayList<>();
            for (var x : parts) {
                pts.addAll(x.points);
            }
            convexHull = new V3D_ConvexHullCoplanar(oom, rm,
                    parts.get(0).triangles.get(0).getPl(oom, rm).n,
                    pts.toArray(V3D_Point[]::new));
        }
        return convexHull;
    }

    @Override
    public boolean isIntersectedBy(V3D_Envelope aabb, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
