/*
 * Copyright 2021 Centre for Computational Geography.
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.v3d.V3D_Test;

/**
 * Test class for V3D_Ray.
 * 
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_RayTest extends V3D_Test {
    
    public V3D_RayTest() {
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
     * Test of equals method, of class V3D_Ray.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        Object o = new V3D_Ray(P0P0P0, P1P1P1);
        V3D_Ray instance = new V3D_Ray(P0P0P0, P1P1P1);
        assertTrue(instance.equals(o));
        // Test 2
        instance = new V3D_Ray(P1P1P1, P2P2P2);
        assertFalse(instance.equals(o));        
    }

    /**
     * Test of equals method, of class V3D_Ray.
     */
    @Test
    public void testEquals_V3D_Ray() {
        System.out.println("equals");
        V3D_Ray r = new V3D_Ray(P0P0P0, P1P1P1);
        V3D_Ray instance = new V3D_Ray(P0P0P0, P1P1P1);
        assertTrue(instance.equals(r));
        // Test 2
        instance = new V3D_Ray(P1P1P1, P2P2P2);
        assertFalse(instance.equals(r)); 
    }

    /**
     * Test of hashCode method, of class V3D_Ray.
     */
    @Test
    @Disabled
    public void testHashCode() {
        System.out.println("hashCode");
        V3D_Ray instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of apply method, of class V3D_Ray.
     */
    @Test
    @Disabled
    public void testApply() {
        System.out.println("apply");
        V3D_Vector v = null;
        V3D_Ray instance = null;
        V3D_Ray expResult = null;
        V3D_Ray result = instance.apply(v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Ray.
     */
    @Test
    @Disabled
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("isIntersectedBy");
        V3D_Point p = P0P0P0;
        V3D_Ray instance = new V3D_Ray(N1N1N1, P1P1P1);
        assertTrue(instance.isIntersectedBy(p));
        // Test2
        p = P1P1P1;
        instance = new V3D_Ray(N1N1N1, P1P1P1);
        assertTrue(instance.isIntersectedBy(p));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Ray.
     */
    @Test
    @Disabled
    public void testIsIntersectedBy_V3D_Ray_boolean() {
        System.out.println("isIntersectedBy");
        V3D_Ray r = new V3D_Ray(P0P0P0, P1P0P0);
        V3D_Ray instance = new V3D_Ray(P0P0P0, P1P0P0);
        assertTrue(instance.isIntersectedBy(r, false));
        // Test 2
        instance = new V3D_Ray(P0P0P0, P1P1P1);
        assertTrue(instance.isIntersectedBy(r, false));
        // Test 3
        instance = new V3D_Ray(N1N1N1, N1N1P0);
        assertFalse(instance.isIntersectedBy(r, false));
        // Test 4
        r = new V3D_Ray(N1N1P0, P1P1P0);
        instance = new V3D_Ray(N1N1N1, P1P1P1);
        assertTrue(instance.isIntersectedBy(r, false));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Ray.
     */
    @Test
    @Disabled
    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
        System.out.println("isIntersectedBy");
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P1P0P0);
        V3D_Ray instance = new V3D_Ray(P0P0P0, P1P0P0);
        assertTrue(instance.isIntersectedBy(l, false));
        // Test 2
        instance = new V3D_Ray(P0P0P0, P1P1P1);
        assertTrue(instance.isIntersectedBy(l, false));
        // Test 3
        instance = new V3D_Ray(N1N1N1, N1N1P0);
        assertFalse(instance.isIntersectedBy(l, false));
        // Test 4
        l = new V3D_LineSegment(N1N1P0, P1P1P0);
        instance = new V3D_Ray(N1N1N1, P1P1P1);
        assertTrue(instance.isIntersectedBy(l, false));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Ray.
     */
    @Test
    @Disabled
    public void testIsIntersectedBy_V3D_Line() {
        System.out.println("isIntersectedBy");
        V3D_Line l = new V3D_Line(P0P0P0, P1P0P0);
        V3D_Ray instance = new V3D_Ray(P0P0P0, P1P0P0);
        assertTrue(instance.isIntersectedBy(l));
        // Test 2
        instance = new V3D_Ray(P0P0P0, P1P1P1);
        assertTrue(instance.isIntersectedBy(l));
        // Test 3
        instance = new V3D_Ray(N1N1N1, N1N1P0);
        assertFalse(instance.isIntersectedBy(l));
        // Test 4
        l = new V3D_Ray(N1N1P0, P1P1P0);
        instance = new V3D_Ray(N1N1N1, P1P1P1);
        assertTrue(instance.isIntersectedBy(l));
    }

    /**
     * Test of getIntersection method, of class V3D_Ray.
     */
    @Test
    @Disabled
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        V3D_Line l = new V3D_Line(P0P0P0, P1P0P0);
        V3D_Ray instance = new V3D_Ray(P0P0P0, P1P0P0);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P0P0);
        V3D_Geometry result = instance.getIntersection(l);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Ray(P0P0P0, P1P1P1);
        result = instance.getIntersection(l);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Ray(N1N1N1, N1N1P0);
        result = instance.getIntersection(l);
        assertNull(result);
        // Test 4
        l = new V3D_Line(N1N1P0, P1P1P0);
        instance = new V3D_Ray(N1N1N1, P1P1P1);
        result = instance.getIntersection(l);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_Line(P0P0P0, P1P0P0);
        instance = new V3D_Ray(N1P0P0, P1P0P0);
        result = instance.getIntersection(l);
        expResult = new V3D_Ray(N1P0P0, P1P0P0);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getIntersection method, of class V3D_Ray.
     */
    @Test
    @Disabled
    public void testGetIntersection_V3D_Ray_boolean() {
        System.out.println("getIntersection");
        V3D_Ray l = new V3D_Ray(P0P0P0, P1P0P0);
        V3D_Ray instance = new V3D_Ray(P0P0P0, P1P0P0);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P0P0);
        V3D_Geometry result = instance.getIntersection(l);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Ray(P0P0P0, P1P1P1);
        result = instance.getIntersection(l);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Ray(N1N1N1, N1N1P0);
        result = instance.getIntersection(l);
        assertNull(result);
        // Test 4
        l = new V3D_Ray(N1N1P0, P1P1P0);
        instance = new V3D_Ray(N1N1N1, P1P1P1);
        result = instance.getIntersection(l);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_Ray(P0P0P0, P1P0P0);
        instance = new V3D_Ray(N1P0P0, P1P0P0);
        result = instance.getIntersection(l);
        expResult = new V3D_Ray(N1P0P0, P1P0P0);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getIntersection method, of class V3D_Ray.
     */
    @Test
    @Disabled
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P1P0P0);
        boolean flag = false;
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P0P0);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P0P0);
        V3D_Geometry result = instance.getIntersection(l, flag);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_LineSegment(P0P0P0, P1P1P1);
        result = instance.getIntersection(l, true);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_LineSegment(N1N1N1, N1N1P0);
        result = instance.getIntersection(l, true);
        assertNull(result);
        // Test 4
        l = new V3D_LineSegment(N1N1P0, P1P1P0);
        instance = new V3D_LineSegment(N1N1N1, P1P1P1);
        result = instance.getIntersection(l, true);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_LineSegment(P0P0P0, P1P0P0);
        instance = new V3D_LineSegment(N1P0P0, P1P0P0);
        result = instance.getIntersection(l, true);
        expResult = new V3D_LineSegment(P0P0P0, P1P0P0);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getLineOfIntersection method, of class V3D_Ray.
     */
    @Test
    @Disabled
    public void testGetLineOfIntersection() {
        System.out.println("getLineOfIntersection");
        V3D_Point pt = null;
        V3D_Ray instance = null;
        V3D_LineSegment expResult = null;
        V3D_LineSegment result = instance.getLineOfIntersection(pt);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPointOfIntersection method, of class V3D_Ray.
     */
    @Test
    @Disabled
    public void testGetPointOfIntersection() {
        System.out.println("getPointOfIntersection");
        V3D_Point pt = null;
        V3D_Ray instance = null;
        V3D_Point expResult = null;
        V3D_Point result = instance.getPointOfIntersection(pt);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDistance method, of class V3D_Ray.
     */
    @Test
    @Disabled
    public void testGetDistance_V3D_Point_int() {
        System.out.println("getDistance");
        V3D_Point p;
        int oom;
        V3D_Ray instance;
        BigDecimal expResult;
        BigDecimal result;
        // Test 1
        p = P0P0P0;
        oom = -1;
        instance = new V3D_Ray(P1P0P0, P1P1P0);
        expResult = BigDecimal.ONE;
        result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Ray.
     */
    @Test
    @Disabled
    public void testGetDistance_V3D_Ray_int() {
        System.out.println("getDistance");
        V3D_Ray p;
        int oom;
        V3D_Ray instance;
        BigDecimal expResult;
        BigDecimal result;
        // Test 1
        p = new V3D_Ray(P0P0P0, P1P1P1);
        oom = -1;
        instance = new V3D_Ray(P1P0P0, P1P1P0);
        expResult = BigDecimal.ONE;
        result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }
    
}
