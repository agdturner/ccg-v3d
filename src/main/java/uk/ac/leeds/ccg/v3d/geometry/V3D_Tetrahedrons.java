package uk.ac.leeds.ccg.v3d.geometry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import uk.ac.leeds.ccg.math.number.Math_BigRational;

/**
 * A set of V3D_Tetrahedron.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Tetrahedrons extends V3D_FiniteGeometry implements V3D_Volume,
        Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The list of tetrahedron. Stored as a list so that the list can be 
     */
    protected final Set<V3D_Tetrahedron> tetrahedrons;

    /**
     * Creates a new instance.
     *
     * @param tetrahedrons What {@link #tetrahedrons} is set to.
     */
    public V3D_Tetrahedrons(Set<V3D_Tetrahedron> tetrahedrons) {
        super(tetrahedrons.stream().findFirst().get().e, tetrahedrons.stream().findFirst().get().offset);
        this.tetrahedrons = tetrahedrons;
    }

    /**
     * Creates a new instance.
     *
     * @param tetrahedrons One or more tetrahedron either in an array or input
     * individually.
     */
    public V3D_Tetrahedrons(V3D_Tetrahedron... tetrahedrons) {
        super(tetrahedrons[0].e, tetrahedrons[0].offset);
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
    public V3D_Envelope getEnvelope(int oom, RoundingMode rm) {
        if (en == null) {
            en = tetrahedrons.stream().findAny().get().getEnvelope(oom, rm);
            tetrahedrons.forEach((V3D_Tetrahedron t) -> {
                en = en.union(t.getEnvelope(oom, rm), oom, rm);
            });
        }
        return en;
    }

    @Override
    public V3D_Point[] getPoints(int oom, RoundingMode rm) {
        int np = tetrahedrons.size() * 4;
        V3D_Point[] r = new V3D_Point[np];
        int i = 0;
        for (var x: tetrahedrons) {
            r[i] = x.getP();
            i ++;
            r[i] = x.getQ();
            i ++;
            r[i] = x.getR();
            i ++;
            r[i] = x.getS();
        }
        return r;
    }
    
    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @return The area of the triangle (rounded).
     */
    @Override
    public BigDecimal getArea(int oom, RoundingMode rm) {
        BigDecimal r = BigDecimal.ZERO;
        Iterator<V3D_Tetrahedron> ite = tetrahedrons.iterator();
        while (ite.hasNext()) {
            r = r.add(ite.next().getArea(oom, rm));
        }
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     */
    @Override
    public BigDecimal getVolume(int oom, RoundingMode rm) {
        BigDecimal r = BigDecimal.ZERO;
        Iterator<V3D_Tetrahedron> ite = tetrahedrons.iterator();
        while (ite.hasNext()) {
            r = r.add(ite.next().getVolume(oom, rm));
        }
        return r;
    }

//    @Override
//    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public BigDecimal getDistance(V3D_Point p, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Point p, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Line l, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_Point p, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_Line l, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Line l, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment l, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
     public void rotate(V3D_Vector axisOfRotation, Math_BigRational theta,
             int oom, RoundingMode rm) {
        for (V3D_Tetrahedron t : tetrahedrons) {
            t.rotate(axisOfRotation, theta, oom, rm);
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Plane p, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_Triangle t, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Plane p, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Triangle t, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Line l, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_LineSegment l, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Plane p, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Plane p, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Triangle t, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Triangle t, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIntersectedBy(V3D_Ray l, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V3D_FiniteGeometry getIntersection(V3D_Ray r, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getDistance(V3D_Ray r, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Math_BigRational getDistanceSquared(V3D_Ray l, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
