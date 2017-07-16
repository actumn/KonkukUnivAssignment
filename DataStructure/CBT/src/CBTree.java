public class CBTree {
	protected Node root;
	private int n;

	public CBTree(int key, Object data) {
		root = new Node(new Entry(key, data), null);
		n = 1;
	}

	public CBTree() {
		root = null;
		n = 0;
	}

	public void preorder() {
		preorder(root);
	}

	public void inorder() {
		inorder(root);
	}

	private void preorder(Node t) {
		((Entry) t.getElement()).printDataAndKey();

		if (t.isLeft()) {
			this.preorder(t.getLeftChild());
		}

		if (t.isRight()) {
			this.preorder(t.getRightChild());
		}
	}

	private void inorder(Node t) {
		if (t.isLeft()) {
			this.inorder(t.getLeftChild());
		}

		((Entry) t.getElement()).printDataAndKey();

		if (t.isRight()) {
			this.inorder(t.getRightChild());
		}
	}

	public Node add(int key, Object data) {
		Entry newEntry = new Entry(key, data);
		Node newNode = new Node(newEntry, null);
		Node temp = root;
		if (root == null) {
			root = newNode;
			n++;
		} else {
			int pos = n + 1;
			String bit = Integer.toBinaryString(pos);
			int len = bit.length();

			for (int i = 2; i <= len; i++) {
				int check = ((pos >> (len - i)) & 1);
				if (check == 0) {
					if (temp.getLeftChild() == null) {
						newNode = new Node(newEntry, temp);
						temp.addLeftChild(newNode);
						n++;
					} else {
						temp = temp.getLeftChild();
					}
				} else if (check == 1) {
					if (temp.getRightChild() == null) {
						newNode = new Node(newEntry, temp);
						temp.addRightChild(newNode);
						n++;
					} else {
						temp = temp.getRightChild();
					}
				}
			}
		}
		return newNode;
	}

	public Node findMin() {
		int pos = n;
		String bit = Integer.toBinaryString(pos);
		int len = bit.length();

		Node temp = root;
		for (int i = 2; i <= len; i++) {
			int check = ((pos >> (len - i)) & 1);
			if (check == 0) {
				temp = temp.getLeftChild();
			} else if (check == 1) {
				temp = temp.getRightChild();
			}
		}
		return temp;
	}

	public Node remove() {
		Node returnLastNode = findMin();

		if (returnLastNode == root) {
			returnLastNode = root;
			root = null;
			return returnLastNode;
		}

		Node lastParent = returnLastNode.getParent();

		if (lastParent.isRight()) {
			if (lastParent.getRightChild() == returnLastNode) {
				lastParent.addRightChild(null);
			}
		} else if (lastParent.isLeft()) {
			if (lastParent.getLeftChild() == returnLastNode) {
				lastParent.addLeftChild(null);
			}
		}
		n--;
		return returnLastNode;
	}

}
