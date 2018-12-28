package repl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main{
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String exit = "";
    while (!exit.equalsIgnoreCase("/exit")) {
      String[] s = br.readLine().split(" ");
      exit = s[0];
      if (exit.equalsIgnoreCase("/exit")){
        System.out.println("Bye!");
        break;
      }
      else if (s.length ==1 && !exit.equalsIgnoreCase("/exit")){
        System.out.println(s[0]);
      }
      else if (s.length > 1) {
        int a = Integer.parseInt(s[0]);
        int b = Integer.parseInt(s[1]);

        System.out.println(a + b);
      }

    } 


  }
}
}
