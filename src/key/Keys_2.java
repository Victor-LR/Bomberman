package key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import agents.AgentAction;
import map.Map;

public class Keys_2 implements KeyListener{
	
	private AgentAction Kaction;

	private boolean haut;
	private boolean gauche;
	private boolean bas;
	private boolean droite;
	private boolean bombe;
	
	public Keys_2() {
		this.Kaction = new AgentAction(Map.STOP);

	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		switch(evt.getKeyCode()) {
		case KeyEvent.VK_UP:
			haut = true;
			bool_to_action();
			break;
		case KeyEvent.VK_LEFT:
			gauche = true;
			bool_to_action();
			break;
		case KeyEvent.VK_DOWN:
			bas = true;
			bool_to_action();
			break;
		case KeyEvent.VK_RIGHT:
			droite = true;
			bool_to_action();
			break;
		case KeyEvent.VK_CONTROL:
			bombe = true;
			bool_to_action();
			break;
		default :
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		switch(evt.getKeyCode()) {
		case KeyEvent.VK_UP:
			haut = false;
			bool_to_action();
			break;
		case KeyEvent.VK_LEFT:
			gauche = false;
			bool_to_action();
			break;
		case KeyEvent.VK_DOWN:
			bas = false;
			bool_to_action();
			break;
		case KeyEvent.VK_RIGHT:
			droite = false;
			bool_to_action();
			break;
		case KeyEvent.VK_CONTROL:
			bombe = false;
			bool_to_action();
			break;
		default :
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		switch(evt.getKeyCode()) {
		case KeyEvent.VK_UP:
			haut = true;
			bool_to_action();
			break;
		case KeyEvent.VK_LEFT:
			gauche = true;
			bool_to_action();
			break;
		case KeyEvent.VK_DOWN:
			bas = true;
			bool_to_action();
			break;
		case KeyEvent.VK_RIGHT:
			droite = true;
			bool_to_action();
			break;
		case KeyEvent.VK_CONTROL:
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
		
		if (!haut & !gauche & !bas & !droite & !bombe ) setKaction(new AgentAction(Map.STOP));
		
	}
	
	public void setKaction(AgentAction kaction) {
		Kaction = kaction;
	}
	
}
