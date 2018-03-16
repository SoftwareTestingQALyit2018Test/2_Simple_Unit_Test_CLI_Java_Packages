# JUnit Example with Package



## Initial steps

Firstly we make the project directory:
```sh
$ mkdir example2
$ cd example2
```

And then create a `README.md` file for the project.

```sh
$ vim README.md
...
$
```

Next we create a directory structure local to the project and then verify it:
```sh
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
defaults for Java projects. Note the two separate directories, one for the
source code of the SUT and one for the JUnit test code.

The packages that are to be created are `ie.lyit.comp.geometry.shape.rectangle`
and `ie.lyit.comp.geometry.shape.circle`. 

I am following the guidance in the Java Tutorial Trail on Packages and I will
firstly create a directory structure that mirrors the package namespace making
sure that I am in the root of the directory tree for the project.

```sh
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

```sh
$ cd main/java/ie/lyit/comp/geometry/shape/
$ vim rectangle.java
...
$ javac rectangle.java
```

Where the rectangle class is implemented thus:
```java
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
```sh
$ javac -cp .:/usr/share/java/junit4.jar:/home/martin/Documents/academic/IT_Skills_4_Agile_QA_Testing/Examples/1_Simple_Examples/2_Simple_Unit_Test_CLI_Java_Packages/src/main/java/ rectangleTest.java
```
Noting in particular the `-cp` or `-classpath' flag takes three arguments that
specify where the compiler will find the classes that it needs:
1. The current directory `.` (the default for the `javac` compiler.
2. The location of the JUnit jar file i.e. `/usr/share/java/junit4.jar` (this is
   a linux system and will be different for a Windows or Apple system).
3. The location of the class that will be tested e.g. `/home/martin/Documents/academic/IT_Skills_4_Agile_QA_Testing/Examples/1_Simple_Examples/2_Simple_Unit_Test_CLI_Java_Packages/src/main/java/` noting that the remaining part of the path to the rectangle class is resolved by the compiler using the package declarations and package imports.

And then we invoke the test:
```sh
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

So now we correct the incomplete method by editing the source code for the SUT
and implementing the `getPerimeter` method correctly:

```java
    public int getPerimeter(){
        perimeter = (2 * length) + (2 * width);
        return perimeter;
    }
```
Below the perma-link
[https://github.com/SoftwareTestingQALyit2018Test/2_Simple_Unit_Test_CLI_Java_Packages/blob/a6ef980e4ec404b68956c6f938e9407ac2a34a36/src/main/java/ie/lyit/comp/geometry/shape/rectangle.java#L15-L18]
And then recompile the rectangle class and rerun the JUnit test:

```sh
$ java -cp .:/usr/share/java/junit4.jar:/home/martin/Documents/academic/IT_Skills_4_Agile_QA_Testing/Examples/1_Simple_Examples/2_Simple_Unit_Test_CLI_Java_Packages/src/main/java/ org.junit.runner.JUnitCore  rectangleTest
JUnit version 4.12
.
Time: 0.003

OK (1 test)
$
```



