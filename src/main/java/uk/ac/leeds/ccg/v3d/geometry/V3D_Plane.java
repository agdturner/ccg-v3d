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
 * V3D_Plane
 *
 * If:
 * <ul>
 * <li>o is the origin oc is the vector from the origin to point c</li>
 * <li>oa is the vector from the origin to point a</li>
 * <li>ab is the vector from point a to point b</li>
 * <li>bc is the vector from point b to point c</li>
 * </ul>
 * The equation for the plane is:
 * <ul>
 * <li>oc = oa + (lambda * ab) + (mu * ac)</li>
 * </ul>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Plane extends V3D_Geometry {

    public final V3D_Point a;
    public final V3D_Point b;
    public final V3D_Point c;

    public final V3D_Point ab;
    public final V3D_Point ac;
    public final V3D_Point bc;

    public V3D_Plane(V3D_Environment e, V3D_Point a, V3D_Point b, V3D_Point c)
            throws Exception {
        super(e);
        ab = new V3D_Point(e, a.x.subtract(b.x),
                a.y.subtract(b.y), a.z.subtract(b.z));
        ac = new V3D_Point(e, a.x.subtract(c.x),
                a.y.subtract(c.y), a.z.subtract(c.z));
        bc = new V3D_Point(e, b.x.subtract(c.x),
                b.y.subtract(c.y), b.z.subtract(c.z));
        BigDecimal d = ab.x.multiply(ac.y).subtract(ac.x.multiply(ab.y));
        if (d.compareTo(BigDecimal.ZERO) == 0) {
            throw new Exception("The three points do not define a plane, but are "
                    + "collinear (they might all be the same point!");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(a=" + a.toString()
                + ", b=" + b.toString() + ", c=" + c.toString() + ")";
    }

    public boolean isOnPlane(V3D_Point p) {
        /**
         * The equation for the plane is: oc = oa + (lambda * ab) + (mu * ac)
         */
        // Get to p to a
        V3D_Point pa = new V3D_Point(e, a.x.subtract(p.x), a.y.subtract(p.y),
                p.z);        
        V3D_Point pap = new V3D_Point(e, pa.x.add(p.x), pa.y.add(p.y), 
                pa.z.add(p.z));
        // Is the determinant ab ac pap 0?
        //     |ab   ac   pap  |
        // det |ab.x ac.x pap.x| = ab.x*det|ac.y pap.y|- ac.x*det|ab.y pap.y| + pap.x*det|ab.y ac.y|
        //     |ab.y ac.y pap.y|           |ac.z pap.z|          |ab.z pap.z|            |ab.z ac.z|
        //     |ab.z ac.z pap.z|
        // 
        // =  (ab.x*((ac.y*(pap.z))-((ac.y*(pap.y))))
        //   -(ac.x*((ab.y*(pap.z))-(ab.z*(pap.y))))
        //   +(pap.x*((ab.y*(ac.z))-(ab.z*(ac.y))))
        BigDecimal d = (ab.x.multiply((ac.y.multiply(pap.z)).subtract((ac.y.multiply(pap.y)))))
                .subtract(ac.x.multiply((ab.y.multiply(pap.z)).subtract(ab.z.multiply(pap.y))))
                .add(pap.x.multiply((ab.y.multiply(ac.z)).subtract(ab.z.multiply(ac.y))));
        // Alternative (rejected due to divisions.):
        // pa.x = lamda(ab.x)+mu(ac.x) ---1
        // pa.y = lamda(ab.y)+mu(ac.y) ---2
        // pa.z = lamda(ab.z)+mu(ac.z) ---3
        // From 1: lamda = (pa.x-mu(ac.x))/(ab.x)
        // Sub into 2: pa.y = ((pa.x-mu(ac.x))/(ab.x))(ab.y)+mu(ac.z)
        // pa.y = (pa.x/ab.x)-mu(ac.x/ab.x)+mu(ac.z)
        // pa.y = (pa.x/ab.x)-mu((ac.x/ab.x)-ac.z)
        // mu((ac.x/ab.x)-ac.z) = (pa.x/ab.x)-pa.y
        // mu = (pa.x/ab.x)-pa.y/((ac.x/ab.x)-ac.z)
        // Sub into 1:
        // pa.x = lambda(ab.x)+(pa.x/ab.x)-pa.y/((ac.x/ab.x)-ac.z)(ac.x)
        // lambda = (pa.x-(pa.x/ab.x)-pa.y/((ac.x/ab.x)-ac.z)(ac.x))/ab.x
        // Check with 3:
        if (d.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Plane) {
            V3D_Plane p = (V3D_Plane) o;
            if (isOnPlane(p.a)) {
                if (isOnPlane(p.b)) {
                    if (isOnPlane(p.c)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
