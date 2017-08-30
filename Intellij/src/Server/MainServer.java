package Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/*MainServer allows for multiple clients to connect, while providing the board configuration from the properties file*/
public class MainServer {
    public static void main(String[] args) throws IOException {
        System.out.println("Game Server is running!");
        Properties prop = new Properties();
        InputStream input = null;
        ServerSocket listener = new ServerSocket(9090);

        while (true) {
            try {
                input = new FileInputStream("src/Server/configBoard.properties"); //Configuration for board is found the "configBoard" files
                prop.load(input); //Load configuration
                int numHoles = Integer.parseInt(prop.getProperty("numHoles"));
                int numSeeds = Integer.parseInt(prop.getProperty("numSeeds"));
                long timeLimit = Long.parseLong(prop.getProperty("timeLimit"));
                char gameMode = prop.getProperty("gameMode").charAt(0);
                GameServer newGame = new GameServer(numHoles, numSeeds, timeLimit, gameMode);

                /*Each player is added to its own socket, AI or user, in order to keep track of the connection*/
                Socket socket = listener.accept();
                Boolean isFirstPlayer = true;
                newGame.addPlayer(socket, isFirstPlayer);

                socket = listener.accept();
                isFirstPlayer = false;
                newGame.addPlayer(socket, isFirstPlayer);
                newGame.run();

            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
