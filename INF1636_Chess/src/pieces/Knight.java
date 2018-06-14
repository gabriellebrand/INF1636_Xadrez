package pieces;
import model.BoardModel;

public class Knight extends Piece {
	
	public Knight(int newx, int newy, int newcor, boolean newfirstmove)
	{
		super(newx, newy, newcor, newfirstmove);
		if (newcor == 0)
			id = "knightW";
		else
			id = "knightB";
	}
	
	
	public boolean testMove (BoardModel board, int newx, int newy)
	{
		int dx = newx-x;
		int dy = newy-y;
		
		Piece dstpiece = board.getPiece(newx, newy);
		if (dx==0 && dy==0) //no move
			return false;
		if (dstpiece != null && dstpiece.getColor() == color) //attack same color piece
			return false;
		
		if (Math.abs(dx)==1 && Math.abs(dy)==2)
		{
			return true;
		}
		if (Math.abs(dx)==2 && Math.abs(dy)==1)
		{
			return true;
		}
		return false;
	}
}
