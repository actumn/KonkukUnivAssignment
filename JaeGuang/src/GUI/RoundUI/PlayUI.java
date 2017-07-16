package GUI.RoundUI;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import DisplayObject.DisplayComponent;
import GUI.UiManager;


public class PlayUI extends UiManager{
	
	private NameUI nameUi;
	private StatUI statUi;
	private Thread roundThread;

	public PlayUI(DisplayComponent dc){
		
		this.nameUi = new NameUI(dc);
		this.statUi = new StatUI(dc);
		
		this.setSize(640, 680);
		this.setTitle("드루와 드루와");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// ------------패널 선언----------------
		JPanel totalPanel = new JPanel();
		add(totalPanel);	//전체 패널
		totalPanel.setPreferredSize(new Dimension(830, 680));
		totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.X_AXIS));
		
		JPanel leftPanel = new JPanel();		
		totalPanel.add(leftPanel);	//왼쪽 전체 패널
		leftPanel.setPreferredSize(new Dimension(640, 680));
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		
		JPanel playPanel = new JPanel();
		leftPanel.add(playPanel);	//게임 플레이 패널
		playPanel.setPreferredSize(new Dimension(640, 640));
		playPanel.add(dc);
		
		dc.setPreferredSize(new Dimension(640, 640));
		
		playPanel.addKeyListener(dc.getCh());
		playPanel.setFocusable(true);
		playPanel.requestFocusInWindow();
		
		leftPanel.add(statUi);	//스텟UI 패널
		leftPanel.add(nameUi);	//레벨, 이름UI 패널
		
		
		
		roundThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					
					// 게임패널의 포커스 조절
					playPanel.setFocusable(true);
					playPanel.requestFocusInWindow();
					
					// 레벨 초기화
					nameUi.getLevelLabel().setText("" + dc.getCh().getLv());
					
					// 스탯 초기화
					statUi.getHpTextLabel().setText(dc.getCh().getHp() + " / " + dc.getCh().getDefaultHp());
					statUi.getMpTextLabel().setText(dc.getCh().getMp() + " / " + dc.getCh().getDefaultMp());
					statUi.getExpTextLabel().setText(dc.getCh().getExp() + " / " + dc.getCh().getDefaultExp());
					
					if (dc.getMonster().size() <= 0) uiChange = true;
					
					try {
						roundThread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		roundThread.start();

	}
	
	public Thread getThread() {
		return roundThread;
	}
	
}

