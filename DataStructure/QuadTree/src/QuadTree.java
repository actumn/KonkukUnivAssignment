import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;


public class QuadTree {

	private int MAX_OBJECTS = 10;
	private int MAX_LEVELS = 5;
	
	private int level;
	private List objects;
	private Rectangle bounds;
	private QuadTree[] nodes;
	
	/*
	 * Constructor
	 */
	public QuadTree(int pLevel, Rectangle pBounds) {
		level = pLevel;
		objects = new ArrayList();
		bounds = pBounds;
		nodes = new QuadTree[4];
	}
	
	/*
	 * Clears the quadtree
	 */
	public void clear() {
		objects.clear();
		
		for (QuadTree qt: nodes) {
			if (qt != null) {
				qt.clear();
				qt = null;
			}
		}
	}
	
	/*
	 * Splits the node into 4 subnodes
	 */
	private void split() {
		int subWidth = (int)(bounds.getWidth() / 2);
		int subHeight = (int)(bounds.getHeight() / 2);
		int x = (int)bounds.getX();
		int y = (int)bounds.getY();
		
		
	}
	
}
