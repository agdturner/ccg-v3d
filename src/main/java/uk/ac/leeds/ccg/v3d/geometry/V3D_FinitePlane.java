/*
 * Copyright 2020 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain xxx copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.v3d.geometry;

import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_FinitePlane This general class defines those planes that are all bounded
 * by their envelope exactly. It can be extended to consider finite planes that
 * have a particular shape, such as circles, ellipses, triangles, squares, and
 * other regular or irregular 2D shapes.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_FinitePlane extends V3D_Plane implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    public V3D_FinitePlane(V3D_Environment e, V3D_Point a, V3D_Point b,
            V3D_Point c) {
        super(e, a, b, c);
    }

    @Override
    public V3D_Envelope getEnvelope3D() {
        return new V3D_Envelope(e, p, q, r);
    }

    /**
     * @return {@code this}
     */
    public V3D_Plane getPlane() {
        return this;
    }

    /**
     * @param pt The point to intersect with.
     * @return A point or line segment.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt) {
        if (getEnvelope3D().isIntersectedBy(pt)) {
            if (getPlane().isIntersectedBy(pt)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param li The line to intersect with.
     * @return A point or line segment.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line li) {
        V3D_Geometry pi = getPlane().getIntersection(li);
        if (pi != null) {
            if (pi instanceof V3D_Line) {
                V3D_Line pil = (V3D_Line) pi;
                /**
                 * There are in total 6 faces of this envelope: top (t) (zMax),
                 * bottom (b) (zMin), left (l) (xMin), right (r) (xMax), for (f)
                 * (yMin), aft (a) (yMax). li must either go in one face and out
                 * of another, or it may enter and/or leave at a corner - either
                 * the intersection between two or three faces.
                 */
                V3D_Envelope en = getEnvelope3D();
                /**
                 * There are 8 corners: 4 at the top; tlf (Top, Left, For), tla
                 * (Top, Left, Aft), trf (Top, Right, For), tra (Top, Right,
                 * Aft); 4 at the bottom; blf (Bottom, Left, For), bla (Bottom,
                 * Left, Aft), brf (Bottom, Right, For), bra (Bottom, Right,
                 * Aft)
                 */
                V3D_Point tlf = new V3D_Point(e, en.zMax, en.xMin, en.yMin);
                V3D_Point tla = new V3D_Point(e, en.zMax, en.xMin, en.yMax);
                V3D_Point trf = new V3D_Point(e, en.zMax, en.xMax, en.yMin);
                V3D_Point tra = new V3D_Point(e, en.zMax, en.xMax, en.yMax);
                V3D_Point blf = new V3D_Point(e, en.zMin, en.xMin, en.yMin);
                V3D_Point bla = new V3D_Point(e, en.zMin, en.xMin, en.yMax);
                V3D_Point brf = new V3D_Point(e, en.zMin, en.xMax, en.yMin);
                V3D_Point bra = new V3D_Point(e, en.zMin, en.xMax, en.yMax);
                V3D_FinitePlane t = new V3D_FinitePlane(e, blf, bla, brf);
                V3D_FinitePlane b = new V3D_FinitePlane(e, blf, bla, brf);
                V3D_FinitePlane l = new V3D_FinitePlane(e, blf, bla, tlf);
                V3D_FinitePlane r = new V3D_FinitePlane(e, brf, bra, trf);
                V3D_FinitePlane f = new V3D_FinitePlane(e, brf, blf, tlf);
                V3D_FinitePlane a = new V3D_FinitePlane(e, brf, bra, trf);
                // Does li intersect with t
                V3D_Geometry tli = t.getIntersection(li);
                if (tli == null) {
                    // Does li intersect with l
                    V3D_Geometry lli = l.getIntersection(li);
                    if (lli == null) {
                        // Does li intersect with a
                        V3D_Geometry ali = a.getIntersection(li);
                        if (ali == null) {
                            // Does li intersect with r
                            V3D_Geometry rli = r.getIntersection(li);
                            if (rli == null) {
                                // Does li intersect with f
                                V3D_Geometry fli = r.getIntersection(li);
                                if (fli == null) {
                                    // It must only intersect with b
                                } else if (lli instanceof V3D_LineSegment) {
                                    // Establish which sides the line intersects
                                    V3D_LineSegment flil = (V3D_LineSegment) fli;

                                } else {
                                    V3D_Point flip = (V3D_Point) fli;
                                    // It could enter at a double corner!
                                    // Establish which of the other 2 faces it leaves

                                }
                            } else if (ali instanceof V3D_LineSegment) {
                                // Establish which sides the line intersects
                                V3D_LineSegment alil = (V3D_LineSegment) ali;

                            } else {
                                V3D_Point alip = (V3D_Point) ali;
                                // It could enter at a double corner!
                                // Establish which of the other 3 faces it leaves

                            }
                        }
                    } else if (lli instanceof V3D_LineSegment) {
                        // Establish which sides the line intersects
                        V3D_LineSegment llil = (V3D_LineSegment) lli;

                    } else {
                        V3D_Point llip = (V3D_Point) lli;
                        // It could enter at a double corner!
                        // Establish which of the other 4 faces it leaves

                    }
                } else if (tli instanceof V3D_LineSegment) {
                    // Establish which sides the line intersects
                    V3D_LineSegment tlil = (V3D_LineSegment) tli;
                    V3D_LineSegment tl = new V3D_LineSegment(tlf, tla);
                    V3D_Geometry tlilitl = tlil.getIntersection(tl);
                    if (tlilitl == null) {

                    } else if (tlilitl instanceof V3D_LineSegment) {
                        return tlilitl;
                    } else {
                        V3D_Point tlilitlp = (V3D_Point) tlilitl;
                        // Work around clockwise
                        // ta
                        V3D_LineSegment ta = new V3D_LineSegment(tra, tla);
                        V3D_Geometry tlilita = tlil.getIntersection(ta);
                        if (tlilita == null) {
                            // tr
                            V3D_LineSegment tr = new V3D_LineSegment(trf, tra);
                            V3D_Geometry tlilitr = tlil.getIntersection(tr);
                            // tlilitr can only be a point as the tl and tr are opposite
                            if (tlilitr == null) {
                                // tf
                                V3D_LineSegment tf = new V3D_LineSegment(trf, tlf);
                                V3D_Geometry tlilitf = tlil.getIntersection(tf);
                                // tlilitf cannot be null.
                                if (tlilitf instanceof V3D_LineSegment) {
                                    return tf;
                                } else if (tlilitf instanceof V3D_Point) {
                                    V3D_Point tlilitfp = (V3D_Point) tlilitf;
                                    if (tlilitfp.equals(tlilitlp)) {
                                        return tlilitlp;
                                    } else {
                                        return new V3D_LineSegment(tlilitfp, tlilitlp);
                                    }
                                }
                            } else {
                                V3D_Point tlilitrp = (V3D_Point) tlilitr;
                                if (tlilitrp.equals(tlilitlp)) {
                                    return tlilitlp;
                                } else {
                                    return new V3D_LineSegment(tlilitrp, tlilitlp);
                                }
                            }
                        } else if (tlilita instanceof V3D_LineSegment) {
                            return ta;
                        } else {
                            V3D_Point tlilitap = (V3D_Point) tlilita;
                            if (tlilitap.equals(tlilitlp)) {
                                return tlilitlp;
                            } else {
                                return new V3D_LineSegment(tlilitap, tlilitlp);
                            }
                        }
                    }
                } else {
                    V3D_Point tlip = (V3D_Point) tli;
                    // It could enter at a double corner!
                    // Establish which of the other 5 faces it leaves
                    // 1) l
                    // if (null){
                    //   do 2
                    // else if line
                    // else 
                    //   if enters at a double corner test the third side
                    //     if line on third side
                    //   else find which side it exits. 
                    //   
                    // 2) a
                    // 3) r
                    // 4) f
                    // 5) b
                }
            }
            // l interects a corner of the finite plane.
            V3D_Point pt = (V3D_Point) pi;
            if (getEnvelope3D().isIntersectedBy(pt)) {
                return pt;
            }
        }
        return null;
    }
}
