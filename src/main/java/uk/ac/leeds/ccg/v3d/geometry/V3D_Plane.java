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

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * 3D representation of an infinite plane. The plane is defined by the point
 * {@link #p} and the normal {@link #n}. A plane be constructed in numerous ways
 * including by using three points that are not collinear.
 *
 * The equation of the plane is:
 * <ul>
 * <li>{@code A*(x-x0) + B*(y-y0) + C*(z-z0) = 0}</li>
 * <li>{@code A*(x) + B*(y) + C*(z) - D = 0 where D = -(A*x0 + B*y0 + C*z0)}</li>
 * </ul>
 * where:
 * <ul>
 * <li>{@code x0}, {@code y0} and {@code z0} represents any other point on the
 * plane</li>
 * <li>{@code A} is the {@code dx} of the vector perpendicular to the
 * plane.</li>
 * <li>{@code B} is the {@code dy} of the vector perpendicular to the
 * plane.</li>
 * <li>{@code C} is the {@code dz} of the vector perpendicular to the
 * plane.</li>
 * </ul>
 *
 * <ol>
 * <li>{@code n.getDX(oom)(x(t)−pl.getX(oom))+n.getDY(oom)(y(t)−pl.getY(oom))+n.getDZ(oom)(z(t)−pl.getZ(oom)) = 0}</li>
 * <li>{@code n.getDX(oom)(x(t)−q.getX(oom))+n.getDY(oom)(y(t)−q.getY(oom))+n.getDZ(oom)(z(t)−q.getZ(oom)) = 0}</li>
 * <li>{@code n.getDX(oom)(x(t)−r.getX(oom))+n.getDY(oom)(y(t)−r.getY(oom))+n.getDZ(oom)(z(t)−r.getZ(oom)) = 0}</li>
 * </ol>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Plane extends V3D_Geometry {

    private static final long serialVersionUID = 1L;

    /**
     * The x = 0 plane.
     */
    public static final V3D_Plane X0 = new V3D_Plane(V3D_Point.ORIGIN,
            V3D_Vector.I);

    /**
     * The y = 0 plane.
     */
    public static final V3D_Plane Y0 = new V3D_Plane(V3D_Point.ORIGIN,
            V3D_Vector.J);

    /**
     * The z = 0 plane.
     */
    public static final V3D_Plane Z0 = new V3D_Plane(V3D_Point.ORIGIN,
            V3D_Vector.K);

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param pl The plane to test points are coplanar with.
     * @param points The points to test if they are coplanar with pl.
     * @return {@code true} iff all points are coplanar with pl.
     */
    public static boolean isCoplanar(int oom,
            RoundingMode rm, V3D_Plane pl, V3D_Point... points) {
        for (V3D_Point pt : points) {
            if (!pl.isIntersectedBy(pt, oom, rm)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points The points to test if they are coplanar.
     * @return {@code false} if points are coincident or collinear. {@code true}
     * iff all points are coplanar.
     */
    public static boolean isCoplanar(int oom,
            RoundingMode rm, V3D_Point... points) {
        // For the points to be in a plane at least one must not be collinear.
        if (V3D_Point.isCoincident(oom, rm, points)) {
            return false;
        }
        if (!V3D_Line.isCollinear0(oom, rm, points)) {
            V3D_Plane p = getPlane0(oom, rm, points);
            return isCoplanar(oom, rm, p, points);
        }
        return false;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points The points from which a plane is to be derived.
     * @return A plane that may or may not contain all the points or
     * {@code null} if there is no such plane (if the points are coincident or
     * collinear).
     */
    public static V3D_Plane getPlane(int oom,
            RoundingMode rm, V3D_Point... points) {
        V3D_Line l = V3D_Line.getLine(oom, rm, points);
        if (l == null) {
            return null;
        }
        for (V3D_Point p : points) {
            if (!V3D_Line.isCollinear(oom, rm, l, p)) {
                return new V3D_Plane(l.getP(), l.getQ(oom, rm), p, oom, rm);
            }
        }
        return null;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param points The points from which a plane is to be derived.
     * @return A plane that may or may not contain all the points or
     * {@code null} if there is no such plane. This does not test if the points
     * are coincident or collinear.
     */
    private static V3D_Plane getPlane0(int oom,
            RoundingMode rm, V3D_Point... points) {
        V3D_Line l = V3D_Line.getLine(oom, rm, points);
        for (V3D_Point p : points) {
            if (!V3D_Line.isCollinear(oom, rm, l, p)) {
                return new V3D_Plane(l.getP(), l.getQ(oom, rm), p, oom, rm);
            }
        }
        return null;
    }
    
    /**
     * The point that defines the plane.
     */
    protected V3D_Vector p;

    /**
     * The normal vector that defines the plane. This is perpendicular to the
     * plane.
     */
    public V3D_Vector n;

    /**
     * Create a new instance.
     *
     * @param p The plane used to create this.
     */
    public V3D_Plane(V3D_Plane p) {
        super(p.offset);
        this.p = new V3D_Vector(p.p);
        this.n = new V3D_Vector(p.n);
    }

    /**
     * Create a new instance.
     *
     * @param p Used to initialise {@link #p}.
     * @param n The normal of the plane.
     */
    public V3D_Plane(V3D_Point p, V3D_Vector n) {
        super(p.offset);
        this.p = p.rel;
        this.n = n;
    }

    /**
     * Create a new instance.
     *
     * @param l A line segment in the plane.
     * @param inplane A vector in the plane that is not a scalar multiple of 
     * the vector of the line of l.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Plane(V3D_LineSegment l, V3D_Vector inplane, int oom,
            RoundingMode rm) {
        super(l.offset);
        this.p = l.getP().rel;
        this.n = l.l.v.getCrossProduct(inplane, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param p Used to initialise {@link #p}.
     * @param q A point coplanar to pl and r, not collinear to both pl and r,
     * and not equal to pl or r.
     * @param r A point coplanar to pl and q, not collinear to both pl and q,
     * and not equal to pl or q.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Plane(V3D_Point p, V3D_Point q, V3D_Point r, int oom,
            RoundingMode rm) {
        super(p.offset);
        V3D_Vector qv = q.getVector(oom, rm);
        V3D_Vector pq = qv.subtract(p.getVector(oom, rm), oom, rm);
        V3D_Vector qr = r.getVector(oom, rm).subtract(qv, oom, rm);
        this.p = p.rel;
        this.n = pq.getCrossProduct(qr, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param p Used to initialise {@link #p}.
     * @param q A point coplanar to pl and r, not collinear to both pl and r,
     * and not equal to pl or r.
     * @param r A point coplanar to pl and q, not collinear to both pl and q,
     * and not equal to pl or q.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Plane(V3D_Vector p, V3D_Vector q, V3D_Vector r, int oom,
            RoundingMode rm) {
        this(V3D_Vector.ZERO, p, q, r, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param offset What {@link #offset} is set to.
     * @param p Used to initialise {@link #p}.
     * @param q A point coplanar to pl and r, not collinear to both pl and r,
     * and not equal to pl or r.
     * @param r A point coplanar to pl and q, not collinear to both pl and q,
     * and not equal to pl or q.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Plane(V3D_Vector offset, V3D_Vector p, 
            V3D_Vector q, V3D_Vector r, int oom, RoundingMode rm) {
        super(offset);
        V3D_Vector pq = q.subtract(p, oom, rm);
        if (pq.equals(V3D_Vector.ZERO)) {
            throw new RuntimeException("Cannot define plane as p equals q.");
        }
        V3D_Vector qr = r.subtract(q, oom, rm);
        if (qr.equals(V3D_Vector.ZERO)) {
            throw new RuntimeException("Cannot define plane as q equals r.");
        }
        this.p = p;
        this.n = pq.getCrossProduct(qr, oom, rm);
        //this.n = qr.getCrossProduct(pq, oom, rm);
    }

    @Override
    public String toString() {
        //return toString("");
        return toStringSimple("");
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toString(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFields(pad + " ") + "\n"
                + pad + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toStringSimple(String pad) {
        return pad + this.getClass().getSimpleName() + "(\n"
                + toStringFieldsSimple(pad + " ") + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFields(String pad) {
        return super.toStringFields(pad) + "\n"
                + pad + ",\n"
                + pad + "p=" + p.toString(pad) + "\n"
                + pad + ",\n"
                + pad + "n=" + n.toString(pad);
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFieldsSimple(String pad) {
        return super.toStringFieldsSimple(pad) + ",\n"
                + pad + "p=" + p.toStringSimple(pad) + ",\n"
                + pad + "n=" + n.toStringSimple(pad);
    }

    /**
     * @return {@link #p} with {@link #offset} and rotations applied.
     */
    public final V3D_Point getP() {
        return new V3D_Point(offset, p);
    }
    
    /**
     * Find a perpendicular vector using: user65203, How to find perpendicular
     * vector to another vector?, URL (version: 2020-10-01):
     * https://math.stackexchange.com/q/3821978
     *
     * @return A perpendicular vector.
     */
    public final V3D_Vector getPV() {
        V3D_Vector pv;
        V3D_Vector v1 = new V3D_Vector(Math_BigRationalSqrt.ZERO, n.dz, n.dy.negate());
        V3D_Vector v2 = new V3D_Vector(n.dz.negate(), Math_BigRationalSqrt.ZERO, n.dx);
        V3D_Vector v3 = new V3D_Vector(n.dy.negate(), n.dx, Math_BigRationalSqrt.ZERO);
        Math_BigRational mv1 = v1.getMagnitudeSquared();
        Math_BigRational mv2 = v2.getMagnitudeSquared();
        Math_BigRational mv3 = v3.getMagnitudeSquared();
        if (mv1.compareTo(mv2) == 1) {
            if (mv1.compareTo(mv3) == 1) {
                pv = v1;
            } else {
                pv = v3;
            }
        } else {
            if (mv2.compareTo(mv3) == 1) {
                pv = v2;
            } else {
                pv = v3;
            }
        }
        return pv;
    }

    /**
     * Get another point on the plane.
     * 
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A point on the plane.
     */
    public final V3D_Point getQ(int oom, RoundingMode rm) {
        return getQ(getPV(), oom, rm);
    }

    /**
     * Get another point on the plane.
     * 
     * @param pv What is from {@link #getPV()}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A point on the plane.
     */
    public final V3D_Point getQ(V3D_Vector pv, int oom, RoundingMode rm) {
        return new V3D_Point(offset, this.p.add(pv, oom, rm));
    }

    /**
     * Get another point on the plane.
     * 
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A point on the plane.
     */
    public final V3D_Point getR(int oom, RoundingMode rm) {
        return getR(getPV(), oom, rm);
    }

    /**
     * Get another point on the plane.
     * 
     * @param pv What is from {@link #getPV()}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A point on the plane.
     */
    public final V3D_Point getR(V3D_Vector pv, int oom, RoundingMode rm) {
        V3D_Vector pvx = pv.getCrossProduct(n, oom, rm);
        return new V3D_Point(offset, this.p.add(pvx, oom, rm));
    }
    
    /**
     * For getting the equation of the plane.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The equation of the plane as a String.
     */
    public String getEquation(int oom, RoundingMode rm) {
        Math_BigRational[] coefficients = getEquationCoefficients(oom, rm);
        return coefficients[0].toRationalString() + " * x + "
                + coefficients[1].toRationalString() + " * y + "
                + coefficients[2].toRationalString() + " * z + "
                + coefficients[3].toRationalString() + " = 0";
    }

    /**
     * For getting the equation of the plane.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The equation of the plane as a String.
     */
    public Math_BigRational[] getEquationCoefficients(int oom, RoundingMode rm) {
        Math_BigRational[] coeffs = new Math_BigRational[4];
//        // Ensure n is not null
//        n = getN(oom, rm);
        Math_BigRational ndxsr = n.dx.getSqrt();
        Math_BigRational ndysr = n.dy.getSqrt();
        Math_BigRational ndzsr = n.dz.getSqrt();
//        Math_BigRational k = (ndxsr.multiply(pl.getX(oom))
//                .add(ndysr.multiply(pl.getY(oom)))
//                .add(ndzsr.multiply(pl.getZ(oom)))).negate();
        V3D_Point tp = getP();
        Math_BigRational k = (ndxsr.multiply(tp.getX(oom, rm))
                .add(ndysr.multiply(tp.getY(oom, rm)))
                .add(ndzsr.multiply(tp.getZ(oom, rm)))).negate();
//        Math_BigRational k = (ndxsr.multiply(pl.getX(oom))
//                .subtract(ndysr.multiply(pl.getY(oom)))
//                .subtract(ndzsr.multiply(pl.getZ(oom))));
//        Math_BigRational k = ndxsr.multiply(pl.getX(oom))
//                .add(ndysr.multiply(pl.getY(oom)))
//                .add(ndzsr.multiply(pl.getZ(oom)));
        coeffs[0] = ndxsr;
        coeffs[1] = ndysr;
        coeffs[2] = ndzsr;
        coeffs[3] = k;
        return coeffs;
    }

    /**
     * Identify if the this is intersected by point {@code pt}. This uses
     * matrices as per:
     * https://math.stackexchange.com/questions/684141/check-if-a-point-is-on-a-plane-minimize-the-use-of-multiplications-and-divisio/684580#684580
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V3D_Point pt, int oom, RoundingMode rm) {
        Math_BigRational[][] m = new Math_BigRational[4][4];
        V3D_Point tp = getP();
        if (tp.isOrigin(oom, rm)) {
            V3D_Point tpt = new V3D_Point(pt);
            tpt.translate(V3D_Vector.IJK, oom, rm);
            V3D_Point ttp = new V3D_Point(getP());
            ttp.translate(V3D_Vector.IJK, oom, rm);
            V3D_Vector pv = getPV();
            V3D_Point ttq = getQ(pv, oom, rm);
            ttq.translate(V3D_Vector.IJK, oom, rm);
            V3D_Point ttr = getR(pv, oom, rm);
            ttr.translate(V3D_Vector.IJK, oom, rm);
            m[0][0] = ttp.getX(oom, rm);
            m[1][0] = ttp.getY(oom, rm);
            m[2][0] = ttp.getZ(oom, rm);
            m[3][0] = Math_BigRational.ONE;
            m[0][1] = ttq.getX(oom, rm);
            m[1][1] = ttq.getY(oom, rm);
            m[2][1] = ttq.getZ(oom, rm);
            m[3][1] = Math_BigRational.ONE;
            m[0][2] = ttr.getX(oom, rm);
            m[1][2] = ttr.getY(oom, rm);
            m[2][2] = ttr.getZ(oom, rm);
            m[3][2] = Math_BigRational.ONE;
            m[0][3] = tpt.getX(oom, rm);
            m[1][3] = tpt.getY(oom, rm);
            m[2][3] = tpt.getZ(oom, rm);
            m[3][3] = Math_BigRational.ONE;
            return new Math_Matrix_BR(m).getDeterminant().compareTo(Math_BigRational.ZERO) == 0;
        } else {
            V3D_Vector pv = getPV();
            V3D_Point tq = getQ(pv, oom, rm);
            V3D_Point tr = getR(pv, oom, rm);
            m[0][0] = tp.getX(oom, rm);
            m[1][0] = tp.getY(oom, rm);
            m[2][0] = tp.getZ(oom, rm);
            m[3][0] = Math_BigRational.ONE;
            m[0][1] = tq.getX(oom, rm);
            m[1][1] = tq.getY(oom, rm);
            m[2][1] = tq.getZ(oom, rm);
            m[3][1] = Math_BigRational.ONE;
            m[0][2] = tr.getX(oom, rm);
            m[1][2] = tr.getY(oom, rm);
            m[2][2] = tr.getZ(oom, rm);
            m[3][2] = Math_BigRational.ONE;
            m[0][3] = pt.getX(oom, rm);
            m[1][3] = pt.getY(oom, rm);
            m[2][3] = pt.getZ(oom, rm);
            m[3][3] = Math_BigRational.ONE;
            return new Math_Matrix_BR(m).getDeterminant().compareTo(Math_BigRational.ZERO) == 0;
        }
    }

    /**
     * @param l The line to test if it is on the plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} If {@code pt} is on the plane.
     */
    public boolean isOnPlane(V3D_Line l, int oom, RoundingMode rm) {
        return isIntersectedBy(l.getP(), oom, rm)
                && isIntersectedBy(l.getQ(oom, rm), oom, rm);
    }

    /**
     * Method adapted from the python code given here:
     * https://stackoverflow.com/a/18543221/1998054
     *
     * @param l The line to intersect with the plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection of the line and the plane. This is either
     * {@code null} a line or a point.
     */
    //@Override
    public V3D_Geometry getIntersectionOption(V3D_Line l, int oom, RoundingMode rm) {
        // Special case
        if (isParallel(l, oom, rm)) {
            if (isOnPlane(l, oom, rm)) {
                return l;
            }
            return null;
        }
        Math_BigRational dot = n.getDotProduct(l.v, oom, rm);
        if (dot.compareTo(Math_BigRational.ZERO) == 0) {
            return null;
        } else {
            V3D_Point lp = l.getP();
            V3D_Point tp = getP();
            boolean atOrigin = lp.isOrigin(oom, rm);
            if (atOrigin) {
                V3D_Point tlp = new V3D_Point(lp);
                tlp.translate(l.v, oom, rm);
                V3D_Point ttp = new V3D_Point(tp);
                ttp.translate(l.v, oom, rm);
                V3D_Vector w = new V3D_Vector(ttp, tlp, oom, rm);
                if (w.isZeroVector()) {
                    return tp;
                }
                Math_BigRational fac = n.getDotProduct(w, oom, rm).divide(dot).negate();
                if (fac == Math_BigRational.ZERO) {
                    return tp;
                }
                //fac = -dot_v3v3(p_no, w) / dot
                //V3D_Vector u2 = l.v.multiply(fac, oom, rm);
                V3D_Vector u2 = l.v.multiply(fac, oom, rm);
                //u = mul_v3_fl(u, fac)
                //V3D_Point p00 = new V3D_Point(tlp);
                //V3D_Point p00 = new V3D_Point(l.getP());
                //V3D_Point p00 = l.getP();
                //V3D_Point p00 = new V3D_Point(tp);
                tlp.translate(u2, oom, rm);
                tlp.translate(l.v.reverse(), oom, rm);
                return tlp;
            } else {
                V3D_Vector w = new V3D_Vector(tp, lp, oom, rm);
                if (w.isZeroVector()) {
                    return tp;
                }
                Math_BigRational fac = n.getDotProduct(w, oom, rm).divide(dot).negate();
                V3D_Vector u2 = l.v.multiply(fac, oom, rm);
                V3D_Point p00 = new V3D_Point(lp);
                p00.translate(u2, oom, rm);
                return p00;
            }
        }
    }

//    /**
//     * If the line is parallel to the plane, then there is no intersection
//     * unless the line is on the plane (when the line is the intersection). If
//     * the line is not parallel to the plane, then the line and the plane
//     * intersect at a point. In some cases that point can be represented exactly
//     * using the coordinates available. In some cases it cannot be represented
//     * exactly and some rounding is needed to provide a result. Rounding options
//     * include:
//     * <ol>
//     * <li>Constraining so the point is a point on the line. (NB. It should be
//     * possible to choose a point on either side of the plane.</li>
//     * <li>Constraining to be on the plane. (NB It should be possible to choose
//     * a point given the orientation of the line relative to the plane. If the
//     * line is perpendicular to the plane, there is a need to have a default
//     * based on the direction of the line and the orientation of the axes.)</li>
//     * <li>The closest point which does not have to be on the line or on the
//     * plane (NB. It should be possible to choose a point above or below the
//     * plane and in an orientation given by the orientation of the line relative
//     * to the plane. As with option 2, there is a need for a default if the line
//     * is perpendicular to the plane).</li>
//     * </ol>
//     * References:
//     * <ul>
//     * <li>https://en.wikipedia.org/wiki/Line%E2%80%93plane_intersection</li>
//     * <li>Weisstein, Eric W. "Line-Plane Intersection." From MathWorld--A
//     * Wolfram Web Resource.
//     * https://mathworld.wolfram.com/Line-PlaneIntersection.html
//     * </li>
//     * </ul>
//     *
//     * @param l The line to intersect with the plane.
//     * @param oom The Order of Magnitude for the calculation.
//     * @return The intersection of the line and the plane. This is either
//     * {@code null} a line or a point.
//     */
//    @Override
//    public V3D_Geometry getIntersection(V3D_Line l, int oom, RoundingMode rm) {
//        if (this.isParallel(l, oom, rm)) {
//            if (this.isOnPlane(l, oom, rm)) {
//                return l;
//            } else {
//                return null;
//            }
//        }
//        // Are either of the points of l on the plane.
//        //V3D_Point lp = l.getP(oom);
//        V3D_Point lp = l.getP();
//        if (this.isIntersectedBy(lp, oom, rm)) {
//            return lp;
//        }
//        V3D_Point lq = l.getQ(oom, rm);
//        if (this.isIntersectedBy(lq, oom, rm)) {
//            return lq;
//        }
//        V3D_Point tp = getP();
//        V3D_Vector pv = getPV();
//        V3D_Point tq = getQ(pv, oom, rm);
//        V3D_Point tr = getR(pv, oom, rm);
//        // Are any of these points on the line?
//        if (l.isIntersectedBy(tp, oom, rm)) {
//            return tp;
//        }
//        if (l.isIntersectedBy(tp, oom, rm)) {
//            return tq;
//        }
//        if (l.isIntersectedBy(tp, oom, rm)) {
//            return tr;
//        }
//        
//        int oomN6 = oom - 6;
//        //V3D_Vector lv = l.getV(oomN6, rm);
//        V3D_Vector lv = l.v;
//        Math_BigRational[][] m = new Math_BigRational[4][4];
//        m[0][0] = Math_BigRational.ONE;
//        m[0][1] = Math_BigRational.ONE;
//        m[0][2] = Math_BigRational.ONE;
//        m[0][3] = Math_BigRational.ONE;
////        m[1][0] = pl.getX(oom);
////        m[1][1] = q.getX(oom);
////        m[1][2] = r.getX(oom);
////        m[1][3] = lp.getX(oom);
////        m[2][0] = pl.getY(oom);
////        m[2][1] = q.getY(oom);
////        m[2][2] = r.getY(oom);
////        m[2][3] = lp.getY(oom);
////        m[3][0] = pl.getZ(oom);
////        m[3][1] = q.getZ(oom);
////        m[3][2] = r.getZ(oom);
////        m[3][3] = lp.getZ(oom);
////        V3D_Point tp = getP();
////        V3D_Vector pv = getPV();
////        V3D_Point tq = getQ(pv, oom, rm);
////        V3D_Point tr = getR(pv, oom, rm);
//        m[1][0] = tp.getX(oomN6, rm);
//        m[1][1] = tq.getX(oomN6, rm);
//        m[1][2] = tr.getX(oomN6, rm);
//        m[1][3] = lp.getX(oomN6, rm);
//        m[2][0] = tp.getY(oomN6, rm);
//        m[2][1] = tq.getY(oomN6, rm);
//        m[2][2] = tr.getY(oomN6, rm);
//        m[2][3] = lp.getY(oomN6, rm);
//        m[3][0] = tp.getZ(oomN6, rm);
//        m[3][1] = tq.getZ(oomN6, rm);
//        m[3][2] = tr.getZ(oomN6, rm);
//        m[3][3] = lp.getZ(oomN6, rm);
//        Math_Matrix_BR numm = new Math_Matrix_BR(m);
//        m[0][0] = Math_BigRational.ONE;
//        m[0][1] = Math_BigRational.ONE;
//        m[0][2] = Math_BigRational.ONE;
//        m[0][3] = Math_BigRational.ZERO;
////        m[1][0] = pl.getX(oom);
////        m[1][1] = q.getX(oom);
////        m[1][2] = r.getX(oom);
////        m[1][3] = lv.getDX(oom);
////        m[2][0] = pl.getY(oom);
////        m[2][1] = q.getY(oom);
////        m[2][2] = r.getY(oom);
////        m[2][3] = lv.getDY(oom);
////        m[3][0] = pl.getZ(oom);
////        m[3][1] = q.getZ(oom);
////        m[3][2] = r.getZ(oom);
////        m[3][3] = lv.getDZ(oom);
//        m[1][0] = tp.getX(oomN6, rm);
//        m[1][1] = tq.getX(oomN6, rm);
//        m[1][2] = tr.getX(oomN6, rm);
//        m[1][3] = lv.getDX(oomN6, rm);
//        m[2][0] = tp.getY(oomN6, rm);
//        m[2][1] = tq.getY(oomN6, rm);
//        m[2][2] = tr.getY(oomN6, rm);
//        m[2][3] = lv.getDY(oomN6, rm);
//        m[3][0] = tp.getZ(oomN6, rm);
//        m[3][1] = tq.getZ(oomN6, rm);
//        m[3][2] = tr.getZ(oomN6, rm);
//        m[3][3] = lv.getDZ(oomN6, rm);
//        Math_Matrix_BR denm = new Math_Matrix_BR(m);
//
//        if (denm.getDeterminant().compareTo(Math_BigRational.ZERO) == 0) {
//            System.out.println("Determinant is zero in getIntersection");
//            System.out.println("Plane:");
//            System.out.println(this.toString());
//            System.out.println("Line:");
//            System.out.println(l.toString());
//            return null;
//        }
//
//        Math_BigRational t = numm.getDeterminant().divide(denm.getDeterminant()).negate();
////        V3D_Point res = new V3D_Point(e,
////                lp.getX(oomN6, rm).add(lv.getDX(oomN6, rm).multiply(t)).round(oom, rm),
////                lp.getY(oomN6, rm).add(lv.getDY(oomN6, rm).multiply(t)).round(oom, rm),
////                lp.getZ(oomN6, rm).add(lv.getDZ(oomN6, rm).multiply(t)).round(oom, rm));
//        V3D_Point res = new V3D_Point(e,
//                lp.getX(oomN6, rm).add(lv.getDX(oomN6, rm).multiply(t)).round(oom, rm),
//                lp.getY(oomN6, rm).add(lv.getDY(oomN6, rm).multiply(t)).round(oom, rm),
//                lp.getZ(oomN6, rm).add(lv.getDZ(oomN6, rm).multiply(t)).round(oom, rm));
//        if (false) {
//            // Check if res is on the line.
//            if (!l.isIntersectedBy(res, oom, rm)) {
//                System.out.println("Not on line! - l.isIntersectedBy(r, oom, rm)");
//            }
//            if (!isIntersectedBy(res, oom, rm)) {
//                System.out.println("Not on plane! - !isIntersectedBy(r, oom, rm)");
//                // Check side of line
//                if (isOnSameSide(res, lp, oom, rm)) {
//                    System.out.println("isOnSameSide(res, lp, oom, rm)");
//                } else {
//                    System.out.println("!isOnSameSide(res, lp, oom, rm)");
//                    V3D_Plane pl2 = new V3D_Plane(tp, tr, tq, oom, rm);
//                    V3D_Point p2 = (V3D_Point) pl2.getIntersectiondel(l, oom, rm);
//                    if (!res.equals(p2, oom, rm)) {
//                        System.out.println(res);
//                        System.out.println(p2);
//                    } else {
//                        System.out.println(res);
//                    }
//                }
//                if (isOnSameSide(res, lq, oom, rm)) {
//                    System.out.println("isOnSameSide(res, lq, oom, rm)");
//                } else {
//                    System.out.println("!isOnSameSide(res, lq, oom, rm)");
//                }
//            }
//        }
//        return res;
//
////        V3D_Vector d = new V3D_Vector(this.pl, l.pl, n.oom);
////        //V3D_Vector d = new V3D_Vector(l.pl, pl, n.oom);
////        Math_BigRational num = d.getDotProduct(this.n);
////        Math_BigRational den = l.v.getDotProduct(this.n);
////        Math_BigRational t = num.divide(den);
////        return new V3D_Point(
////                l.pl.getX(oom).subtract(l.v.getDX(oom).multiply(t)),
////                l.pl.getY(oom).subtract(l.v.getDY(oom).multiply(t)),
////                l.pl.getZ(oom).subtract(l.v.getDZ(oom).multiply(t)));
//    }
    
    /**
     * Get the intersection between the geometry and the line {@code l}.
     *
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_Geometry getIntersection(V3D_Line l, int oom, RoundingMode rm) {
        int oomN2 = oom - 2;
        if (this.isParallel(l, oomN2, rm)) {
            if (this.isOnPlane(l, oomN2, rm)) {
                return l;
            } else {
                return null;
            }
        }
        // Are either of the points of l on the plane.
        //V3D_Point lp = l.getP(oom);
        V3D_Point lp = l.getP();
        if (this.isIntersectedBy(lp, oomN2, rm)) {
            return lp;
        }
        V3D_Point lq = l.getQ(oom, rm);
        if (this.isIntersectedBy(lq, oomN2, rm)) {
            return lq;
        }
        //V3D_Vector lv = l.getV(oomN2, rm);
        V3D_Vector lv = l.v;
        Math_BigRational[][] m = new Math_BigRational[4][4];
        m[0][0] = Math_BigRational.ONE;
        m[0][1] = Math_BigRational.ONE;
        m[0][2] = Math_BigRational.ONE;
        m[0][3] = Math_BigRational.ONE;
//        m[1][0] = pl.getX(oom);
//        m[1][1] = q.getX(oom);
//        m[1][2] = r.getX(oom);
//        m[1][3] = lp.getX(oom);
//        m[2][0] = pl.getY(oom);
//        m[2][1] = q.getY(oom);
//        m[2][2] = r.getY(oom);
//        m[2][3] = lp.getY(oom);
//        m[3][0] = pl.getZ(oom);
//        m[3][1] = q.getZ(oom);
//        m[3][2] = r.getZ(oom);
//        m[3][3] = lp.getZ(oom);
        V3D_Point tp = getP();
        V3D_Vector pv = getPV();
        V3D_Point tq = getQ(pv, oom, rm);
        V3D_Point tr = getR(pv, oom, rm);
        m[1][0] = tp.getX(oomN2, rm);
        m[1][1] = tq.getX(oomN2, rm);
        m[1][2] = tr.getX(oomN2, rm);
        m[1][3] = lp.getX(oomN2, rm);
        m[2][0] = tp.getY(oomN2, rm);
        m[2][1] = tq.getY(oomN2, rm);
        m[2][2] = tr.getY(oomN2, rm);
        m[2][3] = lp.getY(oomN2, rm);
        m[3][0] = tp.getZ(oomN2, rm);
        m[3][1] = tq.getZ(oomN2, rm);
        m[3][2] = tr.getZ(oomN2, rm);
        m[3][3] = lp.getZ(oomN2, rm);
        Math_Matrix_BR numm = new Math_Matrix_BR(m);
        m[0][0] = Math_BigRational.ONE;
        m[0][1] = Math_BigRational.ONE;
        m[0][2] = Math_BigRational.ONE;
        m[0][3] = Math_BigRational.ZERO;
//        m[1][0] = pl.getX(oom);
//        m[1][1] = q.getX(oom);
//        m[1][2] = r.getX(oom);
//        m[1][3] = lv.getDX(oom);
//        m[2][0] = pl.getY(oom);
//        m[2][1] = q.getY(oom);
//        m[2][2] = r.getY(oom);
//        m[2][3] = lv.getDY(oom);
//        m[3][0] = pl.getZ(oom);
//        m[3][1] = q.getZ(oom);
//        m[3][2] = r.getZ(oom);
//        m[3][3] = lv.getDZ(oom);
        m[1][0] = tp.getX(oomN2, rm);
        m[1][1] = tq.getX(oomN2, rm);
        m[1][2] = tr.getX(oomN2, rm);
        m[1][3] = lv.getDX(oomN2, rm);
        m[2][0] = tp.getY(oomN2, rm);
        m[2][1] = tq.getY(oomN2, rm);
        m[2][2] = tr.getY(oomN2, rm);
        m[2][3] = lv.getDY(oomN2, rm);
        m[3][0] = tp.getZ(oomN2, rm);
        m[3][1] = tq.getZ(oomN2, rm);
        m[3][2] = tr.getZ(oomN2, rm);
        m[3][3] = lv.getDZ(oomN2, rm);
        Math_Matrix_BR denm = new Math_Matrix_BR(m);
        Math_BigRational t = numm.getDeterminant().divide(denm.getDeterminant()).negate();
        V3D_Point res = new V3D_Point(
                lp.getX(oomN2, rm).add(lv.getDX(oomN2, rm).multiply(t)),
                lp.getY(oomN2, rm).add(lv.getDY(oomN2, rm).multiply(t)),
                lp.getZ(oomN2, rm).add(lv.getDZ(oomN2, rm).multiply(t)));
        return res;
    }

    /**
     * Get the intersection between the geometry and the line segment {@code l}.
     *
     * @param l The line segment to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment l, int oom, RoundingMode rm) {
        V3D_Geometry g = getIntersection(l.l, oom, rm);
        if (g == null) {
            return null;
        } else if (g instanceof V3D_Line) {
            return l;
        } else {
            V3D_Point pt = (V3D_Point) g;
            if (l.isIntersectedBy(pt, oom, rm)) {
                return pt;
            }
            return null;
        }
    }

    /**
     * https://www.microsoft.com/en-us/research/publication/intersection-of-two-planes/
     * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
     * https://math.stackexchange.com/questions/291289/find-the-point-on-the-line-of-intersection-of-the-two-planes-using-lagranges-me
     * http://tbirdal.blogspot.com/2016/10/a-better-approach-to-plane-intersection.html
     * https://mathworld.wolfram.com/Plane-PlaneIntersection.html
     *
     * @param pl The plane to intersect.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code this} and {@code pl}
     */
    public V3D_Geometry getIntersection(V3D_Plane pl, int oom, RoundingMode rm) {
        /**
         * Calculate the cross product of the normal vectors to get the
         * direction of the line.
         */
        //V3D_Vector v = pl.getN(oomN5).getCrossProduct(getN(oomN5), oomN5);
        //V3D_Vector v = getN(oom, rm).getCrossProduct(pl.getN(oom, rm), oom, rm);
        V3D_Vector v = n.getCrossProduct(pl.n, oom, rm);

        /**
         * Check special cases.
         */
        if (v.isZeroVector()) {
            // The planes are parallel.
            if (pl.equalsIgnoreOrientation(this, oom, rm)) {
                // The planes are the same.
                return this;
            }
            // There is no intersection.
            return null;
        }

//        /**
//         * The following is from:
//         * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
//         * But something is not right!
//         */
//        //function [pl, n] = intersect_planes(p1, n1, p2, n2, p0)
//        //M = [2 0 0 n1(1) n2(1)
//        //     0 2 0 n1(2) n2(2)
//        //     0 0 2 n1(3) n2(3)
//        //     n1(1) n1(2) n1(3) 0 0
//        //     n2(1) n2(2) n2(3) 0 0];
//        Math_BigRational[][] m = new Math_BigRational[5][6];
//        Math_BigRational P0 = Math_BigRational.ZERO;
//        Math_BigRational P2 = Math_BigRational.TWO;
//        m[0][0] = P2;
//        m[0][1] = P0;
//        m[0][2] = P0;
//        m[0][3] = n.getDX(oom, rm);
//        m[0][4] = pl.n.getDX(oom, rm);
//        m[1][0] = P0;
//        m[1][1] = P2;
//        m[1][2] = P0;
//        m[1][3] = n.getDY(oom, rm);
//        m[1][4] = pl.n.getDY(oom, rm);
//        m[2][0] = P0;
//        m[2][1] = P0;
//        m[2][2] = P2;
//        m[2][3] = n.getDZ(oom, rm);
//        m[2][4] = pl.n.getDZ(oom, rm);
//        m[3][0] = n.getDX(oom, rm);
//        m[3][1] = n.getDY(oom, rm);
//        m[3][2] = n.getDZ(oom, rm);
//        m[3][3] = P0;
//        m[3][4] = P0;
//        m[4][0] = pl.n.getDX(oom, rm);
//        m[4][1] = pl.n.getDY(oom, rm);
//        m[4][2] = pl.n.getDZ(oom, rm);
//        m[4][3] = P0;
//        m[4][4] = P0;
//        // b4 = p1(1).*n1(1) + p1(2).*n1(2) + p1(3).*n1(3);
//        Math_BigRational b4 = pl.p.getDX(oom, rm).multiply(n.getDX(oom, rm))
//                .add(pl.p.getDY(oom, rm).multiply(n.getDY(oom, rm)))
//                .add(pl.p.getDZ(oom, rm).multiply(n.getDZ(oom, rm)));
//        //System.out.println(b4);
//        // b5 = p2(1).*n2(1) + p2(2).*n2(2) + p2(3).*n2(3);
//        Math_BigRational b5 = pl.p.getDX(oom, rm).multiply(pl.n.getDX(oom, rm))
//                .add(pl.p.getDY(oom, rm).multiply(pl.n.getDY(oom, rm)))
//                .add(pl.p.getDZ(oom, rm).multiply(pl.n.getDZ(oom, rm)));
//        //System.out.println(b5);
//        // b = [2*p0(1) ; 2*p0(2) ; 2*p0(3); b4 ; b5];
//        m[0][5] = p.getDX(oom, rm).multiply(2);
//        m[1][5] = p.getDY(oom, rm).multiply(2);
//        m[2][5] = p.getDZ(oom, rm).multiply(2);
//        m[3][5] = b4;
//        m[4][5] = b5;
//        // x = M\b;
//        Math_Matrix_BR mm = new Math_Matrix_BR(m);
//        Math_Matrix_BR ref = mm.getReducedRowEchelonForm();
//        //System.out.println(ref);
//        // pl = x(1:3);
//        //???
//        // n = cross(n1, n2);
//        Math_BigRational[][] refrows = ref.getRows();
//        V3D_Point pt = new V3D_Point(e, refrows[0][5], refrows[1][5], refrows[2][5]);
//        return new V3D_Line(pt, v);
        /**
         * Find the intersection of a line in the plane that is not parallel to
         * v.
         */
//        V3D_Point pi;
//        V3D_Point tq = getQ();
//        if (getPQV().isScalarMultiple(v, oomN5)) {
//        //if (pl.getPQV().isScalarMultiple(v, oomN5)) {
//            //pi = (V3D_Point) pl.getIntersection(new V3D_Line(tq, getR(oom), oom), oom);
////            pi = (V3D_Point) pl.getIntersection(
////                    new V3D_Line(e, tq.getVector(oomN5), getR().getVector(oomN5)), oomN5);
////            pi = (V3D_Point) pl.getIntersection(
////                    new V3D_Line(tq.getVector(oomN5), getR().getVector(oomN5)), oomN5);
//            pi = (V3D_Point) pl.getIntersection(
//                    new V3D_Line(getP(), getR()), oomN5);
//        } else {
//            //pi = (V3D_Point) pl.getIntersection(new V3D_Line(getP(oom), tq, oom), oom);
////            pi = (V3D_Point) pl.getIntersection(
////                    new V3D_Line(e, getP().getVector(oomN5), getR().getVector(oomN5)), oomN5);
////            pi = (V3D_Point) getIntersection(
////                    new V3D_Line(e, pl.getP().getVector(oomN5), pl.getQ().getVector(oomN5)), oomN5);
//            pi = (V3D_Point) pl.getIntersection(
//                    new V3D_Line(getP(), getQ()), oomN5);
//        }
//
//        //if (pl.getPV().isScalarMultiple(v, oom, rm)) {
//        if (pl.getPV().getDotProduct(n, oom, rm) == Math_BigRational.ZERO) {
////            pi = (V3D_Point) getIntersection(
////                    new V3D_Line(pl.getP(), pl.getR(oom, rm), oom, rm), oom, rm);
//            return new V3D_Line(pl.getP(), pl.getR(oom, rm), oom, rm);
//        } else {
////            pi = (V3D_Point) getIntersection(
////                    new V3D_Line(pl.getP(), pl.getQ(oom, rm), oom, rm), oom, rm);
//            return new V3D_Line(pl.getP(), pl.getQ(oom, rm), oom, rm);
//        }
        V3D_Point pi = pl.getPointOfProjectedIntersection(getP(), oom, rm);
        return new V3D_Line(pi, v);
    }

//    /**
//     * From https://mathworld.wolfram.com/Plane-PlaneIntersection.html
//     * @param pl1
//     * @param pl2
//     * @param oom
//     * @param rm
//     * @return 
//     */
//    public V3D_Point getIntersection(V3D_Plane pl1, V3D_Plane pl2, int oom,
//            RoundingMode rm) {
//        Math_BigRational[][] m = new Math_BigRational[3][3];
//        m[0][0] = n.getDX(oom, rm);
//        m[1][0] = n.getDY(oom, rm);
//        m[2][0] = n.getDZ(oom, rm);
//        m[0][1] = pl1.n.getDX(oom, rm);
//        m[1][1] = pl1.n.getDY(oom, rm);
//        m[2][1] = pl1.n.getDZ(oom, rm);
//        m[0][2] = pl2.n.getDX(oom, rm);
//        m[1][2] = pl2.n.getDY(oom, rm);
//        m[2][2] = pl2.n.getDZ(oom, rm);        
//        Math_Matrix_BR mm = new Math_Matrix_BR(m);
//    )
    /**
     * The intersection of 3 planes could be a plane, a line, a point or null.
     * It is a plane if all 3 planes are the same. It is a line if two of the
     * planes intersect in a line and that line is on the third plane. It is a
     * point if two planes intersect in a line and that line also intersects the
     * third plane at a point.
     *
     * @param pl1 A plane to intersect.
     * @param pl2 A second plane top intersect.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code this} and {@code pl}
     */
    public V3D_Geometry getIntersection(V3D_Plane pl1, V3D_Plane pl2, int oom,
            RoundingMode rm) {
        V3D_Geometry g = getIntersection(pl1, oom, rm);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_Plane gp) {
                return gp.getIntersection(pl2, oom, rm);
            } else {
                return pl2.getIntersection((V3D_Line) g, oom, rm);
            }
        }
    }

//    private V3D_Geometry getIntersectionOld(V3D_Plane pl, int oom) {
//        /**
//         * Calculate the cross product of the normal vectors to get the
//         * direction of the line.
//         */
//        V3D_Vector v = n.getCrossProduct(pl.n, oom);
//        /**
//         * To find a point on the line find a vector (that defines a line) from
//         * each plane that is not parallel to v, then find their intersection.
//         */
//        if (v.isZeroVector()) {
//            // The planes are parallel.
//            if (pl.equals(this)) {
//                // The planes are the same.
//                return this;
//            }
//            // There is no intersection.
//            return null;
//        }
//        // Calculate the line of intersection, where v is the line vector.
//        // What to do depends on which elements of v are non-zero.
//        Math_BigRational P0 = Math_BigRational.ZERO;
//        if (v.dx.isZero()) {
//            if (v.dy.isZero()) {
//                Math_BigRational z = pl.n.getDX(oom).multiply(pl.pl.getX(oom)
//                        .subtract(pl.q.getX(oom))).add(n.getDY(oom).multiply(pl.pl.getY(oom)
//                        .subtract(pl.q.getX(oom)))).divide(v.getDZ(oom)).add(pl.pl.getZ(oom));
//                V3D_Point pt;
//                if (n.dx.isZero()) {
//                    pt = new V3D_Point(pl.pl.getX(oom), pl.getY(oom), z);
//                } else {
//                    pt = new V3D_Point(pl.getX(oom), pl.pl.getY(oom), z);
//                }
//                return new V3D_Line(pt, pt.tranlsate(v, oom), oom);
//                // The intersection is at z=?
//
//                /**
//                 * n.getDX(oom)(x(t)−pl.getX(oom))+n.getDY(oom)(y(t)−pl.getY(oom))+n.getDZ(oom)(z(t)−pl.getZ(oom))
//                 */
//                // where:
//                // n.getDX(oom) = a; n.getDY(oom) = b; n.getDZ(oom) = c
//                // pl.n.getDX(oom) = d; pl.n.getDY(oom) = e; pl.n.getDZ(oom) = f
//                // a(x−pl.getX(oom)) + b(y−pl.getY(oom)) + c(z−pl.getZ(oom)) = 0
//                // x = pl.getX(oom) + ((- b(y−pl.getY(oom)) - c(z−pl.getZ(oom))) / a)                     --- 1
//                // y = pl.getY(oom) + ((- a(x−pl.getX(oom)) - c(z−pl.getZ(oom))) / b)                     --- 2
//                // z = pl.getZ(oom) + ((- a(x−pl.getX(oom)) - b(y−pl.getY(oom))) / c)                     --- 3
//                // x = pl.pl.getX(oom) + ((- pl.b(y − pl.pl.getY(oom)) - pl.c(z − pl.pl.getZ(oom))) / d)  --- 4
//                // y = pl.pl.getY(oom) + ((- pl.a(x − pl.pl.getX(oom)) - pl.c(z − pl.pl.getZ(oom))) / e)  --- 5
//                // z = pl.pl.getZ(oom) + ((- pl.a(x − pl.pl.getX(oom)) - pl.b(y − pl.pl.getY(oom))) / f)  --- 6
//                // Let:
//                // pl.getX(oom) = k; pl.getY(oom) = l; pl.getZ(oom) = m
//                // x = k + ((b(l - y) - c(z − m)) / a)   --- 1t
//                // y = l + ((a(k - x) - c(z − l)) / b)   --- 2t
//                // z = m + ((a(k - x) - b(y − m)) / c)   --- 3t
//                // Let:
//                // pl.pl.getX(oom) = k; pl.pl.getY(oom) = l; pl.pl.getZ(oom) = m
//                // x = k + ((e(l - y) - f(z - m)) / d)   --- 1p
//                // y = l + ((d(k - x) - f(z - l)) / e)   --- 2p
//                // z = m + ((d(k - x) - e(y - m)) / f)   --- 3p
////                if (n.getDX(oom).compareTo(e.P0) == 0) {
////                    //pl.b
////                } else if (n.getDY(oom).compareTo(e.P0) == 0) {
//////                    // y = l + ((- a(x − k) - c(z − l)) / b)
////                    BigDecimal y = pl.getY(oom);
//////                    // x = k + ((e(l - y) - f(z - m)) / d)
//////                    // z = m + ((- d(x - k) - e(y - m)) / f)
//////                    BigDecimal x = pl.getX(oom).tranlsate(
//////                            Math_BigDecimal.divideRoundIfNecessary(
//////                                    (pl.n.getDY(oom).multiply(pl.getY(oom).subtract(y))).subtract(pl.n.getDZ(oom).multiply(z.subtract(pl.pl.getZ(oom)))),
//////                                    pl.n.getDX(oom), scale, rm));
////                } else {
////                    return e.zAxis;
////                }
////                //BigDecimal x = pl.getX(oom)
////                BigDecimal numerator = pl.getZ(oom).subtract(pl.getY(oom))
////                        .subtract(n.getDZ(oom).multiply(pl.getZ(oom)))
////                        .subtract(pl.getY(oom).multiply(n.getDY(oom)));
////                BigDecimal denominator = n.getDY(oom).subtract(n.getDZ(oom));
////                if (denominator.compareTo(e.P0) == 0) {
////                    // Case 1: The z axis
////                    return null;
////                } else {
////                    // y = (pl.getY(oom) - c(z−pl.getZ(oom))) / b   --- 1          
////                    // z = (pl.getZ(oom) - b(y−pl.getY(oom))) / c   --- 2
////                    // n.getDX(oom) = a; n.getDY(oom) = b; n.getDZ(oom) = c
////                    // Let:
////                    // pl.getX(oom) = k; pl.getY(oom) = l; pl.getZ(oom) = m
////                    // y = (l - c(z - m)) / b
////                    // z = (m - b(y - l)) / b
////                    // z = (m - b(((l - c(z - m)) / b) - l)) / b
////                    // bz = m - b(((l - c(z - m)) / b) - l)
////                    // bz = m - (l - c(z - m)) - lb
////                    // bz = m - l + cz - cm - lb
////                    // bz - cz = m - l -cm - lb
////                    // z(b - c) = m - l -cm - lb
////                    // z = (m - l -cm - lb) / (b - c)
////                    BigDecimal z = Math_BigDecimal.divideRoundIfNecessary(
////                            numerator, denominator, scale + 2, rm); // scale + 2 sensible?
////                    // Substitute into 1
////                    // y = (pl.getY(oom) - c(z−pl.getZ(oom))) / b   --- 1
////                    if (n.getDY(oom).compareTo(e.P0) == 0) {
////                        // Another case to deal with
////                        return null;
////                    } else {
////                        BigDecimal y = Math_BigDecimal.divideRoundIfNecessary(
////                                pl.getY(oom).subtract(n.getDZ(oom).multiply(z.subtract(pl.getZ(oom)))),
////                                n.getDY(oom), scale, rm);
////                        return new V3D_Line(new V3D_Point(e, pl.getX(oom), y, z),
////                                new V3D_Point(e, pl.getX(oom).multiply(e.N1), y.add(v.getDY(oom)), z.add(v.getDZ(oom))));
////                    }
////                }
//            } else {
//                if (v.dz.isZero()) {
//                    Math_BigRational y = n.getDX(oom).multiply(pl.getX(oom).subtract(q.getX(oom))).add(
//                            n.getDZ(oom).multiply(pl.getZ(oom).subtract(q.getX(oom))))
//                            .divide(v.getDY(oom)).add(pl.getY(oom));
//                    V3D_Point pt;
//                    if (n.getDX(oom).compareTo(P0) == 0) {
//                        pt = new V3D_Point(pl.pl.getX(oom), y, pl.getZ(oom));
//                    } else {
//                        pt = new V3D_Point(pl.getX(oom), y, pl.pl.getZ(oom));
//                    }
//                    return new V3D_Line(pt, pt.tranlsate(v, oom), oom);
//                } else {
//                    // Case 3
//                    // y = (pl.getY(oom) - c(z−pl.getZ(oom))) / b   --- 1          
//                    // z = (pl.getZ(oom) - b(y−pl.getY(oom))) / c   --- 2
//                    // n.getDX(oom) = a; n.getDY(oom) = b; n.getDZ(oom) = c
//                    // Let:
//                    // pl.getX(oom) = k; pl.getY(oom) = l; pl.getZ(oom) = m
//                    // y = (l - c(z - m)) / b
//                    // z = (m - b(y - l)) / b
//                    // z = (m - b(((l - c(z - m)) / b) - l)) / b
//                    // bz = m - b(((l - c(z - m)) / b) - l)
//                    // bz = m - (l - c(z - m)) - lb
//                    // bz = m - l + cz - cm - lb
//                    // bz - cz = m - l -cm - lb
//                    // z(b - c) = m - l -cm - lb
//                    // z = (m - l -cm - lb) / (b - c)
//                    Math_BigRational numerator = pl.getZ(oom).subtract(pl.getY(oom))
//                            .subtract(n.getDZ(oom).multiply(pl.getZ(oom)))
//                            .subtract(pl.getY(oom).multiply(n.getDY(oom)));
//                    Math_BigRational denominator = n.getDY(oom).subtract(n.getDZ(oom));
//                    if (denominator.compareTo(P0) == 0) {
//                        // Another case to deal with
//                        return null;
//                    } else {
//                        Math_BigRational z = numerator.divide(denominator);
//                        // Substitute into 1
//                        // y = (pl.getY(oom) - c(z−pl.getZ(oom))) / b   --- 1
//                        if (n.dy.isZero()) {
//                            // Another case to deal with
//                            return null;
//                        } else {
//                            Math_BigRational y = pl.getY(oom).subtract(n.getDZ(oom).multiply(z.subtract(pl.getZ(oom))))
//                                    .divide(n.getDY(oom));
//                            return new V3D_Line(
//                                    new V3D_Point(P0, y, z),
//                                    new V3D_Point(P0, y.add(v.getDY(oom)), z.add(v.getDZ(oom))), oom);
//                        }
//                    }
//                }
//            }
//        } else {
//            if (v.dy.isZero()) {
//                if (v.dz.isZero()) {
//                    Math_BigRational x = pl.n.getDY(oom).multiply(pl.pl.getY(oom).subtract(pl.q.getY(oom))).add(
//                            n.getDZ(oom).multiply(pl.pl.getZ(oom).subtract(pl.q.getY(oom))))
//                            .divide(v.getDX(oom)).add(pl.pl.getX(oom));
//                    V3D_Point pt;
//                    if (n.dy.isZero()) {
//                        if (n.dz.isZero()) {
//                            pt = new V3D_Point(x, pl.getY(oom), pl.pl.getZ(oom));
//                        } else {
//                            pt = new V3D_Point(x, pl.pl.getY(oom), pl.getZ(oom));
//                        }
//                    } else {
//                        if (n.dz.isZero()) {
//                            pt = new V3D_Point(x, pl.getY(oom), pl.pl.getZ(oom));
//                        } else {
//                            pt = new V3D_Point(x, pl.getY(oom), pl.pl.getZ(oom));
//                        }
//                    }
//                    return new V3D_Line(pt, pt.tranlsate(v, oom), oom);
//                } else {
//                    Math_BigRational z = pl.n.getDX(oom).multiply(pl.pl.getX(oom).subtract(pl.q.getX(oom))).add(
//                            n.getDY(oom).multiply(pl.pl.getY(oom).subtract(pl.q.getX(oom))))
//                            .divide(v.getDZ(oom)).add(pl.pl.getZ(oom));
//                    V3D_Point pt;
//                    if (n.dx.isZero()) {
//                        pt = new V3D_Point(pl.pl.getX(oom), pl.getY(oom), z);
//                    } else {
//                        pt = new V3D_Point(pl.getX(oom), pl.pl.getY(oom), z);
//                    }
//                    return new V3D_Line(pt, pt.tranlsate(v, oom), oom);
//                }
//            } else {
//                if (v.dz.isZero()) {
//                    // Case 6
//                    Math_BigRational y = n.getDX(oom).multiply(pl.getX(oom).subtract(q.getX(oom))).add(
//                            n.getDZ(oom).multiply(pl.getZ(oom).subtract(q.getX(oom))))
//                            .divide(v.getDY(oom)).add(pl.getY(oom));
//                    V3D_Point pt;
//                    if (pl.getX(oom).compareTo(P0) == 0) {
//                        pt = new V3D_Point(pl.getX(oom), y, pl.pl.getZ(oom)); // x=1 z=0
//                    } else {
//                        pt = new V3D_Point(pl.pl.getX(oom), y, pl.getZ(oom));
//                    }
//                    return new V3D_Line(pt, pt.tranlsate(v, oom), oom);
//                } else {
//                    /**
//                     * Case 7: Neither plane aligns with any axis and they are
//                     * not orthogonal.
//                     *
//                     * n.getDX(oom)(x(t)−pl.getX(oom))+n.getDY(oom)(y(t)−pl.getY(oom))+n.getDZ(oom)(z(t)−pl.getZ(oom))
//                     */
//                    // where:
//                    // n.getDX(oom) = a; n.getDY(oom) = b; n.getDZ(oom) = c
//                    // pl.n.getDX(oom) = d; pl.n.getDY(oom) = e; pl.n.getDZ(oom) = f
//                    // a(x−pl.getX(oom))+b(y−pl.getY(oom))+c(z−pl.getZ(oom)) = 0
//                    // x = (ap.getX(oom)-by+bp.getY(oom)-cz+cp.getZ(oom))/a
//                    // x = pl.getX(oom)+((b(pl.getY(oom)−y)+c(pl.getZ(oom)−z))/a)                    --- 1
//                    // y = pl.getY(oom)+((a(pl.getX(oom)−x)+c(pl.getZ(oom)−z))/b)                    --- 2
//                    // z = pl.getZ(oom)+((a(pl.getX(oom)−x)+b(pl.getY(oom)−y))/c)                    --- 3
//                    // x = pl.pl.getX(oom)+((pl.b(pl.pl.getY(oom)−y)+pl.c(pl.pl.getZ(oom)−z))/pl.a)  --- 4
//                    // y = pl.pl.getY(oom)+((pl.a(pl.pl.getX(oom)−x)+pl.c(pl.pl.getZ(oom)−z))/pl.b)  --- 5
//                    // z = pl.pl.getZ(oom)+((pl.a(pl.pl.getX(oom)−x)+pl.b(pl.pl.getY(oom)−y))/pl.c)  --- 6
//                    // Let:
//                    // pl.getX(oom) = g; pl.getY(oom) = h; pl.getZ(oom) = i
//                    // x = g+((b(h−y)+c(i−z))/a)                    --- 1p
//                    // y = h+((a(g−x)+c(i−z))/b)                    --- 2p
//                    // z = i+((a(g−x)+b(h−y))/c)                    --- 3p
//                    // Let:
//                    // pl.pl.getX(oom) = j; pl.pl.getY(oom) = k; pl.pl.getZ(oom) = l
//                    // x = j+((e(k−y)+f(l−z))/d)                    --- 1pl
//                    // y = k+((d(j−x)+f(l−z))/e)                    --- 2pl
//                    // z = l+((d(j−x)+e(k−y))/f)                    --- 3pl
//                    // Stage 1: express each coordinate in terms of another
//                    // sub 1p into 2pl:
//                    // y = k+((d(j−(g+((b(h−y)+c(i−z))/a)))+f(l−z))/e)
//                    // ey-ek = d(j−(g+((b(h−y)+c(i−z))/a)))+f(l−z)
//                    // ey-ek = d(j−(g+((b(h−y)+c(i−z))/a)))+fl−fz
//                    // ey-ek = dj−dg-dbh/a+dby/a-dci/a+dcz/a+fl−fz                    
//                    // ey-dby/a = dj−dg-dbh/a-dci/a+dcz/a+fl−fz+ek
//                    // y = (dj−dg-dbh/a-dci/a+dcz/a+fl−fz+ek)/(e-db/a)  ---12 y ito z
//                    // ey-dby/a = dj−dg-dbh/a-dci/a+dcz/a+fl−fz+ek
//                    // ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek = z(dc/a-f)
//                    // z = (ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek)/(dc/a-f)  ---12 z ito y                    
//                    // sub 1p into 3pl
//                    // z = l+((d(j−(g+((b(h−y)+c(i−z))/a)))+e(k−y))/f)
//                    // fz-fl = d(j−(g+((b(h−y)+c(i−z))/a)))+ek−ey
//                    // fz-dcz/a = dj−dg-dbh/a+dby/a-dci/a+ek−ey+fl
//                    // z = (dj−dg-dbh/a+dby/a-dci/a+ek−ey+fl)/(f-dc/a)  ---13 z ito y 
//                    // fz-dcz/a = dj−dg-dbh/a+dby/a-dci/a+ek−ey+fl
//                    // fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl= dby/a−ey
//                    // y = (fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e)  ---13 y ito z
//                    // sub 2p into 1pl:
//                    // x = j+((e(k−(h+((a(g−x)+c(i−z))/b)))+f(l−z))/d)
//                    // dx-dj = e(k−(h+((a(g−x)+c(i−z))/b)))+fl−fz
//                    // dx-dj = ek−eh-eag/b+eax/b-eci/b+ecz/b+fl−fz
//                    // x = (ek−eh-eag/b-eci/b+ecz/b+fl−fz-dj)/(d-ea/b)  ---21 x ito z    
//                    // dx-dj = ek−eh-eag/b+eax/b-eci/b+ecz/b+fl−fz
//                    // z = (dx-dj-ek+eh+eag/b-eax/b+eci/b-fl)/(ec/b−f)  ---21 z ito x
//                    // sub 2p into 3pl
//                    // z = l+((d(j−x)+e(k−(h+((a(g−x)+c(i−z))/b))))/f)
//                    // fz-fl = dj−dx+ek−eh-eag/b+eax/b-eci/b+ecz/b
//                    // z = (dj−dx+ek−eh-eag/b+eax/b-eci/b+fl)/(f-ec/b)  ---23 z ito x
//                    // fz-fl = dj−dx+ek−eh-eag/b+eax/b-eci/b+ecz/b
//                    // x = (fz-fl-dj-ek+eh+eag/b+eci/b-ecz/b)/(ea/b-d)  ---23 x ito z
//                    // sub 3p into 1pl
//                    // x = j+((e(k−y)+f(l−(i+((a(g−x)+b(h−y))/c))))/d)
//                    // dx-dj = ek−ey+f(l−(i+((a(g−x)+b(h−y))/c)))                    
//                    // dx-dj = ek−ey+fl−fi+fag/c−fax/c+fbh/c−fby/c
//                    // x = (dj+ek−ey+fl−fi+fag/c+fbh/c−fby/c)/(d+fa/c)
//                    // x = (ek−ey+fl-fi-fag/c-fbh/c+fb)/c+dj)/(d-fa/c)  ---31 x ito y
//                    // dx-dj = ek−ey+fl−fi+fag/c−fax/c+fbh/c−fby/c                    
//                    // y = (ek+fl−fi+fag/c−fax/c+fbh/c-dx+dj)/(e+fb/c)  ---31 y ito x 
//                    // sub 3p into 2pl
//                    // y = k+((d(j−x)+f(l−(i+((a(g−x)+b(h−y))/c))))/e)
//                    // ey-ek = dj−dx+fl−fi-fag/c+fax/c-fbh/c+fby/c
//                    // y = (ek+dj−dx+fl−fi-fag/c+fax/c-fbh/c)/(e-fb/c)  ---32 y ito x
//                    // ey-ek = dj−dx+fl−fi-fag/c+fax/c-fbh/c+fby/c
//                    // x = (ey-ek-dj-fl+fi+fag/c+fbh/c-fby/c)/(fa/c−d)  ---32 x ito y
//                    // Stage 2: Are any denominators 0?
//                    Math_BigRational x = n.getDY(oom).multiply(pl.pl.getY(oom).subtract(pl.q.getY(oom))).add(
//                            n.getDZ(oom).multiply(pl.pl.getZ(oom).subtract(pl.q.getY(oom))))
//                            .divide(v.getDX(oom)).add(pl.pl.getX(oom));
//                    Math_BigRational y = n.getDX(oom).multiply(pl.getX(oom).subtract(q.getX(oom))).add(
//                            n.getDZ(oom).multiply(pl.getZ(oom).subtract(q.getX(oom))))
//                            .divide(v.getDY(oom)).add(pl.getY(oom));
//                    Math_BigRational z = n.getDX(oom).multiply(pl.pl.getX(oom).subtract(pl.q.getX(oom))).add(
//                            n.getDY(oom).multiply(pl.pl.getY(oom).subtract(pl.q.getX(oom))))
//                            .divide(v.getDZ(oom)).add(pl.pl.getZ(oom));
//                    V3D_Point pt = new V3D_Point(x, y, z);
//                    return new V3D_Line(pt, pt.tranlsate(v, oom), oom);
//
////                    BigDecimal a = n.getDX(oom);
////                    BigDecimal b = n.getDY(oom);
////                    BigDecimal c = n.getDZ(oom);
////                    BigDecimal d = pl.n.getDX(oom);
////                    BigDecimal e = pl.n.getDY(oom);
////                    BigDecimal f = pl.n.getDZ(oom);
////                    BigDecimal db = d.multiply(b);
////                    BigDecimal dc = d.multiply(c);
////                    BigDecimal ea = e.multiply(a);
////                    BigDecimal ec = e.multiply(c);
////                    BigDecimal ef = e.multiply(f);
////                    BigDecimal fa = f.multiply(a);
////                    BigDecimal fb = f.multiply(b);
////                    BigDecimal db_div_a = Math_BigDecimal.divideRoundIfNecessary(db, a, scale, rm);
////                    BigDecimal dc_div_a = Math_BigDecimal.divideRoundIfNecessary(dc, a, scale, rm);
////                    BigDecimal ea_div_b = Math_BigDecimal.divideRoundIfNecessary(ea, b, scale, rm);
////                    BigDecimal ec_div_b = Math_BigDecimal.divideRoundIfNecessary(ec, b, scale, rm);
////                    BigDecimal fa_div_c = Math_BigDecimal.divideRoundIfNecessary(fa, c, scale, rm);
////                    BigDecimal fb_div_c = Math_BigDecimal.divideRoundIfNecessary(fb, c, scale, rm);
////                    BigDecimal denom12yitoz = e.subtract(db_div_a);
////                    BigDecimal denom12zitoy = dc_div_a.subtract(f);
////                    BigDecimal denom13zitoy = f.subtract(dc_div_a);
////                    BigDecimal denom13yitoz = db_div_a.subtract(e);
////                    BigDecimal denom21xitoz = d.subtract(ea_div_b);
////                    BigDecimal denom21zitox = ec_div_b.subtract(f);
////                    BigDecimal denom23zitox = f.subtract(ec_div_b);
////                    BigDecimal denom23xitoz = ea_div_b.subtract(d);
////                    BigDecimal denom31xitoy = d.subtract(fa_div_c);
////                    BigDecimal denom31yitox = e.add(fb_div_c);
////                    BigDecimal denom32yitox = e.subtract(fb_div_c);
////                    BigDecimal denom32xitoy = fa_div_c.subtract(d);
////                    // Solve for z
////                    // Let; n = v.getDX(oom); o = v.getDY(oom); pl = v.getDZ(oom)
////                    // x(t) = x + v.getDX(oom) 
////                    // y(t) = y(0) + v.getDY(oom) 
////                    // z-pl = z(0)
////                    // z = (ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek)/(dc/a-f)  ---12 z ito y                    
////                    // z-pl = (ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek)/(dc/a-f)
////                    // y = (fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e)  ---13 y ito z
////                    // z-pl = (e((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))-db((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))/a-dj-dg+dbh/a+dci/a-fl-ek)/(dc/a-f)
////                    // (z-pl)(dc/a-f) = e((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))-db((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))/a-dj-dg+dbh/a+dci/a-fl-ek
////                    // (efz-edcz/a-edj+edg+edbh/a+edci/a-eek-efl)/(db/a-e) = -dbfz/(db-ae)+dbdcz/(adb-aae)+ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek
////                    // efz/(db/a-e)-edcz/(db-ae)-edj/(db/a-e)+edg/(db/a-e)+edbh/(db-ae)+edci/(db-ae)-eek/(db/a-e)-efl/db/a-e) = -dbfz/(db-ae)+dbdcz/(adb-aae)+ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek
////                    // efz/(db/a-e)-edcz/(db-ae)+dbfz/(db-ae)-dbdcz/(adb-aae) = ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek+edj/(db/a-e)-edg/(db/a-e)-edbh/(db-ae)-edci/(db-ae)+eek/(db/a-e)+efl/db/a-e)
////                    // z(ef/(db/a-e)-edc/(db-ae)+dbf/(db-ae)-dbdc/(adb-aae)) = ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek+edj/(db/a-e)-edg/(db/a-e)-edbh/(db-ae)-edci/(db-ae)+eek/(db/a-e)+efl/db/a-e)
////                    // z = num/den
////                    // den = ef/(db/a-e)-edc/(db-ae)+dbf/(db-ae)-dbdc/(adb-aae);
////                    // num = ddj/(db-ae)-ddg/(db-ae)-ddbh/(adb-aae)-ddci/(adb-aae)+dek/(db-ae)+dfl/(db-ae)-dj-dg+dbh/a+dci/a-fl-ek+edj/(db/a-e)-edg/(db/a-e)-edbh/(db-ae)-edci/(db-ae)+eek/(db/a-e)+efl/db/a-e)
////                    // Let: q = db-ae; r = db/a-e; s=adb-aae
////                    BigDecimal q = db.subtract(a.multiply(e));
////                    BigDecimal r = db_div_a.subtract(e);
////                    BigDecimal s = db.multiply(a).subtract(a.multiply(ea));
////                    BigDecimal den = Math_BigDecimal.divideRoundIfNecessary(ef, r, scale, rm)
////                            .subtract(Math_BigDecimal.divideRoundIfNecessary(e.multiply(dc), q, scale, rm))
////                            .add(Math_BigDecimal.divideRoundIfNecessary(db.multiply(f), q, scale, rm))
////                            .subtract(Math_BigDecimal.divideRoundIfNecessary(d.multiply(db.multiply(c)), s, scale, rm));
////                    BigDecimal g = pl.getX(oom);
////                    BigDecimal h = pl.getY(oom);
////                    BigDecimal i = pl.getZ(oom);
////                    BigDecimal j = pl.pl.getX(oom);
////                    BigDecimal k = pl.pl.getY(oom);
////                    BigDecimal l = pl.pl.getZ(oom);
////                    BigDecimal ek = e.multiply(k);
////                    BigDecimal ci = c.multiply(i);
////                    BigDecimal dg = d.multiply(g);
////                    BigDecimal dbh = db.multiply(h);
////                    BigDecimal dbh_sub_dci = dbh.subtract(d.multiply(ci));
////                    BigDecimal fl = f.multiply(l);
////                    BigDecimal bh = b.multiply(h);
////                    BigDecimal dj = d.multiply(j);
////                    BigDecimal num = d.multiply(j.subtract(dg).add(ek).add(fl))
////                            .subtract(Math_BigDecimal.divideRoundIfNecessary(e.multiply(dbh_sub_dci), q, scale, rm))
////                            .subtract(Math_BigDecimal.divideRoundIfNecessary(d.multiply(dbh_sub_dci), s, scale, rm))
////                            .subtract(dj.subtract(dg))
////                            .add(Math_BigDecimal.divideRoundIfNecessary(d.multiply(bh.add(ci)), a, scale, rm))
////                            .subtract(fl.subtract(ek))
////                            .add(Math_BigDecimal.divideRoundIfNecessary(e.multiply(dj.subtract(dg).add(ek).add(fl)), r, scale, rm));
////                    BigDecimal z = Math_BigDecimal.divideRoundIfNecessary(num, den, scale, rm);
////                    // y = (dj−dg-dbh/a-dci/a+dcz/a+fl−fz+ek)/(e-db/a)  ---12 y ito z
////                    // y = num/den
////                    num = dj.subtract(dg).subtract(db_div_a.multiply(h))
////                            .subtract(dc_div_a.multiply(i))
////                            .add(dc_div_a.multiply(z)).add(fl).subtract(f.multiply(z)).add(ek);
////                    den = e.subtract(db_div_a);
////                    BigDecimal y = Math_BigDecimal.divideRoundIfNecessary(num, den, scale, rm);
////                    // x = (ek−eh-eag/b-eci/b+ecz/b+fl−fz-dj)/(d-ea/b)  ---21 x ito z
////                    BigDecimal e_div_b = Math_BigDecimal.divideRoundIfNecessary(e, b, scale, rm);
////                    num = ek.subtract(e.multiply(h))
////                            .subtract(g.multiply(a).multiply(e_div_b))
////                            .subtract(ci.multiply(e_div_b))
////                            .add(dc_div_a.multiply(z))
////                            .add(c.multiply(z).multiply(e_div_b))
////                            .add(fl)
////                            .subtract(f.multiply(z))
////                            .subtract(dj);
////                    den = d.subtract(a.multiply(e_div_b));
////                    BigDecimal x = Math_BigDecimal.divideRoundIfNecessary(num, den, scale, rm);
////                    V3D_Point aPoint = new V3D_Point(this.e, x, y, z);
////                    return new V3D_Line(aPoint, aPoint.tranlsate(v));
//                }
//            }
//        }
//        // This is where the two equations of the plane are equal.
//        // n.getDX(oom)(x(t)−pl.getX(oom))+n.getDY(oom)(y(t)−pl.getY(oom))+n.getDZ(oom)(z(t)−pl.getZ(oom))
//        // where:
//        // n.getDX(oom) = a; n.getDY(oom) = b; n.getDZ(oom) = c
//        // a(x−pl.getX(oom)) + b(y−pl.getY(oom)) + c(z−pl.getZ(oom)) = 0
//        // x = (pl.getX(oom) - b(y−pl.getY(oom)) - c(z−pl.getZ(oom))) / a                     --- 1
//        // y = (pl.getY(oom) - a(x−pl.getX(oom)) - c(z−pl.getZ(oom))) / b                     --- 2
//        // z = (pl.getZ(oom) - a(x−pl.getX(oom)) - b(y−pl.getY(oom))) / c                     --- 3
//        // x = (pl.pl.getX(oom) - pl.b(y−pl.pl.getY(oom)) - pl.c(z−pl.pl.getZ(oom))) / pl.a   --- 4
//        // y = (pl.pl.getY(oom) - pl.a(x−pl.pl.getX(oom)) - pl.c(z−pl.pl.getZ(oom))) / pl.b   --- 5
//        // z = (pl.pl.getZ(oom) - pl.a(x−pl.pl.getX(oom)) - pl.b(y−pl.pl.getY(oom))) / pl.c   --- 6
//        // Sub 2 and 3 into
//    }
    /**
     * @param p The plane to test if it is parallel to this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is parallel to {@code pl}.
     */
    public boolean isParallel(V3D_Plane p, int oom, RoundingMode rm) {
        return n.isScalarMultiple(p.n, oom, rm);
    }

    /**
     * @param l The line to test if it is parallel to this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is parallel to {@code l}.
     */
    public boolean isParallel(V3D_Line l, int oom, RoundingMode rm) {
        if (n.isOrthogonal(l.v, oom, rm)) {
            return true;
        }
        return n.getDotProduct(l.v, oom, rm).isZero();
    }

    /**
     * Planes are equal if their normal perpendicular vectors point in the same
     * direction, and their normals are scalar multiples, and the point of each
     * plane is on the other.
     *
     * @param pl The plane to check for equality with {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} and {@code pl} are the same.
     */
    public boolean equals(V3D_Plane pl, int oom, RoundingMode rm) {
        if (n.getDotProduct(pl.n, oom, rm).compareTo(Math_BigRational.ZERO) == 1) {
            return equalsIgnoreOrientation(pl, oom, rm);
        }
        return false;
    }

    /**
     * Planes are equal if their normals are scalar multiples, and the point of
     * each plane is on the other.
     *
     * @param pl The plane to check for equality with {@code this}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} and {@code pl} are the same.
     */
    public boolean equalsIgnoreOrientation(V3D_Plane pl, int oom, RoundingMode rm) {
        if (n.isScalarMultiple(pl.n, oom, rm)) {
            if (pl.isIntersectedBy(getP(), oom, rm)) {
                if (this.isIntersectedBy(pl.getP(), oom, rm)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The points that define the plane as a matrix.
     */
    public Math_Matrix_BR getAsMatrix(int oom, RoundingMode rm) {
        V3D_Point tp = getP();
        V3D_Vector pv = getPV();
        V3D_Point tq = getQ(pv, oom, rm);
        V3D_Point tr = getR(pv, oom, rm);
        Math_BigRational[][] m = new Math_BigRational[3][3];
        m[0][0] = tp.getX(oom, rm);
        m[0][1] = tp.getY(oom, rm);
        m[0][2] = tp.getZ(oom, rm);
        m[1][0] = tq.getX(oom, rm);
        m[1][1] = tq.getY(oom, rm);
        m[1][2] = tq.getZ(oom, rm);
        m[2][0] = tr.getX(oom, rm);
        m[2][1] = tr.getY(oom, rm);
        m[2][2] = tr.getZ(oom, rm);
        return new Math_Matrix_BR(m);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The distance from {@code this} to {@code pl}.
     */
    public BigDecimal getDistance(V3D_Point pt, int oom, RoundingMode rm) {
        if (this.isIntersectedBy(pt, oom, rm)) {
            return BigDecimal.ZERO;
        }
//        MathContext mc = new MathContext(Math_Math_BigRationalSqrt
//                .getOOM(Math_BigRational.ONE, oom));
        MathContext mc = new MathContext(6 - oom);
        return new Math_BigRationalSqrt(getDistanceSquared(pt, true, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(mc);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The distance from {@code this} to {@code pl}.
     */
    public Math_BigRational getDistanceSquared(V3D_Point pt, int oom, RoundingMode rm) {
        if (this.isIntersectedBy(pt, oom, rm)) {
            return Math_BigRational.ZERO;
        }
        return getDistanceSquared(pt, true, oom, rm);
    }

//    /**
//     * Get the distance between this and {@code pl}. Nykamp DQ, “Distance from
//     * point to plane.” From Math Insight.
//     * http://mathinsight.org/distance_point_plane
//     *
//     * @param pl A plane.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode.
//     * @return The distance from {@code p} to this.
//     */
//    public Math_BigRational getDistanceSquared(V3D_Point pt, int oom,
//            RoundingMode rm) {
//        //V3D_Vector pq = new V3D_Vector(this, pl.p, oom);
//        //V3D_Vector pq = pl.p.subtract(this.getVector(oom), oom);
//        V3D_Vector pq = getP().getVector(oom, rm).subtract(
//                pt.getVector(oom, rm), oom, rm);
//        //if (pq.isScalarMultiple(pl.getN(oom, rm), oom, rm)) {
//        if (pq.isScalarMultiple(n, oom, rm)) {
//            return pq.getMagnitudeSquared();
//        } else {
//            Math_BigRational[] coeffs = getEquationCoefficients(oom, rm);
//            Math_BigRational num = (coeffs[0].multiply(pt.getX(oom, rm))
//                    .add(coeffs[1].multiply(pt.getY(oom, rm)))
//                    .add(coeffs[2].multiply(pt.getZ(oom, rm)))
//                    .add(coeffs[3])).abs();
//            Math_BigRational den = coeffs[0].pow(2).add(coeffs[1].pow(2))
//                    .add(coeffs[2].pow(2));
//            return num.divide(den).round(oom, rm);
//        }
//    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pt A point.
     * @param noInt To distinguish this from
     * {@link #getDistanceSquared(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int, java.math.RoundingMode)}
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The distance from {@code this} to {@code pl}.
     */
    public Math_BigRational getDistanceSquared(V3D_Point pt, boolean noInt, int oom, RoundingMode rm) {
        V3D_Vector v = new V3D_Vector(pt, getP(), oom, rm);
        //V3D_Vector u = getN(oom, rm).getUnitVector(oom, rm);
        V3D_Vector u = n.getUnitVector(oom, rm);
        return v.getDotProduct(u, oom, rm).pow(2);
    }
    
    /**
     * Get the minimum distance to {@code p}.
     *
     * @param p A plane.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code p}.
     */
    public BigDecimal getDistance(V3D_Plane p, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    /**
     * The planes are either parallel or the distance is zero. If parallel, get
     * any point on one and find the distance squared of the other plane from
     * it.
     *
     * @param pl The other plane used to calculate the distance.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The shortest distance between {@code this} and {@code pl}.
     */
    public Math_BigRational getDistanceSquared(V3D_Plane pl, int oom, RoundingMode rm) {
        if (isParallel(pl, oom, rm)) {
            return pl.getDistanceSquared(getP(), oom, rm);
        }
        return Math_BigRational.ZERO;
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigDecimal getDistance(V3D_Line l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt().toBigDecimal(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom, RoundingMode rm) {
        return getDistanceSquared(l.getP(), true, oom, rm);
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigDecimal getDistance(V3D_LineSegment l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt(oom, rm).toBigDecimal(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom, RoundingMode rm) {
        Math_BigRational lpd = getDistanceSquared(l.getP(), oom, rm);
        Math_BigRational lqd = getDistanceSquared(l.getQ(), oom, rm);
        return lpd.min(lqd);
    }

    /**
     * Change {@link #offset} without changing the overall plane.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param offset What {@link #offset} is set to.
     */
    public void setOffset(V3D_Vector offset, int oom, RoundingMode rm) {
        if (!this.offset.equals(offset)) {
            p = p.add(this.offset, oom, rm).subtract(offset, oom, rm);
//            q = q.add(this.offset, oom, rm).subtract(offset, oom, rm);
//            r = r.add(this.offset, oom, rm).subtract(offset, oom, rm);
            this.offset = offset;
        }
    }

//    @Override
//    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
//        p = p.add(v, oom, rm);
////        q = q.add(v, oom, rm);
////        r = r.add(v, oom, rm);
//    }

    @Override
    public V3D_Plane rotate(V3D_Line axis, Math_BigRational theta, 
            int oom, RoundingMode rm) {
        return new V3D_Plane(getP().rotate(axis, theta, oom, rm),
            n.rotate(axis.v.getUnitVector(oom, rm), theta, oom, rm));
    }

    /**
     * Compute a return the line of intersection between {@code pt} and
     * {@code this}. See also:
     * {@code https://stackoverflow.com/questions/23472048/projecting-3d-points-to-2d-plane}
     *
     * @param pt The point which when projected onto the plane using the normal
     * to the plane forms the end of the returned line of intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The line of intersection between {@code pt} and {@code this} or
     * {@code null} if {@code pt} is on {@code this}. The point pl on the result
     * is the point of intersection
     */
    public V3D_Point getPointOfProjectedIntersection(V3D_Point pt, int oom, RoundingMode rm) {
        if (isIntersectedBy(pt, oom, rm)) {
            return pt;
        }
//        V3D_Point pt2 = new V3D_Point(pt);
//        pt2.setOffset(offset, oom, rm);
//        V3D_Vector nn = getN(oom, rm);
//        V3D_Line l = new V3D_Line(pt2.rel, nn, e);
        //V3D_Line l = new V3D_Line(pt2.rel, n, e);
        V3D_Line l = new V3D_Line(pt, n);
        return (V3D_Point) getIntersection(l, oom, rm);
    }

    /**
     * Check a and b are on the same side of this. If either are on the boundary
     * then return {@code true}.
     *
     * @param a A point.
     * @param b Another point. The triangle to check the points to see if they
     * are all on the same side of a line that intersects the edge of another
     * triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return {@code true} if an intersection is found and {@code false}
     * otherwise.
     */
    public boolean isOnSameSide(V3D_Point a, V3D_Point b, int oom, RoundingMode rm) {
        //n = getN(oom, rm);
        boolean aq = false;
        V3D_Vector av = a.getVector(oom, rm);
        V3D_Vector pv = getP().getVector(oom, rm);
        V3D_Vector avpv = pv.subtract(av, oom, rm);
        if (avpv.isZeroVector()) {
            pv = getQ(pv, oom, rm).getVector(oom, rm);
            avpv = pv.subtract(av, oom, rm);
            aq = true;
        }
        int avd = n.getDotProduct(avpv, oom, rm).compareTo(Math_BigRational.ZERO);
        if (avd == 0) {
            return true;
        }        
        boolean bq = false;
        V3D_Vector bv = b.getVector(oom, rm);
        V3D_Vector bvpv = pv.subtract(bv, oom, rm);
        if (bvpv.isZeroVector()) {
            pv = getQ(pv, oom, rm).getVector(oom, rm);
            bvpv = pv.subtract(bv, oom, rm);
            bq = true;
        }
        int bvd = n.getDotProduct(bvpv, oom, rm).compareTo(Math_BigRational.ZERO);
        if (bvd == 0) {
            return true;
        }
        if (aq && bq) {
            avpv = pv.subtract(av, oom, rm);
            avd = n.getDotProduct(avpv, oom, rm).compareTo(Math_BigRational.ZERO);
        }
        return avd == bvd;
        
//        //int avd = n.getDotProduct(av.add(this.pl.reverse(), oom), oom).compareTo(Math_BigRational.ZERO);
//        int avd = n.getDotProduct(pv.subtract(av, oom, rm), oom, rm).compareTo(Math_BigRational.ZERO);
//        //int avd = this.pl.add(av.reverse(), oom).getDotProduct(n, oom).compareTo(Math_BigRational.ZERO);
//        
//        V3D_Vector bv = b.getVector(oom, rm);
//        int bvd = n.getDotProduct(pv.subtract(bv, oom, rm), oom, rm).compareTo(Math_BigRational.ZERO);
//
//        return avd == bvd;

//        if (avd == 0) {
//            return true;
//        } else {
//            V3D_Vector bv = b.getVector(oom, rm);
//            int bvd = n.getDotProduct(pv.subtract(bv, oom, rm), oom, rm).compareTo(Math_BigRational.ZERO);
//            //int bvd = this.pl.add(bv.reverse(), oom).getDotProduct(n, oom).compareTo(Math_BigRational.ZERO);
//            if (bvd == 0) {
//                return true;
//            }
//            return avd == bvd;
//        }
    }

    /**
     * Calculate a vector v from a point in this to pt so that v is not the zero
     * vector. Calculate the dot product of v and the normal to the plane. If
     * this is positive, then the point P is on same side of the plane that the
     * normal points towards. If the dot product is 0, then pt lies on the
     * plane. Otherwise, pt is on the other side.
     *
     * @param pt The point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return 1 if pt is on the same side of the plane that the normal points
     * towards. 0 if pt is on the plane. -1 if pt is on the other side of the
     * plane that the normal points towards.
     */
    public int getSideOfPlane(V3D_Point pt, int oom, RoundingMode rm) {
        //V3D_Vector v = pt.rel.subtract(getPQV(oom, rm), oom, rm);
        V3D_Vector pv = getPV();
        V3D_Vector v = pt.rel.subtract(pv, oom, rm);
        if (v.isZeroVector()) {
            v = pt.rel.subtract(pv.getCrossProduct(n, oom, rm), oom, rm);
//            if (v.isZeroVector()) {
//                v = pt.rel.subtract(getRPV(), e.oom);                
//            }
        }
        //return v.getDotProduct(getN(oom, rm), oom, rm).compareTo(Math_BigRational.ZERO);
        return v.getDotProduct(n, oom, rm).compareTo(Math_BigRational.ZERO);
    }

    /**
     * Check if all points in pts are on the same side of this. Points on this
     * count either way.
     *
     * @param pts The points to check.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return {@code true} iff all points in pts are on or are on the same side
     * of this.
     */
    protected boolean allOnSameSide(V3D_Point[] pts,
            int oom, RoundingMode rm) {
        // Find a point not on the plane if there is one.
        V3D_Point pt = null;
        for (var x : pts) {
            if (isIntersectedBy(x, oom, rm)) {
                pt = x;
            }
        }
        // If there is not a point in pts that is not on the plane, then returnone, then
        if (pt == null) {
            return true;
        }
        boolean res = true;
        for (var x : pts) {
            if (!isOnSameSide(x, pt, oom, rm)) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     *
     * @param pl A plane parallel to this.
     * @param pt A point to check if it lies on or between the parallel planes.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return true if the point is on or between the parallel planes.
     */
    public boolean isBetweenPlanes(V3D_Plane pl, V3D_Point pt, int oom, RoundingMode rm) {
        if (pl.isOnSameSide(getP(), pt, oom, rm)) {
            return this.isOnSameSide(pl.getP(), pt, oom, rm);
        }
        return false;
    }
}
