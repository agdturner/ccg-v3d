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
package uk.ac.leeds.ccg.vector.geometry;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.grids.core.Grids_Environment;
import uk.ac.leeds.ccg.vector.core.Vector_Environment;

/**
 *
 * @author geoagdt
 */
public class Vector_LineSegment2DTest {

    Vector_Environment env;

    public Vector_LineSegment2DTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() {
        try {
            env = new Vector_Environment(new Grids_Environment(
                    new Generic_Environment(new Generic_Defaults())));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getIntersects method, of class Vector_LineSegment2D.
     */
    @Test
    public void testGetIntersects_VectorPoint2D_int() {
        boolean result;
        Vector_Point2D a = new Vector_Point2D(null, "0", "0");
        Vector_Point2D b = new Vector_Point2D(null, "1", "1");
        Vector_LineSegment2D ab = new Vector_LineSegment2D(a, b);
        System.out.println("ab " + ab);
        boolean expResult = true;
        BigDecimal ten = new BigDecimal("10");
        BigDecimal aX = new BigDecimal("1");
        BigDecimal aY = new BigDecimal("1");
        int dp = 0;
        for (int i = 0; i < 1000; i++) {
            dp++;
            System.out.println("Decimal Places " + dp);
            aX = aX.divide(ten);
            aY = aY.divide(ten);
            a = new Vector_Point2D(env, aX, aY);
            System.out.println("a " + a.toString());
            result = ab.getIntersects(a, dp);
            System.out.println("ab.getIntersects(a,DecimalPlacePrecision)"
                    + result);
            Assertions.assertEquals(expResult, result);
        }
    }

}
