/*
 * Copyright 2020 Centre for Computational Geography.
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
package uk.ac.leeds.ccg.v3d.geometrics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.V3D_Test;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Plane;

/**
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_GeometricsTest extends V3D_Test {

    public V3D_GeometricsTest() {
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
     * Test of isCoincident method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCoincident() {
        System.out.println("isCoincident");
        V3D_Point[] points = new V3D_Point[2];
        int oom = V3D_Environment.DEFAULT_OOM;
        points[0] = new V3D_Point(P0P0P0, oom);
        points[1] = new V3D_Point(P0P0P0, oom);
        assertTrue(V3D_Geometrics.isCoincident(points));
        points[1] = new V3D_Point(P0P0P1, oom);
        assertFalse(V3D_Geometrics.isCoincident(points));
        points[0] = new V3D_Point(P0P0P1, oom);
        assertTrue(V3D_Geometrics.isCoincident(points));
    }

    /**
     * Test of isCollinear method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCollinear_V3D_Line_V3D_PointArr() {
        System.out.println("isCollinear");
        int oom;
        V3D_Line l;
        V3D_Point[] points = new V3D_Point[2];
        // Test 1
        oom = -2;
        l = new V3D_Line(N1N1N1, P1P1P1, -1);
        points[0] = new V3D_Point(P2P2P2, oom);
        points[1] = new V3D_Point(N2N2N2, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, l, points));
        // Test 2
        points[1] = new V3D_Point(N2N2N1, oom);
        assertFalse(V3D_Geometrics.isCollinear(oom, l, points));
        // Test 3
        points[0] = new V3D_Point(N1N2N1, oom);
        assertFalse(V3D_Geometrics.isCollinear(oom, l, points));
    }

    /**
     * Test of isCollinear method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCollinear_V3D_PointArr() {
        System.out.println("isCollinear");
        int oom = -2;
        V3D_Point[] points = new V3D_Point[3];
        points[0] = new V3D_Point(P2P2P2, oom);
        points[1] = new V3D_Point(P2P2P1, oom);
        points[2] = new V3D_Point(P2P2P0, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        points[2] = new V3D_Point(P2P2N1, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        points[2] = new V3D_Point(P2P2N2, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        // P2P1*
        points[0] = new V3D_Point(P2P1P2, oom);
        points[1] = new V3D_Point(P2P1P1, oom);
        points[2] = new V3D_Point(P2P1P0, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        points[2] = new V3D_Point(P2P1N1, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        points[2] = new V3D_Point(P2P1N2, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        // P2P0*
        points[0] = new V3D_Point(P2P0P2, oom);
        points[1] = new V3D_Point(P2P0P1, oom);
        points[2] = new V3D_Point(P2P0P0, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        points[2] = new V3D_Point(P2P0N1, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        points[2] = new V3D_Point(P2P0N2, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        // P2N1*
        points[0] = new V3D_Point(P2N1P2, oom);
        points[1] = new V3D_Point(P2N1P1, oom);
        points[2] = new V3D_Point(P2N1P0, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        points[2] = new V3D_Point(P2N1N1, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        points[2] = new V3D_Point(P2N1N2, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        // P2N2*
        points[0] = new V3D_Point(P2N2P2, oom);
        points[1] = new V3D_Point(P2N2P1, oom);
        points[2] = new V3D_Point(P2N2P0, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        points[2] = new V3D_Point(P2N2N1, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        points[2] = new V3D_Point(P2N2N2, oom);
        // Others
        points = new V3D_Point[3];
        points[0] = new V3D_Point(P2P2P2, oom);
        points[1] = new V3D_Point(N2N2N2, oom);
        points[2] = new V3D_Point(N1N1N1, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        points[1] = new V3D_Point(P1P1P0, oom);
        assertFalse(V3D_Geometrics.isCollinear(oom, points));
        points = new V3D_Point[3];
        points[0] = new V3D_Point(P2P2P2, oom);
        points[1] = new V3D_Point(N2N2N2, oom);
        points[2] = new V3D_Point(P1P1P1, oom);
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
    }

    /**
     * Test of getLine method, of class V3D_Geometrics. No test needed.
     */
    @Test
    @Disabled
    public void testGetLine() {
        System.out.println("getLine");
        V3D_Point[] points = null;
        V3D_Line expResult = null;
        int oom = V3D_Environment.DEFAULT_OOM;
        V3D_Line result = V3D_Geometrics.getLine(oom, points);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isCoplanar method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCoplanar_3args() {
        System.out.println("isCoplanar");
        int oom;
        V3D_Plane p;
        V3D_Point[] points;
        // Test 1
        oom = -2;
        p = new V3D_Plane(P0P0P0, P1P0P0, P0P1P0, oom);
        points = new V3D_Point[3];
        points[0] = new V3D_Point(P2P2P0, oom);
        points[1] = new V3D_Point(N2P2P0, oom);
        points[2] = new V3D_Point(P2N2P0, oom);
        assertTrue(V3D_Geometrics.isCoplanar(oom, p, points));
        // Test 2
        points[2] = new V3D_Point(P2N2P1, oom);
        assertFalse(V3D_Geometrics.isCoplanar(oom, p, points));
        // Test 3
        p = new V3D_Plane(P0P0P0, P0P1P0, P0P0P1, oom);
        points[0] = new V3D_Point(P0P2P2, oom);
        points[1] = new V3D_Point(P0N2P2, oom);
        points[2] = new V3D_Point(P0P2N2, oom);
        assertTrue(V3D_Geometrics.isCoplanar(oom, p, points));
        // Test 4
        points[2] = new V3D_Point(P2N2P1, oom);
        assertFalse(V3D_Geometrics.isCoplanar(oom, p, points));
    }

    /**
     * Test of isCoplanar method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCoplanar_int_V3D_PointArr() {
        System.out.println("isCoplanar");
        int oom = -2;
        V3D_Point[] points = new V3D_Point[6];
        points[0] = new V3D_Point(P0P0P0, oom);
        points[1] = new V3D_Point(P0P1P0, oom);
        points[2] = new V3D_Point(P0P0P1, oom);
        points[3] = new V3D_Point(P0P2P2, oom);
        points[4] = new V3D_Point(P0N2P2, oom);
        points[5] = new V3D_Point(P0P2N2, oom);
        assertTrue(V3D_Geometrics.isCoplanar(oom, points));
        // Test 2
        points[2] = new V3D_Point(P2N2P1, oom);
        assertFalse(V3D_Geometrics.isCoplanar(oom, points));
    }

    /**
     * Test of getPlane method, of class V3D_Geometrics. No test needed.
     */
    @Test
    @Disabled
    public void testGetPlane() {
        System.out.println("getPlane");
        int oom = 0;
        V3D_Point[] points = null;
        V3D_Plane expResult = null;
        V3D_Plane result = V3D_Geometrics.getPlane(oom, points);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
