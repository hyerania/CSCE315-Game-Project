package Client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import static java.lang.System.exit;
import static java.lang.System.out;

/*Client implementation of the AI in order to provide the functionality on our algorithm of making the moves in the game*/
public class AI
{
    /*Initiation of sending and receiving messages between client and server*/
    static BufferedReader input;
    static PrintWriter output;

    /*Initiation for the configuration properties*/
    String config;
    public int numHoles;
    public int numSeeds;
    public long timeLimit;
    public char isFirstPlayer;
    public char gameMode;
    public ArrayList<Integer> seedConfig = new ArrayList<Integer>();

    /*Initiation for the AI and player's turns and boards*/
    public static final int ROWS = 2;
    public static int COLUMNS;
    public static int [][]board;
    public static int otherPlayerEndHouse;
    public static int userEndHouse;
    public static boolean otherPlayersTurn = false;
    public static boolean goAgain = false;
    public static boolean firstMove;


    public AI(BufferedReader in, PrintWriter out, String configuration)
    {
        input = in;
        output = out;
        config = configuration;

        String configPass[] = config.split(" ");
        numHoles = Integer.parseInt(configPass[1]);
        numSeeds = Integer.parseInt(configPass[2]);
        timeLimit = Long.parseLong(configPass[3]);
        isFirstPlayer = configPass[4].charAt(0);
        gameMode = configPass[5].charAt(0);
        for(int x = 6; x < configPass.length; x++)
        {
            seedConfig.add(Integer.parseInt(configPass[x]));
        }


//populating the board
        COLUMNS = numHoles;
        board = new int[ROWS][COLUMNS];
        if(gameMode == 'S'){
            populateBoard(numSeeds, false);
        }
        else if(gameMode == 'R'){
            for(int c = 0; c < numHoles; c++)
            {
                board[0][c] = seedConfig.get(seedConfig.size()-1-c);
                board[1][c] = seedConfig.get(c);
            }
        }

        output.println("READY");

        if(isFirstPlayer == 'F')
        {
            otherPlayersTurn = false;  //AI is first player
            firstMove = false;
        }
        else
        {
            otherPlayersTurn = true;  //AI is second player
            firstMove = true;
        }

        String begin = "";
        try
        {
            begin = input.readLine();
        }
        catch(Exception e){}

        if(!begin.equals("BEGIN"))
        {
            return;
        }
        System.out.println(begin);

        while(true)
        {
            if(!otherPlayersTurn)
            {
                aiTurn();
            }
            //other players turn
            if(otherPlayersTurn)
            {
                otherPlayerTurn();
            }

        }



    }

    /*Provides the appropriate board distribution based on if the game is random or not and the number of seeds provided*/
    void populateBoard(int numSeeds, boolean random)
    {
        //Create this type of board if the configuration was not random
        if(!random)
        {
            for(int r = 0; r < ROWS; r++)
            {
                for(int c = 0; c < COLUMNS; c++)
                {
                    board[r][c] = numSeeds;
                }
            }
        }

        //If the board is random then it will create a number generator and distribute them appropriatly across the board
        else
        {
            Random ran = new Random();
            int sumOfAllSeeds = numSeeds * COLUMNS;

            for(int r = 0; r < ROWS; r++)
            {
                for(int c = 0; c < COLUMNS; c++)
                {
                    if(r == 0)
                    {
                        int seedVal =  ran.nextInt(sumOfAllSeeds);
                        board[r][c] = seedVal;
                        sumOfAllSeeds -= seedVal;
                    }
                    else
                    {
                        board[r][c] = board[r-1][COLUMNS-c-1];
                    }
                }
            }
        }
    }

    /*Provides the movement of seeds based on the rules of the game and move made by the user by placing them app
    * appropriately on the board*/
    void movingSeeds(int seeds, int r, int c)
    {
        for(int x = 0; x < seeds; x++)
        {
            if(c >= COLUMNS || c < 0)
            {
                //Makes sure seeds added to end zone of appropriate player
                if(r == 1)
                {
                    r--;
                    c = COLUMNS - 1;
                    if(!otherPlayersTurn)     //If the users turn, add to the end zone
                    {
                        userEndHouse++;
                        if(x+1 >= seeds) //If last seed, goAgain
                        {
                            goAgain = true;
                        }
                    }
                    continue;
                }
                else
                {
                    r++;
                    c = 0;
                    if(otherPlayersTurn)  //If other player's turn, then add the seeds to this end zone instead
                    {
                        otherPlayerEndHouse++;
                        if(x+1 >= seeds)
                        {
                            goAgain = true;
                        }

                    }

                    continue;
                }
            }

            //If last seed and it lands on an empty house, then all the seeds in the matching house should be yours
            if((x+1 >= seeds) && (board[r][c] == 0))
            {
                if(r == 1 && !(otherPlayersTurn))
                {
                    userEndHouse += board[r-1][c];
                    board[r-1][c] = 0;
                    userEndHouse++;
                }
                else if(r == 0 && otherPlayersTurn)
                {
                    otherPlayerEndHouse += board[r+1][c];
                    board[r+1][c] = 0;
                    otherPlayerEndHouse++;
                }

            }
            else
            {
                board[r][c]++;
            }


            if(r == 1)
            {
                c++;
            }
            else
            {
                c--;
            }
        }

        //If game is over, do not go again.
        if(isGameOver())
        {
            goAgain = false;
        }

    }

