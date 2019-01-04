package repl;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
		String number1;
		String number2;
		int a;
		int b;
		while (true) {
			number1 = scanner.next();
			if (number1.equals("/exit")) {
				System.out.println("Bye!");
				break;
			}
			else {
				a = Integer.parseInt(number1);
			}
			number2 = scanner.next();
			if (number2.equals("/exit")) {
				System.out.println("Bye!");
				break;
			}
			else {
				b = Integer.parseInt(number2);
			}
			System.out.println(a+b);
		}
    }
}
