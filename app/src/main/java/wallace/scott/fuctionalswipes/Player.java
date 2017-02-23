package wallace.scott.fuctionalswipes;

/**
 * Created by maxime on 2017-02-22.
 */

public class Player {

    public int playerHighscore;
    public String playerName;

    public Player(String playerNameIn){
        playerName = playerNameIn;
        playerHighscore = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerHighscore() {
        return playerHighscore;
    }

    public void setPlayerHighscore(int playerHighscore) {
        this.playerHighscore = playerHighscore;
    }
}
