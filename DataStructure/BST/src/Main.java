
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			BST tree = new BST();
			tree.insert(3, "승");
			tree.insert(10, "대");
			tree.insert(5, "코");
			tree.insert(16, "민");
			tree.insert(95, "링");
			tree.insert(1, "오");
			tree.insert(25, "국");
			tree.insert(6, "리");
			tree.insert(7, "아");
			tree.insert(32, "화");
			tree.insert(52, "이");
			tree.insert(2, "필");
			tree.insert(15, "한");

			tree.inorderT();
			
			System.out.println("키 값이 5인 엔트리 찾기");
			tree.find(5);
			System.out.println("키 값이 55인 엔트리 찾기");
			tree.find(55);
			System.out.println("키 값이 15인 엔트리 찾기");
			tree.find(15);
			
			System.out.println("키 값이 5인 엔트리 삭제");
			tree.remove(5);
			tree.inorderT();
	}

}
