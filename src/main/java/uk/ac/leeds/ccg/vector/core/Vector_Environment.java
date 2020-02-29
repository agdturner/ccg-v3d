/*
 * Copyright 2019 Andy Turner, University of Leeds.
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
package uk.ac.leeds.ccg.vector.core;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.generic.io.Generic_Path;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.grids.core.Grids_Environment;
import uk.ac.leeds.ccg.vector.io.Vector_Files;
import uk.ac.leeds.ccg.vector.memory.Vector_Memory;
import uk.ac.leeds.ccg.vector.memory.Vector_MemoryManager;
import uk.ac.leeds.ccg.vector.projection.Vector_OSGBtoLatLon;

/**
 * Vector Environment.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Vector_Environment extends Vector_MemoryManager
        implements Vector_Memory {

    private static final long serialVersionUID = 1L;
    
    public Vector_Files files;

    /**
     * Generic Environment
     */
    public final Generic_Environment env;

    /**
     * Grids Environment
     */
    public final Grids_Environment ge;

    /**
     * MathBigDecimal
     */
    public Math_BigDecimal bd;

    protected Vector_OSGBtoLatLon OSGBtoLatLon;

    public Vector_Environment(Grids_Environment e) throws IOException, 
            Exception {
        this(e, e.files.getDir());
    }
    
    public Vector_Environment(Grids_Environment e, Generic_Path dir)
            throws IOException, Exception {
        super();
        this.ge = e;
        this.env = ge.env;
        bd = new Math_BigDecimal();
        initMemoryReserve(Default_Memory_Threshold, env);
        files = new Vector_Files(new Generic_Defaults(Paths.get(dir.toString(),
                Vector_Strings.s_vector)));
    }
            
    public BigDecimal getRounded_BigDecimal(BigDecimal toRoundBigDecimal,
            BigDecimal toRoundToBigDecimal) {
        int scale = toRoundToBigDecimal.scale();
        BigDecimal r = toRoundBigDecimal.setScale(scale - 1, RoundingMode.FLOOR);
        r = r.setScale(scale);
        r = r.add(toRoundToBigDecimal);
        return r;
    }

    public Vector_OSGBtoLatLon getOSGBtoLatLon() {
        if (OSGBtoLatLon == null) {
            OSGBtoLatLon = new Vector_OSGBtoLatLon();
        }
        return OSGBtoLatLon;
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
