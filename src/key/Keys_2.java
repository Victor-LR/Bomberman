package key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import agents.AgentAction;
import map.Map;

public class Keys_2 implements KeyListener{
	
	private AgentAction Kaction;

	
	public Keys_2() {
		this.Kaction = new AgentAction(Map.STOP);

	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		switch(evt.getKeyCode()) {
		case KeyEvent.VK_UP:
			setKaction(new AgentAction(Map.NORTH));
			break;
		case KeyEvent.VK_LEFT:
			setKaction(new AgentAction(Map.WEST));
			break;
		case KeyEvent.VK_DOWN:
			setKaction(new AgentAction(Map.SOUTH));
			break;
		case KeyEvent.VK_RIGHT:
			setKaction(new AgentAction(Map.EAST));
			break;
		case KeyEvent.VK_CONTROL:
			setKaction(new AgentAction(Map.BOMB));
			break;
		default :
			setKaction(new AgentAction(Map.STOP));
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		// TODO Auto-generated method stub
		switch(evt.getKeyCode()) {
		case KeyEvent.VK_UP:
			setKaction(new AgentAction(Map.NORTH));
			System.out.println("up");
			break;
		case KeyEvent.VK_LEFT:
			setKaction(new AgentAction(Map.WEST));
			System.out.println("left");
			break;
		case KeyEvent.VK_DOWN:
			setKaction(new AgentAction(Map.SOUTH));
			System.out.println("down");
			break;
		case KeyEvent.VK_RIGHT:
			setKaction(new AgentAction(Map.EAST));
			System.out.println("right");
			break;
		case KeyEvent.VK_CONTROL:
			setKaction(new AgentAction(Map.BOMB));
			System.out.println("ctrl");
			break;
		default :
			setKaction(new AgentAction(Map.STOP));
			break;
		}
	}

	public AgentAction getKaction() {
		return Kaction;
	}

	public void setKaction(AgentAction kaction) {
		Kaction = kaction;
	}
	
}
