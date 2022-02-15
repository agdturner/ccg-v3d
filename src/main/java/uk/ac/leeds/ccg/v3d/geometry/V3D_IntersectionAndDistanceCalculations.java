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
import uk.ac.leeds.ccg.math.number.Math_BigRational;

/**
 * V3D_FiniteGeometry
 *
 * @author Andy Turner
 * @version 1.0
 */
public interface V3D_IntersectionAndDistanceCalculations {
    
    /**
     * Identify if the geometry is intersected by point {@code p}.
     *
     * @param p The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public abstract boolean isIntersectedBy(V3D_Point p, int oom);

    /**
     * Identify if the geometry is intersected by line {@code l}.
     *
     * @param l The line to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff the geometry is intersected by {@code l}.
     */
    public abstract boolean isIntersectedBy(V3D_Line l, int oom);

    /**
     * Identify if the geometry is intersected by ray {@code r}.
     *
     * @param r The ray to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff the geometry is intersected by {@code r}.
     */
    public abstract boolean isIntersectedBy(V3D_Ray l, int oom);

    /**
     * Identify if the geometry is intersected by line segment {@code l}.
     *
     * @param l The line segment to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff the geometry is intersected by {@code l}.
     */
    public abstract boolean isIntersectedBy(V3D_LineSegment l, int oom);

    /**
     * Identify if the geometry is intersected by plane {@code p}.
     *
     * @param p The plane to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public abstract boolean isIntersectedBy(V3D_Plane p, int oom);

    /**
     * Identify if the geometry is intersected by plane {@code p}.
     *
     * @param t The triangle to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public abstract boolean isIntersectedBy(V3D_Triangle t, int oom);

    /**
     * Identify if the geometry is intersected by plane {@code p}.
     *
     * @param t The tetrahedron to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public abstract boolean isIntersectedBy(V3D_Tetrahedron t, int oom);

    /**
     * Get the intersection between the geometry and the line {@code l}.
     *
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @return The V3D_Geometry.
     */
    public abstract V3D_Geometry getIntersection(V3D_Line l, int oom);

    /**
     * Get the intersection between the geometry and the ray
     * {@code r}.
     *
     * @param r The ray to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @return The V3D_Geometry.
     */
    public abstract V3D_Geometry getIntersection(V3D_Ray r, int oom);

    /**
     * Get the intersection between the geometry and the line segment
     * {@code l}.
     *
     * @param l The line segment to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @return The V3D_Geometry.
     */
    public abstract V3D_Geometry getIntersection(V3D_LineSegment l, int oom);

    /**
     * Get the intersection between the geometry and the plane {@code p}.
     *
     * @param p The plane to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @return The V3D_Geometry.
     */
    public abstract V3D_Geometry getIntersection(V3D_Plane p, int oom);

    /**
     * Get the intersection between the geometry and the triangle {@code t}.
     *
     * @param t The triangle to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @return The V3D_Geometry.
     */
    public abstract V3D_Geometry getIntersection(V3D_Triangle t, int oom);

    /**
     * Get the intersection between the geometry and the tetrahedron {@code t}.
     *
     * @param t The tetrahedron to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @return The V3D_Geometry.
     */
    public abstract V3D_Geometry getIntersection(V3D_Tetrahedron t, int oom);

    /**
     * Get the distance to {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @return The distance to {@code p}.
     */
    public abstract BigDecimal getDistance(V3D_Point p, int oom);

    /**
     * Get the distance squared to {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The distance squared to {@code p}.
     */
    public abstract Math_BigRational getDistanceSquared(V3D_Point p, int oom);

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance to {@code l}.
     */
    public abstract BigDecimal getDistance(V3D_Line l, int oom);

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance squared to {@code l}.
     */
    public abstract Math_BigRational getDistanceSquared(V3D_Line l, int oom);

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance to {@code l}.
     */
    public abstract BigDecimal getDistance(V3D_LineSegment l, int oom);

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance squared to {@code l}.
     */
    public abstract Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom);

    /**
     * Get the minimum distance to {@code r}.
     *
     * @param r A ray.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance to {@code r}.
     */
    public abstract BigDecimal getDistance(V3D_Ray r, int oom);

    /**
     * Get the minimum distance squared to {@code r}.
     *
     * @param r A ray.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance squared to {@code r}.
     */
    public abstract Math_BigRational getDistanceSquared(V3D_Ray l, int oom);

    /**
     * Get the minimum distance to {@code p}.
     *
     * @param p A plane.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance to {@code p}.
     */
    public abstract BigDecimal getDistance(V3D_Plane p, int oom);

    /**
     * Get the minimum distance squared to {@code p}.
     *
     * @param p A plane.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance squared to {@code p}.
     */
    public abstract Math_BigRational getDistanceSquared(V3D_Plane p, int oom);

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance to {@code t}.
     */
    public abstract BigDecimal getDistance(V3D_Triangle t, int oom);
    
    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance squared to {@code t}.
     */
    public abstract Math_BigRational getDistanceSquared(V3D_Triangle t, int oom);
    
    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A tetrahedron.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance to {@code t}.
     */
    public abstract BigDecimal getDistance(V3D_Tetrahedron t, int oom);
    
    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A tetrahedron.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance squared to {@code t}.
     */
    public abstract Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom);
}
