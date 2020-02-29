/*
 * Copyright 2019 Andy Turner, University of Leeds.
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
package uk.ac.leeds.ccg.vector.geometry;

import java.math.RoundingMode;
import uk.ac.leeds.ccg.vector.core.Vector_Environment;
import uk.ac.leeds.ccg.vector.core.Vector_Object;

/**
 * For 2D geometrical objects.
 * 
 * @author Andy Turner
 * @version 1.0.0
 */
public abstract class Vector_Geometry2D extends Vector_Object {

    /**
     * Decimal places.
     */
    public int dp = 0;
    
    /**
     * RoundingMode
     */
    public RoundingMode rm = RoundingMode.FLOOR;

    public Vector_Geometry2D(Vector_Environment e) {
        super(e);
    }

    @Override
    public String toString() {
        return "dp=" + dp + ", rm=" + rm;
    }

    public abstract Vector_Envelope2D getEnvelope2D();
    
    public abstract void applyDecimalPlacePrecision();
}
