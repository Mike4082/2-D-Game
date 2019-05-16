package newgame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.Runnable;
import java.lang.Thread;
import javax.imageio.ImageIO;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class Main extends JFrame implements Runnable {
    public static int alpha = 0xFFFE00DC;
    private int titleState;
    private int[][] playerCoords;
    private File saveFile;
    private Scanner saveFileReader;
    private FileWriter saveFileWriter;

    private Canvas canvas = new Canvas();
    private RenderHandler renderer;
    //private BufferedImage testImage;
    //private Sprite testSprite;
    private SpriteSheet sheet;

    private Rectangle testRectangle = new Rectangle(30,30,100,100);
    private Tiles tiles, titleTiles;
    private Map map;
    private GameObject[] objects;
    private KeyBoardListener keyListener = new KeyBoardListener(this);
    private Player player, player2;
    private MouseEventListener mouseListener = new MouseEventListener(this);
    private ButtonListener buttonListener = new ButtonListener(this);
    
    private String dir = System.getProperty("user.dir");
    private final int GAME_ZOOM = 3;
    
    private JLabel menuLabel, savedLabel;
    private JButton status, save, back;

    public Main(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1013,813);
        setLocationRelativeTo(null);
        setTitle("Lost Duality");
        add(canvas);
        setVisible(true);
        setResizable(false);
        setLayout(null);
        canvas.createBufferStrategy(3);
        renderer = new RenderHandler(getWidth(),getHeight());
        
        playerCoords = new int[11][2];

        //loads assets
        saveFile = new File(dir + "\\src\\newgame\\Save.txt");
        BufferedImage sheetImage = loadImage("Tiles1.png");
        sheet = new SpriteSheet(sheetImage);
        sheet.loadSprites(16,16);
        
        BufferedImage playerSheetImage = loadImage("Character1.png");
        BufferedImage player2SheetImage = loadImage("Character2.png");
        SpriteSheet playerSheet = new SpriteSheet(playerSheetImage);
        SpriteSheet player2Sheet = new SpriteSheet(player2SheetImage);
        playerSheet.loadSprites(16,24);
        player2Sheet.loadSprites(16,24);

        //testing animated
        AnimatedSprite playerAnimations = new AnimatedSprite(playerSheet, 3);
        AnimatedSprite player2Animations = new AnimatedSprite(player2Sheet, 3);
        
        tiles = new Tiles(new File(dir + "\\src\\newgame\\Tiles"),sheet);
        map = new Map(new File(dir + "\\src\\newgame\\Map"), tiles);
        //testImage = loadImage("GrassTile.png");
        //testSprite = sheet.getSprite(4,1);
        testRectangle.generateGraphics(2,1223);
        objects = new GameObject[2];
        player = new Player(playerAnimations, map, GAME_ZOOM, false);
        player2 = new Player(player2Animations, map, GAME_ZOOM, true);
        objects[0] = player;
        objects[1] = player2;
        
        menuLabel = new JLabel("Menu");
        menuLabel.setFont(new Font("Calibri", Font.BOLD, 60));
        menuLabel.setBounds(1070, 30, 200, 100);
        savedLabel = new JLabel("Game Saved");
        savedLabel.setFont(new Font("Calibri", Font.PLAIN, 40));
        savedLabel.setBounds(1045, 310, 250, 100);
        savedLabel.setVisible(false);
        status = new JButton("Status");
        status.setBounds(1020, 150, 250, 70);
        status.setFont(new Font("Calibri", Font.PLAIN, 40));
        save = new JButton("Save");
        save.setBounds(1020, 250, 250, 70);
        save.setFont(new Font("Calibri", Font.PLAIN, 40));
        back = new JButton("Back");
        back.setBounds(1020, 650, 250, 70);
        back.setFont(new Font("Calibri", Font.PLAIN, 40));
        setSize(1000, 800);
        //add listeners
        canvas.addKeyListener(keyListener);
        canvas.addFocusListener(keyListener);
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseListener);
        status.addActionListener(buttonListener);
        save.addActionListener(buttonListener);
        back.addActionListener(buttonListener);
        
        
        add(menuLabel);
        add(savedLabel);
        add(status);
        add(save);
        add(back);
        
        BufferedImage titleSheetImage = loadImage("TitleSheet.png");
        SpriteSheet titleSheet = new SpriteSheet(titleSheetImage);
        titleSheet.loadSprites(100, 80);
        titleTiles = new Tiles(new File(dir + "\\src\\newgame\\TitleTiles"),titleSheet);
        titleState = 0;
    }

    public void update(){
        for (int i = 0; i<objects.length; i++){
            objects[i].update(this);
        }

    }

    private BufferedImage loadImage(String path){
        try {
            BufferedImage loadedImage = ImageIO.read(Main.class.getResource(path));
            BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            formattedImage.getGraphics().drawImage(loadedImage,0,0,null);
            return formattedImage;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }

    }

    public void render(){

            int mouseX, mouseY;
            BufferStrategy bufferStrategy = canvas.getBufferStrategy();
            Graphics g = bufferStrategy.getDrawGraphics();
            super.paint(g);
            //renderer.renderSprite(testSprite,0,0,5,5);
            if(titleState == 0) {
                titleTiles.renderTile(0, renderer, 0, 0, 10, 10);
                mouseX = MouseInfo.getPointerInfo().getLocation().x - getX();
                mouseY = MouseInfo.getPointerInfo().getLocation().y - getY();
                if((mouseX >= 303) && (mouseX <= 653) && (mouseY >= 552) && (mouseY <= 592)) {
                    titleTiles.renderTile(2, renderer, 0, 0, 10, 10);
                } else if((mouseX >= 303) && (mouseX <= 653) && (mouseY >= 622) && (mouseY <= 662)) {
                    titleTiles.renderTile(3, renderer, 0, 0, 10, 10);
                } else if((mouseX >= 403) && (mouseX <= 553) && (mouseY >= 692) && (mouseY <= 732)) {
                    titleTiles.renderTile(4, renderer, 0, 0, 10, 10);
                } else {
                    titleTiles.renderTile(1, renderer, 0, 0, 10, 10);
                }
            } else {
                tiles.renderTile(3,renderer,0, 0, GAME_ZOOM, GAME_ZOOM);
                map.render(renderer, GAME_ZOOM,GAME_ZOOM);
                
                for (int i = 0; i<objects.length; i++) {
                    objects[i].render(renderer, GAME_ZOOM, GAME_ZOOM);
                }
            }

//            renderer.renderSprite(playerAnimations, 30, 30,xZoom,yZoom);

            //renderer.renderRectangle(testRectangle,1,1);
            renderer.render(g);

            g.dispose();
            bufferStrategy.show();
            renderer.clear();
    }


    public void run(){
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        int i = 0;
        int x = 0;
        long lastTime = System.nanoTime(); // long store more data 2^63
        double nanoSecondConversion = 1000000000.0/60;//frames per second
        double changeSeconds = 0;
        
        while(titleState == 0) {
            render();
        }
        try {
            if(titleState == 1) {
                saveFileReader = new Scanner(saveFile);
                player.setCoords((int)(saveFileReader.nextInt() / (16 * GAME_ZOOM)), (int)(saveFileReader.nextInt() / (16 * GAME_ZOOM)));
                saveFileReader.close();
            }
        } catch(FileNotFoundException f) {
            System.out.println("Save file not found.");
            System.exit(0);
        }
        while (true){
            long now = System.nanoTime();
            changeSeconds += (now - lastTime)/nanoSecondConversion;

            while (changeSeconds >= 1){
                update();
                changeSeconds = 0;

            }
            render();
            lastTime = now;
        }


    }

    public void paint(Graphics g){
        super.paint(g);


    }

    public static void main(String[] args){
        Main main = new Main();
        Thread gameThread = new Thread(main);
        gameThread.start();
    }
    
    public void updateSave() {
        try {
            saveFileWriter = new FileWriter(saveFile, false);
            
            saveFileWriter.write(player.getCurrentCoords()[0] + "\n" + player.getCurrentCoords()[1]);
            saveFileWriter.close();
        } catch(IOException io) {
            System.out.println("Save file write error.");
        }
        
    }
    
    public int[][] getCoords() {
        return playerCoords;
    }
    
    public void setCoords(int x, int y) {
        for(int i = playerCoords.length - 1; i > 0; i--) {
            playerCoords[i][0] = playerCoords[i - 1][0];
            playerCoords[i][1] = playerCoords[i - 1][1];
        }
        playerCoords[0][0] = x;
        playerCoords[0][1] = y;
    }
    
    public int getTitleState() {
        return titleState;
    }
    
    public void setTitleState(int newState) {
        titleState = newState;
    }
    
    public Object getStatusButton() {
        return status;
    }
    
    public Object getSaveButton() {
        return save;
    }
    
    public Object getBackButton() {
        return back;
    }
    
    public JLabel getSavedLabel() {
        return savedLabel;
    }
    
    public FileWriter getFileWriter() {
        return saveFileWriter;
    }

    public KeyBoardListener getKeyListener(){
        return keyListener;

    }

    public MouseEventListener getMouseListener(){
        return mouseListener;

    }
    
    public ButtonListener getButtonListener() {
        return buttonListener;
    }

    public RenderHandler getRenderer() {
        return renderer;
    }

}
