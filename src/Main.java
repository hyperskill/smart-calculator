import java.util.Scanner;

public class Main {

    public static boolean isServiceString(String input){
        return input.startsWith("/");
    }

    public static boolean isValidExpression(String input) {
        return input.matches("[\\d\\+\\-\\s]+");
    }

    public static boolean answerToServiceCommand(String input){
        boolean stop_iteration = false;
        if (input.equals("/exit")) {
            System.out.println("Bye!");
            stop_iteration = true;

        } else if (input.equals("/help")) {
            System.out.println("The program serves as microcalculator");

        }
        return stop_iteration;
    }

    public static void calculateAndPrintExpression(String input){
        String[] numbers_and_operators = input.split("\\s+");
        int ans = 0;
        int number = 0;
        int n_minuses = 0;
        boolean next_minus = false;
        for (int i = 0; i < numbers_and_operators.length; i++) {
            if (numbers_and_operators[i].matches("-?\\d+")) {
                number = Integer.parseInt(numbers_and_operators[i]);
                if (next_minus) {
                    ans -= number;
                } else {
                    ans += number;
                }
            } else if (numbers_and_operators[i].matches("[+\\-]+")) {
                n_minuses = numbers_and_operators[i].replaceAll("[^\\-]", "").length();
                next_minus = n_minuses % 2 == 1;
            } else {
                System.out.println("Wrong input");
            }
        }
        System.out.println(ans);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str;
        while (true) {
            str = scanner.nextLine().trim();

            if (!str.isEmpty()) {

                if (isServiceString(str)) {
                    if (answerToServiceCommand(str)) {
                        break;
                    }
                } else if (isValidExpression(str)) {
                    calculateAndPrintExpression(str);

                } else { // Invalid string input
                    System.out.println(str);
                }
            }
        }
    }

}