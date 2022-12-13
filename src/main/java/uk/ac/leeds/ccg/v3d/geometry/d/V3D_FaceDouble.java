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
package uk.ac.leeds.ccg.v3d.geometry.d;

/**
 * V3D_Face
 *
 * @author Andy Turner
 * @version 1.0
 */
public interface V3D_FaceDouble {

    /**
     * For calculating and returning the perimeter.
     * @return The Perimeter.
     */
    public abstract double getPerimeter();

    /**
     * For calculating and returning the area.
     * @return The area.
     */
    public abstract double getArea();
}
