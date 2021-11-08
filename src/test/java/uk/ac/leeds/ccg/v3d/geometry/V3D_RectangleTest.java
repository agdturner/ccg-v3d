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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.V3D_Test;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * Test of V3D_Rectangle class.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V3D_RectangleTest extends V3D_Test {

    public V3D_RectangleTest() {
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
     * Test of getEnvelope method, of class V3D_Rectangle.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        int oom = -1;
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
        instance = new V3D_Rectangle(N1P1P0, P1P1P0, P1N1P0, N1N1P0, oom);
        expResult = new V3D_Envelope(oom, N1N1P0, P1P1P0);
        result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Rectangle(N1P1P1, P1P1P0, P1N1P0, N1N1P1, oom);
        expResult = new V3D_Envelope(oom, N1N1P0, P1P1P1);
        result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Rectangle(N1P1P1, P1P1N1, P1N1N1, N1N1P1, oom);
        expResult = new V3D_Envelope(oom, N1N1N1, P1P1P1);
        result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result));
        // Test 4
        instance = new V3D_Rectangle(N1N1N1, P1N1N1, P1P1N1, N1P1N1, oom);
        expResult = new V3D_Envelope(oom, N1N1N1, P1P1N1);
        result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_Point pt = P0P0P0;
        V3D_Rectangle instance = new V3D_Rectangle(N1P1P0, P1P1P0, P1N1P0, N1N1P0, oom);
        assertTrue(instance.isIntersectedBy(pt, oom));
        // Test 2
        instance = new V3D_Rectangle(N1P0P0, P0P1P0, P1P0P0, P0N1P0, oom);
        assertTrue(instance.isIntersectedBy(pt, oom));
        // Test 3
        Math_BigRational half = Math_BigRational.ONE.divide(2);
        pt = new V3D_Point(half, half, P0);
        assertTrue(instance.isIntersectedBy(pt, oom));
        // Test 3
        oom = -12;
        pt = new V3D_Point(half.add(Math_BigRational.valueOf("0.00000000001")), half, P0);
        assertFalse(instance.isIntersectedBy(pt, oom));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_Line l = new V3D_Line(P0P0N1, P0P0P1, oom);
        V3D_Rectangle instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        instance = new V3D_Rectangle(N1P1P0, P1P1P0, P1N1P0, N1N1P0, oom);
        expResult = P0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of toString method, of class V3D_Rectangle.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int oom = V3D_Environment.DEFAULT_OOM;
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
        String expResult = "V3D_Rectangle\n"
                + "(\n"
                + " p=V3D_Point\n"
                + " (\n"
                + "  pos=V3D_Vector\n"
                + "  (\n"
                + "   dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + "  )\n"
                + "  ,\n"
                + "  offset=V3D_Vector\n"
                + "  (\n"
                + "   dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + "  )\n"
                + " )\n"
                + " ,\n"
                + " q=V3D_Point\n"
                + " (\n"
                + "  pos=V3D_Vector\n"
                + "  (\n"
                + "   dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + "  )\n"
                + "  ,\n"
                + "  offset=V3D_Vector\n"
                + "  (\n"
                + "   dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + "  )\n"
                + " )\n"
                + " ,\n"
                + " r=V3D_Point\n"
                + " (\n"
                + "  pos=V3D_Vector\n"
                + "  (\n"
                + "   dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "   dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + "  )\n"
                + "  ,\n"
                + "  offset=V3D_Vector\n"
                + "  (\n"
                + "   dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + "  )\n"
                + " )\n"
                + " ,\n"
                + " s=V3D_Point\n"
                + " (\n"
                + "  pos=V3D_Vector\n"
                + "  (\n"
                + "   dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "   dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + "  )\n"
                + "  ,\n"
                + "  offset=V3D_Vector\n"
                + "  (\n"
                + "   dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "   dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + "  )\n"
                + " )\n"
                + ")";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of apply method, of class V3D_Rectangle.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_Line l = new V3D_Line(N1N1N1, P2P2P2, oom);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom));
        //Test 2
        assertFalse(instance.isIntersectedBy(new V3D_Line(N1N1N1, N1N1P1, oom), oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(N1N1P0, P1P1P0, oom);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom));
        //Test 2
        assertFalse(instance.isIntersectedBy(new V3D_Line(P0N1P1, P0N1N1, oom), oom));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_Line l = new V3D_Line(N1N1P0, P1P1P0, oom);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
        V3D_Geometry result = instance.getIntersection(l, oom);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P1P0, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(N1N1P0, P2P2P0, oom);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
        V3D_Geometry result = instance.getIntersection(l, oom, true);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P1P0, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        int oom = -1;
        V3D_Line l = new V3D_LineSegment(N1N1P0, P2P2P0, oom);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
        assertTrue(instance.isEnvelopeIntersectedBy(l, oom));
    }

    /**
     * Test of getPerimeter method, of class V3D_Rectangle.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        int oom = -1;
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
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
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
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
        int oom = -1;
        V3D_Point p = new V3D_Point(N1N1P0);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
        BigDecimal expResult = new Math_BigRationalSqrt(2, oom).toBigDecimal(oom);
        BigDecimal result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

}
