package calculator;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("/exit")) {

            if (line.equals("/help")) {
                System.out.println("Any help here");
            }
            else if (line.length() > 0) {

                String[] numbersAndOperators = splitLineByNumbersAndOperators(line);

                int result = 0;
                for (String numberOrOperator:
                        numbersAndOperators) {
                    System.out.println(numberOrOperator);
//                    result += Integer.parseInt(numberOrOperator);
                }

//                System.out.println(result);
            }

            line = scanner.nextLine();
        }

        System.out.print("Bye!");

    }

    private static String[] splitLineByNumbersAndOperators(String line) {
        Pattern patternNumberOrOperator = Pattern.compile("(\\d+|[\\+-]+)");
        Matcher matcher = patternNumberOrOperator.matcher(String);
        matcher.
    }

}