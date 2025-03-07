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

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_FiniteGeometry for representing finite geometries.
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_FiniteGeometry extends V3D_Geometry {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    protected V3D_Envelope en;
    
    /**
     * Creates a new instance with offset V3D_Vector.ZERO.
     * 
     * @param env The environment.
     */
    public V3D_FiniteGeometry(V3D_Environment env) {
        this(env, V3D_Vector.ZERO);
    }
    
    /**
     * Creates a new instance.
     *
     * @param env The environment.
     * @param offset What {@link #offset} is set to.
     */
    public V3D_FiniteGeometry(V3D_Environment env, V3D_Vector offset) {
        super(env, offset);
    }
    
    /**
     * For getting the envelope (the Axis Aligned Bounding Box) of the geometry.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return The V3D_Envelope.
     */
    public abstract V3D_Envelope getEnvelope(int oom);
    
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A copy of the points of the geometry.
     */
    public abstract V3D_Point[] getPointsArray(int oom, RoundingMode rm);
    
    /**
     * @return A copy of the points of the geometries gs.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param gs The geometries.
     */
    public static V3D_Point[] getPoints(int oom, RoundingMode rm, V3D_FiniteGeometry... gs) {
        ArrayList<V3D_Point> list = new ArrayList<>();
        for (var x: gs) {
            list.addAll(Arrays.asList(x.getPointsArray(oom, rm)));
        }
        return list.toArray(V3D_Point[]::new);
    }
    
    /**
     * For collecting and returning all the points.
     *
     * @param gs The input.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A Set of points that are the corners of the triangles.
     */
    public static V3D_Point[] getPoints(V3D_FiniteGeometry[] gs, int oom, RoundingMode rm) {
        List<V3D_Point> s = new ArrayList<>();
        for (var g : gs) {
            V3D_Point[] gps = g.getPointsArray(oom, rm);
            s.addAll(Arrays.asList(gps));
        }
        ArrayList<V3D_Point> points = V3D_Point.getUnique(s, oom, rm);
        return points.toArray(V3D_Point[]::new);
    }

    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    @Override
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        if (en != null) {
            en.translate(v, oom, rm);
        }
        //en = null;
    }
}
