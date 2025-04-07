/*
 * Copyright 2025 Andy Turner, University of Leeds.
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
import java.util.ArrayList;
import java.util.HashMap;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;

/**
 * Defined by a V3D_ConvexArea and a collection of non edge sharing
 * V3D_PolygonNoInternalHoles areas each defining an external hole or concavity.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V3D_PolygonNoInternalHoles extends V3D_Area {

    private static final long serialVersionUID = 1L;

    /**
     * The convex hull.
     */
    public V3D_ConvexArea ch;

    /**
     * The collection of externalHoles comprised of points in {@link points}.
     * Only two points of an external hole should intersect the edges of the
     * convex hull.
     */
    public HashMap<Integer, V3D_PolygonNoInternalHoles> externalHoles;

    /**
     * Create a new instance that is a shallow copy of the polygon.
     *
     * @param p The polygon to copy.
     */
    public V3D_PolygonNoInternalHoles(V3D_PolygonNoInternalHoles p) {
        this(p.points, p.ch, p.edges, p.externalHoles);
    }

//    /**
//     * Create a new instance that is a deep copy of the polygon.
//     *
//     * @param pv The polygon to copy.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode for any rounding.
//     */
//    public V3D_PolygonNoInternalHoles(V3D_PolygonNoInternalHoles pv, int oom,
//            RoundingMode rm) {
//        this(pv.getPoints(oom, rm).entrySet().stream().collect(Collectors.toMap(
//                Map.Entry::getKey, x -> new V3D_Point(x.getValue()))),
//                pv.getConvexHull(oom, rm), pv.getEdges(oom, rm), 
//                pv.getExternalHoles());
//    }
    /**
     * Create a new instance that is also a convex hull.
     *
     * @param ch This convex hull with the points from which this is created and
     * what {@link #ch} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_PolygonNoInternalHoles(V3D_ConvexArea ch, int oom,
            RoundingMode rm) {
        this(ch.getPointsArray(oom, rm), ch, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param points The external edge points in clockwise order.
     * @param n The normal to the plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_PolygonNoInternalHoles(V3D_Point[] points, V3D_Vector n, int oom,
            RoundingMode rm) {
        this(points, new V3D_ConvexArea(oom, rm, n, points), oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param points The external edge points in clockwise order.
     * @param ch What {@link #ch} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_PolygonNoInternalHoles(V3D_Point[] points, V3D_ConvexArea ch,
            int oom, RoundingMode rm) {
        super(points[0].env, V3D_Vector.ZERO, ch.pl);
        this.points = new HashMap<>();
        this.ch = ch;
        edges = new HashMap<>();
        externalHoles = new HashMap<>();
        PTThing p = new PTThing();
        p.pts = new ArrayList<>();
        p.points = points;
        p.isHole = false;
        // Find a start on the edge.
        int i0 = 0;
        boolean foundStart = false;
        while (!foundStart) {
            p.p0 = points[i0];
            p.p0int = V3D_LineSegment.intersects(oom, rm, p.p0,
                    ch.edges.values());
            if (p.p0int) {
                foundStart = true;
            }
            i0++;
        }
        int i1 = i0;
        p.p1 = points[i1];
        i1++;
        if (p.p0.equals(p.p1, oom, rm)) {
            p.p1int = p.p0int;
        } else {
            this.points.put(this.points.size(), p.p0);
            edges.put(edges.size(), new V3D_LineSegment(p.p0, p.p1, oom, rm));
            p.p1int = V3D_LineSegment.intersects(oom, rm, p.p1,
                    ch.edges.values());
            if (p.p0int) {
                if (!p.p1int) {
                    p.pts.add(p.p0);
                    p.isHole = true;
                }
            }
        }
        for (int i = i1; i < points.length; i++) {
            doThing(oom, rm, i, p);
        }
        for (int i = 0; i < i0; i++) {
            doThing(oom, rm, i, p);
        }
    }

    private void doThing(int oom, RoundingMode rm, int index, PTThing p) {
        p.p0 = p.p1;
        p.p0int = p.p1int;
        p.p1 = p.points[index];
        if (p.p0.equals(p.p1, oom, rm)) {
            p.p1int = p.p0int;
        } else {
            p.p1int = V3D_LineSegment.intersects(oom, rm, p.p1,
                    ch.edges.values());
            if (p.isHole) {
                if (p.p1int) {
                    p.pts.add(p.p0);
                    p.pts.add(p.p1);
                    externalHoles.put(externalHoles.size(),
                            new V3D_PolygonNoInternalHoles(
                                    p.pts.toArray(V3D_Point[]::new),
                                    ch.pl.n, oom, rm));
                                    //getPl(oom, rm).n, oom, rm));
                    p.pts = new ArrayList<>();
                    p.isHole = false;
                } else {
                    p.pts.add(p.p0);
                }
            } else {
                if (p.p0int) {
                    if (!p.p1int) {
                        p.pts.add(p.p0);
                        p.isHole = true;
                    }
                }
            }
            this.points.put(this.points.size(), p.p0);
            edges.put(edges.size(), new V3D_LineSegment(p.p0, p.p1, oom, rm));
        }
    }

    @Override
    public BigRational getPerimeter(int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BigRational getArea(int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean intersects(V3D_AABB aabb, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BigRational getDistanceSquared(V3D_Point pt, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    protected class PTThing {

        boolean isHole;
        boolean p0int;
        boolean p1int;
        ArrayList<V3D_Point> pts;
        V3D_Point[] points;
        V3D_Point p0;
        V3D_Point p1;

        PTThing() {
        }
    }

    /**
     * Create a new instance.
     *
     * @param points What {@link #points} is set to.
     * @param ch What {@link #ch} is set to.
     * @param edges What {@link #edges} is set to.
     * @param externalHoles What {@link #externalHoles} is set to.
     */
    public V3D_PolygonNoInternalHoles(HashMap<Integer, V3D_Point> points,
            V3D_ConvexArea ch, HashMap<Integer, V3D_LineSegment> edges,
            HashMap<Integer, V3D_PolygonNoInternalHoles> externalHoles) {
        super(ch.env, V3D_Vector.ZERO, ch.pl);
        this.points = points;
        this.ch = ch;
        this.edges = edges;
        this.externalHoles = externalHoles;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.getClass().getName()).append("(");
        {
            s.append("\nch (\n").append(ch.toString()).append("\n)\n");
        }
        {
            s.append("\nedges (\n");
            if (edges != null) {
                for (var entry : edges.entrySet()) {
                    s.append("(");
                    s.append(entry.getKey());
                    s.append(",");
                    s.append(entry.getValue().toString());
                    s.append("), ");
                }
            }
            int l = s.length();
            s = s.delete(l - 2, l);
            s.append("\n)\n");
        }
        {
            s.append("\nexternalHoles (\n");
            if (externalHoles != null) {
                for (var entry : externalHoles.entrySet()) {
                    s.append("(");
                    s.append(entry.getKey());
                    s.append(",");
                    s.append(entry.getValue().toString());
                    s.append("), ");
                }
            }
            int l = s.length();
            s = s.delete(l - 2, l);
            s.append("\n)\n");
        }
        s.append("\n)");
        return s.toString();
    }

    @Override
    protected void initPl(int oom, RoundingMode rm) {
        pl = ch.getPl(oom, rm);
    }

    @Override
    protected void initPl(V3D_Point pt, int oom, RoundingMode rm) {
        pl = ch.getPl(pt, oom, rm);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A copy of {@link exterior#ch} with the given tolerance applied.
     */
    public V3D_ConvexArea getConvexHull(int oom, RoundingMode rm) {
        return new V3D_ConvexArea(ch);
    }

    /**
     * @return {@link edges}.
     */
    @Override
    public HashMap<Integer, V3D_LineSegment> getEdges(int oom, RoundingMode rm) {
        return edges;
    }

//    /**
//     * @return Deep copy of {@link edges}.
//     */
//    public HashMap<Integer, V3D_LineSegment> getEdges() {
//        HashMap<Integer, V3D_LineSegment> r = new HashMap<>();
//        for (V3D_LineSegment l : edges.values()) {
//            r.put(r.size(), new V3D_LineSegment(l));
//        }
//        return r;
//    }
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A copy of {@link externalHoles} with the given tolerance applied.
     */
    public HashMap<Integer, V3D_PolygonNoInternalHoles> getExternalHoles(
            int oom, RoundingMode rm) {
        HashMap<Integer, V3D_PolygonNoInternalHoles> r = new HashMap<>();
        for (V3D_PolygonNoInternalHoles h : externalHoles.values()) {
            r.put(r.size(), new V3D_PolygonNoInternalHoles(h));
        }
        return r;
    }

    /**
     * @return {@link externalHoles}.
     */
    public HashMap<Integer, V3D_PolygonNoInternalHoles> getExternalHoles() {
        return externalHoles;
    }

    @Override
    public V3D_Point[] getPointsArray(int oom, RoundingMode rm) {
        return points.values().toArray(new V3D_Point[points.size()]);
        //return points.values().toArray(V3D_Point[]::new);
    }

    @Override
    public HashMap<Integer, V3D_Point> getPoints(int oom, RoundingMode rm) {
        return points;
    }

    @Override
    public V3D_AABB getAABB(int oom, RoundingMode rm) {
        if (en == null) {
            en = ch.getAABB(oom, rm);
        }
        return en;
    }

    /**
     * Identify if this is intersected by pt.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    @Override
    public boolean intersects(V3D_Point pt, int oom, RoundingMode rm) {
        if (getAABB(oom, rm).intersects(pt, oom, rm)) {
            return intersects0(pt, oom, rm);
        } else {
            return false;
        }
    }

    /**
     * Identify if this is intersected by pt. No AABB check.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean intersects0(V3D_Point pt, int oom, RoundingMode rm) {
        if (pl.intersects(pt, oom, rm)) {
            return intersects00(pt, oom, rm);
        } else {
            return false;
        }
    }

    /**
     * Identify if this is intersected by pt. No AABB or Plane check.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean intersects00(V3D_Point pt, int oom, RoundingMode rm) {
        return ch.intersects00(pt, oom, rm)
                && intersects000(pt, oom, rm);
    }

    /**
     * Identify if this is intersected by pt. It is assumed {@code pt}
     * intersects with the convex hull.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean intersects000(V3D_Point pt, int oom, RoundingMode rm) {
        return !V3D_LineSegment.intersects(oom, rm, pt, ch.edges.values())
                && !externalHoles.values().parallelStream().anyMatch(x
                        -> x.contains(pt, oom, rm));
    }

    /**
     * Identify if this contains pt.
     *
     * @param pt The point to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    @Override
    public boolean contains(V3D_Point pt, int oom, RoundingMode rm) {
        return intersects(pt, oom, rm)
                && !V3D_LineSegment.intersects(oom, rm, pt, edges.values());
    }

    /**
     * Identify if this contains ls.
     *
     * @param ls The line segment to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    @Override
    public boolean contains(V3D_LineSegment ls, int oom, RoundingMode rm) {
        return contains(ls.getP(), oom, rm)
                && contains(ls.getQ(oom, rm), oom, rm)
                && !V3D_LineSegment.intersects(oom, rm, ls, edges.values());
    }

    /**
     * Identify if this contains t.
     *
     * @param t The triangle to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is containment.
     */
    @Override
    public boolean contains(V3D_Triangle t, int oom, RoundingMode rm) {
        return contains(t.getPQ(oom, rm), oom, rm)
                && contains(t.getQR(oom, rm), oom, rm)
                && contains(t.getRP(oom, rm), oom, rm);
    }

    /**
     * Identify if this contains r.
     *
     * @param r The rectangle to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V3D_Rectangle r, int oom, RoundingMode rm) {
        return contains(r.getPQR(), oom, rm) && contains(r.getRSP(), oom, rm);
    }

    /**
     * Identify if this contains ch.
     *
     * @param ch The convex hull to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V3D_ConvexArea ch, int oom, RoundingMode rm) {
        return this.ch.getEdges(oom, rm).values().parallelStream().allMatch(x
                ->  !V3D_LineSegment.intersects(
                        oom, rm, x, ch.getEdges(oom, rm).values()))
                && this.ch.getPoints(oom, rm).values().parallelStream()
                        .anyMatch(x -> contains(x, oom, rm));
    }

    /**
     * Identify if this is intersected by l.
     * {@code return ch.intersects(l, oom, rm);}
     *
     * @param l The line segment to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    @Override
    public boolean intersects(V3D_Line l, int oom, RoundingMode rm) {
        return ch.intersects(l, oom, rm);
    }

    /**
     * Identify if this is intersected by l. This first checks for an 
     * intersection with {@link #ch}.
     *
     * @param l The line segment to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    @Override
    public boolean intersects(V3D_LineSegment l, int oom, RoundingMode rm) {
        return ch.intersects(l, oom, rm)
                && intersects0(l, oom, rm);
    }

    /**
     * Identify if this is intersected by l. This does not first check for an 
     * intersection with {@link #ch}.
     *
     * @param l The line segment to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean intersects0(V3D_LineSegment l, int oom, RoundingMode rm) {
        return V3D_LineSegment.intersects(oom, rm, l, edges.values())
                || !externalHoles.values().parallelStream().anyMatch(x
                        -> x.contains(l, oom, rm));
    }

    /**
     * Identify if this is intersected by r.
     * 
     * @param r The ray to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersects(V3D_Ray r, int oom, RoundingMode rm) {
        return ch.intersects(r, oom, rm)
            && intersects0(r, oom, rm);
    }
    
    /**
     * Identify if this is intersected by r. This does not first check for an 
     * intersection with {@link #ch}.
     * 
     * @param r The ray to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if l intersects this.
     */
    public boolean intersects0(V3D_Ray r, int oom, RoundingMode rm) {
        return !externalHoles.values().parallelStream().anyMatch(x
                    -> x.intersects(r, oom, rm));
    }

    /**
     * Identify if this is intersected by t.
     *
     * @param t The triangle to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean intersects(V3D_Triangle t, int oom, RoundingMode rm) {
        return ch.intersects(t, oom, rm)
                && intersects0(t, oom, rm);
    }

    /**
     * Identify if this is intersected by t.
     *
     * @param t The triangle to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean intersects0(V3D_Triangle t, int oom, RoundingMode rm) {
        return (intersects0(t.getPQ(oom, rm), oom, rm)
                || intersects0(t.getQR(oom, rm), oom, rm)
                || intersects0(t.getRP(oom, rm), oom, rm));
    }

    /**
     * Identify if this is intersected by point {@code pv}.
     *
     * @param r The convex hull to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff the geometry is intersected by {@code ch.
     */
    public boolean intersects(V3D_Rectangle r, int oom, RoundingMode rm) {
        return ch.intersects(r, oom, rm)
                && (intersects0(r.getPQR(), oom, rm)
                || intersects0(r.getRSP(), oom, rm));
    }

    /**
     * Identify if this is intersected by {@code ch}.
     *
     * @param ch The convex hull to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff this is intersected by {@code ch}.
     */
    public boolean intersects(V3D_ConvexArea ch, int oom, RoundingMode rm) {
        return this.ch.intersects(ch, oom, rm)
                && /**
                 * If any of the edges intersect or if one geometry contains the
                 * other, there is an intersection.
                 */
                edges.values().parallelStream().anyMatch(x
                        -> V3D_LineSegment.intersects(oom, rm, x,
                        ch.getEdges(oom, rm).values()))
                || ch.getPoints(oom, rm).values().parallelStream().anyMatch(x
                        -> intersects(x, oom, rm))
                || getPoints(oom, rm).values().parallelStream().anyMatch(x
                        -> ch.intersects(x, oom, rm));
    }

    /**
     * Identify if this is intersected by the polygon.
     *
     * @param p The convex hull to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff this is intersected by {@code pv}.
     */
    public boolean intersects(V3D_PolygonNoInternalHoles p, int oom,
            RoundingMode rm) {
        return p.ch.intersects(p.ch, oom, rm)
                && /**
                 * If any of the edges intersect or if one polygon contains the
                 * other, there is an intersection.
                 */
                edges.values().parallelStream().anyMatch(x
                        -> V3D_LineSegment.intersects(oom, rm, x,
                        p.edges.values()))
                || getPoints(oom, rm).values().parallelStream().anyMatch(x
                        -> p.intersects(x, oom, rm))
                || p.getPoints(oom, rm).values().parallelStream().anyMatch(x
                        -> intersects(x, oom, rm));
    }

    /**
     * This sums all the areas irrespective of any overlaps.
     *
     * @return The area of the triangle (rounded).
     */
    //@Override
    public double getArea() {
        throw new UnsupportedOperationException();
//        BigDecimal sum = BigDecimal.ZERO;
//        for (var t : triangles) {
//            sum = sum.add(t.getArea(oom));
//        }
//        return sum;
    }

    /**
     * This sums all the perimeters irrespective of any overlaps.
     *
     * @return The total length of the external edges.
     */
    //@Override
    public double getPerimeter() {
        throw new UnsupportedOperationException();
//        BigDecimal sum = BigDecimal.ZERO;
//        for (var t : triangles) {
//            sum = sum.add(t.getPerimeter(oom));
//        }
//        return sum;
    }

    @Override
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        if (en != null) {
            en.translate(v, oom, rm);
        }
        ch.translate(v, oom, rm);
        if (edges != null) {
            for (int i = 0; i < edges.size(); i++) {
                edges.get(i).translate(v, oom, rm);
            }
        }
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                externalHoles.get(i).translate(v, oom, rm);
            }
        }
    }

    //@Override
    public V3D_PolygonNoInternalHoles rotate(V3D_Ray ray, V3D_Vector uv,
            Math_BigDecimal bd, BigRational theta, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V3D_PolygonNoInternalHoles(this);
        } else {
            return rotateN(ray, uv, bd, theta, oom, rm);
        }
    }

    //@Override
    public V3D_PolygonNoInternalHoles rotateN(V3D_Ray ray, V3D_Vector uv,
            Math_BigDecimal bd, BigRational theta, int oom, RoundingMode rm) {
        V3D_ConvexArea rch = getConvexHull(oom, rm).rotate(ray, uv, bd, theta, oom, rm);
        HashMap<Integer, V3D_Point> rPoints = new HashMap<>();
        for (var x : this.points.entrySet()) {
            rPoints.put(x.getKey(), x.getValue().rotateN(ray, uv, bd, theta, oom, rm));
        }
        HashMap<Integer, V3D_LineSegment> rEdges = new HashMap<>();
        if (edges != null) {
            for (int i = 0; i < edges.size(); i++) {
                rEdges.put(rEdges.size(), edges.get(i).rotate(
                        ray, uv, bd, theta, oom, rm));
            }
        }
        HashMap<Integer, V3D_PolygonNoInternalHoles> rExternalHoles
                = new HashMap<>();
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                rExternalHoles.put(rExternalHoles.size(),
                        externalHoles.get(i).rotate(ray, uv, bd, theta, oom, rm));
            }
        }
        return new V3D_PolygonNoInternalHoles(rPoints, rch, rEdges,
                rExternalHoles);
    }

