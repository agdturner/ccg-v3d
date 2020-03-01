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

/**
 * Class for a line in 3D represented by two points {@link #p} and {@link #q} 
 * and the following parametric equations:
 * <ul>
 * <li>x = p.x + t*(q.x-(p.x))</li>
 * <li>y = p.y + t*(p.y-(p.y))</li>
 * <li>z = p.z + t*(q.z-(p.z))</li>
 * </ul>
 * The line is infinite.
 * 
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Line extends V3D_Geometry  {

    public V3D_Point p;
    public V3D_Point q;

    /**
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @throws java.lang.Exception If p and q are coincident.
     */
    public V3D_Line(V3D_Point p, V3D_Point q) throws Exception {
        super(p.e);
        if(p.equals(q)) {
            throw new Exception("The inputs p and q are the same point and do "
                    + "not define a line.");
        }
        this.p = new V3D_Point(p);
        this.q = new V3D_Point(q);
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
                if (this.isOnLine(l.p)) {
                    if (this.isOnLine(l.q)) {
                        return true;
                    }
                }
        }
        return false;
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
     * @param pt A point to test for intersection within the specified tolerance.
     * @return {@code true} if p is on the line.
     */
    public boolean isOnLine(V3D_Point pt) {
        return false;
    }
}
