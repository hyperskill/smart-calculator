package repl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            int sum = 0;
            if (line.equals("/exit")) { System.out.println("Bye!"); break; }
            if (line.equals("/help")) { System.out.println("The program calculates the sum of numbers"); continue; }
            if (line.trim().equals("")) { continue; }
            else {
                String[] nums = line.split(" ");
                for (String e : nums) {
                    sum += Integer.parseInt(e);
                }
                System.out.println(sum);
            }
        }
    }
}