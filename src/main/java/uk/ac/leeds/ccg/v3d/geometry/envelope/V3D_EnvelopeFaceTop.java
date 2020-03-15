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
 * V3D_EnvelopeFaceTop The top face of an envelope.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_EnvelopeFaceTop extends V3D_EnvelopeFace {

    public final V3D_EnvelopeEdgeTopLeft tl;
    public final V3D_EnvelopeEdgeTopAft ta;
    public final V3D_EnvelopeEdgeTopRight tr;
    public final V3D_EnvelopeEdgeTopFore tf;

    public V3D_EnvelopeFaceTop(V3D_Point tlf, V3D_Point tla, V3D_Point tar,
            V3D_Point trf) {
        super(tlf, tla, tar, trf);
        tl = new V3D_EnvelopeEdgeTopLeft(tlf, tla);
        ta = new V3D_EnvelopeEdgeTopAft(tla, tar);
        tr = new V3D_EnvelopeEdgeTopRight(tar, trf);
        tf = new V3D_EnvelopeEdgeTopFore(trf, tlf);
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
            V3D_Geometry tlli = tl.getIntersection(li);
            if (tlli == null) {
                V3D_Geometry tali = ta.getIntersection(li);
                if (tali == null) {
                    V3D_Geometry trli = tr.getIntersection(li);
                    V3D_Geometry tfli = tf.getIntersection(li);
                    V3D_Point trlip = (V3D_Point) trli;
                    V3D_Point tflip = (V3D_Point) trli;
                    return new V3D_LineSegment(trlip, tflip);
                } else if (tali instanceof V3D_LineSegment) {
                    return tali;
                } else {
                    V3D_Point talip = (V3D_Point) tali;
                    V3D_Geometry trli = tr.getIntersection(li);
                    if (trli == null) {
                        return new V3D_LineSegment(talip,
                                (V3D_Point) tf.getIntersection(li));
                    } else {
                        V3D_Geometry tfli = tf.getIntersection(li);
                        if (tfli == null) {
                            return new V3D_LineSegment(talip,
                                    (V3D_Point) trli);
                        } else {
                            return new V3D_LineSegment(talip, (V3D_Point) tfli);
                        }
                    }
                }
            } else if (tlli instanceof V3D_LineSegment) {
                return tlli;
            } else {
                V3D_Point tllip = (V3D_Point) tlli;
                V3D_Geometry tali = ta.getIntersection(li);
                if (tali == null) {
                    V3D_Geometry trli = tr.getIntersection(li);
                    if (trli == null) {
                        V3D_Geometry tfli = tf.getIntersection(li);
                        if (tfli == null) {
                            return tllip;
                        } else {
                            V3D_Point tflip = (V3D_Point) tfli;
                            if (tllip.equals(tflip)) {
                                return tllip;
                            } else {
                                return new V3D_LineSegment(tllip, tflip);
                            }
                        }
                    } else if (trli instanceof V3D_LineSegment) {
                        return trli;
                    } else {
                        V3D_Point trlip = (V3D_Point) trli;
                        if (tllip.equals(trlip)) {
                            V3D_Geometry tfli = tf.getIntersection(li);
                            if (tfli == null) {
                                return trli;
                            } else {
                                V3D_Point tflip = (V3D_Point) trli;
                                if (trlip.equals(tflip)) {
                                    return trlip;
                                } else {
                                    return new V3D_LineSegment(trlip, tflip);
                                }
                            }
                        } else {
                            return new V3D_LineSegment(tllip, trlip);
                        }
                    }
                } else if (tali instanceof V3D_LineSegment) {
                    return tali;
                } else {
                    V3D_Point talip = (V3D_Point) tali;
                    V3D_Geometry trli = tr.getIntersection(li);
                    if (trli == null) {
                        return talip;
                    } else if (trli instanceof V3D_LineSegment) {
                        return trli;
                    } else {
                        return new V3D_LineSegment(tllip, (V3D_Point) trli);
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
