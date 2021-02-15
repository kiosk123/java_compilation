import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import java.io.*;

public class Memo {

	private JTextArea text;

	public Memo() {
		// 텍스트필드
		text = new JTextArea();

		// 메모장 스크롤을 위한 컴포넌트를 생성하고 생성자 파라미터로 텍스트필드를 넘김
		JScrollPane scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JFrame frm = new JFrame("메모장");

		// 메뉴바
		JMenuBar menuBar = new JMenuBar();

		// 파일메뉴
		JMenu menu = new JMenu("파일");

		// 파일메뉴에 들어가는 아이템들
		JMenuItem fileOpen = new JMenuItem("파일열기");
		JMenuItem fileSave = new JMenuItem("파일저장");
		JMenuItem memoClose = new JMenuItem("종료");

		// 각 파일메뉴의 아이템들에 이벤트 리스너를 추가한다.
		fileOpen.addActionListener(new FileOpenEvent());
		fileSave.addActionListener(new FileSaveEvent());
		memoClose.addActionListener(new FileCloseEvent());

		// 파일메뉴에 아이템들 추가
		menu.add(fileOpen);
		menu.add(fileSave);
		menu.add(memoClose);

		// 파일메뉴 메뉴바에 추가
		menuBar.add(menu);

		// 프레임에 메뉴바 추가
		frm.setJMenuBar(menuBar);

		// 레이아웃은 보더레이아웃으로 설정
		frm.setLayout(new BorderLayout());

		// 스크롤판넬을 프레임의 가운데에 붙인다.
		frm.add(scroll, BorderLayout.CENTER);

		// 프레임의 크기및 위치 설정
		frm.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 4,
				Toolkit.getDefaultToolkit().getScreenSize().height / 4, 700, 500);

		// 프레임을 기본종료 방식 설정
		frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frm.setVisible(true);
	}

	private class FileOpenEvent implements ActionListener // 파일 오픈 이벤트
	{
		@Override
		public void actionPerformed(ActionEvent e) {

			FileRead read = new FileRead();

		}

	}

	private class FileSaveEvent implements ActionListener // 파일 저장 이벤트
	{
		@Override
		public void actionPerformed(ActionEvent e) {

			FileSave save = new FileSave();
		}

	}

	private class FileCloseEvent implements ActionListener // 메모장 종료 이벤트
	{
		@Override
		public void actionPerformed(ActionEvent e) {

			System.exit(0);

		}

	}

	private class FileRead {
		private FileRead() {

			// 문자를 읽고 가공하기 위한 스트링 빌더
			StringBuilder sb = new StringBuilder();

			// 파일열기 창을 위한 프레임
			JFrame window = new JFrame("파일열기");

			// 파일추서를 이용하여 파일을 선택
			JFileChooser fileChooser = new JFileChooser();

			// 텍스트 확장자 필터만든다
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");

			// 모든파일선확장자 옵션제거
			fileChooser.setAcceptAllFileFilterUsed(false);

			// 파일추서에 텍스트 확장자 필터를 추가
			fileChooser.addChoosableFileFilter(filter);

			// 파일다이얼 로그 오픈시 홈화면은 바탕화면으로
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "//" + "Desktop"));

			// 파일오픈을 위한 다이얼로그
			int result = fileChooser.showOpenDialog(window);

			// 정상적으로 파일열기 수행되었을때
			if (result == JFileChooser.APPROVE_OPTION) {

				// 선택된 파일경로를 읽어들여서
				File selectedFile = fileChooser.getSelectedFile();
				try {

					// 파일 입력실행
					BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile)));

					while (true) {
						String str = in.readLine();
						if (str == null) {
							break;
						}
						sb.append(str + "\n");
					}
					text.setText(sb.toString());
					in.close();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}

			}

		}
	}

	private class FileSave {
		private FileSave() {

			// 텍스트 필드 줄바꿈을 출력을 수월하게 출력하기 위한 스캐너
			Scanner sc = new Scanner(text.getText());
			JFrame window = new JFrame("파일저장");
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");

			// 모든파일 확장자 옵션제거
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.addChoosableFileFilter(filter);
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "//" + "Desktop"));

			// 파일저장을 위한 다이얼로그
			int result = fileChooser.showSaveDialog(window);
			BufferedWriter out = null;

			// 정상적으로 파일열기 수행되었을때
			if (result == JFileChooser.APPROVE_OPTION) {

				try {
					out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
							fileChooser.getSelectedFile() + fileChooser.getFileFilter().getDescription())));

					while (true) {
						String str = sc.nextLine();
						if (str == null) {
							break;
						}
						out.write(str + "\r\n");
						out.flush();
					}
					out.close();

				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}

			}

		}
	}

}
