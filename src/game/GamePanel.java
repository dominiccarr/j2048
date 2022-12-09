package game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 2709756259645789247L;
	private final Grid game;
	private static final Color BACKGROUND = new Color(172, 158, 141);

	public GamePanel() {
		game = new Grid(4, 4, this);
	}

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		graphics.setColor(BACKGROUND);
		graphics.drawRect(0, 0, 500, 500);
		Graphics2D g2 = (Graphics2D) graphics;
		Square[][] board = game.getBoard();
		graphics.setFont(new Font("Arial", Font.BOLD, 50));
		int verticalOffset = 10;
		graphics.fillRect(0, 0, 500, 500);
		for (Square[] currentRow : board) {
			int horizontalOffset = 10;
			for (Square square : currentRow) {

				graphics.setColor(square.getColor());
				RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
						horizontalOffset, verticalOffset, square.getWidth(),
						square.getHeight(), 20, 20);
				g2.fill(roundedRectangle);

				if (square.isUsed()) {
					graphics.setColor(Color.white);
					drawString(square.getValue() + "",
							square.getWidth(), horizontalOffset,
							verticalOffset, graphics, square.getHeight());
				}
				horizontalOffset += 110;
			}
			verticalOffset += 110;
		}
	}

	private void drawString(String s, int width, int XPos, int YPos,
			Graphics g, int height) {
		int stringLen = (int) g.getFontMetrics().getStringBounds(s, g)
				.getWidth();
		int stringheight = (int) g.getFontMetrics().getStringBounds(s, g)
				.getHeight();
		int start = (width / 2) - (stringLen / 2);
		int start2 = (height / 2) + (stringheight / 2);
		g.drawString(s, start + XPos, -7 + start2 + YPos);
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		game.handle(e);
		repaint();
	}

	public void keyReleased(KeyEvent e) {
	}

}
