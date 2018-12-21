package blockGame;
import java.awt.Color;


public class Projectile extends Item {

	
	private double magL=2.6, magR=1.25;
	protected int pnum, bCount;
	protected boolean grounded = false;
	
	@SuppressWarnings("static-access")
	public Projectile(double x, double y, boolean left, int playn, int numbounces, type type, Color color){
		super(0,0,0,0,10,10,true,type, color);
		pnum = playn;
		
		int diff=0;
		if (x>=0)diff=10;
		
		super.x = Game.players.get(pnum-1).x +diff;
		super.y = Game.players.get(pnum-1).y-5;
		
		double div = Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2)) / magL;
		if (left) div*= magL/magR;
		
		xVel = x/div + Game.players.get(pnum-1).xVel;
		yVel = y/div + Game.players.get(pnum-1).yVel;
		
		if (numbounces>=0)
			this.bCount = numbounces+1;
		if (this.type == type.STICK || numbounces<0 )this.bCount = -1;

	}
	
	public void go(int i){
		boolean[] h = super.go();
		
		
		if (!grounded && h[1] && !(h[2]||h[3])&& Math.abs(yVel)<.15 && yVel<0) {grounded=true; yVel=0; moveClosestDown();} 

		if ((grounded && !h[1] && !h[2] && !h[3]) && yVel!=0) grounded=false;
		
		if ((h[0]||h[1]||h[2]||h[3]) && bCount>0) 
			bCount--;
		if (bCount==0 ) Game.projs.remove(i);
		
		
		//if (type!=type.STICK&&Math.abs(xVel)<=.01 && Math.abs(yVel)<=.01 && (h[0]||h[1]||h[2]||h[3])) Game.projs.remove(i);


	}


	
	
	
	

}

