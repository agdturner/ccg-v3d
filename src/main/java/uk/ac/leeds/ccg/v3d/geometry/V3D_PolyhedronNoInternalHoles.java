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
import java.util.Collection;
import java.util.HashMap;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;

/**
 * Defined by a V3D_ConvexVolume and a collection of non edge sharing 
 * V3D_PolygonNoInternalHoles areas each defining an external hole or concavity.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V3D_PolyhedronNoInternalHoles extends V3D_Volume {

    private static final long serialVersionUID = 1L;

    /**
     * The convex hull.
     */
    public V3D_ConvexVolume ch;

    /**
     * The collection of externalHoles comprised of points in {@link points}.
     * Only two points of an external hole should intersect the faces of the
     * convex hull.
     */
    public HashMap<Integer, V3D_PolyhedronNoInternalHoles> externalHoles;

    /**
     * Create a new instance that is a shallow copy of the polygon.
     *
     * @param p The polygon to copy.
     */
    public V3D_PolyhedronNoInternalHoles(V3D_PolyhedronNoInternalHoles p) {
        this(p.points, p.ch, p.faces, p.externalHoles);
    }
    
    /**
     * Create a new instance that is also a convex hull.
     *
     * @param ch This convex hull with the points from which this is created and
     * what {@link #ch} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_PolyhedronNoInternalHoles(V3D_ConvexVolume ch, int oom,
            RoundingMode rm) {
        this(ch.getPointsArray(oom, rm), ch, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param points The external edge points in clockwise order.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_PolyhedronNoInternalHoles(V3D_Point[] points, int oom,
            RoundingMode rm) {
        this(points, new V3D_ConvexVolume(oom, rm, points), oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param points The external edge points in clockwise order.
     * @param ch What {@link #ch} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_PolyhedronNoInternalHoles(V3D_Point[] points, V3D_ConvexVolume ch,
            int oom, RoundingMode rm) {
        super(points[0].env, V3D_Vector.ZERO);
        this.points = new HashMap<>();
        this.ch = ch;
        faces = new HashMap<>();
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
            p.p0int = V3D_Area.intersects(oom, rm, p.p0,
                ch.faces.values());
            if (p.p0int) {
                foundStart = true;
            }
            i0 ++;
        }
        int i1 = i0;
        p.p1 = points[i1];
        i1++;
        if (p.p0.equals(p.p1, oom, rm)) {
            p.p1int = p.p0int;
        } else {
            this.points.put(this.points.size(), p.p0);
//            faces.put(faces.size(), new V3D_LineSegment(p.p0, p.p1, oom, rm));
            p.p1int = V3D_Area.intersects(oom, rm, p.p1,
                    ch.faces.values());
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
            p.p1int = V3D_Area.intersects(oom, rm, p.p1,
                    ch.faces.values());
            if (p.isHole) {
                if (p.p1int) {
                    p.pts.add(p.p0);
                    p.pts.add(p.p1);
                    externalHoles.put(externalHoles.size(),
                            new V3D_PolyhedronNoInternalHoles(
                                    p.pts.toArray(V3D_Point[]::new), oom, rm));
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
//            faces.put(faces.size(), new V3D_LineSegment(p.p0, p.p1, oom, rm));
        }
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
     * @param faces What {@link #faces} is set to.
     * @param externalHoles What {@link #externalHoles} is set to.
     */
    public V3D_PolyhedronNoInternalHoles(HashMap<Integer, V3D_Point> points,
            V3D_ConvexVolume ch, HashMap<Integer, V3D_Area> faces,
            HashMap<Integer, V3D_PolyhedronNoInternalHoles> externalHoles) {
        super(ch.env, V3D_Vector.ZERO);
        this.points = points;
        this.ch = ch;
        this.faces = faces;
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
            s.append("\nfaces (\n");
            if (faces != null) {
                for (var entry : faces.entrySet()) {
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A copy of {@link exterior#ch} with the given tolerance applied.
     */
    public V3D_ConvexVolume getConvexHull(int oom, RoundingMode rm) {
        return new V3D_ConvexVolume(ch, oom, rm);
    }

    @Override
    public HashMap<Integer, V3D_Area> getFaces(int oom, RoundingMode rm) {
        return faces;
    }

//    /**
//     * @return Deep copy of {@link faces}.
//     */
//    public HashMap<Integer, V3D_LineSegment> getEdges() {
//        HashMap<Integer, V3D_LineSegment> r = new HashMap<>();
//        for (V3D_LineSegment l : faces.values()) {
//            r.put(r.size(), new V3D_LineSegment(l));
//        }
//        return r;
//    }
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A copy of {@link externalHoles} with the given tolerance applied.
     */
    public HashMap<Integer, V3D_PolyhedronNoInternalHoles> getExternalHoles(
            int oom, RoundingMode rm) {
        HashMap<Integer, V3D_PolyhedronNoInternalHoles> r = new HashMap<>();
        for (V3D_PolyhedronNoInternalHoles h : externalHoles.values()) {
            r.put(r.size(), new V3D_PolyhedronNoInternalHoles(h));
        }
        return r;
    }

    /**
     * @return {@link externalHoles}.
     */
    public HashMap<Integer, V3D_PolyhedronNoInternalHoles> getExternalHoles() {
        return externalHoles;
    }

    @Override
    public V3D_Point[] getPointsArray(int oom, RoundingMode rm) {
        Collection<V3D_Point> pts = points.values();
        return pts.toArray(V3D_Point[]::new);
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
    public boolean intersects(V3D_Point pt, int oom, RoundingMode rm) {
        return ch.intersects(pt, oom, rm)
                && (!V3D_Area.intersects(oom, rm, pt, ch.faces.values())
                && !externalHoles.values().parallelStream().anyMatch(x
                        -> x.contains(pt, oom, rm)));
    }

    /**
     * Identify if this contains pt.
     *
     * @param pt The point to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean contains(V3D_Point pt, int oom, RoundingMode rm) {
        return intersects(pt, oom, rm)
                && !V3D_Area.intersects(oom, rm, pt, faces.values());
    }

    /**
     * Identify if this contains ls.
     *
     * @param ls The line segment to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean contains(V3D_LineSegment ls, int oom, RoundingMode rm) {
        return contains(ls.getP(), oom, rm)
                && contains(ls.getQ(oom, rm), oom, rm);
    }

    /**
     * Identify if this contains t.
     *
     * @param t The triangle to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V3D_Triangle t, int oom, RoundingMode rm) {
        return contains(t.getP(oom, rm), oom, rm)
                && contains(t.getQ(oom, rm), oom, rm)
                && contains(t.getR(oom, rm), oom, rm);
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
        return contains(r.getP(oom, rm), oom, rm)
                && contains(r.getQ(oom, rm), oom, rm)
                && contains(r.getR(oom, rm), oom, rm)
                && contains(r.getS(oom, rm), oom, rm);
    }

    /**
     * Identify if this contains aabb.
     *
     * @param aabb The envelope to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V3D_AABB aabb, int oom, RoundingMode rm) {
        BigRational xmin = aabb.getXMin(oom);
        BigRational xmax = aabb.getXMax(oom);
        BigRational ymin = aabb.getYMin(oom);
        BigRational ymax = aabb.getYMax(oom);
        BigRational zmin = aabb.getZMin(oom);
        BigRational zmax = aabb.getZMax(oom);
        return contains(new V3D_Point(env, xmin, ymin, zmin), oom, rm)
                && contains(new V3D_Point(env, xmin, ymin, zmax), oom, rm)
                && contains(new V3D_Point(env, xmin, ymax, zmin), oom, rm)
                && contains(new V3D_Point(env, xmin, ymax, zmax), oom, rm)
                && contains(new V3D_Point(env, xmax, ymax, zmin), oom, rm)
                && contains(new V3D_Point(env, xmax, ymax, zmax), oom, rm)
                && contains(new V3D_Point(env, xmax, ymin, zmin), oom, rm)
                && contains(new V3D_Point(env, xmax, ymin, zmax), oom, rm);
    }

    /**
     * Identify if this contains ch.
     *
     * @param ch The convex hull to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is containment.
     */
    public boolean contains(V3D_ConvexVolume ch, int oom, RoundingMode rm) {
        return this.ch.intersects(ch, oom, rm)
                && ch.getPoints(oom, rm).values().parallelStream().allMatch(x
                        -> contains(x, oom, rm));
    }

    /**
     * Identify if this contains the polygon.
     *
     * @param p The polygon to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} contains {@code p}.
     */
    public boolean contains(V3D_PolyhedronNoInternalHoles p, int oom, RoundingMode rm) {
        return ch.intersects(p.ch, oom, rm)
                && p.getPoints(oom, rm).values().parallelStream().allMatch(x
                        -> contains(x, oom, rm));
    }

    /**
     * Identify if this is intersected by l.
     *
     * @param l The line segment to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff there is an intersection.
     */
    public boolean intersects(V3D_LineSegment l, int oom, RoundingMode rm) {
        return ch.intersects(l, oom, rm)
                && (V3D_Area.intersects(oom, rm, l,
                        faces.values())
                || !externalHoles.values().parallelStream().anyMatch(x
                        -> x.contains(l, oom, rm)));
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
        V3D_Point tp = t.getP(oom, rm);
        V3D_Point tq = t.getQ(oom, rm);
        V3D_Point tr = t.getR(oom, rm);
        return (intersects(tp, oom, rm)
                || intersects(tq, oom, rm)
                || intersects(tr, oom, rm))
                || (t.getEdges(oom, rm).values().parallelStream().anyMatch(x
                        -> V3D_Area.intersects(oom, rm, x,
                        faces.values())))
                && !(externalHoles.values().parallelStream().anyMatch(x
                        -> x.contains(tp, oom, rm)
                && x.contains(tq, oom, rm)
                && x.contains(tr, oom, rm)));
    }
    
    public boolean intersects(V3D_Area ch, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException();
    }
    
    public boolean intersects(V3D_ConvexArea ch, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Identify if this is intersected by point {@code p}.
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
    public boolean intersects(V3D_ConvexVolume ch, int oom, RoundingMode rm) {
        return this.ch.intersects(ch, oom, rm)
                && /**
                 * If any of the faces intersect or if one geometry contains the
                 * other, there is an intersection.
                 */
                faces.values().parallelStream().anyMatch(x
                        -> V3D_Area.intersects(oom, rm, x,
                        ch.getFaces(oom, rm).values()))
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
     * @return {@code true} iff this is intersected by {@code p}.
     */
    public boolean intersects(V3D_PolyhedronNoInternalHoles p, int oom,
            RoundingMode rm) {
        return p.ch.intersects(p.ch, oom, rm)
                && /**
                 * If any of the faces intersect or if one polygon contains the
                 * other, there is an intersection.
                 */
                faces.values().parallelStream().anyMatch(x
                        -> V3D_Area.intersects(oom, rm, x, p.faces.values()))
                || getPoints(oom, rm).values().parallelStream().anyMatch(x
                        -> p.intersects(x, oom, rm))
                || p.getPoints(oom, rm).values().parallelStream().anyMatch(x
                        -> intersects(x, oom, rm));
    }

    @Override
    public BigRational getVolume(int oom, RoundingMode rm) {
        throw new UnsupportedOperationException();
    }
    @Override
    public BigRational getArea(int oom, RoundingMode rm) {
        throw new UnsupportedOperationException();
    }

    /**
     * This sums all the perimeters irrespective of any overlaps.
     *
     * @return The total length of the external faces.
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
        if (faces != null) {
            for (int i = 0; i < faces.size(); i++) {
                faces.get(i).translate(v, oom, rm);
            }
        }
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                externalHoles.get(i).translate(v, oom, rm);
            }
        }
    }
    
    @Override
    public V3D_PolyhedronNoInternalHoles rotate(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd,
            BigRational theta, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V3D_PolyhedronNoInternalHoles(this);
        } else {
            return rotateN(ray, uv, bd, theta, oom, rm);
        }
    }

    @Override
    public V3D_PolyhedronNoInternalHoles rotateN(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd,
            BigRational theta, int oom, RoundingMode rm) {
        V3D_ConvexVolume rch = getConvexHull(oom, rm).rotate(ray, uv, bd, theta, oom, rm);
        HashMap<Integer, V3D_Point> rPoints = new HashMap<>();
        for (var x : this.points.entrySet()) {
            rPoints.put(x.getKey(), x.getValue().rotateN(ray, uv, bd, theta, oom, rm));
        }
        HashMap<Integer, V3D_Area> rFaces = new HashMap<>();
        if (faces != null) {
            for (int i = 0; i < faces.size(); i++) {
                rFaces.put(rFaces.size(), (V3D_Area) faces.get(i).rotateN(
                        ray, uv, bd, theta, oom, rm));
            }
        }
        HashMap<Integer, V3D_PolyhedronNoInternalHoles> rExternalHoles
                = new HashMap<>();
        if (externalHoles != null) {
            for (int i = 0; i < externalHoles.size(); i++) {
                rExternalHoles.put(rExternalHoles.size(),
                        externalHoles.get(i).rotate(ray, uv, bd, theta, oom, rm));
            }
        }
        return new V3D_PolyhedronNoInternalHoles(rPoints, rch, rFaces,
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
    public int addExternalHole(V3D_PolyhedronNoInternalHoles p) {
        int pid = externalHoles.size();
        externalHoles.put(pid, p);
        return pid;
    }
}
