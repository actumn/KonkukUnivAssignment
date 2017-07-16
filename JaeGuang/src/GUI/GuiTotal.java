package GUI;

import javax.swing.JButton;

import DisplayObject.DisplayComponent;
import GUI.RoundUI.PlayUI;
import GUI.StartUI.StartUI;

public class GuiTotal {
	
	public static final int roundUiFlag = 2;
	public static final int StoreUiFlag = 3;
	
	private UiManager uiManager;
	
	private JButton store;
	private JButton nextround;
	private DisplayComponent dc;
	
	private Thread uiThread;
	
	public GuiTotal() {

		this.dc = new DisplayComponent();
		
		this.uiManager = new StartUI(dc);
		this.uiManager.setVisible(true);
		
		this.uiThread = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				while (true) {
					
					if (uiManager.getUiChange()) {
						
						if(uiManager.getThread() != null) uiManager.getThread().stop();
						
						uiManager.setVisible(false);
						
						dc.makeMonster();
						
						uiManager = new PlayUI(dc);
						uiManager.setVisible(true);
						
						uiManager.setUiChange(false);
					}
					
					try {
						uiThread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		
		uiThread.start();
		
	}
}