    /*Implementing the results for the otherPlayers by using pie rule or displaying the result */
    void otherPlayerTurn()
    {
        try
        {

            while(!input.ready()){} //Waiting for the client to be ready

            String response = input.readLine();
            String allMoves[] = response.split(" ");
            int moveNum = 0;
            if (response.equals("OK"))
            {
                out.println(response);
                otherPlayerTurn();
            }
            out.println(response);

            do{
                String otherPlayerResponse = allMoves[moveNum];

                //Implement the Pie Rule if the the player chooses so
                if(otherPlayerResponse.equals("P"))
                {
                    pieRule();
                    goAgain = false;
                    otherPlayersTurn = false;
                    break;
                }

                //Display appropriate message based on the response from the server and exit
                if(otherPlayerResponse.equals("WINNER") || otherPlayerResponse.equals("LOSER") || otherPlayerResponse.equals("TIE"))
                {
                    winnerByDefault(otherPlayerResponse);
                    break;
                }

                //Display appropriate message based no the response from the server
                if(otherPlayerResponse.equals("ILLEGAL") || otherPlayerResponse.equals("TIME"))
                {
                    winnerByDefault();
                    break;
                }

                int otherPlayerMove = Integer.parseInt(otherPlayerResponse);
                goAgain = false;
                otherPlayerMove--;

                int amount = board[0][otherPlayerMove];
                board[0][otherPlayerMove] = 0;
                movingSeeds(amount, 0, otherPlayerMove-1);
                moveNum++;
            }
            while(goAgain || input.ready()); //Moving Seeds will make the proper adjustment to goAgain
            otherPlayersTurn = false;

        } catch (Exception e){}
        output.println("OK"); //sends to the server to let it know that its turn is starting

    }

    /*Implementing the appropriate move for the AI*/
    void aiTurn()
    {
        String moves = "";
        do
        {
            goAgain = false;

            //Determining the move for the AI at a depth of 3
            algorithmAI go= new algorithmAI(0, otherPlayerEndHouse, userEndHouse);
            algorithmAI current = minmax(go, Integer.MIN_VALUE, Integer.MAX_VALUE, 3, true);

            if(firstMove)
            {
                //Determining if to play pie rule and switching players turns
                if(current.value <= otherPlayerEndHouse)
                {
                    pieRule();
                    output.println("P");
                    out.println("P");
                    otherPlayersTurn = true;
                    firstMove = false;
                    return;
                }
                firstMove = false;
            }

            int numSeeds = board[1][current.column];
            board[1][current.column] = 0;
            movingSeeds(numSeeds, 1, current.column+1);

            int c = current.column + 1;
            if(goAgain)
            {
                moves = moves + c + " ";
            }
            else
            {
                moves = moves + c;
            }

        }
        while(goAgain);
        output.println(moves);
        out.println(moves);
        otherPlayersTurn = true;
    }

    /*Determine if the game is over based on the seeds and returns boolean of true if it is over or else false*/
    boolean isGameOver()
    {
        int countComputer = 0;
        int countUser = 0;
        for(int x = 0; x < COLUMNS; x++)
        {
            countComputer += board[0][x];
            countUser += board[1][x];
        }
        return ((countComputer == 0) || (countUser == 0));
    }

    /*Display the winner of the game*/
    void winnerByDefault()
    {
        try
        {
            String response = input.readLine();
            out.println(response);
        }
        catch(Exception e){}
        exit(0);
    }

    /*Exit the game once the winner has been decided*/
    void winnerByDefault(String response)
    {
        exit(0);
    }

    /*Configures the appropriate board if the player chooses to do pie rule*/
    void pieRule()
    {
        //Switches the end house
        int temp = userEndHouse;
        userEndHouse = otherPlayerEndHouse;
        otherPlayerEndHouse = temp;

        //Receives the original board
        int[][] origBoard = new int[ROWS][COLUMNS];
        for(int r = 0; r < ROWS; r++)
        {
            for (int c = 0; c < COLUMNS; c++)
            {
                origBoard[r][c] = board[r][c];
            }
        }

        //Transposes the board for the proper display
        for(int r = 0; r < ROWS; r++)
        {
            for(int c = 0; c < COLUMNS; c++)
            {
                board[r][c] = origBoard[ROWS-1-r][COLUMNS-1-c];
            }
        }
    }

