package repl;
import java.util.Scanner;

public class Main {

  private static boolean running = true;

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
      }
      String[] commands = input.split("\\s");
      if(commands.length == 1) {
        System.out.println(Integer.parseInt(commands[0]));
      }
      else {
        int a = Integer.parseInt(commands[0]);
        int b = Integer.parseInt(commands[1]);
        System.out.println(a + b);
      }
    }
  }
}