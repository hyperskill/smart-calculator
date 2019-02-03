package calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
         Scanner scanner = new Scanner(System.in);

         boolean isExit = false;

         while (!isExit) {

             int num1 = scanner.nextInt();
             int num2 = scanner.nextInt();

             String exit = scanner.nextLine();

             if (exit.endsWith("/exit")) {
                 isExit = true;
                 System.out.println("Bye!");
                 break;
             }

             System.out.println(num1 + num2);

         }
    }
}