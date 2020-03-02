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
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.core.V3D_Object;

/**
 * V3D_Test
 * 
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_Test extends V3D_Object {

    public static final BigDecimal P0 = BigDecimal.ZERO;
    public static final BigDecimal P1 = BigDecimal.valueOf(1);
    public static final BigDecimal P2 = BigDecimal.valueOf(2);
    public static final BigDecimal P3 = BigDecimal.valueOf(3);
    public static final BigDecimal P4 = BigDecimal.valueOf(4);
    public static final BigDecimal P5 = BigDecimal.valueOf(5);
    public static final BigDecimal P10 = BigDecimal.TEN;
    public static final BigDecimal N0 = P0.negate();
    public static final BigDecimal N1 = P1.negate();
    public static final BigDecimal N2 = P2.negate();
    public static final BigDecimal N3 = P3.negate();
    public static final BigDecimal N4 = P4.negate();
    public static final BigDecimal N5 = P5.negate();
    public static final BigDecimal N10 = P10.negate();

    public final V3D_Point P1P1P1;
    public final V3D_Point P1P1P0;
    public final V3D_Point P1P1N1;
    public final V3D_Point P1P0P1;
    public final V3D_Point P1P0P0;
    public final V3D_Point P1P0N1;
    public final V3D_Point P1N1P1;
    public final V3D_Point P1N1P0;
    public final V3D_Point P1N1N1;
    
    public final V3D_Point P0P1P1;
    public final V3D_Point P0P1P0;
    public final V3D_Point P0P1N1;
    public final V3D_Point P0P0P1;
    public final V3D_Point P0P0P0;
    public final V3D_Point P0P0N1;
    public final V3D_Point P0N1P1;
    public final V3D_Point P0N1P0;
    public final V3D_Point P0N1N1;

    public final V3D_Point N1P1P1;
    public final V3D_Point N1P1P0;
    public final V3D_Point N1P1N1;
    public final V3D_Point N1P0P1;
    public final V3D_Point N1P0P0;
    public final V3D_Point N1P0N1;
    public final V3D_Point N1N1P1;
    public final V3D_Point N1N1P0;
    public final V3D_Point N1N1N1;
        

    
    
    public V3D_Test(V3D_Environment e) {
        super(e);
        
        P1P1P1 = new V3D_Point(e, P1, P1, P1);
        P1P1P0 = new V3D_Point(e, P1, P1, P0);
        P1P1N1 = new V3D_Point(e, P1, P1, N1);
        P1P0P1 = new V3D_Point(e, P1, P0, P1);
        P1P0P0 = new V3D_Point(e, P1, P0, P0);
        P1P0N1 = new V3D_Point(e, P1, P0, N1);
        P1N1P1 = new V3D_Point(e, P1, N1, P1);
        P1N1P0 = new V3D_Point(e, P1, N1, P0);
        P1N1N1 = new V3D_Point(e, P1, N1, N1);

        P0P1P1 = new V3D_Point(e, P0, P1, P1);
        P0P1P0 = new V3D_Point(e, P0, P1, P0);
        P0P1N1 = new V3D_Point(e, P0, P1, N1);
        P0P0P1 = new V3D_Point(e, P0, P0, P1);
        P0P0P0 = new V3D_Point(e, P0, P0, P0);
        P0P0N1 = new V3D_Point(e, P0, P0, N1);
        P0N1P1 = new V3D_Point(e, P0, N1, P1);
        P0N1P0 = new V3D_Point(e, P0, N1, P0);
        P0N1N1 = new V3D_Point(e, P0, N1, N1);

        N1P1P1 = new V3D_Point(e, N1, P1, P1);
        N1P1P0 = new V3D_Point(e, N1, P1, P0);
        N1P1N1 = new V3D_Point(e, N1, P1, N1);
        N1P0P1 = new V3D_Point(e, N1, P0, P1);
        N1P0P0 = new V3D_Point(e, N1, P0, P0);
        N1P0N1 = new V3D_Point(e, N1, P0, N1);
        N1N1P1 = new V3D_Point(e, N1, N1, P1);
        N1N1P0 = new V3D_Point(e, N1, N1, P0);
        N1N1N1 = new V3D_Point(e, N1, N1, N1);
    }
}
