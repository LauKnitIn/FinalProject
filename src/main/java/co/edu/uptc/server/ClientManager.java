package co.edu.uptc.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import co.edu.uptc.model.Difficulty;
import co.edu.uptc.model.Game;
import co.edu.uptc.model.Mode;
import co.edu.uptc.model.Player;
import co.edu.uptc.model.Word;

public class ClientManager extends Thread {

    Socket client;
    BufferedReader input;
    PrintWriter output;
    int nClient;
    Server serverInstance;
    Scanner sc = new Scanner(System.in);
    private Game game;

    public ClientManager(Socket socket, int nClient, Server serverInstance) {
        this.client = socket;
        this.nClient = nClient;
        this.serverInstance = serverInstance;
    }

    @Override
    public void run() {
        try {
            this.input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.output = new PrintWriter(client.getOutputStream(), true);

            String line;
            while ((line = input.readLine()) != null) {
                handleCommand(line);
            }

        } catch (IOException e) {
            System.out.println("Error con el cliente: " + e.getMessage());
        }
    }

    private void handleCommand(String command) {
        String[] parts = command.split(" ");
        String keyword = parts[0].toUpperCase();

        switch (keyword) {
            case "NAME":
                playerName = command.substring(5); // Salta "NAME "
                output.println("Bienvenido, " + playerName + "!");
                break;

            case "START":
                if (parts.length >= 3) {
                    int modeValue = Integer.parseInt(parts[1]);
                    Difficulty difficulty = Difficulty.valueOf(parts[2].toUpperCase());

                    Mode gameMode;
                    if (modeValue == 1) {
                        gameMode = Mode.ONE_PLAYER;
                    } else if (modeValue == 2 && parts.length >= 5) {
                        gameMode = Mode.MULTIPLAYER;
                        Word word1 = new Word(parts[3]);
                        Word word2 = new Word(parts[4]);
                        this.game.assignWordsMultiplayer(word1, word2);
                    }

                    this.game = new Game(difficulty, gameMode);

                
                    output.println("Juego iniciado en modo " + gameMode + " con dificultad " + difficulty);
                    output.println("Progreso: " + game.getPlayerProgress());
                } else {
                    output.println("Comando START inválido. Usa: START:modo,dificultad,palabraP1,palabraP2");
                }
                break;

            case "GUESS":
                if (parts.length == 2 && game != null) {
                    char letter = parts[1].charAt(0);
                    boolean correct = game.processGuess(letter);
                    String progress = game.getPlayerProgress();
                    String message = "";
                    if (correct) {
                        message = "Correcto";
                    }else{
                        message = "Incorrecto";
                    }
                    output.println((message) + " Progreso: " + progress);
                    if (game.isFinished()) {
                        output.println("🎉 Juego terminado. ¿Ganaste?: " + game.getWinner());
                    }
                } else {
                    output.println("No se puede adivinar letra. ¿Iniciaste el juego?");
                }
                break;

            case "STATUS":
                if (game != null) {
                    output.println("Progreso: " + game.getPlayerProgress());
                    output.println("Intentos restantes: " + game.getPlayerAttempts());
                } else {
                    output.println("No has iniciado un juego todavía.");
                }
                break;

            case "EXIT":
                output.println("Gracias por jugar. ¡Hasta luego!");
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            default:
                output.println("Comando no reconocido: " + command);
                break;
        }
        /*
         * public ClientManager(Socket socket, int nClient, Server serverInstance) {
         * super();
         * client = socket;
         * this.nClient = nClient;
         * this.serverInstance = serverInstance;
         * }
         * 
         * public synchronized void run() {
         * try {
         * input = new BufferedReader(new InputStreamReader(client.getInputStream()));
         * output = new PrintWriter(client.getOutputStream(), true);
         * String initialMessage = input.readLine();
         * // if (initialMessage != null) {
         * 
         * // }
         * System.out.print("Mensaje del cliente: " + nClient + " " + initialMessage +
         * "\n"
         * + ">>>Ingrese la respuesta para el cliente #" + nClient + ": ");
         * String message = sc.nextLine();
         * output.println(message);
         * while ((message = input.readLine()) != null) {
         * if (message.equalsIgnoreCase("clientes")) {
         * output.println(Server.showActiveClientsList());
         * }else if (message.equalsIgnoreCase("salir")) {
         * output.println("Adios, cliente");
         * Server.connectedClients.get(nClient).finalize();
         * System.out.println("Client has desconected");
         * sc.close();
         * return;
         * }else{
         * System.out.println("Mensaje del cliente #" + nClient + " " + message + "\n" +
         * ">>>Respuesta para el cliente #" + nClient + ": ");
         * output.println(sc.nextLine());
         * }
         * 
         * }
         * } catch (Exception e) {
         * 
         * } catch (Throwable e) {
         * // TODO Auto-generated catch block
         * e.printStackTrace();
         * }
         * 
         * }
         */
    }

}
