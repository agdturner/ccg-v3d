package uk.ac.leeds.ccg.v3d.geometry;

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;

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

    public V3D_Tetrahedrons(V3D_Tetrahedrons ts) {
        super(ts.env, ts.offset);
        tetrahedrons = new ArrayList<>();
        ts.tetrahedrons.forEach(x -> tetrahedrons.add(new V3D_Tetrahedron(x)));
    }
    
    /**
     * Creates a new instance.
     *
     * @param tetrahedrons One or more tetrahedron either in an array or input
     * individually.
     */
    public V3D_Tetrahedrons(V3D_Tetrahedron... tetrahedrons) {
        super(tetrahedrons[0].env, tetrahedrons[0].offset);
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
    public V3D_AABB getAABB(int oom, RoundingMode rm) {
        if (en == null) {
            en = tetrahedrons.stream().findAny().get().getAABB(oom, rm);
            tetrahedrons.forEach((V3D_Tetrahedron t) -> {
                en = en.union(t.getAABB(oom, rm), oom);
            });
        }
        return en;
    }

    @Override
    public V3D_Point[] getPointsArray(int oom, RoundingMode rm) {
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
     * @param rm The RoundingMode for any rounding.
     * @return The area of the triangle (rounded).
     */
    @Override
    public BigRational getArea(int oom, RoundingMode rm) {
        BigRational r = BigRational.ZERO;
        Iterator<V3D_Tetrahedron> ite = tetrahedrons.iterator();
        while (ite.hasNext()) {
            r = r.add(ite.next().getArea(oom, rm));
        }
        return r;
    }

    /**
     * @param oom The Order of Magnitude for the precision of the calculation.
     * @param rm The RoundingMode for any rounding.
     */
    @Override
    public BigRational getVolume(int oom, RoundingMode rm) {
        BigRational r = BigRational.ZERO;
        Iterator<V3D_Tetrahedron> ite = tetrahedrons.iterator();
        while (ite.hasNext()) {
            r = r.add(ite.next().getVolume(oom, rm));
        }
        return r;
    }

    @Override
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        if (en != null) {
            en.translate(v, oom, rm);
        }
        for (int i = 0; i < tetrahedrons.size(); i++) {
            tetrahedrons.get(i).translate(v, oom, rm);
        }
    }
    
    @Override
    public V3D_Tetrahedrons rotate(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd, 
            BigRational theta, int oom, RoundingMode rm) {
        theta = Math_AngleBigRational.normalise(theta, bd, oom, rm);
        if (theta.compareTo(BigRational.ZERO) == 0) {
            return new V3D_Tetrahedrons(this);
        } else {
            return rotateN(ray, uv, bd, theta, oom, rm);
        }
    }
    
    @Override
     public V3D_Tetrahedrons rotateN(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd, 
             BigRational theta, int oom, RoundingMode rm) {
        V3D_Tetrahedron[] rls = new V3D_Tetrahedron[tetrahedrons.size()];
        for (int i = 0; i < tetrahedrons.size(); i ++) {
            rls[0] = tetrahedrons.get(i).rotate(ray, uv, bd, theta, oom, rm);
        }
        return new V3D_Tetrahedrons(rls);
    }

    //@Override
    public boolean isIntersectedBy(V3D_AABB aabb, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
