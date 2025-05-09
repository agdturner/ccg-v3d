/*
 * Copyright 2020-2025 Andy Turner, University of Leeds.
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
import java.util.Arrays;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

/**
 * V3D_FiniteGeometry for representing finite geometries.
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_FiniteGeometry_d extends V3D_Geometry_d {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the Axis Aligned Bounding Box.
     */
    protected V3D_AABB_d en;
    
    /**
     * For getting the envelope of the geometry
     *
     * @return The Axis.
     */
    public abstract V3D_AABB_d getAABB();
    
    /**
     * Creates a new instance with offset V3D_Vector.ZERO.
     * 
     * @param env What {@link #env} is set to.
     */
    public V3D_FiniteGeometry_d(V3D_Environment_d env) {
        this(env, V3D_Vector_d.ZERO);
    }
    
    /**
     * Creates a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     */
    public V3D_FiniteGeometry_d(V3D_Environment_d env, V3D_Vector_d offset) {
        super(env, offset);
    }
    
//    /**
//     * For evaluating if the geometry is intersected by the Axis Aligned 
//     * Bounding Box aabb.
//     *
//     * @param aabb The Axis Aligned Bounding Box to test for intersection.
//     * @param epsilon The tolerance within which two vector components are
//     * considered equal.
//     * @return {@code true} iff the geometry intersects aabb at the given precision.
//     */
//    public abstract boolean intersects(V3D_AABB_d aabb, double epsilon);
    
    /**
     * @return A copy of the points of the geometry.
     */
    public abstract V3D_Point_d[] getPointsArray();
    
    /**
     * @return A copy of the points of the geometries gs.
     * @param gs The geometries.
     */
    public static V3D_Point_d[] getPoints(V3D_FiniteGeometry_d... gs) {
        ArrayList<V3D_Point_d> list = new ArrayList<>();
        for (var x: gs) {
            list.addAll(Arrays.asList(x.getPointsArray()));
        }
        return list.toArray(V3D_Point_d[]::new);
    }
    
    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     */
    @Override
    public void translate(V3D_Vector_d v) {
        super.translate(v);
        if (en != null) {
            en.translate(v);
        }
    }
}
