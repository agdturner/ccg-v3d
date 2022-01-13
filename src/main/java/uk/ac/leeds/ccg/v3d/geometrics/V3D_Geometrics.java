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

import uk.ac.leeds.ccg.v3d.geometry.V3D_Envelope;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Envelope.Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Envelope.Plane;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Envelope.Point;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Plane;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Vector;

/**
 * A class for geometrics.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Geometrics {

    /**
     * Creates a new instance.
     */
    public V3D_Geometrics(){}
    
    /**
     * @param points The points to test if they are coincident.
     * @return {@code true} iff all the points are coincident.
     */
    public static boolean isCoincident(V3D_Vector... points) {
        V3D_Vector p0 = points[0];
        for (V3D_Vector p1 : points) {
            if (!p1.equals(p0)) {
                return false;
            }
        }
        return true;
    }

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
     * @param points The points to test if they are coincident.
     * @return {@code true} iff all the points are coincident.
     */
    public static boolean isCoincident(Point... points) {
        Point p0 = points[0];
        for (Point p1 : points) {
            if (!p1.equals(p0)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param l The line to test points are collinear with.
     * @param oom The Order of Magnitude for the precision.
     * @param points The points to test if they are collinear with l.
     * @return {@code true} iff all points are collinear with l.
     */
    public static boolean isCollinear(int oom, V3D_Line l, V3D_Point... points) {
        for (V3D_Point p : points) {
            if (!l.isIntersectedBy(p, oom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param l The line to test points are collinear with.
     * @param oom The Order of Magnitude for the precision.
     * @param points The points to test if they are collinear with l.
     * @return {@code true} iff all points are collinear with l.
     */
    public static boolean isCollinear(int oom, V3D_Line l, V3D_Vector... points) {
        V3D_Vector lv = l.getV(oom);
        for (V3D_Vector p : points) {
            if (!lv.isScalarMultiple(p, oom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param l The line to test points are collinear with.
     * @param points The points to test if they are collinear with l.
     * @return {@code true} iff all points are collinear with l.
     */
    public static boolean isCollinear(int oom, Line l, Point... points) {
        for (Point p : points) {
            if (!l.isIntersectedBy(p, oom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param points The points to test if they are collinear.
     * @return {@code false} if all points are coincident. {@code true} iff all
     * the points are collinear.
     */
    public static boolean isCollinear(int oom, V3D_Vector... points) {
        // For the points to be in a line at least two must be different. 
        if (isCoincident(points)) {
            return false;
        }
        return isCollinear0(oom, points);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param points The points to test if they are collinear.
     * @return {@code true} iff all the points are collinear or coincident.
     */
    private static boolean isCollinear0(int oom, V3D_Vector... points) {
        // Get a line
        V3D_Line l = getLine(oom, points);
        return isCollinear(oom, l, points);
    }
    
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param points The points to test if they are collinear.
     * @return {@code false} if all points are coincident. {@code true} iff all
     * the points are collinear.
     */
    public static boolean isCollinear(int oom, V3D_Point... points) {
        // For the points to be in a line at least two must be different. 
        if (isCoincident(points)) {
            return false;
        }
        return isCollinear0(oom, points);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param points The points to test if they are collinear.
     * @return {@code true} iff all the points are collinear or coincident.
     */
    private static boolean isCollinear0(int oom, V3D_Point... points) {
        // Get a line
        V3D_Line l = getLine(oom, points);
        return isCollinear(oom, l, points);
    }
    
    /**
     * There should be at least two different points.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param points Any number of points, but with two being different.
     * @return A line defined by any two different points or null if the points are coincident.
     */
    public static V3D_Line getLine(int oom, V3D_Point... points) {
        V3D_Point p0 = points[0];
        for (V3D_Point p1 : points) {
            if (!p1.equals(p0)) {
                //return new V3D_Line(p0, p1, -1);
                return new V3D_Line(p0.getVector(oom), p1.getVector(oom), oom);
            }
        }
        return null;
    }
    
    /**
     * There should be at least two different points.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param points Any number of points, but with two being different.
     * @return A line defined by any two different points or null if the points are coincident.
     */
    public static V3D_Line getLine(int oom, V3D_Vector... points) {
        V3D_Vector p0 = points[0];
        for (V3D_Vector p1 : points) {
            if (!p1.equals(p0)) {
                //return new V3D_Line(p0, p1, -1);
                return new V3D_Line(p0, p1, oom);
            }
        }
        return null;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param e The Envelope.
     * @param points The points to test if they are collinear.
     * @return {@code false} if all points are coincident. {@code true} iff all
     * the points are collinear.
     */
    public static boolean isCollinear(int oom, V3D_Envelope e, Point... points) {
        // For the points to be in a line at least two must be different. 
        if (isCoincident(points)) {
            return false;
        }
        return isCollinear0(oom, e, points);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param e The Envelope.
     * @param points The points to test if they are collinear.
     * @return {@code true} iff all the points are collinear or coincident.
     */
    private static boolean isCollinear0(int oom, V3D_Envelope e, Point... points) {
        // Get a line
        Line l = getLine(oom, e, points);
        return isCollinear(oom, l, points);
    }

    /**
     * There should be at least two different points.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param e The Envelope.
     * @param points Any number of points, but with two being different.
     * @return A line defined by any two different points or null if the points are coincident.
     */
    public static Line getLine(int oom, V3D_Envelope e, Point... points) {
        Point p0 = points[0];
        for (Point p1 : points) {
            if (!p1.equals(p0)) {
                return e.new Line(p0, p1, oom);
            }
        }
        return null;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param p The plane to test points are coplanar with.
     * @param points The points to test if they are coplanar with p.
     * @return {@code true} iff all points are coplanar with p.
     */
    public static boolean isCoplanar(int oom, V3D_Plane p, V3D_Point... points) {
        for (V3D_Point pt : points) {
            if (!p.isIntersectedBy(pt, oom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param p The plane to test points are coplanar with.
     * @param points The points to test if they are coplanar with p.
     * @return {@code true} iff all points are coplanar with p.
     */
    private static boolean isCoplanar(int oom, V3D_Plane p, V3D_Vector... points) {
        for (V3D_Vector pt : points) {
            V3D_Point point = new V3D_Point(p.offset, pt, oom);
            if (!p.isIntersectedBy(point, oom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param p The plane to test points are coplanar with.
     * @param points The points to test if they are coplanar with p.
     * @return {@code true} iff all points are coplanar with p.
     */
    public static boolean isCoplanar(int oom, Plane p, Point... points) {
        for (Point pt : points) {
            if (!p.isIntersectedBy(pt, oom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param points The points to test if they are coplanar.
     * @return {@code false} if points are coincident or collinear. {@code true}
     * iff all points are coplanar.
     */
    public static boolean isCoplanar(int oom, V3D_Point... points) {
        // For the points to be in a plane at least one must not be collinear.
        if (isCoincident(points)) {
            return false;
        }
        if (!isCollinear0(oom, points)) {
            V3D_Plane p = getPlane0(oom, points);
            return isCoplanar(oom, p, points);
        }
        return false;
    }
    
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param points The points to test if they are coplanar.
     * @return {@code false} if points are coincident or collinear. {@code true}
     * iff all points are coplanar.
     */
    public static boolean isCoplanar(int oom, V3D_Vector... points) {
        // For the points to be in a plane at least one must not be collinear.
        if (isCoincident(points)) {
            return false;
        }
        if (!isCollinear0(oom, points)) {
            V3D_Plane p = getPlane0(oom, points);
            return isCoplanar(oom, p, points);
        }
        return false;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param points The points from which a plane is to be derived.
     * @return A plane that may or may not contain all the points or
     * {@code null} if there is no such plane. This does not test if the points are coincident or
     * collinear.
     */
    private static V3D_Plane getPlane0(int oom, V3D_Point... points) {
        V3D_Line l = getLine(oom, points);
        for (V3D_Point p : points) {
            if (!isCollinear(oom, l, p)) {
                //return new V3D_Plane(l.getP(oom), l.getQ(oom), p, oom);
                return new V3D_Plane(V3D_Vector.ZERO, l.getP(oom).getVector(oom), l.getQ(oom).getVector(oom), p.getVector(oom), oom);
            }
        }
        return null;
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param points The points from which a plane is to be derived.
     * @return A plane that may or may not contain all the points or
     * {@code null} if there is no such plane. This does not test if the points are coincident or
     * collinear.
     */
    private static V3D_Plane getPlane0(int oom, V3D_Vector... points) {
        V3D_Line l = getLine(oom, points);
        for (V3D_Vector p : points) {
            if (!isCollinear(oom, l, p)) {
                //return new V3D_Plane(l.getP(oom), l.getQ(oom), p, oom);
                return new V3D_Plane(l.offset, l.getP(), l.getQ(), p, oom);
            }
        }
        return null;
    }
    
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param points The points from which a plane is to be derived.
     * @return A plane that may or may not contain all the points or
     * {@code null} if there is no such plane (if the points are coincident or
     * collinear).
     */
    public static V3D_Plane getPlane(int oom, V3D_Point... points) {
        V3D_Line l = getLine(oom, points);
        if (l == null) {
            return null;
        }
        for (V3D_Point p : points) {
            if (!isCollinear(oom, l, p)) {
                //return new V3D_Plane(l.getP(oom), l.getQ(oom), p, oom);
                return new V3D_Plane(l.getP(oom).getVector(oom), l.getQ(oom).getVector(oom), p.getVector(oom), oom);
            }
        }
        return null;
    }
}
