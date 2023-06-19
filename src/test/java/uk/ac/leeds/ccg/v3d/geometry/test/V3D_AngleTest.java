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
package uk.ac.leeds.ccg.v3d.geometry.test;

import ch.obermuhlner.math.big.BigRational;
import java.math.BigInteger;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Angle;

/**
 * Test class for V3D_Angle.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_AngleTest {

    public V3D_AngleTest() {
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
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        BigRational pi = Math_BigRational.getPi(new Math_BigDecimal(), oom, rm);
        BigRational dpi = pi.multiply(2);
        BigRational hpi = pi.divide(2);
        // Positive angle
        BigRational theta = dpi.multiply(BigInteger.TEN).add(pi);
        BigRational result = V3D_Angle.normalise(theta, oom, rm);
        BigRational expResult = pi;
        assertTrue(result.compareTo(expResult) == 0);
        // Test 2
        theta = dpi.multiply(BigInteger.TEN).add(hpi);
        result = V3D_Angle.normalise(theta, oom, rm);
        expResult = hpi;
        assertTrue(result.compareTo(expResult) == 0);
        // Negative angle
        theta = dpi.multiply(BigInteger.TEN).add(pi).negate();
        result = V3D_Angle.normalise(theta, oom, rm);
        expResult = pi;
        assertTrue(result.compareTo(expResult) == 0);
        // Test 4
        theta = dpi.multiply(BigInteger.TEN).add(hpi).negate();
        result = V3D_Angle.normalise(theta, oom, rm);
        expResult = hpi.multiply(3);
        assertTrue(result.compareTo(expResult) == 0);
    }

}
