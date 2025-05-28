package co.edu.uptc.client;

import co.edu.uptc.model.Mode;
import java.io.*;
import java.net.*;

public class Client {
    final String SERVIDOR_HOST = "localhost";
    final int PUERTO = 12345;
    private Mode selectedGameMode;
    private Socket client;
    BufferedReader entrada;
    PrintWriter salida;

    public Mode getSelectedMode() {
        return selectedGameMode;
    }

    public void setSelectedMode(Mode mode) {
        this.selectedGameMode = mode;
    }

    public void makeConnecition() {
        try {
            this.client = new Socket(SERVIDOR_HOST, PUERTO);
            System.out.println("Conectado al servidor " + SERVIDOR_HOST + " en puerto " + PUERTO);
            
            entrada = new BufferedReader(new InputStreamReader(client.getInputStream()));
            salida = new PrintWriter(client.getOutputStream(), true);
            System.out.println(entrada.readLine());
            salida.println("Billie");
            System.out.println(entrada.readLine());
            salida.println(1);

        } catch (SocketException e) {
            System.err.println("Error de Socket: " + e.getMessage()
                    + ". Es posible que el servidor no esté disponible o haya cerrado la conexión.");
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        } finally {
            System.out.println("Cliente desconectado.");
        }
    }
}