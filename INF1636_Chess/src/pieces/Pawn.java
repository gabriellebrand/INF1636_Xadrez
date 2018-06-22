package pieces;
import model.BoardModel;

public class Pawn extends Piece {
	
	public Pawn(int newx, int newy, int newcor, boolean newfirstmove)
	{
		super(newx, newy, newcor, newfirstmove);
		if (newcor == 0)
			id = "pawnW";
		else
			id = "pawnB";
	}
	
	public boolean testMove (BoardModel board, int newx, int newy) //Destrocar o x e y
	{
		int dx = newx-x;
		int dy = newy-y;
		
		Piece dstpiece = board.getPiece(newx, newy);
		if (dx==0 && dy==0) //no move
			return false;
		if (dstpiece != null && dstpiece.getColor() == color) //attack same color piece
			return false;
		
		if (color == 0) //White Pawn
		{
			if (dstpiece==null) //movement
			{
				if (dy==0 && dx==-1)
				{
					return true;
				}
				if (dy==0 && dx==-2 && firstmove && !board.validPiece(x-1, y))
				{
					return true;
				}
			}
			else if (Math.abs(dy)==1 && dx==-1 && dstpiece != null && dstpiece.getColor() != color) //attack
			{
				return true;
			}
		}
		else //Black Pawn
		{
			if (dstpiece==null) //move
			{
				if (dy==0 && dx==1)
				{
					return true;
				}
				if (dy==0 && dx==2 && firstmove && !board.validPiece(x+1, y))
				{
					return true;
				}
			}
			else if (Math.abs(dy)==1 && dx==1 && dstpiece != null && dstpiece.getColor() != color) //attack
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean canAttack (BoardModel board, int newx, int newy)
	{
		if (color == 0) //White Pawn
		{
			if (Math.abs(newy-y)==1 && newx-x==-1)
			{
				return true;
			}
		}
		else //Black Pawn
		{
			if (Math.abs(newy-y)==1 && newx-x==1)
			{
				return true;
			}
		}
		return false;
	}
}