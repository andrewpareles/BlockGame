package blockGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Player extends Item implements ActionListener, MouseListener, KeyListener {
	protected int pnum;
	protected Color bCol, sCol;
	public boolean SpR = true, DP = false, AP = false, SpP = false, SP = false, trace = true;

	public Player(String name, int pnum, type type, Color color, Color bCol, Color sCol) {
		super(500 + (int) (Math.random() * Game.WIDTH - 500), Game.HEIGHT / 2, 0, 0, 20, 20, true, type, color);
		this.pnum = pnum;
		this.bCol = bCol;
		this.sCol = sCol;
	}

	public void kill() { // WORK ON THIS

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			SpR = true;
		if (e.getKeyCode() == KeyEvent.VK_A)
			AP = false;
		if (e.getKeyCode() == KeyEvent.VK_D)
			DP = false;
		if (e.getKeyCode() == KeyEvent.VK_S) {
			SP = false;
			trace = false;
		}

		if (AP || DP) {
			if (AP)
				xVel = -1;
			if (DP)
				xVel = 1;
		} else
			xVel = 0;

	}

	@SuppressWarnings("static-access")
	@Override
	public void mousePressed(MouseEvent e) {
		boolean left = true;
		if (e.getButton() == 1) {
			left = false;
		}
		type type1;
		if (left)
			type1 = type.STICK;// (Math.random()>.1)? type.BOUNCE : type.STICK;
		else
			type1 = type.BOUNCE;
		Color c1;
		if (type1 == type.BOUNCE)
			c1 = bCol;
		else
			c1 = sCol;
		Game.projs.add(playerClickProjectile(e.getX(), e.getY(), left, type1, c1));
	}

	public Projectile playerClickProjectile(int mx, int my, boolean left, type type1, Color c1) {

		return new Projectile(mx - (x + 10), my - (y + 20), left, pnum, -1, type1, c1);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE && yVel == 0 && SpR) {
			SpR = false;
			yVel += -2.5;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			xVel = -1;
			AP = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			xVel = 1;
			DP = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			SP = true;
			trace = true;
		}

		// if (e.getKeyCode() == KeyEvent.VK_S) { yVel+=.5; }

	}

	@SuppressWarnings("static-access")
	public void paint(Graphics g) {
		super.paint(g);
		Projectile bp = playerClickProjectile((int) MouseInfo.getPointerInfo().getLocation().getX(),
				(int) MouseInfo.getPointerInfo().getLocation().getY(), false, type.BOUNCE, bCol);
		Projectile sp = playerClickProjectile((int) MouseInfo.getPointerInfo().getLocation().getX(),
				(int) MouseInfo.getPointerInfo().getLocation().getY(), true, type.STICK, sCol);

		trace(bp, g);
		trace(sp, g);

	}

	public void trace(Projectile p, Graphics g) {

		if (trace) {

			g.setColor(p.color);

			double interval = .05, max=.5;
			
			
			double vy = p.yVel, vx = p.xVel, x0 = p.x + p.width / 2, y0 = p.y + p.height / 2, xprev = x0, yprev = y0,
					drag = Game.xDrag * (vx > 0 ? -1 : 1);

			for (double t = 0; t <= max; t += interval) {
				t *= 1000d / Game.refreshTimeMS;
				int nextx = (int) (x0 + vx * t + drag * t * t / 2);
				int nexty = (int) (y0 + vy * t + Game.grav * t * t / 2);

				if (vx > 0 && nextx < xprev || vx < 0 && nextx > xprev)
					nextx = (int) xprev;
				g.drawLine((int) xprev, (int) yprev, nextx, nexty);

				xprev = nextx;
				yprev = nexty;

				t /= 1000d / Game.refreshTimeMS;

			}

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
