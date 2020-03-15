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

import uk.ac.leeds.ccg.v3d.geometry.V3D_Geometry;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Plane;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;

/**
 * V3D_EnvelopeFaceBottom The bottom face of an envelope.
 * 
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_EnvelopeFaceBottom extends V3D_EnvelopeFace {
    
    public final V3D_EnvelopeEdgeBottomLeft bl;
    public final V3D_EnvelopeEdgeBottomAft ba;
    public final V3D_EnvelopeEdgeBottomRight br;
    public final V3D_EnvelopeEdgeBottomFore bf;
    
    public V3D_EnvelopeFaceBottom(V3D_Point blf, V3D_Point bla, V3D_Point bar,
            V3D_Point brf) {
        super(blf, bla, bar, brf);
        bl = new V3D_EnvelopeEdgeBottomLeft(blf, bla);
        ba = new V3D_EnvelopeEdgeBottomAft(bla, bar);
        br = new V3D_EnvelopeEdgeBottomRight(bar, brf);
        bf = new V3D_EnvelopeEdgeBottomFore(brf, blf);
    }

    /**
     * @param l The line to intersect.
     * @return The intersection of {@code this} and {@code l}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l) {
        V3D_Geometry i = V3D_Plane.getIntersection(this, l);
        if (i == null) {
            return null;
        } else if (i instanceof V3D_Line) {
            V3D_Line li = (V3D_Line) i;
            V3D_Geometry blli = bl.getIntersection(li);
            if (blli == null) {
                V3D_Geometry bali = ba.getIntersection(li);
                if (bali == null) {
                    V3D_Geometry brli = br.getIntersection(li);
                    V3D_Geometry bfli = bf.getIntersection(li);
                    V3D_Point brlip = (V3D_Point) brli;
                    V3D_Point bflip = (V3D_Point) brli;
                    return new V3D_LineSegment(brlip, bflip);
                } else if (bali instanceof V3D_LineSegment) {
                    return bali;
                } else {
                    V3D_Point balip = (V3D_Point) bali;
                    V3D_Geometry brli = br.getIntersection(li);
                    if (brli == null) {
                        return new V3D_LineSegment(balip,
                                (V3D_Point) bf.getIntersection(li));
                    } else {
                        V3D_Geometry bfli = bf.getIntersection(li);
                        if (bfli == null) {
                            return new V3D_LineSegment(balip,
                                    (V3D_Point) brli);
                        } else {
                            return new V3D_LineSegment(balip, (V3D_Point) bfli);
                        }
                    }
                }
            } else if (blli instanceof V3D_LineSegment) {
                return blli;
            } else {
                V3D_Point bllip = (V3D_Point) blli;
                V3D_Geometry bali = ba.getIntersection(li);
                if (bali == null) {
                    V3D_Geometry brli = br.getIntersection(li);
                    if (brli == null) {
                        V3D_Geometry bfli = bf.getIntersection(li);
                        if (bfli == null) {
                            return bllip;
                        } else {
                            V3D_Point bflip = (V3D_Point) bfli;
                            if (bllip.equals(bflip)) {
                                return bllip;
                            } else {
                                return new V3D_LineSegment(bllip, bflip);
                            }
                        }
                    } else if (brli instanceof V3D_LineSegment) {
                        return brli;
                    } else {
                        V3D_Point brlip = (V3D_Point) brli;
                        if (bllip.equals(brlip)) {
                            V3D_Geometry bfli = bf.getIntersection(li);
                            if (bfli == null) {
                                return brli;
                            } else {
                                V3D_Point bflip = (V3D_Point) brli;
                                if (brlip.equals(bflip)) {
                                    return brlip;
                                } else {
                                    return new V3D_LineSegment(brlip, bflip);
                                }
                            }
                        } else {
                            return new V3D_LineSegment(bllip, brlip);
                        }
                    }
                } else if (bali instanceof V3D_LineSegment) {
                    return bali;
                } else {
                    V3D_Point balip = (V3D_Point) bali;
                    V3D_Geometry brli = br.getIntersection(li);
                    if (brli == null) {
                        return balip;
                    } else if (brli instanceof V3D_LineSegment) {
                        return brli;
                    } else {
                        return new V3D_LineSegment(bllip, (V3D_Point) brli);
                    }
                }
            }
        } else {
                V3D_Point pi = (V3D_Point) i;
                en = getEnvelope();
                if (pi.x.compareTo(en.getxMin()) != -1
                        && pi.x.compareTo(en.getxMax()) != 1
                        && pi.y.compareTo(en.getyMin()) != -1
                        && pi.y.compareTo(en.getxMax()) != 1) {
                    return i;
                }
                return null;
        }
    }
}
