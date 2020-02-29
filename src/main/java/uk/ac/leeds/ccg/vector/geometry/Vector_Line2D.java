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
import org.ojalgo.function.BigFunction;

/**
 * Vector Line2D
 * @author Andy Turner
 * @version 1.0.0
 */
public class Vector_Line2D extends Vector_Geometry2D {

    /**
     * The angle clockwise from the Y-Axis which defines the line.
     */
    BigDecimal angle;

    /**
     * A point on the line
     */
    Vector_Point2D _Point2D;

    public Vector_Line2D(double angleClockwiseFromYAxis, Vector_Point2D p) {
        super(p.e);
        this.angle = new BigDecimal(angleClockwiseFromYAxis);
        this._Point2D = new Vector_Point2D(p);
        dp = Math.max(this.angle.scale(), this._Point2D.dp);
    }

    public Vector_Line2D(BigDecimal a_AngleClockwiseFromYAxis, Vector_Point2D p) {
        super(p.e);
        this.angle = new BigDecimal(a_AngleClockwiseFromYAxis.toString());
        this._Point2D = new Vector_Point2D(p);
        dp = Math.max(this.angle.scale(), this._Point2D.dp);
    }

    /**
     * Potential precision issues with Envelope extent...
     */
    @Override
    public Vector_Envelope2D getEnvelope2D() {
        //throw new UnsupportedOperationException("Not supported yet.");
        BigDecimal minusALot = new BigDecimal(Double.MIN_VALUE);
        BigDecimal plusALot = new BigDecimal(Double.MAX_VALUE);
        Vector_Point2D a_Point2D = new Vector_Point2D(
                e, minusALot, minusALot);
        Vector_Point2D b_Point2D = new Vector_Point2D(
                e, plusALot, plusALot);
        return new Vector_Envelope2D(a_Point2D, b_Point2D);
    }

    @Override
    public void applyDecimalPlacePrecision() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Vector_Point2D[] getExtremePointsOnLine(double ydiffExtremity) {
        Vector_Point2D[] result = new Vector_Point2D[2];
        BigDecimal ydiff_BigDecimal = new BigDecimal(ydiffExtremity);
        BigDecimal xdiff_BigDecimal
                = BigFunction.TAN.invoke(angle).multiply(ydiff_BigDecimal);
        result[0] = new Vector_Point2D(
                e,
                this._Point2D.x.add(xdiff_BigDecimal),
                this._Point2D.y.add(ydiff_BigDecimal));
        result[1] = new Vector_Point2D(
                e,
                this._Point2D.x.subtract(xdiff_BigDecimal),
                this._Point2D.y.subtract(ydiff_BigDecimal));
        return result;
    }

//    public Point2D[] getExtremePointsOnLine(double ydiffExtremity){
//        Point2D[] result = new Point2D[2];
//        BigDecimal ydiff_BigDecimal = new BigDecimal(ydiffExtremity);
//        BigDecimal xdiff_BigDecimal = new BigDecimal(Math.tan(angle.doubleValue()) * ydiffExtremity);
//        result[0] = new Point2D(
//                this._Point2D.x.add(xdiff_BigDecimal),
//                this._Point2D.y.add(ydiff_BigDecimal));
//       result[1] = new Point2D(
//                this._Point2D.x.subtract(xdiff_BigDecimal),
//                this._Point2D.y.subtract(ydiff_BigDecimal));
//        return result;
//    }
    public Vector_Geometry2D getIntersection(
            Vector_Line2D a_Line2D,
            double ydiffExtremity,
            BigDecimal tollerance,
            int a_DecimalPlacePrecision) {
        Vector_Geometry2D result;
        Vector_Point2D[] extremePointsOnThis = this.getExtremePointsOnLine(
                ydiffExtremity);
        Vector_LineSegment2D extremePointsOnThis_LineSegment2D = new Vector_LineSegment2D(
                extremePointsOnThis[0],
                extremePointsOnThis[1]);
        Vector_Point2D[] extremePointsOna_Line2D = a_Line2D.getExtremePointsOnLine(
                ydiffExtremity);
        Vector_LineSegment2D extremePointsOna_LineSegment2D = new Vector_LineSegment2D(
                extremePointsOna_Line2D[0],
                extremePointsOna_Line2D[1]);
        result = extremePointsOnThis_LineSegment2D.getIntersection(
                extremePointsOna_LineSegment2D,
                tollerance,
                a_DecimalPlacePrecision);
        return result;
    }
}
