package pieces;
import model.BoardModel;

public class Queen extends Piece {
	
	public Queen(int newx, int newy, int newcor, boolean newfirstmove)
	{
		super(newx, newy, newcor, newfirstmove);
		if (newcor == 0)
			id = "queenW";
		else
			id = "queenB";
	}
	
	private boolean blockedPath(BoardModel board, int dx, int dy)
	{
		int i;
		int signaldx, signaldy;
		if (dx<0)
		{
			signaldx=-1;
		}
		else if (dx>0)
		{
			signaldx=1;
		}
		else
		{
			signaldx=0;
		}
		if (dy<0)
		{
			signaldy=-1;
		}
		else if (dy>0)
		{
			signaldy=1;
		}
		else
		{
			signaldy=0;
		}
		if (dx==0)//vertical movement
		{
			for (i=signaldy; Math.abs(i)<Math.abs(dy); i+=signaldy)// teste equivalente a i*signaldy< dy*signaldy, mas mais legivel
			{
				if (board.validPiece(x, y+i))
				{
					return true;
				}
			}
			return false;
		}
		else if (dy==0)//horizontal movement
		{
			for (i=signaldx; Math.abs(i)<Math.abs(dx); i+=signaldx)
			{
				if (board.validPiece(x+i, y))
				{
					return true;
				}
			}
			return false;
		}
		else if (dx==dy)// diagonal movement
		{
			for (i=signaldx; Math.abs(i)<Math.abs(dx); i+=signaldx)
			{
				if (board.validPiece(x+i, y+i))
				{
					return true;
				}
			}
			return false;
		}
		else//if (dx==-dy)// diagonal movement
		{
			for (i=signaldx; Math.abs(i)<Math.abs(dx); i+=signaldx)
			{
				if (board.validPiece(x+i, y-i))
				{
					return true;
				}
			}
			return false;
		}
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
		
		if (dx==0 || dy==0)
		{
			return !blockedPath(board, dx, dy);
		}
		if (dx==dy || dx==-dy)
		{
			return !blockedPath(board, dx, dy);
		}
		return false;
	}
}
