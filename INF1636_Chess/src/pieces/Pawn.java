package pieces;
import model.BoardModel;

public class Pawn extends Piece {
	
	boolean firstmove;
	
	public Pawn(int newx, int newy, int newcor, String id)
	{
		super(newx, newy, newcor, id);
		firstmove = true;
	}
	
	public boolean testMove (BoardModel board, int newx, int newy) //Destrocar o x e y
	{
		int dx = newx-x;
		int dy = newy-y;
		if (dx==0 && dy==0)
		{
			return false;
		}
		Piece dstpiece = board.getPiece(newx, newy);
		if (color == 0) //White Pawn
		{
			if (dstpiece==null) //movement
			{
				if (dy==0 && dx==-1)
				{
					firstmove = false;
					return true;
				}
				if (dy==0 && dx==-2 && firstmove)
				{
					firstmove = false;
					return true;
				}
			}
			else if (Math.abs(dy)==1 && dx==-1 && dstpiece.getColor() != color) //attack
			{
				firstmove = false;
				return true;
			}
		}
		else //Black Pawn
		{
			if (dstpiece==null) //move
			{
				if (dy==0 && dx==1)
				{
					firstmove = false;
					return true;
				}
				if (dy==0 && dx==2 && firstmove)
				{
					firstmove = false;
					return true;
				}
			}
			else if (Math.abs(dy)==1 && dx==1 && dstpiece.getColor() != color) //attack
			{
				firstmove = false;
				return true;
			}
		}
		return false;
	}
	
	public boolean attacks (BoardModel board, int newx, int newy)
	{
		if (color == 0) //White Pawn
		{
			if (Math.abs(newx-x)==1 && newy-y==1)
			{
				return true;
			}
		}
		else //Black Pawn
		{
			if (Math.abs(newx-x)==1 && newy-y==-1)
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean move(BoardModel board, int newx, int newy)
	{
		if (newx<0 || newx>7 || newy<0 ||newy>7) //movimento para fora do boarduleiro
		{
			return false;
		}
		Piece dstpiece = board.getPiece(newx, newy);
		if (dstpiece!=null && dstpiece.getColor()==color) //tentando comer peca de mesma cor
		{
			return false;
		}
		if (!testMove(board, newx, newy)) //teste de movimento valido
		{
			return false;
		}
		
		x=newx;
		y=newy;
		return true;
	}

	//boolean draw() TODO
}
