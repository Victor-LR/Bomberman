package key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import agents.AgentAction;
import map.Map;

public class Keys_2 implements KeyListener{
	
	private AgentAction Kaction;

	private static int Deplacement; 
	
	public Keys_2() {
		this.Kaction = new AgentAction(Map.STOP);

	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		switch(evt.getKeyCode()) {
		case KeyEvent.VK_UP:
			Deplacement = 1;
			bool_to_action();
			break;
		case KeyEvent.VK_LEFT:
			Deplacement = 2;
			bool_to_action();
			break;
		case KeyEvent.VK_DOWN:
			Deplacement = 3;
			bool_to_action();
			break;
		case KeyEvent.VK_RIGHT:
			Deplacement = 4;
			bool_to_action();
			break;
		case KeyEvent.VK_CONTROL:
			Deplacement = 5;
			bool_to_action();
			break;
		default :
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent evt) {
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		switch(evt.getKeyCode()) {
		case KeyEvent.VK_UP:
			Deplacement = 1;
			bool_to_action();
			break;
		case KeyEvent.VK_LEFT:
			Deplacement = 2;
			bool_to_action();
			break;
		case KeyEvent.VK_DOWN:
			Deplacement = 3;
			bool_to_action();
			break;
		case KeyEvent.VK_RIGHT:
			Deplacement = 4;
			bool_to_action();
			break;
		case KeyEvent.VK_CONTROL:
			Deplacement = 5;
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
		switch(Deplacement){
		case 0:
			setKaction(new AgentAction(Map.STOP));
			break;
		case 1:
			setKaction(new AgentAction(Map.NORTH));
			break;
		case 2:
			setKaction(new AgentAction(Map.WEST));
			break;
		case 3:
			setKaction(new AgentAction(Map.SOUTH));
			break;
		case 4:
			setKaction(new AgentAction(Map.EAST));
			break;
		case 5:
			setKaction(new AgentAction(Map.BOMB));
			break;
		default:
			break;
		}
	}
	
	public void setKaction(AgentAction kaction) {
		Kaction = kaction;
	}
	
}
