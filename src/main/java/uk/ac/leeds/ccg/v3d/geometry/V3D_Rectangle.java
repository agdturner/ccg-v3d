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

import ch.obermuhlner.math.big.BigRational;
import uk.ac.leeds.ccg.v3d.geometry.envelope.V3D_Envelope;

/**
 * V3D_Rectangle. This class defines a finite plane that is rectangular. This
 * can be infinitesimally small in which case it is a point when
 * {@link #p}, {@link #q}, {@link #r} and {@link #s} are equal. {@code
 * p ----------- q
 * |             |
 * |             |
 * |             |
 * r ----------- s
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Rectangle extends V3D_Plane implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    public final V3D_Point s;

    /**
     * Checks that pq and rs are orthogonal to pr and qs.
     *
     * @param p The top left corner of the rectangle.
     * @param q The top right corner of the rectangle.
     * @param r The bottom left corner of the rectangle.
     * @param s The bottom right corner of the rectangle.
     */
    public V3D_Rectangle(V3D_Point p, V3D_Point q, V3D_Point r, V3D_Point s) {
        super(p, q, r, true);
        this.s = s;
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
     * @param l The line to intersect with.
     * @return A point or line segment.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l) {
        V3D_Geometry i = super.getIntersection(l);
        if (i == null) {
            return null;
        } else if (i instanceof V3D_Line) {
            V3D_Line li = (V3D_Line) i;
            V3D_Envelope en = getEnvelope();
            V3D_Geometry enil = en.getIntersection(l);
            if (enil == null) {
                return null;
            }
            /**
             * Get the intersection of the line segment and each edge of the
             * rectangle.
             */
            V3D_LineSegment pq2 = new V3D_LineSegment(p, q);
            V3D_Geometry pqi = pq2.getIntersection(li);
            if (pqi == null) {
                // Check qs, sr, rp
                V3D_LineSegment qs = new V3D_LineSegment(q, s);
                V3D_Geometry qsi = qs.getIntersection(li);
                if (qsi == null) {
                    // Check sr, rp
                    V3D_LineSegment sr = new V3D_LineSegment(s, r);
                    V3D_Geometry sri = sr.getIntersection(li);
                    if (sri == null) {
                        // Check rp
                        V3D_LineSegment rp = new V3D_LineSegment(r, p);
                        V3D_Geometry rpi = rp.getIntersection(li);
                        if (rpi == null) {
                            return null;
                        } else {
                            return rpi;
                        }
                    } else if (sri instanceof V3D_LineSegment) {
                        return sri;
                    } else {
                        // Check rp
                        V3D_LineSegment rp = new V3D_LineSegment(r, p);
                        V3D_Geometry rpi = rp.getIntersection(li);
                        if (rpi == null) {
                            return sri;
                        } else {
                            return new V3D_LineSegment((V3D_Point) sri,
                                    (V3D_Point) rpi);
                        }
                    }
                } else if (qsi instanceof V3D_LineSegment) {
                    return qsi;
                } else {
                    // Check sr, rp
                    V3D_LineSegment sr = new V3D_LineSegment(s, r);
                    V3D_Geometry sri = sr.getIntersection(li);
                    if (sri == null) {
                        // Check rp
                        V3D_LineSegment rp = new V3D_LineSegment(r, p);
                        V3D_Geometry rpi = rp.getIntersection(li);
                        if (rpi == null) {
                            return qsi;
                        } else {
                            return new V3D_LineSegment((V3D_Point) qsi,
                                    (V3D_Point) rpi);
                        }
                    } else if (sri instanceof V3D_LineSegment) {
                        return sri;
                    } else {
                        // Check rp
                        V3D_LineSegment rp = new V3D_LineSegment(r, p);
                        V3D_Geometry rpi = rp.getIntersection(li);
                        if (rpi == null) {
                            V3D_Point qsip = (V3D_Point) qsi;
                            V3D_Point srip = (V3D_Point) sri;
                            if (qsip.equals(srip)) {
                                return srip;
                            } else {
                                return new V3D_LineSegment(qsip, srip);
                            }
                        } else {
                            return new V3D_LineSegment((V3D_Point) sri,
                                    (V3D_Point) rpi);
                        }
                    }
                }
            } else if (pqi instanceof V3D_LineSegment) {
                return pqi;
            } else {
                // Check qs, sr, rp
                V3D_LineSegment qs = new V3D_LineSegment(q, s);
                V3D_Geometry qsi = qs.getIntersection(li);
                if (qsi == null) {
                    // Check sr, rp
                    V3D_LineSegment sr = new V3D_LineSegment(s, r);
                    V3D_Geometry sri = sr.getIntersection(li);
                    if (sri == null) {
                        // Check rp
                        V3D_LineSegment rp = new V3D_LineSegment(r, p);
                        V3D_Geometry rpi = rp.getIntersection(li);
                        if (rpi == null) {
                            return pqi;
                        } else {
                            V3D_Point rpip = (V3D_Point) rpi;
                            V3D_Point pqip = (V3D_Point) pqi;
                            if (rpip.equals(pqip)) {
                                return rpip;
                            } else {
                                return new V3D_LineSegment(rpip, pqip);
                            }
                        }
                    } else if (sri instanceof V3D_LineSegment) {
                        return sri;
                    } else {
                        return new V3D_LineSegment((V3D_Point) pqi, (V3D_Point) sri);
                    }
                } else {
                    V3D_Point pqip = (V3D_Point) pqi;
                    V3D_Point qsip = (V3D_Point) qsi;
                    if (pqip.equals(qsip)) {
                        // Check sr, rp
                        V3D_LineSegment sr = new V3D_LineSegment(s, r);
                        V3D_Geometry sri = sr.getIntersection(li);
                        if (sri == null) {
                            // Check rp
                            V3D_LineSegment rp = new V3D_LineSegment(r, p);
                            V3D_Geometry rpi = rp.getIntersection(li);
                            if (rpi == null) {
                                return qsi;
                            } else {
                                return new V3D_LineSegment(qsip,
                                        (V3D_Point) rpi);
                            }
                        } else if (sri instanceof V3D_LineSegment) {
                            return sri;
                        } else {
                            return new V3D_LineSegment(qsip, (V3D_Point) sri);
                        }
                    } else {
                        return new V3D_LineSegment(qsip, pqip);
                    }
                }
            }
        } else {
            V3D_Point pi = (V3D_Point) i;
            if (getEnvelope().isIntersectedBy(pi)) {
                /**
                 * The envelope of this therefore intersects i, but does this?
                 * Can test if the point is between each opposite side. Or can
                 * check going around that the point is always on one side of
                 * the vertices checked. Or can add the distances from the point
                 * to all of the corners of the rectangle and test if this is
                 * less than or equal to the distance of two of the sides and
                 * the distance across the rectangle. Implementing the latter
                 * and using squared distances to avoid having taking square
                 * roots and adding imprecision.
                 */
                // Calculate the distance squared to check dc
                BigRational dc = p.getDistanceSquared(q).add(
                        p.getDistanceSquared(r).add(p.getDistanceSquared(r)));
                // Calculate the distance squared
                BigRational d = pi.getDistanceSquared(p)
                        .add(pi.getDistanceSquared(q)
                                .add(pi.getDistanceSquared(r)
                                        .add(pi.getDistanceSquared(r))));
                if (d.compareTo(dc) != 1) {
                    return pi;
                }
                return null;
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
            V3D_LineSegment r2 = new V3D_LineSegment(tra, trf);
            V3D_Geometry rli = r2.getIntersection(li);
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
            V3D_Rectangle a, V3D_Rectangle r, V3D_Rectangle f,
            V3D_Rectangle b, V3D_Point tlip) {
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
            V3D_Rectangle r, V3D_Rectangle f, V3D_Rectangle b,
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
