import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

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
	private URL myURL;
	
	public MyFrame(){
		this("No Title");
	}
	
	public MyFrame(String title){
		createFrame(title);
		
		Font font=new Font("Serif", Font.BOLD, 20);
		
		displayPanel=new JPanel();
		displayPanel.setLayout(new FlowLayout());
		display=new JTextArea(15,50);
		display.setEditable(false);
		JScrollPane scroll=new JScrollPane(display);
		/*
		 * URL 처리
		 */
		try {
			myURL=new URL("http://media.daum.net/politics/president/newsview?newsid=20160804124809229");
			
			display.append("protocol = "+myURL.getProtocol()+"\n");
			display.append("host = "+myURL.getHost()+"\n");
			display.append("port = "+myURL.getDefaultPort()+"\n");
			display.append("path = "+myURL.getPath()+"\n");
			display.append("query = "+myURL.getQuery()+"\n");
		} catch (MalformedURLException e) {
			display.append("ERROR : "+e.getMessage()+"\n");
		}
	
		
		
		
		
		displayPanel.add(scroll);
		
		this.add(displayPanel,BorderLayout.CENTER);
		
		inputPanel=new JPanel();
		inputPanel.setLayout(new FlowLayout());
		input=new JTextField(40);
		input.setFont(font);
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
