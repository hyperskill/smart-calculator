package calculator;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SmartCalculator {

    static void runCalculator() {

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("/exit")) {

            if (line.equals("/help")) {
                System.out.println("Any help here");
            }
            else if (line.length() > 0) {

                Matcher matcher = Pattern.compile("(^|[ +-])+\\d+").matcher(line);

                int result = 0;
                while (matcher.find()) {
                    result += getNumberFromString(matcher.group());
                }

                System.out.println(result);

            }

            line = scanner.nextLine();
        }

        System.out.print("Bye!");

    }

    private static int getNumberFromString(String group) {

        char[] charArray = group.toCharArray();
        boolean isNegative = false;
        int charPositionOfZero = (int)'0';
        int result = 0;

        for (char symbol:
             charArray) {
            if (symbol == '-') isNegative = !isNegative;
            if (symbol >= charPositionOfZero&& symbol < charPositionOfZero+10) {
                result = result*10+(int)symbol-charPositionOfZero;
            }
            if (isNegative) result = -result;
        }

        return result;
    }

}
