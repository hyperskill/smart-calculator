package calculator;

import java.util.ArrayDeque;
import java.util.Deque;

public class RPN {
    private final String OPERATORS = "[+*-/()]";

    public long parse(String[] values) {
        Deque<Long> digits = new ArrayDeque<>();
        Deque<Character> operators = new ArrayDeque<>();

        if (values.length == 2) {
            return Long.parseLong(values[0] + values[1]);
        }

        for (String value : values) {


            if (!value.matches(OPERATORS)) {
                digits.push(Long.valueOf(value));
                continue;
            }

            if (value.matches(OPERATORS)) {
                char operator = value.charAt(0);

                if (operators.peek() == null) {
                    operators.push(operator);
                    continue;
                }

                if ('(' == operator) {
                    operators.push(operator);
                    continue;
                }

                if (')' == operator) {
                    while (operators.peek() != '(') {
                        long result = calculate(digits.pollFirst(), digits.pollFirst(), operators.pollFirst());
                        digits.push(result);
                    }
                    operators.poll();
                    continue;
                }

                while (operators.peek() != null) {
                    if (priority(operators.peek()) >= priority(operator)) {
                        long result = calculate(digits.pollFirst(), digits.pollFirst(), operators.pollFirst());
                        digits.push(result);
                    } else {
                        operators.push(operator);
                        break;
                    }
                }
                if (operators.size() == 0) {
                    operators.push(operator);
                }
            }
        }

        while (digits.size() != 1) {
            digits.push(calculate(digits.pollFirst(), digits.pollFirst(), operators.pollFirst()));
        }

        return digits.poll();
    }

    private long calculate(long firstValue, long secondValue, char operator) {
        long result = 0;
        switch (operator) {
            case '+':
                result = firstValue + secondValue;
                break;
            case '-':
                result = secondValue - firstValue;
                break;
            case '*':
                result = firstValue * secondValue;
                break;
            case '/':
                result = secondValue / firstValue;
                break;
        }
        return result;
    }

    private int priority(char operator) {
        if (operator == '+' || operator == '-') return 1;
        if (operator == '*' || operator == '/') return 2;
        if (operator == '(' || operator == ')') return 0;
        return -1;
    }
}
