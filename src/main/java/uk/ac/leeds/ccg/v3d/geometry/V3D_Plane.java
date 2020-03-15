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
import java.util.Objects;

/**
 * V3D_Plane - for representing infinite flat 2D planes in 3D. The plane is
 * defined by three points {@link #p}, {@link #q} and {@link #r} that are not
 * collinear, or from any point on the plane and a normal vector that is
 * perpendicular to the plane. From three points that are not collinear an
 * equation of the plane can be derived by creating two V3D_Vectors {@link #pq}
 * and {@link #pr}. The equation of the plane is:
 * <ul>
 * <li>A*(x-x0) + B*(y-y0) + C*(z-z0) = 0</li>
 * <li>A*(x) + B*(y) + C*(z) - D = 0 where D = -(A*x0 + B*y0 + C*z0)</li>
 * </ul>
 * where:
 * <ul>
 * <li>x, y, and z are any of the coordinates in {@link #p}, {@link #q} and
 * {@link #r}</li>
 * <li>x0, y0 and z0 represents any other point in the plane</li>
 * <li>{@code A = pq.dy.multiply(pr.dz).subtract(pr.dy.multiply(pq.dz))} - which
 * is the dx of the normal vector (that is perpendicular to the plane).</li>
 * <li>{@code B = (pq.dx.multiply(pr.dz).subtract(pr.dx.multiply(pq.dz))).negate()}
 * - which is the dy of the normal vector.</li>
 * <li>{@code C = pq.dx.multiply(pr.dy).subtract(pr.dx.multiply(pq.dy))} - which
 * is the dz of the normal vector.</li>
 * </ul>
 *
 * <ol>
 * <li>n.dx(x(t)−p.x)+n.dy(y(t)−p.y)+n.dz(z(t)−p.z) = 0</li>
 * <li>n.dx(x(t)−q.x)+n.dy(y(t)−q.y)+n.dz(z(t)−q.z) = 0</li>
 * <li>n.dx(x(t)−r.x)+n.dy(y(t)−r.y)+n.dz(z(t)−r.z) = 0</li>
 * </ol>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Plane extends V3D_Geometry {

    private static final long serialVersionUID = 1L;

    /**
     * One of the points that defines the plane.
     */
    public final V3D_Point p;

    /**
     * One of the points that defines the plane.
     */
    public final V3D_Point q;

    /**
     * One of the points that defines the plane.
     */
    public final V3D_Point r;

    /**
     * The vector representing the move from {@link #p} to {@link #q}.
     */
    public final V3D_Vector pq;

    /**
     * The vector representing the move from {@link #p} to {@link #r}.
     */
    public final V3D_Vector pr;

    /**
     * The normal vector. (This is perpendicular to the plane and it's direction
     * is given by order in which the two vectors {@link #pq} and {@link #pr}
     * are used in a cross product calculation when the plane is constructed.
     */
    public V3D_Vector n;

    /**
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     * @param checkCollinearity If {@code false} the rectangle is for an
     * envelope face which is allowed to collapse to a line or a point.
     * @throws RuntimeException If p, q and r are collinear.
     */
    public V3D_Plane(V3D_Point p, V3D_Point q, V3D_Point r,
            boolean checkCollinearity) {
        super(p.e);
        //                         i                 j                   k
        pq = new V3D_Vector(e, p.x.subtract(q.x), p.y.subtract(q.y), p.z.subtract(q.z));
        pr = new V3D_Vector(e, p.x.subtract(r.x), p.y.subtract(r.y), p.z.subtract(r.z));
        /**
         * Calculate the normal perpendicular vector.
         */
        n = new V3D_Vector(e,
                pq.dy.multiply(pr.dz).subtract(pr.dy.multiply(pq.dz)),
                pq.dx.multiply(pr.dz).subtract(pr.dx.multiply(pq.dz)).negate(),
                pq.dx.multiply(pr.dy).subtract(pr.dx.multiply(pq.dy)));
        this.p = p;
        this.q = q;
        this.r = r;
        if (!checkCollinearity) {
            // Check for collinearity
            if (n.dx.compareTo(e.P0) == 0
                    && n.dy.compareTo(e.P0) == 0
                    && n.dz.compareTo(e.P0) == 0) {
                throw new RuntimeException("The three points do not define a plane, "
                        + "but are collinear (they might all be the same point!");
            }
        }
    }

    /**
     * An equation of the plane containing the point {@code p} with normal
     * vector {@code n} is {@code n.dx(x) + n.dy(y) + n.dz(z) = d}.
     *
     * @param p What {@link #p} is set to.
     * @param n What {@link #n} is set to.
     * @throws RuntimeException If p, q and r are collinear.
     */
    public V3D_Plane(V3D_Point p, V3D_Vector n) {
        super(p.e);
        this.p = p;
        BigRational d = n.dx.multiply(p.x).add(n.dy.multiply(p.y).add(n.dz.multiply(p.z)));
        if (n.dx.compareTo(e.P0) == 0) {
            if (n.dy.compareTo(e.P0) == 0) {

            } else {

            }
        } else {
            if (n.dy.compareTo(e.P0) == 0) {
                if (n.dz.compareTo(e.P0) == 0) {
                }
            } else {
                if (n.dz.compareTo(e.P0) == 0) {
                }
            }
        }
        // Set: x = 0; y = 0
        // Then: z = d/n.dz
        this.q = new V3D_Point(e, e.P0, e.P0, d.divide(n.dz));
        // Set: x = 0; z = 0
        // Then: y = d/n.dy
        this.r = new V3D_Point(e, e.P0, d.divide(n.dy), e.P0);
        pq = new V3D_Vector(e, p.x.subtract(q.x), p.y.subtract(q.y), p.z.subtract(q.z));
        pr = new V3D_Vector(e, p.x.subtract(r.x), p.y.subtract(r.y), p.z.subtract(r.z));
        this.n = n;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(p=" + p.toString()
                + ", q=" + q.toString() + ", r=" + r.toString() + ")";
    }

    /**
     * @param pl The plane to test for intersection with this.
     * @return {@code true} If this and {@code pl} intersect.
     */
    public boolean isIntersectedBy(V3D_Plane pl) {
        if (this.isParallel(pl)) {
            return this.equals(pl);
        }
        return true;
    }

    /**
     * @param l The line to test for intersection with this.
     * @return {@code true} If this and {@code l} intersect.
     */
    public boolean isIntersectedBy(V3D_Line l) {
        if (isParallel(l)) {
            if (!isOnPlane(l)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param pt The point to test if it is on the plane.
     * @return {@code true} If {@code pt} is on the plane.
     */
    public boolean isIntersectedBy(V3D_Point pt) {
        BigRational d = n.dx.multiply(p.x.subtract(pt.x))
                .add(n.dy.multiply(p.y.subtract(pt.y)))
                .add(n.dz.multiply(p.z.subtract(pt.z)));
        return d.compareTo(e.P0) == 0;
    }

    /**
     * @param l The line segment to test if it is on the plane.
     * @return {@code true} If {@code pt} is on the plane.
     */
    public boolean isOnPlane(V3D_Line l) {
        return isIntersectedBy(l.p) && isIntersectedBy(l.q);
    }

    /**
     * @param pl The plane to intersect with the line.
     * @param l The line to intersect with the plane.
     * @return The intersection of the line and the plane. This is either
     * {@code null} a line or a point.
     */
    public static V3D_Geometry getIntersection(V3D_Plane pl, V3D_Line l) {
        if (pl.isParallel(l)) {
            if (pl.isOnPlane(l)) {
                return l;
            }
        }
        // Are there any points in common?
        if (l.p.equals(pl.p)) {
            return pl.p;
        }
        if (l.p.equals(pl.q)) {
            return pl.q;
        }
        if (l.p.equals(pl.r)) {
            return pl.r;
        }
        if (l.q.equals(pl.p)) {
            return pl.p;
        }
        if (l.q.equals(pl.q)) {
            return pl.q;
        }
        if (l.q.equals(pl.r)) {
            return pl.r;
        }

//        BigRational t;
//        if (l.v.dx.isZero()) {
//            // Line has constant x                    
//            if (l.v.dy.isZero()) {
//                // Line has constant y
//                // Line is parallel to z axis
//                /*
//                         * x = p.x + t(v.dx)
//                         * y = p.y + t(v.dy)
//                         * z = p.z + t(v.dz)
//                 */
//                BigRational num = l.q.x.subtract(q.x).subtract(l.q.z
//                        .subtract(q.z).multiply(v.dx).divide(v.dz));
//                BigRational den = l.v.dz.multiply(v.dx).divide(v.dz)
//                        .subtract(l.v.dx);
//                t = num.divide(den).multiply(l.v.dz).add(l.q.z)
//                        .subtract(q.z).divide(v.dz);
//            } else if (l.v.dz.isZero()) {
//                // Line has constant z
//                BigRational num = l.q.z.subtract(q.z).subtract(l.q.y
//                        .subtract(q.y).multiply(v.dz).divide(v.dy));
//                BigRational den = l.v.dy.multiply(v.dz).divide(v.dy)
//                        .subtract(l.v.dz);
//                t = num.divide(den).multiply(l.v.dz).add(l.q.z)
//                        .subtract(q.z).divide(v.dz);
//            } else {
//                BigRational den = l.v.dy.multiply(v.dx).divide(v.dy)
//                        .subtract(l.v.dx);
//                BigRational num = l.q.x.subtract(q.x).subtract(l.q.y
//                        .subtract(q.y).multiply(v.dx).divide(v.dy));
//                t = num.divide(den).multiply(l.v.dy).add(l.q.y)
//                        .subtract(q.y).divide(v.dy);
//            }
//        } else {
//            if (l.v.dy.isZero()) {
//                if (l.v.dz.isZero()) {
//                    BigRational num = l.q.y.subtract(q.y).subtract(l.q.x
//                            .subtract(q.z).multiply(v.dy).divide(v.dx));
//                    BigRational den = l.v.dz.multiply(v.dy).divide(v.dx)
//                            .subtract(l.v.dy);
//                    t = num.divide(den).multiply(l.v.dy).add(l.q.x)
//                            .subtract(q.x).divide(v.dx);
//                } else {
//                    BigRational num = l.q.y.subtract(q.y).subtract(l.q.z
//                            .subtract(q.z).multiply(v.dy).divide(v.dz));
//                    BigRational den = l.v.dz.multiply(v.dy).divide(v.dz)
//                            .subtract(l.v.dy);
//                    t = num.divide(den).multiply(l.v.dx).add(l.q.x)
//                            .subtract(q.x).divide(v.dx);
//                }
//            } else if (l.v.dz.isZero()) {
//                BigRational num = l.q.z.subtract(q.z).subtract(l.q.y
//                        .subtract(q.y).multiply(v.dz).divide(v.dy));
//                BigRational den = l.v.dy.multiply(v.dz).divide(v.dy)
//                        .subtract(l.v.dz);
//                t = num.divide(den).multiply(l.v.dx).add(l.q.x)
//                        .subtract(q.x).divide(v.dx);
//            } else {
//                //dx dy dz nonzero
//                BigRational den = l.v.dx.multiply(v.dy).divide(v.dx)
//                        .subtract(l.v.dy);
//                BigRational num = l.q.y.subtract(q.y).subtract(l.q.x
//                        .subtract(q.x).multiply(v.dy).divide(v.dx));
//                t = num.divide(den).multiply(l.v.dx).add(l.q.x)
//                        .subtract(q.x).divide(v.dx);
//            }
//        }
        // Parametric equation of line:
        // x = p.x + t(v.dx)
        // y = p.y + t(v.dy)
        // z = p.z + t(v.dz)
        // Equation of this plane:
        // A*(x-x0) + B*(y-y0) + C*(z-z0) = 0
        // A = n.dx
        // B = n.dy
        // C = n.dz
        // x = l.p.x + t(l.v.dx)
        // y = l.p.y + t(l.v.dy)
        // z = l.p.z + t(l.v.dz)
        //
        // t = (z - l.p.z) / t
        // If A != 0
        // Solve for x
        // A*(x-x0) + B*(y-y0) + C*(z-z0) = 0
        // x = ((By0 - By + Cz0 - Cz) / A) - x0
        // x = ((By0 - By + Cz0 - Cz) / A) - x0
//        BigRational x = (((n.dy.multiply(y0)).subtract(n.dy.multiply(y))
//                .add(n.dz.multiply(z0)).subtract(n.dz.multiply(z)))
//                .divide(n.dx)).subtract(x0);
        BigRational x0 = pl.p.x;
        BigRational y0 = pl.p.y;
        BigRational z0 = pl.p.z;
//        l.p.x  + t(l.v.dx) = (((n.dy.multiply(y0)).subtract(n.dy.multiply(l.p.y + t(l.v.dy)))
//                .add(n.dz.multiply(z0)).subtract(n.dz.multiply(l.p.z + t(l.v.dz))))
//                .divide(n.dx)).subtract(x0);
//        n.dx.multiply(l.p.x.add(x0)) + t.multiply(n.dx.multiply(l.v.dx)) 
//                = (n.dy.multiply(y0)).subtract(n.dy.multiply(l.p.y + t(l.v.dy)))
//                .add(n.dz.multiply(z0)).subtract(n.dz.multiply(l.p.z + t(l.v.dz)));
        BigRational num = (pl.n.dy.multiply(y0)).subtract(pl.n.dy.multiply(l.p.y))
                .add(pl.n.dz.multiply(z0)).subtract(pl.n.dz.multiply(l.p.z))
                .subtract(pl.n.dx.multiply(l.p.x.add(x0)));
        BigRational den = pl.n.dx.multiply(l.v.dx).add(pl.n.dz.multiply(l.v.dz))
                .add(pl.n.dy.multiply(l.v.dy));
        if (den.isZero()) {
            return new V3D_Point(pl.e, pl.e.P0, pl.e.P0, pl.e.P0);            // Not sure if this is right?
        } else {
            BigRational t = num.divide(den);
            BigRational x = l.p.x.add(t.multiply(l.v.dx));
            BigRational y = l.p.y.add(t.multiply(l.v.dy));
            BigRational z = l.p.z.add(t.multiply(l.v.dz));
            return new V3D_Point(pl.e, x, y, z);
        }
    }

    /**
     * @param l line to intersect with this.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry getIntersection(V3D_Line l) {
        return getIntersection(this, l);
    }

    /**
     * @param l line segment to intersect with this.
     * @param flag Used to distinguish from {@link #getIntersection(V3D_Line)}.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry getIntersection(V3D_LineSegment l, boolean flag) {
        V3D_Geometry li = getIntersection(l);
        if (li == null) {
            return null;
        }
        if (li instanceof V3D_Line) {
            return l;
        }
        V3D_Point pt = (V3D_Point) li;
        if (l.isIntersectedBy(pt)) {
            return pt;
        }
        return null;
    }

    /**
     * @param pl The plane to intersect.
     * @return The intersection between {@code this} and {@code pl}
     */
    public V3D_Geometry getIntersection(V3D_Plane pl) {
        // Calculate the cross product of the normal vectors.
        V3D_Vector v = n.getCrossProduct(pl.n);
        if (v.isZeroVector()) {
            // The planes are parallel.
            if (pl.equals(this)) {
                // The planes are the same.
                return this;
            }
            // There is no intersection.
            return null;
        }
        // Calculate the line of intersection, where v is the line vector.
        // What to do depends on which elements of v are non-zero.
        if (v.dx.compareTo(e.P0) == 0) {
            if (v.dy.compareTo(e.P0) == 0) {
                BigRational z = pl.n.dx.multiply(pl.p.x
                        .subtract(pl.q.x)).add(n.dy.multiply(pl.p.y
                        .subtract(pl.q.x))).divide(v.dz).add(pl.p.z);
                V3D_Point pt;
                if (n.dx.compareTo(e.P0) == 0) {
                    pt = new V3D_Point(e, pl.p.x, p.y, z);
                } else {
                    pt = new V3D_Point(e, p.x, pl.p.y, z);
                }
                return new V3D_Line(pt, pt.apply(v));
                // The intersection is at z=?

                /**
                 * n.dx(x(t)−p.x)+n.dy(y(t)−p.y)+n.dz(z(t)−p.z)
                 */
                // where:
                // n.dx = a; n.dy = b; n.dz = c
                // pl.n.dx = d; pl.n.dy = e; pl.n.dz = f
                // a(x−p.x) + b(y−p.y) + c(z−p.z) = 0
                // x = p.x + ((- b(y−p.y) - c(z−p.z)) / a)                     --- 1
                // y = p.y + ((- a(x−p.x) - c(z−p.z)) / b)                     --- 2
                // z = p.z + ((- a(x−p.x) - b(y−p.y)) / c)                     --- 3
                // x = pl.p.x + ((- pl.b(y − pl.p.y) - pl.c(z − pl.p.z)) / d)  --- 4
                // y = pl.p.y + ((- pl.a(x − pl.p.x) - pl.c(z − pl.p.z)) / e)  --- 5
                // z = pl.p.z + ((- pl.a(x − pl.p.x) - pl.b(y − pl.p.y)) / f)  --- 6
                // Let:
                // p.x = k; p.y = l; p.z = m
                // x = k + ((b(l - y) - c(z − m)) / a)   --- 1t
                // y = l + ((a(k - x) - c(z − l)) / b)   --- 2t
                // z = m + ((a(k - x) - b(y − m)) / c)   --- 3t
                // Let:
                // pl.p.x = k; pl.p.y = l; pl.p.z = m
                // x = k + ((e(l - y) - f(z - m)) / d)   --- 1p
                // y = l + ((d(k - x) - f(z - l)) / e)   --- 2p
                // z = m + ((d(k - x) - e(y - m)) / f)   --- 3p
//                if (n.dx.compareTo(e.P0) == 0) {
//                    //pl.b
//                } else if (n.dy.compareTo(e.P0) == 0) {
////                    // y = l + ((- a(x − k) - c(z − l)) / b)
//                    BigDecimal y = p.y;
////                    // x = k + ((e(l - y) - f(z - m)) / d)
////                    // z = m + ((- d(x - k) - e(y - m)) / f)
////                    BigDecimal x = p.x.apply(
////                            Math_BigDecimal.divideRoundIfNecessary(
////                                    (pl.n.dy.multiply(p.y.subtract(y))).subtract(pl.n.dz.multiply(z.subtract(pl.p.z))),
////                                    pl.n.dx, scale, rm));
//                } else {
//                    return e.zAxis;
//                }
//                //BigDecimal x = p.x
//                BigDecimal numerator = p.z.subtract(p.y)
//                        .subtract(n.dz.multiply(p.z))
//                        .subtract(p.y.multiply(n.dy));
//                BigDecimal denominator = n.dy.subtract(n.dz);
//                if (denominator.compareTo(e.P0) == 0) {
//                    // Case 1: The z axis
//                    return null;
//                } else {
//                    // y = (p.y - c(z−p.z)) / b   --- 1          
//                    // z = (p.z - b(y−p.y)) / c   --- 2
//                    // n.dx = a; n.dy = b; n.dz = c
//                    // Let:
//                    // p.x = k; p.y = l; p.z = m
//                    // y = (l - c(z - m)) / b
//                    // z = (m - b(y - l)) / b
//                    // z = (m - b(((l - c(z - m)) / b) - l)) / b
//                    // bz = m - b(((l - c(z - m)) / b) - l)
//                    // bz = m - (l - c(z - m)) - lb
//                    // bz = m - l + cz - cm - lb
//                    // bz - cz = m - l -cm - lb
//                    // z(b - c) = m - l -cm - lb
//                    // z = (m - l -cm - lb) / (b - c)
//                    BigDecimal z = Math_BigDecimal.divideRoundIfNecessary(
//                            numerator, denominator, scale + 2, rm); // scale + 2 sensible?
//                    // Substitute into 1
//                    // y = (p.y - c(z−p.z)) / b   --- 1
//                    if (n.dy.compareTo(e.P0) == 0) {
//                        // Another case to deal with
//                        return null;
//                    } else {
//                        BigDecimal y = Math_BigDecimal.divideRoundIfNecessary(
//                                p.y.subtract(n.dz.multiply(z.subtract(p.z))),
//                                n.dy, scale, rm);
//                        return new V3D_Line(new V3D_Point(e, p.x, y, z),
//                                new V3D_Point(e, p.x.multiply(e.N1), y.add(v.dy), z.add(v.dz)));
//                    }
//                }
            } else {
                if (v.dz.compareTo(e.P0) == 0) {
                    BigRational y = n.dx.multiply(p.x.subtract(q.x)).add(
                            n.dz.multiply(p.z.subtract(q.x)))
                            .divide(v.dy).add(p.y);
                    V3D_Point pt;
                    if (n.dx.compareTo(e.P0) == 0) {
                        pt = new V3D_Point(e, pl.p.x, y, p.z);
                    } else {
                        pt = new V3D_Point(e, p.x, y, pl.p.z);
                    }
                    return new V3D_Line(pt, pt.apply(v));
                } else {
                    // Case 3
                    // y = (p.y - c(z−p.z)) / b   --- 1          
                    // z = (p.z - b(y−p.y)) / c   --- 2
                    // n.dx = a; n.dy = b; n.dz = c
                    // Let:
                    // p.x = k; p.y = l; p.z = m
                    // y = (l - c(z - m)) / b
                    // z = (m - b(y - l)) / b
                    // z = (m - b(((l - c(z - m)) / b) - l)) / b
                    // bz = m - b(((l - c(z - m)) / b) - l)
                    // bz = m - (l - c(z - m)) - lb
                    // bz = m - l + cz - cm - lb
                    // bz - cz = m - l -cm - lb
                    // z(b - c) = m - l -cm - lb
                    // z = (m - l -cm - lb) / (b - c)
                    BigRational numerator = p.z.subtract(p.y)
                            .subtract(n.dz.multiply(p.z))
                            .subtract(p.y.multiply(n.dy));
                    BigRational denominator = n.dy.subtract(n.dz);
                    if (denominator.compareTo(e.P0) == 0) {
                        // Another case to deal with
                        return null;
                    } else {
                        BigRational z = numerator.divide(denominator);
                        // Substitute into 1
                        // y = (p.y - c(z−p.z)) / b   --- 1
                        if (n.dy.compareTo(e.P0) == 0) {
                            // Another case to deal with
                            return null;
                        } else {
                            BigRational y = p.y.subtract(n.dz.multiply(z.subtract(p.z)))
                                    .divide(n.dy);
                            return new V3D_Line(new V3D_Point(e, e.P0, y, z),
                                    new V3D_Point(e, e.P0, y.add(v.dy), z.add(v.dz)));
                        }
                    }
                }
            }
        } else {
            if (v.dy.compareTo(e.P0) == 0) {
                if (v.dz.compareTo(e.P0) == 0) {
                    BigRational x = pl.n.dy.multiply(pl.p.y.subtract(pl.q.y)).add(
                            n.dz.multiply(pl.p.z.subtract(pl.q.y)))
                            .divide(v.dx).add(pl.p.x);
                    V3D_Point pt;
                    if (n.dy.compareTo(e.P0) == 0) {
                        if (n.dz.compareTo(e.P0) == 0) {
                            pt = new V3D_Point(e, x, p.y, pl.p.z);
                        } else {
                            pt = new V3D_Point(e, x, pl.p.y, p.z);
                        }
                    } else {
                        if (n.dz.compareTo(e.P0) == 0) {
                            pt = new V3D_Point(e, x, p.y, pl.p.z);
                        } else {
                            pt = new V3D_Point(e, x, p.y, pl.p.z);
                        }
                    }
                    return new V3D_Line(pt, pt.apply(v));
                } else {
                    BigRational z = pl.n.dx.multiply(pl.p.x.subtract(pl.q.x)).add(
                            n.dy.multiply(pl.p.y.subtract(pl.q.x)))
                            .divide(v.dz).add(pl.p.z);
                    V3D_Point pt;
                    if (n.dx.compareTo(e.P0) == 0) {
                        pt = new V3D_Point(e, pl.p.x, p.y, z);
                    } else {
                        pt = new V3D_Point(e, p.x, pl.p.y, z);
                    }
                    return new V3D_Line(pt, pt.apply(v));
                }
            } else {
                if (v.dz.compareTo(e.P0) == 0) {
                    // Case 6
                    BigRational y = n.dx.multiply(p.x.subtract(q.x)).add(
                            n.dz.multiply(p.z.subtract(q.x)))
                            .divide(v.dy).add(p.y);
                    V3D_Point pt;
                    if (p.x.compareTo(e.P0) == 0) {
                        pt = new V3D_Point(e, p.x, y, pl.p.z); // x=1 z=0
                    } else {
                        pt = new V3D_Point(e, pl.p.x, y, p.z);
                    }
                    return new V3D_Line(pt, pt.apply(v));
                } else {
                    /**
                     * Case 7: Neither plane aligns with any axis and they are
                     * not orthogonal.
                     *
                     * n.dx(x(t)−p.x)+n.dy(y(t)−p.y)+n.dz(z(t)−p.z)
                     */
                    // where:
                    // n.dx = a; n.dy = b; n.dz = c
                    // pl.n.dx = d; pl.n.dy = e; pl.n.dz = f
                    // a(x−p.x)+b(y−p.y)+c(z−p.z) = 0
                    // x = (ap.x-by+bp.y-cz+cp.z)/a
                    // x = p.x+((b(p.y−y)+c(p.z−z))/a)                    --- 1
                    // y = p.y+((a(p.x−x)+c(p.z−z))/b)                    --- 2
                    // z = p.z+((a(p.x−x)+b(p.y−y))/c)                    --- 3
                    // x = pl.p.x+((pl.b(pl.p.y−y)+pl.c(pl.p.z−z))/pl.a)  --- 4
                    // y = pl.p.y+((pl.a(pl.p.x−x)+pl.c(pl.p.z−z))/pl.b)  --- 5
                    // z = pl.p.z+((pl.a(pl.p.x−x)+pl.b(pl.p.y−y))/pl.c)  --- 6
                    // Let:
                    // p.x = g; p.y = h; p.z = i
                    // x = g+((b(h−y)+c(i−z))/a)                    --- 1p
                    // y = h+((a(g−x)+c(i−z))/b)                    --- 2p
                    // z = i+((a(g−x)+b(h−y))/c)                    --- 3p
                    // Let:
                    // pl.p.x = j; pl.p.y = k; pl.p.z = l
                    // x = j+((e(k−y)+f(l−z))/d)                    --- 1pl
                    // y = k+((d(j−x)+f(l−z))/e)                    --- 2pl
                    // z = l+((d(j−x)+e(k−y))/f)                    --- 3pl
                    // Stage 1: express each coordinate in terms of another
                    // sub 1p into 2pl:
                    // y = k+((d(j−(g+((b(h−y)+c(i−z))/a)))+f(l−z))/e)
                    // ey-ek = d(j−(g+((b(h−y)+c(i−z))/a)))+f(l−z)
                    // ey-ek = d(j−(g+((b(h−y)+c(i−z))/a)))+fl−fz
                    // ey-ek = dj−dg-dbh/a+dby/a-dci/a+dcz/a+fl−fz                    
                    // ey-dby/a = dj−dg-dbh/a-dci/a+dcz/a+fl−fz+ek
                    // y = (dj−dg-dbh/a-dci/a+dcz/a+fl−fz+ek)/(e-db/a)  ---12 y ito z
                    // ey-dby/a = dj−dg-dbh/a-dci/a+dcz/a+fl−fz+ek
                    // ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek = z(dc/a-f)
                    // z = (ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek)/(dc/a-f)  ---12 z ito y                    
                    // sub 1p into 3pl
                    // z = l+((d(j−(g+((b(h−y)+c(i−z))/a)))+e(k−y))/f)
                    // fz-fl = d(j−(g+((b(h−y)+c(i−z))/a)))+ek−ey
                    // fz-dcz/a = dj−dg-dbh/a+dby/a-dci/a+ek−ey+fl
                    // z = (dj−dg-dbh/a+dby/a-dci/a+ek−ey+fl)/(f-dc/a)  ---13 z ito y 
                    // fz-dcz/a = dj−dg-dbh/a+dby/a-dci/a+ek−ey+fl
                    // fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl= dby/a−ey
                    // y = (fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e)  ---13 y ito z
                    // sub 2p into 1pl:
                    // x = j+((e(k−(h+((a(g−x)+c(i−z))/b)))+f(l−z))/d)
                    // dx-dj = e(k−(h+((a(g−x)+c(i−z))/b)))+fl−fz
                    // dx-dj = ek−eh-eag/b+eax/b-eci/b+ecz/b+fl−fz
                    // x = (ek−eh-eag/b-eci/b+ecz/b+fl−fz-dj)/(d-ea/b)  ---21 x ito z    
                    // dx-dj = ek−eh-eag/b+eax/b-eci/b+ecz/b+fl−fz
                    // z = (dx-dj-ek+eh+eag/b-eax/b+eci/b-fl)/(ec/b−f)  ---21 z ito x
                    // sub 2p into 3pl
                    // z = l+((d(j−x)+e(k−(h+((a(g−x)+c(i−z))/b))))/f)
                    // fz-fl = dj−dx+ek−eh-eag/b+eax/b-eci/b+ecz/b
                    // z = (dj−dx+ek−eh-eag/b+eax/b-eci/b+fl)/(f-ec/b)  ---23 z ito x
                    // fz-fl = dj−dx+ek−eh-eag/b+eax/b-eci/b+ecz/b
                    // x = (fz-fl-dj-ek+eh+eag/b+eci/b-ecz/b)/(ea/b-d)  ---23 x ito z
                    // sub 3p into 1pl
                    // x = j+((e(k−y)+f(l−(i+((a(g−x)+b(h−y))/c))))/d)
                    // dx-dj = ek−ey+f(l−(i+((a(g−x)+b(h−y))/c)))                    
                    // dx-dj = ek−ey+fl−fi+fag/c−fax/c+fbh/c−fby/c
                    // x = (dj+ek−ey+fl−fi+fag/c+fbh/c−fby/c)/(d+fa/c)
                    // x = (ek−ey+fl-fi-fag/c-fbh/c+fb)/c+dj)/(d-fa/c)  ---31 x ito y
                    // dx-dj = ek−ey+fl−fi+fag/c−fax/c+fbh/c−fby/c                    
                    // y = (ek+fl−fi+fag/c−fax/c+fbh/c-dx+dj)/(e+fb/c)  ---31 y ito x 
                    // sub 3p into 2pl
                    // y = k+((d(j−x)+f(l−(i+((a(g−x)+b(h−y))/c))))/e)
                    // ey-ek = dj−dx+fl−fi-fag/c+fax/c-fbh/c+fby/c
                    // y = (ek+dj−dx+fl−fi-fag/c+fax/c-fbh/c)/(e-fb/c)  ---32 y ito x
                    // ey-ek = dj−dx+fl−fi-fag/c+fax/c-fbh/c+fby/c
                    // x = (ey-ek-dj-fl+fi+fag/c+fbh/c-fby/c)/(fa/c−d)  ---32 x ito y
                    // Stage 2: Are any denominators 0?
                    BigRational x = n.dy.multiply(pl.p.y.subtract(pl.q.y)).add(
                            n.dz.multiply(pl.p.z.subtract(pl.q.y)))
                            .divide(v.dx).add(pl.p.x);
                    BigRational y = n.dx.multiply(p.x.subtract(q.x)).add(
                            n.dz.multiply(p.z.subtract(q.x)))
                            .divide(v.dy).add(p.y);
                    BigRational z = n.dx.multiply(pl.p.x.subtract(pl.q.x)).add(
                            n.dy.multiply(pl.p.y.subtract(pl.q.x)))
                            .divide(v.dz).add(pl.p.z);
                    V3D_Point pt = new V3D_Point(this.e, x, y, z);
                    return new V3D_Line(pt, pt.apply(v));

//                    BigDecimal a = n.dx;
//                    BigDecimal b = n.dy;
//                    BigDecimal c = n.dz;
//                    BigDecimal d = pl.n.dx;
//                    BigDecimal e = pl.n.dy;
//                    BigDecimal f = pl.n.dz;
//                    BigDecimal db = d.multiply(b);
//                    BigDecimal dc = d.multiply(c);
//                    BigDecimal ea = e.multiply(a);
//                    BigDecimal ec = e.multiply(c);
//                    BigDecimal ef = e.multiply(f);
//                    BigDecimal fa = f.multiply(a);
//                    BigDecimal fb = f.multiply(b);
//                    BigDecimal db_div_a = Math_BigDecimal.divideRoundIfNecessary(db, a, scale, rm);
//                    BigDecimal dc_div_a = Math_BigDecimal.divideRoundIfNecessary(dc, a, scale, rm);
//                    BigDecimal ea_div_b = Math_BigDecimal.divideRoundIfNecessary(ea, b, scale, rm);
//                    BigDecimal ec_div_b = Math_BigDecimal.divideRoundIfNecessary(ec, b, scale, rm);
//                    BigDecimal fa_div_c = Math_BigDecimal.divideRoundIfNecessary(fa, c, scale, rm);
//                    BigDecimal fb_div_c = Math_BigDecimal.divideRoundIfNecessary(fb, c, scale, rm);
//                    BigDecimal denom12yitoz = e.subtract(db_div_a);
//                    BigDecimal denom12zitoy = dc_div_a.subtract(f);
//                    BigDecimal denom13zitoy = f.subtract(dc_div_a);
//                    BigDecimal denom13yitoz = db_div_a.subtract(e);
//                    BigDecimal denom21xitoz = d.subtract(ea_div_b);
//                    BigDecimal denom21zitox = ec_div_b.subtract(f);
//                    BigDecimal denom23zitox = f.subtract(ec_div_b);
//                    BigDecimal denom23xitoz = ea_div_b.subtract(d);
//                    BigDecimal denom31xitoy = d.subtract(fa_div_c);
//                    BigDecimal denom31yitox = e.add(fb_div_c);
//                    BigDecimal denom32yitox = e.subtract(fb_div_c);
//                    BigDecimal denom32xitoy = fa_div_c.subtract(d);
//                    // Solve for z
//                    // Let; n = v.dx; o = v.dy; p = v.dz
//                    // x(t) = x + v.dx 
//                    // y(t) = y(0) + v.dy 
//                    // z-p = z(0)
//                    // z = (ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek)/(dc/a-f)  ---12 z ito y                    
//                    // z-p = (ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek)/(dc/a-f)
//                    // y = (fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e)  ---13 y ito z
//                    // z-p = (e((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))-db((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))/a-dj-dg+dbh/a+dci/a-fl-ek)/(dc/a-f)
//                    // (z-p)(dc/a-f) = e((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))-db((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))/a-dj-dg+dbh/a+dci/a-fl-ek
//                    // (efz-edcz/a-edj+edg+edbh/a+edci/a-eek-efl)/(db/a-e) = -dbfz/(db-ae)+dbdcz/(adb-aae)+ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek
//                    // efz/(db/a-e)-edcz/(db-ae)-edj/(db/a-e)+edg/(db/a-e)+edbh/(db-ae)+edci/(db-ae)-eek/(db/a-e)-efl/db/a-e) = -dbfz/(db-ae)+dbdcz/(adb-aae)+ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek
//                    // efz/(db/a-e)-edcz/(db-ae)+dbfz/(db-ae)-dbdcz/(adb-aae) = ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek+edj/(db/a-e)-edg/(db/a-e)-edbh/(db-ae)-edci/(db-ae)+eek/(db/a-e)+efl/db/a-e)
//                    // z(ef/(db/a-e)-edc/(db-ae)+dbf/(db-ae)-dbdc/(adb-aae)) = ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek+edj/(db/a-e)-edg/(db/a-e)-edbh/(db-ae)-edci/(db-ae)+eek/(db/a-e)+efl/db/a-e)
//                    // z = num/den
//                    // den = ef/(db/a-e)-edc/(db-ae)+dbf/(db-ae)-dbdc/(adb-aae);
//                    // num = ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek+edj/(db/a-e)-edg/(db/a-e)-edbh/(db-ae)-edci/(db-ae)+eek/(db/a-e)+efl/db/a-e)
//                    // Let: q = db-ae; r = db/a-e; s=adb-aae
//                    BigDecimal q = db.subtract(a.multiply(e));
//                    BigDecimal r = db_div_a.subtract(e);
//                    BigDecimal s = db.multiply(a).subtract(a.multiply(ea));
//                    BigDecimal den = Math_BigDecimal.divideRoundIfNecessary(ef, r, scale, rm)
//                            .subtract(Math_BigDecimal.divideRoundIfNecessary(e.multiply(dc), q, scale, rm))
//                            .add(Math_BigDecimal.divideRoundIfNecessary(db.multiply(f), q, scale, rm))
//                            .subtract(Math_BigDecimal.divideRoundIfNecessary(d.multiply(db.multiply(c)), s, scale, rm));
//                    BigDecimal g = p.x;
//                    BigDecimal h = p.y;
//                    BigDecimal i = p.z;
//                    BigDecimal j = pl.p.x;
//                    BigDecimal k = pl.p.y;
//                    BigDecimal l = pl.p.z;
//                    BigDecimal ek = e.multiply(k);
//                    BigDecimal ci = c.multiply(i);
//                    BigDecimal dg = d.multiply(g);
//                    BigDecimal dbh = db.multiply(h);
//                    BigDecimal dbh_sub_dci = dbh.subtract(d.multiply(ci));
//                    BigDecimal fl = f.multiply(l);
//                    BigDecimal bh = b.multiply(h);
//                    BigDecimal dj = d.multiply(j);
//                    BigDecimal num = d.multiply(j.subtract(dg).add(ek).add(fl))
//                            .subtract(Math_BigDecimal.divideRoundIfNecessary(e.multiply(dbh_sub_dci), q, scale, rm))
//                            .subtract(Math_BigDecimal.divideRoundIfNecessary(d.multiply(dbh_sub_dci), s, scale, rm))
//                            .subtract(dj.subtract(dg))
//                            .add(Math_BigDecimal.divideRoundIfNecessary(d.multiply(bh.add(ci)), a, scale, rm))
//                            .subtract(fl.subtract(ek))
//                            .add(Math_BigDecimal.divideRoundIfNecessary(e.multiply(dj.subtract(dg).add(ek).add(fl)), r, scale, rm));
//                    BigDecimal z = Math_BigDecimal.divideRoundIfNecessary(num, den, scale, rm);
//                    // y = (dj−dg-dbh/a-dci/a+dcz/a+fl−fz+ek)/(e-db/a)  ---12 y ito z
//                    // y = num/den
//                    num = dj.subtract(dg).subtract(db_div_a.multiply(h))
//                            .subtract(dc_div_a.multiply(i))
//                            .add(dc_div_a.multiply(z)).add(fl).subtract(f.multiply(z)).add(ek);
//                    den = e.subtract(db_div_a);
//                    BigDecimal y = Math_BigDecimal.divideRoundIfNecessary(num, den, scale, rm);
//                    // x = (ek−eh-eag/b-eci/b+ecz/b+fl−fz-dj)/(d-ea/b)  ---21 x ito z
//                    BigDecimal e_div_b = Math_BigDecimal.divideRoundIfNecessary(e, b, scale, rm);
//                    num = ek.subtract(e.multiply(h))
//                            .subtract(g.multiply(a).multiply(e_div_b))
//                            .subtract(ci.multiply(e_div_b))
//                            .add(dc_div_a.multiply(z))
//                            .add(c.multiply(z).multiply(e_div_b))
//                            .add(fl)
//                            .subtract(f.multiply(z))
//                            .subtract(dj);
//                    den = d.subtract(a.multiply(e_div_b));
//                    BigDecimal x = Math_BigDecimal.divideRoundIfNecessary(num, den, scale, rm);
//                    V3D_Point aPoint = new V3D_Point(this.e, x, y, z);
//                    return new V3D_Line(aPoint, aPoint.apply(v));
                }
            }
        }
        // This is where the two equations of the plane are equal.
        // n.dx(x(t)−p.x)+n.dy(y(t)−p.y)+n.dz(z(t)−p.z)
        // where:
        // n.dx = a; n.dy = b; n.dz = c
        // a(x−p.x) + b(y−p.y) + c(z−p.z) = 0
        // x = (p.x - b(y−p.y) - c(z−p.z)) / a                     --- 1
        // y = (p.y - a(x−p.x) - c(z−p.z)) / b                     --- 2
        // z = (p.z - a(x−p.x) - b(y−p.y)) / c                     --- 3
        // x = (pl.p.x - pl.b(y−pl.p.y) - pl.c(z−pl.p.z)) / pl.a   --- 4
        // y = (pl.p.y - pl.a(x−pl.p.x) - pl.c(z−pl.p.z)) / pl.b   --- 5
        // z = (pl.p.z - pl.a(x−pl.p.x) - pl.b(y−pl.p.y)) / pl.c   --- 6
        // Sub 2 and 3 into
    }

    /**
     * @param p The plane to test if it is parallel to this.
     * @return {@code true} if {@code this} is parallel to {@code p}.
     */
    public boolean isParallel(V3D_Plane p) {
        return p.n.isParallel(n);
    }

    /**
     * @param l The line to test if it is parallel to this.
     * @return {@code true} if {
     * @coe this} is parallel to {@code l}.
     */
    public boolean isParallel(V3D_Line l) {
        V3D_Vector cp = this.n.getCrossProduct(l.v);
        return !(cp.dx.isZero() && cp.dy.isZero() && cp.dz.isZero());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Plane) {
            V3D_Plane pl = (V3D_Plane) o;
            if (isIntersectedBy(pl.p)) {
                if (isIntersectedBy(pl.q)) {
                    if (isIntersectedBy(pl.r)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.p);
        hash = 47 * hash + Objects.hashCode(this.q);
        hash = 47 * hash + Objects.hashCode(this.r);
        return hash;
    }
}
