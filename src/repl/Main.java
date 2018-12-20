package repl;

import java.util.Scanner;

public class aMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean yes = true;
        do {
           int result = 0;
            String operation = scanner.nextLine();
            if (operation.equals("/exit")) {
                System.out.println("Bye!");
                break;
            } else if (operation.equals("/help")) {
                System.out.println("This program calculate numbers ");
                continue;
            } else if (operation.matches("/.+")) {
                System.out.println("Unknown command");
                continue;
            } else if (operation.matches("[a-zA-Z]+")) {
                System.out.println("Invalid expression");
                continue;
            }else if (operation.matches("\\d+[-+]")){
                System.out.println("Invalid expression");
                continue;
            }else if (operation.matches("\\d+\\s+\\d+")){
                System.out.println("Invalid expression");
                continue;
            }
            System.out.println(calculate(operation));
            }
            while (yes) ;
        }
        public static int calculate(String operation){
        int result = 0;
            String[] arr = operation.split("\\s+");
            String mOperation = "";
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].matches("\\+")) {
                    mOperation += " ";
                } else if (arr[i].matches("\\-")) {
                    arr[i + 1] = '-' + arr[i + 1];
                    arr[i] = " ";
                    mOperation += " ";
                } else mOperation += arr[i];
            }
            String[] arr2 = mOperation.split("\\s+");
            for (String ch : arr2) {
                result += Integer.parseInt(ch);
            }
            return result;
        }
    }
