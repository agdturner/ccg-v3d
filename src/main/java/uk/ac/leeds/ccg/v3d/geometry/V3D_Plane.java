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
 {@link #pv} and the normal {@link #n}.A plane be constructed in numerous ways
 including by using three points that are not collinear. The equation of the plane is:
 <ul>
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
        this.pv = new V3D_Vector(l.getP().rel);
        this.p = new V3D_Point(l.getP());
        this.n = l.l.v.getCrossProduct(inplane, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param p Used to initialise {@link #env}, {@link #offset}, {@link #pv},
     * and {@link #p}.
     * @param q A point coplanar to p and r, not collinear or equal to either 
     * p or r.
     * @param r A point coplanar to p and q, not collinear or equal to either 
     * p or q.
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
     * define collinear points on the plane.
     *
     * @param env What {@link #env} is set to.
     * @param pv Used to initialise {@link #pv}.
     * @param qv Defines a point on the plane.
     * @param rv Defines a point on the plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Plane(V3D_Environment env, V3D_Vector pv, V3D_Vector qv, 
            V3D_Vector rv, int oom, RoundingMode rm) {
        this(env, V3D_Vector.ZERO, pv, qv, rv, oom, rm);
    }

    /**
     * Create a new instance.{@code pv}, {@code qv} and {@code rv} must not 
 define collinear points on the plane.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param pv Used with {@code offset} to initialise {@link #pv}.
     * @param qv Defines a point on the plane.
     * @param rv Defines a point on the plane.
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
     * @param qv Defines a point on the plane.
     * @param rv Defines a point on the plane.
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
        this.n = pq.getCrossProduct(qr, oom, rm);
        if (this.n.isZero()) {
            int debug = 1;
            this.n = pq.reverse().getCrossProduct(qr, oom, rm);
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
        //this.n = qr.getCrossProduct(pq, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param pt A point giving the direction of the normal vector.
     * @param offset What {@link #offset} is set to.
     * @param p Used to initialise {@link #pv}.
     * @param q A point coplanar to pl and r, not collinear to both pl and r,
     * and not equal to pl or r.
     * @param r A point coplanar to pl and v, not collinear to both pl and v,
 and not equal to pl or v.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Plane(V3D_Point pt, V3D_Vector offset, V3D_Vector p,
            V3D_Vector q, V3D_Vector r, int oom, RoundingMode rm) {
        this(pt.env, pt.getVector(oom, rm), offset, p, q, r, oom, rm);
    }

    /**
     * Creates a new plane which is essentially the same as pl, but with the 
     * offset specified.
     * 
     * @param offset What {@link #offset} is set to.
     * @param pl  The plane to use as a template.
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
                + pad + "p=" + pv.toString(pad) + "\n"
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
            V3D_Vector pv = getPV(oom, rm);
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
    //@Override
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
//    public V3D_Geometry getIntersect(V3D_Line l, int oom, RoundingMode rm) {
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
//        if (this.getIntersect(lp, oom, rm)) {
//            return lp;
//        }
//        V3D_Point lq = l.getQ(oom, rm);
//        if (this.getIntersect(lq, oom, rm)) {
//            return lq;
//        }
//        V3D_Point tp = getP();
//        V3D_Vector pv = getPAsVector();
//        V3D_Point tq = getQ(pv, oom, rm);
//        V3D_Point tr = getR(pv, oom, rm);
//        // Are any of these points on the line?
//        if (l.getIntersect(tp, oom, rm)) {
//            return tp;
//        }
//        if (l.getIntersect(tp, oom, rm)) {
//            return tq;
//        }
//        if (l.getIntersect(tp, oom, rm)) {
//            return tr;
//        }
//        
//        int oomN6 = oom - 6;
//        //V3D_Vector lv = l.getV(oomN6, rm);
//        V3D_Vector lv = l.v;
//        BigRational[][] m = new BigRational[4][4];
//        m[0][0] = BigRational.ONE;
//        m[0][1] = BigRational.ONE;
//        m[0][2] = BigRational.ONE;
//        m[0][3] = BigRational.ONE;
////        m[1][0] = pl.getX(oom);
////        m[1][1] = qv.getX(oom);
////        m[1][2] = r.getX(oom);
////        m[1][3] = lp.getX(oom);
////        m[2][0] = pl.getY(oom);
////        m[2][1] = qv.getY(oom);
////        m[2][2] = r.getY(oom);
////        m[2][3] = lp.getY(oom);
////        m[3][0] = pl.getZ(oom);
////        m[3][1] = qv.getZ(oom);
////        m[3][2] = r.getZ(oom);
////        m[3][3] = lp.getZ(oom);
////        V3D_Point tp = getP();
////        V3D_Vector pv = getPAsVector();
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
//        m[0][0] = BigRational.ONE;
//        m[0][1] = BigRational.ONE;
//        m[0][2] = BigRational.ONE;
//        m[0][3] = BigRational.ZERO;
////        m[1][0] = pl.getX(oom);
////        m[1][1] = qv.getX(oom);
////        m[1][2] = r.getX(oom);
////        m[1][3] = lv.getDX(oom);
////        m[2][0] = pl.getY(oom);
////        m[2][1] = qv.getY(oom);
////        m[2][2] = r.getY(oom);
////        m[2][3] = lv.getDY(oom);
////        m[3][0] = pl.getZ(oom);
////        m[3][1] = qv.getZ(oom);
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
//        if (denm.getDeterminant().compareTo(BigRational.ZERO) == 0) {
//            System.out.println("Determinant is zero in getIntersect");
//            System.out.println("Plane:");
//            System.out.println(this.toString());
//            System.out.println("Line:");
//            System.out.println(l.toString());
//            return null;
//        }
//
//        BigRational t = numm.getDeterminant().divide(denm.getDeterminant()).negate();
////        V3D_Point res = new V3D_Point(e,
////                lp.getX(oomN6, rm).add(lv.getDX(oomN6, rm).multiply(t)),
////                lp.getY(oomN6, rm).add(lv.getDY(oomN6, rm).multiply(t)),
////                lp.getZ(oomN6, rm).add(lv.getDZ(oomN6, rm).multiply(t)));
//        V3D_Point res = new V3D_Point(e,
//                lp.getX(oomN6, rm).add(lv.getDX(oomN6, rm).multiply(t)),
//                lp.getY(oomN6, rm).add(lv.getDY(oomN6, rm).multiply(t)),
//                lp.getZ(oomN6, rm).add(lv.getDZ(oomN6, rm).multiply(t)));
//        if (false) {
//            // Check if res is on the line.
//            if (!l.getIntersect(res, oom, rm)) {
//                System.out.println("Not on line! - l.getIntersect(r, oom, rm)");
//            }
//            if (!getIntersect(res, oom, rm)) {
//                System.out.println("Not on plane! - !getIntersect(r, oom, rm)");
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
////        BigRational num = d.getDotProduct(this.n);
////        BigRational den = l.v.getDotProduct(this.n);
////        BigRational t = num.divide(den);
////        return new V3D_Point(
////                l.pl.getX(oom).subtract(l.v.getDX(oom).multiply(t)),
////                l.pl.getY(oom).subtract(l.v.getDY(oom).multiply(t)),
////                l.pl.getZ(oom).subtract(l.v.getDZ(oom).multiply(t)));
//    }
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
            return getIntersect0(l, oom, rm);
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
    public V3D_Point getIntersect0(V3D_Line l, int oom, RoundingMode rm) {
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
//        m[1][0] = pl.getX(oom);
//        m[1][1] = v.getX(oom);
//        m[1][2] = r.getX(oom);
//        m[1][3] = lp.getX(oom);
//        m[2][0] = pl.getY(oom);
//        m[2][1] = v.getY(oom);
//        m[2][2] = r.getY(oom);
//        m[2][3] = lp.getY(oom);
//        m[3][0] = pl.getZ(oom);
//        m[3][1] = v.getZ(oom);
//        m[3][2] = r.getZ(oom);
//        m[3][3] = lp.getZ(oom);
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
//        m[1][0] = pl.getX(oom);
//        m[1][1] = v.getX(oom);
//        m[1][2] = r.getX(oom);
//        m[1][3] = lv.getDX(oom);
//        m[2][0] = pl.getY(oom);
//        m[2][1] = v.getY(oom);
//        m[2][2] = r.getY(oom);
//        m[2][3] = lv.getDY(oom);
//        m[3][0] = pl.getZ(oom);
//        m[3][1] = v.getZ(oom);
//        m[3][2] = r.getZ(oom);
//        m[3][3] = lv.getDZ(oom);
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
            return getIntersect0(l, oom, rm);
        }
    }
    
    /**
     * Compute and return the intersection with {@code l} which is assumed 
     * to be not parallel to the plane.
     *
     * @param l The line segment to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_Point getIntersect0(V3D_LineSegment l, 
            int oom, RoundingMode rm) {
        V3D_Point pt = getIntersect0(l.l, oom, rm);
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
        //V3D_Vector v = pl.getN(oomN5).getCrossProduct(getN(oomN5), oomN5);
        //V3D_Vector v = getN(oom, rm).getCrossProduct(pl.getN(oom, rm), oom, rm);
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
//            if (pl.equalsIgnoreOrientation(this, oom, rm)) {
//                // The planes are the same.
//                return this;
//            }
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
//        BigRational[][] m = new BigRational[5][6];
//        BigRational P0 = BigRational.ZERO;
//        BigRational P2 = BigRational.TWO;
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
//        BigRational b4 = pl.pv.getDX(oom, rm).multiply(n.getDX(oom, rm))
//                .add(pl.pv.getDY(oom, rm).multiply(n.getDY(oom, rm)))
//                .add(pl.pv.getDZ(oom, rm).multiply(n.getDZ(oom, rm)));
//        //System.out.println(b4);
//        // b5 = p2(1).*n2(1) + p2(2).*n2(2) + p2(3).*n2(3);
//        BigRational b5 = pl.pv.getDX(oom, rm).multiply(pl.n.getDX(oom, rm))
//                .add(pl.pv.getDY(oom, rm).multiply(pl.n.getDY(oom, rm)))
//                .add(pl.pv.getDZ(oom, rm).multiply(pl.n.getDZ(oom, rm)));
//        //System.out.println(b5);
//        // b = [2*p0(1) ; 2*p0(2) ; 2*p0(3); b4 ; b5];
//        m[0][5] = pv.getDX(oom, rm).multiply(2);
//        m[1][5] = pv.getDY(oom, rm).multiply(2);
//        m[2][5] = pv.getDZ(oom, rm).multiply(2);
//        m[3][5] = b4;
//        m[4][5] = b5;
//        // x = M\b;
//        Math_Matrix_BR mm = new Math_Matrix_BR(m);
//        Math_Matrix_BR ref = mm.getReducedRowEchelonForm();
//        //System.out.println(ref);
//        // pl = x(1:3);
//        //???
//        // n = cross(n1, n2);
//        BigRational[][] refrows = ref.getRows();
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
//            //pi = (V3D_Point) pl.getIntersect(new V3D_Line(tq, getR(oom), oom), oom);
////            pi = (V3D_Point) pl.getIntersection(
////                    new V3D_Line(e, tq.getVector(oomN5), getR().getVector(oomN5)), oomN5);
////            pi = (V3D_Point) pl.getIntersection(
////                    new V3D_Line(tq.getVector(oomN5), getR().getVector(oomN5)), oomN5);
//            pi = (V3D_Point) pl.getIntersect(
//                    new V3D_Line(getP(), getR()), oomN5);
//        } else {
//            //pi = (V3D_Point) pl.getIntersect(new V3D_Line(getP(oom), tq, oom), oom);
////            pi = (V3D_Point) pl.getIntersection(
////                    new V3D_Line(e, getP().getVector(oomN5), getR().getVector(oomN5)), oomN5);
////            pi = (V3D_Point) getIntersection(
////                    new V3D_Line(e, pl.getP().getVector(oomN5), pl.getQ().getVector(oomN5)), oomN5);
//            pi = (V3D_Point) pl.getIntersect(
//                    new V3D_Line(getP(), getQ()), oomN5);
//        }
//
//        //if (pl.getPAsVector().isScalarMultiple(v, oom, rm)) {
//        if (pl.getPAsVector().getDotProduct(n, oom, rm) == BigRational.ZERO) {
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
//    public V3D_Point getIntersect(V3D_Plane pl1, V3D_Plane pl2, int oom,
//            RoundingMode rm) {
//        BigRational[][] m = new BigRational[3][3];
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
//        if (v.isZero()) {
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
//        BigRational P0 = BigRational.ZERO;
//        if (v.dx.isZero()) {
//            if (v.dy.isZero()) {
//                BigRational z = pl.n.getDX(oom).multiply(pl.pl.getX(oom)
//                        .subtract(pl.v.getX(oom))).add(n.getDY(oom).multiply(pl.pl.getY(oom)
//                        .subtract(pl.v.getX(oom)))).divide(v.getDZ(oom)).add(pl.pl.getZ(oom));
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
//                    BigRational y = n.getDX(oom).multiply(pl.getX(oom).subtract(v.getX(oom))).add(
//                            n.getDZ(oom).multiply(pl.getZ(oom).subtract(v.getX(oom))))
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
//                    BigRational numerator = pl.getZ(oom).subtract(pl.getY(oom))
//                            .subtract(n.getDZ(oom).multiply(pl.getZ(oom)))
//                            .subtract(pl.getY(oom).multiply(n.getDY(oom)));
//                    BigRational denominator = n.getDY(oom).subtract(n.getDZ(oom));
//                    if (denominator.compareTo(P0) == 0) {
//                        // Another case to deal with
//                        return null;
//                    } else {
//                        BigRational z = numerator.divide(denominator);
//                        // Substitute into 1
//                        // y = (pl.getY(oom) - c(z−pl.getZ(oom))) / b   --- 1
//                        if (n.dy.isZero()) {
//                            // Another case to deal with
//                            return null;
//                        } else {
//                            BigRational y = pl.getY(oom).subtract(n.getDZ(oom).multiply(z.subtract(pl.getZ(oom))))
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
//                    BigRational x = pl.n.getDY(oom).multiply(pl.pl.getY(oom).subtract(pl.v.getY(oom))).add(
//                            n.getDZ(oom).multiply(pl.pl.getZ(oom).subtract(pl.v.getY(oom))))
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
//                    BigRational z = pl.n.getDX(oom).multiply(pl.pl.getX(oom).subtract(pl.v.getX(oom))).add(
//                            n.getDY(oom).multiply(pl.pl.getY(oom).subtract(pl.v.getX(oom))))
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
//                    BigRational y = n.getDX(oom).multiply(pl.getX(oom).subtract(v.getX(oom))).add(
//                            n.getDZ(oom).multiply(pl.getZ(oom).subtract(v.getX(oom))))
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
//                    BigRational x = n.getDY(oom).multiply(pl.pl.getY(oom).subtract(pl.v.getY(oom))).add(
//                            n.getDZ(oom).multiply(pl.pl.getZ(oom).subtract(pl.v.getY(oom))))
//                            .divide(v.getDX(oom)).add(pl.pl.getX(oom));
//                    BigRational y = n.getDX(oom).multiply(pl.getX(oom).subtract(v.getX(oom))).add(
//                            n.getDZ(oom).multiply(pl.getZ(oom).subtract(v.getX(oom))))
//                            .divide(v.getDY(oom)).add(pl.getY(oom));
//                    BigRational z = n.getDX(oom).multiply(pl.pl.getX(oom).subtract(pl.v.getX(oom))).add(
//                            n.getDY(oom).multiply(pl.pl.getY(oom).subtract(pl.v.getX(oom))))
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
////                    // Let: qv = db-ae; r = db/a-e; s=adb-aae
////                    BigDecimal qv = db.subtract(a.multiply(e));
////                    BigDecimal r = db_div_a.subtract(e);
////                    BigDecimal s = db.multiply(a).subtract(a.multiply(ea));
////                    BigDecimal den = Math_BigDecimal.divideRoundIfNecessary(ef, r, scale, rm)
////                            .subtract(Math_BigDecimal.divideRoundIfNecessary(e.multiply(dc), qv, scale, rm))
////                            .add(Math_BigDecimal.divideRoundIfNecessary(db.multiply(f), qv, scale, rm))
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
////                            .subtract(Math_BigDecimal.divideRoundIfNecessary(e.multiply(dbh_sub_dci), qv, scale, rm))
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
                if (this.intersects(pl.getP(), oom, rm)) {
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

//    /**
//     * Get the distance between this and {@code pl}. Nykamp DQ, “Distance from
//     * point to plane.” From Math Insight.
//     * http://mathinsight.org/distance_point_plane
//     *
//     * @param pl A plane.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode.
//     * @return The distance from {@code pv} to this.
//     */
//    public BigRational getDistanceSquared(V3D_Point pt, int oom,
//            RoundingMode rm) {
//        //V3D_Vector pq = new V3D_Vector(this, pl.pv, oom);
//        //V3D_Vector pq = pl.pv.subtract(this.getVector(oom), oom);
//        V3D_Vector pq = getP().getVector(oom, rm).subtract(
//                pt.getVector(oom, rm), oom, rm);
//        //if (pq.isScalarMultiple(pl.getN(oom, rm), oom, rm)) {
//        if (pq.isScalarMultiple(n, oom, rm)) {
//            return pq.getMagnitudeSquared();
//        } else {
//            BigRational[] coeffs = getEquationCoefficients(oom, rm);
//            BigRational num = (coeffs[0].multiply(pt.getX(oom, rm))
//                    .add(coeffs[1].multiply(pt.getY(oom, rm)))
//                    .add(coeffs[2].multiply(pt.getZ(oom, rm)))
//                    .add(coeffs[3])).abs();
//            BigRational den = coeffs[0].pow(2).add(coeffs[1].pow(2))
//                    .add(coeffs[2].pow(2));
//            return num.divide(den);
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
    public BigRational getDistanceSquared(V3D_Point pt, boolean noInt,
            int oom, RoundingMode rm) {
        int oomn4 = oom - 4;
        V3D_Vector v = new V3D_Vector(pt, getP(), oomn4, rm);
        //V3D_Vector u = getN(oom, rm).getUnitVector(oom, rm);
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
        }
//        V3D_Point pt2 = new V3D_Point(pt);
//        pt2.setOffset(offset, oom, rm);
//        V3D_Vector nn = getN(oom, rm);
//        V3D_Line l = new V3D_Line(pt2.rel, nn, e);
        //V3D_Line l = new V3D_Line(pt2.rel, n, e);
        V3D_Line l = new V3D_Line(pt, n);
        return (V3D_Point) getIntersect(l, oom, rm);
    }

    /**
     * Check a and b are on the same side of this.If either are on the boundary
 then return {@code true}.
     *
     * @param a A point.
     * @param b Another point. The triangle to check the points to see if they
 are all on the same side of a line that getIntersect the edge of another
 triangle.
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
 then return {@code true}.
     *
     * @param a A point.
     * @param b Another point. The triangle to check the points to see if they
 are all on the same side of a line that getIntersect the edge of another
 triangle.
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
                for (int i = 1; i < pts.length; i ++) {
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
                for (int i = 1; i < pts.length; i ++) {
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
