package repl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String longStr = scanner.nextLine();
            String[] sortStrs = longStr.split("\\s+");
            if (longStr.equals("/help")) {
                System.out.println("The program calculates the sum of numbers");
                continue;
            } else if (longStr.equals("/exit")) {
                System.out.println("Bye!");
                break;
            }
            int sum = 0;
            for (String sortStr : sortStrs) {
                sum += Integer.parseInt(sortStr);
            }
            System.out.println(sum);
        }
    }
}
