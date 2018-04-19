package com.romanmarkunas.blog.odbms.numeric;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        create10kEntries();

        System.out.println("Starting timed insertions");
        List<Long> results = new ArrayList<>(10);

        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            create10kEntries();
            long duration = System.currentTimeMillis() - start;
            results.add(duration);
            System.out.println(String.format("Completed batch %s withing %s", i, duration));
        }

        long average = results.stream().reduce(Long::sum).get() / 10;
        System.out.println("Completed timed insertions! Average time: " + average);
    }

    @After
    public void tearDown() throws Exception {
        if (this.dao != null) {
            this.dao.close();
        }

        try (Connection connection = connection();
             Statement dropTable = connection.createStatement()) {
            dropTable.execute("DROP TABLE numbers");
        }
    }

    public static void main(String[] args) throws Exception {
        new NumbersTest().tearDown();
    }


    private Connection connection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/numbers",
                "testuser",
                "secret");
    }

    private void create10kEntries() throws DBAccessException {
        Random rn = new Random();
        String prefix = String.valueOf(rn.nextInt(1000)) + String.valueOf(rn.nextInt(1000));
        for (int i = 0; i < 10_000; i++) {
            Player randomPlayer = new Player(
                    prefix + String.valueOf(i),
                    rn.nextInt(100),
                    rn.nextInt(90));
            this.dao.create(randomPlayer);
        }
    }
}