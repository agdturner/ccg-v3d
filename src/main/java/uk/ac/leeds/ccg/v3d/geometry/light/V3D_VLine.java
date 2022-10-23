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
package uk.ac.leeds.ccg.v3d.geometry.light;

import java.util.Objects;

/**
 * 3D representation of a moveable finite length line. The line passes through the point
 * {@link #p} and is travelling through point {@link #q}. The "*" denotes a
 * point in 3D and the line is shown with a line of "e" symbols in the following
 * depiction: {@code
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
public class V3D_VLine extends V3D_VGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * A point defining the line.
     */
    protected final V3D_V p;

    /**
     * A point defining the line.
     */
    protected final V3D_V q;

    /**
     * @param l Used to initialise this.
     */
    public V3D_VLine(V3D_VLine l) {
        super(new V3D_V(l.offset));
        this.p = new V3D_V(l.p);
        this.q = new V3D_V(l.q);
    }

    /**
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     */
    public V3D_VLine(V3D_V p, V3D_V q) {
        super(V3D_V.ZERO);
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
        if (o instanceof V3D_VLine l) {
            return equals(l);
        }
        return false;
    }

    /**
     * @param l The line to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_VLine l) {
        return (p.equals(l.p) && q.equals(l.q))
                || (p.equals(l.q) && q.equals(l.p));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.p);
        hash = 17 * hash + Objects.hashCode(this.q);
        return hash;
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
    @Override
    public void translate(V3D_V v) {
        p.translate(v);
        q.translate(v);
    }
}
