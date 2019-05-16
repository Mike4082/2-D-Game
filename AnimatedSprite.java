package newgame;

import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite implements GameObject{

    private Sprite[] sprites;
    private int currentSprite = 0;
    private int speed;
    private int counter = 0;
    private int startSprite=0;
    private int endSprite;

//    public AnimatedSprite(SpriteSheet sheet, Rectangle[] positions, int speed) {
//        
//        
//        sprites = new Sprite[positions.length];
//        this.speed = speed;
//        this.endSprite = positions.length - 1;
//
//        for (int i = 0; i < positions.length; i++){
//            sprites[i] = new Sprite(sheet, positions[i].x,positions[i].y,
//                    positions[i].w,positions[i].h);
//        }
//
//    }

    public AnimatedSprite(SpriteSheet sheet, int speed) {
        super(sheet, 0, 0, 16, 32);
        
        sprites = sheet.getLoadedSprites();
        this.speed = speed;
        this.endSprite = sprites.length - 1;
    }

//    public AnimatedSprite(BufferedImage[] images, int speed) {
//        super(images[0]);
//        
//        sprites = new Sprite[images.length];
//        this.speed = speed;
//        this.endSprite = images.length - 1;
//        for (int i = 0; i<images.length; i++){
//            sprites[i] = new Sprite(images[i]);
//        }
//    }

    //Render is dealt specifically with the layer class
    public void render(RenderHandler renderer, int xZoom, int yZoom){

    }

    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera,int xZoom, int yZoom){
        return false;

    }


    //call at 60 fps
    public void update(Main main){
        counter++;
        if (counter>= speed){
            counter=0;
            incrementSprite();
        }

    }

    public void reset(){
        counter = 0;
        currentSprite = startSprite;
    }
public void setAnimatedRange(int startSprite, int endSprite){
        this.startSprite = startSprite;
        this.endSprite = endSprite;
        reset();


    }

    public int getWidth(){
        return sprites[currentSprite].getWidth();
    }
    public int getHeight(){
        return sprites[currentSprite].getHeight();
    }
    public int[] getPixels(){
        return sprites[currentSprite].getPixels();
    }

    public void incrementSprite(){
        currentSprite++;
        if (currentSprite >= endSprite){
            currentSprite = startSprite;
        }
    }


}  
