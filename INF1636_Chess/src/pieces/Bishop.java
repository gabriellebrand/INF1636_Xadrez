package pieces;
import model.BoardModel;

public class Bishop extends Piece {
	
	public Bishop(int newx, int newy, int newcor)
	{
		super(newx, newy, newcor);
	}
	
	private boolean blockedPath(BoardModel board, int dx, int dy)
	{
		int i;
		int signaldx;
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
		if (dx==dy)// diagonal movement
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
		if (dx==0 && dy==0)
		{
			return false;
		}
		if (dx==dy || dx==-dy)
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
		Piece Piecedst = board.getPiece(newx, newy);
		if (Piecedst.getColor() == color) //tentando comer Piece de mesma cor
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