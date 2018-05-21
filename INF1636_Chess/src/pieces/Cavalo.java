package Pecas;

public class Cavalo extends Peca {
	
	Cavalo(int newx, int newy, int newcor)
	{
		super(newx, newy, newcor);
	}
	
	
	private boolean testmove (Tabuleiro tab, int newx, int newy)
	{
		int dx = newx-x;
		int dy = newy-y;
		if (dx==0 && dy==0)
		{
			return false;
		}
		if (Math.abs(dx)==2 && Math.abs(dy)==3)
		{
			return true;
		}
		return false;
	}
	
	boolean move(Tabuleiro tab, int newx, int newy)
	{
		if (newx<0 || newx>7 || newy<0 ||newy>7) //movimento para fora do tabuleiro
		{
			return false;
		}
		Peca pecadst = tab.getPeca(newx, newy);
		if (pecadst.getcolor() == color) //tentando comer peca de mesma cor
		{
			return false;
		}
		if (!testmove(tab, newx, newy)) //teste de movimento valido
		{
			return false;
		}
		
		x=newx;
		y=newy;
		return true;
	}

	//boolean draw() TODO
}
