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

/**
 *
 * @author geoagdt
 */
public class Vector_SimplePoint2D { //extends Vector_AbstractGeometry2D {
    
    /**
     * The x coordinate of the VectorPoint2D
     */
    public BigDecimal _x;

    /**
     * The y coordinate of the VectorPoint2D
     */
    public BigDecimal _y;

    /**
     * Creates a default VectorPoint2D with:
     * _x = null;
     * _y = null;
     */
    public Vector_SimplePoint2D() {
    }

    /**
     * Creates a default VectorPoint2D with:
     * _x = x;
     * _y = y;
     * @param x BigDecimal
     * @param y BigDecimal
     */
    public Vector_SimplePoint2D(
            BigDecimal x,
            BigDecimal y) {
        _x = x;
        _y = y;
    }

    public BigDecimal getX() {
        return _x;
    }

    public void setX(BigDecimal _x) {
        this._x = _x;
    }

    public BigDecimal getY() {
        return _y;
    }

    public void setY(BigDecimal _y) {
        this._y = _y;
    }
}
