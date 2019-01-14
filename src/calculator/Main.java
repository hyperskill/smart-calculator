package calculator;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.*;

public class Main {

    static int sum = 0; //while now sum is result

    public static void main (String[] args) {
        begin();

    }

    static void begin() {

        Scanner sc = new Scanner(System.in);
        String line = "";

        while (!line.equals("/exit")) {

            line = sc.nextLine();
            if (line.equals("/exit")) break;
            if (line.equals("/help")) {
                System.out.println("The program calculates the sum of numbers.\n" +
                        "Put something like -67 + 5 - 34 in line and you will get result.");
            }
            line = clearingLine(line);
            processing(line);

        }

        System.out.println("Bye!");

    }

    static void sum(int digit) {
        sum = sum + digit;

    }


    static String clearingLine(String line) {

        line = line.replaceAll(" ", "");
        line = line.replaceAll("^", "+");
        line = line.replaceAll("(--)+", "+");
        line = line.replaceAll("\\++", "+");
        line = line.replaceAll("(\\+-)|(-\\+)", "-");
        line = line.replaceAll("\\+", " +");
        line = line.replaceAll("-", " -");
        line = line.replaceAll("^ ", "");
        return line;

    }

    static void processing(String line) {
        Pattern pat = Pattern.compile("(?:\\s)");
        String[] dig_array = pat.split(line);

        for (String dig : dig_array) {
            sum(Integer.parseInt(dig));
        }

        System.out.println("= " + sum);
        sum = 0;
    }

} //main end
