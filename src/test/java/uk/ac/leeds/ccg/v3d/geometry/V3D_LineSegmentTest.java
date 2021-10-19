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
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
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
        int oom = -1;
        V3D_Point start = new V3D_Point(P0, P0, P0);
        V3D_Point end = new V3D_Point(P1, P1, P1);
        V3D_LineSegment instance = new V3D_LineSegment(start, end, oom);
        String expResult = "V3D_LineSegment(p=V3D_Point(x=0, y=0, z=0), "
                + "q=V3D_Point(x=1, y=1, z=1), "
                + "v=V3D_Vector("
                + "dx=Math_BigRationalSqrt(x=1, negative=false, sqrtx=1, "
                + "oomi=-1, sqrtxapprox=null, oom=-1), "
                + "dy=Math_BigRationalSqrt(x=1, negative=false, sqrtx=1, "
                + "oomi=-1, sqrtxapprox=null, oom=-1), "
                + "dz=Math_BigRationalSqrt(x=1, negative=false, sqrtx=1, "
                + "oomi=-1, sqrtxapprox=null, oom=-1), "
                + "m=Math_BigRationalSqrt(x=3, negative=false, sqrtx=null, "
                + "oomi=-1, sqrtxapprox=1.7, oom=-1)))";
        String result = instance.toString();
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of getLength method, of class V3D_LineSegment.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");
        int oom = -2;
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P1P0, oom);
        BigDecimal expResult = Math_BigDecimal.sqrt(P2.toBigDecimal(), oom,
                RoundingMode.HALF_UP);
        BigDecimal result = instance.getLength().toBigDecimal(oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getEnvelope method, of class V3D_LineSegment.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        int oom = -1;
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P1P0, oom);
        V3D_Envelope expResult = new V3D_Envelope(oom, P0P0P0, P1P1P0);
        V3D_Envelope result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class V3D_LineSegment.
     */
    @Test
    public void testEquals_Object() {
        int oom = -1;
        System.out.println("equals");
        Object o = new V3D_LineSegment(P0P0P0, P1P1P0, oom);
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P1P0, oom);
        assertTrue(instance.equals(o));
        // Test 2
        instance = new V3D_LineSegment(P1P1P0, P0P0P0, oom);
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
        int oom = -1;
        V3D_Point p = P0P0P0;
        V3D_LineSegment instance = new V3D_LineSegment(N1N1N1, P1P1P1, oom);
        assertTrue(instance.isIntersectedBy(p, oom));
        // Test2
        p = P1P1P1;
        instance = new V3D_LineSegment(N1N1N1, P1P1P1, oom);
        assertTrue(instance.isIntersectedBy(p, oom));
    }

    /**
     * Test of equals method, of class V3D_LineSegment.
     */
    @Test
    public void testEquals_V3D_LineSegment() {
        System.out.println("equals");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P1P1P0, oom);
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P1P0, oom);
        assertTrue(instance.equals(l));
        // Test 2
        instance = new V3D_LineSegment(P1P1P0, P0P0P0, oom);
        assertFalse(instance.equals(l));
    }

    /**
     * Test of equalsIgnoreDirection method, of class V3D_LineSegment.
     */
    @Test
    public void testEqualsIgnoreDirection() {
        System.out.println("equalsIgnoreDirection");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P1P1P0, oom);
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, new V3D_Point(P2, P2, P0), oom);
        assertFalse(instance.equalsIgnoreDirection(l));
        // Test 2
        instance = new V3D_LineSegment(P1P1P0, P0P0P0, oom);
        assertTrue(instance.equalsIgnoreDirection(l));
        // Test 3
        instance = new V3D_LineSegment(P0P0P0, P1P1P0, oom);
        assertTrue(instance.equalsIgnoreDirection(l));
    }

    /**
     * Test of isPoint method, of class V3D_LineSegment.
     */
    @Test
    public void testIsPoint() {
        System.out.println("isPoint");
        int oom = -1;
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P0P0P0, oom);
        assertTrue(instance.isPoint());
        // Test 2
        instance = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        assertFalse(instance.isPoint());
    }

    /**
     * Test of multiply method, of class V3D_LineSegment.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        int oom = -1;
        V3D_Vector v = V3D_Environment.i;
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        V3D_LineSegment expResult = new V3D_LineSegment(P1P0P0, new V3D_Point(P2, P0, P0), oom);
        V3D_LineSegment result = instance.apply(v);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_LineSegment(P0P0P0, P0P1P0, oom);
        expResult = new V3D_LineSegment(P1P0P0, P1P1P0, oom);
        result = instance.apply(v);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_LineSegment(P0P0P0, P0P0P1, oom);
        expResult = new V3D_LineSegment(P1P0P0, P1P0P1, oom);
        result = instance.apply(v);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegment.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom, false));
        // Test 2
        instance = new V3D_LineSegment(P0P0P0, P1P1P1, oom);
        assertTrue(instance.isIntersectedBy(l, oom, false));
        // Test 3
        instance = new V3D_LineSegment(N1N1N1, N1N1P0, oom);
        assertFalse(instance.isIntersectedBy(l, oom, false));
        // Test 4
        l = new V3D_LineSegment(N1N1P0, P1P1P0, oom);
        instance = new V3D_LineSegment(N1N1N1, P1P1P1, oom);
        assertTrue(instance.isIntersectedBy(l, oom, false));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegment.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_Line l = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom));
        // Test 2
        instance = new V3D_LineSegment(P0P0P0, P1P1P1, oom);
        assertTrue(instance.isIntersectedBy(l, oom));
        // Test 3
        instance = new V3D_LineSegment(N1N1N1, N1N1P0, oom);
        assertFalse(instance.isIntersectedBy(l, oom));
        // Test 4
        l = new V3D_LineSegment(N1N1P0, P1P1P0, oom);
        instance = new V3D_LineSegment(N1N1N1, P1P1P1, oom);
        assertTrue(instance.isIntersectedBy(l, oom));
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegment.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_Line l = new V3D_Line(P0P0P0, P1P0P0, oom);
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        V3D_Geometry result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_LineSegment(P0P0P0, P1P1P1, oom);
        result = instance.getIntersection(l, oom);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_LineSegment(N1N1N1, N1N1P0, oom);
        result = instance.getIntersection(l, oom);
        assertNull(result);
        // Test 4
        l = new V3D_Line(N1N1P0, P1P1P0, oom);
        instance = new V3D_LineSegment(N1N1N1, P1P1P1, oom);
        result = instance.getIntersection(l, oom);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_Line(P0P0P0, P1P0P0, oom);
        instance = new V3D_LineSegment(N1P0P0, P1P0P0, oom);
        result = instance.getIntersection(l, oom);
        expResult = new V3D_LineSegment(N1P0P0, P1P0P0, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegment.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        V3D_Geometry result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_LineSegment(P0P0P0, P1P1P1, oom);
        result = instance.getIntersection(l, oom, true);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_LineSegment(N1N1N1, N1N1P0, oom);
        result = instance.getIntersection(l, oom, true);
        assertNull(result);
        // Test 4
        l = new V3D_LineSegment(N1N1P0, P1P1P0, oom);
        instance = new V3D_LineSegment(N1N1N1, P1P1P1, oom);
        result = instance.getIntersection(l, oom, true);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        instance = new V3D_LineSegment(N1P0P0, P1P0P0, oom);
        result = instance.getIntersection(l, oom, true);
        expResult = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_LineSegment.
     */
    @Test
    @Disabled
    public void testIsEnvelopeIntersectedBy() {
        // No test as this is covered by V3D_EnvelopeTest.testIsIntersectedBy_V3D_LineSegment_boolean()
    }

    /**
     * Test of getDistance method, of class V3D_LineSegment.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        V3D_Point p;
        int oom;
        V3D_LineSegment instance;
        BigDecimal expResult;
        BigDecimal result;
        // Test 1
        p = P0P0P0;
        oom = -1;
        instance = new V3D_LineSegment(P1P0P0, P1P1P0, oom);
        expResult = BigDecimal.ONE;
        result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getLength2 method, of class V3D_LineSegment.
     */
    @Test
    public void testGetLength2() {
        System.out.println("getLength2");
        V3D_LineSegment instance;
        Math_BigRational expResult;
        int oom;
        Math_BigRational result;
        // Test 1
        oom = -1;
        instance = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        expResult = Math_BigRational.ONE;
        result = instance.getLength2();
        assertTrue(expResult.equals(result));
        // Test 2
        oom = -1;
        instance = new V3D_LineSegment(P0P0P0, P2P0P0, oom);
        expResult = Math_BigRational.valueOf(4);
        result = instance.getLength2();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDistance method, of class V3D_LineSegment.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        System.out.println("getDistance");
        V3D_LineSegment instance;
        Math_BigRationalSqrt expResult;
        int oom;
        Math_BigRationalSqrt result;
        // Test 1
        oom = -1;
        instance = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        expResult = Math_BigRationalSqrt.ONE;
        result = instance.getLength();
        assertTrue(expResult.equals(result));
        // Test 2
        oom = -1;
        instance = new V3D_LineSegment(P0P0P0, P2P0P0, oom);
        expResult = new Math_BigRationalSqrt(4, 2, oom);
        result = instance.getLength();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDistance method, of class V3D_LineSegment.
     */
    @Test
    public void testGetDistance_V3D_LineSegment_int() {
        System.out.println("getDistance");
        V3D_LineSegment l;
        int oom;
        V3D_LineSegment instance;
        BigDecimal expResult;
        BigDecimal result;
        // Test 1
        oom = -2;
        l = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        instance = new V3D_LineSegment(P0P1P0, P1P1P0, oom);
        expResult = BigDecimal.ONE;
        result = instance.getDistance(l, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getMidpoint method, of class V3D_LineSegment.
     */
    @Test
    public void testGetMidpoint() {
        System.out.println("getMidpoint");
        int oom;
        V3D_LineSegment instance;
        V3D_Point expResult;
        V3D_Point result;
        // Test 1
        oom = -2;
        instance = new V3D_LineSegment(P0P0P0, P2P0P0, oom);
        expResult = P1P0P0;
        result = instance.getMidpoint(oom);
        assertTrue(expResult.equals(result));
    }

}
