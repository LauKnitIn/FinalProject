package co.edu.uptc.client;


import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost";
        final int PUERTO = 12345;
        Scanner scanner = new Scanner(System.in);
        String clientName = "";
        try (Socket socket = new Socket(SERVIDOR, PUERTO)) {
            System.out.println("Conectado al servidor " + SERVIDOR + " en puerto " + PUERTO);
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            System.out.print("Ingrese su nombre: ");
            clientName = scanner.nextLine();
            String mensaje = "Hola servidor, soy el cliente: " + clientName;
            salida.println(mensaje);
            String respuesta;
            while ((respuesta = entrada.readLine()) != null) {
                System.out.println("Respuesta del servidor: " + respuesta);
                System.out.print("Mensaje para el servidor:");
                String nuevoMensaje = scanner.nextLine();
                salida.println(nuevoMensaje);
            }
            System.out.println("adios");
            scanner.close();
        } catch (UnknownHostException e) {
            System.err.println("Host desconocido: " + SERVIDOR);
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }
}
