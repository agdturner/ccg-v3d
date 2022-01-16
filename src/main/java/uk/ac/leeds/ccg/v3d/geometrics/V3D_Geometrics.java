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

import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
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
    public static boolean isCollinear(V3D_Environment e, V3D_Line l, 
            V3D_Point... points) {
        for (V3D_Point p : points) {
            if (!l.isIntersectedBy(p, e.oom)) {
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
    public static boolean isCollinear(V3D_Environment e, V3D_Line l, 
            V3D_Vector... points) {
        V3D_Vector lv = l.getV(e.oom);
        for (V3D_Vector p : points) {
            if (!lv.isScalarMultiple(p, e.oom)) {
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
    public static boolean isCollinear(V3D_Environment e, Line l, 
            Point... points) {
        for (Point p : points) {
            if (!l.isIntersectedBy(p, e.oom)) {
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
    public static boolean isCollinear(V3D_Environment e, V3D_Vector... points) {
        // For the points to be in a line at least two must be different. 
        if (isCoincident(points)) {
            return false;
        }
        return isCollinear0(e, points);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param points The points to test if they are collinear.
     * @return {@code true} iff all the points are collinear or coincident.
     */
    private static boolean isCollinear0(V3D_Environment e, V3D_Vector... points) {
        // Get a line
        V3D_Line l = getLine(e, points);
        return isCollinear(e, l, points);
    }
    
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param points The points to test if they are collinear.
     * @return {@code false} if all points are coincident. {@code true} iff all
     * the points are collinear.
     */
    public static boolean isCollinear(V3D_Environment e, V3D_Point... points) {
        // For the points to be in a line at least two must be different. 
        if (isCoincident(points)) {
            return false;
        }
        return isCollinear0(e, points);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param points The points to test if they are collinear.
     * @return {@code true} iff all the points are collinear or coincident.
     */
    private static boolean isCollinear0(V3D_Environment e, V3D_Point... points) {
        // Get a line
        V3D_Line l = getLine(e, points);
        return isCollinear(e, l, points);
    }
    
    /**
     * There should be at least two different points.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param points Any number of points, but with two being different.
     * @return A line defined by any two different points or null if the points are coincident.
     */
    public static V3D_Line getLine(V3D_Environment e, V3D_Point... points) {
        V3D_Point p0 = points[0];
        for (V3D_Point p1 : points) {
            if (!p1.equals(p0)) {
                //return new V3D_Line(p0, p1, -1);
                return new V3D_Line(e, p0.getVector(e.oom), p1.getVector(e.oom));
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
    public static V3D_Line getLine(V3D_Environment e, V3D_Vector... points) {
        V3D_Vector p0 = points[0];
        for (V3D_Vector p1 : points) {
            if (!p1.equals(p0)) {
                //return new V3D_Line(p0, p1, -1);
                return new V3D_Line(e, p0, p1);
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
    public static boolean isCollinear(V3D_Environment e, V3D_Envelope en, Point... points) {
        // For the points to be in a line at least two must be different. 
        if (isCoincident(points)) {
            return false;
        }
        return isCollinear0(e, en, points);
    }

    /**
     * @param oom The Order of Magnitude for the precision.
     * @param em The Envelope.
     * @param points The points to test if they are collinear.
     * @return {@code true} iff all the points are collinear or coincident.
     */
    private static boolean isCollinear0(V3D_Environment e, V3D_Envelope em, Point... points) {
        // Get a line
        Line l = getLine(em, points);
        return isCollinear(e, l, points);
    }

    /**
     * There should be at least two different points.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param en The Envelope.
     * @param points Any number of points, but with two being different.
     * @return A line defined by any two different points or null if the points are coincident.
     */
    public static Line getLine(V3D_Envelope en, Point... points) {
        Point p0 = points[0];
        for (Point p1 : points) {
            if (!p1.equals(p0)) {
                return en.new Line(p0, p1);
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
    public static boolean isCoplanar(V3D_Environment e, V3D_Plane p, 
            V3D_Point... points) {
        for (V3D_Point pt : points) {
            if (!p.isIntersectedBy(pt, e.oom)) {
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
    private static boolean isCoplanar(V3D_Environment e, V3D_Plane p, 
            V3D_Vector... points) {
        for (V3D_Vector pt : points) {
            V3D_Point point = new V3D_Point(e, p.offset, pt);
            if (!p.isIntersectedBy(point, e.oom)) {
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
    public static boolean isCoplanar(V3D_Environment e, Plane p, 
            Point... points) {
        for (Point pt : points) {
            if (!p.isIntersectedBy(pt, e.oom)) {
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
    public static boolean isCoplanar(V3D_Environment e, V3D_Point... points) {
        // For the points to be in a plane at least one must not be collinear.
        if (isCoincident(points)) {
            return false;
        }
        if (!isCollinear0(e, points)) {
            V3D_Plane p = getPlane0(e, points);
            return isCoplanar(e, p, points);
        }
        return false;
    }
    
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param points The points to test if they are coplanar.
     * @return {@code false} if points are coincident or collinear. {@code true}
     * iff all points are coplanar.
     */
    public static boolean isCoplanar(V3D_Environment e, V3D_Vector... points) {
        // For the points to be in a plane at least one must not be collinear.
        if (isCoincident(points)) {
            return false;
        }
        if (!isCollinear0(e, points)) {
            V3D_Plane p = getPlane0(e, points);
            return isCoplanar(e, p, points);
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
    private static V3D_Plane getPlane0(V3D_Environment e, V3D_Point... points) {
        V3D_Line l = getLine(e, points);
        for (V3D_Point p : points) {
            if (!isCollinear(e, l, p)) {
                //return new V3D_Plane(l.getP(oom), l.getQ(oom), p, oom);
                return new V3D_Plane(e, V3D_Vector.ZERO, 
                        l.getP(e.oom).getVector(e.oom), 
                        l.getQ(e.oom).getVector(e.oom), p.getVector(e.oom));
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
    private static V3D_Plane getPlane0(V3D_Environment e, V3D_Vector... points) {
        V3D_Line l = getLine(e, points);
        for (V3D_Vector p : points) {
            if (!isCollinear(e, l, p)) {
                //return new V3D_Plane(l.getP(oom), l.getQ(oom), p, oom);
                return new V3D_Plane(e, l.offset, l.getP(), l.getQ(), p);
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
    public static V3D_Plane getPlane(V3D_Environment e, V3D_Point... points) {
        V3D_Line l = getLine(e, points);
        if (l == null) {
            return null;
        }
        for (V3D_Point p : points) {
            if (!isCollinear(e, l, p)) {
                //return new V3D_Plane(l.getP(oom), l.getQ(oom), p, oom);
                return new V3D_Plane(e, l.getP(e.oom).getVector(e.oom), 
                        l.getQ(e.oom).getVector(e.oom), p.getVector(e.oom));
            }
        }
        return null;
    }
}
