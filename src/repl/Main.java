package repl;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;


public class Main {
    static Map<String, String> inputs = new HashMap<String, String>();
    static Deque<String> postfixStack = new ArrayDeque<String>();
    static Deque<BigInteger> resStack = new ArrayDeque<BigInteger>();
    static Deque<Operand> operStack = new ArrayDeque<Operand>();

    static Pattern digits = Pattern.compile("\\d+");
    static Pattern opers = Pattern.compile("[+-/*/()^]");

    public static String spaceKiller(String s){
        s = s.replaceAll(" ", "");//kill all spaces
        s = s.replaceAll("[-]{2}","+");//double minus to plus
        s = s.replaceAll("[+][-]", "-");//if number of minuses is odd stays only 1 minus
        return s.replaceAll("[+]+","+");//if multiple pluses stays only 1 plus
    }

    public static int commandChecker(String s){
        if(s.equals("/exit"))
        {
            System.out.println("Bye!");
            return -1;   //quit the program
        }
        if(s.equals("/help")) {
            System.out.println("The program supports multiplication \"*\", integer division \"/\", \n" +
                    "addition \"+\", subtraction \"-\" and parentheses. Also, variables can be used, for instance: \n" +
                    "a = 1 \nb = 3\na + b\n");
            return 1;//print help message and continue
        }
        return 0; //parse the input
    }

    public static int assignChecker(String s){
        Pattern id = Pattern.compile("[a-zA-z]+=.*");
        Pattern val = Pattern.compile("[a-zA-Z]+=[0-9]+=?[\\d]*");
        Pattern assign = Pattern.compile("[a-zA-Z]+(=[0-9]+)");

        if (!id.matcher(s).matches()) {
            System.out.println("Invalid identifier");
            return 1;
        } else if ((!val.matcher(s).matches()) && !inputs.containsKey(s.split("[=]")[1])) {
            System.out.println("Invalid value");
            return 1;
        } else if (!assign.matcher(s).matches()) {
            System.out.println("Invalid assignment");
            return 1;
        }
        return 0;
    }

    public static int putVarToMap(String s){
        String varName = s.split("[=]")[0];
        if(digits.matcher(s.split("[=]")[1]).matches()) {
            String varValue = s.split("[=]")[1];
            inputs.put(varName, varValue);
        }else if(inputs.containsKey(s.split("[=]")[1])){
            inputs.put(varName, inputs.get(s.split("[=]")[1]));
        }else{
            System.out.println("Unknown variable");
            return 1;
        }
        return 0;
    }



    public static int formRPNStack(char[] operators){
        String s = "";
        for(int i = 0; i < operators.length; i++){
            Operand incomeOperand = new Operand(operators[i]);

            if (inputs.containsKey(Character.toString(operators[i]))) {
                postfixStack.push(inputs.get(Character.toString(operators[i])));
                continue;

            } else if (digits.matcher(Character.toString(operators[i])).matches()) {
                if(i > 0 && !digits.matcher(Character.toString(operators[i-1])).matches())
                    s = Character.toString(operators[i]);
                else if(i == 0)
                    s = Character.toString(operators[i]);

                if(i < operators.length-1 && digits.matcher(Character.toString(operators[i+1])).matches()){
                    s = s + Character.toString(operators[i+1]);
                }
                else {
                    postfixStack.push(s);
                }
                continue;
            } else if(!opers.matcher(Character.toString(operators[i])).matches()){
                System.out.println("Unknown variable");
                return 1;
            }

            if (operStack.isEmpty() || operStack.peek().getInstance() == '(' || incomeOperand.getInstance() == '(' ||
                    (incomeOperand.getPriority() > operStack.peek().getPriority()))

            {
                operStack.push(incomeOperand);
            } else if ((incomeOperand.getPriority() <= operStack.peek().getPriority()) && incomeOperand.getInstance()!=')')
            {
                while (!operStack.isEmpty() && (incomeOperand.getPriority() <= operStack.peek().getPriority()))
                {
                    postfixStack.push(operStack.pop().getInstance().toString());
                }
                operStack.push(incomeOperand);
            } else if (incomeOperand.getInstance() == ')') {
                while (operStack.peek().getInstance() != '(')
                    postfixStack.push(operStack.pop().getInstance().toString());
                if (operStack.peek().getInstance() == '(') {
                    operStack.pop();
                }
            } else {
                operStack.push(incomeOperand);
            }
        }
        while (!operStack.isEmpty()){
            if(operStack.peek().getInstance() == '(' || operStack.peek().getInstance() == ')'){
                System.out.println("Invalid expression");
                return 1;
            }
            postfixStack.push(operStack.pop().getInstance().toString());
        }
        return 0;
    }

    public static void calcResult(){
        ////calculation of postfix //////////////////////
        while (!postfixStack.isEmpty()){
            if(digits.matcher(postfixStack.peekLast()).matches()){
                resStack.push(new BigInteger(postfixStack.pollLast()));
            }else if(opers.matcher(postfixStack.peekFirst()).find()){
                BigInteger a = resStack.pollFirst();
                BigInteger b = resStack.pollFirst();
                switch (postfixStack.pollLast().charAt(0)){
                    case '+':
                        resStack.addFirst(a.add(b));
                        break;
                    case '-':
                        resStack.addFirst(b.subtract(a));
                        break;
                    case '*':
                        resStack.addFirst(a.multiply(b));
                        break;
                    case '/':
                        resStack.addFirst(b.divide(a));
                        break;
                    case '^':
                        resStack.addFirst(a.pow(b.intValue()));
                        break;
                }
            }
        }
        System.out.println(resStack.peekFirst());
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int flag;
        String s;


        while(true) {
            s = in.nextLine();
            s = spaceKiller(s);//remove all no-needed characters
            flag = commandChecker(s);//check whether string equals some command
            if(flag == -1)
                break;
            else if(flag == 1)
                continue;

            resStack.clear();
            postfixStack.clear();
            operStack.clear();
            if(s.contains("=")) {
                flag = assignChecker(s);//check whether assignment is correct
                if (flag == 1)
                    continue;
                putVarToMap(s);//put vars to map
            }else{ //printed string does not contains "=", therefore it is might be an expression
                flag = formRPNStack(s.toCharArray());
                if (flag == 1)
                    continue;
                calcResult();
            }
        }
    }
}
