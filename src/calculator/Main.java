package calculator;
import java.util.Scanner;

class Main {

    static int i;

    public static void main (String[] args) {


        Scanner sc = new Scanner(System.in);
        String line="";
        i=0;
        while (!line.equals("/exit")) {
            line = sc.nextLine();
            int[] arr = scanning(line);
            if (!line.equals("/exit")) System.out.println(sum(arr));
        }
        System.out.println("Bye!");
    }


    static int sum(int[] arr) {
        int s=0;
        for(int j=0;j<i; j++ ){
            s=arr[j]+s;
        }
        return s;
    }

    static int[] scanning(String line){
        Scanner scint = new Scanner(line);
        int[] arr=new int[25];
        while(scint.hasNextInt()){
            arr[i] = scint.nextInt();
            i++;
        }

        return arr;
    }
}