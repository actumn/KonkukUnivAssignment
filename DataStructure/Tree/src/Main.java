
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tree tree = new Tree();
		tree.getRoot().addLeftChild("1. Mammal");
		tree.getRoot().addRightChild("2. Birds");
		tree.getRoot().getLeftChild().addLeftChild("1-1. Dog");
		tree.getRoot().getLeftChild().addRightChild("1-2. Cat");
		tree.getRoot().getRightChild().addLeftChild("2-1. Sparrow");
		tree.getRoot().getRightChild().addRightChild("2-2. Eagle");

		System.out.println("depth of root : " + depth(tree, tree.getRoot()));
		System.out.println("depth of Dog : " + depth(tree, tree.getRoot().getLeftChild().getLeftChild()));
		
		System.out.println("Height : (height1)" + height1(tree));
		System.out.println("Height : (height2)" + height2(tree, tree.getRoot()));
	}

	public static int depth(Tree T, Node v){
		if(T.isRoot(v)){
			return 0;
		}
		else{
			return 1 + depth(T, T.parent(v));
		}
	}
	
	public static int height1(Tree T){
		int h = 0;

		for (Node v : T.nodes()){
			if(T.isExternal(v)){
				h = Math.max(h,  depth(T, v));
			}
		}
		return h;
	}
	public static int height2(Tree T, Node v){
		if(T.isExternal(v)){
			return 0;
		}
		
		int h = 0;
		for(Node w : T.children(v)){
			h = Math.max(h, height2(T, w));
		}
		return 1 + h;
	}
}
