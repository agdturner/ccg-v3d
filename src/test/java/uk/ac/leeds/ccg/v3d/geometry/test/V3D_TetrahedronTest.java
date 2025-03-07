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
package uk.ac.leeds.ccg.v3d.geometry.test;

import ch.obermuhlner.math.big.BigRational;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Envelope;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Geometry;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Plane;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Ray;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Rectangle;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Tetrahedron;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Triangle;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Vector;

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
        String expResult = """
                           V3D_Tetrahedron
                           (
                            p= V3D_Vector(dx=0, dy=0, dz=0),
                            q= V3D_Vector(dx=1, dy=0, dz=0),
                            r= V3D_Vector(dx=0, dy=1, dz=0),
                            s= V3D_Vector(dx=0, dy=1, dz=1)
                           )""";
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
        String expResult = """
                           V3D_Tetrahedron
                           (
                            offset=V3D_Vector
                            (
                             dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                             dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                             dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
                            ) ,
                            p=V3D_Vector
                            (
                             dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                             dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                             dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
                            )
                            ,
                            q=V3D_Vector
                            (
                             dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),
                             dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                             dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
                            )
                            ,
                            r=V3D_Vector
                            (
                             dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                             dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),
                             dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
                            )
                            ,
                            s=V3D_Vector
                            (
                             dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                             dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),
                             dz=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0)
                            )
                           )""";
        String result = instance.toString(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

//    /**
//     * Test of toStringFields method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testToStringFields() {
//        System.out.println("toStringFields");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        String pad = "";
//        V3D_Tetrahedron instance = new V3D_Tetrahedron(pP0P0P0, pP1P0P0,
//                pP0P1P0, pP0P1P1, oom, rm);
//        String expResult = """
//                           offset=V3D_Vector
//                           (
//                            dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
//                            dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
//                            dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
//                           ),
//                           p=V3D_Vector
//                           (
//                            dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
//                            dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
//                            dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
//                           )
//                           ,
//                           q=V3D_Vector
//                           (
//                            dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),
//                            dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
//                            dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
//                           )
//                           ,
//                           r=V3D_Vector
//                           (
//                            dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
//                            dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),
//                            dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
//                           )
//                           ,
//                           s=V3D_Vector
//                           (
//                            dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
//                            dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),
//                            dz=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0)
//                           )""";
//        String result = instance.toStringFields(pad);
//        //System.out.println(result);
//        assertEquals(expResult, result);
//    }

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
        V3D_Envelope expResult = new V3D_Envelope(oom, pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_Envelope result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result, oom));
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
        assertTrue(expResult.equals(result, oom, rm));
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
        assertTrue(expResult.equals(result, oom, rm));
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
        assertTrue(expResult.equals(result, oom, rm));
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
        assertTrue(expResult.equals(result, oom, rm));
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
        V3D_Triangle result = instance.getPqr(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
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
        V3D_Triangle result = instance.getQsr(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
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
        V3D_Triangle result = instance.getSpr(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
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
        V3D_Triangle result = instance.getPsq(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
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
        BigRational expResult = Math_BigRational.round(
                (new Math_BigRationalSqrt(BigRational.valueOf(3, 2), oom, rm)
                        .getSqrt(oom, rm).multiply(new Math_BigRationalSqrt(
                        BigRational.TWO, oom, rm).getSqrt(oom, rm).divide(2)))
                        .add(BigRational.valueOf(3, 2)), oom, rm);
        BigRational result = instance.getArea(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
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
        BigRational expResult = BigRational.valueOf(1, 6);
        BigRational result = instance.getVolume(oom, rm);
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
                new V3D_Point(P0, P0, P4), oom, rm);
        V3D_Point expResult = new V3D_Point(P0P0P0, new V3D_Vector(
                BigRational.ZERO, BigRational.valueOf(1, 2).negate(),
                BigRational.valueOf(1, 2).negate()));
        V3D_Point result = instance.getCentroid(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of intersects method, of class V3D_Tetrahedron.
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
        instance = new V3D_Tetrahedron(P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 2
        pt = new V3D_Point(0.5, 0.5, 0.5);
        instance = new V3D_Tetrahedron(P0P0P0, P0P0P0, P2P0P0, P0P2P0, P0P0P2);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 3
        pt = new V3D_Point(-0.5, -0.5, -0.5);
        assertFalse(instance.isIntersectedBy(pt, oom, rm));
        // Test 4
        pt = new V3D_Point(-0.5, 0, 0);
        assertFalse(instance.isIntersectedBy(pt, oom, rm));
        // Test 5
        instance = new V3D_Tetrahedron(
                new V3D_Point(0, 0, -75),
                new V3D_Point(-50, -37.5, 10),
                new V3D_Point(50, 37.5, 10),
                new V3D_Point(-50, 37.5, 10), oom, rm);
        //pt = new V3D_Point(-24, -19, 0);
        //pt = new V3D_Point(0, 0, -74);
        //pt = new V3D_Point(1, 1, 0);
        pt = new V3D_Point(-11, 11, 0);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
    }

//    /**
//     * Test of intersects method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Line_int() {
//        System.out.println("intersects");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Line l;
//        V3D_Tetrahedron instance;
//        // Test 1
//        l = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
//        instance = new V3D_Tetrahedron(P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 2
//        l = new V3D_Line(pN2P0P0, pN1P0P0, oom, rm);
//        instance = new V3D_Tetrahedron(P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
//        assertTrue(instance.intersects(l, oom, rm));
//    }
//
//    /**
//     * Test of intersects method, of class V3D_Tetrahedron.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_LineSegment_int() {
//        System.out.println("intersects");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_LineSegment l;
//        V3D_Tetrahedron instance;
//        // Test 1
//        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        instance = new V3D_Tetrahedron(P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 2
//        l = new V3D_LineSegment(pN1P0P0, pP0P0P0, oom, rm);
//        instance = new V3D_Tetrahedron(P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 3
//        l = new V3D_LineSegment(pN2P0P0, pN1P0P0, oom, rm);
//        instance = new V3D_Tetrahedron(P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
//        assertFalse(instance.intersects(l, oom, rm));
//        // Test 4
//        l = new V3D_LineSegment(pN2P0P0, pP2P0P0, oom, rm);
//        instance = new V3D_Tetrahedron(P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 5
//        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        instance = new V3D_Tetrahedron(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
//        assertTrue(instance.intersects(l, oom, rm));
//    }
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
        instance = new V3D_Tetrahedron(P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 2
        instance = new V3D_Tetrahedron(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        l = new V3D_Line(pN2N2P0, new V3D_Triangle(P0P0P0, P2N2P0, N2P2P0, P0P0P2, oom, rm).getCentroid(oom, rm), oom, rm);
        expResult = new V3D_LineSegment(pN2N2P0, new V3D_Triangle(P0P0P0, P2N2P0, N2P2P0, P0P0P2, oom, rm).getCentroid(oom, rm), oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
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
        // Test 1 to Test 4 from the centre to each corner
        // Test 1 
        instance = new V3D_Tetrahedron(P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 2
        l = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 3
        l = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 4
        l = new V3D_LineSegment(pP0P0P0, pP0P0P1, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP0P0P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 5 to Test 8 from a corner to the middle of the opposite side.
        // Test 5
        instance = new V3D_Tetrahedron(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        l = new V3D_LineSegment(pN2N2P0, new V3D_Triangle(P0P0P0, P2N2P0, N2P2P0, P0P0P2, oom, rm).getCentroid(oom, rm), oom, rm);
        expResult = new V3D_LineSegment(pN2N2P0, new V3D_Triangle(P0P0P0, P2N2P0, N2P2P0, P0P0P2, oom, rm).getCentroid(oom, rm), oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 6
        l = new V3D_LineSegment(pP2N2P0, new V3D_Triangle(P0P0P0, N2N2P0, N2P2P0, P0P0P2, oom, rm).getCentroid(oom, rm), oom, rm);
        expResult = new V3D_LineSegment(pP2N2P0, new V3D_Triangle(P0P0P0, N2N2P0, N2P2P0, P0P0P2, oom, rm).getCentroid(oom, rm), oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 7
        l = new V3D_LineSegment(pN2P2P0, new V3D_Triangle(P0P0P0, N2N2P0, P2N2P0, P0P0P2, oom, rm).getCentroid(oom, rm), oom, rm);
        expResult = new V3D_LineSegment(pN2P2P0, new V3D_Triangle(P0P0P0, N2N2P0, P2N2P0, P0P0P2, oom, rm).getCentroid(oom, rm), oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 8
        l = new V3D_LineSegment(pP0P0P2, new V3D_Triangle(P0P0P0, N2N2P0, P2N2P0, N2P2P0, oom, rm).getCentroid(oom, rm), oom, rm);
        expResult = new V3D_LineSegment(pP0P0P2, new V3D_Triangle(P0P0P0, N2N2P0, P2N2P0, N2P2P0, oom, rm).getCentroid(oom, rm), oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 9 to Test 12 from a corner away from the centre.
        // Test 9
        instance = new V3D_Tetrahedron(P0P0P0, N1N1P0, P1N1P0, N1P1P0, P0P0P1);
        l = new V3D_LineSegment(pN1N1P0, pN2N2P0, oom, rm);
        expResult = pN1N1P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 10
        l = new V3D_LineSegment(pP1N1P0, pP2N2P0, oom, rm);
        expResult = pP1N1P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 11
        l = new V3D_LineSegment(pN1P1P0, pN2P2P0, oom, rm);
        expResult = pN1P1P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 12
        l = new V3D_LineSegment(pP0P0P1, pP0P0P2, oom, rm);
        expResult = pP0P0P1;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 13 to Test 24 from each corner along an edge.
        //instance = new V3D_Tetrahedron(P0P0P0, N1N1P0, P1N1P0, N1P1P0, P0P0P1);
        // Test 13 to Test 15 from corner A along an edge.
        // Test 13
        l = new V3D_LineSegment(pN1N1P0, pP1N1P0, oom, rm);
        expResult = l;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        // Test 14
        l = new V3D_LineSegment(pN1N1P0, pN1P1P0, oom, rm);
        expResult = l;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        // Test 15
        l = new V3D_LineSegment(pN1N1P0, pP0P0P1, oom, rm);
        expResult = l;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        instance = new V3D_Tetrahedron(P0P0P0, N1N1P0, P1N1P0, N1P1P0, P0P0P1);
        // Test 16 to Test 18 from corner B along an edge.
        // Test 16
        l = new V3D_LineSegment(pP1N1P0, pN1P1P0, oom, rm);
        expResult = l;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        // Test 17
        l = new V3D_LineSegment(pP1N1P0, pP0P0P1, oom, rm);
        expResult = l;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        // Test 18
        l = new V3D_LineSegment(pP1N1P0, pN1N1P0, oom, rm);
        expResult = l;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        // Test 19 to Test 21 from corner C along an edge.
        // Test 19
        l = new V3D_LineSegment(pN1P1P0, pP0P0P1, oom, rm);
        expResult = l;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        // Test 20
        l = new V3D_LineSegment(pN1P1P0, pN1N1P0, oom, rm);
        expResult = l;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        // Test 21
        l = new V3D_LineSegment(pN1P1P0, pP1N1P0, oom, rm);
        expResult = l;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        // Test 22 to Test 24 from corner D along an edge.
        // Test 22
        l = new V3D_LineSegment(pP0P0P1, pN1N1P0, oom, rm);
        expResult = l;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        // Test 23
        l = new V3D_LineSegment(pP0P0P1, pP1N1P0, oom, rm);
        expResult = l;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        // Test 24
        l = new V3D_LineSegment(pP0P0P1, pN1P1P0, oom, rm);
        expResult = l;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        instance = new V3D_Tetrahedron(P0P0P0, N1N1P0, P1N1P0, N1P1P0, P0P0P1);
        // Test 25 to Test 28 from the centre through each corner.
        //instance = new V3D_Tetrahedron(P0P0P0, N1N1P0, P1N1P0, N1P1P0, P0P0P1);
        // Test 25
        l = new V3D_LineSegment(pP0P0P0, pN2N2P0, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pN1N1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        System.out.println(result.toString());
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 26
        l = new V3D_LineSegment(pP0P0P0, pP2N2P0, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP1N1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        System.out.println(result.toString());
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 27
        l = new V3D_LineSegment(pP0P0P0, pN2P2P0, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pN1P1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 28
        l = new V3D_LineSegment(pP0P0P0, pP0P0P2, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP0P0P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
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
        instance = new V3D_Tetrahedron(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        expResult = new V3D_Triangle(pP0P0P0, pP0P0P2, pP0N2P0, oom, rm);
        result = instance.getIntersection(p, oom, rm);
        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
//        // Test 2
//        p = new V3D_Plane(V3D_Plane.X0);
//        p.translate(P1P0P0, oom, rm);
//        instance = new V3D_Tetrahedron(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
//        expResult = new V3D_Triangle(P0P0P0, P1N2P0, P1N1P0, P1N1P1);
//        result = instance.getIntersection(p, oom, rm);
//        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
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
        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
//        // Test 2
//        t = new V3D_Triangle(pP1N2P0, pP1P2P0, pP1P0P2, oom, rm);
//        instance = new V3D_Tetrahedron(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, oom, rm);
//        expResult = new V3D_Triangle(pP1N2P0, pP1N1P0, pP1N1P1, oom, rm);
//        result = instance.getIntersection(t, oom, rm);
//        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
//        // Test 3
//        t = new V3D_Triangle(pP1N1P0, pP1P1P0, pP1P0P1, oom, rm);
//        instance = new V3D_Tetrahedron(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, oom, rm);
//        expResult = new V3D_Triangle(pP1N1P0, pP1P1P0, pP1P0P1, oom, rm);
//        result = instance.getIntersection(t, oom, rm);
//        System.out.println("expResult " + expResult);
//        System.out.println("result " + result);
//        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
        // Test 4
        t = new V3D_Triangle(pN1N1P0, pP1N1P0, pN1P1P0, oom, rm);
        instance = new V3D_Tetrahedron(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, oom, rm);
        expResult = new V3D_Triangle(pN1N1P0, pP1N1P0, pN1P1P0, oom, rm);
        result = instance.getIntersection(t, oom, rm);
        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Triangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_Triangle() {
        System.out.println("getDistanceSquared");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron t;
        V3D_Triangle tr;
        BigRational expResult;
        BigRational result;
        // Test 1
        t = new V3D_Tetrahedron(P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        tr = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        expResult = BigRational.ZERO;
        result = t.getDistanceSquared(tr, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        tr = new V3D_Triangle(pN1P1P0, pN1P0P0, pN1P0P1, oom, rm);
        expResult = BigRational.ONE;
        result = t.getDistanceSquared(tr, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance_V3D_Rectangle() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron t = new V3D_Tetrahedron(pP0P0P0, pP0P1P0, pP1P1P0, pP0P0P1, oom, rm);
        V3D_Rectangle r = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        BigDecimal expResult = BigDecimal.ZERO;
        BigDecimal result = t.getDistance(r, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_Rectangle() {
        System.out.println("getDistanceSquared");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron t = new V3D_Tetrahedron(pP0P0P0, pP0P1P0, pP1P1P0, pP0P0P1, oom, rm);
        V3D_Rectangle r = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        BigRational expResult = BigRational.ZERO;
        BigRational result = t.getDistanceSquared(r, oom, rm);
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
        BigRational expResult;
        BigRational result;
        // Test 1
        instance = new V3D_Tetrahedron(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        p = pP0P0P0;
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = new V3D_Tetrahedron(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        p = pN2N2N2;
        expResult = BigRational.valueOf(4);
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getIntersection method, of class V3D_Tetrahedron.
     */
    @Test
    public void testGetIntersection_V3D_Ray() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Tetrahedron t;
        V3D_Ray r;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        t = new V3D_Tetrahedron(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        r = new V3D_Ray(pN1P0P0, pP0P0P0, oom, rm);
        expResult = new V3D_LineSegment(pN1P0P0, pP0P0P0, oom, rm);
        result = t.getIntersection(r, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 2
        t = new V3D_Tetrahedron(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        r = new V3D_Ray(pN1P1P0, pP0P1P0, oom, rm);
        expResult = pN1P1P0;
        result = t.getIntersection(r, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 3
        t = new V3D_Tetrahedron(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        r = new V3D_Ray(pN1P0P1, pP0P0P1, oom, rm);
        expResult = new V3D_LineSegment(pN1P0P1, pP0P0P1, oom, rm);
        result = t.getIntersection(r, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 4
        t = new V3D_Tetrahedron(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        V3D_Point pNHP0P1 = new V3D_Point(BigRational.valueOf(-1, 2), P0, P1);
        r = new V3D_Ray(pNHP0P1, pP0P0P1, oom, rm);
        expResult = new V3D_LineSegment(pNHP0P1, pP0P0P1, oom, rm);
        result = t.getIntersection(r, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
    }

}
