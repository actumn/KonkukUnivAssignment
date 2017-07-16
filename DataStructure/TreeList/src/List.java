
public class List {
	ListNode header = new ListNode();
	ListNode trailer = new ListNode();
	int size;
	
	//header trailer
	public List(){
		header.setNext(trailer);
		trailer.setPrev(header);
		size = 0;
	}
	
	public int getSize(){
		return size;
	}
	public boolean isEmpty(){
		return size == 0;
	}
	
	
	
	//header / nodeNew / ... ... / treailer
	public void addFrist(TreeNode value){
		ListNode nodeNew = new ListNode(header, header.getNext(), value);
		header.setNext(nodeNew);
		header.getNext().setPrev(nodeNew);
		size++;
	}
	
	//header / ... ...  / nodeNew / trailer
	public void addLast(TreeNode value){
		ListNode nodeNew = new ListNode(trailer.getPrev(), trailer, value);
		trailer.getPrev().setNext(nodeNew);
		trailer.setPrev(nodeNew);
		size++;
	}
	
	
	
	
	
	
	//header / ... ... / (n-1) / nodeNew / temp / ... / trailer
	public boolean addBefore(int n, TreeNode value){
		ListNode temp = header.getNext();
		int index = 0;
		while(temp != trailer){
			if(index == n){
				ListNode nodeNew = new ListNode(temp.getPrev(), temp, value);
				temp.getPrev().setNext(nodeNew);
				temp.setPrev(nodeNew);
				size++;
				return true;
			}
			temp = temp.getNext();
			index++;
		}
		
		return false;
	}
	
	//Overload
	//Search n , and add value Before n
	public boolean addBefore(TreeNode n, TreeNode value){
		ListNode temp = header.getNext();
		while(temp != trailer){
			if(((TreeNode)temp.getEmplement()).getData() == n){
				ListNode nodeNew = new ListNode(temp.getPrev(), temp, value);
				temp.getPrev().setNext(nodeNew);
				temp.setPrev(nodeNew);
				size++;
				return true;
			}
			temp = temp.getNext();
		}
		
		return false;
	}
	
	
	
	
	
	//header / ... ... / temp / nodeNew / (n+1) / ... / trailer
	public boolean addAfter(int n, TreeNode value){
		ListNode temp = header.getNext();
		int index = 0;
		while(temp != trailer){
			if(index == n){
				ListNode nodeNew = new ListNode(temp, temp.getNext(), value);
				temp.setNext(nodeNew);
				temp.getNext().setPrev(nodeNew);
				size++;
				return true;
			}
			temp = temp.getNext();
			index++;
		}
		
		return false;
	}
	
	//Overload
	//Search n, and add value after n
	public boolean addAfter(TreeNode n, TreeNode value){
		ListNode temp = header.getNext();
		while(temp != trailer){
			if(((TreeNode)temp.getEmplement()).getData() == n){
				ListNode nodeNew = new ListNode(temp, temp.getNext(), value);
				temp.setNext(nodeNew);
				temp.getNext().setPrev(nodeNew);
				size++;
				return true;
			}
			temp = temp.getNext();
		}
		
		return false;
	}
	
	
	
	//header / ... ... / (n-1) / temp->setValue(value) / (n+1) / ... ... / trailer
	public boolean set(int n, TreeNode value){
		ListNode temp = header.getNext();
		int index = 0;
		while(temp != trailer){
			if(index == n){
				temp.setValue(value);
				return true;
			}
			index++;
			temp = temp.getNext();
		}
		
		return false;
	}
	
	//Overload
	//Search n, and setValue(Value)
	public boolean set(TreeNode n, TreeNode value){
		ListNode temp = header.getNext();
		while(temp != trailer){
			if(((TreeNode)temp.getEmplement()).getData() == n){
				temp.setValue(value);
				return true;
			}
			temp = temp.getNext();
		}
		return false;
	}
	
	
	
	
	
	
	//header / ... / temp.Prev (temp) temp.Next / ... / trailer
	public Object remove(ListNode p){
		ListNode temp = header.getNext();
		while(temp != trailer){
			if(temp.equals(p)){
				temp.getPrev().setNext(temp.getNext());
				temp.getNext().setPrev(temp.getPrev());
				temp = null;
				size--;
				return ((TreeNode)p.getEmplement()).getData();
			}
			temp = temp.getNext();
		}
		return null;
	}
	
	//Overload
	//Serach n, and remove
	public TreeNode remove(TreeNode value){
		ListNode temp = header.getNext();
		while(temp != trailer){
			if(((TreeNode)temp.getEmplement()).getData() == value){
				temp.getPrev().setNext(temp.getNext());
				temp.getNext().setPrev(temp.getPrev());
				temp = null;
				size--;
				return value;
			}
			temp = temp.getNext();
		}
		return null;
	}
	
	
	
	
	
	
	// ¼øÂ÷ print
	public void printAllNode(){
		ListNode temp = header.getNext();
		while(temp != trailer){
			System.out.println(((TreeNode)temp.getEmplement()).getData());
			temp = temp.getNext();
		}
		System.out.println();
	}
	
	// reverse print
	public void printRev(){
		ListNode temp = trailer.getPrev();
		while(temp != header){
			System.out.print(((TreeNode)temp.getEmplement()).getData());
			temp = temp.getPrev();
		}
		System.out.println();
	}
}
