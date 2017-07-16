
public class DictionaryMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dictionary dic = new Dictionary();
		dic.insert(5, "A");
		dic.insert(7, "B");
		dic.insert(2, "C");
		dic.insert(8, "D");
		dic.insert(2, "E");
		dic.printAll();
		System.out.println("===============");
		Entry[] L = dic.findAll(2);
		printFindedData(L);
		System.out.println("===============");
		dic.remove(L[0]);
		dic.printAll();
	}
	
	public static void printFindedData(Entry[] L){
		for(int i = 0; i < L.length; i++){
			L[i].printDataAndKey();
		}
	}

}
