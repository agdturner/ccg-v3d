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

import ch.obermuhlner.math.big.BigRational;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigDecimal;

/**
 * 3D representation of an infinite length line. The line passes through the
 * point {@link #p} and is travelling in the direction {@link #v}.
 * <ul>
 * <li>Vector Form
 * <ul>
 * <li>(x,y,z) = (p.x,p.y,p.z) + t(v.dx,v.dy,v.dz)</li>
 * </ul>
 * <li>Parametric Form (where t describes a particular point on the line)
 * <ul>
 * <li>x = p.x + t(v.dx)</li>
 * <li>y = p.y + t(v.dy)</li>
 * <li>z = p.z + t(v.dz)</li>
 * </ul>
 * <li>Symmetric Form (assume v.dx, v.dy, and v.dz are all nonzero)
 * <ul>
 * <li>(x−p.x)/v.dx = (y−p.y)/v.dy = (z−p.z)/v.dz</li>
 * </ul></li>
 * </ul>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Line extends V3D_Geometry {

    private static final long serialVersionUID = 1L;

    /**
     * A point defining the line.
     */
    public V3D_Point p;

    /**
     * A point defining the line.
     */
    public V3D_Point q;

    /**
     * The direction vector from {@link #p} in the direction of {@link #q}.
     */
    public V3D_Vector v;

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
        v = new V3D_Vector(e, q.x.subtract(p.x), q.y.subtract(p.y),
                q.z.subtract(p.z));
    }

    /**
     * @param p What {@link #p} is set to.
     * @param v What {@link #v} is set to.
     */
    public V3D_Line(V3D_Point p, V3D_Vector v) {
        super(p.e);
        this.p = new V3D_Point(p);
        this.v = v;
        q = p.apply(v);
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
                + ", q=" + q.toString() + ", v=" + v.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Line) {
            V3D_Line l = (V3D_Line) o;
            if (this.isIntersectedBy(l.p) && this.isIntersectedBy(l.q)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.p);
        hash = 17 * hash + Objects.hashCode(this.v);
        return hash;
    }

    /**
     * @param pt A point to test for intersection.
     * @return {@code true} if p is on the line.
     */
    public boolean isIntersectedBy(V3D_Point pt) {
        V3D_Vector ppt = new V3D_Vector(e, pt.x.subtract(p.x),
                pt.y.subtract(p.y), pt.z.subtract(p.z));
        V3D_Vector cp = v.getCrossProduct(ppt);
        return cp.dx.isZero() && cp.dy.isZero() && cp.dz.isZero();
    }

    /**
     * @param l The line to test this with to see if they are parallel.
     * @return {@code true} If this and {@code l} are parallel.
     */
    public boolean isParallel(V3D_Line l) {
        V3D_Vector cp = v.getCrossProduct(l.v);
        return cp.dx.isZero() && cp.dy.isZero() && cp.dz.isZero();
    }

    /**
     * This computes the intersection and tests if it is {@code null}
     *
     * @param l The line to test if it isIntersectedBy with this.
     * @return {@code true} If this and {@code l} intersect.
     */
    public boolean isIntersectedBy(V3D_Line l) {
        return this.getIntersection(l) != null;
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}.
     *
     * @param l The line to get intersection with this.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry getIntersection(V3D_Line l) {
        return getIntersection(this, l);
    }
    
    public static V3D_Geometry getIntersection(V3D_Line l0, V3D_Line l1) {
        // First check if the points that define l intersect this.
        if (l0.isIntersectedBy(l1.p)) {
            if (l0.isIntersectedBy(l1.q)) {
                return l0; // The lines are coincident.
            } else {
                return l1.p;
            }
        } else {
            if (l0.isIntersectedBy(l1.q)) {
                return l1.q;
            } else {
                // Case of parallel and non equal lines.
                if (l0.isParallel(l1)) {
                    return null;
                }
                /**
                 * Find the intersection point where the two equations of the
                 * lines meet. (x(t)−x0)/a = (y(t)−y0)/b = (z(t)−z0)/c
                 */
                // (x−p.x)/a = (y−p.y)/b = (z−p.z)/c
                // t = (v.dx - x0)/p.x;
                // t = (v.dy - y0)/p.y;
                // t = (v.dz - z0)/p.z;
                // x(t) = t(dx)+q.x
                // y(t) = t(dy)+q.y
                // z(t) = t(dz)+q.z
                // 1: t(v.dx)+q.x = s(l.v.dx)+l.q.x
                // 2: t(v.dy)+q.y = s(l.v.dy)+l.q.y
                // 3: t(v.dz)+q.z = s(l.v.dz)+l.q.z
                // Let:
                // l.v.dx = k; l.v.dy = l; l.v.dz = m;
                // From 1:
                // t = ((s(k)+l.q.x-q.x)/(v.dx))
                // Let:
                // l.q.x-q.x = e; l.q.y-q.y = f; l.q.z-q.z = g
                // v.dx = a; v.dy = b; v.dz = c
                // t = (sk+e)/a
                // Sub into 2:
                // ((sk+e)/a)b+q.y = sl + l.q.y
                // skb/a +eb/a - s1 = l.q.y - q.y
                // s(kb/a - l) = l.q.y - q.y - eb/a
                // s = (l.q.y - q.y - eb/a) / ((kb/a) - l)
                BigRational t;
                if (l0.v.dx.isZero()) {
                    // Line has constant x                    
                    if (l0.v.dy.isZero()) {
                        // Line has constant y
                        // Line is parallel to z axis
                        /*
                         * x = p.x + t(v.dx)
                         * y = p.y + t(v.dy)
                         * z = p.z + t(v.dz)
                         */
                        BigRational num = l1.q.x.subtract(l0.q.x).subtract(l1.q.z
                                .subtract(l0.q.z).multiply(l0.v.dx).divide(l0.v.dz));
                        BigRational den = l1.v.dz.multiply(l0.v.dx).divide(l0.v.dz)
                                .subtract(l1.v.dx);
                        t = num.divide(den).multiply(l1.v.dz).add(l1.q.z)
                                .subtract(l0.q.z).divide(l0.v.dz);
                    } else if (l0.v.dz.isZero()) {
                        // Line has constant z
                        BigRational num = l1.q.z.subtract(l0.q.z).subtract(l1.q.y
                                .subtract(l0.q.y).multiply(l0.v.dz).divide(l0.v.dy));
                        BigRational den = l1.v.dy.multiply(l0.v.dz).divide(l0.v.dy)
                                .subtract(l1.v.dz);
                        t = num.divide(den).multiply(l1.v.dz).add(l1.q.z)
                                .subtract(l0.q.z).divide(l0.v.dz);
                    } else {
                        BigRational den = l1.v.dy.multiply(l0.v.dx).divide(l0.v.dy)
                                .subtract(l1.v.dx);
                        BigRational num = l1.q.x.subtract(l0.q.x).subtract(l1.q.y
                                .subtract(l0.q.y).multiply(l0.v.dx).divide(l0.v.dy));
                        t = num.divide(den).multiply(l1.v.dy).add(l1.q.y)
                                .subtract(l0.q.y).divide(l0.v.dy);
                    }
                } else {
                    if (l0.v.dy.isZero()) {
                        if (l0.v.dz.isZero()) {
                            BigRational num = l1.q.y.subtract(l0.q.y).subtract(l1.q.x
                                    .subtract(l0.q.z).multiply(l0.v.dy).divide(l0.v.dx));
                            BigRational den = l1.v.dz.multiply(l0.v.dy).divide(l0.v.dx)
                                    .subtract(l1.v.dy);
                            t = num.divide(den).multiply(l1.v.dy).add(l1.q.x)
                                    .subtract(l0.q.x).divide(l0.v.dx);
                        } else {
                            BigRational num = l1.q.y.subtract(l0.q.y).subtract(l1.q.z
                                    .subtract(l0.q.z).multiply(l0.v.dy).divide(l0.v.dz));
                            BigRational den = l1.v.dz.multiply(l0.v.dy).divide(l0.v.dz)
                                    .subtract(l1.v.dy);
                            t = num.divide(den).multiply(l1.v.dx).add(l1.q.x)
                                    .subtract(l0.q.x).divide(l0.v.dx);
                        }
                    } else if (l0.v.dz.isZero()) {
                        BigRational num = l1.q.z.subtract(l0.q.z).subtract(l1.q.y
                                .subtract(l0.q.y).multiply(l0.v.dz).divide(l0.v.dy));
                        BigRational den = l1.v.dy.multiply(l0.v.dz).divide(l0.v.dy)
                                .subtract(l1.v.dz);
                        t = num.divide(den).multiply(l1.v.dx).add(l1.q.x)
                                .subtract(l0.q.x).divide(l0.v.dx);
                    } else {
                        //dy dz nonzero
                        BigRational den = l1.v.dx.multiply(l0.v.dy).divide(l0.v.dx)
                                .subtract(l1.v.dy);
                        BigRational num = l1.q.y.subtract(l0.q.y).subtract(l1.q.x
                                .subtract(l0.q.x).multiply(l0.v.dy).divide(l0.v.dx));
                        t = num.divide(den).multiply(l1.v.dx).add(l1.q.x)
                                .subtract(l0.q.x).divide(l0.v.dx);
                    }
                }
                return new V3D_Point(l0.e,
                        t.multiply(l0.v.dx).add(l0.q.x),
                        t.multiply(l0.v.dy).add(l0.q.y),
                        t.multiply(l0.v.dz).add(l0.q.z));
            }
        }
    }

    /**
     * @param l A line.
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return The shortest distance between this and {@code l}.
     */
    public BigDecimal getDistance(V3D_Line l, int scale, RoundingMode rm) {
        // The coordinates of points along the lines are given by:
        // p = <p.x, p.y, p.z> + t<v.dx, v.dy, v.dz>
        // lp = <l.p.x, l.p.y, l.p.z> + t<l.v.dx, l.v.dy, l.v.dz>
        // p2 = r2+t2e2
        // The line connecting the closest points has direction vector:
        // n = v.l.v
        V3D_Vector n = v.getCrossProduct(l.v);
        // d = n.(p−l.p)/||n||
        V3D_Vector p_sub_lp = new V3D_Vector(e, p.x.subtract(l.p.x),
                p.y.subtract(l.p.y), p.z.subtract(l.p.z));
        BigRational m = BigRational.valueOf(n.getMagnitude(scale, rm));
        BigRational d = n.getDotProduct(p_sub_lp).divide(m);
        return Math_BigDecimal.roundIfNecessary(d.toBigDecimal(), scale, rm);
    }
}
