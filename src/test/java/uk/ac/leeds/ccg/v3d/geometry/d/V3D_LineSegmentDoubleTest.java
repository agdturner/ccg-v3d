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
package uk.ac.leeds.ccg.v3d.geometry.d;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_Double;

/**
 * Test of V3D_LineSegmentDouble class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineSegmentDoubleTest extends V3D_DoubleTest {

    public V3D_LineSegmentDoubleTest() {
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
     * Test of toString method, of class V3D_LineSegmentDouble.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_LineSegmentDouble instance = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        String expResult = "V3D_LineSegmentDouble\n"
                + "(\n"
                + " offset=V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0),\n"
                + " l= offset=V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0),\n"
                + " p=V3D_PointDouble(offset=V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0), rel=V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0)),\n"
                + " v= V3D_VectorDouble(dx=1.0, dy=0.0, dz=0.0),\n"
                + " q=dx=1.0, dy=0.0, dz=0.0\n"
                + ")";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of getEnvelope method, of class V3D_LineSegmentDouble.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        V3D_LineSegmentDouble instance = new V3D_LineSegmentDouble(pP0P0P0, pP1P1P0);
        V3D_EnvelopeDouble expResult = new V3D_EnvelopeDouble(pP0P0P0, pP1P1P0);
        V3D_EnvelopeDouble result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegmentDouble.
     */
    @Test
    public void testIsIntersectedBy_V3D_PointDouble() {
        System.out.println("isIntersectedBy");
        double epsilon = 1d / 1000000d;
        V3D_PointDouble p = pP0P0P0;
        V3D_LineSegmentDouble instance = new V3D_LineSegmentDouble(pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(p, epsilon));
        // Test2
        p = pP1P1P1;
        instance = new V3D_LineSegmentDouble(pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(p, epsilon));
    }

    /**
     * Test of equals method, of class V3D_LineSegmentDouble.
     */
    @Test
    public void testEquals_V3D_LineSegmentDouble_double() {
        System.out.println("equals");
        double epsilon = 1d / 1000000d;
        V3D_LineSegmentDouble l = new V3D_LineSegmentDouble(pP0P0P0, pP1P1P0);
        V3D_LineSegmentDouble instance = new V3D_LineSegmentDouble(pP0P0P0, pP1P1P0);
        assertTrue(instance.equals(l, epsilon));
        // Test 2
        instance = new V3D_LineSegmentDouble(pP1P1P0, pP0P0P0);
        assertFalse(instance.equals(l, epsilon));
    }

    /**
     * Test of equalsIgnoreDirection method, of class V3D_LineSegmentDouble.
     */
    @Test
    public void testEqualsIgnoreDirection() {
        System.out.println("equalsIgnoreDirection");
        double epsilon = 1d / 1000000d;
        V3D_LineSegmentDouble l = new V3D_LineSegmentDouble(pP0P0P0, pP1P1P0);
        V3D_LineSegmentDouble instance = new V3D_LineSegmentDouble(pP0P0P0, pP2P2P0);
        assertFalse(instance.equalsIgnoreDirection(l, epsilon));
        // Test 2
        instance = new V3D_LineSegmentDouble(pP1P1P0, pP0P0P0);
        assertTrue(instance.equalsIgnoreDirection(l, epsilon));
        // Test 3
        instance = new V3D_LineSegmentDouble(pP0P0P0, pP1P1P0);
        assertTrue(instance.equalsIgnoreDirection(l, epsilon));
    }

    /**
     * Test of multiply method, of class V3D_LineSegmentDouble.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        double epsilon = 1d / 1000000d;
        V3D_VectorDouble v = V3D_VectorDouble.I;
        V3D_LineSegmentDouble instance = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        V3D_LineSegmentDouble expResult = new V3D_LineSegmentDouble(pP1P0P0, pP2P0P0);
        instance.translate(v);
        assertTrue(expResult.equalsIgnoreDirection(instance, epsilon));
        // Test 2
        instance = new V3D_LineSegmentDouble(pP0P0P0, pP0P1P0);
        expResult = new V3D_LineSegmentDouble(pP1P0P0, pP1P1P0);
        instance.translate(v);
        assertTrue(expResult.equalsIgnoreDirection(instance, epsilon));
        // Test 3
        instance = new V3D_LineSegmentDouble(pP0P0P0, pP0P0P1);
        expResult = new V3D_LineSegmentDouble(pP1P0P0, pP1P0P1);
        instance.translate(v);
        assertTrue(expResult.equalsIgnoreDirection(instance, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegmentDouble.
     */
    @Test
    public void testGetIntersection_V3D_LineDouble() {
        System.out.println("getIntersection");
        double epsilon = 1d / 1000000d;
        V3D_LineDouble l = new V3D_LineDouble(pP0P0P0, pP1P0P0);
        V3D_LineSegmentDouble instance = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        V3D_GeometryDouble expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        V3D_GeometryDouble result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 2
        instance = new V3D_LineSegmentDouble(pP0P0P0, pP1P1P1);
        result = instance.getIntersection(l, epsilon);
        expResult = pP0P0P0;
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 3
        instance = new V3D_LineSegmentDouble(pN1N1N1, pN1N1P0);
        result = instance.getIntersection(l, epsilon);
        assertNull(result);
        // Test 4
        l = new V3D_LineDouble(pN1N1P0, pP1P1P0);
        instance = new V3D_LineSegmentDouble(pN1N1N1, pP1P1P1);
        result = instance.getIntersection(l, epsilon);
        expResult = pP0P0P0;
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 5
        l = new V3D_LineDouble(pP0P0P0, pP1P0P0);
        instance = new V3D_LineSegmentDouble(pN1P0P0, pP1P0P0);
        result = instance.getIntersection(l, epsilon);
        expResult = new V3D_LineSegmentDouble(pN1P0P0, pP1P0P0);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegmentDouble.
     */
    @Test
    public void testGetIntersection_V3D_LineSegmentDouble_double() {
        System.out.println("getIntersection");
        double epsilon = 1d / 1000000d;
        V3D_LineSegmentDouble l = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        V3D_LineSegmentDouble instance = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        V3D_GeometryDouble expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        V3D_GeometryDouble result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 2
        instance = new V3D_LineSegmentDouble(pP0P0P0, pP1P1P1);
        result = instance.getIntersection(l, epsilon);
        expResult = pP0P0P0;
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 3
        instance = new V3D_LineSegmentDouble(pN1N1N1, pN1N1P0);
        result = instance.getIntersection(l, epsilon);
        assertNull(result);
        // Test 4
        l = new V3D_LineSegmentDouble(pN1N1P0, pP1P1P0);
        instance = new V3D_LineSegmentDouble(pN1N1N1, pP1P1P1);
        result = instance.getIntersection(l, epsilon);
        expResult = pP0P0P0;
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 5
        l = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        instance = new V3D_LineSegmentDouble(pN1P0P0, pP1P0P0);
        result = instance.getIntersection(l, epsilon);
        expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_LineSegmentDouble.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        // No test as this is covered by V3D_EnvelopeDoubleTest.testIsIntersectedBy_V3D_LineSegmentDouble_boolean()
    }

    /**
     * Test of getLength method, of class V3D_LineSegmentDouble covered by
     * {@link #testGetLength2()}.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");
    }

    /**
     * Test of getLength2 method, of class V3D_LineSegmentDouble.
     */
    @Test
    public void testGetLength2() {
        System.out.println("getLength2");
        double epsilon = 1d / 1000000d;
        V3D_LineSegmentDouble instance;
        double expResult;
        double result;
        // Test 1
        instance = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        expResult = 1d;
        result = instance.getLength2();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        instance = new V3D_LineSegmentDouble(pP0P0P0, pP2P0P0);
        expResult = 4d;
        result = instance.getLength2();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentDouble covered by
     * {@link #testGetDistanceSquared_V3D_PointDouble_int()}.
     */
    @Test
    public void testGetDistance_V3D_PointDouble() {
        System.out.println("getDistance");
        double epsilon = 1d / 1000000d;
        V3D_LineSegmentDouble l = new V3D_LineSegmentDouble(pP0P0P0, pP2P0P0);
        V3D_PointDouble instance = pP1P1P0;
        double expResult = 1d;
        double result = l.getDistance(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        instance = pN1N1P0;
        result = l.getDistance(instance, epsilon);
        expResult = Math.sqrt(2d);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        instance = pP2P2P0;
        expResult = 2d;
        result = l.getDistance(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        instance = pP2P2P0;
        l = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        expResult = Math.sqrt(5d);
        result = l.getDistance(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_PointDouble() {
        System.out.println("getDistance");
        double epsilon = 1d / 1000000d;
        V3D_LineSegmentDouble instance;
        V3D_PointDouble p;
        double expResult;
        double result;
        // Test 1
        instance = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        p = pP0P0P0;
        expResult = 0d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        p = pP1P0P0;
        expResult = 0d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        instance = new V3D_LineSegmentDouble(pP0P0P0, pP2P0P0);
        p = pP1P0P0;
        expResult = 0d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        p = pP1P0P1;
        expResult = 1d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 5
        p = pN1P0P1;
        expResult = 2d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentDouble covered by
     * {@link #testGetDistanceSquared_V3D_LineSegmentDouble_int()}.
     */
    @Test
    public void testGetDistance_V3D_LineSegmentDouble() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegmentDouble() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 1000000d;
        V3D_LineSegmentDouble l;
        V3D_LineSegmentDouble instance;
        double expResult;
        double result;
        // Test 1
        l = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        instance = new V3D_LineSegmentDouble(pP0P1P0, pP1P1P0);
        expResult = 1d;
        result = instance.getDistanceSquared(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        l = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        instance = new V3D_LineSegmentDouble(pN1P0P0, pN1P1P0);
        expResult = 1d;
        result = instance.getDistanceSquared(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        expResult = 1d;
        result = l.getDistanceSquared(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        l = new V3D_LineSegmentDouble(pP1P0P0, pP0P1P0);
        instance = new V3D_LineSegmentDouble(pN1P0P1, pN1P1P0);
        expResult = 1d;
        result = l.getDistanceSquared(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 5
        instance = new V3D_LineSegmentDouble(pP1P0P0, pP0P1P0);
        l = new V3D_LineSegmentDouble(pN1P0P1, pN1P1P0);
        expResult = 1d;
        result = l.getDistanceSquared(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 6
        instance = new V3D_LineSegmentDouble(pP0P0P0, pP0P1P0);
        l = new V3D_LineSegmentDouble(pN1P0P0, pN1P1P0);
        expResult = 1d;
        result = l.getDistanceSquared(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 7
        instance = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        l = new V3D_LineSegmentDouble(pN1P0P1, pN1P1P0);
        expResult = 3d / 2d;
        result = l.getDistanceSquared(instance, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));

    }

    /**
     * Test of getMidpoint method, of class V3D_LineSegmentDouble.
     */
    @Test
    public void testGetMidpoint() {
        System.out.println("getMidpoint");
        double epsilon = 1d / 1000000d;
        V3D_LineSegmentDouble instance;
        V3D_PointDouble expResult;
        V3D_PointDouble result;
        // Test 1
        instance = new V3D_LineSegmentDouble(pP0P0P0, pP2P0P0);
        expResult = pP1P0P0;
        result = instance.getMidpoint();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getLineOfIntersection method, of class V3D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V3D_LineDouble() {
        System.out.println("getLineOfIntersection");
        double epsilon = 1d / 1000000d;
        V3D_LineSegmentDouble l0 = new V3D_LineSegmentDouble(pP1P0P0, pP1P1P0);
        V3D_LineDouble l1 = new V3D_LineDouble(pP0P0P0, pP0P0P1);
        V3D_GeometryDouble expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        V3D_GeometryDouble result = l0.getLineOfIntersection(l1, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 2
        l1 = new V3D_LineDouble(pP0P0P0, pP0P1P0);
        expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        result = l0.getLineOfIntersection(l1, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 3
        l0 = new V3D_LineSegmentDouble(pP1P0P1, pP1P0P2);
        l1 = new V3D_LineDouble(pN1P0P0, pN2P0P0);
        expResult = new V3D_LineSegmentDouble(pP1P0P0, pP1P0P1);
        result = l0.getLineOfIntersection(l1, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
//        // Test 4
//        l0 = new V3D_LineSegmentDouble(pP1P0P0, pP0P1P0);
//        l1 = new V3D_LineDouble(pN1P0P1, pN1P1P0);
//        expResult = new V3D_LineSegmentDouble(pN1P1P0, pP0P1P0);
//        result = l0.getLineOfIntersection(l1);
//        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result));
    }

    /**
     * Test of getLineOfIntersection method, of class V3D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V3D_LineSegmentDouble() {
        System.out.println("getLineOfIntersection");
        double epsilon = 1d / 1000000d;
        V3D_LineSegmentDouble l0 = new V3D_LineSegmentDouble(pP1P0P0, pP1P1P0);
        V3D_LineSegmentDouble l1 = new V3D_LineSegmentDouble(pP0P0P0, pP0P0P1);
        V3D_LineSegmentDouble expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        V3D_LineSegmentDouble result = l0.getLineOfIntersection(l1, epsilon);
        assertTrue(expResult.equalsIgnoreDirection(result, epsilon));
        // Test 2
        l1 = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        result = l0.getLineOfIntersection(l1, epsilon);
        assertNull(result);
        // Test 3
        l0 = new V3D_LineSegmentDouble(pP1P0P1, pP1P0P2);
        l1 = new V3D_LineSegmentDouble(pN1P0P0, pN2P0P0);
        expResult = new V3D_LineSegmentDouble(pN1P0P0, pP1P0P1);
        result = l0.getLineOfIntersection(l1, epsilon);
        assertTrue(expResult.equalsIgnoreDirection(result, epsilon));
        // Test 4
        l0 = new V3D_LineSegmentDouble(pP1P0P0, pP0P1P0);
        l1 = new V3D_LineSegmentDouble(pN1P0P1, pN1P1P0);
        expResult = new V3D_LineSegmentDouble(pN1P1P0, pP0P1P0);
        result = l0.getLineOfIntersection(l1, epsilon);
        assertTrue(expResult.equalsIgnoreDirection(result, epsilon));
    }
}
