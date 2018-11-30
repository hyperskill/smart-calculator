package repl;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner a = new Scanner(System.in);
        Map<String, Integer> vars = new HashMap<>();
        boolean vare = true;
        while (true) {
            boolean check = true;
            CheckString(a);
            String l = a.nextLine();
            int s = 0;
            String[] num = l.split("\\s+");

            int k = 2;
            int sign = 1;
            int countNumber = 0;
            int countSign = 0;
            for (int i = 0; i < num.length; i++) {
                if (num.length == 1) {
                    if (vars.containsKey(num[0])) {
                        check = false;
                        System.out.println(vars.get(num[0]));
                        break;
                    } else if (num[0].matches("[a-zA-Z]+")) {
                        System.out.println("Unknown variable");

                        check = false;
                        break;
                    } else
                        try {
                            Integer e = Integer.parseInt(num[num.length - 1]);
                            vars.put(num[0], e);
                            check = false;
                            break;
                        } catch (Throwable p) {

                            System.out.println("Invalid expression");
                            check = false;
                            break;
                        }
                } else if (num[1].matches("=")) {


                    if (num[0].matches("[a-zA-Z]+") && num.length <= 3) {
                        try {
                            if (vars.containsKey(num[2])) {
                                vars.put(num[0], vars.get(num[2]));
                                check = false;
                                break;
                            } else if (num[2].matches("[a-zA-Z]+")) {
                                System.out.println("Unknown variable");

                                check = false;
                                break;
                            } else {
                                Integer e = Integer.parseInt(num[num.length - 1]);
                                vars.put(num[0], e);
                                check = false;
                                break;
                            }
                        } catch (Throwable y) {
                            System.out.println("Invalid value");
                            check = false;
                            break;
                        }

                    } else if (num[0].matches(".+") && num.length <= 3) {
                        System.out.println("Invalid Identifier");
                        check = false;
                        break;
                    } else if (num.length > 3) {

                        System.out.println("Invalid Assignment");
                        check = false;
                        break;
                    }

                } else if (k % 2 == 0) {
                    try {
                        if (num[i].matches("[a-zA-Z]+")) {
                            if (vars.containsKey(num[i])) {
                                int u = vars.get(num[i]);
                                s += u * sign;
                                countNumber++;
                            } else {
                                System.out.println("Unknown variable");
                                check = false;
                                break;
                            }
                        } else {
                            int u = Integer.parseInt(num[i]);
                            s += u * sign;
                            countNumber++;
                        }
                    } catch (Throwable y) {
                        System.out.println("Invalid expression");
                        check = false;
                        break;
                    }
                } else {
                    if (num[i].charAt(0) == '-' || num[i].charAt(0) == '+') {
                        countSign++;
                        if (num[i].charAt(0) == '-') {
                            if (num[i].length() % 2 == 1)
                                sign = -1;
                            else
                                sign = 1;
                        } else
                            sign = 1;
                    } else {
                        System.out.println("Invalid expression");
                        check = false;
                        break;
                    }

                }
                k++;

            }
            if (countSign != countNumber - 1 && check) {
                check = false;
                System.out.println("Invalid expression");
            }
            if (check) {
                System.out.println(s);
            }
        }

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