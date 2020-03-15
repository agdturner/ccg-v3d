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
 * For the bottom-aft edge of an envelope. For the two points that define the line
 * segment, {@link #p} is the bottom-left-aft point (bla) and {@link q} is the
 * bottom-aft-right point (bar). {@code bla.x} should be less than or equal to
 * {@code bar.x}: {@code bla.y} should equal {@code bar.y}: {@code bla.z} should
 * equal {@code bar.z}. No checking of these conditions is done for efficiency
 * reasons.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_EnvelopeEdgeBottomAft extends V3D_EnvelopeEdge {

    /**
     * @param bla The point in the bottom-left-aft of an envelope.
     * @param bar The point in the bottom-aft-right of an envelope.
     */
    public V3D_EnvelopeEdgeBottomAft(V3D_Point bla, V3D_Point bar) {
        super(bla, bar);
    }

    public V3D_Point getBla() {
        return p;
    }

    public V3D_Point getBar() {
        return q;
    }

}
