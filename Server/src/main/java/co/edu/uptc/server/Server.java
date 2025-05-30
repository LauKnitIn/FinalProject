package co.edu.uptc.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;

import co.edu.uptc.model.Difficulty;
import co.edu.uptc.model.Game;
import co.edu.uptc.model.Mode;
import co.edu.uptc.model.Player;
import co.edu.uptc.model.Word;
import co.edu.uptc.model.WordHandler;

public class Server {

    public ServerSocket server = null;
    public int PORT = 12345;
    private int nClient = 0;
    private WordHandler wordHandler;
    public static List<ClientManager> connectedClients = new ArrayList<>();
    public static List<ClientManager> waitingPlayers = new ArrayList<>();// lista de jugadores en espera para
                                                                         // multijugador

    public void start() {
        this.wordHandler = new WordHandler();
        try {
            this.server = new ServerSocket(this.PORT);
            System.out.println("Servidor iniciado en puerto " + this.PORT);

            while (true) {
                Socket clientSocket = server.accept();
                nClient++;
                System.out.println("Cliente " + nClient + " conectado.");
                ClientManager clientManager = new ClientManager(clientSocket, nClient, this);
                connectedClients.add(clientManager);
                clientManager.start();
            }

        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public String getWordsByDifficulty(Difficulty chosenDifficulty) {
        List<String> words = this.wordHandler.getWordListDifficulty(chosenDifficulty);
        Gson gson = new Gson();
        String json = gson.toJson(words);
        return json;
    }

    public void manageMultiPLayerGame() {
        if (waitingPlayers.isEmpty()) {

        }
    }

    // Agrega cliente a la lista de espera multiplayer
    public synchronized void addWaitingMultiplayerClient(ClientManager currentClient) {
        if (!(waitingPlayers.contains(currentClient))) {
            waitingPlayers.add(currentClient);
        }
    }

    // Remueve cliente de la lista de espera multiplayer
    public synchronized void removeWaitingMultiplayerClient(ClientManager currentClient) {
        if (waitingPlayers.contains(currentClient)) {
            waitingPlayers.remove(currentClient);
        }
    }

    public synchronized ClientManager findOpponent(ClientManager requester, Difficulty difficulty) {
        Iterator<ClientManager> iterator = waitingPlayers.iterator();
        ClientManager opponent = null;
        while (iterator.hasNext()) {
            ClientManager currentClient = iterator.next();
            if (!requester.equals(currentClient) &&
                    difficulty.equals(currentClient.getChosenDifficulty())) {
                opponent = currentClient;
                iterator.remove();
            }
        }
        return opponent;
    }

    public int getAmount() {
        return waitingPlayers.size();
    }

    public synchronized void tryMatchPlayer(ClientManager requester, Difficulty difficulty, String wordForOpponent, String wordForRequester) {
        // Buscar oponente compatible en la lista de espera
        ClientManager opponent = null;
        String wordToRequester = "";
        Iterator<ClientManager> iterator = waitingPlayers.iterator();

        while (iterator.hasNext()) {
            ClientManager currentClient = iterator.next();
            if (!currentClient.equals(requester) &&
                    difficulty.equals(currentClient.getChosenDifficulty())) {
                opponent = currentClient;
                wordToRequester = opponent.getPendingWord();
                iterator.remove(); // eliminar oponente de la lista de espera de forma segura
                break;
            }
        }

        if (opponent == null) {
            System.out.println("IN OPPONENT NULL " + requester.getPlayerName());
            // No hay oponente disponible, agregar requester a la lista de espera si no está
            // ya
            if (!waitingPlayers.contains(requester)) {
                waitingPlayers.add(requester);
            }
            requester.sendLine("Esperando a otro jugador para iniciar partida...");
            requester.sendEnd();
        } else {
            // Oponente encontrado: crear juego compartido y asignarlo a ambos
            waitingPlayers.remove(requester);
            Game sharedGame = new Game(difficulty, Mode.MULTIPLAYER);
            Player p1 = new Player(requester.getPlayerName());
            Player p2 = new Player(opponent.getPlayerName());
            sharedGame.configGame(p1, p2);
            requester.getGameManager().setGame(sharedGame);
            opponent.getGameManager().setGame(sharedGame);
            System.out.println("PALABRAS: [requester: " + opponent.getPendingWord() + ", opponent: " + requester.getPendingWord() + " ]");
            Word opponentWord = new Word(opponent.getPendingWord());
            Word requesterWord = new Word(requester.getPendingWord());
            p1.setWordToGuess(opponentWord);
            p2.setWordToGuess(requesterWord);
            p1.setID(requester.getClientId());
            p2.setID(opponent.getClientId());
            // Notificar a ambos jugadores
            requester.sendLine("EMPAREJADO");
            requester.sendLine("con " + opponent.getPlayerName());
            requester.sendLine("Juego iniciado. Progreso: " + sharedGame.getPlayerProgress(p1));
            requester.sendEnd();

            opponent.sendLine("EMPAREJADO");
            opponent.sendLine("con " + requester.getPlayerName());
            opponent.sendLine("Juego iniciado. Progreso: " + sharedGame.getPlayerProgress(p2));
            opponent.sendEnd();
        }
    }

    public synchronized void removeClient(ClientManager clientManager) {
        connectedClients.remove(clientManager);
        // System.out.println("Cliente " + clientManager.getClientId() + "
        // desconectado.");
    }

    public List<ClientManager> getConnectedClients() {
        return connectedClients;
    }

    public void stop() {
        try {
            if (server != null) {
                server.close();
                System.out.println("Servidor detenido.");
            }
        } catch (Exception e) {
            System.err.println("Error al detener el servidor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}