    /*Create a temporary board for the AI algorithm while moving and figuring out the possible results*/
    int psuedoMovingSeeds(int seeds, int r, int c)
    {
        int extra = 0;
        int[][] psuedoBoard = new int[ROWS][COLUMNS];
        for(int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLUMNS; j++)
            {
                psuedoBoard[i][j] = board[i][j];
            }
        }
        int col = COLUMNS;
        int uEndHouse = userEndHouse;
        boolean compTurn = otherPlayersTurn;
        boolean goAgain = false;
        int compEndHouse = otherPlayerEndHouse;

        for(int x = 0; x < seeds; x++)
        {
            if(c >= col || c < 0)
            {
                //Makes sure seeds added to end zone of appropriate player
                if(r == 1)
                {
                    r--;
                    c = col - 1;
                    if(!compTurn) //If the users turn, add to the end zone
                    {
                        uEndHouse++;
                        if(x+1 >= seeds) //If last seed, goAgain
                        {
                            goAgain = true;
                        }
                    }
                    continue;    //If last seed, goAgain
                }
                else
                {
                    r++;
                    c = 0;
                    continue;
                }
            }

            //If last seed and it lands on an empty house, then all the seeds in the matching house should be yours
            if((x+1 >= seeds) && (psuedoBoard[r][c] == 0))
            {
                if(r == 1 && !(compTurn))
                {
                    uEndHouse += psuedoBoard[r-1][c];
                    psuedoBoard[r-1][c] = 0;
                    uEndHouse++;
                }

            }
            else
            {
                psuedoBoard[r][c]++;
            }

            if(r == 1)
            {
                c++;
            }
            else
            {
                c--;
            }
        }
        //If the value is positive, the AI is winning, else, the user is winning
        if(goAgain == true)
        {
            extra = 1;
        }
        return uEndHouse - compEndHouse + extra;
    }

    /*By using the min max tree, it will be able to return the appropriate column that will make the move*/
    public algorithmAI minmax(algorithmAI go, int alpha, int beta, int depth, boolean maxTree)
    {
        //If no depth necessary, simply return the same column value provided
        if(depth == 0 || go.column == COLUMNS || isGameOver()){
            return go;
        }

        //Implement the max aspect of the tree, returning the object with the best value
        if(maxTree){
            int bestValue = Integer.MIN_VALUE;
            int bestCol=0;

            for(int i = 0; i < COLUMNS; i++)
            {
                if(board[1][i] != 0)
                {
                    int value = psuedoMovingSeeds(board[1][i],1,i+1);
                    algorithmAI node = new algorithmAI(i, value);
                    algorithmAI abc= minmax(node, alpha, beta,depth-1, false);
                    bestValue= Math.max(bestValue, abc.value);
                    alpha = Math.max(alpha, bestValue);
                    if(bestValue == abc.value)
                    {
                        bestCol = abc.column;
                    }
                    if(beta <= alpha)
                    {
                        break;
                    }
                }
            }
            algorithmAI returnObj = new algorithmAI(bestCol,bestValue);
            return returnObj;
        }

        //Implements the min aspect of the tree returning the object with the best value
        else
        {
            int bestValue = Integer.MAX_VALUE;
            int bestCol=0;
            for(int i=0; i<COLUMNS; i++)
            {
                if(board[1][i] != 0)
                {
                    int value = psuedoMovingSeeds(board[1][i],1,i+1);
                    algorithmAI node = new algorithmAI(i, value);
                    algorithmAI abc= minmax(node,alpha, beta, depth-1, true);
                    bestValue= Math.min(bestValue, abc.value);
                    beta = Math.min(beta, bestValue);
                    if(bestValue==abc.value)
                    {
                        bestCol=abc.column;
                    }
                    if(beta <= alpha)
                    {
                        break;
                    }
                }
            }
            algorithmAI returnObj = new algorithmAI(bestCol,bestValue);
            return returnObj;
        }
    }

}

//Initializing the values for algorithm in order to configure the appropriate move
class algorithmAI
{
    int column = AI.COLUMNS;
    int computerEndZone;
    int userEndZone;
    int value;


    algorithmAI(int c,int cEndZone, int uEndZone)
    {
        column = c;
        computerEndZone = cEndZone;
        userEndZone = uEndZone;
    }
    algorithmAI(int c, int v)
    {
        column = c;
        value = v;
    }
}
