# [agdt-java-vector3d](https://github.com/agdturner/agdt-java-vector3d)

A Java library for 3D spatial geometry.

## Latest Versions
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-vector3d -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-vector3D</artifactId>
    <version>0.6</version>
</dependency>
```
[JAR](https://repo1.maven.org/maven2/io/github/agdturner/agdt-java-vector3d/0.6/agdt-java-vector3d-0.6.jar)

## Dependencies
Developed and tested on Java 15 using Maven.
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-generic -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-generic</artifactId>
    <version>1.7.3</version>
</dependency>
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-math -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-math</artifactId>
    <version>1.7</version>
</dependency>
<!-- https://search.maven.org/artifact/ch.obermuhlner/big-math -->
<dependency>
    <groupId>ch.obermuhlner</groupId>
    <artifactId>big-math</artifactId>
    <version>2.3.0</version>
</dependency>
```
- There are third party dependencies for testing.
- Please see the [POM](https://github.com/agdturner/agdt-java-vector3d/blob/master/pom.xml) for details.

## General description
The space is a Euclidean three dimensional (3D) space defined by orthogonal coordinate axes X, Y and Z that meet at an origin point <x,y,z> where x=y=z=0. Each point is stored as a [V3D_Point](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Point.java) object with each coordinates stored as an immutable [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java). Lines ([V3D_Line](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java)) are straight and have infinite length and are defined by two points and also a vector ([V3D_Vector](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Vector.java)) that gives the direction and difference from the end point to the start point. The vector is essentially a translation which shows how one point maps onto the other. The points and vector of a line are immutable. There is some small redundancy as the line could be defined simply be two points or a single point and a vector and so the line objects are heavier than they need to be, however, this implementation provides some convenience. V3D_Vector also allows for the magnitude and the magnitudeSquared of the vector to be stored. The magnitudeSquared can be stored exactly as a BigRational. However the magnitude may have to be rounded as roots can be irrational and unable to be stored exactly as a BigRational.

Line segments ([V3D_LineSegment](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_LineSegment.java)) are extended from lines (with the two points being the ends of the line segment). The line segment cannot have zero length.

[Planes](https://en.wikipedia.org/wiki/Plane_(geometry)) ([V3D_Plane](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java)) are immutable and extend infintely, and are defined by 3 points (p, q and r) that are not [collinear](https://en.wikipedia.org/wiki/Collinearity) and two vectors (pq that gives how p is mapped onto q, and qr that gives how q is mapped onto r). The perpendicular normal vector to the plane (n) is calculated and stored. The direction of this is given by the order of the points, so each plane effectively has a front and a back. The equality of planes can depend on the direction of the perpendicular normal vector as well as whether the points of each plane are [coplanar](https://en.wikipedia.org/wiki/Coplanarity).

[Triangles](https://en.wikipedia.org/wiki/Triangle) ([V3D_Triangle](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Triangle.java)) are extended from planes and the three points are the corners of the triangle. An additional vector (rp that gives how r is mapped onto p) is stored for each triangle. Each side of the triangle is also stored as a line segment for convenience. All these elements of a triangle are immutable. So again, as with lines, there is some redundancy in what is stored, but these additional things are stored for convenience.

[Rectangles](https://en.wikipedia.org/wiki/Rectangle) ([V3D_Rectangle](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Rectangle.java)) are extended from planes and the three points (p, q and r) are the first three corners of the rectangle working around clockwise from the front of the plane. The other corner point of the rectangle (s) is also stored as are additional vectors (rs that gives how r is mapped onto s, and sp that gives how s is mapped onto p). Additionally the edges of the rectangle are stored as line segments for convenience. All these elements of a rectangle are immutable. So again, as with lines and triangles, there is some redundancy in what is stored, but these additional things are stored for convenience.

So far, some intersection functionality has been implemented. A full set of intersections would indicate both if the following intersect and what that intersection is:
* point-plane, point-line, point-line_segement, point-rectangle, point-triangle
* line-line, line-plane
* line_segment-line line_segment-line_segment, line_segment-plane, line_segment-triangle, line_segment-rectangle
* plane-plane, plane-triangle, plane-rectangle, triangle-triangle, triangle-rectangle, rectangle-rectangle

Not all of this is yet implemented. But, that which is implemented involves no rounding.

Additionally there is some functionality for calculating lengths, areas and angles. Some of this can require precision and rounding modes to be defined. An attempt is being made to be clear if a result is rounded or exact.


## Code status and development roadmap
* Steps towards a 1.0 release:
- Complete implementation of intersection functionality.
- Complete implementation for calculating the minimum distances between points, line segments and shapes.
- Implement area calculations for shapes.
- Implement [tetrahedron](https://en.wikipedia.org/wiki/Tetrahedron) - a truely 3D object.

* The library began development in March 2020. After something of a [hiatus](https://en.wiktionary.org/wiki/hiatus), I am finding more time to develop it again. 

## Known uses and issues
Nothing yet!

## Contributions
- Please report issues.
- Contributions welcome.

## Acknowledgements and feedback
- The [University of Leeds](http://www.leeds.ac.uk) has supported the development of this library.
- Thank you Eric for the [BigMath](https://github.com/eobermuhlner/big-math) library :-)

## LICENCE
Please see the standard Apache 2.0 open source LICENCE.
