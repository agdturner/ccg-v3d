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
package uk.ac.leeds.ccg.v3d.geometry.d;

import java.util.ArrayList;
import java.util.HashMap;
import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;

/**
 * Defined by a V3D_ConvexArea_d and a collection of non edge sharing
 * V3D_PolygonNoInternalHoles_d areas each defining an external hole or
 * concavity.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_PolygonNoInternalHoles_d extends V3D_Area_d {

    private static final long serialVersionUID = 1L;

    /**
     * The convex hull.
     */
    public V3D_ConvexArea_d ch;

    /**
     * The collection of externalHoles comprised of points in {@link points}.
     * Only two points of an external hole should intersect the edges of the
     * convex hull.
     */
    public HashMap<Integer, V3D_PolygonNoInternalHoles_d> externalHoles;

    /**
     * Create a new instance that is a shallow copy of the polygon.
     *
     * @param p The polygon to copy.
     */
    public V3D_PolygonNoInternalHoles_d(V3D_PolygonNoInternalHoles_d p) {
        this(p.points, p.ch, p.edges, p.externalHoles);
    }

//    /**
//     * Create a new instance that is a deep copy of the polygon.
//     *
//     * @param p The polygon to copy.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode for any rounding.
//     */
//    public V3D_PolygonNoInternalHoles(V3D_PolygonNoInternalHoles p, int oom,
//            RoundingMode rm) {
//        this(p.getPoints(epsilon).entrySet().stream().collect(Collectors.toMap(
//                Map.Entry::getKey, x -> new V3D_Point_d (x.getValue()))),
//                p.getConvexHull(epsilon), p.getEdges(epsilon), 
//                p.getExternalHoles());
//    }
    /**
     * Create a new instance that is also a convex hull.
     *
     * @param ch This convex hull with the points from which this is created and
     * what {@link #ch} is set to.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V3D_PolygonNoInternalHoles_d(V3D_ConvexArea_d ch, double epsilon) {
        this(ch.getPointsArray(), ch, epsilon);
    }

    /**
     * Create a new instance.
     *
     * @param points The external edge points in clockwise order.
     * @param n The normal to the plane.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V3D_PolygonNoInternalHoles_d(V3D_Point_d[] points, V3D_Vector_d n,
            double epsilon) {
        this(points, new V3D_ConvexArea_d(epsilon, n, points), epsilon);
    }

    /**
     * Create a new instance.
     *
     * @param points The external edge points in clockwise order.
     * @param ch What {@link #ch} is set to.
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     */
    public V3D_PolygonNoInternalHoles_d(V3D_Point_d[] points,
            V3D_ConvexArea_d ch, double epsilon) {
        super(points[0].env, V3D_Vector_d.ZERO, ch.pl);
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
            p.p0int = V3D_LineSegment_d.intersects(epsilon, p.p0,
                    ch.edges.values());
            if (p.p0int) {
                foundStart = true;
            }
            i0++;
        }
        int i1 = i0;
        p.p1 = points[i1];
        i1++;
        if (p.p0.equals(epsilon, p.p1)) {
            p.p1int = p.p0int;
        } else {
            this.points.put(this.points.size(), p.p0);
            edges.put(edges.size(), new V3D_LineSegment_d(p.p0, p.p1));
            p.p1int = V3D_LineSegment_d.intersects(epsilon, p.p1,
                    ch.edges.values());
            if (p.p0int) {
                if (!p.p1int) {
                    p.pts.add(p.p0);
                    p.isHole = true;
                }
            }
        }
        for (int i = i1; i < points.length; i++) {
            doThing(epsilon, i, p);
        }
        for (int i = 0; i < i0; i++) {
            doThing(epsilon, i, p);
        }
    }

    private void doThing(double epsilon, int index, PTThing p) {
        p.p0 = p.p1;
        p.p0int = p.p1int;
        p.p1 = p.points[index];
        if (p.p0.equals(epsilon, p.p1)) {
            p.p1int = p.p0int;
        } else {
            p.p1int = V3D_LineSegment_d.intersects(epsilon, p.p1,
                    ch.edges.values());
            if (p.isHole) {
                if (p.p1int) {
                    p.pts.add(p.p0);
                    p.pts.add(p.p1);
                    externalHoles.put(externalHoles.size(),
                            new V3D_PolygonNoInternalHoles_d(
                                    p.pts.toArray(V3D_Point_d[]::new),
                                    pl.n, epsilon));
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
            edges.put(edges.size(), new V3D_LineSegment_d(p.p0, p.p1));
        }
    }

    @Override
    public double getPerimeter() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getArea() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean intersects(V3D_AABB_d aabb, double epsilon) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    protected class PTThing {

        boolean isHole;
        boolean p0int;
        boolean p1int;
        ArrayList<V3D_Point_d> pts;
        V3D_Point_d[] points;
        V3D_Point_d p0;
        V3D_Point_d p1;

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
    public V3D_PolygonNoInternalHoles_d(HashMap<Integer, V3D_Point_d> points,
            V3D_ConvexArea_d ch, HashMap<Integer, V3D_LineSegment_d> edges,
            HashMap<Integer, V3D_PolygonNoInternalHoles_d> externalHoles) {
        super(ch.env, V3D_Vector_d.ZERO, ch.pl);
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

    /**
     * @return {@link edges}.
     */
    @Override
    public HashMap<Integer, V3D_LineSegment_d> getEdges() {
        return edges;
    }

//    /**
//     * @return Deep copy of {@link edges}.
//     */
//    public HashMap<Integer, V3D_LineSegment_d> getEdges() {
//        HashMap<Integer, V3D_LineSegment_d> r = new HashMap<>();
//        for (V3D_LineSegment_d l : edges.values()) {
//            r.put(r.size(), new V3D_LineSegment_d(l));
//        }
//        return r;
//    }
    /**
     * @return {@link externalHoles}.
     */
    public HashMap<Integer, V3D_PolygonNoInternalHoles_d> getExternalHoles() {
        return externalHoles;
    }

    @Override
    public V3D_Point_d[] getPointsArray() {
        return points.values().toArray(new V3D_Point_d[points.size()]);
        //return points.values().toArray(V3D_Point_d []::new);
    }

    @Override
    public HashMap<Integer, V3D_Point_d> getPoints() {
        return points;
    }

    @Override
    public V3D_AABB_d getAABB() {
        if (en == null) {
            en = ch.getAABB();
        }
        return en;
    }

    /**
     * Identify if this is intersected by pt.
     *
     * @param pt The point to test for intersection with.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff there is an intersection.
     */
    @Override
    public boolean intersects(V3D_Point_d pt, double epsilon) {
        if (getAABB().intersects(pt)) {
            return intersects0(pt, epsilon);
        } else {
            return false;
        }
    }

    /**
     * Identify if this is intersected by pt. No AABB check.
     *
     * @param pt The point to test for intersection with.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff there is an intersection.
     */
    public boolean intersects0(V3D_Point_d pt, double epsilon) {
        if (pl.intersects(pt)) {
            return intersects00(pt, epsilon);
        } else {
            return false;
        }
    }

    /**
     * Identify if this is intersected by pt.
     *
     * @param pt The point to test for intersection with.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff there is an intersection.
     */
    public boolean intersects00(V3D_Point_d pt, double epsilon) {
        return ch.intersects00(pt, epsilon)
                && intersects000(pt, epsilon);
    }

    /**
     * Identify if this is intersected by pt. It is assumed {@code pt}
     * intersects with the convex hull.
     *
     * @param pt The point to test for intersection with.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff there is an intersection.
     */
    public boolean intersects000(V3D_Point_d pt, double epsilon) {
        return !V3D_LineSegment_d.intersects(epsilon, pt, ch.edges.values())
                && !externalHoles.values().parallelStream().anyMatch(x
                        -> x.contains(pt, epsilon));
    }

    /**
     * Identify if this contains pt.
     *
     * @param pt The point to test for containment.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff there is an intersection.
     */
    @Override
    public boolean contains(V3D_Point_d pt, double epsilon) {
        return intersects(pt, epsilon)
                && !V3D_LineSegment_d.intersects(epsilon, pt, edges.values());
    }

    /**
     * Identify if this contains ls.
     *
     * @param ls The line segment to test for containment.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff there is an intersection.
     */
    @Override
    public boolean contains(V3D_LineSegment_d ls, double epsilon) {
        return contains(ls.getP(), epsilon)
                && contains(ls.getQ(), epsilon);
    }

    /**
     * Identify if this contains t.
     *
     * @param t The triangle to test for containment.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V3D_Triangle_d t, double epsilon) {
        return contains(t.getP(), epsilon)
                && contains(t.getQ(), epsilon)
                && contains(t.getR(), epsilon);
    }

    /**
     * Identify if this contains r.
     *
     * @param r The rectangle to test for containment.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V3D_Rectangle_d r, double epsilon) {
        return contains(r.getP(), epsilon)
                && contains(r.getQ(), epsilon)
                && contains(r.getR(), epsilon)
                && contains(r.getS(), epsilon);
    }

    /**
     * Identify if this contains aabb.
     *
     * @param aabb The envelope to test for containment.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V3D_AABB_d aabb, double epsilon) {
        double xmin = aabb.getXMin();
        double xmax = aabb.getXMax();
        double ymin = aabb.getYMin();
        double ymax = aabb.getYMax();
        double zmin = aabb.getZMin();
        double zmax = aabb.getZMax();
        return contains(new V3D_Point_d(env, xmin, ymin, zmin), epsilon)
                && contains(new V3D_Point_d(env, xmin, ymin, zmax), epsilon)
                && contains(new V3D_Point_d(env, xmin, ymax, zmin), epsilon)
                && contains(new V3D_Point_d(env, xmin, ymax, zmax), epsilon)
                && contains(new V3D_Point_d(env, xmax, ymax, zmin), epsilon)
                && contains(new V3D_Point_d(env, xmax, ymin, zmax), epsilon);
    }

    /**
     * Identify if this contains ch.
     *
     * @param ch The convex hull to test for containment.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V3D_ConvexArea_d ch, double epsilon) {
        return this.ch.intersects(ch, epsilon)
                && ch.getPoints().values().parallelStream().allMatch(x
                        -> contains(x, epsilon));
    }

    /**
     * Identify if this contains the polygon.
     *
     * @param p The polygon to test for containment.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff {@code this} contains {@code p}.
     */
    public boolean contains(V3D_PolygonNoInternalHoles_d p, double epsilon) {
        return ch.intersects(p.ch, epsilon)
                && p.getPoints().values().parallelStream().allMatch(x
                        -> contains(x, epsilon));
    }

    /**
     * Identify if this is intersected by l.
     *
     * @param l The line segment to test for intersection with.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff there is an intersection.
     */
    @Override
    public boolean intersects(V3D_Line_d l, double epsilon) {
        throw new UnsupportedOperationException();
//        return ch.intersects(l, epsilon)
//                && (V3D_Line.intersects(epsilon, l, edges.values())
//                || !externalHoles.values().parallelStream().anyMatch(x
//                        -> x.contains(l, epsilon)));
    }

    /**
     * Identify if this is intersected by l.
     *
     * @param l The line segment to test for intersection with.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff there is an intersection.
     */
    @Override
    public boolean intersects(V3D_LineSegment_d l, double epsilon) {
        return ch.intersects(l, epsilon)
                && (V3D_LineSegment_d.intersects(epsilon, l, edges.values())
                || !externalHoles.values().parallelStream().anyMatch(x
                        -> x.contains(l, epsilon)));
    }

    /**
     * If no point aligns, then returns false, otherwise the intersection is
     * computed, so if that is needed use:
     * {@link #getIntersect(uk.ac.leeds.ccg.v3d.geometry.V3D_Ray, int, java.math.RoundingMode)}
     *
     * @param r The ray to test if it intersects.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} if l intersects this.
     */
    @Override
    public boolean intersects(V3D_Ray_d r, double epsilon) {
        if (getPoints().values().parallelStream().anyMatch(x
                -> r.isAligned(x, epsilon))) {
            return getIntersect(r, epsilon) != null;
        } else {
            return false;
        }
    }

    /**
     * Identify if this is intersected by t.
     *
     * @param t The triangle to test for intersection with.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff there is an intersection.
     */
    public boolean intersects(V3D_Triangle_d t, double epsilon) {
        return ch.intersects(t, epsilon)
                && intersects0(t, epsilon);
    }

    /**
     * Identify if this is intersected by t.
     *
     * @param t The triangle to test for intersection with.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff there is an intersection.
     */
    public boolean intersects0(V3D_Triangle_d t, double epsilon) {
        V3D_Point_d tp = t.getP();
        V3D_Point_d tq = t.getQ();
        V3D_Point_d tr = t.getR();
        return (intersects(tp, epsilon)
                || intersects(tq, epsilon)
                || intersects(tr, epsilon))
                || (t.getEdges().values().parallelStream().anyMatch(x
                        -> V3D_LineSegment_d.intersects(epsilon, x,
                        edges.values())))
                && !(externalHoles.values().parallelStream().anyMatch(x
                        -> x.contains(tp, epsilon)
                && x.contains(tq, epsilon)
                && x.contains(tr, epsilon)));
    }

    /**
     * Identify if this is intersected by point {@code p}.
     *
     * @param r The convex hull to test for intersection with.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff the geometry is intersected by {@code ch.
     */
    public boolean intersects(V3D_Rectangle_d r, double epsilon) {
        return ch.intersects(r, epsilon)
                && (intersects0(r.getPQR(), epsilon)
                || intersects0(r.getRSP(), epsilon));
    }

    /**
     * Identify if this is intersected by {@code ch}.
     *
     * @param ch The convex hull to test for intersection with.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff this is intersected by {@code ch}.
     */
    public boolean intersects(V3D_ConvexArea_d ch, double epsilon) {
        return this.ch.intersects(ch, epsilon)
                && /**
                 * If any of the edges intersect or if one geometry contains the
                 * other, there is an intersection.
                 */
                edges.values().parallelStream().anyMatch(x
                        -> V3D_LineSegment_d.intersects(epsilon, x,
                        ch.getEdges().values()))
                || ch.getPoints().values().parallelStream().anyMatch(x
                        -> intersects(x, epsilon))
                || getPoints().values().parallelStream().anyMatch(x
                        -> ch.intersects(x, epsilon));
    }

    /**
     * Identify if this is intersected by the polygon.
     *
     * @param p The convex hull to test for intersection with.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff this is intersected by {@code p}.
     */
    public boolean intersects(V3D_PolygonNoInternalHoles_d p, double epsilon) {
        return p.ch.intersects(p.ch, epsilon)
                && /**
                 * If any of the edges intersect or if one polygon contains the
                 * other, there is an intersection.
                 */
                edges.values().parallelStream().anyMatch(x
                        -> V3D_LineSegment_d.intersects(epsilon, x,
                        p.edges.values()))
                || getPoints().values().parallelStream().anyMatch(x
                        -> p.intersects(x, epsilon))
                || p.getPoints().values().parallelStream().anyMatch(x
                        -> intersects(x, epsilon));
    }

    @Override
    public void translate(V3D_Vector_d v) {
        super.translate(v);
        if (en != null) {
            en.translate(v);
        }
        ch.translate(v);
        if (edges != null) {
            for (int i = 0; i < edges.size(); i++) {
                edges.get(i).translate(v);
            }
        }
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                externalHoles.get(i).translate(v);
            }
        }
    }

    @Override
    public V3D_PolygonNoInternalHoles_d rotate(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        theta = Math_AngleDouble.normalise(theta);
        if (theta == 0) {
            return new V3D_PolygonNoInternalHoles_d(this);
        } else {
            return rotateN(ray, uv, theta, epsilon);
        }
    }

    @Override
    public V3D_PolygonNoInternalHoles_d rotateN(V3D_Ray_d ray, V3D_Vector_d uv,
            double theta, double epsilon) {
        V3D_ConvexArea_d rch = ch.rotate(ray, uv, theta, epsilon);
        HashMap<Integer, V3D_Point_d> rPoints = new HashMap<>();
        for (var x : this.points.entrySet()) {
            rPoints.put(x.getKey(), x.getValue().rotateN(ray, uv, theta, epsilon));
        }
        HashMap<Integer, V3D_LineSegment_d> rEdges = new HashMap<>();
        if (edges != null) {
            for (int i = 0; i < edges.size(); i++) {
                rEdges.put(rEdges.size(), edges.get(i).rotate(
                        ray, uv, theta, epsilon));
            }
        }
        HashMap<Integer, V3D_PolygonNoInternalHoles_d> rExternalHoles
                = new HashMap<>();
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                rExternalHoles.put(rExternalHoles.size(),
                        externalHoles.get(i).rotate(ray, uv, theta, epsilon));
            }
        }
        return new V3D_PolygonNoInternalHoles_d(rPoints, rch, rEdges,
                rExternalHoles);
    }

