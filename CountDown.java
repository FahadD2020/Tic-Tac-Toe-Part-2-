/************************************************************
 Software Engineering
 Fahad Dawood, Ethan Hannen.
*************************************************************/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class CountDown
{
    private Timer timer;
    private short timeLeft = 10;
    private ActionListener AL;

    CountDown()
    {
        AL = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                --timeLeft;
                if (timeLeft <= 0)
                    timer.stop();
                Game.updateTimer(timeLeft);
            }
        };
    }

    public void start()
    {
        timer = new Timer(1000, AL);
        timer.setInitialDelay(1000);
        timer.start();
    }
    
    public void stop()
    {
        timer.stop();
    }
    
    public void reset()
    {
        timeLeft = 10;
        Game.updateTimer(timeLeft);
        if (timer == null)
            this.start();
        else
            timer.restart();
    }
}