package controller;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.BoardModel;
import view.BoardView;
import view.Window;

import observer.*;

public class BoardController implements MouseListener {
	private BoardView boardView;
	private BoardModel boardModel;
	
	public BoardController() {
		
		GameController.getInstance().setBoardController(this);
		boardModel = new BoardModel();
		
		boardView = new BoardView(800,800,8,8);
		boardView.addMouseListener(this);
		new Window(800,800, boardView, "Xadrez");
	}
	
	public void registerObserver(BoardObserver o)
	{
		boardModel.add(o);
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
