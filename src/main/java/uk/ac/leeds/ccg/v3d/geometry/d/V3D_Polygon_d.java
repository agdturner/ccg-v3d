/*
 * Copyright 2025 Andy Turner, University of Leeds.
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

import java.util.HashMap;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;

/**
 * V3D_Polygon extends V3D_PolygonNoInternalHoles_d and is also defined by a
 * collection of non intersecting internal holes each represented also as a 
 * V3D_Polygon.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V3D_Polygon_d extends V3D_PolygonNoInternalHoles_d {

    private static final long serialVersionUID = 1L;

    /**
     * The collection of internalHoles.
     */
    public HashMap<Integer, V3D_PolygonNoInternalHoles_d> internalHoles;
    //public HashMap<Integer, V3D_Polygon> internalHoles;
    
    /**
     * For storing the internalHoles edges.
     */
    protected HashMap<Integer, V3D_LineSegment_d> internalHolesEdges;

    /**
     * Create a new shallow copy.
     *
     * @param p The polygon to duplicate.
     */
    public V3D_Polygon_d(V3D_Polygon_d p) {
        super(p);
        this.internalHoles = p.internalHoles;
        this.internalHolesEdges = p.internalHolesEdges;
    }
    
    /**
     * Create a new instance.
     *
     * @param p The polygon with no internal holes to use as a basis for this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V3D_Polygon_d(V3D_PolygonNoInternalHoles_d p, double epsilon) {
        this(p, new HashMap<>(), epsilon);
    }

    /**
     * Create a new instance.
     *
     * @param p The polygon with no internal holes to use as a basis for this.
     * @param internalHoles What {@link #internalHoles} is set to.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V3D_Polygon_d(V3D_PolygonNoInternalHoles_d p,
            HashMap<Integer, V3D_PolygonNoInternalHoles_d> internalHoles,
            //HashMap<Integer, V3D_Polygon> internalHoles,
            double epsilon) {
        super(p);
        this.internalHoles = internalHoles;
    }

//    /**
//     * Create a new instance.
//     *
//     * @param pts The external edge points in a clockwise order.
//     * @param internalHoles What {@link #internalHoles} is set to.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode.
//     */
//    public V3D_Polygon(V3D_Point_d[] pts, HashMap<Integer, V3D_PolygonNoInternalHoles_d> internalHoles,
//    //public V3D_Polygon(V3D_Point_d[] pts, HashMap<Integer, V3D_Polygon> internalHoles,
//            double epsilon) {
//        super(pts, new V3D_PlaConvexArea(epsilon, offset, pts));
//        this.internalHoles = internalHoles;
//    }

    /**
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A collection of the edges of all the internal holes.
     */
    public HashMap<Integer, V3D_LineSegment_d> getInternalHolesEdges(double epsilon) {
        if (internalHolesEdges == null) {
            internalHolesEdges = new HashMap<>();
            internalHoles.values().forEach(x
                -> edges.values().forEach(y
                -> internalHolesEdges.put(internalHolesEdges.size(), y)));
        }
        return internalHolesEdges;
    }
    
    /**
     * Identify if {@code this} intersects {@code pt}.
     *
     * @param pt The point to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} intersects {@code pt}.
     */
    @Override
    public boolean intersects(V3D_Point_d pt, double epsilon) {
        return super.intersects(pt, epsilon)
                && !internalHoles.values().parallelStream().anyMatch(x
                        -> x.contains(pt, epsilon));
    }

    /**
     * Identify if {@code this} contains {@code pt}.
     *
     * @param pt The point to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} contains {@code pt}.
     */
    @Override
    public boolean contains(V3D_Point_d pt, double epsilon) {
        return super.contains(pt, epsilon)
                && !internalHolesContains(pt, epsilon);
    }

    /**
     * Identify if {@link #internalHoles} contains {@code pt}.
     *
     * @param pt The point to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@link #internalHoles} contains {@code pt}.
     */
    public boolean internalHolesContains(V3D_Point_d pt, double epsilon) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.contains(pt, epsilon));
    }

    /**
     * Identify if {@link #internalHoles} intersects {@code pt}.
     *
     * @param pt The point to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@link #internalHoles} intersects {@code pt}.
     */
    public boolean internalHolesIntersects(V3D_Point_d pt, double epsilon) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.intersects(pt, epsilon));
    }

    /**
     * Identify if {@code this} intersects {@code l}.
     *
     * @param l The line segment to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} intersects {@code l}.
     */
    @Override
    public boolean intersects(V3D_LineSegment_d l, double epsilon) {
        return super.intersects(l, epsilon)
                && !internalHolesContains(l, epsilon);
    }

    /**
     * Identify if {@link #internalHoles} contains {@code l}.
     *
     * @param l The line segment to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@link #internalHoles} contains {@code l}.
     */
    public boolean internalHolesContains(V3D_LineSegment_d l, double epsilon) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.contains(l, epsilon));
    }

    /**
     * Identify if {@link #internalHoles} intersects {@code l}.
     *
     * @param l The line segment to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@link #internalHoles} intersects {@code l}.
     */
    public boolean internalHolesIntersects(V3D_LineSegment_d l, double epsilon) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.intersects(l, epsilon));
    }

    /**
     * Identify if {@code this} contains {@code l}.
     *
     * @param l The line segment to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} contains {@code l}.
     */
    @Override
    public boolean contains(V3D_LineSegment_d l, double epsilon) {
        return super.contains(l, epsilon)
                && !internalHolesContains(l, epsilon);
    }

    /**
     * Identify if {@code this} intersects {@code t}.
     *
     * @param t The triangle to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} intersects {@code t}.
     */
    @Override
    public boolean intersects(V3D_Triangle_d t, double epsilon) {
        return super.intersects(t, epsilon)
                && !internalHolesContains(t, epsilon);
    }

    /**
     * Identify if {@link #internalHoles} contains {@code t}.
     *
     * @param t The triangle to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@link #internalHoles} contains {@code t}.
     */
    public boolean internalHolesContains(V3D_Triangle_d t, double epsilon) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.contains(t, epsilon));
    }

    /**
     * Identify if {@link #internalHoles} intersects {@code t}.
     *
     * @param t The triangle to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@link #internalHoles} intersects {@code t}.
     */
    public boolean internalHolesIntersects(V3D_Triangle_d t, double epsilon) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.intersects(t, epsilon));
    }

    /**
     * Identify if {@code this} contains {@code t}.
     *
     * @param t The triangle to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} contains {@code t}.
     */
    @Override
    public boolean contains(V3D_Triangle_d t, double epsilon) {
        return super.contains(t, epsilon)
                && !internalHolesContains(t, epsilon);
    }

    /**
     * Identify if {@code this} intersects {@code r}.
     *
     * @param r The rectangle to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} intersects {@code r}.
     */
    @Override
    public boolean intersects(V3D_Rectangle_d r, double epsilon) {
        return super.intersects(r, epsilon)
                && !internalHolesContains(r, epsilon);
    }

    /**
     * Identify if {@link #internalHoles} contains {@code t}.
     *
     * @param r The rectangle to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@link #internalHoles} contains {@code t}.
     */
    public boolean internalHolesContains(V3D_Rectangle_d r, double epsilon) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.contains(r, epsilon));
    }

    /**
     * Identify if {@link #internalHoles} intersects {@code t}.
     *
     * @param r The rectangle to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@link #internalHoles} intersects {@code t}.
     */
    public boolean internalHolesIntersects(V3D_Rectangle_d r, double epsilon) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.intersects(r, epsilon));
    }

    /**
     * Identify if {@code this} contains {@code r}.
     *
     * @param r The rectangle to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} contains {@code r}.
     */
    @Override
    public boolean contains(V3D_Rectangle_d r, double epsilon) {
        return super.contains(r, epsilon)
                && internalHolesContains(r, epsilon);
    }

    /**
     * Identify if {@code this} intersects {@code ch}.
     *
     * @param ch The convex hull to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} intersects {@code ch}.
     */
    @Override
    public boolean intersects(V3D_ConvexArea_d ch, double epsilon) {
        return super.intersects(ch, epsilon)
                && !internalHolesContains(ch, epsilon);
    }

    /**
     * Identify if {@link #internalHoles} contains {@code ch}.
     *
     * @param ch The convex hull to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@link #internalHoles} contains {@code ch}.
     */
    public boolean internalHolesContains(V3D_ConvexArea_d ch, double epsilon) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.contains(ch, epsilon));
    }

    /**
     * Identify if {@link #internalHoles} intersects {@code ch}.
     *
     * @param ch The convex hull to test for intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@link #internalHoles} intersects {@code ch}.
     */
    public boolean internalHolesIntersects(V3D_ConvexArea_d ch, double epsilon) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.intersects(ch, epsilon));
    }

    /**
     * Identify if {@code this} contains {@code ch}.
     *
     * @param ch The convex hull to test for containment.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} contains {@code ch}.
     */
    @Override
    public boolean contains(V3D_ConvexArea_d ch, double epsilon) {
        return super.contains(ch, epsilon)
                && !internalHolesContains(ch, epsilon);
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
        if (internalHoles != null) {
            for (int i = 0; i < internalHoles.size(); i++) {
                internalHoles.get(i).translate(v);
            }
        }
    }

    @Override
    public V3D_Polygon_d rotate(V3D_Ray_d ray, V3D_Vector_d uv, 
            double theta, double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0) {
            return new V3D_Polygon_d(this);
        } else {
            return rotateN(ray, uv, theta, epsilon);
        }
    }

    @Override
    public V3D_Polygon_d rotateN(V3D_Ray_d ray, V3D_Vector_d uv, 
            double theta, double epsilon) {
        V3D_PolygonNoInternalHoles_d exterior = super.rotateN(ray, uv, theta, epsilon);
        HashMap<Integer, V3D_PolygonNoInternalHoles_d> rInternalHoles = new HashMap<>();
        //HashMap<Integer, V3D_Polygon> rInternalHoles = new HashMap<>();
        if (internalHoles != null) {
            for (int i = 0; i < internalHoles.size(); i++) {
                rInternalHoles.put(rInternalHoles.size(),
                    internalHoles.get(i).rotate(ray, uv, theta, epsilon));
            }
        }
        return new V3D_Polygon_d(exterior, rInternalHoles, epsilon);
    }

//    @Override
//    public boolean intersects(V3D_AABB aabb, double epsilon) {
//        if (getEnvelope(epsilon).intersects(aabb, oom)) {
//            if (getConvexHull(epsilon).intersects(aabb, epsilon)) {
//                return true;
//            }
//        }
//        return false;
//    }
    /**
     * Adds an internal hole and return its assigned id.
     *
     * @param p The polygon to add.
     * @return the id assigned to the internal hole
     */
    public int addInternalHole(V3D_Polygon_d p) {
        int pid = internalHoles.size();
        internalHoles.put(pid, p);
        return pid;
    }
}
