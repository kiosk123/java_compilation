import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

class MyFrame extends JFrame implements ActionListener{
	
	JTextArea display;
	JTextField input;
	
	
	 public MyFrame(){
		 this.setSize(500,400);
		 this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		 this.setVisible(true);
		 
		 this.setLayout(new FlowLayout());
		 
		 display=new JTextArea(15,40);
		 input=new JTextField(40);
		 
		 input.addActionListener(this);
		 JScrollPane scroll=new JScrollPane(display);
		 display.setCaretPosition(display.getDocument().getLength());
		 this.add(scroll);
		 this.add(input);
		 
		 this.setResizable(false);
		 this.setVisible(true);
	 }


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==input){
			display.append(input.getText()+"\n");
			
			//^는 정규식 시작을 $은 정규식 끝을 나타냄
			String regex="^01[016789]-\\d{4}-\\d{4}$";
			
			if(Pattern.matches(regex, input.getText())==false)
			{
				display.append("휴대전화 패턴이 잘못됨 \n");
			}
			
			input.selectAll();
			
		}		
	}	 
 }


public class Test {

	public static void main(String[] args) {
		
		new MyFrame();

	}
}
