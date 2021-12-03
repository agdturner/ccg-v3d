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
import org.junit.jupiter.api.Disabled;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.V3D_Test;

/**
 * Test of V3D_Triangle class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_TriangleTest extends V3D_Test {

    public V3D_TriangleTest() {
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
     * Test of getEnvelope method, of class V3D_Triangle.
     */
    @Test
    public void testGetEnvelope3D() {
        System.out.println("getEnvelope3D");
        int oom = -1;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P1P0, oom);
        V3D_Envelope expResult = new V3D_Envelope(oom, pP0P0P0, pP1P0P0, pP1P1P0);
        V3D_Envelope result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy() {
        System.out.println("isIntersectedBy");
        V3D_Point pt = pP0P0P0;
        int oom = -1;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P1P0, oom);
        assertTrue(instance.isIntersectedBy(pt, oom));
        // Test 2
        pt = pN1N1P0;
        assertFalse(instance.isIntersectedBy(pt, oom));
        // Test 3
        pt = new V3D_Point(Math_BigRational.ONE.divide(2), P0, P0);
        assertTrue(instance.isIntersectedBy(pt, oom));
    }

    /**
     * Test of getEnvelope method, of class V3D_Triangle.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        int oom = -1;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        V3D_Envelope expResult = new V3D_Envelope(oom, pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_Envelope result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getArea method, of class V3D_Triangle.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        int oom = -1;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        BigDecimal expResult = new BigDecimal("0.5");
        BigDecimal result = instance.getArea(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of apply method, of class V3D_Triangle.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_Point pt = pP0P0P0;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(pt, oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_Line l = new V3D_Line(P0P0P0, P2P2P2, oom);
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom));
        // Test 2
        l = new V3D_Line(P1P1P1, P1P1P0, oom);
        assertFalse(instance.isIntersectedBy(l, oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P2P2P2, oom);
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom));
        // Test 2
        l = new V3D_LineSegment(P1P1P1, P1P1P0, oom);
        assertFalse(instance.isIntersectedBy(l, oom));
    }

    /**
     * Test of getPerimeter method, of class V3D_Triangle.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        int oom = 0;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        BigDecimal expResult = BigDecimal.valueOf(2).add(new Math_BigRationalSqrt(2, oom).toBigDecimal(oom));
        BigDecimal result = instance.getPerimeter(oom);
        assertTrue(expResult.compareTo(result) == 0);
        oom = -1;
        expResult = BigDecimal.valueOf(2).add(new Math_BigRationalSqrt(2, oom).toBigDecimal(oom));
        result = instance.getPerimeter(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getIntersection method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_Line l = new V3D_Line(P1N1P0, P1P2P0, oom);
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P1P0, P2P0P0, oom);
        V3D_Geometry expResult = new V3D_LineSegment(P1P0P0, P1P1P0, oom);
        V3D_Geometry result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getIntersection method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(P2N2P0, P2P1P0, oom);
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P2P2P0, new V3D_Vector(P4, P0, P0), oom);
        V3D_Geometry expResult = new V3D_LineSegment(P2P0P0, P2P1P0, oom);
        V3D_Geometry result = instance.getIntersection(l, oom, true);
        //System.out.println(result);
        //System.out.println(expResult);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        int oom = -1;
        V3D_Line l = new V3D_Line(P1N1P0, P1P2P0, oom);
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P1P0, P2P0P0, oom);
        assertTrue(instance.isEnvelopeIntersectedBy(l, oom));
    }

    /**
     * Test of getCentroid method, of class V3D_Triangle.
     */
    @Test
    public void testGetCentroid() {
        System.out.println("getCentroid");
        int oom;
        V3D_Triangle instance;
        V3D_Point expResult;
        V3D_Point result;
        // Test
        oom = -3; //-2 fails!
        instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P1P0, oom);
        expResult = new V3D_Point(Math_BigRational.valueOf(2, 3), 
                Math_BigRational.valueOf(1, 3), Math_BigRational.ZERO);
        result = instance.getCentroid(oom);
        assertTrue(expResult.equals(result));
    }

}
