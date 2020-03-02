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

    public static final BigDecimal ZERO = BigDecimal.ZERO;
    public static final BigDecimal ONE = BigDecimal.valueOf(1);
    public static final BigDecimal TWO = BigDecimal.valueOf(2);
    public static final BigDecimal THREE = BigDecimal.valueOf(3);
    public static final BigDecimal FOUR = BigDecimal.valueOf(4);
    public static final BigDecimal FIVE = BigDecimal.valueOf(5);
    public static final BigDecimal TEN = BigDecimal.TEN;
    
    public V3D_Test(V3D_Environment e) {
        super(e);
    }
}
