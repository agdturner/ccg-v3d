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
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_Plane - for representing infinite flat 2D planes in 3D. The plane is
 * defined by three points {@link #p}, {@link #q} and {@link #r} from which an
 * equation of the plane is derived. The derivation is by creating two
 * non-collinear V3D_Vectors {@link #pq} and {@link #pr}. The equation of the
 * plane is:
 * <ul>
 * <li>xxx*(x-x0) + yyy*(y-y0) + zzz*(z-z0 = 0</li>
 * </ul>
 * where:
 * <ul>
 * <li>x, y, and z are any of the coordinates in {@link #p}, {@link #q} and
 * {@link #r}</li>
 * <li>x0, y0 and z0 represents any other point in the plane</li>
 * <li>{@code xxx = pq.dy.multiply(pr.dz).subtract(pr.dy.multiply(pq.dz))} -
 * which is the dx of the normal vector that is perpendicular to the plane.</li>
 * <li>{@code yyy = (pq.dx.multiply(pr.dz).subtract(pr.dx.multiply(pq.dz))).negate()}
 * - which is the dy of the normal vector that is perpendicular to the
 * plane.</li>
 * <li>{@code zzz = pq.dx.multiply(pr.dy).subtract(pr.dx.multiply(pq.dy))} -
 * which is the dx of the normal vector that is perpendicular to the plane.</li>
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
        pq = new V3D_Vector(p.x.subtract(q.x), p.y.subtract(q.y), p.z.subtract(q.z));
        pr = new V3D_Vector(p.x.subtract(r.x), p.y.subtract(r.y), p.z.subtract(r.z));
        /**
         * Calculate the normal perpendicular vector.
         */
        normalVector = new V3D_Vector(pq.dy.multiply(pr.dz).subtract(pr.dy.multiply(pq.dz)),
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
                BigDecimal numerator = p.z.subtract(p.y)
                        .subtract(normalVector.dz.multiply(p.z))
                        .subtract(p.y.multiply(normalVector.dy));
                BigDecimal denominator = normalVector.dy.subtract(normalVector.dz);
                if (denominator.compareTo(e.P0) == 0) {
                    // Case 1: The z axis
                    return e.zAxis;
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
                    // Case 7
                    // This is where the two equations of the plane are equal.
                    // normalVector.dx(x(t)−p.x)+normalVector.dy(y(t)−p.y)+normalVector.dz(z(t)−p.z)
                    // where:
                    // normalVector.dx = a; normalVector.dy = b; normalVector.dz = c
                    // pl.normalVector.dx = d; pl.normalVector.dy = e; pl.normalVector.dz = f
                    // a(x−p.x) + b(y−p.y) + c(z−p.z) = 0
                    // x = p.x + ((- b(y−p.y) - c(z−p.z)) / a)                     --- 1
                    // y = p.y + ((- a(x−p.x) - c(z−p.z)) / b)                     --- 2
                    // z = p.z + ((- a(x−p.x) - b(y−p.y)) / c)                     --- 3
                    // x = pl.p.x + ((- pl.b(y − pl.p.y) - pl.c(z − pl.p.z)) / pl.a)   --- 4
                    // y = pl.p.y + ((- pl.a(x − pl.p.x) - pl.c(z − pl.p.z)) / pl.b)   --- 5
                    // z = pl.p.z + ((- pl.a(x − pl.p.x) - pl.b(y − pl.p.y)) / pl.c)   --- 6
                    // Let:
                    // p.x = k; p.y = l; p.z = m
                    // x = k + ((- b(y − l) - c(z − m)) / a)      --- 1t
                    // y = l + ((- a(x − k) - c(z − l)) / b)      --- 2t
                    // z = m + ((- a(x − k) - b(y − m)) / c)      --- 3t
                    // Let:
                    // pl.p.x = k; pl.p.y = l; pl.p.z = m
                    // x = k + ((- e(y - l) - f(z - m)) / d)       --- 1p
                    // y = l + ((- d(x - k) - f(z - l)) / e)       --- 2p
                    // z = m + ((- d(x - k) - e(y - m)) / f)       --- 3p
                    // sub 1t into 2p:
                    // y = l + ((- d((k + ((- b(y − l) - c(z − m)) / a)) - k) - f(z - l)) / e)
                    // ey - el = - d(k + ((- b(y − l) - c(z − m)) / a)) + dk - fz + fl
                    // ey = - d(k + ((- b(y − l) - c(z − m)) / a)) + dk - fz + fl + el
                    // ey = - dk - d((- b(y − l) - c(z − m)) / a) + dk - fz + fl + el
                    // ey = - db(y - l)/a - cd(z-m)/a - fz + fl + el
                    // ey + dby/a - dbl = - cd(z-m)/a - fz + fl + el
                    // y(e +db/a) = dbl - cd(z-m)/a - fz + fl + el
                    // y ito z      // y = (dbl - cd(z-m)/a - fz + fl + el) / (e +db/a)
                    // Substitute into 3p
                    // z = m + ((- d(x - k) - e(y - m)) / f)
                    // z = m + ((- d(x - k) - e(((dbl - cd(z-m)/a - fz + fl + el) / (e +db/a)) - m)) / f)
                    // zf - mf = - d(x - k) - e(((dbl - cd(z-m)/a - fz + fl + el) / (e +db/a)) - m)
                    // zf - mf = - d(x - k) - e(((dbl - cd(z-m)/a - fz + fl + el) / (e +db/a)) - m)
                    // zf = - d(x - k) - e((dbl - cd(z-m)/a - fz + fl + el) / (e +db/a)) + em + mf
                    // zf = dk - dx - e(dbl - cd(z-m)/a - fz + fl + el) / (e +db/a) + em + mf
                    // Let: e/(e + db/a) = g
                    // zf = dk - dx - g(dbl - cd(z-m)/a - fz + fl + el) + em + mf
                    // zf = dk - dx - gdbl - gcd(z-m)/a - gfz + gfl + gel + em + mf
                    // zf = dk - dx - gdbl - gcdz/a + gm/a - gfz + gfl + gel + em + mf
                    // zf + gcdz/a + gfz = dk - dx - gdbl + gm/a + gfl + gel + em + mf
                    // z(f + gcd/a + gf) = dk - dx - gdbl + gm/a + gfl + gel + em + mf
                    // z ito x      // z = (dk - dx - gdbl + gm/a + gfl + gel + em + mf) / (f + gcd/a + gf)
                    // z(f + gcd/a + gf) = dk - dx - gdbl + gm/a + gfl + gel + em + mf
                    // x ito z      // x = (dk - z(f + gcd/a + gf) - gdbl + gm/a + gfl + gel + em + mf) / d
                    // Substitute x ito z and y ito z into 3P
                    // z = m + ((- d(x - k) - e(y - m)) / f)
                    // z = m + ((- d(((dk - z(f + gcd/a + gf) - gdbl + gm/a + gfl + gel + em + mf) / d) - k) - e(((dbl - cd(z-m)/a - fz + fl + el) / (e +db/a)) - m)) / f)
                    // z - m = ((- d(((dk - z(f + gcd/a + gf) - gdbl + gm/a + gfl + gel + em + mf) / d) - k) - e(((dbl - cd(z-m)/a - fz + fl + el) / (e +db/a)) - m)) / f)
                    // z - m = ((- d(((dk - z(f + gcd/a + gf) - gdbl + gm/a + gfl + gel + em + mf) / d) - k) - e(((dbl - cd(z-m)/a - fz + fl + el) / (e +db/a)) - m)) / f)
                    // zf - mf = - d(((dk - z(f + gcd/a + gf) - gdbl + gm/a + gfl + gel + em + mf) / d) - k) - e(((dbl - cd(z-m)/a - fz + fl + el) / (e +db/a)) - m)
                    // zf - mf = - d((((dk - z(f + gcd/a + gf) - gdbl + gm/a + gfl + gel + em + mf) / d) - k) - e(((dbl - cd(z-m)/a - fz + fl + el) / (e +db/a)) - m))
                    // zf - mf = - d((((dk - z(f + gcd/a + gf) - gdbl + gm/a + gfl + gel + em + mf) / d) - k)
                    //        - e(((dbl - cd(z-m)/a - fz + fl + el) / (e +db/a)) - m))                    
                    // zf = ed((dbl - cd(z-m)/a - fz + fl + el) / (e +db/a)) - edm 
                    //        + z(f + gcd/a + gf) + gdbl - gm/a - gfl - gel - em
                    // Let: ed/(e + db/a) = h
                    // zf = hdbl - hcd(z-m)/a - hfz + hfl + hel - edm 
                    //        + z(f + gcd/a + gf) + gdbl - gm/a - gfl - gel - em
                    // zf - z(f + gcd/a + gf) = hdbl - zf - zgcd/a - zgf - hfz + hfl + hel - edm + gdbl - gm/a - gfl - gel - em
                    // z2f - z(f + gcd/a + gf) + zgcd/a + zgf + hfz = hdbl + hfl + hel - edm + gdbl - gm/a - gfl - gel - em
                    // z(2f - (f + gcd/a + gf) + gcd/a + gf + hf) = hdbl + hfl + hel - edm + gdbl - gm/a - gfl - gel - em
                    // z(f + hf) = hdbl + hfl + hel - edm + gdbl - gm/a - gfl - gel - em
                    // z = (hdbl + hfl + hel - edm + gdbl - gm/a - gfl - gel - em) / (f + fh)
                    // Recall: h = ed/(e + db/a; g = e/(e + db/a)
                    // h = eda/(ea + db)
                    // g = ea/(ea + db)
                    // So, h = dg
                    // z = (dgdbl + dgfl + dgel - edm + gdbl - gm/a - gfl - gel - em) / (f + fdg)
                    // z = (d(gl(db + f + e + b) - em) - g(m/a - l(f - e)) - em) / (f + fdg)
                    BigDecimal a = normalVector.dx;
                    BigDecimal b = normalVector.dy;
                    BigDecimal c = normalVector.dz;
                    BigDecimal d = pl.normalVector.dx;
                    BigDecimal e = pl.normalVector.dy;
                    BigDecimal f = pl.normalVector.dz;
                    BigDecimal k = p.x;
                    BigDecimal l = p.y;
                    BigDecimal m = p.z;
                    BigDecimal ea = e.multiply(a);
                    BigDecimal em = e.multiply(m);
                    BigDecimal db = d.multiply(b);
                    BigDecimal g = Math_BigDecimal.divideRoundIfNecessary(ea, ea.add(db), scale, rm);
                    BigDecimal dg = d.multiply(g);
                    // z = (d(gl(db + f + e + b) - em) - g(m/a - l(f - e)) - em) / (f + fdg)
                    // z = (dgl(db + f + e + b) - dem - g(m/a - l(f - e)) - em) / f(1 + dg)
                    BigDecimal num = dg.multiply(l).multiply(db.add(f.add(e.add(b))))
                            .subtract(d.multiply(e).multiply(m))
                            .subtract(g.multiply(Math_BigDecimal.divideRoundIfNecessary(m, a, scale, rm)
                                    .subtract(l.multiply(f.subtract(e))))
                                    .subtract(em));
                    BigDecimal den = f.multiply(this.e.P1.add(dg));
                    BigDecimal z;
                    if (den.compareTo(this.e.P0) == 0) {
                        // Another case...
                        return null;
                    } else {
                        z = Math_BigDecimal.divideRoundIfNecessary(num, den, scale, rm);
                    }
                    // Now we have z let us get x and y
                    // x = (dk - z(f + gcd/a + gf) - gdbl + gm/a + gfl + gel + em + mf) / d
                    BigDecimal x = Math_BigDecimal.divideRoundIfNecessary(
                            d.multiply(k).subtract(z.multiply(f.add(dg.multiply(c)).add(g.multiply(f))))
                                    .subtract(dg.multiply(b.multiply(l)))
                                    .add(Math_BigDecimal.divideRoundIfNecessary(g.multiply(m), a, scale, rm))
                                    .add(g.multiply(f.multiply(l)))
                                    .add(g.multiply(e.multiply(l)))
                                    .add(em).add(m.multiply(f)), d, scale, rm);
                    //
                    // y = l + ((- a(x − k) - c(z − l)) / b)
                    // y = l + ((a(k - x) - c(z − l)) / b)
                    BigDecimal y = l.add(Math_BigDecimal.divideRoundIfNecessary(
                            a.multiply(k.subtract(x)).subtract(c.multiply(z.subtract(l))), b, scale, rm));
                    V3D_Point rq = new V3D_Point(this.e, x, y, z);
                    return new V3D_Line(rq, rq.add(v));
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
