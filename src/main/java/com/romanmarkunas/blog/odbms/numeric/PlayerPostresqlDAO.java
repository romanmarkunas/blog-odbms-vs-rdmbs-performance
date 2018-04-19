package com.romanmarkunas.blog.odbms.numeric;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Created by Romans Markuns
 */
public class PlayerPostresqlDAO implements PlayerDAO {

    private final Connection connection;

    private PreparedStatement insert;
    private PreparedStatement select;


    public PlayerPostresqlDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(Player entry) throws DBAccessException {
        try {
            lazyInitInsert();
            this.insert.setString(1, entry.accountId());
            this.insert.setInt(2, entry.gamesPlayed());
            this.insert.setInt(3, entry.gamesWon());
            this.insert.execute();
        }
        catch (SQLException e) {
            throw accessExc("create", entry.accountId(), e);
        }
    }

    @Override
    public Optional<Player> get(String accountId) throws DBAccessException {
        try {
            lazyInitSelect();
            this.select.setString(1, accountId);
            ResultSet rs = this.select.executeQuery();
            if (rs.next()) {
                return Optional.of(new Player(
                        rs.getString("accountId"),
                        rs.getInt("gamesPlayed"),
                        rs.getInt("gamesWon")));
            }
            else {
                return Optional.empty();
            }
        }
        catch (SQLException e) {
            throw accessExc("retrieve", accountId, e);
        }
    }

    @Override
    public void update(Player entry) {
//        create(entry);
    }

    // TODO - check out how JDBC caches during query
    @Override
    public List<Player> topTen() {
        return null;
    }

    @Override
    public void close() throws IOException {
        try {
            closeIfInit(this.insert);
            closeIfInit(this.select);
            this.connection.close();
        }
        catch (SQLException e) {
            throw new IOException("Unable to close database connection!", e);
        }
    }


    private void lazyInitInsert() throws SQLException {
        if (this.insert == null) {
            this.insert = this.connection.prepareStatement(
                    "INSERT INTO numbers " +
                    "(accountId, gamesPlayed, gamesWon) " +
                    "VALUES " +
                    "(?, ?, ?)");
        }
    }

    private void lazyInitSelect() throws SQLException {
        if (this.select == null) {
            this.select = this.connection.prepareStatement(
                    "SELECT * FROM numbers WHERE accountId = ?");
        }
    }

    private void closeIfInit(PreparedStatement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }
}
