package graphics;

/*import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;*/

import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;



//import map.Map;
import game.BombermanGame;
import graphics.paint_bomberman;
import graphics.paint_score;
import key.Keys;

public class Cadre_Jeu extends JFrame implements KeyListener { 
	
	private static final long serialVersionUID = 1L;
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Point centerPoint = ge.getCenterPoint();
    private JPanel p_bm;
    private paint_score p_sc;
	
    //Création de la fenêtre de jeu
	public Cadre_Jeu(BombermanGame BbmG){
		
		    this.setSize(BbmG.etatJeu.getMap().getSizeX()*50, (BbmG.etatJeu.getMap().getSizeY()*50)+45);
		    this.setTitle("Jeu Bomberman");
		    this.setLocationRelativeTo(null);

		    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         
	        
	        this.setLayout(new BorderLayout());
	        
	        p_sc = new paint_score(BbmG);
	        this.add("North",p_sc.panel);
	        
	        p_bm = new paint_bomberman(BbmG);
	        this.add("Center",p_bm);
	       
	               
	       this.addKeyListener(BbmG.etatJeu.key_action);
	     //   test.setFocusable(true);
	        this.addWindowListener(new WindowAdapter() {
	        	public void windowOpened(WindowEvent e) { 
	        	requestFocus();	
	        	}
	        	});
	        
	        //this.addKeyListener(this);
	        this.setVisible(true);

	        
	        this.getContentPane();
	        
	}
	
	 @Override
     public void keyTyped(KeyEvent ke) {
         System.out.println(" touche "+ke.getKeyCode());
     }

     @Override
     public void keyPressed(KeyEvent ke) {
         System.out.println("touche "+ke.getKeyCode());
     }

     @Override
     public void keyReleased(KeyEvent ke) {
         System.out.println("touche"+ke.getKeyCode());
     }
	
	
	//JButton choixRun;
}
