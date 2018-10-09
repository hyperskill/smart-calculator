package repl;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner a = new Scanner(System.in);
        int s = 0;
        for(int i =0;i<2;i++)
        {
            String p = a.next();
            if (p.equals("/exit"))
            {
                System.out.println("Bye!");
                return;
            }
            int u = Integer.parseInt(p);
            s+=u;


        }
        System.out.print(s);
    }
}