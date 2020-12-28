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
 * @version 1.0.0
 */
public interface V3D_2DShape extends V3D_FiniteGeometry {

    /**
     * For calculating and returning the perimeter.
     * @param mps The minimum precision scale of the result.
     * @return The Perimeter.
     */
    public abstract BigDecimal getPerimeter(int mps);

    /**
     * For calculating and returning the area.
     * @param mps The minimum precision scale of the result.
     * @return The area.
     */
    public abstract BigDecimal getArea(int mps);
}
