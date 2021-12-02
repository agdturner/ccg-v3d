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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
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
        V3D_Vector instance = P0P0P0;
        String expResult = "V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + ")";
        String result = instance.toString();
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of equals method, of class V3D_Vector.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");

    }

    /**
     * Test of hashCode method, of class V3D_Vector.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        V3D_Vector v = P0P0P0;
        int result = v.hashCode();
        int expResult = 148760;
        assertTrue(result == expResult);
    }

    /**
     * Test of getDotProduct method, of class V3D_Vector.
     */
    @Test
    public void testGetDotProduct() {
        System.out.println("getDotProduct");
        int oom = -1;
        V3D_Vector v = P0P1P0;
        V3D_Vector instance = P1P0P0;
        Math_BigRational expResult = P0;
        Math_BigRational result = instance.getDotProduct(v, oom);
        assertEquals(expResult, result);
        // Test 2
        v = P0P1P0;
        instance = P0P0N1;
        expResult = P0;
        result = instance.getDotProduct(v, oom);
        assertEquals(expResult, result);
        // Test 3
        v = P1P1P1;
        instance = N1N1N1;
        expResult = N3;
        result = instance.getDotProduct(v, oom);
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
        v = P1P0P0;
        instance = P0P1P0;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0P0P1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0N1P0;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = N1P0P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1P1P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1N1P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1N1P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1P1P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P0P1N1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0N1N1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0N1P1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0P1P1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0P1N1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0N1N1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0N1P1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0P1P1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P1P1N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1P1P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1P1P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1P1N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1N1N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1N1P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1N1P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1P0P0;
        assertFalse(instance.isOrthogonal(v, oom));
        // Test 2
        v = P1P1P0;
        instance = P1P0P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1N1P0;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0N1P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1N1P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1P0P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1P1P0;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0P1P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1P1P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1P1N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1P0N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1N1N1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0N1N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1P0N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1P1N1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0P1N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P0P0N1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P1P1P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1P0P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1N1P1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0N1P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1N1P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1P0P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1P1P1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0P1P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P0P0P1;
        assertTrue(instance.isOrthogonal(v, oom));
        // Test 3
        v = P1P1P1;
        instance = P1P1P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1P0P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1N1P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P0N1P1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1P0P1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = N1P1P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P0P1P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P0P0P1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1P1P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1P0P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1N1P0;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0N1P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1N1P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1P0P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1P1P0;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0P1P0;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P0P0P0;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P1P1N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P1P0N1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P1N1N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P0N1N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1N1N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1P0N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = N1P1N1;
        assertFalse(instance.isOrthogonal(v, oom));
        instance = P0P1N1;
        assertTrue(instance.isOrthogonal(v, oom));
        instance = P0P0N1;
        assertFalse(instance.isOrthogonal(v, oom));
    }

    /**
     * Test of getMagnitude method, of class V3D_Vector.
     */
    @Test
    public void testGetMagnitude() {
        System.out.println("getMagnitude");
        int oom = 0;
        V3D_Vector instance = P0P0P0;
        Math_BigRational expResultSquared;
        Math_BigRationalSqrt expResult = Math_BigRationalSqrt.ZERO;
        Math_BigRationalSqrt result = instance.getMagnitude();
        assertTrue(expResult.compareTo(result) == 0);
        // Test 1
        oom = -1;
        instance = P1P1P0;
        expResult = new Math_BigRationalSqrt(P2, oom);
        result = instance.getMagnitude();
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        oom = -1;
        instance = P1P1P1;
        expResult = new Math_BigRationalSqrt(P3, oom);
        result = instance.getMagnitude();
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        oom = -100;
        instance = new V3D_Vector(P10, P10, P10);
        expResult = new Math_BigRationalSqrt(300L, oom);
        result = instance.getMagnitude();
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        instance = new V3D_Vector(P3, P4, N4);
        expResult = new Math_BigRationalSqrt(41L, oom);
        result = instance.getMagnitude();
        assertTrue(expResult.compareTo(result) == 0);
        // Test 5
        instance = new V3D_Vector(P7, P8, N4);
        expResult = new Math_BigRationalSqrt(P7.pow(2).add(P8.pow(2).add(N4.pow(2))), oom);
        result = instance.getMagnitude();
        assertTrue(expResult.compareTo(result) == 0);
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
        V3D_Vector v = P0P0P0;
        V3D_Vector instance = P1P1P1;
        assertFalse(instance.isScalarMultiple(v, oom));
        // Test 2
        v = N1N1N1;
        instance = P1P1P1;
        assertTrue(instance.isScalarMultiple(v, oom));
        // Test 3
        v = P1P0P0;
        instance = P0P1P1;
        assertFalse(instance.isScalarMultiple(v, oom));
        // Test 4
        v = new V3D_Vector(P0, P1, P10000);
        instance = new V3D_Vector(P0, P1, P10001);
        assertFalse(instance.isScalarMultiple(v, oom));
    }

    /**
     * Test of getCrossProduct method, of class V3D_Vector.
     */
    @Test
    public void testGetCrossProduct() {
        System.out.println("getCrossProduct");
        int oom = -1;
        V3D_Vector v = P1P1P1;
        V3D_Vector instance = N1N1N1;
        V3D_Vector expResult = P0P0P0;
        V3D_Vector result = instance.getCrossProduct(v, oom);
        assertEquals(expResult, result);
        // Test 2
        v = P1P1P1;
        instance = P1P1P0;
        expResult = P1N1P0;
        result = instance.getCrossProduct(v, oom);
        assertEquals(expResult, result);
        // Test 3
        v = P1P1P0;
        instance = P1P1P1;
        expResult = N1P1P0;
        result = instance.getCrossProduct(v, oom);
        assertEquals(expResult, result);
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
        int oom = -1;
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
     * Test of isZeroVector method, of class V3D_Vector.
     */
    @Test
    public void testIsZeroVector() {
        System.out.println("isZeroVector");
        int oom = -1;
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
        int oom = -1;
        Math_BigRational s = Math_BigRational.ZERO;
        V3D_Vector instance = new V3D_Vector(0, 0, 0);
        V3D_Vector expResult = new V3D_Vector(0, 0, 0);
        V3D_Vector result = instance.multiply(s, oom);
        assertEquals(expResult, result);
        // Test 2
        s = Math_BigRational.ZERO;
        instance = new V3D_Vector(10, 10, 10);
        expResult = new V3D_Vector(0, 0, 0);
        result = instance.multiply(s, oom);
        assertEquals(expResult, result);
        // Test 3
        s = Math_BigRational.TWO;
        instance = new V3D_Vector(10, 10, 10);
        expResult = new V3D_Vector(20, 20, 20);
        result = instance.multiply(s, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class V3D_Vector.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        int oom = -1;
        V3D_Vector instance = new V3D_Vector(0, 0, 0);
        V3D_Vector v = new V3D_Vector(0, 0, 0);
        V3D_Vector expResult = new V3D_Vector(0, 0, 0);
        V3D_Vector result = instance.add(v, oom);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Vector(0, 0, 0);
        v = new V3D_Vector(1, 1, 1);
        expResult = new V3D_Vector(1, 1, 1);
        result = instance.add(v, oom);
        assertEquals(expResult, result);
        // Test 3
        instance = new V3D_Vector(2, 3, 4);
        v = new V3D_Vector(7, 1, 11);
        expResult = new V3D_Vector(9, 4, 15);
        result = instance.add(v, oom);
        assertEquals(expResult, result);
        // Test 3
        instance = new V3D_Vector(-2, 3, -4);
        v = new V3D_Vector(7, 1, 11);
        expResult = new V3D_Vector(5, 4, 7);
        result = instance.add(v, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of subtract method, of class V3D_Vector.
     */
    @Test
    public void testSubtract() {
        System.out.println("subtract");
        int oom = -1;
        V3D_Vector instance = new V3D_Vector(0, 0, 0);
        V3D_Vector v = new V3D_Vector(0, 0, 0);
        V3D_Vector expResult = new V3D_Vector(0, 0, 0);
        V3D_Vector result = instance.subtract(v, oom);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Vector(0, 0, 0);
        v = new V3D_Vector(1, 1, 1);
        expResult = new V3D_Vector(-1, -1, -1);
        result = instance.subtract(v, oom);
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
        int oom = -1;
        V3D_Vector instance = new V3D_Vector(0, 0, 0);
        Math_BigRational expResult = Math_BigRational.ZERO;
        Math_BigRational result = instance.getMagnitudeSquared();
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = new V3D_Vector(1, 1, 1);
        expResult = Math_BigRational.valueOf(3);
        result = instance.getMagnitudeSquared();
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        instance = new V3D_Vector(2, 2, 2);
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
        V3D_Vector instance = V3D_Vector.I;
        V3D_Vector expResult = V3D_Vector.I;
        V3D_Vector result = instance.getUnitVector(oom);
        assertEquals(expResult, result);
        assertTrue(result.getMagnitudeSquared().compareTo(Math_BigRational.ONE) != 1);
        // Test 2
        instance = new V3D_Vector(100, 0, 0);
        expResult = V3D_Vector.I;
        result = instance.getUnitVector(oom);
        assertEquals(expResult, result);
        assertTrue(result.getMagnitudeSquared().compareTo(Math_BigRational.ONE) != 1);
        // Test 3
        instance = new V3D_Vector(100, 100, 0);
        Math_BigRational m = instance.getMagnitude().getSqrt(oom);
        expResult = new V3D_Vector(
                instance.getDX(oom).divide(m),
                instance.getDY(oom).divide(m),
                instance.getDZ(oom).divide(m));
        result = instance.getUnitVector(oom);
        assertEquals(expResult, result);
        assertTrue(result.getMagnitudeSquared().compareTo(Math_BigRational.ONE) != 1);
        // Test 4
        instance = new V3D_Vector(0, 100, 100);
        m = instance.getMagnitude().getSqrt(oom);
        expResult = new V3D_Vector(
                instance.getDX(oom).divide(m),
                instance.getDY(oom).divide(m),
                instance.getDZ(oom).divide(m));
        result = instance.getUnitVector(oom);
        assertEquals(expResult, result);
        assertTrue(result.getMagnitudeSquared().compareTo(Math_BigRational.ONE) != 1);
        // Test 5
        instance = new V3D_Vector(100, 100, 100);
        m = instance.getMagnitude().getSqrt(oom);
        expResult = new V3D_Vector(
                instance.getDX(oom).divide(m),
                instance.getDY(oom).divide(m),
                instance.getDZ(oom).divide(m));
        result = instance.getUnitVector(oom);
        assertEquals(expResult, result);
        assertTrue(result.getMagnitudeSquared().compareTo(Math_BigRational.ONE) != 1);
        // Test 6
        instance = new V3D_Vector(-100, 0, 0);
        m = instance.getMagnitude().getSqrt(oom);
        expResult = new V3D_Vector(
                instance.getDX(oom).divide(m),
                instance.getDY(oom).divide(m),
                instance.getDZ(oom).divide(m));
        result = instance.getUnitVector(oom);
        assertEquals(expResult, result);
        assertTrue(result.getMagnitudeSquared().compareTo(Math_BigRational.ONE) != 1);
        // Test 7
        instance = new V3D_Vector(-100, -100, 0);
        m = instance.getMagnitude().getSqrt(oom);
        expResult = new V3D_Vector(
                instance.getDX(oom).divide(m),
                instance.getDY(oom).divide(m),
                instance.getDZ(oom).divide(m));
        result = instance.getUnitVector(oom);
        assertEquals(expResult, result);
        assertTrue(result.getMagnitudeSquared().compareTo(Math_BigRational.ONE) != 1);
        // Test 8
        instance = new V3D_Vector(0, -100, -100);
        m = instance.getMagnitude().getSqrt(oom);
        expResult = new V3D_Vector(
                instance.getDX(oom).divide(m),
                instance.getDY(oom).divide(m),
                instance.getDZ(oom).divide(m));
        result = instance.getUnitVector(oom);
        assertEquals(expResult, result);
        assertTrue(result.getMagnitudeSquared().compareTo(Math_BigRational.ONE) != 1);
        // Test 9
        instance = new V3D_Vector(-100, -100, -100);
        m = instance.getMagnitude().getSqrt(oom);
        expResult = new V3D_Vector(
                instance.getDX(oom).divide(m),
                instance.getDY(oom).divide(m),
                instance.getDZ(oom).divide(m));
        result = instance.getUnitVector(oom);
        assertEquals(expResult, result);
        assertTrue(result.getMagnitudeSquared().compareTo(Math_BigRational.ONE) != 1);
    }

    /**
     * Test of getDX method, of class V3D_Vector.
     */
    @Test
    public void testGetDX() {
        System.out.println("getDX");
        int oom = -1;
        V3D_Vector instance = V3D_Vector.I;
        Math_BigRational expResult = Math_BigRational.ONE;
        Math_BigRational result = instance.getDX(oom);
        assertEquals(expResult, result);
        // Test 2
        instance = V3D_Vector.I.reverse();
        expResult = Math_BigRational.ONE.negate();
        result = instance.getDX(oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDY method, of class V3D_Vector.
     */
    @Test
    public void testGetDY() {
        System.out.println("getDY");
        int oom = -1;
        V3D_Vector instance = V3D_Vector.J;
        Math_BigRational expResult = Math_BigRational.ONE;
        Math_BigRational result = instance.getDY(oom);
        assertEquals(expResult, result);
        // Test 2
        instance = V3D_Vector.J.reverse();
        expResult = Math_BigRational.ONE.negate();
        result = instance.getDY(oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDZ method, of class V3D_Vector.
     */
    @Test
    public void testGetDZ() {
        System.out.println("getDZ");
        int oom = -1;
        V3D_Vector instance = V3D_Vector.K;
        Math_BigRational expResult = Math_BigRational.ONE;
        Math_BigRational result = instance.getDZ(oom);
        assertEquals(expResult, result);
        // Test 2
        instance = V3D_Vector.K.reverse();
        expResult = Math_BigRational.ONE.negate();
        result = instance.getDZ(oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of divide method, of class V3D_Vector.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        int oom = -1;
        Math_BigRational s = Math_BigRational.TWO;
        V3D_Vector instance = V3D_Vector.I;
        V3D_Vector expResult = new V3D_Vector(new V3D_Point(0.5d, 0d, 0d), -2);
        V3D_Vector result = instance.divide(s, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAngle method, of class V3D_Vector.
     */
    @Test
    @Disabled
    public void testGetAngle() {
        System.out.println("getAngle");
        V3D_Vector v = null;
        int oom = 0;
        V3D_Vector instance = null;
        Math_BigRational expResult = null;
        Math_BigRational result = instance.getAngle(v, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        assertEquals(expResult, result);
        // Test 2
        instance = V3D_Vector.J;
        expResult = 1;
        result = instance.getDirection();
        assertEquals(expResult, result);
        // Test 3
        instance = V3D_Vector.K;
        expResult = 1;
        result = instance.getDirection();
        assertEquals(expResult, result);
        // Test 4
        instance = V3D_Vector.I.reverse();
        expResult = 5;
        result = instance.getDirection();
        assertEquals(expResult, result);
        // Test 5
        instance = V3D_Vector.J.reverse();
        expResult = 3;
        result = instance.getDirection();
        assertEquals(expResult, result);
        // Test 6
        instance = V3D_Vector.K.reverse();
        expResult = 2;
        result = instance.getDirection();
        assertEquals(expResult, result);
        // Test 7
        //...
    }

}
