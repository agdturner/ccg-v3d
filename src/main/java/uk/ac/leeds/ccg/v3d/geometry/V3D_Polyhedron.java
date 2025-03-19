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
import java.util.HashMap;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;

/**
 * V2D_Polygon extends V3D_PolyhedronNoInternalHoles and is also defined by a
 * collection of non intersecting internal holes each represented also as a 
 * V2D_Polygon.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V3D_Polyhedron extends V3D_PolyhedronNoInternalHoles {

    private static final long serialVersionUID = 1L;

    /**
     * The collection of internalHoles.
     */
    public HashMap<Integer, V3D_PolyhedronNoInternalHoles> internalHoles;

    /**
     * Create a new shallow copy.
     *
     * @param p The polygon to duplicate.
     */
    public V3D_Polyhedron(V3D_Polyhedron p) {
        super(p);
        this.internalHoles = p.internalHoles;
    }

//    /**
//     * Create a new deep copy.
//     *
//     * @param p The polygon to duplicate.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode.
//     */
//    public V2D_Polygon(V2D_Polygon p, int oom, RoundingMode rm) {
//        super(p, oom, rm);
//        this.internalHoles = new HashMap<>();
//        for (var x: p.internalHoles.entrySet()) {
//            this.internalHoles.put(x.getKey(), new V3D_PolyhedronNoInternalHoles(
//                    x.getValue(), oom, rm));
//        }
//    }
    /**
     * Create a new instance.
     *
     * @param p The polygon with no internal holes to use as a basis for this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     */
    public V3D_Polyhedron(V3D_PolyhedronNoInternalHoles p, int oom, RoundingMode rm) {
        this(p, new HashMap<>(), oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param p The polygon with no internal holes to use as a basis for this.
     * @param internalHoles What {@link #internalHoles} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     */
    public V3D_Polyhedron(V3D_PolyhedronNoInternalHoles p,
            HashMap<Integer, V3D_PolyhedronNoInternalHoles> internalHoles,
            int oom, RoundingMode rm) {
        super(p);
        this.internalHoles = internalHoles;
    }

    /**
     * Create a new instance.
     *
     * @param pts The external edge points in a clockwise order.
     * @param internalHoles What {@link #internalHoles} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     */
    public V3D_Polyhedron(V3D_Point[] pts, HashMap<Integer, V3D_PolyhedronNoInternalHoles> internalHoles,
            int oom, RoundingMode rm) {
        super(pts, oom, rm);
        this.internalHoles = internalHoles;
    }

//    /**
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode for any rounding.
//     * @return A copy of {@link internalHoles} with the given tolerance applied.
//     */
//    public HashMap<Integer, V3D_PolyhedronNoInternalHoles> getInternalHoles(
//            int oom, RoundingMode rm) {
//        HashMap<Integer, V3D_PolyhedronNoInternalHoles> r = new HashMap<>();
//        for (V3D_PolyhedronNoInternalHoles h : internalHoles.values()) {
//            r.put(r.size(), new V3D_PolyhedronNoInternalHoles(h, oom, rm));
//        }
//        return r;
//    }
    /**
     * Identify if {@code this} intersects {@code pt}.
     *
     * @param pt The point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} intersects {@code pt}.
     */
    @Override
    public boolean intersects(V3D_Point pt, int oom, RoundingMode rm) {
        return super.intersects(pt, oom, rm)
                && !internalHoles.values().parallelStream().anyMatch(x
                        -> x.contains(pt, oom, rm));
    }

    /**
     * Identify if {@code this} contains {@code pt}.
     *
     * @param pt The point to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} contains {@code pt}.
     */
    @Override
    public boolean contains(V3D_Point pt, int oom, RoundingMode rm) {
        return super.contains(pt, oom, rm)
                && !internalHolesContains(pt, oom, rm);
    }

    /**
     * Identify if {@link #internalHoles} contains {@code pt}.
     *
     * @param pt The point to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@link #internalHoles} contains {@code pt}.
     */
    public boolean internalHolesContains(V3D_Point pt, int oom, RoundingMode rm) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.contains(pt, oom, rm));
    }

    /**
     * Identify if {@link #internalHoles} intersects {@code pt}.
     *
     * @param pt The point to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@link #internalHoles} intersects {@code pt}.
     */
    public boolean internalHolesIntersects(V3D_Point pt, int oom, RoundingMode rm) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.intersects(pt, oom, rm));
    }

    /**
     * Identify if {@code this} intersects {@code l}.
     *
     * @param l The line segment to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} intersects {@code l}.
     */
    @Override
    public boolean intersects(V3D_LineSegment l, int oom, RoundingMode rm) {
        return super.intersects(l, oom, rm)
                && !internalHolesContains(l, oom, rm);
    }

    /**
     * Identify if {@link #internalHoles} contains {@code l}.
     *
     * @param l The line segment to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@link #internalHoles} contains {@code l}.
     */
    public boolean internalHolesContains(V3D_LineSegment l, int oom, RoundingMode rm) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.contains(l, oom, rm));
    }

    /**
     * Identify if {@link #internalHoles} intersects {@code l}.
     *
     * @param l The line segment to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@link #internalHoles} intersects {@code l}.
     */
    public boolean internalHolesIntersects(V3D_LineSegment l, int oom, RoundingMode rm) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.intersects(l, oom, rm));
    }

    /**
     * Identify if {@code this} contains {@code l}.
     *
     * @param l The line segment to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} contains {@code l}.
     */
    @Override
    public boolean contains(V3D_LineSegment l, int oom, RoundingMode rm) {
        return super.contains(l, oom, rm)
                && !internalHolesContains(l, oom, rm);
    }

    /**
     * Identify if {@code this} intersects {@code t}.
     *
     * @param t The triangle to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} intersects {@code t}.
     */
    @Override
    public boolean intersects(V3D_Triangle t, int oom, RoundingMode rm) {
        return super.intersects(t, oom, rm)
                && !internalHolesContains(t, oom, rm);
    }

    /**
     * Identify if {@link #internalHoles} contains {@code t}.
     *
     * @param t The triangle to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@link #internalHoles} contains {@code t}.
     */
    public boolean internalHolesContains(V3D_Triangle t, int oom,
            RoundingMode rm) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.contains(t, oom, rm));
    }

    /**
     * Identify if {@link #internalHoles} intersects {@code t}.
     *
     * @param t The triangle to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@link #internalHoles} intersects {@code t}.
     */
    public boolean internalHolesIntersects(V3D_Triangle t, int oom,
            RoundingMode rm) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.intersects(t, oom, rm));
    }

    /**
     * Identify if {@code this} contains {@code t}.
     *
     * @param t The triangle to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} contains {@code t}.
     */
    @Override
    public boolean contains(V3D_Triangle t, int oom, RoundingMode rm) {
        return super.contains(t, oom, rm)
                && !internalHolesContains(t, oom, rm);
    }

    /**
     * Identify if {@code this} intersects {@code r}.
     *
     * @param r The rectangle to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} intersects {@code r}.
     */
    @Override
    public boolean intersects(V3D_Rectangle r, int oom, RoundingMode rm) {
        return super.intersects(r, oom, rm)
                && !internalHolesContains(r, oom, rm);
    }

    /**
     * Identify if {@link #internalHoles} contains {@code t}.
     *
     * @param r The rectangle to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@link #internalHoles} contains {@code t}.
     */
    public boolean internalHolesContains(V3D_Rectangle r, int oom,
            RoundingMode rm) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.contains(r, oom, rm));
    }

    /**
     * Identify if {@link #internalHoles} intersects {@code t}.
     *
     * @param r The rectangle to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@link #internalHoles} intersects {@code t}.
     */
    public boolean internalHolesIntersects(V3D_Rectangle r, int oom,
            RoundingMode rm) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.intersects(r, oom, rm));
    }

    /**
     * Identify if {@code this} contains {@code r}.
     *
     * @param r The rectangle to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} contains {@code r}.
     */
    @Override
    public boolean contains(V3D_Rectangle r, int oom, RoundingMode rm) {
        return super.contains(r, oom, rm)
                && internalHolesContains(r, oom, rm);
    }

    /**
     * Identify if {@code this} intersects {@code ch}.
     *
     * @param ch The convex hull to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} intersects {@code ch}.
     */
    @Override
    public boolean intersects(V3D_ConvexArea ch, int oom, RoundingMode rm) {
        return super.intersects(ch, oom, rm)
                && !internalHolesContains(ch, oom, rm);
    }

    /**
     * Identify if {@link #internalHoles} contains {@code ch}.
     *
     * @param ch The convex hull to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@link #internalHoles} contains {@code ch}.
     */
    public boolean internalHolesContains(V3D_ConvexArea ch, int oom,
            RoundingMode rm) {
        throw new UnsupportedOperationException();
//        return internalHoles.values().parallelStream().anyMatch(x
//                -> x.contains(ch, oom, rm));
    }

    /**
     * Identify if {@link #internalHoles} intersects {@code ch}.
     *
     * @param ch The convex hull to test for intersection.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@link #internalHoles} intersects {@code ch}.
     */
    public boolean internalHolesIntersects(V3D_ConvexArea ch, int oom,
            RoundingMode rm) {
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.intersects(ch, oom, rm));
    }

    /**
     * Identify if {@code this} contains {@code ch}.
     *
     * @param ch The convex hull to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} iff {@code this} contains {@code ch}.
     */
    //@Override
    public boolean contains(V3D_ConvexArea ch, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException();
//        return super.contains(ch, oom, rm)
//                && !internalHolesContains(ch, oom, rm);
    }

    @Override
    public BigRational getVolume(int oom, RoundingMode rm) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public BigRational getArea(int oom, RoundingMode rm) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        if (internalHoles != null) {
            for (int i = 0; i < internalHoles.size(); i++) {
                internalHoles.get(i).translate(v, oom, rm);
            }
        }
    }

    @Override
    public V3D_Polyhedron rotate(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd,
            BigRational theta, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V3D_Polyhedron(this);
        } else {
            return rotateN(ray, uv, bd, theta, oom, rm);
        }
    }

    @Override
    public V3D_Polyhedron rotateN(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd,
            BigRational theta, int oom, RoundingMode rm) {
        V3D_PolyhedronNoInternalHoles exterior = super.rotateN(ray, uv, bd, theta, oom, rm);
        HashMap<Integer, V3D_PolyhedronNoInternalHoles> rInternalHoles = new HashMap<>();
        if (internalHoles != null) {
            for (int i = 0; i < internalHoles.size(); i++) {
                rInternalHoles.put(rInternalHoles.size(), internalHoles.get(i).rotate(
                        ray, uv, bd, theta, oom, rm));
            }
        }
        return new V3D_Polyhedron(exterior, rInternalHoles, oom, rm);
    }
    
//    @Override
//    public boolean intersects(V2D_AABB aabb, int oom, RoundingMode rm) {
//        if (getEnvelope(oom, rm).intersects(aabb, oom)) {
//            if (getConvexHull(oom, rm).intersects(aabb, oom, rm)) {
//                return true;
//            }
//        }
//        return false;
//    }
    /**
     * Adds an internal hole and return its assigned id.
     *
     * @param p The polygon to add.
     * @return the id assigned to the internal hole
     */
    public int addInternalHole(V3D_Polyhedron p) {
        int pid = internalHoles.size();
        internalHoles.put(pid, p);
        return pid;
    }
}
