# [agdt-java-vector3d](https://github.com/agdturner/agdt-java-vector3d)

A Java library for 3D spatial geometry.

## Latest Versions
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-vector3d -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-vector3D</artifactId>
    <version>0.5</version>
</dependency>
```
[JAR](https://repo1.maven.org/maven2/io/github/agdturner/agdt-java-vector3d/0.5/agdt-java-generic-0.5.jar)

## Dependencies
Developed and tested on Java 15 using Maven.
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-generic -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-generic</artifactId>
    <version>1.7.2</version>
</dependency>
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-math -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-math</artifactId>
    <version>1.6</version>
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
The coordinates for points are stored as rational numbers. An attempt is being made not to round unless absolutely necessary. Mostly this has been successful thanks to the use of BigRational from the BigMath library in that there is no rounding for any intersection operations. However, to get the magnitude of a vector, some rounding might be necessary as the result may not be rational. In these instances the user can specify the precision and RoundingMode desired.

Mostly what is implemented so far is intersection:
* point-plane, point-line, point-line_segement, point-rectangle
* line-line, line-plane
* line_segment-line line_segment-line_segment, line_segment-plane, line_segment-rectangle
* plane-plane

## Code status and development roadmap
* The next phase of development is to add functionality for calculating the minimum distances between points and lines, lines and lines, lines and line_segments, lines and planes and line_segments and line_segments. Not sure when I will get around to this, but maybe soon... 
* The library began development in March 2020 and is actively being developed. 

## Known uses
I am using it to construct and visualise a [3D model of Earth](https://github.com/agdturner/agdt-java-project-Earth)

## Contributions
- Please report issues in the usual way.
- Please liaise with the developer with regards feature requests and about contributing to development.

## Acknowledgements and feedback
* Thanks to Eric for the BigMath library.
* If you find this code useful, please let me know and refer to the resources used in the usual ways.

## LICENCE
Please see the standard Apache 2.0 open source LICENCE.
