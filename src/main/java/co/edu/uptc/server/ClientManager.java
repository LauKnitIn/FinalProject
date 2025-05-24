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
    Scanner sc = new Scanner(System.in);

    public ClientManager(Socket socket, int nClient) {
        super();
        client = socket;
        this.nClient = nClient;
    }

    public synchronized void run() {
        super.run();
        try {
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(client.getOutputStream(), true);
            String message = input.readLine();
            System.out.print("Mensaje del cliente: " + nClient + " " + message + "\n"
                            +  ">>>Ingrese la respuesta para el cliente #" + nClient + ": ");
            String respuesta = sc.nextLine();
            output.println(respuesta);
            while ((message = input.readLine()) != null) {
                if (message.equalsIgnoreCase("clientes")) {
                    output.println(Server.showActiveClientsList());
                }else if (message.equalsIgnoreCase("salir")) {
                    output.println("Adios, cliente");
                    Server.connectedClients.get(nClient).finalize();
                    System.out.println("Client has desconected");
                    return;
                }else{
                    System.out.println("Mensaje del cliente #" + nClient + " " + message + 
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
