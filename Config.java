/************************************************************
 Software Engineering
 Fahad Dawood, Ethan Hannen.
*************************************************************/



import java.awt.Color;
import java.awt.Font;

class Colors
{
    static Color BORDER = Color.LIGHT_GRAY;
    static Color BACKGROUND = Color.black;
    static Color FOREGROUND = Color.white;
    static Color BTN_BACKGROUND = Color.green;
};

class Fonts
{
    static Font ARIAL_SMALL    = new Font("Arial", Font.PLAIN, 15);
    static Font ARIAL_LARGE    = new Font("Arial", Font.PLAIN, 20);
    static Font INK_FREE_SMALL = new Font("Ink Free", Font.ITALIC,30);
    static Font INK_FREE_LARGE = new Font("Ink Free", Font.BOLD,40);
};

public class Config 
{
    public int m; // Rows
    public int n; // Columns
    public int k; // Win length
    public int s; // Board size
    
    public Config(int m, int n, int k) 
    {
        this.m = m;
        this.n = n;
        this.k = k;
        this.s = m * n;
    }
    
    public void updateValues()
    {
        if (!Input.errorsExist())
        {
            m = Integer.parseInt(Input.inputs[0].getText());
            n = Integer.parseInt(Input.inputs[1].getText());
            k = Integer.parseInt(Input.inputs[2].getText());
            s = m * n;
        }
    }
}
