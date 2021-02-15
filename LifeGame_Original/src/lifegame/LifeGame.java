package lifegame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class LifeGame extends JFrame implements ActionListener, MouseListener {

	private int XPOS_FRAME = Toolkit.getDefaultToolkit().getScreenSize().width / 4;
	private int YPOS_FRAME = Toolkit.getDefaultToolkit().getScreenSize().height / 3;
	private final static int maxX = 40;// 월드의 가로축 크기
	private final static int maxY = 20;// 월드의 세로축 크기
	private boolean map[][];
	private int mapState[][]; //생명이 없는 칸은 주위생명의 갯수를 저장 생명이 존재하는 칸은 주위 생명의 개수+100 2,102,103의 경우만 해당원소 true
	private int gen=1; // 현재 몇 세대 인지
	private JPanel controlPanel, mainPanel;
	private JButton startBtn, stopBtn;
	private JLabel genLabel;
	private boolean nextGen = false;

	public LifeGame() {
		super("생명게임");
		
		map=new boolean[maxX][maxY];
		mapState=new int[maxX][maxY];
		controlPanel = new JPanel();
		
		mainPanel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				for(int x=0; x<maxX;x++)
				{
					for(int y=0; y<maxY;y++)
					{
						if(map[x][y])//논리값에 따라 생명을 그린다.
						{
							g.fillRect(x*10, y*10, 10, 10);
						}else {
							g.drawRect(x*10, y*10, 10, 10);
						}
					}
				}
			}
			
		};

		genLabel = new JLabel(gen + "세대");
		startBtn = new JButton("Start");
		stopBtn = new JButton("Stop");

		controlPanel.add(genLabel);
		controlPanel.add(startBtn);
		controlPanel.add(stopBtn);
		controlPanel.setBackground(Color.green);
		;

		mainPanel.addMouseListener(this);
		startBtn.addActionListener(this);
		stopBtn.addActionListener(this);

		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		setBounds(XPOS_FRAME, YPOS_FRAME, 402, 235);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
		Thread t=new Thread(new LifeThread());
		t.start();
	}

	
	private void makeNextGen()
	{
		//mapState를 set한다.
		for(int x=0; x<maxX;x++)
		{
			for(int y=0; y<maxY;y++)
			{
				if(map[x][y])//논리값에 따라 생명을 그린다.
				{
					mapState[x][y]=100;
				}else {
					mapState[x][y]=0;
				}
			}
		}
		//countLife
		for(int x=0; x<maxX;x++)
		{
			for(int y=0; y<maxY;y++)
			{
				countLife(x,y);
			}
		}
		
		//다음 세대
		for(int x=0; x<maxX;x++)
		{
			for(int y=0; y<maxY;y++)
			{
				switch(mapState[x][y])
				{
				   case 3:
				   case 102:
				   case 103:
					   map[x][y]=true;
					   break;
				   default:
					   map[x][y]=false;
					   break;
					   
				}
			}
		}
		
		
	}
	
	private void countLife(int x, int y)
	{
		for(int i=-1;i<=1;i++)
		{
			for(int j=-1;j<=1;j++)
			{
				if((i!=0)||(j!=0))//not(AandB)->드모르강 법칙에 의해 notA or notB가 되는 규칙
				{
					if((x+i>=0)&&(x+i<maxX)&&(y+j>=0)&&(y+j<maxY))
					{
						if(map[x+i][y+j])
						{
							mapState[x][y]++; //자신을 기준으로 주위 8블록의 생명갯수를 조사함
						}
					}
				}
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == startBtn) {
			nextGen = true;
			startBtn.setEnabled(false);
			stopBtn.setEnabled(true);
		}

		if (e.getSource() == stopBtn) {
			nextGen = false;
			startBtn.setEnabled(true);
			stopBtn.setEnabled(false);
		}
	}

	// //////////////////////////////마우스 이벤트// 처리부/////////////////////////////////////////////////////////////////////
	@Override
	public void mouseClicked(MouseEvent e) {

		int mouseX = e.getX();
		int mouseY = e.getY();
		map[mouseX / 10][mouseY / 10] = !map[mouseX / 10][mouseY / 10]; // 마우스로 맵클릭시 true면  false->false면 true로  상태변화
		mainPanel.repaint();

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

	// ////////////////////////////////////////////생명 쓰레드////////////////////////////////////////////////////////////////
	private class LifeThread implements Runnable {
		public void run() {

			while (true) {
				try {
					Thread.sleep(1000);// 1초에 한번씩 월드 갱신
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				if(nextGen)
				{
					makeNextGen();
					gen++;
					genLabel.setText(gen+"세대");
					repaint();
				}
			}

		}
	}

}
