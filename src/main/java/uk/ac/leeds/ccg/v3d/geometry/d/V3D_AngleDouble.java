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
package uk.ac.leeds.ccg.v3d.geometry.d;

/**
 *
 * @author Andy Turner
 */
public class V3D_AngleDouble {

    /**
     * Create a new instance.
     */
    public V3D_AngleDouble(){}
    
    /**
     * Returns a normal angle in the range 0 to 2PI.
     *
     * @param theta The angle to be normalised.
     * @return A normalised angle.
     */
    public static double normalise(double theta) {
        double twoPi = Math.PI * 2d;
        // Change a negative angle into a positive one.
        while (theta < 0d) {
            theta = theta + twoPi;
        }
        // Only rotate less than 2Pi radians.
        while (theta > twoPi) {
            theta = theta - twoPi;
        }
        return theta;
    }
}
