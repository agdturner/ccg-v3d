/*
 * Copyright 2021 Centre for Computational Geography.
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

import java.io.Serializable;

/**
 * V3D_VolumeDouble
 *
 * @author Andy Turner
 */
public interface V3D_VolumeDouble extends Serializable {

    /**
     * For calculating and returning the area.
     *
     * @return The area.
     */
    public abstract double getArea();

    /**
     * For calculating and returning the perimeter.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @return The Perimeter.
     */
    public abstract double getVolume(double epsilon);

}
