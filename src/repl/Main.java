package repl;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int sum, endNumber, beginNumber, count;
        String s;

        while(true)
        {
            beginNumber = 0;
            count = 0;
            s = in.nextLine();
            String[] arrS = s.split(" ");
            int[] arr = new int[arrS.length + 1]; //if only 1 number was printed without space, we need at list 1 element in array
            if(s.equals("/exit"))
            {
                System.out.println("Bye!");
                break;
            }
            if(s.equals("/help"))
            {
                System.out.println("The program calculates the sum of numbers");
            }
            else if (!s.isEmpty())
            {
                sum = 0;
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
                for (int i = 0; i< arr.length; i++)
                    sum += arr[i];

                System.out.println(sum);
            }
        }
    }
}