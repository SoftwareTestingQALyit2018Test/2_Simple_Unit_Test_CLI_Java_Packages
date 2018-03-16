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


