import java.util.ArrayList;
import java.util.Collections;

public class SimpleSort1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		ArrayList<Integer> listInt = new ArrayList<Integer>();
		
		listInt.add(2);
		listInt.add(1);
		listInt.add(3);
		listInt.add(5);
		listInt.add(4);
		System.out.println("======Before sorting======");
		for(Integer item : listInt){
			System.out.print(item + " ");
		}
		System.out.println();
		
		Collections.sort(listInt);
		
		System.out.println("======After sorting======");
		for(Integer item : listInt){
			System.out.print(item + " ");
		}
	}

}
