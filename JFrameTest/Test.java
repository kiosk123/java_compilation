import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

class MyFrame extends JFrame{
	
	private JPanel displayPanel;
	private JPanel inputPanel;
	private JTextArea display;
	private JTextField input;
	
	public MyFrame(){
		this("No Title");
	}
	
	public MyFrame(String title){
		createFrame(title);
		
		displayPanel=new JPanel();
		displayPanel.setLayout(new FlowLayout());
		display=new JTextArea(15,40);
		displayPanel.add(display);
		this.add(displayPanel,BorderLayout.CENTER);
		
		inputPanel=new JPanel();
		inputPanel.setLayout(new FlowLayout());
		input=new JTextField(40);
		inputPanel.add(input);
		this.add(inputPanel, BorderLayout.SOUTH);
		
		this.pack();
	}
	
	private void createFrame(String title){
		this.setTitle(title);
		this.setSize(500,400);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}

public class Test {

	public static void main(String[] args) {
		new MyFrame();

	}

}
