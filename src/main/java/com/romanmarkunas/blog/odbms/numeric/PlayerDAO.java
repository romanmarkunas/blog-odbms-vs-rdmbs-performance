package com.romanmarkunas.blog.odbms.numeric;

import java.util.List;

public interface PlayerDAO {

    void create(Player entry);

    Player get(String accountId);

    void update(Player entry);

    List<Player> topTen();
}
