# [ccg-v3d](https://github.com/agdturner/ccg-v3d)

## Description
A modularised Java three-dimensional ([3D](https://en.wikipedia.org/wiki/Euclidean_space)) [Euclidean geometry](https://en.wikipedia.org/wiki/Euclidean_geometry) library.

The dimensions are defined by [orthogonal](https://en.wikipedia.org/wiki/Orthogonality) axes X, Y and Z that meet at the [cartesian](https://en.wikipedia.org/wiki/Cartesian_coordinate_system) origin - a point <x, y, z> where the coordinates x=y=z=0. Coordinates are stored as [Math_BigRationalSqrt](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRationalSqrt.java) numbers. These store the square and the square root or an appoximation of this given an [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude#Uses) for a set of rational numbers (comprising a numerator and denominator each stored as a [BigDecimal](https://cr.openjdk.java.net/~iris/se/17/latestSpec/api/java.base/java/math/BigDecimal.html)).

Conceptually, the cartesian is [right-handed](https://en.wikipedia.org/wiki/Right-hand_rule). Notes on [handedness](#handedness), the [origin](#origin), and [geography](#geography) are provided below. This library originally began developement with a view to supporting geography and Earth science applications, but it may support other uses too.

The code development aims to be sustainable with a complete coverage of unit tests developed along with the functional methods. For visualisation/rendering, I plan to develop a separate library.

The current focus is to represent and provide instersection, centroid and distance calculation code for:
- Envelopes - [rectangular cuboid](https://en.wikipedia.org/wiki/Cuboid#Rectangular_cuboid)s with sides aligned with the coordinate axes 
- Straight [line segment](https://en.wikipedia.org/wiki/Line_segment)s
- [Polytope](https://en.wikipedia.org/wiki/Polytope)s - shapes with flat faces comprised of collections of coplanar [triangle](https://en.wikipedia.org/wiki/Triangle)s. The shape may or may not completely enclose a volume.

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
[JAR](https://repo1.maven.org/maven2/io/github/agdturner/ccg-3d/0.11/ccg-v3d-0.11.jar)
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
This stores 4 [Math_BigRationalSqrt](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRationalSqrt.java) representing a vector of change in x, y, and z coordinates. The magnitude of the vector once calclated to an Or. A vector can be applied to any geometry and use to translate or rotate geometries.

### [V3D_Geometry](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Geometry.java)
An abstract class with a V3D_Vector offset, and an Order of Magnitude oom. This class is extended to define geometries. The Order of Magnitude is used for the precision of calculations.

### [V3D_Point](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Point.java)
Extends V3D_Geometry and provides a V3D_Vector pos (which can be considered as the position relative to the origin).

### [V3D_Line](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java)
Extends V3D_Geometry and represents a line that extends infinitely passing through two points. A vector of the transation from one point to the other is also provided for convenience.

### [V3D_Ray](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Ray.java)
Extends V3D_Geometry and represents a line that extends infinitely from a point in one direction through another point. A vector of the transation from one point to the other is also provided for convenience.

### [V3D_Plane](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java)
Extends V3D_Geometry and represents an infinite [plane](https://en.wikipedia.org/wiki/Plane_(geometry)). The plane is defined either by: 3 different points (p, q and r) that are not [collinear](https://en.wikipedia.org/wiki/Collinearity) or coincident, or by a normal vector and a point. Either way, three points are stored along with the normal and the vectors of translation between each point.

### [V3D_Envelope](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Envelope.java)
Extends V3D_Geometry and represents a [rectangular cuboid](https://en.wikipedia.org/wiki/Cuboid#Rectangular_cuboid) with edges aligned with the coordinate axes. The dimensions can collapse, so the envolope can form a [rectangle](https://en.wikipedia.org/wiki/Rectangle), or a line segment, or a point. Each finite geometry has an envelope that bounds it. Envelopes are computationally useful for selecting geometries and perfomring calculations, such as checking for potential intersection and visibility.

### [V3D_LineSegment](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_LineSegment.java)
Extends V3D_Geometry and V3D_Line, and represents a finite part of a line, where the two points that define the line are the end points of the line segment. Line segments are considered equal irrespective of the order of these end points.

### [V3D_Triangle](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Triangle.java)
Extends V3D_Geometry and V3D_Plane, and represents [triangle](https://en.wikipedia.org/wiki/Triangle)s. The three coplanar points {p, q and r} are the corners of the triangle. Triangle sides are constructed as needed as line segments.

### [V3D_Rectangle](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Rectangle.java)
Extends V3D_Geometry and V3D_Plane, and represents [rectangle](https://en.wikipedia.org/wiki/Rectangle)s. The fourth point is also stored as can all the are additional vectors between this fourth point and the other points. The edges of the rectangle can be stored as line segments.

### [V3D_Tetrahedron](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Tetrahedron.java)
Extends V3D_Geometry and represent [tetrahedron](https://en.wikipedia.org/wiki/Tetrahedron)s. The fourth point is not to be coplanar with the other three. The four triangles that make up the shape can be stored.

## Collections
There are classes for the collections of basic geometries.

## Development progress
- Most of what is implemented so far is intersection and minimum distance from a point functionality. Not all of this is yet implemented for all geometries particularly collections. The user is asked to supply an [order of magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude) for calculations. For some calculations there is no rounding, for others, rounding may be necessary. (The default rounding is to round half up.)

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
The library began development in March 2020. The envisaged original purpose of the library was to support making spatial models of parts of the [solar system](https://en.wikipedia.org/wiki/Solar_system).
### Summary of main changes
#### 0.10 to 0.11
- Added a offset vector to geometries.
#### 0.9 to 0.10
- Added tetrahedron and collections.
#### 0.8 to 0.9
- Change from left-handed to right handed.
#### 0.7 to 0.8
- Simplifications to the intersection methods removing the static methods.
- Inclusion of a V3D_Ray class.

## Contributions
- Welcome, but to save time and energy, please liaise and we can hopefully get organised.

## LICENCE
- APACHE LICENSE, VERSION 2.0: https://www.apache.org/licenses/LICENSE-2.0

## Acknowledgements and thanks
- The [University of Leeds](http://www.leeds.ac.uk) and externally funded research grants have supported the development of this library or the dependencies.
- Thank you [openJDK](https://openjdk.java.net/) contributors and all involved in creating the platform.
- Thank you Eric (_et al._) for the [BigMath](https://github.com/eobermuhlner/big-math) library.
- Thank you developers and maintainers of [Apache Maven](https://maven.apache.org/), [Apache NetBeans](https://netbeans.apache.org/), and [git](https://git-scm.com/) which I use for developing code.
- Thank you developers and maintainers of [GitHub](http://github.com) and [Maven_Central](https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-vector3d) for supporting the development of this code and for providing a means of creating a community of users/developers.
- Thank you developers, maintainers and contributors of other useful open source Java libraries and relevent content on:
-- [Wikimedia](https://www.wikimedia.org/) projects, in particular the [English language Wikipedia](https://en.wikipedia.org/wiki/Main_Page)
-- [StackExchange](https://stackexchange.com), in particular [StackOverflow](https://stackoverflow.com/) and [Math.StackExchange](http://math.stackexchange.com/).
- Information that has helped me develop this library is cited in the source code.
- Thank you to those that supported me personally and all who have made a positive contribution to society. Let us try to look after each other, look after this world, make space for wildlife, develop know how, safeguard this and apply it to boldy go, explore, find and maybe help or make friends with others :)

# Notes

## Handedness
The handedness (the assignment of axes and their directions) has been changed from left-handed to right-handed. The choice is somewhat arbitrary. Left-handed was originally chosen as it is intuitive considering vertical screens and graphs and zooming in and out in terms of human perception and moving closer and further away. Left-handed is perhaps a good choice for navigation, but for physics and geography right-handedness is more common and as this library is more geared for supporting physics and geography, this change has been implemented.

Right-handed coordinates are more commonly used in much of physics ([chirality](https://en.wikipedia.org/wiki/Chirality_(physics)), [video](https://youtu.be/BoHQtXpWG2Y)).

## [origin](https://en.wikipedia.org/wiki/Origin_(mathematics))
For a lot of physics, it might be sensible to set z=0 at the [centre of mass](https://en.wikipedia.org/wiki/Center_of_mass) (CoM) or the centre of volume. Earth is influenced significantly by the relative positions of the Sun and Moon. Earth's orbit is also influenced by the orbits and masses of other solar system objects. At another scale, there could be significant effects caused by the position of the [solar system](https://en.wikipedia.org/wiki/Solar_System) in the [galactic plane](https://en.wikipedia.org/wiki/Galactic_plane).

## Geography
[Geographical projections](https://en.wikipedia.org/wiki/List_of_map_projections) are commonly used in geography to represent part or all of the surface of Earth as plan view maps. [Equirectangular_projection](https://en.wikipedia.org/wiki/Equirectangular_projection)s have: the Y Axis aligned with lines of [latitude](https://en.wikipedia.org/wiki/Latitude), with zero on the [equator](https://en.wikipedia.org/wiki/Equator), increasing to the [North pole](https://en.wikipedia.org/wiki/North_Pole), and decreasing to the [South pole](https://en.wikipedia.org/wiki/South_Pole); the X Axis aligned with lines of [longitude](https://en.wikipedia.org/wiki/Longitude) with zero at the [prime meridian](https://en.wikipedia.org/wiki/Prime_meridian) and increasing to the [East](https://en.wikipedia.org/wiki/East) and decreasing to the [West](https://en.wikipedia.org/wiki/West); and the Z axis represents height above [sea-level](https://en.wikipedia.org/wiki/Sea_level) or depth below sea-level. The choice of the [meridian](https://en.wikipedia.org/wiki/Meridian_(geography)) is arbitrary, and sea-level varies.

[Spherical](https://en.wikipedia.org/wiki/Spherical_coordinate_system) and [ecliptic](https://en.wikipedia.org/wiki/Ecliptic_coordinate_system) coordinate systems are becoming more commonly used. These represent positions relative to the estimation of a centroid and the axis of [Earth's rotation](https://en.wikipedia.org/wiki/Earth%27s_rotation). See also: [Discrete_global_grid](https://en.wikipedia.org/wiki/Discrete_global_grid#Standard_equal-area_hierarchical_grids).

Everything is moving and changing. The rates of movement and change vary with scale. Some geography involves exploring patterns of change in different things in regions across ranges of spatial and temporal scales using data at specific spatial and temporal resolutions. The [structure of Earth](https://en.wikipedia.org/wiki/Structure_of_Earth), whilst conceptually well defined, is continually changing as Earth moves relative to the [Sun](https://en.wikipedia.org/wiki/Sun), the [Moon](https://en.wikipedia.org/wiki/Moon) and other [astronomical object](https://en.wikipedia.org/wiki/Astronomical_object)s which can have significant affects on Earth. Earth's atmosphere, for example, expands further out towards the Sun as it heats in the day. The orbit of the Earth around the Sun is complicated and the distance from the Earth to the Sun varies through time. [Orbital eccentricity](https://en.wikipedia.org/wiki/Orbital_eccentricity) affects Earth surface processes and probably causes changes deep within the Earth. Earth's solar system has [helicoid](https://en.wikipedia.org/wiki/Helicoid) like motion as it traverses the [Milky Way](https://en.wikipedia.org/wiki/Milky_Way). The Earth spins like a dynamo and the  [magnetic field](https://en.wikipedia.org/wiki/Earth%27s_magnetic_field) is prone to flip. Nearby astronomical objects affect pressure on Earth and may alter the [geoid](https://en.wikipedia.org/wiki/Geoid) significantly. How good are our best estimates of sea levels and Earth's magnetic field in the next 50 to 100 years? Are models based on [floating point](https://en.wikipedia.org/wiki/Floating_point) estimates providing sufficient accuracy? Ocean boundaries change, water density changes with pressure and temperature (and state).

If you find this library useful, think it may be useful, or want to help develop it, then please let me know.
