package repl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            int sum = 0;
            boolean plus = true;
            if (line.equals("/exit")) { System.out.println("Bye!"); break; }
            if (line.equals("/help")) {
                System.out.println("Enter arithmetic expession to calculate.");
                System.out.println("The program supports only + or - operators.");
                System.out.println("Everything should be separated by whitespaces: 2 + 5 or 5 -- 4");
                System.out.println("If you enter several operators following each other the program would still work,");
                System.out.println("two adjacent - operators interpreted as a +");
                continue;
            }
            if (line.trim().equals("")) { continue; }
            else {
                String[] nums = line.trim().split(" ");
                for (String e : nums) {
                    if (e.matches("[+-]+")) {
                        plus = true;
                        for (int i = 0; i < e.length(); i++) {
                            if (e.charAt(i) == '-' && plus) { plus = false; }
                            else if (e.charAt(i) == '-' && !plus) { plus = true; }
                        }
                    } else {
                        if (plus) { sum += Integer.parseInt(e); }
                        else { sum -= Integer.parseInt(e); }
                    }

                }
                System.out.println(sum);
            }
        }
    }
}