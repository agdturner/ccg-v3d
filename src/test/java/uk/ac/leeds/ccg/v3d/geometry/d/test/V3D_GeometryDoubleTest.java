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
package uk.ac.leeds.ccg.v3d.geometry.d.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_Double;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_AngleDouble;

/**
 * Test class for V3D_AngleDouble.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_GeometryDoubleTest {

    public V3D_GeometryDoubleTest() {
        super();
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineDouble.
     */
    @Test
    public void testNormalise() {
        System.out.println("normalise");
        double epsilon = 1d/100000000000000d;
        double pi = Math.PI;
        double dpi = 2d * pi;
        double hpi = pi / 2d;
        // Positive angle
        double theta = (dpi * 10d) + pi;
        double result = V3D_AngleDouble.normalise(theta);
        double expResult = pi;
        assertTrue(Math_Double.equals(result, expResult, epsilon));
        // Test 2
        theta = (dpi * 10d) + hpi;
        result = V3D_AngleDouble.normalise(theta);
        expResult = hpi;
        assertTrue(Math_Double.equals(result, expResult, epsilon));
        // Negative angle
        // The epsilon has to be slightly larger!
        epsilon = 1d/10000000000000d;
        theta = (dpi * -10d) - pi;
        result = V3D_AngleDouble.normalise(theta);
        expResult = pi;
        assertTrue(Math_Double.equals(result, expResult, epsilon));
        // Test 4
        theta = (dpi * -10d) - hpi;
        result = V3D_AngleDouble.normalise(theta);
        expResult = hpi * 3d;
        assertTrue(Math_Double.equals(result, expResult, epsilon));
    }

}
