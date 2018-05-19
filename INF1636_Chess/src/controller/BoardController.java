package controller;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.BoardView;
import view.Window;

public class BoardController implements MouseListener {
	private BoardView boardView;
	
	public BoardController() {
		boardView = new BoardView(800,800,8,8);
		boardView.addMouseListener(this);
		new Window(800,800, boardView, "Xadrez");
	}
	
	/* -------- Mouse Listener --------- */
	@Override
	public void mouseClicked(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();
		
		int coordX = x / boardView.getCellWidth();
		int coordY = y / boardView.getCellHeight();
		System.out.print("posX= " + x + " posY= " + y + "\n" + 
						 "coordX= " + coordX + "coordY= " + coordY + "\n");
	}
	
	@Override public void mouseEntered(MouseEvent arg0) {}
	@Override public void mouseExited(MouseEvent arg0) {}
	@Override public void mousePressed(MouseEvent arg0) {}
	@Override public void mouseReleased(MouseEvent arg0) {}
}
