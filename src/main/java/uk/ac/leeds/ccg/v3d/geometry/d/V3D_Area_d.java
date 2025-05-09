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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

/**
 * The simplest V3D_Area_d is a V3D_Triangle_d that represents a triangle in 3D.
 * V3D_ConvexArea_d extends V2D_Area_d and is for representing a convex area
 * comprising one or more triangles sharing internal edges and with a convex
 * external edge. All points of convex areas are on the external edge.
 * V3D_PolygonNoInternalHoles_d are defined by a V3D_ConvexArea_d and a
 * collection of non edge sharing V3D_PolygonNoInternalHoles_d areas each
 * defining an external hole or concavity. V3D_Polygon_d extends
 * V3D_PolygonNoInternalHoles_d and is also defined by a collection of non edge
 * sharing internal holes each represented also as a V3D_Polygon_d.
 *
 * @author Andy Turner
 * @version 2.0
 */
public abstract class V3D_Area_d extends V3D_FiniteGeometry_d {

    private static final long serialVersionUID = 1L;

    /**
     * The id of the area.
     */
    protected final int id;
    
    /**
     * The plane of the area.
     */
    public V3D_Plane_d pl;
    
    /**
     * For storing the points. The keys are IDs.
     */
    protected HashMap<Integer, V3D_Point_d> points;

    /**
     * For storing the edges. The keys are IDs.
     */
    protected HashMap<Integer, V3D_LineSegment_d> edges;
    
    /**
     * Creates a new instance with offset V3D_Vector.ZERO.
     * 
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param pl What {@link #pl} is set to.
     */
    public V3D_Area_d(V3D_Environment_d env, V3D_Vector_d offset, 
            V3D_Plane_d pl) {
        super(env, offset);
        this.id = env.getNextID();
        this.pl = pl;
    }
    
    /**
     * For getting the plane.
     * 
     * @return {@link #pl} initialising first if it is {@code null}. 
     */
    public abstract V3D_Plane_d getPl();
    
    /**
     * For getting the points.
     * 
     * @return {@link #points} initialising first if it is {@code null}.
     */
    public abstract HashMap<Integer, V3D_Point_d> getPoints();
    
    /**
     * For getting the edges.
     * 
     * @return A HashMap of the edges with integer identifier keys.
     */
    public abstract HashMap<Integer, V3D_LineSegment_d> getEdges();
    
    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     */
    @Override
    public void translate(V3D_Vector_d v) {
        super.translate(v);
        if (pl != null) {
            pl.translate(v);
        }
        if (points != null) {
            points.values().parallelStream().forEach(x -> 
                    x.translate(v));
        }
        if (edges != null) {
            edges.values().parallelStream().forEach(x -> 
                    x.translate(v));
        }
    }
    
    /**
     * For calculating and returning the perimeter.
     * @return The Perimeter.
     */
    public abstract double getPerimeter();

    /**
     * For calculating and returning the area.
     * @return The area.
     */
    public abstract double getArea();
    
    /**
     * @param p The point to test if it intersects.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} if {@code this} is intersected by {@code p}.
     */
    public abstract boolean intersects(V3D_Point_d p, double epsilon);

    /**
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @param p A point to test for intersection.
     * @param as The areas to test for intersection with p.
     * @return {@code true} if any {@code as} are intersected by {@code p}.
     */
    public static boolean intersects(double epsilon, V3D_Point_d p,
            V3D_Area_d... as) {
        return intersects(epsilon, p, Arrays.asList(as));
    }

    /**
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @param p A point to test for intersection.
     * @param as The areas to test for intersection with p.
     * @return {@code true} if any {@code as} are intersected by {@code p}.
     */
    public static boolean intersects(double epsilon, V3D_Point_d p,
            Collection<V3D_Area_d> as) {
        return as.parallelStream().anyMatch(x -> x.intersects(p, epsilon));
    }
    
    /**
     * @param aabb The Axis Aligned Bounding Box to test if it intersects.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} if {@code this} is intersected by {@code aabb}.
     */
    public abstract boolean intersects(V3D_AABB_d aabb, double epsilon);
    
    /**
     * @param l The line to test if it intersects.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} if {@code this} is intersected by {@code l}.
     */
    public abstract boolean intersects(V3D_Line_d l, double epsilon);

    /**
     * @param l The line segment to test if it intersects.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} if {@code this} is intersected by {@code l}.
     */
    public abstract boolean intersects(V3D_LineSegment_d l, double epsilon);
    
    /**
     * @param r The ray to test if it intersects.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} if {@code this} is intersected by {@code r}.
     */
    public abstract boolean intersects(V3D_Ray_d r, double epsilon);
    
