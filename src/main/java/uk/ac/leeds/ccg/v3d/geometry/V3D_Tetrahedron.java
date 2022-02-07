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
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

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
public class V3D_Tetrahedron extends V3D_Geometry implements V3D_Volume {

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
     * @param e What {@link #e} is set to.
     * @param offset What {@link #offset} is set to.
     * @param p A point that defines the tetrahedron.
     * @param q A point that defines the tetrahedron.
     * @param r A point that defines the tetrahedron.
     * @param s A point that defines the tetrahedron.
     */
    public V3D_Tetrahedron(V3D_Environment e, V3D_Vector offset, V3D_Vector p,
            V3D_Vector q, V3D_Vector r, V3D_Vector s) {
        super(e, offset);
        this.p = p;
        this.q = q;
        this.r = r;
        this.s = s;
    }

    /**
     * Create a new instance. {@code p}, {@code q}, {@code r}, {@code s} must
     * all be different and not coplanar. No test is done to check these things.
     *
     * @param p Used to set {@link #p}, {@link #e} and {@link #offset}.
     * @param q Used to set {@link #q}.
     * @param r Used to set {@link #r}.
     * @param s Used to set {@link #s}.
     */
    public V3D_Tetrahedron(V3D_Point p, V3D_Point q, V3D_Point r, V3D_Point s) {
        super(p.e, p.offset);
        this.p = new V3D_Vector(p.rel);
        V3D_Point qp = new V3D_Point(q);
        qp.setOffset(offset);
        this.q = qp.rel;
        V3D_Point rp = new V3D_Point(r);
        rp.setOffset(offset);
        this.r = rp.rel;
        V3D_Point sp = new V3D_Point(s);
        sp.setOffset(offset);
        this.s = sp.rel;
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
    @Override
    protected String toStringFields(String pad) {
        String st = super.toStringFields(pad);
        st += pad + ",\n";
        st += pad + "p=" + p.toString(pad) + "\n";
        st += pad + ",\n";
        st += pad + "q=" + q.toString(pad) + "\n";
        st += pad + ",\n";
        st += pad + "r=" + this.r.toString(pad) + "\n";
        st += pad + ",\n";
        st += pad + "s=" + s.toString(pad);// + "\n";
        return st;
    }

    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
//            en = pqr.getEnvelope(oom).union(qsr.getEnvelope(oom));
            en = getP(e.oom).getEnvelope()
                    .union(getQ(e.oom).getEnvelope())
                    .union(getR(e.oom).getEnvelope())
                    .union(getS(e.oom).getEnvelope());
        }
        return en;
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getP(int oom) {
        return new V3D_Point(e, offset, p);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getQ(int oom) {
        return new V3D_Point(e, offset, q);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getR(int oom) {
        return new V3D_Point(e, offset, r);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getS(int oom) {
        return new V3D_Point(e, offset, s);
    }

    /**
     * If {@code null} initialise {@link #pqr} and return it.
     *
     * @return {@link #pqr}
     */
    public V3D_Triangle getPqr() {
        if (pqr == null) {
            pqr = new V3D_Triangle(e, offset, p, q, r);
        }
        return pqr;
    }

    /**
     * If {@code null} initialise {@link #qsr} and return it.
     *
     * @return {@link #qsr}
     */
    public V3D_Triangle getQsr() {
        if (qsr == null) {
            qsr = new V3D_Triangle(e, offset, q, s, r);
        }
        return qsr;
    }

    /**
     * If {@code null} initialise {@link #spr} and return it.
     *
     * @return {@link #spr}
     */
    public V3D_Triangle getSpr() {
        if (spr == null) {
            spr = new V3D_Triangle(e, offset, s, p, r);
        }
        return spr;
    }

    /**
     * If {@code null} initialise {@link #psq} and return it.
     *
     * @return {@link #psq}
     */
    public V3D_Triangle getPsq() {
        if (psq == null) {
            psq = new V3D_Triangle(e, offset, p, s, q);
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
     * Calculate and return the volume.
     * https://en.wikipedia.org/wiki/Tetrahedron#Volume
     * This implementation is currently a bit rough and ready.
     * @param oom The Order of Magnitude for the precision of the calculation.
     */
    @Override
    public BigDecimal getVolume(int oom) {
        V3D_Triangle tpqr = getPqr();
        V3D_Point ts = getS(oom);
        int oomn6 = oom - 6;
        BigDecimal hd3 = new Math_BigRationalSqrt(
                tpqr.getPointOfProjectedIntersection(ts, oom)
                .getDistanceSquared(ts, oomn6), oomn6).getSqrt(oom).divide(3)
                .toBigDecimal(oomn6);
        return Math_BigDecimal.round(tpqr.getArea(oom - 3).multiply(hd3), oom);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The centroid point.
     */
    public V3D_Point getCentroid(int oom) {
        V3D_LineSegment a = new V3D_LineSegment(e,
                getPqr().getCentroid(oom).getVector(oom), 
                getQsr().getQ().getVector(oom));
        V3D_LineSegment b = new V3D_LineSegment(e,
                getPsq().getCentroid(oom).getVector(oom), 
                getQsr().getR().getVector(oom));
        return (V3D_Point) a.getIntersection(b, oom);
    }

    @Override
    public boolean isIntersectedBy(V3D_Point pt, int oom) {
        if (V3D_Plane.checkOnSameSide(pt, getP(oom), getQsr().getN(oom), oom)) {
            if (V3D_Plane.checkOnSameSide(pt, getQ(oom), getSpr().getN(oom), oom)) {
                if (V3D_Plane.checkOnSameSide(pt, getR(oom), getPsq().getN(oom), oom)) {
                    if (V3D_Plane.checkOnSameSide(pt, getS(oom), getPqr().getN(oom), oom)) {
                        return true;
                    }
                }
            }
        }
        if (getQsr().isIntersectedBy(pt, oom)) {
            return true;
        }
        if (getSpr().isIntersectedBy(pt, oom)) {
            return true;
        }
        if (getPsq().isIntersectedBy(pt, oom)) {
            return true;
        }
        if (getPqr().isIntersectedBy(pt, oom)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Triangle t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Point p, int oom) {
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
        p = p.add(offset, e.oom).subtract(this.offset, e.oom);
        q = q.add(offset, e.oom).subtract(this.offset, e.oom);
        r = r.add(offset, e.oom).subtract(this.offset, e.oom);
        s = s.add(offset, e.oom).subtract(this.offset, e.oom);
        this.offset = offset;
    }

    @Override
    public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta) {
        p = p.rotate(axisOfRotation, theta, e.bI, e.oom);
        q = q.rotate(axisOfRotation, theta, e.bI, e.oom);
        r = r.rotate(axisOfRotation, theta, e.bI, e.oom);
        s = s.rotate(axisOfRotation, theta, e.bI, e.oom);
        pqr = null;
        psq = null;
        spr = null;
        qsr = null;
        en = null;
    }

    @Override
    public boolean isIntersectedBy(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_Triangle t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_Geometry getIntersection(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Plane p, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
