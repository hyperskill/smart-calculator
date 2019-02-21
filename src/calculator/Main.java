import java.util.*;
import java.lang.*;
import java.io.*;

public class Main
{
    public static void main (String[] args) throws java.lang.Exception
    {
        int total = 0;
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
	while(!expression.equals("/exit")) {
		int one = 1;
		int summation = 0;
		//"регулярки" мне тут понадобились только для того, чтобы убрать пробелы
		//с регулярками мое решение будет навскидку O(n ** 2), это же решение O(n)
		expression = expression.replaceAll("[\\s\\t]+", "");
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
		System.out.println(total);
		total = 0;
		expression = scanner.nextLine();
	}
    }
}

