package co.edu.uptc.model;

public enum Difficulty {
    EASY,
    NORMAL,
    DIFFICULT,
    EXTREME;

    public int calculateMaxAttempts(String word) {
        int uniqueLetters = (int) word.toLowerCase()
                .chars()
                .filter(Character::isLetter)
                .distinct()
                .count();

        int modifier = getModifier();

        return Math.max(uniqueLetters + modifier, uniqueLetters);
    }

    private int getModifier(){
         int maxAttempts = 0;
        switch (this) {
            case EASY:
                maxAttempts = 5;
                break;
            case NORMAL:
                maxAttempts = 2;
                break;
            case DIFFICULT:
                maxAttempts = 0;
                break;
            case EXTREME:
                maxAttempts = -2;
                break;
            default:
                maxAttempts = 0;
                break;
        }
        return maxAttempts;
    }
}
