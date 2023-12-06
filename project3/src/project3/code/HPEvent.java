package project3.code;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HPEvent {
	
	JFrame searchExerciseNameFrame;
	JFrame enterSearchExerciseNameFrame;
	JFrame addExerciseNameFrame;
	
	HPGUI hpGUI;
	String exerciseName;
	String record;
	FileReader fr;
	FileWriter fw;
	BufferedReader br;
	BufferedWriter bwEach;
	BufferedWriter bwAll;
	String currentDateString;
	String healthFolderPath;
	String dailyFolderPath;
	String textContent;
	String individualPath;
	String noDataAlert;
	
	Path path;
	int fileNumber;
	
	File directory;
	
    public HPEvent(HPGUI hpGUI) {
        this.hpGUI = hpGUI;
    }

    public void event() {
    	
    	healthFolderPath = "C:/data/health/";
    	dailyFolderPath = healthFolderPath+"daily/";
    	noDataAlert = "데이터가 없습니다.";

        hpGUI.btnList.get(0).jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDataPanel();
                
                // 검색을 눌렀을 때 나오는 새로운 GUI창
                searchExerciseNameFrame = new JFrame("검색창");
                searchExerciseNameFrame.setBounds(350, 450, 250, 120);
                searchExerciseNameFrame.setLayout(null);
                JPanel searchExerciseNamePanel = new JPanel(new GridLayout(1, 1));
                searchExerciseNamePanel.setBounds(30, 20, 170, 40);
                JTextField searchExerciseNameTextField = new JTextField("운동이름을 적어주세요.");
                
                searchExerciseNameTextField.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                    	
                    	searchExerciseNameTextField.setText("");
                    }
                });
               
                /* 	io - file - mkdir(): Creates the directory named by this abstract pathname.
                 *  폴더가 없으면 새로운 폴더 생성*/
                
                searchExerciseNameTextField.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						exerciseName = searchExerciseNameTextField.getText();
						hpGUI.searchLabel.setText(exerciseName);
						hpGUI.currentDataNameTextField.setText(exerciseName);
						
						individualPath = (healthFolderPath+exerciseName);
						directory = new File(individualPath);
							
						if (!directory.exists()) {
							enterSearchExerciseNameFrame = new JFrame("알림");
							enterSearchExerciseNameFrame.setBounds(520,520, 300,150);
							enterSearchExerciseNameFrame.setLayout(null);
		  		            JLabel enterSearchExerciseNameLabel = new JLabel("입력하신 운동이름을 확인해주세요");
		  		            enterSearchExerciseNameLabel.setBounds(40, 20, 230, 50);
		  		                
		  		            enterSearchExerciseNameFrame.add(enterSearchExerciseNameLabel);
		  		            enterSearchExerciseNameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		  		            enterSearchExerciseNameFrame.setVisible(true);
		  		                
		  		            for(int i=0; i<(hpGUI.pastDataNumber); i++) {
		  		            	hpGUI.pastDataDateField[i].setText(noDataAlert);
		  		               	hpGUI.pastDataRecordField[i].setText(noDataAlert);
		  		            }
						}
						else {
							showPastDataText();
							searchExerciseNameFrame.dispose();
							// 엔터 누르면 texxtfield가 있는 frame 꺼짐
					        
							} // else
						}
				}); // sortNameTextField actionlistener

                searchExerciseNameFrame.add(searchExerciseNamePanel);
                searchExerciseNamePanel.add(searchExerciseNameTextField);
                searchExerciseNameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                searchExerciseNameFrame.setVisible(true);
            }
        }); // btnList.get(0) actionlistener

        hpGUI.btnList.get(1).jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	exerciseName = hpGUI.currentDataNameTextField.getText();
            	individualPath = (healthFolderPath+exerciseName);
				directory = new File(individualPath);
            	
            	try {
            		
					bwEach = new BufferedWriter(new FileWriter(healthFolderPath+hpGUI.searchLabel.getText()+"/"
															+hpGUI.currentDataDateTextField.getText()+".txt"));
					bwEach.write(hpGUI.currentDataRecordTextField.getText());
					bwEach.flush();
					
					showPastDataText();
					
					bwAll = new BufferedWriter(new FileWriter(dailyFolderPath+hpGUI.currentDataDateTextField.getText()+".txt",true));
					bwAll.newLine();
					bwAll.write(" * " + hpGUI.currentDataNameTextField.getText());
					bwAll.newLine();
					bwAll.write(hpGUI.currentDataRecordTextField.getText());
					bwAll.newLine(); bwAll.newLine(); bwAll.newLine();
					bwAll.flush();
					
				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				} finally {
					 try {
						 bwEach.close();
						 bwAll.close();
					 } catch (IOException ioe) {
						 ioe.printStackTrace();
					 }
				 }
            } // btnList.get(1).jButton event
        });

        hpGUI.btnList.get(2).jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRecordPanel();
                hpGUI.todayRecordTextArea.setText("");
                
                try {
                	br = new BufferedReader(new FileReader(dailyFolderPath + hpGUI.currentDateString +".txt"));
                	while (true) {
                		textContent = br.readLine();
    	            	if (textContent == null) break;
    	            	hpGUI.todayRecordTextArea.append(textContent+"\n");
                	}
                	
				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				} finally {
					try {
						br.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
            }
        });

        hpGUI.btnList.get(3).jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
