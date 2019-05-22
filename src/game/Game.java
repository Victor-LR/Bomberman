package game;

import java.util.ArrayList;

public abstract class Game implements Runnable, InterfaceGame {
	
	protected ArrayList<GameObserver> observers = new ArrayList<GameObserver>();
	
	private double temps = 150;

	protected int turn;
	protected int maxTurn = 1000;
	
	Thread thread;
	
	private boolean isRunning;
	
	public Game (){
		
	}
	
	//initialise le jeu
	
	public void init(){	
		turn = 0;
		isRunning = true;
		initializeGame();
		notifierObservateurs();	
	}
	
	//Réalise les different changement par étapes du jeu
	
	public void step(){
		
		if(gameContinue()){
			nextTurn();
		} else{
			gameOver();	
		}
	}
	
	//Lance le jeu
		
	public void launch(){
		
		thread = new Thread(this);	
		thread.start();
		isRunning = true;

	}
	
	public void new_thread(){
		thread = new Thread(this);
	}
	
	public Thread getThread() {
		return thread;
	}
	
	//Réalise les differents tours du jeu
	
	public void run(){
		while (gameContinue() && isRunning){
			
			nextTurn();
			
			try {
				Thread.sleep((long) temps);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			
			}
		}
		
		if(!gameContinue()){
			gameOver();	
		}
	}
	
	//Réalise le tour suivant
	
	public void nextTurn(){
		turn++;	
		notifierObservateurs();	
		taketurn();
	}
	
	// getteur et setteur sur le temps entre chaque tours
	public double getTemps() {
		return temps;
	}
	
	public void setTemps(double temps) {
		this.temps = temps;
	}
	
	//informations sur le tour actuel et le maximum de tour possible
	public int getTurn() {
		return this.turn;
	}
	
	public int getMaxTurn() {
		return maxTurn;
	}
	
	//Arrete le jeu en mettant à faux isRunning
	
	public void stop(){
		isRunning = false;
	}
	
	
	//Verifie si le nombre de tours courant est inferieur au nombre de tours maximum permis par le jeu
	
	public boolean gameContinue(){
		
		if (turn < maxTurn){
			return true;
		} else {
			return false;
		}
	}
	
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	
	//design paterns
	
	public void addObserver(GameObserver o) {
		observers.add(o);	
	}

	public void removeObserver(GameObserver o) {
		observers.remove(o);
	}
	
	public void notifierObservateurs() {	
		for(int i = 0; i < observers.size(); i++) {
			GameObserver observateur = observers.get(i);
			observateur.update();
		}
	}


	protected abstract void initializeGame();

	protected abstract void taketurn();
	protected abstract void gameOver();

}
