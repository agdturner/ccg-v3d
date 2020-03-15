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
 * V3D_EnvelopeFaceLeft The bottom face of an envelope.
 * 
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_EnvelopeFaceRight extends V3D_EnvelopeFace {
    
    public final V3D_EnvelopeEdgeTopLeft tr;
    public final V3D_EnvelopeEdgeLeftAft ra;
    public final V3D_EnvelopeEdgeBottomLeft br;
    public final V3D_EnvelopeEdgeLeftFore rf;
    
    public V3D_EnvelopeFaceRight(V3D_Point trf, V3D_Point tar, V3D_Point bar,
            V3D_Point brf) {
        super(trf, tar, bar, brf);
        tr = new V3D_EnvelopeEdgeTopLeft(trf, tar);
        ra = new V3D_EnvelopeEdgeLeftAft(tar, bar);
        br = new V3D_EnvelopeEdgeBottomLeft(brf, bar);
        rf = new V3D_EnvelopeEdgeLeftFore(trf, brf);
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
            V3D_Geometry trli = tr.getIntersection(li);
            if (trli == null) {
                V3D_Geometry rali = ra.getIntersection(li);
                if (rali == null) {
                    V3D_Geometry brli = br.getIntersection(li);
                    V3D_Geometry rfli = rf.getIntersection(li);
                    V3D_Point brlip = (V3D_Point) brli;
                    V3D_Point rflip = (V3D_Point) brli;
                    return new V3D_LineSegment(brlip, rflip);
                } else if (rali instanceof V3D_LineSegment) {
                    return rali;
                } else {
                    V3D_Point ralip = (V3D_Point) rali;
                    V3D_Geometry brli = br.getIntersection(li);
                    if (brli == null) {
                        return new V3D_LineSegment(ralip,
                                (V3D_Point) rf.getIntersection(li));
                    } else {
                        V3D_Geometry rfli = rf.getIntersection(li);
                        if (rfli == null) {
                            return new V3D_LineSegment(ralip,
                                    (V3D_Point) brli);
                        } else {
                            return new V3D_LineSegment(ralip, (V3D_Point) rfli);
                        }
                    }
                }
            } else if (trli instanceof V3D_LineSegment) {
                return trli;
            } else {
                V3D_Point trlip = (V3D_Point) trli;
                V3D_Geometry rali = ra.getIntersection(li);
                if (rali == null) {
                    V3D_Geometry brli = br.getIntersection(li);
                    if (brli == null) {
                        V3D_Geometry rfli = rf.getIntersection(li);
                        if (rfli == null) {
                            return trlip;
                        } else {
                            V3D_Point rflip = (V3D_Point) rfli;
                            if (trlip.equals(rflip)) {
                                return trlip;
                            } else {
                                return new V3D_LineSegment(trlip, rflip);
                            }
                        }
                    } else if (brli instanceof V3D_LineSegment) {
                        return brli;
                    } else {
                        V3D_Point brlip = (V3D_Point) brli;
                        if (trlip.equals(brlip)) {
                            V3D_Geometry rfli = rf.getIntersection(li);
                            if (rfli == null) {
                                return brli;
                            } else {
                                V3D_Point rflip = (V3D_Point) brli;
                                if (brlip.equals(rflip)) {
                                    return brlip;
                                } else {
                                    return new V3D_LineSegment(brlip, rflip);
                                }
                            }
                        } else {
                            return new V3D_LineSegment(trlip, brlip);
                        }
                    }
                } else if (rali instanceof V3D_LineSegment) {
                    return rali;
                } else {
                    V3D_Point ralip = (V3D_Point) rali;
                    V3D_Geometry brli = br.getIntersection(li);
                    if (brli == null) {
                        return ralip;
                    } else if (brli instanceof V3D_LineSegment) {
                        return brli;
                    } else {
                        return new V3D_LineSegment(trlip, (V3D_Point) brli);
                    }
                }
            }
        } else {
                V3D_Point pi = (V3D_Point) i;
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
