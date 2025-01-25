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
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Vector;

/**
 * Test of V3D_Vector class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_VectorTest extends V3D_Test {

    public V3D_VectorTest() {}
    
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
     * Test of getDotProduct method, of class V3D_Vector.
     */
    @Test
    public void testGetDotProduct() {
        System.out.println("getDotProduct");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector v = P0P1P0;
        V3D_Vector instance = P1P0P0;
        BigRational expResult = P0;
        BigRational result = instance.getDotProduct(v, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        v = P0P1P0;
        instance = P0P0N1;
        expResult = P0;
        result = instance.getDotProduct(v, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 3
        v = P1P1P1;
        instance = N1N1N1;
        expResult = N3;
        result = instance.getDotProduct(v, oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isOrthogonal method, of class V3D_Vector.
     */
    @Test
    public void testIsOrthogonal() {
        System.out.println("isOrthogonal");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector v;
        V3D_Vector instance;
        // Test 1
        v = P1P0P0;
        instance = P0P1P0;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0P0P1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0N1P0;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = N1P0P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1P1P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1N1P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1N1P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P1P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P0P1N1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0N1N1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0N1P1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0P1P1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0P1N1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0N1N1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0N1P1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0P1P1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P1P1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1P1P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P1P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1N1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1N1P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1N1P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1P0P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        // Test 2
        v = P1P1P0;
        instance = P1P0P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1N1P0;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0N1P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1N1P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P0P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P1P0;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0P1P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1P1P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1P1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1P0N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1N1N1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0N1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P0N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P1N1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0P1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P0P0N1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P1P1P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1P0P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1N1P1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0N1P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1N1P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P0P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P1P1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0P1P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P0P0P1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        // Test 3
        v = P1P1P1;
        instance = P1P1P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1P0P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1N1P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P0N1P1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P0P1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = N1P1P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P0P1P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P0P0P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1P1P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1P0P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1N1P0;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0N1P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1N1P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P0P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P1P0;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0P1P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P0P0P0;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P1P1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1P0N1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P1N1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P0N1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P0N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P0P1N1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0P0N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
    }

//    /**
//     * Test of getMagnitude method, of class V3D_Vector.
//     */
//    @Test
//    public void testGetMagnitude_0args() {
//        System.out.println("getMagnitude");
//        int oom = 0;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Vector instance = new V3D_Vector(P0P0P0);
//        Math_BigRationalSqrt expResult = Math_BigRationalSqrt.ZERO;
//        Math_BigRationalSqrt result = instance.getMagnitude(oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 1
//        oom = -1;
//        instance = new V3D_Vector(P1P1P0);
//        expResult = new Math_BigRationalSqrt(P2, oom, rm);
//        result = instance.getMagnitude(oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 2
//        oom = -1;
//        instance = new V3D_Vector(P1P1P1);
//        expResult = new Math_BigRationalSqrt(P3, oom, rm);
//        result = instance.getMagnitude(oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 3
//        oom = -100;
//        instance = new V3D_Vector(P10, P10, P10);
//        expResult = new Math_BigRationalSqrt(300L, oom, rm);
//        result = instance.getMagnitude(oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 4
//        instance = new V3D_Vector(P3, P4, N4);
//        expResult = new Math_BigRationalSqrt(41L, oom, rm);
//        result = instance.getMagnitude(oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 5
//        instance = new V3D_Vector(P7, P8, N4);
//        expResult = new Math_BigRationalSqrt(P7.pow(2).add(P8.pow(2).add(N4.pow(2))), oom, rm);
//        result = instance.getMagnitude(oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//    }

    /**
     * Test of initMagnitude method, of class V3D_Vector.
     */
    @Test
    public void testInitMagnitude() {
        System.out.println("initMagnitude");
        assertTrue(true); // No need to test.
    }
    
    /**
     * Test of isScalarMultiple method, of class V3D_VectorDouble.
     */
    @Test
    public void testIsScalarMultiple_V3D_Vector() {
        System.out.println("isScalarMultiple");
        V3D_Vector v = P0P0P0;
        V3D_Vector instance = P1P1P1;
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
        v = new V3D_Vector(0d, 1d, 10000d);
        instance = new V3D_Vector(0d, 1d, 10000d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 7
        v = new V3D_Vector(0d, 1d, 10000d);
        instance = new V3D_Vector(0d, 1d, 10001d);
        assertFalse(instance.isScalarMultiple(v));        
        // Test 8
        v = new V3D_Vector(0d, 1d, 1d);
        instance = new V3D_Vector(0d, 1d, 1d);
        //assertFalse(instance.isScalarMultiple(v));
        assertTrue(instance.isScalarMultiple(v));
        // Test 9
        v = new V3D_Vector(0d, 1d, 10000d);
        instance = new V3D_Vector(0d, 2d, 20000d);
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
        instance = new V3D_Vector(0d, 1d, 10000d);
        v = new V3D_Vector(0d, 1d, 10000d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 23
        v = new V3D_Vector(0d, 1d, 10001d);
        assertFalse(instance.isScalarMultiple(v));  
        // Test 24
        v = new V3D_Vector(0d, 1.01d, 10000d);
        assertFalse(instance.isScalarMultiple(v));  
        // Test 25
        v = new V3D_Vector(0.01d, 1d, 10000d);
        assertFalse(instance.isScalarMultiple(v));  
        
        // Test 26
        instance = new V3D_Vector(0d, 1d, 10000d);
        v = new V3D_Vector(0d, 2d, 20000d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 27
        instance = new V3D_Vector(3d, 1d, 10000d);
        v = new V3D_Vector(6d, 2d, 20000d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 28
        instance = new V3D_Vector(3d, 1d, 10000d);
        v = new V3D_Vector(-6d, -2d, -20000d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 29
        instance = new V3D_Vector(-3d, -1d, -10000d);
        v = new V3D_Vector(6d, 2d, 20000d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 30
        instance = new V3D_Vector(-3d, -1d, -10000d);
        v = new V3D_Vector(-6d, -2d, -20000d);
        assertTrue(instance.isScalarMultiple(v));
        
        // Test 31
        instance = new V3D_Vector(-3d, 0d, -10000d);
        v = new V3D_Vector(+6d, 0d, -20000d);
        assertFalse(instance.isScalarMultiple(v));
        // Test 32
        instance = new V3D_Vector(-3d, 0d, -10000d);
        v = new V3D_Vector(-6d, 0d, -20000d);
        assertTrue(instance.isScalarMultiple(v));
    }

    /**
     * Test of isScalarMultiple method, of class V3D_Vector.
     */
    @Test
    public void testIsScalarMultiple_V3D_Vector_int_RoundingMode() {
        System.out.println("isScalarMultiple");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        BigRational epsilon = N0_1E12;
        V3D_Vector v = P0P0P0;
        V3D_Vector instance = P1P1P1;
        assertTrue(instance.isScalarMultiple(v, oom, rm));
        // Test 2
        v = N1N1N1;
        instance = P1P1P1;
        assertTrue(instance.isScalarMultiple(v, oom, rm));
        // Test 3
        v = N1N1N1;
        instance = P1P1P1;
        assertTrue(instance.isScalarMultiple(v, oom, rm));
        // Test 4
        v = P1P0P0;
        instance = P0P1P1;
        assertFalse(instance.isScalarMultiple(v, oom, rm));
        // Test 5
        v = new V3D_Vector(P0, P1, P10000);
        instance = new V3D_Vector(P0, P1, P10000);
        assertTrue(instance.isScalarMultiple(v, oom, rm));
        // Test 6
        v = new V3D_Vector(P0, P1, P10000);
        instance = new V3D_Vector(P0, P1, P10001);
        assertFalse(instance.isScalarMultiple(v, oom, rm));
        // Test 7
        v = new V3D_Vector(P0, P1, P1);
        instance = new V3D_Vector(P0, P1, P1.add(epsilon.divide(2)));
        //assertFalse(instance.isScalarMultiple(v, epsilon));
        assertTrue(instance.isScalarMultiple(v, oom, rm));
        // Test 8
        v = new V3D_Vector(P0, P1, P10000);
        instance = new V3D_Vector(P0, P2, P10000.multiply(2));
        assertTrue(instance.isScalarMultiple(v, oom, rm));
    }

    /**
     * Test of getCrossProduct method, of class V3D_Vector.
     */
    @Test
    public void testGetCrossProduct() {
        System.out.println("getCrossProduct");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector v = P1P1P1;
        V3D_Vector instance = N1N1N1;
        V3D_Vector expResult = P0P0P0;
        V3D_Vector result = instance.getCrossProduct(v, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        v = P1P1P1;
        instance = P1P1P0;
        expResult = P1N1P0;
        result = instance.getCrossProduct(v, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 3
        v = P1P1P0;
        instance = P1P1P1;
        expResult = N1P1P0;
        result = instance.getCrossProduct(v, oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class V3D_Vector.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        Object o = V3D_Vector.I;
        V3D_Vector instance = V3D_Vector.I;
        assertTrue(instance.equals(o));
        // Test 2
        instance = V3D_Vector.J;
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
     * Test of isReverse method, of class V3D_Vector.
     */
    @Test
    public void testIsReverse() {
        System.out.println("isReverse");
        V3D_Vector v = V3D_Vector.I;
        V3D_Vector instance = new V3D_Vector(-1, 0, 0);
        assertTrue(instance.isReverse(v));
        // Test 2
    }

    /**
     * Test of isZero method, of class V3D_Vector.
     */
    @Test
    public void testIsZeroVector() {
        System.out.println("isZeroVector");
        V3D_Vector instance = new V3D_Vector(0, 0, 0);
        assertTrue(instance.isZero());
        // Test 2
        instance = new V3D_Vector(1, 0, 0);
        assertFalse(instance.isZero());
    }

    /**
     * Test of multiply method, of class V3D_Vector.
     */
    @Test
    public void testMultiply() {
        System.out.println("multiply");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        BigRational s = BigRational.ZERO;
        V3D_Vector instance = new V3D_Vector(0, 0, 0);
        V3D_Vector expResult = new V3D_Vector(0, 0, 0);
        V3D_Vector result = instance.multiply(s, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        s = BigRational.ZERO;
        instance = new V3D_Vector(10, 10, 10);
        expResult = new V3D_Vector(0, 0, 0);
        result = instance.multiply(s, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 3
        s = BigRational.TWO;
        instance = new V3D_Vector(10, 10, 10);
        expResult = new V3D_Vector(20, 20, 20);
        result = instance.multiply(s, oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of add method, of class V3D_Vector.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector instance = new V3D_Vector(0, 0, 0);
        V3D_Vector v = new V3D_Vector(0, 0, 0);
        V3D_Vector expResult = new V3D_Vector(0, 0, 0);
        V3D_Vector result = instance.add(v, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Vector(0, 0, 0);
        v = new V3D_Vector(1, 1, 1);
        expResult = new V3D_Vector(1, 1, 1);
        result = instance.add(v, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Vector(2, 3, 4);
        v = new V3D_Vector(7, 1, 11);
        expResult = new V3D_Vector(9, 4, 15);
        result = instance.add(v, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Vector(-2, 3, -4);
        v = new V3D_Vector(7, 1, 11);
        expResult = new V3D_Vector(5, 4, 7);
        result = instance.add(v, oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of subtract method, of class V3D_Vector.
     */
    @Test
    public void testSubtract() {
        System.out.println("subtract");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector instance = new V3D_Vector(0, 0, 0);
        V3D_Vector v = new V3D_Vector(0, 0, 0);
        V3D_Vector expResult = new V3D_Vector(0, 0, 0);
        V3D_Vector result = instance.subtract(v, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Vector(0, 0, 0);
        v = new V3D_Vector(1, 1, 1);
        expResult = new V3D_Vector(-1, -1, -1);
        result = instance.subtract(v, oom, rm);
        //assertTrue(expResult.compareTo(result) == 0);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of reverse method, of class V3D_Vector.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        V3D_Vector instance = new V3D_Vector(0, 0, 0);
        V3D_Vector expResult = new V3D_Vector(0, 0, 0);
        V3D_Vector result = instance.reverse();
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Vector(1, 1, 1);
        expResult = new V3D_Vector(-1, -1, -1);
        result = instance.reverse();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getMagnitudeSquared method, of class V3D_Vector.
     */
    @Test
    public void testGetMagnitudeSquared() {
        System.out.println("getMagnitudeSquared");
        V3D_Vector instance = new V3D_Vector(0, 0, 0);
        BigRational expResult = BigRational.ZERO;
        BigRational result = instance.getMagnitudeSquared();
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = new V3D_Vector(1, 1, 1);
        expResult = BigRational.valueOf(3);
        result = instance.getMagnitudeSquared();
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        instance = new V3D_Vector(2, 2, 2);
        expResult = BigRational.valueOf(12);
        result = instance.getMagnitudeSquared();
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getUnitVector method, of class V3D_Vector.
     */
    @Test
    public void testGetUnitVector() {
        System.out.println("getUnitVector");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector instance = V3D_Vector.I;
        V3D_Vector expResult = V3D_Vector.I;
        V3D_Vector result = instance.getUnitVector(oom, rm);
        assertTrue(expResult.equals(result));
        assertTrue(result.getMagnitudeSquared().compareTo(BigRational.ONE) != 1);
        // Test 2
        instance = new V3D_Vector(100, 0, 0);
        expResult = V3D_Vector.I;
        result = instance.getUnitVector(oom, rm);
        assertTrue(expResult.equals(result));
        assertTrue(result.getMagnitudeSquared().compareTo(BigRational.ONE) != 1);
        // Test 3
        instance = new V3D_Vector(100, 100, 0);
        expResult = new V3D_Vector(
                BigRational.valueOf("0.707"),
                BigRational.valueOf("0.707"),
                BigRational.valueOf("0"));
        result = instance.getUnitVector(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        //assertTrue(result.getMagnitudeSquared().compareTo(BigRational.ONE) != 1);
        // Test 4
        instance = new V3D_Vector(0, 100, 100);
        expResult = new V3D_Vector(
                BigRational.valueOf("0"),
                BigRational.valueOf("0.707"),
                BigRational.valueOf("0.707"));
        result = instance.getUnitVector(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 5
        instance = new V3D_Vector(100, 100, 100);
        expResult = new V3D_Vector(
                BigRational.valueOf("0.577"),
                BigRational.valueOf("0.577"),
                BigRational.valueOf("0.577"));
        result = instance.getUnitVector(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 6
        instance = new V3D_Vector(-100, 0, 0);
        expResult = new V3D_Vector(-1,0,0);
        result = instance.getUnitVector(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 7
        instance = new V3D_Vector(-100, -100, 0);
        expResult = new V3D_Vector(
                BigRational.valueOf("-0.707"),
                BigRational.valueOf("-0.707"),
                BigRational.valueOf("0"));
        result = instance.getUnitVector(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 8
        instance = new V3D_Vector(0, -100, -100);
        expResult = new V3D_Vector(
                BigRational.valueOf("0"),
                BigRational.valueOf("-0.707"),
                BigRational.valueOf("-0.707"));
        result = instance.getUnitVector(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 9
        instance = new V3D_Vector(-100, -100, -100);
        expResult = new V3D_Vector(
                BigRational.valueOf("-0.577"),
                BigRational.valueOf("-0.577"),
                BigRational.valueOf("-0.577"));
        result = instance.getUnitVector(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of getDX method, of class V3D_Vector.
     */
    @Test
    public void testGetDX() {
        System.out.println("getDX");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector instance = V3D_Vector.I;
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDX(oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = V3D_Vector.I.reverse();
        expResult = BigRational.ONE.negate();
        result = instance.getDX(oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDY method, of class V3D_Vector.
     */
    @Test
    public void testGetDY() {
        System.out.println("getDY");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector instance = V3D_Vector.J;
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDY(oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = V3D_Vector.J.reverse();
        expResult = BigRational.ONE.negate();
        result = instance.getDY(oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDZ method, of class V3D_Vector.
     */
    @Test
    public void testGetDZ() {
        System.out.println("getDZ");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector instance = V3D_Vector.K;
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDZ(oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = V3D_Vector.K.reverse();
        expResult = BigRational.ONE.negate();
        result = instance.getDZ(oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of divide method, of class V3D_Vector.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        BigRational s = BigRational.TWO;
        V3D_Vector instance = V3D_Vector.I;
        V3D_Vector expResult = new V3D_Vector(new V3D_Point(0.5d, 0d, 0d), -2, rm);
        V3D_Vector result = instance.divide(s, oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getAngle method, of class V3D_Vector.
     */
    @Test
    public void testGetAngle() {
        System.out.println("getAngle");
        V3D_Vector v;
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        v = V3D_Vector.I;
        instance = V3D_Vector.J;
        result = instance.getAngle(v, oom, rm);
        expResult = BigRational.valueOf(V3D_Environment.bd.getPiBy2(oom, rm));
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDirection method, of class V3D_Vector.
     */
    @Test
    public void testGetDirection() {
        System.out.println("getDirection");
        V3D_Vector instance = V3D_Vector.I;
        int expResult = 1;
        int result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 2
        instance = V3D_Vector.J;
        expResult = 1;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 3
        instance = V3D_Vector.K;
        expResult = 1;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 4
        instance = V3D_Vector.I.reverse();
        expResult = 5;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 5
        instance = V3D_Vector.J.reverse();
        expResult = 3;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 6
        instance = V3D_Vector.K.reverse();
        expResult = 2;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 7
        //...
    }

    /**
     * Test of toString method, of class V3D_Vector.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        V3D_Vector v = V3D_Vector.ZERO;
        String result = v.toString();
        String expResult = "V3D_Vector(dx=0, dy=0, dz=0)";
//        String expResult = "V3D_Vector\n"
//                + "(\n"
//                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + " dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
//                + ")";
        //System.out.println(result);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class V3D_Vector.
     */
    @Test
    public void testEquals_V3D_Vector() {
        System.out.println("equals");
        V3D_Vector v = new V3D_Vector(1, 1, 1);
        V3D_Vector instance = new V3D_Vector(1, 1, 1);
        assertTrue(instance.equals(v));
        instance = new V3D_Vector(1, 1, 0);
        assertFalse(instance.equals(v));
    }

    /**
     * Test of getDX method, of class V3D_Vector.
     */
    @Test
    public void testGetDX_int() {
        System.out.println("getDX");
        int oom = 0;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector instance = new V3D_Vector(1, 1, 1);
        BigRational expResult = BigRational.ONE;
        assertTrue(expResult.compareTo(instance.getDX(oom, rm)) == 0);
    }

    /**
     * Test of getDY method, of class V3D_Vector.
     */
    @Test
    public void testGetDY_int() {
        System.out.println("getDY");
        int oom = 0;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector instance = new V3D_Vector(1, 1, 1);
        BigRational expResult = BigRational.ONE;
        assertTrue(expResult.compareTo(instance.getDY(oom, rm)) == 0);
    }

    /**
     * Test of getDZ method, of class V3D_Vector.
     */
    @Test
    public void testGetDZ_int() {
        System.out.println("getDZ");
        int oom = 0;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector instance = new V3D_Vector(1, 1, 1);
        BigRational expResult = BigRational.ONE;
        assertTrue(expResult.compareTo(instance.getDZ(oom, rm)) == 0);
    }

    /**
     * Test of getDX method, of class V3D_Vector.
     */
    @Test
    public void testGetDX_0args() {
        System.out.println("getDX");
        V3D_Vector instance = new V3D_Vector(1, 1, 1);
        Math_BigRationalSqrt expResult = Math_BigRationalSqrt.ONE;
        Math_BigRationalSqrt result = instance.getDX();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDY method, of class V3D_Vector.
     */
    @Test
    public void testGetDY_0args() {
        System.out.println("getDY");
        V3D_Vector instance = new V3D_Vector(1, 1, 1);
        Math_BigRationalSqrt expResult = Math_BigRationalSqrt.ONE;
        Math_BigRationalSqrt result = instance.getDY();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDZ method, of class V3D_Vector.
     */
    @Test
    public void testGetDZ_0args() {
        System.out.println("getDZ");
        V3D_Vector instance = new V3D_Vector(1, 1, 1);
        Math_BigRationalSqrt expResult = Math_BigRationalSqrt.ONE;
        Math_BigRationalSqrt result = instance.getDZ();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getMagnitude0 method, of class V3D_Vector.
     */
    @Test
    public void testGetMagnitude0() {
        System.out.println("getMagnitude0");
    }

    /**
     * Test of getMagnitude method, of class V3D_Vector.
     */
    @Test
    public void testGetMagnitude_int() {
        System.out.println("getMagnitude");
    }

    /**
     * Test of rotate method, of class V3D_Vector.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        V3D_Vector axisOfRotation = new V3D_Vector(0, 1, 0);
        Math_AngleBigRational ma = new Math_AngleBigRational();
        int oom = -3;
        int oomt = oom - 2;
        RoundingMode rm = RoundingMode.HALF_UP;
        BigRational Pi = BigRational.valueOf(
                V3D_Environment.bd.getPi(oomt, rm));
        BigRational theta = Pi.divide(2);
        V3D_Vector instance = new V3D_Vector(1, 0, 0);
        V3D_Vector expResult = new V3D_Vector(0, 0, -1);
        V3D_Vector result = instance.rotate(axisOfRotation, V3D_Environment.bd, theta, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 2
        // From Example 2: https://graphics.stanford.edu/courses/cs348a-17-winter/Papers/quaternion.pdf
        axisOfRotation = new V3D_Vector(1, 1, 1).getUnitVector(oomt, rm);
        theta = Pi.multiply(2).divide(3);
        instance = new V3D_Vector(1, 0, 0);
        expResult = new V3D_Vector(0, 1, 0);
        result = instance.rotate(axisOfRotation, V3D_Environment.bd, theta, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
//        // Test 3 Fails :(
//        // From Example 1: https://www.imsc.res.in/~knr/131129workshop/writeup_knr.pdf
//        axisOfRotation = new V3D_Vector(0, 1, 0);
//        theta = Pi.divide(3);
//        instance = new V3D_Vector(1, -1, 2);
//        BigRational sqrt3 = new Math_BigRationalSqrt(3, oom, rm).getSqrt(oom, rm);
//        BigRational x = (BigRational.TEN.add(
//                BigRational.valueOf(4).multiply(sqrt3))).divide(8);
//        BigRational y = (BigRational.ONE.add(
//                BigRational.valueOf(2).multiply(sqrt3))).divide(8);
//        BigRational z = (BigRational.valueOf(14).subtract(
//                BigRational.valueOf(3).multiply(sqrt3))).divide(8);        
//        expResult = new V3D_Vector(x, y, z);
//        result = instance.rotate(axisOfRotation, V3D_Environment.bd, theta, oom, rm);
//        assertTrue(expResult.equals(result, oom, rm));
        // Test 4
        // From Case 1 https://www.tobynorris.com/work/prog/csharp/quatview/help/case_1.htm
        oom = -4;
        axisOfRotation = new V3D_Vector(0, 0, 1);
        theta = Pi.divide(6).negate();
        instance = new V3D_Vector(0.6, 0.8, 0);
        expResult = new V3D_Vector(0.9196, 0.3928, 0);
        result = instance.rotate(axisOfRotation, V3D_Environment.bd, theta, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
//        // Test 5
//        // From Case 2 https://www.tobynorris.com/work/prog/csharp/quatview/help/case_1.htm
//        oom = -4;
//        axisOfRotation = new V3D_Vector(0, 0, 1);
//        //theta = Pi.divide(6).negate().subtract(
//        theta = BigRational.valueOf("53.13").divide(BigRational.valueOf(180)).multiply(Pi).negate();
//        instance = new V3D_Vector(0.6, 0.8, 0);
//        expResult = new V3D_Vector(0.1196, 0.9928, 0);
//        result = instance.rotate(axisOfRotation, V3D_Environment.bd, theta, oom, rm);
//        assertTrue(expResult.equals(result, oom, rm));
        // Test 6
        oom = -4;
        axisOfRotation = new V3D_Vector(0, 0, 1);
        theta = Pi.divide(2);
        instance = new V3D_Vector(1, 0, 0);
        expResult = new V3D_Vector(0, 1, 0);
        result = instance.rotate(axisOfRotation, V3D_Environment.bd, theta, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 7
        oom = -4;
        axisOfRotation = new V3D_Vector(1, 1, 0).getUnitVector(oomt, rm);
        theta = Pi;
        instance = new V3D_Vector(1, 0, 0);
        expResult = new V3D_Vector(0, 1, 0);
        result = instance.rotate(axisOfRotation, V3D_Environment.bd, theta, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 8
        oom = -4;
        axisOfRotation = new V3D_Vector(1, 1, 0).getUnitVector(oomt, rm);
        theta = Pi;
        instance = new V3D_Vector(2, 0, 0);
        expResult = new V3D_Vector(0, 2, 0);
        result = instance.rotate(axisOfRotation, V3D_Environment.bd, theta, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 9
        oom = -4;
        axisOfRotation = new V3D_Vector(1, 1, 0).getUnitVector(oomt, rm);
        theta = Pi;
        instance = new V3D_Vector(3, 1, 0);
        expResult = new V3D_Vector(1, 3, 0);
        result = instance.rotate(axisOfRotation, V3D_Environment.bd, theta, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 10
        oom = -4;
        axisOfRotation = new V3D_Vector(1, 1, 0).getUnitVector(oomt, rm);
        theta = Pi;
        instance = new V3D_Vector(3, 2, 1);
        expResult = new V3D_Vector(2, 3, -1);
        result = instance.rotate(axisOfRotation, V3D_Environment.bd, theta, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 11
        oom = -4;
        axisOfRotation = new V3D_Vector(1, 1, 0).getUnitVector(oomt, rm);
        theta = Pi.multiply(2);
        instance = new V3D_Vector(3, 2, 1);
        expResult = new V3D_Vector(3, 2, 1);
        result = instance.rotate(axisOfRotation, V3D_Environment.bd, theta, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

}
