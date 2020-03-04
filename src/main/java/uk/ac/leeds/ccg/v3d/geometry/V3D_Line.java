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
package uk.ac.leeds.ccg.v3d.geometry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigDecimal;

/**
 * Class for a line in 3D represented by two points {@link #p} and {@link #q}
 * and the following equations:
 * <ul>
 * <li>{@code r(t) = <x(t),y(t),z(t)>}</li>
 * <li>{@code v = <dx,dy,dz>} - This is the vector from p to q.</li>
 * <li>{@code q = <q.x,q.y,q.z>}</li>
 * <li>{@code r(t) = tv+q = <t(dx)+q.x, t(dy)+q.y, t(dz)+q.z>}</li>
 * <li>{@code x(t) = t(dx)+q.x}</li>
 * <li>{@code y(t) = t(dy)+q.y}</li>
 * <li>{@code z(t) = t(dz)+q.z}</li>
 * <li>{@code t = x(t)−q.x(dx)}</li>
 * <li>{@code t = y(t)−q.y(dy)}</li>
 * <li>{@code t = z(t)−q.z(dz)}</li>
 * <li>{@code x(t)−q.x(dx) = y(t)−q.y(dy) = z(t)−q.z(dz)}</li>
 * <li>{@code r(t) = t(pq)+b = <p.x+(pq.dx)t, p.y+(pq.y)t, p.z+(pq.z)t>}</li>
 * </ul>
 * The line is infinite.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Line extends V3D_Geometry {

    /**
     * A point defining the line.
     */
    public V3D_Point p;

    /**
     * A point defining the line.
     */
    public V3D_Point q;

    /**
     * The direction vector from p in the direction of q.
     */
    public V3D_Vector pq;

    /**
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @throws RuntimeException If p and q are coincident.
     */
    public V3D_Line(V3D_Point p, V3D_Point q) {
        super(p.e);
        if (p.equals(q)) {
            throw new RuntimeException("The inputs p and q are the same point "
                    + "and do not define a line.");
        }
        this.p = new V3D_Point(p);
        this.q = new V3D_Point(q);
        pq = new V3D_Vector(q.x.subtract(p.x), q.y.subtract(p.y),
                q.z.subtract(p.z));
    }

    /**
     * @param l Vector_LineSegment3D
     */
    public V3D_Line(V3D_Line l) {
        super(l.e);
        this.p = l.p;
        this.q = l.q;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(p=" + p.toString()
                + ", q=" + q.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Line) {
            V3D_Line l = (V3D_Line) o;
            if (this.getIntersects(l.p) && this.getIntersects(l.q)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.p);
        hash = 17 * hash + Objects.hashCode(this.q);
        return hash;
    }

    /**
     *
     * @param pt A point to test for intersection within the specified
     * tolerance.
     * @return {@code true} if p is on the line.
     */
    public boolean getIntersects(V3D_Point pt) {
        V3D_Vector ppt = new V3D_Vector(pt.x.subtract(p.x), pt.y.subtract(p.y),
                pt.z.subtract(p.z));
        V3D_Vector cp = pq.getCrossProduct(ppt);
        return cp.dx.compareTo(BigDecimal.ZERO) == 0
                && cp.dy.compareTo(BigDecimal.ZERO) == 0
                && cp.dz.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * @param l The line to test this with to see if they are parallel.
     * @return {@code true} If this and {@code l} are parallel.
     */
    public boolean isParallel(V3D_Line l) {
        V3D_Vector v = pq.getCrossProduct(l.pq);
        return v.dx.compareTo(BigDecimal.ZERO) == 0 
                && v.dy.compareTo(BigDecimal.ZERO) == 0
                && v.dz.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * @param pl
     * @return {@code true} if this intersects {
     * @pl}.
     */
    public boolean getIntersects(V3D_Plane pl) {
        throw new UnsupportedOperationException();
    }

    /**
     * This computes the intersection and tests if it is {@code null}
     *
     * @param l The line to test if it intersects with this.
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} If this and {@code l} intersect.
     */
    public boolean getIntersects(V3D_Line l, int scale, RoundingMode rm) {
        return this.getIntersection(l, scale, rm) != null;
    }

    /**
     * if this and l are the same then return this.
     *
     * @param l The line to get intersection with this.
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} If this and {@code l} intersect.
     */
    public V3D_Geometry getIntersection(V3D_Line l, int scale, RoundingMode rm) {
        BigDecimal epsilon = BigDecimal.ONE.scaleByPowerOfTen(-scale);
        if (getIntersects(l.p)) {
            if (getIntersects(l.q)) {
                return this;
            } else {
                return l.p;
            }
        } else {
            if (getIntersects(l.q)) {
                return l.q;
            } else {
                /**
                 * Find the intersection point where the two equations of the
                 * lines meet. (x(t)−x0)/a = (y(t)−y0)/b = (z(t)−z0)/c
                 */
                // (x−p.x)/a = (y−p.y)/b = (z−p.z)/c
                // t = (pq.dx - x0)/p.x;
                // t = (pq.dy - y0)/p.y;
                // t = (pq.dz - z0)/p.z;
                // x(t) = t(dx)+q.x
                // y(t) = t(dy)+q.y
                // z(t) = t(dz)+q.z
                // 1: t(pq.dx)+q.x = s(l.pq.dx)+l.q.x
                // 2: t(pq.dy)+q.y = s(l.pq.dy)+l.q.y
                // 3: t(pq.dz)+q.z = s(l.pq.dz)+l.q.z
                // Let:
                // l.pq.dx = k; l.pq.dy = l; l.pq.dz = m;
                // From 1:
                // t = ((s(k)+l.q.x-q.x)/(pq.dx))
                // Let:
                // l.q.x-q.x = e; l.q.y-q.y = f; l.q.z-q.z = g
                // pq.dx = a; pq.dy = b; pq.dz = c
                // t = (sk+e)/a
                // Sub into 2:
                // ((sk+e)/a)b+q.y = sl + l.q.y
                // skb/a +eb/a - s1 = l.q.y - q.y
                // s(kb/a - l) = l.q.y - q.y - eb/a
                // s = (l.q.y - q.y - eb/a) / ((kb/a) - l)
                // Calculate s:
                BigDecimal numerator = l.q.y.subtract(q.y).subtract(
                        Math_BigDecimal.divideRoundIfNecessary(
                                l.q.x.subtract(q.x).multiply(pq.dy), pq.dx, scale, rm));
                BigDecimal denominator = Math_BigDecimal.divideRoundIfNecessary(
                        l.pq.dx.multiply(pq.dy), pq.dx, scale, rm).subtract(l.pq.dy);
                if (denominator.compareTo(BigDecimal.ZERO) == 0) {
                    numerator = l.q.z.subtract(q.z).subtract(
                            Math_BigDecimal.divideRoundIfNecessary(
                                    l.q.y.subtract(q.y).multiply(pq.dz), pq.dy, scale, rm));
                    denominator = Math_BigDecimal.divideRoundIfNecessary(
                            l.pq.dy.multiply(pq.dz), pq.dy, scale, rm).subtract(l.pq.dz);
                    if (denominator.compareTo(BigDecimal.ZERO) == 0) {
                        numerator = l.q.x.subtract(q.x).subtract(
                                Math_BigDecimal.divideRoundIfNecessary(
                                        l.q.z.subtract(q.z).multiply(pq.dx), pq.dz, scale, rm));
                        denominator = Math_BigDecimal.divideRoundIfNecessary(
                                l.pq.dz.multiply(pq.dx), pq.dz, scale, rm).subtract(l.pq.dx);
                        BigDecimal s = Math_BigDecimal.divideRoundIfNecessary(
                                numerator, denominator, scale, rm);
                        // Calculate t:
                        // t = ((s(k)+l.q.z-q.z)/(pq.dz))
                        BigDecimal t;
                        if (pq.dz.compareTo(BigDecimal.ZERO) != 0) {
                            t = Math_BigDecimal.divideRoundIfNecessary(
                                    s.multiply(l.pq.dz).add(l.q.z).subtract(q.z),
                                    pq.dz, scale, rm);
                            BigDecimal chk = (t.multiply(pq.dy).add(q.y)).subtract(s.multiply(l.pq.dy).add(l.q.y));
                            if (chk.abs().compareTo(epsilon) != -1) {
                                // There is no intersection, return null;
                                return null;
                            }
                            return new V3D_Point(e,
                                    t.multiply(pq.dx).add(q.x),
                                    t.multiply(pq.dy).add(q.y),
                                    t.multiply(pq.dz).add(q.z));
                        } else {
                            if (pq.dx.compareTo(BigDecimal.ZERO) != 0) {
                                t = Math_BigDecimal.divideRoundIfNecessary(
                                        s.multiply(l.pq.dx).add(l.q.x).subtract(q.x),
                                        pq.dx, scale, rm);
                                BigDecimal chk = (t.multiply(pq.dy).add(q.y)).subtract(s.multiply(l.pq.dy).add(l.q.y));
                                if (chk.abs().compareTo(epsilon) != -1) {
                                    // There is no intersection, return null;
                                    return null;
                                }
                                return new V3D_Point(e,
                                        t.multiply(pq.dx).add(q.x),
                                        t.multiply(pq.dy).add(q.y),
                                        t.multiply(pq.dz).add(q.z));
                            } else {
                                t = Math_BigDecimal.divideRoundIfNecessary(
                                        s.multiply(l.pq.dy).add(l.q.y).subtract(q.y),
                                        pq.dy, scale, rm);
                                return new V3D_Point(e,
                                        t.multiply(pq.dx).add(q.x),
                                        t.multiply(pq.dy).add(q.y),
                                        t.multiply(pq.dz).add(q.z));
                            }
                        }
                    } else {
                        BigDecimal s = Math_BigDecimal.divideRoundIfNecessary(
                                numerator, denominator, scale, rm);
                        // Calculate t:
                        // t = ((s(k)+l.q.y-q.y)/(pq.dy))
                        BigDecimal t;
                        if (pq.dy.compareTo(BigDecimal.ZERO) != 0) {
                            t = Math_BigDecimal.divideRoundIfNecessary(
                                    s.multiply(l.pq.dy).add(l.q.y).subtract(q.y),
                                    pq.dy, scale, rm);
                            BigDecimal chk = (t.multiply(pq.dx).add(q.x)).subtract(s.multiply(l.pq.dx).add(l.q.x));
                            if (chk.abs().compareTo(epsilon) != -1) {
                                // There is no intersection, return null;
                                return null;
                            }
                            return new V3D_Point(e,
                                    t.multiply(pq.dx).add(q.x),
                                    t.multiply(pq.dy).add(q.y),
                                    t.multiply(pq.dz).add(q.z));
                        } else {
                            if (pq.dz.compareTo(BigDecimal.ZERO) != 0) {
                                t = Math_BigDecimal.divideRoundIfNecessary(
                                        s.multiply(l.pq.dz).add(l.q.z).subtract(q.z),
                                        pq.dz, scale, rm);
                                BigDecimal chk = (t.multiply(pq.dx).add(q.x)).subtract(s.multiply(l.pq.dx).add(l.q.x));
                                if (chk.abs().compareTo(epsilon) != -1) {
                                    // There is no intersection, return null;
                                    return null;
                                }
                                return new V3D_Point(e,
                                        t.multiply(pq.dx).add(q.x),
                                        t.multiply(pq.dy).add(q.y),
                                        t.multiply(pq.dz).add(q.z));
                            } else {
                                t = Math_BigDecimal.divideRoundIfNecessary(
                                        s.multiply(l.pq.dx).add(l.q.x).subtract(q.x),
                                        pq.dx, scale, rm);
                                return new V3D_Point(e,
                                        t.multiply(pq.dx).add(q.x),
                                        t.multiply(pq.dy).add(q.y),
                                        t.multiply(pq.dz).add(q.z));
                            }
                        }
                    }
                } else {
                    BigDecimal s = Math_BigDecimal.divideRoundIfNecessary(
                            numerator, denominator, scale, rm);
                    // Calculate t:
                    // t = ((s(k)+l.q.x-q.x)/(pq.dx))
                    BigDecimal t;
                    if (pq.dx.compareTo(BigDecimal.ZERO) != 0) {
                        t = Math_BigDecimal.divideRoundIfNecessary(
                                s.multiply(l.pq.dx).add(l.q.x).subtract(q.x),
                                pq.dx, scale, rm);
                        BigDecimal chk = (t.multiply(pq.dz).add(q.z)).subtract(s.multiply(l.pq.dz).add(l.q.z));
                        if (chk.abs().compareTo(epsilon) != -1) {
                            // There is no intersection, return null;
                            return null;
                        }
                        return new V3D_Point(e,
                                t.multiply(pq.dx).add(q.x),
                                t.multiply(pq.dy).add(q.y),
                                t.multiply(pq.dz).add(q.z));
                    } else {
                        if (pq.dy.compareTo(BigDecimal.ZERO) != 0) {
                            t = Math_BigDecimal.divideRoundIfNecessary(
                                    s.multiply(l.pq.dy).add(l.q.y).subtract(q.y),
                                    pq.dy, scale, rm);
                            BigDecimal chk = (t.multiply(pq.dz).add(q.z)).subtract(s.multiply(l.pq.dz).add(l.q.z));
                            if (chk.abs().compareTo(epsilon) != -1) {
                                // There is no intersection, return null;
                                return null;
                            }
                            return new V3D_Point(e,
                                    t.multiply(pq.dx).add(q.x),
                                    t.multiply(pq.dy).add(q.y),
                                    t.multiply(pq.dz).add(q.z));
                        } else {
                            t = Math_BigDecimal.divideRoundIfNecessary(
                                    s.multiply(l.pq.dz).add(l.q.z).subtract(q.z),
                                    pq.dz, scale, rm);
                            return new V3D_Point(e,
                                    t.multiply(pq.dx).add(q.x),
                                    t.multiply(pq.dy).add(q.y),
                                    t.multiply(pq.dz).add(q.z));
                        }
                    }
                }
            }
        }
    }

    public V3D_Geometry getIntersection(V3D_Plane pl) {
        throw new UnsupportedOperationException();
    }

}
