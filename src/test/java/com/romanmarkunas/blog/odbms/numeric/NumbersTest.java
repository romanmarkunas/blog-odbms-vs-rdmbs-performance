package com.romanmarkunas.blog.odbms.numeric;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NumbersTest {

    private PlayerDAO dao;


    @Before
    public void setUp() throws Exception {
        try (Connection connection = connection();
             Statement createTable = connection.createStatement()) {
            createTable.execute(
                    "CREATE TABLE numbers (" +
                    "accountId CHAR(20) PRIMARY KEY NOT NULL, " +
                    "gamesPlayed INT DEFAULT 0 NOT NULL, " +
                    "gamesWon INT DEFAULT 0 NOT NULL)");
        }
    }


    @Test
    public void rdbms() throws Exception {
        this.dao = new PlayerPostresqlDAO(connection());
        this.dao.create(new Player("123"));
        Statement getAll = connection().createStatement();
        ResultSet res = getAll.executeQuery("SELECT * FROM numbers");
        while (res.next()) {
            System.out.println("Fetched player with id: " + res.getString(1));
        }
        System.out.println("Done!");
    }


    @After
    public void tearDown() throws Exception {
        this.dao.close();
        try (Connection connection = connection();
             Statement dropTable = connection.createStatement()) {
            dropTable.execute("DROP TABLE numbers");
        }
    }


    private Connection connection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/numbers",
                "testuser",
                "secret");
    }
}