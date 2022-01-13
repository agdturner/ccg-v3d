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
import uk.ac.leeds.ccg.math.number.Math_BigRational;

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
public class V3D_Tetrahedron extends V3D_Geometry implements V3D_Volume, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    protected V3D_Envelope en;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector p;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector q;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector r;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector s;

    /**
     * For storing the pqr triangle of the tetrahedron.
     */
    public V3D_Triangle pqr;

    /**
     * For storing the qsr triangle of the tetrahedron.
     */
    public V3D_Triangle qsr;

    /**
     * For storing the spr triangle of the tetrahedron.
     */
    public V3D_Triangle spr;

    /**
     * For storing the psq triangle of the tetrahedron.
     */
    public V3D_Triangle psq;

    /**
     * Create a new instance.
     *
     * @param p A point that defines the tetrahedron.
     * @param q A point that defines the tetrahedron.
     * @param r A point that defines the tetrahedron.
     * @param s A point that defines the tetrahedron.
     * @param oom The Order of Magnitude for the initialisation.
     */
    public V3D_Tetrahedron(V3D_Vector offset, int oom, V3D_Vector p,
            V3D_Vector q, V3D_Vector r, V3D_Vector s) {
        super(offset, oom);
        this.p = p;
        this.q = q;
        this.r = r;
        this.s = s;
    }

//    /**
//     * Create a new instance.
//     *
//     * @param pqr What {@link #pqr} is set to.
//     * @param qsr What {@link #qsr} is set to.
//     * @param spr What {@link #spr} is set to.
//     * @param psq What {@link #psq} is set to.
//     */
//    public V3D_Tetrahedron(V3D_Vector offset, int oom, V3D_Triangle pqr,
//            V3D_Triangle qsr, V3D_Triangle spr, V3D_Triangle psq) {
//        super(offset, oom);
//        this.pqr = pqr;
//        this.qsr = qsr;
//        this.spr = spr;
//        this.psq = psq;
//    }

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
    @Override
    protected String toStringFields(String pad) {
        String r = super.toStringFields(pad);
        r += pad + ",\n";
        r += pad + "p=" + p.toString(pad) + "\n";
        r += pad + ",\n";
        r += pad + "q=" + q.toString(pad) + "\n";
        r += pad + ",\n";
        r += pad + "r=" + this.r.toString(pad) + "\n";
        r += pad + ",\n";
        r += pad + "s=" + s.toString(pad) + "\n";
        return r;
    }

    @Override
    public V3D_Envelope getEnvelope(int oom) {
        if (en == null) {
//            en = pqr.getEnvelope(oom).union(qsr.getEnvelope(oom));
            en = getP(oom).getEnvelope(oom)
                    .union(getQ(oom).getEnvelope(oom))
                    .union(getR(oom).getEnvelope(oom))
                    .union(getS(oom).getEnvelope(oom));
        }
        return en;
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getP(int oom) {
        return new V3D_Point(offset, p, oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getQ(int oom) {
        return new V3D_Point(offset, q, oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getR(int oom) {
        return new V3D_Point(offset, r, oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getS(int oom) {
        return new V3D_Point(offset, s, oom);
    }

    /**
     * If {@code null} initialise {@link #pqr} and return it.
     */
    public V3D_Triangle getPqr() {
        if (pqr == null) {
            pqr = new V3D_Triangle(p, q, r, oom);
        }
        return pqr;
    }
    
    /**
     * If {@code null} initialise {@link #qsr} and return it.
     */
    public V3D_Triangle getQsr() {
        if (qsr == null) {
            qsr = new V3D_Triangle(q, s, r, oom);
            }
        return qsr;
    }
    
    /**
     * If {@code null} initialise {@link #spr} and return it.
     */
    public V3D_Triangle getSpr() {
        if (spr == null) {
            spr = new V3D_Triangle(s, p, r, oom);
            }
        return spr;
    }
    
    /**
     * If {@code null} initialise {@link #psq} and return it.
     */
    public V3D_Triangle getPsq() {
        if (psq == null) {
            psq = new V3D_Triangle(p, s, q, oom);
            }
        return psq;
    }
    
    /**
     * For calculating and returning the surface area.
     * 
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The outer surface area of the tetrahedron (rounded).
     */
    @Override
    public BigDecimal getArea(int oom) {
        return getPqr().getArea(oom)
                .add(getQsr().getArea(oom))
                .add(getSpr().getArea(oom))
                .add(getPsq().getArea(oom));
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
        V3D_LineSegment a = new V3D_LineSegment(pqr.getCentroid(oom).getVector(oom), qsr.getQ().getVector(oom), oom);
        V3D_LineSegment b = new V3D_LineSegment(psq.getCentroid(oom).getVector(oom), qsr.getR().getVector(oom), oom);
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

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Change {@link #offset} without changing the overall line.
     *
     * @param offset What {@link #offset} is set to.
     */
    public void setOffset(V3D_Vector offset) {
        p = p.add(offset, oom).subtract(this.offset, oom);
        q = q.add(offset, oom).subtract(this.offset, oom);
        r = r.add(offset, oom).subtract(this.offset, oom);
        s = s.add(offset, oom).subtract(this.offset, oom);
        this.offset = offset;
    }
    
    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta) {
        p = p.rotate(axisOfRotation, theta, bI, oom);
        q = q.rotate(axisOfRotation, theta, bI, oom);
        r = r.rotate(axisOfRotation, theta, bI, oom);
        s = s.rotate(axisOfRotation, theta, bI, oom);
        pqr = null;
        psq = null;
        spr = null;
        qsr = null;
        en = null;
    }
}