    /**
     * @param r The ray to test for intersection which is assumed to be
     * non-coplanar.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} if {@code this} is intersected by {@code r}.
     */
    public abstract boolean intersectsNonCoplanar(V3D_Ray_d r, double epsilon);
    
    /**
     * @param t The triangle to test if it intersects.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} if {@code this} is intersected by {@code t}.
     */
    public abstract boolean intersects(V3D_Triangle_d t, double epsilon);
    
    /**
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @param a An area to test for intersection.
     * @return {@code true} if {@code this} is intersected by {@code a}.
     */
    public boolean intersects(V3D_Area_d a, double epsilon) {
        return a.getPoints().values().parallelStream().anyMatch(x 
            -> a.intersects(x, epsilon));
    }
    
    /**
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @param l A line segment to test for intersection with any of the areas
     * in as.
     * @param as The areas to test for intersection with l.
     * @return {@code true} if any of {@code as} is intersected by {@code l}.
     */
    public static boolean intersects(double epsilon,
            V3D_LineSegment_d l, V3D_Area_d... as) {
        return intersects(epsilon, l, Arrays.asList(as));
    }

    /**
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @param l A line segment to test for intersection with any of the areas
     * in as.
     * @param as The areas to test for intersection with l.
     * @return {@code true} if any of {@code as} is intersected by {@code l}.
     */
    public static boolean intersects(double epsilon,
            V3D_LineSegment_d l, Collection<V3D_Area_d> as) {
        return as.parallelStream().anyMatch(x -> x.intersects(l, epsilon));
    }
    
    /**
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @param as The areas to test for intersection with a.
     * @return {@code true} if {@code this} is intersected by any areas in 
     * {@code as}.
     */
    public boolean intersects(double epsilon, V3D_Area_d... as) {
        return intersects(epsilon, Arrays.asList(as));
    }

    /**
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @param as The areas to test for intersection with a.
     * @return {@code true} if {@code this} is intersected by any areas in 
     * {@code as}.
     */
    public boolean intersects(double epsilon, Collection<V3D_Area_d> as) {
        return as.parallelStream().anyMatch(x -> intersects(x, epsilon));
    }

    /**
     * Identify if {@code this} contains {@code pt}. Containment excludes the
     * edge.
     *
     * @param p The point to test for containment.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} if {@code this} contains {@code p}.
     */
    //public abstract boolean contains(V3D_Point p, double epsilon);
    public boolean contains(V3D_Point_d p, double epsilon) {
        if (intersects(p, epsilon)) {
            return !getEdges().values().parallelStream().anyMatch(x 
                    -> x.intersects(p, epsilon));
        }
        return false;
    }

    /**
     * Identify if {@code this} contains {@code ls}. Containment excludes the
     * edge.
     *
     * @param ls The line segments to test for containment.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} if {@code this} contains {@code ls}.
     */
    //public abstract boolean contains(V3D_LineSegment ls, double epsilon);
    public boolean contains(V3D_LineSegment_d ls, double epsilon) {
        return contains(ls.getP(), epsilon)
                && contains(ls.getQ(), epsilon);
    }

    /**
     * Identify if {@code this} contains {@code t}. Containment excludes the
     * edge.
     *
     * @param t The triangle to test for containment.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} if {@code this} contains {@code t}.
     */
    //public abstract boolean contains(V3D_Area_d a, double epsilon);
    public boolean contains(V3D_Triangle_d t, double epsilon) {
        if (getAABB().contains(t.getAABB())) {
            // Faster than using the general method?
            return contains(t.getPQ(), epsilon)
                    && contains(t.getQR(), epsilon)
                    && contains(t.getRP(), epsilon);
        } else {
            return false;
        }
    }
    
    /**
     * Identify if {@code this} contains {@code a}. Containment excludes the
     * edge.
     *
     * @param a The area to test for containment.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} if {@code this} contains {@code a}.
     */
    //public abstract boolean contains(V3D_Area a, double epsilon);
    public boolean contains(V3D_Area_d a, double epsilon) {
        return a.getPoints().values().parallelStream().allMatch(x
                -> contains(x, epsilon));
    }
    
    /**
     * Compute and return the intersection with {@code r}.
     *
     * @param r The ray to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
          * @return The intersection or {@code null}.
     */
    public abstract V3D_FiniteGeometry_d getIntersect(V3D_Ray_d r,
            double epsilon);
    
    /**
     * Compute and return the intersection with {@code r} which is assumed to be 
     * non-coplanar.
     * 
     * @param r The ray to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection point or {@code null}.
     */
    public abstract V3D_Point_d getIntersectNonCoplanar(V3D_Ray_d r, double epsilon);
}