//    @Override
//    public boolean intersects(V3D_AABB aabb, int oom, RoundingMode rm) {
//        if (getAABB(oom, rm).intersects(aabb, oom)) {
//            if (ch.intersects(aabb, oom, rm)) {
//                return true;
//            }
//        }
//        return false;
//    }
    /**
     * Adds an external hole and return its assigned id.
     *
     * @param p
     * @return the id assigned to the external hole
     */
    public int addExternalHole(V3D_PolygonNoInternalHoles p) {
        int pid = externalHoles.size();
        externalHoles.put(pid, p);
        return pid;
    }

    /**
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A point or line segment.
     */
    public V3D_FiniteGeometry getIntersect(V3D_Line l, int oom,
            RoundingMode rm) {
        V3D_Geometry i = ch.getPl(oom, rm).getIntersect(l, oom, rm);
        if (i == null) {
            return null;
        } else if (i instanceof V3D_Point ip) {
            if (intersects00(ip, oom, rm)) {
                return ip;
            } else {
                return null;
            }
        } else {
            throw new UnsupportedOperationException();
//            /**
//             * Get the intersection of the line and each edge of the triangle.
//             */
//            V3D_FiniteGeometry lpqi = getPQ(oom, rm).getIntersect(l, oom, rm);
//            V3D_FiniteGeometry lqri = getQR(oom, rm).getIntersect(l, oom, rm);
//            V3D_FiniteGeometry lrpi = getRP(oom, rm).getIntersect(l, oom, rm);
//            if (lpqi == null) {
//                if (lqri == null) {
//                    return null; // This should not happen!
//                } else {
//                    if (lrpi == null) {
//                        return lqri;
//                    } else {
//                        return getGeometry(env, ((V3D_Point) lqri).getVector(oom, rm),
//                                ((V3D_Point) lrpi).getVector(oom, rm), oom, rm);
//                    }
//                }
//            } else if (lpqi instanceof V3D_Point lpqip) {
//                if (lqri == null) {
//                    if (lrpi == null) {
//                        return lpqi;
//                    } else {
//                        return getGeometry(env, lpqip.getVector(oom, rm),
//                                ((V3D_Point) lrpi).getVector(oom, rm), oom, rm);
//                    }
//                } else if (lqri instanceof V3D_Point lqrip) {
//                    if (lrpi == null) {
//                        return getGeometry(env, lqrip.getVector(oom, rm),
//                                lpqip.getVector(oom, rm), oom, rm);
//                    } else if (lrpi instanceof V3D_LineSegment) {
//                        return lrpi;
//                    } else {
//                        return getGeometry(lpqip, lqrip, (V3D_Point) lrpi, oom, rm);
//                    }
//                } else {
//                    return lqri;
//                }
//            } else {
//                return lpqi;
//            }
        }
    }

    /**
     * Get the intersection between the geometry and the ray {@code rv}.
     *
     * @param r The ray to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    @Override
    public V3D_FiniteGeometry getIntersect(V3D_Ray r, int oom,
            RoundingMode rm) {
        V3D_FiniteGeometry g = getIntersect(r.l, oom, rm);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_Point gp) {
                if (r.isAligned(gp, oom, rm)) {
//                BigRational[] coeffs = this.pl.equation.coeffs;
//                V3D_Point pt = new V3D_Point(
//                        coeffs[0].multiply(gp.getX(oom, rm)),
//                        coeffs[1].multiply(gp.getY(oom, rm)),
//                        coeffs[2].multiply(gp.getZ(oom, rm)));
//                return pt;
                    return gp;
                } else {
                    return null;
                }
            } else {
                V3D_LineSegment ls = (V3D_LineSegment) g;
                V3D_Point lsp = ls.getP();
                V3D_Point lsq = ls.getQ(oom, rm);
                if (r.isAligned(lsp, oom, rm)) {
                    if (r.isAligned(lsq, oom, rm)) {
                        return ls;
                    } else {
                        return V3D_LineSegment.getGeometry(r.l.getP(), lsp, oom, rm);
                    }
                } else {
                    if (r.isAligned(lsq, oom, rm)) {
                        return V3D_LineSegment.getGeometry(r.l.getP(), lsq, oom, rm);
                    } else {
                        throw new RuntimeException();
                    }
                }
            }
        }
    }
}
