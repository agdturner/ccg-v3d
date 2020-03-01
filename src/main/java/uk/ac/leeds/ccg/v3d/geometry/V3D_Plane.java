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
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_Plane - for representing infinite flat 2D planes in 3D. The plane is
 * defined by three points {@link #p}, {@link #q} and {@link #r} from which an
 * equation of the plane is derived. The derivation is by creating two
 * non-collinear V3D_Vectors {@link #pq} and {@link #pr}. The equation of the
 * plane is:
 * <ul>
 * <li>a*(x-x0) + b*(y-y0) + c*(z-z0 = 0</li>
 * </ul>
 * where:
 * <ul>
 * <li>x, y, and z are any of the coordinates in {@link #p}, {@link #q} and
 * {@link #r}</li>
 * <li>x0, y0 and z0 represents any other point in the plane</li>
 * <li>{@code a = pq.dy.multiply(pr.dz).subtract(pr.dy.multiply(pq.dz))}</li>
 * <li>{@code b = (pq.dx.multiply(pr.dz).subtract(pr.dx.multiply(pq.dz))).negate()}</li>
 * <li>{@code c = pq.dx.multiply(pr.dy).subtract(pr.dx.multiply(pq.dy))}</li>
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
     * The normal perpendicular vector x.
     */
    public BigDecimal a;

    /**
     * The normal perpendicular vector y.
     */
    public BigDecimal b;
    
    /**
     * The normal perpendicular vector z.
     */
    public BigDecimal c;

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
//        BigDecimal d = ab.x.multiply(ac.y.multiply(c.z).subtract(ac.z.multiply(c.y)))
//                .subtract(ac.x.multiply(ab.y.multiply(c.z).subtract(ab.z.multiply(c.y))))
//                .add(c.x.multiply(ab.y.multiply(ac.z).subtract(ab.z.multiply(ac.y))));
//        BigDecimal d = a.x.multiply(b.y.multiply(c.z).subtract(b.z.multiply(c.y)))
//                .subtract(b.x.multiply(a.y.multiply(c.z).subtract(a.z.multiply(c.y))))
//                .add(c.x.multiply(a.y.multiply(b.z).subtract(a.z.multiply(b.y))));
//        if (d.compareTo(BigDecimal.ZERO) == 0) {
//            throw new Exception("The three points do not define a plane, but are "
//                    + "collinear (they might all be the same point!");
//        }
        /**
         * The normal perpendicular vector = n.
         */
        V3D_Vector n = new V3D_Vector(
                pq.dy.multiply(pr.dz).subtract(pr.dy.multiply(pq.dz)),
                (pq.dx.multiply(pr.dz).subtract(pr.dx.multiply(pq.dz))).negate(),
                pq.dx.multiply(pr.dy).subtract(pr.dx.multiply(pq.dy))
        );
        this.p = p;
        this.q = q;
        this.r = r;
        this.a = n.dx;
        this.b = n.dy;
        this.c = n.dz;

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(p=" + p.toString()
                + ", q=" + q.toString() + ", r=" + r.toString() + ")";
    }

    public boolean isOnPlane(V3D_Point pt) {
        // True if
        BigDecimal d = a.multiply(p.x.subtract(pt.x)).add(b.multiply(p.y
                .subtract(pt.y))).add(c.multiply(p.z.subtract(pt.z)));
        if (d.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }
        return false;
//        // Is the determinant ab ac p = 0?
//        //     |ab   ac   p  |
//        // det |ab.x ac.x p.x| = p.x*det|ab.y ac.y|- p.y*det|ab.x ac.x| + p.z*det|ab.x ac.x|
//        //     |ab.y ac.y p.y|          |ab.z ac.z|         |ab.z ac.z|          |ab.y ac.y|
//        //     |ab.z ac.z p.z|
//        // 
//        // = p.x*((ab.y*(ac.z))-((ab.z*(ac.y))))
//        //   -(p.y*((ab.x*(ac.z))-((ab.z*(ac.x)))))
//        //   +(p.z*((ab.x*(ac.y))-((ab.y*(ac.x)))))
//        BigDecimal d = p.x.multiply((ab.y.multiply(ac.z)).subtract((ab.z.multiply(ac.y))))
//           .subtract(p.y.multiply((ab.x.multiply(ac.z)).subtract((ab.z.multiply(ac.x)))))
//           .add(p.z.multiply((ab.x.multiply(ac.y)).subtract((ab.y.multiply(ac.x)))));
//        return d.compareTo(BigDecimal.ZERO) == 0;
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
//        BigDecimal c = ((((p.x.subtract(p.x / ab.x).subtract(p.y) / ((ac.x / ab.x).subtract(ac.z))(ac.x))/ab.x)*(ab.z))+((p.x / ab.x)-p.y / ((ac.x / ab.x) - ac.z) * (ac.z)
//        )).subtract(p.z);
    }

    public boolean isOnPlane(V3D_LineSegment l) {
        if (isOnPlane(l.start) && isOnPlane(l.end)) {
            return true;
        }
        return false;
//        /**
//         * The equation for the plane is: oc = oa + (lambda * ab) + (mu * ac)
//         */
//        // Move l to a
//        V3D_Point la = new V3D_Point(e, a.x.subtract(l.start.x), a.y.subtract(l.start.y),
//                l.start.z);
//        V3D_Point lal = new V3D_Point(e, la.x.add(l.start.x), la.y.add(l.start.y),
//                la.z.add(l.start.z));
//        // Is the determinant ab ac lal 0?
//        //     |ab   ac   lal  |
//        // det |ab.x ac.x lal.x| = ab.x*det|ac.y lal.y|- ac.x*det|ab.y lal.y| + lal.x*det|ab.y ac.y|
//        //     |ab.y ac.y lal.y|           |ac.z lal.z|          |ab.z lal.z|            |ab.z ac.z|
//        //     |ab.z ac.z lal.z|
//        // 
//        // =  ab.x*((ac.y*(lal.z))-(ac.y*(lal.y))))
//        //   -(ac.x*((ab.y*(lal.z))-(ab.z*(lal.y))))
//        //   +(lal.x*((ab.y*(ac.z))-(ab.z*(ac.y))))
//        BigDecimal d = ab.x.multiply((ac.y.multiply(lal.z)).subtract((ac.y.multiply(lal.y))))
//                .subtract(ac.x.multiply((ab.y.multiply(lal.z)).subtract(ab.z.multiply(lal.y))))
//                .add(lal.x.multiply((ab.y.multiply(ac.z)).subtract(ab.z.multiply(ac.y))));
//
//        if (d.compareTo(BigDecimal.ZERO) == 0) {
//            return true;
//        }
//        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Plane) {
            V3D_Plane pl = (V3D_Plane) o;
            if (isOnPlane(pl.p)) {
                if (isOnPlane(pl.q)) {
                    if (isOnPlane(pl.r)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
