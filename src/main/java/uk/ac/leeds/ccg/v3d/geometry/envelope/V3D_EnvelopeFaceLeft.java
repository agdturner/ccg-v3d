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
public class V3D_EnvelopeFaceLeft extends V3D_EnvelopeFace {
    
    public final V3D_EnvelopeEdgeTopLeft tl;
    public final V3D_EnvelopeEdgeLeftAft la;
    public final V3D_EnvelopeEdgeBottomLeft bl;
    public final V3D_EnvelopeEdgeLeftFore lf;
    
    public V3D_EnvelopeFaceLeft(V3D_Point tlf, V3D_Point tla, V3D_Point bla,
            V3D_Point blf) {
        super(tlf, tla, bla, blf);
        tl = new V3D_EnvelopeEdgeTopLeft(tlf, tla);
        la = new V3D_EnvelopeEdgeLeftAft(tla, bla);
        bl = new V3D_EnvelopeEdgeBottomLeft(blf, bla);
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
            V3D_Geometry tlli = tl.getIntersection(li);
            if (tlli == null) {
                V3D_Geometry lali = la.getIntersection(li);
                if (lali == null) {
                    V3D_Geometry blli = bl.getIntersection(li);
                    V3D_Geometry lfli = lf.getIntersection(li);
                    V3D_Point bllip = (V3D_Point) blli;
                    V3D_Point lflip = (V3D_Point) blli;
                    return new V3D_LineSegment(bllip, lflip);
                } else if (lali instanceof V3D_LineSegment) {
                    return lali;
                } else {
                    V3D_Point lalip = (V3D_Point) lali;
                    V3D_Geometry blli = bl.getIntersection(li);
                    if (blli == null) {
                        return new V3D_LineSegment(lalip,
                                (V3D_Point) lf.getIntersection(li));
                    } else {
                        V3D_Geometry lfli = lf.getIntersection(li);
                        if (lfli == null) {
                            return new V3D_LineSegment(lalip,
                                    (V3D_Point) blli);
                        } else {
                            return new V3D_LineSegment(lalip, (V3D_Point) lfli);
                        }
                    }
                }
            } else if (tlli instanceof V3D_LineSegment) {
                return tlli;
            } else {
                V3D_Point tllip = (V3D_Point) tlli;
                V3D_Geometry lali = la.getIntersection(li);
                if (lali == null) {
                    V3D_Geometry blli = bl.getIntersection(li);
                    if (blli == null) {
                        V3D_Geometry lfli = lf.getIntersection(li);
                        if (lfli == null) {
                            return tllip;
                        } else {
                            V3D_Point lflip = (V3D_Point) lfli;
                            if (tllip.equals(lflip)) {
                                return tllip;
                            } else {
                                return new V3D_LineSegment(tllip, lflip);
                            }
                        }
                    } else if (blli instanceof V3D_LineSegment) {
                        return blli;
                    } else {
                        V3D_Point bllip = (V3D_Point) blli;
                        if (tllip.equals(bllip)) {
                            V3D_Geometry lfli = lf.getIntersection(li);
                            if (lfli == null) {
                                return blli;
                            } else {
                                V3D_Point lflip = (V3D_Point) blli;
                                if (bllip.equals(lflip)) {
                                    return bllip;
                                } else {
                                    return new V3D_LineSegment(bllip, lflip);
                                }
                            }
                        } else {
                            return new V3D_LineSegment(tllip, bllip);
                        }
                    }
                } else if (lali instanceof V3D_LineSegment) {
                    return lali;
                } else {
                    V3D_Point lalip = (V3D_Point) lali;
                    V3D_Geometry blli = bl.getIntersection(li);
                    if (blli == null) {
                        return lalip;
                    } else if (blli instanceof V3D_LineSegment) {
                        return blli;
                    } else {
                        return new V3D_LineSegment(tllip, (V3D_Point) blli);
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
