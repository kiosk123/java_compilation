package lifegame_remake;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SouthPanel extends JPanel implements ActionListener{

	private JButton startBtn, stopBtn, resetBtn;
	private JLabel genNumLabel, genNameLabel;
	private LifeGame lg;

	public SouthPanel(LifeGame lg) {
		this.lg = lg;
		startBtn = new JButton("시작");
		stopBtn = new JButton("정지");
		resetBtn= new JButton("화면초기화");
		
		stopBtn.setEnabled(false);
		
		startBtn.addActionListener(this);
		stopBtn.addActionListener(this);
		resetBtn.addActionListener(this);
		
		
		genNumLabel = new JLabel();
		genNameLabel = new JLabel("세대");

		setLayout(new FlowLayout());

		setBackground(new Color(181, 120, 193));
		add(genNumLabel);
		add(genNameLabel);
		add(startBtn);
		add(stopBtn);
		add(resetBtn);
		
		Thread t=new Thread(new UpdateGenThread());
		t.start();
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==startBtn)
		{
			startBtn.setEnabled(false);
			resetBtn.setEnabled(false);
			stopBtn.setEnabled(true);
			lg.setNextGen(true);
		}
		
		if(e.getSource()==stopBtn)
		{
			startBtn.setEnabled(true);
			resetBtn.setEnabled(true);
			stopBtn.setEnabled(false);
			lg.setNextGen(false);
		}
		
		if(e.getSource()==resetBtn)
		{
			startBtn.setEnabled(true);
			stopBtn.setEnabled(false);
			lg.setNextGen(false);
			lg.setGen(1);
			genNumLabel.setText(""+lg.getGen());
			lg.setReset(true);
			
		}
			
	}



	private class UpdateGenThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				while (lg.isNextGen()) {
					
					genNumLabel.setText(""+lg.getGen());

				}
			}

		}

	}

}
