package gui;

import javax.swing.JFrame;

public class GUI {
	public JFrame jf;
	public Draw draw;
	
	public GUI() {
		jf = new JFrame();
		draw = new Draw();
		
		jf.setSize(1000, 800);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(draw);
		jf.setVisible(true);
	}
}
