package co.edu.uptc.model;

import java.util.List;

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
            case "VALIDATE":
                int amount = client.evaluateGame();
                validateState(amount);
                break;
            case "NAME":
                String playerName = command.substring(5);
                client.sendName(playerName);
                break;
            case "START":
                if (parts.length == 3) {
                    String difficulty = parts[2];
                    System.out.println("DIF ->> " + difficulty);
                    int players = Integer.parseInt(parts[1]);
                    System.out.println("player ->> " + players);
                    boolean isMultiplayer = false;
                    if (players == 1) {
                        System.out.println("IN");
                        client.sendGameData(difficulty, isMultiplayer);
                    }
                }
                break;
            case "GUESS":
                String letter = parts[1];
                client.makeGuess(letter);
                break;
            default:
                System.out.println("COMANDO NO RECONOCIDO");
                break;
        }
    }
    public void validateState(int amount){
        if (amount == 0) {
            presenter.setClientState(true);
        }else if (amount == 1) {
            presenter.setClientState(false);
        }else if (amount == 2) {
            presenter.setRoomState(true);
        }
    }

    

    public Client getClient() {
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

    public boolean isFull() {
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

    @Override
    public List<String> getWordList() {
        return client.getWordsByDifficulty();
    }


}
