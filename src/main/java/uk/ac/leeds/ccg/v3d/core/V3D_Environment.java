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
     * Zero vector.
     */
    public static final V3D_Vector V0 = new V3D_Vector(P0, P0, P0);

    /**
     * The point at {@code <0,0,0>}.
     */
    public static final V3D_Point P0P0P0 = V3D_Point.ORIGIN;

    /**
     * The point at {@code <1,0,0>}.
     */
    public static final V3D_Point P1P0P0 = new V3D_Point(P1, P0, P0);

    /**
     * The point at {@code <0,1,0>}.
     */
    public static final V3D_Point P0P1P0 = new V3D_Point(P0, P1, P0);

    /**
     * The point at {@code <0,0,1>}.
     */
    public static final V3D_Point P0P0P1 = new V3D_Point(P0, P0, P1);

    /**
     * Unit vector based at the origin in the x axis direction.
     */
    public static final V3D_Vector i = new V3D_Vector(P1P0P0, DEFAULT_OOM);

    /**
     * Unit vector based at the origin in the y axis direction.
     */
    public static final V3D_Vector j = new V3D_Vector(P0P1P0, DEFAULT_OOM);

    /**
     * Unit vector based at the origin in the z axis direction.
     */
    public static final V3D_Vector k = new V3D_Vector(P0P0P1, DEFAULT_OOM);

    /**
     * The x axis.
     */
    public static final V3D_Line xAxis = new V3D_Line(P0P0P0, i, 0);
    
    /**
     * The y axis.
     */
    public static final V3D_Line yAxis = new V3D_Line(P0P0P0, j, DEFAULT_OOM);

    /**
     * The z axis.
     */
    public static final V3D_Line zAxis = new V3D_Line(P0P0P0, k, DEFAULT_OOM);

    /**
     * The x = 0 plane.
     */
    public static final V3D_Plane x0 = new V3D_Plane(P0P0P0, P0P1P0, P0P0P1, DEFAULT_OOM);

    /**
     * The y = 0 plane.
     */
    public static final V3D_Plane y0 = new V3D_Plane(P0P0P0, P1P0P0, P0P0P1, DEFAULT_OOM);

    /**
     * The z = 0 plane.
     */
    public static final V3D_Plane z0 = new V3D_Plane(P0P0P0, P1P0P0, P0P1P0, DEFAULT_OOM);
    
    /**
     * Creates a new instance.
     */
    public V3D_Environment(){}
}
