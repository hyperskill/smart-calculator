import java.util.Scanner;
import java.util.Arrays;

public class Main {
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		while (!s.equals("/exit")) {
			if (s.contains(" ")) {
				String[] nums = new String[2];
				nums = s.split(" ");
				System.out.println(Integer.parseInt(nums[0]) + Integer.parseInt(nums[1]));
			}
			else if (s.isEmpty()) {
			}
			else {
				System.out.println(s);
			}
			s = scanner.nextLine();
		}
	
}
