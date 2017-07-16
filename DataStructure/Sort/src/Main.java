
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int k = 0;
		int[] array = new int[50000];
		long time = 0;
		for(int i = array.length -1; i >= 0; i--){
			array[k] = (int)(Math.random() * 100000);
			k++;
		}
		time = System.currentTimeMillis();
		bubbleSort(array);
		time = System.currentTimeMillis() - time;
		for(int i = 0; i < array.length; i++){
			System.out.println(array[i] + " ");
		}
		System.out.println("연산시간 : " + time + "ms");
	}
	
	public static void bubbleSort(int[] arr){
		for(int i = 0; i < arr.length - 1; i++){
			for(int j = 0; j < arr.length-i -1; j++){
				int temp = arr[j];
				arr[j] = arr[j+1];
				arr[j+1] = temp;
			}
		}
	}

}
