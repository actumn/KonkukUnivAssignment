
public class ListNode {
	private Object element;
	private ListNode nextNode;
	private ListNode prevNode;
	
	//ListNode가 가리키는 prev와 next, element를 설정한다
	public ListNode(ListNode prevNode, ListNode nextNode, Object element){
		this.element = element;
		this.prevNode = prevNode;
		this.nextNode = nextNode;
	}
	
	//next 설정
	public void setNextNode(ListNode nextNode){
		this.nextNode = nextNode;
	}
	//prev 설정
	public void setPrevNode(ListNode prevNode){
		this.prevNode = prevNode;
	}
	
	//element를 얻는다
	public Object getElement(){
		return element;
	}
	//next를 얻는다
	public ListNode getNextNode(){
		return nextNode;
	}
	//prev를 얻는다
	public ListNode getPrevNode(){
		return prevNode;
	}
	
}
