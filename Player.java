package newgame;

import java.util.*;

public class Player implements GameObject {

    private Rectangle playerRectangle;
    private int speed = 7;
    private int zoom;
    private Sprite sprite;
    private AnimatedSprite animatedSprite = null;
    //0=Up, 2=down, 1=left, 3=right
    private int direction = 0;
    private int[][] previousCoords;
    private Map detectorField;
    private boolean isFollower;

    public Player(Sprite sprite, Map map, int newZoom, boolean follower){
        this.sprite = sprite;
        if (sprite instanceof AnimatedSprite){
            animatedSprite = (AnimatedSprite) sprite;
        }

        updateDirection();
        playerRectangle=new Rectangle(24,16,16,24);
        playerRectangle.generateGraphics(3,0xFF00FF90);

        previousCoords = new int[5][2];
        detectorField = map;
        zoom = newZoom;
        isFollower = follower;
    }

    public void updateDirection(){

        if (animatedSprite != null){
            animatedSprite.setAnimatedRange(direction*4, direction*4+4);
        }


    }

    //call every time
    public void render(RenderHandler renderer, int xZoom, int yZoom){
//        renderer.renderRectangle(playerRectangle,xZoom,yZoom);
        if (animatedSprite != null) {
            renderer.renderSprite(animatedSprite, playerRectangle.x,playerRectangle.y,xZoom,yZoom);
        }else if (sprite!=null){
            renderer.renderSprite(sprite, playerRectangle.x,playerRectangle.y,xZoom,yZoom);
        }else{
            renderer.renderRectangle(playerRectangle, xZoom,yZoom);
        }

    }
    //call at 60 fps
    public void update(Main main){

        KeyBoardListener keyListener = main.getKeyListener();
        int coord;
        int newDirection = direction;
        boolean didMove = false;

        if (keyListener.up()){
            coord = collisionOccurred(0);
            newDirection = 2;
            if(coord == Integer.MAX_VALUE) {
                if(!isFollower)
                    playerRectangle.y -= speed;
                didMove = true;
            } else {
                playerRectangle.y = coord * (16 * zoom);
            }
        }
        if (keyListener.down()){
            coord = collisionOccurred(1);
            newDirection = 0;
            if(coord == Integer.MAX_VALUE) {
                if(!isFollower)
                    playerRectangle.y += speed;
                didMove = true;
            } else {
                playerRectangle.y = coord * (16 * zoom);
            }
        }
        if (keyListener.right()){
            coord = collisionOccurred(2);
            newDirection = 1;
            if(coord == Integer.MAX_VALUE) {
                if(!isFollower)
                    playerRectangle.x += speed;
                didMove = true;
            } else {
                playerRectangle.x = coord * (16 * zoom);
            }
        }
        if (keyListener.left()){
            coord = collisionOccurred(3);
            newDirection = 3;
            if(coord == Integer.MAX_VALUE) {
                if(!isFollower)
                    playerRectangle.x -= speed;
                didMove = true;
            } else {
                playerRectangle.x = coord * (16 * zoom);
            }
        }
        if(newDirection != direction) {
            direction=newDirection;
            updateDirection();
        }
        if(!didMove){
            animatedSprite.reset();
        } else {
            if(!isFollower) {
                main.setCoords(playerRectangle.x, playerRectangle.y);
            } else {
                playerRectangle.x = main.getCoords()[main.getCoords().length - 1][0];
                playerRectangle.y = main.getCoords()[main.getCoords().length - 1][1];
            }
        }
        
        if(!isFollower)
            updateCamera(main.getRenderer().getCamera());
        
        if(didMove)
            animatedSprite.update(main);

    }
    
    public int collisionOccurred(int direction) {
        ArrayList<Map.MappedTile> temp = detectorField.getCollisionTypes();
        
        for(int index = 0; index < temp.size(); index ++) {
            switch(direction) {
                case 0: {
                    if((Math.abs(playerRectangle.y - (temp.get(index).y + 1) * (16 * zoom)) < 8) && (Math.abs(playerRectangle.x - temp.get(index).x * (16 * zoom)) < (16 * zoom))) {
                        if(temp.get(index).isTeleporter()) {
                            return teleport(0);
                        } else {
                            return temp.get(index).y + 1;
                        }
                    }
                    break;
                }
                case 1: {
                    if((Math.abs((playerRectangle.y + (16 * zoom)) - (temp.get(index).y - 1) * (16 * zoom)) < 8) && (Math.abs(playerRectangle.x - temp.get(index).x * (16 * zoom)) < (16 * zoom))) {
                        if(temp.get(index).isTeleporter()) {
                            return teleport(1);
                        } else {
                            return temp.get(index).y - 2;
                        }
                    }
                    break;
                }
                case 2: {
                    if((Math.abs(playerRectangle.x - (temp.get(index).x - 1) * (16 * zoom)) < 8) && (Math.abs((playerRectangle.y + (playerRectangle.h / 2)) - temp.get(index).y * (16 * zoom)) < playerRectangle.h * 2)) {
                        if(temp.get(index).isTeleporter()) {
                            return teleport(2);
                        } else {
                            return temp.get(index).x - 1;
                        }
                    }
                    break;
                }
                case 3: {
                    if((Math.abs(playerRectangle.x - (temp.get(index).x + 1) * (16 * zoom)) < 8) && (Math.abs((playerRectangle.y + (playerRectangle.h / 2)) - temp.get(index).y * (16 * zoom)) < playerRectangle.h * 2)) {
                        if(temp.get(index).isTeleporter()) {
                            return teleport(3);
                        } else {
                            return temp.get(index).x + 1;
                        }
                    }
                    break;
                }
                default: {
                    return Integer.MAX_VALUE;
                }
            }
        }
        return Integer.MAX_VALUE;
    }
    
    private int teleport(int direction) {
        switch(direction) {
            case 0: {
                if((playerRectangle.y / (16 * zoom)) < 100) {
                    return -109;
                } else {
                    return 24;
                }
            }
            case 1: {
                if((playerRectangle.y / (16 * zoom)) > -100) {
                    return 109;
                } else {
                    return -26;
                }
            }
            case 2: {
                return 100;
            }
            case 3: {
                return 25;
            }
            default: {
                return 0;
            }
        }
    }
    
    public void updateCamera(Rectangle camera){
        camera.x = playerRectangle.x - (camera.w/2) + (8 * zoom);
        camera.y = playerRectangle.y - (camera.h/2) + (16 * zoom);


    }
    
    public int[][] getPreviousCoordinates() {
        return previousCoords;
    }
    
    public int[] getCurrentCoords() {
        int[] temp = new int[2];
        
        temp[0] = playerRectangle.x;
        temp[1] = playerRectangle.y;
        
        return temp;
    }
    
    public void setCoords(int newX, int newY) {
        playerRectangle.x = newX * (16 * zoom);
        playerRectangle.y = newY * (16 * zoom);
    }

    public boolean isAFollower() {
        return isFollower;
    }



}
