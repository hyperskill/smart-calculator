package calculator;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.*;

public class Main {

    static int sum = 0; //while now sum is result

    public static void main (String[] args) {
        begin();
    }

    static class WrongCommandException extends Exception {
    }
    static class EmptyLineException extends WrongCommandException {
    }
    static class WrongBackslashCommandException extends WrongCommandException {
    }
    static void LineValidation(String line) throws WrongCommandException {
        if(line.matches("^\\s*/\\w.*")){
            throw new WrongCommandException();
        }
        if(line.matches("^\\s*/\\s+\\w.*")){
            throw new WrongCommandException();
        }
        if(line.matches("^\\s*\\\\\\w.*")){
            throw new WrongBackslashCommandException();
        }
        if(line.equals("")){
            throw new EmptyLineException();
        }
    }

    static void begin() {
        Scanner sc = new Scanner(System.in);
        String line = "";

        while (!line.equals("/exit")) {
            line = sc.nextLine();

            if (line.equals("/exit")) {
                System.out.println("Bye!");
                break;
            }
            if (line.equals("/help")) {
                System.out.println("The program calculates the sum of numbers.\n" +
                        "Type something like -67 + 5 - 34 and you will get the result.\n" +
                        "Commands:\n" +
                        "/exit  -  type it to exit program.\n" +
                        "/help - this text.");
                continue;
            }
            try {
                LineValidation(line);
            } catch (EmptyLineException e) {
                System.out.println("The line is empty. Enter some math expression to calculate.");
                continue;
            } catch (WrongBackslashCommandException e) {
                System.out.println("It seems like you tried to enter command, but started it with backslash.\n" +
                        "Type /help to see full command list.");
                continue;
            } catch (WrongCommandException e) {
                System.out.println("Unknown command.\n" +
                        "Type /help to see full command list.");
                continue;
            }
            line = clearingLine(line);
            processing(line);
        }
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
        try {
        for (String dig : dig_array) {
            sum(Integer.parseInt(dig));
        }
        System.out.println("= " + sum);
        } catch (NumberFormatException e) {
            System.out.println("Invalid expression");
        } finally {
            sum = 0;
        }
    }

} //main end
