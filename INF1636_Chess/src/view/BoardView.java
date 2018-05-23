package view;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.*;
import resources.Pair;


public class BoardView extends JPanel {
	private static final long serialVersionUID = 1L;
	private int lines;
	private int columns;
	private int cellWidth;
	private int cellHeight;
	private Color[][] cellColor;
	private Map<String, Image> pieceImage;
	
	public BoardView(int width, int height, int lines, int columns) {
		this.setSize(width, height);
		this.setBackground(Color.WHITE);
		
		setBoardDimensions(lines, columns);
		Pair[] images = new Pair[]{new Pair("queenW", "bin/images/queen_blue")};
		setPiecesImages(images);
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
	
	/*
	 * Description: Preenche o mapa de imagens com o nome-chave da peça e 
	 * 				a imagem como valor. Imagens nao encontradas no projeto
	 * 				nao serao adicionadas no vetor.
	 * Params[in]:  images - array de pares com a chave e o nome do arquivo
	 */
	public void setPiecesImages(Pair[] images) {
		pieceImage = new HashMap<String,Image>();
		for (int i = 0; i < images.length; i++) {
		Image image;
		
		try {
			image = ImageIO.read(new File(images[i].getSecond()));
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
			continue;
		}
			Image imgScaled = image.getScaledInstance(cellWidth, cellHeight, 
													Image.SCALE_SMOOTH);
			pieceImage.put(images[i].getFirst(), imgScaled);
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
		paintPiece(g, null, 100, 100);
	}
	
	private void paintPiece(Graphics g, Component c, int x, int y) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(pieceImage.get("queenW"), x, y, null);
		
	}
}
