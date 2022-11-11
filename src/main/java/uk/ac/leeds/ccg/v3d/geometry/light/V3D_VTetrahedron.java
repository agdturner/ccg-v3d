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

import java.io.Serializable;

/**
 * A V3D_VTetrahedron comprising 4 V3D_VTriangle faces. {@code
 *  p *- - - - - - - - - - - + - - - - - - - - - - -* q
 *     \ ~                                         /|
 *      \     ~                                   / |
 *       \         ~                             /  |
 *        \             ~                       /   |
 *         \                 ~                 /    |
 *          \                      ~          /     |
 *           \                          ~    /      |
 *            \                             / ~     |
 *             \                           /       ~s
 *              \                         /        :
 *               \                       /
 *                \                     /      :
 *                 \                   /
 *                  \                 /     :
 *                   \               /
 *                    \             /    :
 *                     \           /
 *                      \         /   :
 *                       \       /
 *                        \     /  :
 *                         \   /
 *                          \ /:
 *                           *
 *                           r
 * }
 * For more complicated triangles uk.ac.leeds.ccg.v3d.geometry.V3D_Tetrahedron.
 * 
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_VTetrahedron implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The pqr triangle of the tetrahedron.
     */
    public final V3D_VTriangle pqr;

    /**
     * The qsr triangle of the tetrahedron.
     */
    public final V3D_VTriangle qsr;

    /**
     * The spr triangle of the tetrahedron.
     */
    public final V3D_VTriangle spr;

    /**
     * The psq triangle of the tetrahedron.
     */
    public final V3D_VTriangle psq;

    /**
     * Create a new instance.
     *
     * @param pqr What {@link #pqr} is set to.
     * @param qsr What {@link #qsr} is set to.
     * @param spr What {@link #spr} is set to.
     * @param psq What {@link #psq} is set to.
     */
    public V3D_VTetrahedron(V3D_VTriangle pqr, V3D_VTriangle qsr, 
            V3D_VTriangle spr, V3D_VTriangle psq) {
        this.pqr = pqr;
        this.qsr = qsr;
        this.spr = spr;
        this.psq = psq;
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
        return pad + "pqr=" + pqr.toString(pad) + "\n"
                + pad + ",\n"
                + pad + "qsr=" + qsr.toString(pad) + "\n"
                + pad + ",\n"
                + pad + "spr=" + spr.toString(pad) + "\n"
                + pad + ",\n"
                + pad + "psq=" + psq.toString(pad);
    }

//    @Override
//    public V3D_V getCentroid() {
//        if (centroid == null) {
//            centroid = new V3D_V(
//                    (pqr.getCentroid().x.add(qsr.getCentroid().x).add(spr.getCentroid().x).add(psq.getCentroid().x)).divide(4),
//                    (pqr.getCentroid().y.add(qsr.getCentroid().y).add(spr.getCentroid().y).add(psq.getCentroid().y)).divide(4),
//                    (pqr.getCentroid().z.add(qsr.getCentroid().z).add(spr.getCentroid().z).add(psq.getCentroid().z)).divide(4));
//        }
//        return centroid;
//    }

}
