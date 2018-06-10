package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileController {

	private final JFileChooser fc = new JFileChooser();
	
	public FileController() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
		fc.setFileFilter(filter);
	}
		
	public void saveFile(String[][] boardFile)
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
				
				if (boardFile != null)
				{
					for (int i = 0; i < boardFile.length; i++)
					{
						for (int j = 0; j < boardFile.length; j++)
						{
							if (boardFile[i][j] != null)
							{
								String s = i + " " + j + " " + boardFile[i][j];
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
