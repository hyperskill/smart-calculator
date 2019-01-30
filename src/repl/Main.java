package repl;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> map = new HashMap<>();
        boolean run = true;
        while (run){
            String symbols = scanner.nextLine();
            if (symbols.equals("/exit")){
                System.out.println("Bye!");
                run = false;
                break;
            }else if (symbols.equals("/help")){
                System.out.println("This is a smart calculator operating any latin variable, and for now," +
                        "perform only simple arithmetic operations.(try to broke it)");
                continue;
            }
            String [] s = symbols.trim().split("\\s*[=]+\\s*");
            String [] s1 = symbols.trim().split("\\s+");
            for (int i = 0; i < s.length; i++) {
                if (s1.length>3&& s.length>3 || s.length == 1 && s1.length>3) {
                    int sum = 0;
                    for (int j = 0; j < s1.length; j++) {
                        if (map.containsKey(s1[j])){
                        }else if (s1[j].matches("[+]+")){
                            if (j>2){
                                if (map.containsKey(s1[j-1])&& map.containsKey(s1[j+1])) {
                                    sum = sum + map.get(s1[j + 1]);
                                }else if (map.containsKey(s1[j-1])&& s1[j+1].matches("[0-9]+")){
                                    sum = sum + Integer.parseInt(s1[j+1]);
                                }else if (s1[j-1].matches("[0-9]+")&& map.containsKey(s1[j+1])){
                                    sum = sum + map.get(s1[j+1]);
                                }
                            }else {
                                if (map.containsKey(s1[j-1])&& map.containsKey(s1[j+1])) {
                                    sum += map.get(s1[j - 1]) + map.get(s1[j + 1]);
                                }else {
                                    sum+= sum + Integer.parseInt(s1[j+1]);
                                }
                            }
                        }else if (s1[j].matches("[-]+")) {
                            if (j > 2) {
                                if (map.containsKey(s1[j - 1]) && map.containsKey(s1[j + 1])) {
                                    sum = sum - map.get(s1[j + 1]);
                                }else if (map.containsKey(s1[j-1])&& s1[j+1].matches("[0-9]+")){
                                    sum = sum - Integer.parseInt(s1[j+1]);
                                }else if (s1[j-1].matches("[0-9]+")&& map.containsKey(s1[j+1])){
                                    sum = sum - map.get(s1[j+1]);
                                }
                            } else {
                                if (map.containsKey(s1[j - 1]) && map.containsKey(s1[j + 1])) {
                                    sum += map.get(s1[j - 1]) - map.get(s1[j + 1]);
                                } else {
                                    sum += sum - Integer.parseInt(s1[j + 1]);
                                }
                            }
                        }
                    }
                    System.out.println(sum);
                    break;
                }
                if (s.length == 1&&map.containsKey(s[0])) {
                    System.out.println(map.get(s[0]));
                    break;
                }
                if (!s[0].matches("[a-zA-Z]+")){
                    System.out.println("Invalid identifier");
                    break;
                }else if (map.isEmpty() && s.length == 1|| !map.containsKey(s[0]) && s.length==1){
                    System.out.println("Unknown variable");
                    break;
                }else if (!s[1].matches("[a-zA-Z]+|[0-9]+")){
                    System.out.println("Invalid value");
                    break;
                }else if (s.length == 3){
                    System.out.println("Invalid assignment");
                    break;
                }else if (!map.containsKey(s[1]) && !map.isEmpty() && s[1].matches("[a-zA-Z]+")){
                    System.out.println("Unknown variable");
                    break;
                }else if (map.isEmpty() && s[1].matches("[a-zA-Z]+")){
                    System.out.println("Unknown variable");
                    break;
                }
                if (s.length == 1){
                    System.out.println(map.get(s[0]));
                    continue;
                }else if (map.containsKey(s[1])) {
                    map.put(s[0], map.get(s[1]));
                }else {
                    map.put(s[0], Integer.parseInt(s[1]));
                }
            }
        }
    }
}
