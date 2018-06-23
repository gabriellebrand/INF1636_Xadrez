package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.BoardFile;

public class FileController {

	private final JFileChooser fc = new JFileChooser();
	
	public FileController() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
		fc.setFileFilter(filter);
	}
		
	
	public BoardFile openFile ()
	{
		BoardFile boardStatus = null;
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			boardStatus = new BoardFile();
			BufferedReader inputStream = null;
			
			boardStatus.board = new String[8][8];
			boardStatus.firstMove = new int[8][8];
			for (int i=0;i<8;i++)
			{
				Arrays.fill(boardStatus.board[i], null);
				Arrays.fill(boardStatus.firstMove[i], -1);
			}
			try {
				inputStream = new BufferedReader(new FileReader(fc.getSelectedFile().getPath()));
				
				boardStatus.currplayer = Integer.parseInt(inputStream.readLine());
				if (boardStatus.currplayer != 0 && boardStatus.currplayer != 1)
					throw new IOException("Parse Error");
				boardStatus.selected = inputStream.readLine() == "1";
				boardStatus.selx = Integer.parseInt(inputStream.readLine());
				boardStatus.sely = Integer.parseInt(inputStream.readLine());
				
				String l;
				while ((l = inputStream.readLine()) != null)
				{
					//System.out.println(l);
					String[] tokens = l.split(" ");
					if (tokens.length != 4) 
						throw new IOException("Parse Error");
					
					int i = Integer.parseInt(tokens[0]);
					int j = Integer.parseInt(tokens[1]);
					
					boardStatus.board[i][j] = tokens[2];
					boardStatus.firstMove[i][j] = Integer.parseInt(tokens[3]);
					
					System.out.println(boardStatus.board[i][j]);
				}
			} catch (IOException e) {
				System.out.println("[FileController] " + e.getMessage());
				return null;
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						System.out.println("[FileController] " + e.getMessage());
						e.printStackTrace();
						return null;
					}
				}
			}
		}
		return boardStatus;
	}
	
	public void saveFile(BoardFile boardState)
	{
		int returnVal = fc.showSaveDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			PrintWriter outputStream = null;
			try {
				//adiciona extensao .txt, caso nao tenha
				File file = fc.getSelectedFile();
				String filename = file.getPath();

				if (filename.lastIndexOf(".") == -1 || 
					!filename.substring(filename.lastIndexOf("."),
					filename.length()).equalsIgnoreCase(".txt")) 
				    file = new File(file.toString() + ".txt");

				outputStream = new PrintWriter(new FileWriter(file.getPath()));
				
				outputStream.println(boardState.currplayer);
				outputStream.println(boardState.selected ? 1 : 0);
				outputStream.println(boardState.selx);
				outputStream.println(boardState.sely);
				
				String[][] board = boardState.board;
				int[][] firstMoves = boardState.firstMove;
				if (board != null && firstMoves != null)
				{
					for (int i = 0; i < board.length; i++)
					{
						for (int j = 0; j < board.length; j++)
						{
							if (board[i][j] != null && firstMoves[i][j] >= 0)
							{
								String s = i + " " + j + " " + board[i][j] + " " + firstMoves[i][j];
								outputStream.println(s);
							}
						}
					}	
				}
			} catch (Exception e) {
				System.out.println("[FileController] " + e.getMessage());
			} finally {
				if (outputStream != null) {
					outputStream.close();
				}
			}
		}
	}
}
