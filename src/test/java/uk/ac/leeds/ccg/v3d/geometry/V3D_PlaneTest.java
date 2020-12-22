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

import uk.ac.leeds.ccg.v3d.test.V3D_Test;
import ch.obermuhlner.math.big.BigRational;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_PlaneTest
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V3D_PlaneTest extends V3D_Test {

    private static final long serialVersionUID = 1L;

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

    //@Test
    public void run() {
        testToString();
//        testIsOnPlane_V3D_Point();
//        testIsOnPlane_V3D_Point();
//        testIsOnPlane_V3D_LineSegment();
//        testEquals();
//        testIsIntersectedBy_V3D_Point();
//        testIsIntersectedBy_V3D_Line();
//        testIsIntersectedBy_V3D_Plane();
//        testGetNormalVector();
//        testIsParallel_V3D_Plane();
//        testIsParallel_V3D_Line();
//
//        testGetIntersection_V3D_Plane();
    }

    /**
     * Test of toString method, of class V3D_Plane.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Plane instance = new V3D_Plane(P0P0P0, P1P1P1, P1P0P0);
        String expResult = "V3D_Plane(p=V3D_Point(x=0, y=0, z=0), "
                + "q=V3D_Point(x=1, y=1, z=1), r=V3D_Point(x=1, y=0, z=0))";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane.
     */
    @Test
    public void testIsOnPlane_V3D_Point() {
        System.out.println("isOnPlane");
        V3D_Point pt = P1P0P0;
        V3D_Plane instance = new V3D_Plane(P1P1P0, P1P1P1, P1P0P0);
        boolean expResult = true;
        boolean result = instance.isIntersectedBy(pt);
        assertEquals(expResult, result);
        // Test2
        pt = P1P0P1;
        instance = new V3D_Plane(P1P1P0, P1P1P1, P1P0P0);
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
        // Test 1 to 9 lines segments in line with axes
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P1P0P0);
        V3D_Plane instance = e.x0;
        assertFalse(instance.isOnPlane(l));
        // Test 2
        instance = e.y0;
        assertTrue(instance.isOnPlane(l));
        // Test 3
        instance = e.z0;
        assertTrue(instance.isOnPlane(l));
        // Test 4
        l = new V3D_LineSegment(P0P0P0, P0P1P0);
        instance = e.x0;
        assertTrue(instance.isOnPlane(l));
        // Test 5
        instance = e.y0;
        assertFalse(instance.isOnPlane(l));
        // Test 6
        instance = e.z0;
        assertTrue(instance.isOnPlane(l));
        // Test 7
        l = new V3D_LineSegment(P0P0P0, P0P0P1);
        instance = e.x0;
        assertTrue(instance.isOnPlane(l));
        // Test 8
        instance = e.y0;
        assertTrue(instance.isOnPlane(l));
        // Test 9
        instance = e.z0;
        assertFalse(instance.isOnPlane(l));        
    }

    /**
     * Test of equals method, of class V3D_Plane.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = new V3D_Plane(P0P1P0, P1P1P1, P1P0P0);
        V3D_Plane instance = new V3D_Plane(P0P1P0, P1P1P1, P1P0P0);
        assertTrue(instance.equals(o));
        // Test 2
        instance = new V3D_Plane(P1P1P0, P1P1P1, P1P0P0);
        assertFalse(instance.equals(o));
        // Test 3
        instance = new V3D_Plane(P1P1P1, P1P0P0, P0P1P0);
        assertTrue(instance.equals(o));
        // Test 4
        instance = new V3D_Plane(P1P0P0, P0P1P0, P1P1P1);
        assertTrue(instance.equals(o));
        // Test 5
        o = new V3D_Plane(P0P0P0, P1P0P0, P0P1P0);
        V3D_Point q = new V3D_Point(P2, P0, P0);
        V3D_Point r = new V3D_Point(P0, P2, P0);
        instance = new V3D_Plane(P0P0P0, q, r);
        assertFalse(instance.equals(o));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsIntersectedBy_V3D_Plane() {
        System.out.println("intersects");
        V3D_Plane pl;
        V3D_Plane instance;
        // Test 1 to 4 
        pl = e.x0;
        // Test 1 
        instance = e.x0;
        assertTrue(instance.isIntersectedBy(pl));
        // Test 2
        instance = e.y0;
        assertTrue(instance.isIntersectedBy(pl));
        // Test 3
        instance = e.z0;
        assertTrue(instance.isIntersectedBy(pl));
        // Test 4
        instance = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1); // y=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 5
        instance = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 6 to 9
        pl = new V3D_Plane(e.xAxis.q, P0P0P0, e.zAxis.q); // y=0
        // Test 6
        instance = new V3D_Plane(P0P0P0, e.yAxis.q, e.zAxis.q); // x=0
        assertTrue(instance.isIntersectedBy(pl));
        // Test 7
        instance = new V3D_Plane(e.xAxis.q, e.yAxis.q, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl));
        // Test 8
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1);       // x=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 9
        instance = new V3D_Plane(P0P1P1, P1P1P1, P0P0P1);       // z=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 10 to 13
        pl = new V3D_Plane(e.xAxis.q, e.yAxis.q, P0P0P0); // z=0
        // Test 10
        instance = new V3D_Plane(P0P0P0, e.yAxis.q, e.zAxis.q); // x=0
        assertTrue(instance.isIntersectedBy(pl));
        // Test 11
        instance = new V3D_Plane(e.xAxis.q, P0P0P0, e.zAxis.q); // y=0
        assertTrue(instance.isIntersectedBy(pl));
        // Test 12
        instance = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 13
        instance = new V3D_Plane(P1P1P1, P0P1P0, P1P1P0); // y=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 14 to 15
        pl = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1); // x=1
        // Test 14
        instance = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1); // y=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 15
        instance = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 16 to 17
        pl = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1); // y=1
        // Test 16
        instance = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 17
        instance = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 18 to 19
        pl = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1); // z=1
        // Test 18
        instance = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 19
        instance = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1); // y=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 20 to 21
        pl = new V3D_Plane(N1P0P0, N1P1P1, N1P0P1); // x=-1
        // Test 20
        instance = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1); // y=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 21
        instance = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 22 to 23
        pl = new V3D_Plane(N1N1N1, P0N1P0, P1N1N1); // y=-1
        // Test 22
        instance = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 23
        instance = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 24 to 25
        pl = new V3D_Plane(P1P0N1, P0N1N1, P0P0N1); // z=-1
        // Test 24
        instance = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 25
        instance = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1); // y=1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 26 to 27
        pl = new V3D_Plane(N1P0P0, N1P1P1, N1P0P1); // x=-1
        // Test 26
        instance = new V3D_Plane(N1N1N1, P0N1P0, P1N1N1); // y=-1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 27
        instance = new V3D_Plane(P1P0N1, P0N1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 28 to 29
        pl = new V3D_Plane(N1N1N1, P0N1P0, P1N1N1); // y=-1
        // Test 28
        instance = new V3D_Plane(N1P0P0, N1P1P1, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 29
        instance = new V3D_Plane(P1P0N1, P0N1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 30 to 31
        pl = new V3D_Plane(P1P0N1, P0N1N1, P0P0N1); // z=-1
        // Test 30
        instance = new V3D_Plane(N1P0P0, N1P1P1, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl));
        // Test 31
        instance = new V3D_Plane(N1N1N1, P0N1P0, P1N1N1); // y=-1
        assertTrue(instance.isIntersectedBy(pl));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("intersects");
        V3D_Point pt = P0P0P0;
        V3D_Plane instance = new V3D_Plane(P0P0P0, P1P0P0, N1P0P1);
        assertTrue(instance.isIntersectedBy(pt));
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
        V3D_Plane instance = new V3D_Plane(P0P0P0, P1P0P0, P0P1P0); // Z = 0
        V3D_Vector expResult = new V3D_Vector(P0P0P1);
        V3D_Vector result = instance.n;
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Plane(P0P0N1, P1P0N1, P0P1N1); // Z = -1
        expResult = new V3D_Vector(P0P0P1);
        result = instance.n;
        assertEquals(expResult, result);
        // Test 3
        instance = new V3D_Plane(P0P0P1, P1P0P1, P0P1P1); // Z = 1
        expResult = new V3D_Vector(P0P0P1);
        result = instance.n;
        assertEquals(expResult, result);
        // Test 4
        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1); // Z = 1
        expResult = new V3D_Vector(P0P0P1);
        result = instance.n;
        assertEquals(expResult, result);
        // Test 5
        instance = new V3D_Plane(P0P1P1, P0P0P1, P1P0P1); // Z = 1
        expResult = new V3D_Vector(P0P0P1);
        result = instance.n;
        assertEquals(expResult, result);
        // Test 6
        instance = new V3D_Plane(P0P0P0, P0P1P0, P0P0N1); // Y = 0
        expResult = new V3D_Vector(N1P0P0);
        result = instance.n;
        assertEquals(expResult, result);
        // Test 7
        instance = new V3D_Plane(P0P0P0, P1P0P0, P0P0N1); // X = 0
        expResult = new V3D_Vector(P0P1P0);
        result = instance.n;
        assertEquals(expResult, result);
        // Test 8
        instance = new V3D_Plane(P0P0P0, P1P0P0, N1P0P1); // X = 0
        expResult = new V3D_Vector(P0N1P0); // This is the other normal than for
        result = instance.n;// test 7 due to the right hand rule  
        assertEquals(expResult, result);    // and the orientation.
        instance = new V3D_Plane(P0P1P0, P1P1P1, P1P0P0);
        expResult = new V3D_Vector(P1P1N1);
        result = instance.n;
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Plane(P0P0P0, P0P1P1, P0N1P0);
        expResult = new V3D_Vector(P1P0P0);
        result = instance.n;
        assertEquals(expResult, result);
        // Test 3
        instance = new V3D_Plane(P0P0P0, P1P1P1, P0N1N1);
        expResult = new V3D_Vector(P0P1N1);
        result = instance.n;
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line() {
        System.out.println("intersects");
        V3D_Line l = new V3D_Line(P0P0P0, P0P1P0); // Y axis
        V3D_Plane instance = new V3D_Plane(P1P0P1, P0P0P1, N1P0N1); // Y=0 plane
        assertTrue(instance.isIntersectedBy(l));
        // Test 2
        l = new V3D_Line(N1N1N1, P1P1P1);
        instance = new V3D_Plane(P1P1N1, P1N1N1, N1P1N1); // z=-1 plane
        assertTrue(instance.isIntersectedBy(l));
        // Test 2
        l = new V3D_Line(P0P0P0, P1P1P1);
        instance = new V3D_Plane(P1P1N1, P1N1N1, N1P1N1); // z=-2 plane
        assertTrue(instance.isIntersectedBy(l));
    }

    /**
     * Test of isParallel method, of class V3D_Plane.
     */
    @Test
    public void testIsParallel_V3D_Plane() {
        System.out.println("isParallel");
        V3D_Plane p = new V3D_Plane(P1P1P0, P1N1P0, N1P1P0);
        V3D_Plane instance = new V3D_Plane(P1P1P1, P1N1P1, N1P1P1);
        assertTrue(instance.isParallel(p));
        // Test 2
        instance = new V3D_Plane(P1P1N1, P1N1N1, N1P1N1);
        assertTrue(instance.isParallel(p));
        // Test 3
        instance = new V3D_Plane(P1P1N1, P1N1N1, N1P1N1);
        assertTrue(instance.isParallel(p));
        // Test 4
        p = e.x0;
        instance = e.y0;
        assertFalse(instance.isParallel(p));
        // Test 5
        p = e.x0;
        instance = e.z0;
        assertFalse(instance.isParallel(p));
        // Test 6
        p = e.y0;
        instance = e.z0;
        assertFalse(instance.isParallel(p));
        // Test 7
        p = new V3D_Plane(P0P0P0, P0P1P0, P1P1P1);
        instance = new V3D_Plane(P1P0P0, P1P1P0, new V3D_Point(P2, P1, P1));
        assertTrue(instance.isParallel(p));
        // Test 8
        instance = new V3D_Plane(P1N1P0, P1P0P0, new V3D_Point(P2, P0, P1));
        assertTrue(instance.isParallel(p));
        // Test 9
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P1P1);
        assertFalse(instance.isParallel(p));
    }

    /**
     * Test of isParallel method, of class V3D_Plane.
     */
    @Test
    public void testIsParallel_V3D_Line() {
        System.out.println("isParallel");
        V3D_Line l = e.xAxis; 
        V3D_Plane instance = e.y0;
        assertTrue(instance.isParallel(l));
        // Test 2
        instance = e.z0;
        assertTrue(instance.isParallel(l));
        // Test 3
        instance = e.x0;
        assertFalse(instance.isParallel(l));
        // Test 4
        l = e.yAxis;
        instance = e.x0;
        assertTrue(instance.isParallel(l));
        // Test 5
        instance = e.y0;
        assertFalse(instance.isParallel(l));
        // Test 6
        instance = e.z0;
        assertTrue(instance.isParallel(l));
        // Test 7
        l = e.zAxis;
        instance = e.x0;
        assertTrue(instance.isParallel(l));
        // Test 8
        instance = e.y0;
        assertTrue(instance.isParallel(l));
        // Test 9
        instance = e.z0;
        assertFalse(instance.isParallel(l));
    }

    /**
     * Test of getIntersection method, of class V3D_Plane.
     */
    @Test
    public void testGetIntersection_V3D_Plane() {
        System.out.println("getIntersection");
        V3D_Plane pl;
        V3D_Plane instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1 to 4 
        pl = e.x0;
        // Test 1 
        instance = e.x0;
        expResult = e.x0;
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 2
        instance = e.y0;
        expResult = e.zAxis;
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 3
        instance = e.z0;
        expResult = e.yAxis;
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 4
        instance = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1); // y=1
        expResult = new V3D_Line(P0P1P0, P0P1P1);    // x=0, y=1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 5
        instance = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1); // z=1
        expResult = new V3D_Line(P0P1P1, P0P0P1);    // x=0, z=1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 6 to 9
        pl = new V3D_Plane(e.xAxis.q, P0P0P0, e.zAxis.q); // y=0
        // Test 6
        instance = new V3D_Plane(P0P0P0, e.yAxis.q, e.zAxis.q); // x=0
        expResult = new V3D_Line(P0P0N1, P0P0P1);          // x=0, y=0
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 7
        instance = new V3D_Plane(e.xAxis.q, e.yAxis.q, P0P0P0); // z=0
        expResult = new V3D_Line(P0P0P0, P1P0P0);          // y=0, z=0
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 8
        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1);       // x=1
        expResult = new V3D_Line(P1P0N1, P1P0P1);          // x=1, y=0
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 9
        instance = new V3D_Plane(P0P1P1, P1P1P1, P0P0P1);       // z=1
        expResult = new V3D_Line(P0P0P1, P1P0P1);          // y=0, z=1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 10 to 13
        pl = new V3D_Plane(e.xAxis.q, e.yAxis.q, P0P0P0); // z=0
        // Test 10
        instance = new V3D_Plane(P0P0P0, e.yAxis.q, e.zAxis.q); // x=0
        expResult = new V3D_Line(P0N1P0, P0P1P0);          // x=0, z=0
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 11
        instance = new V3D_Plane(e.xAxis.q, P0P0P0, e.zAxis.q); // y=0
        expResult = new V3D_Line(N1P0P0, P1P0P0);          // y=0, z=0
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 12
        instance = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1); // x=1
        expResult = new V3D_Line(P1N1P0, P1P1P0);    // x=1, z=0
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 13
        instance = new V3D_Plane(P1P1P1, P0P1P0, P1P1P0); // y=1
        expResult = new V3D_Line(N1P1P0, P1P1P0);    // y=1, z=0
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 14 to 15
        pl = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1); // x=1
        // Test 14
        instance = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1); // y=1
        expResult = new V3D_Line(P1P1P0, P1P1P1);    // x=1, y=1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 15
        instance = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1); // z=1
        expResult = new V3D_Line(P1P1P1, P1P0P1);    // x=1, z=1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 16 to 17
        pl = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1); // y=1
        // Test 16
        instance = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1); // x=1
        expResult = new V3D_Line(P1P1P0, P1P1P1);    // x=1, y=1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 17
        instance = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1); // z=1
        expResult = new V3D_Line(P1P1P1, P0P1P1);    // y=1, z=1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 18 to 19
        pl = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1); // z=1
        // Test 18
        instance = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1); // x=1
        expResult = new V3D_Line(P1P0P1, P1P1P1);    // x=1, z=1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 19
        instance = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1); // y=1
        expResult = new V3D_Line(P1P1P1, P0P1P1);    // y=1, z=1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 20 to 21
        pl = new V3D_Plane(N1P0P0, N1P1P1, N1P0P1); // x=-1
        // Test 20
        instance = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1); // y=1
        expResult = new V3D_Line(N1P1P0, N1P1P1);    // x=-1, y=1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 21
        instance = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1); // z=1
        expResult = new V3D_Line(N1P1P1, N1P0P1);    // x=-1, z=1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 22 to 23
        pl = new V3D_Plane(N1N1N1, P0N1P0, P1N1N1); // y=-1
        // Test 22
        instance = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1); // x=1
        expResult = new V3D_Line(P1N1P0, P1N1P1);    // x=1, y=-1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 23
        instance = new V3D_Plane(P1P0P1, P0N1P1, P0P0P1); // z=1
        expResult = new V3D_Line(P1N1P1, P0N1P1);    // y=-1, z=1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 24 to 25
        pl = new V3D_Plane(P1P0N1, P0N1N1, P0P0N1); // z=-1
        // Test 24
        instance = new V3D_Plane(P1P0P0, P1P1P1, P1P0P1); // x=1
        expResult = new V3D_Line(P1P0N1, P1P1N1);    // x=1, z=-1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 25
        instance = new V3D_Plane(N1P1N1, P0P1P0, P1P1N1); // y=1
        expResult = new V3D_Line(P1P1N1, P0P1N1);    // y=1, z=-1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 26 to 27
        pl = new V3D_Plane(N1P0P0, N1P1P1, N1P0P1); // x=-1
        // Test 26
        instance = new V3D_Plane(N1N1N1, P0N1P0, P1N1N1); // y=-1
        expResult = new V3D_Line(N1N1P0, N1N1P1);    // x=-1, y=-1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 27
        instance = new V3D_Plane(P1P0N1, P0N1N1, P0P0N1); // z=-1
        expResult = new V3D_Line(N1P1N1, N1P0N1);    // x=-1, z=-1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 28 to 29
        pl = new V3D_Plane(N1N1N1, P0N1P0, P1N1N1); // y=-1
        // Test 28
        instance = new V3D_Plane(N1P0P0, N1P1P1, N1P0P1); // x=-1
        expResult = new V3D_Line(N1N1P0, N1N1P1);    // x=-1, y=-1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 29
        instance = new V3D_Plane(P1P0N1, P0N1N1, P0P0N1); // z=-1
        expResult = new V3D_Line(P1N1N1, P0N1N1);    // y=-1, z=-1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 30 to 31
        pl = new V3D_Plane(P1P0N1, P0N1N1, P0P0N1); // z=-1
        // Test 30
        instance = new V3D_Plane(N1P0P0, N1P1P1, N1P0P1); // x=-1
        expResult = new V3D_Line(N1P0N1, N1P1N1);    // x=-1, z=-1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
        // Test 31
        instance = new V3D_Plane(N1N1N1, P0N1P0, P1N1N1); // y=-1
        expResult = new V3D_Line(P1N1N1, P0N1N1);    // y=-1, z=-1
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);

        // Test 32 to ?
        pl = new V3D_Plane(N1P0P0, N1P1P1, N1P0P1); // x=-1
        // Test 32
        instance = new V3D_Plane(new V3D_Point(N1, N2, N1),
                new V3D_Point(P0, N2, P0),
                new V3D_Point(P1, N2, N1)); // y=-2
        expResult = new V3D_Line(new V3D_Point(N1, N2, P0),
                new V3D_Point(N1, N2, P1));    // x=-1, y=-2
        result = instance.getIntersection(pl);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Plane.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        V3D_Line l;
        V3D_Plane instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1-3 axis with orthoganol plane through origin.
        // Test 1
        l = e.xAxis;
        instance = e.x0;
        expResult = P0P0P0;
        result = instance.getIntersection(l);
        assertEquals(expResult, result);
        // Test 2
        l = e.yAxis;
        instance = e.y0;
        expResult = P0P0P0;
        result = instance.getIntersection(l);
        assertEquals(expResult, result);
        // Test 3
        l = e.zAxis;
        instance = e.z0;
        expResult = P0P0P0;
        result = instance.getIntersection(l);
        assertEquals(expResult, result);
        // Test 4-6 axis with orthoganol plane not through origin.
        // Test 4
        l = e.xAxis;
        instance = e.x0.apply(new V3D_Vector(P1, P0, P0));
        expResult = P1P0P0;
        result = instance.getIntersection(l);
        assertEquals(expResult, result);
        // Test 5
        l = e.yAxis;
        instance = e.y0.apply(new V3D_Vector(P0, P1, P0));
        expResult = P0P1P0;
        result = instance.getIntersection(l);
        assertEquals(expResult, result);
        // Test 6
        l = e.zAxis;
        instance = e.z0.apply(new V3D_Vector(P0, P0, P1));
        expResult = P0P0P1;
        result = instance.getIntersection(l);
        assertEquals(expResult, result);
        // Test 7
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_Line(new V3D_Point(P0, P2, P0), new V3D_Point(P1, P5, P1));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(P0P0N1, new V3D_Point(P0, P4, P0), 
                new V3D_Point(P2, P0, P0));
        // (2, 8, 2)
        expResult = new V3D_Point(P2, P8, P2);
        result = instance.getIntersection(l);
        assertEquals(expResult, result);
        // Test 9
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_Line(new V3D_Point(P0, P2, P0), new V3D_Point(P1, P5, P1));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(new V3D_Point(P0, P0, N1),
                new V3D_Point(P0, P4, P0), new V3D_Point(P2, P0, P0));
        // (2, 8, 2)
        expResult = new V3D_Point(P2, P8, P2);
        result = instance.getIntersection(l);
        assertEquals(expResult, result);
        // Test 10
        // line
        // x = 0, y = 0, z = t
        // points (0, 0, 0), (0, 0, 1) 
        l = new V3D_Line(new V3D_Point(P0, P0, P0), new V3D_Point(P0, P0, P1));
        // plane
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(new V3D_Point(P0, P0, P2),
                new V3D_Point(P1, P0, P2), new V3D_Point(P0, P1, P2));
        expResult = new V3D_Point(P0, P0, P2);
        result = instance.getIntersection(l);
        assertEquals(expResult, result);
    }

    /**
     * Test of apply method, of class V3D_Plane.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        V3D_Vector v = new V3D_Vector(1, 0, 0);
        V3D_Plane instance = e.x0;
        V3D_Plane expResult = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1);
        V3D_Plane result = instance.apply(v);
        assertEquals(expResult, result);
        // Test 2
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane.
     */
    @Test
    public void testIsOnPlane() {
        System.out.println("isOnPlane");
        V3D_Line l = new V3D_Line(P0P0P0, P1P0P0);
        V3D_Plane instance = new V3D_Plane(P0P0P0, P1P0P0, P1P1P0);
        assertTrue(instance.isOnPlane(l));
        // Test 2
        l = new V3D_Line(P0P0P0, P1P1P0);
        assertTrue(instance.isOnPlane(l));
    }

    /**
     * Test of getIntersection method, of class V3D_Plane.
     */
    @Test
    public void testGetIntersection_V3D_Plane_V3D_Line() {
        System.out.println("getIntersection");
        // No test - These tests are covered by testGetIntersection_V3D_Line.
    }

    /**
     * Test of getIntersection method, of class V3D_Plane.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        V3D_LineSegment l = null;
        boolean flag = false;
        V3D_Plane instance = null;
        V3D_Geometry expResult = null;
        V3D_Geometry result;
        // Test 1-3 part of axis with orthoganol plane through origin.
        // Test 1
        l = new V3D_LineSegment(N1P0P0, P1P0P0);
        instance = e.x0;
        expResult = P0P0P0;
        result = instance.getIntersection(l, flag);
        assertEquals(expResult, result);
        // Test 2
        l = new V3D_LineSegment(P0N1P0, P0P1P0);
        instance = e.y0;
        expResult = P0P0P0;
        result = instance.getIntersection(l, flag);
        assertEquals(expResult, result);
        // Test 3
        l = new V3D_LineSegment(P0P0N1, P0P0P1);
        instance = e.z0;
        expResult = P0P0P0;
        result = instance.getIntersection(l, flag);
        assertEquals(expResult, result);
        // Test 4-6 part of axis with orthoganol plane not through origin.
        // Test 4
        l = new V3D_LineSegment(N1P0P0, P1P0P0);
        instance = e.x0.apply(new V3D_Vector(P1, P0, P0));
        expResult = P1P0P0;
        result = instance.getIntersection(l, flag);
        assertEquals(expResult, result);
        // Test 5
        l = new V3D_LineSegment(P0N1P0, P0P1P0);
        instance = e.y0.apply(new V3D_Vector(P0, P1, P0));
        expResult = P0P1P0;
        result = instance.getIntersection(l, flag);
        assertEquals(expResult, result);
        // Test 6
        l = new V3D_LineSegment(P0P0N1, P0P0P1);
        instance = e.z0.apply(new V3D_Vector(P0, P0, P1));
        expResult = P0P0P1;
        result = instance.getIntersection(l, flag);
        assertEquals(expResult, result);
        // Test 7
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_LineSegment(new V3D_Point(P0, P2, P0), new V3D_Point(P1, P5, P1));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(P0P0N1, new V3D_Point(P0, P4, P0), 
                new V3D_Point(P2, P0, P0));
        // (2, 8, 2)
        expResult = new V3D_Point(P2, P8, P2);
        result = instance.getIntersection(l, flag);
        assertNotEquals(expResult, result);
        l = new V3D_LineSegment(new V3D_Point(P0, P2, P0), new V3D_Point(P2, P8, P2));
        result = instance.getIntersection(l, flag);
        assertEquals(expResult, result);
        // Test 9
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_LineSegment(new V3D_Point(P0, P2, P0), new V3D_Point(P1, P5, P1));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(new V3D_Point(P0, P0, N1),
                new V3D_Point(P0, P4, P0), new V3D_Point(P2, P0, P0));
        // (2, 8, 2)
        expResult = new V3D_Point(P2, P8, P2);
        result = instance.getIntersection(l, flag);
        assertNotEquals(expResult, result);
        l = new V3D_LineSegment(new V3D_Point(P0, P2, P0), new V3D_Point(P2, P8, P2));
        result = instance.getIntersection(l, flag);
        assertEquals(expResult, result);
        // Test 10
        // line
        // x = 0, y = 0, z = t
        // points (0, 0, 0), (0, 0, 1) 
        l = new V3D_LineSegment(new V3D_Point(P0, P0, P0), new V3D_Point(P0, P0, P1));
        // plane
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(new V3D_Point(P0, P0, P2),
                new V3D_Point(P1, P0, P2), new V3D_Point(P0, P1, P2));
        expResult = new V3D_Point(P0, P0, P2);
        result = instance.getIntersection(l, flag);
        assertNotEquals(expResult, result);
        l = new V3D_LineSegment(new V3D_Point(P0, P0, P0), new V3D_Point(P0, P0, P4));
        result = instance.getIntersection(l, flag);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        // No test
    }

    /**
     * Test of getAsMatrix method, of class V3D_Plane.
     */
    @Test
    public void testGetAsMatrix() {
        System.out.println("getAsMatrix");
        V3D_Plane instance = e.x0;
        Math_Matrix_BR expResult = new Math_Matrix_BR(3, 3);
        BigRational[][] m = expResult.getM();
        m[0][0] = BigRational.ZERO;
        m[0][1] = BigRational.ZERO;
        m[0][2] = BigRational.ZERO;
        m[1][0] = BigRational.ZERO;
        m[1][1] = BigRational.ONE;
        m[1][2] = BigRational.ZERO;
        m[2][0] = BigRational.ZERO;
        m[2][1] = BigRational.ZERO;
        m[2][2] = BigRational.ONE;
        Math_Matrix_BR result = instance.getAsMatrix();
        assertEquals(expResult, result);
        // Test 2
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane.
     */
    @Test
    public void testGetDistanceSquared() {
        System.out.println("getDistanceSquared");
        V3D_Plane p = e.x0;
        V3D_Plane instance = e.x0;
        BigRational expResult = BigRational.ZERO;
        BigRational result = instance.getDistanceSquared(p);
        assertEquals(expResult, result);
        // Test 2
        V3D_Vector v = e.i;
         instance = e.x0.apply(v);
         expResult = BigRational.ONE;
         result = instance.getDistanceSquared(p);
        
    }
}
