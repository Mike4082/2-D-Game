package newgame;

public class Rectangle {
    public int x, y, h, w;
    private int[] pixels;


    Rectangle(int x, int y, int w, int h){
        //so we dont need getter/setter
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    Rectangle(){
        this(0,0,0,0);
    }

//    public void generateGraphics(int color){
//        pixels = new int[w*h];
//        for(int y = 0; y < h; y++){
//            for(int x=0; x < w; x++){
//                pixels[x + y * w] = color;
//            }
//        }
//    }

    public void generateGraphics(int boarderWidth, int color){
        pixels = new int[w*h];
        for (int i =0; i< pixels.length; i++){
            pixels[i] = Main.alpha;
        }
        //top boarder
        for(int y = 0; y< boarderWidth; y++){
            for (int x = 0; x < w; x++){
                pixels[x + y * w] = color;
            }
        }
//left boarder
        for (int y = 0; y < h; y++){
            for (int x = 0; x < boarderWidth; x++){
                pixels[x + y * w] = color;
            }
        }
//R
        for (int y = 0; y < h; y++){
            for (int x = w - boarderWidth; x < w; x++){
                pixels[x + y * w] = color;
            }
        }
// B
        for(int y = h - boarderWidth; y < h; y++){
            for (int x = 0; x < w; x++){
                pixels[x + y * w] = color;
            }
        }


    }


    public int[] getPixels(){
        if(pixels != null) {
            return pixels;
        }else{
            System.out.println("Attempted to rectangle without graphics");
        }
        return null;
    }


}
