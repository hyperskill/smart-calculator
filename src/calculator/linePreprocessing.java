import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.regex.*;

/*
 * метод preprocessing проверяет корректность входных данных и в случае корректного ввода
 * вызывает другой метод для расчета результата выражения
 */

class linePreprocessing 
{
	void preprocessing() {
		Scanner sc = new Scanner(System.in);
		String expression = sc.nextLine();
		while(!expression.equals("/exit")) {
			Pattern onlyDigitsAndOperation = Pattern.compile("[^0-9\\+\\-\\s\\t]"); 
			Pattern secondRegex = Pattern.compile("/.*"); 
			Pattern noSymbols = Pattern.compile("[0-9][\\s\\t]+[0-9]");

			Matcher matcher = secondRegex.matcher(expression);
			if (matcher.find()) {System.out.println("Unknown command");}
			else {

				matcher = onlyDigitsAndOperation.matcher(expression);
				if (matcher.find()) {System.out.println("Invalid expression");}
				else {
					matcher = noSymbols.matcher(expression);
					if (matcher.find()) {System.out.println("Invalid expression");}
					else {
						expression = expression.replaceAll("[\\s\\t]+", "");
						char symbol = expression.charAt(expression.length() - 1);
						if (symbol == '+' || symbol == '-') {System.out.println("Invalid expression");}
						else {System.out.println(this.calculation(expression));}
					}
				}
			}
			expression = sc.nextLine();
		}
	}

	int calculation (String expression) {
		int total = 0;
		int one = 1; //это множитель, может равняться -1
		int summation = 0;

		for (int i = 0; i < expression.length(); i++) {
		    char value = expression.charAt(i);
		    switch(value) {
			case '+':
			    break;
			case '-':
			    one *= -1;
			    break;
			default:
			    char tryGetValue = expression.charAt(i);
			    for (int j = 0; i + j < expression.length(); j++) {
				char find = expression.charAt(i + j);
				if (find == '+' || find == '-' || i + j == expression.length() - 1) {
					String number = "";
					if (i + j != expression.length() - 1) {
					    number = expression.substring(i, i + j);
					    summation = Integer.parseInt(number);
					    i = i + j - 1;
					}
					else {
						number = expression.substring(i, i + j + 1);
					    summation = Integer.parseInt(number);
					    i = i + j + 1;
					}
				    j = expression.length();
				}
			    }
			    total += one * summation;
			    one = 1;
			    break;
		    }
		}
		return total;
	}
}
