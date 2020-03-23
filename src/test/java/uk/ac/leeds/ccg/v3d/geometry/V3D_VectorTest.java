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

import uk.ac.leeds.ccg.v3d.test.V3D_Test;
import ch.obermuhlner.math.big.BigRational;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_VectorTest
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_VectorTest extends V3D_Test {

    private static final long serialVersionUID = 1L;

    public V3D_VectorTest() throws Exception {
        super(new V3D_Environment(new Generic_Environment(
                new Generic_Defaults())));
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
     * Test of toString method, of class V3D_Vector.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Vector instance = new V3D_Vector(P0P0P0);
        String expResult = "V3D_Vector(dx=0, dy=0, dz=0)";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V3D_Vector.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = new V3D_Vector(P0P0P0);
        V3D_Vector instance = new V3D_Vector(P0P0P0);
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 2
        o = new V3D_Vector(P0P0P0);
        instance = new V3D_Vector(P1P0P0);
        expResult = false;
        result = instance.equals(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class V3D_Vector.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertTrue(true); // Not a test!
    }

    /**
     * Test of getDotProduct method, of class V3D_Vector.
     */
    @Test
    public void testGetDotProduct() {
        System.out.println("getDotProduct");
        V3D_Vector v = new V3D_Vector(P0P1P0);
        V3D_Vector instance = new V3D_Vector(P1P0P0);
        BigRational expResult = P0;
        BigRational result = instance.getDotProduct(v);
        assertEquals(expResult, result);
        // Test 2
        v = new V3D_Vector(P0P1P0);
        instance = new V3D_Vector(P0P0N1);
        expResult = P0;
        result = instance.getDotProduct(v);
        assertEquals(expResult, result);
        // Test 3
        v = new V3D_Vector(P1P1P1);
        instance = new V3D_Vector(N1N1N1);
        expResult = N3;
        result = instance.getDotProduct(v);
        assertEquals(expResult, result);
    }

    /**
     * Test of isOrthogonal method, of class V3D_Vector.
     */
    @Test
    public void testIsOrthogonal() {
        System.out.println("isOrthogonal");
        V3D_Vector v = new V3D_Vector(P1P0P0);
        // Tests A: 1 To 26
        // Test 1
        V3D_Vector instance = new V3D_Vector(P0P1P0);
        boolean expResult = true;
        boolean result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Vector(P0P0P1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 3
        instance = new V3D_Vector(P0N1P0);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 4
        instance = new V3D_Vector(N1P0P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 5
        instance = new V3D_Vector(P1P1P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 6
        instance = new V3D_Vector(P1N1P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 7
        instance = new V3D_Vector(N1N1P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 8
        instance = new V3D_Vector(N1P1P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 9
        instance = new V3D_Vector(P0P1N1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 10
        instance = new V3D_Vector(P0N1N1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 11
        instance = new V3D_Vector(P0N1P1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 12
        instance = new V3D_Vector(P0P1P1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 14
        instance = new V3D_Vector(P0P1N1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 15
        instance = new V3D_Vector(P0N1N1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 16
        instance = new V3D_Vector(P0N1P1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 17
        instance = new V3D_Vector(P0P1P1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 18
        instance = new V3D_Vector(P1P1N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 19
        instance = new V3D_Vector(P1P1P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 20
        instance = new V3D_Vector(N1P1P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 21
        instance = new V3D_Vector(N1P1N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 22
        instance = new V3D_Vector(P1N1N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 23
        instance = new V3D_Vector(P1N1P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 24
        instance = new V3D_Vector(N1N1P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 25
        instance = new V3D_Vector(N1N1N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 26
        instance = new V3D_Vector(P1P0P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        v = new V3D_Vector(P1P1P0);
        // Tests B: 1 To 26
        // Test 1
        instance = new V3D_Vector(P1P0P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Vector(P1N1P0);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 3
        instance = new V3D_Vector(P0N1P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 4
        instance = new V3D_Vector(N1N1P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 5
        instance = new V3D_Vector(N1P0P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 6
        instance = new V3D_Vector(N1P1P0);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 7
        instance = new V3D_Vector(P0P1P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 8
        instance = new V3D_Vector(P1P1P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 9
        instance = new V3D_Vector(P1P1N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 10
        instance = new V3D_Vector(P1P0N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 11
        instance = new V3D_Vector(P1N1N1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 12
        instance = new V3D_Vector(P0N1N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 13
        instance = new V3D_Vector(N1N1N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 14
        instance = new V3D_Vector(N1P0N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 15
        instance = new V3D_Vector(N1P1N1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 16
        instance = new V3D_Vector(P0P1N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 17
        instance = new V3D_Vector(P0P0N1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 18
        instance = new V3D_Vector(P1P1P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 19
        instance = new V3D_Vector(P1P0P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 20
        instance = new V3D_Vector(P1N1P1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 21
        instance = new V3D_Vector(P0N1P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 22
        instance = new V3D_Vector(N1N1P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 23
        instance = new V3D_Vector(N1P0P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 24
        instance = new V3D_Vector(N1P1P1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 25
        instance = new V3D_Vector(P0P1P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 26
        instance = new V3D_Vector(P0P0P1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        v = new V3D_Vector(P1P1P1);
        // Tests C: 1 To 26
        // Test 1
        instance = new V3D_Vector(P1P1P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Vector(P1P0P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 3
        instance = new V3D_Vector(P1N1P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 4
        instance = new V3D_Vector(P0N1P1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 5
        instance = new V3D_Vector(N1N1N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 6
        instance = new V3D_Vector(N1P0P1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 7
        instance = new V3D_Vector(N1P1P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 8
        instance = new V3D_Vector(P0P1P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 9
        instance = new V3D_Vector(P0P0P1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 9
        instance = new V3D_Vector(P1P1P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 10
        instance = new V3D_Vector(P1P0P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 11
        instance = new V3D_Vector(P1N1P0);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 12
        instance = new V3D_Vector(P0N1P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 13
        instance = new V3D_Vector(N1N1P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 14
        instance = new V3D_Vector(N1P0P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 15
        instance = new V3D_Vector(N1P1P0);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 15
        instance = new V3D_Vector(P0P1P0);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 16
        instance = new V3D_Vector(P0P0P0);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 17
        instance = new V3D_Vector(P1P1N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 18
        instance = new V3D_Vector(P1P0N1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 19
        instance = new V3D_Vector(P1N1N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 20
        instance = new V3D_Vector(P0N1N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 21
        instance = new V3D_Vector(N1N1N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 22
        instance = new V3D_Vector(N1P0N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 23
        instance = new V3D_Vector(N1P1N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 24
        instance = new V3D_Vector(P0P1N1);
        expResult = true;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
        // Test 25
        instance = new V3D_Vector(P0P0N1);
        expResult = false;
        result = instance.isOrthogonal(v);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMagnitude method, of class V3D_Vector.
     */
    @Test
    public void testGetMagnitude() {
        System.out.println("getMagnitude");
        int scale = 0;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector instance = new V3D_Vector(P0P0P0);
        BigDecimal expResult = P0.toBigDecimal();
        BigDecimal result = instance.getMagnitude(scale, rm);
        assertEquals(expResult, result);
        // Test 2
        scale = 1;
        result = instance.getMagnitude(scale, rm);
        assertEquals(expResult, result);
        // Test 3
        scale = 1;
        instance = new V3D_Vector(P1P1P0);
        expResult = Math_BigDecimal.sqrt(P2.toBigDecimal(), scale, rm);
        result = instance.getMagnitude(scale, rm);
        assertEquals(expResult, result);
        // Test 4
        scale = 2;
        expResult = Math_BigDecimal.sqrt(P2.toBigDecimal(), scale, rm);
        result = instance.getMagnitude(scale, rm);
        assertEquals(expResult, result);
        // Test 5
        scale = 10;
        expResult = Math_BigDecimal.sqrt(P2.toBigDecimal(), scale, rm);
        result = instance.getMagnitude(scale, rm);
        assertEquals(expResult, result);
        // Test 6
        scale = 1;
        instance = new V3D_Vector(P1P1P1);
        expResult = Math_BigDecimal.sqrt(P3.toBigDecimal(), scale, rm);
        result = instance.getMagnitude(scale, rm);
        assertEquals(expResult, result);
        // Test 7
        scale = 8;
        instance = new V3D_Vector(P1P1P1);
        expResult = Math_BigDecimal.sqrt(P3.toBigDecimal(), scale, rm);
        result = instance.getMagnitude(scale, rm);
        assertEquals(expResult, result);
        // Test 8
        scale = 100;
        instance = new V3D_Vector(P10, P10, P10);
        expResult = Math_BigDecimal.sqrt(BigDecimal.valueOf(300), scale, rm);
        result = instance.getMagnitude(scale, rm);
        assertEquals(expResult, result);
        // Test 9
        instance = new V3D_Vector(P3, P4, N4);
        expResult = Math_BigDecimal.sqrt(BigDecimal.valueOf(41), scale, rm);
        result = instance.getMagnitude(scale, rm);
        assertEquals(expResult, result);
        // Test 10
        instance = new V3D_Vector(P7, P8, N4);
        expResult = Math_BigDecimal.sqrt((P7.toBigDecimal().pow(2)
                .add(P8.toBigDecimal().pow(2).add(N4.toBigDecimal().pow(2)))),
                scale, rm);
        result = instance.getMagnitude(scale, rm);
        assertEquals(expResult, result);

    }

    /**
     * Test of initMagnitude method, of class V3D_Vector.
     */
    @Test
    public void testInitMagnitude() {
        System.out.println("initMagnitude");
        assertTrue(true); // No need to test.
    }

    /**
     * Test of isParallel method, of class V3D_Vector.
     */
    @Test
    public void testIsParallel() {
        System.out.println("isParallel");
        V3D_Vector v = new V3D_Vector(P0P0P0);
        V3D_Vector instance = new V3D_Vector(P1P1P1);
        boolean expResult = false;
        boolean result = instance.isParallel(v);
        assertEquals(expResult, result);
        // Test 2
        result = instance.isParallel(v);
        assertEquals(expResult, result);
        // Test 3
        v = new V3D_Vector(N1N1N1);
        instance = new V3D_Vector(P1P1P1);
        expResult = true;
        result = instance.isParallel(v);
        assertEquals(expResult, result);
        // Test 4
        expResult = true;
        result = instance.isParallel(v);
        assertEquals(expResult, result);
        // Test 5
        v = new V3D_Vector(P1P0P0);
        instance = new V3D_Vector(P0P1P1);
        expResult = false;
        result = instance.isParallel(v);
        assertEquals(expResult, result);
        // Test 6
        v = new V3D_Vector(P0, P1, P10000);
        instance = new V3D_Vector(P0, P1, P10001);
        expResult = false;
        result = instance.isParallel(v);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCrossProduct method, of class V3D_Vector.
     */
    @Test
    public void testGetCrossProduct() {
        System.out.println("getCrossProduct");
        V3D_Vector v = new V3D_Vector(P1P1P1);
        V3D_Vector instance = new V3D_Vector(N1N1N1);
        V3D_Vector expResult = new V3D_Vector(P0P0P0);
        V3D_Vector result = instance.getCrossProduct(v);
        assertEquals(expResult, result);
        // Test 2
        v = new V3D_Vector(P1P1P1);
        instance = new V3D_Vector(P1P1P0);
        expResult = new V3D_Vector(P1N1P0);
        result = instance.getCrossProduct(v);
        assertEquals(expResult, result);
        // Test 3
        v = new V3D_Vector(P1P1P0);
        instance = new V3D_Vector(P1P1P1);
        expResult = new V3D_Vector(N1P1P0);
        result = instance.getCrossProduct(v);
        assertEquals(expResult, result);
    }

}
