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
 * <li>(x−x0)/a = (y−y0)/b = (z−z0) / c</li>
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
     * @throws java.lang.Exception If p and q are coincident.
     */
    public V3D_Line(V3D_Point p, V3D_Point q) throws Exception {
        super(p.e);
        if (p.equals(q)) {
            throw new Exception("The inputs p and q are the same point and do "
                    + "not define a line.");
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
            if (this.isOnLine(l.p) && this.isOnLine(l.q)) {
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
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Dot_product">dot product</A>.
     *
     * @param l V3D_LineSegment
     * @return dot product
     */
    public BigDecimal getDotProduct(V3D_Line l) {
        return (l.q.x.multiply(this.q.x)).add(l.q.y.multiply(this.q.y))
                .add(l.q.z.multiply(this.q.z));
    }

//    /**
//     * Treat this as the first vector and {@code l} as the second vector. So the
//     * resulting vector starts at {@link #p} and is in the direction given
//     * by the right hand rule.
//     *
//     * @param l V3D_LineSegment
//     * @return BigDecimal
//     */
//    public V3D_Line getCrossProduct(V3D_Line l) {
//        BigDecimal ax = q.x.subtract(p.x);
//        BigDecimal ay = q.y.subtract(p.y);
//        BigDecimal az = q.z.subtract(p.z);
//        BigDecimal bx = l.q.x.subtract(l.p.x);
//        BigDecimal by = l.q.y.subtract(l.p.y);
//        BigDecimal bz = l.q.z.subtract(l.p.z);
//        BigDecimal dx = ay.multiply(bz).subtract(bz.multiply(ay));
//        BigDecimal dy = az.multiply(bx).subtract(bx.multiply(az));
//        BigDecimal dz = ax.multiply(by).subtract(by.multiply(ax));
//        V3D_Point newEnd = new V3D_Point(e, p.x.add(dx),
//                p.y.add(dy), p.z.add(dz));
//        return new V3D_Line(p, newEnd);
//    }
    /**
     *
     * @param pt A point to test for intersection within the specified
     * tolerance.
     * @return {@code true} if p is on the line.
     */
    public boolean isOnLine(V3D_Point pt) {
        V3D_Vector ppt = new V3D_Vector(pt.x.subtract(p.x), pt.y.subtract(p.y),
                pt.z.subtract(p.z));
        V3D_Vector cp = pq.getCrossProduct(ppt);
//        BigDecimal scalar = ppt.getDotProduct(pq);
//        BigDecimal magnitude = Math_BigDecimal.sqrt(
//                pq.dx.pow(2).add(pq.dy.pow(2).add(pq.dz.pow(2))), scale, rm);
//        V3D_Point up = new V3D_Point(e, pq.dx.multiply(magnitude), 
//                pq.dy.multiply(magnitude), pq.dz.multiply(magnitude));
//        
//        V3D_Vector u = new V3D_Vector(pt, up);
//        // Is ppt a scalar product of pq?
//        BigDecimal t = pt.x.divide(pq.dx);
//        if (pt.y.divide(pq.dy).compareTo(t) == 0 &&
//            pt.z.divide(pq.dz).compareTo(t) == 0) {
//            return true;
//        }
//        return false;
        return cp.dx.compareTo(BigDecimal.ZERO) == 0
                && cp.dy.compareTo(BigDecimal.ZERO) == 0
                && cp.dz.compareTo(BigDecimal.ZERO) == 0;
    }

//    /**
//     * Calculate and return the closest distance from pt to the line.
//     * @param pt
//     * @return 
//     */
//    public BigDecimal getDistance(V3D_Point pt) {
//        
//        
//    }
}
