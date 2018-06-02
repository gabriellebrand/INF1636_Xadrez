package view;
import observer.*;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import resources.Pair;
import java.util.Map;
import java.util.HashMap;


public class BoardView extends JPanel implements BoardObserver {
	private static final long serialVersionUID = 1L;
	private int lines;
	private int columns;
	private int cellWidth;
	private int cellHeight;
	private Color highlightColor = new Color(255, 0, 0, 100);
	private Color selectedColor = Color.RED.darker();
	private Color[][] cellColor;
	private Boolean[][] highlightCells;
	private String[][] pieceIDs = null;
	private Pair selectedPos = null;
	private Map<String, Image> pieceImages;
	
	public BoardView(int width, int height, int lines, int columns) {
		this.setSize(width, height);
		this.setBackground(Color.WHITE);

		setBoardDimensions(lines, columns);
		setCellBackgroundColors(new Color[] {Color.DARK_GRAY,Color.WHITE});
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintBoard(g);
	}
	
	private void paintBoard(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++) {
				// Desenha retângulo do tabuleiro
				g2d.setColor(cellColor[i][j]);
				g2d.fillRect(j*cellWidth, i*cellHeight, cellWidth, cellHeight);
				
				//Pinta areas selecionadas (movimentos possiveis da peca)
				paintHighlightedCells(g2d, i, j);
				
				if (pieceIDs != null)
					paintPiece(g2d, pieceIDs[i][j], i,j);
			}
		}
		if (selectedPos != null)
			paintSelectedPiece(g2d, (int)selectedPos.getFirst(), (int)selectedPos.getSecond());	
	}
	
	/*
	 * Description: Desenha um retangulo vermelho transparente para marcar
	 * 				a área de movimento da peça
	 * Params[in]:  g2d  - contexto da area de desenho
	 * 				line, column - posicao do tabuleiro onde será
	 * 							   desenhado retangulo
	 */
	private void paintHighlightedCells(Graphics2D g2d, int line, int column)
	{
		if (highlightCells[line][column]) {
			g2d.setColor(highlightColor);
			g2d.fillRect(column*cellWidth, line*cellHeight, cellWidth, cellHeight);
		}
	}
	
	/*
	 * Description: Desenha uma borda em volta da casa onde está 
	 * 				a peça selecionada
	 * Params[in]:  g2d  - contexto da area de desenho
	 * 				line, column - posicao do tabuleiro onde será
	 * 							   desenhada a borda
	 */
	private void paintSelectedPiece(Graphics2D g2d, int line, int column)
	{
		float thickness = 6;
		Stroke oldStroke = g2d.getStroke();
		g2d.setColor(selectedColor);
		g2d.setStroke(new BasicStroke(thickness));
		g2d.drawRect(column*cellWidth, line*cellHeight, cellWidth, cellHeight);
		g2d.setStroke(oldStroke);
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
	protected void setCellBackgroundColors(Color[] colors)
	{
		int colorIndex = 0;
		cellColor = new Color[lines][columns];
		highlightCells = new Boolean[lines][columns];
		
		for (int i = 0; i < lines; i++) {
			colorIndex = (colorIndex + 1) % colors.length;
			for (int j = 0; j < columns; j++) {
				highlightCells[i][j] = false;
				cellColor[i][j] = colors[colorIndex];
				colorIndex = (colorIndex + 1) % colors.length;
			}
		}
	}
	
	public void setHighlightColor(Color color)
	{
		highlightColor = color;
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
				image = ImageIO.read(new File((String)images[i].getSecond()));
			}
			catch (IOException e) {
				System.out.println(e.getMessage());
				continue;
			}
			Image imgScaled = image.getScaledInstance(cellWidth, cellHeight, 
													  Image.SCALE_SMOOTH);
			pieceImages.put((String)images[i].getFirst(), imgScaled);
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
		pieceIDs = o.getPiecesPosition();
		highlightCells = o.getHighlightedCells();
		selectedPos = o.getSelectedPosition();
		repaint();
	}
}
