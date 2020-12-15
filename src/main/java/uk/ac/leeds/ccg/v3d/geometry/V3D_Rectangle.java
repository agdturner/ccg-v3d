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
 *         t
 *  p ----------- q
 *  |             |
 * l|             |ri
 *  |             |
 *  s ----------- r
 *         b
 * }
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V3D_Rectangle extends V3D_Plane implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The other corner of the rectangle. The others are {@link #p}, {@link #q}, 
     * and {@link #r}.
     */
    public final V3D_Point s;
    
    /**
     * For storing the envelope
     */
    protected V3D_Envelope en;
    
    /**
     * For storing the line segment from {@link #p} to {@link #q}. 
     */
    protected final V3D_LineSegment t;

    /**
     * For storing the line segment from {@link #q} to {@link #r}. 
     */
    protected final V3D_LineSegment ri;

    /**
     * For storing the line segment from {@link #r} to {@link #s}. 
     */
    protected final V3D_LineSegment b;

    /**
     * For storing the vector from {@link #s} to {@link #p}. 
     */
    protected final V3D_LineSegment l;
    
    /**
     * @param p The top left corner of the rectangle.
     * @param q The top right corner of the rectangle.
     * @param r The bottom right corner of the rectangle.
     * @param s The bottom left corner of the rectangle.
     * @throws java.lang.RuntimeException iff the points do not define a 
     * rectangle.
     */
    public V3D_Rectangle(V3D_Point p, V3D_Point q, V3D_Point r, V3D_Point s) {
        super(p, q, r);
        this.s = s;
        //en = new V3D_Envelope(p, q, r, s); Not initialised here as it causes a StackOverflowError
        pq = new V3D_Vector(p, q);
        qr = new V3D_Vector(q, r);
        t = new V3D_LineSegment(p, q);
        ri = new V3D_LineSegment(q, r);
        b = new V3D_LineSegment(r, s);
        l = new V3D_LineSegment(s, p);
        // Check for rectangle.
        if (pq.isZeroVector()) {
            if (qr.isZeroVector()) {
                // Rectangle is a point.
            } else {
                // Rectangle is a line.
            }
        } else {
            if (qr.isZeroVector()) {
                // Rectangle is a line.
            } else {
                // Rectangle has area.
                if (!(pq.isOrthogonal(qr))) {
                    throw new RuntimeException("The points do not define a rectangle.");
                }
            }
        }
    }

    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
            en = new V3D_Envelope(p, q, r, s);
        }
        return en;
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
            getEnvelope();
            V3D_Line li = (V3D_Line) i;
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

}
