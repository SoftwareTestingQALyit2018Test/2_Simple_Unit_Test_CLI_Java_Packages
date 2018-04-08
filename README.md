<link rel="stylesheet" href="resources/pandoc.css">
<link rel="stylesheet" href="resources/prism.css">
<script src="resources/prism.js"></script>

<style>body { padding: 150px } pre { padding: 0 }</style>

# JUnit Example with Source Organised Using Packages

## Initial steps

Firstly we make the project directory:
<<<<<<< HEAD

<pre class="command-line" data-user="martin" data-host="localhost" data-output=""><code class="language-bash">
mkdir example2
cd example2
</code> </pre>
=======
```console
$ mkdir example2
$ cd example2
```
>>>>>>> 8f13a19ab555a97689e51261eba0548b31c60135

In these example projects we will work off a central notion that all source and
resources are contained within a single project folder which I will denote as
`$PROJECT_ROOT`. The `$PROJECT_ROOT` notation is an example of a shell variable
which in Linux shells can contain a directory path.


So we make the `$PROJECT_ROOT` directory our current working directory and then
create a `README.md` file for the project.

<pre class="command-line" data-user="martin" data-host="localhost" data-filter-output="(out)"><code class="language-bash">
vim README.md
(out)...
</code> </pre>

You can use the text editor of your choice.

Next we create a directory structure local to the project and then verify it:

<pre class="command-line" data-user="martin" data-host="localhost" data-filter-output="(out)"><code class="language-bash">
$ mkdir src src/main src/main/java src/test/ src/test/java
$ tree
(out).
(out)├── README.md
(out)└── src
(out)    ├── main
(out)    │   └── java
(out)    └── test
(out)        └── java
</code> </pre>


The structure of the project directory tree mirrors the Gradle, Ant and Maven
defaults for Java projects. Note the two separate directory trees, one for the
source code of the SUT and one for the JUnit test code.

The packages that are to be created are `ie.lyit.comp.geometry.shape.rectangle`
and `ie.lyit.comp.geometry.shape.circle`.

I am following the guidance in the Java Tutorial Trail on Packages and I will
firstly create a directory structure that mirrors the package namespace making
sure that I am in the root of the directory tree for the project.


<pre class="command-line" data-user="martin" data-host="localhost" data-filter-output="(out)"><code class="language-bash">
$ mkdir -p src/main/java/ie/lyit/comp/geometry/shape
$ mkdir -p src/test/java/ie/lyit/comp/geometry/shape
$ tree
(out).
(out)├── README.md
(out)└── src
(out)    ├── main
(out)    │   └── java
(out)    │       └── ie
(out)    │           └── lyit
(out)    │               └── comp
(out)    │                   └── geometry
(out)    │                       └── shape
(out)    └── test
(out)        └── java
(out)            └── ie
(out)                └── lyit
(out)                    └── comp
(out)                        └── geometry
(out)                            └── shape
</code> </pre>


## The System Under Test

We create an incomplete implementation of the SUT:

<pre class="command-line" data-user="martin" data-host="localhost" data-filter-output="(out)"><code class="language-bash">
cd main/java/ie/lyit/comp/geometry/shape/
vim rectangle.java
(out)...
javac rectangle.java
</code> </pre>

Where the `rectangle` class is implemented thus:

<pre class="line-numbers"><code class="language-java" >
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
</code> </pre>


And we note that the `getPerimeter()` method is always going to return `0`
rather than the calculated perimeter from the constructor arguments of length
and width.

## The JUnit Test

And then in the spirit of test driven development, we create the unit test before the
correct implementation of the SUT.

<pre class="command-line" data-user="martin" data-host="localhost" data-filter-output="(out)"><code class="language-bash">
cd $PROJECT_ROOT/src/test/java/ie/lyit/comp/geometry/shape/
vim rectangleTest.java
(out)...
</code> </pre>

And then we create the JUnit test for the `getPerimeter()` method of the SUT:

<pre class="line-numbers"><code class="language-java" >
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
</code> </pre>

