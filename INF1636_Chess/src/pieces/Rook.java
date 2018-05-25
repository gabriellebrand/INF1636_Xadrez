package pieces;
import model.BoardModel;

public class Rook extends Piece {
	
	public Rook(int newx, int newy, int newcor, String id)
	{
		super(newx, newy, newcor, id);
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
		else// if (dy==0)//horizontal movement
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
	}
	
	public boolean testMove (BoardModel board, int newx, int newy)
	{
		int dx = newx-x;
		int dy = newy-y;
		if (dx==0 && dy==0)
		{
			return false;
		}
		if (dx==0 || dy==0)
		{
			return !blockedPath(board, dx, dy);
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
		if (dstpiece!=null && dstpiece.getColor()==color) //tentando comer Piece de mesma cor
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
