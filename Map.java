package newgame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Map {
    private Tiles tileSet;
    private int fillTileID = -1;
    private MappedTile[][][] mappedTiles = new MappedTile[1000][1000][4];
    private ArrayList<MappedTile> collisionTiles = new ArrayList<>();


    public  Map(File mapFile, Tiles tileSet){
        this.tileSet = tileSet;
        try{
            Scanner scanner = new Scanner(mapFile);
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if(!line.startsWith("//"))
                {
                    if(line.contains(":")){
                        String[] splitString = line.split(":");
                        if(splitString[0].equalsIgnoreCase("Fill")){
                            fillTileID = Integer.parseInt(splitString[1]);
                            continue;
                        } else if(splitString[0].equalsIgnoreCase("Rect")) {
//                            System.out.println("Rect command detected.");
                            String[] splitAgain = splitString[1].split(",");
                            if(splitAgain.length >= 5) {
//                                System.out.println("Command contains proper argument count.");
//                                System.out.println("Arguments: " + splitString[1]);
                                MappedTile[][] mapTilePlane = new MappedTile[Integer.parseInt(splitAgain[3]) - Integer.parseInt(splitAgain[1])]
                                                                            [Integer.parseInt(splitAgain[4]) - Integer.parseInt(splitAgain[2])];
                                for(int vertIndex = 0; vertIndex < (Integer.parseInt(splitAgain[4]) - Integer.parseInt(splitAgain[2])); vertIndex++) {
                                    for(int horzIndex = 0; horzIndex < (Integer.parseInt(splitAgain[3]) - Integer.parseInt(splitAgain[1])); horzIndex++) {
                                        mapTilePlane[horzIndex][vertIndex] = new MappedTile(Integer.parseInt(splitAgain[0]),
                                                                                            Integer.parseInt(splitAgain[1]) + horzIndex,
                                                                                            Integer.parseInt(splitAgain[2]) + vertIndex);
                                        for(int layer = 0; layer < 4; layer++) {
                                            if(mappedTiles[Integer.parseInt(splitAgain[1]) + horzIndex + 500][Integer.parseInt(splitAgain[2]) + vertIndex + 500][layer] == null) {
                                                mappedTiles[Integer.parseInt(splitAgain[1]) + horzIndex + 500][Integer.parseInt(splitAgain[2]) + vertIndex + 500][layer] = mapTilePlane[horzIndex][vertIndex];
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            continue;
                        }
                    }
                    String[] splitString = line.split(",");
                    if (splitString.length >= 3){
                        int mappingLoopXIndex, mappingLoopYIndex;
                        switch(Integer.parseInt(splitString[0])) {
                            case 45: {
                                mappingLoopXIndex = 2;
                                mappingLoopYIndex = 4;
                                break;
                            }
                            case 53: {
                                mappingLoopXIndex = 1;
                                mappingLoopYIndex = 2;
                                break;
                            }
                            case 55: {
                                mappingLoopXIndex = 3;
                                mappingLoopYIndex = 4;
                                break;
                            }
                            case 67: {
                                mappingLoopXIndex = 1;
                                mappingLoopYIndex = 2;
                                break;
                            }
                            case 69: {
                                mappingLoopXIndex = 1;
                                mappingLoopYIndex = 2;
                                break;
                            }
                            case 71: {
                                mappingLoopXIndex = 1;
                                mappingLoopYIndex = 2;
                                break;
                            }
                            case 73: {
                                mappingLoopXIndex = 1;
                                mappingLoopYIndex = 2;
                                break;
                            }
                            case 75: {
                                mappingLoopXIndex = 1;
                                mappingLoopYIndex = 2;
                                break;
                            }
                            case 77: {
                                mappingLoopXIndex = 6;
                                mappingLoopYIndex = 4;
                                break;
                            }
                            case 101: {
                                mappingLoopXIndex = 6;
                                mappingLoopYIndex = 4;
                                break;
                            }
                            default: {
                                mappingLoopXIndex = 1;
                                mappingLoopYIndex = 1;
                                break;
                            }
                        }
                        for(int i = 0; i < mappingLoopYIndex; i++) {
                            for(int j = 0; j < mappingLoopXIndex; j++) {
                                MappedTile mappedTile = new MappedTile(Integer.parseInt(splitString[0]) + (mappingLoopXIndex*i + j),
                                                                        Integer.parseInt(splitString[1]) + j,
                                                                        Integer.parseInt(splitString[2]) + i);
                                for(int layer = 0; layer < 4; layer++) {
                                    if(mappedTiles[Integer.parseInt(splitString[1]) + j + 500][Integer.parseInt(splitString[2]) + i + 500][layer] == null) {
                                        mappedTiles[Integer.parseInt(splitString[1]) + j + 500][Integer.parseInt(splitString[2]) + i + 500][layer] = mappedTile;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            collisionTiles = setCollisionTypes(0);
            collisionTiles.addAll(setCollisionTypes(1));
            collisionTiles.addAll(setCollisionTypes(2));
            collisionTiles.addAll(setCollisionTypes(3));

        }
        catch(FileNotFoundException e){
            e.printStackTrace();

        }
        catch(NumberFormatException n) {
            n.printStackTrace();
        }

    }
    
    public MappedTile[][][] getMappedTileList() {
        return mappedTiles;
    }
    
    public ArrayList<MappedTile> getCollisionTypes() {
        return collisionTiles;
    }
    
    private ArrayList<MappedTile> setCollisionTypes(int layer) {
        ArrayList<MappedTile> temp = new ArrayList<>();
        
        for(int index = 0; index < mappedTiles.length; index++) {
            for(int secdex = 0; secdex < mappedTiles[index].length; secdex++) {
                if(mappedTiles[secdex][index][layer] != null) {
                    switch(mappedTiles[secdex][index][layer].id) {
                        case 0: {
                            mappedTiles[secdex][index][layer].setTeleporter(true);
                            temp.add(mappedTiles[secdex][index][layer]);
                            break;
                        }
                        case 2: {
                            temp.add(mappedTiles[secdex][index][layer]);
                            break;
                        }
                        case 3: {
                            temp.add(mappedTiles[secdex][index][layer]);
                            break;
                        }
                        case 20: {
                            temp.add(mappedTiles[secdex][index][layer]);
                            break;
                        }
                        case 21: {
                            temp.add(mappedTiles[secdex][index][layer]);
                            break;
                        }
                        case 22: {
                            temp.add(mappedTiles[secdex][index][layer]);
                            break;
                        }
                        case 23: {
                            temp.add(mappedTiles[secdex][index][layer]);
                            break;
                        }
                        case 32: {
                            temp.add(mappedTiles[secdex][index][layer]);
                            break;
                        }
                        case 34: {
                            temp.add(mappedTiles[secdex][index][layer]);
                            break;
                        }
                        case 35: {
                            temp.add(mappedTiles[secdex][index][layer]);
                            break;
                        }
                        case 36: {
                            temp.add(mappedTiles[secdex][index][layer]);
                            break;
                        }
                        case 37: {
                            temp.add(mappedTiles[secdex][index][layer]);
                            break;
                        }
                        case 38: {
                            temp.add(mappedTiles[secdex][index][layer]);
                            break;
                        }
                    }
                    
                    if(mappedTiles[secdex][index][layer].id >= 45)
                        temp.add(mappedTiles[secdex][index][layer]);
                }
            }
        }
        return temp;
    }
    
    public void render(RenderHandler render, int xZoom,int yZoom){
        int tileWidth = 16 * xZoom;
        int tileHeight = 16 * yZoom;

        if (fillTileID>=0){
            Rectangle camera = render.getCamera();
            for (int y=camera.y -tileHeight- (camera.y % tileHeight); y<camera.y + camera.h; y+= tileHeight){
                for (int x=camera.x -tileWidth- (camera.x % tileWidth); x<camera.x + camera.w; x+= tileWidth){
                    tileSet.renderTile(fillTileID,render, x,y,xZoom,yZoom);
                    for(int layer = 0; layer < 4; layer++)
                        if(mappedTiles[(x / tileWidth) + 500][(y / tileHeight) + 500][layer] != null)
                            tileSet.renderTile(mappedTiles[(x / tileWidth) + 500][(y / tileHeight) + 500][layer].id, render, mappedTiles[(x / tileWidth) + 500][(y / tileHeight) + 500][layer].x*tileWidth, mappedTiles[(x / tileWidth) + 500][(y / tileHeight) + 500][layer].y*tileHeight, xZoom, yZoom);
                }
            }
        }

    }

    class MappedTile{

        public int id, x, y;
        private boolean isTeleporter;

        public MappedTile(int id, int x, int y){
            this.id=id;
            this.x=x;
            this.y=y;
            isTeleporter = false;
        }

        public boolean isTeleporter() {
            return isTeleporter;
        }
        
        public void setTeleporter(boolean isIt) {
            isTeleporter = isIt;
        }

    }




}
