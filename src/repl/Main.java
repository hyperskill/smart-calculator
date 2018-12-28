package repl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Repl{
	public static void main (String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true){
		String numbers = br.readLine();

			if (numbers.equalsIgnoreCase("/help")) {
				System.out.println("The program calculates the sum of numbers");
				continue;
			}
			else if (numbers.equalsIgnoreCase("/exit")) {
				System.out.println("Bye!");
				break;
			}
			else if(numbers.equalsIgnoreCase(" ")||numbers.equalsIgnoreCase("")){
				continue;
			}
			else{
				String[] numberSplitting = numbers.split(" ");
				if (numberSplitting.length == 1){
					System.out.println(numberSplitting[0]);
				}		

				else {
					int sum = 0;
					for(String s : numberSplitting){
						sum += Integer.parseInt(s);
					}
					System.out.println(sum);
				}
			}
		
		}
	}
}
