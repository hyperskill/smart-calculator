import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static boolean cycle = true;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (cycle){
            String data = reader.readLine();

            String[] strings = patternSet(data);
            System.out.println("------------------------------------------------------------------------------");

            if (strings.length == 1){
                if (isCommand(strings[0])){
                    switch (strings[0]){
                        case "/exit":
                            cycle = false;
                            System.out.println("Bye!");

                            break;
                        case "/help":
                            System.out.println("------------------------------------------------------------------------------");
                            System.out.println("Available commands : /help (for helping) ; /exit (for exiting)");
                            System.out.println("------------------------------------------------------------------------------");
                            System.out.println("The program supporting the addition \"+\" and subtraction \"-\" operators" +
                                    "\n The program calculating expressions like these: 4 + 6 - 8, 2 - 3 - 4, and so on.\n\n" +
                                    "If you has entered several operators following each other,\n" +
                                    " the program still working (like Java or Python REPL).\n Interpret two adjacent minus signs as a plus. " +
                                    "\"--\" => \"+\" and \"---*\" => \"-\".");
                            System.out.println("------------------------------------------------------------------------------");
                            break;
                            default:
                                System.out.println("Uncnown command : " + strings[0]);
                                break;

                    }
                    continue;
                }else {
                    if (strings[0].matches("\\+{1}\\d+")){
                        String s = strings[0].substring(1);
                        System.out.println(s);
                        continue;
                    }else if (strings[0].matches("-{1}\\d+")){
                        System.out.println(strings[0]);
                        continue;
                    }else if (strings[0].matches("[a-zA-Z=]+")||strings[0].matches("\\d+[+=-]")){
                        System.out.println("Invalid expression");
                        continue;
                    }
                }
            }
            
            if (strings.length == 2){
                System.out.println("Invalid expression");
                continue;
            }
            long c = Long.parseLong(strings[0]);
            String symbol = "";
            for (int i = 1; i < strings.length; i++) {
                if (strings[i].matches("([-+])")){
                    symbol = strings[i];
                    continue;
                }
                c = simpleExpression(c, symbol, Long.parseLong(strings[i]));
            }
            System.out.println(c);

            
            

        }
    }

    public static String[] patternSet(String s){
        String temp;

        Pattern pattern = Pattern.compile("\\s+");
        Matcher matcher = pattern.matcher(s);
        temp = matcher.replaceAll(" ");

        pattern = Pattern.compile("\\s{1}-{1}-{1}\\s{1}");
        matcher = pattern.matcher(temp);
        temp = matcher.replaceAll(" + ");

        pattern = Pattern.compile("\\s{1}-{3,}\\s{1}");
        matcher = pattern.matcher(temp);
        temp = matcher.replaceAll(" - ");

        pattern = Pattern.compile("\\s{1}\\+{2,}\\s{1}");
        matcher = pattern.matcher(temp);
        temp = matcher.replaceAll(" + ");

        String[] strings = temp.split("\\s");
        return strings;

    }
    public  static boolean isCommand(String s){
        if (s.matches("/{1}.*")){
            return true;
        }else return false;

    }

    public static long simpleExpression(long a, String s, long b){
        long answer = 0;
        switch (s){
            case "+":
                answer = a + b;
                break;
            case "-":
                answer = a - b;
                break;
        }
       return answer;
    }
}
