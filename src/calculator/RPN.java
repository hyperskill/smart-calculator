package calculator;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;

public class RPN {
    private final String OPERATORS = "[+*-/()^]";

    public BigInteger parse(String[] values) {
        Deque<BigInteger> digits = new ArrayDeque<>();
        Deque<Character> operators = new ArrayDeque<>();

        if (values.length == 2) {
            return new BigInteger(values[0] + values[1]);
        }

        for (String value : values) {


            if (!value.matches(OPERATORS)) {
                digits.push(new BigInteger(value));
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
                        BigInteger result = calculate(digits.pollFirst(), digits.pollFirst(), operators.pollFirst());
                        digits.push(result);
                    }
                    operators.poll();
                    continue;
                }

                while (operators.peek() != null) {
                    if (priority(operators.peek()) >= priority(operator)) {
                        BigInteger result = calculate(digits.pollFirst(), digits.pollFirst(), operators.pollFirst());
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

    private BigInteger calculate(BigInteger firstValue, BigInteger secondValue, char operator) {
        BigInteger result = BigInteger.ZERO;
        switch (operator) {
            case '+':
                result = firstValue.add(secondValue);
                break;
            case '-':
                result = secondValue.subtract(firstValue);
                break;
            case '*':
                result = firstValue.multiply(secondValue);
                break;
            case '/':
                result = secondValue.divide(firstValue);
                break;
            case '^':
                result = secondValue.pow(firstValue.intValue());
                break;
        }
        return result;
    }

    private int priority(char operator) {
        if (operator == '+' || operator == '-') return 1;
        if (operator == '*' || operator == '/') return 2;
        if (operator == '^') return 3;
        if (operator == '(' || operator == ')') return 0;
        return -1;
    }
}
