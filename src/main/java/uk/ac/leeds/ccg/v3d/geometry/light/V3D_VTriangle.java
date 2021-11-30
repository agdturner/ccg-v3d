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

/**
 * For representing and processing triangles in 3D. The corner points are
 * {@link #p}, {@link #q} and {@link #r}. The following depicts a triangle {@code
 *
 *  p *- - - - - - - - - - - - - - - - - - - - - - -* q
 *     \                                           /
 *      \                                         /
 *       \                                       /
 *        \                                     /
 *         \                                   /
 *          \                                 /
 *           \                               /
 *            \                             /
 *             \                           /
 *              \                         /
 *               \                       /
 *                \                     /
 *                 \                   /
 *                  \                 /
 *                   \               /
 *                    \             /
 *                     \           /
 *                      \         /
 *                       \       /
 *                        \     /
 *                         \   /
 *                          \ /
 *                           *
 *                           r
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_VTriangle extends V3D_VGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * A point of the triangle. The true position with respect to the origin is 
     * {@code p.add(offset)}.
     */
    public V3D_V p;

    /**
     * A point of the triangle. The true position with respect to the origin is 
     * {@code q.add(offset)}.
     */
    public V3D_V q;

    /**
     * A point of the triangle. The true position with respect to the origin is 
     * {@code r.add(offset)}.
     */
    public V3D_V r;

    /**
     * Creates a new triangle.
     *
     * @param t The triangle to clone.
     */
    public V3D_VTriangle(V3D_VTriangle t) {
        super(new V3D_V(t.offset));
        p = new V3D_V(t.p);
        q = new V3D_V(t.q);
        r = new V3D_V(t.r);
    }

    /**
     * Creates a new triangle.
     *
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     */
    public V3D_VTriangle(V3D_V p, V3D_V q, V3D_V r) {
        super(V3D_V.ZERO);
        this.p = p;
        this.q = q;
        this.r = r;
    }

    /**
     * Creates a new triangle.
     *
     * @param offset What {@link #offset} is set to.
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param r What {@link #r} is set to.
     */
    public V3D_VTriangle(V3D_V offset, V3D_V p, V3D_V q, V3D_V r) {
        super(offset);
        this.p = p;
        this.q = q;
        this.r = r;
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
                + pad + "q=" + q.toString(pad) + "\n"
                + pad + ",\n"
                + pad + "r=" + r.toString(pad);
    }

//    @Override
//    public V3D_V getCentroid() {
//        return new V3D_V(
//                (p.x.add(q.x).add(r.x)).divide(3).add(offset.x),
//                (p.y.add(q.y).add(r.y)).divide(3).add(offset.y),
//                (p.z.add(q.z).add(r.z)).divide(3).add(offset.z));
//    }
    
    @Override
    public void apply(V3D_V v) {
        p.apply(v);
        q.apply(v);
        r.apply(v);
    }

}
