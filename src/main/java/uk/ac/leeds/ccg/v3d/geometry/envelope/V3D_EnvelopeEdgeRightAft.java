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
 * For the right-aft edge of an envelope. For the two points that define the line
 * segment, {@link #p} is the top-right-aft point (tra) and {@link q} is the
 * bottom-right-aft point (bra). {@code bra.z} should be less than or equal to
 * {@code tra.z}: {@code tra.y} should equal {@code bra.x}: {@code tra.x} should
 * equal {@code bra.y}. No checking of these conditions is done for efficiency
 * reasons.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_EnvelopeEdgeRightAft extends V3D_EnvelopeEdge {

    /**
     * @param tar The point in the top-right-aft of an envelope.
     * @param bar The point in the bottom-right-aft of an envelope.
     */
    public V3D_EnvelopeEdgeRightAft(V3D_Point tar, V3D_Point bar) {
        super(tar, bar);
    }

    public V3D_Point getTar() {
        return p;
    }

    public V3D_Point getBar() {
        return q;
    }

}
