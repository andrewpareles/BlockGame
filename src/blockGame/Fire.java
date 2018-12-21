package blockGame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import blockGame.Item.type;

public class Fire implements ActionListener {
	private Timer launch;
	private Color color;
	private Item pellet;
	
	public Fire(Color color){
		this.color = color;
		launch= new Timer((int)(Math.random()*2000) + 1000, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		launch.stop();
		launch= new Timer((int)(Math.random()*2000) + 1000, this);
		launch.start();
		
		pellet = new Item(-10, Game.HEIGHT-60, (int)(1.5+ Math.random()), 0, 10, 10,  false,type.BOUNCE, color);
		Game.fires.add(pellet);
		
	}
	
	public void start(){
		launch.start();
	}
	
}
