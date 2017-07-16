
public class Tree {
	private Node root;
	
	public Tree(){
		root = new Node("root", null);
	}
	
	public Node getRoot(){
		return root;
	}
	


	public void preorder(Node t){
		t.printData();
		if(t.isLeft()){
			this.preorder(t.getLeftChild());
		}
		if(t.isRight()){
			this.preorder(t.getRightChild());
		}
	}
	public void postorder(Node t){
		if(t.isLeft()){
			this.postorder(t.getLeftChild());
		}
		if(t.isRight()){
			this.postorder(t.getRightChild());
		}
		t.printData();
	}
	
	public boolean isRoot(Node v){
		if(root == v){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean isExternal(Node v){
		if(v == null){
			return false;
		}
		if(v.isLeft() || v.isRight()){
			return false;
		}
		else{
			return true;
		}
	}
	
	public Node parent(Node v){
		return v.getParent();
	}

	public Node[] nodes() {
		NodeStack nodes = new NodeStack(14);
		addNodes(nodes, root);
		return nodes.getNodes();
	}
	
	public static void addNodes(NodeStack nodes, Node v){
		nodes.push_back(v);
		if(v.isLeft()){
			addNodes(nodes, v.getLeftChild());
		}
		if(v.isRight()){
			addNodes(nodes, v.getRightChild());
		}
	}

	
	public Node[] children(Node v) {
		
		NodeStack nodes = new NodeStack(2);
		if(v.isLeft()){
			nodes.push_back(v.getLeftChild());
		}
		if(v.isRight()){
			nodes.push_back(v.getRightChild());
		}
		return nodes.getNodes();
	}
}
