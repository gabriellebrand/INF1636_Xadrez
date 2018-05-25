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
	
	private List<BoardObserver> lst = new ArrayList<BoardObserver>();
	
	public BoardModel() //standard board
	{
		matrix = new Piece[lines][columns];
		
		matrix[0][0] = new Rook  (0,0,0); //white pieces
		matrix[0][1] = new Knight(0,1,0);
		matrix[0][2] = new Bishop(0,2,0);
		matrix[0][3] = new Queen (0,3,0);
		matrix[0][4] = new King  (0,4,0);
		matrix[0][5] = new Bishop(0,5,0);
		matrix[0][6] = new Knight(0,6,0);
		matrix[0][7] = new Rook  (0,7,0);
		for (int i=0; i<8; i++)
		{
			matrix[1][i] = new Pawn (1,i,0);
		}
		matrix[7][0] = new Rook  (7,0,1); //black pieces
		matrix[7][1] = new Knight(7,1,1);
		matrix[7][2] = new Bishop(7,2,1);
		matrix[7][3] = new Queen (7,3,1);
		matrix[7][4] = new King  (7,4,1);
		matrix[7][5] = new Bishop(7,5,1);
		matrix[7][6] = new Knight(7,6,1);
		matrix[7][7] = new Rook  (7,7,1);
		for (int i=0; i<8; i++)
		{
			matrix[6][i] = new Pawn (6,i,1);
		}
		
		for (int i=2; i<6; i++) //fill rest (maybe not needed...)
		{
			for (int j=0; j<8; j++)
			{
				matrix[i][j]=null;
			}
		}
		
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
				if (matrix[i][j]!=null && matrix[i][j].getColor()!=color && matrix[i][j].attacks(this, x, y))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	private void update()
	{
		ListIterator<BoardObserver> li = lst.listIterator();
		
		while(li.hasNext())
			li.next().notify(this);
	}

	/************ OBSERVABLE METHODS ************/
	@Override
	public void add(BoardObserver o) {
		// TODO Auto-generated method stub
		lst.add(o);
		
	}

	@Override
	public void remove(BoardObserver o) {
		// TODO Auto-generated method stub
		lst.remove(o);
		
	}

	@Override
	public int get(int i) {
		// TODO Auto-generated method stub
		return 0;
	}
}
