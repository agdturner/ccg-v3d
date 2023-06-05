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
package uk.ac.leeds.ccg.v3d.geometry.d.test;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_EnvelopeDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_PointDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_RayDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_VectorDouble;
//import org.junit.jupiter.api.Disabled;

/**
 * Test of V3D_PointDouble class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_PointDoubleTest extends V3D_DoubleTest {

    public V3D_PointDoubleTest() {
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
     * Test of equals method, of class V3D_PointDouble.
     */
    @Test
    public void testEquals_V3D_PointDouble() {
        System.out.println("equals");
        V3D_PointDouble instance = pP0P0P0;
        double x = 0d;
        double y = 0d;
        double z = 0d;
        V3D_PointDouble p = new V3D_PointDouble(x, y, z);
        assertTrue(instance.equals(p));
        // Test 2
        x = 1d;
        y = 10d;
        z = 0d;
        instance = new V3D_PointDouble(x, y, z);
        p = new V3D_PointDouble(x, y, z);
        assertTrue(instance.equals(p));
        // Test 3
        p = new V3D_PointDouble(0d, 10d, 1d);
        assertFalse(instance.equals(p));
    }

    /**
     * Test of isBetween method, of class V3D_Point.
     */
    @Test
    public void testIsBetween() {
        System.out.println("isBetween");
        // Test 1
        V3D_PointDouble p = pP0P0P0;
        V3D_PointDouble a = pN1P0P0;
        V3D_PointDouble b = pP1P0P0;
        double epsilon = 1 / 100000000d;
        assertTrue(p.isBetween(a, b, epsilon));
        // Test 2
        p = pP0P0P0;
        a = pP0N1P0;
        b = pP0P1P0;
        assertTrue(p.isBetween(a, b, epsilon));
        // Test 3
        p = pP0P0P0;
        a = pP0P0N1;
        b = pP0P0P1;
        assertTrue(p.isBetween(a, b, epsilon));
        // Test 4
        p = pP0P0P0;
        a = pN1N1N1;
        b = pP1P1P1;
        assertTrue(p.isBetween(a, b, epsilon));
        // Test 5
        p = pP0P0P0;
        a = pN1N1N1;
        b = pP1P1P1;
        assertTrue(p.isBetween(a, b, epsilon));
        // Test 6
        p = pP0P0P0;
        a = pN1N1P0;
        b = pP1P1P0;
        assertTrue(p.isBetween(a, b, epsilon));
        // Test 7
        p = pP0P0P0;
        a = pN1P0N1;
        b = pP1P0P1;
        assertTrue(p.isBetween(a, b, epsilon));
        // Test 8
        p = pP0P0P0;
        a = pP0N1N1;
        b = pP0P1P1;
        assertTrue(p.isBetween(a, b, epsilon));
        // Test 9
        p = pP0P0P0;
        a = pN1P0N1;
        b = pP1P0P1;
        assertTrue(p.isBetween(a, b, epsilon));
        // Test 10
        p = pP0P0P0;
        a = pP0N1N1;
        b = pP0P1P1;
        assertTrue(p.isBetween(a, b, epsilon));
        // Test 11
        p = pP0P0P1;
        a = pP0N1N1;
        b = pP0P1P1;
        assertTrue(p.isBetween(a, b, epsilon));
        // Test 12
        p = pP0P0P2;
        a = pP0N1N1;
        b = pP0P1P1;
        assertTrue(p.isBetween(a, b, epsilon));
        // Test 13
        p = pP0P1P2;
        a = pP0N1N1;
        b = pP0P1P1;
        assertFalse(p.isBetween(a, b, epsilon));
        // Test 14
        p = pP1P1P1;
        a = pP0N1N1;
        b = pP0P1P1;
        assertTrue(p.isBetween(a, b, epsilon));
    }
    
    /**
     * Test of getEnvelope method, of class V3D_PointDouble.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        V3D_EnvelopeDouble expResult = new V3D_EnvelopeDouble(pP1P2N2);
        V3D_EnvelopeDouble result = pP1P2N2.getEnvelope();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDistance method, of class V3D_PointDouble.
     */
    @Test
    public void testGetDistance_V3D_PointDouble() {
        System.out.println("getDistance");
        V3D_PointDouble p = V3D_PointDouble.ORIGIN;
        V3D_PointDouble instance = V3D_PointDouble.ORIGIN;
        double expResult = 0d;
        double result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 2
        instance = pP1P0P0;
        expResult = 1d;
        result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 3
        instance = pP1P1P0;
        expResult = Math.sqrt(2d);
        result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 4
        instance = new V3D_PointDouble(3d, 4d, 0d);
        expResult = 5d;
        result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 5
        instance = new V3D_PointDouble(0d, 3d, 4d);
        result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 6
        instance = new V3D_PointDouble(3d, 0d, 4d);
        result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 7
        instance = new V3D_PointDouble(-3d, -4d, 0d);
        result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 8
        instance = new V3D_PointDouble(0d, -3d, -4d);
        result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 9
        instance = new V3D_PointDouble(-3d, 0d, -4d);
        result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 10
        instance = new V3D_PointDouble(-3d, 4d, 0d);
        result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 11
        instance = new V3D_PointDouble(0d, 3d, -4d);
        result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 12
        instance = new V3D_PointDouble(3d, 0d, -4d);
        result = instance.getDistance(p);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_PointDouble.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        V3D_PointDouble instance = pP0P0P0;
        double expResult = 0d;
        double result = instance.getDistance(instance);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_PointDouble(3d, 4d, 0d);
        expResult = 5d;
        result = instance.getDistance(pP0P0P0);
        assertTrue(expResult == result);
    }

    /**
     * Test of getDistance method, of class V3D_PointDouble.
     */
    @Test
    public void testGetDistance_V3D_PointDouble_int() {
        System.out.println("getDistance");
        V3D_PointDouble p = pP0P0P0;
        V3D_PointDouble instance = pP1P0P0;
        double expResult = 1d;
        double result = instance.getDistance(p);
        assertTrue(expResult == result);
        // Test 2
        instance = new V3D_PointDouble(3d, 4d, 0d);
        expResult = 5d;
        result = instance.getDistance(p);
        assertTrue(expResult == result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_PointDouble.
     */
    @Test
    public void testGetDistanceSquared() {
        System.out.println("getDistanceSquared");
        V3D_PointDouble instance = pP0P0P0;
        double expResult = 0d;
        double result = instance.getDistanceSquared(instance);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_PointDouble(3d, 4d, 0d);
        expResult = 25d;
        result = instance.getDistanceSquared(pP0P0P0);
        assertTrue(expResult == result);
    }

    /**
     * Test of toString method, of class V3D_PointDouble.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_PointDouble instance = pP0P1P2;
        String expResult = "V3D_PointDouble("
                + "offset=V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0), "
                + "rel=V3D_VectorDouble(dx=0.0, dy=1.0, dz=2.0))";
        String result = instance.toString();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class V3D_PointDouble.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V3D_PointDouble instance = pP0P1P2;
        String expResult = """
                           V3D_PointDouble
                           (
                            offset=V3D_VectorDouble
                            (
                             dx=0.0,
                             dy=0.0,
                             dz=0.0
                            )
                            ,
                            rel=V3D_VectorDouble
                            (
                             dx=0.0,
                             dy=1.0,
                             dz=2.0
                            )
                           )""";
        String result = instance.toString(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

//    /**
//     * Test of toStringFields method, of class V3D_PointDouble.
//     */
//    @Test
//    public void testToStringFields() {
//        System.out.println("toStringFields");
//        String pad = "";
//        V3D_PointDouble instance = pP0P1P2;
//        String expResult = """
//                           offset=V3D_VectorDouble
//                           (
//                            dx=0.0,
//                            dy=0.0,
//                            dz=0.0
//                           )
//                           ,
//                           rel=V3D_VectorDouble
//                           (
//                            dx=0.0,
//                            dy=1.0,
//                            dz=2.0
//                           )""";
//        String result = instance.toStringFields(pad);
//        System.out.println(result);
//        assertEquals(expResult, result);
//    }

    /**
     * Test of getVector method, of class V3D_PointDouble.
     */
    @Test
    public void testGetVector() {
        System.out.println("getVector");
        V3D_PointDouble instance = pP0P1P2;
        V3D_VectorDouble expResult = new V3D_VectorDouble(0, 1, 2);
        V3D_VectorDouble result = instance.getVector();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getX method, of class V3D_PointDouble.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        V3D_PointDouble instance = pP0P1P2;
        double expResult = 0d;
        double result = instance.getX();
        assertTrue(expResult == result);
    }

    /**
     * Test of getY method, of class V3D_PointDouble.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        V3D_PointDouble instance = pP0P1P2;
        double expResult = 1d;
        double result = instance.getY();
        assertTrue(expResult == result);
    }

    /**
     * Test of getZ method, of class V3D_PointDouble.
     */
    @Test
    public void testGetZ() {
        System.out.println("getZ");
        V3D_PointDouble instance = pP0P1P2;
        double expResult = 2d;
        double result = instance.getZ();
        assertTrue(expResult == result);
    }

    /**
     * Test of isOrigin method, of class V3D_PointDouble.
     */
    @Test
    public void testIsOrigin() {
        System.out.println("isOrigin");
        V3D_PointDouble instance = pP0P1P2;
        assertFalse(instance.isOrigin());
        instance = pP0P0P0;
        assertTrue(instance.isOrigin());
    }

    /**
     * Test of getDistanceSquared method, of class V3D_PointDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_PointDouble() {
        System.out.println("getDistanceSquared");
        V3D_PointDouble p = pP1P0P0;
        V3D_PointDouble instance = pP1P0P0;
        double expResult = 0d;
        double result = instance.getDistanceSquared(p);
        assertTrue(expResult == result);
        // Test 2
        p = pP1P0P0;
        instance = pP2P0P0;
        expResult = 1d;
        result = instance.getDistanceSquared(p);
        assertTrue(expResult == result);
        // Test 3
        p = pP1P0P0;
        instance = pP0P1P0;
        expResult = 2d;
        result = instance.getDistanceSquared(p);
        assertTrue(expResult == result);
        // Test 4
        p = pP1P0P0;
        instance = pP1P1P0;
        expResult = 1d;
        result = instance.getDistanceSquared(p);
        assertTrue(expResult == result);
        // Test 5
        p = pP0P0P0;
        instance = pP1P1P1;
        expResult = 3d;
        result = instance.getDistanceSquared(p);
        assertTrue(expResult == result);
    }

    /**
     * Test of getLocation method, of class V3D_PointDouble.
     */
    @Test
    public void testGetLocation() {
        System.out.println("getLocation");
        V3D_PointDouble instance = V3D_PointDouble.ORIGIN;
        assertEquals(0, instance.getLocation());
        // PPP
        instance = pP0P0P0;
        assertEquals(0, instance.getLocation());
        instance = pP0P0P1;
        assertEquals(1, instance.getLocation());
        instance = pP0P1P0;
        assertEquals(1, instance.getLocation());
        instance = pP0P1P1;
        assertEquals(1, instance.getLocation());
        instance = pP1P0P0;
        assertEquals(1, instance.getLocation());
        instance = pP1P0P1;
        assertEquals(1, instance.getLocation());
        instance = pP1P1P0;
        assertEquals(1, instance.getLocation());
        instance = pP1P1P1;
        assertEquals(1, instance.getLocation());
        // PPN
        instance = pP0P0N1;
        assertEquals(2, instance.getLocation());
        // PNP
        instance = pP0N1P0;
        assertEquals(3, instance.getLocation());
        // PNN
        instance = pP0N1N1;
        assertEquals(4, instance.getLocation());
        // NPP
        instance = pN1P0P0;
        assertEquals(5, instance.getLocation());
        // NPN
        instance = pN1P0N1;
        assertEquals(6, instance.getLocation());
        // NNP
        instance = pN1N1P0;
        assertEquals(7, instance.getLocation());
        // NNN
        instance = pN1N1N1;
        assertEquals(8, instance.getLocation());
    }

    /**
     * Test of rotate method, of class V3D_PointDouble.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double Pi = Math.PI;
        double epsilon = 1 / 100000000d;
        V3D_RayDouble axis = new V3D_RayDouble(P0P0P0, P1P1P0);
        V3D_PointDouble instance = new V3D_PointDouble(pP1P0P0);
        double theta = Pi;
        V3D_PointDouble result = instance.rotate(axis, theta, epsilon);
        V3D_PointDouble expResult = pP0P1P0;
        assertTrue(expResult.equals(result, epsilon));
        // Test 2
        instance = new V3D_PointDouble(pP1P0P0);
        theta = Pi;
        result = instance.rotate(axis, theta, epsilon);
        expResult = pP0P1P0;
        assertTrue(expResult.equals(result, epsilon));
        // Test 3
        V3D_VectorDouble offset = new V3D_VectorDouble(2, 0, 0);
        V3D_VectorDouble rel = new V3D_VectorDouble(1, 0, 0);
        instance = new V3D_PointDouble(offset, rel);
        theta = Pi;
        result = instance.rotate(axis, theta, epsilon);
        expResult = new V3D_PointDouble(0, 3, 0);
        assertTrue(expResult.equals(result, epsilon));
        // Test 4
        offset = new V3D_VectorDouble(1, 0, 0);
        rel = new V3D_VectorDouble(2, 0, 0);
        instance = new V3D_PointDouble(offset, rel);
        theta = Pi;
        result = instance.rotate(axis, theta, epsilon);
        expResult = new V3D_PointDouble(0, 3, 0);
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of setOffset method, of class V3D_PointDouble.
     */
    @Test
    public void testSetOffset() {
        System.out.println("setOffset");
        V3D_VectorDouble offset = P0P0P1;
        V3D_PointDouble instance = new V3D_PointDouble(pP0P0P0);
        instance.setOffset(offset);
        assertTrue(instance.equals(pP0P0P0));
    }

    /**
     * Test of setRel method, of class V3D_PointDouble.
     */
    @Test
    public void testSetRel() {
        System.out.println("setRel");
        V3D_VectorDouble rel = P0P0P1;
        V3D_PointDouble instance = new V3D_PointDouble(pP0P0P0);
        instance.setRel(rel);
        assertTrue(instance.equals(pP0P0P0));
    }
    
    /**
     * Test of equals method, of class V3D_Point.
     */
    @Test
    public void testGetUnique() {
        System.out.println("getUnique");
        ArrayList<V3D_PointDouble> pts;
        ArrayList<V3D_PointDouble> expResult;
        ArrayList<V3D_PointDouble> result;
        double epsilon = 1 / 100000000d;
        // Test 1
        pts = new ArrayList<>();
        pts.add(pP0P0P0);
        pts.add(pP0P0P0);
        expResult = new ArrayList<>();
        expResult.add(pP0P0P0);
        result = V3D_PointDouble.getUnique(pts, epsilon);
        testContainsSamePoints(expResult, result, epsilon);
        // Test 2
        pts = new ArrayList<>();
        pts.add(pP0P0P0);
        pts.add(pP0P0P0);
        pts.add(pP0P0P0);
        pts.add(pP1P0P0);
        pts.add(pP0P0P0);
        pts.add(pP0P0P0);
        pts.add(pP0P0P0);
        pts.add(pP1P0P0);
        pts.add(pP0P0P0);
        pts.add(pP1P0P0);
        expResult = new ArrayList<>();
        expResult.add(pP0P0P0);
        expResult.add(pP1P0P0);
        result = V3D_PointDouble.getUnique(pts, epsilon);
        testContainsSamePoints(expResult, result, epsilon);
        // Test 3
        pts = new ArrayList<>();
        pts.add(pP0P1P0);
        pts.add(pP0P0P0);
        pts.add(pP0P0P0);
        pts.add(pP1P0P0);
        pts.add(pP0P0P0);
        pts.add(pP0P0P0);
        pts.add(pP0P0P0);
        pts.add(pP1P0P0);
        pts.add(pP0P0P0);
        pts.add(pP1P0P0);
        expResult = new ArrayList<>();
        expResult.add(pP0P1P0);
        expResult.add(pP0P0P0);
        expResult.add(pP1P0P0);
        result = V3D_PointDouble.getUnique(pts, epsilon);
        testContainsSamePoints(expResult, result, epsilon);
    }
    
    private void testContainsSamePoints(ArrayList<V3D_PointDouble> expResult,
        ArrayList<V3D_PointDouble> result, double epsilon) {
        assertEquals(expResult.size(), result.size());
        boolean t = false;
        for (var x: result) {
            for (var y: expResult) {
                if (x.equals(y, epsilon)) {
                    t = true;
                    break;
                }
            }
            assertTrue(t);
        }
        t = false;
        for (var x: expResult) {
            for (var y: result) {
                if (x.equals(y, epsilon)) {
                    t = true;
                    break;
                }
            }
            assertTrue(t);
        }
        
    }

}
