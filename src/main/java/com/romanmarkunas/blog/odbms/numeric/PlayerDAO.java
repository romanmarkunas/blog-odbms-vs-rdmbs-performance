package com.romanmarkunas.blog.odbms.numeric;

import java.io.Closeable;
import java.util.List;
import java.util.Optional;

public interface PlayerDAO extends Closeable {

    void create(Player entry) throws DBAccessException;

    Optional<Player> get(String accountId) throws DBAccessException;

    void update(Player entry) throws DBAccessException;

    List<Player> topTen() throws DBAccessException;

    default DBAccessException accessExc(
            String operation,
            String id,
            Exception cause) {
        return new DBAccessException("Cannot " + operation + " Player [" + id + "]", cause);
    }
}
