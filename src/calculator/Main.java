import java.util.Scanner;
import java.util.Arrays;

public class Main {
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		while (!s.equals("/exit")) {
			if(s.isEmpty()) {}
			else {
				if(s.equals("/help")) {
					System.out.println("The program calculates the sum of numbers");
				}
				else {
					int result = 0;
					String[] nums = s.split(" ");
					for(int i = 0; i < nums.length; i++) {
						result += Integer.valueOf(nums[i]);
					}
					if(nums.length > 0) {
						System.out.println(result);
					}
				}
			}
			s = scanner.nextLine();
		}
	}
	
}
