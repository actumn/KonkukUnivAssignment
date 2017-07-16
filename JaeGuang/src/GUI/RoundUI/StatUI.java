package GUI.RoundUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DisplayObject.DisplayComponent;

public class StatUI extends JPanel{
	
	private JPanel hpPanel; 
	private JPanel mpPanel; 
	private JPanel expPanel;
	
	private JLabel hpLabel;
	private JLabel hpTextLabel; 
	
	private JLabel mpLabel; 
	private JLabel mpTextLabel;
	
	private JLabel expLabel; 
	private JLabel expTextLabel;
	
	public StatUI(DisplayComponent dc){
		
		this.setPreferredSize(new Dimension(640, 40));
		
		this.hpPanel = new JPanel();
		this.mpPanel = new JPanel();
		this.expPanel = new JPanel();
		
		hpLabel = new JLabel("HP : ", JLabel.CENTER);
		hpTextLabel = new JLabel();
		mpLabel = new JLabel("MP: ", JLabel.CENTER);
		mpTextLabel = new JLabel();
		expLabel = new JLabel("EXP: ", JLabel.CENTER);
		expTextLabel = new JLabel();

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		//체력바
		this.add(hpPanel);
		hpPanel.setPreferredSize(new Dimension(210, 40));
		hpPanel.setBackground(Color.RED);
		
		hpPanel.add(hpLabel);
		hpLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
		hpLabel.setForeground(Color.white);
		hpPanel.add(hpTextLabel);
		hpTextLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
		hpTextLabel.setForeground(Color.white);
		
		//마력바
		add(mpPanel);
		mpPanel.setPreferredSize(new Dimension(210, 40));
		mpPanel.setBackground(Color.BLUE);

		mpPanel.add(mpLabel);
		mpLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
		mpLabel.setForeground(Color.white);
		mpPanel.add(mpTextLabel);
		mpTextLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
		mpTextLabel.setForeground(Color.white);		
		
		//EXP바
		add(expPanel);
		expPanel.setPreferredSize(new Dimension(220, 40));
		expPanel.setBackground(Color.GREEN);
		
		expPanel.add(expLabel);
		expLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
		expPanel.add(expTextLabel);
		expTextLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
		
	}

	public JLabel getHpTextLabel() {
		return hpTextLabel;
	}

	public JLabel getMpTextLabel() {
		return mpTextLabel;
	}

	public JLabel getExpTextLabel() {
		return expTextLabel;
	}
}
