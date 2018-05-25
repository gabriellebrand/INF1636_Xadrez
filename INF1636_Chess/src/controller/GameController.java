package controller;

public class GameController {
	private static GameController gameCtrl = null;
	private BoardController boardController;
	
	private GameController()
	{
		gameCtrl = this;
		new BoardController();
	}
	
	public static GameController getInstance()
	{
		if (gameCtrl == null)
		{
			System.out.println("create game");
			new GameController();
		}
		
		return gameCtrl;
	}

	public BoardController getBoardController() {
		return boardController;
	}
	
	public void setBoardController(BoardController b) {
		boardController = b;
	}
}
