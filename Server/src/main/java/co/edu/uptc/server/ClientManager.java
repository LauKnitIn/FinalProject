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

    private Socket client;
    private BufferedReader input;
    private PrintWriter output;
    private int clientId;
    private Scanner sc = new Scanner(System.in);
    private Mode chooshenMode;

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
            System.out.println(input.hashCode());
            output.println("Hola!, por favor ingresa tu nickname");
            String nickName = input.readLine();
            if (nickName == null) { System.out.println("Cliente #" + clientId + " cerró inmediatamente"); return;}
            output.println(nickName + " , por favor elige un modo de juego: [1. Individual, 2. Multijugador]");
            Mode mode = (input.read() == 1)? Mode.ONE_PLAYER:Mode.MULTIPLAYER;
            Server.connectedClients.add(this);
        } catch (Exception e){
            e.printStackTrace();
        }
            
        //    output.println(serverResponse);
        //     String clientMessage;
        //     while ((clientMessage = input.readLine()) != null) {
        //         System.out.println("Mensaje del Cliente #" + clientId + ": " + clientMessage);

        //         if ("clientes".equalsIgnoreCase(clientMessage.trim())) {
        //             output.println(Server.showActiveClientsList());
        //         } else if ("salir".equalsIgnoreCase(clientMessage.trim())) {
        //             output.println("Adios, cliente. Desconectando...");
        //             System.out.println("Cliente #" + clientId + " solicitó desconexión.");
        //             break;
        //         } else {
        //             System.out.print(">>> Ingrese la respuesta para el Cliente #" + clientId + " (o 'server_exit' para terminar este cliente desde server): ");
        //             serverResponse = sc.nextLine();
        //             if ("server_exit".equalsIgnoreCase(serverResponse.trim())) {
        //                 output.println("El servidor ha terminado tu conexión.");
        //                 System.out.println("Desconexión forzada del Cliente #" + clientId + " desde el servidor.");
        //                 break;
        //             }
        //             output.println(serverResponse);
        //         }
        //     }

        //     if (clientMessage == null) {
        //         System.out.println("Cliente #" + clientId + " cerró la conexión (readLine devolvió null).");
        //     }

        // } catch (SocketException e) {
            
        //     System.err.println("SocketException para Cliente #" + clientId + ": " + e.getMessage() + ". El cliente probablemente se desconectó abruptamente.");
        // } catch (IOException e) {
        //     System.err.println("IOException para Cliente #" + clientId + ": " + e.getMessage());
        // } catch (Exception e) {
        //     System.err.println("Error inesperado en ClientManager para Cliente #" + clientId + ": " + e.getMessage());
        //     e.printStackTrace();
        // } finally {
        //     System.out.println("Finalizando comunicación con Cliente #" + clientId + ".");
        //     try {
        //         if (output != null) output.close();
        //         if (input != null) input.close();
        //         if (client != null && !client.isClosed()) {
        //             client.close();
        //         }
        //     } catch (IOException e) {
        //         System.err.println("Error al cerrar recursos para Cliente #" + clientId + ": " + e.getMessage());
        //     }
        //     Server.removeClient(this);
            // No es buena práctica cerrar el System.in global desde un hilo.
            // if (serverConsoleScanner != null) {
            // serverConsoleScanner.close(); // Esto cerraría System.in para TODO el servidor. ¡No hacer!
            // }
            // System.out.println("Hilo para Cliente #" + clientId + " terminado.");
        
    }

    public Socket getClient() {
        return client;
    }

    public BufferedReader getInput() {
        return input;
    }

    public PrintWriter getOutput() {
        return output;
    }

    public Mode getChooshenMode() {
        return chooshenMode;
    }

    }
