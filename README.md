# [ccg-v3d](https://github.com/agdturner/ccg-v3d)

## Description
A modularised Java three-dimensional ([3D](https://en.wikipedia.org/wiki/Euclidean_space)) [Euclidean geometry](https://en.wikipedia.org/wiki/Euclidean_geometry) library.

The dimensions are defined by [orthogonal](https://en.wikipedia.org/wiki/Orthogonality) axes X, Y and Z that meet at the [cartesian](https://en.wikipedia.org/wiki/Cartesian_coordinate_system) origin - a point <x, y, z> where the coordinates x=y=z=0. Coordinates are stored as [Math_BigRationalSqrt](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRationalSqrt.java) numbers. These store the square and the square root (or an appoximation of the square root) given an [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude#Uses) for a set of rational numbers (comprising a numerator and denominator [BigDecimal](https://cr.openjdk.java.net/~iris/se/17/latestSpec/api/java.base/java/math/BigDecimal.html)).

Conceptually, the cartesian is [right-handed](https://en.wikipedia.org/wiki/Right-hand_rule). Notes on [handedness](#handedness), the [origin](#origin), and [geography](#geography) are provided below. This library originally began developement with a view to supporting 3D Geography and Earth Science applications, but it may support other uses too.

The code development aspires to be sustainable. An effort is being made to provide comprehensive unit tests developed along with the functional methods.

The current focus is to represent basic shapes and to provide instersection, centroid and distance calculation code for:
- Envelopes - [rectangular cuboid](https://en.wikipedia.org/wiki/Cuboid#Rectangular_cuboid)s with sides aligned with the coordinate axes 
- Straight [line segment](https://en.wikipedia.org/wiki/Line_segment)s
- [Polytope](https://en.wikipedia.org/wiki/Polytope)s - shapes with flat faces comprised of collections of coplanar [triangle](https://en.wikipedia.org/wiki/Triangle)s. The shape may or may not completely enclose a volume.

The user supply an [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude) for calculations. For some calculations there is no rounding, for others, rounding may be necessary. (The default rounding is to round half up.)

## Latest versioned releases
Developed and tested on [Java Development Kit, version 17](https://openjdk.java.net/projects/jdk/17/). The latest version is on GitHub.

```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/ccg-v3d -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>ccg-v3d</artifactId>
    <version>0.11</version>
</dependency>
```
[JAR](https://repo1.maven.org/maven2/io/github/agdturner/ccg-v3d/0.11/ccg-v3d-0.11.jar)
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/ccg-v3d -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>ccg-v3d</artifactId>
    <version>0.12-SNAPSHOT</version>
</dependency>
```

## Dependencies
- [ccg-io](https://github.com/agdturner/ccg-io)
- [ccg-math](https://github.com/agdturner/ccg-math)
- [BigMath](https://github.com/eobermuhlner/big-math)
- Please see the [POM](https://github.com/agdturner/ccg-v3d/blob/master/pom.xml) for details.

## Main classes

### [V3D_Vector](https://github.com/agdturner/ccg-v3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Vector.java)
A 3D vector of change in x, y, and z. The change values are stored as [Math_BigRationalSqrt](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRationalSqrt.java) numbers. The magnitude of the vector once calculated to an [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude#Uses) is also stored. A vector can be applied to any geometry and used to translate or rotate them.

### [V3D_Geometry](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Geometry.java)
An abstract class with a V3D_Vector offset, and an [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude#Uses) (OOM). This class is extended to define geometries. The OOM is used for the precision of calculations.

### [V3D_Point](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Point.java)
Extends V3D_Geometry. Provides a V3D_Vector gives the position relative to the origin.

### [V3D_Line](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java)
Extends V3D_Geometry. Represents a straight line that extends infinitely passing through two points.

### [V3D_Ray](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Ray.java)
Extends V3D_Geometry. Represents a line that extends infinitely from a point in one direction through another point.

### [V3D_Plane](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java)
Extends V3D_Geometry. Represents an infinite [plane](https://en.wikipedia.org/wiki/Plane_(geometry)). The plane is defined either by: 3 different points (p, q and r) that are not [collinear](https://en.wikipedia.org/wiki/Collinearity) or coincident, or by a normal vector and a point (where all points orthogonal to the point in the direction of the vector are on the plane).

### [V3D_Envelope](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Envelope.java)
Extends V3D_Geometry. Represents a [rectangular cuboid](https://en.wikipedia.org/wiki/Cuboid#Rectangular_cuboid) with edges aligned with the coordinate axes. The dimensions can collapse, so the envelope can form: a [rectangle](https://en.wikipedia.org/wiki/Rectangle) with all the x, or all the y, or all the z being the same; or a line segment with all the x and all the y being the same, or all the x and all the z being the same, or all the y and all the z being the same; or a point where all the x and all the y and all the z are the same. Envelopes are computationally useful for testing and calculating intersections and visibilities.

### [V3D_LineSegment](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_LineSegment.java)
Extends V3D_Geometry and V3D_Line. Represents a single continuous finite part of a line. Line segments are considered equal irrespective of the order of the end points.

### [V3D_Triangle](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Triangle.java)
Extends V3D_Geometry and V3D_Plane. Represents [triangle](https://en.wikipedia.org/wiki/Triangle)s. The three coplanar points {p, q and r} are the corners of the triangle. Triangle sides are can be stored as line segments. The centroid of a triangle can be computed.

### [V3D_Rectangle](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Rectangle.java)
Extends V3D_Geometry and V3D_Plane. Represents [rectangle](https://en.wikipedia.org/wiki/Rectangle)s. The fourth point is also stored. A rectangle can be thought of as comprising of triangles.

### [V3D_Tetrahedron](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Tetrahedron.java)
Extends V3D_Geometry and represent [tetrahedron](https://en.wikipedia.org/wiki/Tetrahedron)s. The fourth point is not coplanar with the other three. A tetrahedron can be thought of as compising of four triangles. The [centroid](https://en.wikipedia.org/wiki/Centroid#Of_a_tetrahedron_and_n-dimensional_simplex) and the [volume](https://en.wikipedia.org/wiki/Tetrahedron#Volume) can be calculated and stored given an [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude#Uses) (OOM) for the precision.

## Collections
There are classes that define objects made from collections of basic geometries.

## Development progress
- There is functionality for calculating the intersection and minimum distance between most, but not all different sets of two geometries.

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
- It would be good to have functionality that distinguishes between geometries touching and overlapping.

### Distance, Areas, Perimeters, Volumes
- So far, methods for calculating the minimum distance between geometries are implemented for:
-- point-plane, point-line, point-ray, point-line_segment, point-rectangle, point-triangle
-- plane-plane, plane-line, plane-ray, plane-line_segment
-- line-line, line-ray, line-line_segment
-- ray-ray, ray-line_segment
-- line_segment-line_segment

## Development history
### Origins
The library began development in March 2020. The original aim was to use it to create a model of our [solar system](https://en.wikipedia.org/wiki/Solar_system).
### Summary of changes
#### 0.10 to 0.11
- Added an offset vector for geometries to allow them to be rotated and translated.
#### 0.9 to 0.10
- Added V3D_Tetrahedron class, and V3D_TetrahedronPoly, V3D_TrianglePolyPlanar and V3D_LineSegmentPolyCollinear collection classes.
#### 0.8 to 0.9
- Change from left-handed to right handed coordinate system.
#### 0.7 to 0.8
- Simplifications to the intersection methods removing the static methods.
- Added a V3D_Ray class.

## Contributions
- Welcome, but let's save time and energy by organising :)

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
[Geographical projections](https://en.wikipedia.org/wiki/List_of_map_projections) are commonly used in geography to represent part or all of the surface of Earth as plan view maps. [Equirectangular_projection](https://en.wikipedia.org/wiki/Equirectangular_projection)s have: the Y Axis aligned with lines of [latitude](https://en.wikipedia.org/wiki/Latitude), with zero on the [equator](https://en.wikipedia.org/wiki/Equator), increasing to the [North pole](https://en.wikipedia.org/wiki/North_Pole), and decreasing to the [South pole](https://en.wikipedia.org/wiki/South_Pole); the X Axis aligned with lines of [longitude](https://en.wikipedia.org/wiki/Longitude) with zero at the [prime meridian](https://en.wikipedia.org/wiki/Prime_meridian) and increasing to the [East](https://en.wikipedia.org/wiki/East) and decreasing to the [West](https://en.wikipedia.org/wiki/West); and the Z axis represents height above [sea-level](https://en.wikipedia.org/wiki/Sea_level) or depth below sea-level. The choice of the [meridian](https://en.wikipedia.org/wiki/Meridian_(geography)) is arbitrary. For a 3D coordinate system with the origin at the centre of mass of Earth, the Y axis (the axis of [Earth's rotation](https://en.wikipedia.org/wiki/Earth%27s_rotation)) can continue to be aligned with latitude. With this, the X and Z axes intersect the equator.

[Spherical](https://en.wikipedia.org/wiki/Spherical_coordinate_system) and [ecliptic](https://en.wikipedia.org/wiki/Ecliptic_coordinate_system) coordinate systems are becoming more commonly used in Geography. See also: [Discrete_global_grid](https://en.wikipedia.org/wiki/Discrete_global_grid#Standard_equal-area_hierarchical_grids).

Everything is moving and changing. The rates of movement and change vary. Computational geography often boils down to exploring patterns of change in different things, in regions, and across different spatial and temporal scales. The [structure of Earth](https://en.wikipedia.org/wiki/Structure_of_Earth), whilst conceptually well defined, is also partly fluid and thermodynamics abounds. The [Sun](https://en.wikipedia.org/wiki/Sun) and the [Moon](https://en.wikipedia.org/wiki/Moon) affect the tides. How good are our best estimates of sea levels in the next 50 to 100 years? Global warming is rasing temperatures and ice is melting. Are models that are based on 32, 64, 128 bit or more [floating point](https://en.wikipedia.org/wiki/Floating_point) arithmetic providing sufficient accuracy?

If you find this library useful, think it may be useful, or want to help develop it, then please let me know.
