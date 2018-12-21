package blockGame;

import java.awt.Color;

public class Platform extends Item {
	private boolean kill;
	
	public Platform(double x, double y, int width, int height, boolean kill, type type, Color color){
		super(x,y,0,0,width,height,false,type,color);
		this.kill=kill;
	}
	
	
	public boolean[] go(){
		super.go();
		if (kill) for (int i=Game.players.size()-1; i>=0;i--) if (this.faceCollidedWith(Game.players.get(i))!=null) Game.players.get(i).kill();
		
		
		boolean[] h = {false};
		return h;
		//for (int i=Game.projs.size()-1; i>=0; i--) interact(Game.projs.get(i));
		
	}

}

