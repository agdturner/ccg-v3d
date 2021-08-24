# [agdt-java-vector3d](https://github.com/agdturner/agdt-java-vector3d)

## Description
A modularised Java three-dimensional ([3D](https://en.wikipedia.org/wiki/Euclidean_space)) [Euclidean geometry](https://en.wikipedia.org/wiki/Euclidean_geometry) library. The dimensions are defined by orthogonal coordinate axes X, Y and Z that meet at the origin point <x,y,z> where the [Cartesian](https://en.wikipedia.org/wiki/Cartesian_coordinate_system) coordinates x=y=z=0. Instances of points in this space are immutable. Coordinates are currently stored as [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java) numbers. Conceptually, from the origin, the X Axis runs in a positive direction to the right, the Y Axis runs in a positive direction up, and the Z Axis runs in a positive direction in or forwards. These choices are arbitrary, but it is thought that conceptually, this is most intuitive for people given the norms of graphs and human perception (humans have a perception of forward and backward, and we face forward and move forward to zoom in and move backward to zoom out). You may be interested in the [Geograhical note](#geographical-note).

Code and tests are in development and the current focus is to to develop instersection and distance calculation code for dealing with:
- Envelopes - [rectangular cuboid](https://en.wikipedia.org/wiki/Cuboid#Rectangular_cuboid) with sides aligned with the coordinate axes 
- Straight [line segment](https://en.wikipedia.org/wiki/Line_segment)s
- [Triangle](https://en.wikipedia.org/wiki/Triangle)s
- [Rectangle](https://en.wikipedia.org/wiki/Rectangle)s
- [rectangular cuboid](https://en.wikipedia.org/wiki/Cuboid#Rectangular_cuboid)s
- [tetrahedron](https://en.wikipedia.org/wiki/Tetrahedron).

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
[JAR](https://repo1.maven.org/maven2/io/github/agdturner/agdt-java-vector3d/0.6/agdt-java-vector3d-0.6.jar)
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-vector3d -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-vector3D</artifactId>
    <version>0.8-SNAPSHOT</version>
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
Instances are immutable straight and extend infinitely. They have two points and a vector ([V3D_Vector](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Vector.java)). The vector gives the direction of the line and is calculated as the difference from one point to the other. There is some small redundancy as the line could be defined simply by two points, or by a single point and a vector. Additionally, [V3D_Vector](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Vector.java) instances also store the square of the magnitude and also the magnitude (if this can be stored precisely as a rational number). Alternative implementations may calculate these attributes as needed, but this implementation calculates these attributes so they are conveniently available. So, a [V3D_Line](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java) instance is perhaps considerably heavier than it needs to be. 

### [V3D_LineSegment](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_LineSegment.java)
Instances are immutable and finite. Essentially, they are [V3D_Line](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java) instances with the two points that define the line being the ends of the segment. A line segment is not permitted to have zero length.

### [V3D_Plane](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java)
Instances are immutable [Plane](https://en.wikipedia.org/wiki/Plane_(geometry))s extending infintely. They have 3 points (p, q and r) that are not [collinear](https://en.wikipedia.org/wiki/Collinearity) and two vectors (pq that gives how p is mapped onto q, and qr that gives how q is mapped onto r). The perpendicular normal vector to the plane (n) is calculated and stored. The direction of this is given by the order of the points, so each plane effectively has a front and a back. The equality of planes depends on the direction of the perpendicular normal vector as well as whether the points of each plane are [coplanar](https://en.wikipedia.org/wiki/Coplanarity).

### [V3D_Triangle](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Triangle.java)
Instances are immutable and finite [triangle](https://en.wikipedia.org/wiki/Triangle)s. Essentially they are [V3D_Plane](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java) instances and the three points {p, q and r} are the corners of the triangle. An additional vector (rp that gives how r is mapped onto p) is stored for each triangle. Each side of the triangle is also stored as a line segment for convenience. So again, as with other geometry objects, there is some redundancy in what is stored, but these additional things are stored for convenience.

### [V3D_Rectangle](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Rectangle.java)
Instances are immutable and finite [rectangle](https://en.wikipedia.org/wiki/Rectangle)s. Essentially they are [V3D_Plane](https://github.com/agdturner/agdt-java-vector3D/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java) instances and the three points (p, q and r) are the first three corners of the rectangle working around clockwise from the front of the plane. The other corner point of the rectangle (s) is also stored as are additional vectors (rs that gives how r is mapped onto s, and sp that gives how s is mapped onto p). Additionally the edges of the rectangle are stored as line segments for convenience. So again, as with other geometry objects, there is some redundancy in what is stored, but these additional things are stored for convenience.

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
- Implement some basic volumes including [rectangular cuboid](https://en.wikipedia.org/wiki/Cuboid#Rectangular_cuboid)s and [tetrahedron](https://en.wikipedia.org/wiki/Tetrahedron).
- Implement V3D_Curve - a [differentiable curve](https://en.wikipedia.org/wiki/Differentiable_curve). A line passing through a point but that does not have to be straight.
- [Contribute](https://openjdk.java.net/contribute/) or offer the library as a contribution to the development of the openJDK.

## Development history
### Origins
The library began development in March 2020 with a view to supporting the development of [Cartesian](https://en.wikipedia.org/wiki/Cartesian_coordinate_system) coordinate based 3D models of [Space](https://en.wikipedia.org/wiki/Space), particularly [solar system](https://en.wikipedia.org/wiki/Solar_system)s and parts of them, not least being [Earth](https://en.wikipedia.org/wiki/Earth).

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

## Geographical note
Geographers commonly use projections to represent part or all of the surface of Earth as plan view maps. These often have: the Y Axis aligned with lines of [Longitude](https://en.wikipedia.org/wiki/Longitude), with Zero on the [Equator](https://en.wikipedia.org/wiki/Equator), increasing to the [North Pole](https://en.wikipedia.org/wiki/North_Pole), and decreasing to the [South Pole](https://en.wikipedia.org/wiki/South_Pole); the X Axis aligned with lines of [Latitude](https://en.wikipedia.org/wiki/Latitude) with Zero on the [Prime Meridian](https://en.wikipedia.org/wiki/Prime_meridian) and increasing to the East and decreasing to the West. Yet there are many and varied so called [Map Projection](https://en.wikipedia.org/wiki/Map_projection)s that are used and the X Axis and Y axis do not necessarily align with the directions North, South, East and West, but the X Axis coordinates are orthogonal to the Y axis coordinates and so these form a [Cartesian](https://en.wikipedia.org/wiki/Cartesian_coordinate_system) space. I some cases, the map projections are used to represent surface elevation data and what is most common with these is to measure the heights with the Z Axis coming up from the plan view. This fits with notions of heights, where increasing height is measured from a level increasing upwards (from the centre or some level).   

Geographical projections typically use a model for representing Earth relative to some axis of [Earth's rotation](https://en.wikipedia.org/wiki/Earth%27s_rotation) and some estimation of a line denoting the [Equator](https://en.wikipedia.org/wiki/Equator). The Earth is more eliptical than spherical in these alignments, yet both speherical and eliptical parameters for projections are common. Both have a notion of a point at the centre deep in the Earth.

Geographically everything is moving and changing and that these changes vary with scale. Geographers often look at the patterns of change in different things in a region across a range of scales and at specific resolutions of data. The dimensions are space-time-attribute and the resolutions relate respectively to: spatial units such as metres and kilometers; temporal unts such as minutes, hours, days, months, years; and attribute detail relating to how many variables help to define the entity of study that is defined within the spatial and temporal framework. Without digressing too much, the [Structure of Earth](https://en.wikipedia.org/wiki/Structure_of_Earth), whilst conceptually well defined, is continually changing as the Earth moves relative to the [Sun](https://en.wikipedia.org/wiki/Sun), the [Moon](https://en.wikipedia.org/wiki/Moon) and the other planets. The atmosphere for example stretches further out towards the Sun as it heats and expands in the day. The orbit of the Earth around the Sun is also a complicated motion and the distance from the Earth to the Sun is conditional on the orbits of our [Solar System](https://en.wikipedia.org/wiki/Solar_System)'s other [astronomical object](https://en.wikipedia.org/wiki/Astronomical_object)s, perhaps most influentially the giants [Jupiter](https://en.wikipedia.org/wiki/Jupiter) and [Saturn](https://en.wikipedia.org/wiki/Saturn) and the combined effects of Earths near neighbour planets ([Venus](https://en.wikipedia.org/wiki/Venus) and [Mars](https://en.wikipedia.org/wiki/Mars)) and [asteroid](https://en.wikipedia.org/wiki/Asteroid)s. The effects of these [astronomical objects] can to an extent exaggerate or cancel out. Let us add into the mix variation of the [Geoid](https://en.wikipedia.org/wiki/Geoid) over time. This is related to the orbital gravitational changes, but more so to the structure of Earth - the crust of which can rise up into significant mountains and dip down into deep trenches. What should be clear from this exposition is that what we might think of as solid and unchanging or simple and easy to measure in a physical sense often isn't. If you are wondering how good model estimates are of sea level change under global warming, let me suggest that it is literally hard to fathom (not least because of the relationship between pressure and temperature and the nature and density of water)! Sea level change is important, but is perhaps not the least of our worries. Anyway, it is thought that there will be useful geographical application of this code in [Earth Science](https://en.wikipedia.org/wiki/Earth_science)s which was one of the original motivations for developing it. It might be useful in studying movements of [Galaxies](https://en.wikipedia.org/wiki/Galaxies). If you find it useful, think it may be useful, want to collaborate to develop the libraru, then please let me know.
