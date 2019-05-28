package learning.perceptron;
      
import java.util.ArrayList;
import java.util.Collections;


import learning.Elt;
import learning.Reward;
import learning.RewardTools;
import learning.Sensor;
import map.GameState;
import learning.perceptron.*;

public class RechercheAleatoire {
	
	private int n, m;
	private double variance;
	private GameState state;
	private Reward r;
	private ArrayList<Elt> lis;
	private Sensor s;
	
	
	
	public RechercheAleatoire(int n, int m,double variance,GameState state,Reward r,Sensor s){
		
		this.n=n;
		this.m=m;
		this.variance=variance;
		this.state=state;
		this.r=r;
		this.s=s;
		lis=new ArrayList<>();
		for(int i=0;i<n;i++){
			Elt e=new Elt();
			e.perceptron=new Perceptron(0.001,s.size());
			lis.add(e);
		}		
		
	}

	
	public void evoluer() {
		for(int i=0;i<n;i++){
			lis.get(i).score=RewardTools.getAverageReward(state, new PerceptronAgent(state.getBombermans().get(0),s,lis.get(i).perceptron), r, 200, 100);
		}
		Collections.sort(lis);

		for (int i = m; i < n; i++) {
			Elt e = new Elt();
			int y=(int)Math.random()*m;
			e.perceptron = lis.get(y).perceptron;
			e.perceptron.bruiter(variance);
			e.score = RewardTools.getAverageReward(state, new PerceptronAgent(state.getBombermans().get(0),s, e.perceptron), r, 200, 100);
			lis.set(i, e);
		}
		Collections.sort(lis);
	}
			
	
	public ArrayList<Elt> getLis() {
		return lis;
	}
	
	
	public Perceptron getMeilleur() {
		evoluer();
		Elt e = lis.get(0);
		return e.perceptron;
	}
	

	
}