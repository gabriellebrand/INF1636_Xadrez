package view;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.*;

public class BoardView extends JPanel {
	private static final long serialVersionUID = 1L;
	private int lines;
	private int columns;
	private int cellWidth;
	private int cellHeight;
	private Color[][] cellColor;
	
	public BoardView(int width, int height, int lines, int columns) {
		this.setSize(width, height);
		this.setBackground(Color.WHITE);
		
		setBoardDimensions(lines, columns);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintBoard(g);
	}
	
	public int getCellWidth() {
		return this.cellWidth;
	}
	
	public int getCellHeight() {
		return this.cellHeight;
	}
	
	private void setBoardDimensions(int lines, int columns) {
		this.lines = lines;
		this.columns = columns;
		this.cellWidth = this.getWidth()/columns;
		this.cellHeight = this.getHeight()/lines;
		
		setCellBackgroundColors(new Color[] {Color.WHITE, Color.BLACK});
	}
	
	protected void setCellBackgroundColors(Color[] colors) {
		int colorIndex = 0;
		cellColor = new Color[lines][columns];
		for (int i = 0; i < lines; i++) {
			colorIndex = (colorIndex + 1) % colors.length;
			for (int j = 0; j < columns; j++) {
				cellColor[i][j] = colors[colorIndex];
				colorIndex = (colorIndex + 1) % colors.length;
			}
		}
	}
	
	private void paintBoard(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < lines; j++) {	
				g2d.setColor(cellColor[i][j]);
				// Desenha retângulo
				g2d.fillRect(j*cellWidth, i*cellHeight, cellWidth, cellHeight);
			}
		}
	}
}
