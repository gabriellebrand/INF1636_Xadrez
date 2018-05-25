package controller;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.BoardModel;
import view.BoardView;
import view.Window;
import observer.*;
import resources.Pair;

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
		registerObserver(boardView);
		
		//adiciona as imagens das pecas na boardView
		String path = "INF1636_Chess/src/images/";
		Pair[] pieceImages = new Pair[] { new Pair("bishopW", path + "bishop_blue.png"),
										   new Pair("bishopB", path + "bishop_gray.png"),
										   new Pair("kingW", path + "king_blue.png"),
										   new Pair("kingB", path + "king_gray.png"),
										   new Pair("knightW", path + "knight_blue.png"),
										   new Pair("knightB", path + "knight_gray.png"),
										   new Pair("pawnW", path + "pawn_blue.png"),
										   new Pair("pawnB", path + "pawn_gray.png"),
										   new Pair("queenW", path + "queen_blue.png"),
										   new Pair("queenB", path + "queen_gray.png"),
										   new Pair("rookW", path + "rook_blue.png"),
										   new Pair("rookB", path + "rook_gray.png") };
		boardView.setPiecesImages(pieceImages);
		boardView.repaint();
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
	}
	
	@Override public void mouseEntered(MouseEvent arg0) {}
	@Override public void mouseExited(MouseEvent arg0) {}
	@Override public void mousePressed(MouseEvent arg0) {}
	@Override public void mouseReleased(MouseEvent arg0) {}
}
