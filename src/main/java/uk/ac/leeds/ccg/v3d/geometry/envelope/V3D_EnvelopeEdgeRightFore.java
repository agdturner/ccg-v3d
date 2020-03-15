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
 * For the right-fore edge of an envelope. For the two points that define the line
 * segment, {@link #p} is the top-right-fore point (tfa) and {@link q} is the
 * bottom-right-fore point (bfa). {@code bfa.z} should be less than or equal to
 * {@code tfa.z}: {@code tfa.y} should equal {@code bfa.x}: {@code tfa.x} should
 * equal {@code bfa.y}. No checking of these conditions is done for efficiency
 * reasons.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_EnvelopeEdgeRightFore extends V3D_EnvelopeEdge {

    /**
     * @param tfa The point in the top-right-fore of an envelope.
     * @param bfa The point in the bottom-right-fore of an envelope.
     */
    public V3D_EnvelopeEdgeRightFore(V3D_Point tfa, V3D_Point bfa) {
        super(tfa, bfa);
    }

    public V3D_Point getTfa() {
        return p;
    }

    public V3D_Point getBfa() {
        return q;
    }

}
