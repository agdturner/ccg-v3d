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

import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Plane;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;

/**
 * A class for geometrics.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Geometrics {

    /**
     * @param points The points to test if they are coincident.
     * @return {@code true} iff all the points are coincident.
     */
    public static boolean isCoincident(V3D_Point... points) {
        V3D_Point p0 = points[0];
        for (V3D_Point p1 : points) {
            if (!p1.equals(p0)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param l The line to test points are collinear with.
     * @param points The points to test if they are collinear with l.
     * @return {@code true} iff all points are collinear with l.
     */
    public static boolean isCollinear(V3D_Line l, V3D_Point... points) {
        for (V3D_Point p : points) {
            if (!l.isIntersectedBy(p)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param points The points to test if they are collinear.
     * @return {@code false} if all points are coincident. {@code true} iff all
     * the points are collinear.
     */
    public static boolean isCollinear(V3D_Point... points) {
        // For the points to be in a line at least two must be different. 
        if (isCoincident(points)) {
            return false;
        }
        return isCollinear0(points);
    }

    /**
     * @param points The points to test if they are collinear.
     * @return {@code true} iff all the points are collinear or coincident.
     */
    private static boolean isCollinear0(V3D_Point... points) {
        // Get a line
        V3D_Line l = getLine(points);
        return isCollinear(l, points);
    }

    /**
     * There should be at least two different points.
     *
     * @param points Any number of points, but with two being different.
     * @return A line defined by any two different points or null if the points are coincident.
     */
    public static V3D_Line getLine(V3D_Point... points) {
        V3D_Point p0 = points[0];
        for (V3D_Point p1 : points) {
            if (!p1.equals(p0)) {
                return new V3D_Line(p0, p1, -1);
            }
        }
        return null;
    }

    /**
     * @param p The plane to test points are coplanar with.
     * @param points The points to test if they are coplanar with p.
     * @return {@code true} iff all points are coplanar with p.
     */
    public static boolean isCoplanar(int oom, V3D_Plane p, V3D_Point... points) {
        for (V3D_Point pt : points) {
            if (!p.isIntersectedBy(pt)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param points The points to test if they are coplanar.
     * @return {@code false} if points are coincident or collinear. {@code true}
     * iff all points are coplanar.
     */
    public static boolean isCoplanar(int oom, V3D_Point... points) {
        // For the points to be in a plane at least one must not be collinear.
        if (isCoincident(points)) {
            return false;
        }
        if (!isCollinear0(points)) {
            V3D_Plane p = getPlane0(oom, points);
            return isCoplanar(oom, p, points);
        }
        return false;
    }

    /**
     * @param oom The Order of Magnitude used to initialise any vectors for the
     * resulting plane.
     * @param points The points from which a plane is to be derived.
     * @return A plane that may or may not contain all the points or
     * {@code null} if there is no such plane. This does not test if the points are coincident or
     * collinear.
     */
    private static V3D_Plane getPlane0(int oom, V3D_Point... points) {
        V3D_Line l = getLine(points);
        for (V3D_Point p : points) {
            if (!isCollinear(l, p)) {
                return new V3D_Plane(l.p, l.q, p, oom);
            }
        }
        return null;
    }

    /**
     * @param oom The Order of Magnitude used to initialise any vectors for the
     * resulting plane.
     * @param points The points from which a plane is to be derived.
     * @return A plane that may or may not contain all the points or
     * {@code null} if there is no such plane (if the points are coincident or
     * collinear).
     */
    public static V3D_Plane getPlane(int oom, V3D_Point... points) {
        V3D_Line l = getLine(points);
        if (l == null) {
            return null;
        }
        for (V3D_Point p : points) {
            if (!isCollinear(l, p)) {
                return new V3D_Plane(l.p, l.q, p, oom);
            }
        }
        return null;
    }
}
