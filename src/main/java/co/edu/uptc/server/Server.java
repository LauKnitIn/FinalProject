package co.edu.uptc.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public ServerSocket server = null;
    public int PORT = 12345;
    private int nClient = 0;
    public static List<ClientManager> connectedClients = new ArrayList<>();

    public void start() {
        try {
            this.server = new ServerSocket(this.PORT);
            System.out.println("Servidor iniciado en puerto " + this.PORT);

            while (true) {
                Socket clientSocket = server.accept();
                nClient++;
                System.out.println("Cliente " + nClient + " conectado.");

                ClientManager clientManager = new ClientManager(clientSocket, nClient, this);
                connectedClients.add(clientManager);

                clientManager.start();  // Inicia hilo para atender al cliente
            }

        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public synchronized void removeClient(ClientManager clientManager) {
        connectedClients.remove(clientManager);
        System.out.println("Cliente " + clientManager.getClientId() + " desconectado.");
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
    /*public void init() {
        startServer();
    }*/


    /*public void startServer() {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Servidor iniciado");
            while (true) {
                System.out.println("Ha entrado al while");
                Socket sc = server.accept();
                nClient++;
                System.out.println(nClient);
                System.out.println("Cliente: " + nClient + " conectado");
                ClientManager cm = new ClientManager(sc, nClient, this);
                connectedClients.add(cm);
                cm.start();
                if (!cm.isAlive()) {
                    System.out.println(cm.isAlive());
                    System.out.println(">>>Client " + nClient + "disconnected<<<");
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static String showActiveClientsList() {
        String clients = "[ ";
        for (ClientManager cliente : connectedClients) {
            clients+=(cliente.isAlive())?("(" +cliente.nClient + ") "): "";
        }
        clients+=("]");
        return clients;
    }*/
    

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
