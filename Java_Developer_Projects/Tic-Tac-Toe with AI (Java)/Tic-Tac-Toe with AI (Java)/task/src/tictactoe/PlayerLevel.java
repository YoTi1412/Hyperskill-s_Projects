package tictactoe;

public enum PlayerLevel {
    USER("user"),
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    final String description;
    PlayerLevel(String description) {
        this.description = description;
    }

    public static PlayerLevel getByDescription(String description){
        for (PlayerLevel p : PlayerLevel.values()){
            if (p.description.equals(description)){
                return p;
            }
        }

        return null;
    }
}
