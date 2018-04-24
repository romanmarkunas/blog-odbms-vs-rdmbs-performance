package com.romanmarkunas.blog.odbms.numeric;

import com.objectdb.jpa.EMF;
import com.romanmarkunas.blog.odbms.common.ThrowingRunnable;
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
//        this.dao = new PlayerPostresqlDAO(connection());
        this.dao = new PlayerJPADAO(new EMF().createEntityManager());

        create10kEntries();

        System.out.println("Starting timed insertions");
        List<Long> results = new ArrayList<>(10);
        List<String> accountsToQuery = new ArrayList<>(10);

        for (int i = 0; i < 10; i++) {
            runMetered(
                    () -> {
                        String lastId = create10kEntries();
                        accountsToQuery.add(lastId);
                    },
                    results,
                    "insertion batch " + i);
        }

        System.out.println("Completed timed insertions! Average time, ms: " + average(results));

        results.clear();

        System.out.println("Starting timed retrievals");

        for (int i = 0; i < 10; i++) {
            runMetered(
                    () -> {
                        for (String account : accountsToQuery) {
                            this.dao.get(account);
                        }
                    },
                    results,
                    "retrieval batch " + i);
        }

        System.out.println("Completed timed retrievals! Average time, ns: " + average(results));
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

    private String create10kEntries() throws DBAccessException {
        Random rn = new Random();
        String prefix = String.valueOf(rn.nextInt(1000)) + String.valueOf(rn.nextInt(1000));
        for (int i = 0; i < 10_000; i++) {
            Player randomPlayer = new Player(
                    prefix + String.valueOf(i),
                    rn.nextInt(100),
                    rn.nextInt(90));
            this.dao.create(randomPlayer);
        }
        return prefix + "9999";
    }

    private long average(List<Long> measurements) {
        int size = measurements.size();
        if (size == 0) {
            return 0;
        }
        else {
            return measurements
                    .stream()
                    .reduce(Long::sum)
                    .orElse(0L)
                    / size;
        }
    }

    private void runMetered(
            ThrowingRunnable code,
            List<Long> measurements,
            String description) throws Exception {
        long start = System.nanoTime();
        code.run();
        long duration = System.nanoTime() - start;
        measurements.add(duration);
        System.out.println(String.format(
                "Completed %s within %s ms (%s ns)",
                description,
                duration / 1_000_000,
                duration));
    }
}