package com.spring.ai.controller;


import com.spring.ai.service.OpenAiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class OpenAiController {

    private final OpenAiService openAiService;

    @PostMapping("/ask")
    public String getData(@RequestParam String question) {
        return openAiService.getResponse(question);
    }


}
