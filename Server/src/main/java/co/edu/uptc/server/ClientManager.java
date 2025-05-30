package co.edu.uptc.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import co.edu.uptc.model.Difficulty;
import co.edu.uptc.model.Mode;

public class ClientManager extends Thread {

    private Socket client;
    private BufferedReader input;
    private PrintWriter output;
    private int nClient;
    private Server serverInstance;
    private GameManager gameManager;
    private String playerName;
    private String pendingWord;
    private Difficulty chosenDifficulty;
    private Mode chosenMode;
    private boolean isAdmin;

    /*
     * Player 1 pq = null;
     * Player 2 pq = null;
     * 
     * cuando alguien selccione el modo multijugador se valida que
     * cuando se le de clic al botón mp --v
     * public void multiplayerGameStatus(){
     * if(p1 == null){
     * generateAdmin()
     * }else{
     * generateOponents();
     * }
     */
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
        System.out.println(command);
        switch (keyword) {
            case "VALIDATE":
                //evaluateRole();
                sendLine(serverInstance.getAmount() + "");
                sendEnd();
                break;
            case "NAME":
                this.playerName = command.substring(5);
                output.println("Bienvenido, " + playerName + "!");
                output.println("END");
                break;

            case "START":
                // Servidor envía la cantidad de jugadores mp: cmp < 2
                if (parts.length < 3) {
                    sendLine("Comando START inválido. Usa: START modo dificultad [palabra1 palabra2]");
                    sendEnd();
                }
                int modeValue = Integer.parseInt(parts[1]);
                chosenDifficulty = evlauateDifficulty(parts[2]);
                if (chosenDifficulty == null) {
                    sendLine("Dificultad no válida.");
                    sendEnd();
                }
                chosenMode = (modeValue == 1) ? Mode.ONE_PLAYER : Mode.MULTIPLAYER;
                if (chosenMode == Mode.MULTIPLAYER) {
                    System.out.println(command);
                    // System.out.println(parts[0] + " " + parts[1] + " "  + parts[2] + " " + parts[1]);
                    this.pendingWord = parts[3];
                    System.out.println("PENDING WORD " + pendingWord);
                    //
                }
                gameManager = new GameManager(this, chosenMode, chosenDifficulty, serverInstance);
                gameManager.startGame(playerName, parts);
                break;
            case "RETRY_MATCH": // Comando para reintentar emparejar
                if (chosenMode == Mode.MULTIPLAYER && chosenDifficulty != null) {
                    gameManager.retryMatch();
                } else {
                    sendLine("No estás en modo multijugador o no has seleccionado dificultad.");
                    sendEnd();
                }
                break;

            case "GUESS":
                if (gameManager == null || gameManager.getGame() == null) {
                    output.println("No has iniciado un juego.");
                    output.println("END");
                } else if (parts.length != 2) {
                    output.println("Comando GUESS inválido. Usa: GUESS letra");
                    output.println("END");
                } else {
                    char letter = parts[1].charAt(0);
                    gameManager.processGuess(letter, this);
                }
                break;

            case "STATUS":
                if (gameManager == null) {
                    sendLine("No has iniciado un juego.");
                    sendEnd();
                }
                gameManager.sendStatus();
                break;

            case "EXIT":
                output.println("Gracias por jugar. ¡Hasta luego!");
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "GET_NAME":
                sendMessage(getPlayerName());
                sendEnd();
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "GET_LIST":
                sendList();
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            default:
                output.println("Comando no reconocido: " + command);
                output.println("END");
                break;
        }

    }

    private void evaluateRole(){
        if (this.chosenMode == Mode.MULTIPLAYER) {
            int amount = serverInstance.getAmount();
            if (amount%2 == 0 ) {
                this.isAdmin = true;
            }else if (amount%2 != 0) {
                this.isAdmin = false;
            }
        }
    }

    public void sendLine(String message) {
        output.println(message);
    }

    public void sendList() {
        output.println(this.serverInstance.getWordsByDifficulty(chosenDifficulty));
    }

    public void sendEnd() {
        output.println("END");
    }

    public void sendMessage(String message) {
        output.println(message);
    }

    public int getClientId() {
        return nClient;
    }

    private Difficulty evlauateDifficulty(String val) {
        Difficulty selected = null;
        switch (val) {
            case "EASY":
                selected = Difficulty.EASY;
                break;
            case "NORMAL":
                selected = Difficulty.NORMAL;
                break;
            case "DIFFICULT":
                selected = Difficulty.DIFFICULT;
                break;
            case "EXTREME":
                selected = Difficulty.EXTREME;
                break;
            default:
                break;
        }
        return selected;
    }

    public Difficulty getChosenDifficulty() {
        return this.chosenDifficulty;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

    public String getPendingWord() {
        return pendingWord;
    }

    public void setPendingWord(String pendingWord) {
        this.pendingWord = pendingWord;
    }

}