//    @Override
//    public boolean intersects(V3D_AABB_d  aabb, double epsilon) {
//        if (getAABB(epsilon).intersects(aabb, oom)) {
//            if (ch.intersects(aabb, epsilon)) {
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
    public int addExternalHole(V3D_PolygonNoInternalHoles_d p) {
        int pid = externalHoles.size();
        externalHoles.put(pid, p);
        return pid;
    }

    /**
     * @param l The line to intersect with.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return A point or line segment.
     */
    public V3D_FiniteGeometry_d getIntersect(V3D_Line_d l, double epsilon) {
        V3D_Geometry_d i = ch.pl.getIntersect(l, epsilon);
        if (i == null) {
            return null;
        } else if (i instanceof V3D_Point_d ip) {
            if (intersects00(ip, epsilon)) {
                return ip;
            } else {
                return null;
            }
        } else {
            /**
             * Maybe better to return null? Is there a use case for knowing the
             * intersection across the surface of an area rather than treating
             * this as though there is no intersection?
             */
            throw new UnsupportedOperationException();
//            /**
//             * Get the intersection of the line and each edge of the triangle.
//             */
//            V3D_FiniteGeometry lpqi = getPQ(epsilon).getIntersect(l, epsilon);
//            V3D_FiniteGeometry lqri = getQR(epsilon).getIntersect(l, epsilon);
//            V3D_FiniteGeometry lrpi = getRP(epsilon).getIntersect(l, epsilon);
//            if (lpqi == null) {
//                if (lqri == null) {
//                    return null; // This should not happen!
//                } else {
//                    if (lrpi == null) {
//                        return lqri;
//                    } else {
//                        return getGeometry(env, ((V3D_Point) lqri).getVector(epsilon),
//                                ((V3D_Point) lrpi).getVector(epsilon), epsilon);
//                    }
//                }
//            } else if (lpqi instanceof V3D_Point lpqip) {
//                if (lqri == null) {
//                    if (lrpi == null) {
//                        return lpqi;
//                    } else {
//                        return getGeometry(env, lpqip.getVector(epsilon),
//                                ((V3D_Point) lrpi).getVector(epsilon), epsilon);
//                    }
//                } else if (lqri instanceof V3D_Point lqrip) {
//                    if (lrpi == null) {
//                        return getGeometry(env, lqrip.getVector(epsilon),
//                                lpqip.getVector(epsilon), epsilon);
//                    } else if (lrpi instanceof V3D_LineSegment) {
//                        return lrpi;
//                    } else {
//                        return getGeometry(lpqip, lqrip, (V3D_Point) lrpi, epsilon);
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
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return The V3D_Geometry.
     */
    @Override
    public V3D_FiniteGeometry_d getIntersect(V3D_Ray_d r, double epsilon) {
        V3D_FiniteGeometry_d g = getIntersect(r.l, epsilon);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_Point_d gp) {
                if (r.isAligned(gp, epsilon)) {
//                BigRational[] coeffs = this.pl.equation.coeffs;
//                V3D_Point pt = new V3D_Point(
//                        coeffs[0].multiply(gp.getX(epsilon)),
//                        coeffs[1].multiply(gp.getY(epsilon)),
//                        coeffs[2].multiply(gp.getZ(epsilon)));
//                return pt;
                    return gp;
                } else {
                    return null;
                }
            } else {
                V3D_LineSegment_d ls = (V3D_LineSegment_d) g;
                V3D_Point_d lsp = ls.getP();
                V3D_Point_d lsq = ls.getQ();
                if (r.isAligned(lsp, epsilon)) {
                    if (r.isAligned(lsq, epsilon)) {
                        return ls;
                    } else {
                        return V3D_LineSegment_d.getGeometry(r.l.getP(), lsp, epsilon);
                    }
                } else {
                    if (r.isAligned(lsq, epsilon)) {
                        return V3D_LineSegment_d.getGeometry(r.l.getP(), lsq, epsilon);
                    } else {
                        throw new RuntimeException();
                    }
                }
            }
        }
    }
}