And then we compile the JUnit test class:

<pre class="command-line" data-user="martin" data-host="localhost" data-filter-output="(out)"><code class="language-bash">
$ javac -cp .:/usr/share/java/junit4.jar:/home/martin/Documents/academic/IT_Skills_4_Agile_QA_Testing/Examples/1_Simple_Examples/2_Simple_Unit_Test_CLI_Java_Packages/src/main/java/ rectangleTest.java
</code> </pre>

Noting in particular the `-cp` or `-classpath' flag for `javac` takes three arguments that
specify where the compiler will find the classes that it needs:

1. The current directory `.` (the default for the `javac` compiler).
2. The location of the JUnit jar file i.e. `/usr/share/java/junit4.jar` (this is
   a linux system and will be different for a Windows or Apple system).
3. The location of the class that will be tested e.g. `/home/martin/Documents/academic/IT_Skills_4_Agile_QA_Testing/Examples/1_Simple_Examples/2_Simple_Unit_Test_CLI_Java_Packages/src/main/java/` noting that the remaining part of the path to the rectangle class is resolved by the compiler using the package declarations and package imports.


## The Test Fails...

And then we invoke the test:
<pre class="command-line" data-user="martin" data-host="localhost" data-filter-output="(out)"><code class="language-bash">
$ java -cp .:/usr/share/java/junit4.jar:/home/martin/Documents/academic/IT_Skills_4_Agile_QA_Testing/Examples/1_Simple_Examples/2_Simple_Unit_Test_CLI_Java_Packages/src/main/java/ org.junit.runner.JUnitCore  rectangleTest
(out)
(out)JUnit version 4.12
(out).E
(out)Time: 0.003
(out)There was 1 failure:
(out)1) rectangleCorrectPerimeterTest(rectangleTest)
(out)java.lang.AssertionError: expected:<18> but was:<0>
(out)	at org.junit.Assert.fail(Assert.java:88)
(out)	at org.junit.Assert.failNotEquals(Assert.java:834)
(out)	at org.junit.Assert.assertEquals(Assert.java:645)
(out)	at org.junit.Assert.assertEquals(Assert.java:631)
(out)...
(out)
(out)FAILURES!!!
(out)Tests run: 1,  Failures: 1
</code> </pre>

## And the Test Passes...

So now we correct the incomplete method by editing the source code for the SUT
and implementing the `getPerimeter` method correctly:

<pre class="line-numbers"><code class="language-java" >
    public int getPerimeter(){
        perimeter = (2 * length) + (2 * width);
        return perimeter;
    }
</code> </pre>

And then recompile the rectangle class and rerun the JUnit test:

<pre class="command-line" data-user="martin" data-host="localhost" data-filter-output="(out)"><code class="language-bash">
$ java -cp .:/usr/share/java/junit4.jar:/home/martin/Documents/academic/IT_Skills_4_Agile_QA_Testing/Examples/1_Simple_Examples/2_Simple_Unit_Test_CLI_Java_Packages/src/main/java/ org.junit.runner.JUnitCore  rectangleTest
(out)JUnit version 4.12
(out).
(out)Time: 0.003
(out)
(out)OK (1 test)
</code> </pre>

We can also invoke the Junit test for the SUT from the `$PROJECT_ROOT`
directory:

<pre class="command-line" data-user="martin" data-host="localhost" data-filter-output="(out)"><code class="language-bash">
$ java -classpath .:/usr/share/java/junit4.jar:src/main/java/:src/test/java/ie/lyit/comp/geometry/shape/ org.junit.runner.JUnitCore rectangleTest
(out)JUnit version 4.12
(out).
(out)Time: 0.003
(out)
(out)OK (1 test)
</code> </pre>


## Comments

1. We can see the attraction of wizards and builders in IDEs - we won't need to
   keep track of project directory trees, project root directories, and various
   paths to classes, jarfiles etc.
2. But we can see exactly what is going on under the bonnet.
3. And we can see the relationship between the SUT and the tests that exercise it.

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
