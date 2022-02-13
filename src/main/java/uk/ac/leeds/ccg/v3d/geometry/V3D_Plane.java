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
import java.util.Objects;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * 3D representation of an infinite plane. The plane is defined by three points
 * {@link #p}, {@link #q} and {@link #r} that are not collinear. A plane be
 * constructed in numerous ways including with a vector perpendicular to the
 * plane and a point on the plane.
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
 * <li>{@code A} is the {@code dx} of the vector perpendicular to the
 * plane.</li>
 * <li>{@code B} is the {@code dy} of the vector perpendicular to the
 * plane.</li>
 * <li>{@code C} is the {@code dz} of the vector perpendicular to the
 * plane.</li>
 * </ul>
 *
 * <ol>
 * <li>{@code n.getDX(oom)(x(t)−p.getX(oom))+n.getDY(oom)(y(t)−p.getY(oom))+n.getDZ(oom)(z(t)−p.getZ(oom)) = 0}</li>
 * <li>{@code n.getDX(oom)(x(t)−q.getX(oom))+n.getDY(oom)(y(t)−q.getY(oom))+n.getDZ(oom)(z(t)−q.getZ(oom)) = 0}</li>
 * <li>{@code n.getDX(oom)(x(t)−r.getX(oom))+n.getDY(oom)(y(t)−r.getY(oom))+n.getDZ(oom)(z(t)−r.getZ(oom)) = 0}</li>
 * </ol>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Plane extends V3D_Geometry
        implements V3D_IntersectionAndDistanceCalculations {

    private static final long serialVersionUID = 1L;

    /**
     * The x = 0 plane.
     */
    public static final V3D_Plane X0 = new V3D_Plane(new V3D_Environment(),
            V3D_Vector.ZERO, V3D_Vector.ZERO, V3D_Vector.J, V3D_Vector.K);

    /**
     * The y = 0 plane.
     */
    public static final V3D_Plane Y0 = new V3D_Plane(new V3D_Environment(),
            V3D_Vector.ZERO, V3D_Vector.ZERO, V3D_Vector.I, V3D_Vector.K);

    /**
     * The z = 0 plane.
     */
    public static final V3D_Plane Z0 = new V3D_Plane(new V3D_Environment(),
            V3D_Vector.ZERO, V3D_Vector.ZERO, V3D_Vector.I, V3D_Vector.J);

    /**
     * @param e The V3D_Environment.
     * @param p The plane to test points are coplanar with.
     * @param points The points to test if they are coplanar with p.
     * @return {@code true} iff all points are coplanar with p.
     */
    public static boolean isCoplanar(V3D_Environment e, V3D_Plane p, V3D_Point... points) {
        for (V3D_Point pt : points) {
            if (!p.isIntersectedBy(pt, e.oom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param e The V3D_Environment.
     * @param p The plane to test points are coplanar with.
     * @param points The points to test if they are coplanar with p.
     * @return {@code true} iff all points are coplanar with p.
     */
    private static boolean isCoplanar(V3D_Environment e, V3D_Plane p, V3D_Vector... points) {
        for (V3D_Vector pt : points) {
            V3D_Point point = new V3D_Point(e, p.offset, pt);
            if (!p.isIntersectedBy(point, e.oom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param e The V3D_Environment.
     * @param p The plane to test points are coplanar with.
     * @param points The points to test if they are coplanar with p.
     * @return {@code true} iff all points are coplanar with p.
     */
    public static boolean isCoplanar(V3D_Environment e, V3D_Envelope.Plane p, V3D_Envelope.Point... points) {
        for (V3D_Envelope.Point pt : points) {
            if (!p.isIntersectedBy(pt, e.oom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param e The V3D_Environment.
     * @param points The points to test if they are coplanar.
     * @return {@code false} if points are coincident or collinear. {@code true}
     * iff all points are coplanar.
     */
    public static boolean isCoplanar(V3D_Environment e, V3D_Point... points) {
        // For the points to be in a plane at least one must not be collinear.
        if (V3D_Point.isCoincident(points)) {
            return false;
        }
        if (!V3D_Line.isCollinear0(e, points)) {
            V3D_Plane p = getPlane0(e, points);
            return isCoplanar(e, p, points);
        }
        return false;
    }

    /**
     * @param e The V3D_Environment.
     * @param points The points to test if they are coplanar.
     * @return {@code false} if points are coincident or collinear. {@code true}
     * iff all points are coplanar.
     */
    public static boolean isCoplanar(V3D_Environment e, V3D_Vector... points) {
        // For the points to be in a plane at least one must not be collinear.
        if (V3D_Point.isCoincident(points)) {
            return false;
        }
        if (!V3D_Line.isCollinear0(e, points)) {
            V3D_Plane p = getPlane0(e, points);
            return isCoplanar(e, p, points);
        }
        return false;
    }

    /**
     * @param e The V3D_Environment.
     * @param points The points from which a plane is to be derived.
     * @return A plane that may or may not contain all the points or
     * {@code null} if there is no such plane (if the points are coincident or
     * collinear).
     */
    public static V3D_Plane getPlane(V3D_Environment e, V3D_Point... points) {
        V3D_Line l = V3D_Line.getLine(e, points);
        if (l == null) {
            return null;
        }
        for (V3D_Point p : points) {
            if (!V3D_Line.isCollinear(e, l, p)) {
                //return new V3D_Plane(l.getP(oom), l.getQ(oom), p, oom);
                return new V3D_Plane(e, l.getP(e.oom).getVector(e.oom), l.getQ(e.oom).getVector(e.oom), p.getVector(e.oom));
            }
        }
        return null;
    }

    /**
     * @param e The V3D_Environment.
     * @param points The points from which a plane is to be derived.
     * @return A plane that may or may not contain all the points or
     * {@code null} if there is no such plane. This does not test if the points are coincident or
     * collinear.
     */
    private static V3D_Plane getPlane0(V3D_Environment e, V3D_Point... points) {
        V3D_Line l = V3D_Line.getLine(e, points);
        for (V3D_Point p : points) {
            if (!V3D_Line.isCollinear(e, l, p)) {
                //return new V3D_Plane(l.getP(oom), l.getQ(oom), p, oom);
                return new V3D_Plane(e, V3D_Vector.ZERO, l.getP(e.oom).getVector(e.oom), l.getQ(e.oom).getVector(e.oom), p.getVector(e.oom));
            }
        }
        return null;
    }

    /**
     * @param e The V3D_Environment.
     * @param points The points from which a plane is to be derived.
     * @return A plane that may or may not contain all the points or
     * {@code null} if there is no such plane. This does not test if the points are coincident or
     * collinear.
     */
    private static V3D_Plane getPlane0(V3D_Environment e, V3D_Vector... points) {
        V3D_Line l = V3D_Line.getLine(e, points);
        for (V3D_Vector p : points) {
            if (!V3D_Line.isCollinear(e, l, p)) {
                //return new V3D_Plane(l.getP(oom), l.getQ(oom), p, oom);
                return new V3D_Plane(e, l.offset, l.getP(), l.getQ(), p);
            }
        }
        return null;
    }

//    /**
//     * True iff q is at the origin.
//     */
//    protected boolean qAtOrigin;
    /**
     * One of the points that defines the plane.
     */
    protected V3D_Vector p;

    /**
     * One of the points that defines the plane.
     */
    protected V3D_Vector q;

    /**
     * One of the points that defines the plane.
     */
    protected V3D_Vector r;

    /**
     * The vector representing the move from {@link #p} to {@link #q}.
     */
    protected V3D_Vector pq;

    /**
     * The vector representing the move from {@link #q} to {@link #r}.
     */
    protected V3D_Vector qr;

    /**
     * The vector representing the move from {@link #r} to {@link #p}.
     */
    protected V3D_Vector rp;

    /**
     * The normal vector. (This is perpendicular to the plane and it's direction
     * is given by order in which the two vectors {@link #pq} and {@link #qr}
     * are used in a cross product calculation when the plane is constructed.
     */
    protected V3D_Vector n;

    /**
     * Create a new instance.
     *
     * @param p The plane used to create this.
     */
    public V3D_Plane(V3D_Plane p) {
        super(p.e, p.offset);
        this.p = new V3D_Vector(p.p);
        this.q = new V3D_Vector(p.q);
        this.r = new V3D_Vector(p.r);
    }

    /**
     * Default {@link #offset} to {@link V3D_Vector#ZERO}.
     *
     * @param e What {@link #e} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     * @param check If {@code true} then if p, q and r are coincident or
     * collinear then a RuntimeException is thrown.
     */
    public V3D_Plane(V3D_Environment e, V3D_Vector p, V3D_Vector q,
            V3D_Vector r, boolean check) {
        this(e, V3D_Vector.ZERO, p, q, r, check);
    }

    /**
     * Create a new instance. If {@code q} is at the origin, then swap this with
     * one of the other points as otherwise the cross product/normal vector
     * turns out to be the Zero vector.
     *
     * @param e What {@link #e} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p Used to initialise {@link #p}.
     * @param q Used to initialise {@link #q}.
     * @param r Used to initialise {@link #r}.
     * @param check Ignored.
     * @throws RuntimeException if p, q and r are coincident or collinear.
     */
    public V3D_Plane(V3D_Environment e, V3D_Vector offset, V3D_Vector p, V3D_Vector q,
            V3D_Vector r, boolean check) {
        super(e, offset);
        this.p = p;
        this.q = q;
        this.r = r;
        if (isCoplanar(e, p, q, r)) {
            throw new RuntimeException("The points do not define a plane.");
        }
    }

    /**
     * Create a new instance. This assumes that p, q and r are not collinear.
     * {@link #offset} is set to {@link V3D_Vector#ZERO}.
     *
     * @param e What {@link #e} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     */
    public V3D_Plane(V3D_Environment e, V3D_Vector p, V3D_Vector q,
            V3D_Vector r) {
        this(e, V3D_Vector.ZERO, p, q, r);
    }

    /**
     * Create a new instance. This assumes that p, q and r are not collinear.
     *
     * @param e What {@link #e} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     */
    public V3D_Plane(V3D_Environment e, V3D_Vector offset, V3D_Vector p,
            V3D_Vector q, V3D_Vector r) {
        super(e, offset);
        this.p = p;
        this.q = q;
        this.r = r;
    }

    /**
     * Create a new instance.
     *
     * @param p Used to initialise {@link #p}.
     * @param n The normal of the plane used to get two other points.
     */
    public V3D_Plane(V3D_Point p, V3D_Vector n) {
        super(p.e, p.offset);
        //this.p = new V3D_Point(p);
        //this.p = new V3D_Vector(p, oom);
        this.p = new V3D_Vector(p, e.oom - 1);
        //this.p = p.getRel();
        /**
         * Find a perpendicular vector using: user65203, How to find
         * perpendicular vector to another vector?, URL (version: 2020-10-01):
         * https://math.stackexchange.com/q/3821978 Tested in
         * testIsIntersectedBy_V3D_Point_int()
         */
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
        //this.q = p.apply(pv, oom);
        this.q = this.p.add(pv, e.oom);
        V3D_Vector pvx = pv.getCrossProduct(n, e.oom);
        //this.r = p.apply(pvx, oom);
        this.r = this.p.add(pvx, e.oom);
//        pq = new V3D_Vector(p, q, oom);
//        qr = new V3D_Vector(q, r, oom);
//        rp = new V3D_Vector(r, p, oom);
        this.n = n;
    }

    /**
     * Creates a new instance. {@link p}, {@link q} and {@link r} must all be 
     * different. 
     *
     * @param p Used to initialise {@link #e}, {@link #offset} and {@link #p}.
     * @param q Used to initialise {@link #q}.
     * @param r Used to initialise {@link #r}.
     */
    public V3D_Plane(V3D_Point p, V3D_Point q, V3D_Point r) {
        super(p.e, p.offset);
        this.p = new V3D_Vector(p.rel);
        V3D_Point q2 = new V3D_Point(q);
        q2.setOffset(p.offset);
        this.q = q2.rel;
        V3D_Point r2 = new V3D_Point(r);
        r2.setOffset(p.offset);
        this.r = r2.rel;
    }

    @Override
    public String toString() {
        return toString("");
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
     * @return A description of the fields.
     */
    @Override
    protected String toStringFields(String pad) {
        return super.toStringFields(pad) + "\n"
                + pad + ",\n"
                + pad + "p=" + p.toString(pad) + "\n"
                + pad + ",\n"
                + pad + "q=" + q.toString(pad) + "\n"
                + pad + ",\n"
                + pad + "r=" + r.toString(pad);
    }

//    /**
//     * @return {@link #p} with rotations applied.
//     */
//    public final V3D_Vector getPV() {
//        if (pTemp == null) {
//            pTemp = p;
//        }
//        pTemp = rotate(pTemp, bI, theta);
//        return pTemp;
//    }
    /**
     * @return {@link #p}.
     */
    public final V3D_Vector getPV() {
        return p;
    }

    /**
     * @return {@link #p} with {@link #offset} and rotations applied.
     */
    public final V3D_Point getP() {
        return new V3D_Point(e, offset, getPV());
    }

//    /**
//     * @return {@link #q} with rotations applied.
//     */
//    public final V3D_Vector getQV() {
//        if (qTemp == null) {
//            qTemp = q;
//        }
//        qTemp = rotate(qTemp, bI, theta);
//        return qTemp;
//    }
    /**
     * @return {@link #q}.
     */
    public final V3D_Vector getQV() {
        return q;
    }

    /**
     * @return {@link #q} with {@link #offset} and rotations applied.
     */
    public final V3D_Point getQ() {
        return new V3D_Point(e, offset, getQV());
    }

//    /**
//     * @return {@link #r} with rotations applied.
//     */
//    public final V3D_Vector getRV() {
//        //return new V3D_Point(offset, rotate(r, theta));
//        if (rTemp == null) {
//            rTemp = r;
//        }
//        rTemp = rotate(rTemp, bI, theta);
//        return rTemp;
//    }
    /**
     * @return {@link #r}.
     */
    public final V3D_Vector getRV() {
        return r;
    }

    /**
     * @return {@link #r} with {@link #offset} and rotations applied.
     */
    public final V3D_Point getR() {
        return new V3D_Point(e, offset, getRV());
    }

    /**
     * @return The vector from {@link #p} to {@link #q}.
     */
    public V3D_Vector getPQV() {
        if (pq == null) {
            pq = q.subtract(p, e.oom);
        }
        //return rotate(pq, bI, theta);
        return pq;
    }

    /**
     * @return The vector from {@link #q} to {@link #r}.
     */
    public V3D_Vector getQRV() {
        if (qr == null) {
            qr = r.subtract(q, e.oom);
        }
        //return rotate(qr, bI, theta);
        return qr;
    }

    /**
     * @return The vector from {@link #r} to {@link #p}.
     */
    public V3D_Vector getRPV() {
        if (rp == null) {
            rp = p.subtract(r, e.oom);
        }
        //return rotate(rp, bI, theta);
        return rp;
    }

    /**
     * @return The {@link #p}-{@link #q} triangle edge.
     */
    public V3D_LineSegment getPQ() {
        //return new V3D_LineSegment(offset, p, q, oom);
        return new V3D_LineSegment(e, offset, getPV(), getQV());
    }

    /**
     * @return The {@link #q}-{@link #r} triangle edge.
     */
    public V3D_LineSegment getQR() {
        //return new V3D_LineSegment(offset, q, r, oom);
        return new V3D_LineSegment(e, offset, getQV(), getRV());
    }

    /**
     * @return The {@link #r}-{@link #p} triangle edge.
     */
    public V3D_LineSegment getRP() {
        //return new V3D_LineSegment(offset, r, p, oom);
        return new V3D_LineSegment(e, offset, getRV(), getPV());
    }

    /**
     * @return {@code true} iff the point {@link #q} is at the origin
     */
    public boolean isQAtOrigin() {
        return getQ().equals(V3D_Point.ORIGIN);
    }

    /**
     * @param oom The Order of Magnitude for the calculation.
     * @return The normal vector.
     */
    public V3D_Vector getN(int oom) {
        if (n == null) {
            if (isQAtOrigin()) {
                n = getQRV().getCrossProduct(getRPV(), oom);
            } else {
                n = getPQV().getCrossProduct(getQRV(), oom);
            }
        }
        //return rotate(n, bI, theta);
        return n;
    }

    /**
     * For getting the equation of the plane.
     *
     * @return The equation of the plane as a String.
     */
    public String getEquation() {
        Math_BigRational[] coefficients = getEquationCoefficients();
        return coefficients[0].toRationalString() + " * x + "
                + coefficients[1].toRationalString() + " * y + "
                + coefficients[2].toRationalString() + " * z + "
                + coefficients[3].toRationalString() + " = 0";
    }

    /**
     * For getting the equation of the plane.
     *
     * @return The equation of the plane as a String.
     */
    public Math_BigRational[] getEquationCoefficients() {
        Math_BigRational[] coeffs = new Math_BigRational[4];
        // Ensure n is not null
        n = getN(e.oom);
        Math_BigRational ndxsr = n.dx.getSqrt();
        Math_BigRational ndysr = n.dy.getSqrt();
        Math_BigRational ndzsr = n.dz.getSqrt();
//        Math_BigRational k = (ndxsr.multiply(p.getX(oom))
//                .add(ndysr.multiply(p.getY(oom)))
//                .add(ndzsr.multiply(p.getZ(oom)))).negate();
        V3D_Point tp = getP();
        Math_BigRational k = (ndxsr.multiply(tp.getX(e.oom))
                .add(ndysr.multiply(tp.getY(e.oom)))
                .add(ndzsr.multiply(tp.getZ(e.oom)))).negate();
//        Math_BigRational k = (ndxsr.multiply(p.getX(oom))
//                .subtract(ndysr.multiply(p.getY(oom)))
//                .subtract(ndzsr.multiply(p.getZ(oom))));
//        Math_BigRational k = ndxsr.multiply(p.getX(oom))
//                .add(ndysr.multiply(p.getY(oom)))
//                .add(ndzsr.multiply(p.getZ(oom)));
        coeffs[0] = ndxsr;
        coeffs[1] = ndysr;
        coeffs[2] = ndzsr;
        coeffs[3] = k;
        return coeffs;
    }

//    /**
//     * @param v The vector to apply.
//     * @param oom The Order of Magnitude for the calculation.
//     * @return a new plane.
//     */
//    @Override
//    public V3D_Plane apply(V3D_Vector v, int oom) {
//        return new V3D_Plane(p.apply(v, oom), q.apply(v, oom), r.apply(v, oom),
//                oom);
//    }
    /**
     * @param pl The plane to test for intersection with this.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} If this and {@code pl} intersect.
     */
    @Override
    public boolean isIntersectedBy(V3D_Plane pl, int oom) {
        if (isParallel(pl, oom)) {
            return equals(pl);
        }
        return true;
    }

    /**
     * @param l The line to test for intersection with this.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} If this and {@code l} intersect.
     */
    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        if (isParallel(l, oom)) {
            if (!isOnPlane(l, oom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param l The line segment to test for intersection with this.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} If this and {@code l} intersect.
     */
    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom) {
        V3D_Line ll = new V3D_Line(l);
        if (isIntersectedBy(ll, oom)) {
            V3D_Geometry g = getIntersection(ll, oom);
            if (g == null) {
                return false;
            }
            if (g instanceof V3D_Point pt) {
                return l.isIntersectedBy(pt, oom);
            } else {
                return l.isIntersectedBy((V3D_Line) g, oom);
            }
        }
        return false;
    }

    /**
     * This uses matrices as per the answer to this:
     * <a href="https://math.stackexchange.com/questions/684141/check-if-a-point-is-on-a-plane-minimize-the-use-of-multiplications-and-divisio">https://math.stackexchange.com/questions/684141/check-if-a-point-is-on-a-plane-minimize-the-use-of-multiplications-and-divisio</a>
     *
     * @param pt The point to test if it is on the plane.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} If {@code pt} is on the plane.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom) {
        Math_BigRational[][] m = new Math_BigRational[4][4];
//        m[0][0] = p.getX(oom);
//        m[1][0] = p.getY(oom);
//        m[2][0] = p.getZ(oom);
//        m[3][0] = Math_BigRational.ONE;
//        m[0][1] = q.getX(oom);
//        m[1][1] = q.getY(oom);
//        m[2][1] = q.getZ(oom);
//        m[3][1] = Math_BigRational.ONE;
//        m[0][2] = r.getX(oom);
//        m[1][2] = r.getY(oom);
//        m[2][2] = r.getZ(oom);
        V3D_Point tp = getP();
        m[0][0] = tp.getX(oom);
        m[1][0] = tp.getY(oom);
        m[2][0] = tp.getZ(oom);
        m[3][0] = Math_BigRational.ONE;
        V3D_Point tq = getQ();
        m[0][1] = tq.getX(oom);
        m[1][1] = tq.getY(oom);
        m[2][1] = tq.getZ(oom);
        m[3][1] = Math_BigRational.ONE;
        V3D_Point tr = getR();
        m[0][2] = tr.getX(oom);
        m[1][2] = tr.getY(oom);
        m[2][2] = tr.getZ(oom);
        m[3][2] = Math_BigRational.ONE;
        m[0][3] = pt.getX(oom);
        m[1][3] = pt.getY(oom);
        m[2][3] = pt.getZ(oom);
        m[3][3] = Math_BigRational.ONE;
        return new Math_Matrix_BR(m).getDeterminant().compareTo(Math_BigRational.ZERO) == 0;
    }

    /**
     * @param l The line to test if it is on the plane.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} If {@code pt} is on the plane.
     */
    public boolean isOnPlane(V3D_Line l, int oom) {
        return isIntersectedBy(l.getP(oom), oom) && isIntersectedBy(l.getQ(oom), oom);
    }

    /**
     * https://en.wikipedia.org/wiki/Line%E2%80%93plane_intersection Weisstein,
     * Eric W. "Line-Plane Intersection." From MathWorld--A Wolfram Web
     * Resource. https://mathworld.wolfram.com/Line-PlaneIntersection.html
     *
     * @param l The line to intersect with the plane.
     * @param oom The Order of Magnitude for the calculation.
     * @return The intersection of the line and the plane. This is either
     * {@code null} a line or a point.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        int oomN2 = oom - 2;
        if (this.isParallel(l, oomN2)) {
            if (this.isOnPlane(l, oomN2)) {
                return l;
            } else {
                return null;
            }
        }
        // Are either of the points of l on the plane.
        //V3D_Point lp = l.getP(oom);
        V3D_Point lp = l.getP(oomN2 - 3);
        if (this.isIntersectedBy(lp, oomN2)) {
            return lp;
        }
        V3D_Point lq = l.getQ(oomN2);
        if (this.isIntersectedBy(lq, oomN2)) {
            return lq;
        }
        V3D_Vector lv = l.getV(oomN2);
        Math_BigRational[][] m = new Math_BigRational[4][4];
        m[0][0] = Math_BigRational.ONE;
        m[0][1] = Math_BigRational.ONE;
        m[0][2] = Math_BigRational.ONE;
        m[0][3] = Math_BigRational.ONE;
//        m[1][0] = p.getX(oom);
//        m[1][1] = q.getX(oom);
//        m[1][2] = r.getX(oom);
//        m[1][3] = lp.getX(oom);
//        m[2][0] = p.getY(oom);
//        m[2][1] = q.getY(oom);
//        m[2][2] = r.getY(oom);
//        m[2][3] = lp.getY(oom);
//        m[3][0] = p.getZ(oom);
//        m[3][1] = q.getZ(oom);
//        m[3][2] = r.getZ(oom);
//        m[3][3] = lp.getZ(oom);
        V3D_Point tp = getP();
        V3D_Point tq = getQ();
        V3D_Point tr = getR();
        m[1][0] = tp.getX(oomN2);
        m[1][1] = tq.getX(oomN2);
        m[1][2] = tr.getX(oomN2);
        m[1][3] = lp.getX(oomN2);
        m[2][0] = tp.getY(oomN2);
        m[2][1] = tq.getY(oomN2);
        m[2][2] = tr.getY(oomN2);
        m[2][3] = lp.getY(oomN2);
        m[3][0] = tp.getZ(oomN2);
        m[3][1] = tq.getZ(oomN2);
        m[3][2] = tr.getZ(oomN2);
        m[3][3] = lp.getZ(oomN2);
        Math_Matrix_BR numm = new Math_Matrix_BR(m);
        m[0][0] = Math_BigRational.ONE;
        m[0][1] = Math_BigRational.ONE;
        m[0][2] = Math_BigRational.ONE;
        m[0][3] = Math_BigRational.ZERO;
//        m[1][0] = p.getX(oom);
//        m[1][1] = q.getX(oom);
//        m[1][2] = r.getX(oom);
//        m[1][3] = lv.getDX(oom);
//        m[2][0] = p.getY(oom);
//        m[2][1] = q.getY(oom);
//        m[2][2] = r.getY(oom);
//        m[2][3] = lv.getDY(oom);
//        m[3][0] = p.getZ(oom);
//        m[3][1] = q.getZ(oom);
//        m[3][2] = r.getZ(oom);
//        m[3][3] = lv.getDZ(oom);
        m[1][0] = tp.getX(oomN2);
        m[1][1] = tq.getX(oomN2);
        m[1][2] = tr.getX(oomN2);
        m[1][3] = lv.getDX(oomN2);
        m[2][0] = tp.getY(oomN2);
        m[2][1] = tq.getY(oomN2);
        m[2][2] = tr.getY(oomN2);
        m[2][3] = lv.getDY(oomN2);
        m[3][0] = tp.getZ(oomN2);
        m[3][1] = tq.getZ(oomN2);
        m[3][2] = tr.getZ(oomN2);
        m[3][3] = lv.getDZ(oomN2);
        Math_Matrix_BR denm = new Math_Matrix_BR(m);
        Math_BigRational t = numm.getDeterminant().divide(denm.getDeterminant()).negate();
        return new V3D_Point(e,
                lp.getX(oomN2).add(lv.getDX(oomN2).multiply(t)),
                lp.getY(oomN2).add(lv.getDY(oomN2).multiply(t)),
                lp.getZ(oomN2).add(lv.getDZ(oomN2).multiply(t)));
//        V3D_Vector d = new V3D_Vector(this.p, l.p, n.oom);
//        //V3D_Vector d = new V3D_Vector(l.p, p, n.oom);
//        Math_BigRational num = d.getDotProduct(this.n);
//        Math_BigRational den = l.v.getDotProduct(this.n);
//        Math_BigRational t = num.divide(den);
//        return new V3D_Point(
//                l.p.getX(oom).subtract(l.v.getDX(oom).multiply(t)),
//                l.p.getY(oom).subtract(l.v.getDY(oom).multiply(t)),
//                l.p.getZ(oom).subtract(l.v.getDZ(oom).multiply(t)));

    }

    /**
     * @param l line segment to intersect with this.
     * @param oom The Order of Magnitude for the calculation.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom) {
        V3D_Geometry g = getIntersection(new V3D_Line(l), oom);
        if (g == null) {
            return null;
        }
        if (g instanceof V3D_Line) {
            return l;
        } else {
            V3D_Point pt = (V3D_Point) g;
            if (l.isIntersectedBy(pt, oom)) {
                return pt;
            }
            return null;
        }
    }

    /**
     * @param r The ray to intersect with this.
     * @param oom The Order of Magnitude for the calculation.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry getIntersection(V3D_Ray r, int oom) {
        V3D_Geometry rit = r.getIntersection(this, oom);
        if (rit == null) {
            return rit;
        }
        if (rit instanceof V3D_Line) {
            return r;
        }
        if (r.getV().getDirection() == new V3D_Vector(r.getP(oom),
                (V3D_Point) rit, oom).getDirection()) {
            return rit;
        }
        return null;
    }

    /**
     * https://www.microsoft.com/en-us/research/publication/intersection-of-two-planes/
     * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
     * https://math.stackexchange.com/questions/291289/find-the-point-on-the-line-of-intersection-of-the-two-planes-using-lagranges-me
     * http://tbirdal.blogspot.com/2016/10/a-better-approach-to-plane-intersection.html
     * https://mathworld.wolfram.com/Plane-PlaneIntersection.html
     *
     * @param pl The plane to intersect.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The intersection between {@code this} and {@code pl}
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Plane pl, int oom) {
//        /**
//         * The following is from:
//         * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
//         * But something is not right!
//         */
//        //function [p, n] = intersect_planes(p1, n1, p2, n2, p0)
//        //M = [2 0 0 n1(1) n2(1)
//        //     0 2 0 n1(2) n2(2)
//        //     0 0 2 n1(3) n2(3)
//        //     n1(1) n1(2) n1(3) 0 0
//        //     n2(1) n2(2) n2(3) 0 0];
//        Math_BigRational[][] m = new Math_BigRational[5][6];
//        m[0][0] = Math_BigRational.TWO;
//        m[0][1] = Math_BigRational.ZERO;
//        m[0][2] = Math_BigRational.ZERO;
//        m[0][3] = n.getDX(oom);
//        m[0][4] = pl.n.getDX(oom);
//        m[1][0] = Math_BigRational.ZERO;
//        m[1][1] = Math_BigRational.TWO;
//        m[1][2] = Math_BigRational.ZERO;
//        m[1][3] = n.getDY(oom);
//        m[1][4] = pl.n.getDY(oom);
//        m[2][0] = Math_BigRational.ZERO;
//        m[2][1] = Math_BigRational.ZERO;
//        m[2][2] = Math_BigRational.TWO;
//        m[2][3] = n.getDZ(oom);
//        m[2][4] = pl.n.getDZ(oom);
//        m[3][0] = n.getDX(oom);
//        m[3][1] = n.getDY(oom);
//        m[3][2] = n.getDZ(oom);
//        m[3][3] = Math_BigRational.ZERO;
//        m[3][4] = Math_BigRational.ZERO;
//        m[4][0] = pl.n.getDX(oom);
//        m[4][1] = pl.n.getDY(oom);
//        m[4][2] = pl.n.getDZ(oom);
//        m[4][3] = Math_BigRational.ZERO;
//        m[4][4] = Math_BigRational.ZERO;
//        // b4 = p1(1).*n1(1) + p1(2).*n1(2) + p1(3).*n1(3);
//        Math_BigRational b4 = p.getX(oom).multiply(n.getDX(oom))
//                .add(p.getY(oom).multiply(n.getDY(oom)))
//                .add(p.getZ(oom).multiply(n.getDZ(oom)));
//        System.out.println(b4);
//        // b5 = p2(1).*n2(1) + p2(2).*n2(2) + p2(3).*n2(3);
//        Math_BigRational b5 = pl.p.getX(oom).multiply(pl.n.getDX(oom))
//                .add(pl.p.getY(oom).multiply(pl.n.getDY(oom)))
//                .add(pl.p.getZ(oom).multiply(pl.n.getDZ(oom)));
//        System.out.println(b5);
//        // b = [2*p0(1) ; 2*p0(2) ; 2*p0(3); b4 ; b5];
//        m[0][5] = q.getX(oom).multiply(2);
//        m[1][5] = q.getY(oom).multiply(2);
//        m[2][5] = q.getZ(oom).multiply(2);
//        m[3][5] = b4;
//        m[4][5] = b5;
//        // x = M\b;
//        Math_Matrix_BR mm = new Math_Matrix_BR(m);
//        Math_Matrix_BR ref = mm.getReducedRowEchelonForm();
//        System.out.println(ref);
//        // p = x(1:3);
//        //???
//        // n = cross(n1, n2);

        int oomN5 = oom - 5;
        /**
         * Calculate the cross product of the normal vectors to get the
         * direction of the line.
         */
        V3D_Vector v = getN(oomN5).getCrossProduct(pl.getN(oomN5), oomN5);
        //V3D_Vector v = pl.getN(oomN5).getCrossProduct(getN(oomN5), oomN5);

        /**
         * Check special cases.
         */
        if (v.isZeroVector()) {
            // The planes are parallel.
            if (pl.equals(this)) {
                // The planes are the same.
                return this;
            }
            // There is no intersection.
            return null;
        }
        /**
         * Find the intersection of a line in the plane that is not parallel to
         * v.
         */
        V3D_Point pi;
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

        if (pl.getPQV().isScalarMultiple(v, oomN5)) {
            pi = (V3D_Point) getIntersection(
                    new V3D_Line(pl.getP(), pl.getR()), oomN5);
        } else {
            pi = (V3D_Point) getIntersection(
                    new V3D_Line(pl.getP(), pl.getQ()), oomN5);
        }

        //return new V3D_Line(pi, v, oom);
        return new V3D_Line(pi.offset, pi.getVector(oomN5), v, e);
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
//                Math_BigRational z = pl.n.getDX(oom).multiply(pl.p.getX(oom)
//                        .subtract(pl.q.getX(oom))).add(n.getDY(oom).multiply(pl.p.getY(oom)
//                        .subtract(pl.q.getX(oom)))).divide(v.getDZ(oom)).add(pl.p.getZ(oom));
//                V3D_Point pt;
//                if (n.dx.isZero()) {
//                    pt = new V3D_Point(pl.p.getX(oom), p.getY(oom), z);
//                } else {
//                    pt = new V3D_Point(p.getX(oom), pl.p.getY(oom), z);
//                }
//                return new V3D_Line(pt, pt.apply(v, oom), oom);
//                // The intersection is at z=?
//
//                /**
//                 * n.getDX(oom)(x(t)−p.getX(oom))+n.getDY(oom)(y(t)−p.getY(oom))+n.getDZ(oom)(z(t)−p.getZ(oom))
//                 */
//                // where:
//                // n.getDX(oom) = a; n.getDY(oom) = b; n.getDZ(oom) = c
//                // pl.n.getDX(oom) = d; pl.n.getDY(oom) = e; pl.n.getDZ(oom) = f
//                // a(x−p.getX(oom)) + b(y−p.getY(oom)) + c(z−p.getZ(oom)) = 0
//                // x = p.getX(oom) + ((- b(y−p.getY(oom)) - c(z−p.getZ(oom))) / a)                     --- 1
//                // y = p.getY(oom) + ((- a(x−p.getX(oom)) - c(z−p.getZ(oom))) / b)                     --- 2
//                // z = p.getZ(oom) + ((- a(x−p.getX(oom)) - b(y−p.getY(oom))) / c)                     --- 3
//                // x = pl.p.getX(oom) + ((- pl.b(y − pl.p.getY(oom)) - pl.c(z − pl.p.getZ(oom))) / d)  --- 4
//                // y = pl.p.getY(oom) + ((- pl.a(x − pl.p.getX(oom)) - pl.c(z − pl.p.getZ(oom))) / e)  --- 5
//                // z = pl.p.getZ(oom) + ((- pl.a(x − pl.p.getX(oom)) - pl.b(y − pl.p.getY(oom))) / f)  --- 6
//                // Let:
//                // p.getX(oom) = k; p.getY(oom) = l; p.getZ(oom) = m
//                // x = k + ((b(l - y) - c(z − m)) / a)   --- 1t
//                // y = l + ((a(k - x) - c(z − l)) / b)   --- 2t
//                // z = m + ((a(k - x) - b(y − m)) / c)   --- 3t
//                // Let:
//                // pl.p.getX(oom) = k; pl.p.getY(oom) = l; pl.p.getZ(oom) = m
//                // x = k + ((e(l - y) - f(z - m)) / d)   --- 1p
//                // y = l + ((d(k - x) - f(z - l)) / e)   --- 2p
//                // z = m + ((d(k - x) - e(y - m)) / f)   --- 3p
////                if (n.getDX(oom).compareTo(e.P0) == 0) {
////                    //pl.b
////                } else if (n.getDY(oom).compareTo(e.P0) == 0) {
//////                    // y = l + ((- a(x − k) - c(z − l)) / b)
////                    BigDecimal y = p.getY(oom);
//////                    // x = k + ((e(l - y) - f(z - m)) / d)
//////                    // z = m + ((- d(x - k) - e(y - m)) / f)
//////                    BigDecimal x = p.getX(oom).apply(
//////                            Math_BigDecimal.divideRoundIfNecessary(
//////                                    (pl.n.getDY(oom).multiply(p.getY(oom).subtract(y))).subtract(pl.n.getDZ(oom).multiply(z.subtract(pl.p.getZ(oom)))),
//////                                    pl.n.getDX(oom), scale, rm));
////                } else {
////                    return e.zAxis;
////                }
////                //BigDecimal x = p.getX(oom)
////                BigDecimal numerator = p.getZ(oom).subtract(p.getY(oom))
////                        .subtract(n.getDZ(oom).multiply(p.getZ(oom)))
////                        .subtract(p.getY(oom).multiply(n.getDY(oom)));
////                BigDecimal denominator = n.getDY(oom).subtract(n.getDZ(oom));
////                if (denominator.compareTo(e.P0) == 0) {
////                    // Case 1: The z axis
////                    return null;
////                } else {
////                    // y = (p.getY(oom) - c(z−p.getZ(oom))) / b   --- 1          
////                    // z = (p.getZ(oom) - b(y−p.getY(oom))) / c   --- 2
////                    // n.getDX(oom) = a; n.getDY(oom) = b; n.getDZ(oom) = c
////                    // Let:
////                    // p.getX(oom) = k; p.getY(oom) = l; p.getZ(oom) = m
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
////                    // y = (p.getY(oom) - c(z−p.getZ(oom))) / b   --- 1
////                    if (n.getDY(oom).compareTo(e.P0) == 0) {
////                        // Another case to deal with
////                        return null;
////                    } else {
////                        BigDecimal y = Math_BigDecimal.divideRoundIfNecessary(
////                                p.getY(oom).subtract(n.getDZ(oom).multiply(z.subtract(p.getZ(oom)))),
////                                n.getDY(oom), scale, rm);
////                        return new V3D_Line(new V3D_Point(e, p.getX(oom), y, z),
////                                new V3D_Point(e, p.getX(oom).multiply(e.N1), y.add(v.getDY(oom)), z.add(v.getDZ(oom))));
////                    }
////                }
//            } else {
//                if (v.dz.isZero()) {
//                    Math_BigRational y = n.getDX(oom).multiply(p.getX(oom).subtract(q.getX(oom))).add(
//                            n.getDZ(oom).multiply(p.getZ(oom).subtract(q.getX(oom))))
//                            .divide(v.getDY(oom)).add(p.getY(oom));
//                    V3D_Point pt;
//                    if (n.getDX(oom).compareTo(P0) == 0) {
//                        pt = new V3D_Point(pl.p.getX(oom), y, p.getZ(oom));
//                    } else {
//                        pt = new V3D_Point(p.getX(oom), y, pl.p.getZ(oom));
//                    }
//                    return new V3D_Line(pt, pt.apply(v, oom), oom);
//                } else {
//                    // Case 3
//                    // y = (p.getY(oom) - c(z−p.getZ(oom))) / b   --- 1          
//                    // z = (p.getZ(oom) - b(y−p.getY(oom))) / c   --- 2
//                    // n.getDX(oom) = a; n.getDY(oom) = b; n.getDZ(oom) = c
//                    // Let:
//                    // p.getX(oom) = k; p.getY(oom) = l; p.getZ(oom) = m
//                    // y = (l - c(z - m)) / b
//                    // z = (m - b(y - l)) / b
//                    // z = (m - b(((l - c(z - m)) / b) - l)) / b
//                    // bz = m - b(((l - c(z - m)) / b) - l)
//                    // bz = m - (l - c(z - m)) - lb
//                    // bz = m - l + cz - cm - lb
//                    // bz - cz = m - l -cm - lb
//                    // z(b - c) = m - l -cm - lb
//                    // z = (m - l -cm - lb) / (b - c)
//                    Math_BigRational numerator = p.getZ(oom).subtract(p.getY(oom))
//                            .subtract(n.getDZ(oom).multiply(p.getZ(oom)))
//                            .subtract(p.getY(oom).multiply(n.getDY(oom)));
//                    Math_BigRational denominator = n.getDY(oom).subtract(n.getDZ(oom));
//                    if (denominator.compareTo(P0) == 0) {
//                        // Another case to deal with
//                        return null;
//                    } else {
//                        Math_BigRational z = numerator.divide(denominator);
//                        // Substitute into 1
//                        // y = (p.getY(oom) - c(z−p.getZ(oom))) / b   --- 1
//                        if (n.dy.isZero()) {
//                            // Another case to deal with
//                            return null;
//                        } else {
//                            Math_BigRational y = p.getY(oom).subtract(n.getDZ(oom).multiply(z.subtract(p.getZ(oom))))
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
//                    Math_BigRational x = pl.n.getDY(oom).multiply(pl.p.getY(oom).subtract(pl.q.getY(oom))).add(
//                            n.getDZ(oom).multiply(pl.p.getZ(oom).subtract(pl.q.getY(oom))))
//                            .divide(v.getDX(oom)).add(pl.p.getX(oom));
//                    V3D_Point pt;
//                    if (n.dy.isZero()) {
//                        if (n.dz.isZero()) {
//                            pt = new V3D_Point(x, p.getY(oom), pl.p.getZ(oom));
//                        } else {
//                            pt = new V3D_Point(x, pl.p.getY(oom), p.getZ(oom));
//                        }
//                    } else {
//                        if (n.dz.isZero()) {
//                            pt = new V3D_Point(x, p.getY(oom), pl.p.getZ(oom));
//                        } else {
//                            pt = new V3D_Point(x, p.getY(oom), pl.p.getZ(oom));
//                        }
//                    }
//                    return new V3D_Line(pt, pt.apply(v, oom), oom);
//                } else {
//                    Math_BigRational z = pl.n.getDX(oom).multiply(pl.p.getX(oom).subtract(pl.q.getX(oom))).add(
//                            n.getDY(oom).multiply(pl.p.getY(oom).subtract(pl.q.getX(oom))))
//                            .divide(v.getDZ(oom)).add(pl.p.getZ(oom));
//                    V3D_Point pt;
//                    if (n.dx.isZero()) {
//                        pt = new V3D_Point(pl.p.getX(oom), p.getY(oom), z);
//                    } else {
//                        pt = new V3D_Point(p.getX(oom), pl.p.getY(oom), z);
//                    }
//                    return new V3D_Line(pt, pt.apply(v, oom), oom);
//                }
//            } else {
//                if (v.dz.isZero()) {
//                    // Case 6
//                    Math_BigRational y = n.getDX(oom).multiply(p.getX(oom).subtract(q.getX(oom))).add(
//                            n.getDZ(oom).multiply(p.getZ(oom).subtract(q.getX(oom))))
//                            .divide(v.getDY(oom)).add(p.getY(oom));
//                    V3D_Point pt;
//                    if (p.getX(oom).compareTo(P0) == 0) {
//                        pt = new V3D_Point(p.getX(oom), y, pl.p.getZ(oom)); // x=1 z=0
//                    } else {
//                        pt = new V3D_Point(pl.p.getX(oom), y, p.getZ(oom));
//                    }
//                    return new V3D_Line(pt, pt.apply(v, oom), oom);
//                } else {
//                    /**
//                     * Case 7: Neither plane aligns with any axis and they are
//                     * not orthogonal.
//                     *
//                     * n.getDX(oom)(x(t)−p.getX(oom))+n.getDY(oom)(y(t)−p.getY(oom))+n.getDZ(oom)(z(t)−p.getZ(oom))
//                     */
//                    // where:
//                    // n.getDX(oom) = a; n.getDY(oom) = b; n.getDZ(oom) = c
//                    // pl.n.getDX(oom) = d; pl.n.getDY(oom) = e; pl.n.getDZ(oom) = f
//                    // a(x−p.getX(oom))+b(y−p.getY(oom))+c(z−p.getZ(oom)) = 0
//                    // x = (ap.getX(oom)-by+bp.getY(oom)-cz+cp.getZ(oom))/a
//                    // x = p.getX(oom)+((b(p.getY(oom)−y)+c(p.getZ(oom)−z))/a)                    --- 1
//                    // y = p.getY(oom)+((a(p.getX(oom)−x)+c(p.getZ(oom)−z))/b)                    --- 2
//                    // z = p.getZ(oom)+((a(p.getX(oom)−x)+b(p.getY(oom)−y))/c)                    --- 3
//                    // x = pl.p.getX(oom)+((pl.b(pl.p.getY(oom)−y)+pl.c(pl.p.getZ(oom)−z))/pl.a)  --- 4
//                    // y = pl.p.getY(oom)+((pl.a(pl.p.getX(oom)−x)+pl.c(pl.p.getZ(oom)−z))/pl.b)  --- 5
//                    // z = pl.p.getZ(oom)+((pl.a(pl.p.getX(oom)−x)+pl.b(pl.p.getY(oom)−y))/pl.c)  --- 6
//                    // Let:
//                    // p.getX(oom) = g; p.getY(oom) = h; p.getZ(oom) = i
//                    // x = g+((b(h−y)+c(i−z))/a)                    --- 1p
//                    // y = h+((a(g−x)+c(i−z))/b)                    --- 2p
//                    // z = i+((a(g−x)+b(h−y))/c)                    --- 3p
//                    // Let:
//                    // pl.p.getX(oom) = j; pl.p.getY(oom) = k; pl.p.getZ(oom) = l
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
//                    Math_BigRational x = n.getDY(oom).multiply(pl.p.getY(oom).subtract(pl.q.getY(oom))).add(
//                            n.getDZ(oom).multiply(pl.p.getZ(oom).subtract(pl.q.getY(oom))))
//                            .divide(v.getDX(oom)).add(pl.p.getX(oom));
//                    Math_BigRational y = n.getDX(oom).multiply(p.getX(oom).subtract(q.getX(oom))).add(
//                            n.getDZ(oom).multiply(p.getZ(oom).subtract(q.getX(oom))))
//                            .divide(v.getDY(oom)).add(p.getY(oom));
//                    Math_BigRational z = n.getDX(oom).multiply(pl.p.getX(oom).subtract(pl.q.getX(oom))).add(
//                            n.getDY(oom).multiply(pl.p.getY(oom).subtract(pl.q.getX(oom))))
//                            .divide(v.getDZ(oom)).add(pl.p.getZ(oom));
//                    V3D_Point pt = new V3D_Point(x, y, z);
//                    return new V3D_Line(pt, pt.apply(v, oom), oom);
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
////                    // Let; n = v.getDX(oom); o = v.getDY(oom); p = v.getDZ(oom)
////                    // x(t) = x + v.getDX(oom) 
////                    // y(t) = y(0) + v.getDY(oom) 
////                    // z-p = z(0)
////                    // z = (ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek)/(dc/a-f)  ---12 z ito y                    
////                    // z-p = (ey-dby/a-dj_dg+dbh/a+dci/a-fl-ek)/(dc/a-f)
////                    // y = (fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e)  ---13 y ito z
////                    // z-p = (e((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))-db((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))/a-dj-dg+dbh/a+dci/a-fl-ek)/(dc/a-f)
////                    // (z-p)(dc/a-f) = e((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))-db((fz-dcz/a-dj+dg+dbh/a+dci/a-ek-fl)/(db/a-e))/a-dj-dg+dbh/a+dci/a-fl-ek
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
////                    BigDecimal g = p.getX(oom);
////                    BigDecimal h = p.getY(oom);
////                    BigDecimal i = p.getZ(oom);
////                    BigDecimal j = pl.p.getX(oom);
////                    BigDecimal k = pl.p.getY(oom);
////                    BigDecimal l = pl.p.getZ(oom);
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
////                    return new V3D_Line(aPoint, aPoint.apply(v));
//                }
//            }
//        }
//        // This is where the two equations of the plane are equal.
//        // n.getDX(oom)(x(t)−p.getX(oom))+n.getDY(oom)(y(t)−p.getY(oom))+n.getDZ(oom)(z(t)−p.getZ(oom))
//        // where:
//        // n.getDX(oom) = a; n.getDY(oom) = b; n.getDZ(oom) = c
//        // a(x−p.getX(oom)) + b(y−p.getY(oom)) + c(z−p.getZ(oom)) = 0
//        // x = (p.getX(oom) - b(y−p.getY(oom)) - c(z−p.getZ(oom))) / a                     --- 1
//        // y = (p.getY(oom) - a(x−p.getX(oom)) - c(z−p.getZ(oom))) / b                     --- 2
//        // z = (p.getZ(oom) - a(x−p.getX(oom)) - b(y−p.getY(oom))) / c                     --- 3
//        // x = (pl.p.getX(oom) - pl.b(y−pl.p.getY(oom)) - pl.c(z−pl.p.getZ(oom))) / pl.a   --- 4
//        // y = (pl.p.getY(oom) - pl.a(x−pl.p.getX(oom)) - pl.c(z−pl.p.getZ(oom))) / pl.b   --- 5
//        // z = (pl.p.getZ(oom) - pl.a(x−pl.p.getX(oom)) - pl.b(y−pl.p.getY(oom))) / pl.c   --- 6
//        // Sub 2 and 3 into
//    }
    /**
     * @param p The plane to test if it is parallel to this.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} if {@code this} is parallel to {@code p}.
     */
    public boolean isParallel(V3D_Plane p, int oom) {
        //return p.n.isScalarMultiple(n); // alternative - probably slower?
        return getN(oom).getCrossProduct(p.getN(oom), oom).isZeroVector();
    }

    /**
     * @param l The line to test if it is parallel to this.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} if {@code this} is parallel to {@code l}.
     */
    public boolean isParallel(V3D_Line l, int oom) {
        return getN(oom).getDotProduct(l.getV(oom), oom).isZero();
    }

//    @Override
//    public boolean equals(V3D_Geometry g) {
//        if (g instanceof V3D_Plane) {
//            return equals((V3D_Plane) g);
//        }
//        return false;
//    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Plane pl) {
            return equals(pl);
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
        return isCoplanar(e, this, pl.getP(), pl.getQ(),
                pl.getR());
    }

    /**
     * Planes are equal if the points of one are all on the other.
     *
     * @param pl The plane to check for equality with {@code this}.
     * @param oom The Order of Magnitude for the calculation.
     * @return {@code true} iff {@code this} and {@code pl} are the same.
     */
    public boolean isCoincident(V3D_Plane pl, int oom) {
        if (isIntersectedBy(pl.getP(), oom)) {
            if (isIntersectedBy(pl.getQ(), oom)) {
                if (isIntersectedBy(pl.getR(), oom)) {
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

    /**
     * @return The points that define the plan as a matrix.
     */
    public Math_Matrix_BR getAsMatrix() {
        V3D_Point tp = getP();
        V3D_Point tq = getQ();
        V3D_Point tr = getR();
        Math_BigRational[][] m = new Math_BigRational[3][3];
        m[0][0] = tp.getX(e.oom);
        m[0][1] = tp.getY(e.oom);
        m[0][2] = tp.getZ(e.oom);
        m[1][0] = tq.getX(e.oom);
        m[1][1] = tq.getY(e.oom);
        m[1][2] = tq.getZ(e.oom);
        m[2][0] = tr.getX(e.oom);
        m[2][1] = tr.getY(e.oom);
        m[2][2] = tr.getZ(e.oom);
        return new Math_Matrix_BR(m);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the calculation.
     * @return The distance from {@code this} to {@code p}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point pt, int oom) {
        if (this.isIntersectedBy(pt, oom)) {
            return BigDecimal.ZERO;
        }
//        MathContext mc = new MathContext(Math_Math_BigRationalSqrt
//                .getOOM(Math_BigRational.ONE, oom));
        MathContext mc = new MathContext(6 - oom);
        return new Math_BigRationalSqrt(getDistanceSquared(pt, true, oom), oom)
                .getSqrt(oom).toBigDecimal(mc);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the calculation.
     * @return The distance from {@code this} to {@code p}.
     */
    @Override
    public Math_BigRational getDistanceSquared(V3D_Point pt, int oom) {
        if (this.isIntersectedBy(pt, oom)) {
            return Math_BigRational.ZERO;
        }
        return getDistanceSquared(pt, true, oom);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pt A point.
     * @param noInt To distinguish this from
     * {@link #getDistanceSquared(uk.ac.leeds.ccg.v3d.geometry.V3D_Point, int)}
     * @param oom The Order of Magnitude for the calculation.
     * @return The distance from {@code this} to {@code p}.
     */
    public Math_BigRational getDistanceSquared(V3D_Point pt, boolean noInt, int oom) {
        V3D_Vector v = new V3D_Vector(pt, getP(), oom);
        V3D_Vector u = getN(oom).getUnitVector(oom);
        return v.getDotProduct(u, oom).pow(2);
    }

    @Override
    public BigDecimal getDistance(V3D_Plane p, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
    }

    /**
     * The planes are either parallel or the distance is zero. If parallel, get
     * any point on one and find the distance squared of the other plane from
     * it.
     *
     * @param pl The other plane used to calculate the distance.
     * @param oom The Order of Magnitude for the calculation.
     * @return The shortest distance between {@code this} and {@code pl}.
     */
    @Override
    public Math_BigRational getDistanceSquared(V3D_Plane pl, int oom) {
        if (isParallel(pl, oom)) {
            V3D_Point tp = getP();
            return tp.getDistanceSquared(pl, oom);
//            return tp.getDistanceSquared((V3D_Point) pl.getIntersection(
//                    new V3D_Line(tp, getN(oom), oom), oom), oom);
        }
        return Math_BigRational.ZERO;
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        if (isIntersectedBy(l, oom)) {
            return BigDecimal.ZERO;
        }
        return getDistance(l.getP(oom), oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom) {
        if (isIntersectedBy(l, oom)) {
            return Math_BigRational.ZERO;
        }
        return getDistanceSquared(l.getP(oom), true, oom);
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom), oom)
                .getSqrt(oom).toBigDecimal(oom);
//        BigDecimal lpd = getDistance(l.getP(oom), oom);
//        BigDecimal lqd = getDistance(l.getQ(oom), oom);
//        return lpd.min(lqd);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom) {
        Math_BigRational lpd = getDistanceSquared(l.getP(oom), oom);
        Math_BigRational lqd = getDistanceSquared(l.getQ(oom), oom);
        return lpd.min(lqd);
    }

    /**
     * Change {@link #offset} without changing the overall line.
     *
     * @param offset What {@link #offset} is set to.
     */
    public void setOffset(V3D_Vector offset) {
        p = p.add(this.offset, e.oom).subtract(offset, e.oom);
        q = q.add(this.offset, e.oom).subtract(offset, e.oom);
        r = r.add(this.offset, e.oom).subtract(offset, e.oom);
        this.offset = offset;
    }

    /**
     * Move the plane.
     *
     * @param v What is added to {@link #p}, {@link #q}, {@link #r}.
     */
    public void translate(V3D_Vector v) {
        p = p.add(v, e.oom);
        q = q.add(v, e.oom);
        r = r.add(v, e.oom);
    }

    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta) {
        p = p.rotate(axisOfRotation, theta, e.bI, e.oom);
        q = q.rotate(axisOfRotation, theta, e.bI, e.oom);
        r = r.rotate(axisOfRotation, theta, e.bI, e.oom);
        pq = null;
        qr = null;
        rp = null;
        n = null;
    }

    /**
     * Compute a return the line of intersection between {@code pt} and
     * {@code this}. See also:
     * {@code https://stackoverflow.com/questions/23472048/projecting-3d-points-to-2d-plane}
     *
     * @param pt The point which when projected onto the plane using the normal
     * to the plane forms the end of the returned line of intersection.
     * @param oom The Order of Magnitude for the calculation.
     * @return The line of intersection between {@code pt} and {@code this} or
     * {@code null} if {@code pt} is on {@code this}. The point p on the result
     * is the point of intersection
     */
    public V3D_Point getPointOfProjectedIntersection(V3D_Point pt, int oom) {
        V3D_Point pt2 = new V3D_Point(pt);
        pt2.setOffset(offset);
        V3D_Vector nn = getN(oom);
        V3D_Line l = new V3D_Line(pt2.rel, nn, e);
        return (V3D_Point) getIntersection(l, oom);
    }

    @Override
    public boolean isIntersectedBy(V3D_Triangle t, int oom) {
        if (isIntersectedBy(t.getPQ(), oom)) {
            return true;
        } else {
            return isIntersectedBy(t.getQR(), oom);
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom) {
        // Only need to test 3 of the four triangle faces of t.
        if(this.isIntersectedBy(t.getPqr(), oom)) {
            return true;
        }
        if(this.isIntersectedBy(t.getQsr(), oom)) {
            return true;
        }
        return this.isIntersectedBy(t.getSpr(), oom);
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Triangle t, int oom) {
        return t.getIntersection(this, oom);
//        V3D_Geometry tpqi = getIntersection(t.getPQ(), oom, b);
//        V3D_Geometry tqri = getIntersection(t.getQR(), oom, b);
//        if (tpqi == null) {
//            if (tqri == null) {
//                return null;
//            } else {
//                V3D_Geometry trpi = getIntersection(t.getRP(), oom, b);
//                if (trpi == null) {
//                    return tqri;
//                } else {
//                    if (tqri instanceof V3D_Point tqrip) {
//                        if (trpi instanceof V3D_Point trpip) {
//                            return V3D_LineSegment.getGeometry(tqrip, trpip);
//                        } else {
//                            return trpi;
//                        }
//                    } else {
//                        // tqri instanceof V3D_LineSegment
//                        return tqri;
//                    }
//                }
//            }
//        } else {
//            V3D_Geometry trpi = getIntersection(t.getRP(), oom, b);
//            if (tqri == null) {
//                if (trpi == null) {
//                    return tpqi;
//                } else {
//                    if (tpqi instanceof V3D_Point tpqip) {
//                        if (trpi instanceof V3D_Point trpip) {
//                            return V3D_LineSegment.getGeometry(tpqip, trpip);
//                        } else {
//                            // trpi instanceof V3D_LineSegment
//                            return trpi;
//                        }
//                    } else {
//                        // tpqi instanceof V3D_LineSegment
//                        return tpqi;
//                    }
//                }
//            } else {
//                if (trpi == null) {
//                    if (tpqi instanceof V3D_Point tpqip) {
//                        if (trpi instanceof V3D_Point trpip) {
//                            return V3D_LineSegment.getGeometry(tpqip, trpip);
//                        } else {
//                            // tpqi instanceof V3D_LineSegment
//                            return tpqi;
//                        }
//                    } else {
//                        // trpi instanceof V3D_LineSegment
//                        return trpi;
//                    }
//                } else {
//                    if (trpi instanceof V3D_Point) {
//                        if(tpqi instanceof V3D_Point) {
//                            return tqri;
//                        } else {
//                            return tpqi;
//                        }
//                    } else {
//                        return t;
//                    }
//                }
//            }
//        }
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Tetrahedron t, int oom) {
        return t.getIntersection(this, oom);
    }

    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom) {
        return t.getDistance(this, oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom) {
        return t.getDistanceSquared(this, oom);
    }

    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom) {
        return t.getDistance(this, oom);
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom) {
        return t.getDistanceSquared(this, oom);
    }
    
    /**
     * Check a and b are on the same side of this. If either are on the boundary then return {@code true}.
     *
     * @param a A point.
     * @param b Another point.
     * The triangle to check the points to see if they are all on the
     * same side of a line that intersects the edge of another triangle.
     * @param n The normal of this plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} if an intersection is found and {@code false}
     * otherwise.
     */
    public static boolean checkOnSameSide(V3D_Point a, V3D_Point b, V3D_Vector n, int oom) {
        V3D_Vector av = a.getVector(oom);
        int avd = n.getDotProduct(av, oom).compareTo(Math_BigRational.ZERO);
        if (avd == 0) {
            return true;
        } else {
            V3D_Vector bv = b.getVector(oom);
            int bvd = n.getDotProduct(bv, oom).compareTo(Math_BigRational.ZERO);
            if (bvd == 0) {
                return true;
            }
            return avd == bvd;
        }
    }
    
}
