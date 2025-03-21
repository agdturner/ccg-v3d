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
 * V3D_Polygon extends V3D_PolygonNoInternalHoles and is also defined by a
 * collection of non intersecting internal holes each represented also as a 
 * V3D_Polygon.
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V3D_Polygon extends V3D_PolygonNoInternalHoles {

    private static final long serialVersionUID = 1L;

    /**
     * The collection of internalHoles.
     */
    public HashMap<Integer, V3D_PolygonNoInternalHoles> internalHoles;
    //public HashMap<Integer, V3D_Polygon> internalHoles;
    
    /**
     * For storing the internalHoles edges.
     */
    protected HashMap<Integer, V3D_LineSegment> internalHolesEdges;

    /**
     * Create a new shallow copy.
     *
     * @param p The polygon to duplicate.
     */
    public V3D_Polygon(V3D_Polygon p) {
        super(p);
        this.internalHoles = p.internalHoles;
        this.internalHolesEdges = p.internalHolesEdges;
    }
    
    /**
     * Create a new instance.
     *
     * @param p The polygon with no internal holes to use as a basis for this.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode.
     */
    public V3D_Polygon(V3D_PolygonNoInternalHoles p, int oom, RoundingMode rm) {
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
    public V3D_Polygon(V3D_PolygonNoInternalHoles p,
            HashMap<Integer, V3D_PolygonNoInternalHoles> internalHoles,
            //HashMap<Integer, V3D_Polygon> internalHoles,
            int oom, RoundingMode rm) {
        super(p);
        this.internalHoles = internalHoles;
    }

//    /**
//     * Create a new instance.
//     *
//     * @param pts The external edge points in a clockwise order.
//     * @param internalHoles What {@link #internalHoles} is set to.
//     * @param oom The Order of Magnitude for the precision.
//     * @param rm The RoundingMode.
//     */
//    public V3D_Polygon(V3D_Point[] pts, HashMap<Integer, V3D_PolygonNoInternalHoles> internalHoles,
//    //public V3D_Polygon(V3D_Point[] pts, HashMap<Integer, V3D_Polygon> internalHoles,
//            int oom, RoundingMode rm) {
//        super(pts, new V3D_PlaConvexArea(oom, rm, offset, pts));
//        this.internalHoles = internalHoles;
//    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A collection of the edges of all the internal holes.
     */
    public HashMap<Integer, V3D_LineSegment> getInternalHolesEdges(int oom, RoundingMode rm) {
        if (internalHolesEdges == null) {
            internalHolesEdges = new HashMap<>();
            internalHoles.values().forEach(x
                -> edges.values().forEach(y
                -> internalHolesEdges.put(internalHolesEdges.size(), y)));
        }
        return internalHolesEdges;
    }
    
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
        return internalHoles.values().parallelStream().anyMatch(x
                -> x.contains(ch, oom, rm));
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
    @Override
    public boolean contains(V3D_ConvexArea ch, int oom, RoundingMode rm) {
        return super.contains(ch, oom, rm)
                && !internalHolesContains(ch, oom, rm);
    }

    /**
     * This sums all the areas irrespective of any overlaps.
     *
     * @return The area of the triangle (rounded).
     */
    @Override
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
     */
    @Override
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
        if (internalHoles != null) {
            for (int i = 0; i < internalHoles.size(); i++) {
                internalHoles.get(i).translate(v, oom, rm);
            }
        }
    }

    @Override
    public V3D_Polygon rotate(V3D_Ray ray, V3D_Vector uv, 
            Math_BigDecimal bd, BigRational theta, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V3D_Polygon(this);
        } else {
            return rotateN(ray, uv, bd, theta, oom, rm);
        }
    }

    @Override
    public V3D_Polygon rotateN(V3D_Ray ray, V3D_Vector uv, 
            Math_BigDecimal bd, BigRational theta, int oom, RoundingMode rm) {
        V3D_PolygonNoInternalHoles exterior = super.rotateN(ray, uv, bd, theta, oom, rm);
        HashMap<Integer, V3D_PolygonNoInternalHoles> rInternalHoles = new HashMap<>();
        //HashMap<Integer, V3D_Polygon> rInternalHoles = new HashMap<>();
        if (internalHoles != null) {
            for (int i = 0; i < internalHoles.size(); i++) {
                rInternalHoles.put(rInternalHoles.size(),
                        internalHoles.get(i).rotate(ray, uv, bd, theta, oom, rm));
            }
        }
        return new V3D_Polygon(exterior, rInternalHoles, oom, rm);
    }

//    @Override
//    public boolean intersects(V3D_AABB aabb, int oom, RoundingMode rm) {
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
    public int addInternalHole(V3D_Polygon p) {
        int pid = internalHoles.size();
        internalHoles.put(pid, p);
        return pid;
    }
}
