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
package uk.ac.leeds.ccg.v3d.geometry.envelope;

import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;

/**
 * For the top-fore edge of an envelope. For the two points that define the line
 * segment, {@link #p} is the top-right-fore point (trf) and {@link q} is the
 * top-left-fore point (tar). {@code tlf.x} should be less than or equal to
 * {@code trf.x}: {@code tlf.y} should equal {@code tar.y}: {@code tlf.z} should
 * equal {@code tar.z}. No checking of these conditions is done for efficiency
 * reasons.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_EnvelopeEdgeTopFore extends V3D_EnvelopeEdge {

    /**
     * @param trf The point in the top-right-fore of an envelope.
     * @param tlf The point in the top-left-fore of an envelope.
     */
    public V3D_EnvelopeEdgeTopFore(V3D_Point trf, V3D_Point tlf) {
        super(trf, tlf);
    }

    public V3D_Point getTrf() {
        return p;
    }

    public V3D_Point getTlf() {
        return q;
    }

}
