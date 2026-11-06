package com.spring.ai.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;

public class TokenUsageAdvisor implements CallAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(TokenUsageAdvisor.class);

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
        ChatResponseMetadata metadata = chatClientResponse.chatResponse().getMetadata();
        if (metadata != null) {
            logger.info("Token count ="+metadata.getUsage().getTotalTokens());
        }

        return chatClientResponse;
    }

    @Override
    public String getName() {
        return "TokenUsageData";
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
