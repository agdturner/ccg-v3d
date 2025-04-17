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
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * For representing and processing rectangles in 3D. A rectangle is a right
 * angled quadrilateral. The four corners are the points
 * {@link #p}, {@link #q}, {@link #r} and {@link #s}. The following depicts a
 * rectangle {@code
 q  *-----* r
    |   / |
    |  /  | 
    | /   |
  p *-----* s
 }
 * The angles PQR, QRS, RSP, SPQ are all 90 degrees or Pi/2 radians.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Rectangle extends V3D_Area {

    private static final long serialVersionUID = 1L;

    /**
     * For storing a triangle that makes up the rectangle.
     */
    protected final V3D_Triangle pqr;

    /**
     * For storing a triangle that makes up half the rectangle.
     */
    protected final V3D_Triangle rsp;

    public V3D_Rectangle(V3D_Rectangle r) {
        super(r.env, r.offset, null);
        pqr = new V3D_Triangle(r.pqr);
        rsp = new V3D_Triangle(r.rsp);
    }
            
    /**
     * Create a new instance.
     * @param r
     * @param oom
     * @param rm
     */
    public V3D_Rectangle(V3D_Rectangle r, int oom, RoundingMode rm) {
        this(r.getP(oom, rm), r.getQ(oom, rm), r.getR(oom, rm), r.getS(oom, rm), oom, rm);
    }
    
    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p The bottom left corner of the rectangle.
     * @param q The top left corner of the rectangle.
     * @param r The top right corner of the rectangle.
     * @param s The bottom right corner of the rectangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @throws java.lang.RuntimeException iff the points do not define a
     * rectangle.
     */
    public V3D_Rectangle(V3D_Environment env, V3D_Vector offset, V3D_Vector p, 
            V3D_Vector q, V3D_Vector r, V3D_Vector s, int oom, 
            RoundingMode rm) {
        super(env, offset, new V3D_Plane(env, offset, p, q, r, oom, rm));
        rsp = new V3D_Triangle(pl, offset, r, s, p);
        pqr = new V3D_Triangle(pl, offset, p, q, r);
    }

    /**
     * Creates a new instance.
     *
     * @param p Used to initialise {@link #offset}, {@link #pqr} and {@link #rsp}.
     * @param q Used to initialise {@link #pqr} and {@link #rsp}.
     * @param r Used to initialise {@link #pqr} and {@link #rsp}.
     * @param s Used to initialise {@link #rsp}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Rectangle(V3D_Point p, V3D_Point q, V3D_Point r, V3D_Point s,
            int oom, RoundingMode rm) {
        this(new V3D_Plane(p, q, r, oom, rm), p, q, r, s, oom, rm);
    }
    
    /**
     * Creates a new instance.
     *
     * @param pl What {@link #pl} is set to, and used to initialise the triangle 
     * planes.
     * @param p Used to initialise {@link #offset}, {@link #pqr} and {@link #rsp}.
     * @param q Used to initialise {@link #pqr} and {@link #rsp}.
     * @param r Used to initialise {@link #pqr} and {@link #rsp}.
     * @param s Used to initialise {@link #rsp}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Rectangle(V3D_Plane pl, V3D_Point p, V3D_Point q, V3D_Point r, 
            V3D_Point s, int oom, RoundingMode rm) {
        super(p.env, p.offset, pl);
        V3D_Point qn = new V3D_Point(q);
        qn.setOffset(p.offset, oom, rm);
        V3D_Point rn = new V3D_Point(r);
        rn.setOffset(p.offset, oom, rm);
        V3D_Point sn = new V3D_Point(s);
        sn.setOffset(p.offset, oom, rm);
        rsp = new V3D_Triangle(pl, this.offset, rn.rel, sn.rel, p.rel);
        pqr = new V3D_Triangle(pl, this.offset, p.rel, qn.rel, rn.rel);
    }

    @Override
    public String toString() {
        return toStringSimple("");
    }

    /**
     * @param pad Padding
     * @return A simple String representation of this.
     */
    public String toStringSimple(String pad) {
        return pad + this.getClass().getSimpleName() + "("
                + toStringFieldsSimple("") + "\n)";
    }

    @Override
    protected String toStringFields(String pad) {
        return "\n" + super.toStringFields(pad) + ",\n"
                + pad + "pqr=" + getPQR().toString(pad) + ",\n"
                + pad + "rsp=" + getRSP().toString(pad);
    }

    @Override
    protected String toStringFieldsSimple(String pad) {
        return "\n" + super.toStringFieldsSimple(pad) + ",\n"
                + pad + "pqr=" + getPQR().toStringSimple(pad) + ",\n"
                + pad + "rsp=" + getRSP().toStringSimple(pad);
    }

    @Override
    public V3D_Point[] getPointsArray(int oom, RoundingMode rm) {
        return getPoints(oom, rm).values().toArray(new V3D_Point[4]);
    }
    
    @Override
    public HashMap<Integer, V3D_Point> getPoints(int oom, RoundingMode rm) {
        if (points == null) {
            points = new HashMap<>(4);
            points.put(0, getP(oom, rm));
            points.put(1, getQ(oom, rm));
            points.put(2, getR(oom, rm));
            points.put(3, getS(oom, rm));
        }
        return points;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A collection of the external edges.
     */
    @Override
    public HashMap<Integer, V3D_LineSegment> getEdges(int oom, RoundingMode rm) {
        if (edges == null) {
            edges = new HashMap<>(4);
            edges.put(0, pqr.getPQ(oom, rm));
            edges.put(1, pqr.getQR(oom, rm));
            edges.put(2, rsp.getPQ(oom, rm));
            edges.put(3, rsp.getQR(oom, rm));
        }
        return edges;
    }

    /**
     * @return {@link #pqr}.
     */
    public V3D_Triangle getPQR() {
        return pqr;
    }
    
    /**
     * @return {@link #rsp}.
     */
    public V3D_Triangle getRSP() {
        return rsp;
    }
    
    @Override
    protected void initPl(int oom, RoundingMode rm) {
        pqr.initPl(oom, rm);
        pl = pqr.pl;
    }

    @Override
    protected void initPl(V3D_Point pt, int oom, RoundingMode rm) {
        pqr.initPl(pt, oom, rm);
        pl = pqr.pl;
    }
    
    /**
     * @return {@link #p} with {@link #offset} applied.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Point getP(int oom, RoundingMode rm) {
        return getPQR().getP(oom, rm);
    }

    /**
     * @return {@link #q} with {@link #offset} applied.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Point getQ(int oom, RoundingMode rm) {
        return getPQR().getQ(oom, rm);
    }

    /**
     * @return {@link #r} with {@link #offset} applied.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Point getR(int oom, RoundingMode rm) {
        return getPQR().getR(oom, rm);
    }

    /**
     * @return {@link #s} with {@link #offset} applied.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Point getS(int oom, RoundingMode rm) {
        return getRSP().getQ(oom, rm);
    }

    @Override
    public V3D_AABB getAABB(int oom, RoundingMode rm) {
        if (en == null) {
            en = getPQR().getAABB(oom, rm).union(getRSP().getAABB(oom, rm), oom);
        }
        return en;
    }
    
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The line segment from {@link #r} to {@link #s}.
     */
    protected V3D_LineSegment getRS(int oom, RoundingMode rm) {
        return getRSP().getPQ(oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The line segment from {@link #s} to {@link #p}.
     */
    protected V3D_LineSegment getSP(int oom, RoundingMode rm) {
        return getRSP().getQR(oom, rm);
    }

    /**
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point or line segment.
     */
    public V3D_FiniteGeometry getIntersect(V3D_Line l, int oom, 
            RoundingMode rm) {
        if (getPl(oom, rm).getIntersect(l, oom, rm) != null) {
            V3D_FiniteGeometry pqri = pqr.getIntersect(l, oom, rm);
            V3D_FiniteGeometry rspi = rsp.getIntersect(l, oom, rm);
            return join(oom, rm, pqri, rspi);
        } else {
            return null;
        }
    }

    private V3D_FiniteGeometry join(int oom, RoundingMode rm,
            V3D_FiniteGeometry pqri, V3D_FiniteGeometry rspi) {
        if (pqri == null) {
            if (rspi == null) {
                return null;
            } else {
                return rspi;
            }
        } else if (pqri instanceof V3D_LineSegment pqril) {
            if (rspi == null) {
                return pqri;
            } else if (rspi instanceof V3D_LineSegment rspil) {
                return V3D_LineSegment.getGeometry(oom, rm, pqril, rspil);
            } else {
                return pqri;
            }
        } else {
            // pqri instanceof V3D_Point
            if (rspi == null) {
                return pqri;
            } else if (rspi instanceof V3D_LineSegment) {
                return rspi;
            } else {
                return pqri;
            }
        }
    }

    /**
     * Calculates and returns the intersections between {@code this} and
     * {@code l}.
     *
     * @param l The line segment to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection or {@code null} iff there is no intersection.
     */
    public V3D_FiniteGeometry getIntersect(V3D_LineSegment l, int oom, 
            RoundingMode rm) {
        if (getPl(oom, rm).getIntersect(l, oom, rm) != null) {
            V3D_FiniteGeometry pqri = pqr.getIntersect(l, oom, rm);
            V3D_FiniteGeometry rspi = rsp.getIntersect(l, oom, rm);
            return join(oom, rm, pqri, rspi);
        } else {
            return null;
        }
    }

    @Override
    public BigRational getPerimeter(int oom, RoundingMode rm) {
        int oomn2 = oom - 2;
        return (pqr.getPQ(oomn2, rm).getLength(oomn2, rm).getSqrt(oom, rm)
                .add(pqr.getQR(oomn2, rm).getLength(oomn2, rm).getSqrt(oom, rm)))
                .multiply(BigRational.TWO);
    }

    @Override
    public BigRational getArea(int oom, RoundingMode rm) {
        int oomn2 = oom - 2;
        return (pqr.getPQ(oomn2, rm).getLength(oomn2, rm).multiply(
                pqr.getQR(oomn2, rm).getLength(oomn2, rm), oomn2, rm))
                .getSqrt(oom, rm);
    }

    /**
     * Get the distance between this and {@code pl}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance from {@code this} to {@code pl}.
     */
    public BigRational getDistance(V3D_Point p, int oom, RoundingMode rm) {
        return (new Math_BigRationalSqrt(getDistanceSquared(p, oom, rm), oom, rm))
                .getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code pt}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared to {@code p}.
     */
    @Override
    public BigRational getDistanceSquared(V3D_Point pt, int oom, RoundingMode rm) {
        BigRational d1 = pqr.getDistanceSquared(pt, oom, rm);
        BigRational d2 = rsp.getDistanceSquared(pt, oom, rm);
        return BigRational.min(d1, d2);
    }

    /**
     * Move the rectangle.
     *
     * @param v What is added to {@link #p}, {@link #q}, {@link #r}, {@link #s}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    @Override
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        pqr.translate(v, oom, rm);
        rsp.translate(v, oom, rm);
    }

    @Override
    public V3D_Rectangle rotate(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd, 
            BigRational theta, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V3D_Rectangle(this);
        } else {
            return rotateN(ray, uv, bd, theta, oom, rm);
        }
    }
    
    @Override
    public V3D_Rectangle rotateN(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd, 
            BigRational theta, int oom, RoundingMode rm) {
        return new V3D_Rectangle(
                getP(oom, rm).rotate(ray, uv, bd, theta, oom, rm),
                getQ(oom, rm).rotate(ray, uv, bd, theta, oom, rm),
                getR(oom, rm).rotate(ray, uv, bd, theta, oom, rm),
                getS(oom, rm).rotate(ray, uv, bd, theta, oom, rm), oom, rm);
    }

    /**
     * Calculate and return the intersection between {@code this} and {@code pl}
     *
     * @param pl The plane to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code this} and {@code pl}.
     */
    public V3D_FiniteGeometry getIntersect(V3D_Plane pl, int oom, RoundingMode rm) {
        if (getPl(oom, rm).equals(pl, oom, rm)) {
            return new V3D_Rectangle(this, oom, rm);
        }
        V3D_FiniteGeometry pqri = pqr.getIntersect(pl, oom, rm);
        if (pqri == null) {
            return rsp.getIntersect(pl, oom, rm);
        } else if (pqri instanceof V3D_Triangle) {
            return new V3D_Rectangle(this, oom, rm);
        } else {
            V3D_FiniteGeometry rspi = rsp.getIntersect(pl, oom, rm);
            return join(oom, rm, pqri, rspi);
        }
    }
    
    /**
     * Computes and returns the intersection between {@code this} and {@code t}.
     * The intersection could be: null, a point, a line segment, a triangle, or
     * a convex hull (with 4, 5, 6 or 7 sides).
     *
     * @param t The triangle intersect with this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection between {@code t} and {@code this} or
     * {@code null} if there is no intersection.
     */
    public V3D_FiniteGeometry getIntersect(V3D_Triangle t, int oom, 
            RoundingMode rm) {
        if (getPl(oom, rm).equals(t.getPl(oom, rm), oom, rm)) {
            V3D_FiniteGeometry pqrit = pqr.getIntersect(t, oom, rm);
            V3D_FiniteGeometry rspit = rsp.getIntersect(t, oom, rm);
            if (pqrit == null) {
                return rspit;
            } else if (pqrit instanceof V3D_Point) {
                if (rspit == null) {
                    return pqrit;
                } else {
                    return rspit;
                }
            } else if (pqrit instanceof V3D_LineSegment) {
                if (rspit == null) {
                    return pqrit;
                } else {
                    return rspit;
                }
            } else {
                V3D_Point[] pqritps = pqrit.getPointsArray(oom, rm);
                V3D_Point[] rspitps = rspit.getPointsArray(oom, rm);
                V3D_Point[] pts = Arrays.copyOf(pqritps, pqritps.length + rspitps.length);
                System.arraycopy(rspitps, 0, pts, pqritps.length, rspitps.length);
                return V3D_ConvexArea.getGeometry(oom, rm, pts);
            }
        } else {
            V3D_FiniteGeometry pqrit = pqr.getIntersect(t, oom, rm);
            V3D_FiniteGeometry rspit = rsp.getIntersect(t, oom, rm);
            return join(oom, rm, pqrit, rspit);
        }
    }
    
    /**
     * Compute and return the intersection with {@code r}.
     *
     * @param r The ray to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point, line segment or {@code null}.
     */
    @Override
    public V3D_FiniteGeometry getIntersect(V3D_Ray r, int oom,
            RoundingMode rm) {
        V3D_FiniteGeometry gpqr = pqr.getIntersect(r, oom, rm);
        V3D_FiniteGeometry grsp = rsp.getIntersect(r, oom, rm);
        if (gpqr == null) {
            return grsp;
        } else {
            if (grsp == null) {
                return gpqr;
            }
            return join(oom, rm, gpqr, grsp);
        }
    }
    
    /**
     * Compute and return the intersection with {@code r}.
     *
     * @param r The ray to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point, line segment or {@code null}.
     */
    @Override
    public V3D_Point getIntersectNonCoplanar(V3D_Ray r, int oom,
            RoundingMode rm) {
        V3D_Point gpqr = pqr.getIntersectNonCoplanar(r, oom, rm);
        if (gpqr == null) {
            return rsp.getIntersectNonCoplanar(r, oom, rm);
        } else {
            return gpqr;
        }
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistance(V3D_Line l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom - 6, rm), oom, rm).getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistanceSquared(V3D_Line l, int oom, RoundingMode rm) {
        return BigRational.min(
                rsp.getDistanceSquared(l, oom, rm),
                pqr.getDistanceSquared(l, oom, rm));
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistance(V3D_LineSegment l, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(l, oom - 6, rm), oom, rm).getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public BigRational getDistanceSquared(V3D_LineSegment l, int oom, RoundingMode rm) {
        return BigRational.min(
                rsp.getDistanceSquared(l, oom, rm),
                pqr.getDistanceSquared(l, oom, rm));
    }

    /**
     * Get the minimum distance to {@code pl}.
     *
     * @param pl A plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code p}.
     */
    public BigRational getDistance(V3D_Plane pl, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(pl, oom - 6, rm), oom, rm).getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code pl}.
     *
     * @param pl A plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code p}.
     */
    public BigRational getDistanceSquared(V3D_Plane pl, int oom, RoundingMode rm) {
        return BigRational.min(
                rsp.getDistanceSquared(pl, oom, rm),
                pqr.getDistanceSquared(pl, oom, rm));
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code t}.
     */
    public BigRational getDistance(V3D_Triangle t, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(t, oom - 6, rm), oom, rm)
                .getSqrt(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code t}.
     */
    public BigRational getDistanceSquared(V3D_Triangle t, int oom, RoundingMode rm) {
        return BigRational.min(
                rsp.getDistanceSquared(t, oom, rm),
                pqr.getDistanceSquared(t, oom, rm));
    }

    /**
     * @param r The rectangle to test if it is equal to this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff this is equal to r.
     */
    //@Overrides
    public boolean equals(V3D_Rectangle r, int oom, RoundingMode rm) {
        V3D_Point[] pts = getPointsArray(oom, rm);
        V3D_Point[] rpts = r.getPointsArray(oom, rm);
        for (var x : pts) {
            boolean found = false;
            for (var y : rpts) {
                if (x.equals(y, oom, rm)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        for (var x : rpts) {
            boolean found = false;
            for (var y : pts) {
                if (x.equals(y, oom, rm)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * For testing if four points form a rectangle.
     *
     * @param p First clockwise or anti-clockwise point.
     * @param q Second clockwise or anti-clockwise point.
     * @param r Third clockwise or anti-clockwise point.
     * @param s Fourth clockwise or anti-clockwise point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff pl, qv, r and s form a rectangle.
     */
    public static boolean isRectangle(V3D_Point p, V3D_Point q,
            V3D_Point r, V3D_Point s, int oom, RoundingMode rm) {
        V3D_LineSegment pq = new V3D_LineSegment(p, q, oom, rm);
        V3D_LineSegment qr = new V3D_LineSegment(q, r, oom, rm);
        V3D_LineSegment rs = new V3D_LineSegment(r, s, oom, rm);
        V3D_LineSegment sp = new V3D_LineSegment(s, p, oom, rm);
        return pq.l.isParallel(rs.l, oom, rm)
                && qr.l.isParallel(sp.l, oom, rm);
    }

    /**
     * @param pt The point to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point or line segment.
     */
    @Override
    public boolean intersects(V3D_Point pt, int oom, RoundingMode rm) {
        if (pqr.intersects0(pt, oom, rm)) {
            return true;
        } else {
            return rsp.intersects0(pt, oom, rm);
        }
    }

    @Override
    public boolean intersects(V3D_AABB aabb, int oom, RoundingMode rm) {
        return pqr.intersects(aabb, oom, rm)
                || rsp.intersects(aabb, oom, rm);
    }
    
    /**
     * If there is intersection with the Axis Aligned Bounding Boxes of pqr 
     * or rsp, then intersections are computed, so if the intersection is wanted 
     * consider using:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.V3D_Line, int, java.math.RoundingMode)}
     *
     * @param l The line segment to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersects(V3D_Line l, int oom, RoundingMode rm) {
        return pqr.intersects(l, oom, rm)
            || rsp.intersects(l, oom, rm);
    }
    
    /**
     * If there is intersection with the Axis Aligned Bounding Boxes of pqr 
     * or rsp, then intersections are computed, so if the intersection is wanted 
     * consider using:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegment, int, java.math.RoundingMode)}
     *
     * @param l The line segment to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersects(V3D_LineSegment l, int oom, RoundingMode rm) {
        return pqr.intersects(l, oom, rm)
            || rsp.intersects(l, oom, rm);
    }
    
    /**
     * If no point aligns, then returns false, otherwise the intersection is 
     * computed, so if that is needed use:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.V3D_Ray, int, java.math.RoundingMode)}
     *
     * @param r The ray to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersects(V3D_Ray r, int oom, RoundingMode rm) {
        if (r.isAligned(getP(oom, rm), oom, rm)
            || r.isAligned(getQ(oom, rm), oom, rm)
            || r.isAligned(getR(oom, rm), oom, rm)
            || r.isAligned(getS(oom, rm), oom, rm)) {
            return getIntersect(r, oom, rm) != null;
        } else {
            return false;
        }
    }
    
    /**
     * If no point aligns, then returns false, otherwise the intersection is 
     * computed, so if that is needed use:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.V3D_Ray, int, java.math.RoundingMode)}
     *
     * @param r The ray to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersectsNonCoplanar(V3D_Ray r, int oom, RoundingMode rm) {
        if (r.isAligned(getP(oom, rm), oom, rm)
            || r.isAligned(getQ(oom, rm), oom, rm)
            || r.isAligned(getR(oom, rm), oom, rm)
            || r.isAligned(getS(oom, rm), oom, rm)) {
            return getIntersectNonCoplanar(r, oom, rm) != null;
        } else {
            return false;
        }
    }
    
    /**
     * @param t A triangle to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if t intersects this.
     */
    @Override
    public boolean intersects(V3D_Triangle t, int oom, RoundingMode rm) {
        if (getPl(oom, rm).equalsIgnoreOrientation(t.getPl(oom, rm), oom, rm)) {
            return intersectsCoplanar(t, oom, rm);
        } else {
            return intersects0(t, oom, rm);
        }
    }

    /**
     * Use when {@code this} and {@code t} are known to be coplanar.
     *
     * @param t A triangle to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if t intersects this.
     */
    public boolean intersectsCoplanar(V3D_Triangle t, int oom, RoundingMode rm) {
        return t.intersectsCoplanar(pqr.getP(oom, rm), oom, rm)
                || t.intersectsCoplanar(pqr.getQ(oom, rm), oom, rm)
                || t.intersectsCoplanar(pqr.getR(oom, rm), oom, rm)
                || t.intersectsCoplanar(rsp.getP(oom, rm), oom, rm)
                || t.intersectsCoplanar(rsp.getQ(oom, rm), oom, rm)
                || t.intersectsCoplanar(rsp.getR(oom, rm), oom, rm);
    }

    /**
     * Only use if {@code this} and {@code t} are not coplanar.
     *
     * @param t A triangle to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if t intersects this.
     */
    public boolean intersects0(V3D_Triangle t, int oom, RoundingMode rm) {
        return pqr.intersectsNonCoplanar(t, oom, rm)
                || rsp.intersectsNonCoplanar(t, oom, rm);
    }
    
    /**
     * @param r Another rectangle to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if t intersects this.
     */
    public boolean intersects0(V3D_Rectangle r, int oom, RoundingMode rm) {
        if (getPl(oom, rm).equalsIgnoreOrientation(r.getPl(oom, rm), oom, rm)) {
            return r.intersectsCoplanar(pqr, oom, rm)
                || r.intersectsCoplanar(rsp, oom, rm);
        } else {
            return r.intersects0(pqr, oom, rm)
                || r.intersects0(rsp, oom, rm);
        }
    }
}
