package repl;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    try (Scanner sc = new Scanner(System.in)) {
      while (true) {
        String line = sc.nextLine();
        if (line.isEmpty()) {
          continue;
        }
        if (line.equals("/exit")) {
          System.out.printf("Bye!\n");
          break;
        }
        String[] terms = line.split("\\s+");
        System.out.printf("%d\n", Integer.parseInt(terms[0]) + Integer.parseInt(terms[1]));
      }
    }
  }
}
