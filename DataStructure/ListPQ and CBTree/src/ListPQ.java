
public class ListPQ {
	private Entry[] list;
	private int index = 0;
	
	public ListPQ(int size){
		list = new Entry[size];
	}
	public void insert(int key, Object value){
		list[index] = new Entry(key, value);
		index++;
	}
	public void printAll(){
		System.out.println("==Print All Entries==");
		for(int i = 0; i < index; i ++){
			list[i].printDataAndKey();
		}
	}
	public int findMin(){
		int minKey = 99;
		int minEntryNumber = 0;
		for(int i = 0; i < index; i ++){
			if(minKey > list[i].getKey()){
				minKey = list[i].getKey();
				minEntryNumber = i;
			}
		}
		return minEntryNumber;
	}
	public void min(){
		System.out.println("==Print Min Entry==");
		list[findMin()].printDataAndKey();
	}
	
	public void removeMin(){
		int minEntryNumber = findMin();
		System.out.println("==Print and remove min entry==");
		list[minEntryNumber].printDataAndKey();
		for(int i = minEntryNumber; i < index - 1; i++){
			list[i] = list[i + 1];
		}
		index --;
	}
}
