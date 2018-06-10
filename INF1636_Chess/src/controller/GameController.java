package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Window;

public class GameController implements ActionListener {
	private static GameController gameCtrl = null;
	private BoardController boardCtrl;
	public FileController fileCtrl;
	private Window mainWindow;

	private GameController()
	{
		fileCtrl = new FileController();
	}
	
	public static GameController getInstance()
	{
		if (gameCtrl == null)
		{
			System.out.println("create game");
			gameCtrl = new GameController();
		}
		
		return gameCtrl;
	}

	public BoardController getBoardController()
	{
		return boardCtrl;
	}
	
	public void startGame() 
	{
		mainWindow = new Window(800,800, null, "Xadrez");
		boardCtrl = new BoardController(mainWindow);
		boardCtrl.setupBoard();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		System.out.println(event.getActionCommand());
		Object boardFile = fileCtrl.openFile();
		
		if (boardFile == null)
			System.out.println("Erro ao abrir arquivo");
		else
		{
			System.out.println("Partida carregada");
			boardCtrl.loadBoard(boardFile);
		}
	}
}
