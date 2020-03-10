/*
 * Copyright 2020 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain xxx copy of the License at
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

import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_FinitePlane
 * 
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_FinitePlane extends V3D_Plane 
        implements V3D_FiniteGeometry {

    public V3D_FinitePlane(V3D_Environment e, V3D_Point a,  V3D_Point b, 
            V3D_Point c) {
        super(e, a, b, c);
    }
    
    @Override
    public V3D_Envelope getEnvelope3D() {
        return new V3D_Envelope(e, p, q, r);
    }

}
