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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigInteger;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * Test of V3D_Vector class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_VectorTest extends V3D_Test {

    public V3D_VectorTest() {
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
        int oom = -1;
        V3D_Vector instance = new V3D_Vector(P0P0P0, oom);
        String expResult = "V3D_Vector("
                + "dx=Math_BigRationalSqrt(x=0, negative=false, sqrtx=0, oomi=-1, sqrtxapprox=null, oom=-1), "
                + "dy=Math_BigRationalSqrt(x=0, negative=false, sqrtx=0, oomi=-1, sqrtxapprox=null, oom=-1), "
                + "dz=Math_BigRationalSqrt(x=0, negative=false, sqrtx=0, oomi=-1, sqrtxapprox=null, oom=-1), "
                + "m=Math_BigRationalSqrt(x=0, negative=false, sqrtx=0, oomi=-1, sqrtxapprox=null, oom=-1))";
        String result = instance.toString();
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of equals method, of class V3D_Vector.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        int oom = -1;
        Object o = new V3D_Vector(P0P0P0, oom);
        V3D_Vector instance = new V3D_Vector(P0P0P0, oom);
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 2
        o = new V3D_Vector(P0P0P0, oom);
        instance = new V3D_Vector(P1P0P0, oom);
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
        int oom = -1;
        V3D_Vector v = new V3D_Vector(P0P1P0, oom);
        V3D_Vector instance = new V3D_Vector(P1P0P0, oom);
        Math_BigRational expResult = P0;
        Math_BigRational result = instance.getDotProduct(v);
        assertEquals(expResult, result);
        // Test 2
        v = new V3D_Vector(P0P1P0, oom);
        instance = new V3D_Vector(P0P0N1, oom);
        expResult = P0;
        result = instance.getDotProduct(v);
        assertEquals(expResult, result);
        // Test 3
        v = new V3D_Vector(P1P1P1, oom);
        instance = new V3D_Vector(N1N1N1, oom);
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
        int oom = -1;
        V3D_Vector v;
        V3D_Vector instance;
        // Test 1
        v = new V3D_Vector(P1P0P0, oom);
        instance = new V3D_Vector(P0P1P0, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P0P1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0N1P0, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P0P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1P1P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1N1P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1N1P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P1P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P1N1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0N1N1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0N1P1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P1P1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P1N1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0N1N1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0N1P1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P1P1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1P1N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1P1P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P1P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P1N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1N1N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1N1P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1N1P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1N1N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1P0P0, oom);
        assertFalse(instance.isOrthogonal(v));
        // Test 2
        v = new V3D_Vector(P1P1P0, oom);
        instance = new V3D_Vector(P1P0P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1N1P0, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0N1P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1N1P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P0P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P1P0, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P1P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1P1P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1P1N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1P0N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1N1N1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0N1N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1N1N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P0N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P1N1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P1N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P0N1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1P1P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1P0P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1N1P1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0N1P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1N1P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P0P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P1P1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P1P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P0P1, oom);
        assertTrue(instance.isOrthogonal(v));
        // Test 3
        v = new V3D_Vector(P1P1P1, oom);
        instance = new V3D_Vector(P1P1P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1P0P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1N1P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0N1P1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1N1N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P0P1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P1P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P1P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P0P1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1P1P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1P0P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1N1P0, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0N1P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1N1P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P0P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P1P0, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P1P0, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P0P0, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1P1N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1P0N1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P1N1N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0N1N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1N1N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P0N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(N1P1N1, oom);
        assertFalse(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P1N1, oom);
        assertTrue(instance.isOrthogonal(v));
        instance = new V3D_Vector(P0P0N1, oom);
        assertFalse(instance.isOrthogonal(v));
    }

    /**
     * Test of getMagnitude method, of class V3D_Vector.
     */
    @Test
    public void testGetMagnitude() {
        System.out.println("getMagnitude");
        int oom = 0;
        V3D_Vector instance = new V3D_Vector(P0P0P0, oom);
        Math_BigRational expResultSquared;
        BigDecimal expResult = P0.toBigDecimal();
        int precision;
        BigDecimal result = instance.getMagnitude().toBigDecimal(oom);
        assertEquals(expResult, result);
        // Test 2
        oom = -1;
        expResult = P0.toBigDecimal();
        result = instance.getMagnitude().toBigDecimal(oom);
        assertEquals(expResult, result);
        // Test 3
        oom = -1;
        instance = new V3D_Vector(P1P1P0, oom);
        expResultSquared = P2;
        precision = 3;
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(1 - oom));
        result = instance.getMagnitude().toBigDecimal(oom);
        assertEquals(expResult, result);
        // Test 4
        oom = -2;
        expResultSquared = P2;
        precision = 1 - oom;
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(precision));
        result = instance.getMagnitude().toBigDecimal(oom);
        assertEquals(expResult, result);
        // Test 5
        oom = -10;
        expResultSquared = P2;
        precision = 1 - oom;
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(precision));
        result = instance.getMagnitude().toBigDecimal(oom);
        assertEquals(expResult, result);
        // Test 6
        oom = -1;
        instance = new V3D_Vector(P1P1P1, oom);
        expResultSquared = P3;
        precision = 1 - oom;
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(precision));
        result = instance.getMagnitude().toBigDecimal(oom);
        assertEquals(expResult, result);
        // Test 7
        oom = -8;
        instance = new V3D_Vector(P1P1P1, oom);
        expResultSquared = P3;
        precision = 1 - oom;
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(precision));
        result = instance.getMagnitude().toBigDecimal(oom);
        assertEquals(expResult, result);
        // Test 8
        oom = -100;
        instance = new V3D_Vector(P10, P10, P10, oom);
        int x2 = 300;
        expResultSquared = Math_BigRational.valueOf(x2);
        precision = Math_BigInteger.getOrderOfMagnitudeOfMostSignificantDigit(BigInteger.valueOf(x2).sqrt()) + 1 - oom;
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(precision));
        result = instance.getMagnitude().toBigDecimal(oom);
        assertEquals(expResult, result);
        // Test 9
        instance = new V3D_Vector(P3, P4, N4, oom);
        x2 = 41;
        expResultSquared = Math_BigRational.valueOf(x2);
        precision = Math_BigInteger.getOrderOfMagnitudeOfMostSignificantDigit(BigInteger.valueOf(x2).sqrt()) + 1 - oom;
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(precision));
        result = instance.getMagnitude().toBigDecimal(oom);
        assertEquals(expResult, result);
        // Test 10
        instance = new V3D_Vector(P7, P8, N4, oom);
        expResultSquared = P7.pow(2).add(P8.pow(2).add(N4.pow(2)));
        precision = Math_BigInteger.getOrderOfMagnitudeOfMostSignificantDigit(expResultSquared.toBigDecimal().toBigInteger().sqrt()) + 1 - oom;
        expResult = expResultSquared.toBigDecimal().sqrt(new MathContext(precision));
        result = instance.getMagnitude().toBigDecimal(oom);
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
        int oom = -1;
        V3D_Vector v = new V3D_Vector(P0P0P0, oom);
        V3D_Vector instance = new V3D_Vector(P1P1P1, oom);
        assertFalse(instance.isScalarMultiple(v));
        // Test 2
        v = new V3D_Vector(N1N1N1, oom);
        instance = new V3D_Vector(P1P1P1, oom);
        assertTrue(instance.isScalarMultiple(v));
        // Test 3
        v = new V3D_Vector(P1P0P0, oom);
        instance = new V3D_Vector(P0P1P1, oom);
        assertFalse(instance.isScalarMultiple(v));
        // Test 4
        v = new V3D_Vector(P0, P1, P10000, oom);
        instance = new V3D_Vector(P0, P1, P10001, oom);
        assertFalse(instance.isScalarMultiple(v));
    }

    /**
     * Test of getCrossProduct method, of class V3D_Vector.
     */
    @Test
    public void testGetCrossProduct() {
        System.out.println("getCrossProduct");
        int oom = -1;
        V3D_Vector v = new V3D_Vector(P1P1P1, oom);
        V3D_Vector instance = new V3D_Vector(N1N1N1, oom);
        V3D_Vector expResult = new V3D_Vector(P0P0P0, oom);
        V3D_Vector result = instance.getCrossProduct(v, oom);
        assertEquals(expResult, result);
        // Test 2
        v = new V3D_Vector(P1P1P1, oom);
        instance = new V3D_Vector(P1P1P0, oom);
        expResult = new V3D_Vector(P1N1P0, oom);
        result = instance.getCrossProduct(v, oom);
        assertEquals(expResult, result);
        // Test 3
        v = new V3D_Vector(P1P1P0, oom);
        instance = new V3D_Vector(P1P1P1, oom);
        expResult = new V3D_Vector(N1P1P0, oom);
        result = instance.getCrossProduct(v, oom);
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
        int oom = -1;
        V3D_Vector v = V3D_Environment.i;
        V3D_Vector instance = new V3D_Vector(-1, 0, 0, oom);
        assertTrue(instance.isReverse(v));
        // Test 2
    }

    /**
     * Test of isZeroVector method, of class V3D_Vector.
     */
    @Test
    public void testIsZeroVector() {
        System.out.println("isZeroVector");
        int oom = -1;
        V3D_Vector instance = new V3D_Vector(0, 0, 0, oom);
        assertTrue(instance.isZeroVector());
        // Test 2
        instance = new V3D_Vector(1, 0, 0, oom);
        assertFalse(instance.isZeroVector());
    }

    /**
     * Test of multiply method, of class V3D_Vector.
     */
    @Test
    public void testMultiply() {
        System.out.println("multiply");
        int oom = -1;
        Math_BigRational s = Math_BigRational.ZERO;
        V3D_Vector instance = new V3D_Vector(0, 0, 0, oom);
        V3D_Vector expResult = new V3D_Vector(0, 0, 0, oom);
        V3D_Vector result = instance.multiply(s);
        assertEquals(expResult, result);
        // Test 2
        s = Math_BigRational.ZERO;
        instance = new V3D_Vector(10, 10, 10, oom);
        expResult = new V3D_Vector(0, 0, 0, oom);
        result = instance.multiply(s);
        assertEquals(expResult, result);
        // Test 3
        s = Math_BigRational.TWO;
        instance = new V3D_Vector(10, 10, 10, oom);
        expResult = new V3D_Vector(20, 20, 20, oom);
        result = instance.multiply(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class V3D_Vector.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        int oom = -1;
        V3D_Vector instance = new V3D_Vector(0, 0, 0, oom);
        V3D_Vector v = new V3D_Vector(0, 0, 0, oom);
        V3D_Vector expResult = new V3D_Vector(0, 0, 0, oom);
        V3D_Vector result = instance.add(v);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Vector(0, 0, 0, oom);
        v = new V3D_Vector(1, 1, 1, oom);
        expResult = new V3D_Vector(1, 1, 1, oom);
        result = instance.add(v);
        assertEquals(expResult, result);
        // Test 3
        instance = new V3D_Vector(2, 3, 4, oom);
        v = new V3D_Vector(7, 1, 11, oom);
        expResult = new V3D_Vector(9, 4, 15, oom);
        result = instance.add(v);
        assertEquals(expResult, result);
        // Test 3
        instance = new V3D_Vector(-2, 3, -4, oom);
        v = new V3D_Vector(7, 1, 11, oom);
        expResult = new V3D_Vector(5, 4, 7, oom);
        result = instance.add(v);
        assertEquals(expResult, result);
    }

    /**
     * Test of subtract method, of class V3D_Vector.
     */
    @Test
    public void testSubtract() {
        System.out.println("subtract");
        int oom = -1;
        V3D_Vector instance = new V3D_Vector(0, 0, 0, oom);
        V3D_Vector v = new V3D_Vector(0, 0, 0, oom);
        V3D_Vector expResult = new V3D_Vector(0, 0, 0, oom);
        V3D_Vector result = instance.add(v);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Vector(0, 0, 0, oom);
        v = new V3D_Vector(1, 1, 1, oom);
        expResult = new V3D_Vector(-1, -1, -1, oom);
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
        int oom = -1;
        V3D_Vector instance = new V3D_Vector(0, 0, 0, oom);
        V3D_Vector expResult = new V3D_Vector(0, 0, 0, oom);
        V3D_Vector result = instance.reverse();
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Vector(1, 1, 1, oom);
        expResult = new V3D_Vector(-1, -1, -1, oom);
        result = instance.reverse();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMagnitudeSquared method, of class V3D_Vector.
     */
    @Test
    public void testGetMagnitudeSquared() {
        System.out.println("getMagnitudeSquared");
        int oom = -1;
        V3D_Vector instance = new V3D_Vector(0, 0, 0, oom);
        Math_BigRational expResult = Math_BigRational.ZERO;
        Math_BigRational result = instance.getMagnitudeSquared();
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = new V3D_Vector(1, 1, 1, oom);
        expResult = Math_BigRational.valueOf(3);
        result = instance.getMagnitudeSquared();
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        instance = new V3D_Vector(2, 2, 2, oom);
        expResult = Math_BigRational.valueOf(12);
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
        instance = new V3D_Vector(100, 0, 0, oom);
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
        Math_BigRational[][] m = new Math_BigRational[1][3];
        m[0][0] = Math_BigRational.ONE;
        m[0][1] = Math_BigRational.ZERO;
        m[0][2] = Math_BigRational.ZERO;
        Math_Matrix_BR expResult = new Math_Matrix_BR(m);
        Math_Matrix_BR result = instance.getAsMatrix();
        assertEquals(expResult, result);
        // Test 2
    }

}
