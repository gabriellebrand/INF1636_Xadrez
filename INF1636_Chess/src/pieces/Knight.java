package pieces;
import model.BoardModel;

public class Knight extends Piece {
	
	public Knight(int newx, int newy, int newcor, String id)
	{
		super(newx, newy, newcor, id);
	}
	
	
	public boolean testMove (BoardModel board, int newx, int newy)
	{
		int dx = newx-x;
		int dy = newy-y;
		if (dx==0 && dy==0)
		{
			return false;
		}
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
