package game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class MainFrame extends JFrame{

	private static final long serialVersionUID = 8904641527445622030L;

	/**
	 * TODO new game, win screen, buttons
	 * TODO undo more than once
	 * @param title
	 */
	public MainFrame(String title){
		super(title);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		GamePanel jlbempty = new GamePanel();
		int width = 10 + ((100 +10) * 4);
		jlbempty.setPreferredSize(new Dimension(width, width));
		getContentPane().add(jlbempty, BorderLayout.CENTER);
		this.addKeyListener(jlbempty);
		pack();
		setVisible(true);
	}
	
	public static void main(String args[]) {
		new MainFrame("2048");
	}
	
}
