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
		boardModel = new BoardModel();
		
		boardView = new BoardView(800,800,8,8);
		new Window(800,800, boardView, "Xadrez");
	}
	
	public void setupBoard()
	{
		if (boardView == null)
			return;
		
		//adiciona o controller como listener do mouse (isso nao esta mt certo)
		boardView.addMouseListener(this);
		//registra a view como observador do modelo
		this.registerObserver(boardView);
	}
	
	public void registerObserver(BoardObserver o)
	{
		if(boardModel != null)
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
		
		//comentado temporariamente pois a funcao esta abortando
//		if(boardModel != null)
//			boardModel.click(coordX, coordY);
//		
	}
	
	@Override public void mouseEntered(MouseEvent arg0) {}
	@Override public void mouseExited(MouseEvent arg0) {}
	@Override public void mousePressed(MouseEvent arg0) {}
	@Override public void mouseReleased(MouseEvent arg0) {}
}
