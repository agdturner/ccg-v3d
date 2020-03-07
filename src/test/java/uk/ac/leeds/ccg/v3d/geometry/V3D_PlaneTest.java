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
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_PlaneTest
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_PlaneTest extends V3D_Test {

    public V3D_PlaneTest() throws Exception {
        super(new V3D_Environment(new Generic_Environment(
                new Generic_Defaults())));
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

//    @Test
//    public void run() {
//        testToString();
//        testIsOnPlane_V3D_Point();
//        ...
//    }
    
    /**
     * Test of toString method, of class V3D_Plane.
     */
    //@Test
    public void testToString() {
        System.out.println("toString");
        V3D_Plane instance = getPlane(P0P0P0, P1P1P1, P1P0P0);
        String expResult = "V3D_Plane(p=V3D_Point(x=0, y=0, z=0), "
                + "q=V3D_Point(x=1, y=1, z=1), r=V3D_Point(x=1, y=0, z=0))";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Basic method to get a plane defined by {@code p}, {@code q} and
     * {@code r}.
     *
     * @param p A point.
     * @param q A point.
     * @param r A point.
     * @return A plane or null if the points {@code p}, {@code q} and {@code r}
     * are collinear or coincident.
     */
    public V3D_Plane getPlane(V3D_Point p, V3D_Point q, V3D_Point r) {
        try {
            return new V3D_Plane(e, p, q, r);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        return null;
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane.
     */
    @Test
    public void testIsOnPlane_V3D_Point() {
        System.out.println("isOnPlane");
        V3D_Point pt = P1P0P0;
        V3D_Plane instance = getPlane(P1P1P0, P1P1P1, P1P0P0);
        boolean expResult = true;
        boolean result = instance.isIntersectedBy(pt);
        assertEquals(expResult, result);
        // Test2
        pt = P1P0P1;
        instance = getPlane(P1P1P0, P1P1P1, P1P0P0);
        expResult = true;
        result = instance.isIntersectedBy(pt);
        assertEquals(expResult, result);
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane.
     */
    @Test
    public void testIsOnPlane_V3D_LineSegment() {
        System.out.println("isOnPlane");
        V3D_Point end = new V3D_Point(e, P0, P0, P2);
        V3D_LineSegment l = new V3D_LineSegment(P1P0P1, end);
        V3D_Plane instance = getPlane(P1P1P0, P1P1P1, P1P0P0);
        boolean expResult = false;
        boolean result = instance.isOnPlane(l);
        assertEquals(expResult, result);
        // Test 2
        end = new V3D_Point(e, P1, P0, P2);
        l = new V3D_LineSegment(P1P0P1, end);
        instance = getPlane(P1P1P0, P1P1P1, P1P0P0);
        expResult = true;
        result = instance.isOnPlane(l);
        assertEquals(expResult, result);
        // Test 3
        end = new V3D_Point(e, P1, P0, P2);
        l = new V3D_LineSegment(P1P0P1, end);
        instance = getPlane(P1P1P0, P1P1P1, P1P0P0);
        expResult = true;
        result = instance.isOnPlane(l);
        assertEquals(expResult, result);
        // Test 4
        end = new V3D_Point(e, P1, P10, P10);
        l = new V3D_LineSegment(P1N1N1, end);
        instance = getPlane(P1P1P0, P1P1P1, P1P0P0);
        expResult = true;
        result = instance.isOnPlane(l);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V3D_Plane.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = getPlane(P0P1P0, P1P1P1, P1P0P0);
        V3D_Plane instance = getPlane(P0P1P0, P1P1P1, P1P0P0);
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 2
        instance = getPlane(P1P1P0, P1P1P1, P1P0P0);
        expResult = false;
        result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 3
        instance = getPlane(P1P1P1, P1P0P0, P0P1P0);
        expResult = true;
        result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 4
        instance = getPlane(P1P0P0, P0P1P0, P1P1P1);
        expResult = true;
        result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 4
        instance = getPlane(P1P0P0, P0P1P0, P1P1P1);
        expResult = true;
        result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 5
        o = getPlane(P0P0P0, P1P0P0, P0P1P0);
        V3D_Point q = new V3D_Point(e, P2, P0, P0);
        V3D_Point r = new V3D_Point(e, P0, P2, P0);
        instance = getPlane(P0P0P0, q, r);
        expResult = true;
        result = instance.equals(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsIntersectedBy_3args() {
        System.out.println("intersects");
        V3D_Plane pl = getPlane(P0P0P0, P1P0P0, N1P0P1);
        int scale = 1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Plane instance = getPlane(P0P0P0, P1P0P0, N1P0P1);
        boolean expResult = true;
        boolean result = instance.isIntersectedBy(pl, scale, rm);
        assertEquals(expResult, result);
        // Test 2
        scale = 1;
        rm = RoundingMode.HALF_UP;
        instance = getPlane(N1N1N1, P0N1N1, new V3D_Point(e,
                BigDecimal.valueOf(-2), N1, P0));
        expResult = false;
        result = instance.isIntersectedBy(pl, scale, rm);
        assertEquals(expResult, result);
        // Test 3
        scale = 0;
        rm = RoundingMode.HALF_UP;
        pl = getPlane(P0P0P0, P1P0P0, N1P0P1);
        instance = getPlane(N1N1N1, P0N1N1, new V3D_Point(e,
                BigDecimal.valueOf(-2), N1, P0));
        System.out.println("pl.getNPerp()=" + pl.getNormalVector());
        System.out.println("instance.getNPerp()=" + instance.getNormalVector());
        expResult = false; 
        result = instance.isIntersectedBy(pl, scale, rm);
        assertEquals(expResult, result);
        // Test 4
        scale = 2;
        rm = RoundingMode.HALF_UP;
        expResult = false;
        result = instance.isIntersectedBy(pl, scale, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("intersects");
        V3D_Point pt = P0P0P0;
        V3D_Plane instance = getPlane(P0P0P0, P1P0P0, N1P0P1);
        boolean expResult = true;
        boolean result = instance.isIntersectedBy(pt);
        assertEquals(expResult, result);
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane.
     */
    @Test
    public void testIsOnPlane() {
        System.out.println("isOnPlane");
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P1P0P0);
        V3D_Plane instance = getPlane(P0P0P0, P1P0P0, N1P0P1);
        boolean expResult = true;
        boolean result = instance.isOnPlane(l);
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class V3D_Plane.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertTrue(true); // Not really a test - method does not need testing.
    }

    /**
     * Test of getNormalVector method, of class V3D_Plane.
     */
    @Test
    public void testGetNormalVector() {
        System.out.println("getNormalVector");
        V3D_Plane instance = getPlane(P0P0P0, P1P0P0, P0P1P0); // Z = 0
        V3D_Vector expResult = new V3D_Vector(P0P0P1);
        V3D_Vector result = instance.getNormalVector();
        assertEquals(expResult, result);
        // Test 2
        instance = getPlane(P0P0N1, P1P0N1, P0P1N1); // Z = -1
        expResult = new V3D_Vector(P0P0P1);
        result = instance.getNormalVector();
        assertEquals(expResult, result);
        // Test 3
        instance = getPlane(P0P0P1, P1P0P1, P0P1P1); // Z = 1
        expResult = new V3D_Vector(P0P0P1);
        result = instance.getNormalVector();
        assertEquals(expResult, result);
        // Test 4
        instance = getPlane(P1P0P1, P0P1P1, P0P0P1); // Z = 1
        expResult = new V3D_Vector(P0P0P1);
        result = instance.getNormalVector();
        assertEquals(expResult, result);
        // Test 5
        instance = getPlane(P0P1P1, P0P0P1, P1P0P1); // Z = 1
        expResult = new V3D_Vector(P0P0P1);
        result = instance.getNormalVector();
        assertEquals(expResult, result);
        // Test 6
        instance = getPlane(P0P0P0, P0P1P0, P0P0N1); // Y = 0
        expResult = new V3D_Vector(N1P0P0);
        result = instance.getNormalVector();
        assertEquals(expResult, result);
        // Test 7
        instance = getPlane(P0P0P0, P1P0P0, P0P0N1); // X = 0
        expResult = new V3D_Vector(P0P1P0);
        result = instance.getNormalVector();
        assertEquals(expResult, result);
        // Test 8
        instance = getPlane(P0P0P0, P1P0P0, N1P0P1); // X = 0
        expResult = new V3D_Vector(P0N1P0); // This is the other normal than for
        result = instance.getNormalVector();// test 7 due to the right hand rule  
        assertEquals(expResult, result);    // and the orientation.
        instance = getPlane(P0P1P0, P1P1P1, P1P0P0);
        expResult = new V3D_Vector(P1P1N1);
        result = instance.getNormalVector();
        assertEquals(expResult, result);
        // Test 2
        instance = getPlane(P0P0P0, P0P1P1, P0N1P0);
        expResult = new V3D_Vector(P1P0P0);
        result = instance.getNormalVector();
        assertEquals(expResult, result);
        // Test 3
        instance = getPlane(P0P0P0, P1P1P1, P0N1N1);
        expResult = new V3D_Vector(P0P1N1);
        result = instance.getNormalVector();
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsIntersectedBy_3args_1() {
        System.out.println("intersects");
        V3D_Plane pl = getPlane(P1P0P0, P0P1P0, N1P0P0); // Z = 0 plane
        int scale = 1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Plane instance = getPlane(P1P0P1, P0P0P1, N1P0N1); // Y = 0 plane
        boolean expResult = true;
        boolean result = instance.isIntersectedBy(pl, scale, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsIntersectedBy_3args_2() {
        System.out.println("intersects");
        V3D_Line l = new V3D_Line(P0P0P0, P0P1P0); // X axis
        int scale = 1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Plane instance = getPlane(P1P0P1, P0P0P1, N1P0N1); // Y = 0 plane
        boolean expResult = true;
        boolean result = instance.isIntersectedBy(l, scale, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of isParallel method, of class V3D_Plane.
     */
    @Test
    public void testIsParallel_3args_1() {
        System.out.println("isParallel");
        V3D_Plane p = getPlane(P1P1P0, P1N1P0, N1P1P0);
        int scale = 1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Plane instance = getPlane(P1P1P1, P1N1P1, N1P1P1);
        boolean expResult = true;
        boolean result = instance.isParallel(p, scale, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of isParallel method, of class V3D_Plane.
     */
    @Test
    public void testIsParallel_3args_2() {
        System.out.println("isParallel");
        V3D_Line l = new V3D_Line(P1P1P0, P1N1P0);
        int scale = 1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Plane instance = getPlane(P1P1P1, P1N1P1, N1P1P1);
        boolean expResult = true;
        boolean result = instance.isParallel(l, scale, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Plane.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        V3D_Plane pl = getPlane(P0P0P0, e.yAxis.q, e.zAxis.q); // x = 0
        int scale = 1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Plane instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
//        // Test 1
//        instance = getPlane(P0P0P0, e.yAxis.q.multiply(P2), e.zAxis.q); // x = 0
//        expResult = getPlane(P0P0P0, e.yAxis.q.multiply(P3), e.zAxis.q);
//        result = instance.getIntersection(pl, scale, rm);
//        assertEquals(expResult, result);
//        // Test 2
//         instance = getPlane(e.xAxis.q, P0P0P0, e.zAxis.q); // y = 0
//         expResult = e.zAxis;
//         result = instance.getIntersection(pl, scale, rm);
//        assertEquals(expResult, result);
//        // Test 3
//         instance = getPlane(e.xAxis.q, e.yAxis.q, P0P0P0); // z = 0
//         expResult = e.yAxis;
//         result = instance.getIntersection(pl, scale, rm);
//        assertEquals(expResult, result);
//        // Test 4
//         instance = getPlane(N1P1N1, P0P1P0, P1P1N1); // y = 1
//         expResult = new V3D_Line(P0P1P0, P0P1P1);
//         result = instance.getIntersection(pl, scale, rm);
//        assertEquals(expResult, result);
//        // Test 5
//         instance = getPlane(e.xAxis.q, e.yAxis.q, P0P0P0); // z = 1
//         expResult = new V3D_Line(P0P0P1, P0P0N1);
//         result = instance.getIntersection(pl, scale, rm);
//        assertEquals(expResult, result);
        
        // Test x
        V3D_Point P2P3P5 = new V3D_Point(e, P2, P3, P5);
        V3D_Point P7P11P13 = new V3D_Point(e, P7, BigDecimal.valueOf(11), BigDecimal.valueOf(13));
        pl = getPlane(P1P1P1, P2P3P5, P7P11P13);
        V3D_Point N7N11N13 = new V3D_Point(e, N7, BigDecimal.valueOf(-11), BigDecimal.valueOf(-13));
        V3D_Point N2N3N5 = new V3D_Point(e, N2, N3, N5);        
        instance = getPlane(N2N3N5, P1P1P1, N7N11N13);
         expResult = e.yAxis;
         result = instance.getIntersection(pl, scale, rm);
        assertEquals(expResult, result);
    }
}
