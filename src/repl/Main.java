package repl;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static int inputChecker(String str) {
        Pattern id = Pattern.compile("[a-zA-z]+=.*");
        Pattern val = Pattern.compile("[a-zA-Z]+=[0-9]+=?.*");
        Pattern assign = Pattern.compile("[a-zA-Z]+(=[0-9]+)");
        Pattern equal = Pattern.compile("[=]");

        if(equal.matcher(str).find()) {
            if (!id.matcher(str).matches()) {
                System.out.println("Invalid identifier");
            } else if (!val.matcher(str).matches()) {
                System.out.println("Invalid value");
            } else if (!assign.matcher(str).matches()) {
                System.out.println("Invalid assignment");
            } else
                return 1;

            return -1;
        }
        return 1;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Map<String, Integer> inputs = new HashMap<String, Integer>();
        int sum = 0;
        String s;
        Pattern plusMinus = Pattern.compile("[+-]");
        Pattern equal = Pattern.compile("[=]");
        Pattern digits = Pattern.compile("\\d+");
        Pattern id = Pattern.compile("[a-zA-z]+=.*");
        Pattern val = Pattern.compile("[a-zA-Z]+=[0-9]+=?.*");
        Pattern assign = Pattern.compile("[a-zA-Z]+(=[0-9]+)");

        while(true)
        {
            s = in.nextLine();
            s = s.replaceAll(" ", "");

            if(s.equals("/exit"))
            {
                System.out.println("Bye!");
                break;
            }
            if(s.equals("/help")) {
                System.out.println("The program calculates the sum of numbers if you print addition operator \"+\" \n" +
                        "The program calculates the subtraction of numbers if you print subtraction operator \"-\"");
                continue;
            }

            if(equal.matcher(s).find()) {
                if (!id.matcher(s).matches()) {
                    System.out.println("Invalid identifier");
                    continue;
                } else if ((!val.matcher(s).matches()) && !inputs.containsKey(s.split("[=]")[1])) {
                    System.out.println("Invalid value");
                    continue;
                } else if (!assign.matcher(s).matches()) {
                    System.out.println("Invalid assignment");
                    continue;
                }
            }

            if(!equal.matcher(s).find()){//plusMinus.matcher(s).find()) {
                sum = 0;
                String[] vars = s.split("[+-]");
                String[] signs = s.split("([a-zA-Z]+)|([0-9]+)");
                for(int i = 0; i < vars.length; i++){
                    if(inputs.containsKey(vars[i])){
                        if(plusMinus.matcher(s).matches()){
                            if(signs[i].equals("-")) {
                                sum -= inputs.get(vars[i]);
                            }else {
                                sum += inputs.get(vars[i]);
                            }
                        }else
                            sum += inputs.get(vars[i]);
                    }else if(digits.matcher(vars[i]).matches()){
                        if(plusMinus.matcher(s).matches()) {
                            if (signs[i].equals("-")) {
                                sum -= Integer.parseInt(vars[i]);
                            } else {
                                sum += Integer.parseInt(vars[i]);
                            }
                        }else
                            sum += Integer.parseInt(vars[i]);
                    }else{
                        System.out.println("Unknown variable");
                    }
                }
                System.out.println(sum);
            }else {
                String varName = s.split("[=]")[0];
                if(digits.matcher(s.split("[=]")[1]).matches()) {
                    Integer varValue = Integer.parseInt(s.split("[=]")[1]);
                    inputs.put(varName, varValue);
                }else if(inputs.containsKey(s.split("[=]")[1])){
                    inputs.put(varName, inputs.get(s.split("[=]")[1]));
                }else{
                    System.out.println("Unknown variable");
                }
            }
        }
    }
}
