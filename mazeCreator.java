package maze;

import java.util.Scanner;
import java.io.IOException;
import java.util.Random;

public class mazeCreator 
{
	private int length=0,n=0;
	char s[][];
	int num=1;
	mazeCreator(int n)
	{
		this.n=n;
		this.length=2*n+1;
		s=new char[length][length];
		init();		
	}
	public void init()
	{
		for (int i=0;i<length;i++)
		{
			for (int j=0;j<length;j++)
			{
				if (i%2 == 0)
				{
					s[i][j]='-';
				}
				else if (i%2 == 1)
				{
					if (j%2==0)
						s[i][j]='|';
					else 
						s[i][j]= '0';
				}	
			}
		}
//		s[0][1]=' ';
		s[1][0]=' ';
		s[length-2][length-1]=' ';
//		s[length-1][length-2]=' ';
	}
	
	public int genRandom(int x)
	{
		return (int)(java.lang.Math.random()*100)%x;
	}
	
	public void create()
	{
		int cell=0,wall=0,w=0,xCell=0,yCell=0,xWall=0,yWall=0,adjCell=0,count=0;
		char prevWall = ' ';
		DisjSets d=new DisjSets(n*n);
		
		while ((d.find(n*n-1)) != 0  )
		{
//			cell=genRandom(n*n);
			xCell=2*(cell/n+1)-1;
			yCell=2*(cell%n+1)-1;
//			System.out.println("cell:"+cell);
//			System.out.println("xCell"+xCell);
//			System.out.println("yCell"+yCell);
			w=genRandom(4);
			if (w==0)
			{
				xWall=xCell-1;
				yWall=yCell;
 				adjCell=cell-n;
			}
			if (w==1)
			{
				xWall=xCell;
				yWall=yCell-1;
				adjCell=cell-1;
			}
			if (w==2)
			{
				xWall=xCell+1;
				yWall=yCell;
				adjCell=cell+n;
			}
			if (w==3)
			{
				xWall=xCell;
				yWall=yCell+1;
				adjCell=cell+1;
			}
						
			if ((xWall != 2*n && yWall != 2*n) && (xWall != 0 && yWall != 0))
			{
				
				//d.display();
//				System.out.println("w:"+w);
//				System.out.println("xWall:"+xWall);
//				System.out.println("yWall:"+yWall);
				if (s[xWall][yWall] !=' ' && d.find(adjCell)!= 0)
				{
					count=0;
					s[xWall][yWall]=' ';
					s[xCell][yCell]=' ';
//					System.out.println("adjCell:"+adjCell);
					d.union(cell,adjCell);					
				}	
				cell=adjCell;	
			}		
		}
		s[2*(cell/n+1)-1][2*(cell%n+1)-1]=' ';
	}
	
	public void display()
	{
		//System.out.println("L:"+length);
		for (int i=0;i<length;i++)
		{
			for (int j=0;j<length;j++)
			{
					System.out.print(s[i][j]);
			}
			System.out.println("");
		}
	}
	
	public static void main(String args[])
	{
		int n=0,l;
		Scanner s= new Scanner(System.in);
		System.out.println("Please enter the grid size: ");
		n=s.nextInt();		
		mazeCreator m=new mazeCreator(n);
		m.create();
		m.display();
	}

}
