package learning;

import java.io.Serializable;

import agents.AgentAction;
import learning.perceptron.SparseVector;

public class Quadruplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SparseVector etat, atteint;
	private AgentAction action;
	private double r_obtenu;
	
	public Quadruplet(SparseVector etat,AgentAction action,SparseVector atteint,double reward){
		this.etat=etat;
		this.action=action;
		this.atteint=atteint;
		this.r_obtenu=reward;
	}



	public SparseVector getEtat() {
		return etat;
	}

	public SparseVector getAtteint() {
		return atteint;
	}

	public AgentAction getAction() {
		return action;
	}

	public double getR_obtenu() {
		return r_obtenu;
	}

}
