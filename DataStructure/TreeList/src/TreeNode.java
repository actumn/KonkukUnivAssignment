
public class TreeNode {
	private Object data;
	private TreeNode left;
	private TreeNode right;
	private TreeNode parent;
	
	public TreeNode(Object data, TreeNode parent){
		this.data = data;
		this.parent = parent;
		this.left = null;
		this.right = null;
	}
	
	public void addLeftChild(Object data){
		TreeNode temp = new TreeNode(data,this);
		left = temp;
	}

	public void addRightChild(Object data){
		TreeNode temp = new TreeNode(data,this);
		right = temp;
	}
	
	
	public void printData(){
		System.out.println(data);
	}
	public Object getData(){
		return data;
	}
	public TreeNode getLeftChild(){
		return left;
	}
	public TreeNode getRightChild(){
		return right;
	}
	
	
	public boolean isLeft(){
		if(left == null){
			return false;
		}
		else{
			return true;
		}
	}
	public boolean isRight(){
		if(right == null){
			return false;
		}
		else{
			return true;
		}
		
	}
	
	public TreeNode getParent(){
		return parent;
	}
}
