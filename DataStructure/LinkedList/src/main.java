
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList linkedList =new LinkedList();
		linkedList.addLast("L");//L
		linkedList.addLast("e");//Le
		linkedList.addLast("e");//Lee
		linkedList.addLast("S");//LeeS
		linkedList.addLast("u");//LeeSu
		linkedList.addLast("n");//LeeSun
		linkedList.addLast("M");//LeeSunM
		linkedList.addLast("y");//LeeSunMy
		linkedList.addLast("e");//LeeSunMye
		linkedList.addLast("o");//LeeSunMyeo
		linkedList.addLast("n");//LeeSunMyeon
		linkedList.addLast("g");//LeeSunMyeong
		
		linkedList.printAllNode();//p
		
		linkedList.addBefore("M", "-");//LeeSun-Myeong
		linkedList.printAllNode();//p
		linkedList.printRev();//pR
		
		linkedList.set("S", "J");//LeeJun-Myeong
		linkedList.printAllNode();//p
		
		linkedList.remove("g");//LeeJun-Myeon
		linkedList.printAllNode();//p
		
		System.out.println("size : " + linkedList.getSize());
		linkedList.addFrist("I am ");//I am LeeJun-Myeon
		linkedList.printAllNode();//p
		
		linkedList.addAfter(12, "!");//I am LeeJun-Myeon!
		linkedList.printAllNode();//p
		
	}

}
