package uk.ac.leeds.ccg.v3d.geometry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * A set of V3D_Tetrahedron.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_TetrahedronPoly extends V3D_Geometry implements V3D_3DShape {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    protected V3D_Envelope en;

    /**
     * The list of tetrahedron. Stored as a list so that the list can be 
     */
    protected final Set<V3D_Tetrahedron> tetrahedrons;

    /**
     * Creates a new instance.
     *
     * @param tetrahedrons What {@link #tetrahedrons} is set to.
     */
    public V3D_TetrahedronPoly(Set<V3D_Tetrahedron> tetrahedrons) {
        super(V3D_Vector.ZERO, V3D_Environment.DEFAULT_OOM);
        this.tetrahedrons = tetrahedrons;
    }

    /**
     * Creates a new instance.
     *
     * @param tetrahedrons One or more tetrahedron either in an array or input
     * individually.
     */
    public V3D_TetrahedronPoly(V3D_Tetrahedron... tetrahedrons) {
        super(V3D_Vector.ZERO, V3D_Environment.DEFAULT_OOM);
        this.tetrahedrons = new HashSet<>();
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
            en = tetrahedrons.stream().findAny().get().getEnvelope(oom);
            tetrahedrons.forEach((V3D_Tetrahedron t) -> {
                en = en.union(t.getEnvelope(oom));
            });
        }
        return en;
    }

    /**
     * @param v The vector to apply.
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return a new rectangle.
     */
    @Override
    public V3D_TetrahedronPoly apply(V3D_Vector v, int oom) {
        V3D_TetrahedronPoly r = new V3D_TetrahedronPoly(tetrahedrons.toArray(
                new V3D_Tetrahedron[tetrahedrons.size()]));
        for (int i = 0; i < r.tetrahedrons.size(); i++) {
            r.apply(oom, v);
        }
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The area of the triangle (rounded).
     */
    @Override
    public BigDecimal getArea(int oom) {
        BigDecimal r = BigDecimal.ZERO;
        Iterator<V3D_Tetrahedron> ite = tetrahedrons.iterator();
        while (ite.hasNext()) {
            r = r.add(ite.next().getArea(oom));
        }
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     */
    @Override
    public BigDecimal getVolume(int oom) {
        BigDecimal r = BigDecimal.ZERO;
        Iterator<V3D_Tetrahedron> ite = tetrahedrons.iterator();
        while (ite.hasNext()) {
            r = r.add(ite.next().getVolume(oom));
        }
        return r;
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
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
    
    
}
