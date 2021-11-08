package uk.ac.leeds.ccg.v3d.geometry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Essentially this is just a collection of any V3D_Tetrahedron.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_TetrahedronPoly implements V3D_3DShape, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    protected V3D_Envelope en;

    /**
     * The list of tetrahedron. Stored as a list so that the list can be 
     */
    protected final List<V3D_Tetrahedron> tetrahedrons;

    /**
     * Create a new instance.
     */
    protected V3D_TetrahedronPoly() {
        tetrahedrons = new ArrayList<>();
    }

    /**
     * Creates a new instance.
     *
     * @param tetrahedrons What {@link #tetrahedrons} is set to.
     */
    public V3D_TetrahedronPoly(List<V3D_Tetrahedron> tetrahedrons) {
        this.tetrahedrons = tetrahedrons;
    }

    /**
     * Creates a new instance.
     *
     * @param tetrahedrons One or more tetrahedron either in an array or input
     * individually.
     */
    public V3D_TetrahedronPoly(V3D_Tetrahedron... tetrahedrons) {
        this.tetrahedrons = new ArrayList<>();
        this.tetrahedrons.addAll(Arrays.asList(tetrahedrons));
    }

    @Override
    public String toString() {
        String r;
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append("(");
        this.tetrahedrons.forEach(t -> {
            sb.append(t.toString()).append(", ");
        });
        r = sb.substring(0, sb.length() - 1);
        r += ")";
        return r;
    }

    @Override
    public V3D_Envelope getEnvelope(int oom) {
        if (en == null) {
            en = tetrahedrons.get(0).getEnvelope(oom);
            for (int i = 1; i < tetrahedrons.size(); i++) {
                en = en.union(tetrahedrons.get(i).getEnvelope(oom));
            }
        }
        return en;
    }

    /**
     * @param v The vector to apply.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return a new rectangle.
     */
    public V3D_TetrahedronPoly apply(V3D_Vector v, int oom) {
        V3D_Tetrahedron[] t = new V3D_Tetrahedron[tetrahedrons.size()];
        for (int i = 0; i < tetrahedrons.size(); i++) {
            t[i] = tetrahedrons.get(i).apply(v, oom);
        }
        return new V3D_TetrahedronPoly(t);
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The area of the triangle (rounded).
     */
    @Override
    public BigDecimal getArea(int oom) {
        BigDecimal r = BigDecimal.ZERO;
        for (int i = 0; i < tetrahedrons.size(); i++) {
            r = r.add(tetrahedrons.get(i).getArea(oom));
        }
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     */
    @Override
    public BigDecimal getVolume(int oom) {
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < tetrahedrons.size(); i++) {
            sum = sum.add(tetrahedrons.get(i).getVolume(oom));
        }
        return sum;
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
