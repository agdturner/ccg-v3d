/*
 * Copyright 2020 Andy Turner, University of Leeds.
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
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;

/**
 * 3D representation of an infinite length line. The line passes through the
 * point {@link #p} and is travelling in the direction {@link #v} through point
 * {@link #q}. The "*" denotes a point in 3D and the line is shown with a line
 * of "e" symbols in the following depiction: {@code
 *                                       y                e
 *                          z           +                e
 *                          +          /                * p=<x0,y0,z0>
 *                          |         /                e
 *                          |        /                e
 *                          |    y0-/                e
 *                          |      /                e
 *                          |     /               e
 *                          |    /               e
 *                          |   /               e
 *                       z0-|  /               e
 *                          | /               e
 *                          |/         x1    e
 *  - ----------------------|-----------/---e---/---- + x
 *                         /|              e   x0
 *                        / |-z1          e
 *                       /  |           e
 *                      /   |          e
 *                  y1-/    |         e
 *                    /     |        e
 *                   /      |       * q=<x1,y1,z1>
 *                  /       |      e
 *                 /        |     e
 *                -         |    e
 *                          -   e
 * }
 * <ul>
 * <li>Vector Form
 * <ul>
 * <li>{@code (x,y,z) = (p.x,p.y,p.z) + t(v.dx,v.dy,v.dz)}</li>
 * </ul>
 * <li>Parametric Form (where t describes a particular point on the line)
 * <ul>
 * <li>{@code x = p.x + t(v.dx)}</li>
 * <li>{@code y = p.y + t(v.dy)}</li>
 * <li>{@code z = p.z + t(v.dz)}</li>
 * </ul>
 * <li>Symmetric Form (assume {@code v.dx}, {@code v.dy}, and {@code v.dz} are
 * all nonzero)
 * <ul>
 * <li>{@code (x−p.x)/v.dx = (y−p.y)/v.dy = (z−p.z)/v.dz}</li>
 * </ul></li>
 * </ul>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Line extends V3D_Geometry {

    private static final long serialVersionUID = 1L;

    /**
     * A point defining the line.
     */
    public final V3D_Point p;

    /**
     * A point defining the line.
     */
    public final V3D_Point q;

    /**
     * The direction vector from {@link #p} in the direction of {@link #q}.
     */
    public final V3D_Vector v;

    /**
     * {@code p} should not be equal to {@code q}. If unsure use
     * {@link #V3D_Line(V3D_Point, V3D_Point, boolean)}.
     *
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     */
    public V3D_Line(V3D_Point p, V3D_Point q) {
        this.p = p;
        this.q = q;
        v = new V3D_Vector(q.x.subtract(p.x), q.y.subtract(p.y),
                q.z.subtract(p.z));
    }

    /**
     * {@code p} should not be equal to {@code q}. If unsure use
     * {@link #V3D_Line(V3D_Point, V3D_Point, boolean)}.
     *
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param check Ignored. It is here to distinguish with
     * {@link #V3D_Line(V3D_Point, V3D_Point)}.
     * @throws RuntimeException if {@code p.equals(q)}.
     */
    public V3D_Line(V3D_Point p, V3D_Point q, boolean check) {
        if (p.equals(q)) {
            throw new RuntimeException("Points " + p + " and " + q
                    + " are the same and so do not define a line.");
        }
        this.p = p;
        this.q = q;
        v = new V3D_Vector(q.x.subtract(p.x), q.y.subtract(p.y),
                q.z.subtract(p.z));
    }

    /**
     * {@code v} should not be the zero vector {@code <0,0,0>}. If unsure use
     * {@link #V3D_Line(V3D_Point, V3D_Point, boolean)}.
     *
     * @param p What {@link #p} is set to.
     * @param v What {@link #v} is set to.
     * @throws RuntimeException if {@code v.isZeroVector()}.
     */
    public V3D_Line(V3D_Point p, V3D_Vector v) {
        if (v.isZeroVector()) {
            throw new RuntimeException("Vector " + v + " is the zero vector "
                    + "and so cannot define a line.");
        }
        this.p = p;
        this.v = v;
        q = p.apply(v);
    }

    /**
     * Checks to ensure v is not the zero vector {@code <0,0,0>}.
     *
     * @param p What {@link #p} is set to.
     * @param v What {@link #v} is set to.
     * @param check Ignored. It is here to distinguish with
     * {@link #V3D_Line(V3D_Point, V3D_Vector)}.
     */
    public V3D_Line(V3D_Point p, V3D_Vector v, boolean check) {
        this.p = p;
        this.v = v;
        q = p.apply(v);
    }

    /**
     * @param l Vector_LineSegment3D
     */
    public V3D_Line(V3D_Line l) {
        this.p = l.p;
        this.q = l.q;
        v = new V3D_Vector(q.x.subtract(p.x), q.y.subtract(p.y),
                q.z.subtract(p.z));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(p=" + p.toString()
                + ", q=" + q.toString() + ", v=" + v.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Line) {
            return equals((V3D_Line) o);
        }
        return false;
    }

    /**
     * @param l The line to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_Line l) {
        return isIntersectedBy(l.p) && isIntersectedBy(l.q);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.p);
        hash = 17 * hash + Objects.hashCode(this.v);
        return hash;
    }

    /**
     * @param pt A point to test for intersection.
     * @return {@code true} if p is on the line.
     */
    public boolean isIntersectedBy(V3D_Point pt) {
        V3D_Vector ppt = new V3D_Vector(pt.x.subtract(p.x),
                pt.y.subtract(p.y), pt.z.subtract(p.z));
        V3D_Vector cp = v.getCrossProduct(ppt);
        return cp.dx.isZero() && cp.dy.isZero() && cp.dz.isZero();
    }

    /**
     * @param l The line to test this with to see if they are parallel.
     * @return {@code true} If this and {@code l} are parallel.
     */
    public boolean isParallel(V3D_Line l) {
        return v.isScalarMultiple(l.v);
    }

    /**
     * This computes the intersection and tests if it is {@code null}
     *
     * @param l The line to test if it isIntersectedBy with this.
     * @return {@code true} If this and {@code l} intersect.
     */
    public boolean isIntersectedBy(V3D_Line l) {
        return getIntersection(l) != null;
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}.
     *
     * @param l The line to get intersection with this.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry getIntersection(V3D_Line l) {
        return getIntersection(this, l);
    }

    /**
     * https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection A utility
     * method for calculating and returning the intersection between {@code l0}
     * and {@code l1}
     *
     * @param l0 Line to intersect with {@code l1}.
     * @param l1 Line to intersect with {@code l0}.
     * @return The intersection between {@code l0} and {@code l1}.
     */
    public static V3D_Geometry getIntersection(V3D_Line l0, V3D_Line l1) {
        // Check the points.
        if (l0.isIntersectedBy(l1.p)) {
            if (l0.isIntersectedBy(l1.q)) {
                return l0; // The lines are coincident.
            } else {
                return l1.p;
            }
        } else {
            if (l0.isIntersectedBy(l1.q)) {
                return l1.q;
            }
        }
        if (l1.isIntersectedBy(l0.p)) {
            return l0.p;
        }
        if (l1.isIntersectedBy(l0.q)) {
            return l0.q;
        }
        // Case of parallel and non equal lines.
        if (l0.isParallel(l1)) {
            return null;
        }
        if (l0.v.getMagnitudeSquared().compareTo(BigRational.ZERO) == 0) {
            return null;
        }
        if (l1.v.getMagnitudeSquared().compareTo(BigRational.ZERO) == 0) {
            return null;
        }
        V3D_Vector vl0pl1q = new V3D_Vector(l0.p, l1.q);

        BigRational vl0pl1ql1v = vl0pl1q.dx.multiply(l1.v.dx)
                .add(vl0pl1q.dy.multiply(l1.v.dy))
                .add(vl0pl1q.dz.multiply(l1.v.dz));
        BigRational vl1l0 = l1.v.dx.multiply(l0.v.dx)
                .add(l1.v.dy.multiply(l0.v.dy))
                .add(l1.v.dz.multiply(l0.v.dz));
        BigRational vl0pl1ql0v = vl0pl1q.dx.multiply(l0.v.dx)
                .add(vl0pl1q.dy.multiply(l0.v.dy))
                .add(vl0pl1q.dz.multiply(l0.v.dz));
        BigRational vl1l1 = l1.v.dx.multiply(l1.v.dx)
                .add(l1.v.dy.multiply(l1.v.dy))
                .add(l1.v.dz.multiply(l1.v.dz));
        BigRational vl0l0 = l0.v.dx.multiply(l0.v.dx)
                .add(l0.v.dy.multiply(l0.v.dy))
                .add(l0.v.dz.multiply(l0.v.dz));
        BigRational den = (vl0l0.multiply(vl1l1))
                .subtract(vl1l0.multiply(vl1l0));
        if (den.compareTo(BigRational.ZERO) == 0) {
            return null;
        }
        BigRational num = (vl0pl1ql1v.multiply(vl1l0))
                .subtract(vl0pl1ql0v.multiply(vl1l1));
        BigRational t = num.divide(den);
        return new V3D_Point(l0.q.x.add(t.multiply(l0.v.dx)),
                l0.q.y.add(t.multiply(l0.v.dy)),
                l0.q.z.add(t.multiply(l0.v.dz)));
    }

    /**
     * @param v The vector to apply.
     * @return a new line.
     */
    @Override
    public V3D_Line apply(V3D_Vector v) {
        return new V3D_Line(p.apply(v), q.apply(v));
    }

    /**
     * https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
     * @param p A point for which the minimum distance from {@code this} is 
     * returned.
     * @param mps The minimum precision scale for the precision of the result.
     * @return The minimum distance between this and {@code p}.
     */
    public BigDecimal getDistance(V3D_Point p, int mps) {
        /**
         * Calculate the direction vector of the line connecting the closest 
         * points by computing the cross product.
         */
        V3D_Vector pv = new V3D_Vector(p);
        V3D_Vector cp = v.getCrossProduct(pv);
        /**
         * Calculate the delta from {@link #p} and l.p
         */
        V3D_Vector delta = new V3D_Vector(this.p).subtract(pv);
        BigRational m = BigRational.valueOf(cp.getMagnitude(mps));
        // d = cp.(delta)/m
        BigRational dp = cp.getDotProduct(delta);
        if (dp.compareTo(m) == 0) {
            return BigDecimal.ONE; // Prevent a divide by zero if both are zero.
        }
        BigRational d = dp.divide(m);
        return Math_BigDecimal.roundIfNecessary(d.toBigDecimal(), mps,
                RoundingMode.HALF_UP);
    }
    
    /**
     * https://en.wikipedia.org/wiki/Skew_lines#Nearest_points
     * https://en.wikipedia.org/wiki/Distance_between_two_parallel_lines
     * @param l A line for which the minimum distance from {@code this} is 
     * returned.
     * @param mps The minimum precision scale for the precision of the result.
    * @return The minimum distance between this and {@code l}.
     */
    public BigDecimal getDistance(V3D_Line l, int mps) {
        if (isParallel(l)) {
            //q.getDistance(l, scale, rm);
            return p.getDistance(l, mps);
//            V3D_Vector v2 = new V3D_Vector(l.p, q);
//            return v2.subtract(v.multiply((v2.getDotProduct(v))
//                    .divide(v.getMagnitudeSquared()))).getMagnitude(scale, rm);
        } else {
        /**
         * Calculate the direction vector of the line connecting the closest 
         * points by computing the cross product.
         */
        V3D_Vector cp = v.getCrossProduct(l.v);
        /**
         * Calculate the delta from {@link #p} and l.p
         */
        V3D_Vector delta = new V3D_Vector(p).subtract(new V3D_Vector(l.p));
        BigRational m = BigRational.valueOf(cp.getMagnitude(mps));
        // d = cp.(delta)/m
        BigRational dp = cp.getDotProduct(delta);
        // m should only be zero if the lines are parallel.
        BigRational d = dp.divide(m);
        return Math_BigDecimal.roundIfNecessary(d.toBigDecimal(), mps, 
                RoundingMode.HALF_UP);
        }
    }

    /**
     * @return {@code true} iff this is parallel to the plane defined by x=0.
     */
    public boolean isParallelToX0() {
        return v.dx.compareTo(BigRational.ZERO) == 0;
    }

    /**
     * @return {@code true} iff this is parallel to the plane defined by y=0.
     */
    public boolean isParallelToY0() {
        return v.dy.compareTo(BigRational.ZERO) == 0;
    }

    /**
     * @return {@code true} iff this is parallel to the plane defined by z=0.
     */
    public boolean isParallelToZ0() {
        return v.dz.compareTo(BigRational.ZERO) == 0;
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l) {
        if (this.isIntersectedBy(l)) {
            return true;
        }
        if (this.isParallelToX0()) {
            if (this.isParallelToY0()) {
                return false;
            } else if (this.isParallelToZ0()) {
                return false;
            } else {
                return !this.isParallel(l);
            }
        } else {
            if (this.isParallelToY0()) {
                if (this.isParallelToZ0()) {
                    return false;
                } else {
                    return !this.isParallel(l);
                }
            } else {
                if (this.isParallelToZ0()) {
                    return !this.isParallel(l);
                } else {
                    return true;
                }
            }
        }
    }

    /**
     * @return The points that define the plan as a matrix.
     */
    public Math_Matrix_BR getAsMatrix() {
        Math_Matrix_BR res = new Math_Matrix_BR(2, 3);
        BigRational[][] m = res.getM();
        m[0][0] = p.x;
        m[0][1] = p.y;
        m[0][2] = p.z;
        m[1][0] = q.x;
        m[1][1] = q.y;
        m[1][2] = q.z;
        return res;
    }
}
