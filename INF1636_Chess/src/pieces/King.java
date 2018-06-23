package pieces;
import java.util.Objects;

import model.BoardModel;

public class King extends Piece {
	
	public King(int newx, int newy, int newcor, boolean newfirstmove)
	{
		super(newx, newy, newcor, newfirstmove);
		if (newcor == 0)
			id = "kingW";
		else
			id = "kingB";
	}
	
	public String isRoque (BoardModel board, int newx, int newy)
	{
		if (color == 0 && x == 7 && y == 4 && firstmove) //White King
		{
			if (newx == 7 && newy == 7) //short roque
			{
				Piece rook = board.getPiece(7, 7);
				if (!board.validPiece(7, 5) && !board.validPiece(7, 6)
						&& rook !=null && Objects.equals(rook.id, "rookW") && rook.firstmove)
				{
					return "WS";
				}
			}
			else if (newx == 7 && newy == 0)//long roque
			{
				Piece rook = board.getPiece(7, 0);
				if (!board.validPiece(7, 3) && !board.validPiece(7, 2) && !board.validPiece(7, 1)
						&& rook !=null && Objects.equals(rook.id, "rookW") && rook.firstmove)
				{
					return "WL";
				}
			}
		}
		else if (color == 1 && x == 0 && y == 4 && firstmove) //Black King
		{
			if (newx == 0 && newy == 7) //short roque
			{
				Piece rook = board.getPiece(0, 7);
				if (!board.validPiece(0, 5) && !board.validPiece(0, 6)
						&& rook !=null && Objects.equals(rook.id, "rookB") && rook.firstmove)
				{
					return "BS";
				}
			}
			else if (newx == 0 && newy == 0)//long roque
			{
				Piece rook = board.getPiece(0, 0);
				if (!board.validPiece(0, 3) && !board.validPiece(0, 2) && !board.validPiece(0, 1)
						&& rook !=null && Objects.equals(rook.id, "rookB") && rook.firstmove)
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
		
		if (Math.abs(dx) <= 1 &&  Math.abs(dy) <= 1)
		{
			if (kingBeside(board, newx, newy))
				return false;
			else
				return !board.attacked(newx, newy, color);
		}
//		if (isRoque(board, newx, newy) != null && board.isInCheck()!=color)
//		{	
//			return !board.attacked(newx, newy, color);
//		}
		return false;
	}

	private boolean kingBeside(BoardModel board, int newx, int newy) {
		for (int i=-1; i<=1; i++)
			for (int j=-1; j<=1; j++)
			{
				if (board.validPiece(newx+i, newy+j))
				{
					Piece otherKing = board.getPiece(newx+i, newy+j);
					if (otherKing instanceof King && !(newx+i==x && newy+j==y))
					{
						System.out.println("king beside " + newx + newy + " at " + (newx+i) + (newy+j) + " (" + i + j + ")");
						return true;
					}
				}
			}
		return false;
	}
}