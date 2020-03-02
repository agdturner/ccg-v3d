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
        //BigDecimal x0 = ZERO;
        BigDecimal y0 = ZERO;
        BigDecimal z0 = ZERO;
        BigDecimal x1 = ONE;
        BigDecimal y1 = ONE;
        BigDecimal z1 = ONE;
        V3D_Point p = new V3D_Point(e, x1, y1, z0);
        V3D_Point q = new V3D_Point(e, x1, y1, z1);
        V3D_Point r = new V3D_Point(e, x1, y0, z0);
        V3D_Plane instance = getPlane(p, q, r);
        String expResult = "V3D_Plane(p=V3D_Point(x=1, y=1, z=0), "
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
     * @return A plan or null if the points {@code p}, {@code q} and {@code r}
     * are collinear.
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
        BigDecimal y0 = ZERO;
        BigDecimal z0 = ZERO;
        BigDecimal x1 = ONE;
        BigDecimal y1 = ONE;
        BigDecimal z1 = ONE;
        V3D_Point pt = new V3D_Point(e, x1, y0, z0);
        V3D_Point p = new V3D_Point(e, x1, y1, z0);
        V3D_Point q = new V3D_Point(e, x1, y1, z1);
        V3D_Point r = new V3D_Point(e, x1, y0, z0);
        V3D_Plane instance = getPlane(p, q, r);
        boolean expResult = true;
        boolean result = instance.intersects(pt);
        assertEquals(expResult, result);
        // Test2
        pt = new V3D_Point(e, x1, y0, z1);
        p = new V3D_Point(e, x1, y1, z0);
        q = new V3D_Point(e, x1, y1, z1);
        r = new V3D_Point(e, x1, y0, z0);
        instance = getPlane(p, q, r);
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
        //BigDecimal x0 = ZERO;
        BigDecimal y0 = ZERO;
        BigDecimal z0 = ZERO;
        BigDecimal x1 = ONE;
        BigDecimal y1 = ONE;
        BigDecimal z1 = ONE;
        BigDecimal z2 = TWO;
        V3D_Point start = new V3D_Point(e, x1, y0, z1);
        V3D_Point end = new V3D_Point(e, x1, y0, z2);
        V3D_LineSegment l = new V3D_LineSegment(start, end);
        V3D_Point p = new V3D_Point(e, x1, y1, z0);
        V3D_Point q = new V3D_Point(e, x1, y1, z1);
        V3D_Point r = new V3D_Point(e, x1, y0, z0);
        V3D_Plane instance = getPlane(p, q, r);
        boolean expResult = true;
        boolean result = instance.isOnPlane(l);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V3D_Plane.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        BigDecimal x0 = ZERO;
        BigDecimal y0 = ZERO;
        BigDecimal z0 = ZERO;
        BigDecimal x1 = ONE;
        BigDecimal y1 = ONE;
        BigDecimal z1 = ONE;
        BigDecimal x2 = TWO;
        BigDecimal y2 = TWO;
        BigDecimal z2 = TWO;
        V3D_Point p = new V3D_Point(e, x0, y1, z0);
        V3D_Point q = new V3D_Point(e, x1, y1, z1);
        V3D_Point r = new V3D_Point(e, x1, y0, z0);
        Object o = getPlane(p, q, r);
        p = new V3D_Point(e, x0, y1, z0);
        q = new V3D_Point(e, x1, y1, z1);
        r = new V3D_Point(e, x1, y0, z0);
        V3D_Plane instance = getPlane(p, q, r);
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 2
        p = new V3D_Point(e, x1, y1, z0);
        q = new V3D_Point(e, x1, y1, z1);
        r = new V3D_Point(e, x1, y0, z0);
        instance = getPlane(p, q, r);
        expResult = false;
        result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 3
        p = new V3D_Point(e, x1, y1, z1);
        q = new V3D_Point(e, x1, y0, z0);
        r = new V3D_Point(e, x0, y1, z0);
        instance = getPlane(p, q, r);
        expResult = true;
        result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 4
        p = new V3D_Point(e, x1, y0, z0);
        q = new V3D_Point(e, x0, y1, z0);
        r = new V3D_Point(e, x1, y1, z1);
        instance = getPlane(p, q, r);
        expResult = true;
        result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 4
        p = new V3D_Point(e, x1, y0, z0);
        q = new V3D_Point(e, x0, y1, z0);
        r = new V3D_Point(e, x1, y1, z1);
        instance = getPlane(p, q, r);
        expResult = true;
        result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 5
        p = new V3D_Point(e, x0, y0, z0);
        q = new V3D_Point(e, x1, y0, z0);
        r = new V3D_Point(e, x0, y1, z0);
        o = getPlane(p, q, r);
        p = new V3D_Point(e, x0, y0, z0);
        q = new V3D_Point(e, x2, y0, z0);
        r = new V3D_Point(e, x0, y2, z0);
        instance = getPlane(p, q, r);
        expResult = true;
        result = instance.equals(o);
        assertEquals(expResult, result);
    }

}
