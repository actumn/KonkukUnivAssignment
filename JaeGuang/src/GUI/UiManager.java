package GUI;

import javax.swing.*;

import DisplayObject.DisplayComponent;

public abstract class UiManager extends JFrame {

	protected boolean uiChange;
	
	public Thread getThread() {
		return null;
	}

	public boolean getUiChange() {
		return uiChange;
	}

	public void setUiChange(boolean uiChange) {
		this.uiChange = uiChange;
	}

	
}
