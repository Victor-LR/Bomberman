package game;

import java.util.ArrayList;

public abstract class Game implements Runnable, InterfaceGame {
	
	protected ArrayList<GameObserver> observers = new ArrayList<GameObserver>();
	
	private double temps = 200;
	protected int turn;
	protected int maxTurn = 10000;
	
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
	
	
	//Réalise les differents tours du jeu
	public void run(){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		}
		while (gameContinue() && isRunning){

			nextTurn();
			
			try {
				Thread.sleep((long) temps);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!gameContinue()){
			gameOver();	
		}
	}
	
	//Réalise le tours suivant
	
	public void nextTurn(){
		turn++;	
		notifierObservateurs();	
		taketurn();
	}
	
	public void setTemps(double temps) {
		this.temps = temps;
	}
	
	public int getTurn() {
		return this.turn;
	}
	
	//Arrete le jeu en mettent à faux isRunning
	
	public void stop(){
		isRunning = false;
	}
	
	public void restart(){
		init();
		launch();
	}
	
	//Verifie si le nombre de tours courant est inferieur au nombre de tours maximum permis par le jeu
	
	public boolean gameContinue(){
		

		if (turn < maxTurn){
			return true;
		} else {
			return false;
		}
		
		
	}
	
	//Obscure
	
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
