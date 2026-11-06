package com.spring.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

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

        return builder(chatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultSystem(systemPrompt)
                .defaultUser("who are you ?")
                .defaultOptions(builder)
                .build();
    }
}
