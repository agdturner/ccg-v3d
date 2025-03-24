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
package uk.ac.leeds.ccg.v3d.geometry.d;

import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;

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
public class V3D_Frustum_d extends V3D_Geometry_d {

    private static final long serialVersionUID = 1L;

    /**
     * The focal point.
     */
    public final V3D_Point_d focus;

    /**
     * The rectangle.
     */
    public final V3D_Rectangle_d rect;
    
    /**
     * For storing the horizontal unit vector.
     */
    public V3D_Vector_d horizontalUV;

    /**
     * For storing the vertical unit vector.
     */
    public V3D_Vector_d verticalUV;
    
    /**
     * The top plane.
     */
    public final V3D_Plane_d top;

    /**
     * The bottom plane.
     */
    public final V3D_Plane_d bottom;

    /**
     * The left plane.
     */
    public final V3D_Plane_d left;

    /**
     * The right plane.
     */
    public final V3D_Plane_d right;

    /**
     * Create a new instance.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     * @param focus What {@link #focus} is set to.
     * @param rect What {@link #rect} is set to.
     */
    public V3D_Frustum_d(V3D_Environment_d env, V3D_Vector_d offset,
            V3D_Point_d focus, V3D_Rectangle_d rect) {
        super(env, offset);
        this.focus = focus;
        this.rect = rect;
        horizontalUV = rect.getPQR().getQR().l.v.getUnitVector();
        verticalUV = rect.getPQR().getPQ().l.v.getUnitVector();
        V3D_Point_d rectP = rect.getP();
        V3D_Point_d rectQ = rect.getQ();
        V3D_Point_d rectR = rect.getR();
        V3D_Point_d rectS = rect.getS();
        this.left = new V3D_Plane_d(focus, rectP, rectQ);
        this.top = new V3D_Plane_d(focus, rectQ, rectR);
        this.right = new V3D_Plane_d(focus, rectR, rectS);
        this.bottom = new V3D_Plane_d(focus, rectS, rectP);
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
     */
    public V3D_Frustum_d(V3D_Environment_d env, V3D_Vector_d offset,
            V3D_Point_d focus, V3D_Vector_d direction, V3D_Vector_d horizontal,
            double rectDistance, double rectWidth, double rectHeight) {
        super(env, offset);
        this.focus = focus;
        V3D_Vector_d directionUV = direction.getUnitVector();
        horizontalUV = horizontal.getUnitVector();
        verticalUV = directionUV.getCrossProduct(horizontalUV).getUnitVector();
        // Init rect
        V3D_Point_d rectCentre = new V3D_Point_d(focus);
        rectCentre.translate(directionUV.multiply(rectDistance));
        V3D_Point_d rectP = new V3D_Point_d(rectCentre);
        rectP.translate(horizontalUV.multiply(-rectWidth / 2d)
                .add(verticalUV.multiply(-rectHeight / 2d)));
        V3D_Point_d rectQ = new V3D_Point_d(rectCentre);
        rectP.translate(horizontalUV.multiply(-rectWidth / 2d)
                .add(verticalUV.multiply(rectHeight / 2d)));
        V3D_Point_d rectR = new V3D_Point_d(rectCentre);
        rectP.translate(horizontalUV.multiply(rectWidth / 2d)
                .add(verticalUV.multiply(rectHeight / 2d)));
        V3D_Point_d rectS = new V3D_Point_d(rectCentre);
        rectP.translate(horizontalUV.multiply(rectWidth / 2d)
                .add(verticalUV.multiply(-rectHeight / 2d)));
        this.rect = new V3D_Rectangle_d(rectP, rectQ, rectR, rectS);
        this.left = new V3D_Plane_d(focus, rectP, rectQ);
        this.top = new V3D_Plane_d(focus, rectQ, rectR);
        this.right = new V3D_Plane_d(focus, rectR, rectS);
        this.bottom = new V3D_Plane_d(focus, rectS, rectP);
    }
    
    @Override
    public V3D_Geometry_d rotate(V3D_Ray_d ray, V3D_Vector_d uv, double theta, double epsilon) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public V3D_Geometry_d rotateN(V3D_Ray_d ray, V3D_Vector_d uv, double theta, double epsilon) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
