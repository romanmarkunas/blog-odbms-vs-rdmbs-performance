package com.romanmarkunas.blog.odbms.numeric;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Player {

    @Id
//    @GeneratedValue() TODO think about generators
    private final String accountId;
    private int gamesPlayed;
    private int gamesWon;


    public Player(String accountId) {
        this(accountId, 0, 0);
    }

    public Player(String accountId, int gamesPlayed, int gamesWon) {
        this.accountId = accountId;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
    }


    public String accountId() {
        return this.accountId;
    }

    public int gamesPlayed() {
        return this.gamesPlayed;
    }

    public int gamesWon() {
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
