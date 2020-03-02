/*
 * Copyright 2020 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain xxx copy of the License at
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_Plane - for representing infinite flat 2D planes in 3D. The plane is
 * defined by three points {@link #p}, {@link #q} and {@link #r} from which an
 * equation of the plane is derived. The derivation is by creating two
 * non-collinear V3D_Vectors {@link #pq} and {@link #pr}. The equation of the
 * plane is:
 * <ul>
 * <li>xxx*(x-x0) + yyy*(y-y0) + zzz*(z-z0 = 0</li>
 * </ul>
 * where:
 * <ul>
 * <li>x, y, and z are any of the coordinates in {@link #p}, {@link #q} and
 * {@link #r}</li>
 * <li>x0, y0 and z0 represents any other point in the plane</li>
 * <li>{@code xxx = pq.dy.multiply(pr.dz).subtract(pr.dy.multiply(pq.dz))}</li>
 * <li>{@code yyy = (pq.dx.multiply(pr.dz).subtract(pr.dx.multiply(pq.dz))).negate()}</li>
 * <li>{@code zzz = pq.dx.multiply(pr.dy).subtract(pr.dx.multiply(pq.dy))}</li>
 * </ul>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Plane extends V3D_Geometry {

    /**
     * One of the points that defines the plane.
     */
    public final V3D_Point p;

    /**
     * One of the points that defines the plane.
     */
    public final V3D_Point q;

    /**
     * One of the points that defines the plane.
     */
    public final V3D_Point r;

    /**
     * The vector representing the move from {@link #p} to {@link #q}.
     */
    public final V3D_Vector pq;

    /**
     * The vector representing the move from {@link #p} to {@link #r}.
     */
    public final V3D_Vector pr;

    /**
     * The normal vector.
     */
    public V3D_Vector n;

    /**
     * @param e V3D_Environment
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     * @throws Exception
     */
    public V3D_Plane(V3D_Environment e, V3D_Point p, V3D_Point q, V3D_Point r)
            throws Exception {
        super(e);
        //                         i                 j                   k
        pq = new V3D_Vector(p.x.subtract(q.x), p.y.subtract(q.y), p.z.subtract(q.z));
        pr = new V3D_Vector(p.x.subtract(r.x), p.y.subtract(r.y), p.z.subtract(r.z));
        /**
         * The normal perpendicular vector = n.
         */
        n = new V3D_Vector(pq.dy.multiply(pr.dz).subtract(pr.dy.multiply(pq.dz)),
                pq.dx.multiply(pr.dz).subtract(pr.dx.multiply(pq.dz)).negate(),
                pq.dx.multiply(pr.dy).subtract(pr.dx.multiply(pq.dy))
        );
        this.p = p;
        this.q = q;
        this.r = r;
        // Check for collinearity
        if (n.dx.compareTo(BigDecimal.ZERO) == 0
                && n.dy.compareTo(BigDecimal.ZERO) == 0
                && n.dz.compareTo(BigDecimal.ZERO) == 0) {
            throw new Exception("The three points do not define a plane, but are "
                    + "collinear (they might all be the same point!");
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(p=" + p.toString()
                + ", q=" + q.toString() + ", r=" + r.toString() + ")";
    }

    /**
     * @return {@link #n}
     */
    public V3D_Vector getNormalVector() {
        return n;
    }

    /**
     * @param pl The plane to test for intersection with this.
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} If this and {@code pl} intersect.
     */
    public boolean intersects(V3D_Plane pl, int scale, RoundingMode rm) {
        /** If the normal vectors are parallel, the two planes are either 
         * identical or parallel.
         */
        if (pl.n.isParallel(n, scale, rm)) {
            return this.equals(pl);
        }
        return true;
    }

    /**
     * @param pt The point to test if it is on the plane.
     * @return {@code true} If {@code pt} is on the plane.
     */
    public boolean intersects(V3D_Point pt) {
        BigDecimal d = n.dx.multiply(p.x.subtract(pt.x)).add(n.dy.multiply(p.y
                .subtract(pt.y))).add(n.dz.multiply(p.z.subtract(pt.z)));
        return d.compareTo(BigDecimal.ZERO) == 0;
        // Alternative:
        // p.x = l*(ab.x)+m*(ac.x) ---1
        // p.y = l*(ab.y)+m*(ac.y) ---2
        // p.z = l*(ab.z)+m*(ac.z) ---3
        // From 1: l = (p.x-(m*(ac.x)))/(ab.x)
        // Sub into 2: p.y = (((p.x-(m*(ac.x)))/(ab.x))*(ab.y))+(m*(ac.z))
        // p.y = (p.x/ab.x)-(m*((ac.x/ab.x))+(m*(ac.z)))
        // p.y = (p.x/ab.x)-(m*((ac.x/ab.x)-(ac.z)))
        // m*((ac.x/ab.x)-(ac.z)) = (p.x/ab.x)-(p.y)
        // m = ((p.x/ab.x)-(p.y))/((ac.x/ab.x)-(ac.z))
        // Sub into 1:
        // p.x = (l*(ab.x))+(((p.x/ab.x)-(p.y))/((ac.x/ab.x)-(ac.z))*(ac.x))
        // l = (p.x-((((p.x/ab.x)-(p.y))/((ac.x/ab.x)-(ac.z))*(ac.x)))/(ab.x)
        // Check with 3:
        // 0 = ((((p.x-(p.x/ab.x)-p.y/((ac.x/ab.x)-ac.z)(ac.x))/ab.x)*(ab.z))+((p.x/ab.x)-p.y/((ac.x/ab.x)-ac.z)*(ac.z)))-p.z
    }

    /**
     * @param l The line segment to test if it is on the plane.
     * @return {@code true} If {@code pt} is on the plane.
     */
    public boolean isOnPlane(V3D_LineSegment l) {
        return intersects(l.start) && intersects(l.end);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Plane) {
            V3D_Plane pl = (V3D_Plane) o;
            if (intersects(pl.p)) {
                if (intersects(pl.q)) {
                    if (intersects(pl.r)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.p);
        hash = 47 * hash + Objects.hashCode(this.q);
        hash = 47 * hash + Objects.hashCode(this.r);
        return hash;
    }
}
