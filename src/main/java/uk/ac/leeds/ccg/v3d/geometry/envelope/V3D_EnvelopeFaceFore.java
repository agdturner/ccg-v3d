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
 * V3D_EnvelopeFaceFore The fore face of an envelope.
 * 
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_EnvelopeFaceFore extends V3D_EnvelopeFace {
    
    public final V3D_EnvelopeEdgeTopFore tf;
    public final V3D_EnvelopeEdgeRightFore rf;
    public final V3D_EnvelopeEdgeBottomFore bf;
    public final V3D_EnvelopeEdgeLeftFore lf;
    
    public V3D_EnvelopeFaceFore(V3D_Point tlf, V3D_Point trf,
            V3D_Point brf, V3D_Point blf) {
        super(tlf, trf, brf, blf);
        tf = new V3D_EnvelopeEdgeTopFore(tlf, trf);
        rf = new V3D_EnvelopeEdgeRightFore(trf, brf);
        bf = new V3D_EnvelopeEdgeBottomFore(blf, brf);
        lf = new V3D_EnvelopeEdgeLeftFore(tlf, blf);
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
            V3D_Geometry tfli = tf.getIntersection(li);
            if (tfli == null) {
                V3D_Geometry rfli = rf.getIntersection(li);
                if (rfli == null) {
                    V3D_Geometry bfli = bf.getIntersection(li);
                    V3D_Geometry lfli = lf.getIntersection(li);
                    V3D_Point bflip = (V3D_Point) bfli;
                    V3D_Point lflip = (V3D_Point) bfli;
                    return new V3D_LineSegment(bflip, lflip);
                } else if (rfli instanceof V3D_LineSegment) {
                    return rfli;
                } else {
                    V3D_Point rflip = (V3D_Point) rfli;
                    V3D_Geometry bfli = bf.getIntersection(li);
                    if (bfli == null) {
                        return new V3D_LineSegment(rflip,
                                (V3D_Point) lf.getIntersection(li));
                    } else {
                        V3D_Geometry lfli = lf.getIntersection(li);
                        if (lfli == null) {
                            return new V3D_LineSegment(rflip,
                                    (V3D_Point) bfli);
                        } else {
                            return new V3D_LineSegment(rflip, (V3D_Point) lfli);
                        }
                    }
                }
            } else if (tfli instanceof V3D_LineSegment) {
                return tfli;
            } else {
                V3D_Point tflip = (V3D_Point) tfli;
                V3D_Geometry rfli = rf.getIntersection(li);
                if (rfli == null) {
                    V3D_Geometry bfli = bf.getIntersection(li);
                    if (bfli == null) {
                        V3D_Geometry lfli = lf.getIntersection(li);
                        if (lfli == null) {
                            return tflip;
                        } else {
                            V3D_Point lflip = (V3D_Point) lfli;
                            if (tflip.equals(lflip)) {
                                return tflip;
                            } else {
                                return new V3D_LineSegment(tflip, lflip);
                            }
                        }
                    } else if (bfli instanceof V3D_LineSegment) {
                        return bfli;
                    } else {
                        V3D_Point bflip = (V3D_Point) bfli;
                        if (tflip.equals(bflip)) {
                            V3D_Geometry lfli = lf.getIntersection(li);
                            if (lfli == null) {
                                return bfli;
                            } else {
                                V3D_Point lflip = (V3D_Point) bfli;
                                if (bflip.equals(lflip)) {
                                    return bflip;
                                } else {
                                    return new V3D_LineSegment(bflip, lflip);
                                }
                            }
                        } else {
                            return new V3D_LineSegment(tflip, bflip);
                        }
                    }
                } else if (rfli instanceof V3D_LineSegment) {
                    return rfli;
                } else {
                    V3D_Point rflip = (V3D_Point) rfli;
                    V3D_Geometry bfli = bf.getIntersection(li);
                    if (bfli == null) {
                        return rflip;
                    } else if (bfli instanceof V3D_LineSegment) {
                        return bfli;
                    } else {
                        return new V3D_LineSegment(tflip, (V3D_Point) bfli);
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
