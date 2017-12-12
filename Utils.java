package _2017._09._assignments.projectgo.template.v2;

public class Utils {
	
	public static String twoDArrayToString(int array[][]){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<array.length; i++){
			for(int j=0; j<array[0].length; j++){
				sb.append(array[j][i]);
				if(j<array[0].length-1){
					sb.append(",");
				}
			}
			if(i<array[0].length-1){
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		int array [][] = new int [7][7];
		for(int i=0; i<array.length; i++)
			for(int j=0; j<array[0].length; j++)
				array[i][j] = i*10+j;
		System.out.println("Array:\n"+Utils.twoDArrayToString(array));
		
	}

}
