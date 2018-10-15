package repl;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner a = new Scanner(System.in);
        int s = 0;
        if (a.hasNext("/help")) {
            System.out.println("The program сan add and sub numbers, -- = +");
            String y = a.nextLine();
        }

        if (a.hasNext("/exit")) {
            System.out.println("Bye!");
            return;
        }

        String l = a.nextLine();
        String[] num = l.split("\\s+");
        int k = 2;
        int sign = 1;
        for (String n : num) {
            if (k % 2 == 0) {
                int u = Integer.parseInt(n);
                s += u * sign;
            }
            else {
                if (n.charAt(0)=='-') {
                    if (n.length() % 2 == 1)
                        sign = -1;
                    else
                        sign = 1;
                }
                else
                    sign = 1;

            }
            k++;

        }
        System.out.print(s);

    }
}