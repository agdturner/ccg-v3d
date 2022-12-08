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

import uk.ac.leeds.ccg.math.matrices.Math_Matrix_Double;

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
public class V3D_PlaneDouble extends V3D_GeometryDouble {

    private static final long serialVersionUID = 1L;

    /**
     * The x = 0 plane.
     */
    public static final V3D_PlaneDouble X0 = new V3D_PlaneDouble(
            V3D_PointDouble.ORIGIN, V3D_VectorDouble.I);

    /**
     * The y = 0 plane.
     */
    public static final V3D_PlaneDouble Y0 = new V3D_PlaneDouble(
            V3D_PointDouble.ORIGIN, V3D_VectorDouble.J);

    /**
     * The z = 0 plane.
     */
    public static final V3D_PlaneDouble Z0 = new V3D_PlaneDouble(
            V3D_PointDouble.ORIGIN, V3D_VectorDouble.K);

    /**
     * @param pl The plane to test points are coplanar with.
     * @param points The points to test if they are coplanar with pl.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} iff all points are coplanar with pl.
     */
    public static boolean isCoplanar(double epsilon, V3D_PlaneDouble pl,
            V3D_PointDouble... points) {
        for (V3D_PointDouble pt : points) {
            if (!pl.isIntersectedBy(pt, epsilon)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param points The points to test if they are coplanar.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return {@code false} if points are coincident or collinear. {@code true}
     * iff all points are coplanar.
     */
    public static boolean isCoplanar(double epsilon, V3D_PointDouble... points) {
        // For the points to be in a plane at least one must not be collinear.
        if (V3D_PointDouble.isCoincident(points)) {
            return false;
        }
        if (!V3D_LineDouble.isCollinear0(epsilon, points)) {
            V3D_PlaneDouble p = getPlane0(epsilon, points);
            return isCoplanar(epsilon, p, points);
        }
        return false;
    }

    /**
     * @param points The points from which a plane is to be derived.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return A plane that may or may not contain all the points or
     * {@code null} if there is no such plane (if the points are coincident or
     * collinear).
     */
    public static V3D_PlaneDouble getPlane(double epsilon, 
            V3D_PointDouble... points) {
        V3D_LineDouble l = V3D_LineDouble.getLine(points);
        if (l == null) {
            return null;
        }
        for (V3D_PointDouble p : points) {
            if (!V3D_LineDouble.isCollinear(l, epsilon, p)) {
                return new V3D_PlaneDouble(l.getP(), l.getQ(), p);
            }
        }
        return null;
    }

    /**
     * @param points The points from which a plane is to be derived.
     * @param epsilon The tolerance within which two vectors are regarded as equal.
     * @return A plane that may or may not contain all the points or
     * {@code null} if there is no such plane. This does not test if the points
     * are coincident or collinear.
     */
    private static V3D_PlaneDouble getPlane0(double epsilon, 
            V3D_PointDouble... points) {
        V3D_LineDouble l = V3D_LineDouble.getLine(points);
        for (V3D_PointDouble p : points) {
            if (!V3D_LineDouble.isCollinear(l, epsilon, p)) {
                return new V3D_PlaneDouble(l.getP(), l.getQ(), p);
            }
        }
        return null;
    }

    /**
     * The point that defines the plane.
     */
    protected V3D_VectorDouble p;

    /**
     * The normal vector that defines the plane. This is perpendicular to the
     * plane.
     */
    public V3D_VectorDouble n;

    /**
     * For storing the equation of the plane.
     */
    protected transient Equation equation;

    /**
     * Create a new instance.
     *
     * @param p The plane used to create this.
     */
    public V3D_PlaneDouble(V3D_PlaneDouble p) {
        super(p.offset);
        this.p = new V3D_VectorDouble(p.p);
        this.n = new V3D_VectorDouble(p.n);
    }

    /**
     * Create a new instance.
     *
     * @param p Used to initialise {@link #p}.
     * @param n The normal of the plane.
     */
    public V3D_PlaneDouble(V3D_PointDouble p, V3D_VectorDouble n) {
        super(p.offset);
        this.p = p.rel;
        this.n = n;
    }

    /**
     * Create a new instance.
     *
     * @param l A line segment in the plane.
     * @param inplane A vector in the plane that is not a scalar multiple of the
     * vector of the line of l.
     */
    public V3D_PlaneDouble(V3D_LineSegmentDouble l, V3D_VectorDouble inplane) {
        super(l.offset);
        this.p = l.getP().rel;
        this.n = l.l.v.getCrossProduct(inplane);
    }

    /**
     * Create a new instance.
     *
     * @param p Used to initialise {@link #p}.
     * @param q A point coplanar to pl and r, not collinear to both pl and r,
     * and not equal to pl or r.
     * @param r A point coplanar to pl and qv, not collinear to both pl and qv,
     * and not equal to pl or qv.
     */
    public V3D_PlaneDouble(V3D_PointDouble p, V3D_PointDouble q, V3D_PointDouble r) {
        super(p.offset);
        V3D_VectorDouble qv = q.getVector();
        V3D_VectorDouble pq = qv.subtract(p.getVector());
        V3D_VectorDouble qr = r.getVector().subtract(qv);
        this.p = p.rel;
        this.n = pq.getCrossProduct(qr);
    }

    /**
     * Create a new instance.
     *
     * @param p Used to initialise {@link #p}.
     * @param q A point coplanar to pl and r, not collinear to both pl and r,
     * and not equal to pl or r.
     * @param r A point coplanar to pl and qv, not collinear to both pl and qv,
     * and not equal to pl or qv.
     */
    public V3D_PlaneDouble(V3D_VectorDouble p, V3D_VectorDouble q,
            V3D_VectorDouble r) {
        this(V3D_VectorDouble.ZERO, p, q, r);
    }

    /**
     * Create a new instance.
     *
     * @param offset What {@link #offset} is set to.
     * @param p Used to initialise {@link #p}.
     * @param q Coplanar but not collinear to both p and r.
     * @param r Coplanar but not collinear to both p and q.
     */
    public V3D_PlaneDouble(V3D_VectorDouble offset, V3D_VectorDouble p,
            V3D_VectorDouble q, V3D_VectorDouble r) {
        this(p, offset, p, q, r);
    }

    /**
     * Create a new instance.
     *
     * @param ptv A point vector giving the direction of the normal vector.
     * @param offset What {@link #offset} is set to.
     * @param p Used to initialise {@link #p}.
     * @param q A point coplanar to pl and r, not collinear to both pl and r,
     * and not equal to pl or r.
     * @param r A point coplanar to pl and qv, not collinear to both pl and qv,
     * and not equal to pl or qv.
     */
    public V3D_PlaneDouble(V3D_VectorDouble ptv, V3D_VectorDouble offset,
            V3D_VectorDouble p, V3D_VectorDouble q, V3D_VectorDouble r) {
        super(offset);
        V3D_VectorDouble pq = q.subtract(p);
        if (pq.equals(V3D_VectorDouble.ZERO)) {
            throw new RuntimeException("Cannot define plane as p equals q.");
        }
        V3D_VectorDouble qr = r.subtract(q);
        if (qr.equals(V3D_VectorDouble.ZERO)) {
            throw new RuntimeException("Cannot define plane as q equals r.");
        }
        this.p = p;
        this.n = pq.getCrossProduct(qr);
        if (n.isZero()) {
            throw new RuntimeException("Points do not define a plane");
        }
        V3D_VectorDouble v;
        if (ptv.isZero()) {
            v = p.add(q).add(r).reverse();
        } else {
            v = ptv;
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
     * @param p Used to initialise {@link #p}.
     * @param q A point coplanar to pl and r, not collinear to both pl and r,
     * and not equal to pl or r.
     * @param r A point coplanar to pl and qv, not collinear to both pl and qv,
     * and not equal to pl or qv.
     */
    public V3D_PlaneDouble(V3D_PointDouble pt, V3D_VectorDouble offset,
            V3D_VectorDouble p, V3D_VectorDouble q, V3D_VectorDouble r) {
        this(pt.getVector(), offset, p, q, r);
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
    public final V3D_PointDouble getP() {
        return new V3D_PointDouble(offset, p);
    }

    /**
     * Find a perpendicular vector using: user65203, How to find perpendicular
     * vector to another vector?, URL (version: 2020-10-01):
     * https://math.stackexchange.com/qv/3821978
     *
     * @return A perpendicular vector.
     */
    public final V3D_VectorDouble getPV() {
        V3D_VectorDouble pv;
        V3D_VectorDouble v1 = new V3D_VectorDouble(0d, n.dz, -n.dy);
        V3D_VectorDouble v2 = new V3D_VectorDouble(-n.dz, 0d, n.dx);
        V3D_VectorDouble v3 = new V3D_VectorDouble(-n.dy, n.dx, 0d);
        double mv1 = v1.getMagnitudeSquared();
        double mv2 = v2.getMagnitudeSquared();
        double mv3 = v3.getMagnitudeSquared();
        if (mv1 > mv2) {
            if (mv1 > mv3) {
                pv = v1;
            } else {
                pv = v3;
            }
        } else {
            if (mv2 > mv3) {
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
     * @return A point on the plane.
     */
    public final V3D_PointDouble getQ() {
        return getQ(getPV());
    }

    /**
     * Get another point on the plane.
     *
     * @param pv What is from {@link #getPV()}.
     * @return A point on the plane.
     */
    public final V3D_PointDouble getQ(V3D_VectorDouble pv) {
        return new V3D_PointDouble(offset, this.p.add(pv));
    }

    /**
     * Get another point on the plane.
     *
     * @return A point on the plane.
     */
    public final V3D_PointDouble getR() {
        return getR(getPV());
    }

    /**
     * Get another point on the plane.
     *
     * @param pv What is from {@link #getPV()}.
     * @return A point on the plane.
     */
    public final V3D_PointDouble getR(V3D_VectorDouble pv) {
        V3D_VectorDouble pvx = pv.getCrossProduct(n);
        return new V3D_PointDouble(offset, this.p.add(pvx));
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
        double[] coeffs;

        /**
         * Create a new instance.
         */
        public Equation() {
            coeffs = new double[4];
            V3D_PointDouble tp = getP();
            double k = n.dx * tp.getX() + n.dy * tp.getY() + n.dz * tp.getZ();
            coeffs[0] = n.dx;
            coeffs[1] = n.dy;
            coeffs[2] = n.dz;
            coeffs[3] = -k;
        }
    }

    /**
     * For getting the equation of the plane.
     *
     * @return The equation of the plane as a String.
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
    public boolean isIntersectedBy(V3D_PointDouble pt, double epsilon) {
        equation = getEquation();
        double c = (equation.coeffs[0] * pt.getX() + equation.coeffs[1] * pt.getY()
                + equation.coeffs[2] * pt.getZ() + equation.coeffs[3]);
        return Math.abs(c) < epsilon;
    }

    /**
     * Identify if this is intersected by point {@code pt} using the matrices
     * approach described here:
     * https://math.stackexchange.com/questions/684141/check-if-a-point-is-on-a-plane-minimize-the-use-of-multiplications-and-divisio/684580#684580
     *
     * @param pt The point to test for intersection with.
     * @return {@code true} iff the geometry is intersected by {@code pv}.
     */
    public boolean isIntersectedByAlternative(V3D_PointDouble pt) {
        double[][] m = new double[4][4];
        V3D_PointDouble tp = getP();
        if (tp.isOrigin()) {
            V3D_PointDouble tpt = new V3D_PointDouble(pt);
            tpt.translate(V3D_VectorDouble.IJK);
            V3D_PointDouble ttp = new V3D_PointDouble(getP());
            ttp.translate(V3D_VectorDouble.IJK);
            V3D_VectorDouble pv = getPV();
            V3D_PointDouble ttq = getQ(pv);
            ttq.translate(V3D_VectorDouble.IJK);
            V3D_PointDouble ttr = getR(pv);
            ttr.translate(V3D_VectorDouble.IJK);
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
            V3D_VectorDouble pv = getPV();
            V3D_PointDouble tq = getQ(pv);
            V3D_PointDouble tr = getR(pv);
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
     * @param l The line to test if it is on the plane.
     * @return {@code true} If {@code pt} is on the plane.
     */
    public boolean isOnPlane(V3D_LineDouble l, double epsilon) {
        return isIntersectedBy(l.getP(), epsilon) && isIntersectedBy(l.getQ(), epsilon);
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
    public V3D_GeometryDouble getIntersectionOption(V3D_LineDouble l, double epsilon) {
        // Special case
        if (isParallel(l, epsilon)) {
            if (isOnPlane(l, epsilon)) {
                return l;
            }
            return null;
        }
        double dot = n.getDotProduct(l.v);
        if (dot == 0d) {
            return null;
        } else {
            V3D_PointDouble lp = l.getP();
            V3D_PointDouble tp = getP();
            boolean atOrigin = lp.isOrigin();
            if (atOrigin) {
                V3D_PointDouble tlp = new V3D_PointDouble(lp);
                tlp.translate(l.v);
                V3D_PointDouble ttp = new V3D_PointDouble(tp);
                ttp.translate(l.v);
                V3D_VectorDouble w = new V3D_VectorDouble(ttp, tlp);
                if (w.isZero()) {
                    return tp;
                }
                double fac = -n.getDotProduct(w) / (dot);
                if (fac == 0d) {
                    return tp;
                }
                //fac = -dot_v3v3(p_no, w) / dot
                //V3D_Vector u2 = l.v*(fac);
                V3D_VectorDouble u2 = l.v.multiply(fac);
                //u = mul_v3_fl(u, fac)
                //V3D_Point p00 = new V3D_Point(tlp);
                //V3D_Point p00 = new V3D_Point(l.getP());
                //V3D_Point p00 = l.getP();
                //V3D_Point p00 = new V3D_Point(tp);
                tlp.translate(u2);
                tlp.translate(l.v.reverse());
                return tlp;
            } else {
                V3D_VectorDouble w = new V3D_VectorDouble(tp, lp);
                if (w.isZero()) {
                    return tp;
                }
                double fac = -n.getDotProduct(w) / (dot);
                V3D_VectorDouble u2 = l.v.multiply(fac);
                V3D_PointDouble p00 = new V3D_PointDouble(lp);
                p00.translate(u2);
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
//        if (this.isParallel(l, )) {
//            if (this.isOnPlane(l, )) {
//                return l;
//            } else {
//                return null;
//            }
//        }
//        // Are either of the points of l on the plane.
//        //V3D_Point lp = l.getP(oom);
//        V3D_Point lp = l.getP();
//        if (this.isIntersectedBy(lp, )) {
//            return lp;
//        }
//        V3D_Point lq = l.getQ();
//        if (this.isIntersectedBy(lq, )) {
//            return lq;
//        }
//        V3D_Point tp = getP();
//        V3D_Vector pv = getPAsVector();
//        V3D_Point tq = getQ(pv, );
//        V3D_Point tr = getR(pv, );
//        // Are any of these points on the line?
//        if (l.isIntersectedBy(tp, )) {
//            return tp;
//        }
//        if (l.isIntersectedBy(tp, )) {
//            return tq;
//        }
//        if (l.isIntersectedBy(tp, )) {
//            return tr;
//        }
//        
//        int oomN6 = oom - 6;
//        //V3D_Vector lv = l.getV(oomN6, rm);
//        V3D_Vector lv = l.v;
//        Math_BigRational[][] m = new Math_BigRational[4][4];
//        m[0][0] = 1.0d;
//        m[0][1] = 1.0d;
//        m[0][2] = 1.0d;
//        m[0][3] = 1.0d;
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
////        V3D_Point tq = getQ(pv, );
////        V3D_Point tr = getR(pv, );
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
//        m[0][0] = 1.0d;
//        m[0][1] = 1.0d;
//        m[0][2] = 1.0d;
//        m[0][3] = Math_BigRational.ZERO;
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
////                lp.getX(oomN6, rm)+(lv.getDX(oomN6, rm)*(t)),
////                lp.getY(oomN6, rm)+(lv.getDY(oomN6, rm)*(t)),
////                lp.getZ(oomN6, rm)+(lv.getDZ(oomN6, rm)*(t)));
//        V3D_Point res = new V3D_Point(e,
//                lp.getX(oomN6, rm)+(lv.getDX(oomN6, rm)*(t)),
//                lp.getY(oomN6, rm)+(lv.getDY(oomN6, rm)*(t)),
//                lp.getZ(oomN6, rm)+(lv.getDZ(oomN6, rm)*(t)));
//        if (false) {
//            // Check if res is on the line.
//            if (!l.isIntersectedBy(res, )) {
//                System.out.println("Not on line! - l.isIntersectedBy(r, )");
//            }
//            if (!isIntersectedBy(res, )) {
//                System.out.println("Not on plane! - !isIntersectedBy(r, )");
//                // Check side of line
//                if (isOnSameSide(res, lp, )) {
//                    System.out.println("isOnSameSide(res, lp, )");
//                } else {
//                    System.out.println("!isOnSameSide(res, lp, )");
//                    V3D_Plane pl2 = new V3D_Plane(tp, tr, tq, );
//                    V3D_Point p2 = (V3D_Point) pl2.getIntersectiondel(l, );
//                    if (!res.equals(p2, )) {
//                        System.out.println(res);
//                        System.out.println(p2);
//                    } else {
//                        System.out.println(res);
//                    }
//                }
//                if (isOnSameSide(res, lq, )) {
//                    System.out.println("isOnSameSide(res, lq, )");
//                } else {
//                    System.out.println("!isOnSameSide(res, lq, )");
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
////                l.pl.getX(oom)-(l.v.getDX(oom)*(t)),
////                l.pl.getY(oom)-(l.v.getDY(oom)*(t)),
////                l.pl.getZ(oom)-(l.v.getDZ(oom)*(t)));
//    }
    /**
     * Get the intersection between the geometry and the line {@code l}.
     *
     * @param l The line to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_GeometryDouble getIntersection(V3D_LineDouble l, double epsilon) {
        if (isParallel(l, epsilon)) {
            if (isOnPlane(l, epsilon)) {
                return l;
            } else {
                return null;
            }
        }
        // Are either of the points of l on the plane.
        //V3D_Point lp = l.getP(oom);
        V3D_PointDouble lp = l.getP();
        if (isIntersectedBy(lp, epsilon)) {
            return lp;
        }
        V3D_PointDouble lq = l.getQ();
        if (isIntersectedBy(lq, epsilon)) {
            return lq;
        }
        //V3D_Vector lv = l.getV(oomN2, rm);
        V3D_VectorDouble lv = l.v;
        double[][] m = new double[4][4];
        m[0][0] = 1.0d;
        m[0][1] = 1.0d;
        m[0][2] = 1.0d;
        m[0][3] = 1.0d;
//        m[1][0] = pl.getX(oom);
//        m[1][1] = qv.getX(oom);
//        m[1][2] = r.getX(oom);
//        m[1][3] = lp.getX(oom);
//        m[2][0] = pl.getY(oom);
//        m[2][1] = qv.getY(oom);
//        m[2][2] = r.getY(oom);
//        m[2][3] = lp.getY(oom);
//        m[3][0] = pl.getZ(oom);
//        m[3][1] = qv.getZ(oom);
//        m[3][2] = r.getZ(oom);
//        m[3][3] = lp.getZ(oom);
        V3D_PointDouble tp = getP();
        V3D_VectorDouble pv = getPV();
        V3D_PointDouble tq = getQ(pv);
        V3D_PointDouble tr = getR(pv);
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
        double t = -numm.getDeterminant() / denm.getDeterminant();
        V3D_PointDouble res = new V3D_PointDouble(
                lp.getX() + (lv.dx * (t)),
                lp.getY() + (lv.dy * (t)),
                lp.getZ() + (lv.dz * (t)));
        return res;
    }

    /**
     * Get the intersection between the geometry and the line segment {@code l}.
     *
     * @param l The line segment to intersect with.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_LineSegmentDouble l, double epsilon) {
        V3D_GeometryDouble g = getIntersection(l.l, epsilon);
        if (g == null) {
            return null;
        } else if (g instanceof V3D_LineDouble) {
            return l;
        } else {
            V3D_PointDouble pt = (V3D_PointDouble) g;
            if (l.getPPL().isOnSameSide(pt, l.getQ())
                    && l.getQPL().isOnSameSide(pt, l.getP())) {
                return pt;
            }
//            if (l.isIntersectedBy(pt)) {
//                return pt;
//            }
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
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The intersection between {@code this} and {@code pl}
     */
    public V3D_GeometryDouble getIntersection(V3D_PlaneDouble pl,
            double epsilon) {
        /**
         * Calculate the cross product of the normal vectors to get the
         * direction of the line.
         */
        V3D_VectorDouble v = n.getCrossProduct(pl.n);

        /**
         * Check special cases.
         */
        if (v.isZero()) {
            // The planes are parallel.
            if (pl.isIntersectedBy(getP(), epsilon)) {
                // The planes are the same.
                return this;
            }
//            if (pl.equalsIgnoreOrientation(this)) {
//                // The planes are the same.
//                return this;
//            }
            // There is no intersection.
            return null;
        }
        
        try {
        V3D_PointDouble pi = pl.getPointOfProjectedIntersection(getP(), epsilon);
        } catch (RuntimeException e) {
            V3D_PointDouble pi = pl.getPointOfProjectedIntersection(getP(), epsilon);
        }
        
        V3D_PointDouble pi = pl.getPointOfProjectedIntersection(getP(), epsilon);
        
        
        return new V3D_LineDouble(pi, v);
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
    public V3D_GeometryDouble getIntersection(V3D_PlaneDouble pl1,
            V3D_PlaneDouble pl2, double epsilon) {
        V3D_GeometryDouble g = getIntersection(pl1, epsilon);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_PlaneDouble gp) {
                return gp.getIntersection(pl2, epsilon);
            } else {
                return pl2.getIntersection((V3D_LineDouble) g, epsilon);
            }
        }
    }

    /**
     * @param p The plane to test if it is parallel to this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is parallel to {@code pl}.
     */
    public boolean isParallel(V3D_PlaneDouble p, double epsilon) {
        return n.isScalarMultiple(p.n, epsilon);
    }

    /**
     * @param l The line to test if it is parallel to this.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return {@code true} if {@code this} is parallel to {@code l}.
     */
    public boolean isParallel(V3D_LineDouble l, double epsilon) {
        if (n.isOrthogonal(l.v, epsilon)) {
            return true;
        }
        return n.getDotProduct(l.v) == 0d;
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
    public boolean equals(V3D_PlaneDouble pl, double epsilon) {
        if (n.getDotProduct(pl.n) > 0d) {
            return equalsIgnoreOrientation(pl, epsilon);
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
    public boolean equalsIgnoreOrientation(V3D_PlaneDouble pl, double epsilon) {
        if (n.isScalarMultiple(pl.n, epsilon)) {
            if (pl.isIntersectedBy(getP(), epsilon)) {
                if (this.isIntersectedBy(pl.getP(), epsilon)) {
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
        V3D_PointDouble tp = getP();
        V3D_VectorDouble pv = getPV();
        V3D_PointDouble tq = getQ(pv);
        V3D_PointDouble tr = getR(pv);
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
    public double getDistance(V3D_PointDouble pt) {
        return Math.sqrt(getDistanceSquared(pt, true));
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pt A point.
     * @return The distance from {@code this} to {@code pl}.
     */
    public double getDistanceSquared(V3D_PointDouble pt) {
        return getDistanceSquared(pt, true);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param pt A point.
     * @param noInt To distinguish this from
     * {@link #getDistanceSquared(uk.ac.leeds.ccg.v3d.geometry.d.V3D_PointDouble)}
     * @return The distance from {@code this} to {@code pl}.
     */
    public double getDistanceSquared(V3D_PointDouble pt, boolean noInt) {
        V3D_VectorDouble v = new V3D_VectorDouble(pt, getP());
        V3D_VectorDouble u = n.getUnitVector();
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
    public double getDistance(V3D_PlaneDouble p, double epsilon) {
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
    public double getDistanceSquared(V3D_PlaneDouble pl, double epsilon) {
        if (isParallel(pl, epsilon)) {
            return pl.getDistanceSquared(getP());
        }
        return 0d;
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V3D_LineDouble l) {
        return Math.sqrt(getDistanceSquared(l));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @return The minimum distance to {@code l}.
     */
    public double getDistanceSquared(V3D_LineDouble l) {
        return getDistanceSquared(l.getP(), true);
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V3D_LineSegmentDouble l) {
        return Math.sqrt(getDistanceSquared(l));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @return The minimum distance to {@code l}.
     */
    public double getDistanceSquared(V3D_LineSegmentDouble l) {
        double lpd = getDistanceSquared(l.getP());
        double lqd = getDistanceSquared(l.getQ());
        return Math.min(lpd, lqd);
    }

    /**
     * Change {@link #offset} without changing the overall plane.
     *
     * @param offset What {@link #offset} is set to.
     */
    public void setOffset(V3D_VectorDouble offset) {
        if (!this.offset.equals(offset)) {
            p = p.add(this.offset).subtract(offset);
            this.offset = offset;
        }
    }

    @Override
    public void translate(V3D_VectorDouble v) {
        super.translate(v);
        this.equation = null;
    }

    @Override
    public V3D_PlaneDouble rotate(V3D_LineDouble axis, double theta,
            double epsilon) {
        return new V3D_PlaneDouble(getP().rotate(axis, theta, epsilon),
                n.rotate(axis.v.getUnitVector(), theta));
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
    public V3D_PointDouble getPointOfProjectedIntersection(V3D_PointDouble pt,
            double epsilon) {
        if (isIntersectedBy(pt, epsilon)) {
            return pt;
        }
        V3D_LineDouble l = new V3D_LineDouble(pt, n);
        return (V3D_PointDouble) getIntersection(l, epsilon);
    }

    /**
     * Check a and b are on the same side of this. If either are on the boundary
     * then return {@code true}.
     *
     * @param a A point.
     * @param b Another point. The triangle to check the points to see if they
     * are all on the same side of a line that intersects the edge of another
     * triangle.
     * @return {@code true} if an intersection is found and {@code false}
     * otherwise.
     */
    public boolean isOnSameSide(V3D_PointDouble a, V3D_PointDouble b) {
        double aside = getSideOfPlane(a);
        if (aside == 0d) {
            return true;
        }
        double bside = getSideOfPlane(b);
        if (bside == 0d) {
            return true;
        }
        return aside < 0d && bside < 0d || aside > 0d && bside > 0d;
    }

    /**
     * Plug the coordinates of pt into the plane equation.
     *
     * @param pt The point.
     * @return 1 if pt is on the same side of the plane that the normal points
     * towards. 0 if pt is on the plane. -1 if pt is on the other side of the
     * plane that the normal points towards.
     */
    public double getSideOfPlane(V3D_PointDouble pt) {
        double[] coeffs = getEquation().coeffs;
        return coeffs[0] * pt.getX() + coeffs[1] * pt.getY()
                + coeffs[2] * pt.getZ() + coeffs[3];
    }

    /**
     * Check if all points in pts are on the same side of this. Points on this
     * count either way.
     *
     * @param pts The points to check.
     * @return {@code true} iff all points in pts are on or are on the same side
     * of this.
     */
    protected boolean allOnSameSide(V3D_PointDouble[] pts, double epsilon) {
        // Find a point not on the plane if there is one.
        V3D_PointDouble pt = null;
        for (var x : pts) {
            if (isIntersectedBy(x, epsilon)) {
                pt = x;
            }
        }
        // If there is not a point in pts that is not on the plane, then returnone, then
        if (pt == null) {
            return true;
        }
        boolean res = true;
        for (var x : pts) {
            if (!isOnSameSide(x, pt)) {
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
     * @return true if the point is on or between the parallel planes.
     */
    public boolean isBetweenPlanes(V3D_PlaneDouble pl, V3D_PointDouble pt) {
        if (pl.isOnSameSide(getP(), pt)) {
            return this.isOnSameSide(pl.getP(), pt);
        }
        return false;
    }
}
