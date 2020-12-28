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

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;

/**
 * V3D_FiniteGeometry
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public interface V3D_2DShape extends V3D_FiniteGeometry {

    /**
     * For calculating and returning the perimeter.
     * @param precision The precision the result is rounded to if necessary.
     * @param rm The RoundingMode used to round the result if necessary.
     * @return The Perimeter.
     */
    public abstract BigRational getPerimeter(int precision, RoundingMode rm);

    /**
     * For calculating and returning the area.
     * @param precision The precision the result is rounded to if necessary.
     * @param rm The RoundingMode used to round the result if necessary.
     * @return The area.
     */
    public abstract BigRational getArea(int precision, RoundingMode rm);
}
