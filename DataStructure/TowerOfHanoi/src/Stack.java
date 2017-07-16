
public class Stack {
	private int number[];
	private int index;
	public Stack(){
		number = new int[100];
		index = -1;
		//System.out.println("static Init");
	}
	public void push_back(int number){
		this.number[++index] = number;
		//System.out.println("(" + index + ", " + number + ")");
	}
	
	public int pop(){
		if(index < 0){
			System.out.println("Stack is Empty");
			return 0;
		}
		else{
			//System.out.println("(" + this.number[index] + ")");
			return this.number[index--];
		}
	}
	
	
	public String toString(){
		if(index == -1){
			System.out.println("NULL");
		}
		int n = 0;
		while(true){
			if(n > index){
				break;
			}
			else{
				System.out.print(number[n] + " ");
			}
			n++;
		}
		
		return null;
	}
}
