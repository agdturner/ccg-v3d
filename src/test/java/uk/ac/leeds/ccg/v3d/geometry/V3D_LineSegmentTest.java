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

import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.V3D_Test;

/**
 * Test of V3D_LineSegment class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineSegmentTest extends V3D_Test {

    public V3D_LineSegmentTest() {
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
     * Test of toString method, of class V3D_LineSegment.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        String expResult = "V3D_LineSegment\n"
                + "(\n"
                + " oom=-3\n"
                + " ,\n"
                + " offset=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " p=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " q=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " v=null\n"
                + ")";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of getEnvelope method, of class V3D_LineSegment.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P1P0);
        V3D_Envelope expResult = new V3D_Envelope(e, pP0P0P0, pP1P1P0);
        V3D_Envelope result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class V3D_LineSegment.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        Object o = new V3D_LineSegment(pP0P0P0, pP1P1P0);
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P1P0);
        assertTrue(instance.equals(o));
        // Test 2
        instance = new V3D_LineSegment(pP1P1P0, pP0P0P0);
        assertTrue(instance.equals(o));
    }

    /**
     * Test of hashCode method, of class V3D_LineSegment.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertTrue(true); // Non test
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegment.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("isIntersectedBy");
        V3D_Point p = pP0P0P0;
        V3D_LineSegment instance = new V3D_LineSegment(pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(p, e.oom));
        // Test2
        p = pP1P1P1;
        instance = new V3D_LineSegment(pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(p, e.oom));
    }

    /**
     * Test of equals method, of class V3D_LineSegment.
     */
    @Test
    public void testEquals_V3D_LineSegment() {
        System.out.println("equals");
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P1P0);
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P1P0);
        assertTrue(instance.equals(l));
        // Test 2
        instance = new V3D_LineSegment(pP1P1P0, pP0P0P0);
        assertFalse(instance.equals(l));
    }

    /**
     * Test of equalsIgnoreDirection method, of class V3D_LineSegment.
     */
    @Test
    public void testEqualsIgnoreDirection() {
        System.out.println("equalsIgnoreDirection");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P1P0);
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP2P2P0);
        assertFalse(instance.equalsIgnoreDirection(l));
        // Test 2
        instance = new V3D_LineSegment(pP1P1P0, pP0P0P0);
        assertTrue(instance.equalsIgnoreDirection(l));
        // Test 3
        instance = new V3D_LineSegment(pP0P0P0, pP1P1P0);
        assertTrue(instance.equalsIgnoreDirection(l));
    }

    /**
     * Test of isPoint method, of class V3D_LineSegment.
     */
    @Test
    public void testIsPoint() {
        System.out.println("isPoint");
        int oom = -1;
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP0P0P0);
        assertTrue(instance.isPoint());
        // Test 2
        instance = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        assertFalse(instance.isPoint());
    }

    /**
     * Test of multiply method, of class V3D_LineSegment.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        int oom = -1;
        V3D_Vector v = V3D_Vector.I;
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_LineSegment expResult = new V3D_LineSegment(pP1P0P0, pP2P0P0);
        instance.apply(oom, v);
        assertTrue(expResult.equals(instance));
        // Test 2
        instance = new V3D_LineSegment(pP0P0P0, pP0P1P0);
        expResult = new V3D_LineSegment(pP1P0P0, pP1P1P0);
        instance.apply(oom, v);
        assertTrue(expResult.equals(instance));
        // Test 3
        instance = new V3D_LineSegment(pP0P0P0, pP0P0P1);
        expResult = new V3D_LineSegment(pP1P0P0, pP1P0P1);
        instance.apply(oom, v);
        assertTrue(expResult.equals(instance));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegment.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
        System.out.println("isIntersectedBy");
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 2
        instance = new V3D_LineSegment(pP0P0P0, pP1P1P1);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 3
        instance = new V3D_LineSegment(pN1N1N1, pN1N1P0);
        assertFalse(instance.isIntersectedBy(l, e.oom));
        // Test 4
        l = new V3D_LineSegment(pN1N1P0, pP1P1P0);
        instance = new V3D_LineSegment(pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(l, e.oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegment.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line() {
        System.out.println("isIntersectedBy");
        V3D_Line l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 2
        instance = new V3D_LineSegment(pP0P0P0, pP1P1P1);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 3
        instance = new V3D_LineSegment(pN1N1N1, pN1N1P0);
        assertFalse(instance.isIntersectedBy(l, e.oom));
        // Test 4
        l = new V3D_LineSegment(pN1N1P0, pP1P1P0);
        instance = new V3D_LineSegment(pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(l, e.oom));
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegment.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0);
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_Geometry result = instance.getIntersection(l, e.oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_LineSegment(pP0P0P0, pP1P1P1);
        result = instance.getIntersection(l, e.oom);
        expResult = pP0P0P0;
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_LineSegment(pN1N1N1, pN1N1P0);
        result = instance.getIntersection(l, e.oom);
        assertNull(result);
        // Test 4
        l = new V3D_Line(pN1N1P0, pP1P1P0);
        instance = new V3D_LineSegment(pN1N1N1, pP1P1P1);
        result = instance.getIntersection(l, e.oom);
        expResult = pP0P0P0;
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_Line(pP0P0P0, pP1P0P0);
        instance = new V3D_LineSegment(pN1P0P0, pP1P0P0);
        result = instance.getIntersection(l, e.oom);
        expResult = new V3D_LineSegment(pN1P0P0, pP1P0P0);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegment.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_Geometry result = instance.getIntersection(l, e.oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_LineSegment(pP0P0P0, pP1P1P1);
        result = instance.getIntersection(l, e.oom);
        expResult = pP0P0P0;
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_LineSegment(pN1N1N1, pN1N1P0);
        result = instance.getIntersection(l, e.oom);
        assertNull(result);
        // Test 4
        l = new V3D_LineSegment(pN1N1P0, pP1P1P0);
        instance = new V3D_LineSegment(pN1N1N1, pP1P1P1);
        result = instance.getIntersection(l, e.oom);
        expResult = pP0P0P0;
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        instance = new V3D_LineSegment(pN1P0P0, pP1P0P0);
        result = instance.getIntersection(l, e.oom);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_LineSegment.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        // No test as this is covered by V3D_EnvelopeTest.testIsIntersectedBy_V3D_LineSegment_boolean()
    }

    /**
     * Test of getLength method, of class V3D_LineSegment covered by 
     * {@link #testGetLength2()}.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");
    }
    
    /**
     * Test of getLength2 method, of class V3D_LineSegment.
     */
    @Test
    public void testGetLength2() {
        System.out.println("getLength2");
        V3D_LineSegment instance;
        Math_BigRational expResult;
        Math_BigRational result;
        // Test 1
        instance = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        expResult = Math_BigRational.ONE;
        result = instance.getLength2(e.oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_LineSegment(pP0P0P0, pP2P0P0);
        expResult = Math_BigRational.valueOf(4);
        result = instance.getLength2(e.oom);
        assertTrue(expResult.equals(result));
    }
    
/**
     * Test of getDistance method, of class V3D_LineSegment covered by 
     * {@link #testGetDistanceSquared_V3D_Point_int()}.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        System.out.println("getDistance");
    }
    
    /**
     * Test of getDistance method, of class V3D_LineSegment.
     */
    @Test
    public void testGetDistanceSquared_V3D_Point_int() {
        System.out.println("getDistance");
        V3D_LineSegment instance;
        V3D_Point p;
        Math_BigRational expResult;
        Math_BigRational result;
        // Test 1
        instance = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        p = pP0P0P0;
        expResult = Math_BigRational.ZERO;
        result = instance.getDistanceSquared(p, e.oom);
        assertTrue(expResult.equals(result));
        // Test 2
        p = pP1P0P0;
        expResult = Math_BigRational.ZERO;
        result = instance.getDistanceSquared(p, e.oom);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_LineSegment(pP0P0P0, pP2P0P0);
        p = pP1P0P0;
        expResult = Math_BigRational.ZERO;
        result = instance.getDistanceSquared(p, e.oom);
        assertTrue(expResult.equals(result));
        // Test 4
        p = pP1P0P1;
        expResult = Math_BigRational.ONE;
        result = instance.getDistanceSquared(p, e.oom);
        assertTrue(expResult.equals(result));
        // Test 5
        p = pN1P0P1;
        expResult = Math_BigRational.valueOf(2);
        result = instance.getDistanceSquared(p, e.oom);
        assertTrue(expResult.equals(result));
    }
    
    /**
     * Test of getDistance method, of class V3D_LineSegment covered by 
     * {@link #testGetDistanceSquared_V3D_LineSegment_int()}.
     */
    @Test
    public void testGetDistance_V3D_LineSegment_int() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistance method, of class V3D_LineSegment.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegment_int() {
        System.out.println("getDistanceSquared");
        V3D_LineSegment l;
        V3D_LineSegment instance;
        Math_BigRational expResult;
        Math_BigRational result;
        // Test 1
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        instance = new V3D_LineSegment(pP0P1P0, pP1P1P0);
        expResult = Math_BigRational.ONE;
        result = instance.getDistanceSquared(l, e.oom);
        assertTrue(expResult.equals(result));
        // Test 2
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        instance = new V3D_LineSegment(pN1P0P0, pN1P1P0);
        expResult = Math_BigRational.ONE;
        result = instance.getDistanceSquared(l, e.oom);
        assertTrue(expResult.equals(result));
        // Test 3
        expResult = Math_BigRational.ONE;
        result = l.getDistanceSquared(instance, e.oom);
        assertTrue(expResult.equals(result));
        // Test 4
        l = new V3D_LineSegment(pP1P0P0, pP0P1P0);
        instance = new V3D_LineSegment(pN1P0P1, pN1P1P0);
        expResult = Math_BigRational.ONE;
        result = l.getDistanceSquared(instance, e.oom);
        assertTrue(expResult.equals(result));
        // Test 5
        instance = new V3D_LineSegment(pP1P0P0, pP0P1P0);
        l = new V3D_LineSegment(pN1P0P1, pN1P1P0);
        expResult = Math_BigRational.ONE;
        result = l.getDistanceSquared(instance, e.oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getMidpoint method, of class V3D_LineSegment.
     */
    @Test
    public void testGetMidpoint() {
        System.out.println("getMidpoint");
        V3D_LineSegment instance;
        V3D_Point expResult;
        V3D_Point result;
        // Test 1
        instance = new V3D_LineSegment(pP0P0P0, pP2P0P0);
        expResult = pP1P0P0;
        result = instance.getMidpoint(e.oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getLineOfIntersection method, of class V3D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V3D_Line_int() {
        System.out.println("getLineOfIntersection");
        V3D_LineSegment l0 = new V3D_LineSegment(pP1P0P0, pP1P1P0);
        V3D_Line l1 = new V3D_Line(pP0P0P0, pP0P0P1);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_Geometry result = l0.getLineOfIntersection(l1, e.oom);
        assertTrue(expResult.equals(result));
        // Test 2
        l1 = new V3D_Line(pP0P0P0, pP0P1P0);
        result = l0.getLineOfIntersection(l1, e.oom);
        assertNull(result);
        // Test 3
        l0 = new V3D_LineSegment(pP1P0P1, pP1P0P2);
        l1 = new V3D_Line(pN1P0P0, pN2P0P0);
        expResult = new V3D_LineSegment(pP1P0P0, pP1P0P1);
        result = l0.getLineOfIntersection(l1, e.oom);
        assertTrue(expResult.equals(result));
        // Test 4
        l0 = new V3D_LineSegment(pP1P0P0, pP0P1P0);
        l1 = new V3D_Line(pN1P0P1, pN1P1P0);
        expResult = new V3D_LineSegment(pN1P1P0, pP0P1P0);
        result = l0.getLineOfIntersection(l1, e.oom);
        assertTrue(expResult.equals(result));
    }
    
    /**
     * Test of getLineOfIntersection method, of class V3D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V3D_LineSegment_int() {
        System.out.println("getLineOfIntersection");
        V3D_LineSegment l0 = new V3D_LineSegment(pP1P0P0, pP1P1P0);
        V3D_LineSegment l1 = new V3D_LineSegment(pP0P0P0, pP0P0P1);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_Geometry result = l0.getLineOfIntersection(l1, e.oom);
        assertTrue(expResult.equals(result));
        // Test 2
        l1 = new V3D_LineSegment(pP0P0P0, pP0P1P0);
        result = l0.getLineOfIntersection(l1, e.oom);
        assertNull(result);
        // Test 3
        l0 = new V3D_LineSegment(pP1P0P1, pP1P0P2);
        l1 = new V3D_LineSegment(pN1P0P0, pN2P0P0);
        expResult = new V3D_LineSegment(pN1P0P0, pP1P0P1);
        result = l0.getLineOfIntersection(l1, e.oom);
        assertTrue(expResult.equals(result));
        // Test 4
        l0 = new V3D_LineSegment(pP1P0P0, pP0P1P0);
        l1 = new V3D_LineSegment(pN1P0P1, pN1P1P0);
        expResult = new V3D_LineSegment(pN1P1P0, pP0P1P0);
        result = l0.getLineOfIntersection(l1, e.oom);
        assertTrue(expResult.equals(result));
    }
}
