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
     */
    public V3D_FiniteGeometry() {
        this(V3D_Vector.ZERO);
    }
    
    /**
     * Creates a new instance.
     *
     * @param offset What {@link #offset} is set to.
     */
    public V3D_FiniteGeometry(V3D_Vector offset) {
        super(offset);
    }
    
    /**
     * For getting the envelope (the Axis Aligned Bounding Box) of the geometry.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return The V3D_Envelope.
     */
    public abstract V3D_Envelope getEnvelope(int oom);
    
    /**
     * For evaluating if the geometry is intersected by the Axis Aligned 
     * Bounding Box aabb.
     *
     * @param aabb The Axis Aligned Bounding Box to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff the geometry intersects aabb at the given precision.
     */
    public abstract boolean isIntersectedBy(V3D_Envelope aabb, int oom, RoundingMode rm);
    
    /**
     * @return A copy of the points of the geometry.
     */
    public abstract V3D_Point[] getPoints();
    
    /**
     * @return A copy of the points of the geometries gs.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param gs The geometries.
     */
    public static V3D_Point[] getPoints(V3D_FiniteGeometry... gs) {
        ArrayList<V3D_Point> list = new ArrayList<>();
        for (var x: gs) {
            V3D_Point[] pts = x.getPoints();
            list.addAll(Arrays.asList(pts));
        }
        return list.toArray(V3D_Point[]::new);
    }
    
    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
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
