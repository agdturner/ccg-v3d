# [ccg-v3d](https://github.com/agdturner/ccg-v3d)

## Description
A modular three-dimensional ([3D](https://en.wikipedia.org/wiki/Euclidean_space)) [Euclidean geometry](https://en.wikipedia.org/wiki/Euclidean_geometry) Java library.

There are two main implementations in the library that are distinguished by the type of numbers used for calculations and to represent coordinates:

1. Coordinates and calculations using Java double precision primitive numbers.
2. Coordinates and calculations using a combination of [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java) and [Math_BigRationalSqrt](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRationalSqrt.java) numbers.

Vectors are defined using [cartesian](https://en.wikipedia.org/wiki/Cartesian_coordinate_system) coordinates with [orthogonal](https://en.wikipedia.org/wiki/Orthogonality) X, Y and Z axes that meet at the origin - a point <x,y,z> where x=y=z=0. Geometry based on coordinates stored as Java double precision numbers typically use a small tollerance value (epsilon) to evaluate whether two vectors are the same. The accuracy of this geometry is variable which is due to the nature of [floating point arithmetic](https://en.wikipedia.org/wiki/Floating-point_arithmetic). Coordinates stored using [Math_BigRationalSqrt](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRationalSqrt.java) can have non-rational square root values and calculation can be done with these where the user can specify the [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude) (OOM) of the precision desired.

The coordinate system is "right handed", so: if X increases to the right of this page; and, Y increases towards the top of this page, then Z increases out from the page, (see [Orientation](https://en.wikipedia.org/wiki/Orientation_(vector_space)) and the [note on the choice of right over left handedness](#handedness) for details of why this handedness was chosen.

The original intention was for this library to support large scale Earth Science applications.

The development of the library is aided by [ccg-r3d](https://github.com/agdturner/ccg-r3d) - a rendering library for visualising 3D spatial geometry.

## Details
### Lightweight Geometry
- These are in the [uk.ac.leeds.ccg.v3d.geometry.light](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/) and [uk.ac.leeds.ccg.v3d.geometry.d.light](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/d/light/) packages. These use [V3D_V](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_V.java) or [V3D_VDouble](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/d/light/V3D_VDouble.java) as vectors which can be used to represent points.
- [V3D_VLine](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VLine.java) and [V3D_VLineDouble](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/d/light/V3D_VLineDouble.java) representing finite straight lines and comprise two vectors.
- [V3D_VTriangle](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VTriangle.java) and [V3D_VTriangleDouble](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/d/light/V3D_VTriangleDouble.java) represent triangles as three finite lines which are the edges of the triangle.
- [V3D_VTetrahedron](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VTetrahedron.java) and [V3D_VTetrahedronDouble](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/d/light/V3D_VTetrahedronDouble.java) represent [tetrahedra](https://en.wikipedia.org/wiki/Tetrahedra) which comprise 4 triangles.
### Heavyweight Geometry
- These are in the [uk.ac.leeds.ccg.v3d.geometry](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/) and [uk.ac.leeds.ccg.v3d.geometry.d](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/d/) packages. 
- [V3D_Vector](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Vector.java) and [V3D_VectorDouble](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/d/V3D_VectorDouble.java) are used for vectors
- [V3D_Point](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Point.java) and [V3D_PointDouble](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/d/V3D_PointDouble.java) represent points. A vector called rel gives the location of the point relative to another vector called offset.
- [V3D_Line](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java) and [V3D_LineDouble](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/d/V3D_LineDouble.java) represent infinite lines that pass through a point and along a vector.
- [V3D_Ray](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Ray.java) and [V3D_RayDouble](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/d/V3D_RayDouble.java) represent rays that extends from a point in one direction given by a vector.
- [V3D_Plane](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java) and [V3D_PlaneDouble](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/d/V3D_PlaneDouble.java) represent infinite [plane geometry](https://en.wikipedia.org/wiki/Plane_(geometry)). The plane is defined by a point and a normal vector and can also be constructed using 3 points that are not [collinear](https://en.wikipedia.org/wiki/Collinearity) or coincident.
- [V3D_Envelope](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Envelope.java) and [V3D_EnvelopeDouble](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/d/V3D_EnvelopeDouble.java) represent Axis Aligned Bounding Boxes. The dimensions of these can collapse so the envelope can be considered more like a [rectangle](https://en.wikipedia.org/wiki/Rectangle) with all the x, or all the y, or all the z being the same; or a line segment with all the x and all the y being the same, or all the x and all the z being the same, or all the y and all the z being the same; or a point where all the x and all the y and all the z are the same. Envelopes are useful for ruling out intersections.
- [V3D_LineSegment](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_LineSegment.java) and [V3D_LineSegmentDouble](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/d/V3D_LineSegmentDouble.java) are for representing single continuous finite lines between two points.
- [V3D_Triangle](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Triangle.java) and [V3D_TriangleDouble](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/d/V3D_TriangleDouble.java) are for triangles. These are defined by a plane and three points.
- [V3D_Tetrahedron](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Tetrahedron.java) and [V3D_TetrahedronDouble](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/d/V3D_TetrahedronDouble.java) represent [tetrahedra](https://en.wikipedia.org/wiki/Tetrahedra).

## Geometry collections
- There are rudimentary classes for: collections of collinear and coplanar points and line segments; Rectangle, coplanar [convex hull](https://en.wikipedia.org/wiki/Convex_hull)s and polygon; coplanar and non-coplanar triangles; and tetrahedrons.

## Development plans and progress
- Translating (moving) geometries to new locations is supported.
- Rotating geometries is supported.
- Scaling and warping geometries is not yet supported.
- [Apache Commons Geometry](https://commons.apache.org/proper/commons-geometry/) (see also: [Apache Commons Geometry GitHub Repository](https://github.com/apache/commons-geometry)) appears to be developing some similar arbitrary precision geometrical functionality... 
- Intersections
-- It would be useful to be able to distinguish between geometries that touch at a point, along a line or line_segment or over an area; and those that overlap (all or part of) another geometry.
-- Geometry intersections calculations for triangular or simpler geometries are supported.
-- Implementations of methods to calculate the intersection between tetrahedrons and simpler geometries are wanted.
- Distances
-- There are methods for calculating the minimum distance between geometries including triangles and simpler geometries. 
-- Implementations of methods to calulate distances between tetrahedrons and simpler geometries are wanted.
- New geometries are wanted for surfaces.
- Surface Areas, Perimeters and Volumes
-- For some shapes there are implementations of methods for calculating these.

## Contributions and collaboration
- Please submit issues.

## LICENCE
- APACHE LICENSE, VERSION 2.0: https://www.apache.org/licenses/LICENSE-2.0

## Acknowledgements and thanks
- The [University of Leeds](http://www.leeds.ac.uk) and externally funded research projects supported the development of some of the library dependencies.
- Thank you Eric for the [BigMath](https://github.com/eobermuhlner/big-math) library.

# Notes

## [origin](https://en.wikipedia.org/wiki/Origin_(mathematics))
For a solar system model or a model of Earth, it is probably best to set the origin at the [centre of mass](https://en.wikipedia.org/wiki/Center_of_mass) (CoM).

## Handedness
Handedness or [chirality](https://en.wikipedia.org/wiki/Chirality_(physics)) concerns the assignment of coordinate axes/directions. The choice of left-handed or right-handed is somewhat arbitrary, but this library uses a right-handed system as is more commonly used in much of geography and physics ([video](https://youtu.be/BoHQtXpWG2Y)). Originally the library began using a left-handed system based on the logic of vertical viewing screens, graphs and zooming in and out by the viewer moving closer and further away from the screen.

## Projections
- [Spherical](https://en.wikipedia.org/wiki/Spherical_coordinate_system) and [ecliptic](https://en.wikipedia.org/wiki/Ecliptic_coordinate_system) coordinate systems and [Discrete_global_grid](https://en.wikipedia.org/wiki/Discrete_global_grid#Standard_equal-area_hierarchical_grids) systems are being used for some applications.
- [Geographical projections](https://en.wikipedia.org/wiki/List_of_map_projections) are commonly used to represent part or all of the surface of Earth as plan view maps. [Equirectangular_projection](https://en.wikipedia.org/wiki/Equirectangular_projection)s have: the Y Axis used for lines of [latitude](https://en.wikipedia.org/wiki/Latitude), with zero on the [equator](https://en.wikipedia.org/wiki/Equator), increasing to the [North pole](https://en.wikipedia.org/wiki/North_Pole), and decreasing to the [South pole](https://en.wikipedia.org/wiki/South_Pole); the X Axis used for lines of [longitude](https://en.wikipedia.org/wiki/Longitude), typically with zero on the [prime meridian](https://en.wikipedia.org/wiki/Prime_meridian), increasing to the [East](https://en.wikipedia.org/wiki/East), and decreasing to the [West](https://en.wikipedia.org/wiki/West); and the Z axis represents height (often a measure of a surface height like above or below mean [sea-level](https://en.wikipedia.org/wiki/Sea_level). The choice of the [meridian](https://en.wikipedia.org/wiki/Meridian_(geography)) is arbitrary. For a 3D coordinate system with the origin at the centre of mass of Earth, the Y axis could be used for the axis of [Earth's rotation](https://en.wikipedia.org/wiki/Earth%27s_rotation)) aligning with latitude...

## References
### Similar software for review
- [VTK-m](https://m.vtk.org/) - [README](https://gitlab.kitware.com/vtk/vtk-m/blob/master/README.md) - [VTK-m GitHub Mirror](https://github.com/Kitware/VTK-M)
  - Languages: C/C++.
  - Uses floating point for the coordinate system.
- [VisIt](https://visit-dav.github.io/visit-website/index.html)
  - Open Source, interactive, scalable, visualization, animation and analysis tool.
  - Languages: C 75.8%, C++ 12.8%, Python 3.8%, Java 3.0%
  - [Github repository](https://github.com/visit-dav/visit/)
- [Fides](https://fides.readthedocs.io/en/latest/)
  - Fides enables complex scientific workflows to seamlessly integrate simulation and visualization. This is done by providing a data model in JSON that describes the mesh and fields in the data to be read. Using this data model, Fides maps [ADIOS2](https://github.com/ornladios/ADIOS2) data arrays (from files or streams) to [VTK-m](https://m.vtk.org/) datasets, enabling visualization of the data using shared- and distributed-memory parallel algorithms.
  - Can be used with Paraview
- [ParaView](https://www.paraview.org/)
  - Open source post-processing visualization engine.
  - Uses [VTK](https://vtk.org/) - an open-source, freely available software system for 3D computer graphics, modeling, image processing, volume rendering, scientific visualization, and 2D plotting - [VTK GitHub Mirror](https://github.com/Kitware/VTK)
