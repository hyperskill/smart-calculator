package repl;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    try (Scanner sc = new Scanner(System.in)) {
      while (true) {
        String line = sc.nextLine();
        if (line.equals("/help")) {
          System.out.printf("The program calculates the sum of numbers\n");
          continue;
        }
        if (line.isEmpty()) {
          continue;
        }
        if (line.equals("/exit")) {
          System.out.printf("Bye!\n");
          break;
        }
        String[] terms = line.split("\\s+");
        int[] numbers = Arrays.stream(terms).mapToInt(Integer::parseInt).toArray();
        System.out.printf("%d\n", Arrays.stream(numbers).sum());
      }
    }
  }
}

