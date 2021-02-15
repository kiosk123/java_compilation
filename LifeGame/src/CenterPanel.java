import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class CenterPanel extends JPanel implements MouseListener  {

	private LifeGame lg;
	private boolean map[][];
	private int mapState[][];
	private int mapX=GameSize.WIDTH; 
	private int mapY=GameSize.HEIGHT;
	
	public CenterPanel(LifeGame lg) {
		this.lg = lg;
		map=new boolean[GameSize.HEIGHT][GameSize.WIDTH];
		mapState=new int[GameSize.HEIGHT][GameSize.WIDTH];
		addMouseListener(this);
		Thread t1=new Thread(new DrawingLifeThread());
		Thread t2=new Thread(new ResetLifeThread());
		t1.start();
		t2.start();
	}
	
/////////////////////리셋 쓰레드///////////////////////////////////////////////////////////////////////////
private class ResetLifeThread implements Runnable
{

	@Override
	public void run() {
		
		while(true)
		{
			while(lg.isReset())
			{
				for(int y=0;y<mapY;y++)
				{
					for(int x=0;x<mapX;x++ )
					{
						map[y][x]=false;
					}
				}
				
				repaint();
				lg.setReset(false);
				
			}
						
		}		
	}	
}
//////////////// 생명그리기 쓰레드///////////////////////////////////////////////////////////////////////////
private class DrawingLifeThread implements Runnable
{

	@Override
	public void run() {
		while(true)
		{
			while(lg.isNextGen())
			{
				
				try {
					Thread.sleep(1000); //1초마다 월드 갱신
				} catch (Exception e) {
					// TODO: handle exception
				}
				makeNextGen();
				lg.setGen();
				repaint();
			}
		}
		
	}
	
}
	
/////////////////////////////패널에 그래픽 그리기/////////////////////////////////////////////////////////////
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		for(int y=0;y<mapY;y++)
		{
			for(int x=0;x<mapX;x++ )
			{
				if(map[y][x])
				{
					g.setColor(new Color(81,103,250));
					g.fill3DRect(x*GameSize.CELL_SIZE, y*GameSize.CELL_SIZE, GameSize.CELL_SIZE,GameSize.CELL_SIZE,true);
					
				}else {
					g.setColor(Color.BLACK);
					g.drawRect(x*GameSize.CELL_SIZE, y*GameSize.CELL_SIZE, GameSize.CELL_SIZE,GameSize.CELL_SIZE );
					
				}
			}
		}

	}
	
/////////////////////////세대 생성 함수////////////////////////////////////////////////////////////////////////
private void makeNextGen()
{
	for(int y=0;y<mapY;y++)
	{
		for(int x=0;x<mapX;x++ )
		{
			if(map[y][x])
			{
				mapState[y][x]=100;
			}
			else {
				
				mapState[y][x]=0;
			}
		}
	}
	
	for(int y=0;y<mapY;y++)
	{
		for(int x=0;x<mapX;x++ )
		{
			countLife(x, y);
		}
	}
	
	for(int y=0;y<mapY;y++)
	{
		for(int x=0;x<mapX;x++ )
		{
			switch (mapState[y][x]) {
			case 3:
			case 102:
			case 103:
				map[y][x]=true;
				break;
			default:
				map[y][x]=false;
				break;
			}
		}
	}
}
////////////////////////////////////생명 갯수 세는 함수////////////////////////////////////////////////////////////
private void countLife(int x, int y)
{
	for(int i=-1;i<=1;i++)
	{
		for(int j=-1;j<=1;j++)
		{
			if((i!=0)||(j!=0))
			{
				if((x+i>=0)&&(x+i<mapX)&&(y+j>=0)&&(y+j<mapY))
				{
					if(map[y+j][x+i])
					{
						mapState[y][x]++;
					}
				}
			}
		}
	}
	
}
	
////////////////////////마우스 이벤트 처리/////////////////////////////////////////////////////////////////////////


	@Override
	public void mouseClicked(MouseEvent e) {
		
		int x=e.getX()/GameSize.CELL_SIZE;
		int y=e.getY()/GameSize.CELL_SIZE;
		map[y][x]=!map[y][x];
		repaint();		
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
