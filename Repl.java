import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Repl class implements a simple REPL.
 * It supports addition and substraction including unary.
 * It calculates the expressions like these: 4 + 6 - 8, 2 - 3 - 4 and so on.
 * \help command explains these operations
 * /exit command terminates application
 */
public class Repl {
  public static void main(String[] args) {
    boolean cont = true;
    while (cont) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();

        if (line != null && line.length() > 0) {
          switch(line.trim()) {
            case "/exit":
              cont = false;
              break;
            case "\\help":
              System.out.println("Program calculates the expressions like these: 4 + 6 - 8, 2 - 3 - 4 and so on. It supports both unary and binary minuses. Enter '/exit' to terminate program.");
              break;
            default:
              try {
                String[] postfix =  infixToPostfix(line.replaceAll("\\s+"," ").split(DELIM));
                int res = calculatePostfix(postfix);
                System.out.println("Result = " + res);
              } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
              }
          }//eof switch
        }//eof if
      }//eof while
      System.out.println("Bye!");
    }//eof main

  /**
   * Evaluates arithmetic expression in postfix notation.
   * Simplified Dijkstra algorithm: supports only + and - operations
   *
   * @param postfix arithmetic expression in postfix notation
   * @return result of calculation
  **/
  private static int calculatePostfix(String[] postfix) throws IllegalArgumentException {
    if (postfix != null && postfix.length > 0) {
      Deque<Integer> res = new LinkedList<Integer>();       //results stack
      res.addFirst(0);                                      //add dummy value to allow unary operations
      for (String word : postfix) {
        if (isOperand(word)) {
          res.addFirst(Integer.parseInt(word));
        } else if (word.contains("-") || word.contains("+")) {
            int operand1 = (Integer) res.removeFirst();
            int operand2 = (Integer) res.removeFirst();
            int result = 0;
            if (word.equals("-")) {
              result = operand2 - operand1;
            } else if (word.equals("+")) {
              result = operand1 + operand2;
            } else throw new IllegalArgumentException("Unsupported operation " + word);
            res.addFirst(result);
        } else throw new IllegalArgumentException("Can't process expression " + word);
      }
      return (Integer) res.removeFirst();
    } else throw new IllegalArgumentException("Expression is null or zero length");
  }

  /**
   * Converts infix expression to postfix notation.
   *
   * @param words arithmetic expression in infix notation
   * @return expression in postfix notation
  **/
  private static String[] infixToPostfix(String[] words) throws IllegalArgumentException {
    if (words != null && words.length > 0) {
      String[] result = new String[words.length];
      int i = 0;
      Deque<String> stack = new LinkedList<String>();
      for (String word : words){
          if (word.contains("-") || word.contains("+")) {
              String operation = convertOperation(word);
              if (stack.size() > 0) {
                result[i++] = (String)stack.removeFirst();
              }
              stack.addFirst(operation);
            } else if (isOperand(word)) {
              result[i++] = word;
            } else throw new IllegalArgumentException("Unsupported expression: " + word);
      }
      if (stack.size() > 0) {
        result[i++] = (String)stack.removeFirst();
      }
      return result;
    } else throw new IllegalArgumentException("Passed array is null or empty");
  }

  /**
   * Checks wheter String is operand (int value)
   * @param word string to check
   * @return true if provided string can be converted to int, false otherwise
  **/
  private static boolean isOperand (String word) {
    if (word != null) {
      try {
        Integer.parseInt(word);
      } catch (NumberFormatException e) {
        return false;
      }
    } else return false;
    return true;
  }

  /**
   * Converts operations to unified form.
   * E.g: --- = -, ++++ = +, -- = + etc.
   * @param word operation in raw form
   * @return operation in unified form: + or -
   * @throws IllegalArgumentException if operation can not be converted to unified form
  **/
  private static String convertOperation (String word) throws IllegalArgumentException {
    String plusPattern = "[^\\+]";
    Pattern plus = Pattern.compile(plusPattern);
    String minusPattern = "[^\\-]";
    Pattern minus = Pattern.compile(minusPattern);
    Matcher plusMatcher = plus.matcher(word);
    Matcher minusMatcher = minus.matcher(word);
    if (!plusMatcher.find()) {
      return "+";
    } else if (!minusMatcher.find()) {
      if (word.length() % 2 == 0) return "+";
      else return "-";
    } else {
      throw new IllegalArgumentException("Unsupported operation: " + word);
    }
  }

  private static final String DELIM = " ";
}
