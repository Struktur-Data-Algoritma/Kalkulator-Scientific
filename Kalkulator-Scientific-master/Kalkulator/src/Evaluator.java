import java.util.ArrayList;
import java.util.List;

/**
 * Evaluator class created the binary tree according to the expression given.
 * Internal nodes hold operators and external nodes hold numbers.
 * The tree is then traversed to produce a single value result (type double).
 *
 * @author Luka Kralj
 * @version 30 March 2018
 */
public class Evaluator {
    public static final char SQRT = 'V';
    public static final char SQUARE = 'S';
    public static final char CUBICS = 'I';
    public static final char SIN = 'N';
    public static final char COS = 'C';
    public static final char TAN = 'T';
    public static final char LOG = 'L';
    public static final char PHI = 'P';
    public static final char FAK = 'F';
    public static final char CUBIC = 'C';
    public static final char LON = 'O';
    
  
    private static final char[] nonNumeric = {'+', '-', '/', '*', '%', '(', ')', SQRT, SQUARE, SIN, COS, TAN, LOG, PHI, FAK, CUBICS, CUBIC, LON};
    private BinaryTree tree;

    private String result;

    /**
     * Create a new evaluator for the given expression.
     *
     * @param expression Expression to be evaluated.
     */
    public Evaluator(String expression) {
        String str = replace(expression);
        str = getValidSubstring(str);
        if (getOperator(str) == -1) {
            result = "" + extractNumber(str);
        }
        else {
            makeTree(str);
            result = "" + evaluateTree(tree.root());
        }
    }

    /**
     *
     * @return The value of the expression as string, "Err" if there was an error when evaluating.
     */
    public String getResult() {
        if (result == null) {
            return "Err";
        }
        return result;
    }

    /**
     * Extracts the number from the string. The string can be in format of ((34.5)) for example.
     *
     * @param str String with the numerical value. It can include brackets.
     * @return Value parsed from the string as double.
     */
    private double extractNumber(String str) {
        str = str.replace('(', ' ');
        str = str.replace(')', ' ');
        str = str.trim();
        return Double.parseDouble(str);
    }

    /**
     * Creates new tree.
     *
     * @param expression Expression to be traversed to make an appropriate tree.
     */
    private void makeTree(String expression) {
        expression = getValidSubstring(expression);
        int index = getOperator(expression);
        if (index == -1) {
            // There are no operators. The expression is a single value.
            double value = extractNumber(expression);
            tree = new BinaryTree(new Node(value, null, null));
        }
        else {
            char c = expression.charAt(index);
            if (isUnary(c)) {
                // If the operator is a unary operator the node will only have one child.
                String newExpression = "";
                if (c == SQRT) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else if (c == SIN) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else if (c == COS) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else if (c == TAN) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else if (c == LOG) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else if (c == CUBIC) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else if (c == CUBICS) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else if (c == LON) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else {
                    newExpression = expression.substring(0, index);
                }
                tree = new BinaryTree(new Node(c, getNewChild(getValidSubstring(newExpression)), null));
            }
            else {
                String leftExpression = getValidSubstring(expression.substring(0, index));
                String rightExpression = getValidSubstring(expression.substring(index + 1, expression.length()));
                tree = new BinaryTree(new Node(c, getNewChild(leftExpression), getNewChild(rightExpression)));
            }
        }
    }

    /**
     * Returns the position of the next operator. First it checks for pluses and minuses since they
     * are evaluated the last. Then it checks for multiplication and division and finally for unary operators.
     *
     * In the expression 1 - 2 + 3 the last operator is firstly returned to produce the result
     * same as (1-2) + 3.
     *
     * @param str Expression on which we search for the next operator.
     * @return Position of the operator in the given string. -1 if no operator is found.
     */
    private int getOperator(String str) {
        int bracketCounter = 0;
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') { bracketCounter++; }
            else if (c == ')') { bracketCounter--; }
            else if ((c == '+' || c == '-') && bracketCounter == 0) {
                indexes.add(i);
            }
        }

        if (indexes.size() > 0) {
            return indexes.get(indexes.size()-1);
        }

