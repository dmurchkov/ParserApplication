package com.dmurchkov.parser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class Worker {

    private static final String SELECT_COUNT_SQL = "SELECT count FROM words WHERE word = ?";
    private static final String INSERT_COUNT_SQL = "INSERT INTO words (word, count) VALUES (?, ?)";
    private static final String UPDATE_COUNT_SQL = "UPDATE words SET count = ? WHERE word = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void doWork(String content) {
        String lines[] = content.split("\\r?\\n");
        Map<String, Integer> wordCount = new HashMap<>();
        for (String line : lines) {
            if (!wordCount.containsKey(line)) {
                wordCount.put(line, 0);
            } else {
                int oldCount = wordCount.get(line);
                wordCount.put(line, oldCount + 1);
            }
        }
        storeResults(wordCount);
    }

    public synchronized void storeResults(Map<String, Integer> wordCount) {
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            Integer existingCount = 0;
            try {
                existingCount = jdbcTemplate.queryForObject(SELECT_COUNT_SQL, Integer.class, entry.getKey());
            } catch (EmptyResultDataAccessException e) {
                System.out.println(String.format("No word %s found", entry.getKey()));
                jdbcTemplate.update(INSERT_COUNT_SQL, entry.getKey(), entry.getValue());
            }
            existingCount = jdbcTemplate.queryForObject(SELECT_COUNT_SQL, Integer.class, entry.getKey()) + 1;
            jdbcTemplate.update(UPDATE_COUNT_SQL, existingCount, entry.getKey());
        }
    }
}
