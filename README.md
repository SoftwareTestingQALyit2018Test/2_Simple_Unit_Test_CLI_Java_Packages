# JUnit Example with Package

This is a test:
<script src="https://gist.github.com/martinRobinson/f5f4db163ec843829f8b31bc8055b2d9.js"></script>


## Initial steps

Firstly we make the project directory:
```console {.line-numbers}
$ mkdir example2
$ cd example2
```

In these example projects we will work off a central notion that all source and
resources are contained within a single project folder which I will denote as
`$PROJECT_ROOT`. The `$PROJECT_ROOT` notation is an example of a shell variable
which in Linux shells can contain a directory path.


So we make the `$PROJECT_ROOT` directory our current working directory and then
create a `README.md` file for the project.

```console
$ vim README.md
...
$
```
You can use the text editor of your choice.

Next we create a directory structure local to the project and then verify it:

```console
$ mkdir src src/main src/main/java src/test/ src/test/java
$ tree 
.
├── README.md
└── src
    ├── main
    │   └── java
    └── test
        └── java
```

The structure of the project directory tree mirrors the Gradle, Ant and Maven
defaults for Java projects. Note the two separate directory trees, one for the
source code of the SUT and one for the JUnit test code.

The packages that are to be created are `ie.lyit.comp.geometry.shape.rectangle`
and `ie.lyit.comp.geometry.shape.circle`. 

I am following the guidance in the Java Tutorial Trail on Packages and I will
firstly create a directory structure that mirrors the package namespace making
sure that I am in the root of the directory tree for the project.

```console
$ mkdir -p src/main/java/ie/lyit/comp/geometry/shape
$ mkdir -p src/test/java/ie/lyit/comp/geometry/shape
$ tree
.
├── README.md
└── src
    ├── main
    │   └── java
    │       └── ie
    │           └── lyit
    │               └── comp
    │                   └── geometry
    │                       └── shape
    └── test
        └── java
            └── ie
                └── lyit
                    └── comp
                        └── geometry
                            └── shape
$ 
```

## The System Under Test

We create an incomplete implementation of the SUT:

```console
$ cd main/java/ie/lyit/comp/geometry/shape/
$ vim rectangle.java
...
$ javac rectangle.java
```

Where the `rectangle` class is implemented thus:

```java {.line-numbers}
package ie.lyit.comp.geometry.shape;

public class rectangle{
    private int length = 0;
    private int width = 0;
    private int area = 0;
    private int perimeter;

    public rectangle(int length, int width){
        this.length = length;
        this.width = width;
    }

    public int getPerimeter(){
        return perimeter;
    }

}
```

And we note that the `getPerimeter()` method is always going to return `0`
rather than the calculated perimeter from the constructor arguments of length
and width.

## The JUnit Test 

And then in the spirit of test driven development, we create the unit test before the
correct implementation of the SUT.

```console
$ cd $PROJECT_ROOT/src/test/java/ie/lyit/comp/geometry/shape/
$ vim rectangleTest.java
...
$
```
And then we create the JUnit test for the `getPerimeter()` method of the SUT:

```java
import org.junit.*;
import static org.junit.Assert.*;

import ie.lyit.comp.geometry.shape.rectangle;

public class rectangleTest {
   
    rectangle rect1;

    @Before
    public void setUp(){
        rect1 = new rectangle(4,5);
    }

    @Test 
    public void rectangleCorrectPerimeterTest(){
        assertEquals(18, rect1.getPerimeter());    
    }

}
```

And then we compile the JUnit test class:

```console
$ javac -cp .:/usr/share/java/junit4.jar:/home/martin/Documents/academic/IT_Skills_4_Agile_QA_Testing/Examples/1_Simple_Examples/2_Simple_Unit_Test_CLI_Java_Packages/src/main/java/ rectangleTest.java
```

Noting in particular the `-cp` or `-classpath' flag for `javac` takes three arguments that
specify where the compiler will find the classes that it needs:
1. The current directory `.` (the default for the `javac` compiler.
2. The location of the JUnit jar file i.e. `/usr/share/java/junit4.jar` (this is
   a linux system and will be different for a Windows or Apple system).
3. The location of the class that will be tested e.g. `/home/martin/Documents/academic/IT_Skills_4_Agile_QA_Testing/Examples/1_Simple_Examples/2_Simple_Unit_Test_CLI_Java_Packages/src/main/java/` noting that the remaining part of the path to the rectangle class is resolved by the compiler using the package declarations and package imports.


## The Test Fails...

And then we invoke the test:
```console
$ java -cp .:/usr/share/java/junit4.jar:/home/martin/Documents/academic/IT_Skills_4_Agile_QA_Testing/Examples/1_Simple_Examples/2_Simple_Unit_Test_CLI_Java_Packages/src/main/java/ org.junit.runner.JUnitCore  rectangleTest

JUnit version 4.12
.E
Time: 0.003
There was 1 failure:
1) rectangleCorrectPerimeterTest(rectangleTest)
java.lang.AssertionError: expected:<18> but was:<0>
	at org.junit.Assert.fail(Assert.java:88)
	at org.junit.Assert.failNotEquals(Assert.java:834)
	at org.junit.Assert.assertEquals(Assert.java:645)
	at org.junit.Assert.assertEquals(Assert.java:631)
...

FAILURES!!!
Tests run: 1,  Failures: 1
$
```

## And the Test Passes...

So now we correct the incomplete method by editing the source code for the SUT
and implementing the `getPerimeter` method correctly:

```java
    public int getPerimeter(){
        perimeter = (2 * length) + (2 * width);
        return perimeter;
    }
```
And then recompile the rectangle class and rerun the JUnit test:

```console
$ java -cp .:/usr/share/java/junit4.jar:/home/martin/Documents/academic/IT_Skills_4_Agile_QA_Testing/Examples/1_Simple_Examples/2_Simple_Unit_Test_CLI_Java_Packages/src/main/java/ org.junit.runner.JUnitCore  rectangleTest
JUnit version 4.12
.
Time: 0.003

OK (1 test)
$
```

We can also invoke the Junit test for the SUT from the `$PROJECT_ROOT`
directory:

```console
$ java -classpath .:/usr/share/java/junit4.jar:src/main/java/:src/test/java/ie/lyit/comp/geometry/shape/ org.junit.runner.JUnitCore rectangleTest 
JUnit version 4.12
.
Time: 0.003

OK (1 test)
$ 
## Comments

1. We can see the attraction of wizards and builders in IDEs - we won't need to
   keep track of project directory trees, project root directories, and various
   paths to classes, jarfiles etc.
2. But we can see exactly what is going on under the bonnet.
3. And we can see the relationship between the SUT and the tests that exercise it.
