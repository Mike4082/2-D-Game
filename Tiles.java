package newgame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Tiles {
    private SpriteSheet spriteSheet;
    private ArrayList<Tile> tilesList = new ArrayList<Tile>();

    //This will only work assuming the sprites in the spriteSheet have been loaded.
    public Tiles(File tilesFile, SpriteSheet spriteSheet)
    {
        this.spriteSheet = spriteSheet;
        try
        {
            Scanner scanner = new Scanner(tilesFile);
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if(!line.startsWith("//") && !line.matches(""))
                {
                    String[] splitString = line.split("-");
                    for(int i = 0; i < splitString.length; i++) {
                        System.out.print(splitString[i] + " ");
                    }
                    System.out.println("");
                    String tileName = splitString[0];
                    int spriteX = Integer.parseInt(splitString[1]);
                    int spriteY = Integer.parseInt(splitString[2]);
                    if(splitString.length >= 5) {
                        System.out.println(splitString[0] + " is a multi-tiled sprite.");
                        Tile[][] tileTemp = new Tile[Integer.parseInt(splitString[3])][Integer.parseInt(splitString[4])];
                        for(int yIndex = 0; yIndex < Integer.parseInt(splitString[4]); yIndex++) {
                            for(int xIndex = 0; xIndex < Integer.parseInt(splitString[3]); xIndex++) {
                                tileTemp[xIndex][yIndex] = new Tile(tileName + xIndex + "x" + yIndex, spriteSheet.getSprite(spriteX + xIndex, spriteY + yIndex));
                                tilesList.add(tileTemp[xIndex][yIndex]);
                            }
                        }
                    } else {
                        Tile tile = new Tile(tileName, spriteSheet.getSprite(spriteX, spriteY));
                        tilesList.add(tile);
                    }
                }
            }
            System.out.println(tilesList.size());
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Tile> getTilesList() {
        return tilesList;
    }

    public void renderTile(int tileID, RenderHandler renderer, int xPosition, int yPosition, int xZoom, int yZoom)
    {
        if(tileID >= 0 && tilesList.size() > tileID)
        {
            renderer.renderSprite(tilesList.get(tileID).sprite, xPosition, yPosition, xZoom, yZoom);
        }
        else
        {
            System.out.println("TileID " + tileID + " is not within range " + tilesList.size() + ".");
        }
    }

    class Tile
    {
        public String tileName;
        public Sprite sprite;

        public Tile(String tileName, Sprite sprite)
        {
            this.tileName = tileName;
            this.sprite = sprite;
        }
    }







}
