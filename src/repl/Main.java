package repl;
import java.util.Scanner;

public class Main {

  private static boolean running = true;
  private static String helpString = "This program evaluates additions and subtractions.";

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String input;
    while(running) {
      input = in.nextLine();
      if(input.length() == 0) continue;
      if(input.equals("/exit")) {
        running = false;
        System.out.println("Bye!");
        continue;
      } else if(input.equals("/help")) {
        System.out.println(helpString);
        continue;
      }
      String[] commands = input.replaceAll(" +", " ").split("\\s");
      int s = 0;
      int sign = 1;
      for(String command : commands) {
        if(command.matches("[+-]+")) {
          sign = 1;
          for (char ch : command.toCharArray())
            if (ch == '-') sign *= -1;
        } else {
          s = s + sign * Integer.parseInt(command);
          sign = 1;
        }
      }
      System.out.println(s);
    }
  }
}