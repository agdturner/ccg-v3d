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
import java.util.HashMap;
import java.util.Iterator;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;

/**
 * Comprising two collections of V3D_ConvexHullCoplanar_d one representing parts
 * and the other representing holes. Parts may intersect. Holes may intersect.
 * So in OGC terms, this is pore like a multi-polygon as holes may effectively
 * split parts and they may collectively cover all parts. Simplification of a
 * V3D_Polygon may be possible and may involve creating new geometries to
 * represent parts individually which may involve some rounding.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Polygon_d extends V3D_Face_d {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new instance.
     *
     */
    public V3D_Polygon_d(V3D_Polygon_d p) {
        super(p.env, p.offset);
//        parts = p.parts;
//        holes = p.holes;
//        convexHull = p.convexHull;
    }

    @Override
    public V3D_Point_d[] getPointsArray() {
        return null;
    }

    @Override
    public HashMap<Integer, V3D_Point_d> getPoints() {
//        if (points == null) {
//            points = new HashMap<>(4);
//            points.put(0, getP());
//            points.put(1, getQ());
//            points.put(2, getR());
//            points.put(3, getS());
//        }
        return points;
    }
    
    @Override
    public HashMap<Integer, V3D_LineSegment_d> getEdges() {
//        if (faces == null) {
//            faces = new HashMap<>(4);
//            faces.put(0, getPqr());
//            faces.put(1, getPsq());
//            faces.put(2, getQsr());
//            faces.put(3, getSpr());
//        }
        return edges;
    }

    @Override
    public V3D_AABB_d getAABB() {
        if (en == null) {
//            Iterator<V3D_ConvexHullCoplanar_d> ite = parts.iterator();
//            en = ite.next().getEnvelope()tAABB();
//            while (ite.hasNext()) {
//                en = en.union(ite.next().getAABB());
//            }
//            en = triangles.get(0).getAABB();
//            for (int i = 1; i < triangles.size(); i++) {
//                en = en.union(triangles.get(i).getAABB());
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
    public boolean intersects(V3D_PointDouble pt, double epsilon) {
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
    public void translate(V3D_Vector_d v) {
        super.translate(v);
        if (en != null) {
            en.translate(v);
        }
//        if (holes != null) {
//            for (int i = 0; i < holes.size(); i++) {
//                holes.get(i).translate(v);
//            }
//        }
//        for (int i = 0; i < parts.size(); i++) {
//            parts.get(i).translate(v);
//        }
    }

    @Override
    public V3D_Polygon_d rotate(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V3D_Polygon_d(this);
        } else {
            return rotateN(ray, uv, theta, epsilon);
        }
    }
    
    @Override
    public V3D_Polygon_d rotateN(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        ArrayList<V3D_ConvexHullCoplanar_d> rparts = new ArrayList<>();
        ArrayList<V3D_ConvexHullCoplanar_d> rholes = new ArrayList<>();
//        if (holes != null) {
//            for (int i = 0; i < holes.size(); i++) {
//                rholes.add(holes.get(i).rotate(ray, uv, theta, epsilon));
//            }
//        }
//        for (int i = 0; i < parts.size(); i++) {
//            rparts.add(parts.get(i).rotate(ray, uv, theta, epsilon));
//        }
//        return new V3D_Polygon_d(rparts, rholes);
        return null;
    }

    /**
     * Return the convex hull calculating it first if it has not already been
     * calculated.
     *
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return Get the convex hull.
     */
    public V3D_ConvexHullCoplanar_d getConvexHull(double epsilon) {
//        if (convexHull == null) {
//            ArrayList<V3D_PointDouble> pts = new ArrayList<>();
//            for (var x : parts) {
//                pts.addAll(x.points);
//            }
//            convexHull = new V3D_ConvexHullCoplanar_d(
//                    parts.get(0).triangles.get(0).pl.n, epsilon,
//                    pts.toArray(V3D_PointDouble[]::new));
//        }
//        return convexHull;
        return null;
    }
}
