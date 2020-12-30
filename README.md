# [agdt-java-vector3d](https://github.com/agdturner/agdt-java-vector3d)

## Description
A Java [3D](https://en.wikipedia.org/wiki/Euclidean_space) geometry library. The dimensions are defined by orthogonal coordinate axes X, Y and Z that meet at the origin point <x,y,z> where x=y=z=0.

Points in this space are currently stored as [V3D_Point](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Point.java) instances with each coordinate stored as an immutable [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java). But, I am considering changing this to allow for coordinates that are irrational...

Implementations are written for:
- Straight lines and line segments
- 2D planes (Triangles and Rectangles)

Most of the intersection and length/area work is done without there needing to be any rounding.

(See below for [Details](#Details).)

## Latest Versions
Developed and tested on [Java Development Kit, version 15](https://openjdk.java.net/projects/jdk/15/).
### Stable
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-vector3d -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-vector3D</artifactId>
    <version>0.6</version>
</dependency>
```
[JAR](https://repo1.maven.org/maven2/io/github/agdturner/agdt-java-vector3d/0.6/agdt-java-vector3d-0.6.jar)
### Development
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-vector3d -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-vector3D</artifactId>
    <version>0.7-SNAPSHOT</version>
</dependency>
```
## Dependencies
- [agdt-java-generic](https://github.com/agdturner/agdt-java-generic)
- [agdt-java-math](https://github.com/agdturner/agdt-java-math)
- [BigMath](https://github.com/eobermuhlner/big-math)
- Please see the [POM](https://github.com/agdturner/agdt-java-vector3d/blob/master/pom.xml) for details.

## Details
[V3D_Point](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Point.java) instances are infinitely small tly with each coordinate stored as an immutable [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java)

[V3D_Line](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java) instances are immutable straight and extend infinitely. They have two points and a vector ([V3D_Vector](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Vector.java)). The vector gives the direction of the line and is calculated as the difference from one point to the other. There is some small redundancy as the line could be defined simply by two points, or by a single point and a vector. Additionally, [V3D_Vector](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Vector.java) instances also store the square of the magnitude and also the magnitude (if this can be stored precisely as a rational number). Alternative implementations may calculate these attributes as needed, but this implementation calculates these attributes so they are conveniently available. So, a [V3D_Line](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java) instance is perhaps considerably heavier than it needs to be. 

[V3D_LineSegment](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_LineSegment.java) instances are immutable and finite. Essentially, they are [V3D_Line](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java) instances with the two points that define the line being the ends of the segment. A line segment is not permitted to have zero length.

[V3D_Plane](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java) instances are immutable [Planes](https://en.wikipedia.org/wiki/Plane_(geometry)) extending infintely. They have 3 points (p, q and r) that are not [collinear](https://en.wikipedia.org/wiki/Collinearity) and two vectors (pq that gives how p is mapped onto q, and qr that gives how q is mapped onto r). The perpendicular normal vector to the plane (n) is calculated and stored. The direction of this is given by the order of the points, so each plane effectively has a front and a back. The equality of planes depends on the direction of the perpendicular normal vector as well as whether the points of each plane are [coplanar](https://en.wikipedia.org/wiki/Coplanarity).

[V3D_Triangle](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Triangle.java) instances are immutable and finite [Triangles](https://en.wikipedia.org/wiki/Triangle). Essentially they are [V3D_Plane](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java) instances and the three points {p, q and r} are the corners of the triangle. An additional vector (rp that gives how r is mapped onto p) is stored for each triangle. Each side of the triangle is also stored as a line segment for convenience. So again, as with other geometry objects, there is some redundancy in what is stored, but these additional things are stored for convenience.

[V3D_Rectangle](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Rectangle.java) instances are immutable and finite [Rectangles](https://en.wikipedia.org/wiki/Rectangle). Essentially they are [V3D_Plane](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java) instances and the three points (p, q and r) are the first three corners of the rectangle working around clockwise from the front of the plane. The other corner point of the rectangle (s) is also stored as are additional vectors (rs that gives how r is mapped onto s, and sp that gives how s is mapped onto p). Additionally the edges of the rectangle are stored as line segments for convenience. So again, as with other geometry objects, there is some redundancy in what is stored, but these additional things are stored for convenience.

Most of what is implemented so far is intersection. Not all of this is yet implemented. But, that which is implemented involves no rounding.

The full set of intersection functionality will indicate both if different geometries intersect and what the intersection is.

So far the following intersection code is implemented:
* point-plane, point-line, point-line_segement, point-rectangle, point-triangle
* line-line, line-plane
* line_segment-line line_segment-line_segment, line_segment-plane
* plane-plane

What is still to do is:
* line_segment-triangle, line_segment-rectangle
* plane-triangle, plane-rectangle, triangle-triangle, triangle-rectangle, rectangle-rectangle

Additionally there is some functionality for calculating lengths and areas. Some of this can require precision and rounding modes to be defined. An attempt is being made to be clear if a result is rounded or exact.

## Development plans/ideas
- Complete implementation of intersection functionality.
- Implement code for calculating the minimum distances between all the different geometries.
- Implement V3D_Tetrahedron - a [tetrahedron](https://en.wikipedia.org/wiki/Tetrahedron).
- Implement V3D_Curve - a [differentiable curve](https://en.wikipedia.org/wiki/Differentiable_curve). A line passing through a point but that does not have to be straight.
- Develop the library in an [agile](https://en.wikipedia.org/wiki/Agile_software_development) way.
- [Contribute](https://openjdk.java.net/contribute/) to the development of the openJDK.
- Considering allowing irrational coordinates. 

## Development history
- The library began development in March 2020. After something of a [hiatus](https://en.wiktionary.org/wiki/hiatus), I am finding more time to develop it again. 

## Contributions
- Welcome.

## LICENCE
- APACHE LICENSE, VERSION 2.0: https://www.apache.org/licenses/LICENSE-2.0

## Acknowledgements and thanks
- The [University of Leeds](http://www.leeds.ac.uk) and externally funded research grants have supported the development of this library.
- Thank you [openJDK](https://openjdk.java.net/) contributors and all involved in creating the platform.
- Thank you Eric for the [BigMath](https://github.com/eobermuhlner/big-math) library.
- Thank you developers of other useful Java libraries that have helped inspire me.
- Thank you creators, maintainers and contributors of relevent content on:
-- [Wikimedia](https://www.wikimedia.org/) projects, in particular the [English language Wikipedia](https://en.wikipedia.org/wiki/Main_Page)
-- [StackExchange](https://stackexchange.com), in particular [StackOverflow](https://stackoverflow.com/) and [Math.StackExchange](http://math.stackexchange.com/).
- Information that has helped me develop this library is cited in the source code.
- This library is a product of my education, interest and work. Thank you teachers, especially those that supported me.
