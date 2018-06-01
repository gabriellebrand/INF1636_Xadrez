package observer;
import resources.Pair;

public interface BoardObservable {
	public void add(BoardObserver o);
	
	public void remove(BoardObserver o);
	
	public String[][] getPiecesPosition();
	
	public Boolean[][] getHighlightedCells();
	
	public Pair getSelectedPosition();
}
