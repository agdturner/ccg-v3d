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

import uk.ac.leeds.ccg.v3d.geometry.envelope.V3D_Envelope;

/**
 * V3D_Triangle This general class defines those planes that are all bounded
 * by their envelope exactly. It can be extended to consider finite planes that
 * are not triangular a particular shape, such as circles, ellipses, triangles, squares, and
 * other regular or irregular 2D shapes.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Triangle extends V3D_Plane implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    public V3D_Triangle(V3D_Point p, V3D_Point q, V3D_Point r) {
        super(p, q, r, false);
    }

    @Override
    public V3D_Envelope getEnvelope() {
        return new V3D_Envelope(e, p, q, r);
    }

    /**
     * @param pt The point to intersect with.
     * @return A point or line segment.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt) {
        if (getEnvelope().isIntersectedBy(pt)) {
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
                V3D_Envelope en = getEnvelope();
                /**
                 * Handle special cases where the envelope is effectively flat
                 * as the plane aligns with an axis.
                 */
                boolean dx0 = en.getxMin().equals(en.getxMax());
                boolean dy0 = en.getyMin().equals(en.getyMax());
                boolean dz0 = en.getzMin().equals(en.getzMax());
                if (dx0) {
                    if (dy0 || dz0) {
                        return new V3D_LineSegment(
                                new V3D_Point(e, en.getxMin(), en.getyMin(), en.getzMin()),
                                new V3D_Point(e, en.getxMax(), en.getyMax(), en.getzMax()));
                    }
                    // Intersects with t?
                    V3D_Point tlf = new V3D_Point(e, en.getxMin(), en.getyMin(), en.getzMax());
                    V3D_Point tla = new V3D_Point(e, en.getxMin(), en.getyMax(), en.getzMax());
                    V3D_LineSegment t = new V3D_LineSegment(tlf, tla);
                    V3D_Geometry tli = t.getIntersection(li);
                    if (tli == null) {
                        // Intersects with f?
                        V3D_Point blf = new V3D_Point(e, en.getxMin(), en.getyMin(), en.getzMin());
                        V3D_LineSegment f = new V3D_LineSegment(tlf, blf);
                        V3D_Geometry fli = f.getIntersection(li);
                        if (fli == null) {
                            // Intersects with a?
                            V3D_Point bla = new V3D_Point(e, en.getxMin(), en.getyMax(), en.getzMin());
                            V3D_LineSegment a = new V3D_LineSegment(bla, tla);
                            V3D_Geometry ali = a.getIntersection(li);
                            if (ali == null) {
                                V3D_LineSegment b = new V3D_LineSegment(bla, blf);
                                // Intersects b
                                return b.getIntersection(li);
                            } else {
                                return ali;
                            }
                        } else if (fli instanceof V3D_LineSegment) {
                            return fli;
                        } else {
                            V3D_Point flip = (V3D_Point) fli;
                            // Check for intersection with a, b
                            // Intersects with a?
                            V3D_Point bla = new V3D_Point(e, en.getxMin(), en.getyMax(), en.getzMin());
                            V3D_LineSegment a = new V3D_LineSegment(bla, tla);
                            V3D_Geometry ali = a.getIntersection(li);
                            if (ali == null) {
                                // Intersects b
                                V3D_LineSegment b = new V3D_LineSegment(bla, blf);
                                V3D_Point blip = (V3D_Point) b.getIntersection(li);
                                if (flip.equals(blip)) {
                                    return flip;
                                } else {
                                    return new V3D_LineSegment(blip, flip);
                                }
                            } else {
                                return new V3D_LineSegment((V3D_Point) ali, flip);
                            }
                        }
                    } else if (tli instanceof V3D_LineSegment) {
                        return tli;
                    } else {
                        V3D_Point tlip = (V3D_Point) tli;
                        // Check for intersections with a, f, b
                        // Intersects with a?
                        V3D_Point bla = new V3D_Point(e, en.getxMin(), en.getyMax(), en.getzMin());
                        V3D_LineSegment a = new V3D_LineSegment(bla, tla);
                        V3D_Geometry ali = a.getIntersection(li);
                        if (ali == null) {
                            // Check for intersections with f, b
                            V3D_Point blf = new V3D_Point(e, en.getxMin(), en.getyMin(), en.getzMin());
                            return getIntersects_fb(li, en, tlf, blf, bla, tlip);
                        } else if (ali instanceof V3D_LineSegment) {
                            return ali;
                        } else {
                            V3D_Point alip = (V3D_Point) ali;
                            if (alip.equals(tlip)) {
                                // Check for intersections with f, b
                                V3D_Point blf = new V3D_Point(e, en.getxMin(), en.getyMin(), en.getzMin());
                                return getIntersects_fb(li, en, tlf, blf, bla, tlip);
                            } else {
                                return new V3D_LineSegment(alip, tlip);
                            }
                        }
                    }
                }
                if (dy0) {
                    if (dz0) {
                        return new V3D_LineSegment(
                                new V3D_Point(e, en.getxMin(), en.getyMin(), en.getzMin()),
                                new V3D_Point(e, en.getxMax(), en.getyMax(), en.getzMax()));
                    }
                    // Intersects with t?
                    V3D_Point tlf = new V3D_Point(e, en.getxMin(), en.getyMin(), en.getzMax());
                    V3D_Point trf = new V3D_Point(e, en.getxMax(), en.getyMin(), en.getzMax());
                    V3D_LineSegment t = new V3D_LineSegment(tlf, tlf);
                    V3D_Geometry tli = t.getIntersection(li);
                    if (tli == null) {
                        // Intersects with l?
                        V3D_Point blf = new V3D_Point(e, en.getxMin(), en.getyMin(), en.getzMin());
                        V3D_LineSegment l = new V3D_LineSegment(tlf, blf);
                        V3D_Geometry lli = l.getIntersection(li);
                        if (lli == null) {
                            // Intersects with r?
                            V3D_Point brf = new V3D_Point(e, en.getxMax(), en.getyMin(), en.getzMin());
                            V3D_LineSegment r = new V3D_LineSegment(brf, trf);
                            V3D_Geometry rli = r.getIntersection(li);
                            if (rli == null) {
                                V3D_LineSegment b = new V3D_LineSegment(brf, blf);
                                // Intersects b
                                return b.getIntersection(li);
                            } else {
                                return rli;
                            }
                        } else if (lli instanceof V3D_LineSegment) {
                            return lli;
                        } else {
                            V3D_Point llip = (V3D_Point) lli;
                            // Check for intersection with r, b
                            // Intersects with r?
                            V3D_Point brf = new V3D_Point(e, en.getxMax(), en.getyMin(), en.getzMin());
                            V3D_LineSegment r = new V3D_LineSegment(brf, trf);
                            V3D_Geometry rli = r.getIntersection(li);
                            if (rli == null) {
                                V3D_LineSegment b = new V3D_LineSegment(brf, blf);
                                V3D_Geometry bli = b.getIntersection(li);
                                if (bli == null) {
                                    return llip;
                                } else {
                                    return new V3D_LineSegment(llip, (V3D_Point) bli);
                                }
                            } else {
                                return rli;
                            }
                        }
                    } else if (tli instanceof V3D_LineSegment) {
                        return tli;
                    } else {
                        V3D_Point tlip = (V3D_Point) tli;
                        // Check for intersections with r, l, b
                        // Intersects with r?
                        V3D_Point brf = new V3D_Point(e, en.getxMax(), en.getyMin(), en.getzMin());
                        V3D_LineSegment r = new V3D_LineSegment(brf, trf);
                        V3D_Geometry rli = r.getIntersection(li);
                        if (rli == null) {
                            // Check for intersections with l, b
                            V3D_Point blf = new V3D_Point(e, en.getxMin(), en.getyMin(), en.getzMin());
                            return getIntersects_lb(li, en, tlf, blf, brf, tlip);
                        } else if (rli instanceof V3D_LineSegment) {
                            return rli;
                        } else {
                            V3D_Point rlip = (V3D_Point) rli;
                            if (rlip.equals(tlip)) {
                                // Check for intersections with l, b
                                V3D_Point blf = new V3D_Point(e, en.getxMin(), en.getyMin(), en.getzMin());
                                return getIntersects_lb(li, en, tlf, blf, brf, tlip);
                            } else {
                                return new V3D_LineSegment(rlip, tlip);
                            }
                        }
                    }
                }
                if (dz0) {
                    // Intersects with l?
                    V3D_Point tlf = new V3D_Point(e, en.getxMin(), en.getyMin(), en.getzMax());
                    V3D_Point tla = new V3D_Point(e, en.getxMin(), en.getyMax(), en.getzMax());
                    V3D_LineSegment l = new V3D_LineSegment(tlf, tla);
                    V3D_Geometry lli = l.getIntersection(li);
                    if (lli == null) {
                        // Intersects with f?
                        V3D_Point trf = new V3D_Point(e, en.getxMax(), en.getyMin(), en.getzMax());
                        V3D_LineSegment f = new V3D_LineSegment(tlf, trf);
                        V3D_Geometry fli = f.getIntersection(li);
                        if (fli == null) {
                            // Intersects with a?
                            V3D_Point tra = new V3D_Point(e, en.getxMax(), en.getyMax(), en.getzMax());
                            V3D_LineSegment a = new V3D_LineSegment(tra, tla);
                            V3D_Geometry ali = a.getIntersection(li);
                            if (ali == null) {
                                V3D_LineSegment r = new V3D_LineSegment(tra, trf);
                                // Intersects r
                                return r.getIntersection(li);
                            } else {
                                return ali;
                            }
                        } else if (fli instanceof V3D_LineSegment) {
                            return fli;
                        } else {
                            V3D_Point flip = (V3D_Point) fli;
                            // Check for intersection with a, r
                            // Intersects with a?
                            V3D_Point tra = new V3D_Point(e, en.getxMax(), en.getyMax(), en.getzMax());
                            V3D_LineSegment a = new V3D_LineSegment(tra, tla);
                            V3D_Geometry ali = a.getIntersection(li);
                            if (ali == null) {
                                // Intersects r
                                V3D_LineSegment r = new V3D_LineSegment(tra, trf);
                                V3D_Point rlip = (V3D_Point) r.getIntersection(li);
                                if (flip.equals(rlip)) {
                                    return flip;
                                } else {
                                    return new V3D_LineSegment(rlip, flip);
                                }
                            } else {
                                return new V3D_LineSegment((V3D_Point) ali, flip);
                            }
                        }
                    } else if (lli instanceof V3D_LineSegment) {
                        return lli;
                    } else {
                        V3D_Point llip = (V3D_Point) lli;
                        // Check for intersections with a, f, r
                        // Intersects with a?
                        V3D_Point tra = new V3D_Point(e, en.getxMax(), en.getyMax(), en.getzMax());
                        V3D_LineSegment a = new V3D_LineSegment(tla, tra);
                        V3D_Geometry ali = a.getIntersection(li);
                        if (ali == null) {
                            // Check for intersections with f, r
                            V3D_Point trf = new V3D_Point(e, en.getxMax(), en.getyMin(), en.getzMax());
                            return getIntersects_fr(li, en, tlf, trf, tra, llip);
                        } else if (ali instanceof V3D_LineSegment) {
                            return ali;
                        } else {
                            V3D_Point alip = (V3D_Point) ali;
                            if (alip.equals(llip)) {
                                // Check for intersections with f, r
                                V3D_Point trf = new V3D_Point(e, en.getxMax(), en.getyMin(), en.getzMax());
                                return getIntersects_fr(li, en, tlf, trf, tra, llip);
                            } else {
                                return new V3D_LineSegment(alip, llip);
                            }
                        }
                    }
                }
                /**
                 * There are 8 corners: 4 at the top; tlf (Top, Left, For), tla
                 * (Top, Left, Aft), trf (Top, Right, For), tra (Top, Right,
                 * Aft); 4 at the bottom; blf (Bottom, Left, For), bla (Bottom,
                 * Left, Aft), brf (Bottom, Right, For), bra (Bottom, Right,
                 * Aft)
                 */
                V3D_Point tlf = new V3D_Point(e, en.getxMin(), en.getyMin(), en.getzMax());
                V3D_Point tla = new V3D_Point(e, en.getxMin(), en.getyMax(), en.getzMax());
                V3D_Point trf = new V3D_Point(e, en.getxMax(), en.getyMin(), en.getzMax());
                V3D_Point tra = new V3D_Point(e, en.getxMax(), en.getyMax(), en.getzMax());
                V3D_Point blf = new V3D_Point(e, en.getxMin(), en.getyMin(), en.getzMin());
                V3D_Point bla = new V3D_Point(e, en.getxMin(), en.getyMax(), en.getzMin());
                V3D_Point brf = new V3D_Point(e, en.getxMax(), en.getyMin(), en.getzMin());
                V3D_Point bra = new V3D_Point(e, en.getxMax(), en.getyMax(), en.getzMin());
                V3D_Triangle t = new V3D_Triangle(tlf, tla, trf);
                V3D_Triangle b = new V3D_Triangle(blf, bla, brf);
                V3D_Triangle l = new V3D_Triangle(blf, bla, tlf);
                V3D_Triangle r = new V3D_Triangle(brf, bra, trf);
                V3D_Triangle f = new V3D_Triangle(brf, blf, tlf);
                V3D_Triangle a = new V3D_Triangle(bra, bla, tra);
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
                        return getIntersects_arfb(li, a, r,
                                f, b, tlip);
                    } else if (lli instanceof V3D_LineSegment) {
                        return lli;
                    } else {
                        if (lli instanceof V3D_LineSegment) {
                            return lli;
                        } else {
                            V3D_Point llip = (V3D_Point) lli;
                            if (llip.equals(tlip)) {
                                return getIntersects_arfb(li, a, r,
                                        f, b, tlip);
                            } else {
                                return new V3D_LineSegment(tlip, llip);
                            }
                        }
                    }
                }
            } else if (pi instanceof V3D_Point) {
                if (getEnvelope().isIntersectedBy((V3D_Point) pi)) {
                    return pi;
                }
            }
        }
        return null;
    }

    private V3D_Geometry getIntersects_fr(V3D_Line li, V3D_Envelope en,
            V3D_Point tlf, V3D_Point trf, V3D_Point tra, V3D_Point llip) {
        V3D_LineSegment f = new V3D_LineSegment(tlf, trf);
        V3D_Geometry fli = f.getIntersection(li);
        if (fli == null) {
            // Intersects with r?
            V3D_LineSegment r = new V3D_LineSegment(tra, trf);
            V3D_Geometry rli = r.getIntersection(li);
            if (rli == null) {
                return llip;
            } else {
                return new V3D_LineSegment((V3D_Point) rli, llip);
            }
        } else {
            if (fli instanceof V3D_LineSegment) {
                return fli;
            } else {
                return new V3D_LineSegment(llip, (V3D_Point) fli);
            }
        }
    }

    private V3D_Geometry getIntersects_fb(V3D_Line li, V3D_Envelope en,
            V3D_Point tlf, V3D_Point blf, V3D_Point bla, V3D_Point tlip) {
        V3D_LineSegment f = new V3D_LineSegment(tlf, blf);
        V3D_Geometry fli = f.getIntersection(li);
        if (fli == null) {
            // Intersects with b?
            V3D_LineSegment b = new V3D_LineSegment(bla, blf);
            V3D_Geometry bli = b.getIntersection(li);
            if (bli == null) {
                return tlip;
            } else {
                return new V3D_LineSegment((V3D_Point) bli, tlip);
            }
        } else {
            if (fli instanceof V3D_LineSegment) {
                return fli;
            } else {
                return new V3D_LineSegment(tlip, (V3D_Point) fli);
            }
        }
    }

    private V3D_Geometry getIntersects_lb(V3D_Line li, V3D_Envelope en,
            V3D_Point tlf, V3D_Point blf, V3D_Point brf, V3D_Point tlip) {
        V3D_LineSegment l = new V3D_LineSegment(tlf, blf);
        V3D_Geometry lli = l.getIntersection(li);
        if (lli == null) {
            // Intersects with b?
            V3D_LineSegment b = new V3D_LineSegment(brf, blf);
            V3D_Geometry bli = b.getIntersection(li);
            if (bli == null) {
                return tlip;
            } else {
                return new V3D_LineSegment((V3D_Point) bli, tlip);
            }
        } else {
            if (lli instanceof V3D_LineSegment) {
                return lli;
            } else {
                return new V3D_LineSegment(tlip, (V3D_Point) lli);
            }
        }
    }

    private V3D_Geometry getIntersects_arfb(V3D_Line li,
            V3D_Triangle a, V3D_Triangle r, V3D_Triangle f,
            V3D_Triangle b, V3D_Point tlip) {
        V3D_Geometry ali = a.getIntersection(li);
        if (ali == null) {
            return getIntersects_rfb(li, r, f, b, tlip);
        } else if (ali instanceof V3D_LineSegment) {
            return ali;
        } else {
            V3D_Point alip = (V3D_Point) ali;
            if (alip.equals(tlip)) {
                return getIntersects_rfb(li, r, f, b, tlip);
            } else {
                return new V3D_LineSegment(tlip, alip);
            }
        }
    }

    private V3D_Geometry getIntersects_rfb(V3D_Line li,
            V3D_Triangle r, V3D_Triangle f, V3D_Triangle b,
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
