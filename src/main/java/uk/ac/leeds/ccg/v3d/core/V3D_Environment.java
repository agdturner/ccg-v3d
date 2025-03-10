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
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Face;

/**
 * V3D_Environment
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Environment implements Serializable {
    
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
     * An instance that helps with calculations involving PI and Taylor series.
     */
    public static final Math_BigDecimal bd = new Math_BigDecimal();
    
    /**
     * The default Order of Magnitude.
     */
    public static final int DEFAULT_OOM = -3;
    
    /**
     * The default RoundingMode.
     */
    public static final RoundingMode DEFAULT_RM = RoundingMode.HALF_UP;
    
    /**
    /**
     * The Order of Magnitude for any rounding.
     */
    public int oom;
    
    /**
     * The RoundingMode for any rounding.
     */
    public RoundingMode rm;
    
    /**
     * The shapes.
     */
    public HashMap<Integer, V3D_Face> shapes;
    
    /**
     * The ids of shapes.
     */
    HashSet<Integer> ids;
    
    /**
     * Creates a new instance.
     */
    public V3D_Environment(int oom, RoundingMode rm){
        this.oom = oom;
        this.rm = rm;
        shapes = new HashMap<>();
        ids = new HashSet<>();
    }
    
    /**
     * @return The next id that has not yet been used; 
     */
    public int getNextID() {
        int id;
        if (!ids.isEmpty()) {
            id = ids.iterator().next();
            ids.remove(id);
        } else {
            id = shapes.size();
        }
        return id;
    }
    
    /**
     * @param shape The shape to be put in {@link #shapes}.
     * @return The id of the shape allocated. 
     */
    public int add(V3D_Face shape) {
        int id = getNextID();
        shapes.put(id, shape);
        return id;
    }
    
    /**
     * @param id The id of the shape to remove. 
     */
    public void remove(int id) {
        shapes.remove(id);
        ids.add(id);
    }
}
