package co.edu.uptc.model;

public class Player {

    private String name;
    private Word wordToGuess;
    private boolean isWinner;
    private int remainingAttempts;


    public Player(int maxAttempts){
        this.remainingAttempts = maxAttempts;
        this.isWinner = false;
    }

    public boolean guessLetter(char letter){
        boolean hasGuessed = wordToGuess.hasLetter(letter);
        if (!(hasGuessed)) {
            this.remainingAttempts--;
        }
        return hasGuessed;
    }

    public boolean hasWon(){
        return wordToGuess.isComplete();
    }
    public boolean hasLost(){
        if (remainingAttempts <= 0) {
            return true;
        }else{
            return false;
        }
    }

    public void chooseWord(Word desiredWord, Player oppPlayer){
        if (desiredWord != null && oppPlayer != null) {
            oppPlayer.setWordToGuess(desiredWord);    
        }
    }

    public Word getWordToGuess() {
        return wordToGuess;
    }

    public void setWordToGuess(Word wordToGuess) {
        this.wordToGuess = wordToGuess;

    }

    public int getRemainingAttempts(){
        return remainingAttempts;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public boolean isWinner() {
        return isWinner;
    } 

    public void setIsWinner(boolean isWinner) {
        this.isWinner = isWinner;
    }
    
}
