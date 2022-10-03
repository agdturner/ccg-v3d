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

import java.io.Serializable;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigInteger;
import uk.ac.leeds.ccg.math.number.Math_BigRational;

/**
 * V3D_Environment
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Environment implements Serializable {

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
     * An instance that helps with calculations involving Taylor series.
     */
    public final Math_BigInteger bI;
    
    /**
     * The default Order of Magnitude.
     */
    public static final int DEFAULT_OOM = -3;
    
    /**
     * The Order of Magnitude for the precision.
     */
    public int oom;
    
    /**
     * The default Order of Magnitude.
     */
    public static final RoundingMode DEFAULT_RM = RoundingMode.HALF_UP;

    /**
     * The RoundingMode for the precision.
     */
    public RoundingMode rm;
    
    /**
     * Creates a new instance.
     */
    public V3D_Environment(){
        bI = new Math_BigInteger();
        oom = DEFAULT_OOM;
        rm = DEFAULT_RM;
    }
    
    /**
     * Creates a new instance.
     *
     * @param bI What {@link #bI} is set to.
     * @param oom What {@link #oom} is set to.
     * @param rm What {@link #rm} is set to.
     */
    public V3D_Environment(Math_BigInteger bI, int oom, RoundingMode rm) {
        this.bI = bI;
        this.oom = oom;
        this.rm = rm;
    }
    
    /**
     * @param pad The padding.
     * @return A padded description.
     */
    public String toStringFields(String pad) {
        return pad + "oom=" + oom;
    }
    
}
