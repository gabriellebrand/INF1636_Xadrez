package view;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

public class Window extends JFrame {
	
	public Window(int width, int height, JPanel panel, String title) {
		this.setTitle(title);
		
		JMenuBar mb = new JMenuBar();  
		JMenu menu = new JMenu("Menu");
		JMenuItem item = new JMenuItem("Item");
		menu.add(item);
		mb.add(menu);
		this.setJMenuBar(mb);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int originX = screenSize.width/2 - width/2;
		int originY = screenSize.height/2 - height/2;
		this.setLocation(originX, originY);
		
		this.getContentPane().add(panel);
		this.getContentPane().setPreferredSize(new Dimension(height, width));
		setResizable(false);
		this.pack();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}	
}
