/************************************************************
 Software Engineering
 Fahad Dawood, Ethan Hannen.
*************************************************************/

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Input extends JTextField
{
    static boolean[] errors = new boolean[3];
    public static Input[] inputs = new Input[3];
    int index;

    public Input(String t, int index)
    {
        super(2);
        super.setText(t);
        this.index = index;
        this.setFont(this.getFont().deriveFont(15.f));
        this.setHorizontalAlignment(JTextField.CENTER);
        inputs[index] = this;
 
        this.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent e)
            {
                JTextField f = (JTextField) e.getSource();
                String s = f.getText();

                try
                {
                    int i = Integer.parseInt(s);
 
                    if (i < 3 || i > 9)
                    {
                        errors[index] = true;
                        View.showError("Invalid Range: Must be 3 ~ 9");
                        if (i > 9)
                            f.setText(Character.toString(f.getText().charAt(0)));
                    }
                    else
                    {
                        errors[index] = false;
                        if (!errorsExist())
                            View.resetError();
                    }
                }
                catch(NumberFormatException ex)
                {
                    errors[index] = true;
                    View.showError("Invalid Input");
                }
            }
        });
    }

    public static boolean errorsExist()
    {
        return errors[0] || errors[1] || errors[2];
    }
}
