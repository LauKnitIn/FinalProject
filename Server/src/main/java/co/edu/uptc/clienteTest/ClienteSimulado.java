package co.edu.uptc.clienteTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSimulado {

    public static String leerRespuesta(BufferedReader input) {
        String resuesta = "";
        try {
            String responseLine;
            while (!(responseLine = input.readLine()).equals("END")) {
                resuesta += responseLine;
                System.out.println("Servidor: " + responseLine);
            }
        } catch (Exception e) {
            System.out.println("Error leyendo servidor: " + e.getMessage());
        }
        return resuesta;
    }

    public static void main(String[] args) {
         try (Socket socket = new Socket("localhost", 12345);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado al servidor. Escribe comandos (ej. NAME juan, START 1 1, GUESS a):");
                //START 2 1,2.. palabra
            // Ciclo principal
            // GUESS N
            while (true) {
                System.out.print("> ");
                String userInput = scanner.nextLine().trim();

                if (userInput.equalsIgnoreCase("exit")) {
                    System.out.println(" Cliente terminado.");
                    break;
                }

                if (!userInput.isEmpty()) {
                    output.println(userInput);
                    leerRespuesta(input);
                }
            }

        } catch (Exception e) {
            System.out.println(" Error de conexión: " + e.getMessage());
        }
    }
}
