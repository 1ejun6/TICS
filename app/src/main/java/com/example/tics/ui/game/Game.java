package com.example.tics.ui.game;

public class Game {
    private String gameID;
    private String gameDate;
    private String gameStartTime;
    private String gameEndTime;
    private int gameTicCount;

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(String gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public String getGameEndTime() {
        return gameEndTime;
    }

    public void setGameEndTime(String gameEndTime) {
        this.gameEndTime = gameEndTime;
    }

    public int getGameTicCount() {
        return gameTicCount;
    }

    public void setGameTicCount(int gameTicCount) {
        this.gameTicCount = gameTicCount;
    }
}

