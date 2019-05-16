package newgame;
import java.awt.*;
//import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {
    private BufferedImage view;
    private Rectangle camera;
    private int[] pixels;



    public RenderHandler(int width, int height){
        //create buffer image to rep view
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //create camera
        camera = new Rectangle(0,0, width, height);
//        camera.x = -100;
//        camera.y = -30;

        //create array of pixels
        pixels = ((DataBufferInt)view.getRaster().getDataBuffer()).getData();
        //Create pixel rows
//        for(int heightIndex = 0; heightIndex < height; heightIndex++){
//            int randomPixel = (int)(Math.random() * 0xFFFFFF);
//            for(int widthIndex = 0; widthIndex < width; widthIndex++){
//                pixels[heightIndex*width + widthIndex] = randomPixel;
//            }
//
//        }

    }
//renders pixel array to screen
    public void render(Graphics g){

////Random pixels
//        for(int index = 0; index < pixels.length; index++){
//            pixels[index] = (int)(Math.random() * 0xFFFFFF);
//        }

        g.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);

    }
//render our pixel to our array of pixels
    public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom){
       int[] imagePixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
       renderArray(imagePixels, image.getWidth(), image.getHeight(), xPosition, yPosition, xZoom, yZoom);
//       for(int y = 0; y < image.getHeight(); y++){
//           for(int x = 0; x < image.getWidth(); x++){
//               for (int yZoomPosition=0; yZoomPosition < yZoom; yZoomPosition++) {
//                   for (int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++) {
//                       setPixel(imagePixels[x + y * image.getWidth()], ((x * xZoom) + xPosition + xZoomPosition)
//                               ,((y * yZoom) + yPositition + yZoomPosition));
//
//                   }
//               }
//           }
//       }
    }

    public void renderSprite(Sprite sprite, int xPosition, int yPosition, int xZoom, int yZoom){
        renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), xPosition, yPosition, xZoom, yZoom);


    }

    public void renderRectangle(Rectangle rectangle, int xZoom, int yZoom){
        int[] rectanglePixels = rectangle.getPixels();
        if(rectanglePixels != null){
            renderArray(rectanglePixels,rectangle.w, rectangle.h, rectangle.x, rectangle.y, xZoom,yZoom);


        }


    }


    public void renderArray(int[] renderPixels, int renderWidth, int renderHeight, int xPosition, int yPosition, int xZoom, int yZoom){
        for(int y = 0; y < renderHeight; y++){
            for(int x = 0; x < renderWidth; x++){
                for (int yZoomPosition=0; yZoomPosition < yZoom; yZoomPosition++) {
                    for (int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++) {
                        setPixel(renderPixels[x + y * renderWidth], ((x * xZoom) + xPosition + xZoomPosition)
                                ,((y * yZoom) + yPosition + yZoomPosition));
                    }
                }
            }
        }

    }
    private void setPixel(int pixel, int x, int y){
        if(x >= camera.x && y >= camera.y && x<= camera.x
                + camera.w && y <= camera.y + camera.h) {
            int pixelIndex = (x - camera.x) + (y - camera.y) * view.getWidth();
            if (pixels.length > pixelIndex && pixel != Main.alpha) {
                pixels[pixelIndex] = pixel;
            }
        }
    }

    public Rectangle getCamera(){
       return camera;
    }

    public void clear(){
        for(int i = 0; i<pixels.length;i++){
            pixels[i] = 0;

        }
    }

}
