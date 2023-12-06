package project3.code;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class HPGUI extends JFrame {
	
	Scanner sc = new Scanner(System.in);
	
	List<HPButton> btnList;
	HPEvent hpEvent;
	
	JPanel firstPanel;
	JPanel dataPanel;
	JPanel pastDataPanel;
	JPanel recordPanel;
	JPanel buttonPanel;
	JPanel todayPanel;
	JPanel currentDataPanel;
	JPanel findFilePanel;
	JPanel findFileContentPanel;
	
	JLabel searchLabel;
	JLabel labelImage;
	JLabel findFileDirectoryLabel;
	File fileImage;
	BufferedImage bufferedImage;
	ImageIcon imageIcon;
	
	JTextField[] pastDataDateField;
	JTextField[] pastDataRecordField;
	JLabel todayDateLabel;
	JTextField todayNameTextField;
	JTextArea todayRecordTextArea;
	JTextField currentDataDateTextField;
	JTextField currentDataNameTextField;
	JTextField currentDataRecordTextField;
	JTextArea findFileContentTextArea;
	
	String currentDateString;
	String noDataAlert;
	
	
	int sortNumber;
	int pastDataNumber=5;
	
	HPGUI(){
		
		init();
	}
	
	void init() {
		
		this.setTitle("Health Program");
		this.setBounds(0, 0, 700, 730);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		// GridLayout, BorderLayout은 각 각의 크기를 조절하는법을 몰라 null로 두고 각각의 크기 조절함
		// -> Grid
		
		currentDateString = LocalDate.now().toString();
		noDataAlert = "데이터가 없습니다.";
		
		
		/* main 실행했을 때 처음 나오는 GUI창 */
		labelImage = new JLabel();
		fileImage = new File("C:/data/health.png");
		
		try {
			bufferedImage = ImageIO.read(fileImage);
			labelImage = new JLabel(new ImageIcon(bufferedImage));
			labelImage.setBounds(10, 10, 650, 600);
			labelImage.getIcon();
			this.getContentPane().add(labelImage);
			
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		labelImage.setVisible(true);
		
		
		/* 검색으로 운동종목을 설정했을 때 나오는 GUI창 */
		dataPanel = new JPanel(null);
		dataPanel.setBounds(0,0,700,600);
		this.add(dataPanel);
		
		searchLabel = new JLabel("운동이름");
		searchLabel.setBounds(20,0,700,50);
		dataPanel.add(searchLabel);
		pastDataDateField = new JTextField[5];
		pastDataRecordField = new JTextField[5];
		
		for(int i=0; i<pastDataNumber; i++) {
			pastDataPanel = new JPanel (new GridLayout(2, 1));
			pastDataPanel.setBounds(10,55+80*(i),665,70);
			pastDataDateField[i] = new JTextField(noDataAlert);
			pastDataRecordField[i] = new JTextField(noDataAlert);
			
			dataPanel.add(pastDataPanel);
			pastDataPanel.add(pastDataDateField[i]);
			pastDataPanel.add(pastDataRecordField[i]);
			
			pastDataDateField[i].setEditable(false);
			pastDataRecordField[i].setEditable(false);
			
		}
		
		currentDataPanel = new JPanel (new GridLayout(3, 1));
		currentDataPanel.setBounds(10,480,665,100);
		
		currentDataDateTextField = new JTextField(currentDateString);
		
		currentDataNameTextField = new JTextField();
		currentDataRecordTextField = new JTextField();
		
		dataPanel.add(currentDataPanel);
		currentDataPanel.add(currentDataDateTextField);
		currentDataPanel.add(currentDataNameTextField);
		currentDataPanel.add(currentDataRecordTextField);
		
		dataPanel.setVisible(false);
		
		
		
		/* 오늘을 눌렀을 떄 나오는 오늘 수행한 운동종목 종료와 기록 */
		recordPanel = new JPanel(null);
		recordPanel.setBounds(0,0,700,600);
		this.add(recordPanel);

		todayDateLabel = new JLabel(currentDateString);
		todayDateLabel.setBounds(20,5,700,50);
		recordPanel.add(todayDateLabel);
		
		todayPanel = new JPanel (new GridLayout(1,1));
		todayPanel.setBounds(20,50,650,550);
		todayRecordTextArea = new JTextArea();
		JScrollPane todayRecordTextScroll = new JScrollPane(todayRecordTextArea);
		todayPanel.add(todayRecordTextScroll);
		
		recordPanel.add(todayPanel);
		
		todayRecordTextArea.setEditable(false);
		recordPanel.setVisible(false);
		
		/* 찾기를 눌렀을 때 파일경로 보여주기 */
		findFilePanel = new JPanel(null);
		findFilePanel.setBounds(0,0,700,600);
		this.add(findFilePanel);
		findFileDirectoryLabel = new JLabel(currentDateString);
		findFileDirectoryLabel.setBounds(20,5,700,50);
		findFilePanel.add(findFileDirectoryLabel);
		findFileContentPanel = new JPanel (new GridLayout(1,1));
		findFileContentPanel.setBounds(20,50,650,550);
		findFileContentTextArea = new JTextArea();
		JScrollPane findFileScroll = new JScrollPane(findFileContentTextArea);
		findFileContentPanel.add(findFileScroll);
		
		findFilePanel.add(findFileContentPanel);
		
		findFileContentTextArea.setEditable(true);
		findFilePanel.setVisible(false);
		
		/* GUI창 하단의 버튼 */
		buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setBounds(10,630,700,100);
		
		this.add(buttonPanel);
		
		btnList = new ArrayList<HPButton>();
		btnList.add(new HPButton(new JButton("검색")));
		btnList.add(new HPButton(new JButton("입력")));
		btnList.add(new HPButton(new JButton("오늘")));
		btnList.add(new HPButton(new JButton("추가")));
		btnList.add(new HPButton(new JButton("찾기")));

		for (HPButton hpButton : btnList) {
			buttonPanel.add(hpButton.jButton);
		}

		hpEvent = new HPEvent(this);
		hpEvent.event();
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	public void setButtonAction(JButton button, Runnable action) {
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action.run();
			}
		});
	}
	
} // class

