package co.edu.uptc.server;

import co.edu.uptc.model.Difficulty;
import co.edu.uptc.model.Game;
import co.edu.uptc.model.Mode;
import co.edu.uptc.model.Player;

public class GameManager extends Thread {

    private ClientManager clientManager;
    private Mode mode;
    private Difficulty difficulty;
    private Server server;
    private Game game;

    public GameManager(ClientManager clientManager, Mode mode, Difficulty difficulty, Server server) {
        this.clientManager = clientManager;
        this.mode = mode;
        this.difficulty = difficulty;
        this.server = server;
    }

    public void startGame(String playerName, String[] parts) {
        if (mode == Mode.ONE_PLAYER) {
            startSinglePlayer(playerName);
        } else if (mode == Mode.MULTIPLAYER) {
            startMultiplayer(playerName, parts);
        }
    }

    private void startSinglePlayer(String playerName) {
        System.out.println("IN START SINGLE PLAYER");
        game = new Game(difficulty, mode);
        Player p1 = new Player(playerName);
        game.configGame(p1);
        /*
         * clientManager.sendMessage("Juego iniciado en modo ONE_PLAYER con dificultad "
         * + difficulty);
         * clientManager.sendMessage("MAX_ATTEMPTS:" + difficulty.getMaxAttempts());
         */
        clientManager.sendMessage("Progreso: " + game.getPlayerProgress());
        clientManager.sendMessage("END");
    }

    private void startMultiplayer(String playerName, String[] parts) {
        if (parts.length < 4) {
            clientManager.sendLine("Error: faltan palabras para multijugador.");
            clientManager.sendEnd();
            return;
        }
        String wordForOpponent = parts[3];
        this.difficulty = clientManager.getChosenDifficulty();
        this.mode = Mode.MULTIPLAYER;
        server.tryMatchPlayer(clientManager, difficulty, wordForOpponent, "null");
    }

    public void retryMatch() {
        if (mode != Mode.MULTIPLAYER) {
            clientManager.sendLine("No estás en modo multijugador.");
            clientManager.sendEnd();
            return;
        }
        // el clienteManager ya tiene dificultad elegida
        clientManager.sendLine("Reintentando emparejamiento...");
        clientManager.sendEnd();

        // Reintentar emparejar guardadas si tienes
        server.tryMatchPlayer(clientManager, difficulty, clientManager.getPendingWord(), "palabraDummyParaJugador");
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    public void processGuess(char letter, ClientManager source) {
        Player guessMaker = null;
        if (game == null) {
            clientManager.sendMessage("No hay juego iniciado.");
            clientManager.sendMessage("END");
        } else {
            // eleccion del jugador que hace la peticion
            if (game.getPlayerOne().getId() == source.getClientId()) {
                guessMaker = game.getPlayerOne();
            } else if (game.getPlayerTwo().getId() == source.getClientId()) {
                guessMaker = game.getPlayerTwo();
            }
        }
        Player opponent = (guessMaker == game.getPlayerOne()) ? game.getPlayerTwo() : game.getPlayerOne();
        if (guessMaker != null) {

            game.processGuess(letter, guessMaker);
            String progress = game.getPlayerProgress(guessMaker);
            int attempts = game.getPlayerAttempts(guessMaker);
            clientManager.sendMessage("Progreso: " + progress);
            clientManager.sendMessage("Intentos restantes: " + attempts);
        }
        game.isGameFinished(guessMaker);
        if (game.isFinished()) {
            if (guessMaker.hasWon()) {
                clientManager.sendMessage("[¡GANADOR! " + clientManager.getPlayerName() + "]");
            } else if (guessMaker.hasLost()) {
                clientManager.sendMessage("Juego terminado. Has perdido.");
            } else if (opponent != null && opponent.hasLost()) {
                clientManager.sendMessage(
                        "[¡GANADOR! " + clientManager.getPlayerName() + "] (el oponente se quedó sin intentos)");
            } else {
                clientManager.sendMessage("Juego terminado.");
            }
        }
        clientManager.sendMessage("END");
    }

    public void processGuess(char letter) {
        // aqui hay que extraer el jugador que esta haciendo le peticion
        if (game == null) {
            clientManager.sendMessage("No hay juego iniciado.");
            clientManager.sendMessage("END");
        } else {
            game.processGuess(letter);
        }
        String progress = game.getPlayerProgress();
        int attempts = game.getPlayerAttempts();

        clientManager.sendMessage(/* "Progreso: " + */ progress);
        clientManager.sendMessage("Intentos restantes: " + attempts);

        if (game.isFinished()) {
            if (game.getWinner() != null && game.getWinner().isWinner()) {
                clientManager.sendMessage("¡Ganaste!" + " " + clientManager.getPlayerName());
            } else {
                clientManager.sendMessage("Juego terminado");
            }
        }
        clientManager.sendEnd();
    }

    public void sendStatus() {
        if (game == null) {
            clientManager.sendMessage("No hay juego iniciado.");
        } else {
            clientManager.sendMessage("Progreso: " + game.getPlayerProgress());
            clientManager.sendMessage("Intentos restantes: " + game.getPlayerAttempts());
        }
        clientManager.sendEnd();
    }

    public Difficulty geDifficulty() {
        return this.difficulty;
    }
}
