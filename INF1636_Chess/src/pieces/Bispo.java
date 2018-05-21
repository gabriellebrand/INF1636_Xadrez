package Pecas;

public class Bispo extends Peca {
	
	Bispo(int newx, int newy, int newcor)
	{
		super(newx, newy, newcor);
	}
	
	private boolean nocaminho(Tabuleiro tab, int dx, int dy)
	{
		int i;
		int signaldx;
		if (dx<0)
		{
			signaldx=-1;
		}
		else if (dx>0)
		{
			signaldx=1;
		}
		else
		{
			signaldx=0;
		}
		if (dy<0)
		{
			signaldy=-1;
		}
		else if (dy>0)
		{
			signaldy=1;
		}
		else
		{
			signaldy=0;
		}
		if (dx==dy)// diagonal movement
		{
			for (i=signaldx; Math.abs(i)<Math.abs(dx); i+=signaldx)
			{
				if (tab.existepeca(x+i, y+i))
				{
					return true;
				}
			}
			return false;
		}
		else//if (dx==-dy)// diagonal movement
		{
			for (i=signaldx; Math.abs(i)<Math.abs(dx); i+=signaldx)
			{
				if (tab.existepeca(x+i, y-i))
				{
					return true;
				}
			}
			return false;
		}
	}
	private boolean testmove (Tabuleiro tab, int newx, int newy)
	{
		int dx = newx-x;
		int dy = newy-y;
		if (dx==0 && dy==0)
		{
			return false;
		}
		if (dx==dy || dx==-dy)
		{
			return !nocaminho(tab, dx, dy);
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