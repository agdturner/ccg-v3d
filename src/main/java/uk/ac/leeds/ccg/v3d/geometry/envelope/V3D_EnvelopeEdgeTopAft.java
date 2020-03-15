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
 * For the top-aft edge of an envelope. For the two points that define the line
 * segment, {@link #p} is the top-left-aft point (tla) and {@link q} is the
 * top-aft-right point (tar). {@code tla.x} should be less than or equal to
 * {@code tar.x}: {@code tla.y} should equal {@code tar.y}: {@code tla.z} should
 * equal {@code tar.z}. No checking of these conditions is done for efficiency
 * reasons.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_EnvelopeEdgeTopAft extends V3D_EnvelopeEdge {

    /**
     * @param tla The point in the top-left-aft of an envelope.
     * @param tar The point in the top-aft-right of an envelope.
     */
    public V3D_EnvelopeEdgeTopAft(V3D_Point tla, V3D_Point tar) {
        super(tla, tar);
    }

    public V3D_Point getTla() {
        return p;
    }

    public V3D_Point getTar() {
        return q;
    }

}
