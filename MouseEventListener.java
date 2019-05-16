package newgame;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseEventListener implements MouseListener, MouseMotionListener {

    private Main main;

    public MouseEventListener(Main main){
        this.main = main;

    }

    public void mouseClicked(MouseEvent event){
        int mouseX, mouseY;
        if(main.getTitleState() == 0) {
            mouseX = MouseInfo.getPointerInfo().getLocation().x - main.getX();
            mouseY = MouseInfo.getPointerInfo().getLocation().y - main.getY();
            if((mouseX >= 303) && (mouseX <= 653) && (mouseY >= 552) && (mouseY <= 592)) {
                main.setTitleState(1);
            } else if((mouseX >= 303) && (mouseX <= 653) && (mouseY >= 622) && (mouseY <= 662)) {
                main.setTitleState(2);
            } else if((mouseX >= 403) && (mouseX <= 553) && (mouseY >= 692) && (mouseY <= 732)) {
                System.exit(0);
            }
        }
    }
    public void mouseDragged(MouseEvent event){

    }
    public void mouseEntered(MouseEvent event){

    }
    public void mouseExited(MouseEvent event){

    }
    public void mouseMoved(MouseEvent event){

    }
    public void mousePressed(MouseEvent event){

    }
    public void mouseReleased(MouseEvent event){

    }




}
