public class CBTMain {

	public static void main(String[] args) {
		CBTree test = new CBTree();
		test.add(1, "1");
		test.add(2, "1-1");
		test.add(3, "1-2");
		test.add(4, "1-1-1");
		test.add(5, "1-1-2");
		test.add(6, "1-2-1");
		test.add(7, "1-2-2");
		test.add(8, "1-1-1-1");
		test.inorder();

		Node rNode = (Node) test.remove();
		System.out.println("remove : " + ((Entry) rNode.getElement()).getKey());
		test.preorder();

		rNode = (Node) test.remove();
		System.out.println("remove : " + ((Entry) rNode.getElement()).getKey());
		test.preorder();

		rNode = (Node) test.remove();
		System.out.println("remove : " + ((Entry) rNode.getElement()).getKey());
		test.preorder();

		rNode = (Node) test.remove();
		System.out.println("remove : " + ((Entry) rNode.getElement()).getKey());
		test.preorder();

		rNode = (Node) test.remove();
		System.out.println("remove : " + ((Entry) rNode.getElement()).getKey());
		test.preorder();

		rNode = (Node) test.remove();
		System.out.println("remove : " + ((Entry) rNode.getElement()).getKey());
		test.preorder();

		rNode = (Node) test.remove();
		System.out.println("remove : " + ((Entry) rNode.getElement()).getKey());
		test.preorder();

		rNode = (Node) test.remove();
		System.out.println("remove : " + ((Entry) rNode.getElement()).getKey());
	}

}
