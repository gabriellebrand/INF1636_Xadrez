package model;

import pieces.*;
import resources.Pair;
import observer.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import controller.GameController;


public class BoardModel implements BoardObservable {
	public int currplayer = 0;
	
	private Piece matrix[][];
	Boolean[][] possibleMoves;
	private int lines = 8;
	private int columns = 8;
	private boolean selected = false;
	private int selx, sely;
	private List<BoardObserver> lst = new ArrayList<BoardObserver>();
	
	public BoardModel()
	{
		loadInitialBoard();
		this.update();
	}
	
	public void loadInitialBoard()
	{
		matrix = new Piece[lines][columns];
		
		matrix[0][0] = new Rook  (0,0,1, true); //black pieces
		matrix[0][1] = new Knight(0,1,1, true);
		matrix[0][2] = new Bishop(0,2,1, true);
		matrix[0][3] = new Queen (0,3,1, true);
		matrix[0][4] = new King  (0,4,1, true);
		matrix[0][5] = new Bishop(0,5,1, true);
		matrix[0][6] = new Knight(0,6,1, true);
		matrix[0][7] = new Rook  (0,7,1, true);
		for (int i=0; i<columns; i++)
			matrix[1][i] = new Pawn (1,i,1, true);
		
		matrix[7][0] = new Rook  (7,0,0, true); //white pieces
		matrix[7][1] = new Knight(7,1,0, true);
		matrix[7][2] = new Bishop(7,2,0, true);
		matrix[7][3] = new Queen (7,3,0, true);
		matrix[7][4] = new King  (7,4,0, true);
		matrix[7][5] = new Bishop(7,5,0, true);
		matrix[7][6] = new Knight(7,6,0, true);
		matrix[7][7] = new Rook  (7,7,0, true);
		for (int i=0; i<columns; i++)
			matrix[6][i] = new Pawn (6,i,0, true);

		//Inicializa matriz que armazena movimentos possiveis da peca
		currplayer = 0;
		possibleMoves = new Boolean[lines][columns];
		updatePossibleMoves();
		selected = false;
		this.update();
	}

	public void loadBoard(BoardFile boardFile)
	{

		//recupera estado do jogo
		this.currplayer = boardFile.currplayer;
		this.selected = boardFile.selected;
		this.selx = boardFile.selx;
		this.sely = boardFile.sely;
		
		matrix = new Piece[lines][columns];
		for (int i = 0; i < lines; i++)
			for (int j = 0; j < columns; j++)
			{
				if (boardFile.board[i][j] != null)
				{
					String piece = boardFile.board[i][j].substring(
								   0, boardFile.board[i][j].length()-1);
					String color = boardFile.board[i][j].substring(
								   boardFile.board[i][j].length()-1);
					boolean firstMove = boardFile.firstMove[i][j] == 1;
					System.out.println("piece= " + piece + " color= " + color);
					int player = color.equals("B") ? 1 : 0;
					System.out.println("player= " + player);
					switch (piece) {
					case "bishop":
						matrix[i][j] = new Bishop(i,j, player, firstMove);
						break;
					case "king":
						matrix[i][j] = new King(i,j, player, firstMove);
						break;
					case "knight":
						matrix[i][j] = new Knight(i,j, player, firstMove);
						break;
					case "pawn":
						matrix[i][j] = new Pawn(i,j, player, firstMove);
						break;
					case "queen":
						matrix[i][j] = new Queen(i,j, player, firstMove);
						break;
					case "rook":
						matrix[i][j] = new Rook(i,j, player, firstMove);
						break;
					}
				}
			}
		//reseta matriz de posicoes e selecao
		
		selected = false;
		updatePossibleMoves();
		this.update();
	}
	
	public int[][] getPiecesFirstMove()
	{
		int statusMatrix[][] = new int[lines][columns];
		
		for(int i=0; i<lines; i++)
		{
			for(int j=0; j<columns;j++)
			{
				//se existir uma peca na posicao ij, pega o status do firstMove, se nao, coloca -1
				int status = -1;
				if (matrix[i][j] != null)
					status = matrix[i][j].getFirstMove()? 1 : 0;
			
				statusMatrix[i][j] = status;
			}
		}
		return statusMatrix;
	}
	
	public Piece getPiece(int x, int y)
	{
		if (x<0 || y<0 || x>=lines || y>=columns)
			return null;
		return matrix[x][y];
	}
	
	public boolean validPiece(int x, int y)
	{	
		if (x<0 || y<0 || x>=lines || y>=columns)
			return false;
		return matrix[x][y] != null;
	}
	
	public boolean attacked (int x, int y, int color)
	{
		for (int i=0; i<columns; i++)
			for (int j=0; j<lines; j++)
			{
				if (matrix[i][j] != null && matrix[i][j].getColor() != color && matrix[i][j].canAttack(this, x, y))
					return true;
			}
		return false;
	}
	
