package com.romanmarkunas.blog.odbms.numeric;

import java.util.List;

public interface PlayerDAO {

    void create(Player entry) throws DBAccessException;

    Player get(String accountId) throws DBAccessException;

    void update(Player entry) throws DBAccessException;

    List<Player> topTen() throws DBAccessException;
}
