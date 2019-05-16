package newgame;

import java.awt.event.*;
import java.io.IOException;

public class ButtonListener implements ActionListener {
    
    private Main main;
    
    public ButtonListener(Main newMain) {
        main = newMain;
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == main.getStatusButton()) {
            
        } else if(event.getSource() == main.getSaveButton()) {
            main.updateSave();
            main.getSavedLabel().setVisible(true);
        } else if(event.getSource() == main.getBackButton()) {
            
        }
    }
    
}
