package pieces;
import model.BoardModel;

public class King extends Piece {

	
	public King(int newx, int newy, int newcor)
	{
		super(newx, newy, newcor);
	}
	
	public boolean testMove (BoardModel board, int newx, int newy)
	{
		int dx = newx-x;
		int dy = newy-y;
		if (dx==0 && dy==0)
		{
			return false;
		}
		if (dx<-1 || dx>1)
		{
			return false;
		}
		if (dy<-1 || dy>1)
		{
			return false;
		}
		return true;
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
		if (board.attacked(newx, newy, color))//tentando se colocar em check-mate
		{
			return false;
		}
		if (!testMove(board, newx, newy))//Teste de movimento valido
		{
			return false;
		}
		x=newx;
		y=newy;
		return true;
	}

	//boolean draw() TODO
}
