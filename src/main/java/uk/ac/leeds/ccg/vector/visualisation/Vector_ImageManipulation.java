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
package uk.ac.leeds.ccg.vector.visualisation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author geoagdt
 */
public class Vector_ImageManipulation {

    /**
     * Adapted from http://www.javalobby.org/articles/ultimate-image/
     * @param bi The image to be resized.
     * @param newWidth The new width for the image.
     * @param newHeight The new height for the image.
     * @return A BufferedImage resized
     */
    public static BufferedImage resize(
            BufferedImage bi,
            int newWidth,
            int newHeight) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        BufferedImage result = new BufferedImage(
                newWidth,
                newHeight,
                bi.TYPE_INT_RGB);
                //a_BufferedImage.getType());
        Graphics2D g = result.createGraphics();
//        g.setRenderingHint(
//                RenderingHints.KEY_INTERPOLATION,
//                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(
                bi,
                0,
                0,
                newWidth,
                newHeight,
                0,
                0,
                width,
                height,
                null);
        g.dispose();
        return result;
    }
}
