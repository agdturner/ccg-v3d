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

import java.util.ArrayList;
import java.util.HashMap;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

/**
 * The simplest V3D_Volume_d is a V3D_Tetrahedron_d for representing
 * tetrahedron. V3D_ConvexVolume_d extends V3D_Volume_d and is for representing
 * convex volumes comprising one or more tetrahedron. Convex volumes comprised
 * of multiple tetrahedron have tetrahedron sharing internal tetrahedral sides.
 * All points of convex volumes are on the external face areas and external
 * edges. V3D_ShapeNoInternalHoles_d are defined by a V3D_ConvexVolume_d and a
 * collection of V3D_PolyhedronNoInternalHoles_d volumes each defining an
 * external hole or concavity. V3D_Polyhedron_d extends
 * V3D_PolyhedronNoInternalHoles_d and is also defined by a collection of non
 * face sharing and non intersecting internal holes each represented also as a
 * V3D_Polyhedron_d.
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_Volume_d extends V3D_FiniteGeometry_d {

    private static final long serialVersionUID = 1L;

    /**
     * The id of the shape.
     */
    protected final int id;

    /**
     * Creates a new instance with offset V3D_Vector_d.ZERO.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     */
    public V3D_Volume_d(V3D_Environment_d env, V3D_Vector_d offset) {
        super(env, offset);
        this.id = env.getNextID();
    }

    /**
     * For storing the points.
     */
    protected HashMap<Integer, V3D_Point_d> points;

    /**
     * For storing the faces.
     */
    protected HashMap<Integer, V3D_Area_d> faces;

    /**
     * For getting the points of a shape.
     *
     * @return A HashMap of the points with integer identifier keys.
     */
    public abstract HashMap<Integer, V3D_Point_d> getPoints();

    /**
     * For getting the faces of a shape.
     *
     * @return A HashMap of the edges with integer identifier keys.
     */
    public abstract HashMap<Integer, V3D_Area_d> getFaces();

    /**
     * For calculating and returning the area.
     *
     * @return The area.
     */
    public abstract double getArea();

    /**
     * For calculating and returning the perimeter.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The Volume.
     */
    public abstract double getVolume(double epsilon);

    /**
     * @return A copy of the points of the geometries gs.
     *
     * @param ss The geometries.
     */
    public static ArrayList<V3D_Point_d> getPoints(
            HashMap<Integer, V3D_Volume_d> ss) {
        ArrayList<V3D_Point_d> list = new ArrayList<>();
        ss.values().forEach(x -> list.addAll(x.getPoints().values()));
        return list;
    }
}
