import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String line = sc.nextLine();
            if (line.isEmpty()) {
                continue;
            } else if (line.equals("/help")) {
                System.out.println("The program calculates the sum of numbers");
                continue;
            } else if (line.equals("/exit")) {
                System.out.println("Bye!");
                break;
            }
            String[] terms = line.split("\\s+");
            int[] numbers = Arrays.stream(terms).mapToInt(Integer::parseInt).toArray();
            System.out.printf("%d\n", Arrays.stream(numbers).sum());
        }
    }
}
