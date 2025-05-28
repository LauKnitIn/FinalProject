package co.edu.uptc.model;

public class Player {

    private String name;
    private Word wordToGuess;
    private int remainingAttempts;


    public Player(String name, Word wordToGuess){
        this.name = name;
        this.wordToGuess = wordToGuess;
    }

    public boolean guessLetter(char letter){
        return wordToGuess.hasLetter(letter);
    }

    public boolean hasWon(){
        return wordToGuess.isComplete();
    }
}
