package GUI.StartUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import DisplayObject.DisplayComponent;
import GUI.UiManager;

public class StartUI extends UiManager{

	private JPanel startPanel;
	private JTextField nameField;
	private JLabel nameLabel;
	private JButton startButton;
	private Image im;

	public StartUI(DisplayComponent dc){
		
		this.im = Toolkit.getDefaultToolkit().getImage("image/background/startUI/evilCastle2.png");
		this.setSize(830,680);
		this.setTitle("드루와 드루와");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.startPanel = new JPanel(){
			public void paint(Graphics g) {
				g.drawImage(im, 0, 0,830,680, this);
			}
		};
		this.add(startPanel);	//전체 패널
		startPanel.setLayout(null);

		this.nameLabel = new JLabel("이름   : ");
		startPanel.add(nameLabel);
		nameLabel.setBounds(270, 400, 50, 30);
		
		this.nameField = new JTextField();
		startPanel.add(nameField);
		nameField.setBounds(320, 400 , 230, 30);
		nameField.setDocument(new JTextFieldLimit(9));

		this.startButton = new JButton("시작하기");
		startButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				dc.getCh().setName(nameField.getText());
				uiChange = true;
				System.out.println(uiChange);
			}
		});
		
		startPanel.add(startButton);
		startButton.setBounds(365, 450, 100, 50);
		
		
	}

	

	private class JTextFieldLimit extends PlainDocument {
		  private int limit;
		  JTextFieldLimit(int limit) {
		    super();
		    this.limit = limit;
		  }

		  JTextFieldLimit(int limit, boolean upper) {
		    super();
		    this.limit = limit;
		  }

		  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		    if (str == null)
		      return;

		    if ((getLength() + str.length()) <= limit) {
		      super.insertString(offset, str, attr);
		    }
		  }
		}

	
	
}