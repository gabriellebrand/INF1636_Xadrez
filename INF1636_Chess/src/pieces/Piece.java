package pieces;
import model.BoardModel;

public class Piece {
	protected int x, y, color;
	protected String id = null;
	protected boolean firstmove;

	protected Piece(int newx, int newy, int newcor, boolean newfirstmove)
	{
		this.x=newx;
		this.y=newy;
		this.color = newcor;
		this.firstmove = newfirstmove;
	}
	
	public int getx()
	{
		return x;
	}
	
	public int gety()
	{
		return y;
	}
	
	public int getColor()
	{
		return color;
	}
	
	public String getId()
	{
		return id;
	}
	
	public boolean getFirstMove()
	{
		return firstmove;
	}
	
	public void move(int newx, int newy)
	{
		x=newx;
		y=newy;
		firstmove = false;
	}
	
	public boolean testMove (BoardModel board, int newx, int newy)
	{
		return false;
	}
	
	public boolean canAttack (BoardModel board, int newx, int newy)
	{
		return testMove(board, newx, newy);
	}
	
}
