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
package uk.ac.leeds.ccg.vector.grids;

import java.math.BigDecimal;
import uk.ac.leeds.ccg.grids.d2.Grids_2D_ID_long;
import uk.ac.leeds.ccg.grids.d2.grid.Grids_Dimensions;
import uk.ac.leeds.ccg.grids.d2.grid.d.Grids_GridDouble;
import uk.ac.leeds.ccg.vector.core.Vector_Environment;
import uk.ac.leeds.ccg.vector.core.Vector_Object;
import uk.ac.leeds.ccg.vector.geometry.Vector_LineSegment2D;

/**
 * Vector Line Grid
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Vector_LineGrid extends Vector_Object {

    protected Vector_LineGrid(Vector_Environment ve) {
        super(ve);
    }

    /**
     * Adds the intersection length of {@code l} to each cell of {@code g}.
     *
     * @param g The grid.
     * @param l The line.
     * @param factor Value to multiply the length of {@code l} by.
     * @param tollerance
     * @param hoome
     * @throws Exception If encountered.
     */
    public static void addToGrid(Grids_GridDouble g, Vector_LineSegment2D l, 
            double factor, BigDecimal tollerance) throws Exception {
        int decimalPlacePrecision = 10;
//        int chunkNRows;
//        chunkNRows = g.getChunkNRows();
//        int chunkNCols;
//        chunkNCols = g.getChunkNCols();
        Grids_Dimensions dimensions;
        dimensions = g.getDimensions();
//        BigDecimal cellsize;
//        cellsize = dimensions[0];
        BigDecimal xmin = dimensions.getXMin();
        BigDecimal ymin = dimensions.getYMin();
        BigDecimal xmax = dimensions.getXMax();
        BigDecimal ymax = dimensions.getYMax();
        BigDecimal halfCellsize = dimensions.getHalfCellsize();
        Integer directionIn;
        directionIn = null;
        //System.out.println("line length " + l.getLength(decimalPlacePrecision));
        long nrows = g.getNRows();
        long ncols = g.getNCols();
        // Check if line intersect grid and if not return fast.
        if (Vector_LineSegment2D.getIntersects(xmin, ymin, xmax, ymax, l,
                tollerance, decimalPlacePrecision)) {
            long row;
            row = g.getRow(l.start.y);
            long col;
            col = g.getCol(l.start.x);
            Grids_2D_ID_long cellID = g.getCellID(row, col);
            BigDecimal[] cellBounds = g.getCellBounds(halfCellsize, row, col);
            Object[] lineToIntersectIntersectPointDirection
            = Vector_LineSegment2D.getLineToIntersectLineRemainingDirection(
                    tollerance, xmin, ymin, xmax, ymax, l, directionIn, decimalPlacePrecision);
            Vector_LineSegment2D lineToIntersect = (Vector_LineSegment2D) lineToIntersectIntersectPointDirection[0];
            //System.out.println("lineToIntersect " + lineToIntersect);
            double length = lineToIntersect.getLength(decimalPlacePrecision).doubleValue();
            double v = length * factor;
            //System.out.println("lineToIntersect length " + length);
            g.addToCell(cellID, v);
            if (lineToIntersectIntersectPointDirection[2] == null) {
                return;
            }
            Vector_LineSegment2D remainingLine;
            remainingLine = (Vector_LineSegment2D) lineToIntersectIntersectPointDirection[1];
            //System.out.println("remainingLine " + remainingLine);
            BigDecimal remainingLineLength;
            remainingLineLength = remainingLine.getLength(decimalPlacePrecision);
            //System.out.println("remainingLine length " + remainingLineLength);
            Integer directionOut;
            directionOut = (Integer) lineToIntersectIntersectPointDirection[2];
            //System.out.println("directionOut " + directionOut);
            //while (remainingLineLength.compareTo(BigDecimal.ZERO) == 1) { // Change for a tollerance.
            while (!(remainingLineLength.compareTo(tollerance) == -1 && remainingLineLength.compareTo(tollerance.negate()) == 1)) {
                //System.out.println("remainingLineLength " + remainingLineLength);
                if (directionOut == 0) {
                    row++;
                }
                if (directionOut == 1) {
                    row++;
                    col++;
                }
                if (directionOut == 2) {
                    col++;
                }
                if (directionOut == 3) {
                    col++;
                    row--;
                }
                if (directionOut == 4) {
                    row--;
                }
                if (directionOut == 5) {
                    col--;
                    row--;
                }
                if (directionOut == 6) {
                    col--;
                }
                if (directionOut == 7) {
                    col--;
                    row++;
                }
                if (row < 0 || row >= nrows
                        || col < 0 || col >= ncols) {
                    return;
                }
                //System.out.println("cellRowIndex " + cellRowIndex + ", cellColIndex " + cellColIndex);
                cellID = g.getCellID(row, col);
                cellBounds = g.getCellBounds(halfCellsize, row, col);
                lineToIntersectIntersectPointDirection = Vector_LineSegment2D.getLineToIntersectLineRemainingDirection(
                        tollerance, xmin, ymin, xmax, ymax, remainingLine, directionOut, decimalPlacePrecision);
                lineToIntersect = (Vector_LineSegment2D) lineToIntersectIntersectPointDirection[0];
                //System.out.println("lineToIntersect " + lineToIntersect);
                length = lineToIntersect.getLength(decimalPlacePrecision).doubleValue();
                //System.out.println("lineToIntersect length " + length);
                v = length * factor;
                g.addToCell(cellID, v);
                if (lineToIntersectIntersectPointDirection[2] == null) {
                    return;
                }
                remainingLine = (Vector_LineSegment2D) lineToIntersectIntersectPointDirection[1];
                //System.out.println("remainingLine " + remainingLine);
                remainingLineLength = remainingLine.getLength(decimalPlacePrecision);
                //System.out.println("remainingLine length " + remainingLineLength);
                directionOut = (Integer) lineToIntersectIntersectPointDirection[2];
                //System.out.println("directionOut " + directionOut);
            }
        }
    }
}
