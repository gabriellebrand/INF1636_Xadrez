package Pecas;

public class Peca {
	int x,y, color;
	
	Peca(int newx, int newy, int newcor)
	{
		x=newx;
		y=newy;
		color = newcor;
	}
	
	int getx()
	{
		return x;
	}
	
	int gety()
	{
		return y;
	}
	
	int getcolor()
	{
		return color;
	}
	
	boolean move(Tabuleiro tab, int newx, int newy) //Possivelmente o método é igual para qualquer peca
	{
		return false;
	}
	
	boolean draw()
	{
		return false;
	}
}
