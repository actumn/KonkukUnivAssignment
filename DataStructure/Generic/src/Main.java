
public class Main {
	public static void main(String[] args){
		System.out.println("============Box1==========");
		Box<Entry<Integer, String>> box1 = new Box<Entry<Integer, String>>();
		box1.add(new Entry<Integer, String>(1, "Datastructure-Entry"));
		System.out.println("Key : "+ box1.get().getKey());
		System.out.println("Value : "+ box1.get().getValue());
		
		System.out.println("============Box2=============");
		Box<String> box2 = new Box<String>();
		box2.add("Datastructure-String");
		System.out.println(box2.get());
	}
}
