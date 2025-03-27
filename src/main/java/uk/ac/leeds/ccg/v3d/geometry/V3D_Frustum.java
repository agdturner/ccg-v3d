/*
 * Copyright 2025 Andy Turner, University of Leeds.
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
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * A viewing frustum (https://en.wikipedia.org/wiki/Viewing_frustum) that
 * defines an open ended region of space bounded by 4 planes: {@link #left},
 * {@link #top}, {@link #right} and {@link #bottom} that intersect at 
 * {@link #focus}. A near rectangle is defined some distance along from 
 * {@link #focus}. This can be extended to also defined a bounded region with a 
 * far plane that is parallel to and beyond {@link #rect}.
 *
 * @author Andy Turner
 */
public class V3D_Frustum extends V3D_Geometry {

    private static final long serialVersionUID = 1L;

    /**
     * The focal point.
     */
    public final V3D_Point focus;

    /**
     * The rectangle.
     */
    public final V3D_Rectangle rect;
    
    /**
     * For storing the horizontal unit vector.
     */
    public V3D_Vector horizontalUV;

    /**
     * For storing the vertical unit vector.
     */
    public V3D_Vector verticalUV;
    
    /**
     * The top plane.
     */
    public final V3D_Plane top;

    /**
     * The bottom plane.
     */
    public final V3D_Plane bottom;

    /**
     * The left plane.
     */
    public final V3D_Plane left;

    /**
     * The right plane.
     */
    public final V3D_Plane right;

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param focus What {@link #focus} is set to.
     * @param rect What {@link #rect} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Frustum(V3D_Environment env, V3D_Vector offset,
            V3D_Point focus, V3D_Rectangle rect, int oom, RoundingMode rm) {
        super(env, offset);
        this.focus = focus;
        this.rect = rect;
        horizontalUV = rect.getPQR().getQR(oom, rm).l.v.getUnitVector(oom, rm);
        verticalUV = rect.getPQR().getPQ(oom, rm).l.v.getUnitVector(oom, rm);
        V3D_Point rectP = rect.getP(oom, rm);
        V3D_Point rectQ = rect.getQ(oom, rm);
        V3D_Point rectR = rect.getR(oom, rm);
        V3D_Point rectS = rect.getS(oom, rm);
        this.left = new V3D_Plane(focus, rectP, rectQ, oom, rm);
        this.top = new V3D_Plane(focus, rectQ, rectR, oom, rm);
        this.right = new V3D_Plane(focus, rectR, rectS, oom, rm);
        this.bottom = new V3D_Plane(focus, rectS, rectP, oom, rm);
    }

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param focus What {@link #focus} is set to.
     * @param direction The direction of the frustum from the focus.
     * @param horizontal A vector orthogonal to direction giving the horizontal
     * orientation.
     * @param rectDistance The distance to the centre of {@link #rect} from 
     * {@link focus}.
     * @param rectWidth The width of the rect rectangle.
     * @param rectHeight The height of the rect rectangle.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public V3D_Frustum(V3D_Environment env, V3D_Vector offset,
            V3D_Point focus, V3D_Vector direction, V3D_Vector horizontal,
            BigRational rectDistance, BigRational rectWidth, 
            BigRational rectHeight, int oom, RoundingMode rm) {
        super(env, offset);
        this.focus = focus;
        V3D_Vector directionUV = direction.getUnitVector(oom, rm);
        horizontalUV = horizontal.getUnitVector(oom, rm);
        verticalUV = directionUV.getCrossProduct(horizontalUV, oom, rm)
                .getUnitVector(oom, rm);
        // Init rect
        V3D_Point rectCentre = new V3D_Point(focus);
        rectCentre.translate(directionUV.multiply(rectDistance, oom, rm), oom, rm);
        V3D_Point rectP = new V3D_Point(rectCentre);
        rectP.translate(
                horizontalUV.multiply((rectWidth.divide(2)).negate(), oom, rm)
                .add(verticalUV.multiply((rectHeight.divide(2)).negate(), oom, rm)
                        , oom, rm), oom, rm);
        V3D_Point rectQ = new V3D_Point(rectCentre);
        rectP.translate(
                horizontalUV.multiply((rectWidth.divide(2)).negate(), oom, rm)
                .add(verticalUV.multiply(rectHeight.divide(2), oom, rm)
                        , oom, rm), oom, rm);
        V3D_Point rectR = new V3D_Point(rectCentre);
        rectP.translate(
                horizontalUV.multiply(rectWidth.divide(2), oom, rm)
                .add(verticalUV.multiply(rectHeight.divide(2), oom, rm)
                        , oom, rm), oom, rm);
        V3D_Point rectS = new V3D_Point(rectCentre);
        rectP.translate(
                horizontalUV.multiply(rectWidth.divide(2), oom, rm)
                .add(verticalUV.multiply((rectHeight.divide(2)).negate(), oom, rm)
                    , oom, rm), oom, rm);
        this.rect = new V3D_Rectangle(rectP, rectQ, rectR, rectS, oom, rm);
        this.left = new V3D_Plane(focus, rectP, rectQ, oom, rm);
        this.top = new V3D_Plane(focus, rectQ, rectR, oom, rm);
        this.right = new V3D_Plane(focus, rectR, rectS, oom, rm);
        this.bottom = new V3D_Plane(focus, rectS, rectP, oom, rm);
    }

    @Override
    public V3D_Geometry rotate(V3D_Ray r, V3D_Vector uv, Math_BigDecimal bd, BigRational theta, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public V3D_Geometry rotateN(V3D_Ray ray, V3D_Vector uv, Math_BigDecimal bd, BigRational theta, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
