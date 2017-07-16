public class Node {
	private Object element;
	private Node left;
	private Node right;
	private Node parent;

	public Node(Object element, Node parent) {
		this.element = element;
		this.parent = parent;
		this.left = null;
		this.right = null;
	}

	public void addLeftChild(Node node) {
		left = node;
	}

	public void addRightChild(Node node) {
		right = node;
	}

	public void printElement() {
		System.out.println(element);
	}

	public Object getElement() {
		return element;
	}

	public Node getParent() {
		return parent;
	}

	public Node getLeftChild() {
		return left;
	}

	public Node getRightChild() {
		return right;
	}

	public boolean isLeft() {
		return (left != null);
	}

	public boolean isRight() {
		return (right != null);
	}

	public boolean isLeaf() {
		return (left == null && right == null);
	}

	public void setElement(Object obj) {
		this.element = obj;
	}
}
