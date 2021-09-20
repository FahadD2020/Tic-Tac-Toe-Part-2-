/************************************************************
 Software Engineering
 Fahad Dawood, Ethan Hannen.
*************************************************************/


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class View
{
    public static JFrame window;
    static JLabel errorMessage;
    static JCheckBox enableAI;
    JLabel timeRemaining;
    JLabel playerMessage;

    private JLabel newLabel(String text, int fontSize)
    {
        JLabel label = new JLabel();
        label.setBackground(Colors.BACKGROUND);
        label.setForeground(Colors.FOREGROUND);
        label.setFont(new Font("Arial", Font.PLAIN, fontSize));
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setText(text);
        label.setOpaque(true);
        return label;
    }

    View(Grid grid, Config options)
    {
        double ratio = Math.min(options.n, options.m) * 1. / Math.max(options.n,  options.m);
        int width = (int)(600 * (options.n > options.m ? 1 : ratio));
        width = Math.max(width,  400);
        int height = (int)(600 * (options.m > options.n ? 1 : ratio));
        
        /**************************
        Window Control Panel (P1)
        **************************/
        
        JPanel P1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        P1.setPreferredSize(new Dimension(width, 30));
        P1.setBackground(Colors.BACKGROUND);
        JButton min = new Button("\u2014 ", ButtonType.MIN);
        JButton exit = new Button(" X", ButtonType.EXIT);
        P1.add(min);
        P1.add(exit);

        /**************************
         Title Panel (P2)
        **************************/

        JPanel P2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        P2.setPreferredSize(new Dimension(width, 60));
        P2.setBackground(Color.black);
        JLabel title = newLabel(" Tic Tac Toe", 40);
        title.setFont(Fonts.INK_FREE_LARGE);
        P2.add(title);
        
        /**************************
         Error Panel (P3)
        **************************/
        
        JPanel P3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        P3.setPreferredSize(new Dimension(width, 25));
        P3.setBackground(Color.black);
        errorMessage = newLabel("", 20);
        errorMessage.setFont(Fonts.ARIAL_SMALL);
        errorMessage.setForeground(Color.red);
        P3.add(errorMessage);

        /**************************
         User Controls (P4)
        **************************/
        
        JPanel P4 = new JPanel(new GridBagLayout());
        P4.setPreferredSize(new Dimension(width, 40));
        P4.setBackground(Color.black);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 20;
        c.weightx = 0.7;
        c.gridwidth = 2;

        JButton newGame = new Button("New Game", ButtonType.NEW);
        P4.add(newGame, c);
        
        
        
        // User-defined grid settings
        JPanel control = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        control.setPreferredSize(new Dimension(width, 45));
        control.setBackground(Colors.BACKGROUND);
        
        JLabel M = newLabel(" M ", 20);
        JLabel N = newLabel(" N ", 20);
        JLabel K = newLabel(" K ", 20);
        JTextField m = new Input(String.valueOf(options.m), 0);
        JTextField n = new Input(String.valueOf(options.n), 1);
        JTextField k = new Input(String.valueOf(options.k), 2);

        // Add text fields
        control.add(M);
        control.add(m);
        control.add(N);
        control.add(n);
        control.add(K);
        control.add(k);
        
        // Add AI Field
        JLabel ai = newLabel(" Enable AI ", 20);
        control.add(ai);
        JCheckBox cb = new JCheckBox("", Game.isVersusAI);
        cb.setBackground(Color.BLACK);
        cb.addItemListener(new ItemListener() {    
            public void itemStateChanged(ItemEvent e) {                 
               Game.toggleAI();  
            }
         });
        
        control.add(cb);

        c.gridx = 4;
        c.gridy = 0;
        c.weightx = 1.5;
        c.gridwidth = 5;
        c.fill = GridBagConstraints.HORIZONTAL;
        P4.add(control, c);

        /**************************
         Game Grid Panel (P5)
        **************************/

        JPanel P5 = new JPanel();
        P5.setPreferredSize(new Dimension(width, height));
        P5.setLayout(new GridLayout(options.m, options.n));

        for(int i = 0; i < options.m; i++)
        {
            for (int j = 0; j < options.n; j ++)
            {
                Box b = new Box();              
                grid.addBox(i, j, b);
                b.row = i;
                b.col = j;
                P5.add(b);
            }
        }
        
        /**************************
         Information Panel (P6)
         **************************/

        JPanel P6 = new JPanel();
        P6.setPreferredSize(new Dimension(width, 30));
        P6.setLayout(new GridLayout());
        timeRemaining = newLabel(" Time Remaining: 10", 20);
        playerMessage = newLabel((Game.isVersusAI ? " Player's" : " Player 1's") + " Turn", 20);
        P6.add(timeRemaining);
        P6.add(playerMessage);

        /**************************
         Window Frame
         **************************/

        window = new JFrame("Tic Tac Toe");     
        window.setSize(width + 40,height + 225); // 110 for control elements
        window.add(P1);
        window.add(P2);
        window.add(P3);
        window.add(P4);
        window.add(P5);
        window.add(P6);
        window.setLocationRelativeTo(null); // Center
        window.setLayout(new FlowLayout(FlowLayout.CENTER));
        window.getContentPane().setBackground(Color.BLACK);
        window.setUndecorated(true);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void updateTime(String t)
    {
        timeRemaining.setText(t);
    }
    
    public void updateMessage(String t)
    {
        playerMessage.setText(t);
    }
    
    public void setTimeColor(Color c)
    {
        timeRemaining.setForeground(c);
    }
    
    public void setMessageColor(Color c)
    {
        playerMessage.setForeground(c);
    }
    
    public static void showError(String e)
    {
        errorMessage.setText(e);
    }
    
    public static void resetError()
    {
        errorMessage.setText("");
    }
    
    public void showWinner(Box b)
    {
        if (b == null)
            return;
        b.changeBack = false;
        b.setBackground(Color.green);
        b.setForeground(Color.black);
    }
    
    public static void minimizeWindow()
    {
        window.setState(Frame.ICONIFIED);
    }
    
    public static void closeWindow()
    {
        System.exit(0);
    }
    
    public void destroy()
    {
        window.setVisible(false); 
        window.dispose(); 
    }
}
