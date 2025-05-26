package co.edu.uptc.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientManager extends Thread {

    Socket client;
    BufferedReader input;
    PrintWriter output;
    int nClient;
    Server serverInstance;
    Scanner sc = new Scanner(System.in);

    public ClientManager(Socket socket, int nClient, Server serverInstance) {
        super();
        client = socket;
        this.nClient = nClient;
        this.serverInstance = serverInstance;
    }

    public synchronized void run() {
        try {
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(client.getOutputStream(), true);
            String initialMessage = input.readLine();
            // if (initialMessage != null) {
                
            // }
            System.out.print("Mensaje del cliente: " + nClient + " " + initialMessage + "\n"
                            +  ">>>Ingrese la respuesta para el cliente #" + nClient + ": ");
            String message = sc.nextLine();
            output.println(message);
            while ((message = input.readLine()) != null) {
                if (message.equalsIgnoreCase("clientes")) {
                    output.println(Server.showActiveClientsList());
                }else if (message.equalsIgnoreCase("salir")) {
                    output.println("Adios, cliente");
                    Server.connectedClients.get(nClient).finalize();
                    System.out.println("Client has desconected");
                    sc.close();
                    return;
                }else{
                    System.out.println("Mensaje del cliente #" + nClient + " " + message + "\n" + 
                    ">>>Respuesta para el cliente #" + nClient + ": ");
                    output.println(sc.nextLine());
                }

            }
        } catch (Exception e) {

        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
