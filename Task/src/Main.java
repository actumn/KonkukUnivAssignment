
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* 5¹ø¹®Á¦
		 * 
		List list = new List();
		list.addLast("A");
		list.addLast("B");
		list.addLast("C");
		System.out.println(list.getNode(2));
		System.out.println(list.getNode(0));
		System.out.println(list.getNode(1));
		*/
		
		/* 6¹ø¹®Á¦
		 * 
		Tree tree = new Tree("animal");
		tree.getRoot().addLeftChild("mammal");
		tree.getRoot().addRightChild("bird");
		tree.findNode("mammal").addLeftChild("cat");
		tree.findNode("mammal").addRightChild("dog");
		tree.findNode("bird").addLeftChild("eagle");
		tree.findNode("bird").addRightChild("sparrow");
		tree.preorder(tree.getRoot());
		 */

		/* 6¹ø¹®Á¦ Ãß°¡±¸Çö
		 * 
		 */
		Tree tree = new Tree("±è»óÁÖ");
		tree.findNode("±è»óÁÖ").addLeftChild("±è¹æÁ÷");
		tree.findNode("±è¹æÁ÷").addLeftChild("±èÁø¸ð");
		tree.findNode("±è¹æÁ÷").addRightChild("±èÁÖ±¹");
		tree.findNode("±èÁø¸ð").addRightChild("±èº¸");
		tree.findNode("±èº¸").addRightChild("±è¹Ì¼ö");
		tree.findNode("±èÁø¸ð").addLeftChild("±è¼®°æ");
		tree.findNode("±è»óÁÖ").addRightChild("±è¿ëÁ÷");
		tree.findNode("±è¿ëÁ÷").addLeftChild("±è¸ñ°æ");
		tree.findNode("±è¸ñ°æ").addLeftChild("±èÇÑ¼ö");
		tree.findNode("±è¸ñ°æ").addRightChild("±è¾à°æ");
		tree.findNode("±è¾à°æ").addLeftChild("±è°ü");
		tree.findNode("±è¾à°æ").addRightChild("±èÁøÃ¢");
		
		System.out.println("=======================");
		tree.getAncestors(tree.findNode("±è¹Ì¼ö"));
		System.out.println("=======================");
		tree.getCommonNode(tree.findNode("±è¹Ì¼ö"), tree.findNode("±èÁøÃ¢"));
		tree.getCommonNode(tree.findNode("±è¼®°æ"), tree.findNode("±è¹Ì¼ö"));
		tree.getCommonNode(tree.findNode("±è°ü"), tree.findNode("±èÇÑ¼ö"));
		
	}

}
