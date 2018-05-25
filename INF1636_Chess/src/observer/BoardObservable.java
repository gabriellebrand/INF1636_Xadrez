package observer;

public interface BoardObservable {
	void add(BoardObserver o);
	
	void remove(BoardObserver o);
	
	int get(int i);
}
