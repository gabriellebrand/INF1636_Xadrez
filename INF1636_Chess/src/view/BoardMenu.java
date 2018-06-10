package view;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class BoardMenu extends JPopupMenu {
	private static final long serialVersionUID = -1654539359586724998L;

	public BoardMenu(ActionListener actionListener)
	{
		super();
		JMenuItem item = new JMenuItem(PopupItem.SaveState.getRawValue());
		item.addActionListener(actionListener);
		add(item);
	}
	
	public void show(MouseEvent e)
	{
		this.show(e.getComponent(), e.getX(), e.getY());
	}
}
