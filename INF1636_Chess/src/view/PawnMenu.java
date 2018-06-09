package view;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.awt.event.ActionListener;
//import java.awt.Component;
import java.awt.event.MouseEvent;

public class PawnMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;

	public PawnMenu(ActionListener actionListener)
	{
		super();
		JMenuItem queen = new JMenuItem("Rainha");
		queen.addActionListener(actionListener);
		JMenuItem rook = new JMenuItem("Torre");
		rook.addActionListener(actionListener);
		JMenuItem bishop = new JMenuItem("Bispo");
		bishop.addActionListener(actionListener);
		JMenuItem knight = new JMenuItem("Cavalo");
		knight.addActionListener(actionListener);
		add(queen);
		add(rook);
		add(bishop);
		add(knight);
	}
	
	public void show(MouseEvent e)
	{
		this.show(e.getComponent(), e.getX(), e.getY());
	}
	
//	public void show(Component invoker, int x, int y)
//	{
//		this.show(invoker, x, y);
//	}
}
