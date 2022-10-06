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
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_FiniteGeometry for representing finite geometries.
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_FiniteGeometry extends V3D_Geometry 
        implements V3D_FiniteGeometryInterface {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    protected V3D_Envelope en;
    
    /**
     * Creates a new instance with offset V3D_Vector.ZERO
     *
     * @param e What {@link #e} is set to.
     */
    public V3D_FiniteGeometry(V3D_Environment e) {
        this(e, V3D_Vector.ZERO);
    }
    
    /**
     * Creates a new instance.
     *
     * @param offset What {@link #offset} is set to.
     * @param e What {@link #e} is set to.
     */
    public V3D_FiniteGeometry(V3D_Environment e, V3D_Vector offset) {
        super(e, offset);
    }
    
    public static V3D_Point[] getPoints(int oom, RoundingMode rm, 
            V3D_FiniteGeometry... gs) {
        ArrayList<V3D_Point> list = new ArrayList<>();
        for (var x: gs) {
            V3D_Point[] pts = x.getPoints(oom, rm);
            list.addAll(Arrays.asList(pts));
        }
        return list.toArray(V3D_Point[]::new);
    }
}
