package co.edu.uptc.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    final String SERVIDOR = "localhost";
    final int PUERTO = 12345;
    Scanner scanner = new Scanner(System.in);
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    private static int maxIntentos = 10;

    public static String leerRespuesta(BufferedReader input) {
    String response = "";
    String fullResponse = "";
    try {
        while (!(response = input.readLine()).equals("END")) {
            System.out.println(">> Servidor: " + response);
            fullResponse += response;
            if (response.startsWith("MAX_ATTEMPTS:")) {
                String num = response.substring("MAX_ATTEMPTS:".length());
                try {
                    Client.maxIntentos = Integer.parseInt(num.trim());
                } catch (NumberFormatException e) {
                    Client.maxIntentos = 10;
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    System.out.println("RESPONDE :" + response);
    return fullResponse;
}
    public int getMaxIntentos() {
        return maxIntentos;
    }

    public void createConection() {
        try {
            this.socket = new Socket(SERVIDOR, PUERTO);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            System.out
                    .println("Conectado al servidor " + SERVIDOR + " en puerto " + PUERTO + " " + socket.isConnected());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendName(String name) {
        String command = "NAME " + name;
        System.out.println("Nmobre = " + name);
        output.println(command);
        leerRespuesta(input);
    }

    public void sendGameData(String difficulty, boolean isMultiplayer, String word1, String word2) {
        String mode = "";
        String command = "START ";
        if (isMultiplayer) {
            mode = "2";
        } else {
            mode = "1";
        }
        String fullCommand = command + mode + " " + difficulty;
        if (mode.equals("2")) {
            fullCommand += " " + word1 + " " + word2;
        }
        output.println(fullCommand);
        leerRespuesta(input);
    }

    public void sendGameData(String difficulty, boolean isMultiplayer) {
        String mode = "";
        String command = "START ";
        if (isMultiplayer) {
            mode = "2";
        } else {
            mode = "1";
        }
        String fullCommand = command + mode + " " + difficulty;
        output.println(fullCommand);
        leerRespuesta(input);
    }

    public String makeGuess(String letter) {
        String command = "GUESS ";
        String fullCommand = command + letter.toUpperCase();
        output.println(fullCommand);

        String responseLine;
        String progress = "";
        int intentosRestantes = -1;
        try {
            while (!(responseLine = input.readLine()).equals("END")) {
                System.out.println(">> Servidor: " + responseLine);
                if (responseLine.startsWith("INTENTOS:")) {
                    // Extrae el número de intentos restantes
                    String num = responseLine.substring("INTENTOS:".length());
                    try {
                        intentosRestantes = Integer.parseInt(num.trim());
                    } catch (NumberFormatException e) {
                        intentosRestantes = -1;
                    }
                } else if (!responseLine.startsWith("Juego terminado")) {
                    // Asume que la línea de progreso no empieza con INTENTOS ni "Juego terminado"
                    progress = responseLine;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Notifica a la vista el número de intentos restantes
        if (listener != null) {
            listener.onIntentosRestantes(intentosRestantes);
        }
        return progress;
    }

    public interface IntentosListener {
        void onIntentosRestantes(int intentosRestantes);
    }

    private IntentosListener listener;

    public void setIntentosListener(IntentosListener listener) {
        this.listener = listener;
    }

    public String getPlayerName() {
        System.out.println("IN NAME METHOD");
        String name = "";
        name += leerRespuesta(input);
        return name;
    }

    public void startGame() {
        // Bucle principal del juego
        while (true) { // Manejar lo que se quiere hacer por comandos para que la comunicación sea más
                       // facil
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
                leerRespuesta(input);
            } else if (option.equals("2")) {
                output.println("STATUS");
                leerRespuesta(input);
            } else if (option.equals("3")) {
                output.println("EXIT");
                leerRespuesta(input);
                System.out.println("Saliendo del juego...");
                break;
            } else {
                System.out.println("Opción inválida");
                continue;
            }

        }

    }

    public static void main(String[] args) {

        final String SERVIDOR = "localhost";
        final int PUERTO = 12345;
        Scanner scanner = new Scanner(System.in);

        try (
                Socket socket = new Socket(SERVIDOR, PUERTO);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);) {
            System.out
                    .println("Conectado al servidor " + SERVIDOR + " en puerto " + PUERTO + " " + socket.isConnected());

            // Pedir nombre de jugador a la interfaz
            System.out.print("Ingresa tu nombre: ");
            String playerName = scanner.nextLine();
            output.println("NAME " + playerName);
            leerRespuesta(input);

            // Pedir el modo de juego que se eligio en la interfaz
            System.out.print("Modo de juego (1 = SINGLE, 2 = MULTI): ");
            String mode = scanner.nextLine();
            System.out.print("Dificultad (1, 2, 3, 4): ");
            String difficulty = scanner.nextLine();
            String fullCommand = "START " + mode + " " + difficulty;
            if (mode.equals("2")) {
                System.out.println("P1 : ");
                String palabra1 = scanner.nextLine();
                System.out.println("P2 : ");
                String palabra2 = scanner.nextLine();
                fullCommand += " " + palabra1 + " " + palabra2;
            }
            output.println(fullCommand);
            leerRespuesta(input);

            // Bucle principal del juego
            while (true) { // Manejar lo que se quiere hacer por comandos para que la comunicación sea más
                           // facil
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
                    leerRespuesta(input);
                } else if (option.equals("2")) {
                    output.println("STATUS");
                    leerRespuesta(input);
                } else if (option.equals("3")) {
                    output.println("EXIT");
                    leerRespuesta(input);
                    System.out.println("Saliendo del juego...");
                    break;
                } else {
                    System.out.println("Opción inválida");
                    continue;
                }

            }

        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }

    }
}
