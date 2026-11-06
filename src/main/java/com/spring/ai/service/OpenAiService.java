package com.spring.ai.service;


import com.spring.ai.advisor.TokenUsageAdvisor;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiService {

    private final ChatClient chatClient;


    public String getResponse(String question) {

        SafeGuardAdvisor safeGuardAdvisor = SafeGuardAdvisor.builder()
                .sensitiveWords(List.of(
                        // prompt injection (first letter dropped so it matches "Ignore" and "ignore")
                        "gnore previous instructions",
                        "gnore all instructions",
                        "system prompt",
                        "reveal your prompt",
                        "jailbreak",
                        "personal info"))
                .failureResponse("I can only respond on comic genre topics")
                .order(10)
                .build();

        return chatClient.prompt()
                .advisors(List.of(new TokenUsageAdvisor(),safeGuardAdvisor))
                .user(question)
                .call()
                .content();
    }
}
