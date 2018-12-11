package repl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int result = 0;
        int sub = 0;
        boolean yes = true;
        while (yes) {
            String operation = scanner.nextLine();
            while (yes) {
                while (operation.equals("/help")) {
                    System.out.println("This code is work!");
                    operation = scanner.nextLine();
                }
                while (operation.equals("/exit")) {
                    System.out.println("Hasta la vista!");
                    yes = false;
                    break;
                }
                while (operation.matches("\\S*")&&!operation.matches("\\d*")&&yes) {
                    System.out.println("Invalid sequence of symbols to calculate!");
                    operation = scanner.nextLine();
                }
                if (operation.matches("[^A-Za-z]*")) {
                    break;
                }
            }
            while (yes) {
                String[] arr = operation.split("\\s+");
                String mOperation = "";
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].matches("\\++")) {
                        mOperation += " ";
                    } else if (arr[i].matches("\\-")&&!arr[i+1].matches("\\-")) {
                        arr[i + 1] = '-' + arr[i + 1];
                        arr[i] = " ";
                        mOperation += " ";
                    }else if (arr[i].matches("\\-{2}|\\-{4}|\\-{6}|\\-{8}")){
                        mOperation+= " ";
                    }else if (arr[i].matches("\\-{3}|\\-{5}|\\-{7}|\\-{9}")){
                        arr[i + 1] = '-' + arr[i + 1];
                        arr[i] = " ";
                        mOperation += " ";
                    }
                    else mOperation += arr[i];
                }
                String[] arr2 = mOperation.split("\\s+");
                for (String ch : arr2) {
                    result += Integer.parseInt(ch);
                }
                System.out.println(result);
                result = 0;
                break;
            }
        }
    }
}
