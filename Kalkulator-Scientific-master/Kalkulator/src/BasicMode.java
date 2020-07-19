import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

// TODO: add checking validity of the expression when clicking equals
// TODO: fix entering with keyboard
// TODO: allow leading minus for negative numbers.

/**
 * This class displays a simple calculator.
 *
 * @author Luka Kralj
 * @version 30 March 2018
 */
public class BasicMode extends KeyAdapter {

    private static final int BUTTON_WIDTH = 50;
    private static final int BUTTON_HEIGHT = 30;

    private static final char[] validChars = {0,1,2,3,4,5,6,7,8,9,'(',')','+','-','*','/', '%', Evaluator.LON, Evaluator.SQUARE, Evaluator.SQRT, Evaluator.SIN, Evaluator.COS, Evaluator.TAN, Evaluator.PHI, Evaluator.FAK, Evaluator.CUBICS, Evaluator.CUBIC};

    private JTextArea displayArea; // Results shown here.
    private JTextField inputField; // Expressions entered here.
    // on entering '(' increase by 1, on entering ')' decrease by 1; check if 0 before adding.
    // for expression to be valid, counter must equal 0
    private int bracketCounter;
    //  if a decimal point has just been entered we cannot enter it again until we reach the next operator
    private boolean decimalPointEntered;
    // used for undo; stores previous entries
    private Stack<String> previousExpressions;

