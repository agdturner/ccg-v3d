/*
 * Copyright 2022 Centre for Computational Geography.
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
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 *
 * @author Andy Turner
 */
public class V3D_Angle {
    
    /**
     * Create a new instance.
     */
    public V3D_Angle(){}
    
    /**
     * Returns a normal angle in the range 0 to 2PI.
     * @param theta The angle to be normalised.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A normalised angle.
     */
    public static BigRational normalise(BigRational theta, int oom, 
            RoundingMode rm) {
        BigRational twoPi = BigRational.valueOf(
                V3D_Environment.bd.getPi(oom, rm)).multiply(2);
        BigRational r = theta;
        // Change a negative angle into a positive one.
        if (theta.compareTo(BigRational.ZERO) == -1) {
            int n = theta.divide(twoPi).subtract(1).intValue();
            r = theta.subtract(twoPi.multiply(n));
        } else {
            // Only rotate less than 2Pi radians.
            if (theta.compareTo(BigRational.ZERO) == 1) {
                int n = theta.divide(twoPi).intValue();
                r = theta.subtract(twoPi.multiply(n));
            }
        }
//        BigRational r = theta;
//        // Change a negative angle into a positive one.
//        while (r.compareTo(BigRational.ZERO) == -1) {
//            r = r.add(twoPi);
//        }
//        // Only rotate less than 2Pi radians.
//        while (r.compareTo(twoPi) == 1) {
//            r = r.subtract(twoPi);
//        }
        return r;
    }
    
}
