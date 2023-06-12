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
package uk.ac.leeds.ccg.v3d.geometry.d;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Comprising two collections of V3D_ConvexHullCoplanarDouble one representing parts
 * and the other representing holes. Parts may intersect. Holes may intersect.
 * So in OGC terms, this is pore like a multi-polygon as holes may effectively
 * split parts and they may collectively cover all parts. Simplification of a
 * V3D_Polygon may be possible and may involve creating new geometries to
 * represent parts individually which may involve some rounding.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_PolygonDouble extends V3D_FiniteGeometryDouble 
        implements V3D_FaceDouble {

    private static final long serialVersionUID = 1L;

    /**
     * The collection of non-holes.
     */
    protected final ArrayList<V3D_ConvexHullCoplanarDouble> parts;

    /**
     * The collection of holes.
     */
    protected final ArrayList<V3D_ConvexHullCoplanarDouble> holes;

    /**
     * For storing the convex hull of the parts.
     */
    protected V3D_ConvexHullCoplanarDouble convexHull;

    /**
     * Create a new instance.
     *
     * @param parts A non-empty list of V3D_ConvexHullCoplanarDouble parts.
     * @param holes A potentially empty list of V3D_ConvexHullCoplanarDouble holes.
     */
    public V3D_PolygonDouble(ArrayList<V3D_ConvexHullCoplanarDouble> parts,
            ArrayList<V3D_ConvexHullCoplanarDouble> holes) {
        super();
        this.parts = parts;
        this.holes = holes;
    }

    /**
     * Create a new instance.
     *
     * @param parts A non-empty list of coplanar triangles.
     */
    public V3D_PolygonDouble(V3D_TriangleDouble... parts) {
        super();
        this.parts = new ArrayList<>();
        this.holes = null;
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        {
            s += "\nParts(\n";
            Iterator<V3D_ConvexHullCoplanarDouble> ite = parts.iterator();
            s += ite.next().toString();
            while (ite.hasNext()) {
                s += ", " + ite.next();
            }
            s += "\n)\n";
        }
        {
            if (holes != null) {
                s += "\nHoles(\n";
                Iterator<V3D_ConvexHullCoplanarDouble> ite = holes.iterator();
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
    public V3D_PointDouble[] getPoints() {
        int np = 0;
        for (var x : parts) {
            np += x.points.size();
        }
        for (var x : holes) {
            np += x.points.size();
        }
        V3D_PointDouble[] r = new V3D_PointDouble[np];
        int i = 0;
        for (var x : parts) {
            for (var y : x.points) {
                r[i] = new V3D_PointDouble(y);
                i++;
            }
        }
        for (var x : holes) {
            for (var y : x.points) {
                r[i] = new V3D_PointDouble(y);
                i++;
            }
        }
        return r;
    }

    @Override
    public V3D_EnvelopeDouble getEnvelope() {
        if (en == null) {
            Iterator<V3D_ConvexHullCoplanarDouble> ite = parts.iterator();
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
     * Identify if this is intersected by point {@code p}.
     *
     * @param pt The point to test for intersection with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V3D_PointDouble pt, double epsilon) {
        if (getEnvelope().isIntersectedBy(pt)) {
            if (parts.get(0).triangles.get(0).pl.isIntersectedBy(pt, epsilon)) {
//                // Holes and parts could be checked in parallel.
//                if (holes != null) {
//                    for (V3D_ConvexHullCoplanarDouble h : holes) {
//                        if (h.isAligned(pt)) {
//                            return false;
//                        }
//                    }
//                }
                for (V3D_ConvexHullCoplanarDouble pa : parts) {
                    if (pa.isAligned(pt, epsilon)) {
                        return true;
                    }
                }
//                return isIntersectedBy0(pt);
            }
        }
        return false;
    }
    
    /**
     * This sums all the areas irrespective of any overlaps.
     *
     * @return The area of the triangle (rounded).
     */
    @Override
    public double getArea() {
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
     */
    @Override
    public double getPerimeter() {
        throw new UnsupportedOperationException();
//        BigDecimal sum = BigDecimal.ZERO;
//        for (var t : triangles) {
//            sum = sum.add(t.getPerimeter(oom));
//        }
//        return sum;
    }

    @Override
    public void translate(V3D_VectorDouble v) {
        super.translate(v);
        if (en != null) {
            en.translate(v);
        }
        if (holes != null) {
            for (int i = 0; i < holes.size(); i++) {
                holes.get(i).translate(v);
            }
        }
        for (int i = 0; i < parts.size(); i++) {
            parts.get(i).translate(v);
        }
    }

    @Override
    public V3D_PolygonDouble rotate(V3D_RayDouble ray, V3D_VectorDouble uv,
            double theta, double epsilon) {
        ArrayList<V3D_ConvexHullCoplanarDouble> rparts = new ArrayList<>();
        ArrayList<V3D_ConvexHullCoplanarDouble> rholes = new ArrayList<>();
        if (holes != null) {
            for (int i = 0; i < holes.size(); i++) {
                rholes.add(holes.get(i).rotate(ray, uv, theta, epsilon));
            }
        }
        for (int i = 0; i < parts.size(); i++) {
            rparts.add(parts.get(i).rotate(ray, uv, theta, epsilon));
        }
        return new V3D_PolygonDouble(rparts, rholes);
    }

    /**
     * Return the convex hull calculating it first if it has not already been
     * calculated.
     *
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return Get the convex hull.
     */
    public V3D_ConvexHullCoplanarDouble getConvexHull(double epsilon) {
        if (convexHull == null) {
            ArrayList<V3D_PointDouble> pts = new ArrayList<>();
            for (var x : parts) {
                pts.addAll(x.points);
            }
            convexHull = new V3D_ConvexHullCoplanarDouble(
                    parts.get(0).triangles.get(0).pl.n, epsilon,
                    pts.toArray(V3D_PointDouble[]::new));
        }
        return convexHull;
    }
}
