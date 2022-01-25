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
import java.util.HashSet;
import java.util.Iterator;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

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
     * @param b To distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Line, int)}. The
     * value is ignored.
     * @return The V3D_Geometry.
     */
    public abstract V3D_Geometry getIntersection(V3D_LineSegment l, int oom, boolean b);

    /**
     * For getting the minimum distance to {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @return The minimum distance to {@code p}.
     */
    public abstract BigDecimal getDistance(V3D_Point p, int oom);

}
