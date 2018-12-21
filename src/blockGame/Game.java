package blockGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.Timer;

import blockGame.Item.type;


public class Game implements ActionListener {
 private Timer timer;
 public static Game game;

 public static JFrame jframe = new JFrame();
 public static final int WIDTH = 1600, HEIGHT = 500, refreshTimeMS=3;
 
 public Renderer renderer;
 public static double grav=.04, bounceMultiplier=.8, xDrag=.0001; //grav=.04, bounceMultiplier=.8, xDrag=.0001
 public static ArrayList<Player> players = new ArrayList<Player>();
 public static ArrayList<Item> platforms = new ArrayList<Item>();
 public static ArrayList<Item> fires = new ArrayList<Item>();
 public static ArrayList<Projectile> projs = new ArrayList<Projectile>();
 
 
 public static void main(String[] args) {
	game = new Game();
 }
 
 public Game() {
	JFrame jframe = new JFrame();
	renderer = new Renderer();
	timer = new Timer(refreshTimeMS, this);
	jframe.add(renderer);
	jframe.setTitle("Game");
	jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	jframe.setSize(WIDTH, HEIGHT);
	jframe.setResizable(false);
	jframe.setVisible(true);
	
	//Fire f = new Fire(new Color(100,100,100));
	//f.start();
	

	Player p1 = new Player("Bob",1, type.NONE, Color.blue, Color.red, Color.green);
	jframe.addMouseListener(p1);
	jframe.addKeyListener(p1);
	players.add(p1);
	
	
	//import map
	platforms.add(new Platform(600, HEIGHT-130, 200, 50, false, type.NONE, Color.cyan));

	platforms.add(new Platform(0, HEIGHT-60, WIDTH, 20, false, type.NONE, Color.black));
	
	platforms.add(new Platform(1000, HEIGHT-130, 500, 20, false, type.NONE, Color.black));
	platforms.add(new Platform(70, HEIGHT-84, 200, 10, false, type.NONE, Color.orange));
	platforms.add(new Platform(150, HEIGHT-85, 200, 2, false, type.NONE, Color.orange));
	
	


	timer.start();
 }
 

 public void actionPerformed(ActionEvent e) {

	for (int i=0; i<platforms.size(); i++) platforms.get(i).go();
	for (int i=0; i<fires.size(); i++) fires.get(i).go();
	for (int i=projs.size()-1; i>=0; i--) projs.get(i).go(i);
	for (int i=0; i<players.size(); i++) players.get(i).go();
	
	
	
	ArrayList<Integer> removenums = new ArrayList<Integer>(); 
	for (int i=projs.size()-1; i>=0; i--)
		for (int j=projs.size()-1; j>=0; j--)
			if (projs.get(j).faceCollidedWith(projs.get(i))!=null && i!=j &&projs.get(i).pnum != projs.get(j).pnum ) {removenums.add(i);}
	for (int num:removenums)
		projs.remove(num);
	//projectile collisions

	
	for (int i=Game.fires.size()-1; i>=0; i--) if (Game.fires.get(i).x>Game.WIDTH+20||Game.fires.get(i).x<-10) Game.fires.remove(i);
	for (int i=Game.projs.size()-1; i>=0; i--) if (Game.projs.get(i).x>Game.WIDTH+20||Game.projs.get(i).x<-10) Game.projs.remove(i);
	
	renderer.repaint();
}

 public void repaint(Graphics g) {
	for (int i=0; i<platforms.size(); i++) platforms.get(i).paint(g);
	for (int i=0; i<fires.size(); i++) fires.get(i).paint(g);
	for (int i=0; i<projs.size(); i++) projs.get(i).paint(g);
	

	for (int i=0; i<players.size(); i++) {players.get(i).paint(g);}
 }



	 
	 
	 
 



}