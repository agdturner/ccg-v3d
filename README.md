# [ccg-vector3d](https://github.com/agdturner/ccg-vector3d)

## Description
A modularised Java three-dimensional ([3D](https://en.wikipedia.org/wiki/Euclidean_space)) [Euclidean geometry](https://en.wikipedia.org/wiki/Euclidean_geometry) library.

The dimensions are defined by [orthogonal](https://en.wikipedia.org/wiki/Orthogonality) axes X, Y and Z that meet at the [cartesian](https://en.wikipedia.org/wiki/Cartesian_coordinate_system) origin - a point <x, y, z> where the coordinates x=y=z=0. Coordinates are stored as [Math_BigRational](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRational.java) (rational) numbers that comprise a numerator and denominator [BigDecimal](https://cr.openjdk.java.net/~iris/se/17/latestSpec/api/java.base/java/math/BigDecimal.html). Math_BigRational supports exact arithmetic division and arithmetic where if rounding is necessary it is done using a user specified [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude#Uses).

Conceptually, the cartesian is [right-handed](https://en.wikipedia.org/wiki/Right-hand_rule). Notes on [handedness](#handedness), the choice of [origin](#origin), and [geography](#geography) are provided below. This library is primarily being developed to support geography and Earth science applications, but it may support other uses too.

The code development aims to be sustainable with plentiful unit tests developed along with the functional methods. The current focus is to provide instersection and distance calculation code for dealing with:
- Envelopes - [rectangular cuboid](https://en.wikipedia.org/wiki/Cuboid#Rectangular_cuboid)s with sides aligned with the coordinate axes 
- Straight [line segment](https://en.wikipedia.org/wiki/Line_segment)s
- [Triangle](https://en.wikipedia.org/wiki/Triangle)s
- [Rectangle](https://en.wikipedia.org/wiki/Rectangle)s
- [Rectangular cuboid](https://en.wikipedia.org/wiki/Cuboid#Rectangular_cuboid)s
- [Tetrahedron](https://en.wikipedia.org/wiki/Tetrahedron).

## Latest versioned releases
Developed and tested on [Java Development Kit, version 17](https://openjdk.java.net/projects/jdk/17/). The latest version is on GitHub.

```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-vector3d -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-vector3D</artifactId>
    <version>0.8</version>
</dependency>
```
[JAR](https://repo1.maven.org/maven2/io/github/agdturner/agdt-java-vector3d/0.8/agdt-java-vector3d-0.8.jar)
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-vector3d -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-vector3D</artifactId>
    <version>0.9-SNAPSHOT</version>
</dependency>
```

## Dependencies
- [agdt-java-generic](https://github.com/agdturner/agdt-java-generic)
- [agdt-java-math](https://github.com/agdturner/agdt-java-math)
- [BigMath](https://github.com/eobermuhlner/big-math)
- Please see the [POM](https://github.com/agdturner/agdt-java-vector3d/blob/master/pom.xml) for details.

## Main geometry implementations so far...

### [V3D_Point](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Point.java)
Instances are infinitely small. Each coordinate is stored as an immutable [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java)

### [V3D_Vector](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Vector.java)
This is similar to a point in that it involves storing 3 [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java) numbers, but instead of representing a location relative to the origin of the axes, they define a general change in the x, y, and z coordinates. A vector can be applied to any geometry which essentailly shifts it relative to the origin.

### [V3D_Envelope](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Envelope.java)
In the general case, this is a [rectangular cuboid](https://en.wikipedia.org/wiki/Cuboid#Rectangular_cuboid) with edges aligned with the axes. However, the dimensions can collapse, so that this is essentially just a [rectangle](https://en.wikipedia.org/wiki/Rectangle), or a line segment or a point. Each finite geometry has an envelope that bounds it. Much geometrical calculation uses these.

### [V3D_Line](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java)
Instances are immutable straight lines that extend infinitely. They have two points and a vector ([V3D_Vector](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Vector.java)). The vector gives the direction of the line and is calculated as the difference from one point to the other. There is some small redundancy as the line could be defined simply by two points, or by a single point and a vector. Additionally, [V3D_Vector](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Vector.java) instances also store the square of the magnitude and also the magnitude (if this can be stored precisely as a rational number). Alternative implementations may calculate these attributes as needed, but this implementation calculates these attributes so they are conveniently available. So, a [V3D_Line](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java) instance is perhaps considerably heavier than it needs to be. 

### [V3D_Ray](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Ray.java)
Instances are immutable straight lines that extend infinitely from a point in one direction. Essentially, they are [V3D_Line](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java) instances with the first of the two points that define the line being the start of the ray that extends infinitely in the direction beyond the second point.

### [V3D_LineSegment](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_LineSegment.java)
Instances are immutable and finite. Essentially, they are [V3D_Line](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java) instances with the two points that define the line being the ends of the segment. A line segment is not permitted to have zero length.

### [V3D_Plane](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java)
Instances are immutable [Plane](https://en.wikipedia.org/wiki/Plane_(geometry))s extending infintely. They have 3 points (p, q and r) that are not [collinear](https://en.wikipedia.org/wiki/Collinearity) and two vectors (pq that gives how p is mapped onto q, and qr that gives how q is mapped onto r). The perpendicular normal vector to the plane (n) is calculated and stored. The direction of this is given by the order of the points, so each plane effectively has a front and a back. The equality of planes depends on the direction of the perpendicular normal vector as well as whether the points of each plane are [coplanar](https://en.wikipedia.org/wiki/Coplanarity).

### [V3D_Triangle](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Triangle.java)
Instances are immutable and finite [triangle](https://en.wikipedia.org/wiki/Triangle)s. Essentially they are [V3D_Plane](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java) instances and the three points {p, q and r} are the corners of the triangle. An additional vector (rp that gives how r is mapped onto p) is stored for each triangle. Each side of the triangle is also stored as a line segment for convenience. So again, as with other geometry objects, there is some redundancy in what is stored, but these additional things are stored for convenience.

### [V3D_Rectangle](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Rectangle.java)
Instances are immutable and finite [rectangle](https://en.wikipedia.org/wiki/Rectangle)s. Essentially they are [V3D_Plane](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java) instances and the three points (p, q and r) are the first three corners of the rectangle working around clockwise from the front of the plane. The other corner point of the rectangle (s) is also stored as are additional vectors (rs that gives how r is mapped onto s, and sp that gives how s is mapped onto p). Additionally the edges of the rectangle are stored as line segments for convenience. So again, as with other geometry objects, there is some redundancy in what is stored, but these additional things are stored for convenience.

## Development progress
- Most of what is implemented so far is intersection and distance from a point functionality. Not all of this is yet implemented for all geometries. The implemented intersection test implementations involve no rounding. For distance calculations, the user is asked to supply an [order of magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude) which is used to provide an answer rounded to or correct to this. The default rounding is to round half up.
- The aim is to have intersection functionality for all geometries including a test of whether any two geometry instances intersect and a method to get the intersection. As the geometries become more complicated, this aim becomes harder. Additional intersection funtionality that might be considered is whether or not the geometries touch or whether they overlap oe cross through each other. Additionally robust distance methods are wanted to calculate the shortest distance between any two geometries accurate to a given [order of magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude).  

### Intersection
- So far, methods for testing if there is an intersection and for retrieving the intersection are implemented for:
-- point-plane, point-line, point-ray, point-line_segement, point-rectangle, point-triangle
-- plane-plane, plane-line, plane-ray, plane-line_segment
-- line-line, line-ray, line-line_segment
-- ray-ray, ray-line_segment
-- line_segment-line_segment
- See the respective classes in the [geometry package](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/).
- What is next to do:
-- plane-triangle, plane-rectangle
-- line_segment-triangle, line_segment-rectangle
-- triangle-triangle, triangle-rectangle
-- rectangle-rectangle
- It would also be good to distinguish between geometries touching and overlapping.

### Distance, Areas, Perimeters, Volumes
- Methods for calculating these currently require the user to specify an [order of magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude) so that the result are provided accurate to that precision.
- So far, methods for calculating the minimum distance between geometries are implemented for:
-- point-plane, point-line, point-ray, point-line_segment, point-rectangle, point-triangle
-- plane-plane, plane-line, plane-ray, plane-line_segment
-- line-line, line-ray, line-line_segment
-- ray-ray, ray-line_segment
-- line_segment-line_segment

## Development plans
- Implement some basic volumes starting with [rectangular cuboid](https://en.wikipedia.org/wiki/Cuboid#Rectangular_cuboid)s and [tetrahedron](https://en.wikipedia.org/wiki/Tetrahedron).
- Implement [Surfaces](https://en.wikipedia.org/wiki/Surface#In_mathematics) based on [triangular irregular networks](https://en.wikipedia.org/wiki/Triangulated_irregular_network)


## Development history
### Origins
The library began development in March 2020. The purpose of the library is to support making spatial models of parts of the [solar system](https://en.wikipedia.org/wiki/Solar_system).
### Summary of main changes
#### 0.7 to 0.8
- Simplifications to the intersection methods removing the static methods.
- Inclusion of a V3D_Ray class.   

## Contributions
- Welcome, but to save time and energy, please liaise and we can try to organise.

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

The reason for right-handedness in physics is to do with rules of thumb which are use to figure out the direction of forces caused by the spin we are in (the Earth, Sun and Milky Way all spin a particular way). [Handedness and chirality](https://en.wikipedia.org/wiki/Chirality_(physics)) and the choice of handedness in physics is explained in [this video](https://youtu.be/BoHQtXpWG2Y).

## [origin](https://en.wikipedia.org/wiki/Origin_(mathematics))
For a lot of physics, it might be sensible to set z=0 at the [centre of mass](https://en.wikipedia.org/wiki/Center_of_mass) (CoM). The thing is, there is typically a bigger system within which a system is based and within a system, there are smaller systems that might want to be modelled in relative terms. For each system, there can be a different CoM and it may be helpful to shift the origin and reorientate the axes for each. Some overall framework is wanted at the largest scale to position each entity relatively. What happens on Earth is influenced significantly by the relative positions of the Sun and Moon. Earth's orbit is also influenced by the orbits and masses of other solar system objects. At another scale, there could be significant effects caused by the position of the [solar system](https://en.wikipedia.org/wiki/Solar_System) in the [galactic plane](https://en.wikipedia.org/wiki/Galactic_plane).

## Geography
[Geographical projections](https://en.wikipedia.org/wiki/List_of_map_projections) are commonly used in geography to represent part or all of the surface of Earth as plan view maps. [Equirectangular_projection](https://en.wikipedia.org/wiki/Equirectangular_projection)s have: the Y Axis aligned with lines of [latitude](https://en.wikipedia.org/wiki/Latitude), with zero on the [equator](https://en.wikipedia.org/wiki/Equator), increasing to the [North pole](https://en.wikipedia.org/wiki/North_Pole), and decreasing to the [South pole](https://en.wikipedia.org/wiki/South_Pole); the X Axis aligned with lines of [longitude](https://en.wikipedia.org/wiki/Longitude) with zero at the [prime meridian](https://en.wikipedia.org/wiki/Prime_meridian) and increasing to the [East](https://en.wikipedia.org/wiki/East) and decreasing to the [West](https://en.wikipedia.org/wiki/West); and the Z axis represents height above [sea-level](https://en.wikipedia.org/wiki/Sea_level) or depth below sea-level. The choice of the [meridian](https://en.wikipedia.org/wiki/Meridian_(geography)) is arbitrary, and sea-level varies.

[Spherical](https://en.wikipedia.org/wiki/Spherical_coordinate_system) and [ecliptic](https://en.wikipedia.org/wiki/Ecliptic_coordinate_system) coordinate systems are becoming more commonly used. These represent positions relative to the estimation of a centroid and the axis of [Earth's rotation](https://en.wikipedia.org/wiki/Earth%27s_rotation). See also: [Discrete_global_grid](https://en.wikipedia.org/wiki/Discrete_global_grid#Standard_equal-area_hierarchical_grids).

Everything is moving and changing. The rates of movement and change vary with scale. Some geography involves exploring patterns of change in different things in regions across ranges of spatial and temporal scales using data at specific spatial and temporal resolutions. The [structure of Earth](https://en.wikipedia.org/wiki/Structure_of_Earth), whilst conceptually well defined, is continually changing as Earth moves relative to the [Sun](https://en.wikipedia.org/wiki/Sun), the [Moon](https://en.wikipedia.org/wiki/Moon) and other [astronomical object](https://en.wikipedia.org/wiki/Astronomical_object)s which can have significant affects on Earth. Earth's atmosphere, for example, expands further out towards the Sun as it heats in the day. The orbit of the Earth around the Sun is complicated and the distance from the Earth to the Sun varies through time. [Orbital eccentricity](https://en.wikipedia.org/wiki/Orbital_eccentricity) affects Earth surface processes and probably causes changes deep within the Earth. Earth's solar system has [helicoid](https://en.wikipedia.org/wiki/Helicoid) like motion as it traverses the [Milky Way](https://en.wikipedia.org/wiki/Milky_Way). The Earth spins like a dynamo and the  [magnetic field](https://en.wikipedia.org/wiki/Earth%27s_magnetic_field) is prone to flip. Nearby astronomical objects affect pressure on Earth and may alter the [geoid](https://en.wikipedia.org/wiki/Geoid) significantly. How good are our best estimates of sea levels and Earth's magnetic field in the next 50 to 100 years? Are models based on [floating point](https://en.wikipedia.org/wiki/Floating_point) estimates providing sufficient accuracy? Ocean boundaries change, water density changes with pressure and temperature (and state).

If you find this library useful, think it may be useful, or want to help develop it, then please let me know.
