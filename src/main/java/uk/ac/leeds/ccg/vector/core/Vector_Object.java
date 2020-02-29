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
package uk.ac.leeds.ccg.vector.core;

import java.io.Serializable;

/**
 * Vector Object
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Vector_Object implements Serializable {

    public Vector_Environment e;

    /**
     * @param e What {@link e} is set to.
     */
    public Vector_Object(Vector_Environment e) {
        this.e = e;
    }

}
