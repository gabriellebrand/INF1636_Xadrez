package observer;

public interface BoardObservable {
	public void add(BoardObserver o);
	
	public void remove(BoardObserver o);
	
	public String[][] get();
}
