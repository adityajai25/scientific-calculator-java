import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ScientificCalculator extends JFrame {
    private JTextField display;
    private JPanel buttonPanel;

    public ScientificCalculator() {
        setTitle("Scientific Calculator");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 32));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setPreferredSize(new Dimension(500, 60));
        add(display, BorderLayout.NORTH);

        // Add key listener for keyboard input
        display.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ((c >= '0' && c <= '9') || c == '.' || c == '(' || c == ')' || c == '+' || c == '-' || c == '*' || c == '/' || c == '^') {
                    display.setText(display.getText() + c);
                } else if (c == '\n') { // Enter key for evaluation
                    evaluateExpression();
                } else if (c == '\b') { // Backspace key for clearing the display
                    display.setText("");
                }
            }
        });

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 5, 5, 5));
        add(buttonPanel, BorderLayout.CENTER);

        String[] buttons = {
                "7", "8", "9", "/", "sin",
                "4", "5", "6", "*", "cos",
                "1", "2", "3", "-", "tan",
                "0", ".", "=", "+", "log",
                "(", ")", "C", "sqrt", "exp"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(new ButtonClickListener(display));
            buttonPanel.add(button);
        }

        setVisible(true);
    }

    private void evaluateExpression() {
        try {
            double result = new ExpressionEvaluator().evaluate(display.getText());
            display.setText(String.valueOf(result));
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ScientificCalculator();
            }
        });
    }
}