package com.romanmarkunas.blog.odbms.numeric;

import java.util.List;

public interface StatisticsEntryDAO {

    void create(StatisticsEntry entry);

    StatisticsEntry get(String accountId);

    void update(StatisticsEntry entry);

    List<StatisticsEntry> topTen();
}
