package maze;

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class mazeCreator 
{
	private int length=0,n=0;	
	char s[][];
    private JButton b1[][],b2[][];
    JPanel gui1,gui2;
	mazeCreator(int n)
	{
		this.n=n;
		this.length=2*n+1;
		s=new char[length][length];
		init();		
	}
	
	public void init()
	{
		gui1 = new JPanel();
		gui1.setLayout(new GridLayout(length,length));
		b1=new JButton[length][length];
		for (int i=0;i<length;i++)
		{			
			for (int j=0;j<length;j++)
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
		b1[length-2][length-1].setBackground(Color.WHITE);
		s[length-2][length-1]=' ';
	}
	
	public int genRandom(int x)
	{
		return (int)(java.lang.Math.random()*10*n*n)%x;
	}
	
	public void create()
	{
		int cell=0,w=0,xCell=0,yCell=0,xWall=0,yWall=0,adjCell=0,count=0;
		DisjSets d=new DisjSets(n*n);
 		while (count != n*n-1)
		{			
			cell=genRandom(n*n);
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
						
			if ((xWall != 2*n && yWall != 2*n) && (xWall != 0 && yWall != 0))
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
		cell=length;
		java.util.LinkedList<Integer> q=new java.util.LinkedList<Integer>();
		q.add(cell);
		while(cell != length*length-1-length)
		{
			xCell=cell/length;
			yCell=cell%length;
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
					q.add(cell+length);
					cell=cell+length;
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
					q.add(cell-length);
					cell=cell-length;
					continue;
				}

					s[xCell][yCell]='1';
					q.removeLast();
					cell=q.peekLast();
					xCell=cell/length;
					yCell=cell%length;
					s[xCell][yCell]=' ';
		}
		
		
		gui2 = new JPanel();
		gui2.setLayout(new GridLayout(length,length));
		b2=b1;
		while(!q.isEmpty())
		{
			cell=q.removeFirst();
			xCell=cell/length;
			yCell=cell%length;
			b2[xCell][yCell].setBackground(Color.BLUE);
		}
		for (int i=0;i<length;i++)			
			for (int j=0;j<length;j++)
				gui2.add(b2[i][j]);
			
	}

	public void display()
	{
		for (int i=0;i<length;i++)
		{
			for (int j=0;j<length;j++)
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
		int n=0;
		Scanner sc=new Scanner(System.in);
		System.out.println("Please enter the grid size: ");
		n=sc.nextInt();		
		sc.close();
		mazeCreator m=new mazeCreator(n);

		m.create();
		m.display();

        m.solve();
        JFrame f2=new JFrame("Maze2");
        f2.add(m.getGui2());
        f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f2.pack();
        f2.setVisible(true);
      
        System.out.println("THE APPLET HAS THE SOLVED VERSION OF THE MAZE THAT IS DISPLAYED HERE!");        
	}
}
