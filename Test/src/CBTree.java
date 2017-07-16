
public class CBTree {
	private Node root;
	private int n;
	private Node lastNode;
	
	public CBTree(int key, Object data){
		root = new Node(new Entry(key,data), null);
		n = 1;
		lastNode = null;
	}
	
	public CBTree(){
		root = null;
		n = 0;
		lastNode = null;
	}
	
	public void preorder(){
		preorder(root);
	}
	public void inorder(){
		inorder(root);
	}
	private void preorder(Node t){
		visit(t);
		
		if(t.isLeft()){
			preorder(t.getLeftChild());
		}
		if(t.isRight()){
			preorder(t.getRightChild());
		}
	}
	private void inorder(Node t){
		
	}
	
	private void visit(Node t){
		
	}
	
	public Node add(int key, Object data){
		Entry newEntry = new Entry(key, data);
		Node newNode = new Node(newEntry, null);
		Node temp = root;
		
		if(root == null){
			root = newNode;
			n++;
		}
		else{
			int pos = n + 1;
			String bit = Integer.toBinaryString(pos);
			int len = bit.length();
			
			for(int i = 2; i <= len; i++){
				int check = ((pos >> (len - i)) & 1);
				if(check == 0){
					if(temp.getLeftChild() == null){
						newNode = new Node(newEntry, temp);
						temp.addLeftChild(newNode);
						n++;
					}
					else{
						temp = temp.getLeftChild();
					}
				}
				else{
					if(temp.getRightChild() == null){
						newNode = new Node(newEntry, temp);
						temp.addRightChild(newNode);
						n++;
					}
					else{
						temp = temp.getRightChild();
					}
				}
			}
		}
		lastNode = newNode;
		return newNode;
	}
	
	public Object remove(){
		if(lastNode == null){
			return null;
		}
		
		if(lastNode.equals(root)){
			Node returnRoot = root;
			this.root = null;
			lastNode = null;
			return returnRoot;
		}
		
		Node returnLastNode = lastNode;
		int pos = n-1;
		String bit = Integer.toBinaryString(pos);
		int len = bit.length();
		
		Node temp = root;
		for(int i = 2; i < len; i++){
			int check = ((pos >> (len - i)) & 1);
			if(check == 0){
				temp = temp.getLeftChild();
			}
			else{
				temp = temp.getRightChild();
			}
		}
		lastNode = temp;
		n --;
		
		Node lastParent = returnLastNode.getParent();
		if(lastParent.isLeft()){
			if(lastParent.getLeftChild().equals(returnLastNode)){
				lastParent.addLeftChild(null);
			}
		}
		else if(lastParent.isRight()){
			if(lastParent.getRightChild().equals(returnLastNode)){
				lastParent.addRightChild(null);
			}
		}
		
		return returnLastNode;
	}
}
