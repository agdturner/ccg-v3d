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
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.V3D_Test;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

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
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, oom, rm);
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
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        String pad = "";
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, oom, rm);
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
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        String pad = "";
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, oom, rm);
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
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, oom, rm);
        V3D_Envelope expResult = new V3D_Envelope(e, oom, rm, pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_Envelope result = instance.getEnvelope(oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getP method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetP() {
        System.out.println("getP");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, oom, rm);
        V3D_Point expResult = pP0P0P0;
        V3D_Point result = instance.getP();
        assertEquals(expResult, result);
    }

    /**
     * Test of getQ method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetQ() {
        System.out.println("getQ");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, oom, rm);
        V3D_Point expResult = pP1P0P0;
        V3D_Point result = instance.getQ();
        assertEquals(expResult, result);
    }

    /**
     * Test of getR method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetR() {
        System.out.println("getR");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, oom, rm);
        V3D_Point expResult = pP0P1P0;
        V3D_Point result = instance.getR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getS method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetS() {
        System.out.println("getS");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, oom, rm);
        V3D_Point expResult = pP0P1P1;
        V3D_Point result = instance.getS();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPqr method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetPqr() {
        System.out.println("getPqr");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, oom, rm);
        V3D_Triangle expResult = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        V3D_Triangle result = instance.getPqr();
        assertEquals(expResult, result);
    }

    /**
     * Test of getQsr method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetQsr() {
        System.out.println("getQsr");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, oom, rm);
        V3D_Triangle expResult = new V3D_Triangle(pP1P0P0, pP0P1P0, pP0P1P1, oom, rm);
        V3D_Triangle result = instance.getQsr();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSpr method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetSpr() {
        System.out.println("getSpr");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, oom, rm);
        V3D_Triangle expResult = new V3D_Triangle(pP0P0P0, pP0P1P0, pP0P1P1, oom, rm);
        V3D_Triangle result = instance.getSpr();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPsq method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetPsq() {
        System.out.println("getPsq");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, oom, rm);
        V3D_Triangle expResult = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P1, oom, rm);
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
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P0P1, oom, rm);
        BigDecimal expResult = (new Math_BigRationalSqrt(
                Math_BigRational.valueOf(3, 2), oom, rm).getSqrt(oom, rm)
                .multiply(new Math_BigRationalSqrt(
                        Math_BigRational.TWO, oom, rm).getSqrt(oom, rm).divide(2)))
                .add(Math_BigRational.valueOf(3, 2)).toBigDecimal(oom, rm);
        BigDecimal result = instance.getArea(oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getVolume method, of class V3D_Tetrahedron.
     * https://keisan.casio.com/exec/system/1329962711
     */
    @Test
    public void testGetVolume() {
        System.out.println("getVolume");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P0P1, oom, rm);
        BigDecimal expResult = Math_BigRational.valueOf(1, 6).toBigDecimal(oom, rm);
        BigDecimal result = instance.getVolume(oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCentroid method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetCentroid() {
        System.out.println("getCentroid");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron instance = new V3D_Tetrahedron(
                pN2N2N2, pP2N2N2, pP0P2N2,
                new V3D_Point(e, P0, P0, P4), oom, rm);
        V3D_Point expResult = new V3D_Point(e, P0P0P0, new V3D_Vector(
                Math_BigRational.ZERO, Math_BigRational.valueOf(1, 2).negate(),
                Math_BigRational.valueOf(1, 2).negate()));
        V3D_Point result = instance.getCentroid(oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Tetrahedron.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("isIntersectedBy");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point pt;
        V3D_Tetrahedron instance;
        // Test 1
        pt = pP0P0P0;
        //instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0, pP0P1P0, pP0P0P1);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 2
        pt = new V3D_Point(e, 0.5, 0.5, 0.5);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P2P0P0, P0P2P0, P0P0P2);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 3
        pt = new V3D_Point(e, -0.5, -0.5, -0.5);
        assertFalse(instance.isIntersectedBy(pt, oom, rm));
        // Test 4
        pt = new V3D_Point(e, -0.5, 0, 0);
        assertFalse(instance.isIntersectedBy(pt, oom, rm));
        // Test 5
        instance = new V3D_Tetrahedron(
                new V3D_Point(e, 0, 0, -75), 
                new V3D_Point(e, -50, -37.5, 10),
                new V3D_Point(e, 50, 37.5, 10),
                new V3D_Point(e, -50, 37.5, 10), oom, rm);
        //pt = new V3D_Point(e, -24, -19, 0);
        //pt = new V3D_Point(e, 0, 0, -74);
        //pt = new V3D_Point(e, 1, 1, 0);
        pt = new V3D_Point(e, -11, 11, 0);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Tetrahedron.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line_int() {
        System.out.println("isIntersectedBy");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l;
        V3D_Tetrahedron instance;
        // Test 1
        l = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(l, oom, rm));
        // Test 2
        l = new V3D_Line(pN2P0P0, pN1P0P0, oom, rm);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(l, oom, rm));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Tetrahedron.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_int() {
        System.out.println("isIntersectedBy");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l;
        V3D_Tetrahedron instance;
        // Test 1
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(l, oom, rm));
        // Test 2
        l = new V3D_LineSegment(pN1P0P0, pP0P0P0, oom, rm);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(l, oom, rm));
        // Test 3
        l = new V3D_LineSegment(pN2P0P0, pN1P0P0, oom, rm);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertFalse(instance.isIntersectedBy(l, oom, rm));
        // Test 4
        l = new V3D_LineSegment(pN2P0P0, pP2P0P0, oom, rm);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(l, oom, rm));
        // Test 5
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Tetrahedron(e, P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        assertTrue(instance.isIntersectedBy(l, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l;
        V3D_Tetrahedron instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        l = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Tetrahedron(e, P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        l = new V3D_Line(pN2N2P0, new V3D_Triangle(e, P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid(oom, rm), oom, rm);
        expResult = new V3D_LineSegment(pN2N2P0, new V3D_Triangle(e, P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid(oom, rm), oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getIntersection method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_int() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l;
        V3D_Tetrahedron instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Tetrahedron(e, P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Tetrahedron(e, P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        l = new V3D_LineSegment(pN2N2P0, new V3D_Triangle(e, P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid(oom, rm), oom, rm);
        expResult = new V3D_LineSegment(pN2N2P0, new V3D_Triangle(e, P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid(oom, rm), oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Tetrahedron(e, P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        l = new V3D_LineSegment(e, P0P0P0, N2N2P0, new V3D_Triangle(e, P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid(oom, rm).rel.multiply(P2, oom, rm).add(P2P2P0, oom, rm));
        expResult = new V3D_LineSegment(pN2N2P0, new V3D_Triangle(e, P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid(oom, rm), oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getIntersection method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetIntersection_V3D_Plane_int() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Plane p;
        V3D_Tetrahedron instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        p = V3D_Plane.X0;
        instance = new V3D_Tetrahedron(e, P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        expResult = new V3D_Triangle(pP0P0P0, pP0P0P2, pP0N2P0, oom, rm);
        result = instance.getIntersection(p, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        p = new V3D_Plane(V3D_Plane.X0);
        p.translate(P1P0P0, oom, rm);
        instance = new V3D_Tetrahedron(e, P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        expResult = new V3D_Triangle(e, P0P0P0, P1N2P0, P1N1P0, P1N1P1);
        result = instance.getIntersection(p, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetIntersection_V3D_Triangle_int() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Triangle t;
        V3D_Tetrahedron instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        t = new V3D_Triangle(pP0N2P0, pP0P2P0, pP0P0P2, oom, rm);
        instance = new V3D_Tetrahedron(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, oom, rm);
        expResult = new V3D_Triangle(pP0P0P0, pP0P0P2, pP0N2P0, oom, rm);
        result = instance.getIntersection(t, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        t = new V3D_Triangle(pP1N2P0, pP1P2P0, pP1P0P2, oom, rm);
        instance = new V3D_Tetrahedron(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, oom, rm);
        expResult = new V3D_Triangle(pP1N2P0, pP1N1P0, pP1N1P1, oom, rm);
        result = instance.getIntersection(t, oom, rm);
        assertEquals(expResult, result);
        // Test 3
        t = new V3D_Triangle(pP1N1P0, pP1P1P0, pP1P0P1, oom, rm);
        instance = new V3D_Tetrahedron(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, oom, rm);
        expResult = new V3D_Triangle(pP1N1P0, pP1P1P0, pP1P0P1, oom, rm);
        result = instance.getIntersection(t, oom, rm);
        System.out.println("expResult " + expResult);
        System.out.println("result " + result);
        assertEquals(expResult, result);
        // Test 4
        t = new V3D_Triangle(pN1N1P0, pP1N1P0, pN1P1P0, oom, rm);
        instance = new V3D_Tetrahedron(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, oom, rm);
        expResult = new V3D_Triangle(pN1N1P0, pP1N1P0, pN1P1P0, oom, rm);
        result = instance.getIntersection(t, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Tetrahedron covered by 
     * {@link #testGetDistanceSquared_V3D_Point_int()}.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetDistanceSquared_V3D_Point_int() {
        System.out.println("getDistanceSquared");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point p;
        V3D_Tetrahedron instance;
        Math_BigRational expResult;
        Math_BigRational result;
        // Test 1
        instance = new V3D_Tetrahedron(e, P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        p = pP0P0P0;
        expResult = Math_BigRational.ZERO;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = new V3D_Tetrahedron(e, P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        p = pN2N2N2;
        expResult = Math_BigRational.valueOf(4);
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

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

    /**
     * Test of isIntersectedBy method, of class V3D_Tetrahedron.
     */
    @Test
    public void testIsIntersectedBy_V3D_Plane_int() {
        System.out.println("isIntersectedBy");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Plane p;
        V3D_Tetrahedron instance;
        // Test 1
        p = V3D_Plane.X0;
        instance = new V3D_Tetrahedron(e, P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        assertTrue(instance.isIntersectedBy(p, oom, rm));
        // Test 1
        p = new V3D_Plane(V3D_Plane.X0);
        p.translate(P1P0P0, oom, rm);
        instance = new V3D_Tetrahedron(e, P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        assertTrue(instance.isIntersectedBy(p, oom, rm));
    }

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
