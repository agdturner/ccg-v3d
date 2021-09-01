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

import uk.ac.leeds.ccg.v3d.V3D_Test;
import ch.obermuhlner.math.big.BigRational;
import java.math.BigDecimal;
import java.math.MathContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

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
     * Test of toString method, of class V3D_Vector.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Vector instance = new V3D_Vector(P0P0P0);
        String expResult = "V3D_Vector(dx=0, dy=0, dz=0, "
                + "m=Math_BigRationalSqrt(x=0, sqrtx=0, sqrtxapprox=null, "
                + "oom=0))";
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
        int oom = 0;
        V3D_Vector instance = new V3D_Vector(P0P0P0);
        BigRational expResultSquared;
        BigDecimal expResult = P0.toBigDecimal();
        int oomsqrt;
        BigDecimal result = instance.getMagnitude(oom);
        assertEquals(expResult, result);
        // Test 2
        oom = -1;
        expResult = P0.toBigDecimal();
        result = instance.getMagnitude(oom);
        assertEquals(expResult, result);
        // Test 3
        oom = -1;
        instance = new V3D_Vector(P1P1P0);
        expResultSquared = P2;
        oomsqrt = Math_BigRationalSqrt.getOOM(expResultSquared, oom);
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(oomsqrt - oom));
        result = instance.getMagnitude(oom);
        assertEquals(expResult, result);
        // Test 4
        oom = -2;
        expResultSquared = P2;
        oomsqrt = Math_BigRationalSqrt.getOOM(expResultSquared, oom);
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(oomsqrt - oom));
        result = instance.getMagnitude(oom);
        assertEquals(expResult, result);
        // Test 5
        oom = -10;
        expResultSquared = P2;
        oomsqrt = Math_BigRationalSqrt.getOOM(expResultSquared, oom);
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(oomsqrt - oom));
        result = instance.getMagnitude(oom);
        assertEquals(expResult, result);
        // Test 6
        oom = -1;
        instance = new V3D_Vector(P1P1P1);
        expResultSquared = P3;
        oomsqrt = Math_BigRationalSqrt.getOOM(expResultSquared, oom);
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(oomsqrt - oom));
        result = instance.getMagnitude(oom);
        assertEquals(expResult, result);
        // Test 7
        oom = -8;
        instance = new V3D_Vector(P1P1P1);
        expResultSquared = P3;
        oomsqrt = Math_BigRationalSqrt.getOOM(expResultSquared, oom);
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(oomsqrt - oom));
        result = instance.getMagnitude(oom);
        assertEquals(expResult, result);
        // Test 8
        oom = -100;
        instance = new V3D_Vector(P10, P10, P10);
        expResultSquared = BigRational.valueOf(300);
        oomsqrt = Math_BigRationalSqrt.getOOM(expResultSquared, oom);
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(oomsqrt - oom));
        result = instance.getMagnitude(oom);
        assertEquals(expResult, result);
        // Test 9
        instance = new V3D_Vector(P3, P4, N4);
        expResultSquared = BigRational.valueOf(41);
        oomsqrt = Math_BigRationalSqrt.getOOM(expResultSquared, oom);
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(oomsqrt - oom));
        result = instance.getMagnitude(oom);
        assertEquals(expResult, result);
        // Test 10
        instance = new V3D_Vector(P7, P8, N4);
        expResultSquared = P7.pow(2).add(P8.pow(2).add(N4.pow(2)));
        oomsqrt = Math_BigRationalSqrt.getOOM(expResultSquared, oom);
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(oomsqrt - oom));
        result = instance.getMagnitude(oom);
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
    public void testIsScalarMultiple() {
        System.out.println("isScalarMultiple");
        V3D_Vector v = new V3D_Vector(P0P0P0);
        V3D_Vector instance = new V3D_Vector(P1P1P1);
        assertFalse(instance.isScalarMultiple(v));
        // Test 2
        v = new V3D_Vector(N1N1N1);
        instance = new V3D_Vector(P1P1P1);
        assertTrue(instance.isScalarMultiple(v));
        // Test 3
        v = new V3D_Vector(P1P0P0);
        instance = new V3D_Vector(P0P1P1);
        assertFalse(instance.isScalarMultiple(v));
        // Test 4
        v = new V3D_Vector(P0, P1, P10000);
        instance = new V3D_Vector(P0, P1, P10001);
        assertFalse(instance.isScalarMultiple(v));
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

    /**
     * Test of equals method, of class V3D_Vector.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        Object o = V3D_Environment.i;
        V3D_Vector instance = V3D_Environment.i;
        assertTrue(instance.equals(o));
        // Test 2
        instance = V3D_Environment.j;
        assertFalse(instance.equals(o));
    }

    /**
     * Test of isReverse method, of class V3D_Vector.
     */
    @Test
    public void testIsReverse() {
        System.out.println("isReverse");
        V3D_Vector v = V3D_Environment.i;
        V3D_Vector instance = new V3D_Vector(-1, 0, 0);
        assertTrue(instance.isReverse(v));
        // Test 2
    }

    /**
     * Test of isZeroVector method, of class V3D_Vector.
     */
    @Test
    public void testIsZeroVector() {
        System.out.println("isZeroVector");
        V3D_Vector instance = new V3D_Vector(0, 0, 0);
        assertTrue(instance.isZeroVector());
        // Test 2
        instance = new V3D_Vector(1, 0, 0);
        assertFalse(instance.isZeroVector());
    }

    /**
     * Test of multiply method, of class V3D_Vector.
     */
    @Test
    public void testMultiply() {
        System.out.println("multiply");
        BigRational s = BigRational.ZERO;
        V3D_Vector instance = new V3D_Vector(0, 0, 0);
        V3D_Vector expResult = new V3D_Vector(0, 0, 0);
        V3D_Vector result = instance.multiply(s);
        assertEquals(expResult, result);
        // Test 2
        s = BigRational.ZERO;
        instance = new V3D_Vector(10, 10, 10);
        expResult = new V3D_Vector(0, 0, 0);
        result = instance.multiply(s);
        assertEquals(expResult, result);
        // Test 3
        s = BigRational.TWO;
        instance = new V3D_Vector(10, 10, 10);
        expResult = new V3D_Vector(20, 20, 20);
        result = instance.multiply(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class V3D_Vector.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        V3D_Vector instance = new V3D_Vector(0, 0, 0);
        V3D_Vector v = new V3D_Vector(0, 0, 0);
        V3D_Vector expResult = new V3D_Vector(0, 0, 0);
        V3D_Vector result = instance.add(v);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Vector(0, 0, 0);
        v = new V3D_Vector(1, 1, 1);
        expResult = new V3D_Vector(1, 1, 1);
        result = instance.add(v);
        assertEquals(expResult, result);
    }

    /**
     * Test of subtract method, of class V3D_Vector.
     */
    @Test
    public void testSubtract() {
        System.out.println("subtract");
        V3D_Vector instance = new V3D_Vector(0, 0, 0);
        V3D_Vector v = new V3D_Vector(0, 0, 0);
        V3D_Vector expResult = new V3D_Vector(0, 0, 0);
        V3D_Vector result = instance.add(v);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Vector(0, 0, 0);
        v = new V3D_Vector(1, 1, 1);
        expResult = new V3D_Vector(-1, -1, -1);
        result = instance.subtract(v);
        //assertTrue(expResult.compareTo(result) == 0);
        assertEquals(expResult, result);
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
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Vector(1, 1, 1);
        expResult = new V3D_Vector(-1, -1, -1);
        result = instance.reverse();
        assertEquals(expResult, result);
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
        int oom = -1;
        V3D_Vector instance = V3D_Environment.i;
        V3D_Vector expResult = V3D_Environment.i;
        V3D_Vector result = instance.getUnitVector(oom);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Vector(100, 0, 0);
        expResult = V3D_Environment.i;
        result = instance.getUnitVector(oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAsMatrix method, of class V3D_Vector.
     */
    @Test
    public void testGetAsMatrix() {
        System.out.println("getAsMatrix");
        V3D_Vector instance = V3D_Environment.i;
        Math_Matrix_BR expResult = new Math_Matrix_BR(1, 3);
        BigRational[][] m = expResult.getM();
        m[0][0] = BigRational.ONE;
        m[0][1] = BigRational.ZERO;
        m[0][2] = BigRational.ZERO;
        Math_Matrix_BR result = instance.getAsMatrix();
        assertEquals(expResult, result);
        // Test 2
    }

}
