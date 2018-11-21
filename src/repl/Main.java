package repl;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a;
        int b;
        String str = sc.nextLine();
        while (!(str.equals("/exit"))) {
            String str2 = sc.nextLine();
            if (str.isEmpty() && str2.isEmpty()) {
                System.out.println();
            } else if (str.isEmpty() && !str2.isEmpty()) {
                System.out.println(str2);
            } else if (!str.isEmpty() && str2.isEmpty()) {
                System.out.println(str);
            } else {
                a = Integer.parseInt(str);
                b = Integer.parseInt(str2);
                System.out.println(a + b);
            }
            str = sc.nextLine();
        }
        System.out.println("Bye!");

    }

}
