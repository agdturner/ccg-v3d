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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author geoagdt
 */
public class Vector_Point2DTest {

    public Vector_Point2DTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of equals method, of class Vector_Point2D.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Vector_Point2D a;
        Vector_Point2D b;
        Vector_Point2D c;
        Vector_Point2D d;
        boolean result;
        boolean expResult;
        a = new Vector_Point2D(null, 0.1d, 0.3d);
        System.out.println("a = new VectorPoint2D(0.1d,0.3d); " + a.toString());

        b = new Vector_Point2D(null, "0.1", "0.3");
        System.out.println("b = new VectorPoint2D(\"0.1\",\"0.3\"); "
                + b.toString());

        result = a.equals(b);
        System.out.println("a.equals(b); " + result);
        expResult = false;
        Assertions.assertEquals(expResult, result);

        c = new Vector_Point2D(a, 1);
        System.out.println("c = new VectorPoint2D(a,1); " + c.toString());
        result = a.equals(c);
        System.out.println("a.equals(c); " + result);
        expResult = false;
        Assertions.assertEquals(expResult, result);

        d = new Vector_Point2D(b, 1);
        System.out.println("d = new VectorPoint2D(b,1); " + d.toString());

        result = b.equals(d);
        System.out.println("b.equals(d); " + result);
        expResult = true;
        Assertions.assertEquals(expResult, result);

        result = c.equals(d);
        System.out.println("c.equals(d); " + result);
        expResult = false;
        Assertions.assertEquals(expResult, result);
    }

}
