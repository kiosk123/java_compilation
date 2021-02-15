import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

class MyFrame extends JFrame implements ActionListener {

	private JPanel displayPanel;
	private JPanel inputPanel;
	private JTextArea display;
	private JTextField input;
	private URL myURL;
	private String[] charset={"UTF-8","EUC-KR"};
	private JComboBox charsetList;

	public MyFrame() {
		this("No Title");
	}

	public MyFrame(String title) {
		createFrame(title);

		Font font = new Font("Serif", Font.BOLD, 20);

		/*
		 * HTML 리더
		 * http://itempage3.auction.co.kr/DetailView.aspx?itemno=B347459361
		 */
		
		this.add(createPanel(), BorderLayout.CENTER);

		inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());
		input = new JTextField(20);
		input.setFont(font);
		input.addActionListener(this);

		inputPanel.add(input);
		inputPanel.add(CreateComboBox());
		this.add(inputPanel, BorderLayout.SOUTH);

		this.pack();
				
	}

	public JPanel createPanel() {
		displayPanel = new JPanel();
		displayPanel.setLayout(new FlowLayout());
		display = new JTextArea(15, 50);
		display.setEditable(false);
		JScrollPane scroll = new JScrollPane(display);
		displayPanel.add(scroll);
		return displayPanel;
	}

	private void createFrame(String title) {
		this.setTitle(title);
		this.setSize(500, 400);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private JComboBox CreateComboBox(){
		charsetList=new JComboBox(charset); 
		return charsetList;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == input) {
			display.setText("");
			try {
				System.out.println(input.getText());
				URL myURL=new URL(input.getText());
				String cs=charsetList.getSelectedItem().toString();
				BufferedReader in=new BufferedReader(new InputStreamReader(myURL.openStream(),cs));
				String line;
				String linecount="";
				int count=0;
				
				
				
				while((line=in.readLine())!=null){
					
					count++;
					linecount=String.format("%4d : ", count);
					display.append(linecount+line+"\n");
				}
				
			} catch (Exception exp) {
				display.append("Error : "+exp.getMessage()+"\n");
			}
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
