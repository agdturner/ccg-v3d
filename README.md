# [ccg-v3d](https://github.com/agdturner/ccg-v3d)

## Description
A three-dimensional ([3D](https://en.wikipedia.org/wiki/Euclidean_space)) [Euclidean geometry](https://en.wikipedia.org/wiki/Euclidean_geometry)  Java library (that is modularised and based on [openJDK 17](https://openjdk.java.net/projects/jdk/17/). The only other dependency is [ccg-math](https://github.com/agdturner/ccg-math) - a stand alone modularised mathematics library.

Point positions in space are defined using 3D [cartesian](https://en.wikipedia.org/wiki/Cartesian_coordinate_system) coordinates with [orthogonal](https://en.wikipedia.org/wiki/Orthogonality) X, Y and Z axes that meet at the origin - a point <x,y,z> where x=y=z=0. Coordinates are either stored as [Math_BigRational](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRational.java) numbers - a subset of [rational numbers](https://en.wikipedia.org/wiki/Rational_number), or [Math_BigRationalSqrt](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRationalSqrt.java) - that also allowing for square roots (calculated to a given [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude) (OOM) precision as required). The coordinate system is "right handed", so if X increases to the right of this page, Y increases towards the top of this page, then Z increases out from the page, (see [Orientation](https://en.wikipedia.org/wiki/Orientation_(vector_space)) for details of handedness and the [note on the choice of right over left handedness](#handedness). This library originally began developement with a view to supporting 3D Geography and Earth Science applications, but it may support other scientific and non-scientific aplications. Notes about the [origin](#origin), and [geography](#geography) are also provided below.

## Latest versioned releases
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/ccg-v3d -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>ccg-v3d</artifactId>
    <version>0.13</version>
</dependency>
```
[JAR](https://repo1.maven.org/maven2/io/github/agdturner/ccg-v3d/0.13/ccg-v3d-0.13.jar)
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/ccg-v3d -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>ccg-v3d</artifactId>
    <version>0.14-SNAPSHOT</version>
</dependency>
```
## Details
Lighter geometries are in the [uk.ac.leeds.ccg.v3d.geometry.light](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/) package. These use [V3D_V](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_V.java) which have three Math_BigRational components related to each respective axis. Most classes extend [V3D_VGeometry](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VGeometry.java) - an abstract class that holds a V3D_V "offset" which can be thought of as the offset of the centroid of the geometry:
- [V3D_VPoint](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VPoint.java) - for representing points. A V3D_V instances called rel gives the location of the point relative to the offset.
- [V3D_VLine](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VLine.java) - for representing finite straight lines. V3D_V instances p and q gives the relative location of each end of the line.
- [V3D_VTriangle](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VTriangle.java) - for representing triangles. V3D_V instances p, q and r give the relative location of each point of the triangle.
- [V3D_VTetrahedron](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VTetrahedron.java) - for representing [tetrahedron]https://en.wikipedia.org/wiki/Tetrahedron. V3D_V instances p, q, r and s give the relative location of each point of the tetrahedron.

Heavier geometries use [V3D_Vector](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Vector.java)
which can be thought of as a vertex or vector with x, y, and z Math_BigRationalSqrt components. The magnitude can be calculated and stored to an [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude#Uses). [V3D_Geometry](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Geometry.java) is an abstract class with a V3D_Vector offset, and an [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude#Uses) (OOM). This class is extended to define the following geometries:
- [V3D_Point](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Point.java) - a point geometry where a V3D_Vector gives the position relative to the offset.
- [V3D_Line](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java) - an infinitte line geometry that passes through two defined points.
- [V3D_Ray](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Ray.java) - an infinite line geometry that extends from a point in one direction through another point.
- [V3D_Plane](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java) - an infinite [plane geometry](https://en.wikipedia.org/wiki/Plane_(geometry)). The plane is defined either by: 3 points (p, q and r) that are not [collinear](https://en.wikipedia.org/wiki/Collinearity) or coincident, or by a normal vector and a point (where all points orthogonal to the normal vector at the point are on the plane).
- [V3D_Envelope](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Envelope.java) - represents a [rectangular cuboid](https://en.wikipedia.org/wiki/Cuboid#Rectangular_cuboid) with edges aligned with the coordinate axes. The dimensions can collapse, so the envelope can form: a [rectangle](https://en.wikipedia.org/wiki/Rectangle) with all the x, or all the y, or all the z being the same; or a line segment with all the x and all the y being the same, or all the x and all the z being the same, or all the y and all the z being the same; or a point where all the x and all the y and all the z are the same. Envelopes are computationally useful for testing and calculating intersections and visibilities.
- [V3D_LineSegment](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_LineSegment.java) - represents a single continuous finite part of a line. Line segments are considered equal irrespective of the order of the end points. The centroid of a line segment can be computed and stored.
- [V3D_Triangle](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Triangle.java) - a triangular part of a plane. The three coplanar points {p, q and r} are the corners of the triangle. Triangle sides can be stored as line segments. The centroid of a triangle can be computed and stored.
- [V3D_Tetrahedron](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Tetrahedron.java) - represents a [tetrahedron](https://en.wikipedia.org/wiki/Tetrahedron). Any selection of three points are not coplanar with the fourth. A tetrahedron surface can be thought of as compising of four triangles. The [centroid](https://en.wikipedia.org/wiki/Centroid#Of_a_tetrahedron_and_n-dimensional_simplex) and the [volume](https://en.wikipedia.org/wiki/Tetrahedron#Volume) can be computed and stored.

Geometry collections of the same types of geometry are being supported, including:
- [V3D_Points](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Points.java) - for collections of points.
- [V3D_LineSegments](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_LineSegments.java) - for collections of collinear line segments. 
- [V3D_Triangles](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Triangles.java) - for collections of coplanar triangles. 
- [V3D_Tetrahedrons](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Tetrahedrons.java) - for collections of tetrahedrons.

## Development plans and progress
- There is functionality for calculating the intersection and minimum distance between most, but not all geometries.
- For a 1.0 release, the aim is to provide robust instersection tests and computations, and centroid and distance computations:

### Intersection
- So far, methods for testing if there is an intersection and for retrieving the intersection are implemented for:
-- point-plane, point-line, point-ray, point-line_segement, point-rectangle, point-triangle
-- plane-plane, plane-line, plane-ray, plane-line_segment
-- line-line, line-ray, line-line_segment
-- ray-ray, ray-line_segment
-- line_segment-line_segment
- What is next to do:
-- plane-triangle, plane-rectangle
-- line_segment-triangle, line_segment-rectangle
-- triangle-triangle, triangle-rectangle
-- rectangle-rectangle
- It would be good to have functionality that distinguishes between geometries that touch at a point, along a line or over an area; or that overlap crossing all or part of the other geometries.

### Distance, Areas, Perimeters, Volumes
- So far, methods for calculating the minimum distance between geometries are implemented for:
-- point-plane, point-line, point-ray, point-line_segment, point-rectangle, point-triangle
-- plane-plane, plane-line, plane-ray, plane-line_segment
-- line-line, line-ray, line-line_segment
-- ray-ray, ray-line_segment
-- line_segment-line_segment

## Development
- Whilst translating geometries is now supported, it is not possible to scale or rotate or warp them. It would be good to have these things for modelling things in reality. Perhaps the next progression is to support rotation about the centroid in line with each axis.
- [Apache Commons Geometry](https://commons.apache.org/proper/commons-geometry/) (see also: [Apache Commons Geometry GitHub Repository](https://github.com/apache/commons-geometry)) appears to be developing similar arbitrary precision geometrical functionality... 

### Origins
The library began development in March 2020 as a pet project to try to create a simple gravitational model of our [solar system](https://en.wikipedia.org/wiki/Solar_system) and learn more about 3D modelling.
### Summary of changes
#### 0.13 to 0.14
- Added a package of lightweight geometries.
#### 0.10 to 0.13
- Added an offset vector for geometries to allow them to be translated.
#### 0.9 to 0.10
- Added V3D_Tetrahedron class, and V3D_TetrahedronPoly, V3D_TrianglePolyPlanar and V3D_LineSegmentPolyCollinear collection classes.
#### 0.8 to 0.9
- Change from left-handed to right handed coordinate system.
#### 0.7 to 0.8
- Simplifications to the intersection methods removing the static methods.
- Added a V3D_Ray class.

## Contributions
- Welcome, but let's communicate and organise :)

## LICENCE
- APACHE LICENSE, VERSION 2.0: https://www.apache.org/licenses/LICENSE-2.0

## Acknowledgements and thanks
- The [University of Leeds](http://www.leeds.ac.uk) for supporting the development of this library and some of the dependencies.
- Thank you to those that have and continue to develop the Java language.
- Thank you Eric (_et al._) for the [BigMath](https://github.com/eobermuhlner/big-math) library.
- Thank you developers and maintainers of [Apache Maven](https://maven.apache.org/), [Apache NetBeans](https://netbeans.apache.org/), [git](https://git-scm.com/) and [GitHub](http://github.com) for supporting the development of this code and for providing a means of creating a community of users/developers.
- Thank you developers, maintainers and contributors of other useful open source Java libraries and relevent information content made available on the Web. Information that has helped me develop this library is cited in the source code.

# Notes

## [origin](https://en.wikipedia.org/wiki/Origin_(mathematics))
For a solar system model or a model of Earth, it is probably best to set the origin at the [centre of mass](https://en.wikipedia.org/wiki/Center_of_mass) (CoM).

## Handedness
Handedness or [chirality](https://en.wikipedia.org/wiki/Chirality_(physics)) concerns the assignment of coordinate axes/directions. The choice of left-handed or right-handed is somewhat arbitrary, but this library uses a right-handed system as is more commonly used in much of geography and physics ([video](https://youtu.be/BoHQtXpWG2Y)). Originally the library began using a left-handed system based on the logic of vertical viewing screens, graphs and zooming in and out by the viewer moving closer and further away from the screen.

## Geography
- [Spherical](https://en.wikipedia.org/wiki/Spherical_coordinate_system) and [ecliptic](https://en.wikipedia.org/wiki/Ecliptic_coordinate_system) coordinate systems are becoming more commonly used in Geography. See also: [Discrete_global_grid](https://en.wikipedia.org/wiki/Discrete_global_grid#Standard_equal-area_hierarchical_grids).
- [Geographical projections](https://en.wikipedia.org/wiki/List_of_map_projections) are commonly used in geography to represent part or all of the surface of Earth as plan view maps. [Equirectangular_projection](https://en.wikipedia.org/wiki/Equirectangular_projection)s have: the Y Axis aligned with lines of [latitude](https://en.wikipedia.org/wiki/Latitude), with zero on the [equator](https://en.wikipedia.org/wiki/Equator), increasing to the [North pole](https://en.wikipedia.org/wiki/North_Pole), and decreasing to the [South pole](https://en.wikipedia.org/wiki/South_Pole); the X Axis aligned with lines of [longitude](https://en.wikipedia.org/wiki/Longitude) with zero at the [prime meridian](https://en.wikipedia.org/wiki/Prime_meridian) and increasing to the [East](https://en.wikipedia.org/wiki/East) and decreasing to the [West](https://en.wikipedia.org/wiki/West); and the Z axis represents height above [sea-level](https://en.wikipedia.org/wiki/Sea_level) or depth below sea-level. The choice of the [meridian](https://en.wikipedia.org/wiki/Meridian_(geography)) is arbitrary. For a 3D coordinate system with the origin at the centre of mass of Earth, the Y axis (the axis of [Earth's rotation](https://en.wikipedia.org/wiki/Earth%27s_rotation)) can continue to be aligned with latitude. With this, the X and Z axes intersect the equator.
