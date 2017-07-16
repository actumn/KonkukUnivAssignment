
public class CBTree {
	private Node root;
	private int n;
	private Node lastNode;
	
	public CBTree(int key, Object data){
		root = new Node(new Entry(key, data), null);
		n = 1;
		lastNode = null;
	}
	public CBTree(){
		root = null;
		n = 0;
		lastNode = null;
	}
	
	public void preorder(){
		if(root.isLeft()){
			preorder(root.getLeftChild());
		}
		if(root.isRight()){
			preorder(root.getRightChild());
		}
		visit(root);
	}
	public void inorder(){
		if(root.isLeft()){
			inorder(root.getLeftChild());
		}
		visit(root);
		if(root.isRight()){
			inorder(root.getRightChild());
		}
	}
	private void preorder(Node t){
		if(t.isLeft()){
			preorder(t.getLeftChild());
		}
		if(t.isRight()){
			preorder(t.getRightChild());
		}
		visit(t);
	}
	private void inorder(Node t){
		if(t.isLeft()){
			inorder(t.getLeftChild());
		}
		visit(t);
		if(t.isRight()){
			inorder(t.getRightChild());
		}
		
	}
	
	public void visit(Node t){
		((Entry)t.getElement()).printDataAndKey();
	}
	
	public Node add(int key, Object data){
		Entry newEntry = new Entry(key, data);
		Node newNode = null;
		Node temp = root;
		if(root == null){
			newNode = new Node(newEntry, null);
			root = newNode;
			n++;
		}
		else{
			int pose = n+1;
			String bit = Integer.toBinaryString(pose);
			int len = bit.length();
			
			for(int i = 2; i <= len; i++){
				int check = ((pose >> (len - i)) & 1);
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
				else if(check == 1){
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
			lastNode = newNode;
		}
		
		
		return newNode;
		
	}
	
	public Object remove(){
		int pos = n-1;
		String bit = Integer.toBinaryString(pos);
		int len = bit.length();
		Node temp = root;
		
		Object last = lastNode.getElement();
		
		for(int i = 2; i <= len; i++){
			int check = ((pos >> (len - i)) & 1);
			
			if(check == 0){
				if(temp.getLeftChild() == null){
					lastNode = temp;
				}
				else{
					temp = temp.getLeftChild();
				}
			}
			else if(check == 1){
				if(temp.getLeftChild() == null){
					lastNode = temp;
				}
				else{
					temp = temp.getRightChild();
				}
			}
		}
		
		pos = n;
		bit = Integer.toBinaryString(pos);
		len = bit.length();
		temp = root;
		for(int i = 2; i <= len; i++){
			int check = ((pos >> (len - i)) & 1);
			
			if(check == 0){
				if(temp.getLeftChild() == null){
					temp.getParent().addLeftChild(null);
				}
				else{
					temp = temp.getLeftChild();
				}
			}
			else if(check == 1){
				if(temp.getLeftChild() == null){
					temp.getParent().addRightChild(null);
				}
				else{
					temp = temp.getRightChild();
				}
			}
		}
		
		return last; 
	}
}