//            	if (searchExerciseNameFrame.isVisible()) {
//            		searchExerciseNameFrame.dispose();
//            	}
            	
            	
            	addExerciseNameFrame = new JFrame("새 폴더 이름");
            	addExerciseNameFrame.setBounds(350, 450, 250, 120);
            	addExerciseNameFrame.setLayout(null);
                JPanel addExerciseNamePanel = new JPanel(new GridLayout(1, 1));
                addExerciseNamePanel.setBounds(30, 20, 170, 40);
                
                JTextField addExerciseNameTextField = new JTextField();
                
                addExerciseNameFrame.add(addExerciseNamePanel);
                addExerciseNamePanel.add(addExerciseNameTextField);
                addExerciseNameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                addExerciseNameFrame.setVisible(true);
                
        		addExerciseNameTextField.addActionListener(new ActionListener() {
					
    						@Override
    						public void actionPerformed(ActionEvent e) {
    			            	try {
    			                    
    			            		Path folderPath;
    								Files.createDirectories(folderPath = Paths.get(healthFolderPath+addExerciseNameTextField.getText()));
    							} catch (IOException ioe) {
    								ioe.printStackTrace();
    							}
    			        		addExerciseNameFrame.dispose();
    						}
    					});
            }
        });
        
        hpGUI.btnList.get(4).jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	showFindFilePanel();
            	
                JFileChooser fc = new JFileChooser(dailyFolderPath);

                int result = fc.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String selectedFilePath = fc.getSelectedFile().getAbsolutePath();
                    
                    hpGUI.findFileDirectoryLabel.setText(fc.getName(new File(selectedFilePath)));
                    hpGUI.findFileContentTextArea.setText("");
                    
                    try {
						br = new BufferedReader(new FileReader(selectedFilePath));
						
						while (true) {
	                		textContent = br.readLine();
	    	            	if (textContent == null) break;
	    	            	hpGUI.findFileContentTextArea.append(textContent+"\n");
	                	}
					} catch (FileNotFoundException fnfe) {
						fnfe.printStackTrace();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					} finally {
						try {
							br.close();
						} catch (IOException ioe){
							ioe.printStackTrace();
						}
					}
                } // result if
                
                
            } // btnList[4] actionlistener
        });
    }
    
    public void showDataPanel() {	
        hpGUI.labelImage.setVisible(false);
        hpGUI.recordPanel.setVisible(false);
        hpGUI.dataPanel.setVisible(true);
        hpGUI.findFilePanel.setVisible(false);
    }

    public void showRecordPanel() {
        hpGUI.labelImage.setVisible(false);
        hpGUI.recordPanel.setVisible(true);
        hpGUI.dataPanel.setVisible(false);
        hpGUI.findFilePanel.setVisible(false);
    }
    
    public void showFindFilePanel() {
    	hpGUI.labelImage.setVisible(false);
        hpGUI.recordPanel.setVisible(false);
        hpGUI.dataPanel.setVisible(false);
    	hpGUI.findFilePanel.setVisible(true);
    }
    
    public void showPastDataText() {
    	File[] textList = directory.listFiles(new HPTextFileFilter());
		Arrays.sort(textList, Comparator.comparing(File::getName).reversed());
			
		fileNumber = 0;
        for (File textFile : textList) {
        	if (fileNumber >= hpGUI.pastDataNumber) {
        		break;
        	}
        	try {
        		br = new BufferedReader(new FileReader(textFile));
		        textContent = br.readLine();
//		        if (textContent == null) break;
	         
	            hpGUI.pastDataDateField[(hpGUI.pastDataNumber-1)-fileNumber].setText(textFile.getName());
	    		hpGUI.pastDataRecordField[(hpGUI.pastDataNumber-1)-fileNumber].setText(textContent);
	    		fileNumber++;
	    	} catch (IOException ioe) {
	    		ioe.printStackTrace();
	    	} finally {
	    		try {
	    			br.close();
	    		} catch (IOException ioe){
	    			ioe.printStackTrace();
	    		}
	          }
	        }
    }
    
}