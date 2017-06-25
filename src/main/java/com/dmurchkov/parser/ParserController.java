package com.dmurchkov.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ParserController {

    @Autowired
    private ParserService parserService;

    @PostMapping
    public String uploadFiles(@RequestParam("file") MultipartFile... files) throws IOException {
        for (MultipartFile file : files) {
            parserService.submit(new String(file.getBytes()));
        }
        return "File(s) were successfully uploaded";
    }

    @GetMapping
    public List<WordCount> getResults() {
        return parserService.getResults();
    }
}
