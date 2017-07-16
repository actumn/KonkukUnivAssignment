import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class HashSetExcample {
	public static void main(String[] args){
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		list1.add(1);
		list1.add(2);
		list1.add(3);
		
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list2.add(3);
		list2.add(4);
		list2.add(5);
		
		ArrayList<Integer> listSum = new ArrayList<Integer>();
		listSum.addAll(list1);
		listSum.addAll(list2);
		
		System.out.println("===Items in the list===");
		for(Integer item : listSum){
			System.out.println(item);
		}
		
		HashSet<Integer> set = new HashSet<Integer>();
		set.addAll(list1);
		set.addAll(list2);
		
		Iterator<Integer> iterator = set.iterator();
		System.out.println("===Items in the set===");
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
}
