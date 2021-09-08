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
import java.nio.file.Paths;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.generic.io.Generic_Path;
import uk.ac.leeds.ccg.generic.memory.Generic_MemoryManager;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Plane;
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
     * 0
     */
    public static final BigRational P0 = BigRational.ZERO;
    
    /**
     * 1
     */
    public static final BigRational P1 = BigRational.ONE;
    
    /**
     * 2
     */
    public static final BigRational P2 = BigRational.TWO;
    
    /**
     * 3
     */
    public static final BigRational P3 = BigRational.valueOf(3);
    
    /**
     * -1
     */
    public static final BigRational N1 = BigRational.ONE.negate();

    /**
     * Zero vector.
     */
    public static final V3D_Vector V0 = new V3D_Vector(P0, P0, P0, -1);

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
    public static final V3D_Vector i = new V3D_Vector(P1P0P0, -1);

    /**
     * Unit vector based at the origin in the y axis direction.
     */
    public static final V3D_Vector j = new V3D_Vector(P0P1P0, -1);

    /**
     * Unit vector based at the origin in the z axis direction.
     */
    public static final V3D_Vector k = new V3D_Vector(P0P0P1, -1);

    /**
     * The x axis.
     */
    public static final V3D_Line xAxis = new V3D_Line(P0P0P0, i);
    
    /**
     * The y axis.
     */
    public static final V3D_Line yAxis = new V3D_Line(P0P0P0, j);

    /**
     * The z axis.
     */
    public static final V3D_Line zAxis = new V3D_Line(P0P0P0, k);

    /**
     * The x = 0 plane.
     */
    public static final V3D_Plane x0 = new V3D_Plane(P0P0P0, P0P1P0, P0P0P1, -1);

    /**
     * The y = 0 plane.
     */
    public static final V3D_Plane y0 = new V3D_Plane(P0P0P0, P1P0P0, P0P0P1, -1);

    /**
     * The z = 0 plane.
     */
    public static final V3D_Plane z0 = new V3D_Plane(P0P0P0, P1P0P0, P0P1P0, -1);
    
    /**
     * Generic_Environment.
     */
    public Generic_Environment env;

    /**
     * V3D_Files.
     */
    public V3D_Files files;

    /**
     * Math_BigDecimal
     */
    public Math_BigDecimal bd;

    /**
     * Creates a new V3D_Environment
     * @param e Generic_Environment
     * @throws IOException If encountered.
     * @throws Exception If encountered.
     */
    public V3D_Environment(Generic_Environment e) throws IOException,
            Exception {
        this(e, e.files.getDir());
    }

    /**
     * Creates a new V3D_Environment
     * @param e Generic_Environment
     * @param dir Generic_Path
     * @throws IOException If encountered.
     * @throws Exception If encountered.
     */
    public V3D_Environment(Generic_Environment e, Generic_Path dir)
            throws IOException, Exception {
        super();
        this.env = e;
        bd = new Math_BigDecimal();
        initMemoryReserve(Default_Memory_Threshold, env);
        files = new V3D_Files(new Generic_Defaults(Paths.get(dir.toString(),
                V3D_Strings.s_v3d)));
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