        bracketCounter = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') { bracketCounter++; }
            else if (c == ')') { bracketCounter--; }
            else if ((c == '*' || c == '/') && bracketCounter == 0) {
                indexes.add(i);
            }
        }

        if (indexes.size() > 0) {
            return indexes.get(indexes.size()-1);
        }

        bracketCounter = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') { bracketCounter++; }
            else if (c == ')') { bracketCounter--; }
            else if (isUnary(c) && bracketCounter == 0) {
                indexes.add(i);
            }
        }
        if (indexes.size() > 0) {
            return indexes.get(indexes.size()-1);
        }

        return -1;
    }

    /**
     *
     * @param operator Operator to check.
     * @return True if the operator is unary operator.
     */
    private boolean isUnary(char operator) {
        return operator == '%' || operator == SQRT || operator == SQUARE || operator == SIN || operator == COS || operator == TAN || operator == LOG || operator == PHI || operator == FAK || operator == CUBICS || operator == CUBIC || operator == LON;
    }

    /**
     * Creates a new node to be added to the tree. Children of each node are created recursively
     * to produce te full tree.
     *
     * @param expression Expression for which we need to produce a sub-tree for.
     * @return Root node of the sub-tree.
     */
    private Node getNewChild(String expression) {
        expression = getValidSubstring(expression);
        int index = getOperator(expression);
        if (index == -1) {
            double value = extractNumber(expression);
            return new Node(value, null, null);
        }
        else {
            char c = expression.charAt(index);
            if (isUnary(c)) {
                String newExpression = "";
                if (c == SQRT) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else if (c == SIN) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else if (c == COS) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else if (c == TAN) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else if (c == LOG) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else if (c == CUBIC) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else if (c == CUBICS) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else if (c == LON){
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else {
                    newExpression = expression.substring(0, index);
                }
                return new Node(c, getNewChild(getValidSubstring(newExpression)), null);
            }
            else {
                String leftExpression = getValidSubstring(expression.substring(0, index));
                String rightExpression = getValidSubstring(expression.substring(index + 1, expression.length()));
                return new Node(c, getNewChild(leftExpression), getNewChild(rightExpression));
            }
        }
    }

    /**
     * Evaluates a binary tree with the root node v. The method works recursively.
     *
     * @param v Root node of the tree (or part of the sub-tree) we want to evaluate.
     * @return Double value of expression represented by the tree (or sub-tree)
     */
    private double evaluateTree(Node v) {
        if (tree.isInternal(v)) {
            char op = (char)v.element();
            if (isUnary(op)) {
                if (op == SQRT) {
                    return Math.sqrt(evaluateTree(v.getLeft()));
                }
                else if (op == CUBIC) {
                    return Math.cbrt(evaluateTree(v.getLeft()));
                }
                else if (op == SQUARE) {
                    double i = evaluateTree(v.getLeft());
                    return i*i;
                }
                else if (op == CUBICS) {
                    double i = evaluateTree(v.getLeft());
                    return i*i*i;
                }
                else if (op == '%') {
                    return evaluateTree(v.getLeft())/100;
                }
                else if (op == SIN) {
                    return Math.sin(evaluateTree(v.getLeft())*(Math.PI/180));
                }
                else if (op == COS) {
                    return Math.cos(evaluateTree(v.getLeft())*(Math.PI/180));
                }
                else if (op == TAN) {
                    return Math.tan(evaluateTree(v.getLeft())*(Math.PI/180));
                }
                else if (op == LOG) {
                    return Math.log10(evaluateTree(v.getLeft()));
                }
                else if (op == LON){
                    return Math.log1p(evaluateTree(v.getLeft()));
                }
                else if (op == PHI) {
                    return evaluateTree(v.getLeft())*(Math.PI);
                }
                else if (op == FAK) {
                    double i, fact = 1;
                    double number = evaluateTree(v.getLeft());
                    for(i=1;i<=number;i++){
                        fact = fact * i;
                    }
                    return fact;
                }
            }
            else {
                if (op == '+') {
                    return evaluateTree(v.getLeft()) + evaluateTree(v.getRight());
                }
                else if (op == '-') {
                    return evaluateTree(v.getLeft()) - evaluateTree(v.getRight());
                }
                else if (op == '*') {
                    return evaluateTree(v.getLeft()) * evaluateTree(v.getRight());
                }
                else if (op == '/') {
                    return evaluateTree(v.getLeft()) / evaluateTree(v.getRight());
                }
            }
        }
        return (double)v.element();
    }

    /**
     * Called at the beginning to replace user-friendly representation of square root ("sqrt") and square ("^2")
     * with only one character to represent the same operation in the evaluation process.
     *
     * @param str Initial string as entered by the user.
     * @return Valid string that works with the evaluator.
     */
    private String replace(String str){
        str = str.replaceAll("sqrt", "" + SQRT);
        str = str.replaceAll("cbrt", "" + CUBIC);
        str = str.replaceAll("sin", "" + SIN);
        str = str.replaceAll("cos", "" + COS);
        str = str.replaceAll("tan", "" + TAN);
        str = str.replaceAll("log", "" + LOG);
        str = str.replaceAll("lon", "" + LON);
        str = str.replaceAll(CUBICS + "", "^3");

        StringBuffer newStr = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '^') {
                newStr.append(SQUARE);
                i++;
            }
            else if (str.charAt(i) == 'p') {
                newStr.append(PHI);
                i++;
            }
            else if (str.charAt(i) == '!') {
                newStr.append(FAK);
                i++;
            }
            else {
                newStr.append(str.charAt(i));
            }
        }
        return newStr.toString();
    }
    
    /**
     * Removes the leading and ending brackets, for example:
     * If input is (((1 + (2 - 3)))), then the outputted expression will be 1 + (2 - 3).
     *
     * @param str Expression to validate.
     * @return Valid substring of the inputted expression.
     */
    private String getValidSubstring(String str) {
        while (str.charAt(0) == '(' && str.charAt(str.length() -1) == ')') {
            str = str.substring(1, str.length() - 1);
        }
        return str;
    }
}