package pieces;
import java.util.Objects;

import model.BoardModel;

public class Rook extends Piece {
	
	public Rook(int newx, int newy, int newcor, boolean newfirstmove)
	{
		super(newx, newy, newcor, newfirstmove);
		if (newcor == 0)
			id = "rookW";
		else
			id = "rookB";
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
	
	public String isRoque (BoardModel board, int newx, int newy)
	{
		int dx = newx-x;
		int dy = newy-y;

		if (color == 0 && firstmove) //White Rook
		{
			Piece king = board.getPiece(7, 4);
			if (x == 7 && y == 7 && dx == 0 && dy == -2)//short roque
			{
				if (!board.validPiece(7, 5) && !board.validPiece(7, 6)
						&& king !=null && Objects.equals(king.id, "kingW") && king.firstmove)
				{
					return "WS";
				}
			}
			else if (x == 7 && y == 0 && dx == 0 && dy == 3)//long roque
			{
				
				if (!board.validPiece(7, 3) && !board.validPiece(7, 2) && !board.validPiece(7, 1)
						&& king !=null && Objects.equals(king.id, "kingW") && king.firstmove)
				{
					return "WL";
				}
			}
		}
		else if (color == 1 && firstmove) //Black Rook
		{
			Piece king = board.getPiece(0, 4);
			if (x == 0 && y == 7 && dx == 0 && dy == -2)//short roque
			{
				if (!board.validPiece(0, 5) && !board.validPiece(0, 6)
						&& king !=null && Objects.equals(king.id, "kingB") && king.firstmove)
				{
					return "BS";
				}
			}
			else if (x == 0 && y == 0 && dx == 0 && dy == 3)//long roque
			{
				
				if (!board.validPiece(0, 3) && !board.validPiece(0, 2) && !board.validPiece(0, 1)
						&& king !=null && Objects.equals(king.id, "kingB") && king.firstmove)
				{
					return "BL";
				}
			}
		}
		return null;
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

		if (isRoque(board, newx, newy) != null)
		{
			return true;
		}
		
		if (dx==0 || dy==0)
		{
			return !blockedPath(board, dx, dy);
		}
		return false;
	}
}
