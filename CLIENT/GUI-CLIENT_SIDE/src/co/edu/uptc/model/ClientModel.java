package co.edu.uptc.model;

import java.io.IOException;

import co.edu.uptc.client.Client;
import co.edu.uptc.interfaces.Contract;
import co.edu.uptc.interfaces.Contract.Presenter;

public class ClientModel implements Contract.Model {
    Client client;
    Presenter presenter;

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void initSocket() {
        client = new Client();
        client.createConection();
    }

    @Override
    public void sendCommand(String command) {
        handleCommand(command);
    }

    private void handleCommand(String command) {
        String[] parts = command.split(" ");
        String keyword = parts[0].toUpperCase();
        System.out.println(keyword);
        switch (keyword) {
            case "NAME":
                String playerName = command.substring(5); 
                client.sendName(playerName);
                break;
            case "START":
                if (parts.length == 3) {
                    String difficulty = parts[1];
                    System.out.println("DIF ->> " + difficulty);
                    int players = Integer.parseInt(parts[2]);
                    System.out.println("player ->> " + players);
                    boolean isMultiplayer = false;
                    if (players == 1) {
                        System.out.println("IN");
                        client.sendGameData(difficulty, isMultiplayer);
                    } 
                }
                break;
            case "STARTM":
                if (parts.length == 5) {
                    String difficulty = parts[1];
                    System.out.println("DIF ->> " + difficulty);
                    int players = Integer.parseInt(parts[2]);
                    String word1 = parts[3];
                    String word2 = parts[4];
                    System.out.println("player ->> " + players);
                    boolean isMultiplayer = false;
                    if (players == 2) {
                        System.out.println("IN");
                        client.sendGameData(difficulty, isMultiplayer, word1, word2);
                    }
                }
                break;
            case "GUESS":
                String letter = parts[1];
                client.makeGuess(letter);
                break;

        }
    }

    public Client getClient(){
        return this.client;
    }

    @Override
    public String getStatus(String value) {
        return this.client.makeGuess(value);
    }

    @Override
    public String getClientName() {
        return client.getPlayerName();
    }

    public boolean isFull(){
         return client.isFull();
    }

    @Override
    public boolean isEmpty() {
        return this.client.isEmpty();
    }

    @Override
    public boolean isAvailable() {
        return this.client.isAvailable();
    }

}
