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
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.number.Math_BigRational;

/**
 * V3D_FiniteGeometry
 *
 * @author Andy Turner
 * @version 1.0
 */
public interface V3D_Distance {
    
    /**
     * Get the distance to {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance to {@code p}.
     */
    public abstract BigDecimal getDistance(V3D_Point p, int oom, 
            RoundingMode rm);

    /**
     * Get the distance squared to {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared to {@code p}.
     */
    public abstract Math_BigRational getDistanceSquared(V3D_Point p, int oom,
            RoundingMode rm);

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public abstract BigDecimal getDistance(V3D_Line l, int oom, 
            RoundingMode rm);

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code l}.
     */
    public abstract Math_BigRational getDistanceSquared(V3D_Line l, int oom, 
            RoundingMode rm);

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code l}.
     */
    public abstract BigDecimal getDistance(V3D_LineSegment l, int oom, 
            RoundingMode rm);

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code l}.
     */
    public abstract Math_BigRational getDistanceSquared(V3D_LineSegment l, 
            int oom, RoundingMode rm);

    /**
     * Get the minimum distance to {@code p}.
     *
     * @param p A plane.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code p}.
     */
    public abstract BigDecimal getDistance(V3D_Plane p, int oom, 
            RoundingMode rm);

    /**
     * Get the minimum distance squared to {@code p}.
     *
     * @param p A plane.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code p}.
     */
    public abstract Math_BigRational getDistanceSquared(V3D_Plane p, int oom, 
            RoundingMode rm);

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code t}.
     */
    public abstract BigDecimal getDistance(V3D_Triangle t, int oom, 
            RoundingMode rm);
    
    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code t}.
     */
    public abstract Math_BigRational getDistanceSquared(V3D_Triangle t, int oom, 
            RoundingMode rm);
    
    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A tetrahedron.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code t}.
     */
    public abstract BigDecimal getDistance(V3D_Tetrahedron t, int oom, 
            RoundingMode rm);
    
    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A tetrahedron.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code t}.
     */
    public abstract Math_BigRational getDistanceSquared(V3D_Tetrahedron t, 
            int oom, RoundingMode rm);
}
