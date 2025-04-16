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
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * 3D representation of an infinite plane.The plane is defined by the point
 * {@link #pv} and the normal {@link #n}.A plane be constructed in numerous ways
 * including by using three points that are not collinear. The equation of the
 * plane is:
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
 * <li>{@code n.getDX(oom)(x(t)−v.getX(oom))+n.getDY(oom)(y(t)−v.getY(oom))+n.getDZ(oom)(z(t)−v.getZ(oom)) = 0}</li>
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
     * The vector that defines {@link #p} on the plane.
     */
    protected V3D_Vector pv;

    /**
     * For storing a point on the plane.
     */
    protected V3D_Point p;

    /**
     * The normal vector that defines the plane. This is perpendicular to the
     * plane.
     */
    protected V3D_Vector n;

    /**
     * For storing the equation of the plane.
     */
    protected transient Equation equation;

    /**
     * Create a new instance.
     *
     * @param pl The plane used to create this.
     */
    public V3D_Plane(V3D_Plane pl) {
        super(pl.env, new V3D_Vector(pl.offset));
        this.pv = new V3D_Vector(pl.pv);
        this.n = new V3D_Vector(pl.n);
    }

    /**
     * Create a new instance.
     *
     * @param p Used to initialise {@link #pv}.
     * @param n The normal of the plane.
     */
    public V3D_Plane(V3D_Point p, V3D_Vector n) {
        super(p.env, new V3D_Vector(p.offset));
        this.pv = new V3D_Vector(p.rel);
        this.p = new V3D_Point(p);
        this.n = new V3D_Vector(n);
    }

    /**
     * Create a new instance.
     *
     * @param l A line segment in the plane.
     * @param inplane A vector in the plane that is not a scalar multiple of the
     * vector of the line of l.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Plane(V3D_LineSegment l, V3D_Vector inplane, int oom,
            RoundingMode rm) {
        super(l.env, new V3D_Vector(l.offset));
        pv = new V3D_Vector(l.getP().rel);
        p = new V3D_Point(l.getP());
        n = l.l.v.getCrossProduct(inplane, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param p Used to initialise {@link #env}, {@link #offset}, {@link #pv},
     * and {@link #p}.
     * @param q A point coplanar to {@code p} and {@code r}, not collinear or 
     * equal to either {@code p} or {@code r}.
     * @param r A point coplanar to {@code p} and {@code q}, not collinear or 
     * equal to either {@code p} or {@code q}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Plane(V3D_Point p, V3D_Point q, V3D_Point r, int oom,
            RoundingMode rm) {
        super(p.env, new V3D_Vector(p.offset));
        V3D_Vector qv = q.getVector(oom, rm);
        V3D_Vector pq = qv.subtract(p.getVector(oom, rm), oom, rm);
        V3D_Vector qr = r.getVector(oom, rm).subtract(qv, oom, rm);
        this.pv = new V3D_Vector(p.rel);
        this.n = pq.getCrossProduct(qr, oom, rm);
    }

    /**
     * Create a new instance.{@code pv}, {@code qv} and {@code rv} must not
     * define collinear.
     *
     * @param env What {@link #env} is set to.
     * @param pv Used to initialise {@link #pv}.
     * @param qv A vector in the plane not collinear with {@code pv} or
     * {@code rv}.
     * @param rv A vector in the plane not collinear with {@code pv} or 
     * {@code qv}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Plane(V3D_Environment env, V3D_Vector pv, V3D_Vector qv,
            V3D_Vector rv, int oom, RoundingMode rm) {
        this(env, V3D_Vector.ZERO, pv, qv, rv, oom, rm);
    }

    /**
     * Create a new instance.{@code pv}, {@code qv} and {@code rv} must not
     * define collinear.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param pv Used to initialise {@link #pv}.
     * @param qv A vector in the plane not collinear with {@code pv} or
     * {@code rv}.
     * @param rv A vector in the plane not collinear with {@code pv} or 
     * {@code qv}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Plane(V3D_Environment env, V3D_Vector offset, V3D_Vector pv,
            V3D_Vector qv, V3D_Vector rv, int oom,
            RoundingMode rm) {
        this(env, pv.add(qv.getCrossProduct(rv, oom, rm), oom, rm), offset,
                pv, qv, rv, oom, rm);
    }

    /**
     * Create a new instance.{@code pv}, {@code qv} and {@code rv} must not
     * define collinear points on the plane.
     *
     * @param env What {@link #env} is set to.
     * @param ptv A point vector giving the direction of the normal vector.
     * @param offset What {@link #offset} is set to.
     * @param pv Used to initialise {@link #pv}.
     * @param qv A vector in the plane not collinear with {@code pv} or
     * {@code rv}.
     * @param rv A vector in the plane not collinear with {@code pv} or 
     * {@code qv}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Plane(V3D_Environment env, V3D_Vector ptv, V3D_Vector offset,
            V3D_Vector pv, V3D_Vector qv, V3D_Vector rv, int oom, RoundingMode rm) {
        super(env, new V3D_Vector(offset));
        V3D_Vector pq = qv.subtract(pv, oom, rm);
        if (pq.equals(V3D_Vector.ZERO)) {
            throw new RuntimeException("Cannot define plane as p equals q.");
        }
        V3D_Vector qr = rv.subtract(qv, oom, rm);
        if (qr.equals(V3D_Vector.ZERO)) {
            throw new RuntimeException("Cannot define plane as q equals r.");
        }
        this.pv = new V3D_Vector(pv);
        n = pq.getCrossProduct(qr, oom, rm);
        if (n.isZero()) {
            n = pq.reverse().getCrossProduct(qr, oom, rm);
        }
        V3D_Vector v;
        if (ptv.isZero()) {
            v = pv.add(qv, oom, rm).add(rv, oom, rm).reverse();
        } else {
            v = new V3D_Vector(ptv);
        }
        int direction = (n.getDotProduct(v, oom, rm)
                .divide(n.getDotProduct(n, oom, rm)))
                .compareTo(BigRational.ZERO);
        if (direction == -1) {
            n = n.reverse();
        }
    }

    /**
     * Create a new instance.
     *
     * @param pt A point giving the direction of the normal vector.
     * @param offset What {@link #offset} is set to.
     * @param pv Used to initialise {@link #pv}.
     * @param qv A vector in the plane not collinear with {@code pv} or
     * {@code rv}.
     * @param rv A vector in the plane not collinear with {@code pv} or 
     * {@code qv}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Plane(V3D_Point pt, V3D_Vector offset, V3D_Vector pv,
            V3D_Vector qv, V3D_Vector rv, int oom, RoundingMode rm) {
        this(pt.env, pt.getVector(oom, rm), offset, pv, qv, rv, oom, rm);
    }

    /**
     * Creates a new plane which is essentially the same as pl, but with the
     * offset specified.
     *
     * @param offset What {@link #offset} is set to.
     * @param pl The plane to use as a template.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Plane(V3D_Vector offset, V3D_Plane pl, int oom, RoundingMode rm) {
        super(pl.env, offset);
        n = pl.getN();
        if (offset.equals(pl.offset, oom, rm)) {
            pv = new V3D_Vector(pl.pv);
        } else {
            pv = pl.pv.add(pl.offset, oom, rm).subtract(offset, oom, rm);
        }
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
                + pad + "pv=" + pv.toString(pad) + "\n"
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
                + pad + "p=" + pv.toStringSimple(pad) + ",\n"
                + pad + "n=" + n.toStringSimple(pad);
    }

    /**
     * @return A copy of {@link #n}.
     */
    public final V3D_Vector getN() {
        return new V3D_Vector(n);
    }

    /**
     * @return {@link #pv} with {@link #offset} and rotations applied.
     */
    public final V3D_Point getP() {
        if (p == null) {
            p = new V3D_Point(env, offset, pv);
        }
        return p;
    }

    /**
     * Find a perpendicular vector.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A perpendicular vector.
     */
    public final V3D_Vector getPV(int oom, RoundingMode rm) {
//        /**
//         * Magnitude method adapted from:
//         * https://math.stackexchange.com/questions/137362/how-to-find-perpendicular-vector-to-another-vector
//         */
//        V3D_Vector v1 = new V3D_Vector(Math_BigRationalSqrt.ZERO, n.dz, n.dy.negate());
//        V3D_Vector v2 = new V3D_Vector(n.dz.negate(), Math_BigRationalSqrt.ZERO, n.dx);
//        V3D_Vector v3 = new V3D_Vector(n.dy.negate(), n.dx, Math_BigRationalSqrt.ZERO);
//        BigRational mv1 = v1.getMagnitudeSquared();
//        BigRational mv2 = v2.getMagnitudeSquared();
//        BigRational mv3 = v3.getMagnitudeSquared();
//        if (mv1.compareTo(mv2) == 1) {
//            if (mv1.compareTo(mv3) == 1) {
//                return v1;
//            } else {
//                return v3;
//            }
//        } else {
//            if (mv2.compareTo(mv3) == 1) {
//                return v2;
//            } else {
//                return v3;
//            }
//        }
        /**
         * Alternative method adapted from:
         * https://math.stackexchange.com/questions/137362/how-to-find-perpendicular-vector-to-another-vector
         */
        V3D_Vector v = new V3D_Vector(n.dz, n.dz, n.dx.negate().add(n.dy.negate(), oom, rm));
        if (v.isZero()) {
            return new V3D_Vector(n.dy.negate().add(n.dz.negate(), oom, rm), n.dx, n.dx);
        }
        return v;
    }

    /**
     * Get another point on the plane.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A point on the plane.
     */
    public final V3D_Point getQ(int oom, RoundingMode rm) {
        return getQ(getPV(oom, rm), oom, rm);
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
        return new V3D_Point(env, offset, this.pv.add(pv, oom, rm));
    }

    /**
     * Get another point on the plane.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A point on the plane.
     */
    public final V3D_Point getR(int oom, RoundingMode rm) {
        return getR(getPV(oom, rm), oom, rm);
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
        return new V3D_Point(env, offset, this.pv.add(pvx, oom, rm));
    }

    /**
     * For getting the equation of the plane.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The equation of the plane as a String.
     */
    public String getEquationString(int oom, RoundingMode rm) {
        if (equation == null) {
            equation = new Equation(oom, rm);
        }
        return equation.coeffs[0].toRationalString() + " * x + "
                + equation.coeffs[1].toRationalString() + " * y + "
                + equation.coeffs[2].toRationalString() + " * z + "
                + equation.coeffs[3].toRationalString() + " = 0";
    }

    /**
     * For storing the coefficents of the plane equation along with the
     * precision at which these were calculated.
     */
    public class Equation {

        /**
         * The equation coefficients for the parametric equation of the plane:
         * ax + bx + cx + d
         * <ul>
         * <li>[0] is a</li>
         * <li>[1] is b</li>
         * <li>[2] is c</li>
         * <li>[3] is d</li>
         * </ul>
         */
        public BigRational[] coeffs;

        /**
         * The order of magnitude used to calculate the coefficients.
         */
        public int oom;

        /**
         * The RoundingMode used to calculate the coefficients.
         */
        public RoundingMode rm;

        /**
         * Create a new instance.
         *
         * @param oom Used to set {@link #oom}.
         * @param rm Used to set {@link #rm}.
         */
        public Equation(int oom, RoundingMode rm) {
            this.oom = oom;
            this.rm = rm;
            coeffs = new BigRational[4];
//        // Ensure n is not null
//        n = getN(oom, rm);
            BigRational ndxsr = n.dx.getSqrt(oom, rm);
            BigRational ndysr = n.dy.getSqrt(oom, rm);
            BigRational ndzsr = n.dz.getSqrt(oom, rm);
//        BigRational k = (ndxsr.multiply(pl.getX(oom))
//                .add(ndysr.multiply(pl.getY(oom)))
//                .add(ndzsr.multiply(pl.getZ(oom)))).negate();
            V3D_Point tp = getP();
            BigRational k = (ndxsr.multiply(tp.getX(oom, rm))
                    .add(ndysr.multiply(tp.getY(oom, rm)))
                    .add(ndzsr.multiply(tp.getZ(oom, rm)))).negate();
//        BigRational k = (ndxsr.multiply(pl.getX(oom))
//                .subtract(ndysr.multiply(pl.getY(oom)))
//                .subtract(ndzsr.multiply(pl.getZ(oom))));
//        BigRational k = ndxsr.multiply(pl.getX(oom))
//                .add(ndysr.multiply(pl.getY(oom)))
//                .add(ndzsr.multiply(pl.getZ(oom)));
            coeffs[0] = ndxsr;
            coeffs[1] = ndysr;
            coeffs[2] = ndzsr;
            coeffs[3] = k;
        }

        @Override
        public String toString() {
            return "(" + coeffs[0] + " * x)"
                    + " + (" + coeffs[1] + " * y)"
                    + " + (" + coeffs[2] + " * z)"
                    + " + " + coeffs[3];
        }
    }

    /**
     * For getting the equation of the plane.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The equation of the plane as a String.
     */
    public Equation getEquation(int oom, RoundingMode rm) {
        if (equation == null) {
            equation = new Equation(oom, rm);
        } else {
            if (equation.oom < oom) {
                return equation;
            } else {
                if (equation.oom == oom && equation.rm == rm) {
                    return equation;
                } else {
                    equation = new Equation(oom, rm);
                }
            }
        }
        return equation;
    }

    /**
     * Plug the point into the plane equation.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff the geometry is intersected by {@code pv}.
     */
    public boolean intersects(V3D_Point pt, int oom, RoundingMode rm) {
        oom -= 2;
        equation = getEquation(oom, rm);
        return ((equation.coeffs[0].multiply(pt.getX(oom, rm)))
                .add(equation.coeffs[1].multiply(pt.getY(oom, rm)))
                .add(equation.coeffs[2].multiply(pt.getZ(oom, rm)))
                .add(equation.coeffs[3])).compareTo(BigRational.ZERO) == 0;
    }

    /**
     * Identify if this is intersected by point {@code pt} using the matrices
     * approach described here:
     * https://math.stackexchange.com/questions/684141/check-if-a-point-is-on-a-plane-minimize-the-use-of-multiplications-and-divisio/684580#684580
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff the geometry is intersected by {@code pv}.
     */
    public boolean intersectsAlternative(V3D_Point pt, int oom, RoundingMode rm) {
        BigRational[][] m = new BigRational[4][4];
        V3D_Point tp = getP();
        if (tp.isOrigin(oom, rm)) {
            V3D_Point tpt = new V3D_Point(pt);
            tpt.translate(V3D_Vector.IJK, oom, rm);
            V3D_Point ttp = new V3D_Point(getP());
            ttp.translate(V3D_Vector.IJK, oom, rm);
            V3D_Vector pv = getPV(oom, rm);
            V3D_Point ttq = getQ(pv, oom, rm);
            ttq.translate(V3D_Vector.IJK, oom, rm);
            V3D_Point ttr = getR(pv, oom, rm);
            ttr.translate(V3D_Vector.IJK, oom, rm);
            m[0][0] = ttp.getX(oom, rm);
            m[1][0] = ttp.getY(oom, rm);
            m[2][0] = ttp.getZ(oom, rm);
            m[3][0] = BigRational.ONE;
            m[0][1] = ttq.getX(oom, rm);
            m[1][1] = ttq.getY(oom, rm);
            m[2][1] = ttq.getZ(oom, rm);
            m[3][1] = BigRational.ONE;
            m[0][2] = ttr.getX(oom, rm);
            m[1][2] = ttr.getY(oom, rm);
            m[2][2] = ttr.getZ(oom, rm);
            m[3][2] = BigRational.ONE;
            m[0][3] = tpt.getX(oom, rm);
            m[1][3] = tpt.getY(oom, rm);
            m[2][3] = tpt.getZ(oom, rm);
            m[3][3] = BigRational.ONE;
            return new Math_Matrix_BR(m).getDeterminant().compareTo(BigRational.ZERO) == 0;
        } else {
            //V3D_Vector pv = getPV(oom, rm);
            V3D_Point tq = getQ(pv, oom, rm);
            V3D_Point tr = getR(pv, oom, rm);
            m[0][0] = tp.getX(oom, rm);
            m[1][0] = tp.getY(oom, rm);
            m[2][0] = tp.getZ(oom, rm);
            m[3][0] = BigRational.ONE;
            m[0][1] = tq.getX(oom, rm);
            m[1][1] = tq.getY(oom, rm);
            m[2][1] = tq.getZ(oom, rm);
            m[3][1] = BigRational.ONE;
            m[0][2] = tr.getX(oom, rm);
            m[1][2] = tr.getY(oom, rm);
            m[2][2] = tr.getZ(oom, rm);
            m[3][2] = BigRational.ONE;
            m[0][3] = pt.getX(oom, rm);
            m[1][3] = pt.getY(oom, rm);
            m[2][3] = pt.getZ(oom, rm);
            m[3][3] = BigRational.ONE;
            return new Math_Matrix_BR(m).getDeterminant().compareTo(BigRational.ZERO) == 0;
        }
    }

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
            if (!pl.intersects(pt, oom, rm)) {
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
    public static boolean isCoplanar(int oom, RoundingMode rm,
            V3D_Point... points) {
        // For the points to be in a plane at least one must not be collinear.
        if (V3D_Point.equals(oom, rm, points)) {
            return false;
        }
        if (!V3D_Line.isCollinear(oom, rm, points)) {
            V3D_Plane p = getPlane(oom, rm, points);
            return isCoplanar(oom, rm, p, points);
        }
        return false;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param ps The points from which a plane is to be derived.
     * @return A plane that may or may not contain all the points or
     * {@code null} if there is no such plane (if the points are coincident or
     * collinear).
     */
    public static V3D_Plane getPlane(int oom, RoundingMode rm,
            V3D_Point... ps) {
        V3D_Line l = V3D_Line.getLine(oom, rm, ps);
        if (l == null) {
            return null;
        }
        BigRational max = BigRational.ZERO;
        V3D_Point pt = null;
        for (var p : ps) {
            BigRational d = l.getDistanceSquared(p, oom, rm);
            if (d.compareTo(max) == 1) {
                pt = p;
                max = d;
            }
        }
        if (max.compareTo(BigRational.ZERO) == 1) {
            return new V3D_Plane(l.getP(), l.getQ(oom, rm), pt, oom, rm);
        } else {
            return null;
        }
    }

//    /**
//     * @param l The line to test if it is on the plane.
//     * @return {@code true} If {@code pt} is on the plane.
//     */
//    public boolean isOnPlane(V3D_Line l) {
//        return getIntersect(l.getP()) && getIntersect(l.getQ());
//    }
    /**
     * @param l The line to test if it is on the plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} If {@code pt} is on the plane.
     */
    public boolean isOnPlane(V3D_Line l, int oom, RoundingMode rm) {
        return intersects(l.getP(), oom, rm)
                && intersects(l.getQ(oom, rm), oom, rm);
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
    public V3D_Geometry getIntersectAlternative(V3D_Line l, int oom, RoundingMode rm) {
        // Special case
        if (isParallel(l, oom, rm)) {
            if (isOnPlane(l, oom, rm)) {
                return l;
            }
            return null;
        }
        BigRational dot = n.getDotProduct(l.v, oom, rm);
        if (dot.compareTo(BigRational.ZERO) == 0) {
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
                if (w.isZero()) {
                    return tp;
                }
                BigRational fac = n.getDotProduct(w, oom, rm).divide(dot).negate();
                if (fac == BigRational.ZERO) {
                    return tp;
                }
                V3D_Vector u2 = l.v.multiply(fac, oom, rm);
                tlp.translate(u2, oom, rm);
                tlp.translate(l.v.reverse(), oom, rm);
                return tlp;
            } else {
                V3D_Vector w = new V3D_Vector(tp, lp, oom, rm);
                if (w.isZero()) {
                    return tp;
                }
                BigRational fac = n.getDotProduct(w, oom, rm).divide(dot).negate();
                V3D_Vector u2 = l.v.multiply(fac, oom, rm);
                V3D_Point p00 = new V3D_Point(lp);
                p00.translate(u2, oom, rm);
                return p00;
            }
        }
    }
    
    /**
     * Get the intersection between the geometry and the line {@code l}.
     * https://stackoverflow.com/questions/5666222/3d-line-plane-intersection
     * 
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_Geometry getIntersect(V3D_Line l, int oom, RoundingMode rm) {
        int oomn6 = oom - 6;
        if (isParallel(l, oomn6, rm)) {
            if (isOnPlane(l, oomn6, rm)) {
                return l;
            } else {
                return null;
            }
        } else {
            return getIntersectNonParallel(l, oom, rm);
        }
    }

    /**
     * Get the intersection with {@code l} which is assumed not to be parallel
     * with this.
     *
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_Point getIntersectNonParallel(V3D_Line l, int oom, RoundingMode rm) {
        int oomn6 = oom - 6;
        // Are either of the points of l on the plane.
        //V3D_Point lp = l.getP(oom);
        V3D_Point lp = l.getP();
        if (intersects(lp, oomn6, rm)) {
            return lp;
        }
        V3D_Point lq = l.getQ(oom, rm);
        if (intersects(lq, oomn6, rm)) {
            return lq;
        }
        //V3D_Vector lv = l.getV(oomN2, rm);
        V3D_Vector lv = l.v;
        BigRational[][] m = new BigRational[4][4];
        m[0][0] = BigRational.ONE;
        m[0][1] = BigRational.ONE;
        m[0][2] = BigRational.ONE;
        m[0][3] = BigRational.ONE;
        V3D_Point tp = getP();
        V3D_Vector pv = getPV(oom, rm);
        V3D_Point tq = getQ(pv, oom, rm);
        V3D_Point tr = getR(pv, oom, rm);
        m[1][0] = tp.getX(oomn6, rm);
        m[1][1] = tq.getX(oomn6, rm);
        m[1][2] = tr.getX(oomn6, rm);
        m[1][3] = lp.getX(oomn6, rm);
        m[2][0] = tp.getY(oomn6, rm);
        m[2][1] = tq.getY(oomn6, rm);
        m[2][2] = tr.getY(oomn6, rm);
        m[2][3] = lp.getY(oomn6, rm);
        m[3][0] = tp.getZ(oomn6, rm);
        m[3][1] = tq.getZ(oomn6, rm);
        m[3][2] = tr.getZ(oomn6, rm);
        m[3][3] = lp.getZ(oomn6, rm);
        Math_Matrix_BR numm = new Math_Matrix_BR(m);
        m[0][0] = BigRational.ONE;
        m[0][1] = BigRational.ONE;
        m[0][2] = BigRational.ONE;
        m[0][3] = BigRational.ZERO;
        m[1][0] = tp.getX(oomn6, rm);
        m[1][1] = tq.getX(oomn6, rm);
        m[1][2] = tr.getX(oomn6, rm);
        m[1][3] = lv.getDX(oomn6, rm);
        m[2][0] = tp.getY(oomn6, rm);
        m[2][1] = tq.getY(oomn6, rm);
        m[2][2] = tr.getY(oomn6, rm);
        m[2][3] = lv.getDY(oomn6, rm);
        m[3][0] = tp.getZ(oomn6, rm);
        m[3][1] = tq.getZ(oomn6, rm);
        m[3][2] = tr.getZ(oomn6, rm);
        m[3][3] = lv.getDZ(oomn6, rm);
        Math_Matrix_BR denm = new Math_Matrix_BR(m);
        BigRational t;
        BigRational denmdet = denm.getDeterminant();
        BigRational nummdet = numm.getDeterminant();
        if (denmdet.isZero()) {
            if (Math_BigRational.equals(nummdet, BigRational.ZERO, oom)) {
                t = BigRational.ONE;
            } else {
                return null;
            }
        } else {
            t = nummdet.divide(denmdet).negate();
        }
        V3D_Point res = new V3D_Point(env,
                lp.getX(oomn6, rm).add(lv.getDX(oomn6, rm).multiply(t)),
                lp.getY(oomn6, rm).add(lv.getDY(oomn6, rm).multiply(t)),
                lp.getZ(oomn6, rm).add(lv.getDZ(oomn6, rm).multiply(t)));
        return res;
    }
    
    /**
     * Compute and return the intersection with {@code r}. {@code null} is 
     * returned if there is no intersection.
     * See also V3D_Ray#getIntersect(V3D_Plane, int, RoundingMode).
     *
     * @param r The ray to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_Geometry getIntersect(V3D_Ray r, int oom, RoundingMode rm) {
        V3D_Geometry g = getIntersect(r.l, oom, rm);
        if (g == null) {
            return null;
        } else if (g instanceof V3D_Point gp) {
            if (r.getPl().isOnSameSide(gp, r.l.getQ(oom, rm), oom, rm)) {
//                // Now get the closest point on the plane as gp is on the 
//                // ray but may not be on the plane.
//                System.out.println(gp);
//                System.out.println(getPointOfProjectedIntersection(gp, oom, rm));
//                return getPointOfProjectedIntersection(gp, oom, rm);
                return gp;
            } else {
                return null;
            }
        } else {
            return r;
        }
    }

    /**
     * Compute and return the intersection with {@code l}.
     *
     * @param l The line segment to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersect(V3D_LineSegment l,
            int oom, RoundingMode rm) {
        if (isParallel(l.l, oom, rm)) {
            if (isOnPlane(l.l, oom, rm)) {
                return l;
            } else {
                return null;
            }
        } else {
            return getIntersectNonParallel(l, oom, rm);
        }
    }

    /**
     * Compute and return the intersection with {@code l} which is assumed to be
     * not parallel to the plane.
     *
     * @param l The line segment to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_Point getIntersectNonParallel(V3D_LineSegment l,
            int oom, RoundingMode rm) {
        V3D_Point pt = getIntersectNonParallel(l.l, oom, rm);
        if (l.getPPL().isOnSameSide(pt, l.getQ(oom, rm), oom, rm)
                && l.getQPL(oom, rm).isOnSameSide(pt, l.getP(), oom, rm)) {
            return pt;
        } else {
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
    public V3D_Geometry getIntersect(V3D_Plane pl, int oom, RoundingMode rm) {
        /**
         * Calculate the cross product of the normal vectors to get the
         * direction of the line.
         */
        V3D_Vector v = n.getCrossProduct(pl.n, oom, rm);
        /**
         * Check special cases.
         */
        if (v.isZero()) {
            // The planes are parallel.
            if (pl.intersects(getP(), oom, rm)) {
                // The planes are the same.
                return this;
            }
            return null;
        }
        V3D_Point pi = pl.getPointOfProjectedIntersection(getP(), oom, rm);
        return new V3D_Line(pi, v);
    }

    /**
     * Compute and return the intersection with {@code pl} which is not
     * parallel.
     *
     * @param pl The plane to intersect.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code this} and {@code pl}
     */
    public V3D_Line getIntersectNonParallel(V3D_Plane pl, int oom, RoundingMode rm) {
        /*
         * 1. Calculate a projection of a point onto the plane {@code pl}.
         * 2. Calculate the cross product of the normal vectors to get the
         * direction of the line.
         * Together these define the intersection.
         */
        return new V3D_Line(pl.getPointOfProjectedIntersection(getP(), oom, rm),
                n.getCrossProduct(pl.n, oom, rm));
    }

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
    public V3D_Geometry getIntersect(V3D_Plane pl1, V3D_Plane pl2, int oom,
            RoundingMode rm) {
        V3D_Geometry g = getIntersect(pl1, oom, rm);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_Plane gp) {
                return gp.getIntersect(pl2, oom, rm);
            } else {
                return pl2.getIntersect((V3D_Line) g, oom, rm);
            }
        }
    }

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
        return n.isOrthogonal(l.v, oom, rm);
//        if (n.isOrthogonal(l.v, oom, rm)) {
//            return true;
//        }
//        return n.getDotProduct(l.v, oom, rm).isZero();
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
        if (n.getDotProduct(pl.n, oom, rm).compareTo(BigRational.ZERO) == 1) {
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
            if (pl.intersects(getP(), oom, rm)) {
                if (intersects(pl.getP(), oom, rm)) {
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
        V3D_Vector pv = getPV(oom, rm);
        V3D_Point tq = getQ(pv, oom, rm);
        V3D_Point tr = getR(pv, oom, rm);
        BigRational[][] m = new BigRational[3][3];
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
    public BigRational getDistance(V3D_Point pt, int oom, RoundingMode rm) {
        if (this.intersects(pt, oom, rm)) {
            return BigRational.ZERO;
        }
        return new Math_BigRationalSqrt(getDistanceSquared(pt, true, oom, rm),
                oom, rm).getSqrt(oom, rm);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The distance from {@code this} to {@code pl}.
     */
    public BigRational getDistanceSquared(V3D_Point pt, int oom, RoundingMode rm) {
        if (this.intersects(pt, oom, rm)) {
            return BigRational.ZERO;
        }
        return getDistanceSquared(pt, true, oom, rm);
    }

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
    public BigRational getDistanceSquared(V3D_Point pt, boolean noInt,
            int oom, RoundingMode rm) {
        int oomn4 = oom - 4;
        V3D_Vector v = new V3D_Vector(pt, getP(), oomn4, rm);
        V3D_Vector u = n.getUnitVector(oomn4, rm);
        return v.getDotProduct(u, oomn4, rm).pow(2);
    }

    /**
     * Get the minimum distance to {@code pv}.
     *
     * @param p A plane.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code pv}.
     */
    public BigRational getDistance(V3D_Plane p, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm)
                .getSqrt(oom, rm);
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
    public BigRational getDistanceSquared(V3D_Plane pl, int oom, RoundingMode rm) {
        if (isParallel(pl, oom, rm)) {
            return pl.getDistanceSquared(getP(), oom, rm);
        }
        return BigRational.ZERO;
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistance(V3D_Line l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom, rm), oom, rm)
                .getSqrt();
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistanceSquared(V3D_Line l, int oom, RoundingMode rm) {
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
        return Math_BigRational.toBigDecimal(new Math_BigRationalSqrt(
                getDistanceSquared(l, oom, rm), oom, rm).getSqrt(oom, rm),
                oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistanceSquared(V3D_LineSegment l, int oom, RoundingMode rm) {
        BigRational lpd = getDistanceSquared(l.getP(), oom, rm);
        BigRational lqd = getDistanceSquared(l.getQ(oom, rm), oom, rm);
        return BigRational.min(lpd, lqd);
    }

    @Override
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        this.equation = null;
        if (p != null) {
            p.translate(v, oom, rm);
        }
    }

    @Override
    public V3D_Plane rotate(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd,
            BigRational theta, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V3D_Plane(this);
        } else {
            return rotateN(ray, uv, bd, theta, oom, rm);
        }
    }

    @Override
    public V3D_Plane rotateN(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd,
            BigRational theta, int oom, RoundingMode rm) {
        return new V3D_Plane(getP().rotateN(ray, uv, bd, theta, oom, rm),
                n.rotateN(uv, bd, theta, oom, rm));
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
        if (intersects(pt, oom, rm)) {
            return pt;
        } else {
            V3D_Line l = new V3D_Line(pt, n);
            return (V3D_Point) getIntersect(l, oom, rm);
        }
    }

    /**
     * Check a and b are on the same side of this.If either are on the boundary
     * then return {@code true}.
     *
     * @param a A point.
     * @param b Another point. The triangle to check the points to see if they
     * are all on the same side of a line that getIntersect the edge of another
     * triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return {@code true} if an intersection is found and {@code false}
     * otherwise.
     */
    public boolean isOnSameSide(V3D_Point a, V3D_Point b, int oom, RoundingMode rm) {
        int aside = getSideOfPlane(a, oom, rm);
        if (aside == 0) {
            return true;
        }
        int bside = getSideOfPlane(b, oom, rm);
        if (bside == 0) {
            return true;
        }
        return aside == bside;
//        //n = getN(oom, rm);
//        boolean aq = false;
//        V3D_Vector av = a.getVector(oom, rm);
//        //V3D_Vector pv = getP().getVector(oom, rm);
//        V3D_Vector pv = getP().getVector(oom, rm).getUnitVector(oom, rm);
//        V3D_Vector avpv = pv.subtract(av, oom, rm);
//        if (avpv.isZero()) {
//            //pv = getQ(pv, oom, rm).getVector(oom, rm);
//            pv = getQ(pv, oom, rm).getVector(oom, rm);
//            avpv = pv.subtract(av, oom, rm);
//            aq = true;
//        }
//        int avd = n.getDotProduct(avpv, oom, rm).compareTo(BigRational.ZERO);
//        if (avd == 0) {
//            return true;
//        }        
//        boolean bq = false;
//        V3D_Vector bv = b.getVector(oom, rm);
//        V3D_Vector bvpv = pv.subtract(bv, oom, rm);
//        if (bvpv.isZero()) {
//            pv = getQ(pv, oom, rm).getVector(oom, rm);
//            bvpv = pv.subtract(bv, oom, rm);
//            bq = true;
//        }
//        int bvd = n.getDotProduct(bvpv, oom, rm).compareTo(BigRational.ZERO);
//        if (bvd == 0) {
//            return true;
//        }
//        if (aq && bq) {
//            avpv = pv.subtract(av, oom, rm);
//            avd = n.getDotProduct(avpv, oom, rm).compareTo(BigRational.ZERO);
//        }
//        return avd == bvd;
//        
//        //int avd = n.getDotProduct(av.add(this.pl.reverse(), oom), oom).compareTo(BigRational.ZERO);
//        int avd = n.getDotProduct(pv.subtract(av, oom, rm), oom, rm).compareTo(BigRational.ZERO);
//        //int avd = this.pl.add(av.reverse(), oom).getDotProduct(n, oom).compareTo(BigRational.ZERO);
//        
//        V3D_Vector bv = b.getVector(oom, rm);
//        int bvd = n.getDotProduct(pv.subtract(bv, oom, rm), oom, rm).compareTo(BigRational.ZERO);
//
//        return avd == bvd;

//        if (avd == 0) {
//            return true;
//        } else {
//            V3D_Vector bv = b.getVector(oom, rm);
//            int bvd = n.getDotProduct(pv.subtract(bv, oom, rm), oom, rm).compareTo(BigRational.ZERO);
//            //int bvd = this.pl.add(bv.reverse(), oom).getDotProduct(n, oom).compareTo(BigRational.ZERO);
//            if (bvd == 0) {
//                return true;
//            }
//            return avd == bvd;
//        }
    }

    /**
     * Check a and b are on the same side of this.If either are on the boundary
     * then return {@code true}.
     *
     * @param a A point.
     * @param b Another point. The triangle to check the points to see if they
     * are all on the same side of a line that getIntersect the edge of another
     * triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return {@code true} if an intersection is found and {@code false}
     * otherwise.
     */
    public boolean isOnSameSideNotOn(V3D_Point a, V3D_Point b, int oom, RoundingMode rm) {
        int aside = getSideOfPlane(a, oom, rm);
        if (aside == 0) {
            return false;
        }
        int bside = getSideOfPlane(b, oom, rm);
        if (bside == 0) {
            return false;
        }
        return aside == bside;
    }

    /**
     * Plug the coordinates of pt into the plane equation.
     *
     * @param pt The point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return 1 if pt is on the same side of the plane that the normal points
     * towards. 0 if pt is on the plane. -1 if pt is on the other side of the
     * plane that the normal points towards.
     */
    public int getSideOfPlane(V3D_Point pt, int oom, RoundingMode rm) {
        BigRational[] coeffs = getEquation(oom, rm).coeffs;
        return (coeffs[0].multiply(pt.getX(oom, rm))
                .add(coeffs[1].multiply(pt.getY(oom, rm)))
                .add(coeffs[2].multiply(pt.getZ(oom, rm))).add(coeffs[3]))
                .compareTo(BigRational.ZERO);
    }

    /**
     * Check if all points in pts are on the same side of this.
     *
     * @param pts The points to check.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     * @return {@code true} iff all points in pts are on or are on the same side
     * of this.
     */
    protected boolean allOnSameSide(int oom, RoundingMode rm, V3D_Point... pts) {
        // Special cases
        if (pts == null) {
            return false;
        }
        switch (pts.length) {
            case 0 -> {
                return false;
            }
            case 1 -> {
                return true;
            }
            default -> {
                for (int i = 1; i < pts.length; i++) {
                    if (!isOnSameSide(pts[0], pts[i], oom, rm)) {
                        return false;
                    }
                }
                return true;
            }
        }
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
    protected boolean allOnSameSideNotOn(int oom, RoundingMode rm, V3D_Point... pts) {
        // Special cases
        if (pts == null) {
            return false;
        }
        switch (pts.length) {
            case 0 -> {
                return false;
            }
            case 1 -> {
                return true;
            }
            default -> {
                for (int i = 1; i < pts.length; i++) {
                    if (!isOnSameSideNotOn(pts[0], pts[i], oom, rm)) {
                        return false;
                    }
                }
                return true;
            }
        }
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
