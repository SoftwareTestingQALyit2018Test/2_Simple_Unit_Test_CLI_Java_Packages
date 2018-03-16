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
        perimeter = (2 * length) + (2 * width);
        return perimeter;
    }

}
