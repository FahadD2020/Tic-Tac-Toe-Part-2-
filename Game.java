/************************************************************
 Software Engineering
 Fahad Dawood, Ethan Hannen.
*************************************************************/
import java.awt.Color;

class Grid
{
    private Box[][] boxes;  
    public int markCount;
    
    public Grid(Config options)
    {
        boxes = new Box[options.m][options.n];
        markCount = 0;
    }
    
    public void addBox(int r, int c, Box b)
    {
        boxes[r][c] = b;
    }
    
    public void markBox(int r, int c, String t)
    {
        boxes[r][c].setText(t);
    }
    
    public Box getBox(int r, int c)
    {
        return boxes[r][c];
    }
    
    public int getMarkCount()
    {
        return markCount;
    }
    
    public int addMarkCount()
    {
        return ++markCount;
    }
}

public class Game
{   
    static CountDown timer;
    static Config options;
    static Grid grid;
    static View view;

    static boolean playerTurn = true;
    static boolean gameOver = false;
    static boolean isVersusAI = false;
    public static String xPattern;
    public static String oPattern;

    public Game()
    {
        timer = new CountDown();
        options = new Config(3, 3, 3);
        grid = new Grid(options);
        view = new View(grid, options);
        buildWinStrings();
    }

    public static void newGame()
    {
        // Update/Refresh variables
        Opponent.turn = false;
        playerTurn = true;
        gameOver = false;
        options.updateValues();
        buildWinStrings();
        
        // Rebuild view
        view.destroy(); 
        grid = new Grid(options);
        view = new View(grid, options);
    }
    
    public static void buildWinStrings()
    {
    	xPattern = "";
    	oPattern = "";
        for (int i = 0; i < options.k; i++)
        {
        	xPattern += "X";
        	oPattern += "O";
        }
    }
    
    public static void resetGame()
    {
        view.updateMessage(" Player\'s Turn");
        timer.reset();
    }
    
    public static void setPlayerTurn(boolean turn)
    {
        playerTurn = turn;
    }
    
    public static void toggleAI()
    {
    	isVersusAI = !isVersusAI;
    	newGame();
    }
    
    public static void updateTimer(short timeLeft)
    {
        if (timeLeft != 0 && !gameOver)
        {
            if (timeLeft <= 3)
                view.setTimeColor(Color.RED);
            view.updateTime(" Time Remaining: " + timeLeft);
        }
        else
        {
            gameOver = true;
            view.updateTime(" Time Remaining: " + timeLeft);
            if (isVersusAI)
            	view.updateMessage(" Time\'s up! " + (playerTurn ? "Computer" : "Player") + " wins!");
            else
            	view.updateMessage(" Time\'s up! Player " + (playerTurn ? "2" : "1") + " wins!");
        }
    }
    
    public static void markBlock(Box b)
    {
        if (gameOver || b.getText() != "") return;

        b.setText(playerTurn ? "X": "O");
        gameOver = checkWin(grid, b);

        // Try to clean this up?
        if (gameOver)
        {
            timer.stop();
            if (isVersusAI)
            	view.updateMessage((playerTurn ? "Player" : "Computer") + " wins!");
            else
            	view.updateMessage("Player " + (playerTurn ? "1" : "2") + " wins!");
        }
        else if (grid.addMarkCount() == options.s)
        {
            timer.stop();
            view.updateMessage(" Draw!");
        }
        else
        {
            timer.reset();
            setPlayerTurn(!playerTurn);
            
            if (isVersusAI)
            {
            	view.updateMessage((playerTurn ? "Player" : "Computer") + "\'s Turn");
            	Opponent.play(grid, options);
            }            	
            else
            {
            	view.updateMessage("Player " + (playerTurn ? "1" : "2") + "\'s Turn");
            }
        }
        view.setTimeColor(Color.WHITE);
        b.highlight();
    }
    
    public static boolean hasPattern(String s)
    {
    	return s.contains(xPattern) || s.contains(oPattern);
    }
    
    public static boolean checkWin(Grid g, Box box)
    {
        String sRow = "";
        String sCol = "";
        String sDag = "";
        String temp = "";
        
        Box[] dag = new Box[Math.max(options.m,  options.n)];
        int iDag = 0;
        
        // Check rows and columns
        for (int i = 0; i < options.m; i++)
        {
            temp = g.getBox(i, box.col).getText();
            sRow += temp == "" ? "_" : temp;
        }
        for (int i = 0; i < options.n; i++)
        {
            temp = g.getBox(box.row, i).getText();
            sCol += temp == "" ? "_" : temp;
        }

        if (hasPattern(sRow))
        {
            for (int i = 0; i < options.m; i++)
                view.showWinner(g.getBox(i, box.col));
            return true;
        }
        if (hasPattern(sCol))
        {
            for (int i = 0; i < options.n; i++)
                view.showWinner(g.getBox(box.row, i));
            return true;
        }
        
        // Check Diagonal
        
        int i = box.row;
        int j = box.col;
        
        while (i > 0 && j > 0)
        {
            --i;
            --j;
        }
        
        while (i < options.m && j < options.n)
        {
            dag[iDag] = g.getBox(i++, j++);
            temp = dag[iDag++].getText();
            sDag += temp == "" ? "_" : temp;
        }
        
        if (hasPattern(sDag))
        {
            for (Box b : dag)
                view.showWinner(b);
            return true;
        }

        // Check Anti-diagonal

        sDag = "";
        iDag = 0;
        i = box.row;
        j = box.col;

        while (i > 0 && j < options.n - 1)
        {
            --i;
            ++j;
        }

        while (i < options.m && j >= 0)
        {
            dag[iDag] = g.getBox(i, j);
            temp = dag[iDag++].getText();
            sDag += temp == "" ? "_" : temp;
            ++i;
            --j;
        }
        
        if (hasPattern(sDag))
        {
            for (Box b : dag)
                view.showWinner(b);
            return true;
        }

        return false;
    }
}