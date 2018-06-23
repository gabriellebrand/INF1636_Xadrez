package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import view.BoardView;
import view.PawnMenu;
import view.BoardMenu;
import view.PopupItem;
import observer.*;
import model.BoardFile;
import model.BoardModel;
import resources.Pair;

public class BoardController implements MouseListener, ActionListener {
	private BoardView boardView;
	private BoardModel boardModel;
	private PawnMenu pawnMenu;
	private BoardMenu boardMenu;
	
	public BoardController(JFrame parent) {
		boardView = new BoardView(600,600,8,8);
		boardModel = new BoardModel();
		parent.getContentPane().add(boardView);
	}
	
	public void loadBoard(Object boardFile)
	{
		if (boardFile == null)
			boardModel.loadInitialBoard();
		else
			boardModel.loadBoard((BoardFile)boardFile);
	}
	
	public void setupBoard()
	{
		
		if (boardView == null || boardModel == null)
			return;
		
		//adiciona o controller como listener do mouse
		boardView.addMouseListener(this);
		//registra a view como observador do modelo
		registerObserver(boardView);
		//adiciona o menu popup da promoção do peão
		pawnMenu = new PawnMenu(this);
		//adiciona o menu popup do botao direito
		boardMenu = new BoardMenu(this);
		
		//adiciona as imagens das pecas na boardView
		String path = "src/images/"; //INF1636_Chess/src/images/"; 
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
		boardView.setPiecesImages(pieceImages); //preenche o data source da view
		boardModel.update(); //chama a update para desenhar as pecas pela 1a vez
	}
	
	public void registerObserver(BoardObserver o)
	{
		if(boardModel != null)
			boardModel.add(o);
	}
	
	private Pair translateCoords(int x, int y)
	{
		int coordX = x / boardView.getCellWidth();
		int coordY = y / boardView.getCellHeight();
		
		return new Pair(coordX, coordY);
	}
	
	public void activatePawnPromotion(int line, int column) 
	{
		pawnMenu.show(boardView, column*boardView.getCellWidth(), 
					  line*boardView.getCellHeight());
	}
	
	/* -------- Mouse Listener --------- */
	@Override
	public void mouseClicked(MouseEvent event) {}
	
	@Override public void mouseReleased(MouseEvent event) 
	{
		if(event.getButton() == 1) //left button
		{
			int x = event.getX();
			int y = event.getY();
			
			//Achar porque o x e y estão invertidos
			if(boardModel != null)
			{
				Pair coords = translateCoords(x,y);
				boardModel.click((int)coords.getSecond(), (int)coords.getFirst());
				boardModel.update();		
			}
		}
		else if (event.getButton() == 3) //right button
		{
			boardMenu.show(event);
		}
		
		else if (event.getButton() == 2) { // teste
			GameController.getInstance().endGame(1);
		}
	}
	
	@Override public void mouseEntered(MouseEvent arg0) {}
	@Override public void mouseExited(MouseEvent arg0) {}
	@Override public void mousePressed(MouseEvent arg0) {}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == PopupItem.SaveState.getRawValue())
		{
			GameController gameCtrl = GameController.getInstance();
			BoardFile boardState = boardModel.getBoardState();
			gameCtrl.fileCtrl.saveFile(boardState);
		}
		else
		{
			boardModel.promoteSelectedPiece(event.getActionCommand());
		}
	}
}
