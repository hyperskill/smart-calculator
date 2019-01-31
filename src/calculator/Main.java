package calculator;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.*;

public class Main {

    static Map<String, String> map = new HashMap<>();

    static int sum = 0; //while now sum is result

    public static void main(String[] args) {
        begin();
    }

    static class WrongCommandException extends Exception {
    }

    static class WrongBracket extends RuntimeException {
    }

    static class EmptyLineException extends WrongCommandException {
    }

    static class WrongBackslashCommandException extends WrongCommandException {
    }

    static class WrongVariable extends Exception {
    }

    static class WrongValue extends WrongVariable {
    }

    static class VariableNotSet extends RuntimeException {
    }

    static void LineValidation(String line) throws WrongCommandException {
        if (line.matches("^\\s*/\\w.*")) {
            throw new WrongCommandException();
        }
        if (line.matches("^\\s*/\\s+\\w.*")) {
            throw new WrongCommandException();
        }
        if (line.matches("^\\s*\\\\\\w.*")) {
            throw new WrongBackslashCommandException();
        }
        if (line.equals("")) {
            throw new EmptyLineException();
        }
    }

    static void VariableValidation(String[] dig_array) throws WrongVariable {
        if (dig_array.length != 2) {
            throw new WrongVariable();
        }
        if (!dig_array[1].matches("[a-zA-Z]|-*\\d+")) {
            throw new WrongValue();
        }
        if (!dig_array[0].matches("[a-zA-Z]+")) {
            throw new WrongVariable();
        }
    }

    static void VariableExist(String dig) throws VariableNotSet {
        if (!map.containsKey(dig)) {
            throw new VariableNotSet();
        }
    }

    static void IfWrongBracket (Queue<String> pack) throws WrongBracket {
        if (pack.contains("(")) {
            throw new WrongBracket();
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
                        "/help - this text.\n" +
                        "Keep a clean nose\n" +
                        "Watch the plain clothes\n" +
                        "You don’t need a weatherman\n" +
                        "To know which way the wind blows\n");
                continue;
            }

            //ошибки ввода начало.
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
            //ошибки ввода конец.

            // если вводится переменная
           if (line.matches("^\\s*\\w+\\s*=\\s*-*\\s*\\w+\\s*$")) {
                variableProcessing(line);
                continue;
            }

