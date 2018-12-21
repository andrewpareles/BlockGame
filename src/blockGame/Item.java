package blockGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Item {
	protected double xVel, yVel, x, y;
	protected int height, width;
	protected Color color;
	protected boolean grav;
	protected type type;
	
	public enum face{
		TOP,
		BOTTOM,
		LEFT,
		RIGHT
	}
	
	public enum type{
		JUMP_BOTTOM,
		BOUNCE,
		STICK,
		NONE
	}
	
	
	public Item(double x, double y, double xVel, double yVel, int width, int height, boolean grav, type type, Color color){
		this.xVel=xVel;
		this.yVel = yVel;
		this.x=x;
		this.y=y;
		this.height=height;
		this.width=width;
		this.grav=grav;
		this.type = type;
		this.color=color;
	}
	
	
	public Item(Item i1){
		xVel=i1.xVel;
		yVel = i1.yVel;
		x=i1.x;
		y=i1.y;
		height=i1.height;
		width=i1.width;
		grav=i1.grav;
		type=i1.type;
		color=i1.color;
	}
	

	
	@SuppressWarnings({ "static-access" })
	public boolean[] go(){
		
		boolean noUp=false, noDown=false, noRight=false, noLeft=false;
		for (Item i : Game.platforms) 
			if (i.faceCollidedWith(this)==face.RIGHT) {noLeft=true; x= i.x+i.width;}
			else if (i.faceCollidedWith(this)==face.LEFT) {noRight=true; x = i.x-width;}
			else if (i.faceCollidedWith(this)==face.BOTTOM) {noUp=true; y=i.y+i.height;}
			else if (i.faceCollidedWith(this)==face.TOP) {noDown=true; y = i.y-height;}


			
		
		boolean[] plat = {noUp, noDown, noRight, noLeft};

		if ( /*type != type.STICK &&*/ this instanceof Projectile|| this instanceof Player )
			for (Projectile i : Game.projs) 
				if ((i.type == type.STICK && type == type.BOUNCE) ||( type==type.BOUNCE && i.type == type.BOUNCE) || 
						(this instanceof Player && i.type == type.STICK && i.xVel == 0 && i.yVel==0)
						|| (i.type == type.STICK && type==type.STICK && ((i.xVel == 0 && i.yVel==0) || (xVel == 0 && yVel==0)  )
						))
				if (i.faceCollidedWith(this)==face.RIGHT) {noLeft=true; }
				else if (i.faceCollidedWith(this)==face.LEFT) {noRight=true;}
				else if (i.faceCollidedWith(this)==face.BOTTOM) {noUp=true;}
				else if (i.faceCollidedWith(this)==face.TOP) {noDown=true; }
		
		for (Player i : Game.players) 
			if ( this instanceof Player 
					//|| (this instanceof Projectile && ((Projectile)this).type != type.STICK) 
					)
				if (i.faceCollidedWith(this)==face.RIGHT) {noLeft=true; }
				else if (i.faceCollidedWith(this)==face.LEFT) {noRight=true;}
				else if (i.faceCollidedWith(this)==face.BOTTOM) {noUp=true;}
				else if (i.faceCollidedWith(this)==face.TOP) {noDown=true; }
		
		if (noDown && yVel>=0)
			if (type == type.BOUNCE ) yVel*=-Game.bounceMultiplier; 
			else if (type==type.NONE ) yVel=0;

		if (noUp && yVel<=0) yVel=-yVel;
		if (noRight && xVel>=0)
			if (type==type.BOUNCE) xVel*=-Game.bounceMultiplier;
			else if (type==type.NONE) x-=xVel;
		if (noLeft && xVel<=0) 
			if (type==type.BOUNCE) xVel*=-Game.bounceMultiplier;
			else if (type==type.NONE) x-=xVel;
		
		if (!noDown && grav) yVel+=Game.grav;		
		
		
		if (type==type.STICK && (noUp || noDown || noRight || noLeft)) {yVel=xVel=0; grav=false; }
		
		y+=yVel;
		x+=xVel; 
		
		if (xVel!=0 && !(this instanceof Player))
			if (xVel<0) xVel+=Game.xDrag;
			else xVel-=Game.xDrag;		
		
		return  plat;
	}

	
	public void paint(Graphics g){
		g.setColor(color);
		g.fillRect((int)x, (int)y, width, height);
	}

	 public int regionCollided(Item i1){
		  boolean side, top, both;
		  side=top=both=false;
		  if (x+width>=i1.x&&i1.x+i1.width>=x) side=true;
		  if (y+height>=i1.y&&i1.y+i1.height>=y) top=true;
		  if (side && top) both = true;
		  
		  if (both) return 3;
		  if (side) return 2;
		  if (top) return 1;
		  return 0;
		  //return ;
		  
		 }
	 
		public boolean isAbove(Item i1){
			return (y+height <= i1.y && ((x+width>i1.x && x+width < i1.x+i1.width) || (x>i1.x && x<i1.x+i1.width)));
		}
		public boolean isBelow(Item i1){
			return (y >= i1.y +i1.height && ((x+width>=i1.x && x+width <= i1.x+i1.width) || (x>=i1.x && x<=i1.x+i1.width)));
		}
		public boolean isRightOf(Item i1){
			return (x >= i1.x +i1.width && ((y+height>i1.y && x+height < i1.y+i1.height) || (y>i1.y && y<i1.y+i1.height)));
		}
		
		public boolean isLeftOf(Item i1){
			return (x+width <= i1.x && ((y+height>=i1.y && x+height <= i1.y+i1.height) || (y>=i1.y && y<=i1.y+i1.height)));
		}
		
		public Item undoX(){
			return new Item(x-xVel, y, xVel, yVel, width, height, grav, type, color);
		}
		public Item undoX(double by){
			return new Item(x-by, y, xVel, yVel, width, height, grav, type, color);
		}
		
		public Item undoY(){
			return new Item(x, y-yVel, xVel, yVel, width, height, grav, type, color);
		}
		public Item undoY(double by){
			return new Item(x, y-by, xVel, yVel, width, height, grav, type, color);
		}
		
	public face faceCollidedWith(Item i1){
		if (i1==this) return null;
		
		ArrayList<face> faces= new ArrayList<face>();
		 if ( i1.y <= y + height && i1.y > y && ((i1.x + i1.width > x && i1.x + i1.width < x + width)||(i1.x > x && i1.x < x + width)  || (x > i1.x && x<i1.x+i1.width) || (x+width < i1.x+i1.width && x + width > i1.x)) ) faces.add(face.BOTTOM); 
		 if ( i1.y + i1.height >= y && i1.y + i1.height < y + height && ((i1.x + i1.width > x && i1.x + i1.width < x + width)||(i1.x > x && i1.x <x + width)|| (x > i1.x && x<i1.x+i1.width) || (x+width < i1.x+i1.width && x + width > i1.x)) ) faces.add(face.TOP); 
		 if ( i1.x+i1.width >= x && i1.x < x && ((i1.y >= y  && i1.y <= y+height ) || (i1.y+i1.height >= y && i1.y + i1.height <= y + height) || (i1.y+i1.height>=y+height && i1.y <= y)) ) faces.add (face.LEFT); //this is collided with left face of i1
		 if ( i1.x <= x + width && i1.x + i1.width > x + width && ((i1.y >= y && i1.y <= y + height) || (i1.y + i1.height <= y + height && i1.y + i1.height >= y)|| (i1.y+i1.height>=y+height && i1.y <= y)) ) faces.add(face.RIGHT);
		 
		 //if (this instanceof Platform && i1 instanceof Projectile &&  faces.size()!=0 && faces.size()!=1)
			 //System.out.println(faces);
		 
		 //if (i1 instanceof Player)
			 //System.out.println(faces);
		 
		 if (faces.size()==0) return null;
		 else if (faces.size()==1) return faces.get(0);
		 else if (faces.size()==2){
			 Item i = i1.undoX().undoY();
			 
			 if (faces.contains(face.TOP) && faces.contains(face.LEFT)) {
				 while (i.isAbove(this) && i.isLeftOf(this)) {i =i.undoX().undoY(); if (i.x == i.undoX().x && i.y== i.undoY().y) return face.LEFT;}
				 //redo + check smaller increment xVel/10, yVel/10
				 
			 	 if (i.isAbove(this)) return face.TOP;
			 	 else if (i.isLeftOf(this)) return face.LEFT;
				 else return face.TOP;


			 }
				 
				 
			 else if (faces.contains(face.TOP) && faces.contains(face.RIGHT)) {
				 while (i.isAbove(this) && i.isRightOf(this)) {i =i.undoX().undoY(); if (i.x == i.undoX().x && i.y== i.undoY().y) return face.RIGHT;}
				 if (i.isAbove(this)) return face.TOP;
				 else if (i.isRightOf(this)) return face.RIGHT;
				 else return face.TOP;

			 }
				 
			 else if (faces.contains(face.BOTTOM) && faces.contains(face.LEFT)){ 
				 while (i.isBelow(this) && i.isLeftOf(this)) {i =i.undoX().undoY(); if (i.x == i.undoX().x && i.y== i.undoY().y) return face.LEFT;}
				 if (i.isBelow(this)) return face.BOTTOM;
				 else if (i.isLeftOf(this)) return face.LEFT;
				 else return face.BOTTOM;
			 }

			 else if (faces.contains(face.BOTTOM) && faces.contains(face.RIGHT)) {
				 while (i.isBelow(this) && i.isRightOf(this)) {i =i.undoX().undoY(); if (i.x == i.undoX().x && i.y== i.undoY().y) return face.RIGHT;} 
				 if (i.isBelow(this)) return face.BOTTOM;
				 else if (i.isRightOf(this)) return face.RIGHT;
				 else return face.BOTTOM;
				 
			 }
		 }
		 else if (faces.size()==3){
		 	if (faces.contains(face.LEFT) && faces.contains(face.RIGHT)){
		 		if (faces.contains(face.BOTTOM)) return face.BOTTOM;
		 		else return face.TOP;}
		 	if (faces.contains(face.TOP) && faces.contains(face.BOTTOM)){
		 		if (faces.contains(face.RIGHT)) return face.RIGHT;
		 		else return face.LEFT;}
		 }
		 if (faces.size()!=0)
		 System.out.println("END" + faces);
		 return null;
	}
	

	
	 public void moveClosestDown(){
		double y1=0;
		for (Item i : Game.platforms){
			if (isAbove(i) && y1==0)
				y1 = i.y;
			else if (isAbove(i) && i.y<=y1)
				 y1 = i.y;
		 }
		 y=y1-height;
		 
	 }
	
	
	
}
