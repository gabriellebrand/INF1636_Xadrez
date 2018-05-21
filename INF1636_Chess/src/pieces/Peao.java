package Pecas;

public class Peao extends Peca {
	
	boolean firstmove;
	
	Peao(int newx, int newy, int newcor)
	{
		super(newx, newy, newcor);
		firstmove = true;
	}
	
	private boolean testmove (Tabuleiro tab, int newx, int newy)
	{
		int dx = newx-x;
		int dy = newy-y;
		if (dx==0 && dy==0)
		{
			return false;
		}
		if (dx==0 && dy==1)
		{
			firstmove = false;
			return true;
		}
		if (dx==0 && dy==2 && firstmove)
		{
			firstmove = false;
			return true;
		}
		Peca pecadst = tab.getPeca(newx, newy);
		if (Math.abs(dx)==1 && dx==1 && pecadst.getcolor() != color)
		{
			firstmove = false;
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
