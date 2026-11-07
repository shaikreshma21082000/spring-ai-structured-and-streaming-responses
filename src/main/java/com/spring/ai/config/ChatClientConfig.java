package com.spring.ai.config;

import com.spring.ai.advisor.TokenUsageAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

import static org.springframework.ai.chat.client.ChatClient.builder;

@Configuration
public class ChatClientConfig {

    @Value("classpath:/promptTemplate/SystemPromptTemplate.md")
    Resource systemPrompt;

    @Bean
    public ChatClient chatClient(ChatModel chatModel) {

        OpenAiChatOptions.Builder builder = OpenAiChatOptions.builder()
                .temperature(1.0)
                .maxCompletionTokens(1000);

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

        return builder(chatModel)
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(),new TokenUsageAdvisor(),safeGuardAdvisor))
                .defaultSystem(systemPrompt)
                .defaultUser("who are you ?")
                .defaultOptions(builder)
                .build();
    }

    @Bean
    public ChatClient structuredChatClient(ChatModel chatModel) {

        OpenAiChatOptions.Builder builder = OpenAiChatOptions.builder()
                .temperature(1.0)
                .maxCompletionTokens(5000);

        SafeGuardAdvisor safeGuardAdvisor = SafeGuardAdvisor.builder()
                .sensitiveWords(List.of(
                        // prompt injection (first letter dropped so it matches "Ignore" and "ignore")
                        "gnore previous instructions",
                        "gnore all instructions",
                        "system prompt",
                        "reveal your prompt",
                        "jailbreak",
                        "personal info"))
                .failureResponse("I can only respond on country and their cities related information")
                .order(10)
                .build();

        return builder(chatModel)
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(),new TokenUsageAdvisor(),safeGuardAdvisor))
                .defaultSystem("Act as travel guide and provide information about country and their cities in JSON format")
                .defaultUser("who are you ?")
                .defaultOptions(builder)
                .build();
    }
}
