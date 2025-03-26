/*
 * Copyright 2025 Andy Turner, University of Leeds.
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
import java.math.RoundingMode;
import java.util.HashSet;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * For a 2D Axis Aligned Bounding Box. These are wanted to calculate if there is
 * intersection/containment of finite geometries. General rectangles cannot 
 * be used due to recursive complications.
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_AABB2D implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The environment.
     */
    public V3D_Environment env;

    /**
     * For storing the offset of this.
     */
    protected V3D_Vector offset;

    /**
     * For storing the left lower point.
     */
    protected V3D_Point ll;

    /**
     * For storing the left upper point.
     */
    protected V3D_Point lu;

    /**
     * For storing the right upper point.
     */
    protected V3D_Point uu;

    /**
     * For storing the right lower point.
     */
    protected V3D_Point ul;

    /**
     * The top/upper edge.
     */
    protected V3D_FiniteGeometry top;

    /**
     * The right edge.
     */
    protected V3D_FiniteGeometry right;

    /**
     * The bottom/lower edge.
     */
    protected V3D_FiniteGeometry bottom;

    /**
     * The left edge.
     */
    protected V3D_FiniteGeometry left;

    /**
     * For storing all the points.N.B {@link #ll}, {@link #lu}, {@link #uu},
     * {@link #lu} may all be the same.
     */
    protected HashSet<V3D_Point> pts;

    /**
     * For storing the top plane.
     */
    protected V3D_Plane tpl;

    /**
     * For storing the right plane.
     */
    protected V3D_Plane rpl;

    /**
     * For storing the bottom plane.
     */
    protected V3D_Plane bpl;

    /**
     * For storing the left plane.
     */
    protected V3D_Plane lpl;
    
    /**
     * Create a new instance.
     */
    public V3D_AABB2D() {}

    /**
     * @param e An envelope.
     */
    public V3D_AABB2D(V3D_AABB2D e) {
        env = e.env;
        offset = e.offset;
        ll = e.ll;
        lu = e.lu;
        uu = e.uu;
        ul = e.ul;
        left = e.left;
        right = e.right;
        bottom = e.bottom;
        top = e.top;
        pts = e.pts;
    }
    
    /**
     * @return {@link #pts} initialising first if it is null.
     */
    public HashSet<V3D_Point> getPoints() {
        if (pts == null) {
            pts = new HashSet<>(4);
            pts.add(getll());
            pts.add(getlu());
            pts.add(getuu());
            pts.add(getul());
        }
        return pts;
    }

    /**
     * @return {@link #ll} setting it first if it is null.
     */
    public abstract V3D_Point getll();

    /**
     * @return {@link #lu} setting it first if it is null.
     */
    public abstract V3D_Point getlu();

    /**
     * @return {@link #uu} setting it first if it is null.
     */
    public abstract V3D_Point getuu();

    /**
     * @return {@link #ul} setting it first if it is null.
     */
    public abstract V3D_Point getul();

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #left} initialising it first if it is {@code null}.
     */
    public abstract V3D_FiniteGeometry getLeft(int oom, RoundingMode rm);

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #lpl} initialising first if it is {@code null}.
     */
    public abstract V3D_Plane getLeftPlane(int oom, RoundingMode rm);

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #right} initialising it first if it is {@code null}.
     */
    public abstract V3D_FiniteGeometry getRight(int oom, RoundingMode rm);

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #rpl} initialising first if it is {@code null}.
     */
    public abstract V3D_Plane getRightPlane(int oom, RoundingMode rm);

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #top} initialising it first if it is {@code null}.
     */
    public abstract V3D_FiniteGeometry getTop(int oom, RoundingMode rm);

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #tpl} initialising first if it is {@code null}.
     */
    public abstract V3D_Plane getTopPlane(int oom, RoundingMode rm);

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #bottom} initialising it first if it is {@code null}.
     */
    public abstract V3D_FiniteGeometry getBottom(int oom, RoundingMode rm);

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@link #bpl} initialising first if it is {@code null}.
     */
    public abstract V3D_Plane getBottomPlane(int oom, RoundingMode rm);

    /**
     * Translates this using {@code v}.
     *
     * @param v The vector of translation.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        offset = offset.add(v, oom, rm);
        pts = null;
        if (ll != null) {
            ll.translate(v, oom, rm);
        }
        if (lu != null) {
            lu.translate(v, oom, rm);
        }
        if (uu != null) {
            uu.translate(v, oom, rm);
        }
        if (ul != null) {
            ul.translate(v, oom, rm);
        }
        if (left != null) {
            left.translate(v, oom, rm);
        }
        if (top != null) {
            top.translate(v, oom, rm);
        }
        if (right != null) {
            right.translate(v, oom, rm);
        }
        if (bottom != null) {
            bottom.translate(v, oom, rm);
        }
//        xMax = xMax.add(v.getDX(oom, rm));
//        xMin = xMin.add(v.getDX(oom, rm));
//        yMax = yMax.add(v.getDY(oom, rm));
//        yMin = yMin.add(v.getDY(oom, rm));
    }

    /**
     * Calculate and return the approximate (or exact) centroid of the envelope.
     *
     * @param oom The Order of Magnitude for the precision.
     * @return The approximate or exact centre of this.
     */
    public abstract V3D_Point getCentroid(int oom);

    /**
     * @param p The point to test if it is contained. It is assumed to be in the
     * same Y plane.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code} true iff {@code this} contains {@code p}.
     */
    public abstract boolean contains(V3D_Point p, int oom);

    /**
     * @param l The line to test for containment.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this contains {@code left}
     */
    public abstract boolean contains(V3D_LineSegment l, int oom, RoundingMode rm);

    /**
     * @param p The point to test for intersection. It is assumed to be in the
     * same X plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this intersects with {@code p}
     */
    public abstract boolean intersects(V3D_Point p, int oom, RoundingMode rm);

    /**
     * @param l The line to test for intersection. It is assumed
     * to be in the same plane.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return {@code true} if this intersects with {@code p}
     */
    public boolean intersects(V3D_Line l, int oom, RoundingMode rm) {
        if (intersects(l.getP(), oom, rm)) {
                return true;
            }
            if (intersects(l.getQ(oom, rm), oom, rm)) {
                return true;
            }
        if (getTop(oom, rm) instanceof V3D_Point tp) {
            if (l.intersects(tp, oom, rm)) {
                return true;
            }
        } else {
            if (getIntersect((V3D_LineSegment) getTop(oom, rm), l, oom, rm) != null) {
                return true;
            }
        }
        if (getBottom(oom, rm) instanceof V3D_Point bbp) {
            if (l.intersects(bbp, oom, rm)) {
                return true;
            }
        } else {
            if (getIntersect((V3D_LineSegment) getBottom(oom, rm), l, oom, rm) != null) {
                return true;
            }
        }
        if (getRight(oom, rm) instanceof V3D_Point rp) {
            if (l.intersects(rp, oom, rm)) {
                return true;
            }
        } else {
            if (getIntersect((V3D_LineSegment) getRight(oom, rm), l, oom, rm) != null) {
                return true;
            }
        }
        if (getLeft(oom, rm) instanceof V3D_Point lp) {
            if (l.intersects(lp, oom, rm)) {
                return true;
            }
        } else {
            if (getIntersect((V3D_LineSegment) getLeft(oom, rm), l, oom, rm) != null) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets the intersect {@code left} with {@code ls} where {@code ls} is a side 
     * either {@link #top}, {@link #bottom}, {@link #left} or {@link #right} when a 
     * line segment.
     *
     * @param ls The line segment to get the intersect with left. The line segment 
 must be lying in the same plane. 
     * @param l The line to get intersection with this. The line must be lying 
     * in the same plane. 
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The intersection between {@code this} and {@code left}.
     */
    public abstract V3D_FiniteGeometry getIntersect(V3D_LineSegment ls, 
            V3D_Line l, int oom, RoundingMode rm);
}