            //чистим строку от двойных пробелов
            line = clearingLine(line);
            String result;
            try {
                result = postfixNotation(line);
                calculatePostfix(result);
            } catch (WrongBracket e) {
                System.out.println("Invalid expression (not balanced parenthesis).");
            } catch (VariableNotSet e) {
                System.out.println("Unknown variable.");
            } catch (NumberFormatException | NoSuchElementException e) {
                System.out.println("Invalid expression.");
            } finally {
                map.clear();
                sum = 0;
            }
        }
    }

    //sigh... let's do it.Postfix notation start.
    static String postfixNotation (String line){
        Queue<String> pack = new ArrayDeque<>();

        Scanner sc = new Scanner(line);
        String value;
        Pattern pat_operand = Pattern.compile("-*[a-zA-Z0-9]+");
        Pattern pat_operator = Pattern.compile("(\\(|\\)|\\+|\\*|-|/)");
        String result="";

        while (sc.hasNext()){
            value = sc.next();
            Matcher match_operand = pat_operand.matcher(value); //если число
            if (match_operand.matches()){
                result=result + " " + value ;
                continue;
            }

            Matcher match_operator = pat_operator.matcher(value); //если оператор
            if (match_operator.matches()) {


                //если в стэке пусто
                if (pack.isEmpty() || ((ArrayDeque<String>) pack).peekLast().equals("(")) {
                    ((ArrayDeque<String>) pack).offerLast(value);
                    continue;
                }
                //скобочки
                if (value.equals("(")) {
                    ((ArrayDeque<String>) pack).offerLast(value);
                    continue;
                }
                if (value.equals(")")) {
                    while (!pack.isEmpty() && !((ArrayDeque<String>) pack).peekLast().equals("(")) {
                        result = result + " " + ((ArrayDeque<String>) pack).removeLast();
                    }
                    if (((ArrayDeque<String>) pack).peekLast().equals("(")) ((ArrayDeque<String>) pack).removeLast();
                    continue;
                }
                //+ -
                if (value.equals("+") || value.equals("-")) {
                        while (!pack.isEmpty() && !((ArrayDeque<String>) pack).peekLast().equals("(")) {
                                result = result + " " + ((ArrayDeque<String>) pack).removeLast();
                        }
                    ((ArrayDeque<String>) pack).offerLast(value);
                        continue;
                }

                // * /
                if (value.equals("*") || value.equals("/")) {
                    if (((ArrayDeque<String>) pack).peekLast().equals("+") || ((ArrayDeque<String>) pack).peekLast().equals("-")) {
                        ((ArrayDeque<String>) pack).offerLast(value);
                        continue;
                    }
                    if (((ArrayDeque<String>) pack).peekLast().equals("*") || ((ArrayDeque<String>) pack).peekLast().equals("/")) {
                        while (!pack.isEmpty()) {
                            if (((ArrayDeque<String>) pack).peekLast().equals("(") || ((ArrayDeque<String>) pack).peekLast().equals("-") || ((ArrayDeque<String>) pack).peekLast().equals("+")) {
                                break;
                            }
                                result = result + " " +  ((ArrayDeque<String>) pack).removeLast();
                        }
                        ((ArrayDeque<String>) pack).offerLast(value);
                        continue;
                    }
                }
            }
        }

        IfWrongBracket(pack);

        while (!pack.isEmpty()) {
            result = result + " " + ((ArrayDeque<String>) pack).removeLast();
        }
        return result;
    }
    //Postfix notation end.

    static void calculatePostfix(String line) {
        line = line.replaceAll("^ ", "");

        Queue<String> pack = new ArrayDeque<>();
        Scanner sc = new Scanner(line).useDelimiter(" +");
        String value;
        BigInteger poped1;
        BigInteger poped2;
        while (sc.hasNext()) {
            value = sc.next();

            if (value.matches("-*\\d+")) {
                ((ArrayDeque<String>) pack).addLast(value);
            }
            if (value.matches("[a-zA-Z]+")) {
                ((ArrayDeque<String>) pack).addLast(setVariables(value));
            }
            if (value.matches("(-|\\+|\\*|/)")) {

                poped1 = intProcessing(((ArrayDeque<String>) pack).removeLast());
                poped2 = intProcessing(((ArrayDeque<String>) pack).removeLast());
                if (value.equals("-")) {
                    ((ArrayDeque<String>) pack).addLast("" + (poped2.subtract(poped1)));
                }
                if (value.equals("+")) {
                    ((ArrayDeque<String>) pack).addLast("" + (poped2.add(poped1)));
                }
                if (value.equals("/")) {
                    ((ArrayDeque<String>) pack).addLast("" + (poped2.divide(poped1)));
                }
                if (value.equals("*")) {
                    ((ArrayDeque<String>) pack).addLast("" + (poped2.multiply(poped1)));
                }
            }
        }
        System.out.println(((ArrayDeque<String>) pack).removeLast());
    }

    static String clearingLine(String line) {
        line = line.replaceAll(" ", "");
        line = line.replaceAll("(--)+", "+");
        line = line.replaceAll("\\++", "+");
        line = line.replaceAll("(\\+-)|(-\\+)", "-");

        line = line.replaceAll("-", " - ");
        line = line.replaceAll("\\*", " * ");
        line = line.replaceAll("/", " / ");
        line = line.replaceAll("\\+", " + ");
        line = line.replaceAll("\\(", " ( ");
        line = line.replaceAll("\\)", " ) ");
        line = line.replaceAll(" +", " ");

        line = line.replaceAll("^ ", "");
        line = line.replaceAll("^- ", "-");
        line = line.replaceAll("\\( - ", "( -");
        return line;
    }

    static void variableProcessing(String line) {
        line = line.replaceAll("(^\\s+)|(\\s+$)", "");
        line = line.replaceAll("\\s*=\\s*", " = ");
        line = line.replaceAll("\\s+", " ");
        Pattern pat = Pattern.compile("\\s=\\s");
        String[] dig_array = pat.split(line);
        try {
            VariableValidation(dig_array);
        } catch (WrongValue e) {
            System.out.println("Wrong value.");
        } catch (WrongVariable e) {
            System.out.println("Whatever you typed, it's not variable.\n" +
                    "Variable should initialize as a = 1, b = 51, veganpizza = 1000.\n" +
                    "Latin characters only, no spaces or digits in variable name.");
        }
        if (dig_array[1].matches("-*\\d+")) {
            map.put(dig_array[0], dig_array[1]);
        } else {
            try {
                VariableExist(dig_array[1]);
                map.put(dig_array[0], map.get(dig_array[1]));
            } catch (RuntimeException e) {
                System.out.println("Unknown variable '" + dig_array[1] + "'.");
            }
        }
    }

    static String setVariables (String dig){
        VariableExist(dig);
        return map.get(dig);
    }

    static BigInteger intProcessing(String dig) {
        return new BigInteger(dig);
    }

} //main end
