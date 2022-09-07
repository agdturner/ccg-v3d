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
package uk.ac.leeds.ccg.v3d.geometry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.v3d.V3D_Test;

/**
 *
 * @author Andy Turner
 */
public class V3D_TrianglesCoplanarTest extends V3D_Test {
    
    public V3D_TrianglesCoplanarTest() {
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
     * Test of toString method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_TrianglesCoplanar instance = new V3D_TrianglesCoplanar(
                new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P1P0),
                new V3D_Triangle(pP0P0P0, pP2P2P0, pP0P2P0),
                new V3D_Triangle(pP0P0P0, pP0P2P0, pN1P2P0));
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        Object o = null;
        V3D_TrianglesCoplanar instance = null;
        boolean expResult = false;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        V3D_TrianglesCoplanar instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testEquals_V3D_TrianglesCoplanar_boolean() {
        System.out.println("equals");
        V3D_TrianglesCoplanar i = null;
        boolean b = false;
        V3D_TrianglesCoplanar instance = null;
        boolean expResult = false;
        boolean result = instance.equals(i, b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnvelope method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        V3D_TrianglesCoplanar instance = null;
        V3D_Envelope expResult = null;
        V3D_Envelope result = instance.getEnvelope();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGeometry method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testGetGeometry_3args() {
        System.out.println("getGeometry");
        V3D_Triangle t1 = null;
        V3D_Triangle t2 = null;
        int oom = 0;
        V3D_Geometry expResult = null;
        V3D_Geometry result = V3D_TrianglesCoplanar.getGeometry(t1, t2, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGeometry method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testGetGeometry_int_V3D_TriangleArr() {
        System.out.println("getGeometry");
        int oom = 0;
        V3D_Triangle[] triangles = null;
        V3D_Geometry expResult = null;
        V3D_Geometry result = V3D_TrianglesCoplanar.getGeometry(oom, triangles);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isIntersectedBy method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("isIntersectedBy");
        V3D_Point pt = null;
        int oom = 0;
        V3D_TrianglesCoplanar instance = null;
        boolean expResult = false;
        boolean result = instance.isIntersectedBy(pt, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isIntersectedBy0 method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testIsIntersectedBy0() {
        System.out.println("isIntersectedBy0");
        V3D_Point pt = null;
        int oom = 0;
        V3D_TrianglesCoplanar instance = null;
        boolean expResult = false;
        boolean result = instance.isIntersectedBy0(pt, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isIntersectedBy method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line_int() {
        System.out.println("isIntersectedBy");
        V3D_Line l = null;
        int oom = 0;
        V3D_TrianglesCoplanar instance = null;
        boolean expResult = false;
        boolean result = instance.isIntersectedBy(l, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isIntersectedBy method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_int() {
        System.out.println("isIntersectedBy");
        V3D_LineSegment l = null;
        int oom = 0;
        V3D_TrianglesCoplanar instance = null;
        boolean expResult = false;
        boolean result = instance.isIntersectedBy(l, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getArea method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        int oom = 0;
        V3D_TrianglesCoplanar instance = null;
        BigDecimal expResult = null;
        BigDecimal result = instance.getArea(oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPerimeter method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        int oom = 0;
        V3D_TrianglesCoplanar instance = null;
        BigDecimal expResult = null;
        BigDecimal result = instance.getPerimeter(oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersection");
        V3D_Line l = null;
        int oom = 0;
        V3D_TrianglesCoplanar instance = null;
        V3D_Geometry expResult = null;
        V3D_Geometry result = instance.getIntersection(l, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_int() {
        System.out.println("getIntersection");
        V3D_LineSegment l = null;
        int oom = 0;
        V3D_TrianglesCoplanar instance = null;
        V3D_Geometry expResult = null;
        V3D_Geometry result = instance.getIntersection(l, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testGetIntersection_V3D_Plane_int() {
        System.out.println("getIntersection");
        V3D_Plane p = null;
        int oom = 0;
        V3D_TrianglesCoplanar instance = null;
        V3D_Geometry expResult = null;
        V3D_Geometry result = instance.getIntersection(p, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testGetIntersection_V3D_Triangle_int() {
        System.out.println("getIntersection");
        V3D_Triangle t = null;
        int oom = 0;
        V3D_TrianglesCoplanar instance = null;
        V3D_Geometry expResult = null;
        V3D_Geometry result = instance.getIntersection(t, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rotate method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        V3D_Vector axisOfRotation = null;
        Math_BigRational theta = null;
        V3D_TrianglesCoplanar instance = null;
        instance.rotate(axisOfRotation, theta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPoints method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testGetRelPoints() {
        // No test.
    }

    /**
     * Test of getConvexHull method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testGetConvexHull() {
        System.out.println("getConvexHull");
        V3D_TrianglesCoplanar instance = new V3D_TrianglesCoplanar(
                new V3D_Triangle(pN2N2P0, pP0P1P0, pP1P1P0),
                new V3D_Triangle(pN2N2P0, pP2P2P0, pP2P0P0),
                new V3D_Triangle(pN2N2P0, pP2P0P0, pP2N1P0));
        ArrayList<V3D_Point> expResult = new ArrayList<>();
        expResult.add(pN2N2P0);
        expResult.add(pP2P2P0);
        expResult.add(pP2N1P0);
        expResult.add(pP0P1P0);
        ArrayList<V3D_Point> result = instance.getConvexHull();
        assertEquals(expResult, result);
    }

    /**
     * Test of isTriangle method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testIsTriangle() {
        System.out.println("isTriangle");
        V3D_TrianglesCoplanar instance = null;
        boolean expResult = false;
        boolean result = instance.isTriangle();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isRectangle method, of class V3D_TrianglesCoplanar.
     */
    @Test
    public void testIsRectangle() {
        System.out.println("isRectangle");
        V3D_TrianglesCoplanar instance = null;
        boolean expResult = false;
        boolean result = instance.isRectangle();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
