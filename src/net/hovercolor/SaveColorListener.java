package net.hovercolor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SaveColorListener implements KeyListener{

	@Override
	public void keyTyped(KeyEvent e){
		
	}

	@Override
	public void keyPressed(KeyEvent e){
		
	}

	@Override
	public void keyReleased(KeyEvent e){
		if(e.getKeyChar() == 's'){
			Screen screen = Screen.getScreen();
			screen.addSavedColor(screen.getCurrentColor());
		}
		
	}

}
