package calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        String curStr = "";

        while (true) {
            curStr = scanner.nextLine();
            if (curStr.equals("/help")) {
                System.out.println("The program calculates the sum of numbers");

            } else if (curStr.equals("/exit")) {
                System.out.println("Bye!");
                return;

            } else {
                if (!("".equals(curStr))) {
                    String[] nums = curStr.split(" ");
                    for (String num : nums) {
                        sum += Integer.valueOf(num);
                    }
                }
                System.out.println(sum);
                sum = 0;
            }
        }
    }
}
