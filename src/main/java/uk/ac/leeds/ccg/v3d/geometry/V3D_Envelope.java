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

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * An envelope contains all the extreme values with respect to the X and Y axes.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Envelope extends V3D_Geometry implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The minimum x-coordinate.
     */
    public BigRational xMin;

    /**
     * The maximum x-coordinate.
     */
    public BigRational xMax;

    /**
     * The minimum y-coordinate.
     */
    public BigRational yMin;

    /**
     * The maximum y-coordinate.
     */
    public BigRational yMax;

    /**
     * The minimum z-coordinate.
     */
    public BigRational zMin;

    /**
     * The maximum z-coordinate.
     */
    public BigRational zMax;

    /**
     * @param e An envelope.
     */
    public V3D_Envelope(V3D_Envelope e) {
        super(e.e);
        yMin = e.yMin;
        yMax = e.yMax;
        xMin = e.xMin;
        xMax = e.xMax;
        zMin = e.zMin;
        zMax = e.zMax;
    }

    /**
     * @param e An envelope.
     * @param points The points.
     */
    public V3D_Envelope(V3D_Environment e, V3D_Point... points) {
        super(e);
        if (points.length > 0) {
            xMin = points[0].x;
            xMax = points[0].x;
            yMin = points[0].y;
            yMax = points[0].y;
            zMin = points[0].z;
            zMax = points[0].z;
            for (int i = 1; i < points.length; i++) {
                xMin = BigRational.min(xMin, points[i].x);
                xMax = BigRational.max(xMax, points[i].x);
                yMin = BigRational.min(yMin, points[i].y);
                yMax = BigRational.max(yMax, points[i].y);
                zMin = BigRational.min(zMin, points[i].z);
                zMax = BigRational.max(zMax, points[i].z);
            }
        }
    }

    /**
     * @param e The vector environment.
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     * @param z The z-coordinate of a point.
     */
    public V3D_Envelope(V3D_Environment e, BigRational x, BigRational y,
            BigRational z) {
        super(e);
        xMin = x;
        xMax = x;
        yMin = y;
        yMax = y;
        zMin = z;
        zMax = z;
    }

    /**
     * @param e The vector environment.
     * @param xMin What {@link xMin} is set to.
     * @param xMax What {@link xMax} is set to.
     * @param yMin What {@link yMin} is set to.
     * @param yMax What {@link yMax} is set to.
     * @param zMin What {@link zMin} is set to.
     * @param zMax What {@link zMax} is set to.
     */
    public V3D_Envelope(V3D_Environment e, BigRational xMin, BigRational xMax,
            BigRational yMin, BigRational yMax, BigRational zMin,
            BigRational zMax) {
        super(e);
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "(xMin=" + xMin.toString() + ", xMax=" + xMax.toString() + ","
                + "yMin=" + yMin.toString() + ", yMax=" + yMax.toString() + ","
                + "zMin=" + zMin.toString() + ", zMax=" + zMax.toString() + ")";
    }

    /**
     * @param e The V3D_Envelope for this to envelop
     * @return The possibly expanded envelope which is this.
     */
    public V3D_Envelope envelope(V3D_Envelope e) {
        xMin = BigRational.min(e.xMin, xMin);
        xMax = BigRational.max(e.xMax, xMax);
        yMin = BigRational.min(e.yMin, yMin);
        yMax = BigRational.max(e.yMax, yMax);
        zMin = BigRational.min(e.zMin, zMin);
        zMax = BigRational.max(e.zMax, zMax);
        return this;
    }

    /**
     * If {@code e} touches, or overlaps then it intersects.
     *
     * @param e The Vector_Envelope2D to test for intersection.
     * @return {@code true} if this intersects with {@code e}.
     */
    public boolean isIntersectedBy(V3D_Envelope e) {
        // Does this contain any corners of e
        boolean r = isIntersectedBy(e.xMin, e.yMin, e.zMax);
        if (r) {
            return r;
        }
        r = isIntersectedBy(e.xMin, e.yMax, e.zMax);
        if (r) {
            return r;
        }
        r = isIntersectedBy(e.xMax, e.yMin, e.zMax);
        if (r) {
            return r;
        }
        r = isIntersectedBy(e.xMax, e.yMax, e.zMax);
        if (r) {
            return r;
        }
        r = isIntersectedBy(e.xMin, e.yMin, e.zMin);
        if (r) {
            return r;
        }
        r = isIntersectedBy(e.xMin, e.yMax, e.zMin);
        if (r) {
            return r;
        }
        r = isIntersectedBy(e.xMax, e.yMin, e.zMin);
        if (r) {
            return r;
        }
        r = isIntersectedBy(e.xMax, e.yMax, e.zMin);
        if (r) {
            return r;
        }
        // Does e contain any corners of this
        r = e.isIntersectedBy(xMax, yMax, zMax);
        if (r) {
            return r;
        }
        r = e.isIntersectedBy(xMin, yMax, zMax);
        if (r) {
            return r;
        }
        r = e.isIntersectedBy(xMax, yMin, zMax);
        if (r) {
            return r;
        }
        r = e.isIntersectedBy(xMax, yMax, zMax);
        if (r) {
            return r;
        }
        /**
         * Check to see if xMin and xMax are between e.xMin and e.xMax, e.yMin
         * and e.yMax are between yMin and yMax, and e.zMin and e.zMax are
         * between zMin and zMax.
         */
        if (e.xMax.compareTo(xMax) != 1 && e.xMax.compareTo(xMin) != -1
                && e.xMin.compareTo(xMax) != 1
                && e.xMin.compareTo(xMin) != -1) {
            if (yMin.compareTo(e.yMax) != 1 && yMin.compareTo(e.yMin) != -1
                    && yMax.compareTo(e.yMax) != 1
                    && yMax.compareTo(e.yMin) != -1) {
                if (zMin.compareTo(e.zMax) != 1 && zMin.compareTo(e.zMin) != -1
                        && zMax.compareTo(e.zMax) != 1
                        && zMax.compareTo(e.zMin) != -1) {
                    return true;
                }
            }
        }
        /**
         * Check to see if e.xMin and e.xMax are between xMax, yMin and yMax are
         * between e.yMin and e.yMax, and zMin and zMax are between e.zMin and
         * e.zMax.
         */
        if (xMax.compareTo(e.xMax) != 1 && xMax.compareTo(e.xMin) != -1
                && xMin.compareTo(e.xMax) != 1
                && xMin.compareTo(e.xMin) != -1) {
            if (e.yMin.compareTo(yMax) != 1 && e.yMin.compareTo(yMin) != -1
                    && e.yMax.compareTo(yMax) != 1
                    && e.yMax.compareTo(yMin) != -1) {
                if (e.zMin.compareTo(zMax) != 1 && e.zMin.compareTo(zMin) != -1
                        && e.zMax.compareTo(zMax) != 1
                        && e.zMax.compareTo(zMin) != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param l A line segment to test for intersection.
     * @param scale scale
     * @param rm RoundingMode
     * @return {@code true} if this intersects with {@code l}.
     */
    public boolean isIntersectedBy(V3D_LineSegment l, int scale, RoundingMode rm) {
        V3D_Envelope le = l.getEnvelope3D();
        if (le.isIntersectedBy(getEnvelope3D())) {
            if (isIntersectedBy(l.p)) {
                return true;
            }
            if (isIntersectedBy(l.q)) {
                return true;
            }
            /**
             * Check if l intersects any of the finite planes which defines the
             * faces of the box.
             */
            V3D_Point p0p0p0 = new V3D_Point(e, xMin, yMin, zMin);
            V3D_Point p0p0p1 = new V3D_Point(e, xMin, yMin, zMax);
            V3D_Point p0p1p0 = new V3D_Point(e, xMin, yMax, zMin);
            V3D_Point p0p1p1 = new V3D_Point(e, xMin, yMax, zMax);
            V3D_Point p1p0p0 = new V3D_Point(e, xMax, yMin, zMin);
            V3D_Point p1p0p1 = new V3D_Point(e, xMax, yMin, zMax);
            V3D_Point p1p1p0 = new V3D_Point(e, xMax, yMax, zMin);
            V3D_Point p1p1p1 = new V3D_Point(e, xMax, yMax, zMax);
            V3D_FinitePlane x0 = new V3D_FinitePlane(p0p0p0, p0p0p1, p0p1p1);
            V3D_FinitePlane x1 = new V3D_FinitePlane(p1p0p0, p1p0p1, p1p1p1);
            V3D_FinitePlane y0 = new V3D_FinitePlane(p0p0p0, p1p0p0, p1p0p1);
            V3D_FinitePlane y1 = new V3D_FinitePlane(p0p1p0, p1p1p0, p1p1p1);
            V3D_FinitePlane z0 = new V3D_FinitePlane(p0p0p0, p1p0p0, p1p1p0);
            V3D_FinitePlane z1 = new V3D_FinitePlane(p0p0p1, p1p0p1, p1p1p1);
//        if(l.isIntersectedBy(x0) ||
//                l.isIntersectedBy(x1)) {
//            return true;
//        }
//        return false;
            throw new UnsupportedOperationException();
        } else {
            return false;
        }
    }

    /**
     * @param p The point to test for intersection.
     * @return {@code true} if this intersects with {@code p}
     */
    public boolean isIntersectedBy(V3D_Point p) {
        return isIntersectedBy(p.x, p.y, p.z);
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @return {@code true} if this intersects with {@code p}
     */
    public boolean isIntersectedBy(BigRational x, BigRational y, BigRational z) {
        return x.compareTo(xMin) != -1 && x.compareTo(xMax) != 1
                && y.compareTo(yMin) != -1 && y.compareTo(yMax) != 1
                && z.compareTo(zMin) != -1 && z.compareTo(zMax) != 1;
    }

    /**
     *
     * @param en The envelope to intersect.
     * @return {@code null} if there is no intersection; {@code en} if
     * {@code this.equals(en)}; otherwise returns the intersection.
     */
    public V3D_Envelope getIntersection(V3D_Envelope en) {
        if (this.equals(en)) {
            return en;
        }
        if (!this.isIntersectedBy(en)) {
            return null;
        }
        return new V3D_Envelope(e, BigRational.max(xMin, en.xMin),
                BigRational.min(xMax, en.xMax), BigRational.max(yMin, en.yMin),
                BigRational.min(yMax, en.yMax), BigRational.max(zMin, en.zMin),
                BigRational.min(zMax, en.zMax));
    }

    /**
     * Returns {@code null} if {@code this} does not intersect {@code l};
     * otherwise returns the intersection which is either a point or a line
     * segment.
     *
     * @param l The line to intersect.
     * @return {@code null} if there is no intersection; otherwise returns the
     * intersection.
     */
    public V3D_Geometry getIntersection(V3D_Line l) {
        // Check if l.p intersects
        //return null;
        throw new UnsupportedOperationException();
    }

    @Override
    public V3D_Envelope getEnvelope3D() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Envelope) {
            V3D_Envelope en = (V3D_Envelope) o;
            if (this.xMin.compareTo(en.xMin) == 0
                    && this.xMax.compareTo(en.xMax) == 0
                    && this.yMin.compareTo(en.yMin) == 0
                    && this.yMax.compareTo(en.yMax) == 0
                    && this.zMin.compareTo(en.zMin) == 0
                    && this.zMax.compareTo(en.zMax) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.xMin);
        hash = 43 * hash + Objects.hashCode(this.xMax);
        hash = 43 * hash + Objects.hashCode(this.yMin);
        hash = 43 * hash + Objects.hashCode(this.yMax);
        hash = 43 * hash + Objects.hashCode(this.zMin);
        hash = 43 * hash + Objects.hashCode(this.zMax);
        return hash;
    }

}
