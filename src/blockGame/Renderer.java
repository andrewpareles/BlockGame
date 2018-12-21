package blockGame;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Renderer extends JPanel {

	private static final long serialVersionUID = 100L;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		try{
		Game.game.repaint(g);
		}
		catch (Exception e){
			
		}
	}
}