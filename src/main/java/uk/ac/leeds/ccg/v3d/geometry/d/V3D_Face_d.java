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
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

/**
 * V3D_FiniteGeometry for representing finite geometries.
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_Face_d extends V3D_FiniteGeometry_d {

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
    public V3D_Face_d(V3D_Environment_d env, V3D_Vector_d offset) {
        super(env, offset);
        this.id = env.getNextID();
    }
    
    /**
     * For storing the points.
     */
    protected HashMap<Integer, V3D_Point_d> points;

    /**
     * For storing the edges.
     */
    protected HashMap<Integer, V3D_LineSegment_d> edges;
    
    /**
     * For getting the points of a shape.
     * 
     * @return A HashMap of the points with integer identifier keys.
     */
    public abstract HashMap<Integer, V3D_Point_d> getPoints();
    
    /**
     * For getting the edges of a shape.
     * 
     * @return A HashMap of the edges with integer identifier keys.
     */
    public abstract HashMap<Integer, V3D_LineSegment_d> getEdges();
    
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
    
}
