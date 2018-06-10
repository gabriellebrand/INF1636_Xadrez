package model;

/* Classe que guarda as variáveis de estado do jogo.
 * Formato do arquivo .txt:
 * line 1: [currPlayer] (0 ou 1)
 * line 2: [selected] (0 ou 1)
 * line 3: [selx] (int)
 * line 4: [sely] (int)
 * line 5 - end: [line][column][pieceType] (int,int,String)
 */

public class BoardFile {
	public int currplayer;
	public boolean selected;
	public int selx, sely;
	public String[][] board;
}
