/*
 * Copyright 2021 Andy Turner, University of Leeds.
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
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigRationalSqrt;

/**
 * 3D representation of a ray - a type of infinite line that starts at a point
 * and moves only in one direction and not in the other. The line begins at the
 * point {@link #p}, travels in the direction {@link #v} and goes through the
 * point {@link #q}. The "*" denotes a point in 3D and the line is shown with a
 * line of "e" symbols in the following depiction: {@code
 *                                       z
 *                          y           +
 *                          +          /                * p=<x0,y0,z0>
 *                          |         /                e
 *                          |    z0  /                e
 *                          |      \/                e
 *                          |      /                e
 *                          |     /               e
 *                          |    /               e
 *                          |   /               e
 *                       y0-|  /               e
 *                          | /               e
 *                          |/         x1    e
 *  - ----------------------|-----------|---e---|---- + x
 *                         /|              e   x0
 *                        / |-y1          e
 *                       /  |           e
 *                  z1  /   |          e
 *                    \/    |         e
 *                    /     |        e
 *                   /      |       * q=<x1,y1,z1>
 *                  /       |      e
 *                 /        |     e
 *                -         |    e
 *                          -   e
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Ray extends V3D_Line {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_Ray(V3D_Ray l) {
        super(l);
    }

    /**
     * Create a new instance.
     *
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     */
    public V3D_Ray(V3D_Point p, V3D_Point q) {
        super(p, q);
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_Ray(V3D_Line l) {
        super(l);
    }

    /**
     * Create a new instance.
     *
     * @param l What {@code this} is created from.
     */
    public V3D_Ray(V3D_Envelope.LineSegment l) {
        this(new V3D_Point(l.p), new V3D_Point(l.q));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Ray) {
            V3D_Ray l = (V3D_Ray) o;
            if (l.p.equals(p) && l.q.equals(q)) {
                return true;
            }
            if (l.p.equals(q) && l.q.equals(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param l The line segment to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_Ray l) {
        return p.equals(l.p) && isIntersectedBy(l.q);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.p);
        hash = 31 * hash + Objects.hashCode(this.q);
        return hash;
    }

    /**
     * @param v The vector to apply to each coordinate of this.
     * @return a new V3D_Ray which is {@code this} with the {@code v} applied.
     */
    @Override
    public V3D_Ray apply(V3D_Vector v) {
        return new V3D_Ray(p.apply(v), q.apply(v));
    }

    /**
     * @param p A point to test for intersection.
     * @return {@code true} if {@code this} is intersected by {@code p}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point pt) {
        boolean isPossibleIntersection = isPossibleIntersection(pt);
        if (isPossibleIntersection) {
            Math_BigRationalSqrt a = p.getDistance(this.p);
            if (a.getX().isZero()) {
                return true;
            }
            Math_BigRationalSqrt b = p.getDistance(this.q);
            if (b.getX().isZero()) {
                return true;
            }
            Math_BigRationalSqrt l = this.p.getDistance(this.q);
            if (a.add(b).compareTo(l) != 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * This compares the location of {@code pt} to the location of {@link #p}
     * and the direction of {@link #v}. If the {@code pt} is on a side of
     * {@link #p} and {@link #v} is moving away in any of the axial directions,
     * then there is no chance of an intersection.
     *
     * @param pt The point to test for a possible intersection.
     * @return {@code false} if there is no chance of intersection, and
     * {@code true} otherwise.
     */
    private boolean isPossibleIntersection(V3D_Point pt) {
        int ptxcpx = pt.x.compareTo(p.x);
        int vdxc0 = v.dx.compareTo(BigRational.ZERO);
        switch (ptxcpx) {
            case -1:
                switch (vdxc0) {
                    case -1:
                        return getptycpy(pt);
                    case 0:
                        return false;
                    default:
                        return false;
                }
            case 0:
                return getptycpy(pt);
            default:
                switch (vdxc0) {
                    case -1:
                        return false;
                    case 0:
                        return false;
                    default:
                        return getptycpy(pt);
                }
        }
    }

    private boolean getptycpy(V3D_Point pt) {
        int ptycpy = pt.y.compareTo(p.y);
        int vdyc0 = v.dy.compareTo(BigRational.ZERO);
        switch (ptycpy) {
            case -1:
                switch (vdyc0) {
                    case -1:
                        return getptzcpz(pt);
                    case 0:
                        return false;
                    default:
                        return false;
                }
            case 0:
                return getptzcpz(pt);
            default:
                switch (vdyc0) {
                    case -1:
                        return false;
                    case 0:
                        return false;
                    default:
                        return getptzcpz(pt);
                }
        }
    }

    private boolean getptzcpz(V3D_Point pt) {
        int ptzcpz = pt.z.compareTo(p.z);
        int vdzc0 = v.dz.compareTo(BigRational.ZERO);
        switch (ptzcpz) {
            case -1:
                switch (vdzc0) {
                    case -1:
                        return true;
                    case 0:
                        return false;
                    default:
                        return false;
                }
            case 0:
                return true;
            default:
                switch (vdzc0) {
                    case -1:
                        return false;
                    case 0:
                        return false;
                    default:
                        return true;
                }
        }
    }

    /**
     * @param r A ray to test if it intersects with {@code this}.
     * @param flag Used to distinguish this method from
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}. The
     * value is ignored.
     * @return {@code true} iff {@code r} intersects with {@code this}.
     */
    public boolean isIntersectedBy(V3D_Ray r, boolean flag) {
        if (p.equals(r.p)) {
            return true;
        }
        V3D_Line tl = new V3D_Line(this);
        boolean isIntersectedrtl = r.isIntersectedBy(tl);
        if (isIntersectedrtl == false) {
            return false;
        }
        V3D_Line rl = new V3D_Line(r);
        boolean isIntersectedtrl = isIntersectedBy(rl);
        if (isIntersectedtrl == false) {
            return false;
        }
        /**
         * The rays may point along the same line. If they point in the same
         * direction, then they intersect. If they point in opposite directions,
         * then they do not intersect unless the points they start at intersect
         * with the other ray.
         */
        if (isIntersectedrtl && isIntersectedtrl) {
            if (r.isIntersectedBy(p)) {
                return true;
            }
            if (isIntersectedBy(r.p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param l A line segment to test if it intersects with {@code this}.
     * @param flag Used to distinguish this method from
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}. The
     * value is ignored.
     * @return {@code true} iff {@code r} intersects with {@code this}.
     */
    public boolean isIntersectedBy(V3D_LineSegment l, boolean flag) {
        V3D_Ray rlpq = new V3D_Ray(l);
        if (!isIntersectedBy(rlpq, flag)) {
            return false;
        }
        V3D_Ray rlqp = new V3D_Ray(l.q, l.p);
        return isIntersectedBy(rlqp, flag);
    }

    /**
     * @param l A line to test for intersection within the specified tolerance.
     * @return true if p is within t of this given scale.
     */
    @Override
    public boolean isIntersectedBy(V3D_Line l) {
        V3D_Geometry i = super.getIntersection(l);
        if (i == null) {
            return false;
        }
        if (i instanceof V3D_Point) {
            return isIntersectedBy((V3D_Point) i);
        } else {
            return true;
        }
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if there is no
     * intersection.
     *
     * @param l The line to get the geometrical intersection with this.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l) {
        // Check if infinite lines intersect.
        V3D_Geometry g = V3D_Line.getIntersection(new V3D_Line(this),
                new V3D_Line(l));
        if (g == null) {
            // There is no intersection.
            return g;
        }
        /**
         * If lines intersects at a point, then check this point is on this.
         */
        if (g instanceof V3D_Point) {
            if (isIntersectedBy((V3D_Point) g)) {
                return g;
            } else {
                return null;
            }
        } else if (g instanceof V3D_Line) {
            // If the lines are the same, then return this. 
            return this;
        } else {
            // There is no intersection.
            return null;
        }
    }

    /**
     * Intersects {@code this} with {@code r}. If they are equivalent then
     * return {@code this}. If they overlap then return the part that overlaps -
     * which is either a point, a line segment, or a ray. {@code null} is
     * returned if {@code this} and {@code r} do not intersect.
     *
     * @param r The line to get intersection with this.
     * @param flag To distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}. The
     * value is ignored.
     * @return The intersection between {@code this} and {@code r}.
     */
    public V3D_Geometry getIntersection(V3D_Ray r, boolean flag) {
        V3D_Line tl = new V3D_Line(this);
        V3D_Geometry rtl = r.getIntersection(tl);
        if (rtl == null) {
            return null;
        } else if (rtl instanceof V3D_Point) {
            V3D_Point pt = (V3D_Point) rtl;
            if (r.isIntersectedBy(pt)) {
                return pt;
            } else {
                return null;
            }
        } else {
            // Then rtl is an instance of V3D_Ray.
            V3D_Line rl = new V3D_Line(r);
            V3D_Geometry grl = getIntersection(rl);
            if (grl instanceof V3D_Point) {
                return grl;
            } else {
                /**
                 * Then grl is an instance of a V3D_Ray. The rays may point
                 * along the same line. If they point in the same direction,
                 * then they intersect and the start of the ray is the start
                 * point of the ray that intersects both rays. If they point in
                 * opposite directions, then they do not intersect unless the
                 * points they start at intersect with the other ray and in this
                 * instance, the intersection is the line segment between them.
                 */
                if (isIntersectedBy(r.p)) {
                    if (r.isIntersectedBy(p)) {
                        return new V3D_LineSegment(r.p, p);
                    } else {
                        return r;
                    }
                } else {
                    if (isIntersectedBy(r.p)) {
                        return this;
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap then return the part that overlaps -
     * which is either a point, a line segment, or a ray. {@code null} is
     * returned if {@code this} and {@code l} do not intersect.
     *
     * @param l The line to get intersection with this.
     * @param flag To distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}. The
     * value is ignored.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry getIntersection(V3D_LineSegment l, boolean flag) {
        return getIntersection(this, l);
    }

    /**
     * @param pt A point for which the shortest line to this is returned.
     * @return The line having the shortest distance between {@code pt} and
     * {@code this}.
     */
    @Override
    public V3D_LineSegment getLineOfIntersection(V3D_Point pt) {
        V3D_LineSegment lip = super.getLineOfIntersection(pt);
        if (isIntersectedBy(lip.q)) {
            return lip;
        } else {
            return new V3D_LineSegment(pt, p);
        }
    }

    /**
     * @param pt The point projected onto this.
     * @return A point on {@code this} which is the shortest distance from
     * {@code pt}.
     */
    @Override
    public V3D_Point getPointOfIntersection(V3D_Point pt) {
        if (this.isIntersectedBy(pt)) {
            return pt;
        }
        return (V3D_Point) getIntersection(this, getLineOfIntersection(pt));
    }

    /**
     * Let the distance from {@code pt} to {@link #p} be ptpd. Let the distance
     * from {@code pt} ro {@link q} be ptqd. Let the distance from {@link #p} to
     * {@link #q} be pqd. Let the distance from {@code pt} to the line through
     * {@link #p} and {@link #q} be ptld. Then the is point to the ray is less
     * than the distance of the point from either end of the line and the
     * distance from either end of the line is greater than the length of the
     * line then the distance is the shortest of the distances from the point to
     * the points at either end of the line segment. In all other cases, the
     * distance is the distance between the point and the line.
     *
     * @param p A point for which the minimum distance from {@code this} is
     * returned.
     *
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance between this and {@code p}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point pt, int oom) {
        if (isIntersectedBy(pt)) {
            return BigDecimal.ZERO;
        }
        V3D_Line l = new V3D_Line(this);
        V3D_Point ip = l.getPointOfIntersection(pt);
        if (isIntersectedBy(ip)) {
            return ip.getDistance(l.p, oom);
        } else {
            return p.getDistance(pt, oom);
        }
    }

    /**
     * This is for calculating the minimum distance between {@code this} and
     * {@code r}. If the rays intersect, the distance is zero. If they are going
     * away from each other, the distance is the distance between their start
     * points. If the rays get closer, then calculate the closest they come. If
     * they are parallel, then the distance between them does not change.
     *
     * @param r The line segment to return the distance from.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The distance from {@code this} to {@code r} at the {@code oom}
     * precision.
     */
    @Override
    public BigDecimal getDistance(V3D_Ray r, int oom) {
        if (isParallel(r)) {
            return ((V3D_LineSegment) getLineOfIntersection(r.p)).getLength(oom);
        } else {
            V3D_Line tl = new V3D_Line(this);
            //BigDecimal tldr = tl.getDistance(r, oom);
            V3D_LineSegment tlrp = tl.getLineOfIntersection(r.p);
            V3D_Line rl = new V3D_Line(r);
            //BigDecimal rldt = rl.getDistance(this, oom);
            V3D_LineSegment rltp = rl.getLineOfIntersection(p);
            if (isIntersectedBy(tlrp.q)) {
                BigDecimal tlrpl = tlrp.getLength(oom);
                if (r.isIntersectedBy(rltp.q)) {
                    return tlrpl.min(rltp.getLength(oom));
                } else {
                    return tlrpl;
                }
            } else {
                if (r.isIntersectedBy(rltp.q)) {
                    return rltp.getLength(oom);
                } else {
                    return p.getDistance(r.p, oom);
                }
            }
        }
    }
}
