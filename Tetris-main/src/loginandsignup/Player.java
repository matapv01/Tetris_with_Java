package loginandsignup;

public class Player {
    private String username;
    private int score, rank ;

    public Player(String username, int score) {
        this.username = username;
        this.score = score;
        this.rank = rank;
    }

    public String getUsername() {
        return username;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    public int getRank(){
        return rank;
    }
}