	//returns which player is in check, -1 if no one is in check
	public int isInCheck()
	{
		int checkValue = -1;
		for (int i=0; i<columns; i++)
			for (int j=0; j<lines; j++)
			{
				if (matrix[i][j] != null)
					if  (Objects.equals(matrix[i][j].getId(), "kingW")) //Found White King
					{			
						if (attacked(i, j, 0))
							checkValue = 0;
					}
					else if (Objects.equals(matrix[i][j].getId(), "kingB")) //Found White King
					{
						if (attacked(i, j, 1))
							checkValue = 1;
					}
			}
		
		return checkValue;
	}
	
	private boolean testCheckAfterMove(int x1, int y1, int x2, int y2)
	{
		BoardModel afterMove = new BoardModel();

		for (int i=0; i<lines; i++) //copying board
			for (int j=0; j<columns; j++)
				afterMove.matrix[i][j]=matrix[i][j];
		
		afterMove.matrix[x2][y2]=afterMove.matrix[x1][y1]; //making move
		afterMove.matrix[x1][y1]=null;
		
		int checkState = afterMove.isInCheck();
		
		return checkState == matrix[x1][y1].getColor(); //checking for changes in checkstate
	}
	
	public void promoteSelectedPiece (String newPiece)
	{
		if (Objects.equals(newPiece, "Cavalo"))
			matrix[selx][sely] = new Knight (selx, sely, matrix[selx][sely].getColor(), false);
		else if (Objects.equals(newPiece, "Rainha"))
			matrix[selx][sely] = new Queen (selx, sely, matrix[selx][sely].getColor(), false);
		else if (Objects.equals(newPiece, "Torre"))
			matrix[selx][sely] = new Rook (selx, sely, matrix[selx][sely].getColor(), false);
		else if (Objects.equals(newPiece, "Bispo"))
			matrix[selx][sely] = new Bishop (selx, sely, matrix[selx][sely].getColor(), false);
		
		update();
	}
	
	private void updatePossibleMoves()
	{
		if (!selected)
		{
			for (int i=0;i<lines;i++)
				Arrays.fill(possibleMoves[i], false);
		}
		else
		{
			for (int i=0; i<lines; i++)
				for (int j=0; j<columns; j++)
					if (matrix[selx][sely].testMove(this, i, j))
						possibleMoves[i][j] = !testCheckAfterMove(selx, sely, i, j);
					else
						possibleMoves[i][j] = false;	
		}
	}
	
	private void winner(int player) {
		System.out.printf("winner %s\n", (player==0)? "white":"black");
		// TODO Auto-generated method stub
		GameController.getInstance().endGame(player);
		
	}

	private void draw() {
		System.out.println("draw");
		// TODO Auto-generated method stub
		GameController.getInstance().endGame(-1);
		
	}

	public boolean testRoque (int x, int y)
	{		
		String roqueType = null;
		
		if (matrix[selx][sely] != null && matrix[selx][sely] instanceof King)
		{
			King king = (King) matrix[selx][sely];
			roqueType = king.isRoque(this, x, y);
		}
		
		if (roqueType == null)
			return false;
		
		if (Objects.equals(roqueType, "WS"))
		{
			matrix[7][4].move(7,6);
			matrix[7][7].move(7,5);

			matrix[7][6] = matrix[7][4];
			matrix[7][5] = matrix[7][7];
			
			matrix[7][4] = null;
			matrix[7][7] = null;

		}
		else if (Objects.equals(roqueType, "WL"))
		{
			matrix[7][4].move(7,2);
			matrix[7][0].move(7,3);

			matrix[7][2] = matrix[7][4];
			matrix[7][3] = matrix[7][0];

			matrix[7][4] = null;
			matrix[7][0] = null;
		}
		else if (Objects.equals(roqueType, "BS"))
		{
			matrix[0][4].move(0,6);
			matrix[0][7].move(0,5);

			matrix[0][6] = matrix[0][4];
			matrix[0][5] = matrix[0][7];

			matrix[0][4] = null;
			matrix[0][7] = null;
		}
		else //if (Objects.equals(roqueType, "BL"))
		{
			matrix[0][4].move(0,2);
			matrix[0][0].move(0,3);

			matrix[0][2] = matrix[0][4];
			matrix[0][3] = matrix[0][0];

			matrix[0][4] = null;
			matrix[0][0] = null;
		}
		return true;		
	}
	
