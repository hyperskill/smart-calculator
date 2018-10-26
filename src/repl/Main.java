package repl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int sum, endNumber, beginNumber, count;
        int[] arr = new int[2];

        String s;

        while(true)
        {
            arr[0] = 0;
            arr[1] = 0;
            beginNumber = 0;
            count = 0;
            s = in.nextLine();
            if(s.equals("/exit"))
            {
                System.out.println("Bye!");
                break;
            }
            if (!s.isEmpty())
            {
                for(int i = 0; i < s.length(); i++)
                {
                    if (s.charAt(i) == ' '){
                        endNumber = i;
                        arr[count] = Integer.parseInt(s.substring(beginNumber, endNumber));
                        count++;
                        beginNumber = endNumber + 1;
                    }
                    else if (i == s.length()-1){
                        arr[count] = Integer.parseInt(s.substring(beginNumber, s.length()));
                    }
                }
                sum = arr[0] + arr[1];
                System.out.println(sum);
            }
        }
    }
}