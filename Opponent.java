/************************************************************
 Software Engineering
 Fahad Dawood, Ethan Hannen.
*************************************************************/

import javax.swing.SwingWorker;

public class Opponent
{
    static Box b;
    static boolean turn;
    static SwingWorker<Boolean, Void> worker;

    private static int getRandomNumber(int max)
    {
        return (int) ((Math.random() * (max - 1)));
    }

    public static void play(Grid grid, Config options)
    {
        b = null;
        turn = true;
        worker = 
            new SwingWorker<Boolean, Void>()
            {
                @Override
                protected Boolean doInBackground() throws Exception
                {
                    try {
                        // Emulate the AI 'thinking'
                        Thread.sleep(getRandomNumber(3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int i = 0, j = 0, attempt = 0;
    
                    // Restrict random to 20 tries
                    while (attempt++ < 20 && turn)
                    {
                        i = getRandomNumber(options.m);
                        j = getRandomNumber(options.n);
                        Box box = grid.getBox(i, j);
                        if (box.getText() == "")
                        {
                            b = box;
                            return true;
                        }
                    }
                    
                    // Too many randoms, iterate through
                    for (i = 0; i < options.m; i++)
                    {
                        for (j = 0; j < options.n; j++)
                        {
                            if (!turn)
                                break;
                            Box box = grid.getBox(i, j);
                            if (box.getText() == "")
                            {
                                b = box;
                                return true;
                            }
                        }
                    }
                    return true;
                }
                
                // Once the worker thread is done, update the game board
                protected void done()
                {
                    if (turn)
                        Game.markBlock(b);
                    turn = false;
                }
            };
        worker.execute(); // Execute doInBackground()
    }
}
