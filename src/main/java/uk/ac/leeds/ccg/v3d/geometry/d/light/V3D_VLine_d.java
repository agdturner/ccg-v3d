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
package uk.ac.leeds.ccg.v3d.geometry.d.light;

import java.io.Serializable;

/**
 * 3D representation of a moveable finite length line. The line starts at 
 * {@link #p} and ends at {@link #q}. The "*" denotes a point in 3D and the 
 * line is shown with a line of "e" symbols in the following depiction: {@code
 *                                       z
 *                          y           -
 *                          +          /                * p=<x0,y0,z0>
 *                          |         /                e
 *                          |        /                e
 *                          |    z0-/                e
 *                          |      /                e
 *                          |     /               e
 *                          |    /               e
 *                          |   /               e
 *                       y0-|  /               e
 *                          | /               e
 *                          |/         x1    e
 * x - ---------------------|-----------/---e---/---- + x
 *                         /|              e   x0
 *                        / |-y1          e
 *                       /  |           e
 *                      /   |          e
 *                  z1-/    |         e
 *                    /     |        e
 *                   /      |       * q=<x1,y1,z1>
 *                  /       |
 *                 /        |
 *                +         |
 *               z          -
 *                          y
 * }
 * <ul>
 * <li>Vector Form
 * <ul>
 * <li>{@code (x,y,z) = (p.getX(oom),p.getY(oom),p.getZ(oom)) + t(v.getDX(oom),v.getDY(oom),v.getDZ(oom))}</li>
 * </ul>
 * <li>Parametric Form (where t describes a particular point on the line)
 * <ul>
 * <li>{@code x = p.getX(oom) + t(v.getDX(oom))}</li>
 * <li>{@code y = p.getY(oom) + t(v.getDY(oom))}</li>
 * <li>{@code z = p.getZ(oom) + t(v.getDZ(oom))}</li>
 * </ul>
 * <li>Symmetric Form (assume {@code v.getDX(oom)}, {@code v.getDY(oom)}, and
 * {@code v.getDZ(oom)} are all nonzero)
 * <ul>
 * <li>{@code (x−p.getX(oom))/v.getDX(oom) = (y−p.getY(oom))/v.getDY(oom) = (z−p.getZ(oom))/v.getDZ(oom)}</li>
 * </ul></li>
 * </ul>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_VLine_d implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A point defining the line.
     */
    public final V3D_V_d p;

    /**
     * A point defining the line.
     */
    public final V3D_V_d q;

    /**
     * @param l Used to initialise this.
     */
    public V3D_VLine_d(V3D_VLine_d l) {
        this.p = new V3D_V_d(l.p);
        this.q = new V3D_V_d(l.q);
    }

    /**
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     */
    public V3D_VLine_d(V3D_V_d p, V3D_V_d q) {
        this.p = p;
        this.q = q;
    }

    @Override
    public String toString() {
        return toString("");
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toString(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFields(pad + " ") + "\n"
                + pad + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    protected String toStringFields(String pad) {
        return pad + "p=" + p.toString(pad) + "\n"
                + pad + ",\n"
                + pad + "q=" + q.toString(pad);
//               + pad + "q=" + q.toString(pad) + "\n"
//               + pad + ",\n"
//               + pad + "v=" + v.toString(pad);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_VLine_d l) {
            return equals(l);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * The lines are equal irrespective of the direction vector between the 
     * points.
     * 
     * @param l The line to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_VLine_d l) {
        return (p.equals(l.p) && q.equals(l.q))
                || (p.equals(l.q) && q.equals(l.p));
    }

//    @Override
//    public V3D_VPoint getC() {
//        if (c == null) {
//            c = new V3D_VPoint(
//                    (p.getX().add(q.getX())).divide(2),
//                    (p.getY().add(q.getY())).divide(2),
//                    (p.getZ().add(q.getZ())).divide(2));
//        }
//        return c;
//    }
}
