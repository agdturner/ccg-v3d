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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigDecimal;

/**
 * Class for a line in 3D represented by two points {@link #p} and {@link #q}
 * and the following equations:
 * <ul>
 * <li>Vector Form:
 * <ul>
 * <li>(x,y,z)=(x0,y0,z0)+t(a,b,c)</li>
 * <li>Where t describes a particular point on the line.</li>
 * </ul>
 * </li>
 * <li>Parametric Form:
 * <ul>
 * <li>x = x0 + ta</li>
 * <li>y = y0 + tb</li>
 * <li>z = z0 + tc</li>
 * </ul>
 * <li>Symmetric Form:
 * <ul>
 * <li>(x−x0)/a = (y−y0)/b = (z−z0)/c</li>
 * <li>Where it is assumed that a,b, and c are all nonzero.</li>
 * </ul>
 * </ul>
 *
 * The line is infinite.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Line extends V3D_Geometry {

    /**
     * A point defining the line.
     */
    public V3D_Point p;

    /**
     * A point defining the line.
     */
    public V3D_Point q;

    /**
     * The direction vector from p in the direction of q.
     */
    public V3D_Vector pq;

    /**
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @throws RuntimeException If p and q are coincident.
     */
    public V3D_Line(V3D_Point p, V3D_Point q) {
        super(p.e);
        if (p.equals(q)) {
            throw new RuntimeException("The inputs p and q are the same point "
                    + "and do not define a line.");
        }
        this.p = new V3D_Point(p);
        this.q = new V3D_Point(q);
        pq = new V3D_Vector(q.x.subtract(p.x), q.y.subtract(p.y),
                q.z.subtract(p.z));
    }

    /**
     * @param l Vector_LineSegment3D
     */
    public V3D_Line(V3D_Line l) {
        super(l.e);
        this.p = l.p;
        this.q = l.q;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(p=" + p.toString()
                + ", q=" + q.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Line) {
            V3D_Line l = (V3D_Line) o;
            if (this.getIntersects(l.p) && this.getIntersects(l.q)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.p);
        hash = 17 * hash + Objects.hashCode(this.q);
        return hash;
    }

    /**
     *
     * @param pt A point to test for intersection within the specified
     * tolerance.
     * @return {@code true} if p is on the line.
     */
    public boolean getIntersects(V3D_Point pt) {
        V3D_Vector ppt = new V3D_Vector(pt.x.subtract(p.x), pt.y.subtract(p.y),
                pt.z.subtract(p.z));
        V3D_Vector cp = pq.getCrossProduct(ppt);
        return cp.dx.compareTo(BigDecimal.ZERO) == 0
                && cp.dy.compareTo(BigDecimal.ZERO) == 0
                && cp.dz.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * @return {@code true} If this and {@code l} are parallel. 
     * @throws java.lang.Exception This should not happen as .
     */
    public boolean isParallel(V3D_Line l, int scale, RoundingMode rm) throws Exception {
        V3D_LineSegment tls = new V3D_LineSegment(p, q);
        V3D_Vector u = tls.getUnitVector(scale, rm);
        throw new UnsupportedOperationException();
    }
    
    /**
     * @param pl
     * @return {@code true} if this intersects {
     * @pl}.
     */
    public boolean getIntersects(V3D_Plane pl) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param pl
     * @return {@code true} if this intersects {
     * @pl}.
     */
    public boolean getIntersects(V3D_Line pl) {
        throw new UnsupportedOperationException();
    }

    /**
     * if this and l are the same then return this.
     *
     * @param l
     * @return
     */
    public V3D_Geometry getIntersection(V3D_Line l) {
        if (getIntersects(l)) {
            if (getIntersects(l.p)) {
                if (getIntersects(l.q)) {
                    return this;
                } else {
                    return l.p;
                }
            } else {
                if (getIntersects(l.q)) {
                    return l.q;
                } else {
                    /**
                     * Find the intersection point where the two equations of
                     * the lines meet. (x−x0)/a = (y−y0)/b = (z−z0)/c
                     */
                    // (x−p.x)/a = (y−p.y)/b = (z−p.z)/c
                    V3D_Point r = null;
                    return r;
                }
            }
        } else {
            return null;
        }
    }

    public V3D_Geometry getIntersection(V3D_Plane pl) {
        throw new UnsupportedOperationException();
    }

}
