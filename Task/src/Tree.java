
public class Tree {
	private TreeNode root;
	private TreeNode findNode;
	
	//Tree클래스의 생성자. root노드를 생성한다
	public Tree(Object element){
		root = new TreeNode(element, null);
		list = new List();
		listSub = new List();
	}

	//전달받은 노드 t로부터 preorder traversal를 수행하며 해당노드의 element와 str이 같다면 findNode에 그 노드의 레퍼런스 하는 메소드
	private void findRecursive(TreeNode t, String str){
		// ##############  수정하였습니다 (Line 16)  ################
		if(str.equals(t.getData())){
			findNode = t;
		}
		if(t.isLeft())
			findRecursive(t.getLeftChild(), str);
		
		if(t.isRight())
			findRecursive(t.getRightChild(), str);
		
	}
	
	//findRecursive를 호출하는 메소드
	public TreeNode findNode(String name){
		findRecursive(root, name);
		return findNode;
	}
	
	//root노드를 전달한다.
	public TreeNode getRoot(){
		return root;
	}
	
	//preorder traversal를 수행하면서 각 노드의 element를 출력한다.
	public void preorder(TreeNode root){
		System.out.println(root.getData());
		if(root.isLeft())
			preorder(root.getLeftChild());
		if(root.isRight())
			preorder(root.getRightChild());
	}
	
	//추가구현
	
	List list;
	List listSub;
	

	// ##############  수정하였습니다 (Line 55 ~  96) ################
	//조상 목록을 출력한다.
	public void getAncestors(TreeNode n){
		TreeNode temp = n;
		list.initializaiton();
		while(temp != null){
			list.addFirst(temp);
			temp = temp.getParent();
		}
		printNodeList();
	}
	//list를 차례로 출력한다
	public void printNodeList(){
		for(int i = 0; i < list.getSize(); i++){
			System.out.println(((TreeNode)list.getNode(i)).getData() + " ");
		}
	}
	//list와 listSub에 각각 n1,n2의 조상을 저장한다.
	public void getAncestors(TreeNode n1, TreeNode n2){
		TreeNode temp = n1;
		list.initializaiton();
		while(temp != null){
			list.addLast(temp);
			temp = temp.getParent();
		}
		temp = n2;
		listSub.initializaiton();
		while(temp != null){
			listSub.addLast(temp);
			temp = temp.getParent();
		}
	}
	//n1과 n2의 공동 조상을 검색한다.
	public void getCommonNode(TreeNode n1, TreeNode n2){
		getAncestors(n1, n2);
		for(int i = 0; i < list.getSize(); i++){
			for(int j = 0; j < listSub.getSize(); j++){
				if(list.getNode(i) == listSub.getNode(j)){
					System.out.println(((TreeNode)list.getNode(i)).getData() + "(촌수:" + (i + j) + ")");
					return;
				}
			}
		}
	}
	
	
	

} 
