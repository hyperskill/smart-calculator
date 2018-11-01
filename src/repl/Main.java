package repl;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static int sumCounter(String[] ar){
        int sum = 0;
        for (int i = 0; i < ar.length; i++){
            if(ar[i].equals(""))
                sum += 0;
            else
                sum += Integer.parseInt(ar[i]);
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s;
        Pattern pattern = Pattern.compile("^[+-]*\\d+\\s*([+-]+\\s*\\d+)*");
        Matcher matcher;
        boolean matches;

        while(true)
        {
            s = in.nextLine();
            String s1 = s.replaceAll("\\s{2,}", " ");
            matcher = pattern.matcher(s1);
            matches = matcher.matches();

            s = s1.replaceAll(" ", "");
            s1 = s.replaceAll("[-]{2}", "+");
            s = s1.replaceAll("[-]","+-");
            String[] arrS = s.split("[+]+");

            if(s.equals("/exit"))
            {
                System.out.println("Bye!");
                break;
            }
            if(s.equals("/help")) {
                System.out.println("The program calculates the sum of numbers if you print addition operator \"+\" \n" +
                        "The program calculates the subtraction of numbers if you print subtraction operator \"-\"");
                continue;
            }
            if(!matches) {
                System.out.println("Unknown command");
                continue;
            }
            System.out.println(sumCounter(arrS));
        }
    }
}
