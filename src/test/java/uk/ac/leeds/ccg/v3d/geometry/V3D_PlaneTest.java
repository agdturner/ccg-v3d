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

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.V3D_Test;

/**
 * Test of V3D_Plane class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_PlaneTest extends V3D_Test {

    public V3D_PlaneTest() {
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
        V3D_Plane instance = new V3D_Plane(pP0P0P0, pP1P1P1, pP1P0P0);
        String expResult = "V3D_Plane\n"
                + "(\n"
                + " oom=-3\n"
                + " ,\n"
                + " offset=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " p=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " q=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " r=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + ")";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of toString method, of class V3D_Plane.
     */
    @Test
    public void testGetEquation() {
        System.out.println("getEquation");
        V3D_Plane instance;
        String expResult;
        String result;
        // Test 1
        instance = new V3D_Plane(pP0P0P0, pP1P1P1, pP1P0P0);
        expResult = "0 * x + 1 * y + -1 * z + 0 = 0";
        result = instance.getEquation();
        assertTrue(expResult.equalsIgnoreCase(result));
        // Test 2
        instance = new V3D_Plane(e, P1N2P1, new V3D_Vector(P4, N2, N2),
                new V3D_Vector(P4, P1, P4));
        expResult = "9 * x + -18 * y + 9 * z + -54 = 0";
        result = instance.getEquation();
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane.
     */
    @Test
    public void testIsOnPlane_V3D_LineSegment() {
        System.out.println("isOnPlane");
        // Test 1 to 9 lines segments in line with axes
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_Plane instance = V3D_Plane.X0;
        assertFalse(instance.isOnPlane(l, e.oom));
        // Test 2
        instance = V3D_Plane.Y0;
        assertTrue(instance.isOnPlane(l, e.oom));
        // Test 3
        instance = V3D_Plane.Z0;
        assertTrue(instance.isOnPlane(l, e.oom));
        // Test 4
        l = new V3D_LineSegment(pP0P0P0, pP0P1P0);
        instance = V3D_Plane.X0;
        assertTrue(instance.isOnPlane(l, e.oom));
        // Test 5
        instance = V3D_Plane.Y0;
        assertFalse(instance.isOnPlane(l, e.oom));
        // Test 6
        instance = V3D_Plane.Z0;
        assertTrue(instance.isOnPlane(l, e.oom));
        // Test 7
        l = new V3D_LineSegment(pP0P0P0, pP0P0P1);
        instance = V3D_Plane.X0;
        assertTrue(instance.isOnPlane(l, e.oom));
        // Test 8
        instance = V3D_Plane.Y0;
        assertTrue(instance.isOnPlane(l, e.oom));
        // Test 9
        instance = V3D_Plane.Z0;
        assertFalse(instance.isOnPlane(l, e.oom));
    }

    /**
     * Test of equals method, of class V3D_Plane.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        Object o = new V3D_Plane(pP0P1P0, pP1P1P1, pP1P0P0);
        V3D_Plane instance = new V3D_Plane(pP0P1P0, pP1P1P1, pP1P0P0);
        assertTrue(instance.equals(o));
        // Test 2
        instance = new V3D_Plane(pP1P1P0, pP1P1P1, pP1P0P0);
        assertFalse(instance.equals(o));
        // Test 3
        instance = new V3D_Plane(pP1P1P1, pP1P0P0, pP0P1P0);
        assertTrue(instance.equals(o));
        // Test 4
        instance = new V3D_Plane(pP1P0P0, pP0P1P0, pP1P1P1);
        assertTrue(instance.equals(o));
        // Test 5
        o = new V3D_Plane(pP0P0P0, pP1P0P0, pP0P1P0);
        instance = new V3D_Plane(pP0P0P0, pP2P0P0, pP0P2P0);
        assertTrue(instance.equals(o));
        // Test 6
        instance = new V3D_Triangle(pP0P0P0, pP2P0P0, pP0P2P0);
        assertFalse(instance.equals(o));
        assertTrue(o.equals(instance));
        // Test 7
        o = new V3D_Line(pP0P0P0, pP1P0P0);
        assertFalse(instance.equals(o));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsIntersectedBy_V3D_Plane_int() {
        System.out.println("intersects");
        V3D_Plane pl;
        V3D_Plane instance;
        // x=1
        pl = new V3D_Plane(pP1P0P0, pP1P1P0, pP1P0P1);
        instance = new V3D_Plane(pP1P0P0, pP1P1P0, pP1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(pP1P1P0, pP0P1P0, pP0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(pP1P0P1, pP0P1P1, pP0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        // y=1
        pl = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        // z=1
        pl = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        // x=0
        pl = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        // y=0
        pl = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        // z=0
        pl = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        // x=-1
        pl = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        // y=-1
        pl = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        // z=-1
        pl = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        // x=y
        pl = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        // x=y+1
        pl = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        // x=y-1
        pl = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        // x=z
        pl = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        // x=z+1
        pl = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        // x=z-1
        pl = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        // y=z
        pl = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        // y=z+1
        pl = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
//        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        // y=z-1
        pl = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P0P1); // x=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P1P1); // y=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1); // z=1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, N1P0P1); // x=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0N1P0, P0N1P1); // y=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0N1, P0P1N1, P0P0N1); // z=-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P1P1P1); // x=y
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0N1P0, P0N1P1); // x=y+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, P0P1P0, N1P0P1); // x=y-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1); // x=z
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P0P1); // x=z+1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, N1P0P0, N1P1P0, P0P0P1); // x=z-1
        assertTrue(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P0P0, P0P1P1, P1P1P1); // y=z
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1P1P0, P0P1P0, P0P2P1); // y=z+1
        assertFalse(instance.isIntersectedBy(pl, e.oom));
        instance = new V3D_Plane(e, P1N1P0, P0P1P2, P0P0P1); // y=z-1
//        assertTrue(instance.isIntersectedBy(pl, e.oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("intersects");
        V3D_Plane instance;
        // x=0
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P0P0P1);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N2, e.oom));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N2, e.oom));
        // P0
        assertTrue(instance.isIntersectedBy(pP0P2P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P2P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P2N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P1N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N1N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N2P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N2P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N2N2, e.oom));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N2, e.oom));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N2, e.oom));

        // y=0
        instance = new V3D_Plane(e, P1P0P0, P0P0P0, P0P0P1);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P0P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P0P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P0N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N2, e.oom));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P0P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P0P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P0N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N2, e.oom));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N2, e.oom));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P0P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P0P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P0N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N2, e.oom));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P0P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P0P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P0N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P1, e.oom));

        // z=0
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P0);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N2, e.oom));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N2, e.oom));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N2, e.oom));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N2, e.oom));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N2, e.oom));

        // x=y
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, P0P0P1);
        // P2
        assertTrue(instance.isIntersectedBy(pP2P2P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P2P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N2, e.oom));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P1N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N2, e.oom));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N2, e.oom));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N1N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N2, e.oom));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N2P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N2P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N2N2, e.oom));

        // x=-y
        instance = new V3D_Plane(e, P0P0P0, N1P1P0, P0P0P1);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N2P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N2P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N2N2, e.oom));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N1N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N2, e.oom));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N2, e.oom));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P1N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N2, e.oom));
        // N2
        assertTrue(instance.isIntersectedBy(pN2P2P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P2P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N2, e.oom));

        // x=z
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P1P0P1);
        // P2
        assertTrue(instance.isIntersectedBy(pP2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N2, e.oom));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N2, e.oom));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N2, e.oom));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N2, e.oom));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N2N2, e.oom));

        // x=-z
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, N1P0P1);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N2N2, e.oom));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N2, e.oom));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N2, e.oom));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N2, e.oom));
        // N2
        assertTrue(instance.isIntersectedBy(pN2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N2, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N2, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N2, e.oom));

        // y=z
        instance = new V3D_Plane(e, P1P0P0, P0P0P0, P0P1P1);
        // P2
        assertTrue(instance.isIntersectedBy(pP2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N2N2, e.oom));
        // P1
        assertTrue(instance.isIntersectedBy(pP1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N2N2, e.oom));
        // P0
        assertTrue(instance.isIntersectedBy(pP0P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N2N2, e.oom));
        // N1
        assertTrue(instance.isIntersectedBy(pN1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N2N2, e.oom));
        // N2
        assertTrue(instance.isIntersectedBy(pN2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N2N2, e.oom));

        // x=-z
        instance = new V3D_Plane(e, P1P0P0, P0P0P0, P0N1P1);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N2, e.oom));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N2, e.oom));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N2, e.oom));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N2, e.oom));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N2, e.oom));
        // x=y-z
        instance = new V3D_Plane(e, P0P0P0, P1P1P0, N1P0P1);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N2, e.oom));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N2, e.oom));
        // P0
        assertTrue(instance.isIntersectedBy(pP0P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N2N2, e.oom));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N2, e.oom));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N2, e.oom));
        // x=z-y
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P0);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N2, e.oom));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N2, e.oom));
        // P0
        assertTrue(instance.isIntersectedBy(pP0P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N2N2, e.oom));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N2, e.oom));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N2, e.oom));
        // y=x-z
        instance = new V3D_Plane(e, P1P1P0, P0P0P0, P0N1P1);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP2N2N2, e.oom));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1P0N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP1N2N2, e.oom));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P2N1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P1, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N1N2, e.oom));
        assertTrue(instance.isIntersectedBy(pP0N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pP0N2N2, e.oom));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P1N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN1P0N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P2, e.oom));
        assertTrue(instance.isIntersectedBy(pN1N2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN1N2N2, e.oom));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P2N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2P0N1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2P0N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N1N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N1N2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P2, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2P1, e.oom));
        assertTrue(instance.isIntersectedBy(pN2N2P0, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N1, e.oom));
        assertFalse(instance.isIntersectedBy(pN2N2N2, e.oom));
        // Test 2 from https://math.stackexchange.com/questions/2686606/equation-of-a-plane-passing-through-3-points        
        V3D_Vector n = new V3D_Vector(1, -2, 1);
        V3D_Point p = pP1N2P1;
        instance = new V3D_Plane(p, n);
        assertTrue(instance.isIntersectedBy(new V3D_Point(e, 4, -2, -2), e.oom));
        assertTrue(instance.isIntersectedBy(new V3D_Point(e, 4, 1, 4), e.oom));
        n = new V3D_Vector(1, -2, 1);
        p = new V3D_Point(e, 4, -2, -2);
        instance = new V3D_Plane(p, n);
        assertTrue(instance.isIntersectedBy(new V3D_Point(e, 1, -2, 1), e.oom));
        assertTrue(instance.isIntersectedBy(new V3D_Point(e, 4, 1, 4), e.oom));
        // Test 3
        n = new V3D_Vector(1, -2, 1);
        p = pP0P0P0;
        instance = new V3D_Plane(p, n);
        assertTrue(instance.isIntersectedBy(new V3D_Point(e, 3, 0, -3), e.oom));
        assertTrue(instance.isIntersectedBy(new V3D_Point(e, 3, 3, 3), e.oom));
        n = new V3D_Vector(1, -2, 1);
        p = new V3D_Point(e, 3, 0, -3);
        instance = new V3D_Plane(p, n);
        assertTrue(instance.isIntersectedBy(new V3D_Point(e, 0, 0, 0), e.oom));
        assertTrue(instance.isIntersectedBy(new V3D_Point(e, 3, 3, 3), e.oom));

    }

    /**
     * Test of hashCode method, of class V3D_Plane.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        V3D_Plane p = V3D_Plane.X0;
        int result = p.hashCode();
        int expResult = 336337519;
        //System.out.println(result);
        assertTrue(result == expResult);
    }

    /**
     * Test of getNormalVector method, of class V3D_Plane.
     */
    @Test
    public void testGetNormalVector() {
        System.out.println("getNormalVector");
        int oom = -1;
        // Z = 0
        V3D_Plane instance = new V3D_Plane(e, P0P0P0, P1P0P0, P0P1P0);
        V3D_Vector expResult = P0P0P1;
        V3D_Vector result = instance.getN(oom);
        assertTrue(expResult.equals(result));
        // Z = -1
        instance = new V3D_Plane(e, P0P0N1, P1P0N1, P0P1N1);
        expResult = P0P0P1;
        result = instance.getN(oom);
        assertTrue(expResult.equals(result));
        // Z = 1
        instance = new V3D_Plane(e, P0P0P1, P1P0P1, P0P1P1);
        expResult = P0P0P1;
        result = instance.getN(oom);
        assertTrue(expResult.equals(result));
        // Z = 1
        instance = new V3D_Plane(e, P1P0P1, P0P1P1, P0P0P1);
        expResult = P0P0P1;
        result = instance.getN(oom);
        assertTrue(expResult.equals(result));
        // Z = 1
        instance = new V3D_Plane(e, P0P1P1, P0P0P1, P1P0P1);
        //expResult = new V3D_Vector(P0P0N1);
        expResult = P0P0P1;
        result = instance.getN(oom);
        assertTrue(expResult.equals(result));
        // Y = 0
        instance = new V3D_Plane(e, P0P0P0, P0P1P0, P0P0N1);
        //expResult = new V3D_Vector(P1P0P0);
        expResult = N1P0P0;
        result = instance.getN(oom);
        assertTrue(expResult.equals(result));
        // X = 0
        instance = new V3D_Plane(e, P0P0P0, P1P0P0, P0P0N1);
        //expResult = new V3D_Vector(P0N1P0);
        expResult = P0P1P0;
        result = instance.getN(oom);
        assertTrue(expResult.equals(result));
        // Y = 0
        instance = new V3D_Plane(e, P0P0P0, P1P0P0, N1P0P1);
        expResult = P0N1P0;
        result = instance.getN(oom);
        assertTrue(expResult.equals(result));
        // 
        instance = new V3D_Plane(e, P0P1P0, P1P1P1, P1P0P0);
        //expResult = new V3D_Vector(N1N1P1);
        expResult = P1P1N1;
        result = instance.getN(oom);
        assertTrue(expResult.equals(result));
        // X = 0
        instance = new V3D_Plane(e, P0P0P0, P0P1P1, P0N1P0);
        //expResult = new V3D_Vector(N1P0P0);
        expResult = P1P0P0;
        result = instance.getN(oom);
        assertTrue(expResult.equals(result));
        // 
        instance = new V3D_Plane(e, P0P0P0, P1P1P1, P0N1N1);
        //expResult = new V3D_Vector(P0N1P1);
        expResult = P0P1N1;
        result = instance.getN(oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line_int() {
        System.out.println("intersects");
        V3D_Line l = new V3D_Line(pP0P0P0, pP0P1P0); // Y axis
        V3D_Plane instance = new V3D_Plane(e, P1P0P1, P0P0P1, N1P0N1); // Y=0 plane
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 2
        l = new V3D_Line(pN1N1N1, pP1P1P1);
        instance = new V3D_Plane(e, P1P1N1, P1N1N1, N1P1N1); // z=-1 plane
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 2
        l = new V3D_Line(pP0P0P0, pP1P1P1);
        instance = new V3D_Plane(e, P1P1N1, P1N1N1, N1P1N1); // z=-2 plane
        assertTrue(instance.isIntersectedBy(l, e.oom));
    }

    /**
     * Test of isParallel method, of class V3D_Plane.
     */
    @Test
    public void testIsParallel_V3D_Plane_int() {
        System.out.println("isParallel");
        V3D_Plane p = new V3D_Plane(e, P1P1P0, P1N1P0, N1P1P0);
        V3D_Plane instance = new V3D_Plane(e, P1P1P1, P1N1P1, N1P1P1);
        assertTrue(instance.isParallel(p, e.oom));
        // Test 2
        instance = new V3D_Plane(e, P1P1N1, P1N1N1, N1P1N1);
        assertTrue(instance.isParallel(p, e.oom));
        // Test 3
        instance = new V3D_Plane(e, P1P1N1, P1N1N1, N1P1N1);
        assertTrue(instance.isParallel(p, e.oom));
        // Test 4
        p = V3D_Plane.X0;
        instance = V3D_Plane.Y0;
        assertFalse(instance.isParallel(p, e.oom));
        // Test 5
        p = V3D_Plane.X0;
        instance = V3D_Plane.Z0;
        assertFalse(instance.isParallel(p, e.oom));
        // Test 6
        p = V3D_Plane.Y0;
        instance = V3D_Plane.Z0;
        assertFalse(instance.isParallel(p, e.oom));
        // Test 7
        p = new V3D_Plane(e, P0P0P0, P0P1P0, P1P1P1);
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P2P1P1);
        assertTrue(instance.isParallel(p, e.oom));
        // Test 8
        instance = new V3D_Plane(e, P1N1P0, P1P0P0, P2P0P1);
        assertTrue(instance.isParallel(p, e.oom));
        // Test 9
        instance = new V3D_Plane(e, P1P0P0, P1P1P0, P1P1P1);
        assertFalse(instance.isParallel(p, e.oom));
    }

    /**
     * Test of isParallel method, of class V3D_Plane.
     */
    @Test
    public void testIsParallel_V3D_Line_int() {
        System.out.println("isParallel");
        V3D_Line l = V3D_Line.X_AXIS;
        V3D_Plane instance = V3D_Plane.Y0;
        assertTrue(instance.isParallel(l, e.oom));
        // Test 2
        instance = V3D_Plane.Z0;
        assertTrue(instance.isParallel(l, e.oom));
        // Test 3
        instance = V3D_Plane.X0;
        assertFalse(instance.isParallel(l, e.oom));
        // Test 4
        l = V3D_Line.Y_AXIS;
        instance = V3D_Plane.X0;
        assertTrue(instance.isParallel(l, e.oom));
        // Test 5
        instance = V3D_Plane.Y0;
        assertFalse(instance.isParallel(l, e.oom));
        // Test 6
        instance = V3D_Plane.Z0;
        assertTrue(instance.isParallel(l, e.oom));
        // Test 7
        l = V3D_Line.Z_AXIS;
        instance = V3D_Plane.X0;
        assertTrue(instance.isParallel(l, e.oom));
        // Test 8
        instance = V3D_Plane.Y0;
        assertTrue(instance.isParallel(l, e.oom));
        // Test 9
        instance = V3D_Plane.Z0;
        assertFalse(instance.isParallel(l, e.oom));
    }

    /**
     * Test of getIntersection method, of class V3D_Plane. The following can be
     * used for creating test cases:
     * http://www.ambrsoft.com/TrigoCalc/Plan3D/Plane3D_.htm
     */
    @Test
    public void testGetIntersection_V3D_Plane_int() {
        System.out.println("getIntersection");
        V3D_Plane pl;
        V3D_Plane instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        /**
         * The following is from:
         * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
         * Two Planar patches.
         */
        e.oom = e.oom - 5; // Why 5 extra places of precision works? 
        pl = new V3D_Plane(
                new V3D_Point(e,
                        Math_BigRational.valueOf(8).divide(3),
                        Math_BigRational.valueOf(-2).divide(3),
                        Math_BigRational.ZERO),
                new V3D_Vector(2, 8, 0));
        //new V3D_Vector(2, 8, 0), oomN5);
        instance = new V3D_Plane(
                new V3D_Point(e,
                        Math_BigRational.valueOf(8).divide(3),
                        Math_BigRational.ZERO,
                        Math_BigRational.valueOf(-2).divide(3)),
                new V3D_Vector(2, 0, 8));
        //new V3D_Vector(2, 0, 8), oomN5);
        expResult = new V3D_Line(e,
                new V3D_Vector(
                        Math_BigRational.valueOf(68).divide(27),
                        Math_BigRational.valueOf(-17).divide(27),
                        Math_BigRational.valueOf(-17).divide(27)),
                new V3D_Vector(
                        Math_BigRational.valueOf(1).divide(4),
                        Math_BigRational.valueOf(-1).divide(16),
                        Math_BigRational.valueOf(-1).divide(16)));
        //, oomN5);
        result = instance.getIntersection(pl, e.oom);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result));
        assertTrue(expResult.equals(result));
        /**
         * The following is from:
         * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
         * Simple. Something is not right with this test which had worked at
         * some stage... :(
         */
        e.oom = -8;
        pl = new V3D_Plane(new V3D_Point(e, 7, 11, 0), new V3D_Vector(0, 0, 3));
        instance = new V3D_Plane(new V3D_Point(e, 1, 0, 0), new V3D_Vector(5, 5, 0));
        V3D_Point p2 = new V3D_Point(e, 0.5d, 0.5d, 0);
        assertTrue(V3D_Plane.isCoplanar(e, pl, p2));
        V3D_Vector v2 = new V3D_Vector(-15, 15, 0);
        //assertTrue(V3D_Geometrics.isCoplanar(oom, pl, p2.apply(v2, oom)));
        assertTrue(V3D_Plane.isCoplanar(e, pl, new V3D_Point(e, p2.offset, p2.rel.add(v2, e.oom))));
        //assertTrue(V3D_Geometrics.isCoplanar(oom, pl, new V3D_Point(p2.offset.add(v2, oom), p2.rel)));

        //expResult = new V3D_Line(p2, v2, oom);
        expResult = new V3D_Line(e, p2.getVector(e.oom), v2);
        //expResult = new V3D_Line(p2.offset, p2.rel, v2, oom);

        result = instance.getIntersection(pl, e.oom);
        //System.out.println(result);
        //System.out.println(expResult);
        result = instance.getIntersection(pl, e.oom);
        //assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, e.oom));
        //assertTrue(expResult.equals(result));
        // Test V3D_Plane.X0
        pl = V3D_Plane.X0;
        // Test 1
        e.oom = V3D_Environment.DEFAULT_OOM;
        instance = V3D_Plane.X0;
        expResult = V3D_Plane.X0;
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = V3D_Plane.Y0;
        expResult = V3D_Line.Z_AXIS;
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = V3D_Plane.Z0;
        expResult = V3D_Line.Y_AXIS;
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 4
        instance = new V3D_Plane(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_Line(pP0P1P0, pP0P1P1);    // x=0, y=1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 5
        instance = new V3D_Plane(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_Line(pP0P1P1, pP0P0P1);    // x=0, z=1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 6 to 9
        pl = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        // Test 6
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        expResult = new V3D_Line(pP0P0N1, pP0P0P1);          // x=0, y=0
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 7
        instance = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        expResult = new V3D_Line(pP0P0P0, pP1P0P0);          // y=0, z=0
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 8
        instance = new V3D_Plane(pP1P0P0, pP1P1P0, pP1P0P1);       // x=1
        expResult = new V3D_Line(pP1P0N1, pP1P0P1);          // x=1, y=0
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 9
        instance = new V3D_Plane(pP0P1P1, pP1P1P1, pP0P0P1);       // z=1
        expResult = new V3D_Line(pP0P0P1, pP1P0P1);          // y=0, z=1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 10 to 13
        pl = new V3D_Plane(e, V3D_Vector.I, V3D_Vector.J, P0P0P0); // z=0
        // Test 10
        instance = new V3D_Plane(e, P0P0P0, V3D_Vector.J, V3D_Vector.K); // x=0
        expResult = new V3D_Line(pP0N1P0, pP0P1P0);          // x=0, z=0
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 11
        instance = new V3D_Plane(e, V3D_Vector.I, P0P0P0, V3D_Vector.K); // y=0
        expResult = new V3D_Line(pN1P0P0, pP1P0P0);          // y=0, z=0
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 12
        instance = new V3D_Plane(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_Line(pP1N1P0, pP1P1P0);    // x=1, z=0
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 13
        instance = new V3D_Plane(pP1P1P1, pP0P1P0, pP1P1P0); // y=1
        expResult = new V3D_Line(pN1P1P0, pP1P1P0);    // y=1, z=0
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 14 to 15
        pl = new V3D_Plane(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        // Test 14
        instance = new V3D_Plane(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_Line(pP1P1P0, pP1P1P1);    // x=1, y=1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 15
        instance = new V3D_Plane(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_Line(pP1P1P1, pP1P0P1);    // x=1, z=1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 16 to 17
        pl = new V3D_Plane(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        // Test 16
        instance = new V3D_Plane(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_Line(pP1P1P0, pP1P1P1);    // x=1, y=1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 17
        instance = new V3D_Plane(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_Line(pP1P1P1, pP0P1P1);    // y=1, z=1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 18 to 19
        pl = new V3D_Plane(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        // Test 18
        instance = new V3D_Plane(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_Line(pP1P0P1, pP1P1P1);    // x=1, z=1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 19
        instance = new V3D_Plane(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_Line(pP1P1P1, pP0P1P1);    // y=1, z=1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 20 to 21
        pl = new V3D_Plane(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        // Test 20
        instance = new V3D_Plane(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_Line(pN1P1P0, pN1P1P1);    // x=-1, y=1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 21
        instance = new V3D_Plane(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_Line(pN1P1P1, pN1P0P1);    // x=-1, z=1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 22 to 23
        pl = new V3D_Plane(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        // Test 22
        instance = new V3D_Plane(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_Line(pP1N1P0, pP1N1P1);    // x=1, y=-1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 23
        instance = new V3D_Plane(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_Line(pP1N1P1, pP0N1P1);    // y=-1, z=1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 24 to 25
        pl = new V3D_Plane(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        // Test 24
        instance = new V3D_Plane(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_Line(pP1P0N1, pP1P1N1);    // x=1, z=-1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 25
        instance = new V3D_Plane(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_Line(pP1P1N1, pP0P1N1);    // y=1, z=-1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 26 to 27
        pl = new V3D_Plane(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        // Test 26
        instance = new V3D_Plane(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        expResult = new V3D_Line(pN1N1P0, pN1N1P1);    // x=-1, y=-1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 27
        instance = new V3D_Plane(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        expResult = new V3D_Line(pN1P1N1, pN1P0N1);    // x=-1, z=-1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 28 to 29
        pl = new V3D_Plane(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        // Test 28
        instance = new V3D_Plane(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        expResult = new V3D_Line(pN1N1P0, pN1N1P1);    // x=-1, y=-1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 29
        instance = new V3D_Plane(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        expResult = new V3D_Line(pP1N1N1, pP0N1N1);    // y=-1, z=-1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 30 to 31
        pl = new V3D_Plane(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        // Test 30
        instance = new V3D_Plane(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        expResult = new V3D_Line(pN1P0N1, pN1P1N1);    // x=-1, z=-1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
        // Test 31
        instance = new V3D_Plane(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        expResult = new V3D_Line(pP1N1N1, pP0N1N1);    // y=-1, z=-1
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));

        // Test 32 to ?
        pl = new V3D_Plane(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        // Test 32
        instance = new V3D_Plane(pN1N2N1, pP0N2P0, pP1N2N1); // y=-2
        expResult = new V3D_Line(pN1N2P0, pN1N2P1);    // x=-1, y=-2
        result = instance.getIntersection(pl, e.oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getIntersection method, of class V3D_Plane.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_Line l;
        V3D_Plane instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1-3 axis with orthoganol plane through origin.
        // Test 1
        l = V3D_Line.X_AXIS;
        instance = V3D_Plane.X0;
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        l = V3D_Line.Y_AXIS;
        instance = V3D_Plane.Y0;
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 3
        l = V3D_Line.Z_AXIS;
        instance = V3D_Plane.Z0;
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 4-6 axis with orthoganol plane not through origin.
        // Test 4
        l = V3D_Line.X_AXIS;
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.apply(oom, P1P0P0);
        expResult = pP1P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 5
        l = V3D_Line.Y_AXIS;
        instance = new V3D_Plane(V3D_Plane.Y0);
        instance.apply(oom, P0P1P0);
        expResult = pP0P1P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 6
        l = V3D_Line.Z_AXIS;
        instance = new V3D_Plane(V3D_Plane.Z0);
        instance.apply(oom, P0P0P1);
        expResult = pP0P0P1;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 7
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_Line(e, V3D_Vector.ZERO, P0P2P0, new V3D_Vector(P1, P5, P1));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(e, P0P0N1, new V3D_Vector(P0, P4, P0), P2P0P0);
        // (2, 8, 2)
        expResult = new V3D_Point(e, P2, P8, P2);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 9
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_Line(e, P0P2P0, new V3D_Vector(P1, P5, P1));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(e, new V3D_Vector(P0, P0, N1),
                new V3D_Vector(P0, P4, P0), new V3D_Vector(P2, P0, P0));
        // (2, 8, 2)
        expResult = new V3D_Point(e, P2, P8, P2);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 10
        // line
        // x = 0, y = 0, z = t
        // points (0, 0, 0), (0, 0, 1) 
        l = new V3D_Line(pP0P0P0, pP0P0P1);
        // plane
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(e, P0P0P2, P1P0P2, P0P1P2);
        expResult = pP0P0P2;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of apply method, of class V3D_Plane.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane.
     */
    @Test
    public void testIsOnPlane() {
        System.out.println("isOnPlane");
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0);
        V3D_Plane instance = new V3D_Plane(e, P0P0P0, P1P0P0, P1P1P0);
        assertTrue(instance.isOnPlane(l, e.oom));
        // Test 2
        l = new V3D_Line(pP0P0P0, pP1P1P0);
        assertTrue(instance.isOnPlane(l, e.oom));
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
    public void testGetIntersection_V3D_LineSegment_int() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_LineSegment l;
        V3D_Plane instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1-3 part of axis with orthoganol plane through origin.
        // Test 1
        l = new V3D_LineSegment(pN1P0P0, pP1P0P0);
        instance = V3D_Plane.X0;
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        l = new V3D_LineSegment(pP0N1P0, pP0P1P0);
        instance = V3D_Plane.Y0;
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 3
        l = new V3D_LineSegment(pP0P0N1, pP0P0P1);
        instance = V3D_Plane.Z0;
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 4-6 part of axis with orthoganol plane not through origin.
        // Test 4
        l = new V3D_LineSegment(pN1P0P0, pP1P0P0);
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.apply(oom, P1P0P0);
        expResult = pP1P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_LineSegment(pP0N1P0, pP0P1P0);
        instance = new V3D_Plane(V3D_Plane.Y0);
        instance.apply(oom, P0P1P0);
        expResult = pP0P1P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 6
        l = new V3D_LineSegment(pP0P0N1, pP0P0P1);
        instance = new V3D_Plane(V3D_Plane.Z0);
        instance.apply(oom, P0P0P1);
        expResult = pP0P0P1;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 7
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_LineSegment(e, P0P2P0, new V3D_Vector(P1, P5, P1));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(e, P0P0N1, new V3D_Vector(P0, P4, P0),
                new V3D_Vector(P2, P0, P0));
        // (2, 8, 2)
        expResult = new V3D_Point(e, P2, P8, P2);
        result = instance.getIntersection(l, oom);
        assertNotEquals(expResult, result);
        l = new V3D_LineSegment(e, P0P2P0, new V3D_Vector(P2, P8, P2));
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 9
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_LineSegment(e, P0P2P0, new V3D_Vector(P1, P5, P1));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(e, P0P0N1, new V3D_Vector(P0, P4, P0), P2P0P0);
        // (2, 8, 2)
        expResult = new V3D_Point(e, P2, P8, P2);
        result = instance.getIntersection(l, oom);
        assertNotEquals(expResult, result);
        l = new V3D_LineSegment(e, P0P2P0, new V3D_Vector(P2, P8, P2));
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 10
        // line
        // x = 0, y = 0, z = t
        // points (0, 0, 0), (0, 0, 1) 
        l = new V3D_LineSegment(pP0P0P0, pP0P0P1);
        // plane
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(e, P0P0P2, P1P0P2, P0P1P2);
        expResult = pP0P0P2;
        result = instance.getIntersection(l, oom);
        assertNotEquals(expResult, result);
        l = new V3D_LineSegment(e, P0P0P0, new V3D_Vector(P0, P0, P4));
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));

    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        // No test.
    }

    /**
     * Test of getAsMatrix method, of class V3D_Plane.
     */
    @Test
    public void testGetAsMatrix() {
        System.out.println("getAsMatrix");
        V3D_Plane instance = V3D_Plane.X0;
        Math_BigRational[][] m = new Math_BigRational[3][3];
        m[0][0] = Math_BigRational.ZERO;
        m[0][1] = Math_BigRational.ZERO;
        m[0][2] = Math_BigRational.ZERO;
        m[1][0] = Math_BigRational.ZERO;
        m[1][1] = Math_BigRational.ONE;
        m[1][2] = Math_BigRational.ZERO;
        m[2][0] = Math_BigRational.ZERO;
        m[2][1] = Math_BigRational.ZERO;
        m[2][2] = Math_BigRational.ONE;
        Math_Matrix_BR expResult = new Math_Matrix_BR(m);
        Math_Matrix_BR result = instance.getAsMatrix();
        assertTrue(expResult.getRows().length == result.getRows().length);
        assertTrue(expResult.getCols().length == result.getCols().length);
        for (int i = 0; i < expResult.getRows().length; i++) {
            for (int j = 0; j < expResult.getCols().length; j++) {
//                if (expResult.getRows()[i][j].compareTo(result.getRows()[i][j]) != 0) {
//                    int debug = 1;
//                }
                assertTrue(expResult.getRows()[i][j].compareTo(result.getRows()[i][j]) == 0);
            }
        }
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane.
     */
    @Test
    public void testGetDistanceSquared() {
        System.out.println("getDistanceSquared");
        int oom = -2;
        V3D_Plane p = V3D_Plane.X0;
        V3D_Plane instance = V3D_Plane.X0;
        Math_BigRational expResult = Math_BigRational.ZERO;
        Math_BigRational result = instance.getDistanceSquared(p, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        V3D_Vector v = V3D_Vector.I;
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.apply(oom, v);
        expResult = Math_BigRational.ONE;
        result = instance.getDistanceSquared(p, oom);
        assertTrue(expResult.equals(result));
        // Test 3
        v = V3D_Vector.J;
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.apply(oom, v);
        expResult = Math_BigRational.ZERO;
        result = instance.getDistanceSquared(p, oom);
        assertTrue(expResult.equals(result));
        // Test 4
        v = V3D_Vector.K;
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.apply(oom, v);
        expResult = Math_BigRational.ZERO;
        result = instance.getDistanceSquared(p, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class V3D_Plane.
     */
    @Test
    public void testEquals_V3D_Plane() {
        System.out.println("equals");
        V3D_Plane p;
        V3D_Plane instance;
        // Test 1
        p = V3D_Plane.X0;
        instance = V3D_Plane.X0;
        assertTrue(p.equals(instance));
        // Test 2
        p = new V3D_Plane(e, P0P1P0, P1P1P1, P1P0P0);
        instance = new V3D_Plane(e, P0P1P0, P1P1P1, P1P0P0);
        assertTrue(instance.equals(p));
        instance = new V3D_Plane(e, P1P1P0, P1P1P1, P1P0P0);
        assertFalse(instance.equals(p));
        instance = new V3D_Plane(e, P1P1P1, P1P0P0, P0P1P0);
        assertTrue(instance.equals(p));
        instance = new V3D_Plane(e, P1P0P0, P0P1P0, P1P1P1);
        assertTrue(instance.equals(p));
        // Test 3
        p = new V3D_Plane(e, P0P0P0, P1P0P0, P0P1P0);
        instance = new V3D_Plane(e, P0P0P0, P2P0P0, P0P2P0);
        assertTrue(instance.equals(p));
        // Test 6
        instance = new V3D_Triangle(e, P0P0P0, P2P0P0, P0P2P0);
        assertTrue(instance.equals(p));
    }

    /**
     * Test of isCoincident method, of class V3D_Plane.
     */
    @Test
    public void testIsCoincident() {
        // No test as covered by other tests.
    }

    /**
     * Test of getDistance method, of class V3D_Plane.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        System.out.println("getDistance");
        V3D_Point p = new V3D_Point(e, P5, P0, P0);
        int oom = 0;
        V3D_Plane instance = new V3D_Plane(e, P0P0P0, P0P0P1, P0P1P1);
        BigDecimal expResult = BigDecimal.valueOf(5);
        BigDecimal result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        p = new V3D_Point(e, P5, P10, P0);
        result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Plane.
     */
    @Test
    public void testGetDistance_V3D_Line_int() {
        System.out.println("getDistance");
        V3D_Line l = new V3D_Line(e, new V3D_Vector(P10, P1, P1),
                new V3D_Vector(P100, P1, P1));
        V3D_Plane instance = V3D_Plane.X0;
        BigDecimal expResult = BigDecimal.TEN;
        BigDecimal result = instance.getDistance(l, e.oom);
        assertFalse(expResult.compareTo(result) == 0);
        l = new V3D_Line(e, new V3D_Vector(P10, P1, P1),
                new V3D_Vector(P10, P0, P1));
        expResult = BigDecimal.TEN;
        result = instance.getDistance(l, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Plane.
     */
    @Test
    public void testGetDistance_V3D_LineSegment_int() {
        System.out.println("getDistance");
        V3D_LineSegment l = new V3D_LineSegment(e, new V3D_Vector(P10, P1, P1),
                new V3D_Vector(P100, P1, P1));
        V3D_Plane instance = V3D_Plane.X0;
        BigDecimal expResult = BigDecimal.TEN;
        BigDecimal result = instance.getDistance(l, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of toString method, of class V3D_Plane.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        V3D_Plane instance = V3D_Plane.X0;
        String expResult = "V3D_Plane\n"
                + "(\n"
                + " oom=-3\n"
                + " ,\n"
                + " offset=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " p=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " q=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " r=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0)\n"
                + " )\n"
                + ")";
        String result = instance.toString();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class V3D_Plane.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V3D_Plane instance = V3D_Plane.X0;
        String expResult = "V3D_Plane\n"
                + "(\n"
                + " oom=-3\n"
                + " ,\n"
                + " offset=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " p=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " q=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " r=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0)\n"
                + " )\n"
                + ")";
        String result = instance.toString(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toStringFields method, of class V3D_Plane.
     */
    @Test
    public void testToStringFields() {
        System.out.println("toStringFields");
        String pad = "";
        V3D_Plane instance = V3D_Plane.X0;
        String expResult = "oom=-3\n"
                + ",\n"
                + "offset=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + ")\n"
                + ",\n"
                + "p=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + ")\n"
                + ",\n"
                + "q=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + ")\n"
                + ",\n"
                + "r=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0)\n"
                + ")";
        String result = instance.toStringFields(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPV method, of class V3D_Plane.
     */
    @Test
    public void testGetPV() {
        System.out.println("getPV");
        V3D_Plane instance = V3D_Plane.X0;
        //int oom = -3;
        V3D_Vector expResult = V3D_Vector.ZERO;
        V3D_Vector result = instance.getPV();
        //System.out.println(result);
        assertEquals(expResult, result);
        // Test 2
        instance = V3D_Plane.Y0;
        expResult = V3D_Vector.ZERO;
        result = instance.getPV();
        //System.out.println(result);
        assertEquals(expResult, result);
        // Test 3
        instance = V3D_Plane.Z0;
        expResult = V3D_Vector.ZERO;
        result = instance.getPV();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getP method, of class V3D_Plane.
     */
    @Test
    public void testGetP() {
        System.out.println("getP");
        V3D_Plane instance = V3D_Plane.X0;
        V3D_Point expResult = V3D_Point.ORIGIN;
        V3D_Point result = instance.getP();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getQV method, of class V3D_Plane.
     */
    @Test
    public void testGetQV() {
        System.out.println("getQV");
        V3D_Plane instance = V3D_Plane.X0;
        //int oom = -3;
        V3D_Vector expResult = V3D_Vector.J;
        V3D_Vector result = instance.getQV();
        //System.out.println(result);
        assertEquals(expResult, result);
        // Test 2
        instance = V3D_Plane.Y0;
        expResult = V3D_Vector.I;
        result = instance.getQV();
        //System.out.println(result);
        assertEquals(expResult, result);
        // Test 3
        instance = V3D_Plane.Z0;
        expResult = V3D_Vector.I;
        result = instance.getQV();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getQ method, of class V3D_Plane.
     */
    @Test
    public void testGetQ() {
        System.out.println("getQ");
        V3D_Plane instance = V3D_Plane.X0;
        V3D_Point expResult = pP0P1P0;
        V3D_Point result = instance.getQ();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getRV method, of class V3D_Plane.
     */
    @Test
    public void testGetRV() {
        System.out.println("getRV");
        V3D_Plane instance = V3D_Plane.X0;
        //int oom = -3;
        V3D_Vector expResult = V3D_Vector.K;
        V3D_Vector result = instance.getRV();
        //System.out.println(result);
        assertEquals(expResult, result);
        // Test 2
        instance = V3D_Plane.Y0;
        expResult = V3D_Vector.K;
        result = instance.getRV();
        //System.out.println(result);
        assertEquals(expResult, result);
        // Test 3
        instance = V3D_Plane.Z0;
        expResult = V3D_Vector.J;
        result = instance.getRV();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getR method, of class V3D_Plane.
     */
    @Test
    public void testGetR() {
        System.out.println("getR");
        V3D_Plane instance = V3D_Plane.X0;
        V3D_Point expResult = pP0P0P1;
        V3D_Point result = instance.getR();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPQV method, of class V3D_Plane.
     */
    @Test
    public void testGetPQV() {
        System.out.println("getPQV");
        V3D_Plane instance = V3D_Plane.X0;
        V3D_Vector expResult = P0P1P0;
        V3D_Vector result = instance.getPQV();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getQRV method, of class V3D_Plane.
     */
    @Test
    public void testGetQRV() {
        System.out.println("getQRV");
        V3D_Plane instance = V3D_Plane.X0;
        V3D_Vector expResult = P0N1P1;
        V3D_Vector result = instance.getQRV();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getRPV method, of class V3D_Plane.
     */
    @Test
    public void testGetRPV() {
        System.out.println("getRPV");
        V3D_Plane instance = V3D_Plane.X0;
        V3D_Vector expResult = P0P0N1;
        V3D_Vector result = instance.getRPV();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPQ method, of class V3D_Plane.
     */
    @Test
    public void testGetPQ() {
        System.out.println("getPQ");
        V3D_Plane instance = V3D_Plane.X0;
        V3D_LineSegment expResult = new V3D_LineSegment(pP0P0P0, pP0P1P0);
        V3D_LineSegment result = instance.getPQ();
        //System.out.println(result.toString());
        assertEquals(expResult, result);
    }

    /**
     * Test of getQR method, of class V3D_Plane.
     */
    @Test
    public void testGetQR() {
        System.out.println("getQR");
        V3D_Plane instance = V3D_Plane.X0;
        V3D_LineSegment expResult = new V3D_LineSegment(pP0P1P0, pP0P0P1);
        V3D_LineSegment result = instance.getQR();
        //System.out.println(result.toString());
        assertEquals(expResult, result);
    }

    /**
     * Test of getRP method, of class V3D_Plane.
     */
    @Test
    public void testGetRP() {
        System.out.println("getRP");
        V3D_Plane instance = V3D_Plane.X0;
        V3D_LineSegment expResult = new V3D_LineSegment(pP0P0P1, pP0P0P0);
        V3D_LineSegment result = instance.getRP();
        //System.out.println(result.toString());
        assertEquals(expResult, result);
    }

    /**
     * Test of isQAtOrigin method, of class V3D_Plane.
     */
    @Test
    public void testIsQAtOrigin() {
        System.out.println("isQAtOrigin");
        V3D_Plane instance = V3D_Plane.X0;
        assertFalse(instance.isQAtOrigin());
        // Test 2
        instance = new V3D_Plane(e, P2P2P2, P0P0P0, P1P1P0);
        assertTrue(instance.isQAtOrigin());
    }

    /**
     * Test of getN method, of class V3D_Plane.
     */
    @Test
    public void testGetN() {
        System.out.println("getN");
        V3D_Plane instance = V3D_Plane.X0;
        V3D_Vector expResult = V3D_Vector.I;
        V3D_Vector result = instance.getN(e.oom);
        assertEquals(expResult, result);
        // Test 2
        instance = V3D_Plane.Y0;
        expResult = V3D_Vector.J.reverse();
        result = instance.getN(e.oom);
        assertEquals(expResult, result);
        // Test 2
        instance = V3D_Plane.Z0;
        expResult = V3D_Vector.K;
        result = instance.getN(e.oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getEquationCoefficients method, of class V3D_Plane.
     */
    @Test
    public void testGetEquationCoefficients() {
        System.out.println("getEquationCoefficients");
        V3D_Plane instance = instance = new V3D_Plane(e, P0P0P0, P1P1P1, P1P0P0);
        Math_BigRational[] expResult = new Math_BigRational[4];
        expResult[0] = Math_BigRational.ZERO;
        expResult[1] = Math_BigRational.ONE;
        expResult[2] = Math_BigRational.ONE.negate();
        expResult[3] = Math_BigRational.ZERO;
        Math_BigRational[] result = instance.getEquationCoefficients();
//        for (int i = 0; i < result.length; i ++) {
//            System.out.println(i + " " + result[i].toRationalString());
//        }
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of rotate method, of class V3D_Plane.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        int oomt = e.oom - 2;
        Math_BigRational Pi = Math_BigRational.valueOf(
                new Math_BigDecimal().getPi(oomt, RoundingMode.HALF_UP));
        V3D_Vector axisOfRotation = V3D_Vector.I;
        Math_BigRational theta = Pi.divide(2);
        V3D_Plane instance = new V3D_Plane(V3D_Plane.X0);
        instance.rotate(axisOfRotation, theta);
        assertEquals(V3D_Plane.X0, instance);
        // Test 2
        axisOfRotation = V3D_Vector.J;
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.rotate(axisOfRotation, theta);
        //System.out.println(instance.toString());
        assertEquals(V3D_Plane.Z0, instance);
        // Test 3
        axisOfRotation = V3D_Vector.K;
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.rotate(axisOfRotation, theta);
        //System.out.println(instance.toString());
        assertEquals(V3D_Plane.Y0, instance);
        // Test 4
        axisOfRotation = V3D_Vector.I;
        theta = Pi;
        instance = new V3D_Plane(e, P1P0P0, P0P0P0, P0P2P0, P0P2P2);
        instance.rotate(axisOfRotation, theta);
        //System.out.println(instance.toString());
        assertEquals(new V3D_Plane(e, P1P0P0, P0P0P0, P0P2P0, P0P2P2), instance);
        // Test 5
        axisOfRotation = V3D_Vector.J;
        theta = Pi;
        instance = new V3D_Plane(e, P1P0P0, P0P0P0, P0P2P0, P0P2P2);
        instance.rotate(axisOfRotation, theta);
        V3D_Plane expResult = new V3D_Plane(e, P1P0P0, P0P0P0, P0P2P0, P0P2N2);
        //System.out.println(instance.toString());
        assertEquals(expResult, instance);
        // Test 5
        axisOfRotation = V3D_Vector.K;
        theta = Pi;
        instance = new V3D_Plane(e, P1P0P0, P0P0P0, P0P2P0, P0P2P2);
        instance.rotate(axisOfRotation, theta);
        expResult = new V3D_Plane(e, P1P0P0, P0P0P0, P0N2P0, P0N2P2);
        //System.out.println(instance.toString());
        assertEquals(expResult, instance);
        //assertEquals(new V3D_Plane(P1P0P0, P0P0P0, P0P2P0, P0P2P2, oom), instance);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_int() {
        System.out.println("isIntersectedBy");
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_Plane instance = V3D_Plane.X0;
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 2
        instance = V3D_Plane.Y0;
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 3
        instance = V3D_Plane.Z0;
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 4
        l = new V3D_LineSegment(pP0P0P2, pP1P0P2);
        instance = V3D_Plane.Z0;
        assertFalse(instance.isIntersectedBy(l, e.oom));
        // Test 5
        l = new V3D_LineSegment(pN1P0P0, pP1P0P0);
        instance = V3D_Plane.X0;
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 6
        l = new V3D_LineSegment(pP1P0P0, pP2P0P0);
        assertFalse(instance.isIntersectedBy(l, e.oom));    
    }

    /**
     * Test of getIntersection method, of class V3D_Plane.
     */
    @Test
    public void testGetIntersection_V3D_Ray_int() {
        System.out.println("getIntersection");
        V3D_Ray r;
        V3D_Plane instance;
        V3D_Geometry result;
        V3D_Geometry expResult;
        // Test 1
        r = new V3D_Ray(pP0P0P0, pP1P0P0);
        instance = V3D_Plane.X0;
        expResult = pP0P0P0;
        result = instance.getIntersection(r, e.oom);
        assertEquals(expResult, result);
        // Test 2
        instance = V3D_Plane.Y0;
        expResult = new V3D_Ray(pP0P0P0, pP1P0P0);
        result = instance.getIntersection(r, e.oom);
        assertEquals(expResult, result);
        // Test 3
        instance = V3D_Plane.Z0;
        expResult = new V3D_Ray(pP0P0P0, pP1P0P0);
        result = instance.getIntersection(r, e.oom);
        assertEquals(expResult, result);
        // Test 4
        r = new V3D_Ray(pN2P0P0, pN1P0P0);
        instance = V3D_Plane.X0;
        expResult = pP0P0P0;
        result = instance.getIntersection(r, e.oom);
        assertEquals(expResult, result);
        // Test 5
        r = new V3D_Ray(pN1P0P0, pN2P0P0);
        instance = V3D_Plane.X0;
        result = instance.getIntersection(r, e.oom);
        assertNull(result);
    }

    /**
     * Test of setOffset method, of class V3D_Plane.
     */
    @Test
    public void testSetOffset() {
        System.out.println("setOffset");
        V3D_Vector offset = V3D_Vector.I;
        V3D_Plane instance = new V3D_Plane(V3D_Plane.X0);
        instance.setOffset(offset);
        V3D_Plane expResult = new V3D_Plane(new V3D_Environment(),
                V3D_Vector.I, N1P0P0, N1P1P0, N1P0P1);
        assertEquals(expResult, instance);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane.
     */
    @Test
    public void testGetDistanceSquared_V3D_Point_int() {
        System.out.println("getDistanceSquared");
        V3D_Point pt;
        V3D_Plane instance;
        Math_BigRational expResult;
        Math_BigRational result;
        // Test 1
        pt = pP1P0P0;
        instance = V3D_Plane.X0;
        expResult = Math_BigRational.ONE;
        result = instance.getDistanceSquared(pt, e.oom);
        assertEquals(expResult, result);
        // Test 2
        pt = pP0P0P0;
        expResult = Math_BigRational.ZERO;
        result = instance.getDistanceSquared(pt, e.oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane covered by
     * {@link #testGetDistanceSquared_V3D_Point_int()}.
     */
    @Test
    public void testGetDistanceSquared_3args() {
        System.out.println("getDistanceSquared");
    }

    /**
     * Test of getDistance method, of class V3D_Plane covered by 
     * {@link #testGetDistanceSquared_V3D_Plane_int()}.
     */
    @Test
    public void testGetDistance_V3D_Plane_int() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane.
     */
    @Test
    public void testGetDistanceSquared_V3D_Plane_int() {
        System.out.println("getDistanceSquared");
        V3D_Plane pl;
        V3D_Plane instance;
        Math_BigRational expResult;
        Math_BigRational result;
        // Test 1
        pl = V3D_Plane.X0;
        instance =V3D_Plane.Y0;
        expResult = Math_BigRational.ZERO;
        result = instance.getDistanceSquared(pl, e.oom);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.translate(P1P0P0);
        expResult = Math_BigRational.ONE;
        result = instance.getDistanceSquared(pl, e.oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane.
     */
    @Test
    public void testGetDistanceSquared_V3D_Line_int() {
        System.out.println("getDistanceSquared");
        V3D_Line l;
        V3D_Plane instance;
        Math_BigRational expResult;
        Math_BigRational result;
        // Test 1
        l = V3D_Line.X_AXIS;
        instance = V3D_Plane.Y0;
        expResult = Math_BigRational.ZERO;
        result = instance.getDistanceSquared(l, e.oom);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Plane(V3D_Plane.Y0);
        instance.setOffset(P0P1P0);
        expResult = Math_BigRational.ONE;
        result = instance.getDistanceSquared(l, e.oom);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegment_int() {
        System.out.println("getDistanceSquared");
        V3D_LineSegment l;
        boolean b = true;
        V3D_Plane instance;
        Math_BigRational expResult;
        Math_BigRational result;
        // Test 1
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        instance = V3D_Plane.Y0;
        expResult = Math_BigRational.ZERO;
        result = instance.getDistanceSquared(l, e.oom);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Plane(V3D_Plane.Y0);
        instance.setOffset(P0P1P0);
        expResult = Math_BigRational.ONE;
        result = instance.getDistanceSquared(l, e.oom);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of getPointOfProjectedIntersection method, of class V3D_Plane.
     */
    @Test
    public void testGetPointOfProjectedIntersection() {
        System.out.println("getPointOfProjectedIntersection");
        V3D_Point pt;
        V3D_Plane instance;
        V3D_Point expResult;
        V3D_Point result;
        // Test 1
        pt = pP1P0P0;
        instance = V3D_Plane.X0;
        expResult = pP0P0P0;
        result = instance.getPointOfProjectedIntersection(pt, e.oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsIntersectedBy_V3D_Triangle_int() {
        System.out.println("isIntersectedBy");
        V3D_Triangle t;
        V3D_Plane instance;
        // Test 1
        t = new V3D_Triangle(pP1P0P0, pP1P1P0, pP0P1P0);
        instance = V3D_Plane.X0;
        assertTrue(instance.isIntersectedBy(t, e.oom));
        // Test 2
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.translate(P2P0P0);
        assertFalse(instance.isIntersectedBy(t, e.oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsIntersectedBy_V3D_Tetrahedron_int() {
        System.out.println("isIntersectedBy");
        V3D_Tetrahedron t;
        V3D_Plane instance;
        // Test 1
        t = new V3D_Tetrahedron(e, P0P0P0, P1P0P0, P0P1P0, P1P1P0, P1P1P1);
        instance = V3D_Plane.X0;
        assertTrue(instance.isIntersectedBy(t, e.oom));        
        // Test 1
        t = new V3D_Tetrahedron(e, P0P0P0, P1P0P0, P0P1P0, P1P1P0, P1P1P1);
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.translate(new V3D_Vector(0.5d, 0, 0));
        assertTrue(instance.isIntersectedBy(t, e.oom));
    }

    /**
     * Test of getIntersection method, of class V3D_Plane.
     */
    @Test
    public void testGetIntersection_V3D_Triangle_int() {
        System.out.println("getIntersection");
        V3D_Triangle t;
        //boolean b = false;
        V3D_Plane instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        t = new V3D_Triangle(e, P0P0P0, P1P0P0, P0P1P0, P1P1P0);
        instance = V3D_Plane.X0;
        expResult = pP0P1P0;
        result = instance.getIntersection(t, e.oom);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.translate(P1P0P0);
        expResult = new V3D_LineSegment(pP1P0P0, pP1P1P0);
        result = instance.getIntersection(t, e.oom);
        assertEquals(expResult, result);
        // Test 3
        instance = new V3D_Plane(V3D_Plane.Z0);
        expResult = new V3D_Triangle(t);
        result = instance.getIntersection(t, e.oom);
        assertEquals(expResult, result);
    }

//    /**
//     * Test of getIntersection method, of class V3D_Plane.
//     */
//    @Test
//    public void testGetIntersection_V3D_Tetrahedron_int() {
//        System.out.println("getIntersection");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Plane instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersection(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Plane.
//     */
//    @Test
//    public void testGetDistance_V3D_Triangle_int() {
//        System.out.println("getDistance");
//        V3D_Triangle t = null;
//        int oom = 0;
//        V3D_Plane instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Plane.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_Triangle_int() {
//        System.out.println("getDistanceSquared");
//        V3D_Triangle t = null;
//        int oom = 0;
//        V3D_Plane instance = null;
//        Math_BigRational expResult = null;
//        Math_BigRational result = instance.getDistanceSquared(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Plane.
//     */
//    @Test
//    public void testGetDistance_V3D_Tetrahedron_int() {
//        System.out.println("getDistance");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Plane instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Plane.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_Tetrahedron_int() {
//        System.out.println("getDistanceSquared");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Plane instance = null;
//        Math_BigRational expResult = null;
//        Math_BigRational result = instance.getDistanceSquared(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    /**
     * Test of isCoplanar method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCoplanar_3args() {
        System.out.println("isCoplanar");
        V3D_Plane p;
        V3D_Point[] points;
        // Test 1
        p = new V3D_Plane(pP0P0P0, pP1P0P0, pP0P1P0);
        points = new V3D_Point[3];
        points[0] = pP2P2P0;
        points[1] = pN2P2P0;
        points[2] = pP2N2P0;
        assertTrue(V3D_Plane.isCoplanar(e, p, points));
        // Test 2
        points[2] = pP2N2P1;
        assertFalse(V3D_Plane.isCoplanar(e, p, points));
        // Test 3
        p = new V3D_Plane(pP0P0P0, pP0P1P0, pP0P0P1);
        points[0] = pP0P2P2;
        points[1] = pP0N2P2;
        points[2] = pP0P2N2;
        assertTrue(V3D_Plane.isCoplanar(e, p, points));
        // Test 4
        points[2] = pP2N2P1;
        assertFalse(V3D_Plane.isCoplanar(e, p, points));
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
        assertTrue(V3D_Plane.isCoplanar(e, points));
        // Test 2
        points[2] = pP2N2P1;
        assertFalse(V3D_Plane.isCoplanar(e, points));
    }

    /**
     * Test of getPlane method, of class V3D_Geometrics. No test needed.
     */
    @Test
    @Disabled
    public void testGetPlane() {
        System.out.println("getPlane");
        V3D_Point[] points = null;
        V3D_Plane expResult = null;
        V3D_Plane result = V3D_Plane.getPlane(e, points);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
