package co.edu.uptc.model;

import java.util.ArrayList;

public class Word {
    private String value;
    private Difficulty difficulty;
    private ArrayList<String> wordLetters;
    private ArrayList<String> guessedLetters;

    public Word(){
        this.wordLetters = new ArrayList<>();
        this.guessedLetters = new ArrayList<>();
        fillWordLetters();
    }

    private void fillWordLetters(){
        for (int i = 0; i < value.length(); i++) {
            String letter = value.substring(i, i+1);
            this.wordLetters.add(letter);
        }
    }

    public boolean hasLetter(char letter){
        boolean hasLetter = false;
        String toCompare = "" + letter;
        if (wordLetters.contains(toCompare)) {
            hasLetter = true;
            addGuessedLetter(letter);

        }
        return hasLetter;
    }

    private void addGuessedLetter(char letter){
        String letterToAdd = "" + letter;
        this.guessedLetters.add(letterToAdd);
    }

    public ArrayList<String> guessedLetters(char letter){
        //Añadir excepcion en caso de no tener ninguna
        return this.guessedLetters;
    }

    public boolean isComplete(){
        boolean isWordGuessed = false;
        if (wordLetters.containsAll(guessedLetters)) {
            isWordGuessed = true;
        }
        return isWordGuessed;
    }
    
}
