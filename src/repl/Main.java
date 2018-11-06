package repl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String num;
        boolean on = true;
        Scanner in = new Scanner(System.in);
        do {
            num = in.nextLine();
            if (num.length()==0)
                continue;
            if (num.equals("/exit")) {
                System.out.println("Bye!");
                on = false;
                break;
            }
            String[]strings = num.split("\\s");
            if (strings.length==1){
                System.out.println(Integer.parseInt(strings[0]));
            }else {
                int a = Integer.parseInt(strings[0]);
                int b = Integer.parseInt(strings[1]);
                System.out.println(a+b);
            }
        }while (on);


    }
}
