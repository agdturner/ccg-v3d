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
package uk.ac.leeds.ccg.v3d.geometry.d;

import uk.ac.leeds.ccg.math.arithmetic.Math_Double;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_Double;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

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
 * <li>{@code n.getDX(oom)(x(t)−qv.getX(oom))+n.getDY(oom)(y(t)−qv.getY(oom))+n.getDZ(oom)(z(t)−qv.getZ(oom)) = 0}</li>
 * <li>{@code n.getDX(oom)(x(t)−r.getX(oom))+n.getDY(oom)(y(t)−r.getY(oom))+n.getDZ(oom)(z(t)−r.getZ(oom)) = 0}</li>
 * </ol>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Plane_d extends V3D_Geometry_d {

    private static final long serialVersionUID = 1L;

    /**
     * The x = 0 plane.
     */
    public static final V3D_Plane_d X0 = new V3D_Plane_d(
            V3D_Point_d.ORIGIN, V3D_Vector_d.I);

    /**
     * The y = 0 plane.
     */
    public static final V3D_Plane_d Y0 = new V3D_Plane_d(
            V3D_Point_d.ORIGIN, V3D_Vector_d.J);

    /**
     * The z = 0 plane.
     */
    public static final V3D_Plane_d Z0 = new V3D_Plane_d(
            V3D_Point_d.ORIGIN, V3D_Vector_d.K);

    /**
     * The vector that defines {@link #p} on the plane.
     */
    protected V3D_Vector_d pv;

    /**
     * The point that defines the plane.
     */
    protected V3D_Point_d p;

    /**
     * The normal vector that defines the plane.
     */
    protected V3D_Vector_d n;

    /**
     * For storing the equation of the plane.
     */
    protected transient Equation equation;

    /**
     * Create a new instance.
     *
     * @param pl The plane used to create this.
     */
    public V3D_Plane_d(V3D_Plane_d pl) {
        super(pl.env, new V3D_Vector_d(pl.offset));
        this.pv = new V3D_Vector_d(pl.pv);
        this.n = new V3D_Vector_d(pl.n);
    }

    /**
     * Create a new instance.
     *
     * @param p Used to initialise {@link #offset} and {@link #p}.
     * @param n What {@link #n} is set to.
     */
    public V3D_Plane_d(V3D_Point_d p, V3D_Vector_d n) {
        super(p.env, new V3D_Vector_d(p.offset));
        this.pv = new V3D_Vector_d(p.rel);
        this.p = new V3D_Point_d(p);
        this.n = new V3D_Vector_d(n);
    }

    /**
     * Create a new instance.
     *
     * @param l A line segment in the plane.
     * @param inplane A vector in the plane that is not a scalar multiple of the
     * vector of the line of l.
     */
    public V3D_Plane_d(V3D_LineSegment_d l, V3D_Vector_d inplane) {
        super(l.env, new V3D_Vector_d(l.offset));
        pv = new V3D_Vector_d(l.getP().rel);
        p = new V3D_Point_d(l.getP());
        n = l.l.v.getCrossProduct(inplane);
    }

    /**
     * Create a new instance.{@code pv}, {@code qv} and {@code rv} must not
     * define collinear.
     *
     * @param p Used to initialise {@link #env}, {@link #offset}, {@link #pv},
     * and {@link #p}.
     * @param q A point coplanar to {@code p} and {@code r}, not collinear or 
     * equal to either {@code p} or {@code r}.
     * @param r A point coplanar to {@code p} and {@code q}, not collinear or 
     * equal to either {@code p} or {@code q}.
     */
    public V3D_Plane_d(V3D_Point_d p, V3D_Point_d q, V3D_Point_d r) {
        super(p.env, new V3D_Vector_d(p.offset));
        V3D_Vector_d qv = q.getVector();
        V3D_Vector_d pq = qv.subtract(p.getVector());
        V3D_Vector_d qr = r.getVector().subtract(qv);
        this.pv = new V3D_Vector_d(p.rel);
        this.n = pq.getCrossProduct(qr);
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
     */
    public V3D_Plane_d(V3D_Environment_d env, V3D_Vector_d pv, V3D_Vector_d qv,
            V3D_Vector_d rv) {
        this(env, V3D_Vector_d.ZERO, pv, qv, rv);
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param pv Used to initialise {@link #pv}.
     * @param qv A vector in the plane not collinear with {@code pv} or
     * {@code rv}.
     * @param rv A vector in the plane not collinear with {@code pv} or 
     * {@code qv}.
     */
    public V3D_Plane_d(V3D_Environment_d env, V3D_Vector_d offset, 
            V3D_Vector_d pv, V3D_Vector_d qv, V3D_Vector_d rv) {
        this(env, qv.subtract(pv).getCrossProduct(rv.subtract(qv)), offset, pv, 
                qv, rv);
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param ptv A point vector giving the direction of the normal vector.
     * @param offset What {@link #offset} is set to.
     * @param pv Used to initialise {@link #pv}.
     * @param qv A vector in the plane not collinear with {@code pv} or
     * {@code rv}.
     * @param rv A vector in the plane not collinear with {@code pv} or 
     * {@code qv}.
     */
    public V3D_Plane_d(V3D_Environment_d env, V3D_Vector_d ptv, V3D_Vector_d offset,
            V3D_Vector_d pv, V3D_Vector_d qv, V3D_Vector_d rv) {
        super(env, new V3D_Vector_d(offset));
        V3D_Vector_d pq = qv.subtract(pv);
        if (pq.equals(V3D_Vector_d.ZERO)) {
            throw new RuntimeException("Cannot define plane as p equals q.");
        }
        V3D_Vector_d qr = rv.subtract(qv);
        if (qr.equals(V3D_Vector_d.ZERO)) {
            throw new RuntimeException("Cannot define plane as q equals r.");
        }
        this.pv = new V3D_Vector_d(pv);
        n = pq.getCrossProduct(qr);
        if (n.isZero()) {
            n = pq.reverse().getCrossProduct(qr);
        }
        V3D_Vector_d v;
        if (ptv.isZero()) {
            v = pv.add(qv).add(rv).reverse();
        } else {
            v = new V3D_Vector_d(ptv);
        }
        double direction = n.getDotProduct(v) / n.getDotProduct(n);
        if (direction < 0d) {
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
     */
    public V3D_Plane_d(V3D_Point_d pt, V3D_Vector_d offset,
            V3D_Vector_d pv, V3D_Vector_d qv, V3D_Vector_d rv) {
        this(pt.env, pt.getVector(), offset, pv, qv, rv);
    }

    /**
     * Creates a new plane which is essentially the same as pl, but with the
     * offset specified.
     *
     * @param env What {@link #env} is set to.
     * @param pl The plane to use as a template.
     * @param offset What {@link #offset} is set to.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V3D_Plane_d(V3D_Environment_d env, V3D_Vector_d offset, V3D_Plane_d pl,
            double epsilon) {
        super(env, offset);
        n = pl.getN();
        if (offset.equals(epsilon, pl.offset)) {
            pv = new V3D_Vector_d(pl.pv);
        } else {
            pv = pl.pv.add(pl.offset).subtract(offset);
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
        return this.getClass().getSimpleName() + "(\n"
                + toStringFields(pad + " ") + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFields(String pad) {
        return super.toStringFields(pad) + ",\n"
                + pad + "pv=" + pv.toStringSimple("") + ",\n"
                + pad + "n=" + n.toStringSimple("");
    }

    /**
     * @return A copy of {@link #n}.
     */
    public final V3D_Vector_d getN() {
        return new V3D_Vector_d(n);
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public final V3D_Point_d getP() {
        return new V3D_Point_d(env, offset, pv);
    }

    /**
     * Find a perpendicular vector.
     *
     * @return A perpendicular vector.
     */
    public final V3D_Vector_d getPV() {
//        /**
//         * Magnitude method adapted from:
//         * https://math.stackexchange.com/questions/137362/how-to-find-perpendicular-vector-to-another-vector
//         */
//        V3D_Vector_d v1 = new V3D_Vector_d(0d, n.dz, -n.dy);
//        V3D_Vector_d v2 = new V3D_Vector_d(-n.dz, 0d, n.dx);
//        V3D_Vector_d v3 = new V3D_Vector_d(-n.dy, n.dx, 0d);
//        double mv1 = v1.getMagnitudeSquared();
//        double mv2 = v2.getMagnitudeSquared();
//        double mv3 = v3.getMagnitudeSquared();
//        if (mv1 > mv2) {
//            if (mv1 > mv3) {
//                return v1;
//            }
//        } else {
//            if (mv2 > mv3) {
//               return v2;
//            }
//        }
//        return v3;
        /**
         * Alternative method adapted from:
         * https://math.stackexchange.com/questions/137362/how-to-find-perpendicular-vector-to-another-vector
         */
        V3D_Vector_d v = new V3D_Vector_d(n.dz, n.dz, -n.dx - n.dy);
        if (v.isZero()) {
            return new V3D_Vector_d(-n.dy - n.dz, n.dx, n.dx);
        }
        return v;
    }

    /**
     * Get another point on the plane.
     *
     * @return A point on the plane.
     */
    public final V3D_Point_d getQ() {
        return getQ(getPV());
    }

    /**
     * Get another point on the plane.
     *
     * @param pv What is from {@link #getPV()}.
     * @return A point on the plane.
     */
    public final V3D_Point_d getQ(V3D_Vector_d pv) {
        return new V3D_Point_d(env, offset, this.pv.add(pv));
    }

    /**
     * Get another point on the plane.
     *
     * @return A point on the plane.
     */
    public final V3D_Point_d getR() {
        return getR(getPV());
    }

    /**
     * Get another point on the plane.
     *
     * @param pv What is from {@link #getPV()}.
     * @return A point on the plane.
     */
    public final V3D_Point_d getR(V3D_Vector_d pv) {
        V3D_Vector_d pvx = pv.getCrossProduct(n);
        return new V3D_Point_d(env, offset, this.pv.add(pvx));
    }

    /**
     * For getting the equation of the plane.
     *
     * @return The equation of the plane as a String.
     */
    public String getEquationString() {
        if (equation == null) {
            equation = new Equation();
        }
        String r = "";
        boolean nx = equation.coeffs[0] < 0d;
        boolean ny = equation.coeffs[1] < 0d;
        boolean nz = equation.coeffs[2] < 0d;
        boolean nc = equation.coeffs[3] < 0d;
        if (!nx) {
            if (equation.coeffs[0] != 0d) {
                if (equation.coeffs[0] != 1d) {
                    r += equation.coeffs[0] + "(x)";
                } else {
                    r += "x";
                }
            }
        }
        if (!ny) {
            if (!nx) {
                if (equation.coeffs[0] != 0d) {
                    r += "+";
                }
            }
            if (equation.coeffs[1] != 0d) {
                if (equation.coeffs[1] != 1d) {
                    r += equation.coeffs[1] + "(y)";
                } else {
                    r += "y";
                }
            }
        }
        if (!nz) {
            if (!nx || !ny) {
                if (equation.coeffs[0] != 0d && equation.coeffs[1] != 0d) {
                    r += "+";
                }
            }
            if (equation.coeffs[2] != 0d) {
                if (equation.coeffs[2] != 1d) {
                    r += equation.coeffs[2] + "(z)";
                } else {
                    r += "z";
                }
            }
        }
        if (!nc) {
            if (!nx || !ny || !nz) {
                if (equation.coeffs[0] != 0d && equation.coeffs[1] != 0d
                        && equation.coeffs[2] != 0d) {
                    r += "+";
                }
            }
            if (equation.coeffs[3] != 0d) {
                r += equation.coeffs[3];
            }
        }
        r += "=";
        if (nx) {
            if (equation.coeffs[0] != -1d) {
                r += -equation.coeffs[0] + "(x)";
            } else {
                r += "x";
            }
        }
        if (ny) {
            if (nx) {
                if (equation.coeffs[0] != -0d) {
                    r += "+";
                }
            }
            if (equation.coeffs[1] != -1d) {
                r += -equation.coeffs[1] + "(y)";
            } else {
                r += "y";
            }
        }
        if (nz) {
            if (nx || ny) {
                if (equation.coeffs[0] != -0d && equation.coeffs[1] != -0d) {
                    r += "+";
                }
            }
            if (equation.coeffs[2] != -1d) {
                r += -equation.coeffs[2] + "(z)";
            } else {
                r += "z";
            }
        }
        if (nc) {
            if (nx || ny || nz) {
                if (equation.coeffs[0] != -0d && equation.coeffs[1] != -0d
                        && equation.coeffs[2] != -0d) {
                    r += "+";
                }
            }
            if (equation.coeffs[3] != -0d) {
                r += -equation.coeffs[3];
            }
        }
        return r;
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
        public double[] coeffs;

        /**
         * Create a new instance.
         */
        public Equation() {
            coeffs = new double[4];
            V3D_Point_d tp = getP();
            double k = n.dx * tp.getX() + n.dy * tp.getY() + n.dz * tp.getZ();
            coeffs[0] = n.dx;
            coeffs[1] = n.dy;
            coeffs[2] = n.dz;
            coeffs[3] = -k;
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
     * @return The equation of the plane as an Equation.
     */
    public Equation getEquation() {
        if (equation == null) {
            equation = new Equation();
        }
        return equation;
    }

    /**
     * Plug the point into the plane equation.
     *
     * @param pt The point to test for intersection with.
     * @return {@code true} iff the geometry is intersected by {@code pv}.
     */
    public boolean intersects(V3D_Point_d pt) {
        equation = getEquation();
        return ((equation.coeffs[0] * pt.getX() + equation.coeffs[1] * pt.getY()
                + equation.coeffs[2] * pt.getZ() + equation.coeffs[3]) == 0d);
    }

    /**
     * Identify if this is intersected by point {@code pt} using the matrices
     * approach described here:
     * https://math.stackexchange.com/questions/684141/check-if-a-point-is-on-a-plane-minimize-the-use-of-multiplications-and-divisio/684580#684580
     *
     * @param pt The point to test for intersection with.
     * @return {@code true} iff the geometry is intersected by {@code pv}.
     */
    public boolean intersectsAlternative(V3D_Point_d pt) {
        double[][] m = new double[4][4];
        V3D_Point_d tp = getP();
        if (tp.isOrigin()) {
            V3D_Point_d tpt = new V3D_Point_d(pt);
            tpt.translate(V3D_Vector_d.IJK);
            V3D_Point_d ttp = new V3D_Point_d(getP());
            ttp.translate(V3D_Vector_d.IJK);
            V3D_Vector_d perpv = getPV();
            V3D_Point_d ttq = getQ(perpv);
            ttq.translate(V3D_Vector_d.IJK);
            V3D_Point_d ttr = getR(perpv);
            ttr.translate(V3D_Vector_d.IJK);
            m[0][0] = ttp.getX();
            m[1][0] = ttp.getY();
            m[2][0] = ttp.getZ();
            m[3][0] = 1.0d;
            m[0][1] = ttq.getX();
            m[1][1] = ttq.getY();
            m[2][1] = ttq.getZ();
            m[3][1] = 1.0d;
            m[0][2] = ttr.getX();
            m[1][2] = ttr.getY();
            m[2][2] = ttr.getZ();
            m[3][2] = 1.0d;
            m[0][3] = tpt.getX();
            m[1][3] = tpt.getY();
            m[2][3] = tpt.getZ();
            m[3][3] = 1.0d;
            return new Math_Matrix_Double(m).getDeterminant() == 0d;
        } else {
            //V3D_Vector_d pv = getPV();
            V3D_Point_d tq = getQ(pv);
            V3D_Point_d tr = getR(pv);
            m[0][0] = tp.getX();
            m[1][0] = tp.getY();
            m[2][0] = tp.getZ();
            m[3][0] = 1.0d;
            m[0][1] = tq.getX();
            m[1][1] = tq.getY();
            m[2][1] = tq.getZ();
            m[3][1] = 1.0d;
            m[0][2] = tr.getX();
            m[1][2] = tr.getY();
            m[2][2] = tr.getZ();
            m[3][2] = 1.0d;
            m[0][3] = pt.getX();
            m[1][3] = pt.getY();
            m[2][3] = pt.getZ();
            m[3][3] = 1.0d;
            return new Math_Matrix_Double(m).getDeterminant() == 0d;
        }
    }

    /**
     * Plug the point into the plane equation.
     *
     * @param epsilon The tolerance within which vector components are regarded
     * as equal.
     * @param pt The point to test for intersection with.
     * @return {@code true} iff the geometry is intersected by {@code pv}.
     */
    public boolean intersects(double epsilon, V3D_Point_d pt) {
        equation = getEquation();
        double c = (equation.coeffs[0] * pt.getX() + equation.coeffs[1] * pt.getY()
                + equation.coeffs[2] * pt.getZ() + equation.coeffs[3]);
        return Math_Double.equals(c, 0d, epsilon);
    }

    /**
     * @param epsilon The tolerance within which vector components are regarded
     * as equal.
     * @param ps The points to test if they are coplanar with pl.
     * @return {@code true} iff all points are coplanar with pl.
     */
    public static boolean isCoplanar(double epsilon, V3D_Point_d... ps) {
        V3D_Plane_d pl = getPlane(epsilon, ps);
        if (pl == null) {
            return false;
        } else {
            return isCoplanar(epsilon, pl, ps);
        }
    }

    /**
     * @param epsilon The tolerance within which vector components are regarded
     * as equal.
     * @param pl The plane to test points are coplanar with.
     * @param ps The points to test if they are coplanar with this.
     * @return {@code false} if points are coincident or collinear. {@code true}
     * iff all points are coplanar.
     */
    public static boolean isCoplanar(double epsilon, V3D_Plane_d pl,
            V3D_Point_d... ps) {
        for (var p : ps) {
            if (!pl.intersects(epsilon, p)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param points The points from which a plane is to be derived.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return A plane that may or may not contain all the points or
     * {@code null} if there is no such plane (if the points are coincident or
     * collinear).
     */
    public static V3D_Plane_d getPlane(double epsilon,
            V3D_Point_d... points) {
        V3D_Line_d l = V3D_Line_d.getLine(points);
        if (l == null) {
            return null;
        }
        double max = 0d;
        V3D_Point_d pt = null;
        for (var p : points) {
            double d = l.getDistanceSquared(p, epsilon);
            if (d > max) {
                pt = p;
                max = d;
            }
        }
        if (max > 0d) {
            return new V3D_Plane_d(l.getP(), l.getQ(), pt);
        } else {
            return null;
        }
    }

    /**
     * @param l The line to test if it is on the plane.
     * @return {@code true} If {@code pt} is on the plane.
     */
    public boolean isOnPlane(V3D_Line_d l) {
        return intersects(l.getP()) && intersects(l.getQ());
    }

    /**
     * @param l The line to test if it is on the plane.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} If {@code pt} is on the plane.
     */
    public boolean isOnPlane(V3D_Line_d l, double epsilon) {
        return intersects(epsilon, l.getP()) && intersects(epsilon, l.getQ());
    }

    /**
     * Method adapted from the python code given here:
     * https://stackoverflow.com/a/18543221/1998054
     *
     * @param l The line to intersect with the plane.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection of the line and the plane. This is either
     * {@code null} a line or a point.
     */
    public V3D_Geometry_d getIntersectionAlternative(V3D_Line_d l, double epsilon) {
        // Special case
        if (isParallel(l)) {
            //if (isParallel(l, epsilon)) {
            if (isOnPlane(l, epsilon)) {
                return l;
            }
            return null;
        }
        double dot = n.getDotProduct(l.v);
        if (dot == 0d) {
            return null;
        } else {
            V3D_Point_d lp = l.getP();
            V3D_Point_d tp = getP();
            boolean atOrigin = lp.isOrigin();
            if (atOrigin) {
                V3D_Point_d tlp = new V3D_Point_d(lp);
                tlp.translate(l.v);
                V3D_Point_d ttp = new V3D_Point_d(tp);
                ttp.translate(l.v);
                V3D_Vector_d w = new V3D_Vector_d(ttp, tlp);
                if (w.isZero()) {
                    return tp;
                }
                double fac = -n.getDotProduct(w) / (dot);
                if (fac == 0d) {
                    return tp;
                }
                V3D_Vector_d u2 = l.v.multiply(fac);
                tlp.translate(u2);
                tlp.translate(l.v.reverse());
                return tlp;
            } else {
                V3D_Vector_d w = new V3D_Vector_d(tp, lp);
                if (w.isZero()) {
                    return tp;
                }
                double fac = -n.getDotProduct(w) / (dot);
                V3D_Vector_d u2 = l.v.multiply(fac);
                V3D_Point_d p00 = new V3D_Point_d(lp);
                p00.translate(u2);
                return p00;
            }
        }
    }

    /**
     * Get the intersection between the geometry and the line {@code l}.
     * https://stackoverflow.com/questions/5666222/3d-line-plane-intersection
     *
     * @param l The line to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry_d.
     */
    public V3D_Geometry_d getIntersect(V3D_Line_d l, double epsilon) {
        if (isParallel(l, epsilon)) {
            if (isOnPlane(l, epsilon)) {
                return l;
            } else {
                return null;
            }
        } else {
            return getIntersectNonParallel(l, epsilon);
        }
    }

    /**
     * Get the intersection with {@code l} which is assumed not to be parallel
     * with this.
     *
     * @param l The line to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_Point_d getIntersectNonParallel(V3D_Line_d l, double epsilon) {
        // Are either of the points of l on the plane.
        //V3D_Point lp = l.getP(oom);
        V3D_Point_d lp = l.getP();
        if (intersects(epsilon, lp)) {
            return lp;
        }
        V3D_Point_d lq = l.getQ();
        if (intersects(epsilon, lq)) {
            return lq;
        }
        //V3D_Vector lv = l.getV(oomN2, rm);
        V3D_Vector_d lv = l.v;
        double[][] m = new double[4][4];
        m[0][0] = 1.0d;
        m[0][1] = 1.0d;
        m[0][2] = 1.0d;
        m[0][3] = 1.0d;
        V3D_Point_d tp = getP();
        V3D_Vector_d perpv = getPV();
        V3D_Point_d tq = getQ(perpv);
        V3D_Point_d tr = getR(perpv);
        m[1][0] = tp.getX();
        m[1][1] = tq.getX();
        m[1][2] = tr.getX();
        m[1][3] = lp.getX();
        m[2][0] = tp.getY();
        m[2][1] = tq.getY();
        m[2][2] = tr.getY();
        m[2][3] = lp.getY();
        m[3][0] = tp.getZ();
        m[3][1] = tq.getZ();
        m[3][2] = tr.getZ();
        m[3][3] = lp.getZ();
        Math_Matrix_Double numm = new Math_Matrix_Double(m);
        m[0][0] = 1.0d;
        m[0][1] = 1.0d;
        m[0][2] = 1.0d;
        m[0][3] = 0d;
        m[1][0] = tp.getX();
        m[1][1] = tq.getX();
        m[1][2] = tr.getX();
        m[1][3] = lv.dx;
        m[2][0] = tp.getY();
        m[2][1] = tq.getY();
        m[2][2] = tr.getY();
        m[2][3] = lv.dy;
        m[3][0] = tp.getZ();
        m[3][1] = tq.getZ();
        m[3][2] = tr.getZ();
        m[3][3] = lv.dz;
        Math_Matrix_Double denm = new Math_Matrix_Double(m);
        double t;
        double denmdet = denm.getDeterminant();
        double nummdet = numm.getDeterminant();
        if (denmdet == 0d) {
            if (Math_Double.equals(nummdet, 0d, epsilon)) {
                t = 1;
            } else {
                return null;
            }
        } else {
            t = -nummdet / denmdet;
        }
        return new V3D_Point_d(env,
                lp.getX() + (lv.dx * (t)),
                lp.getY() + (lv.dy * (t)),
                lp.getZ() + (lv.dz * (t)));
    }

    /**
     * Get the intersection between the geometry and the line {@code l}.
     *
     * @param l The line to intersect with.
     * @return The V3D_Geometry.
     */
    public V3D_Geometry_d getIntersect(V3D_Line_d l) {
        if (isParallel(l)) {
            if (isOnPlane(l)) {
                return l;
            } else {
                return null;
            }
        }
        // Are either of the points of l on the plane.
        V3D_Point_d lp = l.getP();
        if (intersects(lp)) {
            return lp;
        }
        V3D_Point_d lq = l.getQ();
        if (intersects(lq)) {
            return lq;
        }
        V3D_Vector_d lv = l.v;
        double[][] m = new double[4][4];
        m[0][0] = 1.0d;
        m[0][1] = 1.0d;
        m[0][2] = 1.0d;
        m[0][3] = 1.0d;
        V3D_Point_d tp = getP();
        V3D_Vector_d perpv = getPV();
        V3D_Point_d tq = getQ(perpv);
        V3D_Point_d tr = getR(perpv);
        m[1][0] = tp.getX();
        m[1][1] = tq.getX();
        m[1][2] = tr.getX();
        m[1][3] = lp.getX();
        m[2][0] = tp.getY();
        m[2][1] = tq.getY();
        m[2][2] = tr.getY();
        m[2][3] = lp.getY();
        m[3][0] = tp.getZ();
        m[3][1] = tq.getZ();
        m[3][2] = tr.getZ();
        m[3][3] = lp.getZ();
        Math_Matrix_Double numm = new Math_Matrix_Double(m);
        m[0][0] = 1.0d;
        m[0][1] = 1.0d;
        m[0][2] = 1.0d;
        m[0][3] = 0d;
        m[1][0] = tp.getX();
        m[1][1] = tq.getX();
        m[1][2] = tr.getX();
        m[1][3] = lv.dx;
        m[2][0] = tp.getY();
        m[2][1] = tq.getY();
        m[2][2] = tr.getY();
        m[2][3] = lv.dy;
        m[3][0] = tp.getZ();
        m[3][1] = tq.getZ();
        m[3][2] = tr.getZ();
        m[3][3] = lv.dz;
        Math_Matrix_Double denm = new Math_Matrix_Double(m);
        double t;
        double denmdet = denm.getDeterminant();
        double nummdet = numm.getDeterminant();
        if (denmdet == 0d) {
            if (nummdet == 0d) {
                t = 1;
            } else {
                return null;
            }
        } else {
            t = -nummdet / denmdet;
        }
        return new V3D_Point_d(env,
                lp.getX() + (lv.dx * (t)),
                lp.getY() + (lv.dy * (t)),
                lp.getZ() + (lv.dz * (t)));
    }
    
    /**
     * Compute and return the intersection with {@code r}. {@code null} is 
     * returned if there is no intersection.
     * See also V3D_Ray_d#getIntersect(V3D_Plane_d, double).
     *
     * @param r The ray to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_Geometry_d getIntersect(V3D_Ray_d r, double epsilon) {
        // Check if infinite line intersects.
        V3D_Geometry_d g = getIntersect(r.l, epsilon);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_Point_d gp) {
                if (r.getPl().isOnSameSide(gp, r.l.getQ(), epsilon)) {
//                    // Get closest point on the plane as gp is on the 
//                    // ray but may not be on the plane?
//                    System.out.println(gp);
//                    System.out.println(getPointOfProjectedIntersect(gp, epsilon).toString());
//                    System.out.println(getPointOfProjectedIntersect(gp).toString());
//                    System.out.println(r.l.getPointOfIntersect(gp, epsilon));
//                    System.out.println(r.l.getPointOfIntersect(gp, true, epsilon));
//                    return getPointOfProjectedIntersect(gp, epsilon);
                    return gp;
                } else {
                    return null;
                }
            } else {
                return r;
            }
        }
    }

    /**
     * Compute and return the intersection with {@code l}.
     *
     * @param l The line segment to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_LineSegment_d l, double epsilon) {
        if (isParallel(l.l, epsilon)) {
            if (isOnPlane(l.l, epsilon)) {
                return l;
            } else {
                return null;
            }
        } else {
            return getIntersectNonParallel(l, epsilon);
        }
    }

    /**
     * Compute and return the intersection with {@code l} which is assumed to be
     * not parallel to the plane.
     *
     * @param l The line segment to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_Point_d getIntersectNonParallel(V3D_LineSegment_d l, double epsilon) {
        V3D_Point_d pt = getIntersectNonParallel(l.l, epsilon);
        if (l.getPPL().isOnSameSide(pt, l.getQ(), epsilon)
                && l.getQPL().isOnSameSide(pt, l.getP(), epsilon)) {
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
     * @return The intersection between {@code this} and {@code pl}
     */
    public V3D_Geometry_d getIntersect(V3D_Plane_d pl) {
        /**
         * Calculate the cross product of the normal vectors to get the
         * direction of the line.
         */
        V3D_Vector_d v = n.getCrossProduct(pl.n);
        /**
         * Check special cases.
         */
        if (v.isZero()) {
            // The planes are parallel.
            if (pl.intersects(getP())) {
                // The planes are the same.
                return this;
            }
            return null;
        }
        V3D_Point_d pi = pl.getPointOfProjectedIntersect(getP());
        return new V3D_Line_d(pi, v);
    }

    /**
     * https://www.microsoft.com/en-us/research/publication/intersection-of-two-planes/
     * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
     * https://math.stackexchange.com/questions/291289/find-the-point-on-the-line-of-intersection-of-the-two-planes-using-lagranges-me
     * http://tbirdal.blogspot.com/2016/10/a-better-approach-to-plane-intersection.html
     * https://mathworld.wolfram.com/Plane-PlaneIntersection.html
     *
     * @param pl The plane to intersect.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code pl}
     */
    public V3D_Geometry_d getIntersect(V3D_Plane_d pl,
            double epsilon) {
        /**
         * Calculate the cross product of the normal vectors to get the
         * direction of the line.
         */
        V3D_Vector_d v = n.getCrossProduct(pl.n);
        /**
         * Check special cases.
         */
        //if (v.isZero()) {
        if (v.isZero(epsilon)) {
            // The planes are parallel.
            if (pl.intersects(epsilon, getP())) {
                // The planes are the same.
                return this;
            }
            return null;
        }
        V3D_Point_d pi = pl.getPointOfProjectedIntersect(getP(), epsilon);
        return new V3D_Line_d(pi, v);
    }

    /**
     * Compute and return the intersection with {@code pl} which is not
     * parallel.
     *
     * @param pl The plane to intersect.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code pl}
     */
    public V3D_Line_d getIntersectNonParallel(V3D_Plane_d pl, double epsilon) {
        /*
         * 1. Calculate a projection of a point onto the plane {@code pl}.
         * 2. Calculate the cross product of the normal vectors to get the
         * direction of the line.
         * Together these define the intersection.
         */
        return new V3D_Line_d(pl.getPointOfProjectedIntersect(getP(), epsilon),
                n.getCrossProduct(pl.n));
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code pl}
     */
    public V3D_Geometry_d getIntersect(V3D_Plane_d pl1,
            V3D_Plane_d pl2, double epsilon) {
        V3D_Geometry_d g = getIntersect(pl1, epsilon);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_Plane_d gp) {
                return gp.getIntersect(pl2, epsilon);
            } else {
                return pl2.getIntersect((V3D_Line_d) g, epsilon);
            }
        }
    }

    /**
     * @param p The plane to test if it is parallel to this.
     * @return {@code true} if {@code this} is parallel to {@code pl}.
     */
    public boolean isParallel(V3D_Plane_d p) {
        return n.isScalarMultiple(p.n);
    }

    /**
     * @param p The plane to test if it is parallel to this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is parallel to {@code pl}.
     */
    public boolean isParallel(V3D_Plane_d p, double epsilon) {
        return n.isScalarMultiple(p.n, epsilon);
    }

    /**
     * @param l The line to test if it is parallel to this.
     * @return {@code true} if {@code this} is parallel to {@code l}.
     */
    public boolean isParallel(V3D_Line_d l) {
        return n.isOrthogonal(l.v);
//        if (n.isOrthogonal(l.v)) {
//            return true;
//        }
//        return n.getDotProduct(l.v) == 0d;
    }

    /**
     * @param l The line to test if it is parallel to this.
     * @param epsilon The tolerance within which vector components are regarded
     * as equal.
     * @return {@code true} if {@code this} is parallel to {@code l}.
     */
    public boolean isParallel(V3D_Line_d l, double epsilon) {
        return n.isOrthogonal(l.v, epsilon);
//        if (n.isOrthogonal(l.v, epsilon)) {
//            return true;
//        }
//        return n.getDotProduct(l.v) == 0d;
    }

    /**
     * Planes are equal if their normal perpendicular vectors point in the same
     * direction, and their normals are scalar multiples, and the point of each
     * plane is on the other.
     *
     * @param pl The plane to check for equality with {@code this}.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} and {@code pl} are the same.
     */
    public boolean equals(V3D_Plane_d pl, double epsilon) {
        if (n.getDotProduct(pl.n) > 0) {
            return equalsIgnoreOrientation(pl, epsilon);
        }
        return false;
    }

    /**
     * Planes are equal if their normals are scalar multiples, and the point of
     * each plane is on the other.
     *
     * @param pl The plane to check for equality with {@code this}.
     * @return {@code true} iff {@code this} and {@code pl} are the same.
     */
    public boolean equalsIgnoreOrientation(V3D_Plane_d pl) {
        if (n.isScalarMultiple(pl.n)) {
            if (pl.intersects(getP())) {
                if (intersects(pl.getP())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Planes are equal if their normals are scalar multiples, and the point of
     * each plane is on the other.
     *
     * @param pl The plane to check for equality with {@code this}.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff {@code this} and {@code pl} are the same.
     */
    public boolean equalsIgnoreOrientation(V3D_Plane_d pl, double epsilon) {
        if (n.isScalarMultiple(pl.n, epsilon)) {
            if (pl.intersects(epsilon, getP())) {
                if (intersects(epsilon, pl.getP())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return The points that define the plane as a matrix.
     */
    public Math_Matrix_Double getAsMatrix() {
        V3D_Point_d tp = getP();
        V3D_Vector_d perpv = getPV();
        V3D_Point_d tq = getQ(perpv);
        V3D_Point_d tr = getR(perpv);
        double[][] m = new double[3][3];
        m[0][0] = tp.getX();
        m[0][1] = tp.getY();
        m[0][2] = tp.getZ();
        m[1][0] = tq.getX();
        m[1][1] = tq.getY();
        m[1][2] = tq.getZ();
        m[2][0] = tr.getX();
        m[2][1] = tr.getY();
        m[2][2] = tr.getZ();
        return new Math_Matrix_Double(m);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pt A point.
     * @return The distance from {@code this} to {@code pl}.
     */
    public double getDistance(V3D_Point_d pt) {
        return Math.sqrt(getDistanceSquared(pt, true));
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pt A point.
     * @param epsilon The tolerance within which vector components are regarded
     * as equal.
     * @return The distance from {@code this} to {@code pl}.
     */
    public double getDistanceSquared(V3D_Point_d pt, double epsilon) {
        if (intersects(epsilon, pt)) {
            return 0d;
        } else {
            return getDistanceSquared(pt, true);
        }
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pt A point.
     * @param noInt To distinguish this from
     * {@link #getDistanceSquared(uk.ac.leeds.ccg.v3d.geometry.d.V3D_Point_d)}
     * @return The distance from {@code this} to {@code pl}.
     */
    public double getDistanceSquared(V3D_Point_d pt, boolean noInt) {
        V3D_Vector_d v = new V3D_Vector_d(pt, getP());
        V3D_Vector_d u = n.getUnitVector();
        return Math.pow(v.getDotProduct(u), 2d);
    }

    /**
     * Get the minimum distance to {@code pv}.
     *
     * @param p A plane.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The minimum distance to {@code pv}.
     */
    public double getDistance(V3D_Plane_d p, double epsilon) {
        return Math.sqrt(getDistanceSquared(p, epsilon));
    }

    /**
     * The planes are either parallel or the distance is zero. If parallel, get
     * any point on one and find the distance squared of the other plane from
     * it.
     *
     * @param pl The other plane used to calculate the distance.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The shortest distance between {@code this} and {@code pl}.
     */
    public double getDistanceSquared(V3D_Plane_d pl, double epsilon) {
        if (isParallel(pl, epsilon)) {
            return pl.getDistanceSquared(getP(), epsilon);
        }
        return 0d;
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V3D_Line_d l) {
        return Math.sqrt(getDistanceSquared(l));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @return The minimum distance to {@code l}.
     */
    public double getDistanceSquared(V3D_Line_d l) {
        return getDistanceSquared(l.getP(), true);
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which vector components are regarded
     * as equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V3D_LineSegment_d l, double epsilon) {
        return Math.sqrt(getDistanceSquared(l, epsilon));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param epsilon The tolerance within which vector components are regarded
     * as equal.
     * @return The minimum distance to {@code l}.
     */
    public double getDistanceSquared(V3D_LineSegment_d l, double epsilon) {
        double lpd = getDistanceSquared(l.getP(), epsilon);
        double lqd = getDistanceSquared(l.getQ(), epsilon);
        return Math.min(lpd, lqd);
    }

    @Override
    public void translate(V3D_Vector_d v) {
        super.translate(v);
        this.equation = null;
        if (p != null) {
            p.translate(v);
        }
    }

    @Override
    public V3D_Plane_d rotate(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V3D_Plane_d(this);
        } else {
            return rotateN(ray, uv, theta, epsilon);
        }
    }

    @Override
    public V3D_Plane_d rotateN(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        return new V3D_Plane_d(getP().rotate(ray, uv, theta, epsilon),
                n.rotate(uv, theta));
    }

    /**
     * Compute and return the line of intersection between {@code pt} and
     * {@code this}. See also:
     * {@code https://stackoverflow.com/questions/23472048/projecting-3d-points-to-2d-plane}
     *
     * @param pt The point which when projected onto the plane using the normal
     * to the plane forms the end of the returned line of intersection.
     * @return The line of intersection between {@code pt} and {@code this} or
     * {@code null} if {@code pt} is on {@code this}. The point pl on the result
     * is the point of intersection
     */
    public V3D_Point_d getPointOfProjectedIntersect(V3D_Point_d pt) {
        if (intersects(pt)) {
            return pt;
        }
        V3D_Line_d l = new V3D_Line_d(pt, n);
        return (V3D_Point_d) getIntersect(l);
    }

    /**
     * Compute a return the line of intersection between {@code pt} and
     * {@code this}. See also:
     * {@code https://stackoverflow.com/questions/23472048/projecting-3d-points-to-2d-plane}
     *
     * @param pt The point which when projected onto the plane using the normal
     * to the plane forms the end of the returned line of intersection.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The line of intersection between {@code pt} and {@code this} or
     * {@code null} if {@code pt} is on {@code this}. The point pl on the result
     * is the point of intersection
     */
    public V3D_Point_d getPointOfProjectedIntersect(V3D_Point_d pt,
            double epsilon) {
        if (intersects(epsilon, pt)) {
            return pt;
        }
        V3D_Line_d l = new V3D_Line_d(pt, n);
        return (V3D_Point_d) getIntersect(l, epsilon);
    }

    /**
     * Check a and b are on the same side of this. If either are on the boundary
     * then return {@code true}.
     *
     * @param a A point.
     * @param b Another point. The triangle to check the points to see if they
     * are all on the same side of a line that intersects the edge of another
     * triangle.
     * @param epsilon The tolerance within which vector components are regarded
     * as equal.
     * @return {@code true} if an intersection is found and {@code false}
     * otherwise.
     */
    public boolean isOnSameSide(V3D_Point_d a, V3D_Point_d b,
            double epsilon) {
        int aside = getSideOfPlane(a, epsilon);
        if (aside == 0) {
            return true;
        }
        int bside = getSideOfPlane(b, epsilon);
        if (bside == 0) {
            return true;
        }
        return aside == bside;
    }

    /**
     * Check a and b are on the same side of this. If either are on the boundary
     * then return {@code false}.
     *
     * @param a A point.
     * @param b Another point. The triangle to check the points to see if they
     * are all on the same side of a line that intersects the edge of another
     * triangle.
     * @param epsilon The tolerance within which vector components are regarded
     * as equal.
     * @return {@code true} if an intersection is found and {@code false}
     * otherwise.
     */
    public boolean isOnSameSideNotOn(V3D_Point_d a, V3D_Point_d b,
            double epsilon) {
        int aside = getSideOfPlane(a, epsilon);
        if (aside == 0) {
            return false;
        }
        int bside = getSideOfPlane(b, epsilon);
        if (bside == 0) {
            return false;
        }
        return aside == bside;
    }

    /**
     * Return the side of the plane pt is on.
     *
     * @param epsilon The tolerance within which two vector components are
     * regarded as being equal.
     * @param pt The point.
     * @return 1 if pt is on the same side of the plane that the normal points
     * towards. 0 if pt is on the plane. -1 if pt is on the other side of the
     * plane that the normal points towards.
     */
    public int getSideOfPlane(V3D_Point_d pt, double epsilon) {
        double[] coeffs = getEquation().coeffs;
        Double x = coeffs[0] * pt.getX()
                + coeffs[1] * pt.getY()
                + coeffs[2] * pt.getZ()
                + coeffs[3];
        if (Math_Double.equals(x, 0d, epsilon)) {
            return 0;
        } else {
            return x.compareTo(0d);
        }
    }

    /**
     * Return the side of the plane pt is on.
     *
     * @param pt The point.
     * @return 1 if pt is on the same side of the plane that the normal points
     * towards. 0 if pt is on the plane. -1 if pt is on the other side of the
     * plane that the normal points towards.
     */
    public int getSideOfPlane(V3D_Point_d pt) {
        double[] coeffs = getEquation().coeffs;
        double x = coeffs[0] * pt.getX()
                + coeffs[1] * pt.getY()
                + coeffs[2] * pt.getZ()
                + coeffs[3];
        if (x < 0d) {
            return -1;
        } else {
            if (x > 0d) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Check if all points in pts are on the same side of this. Points on this
     * count either way.
     *
     * @param pts The points to check.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff all points in pts are on or are on the same side
     * of this.
     */
    protected boolean allOnSameSide(double epsilon, V3D_Point_d... pts) {
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
                    if (!isOnSameSide(pts[0], pts[i], epsilon)) {
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff all points in pts are on or are on the same side
     * of this.
     */
    protected boolean allOnSameSideNotOn(double epsilon, V3D_Point_d... pts) {
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
                    if (!isOnSameSideNotOn(pts[0], pts[i], epsilon)) {
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
     * @param epsilon The tolerance within which vector components are regarded
     * as equal.
     * @return true if the point is on or between the parallel planes.
     */
    public boolean isBetweenPlanes(V3D_Plane_d pl, V3D_Point_d pt,
            double epsilon) {
        if (pl.isOnSameSide(getP(), pt, epsilon)) {
            return this.isOnSameSide(pl.getP(), pt, epsilon);
        }
        return false;
    }
}
