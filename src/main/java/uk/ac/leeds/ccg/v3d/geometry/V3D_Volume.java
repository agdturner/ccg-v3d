/*
 * Copyright 2021 Centre for Computational Geography.
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
import uk.ac.leeds.ccg.math.number.Math_BigRational;

/**
 * V3D_Volume
 * 
 * @author Andy Turner
 */
public interface V3D_Volume extends Serializable {
    
    /**
     * For calculating and returning the area.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The area.
     */
    public abstract Math_BigRational getArea(int oom, RoundingMode rm);
    
    /**
     * For calculating and returning the perimeter.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The Perimeter.
     */
    public abstract Math_BigRational getVolume(int oom, RoundingMode rm);
    
}
