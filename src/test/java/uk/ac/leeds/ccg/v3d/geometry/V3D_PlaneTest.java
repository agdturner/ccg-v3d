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

    /**
     * Test of toString method, of class V3D_Plane.
     */
    @Test
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
        boolean result = instance.intersects(pt);
        assertEquals(expResult, result);
        // Test2
        pt = P1P0P1;
        instance = getPlane(P1P1P0, P1P1P1, P1P0P0);
        expResult = true;
        result = instance.intersects(pt);
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
     * Test of getNormalVector method, of class V3D_Plane.
     */
    @Test
    public void testGetNormalVector() {
        System.out.println("getNormalVector");
        V3D_Plane instance = getPlane(P0P1P0, P1P1P1, P1P0P0);
        V3D_Vector expResult = new V3D_Vector(P1P1N1);
        V3D_Vector result = instance.getNormalVector();
        assertEquals(expResult, result);
        // Test 2
        instance = getPlane(P0P0P0, P0P1P1, P0N1P0);
        expResult = new V3D_Vector(P1P1N1);
        result = instance.getNormalVector();
        assertEquals(expResult, result);
        // Test 3
        instance = getPlane(P0P0P0, P0P1P1, P0N1P0);
        expResult = new V3D_Vector(P1P1N1);
        result = instance.getNormalVector();
        assertEquals(expResult, result);
    }

    /**
     * Test of intersects method, of class V3D_Plane.
     */
    @Test
    public void testIntersects_3args() {
        System.out.println("intersects");
        V3D_Plane pl = null;
        int scale = 0;
        RoundingMode rm = null;
        V3D_Plane instance = null;
        boolean expResult = false;
        boolean result = instance.intersects(pl, scale, rm);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of intersects method, of class V3D_Plane.
     */
    @Test
    public void testIntersects_V3D_Point() {
        System.out.println("intersects");
        V3D_Point pt = null;
        V3D_Plane instance = null;
        boolean expResult = false;
        boolean result = instance.intersects(pt);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane.
     */
    @Test
    public void testIsOnPlane() {
        System.out.println("isOnPlane");
        V3D_LineSegment l = null;
        V3D_Plane instance = null;
        boolean expResult = false;
        boolean result = instance.isOnPlane(l);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class V3D_Plane.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        V3D_Plane instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
}
