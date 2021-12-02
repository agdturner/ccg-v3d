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
package uk.ac.leeds.ccg.v3d.core;

import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Plane;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Vector;

/**
 * V3D_Environment
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Environment {

    /**
     * 0
     */
    public static final Math_BigRational P0 = Math_BigRational.ZERO;
    
    /**
     * 1
     */
    public static final Math_BigRational P1 = Math_BigRational.ONE;
    
    /**
     * 2
     */
    public static final Math_BigRational P2 = Math_BigRational.TWO;
    
    /**
     * 3
     */
    public static final Math_BigRational P3 = Math_BigRational.valueOf(3);
    
    /**
     * -1
     */
    public static final Math_BigRational N1 = Math_BigRational.ONE.negate();

    /**
     * The default Order of Magnitude.
     */
    public static final int DEFAULT_OOM = -3;
    /**
     * Creates a new instance.
     */
    public V3D_Environment(){}
}
