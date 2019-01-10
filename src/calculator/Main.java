package calculator;

public class Main {
    public static void main(String[] args) {
     
        int sum;
        String exit = "/exit";
        String help = "/help";


        while (true) {
            sum= 0;
            Scanner sc = new Scanner(System.in);
            input = sc.nextLine();


            if(input.equals(""))
            continue;



            if(input.equals(exit))
            {
                System.out.println("Bye");
                break;
            }

            if(input.equals(help))
            {
                System.out.println("The program calculates the sum of numbers");
                continue;
            }



            for (String number : input.split(" ")) {
                sum += Integer.parseInt(number);
            }
            System.out.println(sum);



        }
    }
}
