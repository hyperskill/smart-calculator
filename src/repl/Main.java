package repl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            String s = in.nextLine();
            if (s.equals("/exit")) {
                System.out.println("Buy!");
                return;
            } else if (s.equals("/help")) {
                System.out.println("The program calculates the sum of numbers");
            } else {
                String[] ary = s.split(" ");
                int result = 0;
                for (String num : ary) {
                    result += Integer.parseInt(num);
                }
                System.out.println(result);
            }
        }
    }
}