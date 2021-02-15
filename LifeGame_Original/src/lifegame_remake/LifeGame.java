package lifegame_remake;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class LifeGame extends JFrame {

	private volatile int gen=1;
	private volatile boolean nextGen=false;
	private volatile boolean reset=false;
	private int xPos=Toolkit.getDefaultToolkit().getScreenSize().width/20;
	private int yPos=Toolkit.getDefaultToolkit().getScreenSize().height/50;
	
	public LifeGame()
	{
		super("생명게임");
		
		setLayout(new BorderLayout());
		add(new CenterPanel(this),BorderLayout.CENTER);
		add(new SouthPanel(this),BorderLayout.SOUTH);
		setBounds(xPos, yPos, GameSize.WIDTH*GameSize.CELL_SIZE+8, GameSize.HEIGHT*GameSize.CELL_SIZE+63);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
	}

	public int getGen() {
		return gen;
	}

	public void setGen() {
		this.gen++;
	}
	
	public void setGen(int val) {
		this.gen=val;
	}

	public boolean isNextGen() {
		return nextGen;
	}

	public void setNextGen(boolean nextGen) {
		this.nextGen = nextGen;
	}

	public boolean isReset() {
		return reset;
	}

	public void setReset(boolean reset) {
		this.reset = reset;
	}
	
	
}
