package pieces;
import model.BoardModel;

public class Piece {
	int x, y, color;
	private String id;

	public Piece(int newx, int newy, int newcor, String id)
	{
		this.x=newx;
		this.y=newy;
		this.color = newcor;
		this.id = id;
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
	
	public boolean move(BoardModel board, int newx, int newy) //Possivelmente o método é igual para qualquer peca
	{
		return false;
	}
	
	public boolean testMove (BoardModel board, int newx, int newy)
	{
		return false;
	}
	
	public boolean attacks (BoardModel board, int newx, int newy)
	{
		return testMove(board, newx, newy);
	}
	
	public boolean draw()
	{
		return false;
	}
}
