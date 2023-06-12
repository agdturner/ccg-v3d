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
package uk.ac.leeds.ccg.v3d.geometry.d.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_Double;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_EnvelopeDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_GeometryDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineSegmentDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_PlaneDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_PointDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_RayDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_RectangleDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_TetrahedronDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_TriangleDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_VectorDouble;

/**
 *
 * @author Andy Turner
 */
public class V3D_TetrahedronDoubleTest extends V3D_DoubleTest {

    public V3D_TetrahedronDoubleTest() {
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
     * Test of toString method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        V3D_TetrahedronDouble instance = new V3D_TetrahedronDouble(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        String expResult = """
                           V3D_TetrahedronDouble
                           (
                            p= V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0),
                            q= V3D_VectorDouble(dx=1.0, dy=0.0, dz=0.0),
                            r= V3D_VectorDouble(dx=0.0, dy=1.0, dz=0.0),
                            s= V3D_VectorDouble(dx=0.0, dy=1.0, dz=1.0)
                           )""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of toString method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V3D_TetrahedronDouble instance = new V3D_TetrahedronDouble(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        String expResult = """
                           V3D_TetrahedronDouble
                           (
                            offset=V3D_VectorDouble
                            (
                             dx=0.0,
                             dy=0.0,
                             dz=0.0
                            ) ,
                            p=V3D_VectorDouble
                            (
                             dx=0.0,
                             dy=0.0,
                             dz=0.0
                            )
                            ,
                            q=V3D_VectorDouble
                            (
                             dx=1.0,
                             dy=0.0,
                             dz=0.0
                            )
                            ,
                            r=V3D_VectorDouble
                            (
                             dx=0.0,
                             dy=1.0,
                             dz=0.0
                            )
                            ,
                            s=V3D_VectorDouble
                            (
                             dx=0.0,
                             dy=1.0,
                             dz=1.0
                            )
                           )""";
        String result = instance.toString(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

//    /**
//     * Test of toStringFields method, of class V3D_TetrahedronDouble.
//     */
//    @Test
//    public void testToStringFields() {
//        System.out.println("toStringFields");
//        String pad = "";
//        V3D_TetrahedronDouble instance = new V3D_TetrahedronDouble(pP0P0P0, pP1P0P0,
//                pP0P1P0, pP0P1P1);
//        String expResult = """
//                           offset=V3D_VectorDouble
//                           (
//                            dx=0.0,
//                            dy=0.0,
//                            dz=0.0
//                           ),
//                           p=V3D_VectorDouble
//                           (
//                            dx=0.0,
//                            dy=0.0,
//                            dz=0.0
//                           )
//                           ,
//                           q=V3D_VectorDouble
//                           (
//                            dx=1.0,
//                            dy=0.0,
//                            dz=0.0
//                           )
//                           ,
//                           r=V3D_VectorDouble
//                           (
//                            dx=0.0,
//                            dy=1.0,
//                            dz=0.0
//                           )
//                           ,
//                           s=V3D_VectorDouble
//                           (
//                            dx=0.0,
//                            dy=1.0,
//                            dz=1.0
//                           )""";
//        String result = instance.toStringFields(pad);
//        //System.out.println(result);
//        assertEquals(expResult, result);
//    }

    /**
     * Test of getEnvelope method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        V3D_TetrahedronDouble instance = new V3D_TetrahedronDouble(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_EnvelopeDouble expResult = new V3D_EnvelopeDouble(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_EnvelopeDouble result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getP method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetP() {
        System.out.println("getP");
        V3D_TetrahedronDouble instance = new V3D_TetrahedronDouble(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_PointDouble expResult = pP0P0P0;
        V3D_PointDouble result = instance.getP();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getQ method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetQ() {
        System.out.println("getQ");
        V3D_TetrahedronDouble instance = new V3D_TetrahedronDouble(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_PointDouble expResult = pP1P0P0;
        V3D_PointDouble result = instance.getQ();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getR method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetR() {
        System.out.println("getR");
        V3D_TetrahedronDouble instance = new V3D_TetrahedronDouble(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_PointDouble expResult = pP0P1P0;
        V3D_PointDouble result = instance.getR();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getS method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetS() {
        System.out.println("getS");
        V3D_TetrahedronDouble instance = new V3D_TetrahedronDouble(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_PointDouble expResult = pP0P1P1;
        V3D_PointDouble result = instance.getS();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getPqr method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetPqr() {
        System.out.println("getPqr");
        double epsilon = 1d / 10000000d;
        V3D_TetrahedronDouble instance = new V3D_TetrahedronDouble(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_TriangleDouble expResult = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        V3D_TriangleDouble result = instance.getPqr();
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of getQsr method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetQsr() {
        System.out.println("getQsr");
        double epsilon = 1d / 10000000d;
        V3D_TetrahedronDouble instance = new V3D_TetrahedronDouble(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_TriangleDouble expResult = new V3D_TriangleDouble(pP1P0P0, pP0P1P0, pP0P1P1);
        V3D_TriangleDouble result = instance.getQsr();
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of getSpr method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetSpr() {
        System.out.println("getSpr");
        double epsilon = 1d / 10000000d;
        V3D_TetrahedronDouble instance = new V3D_TetrahedronDouble(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_TriangleDouble expResult = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP0P1P1);
        V3D_TriangleDouble result = instance.getSpr();
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of getPsq method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetPsq() {
        System.out.println("getPsq");
        double epsilon = 1d / 10000000d;
        V3D_TetrahedronDouble instance = new V3D_TetrahedronDouble(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_TriangleDouble expResult = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P1);
        V3D_TriangleDouble result = instance.getPsq();
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of getArea method, of class V3D_TetrahedronDouble.
     * https://keisan.casio.com/exec/system/1329962711
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        double epsilon = 1d / 10000000d;
        V3D_TetrahedronDouble instance = new V3D_TetrahedronDouble(pP0P0P0,
                pP1P0P0, pP0P1P0, pP0P0P1);
        double expResult = (Math.sqrt(3d / 2d) * Math.sqrt(2d) / 2d) + 3d / 2d;
        double result = instance.getArea();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getVolume method, of class V3D_TetrahedronDouble.
     * https://keisan.casio.com/exec/system/1329962711
     */
    @Test
    public void testGetVolume() {
        System.out.println("getVolume");
        double epsilon = 1d / 10000000d;
        V3D_TetrahedronDouble instance = new V3D_TetrahedronDouble(pP0P0P0,
                pP1P0P0, pP0P1P0, pP0P0P1);
        double expResult = 1d / 6d;
        double result = instance.getVolume(epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getCentroid method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetCentroid() {
        System.out.println("getCentroid");
        V3D_TetrahedronDouble instance = new V3D_TetrahedronDouble(
                pN2N2N2, pP2N2N2, pP0P2N2,
                new V3D_PointDouble(0d, 0d, 4d));
        V3D_PointDouble expResult = new V3D_PointDouble(P0P0P0,
                new V3D_VectorDouble(0d, -1d / 2d, -1d / 2d));
        V3D_PointDouble result = instance.getCentroid();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testIsIntersectedBy_V3D_PointDouble_int() {
        System.out.println("isIntersectedBy");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble pt;
        V3D_TetrahedronDouble instance;
        // Test 1
        pt = pP0P0P0;
        //instance = new V3D_TetrahedronDouble(pP0P0P0, pP1P0P0, pP0P1P0, pP0P0P1);
        instance = new V3D_TetrahedronDouble(P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 2
        pt = new V3D_PointDouble(0.5, 0.5, 0.5);
        instance = new V3D_TetrahedronDouble(P0P0P0, P0P0P0, P2P0P0, P0P2P0, P0P0P2);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 3
        pt = new V3D_PointDouble(-0.5, -0.5, -0.5);
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        // Test 4
        pt = new V3D_PointDouble(-0.5, 0, 0);
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        // Test 5
        instance = new V3D_TetrahedronDouble(
                new V3D_PointDouble(0, 0, -75),
                new V3D_PointDouble(-50, -37.5, 10),
                new V3D_PointDouble(50, 37.5, 10),
                new V3D_PointDouble(-50, 37.5, 10));
        //pt = new V3D_PointDouble(-24, -19, 0);
        //pt = new V3D_PointDouble(0, 0, -74);
        //pt = new V3D_PointDouble(1, 1, 0);
        pt = new V3D_PointDouble(-11, 11, 0);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l;
        V3D_TetrahedronDouble instance;
        V3D_GeometryDouble expResult;
        V3D_GeometryDouble result;
        // Test 1
        l = new V3D_LineDouble(pP0P0P0, pP1P0P0);
        instance = new V3D_TetrahedronDouble(P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 2
        instance = new V3D_TetrahedronDouble(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        l = new V3D_LineDouble(pN2N2P0, new V3D_TriangleDouble(P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid());
        expResult = new V3D_LineSegmentDouble(pN2N2P0, new V3D_TriangleDouble(P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid());
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetIntersection_V3D_LineSegmentDouble_int() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_LineSegmentDouble l;
        V3D_TetrahedronDouble instance;
        V3D_GeometryDouble expResult;
        V3D_GeometryDouble result;
        // Test 1
        l = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        instance = new V3D_TetrahedronDouble(P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 2
        instance = new V3D_TetrahedronDouble(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        l = new V3D_LineSegmentDouble(pN2N2P0, new V3D_TriangleDouble(P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid());
        expResult = new V3D_LineSegmentDouble(pN2N2P0, new V3D_TriangleDouble(P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid());
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
//        // Test 3
//        instance = new V3D_TetrahedronDouble(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
//        l = new V3D_LineSegmentDouble(P0P0P0, N2N2P0, new V3D_TriangleDouble(P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid().rel.multiply(P2).add(P2P2P0));
//        expResult = new V3D_LineSegmentDouble(pN2N2P0, new V3D_TriangleDouble(P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid());
//        result = instance.getIntersection(l);
//        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreOrientation((V3D_LineSegmentDouble) result));
    }

    /**
     * Test of getIntersection method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetIntersection_V3D_PlaneDouble_int() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_PlaneDouble p;
        V3D_TetrahedronDouble instance;
        V3D_GeometryDouble expResult;
        V3D_GeometryDouble result;
        // Test 1
        p = V3D_PlaneDouble.X0;
        instance = new V3D_TetrahedronDouble(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        expResult = new V3D_TriangleDouble(pP0P0P0, pP0P0P2, pP0N2P0);
        result = instance.getIntersection(p, epsilon);
        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result, epsilon));
//        // Test 2
//        p = new V3D_PlaneDouble(V3D_PlaneDouble.X0);
//        p.translate(P1P0P0);
//        instance = new V3D_TetrahedronDouble(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
//        expResult = new V3D_TriangleDouble(P0P0P0, P1N2P0, P1N1P0, P1N1P1);
//        result = instance.getIntersection(p);
//        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetIntersection_V3D_TriangleDouble_double() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_TriangleDouble t;
        V3D_TetrahedronDouble instance;
        V3D_GeometryDouble expResult;
        V3D_GeometryDouble result;
        // Test 1
        t = new V3D_TriangleDouble(pP0N2P0, pP0P2P0, pP0P0P2);
        instance = new V3D_TetrahedronDouble(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2);
        expResult = new V3D_TriangleDouble(pP0P0P0, pP0P0P2, pP0N2P0);
        result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result, epsilon));
//        // Test 2
//        t = new V3D_TriangleDouble(pP1N2P0, pP1P2P0, pP1P0P2);
//        instance = new V3D_TetrahedronDouble(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2);
//        expResult = new V3D_TriangleDouble(pP1N2P0, pP1N1P0, pP1N1P1);
//        result = instance.getIntersection(t);
//        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result, epsilon));
//        // Test 3
//        t = new V3D_TriangleDouble(pP1N1P0, pP1P1P0, pP1P0P1);
//        instance = new V3D_TetrahedronDouble(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2);
//        expResult = new V3D_TriangleDouble(pP1N1P0, pP1P1P0, pP1P0P1);
//        result = instance.getIntersection(t);
//        System.out.println("expResult " + expResult);
//        System.out.println("result " + result);
//        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result, epsilon));
        // Test 4
        t = new V3D_TriangleDouble(pN1N1P0, pP1N1P0, pN1P1P0);
        instance = new V3D_TetrahedronDouble(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2);
        expResult = new V3D_TriangleDouble(pN1N1P0, pP1N1P0, pN1P1P0);
        result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_TriangleDouble() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_TetrahedronDouble t;
        V3D_TriangleDouble tr;
        double expResult;
        double result;
        // Test 1
        t = new V3D_TetrahedronDouble(P0P0P0, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        tr = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 0d;
        result = t.getDistanceSquared(tr, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        tr = new V3D_TriangleDouble(pN1P1P0, pN1P0P0, pN1P0P1);
        expResult = 1d;
        result = t.getDistanceSquared(tr, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetDistance_V3D_RectangleDouble() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V3D_TetrahedronDouble t = new V3D_TetrahedronDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP0P0P1);
        V3D_RectangleDouble r = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 0d;
        double result = t.getDistance(r, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_RectangleDouble() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_TetrahedronDouble t = new V3D_TetrahedronDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP0P0P1);
        V3D_RectangleDouble r = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 0d;
        double result = t.getDistance(r, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_TetrahedronDouble covered by
     * {@link #testGetDistanceSquared_V3D_PointDouble_int()}.
     */
    @Test
    public void testGetDistance_V3D_PointDouble_int() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_PointDouble_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble p;
        V3D_TetrahedronDouble instance;
        double expResult;
        double result;
        // Test 1
        instance = new V3D_TetrahedronDouble(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        p = pP0P0P0;
        expResult = 0d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        instance = new V3D_TetrahedronDouble(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        p = pN2N2N2;
        expResult = 4d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_TetrahedronDouble.
     */
    @Test
    public void testGetIntersection_V3D_RayDouble() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_TetrahedronDouble t;
        V3D_RayDouble r;
        V3D_GeometryDouble expResult;
        V3D_GeometryDouble result;
        // Test 1
        t = new V3D_TetrahedronDouble(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        r = new V3D_RayDouble(pN1P0P0, pP0P0P0);
        expResult = new V3D_LineSegmentDouble(pN1P0P0, pP0P0P0);
        result = t.getIntersection(r, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 2
        t = new V3D_TetrahedronDouble(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        r = new V3D_RayDouble(pN1P1P0, pP0P1P0);
        expResult = pN1P1P0;
        result = t.getIntersection(r, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result, epsilon));
        // Test 3
        t = new V3D_TetrahedronDouble(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        r = new V3D_RayDouble(pN1P0P1, pP0P0P1);
        expResult = new V3D_LineSegmentDouble(pN1P0P1, pP0P0P1);
        result = t.getIntersection(r, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 4
        t = new V3D_TetrahedronDouble(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
        V3D_PointDouble pNHP0P1 = new V3D_PointDouble(-1d / 2d, 0d, 1d);
        r = new V3D_RayDouble(pNHP0P1, pP0P0P1);
        expResult = new V3D_LineSegmentDouble(pNHP0P1, pP0P0P1);
        result = t.getIntersection(r, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
    }

}
