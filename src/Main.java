import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str;
        String[] strs;
        int ans = 0;
        while (true) {
            str = scanner.nextLine().trim();

            if (str != null && !str.isEmpty()) {

                if (str.equals("/exit")) {
                    System.out.println("Bye!");
                    break;

                } else if (str.equals("/help")) {
                    System.out.println("The program calculates the sum of numbers");

                } else if (str.contains(" ")) {
                    strs = str.split("\\s+");
                    for (int i = 0; i < strs.length; i++) {
                        ans += Integer.parseInt(strs[i]);
                    }
                    System.out.println(ans);
                    ans = 0;

                } else { // If there was only one number or non-service string
                    System.out.println(str);
                }
            }
        }
    }

}