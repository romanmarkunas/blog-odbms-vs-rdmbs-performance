package com.romanmarkunas.blog.odbms.numeric;

import java.util.List;

/**
 * Created by Romans Markuns
 */
public class PlayerPostresqlDAO implements PlayerDAO {

    @Override
    public void create(Player entry) {

    }

    @Override
    public Player get(String accountId) {

    }

    @Override
    public void update(Player entry) {
        create(entry);
    }

    // TODO - check out how JDBC caches during query
    @Override
    public List<Player> topTen() {

    }
}
