package game;

public interface InterfaceGame {
	void addObserver(GameObserver o);
	void removeObserver(GameObserver o);
	void notifierObservateurs();
}
