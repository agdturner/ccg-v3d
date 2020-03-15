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

import ch.obermuhlner.math.big.BigRational;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.generic.io.Generic_Path;
import uk.ac.leeds.ccg.generic.memory.Generic_MemoryManager;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Vector;
import uk.ac.leeds.ccg.v3d.io.V3D_Files;

/**
 * V3D_Environment
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Environment extends Generic_MemoryManager {

    private static final long serialVersionUID = 1L;
    
    /**
     * The origin at {@code <0,0,0>}.
     */
    public final V3D_Point origin;

    /**
     * The x axis.
     */
    public final V3D_Line xAxis;
   
    /**
     * The y axis.
     */
    public final V3D_Line yAxis;

    /**
     * The z axis.
     */
    public final V3D_Line zAxis;

    /**
     * Unit vector based at the origin in the x axis direction. 
     */
    public final V3D_Vector i;

    /**
     * Unit vector based at the origin in the y axis direction. 
     */
    public final V3D_Vector j;

    /**
     * Unit vector based at the origin in the z axis direction. 
     */
    public final V3D_Vector k;

    /**
     * Zero vector.
     */
    public final V3D_Vector zeroVector;

    /**
     * For code brevity.
     */
    public final BigRational P0 = BigRational.ZERO;
    public final BigRational P1 = BigRational.ONE;
    public final BigRational P2 = BigRational.valueOf(2);
    public final BigRational P3 = BigRational.valueOf(3);
    public final BigRational N1 = BigRational.ONE.negate();
    
    public Generic_Environment env;
    
    public V3D_Files files;

    /**
     * MathBigDecimal
     */
    public Math_BigDecimal bd;

    public V3D_Environment(Generic_Environment e) throws IOException, 
            Exception {
        this(e, e.files.getDir());
    }
    
    public V3D_Environment(Generic_Environment e, Generic_Path dir)
            throws IOException, Exception {
        super();
        this.env = e;
        origin = new V3D_Point(this, P0, P0, P0);
        xAxis = new V3D_Line(origin, new V3D_Point(this, P1, P0, P0), false);
        yAxis = new V3D_Line(origin, new V3D_Point(this, P0, P1, P0));
        zAxis = new V3D_Line(origin, new V3D_Point(this, P0, P0, P1));
        i = new V3D_Vector(new V3D_Point(this, P1, P0, P0));
        j = new V3D_Vector(new V3D_Point(this, P0, P1, P0));
        k = new V3D_Vector(new V3D_Point(this, P0, P0, P1));
        bd = new Math_BigDecimal();
        initMemoryReserve(Default_Memory_Threshold, env);
        files = new V3D_Files(new Generic_Defaults(Paths.get(dir.toString(),
                V3D_Strings.s_v3d)));
        zeroVector = new V3D_Vector(this, P0, P0, P0);
    }
            
    public BigDecimal getRounded_BigDecimal(BigDecimal toRoundBigDecimal,
            BigDecimal toRoundToBigDecimal) {
        int scale = toRoundToBigDecimal.scale();
        BigDecimal r = toRoundBigDecimal.setScale(scale - 1, RoundingMode.FLOOR);
        r = r.setScale(scale);
        r = r.add(toRoundToBigDecimal);
        return r;
    }

    @Override
    public boolean swapSomeData() throws IOException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean swapSomeData(boolean hoome) throws IOException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkAndMaybeFreeMemory() throws IOException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
