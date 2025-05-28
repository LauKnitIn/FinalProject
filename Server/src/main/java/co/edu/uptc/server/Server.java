package co.edu.uptc.server;

import co.edu.uptc.model.Mode;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    public ServerSocket server = null;
    public int PORT = 12345;
    private int nClient = 0;
    public static List<ClientManager> connectedClients = Collections.synchronizedList(new ArrayList<>());
    public static List<ClientManager> singlePlayers = Collections.synchronizedList(new ArrayList<>());
    public static List<ClientManager> multiPlayers = Collections.synchronizedList(new ArrayList<>());

    public void init() {
        startServer();
    }

    public void startServer() {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Servidor iniciado");
            while (true) {
                Socket sc = server.accept();
                System.out.println("Ha entrado al while");
                nClient++;
                System.out.println("Cliente: " + nClient + " conectado");
                ClientManager cm = new ClientManager(sc, nClient, this);
                connectedClients.add(cm);
                cm.start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        } finally {
            if (server != null && !server.isClosed()) {
                try {
                    server.close();
                    System.out.println("Servidor detenido.");
                } catch (IOException e) {
                    System.err.println("Error al cerrar el socket del servidor: " + e.getMessage());
                }
            }
        }
    }

    public static void removeClient(ClientManager clientManager){
        boolean removed = connectedClients.remove(clientManager);
        if (removed) {
            System.out.println("Cliente #" + clientManager.getId() + " desconectado y eliminado de la lista");
        }
    }
    public static String showActiveClientsList() {
        StringBuilder clients = new StringBuilder("[ ");
        synchronized (connectedClients) {
            if (connectedClients.isEmpty()) {
                clients.append("No hay clientes conectados ");
            } else {
                for (ClientManager client : connectedClients) {
                    clients.append("(Cliente #").append(client.getId()).append(") ");
                }
            }
        }
        clients.append("]");
        return clients.toString();
    }

    private void handleMultiplayerJoin(ClientManager cm){
        if (!multiPlayers.isEmpty()) {
            multiPlayers.add(cm);
        }else{
            System.out.println("Administrados de sala: " + cm.getName());
            
        }
    }
    public static void processNewClientChoices(ClientManager cm){
        Mode mode = cm.getChooshenMode();
        switch (mode) {
                case ONE_PLAYER:
                    singlePlayers.add(cm);
                    break;
                case MULTIPLAYER:
                    
                default:
                    throw new AssertionError();
            }
    }
    public static void main(String[] args) {
        Server server = new Server();
        server.init();
    }
}
