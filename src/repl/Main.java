package repl;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
            Scanner a = new Scanner(System.in);
            do {
                boolean check = true;
                CheckString(a);
                String l = a.nextLine();
                int s = 0;
                String[] num = l.split("\\s+");
                int k = 2;
                int sign = 1;
                int countNumber = 0;
                int countSign = 0;
                for (String n : num) {
                    if (k % 2 == 0) {
                        try {
                            int u = Integer.parseInt(n);
                            s += u * sign;
                            countNumber++;
                        }catch(Throwable y)
                        {
                            System.out.println("Invalid expression");
                            check = false;
                            break;
                        }
                    } else {
                        if(n.charAt(0)=='-'||n.charAt(0)=='+') {
                            countSign++;
                            if (n.charAt(0) == '-') {
                                if (n.length() % 2 == 1)
                                    sign = -1;
                                else
                                    sign = 1;
                            } else
                                sign = 1;
                        }
                        else {
                            System.out.println("Invalid expression");
                            check = false;
                            break;
                        }

                    }
                    k++;

                }
                if (countSign!=countNumber-1&& check)
                {
                    check = false;
                    System.out.println("Invalid expression");
                }
                if (check) {
                    System.out.println(s);
                }
            }while(true);

    }

    public static void CheckString(Scanner a) {
        while(true) {
            if (a.hasNext("/help")) {
                System.out.println("The program сan add and sub numbers, -- = +");
                String y = a.nextLine();

            } else if (a.hasNext("/exit")) {
                System.out.println("Bye!");
                System.exit(0);
            } else if (a.hasNext("[/].*")) {
                System.out.println("Unknown command");
                String y = a.nextLine();
            } else {
                break;
            }


        }

    }
}