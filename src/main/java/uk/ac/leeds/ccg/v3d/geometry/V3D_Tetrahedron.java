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

import java.io.Serializable;
import java.math.BigDecimal;

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
public class V3D_Tetrahedron implements V3D_3DShape, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    protected V3D_Envelope en;

    /**
     * The pqr triangle of the tetrahedron.
     */
    protected final V3D_Triangle pqr;

    /**
     * The qsr triangle of the tetrahedron.
     */
    protected final V3D_Triangle qsr;

    /**
     * The spr triangle of the tetrahedron.
     */
    protected final V3D_Triangle spr;

    /**
     * The psq triangle of the tetrahedron.
     */
    protected final V3D_Triangle psq;

    /**
     * For storing the centroid.
     */
    protected V3D_Point c;

    /**
     * Create a new instance.
     *
     * @param p A point that defines the tetrahedron.
     * @param q A point that defines the tetrahedron.
     * @param r A point that defines the tetrahedron.
     * @param s A point that defines the tetrahedron.
     * @param oom The Order of Magnitude for the initialisation.
     */
    public V3D_Tetrahedron(V3D_Point p, V3D_Point q, V3D_Point r,
            V3D_Point s, int oom) {
        pqr = new V3D_Triangle(p, q, r, oom);
        qsr = new V3D_Triangle(q, s, r, oom);
        spr = new V3D_Triangle(s, p, r, oom);
        psq = new V3D_Triangle(p, s, q, oom);
    }

    /**
     * Create a new instance.
     * 
     * @param pqr What {@link #pqr} is set to.
     * @param qsr What {@link #qsr} is set to.
     * @param spr What {@link #spr} is set to.
     * @param psq What {@link #psq} is set to. 
     */
    public V3D_Tetrahedron(V3D_Triangle pqr, V3D_Triangle qsr, V3D_Triangle spr,
            V3D_Triangle psq) {
        this.pqr = pqr;
        this.qsr = qsr;
        this.spr = spr;
        this.psq = psq;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "(" + pqr.toString() + ", "
                + qsr.toString() + ", " + spr.toString() + ", "
                + psq.toString() + ")";
    }

    @Override
    public V3D_Envelope getEnvelope(int oom) {
        if (en == null) {
            en = pqr.getEnvelope(oom).union(qsr.getEnvelope(oom));
        }
        return en;
    }

    /**
     * @param v The vector to apply.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return a new rectangle.
     */
    public V3D_Tetrahedron apply(V3D_Vector v, int oom) {
        return new V3D_Tetrahedron(pqr.apply(v, oom), qsr.apply(v, oom), 
                spr.apply(v, oom), psq.apply(v, oom));
    }
                
    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The area of the triangle (rounded).
     */
    @Override
    public BigDecimal getArea(int oom) {
        return pqr.getArea(oom).add(qsr.getArea(oom)).add(spr.getArea(oom)).add(psq.getArea(oom));
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     */
    @Override
    public BigDecimal getVolume(int oom) {
        return null;
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The centroid point.
     */
    public V3D_Point getCentroid(int oom) {
        V3D_LineSegment a = new V3D_LineSegment(pqr.getCentroid(oom), qsr.q, oom);
        V3D_LineSegment b = new V3D_LineSegment(psq.getCentroid(oom), qsr.r, oom);
        return (V3D_Point) a.getIntersection(b, oom, true);
    }

    @Override
    public boolean isIntersectedBy(V3D_Point p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom, boolean flag) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
