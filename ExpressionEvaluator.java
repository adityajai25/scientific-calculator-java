import java.util.Stack;

public class ExpressionEvaluator {
    public double evaluate(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operations = new Stack<>();
        Stack<String> functions = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                StringBuilder number = new StringBuilder();
                while (Character.isDigit(c) || c == '.') {
                    number.append(c);
                    i++;
                    if (i < expression.length()) {
                        c = expression.charAt(i);
                    } else {
                        break;
                    }
                }
                i--;
                numbers.push(Double.parseDouble(number.toString()));
            } else if (c == '(') {
                operations.push(c);
            } else if (c == ')') {
                while (operations.peek() != '(') {
                    numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
                }
                operations.pop();
                if (!functions.isEmpty()) {
                    numbers.push(applyFunction(functions.pop(), numbers.pop()));
                }
            } else if (isOperator(c)) {
                while (!operations.isEmpty() && hasPrecedence(c, operations.peek())) {
                    numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
                }
                operations.push(c);
            } else if (isFunctionStart(c)) {
                StringBuilder func = new StringBuilder();
                while (i < expression.length() && Character.isAlphabetic(expression.charAt(i))) {
                    func.append(expression.charAt(i));
                    i++;
                }
                i--;
                functions.push(func.toString());
            }
        }

        while (!operations.isEmpty()) {
            numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        } else {
            return true;
        }
    }

    private double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new UnsupportedOperationException("Cannot divide by zero");
                }
                return a / b;
            case '^':
                return Math.pow(a, b);
        }
        return 0;
    }

    private boolean isFunctionStart(char c) {
        return c == 's' || c == 'c' || c == 't' || c == 'l' || c == 'e';
    }

    private double applyFunction(String function, double value) {
        switch (function) {
            case "sin":
                return Math.sin(Math.toRadians(value));
            case "cos":
                return Math.cos(Math.toRadians(value));
            case "tan":
                return Math.tan(Math.toRadians(value));
            case "sqrt":
                return Math.sqrt(value);
            case "log":
                return Math.log10(value);
            case "exp":
                return Math.exp(value);
        }
        return 0;
    }
}
