package co.edu.uptc.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import co.edu.uptc.model.Difficulty;
import co.edu.uptc.model.Game;
import co.edu.uptc.model.Mode;

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

    private Difficulty evlauateDifficulty(String val){
        Difficulty selected = null;
        switch (val) {
            case "1":
                selected = Difficulty.EASY;
                break;
            case "2":
                selected = Difficulty.NORMAL;
                break;
            case "3":
                selected = Difficulty.DIFFICULT;
                break;
            case "4":
                selected = Difficulty.EXTREME;
                break;
            default:
                break;
        }
        return selected;
    }

    private void handleCommand(String command) {
        String[] parts = command.split(" ");
        String keyword = parts[0].toUpperCase();
        System.out.println(keyword);
        switch (keyword) {
            case "NAME":
                String playerName = command.substring(5); // Salta "NAME "
                output.println("Bienvenido, " + playerName + "!");
                 output.println("END");
                break;
            case "START":
                if (parts.length >= 3) {
                    int modeValue = Integer.parseInt(parts[1]);
                    Difficulty difficulty = evlauateDifficulty(parts[2].toUpperCase());

                    Mode gameMode = null;
                    if (modeValue == 1) {
                        gameMode = Mode.ONE_PLAYER;
                    } else if (modeValue == 2 && parts.length >= 5) {
                        gameMode = Mode.MULTIPLAYER;
                        Word word1 = new Word(parts[3]);
                        Word word2 = new Word(parts[4]);
                        this.game.assignWordsMultiplayer(word1, word2);
                    }
                    this.game = new Game(difficulty, gameMode);

                
                    //output.println("Juego iniciado en modo " + gameMode + " con dificultad " + difficulty);
                    output.println("Progreso: " + game.getPlayerProgress());
                } else {
                    output.println("Comando START inválido. Usa: START:modo,dificultad,palabraP1,palabraP2");
                }
                 output.println("END");
                break;

            case "GUESS":
                if (parts.length == 2 && this.game != null) {
                    char letter = parts[1].charAt(0);
                    System.out.println(letter + "LETRA");
                    boolean correct = this.game.processGuess(letter);
                    String progress = this.game.getPlayerProgress();
                    String palabra = this.game.p1.getWordToGuess().getValue();
                    System.out.println("PALABRA --> " + palabra);
                    String message = "";
                    if (correct) {
                        message = "Correcto";
                    }else{
                        message = "Incorrecto";
                    }
                    output.println((message) + " Progreso: " + progress);
                    if (game.isFinished()) {
                        output.println("Juego terminado. ¿Ganaste?: " + this.game.getWinner());
                    }
                } else {
                    output.println("No se puede adivinar letra. ¿Iniciaste el juego?");
                }
                 output.println("END");
                break;

            case "STATUS":
                if (game != null) {
                    output.println("Progreso: " + this.game.getPlayerProgress());
                    output.println("Intentos restantes: " + this.game.getPlayerAttempts());
                } else {
                    output.println("No has iniciado un juego todavía.");
                }
                 output.println("END");
                break;

            case "EXIT":
                output.println("Gracias por jugar. ¡Hasta luego!");
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                 output.println("END");
                break;

            default:
                output.println("Comando no reconocido: " + command);
                output.println("END");
                break;
        }
       
    }

    public String getClientId() {
       return String.valueOf(this.nClient);
    }

}
