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

/**
 * Provides utility for 3D spatial geometry.
 */
module uk.ac.leeds.ccg.v3d {

    requires transitive uk.ac.leeds.ccg.math;
    requires transitive uk.ac.leeds.ccg.generic;
    exports uk.ac.leeds.ccg.v3d.core;
    exports uk.ac.leeds.ccg.v3d.geometry;
    exports uk.ac.leeds.ccg.v3d.geometry.d;
    exports uk.ac.leeds.ccg.v3d.geometry.d.light;
    exports uk.ac.leeds.ccg.v3d.geometry.light;
}
