/*
 * Copyright 2022 Centre for Computational Geography.
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
import java.util.ArrayList;
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
 *
 * @author Andy Turner
 */
public class V3D_LineSegmentsCollinearTest extends V3D_Test {

    public V3D_LineSegmentsCollinearTest() {
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
     * Test of toString method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_LineSegmentsCollinear instance = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pP0P0P0, pP1P0P0),
                new V3D_LineSegment(pP0P1P0, pP1P1P0));
        String expResult = "uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegmentsCollinear(V3D_LineSegment\n"
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
                + "), V3D_LineSegment\n"
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
                + "  dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " q=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " v=null\n"
                + "))";
        String result = instance.toString();
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        Object o = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pP0P0P0, pP1P0P0),
                new V3D_LineSegment(pP0P1P0, pP1P1P0));
        V3D_LineSegmentsCollinear instance = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pP0P0P0, pP1P0P0),
                new V3D_LineSegment(pP0P1P0, pP1P1P0));
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        // No test
    }

    /**
     * Test of equals method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testEquals_V3D_LineSegmentsCollinear() {
        System.out.println("equals");
        V3D_LineSegmentsCollinear l = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pP0P0P0, pP1P0P0),
                new V3D_LineSegment(pP0P1P0, pP1P1P0));
        V3D_LineSegmentsCollinear instance = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pP0P0P0, pP1P0P0),
                new V3D_LineSegment(pP0P1P0, pP1P1P0));
        boolean result = instance.equals(l);
        assertTrue(result);
        // Test 2
        instance = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pP0P1P0, pP1P0P0),
                new V3D_LineSegment(pP0P1P0, pP1P1P0));
        result = instance.equals(l);
        assertFalse(result);
    }

    /**
     * Test of getGeometry method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetUnion() {
        System.out.println("getUnion");
        // Test 1 touch
        V3D_LineSegment l1 = new V3D_LineSegment(pN2P0P0, pN1P0P0);
        V3D_LineSegment l2 = new V3D_LineSegment(pN1P0P0, pP0P0P0);
        int oom = -3;
        V3D_Geometry expResult = new V3D_LineSegment(pN2P0P0, pP0P0P0);
        V3D_Geometry result = V3D_LineSegmentsCollinear.getGeometry(l1, l2, oom);//getUnion(l1, l2, oom);
        assertEquals(expResult, result);
        result = V3D_LineSegmentsCollinear.getGeometry(l2, l1, oom);
        assertEquals(expResult, result);
        // Test 2 overlap
        l2 = new V3D_LineSegment(pN2P0P0, pP0P0P0);
        expResult = new V3D_LineSegment(pN2P0P0, pP0P0P0);
        result = V3D_LineSegmentsCollinear.getGeometry(l1, l2, oom);
        assertEquals(expResult, result);
        result = V3D_LineSegmentsCollinear.getGeometry(l2, l1, oom);
        assertEquals(expResult, result);
        l1 = new V3D_LineSegment(pN2P0P0, pP2P0P0);
        l2 = new V3D_LineSegment(pN1P0P0, pP1P0P0);
        expResult = new V3D_LineSegment(pN2P0P0, pP2P0P0);
        result = V3D_LineSegmentsCollinear.getGeometry(l1, l2, oom);
        assertEquals(expResult, result);
        // No overlap
        l1 = new V3D_LineSegment(pN2P0P0, pN1P0P0);
        l2 = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        expResult = new V3D_LineSegmentsCollinear(l1, l2);
        result = V3D_LineSegmentsCollinear.getGeometry(l1, l2, oom);
        assertEquals(expResult, result);
        result = V3D_LineSegmentsCollinear.getGeometry(l2, l1, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getEnvelope method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetEnvelope() {
        // No test.
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        // No test.
    }

    /**
     * Test of getDistanceSquared method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistanceSquared_V3D_Point_int() {
        // No test.
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistance_V3D_Line_int() {
        // No test.
    }

    /**
     * Test of getDistanceSquared method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistanceSquared_V3D_Line_int() {
        // No test.
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistance_V3D_LineSegment_int() {
        // No test.
    }

    /**
     * Test of getDistanceSquared method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegment_int() {
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("isIntersectedBy");
        // No test yet.
//        V3D_Point pt = null;
//        int oom = 0;
//        V3D_LineSegmentsCollinear instance = null;
//        boolean expResult = false;
//        boolean result = instance.isIntersectedBy(pt, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line_int() {
        System.out.println("isIntersectedBy");
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_int() {
        System.out.println("isIntersectedBy");
        // No test.
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersection");
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0);
        int oom = -3;
        V3D_LineSegmentsCollinear instance = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pN1P0P0, pP0P0P0),
                new V3D_LineSegment(pP1P0P0, pP2P0P0));
        V3D_Geometry expResult = instance = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pN1P0P0, pP0P0P0),
                new V3D_LineSegment(pP1P0P0, pP2P0P0));
        V3D_Geometry result = instance.getIntersection(l, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_int() {
        System.out.println("getIntersection");
        V3D_LineSegment l = new V3D_LineSegment(pN1P0P0, pP0P0P0);
        int oom = -3;
        V3D_LineSegmentsCollinear instance = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pN1P0P0, pP0P0P0),
                new V3D_LineSegment(pP1P0P0, pP2P0P0));
        V3D_Geometry expResult = new V3D_LineSegment(pN1P0P0, pP0P0P0);
        V3D_Geometry result = instance.getIntersection(l, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testIsIntersectedBy_V3D_Plane_int() {
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testIsIntersectedBy_V3D_Triangle_int() {
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testIsIntersectedBy_V3D_Tetrahedron_int() {
        // No test yet.
    }

    /**
     * Test of simplify method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testSimplify() {
        System.out.println("simplify");
        V3D_LineSegmentsCollinear instance = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pP0P0P0, pP1P0P0),
                new V3D_LineSegment(pP1P0P0, pP2P0P0));
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP2P0P0);
        V3D_Geometry result = instance.simplify();
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pP0P0P0, pP1P0P0),
                new V3D_LineSegment(pP1P0P0, pP2P0P0),
                new V3D_LineSegment(pP0P0P0, pP2P0P0));
        expResult = new V3D_LineSegment(pP0P0P0, pP2P0P0);
        result = instance.simplify();
        assertEquals(expResult, result);
    }

    /**
     * Test of simplify0 method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testSimplify0() {
        // No test.
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetIntersection_V3D_Plane_int() {
        System.out.println("getIntersection");
        // No test yet.
//        V3D_Plane p = null;
//        int oom = 0;
//        V3D_LineSegmentsCollinear instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersection(p, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetIntersection_V3D_Triangle_int() {
        System.out.println("getIntersection");
        // No test yet.
//        V3D_Triangle t = null;
//        int oom = 0;
//        V3D_LineSegmentsCollinear instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersection(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetIntersection_V3D_Tetrahedron_int() {
        System.out.println("getIntersection");
        // No test yet.
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_LineSegmentsCollinear instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersection(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistance_V3D_Plane_int() {
        // No test.
    }

    /**
     * Test of getDistanceSquared method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistanceSquared_V3D_Plane_int() {
        // No test.
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistance_V3D_Triangle_int() {
        // No test.
    }

    /**
     * Test of getDistanceSquared method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistanceSquared_V3D_Triangle_int() {
        // No test.
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistance_V3D_Tetrahedron_int() {
        // No test.
    }

    /**
     * Test of getDistanceSquared method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistanceSquared_V3D_Tetrahedron_int() {
        // No test.
    }

}
