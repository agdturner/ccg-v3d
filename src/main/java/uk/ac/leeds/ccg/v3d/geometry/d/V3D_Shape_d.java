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

import uk.ac.leeds.ccg.v3d.geometry.*;
import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * For representing 3D Shapes in double precision.
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_Shape_d extends V3D_FiniteGeometry {

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
    public V3D_Shape_d(V3D_Environment env, V3D_Vector offset) {
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
    protected HashMap<Integer, V3D_Face> faces;
    
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
    public abstract HashMap<Integer, V3D_Face> getFaces(int oom, 
            RoundingMode rm);
    
    /**
     * For calculating and returning the area.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The area.
     */
    public abstract BigRational getArea(int oom, RoundingMode rm);
    
    /**
     * For calculating and returning the perimeter.
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
    public static ArrayList<V3D_Point> getPoints(HashMap<Integer, V3D_Shape_d> ss, 
            int oom, RoundingMode rm) {
        ArrayList<V3D_Point> list = new ArrayList<>();
        ss.values().forEach(x -> list.addAll(x.getPoints(oom, rm).values()));
        return list;
    }
}
