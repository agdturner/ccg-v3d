/*
 * Copyright 2019 Andy Turner, University of Leeds.
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
package uk.ac.leeds.ccg.vector.geometry;

import java.math.BigDecimal;
import uk.ac.leeds.ccg.vector.core.Vector_Environment;

/**
 * An envelope contains all the extreme values with respect to the X and Y axes.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Vector_Envelope2D extends Vector_Geometry2D {

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
     * @param e The vector enviornment.
     */
    public Vector_Envelope2D(Vector_Environment e) {
        super(e);
    }

    /**
     * @param e An envelope.
     */
    public Vector_Envelope2D(Vector_Envelope2D e) {
        super(e.e);
        yMin = new BigDecimal(e.yMin.toString());
        yMax = new BigDecimal(e.yMax.toString());
        xMin = new BigDecimal(e.xMin.toString());
        xMax = new BigDecimal(e.xMax.toString());
        dp = e.dp;
        //applyDecimalPlacePrecision();
    }

    /**
     * @param g A geometry.
     */
    public Vector_Envelope2D(Vector_Geometry2D g) {
        super(g.e);
        Vector_Envelope2D env = g.getEnvelope2D();
        xMin = env.xMin;
        xMax = env.xMin;
        yMin = env.yMin;
        yMax = env.yMax;
        dp = env.dp;
        //applyDecimalPlacePrecision();
    }

    /**
     * @param a A point.
     * @param b A point.
     */
    public Vector_Envelope2D(Vector_Point2D a, Vector_Point2D b) {
        super(a.e);
        if (a.y.compareTo(b.y) == 1) {
            yMin = b.y;
            yMax = a.y;
        } else {
            yMin = a.y;
            yMax = b.y;
        }
        if (a.x.compareTo(b.x) == 1) {
            xMin = b.x;
            xMax = a.x;
        } else {
            xMin = a.x;
            xMax = b.x;
        }
        dp = Math.max(a.dp, b.dp);
    }

    /**
     * @param e The vector environment.
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     */
    public Vector_Envelope2D(Vector_Environment e, BigDecimal x, BigDecimal y) {
        super(e);
        yMin = new BigDecimal(y.toString());
        yMax = new BigDecimal(y.toString());
        xMin = new BigDecimal(x.toString());
        xMax = new BigDecimal(x.toString());
        dp = Math.max(x.scale(), y.scale());
    }

    /**
     *
     * @param g The geometries.
     */
    public Vector_Envelope2D(Vector_Geometry2D[] g) {
        super(g[0].e);
        Vector_Envelope2D env = g[0].getEnvelope2D();
        xMin = env.xMin;
        dp = xMin.scale();
        xMax = env.xMin;
        dp = Math.max(dp, xMax.scale());
        yMin = env.yMin;
        dp = Math.max(dp, yMin.scale());
        yMax = env.yMax;
        dp = Math.max(dp, yMax.scale());
        for (int i = 1; i < g.length; i++) {
            env = g[i].getEnvelope2D();
            if (env.xMin.compareTo(xMin) == -1) {
                xMin = env.xMin;
                dp = Math.max(dp, xMin.scale());
            }
            if (env.xMax.compareTo(xMax) == 1) {
                xMax = env.xMax;
                dp = Math.max(dp, xMax.scale());
            }
            if (env.yMin.compareTo(yMin) == -1) {
                yMin = env.yMin;
                dp = Math.max(dp, yMin.scale());
            }
            if (env.yMax.compareTo(yMax) == 1) {
                yMax = env.yMax;
                dp = Math.max(dp, yMax.scale());
            }
        }
        applyDecimalPlacePrecision();
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "(" + super.toString()
                + "xMin=" + xMin.toString() + ", xMax=" + xMax.toString() + ","
                + "yMin=" + yMin.toString() + ", yMax=" + yMax.toString() + ")";
    }

