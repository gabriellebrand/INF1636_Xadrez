package view;
import resources.Pair;

public interface BoardViewDataSource {
	public int numberOfPieces ();
	
	public Pair getPieceImage(int i);
}
