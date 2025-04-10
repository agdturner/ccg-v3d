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
package uk.ac.leeds.ccg.v3d.geometry.test;

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Vector;

/**
 * V3D_Test
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Test {
    
    public static final BigRational P0 = BigRational.ZERO;
    public static final BigRational P1 = BigRational.ONE;
    public static final BigRational P2 = BigRational.TWO;
    public static final BigRational P3 = BigRational.valueOf(3);
    public static final BigRational P4 = BigRational.valueOf(4);
    public static final BigRational P5 = BigRational.valueOf(5);
    public static final BigRational P6 = BigRational.valueOf(6);
    public static final BigRational P7 = BigRational.valueOf(7);
    public static final BigRational P8 = BigRational.valueOf(8);
    public static final BigRational P9 = BigRational.valueOf(9);
    public static final BigRational P10 = BigRational.TEN;
    public static final BigRational P99 = BigRational.valueOf(99);
    public static final BigRational P100 = BigRational.valueOf(100);
    public static final BigRational P101 = BigRational.valueOf(101);
    public static final BigRational P999 = BigRational.valueOf(999);
    public static final BigRational P1000 = BigRational.valueOf(1000);
    public static final BigRational P1001 = BigRational.valueOf(1001);
    public static final BigRational P9999 = BigRational.valueOf(9999);
    public static final BigRational P10000 = BigRational.valueOf(10000);
    public static final BigRational P10001 = BigRational.valueOf(10001);
    public static final BigRational P1E5 = P10.multiply(P10000);
    public static final BigRational P1E6 = P10.multiply(P1E5);
    public static final BigRational P1E7 = P10.multiply(P1E6);
    public static final BigRational P1E8 = P10.multiply(P1E7);
    public static final BigRational P1E9 = P10.multiply(P1E8);
    public static final BigRational P1E10 = P10.multiply(P1E9);
    public static final BigRational P1E11 = P10.multiply(P1E10);
    public static final BigRational P1E12 = P10.multiply(P1E11);
    public static final BigRational P1E13 = P10.multiply(P1E12);
    public static final BigRational P0_1 = BigRational.ONE.divide(BigRational.TEN);
    public static final BigRational P0_1E2 = P0_1.multiply(P0_1);
    public static final BigRational P0_1E3 = P0_1.multiply(P0_1E2);
    public static final BigRational P0_1E4 = P0_1.multiply(P0_1E3);
    public static final BigRational P0_1E5 = P0_1.multiply(P0_1E4);
    public static final BigRational P0_1E6 = P0_1.multiply(P0_1E5);
    public static final BigRational P0_1E7 = P0_1.multiply(P0_1E6);
    public static final BigRational P0_1E8 = P0_1.multiply(P0_1E7);
    public static final BigRational P0_1E9 = P0_1.multiply(P0_1E8);
    public static final BigRational P0_1E10 = P0_1.multiply(P0_1E9);
    public static final BigRational P0_1E11 = P0_1.multiply(P0_1E10);
    public static final BigRational P0_1E12 = P0_1.multiply(P0_1E11);
    public static final BigRational N0 = P0.negate();
    public static final BigRational N1 = P1.negate();
    public static final BigRational N2 = P2.negate();
    public static final BigRational N3 = P3.negate();
    public static final BigRational N4 = P4.negate();
    public static final BigRational N5 = P5.negate();
    public static final BigRational N6 = P6.negate();
    public static final BigRational N7 = P7.negate();
    public static final BigRational N8 = P8.negate();
    public static final BigRational N9 = P9.negate();
    public static final BigRational N10 = P10.negate();
    public static final BigRational N99 = P99.negate();
    public static final BigRational N100 = P100.negate();
    public static final BigRational N101 = P101.negate();
    public static final BigRational N999 = P999.negate();
    public static final BigRational N1000 = P1000.negate();
    public static final BigRational N1001 = P1001.negate();
    public static final BigRational N9999 = P9999.negate();
    public static final BigRational N10000 = P10000.negate();
    public static final BigRational N10001 = P10001.negate();
    public static final BigRational N1E5 = P1E5.negate();
    public static final BigRational N1E6 = P1E6.negate();
    public static final BigRational N1E7 = P1E7.negate();
    public static final BigRational N1E8 = P1E8.negate();
    public static final BigRational N1E9 = P1E9.negate();
    public static final BigRational N1E10 = P1E10.negate();
    public static final BigRational N1E11 = P1E11.negate();
    public static final BigRational N1E12 = P1E12.negate();
    public static final BigRational N0_1 = P0_1.negate();
    public static final BigRational N0_1E2 = P0_1E2.negate();
    public static final BigRational N0_1E3 = P0_1E3.negate();
    public static final BigRational N0_1E4 = P0_1E4.negate();
    public static final BigRational N0_1E5 = P0_1E5.negate();
    public static final BigRational N0_1E6 = P0_1E6.negate();
    public static final BigRational N0_1E7 = P0_1E7.negate();
    public static final BigRational N0_1E8 = P0_1E8.negate();
    public static final BigRational N0_1E9 = P0_1E9.negate();
    public static final BigRational N0_1E10 = P0_1E10.negate();
    public static final BigRational N0_1E11 = P0_1E11.negate();
    public static final BigRational N0_1E12 = P0_1E12.negate();
    // P2xx
    public static final V3D_Vector P2P2P2 = new V3D_Vector(P2, P2, P2);
    public static final V3D_Vector P2P2P1 = new V3D_Vector(P2, P2, P1);
    public static final V3D_Vector P2P2P0 = new V3D_Vector(P2, P2, P0);
    public static final V3D_Vector P2P2N1 = new V3D_Vector(P2, P2, N1);
    public static final V3D_Vector P2P2N2 = new V3D_Vector(P2, P2, N2);
    public static final V3D_Vector P2P1P2 = new V3D_Vector(P2, P1, P2);
    public static final V3D_Vector P2P1P1 = new V3D_Vector(P2, P1, P1);
    public static final V3D_Vector P2P1P0 = new V3D_Vector(P2, P1, P0);
    public static final V3D_Vector P2P1N1 = new V3D_Vector(P2, P1, N1);
    public static final V3D_Vector P2P1N2 = new V3D_Vector(P2, P1, N2);
    public static final V3D_Vector P2P0P2 = new V3D_Vector(P2, P0, P2);
    public static final V3D_Vector P2P0P1 = new V3D_Vector(P2, P0, P1);
    public static final V3D_Vector P2P0P0 = new V3D_Vector(P2, P0, P0);
    public static final V3D_Vector P2P0N1 = new V3D_Vector(P2, P0, N1);
    public static final V3D_Vector P2P0N2 = new V3D_Vector(P2, P0, N2);
    public static final V3D_Vector P2N1P2 = new V3D_Vector(P2, N1, P2);
    public static final V3D_Vector P2N1P1 = new V3D_Vector(P2, N1, P1);
    public static final V3D_Vector P2N1P0 = new V3D_Vector(P2, N1, P0);
    public static final V3D_Vector P2N1N1 = new V3D_Vector(P2, N1, N1);
    public static final V3D_Vector P2N1N2 = new V3D_Vector(P2, N1, N2);
    public static final V3D_Vector P2N2P2 = new V3D_Vector(P2, N2, P2);
    public static final V3D_Vector P2N2P1 = new V3D_Vector(P2, N2, P1);
    public static final V3D_Vector P2N2P0 = new V3D_Vector(P2, N2, P0);
    public static final V3D_Vector P2N2N1 = new V3D_Vector(P2, N2, N1);
    public static final V3D_Vector P2N2N2 = new V3D_Vector(P2, N2, N2);
    // P1xx
    public static final V3D_Vector P1P2P2 = new V3D_Vector(P1, P2, P2);
    public static final V3D_Vector P1P2P1 = new V3D_Vector(P1, P2, P1);
    public static final V3D_Vector P1P2P0 = new V3D_Vector(P1, P2, P0);
    public static final V3D_Vector P1P2N1 = new V3D_Vector(P1, P2, N1);
    public static final V3D_Vector P1P2N2 = new V3D_Vector(P1, P2, N2);
    public static final V3D_Vector P1P1P2 = new V3D_Vector(P1, P1, P2);
    public static final V3D_Vector P1P1P1 = new V3D_Vector(P1, P1, P1);
    public static final V3D_Vector P1P1P0 = new V3D_Vector(P1, P1, P0);
    public static final V3D_Vector P1P1N1 = new V3D_Vector(P1, P1, N1);
    public static final V3D_Vector P1P1N2 = new V3D_Vector(P1, P1, N2);
    public static final V3D_Vector P1P0P2 = new V3D_Vector(P1, P0, P2);
    public static final V3D_Vector P1P0P1 = new V3D_Vector(P1, P0, P1);
    public static final V3D_Vector P1P0P0 = new V3D_Vector(P1, P0, P0);
    public static final V3D_Vector P1P0N1 = new V3D_Vector(P1, P0, N1);
    public static final V3D_Vector P1P0N2 = new V3D_Vector(P1, P0, N2);
    public static final V3D_Vector P1N1P2 = new V3D_Vector(P1, N1, P2);
    public static final V3D_Vector P1N1P1 = new V3D_Vector(P1, N1, P1);
    public static final V3D_Vector P1N1P0 = new V3D_Vector(P1, N1, P0);
    public static final V3D_Vector P1N1N1 = new V3D_Vector(P1, N1, N1);
    public static final V3D_Vector P1N1N2 = new V3D_Vector(P1, N1, N2);
    public static final V3D_Vector P1N2P2 = new V3D_Vector(P1, N2, P2);
    public static final V3D_Vector P1N2P1 = new V3D_Vector(P1, N2, P1);
    public static final V3D_Vector P1N2P0 = new V3D_Vector(P1, N2, P0);
    public static final V3D_Vector P1N2N1 = new V3D_Vector(P1, N2, N1);
    public static final V3D_Vector P1N2N2 = new V3D_Vector(P1, N2, N2);
    // P0xx
    public static final V3D_Vector P0P2P2 = new V3D_Vector(P0, P2, P2);
    public static final V3D_Vector P0P2P1 = new V3D_Vector(P0, P2, P1);
    public static final V3D_Vector P0P2P0 = new V3D_Vector(P0, P2, P0);
    public static final V3D_Vector P0P2N1 = new V3D_Vector(P0, P2, N1);
    public static final V3D_Vector P0P2N2 = new V3D_Vector(P0, P2, N2);
    public static final V3D_Vector P0P1P2 = new V3D_Vector(P0, P1, P2);
    public static final V3D_Vector P0P1P1 = new V3D_Vector(P0, P1, P1);
    public static final V3D_Vector P0P1P0 = new V3D_Vector(P0, P1, P0);
    public static final V3D_Vector P0P1N1 = new V3D_Vector(P0, P1, N1);
    public static final V3D_Vector P0P1N2 = new V3D_Vector(P0, P1, N2);
    public static final V3D_Vector P0P0P2 = new V3D_Vector(P0, P0, P2);
    public static final V3D_Vector P0P0P1 = new V3D_Vector(P0, P0, P1);
    // ORIGIN
    public static final V3D_Vector P0P0P0 = new V3D_Vector(P0, P0, P0);
    public static final V3D_Vector P0P0N1 = new V3D_Vector(P0, P0, N1);
    public static final V3D_Vector P0P0N2 = new V3D_Vector(P0, P0, N2);
    public static final V3D_Vector P0N1P2 = new V3D_Vector(P0, N1, P2);
    public static final V3D_Vector P0N1P1 = new V3D_Vector(P0, N1, P1);
    public static final V3D_Vector P0N1P0 = new V3D_Vector(P0, N1, P0);
    public static final V3D_Vector P0N1N1 = new V3D_Vector(P0, N1, N1);
    public static final V3D_Vector P0N1N2 = new V3D_Vector(P0, N1, N2);
    public static final V3D_Vector P0N2P2 = new V3D_Vector(P0, N2, P2);
    public static final V3D_Vector P0N2P1 = new V3D_Vector(P0, N2, P1);
    public static final V3D_Vector P0N2P0 = new V3D_Vector(P0, N2, P0);
    public static final V3D_Vector P0N2N1 = new V3D_Vector(P0, N2, N1);
    public static final V3D_Vector P0N2N2 = new V3D_Vector(P0, N2, N2);
    // N1xx
    public static final V3D_Vector N1P2P2 = new V3D_Vector(N1, P2, P2);
    public static final V3D_Vector N1P2P1 = new V3D_Vector(N1, P2, P1);
    public static final V3D_Vector N1P2P0 = new V3D_Vector(N1, P2, P0);
    public static final V3D_Vector N1P2N1 = new V3D_Vector(N1, P2, N1);
    public static final V3D_Vector N1P2N2 = new V3D_Vector(N1, P2, N2);
    public static final V3D_Vector N1P1P2 = new V3D_Vector(N1, P1, P2);
    public static final V3D_Vector N1P1P1 = new V3D_Vector(N1, P1, P1);
    public static final V3D_Vector N1P1P0 = new V3D_Vector(N1, P1, P0);
    public static final V3D_Vector N1P1N1 = new V3D_Vector(N1, P1, N1);
    public static final V3D_Vector N1P1N2 = new V3D_Vector(N1, P1, N2);
    public static final V3D_Vector N1P0P2 = new V3D_Vector(N1, P0, P2);
    public static final V3D_Vector N1P0P1 = new V3D_Vector(N1, P0, P1);
    public static final V3D_Vector N1P0P0 = new V3D_Vector(N1, P0, P0);
    public static final V3D_Vector N1P0N1 = new V3D_Vector(N1, P0, N1);
    public static final V3D_Vector N1P0N2 = new V3D_Vector(N1, P0, N2);
    public static final V3D_Vector N1N1P2 = new V3D_Vector(N1, N1, P2);
    public static final V3D_Vector N1N1P1 = new V3D_Vector(N1, N1, P1);
    public static final V3D_Vector N1N1P0 = new V3D_Vector(N1, N1, P0);
    public static final V3D_Vector N1N1N1 = new V3D_Vector(N1, N1, N1);
    public static final V3D_Vector N1N1N2 = new V3D_Vector(N1, N1, N2);
    public static final V3D_Vector N1N2P2 = new V3D_Vector(N1, N2, P2);
    public static final V3D_Vector N1N2P1 = new V3D_Vector(N1, N2, P1);
    public static final V3D_Vector N1N2P0 = new V3D_Vector(N1, N2, P0);
    public static final V3D_Vector N1N2N1 = new V3D_Vector(N1, N2, N1);
    public static final V3D_Vector N1N2N2 = new V3D_Vector(N1, N2, N2);
   // N2xx
    public static final V3D_Vector N2P2P2 = new V3D_Vector(N2, P2, P2);
    public static final V3D_Vector N2P2P1 = new V3D_Vector(N2, P2, P1);
    public static final V3D_Vector N2P2P0 = new V3D_Vector(N2, P2, P0);
    public static final V3D_Vector N2P2N1 = new V3D_Vector(N2, P2, N1);
    public static final V3D_Vector N2P2N2 = new V3D_Vector(N2, P2, N2);
    public static final V3D_Vector N2P1P2 = new V3D_Vector(N2, P1, P2);
    public static final V3D_Vector N2P1P1 = new V3D_Vector(N2, P1, P1);
    public static final V3D_Vector N2P1P0 = new V3D_Vector(N2, P1, P0);
    public static final V3D_Vector N2P1N1 = new V3D_Vector(N2, P1, N1);
    public static final V3D_Vector N2P1N2 = new V3D_Vector(N2, P1, N2);
    public static final V3D_Vector N2P0P2 = new V3D_Vector(N2, P0, P2);
    public static final V3D_Vector N2P0P1 = new V3D_Vector(N2, P0, P1);
    public static final V3D_Vector N2P0P0 = new V3D_Vector(N2, P0, P0);
    public static final V3D_Vector N2P0N1 = new V3D_Vector(N2, P0, N1);
    public static final V3D_Vector N2P0N2 = new V3D_Vector(N2, P0, N2);
    public static final V3D_Vector N2N1P2 = new V3D_Vector(N2, N1, P2);
    public static final V3D_Vector N2N1P1 = new V3D_Vector(N2, N1, P1);
    public static final V3D_Vector N2N1P0 = new V3D_Vector(N2, N1, P0);
    public static final V3D_Vector N2N1N1 = new V3D_Vector(N2, N1, N1);
    public static final V3D_Vector N2N1N2 = new V3D_Vector(N2, N1, N2);
    public static final V3D_Vector N2N2P2 = new V3D_Vector(N2, N2, P2);
    public static final V3D_Vector N2N2P1 = new V3D_Vector(N2, N2, P1);
    public static final V3D_Vector N2N2P0 = new V3D_Vector(N2, N2, P0);
    public static final V3D_Vector N2N2N1 = new V3D_Vector(N2, N2, N1);
    public static final V3D_Vector N2N2N2 = new V3D_Vector(N2, N2, N2);

    static int oom = -3;
    static RoundingMode rm = RoundingMode.HALF_UP;
    static V3D_Environment env = new V3D_Environment(oom, rm);
    
    // P2xx
    public static final V3D_Point pP2P2P2 = new V3D_Point(env, P2, P2, P2);
    public static final V3D_Point pP2P2P1 = new V3D_Point(env, P2, P2, P1);
    public static final V3D_Point pP2P2P0 = new V3D_Point(env, P2, P2, P0);
    public static final V3D_Point pP2P2N1 = new V3D_Point(env, P2, P2, N1);
    public static final V3D_Point pP2P2N2 = new V3D_Point(env, P2, P2, N2);
    public static final V3D_Point pP2P1P2 = new V3D_Point(env, P2, P1, P2);
    public static final V3D_Point pP2P1P1 = new V3D_Point(env, P2, P1, P1);
    public static final V3D_Point pP2P1P0 = new V3D_Point(env, P2, P1, P0);
    public static final V3D_Point pP2P1N1 = new V3D_Point(env, P2, P1, N1);
    public static final V3D_Point pP2P1N2 = new V3D_Point(env, P2, P1, N2);
    public static final V3D_Point pP2P0P2 = new V3D_Point(env, P2, P0, P2);
    public static final V3D_Point pP2P0P1 = new V3D_Point(env, P2, P0, P1);
    public static final V3D_Point pP2P0P0 = new V3D_Point(env, P2, P0, P0);
    public static final V3D_Point pP2P0N1 = new V3D_Point(env, P2, P0, N1);
    public static final V3D_Point pP2P0N2 = new V3D_Point(env, P2, P0, N2);
    public static final V3D_Point pP2N1P2 = new V3D_Point(env, P2, N1, P2);
    public static final V3D_Point pP2N1P1 = new V3D_Point(env, P2, N1, P1);
    public static final V3D_Point pP2N1P0 = new V3D_Point(env, P2, N1, P0);
    public static final V3D_Point pP2N1N1 = new V3D_Point(env, P2, N1, N1);
    public static final V3D_Point pP2N1N2 = new V3D_Point(env, P2, N1, N2);
    public static final V3D_Point pP2N2P2 = new V3D_Point(env, P2, N2, P2);
    public static final V3D_Point pP2N2P1 = new V3D_Point(env, P2, N2, P1);
    public static final V3D_Point pP2N2P0 = new V3D_Point(env, P2, N2, P0);
    public static final V3D_Point pP2N2N1 = new V3D_Point(env, P2, N2, N1);
    public static final V3D_Point pP2N2N2 = new V3D_Point(env, P2, N2, N2);
    // P1xx
    public static final V3D_Point pP1P2P2 = new V3D_Point(env, P1, P2, P2);
    public static final V3D_Point pP1P2P1 = new V3D_Point(env, P1, P2, P1);
    public static final V3D_Point pP1P2P0 = new V3D_Point(env, P1, P2, P0);
    public static final V3D_Point pP1P2N1 = new V3D_Point(env, P1, P2, N1);
    public static final V3D_Point pP1P2N2 = new V3D_Point(env, P1, P2, N2);
    public static final V3D_Point pP1P1P2 = new V3D_Point(env, P1, P1, P2);
    public static final V3D_Point pP1P1P1 = new V3D_Point(env, P1, P1, P1);
    public static final V3D_Point pP1P1P0 = new V3D_Point(env, P1, P1, P0);
    public static final V3D_Point pP1P1N1 = new V3D_Point(env, P1, P1, N1);
    public static final V3D_Point pP1P1N2 = new V3D_Point(env, P1, P1, N2);
    public static final V3D_Point pP1P0P2 = new V3D_Point(env, P1, P0, P2);
    public static final V3D_Point pP1P0P1 = new V3D_Point(env, P1, P0, P1);
    public static final V3D_Point pP1P0P0 = new V3D_Point(env, P1, P0, P0);
    public static final V3D_Point pP1P0N1 = new V3D_Point(env, P1, P0, N1);
    public static final V3D_Point pP1P0N2 = new V3D_Point(env, P1, P0, N2);
    public static final V3D_Point pP1N1P2 = new V3D_Point(env, P1, N1, P2);
    public static final V3D_Point pP1N1P1 = new V3D_Point(env, P1, N1, P1);
    public static final V3D_Point pP1N1P0 = new V3D_Point(env, P1, N1, P0);
    public static final V3D_Point pP1N1N1 = new V3D_Point(env, P1, N1, N1);
    public static final V3D_Point pP1N1N2 = new V3D_Point(env, P1, N1, N2);
    public static final V3D_Point pP1N2P2 = new V3D_Point(env, P1, N2, P2);
    public static final V3D_Point pP1N2P1 = new V3D_Point(env, P1, N2, P1);
    public static final V3D_Point pP1N2P0 = new V3D_Point(env, P1, N2, P0);
    public static final V3D_Point pP1N2N1 = new V3D_Point(env, P1, N2, N1);
    public static final V3D_Point pP1N2N2 = new V3D_Point(env, P1, N2, N2);
    // P0xx
    public static final V3D_Point pP0P2P2 = new V3D_Point(env, P0, P2, P2);
    public static final V3D_Point pP0P2P1 = new V3D_Point(env, P0, P2, P1);
    public static final V3D_Point pP0P2P0 = new V3D_Point(env, P0, P2, P0);
    public static final V3D_Point pP0P2N1 = new V3D_Point(env, P0, P2, N1);
    public static final V3D_Point pP0P2N2 = new V3D_Point(env, P0, P2, N2);
    public static final V3D_Point pP0P1P2 = new V3D_Point(env, P0, P1, P2);
    public static final V3D_Point pP0P1P1 = new V3D_Point(env, P0, P1, P1);
    public static final V3D_Point pP0P1P0 = new V3D_Point(env, P0, P1, P0);
    public static final V3D_Point pP0P1N1 = new V3D_Point(env, P0, P1, N1);
    public static final V3D_Point pP0P1N2 = new V3D_Point(env, P0, P1, N2);
    public static final V3D_Point pP0P0P2 = new V3D_Point(env, P0, P0, P2);
    public static final V3D_Point pP0P0P1 = new V3D_Point(env, P0, P0, P1);
    // ORIGIN
    public static final V3D_Point pP0P0P0 = new V3D_Point(env, P0, P0, P0);
    public static final V3D_Point pP0P0N1 = new V3D_Point(env, P0, P0, N1);
    public static final V3D_Point pP0P0N2 = new V3D_Point(env, P0, P0, N2);
    public static final V3D_Point pP0N1P2 = new V3D_Point(env, P0, N1, P2);
    public static final V3D_Point pP0N1P1 = new V3D_Point(env, P0, N1, P1);
    public static final V3D_Point pP0N1P0 = new V3D_Point(env, P0, N1, P0);
    public static final V3D_Point pP0N1N1 = new V3D_Point(env, P0, N1, N1);
    public static final V3D_Point pP0N1N2 = new V3D_Point(env, P0, N1, N2);
    public static final V3D_Point pP0N2P2 = new V3D_Point(env, P0, N2, P2);
    public static final V3D_Point pP0N2P1 = new V3D_Point(env, P0, N2, P1);
    public static final V3D_Point pP0N2P0 = new V3D_Point(env, P0, N2, P0);
    public static final V3D_Point pP0N2N1 = new V3D_Point(env, P0, N2, N1);
    public static final V3D_Point pP0N2N2 = new V3D_Point(env, P0, N2, N2);
    // N1xx
    public static final V3D_Point pN1P2P2 = new V3D_Point(env, N1, P2, P2);
    public static final V3D_Point pN1P2P1 = new V3D_Point(env, N1, P2, P1);
    public static final V3D_Point pN1P2P0 = new V3D_Point(env, N1, P2, P0);
    public static final V3D_Point pN1P2N1 = new V3D_Point(env, N1, P2, N1);
    public static final V3D_Point pN1P2N2 = new V3D_Point(env, N1, P2, N2);
    public static final V3D_Point pN1P1P2 = new V3D_Point(env, N1, P1, P2);
    public static final V3D_Point pN1P1P1 = new V3D_Point(env, N1, P1, P1);
    public static final V3D_Point pN1P1P0 = new V3D_Point(env, N1, P1, P0);
    public static final V3D_Point pN1P1N1 = new V3D_Point(env, N1, P1, N1);
    public static final V3D_Point pN1P1N2 = new V3D_Point(env, N1, P1, N2);
    public static final V3D_Point pN1P0P2 = new V3D_Point(env, N1, P0, P2);
    public static final V3D_Point pN1P0P1 = new V3D_Point(env, N1, P0, P1);
    public static final V3D_Point pN1P0P0 = new V3D_Point(env, N1, P0, P0);
    public static final V3D_Point pN1P0N1 = new V3D_Point(env, N1, P0, N1);
    public static final V3D_Point pN1P0N2 = new V3D_Point(env, N1, P0, N2);
    public static final V3D_Point pN1N1P2 = new V3D_Point(env, N1, N1, P2);
    public static final V3D_Point pN1N1P1 = new V3D_Point(env, N1, N1, P1);
    public static final V3D_Point pN1N1P0 = new V3D_Point(env, N1, N1, P0);
    public static final V3D_Point pN1N1N1 = new V3D_Point(env, N1, N1, N1);
    public static final V3D_Point pN1N1N2 = new V3D_Point(env, N1, N1, N2);
    public static final V3D_Point pN1N2P2 = new V3D_Point(env, N1, N2, P2);
    public static final V3D_Point pN1N2P1 = new V3D_Point(env, N1, N2, P1);
    public static final V3D_Point pN1N2P0 = new V3D_Point(env, N1, N2, P0);
    public static final V3D_Point pN1N2N1 = new V3D_Point(env, N1, N2, N1);
    public static final V3D_Point pN1N2N2 = new V3D_Point(env, N1, N2, N2);
    // N2xx
    public static final V3D_Point pN2P2P2 = new V3D_Point(env, N2, P2, P2);
    public static final V3D_Point pN2P2P1 = new V3D_Point(env, N2, P2, P1);
    public static final V3D_Point pN2P2P0 = new V3D_Point(env, N2, P2, P0);
    public static final V3D_Point pN2P2N1 = new V3D_Point(env, N2, P2, N1);
    public static final V3D_Point pN2P2N2 = new V3D_Point(env, N2, P2, N2);
    public static final V3D_Point pN2P1P2 = new V3D_Point(env, N2, P1, P2);
    public static final V3D_Point pN2P1P1 = new V3D_Point(env, N2, P1, P1);
    public static final V3D_Point pN2P1P0 = new V3D_Point(env, N2, P1, P0);
    public static final V3D_Point pN2P1N1 = new V3D_Point(env, N2, P1, N1);
    public static final V3D_Point pN2P1N2 = new V3D_Point(env, N2, P1, N2);
    public static final V3D_Point pN2P0P2 = new V3D_Point(env, N2, P0, P2);
    public static final V3D_Point pN2P0P1 = new V3D_Point(env, N2, P0, P1);
    public static final V3D_Point pN2P0P0 = new V3D_Point(env, N2, P0, P0);
    public static final V3D_Point pN2P0N1 = new V3D_Point(env, N2, P0, N1);
    public static final V3D_Point pN2P0N2 = new V3D_Point(env, N2, P0, N2);
    public static final V3D_Point pN2N1P2 = new V3D_Point(env, N2, N1, P2);
    public static final V3D_Point pN2N1P1 = new V3D_Point(env, N2, N1, P1);
    public static final V3D_Point pN2N1P0 = new V3D_Point(env, N2, N1, P0);
    public static final V3D_Point pN2N1N1 = new V3D_Point(env, N2, N1, N1);
    public static final V3D_Point pN2N1N2 = new V3D_Point(env, N2, N1, N2);
    public static final V3D_Point pN2N2P2 = new V3D_Point(env, N2, N2, P2);
    public static final V3D_Point pN2N2P1 = new V3D_Point(env, N2, N2, P1);
    public static final V3D_Point pN2N2P0 = new V3D_Point(env, N2, N2, P0);
    public static final V3D_Point pN2N2N1 = new V3D_Point(env, N2, N2, N1);
    public static final V3D_Point pN2N2N2 = new V3D_Point(env, N2, N2, N2);
    
    public V3D_Test() {}
}
