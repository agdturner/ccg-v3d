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

import java.io.Serializable;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * For 3D Euclidean geometrical objects. The three dimensions have are
 * orthogonal axes X, Y, and Z that meet at the origin point {@code<0, 0, 0>}
 * where {@code x=0, y=0 and z=0} the following depicts the origin and
 * dimensions. {@code
 *                                       z
 *                          y           -
 *                          +          /
 *                          |         /
 *                          |        /
 *                          |       /
 *                          |      /
 *                          |     /
 *                          |    /
 *                          |   /
 *                          |  /
 *                          | /
 *                          |/
 * x - ---------------------|------------------------ + x
 *                         /|
 *                        / |
 *                       /  |
 *                      /   |
 *                     /    |
 *                    /     |
 *                   /      |
 *                  /       |
 *                 +        -
 *                z         y
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_Geometry implements Serializable, 
        V3D_IntersectionAndDistanceCalculations {

    private static final long serialVersionUID = 1L;

    /**
     * A reference to the environment.
     */
    public final V3D_Environment e;

    /**
     * The offset used to position a geometry object relative to the
     * {@link V3D_Point#ORIGIN}.
     */
    public V3D_Vector offset;
    
    /**
     * Creates a new instance.
     *
     * @param e What {@link #e} is set to.
     */
    public V3D_Geometry(V3D_Environment e) {
        this(e, V3D_Vector.ZERO);
    }

    /**
     * Creates a new instance.
     *
     * @param offset What {@link #offset} is set to.
     * @param e What {@link #e} is set to.
     */
    public V3D_Geometry(V3D_Environment e, V3D_Vector offset) {
        this.e = e;
        this.offset = new V3D_Vector(offset);
    }

    /**
     * @param pad The padding.
     * @return A padded description.
     */
    protected String toStringFields(String pad) {
        return e.toStringFields(pad) + "\n"
                + pad + ",\n"
                + pad + "offset=" + offset.toString(pad);
    }
    
    /**
     * Translate (move relative to the origin).
     * 
     * @param v The vector to apply.
     * @param oom The Order of Magnitude for the precision.
     */
    public void apply(V3D_Vector v, int oom, RoundingMode rm) {
        offset = offset.add(v, oom, rm);
    }

    /**
     * Rotate about the {@link #offset}.
     * 
     * @param axisOfRotation The axis of rotation.
     * @param theta The angle of rotation around the {@code axisOfRotation} in
     * radians. Options for rotation include:
     * <ul>
     * <li>Rotation Matrix https://en.wikipedia.org/wiki/Rotation_matrix</li>
     * <li>Quaternions
     * https://en.wikipedia.org/wiki/Quaternions_and_spatial_rotation</li>
     * <li>Rotation_matrix_from_axis_and_angle
     * https://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle</li>
     * </ul>
     * See also:
     * <ul>
     * <li>https://en.wikipedia.org/wiki/3D_rotation_group#Exponential_map</li>
     * <li>https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula</li>
     * <li>https://en.wikipedia.org/wiki/3D_rotation_group</li>
     * </ul>
     */
    public abstract void rotate(V3D_Vector axisOfRotation, Math_BigRational theta);
    
//    /**
//     * Identify if the envelope of this is intersected by {@code l}.
//     * 
//     * @param l The line for which intersection with the envelope is indicated.
//     * @param oom The Order of Magnitude for the precision.
//     * @return {@code true} iff {@code l} intersects with the envelope.
//     */
//    public abstract boolean isEnvelopeIntersectedBy(V3D_Line l, int oom);
//    
//    /**
//     * Identify if the geometry is intersected by point {@code p}.
//     *
//     * @param p The point to test for intersection with.
//     * @param oom The Order of Magnitude for the precision.
//     * @return {@code true} iff the geometry is intersected by {@code p}.
//     */
//    public abstract boolean isIntersectedBy(V3D_Point p, int oom);
//
//    /**
//     * Identify if the geometry is intersected by line {@code l}.
//     *
//     * @param l The line to test for intersection with.
//     * @param oom The Order of Magnitude for the precision.
//     * @return {@code true} iff the geometry is intersected by {@code l}.
//     */
//    public abstract boolean isIntersectedBy(V3D_Line l, int oom);
//
//    /**
//     * Identify if the geometry is intersected by line {@code l}.
//     *
//     * @param l The line segment to test for intersection with.
//     * @param oom The Order of Magnitude for the precision.
//     * @return {@code true} iff the geometry is intersected by {@code l}.
//     */
//    public abstract boolean isIntersectedBy(V3D_LineSegment l, int oom);
//
//    /**
//     * Identify if the geometry is intersected by plane {@code p}.
//     *
//     * @param p The plane to test for intersection with.
//     * @param oom The Order of Magnitude for the precision.
//     * @return {@code true} iff the geometry is intersected by {@code p}.
//     */
//    public abstract boolean isIntersectedBy(V3D_Plane p, int oom);
//
//    /**
//     * Identify if the geometry is intersected by plane {@code p}.
//     *
//     * @param t The triangle to test for intersection with.
//     * @param oom The Order of Magnitude for the precision.
//     * @return {@code true} iff the geometry is intersected by {@code p}.
//     */
//    public abstract boolean isIntersectedBy(V3D_Triangle t, int oom);
//
//    /**
//     * Identify if the geometry is intersected by plane {@code p}.
//     *
//     * @param t The tetrahedron to test for intersection with.
//     * @param oom The Order of Magnitude for the precision.
//     * @return {@code true} iff the geometry is intersected by {@code p}.
//     */
//    public abstract boolean isIntersectedBy(V3D_Tetrahedron t, int oom);
//
//    /**
//     * Get the intersection between the geometry and the line {@code l}.
//     *
//     * @param l The line to intersect with.
//     * @param oom The Order of Magnitude for the precision.
//     * @return The V3D_Geometry.
//     */
//    public abstract V3D_Geometry getIntersection(V3D_Line l, int oom);
//
//    /**
//     * Get the intersection between the geometry and the line segment
//     * {@code l}.
//     *
//     * @param l The line segment to intersect with.
//     * @param oom The Order of Magnitude for the precision.
//     * @return The V3D_Geometry.
//     */
//    public abstract V3D_Geometry getIntersection(V3D_LineSegment l, int oom);
//
//    /**
//     * Get the intersection between the geometry and the plane {@code p}.
//     *
//     * @param p The plane to intersect with.
//     * @param oom The Order of Magnitude for the precision.
//     * @return The V3D_Geometry.
//     */
//    public abstract V3D_Geometry getIntersection(V3D_Plane p, int oom);
//
//    /**
//     * Get the intersection between the geometry and the triangle {@code t}.
//     *
//     * @param t The triangle to intersect with.
//     * @param oom The Order of Magnitude for the precision.
//     * @return The V3D_Geometry.
//     */
//    public abstract V3D_Geometry getIntersection(V3D_Triangle t, int oom);
//
//    /**
//     * Get the intersection between the geometry and the tetrahedron {@code t}.
//     *
//     * @param t The tetrahedron to intersect with.
//     * @param oom The Order of Magnitude for the precision.
//     * @return The V3D_Geometry.
//     */
//    public abstract V3D_Geometry getIntersection(V3D_Tetrahedron t, int oom);
//
//    /**
//     * Get the distance to {@code p}.
//     *
//     * @param p A point.
//     * @param oom The Order of Magnitude for the precision.
//     * @return The distance to {@code p}.
//     */
//    public abstract BigDecimal getDistance(V3D_Point p, int oom);
//
//    /**
//     * Get the distance squared to {@code p}.
//     *
//     * @param p A point.
//     * @param oom The Order of Magnitude for the precision of the result.
//     * @return The distance squared to {@code p}.
//     */
//    public abstract Math_BigRational getDistanceSquared(V3D_Point p, int oom);
//
//    /**
//     * Get the minimum distance to {@code l}.
//     *
//     * @param l A line.
//     * @param oom The Order of Magnitude for the precision of the result.
//     * @return The minimum distance to {@code l}.
//     */
//    public abstract BigDecimal getDistance(V3D_Line l, int oom);
//
//    /**
//     * Get the minimum distance squared to {@code l}.
//     *
//     * @param l A line.
//     * @param oom The Order of Magnitude for the precision of the result.
//     * @return The minimum distance squared to {@code l}.
//     */
//    public abstract Math_BigRational getDistanceSquared(V3D_Line l, int oom);
//
//    /**
//     * Get the minimum distance to {@code l}.
//     *
//     * @param l A line segment.
//     * @param oom The Order of Magnitude for the precision of the result.
//     * @return The minimum distance to {@code l}.
//     */
//    public abstract BigDecimal getDistance(V3D_LineSegment l, int oom);
//
//    /**
//     * Get the minimum distance squared to {@code l}.
//     *
//     * @param l A line segment.
//     * @param oom The Order of Magnitude for the precision of the result.
//     * @return The minimum distance squared to {@code l}.
//     */
//    public abstract Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom);
//
//    /**
//     * Get the minimum distance to {@code p}.
//     *
//     * @param p A plane.
//     * @param oom The Order of Magnitude for the precision of the result.
//     * @return The minimum distance to {@code p}.
//     */
//    public abstract BigDecimal getDistance(V3D_Plane p, int oom);
//
//    /**
//     * Get the minimum distance squared to {@code p}.
//     *
//     * @param p A plane.
//     * @param oom The Order of Magnitude for the precision of the result.
//     * @return The minimum distance squared to {@code p}.
//     */
//    public abstract Math_BigRational getDistanceSquared(V3D_Plane p, int oom);
//
//    /**
//     * Get the minimum distance to {@code t}.
//     *
//     * @param t A triangle.
//     * @param oom The Order of Magnitude for the precision of the result.
//     * @return The minimum distance to {@code t}.
//     */
//    public abstract BigDecimal getDistance(V3D_Triangle t, int oom);
//    
//    /**
//     * Get the minimum distance squared to {@code t}.
//     *
//     * @param t A triangle.
//     * @param oom The Order of Magnitude for the precision of the result.
//     * @return The minimum distance squared to {@code t}.
//     */
//    public abstract Math_BigRational getDistanceSquared(V3D_Triangle t, int oom);
//    
//    /**
//     * Get the minimum distance to {@code t}.
//     *
//     * @param t A tetrahedron.
//     * @param oom The Order of Magnitude for the precision of the result.
//     * @return The minimum distance to {@code t}.
//     */
//    public abstract BigDecimal getDistance(V3D_Tetrahedron t, int oom);
//    
//    /**
//     * Get the minimum distance squared to {@code t}.
//     *
//     * @param t A tetrahedron.
//     * @param oom The Order of Magnitude for the precision of the result.
//     * @return The minimum distance squared to {@code t}.
//     */
//    public abstract Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom);
}
