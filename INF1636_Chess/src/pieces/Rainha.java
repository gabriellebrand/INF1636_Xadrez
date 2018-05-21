package Pecas;

public class Rainha extends Peca {
	
	Rainha(int newx, int newy, int newcor)
	{
		super(newx, newy, newcor);
	}
	
	private boolean nocaminho(Tabuleiro tab, int dx, int dy)
	{
		int i;
		int signaldx, signaldy;
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
		if (dx==0)//vertical movement
		{
			for (i=signaldy; Math.abs(i)<Math.abs(dy); i+=signaldy)// teste equivalente a i*signaldy< dy*signaldy, mas mais legivel
			{
				if (tab.existepeca(x, y+i))
				{
					return true;
				}
			}
			return false;
		}
		else if (dy==0)//horizontal movement
		{
			for (i=signaldx; Math.abs(i)<Math.abs(dx); i+=signaldx)
			{
				if (tab.existepeca(x+i, y))
				{
					return true;
				}
			}
			return false;
		}
		else if (dx==dy)// diagonal movement
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
		if (dx==0 || dy==0)
		{
			return !nocaminho(tab, dx, dy);
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
