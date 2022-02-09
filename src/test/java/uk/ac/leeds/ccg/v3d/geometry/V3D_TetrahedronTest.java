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
public class V3D_TetrahedronTest extends V3D_Test {

    public V3D_TetrahedronTest() {
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
     * Test of toString method, of class V3D_Tetrahedron.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        String expResult = "V3D_Tetrahedron\n"
                + "(\n"
                + " oom=-3\n"
                + " ,\n"
                + " offset=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " ) ,\n"
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
                + " r=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " s=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0)\n"
                + " )\n"
                + ")";
        String result = instance.toString();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class V3D_Tetrahedron.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        String expResult = "V3D_Tetrahedron\n"
                + "(\n"
                + " oom=-3\n"
                + " ,\n"
                + " offset=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " ) ,\n"
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
                + " r=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " s=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0)\n"
                + " )\n"
                + ")";
        String result = instance.toString(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toStringFields method, of class V3D_Tetrahedron.
     */
    @Test
    public void testToStringFields() {
        System.out.println("toStringFields");
        String pad = "";
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        String expResult = "oom=-3\n"
                + ",\n"
                + "offset=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + "),\n"
                + "p=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + ")\n"
                + ",\n"
                + "q=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + ")\n"
                + ",\n"
                + "r=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + ")\n"
                + ",\n"
                + "s=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0)\n"
                + ")";
        String result = instance.toStringFields(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getEnvelope method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_Envelope expResult = new V3D_Envelope(e, pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_Envelope result = instance.getEnvelope();
        assertEquals(expResult, result);
    }

    /**
     * Test of getP method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetP() {
        System.out.println("getP");
        int oom = 0;
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_Point expResult = pP0P0P0;
        V3D_Point result = instance.getP(oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getQ method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetQ() {
        System.out.println("getQ");
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_Point expResult = pP1P0P0;
        V3D_Point result = instance.getQ(e.oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getR method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetR() {
        System.out.println("getR");
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_Point expResult = pP0P1P0;
        V3D_Point result = instance.getR(e.oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getS method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetS() {
        System.out.println("getS");
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_Point expResult = pP0P1P1;
        V3D_Point result = instance.getS(e.oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPqr method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetPqr() {
        System.out.println("getPqr");
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_Triangle expResult = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0);
        V3D_Triangle result = instance.getPqr();
        assertEquals(expResult, result);
    }

    /**
     * Test of getQsr method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetQsr() {
        System.out.println("getQsr");
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_Triangle expResult = new V3D_Triangle(pP1P0P0, pP0P1P0, pP0P1P1);
        V3D_Triangle result = instance.getQsr();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSpr method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetSpr() {
        System.out.println("getSpr");
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_Triangle expResult = new V3D_Triangle(pP0P0P0, pP0P1P0, pP0P1P1);
        V3D_Triangle result = instance.getSpr();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPsq method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetPsq() {
        System.out.println("getPsq");
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_Triangle expResult = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P1);
        V3D_Triangle result = instance.getPsq();
        assertEquals(expResult, result);
    }

    /**
     * Test of getArea method, of class V3D_Tetrahedron.
     * https://keisan.casio.com/exec/system/1329962711
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P0P1);
        BigDecimal expResult = (new Math_BigRationalSqrt(
                Math_BigRational.valueOf(3, 2), e.oom).getSqrt(e.oom)
                .multiply(new Math_BigRationalSqrt(
                        Math_BigRational.TWO, e.oom).getSqrt(e.oom).divide(2)))
                .add(Math_BigRational.valueOf(3, 2)).toBigDecimal(e.oom);
        BigDecimal result = instance.getArea(e.oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getVolume method, of class V3D_Tetrahedron.
     * https://keisan.casio.com/exec/system/1329962711
     */
    @Test
    public void testGetVolume() {
        System.out.println("getVolume");
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P0P1);
        BigDecimal expResult = Math_BigRational.valueOf(1, 6).toBigDecimal(e.oom);
        BigDecimal result = instance.getVolume(e.oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCentroid method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetCentroid() {
        System.out.println("getCentroid");
        V3D_Tetrahedron instance = new V3D_Tetrahedron(
                pN2N2N2, pP2N2N2, pP0P2N2,
                new V3D_Point(e, P0, P0, P4));
        V3D_Point expResult = new V3D_Point(e, P0P0P0, new V3D_Vector(
                Math_BigRational.ZERO, Math_BigRational.valueOf(1, 2).negate(), 
                Math_BigRational.valueOf(1, 2).negate()));
        V3D_Point result = instance.getCentroid(e.oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Tetrahedron.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("isIntersectedBy");
        V3D_Point pt;
        V3D_Tetrahedron instance;
        // Test 1
        pt = pP0P0P0;
        //instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0, pP0P1P0, pP0P0P1);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 2
        pt = new V3D_Point(e, 0.5, 0.5, 0.5);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P2P0P0, P0P2P0, P0P0P2);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 3
        pt = new V3D_Point(e, -0.5, -0.5, -0.5);
        assertFalse(instance.isIntersectedBy(pt, e.oom));
        // Test 4
        pt = new V3D_Point(e, -0.5, 0, 0);
        assertFalse(instance.isIntersectedBy(pt, e.oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Tetrahedron.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line_int() {
        System.out.println("isIntersectedBy");
        V3D_Line l;
        V3D_Tetrahedron instance;
        // Test 1
        l = new V3D_Line(pP0P0P0, pP1P0P0);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(l, e.oom));        
        // Test 2
        l = new V3D_Line(pN2P0P0, pN1P0P0);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(l, e.oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Tetrahedron.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_int() {
        System.out.println("isIntersectedBy");
        V3D_LineSegment l;
        V3D_Tetrahedron instance;
        // Test 1
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 2
        l = new V3D_LineSegment(pN1P0P0, pP0P0P0);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 3
        l = new V3D_LineSegment(pN2P0P0, pN1P0P0);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertFalse(instance.isIntersectedBy(l, e.oom));
        // Test 4
        l = new V3D_LineSegment(pN2P0P0, pP2P0P0);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 5
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        instance = new V3D_Tetrahedron(e, P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        assertTrue(instance.isIntersectedBy(l, e.oom));
    }

//    /**
//     * Test of getIntersection method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetIntersection_V3D_Line_int() {
//        System.out.println("getIntersection");
//        V3D_Line l = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersection(l, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntersection method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetIntersection_V3D_LineSegment_int() {
//        System.out.println("getIntersection");
//        V3D_LineSegment l = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersection(l, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntersection method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetIntersection_V3D_Plane_int() {
//        System.out.println("getIntersection");
//        V3D_Plane p = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersection(p, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntersection method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetIntersection_V3D_Triangle_int() {
//        System.out.println("getIntersection");
//        V3D_Triangle t = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersection(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetDistance_V3D_Point_int() {
//        System.out.println("getDistance");
//        V3D_Point p = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(p, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_Point_int() {
//        System.out.println("getDistanceSquared");
//        V3D_Point p = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        Math_BigRational expResult = null;
//        Math_BigRational result = instance.getDistanceSquared(p, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isEnvelopeIntersectedBy method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testIsEnvelopeIntersectedBy() {
//        System.out.println("isEnvelopeIntersectedBy");
//        V3D_Line l = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        boolean expResult = false;
//        boolean result = instance.isEnvelopeIntersectedBy(l, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetDistance_V3D_Line_int() {
//        System.out.println("getDistance");
//        V3D_Line l = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(l, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetDistance_V3D_LineSegment_int() {
//        System.out.println("getDistance");
//        V3D_LineSegment l = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(l, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setOffset method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testSetOffset() {
//        System.out.println("setOffset");
//        V3D_Vector offset = null;
//        V3D_Tetrahedron instance = null;
//        instance.setOffset(offset);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of rotate method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testRotate() {
//        System.out.println("rotate");
//        V3D_Vector axisOfRotation = null;
//        Math_BigRational theta = null;
//        V3D_Tetrahedron instance = null;
//        instance.rotate(axisOfRotation, theta);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isIntersectedBy method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Plane_int() {
//        System.out.println("isIntersectedBy");
//        V3D_Plane p = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        boolean expResult = false;
//        boolean result = instance.isIntersectedBy(p, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isIntersectedBy method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Triangle_int() {
//        System.out.println("isIntersectedBy");
//        V3D_Triangle t = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        boolean expResult = false;
//        boolean result = instance.isIntersectedBy(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isIntersectedBy method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Tetrahedron_int() {
//        System.out.println("isIntersectedBy");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        boolean expResult = false;
//        boolean result = instance.isIntersectedBy(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntersection method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetIntersection_V3D_Tetrahedron_int() {
//        System.out.println("getIntersection");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersection(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_Line_int() {
//        System.out.println("getDistanceSquared");
//        V3D_Line l = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        Math_BigRational expResult = null;
//        Math_BigRational result = instance.getDistanceSquared(l, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_LineSegment_int() {
//        System.out.println("getDistanceSquared");
//        V3D_LineSegment l = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        Math_BigRational expResult = null;
//        Math_BigRational result = instance.getDistanceSquared(l, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetDistance_V3D_Plane_int() {
//        System.out.println("getDistance");
//        V3D_Plane p = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(p, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_Plane_int() {
//        System.out.println("getDistanceSquared");
//        V3D_Plane p = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        Math_BigRational expResult = null;
//        Math_BigRational result = instance.getDistanceSquared(p, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetDistance_V3D_Triangle_int() {
//        System.out.println("getDistance");
//        V3D_Triangle t = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_Triangle_int() {
//        System.out.println("getDistanceSquared");
//        V3D_Triangle t = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        Math_BigRational expResult = null;
//        Math_BigRational result = instance.getDistanceSquared(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetDistance_V3D_Tetrahedron_int() {
//        System.out.println("getDistance");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_Tetrahedron_int() {
//        System.out.println("getDistanceSquared");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Tetrahedron instance = null;
//        Math_BigRational expResult = null;
//        Math_BigRational result = instance.getDistanceSquared(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
