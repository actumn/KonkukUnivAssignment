package DisplayObject;

public interface Movable {
	
	public static final int CTRL = 17;
	public static final int SPACEBAR = 32;
	public static final int LEFT_ARROW = 37;
	public static final int UP_ARROW = 38;
	public static final int RIGHT_ARROW = 39;
	public static final int DOWN_ARROW = 40;
	
	public static final int A = 65;
	public static final int S = 83;
	public static final int D = 68;
	
	public static final int MOVE_VALUE = 8;
	public static final int STOP_VALUE = 4;
	
	public static final int ACT_SPEED = 80;
	
	public void hitTest(DisplayComponent dc) throws InterruptedException;
	public void moveMotion(DisplayComponent dc);

}
