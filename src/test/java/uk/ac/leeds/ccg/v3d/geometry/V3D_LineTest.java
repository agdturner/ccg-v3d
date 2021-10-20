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

import uk.ac.leeds.ccg.v3d.V3D_Test;
import java.math.BigDecimal;
import java.math.MathContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * Test class for V3D_Line.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineTest extends V3D_Test {

    public V3D_LineTest() {
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
     * Test of toString method, of class V3D_Line.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int oom = -1;
        V3D_Line instance = new V3D_Line(P0P0P0, P1P0P0, oom);
        String expResult = "V3D_Line("
                + "p=V3D_Point(x=0, y=0, z=0), q=V3D_Point(x=1, y=0, z=0), "
                + "v=V3D_Vector("
                + "dx=Math_BigRationalSqrt(x=1, negative=false, sqrtx=1, oomi=-1, sqrtxapprox=null, oom=-1), "
                + "dy=Math_BigRationalSqrt(x=0, negative=false, sqrtx=0, oomi=-1, sqrtxapprox=null, oom=-1), "
                + "dz=Math_BigRationalSqrt(x=0, negative=false, sqrtx=0, oomi=-1, sqrtxapprox=null, oom=-1), "
                + "m=Math_BigRationalSqrt(x=1, negative=false, sqrtx=1, oomi=-1, sqrtxapprox=null, oom=-1)))";
        String result = instance.toString();
        System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of equals method, of class V3D_Line.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        int oom = -1;
        Object o = new V3D_Line(P0P0P0, P1P0P0, oom);
        V3D_Line instance = new V3D_Line(P0P0P0, P1P0P0, oom);
        assertTrue(instance.equals(o));
        // Test 2
        instance = new V3D_Line(P0P0P0, N1P0P0, oom);
        assertTrue(instance.equals(o));
        // Test 2
        instance = new V3D_Line(P0P0P0, N1P1P0, oom);
        assertFalse(instance.equals(o));
    }

    /**
     * Test of hashCode method, of class V3D_Line.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        V3D_Line e = V3D_Environment.xAxis;
        int result = e.hashCode();
        int expResult = 9644914;
        assertTrue(result == expResult);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Line.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_Point pt = P0P0P0;
        V3D_Line instance = new V3D_Line(N1N1N1, P1P1P1, oom);
        assertTrue(instance.isIntersectedBy(pt, oom));
        // Test 2
        pt = P1P1P1;
        instance = new V3D_LineSegment(P0P0P0, P1P1P1, oom);
        assertTrue(instance.isIntersectedBy(pt, oom));
    }

    /**
     * Test of isParallel method, of class V3D_Line.
     */
    @Test
    public void testIsParallel() {
        System.out.println("isParallel");
        int oom = -1;
        V3D_Line l = V3D_Environment.xAxis;
        V3D_Line instance = V3D_Environment.xAxis;
        assertTrue(instance.isParallel(l));
        // Test 2
        instance = V3D_Environment.yAxis;
        assertFalse(instance.isParallel(l));
        // Test 3
        instance = V3D_Environment.zAxis;
        assertFalse(instance.isParallel(l));
        // Test 4
        instance = new V3D_Line(P0P1P0, P1P1P0, oom);
        assertTrue(instance.isParallel(l));
        // Test 5
        instance = new V3D_Line(P0P1P0, P1P1P0, oom);
        assertTrue(instance.isParallel(l));
        // Test 6
        instance = new V3D_Line(P0P0P1, P1P0P1, oom);
        assertTrue(instance.isParallel(l));
        // Test 7
        instance = new V3D_Line(P1P0P1, P0P0P1, oom);
        assertTrue(instance.isParallel(l));
        // Test 8
        instance = new V3D_Line(P1P0P1, P0P1P1, oom);
        assertFalse(instance.isParallel(l));
        // Test 9
        l = V3D_Environment.yAxis;
        instance = new V3D_Line(P0P0P1, P0P1P1, oom);
        assertTrue(instance.isParallel(l));
        // Test 9
        instance = new V3D_Line(P1P0P0, P1P1P0, oom);
        assertTrue(instance.isParallel(l));
        // Test 10
        instance = new V3D_Line(P1P0P1, P1P1P1, oom);
        assertTrue(instance.isParallel(l));
        // Test 11
        instance = new V3D_Line(P1P0P1, P1P1P0, oom);
        assertFalse(instance.isParallel(l));
        // Test 12
        l = V3D_Environment.zAxis;
        instance = new V3D_Line(P1P0P0, P1P0P1, oom);
        assertTrue(instance.isParallel(l));
        // Test 9
        instance = new V3D_Line(P0P1P0, P0P1P1, oom);
        assertTrue(instance.isParallel(l));
        // Test 10
        instance = new V3D_Line(P1P1P0, P1P1P1, oom);
        assertTrue(instance.isParallel(l));
        // Test 11
        instance = new V3D_Line(P1P0P1, P1P1P0, oom);
        assertFalse(instance.isParallel(l));
        // Test 12
        l = new V3D_Line(P1P1P1, N1N1N1, oom);
        instance = new V3D_Line(P1N1P1, N1P1N1, oom);
        assertFalse(instance.isParallel(l));
    }

    /**
     * Test of getIntersection method, of class V3D_Line.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_Line l;
        V3D_Line instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        //l = new V3D_Line(N1N1N1, P1P1P1);
        l = new V3D_Line(P1P1P1, N1N1N1, oom);
        //instance = new V3D_Line(N1P1N1, P1N1P1);
        instance = new V3D_Line(P1N1P1, N1P1N1, oom);
        expResult = P0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        l = new V3D_Line(N1N1N1, P1P1P1, oom);
        instance = new V3D_Line(P1P1P0, new V3D_Point(P1, P1, P2), oom);
        expResult = P1P1P1;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 3
        expResult = P0P0P0;
        instance = new V3D_Line(N1N1P0, P1P1P0, oom);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 4
        l = new V3D_Line(N1N1N1, P1P1P1, oom);
        instance = new V3D_Line(new V3D_Point(P3, P1, P1), new V3D_Point(P1, P3, P3), oom);
        expResult = new V3D_Point(P2, P2, P2);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_Line(N1N1P0, P1P1P0, oom);
        instance = new V3D_Line(new V3D_Point(P3, P3, P0), new V3D_Point(P3, P3, N1), oom);
        expResult = new V3D_Point(P3, P3, P0);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 6
        l = new V3D_Line(N1N1N1, P1P1P1, oom);
        instance = new V3D_Line(P1N1N1, N1P1P1, oom);
        expResult = P0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 7
        l = new V3D_Line(P0P0P0, P1P1P1, oom);
        instance = new V3D_Line(P1N1N1, N1P1P1, oom);
        expResult = P0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 8
        l = new V3D_Line(N1N1N1, P0P0P0, oom);
        instance = new V3D_Line(P1N1N1, N1P1P1, oom);
        expResult = P0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 9
        l = new V3D_Line(new V3D_Point(N2, N2, N2), N1N1N1, oom);
        instance = new V3D_Line(P1N1N1, P0P0P0, oom);
        expResult = P0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 10
        l = new V3D_Line(new V3D_Point(N2, N2, N2), N1N1N1, oom);
        instance = new V3D_Line(P0P0P0, N1P1P1, oom);
        expResult = P0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 11
        l = new V3D_Line(N1N1N1, P1P1P1, oom);
        expResult = new V3D_Line(N1N1N1, P1P1P1, oom);
        instance = new V3D_Line(new V3D_Point(N3, N3, N3), new V3D_Point(N4, N4, N4), oom);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 12 to 14
        // v.dx = 0, v.dy != 0, v.dz !=0
        // Test 11
        l = new V3D_Line(N1N1N1, P1P1P1, oom);
        expResult = P0P0P0;
        instance = new V3D_Line(P0P0P0, P0P1P1, oom);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 15
        l = new V3D_Line(P0N1N1, new V3D_Point(P2, P1, P1), oom);
        expResult = P1P0P0;
        instance = new V3D_Line(P1P0P0, P1P1P1, oom);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 16
        l = new V3D_Line(P0N1P1, new V3D_Point(P2, P1, P3), oom);
        expResult = new V3D_Point(P1, P0, P2);
        instance = new V3D_Line(new V3D_Point(P1, P0, P2),
                new V3D_Point(P1, P1, P3), oom);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 17 to 18
        // v.dx != 0, v.dy = 0, v.dz = 0
        // Test 17
        l = new V3D_Line(N1N1N1, P1P1P1, oom);
        expResult = P0P0P0;
        instance = new V3D_Line(P0P0P0, P1P0P0, oom);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 18
        l = new V3D_Line(P0N1N1, new V3D_Point(P2, P1, P1), oom);
        expResult = P1P0P0;
        instance = new V3D_Line(P1P0P0, new V3D_Point(P2, P0, P0), oom);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 19
        l = new V3D_Line(P0N1P0, new V3D_Point(P2, P1, P2), oom);
        expResult = P1P0P1;
        instance = new V3D_Line(P1P0P1, new V3D_Point(P2, P0, P1), oom);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 20 to 21
        // v.dx != 0, v.dy = 0, v.dz != 0
        // Test 20
        l = new V3D_Line(N1N1N1, P1P1P1, oom);
        expResult = P0P0P0;
        instance = new V3D_Line(P0P0P0, P1P0P1, oom);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 21
        l = new V3D_Line(P0N1N1, new V3D_Point(P2, P1, P1), oom);
        expResult = P1P0P0;
        instance = new V3D_Line(P1P0P0, new V3D_Point(P2, P0, P1), oom);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 22
        l = new V3D_Line(P0P1N1, new V3D_Point(P2, P3, P1), oom);
        expResult = new V3D_Point(P1, P2, P0);
        instance = new V3D_Line(new V3D_Point(P1, P2, P0), new V3D_Point(P2, P2, P1), oom);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Line.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_Line l;
        V3D_Line instance;
        boolean result;
        // Test 1
        l = new V3D_Line(N1N1N1, P1P1P1, oom);
        instance = new V3D_Line(N1P1N1, P1N1P1, oom);
        result = instance.isIntersectedBy(l, oom);
        assertTrue(result);
        // Test 2
        l = new V3D_Line(P0N1N1, P1P1P1, oom);
        instance = new V3D_Line(N1P1N1, P1N1P1, oom);
        result = instance.isIntersectedBy(l, oom);
        assertFalse(result);
    }

    /**
     * Test of equals method, of class V3D_Line.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        int oom = -1;
        Object o = new V3D_Line(P0P0P0, P1P1P1, oom);
        V3D_Line instance = new V3D_Line(P0P0P0, P1P1P1, oom);
        assertTrue(instance.equals(o));
        // Test 2
        instance = new V3D_Line(P1P1P1, P0P0P0, oom);
        assertTrue(instance.equals(o));
    }

    /**
     * Test of equals method, of class V3D_Line.
     */
    @Test
    public void testEquals_V3D_Line() {
        System.out.println("equals");
        int oom = -1;
        V3D_Line l = new V3D_Line(P0P0P0, P1P1P1, oom);
        V3D_Line instance = new V3D_Line(P0P0P0, P1P1P1, oom);
        assertTrue(instance.equals(l));
        // Test 2
        instance = new V3D_Line(P1P1P1, P0P0P0, oom);
        assertTrue(instance.equals(l));
    }

    /**
     * Test of getIntersection method, of class V3D_Line.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_Line l = new V3D_Line(P0P0P0, P1P1P1, oom);
        V3D_Line instance = new V3D_Line(P0P0P0, P1P1P1, oom);
        V3D_Geometry expResult = new V3D_Line(P0P0P0, P1P1P1, oom);
        V3D_Geometry result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Line(P1P1P1, P0P0P0, oom);
        expResult = new V3D_Line(P0P0P0, P1P1P1, oom);
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
        // Test 3
        //instance = new V3D_Line(P0P1P0, P0N1P0, oom);
        instance = new V3D_Line(P0N1P0, P0P1P0, oom);
        l = new V3D_Line(P1P1P1, P0P0P0, oom);
        expResult = P0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isParallelToX0 method, of class V3D_Line.
     */
    @Test
    public void testIsParallelToX0() {
        System.out.println("isParallelToX0");
        int oom = -1;
        V3D_Line instance = new V3D_Line(P1P0P0, P1P1P0, oom);
        assertTrue(instance.isParallelToX0());
        // Test 1
        instance = new V3D_Line(P0P0P0, P0P1P0, oom);
        assertTrue(instance.isParallelToX0());
        // Test 2
        instance = new V3D_Line(P0P0P1, P1P1P0, oom);
        assertFalse(instance.isParallelToX0());
    }

    /**
     * Test of isParallelToY0 method, of class V3D_Line.
     */
    @Test
    public void testIsParallelToY0() {
        System.out.println("isParallelToY0");
        int oom = -1;
        V3D_Line instance = new V3D_Line(P0P0P1, P0P0N1, oom);
        assertTrue(instance.isParallelToY0());
    }

    /**
     * Test of isParallelToZ0 method, of class V3D_Line.
     */
    @Test
    public void testIsParallelToZ0() {
        System.out.println("isParallelToZ0");
        int oom = -1;
        V3D_Line instance = new V3D_Line(P0P0P1, P1P1P1, oom);
        assertTrue(instance.isParallelToZ0());
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Line.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        int oom = -1;
        V3D_Line l = new V3D_Line(P0P0P0, P1P1P1, oom);
        V3D_Line instance = new V3D_Line(P0P0P1, P0P0N1, oom);
        assertTrue(instance.isEnvelopeIntersectedBy(l, oom));
        // Test 2
        l = new V3D_Line(P0P0P1, P0P1P1, oom);
        instance = new V3D_Line(P0P0N1, P0P1N1, oom);
        assertFalse(instance.isEnvelopeIntersectedBy(l, oom));
    }

    /**
     * Test of apply method, of class V3D_Line.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        int oom = -1;
        V3D_Vector v = V3D_Environment.i;
        V3D_Line instance = V3D_Environment.xAxis;
        V3D_Line expResult = V3D_Environment.xAxis;
        V3D_Line result = instance.apply(v);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = V3D_Environment.yAxis;
        expResult = new V3D_Line(P1P0P0, P1P1P0, oom);
        result = instance.apply(v);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = V3D_Environment.zAxis;
        expResult = new V3D_Line(P1P0P0, P1P0P1, oom);
        result = instance.apply(v);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getAsMatrix method, of class V3D_Line.
     */
    @Test
    public void testGetAsMatrix() {
        System.out.println("getAsMatrix");
        V3D_Line instance = V3D_Environment.xAxis;
        Math_BigRational[][] m = new Math_BigRational[2][3];
        m[0][0] = Math_BigRational.ZERO;
        m[0][1] = Math_BigRational.ZERO;
        m[0][2] = Math_BigRational.ZERO;
        m[1][0] = Math_BigRational.ONE;
        m[1][1] = Math_BigRational.ZERO;
        m[1][2] = Math_BigRational.ZERO;
        Math_Matrix_BR expResult = new Math_Matrix_BR(m);
        Math_Matrix_BR result = instance.getAsMatrix();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDistance method, of class V3D_Line.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        System.out.println("getDistance");
        V3D_Point p;
        int oom;
        V3D_Line instance;
        BigDecimal expResult;
        BigDecimal result;
        // Test 1
        p = P0P0P0;
        oom = -1;
        instance = new V3D_Line(P1P0P0, P1P1P0, oom);
        expResult = BigDecimal.ONE;
        result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        oom = -2;
        instance = new V3D_Line(P0P1P0, P1P1P0, oom);
        result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        p = P1P1P1;
        oom = -3;
        instance = new V3D_Line(P0P0P0, P1P1P0, oom);
        result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        oom = - 4;
        p = P0P1P0;
        instance = new V3D_Line(P0P0P0, P1P1P0, oom);
        int ooms = Math_BigRationalSqrt.getOOM(Math_BigRational.TWO, oom);
        if (ooms > 0) {
            ooms = 0;
        }
        MathContext mc = new MathContext(-ooms);
        expResult = Math_BigRational.valueOf(new Math_BigRationalSqrt(
                Math_BigRational.TWO, oom).toBigDecimal(oom)).divide(2)
                .toBigDecimal(mc);
        result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Line.
     */
    @Test
    @Disabled
    public void testIsIntersectedBy_V3D_Ray() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_Ray r = null;
        V3D_Line instance = null;
        boolean result = instance.isIntersectedBy(r, oom);
        assertTrue(result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class V3D_Line.
     */
    @Test
    @Disabled
    public void testGetIntersection_V3D_Ray() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_Ray r = null;
        V3D_Line instance = null;
        V3D_Geometry expResult = null;
        V3D_Geometry result = instance.getIntersection(r, oom);
        assertTrue(expResult.equals(result));
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLineOfIntersection method, of class V3D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V3D_Point() {
        System.out.println("getLineOfIntersection");
        int oom = -1;
        V3D_Point pt;
        V3D_Line instance;
        V3D_LineSegment expResult;
        V3D_LineSegment result;
        // Test 1
        pt = P0P0P0;
        instance = new V3D_Line(P1P0P0, P1P1P0, oom);
        expResult = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        result = instance.getLineOfIntersection(pt, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Line(P1N1P0, P1P1P0, oom);
        expResult = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        result = instance.getLineOfIntersection(pt, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getPointOfIntersection method, of class V3D_Line. No test: Test
     * covered by {@link #testGetLineOfIntersection_V3D_Point()}
     */
    @Test
    public void testGetPointOfIntersection() {
        System.out.println("getPointOfIntersection");
        int oom = -1;
        V3D_Point pt = P2P0P0;
        V3D_Line instance = new V3D_Line(P0P0P0, P0P2P2, oom);
        V3D_Point expResult = P0P0P0;
        V3D_Point result = instance.getPointOfIntersection(pt, oom);
        assertTrue(expResult.equals(result));
        // Test 2
        // ...
    }

    /**
     * Test of getLineOfIntersection method, of class V3D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V3D_Line_V3D_Line() {
        System.out.println("getLineOfIntersection");
        int oom = -1;
        V3D_Line l0 = new V3D_Line(P1P0P0, P1P1P0, oom);
        V3D_Line l1 = new V3D_Line(P0P0P0, P0P0P1, oom);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        V3D_Geometry result = l0.getLineOfIntersection(l1);
        assertTrue(expResult.equals(result));
        // Test 2
        l1 = new V3D_Line(P0P0P0, P0P1P0, oom);
        result = l0.getLineOfIntersection(l1);
        assertNull(result);
    }

    /**
     * Test of getDistance method, of class V3D_Line.
     */
    @Test
    @Disabled
    public void testGetDistance_V3D_Ray_int() {
        System.out.println("getDistance");
        int oom = -1;
        V3D_Ray r = new V3D_Ray(P0P0P0, P1P0P0, oom);
        V3D_Line instance = new V3D_Line(P0P0P0, P1P0P0, oom);
        BigDecimal expResult = BigDecimal.ZERO;
        BigDecimal result = instance.getDistance(r, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = new V3D_Line(P0P1P0, P1P1P0, oom);
        expResult = BigDecimal.ONE;
        result = instance.getDistance(r, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        instance = new V3D_Line(P0P2P0, P1P2P0, oom);
        expResult = BigDecimal.valueOf(2);
        result = instance.getDistance(r, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        r = new V3D_Ray(P1P0P0, P2P0P0, oom);
        instance = new V3D_Line(P0P0P0, P0P1P0, oom);
        expResult = new Math_BigRationalSqrt(2, oom, false).divide(Math_BigRational.valueOf(2)).toBigDecimal(oom);
        result = instance.getDistance(r, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 5
        r = new V3D_Ray(P1P0P0, P2P0P0, oom);
        instance = new V3D_Line(P0P0P0, P0P1P0, oom);
        expResult = BigDecimal.valueOf(0.5d);
        result = instance.getDistance(r, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Line.
     */
    @Test
    @Disabled
    public void testGetDistance_V3D_LineSegment_int() {
        System.out.println("getDistance");
        V3D_LineSegment l = null;
        int oom = 0;
        V3D_Line instance = null;
        BigDecimal expResult = null;
        BigDecimal result = instance.getDistance(l, oom);
        assertTrue(expResult.equals(result));
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDistance method, of class V3D_Line.
     */
    @Test
    public void testGetDistance_V3D_Line_int() {
        System.out.println("getDistance");
        int oom = -1;
        V3D_Line l;
        V3D_Line instance;
        BigDecimal expResult;
        BigDecimal result;
        // Test 1 
        // https://math.stackexchange.com/questions/2213165/find-shortest-distance-between-lines-in-3d
        l = new V3D_Line(new V3D_Point(P2, P6, N9), new V3D_Vector(P3, P4, N4, oom));
        oom = -1;
        instance = new V3D_Line(new V3D_Point(N1, N2, P3), new V3D_Vector(P2, N6, P1, oom));
        expResult = new BigDecimal("4.7");
        result = instance.getDistance(l, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        l = new V3D_Line(P0P0P0, P1P1P0, oom);
        oom = -4;
        instance = new V3D_Line(P1N1P0, new V3D_Point(P2, P0, P0), oom);
        expResult = BigDecimal.valueOf(2).sqrt(new MathContext(1 - oom));
        result = instance.getDistance(l, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }
}
