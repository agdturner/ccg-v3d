# [agdt-java-vector3d](https://github.com/agdturner/agdt-java-vector3d)

## Description
A modularised Java three-dimensional ([3D](https://en.wikipedia.org/wiki/Euclidean_space)) [Euclidean geometry](https://en.wikipedia.org/wiki/Euclidean_geometry) library. The dimensions are defined by orthogonal coordinate axes X, Y and Z that meet at the origin point <x,y,z> where the coordinates x=y=z=0. Points in this space can be defined as immutable [V3D_Point](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Point.java) instances. Coordinates are currently stored as [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java) numbers.

Code and tests are in development and the current focus is to to develop instersection and distance measurement code for dealing with:
- Envelopes - Rectilinear volumes with sides aligned with the coordinate axes 
- Straight line segments in any orientation
- Regular 2D shapes in any orientation

(See below for [Details](#Details).)

## Latest versioned releases
Developed and tested on [Java Development Kit, version 15](https://openjdk.java.net/projects/jdk/15/). The latest version is on GitHub.

<!-- I have been trying and failing to submit the Maven central repoistory via https://central.sonatype.org/ -->
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-vector3d -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-vector3D</artifactId>
    <version>0.6</version>
</dependency>
```
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-vector3d -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-vector3D</artifactId>
    <version>0.8-SNAPSHOT</version>
</dependency>
```

[JAR](https://repo1.maven.org/maven2/io/github/agdturner/agdt-java-vector3d/0.6/agdt-java-vector3d-0.6.jar)

## Dependencies
- [agdt-java-generic](https://github.com/agdturner/agdt-java-generic)
- [agdt-java-math](https://github.com/agdturner/agdt-java-math)
- [BigMath](https://github.com/eobermuhlner/big-math)
- Please see the [POM](https://github.com/agdturner/agdt-java-vector3d/blob/master/pom.xml) for details.

## Main geometry implementations so far...

### [V3D_Point](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Point.java)
Instances are infinitely small. Each coordinate is stored as an immutable [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java)

### [V3D_Vector](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Vector.java)
This is similar to a point in that it involves storing 3 [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java) numbers, but instead of representing a location relative to the origin of the axes, they define a general change in the x, y, and z coordinates. A vector can be applied to any point or geometry to move it.

### [V3D_Line](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java)
Instances are immutable straight and extend infinitely. They have two points and a vector ([V3D_Vector](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Vector.java)). The vector gives the direction of the line and is calculated as the difference from one point to the other. There is some small redundancy as the line could be defined simply by two points, or by a single point and a vector. Additionally, [V3D_Vector](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Vector.java) instances also store the square of the magnitude and also the magnitude (if this can be stored precisely as a rational number). Alternative implementations may calculate these attributes as needed, but this implementation calculates these attributes so they are conveniently available. So, a [V3D_Line](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java) instance is perhaps considerably heavier than it needs to be. 

### [V3D_LineSegment](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_LineSegment.java)
Instances are immutable and finite. Essentially, they are [V3D_Line](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java) instances with the two points that define the line being the ends of the segment. A line segment is not permitted to have zero length.

### [V3D_Plane](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java)
Instances are immutable [Planes](https://en.wikipedia.org/wiki/Plane_(geometry)) extending infintely. They have 3 points (p, q and r) that are not [collinear](https://en.wikipedia.org/wiki/Collinearity) and two vectors (pq that gives how p is mapped onto q, and qr that gives how q is mapped onto r). The perpendicular normal vector to the plane (n) is calculated and stored. The direction of this is given by the order of the points, so each plane effectively has a front and a back. The equality of planes depends on the direction of the perpendicular normal vector as well as whether the points of each plane are [coplanar](https://en.wikipedia.org/wiki/Coplanarity).

### [V3D_Triangle](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Triangle.java)
Instances are immutable and finite [Triangles](https://en.wikipedia.org/wiki/Triangle). Essentially they are [V3D_Plane](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java) instances and the three points {p, q and r} are the corners of the triangle. An additional vector (rp that gives how r is mapped onto p) is stored for each triangle. Each side of the triangle is also stored as a line segment for convenience. So again, as with other geometry objects, there is some redundancy in what is stored, but these additional things are stored for convenience.

### [V3D_Rectangle](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Rectangle.java)
Instances are immutable and finite [Rectangles](https://en.wikipedia.org/wiki/Rectangle). Essentially they are [V3D_Plane](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java) instances and the three points (p, q and r) are the first three corners of the rectangle working around clockwise from the front of the plane. The other corner point of the rectangle (s) is also stored as are additional vectors (rs that gives how r is mapped onto s, and sp that gives how s is mapped onto p). Additionally the edges of the rectangle are stored as line segments for convenience. So again, as with other geometry objects, there is some redundancy in what is stored, but these additional things are stored for convenience.

## Development progress
Most of what is implemented so far is intersection and distance from a point functionality. Not all of this is yet implemented for all geometries. The implemented intersection test implementations involve no rounding. For distance calculations, the user is asked to supply an [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude) which is used to provide an answer rounded to or correct to this. The default rounding is to round half up.

The aim is to have intersection functionality for all geometries including a test of whether any two geometry instances intersect and a method to get the intersection. As the geometries become more complicated, this aim becomes harder. Additional intersection funtionality that might be considered is whether or not the geometries touch or whether they overlap oe cross through each other. Additionally robust distance methods are wanted to calculate the shortest distance between any two geometries accurate to a given [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude).  

### Intersection
So far, methods for testing if there is an intersection and for retrieving the intersection are implemented for:
- point-plane, point-line, point-line_segement, point-rectangle, point-triangle
- line-line, line-plane
- line_segment-line line_segment-line_segment, line_segment-plane
- plane-plane
See the respective classes in the [geometry package](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/).
What is next to do is:
- line_segment-triangle, line_segment-rectangle
- plane-triangle, plane-rectangle, triangle-triangle, triangle-rectangle, rectangle-rectangle

### Distance
So far, methods for calculating the distance between geometries are implemented for:
- point-plane, point-line, point-line_segement, point-rectangle, point-triangle
- line-line, line-plane
See the respective classes in the [geometry package](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/). There is a related method in With a distance calculations it is 
What is next to do is:
- line-line_segment
- line_segment-line_segment

### Areas, Perimeters, Volumes
Methods or calculating these require the user to specify an [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude) so that the result are provided accurate to that precision. There is the So far methods for calculating lengths fothese, but not muchlengths and areas. This typycally 

## Development plans
- Implement code for calculating the minimum distances between all the different geometries.
- Implement some basic volumes including [rectangular cuboids](https://en.wikipedia.org/wiki/Cuboid#Rectangular_cuboid) and [tetrahedron](https://en.wikipedia.org/wiki/Tetrahedron).
- Implement V3D_Curve - a [differentiable curve](https://en.wikipedia.org/wiki/Differentiable_curve). A line passing through a point but that does not have to be straight.
- [Contribute](https://openjdk.java.net/contribute/) or offer the library as a contribution to the development of the openJDK.

## Development history
### Origins
The library began development in March 2020 with a view to supporting the development of [Cartesian](https://en.wikipedia.org/wiki/Cartesian_coordinate_system) coordinate based 3D models of [Space](https://en.wikipedia.org/wiki/Space), particularly [solar systems](https://en.wikipedia.org/wiki/Solar_systems) and parts of them, not least being [Earth](https://en.wikipedia.org/wiki/Earth).

## Contributions
- Welcome, but to save time and energy, try liaising and we can collect our thoughts about how to organise.

## LICENCE
- APACHE LICENSE, VERSION 2.0: https://www.apache.org/licenses/LICENSE-2.0

## Acknowledgements and thanks
- The [University of Leeds](http://www.leeds.ac.uk) and externally funded research grants have supported the development of this library or the dependencies.
- Thank you [openJDK](https://openjdk.java.net/) contributors and all involved in creating the platform.
- Thank you Eric (_et al._) for the [BigMath](https://github.com/eobermuhlner/big-math) library.
- Thank you developers and maintainers of [Apache Maven](https://maven.apache.org/), [Apache NetBeans](https://netbeans.apache.org/), and [git](https://git-scm.com/) which I use for developing code.
- Thank you developers and maintainers of [GitHub](http://github.com) and [Maven_Central](https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-vector3d) for supporting the development of this code and for providing a means of creating a communities of users/developers.
- Thank you developers, maintainers and contributors of other useful open source Java libraries and relevent content on:
-- [Wikimedia](https://www.wikimedia.org/) projects, in particular the [English language Wikipedia](https://en.wikipedia.org/wiki/Main_Page)
-- [StackExchange](https://stackexchange.com), in particular [StackOverflow](https://stackoverflow.com/) and [Math.StackExchange](http://math.stackexchange.com/).
- Information that has helped me develop this library is cited in the source code.
- Thank you to those that supported me personally and all who have made a positive contribution to society. Let us try to look after each other, look after this world, make space for wildlife, develop know how, safeguard this and apply it to boldy go, explore, find and maybe help or make friends with others :)
