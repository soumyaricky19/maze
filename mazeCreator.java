package maze;

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class mazeCreator 
{
	private int length=0,n=0;
	char s[][];
	int c=0;
    private JButton b[][];
//    JComponent gui;
    JPanel gui;
	mazeCreator(int n)
	{
		this.n=n;
		this.length=2*n+1;
		s=new char[length][length];
		init();		
	}
	public void init()
	{
		gui = new JPanel();
		gui.setLayout(new GridLayout(length,length));
		b=new JButton[length][length];
		for (int i=0;i<length;i++)
		{
			
			for (int j=0;j<length;j++)
			{
			 	b[i][j]=new JButton();
				if (i%2 == 0)
				{	
					b[i][j].setBackground(Color.BLACK);
					b[i][j].setIcon(null);
				}
				else if (i%2 == 1)
				{
					if (j%2==0)
						b[i][j].setBackground(Color.BLACK);
					else 
						b[i][j].setBackground(Color.YELLOW);
				}				
			gui.add(b[i][j]);
			}
		}
		b[1][0].setBackground(Color.WHITE);
		b[length-2][length-1].setBackground(Color.WHITE);
	}
	
	public int genRandom(int x)
	{
		return (int)(java.lang.Math.random()*10*n*n)%x;
	}
	
	public void create()
	{
		int cell=0,wall=0,w=0,xCell=0,yCell=0,xWall=0,yWall=0,adjCell=0,count=0;
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
					b[xWall][yWall].setBackground(Color.WHITE);;
					d.union(d.find(cell),d.find(adjCell));		
					count++;
				}	
			}		
		}
	//	b[2*(cell/n+1)-1][2*(cell%n+1)-1].setBackground(Color.WHITE);;
 		System.out.println("Hint:"+d.find(0)+"..Yes:"+d.find(n*n-1));
 		System.out.println();
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
	public final JPanel getGui() 
	{
        return gui;
	}
	public static void main(String args[])
	{
		int n=0;
		Scanner s=new Scanner(System.in);
		System.out.println("Please enter the grid size: ");
		n=s.nextInt();		
		mazeCreator m=new mazeCreator(n);
		m.create();
//		m.display();
		JFrame f=new JFrame("Maze");
        f.add(m.getGui());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);
        // ensures the frame is the minimum size it needs to be
        // in order display the components within it
        f.pack();
        f.setVisible(true);
	}
}
