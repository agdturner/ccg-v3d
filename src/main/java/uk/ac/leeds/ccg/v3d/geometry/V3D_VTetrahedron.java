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

/**
 *
 * A V3D_Tetrahedron is a V3D_Shape comprising 4 corner V3D_Point points that
 * are not coplanar or collinear or coincident. There are 4 V3D_Triangle
 * triangle faces defined from the outside with the points arranged clockwise.
 * Each face has a V3D_Line_Segment edge equal to that of another face, but
 * these are stored independently in that the order of the points and the
 * directions of the vectors might be opposite. The points are shared between
 * all the triangles. {@code
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
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_VTetrahedron extends V3D_VGeometry {

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

//    /**
//     * For storing the centroid.
//     */
//    protected V3D_Point c;
    /**
     * Create a new instance.
     *
     * @param p A point that defines the tetrahedron.
     * @param q A point that defines the tetrahedron.
     * @param r A point that defines the tetrahedron.
     * @param s A point that defines the tetrahedron.
     */
    public V3D_VTetrahedron(V3D_VPoint p, V3D_VPoint q, V3D_VPoint r, V3D_VPoint s) {
        super(V3D_V.ZERO);
        pqr = new V3D_VTriangle(p, q, r);
        qsr = new V3D_VTriangle(q, s, r);
        spr = new V3D_VTriangle(s, p, r);
        psq = new V3D_VTriangle(p, s, q);
    }

    /**
     * Create a new instance.
     *
     * @param pqr What {@link #pqr} is set to.
     * @param qsr What {@link #qsr} is set to.
     * @param spr What {@link #spr} is set to.
     * @param psq What {@link #psq} is set to.
     */
    public V3D_VTetrahedron(V3D_V offset, V3D_VTriangle pqr, V3D_VTriangle qsr, 
            V3D_VTriangle spr, V3D_VTriangle psq) {
        super(offset);
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
    
    @Override
    public V3D_VPoint getC() {
        if (c == null) {
            c = new V3D_VPoint(
                    (pqr.getC().getX().add(qsr.getC().getX()).add(spr.getC().getX()).add(psq.getC().getX())).divide(4),
                    (pqr.getC().getY().add(qsr.getC().getY()).add(spr.getC().getY()).add(psq.getC().getY())).divide(4),
                    (pqr.getC().getZ().add(qsr.getC().getZ()).add(spr.getC().getZ()).add(psq.getC().getZ())).divide(4));
        }
        return c;
    }
}
