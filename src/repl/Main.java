package repl;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner a = new Scanner(System.in);
        int s = 0;
        if (a.hasNext("/help")){
                System.out.println("The program calculates the sum of numbers");
                String y = a.nextLine();
        }

        if (a.hasNext("/exit")) {
                System.out.println("Bye!");
                return;
        }

        String l = a.nextLine();
        l = l.replaceAll("\\s","");
        char o[]=l.toCharArray();
        for (char r:o) {
            int u = Character.getNumericValue(r);
            s+=u;
        }
        System.out.print(s);
    }
}