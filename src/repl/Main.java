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
            }
            String[] ary = s.split(" ");
            if (ary.length == 2) {
                System.out.println(Integer.parseInt(ary[0]) + Integer.parseInt(ary[1]));
            } else if (ary.length == 1) {
                System.out.println(Integer.parseInt(ary[0]));
            }
        }
    }
}