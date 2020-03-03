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
import java.util.Objects;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * An envelope contains all the extreme values with respect to the X and Y axes.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Envelope extends V3D_Geometry implements V3D_FiniteGeometry {

    /**
     * The minimum x-coordinate.
     */
    public BigDecimal xMin;

    /**
     * The maximum x-coordinate.
     */
    public BigDecimal xMax;

    /**
     * The minimum y-coordinate.
     */
    public BigDecimal yMin;

    /**
     * The maximum y-coordinate.
     */
    public BigDecimal yMax;

    /**
     * The minimum z-coordinate.
     */
    public BigDecimal zMin;

    /**
     * The maximum z-coordinate.
     */
    public BigDecimal zMax;

    /**
     * @param e The vector environment.
     */
    public V3D_Envelope(V3D_Environment e) {
        super(e);
    }

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
     * @param a A point.
     * @param b A point.
     */
    public V3D_Envelope(V3D_Point a, V3D_Point b) {
        super(a.e);
        xMax = a.x.max(b.x);
        xMin = a.x.min(b.x);
        yMax = a.y.max(b.y);
        yMin = a.y.min(b.y);
        zMax = a.z.max(b.z);
        zMin = a.z.min(b.z);
    }

    /**
     * @param a A point.
     * @param b A point.
     * @param c A point.
     */
    public V3D_Envelope(V3D_Point a, V3D_Point b,
            V3D_Point c) {
        super(a.e);
        xMin = a.x.min(b.x).min(c.x);
        xMax = a.x.max(b.x).max(c.x);
        yMin = a.y.min(b.y).min(c.y);
        yMax = a.y.max(b.y).max(c.y);
        zMin = a.z.min(b.z).min(c.z);
        zMax = a.z.max(b.z).max(c.z);
    }

    /**
     * @param e The vector environment.
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     * @param z The z-coordinate of a point.
     */
    public V3D_Envelope(V3D_Environment e, BigDecimal x, BigDecimal y,
            BigDecimal z) {
        super(e);
        xMin = x;
        xMax = x;
        yMin = y;
        yMax = y;
        zMin = z;
        zMax = z;
    }

    /**
     *
     * @param g The geometries.
     */
    public V3D_Envelope(V3D_Point[] g) {
        super(g[0].e);
        V3D_Envelope en = g[0].getEnvelope3D();
        xMin = en.xMin;
        xMax = en.xMin;
        yMin = en.yMin;
        yMax = en.yMax;
        zMin = en.zMin;
        zMax = en.zMax;
        for (int i = 1; i < g.length; i++) {
            en = g[i].getEnvelope3D();
            xMin = xMin.min(en.xMin);
            xMax = xMax.max(en.xMax);
            yMin = yMin.min(en.yMin);
            yMax = yMax.max(en.yMax);
            zMin = zMin.min(en.zMin);
            zMax = zMax.max(en.zMax);
        }
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
        xMin = e.xMin.min(xMin);
        xMax = e.xMax.max(xMax);
        yMin = e.yMin.min(yMin);
        yMax = e.yMax.max(yMax);
        zMin = e.zMin.min(zMin);
        zMax = e.zMax.max(zMax);
        return this;
    }

    /**
     * If {@code e} touches, or overlaps then it intersects.
     *
     * @param e The Vector_Envelope2D to test for intersection.
     * @return {@code true} if this intersects with {@code e}.
     */
    public boolean getIntersects(V3D_Envelope e) {
        // Does this contain any corners of e
        boolean r = getIntersects(e.xMin, e.yMin, e.zMax);
        if (r) {
            return r;
        }
        r = getIntersects(e.xMin, e.yMax, e.zMax);
        if (r) {
            return r;
        }
        r = getIntersects(e.xMax, e.yMin, e.zMax);
        if (r) {
            return r;
        }
        r = getIntersects(e.xMax, e.yMax, e.zMax);
        if (r) {
            return r;
        }
        r = getIntersects(e.xMin, e.yMin, e.zMin);
        if (r) {
            return r;
        }
        r = getIntersects(e.xMin, e.yMax, e.zMin);
        if (r) {
            return r;
        }
        r = getIntersects(e.xMax, e.yMin, e.zMin);
        if (r) {
            return r;
        }
        r = getIntersects(e.xMax, e.yMax, e.zMin);
        if (r) {
            return r;
        }
        // Does e contain any corners of this
        r = e.getIntersects(xMax, yMax, zMax);
        if (r) {
            return r;
        }
        r = e.getIntersects(xMin, yMax, zMax);
        if (r) {
            return r;
        }
        r = e.getIntersects(xMax, yMin, zMax);
        if (r) {
            return r;
        }
        r = e.getIntersects(xMax, yMax, zMax);
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
     * A quick test for intersection between this and {@code l}.
     *
     * @param l A line segment to test for intersection.
     * @return -1 if no, 1 if yes, 0 if maybe.
     */
    public int getIntersectsFailFast(V3D_LineSegment l) {
        V3D_Envelope le = l.getEnvelope3D();
        if (le.getIntersects(getEnvelope3D())) {
            if (getIntersects(l.p)) {
                return 1;
            }
            if (getIntersects(l.q)) {
                return 1;
            }
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * @param l A line segment to test for intersection.
     * @param scale scale
     * @return {@code true} if this intersects with {@code l}.
     */
    public boolean getIntersects(V3D_LineSegment l, int scale) {
        /**
         * Check if the start or end of l is within this.
         */
        if (getIntersects(l.p)) {
            return true;
        }
        if (getIntersects(l.q)) {
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
        V3D_FinitePlane x0 = new V3D_FinitePlane(e, p0p0p0, p0p0p1, p0p1p1);
        V3D_FinitePlane x1 = new V3D_FinitePlane(e, p1p0p0, p1p0p1, p1p1p1);
        V3D_FinitePlane y0 = new V3D_FinitePlane(e, p0p0p0, p1p0p0, p1p0p1);
        V3D_FinitePlane y1 = new V3D_FinitePlane(e, p0p1p0, p1p1p0, p1p1p1);
        V3D_FinitePlane z0 = new V3D_FinitePlane(e, p0p0p0, p1p0p0, p1p1p0);
        V3D_FinitePlane z1 = new V3D_FinitePlane(e, p0p0p1, p1p0p1, p1p1p1);
//        if(l.getIntersects(x0) ||
//                l.getIntersects(x1)) {
//            return true;
//        }
//        return false;
        throw new UnsupportedOperationException();
    }

    /**
     * @param p The point to test for intersection.
     * @return {@code true} if this intersects with {@code p}
     */
    public boolean getIntersects(V3D_Point p) {
        return getIntersects(p.x, p.y, p.z);
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @return {@code true} if this intersects with {@code p}
     */
    public boolean getIntersects(BigDecimal x, BigDecimal y, BigDecimal z) {
        return x.compareTo(xMin) != -1 && x.compareTo(xMax) != 1
                && y.compareTo(yMin) != -1 && y.compareTo(yMax) != 1
                && z.compareTo(zMin) != -1 && z.compareTo(zMax) != 1;
    }

    @Override
    public V3D_Envelope getEnvelope3D() {
        return this;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Envelope) {
            V3D_Envelope en = (V3D_Envelope) o;
            if( this.xMin.compareTo(en.xMin) == 0 &&
                this.xMax.compareTo(en.xMax) == 0 &&
                this.yMin.compareTo(en.yMin) == 0 &&
                this.yMax.compareTo(en.yMax) == 0 &&
                this.zMin.compareTo(en.zMin) == 0 &&
                this.zMax.compareTo(en.zMax) == 0) {
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
