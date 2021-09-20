/************************************************************
 Software Engineering
 Fahad Dawood, Ethan Hannen.
*************************************************************/

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

enum ButtonType
{
    NEW,
    MARK,
    MIN,
    EXIT
}

@SuppressWarnings("serial")
public class Button extends JButton implements MouseListener
{
    private Cursor defaultCursor = this.getCursor();;
    private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    private ButtonType type;

    public Button(String text, ButtonType t)
    {
        this.setBackground(t == ButtonType.NEW ? Colors.BTN_BACKGROUND : Colors.BACKGROUND);
        this.setForeground(t == ButtonType.NEW ? Colors.BACKGROUND : Colors.FOREGROUND);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setFocusable(false);
        this.setOpaque(true);
        this.setText(text);
        this.setFont(Fonts.ARIAL_SMALL);
        this.type = t;
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    	if (this.type == ButtonType.NEW)
            Game.newGame();
    	else if (this.type == ButtonType.EXIT)
            View.closeWindow();
    	else if (this.type == ButtonType.MIN)
            View.minimizeWindow();
    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        this.setCursor(handCursor);
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        this.setCursor(defaultCursor);
    }
}
