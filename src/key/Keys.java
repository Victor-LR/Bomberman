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

//	public AgentAction getKeyAction(){
//		
//	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		switch(evt.getKeyChar()) {
		case 'z':
			haut = true;
			gauche = false;
			bas = false; 
			droite = false; 
			bool_to_action();
			
			break;
		case 'q':
			haut = false;
			gauche = true;
			bas = false; 
			droite = false; 
			bool_to_action();
			break;
		case 's':
			haut = false;
			gauche = false;
			bas = true; 
			droite = false; 
			bool_to_action();
			break;
		case 'd':
			haut = false;
			gauche = false;
			bas = false; 
			droite = true; 
			bool_to_action();
			break;
		case 'e':
			bombe = true;
			bool_to_action();
			break;
		default :
			haut = false;
			gauche = false;
			bas = false; 
			droite = false; 
			bool_to_action();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		haut = false;
		gauche = false;
		bas = false; 
		droite = false;
		bombe = false;
		bool_to_action();
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		// TODO Auto-generated method stub
//		switch(evt.getKeyChar()) {
//		case 'z':
//			setKaction(new AgentAction(Map.NORTH));
//			System.out.println("z");
//			break;
//		case 'q':
//			setKaction(new AgentAction(Map.WEST));
//			System.out.println("q");
//			break;
//		case 's':
//			setKaction(new AgentAction(Map.SOUTH));
//			System.out.println("s");
//			break;
//		case 'd':
//			setKaction(new AgentAction(Map.EAST));
//			System.out.println("d");
//			break;
//		case 'e':
//			setKaction(new AgentAction(Map.BOMB));
//			System.out.println("e");
//			break;
//		default :
//			setKaction(new AgentAction(Map.STOP));
//			break;
//		}
	}

	public AgentAction getKaction() {
		return Kaction;
	}

	public void bool_to_action() {
		if (haut) setKaction(new AgentAction(Map.NORTH));
		else if (gauche) setKaction(new AgentAction(Map.WEST));
		else if (bas) setKaction(new AgentAction(Map.SOUTH));
		else if (droite )setKaction(new AgentAction(Map.EAST));
		
		if (bombe) setKaction(new AgentAction(Map.BOMB));
		
	}
	
	public void setKaction(AgentAction action) {
		this.Kaction = action;
	}
}
