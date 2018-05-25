package model;

import pieces.*;
import observer.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class BoardModel implements BoardObservable {
	private Piece matrix[][];
	private int lines = 8;
	private int columns = 8;
	private boolean selected = false;
	private int selx, sely;
	public int currplayer = 0;
	
	private List<BoardObserver> lst = new ArrayList<BoardObserver>();
	
	public BoardModel() //standard board
	{
		matrix = new Piece[lines][columns];
		
		matrix[0][0] = new Rook  (0,0,0, "rookW"); //white pieces
		matrix[0][1] = new Knight(0,1,0, "knightW");
		matrix[0][2] = new Bishop(0,2,0, "bishopW");
		matrix[0][3] = new Queen (0,3,0, "queenW");
		matrix[0][4] = new King  (0,4,0, "kingW");
		matrix[0][5] = new Bishop(0,5,0, "bishopW");
		matrix[0][6] = new Knight(0,6,0, "knightW");
		matrix[0][7] = new Rook  (0,7,0, "rookW");
		for (int i=0; i<columns; i++)
			matrix[1][i] = new Pawn (1,i,0, "pawnW");
		
		matrix[7][0] = new Rook  (7,0,1,"rookB"); //black pieces
		matrix[7][1] = new Knight(7,1,1, "knightB");
		matrix[7][2] = new Bishop(7,2,1, "bishopB");
		matrix[7][3] = new Queen (7,3,1, "queenB");
		matrix[7][4] = new King  (7,4,1, "kingB");
		matrix[7][5] = new Bishop(7,5,1, "bishopB");
		matrix[7][6] = new Knight(7,6,1, "knightB");
		matrix[7][7] = new Rook  (7,7,1, "rookB");
		for (int i=0; i<columns; i++)
			matrix[6][i] = new Pawn (6,i,1, "pawnB");

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
				if (matrix[i][j] != null && matrix[i][j].getColor() != color && matrix[i][j].attacks(this, x, y))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean click(int x, int y)
	{	
		if (selected)
		{
			if (matrix[selx][sely].move(this, x, y))
			{
				matrix[x][y] = matrix[selx][sely];
				matrix[selx][sely] = null;
				selected = false;
				currplayer = 1 - currplayer; //changes 0<->1
				return true;
			}
			return false;
		}
		else
		{
			if (matrix[x][y] == null && matrix[x][y].getColor()==currplayer)
			{
				selx = x;
				sely = y;
				selected = true;
				return true;
			}
			return false;
		}
		
		//TODO: chamar o metodo update()
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
	public String[][] get() {
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
}
