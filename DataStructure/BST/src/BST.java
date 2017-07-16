
public class BST {
	private Node root;
	
	private Node findNode;
	
	public BST(){
		root = null;
	}
	
	public void insert(int key, Object obj){
		Entry entry = new Entry(key, obj);
		if(this.root == null){
			this.root = new Node(entry, null);
		}
		else{
			insert(entry, this.root);
		}
	}
	
	public void insert(Entry entry, Node temp){
		if(((Entry) temp.getElement()).getKey() > entry.getKey()){
			if(temp.isLeft())
				insert(entry, temp.getLeftChild());
			else
				temp.addLeftChild(entry);
		}
		else{
			if(temp.isRight())
				insert(entry, temp.getRightChild());
			else
				temp.addRightChild(entry);
		}
	}
	
	public void inorderT(){
		inorderT(this.root);
	}
	private void inorderT(Node t){
		if(t.isLeft())
			this.inorderT(t.getLeftChild());
		((Entry)t.getElement()).printDataAndKey();
		if(t.isRight())
			this.inorderT(t.getRightChild());
	}
	
	public Node find(int key){
		findNode = null;
		//System.out.println("키 값이 " + key + " 인 엔트리 출력");
		find(key, root);
		return findNode;
	}
	
	private void find(int key, Node temp){
		Entry entry = ((Entry) temp.getElement());
		if(entry.getKey() == key){
			entry.printDataAndKey();
			findNode = temp;
			return;
		}
		if(temp.isLeaf()){
			System.out.println("찾기 실패");
			return;
		}
		
		if(entry.getKey() > key){
			find(key, temp.getLeftChild());
		}
		else{
			find(key, temp.getRightChild());
		}
	}
	
	public void remove(int key){
		Node deleteNode = find(key);
		boolean isLeftChild = ((Entry)deleteNode.getElement()).getKey() < ((Entry)deleteNode.getParent().getElement()).getKey();
		
		if(deleteNode.isLeaf()){
			if(isLeftChild)
				deleteNode.getParent().setLeftChild(null);
			else
				deleteNode.getParent().setRightChild(null);
			
			deleteNode = null;
		}
		
		else{
			Node swapNode = null;
			if(deleteNode.isLeft()){
				swapNode = deleteNode.getLeftChild();
				while(swapNode.isRight()){
					swapNode = swapNode.getRightChild();
				}
				
				swapNode.getParent().setRightChild(null);
			}
			else{
				swapNode = deleteNode.getRightChild();
				while(swapNode.isLeft()){
					swapNode = swapNode.getLeftChild();
				}
				
				swapNode.getParent().setLeftChild(null);
			}
			
			if(isLeftChild)
				deleteNode.getParent().setLeftChild(swapNode);
			else
				deleteNode.getParent().setRightChild(swapNode);
			
			swapNode.setParent(deleteNode.getParent());
			
			
			swapNode = null;
			deleteNode = null;
		}
	}
}
