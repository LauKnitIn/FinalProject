package co.edu.uptc.model;

import java.io.IOException;

import co.edu.uptc.client.Client;
import co.edu.uptc.interfaces.Contract;
import co.edu.uptc.interfaces.Contract.Presenter;

public class ClientModel implements Contract.Model {
    Client client;
    Presenter presenter;

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void initSocket() {
        client = new Client();
        client.createConection();
    }

    @Override
    public void sendCommand(String command) {
        handleCommand(command);
    }

    private void handleCommand(String command) {
        String[] parts = command.split(" ");
        String keyword = parts[0].toUpperCase();
        System.out.println(keyword);
        switch (keyword) {
            case "NAME":
                String playerName = command.substring(5); // Salta "NAME "
                client.sendName(playerName);
                break;
            case "START":
                if (parts.length == 3) {
                    String difficulty = parts[1];
                    System.out.println("DIF ->> " + difficulty);
                    int players = Integer.parseInt(parts[2]);
                    System.out.println("player ->> " + players);
                    boolean isMultiplayer = false;
                    if (players == 1) {
                        System.out.println("IN");
                        client.sendGameData(difficulty, isMultiplayer);
                    } 
                }
                break;
            case "STARTM":
                if (parts.length == 5) {
                    String difficulty = parts[1];
                    System.out.println("DIF ->> " + difficulty);
                    int players = Integer.parseInt(parts[2]);
                    String word1 = parts[3];
                    String word2 = parts[4];
                    System.out.println("player ->> " + players);
                    boolean isMultiplayer = false;
                    if (players == 2) {
                        System.out.println("IN");
                        client.sendGameData(difficulty, isMultiplayer, word1, word2);
                    }
                }
                break;
            case "GUESS":
                String letter = parts[1];
                client.makeGuess(letter);
                break;
            /*
             * this.gameMode = null;
             * if (modeValue == 1) {
             * this.gameMode = Mode.ONE_PLAYER;
             * } else if (modeValue == 2 && parts.length >= 5) {
             * this.gameMode = Mode.MULTIPLAYER;
             * Word word1 = new Word(parts[3]);
             * Word word2 = new Word(parts[4]);
             * this.game.assignWordsMultiplayer(word1, word2);
             * }
             * this.game = new Game(difficulty, this.gameMode);
             * if (modeValue == 1) {
             * this.game.getPlayerOne().setName(this.playerName);
             * }
             * output.println("Juego iniciado en modo " + this.gameMode + " con dificultad "
             * + difficulty);
             * } else {
             * output.
             * println("Comando START inválido. Usa: START:modo,dificultad,palabraP1,palabraP2"
             * );
             * }
             * output.println("END");
             * break;
             * 
             * case "GUESS":
             * if (parts.length == 2 && this.game != null) {
             * char letter = parts[1].charAt(0);
             * System.out.println(letter + "LETRA");
             * boolean correct = this.game.processGuess(letter);
             * String progress = this.game.getPlayerProgress();
             * String palabra = this.game.getPlayerOne().getWordToGuess().getValue();
             * System.out.println("PALABRA --> " + palabra);
             * String message = "";
             * if (correct) {
             * message = "Correcto";
             * } else {
             * message = "Incorrecto";
             * }
             * output.println(/*(message) + " Progreso: " + progress);
             * if (game.isFinished()) {
             * output.println("Juego terminado. ¿Ganaste?: " + this.game.getWinner());
             * serverInstance.removeSinglePlayer(this);
             * }
             * } else {
             * output.println("No se puede adivinar letra. ¿Iniciaste el juego?");
             * }
             * output.println("END");
             * break;
             * 
             * case "STATUS":
             * if (game != null) {
             * output.println("Progreso: " + this.game.getPlayerProgress());
             * output.println("Intentos restantes: " + this.game.getPlayerAttempts());
             * } else {
             * output.println("No has iniciado un juego todavía.");
             * }
             * output.println("END");
             * break;
             * 
             * case "EXIT":
             * output.println("Gracias por jugar. ¡Hasta luego!");
             * try {
             * client.close();
             * } catch (IOException e) {
             * e.printStackTrace();
             * }
             * output.println("END");
             * break;
             * 
             * default:
             * output.println("Comando no reconocido: " + command);
             * output.println("END");
             * break;
             */

        }
    }

    @Override
    public String getStatus(String value) {
        return this.client.makeGuess(value);
    }

    @Override
    public String getClientName() {
        return client.getPlayerName();
    }

    public boolean isFull(){
         return client.isFull();
    }

    @Override
    public boolean isEmpty() {
        return this.client.isEmpty();
    }

    @Override
    public boolean isAvailable() {
        return this.client.isAvailable();
    }

}
