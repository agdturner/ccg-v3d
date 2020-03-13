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

    public V3D_FinitePlane(V3D_Point a, V3D_Point b,
            V3D_Point c) {
        super(a, b, c);
    }

    @Override
    public V3D_Envelope getEnvelope3D() {
        return new V3D_Envelope(e, p, q, r);
    }

    /**
     * @param pt The point to intersect with.
     * @return A point or line segment.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt) {
        if (getEnvelope3D().isIntersectedBy(pt)) {
            if (super.isIntersectedBy(pt)) {
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
        V3D_Geometry pi = super.getIntersection(li);
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
                V3D_Point tlf = new V3D_Point(e, en.xMin, en.yMin, en.zMax);
                V3D_Point tla = new V3D_Point(e, en.xMin, en.yMax, en.zMax);
                V3D_Point trf = new V3D_Point(e, en.xMax, en.yMin, en.zMax);
                V3D_Point tra = new V3D_Point(e, en.xMax, en.yMax, en.zMax);
                V3D_Point blf = new V3D_Point(e, en.xMin, en.yMin, en.zMin);
                V3D_Point bla = new V3D_Point(e, en.xMin, en.yMax, en.zMin);
                V3D_Point brf = new V3D_Point(e, en.xMax, en.yMin, en.zMin);
                V3D_Point bra = new V3D_Point(e, en.xMax, en.yMax, en.zMin);
                V3D_FinitePlane t = new V3D_FinitePlane(blf, bla, brf);
                V3D_FinitePlane b = new V3D_FinitePlane(blf, bla, brf);
                V3D_FinitePlane l = new V3D_FinitePlane(blf, bla, tlf);
                V3D_FinitePlane r = new V3D_FinitePlane(brf, bra, trf);
                V3D_FinitePlane f = new V3D_FinitePlane(brf, blf, tlf);
                V3D_FinitePlane a = new V3D_FinitePlane(brf, bra, trf);
                // Does li intersect with t?
                V3D_Geometry tli = t.getIntersection(li);
                if (tli == null) {
                    // Does li intersect with l?
                    V3D_Geometry lli = l.getIntersection(li);
                    if (lli == null) {
                        // Does li intersect with a?
                        V3D_Geometry ali = a.getIntersection(li);
                        if (ali == null) {
                            // Does li intersect with r?
                            V3D_Geometry rli = r.getIntersection(li);
                            if (rli == null) {
                                /**
                                 * Does li intersect with f? - If so it can only
                                 * intersect at a point. This point is either a
                                 * touching point with b, or the intersection
                                 * with b is at a different point. li cannot
                                 * intersect with b as it is // * infinite and
                                 * so must also intersect with // * one other
                                 * face (albeit at a corner).
                                 */
                                V3D_Point flip = (V3D_Point) r.getIntersection(li);
                                V3D_Point blip = (V3D_Point) b.getIntersection(li);
                                if (flip.equals(blip)) {
                                    return flip;
                                } else {
                                    return new V3D_LineSegment(flip, blip);
                                }
                            } else {
                                V3D_Point alip = (V3D_Point) ali;
                                // Does li intersect with f?
                                V3D_Geometry fli = f.getIntersection(li);
                                if (fli == null) {
                                    // li intersects b
                                    return new V3D_LineSegment((V3D_Point) b.getIntersection(li), alip);
                                } else if (fli instanceof V3D_LineSegment) {
                                    return fli;
                                } else {
                                    V3D_Point flip = (V3D_Point) fli;
                                    if (flip.equals(alip)) {
                                        return flip;
                                    } else {
                                        return new V3D_LineSegment(flip, alip);
                                    }
                                }
                            }
                        }
                    } else if (lli instanceof V3D_LineSegment) {
                        return lli;
                    } else {
                        V3D_Point llip = (V3D_Point) lli;
                        // Does li intersect a?
                        V3D_Geometry ali = a.getIntersection(li);
                        if (ali == null) {
                            // Does li intersect r?
                            V3D_Geometry rli = r.getIntersection(li);
                            if (rli == null) {
                                // Does li intersect f?
                                V3D_Geometry fli = f.getIntersection(li);
                                if (fli == null) {
                                    // li intersects with b at a point
                                    V3D_Point blip = (V3D_Point) b.getIntersection(li);
                                    if (blip.equals(llip)) {
                                        return blip;
                                    } else {
                                        return new V3D_LineSegment(blip, llip);
                                    }
                                } else if (fli instanceof V3D_LineSegment) {
                                    return fli;
                                } else {
                                    V3D_Point flip = (V3D_Point) fli;
                                    if (flip.equals(llip)) {
                                        return flip;
                                    } else {
                                        return new V3D_LineSegment(flip, llip);
                                    }
                                }
                            } else {
                                return new V3D_LineSegment((V3D_Point) rli, llip);
                            }
                        } else if (ali instanceof V3D_LineSegment) {
                            return ali;
                        } else {
                            V3D_Point alip = (V3D_Point) ali;
                            if (alip.equals(llip)) {
                                return alip;
                            } else {
                                return new V3D_LineSegment(alip, llip);
                            }
                        }
                    }
                } else if (tli instanceof V3D_LineSegment) {
                    return tli;
                } else {
                    V3D_Point tlip = (V3D_Point) tli;
                    V3D_Geometry lli = l.getIntersection(li);
                    if (lli == null) {
                        return checkFurtherIntersections_arfb(li, a, r,
                                f, b, tlip);
                    } else if (lli instanceof V3D_LineSegment) {
                        return lli;
                    } else {
                        if (lli instanceof V3D_LineSegment) {
                            return lli;
                        } else {
                            V3D_Point llip = (V3D_Point) lli;
                            if (llip.equals(tlip)) {
                                return checkFurtherIntersections_arfb(li, a, r,
                                        f, b, tlip);
                            } else {
                                return new V3D_LineSegment(tlip, llip);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private V3D_Geometry checkFurtherIntersections_arfb(V3D_Line li,
            V3D_FinitePlane a, V3D_FinitePlane r, V3D_FinitePlane f,
            V3D_FinitePlane b, V3D_Point tlip) {
        V3D_Geometry ali = a.getIntersection(li);
        if (ali == null) {
            return checkFurtherIntersections_rfb(li, r, f, b, tlip);
        } else if (ali instanceof V3D_LineSegment) {
            return ali;
        } else {
            V3D_Point alip = (V3D_Point) ali;
            if (alip.equals(tlip)) {
                return checkFurtherIntersections_rfb(li, r, f, b, tlip);
            } else {
                return new V3D_LineSegment(tlip, alip);
            }
        }
    }

    private V3D_Geometry checkFurtherIntersections_rfb(V3D_Line li,
            V3D_FinitePlane r, V3D_FinitePlane f, V3D_FinitePlane b,
            V3D_Point tlip) {
        V3D_Geometry rli = r.getIntersection(li);
        if (rli == null) {
            // Check for further intersections
            V3D_Geometry fli = f.getIntersection(li);
            if (fli == null) {
                // Check for further intersections
                V3D_Geometry bli = b.getIntersection(li);
                if (bli == null) {
                    return tlip;
                } else {
                    return new V3D_LineSegment(tlip,
                            (V3D_Point) bli);
                }
            } else {
                return new V3D_LineSegment(tlip,
                        (V3D_Point) fli);
            }
        } else {
            return new V3D_LineSegment(tlip,
                    (V3D_Point) rli);
        }
    }
}
