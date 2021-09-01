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

import ch.obermuhlner.math.big.BigRational;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.V3D_Test;

/**
 * Test of V3D_Rectangle class.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V3D_RectangleTest extends V3D_Test {

    public V3D_RectangleTest(){}

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
     * Test of getEnvelope method, of class V3D_Rectangle.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        /*
         * q ----------- r
         * |             |
         * |             |
         * |             |
         * p ----------- s
         */
        V3D_Rectangle instance;
        V3D_Envelope expResult;
        V3D_Envelope result;
        instance = new V3D_Rectangle(N1P1P0, P1P1P0, P1N1P0, N1N1P0);
        expResult = new V3D_Envelope(N1N1P0, P1P1P0);
        result = instance.getEnvelope();
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Rectangle(N1P1P1, P1P1P0, P1N1P0, N1N1P1);
        expResult = new V3D_Envelope(N1N1P0, P1P1P1);
        result = instance.getEnvelope();
        assertEquals(expResult, result);
        // Test 3
        instance = new V3D_Rectangle(N1P1P1, P1P1N1, P1N1N1, N1N1P1);
        expResult = new V3D_Envelope(N1N1N1, P1P1P1);
        result = instance.getEnvelope();
        assertEquals(expResult, result);
        // Test 4
        instance = new V3D_Rectangle(N1N1N1, P1N1N1, P1P1N1, N1P1N1);
        expResult = new V3D_Envelope(N1N1N1, P1P1N1);
        result = instance.getEnvelope();
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("isIntersectedBy");
        V3D_Point pt = P0P0P0;
        V3D_Rectangle instance = new V3D_Rectangle(N1P1P0, P1P1P0, P1N1P0, N1N1P0);
        assertTrue(instance.isIntersectedBy(pt));
        // Test 2
        instance = new V3D_Rectangle(N1P0P0, P0P1P0, P1P0P0, P0N1P0);
        assertTrue(instance.isIntersectedBy(pt));
        // Test 3
        BigRational half = BigRational.ONE.divide(2);
        pt = new V3D_Point(half, half, P0);
        assertTrue(instance.isIntersectedBy(pt));
        // Test 3
        pt = new V3D_Point(half.add(BigRational.valueOf("0.00000000001")), half, P0);
        assertFalse(instance.isIntersectedBy(pt));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        V3D_Line l = new V3D_Line(P0P0N1, P0P0P1);
        V3D_Rectangle instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        instance = new V3D_Rectangle(N1P1P0, P1P1P0, P1N1P0, N1N1P0);
        expResult = P0P0P0;
        result = instance.getIntersection(l);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class V3D_Rectangle.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0);
        String expResult = "V3D_Rectangle(p=" + P0P0P0.toString()
                + ", q=" + P0P1P0.toString() + ", r=" + P1P1P0.toString() 
                + ", s=" + P1P0P0.toString() + ")";
        String result = instance.toString();
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of apply method, of class V3D_Rectangle.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        V3D_Vector v = new V3D_Vector(1,0,0);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0);
        V3D_Rectangle expResult = new V3D_Rectangle(P1P0P0, P1P1P0, new V3D_Point(P2,P1,P0), P2P0P0);
        V3D_Rectangle result = instance.apply(v);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line() {
        System.out.println("isIntersectedBy");
        V3D_Line l = new V3D_Line(N1N1N1, P2P2P2);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0);
        assertTrue(instance.isIntersectedBy(l));
        //Test 2
        assertFalse(instance.isIntersectedBy(new V3D_Line(N1N1N1, N1N1P1)));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
        System.out.println("isIntersectedBy");
        V3D_LineSegment l = new V3D_LineSegment(N1N1P0, P1P1P0);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0);
        assertTrue(instance.isIntersectedBy(l));
        //Test 2
        assertFalse(instance.isIntersectedBy(new V3D_Line(P0N1P1, P0N1N1)));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        V3D_Line l = new V3D_Line(N1N1P0, P1P1P0);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0);
        V3D_Geometry result = instance.getIntersection(l);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P1P0);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        V3D_LineSegment l = new V3D_LineSegment(N1N1P0, P2P2P0);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0);
        V3D_Geometry result = instance.getIntersection(l, true);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P1P0);
        assertEquals(expResult, result);
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        V3D_Line l = new V3D_LineSegment(N1N1P0, P2P2P0);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0);
        assertTrue(instance.isEnvelopeIntersectedBy(l));
    }

    /**
     * Test of getPerimeter method, of class V3D_Rectangle.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        int oom = -1;
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0);
        BigDecimal expResult = BigDecimal.valueOf(4);
        BigDecimal result = instance.getPerimeter(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getArea method, of class V3D_Rectangle.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        int oom = 0;
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0);
        BigDecimal expResult = BigDecimal.valueOf(1);
        BigDecimal result = instance.getArea(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        V3D_Point p = new V3D_Point(N1N1P0);
        int oom = -1;
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0);
        BigDecimal expResult = new Math_BigRationalSqrt(2).toBigDecimal(oom);
        BigDecimal result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

}
