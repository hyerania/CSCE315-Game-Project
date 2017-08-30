package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*Allows client AI to setup based on configuration sent from server*/
public class MainAI {
    public static void main(String[] args) throws IOException {
        System.out.println("AI started!");
        String serverAddress = "localhost";
        Socket s = new Socket(serverAddress, 9090);

        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String welcome = input.readLine();
        String configuration = input.readLine();

        System.out.println(welcome);
        System.out.println(configuration);

        PrintWriter output = new PrintWriter(s.getOutputStream(), true);

        //Creates a board based on the AI
        new AI(input, output, configuration);
    }
}
