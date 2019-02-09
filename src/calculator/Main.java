package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("/exit")) {
            if (line.length() > 0) {

                String[] numbers = line.split(" ");

                int result = 0;
                for (String number:
                        numbers) {
                    result += Integer.parseInt(number);
                }

                System.out.println(result);
            }

            line = scanner.nextLine();
        }

        System.out.print("Bye!");

    }

}