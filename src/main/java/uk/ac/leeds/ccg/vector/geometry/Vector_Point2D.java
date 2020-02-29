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

import java.io.Serializable;
import java.math.BigDecimal;
import org.ojalgo.function.constant.BigMath;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.vector.core.Vector_Environment;

/**
 * 2D points.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Vector_Point2D extends Vector_Geometry2D
        implements Comparable, Serializable {

    /**
     * The x coordinate of the Vector_Point2D
     */
    public BigDecimal x;
    /**
     * The y coordinate of the Vector_Point2D
     */
    public BigDecimal y;

    /**
     * Creates a default Vector_Point2D with: x = null; y = null;
     *
     * @param ve
     */
    public Vector_Point2D(Vector_Environment ve) {
        super(ve);
    }

    /**
     * @param p Vector_Point2D
     */
    public Vector_Point2D(Vector_Point2D p) {
        super(p.e);
        x = new BigDecimal(p.x.toString());
        y = new BigDecimal(p.y.toString());
        dp = p.dp;
    }

    /**
     * @param p Vector_Point2D
     * @param dp What {@link #dp} is set to.
     */
    public Vector_Point2D(Vector_Point2D p, int dp) {
        super(p.e);
        x = new BigDecimal(p.x.toString());
        y = new BigDecimal(p.y.toString());
        this.dp = dp;
        applyDecimalPlacePrecision();
    }

    /**
     * x = new BigDecimal(a_Point2D.x.toString()); y = new
     * BigDecimal(a_Point2D.y.toString()); x =
     * VectorStaticBigDecimal.getRounded_BigDecimal( x, toRoundToX_BigDecimal);
     * y = VectorStaticBigDecimal.getRounded_BigDecimal( y,
     * toRoundToY_BigDecimal); setDecimalPlacePrecision( Math.max(
     * toRoundToX_BigDecimal.scale(), toRoundToY_BigDecimal.scale()));
     *
     * @param p Vector_Point2D
     * @param toRoundToX BigDecimal toRoundToX_BigDecimal
     * @param toRoundToY BigDecimal toRoundToY_BigDecimal
     */
    public Vector_Point2D(Vector_Point2D p, BigDecimal toRoundToX,
            BigDecimal toRoundToY) {
        super(p.e);
        x = new BigDecimal(p.x.toString());
        y = new BigDecimal(p.y.toString());
        x = e.getRounded_BigDecimal(x, toRoundToX);
        y = e.getRounded_BigDecimal(y, toRoundToY);
        dp = Math.max(toRoundToX.scale(), toRoundToY.scale());
    }

    /**
     * this.x = new BigDecimal(x.toString()); this.y = new
     * BigDecimal(y.toString());
     * setDecimalPlacePrecision(Math.max(x.scale(),y.scale()));
     *
     * @param x BigDecimal
     * @param y BigDecimal
     * @param ve
     */
    public Vector_Point2D(Vector_Environment ve, BigDecimal x, BigDecimal y) {
        super(ve);
        this.x = new BigDecimal(x.toString());
        this.y = new BigDecimal(y.toString());
        dp = Math.max(x.scale(), y.scale());
    }

    /**
     * @param e The vector environment.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param dp The decimal places.
     */
    public Vector_Point2D(Vector_Environment e, BigDecimal x, BigDecimal y,
            int dp) {
        super(e);
        this.x = new BigDecimal(x.toString());
        this.y = new BigDecimal(y.toString());
        this.dp = dp;
        applyDecimalPlacePrecision();
    }

    /**
     * @param e The vector environment.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param toRoundToX BigDecimal toRoundToX_BigDecimal
     * @param toRoundToY BigDecimal
     */
    public Vector_Point2D(Vector_Environment e, BigDecimal x, BigDecimal y,
            BigDecimal toRoundToX, BigDecimal toRoundToY) {
        super(e);
        this.x = e.getRounded_BigDecimal(x, toRoundToX);
        this.y = e.getRounded_BigDecimal(y, toRoundToY);
        dp = Math.max(toRoundToX.scale(), toRoundToY.scale());
    }

    /**
     * @param e The vector environment.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Vector_Point2D(Vector_Environment e, String x, String y) {
        super(e);
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
        dp = Math.max(this.x.scale(), this.y.scale());
    }

    /**
     * @param e The vector environment.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param dp The decimal places.
     */
    public Vector_Point2D(Vector_Environment e, String x, String y, int dp) {
        super(e);
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
        this.dp = dp;
        applyDecimalPlacePrecision();
    }

    /**
     * @param e The vector environment.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Vector_Point2D(Vector_Environment e, double x, double y) {
        super(e);
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
        dp = Math.max(this.x.scale(), this.y.scale());
    }

    /**
     * @param e The vector environment.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param dp The decimal places.
     */
    public Vector_Point2D(Vector_Environment e, double x, double y, int dp) {
        super(e);
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
        this.dp = dp;
        applyDecimalPlacePrecision();
    }

    /**
     * @param e The vector environment.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param toRoundToX For rounding x.
     * @param toRoundToY For rounding y.
     */
    public Vector_Point2D(Vector_Environment e, double x, double y,
            BigDecimal toRoundToX, BigDecimal toRoundToY) {
        super(e);
        this.x = e.getRounded_BigDecimal(new BigDecimal(x), toRoundToX);
        this.y = e.getRounded_BigDecimal(new BigDecimal(y), toRoundToY);
        dp = Math.max(toRoundToX.scale(), toRoundToY.scale());
    }

    /**
     * For rounding.
     *
     * @param r The number to round to.
     */
    public void roundTo(BigDecimal r) {
        x = e.getRounded_BigDecimal(x, r);
        y = e.getRounded_BigDecimal(y, r);
        dp = r.scale();
    }

    @Override
    public String toString() {
        return "Point2D(" + super.toString() + "x=" + x.toString() + " y=" 
                + y.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vector_Point2D) {
            if (hashCode() == ((Vector_Point2D) o).hashCode()) {
                if (compareTo(o) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.x != null ? this.x.hashCode() : 0);
        hash = 67 * hash + (this.y != null ? this.y.hashCode() : 0);
        return hash;
    }

    /**
     * @param o Object to compare to.
     * @return 0 if this is the same as o and +1 or -1 otherwise.
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof Vector_Point2D) {
            Vector_Point2D p = (Vector_Point2D) o;
            int cty = y.compareTo(p.y);
            if (cty != 0) {
                return cty;
            } else {
                return x.compareTo(p.x);
            }
        }
        return 1;
    }

    public boolean getIntersects(Vector_LineSegment2D l, int dpc) {
        return l.getIntersects(this, dpc);
    }

    /**
     * Get the distance to/from this to {@code p} precise to {@code dp2} number
     * of decimal places.
     *
     * @param p A point.
     * @param dp2 Decimal place precision.
     * @return The distance from {@code p} to this.
     */
    public BigDecimal getDistance(Vector_Point2D p, int dp2) {
        if (this.equals(p)) {
            return BigDecimal.ZERO;
        }
        BigDecimal diffx = this.x.subtract(p.x);
        BigDecimal diffy = this.y.subtract(p.y);
//        return BigMath.POW.invoke(diffx.pow(2).add(diffy.pow(2)),
//                BigMath.HALF);
        return Math_BigDecimal.sqrt(diffx.pow(2).add(diffy.pow(2)), dp2,
                e.bd.getRoundingMode());
    }

    /**
     * Get the angle from {@code p} to the Y-Axis clockwise.
     *
     * @param p A point.
     * @return Angle to the y axis clockwise.
     */
    public double getAngle_double(Vector_Point2D p) {
        double dx = p.x.doubleValue() - x.doubleValue();
        double dy = p.y.doubleValue() - y.doubleValue();
        if (dy == 0.0d) {
            if (dx == 0.0d) {
                return 0.0d;
            } else {
                if (dx > 0.0d) {
                    return Math.PI / 2.0d;
                } else {
                    return (3.0d * Math.PI) / 2.0d;
                }
            }
        } else {
            if (dy > 0.0d) {
                if (dx == 0.0d) {
                    return 0.0d;
                } else {
                    if (dx > 0.0d) {
                        return Math.atan(dx / dy);
                    } else {
                        return (2.0d * Math.PI) - Math.atan(Math.abs(dx) / dy);
                    }
                }
            } else {
                // dy < 0.0d
                if (dx == 0.0d) {
                    return Math.PI;
                } else {
                    if (dx > 0.0d) {
                        return Math.PI - Math.atan(dx / Math.abs(dy));
                    } else {
                        return Math.PI + Math.atan(Math.abs(dx) / Math.abs(dy));
                    }
                }
            }
        }
    }

    /**
     * Get the angle from {@code p} to the Y-Axis clockwise. Uses BigMath default precision.
     *
     * @param p A point.
     * @return Angle to the Y-Axis clockwise. Default 0.0d.
     */
    public BigDecimal getAngle_BigDecimal(Vector_Point2D p) {
        BigDecimal dx = p.x.subtract(x);
        BigDecimal dy = p.y.subtract(y);
        if (dy.compareTo(BigDecimal.ZERO) == 0) {
            if (dx.compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO;
            } else {
                if (dx.compareTo(BigDecimal.ZERO) == 1) {
                    return BigMath.HALF_PI;
                } else {
                    return new BigDecimal("3").multiply(BigMath.HALF_PI);
                }
            }
        } else {
            if (dy.compareTo(BigDecimal.ZERO) == 1) {
                if (dx.compareTo(BigDecimal.ZERO) == 0) {
                    return BigDecimal.ZERO;
                } else {
                    if (dx.compareTo(BigDecimal.ZERO) == 1) {
                        return BigMath.ATAN.invoke(BigMath.DIVIDE.invoke(dx, dy));
                    } else {
                        return BigMath.SUBTRACT.invoke(BigMath.TWO_PI,
                                BigMath.ATAN.invoke(BigMath.DIVIDE.invoke(
                                        BigMath.ABS.invoke(dx), dy)));
                        //return (2.0d * Math.PI) - Math.atan(Math.abs(dx) / dy);
                    }
                }
            } else {
                // dy < 0.0d
                if (dx.compareTo(BigDecimal.ZERO) == 0) {
                    return BigMath.PI;
                } else {
                    if (dx.compareTo(BigDecimal.ZERO) == 1) {
                        return BigMath.SUBTRACT.invoke(
                                BigMath.PI,
                                BigMath.ATAN.invoke(BigMath.DIVIDE.invoke(dx, BigMath.ABS.invoke(dy))));
                        //return Math.PI - Math.atan(dx / Math.abs(dy));
                    } else {
                        return BigMath.ADD.invoke(
                                BigMath.PI,
                                BigMath.ATAN.invoke(BigMath.DIVIDE.invoke(BigMath.ABS.invoke(dx), BigMath.ABS.invoke(dy))));
                        //return Math.PI + Math.atan(Math.abs(dx) / Math.abs(dy));
                    }
                }
            }
        }
    }

    public BigDecimal getGradient(Vector_Point2D p, int decimalPlacePrecision) {
        BigDecimal xDiff0 = x.subtract(p.x);
        BigDecimal yDiff0 = y.subtract(p.y);
        if (yDiff0.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ONE;
        } else {
            if (xDiff0.compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO;
            }
            return xDiff0.divide(yDiff0, decimalPlacePrecision, rm);
        }
    }

    @Override
    public Vector_Envelope2D getEnvelope2D() {
        return new Vector_Envelope2D(e, x, y);
    }

    @Override
    public final void applyDecimalPlacePrecision() {
        x = x.setScale(dp, rm);
        y = y.setScale(dp, rm);
    }

    /**
     * double[] result = new double[2]; result[0] = this.x.doubleValue();
     * result[1] = this.y.doubleValue(); return result;
     *
     * @return double[}
     */
    public double[] to_doubleArray() {
        double[] result = new double[2];
        result[0] = x.doubleValue();
        result[1] = y.doubleValue();
        return result;
    }
}