	public void click(int x, int y)
	{	
		
		if (selected)
		{
			if (matrix[x][y] != null &&
				matrix[x][y].getColor()==matrix[selx][sely].getColor()) // Reselect
			{

				if (testRoque (x, y))
				{
					System.out.println("Roque");
					
					selected = false;
					currplayer = 1 - currplayer; //changes 0<->1
					
					updatePossibleMoves();
					checkEndGame();
					
					return;
				}
				
				System.out.printf("Reselecionado %d %d\n", x, y);
				selx = x;
				sely = y;
				//Atualiza a posicao da peca atualmente selecionada
				updatePossibleMoves();
				//Atualiza a matriz possibleMoves com os movimentos possiveis
				//	da peça. true = movimento possivel, false = impossivel
				return;
			}
			else if (possibleMoves[x][y])//Move
			{				
				System.out.printf("Movido %d %d\n", x, y);
				
				matrix[selx][sely].move(x, y);
				matrix[x][y] = matrix[selx][sely];
				matrix[selx][sely] = null;
				selected = false;
				
				updatePossibleMoves();
				//Atualiza a matriz possibleMoves com os movimentos possiveis
				//	da peça. true = movimento possivel, false = impossivel
				
				if (isPawnPromotion(x, y))
				{
					selx = x;
					sely = y;
					GameController.getInstance().getBoardController().
								   activatePawnPromotion(x, y);
				}
				
				currplayer = 1 - currplayer; //changes 0<->1

				checkEndGame();
				return;
			}
			System.out.printf("Movimento impossivel para %d %d\n", x, y);
		}
		else  //if (!selected)
		{
			if (matrix[x][y] != null && matrix[x][y].getColor()==currplayer)
			{
				System.out.printf("Selecionado %d %d\n", x, y);
				selx = x;
				sely = y;
				selected = true;
				//Atualiza a posicao da peca atualmente selecionada
				
				updatePossibleMoves();
				//Atualiza a matriz possibleMoves com os movimentos possiveis
				//	da peça. true = movimento possivel, false = impossivel
			}
			else if (matrix[x][y] == null)
			{
				System.out.printf("Nao tem peca em %d %d\n", x, y);
			}
			else
			{
				System.out.printf("Jogador errado para %d %d\n", x, y);
			}
		}
	}
	
	private boolean checkEndGame() {
		int checkState = isInCheck();
		
		if (checkState >= 0)
			System.out.println("Check in " + (checkState==0? "white":"black"));
		for (int x1=0; x1<lines; x1++)
			for (int y1=0; y1<columns; y1++)
				if (matrix[x1][y1] != null && matrix[x1][y1].getColor() == currplayer) //pick every player piece
					for (int x2=0; x2<lines; x2++)
						for (int y2=0; y2<columns; y2++)
							if (matrix[x1][y1].testMove(this, x2, y2)) //test every possible move
							{
								if (checkState == currplayer)
								{
									if (!testCheckAfterMove(x1, y1, x2, y2))
										return false;
									
								}
								else //valid move and not in check
									return false;
							}
		
		if (checkState == currplayer)
			winner(1-currplayer);
		else
			draw();
		return true;
	}

	private boolean isPawnPromotion (int line, int column)
	{
		if(matrix[line][column] != null && matrix[line][column] instanceof Pawn)
		{
			if ((currplayer == 0 && line == 0) || 
				(currplayer == 1 && line == lines-1))
				return true;
		}
		return false;
	}
	
	public BoardFile getBoardState () 
	{
		BoardFile boardState = new BoardFile();
		
		boardState.board = this.getPiecesPosition();
		boardState.firstMove = this.getPiecesFirstMove();
		boardState.currplayer = this.currplayer;
		boardState.selected = this.selected;
		boardState.selx = this.selx;
		boardState.sely = this.sely;
		
		return boardState;
	}
	
	public void update()
	{
		ListIterator<BoardObserver> li = lst.listIterator();
		//notifica aos observers que o tabuleiro foi modificado
		while(li.hasNext())
			li.next().notify(this);
	}

	/************ OBSERVABLE METHODS ************/
	@Override
	public void add(BoardObserver o) {
		lst.add(o);
	}

	@Override
	public void remove(BoardObserver o) {
		lst.remove(o);
	}

	/*
	 * Description: Traduz a matriz de pecas para um padrao legivel para o
	 * 				observador (view).
	 * Returns: String[][] - retorna somente uma matriz com os ids (string) 
	 * 						 de cada peca. As casas ij que nao possuem peca 
	 * 					     recebem null.
	 */
	@Override
	public String[][] getPiecesPosition() {
		String idMatrix[][] = new String[lines][columns];
		
		for(int i=0; i<lines; i++)
		{
			for(int j=0; j<columns;j++)
			{
				//se existir uma peca na posicao ij, pega a id, se nao, coloca nulo
				String id = (matrix[i][j] != null) ? matrix[i][j].getId() : null;
				idMatrix[i][j] = id;
			}
		}
		return idMatrix;
	}

	@Override
	public Boolean[][] getHighlightedCells() {
		return possibleMoves;
	}

	@Override
	public Pair getSelectedPosition() {
		if (selected)
			return new Pair(selx, sely);
		else
			return null;
	}
}