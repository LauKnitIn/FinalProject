package co.edu.uptc.model;

import java.util.List;
import java.util.Random;

public class Game {

    private Difficulty chosenDifficulty;
    private Mode gameMode;
    private boolean hasFinished;
    public Player p1;
    public Player p2;
    private WordHandler wordHandler;

    public Game(Difficulty chosenDifficulty, Mode mode) {
        this.chosenDifficulty = chosenDifficulty;
        this.gameMode = mode;
        this.wordHandler = new WordHandler();
        configGame();
    }

    private void configGame() {
        int attempts = chosenDifficulty.getMaxAttempts();
        if (this.gameMode == Mode.ONE_PLAYER) {
            Word assignedWord = getRandomWord();
            this.p1 = new Player(attempts);
            this.p1.setWordToGuess(assignedWord);
        } else if (this.gameMode.equals(Mode.MULTIPLAYER)) {
            this.p1 = new Player(attempts);
            this.p2 = new Player(attempts);
        }

    }

    private Word getRandomWord() {
        List<String> words = wordHandler.getWordListDifficulty(chosenDifficulty);
        Random rand = new Random();
        String chosen = words.get(rand.nextInt(words.size()));
        Word word = new Word(chosen);
        return word;
    }

    public boolean processGuess(char letter) {
        System.out.println("IN");
        boolean isCorrect = p1.guessLetter(letter);
        System.out.println(p1.getWordToGuess() + " <--PALABRA");
        if (gameMode.equals(Mode.ONE_PLAYER)) {
            isGameFinished();
        }
        return isCorrect;
    }

    public boolean processGuess(char letter, Player guessMaker) {
        boolean isCorrect = guessMaker.guessLetter(letter);
        isGameFinished(guessMaker);
        return isCorrect;
    }

    private void isGameFinished() {
        if (p1.hasWon()) {
            this.hasFinished = true;
            p1.setIsWinner(true);
        } else if (p1.hasLost()) {
            p1.setIsWinner(false);
            this.hasFinished = true;
        }
    }

    private void isGameFinished(Player guessMaker) {
        if (guessMaker.hasWon()) {
            this.hasFinished = true;
            guessMaker.setIsWinner(true);
        }
        if (guessMaker.hasLost()) {
            this.hasFinished = true;
            guessMaker.setIsWinner(false);
        }
        if (getOpponent(guessMaker).hasLost()) {
            this.hasFinished = true;
            getOpponent(guessMaker).setIsWinner(false);
        }
        if (isTie(guessMaker)) {
            this.hasFinished = true;
        }
    }

    private Player getOpponent(Player p) {
        return p == p1 ? p2 : p1;
    }

    public String getPlayerProgress() {
        return p1.getWordToGuess().getProgress();
    }

    public int getPlayerAttempts() {
        return p1.getRemainingAttempts();
    }

    public String getPlayerProgress(Player guessMaker) {
        return guessMaker.getWordToGuess().getProgress();
    }

    public int getPlayerAttempts(Player guessMaker) {
        return guessMaker.getRemainingAttempts();
    }

    public Player getWinner() {
        if (this.gameMode.equals(Mode.MULTIPLAYER)) {
            if ((this.p1.isWinner()) && !(this.p2.isWinner())) {
                return p1;
            }
            if ((this.p2.isWinner()) && !(this.p1.isWinner())) {
                return p2;
            }
        } else if (this.gameMode.equals(Mode.ONE_PLAYER)) {
            if (this.p1.isWinner()) {
                return p1;
            }
        }
        return null;
    }

    public void assignWordsMultiplayer(Word wordForP1, Word wordForP2) {
        if (gameMode == Mode.MULTIPLAYER) {
            this.p1.setWordToGuess(wordForP1);
            this.p2.setWordToGuess(wordForP2);
        }
    }

    public boolean isTie(Player guessMaker) {
        boolean isTie = false;
        if ((guessMaker.hasLost()) && (getOpponent(guessMaker).hasLost())) {
            isTie = true;
        }
        return isTie;
    }

    public boolean isFinished() {
        return hasFinished;
    }

    public Player getPLayerTestOne(){
        return this.p1;
    }


}
