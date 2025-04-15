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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_FiniteGeometry for representing finite geometries.
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_Area extends V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The id of the area.
     */
    protected final int id;

    /**
     * For storing the plane of the area.
     */
    protected V3D_Plane pl;

    /**
     * The order of magnitude used for the calculation of {@link #pl}.
     */
    public int ploom;

    /**
     * The RoundingMode used for the calculation of {@link #pl}.
     */
    public RoundingMode plrm;

    /**
     * For storing the points. The keys are IDs.
     */
    protected HashMap<Integer, V3D_Point> points;

    /**
     * For storing the edges. The keys are IDs.
     */
    protected HashMap<Integer, V3D_LineSegment> edges;

    /**
     * Creates a new instance with offset V3D_Vector.ZERO.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param pl What {@link #pl} is set to.
     */
    public V3D_Area(V3D_Environment env, V3D_Vector offset, V3D_Plane pl) {
        super(env, offset);
        this.id = env.getNextID();
        this.pl = pl;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@link pl} accurate to at least the oom precision using
     * RoundingMode rm.
     */
    public final V3D_Plane getPl(int oom, RoundingMode rm) {
        if (pl == null) {
            initPl(oom, rm);
        } else if (ploom < oom) {
            return pl;
        } else if (ploom == oom && plrm.equals(rm)) {
            return pl;
        }
        //initPl(oom, rm);
        return pl;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed. For initialising
     * {@link #pl}.
     */
    protected abstract void initPl(int oom, RoundingMode rm);

    /**
     * @param pt The normal will point to this side of the plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@link pl} accurate to at least the oom precision using
     * RoundingMode rm.
     */
    public final V3D_Plane getPl(V3D_Point pt, int oom, RoundingMode rm) {
        if (pl == null) {
            initPl(pt, oom, rm);
        } else if (ploom < oom) {
            return pl;
        } else if (ploom == oom && plrm.equals(rm)) {
            return pl;
        }
        //initPl(pt, oom, rm);
        return pl;
    }

    /**
     * @param pt The normal will point to this side of the plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed. For initialising
     * {@link #pl}.
     */
    protected abstract void initPl(V3D_Point pt, int oom, RoundingMode rm);

    /**
     * For getting the points of a shape.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A HashMap of the points with integer identifier keys.
     */
    public abstract HashMap<Integer, V3D_Point> getPoints(int oom,
            RoundingMode rm);

    /**
     * For getting the edges of a shape.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A HashMap of the edges with integer identifier keys.
     */
    public abstract HashMap<Integer, V3D_LineSegment> getEdges(int oom,
            RoundingMode rm);

    /**
     * For calculating and returning the perimeter.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The Perimeter.
     */
    public abstract BigRational getPerimeter(int oom, RoundingMode rm);

    /**
     * For calculating and returning the area.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The area.
     */
    public abstract BigRational getArea(int oom, RoundingMode rm);

    /**
     * @param p The point to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is intersected by {@code p}.
     */
    public abstract boolean intersects(V3D_Point p, int oom, RoundingMode rm);

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param p A point to test for intersection.
     * @param as The areas to test for intersection with p.
     * @return {@code true} if any {@code as} are intersected by {@code p}.
     */
    public static boolean intersects(int oom, RoundingMode rm, V3D_Point p,
            V3D_Area... as) {
        return intersects(oom, rm, p, Arrays.asList(as));
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param p A point to test for intersection.
     * @param as The areas to test for intersection with p.
     * @return {@code true} if any {@code as} are intersected by {@code p}.
     */
    public static boolean intersects(int oom, RoundingMode rm, V3D_Point p,
            Collection<V3D_Area> as) {
        return as.parallelStream().anyMatch(x -> x.intersects(p, oom, rm));
    }

    /**
     * @param aabb The Axis Aligned Bounding Box to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is intersected by {@code aabb}.
     */
    public abstract boolean intersects(V3D_AABB aabb, int oom, RoundingMode rm);

    /**
     * @param l The line to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is intersected by {@code l}.
     */
    public abstract boolean intersects(V3D_Line l, int oom, RoundingMode rm);

    /**
     * @param l The line segment to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is intersected by {@code l}.
     */
    public abstract boolean intersects(V3D_LineSegment l, int oom, RoundingMode rm);

    /**
     * @param r The ray to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is intersected by {@code r}.
     */
    public abstract boolean intersects(V3D_Ray r, int oom, RoundingMode rm);
    
    /**
     * @param r The ray to test for intersection which is assumed to be
     * non-coplanar.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is intersected by {@code r}.
     */
    public abstract boolean intersectsNonCoplanar(V3D_Ray r, int oom, RoundingMode rm);

    /**
     * @param t The triangle to test if it intersects.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if {@code this} is intersected by {@code t}.
     */
    public abstract boolean intersects(V3D_Triangle t, int oom, RoundingMode rm);

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param a An area to test for intersection.
     * @return {@code true} if {@code this} is intersected by {@code a}.
     */
    public boolean intersects(V3D_Area a, int oom, RoundingMode rm) {
        return a.getPoints(oom, rm).values().parallelStream().anyMatch(x
                -> a.intersects(x, oom, rm));
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param l A line segment to test for intersection with any of the areas in
     * as.
     * @param as The areas to test for intersection with l.
     * @return {@code true} if any of {@code as} is intersected by {@code l}.
     */
    public static boolean intersects(int oom, RoundingMode rm,
            V3D_LineSegment l, V3D_Area... as) {
        return intersects(oom, rm, l, Arrays.asList(as));
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param l A line segment to test for intersection with any of the areas in
     * as.
     * @param as The areas to test for intersection with l.
     * @return {@code true} if any of {@code as} is intersected by {@code l}.
     */
    public static boolean intersects(int oom, RoundingMode rm,
            V3D_LineSegment l, Collection<V3D_Area> as) {
        return as.parallelStream().anyMatch(x -> x.intersects(l, oom, rm));
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param as The areas to test for intersection with a.
     * @return {@code true} if {@code this} is intersected by any areas in
     * {@code as}.
     */
    public boolean intersects(int oom, RoundingMode rm, V3D_Area... as) {
        return intersects(oom, rm, Arrays.asList(as));
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param as The areas to test for intersection with a.
     * @return {@code true} if {@code this} is intersected by any areas in
     * {@code as}.
     */
    public boolean intersects(int oom, RoundingMode rm, Collection<V3D_Area> as) {
        return as.parallelStream().anyMatch(x -> intersects(x, oom, rm));
    }

    /**
     * Identify if {@code this} contains {@code p}. Containment excludes the
     * edge.
     *
     * @param p The point to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} if {@code this} contains {@code p}.
     */
    //public abstract boolean contains(V3D_Point p, int oom, RoundingMode rm);
    public boolean contains(V3D_Point p, int oom, RoundingMode rm) {
        if (intersects(p, oom, rm)) {
            return !getEdges(oom, rm).values().parallelStream().anyMatch(x
                    -> x.intersects(p, oom, rm));
        }
        return false;
    }

    /**
     * Identify if {@code this} contains {@code l}. Containment excludes the
     * edge.
     *
     * @param l The line segments to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} if {@code this} contains {@code l}.
     */
    //public abstract boolean contains(V3D_LineSegment l, int oom, RoundingMode rm);
    public boolean contains(V3D_LineSegment l, int oom, RoundingMode rm) {
        return contains(l.getP(), oom, rm)
                && contains(l.getQ(oom, rm), oom, rm);
    }

    /**
     * Identify if {@code this} contains {@code t}. Containment excludes the
     * edge.
     *
     * @param t The triangle to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} if {@code this} contains {@code t}.
     */
    //public abstract boolean contains(V3D_Area a, int oom, RoundingMode rm);
    public boolean contains(V3D_Triangle t, int oom, RoundingMode rm) {
        if (getAABB(oom, rm).contains(t.getAABB(oom, rm), oom)) {
            // Faster than using the general method?
            return contains(t.getPQ(oom, rm), oom, rm)
                    && contains(t.getQR(oom, rm), oom, rm)
                    && contains(t.getRP(oom, rm), oom, rm);
        } else {
            return false;
        }
    }

    /**
     * Identify if {@code this} contains {@code a}. Containment excludes the
     * edge.
     *
     * @param a The area to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} if {@code this} contains {@code a}.
     */
    //public abstract boolean contains(V3D_Area a, int oom, RoundingMode rm);
    public boolean contains(V3D_Area a, int oom, RoundingMode rm) {
        return a.getPoints(oom, rm).values().parallelStream().allMatch(x
                -> contains(x, oom, rm));
    }

    /**
     * Get the minimum distance squared to {@code pt}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared to {@code pv}.
     */
    public abstract BigRational getDistanceSquared(V3D_Point pt, int oom, RoundingMode rm);

    /**
     * Compute and return the intersection with {@code r}.
     *
     * @param r The ray to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection or {@code null}.
     */
    public abstract V3D_FiniteGeometry getIntersect(V3D_Ray r, int oom,
            RoundingMode rm);

    /**
     * Compute and return the intersection with {@code r} which is assumed to be
     * non-coplanar.
     *
     * @param r The ray to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection point or {@code null}.
     */
    public abstract V3D_Point getIntersectNonCoplanar(V3D_Ray r, int oom,
            RoundingMode rm);
}
