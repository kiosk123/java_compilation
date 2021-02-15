import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

class MyFrame extends JFrame implements ActionListener{
	
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
		display=new JTextArea(15,50);
		display.setEditable(false);
		JScrollPane scroll=new JScrollPane(display);
		displayPanel.add(scroll);
		
		this.add(displayPanel,BorderLayout.CENTER);
		
		inputPanel=new JPanel();
		inputPanel.setLayout(new FlowLayout());
		input=new JTextField(40);
		
		input.addActionListener(this);
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==input){
			display.append(input.getText()+"\n");
			input.selectAll();
		}		
	}	
}

public class Test {

	public static void main(String[] args) {
		
		  try {
	            // Set System L&F
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        } catch (UnsupportedLookAndFeelException e) {
	            // handle exception
	        } catch (ClassNotFoundException e) {
	            // handle exception
	        } catch (InstantiationException e) {
	            // handle exception
	        } catch (IllegalAccessException e) {
	            // handle exception
	        }
		new MyFrame();

	}

}
