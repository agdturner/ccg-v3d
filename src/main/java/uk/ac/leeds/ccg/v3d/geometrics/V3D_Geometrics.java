/*
 * Copyright 2020 Centre for Computational Geography.
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
package uk.ac.leeds.ccg.v3d.geometrics;

import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;

/**
 * A class for geometrics.
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Geometrics {
    
    /**
     * @param points The points to test if they are collinear.
     * @return {@code true} iff all the points are collinear.
     */
    public static boolean isCollinear(int oom, V3D_Point... points) {
        if (points.length > 2) {
            V3D_Line l = new V3D_Line(points[0], points[1], oom);
            for (int i = 2; i < points.length; i++) {
                if (!l.isIntersectedBy(points[i])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    
}
