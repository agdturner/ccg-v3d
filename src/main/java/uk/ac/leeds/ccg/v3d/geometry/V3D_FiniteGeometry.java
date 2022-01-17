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

/**
 * V3D_FiniteGeometry
 *
 * @author Andy Turner
 * @version 1.0
 */
public interface V3D_FiniteGeometry {

    /**
     * For getting the envelope of the geometry
     *
     * @return The V3D_Envelope.
     */
    public abstract V3D_Envelope getEnvelope();

    /**
     * For identifying if the geometry is intersected by point {@code p}.
     *
     * @param p The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public abstract boolean isIntersectedBy(V3D_Point p, int oom);

    /**
     * For identifying if the geometry is intersected by line {@code l}.
     *
     * @param l The line to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff the geometry is intersected by {@code l}.
     */
    public abstract boolean isIntersectedBy(V3D_Line l, int oom);

    /**
     * For identifying if the geometry is intersected by line {@code l}.
     *
     * @param l The line segment to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param b To distinguish this method from
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Line, int)}.
     * @return {@code true} iff the geometry is intersected by {@code l}.
     */
    public abstract boolean isIntersectedBy(V3D_LineSegment l, int oom, boolean b);

    /**
     * For identifying if the geometry is intersected by plane {@code p}.
     *
     * @param p The plane to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public abstract boolean isIntersectedBy(V3D_Plane p, int oom);
    
    /**
     * For getting the intersection between the geometry and the line {@code l}.
     *
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @return The V3D_Geometry.
     */
    public abstract V3D_Geometry getIntersection(V3D_Line l, int oom);

    /**
     * For getting the intersection between the geometry and the line segment
     * {@code l}.
     *
     * @param l The line segment to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param flag To distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Line, int)}. The
     * value is ignored.
     * @return The V3D_Geometry.
     */
    public abstract V3D_Geometry getIntersection(V3D_LineSegment l, int oom, boolean flag);

    /**
     * For getting the minimum distance to {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @return The minimum distance to {@code p}.
     */
    public abstract BigDecimal getDistance(V3D_Point p, int oom);

    /**
     * If p and q are equal then the point is returned otherwise the line
     * segment is returned
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @return either {@code p} or {@code new V3D_LineSegment(p, q)}
     */
    public static V3D_Geometry getGeometry(V3D_Point p, V3D_Point q) {
        if (p.equals(q)) {
            return p;
        } else {
            return new V3D_LineSegment(p, q);
        }
    }

    /**
     * If p, q and r are equal then the point is returned. If two of the points
     * are the same, then a line segment is returned. If all points are
     * different then a triangle is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @return either {@code p} or {@code new V3D_LineSegment(p, q)} or 
     * {@code new V3D_Triangle(p, q, r)}
     */
    public static V3D_Geometry getGeometry(V3D_Point p, V3D_Point q, 
            V3D_Point r) {
        if (p.equals(q)) {
            return getGeometry(p, r);
        } else {
            if (q.equals(r)) {
                return getGeometry(q, p);
            } else {
                if (r.equals(p)) {
                    return getGeometry(r, q);
                } else {
                    return new V3D_Triangle(p.e, V3D_Vector.ZERO, 
                            p.getVector(p.e.oom), 
                            q.getVector(p.e.oom), r.getVector(p.e.oom));
                }
            }
        }
    }
}
