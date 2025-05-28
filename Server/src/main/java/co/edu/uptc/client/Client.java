package co.edu.uptc.client;

import co.edu.uptc.model.Mode;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private Mode selectedGameMode;
    
    public Mode getSelectedMode(){
        return selectedGameMode;
    }
    
    public void setSelectedMode(Mode mode){
        this.selectedGameMode = mode;
    }
    public static void main(String[] args) {
        final String SERVIDOR_HOST = "localhost"; // Renombrado para claridad
        final int PUERTO = 12345;
        Scanner scanner = new Scanner(System.in);
        String clientName = "";
        int choosenMode;
        

        try (Socket socket = new Socket(SERVIDOR_HOST, PUERTO);
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true); // autoFlush
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Conectado al servidor " + SERVIDOR_HOST + " en puerto " + PUERTO);

            System.out.print(entrada.readLine());
            clientName = scanner.nextLine();
            System.out.println(entrada.readLine());
            choosenMode = scanner.nextInt();
            salida.println(choosenMode);
            System.out.println("Mensaje inicial enviado al servidor.");

            String respuestaServidor;
            respuestaServidor = entrada.readLine();
            if (respuestaServidor == null) {
                System.out.println("El servidor cerró la conexión prematuramente.");
                return;
            }
            System.out.println("Respuesta del servidor: " + respuestaServidor);

            String mensajeUsuario;
            while (socket.isConnected() && !socket.isClosed()) { // Bucle mientras esté conectado
                System.out.print("Mensaje para el servidor (o 'salir' para desconectar): ");
                mensajeUsuario = scanner.nextLine();
                salida.println(mensajeUsuario);
                if ("salir".equalsIgnoreCase(mensajeUsuario.trim())) {
                    System.out.println("Solicitando desconexión...");
                }
                respuestaServidor = entrada.readLine();
                if (respuestaServidor == null) {
                    System.out.println("El servidor cerró la conexión.");
                    break;
                }
                System.out.println("Respuesta del servidor: " + respuestaServidor);
                if ("Adios, cliente. Desconectando...".equalsIgnoreCase(respuestaServidor) ||
                        "El servidor ha terminado tu conexión.".equalsIgnoreCase(respuestaServidor)) {
                    System.out.println("Fin de la conversación indicado por el servidor.");
                    break;
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Host desconocido: " + SERVIDOR_HOST);
        } catch (SocketException e) {
            System.err.println("Error de Socket: " + e.getMessage()
                    + ". Es posible que el servidor no esté disponible o haya cerrado la conexión.");
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        } finally {
            scanner.close(); // Cerrar el scanner al final
            System.out.println("Cliente desconectado.");
        }
    }
}