package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;


import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

import javax.swing.JPanel;

import agents.Agent;
import agents.Agent_Bomberman;
import objets.Objet;
import objets.ObjetType;
import objets.Objet_Bomb;

import game.BombermanGame;
import map.Map;

//Création graphique de la carte et des agents
public class Review extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Color wallColor=Color.GRAY;
	protected Color brokable_walls_Color=Color.lightGray;
	protected Color ground_Color= new Color(50,175,50);
	
	BombermanGame BbmG;
	private int taille_x;
	private int taille_y;
	
	float[] contraste = { 0, 0, 0, 1.0f };
	
	public Review(BombermanGame BbmG){
		
		this.BbmG = BbmG;
		
		//Taille de la carte
	   taille_x= this.BbmG.etatJeu.getMap().getSizeX();
	   taille_y= this.BbmG.etatJeu.getMap().getSizeY();
	   
		
	}
	
	public void paint(Graphics g)
	{
		//Taille de la fenêtre
		int fen_x=getSize().width;
		int fen_y=getSize().height;
		
		g.setColor(ground_Color);
		g.fillRect(0, 0,fen_x,fen_y);
	   // System.out.println(taille_x);
	
		double stepx=fen_x/(double)taille_x;
		double stepy=fen_y/(double)taille_y;
		double position_x=0;
	
		for(int x=0; x<taille_x; x++)
		{
			double position_y = 0 ;
			for(int y=0; y<taille_y; y++)
			{
				//Création des murs
				if (this.BbmG.etatJeu.getMap().isWall(x, y)){
					try {
						Image img = ImageIO.read(new File("./image/wall.png"));
						g.drawImage(img, (int)position_x, (int)position_y, (int)stepx, (int)stepy, this);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				//Création des briques destructibles
				else if (this.BbmG.etatJeu.getMap().isBrokable_Wall(x, y)){
					try {
						Image img = ImageIO.read(new File("./image/brique_2.png"));
						g.drawImage(img, (int)position_x, (int)position_y, (int)stepx, (int)stepy, this);
					} catch (IOException e) {
						e.printStackTrace();
					}
			
				}else {
					try {
						Image img = ImageIO.read(new File("./image/grass.png"));
						g.drawImage(img, (int)position_x, (int)position_y, (int)stepx, (int)stepy, this);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				position_y+=stepy;				
			}
			position_x+=stepx;
		}
		
		
		ArrayList<Agent_Bomberman> bombermans = BbmG.etatJeu.getBombermans();
		
		ArrayList<Agent> ennemies = BbmG.etatJeu.getEnnemies();
		
		
		for(int i = 0; i < ennemies.size(); i++){
			if(!ennemies.get(i).isDead())
			dessine_Ennemy(g,ennemies.get(i));	
		}
		
		
		for(int i = 0; i < bombermans.size(); i++){

			if(!bombermans.get(i).isDead())
				dessine_Bomberman(g,bombermans.get(i));
			
		}
		

		
	}
	
	void dessine_Ennemy(Graphics g, Agent agent)
	{
		int fen_x = getSize().width;
		int fen_y = getSize().height;

		double stepx = fen_x/(double)taille_x;
		double stepy = fen_y/(double)taille_y;

		int px = agent.getX();
		int py = agent.getY();
		
		double pos_x=px*stepx;
		double pos_y=py*stepy;
		
		int direc_en = agent.getDirection();
		if (direc_en==Map.NORTH){
			
			try {
				Image img = ImageIO.read(new File("./image/ennemy_North.png"));
				g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (direc_en==Map.SOUTH){
			
			try {
				Image img = ImageIO.read(new File("./image/ennemy_South.png"));
				g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (direc_en==Map.EAST){
			
			try {
				Image img = ImageIO.read(new File("./image/ennemy_East.png"));
				g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (direc_en==Map.WEST){
			
			try {
				Image img = ImageIO.read(new File("./image/ennemy_West.png"));
				g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	void dessine_Bomberman(Graphics g, Agent_Bomberman agentBBM)
	{
		int fen_x = getSize().width;
		int fen_y = getSize().height;

		double stepx = fen_x/(double)taille_x;
		double stepy = fen_y/(double)taille_y;
		
		float [] scales = null;

		switch(agentBBM.getCouleur())
        {
            case ROUGE :
            	 scales = new float[]{3 ,0.75f, 0.75f, 1.0f };
                break;
            case VERT :
            	scales = new float[]{0.75f ,3, 0.75f, 1.0f };
                break;
            case BLEU :
            	scales = new float[]{0.75f ,0.75f, 3, 1.0f };
                break;
            case JAUNE :
            	scales = new float[]{3 ,3, 0.75f, 1.0f };
                break;
            case BLANC :
            	scales = new float[]{2 ,2, 2, 1.0f };
                break;
        }
		
		
		float [] couleur = scales;
		
		int px = agentBBM.getX();
		int py = agentBBM.getY();
		
		double pos_x=px*stepx;
		double pos_y=py*stepy;
		
		int direc_en = agentBBM.getDirection();
		if (direc_en==Map.NORTH){
			
			try {
				BufferedImage img = ImageIO.read(new File("./image/bomberman_NORTH.png"));
				
				RescaleOp op = new RescaleOp(scales, contraste, null);
				BufferedImage resultat = op.filter(img, null);
				Image img_result = resultat;
				g.drawImage(img_result, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (direc_en==Map.SOUTH){
			
			try {
				BufferedImage img = ImageIO.read(new File("./image/bomberman_SOUTH.png"));				
				
			    RescaleOp op = new RescaleOp(scales, contraste, null);
				BufferedImage resultat = op.filter(img, null);
				Image img_result = resultat;
				g.drawImage(img_result, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (direc_en==Map.EAST){
			
			try {
				BufferedImage img = ImageIO.read(new File("./image/bomberman_EAST.png"));				
				
			    RescaleOp op = new RescaleOp(scales, contraste, null);
				BufferedImage resultat = op.filter(img, null);
				Image img_result = resultat;
				g.drawImage(img_result, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (direc_en==Map.WEST){
			
			try {
				BufferedImage img = ImageIO.read(new File("./image/bomberman_WEST.png"));
				
			    RescaleOp op = new RescaleOp(scales, contraste, null);
				BufferedImage resultat = op.filter(img, null);
				Image img_result = resultat;
				g.drawImage(img_result, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	
}
