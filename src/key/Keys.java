package key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Component;

import agents.AgentAction;
import agents.Agent_Bomberman;
import map.GameState;
import map.Map;

public class Keys implements KeyListener{
	
	//private Agent_Bomberman bomberman;
	//private GameState game;
	private AgentAction Kaction;
	
//	public Keys () {
//
//	}
	
	public Keys () {
		//this.bomberman = bomberman;
		//this.game = game;
		this.Kaction = new AgentAction(Map.STOP);
		//addKeyListener(this);
	}

//	public AgentAction getKeyAction(){
//		
//	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		switch(evt.getKeyChar()) {
		case 'z':
			setKaction(new AgentAction(Map.NORTH));
			System.out.println("z");
			break;
		case 'q':
			setKaction(new AgentAction(Map.WEST));
			System.out.println("q");
			break;
		case 's':
			setKaction(new AgentAction(Map.SOUTH));
			System.out.println("s");
			break;
		case 'd':
			setKaction(new AgentAction(Map.EAST));
			System.out.println("d");
			break;
		case 'e':
			setKaction(new AgentAction(Map.BOMB));
			System.out.println("e");
			break;
		default :
			setKaction(new AgentAction(Map.STOP));
			break;
		}
		//if (evt.getKeyChar() == 'r')
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		setKaction(new AgentAction(Map.STOP));
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		// TODO Auto-generated method stub
		switch(evt.getKeyChar()) {
		case 'z':
			setKaction(new AgentAction(Map.NORTH));
			System.out.println("z");
			break;
		case 'q':
			setKaction(new AgentAction(Map.WEST));
			System.out.println("q");
			break;
		case 's':
			setKaction(new AgentAction(Map.SOUTH));
			System.out.println("s");
			break;
		case 'd':
			setKaction(new AgentAction(Map.EAST));
			System.out.println("d");
			break;
		case 'e':
			setKaction(new AgentAction(Map.BOMB));
			System.out.println("e");
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
