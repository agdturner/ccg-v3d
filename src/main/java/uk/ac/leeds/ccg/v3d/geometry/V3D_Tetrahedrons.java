package uk.ac.leeds.ccg.v3d.geometry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import uk.ac.leeds.ccg.math.number.Math_BigRational;

/**
 * A set of V3D_Tetrahedron.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Tetrahedrons extends V3D_FiniteGeometry implements V3D_Volume {

    private static final long serialVersionUID = 1L;

    /**
     * The list of tetrahedron. Stored as a list so that the list can be 
     */
    protected final ArrayList<V3D_Tetrahedron> tetrahedrons;

    /**
     * Creates a new instance.
     *
     * @param tetrahedrons One or more tetrahedron either in an array or input
     * individually.
     */
    public V3D_Tetrahedrons(V3D_Tetrahedron... tetrahedrons) {
        super(tetrahedrons[0].offset);
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
     public V3D_Tetrahedrons rotate(V3D_Line axis, Math_BigRational theta,
             int oom, RoundingMode rm) {
        V3D_Tetrahedron[] rls = new V3D_Tetrahedron[tetrahedrons.size()];
        for (int i = 0; i < tetrahedrons.size(); i ++) {
            rls[0] = tetrahedrons.get(i).rotate(axis, theta, oom, rm);
        }
        return new V3D_Tetrahedrons(rls);
    }

}
