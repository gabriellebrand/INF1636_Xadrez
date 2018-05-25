package view;
import observer.*;
//import controller.*;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.*;
import resources.Pair;

public class BoardView extends JPanel implements BoardObserver {
	private static final long serialVersionUID = 1L;
	private int lines;
	private int columns;
	private int cellWidth;
	private int cellHeight;
	private Color[][] cellColor;
	private String[][] componentsID = null;
	private Map<String, Image> pieceImages;
	
	public BoardView(int width, int height, int lines, int columns) {
		this.setSize(width, height);
		this.setBackground(Color.WHITE);
		
		setBoardDimensions(lines, columns);
		setCellBackgroundColors(new Color[] {Color.WHITE, Color.DARK_GRAY});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintBoard(g);
	}
	
	private void paintBoard(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < lines; j++) {	
				g2d.setColor(cellColor[i][j]);
				// Desenha retângulo
				g2d.fillRect(j*cellWidth, i*cellHeight, cellWidth, cellHeight);
				
				if (componentsID != null)
					paintPiece(g2d, componentsID[i][j], i,j);
			}
		}
	}
	
	/*
	 * Description: Desenha uma peca em uma celula do tabuleiro.
	 * Params[in]:  g  - contexto da area de desenho
	 * 				id - identificador da peca, usado para encontrar 
	 * 					 a imagem da peca a ser desenhada
	 * 				line, column - posicao do tabuleiro onde sera'
	 * 							   desenhada a peca
	 */
	private void paintPiece(Graphics2D g2d, String id, int line, int column)
	{
		//verifica se existe peca na posicao e se a imagem existe no data source
		if (id != null && pieceImages.get(id) != null)
		{
			g2d.drawImage(pieceImages.get(id), cellWidth*column, 
						  cellHeight*line, null);	
		}
	}
	
	/*
	 * Description: Configura as dimensoes da celula do tabuleiro
	 * Params[in]:  lines, columns - quantidade de linhas e colunas
	 * 								 do tabuleiro
	 */
	private void setBoardDimensions(int lines, int columns) 
	{
		this.lines = lines;
		this.columns = columns;
		this.cellWidth = this.getWidth()/columns;
		this.cellHeight = this.getHeight()/lines;
	}
	
	/*
	 * Description: Preenche o mapa de que guarda as cores de fundo do
	 * 				tabuleiro. Intercala as cores recebidas como parametro.
	 * Params[in]:  colors - array com as cores que serao usadas para
	 * 				pintar o fundo
	 */
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
		pieceImages = new HashMap<String,Image>();
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
			pieceImages.put(images[i].getFirst(), imgScaled);
		}
	}
	
	public int getCellWidth() {
		return this.cellWidth;
	}
	
	public int getCellHeight() {
		return this.cellHeight;
	}
	
	@Override
	public void notify(BoardObservable o)
	{
		//pega a nova posicao dos componentes (reposicionados pelo modelo) e redesenha
		componentsID = o.get();
		repaint();
	}
}
