package co.edu.uptc.model;


public enum Difficulty {
    EASY,
    NORMAL,
    DIFFICULT,
    EXTREME;

    public int getMaxAttempts(){
        int maxAttempts = 0;
        switch (DIFFICULT) {
            case EASY:
                maxAttempts = 16;
                break;
            case NORMAL:
                maxAttempts = 10;
                break;
            case DIFFICULT:
                maxAttempts = 6;
                break;
            case EXTREME:
                maxAttempts = 5;
                break;
            default:
                maxAttempts = 30;
                break;
        }
        return maxAttempts;
    }
}