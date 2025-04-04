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
package uk.ac.leeds.ccg.v3d.geometry.d;

import java.io.Serializable;
import java.util.HashSet;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

/**
 * For a 2D Axis Aligned Bounding Box. These are wanted to calculate if there is
 * intersection/containment of finite geometries. General rectangles cannot be
 * used due to recursive complications.
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_AABB2D_d implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The environment.
     */
    public V3D_Environment_d env;

    /**
     * For storing the offset of this.
     */
    protected V3D_Vector_d offset;

    /**
     * For storing the left lower point.
     */
    protected V3D_Point_d ll;

    /**
     * For storing the left upper point.
     */
    protected V3D_Point_d lu;

    /**
     * For storing the right upper point.
     */
    protected V3D_Point_d uu;

    /**
     * For storing the right lower point.
     */
    protected V3D_Point_d ul;

    /**
     * The top/upper edge.
     */
    protected V3D_FiniteGeometry_d top;

    /**
     * The right edge.
     */
    protected V3D_FiniteGeometry_d right;

    /**
     * The bottom/lower edge.
     */
    protected V3D_FiniteGeometry_d bottom;

    /**
     * The left edge.
     */
    protected V3D_FiniteGeometry_d left;

    /**
     * For storing all the points.N.B {@link #ll}, {@link #lu}, {@link #uu},
     * {@link #lu} may all be the same.
     */
    protected HashSet<V3D_Point_d> pts;

    /**
     * For storing the top plane.
     */
    protected V3D_Plane_d tpl;

    /**
     * For storing the right plane.
     */
    protected V3D_Plane_d rpl;

    /**
     * For storing the bottom plane.
     */
    protected V3D_Plane_d bpl;

    /**
     * For storing the left plane.
     */
    protected V3D_Plane_d lpl;

    /**
     * Create a new instance.
     */
    public V3D_AABB2D_d() {
    }

    /**
     * @param e An envelope.
     */
    public V3D_AABB2D_d(V3D_AABB2D_d e) {
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
    public HashSet<V3D_Point_d> getPoints() {
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
    public abstract V3D_Point_d getll();

    /**
     * @return {@link #lu} setting it first if it is null.
     */
    public abstract V3D_Point_d getlu();

    /**
     * @return {@link #uu} setting it first if it is null.
     */
    public abstract V3D_Point_d getuu();

    /**
     * @return {@link #ul} setting it first if it is null.
     */
    public abstract V3D_Point_d getul();

    /**
     * @return the left of the envelope.
     */
    public abstract V3D_FiniteGeometry_d getLeft();

    /**
     * The left plane is orthogonal to the xPlane. With a normal pointing away.
     *
     * @return {@link #lpl} initialising first if it is {@code null}.
     */
    public abstract V3D_Plane_d getLeftPlane();

    /**
     * @return the right of the envelope.
     */
    public abstract V3D_FiniteGeometry_d getRight();

    /**
     * The right plane is orthogonal to the xPlane. With a normal pointing away.
     *
     * @return {@link #rpl} initialising first if it is {@code null}.
     */
    public abstract V3D_Plane_d getRightPlane();

    /**
     * @return the top of the envelope.
     */
    public abstract V3D_FiniteGeometry_d getTop();

    /**
     * The top plane is orthogonal to the xPlane. With a normal pointing away.
     *
     * @return {@link #tpl} initialising first if it is {@code null}.
     */
    public abstract V3D_Plane_d getTopPlane();

    /**
     * @return the bottom of the envelope.
     */
    public abstract V3D_FiniteGeometry_d getBottom();

    /**
     * The bottom plane is orthogonal to the xPlane. With a normal pointing
     * away.
     *
     * @return {@link #bpl} initialising first if it is {@code null}.
     */
    public abstract V3D_Plane_d getBottomPlane();

    /**
     * Translates this using {@code v}.
     *
     * @param v The vector of translation.
     */
    public void translate(V3D_Vector_d v) {
        offset = offset.add(v);
        pts = null;
        if (ll != null) {
            ll.translate(v);
        }
        if (lu != null) {
            lu.translate(v);
        }
        if (uu != null) {
            uu.translate(v);
        }
        if (ul != null) {
            ul.translate(v);
        }
        if (left != null) {
            left.translate(v);
        }
        if (top != null) {
            top.translate(v);
        }
        if (right != null) {
            right.translate(v);
        }
        if (bottom != null) {
            bottom.translate(v);
        }
        if (lpl != null) {
            lpl.translate(v);
        }
        if (tpl != null) {
            tpl.translate(v);
        }
        if (rpl != null) {
            rpl.translate(v);
        }
        if (bpl != null) {
            bpl.translate(v);
        }
    }

    /**
     * Calculate and return the approximate (or exact) centroid of the envelope.
     *
     * @return The approximate or exact centre of this.
     */
    public abstract V3D_Point_d getCentroid();

    /**
     * @param p The point to test if it is contained. It is assumed to be in the
     * same Y plane.
     * @return {@code} true iff {@code this} contains {@code p}.
     */
    public abstract boolean contains(V3D_Point_d p);

    /**
     * @param l The line to test for containment.
     * @return {@code true} if this contains {@code left}
     */
    public abstract boolean contains(V3D_LineSegment_d l);

    /**
     * @param p The point to test for intersection. It is assumed to be in the
     * same X plane.
     * @return {@code true} if this intersects with {@code p}
     */
    public abstract boolean intersects(V3D_Point_d p);

    /**
     * @param l The line to test for intersection. It is assumed to be in the
     * same X plane.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} if this intersects with {@code p}
     */
    public boolean intersects(V3D_Line_d l, double epsilon) {
        if (intersects(l.getP()) || intersects(l.getQ())) {
            return true;
        }
        if (getTop() instanceof V3D_Point_d tp) {
            if (l.intersects(tp, epsilon)) {
                return true;
            }
        } else {
            if (getIntersect((V3D_LineSegment_d) getTop(), l, epsilon) != null) {
                return true;
            }
        }
        if (getBottom() instanceof V3D_Point_d bbp) {
            if (l.intersects(bbp, epsilon)) {
                return true;
            }
        } else {
            if (getIntersect((V3D_LineSegment_d) getBottom(), l, epsilon) != null) {
                return true;
            }
        }
        if (getRight() instanceof V3D_Point_d rp) {
            if (l.intersects(rp, epsilon)) {
                return true;
            }
        } else {
            if (getIntersect((V3D_LineSegment_d) getRight(), l, epsilon) != null) {
                return true;
            }
        }
        if (getLeft() instanceof V3D_Point_d lp) {
            if (l.intersects(lp, epsilon)) {
                return true;
            }
        } else {
            if (getIntersect((V3D_LineSegment_d) getLeft(), l, epsilon) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the intersect {@code left} with {@code ls} where {@code ls} is a
     * side either {@link #top}, {@link #bottom}, {@link #left} or
     * {@link #right} when a line segment.
     *
     * @param ls The line segment to get the intersect with left. The line
     * segment must be lying in the same xPlane.
     * @param l The line to get intersection with this. The line must be lying
     * in the same xPlane.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return The intersection between {@code this} and {@code left}.
     */
    public abstract V3D_FiniteGeometry_d getIntersect(V3D_LineSegment_d ls,
            V3D_Line_d l, double epsilon);
}
