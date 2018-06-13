import java.util.*;

public class Repl {
  public static void main(String[] args) {
      while(true) {
        read();
        if  (!eval()) break;
      }
      System.out.println("Bye!");
  }

  private static boolean eval() {
      switch (operator) {
        case "/exit": return false;

        case "/help":
        System.out.println("The program calculates the sum of numbers"); 
        break;

        default:
        if (operands.size() >= 1) System.out.println(sum());
      }
    return true;
  }

  private static void read() {
    Scanner sc = new Scanner(System.in);
    String line = sc.nextLine();
    String[] words = line.split(DELIM);
    operator = "";
    operands = new ArrayList<Integer>();

    for (String word : words) {
      try {
        int operand = Integer.parseInt(word);
        operands.add(operand);

      } catch (NumberFormatException nfe) {
        operator = word;
      }
    }
  }

  private static int sum() {
    int result = 0;
    for (Integer i : operands) result += i;
    return result;
  }

  private static final String TERMINATE = "/exit";
  private static final String DELIM = " ";
  private static String operator;
  private static List<Integer> operands;

}
