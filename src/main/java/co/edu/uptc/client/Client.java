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

        try (
            Socket socket = new Socket(SERVIDOR, PUERTO);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        ) {
            System.out.println("Conectado al servidor " + SERVIDOR + " en puerto " + PUERTO + " " + socket.isConnected());

            //Pedir nombre de jugador a la interfaz
            System.out.print("Ingresa tu nombre: ");
            String playerName = scanner.nextLine();
            output.println("NAME " + playerName);

            //Pedir el modo de juego que se eligio en la interfaz
            System.out.print("Modo de juego (1 = SINGLE, 2 = MULTI): ");
            String mode = scanner.nextLine();
            System.out.print("Dificultad (EASY, NORMAL, DIFFICULT, EXTREME): ");
            String difficulty = scanner.nextLine();
            String fullCommand = "START " + mode + " " + difficulty;  
            if(mode.equals("2")){
                System.out.println("P1 : ");
                String palabra1 = scanner.nextLine();
                System.out.println("P2 : ");
                String palabra2 = scanner.nextLine();
                fullCommand += " " +  palabra1 + " " + palabra2;
            }
            output.println(fullCommand);
            // Bucle principal del juego
            while (true) {  //Manejar lo que se quiere hacer por comandos para que la comunicación sea más facil
                System.out.println("\n--- Opciones ---");
                System.out.println("1. Adivinar letra");
                System.out.println("2. Ver progreso");
                System.out.println("3. Salir");
                System.out.print("Selecciona una opción: ");
                String option = scanner.nextLine();

                if (option.equals("1")) {
                    System.out.print("Ingresa letra: ");
                    String letter = scanner.nextLine().toUpperCase();
                    output.println("GUESS " + letter);
                } else if (option.equals("2")) {
                    output.println("STATUS");
                } else if (option.equals("3")) {
                    output.println("EXIT");
                    System.out.println("Saliendo del juego...");
                    break;
                } else {
                    System.out.println("Opción inválida");
                    continue;
                }
                // Mostrar respuesta del servidor
                String response = input.readLine();
                System.out.println(">> Servidor: " + response);
            }

        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
        /*try (Socket socket = new Socket(SERVIDOR, PUERTO)) {
            System.out.println("Conectado al servidor " + SERVIDOR + " en puerto " + PUERTO + " " + socket.isConnected());
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            System.out.print("Ingrese su nombre: ");
            clientName = scanner.nextLine();
            String mensaje = "Hola servidor, soy el cliente: " + clientName;
            salida.println(mensaje);
            System.out.println("se mando el mensaje");
            String respuesta;
            while ((respuesta = entrada.readLine()) != null) {
                if (!respuesta.equals("Adios, cliente")) {   
                    System.out.println("Respuesta del servidor: " + respuesta);
                    System.out.print("Mensaje para el servidor:");
                    String nuevoMensaje = scanner.nextLine();
                    salida.println(nuevoMensaje);
                }else{
                    System.out.println("Fin de la conversacion");
                    return;
                }
            }
            scanner.close();
        } catch (UnknownHostException e) {
            System.err.println("Host desconocido: " + SERVIDOR);
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }*/
    }
}
