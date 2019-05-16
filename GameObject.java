package newgame;

public interface GameObject {
//call every time
    public void render(RenderHandler renderer, int xZoom, int yZoom);
//call at 60 fps
    public void update(Main main);

}
