package perigk.hangdumb.core;

public class Player {
    private String name;
    private int score;
    private int num_wins;
    private int num_losses;

    public Player(String name, int score, int num_wins, int num_losses) {
        this.name = name;
        this.score = score;

        this.num_wins = num_wins;
        this.num_losses = num_losses;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum_wins() {
        return num_wins;
    }

    public void setNum_wins(int num_wins) {
        this.num_wins = num_wins;
    }

    public int getNum_losses() {
        return num_losses;
    }

    public void setNum_losses(int num_losses) {
        this.num_losses = num_losses;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", num_wins=" + num_wins +
                ", num_losses=" + num_losses +
                '}';
    }
}
