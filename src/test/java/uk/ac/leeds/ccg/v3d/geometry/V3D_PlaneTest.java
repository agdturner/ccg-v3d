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
public class V3D_PlaneTest {
    
    public static V3D_Environment e;
    
    public V3D_PlaneTest() {
    }
    
    @BeforeAll
    public static void setUpClass() throws Exception {
        e = new V3D_Environment(new Generic_Environment(new Generic_Defaults()));
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
    public void testToString() throws Exception {
        System.out.println("toString");
        V3D_Point x = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(0));
        V3D_Point y = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1));
        V3D_Point z = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        V3D_Plane instance = new V3D_Plane(e, x, y, z);
        String expResult = "V3D_Plane(a=V3D_Point(x=1, y=1, z=0), b=V3D_Point(x=1, y=1, z=1), c=V3D_Point(x=1, y=0, z=0))";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane.
     */
    @Test
    public void testIsOnPlane_V3D_Point() throws Exception {
        System.out.println("isOnPlane");
        V3D_Point p = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(0), BigDecimal.valueOf(1));
        V3D_Point x = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(0));
        V3D_Point y = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1));
        V3D_Point z = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        V3D_Plane instance = new V3D_Plane(e, x, y, z);
        boolean expResult = true;
        boolean result = instance.isOnPlane(p);
        assertEquals(expResult, result);
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane.
     */
    @Test
    public void testIsOnPlane_V3D_LineSegment() throws Exception {
        System.out.println("isOnPlane");
        V3D_Point start = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(0), BigDecimal.valueOf(1));
        V3D_Point end = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(0), BigDecimal.valueOf(2));
        V3D_LineSegment l = new V3D_LineSegment(start, end);
        V3D_Point x = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(0));
        V3D_Point y = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1));
        V3D_Point z = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        V3D_Plane instance = new V3D_Plane(e, x, y, z);
        boolean expResult = true;
        boolean result = instance.isOnPlane(l);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V3D_Plane.
     */
    @Test
    public void testEquals() throws Exception {
        System.out.println("equals");
        V3D_Point x = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(0));
        V3D_Point y = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1));
        V3D_Point z = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        Object o = new V3D_Plane(e, x, y, z);
        V3D_Point x1 = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(0));
        V3D_Point y1 = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1));
        V3D_Point z1 = new V3D_Point(e, BigDecimal.valueOf(1), BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        V3D_Plane instance = new V3D_Plane(e, x1, y1, z1);
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
    }
    
}
