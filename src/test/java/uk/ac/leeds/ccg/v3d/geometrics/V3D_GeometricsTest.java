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
        points[0] = pP0P0P0;
        points[1] = pP0P0P0;
        assertTrue(V3D_Geometrics.isCoincident(points));
        points[1] = pP0P0P1;
        assertFalse(V3D_Geometrics.isCoincident(points));
        points[0] = pP0P0P1;
        assertTrue(V3D_Geometrics.isCoincident(points));
    }

    /**
     * Test of isCollinear method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCollinear_V3D_Line_V3D_PointArr() {
        System.out.println("isCollinear");
        V3D_Line l;
        V3D_Point[] points = new V3D_Point[2];
        // Test 1
        l = new V3D_Line(pN1N1N1, pP1P1P1);
        points[0] = pP2P2P2;
        points[1] = pN2N2N2;
        assertTrue(V3D_Geometrics.isCollinear(e, l, points));
        // Test 2
        points[1] = pN2N2N1;
        assertFalse(V3D_Geometrics.isCollinear(e, l, points));
        // Test 3
        points[0] = pN1N2N1;
        assertFalse(V3D_Geometrics.isCollinear(e, l, points));
    }

    /**
     * Test of isCollinear method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCollinear_V3D_PointArr() {
        System.out.println("isCollinear");
        V3D_Point[] points = new V3D_Point[3];
        points[0] = pP2P2P2;
        points[1] = pP2P2P1;
        points[2] = pP2P2P0;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
        points[2] = pP2P2N1;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
        points[2] = pP2P2N2;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
        // P2P1*
        points[0] = pP2P1P2;
        points[1] = pP2P1P1;
        points[2] = pP2P1P0;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
        points[2] = pP2P1N1;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
        points[2] = pP2P1N2;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
        // P2P0*
        points[0] = pP2P0P2;
        points[1] = pP2P0P1;
        points[2] = pP2P0P0;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
        points[2] = pP2P0N1;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
        points[2] = pP2P0N2;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
        // P2N1*
        points[0] = pP2N1P2;
        points[1] = pP2N1P1;
        points[2] = pP2N1P0;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
        points[2] = pP2N1N1;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
        points[2] = pP2N1N2;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
        // P2N2*
        points[0] = pP2N2P2;
        points[1] = pP2N2P1;
        points[2] = pP2N2P0;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
        points[2] = pP2N2N1;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
        points[2] = pP2N2N2;
        // Others
        points = new V3D_Point[3];
        points[0] = pP2P2P2;
        points[1] = pN2N2N2;
        points[2] = pN1N1N1;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
        points[1] = pP1P1P0;
        assertFalse(V3D_Geometrics.isCollinear(e, points));
        points = new V3D_Point[3];
        points[0] = pP2P2P2;
        points[1] = pN2N2N2;
        points[2] = pP1P1P1;
        assertTrue(V3D_Geometrics.isCollinear(e, points));
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
        V3D_Line result = V3D_Geometrics.getLine(e, points);
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
        p = new V3D_Plane(pP0P0P0, pP1P0P0, pP0P1P0);
        points = new V3D_Point[3];
        points[0] = pP2P2P0;
        points[1] = pN2P2P0;
        points[2] = pP2N2P0;
        assertTrue(V3D_Geometrics.isCoplanar(e, p, points));
        // Test 2
        points[2] = pP2N2P1;
        assertFalse(V3D_Geometrics.isCoplanar(e, p, points));
        // Test 3
        p = new V3D_Plane(pP0P0P0, pP0P1P0, pP0P0P1);
        points[0] = pP0P2P2;
        points[1] = pP0N2P2;
        points[2] = pP0P2N2;
        assertTrue(V3D_Geometrics.isCoplanar(e, p, points));
        // Test 4
        points[2] = pP2N2P1;
        assertFalse(V3D_Geometrics.isCoplanar(e, p, points));
    }

    /**
     * Test of isCoplanar method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCoplanar_int_V3D_PointArr() {
        System.out.println("isCoplanar");
        V3D_Point[] points = new V3D_Point[6];
        points[0] = pP0P0P0;
        points[1] = pP0P1P0;
        points[2] = pP0P0P1;
        points[3] = pP0P2P2;
        points[4] = pP0N2P2;
        points[5] = pP0P2N2;
        assertTrue(V3D_Geometrics.isCoplanar(e, points));
        // Test 2
        points[2] = pP2N2P1;
        assertFalse(V3D_Geometrics.isCoplanar(e, points));
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
        V3D_Plane result = V3D_Geometrics.getPlane(e, points);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
