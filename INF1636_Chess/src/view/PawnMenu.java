package view;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class PawnMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;

	public PawnMenu(ActionListener actionListener)
	{
		super();
		JMenuItem queen = new JMenuItem(PopupItem.Queen.getRawValue());
		queen.addActionListener(actionListener);
		JMenuItem rook = new JMenuItem(PopupItem.Rook.getRawValue());
		rook.addActionListener(actionListener);
		JMenuItem bishop = new JMenuItem(PopupItem.Bishop.getRawValue());
		bishop.addActionListener(actionListener);
		JMenuItem knight = new JMenuItem(PopupItem.Knight.getRawValue());
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
