package maze;

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class mazeCreator 
{
	private int rows=0,columns=0,m=0,n=0;	
	private char s[][];
    private JButton b1[][],b2[][];
    JPanel gui1,gui2;
	mazeCreator(int m,int n)
	{
		this.m=m;
		this.n=n;
		this.rows=2*m+1;
		this.columns=2*n+1;
		s=new char[rows][columns];
		init();		
	}
	
	public void init()
	{
		gui1 = new JPanel();
		gui1.setLayout(new GridLayout(rows,columns));
		b1=new JButton[rows][columns];
		for (int i=0;i<rows;i++)
		{			
			for (int j=0;j<columns;j++)
			{
			 	b1[i][j]=new JButton();
			 	b1[i][j].setEnabled(false);
			 	b1[i][j].setBorderPainted(false);
				if (i%2 == 0)
				{	
					b1[i][j].setBackground(Color.BLACK);
					if (j%2==0)
						s[i][j]='-';
					else
						s[i][j]='+';
				}
				else if (i%2 == 1)
				{
					if (j%2==0)
					{
						b1[i][j].setBackground(Color.BLACK);
						s[i][j]='|';
					}
					else
					{
						b1[i][j].setBackground(Color.WHITE);
						s[i][j]=' ';
					}
				}				
			gui1.add(b1[i][j]);
			}
		}
		b1[1][0].setBackground(Color.WHITE);
		s[1][0]=' ';
		b1[rows-2][columns-1].setBackground(Color.WHITE);
		s[rows-2][columns-1]=' ';
	}
	
	public int genRandom(int x)
	{
		return (int)(java.lang.Math.random()*10*m*n)%x;
	}
	
	public void create()
	{
		int cell=0,w=0,xCell=0,yCell=0,xWall=0,yWall=0,adjCell=0,count=0;
		DisjSets d=new DisjSets(m*n);
 		while (count != m*n -1)
		{			
			cell=genRandom(m*n);
			xCell=2*(cell/n+1)-1;
			yCell=2*(cell%n+1)-1;
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
						
			if ((xWall != 2*m && yWall != 2*n) && (xWall != 0 && yWall != 0))
			{
				
				if (d.find(cell) != d.find(adjCell))
				{
					b1[xWall][yWall].setBackground(Color.WHITE);
					s[xWall][yWall]=' ';
					d.union(d.find(cell),d.find(adjCell));		
					count++;
				}	
			}		
		}

	}
	
	public void solve()
	{
		int cell=0,xCell=0,yCell=0;
		cell=columns;
		java.util.LinkedList<Integer> q=new java.util.LinkedList<Integer>();
		q.add(cell);
		while(cell != rows*columns-1-columns)
		{
			xCell=cell/columns;
			yCell=cell%columns;
				if (s[xCell][yCell+1] == ' ')				
				{
					s[xCell][yCell]='1';
					q.add(cell+1);
					cell=cell+1;
					continue;
				} 
				if (s[xCell+1][yCell] == ' ')
				{
					s[xCell][yCell]='1';
					q.add(cell+columns);
					cell=cell+columns;
					continue;
				}	
	
				if (s[xCell][yCell-1] == ' ')
				{
					s[xCell][yCell]='1';
					q.add(cell-1);
					cell=cell-1;
					continue;
				}
			
				if (s[xCell-1][yCell] == ' ')
				{
					s[xCell][yCell]='1';	
					q.add(cell-columns);
					cell=cell-columns;
					continue;
				}

					s[xCell][yCell]='1';
					q.removeLast();
					cell=q.peekLast();
					xCell=cell/columns;
					yCell=cell%columns;
					s[xCell][yCell]=' ';
		}
		
		
		gui2 = new JPanel();
		gui2.setLayout(new GridLayout(rows,columns));
		b2=b1;
		while(!q.isEmpty())
		{
			cell=q.removeFirst();
			xCell=cell/columns;
			yCell=cell%columns;
			b2[xCell][yCell].setBackground(Color.YELLOW);
		}
		for (int i=0;i<rows;i++)			
			for (int j=0;j<columns;j++)
				gui2.add(b2[i][j]);
			
	}

	public void display()
	{
		for (int i=0;i<rows;i++)
		{
			for (int j=0;j<columns;j++)
			{
					System.out.print(s[i][j]);
			}
			System.out.println("");
		}
	}
	public final JPanel getGui1() 
	{
        return gui1;
	}
	public final JPanel getGui2() 
	{
        return gui2;
	}
	public static void main(String args[])
	{
		int m=0,n=0;
		Scanner sc=new Scanner(System.in);
		System.out.println("Please number of rows of grid: ");
		m=sc.nextInt();	
		System.out.println("Please number of columns of grid: ");
		n=sc.nextInt();		
		sc.close();
		mazeCreator mc=new mazeCreator(m,n);

		mc.create();
		mc.display();

        mc.solve();
        JFrame f2=new JFrame("Maze2");
        f2.add(mc.getGui2());
        f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f2.pack();
        f2.setVisible(true);
      
        System.out.println("THE APPLET HAS THE SOLVED VERSION OF THE MAZE THAT IS DISPLAYED HERE!");        
	}
}
