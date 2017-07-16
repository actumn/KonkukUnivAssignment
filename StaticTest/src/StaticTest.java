
public class StaticTest {
	static private int test = 777;
	static public void testStatic(){
		System.out.println("static test");
	}
	static public void setVariable(int t){
		test = t;
	}
	static public void printStatic(){
		System.out.println("printStatic: "+test);
	}
	static public void print(){
		System.out.println("print : "+test);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StaticTest.testStatic();
		System.out.println("static variable");
		StaticTest.printStatic();
		StaticTest.setVariable(3333);
		StaticTest.printStatic();
		StaticTest staticTest = new StaticTest();
		staticTest.print();
		staticTest.setVariable(7777);
		StaticTest.printStatic();
		staticTest.print();
	}

}
