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
	
	public BoardModel() //standard board
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
		possibleMoves = new Boolean[lines][columns];
		updatePossibleMoves();
		
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
					
					System.out.println("piece= " + piece + " color= " + color);
					int player = color.equals("B") ? 1 : 0;
					System.out.println("player= " + player);
					switch (piece) {
					case "bishop":
						matrix[i][j] = new Bishop(i,j, player, false);
						break;
					case "king":
						matrix[i][j] = new King(i,j, player, false); //TODO recover actual firstmove state
						break;
					case "knight":
						matrix[i][j] = new Knight(i,j, player, false);
						break;
					case "pawn":
						matrix[i][j] = new Pawn(i,j, player, false); //TODO recover actual firstmove state
						break;
					case "queen":
						matrix[i][j] = new Queen(i,j, player, false);
						break;
					case "rook":
						matrix[i][j] = new Rook(i,j, player, false); //TODO recover actual firstmove state
						break;
					}
				}
			}
		//reseta matriz de posicoes e selecao
		
		selected = false;
		updatePossibleMoves();
		this.update();
	}
	
	public Piece getPiece(int x, int y)
	{
		return matrix[x][y];
	}
	
	public boolean validPiece(int x, int y)
	{	
		return matrix[x][y] != null;
	}
	
	public boolean attacked (int x, int y, int color)
	{
		for (int i=0; i<8; i++)
		{
			for (int j=0; j<8; j++)
			{
				if (matrix[i][j] != null && matrix[i][j].getColor() != color 
					&& matrix[i][j].canAttack(this, x, y))
				{
					return true;
				}
			}
		}
		return false;
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
			{
				for (int j=0; j<columns; j++)
				{
					possibleMoves[i][j] = matrix[selx][sely].testMove(this, i, j);
				}
			}
		}
	}
	
	public void testRoque (int x, int y)
	{
		if (matrix[selx][sely] == null)
			return;
		
		String id = matrix[selx][sely].getId();
		String roqueType = null;
		
		if (Objects.equals(id, "rookW") || Objects.equals(id, "rookB"))
		{
			Rook rook = (Rook) matrix[selx][sely];
			roqueType = rook.isRoque(this, x, y);
		}
		else if (Objects.equals(id, "kingW") || Objects.equals(id, "kingB"))
		{
			King king = (King) matrix[selx][sely];
			roqueType = king.isRoque(this, x, y);
		}
		
		if (roqueType == null)
			return;
		
		if (Objects.equals(roqueType, "WS"))
		{
			if (y == 6)//rook left to move
			{
				matrix[7][7].move(7,5);
				
				matrix[7][5] = matrix[7][7];
				matrix[7][7] = null;
			}
			else//king left to move
			{
				matrix[7][4].move(7,6);
				
				matrix[7][6] = matrix[7][4];
				matrix[7][4] = null;
			}
		}
		else if (Objects.equals(roqueType, "WL"))
		{
			if (y == 2)//rook left to move
			{
				matrix[7][0].move(7,3);
				
				matrix[7][3] = matrix[7][0];
				matrix[7][0] = null;
			}
			else//king left to move
			{
				matrix[7][4].move(7,2);
				
				matrix[7][2] = matrix[7][4];
				matrix[7][4] = null;
			}
		}
		else if (Objects.equals(roqueType, "BS"))
		{
			if (y == 6)//rook left to move
			{
				matrix[0][7].move(0,5);
				
				matrix[0][5] = matrix[0][7];
				matrix[0][7] = null;
			}
			else//king left to move
			{
				matrix[0][4].move(0,6);
				
				matrix[0][6] = matrix[0][4];
				matrix[0][4] = null;
			}
		}
		else //if (Objects.equals(roqueType, "BL"))
		{
			if (y == 2)//rook left to move
			{
				matrix[0][0].move(0,3);
				
				matrix[0][3] = matrix[0][0];
				matrix[0][0] = null;
			}
			else//king left to move
			{
				matrix[0][4].move(0,2);
				
				matrix[0][2] = matrix[0][4];
				matrix[0][4] = null;
			}
		}
		
	}
	
	public void click(int x, int y)
	{	
		
		if (selected)
		{
			if (matrix[x][y] != null &&
				matrix[x][y].getColor()==matrix[selx][sely].getColor()) // Reselect
			{
				
				System.out.printf("Reselecionado %d %d\n", x, y);
				selx = x;
				sely = y;
				//Atualiza a posicao da peca atualmente selecionada
				updatePossibleMoves();
				//Atualiza a matriz possibleMoves com os movimentos possiveis
				//	da peça. true = movimento possivel, false = impossivel
			}
			else if (matrix[selx][sely].testMove(this, x, y))//Move
			{
				testRoque (x, y);

				System.out.printf("Movido %d %d\n", x, y);
				
				matrix[selx][sely].move(x, y);
				matrix[x][y] = matrix[selx][sely];
				matrix[selx][sely] = null;
				selected = false;
				
				updatePossibleMoves();
				//Atualiza a matriz possibleMoves com os movimentos possiveis
				//	da peça. true = movimento possivel, false = impossivel
				
				testRoque(x, y);
				
				if (isPawnPromotion(x, y))
				{
					selx = x;
					sely = y;
					GameController.getInstance().getBoardController().
								   activatePawnPromotion(x, y);
				}
				currplayer = 1 - currplayer; //changes 0<->1
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
			if (matrix[x][y] == null)
			{
				System.out.printf("Nao tem peca em %d %d\n", x, y);
			}
			else
			{
				System.out.printf("Jogador errado para %d %d\n", x, y);
			}
		}
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





	
