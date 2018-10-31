package repl;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int sum;
        String s;

        while(true)
        {
            sum = 0;
            s = in.nextLine();

            String s1 = s.replaceAll("\\s+", "");
            String s2 = s1.replaceAll("[-]{2}", "+");
            s1 = s2.replaceAll("[-]","+-");
            String[] arrS = s1.split("[+]+");

            if(s.equals("/exit"))
            {
                System.out.println("Bye!");
                break;
            }
            if(s.equals("/help")) {
                System.out.println("The program calculates the sum of numbers if you print addition operator \"+\" \n" +
                        "The program calculates the subtraction of numbers if you print subtraction operator \"-\"");
            }

            for (int i = 0; i< arrS.length; i++){
                if(arrS[i].equals(""))
                    sum += 0;
                else
                    sum += Integer.parseInt(arrS[i]);
            }
            System.out.println(sum);
        }
    }
}