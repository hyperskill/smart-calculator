package calculator;
import java.util.*;

public class Main {
    public static void main(String[] args) {
       String exit = "/exit";
        int a, b;
        String input1;
        String input2;
        Scanner sc1 = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);

        while (true) {


            input1 = sc1.nextLine();
            if (input1.equals(exit)) {
                System.out.println("Bye");
                break;
            }
            input2 = sc2.nextLine();
            if (input2.equals(exit)) {
                System.out.println("Bye");
                break;
            }


            if (input1.equals("") && input2.equals("")) {
                continue;
            }

            if (input1.equals("") || input2.equals("")) {
                System.out.println((input1.equals("")) ? input2 : (input2.equals("")) ? input1 : "");
                continue;
            }
            a = Integer.parseInt(input1);
            b = Integer.parseInt(input2);
            System.out.println(a + b);
        }
    }
}
