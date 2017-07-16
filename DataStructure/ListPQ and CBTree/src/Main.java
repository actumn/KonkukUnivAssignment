
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ListPQ pq = new ListPQ(100);
		pq.insert(3, "Àº¾ÖÃµ");
		pq.insert(2, "Á¤ÇõÁØ");
		pq.insert(1, "Àå¹ü¼®");
		pq.insert(6, "±è¼º¹Î");
		pq.insert(4, "¹Ú¼ºÈÆ");
		pq.insert(5, "¼Õ¿µ¼®");
		pq.printAll();
		pq.min();
		pq.printAll();
		pq.removeMin();
		pq.printAll();
		pq.removeMin();
		pq.printAll();
	}

}
