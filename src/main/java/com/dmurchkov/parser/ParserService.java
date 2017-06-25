package com.dmurchkov.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class ParserService {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ExecutorService executor = Executors.newFixedThreadPool(3);

    @Autowired
    private Worker worker;

    public void submit(String content) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                worker.doWork(content);
            }
        });
    }

    public List<WordCount> getResults() {
        List<WordCount> result = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT word, count FROM words");
        for (Map<String, Object> row : rows) {
            result.add(new WordCount((String) row.get("word"), (Integer) row.get("count")));
        }
        return result;
    }
}
