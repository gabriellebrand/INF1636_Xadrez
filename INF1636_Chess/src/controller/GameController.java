package controller;

public class GameController {
	private static GameController gameCtrl = null;
	private BoardController boardCtrl;
	
	private GameController()
	{}
	
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
