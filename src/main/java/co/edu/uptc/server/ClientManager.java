package co.edu.uptc.server;

import co.edu.uptc.model.Mode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ClientManager extends Thread {

    Socket client;
    BufferedReader input;
    PrintWriter output;
    int clientId;
    Scanner sc = new Scanner(System.in);

    public ClientManager(Socket socket, int nClient, Server serverInstance) {
        super();
        client = socket;
        this.clientId = nClient;
    }

    public int getClientId(){
        return clientId;
    }

    public synchronized void run() {
        try {
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(client.getOutputStream(), true);
            String initialMessage = input.readLine();
            if (initialMessage == null) { System.out.println("Cliente #" + clientId + " cerró inmediatamente"); return;}
            System.out.print("Mensaje del cliente: " + clientId + " " + initialMessage + "\n"+ ">>>Ingrese la respuesta para el cliente #" + clientId + ": ");
            String serverResponse = sc.nextLine();
            System.out.println("Seleccione el modo de juego mediante el número:\n1.SINGLE_PLAYER\n2.MULTIPLAYER");
            Mode mode = (sc.nextInt() == 1)? Mode.ONE_PLAYER:Mode.MULTIPLAYER;
            client.
            Server.connectedClients.add(this);
            output.println("Seleccione el la dificultad del juego:\nEASY\nNORMAL\nDIFFICULT\nEXTREMLY");
            String gameMode = input.readLine();
            
           output.println(serverResponse);
            String clientMessage;
            while ((clientMessage = input.readLine()) != null) {
                System.out.println("Mensaje del Cliente #" + clientId + ": " + clientMessage);

                if ("clientes".equalsIgnoreCase(clientMessage.trim())) {
                    output.println(Server.showActiveClientsList());
                } else if ("salir".equalsIgnoreCase(clientMessage.trim())) {
                    output.println("Adios, cliente. Desconectando...");
                    System.out.println("Cliente #" + clientId + " solicitó desconexión.");
                    break;
                } else {
                    System.out.print(">>> Ingrese la respuesta para el Cliente #" + clientId + " (o 'server_exit' para terminar este cliente desde server): ");
                    serverResponse = sc.nextLine();
                    if ("server_exit".equalsIgnoreCase(serverResponse.trim())) {
                        output.println("El servidor ha terminado tu conexión.");
                        System.out.println("Desconexión forzada del Cliente #" + clientId + " desde el servidor.");
                        break;
                    }
                    output.println(serverResponse);
                }
            }

            if (clientMessage == null) {
                System.out.println("Cliente #" + clientId + " cerró la conexión (readLine devolvió null).");
            }

        } catch (SocketException e) {
            
            System.err.println("SocketException para Cliente #" + clientId + ": " + e.getMessage() + ". El cliente probablemente se desconectó abruptamente.");
        } catch (IOException e) {
            System.err.println("IOException para Cliente #" + clientId + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado en ClientManager para Cliente #" + clientId + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Finalizando comunicación con Cliente #" + clientId + ".");
            try {
                if (output != null) output.close();
                if (input != null) input.close();
                if (client != null && !client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                System.err.println("Error al cerrar recursos para Cliente #" + clientId + ": " + e.getMessage());
            }
            Server.removeClient(this);
            // No es buena práctica cerrar el System.in global desde un hilo.
            // if (serverConsoleScanner != null) {
            // serverConsoleScanner.close(); // Esto cerraría System.in para TODO el servidor. ¡No hacer!
            // }
            System.out.println("Hilo para Cliente #" + clientId + " terminado.");
        }
    }

    }

