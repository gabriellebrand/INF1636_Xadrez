package controller;

public class GameController {
	private static GameController gameCtrl = null;
	private BoardController boardController;
	
	private GameController()
	{
		boardController = new BoardController();
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
	
	public BoardController getBoardController() {
		return boardController;
	}
}
