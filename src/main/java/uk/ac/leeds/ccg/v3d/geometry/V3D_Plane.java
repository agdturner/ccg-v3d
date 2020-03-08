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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_Plane - for representing infinite flat 2D planes in 3D. The plane is
 * defined by three points {@link #p}, {@link #q} and {@link #r} that are not
 * collinear, or from any point on the plane and a normal vector that is
 * perpendicular to the plane. From three points that are not collinear an
 * equation of the plane can be derived by creating two V3D_Vectors {@link #pq}
 * and {@link #pr}. The equation of the plane is:
 * <ul>
 * <li>A*(x-x0) + B*(y-y0) + C*(z-z0) = 0</li>
 * <li>A*(x) + B*(y) + C*(z) - D = 0      where D = -(A*x0 + B*y0 + C*z0)</li>
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
 * <li>normalVector.dx(x(t)−p.x)+normalVector.dy(y(t)−p.y)+normalVector.dz(z(t)−p.z)
 * = 0</li>
 * <li>normalVector.dx(x(t)−q.x)+normalVector.dy(y(t)−q.y)+normalVector.dz(z(t)−q.z)
 * = 0</li>
 * <li>normalVector.dx(x(t)−r.x)+normalVector.dy(y(t)−r.y)+normalVector.dz(z(t)−r.z)
 * = 0</li>
 * </ol>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Plane extends V3D_Geometry {

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
    public V3D_Vector normalVector;

    /**
     * @param e V3D_Environment
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     * @throws RuntimeException If p, q and r are collinear.
     */
    public V3D_Plane(V3D_Environment e, V3D_Point p, V3D_Point q, V3D_Point r) {
        super(e);
        //                         i                 j                   k
        pq = new V3D_Vector(e, p.x.subtract(q.x), p.y.subtract(q.y), p.z.subtract(q.z));
        pr = new V3D_Vector(e, p.x.subtract(r.x), p.y.subtract(r.y), p.z.subtract(r.z));
        /**
         * Calculate the normal perpendicular vector.
         */
        normalVector = new V3D_Vector(e, 
                pq.dy.multiply(pr.dz).subtract(pr.dy.multiply(pq.dz)),
                pq.dx.multiply(pr.dz).subtract(pr.dx.multiply(pq.dz)).negate(),
                pq.dx.multiply(pr.dy).subtract(pr.dx.multiply(pq.dy)));
        this.p = p;
        this.q = q;
        this.r = r;
        // Check for collinearity
        if (normalVector.dx.compareTo(BigDecimal.ZERO) == 0
                && normalVector.dy.compareTo(BigDecimal.ZERO) == 0
                && normalVector.dz.compareTo(BigDecimal.ZERO) == 0) {
            throw new RuntimeException("The three points do not define a plane, "
                    + "but are collinear (they might all be the same point!");
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(p=" + p.toString()
                + ", q=" + q.toString() + ", r=" + r.toString() + ")";
    }

    /**
     * @return {@link #normalVector}
     */
    public V3D_Vector getNormalVector() {
        return normalVector;
    }

    /**
     * @param pl The plane to test for intersection with this.
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} If this and {@code pl} intersect.
     */
    public boolean isIntersectedBy(V3D_Plane pl, int scale, RoundingMode rm) {
        if (this.isParallel(pl, scale, rm)) {
            return this.equals(pl);
        }
        return true;
    }

    /**
     * @param l The line to test for intersection with this.
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} If this and {@code l} intersect.
     */
    public boolean isIntersectedBy(V3D_Line l, int scale, RoundingMode rm) {
        if (isParallel(l, scale, rm)) {
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
        BigDecimal d = normalVector.dx.multiply(p.x.subtract(pt.x)).add(normalVector.dy.multiply(p.y
                .subtract(pt.y))).add(normalVector.dz.multiply(p.z.subtract(pt.z)));
        return d.compareTo(BigDecimal.ZERO) == 0;
        // Alternative:
        // p.x = l*(ab.x)+m*(ac.x) ---1
        // p.y = l*(ab.y)+m*(ac.y) ---2
        // p.z = l*(ab.z)+m*(ac.z) ---3
        // From 1: l = (p.x-(m*(ac.x)))/(ab.x)
        // Sub into 2: p.y = (((p.x-(m*(ac.x)))/(ab.x))*(ab.y))+(m*(ac.z))
        // p.y = (p.x/ab.x)-(m*((ac.x/ab.x))+(m*(ac.z)))
        // p.y = (p.x/ab.x)-(m*((ac.x/ab.x)-(ac.z)))
        // m*((ac.x/ab.x)-(ac.z)) = (p.x/ab.x)-(p.y)
        // m = ((p.x/ab.x)-(p.y))/((ac.x/ab.x)-(ac.z))
        // Sub into 1:
        // p.x = (l*(ab.x))+(((p.x/ab.x)-(p.y))/((ac.x/ab.x)-(ac.z))*(ac.x))
        // l = (p.x-((((p.x/ab.x)-(p.y))/((ac.x/ab.x)-(ac.z))*(ac.x)))/(ab.x)
        // Check with 3:
        // 0 = ((((p.x-(p.x/ab.x)-p.y/((ac.x/ab.x)-ac.z)(ac.x))/ab.x)*(ab.z))+((p.x/ab.x)-p.y/((ac.x/ab.x)-ac.z)*(ac.z)))-p.z
    }

    /**
     * @param l The line segment to test if it is on the plane.
     * @return {@code true} If {@code pt} is on the plane.
     */
    public boolean isOnPlane(V3D_Line l) {
        return isIntersectedBy(l.p) && isIntersectedBy(l.q);
    }

    public V3D_Geometry getIntersection(V3D_Line l, int scale, RoundingMode rm) {
        if (this.isParallel(l, scale, rm)) {
            if (isOnPlane(l)) {
                return this;
            }
        }        
        throw new UnsupportedOperationException();
    }

    public V3D_Geometry getIntersection(V3D_Plane pl, int scale, RoundingMode rm) {
        if (isParallel(pl, scale, rm)) {
            if (pl.equals(this)) {
                return this;
            }
            return null;
        }
        // Calculate the line of intersection.
        // The cross product of the normal vectors gives the vector defining the 
        // line.
        V3D_Vector v = normalVector.getCrossProduct(pl.normalVector);
        // Get any non zero elements of v?
        if (v.dx.compareTo(BigDecimal.ZERO) == 0) {
            if (v.dy.compareTo(BigDecimal.ZERO) == 0) {
                /**
                 * normalVector.dx(x(t)−p.x)+normalVector.dy(y(t)−p.y)+normalVector.dz(z(t)−p.z)
                 */
                // where:
                // normalVector.dx = a; normalVector.dy = b; normalVector.dz = c
                // pl.normalVector.dx = d; pl.normalVector.dy = e; pl.normalVector.dz = f
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
                if (normalVector.dx.compareTo(e.P0) == 0) {
                    //pl.b
                } else if (normalVector.dy.compareTo(e.P0) == 0) {
//                    // y = l + ((- a(x − k) - c(z − l)) / b)
                    BigDecimal y = p.y;
//                    // x = k + ((e(l - y) - f(z - m)) / d)
//                    // z = m + ((- d(x - k) - e(y - m)) / f)
//                    BigDecimal x = p.x.apply(
//                            Math_BigDecimal.divideRoundIfNecessary(
//                                    (pl.normalVector.dy.multiply(p.y.subtract(y))).subtract(pl.normalVector.dz.multiply(z.subtract(pl.p.z))),
//                                    pl.normalVector.dx, scale, rm));
                } else {
                    return e.zAxis;
                }
                //BigDecimal x = p.x
                BigDecimal numerator = p.z.subtract(p.y)
                        .subtract(normalVector.dz.multiply(p.z))
                        .subtract(p.y.multiply(normalVector.dy));
                BigDecimal denominator = normalVector.dy.subtract(normalVector.dz);
                if (denominator.compareTo(e.P0) == 0) {
                    // Case 1: The z axis
                    return null;
                } else {
                    // y = (p.y - c(z−p.z)) / b   --- 1          
                    // z = (p.z - b(y−p.y)) / c   --- 2
                    // normalVector.dx = a; normalVector.dy = b; normalVector.dz = c
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
                    BigDecimal z = Math_BigDecimal.divideRoundIfNecessary(
                            numerator, denominator, scale + 2, rm); // scale + 2 sensible?
                    // Substitute into 1
                    // y = (p.y - c(z−p.z)) / b   --- 1
                    if (normalVector.dy.compareTo(e.P0) == 0) {
                        // Another case to deal with
                        return null;
                    } else {
                        BigDecimal y = Math_BigDecimal.divideRoundIfNecessary(
                                p.y.subtract(normalVector.dz.multiply(z.subtract(p.z))),
                                normalVector.dy, scale, rm);
                        return new V3D_Line(new V3D_Point(e, p.x, y, z),
                                new V3D_Point(e, p.x.multiply(e.N1), y.add(v.dy), z.add(v.dz)));
                    }
                }
            } else {
                if (v.dz.compareTo(BigDecimal.ZERO) == 0) {
                    // Case 2: The y axis
                    return e.yAxis;
                } else {
                    // Case 3
                    // y = (p.y - c(z−p.z)) / b   --- 1          
                    // z = (p.z - b(y−p.y)) / c   --- 2
                    // normalVector.dx = a; normalVector.dy = b; normalVector.dz = c
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
                    BigDecimal numerator = p.z.subtract(p.y)
                            .subtract(normalVector.dz.multiply(p.z))
                            .subtract(p.y.multiply(normalVector.dy));
                    BigDecimal denominator = normalVector.dy.subtract(normalVector.dz);
                    if (denominator.compareTo(e.P0) == 0) {
                        // Another case to deal with
                        return null;
                    } else {
                        BigDecimal z = Math_BigDecimal.divideRoundIfNecessary(
                                numerator, denominator, scale + 2, rm); // scale + 2 sensible?
                        // Substitute into 1
                        // y = (p.y - c(z−p.z)) / b   --- 1
                        if (normalVector.dy.compareTo(e.P0) == 0) {
                            // Another case to deal with
                            return null;
                        } else {
                            BigDecimal y = Math_BigDecimal.divideRoundIfNecessary(
                                    p.y.subtract(normalVector.dz.multiply(z.subtract(p.z))),
                                    normalVector.dy, scale, rm);
                            return new V3D_Line(new V3D_Point(e, e.P0, y, z),
                                    new V3D_Point(e, e.P0, y.add(v.dy), z.add(v.dz)));
                        }
                    }
                }
            }
        } else {
            if (v.dy.compareTo(BigDecimal.ZERO) == 0) {
                if (v.dz.compareTo(BigDecimal.ZERO) == 0) {
                    // Case 4: The x axis
                    return e.xAxis;
                } else {
                    // Case 5
                    return null;
                }
            } else {
                if (v.dz.compareTo(BigDecimal.ZERO) == 0) {
                    // Case 6
                    return null;
                } else {
                    /**
                     * Case 7: Neither plane aligns with any axis and they are
                     * not orthogonal.
                     *
                     * normalVector.dx(x(t)−p.x)+normalVector.dy(y(t)−p.y)+normalVector.dz(z(t)−p.z)
                     */
                    // where:
                    // normalVector.dx = a; normalVector.dy = b; normalVector.dz = c
                    // pl.normalVector.dx = d; pl.normalVector.dy = e; pl.normalVector.dz = f
                    // a(x−p.x)+b(y−p.y)+c(z−p.z) = 0
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
                    BigDecimal a = normalVector.dx;
                    BigDecimal b = normalVector.dy;
                    BigDecimal c = normalVector.dz;
                    BigDecimal d = pl.normalVector.dx;
                    BigDecimal e = pl.normalVector.dy;
                    BigDecimal f = pl.normalVector.dz;
                    BigDecimal db = d.multiply(b);
                    BigDecimal dc = d.multiply(c);
                    BigDecimal ea = e.multiply(a);
                    BigDecimal ec = e.multiply(c);
                    BigDecimal ef = e.multiply(f);
                    BigDecimal fa = f.multiply(a);
                    BigDecimal fb = f.multiply(b);
                    BigDecimal db_div_a = Math_BigDecimal.divideRoundIfNecessary(db, a, scale, rm); 
                    BigDecimal dc_div_a = Math_BigDecimal.divideRoundIfNecessary(dc, a, scale, rm);
                    BigDecimal ea_div_b = Math_BigDecimal.divideRoundIfNecessary(ea, b, scale, rm); 
                    BigDecimal ec_div_b = Math_BigDecimal.divideRoundIfNecessary(ec, b, scale, rm); 
                    BigDecimal fa_div_c = Math_BigDecimal.divideRoundIfNecessary(fa, c, scale, rm);
                    BigDecimal fb_div_c = Math_BigDecimal.divideRoundIfNecessary(fb, c, scale, rm);
                    BigDecimal denom12yitoz = e.subtract(db_div_a);
                    BigDecimal denom12zitoy = dc_div_a.subtract(f);
                    BigDecimal denom13zitoy = f.subtract(dc_div_a);
                    BigDecimal denom13yitoz = db_div_a.subtract(e);
                    BigDecimal denom21xitoz = d.subtract(ea_div_b);
                    BigDecimal denom21zitox = ec_div_b.subtract(f);
                    BigDecimal denom23zitox = f.subtract(ec_div_b);
                    BigDecimal denom23xitoz = ea_div_b.subtract(d);
                    BigDecimal denom31xitoy = d.subtract(fa_div_c);
                    BigDecimal denom31yitox = e.add(fb_div_c);
                    BigDecimal denom32yitox = e.subtract(fb_div_c);
                    BigDecimal denom32xitoy = fa_div_c.subtract(d);
                    // Solve for z
                    // Let; n = v.dx; o = v.dy; p = v.dz
                    // x(t) = x + v.dx 
                    // y(t) = y(0) + v.dy 
                    // z-p = z(0)
                    // z = (ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek)/(dc/a-f)  ---12 z ito y                    
                    // z-p = (ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek)/(dc/a-f)
                    // y = (fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e)  ---13 y ito z
                    // z-p = (e((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))-db((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))/a-dj-dg+dbh/a+dci/a-fl-ek)/(dc/a-f)
                    // (z-p)(dc/a-f) = e((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))-db((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))/a-dj-dg+dbh/a+dci/a-fl-ek
                    // (efz-edcz/a-edj+edg+edbh/a+edci/a-eek-efl)/(db/a-e) = -dbfz/(db-ae)+dbdcz/(adb-aae)+ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek
                    // efz/(db/a-e)-edcz/(db-ae)-edj/(db/a-e)+edg/(db/a-e)+edbh/(db-ae)+edci/(db-ae)-eek/(db/a-e)-efl/db/a-e) = -dbfz/(db-ae)+dbdcz/(adb-aae)+ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek
                    // efz/(db/a-e)-edcz/(db-ae)+dbfz/(db-ae)-dbdcz/(adb-aae) = ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek+edj/(db/a-e)-edg/(db/a-e)-edbh/(db-ae)-edci/(db-ae)+eek/(db/a-e)+efl/db/a-e)
                    // z(ef/(db/a-e)-edc/(db-ae)+dbf/(db-ae)-dbdc/(adb-aae)) = ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek+edj/(db/a-e)-edg/(db/a-e)-edbh/(db-ae)-edci/(db-ae)+eek/(db/a-e)+efl/db/a-e)
                    // z = num/den
                    // den = ef/(db/a-e)-edc/(db-ae)+dbf/(db-ae)-dbdc/(adb-aae);
                    // num = ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek+edj/(db/a-e)-edg/(db/a-e)-edbh/(db-ae)-edci/(db-ae)+eek/(db/a-e)+efl/db/a-e)
                    // Let: q = db-ae; r = db/a-e; s=adb-aae
                    BigDecimal q = db.subtract(a.multiply(e));
                    BigDecimal r = db_div_a.subtract(e);
                    BigDecimal s = db.multiply(a).subtract(a.multiply(ea));
                    BigDecimal den = Math_BigDecimal.divideRoundIfNecessary(ef, r, scale, rm)   
                            .subtract(Math_BigDecimal.divideRoundIfNecessary(e.multiply(dc), q, scale, rm))
                            .add(Math_BigDecimal.divideRoundIfNecessary(db.multiply(f), q, scale, rm))
                            .subtract(Math_BigDecimal.divideRoundIfNecessary(d.multiply(db.multiply(c)), s, scale, rm));
                    BigDecimal g = p.x;
                    BigDecimal h = p.y;
                    BigDecimal i = p.z;
                    BigDecimal j = pl.p.x;
                    BigDecimal k = pl.p.y;
                    BigDecimal l = pl.p.z;                    
                    BigDecimal ek = e.multiply(k);
                    BigDecimal ci = c.multiply(i);
                    BigDecimal dg = d.multiply(g);
                    BigDecimal dbh = db.multiply(h);
                    BigDecimal dbh_sub_dci = dbh.subtract(d.multiply(ci));
                    BigDecimal fl = f.multiply(l);
                    BigDecimal bh = b.multiply(h);
                    BigDecimal dj = d.multiply(j);
                    BigDecimal num = d.multiply(j.subtract(dg).add(ek).add(fl))
                            .subtract(Math_BigDecimal.divideRoundIfNecessary(e.multiply(dbh_sub_dci), q, scale, rm))
                            .subtract(Math_BigDecimal.divideRoundIfNecessary(d.multiply(dbh_sub_dci), s, scale, rm))
                            .subtract(dj.subtract(dg))
                            .add(Math_BigDecimal.divideRoundIfNecessary(d.multiply(bh.add(ci)), a, scale, rm))
                            .subtract(fl.subtract(ek))
                            .add(Math_BigDecimal.divideRoundIfNecessary(e.multiply(dj.subtract(dg).add(ek).add(fl)), r, scale, rm));
                    BigDecimal z = Math_BigDecimal.divideRoundIfNecessary(num, den, scale, rm);
                    // y = (dj−dg-dbh/a-dci/a+dcz/a+fl−fz+ek)/(e-db/a)  ---12 y ito z
                    // y = num/den
                    num = dj.subtract(dg).subtract(db_div_a.multiply(h))
                            .subtract(dc_div_a.multiply(i))
                            .add(dc_div_a.multiply(z)).add(fl).subtract(f.multiply(z)).add(ek);
                    den = e.subtract(db_div_a);
                    BigDecimal y = Math_BigDecimal.divideRoundIfNecessary(num, den, scale, rm);
                    // x = (ek−eh-eag/b-eci/b+ecz/b+fl−fz-dj)/(d-ea/b)  ---21 x ito z
                    BigDecimal e_div_b = Math_BigDecimal.divideRoundIfNecessary(e, b, scale, rm);;
                    num = ek.subtract(e.multiply(h))
                            .subtract(g.multiply(a).multiply(e_div_b))
                            .subtract(ci.multiply(e_div_b))
                            .add(dc_div_a.multiply(z))
                            .add(c.multiply(z).multiply(e_div_b))
                            .add(fl)
                            .subtract(f.multiply(z))
                            .subtract(dj);
                    den = d.subtract(a.multiply(e_div_b));                    
                    BigDecimal x = Math_BigDecimal.divideRoundIfNecessary(num, den, scale, rm);
                    V3D_Point aPoint = new V3D_Point(this.e, x, y, z);
                    return new V3D_Line(aPoint, aPoint.apply(v));
                }
            }
        }
        // This is where the two equations of the plane are equal.
        // normalVector.dx(x(t)−p.x)+normalVector.dy(y(t)−p.y)+normalVector.dz(z(t)−p.z)
        // where:
        // normalVector.dx = a; normalVector.dy = b; normalVector.dz = c
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
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this is parallel to p given scale and rm.
     */
    public boolean isParallel(V3D_Plane p, int scale, RoundingMode rm) {
        return p.getNormalVector().isParallel(getNormalVector(), scale, rm);
    }

    /**
     * @param l The line to test if it is parallel to this.
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this is parallel to p given scale and rm.
     */
    public boolean isParallel(V3D_Line l, int scale, RoundingMode rm) {
        return l.pq.isParallel(this.pq, scale, rm);
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
