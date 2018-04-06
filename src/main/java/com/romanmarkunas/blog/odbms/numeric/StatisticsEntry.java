package com.romanmarkunas.blog.odbms.numeric;

public class StatisticsEntry {

    private final String accountId;
    private int gamesPlayed;
    private int gamesWon;


    public StatisticsEntry(String accountId) {
        this(accountId, 0, 0);
    }

    public StatisticsEntry(String accountId, int gamesPlayed, int gamesWon) {
        this.accountId = accountId;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
    }


    public String getAccountId() {
        return this.accountId;
    }

    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    public int getGamesWon() {
        return this.gamesWon;
    }

    public void addWin() {
        this.gamesPlayed++;
        this.gamesWon++;
    }

    public void addLoss() {
        this.gamesPlayed++;
    }
}