//    public static Vector_Envelope2D envelope(
//            Vector_Envelope2D a,
//            Vector_Envelope2D b) {
//        Vector_Envelope2D result = new Vector_Envelope2D();
//        result.xMin = a.xMin.min(b.xMin);
//        result.yMin = a.yMin.min(b.yMin);
//        result.xMax = a.xMax.max(b.xMax);
//        result.yMax = a.yMax.max(b.yMax);
//        return result;
//    }
    public Vector_Envelope2D envelope(Vector_Envelope2D e) {
        Vector_Envelope2D r = new Vector_Envelope2D(e.e);
        r.xMin = e.xMin.min(this.xMin);
        r.yMin = e.yMin.min(this.yMin);
        r.xMax = e.xMax.max(this.xMax);
        r.yMax = e.yMax.max(this.yMax);
        return r;
    }

    /**
     * If {@code e} touches, or overlaps then it intersects.
     *
     * @param e The Vector_Envelope2D to test for intersection.
     * @return {@code true} if this intersects with {@code e}.
     */
    public boolean getIntersects(Vector_Envelope2D e) {
        // Does this contain any corners of e
        boolean r = getIntersects(e.xMin, e.yMin);
        if (r) {
            return r;
        }
        r = getIntersects(e.xMin, e.yMax);
        if (r) {
            return r;
        }
        r = getIntersects(e.xMax, e.yMin);
        if (r) {
            return r;
        }
        r = getIntersects(e.xMax, e.yMax);
        if (r) {
            return r;
        }
        // Does e contain and corners of this
        r = e.getIntersects(xMax, yMax);
        if (r) {
            return r;
        }
        r = e.getIntersects(xMin, yMax);
        if (r) {
            return r;
        }
        r = e.getIntersects(xMax, yMin);
        if (r) {
            return r;
        }
        r = e.getIntersects(xMax, yMax);
        if (r) {
            return r;
        }
        /**
         * Check to see if xMin and xMax are between e.xMin and e.xMax, and
         * e.yMin and e.yMax are between yMin and yMax.
         */
        if (e.xMax.compareTo(xMax) != 1 && e.xMax.compareTo(xMin) != -1
                && e.xMin.compareTo(xMax) != 1
                && e.xMin.compareTo(xMin) != -1) {
            if (yMin.compareTo(e.yMax) != 1 && yMin.compareTo(e.yMin) != -1
                    && yMax.compareTo(e.yMax) != 1
                    && yMax.compareTo(e.yMin) != -1) {
                return true;
            }
        }
        /**
         * Check to see if e.xMin and e.xMax are between xMax, and yMin and yMax
         * are between e.yMin and e.yMax.
         */
        if (xMax.compareTo(e.xMax) != 1 && xMax.compareTo(e.xMin) != -1
                && xMin.compareTo(e.xMax) != 1
                && xMin.compareTo(e.xMin) != -1) {
            if (e.yMin.compareTo(yMax) != 1 && e.yMin.compareTo(yMin) != -1
                    && e.yMax.compareTo(yMax) != 1
                    && e.yMax.compareTo(yMin) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * A quick test for intersection between this and {@code l}.
     *
     * @param l A line segment to test for intersection.
     * @return 0 if no, 1 if yes, 2 if maybe.
     */
    public int getIntersectsFailFast(Vector_LineSegment2D l) {
        Vector_Envelope2D a_Envelope2D = l.getEnvelope2D();
        if (a_Envelope2D.getIntersects(getEnvelope2D())) {
            if (getIntersects(l.start)) {
                return 1;
            }
            if (getIntersects(l.end)) {
                return 1;
            }
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * @param l A line segment to test for intersection.
     * @param t The tolerance.
     * @return {@code true} if this intersects with {@code l}.
     */
    public boolean getIntersects(Vector_LineSegment2D l, BigDecimal t) {
        return Vector_LineSegment2D.getIntersects(xMin, yMin, xMax, yMax, l, t,
                dp);
    }

    /**
     * @param p The point to test for intersection.
     * @return {@code true} if this intersects with {@code p}
     */
    public boolean getIntersects(Vector_Point2D p) {
        return p.x.compareTo(xMin) != -1 && p.x.compareTo(xMax) != 1
                && p.y.compareTo(yMin) != -1 && p.y.compareTo(yMax) != 1;
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @return {@code true} if this intersects with {@code p}
     */
    public boolean getIntersects(BigDecimal x, BigDecimal y) {
        return x.compareTo(xMin) != -1 && x.compareTo(xMax) != 1
                && y.compareTo(yMin) != -1 && y.compareTo(yMax) != 1;
    }

    @Override
    public Vector_Envelope2D getEnvelope2D() {
        return this;
    }

    @Override
    public final void applyDecimalPlacePrecision() {
        xMin = xMin.setScale(dp, rm);
        xMax = xMax.setScale(dp, rm);
        yMin = yMin.setScale(dp, rm);
        yMax = yMax.setScale(dp, rm);
    }
}
