package co.edu.uptc.model;


public enum Difficulty {
    EASY,
    NORMAL,
    DIFFICULT,
    EXTREME;

    public int getMaxAttempts(){
        int maxAttempts = 0;
        switch (this) {
            case EASY:
                maxAttempts = 5;
                break;
            case NORMAL:
                maxAttempts = 6;
                break;
            case DIFFICULT:
                maxAttempts = 7;
                break;
            case EXTREME:
                maxAttempts = 10;
                break;
            default:
                maxAttempts = 10;
                break;
        }
        return maxAttempts;
    }
}
