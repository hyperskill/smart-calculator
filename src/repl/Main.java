import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.*;

public class Main{
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String exit = "";
    while (!exit.equalsIgnoreCase("/exit")) {
      String s = br.readLine();

      // Pattern for multiple \\s

      Pattern split = Pattern.compile("\\s+");
      Matcher m = split.matcher(s);

      s = m.replaceAll(" ");

      //Pattern for -- 
      split = Pattern.compile("\\s{1}-{1}-{1}\\s{1}");
      m = split.matcher(s);

      s = m.replaceAll(" + ");

      //Pattern for ---*
      split = Pattern.compile("\\s{1}-{3,}\\s{1}");
      m = split.matcher(s);
      s = m.replaceAll(" - ");

      //Pattern for ++*
      split = Pattern.compile("\\s{1}\\+{2,}\\s{1}");
      m = split.matcher(s);
      s = m.replaceAll(" + ");

     // System.out.println(s);

     	String[] strings = s.split(" ");

      exit = strings[0];

      //Section for exiting
      if (exit.equalsIgnoreCase("/exit")){
        System.out.println("Bye!");
        break;

      }else if (exit.equalsIgnoreCase("/help")){ 							//Section for helping
      	System.out.println("The program supporting the addition \"+\" and subtraction \"-\" operators" + 
      		"\n The program calculating expressions like these: 4 + 6 - 8, 2 - 3 - 4, and so on.\n\n" + 
      		"If you has entered several operators following each other,\n" + 
      		" the program still working (like Java or Python REPL).\n Interpret two adjacent minus signs as a plus. " + 
      		"\"--\" => \"+\" and \"---*\" => \"-\".");
      	continue;

      }else if (strings.length ==1 && !exit.equalsIgnoreCase("/exit")){
        System.out.println(strings[0]);

      }else if (strings.length > 1) {
      	int digit = 0;
      	String regex = "-*\\d+";
      	String regexMinus = "-{1}";
      	boolean plus = true;
      	for (int i = 0; i < strings.length; i++) {
      		if (strings[i].matches(regex)) {
      			if (plus) {
      				digit = addition(digit, Integer.parseInt(strings[i]));

      			}else {
      				digit = subtraction(digit, Integer.parseInt(strings[i]));
      			}

      		}else {
      			if (strings[i].matches(regexMinus)){
      				plus = false;

      			}else {
      				plus = true;
      			}
      		}
      	}
      	System.out.println(digit);
      }


    } 


  }

  //Method for Addition
  private static int addition(int a, int b){
  	int sum = a + b;
  	return sum;
  }
  // Method for subtraction
  private static int subtraction(int a, int b){
  	int sub = a - b;
  	return sub;
  }
}

