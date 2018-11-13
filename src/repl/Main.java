package repl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            int n = 0;
            int m = 0;
            String first = "";
            String second = "";
            boolean flag = false;
            if (line.equals("/exit")) { System.out.println("Bye!"); break; }
            if (line.trim().equals("")) { continue; }
            else {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == ' ') { flag = true; continue; }
                    if (!flag) first += line.charAt(i);
                    else second += line.charAt(i);
                }
                n = Integer.parseInt(first);
                if (second.length() > 0) { m = Integer.parseInt(second); }
                System.out.println(m+n);
            }
        }
    }
}