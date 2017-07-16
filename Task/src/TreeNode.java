
public class TreeNode {
	private Object element;
	private TreeNode parent;
	private TreeNode left;
	private TreeNode right;
	
	//TreeNode 생성자
	public TreeNode(Object element, TreeNode parent){
		this.element = element;
		this.parent = parent;
		this.left = null;
		this.right = null;
	}
	//전달받은 element를 갖는 노드를 왼쪽에 추가한다
	public void addLeftChild(Object element){
		left = new TreeNode(element, this);
	}
	//전달받은 element를 갖는 노드를 오른쪽에 추가한다
	public void addRightChild(Object element){
		right = new TreeNode(element, this);
	}
	//현재 노드의 element를 print한다
	public void printData(){
		System.out.println(element);
	}
	//element 인스턴스를 전달한다
	public Object getData(){
		return element;
	}
	//왼쪽 자식노드를 전달한다
	public TreeNode getLeftChild(){
		return left;
	}
	//오른쪽 자식노드를 전달한다
	public TreeNode getRightChild(){
		return right;
	}
	//왼쪽 자식 노드의 현재 존재 여부를 전달한다.
	public boolean isLeft(){
		return !(left == null);
	}
	//오른쪽 자식 노드의 현재 존재 여부를 전달한다.
	public boolean isRight(){
		return !(right == null);
	}
	//root노드인지 전달한다
	public boolean isRoot(){
		return parent == null;
	}
	//부모 노드를 전달한다.
	public TreeNode getParent(){
		return parent;
	}
	
}
