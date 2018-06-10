package controller;

public class GameController {
	private static GameController gameCtrl = null;
	private BoardController boardCtrl;
	public FileController fileCtrl;
	
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
		boardCtrl = new BoardController();
		boardCtrl.setupBoard();
	}
}
