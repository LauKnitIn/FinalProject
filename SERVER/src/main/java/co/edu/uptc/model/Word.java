package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Word {
    private String value;
    private Difficulty difficulty;
    private List<String> wordLetters;
    private Set<String> wrongLetters;
    private Set<String> guessedLetters;

    public Word(String value){
        this.value = value;
        this.wordLetters = new ArrayList<>();
        this.guessedLetters = new HashSet<>();
        this.wrongLetters = new HashSet<>();
        fillWordLetters();
    }

    private void fillWordLetters(){
         for (char letter : value.toCharArray()) {
            this.wordLetters.add(String.valueOf(letter).toUpperCase());
        }
    }

    public boolean hasLetter(char letter){
        String letterStrng = String.valueOf(letter).toUpperCase();

        if (wordLetters.contains(letterStrng)) {
           this.guessedLetters.add(letterStrng);
            return true;
        } else {
            this.wrongLetters.add(letterStrng);
            return false;
        }
    }

    public Set<String> guessedLetters(char letter){
        return this.guessedLetters;
    }

    public boolean isComplete(){
        Set<String> uniqueLetters = new HashSet<>(this.wordLetters);
        boolean isWordGuessed = false;
        if (guessedLetters.containsAll(uniqueLetters)) {
            isWordGuessed = true;
        }
        return isWordGuessed;
    }

     public Set<String> getWrongLetters() {
        return this.wrongLetters;
    }

    public String getProgress() {
        StringBuilder auxSb = new StringBuilder();
        for (String letter : wordLetters) {
            if (guessedLetters.contains(letter)) {
                auxSb.append(letter).append(" ");
            } else {
                auxSb.append("_ ");
            }
        }
        System.out.println();
        return auxSb.toString().trim();
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getValue() {
        return value;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    
}