    /**
     * Construct the basic calculator.
     */
    public BasicMode() {
        decimalPointEntered = false;
        bracketCounter = 0;
        previousExpressions = new Stack<>();

        JFrame frame = new JFrame("KALKULATOR SCIENTIFIC");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        JPanel all = new JPanel();
        all.setLayout(new BoxLayout(all, BoxLayout.PAGE_AXIS));
        
        all.setBorder(new EmptyBorder(7, 7, 7, 7));

        JPanel labels = new JPanel(new BorderLayout());
        labels.setBorder(new LineBorder(Color.BLACK, 3)); 

        displayArea = new JTextArea();
        displayArea.setOpaque(true);
        displayArea.setBackground(Color.WHITE); //kolom hasil
        displayArea.setLineWrap(false);
        displayArea.setEditable(false);

        JScrollPane display = new JScrollPane(displayArea);
        display.setPreferredSize(new Dimension(frame.getWidth(), 100));
        display.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        display.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        labels.add(display, BorderLayout.CENTER);

        inputField = new JTextField("");
        inputField.setPreferredSize(new Dimension(frame.getWidth(), 20));
        inputField.setBackground(Color.WHITE);
        inputField.setEditable(false);
        inputField.setHorizontalAlignment(SwingConstants.RIGHT);
        labels.add(inputField, BorderLayout.SOUTH);

        all.add(labels);
        all.add(Box.createVerticalStrut(10));

        JPanel buttons = createButtons();
        all.add(buttons);

        contentPane.add(all);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.addKeyListener(this);
        frame.setFocusableWindowState(true);
        frame.setAutoRequestFocus(true);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates all the buttons that are needed for the input.
     *
     * @return Panel with all the buttons to be added to the calculator.
     */
    private JPanel createButtons() {

        JPanel allFlow = new JPanel(new FlowLayout());

        JPanel allAll = new JPanel();
        allAll.setLayout(new BoxLayout(allAll, BoxLayout.LINE_AXIS));

        JPanel all = new JPanel();
        all.setLayout(new BoxLayout(all, BoxLayout.PAGE_AXIS));


        JPanel topButtons = new JPanel();
        topButtons.setLayout(new GridLayout(8, 7, 4,4));
        
        //TOP ROW:
        JButton fak = new JButton("!");
        fak.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        fak.setBackground(new java.awt.Color(253, 245, 230));
        fak.addActionListener(e -> fakClicked());
        topButtons.add(fak);
        
        JButton phi = new JButton("<html><span>&#928</span></html>");
        phi.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        phi.setBackground(new java.awt.Color(253, 245, 230));
        phi.addActionListener(e -> phiClicked());
        topButtons.add(phi);
        
        JButton undo = new JButton();
        undo.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        undo.setBackground(Color.WHITE);
        ImageIcon undoIcon = new ImageIcon(getClass().getResource("undo_arrow.jpg"));
        undo.setIcon(undoIcon);
        undo.addActionListener(e -> undoClicked());
        topButtons.add(undo);
        
        JButton delete = new JButton();
        delete.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        delete.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        delete.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        delete.setBackground(Color.WHITE);
        ImageIcon deleteIcon = new ImageIcon(getClass().getResource("delete.png"));
        delete.setIcon(deleteIcon);
        delete.addActionListener(e -> deleteClicked());
        topButtons.add(delete);
        
        JButton openingBracket = new JButton("(");
        openingBracket.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        openingBracket.setBackground(new java.awt.Color(238, 173, 14));
        openingBracket.addActionListener(e -> openingBracketClicked());
        topButtons.add(openingBracket);

        JButton closingBracket = new JButton(")");
        closingBracket.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        closingBracket.setBackground(new java.awt.Color(238, 173, 14));
        closingBracket.addActionListener(e -> closingBracketClicked());
        topButtons.add(closingBracket);
        
        JButton log = new JButton("log");
        log.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        log.setBackground(new java.awt.Color(238, 173, 14));
        log.addActionListener(e -> logClicked());
        topButtons.add(log);
        
        JButton percent = new JButton("%");
        percent.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        percent.setBackground(new java.awt.Color(238, 173, 14));
        percent.addActionListener(e -> percentClicked());
        topButtons.add(percent);
        
        JButton divide = new JButton("/");
        divide.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        divide.setBackground(new java.awt.Color(238, 173, 14));
        divide.addActionListener(e -> operatorClicked("/"));
        topButtons.add(divide);
        
        JButton multiply = new JButton("*");
        multiply.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        multiply.setBackground(new java.awt.Color(238, 173, 14));
        multiply.addActionListener(e -> operatorClicked("*"));
        topButtons.add(multiply);
        
        JButton minus = new JButton("-");
        minus.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        minus.setBackground(new java.awt.Color(238, 173, 14));
        minus.addActionListener(e -> operatorClicked("-"));
        topButtons.add(minus);

        JButton plus = new JButton("+");
        plus.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        plus.setBackground(new java.awt.Color(238, 173, 14));
        plus.addActionListener(e -> operatorClicked("+"));
        topButtons.add(plus);

        JButton square = new JButton("<html>x<sup>2</sup></html>");
        square.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        square.setBackground(new java.awt.Color(238, 197, 145));
        square.addActionListener(e -> squareClicked());
        topButtons.add(square);
        
        JButton cubics = new JButton("<html>x<sup>3</sup></html>"); //pangkat 3
        cubics.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        cubics.setBackground(new java.awt.Color(238, 197, 145));
        cubics.addActionListener(e -> cubicsClicked());
        topButtons.add(cubics);
        
        JButton sqrt = new JButton("<html><span>&#8730;</span></html>");
        sqrt.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        sqrt.setBackground(new java.awt.Color(238, 197, 145));
        sqrt.addActionListener(e -> sqrtClicked());
        topButtons.add(sqrt);
        
        JButton cubic = new JButton("<html><span><sup>3</sup>&#8730;</span></html>"); //akar pangkat 3
        cubic.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        cubic.setBackground(new java.awt.Color(238, 197, 145));
        cubic.addActionListener(e -> cubicClicked());
        topButtons.add(cubic);
        
        //FIRST:
        JButton seven = new JButton("7");
        seven.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        seven.setBackground(new java.awt.Color(205, 197, 191));
        seven.addActionListener(e -> updateInputField("7"));
        topButtons.add(seven);

        JButton eight = new JButton("8");
        eight.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        eight.setBackground(new java.awt.Color(205, 197, 191));
        eight.addActionListener(e -> updateInputField("8"));
        topButtons.add(eight);

        JButton nine = new JButton("9");
        nine.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        nine.setBackground(new java.awt.Color(205, 197, 191));
        nine.addActionListener(e -> updateInputField("9"));
        topButtons.add(nine);
        
        JButton sin = new JButton("sin");
        sin.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        sin.setBackground(new java.awt.Color(238, 197, 145));
        sin.addActionListener(e -> sinClicked());
        topButtons.add(sin);
        
        // SECOND ROW:
        JButton four = new JButton("4");
        four.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        four.setBackground(new java.awt.Color(205, 197, 191));
        four.addActionListener(e -> updateInputField("4"));
        topButtons.add(four);

        JButton five = new JButton("5");
        five.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        five.setBackground(new java.awt.Color(205, 197, 191));
        five.addActionListener(e -> updateInputField("5"));
        topButtons.add(five);

        JButton six = new JButton("6");
        six.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        six.setBackground(new java.awt.Color(205, 197, 191));
        six.addActionListener(e -> updateInputField("6"));
        topButtons.add(six);
        
        JButton cos = new JButton("cos");
        cos.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        cos.setBackground(new java.awt.Color(238, 197, 145));
        cos.addActionListener(e -> cosClicked());
        topButtons.add(cos);
        
        // THIRD ROW:
        JButton one = new JButton("1");
        one.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        one.setBackground(new java.awt.Color(205, 197, 191));
        one.addActionListener(e -> updateInputField("1"));
        topButtons.add(one);

        JButton two = new JButton("2");
        two.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        two.setBackground(new java.awt.Color(205, 197, 191));
        two.addActionListener(e -> updateInputField("2"));
        topButtons.add(two);

        JButton three = new JButton("3");
        three.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        three.setBackground(new java.awt.Color(205, 197, 191));
        three.addActionListener(e -> updateInputField("3"));
        topButtons.add(three);
        
        JButton tan = new JButton("tan");
        tan.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        tan.setBackground(new java.awt.Color(238, 197, 145));
        tan.addActionListener(e -> tanClicked());
        topButtons.add(tan);

        JButton decimalPoint = new JButton(".");
        decimalPoint.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        decimalPoint.setBackground(new java.awt.Color(238, 173, 14));
        decimalPoint.addActionListener(e -> decimalPointClicked());
        topButtons.add(decimalPoint);
        
        JButton zero = new JButton("0");
        zero.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        zero.setBackground(new java.awt.Color(205, 197, 191));
        zero.addActionListener(e -> updateInputField("0"));
        topButtons.add(zero);
     
        JButton equals = new JButton("=");
        equals.setPreferredSize(new Dimension(BUTTON_WIDTH*2 + 6, BUTTON_HEIGHT));
        equals.setBackground(new java.awt.Color(0, 250, 154));
        equals.addActionListener(e -> equalsClicked());
        topButtons.add(equals);
        
        JButton ln = new JButton("ln");
        ln.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        ln.setBackground(new java.awt.Color(238, 197, 145));
        ln.addActionListener(e -> lnClicked()); 
        topButtons.add(ln);
        
        all.add(topButtons);

        allAll.add(all);
        allFlow.add(allAll);
        return allFlow;
        
    }

    /**
     * Clears the expression currently displayed.
     */
    private void clearInputField(){
        inputField.setText("");
        enableDecimalPoint();
    }

    /**
     * Adds a new character (number,...) to the expression.
     *
     * @param newCharacter To be added at the end of expression.
     */
    private void updateInputField(String newCharacter) {
        inputField.setText(inputField.getText() + newCharacter);
    }
    
    private void sinClicked() {
    	 if (!canEnterSymbol()) {
            inputField.setText(inputField.getText() + "sin(");
            bracketCounter++;
        }
    	 else {
    		 showWarning();
    	 }
	}
    
    private void lnClicked(){
        if(!canEnterSymbol()){
            inputField.setText(inputField.getText() + "lon(");
            bracketCounter++;
        }
    	 else {
    		 showWarning();
    	 }
	}
    
    private void cosClicked() {
    	 if (!canEnterSymbol()) {
            inputField.setText(inputField.getText() + "cos(");
            bracketCounter++;
        }
    	 else {
    		 showWarning();
    	 }
	}
    
    private void tanClicked() {
    	 if (!canEnterSymbol()) {
            inputField.setText(inputField.getText() + "tan(");
            bracketCounter++;
        }
    	 else {
    		 showWarning();
    	 }
	}
    
    private void logClicked() {
    	 if (!canEnterSymbol()) {
            inputField.setText(inputField.getText() + "log(");
            bracketCounter++;
        }
    	 else {
    		 showWarning();
    	 }
	}

    /**
     * Updates the display are to include the expression and its result.
     *
     * @param expression Expression to be added.
     * @param result Result of the expression.
     */
    private void updateDisplayField(String expression, String result) {
        displayArea.append(expression + "\n= " + result + "\n\n");
    }

    /**
     * Undo button was clicked. Set the expression to the last entered expression, if any.
     */
    private void undoClicked() {
        if (previousExpressions.isEmpty()) {
            clearInputField();
        }
        else {
            inputField.setText(previousExpressions.pop());
        }
    }

    /**
     * Delete the last character of the current expression.
     * TODO: add more checks what to delete.
     */
    private void deleteClicked() {
        // Delete the last character.
        String str = inputField.getText();
        if (!str.equals("")) {
            inputField.setText(str.substring(0, str.length() - 1));
        }
    }

    /**
     * Adds a square to the expression.
     */
    private void squareClicked() {
        if (canEnterSymbol()) {
            inputField.setText(inputField.getText() + "^2");
        }
        else {
            showWarning();
        }
    }
    
     private void phiClicked() {
        if (canEnterSymbol()) {
            inputField.setText(inputField.getText() + "p");
        }
        else {
            showWarning();
        }
    }
     
     private void fakClicked() {
        if (canEnterSymbol()) {
            inputField.setText(inputField.getText() + "!");
        }
        else {
            showWarning();
        }
    }
    
    private void cubicsClicked() {
        if (canEnterSymbol()) {
            inputField.setText(inputField.getText() + "^3");
        }
        else {
            showWarning();
        }
    }

    /**
     * Adds the symbol for square root as "sqrt(" to the expression.
     */
    private void sqrtClicked() {
        if (!canEnterSymbol()) {
            inputField.setText(inputField.getText() + "sqrt(");
            bracketCounter++;
        }
        else {
            showWarning();
        }
    }
    
    private void  cubicClicked() {
        if (!canEnterSymbol()) {
            inputField.setText(inputField.getText() + "cbrt(");
            bracketCounter++;
        }
        else {
            showWarning();
        }
    }

    /**
     * Decimal point was clicked. It is added to the expression if and only if the expression would still be valid.
     */
    private void decimalPointClicked() {
        char last = ' '; // placeholder
        if (!inputField.getText().equals("")) {
            last = inputField.getText().charAt(inputField.getText().length()-1);
        }
        if (!decimalPointEntered && !(last == ' ' || last == '+' || last == '-' || last == '/' || last == '*' || last == '(' || last == ')' || last == '%')) {
            decimalPointEntered = true;
            updateInputField(".");
        }
        else {
            showWarning();
        }
    }

    /**
     * Allows a decimal point to be entered.
     */
    private void enableDecimalPoint() {
        decimalPointEntered = false;
    }

    /**
     * Adds percentage sign to the expression. This sign takes the expression in front of it and
     * divides it by 100 when evaluated, for example: 25.4% = 0.254 and 25.4%% = 0.00254
     */
    private void percentClicked() {
        if (canEnterSymbol()) {
            updateInputField("%");
            enableDecimalPoint();
        }
        else {
            showWarning();
        }
    }

    private void equalsClicked() {
        String str = inputField.getText();
        if (str.equals("")) {
            return;
        }
        previousExpressions.push(str);
        Evaluator evaluator = new Evaluator(str);
        String result = evaluator.getResult();
        if (result.startsWith("-")) {
            result = "(0" + result + ")";
        }
        updateDisplayField(str, result);
        clearInputField();
        updateInputField(result);

    }

    /**
     * Add operator to the expression.
     *
     * @param newCharacter Operator to add.
     */
    private void operatorClicked(String newCharacter) {
        if(canEnterSymbol()) {
            updateInputField(newCharacter);
            enableDecimalPoint();
        }
        /*else if (newCharacter.equals("-") && (inputField.getText().equals("") || inputField.getText().charAt(inputField.getText().length()-1) == '(')) {
            updateInputField("-");
            enableDecimalPoint();
        }*/
        else {
            showWarning();
        }
    }

    /**
     * Add opening bracket to the expression.
     */
    private void openingBracketClicked() {
        String expression = inputField.getText();
        if (expression.equals("")) {
            updateInputField("(");
            bracketCounter++;
            enableDecimalPoint();
            return;
        }

        char last = expression.charAt(expression.length()-1);
        if (last == '+' || last == '-' || last == '/' || last == '*' || last == '(') {
            updateInputField("(");
            bracketCounter++;
            enableDecimalPoint();
        }
        else {
            showWarning();
        }
    }

    /**
     * Adds closing bracket to the expression.
     */
    private void closingBracketClicked() {
        if (bracketCounter > 0 && canEnterSymbol()) {
            updateInputField(")");
            bracketCounter--;
            enableDecimalPoint();
        }
        else {
            showWarning();
        }
    }

    /**
     * Checks whether the symbol, such as operators and brackets can be added.
     * Needed to maintain validity of the expression.
     *
     * @return True if the symbol can be added.
     */
    private boolean canEnterSymbol() {
        String expression = inputField.getText();
        if (expression.equals("")) {
            return false;
        }

        char last = expression.charAt(expression.length()-1);
        if (last == '+' || last == '-' || last == '/' || last == '*' || last == '(') {
            return false;
        }
        return true;
    }

    /**
     * Whenever a user attempts to enter an invalid combination of symbols this warning is shown.
     */
    private void showWarning() {
        JOptionPane.showMessageDialog(null, "Invalid operation.", "Invalid operation.", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * This method is intended to accept inputs entered with the keyboard.
     * There is still quite a lot of problems with it. It seems that the key codes are not matching with the constants.
     * Also the focus is lost once the user clicks one of the buttons.
     *
     * TODO: debug
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == '+' ||
                c == '-' ||
                c == '*' ||
                c == '/') {
            operatorClicked("" + c);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            clearInputField();
        }
        else if (e.getKeyCode() == KeyEvent.VK_DECIMAL || c == '.') {
            decimalPointClicked();
        }
        else if (c == KeyEvent.VK_UNDO) {
            undoClicked();
        }
        else if (c == KeyEvent.VK_V) {
            sqrtClicked();
        }
        else if (e.getKeyChar() == Evaluator.SQUARE) {
            squareClicked();
        }
        else if (c == KeyEvent.VK_LEFT_PARENTHESIS) {
            openingBracketClicked();
        }
        else if (c == KeyEvent.VK_RIGHT_PARENTHESIS) {
            closingBracketClicked();
        }
        else if (c == KeyEvent.VK_ENTER) {
            equalsClicked();
        }
        else if (e.getKeyChar() == '%') {
            percentClicked();
        }
        else if (c == KeyEvent.VK_0 ||
                c == KeyEvent.VK_1 ||
                c == KeyEvent.VK_2 ||
                c == KeyEvent.VK_3 ||
                c == KeyEvent.VK_4 ||
                c == KeyEvent.VK_5 ||
                c == KeyEvent.VK_6 ||
                c == KeyEvent.VK_7 ||
                c == KeyEvent.VK_8 ||
                c == KeyEvent.VK_9 ||
                c == KeyEvent.VK_NUMPAD0 ||
                c == KeyEvent.VK_NUMPAD1 ||
                c == KeyEvent.VK_NUMPAD2 ||
                c == KeyEvent.VK_NUMPAD3 ||
                c == KeyEvent.VK_NUMPAD4 ||
                c == KeyEvent.VK_NUMPAD5 ||
                c == KeyEvent.VK_NUMPAD6 ||
                c == KeyEvent.VK_NUMPAD7 ||
                c == KeyEvent.VK_NUMPAD8 ||
                c == KeyEvent.VK_NUMPAD9) {
            updateInputField("" + e.getKeyChar());
        }
        else {
            // Do nothing.
        }

    }

}
