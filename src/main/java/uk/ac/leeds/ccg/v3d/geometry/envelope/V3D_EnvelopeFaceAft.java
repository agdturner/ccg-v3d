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
 * V3D_EnvelopeFaceAft The aft face of an envelope.
 * 
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_EnvelopeFaceAft extends V3D_EnvelopeFace {
    
    public final V3D_EnvelopeEdgeTopAft ta;
    public final V3D_EnvelopeEdgeRightAft ra;
    public final V3D_EnvelopeEdgeBottomAft ba;
    public final V3D_EnvelopeEdgeLeftAft la;
    
    public V3D_EnvelopeFaceAft(V3D_Point tla, V3D_Point tar,
            V3D_Point bar, V3D_Point bla) {
        super(tla, tar, bar, bla);
        ta = new V3D_EnvelopeEdgeTopAft(tla, tar);
        ra = new V3D_EnvelopeEdgeRightAft(tar, bar);
        ba = new V3D_EnvelopeEdgeBottomAft(bla, bar);
        la = new V3D_EnvelopeEdgeLeftAft(tla, bla);
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
            V3D_Geometry tali = ta.getIntersection(li);
            if (tali == null) {
                V3D_Geometry rali = ra.getIntersection(li);
                if (rali == null) {
                    V3D_Geometry bali = ba.getIntersection(li);
                    V3D_Geometry lali = la.getIntersection(li);
                    V3D_Point balip = (V3D_Point) bali;
                    V3D_Point lalip = (V3D_Point) bali;
                    return new V3D_LineSegment(balip, lalip);
                } else if (rali instanceof V3D_LineSegment) {
                    return rali;
                } else {
                    V3D_Point ralip = (V3D_Point) rali;
                    V3D_Geometry bali = ba.getIntersection(li);
                    if (bali == null) {
                        return new V3D_LineSegment(ralip,
                                (V3D_Point) la.getIntersection(li));
                    } else {
                        V3D_Geometry lali = la.getIntersection(li);
                        if (lali == null) {
                            return new V3D_LineSegment(ralip,
                                    (V3D_Point) bali);
                        } else {
                            return new V3D_LineSegment(ralip, (V3D_Point) lali);
                        }
                    }
                }
            } else if (tali instanceof V3D_LineSegment) {
                return tali;
            } else {
                V3D_Point talip = (V3D_Point) tali;
                V3D_Geometry rali = ra.getIntersection(li);
                if (rali == null) {
                    V3D_Geometry bali = ba.getIntersection(li);
                    if (bali == null) {
                        V3D_Geometry lali = la.getIntersection(li);
                        if (lali == null) {
                            return talip;
                        } else {
                            V3D_Point lalip = (V3D_Point) lali;
                            if (talip.equals(lalip)) {
                                return talip;
                            } else {
                                return new V3D_LineSegment(talip, lalip);
                            }
                        }
                    } else if (bali instanceof V3D_LineSegment) {
                        return bali;
                    } else {
                        V3D_Point balip = (V3D_Point) bali;
                        if (talip.equals(balip)) {
                            V3D_Geometry lali = la.getIntersection(li);
                            if (lali == null) {
                                return bali;
                            } else {
                                V3D_Point lalip = (V3D_Point) bali;
                                if (balip.equals(lalip)) {
                                    return balip;
                                } else {
                                    return new V3D_LineSegment(balip, lalip);
                                }
                            }
                        } else {
                            return new V3D_LineSegment(talip, balip);
                        }
                    }
                } else if (rali instanceof V3D_LineSegment) {
                    return rali;
                } else {
                    V3D_Point ralip = (V3D_Point) rali;
                    V3D_Geometry bali = ba.getIntersection(li);
                    if (bali == null) {
                        return ralip;
                    } else if (bali instanceof V3D_LineSegment) {
                        return bali;
                    } else {
                        return new V3D_LineSegment(talip, (V3D_Point) bali);
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
