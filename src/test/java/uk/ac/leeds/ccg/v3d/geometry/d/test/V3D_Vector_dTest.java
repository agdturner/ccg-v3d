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
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Point_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Vector_d;

/**
 * Test of V3D_Vector_d class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Vector_dTest extends V3D_Test_d {

    public V3D_Vector_dTest() {
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
     * Test of getDotProduct method, of class V3D_Vector_d.
     */
    @Test
    public void testGetDotProduct() {
        System.out.println("getDotProduct");
        V3D_Vector_d v = P0P1P0;
        V3D_Vector_d instance = P1P0P0;
        double expResult = 0d;
        double result = instance.getDotProduct(v);
        assertTrue(expResult == result);
        // Test 2
        v = P0P1P0;
        instance = P0P0N1;
        result = instance.getDotProduct(v);
        assertTrue(expResult == result);
        // Test 3
        v = P1P1P1;
        instance = N1N1N1;
        expResult = -3d;
        result = instance.getDotProduct(v);
        assertTrue(expResult == result);
    }

//    /**
//     * Test of isOrthogonal method, of class V3D_Vector_d.
//     */
//    @Test
//    public void testIsOrthogonal() {
//        System.out.println("isOrthogonal");
//        double epsilon = 1 / 1000000d;
//        V3D_Vector_d v;
//        V3D_Vector_d instance;
//        // Test 1
//        v = P1P0P0;
//        instance = P0P1P0;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0P0P1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0N1P0;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = N1P0P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1P1P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1N1P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1N1P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1P1P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P0P1N1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0N1N1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0N1P1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0P1P1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0P1N1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0N1N1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0N1P1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0P1P1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P1P1N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1P1P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1P1P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1P1N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1N1N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1N1P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1N1P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1N1N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1P0P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        // Test 2
//        v = P1P1P0;
//        instance = P1P0P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1N1P0;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0N1P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1N1P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1P0P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1P1P0;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0P1P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1P1P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1P1N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1P0N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1N1N1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0N1N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1N1N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1P0N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1P1N1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0P1N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P0P0N1;
//        v = P1P1P0;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P1P1P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1P0P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1N1P1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0N1P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1N1P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1P0P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1P1P1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0P1P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P0P0P1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        // Test 3
//        v = P1P1P1;
//        instance = P1P1P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1P0P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1N1P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P0N1P1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = N1N1N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1P0P1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = N1P1P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P0P1P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P0P0P1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1P1P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1P0P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1N1P0;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0N1P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1N1P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1P0P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1P1P0;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0P1P0;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P0P0P0;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P1P1N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P1P0N1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P1N1N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P0N1N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1N1N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1P0N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = N1P1N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//        instance = P0P1N1;
//        assertTrue(instance.isOrthogonal(v, epsilon));
//        instance = P0P0N1;
//        assertFalse(instance.isOrthogonal(v, epsilon));
//    }
    
    /**
     * Test of isOrthogonal method, of class V3D_Vector_d.
     */
    @Test
    public void testIsOrthogonal() {
        System.out.println("isOrthogonal");
        V3D_Vector_d v;
        V3D_Vector_d instance;
        // Test 1
        v = P1P0P0;
        instance = P0P1P0;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P0P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1P0;
        assertTrue(instance.isOrthogonal(v));
        instance = N1P0P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P0P1N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P1P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P1N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P1P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P1P1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P0P0;
        assertFalse(instance.isOrthogonal(v));
        // Test 2
        v = P1P1P0;
        instance = P1P0P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1P0;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P0P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1P0;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P0N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P0N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P0P0N1;
        v = P1P1P0;
        assertTrue(instance.isOrthogonal(v));
        instance = P1P1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P0P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P0P1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P0P0P1;
        assertTrue(instance.isOrthogonal(v));
        // Test 3
        v = P1P1P1;
        instance = P1P1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P0P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P0N1P1;
        assertTrue(instance.isOrthogonal(v));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P0P1;
        assertTrue(instance.isOrthogonal(v));
        instance = N1P1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P0P1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P0P0P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P0P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1P0;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P0P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1P0;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P0P0P0;
        assertTrue(instance.isOrthogonal(v));
        instance = P1P1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P0N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P1N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P0N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P0N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = P0P1N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P0N1;
        assertFalse(instance.isOrthogonal(v));
    }

    /**
     * Test of getMagnitude method, of class V3D_Vector_d.
     */
    @Test
    public void testGetMagnitude() {
        System.out.println("getMagnitude");
        V3D_Vector_d instance = P0P0P0;
        double expResult = 0d;
        double result = instance.getMagnitude();
        assertTrue(expResult == result);
        // Test 1
        instance = P1P1P0;
        expResult = Math.sqrt(2d);
        result = instance.getMagnitude();
        assertTrue(expResult == result);
        // Test 2
        instance = P1P1P1;
        expResult = Math.sqrt(3d);
        result = instance.getMagnitude();
        assertTrue(expResult == result);
        // Test 3
        instance = new V3D_Vector_d(10d, 10d, 10d);
        expResult = Math.sqrt(300d);
        result = instance.getMagnitude();
        assertTrue(expResult == result);
        // Test 4
        instance = new V3D_Vector_d(3d, 4d, -4d);
        expResult = Math.sqrt(41d);
        result = instance.getMagnitude();
        assertTrue(expResult == result);
        // Test 5
        instance = new V3D_Vector_d(7d, 8d, -4d);
        expResult = Math.sqrt(49d + 64d + 16);
        result = instance.getMagnitude();
        assertTrue(expResult == result);
    }

    /**
     * Test of initMagnitude method, of class V3D_Vector_d.
     */
    @Test
    public void testInitMagnitude() {
        System.out.println("initMagnitude");
        assertTrue(true); // No need to test.
    }

    /**
     * Test of isScalarMultiple method, of class V3D_Vector_d.
     */
    @Test
    public void testIsScalarMultiple_double_V3D_Vector() {
        System.out.println("isScalarMultiple");
        double epsilon = 1 / 1000000d;
        V3D_Vector_d v = P0P0P0;
        V3D_Vector_d instance = P1P1P1;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 2
        v = P0P0P0;
        instance = P0P0P0;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 3
        v = N1N1N1;
        instance = P0P0P0;
        assertFalse(instance.isScalarMultiple(epsilon, v));
        // Test 4
        v = N1N1N1;
        instance = P1P1P1;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 5
        v = P1P0P0;
        instance = P0P1P1;
        assertFalse(instance.isScalarMultiple(epsilon, v));
        // Test 6
        v = new V3D_Vector_d(0d, 1d, 10000d);
        instance = new V3D_Vector_d(0d, 1d, 10000d);
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 7
        v = new V3D_Vector_d(0d, 1d, 10000d);
        instance = new V3D_Vector_d(0d, 1d, 10001d);
        assertFalse(instance.isScalarMultiple(epsilon, v));        
        // Test 8
        v = new V3D_Vector_d(0d, 1d, 1d);
        instance = new V3D_Vector_d(0d, 1d, 1d + (epsilon / 2.0d));
        //assertFalse(instance.isScalarMultiple(epsilon, v));
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 9
        v = new V3D_Vector_d(0d, 1d, 10000d);
        instance = new V3D_Vector_d(0d, 2d, 20000d);
        assertTrue(instance.isScalarMultiple(epsilon, v));
        
        // Test 10
        instance = P0P0N1;
        v = P0P0N2;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 11
        v = P0P0P2;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 12
        v = P0P0N2;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 13
        v = P0P0P2;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        
        // Test 14
        instance = P0N1P0;
        v = P0N2P0;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 15
        v = P0P2P0;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 16
        v = P0N2P0;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 17
        v = P0P2P0;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        
        // Test 18
        instance = N1P0P0;
        v = N2P0P0;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 19
        v = P2P0P0;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 20
        v = N2P0P0;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 21
        v = P2P0P0;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        
        // Test 22
        instance = new V3D_Vector_d(0d, 1d, 10000d);
        v = new V3D_Vector_d(0d, 1d, 10000d);
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 23
        v = new V3D_Vector_d(0d, 1d, 10001d);
        assertFalse(instance.isScalarMultiple(epsilon, v));  
        // Test 24
        v = new V3D_Vector_d(0d, 1.01d, 10000d);
        assertFalse(instance.isScalarMultiple(epsilon, v));  
        // Test 25
        v = new V3D_Vector_d(0.01d, 1d, 10000d);
        assertFalse(instance.isScalarMultiple(epsilon, v));  
        
        // Test 26
        instance = new V3D_Vector_d(0d, 1d, 10000d);
        v = new V3D_Vector_d(0d, 2d, 20000d);
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 27
        instance = new V3D_Vector_d(3d, 1d, 10000d);
        v = new V3D_Vector_d(6d, 2d, 20000d);
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 28
        instance = new V3D_Vector_d(3d, 1d, 10000d);
        v = new V3D_Vector_d(-6d, -2d, -20000d);
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 29
        instance = new V3D_Vector_d(-3d, -1d, -10000d);
        v = new V3D_Vector_d(6d, 2d, 20000d);
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 30
        instance = new V3D_Vector_d(-3d, -1d, -10000d);
        v = new V3D_Vector_d(-6d, -2d, -20000d);
        assertTrue(instance.isScalarMultiple(epsilon, v));
        
        // Test 31
        instance = new V3D_Vector_d(-3d, 0d, -10000d);
        v = new V3D_Vector_d(+6d, 0d, -20000d);
        assertFalse(instance.isScalarMultiple(epsilon, v));
        // Test 32
        instance = new V3D_Vector_d(-3d, 0d, -10000d);
        v = new V3D_Vector_d(-6d, 0d, -20000d);
        assertTrue(instance.isScalarMultiple(epsilon, v));
        
        // Test 31
        instance = new V3D_Vector_d(0d, 1d, 1d);
        v = new V3D_Vector_d(0d, 1d, 1d + (epsilon / 2.0d));
        //assertFalse(instance.isScalarMultiple(epsilon, v));
        assertTrue(instance.isScalarMultiple(epsilon, v));
    }
    
    /**
     * Test of isScalarMultiple method, of class V3D_Vector_d.
     */
    @Test
    public void testIsScalarMultiple_V3D_Vector() {
        System.out.println("isScalarMultiple");
        V3D_Vector_d v = P0P0P0;
        V3D_Vector_d instance = P1P1P1;
        assertTrue(instance.isScalarMultiple(v));
        // Test 2
        v = P0P0P0;
        instance = P0P0P0;
        assertTrue(instance.isScalarMultiple(v));
        // Test 3
        v = N1N1N1;
        instance = P0P0P0;
        assertFalse(instance.isScalarMultiple(v));
        // Test 4
        v = N1N1N1;
        instance = P1P1P1;
        assertTrue(instance.isScalarMultiple(v));
        // Test 5
        v = P1P0P0;
        instance = P0P1P1;
        assertFalse(instance.isScalarMultiple(v));
        // Test 6
        v = new V3D_Vector_d(0d, 1d, 10000d);
        instance = new V3D_Vector_d(0d, 1d, 10000d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 7
        v = new V3D_Vector_d(0d, 1d, 10000d);
        instance = new V3D_Vector_d(0d, 1d, 10001d);
        assertFalse(instance.isScalarMultiple(v));        
        // Test 8
        v = new V3D_Vector_d(0d, 1d, 1d);
        instance = new V3D_Vector_d(0d, 1d, 1d);
        //assertFalse(instance.isScalarMultiple(v));
        assertTrue(instance.isScalarMultiple(v));
        // Test 9
        v = new V3D_Vector_d(0d, 1d, 10000d);
        instance = new V3D_Vector_d(0d, 2d, 20000d);
        assertTrue(instance.isScalarMultiple(v));
        
        // Test 10
        instance = P0P0N1;
        v = P0P0N2;
        assertTrue(instance.isScalarMultiple(v));
        // Test 11
        v = P0P0P2;
        assertTrue(instance.isScalarMultiple(v));
        // Test 12
        v = P0P0N2;
        assertTrue(instance.isScalarMultiple(v));
        // Test 13
        v = P0P0P2;
        assertTrue(instance.isScalarMultiple(v));
        
        // Test 14
        instance = P0N1P0;
        v = P0N2P0;
        assertTrue(instance.isScalarMultiple(v));
        // Test 15
        v = P0P2P0;
        assertTrue(instance.isScalarMultiple(v));
        // Test 16
        v = P0N2P0;
        assertTrue(instance.isScalarMultiple(v));
        // Test 17
        v = P0P2P0;
        assertTrue(instance.isScalarMultiple(v));
        
        // Test 18
        instance = N1P0P0;
        v = N2P0P0;
        assertTrue(instance.isScalarMultiple(v));
        // Test 19
        v = P2P0P0;
        assertTrue(instance.isScalarMultiple(v));
        // Test 20
        v = N2P0P0;
        assertTrue(instance.isScalarMultiple(v));
        // Test 21
        v = P2P0P0;
        assertTrue(instance.isScalarMultiple(v));
        
        // Test 22
        instance = new V3D_Vector_d(0d, 1d, 10000d);
        v = new V3D_Vector_d(0d, 1d, 10000d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 23
        v = new V3D_Vector_d(0d, 1d, 10001d);
        assertFalse(instance.isScalarMultiple(v));  
        // Test 24
        v = new V3D_Vector_d(0d, 1.01d, 10000d);
        assertFalse(instance.isScalarMultiple(v));  
        // Test 25
        v = new V3D_Vector_d(0.01d, 1d, 10000d);
        assertFalse(instance.isScalarMultiple(v));  
        
        // Test 26
        instance = new V3D_Vector_d(0d, 1d, 10000d);
        v = new V3D_Vector_d(0d, 2d, 20000d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 27
        instance = new V3D_Vector_d(3d, 1d, 10000d);
        v = new V3D_Vector_d(6d, 2d, 20000d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 28
        instance = new V3D_Vector_d(3d, 1d, 10000d);
        v = new V3D_Vector_d(-6d, -2d, -20000d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 29
        instance = new V3D_Vector_d(-3d, -1d, -10000d);
        v = new V3D_Vector_d(6d, 2d, 20000d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 30
        instance = new V3D_Vector_d(-3d, -1d, -10000d);
        v = new V3D_Vector_d(-6d, -2d, -20000d);
        assertTrue(instance.isScalarMultiple(v));
        
        // Test 31
        instance = new V3D_Vector_d(-3d, 0d, -10000d);
        v = new V3D_Vector_d(+6d, 0d, -20000d);
        assertFalse(instance.isScalarMultiple(v));
        // Test 32
        instance = new V3D_Vector_d(-3d, 0d, -10000d);
        v = new V3D_Vector_d(-6d, 0d, -20000d);
        assertTrue(instance.isScalarMultiple(v));
        
        // Test 33
        instance = new V3D_Vector_d(0d, 0d, -10000d);
        v = new V3D_Vector_d(0d, 0d, -20000d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 34
        instance = new V3D_Vector_d(0d, -10000d, 0d);
        v = new V3D_Vector_d(0d, -20000d, 0d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 35
        instance = new V3D_Vector_d(-10000d, 0d, 0d);
        v = new V3D_Vector_d(-20000d, 0d, 0d);
        assertTrue(instance.isScalarMultiple(v));
        
        // Test 36
        instance = new V3D_Vector_d(-10000d, 1d, 0d);
        v = new V3D_Vector_d(-20000d, 2d, 0d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 37
        instance = new V3D_Vector_d(-10000d, 0d, 10d);
        v = new V3D_Vector_d(-20000d, 0d, 20d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 38
        instance = new V3D_Vector_d(-10000d, 1d, 0d);
        v = new V3D_Vector_d(-20000d, 2d, 0d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 39
        instance = new V3D_Vector_d(-10000d, 0d, -10d);
        v = new V3D_Vector_d(20000d, 0d, 20d);
        assertTrue(instance.isScalarMultiple(v));
    }

    /**
     * Test of getCrossProduct method, of class V3D_Vector_d.
     */
    @Test
    public void testGetCrossProduct() {
        System.out.println("getCrossProduct");
        V3D_Vector_d v = P1P1P1;
        V3D_Vector_d instance = N1N1N1;
        V3D_Vector_d expResult = P0P0P0;
        V3D_Vector_d result = instance.getCrossProduct(v);
        assertTrue(expResult.equals(result));
        // Test 2
        v = P1P1P1;
        instance = P1P1P0;
        expResult = P1N1P0;
        result = instance.getCrossProduct(v);
        assertTrue(expResult.equals(result));
        // Test 3
        v = P1P1P0;
        instance = P1P1P1;
        expResult = N1P1P0;
        result = instance.getCrossProduct(v);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class V3D_Vector_d.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        Object o = V3D_Vector_d.I;
        V3D_Vector_d instance = V3D_Vector_d.I;
        assertTrue(instance.equals(o));
        // Test 2
        instance = V3D_Vector_d.J;
        assertFalse(instance.equals(o));
        // Test 3
        o = P0P0P0;
        instance = P0P0P0;
        assertTrue(instance.equals(o));
        // Test 4
        o = P0P0P0;
        instance = P1P0P0;
        assertFalse(instance.equals(o));
    }

    /**
     * Test of isReverse method, of class V3D_Vector_d.
     */
    @Test
    public void testIsReverse() {
        System.out.println("isReverse");
        V3D_Vector_d v = V3D_Vector_d.I;
        V3D_Vector_d instance = new V3D_Vector_d(-1, 0, 0);
        double epsilon = 0d;
        assertTrue(instance.isReverse(v, epsilon));
        // Test 2
        epsilon = 0.0000000001d;
        instance = new V3D_Vector_d(-1d + epsilon/2, 0, 0);
        assertTrue(instance.isReverse(v, epsilon));
        // Test 3
        epsilon = 0.0000000001d;
        instance = new V3D_Vector_d(-1d + 2 * epsilon, 0, 0);
        assertFalse(instance.isReverse(v, epsilon));
        // Test 4
        epsilon = 0.0000000001d;
        instance = new V3D_Vector_d(-1d + epsilon, epsilon, -epsilon);
        assertTrue(instance.isReverse(v, epsilon));
    }

    /**
     * Test of isZero method, of class V3D_Vector_d.
     */
    @Test
    public void testIsZeroVector() {
        System.out.println("isZeroVector");
        V3D_Vector_d instance = new V3D_Vector_d(0, 0, 0);
        assertTrue(instance.isZero());
        // Test 2
        instance = new V3D_Vector_d(1, 0, 0);
        assertFalse(instance.isZero());
    }

    /**
     * Test of isZero method, of class V3D_Vector_d.
     */
    @Test
    public void testIsZeroVector_double() {
        System.out.println("isZeroVector");
        double epsilon = 0.000000001d;
        V3D_Vector_d instance = new V3D_Vector_d(0, 0, 0);
        assertTrue(instance.isZero(epsilon));
        // Test 2
        instance = new V3D_Vector_d(1, 0, 0);
        assertFalse(instance.isZero(epsilon));
        // Test 3
        instance = new V3D_Vector_d(epsilon, 0, 0);
        assertTrue(instance.isZero(epsilon));        
        // Test 4
        instance = new V3D_Vector_d(epsilon, epsilon, epsilon);
        assertTrue(instance.isZero(epsilon));
        // Test 5
        instance = new V3D_Vector_d(2d * epsilon, 0, 0);
        assertFalse(instance.isZero(epsilon));        
        // Test 6
        instance = new V3D_Vector_d(2d * epsilon, 2d * epsilon, 2d * epsilon);
        assertFalse(instance.isZero(epsilon));
    }

    /**
     * Test of multiply method, of class V3D_Vector_d.
     */
    @Test
    public void testMultiply() {
        System.out.println("multiply");
        double s = 0d;
        V3D_Vector_d instance = new V3D_Vector_d(0, 0, 0);
        V3D_Vector_d expResult = new V3D_Vector_d(0, 0, 0);
        V3D_Vector_d result = instance.multiply(s);
        assertTrue(expResult.equals(result));
        // Test 2
        s = 0d;
        instance = new V3D_Vector_d(10, 10, 10);
        expResult = new V3D_Vector_d(0, 0, 0);
        result = instance.multiply(s);
        assertTrue(expResult.equals(result));
        // Test 3
        s = 2d;
        instance = new V3D_Vector_d(10, 10, 10);
        expResult = new V3D_Vector_d(20, 20, 20);
        result = instance.multiply(s);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of add method, of class V3D_Vector_d.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        V3D_Vector_d instance = new V3D_Vector_d(0, 0, 0);
        V3D_Vector_d v = new V3D_Vector_d(0, 0, 0);
        V3D_Vector_d expResult = new V3D_Vector_d(0, 0, 0);
        V3D_Vector_d result = instance.add(v);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Vector_d(0, 0, 0);
        v = new V3D_Vector_d(1, 1, 1);
        expResult = new V3D_Vector_d(1, 1, 1);
        result = instance.add(v);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Vector_d(2, 3, 4);
        v = new V3D_Vector_d(7, 1, 11);
        expResult = new V3D_Vector_d(9, 4, 15);
        result = instance.add(v);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Vector_d(-2, 3, -4);
        v = new V3D_Vector_d(7, 1, 11);
        expResult = new V3D_Vector_d(5, 4, 7);
        result = instance.add(v);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of subtract method, of class V3D_Vector_d.
     */
    @Test
    public void testSubtract() {
        System.out.println("subtract");
        V3D_Vector_d instance = new V3D_Vector_d(0, 0, 0);
        V3D_Vector_d v = new V3D_Vector_d(0, 0, 0);
        V3D_Vector_d expResult = new V3D_Vector_d(0, 0, 0);
        V3D_Vector_d result = instance.subtract(v);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Vector_d(0, 0, 0);
        v = new V3D_Vector_d(1, 1, 1);
        expResult = new V3D_Vector_d(-1, -1, -1);
        result = instance.subtract(v);
        //assertTrue(expResult.compareTo(result) == 0);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of reverse method, of class V3D_Vector_d.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        V3D_Vector_d instance = new V3D_Vector_d(0, 0, 0);
        V3D_Vector_d expResult = new V3D_Vector_d(0, 0, 0);
        V3D_Vector_d result = instance.reverse();
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Vector_d(1, 1, 1);
        expResult = new V3D_Vector_d(-1, -1, -1);
        result = instance.reverse();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getMagnitudeSquared method, of class V3D_Vector_d.
     */
    @Test
    public void testGetMagnitudeSquared() {
        System.out.println("getMagnitudeSquared");
        V3D_Vector_d instance = new V3D_Vector_d(0, 0, 0);
        double expResult = 0d;
        double result = instance.getMagnitudeSquared();
        assertTrue(expResult == result);
        // Test 2
        instance = new V3D_Vector_d(1, 1, 1);
        expResult = 3d;
        result = instance.getMagnitudeSquared();
        assertTrue(expResult == result);
        // Test 3
        instance = new V3D_Vector_d(2, 2, 2);
        expResult = 12d;
        result = instance.getMagnitudeSquared();
        assertTrue(expResult == result);
        // Test 4
        instance = new V3D_Vector_d(-2, 3, 1);
        expResult = 14d;
        result = instance.getMagnitudeSquared();
        assertTrue(expResult == result);
    }

    /**
     * Test of getUnitVector method, of class V3D_Vector_d.
     */
    @Test
    public void testGetUnitVector() {
        System.out.println("getUnitVector");
        V3D_Vector_d instance = V3D_Vector_d.I;
        V3D_Vector_d expResult = V3D_Vector_d.I;
        V3D_Vector_d result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        assertTrue(result.getMagnitudeSquared() == 1d);
        // Test 2
        instance = new V3D_Vector_d(100d, 0d, 0d);
        expResult = V3D_Vector_d.I;
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        assertTrue(result.getMagnitudeSquared() == 1d);
        // Test 3
        instance = new V3D_Vector_d(100d, 100d, 0d);
        expResult = new V3D_Vector_d(100d/Math.sqrt(20000d), 100d/Math.sqrt(20000d), 0d);
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        //assertTrue(result.getMagnitudeSquared().compareTo(double.ONE) != 1);
        // Test 4
        instance = new V3D_Vector_d(0d, 100d, 100d);
        expResult = new V3D_Vector_d(0d, 100d/Math.sqrt(20000d), 100d/Math.sqrt(20000d));
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        // Test 5
        instance = new V3D_Vector_d(100, 100, 100);
        expResult = new V3D_Vector_d(100d/Math.sqrt(30000d), 100d/Math.sqrt(30000d), 100d/Math.sqrt(30000d));
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        // Test 6
        instance = new V3D_Vector_d(-100, 0, 0);
        expResult = new V3D_Vector_d(-1,0,0);
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        // Test 7
        instance = new V3D_Vector_d(-100, -100, 0);
        expResult = new V3D_Vector_d(-100d/Math.sqrt(20000d), -100d/Math.sqrt(20000d), 0d);
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        // Test 8
        instance = new V3D_Vector_d(0, -100, -100);
        expResult = new V3D_Vector_d(0d, -100d/Math.sqrt(20000d), -100d/Math.sqrt(20000d));
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        // Test 9
        instance = new V3D_Vector_d(-100, -100, -100);
        expResult = new V3D_Vector_d(-100d/Math.sqrt(30000d), -100d/Math.sqrt(30000d), -100d/Math.sqrt(30000d));
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDX method, of class V3D_Vector_d.
     */
    @Test
    public void testGetDX() {
        System.out.println("getDX");
        V3D_Vector_d instance = V3D_Vector_d.I;
        double expResult = 1d;
        double result = instance.dx;
        assertTrue(expResult == result);
        // Test 2
        instance = V3D_Vector_d.I.reverse();
        expResult = -1d;
        result = instance.dx;
        assertTrue(expResult == result);
    }

    /**
     * Test of getDY method, of class V3D_Vector_d.
     */
    @Test
    public void testGetDY() {
        System.out.println("getDY");
        V3D_Vector_d instance = V3D_Vector_d.J;
        double expResult = 1d;
        double result = instance.dy;
        assertTrue(expResult==result);
        // Test 2
        instance = V3D_Vector_d.J.reverse();
        expResult = -1d;
        result = instance.dy;
        assertTrue(expResult==result);
    }

    /**
     * Test of getDZ method, of class V3D_Vector_d.
     */
    @Test
    public void testGetDZ() {
        System.out.println("getDZ");
        V3D_Vector_d instance = V3D_Vector_d.K;
        double expResult = 1d;
        double result = instance.dz;
        assertTrue(expResult==result);
        // Test 2
        instance = V3D_Vector_d.K.reverse();
        expResult = -1d;
        result = instance.dz;
        assertTrue(expResult==result);
    }

    /**
     * Test of divide method, of class V3D_Vector_d.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        double s = 2d;
        V3D_Vector_d instance = V3D_Vector_d.I;
        V3D_Vector_d expResult = new V3D_Vector_d(
                new V3D_Point_d(env, 0.5d, 0d, 0d));
        V3D_Vector_d result = instance.divide(s);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getAngle method, of class V3D_Vector_d.
     */
    @Test
    public void testGetAngle() {
        System.out.println("getAngle");
        V3D_Vector_d v;
        V3D_Vector_d instance;
        double expResult;
        double result;
        // Test 1
        v = V3D_Vector_d.I;
        instance = V3D_Vector_d.J;
        result = instance.getAngle(v);
        expResult = Math.PI / 2d;
        assertTrue(expResult== result);
    }

    /**
     * Test of getDirection method, of class V3D_Vector_d.
     */
    @Test
    public void testGetDirection() {
        System.out.println("getDirection");
        V3D_Vector_d instance = V3D_Vector_d.I;
        int expResult = 1;
        int result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 2
        instance = V3D_Vector_d.J;
        expResult = 1;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 3
        instance = V3D_Vector_d.K;
        expResult = 1;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 4
        instance = V3D_Vector_d.I.reverse();
        expResult = 5;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 5
        instance = V3D_Vector_d.J.reverse();
        expResult = 3;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 6
        instance = V3D_Vector_d.K.reverse();
        expResult = 2;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 7
        //...
    }

    /**
     * Test of toString method, of class V3D_Vector_d.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        V3D_Vector_d v = V3D_Vector_d.ZERO;
        String result = v.toString();
        String expResult = "V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0)";
//        String expResult = "V3D_Vector_d\n"
//                + "(\n"
//                + " dx=double(x=0, sqrtx=0, oom=0),\n"
//                + " dy=double(x=0, sqrtx=0, oom=0),\n"
//                + " dz=double(x=0, sqrtx=0, oom=0)\n"
//                + ")";
        //System.out.println(result);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class V3D_Vector_d.
     */
    @Test
    public void testEquals_V3D_Vector_d() {
        System.out.println("equals");
        V3D_Vector_d v = new V3D_Vector_d(1, 1, 1);
        V3D_Vector_d instance = new V3D_Vector_d(1, 1, 1);
        assertTrue(instance.equals(v));
        instance = new V3D_Vector_d(1, 1, 0);
        assertFalse(instance.equals(v));
    }

    /**
     * Test of equals method, of class V3D_Vector_d.
     */
    @Test
    public void testEquals_V3D_Vector_d_double() {
        System.out.println("equals");
        double epsilon = 1/10000d;
        double ediv2 = epsilon / 2.0d;
        V3D_Vector_d v = new V3D_Vector_d(1, 1, 1);
        V3D_Vector_d instance = new V3D_Vector_d(1, 1, 1);
        assertTrue(instance.equals(epsilon, v));
        // Test 2
        instance = new V3D_Vector_d(1, 1, 0);
        assertFalse(instance.equals(epsilon, v));
        // Test 3
        instance = new V3D_Vector_d(1, 1, 1 + ediv2);
        assertTrue(instance.equals(epsilon, v));
        // Test 4
        instance = new V3D_Vector_d(1, 1 + ediv2, 1);
        assertTrue(instance.equals(epsilon, v));
        // Test 5
        instance = new V3D_Vector_d(1 + ediv2, 1, 1);
        assertTrue(instance.equals(epsilon, v));
        
    }

    /**
     * Test of getDX method, of class V3D_Vector_d.
     */
    @Test
    public void testGetDX_int() {
        System.out.println("getDX");
        V3D_Vector_d instance = new V3D_Vector_d(1, 1, 1);
        double expResult = 1d;
        assertTrue(expResult == instance.dx);
    }

    /**
     * Test of getDY method, of class V3D_Vector_d.
     */
    @Test
    public void testGetDY_int() {
        System.out.println("getDY");
        V3D_Vector_d instance = new V3D_Vector_d(1, 1, 1);
        double expResult = 1d;
        assertTrue(expResult==instance.dy);
    }

    /**
     * Test of getDZ method, of class V3D_Vector_d.
     */
    @Test
    public void testGetDZ_int() {
        System.out.println("getDZ");
        V3D_Vector_d instance = new V3D_Vector_d(1, 1, 1);
        double expResult = 1d;
        assertTrue(expResult==instance.dz);
    }

    /**
     * Test of getDX method, of class V3D_Vector_d.
     */
    @Test
    public void testGetDX_0args() {
        System.out.println("getDX");
        V3D_Vector_d instance = new V3D_Vector_d(1, 1, 1);
        double expResult = 1d;
        double result = instance.dx;
        assertTrue(expResult==result);
    }

    /**
     * Test of getDY method, of class V3D_Vector_d.
     */
    @Test
    public void testGetDY_0args() {
        System.out.println("getDY");
        V3D_Vector_d instance = new V3D_Vector_d(1, 1, 1);
        double expResult = 1d;
        double result = instance.dy;
        assertTrue(expResult==result);
    }

    /**
     * Test of getDZ method, of class V3D_Vector_d.
     */
    @Test
    public void testGetDZ_0args() {
        System.out.println("getDZ");
        V3D_Vector_d instance = new V3D_Vector_d(1, 1, 1);
        double expResult = 1d;
        double result = instance.dz;
        assertTrue(expResult==result);
    }

    /**
     * Test of rotate method, of class V3D_Vector_d.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        V3D_Vector_d uv = new V3D_Vector_d(0, 1, 0);
        double epsilon = 1/10000d;
        double Pi = Math.PI;
        double theta = Pi / 2d;
        V3D_Vector_d instance = new V3D_Vector_d(1, 0, 0);
        V3D_Vector_d expResult = new V3D_Vector_d(0, 0, -1);
        V3D_Vector_d result = instance.rotate(uv, theta);
        assertTrue(expResult.equals(epsilon, result));
        // Test 2
        // From Example 2: https://graphics.stanford.edu/courses/cs348a-17-winter/Papers/quaternion.pdf
        uv = new V3D_Vector_d(1, 1, 1).getUnitVector();
        theta = Pi * 2d / 3d;
        instance = new V3D_Vector_d(1, 0, 0);
        expResult = new V3D_Vector_d(0, 1, 0);
        result = instance.rotate(uv, theta);
        assertTrue(expResult.equals(epsilon, result));
//        // Test 3 Fails :(?
//        // From Example 1: https://www.imsc.res.in/~knr/131129workshop/writeup_knr.pdf
//        uv = new V3D_Vector_d(0, 1, 0);
//        theta = Pi / 3d;
//        instance = new V3D_Vector_d(1, -1, 2);
//        double sqrt3 = new double(3).getSqrt();
//        double x = (double.TEN.add(
//                double.valueOf(4).multiply(sqrt3))).divide(8);
//        double y = (double.ONE.add(
//                double.valueOf(2).multiply(sqrt3))).divide(8);
//        double z = (double.valueOf(14).subtract(
//                double.valueOf(3).multiply(sqrt3))).divide(8);        
//        expResult = new V3D_Vector_d(x, y, z);
//        result = instance.rotate(axisOfRotation, theta);
//        assertTrue(expResult.equals(result));
        // Test 4
        // From Case 1 https://www.tobynorris.com/work/prog/csharp/quatview/help/case_1.htm
        uv = new V3D_Vector_d(0, 0, 1);
        theta = -Pi / 6d;
        instance = new V3D_Vector_d(0.6d, 0.8d, 0d);
        expResult = new V3D_Vector_d(0.9196d, 0.3928d, 0d);
        result = instance.rotate(uv, theta);
        assertTrue(expResult.equals(epsilon, result));
//        // Test 5
//        // From Case 2 https://www.tobynorris.com/work/prog/csharp/quatview/help/case_1.htm
//        oom = -4;
//        axisOfRotation = new V3D_Vector_d(0, 0, 1);
//        //theta = Pi.divide(6).negate().subtract(
//        theta = double.valueOf("53.13").divide(double.valueOf(180)).multiply(Pi).negate();
//        instance = new V3D_Vector_d(0.6, 0.8, 0);
//        expResult = new V3D_Vector_d(0.1196, 0.9928, 0);
//        result = instance.rotate(axisOfRotation, theta);
//        assertTrue(expResult.equals(result));
        // Test 6
        uv = new V3D_Vector_d(0, 0, 1);
        theta = Pi / 2d;
        instance = new V3D_Vector_d(1, 0, 0);
        expResult = new V3D_Vector_d(0, 1, 0);
        result = instance.rotate(uv, theta);
        assertTrue(expResult.equals(epsilon, result));
        // Test 7
        uv = new V3D_Vector_d(1, 1, 0).getUnitVector();
        theta = Pi;
        instance = new V3D_Vector_d(1, 0, 0);
        expResult = new V3D_Vector_d(0, 1, 0);
        result = instance.rotate(uv, theta);
        assertTrue(expResult.equals(epsilon, result));
        // Test 8
        uv = new V3D_Vector_d(1, 1, 0).getUnitVector();
        theta = Pi;
        instance = new V3D_Vector_d(2, 0, 0);
        expResult = new V3D_Vector_d(0, 2, 0);
        result = instance.rotate(uv, theta);
        assertTrue(expResult.equals(epsilon, result));
        // Test 9
        uv = new V3D_Vector_d(1, 1, 0).getUnitVector();
        theta = Pi;
        instance = new V3D_Vector_d(3, 1, 0);
        expResult = new V3D_Vector_d(1, 3, 0);
        result = instance.rotate(uv, theta);
        assertTrue(expResult.equals(epsilon, result));
        // Test 10
        uv = new V3D_Vector_d(1, 1, 0).getUnitVector();
        theta = Pi;
        instance = new V3D_Vector_d(3, 2, 1);
        expResult = new V3D_Vector_d(2, 3, -1);
        result = instance.rotate(uv, theta);
        assertTrue(expResult.equals(epsilon, result));
        // Test 11
        uv = new V3D_Vector_d(1, 1, 0).getUnitVector();
        theta = Pi * 2d;
        instance = new V3D_Vector_d(3, 2, 1);
        expResult = new V3D_Vector_d(3, 2, 1);
        result = instance.rotate(uv, theta);
        assertTrue(expResult.equals(epsilon, result));
    }

}
