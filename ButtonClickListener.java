import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClickListener implements ActionListener {
    private JTextField display;

    public ButtonClickListener(JTextField display) {
        this.display = display;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.charAt(0) == 'C') {
            display.setText("");
        } else if (command.charAt(0) == '=') {
            evaluateExpression();
        } else {
            display.setText(display.getText() + command);
        }
    }

    private void evaluateExpression() {
        try {
            double result = new ExpressionEvaluator().evaluate(display.getText());
            display.setText(String.valueOf(result));
        } catch (Exception ex) {
            display.setText("Error");
        }
    }
}
