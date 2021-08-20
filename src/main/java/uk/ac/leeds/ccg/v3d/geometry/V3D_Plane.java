/*
 * Copyright 2020 CCG, University of Leeds.
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
import java.math.MathContext;
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.geometrics.V3D_Geometrics;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;

/**
 * 3D representation of an infinite plane. The plane is defined by three points
 * {@link #p}, {@link #q} and {@link #r} that are not collinear, a normal vector
 * {@link #n} that is perpendicular to the plane, and two vectors; {@link #pq}
 * (which is the vector from {@link #p} to {@link #q}), and {@link #qr} (which
 * is the vector from {@link #q} to {@link #r}). The "*" denotes a point in 3D,
 * {@link #pq} is depicted with a line of "e" symbols, {@link #qr} is depicted
 * with a line of "f" symbols in the following depiction: {@code
 *                                       z
 *                          y           +
 *                          +          /                * p=<x0,y0,z0>
 *                          |         /                e
 *                          |        /                e
 *                          |    z0-/                e
 *                          |      /                e
 *                          |     /               e
 *                          |    /               e
 *                          |   /               e
 *                       y0-|  /               e
 *                          | /               e
 *             x2           |/         x1    e
 *  - ---------/------------|-----------/---e---/---- + x
 *                         /|              e   x0
 *                        / |-y1          e
 *                    z2-/  |           e
 *                      /   |          e
 *                  z1-/    |         e
 *                    /  y2-|        e
 *                   /      | f f f * q=<x1,y1,z1>
 *                  f f f f f
 *          * f f f/        |
 *  r=<x2,y2,z2>  -         |
 *                          -
 * }
 *
 *
 * The equation of the plane is:
 * <ul>
 * <li>{@code A*(x-x0) + B*(y-y0) + C*(z-z0) = 0}</li>
 * <li>{@code A*(x) + B*(y) + C*(z) - D = 0 where D = -(A*x0 + B*y0 + C*z0)}</li>
 * </ul>
 * where:
 * <ul>
 * <li>{@code x}, {@code y}, and {@code z} are the coordinates from either
 * {@link #p}, {@link #q} or {@link #r}</li>
 * <li>{@code x0}, {@code y0} and {@code z0} represents any other point on the
 * plane</li>
 * <li>{@code A} is the {@code dx} of {@link #n}.</li>
 * <li>{@code B} is the {@code dy} of {@link #n}.</li>
 * <li>{@code C} is the {@code dz} of {@link #n}.</li>
 * </ul>
 *
 * <ol>
 * <li>{@code n.dx(x(t)−p.x)+n.dy(y(t)−p.y)+n.dz(z(t)−p.z) = 0}</li>
 * <li>{@code n.dx(x(t)−q.x)+n.dy(y(t)−q.y)+n.dz(z(t)−q.z) = 0}</li>
 * <li>{@code n.dx(x(t)−r.x)+n.dy(y(t)−r.y)+n.dz(z(t)−r.z) = 0}</li>
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
    protected final V3D_Point p;

    /**
     * One of the points that defines the plane.
     */
    protected final V3D_Point q;

    /**
     * One of the points that defines the plane.
     */
    protected final V3D_Point r;

    /**
     * The vector representing the move from {@link #p} to {@link #q}.
     */
    protected final V3D_Vector pq;

    /**
     * The vector representing the move from {@link #q} to {@link #r}.
     */
    protected final V3D_Vector qr;

    /**
     * The normal vector. (This is perpendicular to the plane and it's direction
     * is given by order in which the two vectors {@link #pq} and {@link #qr}
     * are used in a cross product calculation when the plane is constructed.
     */
    protected final V3D_Vector n;

    /**
     * Create a new instance. Collinearity is not checked.
     *
     * @param p The plane used to create this.
     */
    public V3D_Plane(V3D_Plane p) {
        this(p.p, p.q, p.r, p.pq, p.qr, p.n, false);
    }

    /**
     * Create a new instance.
     *
     * @param p The plane used to create this.
     * @param checkCollinearity If {@code false} the there is no check for
     * co-linearity.
     * @throws RuntimeException If p, q and r are collinear and this is checked
     * for.
     */
    public V3D_Plane(V3D_Plane p, boolean checkCollinearity) {
        this(p.p, p.q, p.r, p.pq, p.qr, p.n, checkCollinearity);
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     * @param pq What {@link #pq} is set to.
     * @param qr What {@link #qr} is set to.
     * @param n What {@link #n} is set to.
     * @param checkCollinearity If {@code false} the there is no check for
     * collinearity.
     * @throws RuntimeException If p, q and r are collinear and this is checked
     * for.
     */
    public V3D_Plane(V3D_Point p, V3D_Point q, V3D_Point r, V3D_Vector pq,
            V3D_Vector qr, V3D_Vector n, boolean checkCollinearity) {
        this.p = p;
        this.q = q;
        this.r = r;
        this.pq = pq;
        this.qr = qr;
        this.n = n;
        if (checkCollinearity) {
            // Check for collinearity
            BigRational P0 = BigRational.ZERO;
            if (n.dx.compareTo(P0) == 0
                    && n.dy.compareTo(P0) == 0
                    && n.dz.compareTo(P0) == 0) {
                throw new RuntimeException("The three points do not define a plane, "
                        + "but are collinear (they might all be the same point!");
            }
        }
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     * @param checkCollinearity If {@code false} the there is no check for
     * collinearity.
     * @throws RuntimeException If p, q and r are collinear and this is checked
     * for.
     */
    public V3D_Plane(V3D_Point p, V3D_Point q, V3D_Point r,
            boolean checkCollinearity) {
        this(p, q, r, new V3D_Vector(p, q), new V3D_Vector(q, r),
                new V3D_Vector(p, q).getCrossProduct(new V3D_Vector(q, r)),
                checkCollinearity);
    }

    /**
     * Create a new instance. This assumes that p, q and r are not collinear.
     *
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     */
    public V3D_Plane(V3D_Point p, V3D_Point q, V3D_Point r) {
        this(p, q, r, false);
    }

    /**
     * An equation of the plane containing the point {@code p} with normal
     * vector {@code n} is {@code n.dx(x) + n.dy(y) + n.dz(z) = d}.
     *
     * @param p What {@link #p} is set to.
     * @param n What {@link #n} is set to.
     */
    public V3D_Plane(V3D_Point p, V3D_Vector n) {
        this.p = p;
        BigRational d = n.dx.multiply(p.x).add(n.dy.multiply(p.y).add(n.dz.multiply(p.z)));
        BigRational P0 = BigRational.ZERO;
        if (n.dx.compareTo(P0) == 0) {
            // Set: x = 0; y = 0
            // Then: z = d/n.dz
            this.q = new V3D_Point(P0, P0, d.divide(n.dz));
            // Set: x = 0; z = 0
            // Then: y = d/n.dy
            this.r = new V3D_Point(P0, d.divide(n.dy), P0);
        } else {
            if (n.dy.compareTo(P0) == 0) {
                // Set: x = 0; y = 0
                // Then: z = d/n.dz
                this.q = new V3D_Point(P0, P0, d.divide(n.dz));
                // Set: y = 0; z = 0
                // Then: x = d/n.dx
                this.r = new V3D_Point(P0, d.divide(n.dx), P0);
            } else {
                // Set: x = 0; y = 0
                // Then: z = d/n.dz
                this.q = new V3D_Point(P0, P0, d.divide(n.dz));
                // Set: x = 0; z = 0
                // Then: y = d/n.dy
                this.r = new V3D_Point(P0, d.divide(n.dy), P0);
            }
        }
        pq = new V3D_Vector(p, q);
        qr = new V3D_Vector(q, r);
        this.n = pq.getCrossProduct(qr);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(p=" + p.toString()
                + ", q=" + q.toString() + ", r=" + r.toString() + ")";
    }

    /**
     * @param v The vector to apply.
     * @return a new plane.
     */
    @Override
    public V3D_Plane apply(V3D_Vector v) {
        return new V3D_Plane(p.apply(v), q.apply(v), r.apply(v));
    }

    /**
     * @param pl The plane to test for intersection with this.
     * @return {@code true} If this and {@code pl} intersect.
     */
    public boolean isIntersectedBy(V3D_Plane pl) {
        if (isParallel(pl)) {
            return equals(pl);
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
        return d.compareTo(BigRational.ZERO) == 0;
    }

    /**
     * @param l The line to test if it is on the plane.
     * @return {@code true} If {@code pt} is on the plane.
     */
    public boolean isOnPlane(V3D_Line l) {
        return isIntersectedBy(l.p) && isIntersectedBy(l.q);
    }

    /**
     * https://en.wikipedia.org/wiki/Line%E2%80%93plane_intersection
     *
     * @param pl The plane to intersect with the line.
     * @param l The line to intersect with the plane.
     * @return The intersection of the line and the plane. This is either
     * {@code null} a line or a point.
     */
    public static V3D_Geometry getIntersection(V3D_Plane pl, V3D_Line l) {
        if (pl.isParallel(l)) {
            if (pl.isOnPlane(l)) {
                return l;
            } else {
                return null;
            }
        }
        // Are either of the points of l on the plane.
        if (pl.isIntersectedBy(l.p)) {
            return l.p;
        }
        if (pl.isIntersectedBy(l.q)) {
            return l.q;
        }
        BigRational num = new V3D_Vector(pl.p, l.p).getDotProduct(pl.n);
        BigRational den = l.v.getDotProduct(pl.n);
        BigRational t = num.divide(den);
        return new V3D_Point(l.p.x.subtract(l.v.dx.multiply(t)),
                l.p.y.subtract(l.v.dy.multiply(t)),
                l.p.z.subtract(l.v.dz.multiply(t)));
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
        BigRational P0 = BigRational.ZERO;
        if (v.dx.compareTo(P0) == 0) {
            if (v.dy.compareTo(P0) == 0) {
                BigRational z = pl.n.dx.multiply(pl.p.x
                        .subtract(pl.q.x)).add(n.dy.multiply(pl.p.y
                        .subtract(pl.q.x))).divide(v.dz).add(pl.p.z);
                V3D_Point pt;
                if (n.dx.compareTo(P0) == 0) {
                    pt = new V3D_Point(pl.p.x, p.y, z);
                } else {
                    pt = new V3D_Point(p.x, pl.p.y, z);
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
                if (v.dz.compareTo(P0) == 0) {
                    BigRational y = n.dx.multiply(p.x.subtract(q.x)).add(
                            n.dz.multiply(p.z.subtract(q.x)))
                            .divide(v.dy).add(p.y);
                    V3D_Point pt;
                    if (n.dx.compareTo(P0) == 0) {
                        pt = new V3D_Point(pl.p.x, y, p.z);
                    } else {
                        pt = new V3D_Point(p.x, y, pl.p.z);
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
                    if (denominator.compareTo(P0) == 0) {
                        // Another case to deal with
                        return null;
                    } else {
                        BigRational z = numerator.divide(denominator);
                        // Substitute into 1
                        // y = (p.y - c(z−p.z)) / b   --- 1
                        if (n.dy.compareTo(P0) == 0) {
                            // Another case to deal with
                            return null;
                        } else {
                            BigRational y = p.y.subtract(n.dz.multiply(z.subtract(p.z)))
                                    .divide(n.dy);
                            return new V3D_Line(new V3D_Point(P0, y, z),
                                    new V3D_Point(P0, y.add(v.dy), z.add(v.dz)));
                        }
                    }
                }
            }
        } else {
            if (v.dy.compareTo(P0) == 0) {
                if (v.dz.compareTo(P0) == 0) {
                    BigRational x = pl.n.dy.multiply(pl.p.y.subtract(pl.q.y)).add(
                            n.dz.multiply(pl.p.z.subtract(pl.q.y)))
                            .divide(v.dx).add(pl.p.x);
                    V3D_Point pt;
                    if (n.dy.compareTo(P0) == 0) {
                        if (n.dz.compareTo(P0) == 0) {
                            pt = new V3D_Point(x, p.y, pl.p.z);
                        } else {
                            pt = new V3D_Point(x, pl.p.y, p.z);
                        }
                    } else {
                        if (n.dz.compareTo(P0) == 0) {
                            pt = new V3D_Point(x, p.y, pl.p.z);
                        } else {
                            pt = new V3D_Point(x, p.y, pl.p.z);
                        }
                    }
                    return new V3D_Line(pt, pt.apply(v));
                } else {
                    BigRational z = pl.n.dx.multiply(pl.p.x.subtract(pl.q.x)).add(
                            n.dy.multiply(pl.p.y.subtract(pl.q.x)))
                            .divide(v.dz).add(pl.p.z);
                    V3D_Point pt;
                    if (n.dx.compareTo(P0) == 0) {
                        pt = new V3D_Point(pl.p.x, p.y, z);
                    } else {
                        pt = new V3D_Point(p.x, pl.p.y, z);
                    }
                    return new V3D_Line(pt, pt.apply(v));
                }
            } else {
                if (v.dz.compareTo(P0) == 0) {
                    // Case 6
                    BigRational y = n.dx.multiply(p.x.subtract(q.x)).add(
                            n.dz.multiply(p.z.subtract(q.x)))
                            .divide(v.dy).add(p.y);
                    V3D_Point pt;
                    if (p.x.compareTo(P0) == 0) {
                        pt = new V3D_Point(p.x, y, pl.p.z); // x=1 z=0
                    } else {
                        pt = new V3D_Point(pl.p.x, y, p.z);
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
                    V3D_Point pt = new V3D_Point(x, y, z);
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
        //return p.n.isScalarMultiple(n); // alternative - probably slower?
        return n.getCrossProduct(p.n).isZeroVector();
    }

    /**
     * @param l The line to test if it is parallel to this.
     * @return {@code true} if {@code this} is parallel to {@code l}.
     */
    public boolean isParallel(V3D_Line l) {
        return n.getDotProduct(l.v).isZero();
    }

    @Override
    public boolean equals(V3D_Geometry g) {
        if (g instanceof V3D_Plane) {
            return equals((V3D_Plane) g);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Plane) {
            return equals((V3D_Plane) o);
        }
        return false;
    }

    /**
     * Planes are equal if they are coincident and their normal perpendicular
     * vectors point in the same direction. They may be coincident and be
     * defined by the same three points, but may not be equal. They may be equal
     * even if they are defined by different points.
     *
     * @param pl The plane to check for equality with {@code this}.
     * @return {@code true} iff {@code this} and {@code pl} are the same.
     */
    public boolean equals(V3D_Plane pl) {
        if (n.equals(pl.n)) {
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

    /**
     * Planes are equal if they are coincident and the {@link #n} is the same.
     *
     * @param pl The plane to check for equality with {@code this}.
     * @return {@code true} iff {@code this} and {@code pl} are the same.
     */
    public boolean isCoincident(V3D_Plane pl) {
        if (isIntersectedBy(pl.p)) {
            if (isIntersectedBy(pl.q)) {
                if (isIntersectedBy(pl.r)) {
                    return true;
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

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l) {
        return true;
    }

    /**
     * @return The points that define the plan as a matrix.
     */
    public Math_Matrix_BR getAsMatrix() {
        Math_Matrix_BR res = new Math_Matrix_BR(3, 3);
        BigRational[][] m = res.getM();
        m[0][0] = p.x;
        m[0][1] = p.y;
        m[0][2] = p.z;
        m[1][0] = q.x;
        m[1][1] = q.y;
        m[1][2] = q.z;
        m[2][0] = r.x;
        m[2][1] = r.y;
        m[2][2] = r.z;
        return res;
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param p A point.
     * @param oom The order of magnitude of the precision.
     * @return The distance from {@code this} to {@code p}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        if (this.isIntersectedBy(p)) {
            return BigDecimal.ZERO;
        }
        V3D_Vector v = new V3D_Vector(p, this.p);
        V3D_Vector u = this.n.getUnitVector(oom);
//        MathContext mc = new MathContext(Math_BigRationalSqrt
//                .getOOM(BigRational.ONE, oom));
        MathContext mc = new MathContext(6 - oom);
        return v.getDotProduct(u).abs().toBigDecimal(mc);
    }

    /**
     * Using {@link #p} and {@link #n} define a line and find the point of
     * intersection on {@code p} and then return the distance squared between it
     * and {@link #p}.
     *
     * @param p The other plane used to calculate the distance.
     * @return The shortest distance between {@code this} and {@code p}. Choose
     * {@link #p}
     */
    public BigRational getDistanceSquared(V3D_Plane p) {
        if (isParallel(p)) {
            return this.p.getDistanceSquared((V3D_Point) p.getIntersection(
                    new V3D_Line(this.p, n)));
        }
        return BigRational.ZERO;
    }
}
