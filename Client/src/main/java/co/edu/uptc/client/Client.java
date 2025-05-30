package co.edu.uptc.client;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Client {

    final String SERVIDOR = "localhost";
    final int PUERTO = 12345;
    Scanner scanner = new Scanner(System.in);
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    private static int maxIntentos = 10;

    public interface IntentosListener {
        void onIntentosRestantes(int intentosRestantes);
    }

    private IntentosListener listener;

    public void setIntentosListener(IntentosListener listener) {
        this.listener = listener;
    }

    public static String leerComando(BufferedReader input) {
        String comando = "";
        try {
            comando = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return comando;
    }

    public static String leerRespuesta(BufferedReader input) {
        String response = "";
        String fullResponse = "";
        try {
            while (!(response = input.readLine()).equals("END")) {
                // System.out.println(">> Servidor: " + response);
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
        System.out.println("FULL RESPONSE :" + fullResponse);
        return fullResponse;
    }

    public int getMaxIntentos() {
        return maxIntentos;
    }

    public boolean isFull() {
        boolean isFull = false;
        output.println("FULL ");
        String value = leerRespuesta(input);
        if (value.equals("FULL")) {
            isFull = true;
        }
        return isFull;
    }

    public boolean isEmpty() {
        boolean isEmpty = false;
        output.println("FULL ");
        String value = leerRespuesta(input);
        if (value.equals("EMPTY")) {
            isEmpty = true;
        }
        return isEmpty;
    }

    public boolean isAvailable() {
        boolean isAvailable = false;
        output.println("FULL ");
        String value = leerRespuesta(input);
        if (value.equals("DISPO")) {
            isAvailable = true;
        }
        return isAvailable;
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

    public int evaluateGame() {
        String command = "VALIDATE ";
        output.println(command);
        String val = leerRespuesta(input);
        int toReturn = Integer.parseInt(val);
        return toReturn;
    }

    public void sendGameData(String difficulty, boolean isMultiplayer, String word1, String word2) {
        String mode = isMultiplayer ? "2" : "1";
        String command = "START " + mode + " " + difficulty;
        if (isMultiplayer) {
            command += " " + word1 + " " + word2;
        }
        output.println(command);
        // leerRespuesta(input);
    }

    public List<String> getWordsByDifficulty() {
        Gson gson = new Gson();
        String json = "";
        String command = "GET_LIST";
        output.println(command);
        try {
            json = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type list = new TypeToken<List<String>>() {
        }.getType();
        List<String> requestedList = gson.fromJson(json, list);
        return requestedList;
    }

    public void sendGameData(String difficulty, boolean isMultiplayer) {
        String mode = isMultiplayer ? "2" : "1";
        String command = "START " + mode + " " + difficulty;
        output.println(command);
        leerRespuesta(input);
    }

    public String makeGuess(String letter) {
        String command = "GUESS " + letter.toUpperCase();
        output.println(command);
        String responseLine;
        String progress = "";
        int intentosRestantes = -1;
        try {
            while (!(responseLine = input.readLine()).equals("END")) {
                if (responseLine.startsWith("Intentos restantes: ")) {
                    String num = responseLine.substring("Intentos restantes: ".length());
                    try {
                        intentosRestantes = Integer.parseInt(num.trim());
                    } catch (NumberFormatException e) {
                        intentosRestantes = -1;
                    }
                } else if (responseLine.startsWith("Juego terminado")) {
                    final String respCopy = responseLine;
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        if (respCopy.contains("null")) {
                            javax.swing.JOptionPane.showMessageDialog(null, "¡Has perdido!", "Juego terminado",
                                    javax.swing.JOptionPane.ERROR_MESSAGE);
                        } else {
                            javax.swing.JOptionPane.showMessageDialog(null, "¡Felicidades, ganaste!", "Juego terminado",
                                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        }
                    });
                } else {
                    progress = responseLine;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (listener != null) {
            listener.onIntentosRestantes(intentosRestantes);
        }
        return progress;
    }

    public String getPlayerName() {
        System.out.println("IN NAME METHOD");
        String command = "GET_";
        String fullCommand = command + "NAME";
        output.println(fullCommand);
        String name = leerRespuesta(input);
        return name;
    }

    public void startGame() {
        while (true) {
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

            System.out.print("Ingresa tu nombre: ");
            String playerName = scanner.nextLine();
            output.println("NAME " + playerName);
            leerRespuesta(input);

            System.out.print("Ingrese un numero para modo de juego\n(1 = SINGLE, 2 = MULTI): ");
            String mode = scanner.nextLine();
            System.out.print("Dificultad EASY, NORMAL, DIFFICULT, EXTREME): ");
            String difficulty = scanner.nextLine();
            String fullCommand = "START " + mode + " " + difficulty;
            if (mode.equals("2")) {
                System.out.print("Palabra para el oponente : ");
                String palabra1 = scanner.nextLine();
                fullCommand += " " + palabra1;
                System.out.println(fullCommand);
            }
            output.println(fullCommand);
            // leerComando(input);
            String comando = leerComando(input);
            System.out.println("COMANDO: " + comando);

            while (comando != null && !comando.equals("EMPAREJADO")) {
                // Espera el siguiente mensaje (por ejemplo, cuando llegue el segundo jugador)
                comando = leerComando(input);
                System.out.println("COMANDO: " + comando);
            }

            if ("EMPAREJADO".equals(comando)) {
                System.out.println(leerRespuesta(input));
            }
            while (true) {
                System.out.println("\n--- Opciones ---\n" +
                        "1. Adivinar letra\n" +
                        "2. Ver progreso\n" +
                        "3. Salir");
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
                }
            }

        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
}