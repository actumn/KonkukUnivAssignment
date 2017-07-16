
public class Tree {
	private TreeNode root;
	public List list;
	
	public Tree(){
		root = new TreeNode("root", null);
		list = new List();
	}
	
	public TreeNode getRoot(){
		return root;
	}
	
	
	
	public void preorder(TreeNode t){
		t.printData();
		if(t.isLeft()){
			this.preorder(t.getLeftChild());
		}
		if(t.isRight()){
			this.preorder(t.getRightChild());
		}
	}
	public void postorder(TreeNode t){
		if(t.isLeft()){
			this.postorder(t.getLeftChild());
		}
		if(t.isRight()){
			this.postorder(t.getRightChild());
		}
		t.printData();
	}
	
	public boolean isRoot(TreeNode v){
		if(root == v){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean isExternal(TreeNode v){
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
	
	public TreeNode parent(TreeNode v){
		return v.getParent();
	}

	
	public void setNodeListPreorder(TreeNode t){
		list.addLast(t);
		if(t.isLeft()){
			this.setNodeListPreorder(t.getLeftChild());
		}
		if(t.isRight()){
			this.setNodeListPreorder(t.getRightChild());
		}
	}
	
	public void printNodeList(){
		list.printAllNode();
	}
}
