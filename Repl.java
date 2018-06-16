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
          if (line.contains("/") || line.contains("\\")) {
            switch (line.trim()) {
              case "/help":
                System.out.println("Program calculates the expressions like these: 4 + 6 - 8, 2 - 3 - 4 and so on. It supports both unary and binary minuses. Enter '/exit' to terminate program.");
                break;
              case "\\exit":
                cont = false;
                break;
              default:
                System.out.println("Unsupported command");
            } //
          } else {
            try {
              String[] postfix =  infixToPostfix(lineToInfix(line));
              int res = calculatePostfix(postfix);
              System.out.println("Result = " + res);
            } catch (IllegalArgumentException e) {
              System.out.println(e.getMessage());
            }
          }
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
        } else if ((word.contains("-") || word.contains("+")) && res.size() >= 2) {
            int operand1 = (Integer) res.removeFirst();
            int operand2 = (Integer) res.removeFirst();
            int result = 0;
            if (word.equals("-")) {
              result = operand2 - operand1;
            } else if (word.equals("+")) {
              result = operand1 + operand2;
            } else throw new IllegalArgumentException("Unsupported operation " + word);
            res.addFirst(result);
        } else throw new IllegalArgumentException("Can't process an expression");
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
      boolean prevIsOperation = true;
      for (String word : words){
          if (word.matches("[\\+]+") || word.matches("[-]+")) {
              String operation = convertOperation(word);
              if (stack.size() > 0) {
                result[i++] = (String)stack.removeFirst();
              }
              stack.addFirst(operation);
              prevIsOperation = true;
            } else if (isOperand(word) && prevIsOperation) {
              result[i++] = word;
              prevIsOperation = false;
            } else throw new IllegalArgumentException("Unsupported expression");
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


  private static String[] lineToInfix(String line) throws IllegalArgumentException {
    String[] expr = line.replaceAll("\\s+"," ").split(DELIM);
    String[] result = new String[expr.length];
    int i = 0;

    for (String word : expr) {
      if ((!word.matches("[0-9+-]+")) || (word.contains("-") && word.contains("+"))) throw new IllegalArgumentException("Unsupported expression: " + word);
      if (word.matches("[\\+]+[0-9]+")) {
        word = word.replaceAll("\\+","");
      } else if (word.matches("[-]+[0-9]+")){
        Pattern pattern = Pattern.compile("\\-");
        Matcher matcher = pattern.matcher(word);
        int count = 0;
        while (matcher.find()) {
          count++;
        }
        if (count % 2 != 0) {
          word = "-" + word.replaceAll("\\-","");
        } else {
          word = word.replaceAll("\\-","");
        }
      }
      result[i++] = word;
    }
      return result;
  }

  private static final String DELIM = " ";
}
