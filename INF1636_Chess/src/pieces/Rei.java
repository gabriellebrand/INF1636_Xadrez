package Pecas;

public class Rei extends Peca {

	
	Rei(int newx, int newy, int newcor)
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
		if (dx<-1 || dx>1)
		{
			return false;
		}
		if (dy<-1 || dy>1)
		{
			return false;
		}
		return true;
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
		if (tab.atacked(newx, newy, color))//tentando se colocar em check-mate
		{
			return false;
		}
		if (!testmove(tab, newx, newy))//Teste de movimento valido
		{
			return false;
		}
		x=newx;
		y=newy;
		return true;
	}

	//boolean draw() TODO
}
