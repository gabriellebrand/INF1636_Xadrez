package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

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
			gameCtrl = new GameController();
		
		return gameCtrl;
	}

	public BoardController getBoardController()
	{
		return boardCtrl;
	}
	
	public void startGame() 
	{
		mainWindow = new Window(600,600, null, "Xadrez");
		boardCtrl = new BoardController(mainWindow);
		boardCtrl.setupBoard();
	}
	
	public void endGame(int endGameStatus)
	{
		Object[] options = {"Iniciar nova partida",
		                    "Carregar partida"};
		String endGameMsg = "";
		switch (endGameStatus) {
		case -1:
			endGameMsg = "Ocorreu um empate! Escolha a opção desejada.";
			break;
		case 0:
			endGameMsg = "Check Mate! Vitória para o jogador azul.";
			break;
		case 1:
			endGameMsg = "Check Mate! Vitória para o jogador cinza.";
			break;
		default:
			endGameMsg = "Fim do jogo.";	 
		}
		int result = JOptionPane.showOptionDialog(mainWindow, 
											 endGameMsg, "Fim do jogo",
											 JOptionPane.YES_NO_CANCEL_OPTION,
											 JOptionPane.PLAIN_MESSAGE,
											 null, options, options[1]);
		Object boardFile = null;
		if (result == 1)
		{
			boardFile = fileCtrl.openFile();
			if (boardFile == null)
				System.out.println("Erro ao abrir arquivo");
			else
				System.out.println("Partida carregada");
		}
		boardCtrl.loadBoard(boardFile);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
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
