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
package uk.ac.leeds.ccg.v3d.geometry;

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * The simplest V3D_Volume is a V3D_Tetrahedron for representing tetrahedron.
 * V3D_ConvexVolume extends V3D_Volume and is for representing convex volumes
 * comprising one or more tetrahedron. Convex volumes comprised of multiple
 * tetrahedron have tetrahedron sharing internal tetrahedral sides. All points
 * of convex volumes are on the external face areas and external edges.
 * V3D_ShapeNoInternalHoles are defined by a V3D_ConvexVolume and a collection
 * of V3D_PolyhedronNoInternalHoles volumes each defining an external hole or
 * concavity. V3D_Shape extends V3D_PolyhedronNoInternalHoles and is also
 * defined by a collection of non face sharing and non intersecting internal
 * holes each represented also as a V3D_Polyhedron.
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_Volume extends V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The id of the shape.
     */
    protected final int id;

    /**
     * Creates a new instance with offset V3D_Vector.ZERO.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     */
    public V3D_Volume(V3D_Environment env, V3D_Vector offset) {
        super(env, offset);
        this.id = env.getNextID();
    }

    /**
     * For storing the points.
     */
    protected HashMap<Integer, V3D_Point> points;

    /**
     * For storing the faces.
     */
    protected HashMap<Integer, V3D_Area> faces;

    /**
     * For getting the points of a shape.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A HashMap of the points with integer identifier keys.
     */
    public abstract HashMap<Integer, V3D_Point> getPoints(int oom,
            RoundingMode rm);

    /**
     * For getting the faces of a shape.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A HashMap of the edges with integer identifier keys.
     */
    public abstract HashMap<Integer, V3D_Area> getFaces(int oom,
            RoundingMode rm);

    /**
     * For calculating and returning the area.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The area.
     */
    public abstract BigRational getArea(int oom, RoundingMode rm);

    /**
     * For calculating and returning the perimeter.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The Perimeter.
     */
    public abstract BigRational getVolume(int oom, RoundingMode rm);

    /**
     * @return A copy of the points of the geometries gs.
     *
     * @param ss The geometries.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public static ArrayList<V3D_Point> getPoints(HashMap<Integer, V3D_Volume> ss,
            int oom, RoundingMode rm) {
        ArrayList<V3D_Point> list = new ArrayList<>();
        ss.values().forEach(x -> list.addAll(x.getPoints(oom, rm).values()));
        return list;
    }
}
