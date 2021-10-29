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
        int oom = -1;
        Object o = new V3D_Ray(P0P0P0, P1P1P1, oom);
        V3D_Ray instance = new V3D_Ray(P0P0P0, P1P1P1, oom);
        assertTrue(instance.equals(o));
        // Test 2
        instance = new V3D_Ray(P1P1P1, P2P2P2, oom);
        assertFalse(instance.equals(o));        
    }

    /**
     * Test of equals method, of class V3D_Ray.
     */
    @Test
    public void testEquals_V3D_Ray() {
        System.out.println("equals");
        int oom = -1;
        V3D_Ray r = new V3D_Ray(P0P0P0, P1P1P1, oom);
        V3D_Ray instance = new V3D_Ray(P0P0P0, P1P1P1, oom);
        assertTrue(instance.equals(r));
        // Test 2
        instance = new V3D_Ray(P1P1P1, P2P2P2, oom);
        assertFalse(instance.equals(r)); 
    }

    /**
     * Test of hashCode method, of class V3D_Ray.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int oom = -1;
        V3D_Ray e = new V3D_Ray(P0P0P0, P1P1P1, oom);
        int result = e.hashCode();
        int expResult = 17933661;
        assertTrue(result == expResult);
    }

    /**
     * Test of apply method, of class V3D_Ray.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Ray.
     */
    @Test
    public void testIsIntersectedBy_3args() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_Point p = P0P0P0;
        V3D_Ray instance = new V3D_Ray(N1N1N1, P1P1P1, oom);
        assertTrue(instance.isIntersectedBy(p, oom, true));
        // Test2
        p = P1P1P1;
        instance = new V3D_Ray(N1N1N1, P1P1P1, oom);
        assertTrue(instance.isIntersectedBy(p, oom, true));
        // Test3
        p = N2N2N2;
        instance = new V3D_Ray(N1N1N1, P1P1P1, oom);
        assertFalse(instance.isIntersectedBy(p, oom, true));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Ray.
     */
    @Test
    public void testIsIntersectedBy_V3D_Ray_boolean() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_Ray r = new V3D_Ray(P0P0P0, P1P0P0, oom);
        V3D_Ray instance = new V3D_Ray(P0P0P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(r, oom, false));
        // Test 2
        instance = new V3D_Ray(P0P0P0, P1P1P1, oom);
        assertTrue(instance.isIntersectedBy(r, oom, false));
        // Test 3
        instance = new V3D_Ray(N1N1N1, N1N1P0, oom);
        assertFalse(instance.isIntersectedBy(r, oom, false));
        // Test 4
        r = new V3D_Ray(N1N1P0, P1P1P0, oom);
        instance = new V3D_Ray(N1N1N1, P1P1P1, oom);
        instance.getIntersection(r, oom);
        assertTrue(instance.isIntersectedBy(r, oom, false));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Ray.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        V3D_Ray instance = new V3D_Ray(P0P0P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom, false));
        // Test 2
        instance = new V3D_Ray(P0P0P0, P1P1P1, oom);
        assertTrue(instance.isIntersectedBy(l, oom, false));
        // Test 3
        instance = new V3D_Ray(N1N1N1, N1N1P0, oom);
        assertFalse(instance.isIntersectedBy(l, oom, false));
        // Test 4
        l = new V3D_LineSegment(N1N1P0, P1P1P0, oom);
        instance = new V3D_Ray(N1N1N1, P1P1P1, oom);
        instance.getIntersection(l, oom);
        assertTrue(instance.isIntersectedBy(l, oom, false));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Ray.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_Line l = new V3D_Line(P0P0P0, P1P0P0, oom);
        V3D_Ray instance = new V3D_Ray(P0P0P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom));
        // Test 2
        instance = new V3D_Ray(P0P0P0, P1P1P1, oom);
        assertTrue(instance.isIntersectedBy(l, oom));
        // Test 3
        instance = new V3D_Ray(N1N1N1, N1N1P0, oom);
        assertFalse(instance.isIntersectedBy(l, oom));
        // Test 4
        l = new V3D_Ray(N1N1P0, P1P1P0, oom);
        instance = new V3D_Ray(N1N1N1, P1P1P1, oom);
        assertTrue(instance.isIntersectedBy(l, oom));
    }

    /**
     * Test of getIntersection method, of class V3D_Ray.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_Line l = new V3D_Line(P0P0P0, P1P0P0, oom);
        V3D_Ray instance = new V3D_Ray(P0P0P0, P1P0P0, oom);
        V3D_Geometry expResult = new V3D_Ray(P0P0P0, P1P0P0, oom);
        V3D_Geometry result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Ray(P0P0P0, P1P1P1, oom);
        result = instance.getIntersection(l, oom);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Ray(N1N1N1, N1N1P0, oom);
        result = instance.getIntersection(l, oom);
        assertNull(result);
        // Test 4
        l = new V3D_Line(N1N1P0, P1P1P0, oom);
        instance = new V3D_Ray(N1N1N1, P1P1P1, oom);
        result = instance.getIntersection(l, oom);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_Line(P0P0P0, P1P0P0, oom);
        instance = new V3D_Ray(N1P0P0, P1P0P0, oom);
        result = instance.getIntersection(l, oom);
        expResult = new V3D_Ray(N1P0P0, P1P0P0, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getIntersection method, of class V3D_Ray.
     */
    @Test
    public void testGetIntersection_V3D_Ray_boolean() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_Ray r = new V3D_Ray(P0P0P0, P1P0P0, oom);
        V3D_Ray instance = new V3D_Ray(P0P0P0, P1P0P0, oom);
        V3D_Geometry expResult = new V3D_Ray(P0P0P0, P1P0P0, oom);
        V3D_Geometry result = instance.getIntersection(r, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Ray(P0P0P0, P1P1P1, oom);
        result = instance.getIntersection(r, oom);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Ray(N1N1N1, N1N1P0, oom);
        result = instance.getIntersection(r, oom);
        assertNull(result);
        // Test 4
        r = new V3D_Ray(N1N1P0, P1P1P0, oom);
        instance = new V3D_Ray(N1N1N1, P1P1P1, oom);
        result = instance.getIntersection(r, oom);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 5
        r = new V3D_Ray(P0P0P0, P1P0P0, oom);
        instance = new V3D_Ray(N1P0P0, P1P0P0, oom);
        result = instance.getIntersection(r, oom);
        expResult = new V3D_Ray(P0P0P0, P1P0P0, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getIntersection method, of class V3D_Ray.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        boolean flag = false;
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        V3D_Geometry result = instance.getIntersection(l, oom, flag);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_LineSegment(P0P0P0, P1P1P1, oom);
        result = instance.getIntersection(l, oom, true);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_LineSegment(N1N1N1, N1N1P0, oom);
        result = instance.getIntersection(l, oom, true);
        assertNull(result);
        // Test 4
        l = new V3D_LineSegment(N1N1P0, P1P1P0, oom);
        instance = new V3D_LineSegment(N1N1N1, P1P1P1, oom);
        result = instance.getIntersection(l, oom, true);
        expResult = P0P0P0;
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        instance = new V3D_LineSegment(N1P0P0, P1P0P0, oom);
        result = instance.getIntersection(l, oom, true);
        expResult = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getLineOfIntersection method, of class V3D_Ray.
     */
    @Test
    public void testGetLineOfIntersection() {
        System.out.println("getLineOfIntersection");
        int oom = -1;
        V3D_Ray instance = new V3D_Ray(P0P0P0, P0P0P1, oom);
        V3D_Line l = new V3D_Line(P1P0P0, P1P1P0, oom);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        V3D_Geometry result = instance.getLineOfIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Ray(P0P0P0, P0P1P0, oom);
        result = instance.getLineOfIntersection(l, oom);
        assertNull(result);
    }

    /**
     * Test of getPointOfIntersection method, of class V3D_Ray.
     */
    @Test
    public void testGetPointOfIntersection() {
        System.out.println("getPointOfIntersection");
        int oom = -1;
        V3D_Point pt;
        V3D_Ray instance;
        V3D_LineSegment expResult;
        V3D_LineSegment result;
        // Test 1
        pt = P0P0P0;
        instance = new V3D_Ray(P1P0P0, P1P1P0, oom);
        expResult = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        result = instance.getLineOfIntersection(pt, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Ray(P1N1P0, P1P1P0, oom);
        expResult = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        result = instance.getLineOfIntersection(pt, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDistance method, of class V3D_Ray.
     */
    @Test
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
        instance = new V3D_Ray(P1P0P0, P1P1P0, oom);
        expResult = BigDecimal.ONE;
        result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Ray.
     */
    @Test
    public void testGetDistance_V3D_Ray_int() {
        System.out.println("getDistance");
        int oom = -1;
        V3D_Ray p;
        V3D_Ray instance;
        BigDecimal expResult;
        BigDecimal result;
        // Test 1
        p = new V3D_Ray(P0P0P0, P1P1P1, oom);
        oom = -1;
        instance = new V3D_Ray(P1P0P0, P1P1P0, oom);
        expResult = BigDecimal.ONE;
        result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }
    
}
