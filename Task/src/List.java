
public class List {
	private ListNode header = new ListNode(null, null, null);
	private ListNode trailer = new ListNode(null, null, null);
	private int size;
	
	//header와 trailer의 인스턴스를 생성하고 서로를 가르킬 수 있도록 하는 생성자
	public List(){
		header.setNextNode(trailer);
		trailer.setPrevNode(header);
		size = 0;
	}
	
	//현재 리스트에서 header와 trailer를 제외하고 모두 삭제한다
	public void initializaiton(){
		ListNode temp = header.getNextNode();
		ListNode temp2 = temp.getNextNode();
		while(temp != trailer){
			temp = null;
			temp = temp2;
			temp2 = temp.getNextNode();
		}
		header.setNextNode(trailer);
		trailer.setPrevNode(header);
		size = 0;
	}
	
	//마지맋(trailer 이전)에 새로운 노드를 삽입한다.
	public void addLast(Object element){
		ListNode temp = new ListNode(trailer.getPrevNode(), trailer, element);
		trailer.getPrevNode().setNextNode(temp);
		trailer.setPrevNode(temp);
		size++;
	}
	
	//처음(header 다음)에 새로운 노드를 삽입한다.
	public void addFirst(Object element){
		ListNode temp = new ListNode(header, header.getNextNode(), element);
		header.setNextNode(temp);
		header.getNextNode().setPrevNode(temp);
		size++;
	}
	
	//현재 리스트에서 i번째 노드의 element를 얻는다.(header와 trailer 제외)
	public Object getNode(int i){
		if(i >= size){
			System.out.println("Index Error : Too large");
			return null;
		}
		else if(i < 0){
			System.out.println("Index Error : Too small");
			return null;
		}
		
		ListNode temp = header.getNextNode();
		
		for(int k = 0; k < i; k ++){
			temp = temp.getNextNode();
		}
		return temp.getElement();
	}
	
	//List의 크기를 출력한다.
	public int getSize(){
		return size;
	}
}
