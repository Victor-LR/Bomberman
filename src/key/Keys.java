package key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import agents.AgentAction;
import map.Map;



public class Keys implements KeyListener{
	
	private AgentAction Kaction;
	
	private boolean haut;
	private boolean gauche;
	private boolean bas;
	private boolean droite;
	private boolean bombe;
	
	public Keys () {
		this.Kaction = new AgentAction(Map.STOP);

	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		switch(evt.getKeyChar()) {
		case 'z':
			haut = true;
			bool_to_action();
			System.out.println("ceci est la touche z");
			break;
		case 'q':
			gauche = true;
			bool_to_action();
			break;
		case 's':
			bas = true; 
			bool_to_action();
			break;
		case 'd':
			droite = true; 
			bool_to_action();
			break;
		case 'e':
			bombe = true;
			bool_to_action();
			break;
		default :
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent evt) {

		switch(evt.getKeyChar()) {
		case 'z':
			haut = false; 
			bool_to_action();
			break;
		case 'q':
			gauche = false;
			bool_to_action();
			break;
		case 's':
			bas = false; 
			bool_to_action();
			break;
		case 'd':
			droite = false; 
			bool_to_action();
			break;
		case 'e':
			bombe = false;
			bool_to_action();
			break;
		default :
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		switch(evt.getKeyChar()) {
		case 'z':
			haut = true; 
			bool_to_action();

			break;
		case 'q':
			gauche = true;
			bool_to_action();
			break;
		case 's':
			bas = true; 
			bool_to_action();
			break;
		case 'd':
			droite = true; 
			bool_to_action();
			break;
		case 'e':
			bombe = true;
			bool_to_action();
			break;
		default :

			break;
		}
	}

	public AgentAction getKaction() {
		return Kaction;
	}

	public void bool_to_action() {
		
		
		
		if (haut) setKaction(new AgentAction(Map.NORTH));
		if (gauche) setKaction(new AgentAction(Map.WEST));
		if (bas) setKaction(new AgentAction(Map.SOUTH));
		if (droite)setKaction(new AgentAction(Map.EAST));
		
		if (bombe) setKaction(new AgentAction(Map.BOMB));
		
		
		
		
	}
	
	public void setKaction(AgentAction action) {
		this.Kaction = action;
	}
}
