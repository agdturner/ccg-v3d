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
package uk.ac.leeds.ccg.v3d;

import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Vector;

/**
 * V3D_Test
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_Test {

    public static final Math_BigRational P0 = Math_BigRational.ZERO;
    public static final Math_BigRational P1 = Math_BigRational.ONE;
    public static final Math_BigRational P2 = Math_BigRational.TWO;
    public static final Math_BigRational P3 = Math_BigRational.valueOf(3);
    public static final Math_BigRational P4 = Math_BigRational.valueOf(4);
    public static final Math_BigRational P5 = Math_BigRational.valueOf(5);
    public static final Math_BigRational P6 = Math_BigRational.valueOf(6);
    public static final Math_BigRational P7 = Math_BigRational.valueOf(7);
    public static final Math_BigRational P8 = Math_BigRational.valueOf(8);
    public static final Math_BigRational P9 = Math_BigRational.valueOf(9);
    public static final Math_BigRational P10 = Math_BigRational.TEN;
    public static final Math_BigRational P99 = Math_BigRational.valueOf(99);
    public static final Math_BigRational P100 = Math_BigRational.valueOf(100);
    public static final Math_BigRational P101 = Math_BigRational.valueOf(101);
    public static final Math_BigRational P999 = Math_BigRational.valueOf(999);
    public static final Math_BigRational P1000 = Math_BigRational.valueOf(1000);
    public static final Math_BigRational P1001 = Math_BigRational.valueOf(1001);
    public static final Math_BigRational P9999 = Math_BigRational.valueOf(9999);
    public static final Math_BigRational P10000 = Math_BigRational.valueOf(10000);
    public static final Math_BigRational P10001 = Math_BigRational.valueOf(10001);
    public static final Math_BigRational N0 = P0.negate();
    public static final Math_BigRational N1 = P1.negate();
    public static final Math_BigRational N2 = P2.negate();
    public static final Math_BigRational N3 = P3.negate();
    public static final Math_BigRational N4 = P4.negate();
    public static final Math_BigRational N5 = P5.negate();
    public static final Math_BigRational N6 = P6.negate();
    public static final Math_BigRational N7 = P7.negate();
    public static final Math_BigRational N8 = P8.negate();
    public static final Math_BigRational N9 = P9.negate();
    public static final Math_BigRational N10 = P10.negate();
    public static final Math_BigRational N99 = P99.negate();
    public static final Math_BigRational N100 = P100.negate();
    public static final Math_BigRational N101 = P101.negate();
    public static final Math_BigRational N999 = P999.negate();
    public static final Math_BigRational N1000 = P1000.negate();
    public static final Math_BigRational N1001 = P1001.negate();
    public static final Math_BigRational N9999 = P9999.negate();
    public static final Math_BigRational N10000 = P10000.negate();
    public static final Math_BigRational N10001 = P10001.negate();
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

    public V3D_Test() {}
}
